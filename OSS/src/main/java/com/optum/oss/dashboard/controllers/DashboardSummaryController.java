/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dashboard.controllers;

import com.optum.oss.constants.ReportsAndDashboardConstants;
import com.optum.oss.dto.EngagementsDTO;
import com.optum.oss.dto.ExceptionDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.CSVFileCreationHelper;
import com.optum.oss.model.FindingsModel;
import com.optum.oss.model.HitrustInfoModel;
import com.optum.oss.model.SecurityModel;
import com.optum.oss.model.SummaryDropDownModel;
import com.optum.oss.service.impl.ReportsAndDashboardServiceImpl;
import com.optum.oss.util.EncryptDecrypt;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.owasp.esapi.filters.SecurityWrapperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author spatepuram
 */
@Controller
public class DashboardSummaryController {

    @Resource(name = "myProperties")
    private Properties myProperties;

    private final String INFO_USER_ACCESSED_DASHBOARD_SERVICES = "User accessed dashboard page";
    private final String INFO_USER_ACCESSED_ENGAGEMENT_DASHBOARD = "User accessed accessed engagment dashboard page";

    @Autowired
    private ReportsAndDashboardServiceImpl serviceImpl;

    @Autowired
    private EncryptDecrypt encryptDecrypt;

    @Autowired
    private CSVFileCreationHelper csvFileCreation;

    private static final String uploadFilePath = System.getProperty("OSS_UPLOAD_FOLDER");

    private static final Logger logger = Logger.getLogger(DashboardSummaryController.class);

