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
import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.dto.ExceptionDTO;
import com.optum.oss.dto.ManageServiceUserDTO;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.OrganizationDTO;
import com.optum.oss.dto.RoleDTO;
import com.optum.oss.dto.SecurityPackageDTO;
import com.optum.oss.dto.SecurityServiceDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.dto.Views;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.PermissionCheckHelper;
import com.optum.oss.service.impl.ManageEngagementsServiceImpl;
import com.optum.oss.service.impl.MasterLookUpServiceImpl;
import com.optum.oss.service.impl.UserManagementServiceImpl;
import com.optum.oss.util.CommonUtil;
import com.optum.oss.util.EncryptDecrypt;
import com.optum.oss.util.LoggerUtil;
import com.optum.oss.validators.AddEngagementValidator;
import com.optum.oss.validators.UpdateEngagementValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author sbhagavatula
 */
@Controller
public class ManageEngagementsController {

    private static final Logger logger = Logger.getLogger(ManageEngagementsController.class);

    @Resource(name = "myProperties")
    private Properties myProperties;
    /*
     Autowiring the required Class instances
     */
    @Autowired
    private UserManagementServiceImpl userManagementServiceImpl;
    @Autowired
    private MasterLookUpServiceImpl masterLookUpServiceImpl;
    @Autowired
    private ManageEngagementsServiceImpl manageEngagementsServiceImpl;
    @Autowired
    private ManageEngagementsServiceImpl manageEngagementsService;
    @Autowired
    private EncryptDecrypt encDec;
    @Autowired
    PermissionCheckHelper permissionCheckHelper;
    @Autowired
    private LoggerUtil auditLogger;
    @Autowired
    private AddEngagementValidator addEngagementValidator;
    @Autowired
    private UpdateEngagementValidator updateEngagementValidator;
    @Autowired
    private CommonUtil commonUtil;
    /*
     Autowiring the required Class instances
     */
    /*
     START : LOG MESSAGES
     */
    private final String TRACE_USER_ENG_LIST = "User accessed engagementlist";

    private final String TRACE_USER_ADD_ENGAGEMENT = "User accessed saveengagement";
    private final String TRACE_USER_SAVE_ENGAGEMENT = "User accessed saveengagement";
    private final String TRACE_USER_UPDATE_ENGAGEMENT = "User accessed updateengagement";
    private final String TRACE_USER_ENG_UPDATE_ = "User accessed engagementupdate";
    private final String TRACE_USER_DELETE_ENGAGEMENT = "User accessed deleteengagement";
    private final String TRACE_USER_VIEW_CLIENT_ENGAGEMENT = "User accessed viewclientengagement";
    private final String TRACE_USER_MANAGE_SERVICE = "User accessed vmanageserviceuser";
    private final String TRACE_USER_FETCH_USERS = "User accessed fetchusers";
    private final String TRACE_USER_FETCH_SERVICES = "User accessed fetchservices";
    private final String TRACE_USER_SAVEOREDIT_ASSSIGN_USER = "User accessed saveoreditassignuser";
    private final String TRACE_USER_SEARCH_ASSIGN_INTERNAL_VIEW = "User accessed searchassignuser internalview";
    private final String TRACE_USER_SEARCH_ASSIGNUSER_PATRNER_VIEW = "User accessed ssearchassignuser partnerview";
    private final String TRACE_USER_DELETE_ASSSIGN_USER = "User accessed deleteassignedusertoservice";
    private final String INFO_USER_ENG_list = "User accessed engagement list";
    private final String INFO_USER_VIEW_CLIENT_ENGAGEMENT = "User accessed view client engagement";
    private final String INFO_USER_SAVE_OR_EDIT_ASSIGN_USER = "User accessed save or edit assign user";
    private final String INFO_USER_SEARCH_ASSIGN_INTERNAL_VIEW = "User accessed search assign internalview";
    private final String INFO_USER_SEARCH_ASSIGNUSER_PATRNER_VIEW = "User accessed search assign partner view";
    private final String INFO_USER_MANAGE_SERVICE = "User accesses manage service";
    private final String INFO_USER_DELETE_ASSIGN_USER = "User accessed delete assign user";
    private final String INFO_MANAGE_ENGAGEMENT_USERS_WORKLIST = "User accessed manage engagement users worklist";
    private final String INFO_ADD_ENGAGEMENT_USER = "User accessed add engagement user";
    private final String TRACE_ADD_ENGAGEMENT_USER = "User accessed add engagement user";
    private final String INFO_ADD_SERVICE_USER = "User accessed add service user";
    private final String TRACE_ADD_SERVICE_USER = "User accessed add service user";
    private final String INFO_DEL_SERVICE_USER = "User accessed delete engagement user";
    private final String TRACE_DEL_SERVICE_USER = "User accessed delet engagement user";
    /*
     END: LOG MESSAGES
     */

