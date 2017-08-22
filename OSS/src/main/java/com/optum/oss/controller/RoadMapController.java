/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.controller;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.constants.PermissionConstants;
import com.optum.oss.constants.ReportsAndDashboardConstants;
import com.optum.oss.constants.SessionConstants;
import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.dto.EngagementsDTO;
import com.optum.oss.dto.ExceptionDTO;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.RoadMapDTO;
import com.optum.oss.dto.SecurityServiceDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.dto.VulnerabilityDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.CSVFileCreationHelper;
import com.optum.oss.helper.PermissionCheckHelper;
import com.optum.oss.model.FindingsModel;
import com.optum.oss.model.SecurityModel;
import com.optum.oss.model.SummaryDropDownModel;
import com.optum.oss.service.impl.AnalystValidationServiceImpl;
import com.optum.oss.service.impl.ManageEngagementsServiceImpl;
import com.optum.oss.service.impl.ReportsAndDashboardServiceImpl;
import com.optum.oss.service.impl.RoadMapServiceImpl;
import com.optum.oss.util.CommonUtil;
import com.optum.oss.util.EncryptDecrypt;
import java.util.ArrayList;
import java.util.LinkedList;
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
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author sbhagavatula
 */
@Controller
public class RoadMapController {
    
    public static final Logger logger = Logger.getLogger(RoadMapController.class);
    
    @Resource(name = "myProperties")
    private Properties myProperties;
    
    @Autowired
    private RoadMapServiceImpl roadMapService;
    @Autowired
    private EncryptDecrypt encDec;
    @Autowired
    PermissionCheckHelper permissionCheckHelper;
    @Autowired
    private AnalystValidationServiceImpl analystValidationService;
    @Autowired
    private ManageEngagementsServiceImpl manageEngagementsServiceImpl;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private ReportsAndDashboardServiceImpl serviceImpl;
    @Autowired
    private CSVFileCreationHelper csvFileCreation;
    
    private final String TRACE_ROADMAP_SUMMARY = "User accessed roadmap summary view";
    private final String TRACE_CREATE_ROADMAP_LIST = "User accessed roadmap vulnerabilities category";
    private final String TRACE_EDIT_ROADMAP = "User accessed edit roadmap";
    private final String TRACE_UPDATE_ROADMAP = "User accessed update roadmap category";
    private final String TRACE_ROADMAP_SUMMARY_CHART = "User accessed roadmap summary chart";
    
    @RequestMapping(value="/roadmapsummary")
    public String roadMapSummary(@ModelAttribute (value="roadMapDTO") RoadMapDTO roadMapDTO,
            HttpServletRequest request, HttpServletResponse response, Model model,
            RedirectAttributes redirectAttributes){
        logger.info(TRACE_ROADMAP_SUMMARY);
        UserProfileDTO userDto = new UserProfileDTO();
        Set<String> permissionSet = null;
        try {
            HttpSession session = request.getSession();
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(TRACE_ROADMAP_SUMMARY);
                permissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.ANALYST_VALIDATION_MODULE_MENU);
                model.addAttribute("permissions", permissionSet);
            }
            
