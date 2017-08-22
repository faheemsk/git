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
import com.optum.oss.dto.OrganizationDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.dto.Views;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.PermissionCheckHelper;
import com.optum.oss.service.impl.MasterLookUpServiceImpl;
import com.optum.oss.service.impl.OrganizationServiceImpl;
import com.optum.oss.util.CommonUtil;
import com.optum.oss.util.EncryptDecrypt;
import com.optum.oss.util.LoggerUtil;
import com.optum.oss.validators.OrganizationValidator;
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
 * @author rpanuganti
 */
@Controller
public class OrganizationController {

    private static final Logger logger = Logger.getLogger(OrganizationController.class);
    /*
     Start : Autowiring the required Class instances
     */
    @Resource(name = "myProperties")
    private Properties myProperties;

    @Autowired
    private OrganizationServiceImpl organizationService;

    @Autowired
    private MasterLookUpServiceImpl masterLookUpService;

    @Autowired
    private EncryptDecrypt encDec;

    @Autowired
    PermissionCheckHelper permissionCheckHelper;
    @Autowired
    private LoggerUtil auditLogger;

    @Autowired
    private OrganizationValidator orgValidator;
    @Autowired
    private CommonUtil commonUtil;
    /*
     START : LOG MESSAGES
     */
    private final String TRACE_USER_ORG_WORKLIST = "User accessed organizationWorkList";
    private final String TRACE_USER_ADD_ORG = "User accessed addOrganization";
    private final String TRACE_USER_SAVE_ORG = "User accessed saveOrganization";
    private final String TRACE_USER_FETCH_STATES = "User accessed fectchStates ";
    private final String TRACE_USER_GET_ORG_DETAILS = "User accessed getOrgDetails";
    private final String TRACE_USER_EDIT_ORG = "User accessed edit OrganizationPage";
    private final String TRACE_USER_VAL_ORG = "User accessed validate Organization";
    private final String TRACE_USER_VAL_AUTH_SOURCE = "User accessed validate Authoritative Source";