    /**
     * This method for fetching Manage Engagements work list
     *
     * @param request
     * @param response
     * @param redirectAttributes
     * @param model
     * @return ModelAndView
     */
    @RequestMapping(value = "/engagementlist")
    public ModelAndView manageEngagements(final HttpServletRequest request, final HttpServletResponse response,
            RedirectAttributes redirectAttributes, Model model) {

        logger.info(INFO_USER_ENG_list);
        ModelAndView modelView = new ModelAndView();
        UserProfileDTO userDto = new UserProfileDTO();
        Set<String> permissionSet = null;
        try {
            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return new ModelAndView("redirect:/logout.htm");
            }
            HttpSession session = request.getSession();

            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(TRACE_USER_ENG_LIST);
                permissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU);
                modelView.addObject("permissions", permissionSet);
            }
            if (permissionCheckHelper.checkUserSubMenuNotAdmin(userDto,
                    PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU)) {
                List<ClientEngagementDTO> engagementsList = manageEngagementsService.fetchEngagementsWorklist(userDto, permissionSet);
                modelView.addObject("engagementsList", engagementsList);
                modelView.setViewName("clientengagement/clientengagementsworklist");
            } else {
                modelView.setViewName("redirect:/logout.htm");
            }
        } catch (AppException e) {
            logger.error("Exception occurred : fetchPermissionGroupWorkList:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        logger.info("End: manageEngagements() to fetching Manage Engagements work list");
        return modelView;
    }
    
    /**
     * This method for fetching Manage Engagements work list
     *
     * @param request
     * @param response
     * @param redirectAttributes
     * @param model
     * @return ModelAndView
     */
    @RequestMapping(value = "/manageengusers")
    public ModelAndView manageEngUsersWorklist(final HttpServletRequest request, final HttpServletResponse response,
            RedirectAttributes redirectAttributes, Model model) {

        logger.info(INFO_MANAGE_ENGAGEMENT_USERS_WORKLIST);
        ModelAndView modelView = new ModelAndView();
        UserProfileDTO userDto = new UserProfileDTO();
        Set<String> permissionSet = null;
        Set<String> userPermissionSet = null;
        try {
            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return new ModelAndView("redirect:/logout.htm");
            }
            HttpSession session = request.getSession();

            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(TRACE_USER_ENG_LIST);
                permissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_ENGAGEMENT_USERS_SUB_MENU);
                userPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU);
                if(userPermissionSet != null) permissionSet.addAll(userPermissionSet);
                modelView.addObject("permissions", permissionSet);
            }
            if (permissionCheckHelper.checkUserSubMenuNotAdmin(userDto,
                    PermissionConstants.MANAGE_ENGAGEMENT_USERS_SUB_MENU)) {
                List<ClientEngagementDTO> engagementsList = manageEngagementsService.fetchEngagementsWorklist(userDto, permissionSet);
                modelView.addObject("engagementsList", engagementsList);
                modelView.setViewName("clientengagement/manageengusers");
            } else {
                modelView.setViewName("redirect:/logout.htm");
            }
        } catch (AppException e) {
            logger.error("Exception occurred : manageEngUsersWorklist:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        logger.info("End: manageEngUsersWorklist() to fetching Manage Engagements work list");
        return modelView;
    }

    /**
     *
     * @param request
     * @param response
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/addengagement")
    public String addEngagaementPage(final HttpServletRequest request,
            final HttpServletResponse response, Model model,
            RedirectAttributes redirectAttributes) {
        UserProfileDTO userDto = new UserProfileDTO();
        try {

            HttpSession session = request.getSession();

            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return "redirect:/logout.htm";
            }
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(TRACE_USER_ADD_ENGAGEMENT);
                Set<String> permissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU);
                model.addAttribute("permissions", permissionSet);
            }
            ClientEngagementDTO clientEngagementDTO;
            boolean flag = false;
            if (request.getParameter("addengagementFlag") != null || model.asMap().get("addengagementFlag") != null) {
                flag = true;
            }

            if (!flag) {
                return "redirect:/logout.htm";
            }
            if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU,
                    PermissionConstants.ADD_ENGAGEMENT)) {
                if (model.containsAttribute("clientEngagementDTO")) {
                    clientEngagementDTO = (ClientEngagementDTO) model.asMap().get("clientEngagementDTO");

                    model.addAttribute("engStatusMessage",
                            model.asMap().get("engStatusMessage"));
                    model.addAttribute("org.springframework.validation.BindingResult.saveEngagement",
                            model.asMap().get("org.springframework.validation.BindingResult.clientEngagementDTO"));
                    model.addAttribute("updateClientDTO", clientEngagementDTO);

                    //FETCH ALL SERVICES FOR UPDATED ENGAGEMENT BASED ON SECURITY PACKAGE KEY
                    List<SecurityServiceDTO> servicesList = manageEngagementsServiceImpl.fetchServicesByPackage(clientEngagementDTO.getSecurityPackageObj().getSecurityPackageCode());
                    model.addAttribute("servicesList", servicesList);

                    model.addAttribute("validationFlag", "valid");
                } else {
                    model.addAttribute("updateClientDTO", new ClientEngagementDTO());
                }

                //FETCH SOURCE SYSTEMS
                List<MasterLookUpDTO> srcSystems = masterLookUpServiceImpl.fetchMasterLookUpEntitiesByEntityType(ApplicationConstants.DB_CONSTANT_SOURCE);
                model.addAttribute("srcSystems", srcSystems);

                //FETCH SECURITY PACKAGES
                List<SecurityPackageDTO> securityPkgs = manageEngagementsServiceImpl.fetchSecurityPackages();
                model.addAttribute("securityPkgs", securityPkgs);

                int clientUserTypeId = masterLookUpServiceImpl.fetchEntityIdByEntityName(ApplicationConstants.DB_CONSTANT_USER_TYPE, ApplicationConstants.DB_USER_TYPE_CLIENT);
                //FETCH ALL CLIENT ORGANIZATIONS
                List<OrganizationDTO> clientOrgList = userManagementServiceImpl.fetchOrgListByUserType(clientUserTypeId);
                model.addAttribute("clientOrgList", clientOrgList);

            } else {
                return "redirect:/logout.htm";
            }
        } catch (AppException e) {
            logger.debug("Exceptionoccured : addEngagaementPage : " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        return "clientengagement/addengagement";
    }

    @InitBinder("saveEngagement")
    protected void saveEngagaementBinder(WebDataBinder binder)
            throws Exception {
        binder.setDisallowedFields(new String[]{
            "encClientEngagementKey",
            "engagementStatusKey",
            "engagementStatusName",
            "engagementLeadClientUserID",
            "engagementLeadInternalUserID",
            "engagementComments",
            "clientName",
            "parentClientName",
            "engagementLeadClientUserName",
            "engagementLeadInternalUserName",
            "clientUserType",
            "internalUserType",
            "sourceNameKey",
            "sourceName",
            "documentTypeKey",
            "documentType",
            "uploadFileName",
            "uploadFilePath",
            "uploadComments",
            "securityServiceCode",
            "encSecurityServiceCode",
            "securityServiceName",
            "serviceLockFlag",
            "templateupload",
            "fileUploadID",
            "encfileUploadID",
            "tempFilePath",
            "userTypeFlag",
            "fileStatusKey",
            "uploadedFilesCount",
            "encS",
            "encFileDownload"
        });
    }

    /**
     *
     * @param saveEngagement
     * @param request
     * @param response
     * @param redirectAttributes
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveengagement", method = RequestMethod.POST)
    public String saveEngagaement(@ModelAttribute(value = "saveEngagement") ClientEngagementDTO saveEngagement, final HttpServletRequest request,
            final HttpServletResponse response, RedirectAttributes redirectAttributes, BindingResult result,
            Model model) {
        HttpSession session = request.getSession();
        ModelAndView modelView = new ModelAndView();
        try {
            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return "redirect:/logout.htm";
            }

            UserProfileDTO sessionUserDTO = new UserProfileDTO();
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                sessionUserDTO = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", sessionUserDTO.getEmail());
                MDC.put("ip", sessionUserDTO.getUserMACAddress());
                logger.trace(TRACE_USER_SAVE_ENGAGEMENT);
            }
            addEngagementValidator.validate(saveEngagement, result);
            redirectAttributes.addFlashAttribute("addengagementFlag", "addengagementFlag");
            if (result.hasErrors()) {
                //CONSTRUCT DATA TO RETAIN THE DATA ENTERED BY THE USER FOR CREATING ENGAGEMENT
                saveEngagement = manageEngagementsServiceImpl.prepareEngagementDataForRetaing(saveEngagement);

                redirectAttributes.addFlashAttribute("clientEngagementDTO", saveEngagement);
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.clientEngagementDTO", result);
                return "redirect:/addengagement.htm";
            }

            int retVal = manageEngagementsServiceImpl.saveEngagement(saveEngagement, sessionUserDTO);
            if (retVal > 0) {
                redirectAttributes.addFlashAttribute("engStatusMessage",
                        myProperties.getProperty("engagement.add.success-message"));
                redirectAttributes.addFlashAttribute("clientEngagementDTO", saveEngagement);
                redirectAttributes.addAttribute("cei", encDec.encrypt(saveEngagement.getEngagementCode()));
                return "redirect:/updateengagement.htm";
            } else if (retVal == -2) {
                //CONSTRUCT DATA TO RETAIN THE DATA ENTERED BY THE USER FOR CREATING ENGAGEMENT
                saveEngagement = manageEngagementsServiceImpl.prepareEngagementDataForRetaing(saveEngagement);

                redirectAttributes.addFlashAttribute("clientEngagementDTO", saveEngagement);
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.clientEngagementDTO", result);
                redirectAttributes.addFlashAttribute("engStatusMessage", "Active");
                return "redirect:/addengagement.htm";
            } else if (retVal == -3) {
                //CONSTRUCT DATA TO RETAIN THE DATA ENTERED BY THE USER FOR CREATING ENGAGEMENT
                saveEngagement = manageEngagementsServiceImpl.prepareEngagementDataForRetaing(saveEngagement);

                redirectAttributes.addFlashAttribute("clientEngagementDTO", saveEngagement);
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.clientEngagementDTO", result);
                redirectAttributes.addFlashAttribute("engStatusMessage", "Inactive");
                return "redirect:/addengagement.htm";
            } else {
                ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"));
                modelView.addObject("exceptionBean", exception);
                return "/exception";
            }
        } catch (AppException e) {
            logger.debug("Exception occured : saveEngagaement : " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            modelView.addObject("exceptionBean", exception);
            return "/exception";
        }
    }

    @RequestMapping(value = "/activateengagement")
    public String activateEngagaement(final HttpServletRequest request,
            final HttpServletResponse response, RedirectAttributes redirectAttributes,
            @RequestParam("cei") String cei, Model model) {
        ModelAndView modelView = new ModelAndView();
        try {
            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return "redirect:/logout.htm";
            }
            manageEngagementsService.activateEngagement(encDec.decrypt(cei));
            redirectAttributes.addAttribute("cei", cei);
        } catch (AppException e) {
            logger.debug("Exception occured : updateEngagaementPage : " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            modelView.addObject("exceptionBean", exception);
            return "/exception";
        }
        return "redirect:/updateengagement.htm";
    }

    /**
     *
     * @param request
     * @param response
     * @param redirectAttributes
     * @param model
     * @return
     */
    @RequestMapping(value = "/updateengagement")
    public String updateEngagaementPage(final HttpServletRequest request,
            final HttpServletResponse response,
            RedirectAttributes redirectAttributes, Model model) {
        UserProfileDTO userDto = new UserProfileDTO();
        try {
            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return "redirect:/logout.htm";
            }
            HttpSession session = request.getSession();
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(TRACE_USER_UPDATE_ENGAGEMENT);
                Set<String> permissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU);
                model.addAttribute("permissions", permissionSet);
            }
            if (request.getParameter("cei") != null && !request.getParameter("cei").equalsIgnoreCase("")) {
                String cei = request.getParameter("cei");
                String decGroupKey = encDec.decrypt(cei);
                if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU,
                        PermissionConstants.EDIT_ENGAGEMENT)) {
                    ClientEngagementDTO clientEngagementDTO;
                    if (model.containsAttribute("clientEngagementDTO")) {
                        clientEngagementDTO = (ClientEngagementDTO) model.asMap().get("clientEngagementDTO");

                        //FETCH ENGAGEMENT DETAILS BASED ON ENGAGEMENT KEY
                        ClientEngagementDTO nonEditInfo = manageEngagementsService.viewEngagementByEngmtKey(decGroupKey);
                        clientEngagementDTO.setClientName(nonEditInfo.getClientName());
                        clientEngagementDTO.setClientID(nonEditInfo.getClientID());
                        clientEngagementDTO.getSecurityPackageObj().setSecurityPackageCode(nonEditInfo.getSecurityPackageObj().getSecurityPackageCode());
                        clientEngagementDTO.getSecurityPackageObj().setSecurityPackageName(nonEditInfo.getSecurityPackageObj().getSecurityPackageName());
                        clientEngagementDTO.setAgreementDate(nonEditInfo.getAgreementDate());

                        //CONSTRUCT DATA TO RETAIN THE DATA ENTERED BY THE USER FOR CREATING ENGAGEMENT
                        clientEngagementDTO = manageEngagementsServiceImpl.prepareEngagementDataForRetaing(clientEngagementDTO);

                        model.addAttribute("org.springframework.validation.BindingResult.saveEngagement",
                                model.asMap().get("org.springframework.validation.BindingResult.clientEngagementDTO"));
                        model.addAttribute("updateClientDTO", clientEngagementDTO);

                        //FETCH ALL SERVICES FOR UPDATED ENGAGEMENT BASED ON SECURITY PACKAGE KEY
                        List<SecurityServiceDTO> servicesList = manageEngagementsServiceImpl.fetchServicesByPackage(clientEngagementDTO.getSecurityPackageObj().getSecurityPackageCode());
                        model.addAttribute("servicesList", servicesList);

                    } else {
                        //FETCH ENGAGEMENT DETAILS BASED ON ENGAGEMENT KEY
                        ClientEngagementDTO engagementDTO = manageEngagementsService.viewEngagementByEngmtKey(decGroupKey);
                        model.addAttribute("updateClientDTO", engagementDTO);

                        //FETCH ALL SERVICES FOR UPDATED ENGAGEMENT BASED ON SECURITY PACKAGE KEY
                        List<SecurityServiceDTO> servicesList = manageEngagementsServiceImpl.fetchServicesByPackage(engagementDTO.getSecurityPackageObj().getSecurityPackageCode());
                        model.addAttribute("servicesList", servicesList);
                    }

                    //FETCH SOURCE SYSTEMS
                    List<MasterLookUpDTO> srcSystems = masterLookUpServiceImpl.fetchMasterLookUpEntitiesByEntityType(ApplicationConstants.DB_CONSTANT_SOURCE);
                    model.addAttribute("srcSystems", srcSystems);

                    //FETCH SECURITY PACKAGES
                    List<SecurityPackageDTO> securityPkgs = manageEngagementsServiceImpl.fetchSecurityPackages();
                    model.addAttribute("securityPkgs", securityPkgs);

                    int clientUserTypeId = masterLookUpServiceImpl.fetchEntityIdByEntityName(ApplicationConstants.DB_CONSTANT_USER_TYPE, ApplicationConstants.DB_USER_TYPE_CLIENT);
                    //FETCH ALL CLIENT ORGANIZATIONS
                    List<OrganizationDTO> clientOrgList = userManagementServiceImpl.fetchOrgListByUserType(clientUserTypeId);
                    model.addAttribute("clientOrgList", clientOrgList);

                    model.addAttribute("redirectPage", "updatepage");

                } else {
                    return "redirect:/logout.htm";
                }
            } else {
                return "redirect:/logout.htm";
            }

        } catch (AppException e) {
            logger.debug("Exception occured : updateEngagaementPage : " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        return "clientengagement/addengagement";
    }

    /**
     *
     * @param saveEngagement
     * @param request
     * @param response
     * @param redirectAttributes
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(value = "/engagementupdate")
    public String updateEngagaement(@ModelAttribute(value = "saveEngagement") ClientEngagementDTO saveEngagement, final HttpServletRequest request,
            final HttpServletResponse response, RedirectAttributes redirectAttributes, BindingResult result, Model model) {
        HttpSession session = request.getSession();
        ModelAndView modelView = new ModelAndView();
        try {
            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return "redirect:/logout.htm";
            }

            UserProfileDTO sessionUserDTO = new UserProfileDTO();
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                sessionUserDTO = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", sessionUserDTO.getEmail());
                MDC.put("ip", sessionUserDTO.getUserMACAddress());
                logger.trace(TRACE_USER_ENG_UPDATE_);

            }
            updateEngagementValidator.validate(saveEngagement, result);
            if (result.hasErrors()) {
                redirectAttributes.addFlashAttribute("clientEngagementDTO", saveEngagement);
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.clientEngagementDTO", result);
                redirectAttributes.addAttribute("cei", encDec.encrypt(saveEngagement.getEngagementCode()));
                return "redirect:/updateengagement.htm";
            }

            int retVal = manageEngagementsServiceImpl.updateEngagement(saveEngagement, sessionUserDTO);
            if (retVal > 0) {
                redirectAttributes.addFlashAttribute("clientEngagementDTO", saveEngagement);
                redirectAttributes.addAttribute("cei", encDec.encrypt(saveEngagement.getEngagementCode()));
                redirectAttributes.addFlashAttribute("engStatusMessage",
                        myProperties.getProperty("engagement.update.success-message"));
                return "redirect:/updateengagement.htm";
            } else {
                ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"));
                modelView.addObject("exceptionBean", exception);
                return "/exception";
            }
        } catch (AppException e) {
            logger.debug("Exception occured : saveEngagaement : " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            modelView.addObject("exceptionBean", exception);
            return "/exception";
        }
    }

    /**
     *
     * @param request
     * @param response
     * @param redirectAttributes
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteengagement", method = RequestMethod.POST)
    public ModelAndView deleteEngagaement(final HttpServletRequest request,
            final HttpServletResponse response,
            RedirectAttributes redirectAttributes, Model model) {
        HttpSession session = request.getSession();
        ModelAndView modelView = new ModelAndView();
        try {
            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return new ModelAndView("redirect:/logout.htm");
            }

            UserProfileDTO sessionUserDTO = new UserProfileDTO();
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                sessionUserDTO = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", sessionUserDTO.getEmail());
                MDC.put("ip", sessionUserDTO.getUserMACAddress());
                logger.trace(TRACE_USER_DELETE_ENGAGEMENT);
            }
            String engCode = request.getParameter("engagementCode");
            ClientEngagementDTO saveEngagement = new ClientEngagementDTO();
            saveEngagement.setEngagementCode(engCode);
            int retVal = manageEngagementsServiceImpl.deleteEngagement(saveEngagement, sessionUserDTO);
            if (retVal > 0) {
                redirectAttributes.addFlashAttribute("engStatusMessage",
                        myProperties.getProperty("engagement.delete.success-message"));
                modelView.setViewName("redirect:engagementlist.htm");
            } else {
                ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"));
                return new ModelAndView("/exception", "exceptionBean", exception);
            }
        } catch (AppException e) {
            logger.debug("Exception occured : deleteEngagaement : " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        return modelView;
    }

    /**
     * This method for view client engagement
     *
     * @param request
     * @param redirectAttributes
     * @param model
     * @return ModelAndView
     */
    @RequestMapping(value = "/viewclientengagement")
    public ModelAndView viewClientEngagement(final HttpServletRequest request,
            RedirectAttributes redirectAttributes, Model model) {

        logger.info(INFO_USER_VIEW_CLIENT_ENGAGEMENT);
//        logger.info("Start: viewClientEngagement() to view client engagement");
        ModelAndView modelView = new ModelAndView();
        UserProfileDTO userDto = new UserProfileDTO();
        HttpSession session = request.getSession();
        try {
            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return new ModelAndView("redirect:/logout.htm");
            }

            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(TRACE_USER_VIEW_CLIENT_ENGAGEMENT);
                Set<String> permissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU);
                modelView.addObject("permissions", permissionSet);
            }
            if (request.getParameter("cei") != null && !request.getParameter("cei").equalsIgnoreCase("")) {
                String cei = request.getParameter("cei");
                String decGroupKey = encDec.decrypt(cei);
                if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU,
                        PermissionConstants.VIEW_ENGAGEMENT) && !decGroupKey.isEmpty()) {
                    ClientEngagementDTO engagementDTO = manageEngagementsService.viewEngagementByEngmtKey(decGroupKey);
                    modelView.addObject("clientEngagementDTO", engagementDTO);
                    
                    //FETCH CLIENT ENGAGEMENT USERS
                    List<UserProfileDTO> engagementUsersList = manageEngagementsService.clientEngagementUsers(decGroupKey);
                    model.addAttribute("engusrlist", engagementUsersList);
                    
                    //FETCH CLIENT ENGAGEMENT SERVICE USERS
                    List<ManageServiceUserDTO> serviceUsersList = manageEngagementsService.fetchServiceUsers(decGroupKey);
                     model.addAttribute("srvusrlist", serviceUsersList);
                     
                    modelView.setViewName("clientengagement/viewclientengagement");
                } else {
                    modelView.setViewName("redirect:/logout.htm");
                }
            } else {
                return new ModelAndView("redirect:/logout.htm");
            }
        } catch (AppException e) {
            logger.error("Exception occurred : viewClientEngagement:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        logger.info("End: viewClientEngagement() to view client engagement");
        return modelView;
    }

    /**
     * This method for manage users to services page for partner view
     *
     * @param engUserObj
     * @param request
     * @param model
     * @param redirectAttributes
     * @return ModelAndView
     */
    @RequestMapping(value = "/manageengagementusers")
    public String manageEngagementUsers(@ModelAttribute(value = "enguserobj") UserProfileDTO engUserObj,
            final HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {

        logger.info(INFO_USER_MANAGE_SERVICE);
        String retString = "";
        HttpSession session = request.getSession();
        UserProfileDTO sessionUserDTO = new UserProfileDTO();
        String cei = "";
        Set<String> userPermissionSet = null;
        try {
            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return "redirect:/logout.htm";
            }

            if (model.asMap().get("cei") != null && !model.asMap().get("cei").toString().equalsIgnoreCase("")) {
                cei = model.asMap().get("cei").toString();
            } else {
                cei = request.getParameter("cei");
            }

            sessionUserDTO = new UserProfileDTO();
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                sessionUserDTO = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", sessionUserDTO.getEmail());
                MDC.put("ip", sessionUserDTO.getUserMACAddress());
                logger.trace(TRACE_USER_MANAGE_SERVICE);
                Set<String> permissionSet = sessionUserDTO.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_ENGAGEMENT_USERS_SUB_MENU);
                userPermissionSet = sessionUserDTO.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU);
                if(userPermissionSet != null) permissionSet.addAll(userPermissionSet);
                model.addAttribute("permissions", permissionSet);
            }
            if (cei != null && !cei.equalsIgnoreCase("")) {
                String decGroupKey = encDec.decrypt(cei);
                if (permissionCheckHelper.checkUserSubMenuNotAdmin(sessionUserDTO,
                    PermissionConstants.MANAGE_ENGAGEMENT_USERS_SUB_MENU) && !decGroupKey.isEmpty()) {
                    //ClientEngagementDTO engagementDTO = manageEngagementsService.viewManageServiceUser(decGroupKey, sessionUserDTO);
                    ClientEngagementDTO engagementDTO = manageEngagementsService.viewEngagementByEngmtKey(decGroupKey);
                    
                    //FETCH USER TYPES 
                    List<MasterLookUpDTO> userTypes = masterLookUpServiceImpl.fetchMasterLookUpEntitiesByEntityType(ApplicationConstants.DB_CONSTANT_USER_TYPE);
                    model.addAttribute("userTypes", userTypes);
                    
                    if(ApplicationConstants.USER_TYPE_CLIENT_KEY == engUserObj.getUserTypeKey()){
                        engUserObj.setOrganizationKey(engagementDTO.getClientID());
                    }
                    
                    //FETCH USERS BY ROLE
                    List<UserProfileDTO> usersList = manageEngagementsService.fetchUsersByRole(engUserObj);
                    
                    int partnerUserTypeId = masterLookUpServiceImpl.fetchEntityIdByEntityName(ApplicationConstants.DB_CONSTANT_USER_TYPE, ApplicationConstants.DB_USER_TYPE_PARTNER);
                    //FETCH ALL PARTNER ORGANIZATIONS
                    List<OrganizationDTO> partnerOrgList = userManagementServiceImpl.fetchOrgListByUserType(partnerUserTypeId);
                    model.addAttribute("partnerOrgList", partnerOrgList);
                    
                    List<UserProfileDTO> engagementUsersList = manageEngagementsService.clientEngagementUsers(decGroupKey);
                    model.addAttribute("engusrlist", engagementUsersList);
                    
                    List<ManageServiceUserDTO> serviceUsersList = manageEngagementsService.fetchServiceUsers(decGroupKey);
                     model.addAttribute("srvusrlist", serviceUsersList);

                    model.addAttribute("enguserobj", null);
                    model.addAttribute("srvuserobj", null);
                    model.addAttribute("engRoleList", null);
                    model.addAttribute("srvRoleList", null);
                    model.addAttribute("engUsrList", null);
                    model.addAttribute("srvUsrList", null);

                    if ('E' == engUserObj.getUserEngSrv()) {
                        model.addAttribute("enguserobj", engUserObj);
                         String pkgCOde=engagementDTO.getSecurityPackageObj().getSecurityPackageCode();
                        //FETCH ALL THE ROLES BY USER TYPES
                        List<RoleDTO> engRoleList = manageEngagementsService.fetchEngRolesList(engUserObj.getUserTypeKey(),pkgCOde);
                        model.addAttribute("engRoleList", engRoleList);
                        
                        model.addAttribute("engUsrList", usersList);
                    } else if ('S' == engUserObj.getUserEngSrv()) {
                        model.addAttribute("srvuserobj", engUserObj);
                        //FETCH ALL THE ROLES BY USER TYPES
                        List<RoleDTO> srvRoleList = manageEngagementsService.fetchServceRolesList(engUserObj.getUserTypeKey());
                        model.addAttribute("srvRoleList", srvRoleList);
                        
                        model.addAttribute("srvUsrList", usersList);
                    }
                    
                    model.addAttribute("clientEngagementDTO", engagementDTO);
                    retString = "clientengagement/assignengagementusers";
                } else {
                   retString = "redirect:/logout.htm";
                }
            } else {
                retString = "redirect:/logout.htm";
            }
        } catch (AppException e) {
            logger.error("Exception occurred : manageServiceUser:" + e.getMessage());
             ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            retString = "/exception";
        }
        logger.info("End: manageServiceUser() to load manage users to services page");
        return retString;
    }
    
    @RequestMapping(value = "/addengagementusers")
    public String addEngagementUsers(@ModelAttribute(value = "enguserobj") UserProfileDTO engUserObj,
            final HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {

        logger.info(INFO_ADD_ENGAGEMENT_USER);
        String retString = "";
        HttpSession session = request.getSession();
        UserProfileDTO sessionUserDTO = new UserProfileDTO();
        String cei = "";
        try {
             if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return "redirect:/logout.htm";
            }

            if (model.asMap().get("cei") != null && !model.asMap().get("cei").toString().equalsIgnoreCase("")) {
                cei = model.asMap().get("cei").toString();
            } else {
                cei = request.getParameter("cei");
            }

            sessionUserDTO = new UserProfileDTO();
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                sessionUserDTO = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", sessionUserDTO.getEmail());
                MDC.put("ip", sessionUserDTO.getUserMACAddress());
                logger.trace(TRACE_ADD_ENGAGEMENT_USER);
                Set<String> permissionSet = sessionUserDTO.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_ENGAGEMENT_USERS_SUB_MENU);
                model.addAttribute("permissions", permissionSet);
            }
            if (cei != null && !cei.equalsIgnoreCase("")) {
                String decGroupKey = encDec.decrypt(cei);
                if (permissionCheckHelper.checkUserSubMenuNotAdmin(sessionUserDTO,
                    PermissionConstants.MANAGE_ENGAGEMENT_USERS_SUB_MENU) && !decGroupKey.isEmpty()) {
                   
                    boolean leadFlag = false;
                    boolean userFlag = false;
                    List<UserProfileDTO> engagementUsersList = manageEngagementsService.clientEngagementUsers(decGroupKey);
                    for(UserProfileDTO userDto : engagementUsersList){
                        leadFlag = engUserObj.getAppRoleKey() == userDto.getAppRoleKey() && engUserObj.getAppRoleKey() == ApplicationConstants.OPTUM_ENGAGEMENT_LEAD_KEY;
                        if(leadFlag) break;
                        
                        userFlag = engUserObj.getAppRoleKey() == userDto.getAppRoleKey() && userDto.getUserID().equalsIgnoreCase(engUserObj.getUserProfileKey()+"");
                        if(userFlag) break;
                    }
                    
                    if(leadFlag){
                        redirectAttributes.addFlashAttribute("userexists", "Optum Engagement Lead is already added");
                    }else if(userFlag){
                        redirectAttributes.addFlashAttribute("userexists", "User is already added");
                    }else{
                        manageEngagementsService.saveEngagementUsers(engUserObj, sessionUserDTO, decGroupKey);
                    }
                    redirectAttributes.addFlashAttribute("cei", cei);
                    UserProfileDTO emptyObj = new UserProfileDTO();
                    redirectAttributes.addFlashAttribute("enguserobj", emptyObj);
                    retString = "redirect:/manageengagementusers.htm";
                } else {
                   retString = "redirect:/logout.htm";
                }
            } else {
                retString = "redirect:/logout.htm";
            }
        } catch (AppException e) {
            logger.error("Exception occurred : addEngagementUsers:" + e.getMessage());
             ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            retString = "/exception";
        }
        logger.info("End: addEngagementUsers() to add engagement users");
        return retString;
    }
    
    @RequestMapping(value = "/delengagementusers")
    public String deleteEngagementUsers(@ModelAttribute(value = "deluserobj") UserProfileDTO engUserObj,
            final HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {

        logger.info(INFO_DEL_SERVICE_USER);
        String retString = "";
        HttpSession session = request.getSession();
        UserProfileDTO sessionUserDTO = new UserProfileDTO();
        String cei = "";
        try {
             if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return "redirect:/logout.htm";
            }

            if (model.asMap().get("cei") != null && !model.asMap().get("cei").toString().equalsIgnoreCase("")) {
                cei = model.asMap().get("cei").toString();
            } else {
                cei = request.getParameter("cei");
            }

            sessionUserDTO = new UserProfileDTO();
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                sessionUserDTO = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", sessionUserDTO.getEmail());
                MDC.put("ip", sessionUserDTO.getUserMACAddress());
                logger.trace(TRACE_DEL_SERVICE_USER);
                Set<String> permissionSet = sessionUserDTO.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_ENGAGEMENT_USERS_SUB_MENU);
                model.addAttribute("permissions", permissionSet);
            }
            if (cei != null && !cei.equalsIgnoreCase("")) {
                String decGroupKey = encDec.decrypt(cei);
                if (permissionCheckHelper.checkUserSubMenuNotAdmin(sessionUserDTO,
                    PermissionConstants.MANAGE_ENGAGEMENT_USERS_SUB_MENU) && !decGroupKey.isEmpty()) {
                   
                    manageEngagementsService.deleteEngagementUsers(engUserObj.getUserProfileKey()+"");
                    redirectAttributes.addFlashAttribute("cei", cei);
                    UserProfileDTO emptyObj = new UserProfileDTO();
                    redirectAttributes.addFlashAttribute("enguserobj", emptyObj);
                    retString = "redirect:/manageengagementusers.htm";
                } else {
                   retString = "redirect:/logout.htm";
                }
            } else {
                retString = "redirect:/logout.htm";
            }
        } catch (AppException e) {
            logger.error("Exception occurred : deleteEngagementUsers:" + e.getMessage());
             ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            retString = "/exception";
        }
        logger.info("End: deleteEngagementUsers() to delete engagement users");
        return retString;
    }
    
    @RequestMapping(value = "/addserviceusers")
    public String addServiceUsers(@ModelAttribute(value = "srvuserobj") UserProfileDTO engUserObj,
            final HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {

        logger.info(INFO_ADD_SERVICE_USER);
        String retString = "";
        HttpSession session = request.getSession();
        UserProfileDTO sessionUserDTO = new UserProfileDTO();
        String cei = "";
        try {
             if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return "redirect:/logout.htm";
            }

            if (model.asMap().get("cei") != null && !model.asMap().get("cei").toString().equalsIgnoreCase("")) {
                cei = model.asMap().get("cei").toString();
            } else {
                cei = request.getParameter("cei");
            }

            sessionUserDTO = new UserProfileDTO();
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                sessionUserDTO = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", sessionUserDTO.getEmail());
                MDC.put("ip", sessionUserDTO.getUserMACAddress());
                logger.trace(TRACE_ADD_SERVICE_USER);
                Set<String> permissionSet = sessionUserDTO.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_ENGAGEMENT_USERS_SUB_MENU);
                model.addAttribute("permissions", permissionSet);
            }
            if (cei != null && !cei.equalsIgnoreCase("")) {
                String decGroupKey = encDec.decrypt(cei);
                if (permissionCheckHelper.checkUserSubMenuNotAdmin(sessionUserDTO,
                    PermissionConstants.MANAGE_ENGAGEMENT_USERS_SUB_MENU) && !decGroupKey.isEmpty()) {
                    
                    boolean userFlag = false;
                    List<ManageServiceUserDTO> engagementUsersList = manageEngagementsService.fetchServiceUsers(decGroupKey);
                    for(ManageServiceUserDTO userDto : engagementUsersList){
                        userFlag = engUserObj.getAppRoleKey() == userDto.getRoleKey()&& engUserObj.getUserProfileKey() == userDto.getUserKey()
                                && engUserObj.getSelServCode().contains(userDto.getSecureServiceCode());
                        if(userFlag) break;
                    }
                    
                    if(userFlag){
                        redirectAttributes.addFlashAttribute("srvuserexists", "User is already added to this service");
                    }else{
                        manageEngagementsService.saveServiceUser(engUserObj, sessionUserDTO, decGroupKey);
                    }
                   
                    redirectAttributes.addFlashAttribute("cei", cei);
                    UserProfileDTO emptyObj = new UserProfileDTO();
                    redirectAttributes.addFlashAttribute("enguserobj", emptyObj);
                    retString = "redirect:/manageengagementusers.htm";
                } else {
                   retString = "redirect:/logout.htm";
                }
            } else {
                retString = "redirect:/logout.htm";
            }
        } catch (AppException e) {
            logger.error("Exception occurred : manageServiceUser:" + e.getMessage());
             ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            retString = "/exception";
        }
        logger.info("End: manageServiceUser() to load manage users to services page");
        return retString;
    }
    
    /**
     *
     * @param request
     * @param response
     * @param userType
     * @param organizationKey
     * @param userTypeFlag
     * @return
     */
    @JsonView(Views.Public.class)
    @RequestMapping(value = "/fetchusers")
    public @ResponseBody
    List<UserProfileDTO> fetchUsersByOrgIDAndUserTypeID(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("userType") String userType,
            @RequestParam("orgKey") long organizationKey,
            @RequestParam("userTypeFlag") String userTypeFlag) {
        List<UserProfileDTO> usersList = null;
        try {
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                UserProfileDTO userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User accessed fetchUsersByOrgIDAndUserTypeID page"));
                logger.trace(TRACE_USER_FETCH_USERS);
            }
            usersList = manageEngagementsServiceImpl.fetchUsersByOrgIDAndUserTypeID(organizationKey, userType, userTypeFlag, new ArrayList<String>());

        } catch (Exception e) {
            logger.debug("Exception occured : fetchUsersByOrgIDAndUserTypeID : " + e.getMessage());
        }
        return usersList;
    }

    /**
     *
     * @param request
     * @param response
     * @param packageKey
     * @return
     */
    @JsonView(Views.Public.class)
    @RequestMapping(value = "/fetchservices")
    public @ResponseBody
    List<SecurityServiceDTO> fetchServicesByPackage(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("packageKey") String packageKey) {
        List<SecurityServiceDTO> servicesList = null;
        try {
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                UserProfileDTO userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User accessed fetchServicesByPackage page"));
                logger.trace(TRACE_USER_FETCH_SERVICES);
            }
            servicesList = manageEngagementsServiceImpl.fetchServicesByPackage(packageKey);

        } catch (Exception e) {
            logger.debug("Exception occured : fetchUsersByOrgIDAndUserTypeID : " + e.getMessage());
        }
        return servicesList;
    }

    @InitBinder("manageServiceUserDto")
    protected void saveOrUpdateUserToService(WebDataBinder binder)
            throws Exception {
        binder.setAllowedFields(new String[]{"manageServiceUserKey",
            "userKey",
            "startDate",
            "endDate",
            "secureServiceCode",
            "clientEngagementCode",
            "clientEngagementEndDate",
            "oldAssignedUserKey",
            "rowStatusKey",
            "userName",
            "serviceName"});
    }

    /**
     * This method for save or update assign user to service
     *
     * @param request
     * @param manageServiceUserDto
     * @param redirectAttributes
     * @param model
     * @return ModelAndView
     */
    @RequestMapping(value = "/saveoreditassignuser", method = RequestMethod.POST)
    public ModelAndView saveOrUpdateAssignUserToService(final HttpServletRequest request,
            @ModelAttribute(value = "manageServiceUserDto") ManageServiceUserDTO manageServiceUserDto,
            RedirectAttributes redirectAttributes, Model model) {

        logger.info(INFO_USER_SAVE_OR_EDIT_ASSIGN_USER);
//        logger.info("Start: saveAssignUserToService() to save assign user to service");
        ModelAndView modelView = new ModelAndView();
        HttpSession session = request.getSession();
        long manageServiceKey = manageServiceUserDto.getManageServiceUserKey();
        try {
            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return new ModelAndView("redirect:/logout.htm");
            }

            UserProfileDTO sessionUserDTO = new UserProfileDTO();
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                sessionUserDTO = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", sessionUserDTO.getEmail());
                MDC.put("ip", sessionUserDTO.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(sessionUserDTO.getEmail(),sessionUserDTO.getUserMACAddress(), " User accessed saveOrUpdateAssignUserToService page"));
                logger.trace(TRACE_USER_SAVEOREDIT_ASSSIGN_USER);
            }
            int retVal = manageEngagementsService.saveOrUpdateManageUserToService(manageServiceUserDto, sessionUserDTO);
            if (manageServiceKey <= 0 && retVal > 0) {
                redirectAttributes.addFlashAttribute("manageserviceusersuccessMessage", myProperties.getProperty("manageserviceuser.add.success-message"));
                redirectAttributes.addFlashAttribute("manageserviceusersaveflag", ApplicationConstants.SAVE_FLAG);
            }
            if (manageServiceKey > 0 && retVal > 0) {
                redirectAttributes.addFlashAttribute("manageserviceusersuccessMessage", myProperties.getProperty("manageserviceuser.update.success-message"));
                redirectAttributes.addFlashAttribute("manageserviceusersaveflag", ApplicationConstants.UPDATE_FLAG);
            }
            redirectAttributes.addFlashAttribute("cei", encDec.encrypt(manageServiceUserDto.getClientEngagementCode()));
            modelView.setViewName("redirect:manageserviceuser.htm");
        } catch (AppException e) {
            logger.error("Exception occurred : saveAssignUserToService:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        logger.info("End: saveAssignUserToService() to save assign user to service");
        return modelView;
    }

    @InitBinder("searchManageServiceUserDto")
    protected void searchManageUsersToService(WebDataBinder binder)
            throws Exception {
        binder.setAllowedFields(new String[]{"userName",
            "startDate",
            "endDate",
            "clientEngagementCode"});
    }

    /**
     * This method for search assign user to service
     *
     * @param request
     * @param searchManageServiceUserDto
     * @param redirectAttributes
     * @param model
     * @return ModelAndView
     */
    @RequestMapping(value = "/searchassignuserinternalview")
    public ModelAndView searchAssignUserToServiceInternalView(final HttpServletRequest request,
            @ModelAttribute(value = "searchManageServiceUserDto") ManageServiceUserDTO searchManageServiceUserDto,
            RedirectAttributes redirectAttributes, Model model) {

        logger.info(INFO_USER_SEARCH_ASSIGN_INTERNAL_VIEW);
//        logger.info("Start: searchAssignUserToServiceInternalView() to search assign user to service in internal view");
        ModelAndView modelView = new ModelAndView();
        HttpSession session = request.getSession();
        try {
            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return new ModelAndView("redirect:/logout.htm");
            }

            if (null != searchManageServiceUserDto.getUserName() && null != searchManageServiceUserDto.getStartDate()
                    && null != searchManageServiceUserDto.getEndDate()) {
                UserProfileDTO sessionUserDTO = new UserProfileDTO();
                if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                    sessionUserDTO = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                    MDC.put("user", sessionUserDTO.getEmail());
                    MDC.put("ip", sessionUserDTO.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(sessionUserDTO.getEmail(),sessionUserDTO.getUserMACAddress(), " User accessed searchAssignUserToServiceInternalView page"));
                    logger.trace(TRACE_USER_SEARCH_ASSIGN_INTERNAL_VIEW);
                    Set<String> permissionSet = sessionUserDTO.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU);
                    modelView.addObject("permissions", permissionSet);
                }
                if (permissionCheckHelper.checkUserPermissionNotAdmin(sessionUserDTO, PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU,
                        PermissionConstants.ADD_USER_TO_SERVICE) || permissionCheckHelper.checkUserPermissionNotAdmin(sessionUserDTO, PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU,
                                PermissionConstants.EDIT_USER_TO_SERVICE) || permissionCheckHelper.checkUserPermissionNotAdmin(sessionUserDTO, PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU,
                                PermissionConstants.VIEW_USER_TO_SERVICE) || permissionCheckHelper.checkUserPermissionNotAdmin(sessionUserDTO, PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU,
                                PermissionConstants.DELETE_USER_TO_SERVICE)) {
                    if ("".equalsIgnoreCase(searchManageServiceUserDto.getUserName()) && "".equalsIgnoreCase(searchManageServiceUserDto.getStartDate())
                            && "".equalsIgnoreCase(searchManageServiceUserDto.getEndDate())) {
                        redirectAttributes.addFlashAttribute("cei", encDec.encrypt(searchManageServiceUserDto.getClientEngagementCode()));
                        return new ModelAndView("redirect:manageserviceuser.htm");
                    }
                    ClientEngagementDTO engagementDto = manageEngagementsService.searchAssignedUsersForService(searchManageServiceUserDto, sessionUserDTO);
                    List<UserProfileDTO> internalUsers = manageEngagementsServiceImpl.fetchUsersByOrgIDAndUserTypeID(sessionUserDTO.getOrganizationKey(),
                            ApplicationConstants.DB_ORGANIZATIION_TYPE_INTERNAL, ApplicationConstants.DB_CONSTANT_USER_TYPE_ANALYST, new ArrayList<String>());
                    modelView.addObject("internalUsersList", internalUsers);
                    modelView.addObject("clientEngagementDTO", engagementDto);
                    modelView.addObject("searchUserName", searchManageServiceUserDto.getUserName());
                    modelView.addObject("searchStartDate", searchManageServiceUserDto.getStartDate());
                    modelView.addObject("searchEndDate", searchManageServiceUserDto.getEndDate());
                    modelView.addObject("searchUserTypeKey", searchManageServiceUserDto.getUserTypeKey());
                    modelView.setViewName("clientengagement/manageserviceuserinternalview");
                } else {
                    modelView.setViewName("redirect:/logout.htm");
                }
            } else {
                return new ModelAndView("redirect:/logout.htm");
            }
        } catch (AppException e) {
            logger.error("Exception occurred : searchAssignUserToServiceInternalView:" + e.getMessage());
            //ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            //return new ModelAndView("/exception", "exceptionBean", exception);
            return new ModelAndView("redirect:/logout.htm");
        }
        logger.info("End: searchAssignUserToServiceInternalView() to search assign user to service in internal view");
        return modelView;
    }

    /**
     * This method for search assign user to service
     *
     * @param request
     * @param searchManageServiceUserDto
     * @param redirectAttributes
     * @param model
     * @return ModelAndView
     */
    @RequestMapping(value = "/searchassignuserpartnerview")
    public ModelAndView searchAssignUserToServicePartnerView(final HttpServletRequest request,
            @ModelAttribute(value = "searchManageServiceUserDto") ManageServiceUserDTO searchManageServiceUserDto,
            RedirectAttributes redirectAttributes, Model model) {

        logger.info(INFO_USER_SEARCH_ASSIGNUSER_PATRNER_VIEW);
//        logger.info("Start: searchAssignUserToServicePartnerView() to search assign user to service in partner view");
        ModelAndView modelView = new ModelAndView();
        HttpSession session = request.getSession();
        try {
            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return new ModelAndView("redirect:/logout.htm");
            }

            if (null != searchManageServiceUserDto.getUserName() && null != searchManageServiceUserDto.getStartDate()
                    && null != searchManageServiceUserDto.getEndDate()) {
                UserProfileDTO sessionUserDTO = new UserProfileDTO();
                if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                    sessionUserDTO = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                    MDC.put("user", sessionUserDTO.getEmail());
                    MDC.put("ip", sessionUserDTO.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(sessionUserDTO.getEmail(),sessionUserDTO.getUserMACAddress(), " User accessed searchAssignUserToServicePartnerView page"));
                    logger.trace(TRACE_USER_SEARCH_ASSIGNUSER_PATRNER_VIEW);
                    Set<String> permissionSet = sessionUserDTO.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU);
                    modelView.addObject("permissions", permissionSet);
                }
                if (permissionCheckHelper.checkUserPermissionNotAdmin(sessionUserDTO, PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU,
                        PermissionConstants.ADD_USER_TO_SERVICE) || permissionCheckHelper.checkUserPermissionNotAdmin(sessionUserDTO, PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU,
                                PermissionConstants.EDIT_USER_TO_SERVICE) || permissionCheckHelper.checkUserPermissionNotAdmin(sessionUserDTO, PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU,
                                PermissionConstants.VIEW_USER_TO_SERVICE) || permissionCheckHelper.checkUserPermissionNotAdmin(sessionUserDTO, PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU,
                                PermissionConstants.DELETE_USER_TO_SERVICE)) {
                    if ("".equalsIgnoreCase(searchManageServiceUserDto.getUserName()) && "".equalsIgnoreCase(searchManageServiceUserDto.getStartDate())
                            && "".equalsIgnoreCase(searchManageServiceUserDto.getEndDate())) {
                        redirectAttributes.addFlashAttribute("cei", encDec.encrypt(searchManageServiceUserDto.getClientEngagementCode()));
                        return new ModelAndView("redirect:manageserviceuser.htm");
                    }
                    ClientEngagementDTO engagementDto = manageEngagementsService.searchAssignedUsersForService(searchManageServiceUserDto, sessionUserDTO);
                    List<UserProfileDTO> partnerUsers = manageEngagementsServiceImpl.fetchUsersByOrgIDAndUserTypeID(sessionUserDTO.getOrganizationKey(),
                            ApplicationConstants.DB_ORGANIZATIION_TYPE_PARTNER, ApplicationConstants.DB_CONSTANT_USER_TYPE_ANALYST, new ArrayList<String>());
                    modelView.addObject("partnerUsersList", partnerUsers);
                    modelView.addObject("clientEngagementDTO", engagementDto);
                    modelView.addObject("searchUserName", searchManageServiceUserDto.getUserName());
                    modelView.addObject("searchStartDate", searchManageServiceUserDto.getStartDate());
                    modelView.addObject("searchEndDate", searchManageServiceUserDto.getEndDate());
                    modelView.setViewName("clientengagement/manageserviceuserpartnerview");
                } else {
                    modelView.setViewName("redirect:/logout.htm");
                }
            } else {
                return new ModelAndView("redirect:/logout.htm");
            }
        } catch (AppException e) {
            logger.error("Exception occurred : searchAssignUserToServicePartnerView:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        logger.info("End: searchAssignUserToServicePartnerView() to search assign user to service in partner view");
        return modelView;
    }

    /**
     * This method for delete assigned user to service in internal / partner
     * view of Manage Users to Service page For reference defect number: DE10517
     *
     * @param request
     * @param manageServiceUserDto
     * @param redirectAttributes
     * @param model
     * @return ModelAndView
     */
    @RequestMapping(value = "/deleteassignedusertoservice")
    public ModelAndView deleteAssignedUserToService(final HttpServletRequest request,
            @ModelAttribute(value = "manageServiceUserDto") ManageServiceUserDTO manageServiceUserDto, RedirectAttributes redirectAttributes,
            Model model) {
        logger.info(INFO_USER_DELETE_ASSIGN_USER);
        ModelAndView modelView = new ModelAndView();
        HttpSession session = request.getSession();
        try {
            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return new ModelAndView("redirect:/logout.htm");
            }
            UserProfileDTO sessionUserDTO = new UserProfileDTO();
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                sessionUserDTO = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", sessionUserDTO.getEmail());
                MDC.put("ip", sessionUserDTO.getUserMACAddress());
                logger.trace(TRACE_USER_DELETE_ASSSIGN_USER);
            }
            int retVal = manageEngagementsService.deleteAssignedUserToService(manageServiceUserDto);
            if (retVal > 0) {
                redirectAttributes.addFlashAttribute("manageserviceusersuccessMessage",
                        "Successfully unassigned " + manageServiceUserDto.getUserName() + " from " + manageServiceUserDto.getServiceName());
                redirectAttributes.addFlashAttribute("manageserviceusersaveflag", ApplicationConstants.DELETE_FLAG);
            }
            redirectAttributes.addFlashAttribute("cei", encDec.encrypt(manageServiceUserDto.getClientEngagementCode()));
            modelView.setViewName("redirect:manageserviceuser.htm");
        } catch (AppException e) {
            logger.error("Exception occurred : deleteAssignedUserToService:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        logger.info("End: deleteAssignedUserToService() to delete assign user to service");
        return modelView;
    }
    
    @RequestMapping(value = "/delserviceusers")
    public String deleteServiceUsers(@ModelAttribute(value = "deluserobj") UserProfileDTO engUserObj,
            final HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {

        logger.info(INFO_DEL_SERVICE_USER);
        String retString = "";
        HttpSession session = request.getSession();
        UserProfileDTO sessionUserDTO = new UserProfileDTO();
        String cei = "";
        try {
             if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return "redirect:/logout.htm";
            }

            if (model.asMap().get("cei") != null && !model.asMap().get("cei").toString().equalsIgnoreCase("")) {
                cei = model.asMap().get("cei").toString();
            } else {
                cei = request.getParameter("cei");
            }

            sessionUserDTO = new UserProfileDTO();
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                sessionUserDTO = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", sessionUserDTO.getEmail());
                MDC.put("ip", sessionUserDTO.getUserMACAddress());
                logger.trace(TRACE_DEL_SERVICE_USER);
                Set<String> permissionSet = sessionUserDTO.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU);
                model.addAttribute("permissions", permissionSet);
            }
            if (cei != null && !cei.equalsIgnoreCase("")) {
                String decGroupKey = encDec.decrypt(cei);
                if (permissionCheckHelper.checkUserPermissionNotAdmin(sessionUserDTO, PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU,
                    PermissionConstants.VIEW_ENGAGEMENT) && !decGroupKey.isEmpty()) {
                   
                    manageEngagementsService.deleteServiceUsers(engUserObj.getUserProfileKey()+"");
                    redirectAttributes.addFlashAttribute("cei", cei);
                    UserProfileDTO emptyObj = new UserProfileDTO();
                    redirectAttributes.addFlashAttribute("enguserobj", emptyObj);
                    retString = "redirect:/manageengagementusers.htm";
                } else {
                   retString = "redirect:/logout.htm";
                }
            } else {
                retString = "redirect:/logout.htm";
            }
        } catch (AppException e) {
            logger.error("Exception occurred : deleteServiceUsers:" + e.getMessage());
             ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            retString = "/exception";
        }
        logger.info("End: deleteServiceUsers() to delete service users");
        return retString;
    }
}
