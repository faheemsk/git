/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.constants.PermissionConstants;
import com.optum.oss.constants.SessionConstants;
import com.optum.oss.dto.ExceptionDTO;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.MenuDTO;
import com.optum.oss.dto.SubMenuDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.dto.UserSecurityDetailsDTO;
import com.optum.oss.dto.Views;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.WebAppAdminMailHelper;
import com.optum.oss.service.impl.LoginServiceImpl;
import com.optum.oss.service.impl.MasterLookUpServiceImpl;
import com.optum.oss.service.impl.UserActivationServiceImpl;
import com.optum.oss.service.impl.UserProfileServiceImpl;
import com.optum.oss.util.CommonUtil;
import com.optum.oss.util.DateUtil;
import com.optum.oss.util.DeleteCookies;
import com.optum.oss.util.EncryptDecrypt;
import com.optum.oss.util.GetManifestVersion;
import com.optum.oss.util.LoggerUtil;
import com.optum.oss.validators.LoginValidator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author hpasupuleti
 */
@Controller
public class LoginController {

    private static final Logger logger = Logger.getLogger(LoginController.class);

    /*
     Start : Autowiring the required Class instances
     */
    @Resource(name = "myProperties")
    private Properties myProperties;

    @Autowired
    private LoginServiceImpl loginService;

    @Autowired
    private UserActivationServiceImpl userActivationService;

    @Autowired
    private EncryptDecrypt encDec;

    @Autowired
    private MasterLookUpServiceImpl masterLookUpService;

    @Autowired
    private DeleteCookies deleteCookies;

    @Autowired
    private UserProfileServiceImpl userProfileService;

    @Autowired
    private WebAppAdminMailHelper adminMailHelper;

    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private LoggerUtil auditLogger;

    @Autowired
    private LoginValidator loginValidator;
    
        @Autowired
    private CommonUtil commonUtil;
    /*
     End : Autowiring the required Class instances
     */

    /*
     START : LOG MESSAGES
     */
    private final String TRACE_USER_LOGIN = "User accessed login page";
    private final String TRACE_USER_VALIDATE = "User accessed validate login page";
    private final String TRACE_USER_PSWD_UPD = "User updated password for first time login";
    private final String TRACE_USER_MAC_UPD = "User mac adressed changed. Verifying action";
    private final String TRACE_USER_FORGOT_PSWD = "User accessed Forgot Password";
    private final String TRACE_USER_LOGOUT = "User accessed logout page";
    private final String INFO_USER_LOGIN_SUCCESS = "User logged in to the application";
    /*
     END: LOG MESSAGES
     */

    // NAVIGATES TO THE LOGIN PAGE AND PRESENTS MESSAGE IF ANY 
    @RequestMapping(value = "/login")
    public ModelAndView LoginPage(final HttpServletRequest request,
            final HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        HttpSession session = request.getSession();
        try {
            MDC.put("user", "");
            MDC.put("ip", "");
            //final String auditLog = auditLogger.prepareLog("", "", "User accessed login page");
            logger.trace(TRACE_USER_LOGIN);
            String message = "";
            if (request.getParameter("msg") != null) {
                message = encDec.decrypt(request.getParameter("msg"));
                modelView.addObject("valmessage", message);
            }
            if (request.getParameter("smsg") != null) {
                message = encDec.decrypt(request.getParameter("smsg"));
                modelView.addObject("sucmessage", message);
            }
            if (request.getParameter("amg") != null) {
                message = encDec.decrypt(request.getParameter("amg"));
                modelView.addObject("actmessage", message);
            }

            session.setAttribute("BUILD_VERSION_SESSION",
                    GetManifestVersion.getVersion());
            modelView.setViewName("login");
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "LoginPage Action :: " + e.getMessage());

            ExceptionDTO exception = new ExceptionDTO("ERR:401", myProperties.getProperty("failure-message"), e);

            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        return modelView;
    }

