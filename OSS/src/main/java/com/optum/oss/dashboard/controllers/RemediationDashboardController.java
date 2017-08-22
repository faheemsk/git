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
import com.optum.oss.dto.AssessmentFindingsCountDTO;
import com.optum.oss.dto.ExceptionDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.CSVFileCreationHelper;
import com.optum.oss.helper.PermissionCheckHelper;
import com.optum.oss.service.impl.MasterLookUpServiceImpl;
import com.optum.oss.service.impl.RemediationDashboardServiceImpl;
import com.optum.oss.service.impl.ReportsAndDashboardServiceImpl;
import com.optum.oss.util.CommonUtil;
import com.optum.oss.util.EncryptDecrypt;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author rpanuganti
 */
@Controller
public class RemediationDashboardController {

    private static final Logger logger = Logger.getLogger(RemediationDashboardController.class);

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
    private RemediationDashboardServiceImpl remediationDashboardServic;
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
    private final String TRACE_REMEDIATION_DASHBOARDS = "User accessed Remediation Dashboards";
    /*
     END : LOG MESSAGES
     */

    @RequestMapping(value = "/toRemediationDashBoard")
    public String toRemediationDashBoard(HttpServletRequest request, HttpServletResponse response, Model model) {
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
                logger.trace(TRACE_REMEDIATION_DASHBOARDS);

                if (session.getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_CODE) != null) {
                    engCode = (String) session.getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_CODE);
                    if (engCode != null && !engCode.equalsIgnoreCase("")) {
                        encCodeDec = engCode;
                    }
                }

                if (null != session.getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                    schemaName = session.getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
                }
              
                AssessmentFindingsCountDTO remediationPlanCount = remediationDashboardServic.fetchRemediationDashBoardFindingCount(encCodeDec, schemaName,checkExecutiveOrPersonalRemediationDashBoard(request));
                model.addAttribute("remediationPlanCount", remediationPlanCount);
            } else {
                return "/login";
            }
        } catch (Exception e) {
            logger.debug("Exception occured : toRemediationDashBoard : " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        return "dashboards/remediationDashboard";
    }

    @RequestMapping(value = "/remediationPlanCompletion.htm", method = RequestMethod.GET)
    public @ResponseBody
    List<String> remediationPlanCompletion(HttpServletRequest request, HttpServletResponse response) {
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

            data = remediationDashboardServic.remediationPlanCompletion(engCode, orgSchema,checkExecutiveOrPersonalRemediationDashBoard(request));
            if (!StringUtils.isEmpty(data)) {
                data = commonUtil.encodeHTMLEntities(data);
                retLi.add(data);
                return retLi;
            }
        } catch (Exception e) {
            logger.debug("Exceptionoccured : remediationPlanCompletion() : " + e.getMessage());
        }
        return retLi;
    }
//
    @RequestMapping(value = "/remediationOwnerDataByPlanStatus.htm", method = RequestMethod.GET)
    public @ResponseBody
    List<String> remediationOwnerDataByPlanStatus(HttpServletRequest request, HttpServletResponse response) {
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
            data = remediationDashboardServic.remediationOwnerDataByPlanStatus(engCode, orgSchema,checkExecutiveOrPersonalRemediationDashBoard(request));
            if (!StringUtils.isEmpty(data)) {
                data = commonUtil.encodeHTMLEntities(data);
                retLi.add(data);
                return retLi;
            }
        } catch (Exception e) {
            logger.debug("Exceptionoccured : remediationOwnerDataByPlanStatus() : " + e.getMessage());
        }
        return retLi;
    }
    
    @RequestMapping(value = "/remediationPlansByStatusAndSeverity.htm", method = RequestMethod.GET)
    public @ResponseBody
    List<String> remediationPlansByStatusAndSeverity(HttpServletRequest request, HttpServletResponse response) {
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
            data = remediationDashboardServic.remediationPlansByStatusAndSeverity(engCode, orgSchema,checkExecutiveOrPersonalRemediationDashBoard(request));
            if (!StringUtils.isEmpty(data)) {
                data = commonUtil.encodeHTMLEntities(data);
                retLi.add(data);
                return retLi;
            }
        } catch (Exception e) {
            logger.debug("Exceptionoccured : remediationPlansByStatusAndSeverity() : " + e.getMessage());
        }
        return retLi;
    }
    @RequestMapping(value = "/remediationPlansByDaysAndSeverity.htm", method = RequestMethod.GET)
    public @ResponseBody
    List<String> remediationPlansByDaysAndSeverity(HttpServletRequest request, HttpServletResponse response) {
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
            data = remediationDashboardServic.remediationPlansByDaysAndSeverity(engCode, orgSchema,checkExecutiveOrPersonalRemediationDashBoard(request));
            if (!StringUtils.isEmpty(data)) {
                data = commonUtil.encodeHTMLEntities(data);
                retLi.add(data);
                return retLi;
            }
        } catch (Exception e) {
            logger.debug("Exceptionoccured : remediationPlansByDaysAndSeverity() : " + e.getMessage());
        }
        return retLi;
    }
    
     private long checkExecutiveOrPersonalRemediationDashBoard(HttpServletRequest request) throws AppException {
        long retVal = 0;
        HttpSession session = request.getSession();
        Set<String> permissionSet = null;
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            UserProfileDTO userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
            permissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.REPORTS_MENU);
            if (permissionSet != null) {
                if (permissionSet.contains(PermissionConstants.EXECUTIVE_REMEDIATION_DASHBOARD)) {
                    retVal = 0;
                } else if (permissionSet.contains(PermissionConstants.PERSONAL_REMEDIATION_DASHBOARD)) {
                    retVal = userDto.getUserProfileKey();
                }
            }
            MDC.put("user", userDto.getEmail());
            MDC.put("ip", userDto.getUserMACAddress());
        }
        return retVal;
    }

}
