/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao.impl;

import com.optum.oss.constants.ReportsAndDashboardConstants;
import com.optum.oss.dao.AssessmentDAO;
import com.optum.oss.exception.AppException;
import com.optum.oss.model.AssessmentModel;
import com.optum.oss.model.FindingsModel;
import com.optum.oss.model.SecurityModel;
import com.optum.oss.model.SummaryDropDownModel;
import com.optum.oss.util.CommonUtil;
import com.optum.oss.util.EncryptDecrypt;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author rpanuganti
 */
public class AssessmentDAOImpl implements AssessmentDAO {

    @Autowired
    private EncryptDecrypt encryptDecrypt;

    private static final Logger logger = Logger.getLogger(AssessmentDAOImpl.class);
    /*
     START : LOG MESSAGES
     */
    private final String INFO_USER_ACCESSED_SERVICE_FINDIMGS_FOR_EXPORTCSV = "User accessed service findings for exportcsv page";

    /*
     END: LOG MESSAGES
     */
    private JdbcTemplate jdbcTemplate;
    /*
     Start : Setter getters for private variables
     */

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    /*
     End : Setter getters for private variables
     */

    @Override
    public List<AssessmentModel> topOSCategories(String engagementCode, String serviceCode, String orgSchema) throws AppException {
        List<AssessmentModel> osList = new ArrayList<AssessmentModel>();
        try {
            String procName = "{CALL Report_TopOSCategories(?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{engagementCode, serviceCode, orgSchema});
            if (null != resultList && resultList.size() > 0) {
                for (Map<String, Object> resultMap : resultList) {
                    if (resultMap.get("OS_CATGY_NM") != null && !((String) resultMap.get("OS_CATGY_NM")).equalsIgnoreCase("")) {
                        AssessmentModel model = new AssessmentModel();
                        model.setLabel((String) resultMap.get("OS_CATGY_NM"));
                        model.setValue(resultMap.get("VULN_COUNT") + "");
                        osList.add(model);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : topOSCategories():" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "topOSCategories() :: " + e.getMessage());
        }
        return osList;
    }

    @Override
    public SecurityModel oSCategoriesList(String engagementCode, String serviceCode, String orgSchema) throws AppException {
        List<AssessmentModel> labelList = new ArrayList<AssessmentModel>();
        List<AssessmentModel> valueList = new ArrayList<AssessmentModel>();
        SecurityModel securityModel = new SecurityModel();
        try {
            String procName = "{CALL Report_TopOSCategories(?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{engagementCode, serviceCode, orgSchema});
            if (null != resultList && resultList.size() > 0) {
                for (Map<String, Object> resultMap : resultList) {
                    if (resultMap.get("OS_CATGY_NM") != null && !((String) resultMap.get("OS_CATGY_NM")).equalsIgnoreCase("")) {
                        AssessmentModel labelModel = new AssessmentModel();
                        labelModel.setLabel((String) resultMap.get("OS_CATGY_NM"));

                        AssessmentModel valueModel = new AssessmentModel();
                        valueModel.setValue(resultMap.get("VULN_COUNT") + "");

                        labelList.add(labelModel);
                        valueList.add(valueModel);
                    }
                }
            }
            securityModel.setCategoryLabelList(labelList);
            securityModel.setCategoryDataList(valueList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : topOSCategories():" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "topOSCategories() :: " + e.getMessage());
        }
        return securityModel;
    }

    @Override
    public List<AssessmentModel> topRootCauses(AssessmentModel assessmentObj) throws Exception {
        List<AssessmentModel> rootList = new ArrayList<AssessmentModel>();
        try {
            String procName = "{CALL Report_RouteCauseAnalysis(?,?,?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{assessmentObj.getEngagementCode(), assessmentObj.getServiceCode(), assessmentObj.getAssessmentFlag(), assessmentObj.getSelectedString(), assessmentObj.getOrgSchema()});
            if (null != resultList && resultList.size() > 0) {
                for (Map<String, Object> resultMap : resultList) {
                    if (resultMap.get("VULN_CATGY_NM") != null && !((String) resultMap.get("VULN_CATGY_NM")).equalsIgnoreCase("")) {
                        AssessmentModel model = new AssessmentModel();
                        model.setLabel((String) resultMap.get("VULN_CATGY_NM"));
                        model.setValue(resultMap.get("Percentage") + "");
                        rootList.add(model);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : topRootCauses():" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "topRootCauses() :: " + e.getMessage());
        }
        return rootList;
    }

    @Override
    public SecurityModel rootCausesList(AssessmentModel assessmentObj) throws Exception {
        List<AssessmentModel> labelList = new ArrayList<AssessmentModel>();
        List<AssessmentModel> valueList = new ArrayList<AssessmentModel>();
        SecurityModel securityModel = new SecurityModel();
        try {
            String procName = "{CALL Report_RouteCauseAnalysis(?,?,?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{assessmentObj.getEngagementCode(), assessmentObj.getServiceCode(), assessmentObj.getAssessmentFlag(), assessmentObj.getSelectedString(), assessmentObj.getOrgSchema()});
            if (null != resultList && resultList.size() > 0) {
                for (Map<String, Object> resultMap : resultList) {
                    if (resultMap.get("VULN_CATGY_NM") != null && !((String) resultMap.get("VULN_CATGY_NM")).equalsIgnoreCase("")) {
                        AssessmentModel labelModel = new AssessmentModel();
                        labelModel.setLabel((String) resultMap.get("VULN_CATGY_NM"));

                        AssessmentModel valueModel = new AssessmentModel();
                        valueModel.setValue(resultMap.get("Percentage") + "");

                        labelList.add(labelModel);
                        valueList.add(valueModel);
                    }
                }
            }
            securityModel.setCategoryLabelList(labelList);
            securityModel.setCategoryDataList(valueList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : topRootCauses():" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "topRootCauses() :: " + e.getMessage());
        }
        return securityModel;
    }

    @Override
    public List<AssessmentModel> severityList(AssessmentModel assessmentObj) throws Exception {
        List<AssessmentModel> severityList = new ArrayList<AssessmentModel>();
        try {
            String procName = "{CALL Report_Severity(?,?,?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{assessmentObj.getEngagementCode(), assessmentObj.getServiceCode(), assessmentObj.getAssessmentFlag(), assessmentObj.getSelectedString(), assessmentObj.getOrgSchema()});
            if (null != resultList && resultList.size() > 0) {
                for (Map<String, Object> resultMap : resultList) {
                    AssessmentModel model = new AssessmentModel();
                    model.setLabel((String) resultMap.get("Header"));
                    model.setValue(resultMap.get("SeverityCount") + "");
                    severityList.add(model);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : severityList():" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "severityList() :: " + e.getMessage());
        }
        return severityList;
    }

    public List topHosts(String engagementCode, String serviceCode, String orgSchema) throws AppException {
        List<AssessmentModel> osList = new ArrayList<AssessmentModel>();
        List finalList = new ArrayList();
        try {
            String procName = "{CALL Report_TopHost(?,?,?)}";
            List<String> hotsName = new ArrayList<String>();
            List<Integer> critacalList = new ArrayList<Integer>();
            List<Integer> highList = new ArrayList<Integer>();
            List<Integer> mediumList = new ArrayList<Integer>();
            List<Integer> lowList = new ArrayList<Integer>();

            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{engagementCode, serviceCode, orgSchema});
            if (null != resultList && resultList.size() > 0) {
                for (Map<String, Object> resultMap : resultList) {
                    if (resultMap.get("HST_NM") != null && !((String) resultMap.get("HST_NM")).equalsIgnoreCase("")) {
                        hotsName.add((String) resultMap.get("HST_NM"));
                        critacalList.add((Integer) resultMap.get("Critical"));
                        highList.add((Integer) resultMap.get("High"));
                        mediumList.add((Integer) resultMap.get("Medium"));
                        lowList.add((Integer) resultMap.get("Low"));
                    }
                }
            }
            finalList.add(hotsName);
            finalList.add(critacalList);
            finalList.add(highList);
            finalList.add(mediumList);
            finalList.add(lowList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : topHosts():" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "topHosts() :: " + e.getMessage());
        }
        return finalList;
    }

    @Override
    public List<AssessmentModel> vulnerabilitiesbyFrequency(AssessmentModel assessmentModel) throws AppException {
        List<AssessmentModel> list = new ArrayList<AssessmentModel>();
        try {
            String procName = "{CALL Report_TopVulnerabilities(?,?,?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{assessmentModel.getEngagementCode(), assessmentModel.getServiceCode(), assessmentModel.getAssessmentFlag(), assessmentModel.getSelectedString(), assessmentModel.getOrgSchema()});
            if (null != resultList && resultList.size() > 0) {
                for (Map<String, Object> resultMap : resultList) {
                    if (resultMap.get("VULN_NM") != null && !((String) resultMap.get("VULN_NM")).equalsIgnoreCase("")) {
                        AssessmentModel model = new AssessmentModel();
                        model.setLabel((String) resultMap.get("VULN_NM"));
                        model.setValue(resultMap.get("VULCOUNT") + "");
                        list.add(model);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : vulnerabilitiesbyFrequency():" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "vulnerabilitiesbyFrequency() :: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<AssessmentModel> ssidDetails(AssessmentModel assessmentObj) throws Exception {
        List<AssessmentModel> ssidList = new ArrayList<AssessmentModel>();
        try {
            String procName = "{CALL Report_TopSSID(?,?,?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{assessmentObj.getEngagementCode(), assessmentObj.getServiceCode(), assessmentObj.getAssessmentFlag(), assessmentObj.getSelectedString(), assessmentObj.getOrgSchema()});
            if (null != resultList && resultList.size() > 0) {
                for (Map<String, Object> resultMap : resultList) {
                    if (resultMap.get("NETBIOS_NM") != null && (!((String) resultMap.get("NETBIOS_NM")).equalsIgnoreCase(""))) {
                        AssessmentModel model = new AssessmentModel();
                        model.setLabel((String) resultMap.get("NETBIOS_NM"));
                        model.setValue(resultMap.get("VULCOUNT") + "");
                        ssidList.add(model);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : ssidDetails():" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "ssidDetails() :: " + e.getMessage());
        }
        return ssidList;
    }

    @Override
    public SecurityModel ssidDetailsList(AssessmentModel assessmentObj) throws Exception {
        List<AssessmentModel> labelList = new ArrayList<AssessmentModel>();
        List<AssessmentModel> valueList = new ArrayList<AssessmentModel>();
        SecurityModel securityModel = new SecurityModel();
        try {
            String procName = "{CALL Report_TopSSID(?,?,?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{assessmentObj.getEngagementCode(), assessmentObj.getServiceCode(), assessmentObj.getAssessmentFlag(), assessmentObj.getSelectedString(), assessmentObj.getOrgSchema()});
            if (null != resultList && resultList.size() > 0) {
                for (Map<String, Object> resultMap : resultList) {
                    if (resultMap.get("NETBIOS_NM") != null && (!((String) resultMap.get("NETBIOS_NM")).equalsIgnoreCase(""))) {
                        AssessmentModel labelModel = new AssessmentModel();
                        labelModel.setLabel((String) resultMap.get("NETBIOS_NM"));

                        AssessmentModel valueModel = new AssessmentModel();
                        valueModel.setValue(resultMap.get("VULCOUNT") + "");
                        labelList.add(labelModel);
                        valueList.add(valueModel);
                    }
                }
            }
            securityModel.setCategoryLabelList(labelList);
            securityModel.setCategoryDataList(valueList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : ssidDetailsList():" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "ssidDetailsList() :: " + e.getMessage());
        }
        return securityModel;
    }

    @Override
    public List<FindingsModel> getEngagementServiceFindingsData(AssessmentModel assessmentObj) throws AppException {
        List<FindingsModel> findingList = null;
        try {
            String procName = "{CALL Report_AppFindingDetail(?,?,?,?,?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{assessmentObj.getAssessmentFlag(), assessmentObj.getEngagementCode(), assessmentObj.getServiceCode(), "", "", assessmentObj.getSelectedString(), assessmentObj.getOrgSchema()});
            if (null != resultList && resultList.size() > 0) {
                findingList = new ArrayList<>(resultList.size());
                for (Map<String, Object> resultMap : resultList) {
                    FindingsModel findingsModel = new FindingsModel();
                    findingsModel.setId(CommonUtil.replaceNullWithEmpty(resultMap.get("CLNT_VULN_INSTC_KEY")));
                    findingsModel.setFinding(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_NM")));
                    findingsModel.setRisklevel(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_SEV_NM")));
                    findingsModel.setRecommendation(CommonUtil.removeHtmlTagsFromString(resultMap.get("RECOM_COMMT_TXT")));
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
                    findingsModel.setRootCauseDetail(CommonUtil.replaceCommaByCommaAndSpace(resultMap.get("VULN_CATGY_NM")));
                    findingList.add(findingsModel);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : getEngagementServiceFindingsData():" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "getEngagementServiceFindingsData() :: " + e.getMessage());
        }
        return findingList;
    }

    @Override
    public List<AssessmentModel> topHitrustDetails(String engagementCode, String serviceCode, String orgSchema) throws AppException {
        List<AssessmentModel> ssidList = new ArrayList<AssessmentModel>();
        try {
            String procName = "{CALL Report_TopHitrust (?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{engagementCode, serviceCode, orgSchema});
            if (null != resultList && resultList.size() > 0) {
                for (Map<String, Object> resultMap : resultList) {
                    if (resultMap.get("SECUR_OBJ_NM") != null && !((String) resultMap.get("SECUR_OBJ_NM")).equalsIgnoreCase("")) {
                        AssessmentModel model = new AssessmentModel();
                        model.setLabel((String) resultMap.get("SECUR_OBJ_NM"));
                        model.setValue(resultMap.get("FindingCount") + "");
                        ssidList.add(model);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : topHitrustDetails():" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "topHitrustDetails() :: " + e.getMessage());
        }
        return ssidList;
    }

    @Override
    public SecurityModel hitrustDetailsList(String engagementCode, String serviceCode, String orgSchema) throws AppException {
        List<AssessmentModel> labelList = new ArrayList<AssessmentModel>();
        List<AssessmentModel> valueList = new ArrayList<AssessmentModel>();
        SecurityModel securityModel = new SecurityModel();
        try {
            String procName = "{CALL Report_TopHitrust (?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{engagementCode, serviceCode, orgSchema});
            if (null != resultList && resultList.size() > 0) {
                for (Map<String, Object> resultMap : resultList) {
                    if (resultMap.get("SECUR_OBJ_NM") != null && !((String) resultMap.get("SECUR_OBJ_NM")).equalsIgnoreCase("")) {
                        AssessmentModel labelModel = new AssessmentModel();
                        labelModel.setLabel((String) resultMap.get("SECUR_OBJ_NM"));

                        AssessmentModel valueModel = new AssessmentModel();
                        valueModel.setValue(resultMap.get("FindingCount") + "");

                        labelList.add(labelModel);
                        valueList.add(valueModel);
                    }
                }
            }
            securityModel.setCategoryLabelList(labelList);
            securityModel.setCategoryDataList(valueList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : topHitrustDetails():" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "topHitrustDetails() :: " + e.getMessage());
        }
        return securityModel;
    }

    @Override
    public List<AssessmentModel> applicationDetails(AssessmentModel assessmentObj) throws AppException {

        List<AssessmentModel> list = new ArrayList<AssessmentModel>();
        try {
            String procName = "{CALL Report_TopApplications(?,?,?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{assessmentObj.getEngagementCode(), assessmentObj.getServiceCode(), assessmentObj.getAssessmentFlag(), assessmentObj.getSelectedString(), assessmentObj.getOrgSchema()});
            if (null != resultList && resultList.size() > 0) {
                for (Map<String, Object> resultMap : resultList) {
                    if (resultMap.get("SFTW_NM") != null && !((String) resultMap.get("SFTW_NM")).equalsIgnoreCase("")) {
                        AssessmentModel model = new AssessmentModel();
                        model.setLabel((String) resultMap.get("SFTW_NM"));
                        model.setValue(resultMap.get("VULCOUNT") + "");
                        list.add(model);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : applicationDetails():" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "applicationDetails() :: " + e.getMessage());
        }
        return list;
    }

    @Override
    public SecurityModel applicationDetailsList(AssessmentModel assessmentObj) throws AppException {

        List<AssessmentModel> labelList = new ArrayList<AssessmentModel>();
        List<AssessmentModel> valueList = new ArrayList<AssessmentModel>();
        SecurityModel securityModel = new SecurityModel();
        try {
            String procName = "{CALL Report_TopApplications(?,?,?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{assessmentObj.getEngagementCode(), assessmentObj.getServiceCode(), assessmentObj.getAssessmentFlag(), assessmentObj.getSelectedString(), assessmentObj.getOrgSchema()});
            if (null != resultList && resultList.size() > 0) {
                for (Map<String, Object> resultMap : resultList) {
                    if (resultMap.get("SFTW_NM") != null && !((String) resultMap.get("SFTW_NM")).equalsIgnoreCase("")) {
                        AssessmentModel labelModel = new AssessmentModel();
                        labelModel.setLabel((String) resultMap.get("SFTW_NM"));

                        AssessmentModel valueModel = new AssessmentModel();
                        valueModel.setValue(resultMap.get("VULCOUNT") + "");
                        labelList.add(labelModel);
                        valueList.add(valueModel);
                    }
                }
            }

            securityModel.setCategoryLabelList(labelList);
            securityModel.setCategoryDataList(valueList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : applicationDetails():" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "applicationDetails() :: " + e.getMessage());
        }
        return securityModel;
    }

    @Override
    public List<SummaryDropDownModel> ssidDropDownDetails(String engagementCode, String serviceCode, String orgSchema) throws Exception {
        List<SummaryDropDownModel> dropDownList = new ArrayList<SummaryDropDownModel>();
        try {
            String procName = "{CALL Report_TopSSID(?,?,?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{engagementCode, serviceCode, "AL", "", orgSchema});
            if (null != resultList && resultList.size() > 0) {
                for (Map<String, Object> resultMap : resultList) {
                    if (resultMap.get("NETBIOS_NM") != null && (!((String) resultMap.get("NETBIOS_NM")).equalsIgnoreCase(""))) {
                        SummaryDropDownModel model = new SummaryDropDownModel();
                        model.setId((String) resultMap.get("NETBIOS_NM"));
                        model.setName((String) resultMap.get("NETBIOS_NM"));
                        model.setMaker(ReportsAndDashboardConstants.APP_SSID);
                        dropDownList.add(model);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : ssidDropDownDetails():" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "ssidDropDownDetails() :: " + e.getMessage());
        }
        return dropDownList;
    }

    @Override
    public List<SummaryDropDownModel> applicatonDropDownDetails(String engagementCode, String serviceCode, String orgSchema) throws Exception {
        List<SummaryDropDownModel> dropDownList = new ArrayList<SummaryDropDownModel>();
        try {
            String procName = "{CALL Report_TopApplications(?,?,?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{engagementCode, serviceCode, "AL", "", orgSchema});
            if (null != resultList && resultList.size() > 0) {
                for (Map<String, Object> resultMap : resultList) {
                    if (resultMap.get("SFTW_NM") != null && !((String) resultMap.get("SFTW_NM")).equalsIgnoreCase("")) {
                        SummaryDropDownModel model = new SummaryDropDownModel();
                        model.setId((String) resultMap.get("SFTW_NM"));
                        model.setName((String) resultMap.get("SFTW_NM"));
                        model.setMaker(ReportsAndDashboardConstants.APP_SSID);
                        dropDownList.add(model);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : applicatonDropDownDetails():" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "applicatonDropDownDetails() :: " + e.getMessage());
        }
        return dropDownList;
    }

    @Override
    public List applicationMostVulnerable(AssessmentModel assessmentObj) throws AppException {
        List<AssessmentModel> osList = new ArrayList<AssessmentModel>();

        List finalList = new ArrayList();
        try {
            String procName = "{CALL Report_TopApplicationSeverity(?,?,?,?,?)}";
            List<String> hotsName = new ArrayList<String>();
            List<Integer> critacalList = new ArrayList<Integer>();
            List<Integer> highList = new ArrayList<Integer>();
            List<Integer> mediumList = new ArrayList<Integer>();
            List<Integer> lowList = new ArrayList<Integer>();

            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{assessmentObj.getEngagementCode(), assessmentObj.getServiceCode(), assessmentObj.getAssessmentFlag(), assessmentObj.getSelectedString(), assessmentObj.getOrgSchema()});
            if (null != resultList && resultList.size() > 0) {
                for (Map<String, Object> resultMap : resultList) {
                    if (resultMap.get("SFTW_NM") != null && !((String) resultMap.get("SFTW_NM")).equalsIgnoreCase("")) {
                        hotsName.add((String) resultMap.get("SFTW_NM"));
                        critacalList.add((Integer) resultMap.get("Critical"));
                        highList.add((Integer) resultMap.get("High"));
                        mediumList.add((Integer) resultMap.get("Medium"));
                        lowList.add((Integer) resultMap.get("Low"));
                    }
                }
            }
            finalList.add(hotsName);
            finalList.add(critacalList);
            finalList.add(highList);
            finalList.add(mediumList);
            finalList.add(lowList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : applicationMostVulnerable():" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "applicationMostVulnerable() :: " + e.getMessage());
        }
        return finalList;
    }

    @Override
    public List getServiceFindingsDataForExportCSV(AssessmentModel assessmentObj, SecurityModel securityModel) throws AppException {
//      logger.info("Start: AssessmentDAOImpl: getServiceFindingsDataForExportCSV() to get Engagement Findings Data");
        logger.info(INFO_USER_ACCESSED_SERVICE_FINDIMGS_FOR_EXPORTCSV);

        String procName = "{CALL Report_GetFindingsCSV(?,?,?,?,?,?,?)}";
        List dbhlist = securityModel.getCsvDBHeaders();
        List csvlist = securityModel.getCsvHeaders();
        List finalList = new ArrayList();
        finalList.add(csvlist);
        try {

            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{assessmentObj.getEngagementCode(), assessmentObj.getServiceCode(), "", "", assessmentObj.getAssessmentFlag(), assessmentObj.getSelectedString(),securityModel.getEngSchemaName()});
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
            logger.debug("Exception occured : AssessmentDAOImpl: getServiceFindingsDataForExportCSV(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "AssessmentDAOImpl: getServiceFindingsDataForExportCSV(): " + e.getMessage());
        }
        logger.info("End: AssessmentDAOImpl: getServiceFindingsDataForExportCSV() to get Engagement Findings Data");
        return finalList;
    }

    @Override
    public List<AssessmentModel> remediationPriorities(AssessmentModel assessmentObj) throws AppException {

        List<AssessmentModel> list = new ArrayList<AssessmentModel>();
        try {
            String procName = "{CALL Report_RemediationPriority(?,?,?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{assessmentObj.getEngagementCode(), assessmentObj.getServiceCode(), assessmentObj.getAssessmentFlag(), assessmentObj.getSelectedString(), assessmentObj.getOrgSchema()});
            if (null != resultList && resultList.size() > 0) {
                for (Map<String, Object> resultMap : resultList) {
                    AssessmentModel model = new AssessmentModel();
                    model.setLabel((String) resultMap.get("VULN_NM"));
                    model.setValue(CommonUtil.replaceNullWithEmpty(resultMap.get("VULCOUNT")));
                    model.setTotalCount((Integer) resultMap.get("FindingsCount"));
                    list.add(model);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : remediationPriorities():" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "remediationPriorities() :: " + e.getMessage());
        }
        return list;
    }
    
    
    public SecurityModel remediationPrioritiesList(AssessmentModel assessmentObj) throws AppException {

        List<AssessmentModel> labelList = new ArrayList<AssessmentModel>();
        List<AssessmentModel> valueList = new ArrayList<AssessmentModel>();
        List<AssessmentModel> percentageCountList = new ArrayList<AssessmentModel>();
        SecurityModel securityModel = new SecurityModel();
        int countSum=0;
        try {
            String procName = "{CALL Report_RemediationPriority(?,?,?,?,?)}";
           List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{assessmentObj.getEngagementCode(), assessmentObj.getServiceCode(), assessmentObj.getAssessmentFlag(), assessmentObj.getSelectedString(), assessmentObj.getOrgSchema()});
            if (null != resultList && resultList.size() > 0) {
                for (Map<String, Object> resultMap : resultList) {
                        AssessmentModel labelModel = new AssessmentModel();
                        labelModel.setLabel((String) resultMap.get("VULN_NM"));

                        AssessmentModel valueModel = new AssessmentModel();
                        valueModel.setValue(resultMap.get("VULCOUNT") + "");
                        
                       
                         
                        int totalCount = ((Integer) resultMap.get("FindingsCount"));
                        int value = (Integer) resultMap.get("VULCOUNT");
                        countSum = countSum + value;
                        double percentage = CommonUtil.percentageCount(countSum, totalCount);
                        AssessmentModel perCount = new AssessmentModel();
                        perCount.setValue(percentage + "");
                         
                        labelList.add(labelModel);
                        valueList.add(valueModel);
                        percentageCountList.add(perCount);
                        
                }
            }

            securityModel.setCategoryLabelList(labelList);
            securityModel.setCategoryDataList(valueList);
            securityModel.setPercentageCountList(percentageCountList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : remediationPrioritiesList():" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "remediationPrioritiesList() :: " + e.getMessage());
        }
        return securityModel;
    }
    
    @Override
    public List<AssessmentModel> owaspChartData(AssessmentModel assessmentObj) throws Exception {
        List<AssessmentModel> rootList = new ArrayList<AssessmentModel>();
        try {
            String procName = "{CALL Report_TopOWASP(?,?,?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{assessmentObj.getEngagementCode(), assessmentObj.getServiceCode(), assessmentObj.getAssessmentFlag(), assessmentObj.getSelectedString(), assessmentObj.getOrgSchema()});
            if (null != resultList && resultList.size() > 0) {
                for (Map<String, Object> resultMap : resultList) {
                    if (resultMap.get("OWASP_NM") != null && !((String) resultMap.get("OWASP_NM")).equalsIgnoreCase("")) {
                        AssessmentModel model = new AssessmentModel();
                        model.setLabel((String) resultMap.get("OWASP_NM"));
                        model.setValue(resultMap.get("OWASPCOUNT") + "");
                        rootList.add(model);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : owaspChartData():" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "owaspChartData() :: " + e.getMessage());
        }
        return rootList;
    }
    
    @Override
    public SecurityModel owaspViewList(AssessmentModel assessmentObj) throws Exception {
        List<AssessmentModel> labelList = new ArrayList<AssessmentModel>();
        List<AssessmentModel> valueList = new ArrayList<AssessmentModel>();
        SecurityModel securityModel = new SecurityModel();
        try {
            String procName = "{CALL Report_TopOWASP(?,?,?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{assessmentObj.getEngagementCode(), assessmentObj.getServiceCode(), assessmentObj.getAssessmentFlag(), assessmentObj.getSelectedString(), assessmentObj.getOrgSchema()});
            if (null != resultList && resultList.size() > 0) {
                for (Map<String, Object> resultMap : resultList) {
                    if (resultMap.get("OWASP_NM") != null && !((String) resultMap.get("OWASP_NM")).equalsIgnoreCase("")) {
                        AssessmentModel labelModel = new AssessmentModel();
                        labelModel.setLabel((String) resultMap.get("OWASP_NM"));

                        AssessmentModel valueModel = new AssessmentModel();
                        valueModel.setValue(resultMap.get("OWASPCOUNT") + "");

                        labelList.add(labelModel);
                        valueList.add(valueModel);
                    }
                }
            }
            securityModel.setCategoryLabelList(labelList);
            securityModel.setCategoryDataList(valueList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : topRootCauses():" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "topRootCauses() :: " + e.getMessage());
        }
        return securityModel;
    }
}
