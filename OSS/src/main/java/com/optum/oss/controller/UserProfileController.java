/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.controller;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.constants.SessionConstants;
import com.optum.oss.dto.ExceptionDTO;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.dto.UserSecurityDetailsDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.service.impl.LoginServiceImpl;
import com.optum.oss.service.impl.MasterLookUpServiceImpl;
import com.optum.oss.service.impl.UserProfileServiceImpl;
import com.optum.oss.util.CommonUtil;
import com.optum.oss.util.EncryptDecrypt;
import com.optum.oss.util.LoggerUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author hpasupuleti
 */
@Controller
public class UserProfileController {

    private static final Logger logger = Logger.getLogger(UserProfileController.class);

    /*
     Start : Autowiring the required Class instances
     */
    @Resource(name = "myProperties")
    private Properties myProperties;

    @Autowired
    private MasterLookUpServiceImpl masterLookUpService;

    @Autowired
    private UserProfileServiceImpl profileService;

    @Autowired
    private EncryptDecrypt encryptDecrypt;

    @Autowired
    private LoginServiceImpl loginService;

    @Autowired
    private LoggerUtil auditLogger;
    @Autowired
    private CommonUtil commonUtil;
    /*
     START : LOG MESSAGES
     */
    private final String TRACE_USER_EXP_CHANGE_PASS_UPDATE = "User accessed expchangepass Update";
    private final String TRACE_USER_UPD_CHANGE_PSWD = "User accessed upchangepassword";
    private final String TRACE_USER_UP_EXP_USER_PSWD = "User accessed upexpuserpassword";
    private final String TRACE_USER_CONFIRM_PSWD = "User accessed confirmpassword";
    private final String TRACE_USER_VAL_PROFILE_CONFIRM_PSWD = "User accessed validateProfileConfirmpassword";
    private final String TRACE_USER_PER_INFO = "User accessed personnelinfo";
    private final String TRACE_USER_UPD_PER_INFO = "User accessed updpersonnelinfo";
    private final String TRACE_USER_WEB_HELP = "User accessed webHelp";
    /*
     END: LOG MESSAGES
     /*
     End : Autowiring the required Class instances
     */

