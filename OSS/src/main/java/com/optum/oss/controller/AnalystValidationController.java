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
import com.optum.oss.dto.CVEInformationDTO;
import com.optum.oss.dto.CVSSCalculatorDTO;
import com.optum.oss.dto.CVSSMetricsDTO;
import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.dto.ComplianceInfoDTO;
import com.optum.oss.dto.ExceptionDTO;
import com.optum.oss.dto.ManageServiceUserDTO;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.RoadMapDTO;
import com.optum.oss.dto.SecurityServiceDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.dto.Views;
import com.optum.oss.dto.VulnerabilityDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.PermissionCheckHelper;
import com.optum.oss.model.SummaryDropDownModel;
import com.optum.oss.service.impl.AnalystValidationServiceImpl;
import com.optum.oss.service.impl.CVSSCalculatorServiceImpl;
import com.optum.oss.service.impl.ManageEngagementsServiceImpl;
import com.optum.oss.service.impl.MasterLookUpServiceImpl;
import com.optum.oss.util.CommonUtil;
import com.optum.oss.util.EncryptDecrypt;
import com.optum.oss.util.LoggerUtil;
import com.optum.oss.validators.AddVulnerabilityValidator;
import com.optum.oss.validators.UpdateVulnerabilityValidator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
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
public class AnalystValidationController {

    private static final Logger logger = Logger.getLogger(AnalystValidationController.class);

    @Resource(name = "myProperties")
    private Properties myProperties;
    /*
     *Begin: Autowiring the required Class instances
     */
    @Autowired
    private AnalystValidationServiceImpl analystValidationService;
    @Autowired
    private ManageEngagementsServiceImpl manageEngagementsServiceImpl;
    @Autowired
    private EncryptDecrypt encDec;
    @Autowired
    private LoggerUtil auditLogger;
    @Autowired
    private CVSSCalculatorServiceImpl cvssCalcService;
    @Autowired
    private AddVulnerabilityValidator addVulnerabilityValidator;
    @Autowired
    private UpdateVulnerabilityValidator updateVulnerabilityValidator;
    @Autowired
    PermissionCheckHelper permissionCheckHelper;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private MasterLookUpServiceImpl masterLookUpService;
    /*
     * End: Autowiring the required Class instances
     */
    /*
     START : LOG MESSAGES
     */
    private final String TRACE_USER_ADD_VUL = "User accessed addvulnerability view";
    private final String TRACE_USER_REVIEW_VAL = "User accessed review vulnerability";
    private final String TRACE_USER_EDIT_VAL = "User accessed edit vulnerability";
    private final String TRACE_USER_VIEW_VAL = "User accessed view vulnerability";
    private final String TRACE_USER_SEARCH_ANALYST_VAL_WORKLIST = "User accessed analyst search validation worklist";
    private final String TRACE_USER_ANALYST_VAL_WORKLIST = "User accessed analyst validation worklist";
    private final String TRACE_USER_ADD_NEW_VAL = "User accessed add new vulnerability";
    private final String TRACE_USER_UPDATE_VAL_LIST = "User accessed update vulnerability";
    private final String TRACE_USER_DELETE_VAL_LIST = "User accessed delete vulnerability";
    private final String TRACE_USER_ADD_CVE_INFO = "User accessed CVE info";
    private final String TRACE_USER_ADD_HIT_RUST = "User accessed addHitrust";
    private final String TRACE_USER_UPDATE_SERVICE_STATUS = "User accessed update service status";
    private final String TRACE_USER_VIEW_VAL_FOR_REVIEW_VAL = "User accessed view vulnerability for review vulnerability";
    private final String INFO_USER_ADD_VAL = "User accessed add vulnerabilityview";
    private final String INFO_USER_REVIEW_VAL = "User accessed review valnarability";
    private final String INFO_USER_EDIT_VAL = "User accessed edit valnarability";
    private final String INFO_USER_VIEW_VAL = "User accessed view valnarability";
    private final String INFO_USER_VIEW_VAL_DETAILS = "User accessed view vanarability details";
    private final String INFO_USER_ANALYST_VAL_WORKLIST = "User accessed analyst validation worklist";
    private final String INFO_USER_DEL_VAL = "User accessed delete valnarability";
    private final String INFO_USER_ADD_NEW_VAL = "User accessed add new valnarability";
    private final String INFO_USER_UPDATE_VAL = "User accessed update valnarability";
    private final String INFO_USER_SEARCH_ANALYST_VAL_WORKLIST = "User accessed analyst search validation worklist";
    private final String INFO_USER_VAL_EXPORT_EXCEL = "User accessed valnarability export excel";
    private final String INFO_USER_UPDATE_SERVICE_STATUS = "User accessed update service status";
    private final String INFO_USER_VIEW_VAL_REVIEW = "User accessed view valnarability review";
    private final String TRACE_USER_CVE_LIST = "User accessed CVE list";
    /*
     END : LOG MESSAGES
     */

    /**
     * Add New Vulnerability Page
     *
     * @param request
     * @param response
     * @param model
     * @param encryptedEngCode
     * @param pageCount
     * @param orgSchema1
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/addvulnerabilityview")
    public String addVulnerabilityPage(HttpServletRequest request, HttpServletResponse response, Model model,
            @ModelAttribute (value = "cgenk") String encryptedEngCode, @ModelAttribute (value = "pgno") String pageCount,
            @ModelAttribute(value = "orgSchma") String orgSchema1,
            RedirectAttributes redirectAttributes) {
//        logger.info("Start: AnalystValidationController : reviewVulnerability");
        logger.info(INFO_USER_ADD_VAL);
        UserProfileDTO userDto = null;
        String engagementCode = "";
        int pgcnt = 0;
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            
            if (!StringUtils.isEmpty(request.getParameter("cgenk")) || !StringUtils.isEmpty(encryptedEngCode)) {
                if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                    userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                    MDC.put("user", userDto.getEmail());
                    MDC.put("ip", userDto.getUserMACAddress());

                    //Bug Id: IN:021271
                    Set<String> analystPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.ANALYST_VALIDATION_MODULE_MENU);
                    model.addAttribute("permissions", analystPermissionSet);
                    //Bug Id: IN:021271
                    Set<String> permissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.CLIENT_REPORTS_UPLOAD);
                    model.addAttribute("clientreportspermission", permissionSet);

                    logger.trace(TRACE_USER_ADD_VUL);
                }

                VulnerabilityDTO vulnerabilityDTO;
                if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.ANALYST_VALIDATION_MODULE_MENU,
                        PermissionConstants.REVIEW_VULNERABILITY)) {
                    if (model.containsAttribute("vulnerabilityDTO")) {
                        vulnerabilityDTO = (VulnerabilityDTO) model.asMap().get("vulnerabilityDTO");
                        vulnerabilityDTO = analystValidationService.addHitrustToVulnerability(vulnerabilityDTO);
                        model.addAttribute("vulnerabilityDTO", vulnerabilityDTO);
                        model.addAttribute("comMap", vulnerabilityDTO.getCveInformationDTO().getComplianceMap());
                        model.addAttribute("crossWalkMap", vulnerabilityDTO.getCveInformationDTO().getHitrustCrossWalkMap());
                    } else {
                        model.addAttribute("vulnerabilityDTO", new VulnerabilityDTO());
                    }

                    if (!StringUtils.isEmpty(pageCount)) {
                        pgcnt = Integer.parseInt(pageCount);
                    } else {
                        pgcnt = Integer.parseInt(request.getParameter("pgno"));
                    }
                    model.addAttribute("pgcnt", pgcnt);
                    String orgSchema = "";
                    if(!StringUtils.isEmpty(request.getParameter("orgSchma"))) {
                        orgSchema = request.getParameter("orgSchma");
                    }else{
                        orgSchema= orgSchema1;
                    }
                    
                    if (!StringUtils.isEmpty(encryptedEngCode)) {
                        engagementCode = encryptedEngCode;
                    } else {
                        engagementCode = request.getParameter("cgenk");
                    }
                    if (engagementCode == null || "".equalsIgnoreCase(engagementCode)) {
                        vulnerabilityDTO = (VulnerabilityDTO) model.asMap().get("vulnerabilityDTO");
                        engagementCode = vulnerabilityDTO.getClientEngagementDTO().getEncEngagementCode();
                    }
                    String decEngagementCode = "";
                    if (engagementCode != null && !engagementCode.equalsIgnoreCase("")) {
                        decEngagementCode = encDec.decrypt(engagementCode);
                    } else {//Fix for issue IN021246
                        return "redirect:/logout.htm";
                    }
                    //Services list along with their statuses for an engagement
                    List<SecurityServiceDTO> servicesList = analystValidationService.analystEngagementServices(decEngagementCode);
                    model.addAttribute("servicesList", servicesList);
                    
                    //OWASP List
                    model.addAttribute("owaspList",analystValidationService.fetchOwaspList());

                    ClientEngagementDTO engagementDTO = manageEngagementsServiceImpl.viewEngagementByEngmtKey(decEngagementCode);
                    engagementDTO.setOrgSchema(orgSchema);
                    model.addAttribute("engagementDTO", engagementDTO);

                //List<VulnerabilityDTO> vulnerabilityList = analystValidationService.vulnerabilityList(decEngagementCode);
                    //model.addAttribute("vulnerabilityList", vulnerabilityList);
                    //Operating System dropdown list
                    List<MasterLookUpDTO> osList = analystValidationService.fetchOSList();
                    model.addAttribute("osList", osList);

                    //Analyst status dropdown list
                    List<MasterLookUpDTO> statusList = analystValidationService.analystStatusList(ApplicationConstants.STATUS_VULNERABILITY);
                    model.addAttribute("statusList", statusList);

                    //Root cause dropdown list
                    List<MasterLookUpDTO> rootCauseList = analystValidationService.vulnerabilityCategoryList();
                    model.addAttribute("rootCauseList", rootCauseList);

                    //Cost effort drop down list
                    List<MasterLookUpDTO> costEffortList = analystValidationService.analystCostEffortList();
                    model.addAttribute("costEffortList", costEffortList);

                    //Hitrust lookup Map
                    Map<String, LinkedHashMap<String, ArrayList<ComplianceInfoDTO>>> complianceMap = analystValidationService.fetchComplianceInformation();
                    model.addAttribute("cveCompInfo", complianceMap);

                    //Fetch manual vulnerability names
                    List<VulnerabilityDTO> manualVulnerabilities = analystValidationService.fetchManualVulnerabilityNames();
                    model.addAttribute("manualVulnerabilities", manualVulnerabilities);

                    //Page to display
                    model.addAttribute("pageName", "ADDPAGE");
                } else {
                    return "redirect:/logout.htm";
                }
            } else {
                return "redirect:/logout.htm";
            }
        } catch (AppException e) {
            logger.debug("Exception occured : reviewVulnerability : " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        return "analyst/vulnerabilitypage";
    }

    /**
     * Review Vulnerability
     * 
     * @param vulnerabilityDTOs
     * @param request
     * @param response
     * @param redirectAttributes
     * @param model
     * @param encryptedEngCode
     * @param pageCount
     * @param orgSchema
     * @return
     */
    @RequestMapping(value = "/reviewvulnerability")
    public String reviewVulnerability(@ModelAttribute (value="vulnerabilityDTOs") VulnerabilityDTO vulnerabilityDTOs, 
            HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,
            Model model, @ModelAttribute(value = "cgenk") String encryptedEngCode, @ModelAttribute(value = "pgcnt") String pageCount,
            @ModelAttribute(value = "orgSchma") String orgSchema) {
        logger.info(INFO_USER_REVIEW_VAL);
//        logger.info("Start: AnalystValidationController : reviewVulnerability");
        UserProfileDTO userDto = null;
        Set<String> analystModPermSet = null;
        int pgcnt = 0;
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            request.getSession().setAttribute("ROADMAPOBJ", null);
            if (!StringUtils.isEmpty(request.getParameter("cgenk")) || !StringUtils.isEmpty(encryptedEngCode)) {
                if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                    userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                    MDC.put("user", userDto.getEmail());
                    MDC.put("ip", userDto.getUserMACAddress());

                    //Bug Id: IN:021271
                    Set<String> analystPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.ANALYST_VALIDATION_MODULE_MENU);
                    Set<String> engagementPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU);
                    analystModPermSet = new HashSet<>();
                    analystModPermSet.addAll(analystPermissionSet);
                    if(engagementPermissionSet != null){
                        analystModPermSet.addAll(engagementPermissionSet);
                    }
                