            if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU,
                        PermissionConstants.ADD_USER_TO_SERVICE)) {
                ClientEngagementDTO engagementDTO = roadMapService.engagementInfoByEngCode(roadMapDTO);
                model.addAttribute("engagementDTO", engagementDTO);

                List<RoadMapDTO> roadmapList = roadMapService.roadmapCategoryByEngCode(roadMapDTO);
                model.addAttribute("roadmapList", roadmapList);
            } else {
                return "redirect:/logout.htm";
            }
        } catch (AppException e) {
            logger.debug("Exception occured : roadMapSummary : " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        return "analyst/roadmapsummary";
    }
    
    @RequestMapping(value="/createroadmapcat")
    public String createRoadMapCategory(@ModelAttribute (value="roadMapDTO") RoadMapDTO roadMapDTO,
            HttpServletRequest request, HttpServletResponse response, Model model,
            RedirectAttributes redirectAttributes){
        logger.info(TRACE_CREATE_ROADMAP_LIST);
        UserProfileDTO userDto = new UserProfileDTO();
        Set<String> permissionSet = null;
        try {
            HttpSession session = request.getSession();
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(TRACE_CREATE_ROADMAP_LIST);
                permissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.ANALYST_VALIDATION_MODULE_MENU);
                model.addAttribute("permissions", permissionSet);
            }
            
            roadMapService.createRoadmapCategory(roadMapDTO,userDto);
            
            ClientEngagementDTO engagementDTO = roadMapService.engagementInfoByEngCode(roadMapDTO);
            model.addAttribute("engagementDTO", engagementDTO);
            
            redirectAttributes.addFlashAttribute("roadMapDTO", roadMapDTO);
            request.getSession().setAttribute("ROADMAPOBJ", null);
        } catch (AppException e) {
            logger.debug("Exception occured : roadMapSummary : " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        return "redirect:/roadmapsummary.htm";
    }
    
    @RequestMapping(value = "/editroadmap")
    public String editRoadmap(@ModelAttribute (value="vulnerabilityDTOs") VulnerabilityDTO vulnerabilityDTOs, 
            HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,
            Model model, @ModelAttribute(value = "cgenk") String encryptedEngCode, @ModelAttribute(value = "pgcnt") String pageCount,
            @ModelAttribute(value = "orgSchma") String orgSchema) {
        logger.info(TRACE_EDIT_ROADMAP);
        UserProfileDTO userDto = null;
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

                    Set<String> analystPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.ANALYST_VALIDATION_MODULE_MENU);
                    Set<String> permissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.CLIENT_REPORTS_UPLOAD);
                    model.addAttribute("permissions", analystPermissionSet);
                    model.addAttribute("clientreportspermission", permissionSet);

                    logger.trace(TRACE_EDIT_ROADMAP);
                }
                if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU,
                        PermissionConstants.ADD_USER_TO_SERVICE)) {
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
                        vulnerabilityDTOs.setRowCount(ApplicationConstants.CVE_COUNT_PER_PAGE);
                    }

                    vulnerabilityDTOs.getClientEngagementDTO().setEngagementCode(decEngagementCode);
                    
                    request.getSession().setAttribute("searchVuln", vulnerabilityDTOs);

                    model.addAttribute("searchVuln", vulnerabilityDTOs);
                    vulnerabilityDTOs.setOrgSchema(orgSchema);

                    String catcode = request.getParameter("catcode") == null? (String)model.asMap().get("catcode") : request.getParameter("catcode");
                    String csacode = request.getParameter("csacode") == null? (String)model.asMap().get("csacode") : request.getParameter("csacode");
                    vulnerabilityDTOs.setRootCauseCode(catcode);
                    vulnerabilityDTOs.setStatusCode("V");
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
                    List<MasterLookUpDTO> statusList = analystValidationService.analystStatusList(ApplicationConstants.STATUS_VULNERABILITY);
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

                    //Severity dropdown list
                    List<SummaryDropDownModel> severityList = analystValidationService.analystSeverityList();
                    model.addAttribute("severityList", severityList);
                    
                    //Timeline dropdown list
                    List<MasterLookUpDTO> timeLineList = roadMapService.timeLineList();
                    model.addAttribute("timeLineList", timeLineList);

                    //Page to display
                    model.addAttribute("pageName", "REVIEWPAGE");
                    
                    //Roadmap Info
                    RoadMapDTO rmapobj = new RoadMapDTO();
                    rmapobj.getClientEngagementDTO().setEngagementCode(decEngagementCode);
                    rmapobj.setOrgSchema(orgSchema);
                    rmapobj.setCsaDomainCode(csacode);
                    rmapobj.setRootCauseCode(catcode);
                    List<RoadMapDTO> roadmapObjs = roadMapService.fetchRoadmapInfo(rmapobj);
                    model.addAttribute("roadmapDTO", roadmapObjs.get(0));
                    request.getSession().setAttribute("ROADMAPOBJ", roadmapObjs.get(0));
                    return "analyst/updateroadmap";
                } else {
                    return "redirect:/logout.htm";
                }
            } else {
                return "redirect:/logout.htm";
            }
        } catch (AppException e) {
            logger.debug("Exception occured : editRoadmap : " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
    }
    
    @RequestMapping(value="/updateroadmapcat")
    public String updateRoadMapCategory(@ModelAttribute (value="roadMapDTO") RoadMapDTO roadMapDTO,
            HttpServletRequest request, HttpServletResponse response, Model model,
            RedirectAttributes redirectAttributes){
        logger.info(TRACE_UPDATE_ROADMAP);
        UserProfileDTO userDto = new UserProfileDTO();
        Set<String> permissionSet = null;
        try {
            HttpSession session = request.getSession();
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(TRACE_UPDATE_ROADMAP);
                permissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.ANALYST_VALIDATION_MODULE_MENU);
                model.addAttribute("permissions", permissionSet);
            }
            
            int retval = roadMapService.updateRoadmapCategory(roadMapDTO, userDto);
            
            ClientEngagementDTO engagementDTO = roadMapService.engagementInfoByEngCode(roadMapDTO);
            model.addAttribute("engagementDTO", engagementDTO);
            
            redirectAttributes.addFlashAttribute("roadMapDTO", roadMapDTO);
            
            if (retval > 0) {
                redirectAttributes.addFlashAttribute("roadmapStatusMessage",
                        myProperties.getProperty("roadmap.update.success-message"));
                return "redirect:/roadmapsummary.htm";
            } else {
                ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"));
                model.addAttribute("exceptionBean", exception);
                return "/exception";
            }
        } catch (AppException e) {
            logger.debug("Exception occured : updateRoadMapCategory : " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        
    }
    
      @RequestMapping(value = "/roadMapData.htm", method = RequestMethod.GET)
    public @ResponseBody
    List<String> roadMapSummaryCharData(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String engCode = "";
        String serviceCode = "";
        String data = "";
        List<String> retLi = new LinkedList<String>();
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if (null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                orgSchema = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }

            if (session.getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_CODE);
            }

            /* setting the orgSchema and EngagementCode to the RoadMapDTO */
            RoadMapDTO roadMapDto = new RoadMapDTO();
            ClientEngagementDTO engDTO = new ClientEngagementDTO();
            engDTO.setEngagementCode(engCode);
            roadMapDto.setOrgSchema(orgSchema);
            roadMapDto.setClientEngagementDTO(engDTO);
            
            data = roadMapService.fetchRoadMapSummaryChartData(roadMapDto);
            if (!StringUtils.isEmpty(data)) {
                data = commonUtil.encodeHTMLEntities(data);
                retLi.add(data);
                return retLi;
            }
        } catch (Exception e) {
            logger.debug("Exceptionoccured : roadMapSummaryCharData() : " + e.getMessage());
        }
        return retLi;
    }

    @RequestMapping(value = "/toRoadMap")
    public String toRoadMapPage(HttpServletRequest request, HttpServletResponse response, Model model) {
        HttpSession session = request.getSession();
        UserProfileDTO userDto = null;
        try {
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(TRACE_ROADMAP_SUMMARY_CHART);
            } else {
                return "/login";
            }
        } catch (Exception e) {
            logger.debug("Exception occured : toRoadMapPage : " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        return "dashboards/roadmap";
    }
    
    @RequestMapping(value = "/getRoadMapFindingsData.htm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<FindingsModel> getRoadMapFindingsData(HttpServletRequest req, HttpServletResponse res) {
        List<FindingsModel> findingsModels = new ArrayList<>();
        SecurityModel securityModel = new SecurityModel();
        String engCode = "";
        String schemaName="";
        HttpSession session = req.getSession();
        String encCodeDec="";
        try {
            if (session.getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_CODE);
                if (engCode != null && !engCode.equalsIgnoreCase("")) {
                    encCodeDec=engCode;
                    engCode = encDec.encrypt(engCode);
                }
            }
            securityModel.getEngagementsDTO().setEngagementKey(engCode);
            String fndId = req.getParameter("catCode");
            if (fndId != null) {
                securityModel.setCategoryCode(fndId);
            } else {
                securityModel.setCategoryCode("");
            }

            if (null != req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                schemaName=req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
                securityModel.setEngSchemaName(schemaName);
            }
            
            /* setting the orgSchema and EngagementCode to the RoadMapDTO */
            RoadMapDTO roadMapDto = new RoadMapDTO();
            ClientEngagementDTO engDTO = new ClientEngagementDTO();
            engDTO.setEngagementCode(encCodeDec);
            roadMapDto.setOrgSchema(schemaName);
            roadMapDto.setClientEngagementDTO(engDTO);
            roadMapDto.setCsaDomainCode("");
            roadMapDto.setRootCauseCode("");
            List<RoadMapDTO> categeryList = roadMapService.fetchRoadmapInfo(roadMapDto);
            
            if (categeryList != null && categeryList.size() > 0) {
                findingsModels = roadMapService.getFindingsByCategory(securityModel);
            }
        } catch (AppException e) {
            logger.debug("Exceptionoccured : getRoadMapFindingsData() : " + e.getMessage());
        }

        return findingsModels;
    }
    
    @RequestMapping(value = "/roadMapDataForCSVDownload.htm")
    public void roadMapDataForCSVDownload(HttpServletRequest request, HttpServletResponse response, @RequestBody List<SummaryDropDownModel> summaryDropDownModel) {
        SecurityModel securityModel = new SecurityModel();
        HttpSession session = request.getSession();
        List dbHeaders = new ArrayList();
        List csvHeaders = new ArrayList();
        String engCode="";

        try {

            if (session.getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_CODE);
                if (engCode != null && !engCode.equalsIgnoreCase("")) {
                    engCode = encDec.encrypt(engCode);
                    securityModel.getEngagementsDTO().setEngagementKey(engCode);
                }
            }
            if (null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                securityModel.setEngSchemaName(request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString());
            }
            
           String categeoryCode=request.getParameter("catCode");
           if(!StringUtils.isEmpty(categeoryCode)){
           securityModel.setCategoryCode(categeoryCode);
           }else{
           securityModel.setCategoryCode("");
           }
            for (SummaryDropDownModel summaryModel1 : summaryDropDownModel) {
                String maker = summaryModel1.getMaker();
                dbHeaders.add(summaryModel1.getMaker());
                csvHeaders.add(summaryModel1.getLabel());
            }
            
            securityModel.setCsvDBHeaders(dbHeaders);
            securityModel.setCsvHeaders(csvHeaders);
            
            List listOfFindings = roadMapService.roadMapDataForExportCSV( securityModel);
            EngagementsDTO engagementsDTO = serviceImpl.getEngagementDetailsForExportCSV(securityModel);
            
            csvFileCreation.prepareCSVFile(listOfFindings, engagementsDTO);


        } catch (Exception ie) {
            logger.debug("Exception Occured while Executing the roadMapDataForCSVDownload Action :: " + ie.getMessage());
        }

    }
}
