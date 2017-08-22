/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.controller;

/**
 *
 * @author rpanuganti
 */
import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.constants.PermissionConstants;
import com.optum.oss.constants.SessionConstants;
import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.dto.ExceptionDTO;
import com.optum.oss.dto.ManageServiceUserDTO;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.OrganizationDTO;
import com.optum.oss.dto.RiskRegisterDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.dto.VulnerabilityDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.PermissionCheckHelper;
import com.optum.oss.model.SummaryDropDownModel;
import com.optum.oss.service.impl.AnalystValidationServiceImpl;
import com.optum.oss.service.impl.ManageEngagementsServiceImpl;
import com.optum.oss.service.impl.MasterLookUpServiceImpl;
import com.optum.oss.service.impl.OrganizationServiceImpl;
import com.optum.oss.service.impl.RemediationProjectManagerServiceImpl;
import com.optum.oss.service.impl.RemediationServiceImpl;
import com.optum.oss.service.impl.ReportsAndDashboardServiceImpl;
import com.optum.oss.service.impl.RiskRegistryServiceImpl;
import com.optum.oss.util.CommonUtil;
import com.optum.oss.util.DateUtil;
import com.optum.oss.util.EncryptDecrypt;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author sbhagavatula
 */
@Controller
public class RiskRegistryController {

    private static final Logger logger = Logger.getLogger(RemediationController.class);

    @Resource(name = "myProperties")
    private Properties myProperties;
    /*
     *Begin: Autowiring the required Class instances
     */
    @Autowired
    private ManageEngagementsServiceImpl manageEngagementsServiceImpl;
    @Autowired
    private AnalystValidationServiceImpl analystValidationService;
    @Autowired
    private OrganizationServiceImpl organizationService;
    @Autowired
    private RiskRegistryServiceImpl riskRegistryService;
    @Autowired
    private EncryptDecrypt encDec;
    @Autowired
    PermissionCheckHelper permissionCheckHelper;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private MasterLookUpServiceImpl masterLookUpServiceImpl;
    @Autowired
    private RemediationServiceImpl remediationService;
    @Autowired
    private ReportsAndDashboardServiceImpl reportsAndDashboardService;
    @Autowired
    private DateUtil dateUtil;
    /*
     * End: Autowiring the required Class instances
     */
    /*
     START : LOG MESSAGES
     */
    private final String TRACE_USER_RISK_REGISTRY_MODULE_WORKLIST = "User accessed Risk Registry module worklist";
    private final String TRACE_USER_RISK_REGISTRY_MODULE_REGISTRY_DETAILS_UPDATE = "Risk Registry Details Updation";
    private final String TRACE_USER_RISK_REGISTRY_MODULE_REGISTRY_ITEMS_REMOVE = "Risk Registry Details Updation";
    private final String TRACE_USER_RISK_REGISTRY_MODULE_REGISTRY_SUMMARY_PAGE = "User accessed Risk Registry Summary Page";
    private final String TRACE_USER_RISK_REGISTRY_MODULE_REGISTRY_DETAILS_PAGE = "User accessed Risk Registry Details Page";
    /*
     END : LOG MESSAGES
     */