                    Set<String> permissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.CLIENT_REPORTS_UPLOAD);
                    model.addAttribute("permissions", analystModPermSet);
                    model.addAttribute("clientreportspermission", permissionSet);
                //Bug Id: IN:021271

                    logger.trace(TRACE_USER_REVIEW_VAL);
                }
                if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.ANALYST_VALIDATION_MODULE_MENU,
                        PermissionConstants.REVIEW_VULNERABILITY)) {
                    String decEngagementCode = "";
                    String engagementCode = "";
                   // String orgSchema = "";
                    if(!StringUtils.isEmpty(request.getParameter("orgSchma"))) {
                        orgSchema = request.getParameter("orgSchma");
                    } 
                    if(!StringUtils.isEmpty(encryptedEngCode)) {
                        engagementCode = encryptedEngCode;
                    } else {
                        engagementCode = request.getParameter("cgenk");
                    }
                    if (engagementCode != null && !engagementCode.equalsIgnoreCase("")) {
                        decEngagementCode = encDec.decrypt(engagementCode);
                    }
                    //Services list along with their statuses for an engagement
                    List<SecurityServiceDTO> servicesList = analystValidationService.analystEngagementServices(decEngagementCode);
                    model.addAttribute("servicesList", servicesList);
                    
                    //OWASP List
                    model.addAttribute("owaspList",analystValidationService.fetchOwaspList());
                    
                    ClientEngagementDTO engagementDTO = manageEngagementsServiceImpl.viewEngagementByEngmtKey(decEngagementCode);
                    engagementDTO.setOrgSchema(orgSchema);
                    model.addAttribute("engagementDTO", engagementDTO);

                    if(!StringUtils.isEmpty(pageCount)) {
                        pgcnt = Integer.parseInt(pageCount);
                    } else {
                        pgcnt = Integer.parseInt(request.getParameter("pgcnt"));
                    }
                    
                    if("CANCEL".equalsIgnoreCase(request.getParameter("pageName")) || "CANCEL".equalsIgnoreCase(model.asMap().get("pageName")+"")){
                        vulnerabilityDTOs = (VulnerabilityDTO) request.getSession().getAttribute("searchVuln");
                    }
                    
                    model.addAttribute("pageNo", pgcnt);
                    model.addAttribute("orgSchma",orgSchema);
                    vulnerabilityDTOs.setPageNo(pgcnt); //Page Number
                    if (System.getProperty("VUL_COUNT") != null) {
                        vulnerabilityDTOs.setRowCount(Integer.parseInt(System.getProperty("VUL_COUNT"))); //Number of records per page
                    } else {
                        vulnerabilityDTOs.setRowCount(100);
                    }

                    vulnerabilityDTOs.getClientEngagementDTO().setEngagementCode(decEngagementCode);
                    
                    request.getSession().setAttribute("searchVuln", vulnerabilityDTOs);

                        model.addAttribute("searchVuln", vulnerabilityDTOs);
                    vulnerabilityDTOs.setOrgSchema(orgSchema);

                    vulnerabilityDTOs.setRootCauseCode("");
                    List<VulnerabilityDTO> vulnerabilityList = analystValidationService.vulnerabilityList(vulnerabilityDTOs);
                    model.addAttribute("vulnerabilityList", vulnerabilityList);
                    
                    //CODE FOR THE TOTAL VUL COUNT AND VALIDATED
                    if (vulnerabilityList != null && vulnerabilityList.size() > 0) {
                        VulnerabilityDTO vDto = vulnerabilityList.get(vulnerabilityList.size() - 1);
                        model.addAttribute("totalVulCount", vDto.getVulCount());
                        model.addAttribute("vulNotReviewedCount", vDto.getNotReviewedCount());
                    } else {
                        model.addAttribute("totalVulCount", 0);
                        model.addAttribute("vulNotReviewedCount", 0);
                    }

                    //Analyst status dropdown list
                    List<MasterLookUpDTO> statusList = analystValidationService.analystStatusList(ApplicationConstants.STATUS_ALL);
                    model.addAttribute("statusList", statusList);

                    //Root cause dropdown list
                    List<MasterLookUpDTO> rootCauseList = analystValidationService.vulnerabilityCategoryList();
                    model.addAttribute("rootCauseList", rootCauseList);

                    //Cost effort drop down list
                    List<MasterLookUpDTO> costEffortList = analystValidationService.analystCostEffortList();
                    model.addAttribute("costEffortList", costEffortList);

                    //Source drop down list
                    List<MasterLookUpDTO> sourceList = analystValidationService.analystSourceList();
                    model.addAttribute("sourceList", sourceList);
                    //scan tool list
                    List<MasterLookUpDTO> scanToolList = masterLookUpService.fetchMasterLookUpEntitiesByEntityType(ApplicationConstants.DB_CONSTANT_SCAN_TOOL);
                    model.addAttribute("scanToolList", scanToolList);

                    //Severity dropdown list
                    List<SummaryDropDownModel> severityList = analystValidationService.analystSeverityList();
                    model.addAttribute("severityList", severityList);

                    //Hitrust lookup Map
                    Map<String, LinkedHashMap<String, ArrayList<ComplianceInfoDTO>>> complianceMap = analystValidationService.fetchComplianceInformation();
                    model.addAttribute("cveCompInfo", complianceMap);


                    //Page to display
                    model.addAttribute("pageName", "REVIEWPAGE");
                    //modelView.setViewName("analyst/reviewvulnerability");
                    return "analyst/reviewvulnerability";
                } else {
                    //modelView.setViewName("redirect:/logout.htm");
                    return "redirect:/logout.htm";
                }
            } else {
                return "redirect:/logout.htm";
            }
        } catch (AppException e) {
            logger.debug("Exception occured : reviewVulnerability : " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        //logger.info("End: AnalystValidationController : reviewVulnerability");
    }

    /**
     * This method for load update vulnerability page to update vulnerability
     * details
     *
     * @param request
     * @param response
     * @param model
     * @param encryptedEngCode
     * @param orgSchma
     * @param pageCount
     * @param redirectAttributes
     * @return modelView
     */
    @RequestMapping(value = "/editvulnerability")
    public String updateVulnerabilityPage(HttpServletRequest request, HttpServletResponse response,
            Model model, @ModelAttribute (value = "cgenk") String encryptedEngCode, 
            @ModelAttribute (value = "orgSchma") String orgSchma,
            @ModelAttribute (value = "pgno") String pageCount,
            RedirectAttributes redirectAttributes) {

        logger.info(INFO_USER_EDIT_VAL);
//        logger.info("Start: AnalystValidationController : updateVulnerabilityPage");
        UserProfileDTO userDto = null;
        String engagementCode = "";
        int pgcnt = 0;
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                
                //Bug Id: IN:021271
                Set<String> analystPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.ANALYST_VALIDATION_MODULE_MENU);
                model.addAttribute("permissions", analystPermissionSet);
                //Bug Id: IN:021271
                Set<String>permissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.CLIENT_REPORTS_UPLOAD);
                model.addAttribute("clientreportspermission",permissionSet);
                
                logger.trace(TRACE_USER_EDIT_VAL);
            }
            if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.ANALYST_VALIDATION_MODULE_MENU,
                    PermissionConstants.REVIEW_VULNERABILITY)) {
                String decInstaceIdentifier = "";

                VulnerabilityDTO vulnerabilityDTO;

                if (model.containsAttribute("vulnerabilityDTO")) {
                    vulnerabilityDTO = (VulnerabilityDTO) model.asMap().get("vulnerabilityDTO");
                    String instanceIdentifier = vulnerabilityDTO.getEncInstanceIdentifier().trim();
                    if (!StringUtils.isEmpty(instanceIdentifier)) {
                        decInstaceIdentifier = encDec.decrypt(instanceIdentifier);
                    }
                    vulnerabilityDTO.setInstanceIdentifier(decInstaceIdentifier);
                    if(!ApplicationConstants.DB_SOURCE_NAME.equalsIgnoreCase(vulnerabilityDTO.getSourceName())) {
                        vulnerabilityDTO = analystValidationService.fetchVulnerabilityBasicDetails(vulnerabilityDTO);
                    }
                    vulnerabilityDTO = analystValidationService.addHitrustToVulnerability(vulnerabilityDTO);
                } else {
                    model.addAttribute("vulnerabilityDTO", new VulnerabilityDTO());

                    String instanceIdentifier = request.getParameter("encInstanceIdentifier");
                    if (instanceIdentifier == null || "".equalsIgnoreCase(instanceIdentifier)) {
                        vulnerabilityDTO = (VulnerabilityDTO) model.asMap().get("vulnerabilityDTO");
                        instanceIdentifier = vulnerabilityDTO.getEncInstanceIdentifier();
                    }
                    if (instanceIdentifier != null && !instanceIdentifier.equalsIgnoreCase("")) {
                        decInstaceIdentifier = encDec.decrypt(instanceIdentifier);
                    } else {
                        return "redirect:/logout.htm";
                    }

                    vulnerabilityDTO = analystValidationService.viewVulnerabilityDetails(Long.parseLong(decInstaceIdentifier),orgSchma);
                    vulnerabilityDTO.setEncInstanceIdentifier(instanceIdentifier);
                }

                model.addAttribute("vulnerabilityDTO", vulnerabilityDTO);
                model.addAttribute("comMap", vulnerabilityDTO.getCveInformationDTO().getComplianceMap());
                model.addAttribute("crossWalkMap", vulnerabilityDTO.getCveInformationDTO().getHitrustCrossWalkMap());

                if(!StringUtils.isEmpty(encryptedEngCode)) {
                    engagementCode = encryptedEngCode;
                } else {
                    engagementCode = request.getParameter("cgenk");
                }
                if (engagementCode == null || "".equalsIgnoreCase(engagementCode)) {
                    vulnerabilityDTO = (VulnerabilityDTO) model.asMap().get("vulnerabilityDTO");
                    engagementCode = vulnerabilityDTO.getClientEngagementDTO().getEncEngagementCode();
                }
                String decEngagementCode = "";
                if (engagementCode != null && !engagementCode.equalsIgnoreCase("")) {
                    decEngagementCode = encDec.decrypt(engagementCode);
                } else {
                    return "redirect:/logout.htm";
                }
                
                if(!StringUtils.isEmpty(pageCount)) {
                    pgcnt = Integer.parseInt(pageCount);
                } else {
                    pgcnt = Integer.parseInt(request.getParameter("pgno"));
                }
                model.addAttribute("pgcnt", pgcnt);
                 
                //Services list along with their statuses for an engagement
                List<SecurityServiceDTO> servicesList = analystValidationService.analystEngagementServices(decEngagementCode);
                model.addAttribute("servicesList", servicesList);
                
                //OWASP List
                model.addAttribute("owaspList",analystValidationService.fetchOwaspList());

                ClientEngagementDTO engagementDTO = manageEngagementsServiceImpl.viewEngagementByEngmtKey(decEngagementCode);
                engagementDTO.setOrgSchema(orgSchma);
                model.addAttribute("engagementDTO", engagementDTO);

                //List<VulnerabilityDTO> vulnerabilityList = analystValidationService.vulnerabilityList(decEngagementCode);
                //model.addAttribute("vulnerabilityList", vulnerabilityList);

                //Analyst status dropdown list
//                List<MasterLookUpDTO> statusList = analystValidationService.analystStatusList();
                List<MasterLookUpDTO> statusList=analystValidationService.analystStatusConditionalList(vulnerabilityDTO.getStatusCode());
                model.addAttribute("statusList", statusList);
                
                //OWASP List
                model.addAttribute("owaspList",analystValidationService.fetchOwaspList());

                //Root cause dropdown list
                List<MasterLookUpDTO> rootCauseList = analystValidationService.vulnerabilityCategoryList();
                model.addAttribute("rootCauseList", rootCauseList);

                //Cost effort drop down list
                List<MasterLookUpDTO> costEffortList = analystValidationService.analystCostEffortList();
                model.addAttribute("costEffortList", costEffortList);
                
                //Operating System dropdown list
                List<MasterLookUpDTO> osList = analystValidationService.fetchOSList();
                model.addAttribute("osList", osList);

                //Hitrust lookup Map
                Map<String, LinkedHashMap<String, ArrayList<ComplianceInfoDTO>>> complianceMap = analystValidationService.fetchComplianceInformation();
                model.addAttribute("cveCompInfo", complianceMap);
                
                //Fetch manual vulnerability names
                List<VulnerabilityDTO> manualVulnerabilities = analystValidationService.fetchManualVulnerabilityNames();
                model.addAttribute("manualVulnerabilities", manualVulnerabilities);

                //Page to display
                model.addAttribute("pageName", "UPDATEPAGE");
                
                if(request.getSession().getAttribute("ROADMAPOBJ") != null){
                    RoadMapDTO roadmapObj = (RoadMapDTO)request.getSession().getAttribute("ROADMAPOBJ");
                    model.addAttribute("roadmapObj", roadmapObj);
                }
            } else {
                return "redirect:/logout.htm";
            }
        } catch (AppException e) {
            logger.debug("Exception occured : updateVulnerabilityPage : " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        logger.info("End: AnalystValidationController : updateVulnerabilityPage");
        return "analyst/vulnerabilitypage";
    }

    /**
     *
     * @param request
     * @param response
     * @param redirectAttributes
     * @param model
     * @return modelView
     */
    @RequestMapping(value = "/viewvulnerability")
    public ModelAndView viewVulnerability(@ModelAttribute (value="vulnerabilityDTOs") VulnerabilityDTO vulnerabilityDTOs,
            HttpServletRequest request, HttpServletResponse response,
            RedirectAttributes redirectAttributes, Model model) throws AppException {

        logger.info(INFO_USER_VIEW_VAL);
//        logger.info("Start: AnalystValidationController: viewVulnerability() to load View Vulnerability");
        ModelAndView modelView = new ModelAndView();
        UserProfileDTO userDto = null;
        if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
            return new ModelAndView("redirect:/logout.htm");
        }
        Set<String> analystModPermSet = null;
        request.getSession().setAttribute("ROADMAPOBJ", null);
        if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
            userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
            MDC.put("user", userDto.getEmail());
            MDC.put("ip", userDto.getUserMACAddress());

            //Bug Id: IN:021271
            Set<String> analystPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.ANALYST_VALIDATION_MODULE_MENU);
            Set<String> engagementPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU);
            analystModPermSet = new HashSet<>();
            analystModPermSet.addAll(analystPermissionSet);
            if(engagementPermissionSet != null){
                analystModPermSet.addAll(engagementPermissionSet);
            }
                    
            Set<String> permissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.CLIENT_REPORTS_UPLOAD);
            modelView.addObject("clientreportspermission", permissionSet);
            modelView.addObject("permissions", analystModPermSet);
            //Bug Id: IN:021271

            logger.trace(TRACE_USER_VIEW_VAL);
        }
        try {
            if (!StringUtils.isEmpty(request.getParameter("cgenk"))) {
                if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.ANALYST_VALIDATION_MODULE_MENU,
                        PermissionConstants.VIEW_VULNERABILITY)) {
                    String decEngagementCode = "";
                    String engagementCode = request.getParameter("cgenk");
                    if (engagementCode != null && !engagementCode.equalsIgnoreCase("")) {
                        decEngagementCode = encDec.decrypt(engagementCode);
                    }
                    //Services list along with their statuses for an engagement
                    List<SecurityServiceDTO> servicesList = analystValidationService.analystEngagementServices(decEngagementCode);
                    modelView.addObject("servicesList", servicesList);

                    //OWASP List
                    modelView.addObject("owaspList",analystValidationService.fetchOwaspList());
                    
                    //Analyst status dropdown list
                    List<MasterLookUpDTO> statusList = analystValidationService.analystStatusList(ApplicationConstants.STATUS_ALL);
                    modelView.addObject("statusList", statusList);

                    //Root cause dropdown list
                    List<MasterLookUpDTO> rootCauseList = analystValidationService.vulnerabilityCategoryList();
                    modelView.addObject("rootCauseList", rootCauseList);

                    //Cost effort drop down list
                    List<MasterLookUpDTO> costEffortList = analystValidationService.analystCostEffortList();
                    modelView.addObject("costEffortList", costEffortList);

                    //Source drop down list
                    List<MasterLookUpDTO> sourceList = analystValidationService.analystSourceList();
                    modelView.addObject("sourceList", sourceList);

                    //Severity dropdown list
                    List<SummaryDropDownModel> severityList = analystValidationService.analystSeverityList();
                    modelView.addObject("severityList", severityList);
                    String orgSchema = request.getParameter("orgSchma");
                    
                    //Service Engagement Information along with their statuses for an engagement
                    ClientEngagementDTO engagementDTO = manageEngagementsServiceImpl.viewEngagementByEngmtKey(decEngagementCode);
                    engagementDTO.setOrgSchema(orgSchema);
                    modelView.addObject("engagementDTO", engagementDTO);

                    int pgcnt = Integer.parseInt(request.getParameter("pgcnt"));
                    modelView.addObject("pageNo", pgcnt);
                    modelView.addObject("orgSchma",orgSchema);
                    vulnerabilityDTOs.setOrgSchema(orgSchema);
                    vulnerabilityDTOs.setPageNo(pgcnt); //Page Number
                    if (System.getProperty("VUL_COUNT") != null) {
                        vulnerabilityDTOs.setRowCount(Integer.parseInt(System.getProperty("VUL_COUNT"))); //Number of records per page
                    } else {
                        vulnerabilityDTOs.setRowCount(100);
                    }
                    vulnerabilityDTOs.getClientEngagementDTO().setEngagementCode(decEngagementCode);
                    modelView.addObject("searchVuln", vulnerabilityDTOs);

                    vulnerabilityDTOs.setRootCauseCode("");
                    List<VulnerabilityDTO> vulnerabilityList = analystValidationService.vulnerabilityList(vulnerabilityDTOs);
                    modelView.addObject("vulnerabilityList", vulnerabilityList);
                    
                    //TOTAL VUL COUNT AND NOT REVIEWED COUNT
                    if (vulnerabilityList != null && vulnerabilityList.size() > 0) {
                        VulnerabilityDTO vDto = vulnerabilityList.get(vulnerabilityList.size() - 1);
                        model.addAttribute("totalVulCount", vDto.getVulCount());
                        model.addAttribute("vulNotReviewedCount", vDto.getNotReviewedCount());
                    } else {
                        model.addAttribute("totalVulCount", 0);
                        model.addAttribute("vulNotReviewedCount", 0);
                    }

                    modelView.setViewName("analyst/viewvulnerability");
                } else {
                    modelView.setViewName("redirect:/logout.htm");
                }
            } else {
                modelView.setViewName("redirect:/logout.htm");
            }
        } catch (AppException e) {
            logger.debug("Exception occured : viewVulnerability : " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }

        logger.info("End: AnalystValidationController: viewVulnerability() to load View Vulnerability");
        return modelView;
    }

    /**
     * This method for load view vulnerability details page when click on view
     * link from Vulnerability list
     *
     * @param request
     * @param response
     * @param redirectAttributes
     * @param model
     * @return modelView
     */
    @RequestMapping(value = "/viewvulnerabilitydetails")
    public ModelAndView viewVulnerabilityDetails(@ModelAttribute (value="vulnerabilityDTOs") VulnerabilityDTO vulnerabilityObj,
            HttpServletRequest request, HttpServletResponse response,
            RedirectAttributes redirectAttributes, Model model) {

        logger.info(INFO_USER_VIEW_VAL_DETAILS);
//        logger.info("Start: AnalystValidationController: viewvulnerabilitydetails() to load View viewvulnerabilitydetails");
        ModelAndView modelView = new ModelAndView();
        UserProfileDTO userDto = null;
        Set<String> analystModPermSet = null;
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return new ModelAndView("redirect:/logout.htm");
            }
            if (!StringUtils.isEmpty(request.getParameter("clientEngagementDTO.encEngagementCode"))
                    || !StringUtils.isEmpty(request.getParameter("encInstanceIdentifier"))) {
                if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                    userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);

                    //Bug Id: IN:021271
                    Set<String> analystPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.ANALYST_VALIDATION_MODULE_MENU);
                    Set<String> engagementPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU);
                    analystModPermSet = new HashSet<>();
                    analystModPermSet.addAll(analystPermissionSet);
                    if(engagementPermissionSet != null){
                        analystModPermSet.addAll(engagementPermissionSet);
                    }
                    modelView.addObject("permissions", analystModPermSet);
                    //Bug Id: IN:021271
                    Set<String> permissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.CLIENT_REPORTS_UPLOAD);
                    modelView.addObject("clientreportspermission", permissionSet);
                }

                if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.ANALYST_VALIDATION_MODULE_MENU,
                        PermissionConstants.VIEW_VULNERABILITY)) {
                    String decEngagementCode = "";
                    String decInstanceIdentifier = "";
                    if (!StringUtils.isEmpty(request.getParameter("clientEngagementDTO.encEngagementCode"))) {
                        decEngagementCode = encDec.decrypt(request.getParameter("clientEngagementDTO.encEngagementCode"));
                    }
                    if (!StringUtils.isEmpty(request.getParameter("encInstanceIdentifier"))) {
                        decInstanceIdentifier = encDec.decrypt(request.getParameter("encInstanceIdentifier"));
                    }
                    String orgSchema = request.getParameter("orgSchma");
                    //Service Engagement Information along with their statuses for an engagement
                    ClientEngagementDTO engagementDTO = manageEngagementsServiceImpl.viewEngagementByEngmtKey(decEngagementCode);
                    engagementDTO.setOrgSchema(orgSchema); 
                    modelView.addObject("engagementDTO", engagementDTO);

                    //Services list along with their statuses for an engagement
                    List<SecurityServiceDTO> servicesList = analystValidationService.analystEngagementServices(decEngagementCode);
                    modelView.addObject("servicesList", servicesList);
                    
                    //Analyst status dropdown list
                    List<MasterLookUpDTO> statusList = analystValidationService.analystStatusList(ApplicationConstants.STATUS_ALL);
                    modelView.addObject("statusList", statusList);
                    
                    //Source drop down list
                    List<MasterLookUpDTO> sourceList = analystValidationService.analystSourceList();
                    modelView.addObject("sourceList", sourceList);
                    
                    //Severity dropdown list
                    List<SummaryDropDownModel> severityList = analystValidationService.analystSeverityList();
                    modelView.addObject("severityList", severityList);
                    
                    //OWASP List
                    modelView.addObject("owaspList",analystValidationService.fetchOwaspList());

                    /* start View Vulnerability Details list */
                    VulnerabilityDTO vulnerabilityDTO = analystValidationService.viewVulnerabilityDetails(Long.parseLong(decInstanceIdentifier),orgSchema);
                    modelView.addObject("vulnerabilityDTO", vulnerabilityDTO);
                    modelView.addObject("comMap", vulnerabilityDTO.getCveInformationDTO().getComplianceMap());
                    modelView.addObject("crossWalkMap", vulnerabilityDTO.getCveInformationDTO().getHitrustCrossWalkMap());
                    /* End  View Vulnerability Details list */

                    int pgcnt = Integer.parseInt(request.getParameter("pgno"));
                    modelView.addObject("pgcnt", pgcnt);
                    modelView.addObject("pageNo", pgcnt);
                    modelView.addObject("orgSchma",orgSchema);

                    vulnerabilityObj.setPageNo(pgcnt); //Page Number
                    if (System.getProperty("VUL_COUNT") != null) {
                        vulnerabilityObj.setRowCount(Integer.parseInt(System.getProperty("VUL_COUNT"))); //Number of records per page
                    } else {
                        vulnerabilityObj.setRowCount(100);
                    }
                    vulnerabilityObj.getClientEngagementDTO().setEngagementCode(decEngagementCode);

                    modelView.addObject("searchVuln", vulnerabilityObj);
                    vulnerabilityObj.setOrgSchema(orgSchema); 
                    vulnerabilityObj.setRootCauseCode("");
                    List<VulnerabilityDTO> vulnerabilityList = analystValidationService.vulnerabilityList(vulnerabilityObj);
                    modelView.addObject("vulnerabilityList", vulnerabilityList);
                    
                    //TOTAL VUL COUNT AND NOT REVIEWED COUNT
                    if (vulnerabilityList != null && vulnerabilityList.size() > 0) {
                        VulnerabilityDTO vDto = vulnerabilityList.get(vulnerabilityList.size() - 1);
                        model.addAttribute("totalVulCount", vDto.getVulCount());
                        model.addAttribute("vulNotReviewedCount", vDto.getNotReviewedCount());
                    } else {
                        model.addAttribute("totalVulCount", 0);
                        model.addAttribute("vulNotReviewedCount", 0);
                    }

                    /*Start Page to display */
                    modelView.addObject("pageName", "VIEWVULNERBILITY");
                    /*End Page to display */
                    modelView.setViewName("analyst/viewvulnerability");
                } else {
                    modelView.setViewName("redirect:/logout.htm");
                }
            } else {
                modelView.setViewName("redirect:/logout.htm");
            }
        } catch (AppException e) {
            logger.debug("Exception occured : viewVulnerabilityDetails : " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        logger.info("End: AnalystValidationController: viewVulnerabilityDetails() to load View Vulnerability");
        return modelView;
    }

    /**
     *
     * @param request
     * @param response
     * @param redirectAttributes
     * @param model
     * @return modelView
     */
    @RequestMapping(value = "/analystvalidationworklist")
    public ModelAndView analystValidationWorklist(HttpServletRequest request, HttpServletResponse response,
            RedirectAttributes redirectAttributes, Model model) {

        logger.info(INFO_USER_ANALYST_VAL_WORKLIST);
//        logger.info("Start: AnalystValidationController: analystValidationWorklist() to fetch Analyst Validation worklist");
        ModelAndView modelView = new ModelAndView();
        UserProfileDTO userDto = new UserProfileDTO();
        Set<String> analystPermissionSet = null;
        Set<String> engagementPermissionSet = null;
        Set<String> docUpPermSet = null;
        
        Set<String> analystModPermSet = null;
        ManageServiceUserDTO manageServiceUserDTO = new ManageServiceUserDTO();
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return new ModelAndView("redirect:/logout.htm");
            }
            
            HttpSession session = request.getSession();
            
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(TRACE_USER_ANALYST_VAL_WORKLIST);
                analystPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.ANALYST_VALIDATION_MODULE_MENU);
                engagementPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_ENGAGEMENT_USERS_SUB_MENU);
                docUpPermSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.ENGAGEMENT_DATA_UPLOAD_MENU);
                
                analystModPermSet = new HashSet<>();
                analystModPermSet.addAll(analystPermissionSet);
                if(engagementPermissionSet != null){
                    analystModPermSet.addAll(engagementPermissionSet);
                }
                if(docUpPermSet != null){
                    analystModPermSet.addAll(docUpPermSet);
                }
                
                modelView.addObject("permissions", analystPermissionSet);
            }
            if (permissionCheckHelper.checkUserSubMenuNotAdmin(userDto,
                    PermissionConstants.ANALYST_VALIDATION_MODULE_MENU)) {
                Map analystWorklist = analystValidationService.fetchAnalystValidationWorklist(userDto, analystModPermSet, manageServiceUserDTO);
                modelView.addObject("engagementMap", analystWorklist.get("engagementMap"));
                modelView.addObject("serviceMap", analystWorklist.get("serviceMap"));
                modelView.addObject("organizationServieCount", analystWorklist.get("organizationServieCount"));
                modelView.addObject("statusMap", analystWorklist.get("statusMap"));
                modelView.setViewName("analyst/analystvalidationworklist");
            } else {
                modelView.setViewName("redirect:/logout.htm");
            }
        } catch (AppException e) {
            logger.error("Exception occurred : AnalystValidationController: analystValidationWorklist:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        logger.info("End: AnalystValidationController: analystValidationWorklist() to fetch Analyst Validation worklist");
        return modelView;
    }
    
    @InitBinder("vulnerabilityDTOs")
    protected void deleteVulnerabilityBinder(WebDataBinder binder) 
            throws Exception
    {
        binder.setAllowedFields(new String[]
                            {"clientEngagementDTO.encEngagementCode",
                             "instanceIdentifier",
                             "vulnerabilityName",
                             "securityServiceObj.securityServiceCode",
                             "cveInformationDTO.severityCode",
                             "ipAddress",
                             "statusCode",
                             "sourceCode",
                             "searchID",
                             "softwareName"
                            });
    }

    /**
     * This method for delete vulnerability
     *
     * @param request
     * @param response
     * @param redirectAttributes
     * @param vulnerabilityDTOs
     * @param model
     * @return modelView
     */
    @RequestMapping(value = "/deletevulnerability", method = RequestMethod.POST)
    public ModelAndView deleteVulnerability(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,
            @ModelAttribute(value = "vulnerabilityDTOs") VulnerabilityDTO vulnerabilityDTOs, Model model) {

        logger.info(INFO_USER_DEL_VAL);
//        logger.info("Start: AnalnlystValidationController: deleteVulnerability() to remove Vulnerability");
        ModelAndView modelView = new ModelAndView();
        UserProfileDTO userDto = null;
        String decEngCode = "";
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return new ModelAndView("redirect:/logout.htm");
            }
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(TRACE_USER_DELETE_VAL_LIST);
            }
            if (vulnerabilityDTOs.getClientEngagementDTO().getEncEngagementCode() != null
                    && !vulnerabilityDTOs.getClientEngagementDTO().getEncEngagementCode().equalsIgnoreCase("")) {
                decEngCode = encDec.decrypt(vulnerabilityDTOs.getClientEngagementDTO().getEncEngagementCode());
                vulnerabilityDTOs.getClientEngagementDTO().setEngagementCode(decEngCode);
            }
            if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.ANALYST_VALIDATION_MODULE_MENU,
                    PermissionConstants.REVIEW_VULNERABILITY)) {
                String orgSchema  = request.getParameter("orgSchma");
                vulnerabilityDTOs.setOrgSchema(orgSchema);
                int retVal = analystValidationService.deleteVulnerability(vulnerabilityDTOs,userDto.getUserProfileKey());
                if (0 < retVal) {
                    //Bug Id: IN021199: Included vulnerability id's for remove success message
                    StringBuilder returnString = new StringBuilder();
                    String delim = "";
                    String[] vulnerabilityInstances = vulnerabilityDTOs.getInstanceIdentifier().split(",");
                    for (String instance : vulnerabilityInstances) {
                        returnString.append(delim);
                        returnString.append(encDec.decrypt(instance));
                        delim = ", ";
                    }
                    redirectAttributes.addFlashAttribute("deleteSuccessMessage",
                            "'" + returnString + "' " + myProperties.getProperty("vulnerability.delete.success-message"));
                    //Bug Id: IN021199: Included vulnerability id's for remove success message
                }
                redirectAttributes.addFlashAttribute("cgenk", vulnerabilityDTOs.getClientEngagementDTO().getEncEngagementCode());
                int pgcnt = Integer.parseInt(request.getParameter("pgno"));
                redirectAttributes.addFlashAttribute("pgcnt", pgcnt);
                redirectAttributes.addFlashAttribute("orgSchma",orgSchema);
                modelView.setViewName("redirect:/reviewvulnerability.htm");
            } else {
                modelView.setViewName("redirect:/logout.htm");
            }
        } catch (AppException e) {
            logger.error("Exception occurred : AnalystValidationController: deleteVulnerability:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        logger.info("End: AnalystValidationController: deleteVulnerability() to remove Vulnerability");
        return modelView;
    }

    /**
     * Add new vulnerability
     *
     * @param vulnerabilityDTO
     * @param request
     * @param response
     * @param redirectAttributes
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(value = "/addnewvulnerability", method = RequestMethod.POST)
    public String addVulnerability(@ModelAttribute VulnerabilityDTO vulnerabilityDTO,
            final HttpServletRequest request, final HttpServletResponse response,
            RedirectAttributes redirectAttributes, BindingResult result, Model model) {

        logger.info(INFO_USER_ADD_NEW_VAL);
//        logger.info("Start: AnalystValidationController : reviewVulnerability");
        ModelAndView modelView = new ModelAndView();
        UserProfileDTO userDto = new UserProfileDTO();
        addVulnerabilityValidator.validate(vulnerabilityDTO, result);
        try {
           
            HttpSession session = request.getSession();
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(TRACE_USER_ADD_NEW_VAL);
            }
            if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.ANALYST_VALIDATION_MODULE_MENU,
                    PermissionConstants.REVIEW_VULNERABILITY)) {
                int pgcnt = Integer.parseInt(request.getParameter("pgcnt"));
                if (result.hasErrors()) {
                    redirectAttributes.addFlashAttribute("vulnerabilityDTO", vulnerabilityDTO);
                    redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.vulnerabilityDTO", result);
                    redirectAttributes.addFlashAttribute("cgenk", vulnerabilityDTO.getClientEngagementDTO().getEncEngagementCode());
                    redirectAttributes.addFlashAttribute("orgSchma",vulnerabilityDTO.getOrgSchema());
                    redirectAttributes.addAttribute("pgno", pgcnt);
                    return "redirect:/addvulnerabilityview.htm";
                }

                int retVal = analystValidationService.saveVulnerability(vulnerabilityDTO, userDto);
                if (0 < retVal) {
                    redirectAttributes.addFlashAttribute("addSuccessMessage",
                            "'" + retVal + "' " + myProperties.getProperty("vulnerability.add.success-message"));
                }
                redirectAttributes.addFlashAttribute("orgSchma",vulnerabilityDTO.getOrgSchema());
                redirectAttributes.addFlashAttribute("cgenk", vulnerabilityDTO.getClientEngagementDTO().getEncEngagementCode());
                redirectAttributes.addFlashAttribute("pgcnt", 1);
            } else {
                return "redirect:/logout.htm";
            }
        } catch (AppException e) {
            logger.debug("Exception Occured : AnalystValidationController : addVulnerability :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            modelView.addObject("exceptionBean", exception);
            return "/exception";
        }
        logger.info("End: AnalystValidationController : addVulnerability");
        return "redirect:/reviewvulnerability.htm";
    }

    @RequestMapping(value = "/updatevulnerability", method = RequestMethod.POST)
    public String updateVulnerability(@ModelAttribute VulnerabilityDTO vulnerabilityDTO,
            final HttpServletRequest request, final HttpServletResponse response,
            RedirectAttributes redirectAttributes, BindingResult result, Model model) {

        logger.info(INFO_USER_UPDATE_VAL);
//        logger.info("Start: AnalystValidationController : updateVulnerability");
        ModelAndView modelView = new ModelAndView();
        UserProfileDTO userDto = new UserProfileDTO();
        try {
            HttpSession session = request.getSession();
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(TRACE_USER_UPDATE_VAL_LIST);
            }
            if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.ANALYST_VALIDATION_MODULE_MENU,
                    PermissionConstants.REVIEW_VULNERABILITY)) {
                updateVulnerabilityValidator.validate(vulnerabilityDTO, result);
                int pgcnt = Integer.parseInt(request.getParameter("pgcnt"));
                if (result.hasErrors()) {
                    redirectAttributes.addFlashAttribute("vulnerabilityDTO", vulnerabilityDTO);
                    redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.vulnerabilityDTO", result);
                    redirectAttributes.addAttribute("pgno", pgcnt);
                    redirectAttributes.addAttribute("orgSchma", vulnerabilityDTO.getOrgSchema());
                    return "redirect:/editvulnerability.htm";
                }

                int retVal = analystValidationService.updateVulnerability(vulnerabilityDTO, userDto);
                if (0 < retVal) {
                    redirectAttributes.addFlashAttribute("addSuccessMessage",
                            "'" + vulnerabilityDTO.getInstanceIdentifier() + "' " + myProperties.getProperty("vulnerability.update.success-message"));
                }
                redirectAttributes.addFlashAttribute("cgenk", vulnerabilityDTO.getClientEngagementDTO().getEncEngagementCode());
                redirectAttributes.addFlashAttribute("pgcnt", pgcnt);
                redirectAttributes.addFlashAttribute("orgSchma",vulnerabilityDTO.getOrgSchema());
                redirectAttributes.addFlashAttribute("pageName", "CANCEL");
                
                if(request.getSession().getAttribute("ROADMAPOBJ") != null){
                    RoadMapDTO roadmapObj = (RoadMapDTO)request.getSession().getAttribute("ROADMAPOBJ");
                    redirectAttributes.addFlashAttribute("csacode", roadmapObj.getCsaDomainCode());
                    redirectAttributes.addFlashAttribute("catcode", roadmapObj.getRootCauseCode());
                    return "redirect:/editroadmap.htm";
                }
            } else {
                return "redirect:/logout.htm";
            }
        } catch (AppException e) {
            logger.debug("Exception Occured : AnalystValidationController : updateVulnerability : " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            modelView.addObject("exceptionBean", exception);
            return "/exception";
        }
        logger.info("End: AnalystValidationController : updateVulnerability");
        return "redirect:/reviewvulnerability.htm";
    }

    /**
     * This method for search analyst validation work list
     *
     * @param request
     * @param response
     * @param searchAnalystWorklist
     * @param redirectAttributes
     * @param model
     * @return modelView
     */
    @RequestMapping(value = "/searchanalystvalidationworklist")
    public ModelAndView searchAnalystValidationWorkList(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute(value = "searchAnalystWorklist") ManageServiceUserDTO searchAnalystWorklist,
            RedirectAttributes redirectAttributes, Model model) {

        logger.info(INFO_USER_SEARCH_ANALYST_VAL_WORKLIST);
//        logger.info("Start: AnalystValidationController: searchAnalystValidationWorkList() to search analyst validation work list");
        ModelAndView modelView = new ModelAndView();
        UserProfileDTO userDto = new UserProfileDTO();
        Set<String> analystPermissionSet = null;
        Set<String> engagementPermissionSet = null;
        Set<String> docUpPermSet = null;
        Set<String> analystModPermSet = null;
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return new ModelAndView("redirect:/logout.htm");
            }
            if(request.getParameter("cgenk")!= null){
            HttpSession session = request.getSession();
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(TRACE_USER_SEARCH_ANALYST_VAL_WORKLIST);
                analystPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.ANALYST_VALIDATION_MODULE_MENU);
                engagementPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU);
                docUpPermSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.ENGAGEMENT_DATA_UPLOAD_MENU);
                
                analystModPermSet = new HashSet<>();
                analystModPermSet.addAll(analystPermissionSet);
                if(engagementPermissionSet != null){
                    analystModPermSet.addAll(engagementPermissionSet);
                }
                if(docUpPermSet != null){
                    analystModPermSet.addAll(docUpPermSet);
                }
                
                modelView.addObject("permissions", analystPermissionSet);
            }
            if (permissionCheckHelper.checkUserSubMenuNotAdmin(userDto,
                    PermissionConstants.ANALYST_VALIDATION_MODULE_MENU)) {
                Map analystWorklist = analystValidationService.fetchAnalystValidationWorklist(userDto, analystModPermSet, searchAnalystWorklist);
                modelView.addObject("engagementMap", analystWorklist.get("engagementMap"));
                modelView.addObject("serviceMap", analystWorklist.get("serviceMap"));
                modelView.addObject("organizationServieCount", analystWorklist.get("organizationServieCount"));
                modelView.addObject("statusMap", analystWorklist.get("statusMap"));
                modelView.addObject("searchAnalystWorklist", searchAnalystWorklist);
                modelView.setViewName("analyst/analystvalidationworklist");
                } else {
                    modelView.setViewName("redirect:/logout.htm");
                }
            } else {
                modelView.setViewName("redirect:/logout.htm");
            }
        } catch (AppException e) {
            logger.error("Exception occurred : AnalystValidationController: searchAnalystValidationWorkList:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        logger.info("End: AnalystValidationController: searchAnalystValidationWorkList() to search analyst validation work list");
        return modelView;
    }

    /**
     *
     * @param request
     * @param response
     * @param vulnerabilityDTO
     * @param redirectAttributes
     * @param model
     * @return
     */
    @RequestMapping(value = "/addCVEInformation")
    public ModelAndView addCVEInformation(HttpServletRequest request, HttpServletResponse response, 
            @ModelAttribute(value = "vulnerabilityDTO") VulnerabilityDTO vulnerabilityDTO,
            RedirectAttributes redirectAttributes, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        UserProfileDTO userDto = null;
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return new ModelAndView("redirect:/logout.htm");
            }
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userDto.getEmail(), userDto.getUserMACAddress(), " User accessed addCVEInformation page"));
                //Bug Id: IN:021271
                Set<String> analystPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.ANALYST_VALIDATION_MODULE_MENU);
                modelAndView.addObject("permissions", analystPermissionSet);
                //Bug Id: IN:021271
            
                logger.trace(TRACE_USER_ADD_CVE_INFO);
            }
            if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.ANALYST_VALIDATION_MODULE_MENU,
                    PermissionConstants.REVIEW_VULNERABILITY)) {
                String cveIdentifier = vulnerabilityDTO.getCveInformationDTO().getCveIdentifier();
                vulnerabilityDTO.getCveInformationDTO().setCveIdentifier(cveIdentifier.toUpperCase());
                vulnerabilityDTO = analystValidationService.addCVEInformation(vulnerabilityDTO);
                
                /*
                 Display message if CVE ID is not present in our database
                */
                if (vulnerabilityDTO.getCveInfoExist() == 0) {
                    modelAndView.addObject("cveHubMessage", myProperties.getProperty("error.cveHubMessage"));
                }
                String decInstaceIdentifier = "";
                String instanceIdentifier = "";
                if(vulnerabilityDTO.getEncInstanceIdentifier() != null) {
                    instanceIdentifier = vulnerabilityDTO.getEncInstanceIdentifier().trim();
                } else {
                    return new ModelAndView("redirect:/logout.htm");
                }
                
                if (!StringUtils.isEmpty(instanceIdentifier)) {
                    decInstaceIdentifier = encDec.decrypt(instanceIdentifier);
                    vulnerabilityDTO.setInstanceIdentifier(decInstaceIdentifier);
                    if(!ApplicationConstants.DB_SOURCE_NAME.equalsIgnoreCase(vulnerabilityDTO.getSourceName())) {
                        vulnerabilityDTO = analystValidationService.fetchVulnerabilityBasicDetails(vulnerabilityDTO);
                    }
                }
                
                modelAndView.addObject("vulnerabilityDTO", vulnerabilityDTO);
                modelAndView.addObject("cveCompInfo", vulnerabilityDTO.getCveInformationDTO().getTotalComplianceMap());
                 modelAndView.addObject("comMap", vulnerabilityDTO.getCveInformationDTO().getComplianceMap());
                modelAndView.addObject("crossWalkMap", vulnerabilityDTO.getCveInformationDTO().getHitrustCrossWalkMap());
                //DECRYPTING THE ENCRYPTED ENGAGEMENT CODE
                String engagementCode = vulnerabilityDTO.getClientEngagementDTO().getEncEngagementCode();
                String decEngagementCode = "";
                if (engagementCode != null && !engagementCode.equalsIgnoreCase("")) {
                    decEngagementCode = encDec.decrypt(engagementCode);
                }
                //ENGAGEMENT DETAILS FOR THE ENGAGEMENT
                ClientEngagementDTO engagementDTO = manageEngagementsServiceImpl.viewEngagementByEngmtKey(decEngagementCode);
                engagementDTO.setOrgSchema(vulnerabilityDTO.getOrgSchema());
                modelAndView.addObject("engagementDTO", engagementDTO);

                //VULNERABILITY LIST FOR THE ENGAGEMENT
                //List<VulnerabilityDTO> vulnerabilityList = analystValidationService.vulnerabilityList(decEngagementCode);
                //modelAndView.addObject("vulnerabilityList", vulnerabilityList);

                //Services list along with their statuses for an engagement
                List<SecurityServiceDTO> servicesList = analystValidationService.analystEngagementServices(decEngagementCode);
                modelAndView.addObject("servicesList", servicesList);
                
                //OWASP List
                modelAndView.addObject("owaspList",analystValidationService.fetchOwaspList());
                
                //Operating System dropdown list
                List<MasterLookUpDTO> osList = analystValidationService.fetchOSList();
                modelAndView.addObject("osList", osList);

                //Analyst status dropdown list
                List<MasterLookUpDTO> statusList = analystValidationService.analystStatusList(ApplicationConstants.STATUS_VULNERABILITY);
                modelAndView.addObject("statusList", statusList);

                //Root cause dropdown list
                List<MasterLookUpDTO> rootCauseList = analystValidationService.vulnerabilityCategoryList();
                modelAndView.addObject("rootCauseList", rootCauseList);

                //Cost effort drop down list
                List<MasterLookUpDTO> costEffortList = analystValidationService.analystCostEffortList();
                modelAndView.addObject("costEffortList", costEffortList);
                
                //Fetch manual vulnerability names
                List<VulnerabilityDTO> manualVulnerabilities = analystValidationService.fetchManualVulnerabilityNames();
                modelAndView.addObject("manualVulnerabilities", manualVulnerabilities);

                //Page to display
                if (!vulnerabilityDTO.getPageName().equalsIgnoreCase("UPDATEPAGE")) {
                    modelAndView.addObject("pageName", "ADDPAGE");
                } else {
                    modelAndView.addObject("pageName", "UPDATEPAGE");
                }
                int pgcnt = Integer.parseInt(request.getParameter("pgcnt"));
                modelAndView.addObject("pgcnt", pgcnt);
                
                modelAndView.setViewName("/analyst/vulnerabilitypage");
            } else {
                modelAndView.setViewName("redirect:/logout.htm");
            }
        } catch (Exception e) {
            logger.debug("Exception Occured : AnalystValidationController : addCVEInformation :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/vcalculator")
    public String openVulnerabilityCalculator(final HttpServletRequest request,
            final HttpServletResponse response, Model model, RedirectAttributes redirectAttributes,
            @ModelAttribute("vulnerabilityDTO") VulnerabilityDTO vulnerabilityObj) {
        String retString = "";
        UserProfileDTO userDto = null;
        HttpSession session = request.getSession();
        session.setAttribute("vulnerabilityObj", null);
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            if (!StringUtils.isEmpty(request.getParameter("pgcnt"))) {
                if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                    userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                }
                if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.ANALYST_VALIDATION_MODULE_MENU,
                        PermissionConstants.REVIEW_VULNERABILITY)) {
                    Map<String, List<CVSSCalculatorDTO>> cvssMetricMap = cvssCalcService.
                            prepareCVSSCalculatorData("", "", vulnerabilityObj.getVectorText());

                    model.addAttribute("cvssDetailMap", cvssMetricMap);

                    int pgcnt = Integer.parseInt(request.getParameter("pgcnt"));
                    model.addAttribute("pgcnt", pgcnt);
                    model.addAttribute("orgSchma",vulnerabilityObj.getOrgSchema());

                    session.setAttribute("vulnerabilityObj", vulnerabilityObj);

                    retString = "/analyst/cvsscalculator";
                } else {
                    return "redirect:/logout.htm";
                }
            } else {
                return "redirect:/logout.htm";
            }
        } catch (Exception e) {
            logger.debug("Exception Occured : AnalystValidationController : openVulnerabilityCalculator  :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            retString = "/exception";
        }

        return retString;
    }

    @RequestMapping(value = "/bkfrmcalculator")
    public String BackFromCVSSCalculator(final HttpServletRequest request,
            final HttpServletResponse response, Model model,
            RedirectAttributes redirectAttrs) {
        String retString = "";
        HttpSession session = request.getSession();
        try {
            if(!commonUtil.refreshCheck(request, redirectAttrs, model)){
                return "redirect:/logout.htm";
            }
            if (session.getAttribute("vulnerabilityObj") != null) {

                VulnerabilityDTO vulnerabilityObj = (VulnerabilityDTO) session.getAttribute("vulnerabilityObj");

                redirectAttrs.addFlashAttribute("vulnerabilityDTO", vulnerabilityObj);
                
                redirectAttrs.addFlashAttribute("cgenk", request.getParameter("cgenk"));
                int pgcnt = Integer.parseInt(request.getParameter("pgcnt"));
                String orgSchema = request.getParameter("orgSchma");
                request.getParameter("orgSchema");
                redirectAttrs.addFlashAttribute("pgno", pgcnt);
                redirectAttrs.addFlashAttribute("orgSchma",orgSchema);

                if (vulnerabilityObj.getPageName().equalsIgnoreCase("ADDPAGE")) {
                    retString = "redirect:/addvulnerabilityview.htm";
                } else if (vulnerabilityObj.getPageName().equalsIgnoreCase("UPDATEPAGE")) {
                    retString = "redirect:/editvulnerability.htm";
                }
                session.setAttribute("vulnerabilityObj", null);
            } else {
                logger.error("Exception occurred : Session Invalidated:");
                ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"));
                model.addAttribute("exceptionBean", exception);
                retString = "/exception";
            }
        } catch (Exception e) {
            logger.debug("Exception Occured : AnalystValidationController : BackFromCVSSCalculator :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            retString = "/exception";
        }

        return retString;
    }

    @RequestMapping(value = "/addHitrust")
    public ModelAndView addHitrust(HttpServletRequest request, HttpServletResponse response, 
            @ModelAttribute(value = "vulnerabilityDTO") VulnerabilityDTO vulnerabilityDTO,
            RedirectAttributes redirectAttributes, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        UserProfileDTO userDto = null;
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return new ModelAndView("redirect:/logout.htm");
            }

            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                
                //Bug Id: IN:021271
                Set<String> analystPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.ANALYST_VALIDATION_MODULE_MENU);
                modelAndView.addObject("permissions", analystPermissionSet);
                //Bug Id: IN:021271
                
                logger.trace(TRACE_USER_ADD_HIT_RUST);
            }
            if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.ANALYST_VALIDATION_MODULE_MENU,
                    PermissionConstants.REVIEW_VULNERABILITY) && vulnerabilityDTO.getClientEngagementDTO().getEncEngagementCode() != null) {
                vulnerabilityDTO = analystValidationService.addHitrustToVulnerability(vulnerabilityDTO);
                
                String decInstaceIdentifier = "";
                String instanceIdentifier = vulnerabilityDTO.getEncInstanceIdentifier().trim();
                if (!StringUtils.isEmpty(instanceIdentifier)) {
                    decInstaceIdentifier = encDec.decrypt(instanceIdentifier);
                    vulnerabilityDTO.setInstanceIdentifier(decInstaceIdentifier);
                    if (!ApplicationConstants.DB_SOURCE_NAME.equalsIgnoreCase(vulnerabilityDTO.getSourceName())) {
                        vulnerabilityDTO = analystValidationService.fetchVulnerabilityBasicDetails(vulnerabilityDTO);
                    }
                }
                
                //Hitrust lookup Map
                Map<String, LinkedHashMap<String, ArrayList<ComplianceInfoDTO>>> complianceMap = analystValidationService.fetchComplianceInformation();
                modelAndView.addObject("vulnerabilityDTO", vulnerabilityDTO);
                modelAndView.addObject("cveCompInfo", complianceMap);
                modelAndView.addObject("comMap", vulnerabilityDTO.getCveInformationDTO().getComplianceMap());
                modelAndView.addObject("crossWalkMap", vulnerabilityDTO.getCveInformationDTO().getHitrustCrossWalkMap());
                modelAndView.addObject("orgSchma",vulnerabilityDTO.getOrgSchema());

                //DECRYPTING THE ENCRYPTED ENGAGEMENT CODE
                String engagementCode = vulnerabilityDTO.getClientEngagementDTO().getEncEngagementCode();
                String decEngagementCode = "";
                if (engagementCode != null && !engagementCode.equalsIgnoreCase("")) {
                    decEngagementCode = encDec.decrypt(engagementCode);
                }
                //ENGAGEMENT DETAILS FOR THE ENGAGEMENT
                ClientEngagementDTO engagementDTO = manageEngagementsServiceImpl.viewEngagementByEngmtKey(decEngagementCode);
                engagementDTO.setOrgSchema(vulnerabilityDTO.getOrgSchema());
                modelAndView.addObject("engagementDTO", engagementDTO);

                //VULNERABILITY LIST FOR THE ENGAGEMENT
                //List<VulnerabilityDTO> vulnerabilityList = analystValidationService.vulnerabilityList(decEngagementCode);
                //modelAndView.addObject("vulnerabilityList", vulnerabilityList);

                //Services list along with their statuses for an engagement
                List<SecurityServiceDTO> servicesList = analystValidationService.analystEngagementServices(decEngagementCode);
                modelAndView.addObject("servicesList", servicesList);

                //OWASP List
                modelAndView.addObject("owaspList",analystValidationService.fetchOwaspList());
                    
                //Operating System dropdown list
                List<MasterLookUpDTO> osList = analystValidationService.fetchOSList();
                modelAndView.addObject("osList", osList);
                
                //Analyst status dropdown list
                List<MasterLookUpDTO> statusList = analystValidationService.analystStatusList(ApplicationConstants.STATUS_VULNERABILITY);
                modelAndView.addObject("statusList", statusList);

                //Root cause dropdown list
                List<MasterLookUpDTO> rootCauseList = analystValidationService.vulnerabilityCategoryList();
                modelAndView.addObject("rootCauseList", rootCauseList);

                //Cost effort drop down list
                List<MasterLookUpDTO> costEffortList = analystValidationService.analystCostEffortList();
                modelAndView.addObject("costEffortList", costEffortList);
                
                //Fetch manual vulnerability names
                List<VulnerabilityDTO> manualVulnerabilities = analystValidationService.fetchManualVulnerabilityNames();
                modelAndView.addObject("manualVulnerabilities", manualVulnerabilities);
                
                //Page to display
                if (!vulnerabilityDTO.getPageName().equalsIgnoreCase("UPDATEPAGE")) {
                    modelAndView.addObject("pageName", "ADDPAGE");
                } else {
                    modelAndView.addObject("pageName", "UPDATEPAGE");
                }
                int pgcnt = Integer.parseInt(request.getParameter("pgcnt"));
                modelAndView.addObject("pgcnt", pgcnt);
                
                modelAndView.setViewName("/analyst/vulnerabilitypage");
            } else {
                modelAndView.setViewName("redirect:/logout.htm");
            }
        } catch (Exception e) {
            logger.debug("Exception occurred : AnalystValidationController : addHitrust :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        return modelAndView;
    }

    @InitBinder("vulnerabilityExport")
    protected void vulnerabilityExportBinder(WebDataBinder binder) 
            throws Exception
    {
        binder.setAllowedFields(new String[]
                            {"clientEngagementDTO.encEngagementCode",
                             "instanceIdentifier"   });
    }
    /**
     * This method for export to excel
     *
     * @param request
     * @param response
     * @param redirectAttributes
     * @param model
     * @param vulnerabilityExport
     * @return modelView
     */
    @RequestMapping(value = "/vulnerabilityexporttoexcel", method = RequestMethod.POST)
    public ModelAndView vulnerabilityExportToExcel(HttpServletRequest request, HttpServletResponse response, 
            RedirectAttributes redirectAttributes, Model model,
            @ModelAttribute(value = "vulnerabilityExport") VulnerabilityDTO vulnerabilityExport) {

        logger.info(INFO_USER_VAL_EXPORT_EXCEL);
//        logger.info("Start: AnalnlystValidationController: vulnerabilityExportToExcel() to export to excel");
        ModelAndView modelView = new ModelAndView();
        String decEngCode = "";
        UserProfileDTO userDto = null;
        String orgSchema = request.getParameter("orgSchma");
        
        try {
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
            }
            if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.ANALYST_VALIDATION_MODULE_MENU,
                    PermissionConstants.REVIEW_VULNERABILITY)) {
                if (vulnerabilityExport.getClientEngagementDTO().getEncEngagementCode() != null
                        && !vulnerabilityExport.getClientEngagementDTO().getEncEngagementCode().equalsIgnoreCase("")) {
                    decEngCode = encDec.decrypt(vulnerabilityExport.getClientEngagementDTO().getEncEngagementCode());
                    vulnerabilityExport.getClientEngagementDTO().setEngagementCode(decEngCode);
                }
                ClientEngagementDTO engagementDTO = manageEngagementsServiceImpl.viewEngagementByEngmtKey(decEngCode);
                vulnerabilityExport.setOrgSchema(orgSchema);
                List<VulnerabilityDTO> vulnerabilityList = analystValidationService.vulnerabilityDetailsForExcelExport(vulnerabilityExport);

                modelView.addObject("engagementDTO", engagementDTO);
                modelView.addObject("vulnerabilityList", vulnerabilityList);
                redirectAttributes.addFlashAttribute("exportSuccessMessage",
                        myProperties.getProperty("vulnerability.export.success-message"));
                modelView.setViewName("analystVulnerabilityExcelabilit");
            } else {
                modelView.setViewName("redirect:/logout.htm");
            }
        } catch (AppException e) {
            logger.error("Exception occurred : AnalystValidationController: vulnerabilityExportToExcel:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        logger.info("End: AnalystValidationController: vulnerabilityExportToExcel() to export to excel");
        return modelView;
    }

    @RequestMapping(value = "/updcalcvals", method = RequestMethod.POST)
    public String UpdateCVSSCaluculatedValues(final HttpServletRequest request,
            final HttpServletResponse response, RedirectAttributes redirectAttributes,
            @ModelAttribute(value = "cvssMetricObj") CVSSMetricsDTO cvssMetricsDto,
            Model model) {
        String retString = "";
        UserProfileDTO userDto = null;
        HttpSession session = request.getSession();
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }

            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
            }
            if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.ANALYST_VALIDATION_MODULE_MENU,
                    PermissionConstants.REVIEW_VULNERABILITY)) {
                if (session.getAttribute("vulnerabilityObj") != null) {
                    VulnerabilityDTO vulnerabilityDTO = (VulnerabilityDTO) session.getAttribute("vulnerabilityObj");
                    vulnerabilityDTO = cvssCalcService.CalculateCVSSMetricValues(cvssMetricsDto, vulnerabilityDTO);

                    redirectAttributes.addFlashAttribute("vulnerabilityDTO", vulnerabilityDTO);
                    
                    redirectAttributes.addFlashAttribute("cgenk", request.getParameter("cgenk"));
                    int pgcnt = Integer.parseInt(request.getParameter("pgcnt"));
                    redirectAttributes.addFlashAttribute("pgno", pgcnt);
                      String orgSchema = request.getParameter("orgSchma");
                      redirectAttributes.addFlashAttribute("orgSchma",orgSchema);

                    if (vulnerabilityDTO.getPageName().equalsIgnoreCase("ADDPAGE")) {
                        retString = "redirect:/addvulnerabilityview.htm";
                    } else if (vulnerabilityDTO.getPageName().equalsIgnoreCase("UPDATEPAGE")) {
                        retString = "redirect:/editvulnerability.htm";
                    }

                } else {
                    logger.error("Exception occurred : Session Invalidated:");
                    ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"));
                    model.addAttribute("exceptionBean", exception);
                    retString = "/exception";
                }
            }
        } catch (AppException e) {
            logger.error("Exception occurred : AnalystValidationController: UpdateCVSSCaluculatedValues:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            retString = "/exception";
        }
        return retString;
    }

    /**
     * This method for change status of service(s) as Reviewed / Not Reviewed
     * for an engagement
     *
     * @param request
     * @param response
     * @param vulnerabilityDto
     * @param redirectAttributes
     * @param model
     * @return modelView
     */
    @RequestMapping(value = "/updateservicestatus", method = RequestMethod.POST)
    public ModelAndView updateServicesStatus(HttpServletRequest request, HttpServletResponse response, 
            @ModelAttribute(value = "vulnerabilityDto") VulnerabilityDTO vulnerabilityDto, 
            RedirectAttributes redirectAttributes, Model model) {

        logger.info(INFO_USER_UPDATE_SERVICE_STATUS);
//        logger.info("Start: AnalnlystValidationController: updateServicesStatus() to change status of service(s)");
        UserProfileDTO userDto = null;
        ModelAndView modelView = new ModelAndView();
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return new ModelAndView("redirect:/logout.htm");
            }

            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(TRACE_USER_UPDATE_SERVICE_STATUS);
            }
            if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.ANALYST_VALIDATION_MODULE_MENU,
                    PermissionConstants.REVIEW_VULNERABILITY)) {
                String decEngagementCode = "";
                if (vulnerabilityDto.getClientEngagementDTO().getEncEngagementCode() != null
                        && !vulnerabilityDto.getClientEngagementDTO().getEncEngagementCode().equalsIgnoreCase("")) {
                    decEngagementCode = encDec.decrypt(vulnerabilityDto.getClientEngagementDTO().getEncEngagementCode());
                    vulnerabilityDto.getClientEngagementDTO().setEngagementCode(decEngagementCode);
                }
                int retVal = analystValidationService.updateServicesStatus(vulnerabilityDto, userDto);
                if (retVal > 0) {
                    redirectAttributes.addFlashAttribute("reviewSuccessMessage", myProperties.getProperty("vulnerability.review.success-message"));
                }
                modelView.setViewName("redirect:/analystvalidationworklist.htm");
            } else {
                modelView.setViewName("redirect:/logout.htm");
            }
        } catch (AppException e) {
            logger.error("Exception occurred : AnalystValidationController: updateServicesStatus:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        logger.info("Start: AnalnlystValidationController: updateServicesStatus() to change status of service(s)");
        return modelView;
    }

    /**
     * This method for load view vulnerability details when click on view link
     * in Review vulnerability list page
     *
     * @param vulnerabilityObj
     * @param request
     * @param response
     * @param redirectAttributes
     * @param model
     * @return modelView
     */
    @RequestMapping(value = "/viewreviewvulnerability")
    public ModelAndView viewReviewVulnerability(@ModelAttribute (value="vulnerabilityDTOs") VulnerabilityDTO vulnerabilityObj,
            HttpServletRequest request, HttpServletResponse response,
            RedirectAttributes redirectAttributes, Model model) {

        logger.info(INFO_USER_VIEW_VAL_REVIEW);
//        logger.info("Start: AnalystValidationController : updateVulnerabilityPage");
        ModelAndView modelView = new ModelAndView();
        UserProfileDTO userDto = null;
        Set<String> analystModPermSet = null;
        request.getSession().setAttribute("ROADMAPOBJ", null);
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return new ModelAndView("redirect:/logout.htm");
            }

            if (!StringUtils.isEmpty(request.getParameter("clientEngagementDTO.encEngagementCode")) ||
                    !StringUtils.isEmpty(request.getParameter("encInstanceIdentifier"))) {
                if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                    userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                    MDC.put("user", userDto.getEmail());
                    MDC.put("ip", userDto.getUserMACAddress());

                    //Bug Id: IN:021271
                    Set<String> analystPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.ANALYST_VALIDATION_MODULE_MENU);
                    Set<String> engagementPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU);
                    analystModPermSet = new HashSet<>();
                    analystModPermSet.addAll(analystPermissionSet);
                    if(engagementPermissionSet != null){
                        analystModPermSet.addAll(engagementPermissionSet);
                    }
                    modelView.addObject("permissions", analystModPermSet);
                    //Bug Id: IN:021271
                    //Bug Id: IN:21880
                    Set<String> permissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.CLIENT_REPORTS_UPLOAD);
                    modelView.addObject("clientreportspermission", permissionSet);
                    //Bug Id: IN:21880
                    logger.trace(TRACE_USER_VIEW_VAL_FOR_REVIEW_VAL);
                }
                if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.ANALYST_VALIDATION_MODULE_MENU,
                        PermissionConstants.VIEW_VULNERABILITY)) {
                    String decEngagementCode = "";
                    String decInstaceIdentifier = "";
                    if (!StringUtils.isEmpty(request.getParameter("clientEngagementDTO.encEngagementCode"))) {
                        decEngagementCode = encDec.decrypt(request.getParameter("clientEngagementDTO.encEngagementCode"));
                    }
                    if (!StringUtils.isEmpty(request.getParameter("encInstanceIdentifier"))) {
                        decInstaceIdentifier = encDec.decrypt(request.getParameter("encInstanceIdentifier"));
                    }
                    //Services list along with their statuses for an engagement
                    List<SecurityServiceDTO> servicesList = analystValidationService.analystEngagementServices(decEngagementCode);
                    modelView.addObject("servicesList", servicesList);

                    //OWASP List
                    modelView.addObject("owaspList",analystValidationService.fetchOwaspList());
                    
                    //Analyst status dropdown list
                    List<MasterLookUpDTO> statusList = analystValidationService.analystStatusList(ApplicationConstants.STATUS_VULNERABILITY);
                    modelView.addObject("statusList", statusList);
                    
                    //Severity dropdown list
                    List<SummaryDropDownModel> severityList = analystValidationService.analystSeverityList();
                    modelView.addObject("severityList", severityList);
                
                    //Source drop down list
                    List<MasterLookUpDTO> sourceList = analystValidationService.analystSourceList();
                    modelView.addObject("sourceList", sourceList);
                    
                      //scan tool list
                    List<MasterLookUpDTO> scanToolList = masterLookUpService.fetchMasterLookUpEntitiesByEntityType(ApplicationConstants.DB_CONSTANT_SCAN_TOOL);
                    model.addAttribute("scanToolList", scanToolList);
                    
                    String orgSchema = request.getParameter("orgSchma");
                    modelView.addObject("orgSchma",orgSchema);
                    
                    ClientEngagementDTO engagementDTO = manageEngagementsServiceImpl.viewEngagementByEngmtKey(decEngagementCode);
                    engagementDTO.setOrgSchema(orgSchema);
                    modelView.addObject("engagementDTO", engagementDTO);

                    int pgcnt = Integer.parseInt(request.getParameter("pgno"));
                    modelView.addObject("pgcnt", pgcnt);
                    modelView.addObject("pageNo", pgcnt);

                    vulnerabilityObj.setPageNo(pgcnt); //Page Number
                    if (System.getProperty("VUL_COUNT") != null) {
                        vulnerabilityObj.setRowCount(Integer.parseInt(System.getProperty("VUL_COUNT"))); //Number of records per page
                    } else {
                        vulnerabilityObj.setRowCount(100);
                    }
                    vulnerabilityObj.getClientEngagementDTO().setEngagementCode(decEngagementCode);

                    modelView.addObject("searchVuln", vulnerabilityObj);
                    vulnerabilityObj.setOrgSchema(orgSchema);
                    vulnerabilityObj.setRootCauseCode("");
                    List<VulnerabilityDTO> vulnerabilityList = analystValidationService.vulnerabilityList(vulnerabilityObj);
                    modelView.addObject("vulnerabilityList", vulnerabilityList);
                    
                    //TOTAL VUL COUNT AND NOT REVIEWED COUNT
                    if (vulnerabilityList != null && vulnerabilityList.size() > 0) {
                        VulnerabilityDTO vDto = vulnerabilityList.get(vulnerabilityList.size() - 1);
                        model.addAttribute("totalVulCount", vDto.getVulCount());
                        model.addAttribute("vulNotReviewedCount", vDto.getNotReviewedCount());
                    } else {
                        model.addAttribute("totalVulCount", 0);
                        model.addAttribute("vulNotReviewedCount", 0);
                    }

                    VulnerabilityDTO vulnerabilityDTO = analystValidationService.viewVulnerabilityDetails(Long.parseLong(decInstaceIdentifier),orgSchema);
                    modelView.addObject("vulnerabilityDTO", vulnerabilityDTO);
                    modelView.addObject("comMap", vulnerabilityDTO.getCveInformationDTO().getComplianceMap());
                    modelView.addObject("crossWalkMap", vulnerabilityDTO.getCveInformationDTO().getHitrustCrossWalkMap());

                    modelView.addObject("cgenk", request.getParameter("clientEngagementDTO.encEngagementCode"));

                    //Page to display
                    modelView.addObject("pageName", "REVIEWPAGE");
                    modelView.addObject("viewPageName", "VIEWPAGE");
                    modelView.setViewName("analyst/reviewvulnerability");
                } else {
                    modelView.setViewName("redirect:/logout.htm");
                }
            } else {
                modelView.setViewName("redirect:/logout.htm");
            }
        } catch (AppException e) {
            logger.debug("Exception occured : AnalystValidationController : viewReviewVulnerability : " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        logger.info("End: AnalystValidationController : updateVulnerabilityPage");
        return modelView;
    }
    
    /**This method is used to fetch CVE Lookup
     *
     * @param request
     * @param response
     * @param keyword
     * @param pgno
     * @param cveid
     * @param flag
     * @return
     */
    @JsonView(Views.Public.class)
    @RequestMapping(value = "/fetchcvelist")
    public @ResponseBody
    List<CVEInformationDTO> fetchCVEList(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("keyword") String keyword, @RequestParam("pgno") int pgno,
            @RequestParam("cveid") String cveid, @RequestParam("flag") String flag) {
        List<CVEInformationDTO> cveList = null;
        try {
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                UserProfileDTO userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(TRACE_USER_CVE_LIST);
            }
            VulnerabilityDTO vdto = new VulnerabilityDTO();
            vdto.setStatusCode(flag); //This is used as flag in procedure
            vdto.getCveInformationDTO().setCveID(cveid);
            vdto.getCveInformationDTO().setCveDesc(keyword);
            vdto.setPageNo(pgno);
            vdto.setRowCount(ApplicationConstants.CVE_COUNT_PER_PAGE);
            cveList = analystValidationService.fetchCVEList(vdto);
            
        } catch (Exception e) {
            logger.debug("Exception occured : fetchCVEList : " + e.getMessage());
        }
        return cveList;
    }
}
