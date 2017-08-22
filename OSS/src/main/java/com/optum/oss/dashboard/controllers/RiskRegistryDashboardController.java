/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dashboard.controllers;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.constants.PermissionConstants;
import com.optum.oss.constants.ReportsAndDashboardConstants;
import com.optum.oss.constants.SessionConstants;
import com.optum.oss.controller.RemediationController;
import static com.optum.oss.controller.RoadMapController.logger;
import com.optum.oss.dao.impl.RiskRegistryDashboardDAOImpl;
import com.optum.oss.dto.AssessmentFindingsCountDTO;
import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.dto.EngagementsDTO;
import com.optum.oss.dto.ExceptionDTO;
import com.optum.oss.dto.RiskRegistryDashboardDTO;
import com.optum.oss.dto.RoadMapDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.CSVFileCreationHelper;
import com.optum.oss.helper.PermissionCheckHelper;
import com.optum.oss.model.FindingsModel;
import com.optum.oss.model.SecurityModel;
import com.optum.oss.model.SummaryDropDownModel;
import com.optum.oss.service.impl.MasterLookUpServiceImpl;
import com.optum.oss.service.impl.ReportsAndDashboardServiceImpl;
import com.optum.oss.service.impl.RiskRegistryDashboardServiceImpl;
import com.optum.oss.service.impl.RiskRegistryServiceImpl;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author rpanuganti
 */
@Controller
public class RiskRegistryDashboardController {

    private static final Logger logger = Logger.getLogger(RiskRegistryDashboardController.class);

    @Resource(name = "myProperties")
    private Properties myProperties;
    /*
     *Begin: Autowiring the required Class instances
     */
    @Autowired
    private EncryptDecrypt encDec;
    @Autowired
    PermissionCheckHelper permissionCheckHelper;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private MasterLookUpServiceImpl masterLookUpServiceImpl;

    @Autowired
    private RiskRegistryDashboardServiceImpl riskRegistryDashboardService;
    @Autowired
    private ReportsAndDashboardServiceImpl serviceImpl;
    @Autowired
    private CSVFileCreationHelper csvFileCreation;
    /*
     * End: Autowiring the required Class instances
     */
    /*
     START : LOG MESSAGES
     */
    private final String TRACE_RISK_REGISTRY_DASHBOARDS = "User accessed Risk Registry Dashboards";
    /*
     END : LOG MESSAGES
     */

    @RequestMapping(value = "/toRegistryDashBoard")
    public String toRegistryDashBoard(HttpServletRequest request, HttpServletResponse response, Model model) {
        HttpSession session = request.getSession();
        UserProfileDTO userDto = null;
        ModelAndView modelAndView = new ModelAndView();
        String engCode = "";
        String encCodeDec = "";
        String schemaName = "";
        try {
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(TRACE_RISK_REGISTRY_DASHBOARDS);

                if (session.getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_CODE) != null) {
                    engCode = (String) session.getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_CODE);
                    if (engCode != null && !engCode.equalsIgnoreCase("")) {
                        encCodeDec = engCode;
                    }
                }

                if (null != session.getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                    schemaName = session.getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
                }

