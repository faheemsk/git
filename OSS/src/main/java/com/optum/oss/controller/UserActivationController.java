/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.controller;

import com.optum.oss.constants.PermissionConstants;
import com.optum.oss.constants.SessionConstants;
import com.optum.oss.dto.ExceptionDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.PermissionCheckHelper;
import com.optum.oss.service.impl.UserActivationServiceImpl;
import com.optum.oss.util.CommonUtil;
import com.optum.oss.util.EncryptDecrypt;
import com.optum.oss.util.LoggerUtil;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author schanganti
 */
@Controller
public class UserActivationController {

    private static final Logger log = Logger.getLogger(UserActivationController.class);

    @Resource(name = "myProperties")
    private Properties myProperties;

    @Autowired
    private UserActivationServiceImpl useractivationservice;

    @Autowired
    private ExceptionDTO exception;

    @Autowired
    private EncryptDecrypt encryptDecrypt;

    @Autowired
    PermissionCheckHelper permissionCheckHelper;
    
    @Autowired
    private LoggerUtil auditLogger;
    @Autowired
    private CommonUtil commonUtil;
    /*
    START : LOG MESSAGES
    */
    private final String TRACE_USER_WORKLIST="User accessed userworklist";
    private final String TRACE_USER_CHANGE_USER_STATUS="User accessed changeuserstatus";
    /*
    END: LOG MESSAGES
    /*
     @parm  organizationKey
     @return List<UserProfileDTO>
     @throws AppException
     */

    @RequestMapping(value = "/userworklist")
    public ModelAndView userActivationWorklist(HttpServletRequest request, HttpServletResponse response,
            RedirectAttributes redirectAttributes, Model model) throws AppException {
        ModelAndView modelView = new ModelAndView();
        List<UserProfileDTO> userDTO = null;
        long userTypeKey;
        long orgid = 0;
        HttpSession session = request.getSession();
        Set<String> permissionSet = new HashSet<String>();
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return new ModelAndView("redirect:/logout.htm");
            }
            
            UserProfileDTO userDto = new UserProfileDTO();
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user",userDto.getEmail());
                MDC.put("ip",userDto.getUserMACAddress());
//                log.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " UseractivationWorklist page"));
                log.trace(TRACE_USER_WORKLIST);
                permissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.USER_ACTIVATION_MENU);
            }else{
                return new ModelAndView("/login");
            }

            if (permissionCheckHelper.checkUserMenu(userDto, PermissionConstants.USER_ACTIVATION_MENU)) {
                modelView.setViewName("usermgmt/Useractivation");
            } else {
                modelView.setViewName("redirect:/logout.htm");
            }

            List<UserProfileDTO> worklist = useractivationservice.userActivationWorklist(orgid);
            modelView.addObject("worklist", worklist);
            modelView.addObject("successmsg", request.getParameter("successmsg"));
            modelView.addObject("permissionSet", permissionSet);
            return modelView;
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "OrganizationController Controller :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
    }
    /*
     @parm  userKey,statusKey
     @return int
     @throws AppException
     */

    @RequestMapping(value = "/changeuserstatus")
    public ModelAndView changUserStatus(HttpServletRequest request,
            HttpServletResponse response, @RequestParam("upKey") String userKey, @RequestParam("sKey") String statusKey,
            final RedirectAttributes redirectAttributes, Model model) {
        ModelAndView modelView = new ModelAndView();
        HttpSession session = request.getSession();
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return new ModelAndView("redirect:/logout.htm");
            }
            
            UserProfileDTO userDto = new UserProfileDTO();
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user",userDto.getEmail());
                MDC.put("ip",userDto.getUserMACAddress());
//                log.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User accessed changUserStatus page"));
                
		log.trace(TRACE_USER_CHANGE_USER_STATUS);
            }else{
                return new ModelAndView("/login");
            }
            if (!StringUtils.isEmpty(userKey) && !StringUtils.isEmpty(statusKey)) {
                int retVal = useractivationservice.updateUserStatus(userKey, statusKey, userDto.getUserProfileKey());
                if (retVal == 2) {
                    redirectAttributes.addFlashAttribute("successMessage", myProperties.getProperty("useractivation-statusmessage-unlock"));
                } else if (retVal == 3) {
                    redirectAttributes.addFlashAttribute("successMessage", myProperties.getProperty("useractivation-statusmessage-Active"));
                } else if (retVal <= 0) {
                    log.debug("Exception Occured while Executing the "
                            + "changUserStatus Controller :: ");
                    ExceptionDTO exception = new ExceptionDTO("ERR:401", "Unable to Load the Application");
                    return new ModelAndView("/exception", "exceptionBean", exception);
                } else {

                }
            } else {
                log.debug("Exception Occured while Executing the "
                        + "changUserStatus Controller :: ");
                ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"));
                return new ModelAndView("/exception", "exceptionBean", exception);
            }

            modelView.setViewName("redirect:/userworklist.htm");
            return modelView;
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "changUserStatus Controller :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
    }
     }
