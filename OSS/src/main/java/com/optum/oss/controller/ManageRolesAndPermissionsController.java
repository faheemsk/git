/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.controller;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.constants.PermissionConstants;
import com.optum.oss.constants.SessionConstants;
import com.optum.oss.dto.ExceptionDTO;
import com.optum.oss.dto.PermissionGroupDTO;
import com.optum.oss.dto.RoleDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.PermissionCheckHelper;
import com.optum.oss.service.ManageRolesAndPermissionsService;
import com.optum.oss.util.CommonUtil;
import com.optum.oss.util.EncryptDecrypt;
import com.optum.oss.util.LoggerUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
 * @author akeshavulu
 */
@Controller
public class ManageRolesAndPermissionsController {

    private static final Logger logger = Logger.getLogger(ManageRolesAndPermissionsController.class);

    @Resource(name = "myProperties")
    private Properties myProperties;

    /*
     Start Autowiring the required Class instances
     */
    @Autowired
    private ManageRolesAndPermissionsService manageRolesAndPermissionsService;

    @Autowired
    private EncryptDecrypt encDec;
    @Autowired
    PermissionCheckHelper permissionCheckHelper;
    @Autowired
    private LoggerUtil auditLogger;
    @Autowired
    private CommonUtil commonUtil;
    /*
     START : LOG MESSAGES
     */
    private final String TRACE_USER_SAVE_ROLE_DETAILS = "User accessed saveroledetails";
    private final String TRACE_USER_DELETE_APP_ROLE = "User accessed deleteAppRole";
    private final String TRACE_USER_MANAGE_ROLE_PAGE = "User accessed manageRolepage";
    private final String TRACE_USER_ADD_ROLE = "User accessed addRolepage";
    private final String TRACE_USER_FETCH_EDIT_ROLE_DETAILS = "User accessed fetchEditAppRoleDetails";
    private final String TRACE_USER_UPDATE_ROLE_PERMISSION_GROUP = "User accessed updateRolePrmnGrpDetails";
    private final String TRACE_USER_VAL_APP_ROLE = "User accessed validateapprolename";
    private final String TRACE_USER_VIEW_APP_ROLE_DETAILS = "User accessed viewAppRoleDetails";
    private final String INFO_USER_VAL_APP_ROLE = "User accessesed valnarability app role";

    /*
     END: LOG MESSAGES
     /*
     End Autowiring the required Class instances
     */
    
    @InitBinder("addrole")
    protected void saveRoleDetailsBinder(WebDataBinder binder)
            throws Exception {
        binder.setAllowedFields(new String[]{"encAppRoleKey",
            "appRoleName",
            "appRoleDescription",
            "rowStatusValue",
            "appRoleComments",
            "permissionGroupIDs"});

    }
    