    @RequestMapping(value = "/changepassword")
    public ModelAndView ViewChangePasswordPage(final HttpServletRequest request,
            final HttpServletResponse response, RedirectAttributes redirectAttributes,
            Model model) {
        if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
            return new ModelAndView("redirect:/logout.htm");
        }
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/usermgmt/changepassword");
        return modelView;
    }

    @RequestMapping(value = "/expchangepass")
    public ModelAndView ViewExpiredUserChangedPasswordPage(final HttpServletRequest request,
            final HttpServletResponse response, RedirectAttributes redirectAttributes,
            Model model) {
        if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
            return new ModelAndView("redirect:/logout.htm");
        }
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/usermgmt/expuserchangepassword");
        return modelView;
    }

    @RequestMapping(value = "/expchangepassUpdate")
    public ModelAndView expChangePassUpdate(final HttpServletRequest request,
            final HttpServletResponse response,
            @RequestParam(value = "currentPswd") String currentPassword,
            @RequestParam(value = "newPswd") String newPassword,
            @RequestParam(value = "confirmPswd") String confirmPassword,
            RedirectAttributes redirectAttributes,
            Model model) {

        ModelAndView modelView = new ModelAndView();
        HttpSession session = request.getSession();
        try {
            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return new ModelAndView("redirect:/logout.htm");
            }
            UserProfileDTO userSessionDto = new UserProfileDTO();
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userSessionDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userSessionDto.getEmail());
                MDC.put("ip", userSessionDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userSessionDto.getEmail(),userSessionDto.getUserMACAddress(), " User accessed expChangePassUpdate page"));
                logger.trace(TRACE_USER_EXP_CHANGE_PASS_UPDATE);
                String changePassFlag = profileService.expChangePassUpdate(currentPassword, newPassword, userSessionDto);
                if (StringUtils.isEmpty(changePassFlag)) {
                    String message = myProperties.getProperty("userconfig.password-update-success");
                    //modelView.setViewName("redirect:/login.htm?smsg="+encryptDecrypt.encrypt(message));
                    modelView.addObject("sucmessage", message);

                    // INVALIDATE USER SESSION AND REDIRECT TO LOGIN PAGE.
                    int retVal = loginService.updateUserSessionInfoByUserID(ApplicationConstants.DB_INDICATOR_DELETE, userSessionDto);
                    logger.info("In logOut:Delete Entry from User Login:retVal:" + retVal);
                    String msg = encryptDecrypt.encrypt(message);
                    session.invalidate();
                    modelView.setViewName("redirect:/login.htm?smsg=" + msg);
                } else {
                    if(changePassFlag.equalsIgnoreCase(ApplicationConstants.OPENDJ_DISCONNECTED)){
                        loginService.updateUserSessionInfoByUserID(ApplicationConstants.DB_INDICATOR_DELETE, userSessionDto);
                           return new ModelAndView("/error");
                    }else{
                    String message = changePassFlag;
                    modelView.addObject("ermessage", message);
                    modelView.setViewName("/usermgmt/expuserchangepassword");
                    }
                }
            }else{
                return new ModelAndView("/login");
            }
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "UpdateChangePasswordAction Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }

        return modelView;
    }

    // below method is using for userprofile change password
    @RequestMapping(value = "/upchangepassword")
    public ModelAndView UpdateChangePasswordAction(final HttpServletRequest request,
            final HttpServletResponse response,
            @RequestParam(value = "currentPswd") String currentPassword,
            @RequestParam(value = "newPswd") String newPassword,
            @RequestParam(value = "confirmPswd") String confirmPassword,
            RedirectAttributes redirectAttributes,
            Model model) {
        ModelAndView modelView = new ModelAndView();
        HttpSession session = request.getSession();
        try {
            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return new ModelAndView("redirect:/logout.htm");
            }
            UserProfileDTO userSessionDto = new UserProfileDTO();
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userSessionDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userSessionDto.getEmail());
                MDC.put("ip", userSessionDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userSessionDto.getEmail(),userSessionDto.getUserMACAddress(), " User accessed UpdateChangePasswordAction page"));
                logger.trace(TRACE_USER_UPD_CHANGE_PSWD);
            }else{
                return new ModelAndView("/login");
            }
            String changePassFlag = profileService.updateChangedUserPassword(currentPassword, newPassword, userSessionDto);
            if (StringUtils.isEmpty(changePassFlag)) {
                String message = myProperties.getProperty("userconfig.profile-update-success");
                //modelView.setViewName("redirect:/login.htm?smsg="+encryptDecrypt.encrypt(message));
                modelView.addObject("sucmessage", message);
                modelView.setViewName("/usermgmt/changepassword");
            } else {
                if(changePassFlag.equalsIgnoreCase(ApplicationConstants.OPENDJ_DISCONNECTED)){
                    loginService.updateUserSessionInfoByUserID(ApplicationConstants.DB_INDICATOR_DELETE, userSessionDto);
                        return new ModelAndView("/error");
                }else{
                String message = changePassFlag;
                modelView.addObject("ermessage", message);
                modelView.setViewName("/usermgmt/changepassword");
                }
            }
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "UpdateChangePasswordAction Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/upexpuserpassword")
    public ModelAndView UpdateExpiredUserPasswordAction(final HttpServletRequest request,
            final HttpServletResponse response,
            @RequestParam(value = "currentPassword") String currentPassword,
            @RequestParam(value = "newPassword") String newPassword,
            @RequestParam(value = "confirmPassword") String confirmPassword,
            RedirectAttributes redirectAttributes,
            Model model) {
        ModelAndView modelView = new ModelAndView();
        HttpSession session = request.getSession();
        try {
            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return new ModelAndView("redirect:/logout.htm");
            }
            UserProfileDTO userSessionDto = new UserProfileDTO();
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userSessionDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userSessionDto.getEmail());
                MDC.put("ip", userSessionDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userSessionDto.getEmail(),userSessionDto.getUserMACAddress(), " User accessed UpdateExpiredUserPasswordAction page"));
                logger.trace(TRACE_USER_UP_EXP_USER_PSWD);
            }else{
                return new ModelAndView("/login");
            }
            //int retVal = profileService.updateChangedUserPassword(currentPassword, newPassword, userSessionDto);
            String changePassFlag = profileService.updateChangedUserPassword(currentPassword, newPassword, userSessionDto);
            if (StringUtils.isEmpty(changePassFlag)) {
                String message = myProperties.getProperty("userconfig.profile-update-success");
                modelView.setViewName("redirect:/login.htm?smsg=" + encryptDecrypt.encrypt(message));
            } //            else if(retVal == -1)
            //            {
            //                String message = myProperties.getProperty("userconfig.incorrect-curpassword");
            //                modelView.setViewName("redirect:/login.htm?msg="+encryptDecrypt.encrypt(message));
            //            }
            //            else if(retVal == -2)
            //            {
            //                String message = myProperties.getProperty("userconfig.change-pass-failed");
            //                modelView.setViewName("redirect:/login.htm?msg="+encryptDecrypt.encrypt(message));
            //            }
            else {
                //String message = myProperties.getProperty("failure-message");
                String message = changePassFlag;
                modelView.setViewName("redirect:/login.htm?msg=" + encryptDecrypt.encrypt(message));
            }
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "UpdateExpiredUserPasswordAction Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/confirmpassword")
    public ModelAndView ViewConfirmPasswordPage(final HttpServletRequest req, final HttpServletResponse resp,
            RedirectAttributes redirectAttributes, Model model) {
        ModelAndView modelView = new ModelAndView();
        String message = "";
        try {
            if (!commonUtil.refreshCheck(req, redirectAttributes, model)) {
                return new ModelAndView("redirect:/logout.htm");
            }
            if (req.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                UserProfileDTO userDto = (UserProfileDTO) req.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User ViewConfirmPasswordPage page"));
                logger.trace(TRACE_USER_CONFIRM_PSWD);
            }else{
                return new ModelAndView("/login");
            }
            if (req.getParameter("msg") != null) {
                message = encryptDecrypt.decrypt(req.getParameter("msg"));
                modelView.addObject("valmessage", message);
            }
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "validateProfileConfirmpassword Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        modelView.setViewName("usermgmt/profileconfirmpassword");
        return modelView;
    }

    @RequestMapping(value = "/validateProfileConfirmpassword")
    public ModelAndView validateProfileConfirmpassword(final HttpServletRequest req, final HttpServletResponse resp,
            @RequestParam(value = "currentPassword") String confirmPassword,
            RedirectAttributes redirectAttributes, Model model) {
        ModelAndView modelView = new ModelAndView();
        try {
            if (!commonUtil.refreshCheck(req, redirectAttributes, model)) {
                return new ModelAndView("redirect:/logout.htm");
            }
            UserProfileDTO userDto = (UserProfileDTO) req.getSession().getAttribute(SessionConstants.USER_SESSION);
            MDC.put("user", userDto.getEmail());
            MDC.put("ip", userDto.getUserMACAddress());
//            logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User accessed ViewConfirmPassword Page"));
            logger.trace(TRACE_USER_VAL_PROFILE_CONFIRM_PSWD);
            String msg = loginService.profileValidateUserpassword(userDto.getEmail(), confirmPassword);
            if (!StringUtils.isEmpty(msg)) {
                if (StringUtils.equalsIgnoreCase(ApplicationConstants.SUCCESS, msg)) {
                    modelView.setViewName("redirect:/personnelinfo.htm");
                } else {
                    if (msg.equalsIgnoreCase(ApplicationConstants.OPENDJ_DISCONNECTED)) {
                      loginService.updateUserSessionInfoByUserID(ApplicationConstants.DB_INDICATOR_DELETE, userDto);
                        return new ModelAndView("/error");
                    } else {
                        msg = encryptDecrypt.encrypt(msg);
                          modelView.addObject("msg", msg);
                        modelView.setViewName("redirect:/confirmpassword.htm");
                    }
                }
            }
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "validateProfileConfirmpassword Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        return modelView;
    }

    @RequestMapping(value = "/personnelinfo")
    public String ViewPersonnelInfoPage(final HttpServletRequest request,
            final HttpServletResponse response, RedirectAttributes redirectAttributes,final Model model) {
//        ModelAndView modelView = new ModelAndView();
        try {
            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return  "redirect:/logout.htm";
            }
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                UserProfileDTO userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());

//                logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User accessed ViewPersonnelInfoPage page"));
                logger.trace(TRACE_USER_PER_INFO);
            }else{
                return "/login";
            }

                    List<MasterLookUpDTO> securityQuestionLi = new ArrayList<>();
                    securityQuestionLi = masterLookUpService.fetchMasterLookUpEntitiesByEntityType(ApplicationConstants.DB_CONSTANT_SECURITY_QUESTION);
                    model.addAttribute("securityQuestionLi", securityQuestionLi);
                    String message = "";
                    if (request.getParameter("msg") != null) {
                        message = encryptDecrypt.decrypt(request.getParameter("msg"));
                        model.addAttribute("valmessage", message);
                    }else {
                        message = (String) model.asMap().get("msg");
                        if(message!=null){
                        message = encryptDecrypt.decrypt(message);
                        model.addAttribute("valmessage", message);
                        }
                   }   
                    
                    if (request.getParameter("smsg") != null) {
                        message = encryptDecrypt.decrypt(request.getParameter("smsg"));
                        model.addAttribute("sucmessage", message);
                    }else {
                         message = (String) model.asMap().get("smsg");
                          if(message!=null){
                          message = encryptDecrypt.decrypt(message);
                           model.addAttribute("sucmessage", message);
                          }
                   }  
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "LoginPage Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return "/exception";
        }