    // VALIDATES USER CREDENTIALS FORM LOGIN PAGE.
    @RequestMapping(value = "/validatelogin")
    public ModelAndView validateLogin(final HttpServletRequest request,
            final HttpServletResponse response, RedirectAttributes redirectAttributes,
            @RequestParam(value = "user_name") String userName,
            @RequestParam(value = "password") String pswd) {
        ModelAndView modelView = new ModelAndView();
        HttpSession session = request.getSession();
        try {

            String message = "";
            MDC.put("user", userName);
            MDC.put("ip", "");
            logger.trace(TRACE_USER_VALIDATE);
            logger.info(INFO_USER_LOGIN_SUCCESS);
            String retErrorMsg = loginValidator.validateLogin(userName, pswd);
            if (!StringUtils.isEmpty(retErrorMsg)) {
                String msg = encDec.encrypt(retErrorMsg);
                modelView.setViewName("redirect:/login.htm?msg=" + msg);
                return modelView;
            }
            UserProfileDTO userProfileObj = loginService.validateUserLogin(userName, pswd);
            
           
            if (!StringUtils.isEmpty(userProfileObj.getUserValidateMessage())) {
                // REDIRECTING TO ERROR PAGE IF THE OPENDJ IS DISCONNECTED 
                if (userProfileObj.getUserValidateMessage().equalsIgnoreCase(ApplicationConstants.OPENDJ_DISCONNECTED)) {
                    modelView.setViewName("error");
                    userProfileObj.setUserValidateMessage(null);
                } else {
                     // IF ANY ERROR .. REDIRECT BACK TO USER LOGIN PAGE WITH MESSAGE
                    message = encDec.encrypt(userProfileObj.getUserValidateMessage());
                    modelView.setViewName("redirect:/login.htm?msg=" + message);
                }
            } else {
                redirectAttributes.addFlashAttribute("mnval", "mnval");
                // USER IS VALID USER.. ACTIVATE USER TEMPORARY SESSION
                userProfileObj.setUserID(userName);
                userProfileObj.setPassword(pswd);
                session.setAttribute(SessionConstants.USER_SESSION, userProfileObj);
                MDC.put("user", userProfileObj.getEmail());
                MDC.put("ip", userProfileObj.getUserMACAddress());
                //final String auditLog = auditLogger.prepareLog(userProfileObj.getEmail(), userProfileObj.getUserMACAddress(), " User accessed validate login page");
                logger.trace(TRACE_USER_VALIDATE);

                logger.info("Test message :: "+userProfileObj.getActiveSessionFlag());
                //CHECK FOR USER ACTIVE SESSION
                if (userProfileObj.getActiveSessionFlag() == 'Y') {
                    message = myProperties.getProperty("uservalidate.user-active-session");
                    modelView.setViewName("redirect:/login.htm?amg=" + encDec.encrypt(message));
                } //IF USER IS ADMIN THEN REDIRECT TO MANAGE ROLES PAGE
                else if (userProfileObj.getUserTypeObj().getLookUpEntityName().
                        equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_ADMIN)) {
                    modelView.setViewName("redirect:/manageRolepage.htm");
                } // USER LOGS IN TO THE APPLICATION FOR FIRST TIME THEN REIDRECT TO END USER LICENSE AGREEMENT PAGE. -- 
                //IF LAST LOGIN TIME EMPTY AND LOGIN INDICTOR IS 0
                else if ((ApplicationConstants.USER_FIRST_TIME_LOGIN_IND == userProfileObj.getUserVerifiedIndicator())
                        && (StringUtils.isEmpty(userProfileObj.getLastLoginDate()))) {
                    modelView.setViewName("usermgmt/enduserlicense");

                } // CHECKING IF USER REACTIVATED OR ACTIVATED -- IF LOGININDICATOR 0 AND LASTLOGINDATE IS EMPTY
                else if ((ApplicationConstants.USER_FIRST_TIME_LOGIN_IND == userProfileObj.getUserVerifiedIndicator())
                        && (!StringUtils.isEmpty(userProfileObj.getLastLoginDate()))) {
                    modelView.setViewName("redirect:/expchangepass.htm");
                } else {

                    // VALIDATE USER MAC ADDRESS
                    userProfileObj = loginService.validateUserMACAddress(request, userProfileObj);
                    if (!userProfileObj.getUserMACAddress().equalsIgnoreCase(userProfileObj.getNewUserMACAddress())) {

                        // REDIRECT TO SECURITY QUESTION(s) PAGE.
                        modelView.setViewName("redirect:/verifyuser.htm");
                    } else {

                        // CHECK IF USER PASSWORD HAS BEEN EXPIRED 
                        if (userProfileObj.getPswdExpiryFlag() == 'C') {
                            modelView.setViewName("redirect:/expchangepass.htm");
                        } else {

                            //UPDATE LAST LOGIN DATE AND REDIRECT TO USER LANDING PAGE ACTION.
                            loginService.updateUserLastLoginDate(userProfileObj.getUserProfileKey());

                            //REDIRECT TO USER HOME PAGE CONTROLLER.
                            modelView.setViewName("redirect:/userLandingPage.htm");
                        }
                    }
                }

            }
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "LoginPage Action :: " + e.getMessage());

            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);

            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        return modelView;
    }

    @RequestMapping(value = "/checkactivesession")
    public @ResponseBody
    String checkActiveSession(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("user_name") String userName) {
        String message = "";
        try {
            String concurrentSession = "";
            if (System.getProperty("OSS_ALLOW_CONCURRENT_SESSION") != null) {
                concurrentSession = System.getProperty("OSS_ALLOW_CONCURRENT_SESSION");
            }
            if (concurrentSession.equalsIgnoreCase("false")) {
                UserProfileDTO userDto = userActivationService.checkUserActiveSession(encDec.decrypt(userName));
                if (!StringUtils.isEmpty(userDto.getUserSessionID())) {
                    message = myProperties.getProperty("uservalidate.user-active-session");
                } else {
                    message = "No";
                }
            } else {
                message = "No";
            }
        } catch (Exception e) {
            logger.debug("Exceptionoccured : checkActiveSession : " + e.getMessage());
        }
        return message;
    }

    @RequestMapping(value = "/killactivesession")
    public ModelAndView KillUserActiveSession(final HttpServletRequest request,
            final HttpServletResponse response, RedirectAttributes redirectAttributes) throws AppException {
        ModelAndView modelView = new ModelAndView();
        HttpSession session = request.getSession();
        try {
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                UserProfileDTO userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                int kilRet = loginService.killUserActiveSessionDetails(userDto);
                if (kilRet > 0) {
                    redirectAttributes.addFlashAttribute("mnval", "mnval");
                    // IF ADMIN LOGINS AND REDIRECT TO MANAGE ROLES PAGE
                    userDto.setActiveSessionFlag('N');
                    session.setAttribute(SessionConstants.USER_SESSION, null);
                    session.setAttribute(SessionConstants.USER_SESSION, userDto);
                    
                    if (userDto.getUserTypeObj().getLookUpEntityName().
                            equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_ADMIN)) {
                        modelView.setViewName("redirect:/manageRolepage.htm");
                    } // USER LOGS IN TO THE APPLICATION FOR FIRST TIME THEN REIDRECT TO END USER LICENSE AGREEMENT PAGE. -- 
                    //IF LAST LOGIN TIME EMPTY AND LOGIN INDICTOR IS 0
                    else if ((ApplicationConstants.USER_FIRST_TIME_LOGIN_IND == userDto.getUserVerifiedIndicator())
                            && (StringUtils.isEmpty(userDto.getLastLoginDate()))) {
                        modelView.setViewName("usermgmt/enduserlicense");
                    } // CHECKING IF USER REACTIVATED OR ACTIVATED -- IF LOGININDICATOR 0 AND LASTLOGINDATE IS EMPTY
                    else if ((ApplicationConstants.USER_FIRST_TIME_LOGIN_IND == userDto.getUserVerifiedIndicator())
                            && (!StringUtils.isEmpty(userDto.getLastLoginDate()))) {
                        modelView.setViewName("redirect:/expchangepass.htm");
                    } else {

                        // VALIDATE USER MAC ADDRESS
                        userDto = loginService.validateUserMACAddress(request, userDto);
                        if (!userDto.getUserMACAddress().equalsIgnoreCase(userDto.getNewUserMACAddress())) {

                            // REDIRECT TO SECURITY QUESTION(s) PAGE.
                            modelView.setViewName("redirect:/verifyuser.htm");
                        } else {

                            // CHECK IF USER PASSWORD HAS BEEN EXPIRED 
                            if (userDto.getPswdExpiryFlag() == 'C') {
                                modelView.setViewName("redirect:/expchangepass.htm");
                            } else {

                                //UPDATE LAST LOGIN DATE AND REDIRECT TO USER LANDING PAGE ACTION.
                                loginService.updateUserLastLoginDate(userDto.getUserProfileKey());

                                //REDIRECT TO USER HOME PAGE CONTROLLER.
                                modelView.setViewName("redirect:/userLandingPage.htm");
                            }
                        }
                    }
                } else {

                    // REDIRECT TO LOGIN PAGE(S)
                    String message = encDec.encrypt(myProperties.getProperty("failure-message"));
                    modelView.setViewName("redirect:/login.htm?msg=" + message);
                }
            }
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "ConfigureUserPage Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("exception", "exceptionBean", exception);
        }
        return modelView;
    }

    // 
    @RequestMapping(value = "/configureuser")
    public ModelAndView ConfigureUserPage(final HttpServletRequest request,
            final HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {

            List<MasterLookUpDTO> securityQuestionLi = new ArrayList<>();
            securityQuestionLi = masterLookUpService.fetchMasterLookUpEntitiesByEntityType(ApplicationConstants.DB_CONSTANT_SECURITY_QUESTION);
            modelView.addObject("securityQuestionLi", securityQuestionLi);
            if (!StringUtils.isEmpty(request.getParameter("erm"))) {
                modelView.setViewName("/usermgmt/configureuser");
                modelView.addObject("errorMessage", encDec.decrypt(request.getParameter("erm")));

            } else {
                modelView.setViewName("/usermgmt/configureuser");
            }
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "ConfigureUserPage Action :: " + e.getMessage());

            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);

            return new ModelAndView("exception", "exceptionBean", exception);
        }
        return modelView;
    }

    @InitBinder("configUserObj")
