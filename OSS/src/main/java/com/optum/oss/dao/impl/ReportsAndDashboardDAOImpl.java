/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao.impl;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.constants.ReportsAndDashboardConstants;
import com.optum.oss.dao.ReportsAndDashboardDAO;
import com.optum.oss.dto.AssessmentFindingsCountDTO;
import com.optum.oss.dto.EngagementsDTO;
import com.optum.oss.dto.PrioritizationMatrixDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.model.AssessmentModel;
import com.optum.oss.model.AssessmentServicesModel;
import com.optum.oss.model.ChartModel;
import com.optum.oss.model.FindingsModel;
import com.optum.oss.model.HitrustInfoModel;
import com.optum.oss.model.RemediationChartModel;
import com.optum.oss.model.RemidiationDataModel;
import com.optum.oss.model.ReportsModel;
import com.optum.oss.model.SecurityModel;
import com.optum.oss.model.SummaryDropDownModel;
import com.optum.oss.util.CommonUtil;
import com.optum.oss.util.DateUtil;
import com.optum.oss.util.EncryptDecrypt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author mrejeti
 */
public class ReportsAndDashboardDAOImpl implements ReportsAndDashboardDAO {
    
    @Autowired
    private EncryptDecrypt encryptDecrypt;
    
    @Autowired
    private DateUtil dateUtil;
    
    private static final Logger logger = Logger.getLogger(ReportsAndDashboardDAOImpl.class);
    
    private JdbcTemplate jdbcTemplate;
    /*
     Start : Setter getters for private variables
     */
    /*
     START : LOG MESSAGES
     */
    private final String INFO_USER_ACCESSED_SERVICE_LIST_BY_ENGAGEMENT = "User accessed service list by engagement";
    private final String INFO_USER_ACCESSED_FETCH_OBJECTIVELIST_FOR_SUMMARY = "User accessed fetch objectivelist for summary";
    private final String INFO_USER_ACCESSED_FETCH_LIST_FOR_SUMMARY = "User accessed fetch list for summary";
    private final String INFO_USER_ACCESSED_FINDINGS_DOUGHNUTDATA = "User accessed findings downhutdata";
    private final String INFO_USER_ACCESSED_SUMMARY_BUBLECHART_DATA = "User accessed summary bublechart data";
    private final String INFO_USER_ACCESSED_SUMMARY_ENGAGEMENTFINDINGS_DATA = "User accessed summary engagement findingsdata";
    private final String INFO_USER_ACCESSED_HITRUSTINFO_BY_CTRLCD = "User accessed hitrustinfo by ctrlcd page";
    private final String INFO_USER_ACCESSED_FETCH_TOP_REMIDIATIONCHARTS = "User accessed fetch remidiation charts page";
    private final String INFO_USER_ACCESSED_FETCH_REMIDIATION_VULNERABILITIES = "User accessed fetch remidiation vulnerabilities page";
    private final String INFO_USER_ACCESSED_ENGAGEMENT_FINDINGS_DATA_FOR_EXPORTCSV = "User accessed engagement findings data for exportcsv page";
    private final String INFO_USER_ACCESSED_ENGAGEMENT_DETAILS_FOR_EXPORTCSV = "User accessed engagement details for exportcsv page";
    private final String INFO_USER_ACCESSED_ENGAGEMENT_DETAILS_FOR_TOP_ROOTCAUSE = "User accessed engagement details for top root cause in summary";
    private final String INFO_USER_ACCESSED_FETCH_ROOTCAUSELIST_FOR_SUMMARY = "User accessed fetch root cause list for summary";
    /*
     END: LOG MESSAGES
     */
    
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    /*
     End : Setter getters for private variables
     */
    
