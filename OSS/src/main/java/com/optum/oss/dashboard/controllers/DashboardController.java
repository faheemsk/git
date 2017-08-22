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
import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.dto.EngagementsDTO;
import com.optum.oss.dto.ExceptionDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.CSVFileCreationHelper;
import com.optum.oss.model.AssessmentServicesModel;
import com.optum.oss.model.FindingsModel;
import com.optum.oss.model.HeadersActionsModel;
import com.optum.oss.model.RemediationChartModel;
import com.optum.oss.model.RemidiationDataModel;
import com.optum.oss.model.ReportsModel;
import com.optum.oss.model.SecurityModel;
import com.optum.oss.model.SummaryDropDownModel;
import com.optum.oss.service.impl.ReportsAndDashboardServiceImpl;
import com.optum.oss.util.CommonUtil;
import com.optum.oss.util.EncryptDecrypt;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author mrejeti
 */
@Controller

public class DashboardController {
    /*
     LOGGER MESSAGES START
     */

    private static final Logger logger = Logger.getLogger(DashboardController.class);

    private final String INFO_USER_ACCESSED_DASHBOARD = "User accessed accessed dashboard page";
    private final String INFO_USER_ACCESSED_ENGAGEMENT_DASHBOARD = "User accessed accessed engagment dashboard page";
    private final String INFO_USER_DASHBOARD_CONTROLLER = "User accessed  in dashboardcontroller";
    private final String INFO_USER_GET_ENGAGEMENT = "User accessed  Engagement";
    private final String INFO_USER_ACCESSED_TO_ASSESSMENTPAGE = "User accessed   to Engagemen page";
    private final String INFO_USER_ACCESSED_TO_REMIDIATIONPAGE = "User accessed  to remididiation page";
    private final String INFO_USER_ACCESSED_FETCH_REMIDIATION_CHARTS = "User accessed  to remididiation charts page";
    private final String INFO_USER_FETCH_REMIDIATION_VULNERABILITIES = "User accessed  to fetch remididiation vul page";

    /*
     LOGGER MESSAGES END
     */
    @Autowired
    private ReportsAndDashboardServiceImpl serviceImpl;

    @Autowired
    private EncryptDecrypt encryptDecrypt;
    @Autowired
    private CSVFileCreationHelper csvFileCreation;
    @Autowired
    private CommonUtil commonUtil;

    @Resource(name = "myProperties")
    private Properties myProperties;
    /*
     THIS METHOD IS HOMECONTROLLER FOR ANALYST AND CLIENT USERS- FIRST ENGAGEMENT WILL BE SHOWN BY DEFAULT.
     */