    /*
     END: LOG MESSAGES
     /*
     /*
     End : Autowiring the required Class instances
     */
    @RequestMapping(value = "/organizationWorkList")
    public ModelAndView organizationWorkList(final HttpServletRequest request,
            final HttpServletResponse response, Model model,
            RedirectAttributes redirectAttributes) throws AppException {
        try {

            ModelAndView modelView = new ModelAndView();
            HttpSession session = request.getSession();
            
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return new ModelAndView("redirect:/logout.htm");
            }
            
            UserProfileDTO userDto = new UserProfileDTO();
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User accessed organizationWorkList page"));
                logger.trace(TRACE_USER_ORG_WORKLIST);
                Set<String> permissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_CLIENT_INFO_MENU);
                modelView.addObject("permissionSet", permissionSet);
            }
            if (permissionCheckHelper.checkUserMenu(userDto, PermissionConstants.MANAGE_CLIENT_INFO_MENU)) {
                List<OrganizationDTO> organizationDetails = organizationService.organizationWorkList();
                modelView.addObject("organizationDetails", organizationDetails);
                modelView.setViewName("usermgmt/organizationWorklist");
            } else {
                modelView.setViewName("redirect:/logout.htm");
            }
            return modelView;
        } catch (Exception e) {
            logger.debug("Exception Occured while Executing the "
                    + "OrganizationController Controller :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
    }

    @RequestMapping(value = "/addOrganization")
    public String addOrganization(final HttpServletRequest request,
            final HttpServletResponse response, final Model model,
            RedirectAttributes redirectAttributes) {
        ModelAndView modelView = new ModelAndView();
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
        
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                UserProfileDTO userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User accessed addOrganization page"));
                logger.trace(TRACE_USER_ADD_ORG);
                if (permissionCheckHelper.checkUserPermission(userDto, PermissionConstants.MANAGE_CLIENT_INFO_MENU,
                        PermissionConstants.ADD_ORGANIZATION)) {

//                    OrganizationDTO organizationDetails = new OrganizationDTO();
                    OrganizationDTO organizationDetails;
                    if (model.containsAttribute("orgobj")) {
                        organizationDetails = (OrganizationDTO) model.asMap().get("orgobj");
                        model.addAttribute("orgobj", organizationDetails);
                        model.addAttribute("organizationDetails", organizationDetails);
                    } else {
                        organizationDetails = new OrganizationDTO();
                        model.addAttribute("organizationDetails", organizationDetails);
                    }

                    List<MasterLookUpDTO> organizationTypeList = masterLookUpService.fetchMasterLookUpEntitiesByEntityType(ApplicationConstants.DB_CONSTANT_ORGANIZATION_TYPE);
                    List<MasterLookUpDTO> organizationIndustryList = masterLookUpService.fetchMasterLookUpEntitiesByEntityType(ApplicationConstants.DB_CONSTANT_ORGANIZATION_INDUSTRY);
                    List<MasterLookUpDTO> authoritativeSourceList = masterLookUpService.fetchMasterLookUpEntitiesByEntityType(ApplicationConstants.DB_CONSTANT_SOURCE);
                    List<MasterLookUpDTO> rowStatusList = masterLookUpService.fetchMasterLookUpEntitiesByEntityType(ApplicationConstants.DB_CONSTANT_ROW_STATUS);
                    List<OrganizationDTO> organizationsList = organizationService.parentOrganizationList();
                    List countriesList = masterLookUpService.fetchCountries();
                    /* Start Fetching default country States*/
                    List<String> statesList = masterLookUpService.fetchStatesByCountry(ApplicationConstants.DEFAULT_COUNTRY);
                    organizationDetails.setCountryName(ApplicationConstants.DEFAULT_COUNTRY);
                    /* End Fetching default country States*/
                    model.addAttribute("statesList", statesList);
                    model.addAttribute("organizationTypeList", organizationTypeList);
                    model.addAttribute("organizationIndustryList", organizationIndustryList);
                    model.addAttribute("authoritativeSourceList", authoritativeSourceList);
                    model.addAttribute("rowStatusList", rowStatusList);
                    model.addAttribute("countriesList", countriesList);
                    model.addAttribute("organizationDetails", organizationDetails);
                    model.addAttribute("organizationsList", organizationsList);
                    return "usermgmt/addOrganization";
                } else {
                    modelView.setViewName("redirect:/logout.htm");
                }
            }

        } catch (Exception e) {
            logger.debug("Exception Occured while Executing the "
                    + "OrganizationController Controller:: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        return "usermgmt/addOrganization";
    }

    @InitBinder("orgobj")
    protected void saveOrganizationBinder(WebDataBinder binder)
            throws Exception {
        binder.setAllowedFields(new String[]{"encOrganizationKey",
            "encOrganizationName",
            "parentOrganizationKey",
            "organizationName",
            "organizationTypeKey",
            "organizationIndustryKey",
            "countryName",
            "streetAddress1",
            "streetAddress2",
            "cityName",
            "stateName",
            "postalCode",
            "organizationDescription",
            "rowStatusKey",
            "deactiveReason",
            "sourceKeyArray",
            "sourceClientIDArray",
			"organizationType"
        });
    }

    @RequestMapping(value = "/saveOrganization", method = RequestMethod.POST)
    public String saveOrganization(@ModelAttribute(value = "orgobj") OrganizationDTO organizationDTO,
            final HttpServletRequest request, Model model,
            final HttpServletResponse response, RedirectAttributes redirectAttributes, BindingResult bindResult) throws AppException {
        long orgID = 0;
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
        
            /* Validation for organization Details */
            orgValidator.validate(organizationDTO, bindResult);
            if (bindResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("orgobj", organizationDTO);
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.orgobj", bindResult);
                if (!StringUtils.isEmpty(organizationDTO.getEncOrganizationKey())) {
                    redirectAttributes.addAttribute("orgID", organizationDTO.getEncOrganizationKey());
                    return "redirect:/editOrganizationPage.htm";
                } else {
                    return "redirect:/addOrganization.htm";
                }
            }

            if (organizationDTO.getEncOrganizationKey() != null && !organizationDTO.getEncOrganizationKey().equalsIgnoreCase("")) {
                orgID = Long.parseLong(encDec.decrypt(organizationDTO.getEncOrganizationKey()));
                organizationDTO.setOrganizationKey(orgID);
            }
            UserProfileDTO userSessionDto = new UserProfileDTO();
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                userSessionDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
            }
            MDC.put("user", userSessionDto.getEmail());
            MDC.put("ip", userSessionDto.getUserMACAddress());
//            logger.trace(auditLogger.prepareLog(userSessionDto.getEmail(),userSessionDto.getUserMACAddress(), " User accessed saveOrganization page"));

            logger.trace(TRACE_USER_SAVE_ORG);
            //if (userSessionDto != null) {
            organizationDTO.setCreatedByUserID(userSessionDto.getUserProfileKey());
            //}
            if (organizationDTO.getOrganizationKey() > 0) {
                /* Update Organization   */
                organizationService.updateOrganizationDetails(organizationDTO);
                redirectAttributes.addFlashAttribute("statusMessage", myProperties.getProperty("organization.update-success"));

            } else {
                /* Save Organization */
                organizationService.saveOrganizationDetails(organizationDTO,userSessionDto);
                redirectAttributes.addFlashAttribute("statusMessage", myProperties.getProperty("organization.add-success"));

            }
            return "redirect:/organizationWorkList.htm";
        } catch (Exception e) {
            logger.debug("Exception Occured while Executing the "
                    + "OrganizationController Controller:: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
//            return new ModelAndView("/exception", "exceptionBean", exception);
            return "/exception";
        }
    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/fectchStates")
    @ResponseBody
    public List<String> fectchStatesByCountry(@RequestParam("country") String country, final HttpServletRequest request,
            final HttpServletResponse response) throws AppException {
        List<String> countriesList = null;
        try {
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                UserProfileDTO userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User Accessed fectchStatesByCountry page"));
                logger.trace(TRACE_USER_FETCH_STATES);
            }
            countriesList = masterLookUpService.fetchStatesByCountry(country);
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "OrganizationController Controller :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);

        }
        return countriesList;
    }

    @RequestMapping(value = "/getOrgDetails")
    public ModelAndView getOrgDetailsById(
            final HttpServletRequest request,
            final HttpServletResponse response, 
            Model model, RedirectAttributes redirectAttributes) {
        ModelAndView modelView = null;
        String decOrgID;
        long orgID = 0;
        String encOrgId="";
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return new ModelAndView("redirect:/logout.htm");
            }
        
            modelView = new ModelAndView();
            if (request.getParameter("orgID") != null && !request.getParameter("orgID").equals("")) {
                encOrgId = request.getParameter("orgID");
            } else {
                /* if organization id is null or empty, Redirecting to the l page */
                modelView.setViewName("redirect:/logout.htm");
                return modelView;
            }
       
            if (encOrgId != null && !encOrgId.equalsIgnoreCase("")) {
                decOrgID = encDec.decrypt(encOrgId);
                orgID = Long.parseLong(decOrgID);
            }
            modelView = new ModelAndView();
            OrganizationDTO organizationDetails = organizationService.getOrganizationDetailByOrgID(orgID);
            UserProfileDTO userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
            MDC.put("user", userDto.getEmail());
            MDC.put("ip", userDto.getUserMACAddress());