                AssessmentFindingsCountDTO registryFindingCount = riskRegistryDashboardService.fetchRiskRegistryFindingCount(encCodeDec, schemaName,checkExecutiveOrPersonalRiskRegistryDashBoard(request));
                model.addAttribute("registryFindingCount", registryFindingCount);
            } else {
                return "/login";
            }
        } catch (Exception e) {
            logger.debug("Exception occured : toRegistryDashBoard : " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        return "dashboards/riskregistryDashboard";
    }

    @RequestMapping(value = "/registryDataByResponse.htm", method = RequestMethod.GET)
    public @ResponseBody
    List<String> registryDataByResponse(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String engCode = "";
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

            data = riskRegistryDashboardService.registryDataByResponse(engCode, orgSchema,checkExecutiveOrPersonalRiskRegistryDashBoard(request));
            if (!StringUtils.isEmpty(data)) {
                data = commonUtil.encodeHTMLEntities(data);
                retLi.add(data);
                return retLi;
            }
        } catch (Exception e) {
            logger.debug("Exceptionoccured : registryDataByResponse() : " + e.getMessage());
        }
        return retLi;
    }

    @RequestMapping(value = "/registryDataByOwner.htm", method = RequestMethod.GET)
    public @ResponseBody
    List<String> registryDataByOwner(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String engCode = "";
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
            data = riskRegistryDashboardService.registryDataByOwner(engCode, orgSchema,checkExecutiveOrPersonalRiskRegistryDashBoard(request));
            if (!StringUtils.isEmpty(data)) {
                data = commonUtil.encodeHTMLEntities(data);
                retLi.add(data);
                return retLi;
            }
        } catch (Exception e) {
            logger.debug("Exceptionoccured : registryDataByOwner() : " + e.getMessage());
        }
        return retLi;
    }

    @RequestMapping(value = "/registryDataBySeverity.htm", method = RequestMethod.GET)
    public @ResponseBody
    List<String> registryDataBySeverity(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String engCode = "";
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
            data = riskRegistryDashboardService.registryDataBySeverity(engCode, orgSchema,checkExecutiveOrPersonalRiskRegistryDashBoard(request));
            if (!StringUtils.isEmpty(data)) {
                data = commonUtil.encodeHTMLEntities(data);
                retLi.add(data);
                return retLi;
            }
        } catch (Exception e) {
            logger.debug("Exceptionoccured : registryDataBySeverity() : " + e.getMessage());
        }
        return retLi;
    }

    @RequestMapping(value = "/getRiskRegistryData.htm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<RiskRegistryDashboardDTO> getRiskRegistryData(HttpServletRequest req, HttpServletResponse res) {
        List<RiskRegistryDashboardDTO> registryList = new LinkedList<RiskRegistryDashboardDTO>();
        List<FindingsModel> findingsModels = new ArrayList<>();
        SecurityModel securityModel = new SecurityModel();
        String engCode = "";
        String schemaName = "";
        HttpSession session = req.getSession();
        String encCodeDec = "";
        try {
            if (session.getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_CODE);
                if (engCode != null && !engCode.equalsIgnoreCase("")) {
                    encCodeDec = engCode;
                }
            }
             String sevCode = req.getParameter("sevCode");
            if (sevCode != null) {
                sevCode = sevCode;
            } else {
                sevCode = "";
            }

            if (null != req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                schemaName = req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
            registryList = riskRegistryDashboardService.getRiskRegistryData(engCode, schemaName, sevCode,checkExecutiveOrPersonalRiskRegistryDashBoard(req));
        } catch (Exception e) {
            logger.debug("Exceptionoccured : getRiskRegistryData() : " + e.getMessage());
        }

        return registryList;
    }

    @RequestMapping(value = "/registryMapDataForCSVDownload.htm")
    public void registryMapDataForCSVDownload(HttpServletRequest request, HttpServletResponse response) {
        SecurityModel securityModel = new SecurityModel();
        HttpSession session = request.getSession();
        List dbHeaders = new ArrayList();
        List csvHeaders = new ArrayList();
        String engCode = "";
        String encEngCode = "";
        String schemaName = "";
        String flag = "";

        try {

            if (session.getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_CODE);
                if (engCode != null && !engCode.equalsIgnoreCase("")) {
                    encEngCode = encDec.encrypt(engCode);
                    securityModel.getEngagementsDTO().setEngagementKey(encEngCode);
                }

            }
            if (null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                securityModel.setEngSchemaName(request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString());
                schemaName = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
//             String categeoryCode=request.getParameter("catCode");
//           if(!StringUtils.isEmpty(categeoryCode)){
//           securityModel.setCategoryCode(categeoryCode);
//           }else{
//           securityModel.setCategoryCode("");
//           }

            List listOfFindings = riskRegistryDashboardService.riskRegistryDataForExportCSV(engCode, schemaName, flag,checkExecutiveOrPersonalRiskRegistryDashBoard(request));
            EngagementsDTO engagementsDTO = serviceImpl.getEngagementDetailsForExportCSV(securityModel);

            csvFileCreation.prepareCSVFile(listOfFindings, engagementsDTO);
        } catch (Exception ie) {
            logger.debug("Exception Occured while Executing the registryMapDataForCSVDownload Action :: " + ie.getMessage());
        }

    }
    private long checkExecutiveOrPersonalRiskRegistryDashBoard(HttpServletRequest request) throws AppException {
        long retVal = 0;
        HttpSession session = request.getSession();
        Set<String> permissionSet = null;
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            UserProfileDTO userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
            permissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.REPORTS_MENU);
            if (permissionSet != null) {
                if (permissionSet.contains(PermissionConstants.EXECUTIVE_REGISTRY_DASHBOARD)) {
                    retVal = 0;
                } else if (permissionSet.contains(PermissionConstants.PERSONAL_REGISTRY_DASHBOARD)) {
                    retVal = userDto.getUserProfileKey();
                }
            }
            MDC.put("user", userDto.getEmail());
            MDC.put("ip", userDto.getUserMACAddress());
        }
        return retVal;
    }
}
