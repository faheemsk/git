/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.controller;

import com.optum.oss.constants.PermissionConstants;
import com.optum.oss.constants.SessionConstants;
import com.optum.oss.dto.ExceptionDTO;
import com.optum.oss.dto.PermissionDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.PermissionCheckHelper;
import com.optum.oss.service.impl.DetailedPermissionsServiceImpl;
import com.optum.oss.util.CommonUtil;
import com.optum.oss.util.LoggerUtil;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author bpilla
 */
@Controller
public class DetailedPermissionsController {

    private static final Logger logger = Logger.getLogger(DetailedPermissionsController.class);

    @Resource(name = "myProperties")
    private Properties myProperties;

    @Autowired
    private DetailedPermissionsServiceImpl detailedPermissionsService;

    @Autowired
    PermissionCheckHelper permissionCheckHelper;

    @Autowired
    private LoggerUtil auditLogger;
    @Autowired
    private CommonUtil commonUtil;
    /**
     * This method for fetching View Detailed Permissions work list
     *
     * @param request
     * @param response
     * @return ModelAndView
     */
    /*
     START : LOG MESSAGES
     */
    private final String TRACE_USER_VIEW_ALL_PERMISSION = "User accessed viewpermission";
    private final String TRACE_USER_SAVE_DEFINITION = "User accessed savedefinition";
//    private final String INFO_START_AnalystValidationController="addvulnerabilityview";
    /*
     END: LOG MESSAGES
     */

    @RequestMapping(value = "/viewpermission")
    public ModelAndView ViewAllPermissions(final HttpServletRequest request,
            final HttpServletResponse response, Model model,
            RedirectAttributes redirectAttributes) {
        List<PermissionDTO> masterLookUpList = null;
        ModelAndView modelView = new ModelAndView();
        HttpSession session = request.getSession();
        UserProfileDTO userDto = new UserProfileDTO();
        long groupID = 0;

        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return new ModelAndView("redirect:/logout.htm");
            }
            
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User accessed viewpermission page"));
                logger.trace(TRACE_USER_VIEW_ALL_PERMISSION);
                if (permissionCheckHelper.checkUserSubMenu(userDto, PermissionConstants.VIEW_DETAILED_PERMISSIONS_SUB_MENU)) {
                    Set<String> permissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.VIEW_DETAILED_PERMISSIONS_SUB_MENU);
                    modelView.addObject("permissionSet", permissionSet);
                    masterLookUpList = detailedPermissionsService.fetchViewDetailedPermissions(groupID);
                    modelView.addObject("masterLookUpList", masterLookUpList);
                    modelView.setViewName("usermgmt/viewpermissions");
                } else {
                    modelView.setViewName("redirect:/logout.htm");
                }
            } else {
                modelView.setViewName("redirect:/logout.htm");
            }

        } catch (AppException ex) {
            logger.debug("Exception Occured while Executing the "
                    + "LoginPage Action :: " + ex.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), ex);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }

        return modelView;
    }

    @InitBinder("permissionDto")
    protected void saveDefinitionBinder(WebDataBinder binder) 
            throws Exception
    {
        binder.setAllowedFields(new String[]
                            {"encPermissionKey",
                             "permissionDescription" });
    }
    /**
     * This method to saveDefinition
     *
     * @param permissionDto
     * @param result
     * @param requestAttr
     * @param request
     * @param model
     * @return ModelAndView
     */
    @RequestMapping(value = "/savedefinition")
    public String saveDefinition(
            @ModelAttribute("permissionDto") @Valid PermissionDTO permissionDto, BindingResult result,
            RedirectAttributes requestAttr, HttpServletRequest request, Model model) throws AppException {
        ModelAndView modelAndView = new ModelAndView();
        if (!commonUtil.refreshCheck(request, requestAttr, model)) {
            return "redirect:/logout.htm";
        }
            
        if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
            UserProfileDTO userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
            MDC.put("user", userDto.getEmail());
            MDC.put("ip", userDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User saving definition of permission in page"));
            logger.trace(TRACE_USER_SAVE_DEFINITION);
        }

        if (result.hasErrors()) {
            requestAttr.addFlashAttribute("valError", result.getFieldError().getDefaultMessage());
            return "redirect:viewpermission.htm";
        } else {
            int retval = detailedPermissionsService.saveDefinition(permissionDto.getPermissionDescription(), permissionDto.getEncPermissionKey());
            if (retval > 0) {
                requestAttr.addFlashAttribute("successMsg", "Definition updated successfully");
                modelAndView.setViewName("redirect:viewpermission.htm");
            }
        }
        return "redirect:viewpermission.htm";
    }
}