//    protected void UpdateConfigureUserBinder(HttpServletRequest request, ServletRequestDataBinder binder) 
//            throws Exception
    protected void UpdateConfigureUserBinder(WebDataBinder binder)
            throws Exception {
        binder.setAllowedFields(new String[]{"userSecurityQuestionsListObj[0].securityQuestionKey",
            "userSecurityQuestionsListObj[0].securityAnswer",
            "userSecurityQuestionsListObj[1].securityQuestionKey",
            "userSecurityQuestionsListObj[1].securityAnswer",
            "userSecurityQuestionsListObj[2].securityQuestionKey",
            "userSecurityQuestionsListObj[2].securityAnswer"});
    }

    @RequestMapping(value = "/updconfigureuser")
    public ModelAndView UpdateConfigureUserAction(final HttpServletRequest request,
            final HttpServletResponse response,
            @RequestParam(value = "currentPwd") String currentPwd,
            @RequestParam(value = "newPwd") String newPwd,
            @RequestParam(value = "confirmPwd") String confirmPwd,
            @ModelAttribute(value = "configUserObj") UserProfileDTO userProfileObj) {
        ModelAndView modelView = new ModelAndView();
        try {

            UserProfileDTO userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                //final String auditLog = auditLogger.prepareLog(userDto.getEmail(), userDto.getUserMACAddress(), " User saving definition of permission in page");
                logger.trace(TRACE_USER_PSWD_UPD);
            }
            userProfileObj.setEmail(userDto.getEmail());
            userProfileObj.setUserSessionID(userDto.getUserSessionID());
            userProfileObj.setUserProfileKey(userDto.getUserProfileKey());
            userProfileObj.setFirstName(userDto.getFirstName());
            userProfileObj.setLastName(userDto.getLastName());
            userProfileObj = loginService.insertUserSecurityDetails(null, userProfileObj, currentPwd, newPwd, confirmPwd, request);
            if (!StringUtils.isEmpty(userProfileObj.getUserValidateMessage())) {
                 if(userProfileObj.getUserValidateMessage().equalsIgnoreCase(ApplicationConstants.OPENDJ_DISCONNECTED)){
                    // REDIRECTING TO ERROR PAGE IF THE OPENDJ IS DISCONNECTED 
                    loginService.updateUserSessionInfoByUserID(ApplicationConstants.DB_INDICATOR_DELETE, userDto);
                           return new ModelAndView("error");
                }else{
                String message = encDec.encrypt(userProfileObj.getUserValidateMessage());
                modelView.setViewName("redirect:/configureuser.htm?erm=" + message);
                 }
            } else {
               
                // MAIL SEND TO USER 
                // 1. PROFILE UPDATION SUCCESS
                // 2. NEW PASSWORD
                // REDIRECT TO LOGIN PAGE WITH PROFILE UPDATED MESSAGE
                String message = encDec.encrypt(myProperties.getProperty("userconfig.profile-update-success"));
                modelView.setViewName("redirect:/login.htm?smsg=" + message);
                request.getSession().invalidate();
            }
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "UpdateConfigureUserAction Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("exception", "exceptionBean", exception);
        }
        return modelView;
    }

    /*
     VerifyUserChangeMacAddress is used for Validate User when he logged in to new device.
     */
    @RequestMapping(value = "/verifyuser")
    public ModelAndView VerifyUserChangeMacAddress(final HttpServletRequest request,
            final HttpServletResponse response,final Model model, final RedirectAttributes redirectAttributes
    ) {
        ModelAndView modelView = new ModelAndView();
        HttpSession session = request.getSession();
        try {
if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return new ModelAndView("redirect:/logout.htm");
            }
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                UserProfileDTO userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                //final String auditLog = auditLogger.prepareLog(userDto.getEmail(), userDto.getUserMACAddress(), " User accessed verify user change macaddress page");
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(TRACE_USER_MAC_UPD);
                List<UserSecurityDetailsDTO> securityDtlsLi = userDto.getUserSecurityQuestionsListObj();
                Integer count = 0;
                if (request.getParameter("count") != null) {
                    String enCount = encDec.decrypt(request.getParameter("count"));
                    count = (Integer.parseInt(enCount));
                    if (count == 1) {
                        // SENDING MAIL TO USER THAT HE IS LOGIN IN TO NEW DEVICE
                        adminMailHelper.mail_UserLoggingInAnotherBrowser(userDto, userDto.getUserProfileKey());
                        modelView.addObject("errorMessage", myProperties.getProperty("userconfig.invalid-security-Answer-2-Attempts"));
                    }
                    if (count == 2) {
                        modelView.addObject("errorMessage", myProperties.getProperty("userconfig.invalid-security-Answer-1-Attempts"));
                    }
                    if (count == 3) {
                        // LOCKING USER AFTER THREE ATTEMPTS
                        loginService.lockUnlockUserBasedOnUserID(userDto.getUserProfileKey(),
                                ApplicationConstants.DB_ROW_STATUS_LOCKED);
//                         IF USER IS SUCCESSFULLY LOCKED SENDING LOCKED MAIL TO USER
                        adminMailHelper.mail_UserAccountLocked(userDto, userDto.getUserProfileKey());
                        String message = encDec.encrypt(myProperties.getProperty("uservalidate.account-locked"));
                        modelView.setViewName("redirect:/login.htm?msg=" + message);
                    }

                    count++;
                    String IncCount = encDec.encrypt(count.toString());
                    modelView.addObject("count", IncCount);

                }
                if (count < 4) {
                    if (!securityDtlsLi.isEmpty()) {
                        Random random = new Random();
                        int index1 = random.nextInt(securityDtlsLi.size());
                        modelView.addObject("securityDtls1", securityDtlsLi.get(index1));
                    } else {
                        modelView.addObject("message", myProperties.getProperty("forgotpass.user-notconfigued"));
                    }
                    modelView.setViewName("/usermgmt/verifyuser");
                }
            }
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "VerifyUserChangeMacAddress Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("exception", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/forgotpassword")
    public ModelAndView ForgotPassword(final HttpServletRequest request,
            final HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            MDC.put("user", "");
            MDC.put("ip", request.getRemoteAddr());
            //final String auditLog = auditLogger.prepareLog("", "", " User accessed Forgot user's login page");
            logger.trace(TRACE_USER_FORGOT_PSWD);
            String encCheck = encDec.encrypt(ApplicationConstants.APP_CONSTANT_CHECK);
            String encValidate = encDec.encrypt(ApplicationConstants.APP_CONSTANT_VALIDATE);
            String encSave = encDec.encrypt(ApplicationConstants.APP_CONSTANT_SAVE);

            modelView.addObject("Check", encCheck);
            modelView.addObject("Validate", encValidate);
            modelView.addObject("Save", encSave);
            modelView.addObject("divshow", "false");
            modelView.setViewName("/usermgmt/forgotpassword");
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "ForgotPassword Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("exception", "exceptionBean", exception);
        }
        return modelView;
    }

    @RequestMapping(value = "/actforgotpassword")
    public ModelAndView UpdateForgotPasswordAction(final HttpServletRequest request,
            final HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            if (request.getParameter("username") != null) {
                String username = request.getParameter("username");
                String securityQuesID1 = request.getParameter("securityQ1");
                String securityQuesID2 = request.getParameter("securityQ2");
                String securityAnswer1 = request.getParameter("securityAnswer1");
                String securityAnswer2 = request.getParameter("securityAnswer2");

                String encCheck = encDec.encrypt(ApplicationConstants.APP_CONSTANT_CHECK);
                String encValidate = encDec.encrypt(ApplicationConstants.APP_CONSTANT_VALIDATE);
                String encCount = encDec.encrypt(ApplicationConstants.APP_CONSTANT_SAVE);

                modelView.addObject("Check", encCheck);
                modelView.addObject("Validate", encValidate);
                modelView.addObject("username", username);

                if (request.getParameter("chk") != null) {

                    // IF USER CLICKS ON CHECK AFTER ENTERING THE USER NAME
                    UserProfileDTO userProfileObj = loginService.fetchUserDetailsForForgotPassword(username);
                    List<UserSecurityDetailsDTO> securityDtlsLi = loginService.
                            fetchUserSecurityQuestionDetails(userProfileObj.getUserProfileKey());
                    String encChk = request.getParameter("chk").trim();
                    if (encChk.trim().equalsIgnoreCase(encCheck.trim())) {
                        if ((StringUtils.isEmpty(userProfileObj.getUserValidateMessage()))) {
                            if (!securityDtlsLi.isEmpty()) {
                                Random random = new Random();
                                int index1 = random.nextInt(securityDtlsLi.size());
                                modelView.addObject("securityDtls1", securityDtlsLi.get(index1));
                                int index2 = random.nextInt(securityDtlsLi.size());
                                if (index1 != index2) {
                                    modelView.addObject("securityDtls2", securityDtlsLi.get(index2));
                                } else {
                                    while (index1 == index2) {
                                        index2 = random.nextInt(securityDtlsLi.size());
                                    }
                                    modelView.addObject("securityDtls2", securityDtlsLi.get(index2));
                                }
                            } else {
                                modelView.addObject("divshow", "false");
                                modelView.addObject("message", myProperties.getProperty("forgotpass.user-notconfigued"));
                            }
                            modelView.addObject("divshow", "true");

                        } else {
                            String message = userProfileObj.getUserValidateMessage();
                            modelView.addObject("message", message);
                            modelView.addObject("divshow", "false");

                        }
                        modelView.setViewName("/usermgmt/forgotpassword");
                    } else if (encChk.trim().equalsIgnoreCase(encValidate.trim())) {

                        // IF USER CLICKS ON SUBMIT AFTER ENTERING THE ANSWER(s)
                        String count = "";
                        if (request.getParameter("count") != null) {
                            count = request.getParameter("count");
                        }

                        int retVal = loginService.validateSecurityAnswersInForgotPassword(securityDtlsLi,
                                securityQuesID1, securityAnswer1, securityQuesID2, securityAnswer2);

                        if (retVal > 0) {
                            int res = loginService.changeLDAPPAsswordForForgotPassword(userProfileObj);
                            if (res > 0) {
                                String message = encDec.encrypt(myProperties.getProperty("forgotpass.password-update-success"));
                                modelView.setViewName("redirect:/login.htm?smsg=" + message);
                            } else {
                                String message = encDec.encrypt(myProperties.getProperty("failure-message"));
                                modelView.setViewName("redirect:/login.htm?msg=" + message);
                            }
                        } else {

                            // IF USER IS SUBMITTING FOR FIRST TIME COUNT WILL BE EMPTY
                            if (StringUtils.isEmpty(count)) {
                                modelView.addObject("message", myProperties.getProperty("forgotpass.invalid-answers"));
                                Random random = new Random();
                                int index1 = random.nextInt(securityDtlsLi.size());
                                modelView.addObject("securityDtls1", securityDtlsLi.get(index1));
                                int index2 = random.nextInt(securityDtlsLi.size());
                                if (index1 != index2) {
                                    modelView.addObject("securityDtls2", securityDtlsLi.get(index2));
                                } else {
                                    while (index1 == index2) {
                                        index2 = random.nextInt(securityDtlsLi.size());
                                    }
                                    modelView.addObject("securityDtls2", securityDtlsLi.get(index2));
                                }
                                modelView.addObject("count", encCount);
                                modelView.addObject("divshow", "true");
                                modelView.setViewName("/usermgmt/forgotpassword");
                            } // FOR THE SECOND TIME - IF USER ENTERS INVALID ANSWERS
                            else {
                                // LOCK THE USER
                                loginService.lockUnlockUserBasedOnUserID(userProfileObj.getUserProfileKey(),
                                        ApplicationConstants.DB_ROW_STATUS_LOCKED);
                                // IF USER IS SUCCESSFULLY LOCKED
                                String message = encDec.encrypt(myProperties.getProperty("uservalidate.account-locked"));
                                modelView.setViewName("redirect:/login.htm?msg=" + message);
                            }
                        }
                    } else {
                        String message = encDec.encrypt(myProperties.getProperty("failure-message"));
                        modelView.setViewName("redirect:/login.htm?msg=" + message);
                    }
                } else {
                    String message = encDec.encrypt(myProperties.getProperty("failure-message"));
                    modelView.setViewName("redirect:/login.htm?msg=" + message);
                }
            } else {
                modelView.setViewName("redirect:/login.htm");
            }
        } catch (AppException e) {
            modelView.setViewName("redirect:/login.htm");
        }
        return modelView;
    }

    // USER LOGOUT CONTROLLER-- SESSION WILL BE DELETED AND PROFILE WILL BE UPDATED.
    @RequestMapping(value = "/logout")
    public ModelAndView Logout(final HttpServletRequest request,
            final HttpServletResponse response) {
        HttpSession session = request.getSession();
        logger.info("Start : logout");
        try {

            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                UserProfileDTO userSessionDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userSessionDto.getEmail());
                MDC.put("ip", userSessionDto.getUserMACAddress());
                //final String auditLog = auditLogger.prepareLog(userSessionDto.getEmail(), userSessionDto.getUserMACAddress(), " User accessed logout page");
                logger.trace(TRACE_USER_LOGOUT);
                int retVal = loginService.updateUserSessionInfoByUserID(ApplicationConstants.DB_INDICATOR_DELETE, userSessionDto);
                logger.info("In logOut:Delete Entry from User Login:retVal:" + retVal);

            }
            session.invalidate();
            deleteCookies.eraseCookies(request, response);

        } catch (AppException ie) {
            logger.debug("Exception Occured while Executing the "
                    + "logOut() :: " + ie.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), ie);
            return new ModelAndView("exception", "exceptionBean", exception);
        }
        return new ModelAndView("redirect:/login.htm");
    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/refreshlogout")
    public @ResponseBody
    String refreshLogout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        logger.info("Start : refreshLogout");
        try {

            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                UserProfileDTO userSessionDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userSessionDto.getEmail());
                MDC.put("ip", userSessionDto.getUserMACAddress());
                logger.trace(TRACE_USER_LOGOUT);
                int retVal = loginService.updateUserSessionInfoByUserID(ApplicationConstants.DB_INDICATOR_DELETE, userSessionDto);
                logger.info("In refreshLogout:Delete Entry from User Login:retVal:" + retVal);

            }
            session.invalidate();
            deleteCookies.eraseCookies(request, response);

        } catch (AppException e) {
            logger.debug("Exception occured : refreshLogout : " + e.getMessage());
        }
        return "success";
    }

    /**
     * This method used for load terms of use page
     *
     * @param request
     * @param response
     * @return ModelAndView
     */
    @RequestMapping(value = "/termsofuse")
    public ModelAndView termsOfUse(final HttpServletRequest request,
            final HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("usermgmt/termsofuse");
        return modelView;

    }

    /**
     * This method used for load privacy policy page
     *
     * @param request
     * @param response
     * @return ModelAndView
     */
    @RequestMapping(value = "/privacypolicy")
    public ModelAndView privacyPolicy(final HttpServletRequest request,
            final HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("usermgmt/privacypolicy");
        return modelView;

    }

    /**
     * This method used for navigatE to the User landing page
     *
     * @param req
     * @param resp
     * @return ModelAndView
     */
    @RequestMapping(value = "/userLandingPage")
    public ModelAndView userLandingPageController(final HttpServletRequest req, final HttpServletResponse resp,
            RedirectAttributes redirectAttributes) {
        ModelAndView modelView = new ModelAndView();
        logger.info("in userlandingpage controller...");
        try {

            if (req.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                UserProfileDTO userDto = (UserProfileDTO) req.getSession().getAttribute(SessionConstants.USER_SESSION);
                // IF USER IS CLIENT USER THEN NAVIGATE TO DASHBOARD AND REPORTS LANDING ACTION
                
                 UserProfileDTO userProfileObjCheck = userProfileService.prepareUserPermissions(userDto);
                 
                 boolean isClient=userDto.getUserTypeObj().getLookUpEntityName().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_CLIENT);
                 boolean clintOwner = (userProfileObjCheck.getSubMenuPermissionMap().containsKey(PermissionConstants.REMEDIATION_MODULE) ||  userProfileObjCheck.getSubMenuPermissionMap().containsKey(PermissionConstants.RISK_REGISTRY_MODULE));
                 
                 req.getSession().removeAttribute(ApplicationConstants.CONSTANT_ONLY_DASHBOARD_PERMISSION);
                 if (isClient && !clintOwner) {
                     req.getSession().setAttribute(ApplicationConstants.CONSTANT_ONLY_DASHBOARD_PERMISSION,ApplicationConstants.CONSTANT_ONLY_DASHBOARD_PERMISSION);
                    modelView.setViewName("redirect:/dashboardhomecontroller.htm");
                } else {
                    UserProfileDTO userProfileObj = userProfileService.prepareUserPermissions(userDto);
                    MDC.put("user", userDto.getEmail());
                    MDC.put("ip", userDto.getUserMACAddress());

                    //final String auditLog = auditLogger.prepareLog(userDto.getEmail(), userDto.getUserMACAddress(), " User accessed validate user login page");
                    logger.trace("User accessed validate user login page");
                    redirectAttributes.addFlashAttribute("mnval", "mnval");
                    // CHECKING FOR USER HAVE ANY ROLES AND PERMISSIONS IF NOT REDIRECT TO LOGIN PAGE AND SHOW ERROR MESSAGE
                    if (userProfileObj.getMenuList().isEmpty()) {
                        String message = myProperties.getProperty("uservalidate.user-norole-permission");
                        modelView.setViewName("redirect:/login.htm?msg=" + encDec.encrypt(message));
                    } else {
                        boolean managerolefalg = false;
                        for (MenuDTO menu : userProfileObj.getMenuList()) {
                            // CHECKING REDIRECTION AND NAVIGATING TO THE PAGE
                            if (menu.getSubMenuExistsFlag() == 1) {
                                Iterator<SubMenuDTO> itr = menu.getSubMenuObjSet().iterator();
                                while (itr.hasNext()) {
                                    SubMenuDTO dto = itr.next();
                                    modelView.setViewName("redirect:/" + dto.getAppLink());
                                    managerolefalg = true;
                                    break;
                                }
                            } else {
                                modelView.setViewName("redirect:/" + menu.getAppLink());
                                break;
                            }
                            if (managerolefalg) {
                                break;
                            }
                        }
                    }
                }
            }
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "UpdateConfigureUserAction Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("exception", "exceptionBean", exception);
        }
        return modelView;
    }

    /*
     validateVerifyUser is used for Validate User when he logged in to new device.
     */
    @RequestMapping(value = "/validateverifyuser")
    public ModelAndView validateVerifyUser(final HttpServletRequest request,
            final HttpServletResponse response,
            @RequestParam(value = "securityQ1") String securityQuesID1,
            @RequestParam(value = "securityAnswer1") String securityAnswer1,Model model,RedirectAttributes redirectAttributes) {
        ModelAndView modelView = new ModelAndView();
        HttpSession session = request.getSession();
        UserProfileDTO userDto = new UserProfileDTO();
        try {
            
                if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return new ModelAndView("redirect:/logout.htm");
            }

            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
            }

            MDC.put("user", userDto.getEmail());
            MDC.put("ip", userDto.getUserMACAddress());

            //final String auditLog = auditLogger.prepareLog(userDto.getEmail(), userDto.getUserMACAddress(), " User accessed validate verify user page");
            logger.trace("User accessed validate verify user page");

            if (null != userDto.getUserSecurityQuestionsListObj()) {
                List<UserSecurityDetailsDTO> securityDtlsLi = userDto.getUserSecurityQuestionsListObj();
                int retVal = loginService.validateSecurityAnswersInVerifyUser(securityDtlsLi,
                        securityQuesID1, securityAnswer1);
                if (retVal > 0) {
                    userDto.setLastLoginDate(dateUtil.generateDBCurrentDateInString());
                    userDto.setUserMACAddress(userDto.getNewUserMACAddress());
                    loginService.updateUserMacAddressForNewDevice(userDto);
                    modelView.setViewName("redirect:/userLandingPage.htm");
                } else {
                    String encCount = encDec.encrypt(ApplicationConstants.VERIFY_SECURITY_QUESTIONS_COUNT.toString());
                    String iCount = "";
                    if (!request.getParameter("count").isEmpty()) {
                        iCount = request.getParameter("count");

                    } else {
                        iCount = encCount;
                    }
                    modelView.addObject("count", iCount);
                    modelView.setViewName("redirect:/verifyuser.htm");
                }
            }
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "validateVerifyUser Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("exception", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/getSessionTime", method = RequestMethod.POST)
    public @ResponseBody
    String getUserSessionTime(HttpServletRequest request, HttpServletResponse response) {
        String returnSessionTime = "0";
        try {
            HttpSession session = request.getSession();
            returnSessionTime = session.getMaxInactiveInterval() + "";
        } catch (Exception e) {
            logger.debug("Exceptionoccured : getUserSessionTime : " + e.getMessage());
        }
        return returnSessionTime;
    }

    @RequestMapping(value = "/sessionInactive")
    public ModelAndView sessionInactive(final HttpServletRequest request,
            final HttpServletResponse response, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        ModelAndView modelView = new ModelAndView();
        try {
            session.invalidate();
            deleteCookies.eraseCookies(request, response);
            redirectAttributes.addFlashAttribute("sessionInactiveMessage", myProperties.getProperty("uservalidate.user-inactive-session"));
        } catch (Exception e) {
            logger.debug("Exception Occured while Executing the "
                    + "sessionInactive Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("exception", "exceptionBean", exception);
        }

        return new ModelAndView("redirect:/login.htm");
    }
}
