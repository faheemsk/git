/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.controller;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.constants.PermissionConstants;
import com.optum.oss.constants.SessionConstants;
import com.optum.oss.dao.impl.ManagePermissionGroupDAOImpl;
import com.optum.oss.dto.ExceptionDTO;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.PermissionDTO;
import com.optum.oss.dto.PermissionGroupDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.PermissionCheckHelper;
import com.optum.oss.service.impl.ManagePermissionGroupServiceImpl;
import com.optum.oss.service.impl.MasterLookUpServiceImpl;
import com.optum.oss.util.CommonUtil;
import com.optum.oss.util.EncryptDecrypt;
import com.optum.oss.util.LoggerUtil;
import com.optum.oss.validators.ManagePermissionGroupValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author sathuluri
 */
@Controller
public class ManagePermissionGroupController {

    private static final Logger logger = Logger.getLogger(ManagePermissionGroupDAOImpl.class);

    @Resource(name = "myProperties")
    private Properties myProperties;

    @Autowired
    private ManagePermissionGroupServiceImpl managePermissionGroupService;

    @Autowired
    private MasterLookUpServiceImpl masterLookUpService;

    @Autowired
    private EncryptDecrypt encDec;

    @Autowired
    PermissionCheckHelper permissionCheckHelper;
    @Autowired
    private LoggerUtil auditLogger;

    @Autowired
    private ManagePermissionGroupValidator managePermissionGroupValidator;
    @Autowired
    private CommonUtil commonUtil;
    /*
     START : LOG MESSAGES
     */
    private final String TRACE_USER_PERMISSION_GROUP_WORKLIST = "User accessed permissiongroupworklist";
    private final String TRACE_USER_ADDORUPDATE_PERMISSION_GROUP = "User accessed addorupdate permission group page";
    private final String TRACE_USER_SAVE_UPDATE_PERMISSION_GROUP = "User accessed ssaveorupdate permission group";
    private final String TRACE_USER_DELETE_PERMITION_GROUP = "User accessed deletepermissiongroup";
    private final String TRACE_USER_VALIDATE_PERMISSION_GROUP = "User accessed validatepermissiongroupname";
    private final String TRACE_USER_VIEW_PERMITION_GROUP = "User accessed deletepermissiongroup";
    private final String INFO_USER_PERMISSION_GROUP_WORKLIST = "User accessed permissiongroupworklist";
    private final String INFO_USER_ADDORUPDATE_PERMISSION_GROUP = "User accessed add or update permissiongroup page";
    private final String INFO_USER_SAVE_UPDATE_PERMISSION_GROUP = "User accessed save or update permission group";
    private final String INFO_USER_DELETE_PERMISSION_GROUP = "User accessesed delete permission group";
    private final String INFO_USER_VALIDATE_PERMISSION_GROUP = "User accessesed validated permission group";
    private final String INFO_USER_VIEW_PERMISSION_GROUP = "User accessesed view permission group";