    /**
     * @param request
     * @param response
     * @param redirectAttributes
     * @param model
     * @param roleDTO
     * @param bindResult
     * @return ModelAndView
     */
    @RequestMapping(value = "/saveroledetails", method = RequestMethod.POST)
    public String saveRoleDetails(HttpServletRequest request, HttpServletResponse response,
            RedirectAttributes redirectAttributes, Model model,
            @ModelAttribute("addrole") @Valid RoleDTO roleDTO,
            BindingResult bindResult) {
        ModelAndView modelAndView = new ModelAndView();
        Set<String> permissionSet = new HashSet<String>();
        HttpSession session = request.getSession();
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            
            if (bindResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("addrole", roleDTO);
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addrole", bindResult);
                return "redirect:/addRolepage.htm";
            }
            
            UserProfileDTO userDto = new UserProfileDTO();
            
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User accessed saveRoleDetails page"));
                logger.trace(TRACE_USER_SAVE_ROLE_DETAILS);
                permissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_ROLES_SUB_MENU);
            }

            roleDTO.setCreatedByUserID(userDto.getUserProfileKey());
            int successValue = manageRolesAndPermissionsService.saveAppRolePermissionGrpDetails(roleDTO);
            if (successValue > 0) {
                redirectAttributes.addFlashAttribute("successMessage", myProperties.getProperty("roleadd.success-message"));
                redirectAttributes.addFlashAttribute("appRoleName", roleDTO.getAppRoleName());
                redirectAttributes.addFlashAttribute("saveFlag", ApplicationConstants.SAVE_FLAG);
            }
            //modelAndView.setViewName("/usermgmt/ManageRoles");
            return "redirect:/manageRolepage.htm";
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "saveroledetails Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            modelAndView.addObject("exceptionBean", exception);

            return "/exception";
        }
        //return modelAndView;

    }

    /**
     * @param request
     * @param response
     * @param redirectAttributes
     * @return ModelAndView
     */
    @RequestMapping(value = "/deleteAppRole")
    public ModelAndView DeleteRoleDetails(HttpServletRequest request, HttpServletResponse response,
            RedirectAttributes redirectAttributes, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        RoleDTO mgmtRolePerDTO = new RoleDTO();
        HttpSession session = request.getSession();
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return new ModelAndView("redirect:/logout.htm");
            }
            
            if (!StringUtils.isEmpty(request.getParameter("ed"))) {
                long decryptRoleKey = 0;
                String encRoleKey = request.getParameter("ed");
                if (encRoleKey != null && !encRoleKey.equalsIgnoreCase("")) {
                    decryptRoleKey = Long.parseLong(encDec.decrypt(encRoleKey));
                }

                UserProfileDTO userDto = new UserProfileDTO();

                if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                    userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                    MDC.put("user", userDto.getEmail());
                    MDC.put("ip", userDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User accessed DeleteRoleDetails page"));
                    logger.trace(TRACE_USER_DELETE_APP_ROLE);
                }
                mgmtRolePerDTO.setAppRoleKey(decryptRoleKey);
                int successValue = manageRolesAndPermissionsService.deleteRoleDetails(mgmtRolePerDTO);

                RoleDTO roleDTO = manageRolesAndPermissionsService.fetchAppRoleDetails(encRoleKey);

                redirectAttributes.addFlashAttribute("successMessage", myProperties.getProperty("roledelete.success-message"));
                redirectAttributes.addFlashAttribute("appRoleName", roleDTO.getAppRoleName());
                redirectAttributes.addFlashAttribute("saveFlag", ApplicationConstants.DELETE_FLAG);

                modelAndView.setViewName("redirect:/manageRolepage.htm");
            } else {
                modelAndView.setViewName("redirect:/logout.htm");
            }
            
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "deleteAppRole Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        return modelAndView;

    }

    /**
     * @param request
     * @param response
     * @param model
     * @param redirectAttributes
     * @return ModelAndView
     */
    @RequestMapping(value = "/manageRolepage")
    public ModelAndView manageRolePage(HttpServletRequest request, HttpServletResponse response,
            Model model, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        RoleDTO mgmtRolePerDTO = new RoleDTO();
        HttpSession session = request.getSession();
        Set<String> permissionSet = new HashSet<String>();
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return new ModelAndView("redirect:/logout.htm");
            }
            
            UserProfileDTO userDto = new UserProfileDTO();

            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User accessed validate verify user page"));
                logger.trace(TRACE_USER_MANAGE_ROLE_PAGE);
                permissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_ROLES_SUB_MENU);
            }

            userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);

            if (permissionCheckHelper.checkUserSubMenu(userDto, PermissionConstants.MANAGE_ROLES_SUB_MENU)) {
                List<RoleDTO> manageRolesPermissionsList = new ArrayList<RoleDTO>();
                manageRolesPermissionsList = manageRolesAndPermissionsService.getManageRolesAndPermissionsWorkList(mgmtRolePerDTO);
                modelAndView.addObject("manageRolePermissionWorkList", manageRolesPermissionsList);
                modelAndView.addObject("permissionSet", permissionSet);
                if (model.containsAttribute("successMessage")) {
                    modelAndView.addObject("successMessage", model.asMap().get("successMessage"));
                    modelAndView.addObject("appRoleName", model.asMap().get("appRoleName"));
                    modelAndView.addObject("saveFlag", model.asMap().get("saveFlag"));
                }
                modelAndView.setViewName("/usermgmt/ManageRoles");
            } else {
                modelAndView.setViewName("redirect:/logout.htm");
            }

        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "manageRolepage Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        return modelAndView;

    }

    /**
     * @param request
     * @param response
     * @param model
     * @param redirectAttributes
     * @return ModelAndView
     */
    @RequestMapping(value = "/addRolepage")
    public String addRolePage(HttpServletRequest request, HttpServletResponse response,
            Model model, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        UserProfileDTO userDto = new UserProfileDTO();
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
                       
            List<PermissionGroupDTO> permissionGroupList = new ArrayList<PermissionGroupDTO>();
            permissionGroupList = manageRolesAndPermissionsService.getRolesPermissionGroup();
            model.addAttribute("permissionGroupList", permissionGroupList);
            userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
            MDC.put("user", userDto.getEmail());
            MDC.put("ip", userDto.getUserMACAddress());
//            logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User accessed addRole Page"));
            logger.trace(TRACE_USER_ADD_ROLE);

            if (permissionCheckHelper.checkUserPermission(userDto, PermissionConstants.MANAGE_ROLES_SUB_MENU, PermissionConstants.ADD_ROLE)) {

                if (!model.containsAttribute("addrole")) {
                    model.addAttribute("roleDTO", new RoleDTO());
                    RoleDTO objRoleDTO = new RoleDTO();
                    //objRoleDTO.setAppRoleComments("NA");
                    model.addAttribute("addrole", objRoleDTO);
                } else {
                    Map<String, Object> modelMap = model.asMap();
                    RoleDTO bindRoleDTO = (RoleDTO) (modelMap.get("addrole"));
                    model.addAttribute("permissionGroupIDs", bindRoleDTO.getPermissionGroupIDs());
                    model.addAttribute("roleDTO", new RoleDTO());
                }

                return "/usermgmt/AddRole";
            } else {
                return "redirect:/logout.htm";
            }
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "addRolepage Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        // return modelAndView;

    }

    /**
     * @param request
     * @param response
     * @param model
     * @return ModelAndView
     *
     */
    @RequestMapping(value = "/fetchEditAppRoleDetails")
    public String editAppRoleDetails(final HttpServletRequest request,
            final HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
        List<PermissionGroupDTO> editPrmnGrpList = new ArrayList<PermissionGroupDTO>();
        List<PermissionGroupDTO> allPermissionGroupList = new ArrayList<PermissionGroupDTO>();
        HttpSession session = request.getSession();
        UserProfileDTO userDto = new UserProfileDTO();
        Set<String> permissionSet = new HashSet<String>();
        String retString = "";
        
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            
            if (!StringUtils.isEmpty(request.getParameter("ed"))) {
                String encRoleKey = request.getParameter("ed");

                if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                    userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                    MDC.put("user", userDto.getEmail());
                    MDC.put("ip", userDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User accessed editAppRoleDetails page"));
                    logger.trace(TRACE_USER_FETCH_EDIT_ROLE_DETAILS);
                    permissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_ROLES_SUB_MENU);
                }
                if (permissionCheckHelper.checkUserPermission(userDto,
                        PermissionConstants.MANAGE_ROLES_SUB_MENU, PermissionConstants.UPDATE_ROLE)) {

                    allPermissionGroupList = manageRolesAndPermissionsService.getRolesPermissionGroup();

                    RoleDTO roleDTO = manageRolesAndPermissionsService.fetchAppRoleDetails(encRoleKey);
                    editPrmnGrpList = manageRolesAndPermissionsService.fetchRolePermissionsGrpDetails(roleDTO);
                    if (roleDTO.getRowStatusKey() == ApplicationConstants.DB_ROW_STATUS_ACTIVE_VALUE) {
                        roleDTO.setRowStatusValue("active");
                    } else if (roleDTO.getRowStatusKey() == ApplicationConstants.DB_ROW_STATUS_DE_ACTIVE_VALUE) {
                        roleDTO.setRowStatusValue("deactive");
                    }

                    if (model.containsAttribute("addrole")) {
                        Map<String, Object> modelMap = model.asMap();
                        RoleDTO bindRoleDTO = (RoleDTO) (modelMap.get("addrole"));
                        model.addAttribute("addrole", bindRoleDTO);
                        model.addAttribute("roleDTO", roleDTO);
                        model.addAttribute("permissionGroupIDs", bindRoleDTO.getPermissionGroupIDs());
                    } else {
                        //roleDTO.setAppRoleComments("NA");
                        model.addAttribute("addrole", roleDTO);
                        model.addAttribute("roleDTO", roleDTO);
                        model.addAttribute("editPrmnGrpList", editPrmnGrpList);
                    }

                    model.addAttribute("allPermissionGroupList", allPermissionGroupList);
                    model.addAttribute("permissionSet", permissionSet);

                    retString = "/usermgmt/AddRole";
                } else {
                    retString = "redirect:/logout.htm";
                }
            } else {
                retString = "redirect:/logout.htm";
            }
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "fetchEditAppRoleDetails Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            retString = "/exception";
        }
        return retString;

    }

    /**
     * @param request
     * @param response
     * @param redirectAttributes
     * @param roleDTO
     * @param bindResult
     * @return ModelAndView
     */
    @RequestMapping(value = "/updateRolePrmnGrpDetails", method = RequestMethod.POST)
    public String updateRolePrmnGrpDetails(HttpServletRequest request, HttpServletResponse response,
            RedirectAttributes redirectAttributes, Model model,
            @ModelAttribute("addrole") @Valid RoleDTO roleDTO,
            BindingResult bindResult) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        UserProfileDTO userDto = new UserProfileDTO();
        String retString = "";
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            
            if (bindResult.hasErrors()) {
                redirectAttributes.addAttribute("ed", roleDTO.getEncAppRoleKey());
                redirectAttributes.addFlashAttribute("addrole", roleDTO);
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addrole", bindResult);
                return "redirect:/fetchEditAppRoleDetails.htm";
            }

            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User accessed updateRolePrmnGrpDetails page"));
                logger.trace(TRACE_USER_UPDATE_ROLE_PERMISSION_GROUP);
            }
            roleDTO.setCreatedByUserID(userDto.getUserProfileKey());
            int successValue = manageRolesAndPermissionsService.updateAppRolePermissionGrpDetails(roleDTO);
            if (successValue > 0) {
                redirectAttributes.addFlashAttribute("successMessage", myProperties.getProperty("roleupdate.success-message"));
                redirectAttributes.addFlashAttribute("appRoleName", roleDTO.getAppRoleName());
                redirectAttributes.addFlashAttribute("updateFlag", ApplicationConstants.UPDATE_FLAG);
            }

            retString = "redirect:/manageRolepage.htm";

        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "updateRolePrmnGrpDetails Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            modelAndView.addObject("exceptionBean", exception);
            retString = "/exception";
        }
        return retString;

    }

    @RequestMapping(value = "/validateapprolename")
    public @ResponseBody
    String validateAppRoleName(final HttpServletRequest request, @RequestParam("appRoleName") String appRoleName) {

        logger.info(INFO_USER_VAL_APP_ROLE);
//        logger.info("Start: validateAppRoleName() ");
        String message = "";
        try {
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                UserProfileDTO userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User accessed validateAppRoleName page"));
                logger.trace(TRACE_USER_VAL_APP_ROLE);
            }
            int appRoleNameCheck = manageRolesAndPermissionsService.fetchAppRoleByName(appRoleName);
            if (appRoleNameCheck > 0) {
                message = myProperties.getProperty("approle.name.check");
            } else {
                message = "No";
            }

        } catch (AppException e) {
            logger.error("Exceptionoccured : validateAppRoleName: " + e.getMessage());
        }
        return message;
    }

    /**
     * @param request
     * @param response
     * @param redirectAttributes
     * @param model
     * @return ModelAndView
     *
     */
    @RequestMapping(value = "/viewAppRoleDetails")
    public ModelAndView viewAppRolePrmnDetails(HttpServletRequest request, HttpServletResponse response,
            RedirectAttributes redirectAttributes, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        List<PermissionGroupDTO> editPrmnGrpList = new ArrayList<PermissionGroupDTO>();
        List<PermissionGroupDTO> allPermissionGroupList = new ArrayList<PermissionGroupDTO>();
        HttpSession session = request.getSession();
        UserProfileDTO userDto = new UserProfileDTO();
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return new ModelAndView("redirect:/logout.htm");
            }
            
            if (!StringUtils.isEmpty(request.getParameter("ed"))) {
                String encRoleKey = request.getParameter("ed");
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
//            logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User accessed viewAppRolePrmnDetails page"));
                logger.trace(TRACE_USER_VIEW_APP_ROLE_DETAILS);
                if (permissionCheckHelper.checkUserPermission(userDto,
                        PermissionConstants.MANAGE_ROLES_SUB_MENU, PermissionConstants.VIEW_ROLE)) {
                    allPermissionGroupList = manageRolesAndPermissionsService.getRolesPermissionGroup();

                    RoleDTO roleDTO = manageRolesAndPermissionsService.fetchAppRoleDetails(encRoleKey);
                    if (roleDTO.getRowStatusKey() == ApplicationConstants.DB_ROW_STATUS_ACTIVE_VALUE) {
                        roleDTO.setRowStatusValue("active");
                    } else if (roleDTO.getRowStatusKey() == ApplicationConstants.DB_ROW_STATUS_DE_ACTIVE_VALUE) {
                        roleDTO.setRowStatusValue("deactive");
                    }
                    editPrmnGrpList = manageRolesAndPermissionsService.fetchRolePermissionsGrpDetails(roleDTO);
                    modelAndView.addObject("roleDTO", roleDTO);
                    modelAndView.addObject("editPrmnGrpList", editPrmnGrpList);
                    modelAndView.addObject("allPermissionGroupList", allPermissionGroupList);
                    modelAndView.setViewName("/usermgmt/ViewRole");
                } else {
                    modelAndView.setViewName("redirect:/logout.htm");
                }

            } else {
                modelAndView.setViewName("redirect:/logout.htm");
            }
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "viewAppRolePrmnDetails Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        return modelAndView;
    }

}