//        modelView.setViewName("/usermgmt/personnelinformation");
        return "/usermgmt/personnelinformation";
    }

    
    @InitBinder("updUserInfo")
    protected void UpdateConfigureUserBinder(WebDataBinder binder) 
            throws Exception
    {
        binder.setAllowedFields(new String[]
                            {"userSecurityQuestionsListObj[0].securityQuestionKey",
                             "userSecurityQuestionsListObj[0].securityAnswer"   ,
                             "userSecurityQuestionsListObj[1].securityQuestionKey",
                             "userSecurityQuestionsListObj[1].securityAnswer"   ,
                             "userSecurityQuestionsListObj[2].securityQuestionKey",
                             "userSecurityQuestionsListObj[2].securityAnswer"   });
    }
    
    @RequestMapping(value = "/updpersonnelinfo")
    public String UpdatePersonnelInfoAction(final HttpServletRequest request,
            final HttpServletResponse response,
            @ModelAttribute(value = "updUserInfo") UserProfileDTO userProfileObj,
            RedirectAttributes redirectAttributes,final Model model) {
//        ModelAndView modelView = new ModelAndView();
        HttpSession session = request.getSession();
        try {
            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return "redirect:/logout.htm";
            }
             redirectAttributes.addFlashAttribute("mnval", "2321D343");
             UserProfileDTO userSessionDto = new UserProfileDTO();
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userSessionDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userSessionDto.getEmail());
                MDC.put("ip", userSessionDto.getUserMACAddress());