    @RequestMapping(value = "/dashboardhomecontroller")
    public ModelAndView dashboardHomeController(HttpServletRequest req, HttpServletResponse res,RedirectAttributes redirectAttributes,Model model) {
        ModelAndView modelAndView = new ModelAndView();
        UserProfileDTO userDto = new UserProfileDTO();
        logger.info(INFO_USER_DASHBOARD_CONTROLLER);
        try {
            
            if (req.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) req.getSession().getAttribute(SessionConstants.USER_SESSION);
                String flag = ApplicationConstants.EMPTY_STRING;
                if (userDto.getSubMenuPermissionMap().containsKey(PermissionConstants.REMEDIATE_PROJECT_MANAGER)) {
                    flag = ApplicationConstants.DB_CONSTANT_DASHBOARD_REMEDIATION;
                    req.getSession().setAttribute("publishFlag", ApplicationConstants.DB_CONSTANT_DASHBOARD_REMEDIATION);
                } else {
                    flag = ApplicationConstants.DB_CONSTANT_DASHBOARD_NON_REMEDIATION;
                    req.getSession().removeAttribute("publishFlag");
                }
                
                // FETCH ENGAGEMETNS LIST
                List<EngagementsDTO> engList = serviceImpl.fetchEngagments(flag,userDto.getOrganizationKey(), userDto.getUserTypeKey(), userDto.getUserProfileKey());
                if (null != engList && engList.size() > 0) {
                    // logger.info("list size in dashboardHomeController" + engList.size());
                    if( null != req.getSession().getAttribute("from")){
                        req.getSession().removeAttribute("from");
                        req.getSession().removeAttribute("encEngCodeForBack");
                    }
                    String engPkgType = engList.get(0).getEngagementPkgType();
                    String engKey = engList.get(0).getEngagementKey();
                    String engPkgName = engList.get(0).getEngagementPkgName();
                      redirectAttributes.addFlashAttribute("engageKey", engKey);
                    modelAndView.setViewName("redirect:/toengagementhomepage.htm");
                } else {
                        modelAndView.setViewName("dashboards/noengagements");
                }
            }
            else{
                  return new ModelAndView("/login");
            }
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "dashboardHomeController Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", "failed", e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/engsummary")
    public ModelAndView engagmentSummeryPage(HttpServletRequest req, HttpServletResponse res) {
        ModelAndView modelAndView = new ModelAndView();
        if (null != req.getSession().getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_PACKAGE_TYPE)) {
            modelAndView.setViewName("redirect:/toengagementhomepage.htm?engKey=" + req.getSession().getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_KEY));
        }
        return modelAndView;
    }

    @InitBinder("pageSecModel")
    private void engagementDataUploadBinder(WebDataBinder binder)
            throws Exception {
        binder.setAllowedFields(new String[]{
            "engagementKey", "engStatusKey", "engStatusValue"
        });

    }

    @RequestMapping(value = "/toengagementhomepage")
    public ModelAndView toEngagementHomePage(HttpServletRequest req, HttpServletResponse res,
            @ModelAttribute("engageKey") String engageKey,
            @ModelAttribute("pageSecModel") SecurityModel securityModel) {
        ModelAndView modelAndView = new ModelAndView();
          HttpSession session = req.getSession();
        try {
             if (session.getAttribute(SessionConstants.USER_SESSION) == null) {
                return new ModelAndView("/login");
            }
            // FETCH ENGAGMENT DETAILS BY ENGCODE
            ClientEngagementDTO dto = new ClientEngagementDTO();
            String engKey = "";
            if (req.getParameter("engKey") != null || !engageKey.equalsIgnoreCase("")) {
                if (!StringUtils.isEmpty(req.getParameter("engKey"))) {
                    //remaining business code
                    engKey = req.getParameter("engKey");
                } else if (!engageKey.equalsIgnoreCase("")) {
                    engKey = engageKey;
                    } else {
                    modelAndView.setViewName("redirect:/logout.htm");
                }
                    //            if(engKey ==null){
//                engKey = req.getSession().getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_KEY)+"";
//            }
                    dto = serviceImpl.fetchEngagmentDetailsByEngKey(encryptDecrypt.decrypt(engKey));
                    if (null != dto) {
                        //SETTING ENGAGEMENT SCHEMA NAME IN SESSION
                        String engSchema = serviceImpl.getSchemaNameByEngKey(engKey);
                        req.getSession().setAttribute(ReportsAndDashboardConstants.ENG_SCHEMA, engSchema);
                        
                        // FETCHING THE TOTAL FINDINGS COUNT - FOR SUMMARY PAGE THERE IS NO  SERVICE CODE, ONLY ENGAGMENTKEY IS REQUIRED.
                        modelAndView.addObject("summaryfindingcounts", serviceImpl.fetchAssessmentsFindliingCounts(encryptDecrypt.decrypt(engKey), "",engSchema));
                        
                        // SETTING ENGAGMENT DETAILS IN SESSION
                        securityModel.getEngagementsDTO().setEngagementKey(engKey);
                        securityModel.getEngagementsDTO().setEngStatusKey(dto.getEngagementStatusKey());
                        securityModel.getEngagementsDTO().setEngStatusValue(dto.getRowStatusValue());
                        req.getSession().setAttribute(ReportsAndDashboardConstants.ENGAGEMENT_PACKAGE_TYPE, dto.getSecurityPackageObj().getEncSecurityPackageCode());
                        req.getSession().setAttribute(ReportsAndDashboardConstants.ENGAGEMENT_KEY, dto.getEncEngagementCode());
                        req.getSession().setAttribute(ReportsAndDashboardConstants.ENGAGEMENT_PACKAGE_NAME, dto.getSecurityPackageObj().getSecurityPackageName());
                        req.getSession().setAttribute(ReportsAndDashboardConstants.ENGAGEMENT_NAME, dto.getEngagementName());
                        req.getSession().setAttribute(ReportsAndDashboardConstants.ENGAGEMENT_CODE, dto.getEngagementCode());
                        req.getSession().setAttribute(ReportsAndDashboardConstants.ENGAGEMENT_CLIENT_ORG_NAME, dto.getClientName());
                        req.getSession().setAttribute(ReportsAndDashboardConstants.ENGAGEMENT_OBJECT, dto);
                        modelAndView.addObject("securityModel", securityModel);
                        logger.info(INFO_USER_ACCESSED_ENGAGEMENT_DASHBOARD + " engagment code::" + "");
//                        modelAndView.setViewName("dashboards/dashboardhome");
                        
                        /* Navigation Based on roles and permissions */
                        String decodeEngPkgType = encryptDecrypt.decrypt(dto.getSecurityPackageObj().getEncSecurityPackageCode());
                        UserProfileDTO userDto = new UserProfileDTO();
                        Set<String> permissionSet = null;
                        userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                        permissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.REPORTS_MENU);
                        if (permissionSet != null) {
                            
                            if (permissionSet.contains(PermissionConstants.DASHBOARD)) {
                                modelAndView.setViewName("dashboards/dashboardhome");
                            } else if (permissionSet.contains(PermissionConstants.EXECUTIVE_REMEDIATION_DASHBOARD) || permissionSet.contains(PermissionConstants.PERSONAL_REMEDIATION_DASHBOARD)) {
                               
                                if (decodeEngPkgType.equalsIgnoreCase(ReportsAndDashboardConstants.PACKAGE_TYPE_AS_GW) || decodeEngPkgType.equalsIgnoreCase(ReportsAndDashboardConstants.PACKAGE_TYPE_AS_SW)) {
                                    modelAndView.setViewName("redirect:/toRemediationDashBoard.htm");
                                } else {
                                    modelAndView.setViewName("dashboards/nopermission");
                                }
                           
                            } else if (permissionSet.contains(PermissionConstants.EXECUTIVE_REGISTRY_DASHBOARD) || permissionSet.contains(PermissionConstants.PERSONAL_REGISTRY_DASHBOARD)) {
                               
                                if (decodeEngPkgType.equalsIgnoreCase(ReportsAndDashboardConstants.PACKAGE_TYPE_AS_GW) || decodeEngPkgType.equalsIgnoreCase(ReportsAndDashboardConstants.PACKAGE_TYPE_AS_SW)) {
                                    modelAndView.setViewName("redirect:/toRegistryDashBoard.htm");
                                } else {
                                    modelAndView.setViewName("dashboards/nopermission");
                                }
                           
                            } else {
                                modelAndView.setViewName("redirect:/logout.htm");
                            }
                        } else {
                            modelAndView.setViewName("redirect:/logout.htm");
                        }
                        /* Navigation Based on roles and permissions */
                        
                        if (null != req.getParameter("from")) {
                            req.getSession().setAttribute("from", req.getParameter("from"));
                            req.getSession().setAttribute("encEngCodeForBack", engKey);//Back Navigation to Finding List from Dashboard (Ref: US41394)
                        }
//                        else{
//                             req.getSession().removeAttribute("from");
//                        }
                    } else {
                        modelAndView.setViewName("redirect:/logout.htm");

                    }
                
            } else {
                modelAndView.setViewName("redirect:/logout.htm");
            }

        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "dashboardHomeController Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", "failed", e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/switchEngagment")
    public ModelAndView switchEngagment(HttpServletRequest req, HttpServletResponse res,RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        if (null != req.getParameter("engKey")) {
            //logger.info(req.getParameter("engKey"));
            redirectAttributes.addFlashAttribute("engageKey", req.getParameter("engKey"));
            modelAndView.setViewName("redirect:/toengagementhomepage.htm");
        } else {
            modelAndView.setViewName("redirect:/logout.htm");
        }
        return modelAndView;
    }
    
    

    /*
     THIS METHOD IS TO FETCH THE ENGAGMENTS LIST IN HEADER PAGE
     */
    @RequestMapping(value = "/getEngagement", method = RequestMethod.GET)
    public @ResponseBody
    List<EngagementsDTO> getEngagementList(HttpServletRequest req, HttpServletResponse res) {
        logger.info(INFO_USER_GET_ENGAGEMENT);
        List<EngagementsDTO> engList = null;
        UserProfileDTO userDto = new UserProfileDTO();
        try {
            if (req.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) req.getSession().getAttribute(SessionConstants.USER_SESSION);
                  String flag = ApplicationConstants.EMPTY_STRING;
                if (userDto.getSubMenuPermissionMap().containsKey(PermissionConstants.REMEDIATE_PROJECT_MANAGER)) {
                    flag = ApplicationConstants.DB_CONSTANT_DASHBOARD_REMEDIATION;
                } else {
                    flag = ApplicationConstants.DB_CONSTANT_DASHBOARD_NON_REMEDIATION;
                }
                engList = serviceImpl.fetchEngagments(flag,userDto.getOrganizationKey(), userDto.getUserTypeKey(), userDto.getUserProfileKey());// CLIENT_ORG_ID, USER_ID
                //logger.info("list size in getEngagement" + engList.size());
            }
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "getEngagement Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", "failed", e);
        }
        return engList;
    }

    /**
     * THIS METHOD FOR FETCH THE REPORTS OF THE ENGAGMENT
     *
     * @param req
     * @param res
     * @return List<ReportsModel>
     *
     */
    @RequestMapping(value = "/getReports.htm", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<ReportsModel> getReportsForEngagement(HttpServletRequest req, HttpServletResponse res) {
        List<ReportsModel> reportsModelList = new ArrayList<>();
        String engKey = "";
        try {
            engKey = (String) req.getSession().getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_KEY);
            reportsModelList = serviceImpl.fetchEngagementReports(engKey);
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "getEngagement Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", "failed", e);
        }
        return reportsModelList;
    }

    /**
     * THIS METHOD RETURN THE REPORTS PAGE
     *
     * @param req
     * @param res
     * @return modelAndView
     *
     */
    @RequestMapping(value = "/toReportsPage.htm", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView toReportsPage(HttpServletRequest req, HttpServletResponse res) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("dashboards/reports");
        return modelAndView;
    }

    /**
     * THIS METHOD DOWNLOAD THE UPLOADED REPORTS FILE
     *
     * @param request
     * @param response
     * @param reportsdownloadFile
     */
    @RequestMapping(value = "/downloadReportFile")
    public void dashboardreportsFiledownload(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "filename") String reportsdownloadFile) {

        String pathfromdownload = "";
        String encFilePath = "";

        try {
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                UserProfileDTO userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
            }

            String destFolder = System.getProperty("OSS_UPLOAD_FOLDER");
            if (!StringUtils.isEmpty(reportsdownloadFile)) {
                encFilePath = encryptDecrypt.decrypt(reportsdownloadFile);
            }
            pathfromdownload = encFilePath;

            // construct the complete absolute path of the file
            if (pathfromdownload != null) {
                String fullPath = pathfromdownload;

                File downloadFile = new File(fullPath);
                String orginalFileName = downloadFile.getName();
                response.setHeader("Content-Disposition", "attachment;filename= \"" + orginalFileName + "\"");
                FileInputStream fin = new FileInputStream(downloadFile);

                int size = fin.available();
                response.setContentLength(size);
                byte[] ab = new byte[size];
                OutputStream os = response.getOutputStream();
                int bytesread;
                do {
                    bytesread = fin.read(ab, 0, size);
                    if (bytesread > -1) {
                        os.write(ab, 0, bytesread);
                    }
                } while (bytesread > -1);
                fin.close();
                os.flush();
                os.close();
            }
        } catch (Exception ie) {
            logger.debug("Exception Occured while Executing the clientreportsFiledownload Action :: " + ie.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", "", ie);
        }
    }

    @RequestMapping(value = "/getHeaders.htm", method = RequestMethod.GET)
    public @ResponseBody
    List<HeadersActionsModel> getHeaders(HttpServletRequest req, HttpServletResponse res) {
        List<HeadersActionsModel> headers = new ArrayList<HeadersActionsModel>();
         HttpSession session = req.getSession();
        try {
            /*
            headers.add(new HeadersActionsModel("Summary", ReportsAndDashboardConstants.SUMMARY_ACTION_URL));
            headers.add(new HeadersActionsModel("Assessments", ReportsAndDashboardConstants.ASSESSMENT_ACTION_URL));
            if (null != req.getSession().getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_PACKAGE_TYPE)) {
                String decodeEngPkgType = encryptDecrypt.decrypt(
                        req.getSession().getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_PACKAGE_TYPE).toString());
//              decodeEngPkgType="TR";
                if (decodeEngPkgType.equalsIgnoreCase("HC")) {
                    // selected package type is Health Check
                    headers.add(new HeadersActionsModel("Threat Hunting", ReportsAndDashboardConstants.THREAT_HUNTING_ACTION_URL));
                } else if (decodeEngPkgType.equalsIgnoreCase(ReportsAndDashboardConstants.PACKAGE_TYPE_AS_TR)) {
                    //selected package type is Triage
                    headers.add(new HeadersActionsModel("Prioritization Matrix", ReportsAndDashboardConstants.PRIORITIZATION_ACTION_URL));
                    headers.add(new HeadersActionsModel("Remediation", ReportsAndDashboardConstants.REMEDIATION_ACTION_URL));
                    headers.add(new HeadersActionsModel("Road Map", ReportsAndDashboardConstants.ROADMAP_ACTION_URL));
                    
                } else if (decodeEngPkgType.equalsIgnoreCase(ReportsAndDashboardConstants.PACKAGE_TYPE_AS_GW) || decodeEngPkgType.equalsIgnoreCase(ReportsAndDashboardConstants.PACKAGE_TYPE_AS_SW) ) {
                    // selected package type is Get Well and Stay Well
                    headers.add(new HeadersActionsModel("Prioritization Matrix", ReportsAndDashboardConstants.PRIORITIZATION_ACTION_URL));
                    headers.add(new HeadersActionsModel("Road Map", ReportsAndDashboardConstants.ROADMAP_ACTION_URL));
                    headers.add(new HeadersActionsModel("Remediation", ReportsAndDashboardConstants.REMEDIATION_DASHBOARD_ACTION_URL));
                    headers.add(new HeadersActionsModel("Risk Registry", ReportsAndDashboardConstants.RISK_REGISTRY_ACTION_URL));
                }

                headers.add(new HeadersActionsModel("Reports", ReportsAndDashboardConstants.REPORTS_ACTION_URL));
            } */
            
            // dashboard menu construction bases on new roles and permissions
            UserProfileDTO userDto = new UserProfileDTO();
            Set<String> permissionSet = null;
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                if (null != req.getSession().getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_PACKAGE_TYPE)) {
                    String decodeEngPkgType = encryptDecrypt.decrypt(
                            req.getSession().getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_PACKAGE_TYPE).toString());
                    userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                    MDC.put("user", userDto.getEmail());
                    MDC.put("ip", userDto.getUserMACAddress());
                    permissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.REPORTS_MENU);
                    if (permissionSet.contains(PermissionConstants.DASHBOARD)) {
                        headers.add(new HeadersActionsModel("Summary", ReportsAndDashboardConstants.SUMMARY_ACTION_URL));
                        headers.add(new HeadersActionsModel("Assessments", ReportsAndDashboardConstants.ASSESSMENT_ACTION_URL));
                        if (decodeEngPkgType.equalsIgnoreCase(ReportsAndDashboardConstants.PACKAGE_TYPE_AS_HC)) {
                            headers.add(new HeadersActionsModel("Threat Hunting", ReportsAndDashboardConstants.THREAT_HUNTING_ACTION_URL));
                        } else if (decodeEngPkgType.equalsIgnoreCase(ReportsAndDashboardConstants.PACKAGE_TYPE_AS_TR)) {
                            headers.add(new HeadersActionsModel("Prioritization Matrix", ReportsAndDashboardConstants.PRIORITIZATION_ACTION_URL));
                            headers.add(new HeadersActionsModel("Remediation", ReportsAndDashboardConstants.REMEDIATION_ACTION_URL));
                            headers.add(new HeadersActionsModel("Road Map", ReportsAndDashboardConstants.ROADMAP_ACTION_URL));
                        } else if (decodeEngPkgType.equalsIgnoreCase(ReportsAndDashboardConstants.PACKAGE_TYPE_AS_GW) || decodeEngPkgType.equalsIgnoreCase(ReportsAndDashboardConstants.PACKAGE_TYPE_AS_SW)) {
                            headers.add(new HeadersActionsModel("Prioritization Matrix", ReportsAndDashboardConstants.PRIORITIZATION_ACTION_URL));
                            headers.add(new HeadersActionsModel("Road Map", ReportsAndDashboardConstants.ROADMAP_ACTION_URL));
                        }
                        headers.add(new HeadersActionsModel("Reports", ReportsAndDashboardConstants.REPORTS_ACTION_URL));
                    }

                    if (decodeEngPkgType.equalsIgnoreCase(ReportsAndDashboardConstants.PACKAGE_TYPE_AS_GW) || decodeEngPkgType.equalsIgnoreCase(ReportsAndDashboardConstants.PACKAGE_TYPE_AS_SW)) {
                        if (permissionSet.contains(PermissionConstants.EXECUTIVE_REMEDIATION_DASHBOARD) || permissionSet.contains(PermissionConstants.PERSONAL_REMEDIATION_DASHBOARD)) {
                            headers.add(new HeadersActionsModel("Remediation", ReportsAndDashboardConstants.REMEDIATION_DASHBOARD_ACTION_URL));
                        }

                        if (permissionSet.contains(PermissionConstants.EXECUTIVE_REGISTRY_DASHBOARD) || permissionSet.contains(PermissionConstants.PERSONAL_REGISTRY_DASHBOARD)) {
                            headers.add(new HeadersActionsModel("Risk Registry", ReportsAndDashboardConstants.RISK_REGISTRY_ACTION_URL));
                        }
                    }
                }
            }

        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "getEngagement Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", "failed", e);
        }
        return headers;
    }

    /*
     THIS METHOD IS TO FETCH THE ASSESSMENTS (SERVICES) LIST OF THE ENGAGMENT.
     */
    @RequestMapping(value = "/getAssessments.htm", method = RequestMethod.GET)
    public @ResponseBody
    List<AssessmentServicesModel> getAssessments(HttpServletRequest req, HttpServletResponse res) {
        List<AssessmentServicesModel> headers = null;
        try {
            headers = serviceImpl.fetchAssessmentsServicesListByEngagment(
                    req.getSession().getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_KEY).toString());
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "getAssessments Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", "failed", e);
        }
        return headers;
    }

    @RequestMapping(value = "/toEngSummeryPage")
    public ModelAndView toEngSummeryPage(HttpServletRequest req, HttpServletResponse res,RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        redirectAttributes.addFlashAttribute("engageKey",  req.getSession().getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_KEY));
        modelAndView.setViewName("redirect:/toengagementhomepage.htm"
//                + "?engType=" +req.getSession().getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_PACKAGE_TYPE)
//                + "?engKey=" + req.getSession().getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_KEY)
//                + "&engPkgName=" + req.getSession().getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_PACKAGE_NAME)
        );
        return modelAndView;
    }

    @RequestMapping(value = "/switchAssessment")
    public ModelAndView switchAssessment(HttpServletRequest req, HttpServletResponse res,RedirectAttributes redirectAttributes) {
        logger.info(INFO_USER_ACCESSED_TO_ASSESSMENTPAGE);
//        logger.info("in toAssessmentPage and servicecode is " + req.getParameter("srvcd"));
          ModelAndView modelAndView = new ModelAndView();
        if (null != req.getParameter("srvcd")) {
            //logger.info(req.getParameter("engKey"));
            redirectAttributes.addFlashAttribute("srvcd", req.getParameter("srvcd"));
            redirectAttributes.addFlashAttribute("engcd", req.getParameter("engcd"));
            modelAndView.setViewName("redirect:/assessmentPage.htm");
        } else {
            modelAndView.setViewName("redirect:/logout.htm");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/toRoadMapPage")
    public ModelAndView toRoadMapPage(HttpServletRequest req, HttpServletResponse res) {
        return null;
    }

    /**
     * This method for fetch the Prioritization matrix QuadrantValues of the
     * engagement
     *
     * @param req
     * @param res
     * @return modelView
     */
    @RequestMapping(value = "/toPrioritizationPage")
    public ModelAndView toPrioritizationPage(HttpServletRequest req, HttpServletResponse res) {
        ModelAndView modelAndView = new ModelAndView();
        String engKey = "";
       String orgSchema = ApplicationConstants.EMPTY_STRING;

        try {
            if (null != req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                orgSchema = req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }

            engKey = (String) req.getSession().getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_KEY);
            modelAndView.addObject("quadrantData", serviceImpl.fetchPrioritizatioMatrixQuadrantValues(engKey, "", orgSchema));

        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "getEngagement Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", "failed", e);

        }
        modelAndView.setViewName("dashboards/prioritizationMatrix");
        return modelAndView;
    }

    /**
     * This method for fetch the Prioritization matrix findings of the
     * engagement
     *
     * @param req
     * @param res
     * @return List<FindingsModel>
     *
     */
    @RequestMapping(value = "/fetchPrioritizationFindings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<FindingsModel> fetchPrioritizationMatrixGridFindnigs(HttpServletRequest req, HttpServletResponse res) {
        List<FindingsModel> prioritizationMatrixFindingsList = new ArrayList<>();
        List<FindingsModel> findingsModels = new ArrayList<>();
        String engKey = "";
        FindingsModel findingsModel = null;
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if (null != req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                orgSchema = req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }

            engKey = (String) req.getSession().getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_KEY);
            prioritizationMatrixFindingsList = serviceImpl.fetchPrioritizationMatrixGridFindnigs(engKey,orgSchema);

        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "getEngagement Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", "failed", e);
        }
        return prioritizationMatrixFindingsList;

    }

    /**
     * This method for filter each quadrant the Prioritization matrix findings
     * of the engagement
     *
     * @param req
     * @param res
     * @return List<FindingsModel>
     *
     */
    @RequestMapping(value = "/fetchPrioritizationFilterFindings", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<FindingsModel> fetchPrioritizationFilterFindings(HttpServletRequest req, HttpServletResponse res) {
        List<FindingsModel> prioritizationMatrixFindingsList = new ArrayList<>();
        List<FindingsModel> findingsModels = new ArrayList<>();
        String engKey = "";
        FindingsModel findingsModel = null;
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if (null != req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                orgSchema = req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
            engKey = (String) req.getSession().getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_KEY);
            String finding = req.getParameter("findingIds");
            prioritizationMatrixFindingsList = serviceImpl.fetchPrioritizationFilterFindings(engKey, finding,orgSchema);

        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "getEngagement Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", "failed", e);
        }
        return prioritizationMatrixFindingsList;

    }

    /**
     * THIS METHOD RETURN THE REMEDIATION PAGE
     *
     * @param req
     * @param res
     * @return modelAndView
     *
     */
    @RequestMapping(value = "/toRemediationPage")
    public ModelAndView toRemediationPage(HttpServletRequest req, HttpServletResponse res) {
        logger.info(INFO_USER_ACCESSED_TO_REMIDIATIONPAGE);

        ModelAndView modelAndView = new ModelAndView();
        String engKey = (String) req.getSession().getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_KEY);
        modelAndView.addObject("engagementKey", engKey);
        modelAndView.setViewName("dashboards/remediation");

        logger.info("End: in toRemediationPage method");
        return modelAndView;
    }

    @RequestMapping(value = "/fetchSummeryPageFindingsCharts", method = RequestMethod.GET)
    public @ResponseBody
    List<String> fetchSummeryPageFindingsCharts(HttpServletRequest req, HttpServletResponse res) {
        List<String> chartsList = new ArrayList<String>();
        chartsList.add("<chart caption='' numDivLines='0' showTooltip='1' bgAlpha='0' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5'  doughnutRadius='45' centerLabelPadding='-10'  labelFontSize='12' valueFontSize='12' enableSmartLabels='1'  baseFont='arial'  smartLabelClearance='-15' centerLabelFontSize='11'  defaultcenterlabel='Red Team' centerlabel='Red Team' centerlabelbold='1'  showYAxisValues='0' showPlotBorder='0' yAxisMaxValue='170' plotSpacePercent='50' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='0' showValues='1' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix=''  rightmargin='0' bgcolor='ECF5FF' bgalpha='70' bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff' canvasBorderThickness='1' canvasBorderColor='d7dcd2' showpercentvalues='0' bgratio='0' startingangle='90' animation='1'> <set value='150' label='Low' color='627D32'  /> <set value='10' label='Medium' color='F2B411'  /> <set value='20' label='High' color='E87722' /> <set value='40' label='Critical' color='A32A2E'/>   </chart>");
        chartsList.add("<chart caption='' numDivLines='0' showTooltip='1' bgAlpha='0' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5'  doughnutRadius='45' centerLabelPadding='-10'  labelFontSize='12' valueFontSize='12' enableSmartLabels='1'  baseFont='arial'  smartLabelClearance='-15' centerLabelFontSize='11'  defaultcenterlabel='Red Team' centerlabel='Red Team' centerlabelbold='1'  showYAxisValues='0' showPlotBorder='0' yAxisMaxValue='170' plotSpacePercent='50' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='0' showValues='1' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix=''  rightmargin='0' bgcolor='ECF5FF' bgalpha='70' bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff' canvasBorderThickness='1' canvasBorderColor='d7dcd2' showpercentvalues='0' bgratio='0' startingangle='90' animation='1'> <set value='150' label='Low' color='627D32'  /> <set value='10' label='Medium' color='F2B411'  /> <set value='20' label='High' color='E87722' /> <set value='40' label='Critical' color='A32A2E'/>   </chart>");
        chartsList.add("<chart caption='' numDivLines='0' showTooltip='1' bgAlpha='0' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5'  doughnutRadius='45' centerLabelPadding='-10'  labelFontSize='12' valueFontSize='12' enableSmartLabels='1'  baseFont='arial'  smartLabelClearance='-15' centerLabelFontSize='11'  defaultcenterlabel='Red Team' centerlabel='Red Team' centerlabelbold='1'  showYAxisValues='0' showPlotBorder='0' yAxisMaxValue='170' plotSpacePercent='50' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='0' showValues='1' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix=''  rightmargin='0' bgcolor='ECF5FF' bgalpha='70' bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff' canvasBorderThickness='1' canvasBorderColor='d7dcd2' showpercentvalues='0' bgratio='0' startingangle='90' animation='1'> <set value='150' label='Low' color='627D32'  /> <set value='10' label='Medium' color='F2B411'  /> <set value='20' label='High' color='E87722' /> <set value='40' label='Critical' color='A32A2E'/>   </chart>");
        chartsList.add("<chart caption='' numDivLines='0' showTooltip='1' bgAlpha='0' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5'  doughnutRadius='45' centerLabelPadding='-10'  labelFontSize='12' valueFontSize='12' enableSmartLabels='1'  baseFont='arial'  smartLabelClearance='-15' centerLabelFontSize='11'  defaultcenterlabel='Red Team' centerlabel='Red Team' centerlabelbold='1'  showYAxisValues='0' showPlotBorder='0' yAxisMaxValue='170' plotSpacePercent='50' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='0' showValues='1' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix=''  rightmargin='0' bgcolor='ECF5FF' bgalpha='70' bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff' canvasBorderThickness='1' canvasBorderColor='d7dcd2' showpercentvalues='0' bgratio='0' startingangle='90' animation='1'> <set value='150' label='Low' color='627D32'  /> <set value='10' label='Medium' color='F2B411'  /> <set value='20' label='High' color='E87722' /> <set value='40' label='Critical' color='A32A2E'/>   </chart>");
        return chartsList;

    }

    /**
     * This method to fetch service based finding charts for remediation
     *
     * @param req
     * @param res
     * @param selectedService
     * @return modelAndView
     */
    @RequestMapping(value = "/fetchremediationcharts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<RemediationChartModel> fetchRemediationCharts(HttpServletRequest req, HttpServletResponse res, @RequestBody List<SummaryDropDownModel> selectedService) {
        logger.info(INFO_USER_ACCESSED_FETCH_REMIDIATION_CHARTS);
        List<RemediationChartModel> remediationChartsList = null;
        List<RemediationChartModel> finalChartsList = new ArrayList<>();
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        if (null != req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
            orgSchema = req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
        }

        String engKey = (String) req.getSession().getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_KEY);
        try {
            //String serviceCode = req.getParameter("sc");
            remediationChartsList = serviceImpl.fetchTopRemediationCharts(encryptDecrypt.decrypt(engKey), selectedService,orgSchema);
            for (RemediationChartModel chartModel : remediationChartsList) {
                RemediationChartModel sModel = new RemediationChartModel();
                String data = "<chart caption='" + chartModel.getServiceName()
                        + "' captionAlignment='center' captionFontColor='333333'  baseFont='arial' basefontsize='10'  labelFontSize='12' "
                        + "valueFontSize='12' labelPadding='13' numDivLines='0' maxColWidth='15' bgcolor='FFFFFF' plotgradientcolor='' "
                        + "showalternatehgridcolor='0' baseFontSize='11' showplotborder='0' divlinecolor='CCCCCC' showvalues='1' showcanvasborder='1' "
                        + "borderThickness='0'  canvasBorderThickness='0' canvasBorderColor='ffffff' pyaxisname='' syaxisname='' numberprefix='' "
                        + "labeldisplay='' slantlabels='0' canvasborderalpha='0' legendshadow='0' legendborderalpha='0' showborder='0' borderAlpha='0' "
                        + "showVLineLabelBorder='0' showPlotBorder='0' plotBorderAlpha='0' showYAxisValues='0' showAlternateVGridColor='0'>\\n\\\n"
                        + "<categories>\\n\\\n"
                        + "<category label='Critical' />\\n\\\n"
                        + "<category label='High' />\\n\\\n"
                        + "<category label='Medium' />\\n\\\n"
                        + "<category label='Low' />\\n\\\n"
                        + "</categories>\\n\\\n"
                        + "<dataset seriesname='' >\\n\\\n"
                        + "<set value='" + chartModel.getCriticalCount() + "'  color='A32A2E'  label='Critical'/>\\n\\\n"
                        + "<set value='" + chartModel.getHighCount() + "' color='E87722'  label='High'/>\\n\\\n"
                        + "<set value='" + chartModel.getMediumCount() + "' color='F2B411'  label='Medium'/>\\n\\\n"
                        + "<set value='" + chartModel.getLowCount() + "' color='627D32'  label='Low' />\\n\\\n"
                        + "</dataset>\\n\\\n"
                        + "<dataset seriesname='Open' parentyaxis='S' renderas='Line' color='f8bd19'>\\n\\\n"
                        + "<set value='" + chartModel.getCriticalOpenCount() + "' />\\n\\\n"
                        + "<set value='" + chartModel.getHighOpenCount() + "' />\\n\\\n"
                        + "<set value='" + chartModel.getMediumOpenCount() + "' />\\n\\\n"
                        + "<set value='" + chartModel.getLowOpenCount() + "' />\\n\\\n"
                        + "</dataset>\\n\\\n"
                        + "</chart>";
                String output = chartModel.getServiceName().replaceAll("\\s+", "");
                sModel.setServiceName(output);
                sModel.setRemediationChartText(data);
                finalChartsList.add(sModel);
            }

        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "fetchRemediationCharts Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", "failed", e);
        }
        logger.info("End: in fetchRemediationCharts method");
        return finalChartsList;
    }

    /**
     * This method to fetch vulnerabilities based on engagement code for
     * remediaiton
     *
     * @param req
     * @param res
     * @param selectedService
     * @return modelAndView
     */
    @RequestMapping(value = "/fetchremediationvulnerabilites", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<RemidiationDataModel> fetchRemediationVulnerabilites(HttpServletRequest req, HttpServletResponse res,
            @RequestBody List<SummaryDropDownModel> selectedService) {
        logger.info(INFO_USER_FETCH_REMIDIATION_VULNERABILITIES);
        List<RemidiationDataModel> remediationChartsList = null;
        String engKey = (String) req.getSession().getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_KEY);
        
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        if (null != req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
            orgSchema = req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
        }
        
        try {
            //String serviceCode = req.getParameter("sc");
            remediationChartsList = serviceImpl.fetchRemediationVulnerabilities(encryptDecrypt.decrypt(engKey),
                    selectedService,orgSchema);
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "fetchRemediationCharts Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", "failed", e);
        }
        logger.info("End: in fetchRemediationVulnerabilites method");
        return remediationChartsList;
    }

    /**
     * This method for prioritizationFindingscsvExport
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/prioritizationFindingscsvExport")
    public void prioritizationFindingscsvExport(HttpServletRequest request, HttpServletResponse response,@RequestBody List<SummaryDropDownModel> summaryDropDownModel) {
        SecurityModel securityModel = new SecurityModel();
        try {
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                UserProfileDTO userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
            }
             String orgSchema = ApplicationConstants.EMPTY_STRING;
        if (null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
            orgSchema = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
        }
            
            String engKey = (String) request.getSession().getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_KEY);
            String quadrantvalues = request.getParameter("quadrantvalues");

            securityModel.getEngagementsDTO().setEngagementKey(engKey);
            securityModel.setFindingId(CommonUtil.replaceNullWithEmpty(quadrantvalues));
            List dbHeaders = new ArrayList();
            List csvHeaders = new ArrayList();
              for (SummaryDropDownModel summaryModel1 : summaryDropDownModel) {
                String maker = summaryModel1.getMaker();
                if (maker.equalsIgnoreCase("severity")) {

                } else if (maker.equalsIgnoreCase("category")) {

                } else if (maker.equalsIgnoreCase("service")) {

                } else {
                    dbHeaders.add(summaryModel1.getMaker());
                    csvHeaders.add(summaryModel1.getLabel());
                }

            }
                securityModel.setCsvDBHeaders(dbHeaders);
            securityModel.setCsvHeaders(csvHeaders);
            securityModel.setEngSchemaName(orgSchema);
            List listOfFindings  = serviceImpl.prioritizationFindingscsvExport(securityModel);
            EngagementsDTO engagementsDTO = serviceImpl.getEngagementDetailsForExportCSV(securityModel);
            csvFileCreation.prepareCSVFile(listOfFindings, engagementsDTO);

        } catch (Exception ie) {
            logger.debug("Exception Occured while Executing the engagementDataFiledownload Action :: " + ie.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), ie);
        }
    }
}