    @Override
    public List<EngagementsDTO> fetchEngagments(final String flag, final long clintOrgId, final long userTypeId, final long userProfileKey) throws AppException {
        
        List<EngagementsDTO> engList = null;
        String procName = "{CALL Report_ListEngmts(?,?,?,?)}";
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{flag,clintOrgId, userTypeId, userProfileKey});
        if (null != resultList && resultList.size() > 0) {
            engList = new ArrayList<>(resultList.size());
            for (Map<String, Object> resultMap : resultList) {
                engList.add(new EngagementsDTO(encryptDecrypt.encrypt(resultMap.get("CLNT_ENGMT_CD").toString()),
                        resultMap.get("CLNT_ENGMT_NM").toString(),
                        encryptDecrypt.encrypt(resultMap.get("SECUR_PKG_CD").toString()),
                        resultMap.get("SECUR_PKG_NM").toString()));
            }
        }
        return engList;
    }
    
    @Override
    public List<AssessmentServicesModel> fetchAssessmentsServicesListByEngagment(String engCode) throws AppException {
        List<AssessmentServicesModel> assessmentList = null;
        String procName = "{CALL Analyst_ListServicesByEngmt(?)}";
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{encryptDecrypt.decrypt(engCode)});
        if (null != resultList && resultList.size() > 0) {
            assessmentList = new ArrayList<>(resultList.size());
            for (Map<String, Object> resultMap : resultList) {
                if (null != resultMap.get("SECUR_SRVC_CD")
                        && !resultMap.get("SECUR_SRVC_CD").toString().equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_THREATHUNTING)) {
                    
                    String assmentCode = resultMap.get("SECUR_SRVC_CD").toString();
                    String serviceName = "";
                    if (assmentCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_APPLICATION_VULNERABILITY_ASSESSMENT)) {
                        serviceName = ReportsAndDashboardConstants.SERVICE_APPLICATION_VULNERABILITY_ASSESSMENT;
                    } else if (assmentCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_NETWORK_VULNERABILITY_ASSESSMENT)) {
                        serviceName = ReportsAndDashboardConstants.SERVICE_NETWORK_VULNERABILITY_ASSESSMENT;
                    } else if (assmentCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_PENETRATION_TESTING)) {
                        serviceName = ReportsAndDashboardConstants.SERVICE_PENETRATION_TESTING;
                    } else if (assmentCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_LIMITED_REDTEAM_ASSESSMENT)) {
                        serviceName = ReportsAndDashboardConstants.SERVICE_LIMITED_REDTEAM_ASSESSMENT;
                    } else if (assmentCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_SECURITY_RISK_ASSESSMENT_CODE)) {
                        serviceName = ReportsAndDashboardConstants.SERVICE_SECURITY_RISK_ASSESSMENT_CODE;
                    } else if (assmentCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_WIRELESS_RISK_ASSESSMENT)) {
                        serviceName = ReportsAndDashboardConstants.SERVICE_WIRELESS_RISK_ASSESSMENT;
                    } else if (assmentCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_THREATHUNTING)) {
                        serviceName = ReportsAndDashboardConstants.SERVICE_NAME_THREATHUNTING;
                    } else {
                        serviceName = resultMap.get("SECUR_SRVC_NM").toString();
                    }
                    
                    assessmentList.add(new AssessmentServicesModel(resultMap.get("SECUR_SRVC_CD").toString(),
                            serviceName,
                            ReportsAndDashboardConstants.ASSESSMENT_ACTION_URL + "?srvcd=" + encryptDecrypt.encrypt(resultMap.get("SECUR_SRVC_CD").toString()) + "&engcd=" + engCode));
                }
            }
        }
        return assessmentList;
    }
    
    @Override
    public AssessmentFindingsCountDTO fetchAssessmentsFindliingCounts(String engCode, final String serviceCode,final String engSchema) throws AppException {
        AssessmentFindingsCountDTO assessmentFindingsCount = null;
        String procName = "{CALL Report_FindingAssessments(?,?,?)}";
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName,
                new Object[]{engCode, serviceCode,engSchema});
        //new Object[]{"MAR-HC-20160621", ""});
        if (null != resultList && resultList.size() > 0) {
            for (Map<String, Object> resultMap : resultList) {
                assessmentFindingsCount = new AssessmentFindingsCountDTO();
                assessmentFindingsCount.setTotalCount((Integer) resultMap.get("Total"));
                assessmentFindingsCount.setCriticalCount((Integer) resultMap.get("Critical"));
                assessmentFindingsCount.setHighCount((Integer) resultMap.get("High"));
                assessmentFindingsCount.setMediumCount((Integer) resultMap.get("Medium"));
                assessmentFindingsCount.setLowCount((Integer) resultMap.get("Low"));
                
            }
        }
        return assessmentFindingsCount;
    }

    /**
     * This method for fetch the reports of the engagement
     *
     * @param engagementCode
     * @return List<ReportsModel>
     * @throws AppException
     */
    @Override
    public List<ReportsModel> fetchEngagementReports(String engagementCode) throws AppException {
        String fetchEngagementReports = "{CALL Report_ListReportFileUpload(?)}";
        List<ReportsModel> reportsList = null;
        // String engagementCode1 = "MAX-HC-20160705";
        try {
            
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(fetchEngagementReports, new Object[]{encryptDecrypt.decrypt(engagementCode)});
            //  List<Map<String, Object>> resultList = jdbcTemplate.queryForList(fetchEngagementReports, new Object[]{encryptDecrypt.decrypt(engagementCode)});
            if (!resultList.isEmpty()) {
                reportsList = new ArrayList<>(resultList.size());
                for (Map<String, Object> resultMap : resultList) {
                    //FETCH ONLY PUBLISHED DOCUMENTS 
                    if (null != resultMap.get("LKP_ENTY_NM")
                            && ReportsAndDashboardConstants.DB_STATUS_AS_PUBLISHED.equalsIgnoreCase(resultMap.get("LKP_ENTY_NM").toString())) {
                        ReportsModel reportsModel = new ReportsModel();
                        reportsModel.setName(resultMap.get("RPT_NM") + "");
                        reportsModel.setFilename(resultMap.get("FL_NM") + "");
                        reportsModel.setFilePath(encryptDecrypt.encrypt(resultMap.get("FL_FLDR_PTH") + ""));
                        reportsList.add(reportsModel);
                        
                    }
                }
                
            }
        } catch (Exception e) {
            logger.debug("Exceptionoccured : fetchOSList : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "fetchOSList() :: " + e.getMessage());
        }
        logger.info("End: fetchEngagementReports : fetchEngagementReports");
        return reportsList;
        
    }

    /**
     * @param engCode
     * @param orgSchema
     * @param serviceCode
     * @return List<FindingsModel>
     * @throws AppException
     */
    @Override
    public List<FindingsModel> fetchPrioritizationMatrixGridFindnigs(String engCode,String orgSchema) throws AppException {
        String fetchEngagementReports = "{CALL REPORT_MatrixFindings(?,?)}";
        List<FindingsModel> prioritizationMatrixFindingsList = null;
        //String engagementCode1 = "MAR-HC-20160621";
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(fetchEngagementReports, new Object[]{encryptDecrypt.decrypt(engCode),orgSchema});
        //  List<Map<String, Object>> resultList = jdbcTemplate.queryForList(fetchEngagementReports, new Object[]{encryptDecrypt.decrypt(engagementCode)});
        if (!resultList.isEmpty()) {
            prioritizationMatrixFindingsList = new ArrayList<>(resultList.size());
            for (Map<String, Object> resultMap : resultList) {
                FindingsModel findingsModel = new FindingsModel();
                findingsModel.setId(resultMap.get("ID") + "");
                findingsModel.setFinding(resultMap.get("VULN_NM") + "");
                findingsModel.setRisklevel(resultMap.get("VULN_SEV_NM") + "");
                findingsModel.setDescription(CommonUtil.removeHtmlTagsFromString(resultMap.get("VULN_DESC")));
                findingsModel.setRecommendation(CommonUtil.removeHtmlTagsFromString(resultMap.get("RECOM")));
                prioritizationMatrixFindingsList.add(findingsModel);
            }
            
        }
        return prioritizationMatrixFindingsList;
        
    }

    /**
     * @param engCode
     * @param serviceCode
     * @param orgSchema
     * @return prioritizationMatrixDTO
     * @throws AppException
     */
    @Override
    public PrioritizationMatrixDTO fetchPrioritizatioMatrixQuadrantValues(String engCode, String serviceCode,String orgSchema) throws AppException {
        String fetchEngagementReports = "{CALL REPORT_PRIORITYMATRIX(?,?,?)}";
        //String engagementCode1 = "MAR-HC-20160621";
        PrioritizationMatrixDTO prioritizationMatrixDTO = new PrioritizationMatrixDTO();
        List<String> q1Findings = new ArrayList<>();
        List<String> q2Findings = new ArrayList<>();
        List<String> q3Findings = new ArrayList<>();
        List<String> q4Findings = new ArrayList<>();
        long count1 = 0;
        long count2 = 0;
        long count3 = 0;
        long count4 = 0;
        Map mapCount1 = new HashMap<String, String>();
        Map mapCount2 = new HashMap<String, String>();
        Map mapCount3 = new HashMap<String, String>();
        Map mapCount4 = new HashMap<String, String>();
        String seperator1 = "";
        String seperator2 = "";
        String seperator3 = "";
        String seperator4 = "";
        StringBuilder quadrant1FilterFindings = new StringBuilder();
        StringBuilder quadrant2FilterFindings = new StringBuilder();
        StringBuilder quadrant3FilterFindings = new StringBuilder();
        StringBuilder quadrant4FilterFindings = new StringBuilder();
        
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(fetchEngagementReports, new Object[]{encryptDecrypt.decrypt(engCode), "",orgSchema});
        if (!resultList.isEmpty()) {
            for (Map<String, Object> resultMap : resultList) {
                if (resultMap.get("Quadrant").toString().equalsIgnoreCase("Quadrant1")) {
                    if (!mapCount1.containsKey((resultMap.get("CLNT_VULN_INSTC_KEY") + ""))) {
                        mapCount1.put(resultMap.get("CLNT_VULN_INSTC_KEY") + "", resultMap.get("CLNT_VULN_INSTC_KEY") + "");
                        count1 = count1 + 1;
                        quadrant1FilterFindings.append(seperator1);
                        quadrant1FilterFindings.append(resultMap.get("CLNT_VULN_INSTC_KEY") + "");
                        seperator1 = ",";
                    }
                    if (resultMap.get("SECUR_OBJ_NM") != null && !q1Findings.contains(resultMap.get("SECUR_OBJ_NM"))) {
                        q1Findings.add(resultMap.get("SECUR_OBJ_NM").toString());
                    }
                } else if (resultMap.get("Quadrant").toString().equalsIgnoreCase("Quadrant2")) {
                    if (!mapCount2.containsKey((resultMap.get("CLNT_VULN_INSTC_KEY") + ""))) {
                        mapCount2.put(resultMap.get("CLNT_VULN_INSTC_KEY") + "", resultMap.get("CLNT_VULN_INSTC_KEY") + "");
                        count2 = count2 + 1;
                        quadrant2FilterFindings.append(seperator2);
                        quadrant2FilterFindings.append(resultMap.get("CLNT_VULN_INSTC_KEY") + "");
                        seperator2 = ",";
                    }
                    if (resultMap.get("SECUR_OBJ_NM") != null && !q2Findings.contains(resultMap.get("SECUR_OBJ_NM"))) {
                        q2Findings.add(resultMap.get("SECUR_OBJ_NM").toString());
                    }
                } else if (resultMap.get("Quadrant").toString().equalsIgnoreCase("Quadrant3")) {
                    if (!mapCount3.containsKey((resultMap.get("CLNT_VULN_INSTC_KEY") + ""))) {
                        mapCount3.put(resultMap.get("CLNT_VULN_INSTC_KEY") + "", resultMap.get("CLNT_VULN_INSTC_KEY") + "");
                        count3 = count3 + 1;
                        quadrant3FilterFindings.append(seperator3);
                        quadrant3FilterFindings.append(resultMap.get("CLNT_VULN_INSTC_KEY") + "");
                        seperator3 = ",";
                    }
                    if (resultMap.get("SECUR_OBJ_NM") != null && !q3Findings.contains(resultMap.get("SECUR_OBJ_NM"))) {
                        q3Findings.add(resultMap.get("SECUR_OBJ_NM").toString());
                    }
                } else {
                    if (!mapCount4.containsKey((resultMap.get("CLNT_VULN_INSTC_KEY") + ""))) {
                        mapCount4.put((resultMap.get("CLNT_VULN_INSTC_KEY") + ""), resultMap.get("CLNT_VULN_INSTC_KEY") + "");
                        count4 = count4 + 1;
                        quadrant4FilterFindings.append(seperator4);
                        quadrant4FilterFindings.append(resultMap.get("CLNT_VULN_INSTC_KEY") + "");
                        seperator4 = ",";
                    }
                    if (resultMap.get("SECUR_OBJ_NM") != null && !q4Findings.contains(resultMap.get("SECUR_OBJ_NM"))) {
                        q4Findings.add(resultMap.get("SECUR_OBJ_NM").toString());
                    }
                }
            }
            prioritizationMatrixDTO.setQ1FindingsTotal(count1);
            prioritizationMatrixDTO.setQ2FindingsTotal(count2);
            prioritizationMatrixDTO.setQ3FindingsTotal(count3);
            prioritizationMatrixDTO.setQ4FindingsTotal(count4);
            
            prioritizationMatrixDTO.setQ1Findings(q1Findings);
            prioritizationMatrixDTO.setQ2Findings(q2Findings);
            prioritizationMatrixDTO.setQ3Findings(q3Findings);
            prioritizationMatrixDTO.setQ4Findings(q4Findings);
            
            prioritizationMatrixDTO.setQuadrant1FilterFindings(quadrant1FilterFindings.toString());
            prioritizationMatrixDTO.setQuadrant2FilterFindings(quadrant2FilterFindings.toString());
            prioritizationMatrixDTO.setQuadrant3FilterFindings(quadrant3FilterFindings.toString());
            prioritizationMatrixDTO.setQuadrant4FilterFindings(quadrant4FilterFindings.toString());
            
        }
        return prioritizationMatrixDTO;
    }
    
    @Override
    public List<SummaryDropDownModel> fetchServicesListByEngagment(SecurityModel securityModel) throws AppException {
//        logger.info("Start: ReportsAndDashboardDAOImpl: fetchServicesListByEngagment() to fetch ServicesList By Engagment");
        logger.info(INFO_USER_ACCESSED_SERVICE_LIST_BY_ENGAGEMENT);
        List<SummaryDropDownModel> serviceList = null;
        String procName = "{CALL Report_ListServiceswithFindings(?,?)}";
        
        try {
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{encryptDecrypt.decrypt(CommonUtil.replaceNullWithEmpty(securityModel.getEngagementsDTO().getEngagementKey())),CommonUtil.replaceNullWithEmpty(securityModel.getEngSchemaName())});
            if (null != resultList && resultList.size() > 0) {
                serviceList = new ArrayList<>(resultList.size());
                for (Map<String, Object> resultMap : resultList) {
                    SummaryDropDownModel summaryDropDownModel = new SummaryDropDownModel();
                    summaryDropDownModel.setId(CommonUtil.replaceNullWithEmpty(resultMap.get("SECUR_SRVC_CD")));
                    summaryDropDownModel.setName(CommonUtil.replaceNullWithEmpty(resultMap.get("SECUR_SRVC_NM")));
                    summaryDropDownModel.setMaker("Service");
                    summaryDropDownModel.setTicked(false);
                    serviceList.add(summaryDropDownModel);
                    
                }
            }
            else{
                serviceList = new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : ReportsAndDashboardDAOImpl: fetchServicesListByEngagment(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "ReportsAndDashboardDAOImpl: fetchServicesListByEngagment(): " + e.getMessage());
        }
        logger.info("End: ReportsAndDashboardDAOImpl: fetchServicesListByEngagment() to fetch ServicesList By Engagment");
        return serviceList;
    }
    
    @Override
    public List<SummaryDropDownModel> fetchObjectiveListForSummary() throws AppException {
//        logger.info("Start: ReportsAndDashboardDAOImpl: fetchObjectiveListForSummary() to fetch ObjectiveList For Summary");
        logger.info(INFO_USER_ACCESSED_FETCH_OBJECTIVELIST_FOR_SUMMARY);
        List<SummaryDropDownModel> objectiveList = null;
        String procName = "{CALL Report_ListObjective()}";
        try {
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName);
            if (null != resultList && resultList.size() > 0) {
                objectiveList = new ArrayList<>(resultList.size());
                for (Map<String, Object> resultMap : resultList) {
                    SummaryDropDownModel summaryDropDownModel = new SummaryDropDownModel();
                    summaryDropDownModel.setId(CommonUtil.replaceNullWithEmpty(resultMap.get("SECUR_OBJ_CD")));
                    summaryDropDownModel.setName(CommonUtil.replaceNullWithEmpty(resultMap.get("ObjectiveName")));
                    summaryDropDownModel.setMaker("Category");
                    summaryDropDownModel.setTicked(false);
                    objectiveList.add(summaryDropDownModel);
                    
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : ReportsAndDashboardDAOImpl: fetchObjectiveListForSummary(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "ReportsAndDashboardDAOImpl: fetchObjectiveListForSummary(): " + e.getMessage());
        }
        logger.info("End: ReportsAndDashboardDAOImpl: fetchObjectiveListForSummary() to fetch ObjectiveList For Summary");
        return objectiveList;
    }
    
    @Override
    public List<SummaryDropDownModel> fetchSeverityListForSummary(SecurityModel securityModel) throws AppException {
//        logger.info("Start: ReportsAndDashboardDAOImpl: fetchSeverityListForSummary() to fetch Severity List For Summary");
        logger.info(INFO_USER_ACCESSED_FETCH_LIST_FOR_SUMMARY);
        List<SummaryDropDownModel> severityList = null;
        String procName = "{CALL analyst_ListSeverity(?,?)}";
        try {
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{encryptDecrypt.decrypt(CommonUtil.replaceNullWithEmpty(securityModel.getEngagementsDTO().getEngagementKey())),securityModel.getEngSchemaName()});
            if (null != resultList && resultList.size() > 0) {
                severityList = new ArrayList<>(resultList.size());
                for (Map<String, Object> resultMap : resultList) {
                    SummaryDropDownModel summaryDropDownModel = new SummaryDropDownModel();
                    summaryDropDownModel.setId(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_SEV_CD")));
                    summaryDropDownModel.setName(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_SEV_NM")));
                    summaryDropDownModel.setMaker("Severity");
                    summaryDropDownModel.setTicked(false);
                    severityList.add(summaryDropDownModel);
                    
                }
            }
            else{
                severityList = new ArrayList<>();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : ReportsAndDashboardDAOImpl: fetchSeverityListForSummary(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "ReportsAndDashboardDAOImpl: fetchSeverityListForSummary(): " + e.getMessage());
        }
        logger.info("End: ReportsAndDashboardDAOImpl: fetchSeverityListForSummary() to fetch SeverityList For Summary");
        return severityList;
    }
    
    @Override
    public List<ChartModel> getFindingsDoughnutData(SecurityModel securityModel) throws AppException {
//        logger.info("Start: ReportsAndDashboardDAOImpl: getFindingsDoughnutData() to get Findings Doughnut Data");
        logger.info(INFO_USER_ACCESSED_FINDINGS_DOUGHNUTDATA);
        List<ChartModel> chartList = null;
        String procName = "{CALL Report_FindingsCount(?,?,?)}";
        try {
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{securityModel.getEngagementsDTO().getEngagementKey(), "",securityModel.getEngSchemaName()});
            if (null != resultList && resultList.size() > 0) {
                chartList = new ArrayList<>(resultList.size());
                for (Map<String, Object> resultMap : resultList) {
                    ChartModel chartModel = new ChartModel();
                     String assmentCode = CommonUtil.replaceNullWithEmpty(resultMap.get("SECUR_SRVC_CD"));
                    String serviceName = "";
                    if (assmentCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_APPLICATION_VULNERABILITY_ASSESSMENT)) {
                        serviceName = ReportsAndDashboardConstants.SERVICE_APPLICATION_VULNERABILITY_ASSESSMENT;
                    } else if (assmentCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_NETWORK_VULNERABILITY_ASSESSMENT)) {
                        serviceName = ReportsAndDashboardConstants.SERVICE_NETWORK_VULNERABILITY_ASSESSMENT;
                    } else if (assmentCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_PENETRATION_TESTING)) {
                        serviceName = ReportsAndDashboardConstants.SERVICE_PENETRATION_TESTING;
                    } else if (assmentCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_LIMITED_REDTEAM_ASSESSMENT)) {
                        serviceName = ReportsAndDashboardConstants.SERVICE_LIMITED_REDTEAM_ASSESSMENT;
                    } else if (assmentCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_SECURITY_RISK_ASSESSMENT_CODE)) {
                        serviceName = ReportsAndDashboardConstants.SERVICE_SECURITY_RISK_ASSESSMENT_CODE;
                    } else if (assmentCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_WIRELESS_RISK_ASSESSMENT)) {
                        serviceName = ReportsAndDashboardConstants.SERVICE_WIRELESS_RISK_ASSESSMENT;
                    } else if (assmentCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_THREATHUNTING)) {
                        serviceName = ReportsAndDashboardConstants.SERVICE_NAME_THREATHUNTING;
                    } else {
                        serviceName = CommonUtil.replaceNullWithEmpty(resultMap.get("SECUR_SRVC_NM"));
                    }
                    chartModel.setServiceName(serviceName);
                    chartModel.setTotalFindings(CommonUtil.replaceNullWithEmpty(resultMap.get("Total")));
                    chartModel.setCritical(CommonUtil.replaceNullWithEmpty(resultMap.get("Critical")));
                    chartModel.setHigh(CommonUtil.replaceNullWithEmpty(resultMap.get("High")));
                    chartModel.setMedium(CommonUtil.replaceNullWithEmpty(resultMap.get("Medium")));
                    chartModel.setLow(CommonUtil.replaceNullWithEmpty(resultMap.get("Low")));
                    chartList.add(chartModel);
                    
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : ReportsAndDashboardDAOImpl: getFindingsDoughnutData(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "ReportsAndDashboardDAOImpl: getFindingsDoughnutData(): " + e.getMessage());
        }
        logger.info("End: ReportsAndDashboardDAOImpl: getFindingsDoughnutData() to get Findings Doughnut Data");
        return chartList;
    }
    
    @Override
    public List<ChartModel> getSummaryBubbleChartData(SecurityModel securityModel) throws AppException {
//        logger.info("Start: ReportsAndDashboardDAOImpl: getSummaryBubbleChartData() to get Summary Bubble Chart Data");
        logger.info(INFO_USER_ACCESSED_SUMMARY_BUBLECHART_DATA);
        List<ChartModel> chartList = null;
        String procName = "{CALL Report_ProbabilityImapct(?,?,?,?,?)}";
        int sNo =1;
        try {
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{encryptDecrypt.decrypt(securityModel.getEngagementsDTO().getEngagementKey()), securityModel.getServiceText() == null ? "" : securityModel.getServiceText(), securityModel.getSeverityText() == null ? "" : securityModel.getSeverityText(), securityModel.getCategoryText() == null ? "" : securityModel.getCategoryText(),CommonUtil.replaceNullWithEmpty(securityModel.getEngSchemaName())});
            if (null != resultList && resultList.size() > 0) {
                chartList = new ArrayList<>(resultList.size());
                for (Map<String, Object> resultMap : resultList) {
                    ChartModel chartModel = new ChartModel();
                    chartModel.setFindingId(resultMap.get("FindingsCount").toString());
                    chartModel.setVulnCatyCD(resultMap.get("VULN_CATGY_CD").toString());
                    chartModel.setSeverityLevel(resultMap.get("VULN_CATGY_NM").toString());
                    chartModel.setImpactXScore(resultMap.get("Impact").toString());
                    chartModel.setProbYScore(resultMap.get("Risk").toString());
                    chartModel.setEllipsedFindingId(sNo+"");
                     chartList.add(chartModel);
                    
                    
                    
//                    if (resultMap.get("VULN_IMP_SUB_SCOR") != null && resultMap.get("VULN_EXPLT_SUB_SCOR") != null) {
//                        ChartModel chartModel = new ChartModel();
//                        chartModel.setFindingId(CommonUtil.replaceNullWithEmpty(resultMap.get("CLNT_VULN_INSTC_KEY")));
////                        if (resultMap.get("CLNT_VULN_INSTC_KEY") != null && !resultMap.get("CLNT_VULN_INSTC_KEY").toString().equalsIgnoreCase("")) {
////                            if (resultMap.get("CLNT_VULN_INSTC_KEY").toString().length() <= 3) {
////                                chartModel.setEllipsedFindingId(resultMap.get("CLNT_VULN_INSTC_KEY").toString());
////                            } else {
////                                chartModel.setEllipsedFindingId(resultMap.get("CLNT_VULN_INSTC_KEY").toString().substring(0, 3) + "..");
////                            }
////                        }
//                        chartModel.setEllipsedFindingId(sNo+"");
//                        chartModel.setSeverityLevel(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_SEV_NM")));
//                        chartModel.setImpactXScore(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_IMP_SUB_SCOR")));
//                        chartModel.setProbYScore(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_EXPLT_SUB_SCOR")));
//                        chartList.add(chartModel);
//                    }
                    sNo++;
                    
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : ReportsAndDashboardDAOImpl: getSummaryBubbleChartData(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "ReportsAndDashboardDAOImpl: getSummaryBubbleChartData(): " + e.getMessage());
        }
        logger.info("End: ReportsAndDashboardDAOImpl: getSummaryBubbleChartData() to get Summary Bubble Chart Data");
        return chartList;
    }
    
    @Override
    public List<FindingsModel> getEngagementFindingsData(SecurityModel securityModel) throws AppException {
//        logger.info("Start: ReportsAndDashboardDAOImpl: getEngagementFindingsData() to get Engagement Findings Data");
        logger.info(INFO_USER_ACCESSED_SUMMARY_ENGAGEMENTFINDINGS_DATA);
        List<FindingsModel> findingList = null;
        String procName = "{CALL Report_FindingDetails(?,?,?,?,?)}";
        String findingIdProcName = "{CALL Report_FindingDetailsByID(?,?,?,?)}";
        int sNo = 1;
        try {
            List<Map<String, Object>> resultList = null;
            if (securityModel.getFindingId() != null && !securityModel.getFindingId().equalsIgnoreCase("")) {
                resultList = jdbcTemplate.queryForList(findingIdProcName, new Object[]{encryptDecrypt.decrypt(securityModel.getEngagementsDTO().getEngagementKey()), securityModel.getFindingId(),"S",securityModel.getEngSchemaName()});
            } else {
                resultList = jdbcTemplate.queryForList(procName, new Object[]{encryptDecrypt.decrypt(securityModel.getEngagementsDTO().getEngagementKey()), securityModel.getServiceText() == null ? "" : securityModel.getServiceText(), securityModel.getSeverityText() == null ? "" : securityModel.getSeverityText(), securityModel.getCategoryText() == null ? "" : securityModel.getCategoryText(),securityModel.getEngSchemaName()});
            }
            if (null != resultList && resultList.size() > 0) {
                findingList = new ArrayList<>(resultList.size());
                for (Map<String, Object> resultMap : resultList) {
                    FindingsModel findingsModel = new FindingsModel();
                    if(securityModel.getFindingId() != null && !securityModel.getFindingId().equalsIgnoreCase("")){
                        findingsModel.setId(CommonUtil.replaceNullWithEmpty(resultMap.get("CLNT_VULN_INSTC_KEY")));
                         findingsModel.setRecommendation(CommonUtil.removeHtmlTagsFromString(resultMap.get("RECOM_COMMT_TXT")));
                    }else{
                        findingsModel.setId(CommonUtil.replaceNullWithEmpty(resultMap.get("ID")));
                         findingsModel.setRecommendation(CommonUtil.removeHtmlTagsFromString(resultMap.get("RECOM_COMMT_TXT")));
                    }
                    
                    findingsModel.setFinding(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_NM")));
                    findingsModel.setRisklevel(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_SEV_NM")));
                    findingsModel.setRootCauseDetail(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_CATGY_NM")));
                    findingsModel.setDescription(CommonUtil.removeHtmlTagsFromString(resultMap.get("VULN_DESC")));
                    findingsModel.setHitrust(CommonUtil.replaceCommaByCommaAndSpace(resultMap.get("HITRUST")));
                    findingsModel.setHipaa(CommonUtil.replaceCommaByCommaAndSpace(resultMap.get("HIPAA")));
                    findingsModel.setNist(CommonUtil.replaceCommaByCommaAndSpace(resultMap.get("NIST")));
                    findingsModel.setIrs(CommonUtil.replaceCommaByCommaAndSpace(resultMap.get("IRS")));
                    findingsModel.setMars(CommonUtil.replaceCommaByCommaAndSpace(resultMap.get("MARSE")));
                    findingsModel.setSox(CommonUtil.replaceCommaByCommaAndSpace(resultMap.get("SOC2")));
                    findingsModel.setIso(CommonUtil.replaceCommaByCommaAndSpace(resultMap.get("ISO")));
                    findingsModel.setPci(CommonUtil.replaceCommaByCommaAndSpace(resultMap.get("PCIDSS")));
                    findingsModel.setCsa(CommonUtil.replaceCommaByCommaAndSpace(resultMap.get("CSACCM")));
                    findingsModel.setFisma(CommonUtil.replaceCommaByCommaAndSpace(resultMap.get("FISMA")));
                    findingsModel.setGlba("");
                    findingsModel.setFedRamp(CommonUtil.replaceCommaByCommaAndSpace(resultMap.get("FedRAMP")));
                    if(securityModel.getFindingSno() !=null && !securityModel.getFindingSno().equalsIgnoreCase("")){
                         findingsModel.setsNo(securityModel.getFindingSno());
                    }else{
                    findingsModel.setsNo(sNo + "");
                    }
                    findingList.add(findingsModel);
                     sNo++;
                    
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : ReportsAndDashboardDAOImpl: getEngagementFindingsData(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "ReportsAndDashboardDAOImpl: getEngagementFindingsData(): " + e.getMessage());
        }
        logger.info("End: ReportsAndDashboardDAOImpl: getEngagementFindingsData() to get Engagement Findings Data");
        return findingList;
    }
    
    @Override
    public List<HitrustInfoModel> getHitrustInfoByCtrlCD(String hitrustCd) throws AppException {
//        logger.info("Start: ReportsAndDashboardDAOImpl: getHitrustInfoByCtrlCD() to get Hitrust Info By CtrlCD");
        logger.info(INFO_USER_ACCESSED_HITRUSTINFO_BY_CTRLCD);
        List<HitrustInfoModel> objectList = null;
        String procName = "{CALL Report_GetHitrustInfoByCD(?)}";
//        int i = 1;
        try {
            hitrustCd = hitrustCd.replaceAll(", ", ",");
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{hitrustCd});
            if (null != resultList && resultList.size() > 0) {
                objectList = new ArrayList<>(resultList.size());
                for (Map<String, Object> resultMap : resultList) {
//                if(i==1){
                    HitrustInfoModel hModel = new HitrustInfoModel();
                    hModel.setControlName(CommonUtil.replaceNullWithEmpty(resultMap.get("SECUR_CTL_CD")) + " " + CommonUtil.replaceNullWithEmpty(resultMap.get("SECUR_CTL_NM")));
                    hModel.setFamilyName(CommonUtil.replaceNullWithEmpty(resultMap.get("SECUR_CTL_FAM_CD")) + " " + CommonUtil.replaceNullWithEmpty(resultMap.get("SECUR_CTL_FAM_NM")));
                    hModel.setObjectiveName(CommonUtil.replaceNullWithEmpty(resultMap.get("SECUR_OBJ_CD")) + " " + CommonUtil.replaceNullWithEmpty(resultMap.get("SECUR_OBJ_NM")));
                    hModel.setDescription(CommonUtil.replaceNullWithEmpty(resultMap.get("SECUR_CTL_DESC")));
                    objectList.add(hModel);
//                }
//                i++;
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : ReportsAndDashboardDAOImpl: getHitrustInfoByCtrlCD(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "ReportsAndDashboardDAOImpl: getHitrustInfoByCtrlCD(): " + e.getMessage());
        }
        logger.info("End: ReportsAndDashboardDAOImpl: getHitrustInfoByCtrlCD() to get Hitrust Info By CtrlCD");
        return objectList;
    }

    /**
     * This method for fetch service based remediation charts
     *
     * @param engKey
     * @param serviceCode
     * @return List<RemediationModel>
     * @throws AppException
     */
    @Override
    public List<RemediationChartModel> fetchTopRemediationCharts(final String engKey, final String serviceCode,String orgSchema) throws AppException {
//        logger.info("Start: ReportsAndDashboardDAOImpl: fetchTopRemediationCharts() to fetch remediation finding counts");
        logger.info(INFO_USER_ACCESSED_FETCH_TOP_REMIDIATIONCHARTS);
        List<RemediationChartModel> remediationChartsList = null;
        String procName = "{CALL Report_Remediation(?,?,?)}";
        try {
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{engKey, serviceCode,orgSchema});
            remediationChartsList = new ArrayList<>(resultList.size());
            for (Map<String, Object> resultMap : resultList) {
                RemediationChartModel model = new RemediationChartModel();
                model.setServiceName(resultMap.get("ServiceName") + "");
                model.setCriticalCount(resultMap.get("Critical") + "");
                model.setHighCount(resultMap.get("High") + "");
                model.setMediumCount(resultMap.get("Medium") + "");
                model.setLowCount(resultMap.get("Low") + "");
                model.setCriticalOpenCount(resultMap.get("CriticalOpenCount") + "");
                model.setHighOpenCount(resultMap.get("HighOpenCount") + "");
                model.setMediumOpenCount(resultMap.get("MediumOpenCount") + "");
                model.setLowOpenCount(resultMap.get("LowOpenCount") + "");
                
                remediationChartsList.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : ReportsAndDashboardDAOImpl: fetchTopRemediationCharts(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "ReportsAndDashboardDAOImpl: fetchTopRemediationCharts(): " + e.getMessage());
        }
        logger.info("End: ReportsAndDashboardDAOImpl: fetchTopRemediationCharts() to fetch remediation finding counts");
        return remediationChartsList;
    }

    /**
     * This method for fetch vulnerabilities based on engagement code for
     * remediation
     *
     * @param engKey
     * @param serviceCode
     * @return List<RemediationModel>
     * @throws AppException
     */
    @Override
    public List<RemidiationDataModel> fetchRemediationVulnerabilities(final String engKey, final String serviceCode,String orgSchema) throws AppException {
//        logger.info("Start: ReportsAndDashboardDAOImpl: fetchRemediationVulnerabilities() to fetch vulnerabilities based on engagement code");
        logger.info(INFO_USER_ACCESSED_FETCH_REMIDIATION_VULNERABILITIES);
        List<RemidiationDataModel> remediationChartsList = null;
        String procName = "{CALL Report_RemediationVulnerabilities(?,?,?)}";
        try {
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{engKey, serviceCode,orgSchema});
            remediationChartsList = new ArrayList<>(resultList.size());
            for (Map<String, Object> resultMap : resultList) {
                RemidiationDataModel model = new RemidiationDataModel();
                model.setVulnerabilities(resultMap.get("VULN_NM") + "");
                model.setRiskLevel(resultMap.get("VULN_SEV_NM") + "");
                model.setStatus(resultMap.get("VULN_INSTC_STS_NM") + "");
                
                remediationChartsList.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : ReportsAndDashboardDAOImpl: fetchRemediationVulnerabilities(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "ReportsAndDashboardDAOImpl: fetchRemediationVulnerabilities(): " + e.getMessage());
        }
        logger.info("End: ReportsAndDashboardDAOImpl: fetchRemediationVulnerabilities() to fetch vulnerabilities based on engagement code");
        return remediationChartsList;
    }
    
//    @Override
//    public List<FindingsModel> getEngagementFindingsDataForExportCSV(SecurityModel securityModel) throws AppException {
////        logger.info("Start: ReportsAndDashboardDAOImpl: getEngagementFindingsData() to get Engagement Findings Data");
//        logger.info(INFO_USER_ACCESSED_ENGAGEMENT_FINDINGS_DATA_FOR_EXPORTCSV);
//        List<FindingsModel> findingList = null;
//        String procName = "{CALL Report_GetVulnerabilityCSV(?,?,?,?)}";
////        String findingIdProcName = "{CALL Report_FindingDetailsByID(?,?)}";
//        try {
////            List<Map<String, Object>> resultList = null;
////            if (securityModel.getFindingId() != null && !securityModel.getFindingId().equalsIgnoreCase("")) {
////                resultList = jdbcTemplate.queryForList(findingIdProcName, new Object[]{encryptDecrypt.decrypt(securityModel.getEngagementsDTO().getEngagementKey()), Integer.parseInt(securityModel.getFindingId())});
////            } else {
////                resultList = jdbcTemplate.queryForList(procName, new Object[]{encryptDecrypt.decrypt(securityModel.getEngagementsDTO().getEngagementKey()), securityModel.getServiceText() == null ? "" : securityModel.getServiceText(), securityModel.getSeverityText() == null ? "" : securityModel.getSeverityText(), securityModel.getCategoryText() == null ? "" : securityModel.getCategoryText()});
////            }
//            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{encryptDecrypt.decrypt(securityModel.getEngagementsDTO().getEngagementKey()), securityModel.getServiceText() == null ? "" : securityModel.getServiceText(), securityModel.getSeverityText() == null ? "" : securityModel.getSeverityText(), securityModel.getCategoryText() == null ? "" : securityModel.getCategoryText()});
//            if (null != resultList && resultList.size() > 0) {
//                findingList = new ArrayList<>(resultList.size());
//                for (Map<String, Object> resultMap : resultList) {
//                    FindingsModel findingsModel = new FindingsModel();
//                    findingsModel.setId(CommonUtil.replaceNullWithEmpty(resultMap.get("CLNT_VULN_INSTC_KEY")));
//                    findingsModel.setFinding(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_NM")));
//                    findingsModel.setSource(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_SRC")));
//                    findingsModel.setCostEffort(CommonUtil.replaceNullWithEmpty(resultMap.get("RMDTN_CST_EFFRT_NM")));
//                    findingsModel.setDomain(CommonUtil.replaceNullWithEmpty(resultMap.get("DOM_NM")));
//                    findingsModel.setRisklevel(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_SEV_NM")));
//                    findingsModel.setIpAddress(CommonUtil.replaceNullWithEmpty(resultMap.get("IPADR")));
//                    findingsModel.setStatus(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_INSTC_STS_NM")));
//                    findingsModel.setService(CommonUtil.replaceNullWithEmpty(resultMap.get("SECUR_SRVC_NM")));
//                    if (resultMap.get("SRC_VULN_SCAN_ID") != null) {
//                        findingsModel.setScanIdentifier(resultMap.get("SRC_VULN_SCAN_ID").toString());
//                    }
//                    if (resultMap.get("STRT_DT") != null) {
//                        findingsModel.setScanStartDate(resultMap.get("STRT_DT").toString());
//                    }
//                    if (resultMap.get("END_DT") != null) {
//                        findingsModel.setScanEndDate(resultMap.get("END_DT").toString());
//                    }
//                    findingsModel.setSoftwareName(CommonUtil.replaceNullWithEmpty(resultMap.get("SFTW_NM")));
//                    findingsModel.setHost(CommonUtil.replaceNullWithEmpty(resultMap.get("HST_NM")));
//                    findingsModel.setUrl(CommonUtil.replaceNullWithEmpty(resultMap.get("APPL_URL")));
//                    if (resultMap.get("OS_NM") != null) {
//                        findingsModel.setOperatingSystem(resultMap.get("OS_NM").toString());
//                    }
//                    if (resultMap.get("NETBIOS_NM") != null) {
//                        findingsModel.setNetBios(resultMap.get("NETBIOS_NM").toString());
//                    }
//                    findingsModel.setMacAddress(CommonUtil.replaceNullWithEmpty(resultMap.get("MAC_ADR_NM")));
//                    findingsModel.setPort(resultMap.get("PORT_NBR") != null ? resultMap.get("PORT_NBR").toString() : "0");
//                    findingsModel.setRecommendation(CommonUtil.replaceNullWithEmpty(resultMap.get("RECOM_COMMT_TXT")));
//                    findingsModel.setDescription(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_DESC")));
//                    findingsModel.setTechnicalDetails(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_TECH_COMMT_TXT")));
//                    findingsModel.setImpactDetail(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_IMP_COMMT_TXT")));
//                    findingsModel.setRootCauseDetail(CommonUtil.replaceNullWithEmpty(resultMap.get("ROOT_CAUS_COMMT_TXT")));
////                    findingsModel.setCveId(resultMap.get("CVE_ID").toString());
//
//                    if (resultMap.get("CVE_ID") != null) {
//                        findingsModel.setCveId(resultMap.get("CVE_ID").toString());
//                    }
//                    if (resultMap.get("CVE_DESC") != null) {
//                        findingsModel.setCveDescription(resultMap.get("CVE_DESC").toString());
//                    }
//                    if (resultMap.get("VULN_BAS_SCOR") != null) {
//                        findingsModel.setBaseScore(resultMap.get("VULN_BAS_SCOR").toString());
//                    }
//                    if (resultMap.get("VULN_TMPRL_SCOR") != null) {
//                        findingsModel.setTemporalScore(resultMap.get("VULN_TMPRL_SCOR").toString());
//                    }
//                    if (resultMap.get("VULN_ENV_SCOR") != null) {
//                        findingsModel.setEnvironmentalScore(resultMap.get("VULN_ENV_SCOR").toString());
//                    }
//                    findingsModel.setOverAllScore(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_OVALL_SCOR")));
//                    findingsModel.setSeverity(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_SEV_NM")));
//                    findingsModel.setImpact(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_IMP_NM")));
//                    findingsModel.setProbability(CommonUtil.replaceNullWithEmpty(resultMap.get("RISK_PRBL_NM")));
//                    findingsModel.setHitrust(CommonUtil.replaceNullWithEmpty(resultMap.get("HITRUST")));
//                    findingsModel.setHipaa(CommonUtil.replaceNullWithEmpty(resultMap.get("HIPAA")));
//                    findingsModel.setNist(CommonUtil.replaceNullWithEmpty(resultMap.get("NIST")));
//                    findingsModel.setIrs(CommonUtil.replaceNullWithEmpty(resultMap.get("IRS")));
//                    findingsModel.setMars(CommonUtil.replaceNullWithEmpty(resultMap.get("MARSE")));
//                    findingsModel.setSox(CommonUtil.replaceNullWithEmpty(resultMap.get("SOC2")));
//                    findingsModel.setIso(CommonUtil.replaceNullWithEmpty(resultMap.get("ISO")));
//                    findingsModel.setPci(CommonUtil.replaceNullWithEmpty(resultMap.get("PCIDSS")));
//                    findingsModel.setCsa(CommonUtil.replaceNullWithEmpty(resultMap.get("CSACCM")));
//                    findingsModel.setFisma(CommonUtil.replaceNullWithEmpty(resultMap.get("FISMA")));
//                    findingsModel.setGlba("");
//                    findingsModel.setFedRamp(CommonUtil.replaceNullWithEmpty(resultMap.get("FedRAMP")));
//                    findingList.add(findingsModel);
//                    
//                }
//            }
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.debug("Exception occured : ReportsAndDashboardDAOImpl: getEngagementFindingsData(): " + e.getMessage());
//            throw new AppException("Exception occured ehile Excecuting the "
//                    + "ReportsAndDashboardDAOImpl: getEngagementFindingsData(): " + e.getMessage());
//        }
//        logger.info("End: ReportsAndDashboardDAOImpl: getEngagementFindingsData() to get Engagement Findings Data");
//        return findingList;
//    }
    
//    @Override
//    public List<FindingsModel> getEngagementFindingsDataForExportCSV(SecurityModel securityModel) throws AppException {
//        logger.info(INFO_USER_ACCESSED_ENGAGEMENT_FINDINGS_DATA_FOR_EXPORTCSV);
//        List<FindingsModel> findingList = null;
//        String procName = "{CALL Report_GetVulnerabilityCSV(?,?,?,?)}";
//        try {
//            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{encryptDecrypt.decrypt(securityModel.getEngagementsDTO().getEngagementKey()), securityModel.getServiceText() == null ? "" : securityModel.getServiceText(), securityModel.getSeverityText() == null ? "" : securityModel.getSeverityText(), securityModel.getCategoryText() == null ? "" : securityModel.getCategoryText()});
//            if (null != resultList && resultList.size() > 0) {
//                findingList = new ArrayList<>(resultList.size());
//                for (Map<String, Object> resultMap : resultList) {
//                    FindingsModel findingsModel = new FindingsModel();
//                    findingsModel.setId(CommonUtil.replaceNullWithEmpty(resultMap.get("CLNT_VULN_INSTC_KEY")));
//                    findingsModel.setFinding(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_NM")));
//                    findingsModel.setSource(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_SRC")));
//                    findingsModel.setCostEffort(CommonUtil.replaceNullWithEmpty(resultMap.get("RMDTN_CST_EFFRT_NM")));
//                    findingsModel.setDomain(CommonUtil.replaceNullWithEmpty(resultMap.get("DOM_NM")));
//                    findingsModel.setRisklevel(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_SEV_NM")));
//                    findingsModel.setIpAddress(CommonUtil.replaceNullWithEmpty(resultMap.get("IPADR")));
//                    findingsModel.setStatus(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_INSTC_STS_NM")));
//                    findingsModel.setService(CommonUtil.replaceNullWithEmpty(resultMap.get("SECUR_SRVC_NM")));
//                    if (resultMap.get("SRC_VULN_SCAN_ID") != null) {
//                        findingsModel.setScanIdentifier(resultMap.get("SRC_VULN_SCAN_ID").toString());
//                    }
//                    if (resultMap.get("STRT_DT") != null) {
//                        findingsModel.setScanStartDate(resultMap.get("STRT_DT").toString());
//                    }
//                    if (resultMap.get("END_DT") != null) {
//                        findingsModel.setScanEndDate(resultMap.get("END_DT").toString());
//                    }
//                    findingsModel.setSoftwareName(CommonUtil.replaceNullWithEmpty(resultMap.get("SFTW_NM")));
//                    findingsModel.setHost(CommonUtil.replaceNullWithEmpty(resultMap.get("HST_NM")));
//                    findingsModel.setUrl(CommonUtil.replaceNullWithEmpty(resultMap.get("APPL_URL")));
//                    if (resultMap.get("OS_NM") != null) {
//                        findingsModel.setOperatingSystem(resultMap.get("OS_NM").toString());
//                    }
//                    if (resultMap.get("NETBIOS_NM") != null) {
//                        findingsModel.setNetBios(resultMap.get("NETBIOS_NM").toString());
//                    }
//                    findingsModel.setMacAddress(CommonUtil.replaceNullWithEmpty(resultMap.get("MAC_ADR_NM")));
//                    findingsModel.setPort(resultMap.get("PORT_NBR") != null ? resultMap.get("PORT_NBR").toString() : "0");
//                    findingsModel.setRecommendation(CommonUtil.replaceNullWithEmpty(resultMap.get("RECOM_COMMT_TXT")));
//                    findingsModel.setDescription(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_DESC")));
//                    findingsModel.setTechnicalDetails(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_TECH_COMMT_TXT")));
//                    findingsModel.setImpactDetail(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_IMP_COMMT_TXT")));
//                    findingsModel.setRootCauseDetail(CommonUtil.replaceNullWithEmpty(resultMap.get("ROOT_CAUS_COMMT_TXT")));
////                    findingsModel.setCveId(resultMap.get("CVE_ID").toString());
//
//                    if (resultMap.get("CVE_ID") != null) {
//                        findingsModel.setCveId(resultMap.get("CVE_ID").toString());
//                    }
//                    if (resultMap.get("CVE_DESC") != null) {
//                        findingsModel.setCveDescription(resultMap.get("CVE_DESC").toString());
//                    }
//                    if (resultMap.get("VULN_BAS_SCOR") != null) {
//                        findingsModel.setBaseScore(resultMap.get("VULN_BAS_SCOR").toString());
//                    }
//                    if (resultMap.get("VULN_TMPRL_SCOR") != null) {
//                        findingsModel.setTemporalScore(resultMap.get("VULN_TMPRL_SCOR").toString());
//                    }
//                    if (resultMap.get("VULN_ENV_SCOR") != null) {
//                        findingsModel.setEnvironmentalScore(resultMap.get("VULN_ENV_SCOR").toString());
//                    }
//                    findingsModel.setOverAllScore(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_OVALL_SCOR")));
//                    findingsModel.setSeverity(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_SEV_NM")));
//                    findingsModel.setImpact(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_IMP_NM")));
//                    findingsModel.setProbability(CommonUtil.replaceNullWithEmpty(resultMap.get("RISK_PRBL_NM")));
//                    findingsModel.setHitrust(CommonUtil.replaceNullWithEmpty(resultMap.get("HITRUST")));
//                    findingsModel.setHipaa(CommonUtil.replaceNullWithEmpty(resultMap.get("HIPAA")));
//                    findingsModel.setNist(CommonUtil.replaceNullWithEmpty(resultMap.get("NIST")));
//                    findingsModel.setIrs(CommonUtil.replaceNullWithEmpty(resultMap.get("IRS")));
//                    findingsModel.setMars(CommonUtil.replaceNullWithEmpty(resultMap.get("MARSE")));
//                    findingsModel.setSox(CommonUtil.replaceNullWithEmpty(resultMap.get("SOC2")));
//                    findingsModel.setIso(CommonUtil.replaceNullWithEmpty(resultMap.get("ISO")));
//                    findingsModel.setPci(CommonUtil.replaceNullWithEmpty(resultMap.get("PCIDSS")));
//                    findingsModel.setCsa(CommonUtil.replaceNullWithEmpty(resultMap.get("CSACCM")));
//                    findingsModel.setFisma(CommonUtil.replaceNullWithEmpty(resultMap.get("FISMA")));
//                    findingsModel.setGlba("");
//                    findingsModel.setFedRamp(CommonUtil.replaceNullWithEmpty(resultMap.get("FedRAMP")));
//                    findingList.add(findingsModel);
//                    
//                }
//            }
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.debug("Exception occured : ReportsAndDashboardDAOImpl: getEngagementFindingsData(): " + e.getMessage());
//            throw new AppException("Exception occured ehile Excecuting the "
//                    + "ReportsAndDashboardDAOImpl: getEngagementFindingsData(): " + e.getMessage());
//        }
//        logger.info("End: ReportsAndDashboardDAOImpl: getEngagementFindingsData() to get Engagement Findings Data");
//        return findingList;
//    }
//    
    @Override
    public EngagementsDTO getEngagementDetailsForExportCSV(final SecurityModel securityModel) throws AppException {
//        logger.info("Start: ReportsAndDashboardDAOImpl : getEngagementDetailsForExportCSV");
        logger.info(INFO_USER_ACCESSED_ENGAGEMENT_DETAILS_FOR_EXPORTCSV);
        String viewEngagementProc = "{CALL Get_ClntEngmtByID(?)}";
        EngagementsDTO engagementDTO = new EngagementsDTO();
        
        try {
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(viewEngagementProc, new Object[]{encryptDecrypt.decrypt(securityModel.getEngagementsDTO().getEngagementKey())});
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    engagementDTO.setEngagementKey(resultMap.get("CLNT_ENGMT_CD") + "");
                    engagementDTO.setEngagmentName(resultMap.get("CLNT_ENGMT_NM") + "");
                    engagementDTO.setAgreementDate(dateUtil.dateConvertionFromDBToJSP(resultMap.get("AGR_DT") + ""));
                    engagementDTO.setClientName(resultMap.get("ClientName") + "");
                    engagementDTO.setEngagementPkgName(resultMap.get("SECUR_PKG_NM") + "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : getEngagementDetailsForExportCSV : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "getEngagementDetailsForExportCSV() :: " + e.getMessage());
        }
        logger.info("End:  ReportsAndDashboardDAOImpl : getEngagementDetailsForExportCSV");
        return engagementDTO;
    }

    /**
     * This method for fetchPrioritizationFilterFindings
     *
     * @param engKey
     * @param findingId
     * @return List<RemediationModel>
     * @throws AppException
     */
    @Override
    public List<FindingsModel> fetchPrioritizationFilterFindings(String engKey, String findingId,String orgSchema) throws AppException {
        String fetchEngagementReports = "{CALL Report_FindingDetailsByID(?,?,?,?)}";
        List<FindingsModel> prioritizationMatrixFindingsList = null; 
        if(!findingId.trim().equalsIgnoreCase("")){
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(fetchEngagementReports, new Object[]{encryptDecrypt.decrypt(engKey), findingId,"M",orgSchema});
        if (!resultList.isEmpty()) {
            prioritizationMatrixFindingsList = new ArrayList<>(resultList.size());
            for (Map<String, Object> resultMap : resultList) {
                FindingsModel findingsModel = new FindingsModel();
                findingsModel.setId(CommonUtil.replaceNullWithEmpty(resultMap.get("CLNT_VULN_INSTC_KEY")));
                findingsModel.setFinding(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_NM")));
                findingsModel.setRisklevel(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_SEV_NM")));
                findingsModel.setDescription(CommonUtil.removeHtmlTagsFromString(resultMap.get("VULN_DESC")));
                findingsModel.setRecommendation(CommonUtil.removeHtmlTagsFromString(resultMap.get("RECOM_COMMT_TXT")));
                prioritizationMatrixFindingsList.add(findingsModel);
            }
        }
        }
        return prioritizationMatrixFindingsList;
        
    }
    
    @Override
    public SecurityModel getSummaryTopRootCauseChart(SecurityModel securityModel) throws AppException {
        logger.info(INFO_USER_ACCESSED_ENGAGEMENT_DETAILS_FOR_TOP_ROOTCAUSE);
        List<AssessmentModel> categoryLabelList = new ArrayList<>();
        List<AssessmentModel> categoryDataList = new ArrayList<>();
        try {
            String procName = "{CALL Report_RouteCauseAnalysis(?,?,?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{encryptDecrypt.decrypt(securityModel.getEngagementsDTO().getEngagementKey()), "", "AL", "",securityModel.getEngSchemaName()});
            if (null != resultList && resultList.size() > 0) {
                for (Map<String, Object> resultMap : resultList) {
                    if (resultMap.get("VULN_CATGY_NM") != null && !((String) resultMap.get("VULN_CATGY_NM")).equalsIgnoreCase("")) {
                        AssessmentModel labelModel = new AssessmentModel();
                        labelModel.setLabel(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_CATGY_NM")));
                        categoryLabelList.add(labelModel);
                        
                        AssessmentModel dataModel = new AssessmentModel();
                        dataModel.setValue(CommonUtil.replaceNullWithEmpty(resultMap.get("Percentage")));
                        categoryDataList.add(dataModel);
                    }
                }
                securityModel.setCategoryLabelList(categoryLabelList);
                securityModel.setCategoryDataList(categoryDataList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : getSummaryTopRootCauseChart : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "getSummaryTopRootCauseChart() :: " + e.getMessage());
        }
        logger.info("End:  ReportsAndDashboardDAOImpl : getSummaryTopRootCauseChart");
        return securityModel;
    }
    @Override
    public List getEngagementFindingsDataForExportCSV(SecurityModel securityModel) throws AppException {
//        logger.info("Start: ReportsAndDashboardDAOImpl: getEngagementFindingsData() to get Engagement Findings Data");
        logger.info(INFO_USER_ACCESSED_ENGAGEMENT_FINDINGS_DATA_FOR_EXPORTCSV);
        String procName = "{CALL Report_GetVulnerabilityCSV(?,?,?,?,?)}";
        String findingIdProcName = "{CALL Report_FindingDetailsByID(?,?,?,?)}";
        List dbhlist = securityModel.getCsvDBHeaders();
        List csvlist = securityModel.getCsvHeaders();
        List finalList = new ArrayList();
        finalList.add(csvlist);
        
        try {
            List<Map<String, Object>> resultList = null;
                        if (securityModel.getFindingId() != null && !securityModel.getFindingId().equalsIgnoreCase("")) {
                resultList = jdbcTemplate.queryForList(findingIdProcName, new Object[]{encryptDecrypt.decrypt(securityModel.getEngagementsDTO().getEngagementKey()), securityModel.getFindingId(),"S",securityModel.getEngSchemaName()});
            } else {
               resultList = jdbcTemplate.queryForList(procName, new Object[]{encryptDecrypt.decrypt(securityModel.getEngagementsDTO().getEngagementKey()), securityModel.getServiceText() == null ? "" : securityModel.getServiceText(), securityModel.getSeverityText() == null ? "" : securityModel.getSeverityText(), securityModel.getCategoryText() == null ? "" : securityModel.getCategoryText(),securityModel.getEngSchemaName()});
            }
//              List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{encryptDecrypt.decrypt(securityModel.getEngagementsDTO().getEngagementKey()), securityModel.getServiceText() == null ? "" : securityModel.getServiceText(), securityModel.getSeverityText() == null ? "" : securityModel.getSeverityText(), securityModel.getCategoryText() == null ? "" : securityModel.getCategoryText()});
            if (null != resultList && resultList.size() > 0) {
                for (Map<String, Object> resultMap : resultList) {
                    List entryList = new ArrayList();
                    for (Object str : dbhlist) {
                        String s = str.toString();
                         entryList.add(CommonUtil.removeHtmlTagsFromString(resultMap.get(s)));
                        
                    }
                    finalList.add(entryList);
                    
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : ReportsAndDashboardDAOImpl: getEngagementFindingsDataForExportCSV(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "ReportsAndDashboardDAOImpl: getEngagementFindingsDataForExportCSV(): " + e.getMessage());
        }
        logger.info("End: ReportsAndDashboardDAOImpl: getEngagementFindingsDataForExportCSV() to get Engagement Findings Data");
        return finalList;
    }
    
    @Override
    public List getMatrixFindingsDataForExportCSV(SecurityModel securityModel) throws AppException {
//        logger.info("Start: ReportsAndDashboardDAOImpl: getEngagementFindingsData() to get Engagement Findings Data");
        logger.info(INFO_USER_ACCESSED_ENGAGEMENT_FINDINGS_DATA_FOR_EXPORTCSV);
        String procName = "{CALL Report_FindingDetailsByID(?,?,?,?)}";
        List dbhlist = securityModel.getCsvDBHeaders();
        List csvlist = securityModel.getCsvHeaders();
        List finalList = new ArrayList();
        finalList.add(csvlist);
        
        try {
              List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{encryptDecrypt.decrypt(securityModel.getEngagementsDTO().getEngagementKey()), securityModel.getFindingId(),"M",securityModel.getEngSchemaName()});
            if (null != resultList && resultList.size() > 0) {
                for (Map<String, Object> resultMap : resultList) {
                    List entryList = new ArrayList();
                    for (Object str : dbhlist) {
                        String s = str.toString();
                         entryList.add(CommonUtil.removeHtmlTagsFromString(resultMap.get(s)));
                        
                    }
                    finalList.add(entryList);
                    
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : ReportsAndDashboardDAOImpl: getEngagementFindingsDataForExportCSV(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "ReportsAndDashboardDAOImpl: getEngagementFindingsDataForExportCSV(): " + e.getMessage());
        }
        logger.info("End: ReportsAndDashboardDAOImpl: getEngagementFindingsDataForExportCSV() to get Engagement Findings Data");
        return finalList;
    }
    
    @Override
    public List<SummaryDropDownModel> fetchRootCauseListForSummary(SecurityModel securityModel) throws AppException {
//        logger.info("Start: ReportsAndDashboardDAOImpl: fetchObjectiveListForSummary() to fetch ObjectiveList For Summary");
        logger.info(INFO_USER_ACCESSED_FETCH_ROOTCAUSELIST_FOR_SUMMARY);
        List<SummaryDropDownModel> objectiveList = null;
        String procName = "{CALL Analyst_ListVULNCATGY(?,?)}";
        try {
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{encryptDecrypt.decrypt(CommonUtil.replaceNullWithEmpty(securityModel.getEngagementsDTO().getEngagementKey())),CommonUtil.replaceNullWithEmpty(securityModel.getEngSchemaName())});
            if (null != resultList && resultList.size() > 0) {
                objectiveList = new ArrayList<>(resultList.size());
                for (Map<String, Object> resultMap : resultList) {
                    SummaryDropDownModel summaryDropDownModel = new SummaryDropDownModel();
                    summaryDropDownModel.setId(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_CATGY_CD")));
                    summaryDropDownModel.setName(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_CATGY_NM")));
                    summaryDropDownModel.setMaker("Category");
                    summaryDropDownModel.setTicked(false);
                    objectiveList.add(summaryDropDownModel);
                    
                }
            }else{
                objectiveList = new ArrayList<>();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : ReportsAndDashboardDAOImpl: fetchRootCauseListForSummary(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "ReportsAndDashboardDAOImpl: fetchRootCauseListForSummary(): " + e.getMessage());
        }
        logger.info("End: ReportsAndDashboardDAOImpl: fetchRootCauseListForSummary() to fetch root cause List For Summary");
        return objectiveList;
    }
    
    @Override
    public String getSchemaNameByEngKey(String engKey) throws AppException {
//        logger.info("Start: ReportsAndDashboardDAOImpl: fetchObjectiveListForSummary() to fetch ObjectiveList For Summary");
        logger.info(INFO_USER_ACCESSED_FETCH_ROOTCAUSELIST_FOR_SUMMARY);
        String procName = "{CALL GetSchmaByEngmtCD(?)}";
        String engSchema = ApplicationConstants.EMPTY_STRING;
        try {
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{encryptDecrypt.decrypt(CommonUtil.replaceNullWithEmpty(engKey))});
            if (null != resultList && resultList.size() > 0) {
                for (Map<String, Object> resultMap : resultList) {
                    engSchema = CommonUtil.replaceNullWithEmpty(resultMap.get("ORG_SCHM"));

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : ReportsAndDashboardDAOImpl: fetchRootCauseListForSummary(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "ReportsAndDashboardDAOImpl: fetchRootCauseListForSummary(): " + e.getMessage());
        }
        logger.info("End: ReportsAndDashboardDAOImpl: fetchRootCauseListForSummary() to fetch root cause List For Summary");
        return engSchema;
    }
}