//            logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User accessed getOrgDetailsById page"));
            logger.trace(TRACE_USER_GET_ORG_DETAILS);
            if (permissionCheckHelper.checkUserPermission(userDto, PermissionConstants.MANAGE_CLIENT_INFO_MENU,
                    PermissionConstants.VIEW_ORGANIZATION)) {
                modelView.addObject("organizationDetails", organizationDetails);
                modelView.setViewName("usermgmt/viewOrganizationDetails");
            } else {
                modelView.setViewName("redirect:/logout.htm");
            }

        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "OrganizationController Controller:: " + e.getMessage());

            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/editOrganizationPage")
    public String editOrganizationPage(
            final HttpServletRequest request,
            final HttpServletResponse response, 
            final Model model,
            RedirectAttributes redirectAttributes) {
        String decOrgID;
        long orgID = 0;
        String retString = "";
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            
            String encOrgId = "";
            if (request.getParameter("orgID") != null) {
                encOrgId = request.getParameter("orgID");
            } else if (request.getParameter("encOrganizationKey") != null) {
                encOrgId = request.getParameter("encOrganizationKey");
            } else {
                if (model.containsAttribute("orgID")) {
                    encOrgId = (String) model.asMap().get("orgID");
                }
            }
            if (encOrgId != null && !encOrgId.equalsIgnoreCase("")) {
                decOrgID = encDec.decrypt(encOrgId);
                orgID = Long.parseLong(decOrgID);
            } else {
                return "redirect:/logout.htm";
            }
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                UserProfileDTO userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User accessed editOrganization page"));
                logger.trace(TRACE_USER_EDIT_ORG);
                if (permissionCheckHelper.checkUserPermission(userDto, PermissionConstants.MANAGE_CLIENT_INFO_MENU,
                        PermissionConstants.UPDATE_ORGANIZATION)) {

                    List<MasterLookUpDTO> organizationTypeList = masterLookUpService.fetchMasterLookUpEntitiesByEntityType(ApplicationConstants.DB_CONSTANT_ORGANIZATION_TYPE);
                    List<MasterLookUpDTO> organizationIndustryList = masterLookUpService.fetchMasterLookUpEntitiesByEntityType(ApplicationConstants.DB_CONSTANT_ORGANIZATION_INDUSTRY);
                    List<MasterLookUpDTO> authoritativeSourceList = masterLookUpService.fetchMasterLookUpEntitiesByEntityType(ApplicationConstants.DB_CONSTANT_SOURCE);
                    List<MasterLookUpDTO> rowStatusList = masterLookUpService.fetchMasterLookUpEntitiesByEntityType(ApplicationConstants.DB_CONSTANT_ROW_STATUS);
                    List countriesList = masterLookUpService.fetchCountries();
                    List<OrganizationDTO> organizationsList = organizationService.parentOrganizationList();
                    model.addAttribute("organizationTypeList", organizationTypeList);
                    model.addAttribute("organizationIndustryList", organizationIndustryList);
                    model.addAttribute("authoritativeSourceList", authoritativeSourceList);
                    model.addAttribute("rowStatusList", rowStatusList);
                    model.addAttribute("countriesList", countriesList);
                    model.addAttribute("organizationsList", organizationsList);
                    /* Fetching organization Details */
                    OrganizationDTO organizationDetails = null;
                    if (model.containsAttribute("orgobj")) {
                        organizationDetails = (OrganizationDTO) model.asMap().get("orgobj");
                        model.addAttribute("orgobj", organizationDetails);
                        model.addAttribute("organizationDetails", organizationDetails);
                    } else {
                        organizationDetails = organizationService.getOrganizationDetailByOrgID(orgID);
                        model.addAttribute("organizationDetails", organizationDetails);
                    }
                    model.addAttribute("organizationDetails", organizationDetails);
                    if (organizationDetails != null) {
                        List<String> statesList = masterLookUpService.fetchStatesByCountry(organizationDetails.getCountryName());
                        model.addAttribute("statesList", statesList);
                    }
                    retString = "usermgmt/addOrganization";
                } else {
                    retString = "redirect:/logout.htm";
                }
            }

            /* Fetching States by country */
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "OrganizationController Controller :: " + e.getMessage());

            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            retString = "/exception";
        }

        return retString;
    }

    @RequestMapping(value = "/validateOrganization")
    public @ResponseBody
    String validateOrganization(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("organization") String organization) {
        String message = "";
        try {
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                UserProfileDTO userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User validateOrganization page"));
                logger.trace(TRACE_USER_VAL_ORG);
            }
            int retVal = organizationService.validateOrganization(organization);
            if (retVal > 0) {
                message = myProperties.getProperty("organization.validate-message");
            } else {
                message = "No";
            }
        } catch (Exception e) {
            logger.debug("Exceptionoccured : validateOrganization : " + e.getMessage());
        }
        return message;
    }

    @RequestMapping(value = "/validateAuthoritativeSource")
    public @ResponseBody
    String validateAuthoritativeSource(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("authoritativeSource") String authoritativeSource) {
        String message = "";
        try {
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                UserProfileDTO userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User validateAuthoritativeSource page"));
                logger.trace(TRACE_USER_VAL_AUTH_SOURCE);
            }
            int retVal = organizationService.validateAuthoritativeSource(authoritativeSource);
            if (retVal > 0) {
                message = myProperties.getProperty("organization.authoritativesource-validate-message");
            } else {
                message = "No";
            }
        } catch (Exception e) {
            logger.debug("Exceptionoccured : validateAuthoritativeSource : " + e.getMessage());
        }
        return message;
    }

 @RequestMapping(value = "/onBoardingProcess")
    public String onBoardingProcess(final HttpServletRequest request,
            final HttpServletResponse response, Model model,
            RedirectAttributes redirectAttributes) throws AppException {
        long orgID=0;
        try {

            HttpSession session = request.getSession();
            
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            
            UserProfileDTO userDto = new UserProfileDTO();
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
            }
           
            if (request.getParameter("orgID") != null && !request.getParameter("orgID").equals("")) {
                orgID = Long.parseLong(request.getParameter("orgID"));
            }
            
            OrganizationDTO organizationDetails = organizationService.getOrganizationDetailByOrgID(orgID);
            organizationService.onBoardingProcess(organizationDetails,userDto);
            return "redirect:/organizationWorkList.htm";
        } catch (Exception e) {
            logger.debug("Exception Occured while Executing the "
                    + "onBoardingProcess Controller :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
    }
}