    @RequestMapping(value = "/getSummaryServices", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SecurityModel getSummaryServices(HttpServletRequest req, HttpServletResponse res) {
        SecurityModel securityModel = new SecurityModel();
        try {
            String engkey = req.getParameter("engkey");
            securityModel.getEngagementsDTO().setEngagementKey(engkey);
            if(null != req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)){
                securityModel.setEngSchemaName(req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString());
            }
            securityModel = serviceImpl.fetchSummaryDropDownValues(securityModel);
            req.getSession().setAttribute("VulnCat", securityModel.getCatagoryListSummary());
        } catch (AppException ex) {
            java.util.logging.Logger.getLogger(DashboardSummaryController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return securityModel;
    }

    @RequestMapping(value = "/getCharts.htm", method = RequestMethod.POST)
    public @ResponseBody
    SecurityModel getCharts(HttpServletRequest req, HttpServletResponse res) {
        String reqParam = req.getParameter("chart");
        SecurityModel securityModel = new SecurityModel();
        try {
            String engkey = req.getParameter("engkey");
            securityModel.getEngagementsDTO().setEngagementKey(engkey);
             if(null != req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)){
                securityModel.setEngSchemaName(req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString());
            }
            securityModel = serviceImpl.getSummaryBubbleChartData(securityModel);
        } catch (AppException ex) {
            java.util.logging.Logger.getLogger(DashboardSummaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return securityModel;

    }

    @RequestMapping(value = "/getFindingsChart.htm", method = RequestMethod.GET)
    public @ResponseBody
    List<SecurityModel> getFindingsChart(HttpServletRequest req, HttpServletResponse res) throws AppException {
        SecurityModel secModel = new SecurityModel();
        String engkey = encryptDecrypt.decrypt(req.getParameter("engKey"));
        secModel.getEngagementsDTO().setEngagementKey(engkey);
         if(null != req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)){
                secModel.setEngSchemaName(req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString());
            }
        List<SecurityModel> securityModel = serviceImpl.getFindingsDoughnutData(secModel);
        return securityModel;

    }

    @RequestMapping(value = "/getHitrustInfoByCtrlCD", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    public @ResponseBody
    List<HitrustInfoModel> getHitrustInfoByCtrlCD(HttpServletRequest req, HttpServletResponse res) {

        String hData = req.getParameter("hData");
        List<HitrustInfoModel> objectList = null;
        try {
            objectList = serviceImpl.getHitrustInfoByCtrlCD(hData);
        } catch (AppException ex) {
            java.util.logging.Logger.getLogger(DashboardSummaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return objectList;

    }

    @RequestMapping(value = "/getsummaryTableData.htm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<FindingsModel> getsummaryTableData(HttpServletRequest req, HttpServletResponse res, @RequestBody List<SummaryDropDownModel> summaryDropDownModel) {
        List<FindingsModel> findingsModels = new ArrayList<>();
        SecurityModel securityModel = new SecurityModel();
        try {
            List<String> severityTextList = new ArrayList<>();
            List<String> categoryTextList = new ArrayList<>();
            List<String> serviceTextList = new ArrayList<>();

            for (SummaryDropDownModel sModel : summaryDropDownModel) {
                if (sModel.getMaker().equalsIgnoreCase("severity")) {
                    severityTextList.add(sModel.getId() + "");

                } else if (sModel.getMaker().equalsIgnoreCase("category")) {
                    categoryTextList.add(sModel.getId() + "");

                } else if (sModel.getMaker().equalsIgnoreCase("service")) {
                    serviceTextList.add(sModel.getId() + "");

                }
            }
            securityModel.setSeverityText(StringUtils.join(severityTextList, ","));
            securityModel.setCategoryText(StringUtils.join(categoryTextList, ","));
            securityModel.setServiceText(StringUtils.join(serviceTextList, ","));

            String engkey = req.getParameter("engkey");
            securityModel.getEngagementsDTO().setEngagementKey(engkey);
            String fndId = req.getParameter("fndId");
            securityModel.setFindingId(fndId);
            String sNo = req.getParameter("sNo");
            securityModel.setFindingSno(sNo);
             if(null != req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)){
                securityModel.setEngSchemaName(req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString());
            }
            findingsModels = serviceImpl.getEngagementFindingsData(securityModel);
        } catch (AppException ex) {
            java.util.logging.Logger.getLogger(DashboardSummaryController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return findingsModels;
    }

    @RequestMapping(value = "/getFilterCharts.htm", method = RequestMethod.POST)
    public @ResponseBody
    SecurityModel getFilterCharts(HttpServletRequest req, HttpServletResponse res, @RequestBody List<SummaryDropDownModel> summaryDropDownModel) {
//        String reqParam = req.getParameter("chart");
        SecurityModel securityModel = new SecurityModel();
        try {
            List<String> severityTextList = new ArrayList<>();
            List<String> categoryTextList = new ArrayList<>();
            List<String> serviceTextList = new ArrayList<>();

            for (SummaryDropDownModel sModel : summaryDropDownModel) {
                if (sModel.getMaker().equalsIgnoreCase("severity")) {
                    severityTextList.add(sModel.getId() + "");

                } else if (sModel.getMaker().equalsIgnoreCase("category")) {
                    categoryTextList.add(sModel.getId() + "");

                } else if (sModel.getMaker().equalsIgnoreCase("service")) {
                    serviceTextList.add(sModel.getId() + "");

                }
            }
            securityModel.setSeverityText(StringUtils.join(severityTextList, ","));
            securityModel.setCategoryText(StringUtils.join(categoryTextList, ","));
            securityModel.setServiceText(StringUtils.join(serviceTextList, ","));

            String engkey = req.getParameter("engkey");
            securityModel.getEngagementsDTO().setEngagementKey(engkey);
              if(null != req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)){
                securityModel.setEngSchemaName(req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString());
            }
            securityModel = serviceImpl.getSummaryBubbleChartData(securityModel);
        } catch (AppException ex) {
            java.util.logging.Logger.getLogger(DashboardSummaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return securityModel;

    }

//    @RequestMapping(value = "/csvExport")
//    public void engagementDataFiledownload(HttpServletRequest request, HttpServletResponse response) {
//        SecurityModel securityModel = new SecurityModel();
//        try {
//            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
//                UserProfileDTO userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
//                MDC.put("user", userDto.getEmail());
//                MDC.put("ip", userDto.getUserMACAddress());
//            }
//
//            // FETCH LIST OF FINDINGS
//            String engkey = request.getParameter("engkey");
//            String param = request.getParameter("param");
// 
//            List<String> severityTextList = new ArrayList<>();
//            List<String> categoryTextList = new ArrayList<>();
//            List<String> serviceTextList = new ArrayList<>();
//
//            JSONArray jsonarray = new JSONArray(param);
//            for (int i = 0; i < jsonarray.length(); i++) {
//                JSONObject jsonobject = jsonarray.getJSONObject(i);
//                String maker = jsonobject.getString("maker");
//                if (maker.equalsIgnoreCase("severity")) {
//                    severityTextList.add(jsonobject.getString("id"));
//
//                } else if (maker.equalsIgnoreCase("category")) {
//                    categoryTextList.add(jsonobject.getString("id"));
//
//                } else if (maker.equalsIgnoreCase("service")) {
//                    serviceTextList.add(jsonobject.getString("id"));
//
//                }
//            }
//            securityModel.setSeverityText(StringUtils.join(severityTextList, ","));
//            securityModel.setCategoryText(StringUtils.join(categoryTextList, ","));
//            securityModel.setServiceText(StringUtils.join(serviceTextList, ","));
//            
//            securityModel.getEngagementsDTO().setEngagementKey(engkey);
//            List<FindingsModel> listOfFindings = serviceImpl.getEngagementFindingsDataForExportCSV(securityModel);
//              EngagementsDTO engagementsDTO= serviceImpl.getEngagementDetailsForExportCSV(securityModel);
//
//            File downloadFile = csvFileCreation.prepareCSVFile(listOfFindings,engagementsDTO);
//
//            response.setContentType("text/csv");
//            response.setHeader("Content-Disposition", "attachment; filename=\"FindingDetails_.csv\"");
//
//            FileInputStream fin = new FileInputStream(downloadFile);
//
//            int size = fin.available();
//            response.setContentLength(size);
//            byte[] ab = new byte[size];
//            OutputStream os = response.getOutputStream();
//            int bytesread;
//            do {
//                bytesread = fin.read(ab, 0, size);
//                if (bytesread > -1) {
//                    os.write(ab, 0, bytesread);
//                }
//            } while (bytesread > -1);
//
//            fin.close();
//            os.flush();
//            os.close();
//        } catch (Exception ie) {
//            logger.debug("Exception Occured while Executing the engagementDataFiledownload Action :: " + ie.getMessage());
//            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), ie);
//            //return new ModelAndView("/exception", "exceptionBean", exception);
//        }
//
//        //return new ModelAndView("/engagementdataupload/engagementdataupload");
//    }
    @RequestMapping(value = "/getSummaryTopRootCauseChart.htm", method = RequestMethod.POST)
    public @ResponseBody
    SecurityModel getSummaryTopRootCauseChart(HttpServletRequest req, HttpServletResponse res) {
        SecurityModel securityModel = new SecurityModel();
        try {
            String engkey = req.getParameter("engkey");
            securityModel.getEngagementsDTO().setEngagementKey(engkey);
              if(null != req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)){
                securityModel.setEngSchemaName(req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString());
            }
            securityModel = serviceImpl.getSummaryTopRootCauseChart(securityModel);
        } catch (AppException ex) {
            logger.debug("Exception Occured while Executing the getSummaryTopRootCauseChart Action :: " + ex.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), ex);
        }
        return securityModel;

    }

    @RequestMapping(value = "/getExportCSVCheckBoxes", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SecurityModel getExportCSVCheckBoxes(HttpServletRequest req, HttpServletResponse res) {
        SecurityModel securityModel = null;
        try {
            securityModel = serviceImpl.getExportCSVCheckBoxes();

        } catch (AppException ex) {
            logger.debug("Exception Occured while Executing the getExportCSVCheckBoxes Action :: " + ex.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), ex);
        }
        return securityModel;
    }

    @RequestMapping(value = "/exportCSVForCheckedColumns.htm")
    public void exportCSVForCheckedColumns(HttpServletRequest req, HttpServletResponse res, @RequestBody List<SummaryDropDownModel> summaryDropDownModel) {
        SecurityModel securityModel = new SecurityModel();
        try {
            List dbHeaders = new ArrayList();
            List csvHeaders = new ArrayList();

            String engkey = req.getParameter("engkey");
            String fndId = req.getParameter("fndId");
//            String param = req.getParameter("param");
//            String csvParam = req.getParameter("cp");

            List<String> severityTextList = new ArrayList<>();
            List<String> categoryTextList = new ArrayList<>();
            List<String> serviceTextList = new ArrayList<>();

            for (SummaryDropDownModel summaryModel1 : summaryDropDownModel) {
                String maker = summaryModel1.getMaker();
                if (maker.equalsIgnoreCase("severity")) {
                    severityTextList.add(summaryModel1.getId());

                } else if (maker.equalsIgnoreCase("category")) {
                    categoryTextList.add(summaryModel1.getId());

                } else if (maker.equalsIgnoreCase("service")) {
                    serviceTextList.add(summaryModel1.getId());

                } else {
                    dbHeaders.add(summaryModel1.getMaker());
                    csvHeaders.add(summaryModel1.getLabel());
                }

            }

            securityModel.setSeverityText(StringUtils.join(severityTextList, ","));
            securityModel.setCategoryText(StringUtils.join(categoryTextList, ","));
            securityModel.setServiceText(StringUtils.join(serviceTextList, ","));

            securityModel.setCsvDBHeaders(dbHeaders);
            securityModel.setCsvHeaders(csvHeaders);
            securityModel.getEngagementsDTO().setEngagementKey(engkey);
            securityModel.setFindingId(fndId);
             if(null != req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)){
                securityModel.setEngSchemaName(req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString());
            }
            List listOfFindings = serviceImpl.getEngagementFindingsDataForExportCSV(securityModel);
            EngagementsDTO engagementsDTO = serviceImpl.getEngagementDetailsForExportCSV(securityModel);

            csvFileCreation.prepareCSVFile(listOfFindings, engagementsDTO);

        } catch (Exception ie) {
            logger.debug("Exception Occured while Executing the engagementDataFiledownload Action :: " + ie.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), ie);
            //return new ModelAndView("/exception", "exceptionBean", exception);
        }

        //return new ModelAndView("/engagementdataupload/engagementdataupload");
    }

    @RequestMapping(value = "/downloadCSVFile")
    public void downloadCSVFile(HttpServletRequest request, HttpServletResponse res,
            @RequestParam("rName") String reportName) {
        try {
//            String reportName = "";
//            if (request.getParameter("rName") != null) {
//                reportName = request.getParameter("rName");
//            }
            
            /*
            SecurityWrapperResponse securityWrapperResponse = new 
                    SecurityWrapperResponse(res, "sanitize");
            */
            
            if (this.validateReportName(reportName)) {
                String decReportName="";
               if(!StringUtils.isEmpty(reportName)){
                   decReportName = encryptDecrypt.decrypt(reportName);
                }
                  
                Calendar currentDate = Calendar.getInstance(); //Get the current date
                DateFormat dateFormatH = new SimpleDateFormat("MMddyyyyHHmm");
                String dateNow = dateFormatH.format(currentDate.getTime());
                logger.info("Now the date is :=>  " + dateNow);
                File downloadFile = new File(uploadFilePath + File.separator + "findingsCSV.csv");
                String downloadFileName  = decReportName + "FindingDetails-" + dateNow + ".csv";

                String contDisposition = "attachment; filename=\"" + downloadFileName + "\"" ;
                
                res.setContentType("text/csv");
                res.addHeader("Content-Transfer-Encoding", "Binary");
                res.addHeader("Cache-Control", "must-revalidate, private");  
                res.setHeader("Content-Disposition", contDisposition);

                //securityWrapperResponse.setContentType("text/csv");
                /*
                securityWrapperResponse.setContentType("application/x-download");
                securityWrapperResponse.addHeader("Content-Transfer-Encoding", "Binary");
                securityWrapperResponse.addHeader("Cache-Control", "must-revalidate, private");  
                securityWrapperResponse.setHeader("Content-Disposition", contDisposition);
                */
                
                InputStream fin = new FileInputStream(downloadFile);
                int size = fin.available();
                res.setContentLength(size);
                byte[] ab = new byte[size];
                OutputStream os = res.getOutputStream();
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
            
//            FileCopyUtils.copy(fin, res.getOutputStream());
//            res.flushBuffer();

        } catch (Exception e) {
            logger.debug("Exception Occured while Executing the engagementDataFiledownload Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
        }
    }
    
    private boolean validateReportName(final String reportName) throws AppException
    {
        boolean ret = false;
        
//        String a = encryptDecrypt.encrypt(ReportsAndDashboardConstants.DOWNLOAD_ASSESSMENT);
//        logger.info("Assessment  ==" + a);
//
//        String s = encryptDecrypt.encrypt(ReportsAndDashboardConstants.DOWNLOAD_SUMMARY);
//        logger.info("Summary  ==" + s);
//
//        String p = encryptDecrypt.encrypt(ReportsAndDashboardConstants.DOWNLOAD_PRIORITIZATIONMATRIX);
//        logger.info("PRIORITIZATIONMATRIX  ==" + p);
//
//        String r = encryptDecrypt.encrypt(ReportsAndDashboardConstants.DOWNLOAD_REMEDIATION);
//        logger.info("REMEDIATION  ==" + r);
//        String r = encryptDecrypt.encrypt(ReportsAndDashboardConstants.DOWNLOAD_RISKREGISTRY);
//        logger.info("DOWNLOAD_RISKREGISTRY  ==" + r);
        
        if(StringUtils.isEmpty(reportName))
        {
            ret = false;
        }
        else
        {
            String decReportName = encryptDecrypt.decrypt(reportName);
            if(decReportName.equalsIgnoreCase(ReportsAndDashboardConstants.DOWNLOAD_ASSESSMENT))
            {
                return true;
            }
            else if(decReportName.equalsIgnoreCase(ReportsAndDashboardConstants.DOWNLOAD_REMEDIATION))
            {
                return true;
            }
            else if(decReportName.equalsIgnoreCase(ReportsAndDashboardConstants.DOWNLOAD_SUMMARY))
            {
                return true;
            }
            else if(decReportName.equalsIgnoreCase(ReportsAndDashboardConstants.DOWNLOAD_PRIORITIZATIONMATRIX))
            {
                return true;
            } else if (decReportName.equalsIgnoreCase(ReportsAndDashboardConstants.DOWNLOAD_ROADMAP)) {
                return true;
            }else if (decReportName.equalsIgnoreCase(ReportsAndDashboardConstants.DOWNLOAD_RISKREGISTRY)) {
                return true;
            }
        }
        
        return ret;
    }
}