    @RequestMapping(value = "/registryList")
    public ModelAndView registryWorkList(HttpServletRequest request, HttpServletResponse response, Model model,
            RedirectAttributes redirectAttributes) {

        logger.info(TRACE_USER_RISK_REGISTRY_MODULE_WORKLIST);
        ModelAndView modelView = new ModelAndView();
        UserProfileDTO userDto = new UserProfileDTO();
        Set<String> riskRegistryPermissionSet = null;
        ManageServiceUserDTO manageServiceUserDTO = new ManageServiceUserDTO();
        String flag = "";
        try {
            HttpSession session = request.getSession();

            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return new ModelAndView("redirect:/logout.htm");
            }
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(TRACE_USER_RISK_REGISTRY_MODULE_WORKLIST);

                riskRegistryPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.RISK_REGISTRY_MODULE);
                if (riskRegistryPermissionSet.contains(PermissionConstants.ACCEPT_REGISTRY)) {
                    flag = ApplicationConstants.DB_CONSTANT_REGISTRY_OWNER_FLAG;
                } else {
                    flag = ApplicationConstants.DB_CONSTANT_REMEDIATION_ANALYST_FLAG;
                }
                modelView.addObject("permissions", riskRegistryPermissionSet);
            }
             manageServiceUserDTO.setUserFlag(flag);
            OrganizationDTO organizationDetails = organizationService.getOrganizationDetailByOrgID(userDto.getOrganizationKey());
            if (organizationDetails != null && organizationDetails.getSchemaName() != null) {
                manageServiceUserDTO.setOrgSchema(organizationDetails.getSchemaName());
            } else {
                manageServiceUserDTO.setOrgSchema("");
            }

            if (permissionCheckHelper.checkUserSubMenuNotAdmin(userDto, PermissionConstants.RISK_REGISTRY_MODULE)) {
                List<ManageServiceUserDTO> remediationWorklist = riskRegistryService.fetchRiskRegistryWorklist(userDto, manageServiceUserDTO);
                Map remediationMap = analystValidationService.engagementServicesListToMapConversion(remediationWorklist);
                modelView.addObject("engagementMap", remediationMap.get("engagementMap"));
                modelView.addObject("serviceMap", remediationMap.get("serviceMap"));
                modelView.addObject("organizationServieCount", remediationMap.get("organizationServieCount"));
                modelView.addObject("statusMap", remediationMap.get("statusMap"));
                modelView.setViewName("riskregistry/riskregistry");
            } else {
                modelView.setViewName("redirect:/logout.htm");
            }
        } catch (AppException e) {
            logger.error("Exception occurred : RiskRegistryController: registryWorkList:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        logger.info("End: RemediationController: registryWorkList()");
        return modelView;
    }

    @RequestMapping(value = "/searchRegistryList")
    public ModelAndView searchRegistryWorkList(HttpServletRequest request, HttpServletResponse response, Model model,
            @ModelAttribute(value = "searchRegistryList") ManageServiceUserDTO remediationData, RedirectAttributes redirectAttributes) {

        logger.info(TRACE_USER_RISK_REGISTRY_MODULE_WORKLIST);
        ModelAndView modelView = new ModelAndView();
        UserProfileDTO userDto = new UserProfileDTO();
        Set<String> riskRegistryPermissionSet = null;
        ManageServiceUserDTO manageServiceUserDTO = new ManageServiceUserDTO();
         String flag = "";
        try {
            HttpSession session = request.getSession();

//            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
//                return new ModelAndView("redirect:/logout.htm");
//            }
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(TRACE_USER_RISK_REGISTRY_MODULE_WORKLIST);

                riskRegistryPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.RISK_REGISTRY_MODULE);
                if (riskRegistryPermissionSet.contains(PermissionConstants.ACCEPT_REGISTRY)) {
                    flag = ApplicationConstants.DB_CONSTANT_REGISTRY_OWNER_FLAG;
                } else {
                    flag = ApplicationConstants.DB_CONSTANT_REMEDIATION_ANALYST_FLAG;
                }
                modelView.addObject("permissions", riskRegistryPermissionSet);
            }
                    remediationData.setUserFlag(flag);
            OrganizationDTO organizationDetails = organizationService.getOrganizationDetailByOrgID(userDto.getOrganizationKey());
            if (organizationDetails != null && organizationDetails.getSchemaName() != null) {
                remediationData.setOrgSchema(organizationDetails.getSchemaName());
            } else {
                remediationData.setOrgSchema("");
            }
            if (permissionCheckHelper.checkUserSubMenuNotAdmin(userDto, PermissionConstants.RISK_REGISTRY_MODULE)) {
                List<ManageServiceUserDTO> remediationWorklist = riskRegistryService.fetchRiskRegistryWorklist(userDto, remediationData);
                Map remediationMap = analystValidationService.engagementServicesListToMapConversion(remediationWorklist);
                modelView.addObject("engagementMap", remediationMap.get("engagementMap"));
                modelView.addObject("serviceMap", remediationMap.get("serviceMap"));
                modelView.addObject("organizationServieCount", remediationMap.get("organizationServieCount"));
                modelView.addObject("statusMap", remediationMap.get("statusMap"));
                modelView.addObject("remediationData", remediationData);
                modelView.setViewName("riskregistry/riskregistry");
            } else {
                modelView.setViewName("redirect:/logout.htm");
            }
        } catch (AppException e) {
            logger.error("Exception occurred : RiskRegistryController: searchRegistryWorkList:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        logger.info("End: RemediationController: searchRegistryWorkList()");
        return modelView;
    }

    /**
     *
     * @param request
     * @param response
     * @param model
     * @param redirectAttributes
     * @param redirectEngagementCode
     * @return
     */
    @RequestMapping(value = "/riskRegistrySummary")
    public String riskRegistrySummary(HttpServletRequest request, HttpServletResponse response,
            Model model, RedirectAttributes redirectAttributes) {
        logger.info("Start: RiskRegistryController: riskRegistrySummary()");
        UserProfileDTO userDto = new UserProfileDTO();
        Set<String> riskRegistryPermissionSet = null;
        String engagementCode = "";
        String flag="";
        try {
            HttpSession session = request.getSession();
            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return "redirect:/logout.htm";
            }
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(TRACE_USER_RISK_REGISTRY_MODULE_REGISTRY_SUMMARY_PAGE);
                riskRegistryPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.RISK_REGISTRY_MODULE);
                model.addAttribute("permissions", riskRegistryPermissionSet);
                       if (riskRegistryPermissionSet.contains(PermissionConstants.ACCEPT_REGISTRY)) {
                    flag = ApplicationConstants.DB_CONSTANT_REGISTRY_OWNER_FLAG;
                } else {
                    flag = ApplicationConstants.DB_CONSTANT_REMEDIATION_ANALYST_FLAG;
                }
            }
            if (request.getParameter("engkey") != null && !StringUtils.isEmpty(request.getParameter("engkey"))) {
                engagementCode = request.getParameter("engkey");
            } else if (request.getParameter("redengkey") != null && !StringUtils.isEmpty(request.getParameter("redengkey"))) {
                engagementCode = request.getParameter("redengkey");
            }
            if (engagementCode != null && !StringUtils.isEmpty(engagementCode)) {
                if (permissionCheckHelper.checkUserSubMenuNotAdmin(userDto, PermissionConstants.RISK_REGISTRY_MODULE)) {
                    String decEngagementCode = "";
                    if (engagementCode != null && !engagementCode.equalsIgnoreCase("")) {
                        decEngagementCode = encDec.decrypt(engagementCode);
                    }
                    ClientEngagementDTO engagementDTO = manageEngagementsServiceImpl.viewEngagementByEngmtKey(decEngagementCode);
                    model.addAttribute("engagementDTO", engagementDTO);

                    String orgSchema = reportsAndDashboardService.getSchemaNameByEngKey(engagementCode);

                    List<RiskRegisterDTO> riskRegistryList = riskRegistryService.riskRegistryList(decEngagementCode, orgSchema, userDto.getUserProfileKey(),flag);
                    model.addAttribute("riskRegistryList", riskRegistryList);
                } else {
                    return "redirect:/logout.htm";
                }
            } else {
                return "redirect:/logout.htm";
            }
        } catch (AppException e) {
            logger.error("Exception occurred : RiskRegistryController: riskRegistrySummary:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        logger.info("End: RiskRegistryController: riskRegistrySummary()");
        return "riskregistry/riskregistrysummary";
    }

    /**
     *
     * @param request
     * @param response
     * @param model
     * @param redirectAttributes
     * @param redirectEngagementCode
     * @return
     */
    @RequestMapping(value = "/riskRegistryDetails")
    public String riskRegistryDetails(HttpServletRequest request, HttpServletResponse response,
            Model model, RedirectAttributes redirectAttributes) {
        logger.info("Start: RiskRegistryController: riskRegistryDetails()");
        UserProfileDTO userDto = new UserProfileDTO();
        Set<String> riskRegistryPermissionSet = null;
        RiskRegisterDTO riskRegisterDTO = new RiskRegisterDTO();
        String engagementCode = "";
        String decEngagementCode = "";
        int riskRegisterID = 0;
        try {
            HttpSession session = request.getSession();
            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return "redirect:/logout.htm";
            }
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(TRACE_USER_RISK_REGISTRY_MODULE_REGISTRY_DETAILS_PAGE);
                riskRegistryPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.RISK_REGISTRY_MODULE);
                model.addAttribute("permissions", riskRegistryPermissionSet);
            }
            if (request.getParameter("engkey") != null && !StringUtils.isEmpty(request.getParameter("engkey"))) {
                engagementCode = request.getParameter("engkey");
            } else if (model.containsAttribute("engkey")) {
                engagementCode = (String) model.asMap().get("engkey");
            }
            if (request.getParameter("riskRegisterID") != null && !StringUtils.isEmpty(request.getParameter("riskRegisterID"))) {
                riskRegisterID = Integer.parseInt(request.getParameter("riskRegisterID"));
            } else if (model.containsAttribute("riskRegisterID")) {
                riskRegisterID = (Integer) model.asMap().get("riskRegisterID");
            }
            if (engagementCode != null && !StringUtils.isEmpty(engagementCode)) {
                if (permissionCheckHelper.checkUserSubMenuNotAdmin(userDto, PermissionConstants.RISK_REGISTRY_MODULE)) {

                    if (engagementCode != null && !engagementCode.equalsIgnoreCase("")) {
                        decEngagementCode = encDec.decrypt(engagementCode);
                    }
                    ClientEngagementDTO engagementDTO = manageEngagementsServiceImpl.viewEngagementByEngmtKey(decEngagementCode);
                    model.addAttribute("engagementDTO", engagementDTO);
                    String orgSchema = reportsAndDashboardService.getSchemaNameByEngKey(engagementCode);

                    RiskRegisterDTO riskRegistryDetails = riskRegistryService.riskRegistryDetailsById(orgSchema, riskRegisterID);
                    List<MasterLookUpDTO> riskResponseList = riskRegistryService.fetchRiskResponseList();
                    model.addAttribute("riskRegistryDetails", riskRegistryDetails);
                    model.addAttribute("riskResponseList", riskResponseList);

                    //CLIENT USERS BASED ON CLIENT ORGANIZATION
                    List<UserProfileDTO> clientUsers = remediationService.remediationOwnerUsers(ApplicationConstants.RISK_OWNER, decEngagementCode);
                    model.addAttribute("clientUsers", clientUsers);

                    //Severity dropdown list
                    List<SummaryDropDownModel> severityList = analystValidationService.analystSeverityList();
                    model.addAttribute("severityList", severityList);

                    List<VulnerabilityDTO> findingsList = riskRegistryService.riskRegistryFindingsListByID(riskRegisterID, orgSchema);
                    model.addAttribute("findingsList", findingsList);
                } else {
                    return "redirect:/logout.htm";
                }
            } else {
                return "redirect:/logout.htm";
            }
        } catch (AppException e) {
            logger.error("Exception occurred : RiskRegistryController: riskRegistryDetails:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        logger.info("End: RiskRegistryController: riskRegistryDetails()");
        return "riskregistry/riskregistrydetails";
    }

    @RequestMapping(value = "/updateRiskRegistryDetails", method = RequestMethod.POST)
    public String updateRiskRegistryDetails(@ModelAttribute(value = "riskRegisterObj") RiskRegisterDTO riskRegistryDTO,
            final HttpServletRequest request, Model model,
            final HttpServletResponse response, RedirectAttributes redirectAttributes) throws AppException {
        ModelAndView modelView = new ModelAndView();
        UserProfileDTO userDto = null;
        Set<String> riskRegistryPermissionSet = null;
        try {
            HttpSession session = request.getSession();

            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return "redirect:/logout.htm";
            }
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(TRACE_USER_RISK_REGISTRY_MODULE_REGISTRY_DETAILS_UPDATE);

                riskRegistryPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.RISK_REGISTRY_MODULE);
                modelView.addObject("permissions", riskRegistryPermissionSet);
            } else {
                return "redirect:/logout.htm";
            }

            String engagementCode = "";
            if (request.getParameter("engkey") != null && !StringUtils.isEmpty(request.getParameter("engkey"))) {
                engagementCode = request.getParameter("engkey");
            }
            String orgSchema = reportsAndDashboardService.getSchemaNameByEngKey(engagementCode);
            riskRegistryDTO.setOrgSchema(orgSchema);
            if (permissionCheckHelper.checkUserSubMenuNotAdmin(userDto, PermissionConstants.RISK_REGISTRY_MODULE)) {
                riskRegistryService.updateRiskRegistryDetails(riskRegistryDTO, userDto);

                redirectAttributes.addFlashAttribute("statusMessage", myProperties.getProperty("riksregistry.detailsupdate.success-message"));
                redirectAttributes.addFlashAttribute("engkey", engagementCode);
                redirectAttributes.addFlashAttribute("riskRegisterID", riskRegistryDTO.getRiskRegisterID());
            } else {
                return "redirect:/logout.htm";
            }
            return "redirect:/riskRegistryDetails.htm";
        } catch (Exception e) {
            logger.debug("Exception Occured while Executing the "
                    + "RiskRegistryController Controller:: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return "/exception";
        }
    }

    @RequestMapping(value = "/updateriskAcceptanceDetails", method = RequestMethod.POST)
    public String updateriskAcceptanceDetails(@ModelAttribute(value = "riskRegisterObj") RiskRegisterDTO riskRegistryDTO,
            final HttpServletRequest request, Model model,
            final HttpServletResponse response, RedirectAttributes redirectAttributes) throws AppException {
        ModelAndView modelView = new ModelAndView();
        UserProfileDTO userDto = null;
        Set<String> riskRegistryPermissionSet = null;
        try {
            HttpSession session = request.getSession();

            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return "redirect:/logout.htm";
            }
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(TRACE_USER_RISK_REGISTRY_MODULE_REGISTRY_DETAILS_UPDATE);

                riskRegistryPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.RISK_REGISTRY_MODULE);
                modelView.addObject("permissions", riskRegistryPermissionSet);
            } else {
                return "redirect:/logout.htm";
            }

            String engagementCode = "";
            if (request.getParameter("engkey") != null && !StringUtils.isEmpty(request.getParameter("engkey"))) {
                engagementCode = request.getParameter("engkey");
            }
            String orgSchema = reportsAndDashboardService.getSchemaNameByEngKey(engagementCode);
            riskRegistryDTO.setOrgSchema(orgSchema);
            if (permissionCheckHelper.checkUserSubMenuNotAdmin(userDto, PermissionConstants.RISK_REGISTRY_MODULE)) {

                riskRegistryService.updateRiskAcceptanceDetails(riskRegistryDTO, userDto);
                riskRegistryService.updateRiskRegistryItemsByRiskRegistryResponse(riskRegistryDTO, userDto);
                
//                riskRegistryService.updateFindingCountRegistryAnsPlan(riskRegistryDTO.getRiskRegisterID(), 0, userDto.getUserProfileKey(), orgSchema, ApplicationConstants.DB_CONSTANT_REGISTRY_FINDING_STATUS_UPDATE);

                redirectAttributes.addFlashAttribute("statusMessage", myProperties.getProperty("riksregistry.detailsupdate.success-message"));
                redirectAttributes.addFlashAttribute("engkey", engagementCode);
                redirectAttributes.addFlashAttribute("riskRegisterID", riskRegistryDTO.getRiskRegisterID());
            } else {
                return "redirect:/logout.htm";
            }
            return "redirect:/riskRegistryDetails.htm";
        } catch (Exception e) {
            logger.debug("Exception Occured while Executing the "
                    + "RiskRegistryController Controller:: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return "/exception";
        }
    }

    @RequestMapping(value = "/removeRegistryItems", method = RequestMethod.POST)
    public String removeRiskRegistryItems(@ModelAttribute(value = "riskRegisterObj") RiskRegisterDTO riskRegistryDTO,
            final HttpServletRequest request, Model model,
            final HttpServletResponse response, RedirectAttributes redirectAttributes) throws AppException {
        ModelAndView modelView = new ModelAndView();
        UserProfileDTO userDto = null;
        Set<String> riskRegistryPermissionSet = null;
        String engagementCode = "";
        int findingCount=0;
        try {
            HttpSession session = request.getSession();

            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return "redirect:/logout.htm";
            }
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(TRACE_USER_RISK_REGISTRY_MODULE_REGISTRY_DETAILS_UPDATE);
                riskRegistryPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.RISK_REGISTRY_MODULE);
                modelView.addObject("permissions", riskRegistryPermissionSet);
            } else {
                return "redirect:/logout.htm";
            }
            if (permissionCheckHelper.checkUserSubMenuNotAdmin(userDto, PermissionConstants.RISK_REGISTRY_MODULE)) {
                if (request.getParameter("engkey") != null && !StringUtils.isEmpty(request.getParameter("engkey"))) {
                    engagementCode = request.getParameter("engkey");
                }
                String orgSchema = reportsAndDashboardService.getSchemaNameByEngKey(engagementCode);

                if (riskRegistryDTO.getRiskItemIds() != null) {
                    riskRegistryService.removeRiskRegistryItems(riskRegistryDTO.getRiskItemIds(), riskRegistryDTO.getRiskRegisterID(), orgSchema,userDto);
                           List<VulnerabilityDTO> findingsList = riskRegistryService.riskRegistryFindingsListByID(riskRegistryDTO.getRiskRegisterID(), orgSchema);
               
                /* Updating Finding Count in Registry */
                if (findingsList != null) {
                    findingCount = findingsList.size();
                }
                   riskRegistryService.updateFindingCountRegistryAnsPlan(riskRegistryDTO.getRiskRegisterID(), findingCount, userDto.getUserProfileKey(), orgSchema, ApplicationConstants.DB_CONSTANT_REGISTRY_FINDING_COUNT_UPDATE);
                }
                redirectAttributes.addFlashAttribute("engkey", engagementCode);
                redirectAttributes.addFlashAttribute("riskRegisterID", riskRegistryDTO.getRiskRegisterID());
            } else {
                return "redirect:/logout.htm";

            }
            return "redirect:/riskRegistryDetails.htm";
        } catch (Exception e) {
            logger.debug("Exception Occured while Executing the "
                    + "RiskRegistryController removeRegistryItems():: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return "/exception";
        }
    }

    @RequestMapping(value = "/insPlanRegItems")
    public String insertPlanAndRegistryItems(@ModelAttribute(value = "riskRegisterObj") RiskRegisterDTO riskRegistryDTO,
            final HttpServletRequest request, Model model,
            final HttpServletResponse response, RedirectAttributes redirectAttributes) throws AppException {
        ModelAndView modelView = new ModelAndView();
        UserProfileDTO userDto = null;
        Set<String> riskRegistryPermissionSet = null;
        String engagementCode = "";
        String planID = "";
        int riskID = 0;
        int findingCount=0;
        try {
            HttpSession session = request.getSession();

            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return "redirect:/logout.htm";
            }
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(TRACE_USER_RISK_REGISTRY_MODULE_REGISTRY_DETAILS_UPDATE);
                riskRegistryPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.RISK_REGISTRY_MODULE);
                modelView.addObject("permissions", riskRegistryPermissionSet);
            } else {
                return "redirect:/logout.htm";
            }

            if (request.getParameter("redengkey") != null && !StringUtils.isEmpty(request.getParameter("redengkey"))) {
                engagementCode = request.getParameter("redengkey");
            }
            if (permissionCheckHelper.checkUserSubMenuNotAdmin(userDto, PermissionConstants.RISK_REGISTRY_MODULE)) {
                if (riskRegistryDTO != null) {
                    if (!StringUtils.isEmpty(riskRegistryDTO.getEncRiskRegistryId())) {
                        riskID = Integer.parseInt(encDec.decrypt(riskRegistryDTO.getEncRiskRegistryId()));
                    }
                    if (!StringUtils.isEmpty(riskRegistryDTO.getEncPlanID())) {
                        planID = encDec.decrypt(riskRegistryDTO.getEncPlanID());
                    }
                }
                String orgSchema = reportsAndDashboardService.getSchemaNameByEngKey(engagementCode);
                riskRegistryDTO.setPlanID(planID);
                riskRegistryDTO.setRiskRegisterID(riskID);
                riskRegistryDTO.setOrgSchema(orgSchema);

//                riskRegistryService.saveRemediatePlanFindings(riskRegistryDTO, userDto);
                if(riskRegistryDTO.getSelectedInstances()!=null){
                riskRegistryService.saveRiskRigistryFindings(riskRegistryDTO, userDto);
                List<VulnerabilityDTO> findingsList = riskRegistryService.riskRegistryFindingsListByID(riskRegistryDTO.getRiskRegisterID(), orgSchema);
               
                /* Updating Finding Count in Registry */
                if (findingsList != null) {
                    findingCount = findingsList.size();
                }
                riskRegistryService.updateFindingCountRegistryAnsPlan(riskID, findingCount, userDto.getUserProfileKey(), orgSchema, ApplicationConstants.DB_CONSTANT_REGISTRY_FINDING_COUNT_UPDATE);
                }

                redirectAttributes.addFlashAttribute("engkey", engagementCode);
                redirectAttributes.addFlashAttribute("riskRegisterID", riskRegistryDTO.getRiskRegisterID());
            } else {
                return "redirect:/logout.htm";
            }
            return "redirect:/riskRegistryDetails.htm";
        } catch (Exception e) {
            logger.debug("Exception Occured while Executing the "
                    + "RiskRegistryController removeRegistryItems():: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return "/exception";
        }
    }
    
     @RequestMapping(value = "/updRegNotify", method = RequestMethod.POST)
    public String updateRiskRegistryNotifyDate(@ModelAttribute(value = "riskRegisterObj") RiskRegisterDTO riskRegistryDTO,
            final HttpServletRequest request, Model model,
            final HttpServletResponse response, RedirectAttributes redirectAttributes) throws AppException {
        ModelAndView modelView = new ModelAndView();
        UserProfileDTO userDto = null;
        Set<String> riskRegistryPermissionSet = null;
        try {
            HttpSession session = request.getSession();

            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return "redirect:/logout.htm";
            }
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(TRACE_USER_RISK_REGISTRY_MODULE_REGISTRY_DETAILS_UPDATE);

                riskRegistryPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.RISK_REGISTRY_MODULE);
                modelView.addObject("permissions", riskRegistryPermissionSet);
            } else {
                return "redirect:/logout.htm";
            }

            String engagementCode = "";
            if (request.getParameter("engkey") != null && !StringUtils.isEmpty(request.getParameter("engkey"))) {
                engagementCode = request.getParameter("engkey");
            }
            String orgSchema = reportsAndDashboardService.getSchemaNameByEngKey(engagementCode);
            riskRegistryDTO.setOrgSchema(orgSchema);
            if (permissionCheckHelper.checkUserSubMenuNotAdmin(userDto, PermissionConstants.RISK_REGISTRY_MODULE)) {
                riskRegistryService.updateRiskRegistryNotifyDate(riskRegistryDTO, userDto);

//                redirectAttributes.addFlashAttribute("statusMessage", myProperties.getProperty("riksregistry.detailsupdate.success-message"));
                redirectAttributes.addFlashAttribute("engkey", engagementCode);
                redirectAttributes.addFlashAttribute("riskRegisterID", riskRegistryDTO.getRiskRegisterID());
            } else {
                return "redirect:/logout.htm";
            }
            return "redirect:/riskRegistryDetails.htm";
        } catch (Exception e) {
            logger.debug("Exception Occured while Executing the "
                    + "RiskRegistryController Controller:: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return "/exception";
        }
    }
    
    /**
     *
     * @param request
     * @param response
     * @param model
     * @param redirectAttributes
     * @param redirectEngagementCode
     * @return
     */
    @RequestMapping(value = "/riskRegistryFindDetails")
    public String riskRegistryFindingDetails(HttpServletRequest request, HttpServletResponse response,
            Model model, RedirectAttributes redirectAttributes) {
        logger.info("Start: RiskRegistryController: riskRegistryDetails()");
        UserProfileDTO userDto = new UserProfileDTO();
        Set<String> riskRegistryPermissionSet = null;
        RiskRegisterDTO riskRegisterDTO = new RiskRegisterDTO();
        String engagementCode = "";
        String decEngagementCode = "";
        int riskRegisterID = 0;
        try {
            HttpSession session = request.getSession();
            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return "redirect:/logout.htm";
            }
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(TRACE_USER_RISK_REGISTRY_MODULE_REGISTRY_DETAILS_PAGE);
                riskRegistryPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.RISK_REGISTRY_MODULE);
                model.addAttribute("permissions", riskRegistryPermissionSet);
            }
            if (request.getParameter("engkey") != null && !StringUtils.isEmpty(request.getParameter("engkey"))) {
                engagementCode = request.getParameter("engkey");
            } else if (model.containsAttribute("engkey")) {
                engagementCode = (String) model.asMap().get("engkey");
            }
            if (request.getParameter("riskRegisterID") != null && !StringUtils.isEmpty(request.getParameter("riskRegisterID"))) {
                riskRegisterID = Integer.parseInt(request.getParameter("riskRegisterID"));
            } else if (model.containsAttribute("riskRegisterID")) {
                riskRegisterID = (Integer) model.asMap().get("riskRegisterID");
            }
            if (engagementCode != null && !StringUtils.isEmpty(engagementCode)) {
                if (permissionCheckHelper.checkUserSubMenuNotAdmin(userDto, PermissionConstants.RISK_REGISTRY_MODULE)) {

                    if (engagementCode != null && !engagementCode.equalsIgnoreCase("")) {
                        decEngagementCode = encDec.decrypt(engagementCode);
                    }
                    ClientEngagementDTO engagementDTO = manageEngagementsServiceImpl.viewEngagementByEngmtKey(decEngagementCode);
                    model.addAttribute("engagementDTO", engagementDTO);
                    String orgSchema = reportsAndDashboardService.getSchemaNameByEngKey(engagementCode);

                    RiskRegisterDTO riskRegistryDetails = riskRegistryService.riskRegistryDetailsById(orgSchema, riskRegisterID);
                    List<MasterLookUpDTO> riskResponseList = riskRegistryService.fetchRiskResponseList();
                    model.addAttribute("riskRegistryDetails", riskRegistryDetails);
                    model.addAttribute("riskResponseList", riskResponseList);

                    //CLIENT USERS BASED ON CLIENT ORGANIZATION
                    List<UserProfileDTO> clientUsers = remediationService.remediationOwnerUsers(ApplicationConstants.RISK_OWNER, decEngagementCode);
                    model.addAttribute("clientUsers", clientUsers);

                    //Severity dropdown list
                    List<SummaryDropDownModel> severityList = analystValidationService.analystSeverityList();
                    model.addAttribute("severityList", severityList);

                    String riskFindingId = "";
                    if (request.getParameter("findingID") != null && !StringUtils.isEmpty(request.getParameter("findingID"))) {
                        riskFindingId = request.getParameter("findingID");
                    } else if (model.containsAttribute("findingID")) {
                        riskFindingId = (String) model.asMap().get("findingID");
                    }

                    VulnerabilityDTO riskFinding = riskRegistryService.riskRegistryFindingInfo(Integer.parseInt(riskFindingId + ""), orgSchema);
                    model.addAttribute("riskFinding", riskFinding);
                } else {
                    return "redirect:/logout.htm";
                }
            } else {
                return "redirect:/logout.htm";
            }
        } catch (AppException e) {
            logger.error("Exception occurred : RiskRegistryController: riskRegistryDetails:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        logger.info("End: RiskRegistryController: riskRegistryDetails()");
        return "riskregistry/riskregistryfinddetails";
    }
    
    @RequestMapping(value = "/updateRiskRegistryFinding", method = RequestMethod.POST)
    public String updateRiskRegistryFinding(@ModelAttribute(value = "riskRegisterObj") RiskRegisterDTO riskRegistryDTO,
            final HttpServletRequest request, Model model,
            final HttpServletResponse response, RedirectAttributes redirectAttributes) throws AppException {
        ModelAndView modelView = new ModelAndView();
        UserProfileDTO userDto = null;
        Set<String> riskRegistryPermissionSet = null;
        try {
            HttpSession session = request.getSession();

            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return "redirect:/logout.htm";
            }
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(TRACE_USER_RISK_REGISTRY_MODULE_REGISTRY_DETAILS_UPDATE);

                riskRegistryPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.RISK_REGISTRY_MODULE);
                modelView.addObject("permissions", riskRegistryPermissionSet);
            } else {
                return "redirect:/logout.htm";
            }

            String engagementCode = "";
            if (request.getParameter("engkey") != null && !StringUtils.isEmpty(request.getParameter("engkey"))) {
                engagementCode = request.getParameter("engkey");
            }
            String orgSchema = reportsAndDashboardService.getSchemaNameByEngKey(engagementCode);
            riskRegistryDTO.setOrgSchema(orgSchema);
            if (permissionCheckHelper.checkUserSubMenuNotAdmin(userDto, PermissionConstants.RISK_REGISTRY_MODULE)) {
                riskRegistryService.updateRiskRegistryFinding(riskRegistryDTO, userDto);

                redirectAttributes.addFlashAttribute("statusMessage", myProperties.getProperty("riksregistry.detailsupdate.success-message"));
                redirectAttributes.addFlashAttribute("engkey", engagementCode);
                redirectAttributes.addFlashAttribute("riskRegisterID", riskRegistryDTO.getRiskRegisterID());
                redirectAttributes.addFlashAttribute("findingID", riskRegistryDTO.getInstanceIdentifier());
            } else {
                return "redirect:/logout.htm";
            }
            return "redirect:/riskRegistryFindDetails.htm";
        } catch (Exception e) {
            logger.debug("Exception Occured while Executing the "
                    + "RiskRegistryController Controller:: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return "/exception";
        }
    }
}