    /*
     END: LOG MESSAGES
    
     /**
     * This method for fetching Manage Permission Group work list
     *
     * @param request
     * @param response
     * @return ModelAndView
     */
    @RequestMapping(value = "/permissiongroupworklist")
    public ModelAndView fetchPermissionGroupWorkList(final HttpServletRequest request,
            final HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) {

        logger.info(INFO_USER_PERMISSION_GROUP_WORKLIST);
//        logger.info("Start: fetchPermissionGroupWorkList() to fetch permission groups worklist");
        ModelAndView modelView = new ModelAndView();
        HttpSession session = request.getSession();
        UserProfileDTO userDto = new UserProfileDTO();
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return new ModelAndView("redirect:/logout.htm");
            }
            
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User accessed fetchPermissionGroupWorkList page"));
                logger.trace(TRACE_USER_PERMISSION_GROUP_WORKLIST);
                Set<String> permissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_PERMISSION_GROUP_SUB_MENU);
                modelView.addObject("permissionSet", permissionSet);
            }
            if (permissionCheckHelper.checkUserSubMenu(userDto, PermissionConstants.MANAGE_PERMISSION_GROUP_SUB_MENU)) {
                List<PermissionGroupDTO> permissionGroupWorkList = managePermissionGroupService.fetchPermissionGroupWorkList();
                modelView.addObject("permissionGroupWorkList", permissionGroupWorkList);
                modelView.setViewName("usermgmt/permissiongroupworklist");
            } else {
                modelView.setViewName("redirect:/logout.htm");
            }

        } catch (AppException e) {
            logger.error("Exception occurred : fetchPermissionGroupWorkList:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        logger.info("Start: fetchPermissionGroupWorkList() to fetch permission groups worklist");
        return modelView;
    }

    /**
     * This method to load add or update permission group page
     *
     * @param request
     * @param model
     * @param redirectAttributes
     * @return ModelAndView
     */
    @RequestMapping(value = "/addorupdatepermissiongrouppage")
    public String addOrUpdatePermissionGroupPage(final HttpServletRequest request, Model model,
            RedirectAttributes redirectAttributes) {

        logger.info(INFO_USER_ADDORUPDATE_PERMISSION_GROUP);
//        logger.info("Start: addOrUpdatePermissionGroupPage() to load add or update permission gorup page");
        PermissionGroupDTO permissionGroupDTO = new PermissionGroupDTO();
        UserProfileDTO userDto = null;
        long decGroupKey = 0;
        String retString = "";
        String[] permissionIDs = {};
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            
            if (!StringUtils.isEmpty(request.getParameter("encGroupKey"))) {
                    //String encGroupKey = request.getParameter("encGroupKey");
                    if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                        userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                        MDC.put("user", userDto.getEmail());
                        MDC.put("ip", userDto.getUserMACAddress());
//               logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User accessed addOrUpdatePermissionGroupPage page"));
                        logger.trace(TRACE_USER_ADDORUPDATE_PERMISSION_GROUP);
                    }

                    if ((request.getParameter("encGroupKey") != null && !request.getParameter("encGroupKey").equalsIgnoreCase("")) &&
                            !request.getParameter("encGroupKey").equalsIgnoreCase("123")) { //request.getParameter("encGroupKey").equalsIgnoreCase("123") condition for load add permission group page
                        decGroupKey = Long.parseLong(encDec.decrypt(request.getParameter("encGroupKey")));
                        permissionGroupDTO.setPermissionGroupKey(decGroupKey);
                    }

                    if (permissionCheckHelper.checkUserPermission(userDto, PermissionConstants.MANAGE_PERMISSION_GROUP_SUB_MENU,
                            PermissionConstants.ADD_PERMISSION_GROUP)
                            || permissionCheckHelper.checkUserPermission(userDto, PermissionConstants.MANAGE_PERMISSION_GROUP_SUB_MENU,
                                    PermissionConstants.UPDATE_PERMISSION_GROUP)) {

                        if (0 < decGroupKey && !model.containsAttribute("addeditpermissiongroup")) {
                            permissionGroupDTO = managePermissionGroupService.fetchPermissionGroupById(decGroupKey);
                        }
                        List<PermissionDTO> permissionsList = managePermissionGroupService.fetchPermissionsForPermissionGroup(permissionGroupDTO);
                        permissionGroupDTO = managePermissionGroupService.filterDetailedPermissionsForPermissionGroup(permissionGroupDTO, permissionsList);
                        List<MasterLookUpDTO> lookUpList = masterLookUpService.fetchMasterLookUpEntitiesByEntityType(ApplicationConstants.DB_CONSTANT_ROW_STATUS);

                        if (model.containsAttribute("permissionGroupDTO")) {
                            Map<String, Object> modelMap = model.asMap();
                            PermissionGroupDTO permGroupDto = (PermissionGroupDTO) (modelMap.get("permissionGroupDTO"));
                            if (decGroupKey > 0) {
                                permGroupDto.setPermissionGroupKey(decGroupKey);
                            }
                            if (null != permGroupDto.getPermissionGroupKeyChkIds()) {
                                permissionIDs = managePermissionGroupService.convertingStringToArray(permGroupDto.getPermissionGroupKeyChkIds());
                            }
                            model.addAttribute("permissionIDs", permissionIDs);
                            model.addAttribute("permissionGroupDTO", permGroupDto);
                        } else {
                            model.addAttribute("permissionGroupDTO", permissionGroupDTO);
                        }

                        model.addAttribute("lookUpList", lookUpList);
                        model.addAttribute("allPermissions", permissionGroupDTO.getPermissionListObj());
                        retString = "/usermgmt/addpermissiongroup";
                    } else {
                        logger.error("Exception occurred : addOrUpdatePermissionGroupPage:Request Param Tampered.");
                        ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"));
                        //return new ModelAndView("/exception", "exceptionBean", exception);
                        model.addAttribute("exceptionBean", exception);
                        retString = "/exception";
                    }
            } else {
                retString = "redirect:/logout.htm";
            }
            
//            } else {
//                retString = "redirect:/logout.htm";
//            }
        } catch (AppException e) {
            logger.error("Exception occurred : addOrUpdatePermissionGroupPage:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            //return new ModelAndView("/exception", "exceptionBean", exception);
            model.addAttribute("exceptionBean", exception);
            retString = "/exception";
        }
        logger.info("Start: addOrUpdatePermissionGroupPage() to load add or update permission gorup page");
        return retString;
    }

    @InitBinder("permissionGroupDTO")
    protected void saveOrUpdatePermissionGroupBinder(WebDataBinder binder)
            throws Exception {
        binder.setAllowedFields(new String[]{"encPermissionGroupKey",
            "permissionGroupName",
            "encPermissionGroupName",
            "permissionGroupDesc",
            "permissionGroupStatusValue",
            "reason",
            "permissionGroupKeyChkIds"});

    }

    /**
     * This method for save or update permission group
     *
     * @param request
     * @param redirectAttributes
     * @param permissionGroupDTO
     * @param bindResult
     * @return ModelAndView
     */
    @RequestMapping(value = "/saveorupdatepermissiongroup", method = RequestMethod.POST)
    public String saveOrUpdatePermissionGroup(final HttpServletRequest request,
            final RedirectAttributes redirectAttributes, @ModelAttribute(value = "permissionGroupDTO") PermissionGroupDTO permissionGroupDTO,
            BindingResult bindResult, Model model) {

        logger.info(INFO_USER_SAVE_UPDATE_PERMISSION_GROUP);
//        logger.info("Start: saveOrUpdatePermissionGroup() to save permission gorup");
        List<PermissionDTO> oldPermissionsList = new ArrayList<>();
        ModelAndView modelAndView = new ModelAndView();
        long groupKey = 0;
        long decPermissionGroup = 0;
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            
            /* Validation for permission group Details */
            managePermissionGroupValidator.validate(permissionGroupDTO, bindResult);
            if (bindResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("permissionGroupDTO", permissionGroupDTO);
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.permissionGroupDTO", bindResult);
                redirectAttributes.addAttribute("encGroupKey", permissionGroupDTO.getEncPermissionGroupKey());
                return "redirect:/addorupdatepermissiongrouppage.htm";
            }
            if (!StringUtils.isEmpty(permissionGroupDTO.getEncPermissionGroupKey())) {
                decPermissionGroup = Long.parseLong(encDec.decrypt(permissionGroupDTO.getEncPermissionGroupKey()));
                permissionGroupDTO.setPermissionGroupKey(decPermissionGroup);
                groupKey = decPermissionGroup;
            }

            UserProfileDTO userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
            MDC.put("user", userDto.getEmail());
            MDC.put("ip", userDto.getUserMACAddress());
//            logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User accessed saveOrUpdatePermissionGroup page"));
            logger.trace(TRACE_USER_SAVE_UPDATE_PERMISSION_GROUP);
            if (0 < permissionGroupDTO.getPermissionGroupKey()) {
                oldPermissionsList = managePermissionGroupService.fetchPermissionsForPermissionGroup(permissionGroupDTO);
            }
            long returnValue = managePermissionGroupService.saveOrUpdatePermissionGroup(permissionGroupDTO, oldPermissionsList, userDto,
                    permissionGroupDTO.getPermissionGroupKeyChkIds());
            if (0 < returnValue && groupKey <= 0) {
                redirectAttributes.addFlashAttribute("successMessage", myProperties.getProperty("permissiongroup.add.success-message"));
                redirectAttributes.addFlashAttribute("sessionPermissionGroupName", permissionGroupDTO.getPermissionGroupName());
                redirectAttributes.addFlashAttribute("saveFlag", ApplicationConstants.SAVE_FLAG);
            } else if (0 < returnValue && 0 < permissionGroupDTO.getPermissionGroupKey()) {
                redirectAttributes.addFlashAttribute("successMessage", myProperties.getProperty("permissiongroup.update.success-message"));
                redirectAttributes.addFlashAttribute("updateFlag", ApplicationConstants.UPDATE_FLAG);
                redirectAttributes.addFlashAttribute("sessionPermissionGroupName", permissionGroupDTO.getPermissionGroupName());
            }

        } catch (AppException e) {
            logger.error("Exception occurred : saveOrUpdatePermissionGroup:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            modelAndView.addObject("exceptionBean", exception);
            return "/exception";
        }
        logger.info("Start: saveOrUpdatePermissionGroup() to save permission gorup");
        return "redirect:permissiongroupworklist.htm";
    }

    @InitBinder("delPermissionGroupObj")
    protected void deletePermissionGroupBinder(WebDataBinder binder)
            throws Exception {
        binder.setAllowedFields(new String[]{"encPermissionGroupKey",
            "permissionGroupName"});
    }

    /**
     * This method for delete permission group
     *
     * @param request
     * @param redirectAttributes
     * @param permissionGroupDTO
     * @param model
     * @return ModelAndView
     */
    @RequestMapping(value = "/deletepermissiongroup", method = RequestMethod.POST)
    public ModelAndView deletePermissionGroup(final HttpServletRequest request, final RedirectAttributes redirectAttributes,
            @ModelAttribute(value = "delPermissionGroupObj") PermissionGroupDTO permissionGroupDTO, Model model) {

        logger.info(INFO_USER_DELETE_PERMISSION_GROUP);
//        logger.info("Start: deletePermissionGroup() to delete permission group");
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return new ModelAndView("redirect:/logout.htm");
            }
            
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                UserProfileDTO userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User deletePermissionGroup page"));
                logger.trace(TRACE_USER_DELETE_PERMITION_GROUP);
            }
            long decGroupKey = 0;
            if (permissionGroupDTO.getEncPermissionGroupKey() != null && !permissionGroupDTO.getEncPermissionGroupKey().equalsIgnoreCase("")) {
                decGroupKey = Long.parseLong(encDec.decrypt(permissionGroupDTO.getEncPermissionGroupKey()));
                permissionGroupDTO.setPermissionGroupKey(decGroupKey);
            }
            long returnValue = managePermissionGroupService.deletePermissionGroup(permissionGroupDTO);
            if (0 < returnValue) {
                redirectAttributes.addFlashAttribute("successMessage", myProperties.getProperty("permissiongroup.delete.success-message"));
                redirectAttributes.addFlashAttribute("deleteFlag", ApplicationConstants.DELETE_FLAG);
                redirectAttributes.addFlashAttribute("sessionPermissionGroupName", permissionGroupDTO.getPermissionGroupName());
            }
            logger.info("Start: deletePermissionGroup() to delete permission group");
        } catch (AppException e) {
            logger.error("Exceptionoccured : deletePermissionGroup: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        return new ModelAndView("redirect:permissiongroupworklist.htm");
    }

    /**
     * This method for validate permission group name to avoid duplicate
     * permission group name
     *
     * @param request
     * @param redirectAttributes
     * @param permsnGrpNm
     * @return ModelAndView
     */
    @RequestMapping(value = "/validatepermissiongroupname")
    public @ResponseBody
    String validatePemissionGroupName(final HttpServletRequest request, final RedirectAttributes redirectAttributes,
            @RequestParam("permsnGrpNm") String permsnGrpNm) {

        logger.info(INFO_USER_VALIDATE_PERMISSION_GROUP);
//        logger.info("Start: deletePermissionGroup() to delete permission group");
        String message = "";
        try {
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                UserProfileDTO userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User validatePemissionGroupName page"));
                logger.trace(TRACE_USER_VALIDATE_PERMISSION_GROUP);
            }
            int permissionGroupNameCheck = managePermissionGroupService.fetchPermissionGroupByName(permsnGrpNm);
            if (0 < permissionGroupNameCheck) {
                message = myProperties.getProperty("permissiongroup.name.check");
            } else {
                message = "No";
            }
            logger.info("Start: deletePermissionGroup() to delete permission group");
        } catch (AppException e) {
            logger.error("Exceptionoccured : deletePermissionGroup: " + e.getMessage());
        }
        return message;
    }

    /**
     * This method for view permission group page
     *
     * @param request
     * @param redirectAttributes
     * @param model
     * @return ModelAndView
     */
    @RequestMapping(value = "/viewpermissiongroup")
    public @ResponseBody
    ModelAndView viewPemissionGroup(final HttpServletRequest request,
            RedirectAttributes redirectAttributes, Model model) {

        logger.info(INFO_USER_VIEW_PERMISSION_GROUP);
//        logger.info("Start: viewPemissionGroup() to view permission group page");
        PermissionGroupDTO permissionGroupDTO = new PermissionGroupDTO();
        ModelAndView modelView = new ModelAndView();
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return new ModelAndView("redirect:/logout.htm");
            }
            
            if (!StringUtils.isEmpty(request.getParameter("encGroupKey"))) {
                if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                    UserProfileDTO userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                    MDC.put("user", userDto.getEmail());
                    MDC.put("ip", userDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User accessed viewPemissionGroup page"));
                    logger.trace(TRACE_USER_VIEW_PERMITION_GROUP);
                    if (permissionCheckHelper.checkUserPermission(userDto, PermissionConstants.MANAGE_PERMISSION_GROUP_SUB_MENU,
                            PermissionConstants.VIEW_PERMISSION_GROUP)) {

                        long decGroupKey = 0;
                        if (request.getParameter("encGroupKey") != null && !request.getParameter("encGroupKey").equalsIgnoreCase("")) {
                            decGroupKey = Long.parseLong(encDec.decrypt(request.getParameter("encGroupKey")));
                        }
                        if (0 < decGroupKey) {
                            permissionGroupDTO = managePermissionGroupService.fetchPermissionGroupById(decGroupKey);
                        }
                        List<PermissionDTO> permissionsList = managePermissionGroupService.fetchPermissionsForPermissionGroup(permissionGroupDTO);
                        permissionGroupDTO = managePermissionGroupService.filterDetailedPermissionsForPermissionGroup(permissionGroupDTO, permissionsList);
                        List<MasterLookUpDTO> lookUpList = masterLookUpService.fetchMasterLookUpEntitiesByEntityType(ApplicationConstants.DB_CONSTANT_ROW_STATUS);

                        modelView.addObject("permissionGroupDTO", permissionGroupDTO);
                        modelView.addObject("lookUpList", lookUpList);
                        modelView.setViewName("usermgmt/viewpermissiongroup");
                        logger.info("Start: viewPemissionGroup() view permission group page");
                    } else {
                        modelView.setViewName("redirect:/logout.htm");
                    }
                }
            } else {
                modelView.setViewName("redirect:/logout.htm");
            }

        } catch (AppException e) {
            logger.error("Exceptionoccured : viewPemissionGroup: " + e.getMessage());
        }
        return modelView;
    }
}