//                logger.trace(auditLogger.prepareLog(userSessionDto.getEmail(),userSessionDto.getUserMACAddress(), " User accessed UpdatePersonnelInfoAction page"));
                logger.trace(TRACE_USER_UPD_PER_INFO);
            }else{
                return "/login";
            }

            int res = profileService.updateUserPersonnelInformation(userProfileObj, userSessionDto);

            if (res > 0) {
                List<UserSecurityDetailsDTO> userSecurityList = loginService.fetchUserSecurityQuestionDetails(userSessionDto.getUserProfileKey());

                userSessionDto.setUserSecurityQuestionsListObj(userSecurityList);
                // RE-BUILD THE SESSION WITH NEW DETAILS
                session.setAttribute(SessionConstants.USER_SESSION, null);
                session.setAttribute(SessionConstants.USER_SESSION, userSessionDto);
                // RE-BUILD THE SESSION WITH NEW DETAILS

                String message = myProperties.getProperty("userconfig.profile-update-success");
                redirectAttributes.addFlashAttribute("smsg",  encryptDecrypt.encrypt(message));
                return "redirect:/personnelinfo.htm";
            } else {
                String message = myProperties.getProperty("userconfig.error-profile-update");
                redirectAttributes.addFlashAttribute("msg",  encryptDecrypt.encrypt(message));
                return "redirect:/personnelinfo.htm";
            }
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "LoginPage Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return "/exception";
        }
//        return modelView;
    }

    @RequestMapping(value = "/webHelp")
    public ModelAndView webHelpAction(final HttpServletRequest request,
            final HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) {
        HttpSession session = request.getSession();
        ModelAndView modelView = new ModelAndView();
        try {
            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return new ModelAndView("redirect:/logout.htm");
            }
            UserProfileDTO userSessionDto = null;
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userSessionDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userSessionDto.getEmail());
                MDC.put("ip", userSessionDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userSessionDto.getEmail(),userSessionDto.getUserMACAddress(), " User accessed webHelpAction page"));
                logger.trace(TRACE_USER_WEB_HELP);
                userSessionDto.setEncUserProfileProperty(encryptDecrypt.encrypt(userSessionDto.getEmail()));
            }else{
                return new ModelAndView("/login");
            }
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "webHelpAction Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        modelView.setViewName("webHelp");
        return modelView;
    }

}
