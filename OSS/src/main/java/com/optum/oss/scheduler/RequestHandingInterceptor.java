/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.scheduler;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.constants.SessionConstants;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.service.impl.LoginServiceImpl;
import com.optum.oss.service.impl.UserActivationServiceImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author hpasupuleti
 */
public class RequestHandingInterceptor implements HandlerInterceptor {

    private static final Logger log = Logger.getLogger(RequestHandingInterceptor.class);
    private static volatile int  count=0;
    

    @Autowired
    private UserActivationServiceImpl userActivationService;
    
    @Autowired
    private LoginServiceImpl loginService;
    
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        boolean  redirectFlag=false; 
        HttpSession session = request.getSession();
        UserProfileDTO userSessionDto = null;
        try {
            if (session != null) {
                log.info("In RequestHandingInterceptor:preHandle:session Found");
                String appPath = ApplicationConstants.APPLICATION_PATH;

                //String redirectPath = appPath + "/logout.htm";
                String redirectPath = appPath + "/logout";
                String loginRedirectPath = appPath + "/login.htm";
                String loginTermsOfUsePath = appPath + "/termsofuse.htm";
                String loginPrivacyPolicyPath = appPath + "/privacypolicy.htm";
                String loginValidatePath = appPath + "/validatelogin.htm";
                String forgotPasswordPath = appPath+"/forgotpassword.htm";
                String actForgotPasswordPath = appPath+"/actforgotpassword.htm";
                String inActiveSessionPath = appPath+"/sessionInactive.htm";
                if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                    log.info("In RequestHandingInterceptor:preHandle:User Session Found");
                    redirectFlag =true;
                    userSessionDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                    if (userSessionDto != null) {
                        //log.info("In RequestHandingInterceptor:preHandle:EmailID:" + userSessionDto.getEmail());
                        //log.info("In RequestHandingInterceptor:preHandle:SessionID:" + userSessionDto.getUserSessionID());

                        UserProfileDTO dbUserProfileObj  =userActivationService.
                                checkUserActiveSession(userSessionDto.getEmail());
                        if (!StringUtils.isEmpty(dbUserProfileObj.getUserSessionID())) {
                            
                            if (dbUserProfileObj.getUserSessionID().equalsIgnoreCase
                                            (userSessionDto.getUserSessionID())) {
                                redirectFlag = true;
                                dbUserProfileObj.setUserProfileKey(userSessionDto.getUserProfileKey());
                                loginService.updateUserSessionInfoByUserID
                                        (ApplicationConstants.DB_INDICATOR_UPDATE,dbUserProfileObj);
                                //return true;
                            } else {
                                if(userSessionDto.getActiveSessionFlag() == 'Y')
                                {
                                    count++;
                                    redirectFlag = true; // CHECK ACTIVE SESSION.. IF ACTIVE SESSION RETURN TRUE
                                }
                                else
                                {
                                    redirectFlag = false; // CHECK NO ACTIVE SESSION.. RETURN FALSE
                                }
                            }
                        } else {
                            redirectFlag = false; // FIRST TIME LOGIN/CONCURRENT SESSION(s).
                        }

                    } else {
                        //log.info("redirecting to ...." + redirectPath);
                        redirectFlag = false; // IF USER SESSION IS NOT FOUND.. USER NOT LOGGED IN.. RETURN FALSE
                    }
                    if(!redirectFlag){
                        //log.info("redirecting to ...." + redirectPath);
                        response.sendRedirect(redirectPath);
                        //return false;
                        return true;
                    }
                } else {
                    if(redirectFlag)
                    {
                        //log.info("redirecting to ...." + redirectPath);
                        response.sendRedirect(redirectPath);
                        redirectFlag =false;
                        return false;
                    }
                    else
                    {
                        String reqURL = request.getRequestURL().toString();
                        //log.info("request url::"+reqURL);
                        String actReqURL = loginRedirectPath; 
                        if((!reqURL.equalsIgnoreCase(actReqURL)) && (!reqURL.equalsIgnoreCase(forgotPasswordPath))&&
                            (!reqURL.equalsIgnoreCase(actForgotPasswordPath))&& (!reqURL.equalsIgnoreCase(loginValidatePath))&&
                            (!reqURL.equalsIgnoreCase(loginTermsOfUsePath))&& (!reqURL.equalsIgnoreCase(loginPrivacyPolicyPath)) && (!reqURL.equalsIgnoreCase(inActiveSessionPath)))
                        {
                            //log.info("redirecting to ...." + redirectPath);
                            response.sendRedirect(redirectPath);
                            return true;
                        }
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    /*
    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        HttpSession session = request.getSession(true);
        //if (session != null) {
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                UserProfileDTO userSessionDto = (UserProfileDTO) 
                        session.getAttribute(SessionConstants.USER_SESSION);
                if (userSessionDto != null) {
                    UserProfileDTO dbUserProfileObj = userActivationService.
                            checkUserActiveSession(userSessionDto.getEmail());
                    if (!StringUtils.isEmpty(dbUserProfileObj.getUserSessionID())) {
                        if (dbUserProfileObj.getUserSessionID().equalsIgnoreCase
                                    (userSessionDto.getUserSessionID())) {
                            // UPDATE LAST ACTION PERFORMED DATE TIME
                            dbUserProfileObj.setUserProfileKey(userSessionDto.getUserProfileKey());
                            loginService.updateUserSessionInfoByUserID(ApplicationConstants.DB_INDICATOR_UPDATE, dbUserProfileObj);
                        }
                    }
                }
            }
        //}
        log.info("Post-handle");
    }

    */
    
    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        log.info("Post-handle");
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        log.info("After completion handle");
    }
}
