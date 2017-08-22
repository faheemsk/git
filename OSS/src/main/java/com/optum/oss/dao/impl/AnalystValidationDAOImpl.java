/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao.impl;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dao.AnalystValidationDAO;
import com.optum.oss.dto.CVEInformationDTO;
import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.dto.ComplianceInfoDTO;
import com.optum.oss.dto.ManageServiceUserDTO;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.OwaspDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.dto.SecurityServiceDTO;
import com.optum.oss.dto.VulnerabilityDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.util.CommonUtil;
import com.optum.oss.util.DateUtil;
import com.optum.oss.util.EncryptDecrypt;
import com.sun.rowset.CachedRowSetImpl;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.sql.rowset.CachedRowSet;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author sbhagavatula
 */
public class AnalystValidationDAOImpl implements AnalystValidationDAO {

    private static final Logger logger = Logger.getLogger(AnalystValidationDAOImpl.class);

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

    /*
     Start : Autowiring of Fields
     */
    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private EncryptDecrypt encDecrypt;
    /*
     End : Autowiring of Fields
     */

    /**
     * This method for fetch Analyst Validation work list
     *
     * @param userDto
     * @param pvcFlag
     * @param manageServiceDto
     * @return List<ManageServiceUserDTO>
     * @throws AppException
     */
    @Override
    public List<ManageServiceUserDTO> fetchAnalystValidationWorklist(final UserProfileDTO userDto, final String pvcFlag,
            final ManageServiceUserDTO manageServiceDto) throws AppException {
        logger.info("Start: AnalystValidationDAOImpl: fetchAnalystValidationWorklist() to fetch Analyst Validation worklist");
        String analystWorklistProc = "{CALL Analyst_GetEngmtAnalystWorkList(?,?,?,?,?,?,?,?,?,?,?)}";
        String lFlag = pvcFlag;
        int count = 1;
        if (pvcFlag.equalsIgnoreCase(ApplicationConstants.DB_CONSTANT_ENG_LEAD_ENG_ANALYST)) {
            count = 2;
        }
        List<String> dupServList = new ArrayList<>();
        List<ManageServiceUserDTO> serviceLists = new ArrayList<>();
        try {
            for (int i = 0; i < count; i++) {
                if (count == 2) {
                    if (i == 0) {
                        lFlag = ApplicationConstants.DB_CONSTANT_ENGAGEMENT_LEAD;
                    } else if (i == 1) {
                        lFlag = ApplicationConstants.DB_CONSTANT_USER_TYPE_ANALYST;
                    }
                }
                List<Map<String, Object>> resultList = jdbcTemplate.queryForList(analystWorklistProc, new Object[]{
                    userDto.getUserProfileKey(),
                    manageServiceDto.getOrganizationName(), //organization name
                    manageServiceDto.getClientEngagementCode(), //client engagement code
                    manageServiceDto.getEngagementName(), //client engagement name
                    manageServiceDto.getServiceName(), //secure service name
                    manageServiceDto.getStartDate(), //start date
                    manageServiceDto.getEndDate(), //end date
                    userDto.getUserTypeKey(),
                    manageServiceDto.getModifiedDate(),
                    manageServiceDto.getRowStatusValue(),
                    lFlag //Analyst or Engagement Lead
                });

                if (!resultList.isEmpty()) {
                    for (Map<String, Object> resultMap : resultList) {
                        ManageServiceUserDTO manageServiceUserDTO = new ManageServiceUserDTO();
                        manageServiceUserDTO.setOrganizationName(resultMap.get("Client NAME") + "");
                        manageServiceUserDTO.setOrgSchema(resultMap.get("ORG_SCHM").toString());
                        manageServiceUserDTO.setOrganizationKey((Integer) resultMap.get("CLNT_ORG_KEY"));
                        manageServiceUserDTO.setClientEngagementCode(resultMap.get("CLNT_ENGMT_CD") + "");
                        manageServiceUserDTO.setEncClientEngagementCode(encDecrypt.encrypt(manageServiceUserDTO.getClientEngagementCode()));
                        manageServiceUserDTO.setEngagementName(resultMap.get("CLNT_ENGMT_NM") + "");
                        manageServiceUserDTO.setServiceName(resultMap.get("SECUR_SRVC_NM") + "");
                        manageServiceUserDTO.setSecureServiceCode(resultMap.get("SECUR_SRVC_CD") + "");
                        manageServiceUserDTO.setFileLockIndicator((Integer) resultMap.get("FL_LCK_IND"));
                        manageServiceUserDTO.setStartDate(dateUtil.dateConvertionFromDBToJSP(resultMap.get("Start Date") + ""));
                        manageServiceUserDTO.setEndDate(dateUtil.dateConvertionFromDBToJSP(resultMap.get("End Date") + ""));
                        manageServiceUserDTO.setRowStatusKey((Integer) resultMap.get("SRVC_ENGMT_STS_KEY"));
                        if (!(resultMap.get("UpdatedDate") + "").isEmpty() && !(resultMap.get("UpdatedDate") + "").equalsIgnoreCase("null")) {
                            manageServiceUserDTO.setModifiedDate(dateUtil.dateConvertionFromDBToJSP(resultMap.get("UpdatedDate") + ""));
                        }
                        manageServiceUserDTO.setRowStatusValue(resultMap.get("ReviewStatus") + "");

                        if (i == 1) {
                            if (!dupServList.contains(resultMap.get("CLNT_ENGMT_CD") + "" + resultMap.get("SECUR_SRVC_CD"))) {
                                serviceLists.add(manageServiceUserDTO);
                            }
                        } else {
                            serviceLists.add(manageServiceUserDTO);
                        }
                        dupServList.add(resultMap.get("CLNT_ENGMT_CD") + "" + resultMap.get("SECUR_SRVC_CD"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : AnalystValidationDAOImpl: fetchAnalystValidationWorklist(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "AnalystValidationDAOImpl: fetchAnalystValidationWorklist(): " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl: fetchAnalystValidationWorklist() to fetch Analyst Validation worklist");
        return serviceLists;
    }

    /**
     *
     * @param vulnerabilityDTO
     * @return
     * @throws AppException
     */
    @Override
    public List<VulnerabilityDTO> vulnerabilityList(VulnerabilityDTO vulnDTO) throws AppException {
        logger.info("Start: AnalystValidationDAOImpl : vulnerabilityList");
        List<VulnerabilityDTO> vulnerabilityList = null;
        try {
            vulnerabilityList = new ArrayList<>();
            int totalCount = 0;
            int notReviewedCount = 0; //Status as 'Open'
            String vulnerabilityProc = "{CALL Analyst_ListVulnerability(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

            List<CachedRowSet> rsSet = getCachedVulnerabilities(vulnerabilityProc, vulnDTO);

            int count = 1;
            Iterator rsItr = rsSet.iterator();
            while (rsItr.hasNext()) {
                CachedRowSet rowset = (CachedRowSet) rsItr.next();
                if (!rowset.wasNull()) {
                    ResultSet rs = rowset.getOriginal();
                    if (count == 1) {
                        while (rs.next()) {
                            totalCount = rs.getInt("TotalCount");
                        }
                    }

                    if (count == 2) {
                        while (rs.next()) {
                            notReviewedCount = rs.getInt("OpenCount");
                        }
                    }

                    if (count == 3) {
                        while (rs.next()) {
                            VulnerabilityDTO vulnerabilityDTO = new VulnerabilityDTO();
                            vulnerabilityDTO.setInstanceIdentifier(rs.getString("CLNT_VULN_INSTC_KEY") + "");
                            vulnerabilityDTO.setEncInstanceIdentifier(encDecrypt.encrypt(rs.getString("CLNT_VULN_INSTC_KEY") + ""));
                            vulnerabilityDTO.setVulnerabilityName((String) rs.getString("VULN_NM"));
                            vulnerabilityDTO.setSourceName(rs.getString("VULN_SRC") + "");
                            
                            if (rs.getString("SCAN_TL_NM") == null) {
                                vulnerabilityDTO.setScanTool("");
                            } else {
                                vulnerabilityDTO.setScanTool(rs.getString("SCAN_TL_NM") + "");
                            }

                            SecurityServiceDTO serviceObj = new SecurityServiceDTO();
                            serviceObj.setSecurityServiceName((String) rs.getString("SECUR_SRVC_NM"));

                            vulnerabilityDTO.setSecurityServiceObj(serviceObj);
                            vulnerabilityDTO.setIpAddress((String) rs.getString("IPADR"));

                            CVEInformationDTO cveInformationDTO = new CVEInformationDTO();
                            cveInformationDTO.setSeverityName((String) rs.getString("VULN_SEV_NM"));

                            vulnerabilityDTO.setCveInformationDTO(cveInformationDTO);
                            vulnerabilityDTO.setStatusName((String) rs.getString("VULN_INSTC_STS_NM"));
                            vulnerabilityDTO.setStatusCode((String) rs.getString("VULN_INSTC_STS_CD"));
                            vulnerabilityDTO.setRowStatusValue((String) rs.getString("Service Status"));
                            vulnerabilityDTO.setSoftwareName((String) rs.getString("SFTW_NM"));
                            vulnerabilityDTO.setVulCount(totalCount);
                            vulnerabilityDTO.setNotReviewedCount(notReviewedCount);
                            vulnerabilityList.add(vulnerabilityDTO);
                        }
                    }

                }
                count++;
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : vulnerabilityList : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "vulnerabilityList() :: " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl : vulnerabilityList");
        return vulnerabilityList;
    }

    /**
     *
     * @param engagementCode
     * @return
     * @throws AppException
     */
    @Override
    public List<SecurityServiceDTO> analystEngagementServices(String engagementCode) throws AppException {
        logger.info("Start: AnalystValidationDAOImpl : analystEngagementServices");
        List<SecurityServiceDTO> servicesList = null;
        try {
            String servicesProc = "{CALL Analyst_ListServicesByEngmt(?)}";
            List<Map<String, Object>> servicesMap = jdbcTemplate.queryForList(servicesProc,
                    new Object[]{engagementCode});
            servicesList = new ArrayList<>(servicesMap.size());

            for (Map<String, Object> service : servicesMap) {
                SecurityServiceDTO securityServiceDTO = new SecurityServiceDTO();
                securityServiceDTO.setSecurityServiceCode((String) service.get("SECUR_SRVC_CD"));
                securityServiceDTO.setSecurityServiceName((String) service.get("SECUR_SRVC_NM"));
                securityServiceDTO.setReviewStatus((String) service.get("ReviewStatus"));
                securityServiceDTO.setCreatedByUser((String) service.get("UserName"));
                securityServiceDTO.setCreatedDate(dateUtil.dateConvertionFromDBToJSP(service.get("UPDT_DT") + ""));
                servicesList.add(securityServiceDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : analystEngagementServices : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "analystEngagementServices() :: " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl : analystEngagementServices");
        return servicesList;
    }

    /**
     * This method for delete vulnerability based on vulnerability instance
     *
     * @param vulnerabilityList
     * @return int
     * @throws AppException
     */
    @Override
    public int deleteVulnerability(final List<VulnerabilityDTO> vulnerabilityList, long userId) throws AppException {
        logger.info("Start: AnalystValidationDAOImpl : deleteVulnerability");
        int returnVal = 0, deletedCount = 0;
        try {
            String deleteVulnerabilityProc = "{CALL Analyst_UpdateRowstatusByVulKey(?,?,?)}";
            if (!vulnerabilityList.isEmpty()) {
                for (VulnerabilityDTO dto : vulnerabilityList) {
                    returnVal = jdbcTemplate.queryForObject(deleteVulnerabilityProc,
                            new Object[]{
                                Integer.parseInt(encDecrypt.decrypt(dto.getInstanceIdentifier())), userId, dto.getOrgSchema()
                            }, Integer.class);
                    deletedCount++;
                }
            }
            logger.info("Number of Vulnerabilities deleted: " + deletedCount);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : deleteVulnerability: " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "deleteVulnerability(): " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl : deleteVulnerability");
        return returnVal;
    }

    /**
     * Used in review vulnerability - Vulnerability Information for Status drop
     * down values
     *
     * @param flag
     * @return
     * @throws AppException
     */
    @Override
    public List<MasterLookUpDTO> analystStatusList(String flag) throws AppException {
        logger.info("Start: AnalystValidationDAOImpl : analystStatusList");
        List<MasterLookUpDTO> statusList = null;
        try {
            String statusProc = "{CALL Analyst_ListStatus(?)}";
            List<Map<String, Object>> statusMap = jdbcTemplate.queryForList(statusProc,
                    new Object[]{flag});
            statusList = new ArrayList<>(statusMap.size());

            for (Map<String, Object> status : statusMap) {
                MasterLookUpDTO masterLookUpDTO = new MasterLookUpDTO();
                masterLookUpDTO.setMasterLookupCode((String) status.get("VULN_INSTC_STS_CD"));
                masterLookUpDTO.setLookUpEntityName((String) status.get("VULN_INSTC_STS_NM"));
                statusList.add(masterLookUpDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : analystStatusList : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "analystStatusList() :: " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl : analystStatusList");
        return statusList;
    }

    public List<MasterLookUpDTO> analystStatusConditionalList(String statusCode) throws AppException {
        logger.info("Start: AnalystValidationDAOImpl : analystStatusList");
        List<MasterLookUpDTO> statusList = null;
        try {
            String statusProc = "{CALL Analyst_ListStatus(?)}";
            List<Map<String, Object>> statusMap = jdbcTemplate.queryForList(statusProc,
                    new Object[]{ApplicationConstants.STATUS_VULNERABILITY});
            statusList = new ArrayList<>(statusMap.size());

            for (Map<String, Object> status : statusMap) {
                String code = (String) status.get("VULN_INSTC_STS_CD");
                MasterLookUpDTO masterLookUpDTO = new MasterLookUpDTO();
                masterLookUpDTO.setMasterLookupCode((String) status.get("VULN_INSTC_STS_CD"));
                masterLookUpDTO.setLookUpEntityName((String) status.get("VULN_INSTC_STS_NM"));

                if (statusCode.equalsIgnoreCase(ApplicationConstants.DB_CONSTANT_FINDING_OPEN_STATUS_CODE)) {
                    if (code.equalsIgnoreCase(ApplicationConstants.DB_CONSTANT_FINDING_FALSE_POSITIVE_STATUS_CODE) || code.equalsIgnoreCase(ApplicationConstants.DB_CONSTANT_FINDING_DUPLICATE_STATUS_CODE)
                            || code.equalsIgnoreCase(ApplicationConstants.DB_CONSTANT_FINDING_VALIDATE_STATUS_CODE) || code.equalsIgnoreCase(ApplicationConstants.DB_CONSTANT_FINDING_OPEN_STATUS_CODE)) {
                        statusList.add(masterLookUpDTO);
                    }
                } else if (statusCode.equalsIgnoreCase(ApplicationConstants.DB_CONSTANT_FINDING_VALIDATE_STATUS_CODE)) {

                    if (code.equalsIgnoreCase(ApplicationConstants.DB_CONSTANT_FINDING_FALSE_POSITIVE_STATUS_CODE) || code.equalsIgnoreCase(ApplicationConstants.DB_CONSTANT_FINDING_CLOSED_STATUS_CODE)
                            || code.equalsIgnoreCase(ApplicationConstants.DB_CONSTANT_FINDING_CLOSED_WITH_EXCEPTION_STATUS_CODE) || code.equalsIgnoreCase(ApplicationConstants.DB_CONSTANT_FINDING_VALIDATE_STATUS_CODE)) {
                        statusList.add(masterLookUpDTO);
                    }
                } else if (statusCode.equalsIgnoreCase(ApplicationConstants.DB_CONSTANT_FINDING_CLOSED_WITH_EXCEPTION_STATUS_CODE)) {
                    if (code.equalsIgnoreCase(ApplicationConstants.DB_CONSTANT_FINDING_VALIDATE_STATUS_CODE) || code.equalsIgnoreCase(ApplicationConstants.DB_CONSTANT_FINDING_CLOSED_WITH_EXCEPTION_STATUS_CODE)) {
                        statusList.add(masterLookUpDTO);
                    }
                } else if (statusCode.equalsIgnoreCase(ApplicationConstants.DB_CONSTANT_FINDING_CLOSED_STATUS_CODE)) {
                    if (code.equalsIgnoreCase(ApplicationConstants.DB_CONSTANT_FINDING_VALIDATE_STATUS_CODE) || code.equalsIgnoreCase(ApplicationConstants.DB_CONSTANT_FINDING_CLOSED_STATUS_CODE)) {
                        statusList.add(masterLookUpDTO);
                    }
                } else if (statusCode.equalsIgnoreCase(ApplicationConstants.DB_CONSTANT_FINDING_DUPLICATE_STATUS_CODE)) {

                    if (code.equalsIgnoreCase(ApplicationConstants.DB_CONSTANT_FINDING_FALSE_POSITIVE_STATUS_CODE) || code.equalsIgnoreCase(ApplicationConstants.DB_CONSTANT_FINDING_OPEN_STATUS_CODE)
                            || code.equalsIgnoreCase(ApplicationConstants.DB_CONSTANT_FINDING_VALIDATE_STATUS_CODE) || code.equalsIgnoreCase(ApplicationConstants.DB_CONSTANT_FINDING_DUPLICATE_STATUS_CODE)) {
                        statusList.add(masterLookUpDTO);
                    }
                } else if (statusCode.equalsIgnoreCase(ApplicationConstants.DB_CONSTANT_FINDING_FALSE_POSITIVE_STATUS_CODE)) {

                    if (code.equalsIgnoreCase(ApplicationConstants.DB_CONSTANT_FINDING_DUPLICATE_STATUS_CODE) || code.equalsIgnoreCase(ApplicationConstants.DB_CONSTANT_FINDING_OPEN_STATUS_CODE)
                            || code.equalsIgnoreCase(ApplicationConstants.DB_CONSTANT_FINDING_VALIDATE_STATUS_CODE) || code.equalsIgnoreCase(ApplicationConstants.DB_CONSTANT_FINDING_FALSE_POSITIVE_STATUS_CODE)) {
                        statusList.add(masterLookUpDTO);
                    }
                } else {
                    statusList.add(masterLookUpDTO);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : analystStatusList : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "analystStatusList() :: " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl : analystStatusList");
        return statusList;
    }

    /**
     * Used in review vulnerability RISK Information for Root Cause Effort drop
     * down values
     *
     * @return
     * @throws AppException
     */
    @Override
    public List<MasterLookUpDTO> analystCostEffortList() throws AppException {
        logger.info("Start: AnalystValidationDAOImpl : analystCostEffortList");
        List<MasterLookUpDTO> costEffortList = null;
        try {
            String costEffortProc = "{CALL Analyst_ListCostEffort()}";
            List<Map<String, Object>> costEffortMap = jdbcTemplate.queryForList(costEffortProc);
            costEffortList = new ArrayList<>(costEffortMap.size());

            for (Map<String, Object> costEffort : costEffortMap) {
                MasterLookUpDTO masterLookUpDTO = new MasterLookUpDTO();
                masterLookUpDTO.setMasterLookupCode((String) costEffort.get("RMDTN_CST_EFFRT_CD"));
                masterLookUpDTO.setLookUpEntityName((String) costEffort.get("RMDTN_CST_EFFRT_NM"));
                costEffortList.add(masterLookUpDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : analystCostEffortList : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "analystCostEffortList() :: " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl : analystCostEffortList");
        return costEffortList;
    }

    /**
     * Used in review vulnerability - Vulnerability Information for Root Cause
     * drop down values
     *
     * @return
     * @throws AppException
     */
    @Override
    public List<MasterLookUpDTO> vulnerabilityCategoryList() throws AppException {
        logger.info("Start: AnalystValidationDAOImpl : vulnerabilityCategoryList");
        List<MasterLookUpDTO> vulCatList = null;
        try {
            String rootCauseProc = "{CALL Analyst_ListVULNCATGY(?,?)}";
            List<Map<String, Object>> rootCauseMap = jdbcTemplate.queryForList(rootCauseProc, new Object[]{ApplicationConstants.EMPTY_STRING, ApplicationConstants.EMPTY_STRING});
            vulCatList = new ArrayList<>(rootCauseMap.size());

            for (Map<String, Object> rootCause : rootCauseMap) {
                MasterLookUpDTO masterLookUpDTO = new MasterLookUpDTO();
                masterLookUpDTO.setMasterLookupCode((String) rootCause.get("VULN_CATGY_CD"));
                masterLookUpDTO.setLookUpEntityName((String) rootCause.get("VULN_CATGY_NM"));
                masterLookUpDTO.setLookUpEntityDescription((String) rootCause.get("VULN_CATGY_DESC"));
                vulCatList.add(masterLookUpDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : vulnerabilityCategoryList : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "vulnerabilityCategoryList() :: " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl : vulnerabilityCategoryList");
        return vulCatList;
    }

    /**
     * Save vulnerability information
     *
     * @param saveVulnerability
     * @param sessionDTO
     * @return
     * @throws AppException
     */
    @Override
    public int saveVulnerabilityInfo(VulnerabilityDTO saveVulnerability, UserProfileDTO sessionDTO) throws AppException {
        logger.info("Start: AnalystValidationDAOImpl : saveVulnerabilityInfo");
        int vulnerabilityId = 0;
        try {
            //38 columns
            int rowStatusKey = ApplicationConstants.DB_ROW_STATUS_ACTIVE_VALUE;
            int engagementStatusKey = 1;
            String saveEngagementProc = "{CALL Analyst_INS_CLNT_VULN_INSTC(?,?,?,?,?,?,?,?,?,?,"
                    + "?,?,?,?,?,?,?,?,?,?,"
                    + "?,?,?,?,?,?,?,?,?,?,"
                    + "?,?,?,?,?,?,?,?,?,?,"
                    + "?,?,?,?)}";
            vulnerabilityId = jdbcTemplate.queryForObject(saveEngagementProc,
                    new Object[]{rowStatusKey, sessionDTO.getOrganizationKey(),
                        saveVulnerability.getClientEngagementDTO().getEngagementCode(),
                        saveVulnerability.getSecurityServiceObj().getSecurityServiceCode(),
                        saveVulnerability.getSourceCode(), //Source key
                        saveVulnerability.getStatusCode(),
                        (saveVulnerability.getCveInformationDTO().getSeverityCode().equalsIgnoreCase("")) ? null : saveVulnerability.getCveInformationDTO().getSeverityCode(),
                        (saveVulnerability.getCveInformationDTO().getImpactCode().equalsIgnoreCase("")) ? null : saveVulnerability.getCveInformationDTO().getImpactCode(),
                        (saveVulnerability.getCveInformationDTO().getProbabilityCode().equalsIgnoreCase("")) ? null : saveVulnerability.getCveInformationDTO().getProbabilityCode(),
                        (saveVulnerability.getCveInformationDTO().getCostEffortCode().equalsIgnoreCase("")) ? null : saveVulnerability.getCveInformationDTO().getCostEffortCode(),
                        (saveVulnerability.getRootCauseCode().equalsIgnoreCase("")) ? null : saveVulnerability.getRootCauseCode(),
                        (saveVulnerability.getCveInformationDTO().getCveIdentifier().equalsIgnoreCase("")) ? null : saveVulnerability.getCveInformationDTO().getCveIdentifier(),
                        null, //Vulnerability instance code
                        CommonUtil.trimTheValue(saveVulnerability.getVulnerabilityName()),
                        (saveVulnerability.getDescription().equalsIgnoreCase("") ? null : saveVulnerability.getDescription()),
                        (saveVulnerability.getIpAddress().equalsIgnoreCase("") ? null : saveVulnerability.getIpAddress()),
                        //Bug Id: IN021212: For set port number as null when it is empty
                        (saveVulnerability.getPortNumber().equalsIgnoreCase("")) ? null : saveVulnerability.getPortNumber(),
                        //Bug Id: IN021212: For set port number as null when it is empty
                        null, saveVulnerability.getCveInformationDTO().getBaseScore(),
                        saveVulnerability.getCveInformationDTO().getImpSubScore(), saveVulnerability.getCveInformationDTO().getExpSubScore(),
                        saveVulnerability.getCveInformationDTO().getTemporalScore(), saveVulnerability.getCveInformationDTO().getEnvironmentalScore(),
                        (saveVulnerability.getVectorText().equalsIgnoreCase("") ? null : saveVulnerability.getVectorText()),
                        saveVulnerability.getNetworkName(),
                        null, (saveVulnerability.getHostName().equalsIgnoreCase("") ? null : saveVulnerability.getHostName()),
                        (saveVulnerability.getDomainName().equalsIgnoreCase("") ? null : saveVulnerability.getDomainName()),
                        (saveVulnerability.getSoftwareName().equalsIgnoreCase("") ? null : saveVulnerability.getSoftwareName()),
                        (saveVulnerability.getOperatingSystemCode().equalsIgnoreCase("")) ? null : saveVulnerability.getOperatingSystemCode(),
                        (saveVulnerability.getAppURL().equalsIgnoreCase("") ? null : saveVulnerability.getAppURL()),
                        saveVulnerability.getNetbiosName(),
                        (saveVulnerability.getMacAddress().equalsIgnoreCase("") ? null : saveVulnerability.getMacAddress()),
                        (saveVulnerability.getTechDetails().equalsIgnoreCase("") ? null : saveVulnerability.getTechDetails()),
                        (saveVulnerability.getImpactDetails().equalsIgnoreCase("") ? null : saveVulnerability.getImpactDetails()),
                        (saveVulnerability.getRecommendation().equalsIgnoreCase("") ? null : saveVulnerability.getRecommendation()),
                        (saveVulnerability.getRootCauseName().equalsIgnoreCase("") ? null : saveVulnerability.getRootCauseName()),
                        sessionDTO.getUserProfileKey(),
                        saveVulnerability.getCveInformationDTO().getOverallScore(),
                        (saveVulnerability.getSecurityServiceObj().getSecurityServiceCode().equalsIgnoreCase("AV")) ? saveVulnerability.getOwaspKey() : null,
                        "".equalsIgnoreCase(saveVulnerability.getValidatedDate()) ? null : saveVulnerability.getValidatedDate(),
                        "".equalsIgnoreCase(saveVulnerability.getClosedDate()) ? null : saveVulnerability.getClosedDate(),
                        "".equalsIgnoreCase(saveVulnerability.getClosedComments()) ? null : saveVulnerability.getClosedComments(),
                        saveVulnerability.getOrgSchema()

                    }, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : saveVulnerabilityInfo : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "saveVulnerabilityInfo() :: " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl : saveVulnerabilityInfo");
        return vulnerabilityId;
    }

    @Override
    public int updateVulnerabilityInfo(VulnerabilityDTO saveVulnerability, UserProfileDTO sessionDTO) throws AppException {
        logger.info("Start: AnalystValidationDAOImpl : updateVulnerabilityInfo");
        int vulnerabilityId = 0;
        String flag = "";
        try {
            //22 columns CALL Analyst_UpdateVulnerabilityInstance(3,"FP",null,null,null,null,"RCA-1",null,"vname 1","test","ttt","2.10",null,0,0,0,null,"test","test","test","ABC",4)
            String updateEngagement = "{CALL Analyst_UpdateVulnerabilityInstance(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
                    + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            int owaspkey = saveVulnerability.getOwaspKey();
            if (saveVulnerability.getSecurityServiceObj().getSecurityServiceCode() != null) {
                flag = "M";
                owaspkey = saveVulnerability.getSecurityServiceObj().getSecurityServiceCode().equalsIgnoreCase("AV") ? saveVulnerability.getOwaspKey() : 0;
            } else {
                flag = "E";
            }

            vulnerabilityId = jdbcTemplate.queryForObject(updateEngagement,
                    new Object[]{flag,
                        saveVulnerability.getSecurityServiceObj().getSecurityServiceCode(),
                        ("".equalsIgnoreCase(saveVulnerability.getIpAddress()) ? null : saveVulnerability.getIpAddress()),
                        ("".equalsIgnoreCase(saveVulnerability.getSoftwareName()) ? null : saveVulnerability.getSoftwareName()),
                        ("".equalsIgnoreCase(saveVulnerability.getHostName()) ? null : saveVulnerability.getHostName()),
                        ("".equalsIgnoreCase(saveVulnerability.getDomainName()) ? null : saveVulnerability.getDomainName()),
                        ("".equalsIgnoreCase(saveVulnerability.getAppURL()) ? null : saveVulnerability.getAppURL()),
                        ("".equalsIgnoreCase(saveVulnerability.getOperatingSystemCode())) ? null : saveVulnerability.getOperatingSystemCode(),
                        ("".equalsIgnoreCase(saveVulnerability.getMacAddress()) ? null : saveVulnerability.getMacAddress()),
                        saveVulnerability.getPortNumber(),
                        saveVulnerability.getInstanceIdentifier(),
                        saveVulnerability.getStatusCode(),
                        ("".equalsIgnoreCase(saveVulnerability.getCveInformationDTO().getSeverityCode())) ? null : saveVulnerability.getCveInformationDTO().getSeverityCode(),
                        ("".equalsIgnoreCase(saveVulnerability.getCveInformationDTO().getImpactCode())) ? null : saveVulnerability.getCveInformationDTO().getImpactCode(),
                        ("".equalsIgnoreCase(saveVulnerability.getCveInformationDTO().getProbabilityCode())) ? null : saveVulnerability.getCveInformationDTO().getProbabilityCode(),
                        ("".equalsIgnoreCase(saveVulnerability.getCveInformationDTO().getCostEffortCode())) ? null : saveVulnerability.getCveInformationDTO().getCostEffortCode(),
                        ("".equalsIgnoreCase(saveVulnerability.getRootCauseCode())) ? null : saveVulnerability.getRootCauseCode(),
                        ("".equalsIgnoreCase(saveVulnerability.getCveInformationDTO().getCveIdentifier())) ? null : saveVulnerability.getCveInformationDTO().getCveIdentifier(),
                        CommonUtil.trimTheValue(saveVulnerability.getVulnerabilityName()),
                        ("".equalsIgnoreCase(saveVulnerability.getDescription()) ? null : saveVulnerability.getDescription()),
                        null, saveVulnerability.getCveInformationDTO().getBaseScore(),
                        saveVulnerability.getCveInformationDTO().getImpSubScore(), saveVulnerability.getCveInformationDTO().getExpSubScore(),
                        saveVulnerability.getCveInformationDTO().getTemporalScore(), saveVulnerability.getCveInformationDTO().getEnvironmentalScore(),
                        ("".equalsIgnoreCase(saveVulnerability.getVectorText()) ? null : saveVulnerability.getVectorText()),
                        ("".equalsIgnoreCase(saveVulnerability.getTechDetails()) ? null : saveVulnerability.getTechDetails()),
                        ("".equalsIgnoreCase(saveVulnerability.getImpactDetails()) ? null : saveVulnerability.getImpactDetails()),
                        ("".equalsIgnoreCase(saveVulnerability.getRecommendation()) ? null : saveVulnerability.getRecommendation()),
                        ("".equalsIgnoreCase(saveVulnerability.getRootCauseName()) ? null : saveVulnerability.getRootCauseName()),
                        sessionDTO.getUserProfileKey(),
                        saveVulnerability.getCveInformationDTO().getOverallScore(),
                        owaspkey == 0 ? null : owaspkey,
                        "".equalsIgnoreCase(saveVulnerability.getValidatedDate()) ? null : saveVulnerability.getValidatedDate(),
                        "".equalsIgnoreCase(saveVulnerability.getClosedDate()) ? null : saveVulnerability.getClosedDate(),
                        "".equalsIgnoreCase(saveVulnerability.getClosedComments()) ? null : saveVulnerability.getClosedComments(),
                        saveVulnerability.getOrgSchema()
                    }, Integer.class);

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : updateVulnerabilityInfo : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "updateVulnerabilityInfo() :: " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl : updateVulnerabilityInfo");
        return vulnerabilityId;
    }

    /**
     * This method for get complianceInformation list
     *
     * @return
     * @throws AppException
     */
    @Override
    public List<ComplianceInfoDTO> getComplianceInformation() throws AppException {
        logger.info("Start: AnalystValidationDAOImpl: getComplianceInformation() to get Compliance Information");
        String procName;
        List<ComplianceInfoDTO> complianceList = new ArrayList<>();

        try {
            procName = "{CALL Analyst_ListFamilyCntrl()}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName);
            // REG_CMPLN_CD, SECUR_CTL_FAM_CD,SECUR_CTL_FAM_NM,SECUR_OBJ_CD,SECUR_OBJ_NM,SECUR_CTL_NM,SECUR_CTL_DESC
            if (!resultList.isEmpty()) {
                complianceList = new ArrayList<>(resultList.size());
                for (Map<String, Object> resultMap : resultList) {
                    ComplianceInfoDTO complianceInfoDTO = new ComplianceInfoDTO();
                    complianceInfoDTO.setComplicanceCode((String) resultMap.get("REG_CMPLN_CD"));
                    complianceInfoDTO.setComplianceVersion((String) resultMap.get("REG_CMPLN_VER"));
                    complianceInfoDTO.setFamilyCode((String) resultMap.get("SECUR_CTL_FAM_CD"));
                    complianceInfoDTO.setFamilyName((String) resultMap.get("SECUR_CTL_FAM_NM"));
                    complianceInfoDTO.setObjectiveCode((String) resultMap.get("SECUR_OBJ_CD"));
                    complianceInfoDTO.setObjectiveName((String) resultMap.get("SECUR_OBJ_NM"));
                    complianceInfoDTO.setControlName((String) resultMap.get("SECUR_CTL_NM"));
                    complianceInfoDTO.setControlDesc((String) resultMap.get("SECUR_CTL_DESC"));
                    complianceInfoDTO.setControlCode(CommonUtil.trimTheValue(resultMap.get("SECUR_CTL_CD")));
                    complianceInfoDTO.setCheckedHitrust(ApplicationConstants.COMPLIANCE_FROM_LOOKUP);
                    complianceList.add(complianceInfoDTO);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : getComplianceInformation : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "getComplianceInformation() :: " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl: getComplianceInformation() to get Compliance Information");
        return complianceList;
    }

    /**
     * This method for get CVEInformation By CveID
     *
     * @return VulnerabilityDTO
     * @throws AppException
     */
    @Override
    public VulnerabilityDTO getCVEInformationByCveID(VulnerabilityDTO vulnerabilityDTO) throws AppException {
        logger.info("Start: AnalystValidationDAOImpl: getCVEInformationByCveID() to add CVE Information");
        String procName;
        try {
            procName = "{CALL Analyst_GetCVEInformationByID(?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{vulnerabilityDTO.getCveInformationDTO().getCveIdentifier()});

            // CVE_ID,CVE_DESC,CWE_ID,UPDT_DT,BAS_SCOR,ACS_VCTR,ACS_CMPLX,CONFDTY_IMP,INGTY_IMP,AVL_IMP,VCTR_TXT,IMP_SUB_SCOR,EXPLT_SUB_SCOR
            if (!resultList.isEmpty()) {
                vulnerabilityDTO.setCveInfoExist(1);
                for (Map<String, Object> resultMap : resultList) {
                    vulnerabilityDTO.getCveInformationDTO().setCveDesc((String) resultMap.get("CVE_DESC"));
                    vulnerabilityDTO.getCveInformationDTO().setCweID((String) resultMap.get("CWE_ID"));
                    vulnerabilityDTO.getCveInformationDTO().setUpdatedDate(dateUtil.dateConvertionFromDBToJSP(resultMap.get("UPDT_DT") + ""));
                    if (resultMap.get("BAS_SCOR") != null) {
                        vulnerabilityDTO.getCveInformationDTO().setBaseScore(((BigDecimal) (resultMap.get("BAS_SCOR"))).setScale(1, RoundingMode.CEILING));
                        vulnerabilityDTO.getCveInformationDTO().setOverallScore(((BigDecimal) (resultMap.get("BAS_SCOR"))).setScale(1, RoundingMode.CEILING));
                    } else {
                        vulnerabilityDTO.getCveInformationDTO().setBaseScore(null);
                        vulnerabilityDTO.getCveInformationDTO().setOverallScore(null);
                    }
                    String vectorText = (String) resultMap.get("VCTR_TXT");
                    if (vectorText != null) {
                        vulnerabilityDTO.getCveInformationDTO().setVectorText(vectorText);
                        vulnerabilityDTO.setVectorText(vectorText);
                    } else {
                        vulnerabilityDTO.getCveInformationDTO().setVectorText("");
                        vulnerabilityDTO.setVectorText("");
                    }

                    if (resultMap.get("IMP_SUB_SCOR") != null) {
                        vulnerabilityDTO.getCveInformationDTO().setImpSubScore((BigDecimal) (resultMap.get("IMP_SUB_SCOR")));
                    } else {
                        vulnerabilityDTO.getCveInformationDTO().setImpSubScore(null);
                    }

                    if (resultMap.get("EXPLT_SUB_SCOR") != null) {
                        vulnerabilityDTO.getCveInformationDTO().setExpSubScore((BigDecimal) (resultMap.get("EXPLT_SUB_SCOR")));
                    } else {
                        vulnerabilityDTO.getCveInformationDTO().setExpSubScore(null);
                    }

                    vulnerabilityDTO.getCveInformationDTO().setTemporalScore(null);
                    vulnerabilityDTO.getCveInformationDTO().setEnvironmentalScore(null);
                }
            } else {
                vulnerabilityDTO.setCveInfoExist(0);
                vulnerabilityDTO.getCveInformationDTO().setCveDesc(ApplicationConstants.EMPTY_STRING);
                vulnerabilityDTO.getCveInformationDTO().setCweID(ApplicationConstants.EMPTY_STRING);
                vulnerabilityDTO.getCveInformationDTO().setUpdatedDate(ApplicationConstants.EMPTY_STRING);
                vulnerabilityDTO.getCveInformationDTO().setVectorText(ApplicationConstants.EMPTY_STRING);
                vulnerabilityDTO.setVectorText(ApplicationConstants.EMPTY_STRING);
                vulnerabilityDTO.getCveInformationDTO().setSeverityCode(ApplicationConstants.EMPTY_STRING);
                vulnerabilityDTO.getCveInformationDTO().setProbabilityCode(ApplicationConstants.EMPTY_STRING);
                vulnerabilityDTO.getCveInformationDTO().setImpactCode(ApplicationConstants.EMPTY_STRING);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : getCVEInformationByCveID : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "getCVEInformationByCveID() :: " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl: getCVEInformationByCveID() to add CVE Information");
        return vulnerabilityDTO;
    }

    @Override
    public VulnerabilityDTO getComplianceInformationByCveID(VulnerabilityDTO vulnerabilityDTO) throws AppException {
        logger.info("Start: AnalystValidationDAOImpl: getComplianceInformationByCveID() to get Compliance Information By CveID");
        String procName;
        List<String> controlCodesList = new ArrayList<>();
        List<ComplianceInfoDTO> complianceList;
        try {
            procName = "{CALL Analyst_GetComplianceInfoBycveID(?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{vulnerabilityDTO.getCveInformationDTO().getCveIdentifier()});

            complianceList = new ArrayList<>(resultList.size());
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    ComplianceInfoDTO complianceInfoDTO = new ComplianceInfoDTO();
                    complianceInfoDTO.setComplicanceCode((String) resultMap.get("REG_CMPLN_CD"));
                    complianceInfoDTO.setFamilyCode((String) resultMap.get("SECUR_CTL_FAM_CD"));
                    complianceInfoDTO.setFamilyName((String) resultMap.get("SECUR_CTL_FAM_NM"));
                    complianceInfoDTO.setObjectiveCode((String) resultMap.get("SECUR_OBJ_CD"));
                    complianceInfoDTO.setObjectiveName((String) resultMap.get("SECUR_OBJ_NM"));
                    complianceInfoDTO.setControlName((String) resultMap.get("SECUR_CTL_NM"));
                    complianceInfoDTO.setControlDesc((String) resultMap.get("SECUR_CTL_DESC"));
                    complianceInfoDTO.setControlCode(CommonUtil.trimTheValue(resultMap.get("SECUR_CTL_CD")));
                    controlCodesList.add(CommonUtil.trimTheValue(resultMap.get("SECUR_CTL_CD")));
                    complianceInfoDTO.setCheckedHitrust(ApplicationConstants.COMPLIANCE_FROM_CVE_ID);
                    complianceList.add(complianceInfoDTO);

                }
            }
            vulnerabilityDTO.setCheckedHitrustValues(StringUtils.join(controlCodesList, ","));
            if (null != complianceList && !complianceList.isEmpty()) {
                vulnerabilityDTO.getCveInformationDTO().setComplianceList(complianceList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : getComplianceInformation : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "getComplianceInformation() :: " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl: getComplianceInformationByCveID() to get Compliance Information By CveID");
        return vulnerabilityDTO;
    }

    /**
     * Insert selected compliances(Security control codes) for a vulnerability
     *
     * @param saveVulnerability
     * @param selectedCompliances
     * @param sessionDTO
     * @return
     * @throws AppException
     */
    @Override
    public int saveComplianceInfo(VulnerabilityDTO saveVulnerability, List<String> selectedCompliances, UserProfileDTO sessionDTO) throws AppException {
        logger.info("Start: AnalystValidationDAOImpl : saveComplianceInfo");
        int retVal = 0;
        List<String> complianceCodes = new ArrayList<>();
        try {
            String saveCompliancesProc = "{CALL Analyst_INSClientVulnerabiltysecctrl(?,?,?,?,?,?,?,?)}";
            String insertFlag = "I";
            if (selectedCompliances != null) {
                //Checked compliance codes
                if (saveVulnerability.getDftChkHitrust() != null) {
                    complianceCodes = Arrays.asList(saveVulnerability.getDftChkHitrust().split("\\s*,\\s*"));
                }
                for (String controlCode : selectedCompliances) {
                    retVal = jdbcTemplate.queryForObject(saveCompliancesProc,
                            new Object[]{saveVulnerability.getInstanceIdentifier(),
                                controlCode,
                                sessionDTO.getUserProfileKey(),
                                saveVulnerability.getComplianceInfoDTO().getComplicanceCode(),
                                saveVulnerability.getComplianceInfoDTO().getComplianceVersion(),
                                (complianceCodes.contains(controlCode)) ? ApplicationConstants.COMPLIANCE_INACTIVE : ApplicationConstants.COMPLIANCE_ACTIVE,
                                insertFlag,
                                saveVulnerability.getOrgSchema()
                            },
                            Integer.class);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : saveComplianceInfo : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "saveComplianceInfo() :: " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl : saveComplianceInfo");
        return retVal;
    }

    /**
     * Delete all compliances based on vulnerability key. Used in updating the
     * vulnerability (Delete and Insert).
     *
     * @param vulnerabilityKey
     * @return
     * @throws AppException
     */
    @Override
    public int deleteComplianceInfo(long vulnerabilityKey) throws AppException {
        logger.info("Start: AnalystValidationDAOImpl : deleteComplianceInfo");
        int retVal = 0;
        try {
            String saveCompliancesProc = "{CALL Analyst_INSClientVulnerabiltysecctrl(?,?,?,?,?,?,?)}";
            String deleteFlag = "D";
            retVal = jdbcTemplate.queryForObject(saveCompliancesProc,
                    new Object[]{vulnerabilityKey, "", 0, "", "", ApplicationConstants.COMPLIANCE_DELETE, deleteFlag
                    }, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : deleteComplianceInfo : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "deleteComplianceInfo() :: " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl : deleteComplianceInfo");
        return retVal;
    }

    @Override
    public int updateComplianceStatus(VulnerabilityDTO saveVulnerability, List<String> updtCompliances, UserProfileDTO sessionDTO, int status) throws AppException {
        logger.info("Start: AnalystValidationDAOImpl : updateComplianceStatus");
        int retVal = 0;
        List<String> complianceCodes = new ArrayList<>();
        try {
            String saveCompliancesProc = "{CALL Analyst_INSClientVulnerabiltysecctrl(?,?,?,?,?,?,?,?)}";
            String deleteFlag = "U";

            if (updtCompliances != null) {
                if (saveVulnerability.getDftChkHitrust() != null) {
                    complianceCodes = Arrays.asList(saveVulnerability.getDftChkHitrust().split("\\s*,\\s*"));
                }

                for (String controlCode : updtCompliances) {
                    int localStat = 0;
                    if (status == ApplicationConstants.COMPLIANCE_ACTIVE) {
                        localStat = (complianceCodes.contains(controlCode)) ? ApplicationConstants.COMPLIANCE_INACTIVE : ApplicationConstants.COMPLIANCE_ACTIVE;
                    } else {
                        localStat = status;
                    }

                    retVal = jdbcTemplate.queryForObject(saveCompliancesProc,
                            new Object[]{saveVulnerability.getInstanceIdentifier(),
                                controlCode,
                                sessionDTO.getUserProfileKey(), "", "",
                                localStat,
                                deleteFlag,
                                saveVulnerability.getOrgSchema()
                            }, Integer.class);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : updateComplianceStatus : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "updateComplianceStatus() :: " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl : updateComplianceStatus");
        return retVal;
    }

    /**
     *
     * @param vulnerabilityKey
     * @return
     * @throws AppException
     */
    @Override
    public VulnerabilityDTO viewVulnerabilityDetails(long vulnerabilityKey, String orgSchma) throws AppException {
        logger.info("Start: AnalystValidationDAOImpl : viewvulnerabilitydetailsList");
        VulnerabilityDTO vulnerabilityDTO = new VulnerabilityDTO();
        List<Map<String, Object>> vulnerabilityMap = null;
        try {

            String vulnerabilityProc = "{CALL Analyst_ListVulnerabilityDetails(?,?)}";
            vulnerabilityMap = jdbcTemplate.queryForList(vulnerabilityProc,
                    new Object[]{vulnerabilityKey, orgSchma});

            for (Map<String, Object> vulnerability : vulnerabilityMap) {
                vulnerabilityDTO = new VulnerabilityDTO();
                //Fetch the Vulnerability Details and Vulnerability Information details
                vulnerabilityDTO.setInstanceIdentifier(vulnerabilityKey + "");

                ClientEngagementDTO clientEngagementDTO = new ClientEngagementDTO();
                clientEngagementDTO.setSecurityServiceName(vulnerability.get("SECUR_SRVC_NM") + "");
                vulnerabilityDTO.setClientEngagementDTO(clientEngagementDTO);

                vulnerabilityDTO.setSourceCode((Integer) vulnerability.get("VULN_SRC_KEY"));
                vulnerabilityDTO.setSourceName((String) vulnerability.get("VULN_SRC"));

                SecurityServiceDTO serviceObj = new SecurityServiceDTO();
                if (vulnerability.get("VULN_SEV_NM") != null) {
                    serviceObj.setSecurityServiceName((String) vulnerability.get("VULN_SEV_NM"));
                } else {
                    serviceObj.setSecurityServiceName("");
                }
                serviceObj.setSecurityServiceCode(vulnerability.get("SECUR_SRVC_CD") + "");
                vulnerabilityDTO.setSecurityServiceObj(serviceObj);
                vulnerabilityDTO.setIpAddress((String) vulnerability.get("IPADR"));
                vulnerabilityDTO.setScanIdentifier((String) vulnerability.get("SRC_VULN_SCAN_ID"));
                vulnerabilityDTO.setScanStartDate(dateUtil.dateConvertionFromDBToJSP(vulnerability.get("SRC_VULN_SCAN_STRT_DT") + ""));
                vulnerabilityDTO.setScanEndDate(dateUtil.dateConvertionFromDBToJSP(vulnerability.get("SRC_VULN_SCAN_END_DT") + ""));

                CVEInformationDTO cveInformationDTO = new CVEInformationDTO();

                if (vulnerability.get("SFTW_NM") != null) {
                    vulnerabilityDTO.setSoftwareName(vulnerability.get("SFTW_NM") + "");
                }

                if (vulnerability.get("HST_NM") != null) {
                    vulnerabilityDTO.setHostName(vulnerability.get("HST_NM") + "");
                }

                if (vulnerability.get("DOM_NM") != null) {
                    vulnerabilityDTO.setDomainName(vulnerability.get("DOM_NM") + "");
                }

                if (vulnerability.get("APPL_URL") != null) {
                    vulnerabilityDTO.setAppURL(vulnerability.get("APPL_URL") + "");
                }

                if (vulnerability.get("OS_NM") != null) {
                    vulnerabilityDTO.setOperatingSystem(vulnerability.get("OS_NM") + "");
                }

                vulnerabilityDTO.setOperatingSystemCode(vulnerability.get("OS_KEY") + "");

                if (vulnerability.get("NETBIOS_NM") != null) {
                    vulnerabilityDTO.setNetbiosName(vulnerability.get("NETBIOS_NM") + "");
                }

                if (vulnerability.get("MAC_ADR_NM") != null) {
                    vulnerabilityDTO.setMacAddress(vulnerability.get("MAC_ADR_NM") + "");
                }

                //Bug Id: IN021212
                if (vulnerability.get("PORT_NBR") != null) {
                    vulnerabilityDTO.setPortNumber(vulnerability.get("PORT_NBR") + "");
                }
                //Bug Id: IN021212

                if (vulnerability.get("VULN_NM") != null) {
                    vulnerabilityDTO.setVulnerabilityName((String) vulnerability.get("VULN_NM"));
                }

                vulnerabilityDTO.setCreatedDate(dateUtil.dateConvertionFromDBToJSP(vulnerability.get("VULN_CREAT_DT") + ""));
                vulnerabilityDTO.setDescription((String) vulnerability.get("VULN_DESC"));
                vulnerabilityDTO.setTechDetails((String) vulnerability.get("VULN_TECH_COMMT_TXT"));
                vulnerabilityDTO.setImpactDetails((String) vulnerability.get("VULN_IMP_COMMT_TXT"));
                vulnerabilityDTO.setRecommendation((String) vulnerability.get("RECOM_COMMT_TXT"));
                vulnerabilityDTO.setVectorText((String) vulnerability.get("VULN_VCTR_TXT"));
                if (!StringUtils.isEmpty(vulnerability.get("VULN_CATGY_CD") + "")) {
                    vulnerabilityDTO.setRootCauseCode((String) vulnerability.get("VULN_CATGY_CD"));
                } else {
                    vulnerabilityDTO.setRootCauseCode("");
                }
                if ((vulnerability.get("VULN_CATGY_CD") + "").equalsIgnoreCase(ApplicationConstants.DB_OTHER_ROOT_CAUSE_CODE)) {
                    vulnerabilityDTO.setRootCauseName((String) vulnerability.get("ROOT_CAUS_COMMT_TXT"));
                } else {
                    vulnerabilityDTO.setRootCauseName((String) vulnerability.get("VULN_CATGY_NM"));
                }
                vulnerabilityDTO.setStatusCode((String) vulnerability.get("VULN_INSTC_STS_CD"));
                vulnerabilityDTO.setOldSelectedStatusCode((String) vulnerability.get("VULN_INSTC_STS_CD"));
                vulnerabilityDTO.setStatusName((String) vulnerability.get("VULN_INSTC_STS_NM"));          // StatusName
                vulnerabilityDTO.setOwaspKey((Integer) vulnerability.get("OWASP_TOP_10_KEY"));
                vulnerabilityDTO.setOwaspName((String) vulnerability.get("OWASP_NM"));

                //Fetch the  CVE Information details 
                if (vulnerability.get("CVE_ID") != null) {

                    cveInformationDTO.setCveIdentifier(vulnerability.get("CVE_ID").toString().toUpperCase());
                } else {
                    cveInformationDTO.setCveIdentifier("");
                }
                cveInformationDTO.setCweID((String) vulnerability.get("CWE_ID"));
                cveInformationDTO.setUpdatedDate(dateUtil.dateConvertionFromDBToJSP(vulnerability.get("UPDT_DT") + ""));
                cveInformationDTO.setCveDesc((String) vulnerability.get("CVE_DESC"));

                //Fetch the CVSS Scoring details
                if (vulnerability.get("VULN_IMP_SUB_SCOR") != null) {
                    cveInformationDTO.setImpSubScore(((BigDecimal) (vulnerability.get("VULN_IMP_SUB_SCOR"))).setScale(1, RoundingMode.CEILING));
                }
                if (vulnerability.get("VULN_EXPLT_SUB_SCOR") != null) {
                    cveInformationDTO.setExpSubScore(((BigDecimal) (vulnerability.get("VULN_EXPLT_SUB_SCOR"))).setScale(1, RoundingMode.CEILING));
                }
                if (vulnerability.get("VULN_BAS_SCOR") != null) {
                    cveInformationDTO.setBaseScore(((BigDecimal) (vulnerability.get("VULN_BAS_SCOR"))).setScale(1, RoundingMode.CEILING));
                }
                if (vulnerability.get("VULN_TMPRL_SCOR") != null) {
                    cveInformationDTO.setTemporalScore(((BigDecimal) (vulnerability.get("VULN_TMPRL_SCOR"))).setScale(1, RoundingMode.CEILING));
                }
                if (vulnerability.get("VULN_ENV_SCOR") != null) {
                    cveInformationDTO.setEnvironmentalScore(((BigDecimal) (vulnerability.get("VULN_ENV_SCOR"))).setScale(1, RoundingMode.CEILING));
                }

                BigDecimal ovrScr = BigDecimal.ZERO;
                if (cveInformationDTO.getBaseScore() != null) {
                    ovrScr = cveInformationDTO.getBaseScore();
                }

                if (cveInformationDTO.getTemporalScore() != null) {
                    ovrScr = cveInformationDTO.getTemporalScore();
                }

                if (cveInformationDTO.getEnvironmentalScore() != null) {
                    ovrScr = cveInformationDTO.getEnvironmentalScore();
                }

                if (vulnerability.get("VULN_OVALL_SCOR") != null) {
                    cveInformationDTO.setOverallScore(((BigDecimal) (vulnerability.get("VULN_OVALL_SCOR"))).setScale(1, RoundingMode.CEILING));
                }

                if (vulnerability.get("VULN_SEV_CD") != null) {
                    cveInformationDTO.setSeverityCode((String) vulnerability.get("VULN_SEV_CD"));
                } else {
                    cveInformationDTO.setSeverityCode("");
                }
                cveInformationDTO.setSeverityName((String) vulnerability.get("VULN_SEV_NM")); //SeverityName

                if (vulnerability.get("RISK_PRBL_CD") != null) {
                    cveInformationDTO.setProbabilityCode((String) vulnerability.get("RISK_PRBL_CD"));
                } else {
                    cveInformationDTO.setProbabilityCode("");
                }
                if (vulnerability.get("RISK_PRBL_CD") != null) {
                    cveInformationDTO.setProbabilityCode((String) vulnerability.get("RISK_PRBL_CD"));
                } else {
                    cveInformationDTO.setProbabilityCode("");
                }

                cveInformationDTO.setProbabilityName((String) vulnerability.get("RISK_PRBL_NM"));
                if (vulnerability.get("VULN_IMP_CD") != null) {
                    cveInformationDTO.setImpactCode((String) vulnerability.get("VULN_IMP_CD"));
                } else {
                    cveInformationDTO.setImpactCode("");
                }
                cveInformationDTO.setImpactName((String) vulnerability.get("VULN_IMP_NM"));
                if (vulnerability.get("RMDTN_CST_EFFRT_CD") != null) {
                    cveInformationDTO.setCostEffortCode((String) vulnerability.get("RMDTN_CST_EFFRT_CD"));
                } else {
                    cveInformationDTO.setCostEffortCode("");
                }

                if (vulnerability.get("SRC_BAS_SCOR") != null) {
                    cveInformationDTO.setSrcBaseScore(((BigDecimal) (vulnerability.get("SRC_BAS_SCOR"))).setScale(1, RoundingMode.CEILING));
                }
                if (vulnerability.get("SRC_TMPRL_SCOR") != null) {
                    cveInformationDTO.setSrctemporalScore(((BigDecimal) (vulnerability.get("SRC_TMPRL_SCOR"))).setScale(1, RoundingMode.CEILING));
                }
                if (vulnerability.get("SRC_VCTR_TXT") != null) {
                    vulnerabilityDTO.setVectorText((String) vulnerability.get("SRC_VCTR_TXT"));
                } else {
                    vulnerabilityDTO.setVectorText("");
                }
                if (vulnerability.get("SRC_VULN_SEV_NM") != null) {
                    vulnerabilityDTO.setSrcSeverityLevel((String) vulnerability.get("SRC_VULN_SEV_NM"));
                } else {
                    vulnerabilityDTO.setSrcSeverityLevel("");
                }
                if (vulnerability.get("SCAN_TL_NM") != null) {
                    vulnerabilityDTO.setScanTool((String) vulnerability.get("SCAN_TL_NM"));
                } else {
                    vulnerabilityDTO.setScanTool("");
                }

                cveInformationDTO.setCostEffortName((String) vulnerability.get("RMDTN_CST_EFFRT_NM"));
                vulnerabilityDTO.setCveInformationDTO(cveInformationDTO);
                vulnerabilityDTO.setCveInformationDTO(cveInformationDTO);
                vulnerabilityDTO.setValidatedDate(dateUtil.dateConvertionFromDBToJSP(vulnerability.get("VULN_VLD_DT") + ""));
                vulnerabilityDTO.setOldClosedDate(dateUtil.dateConvertionFromDBToJSP(vulnerability.get("VULN_CLOS_DT") + ""));
                vulnerabilityDTO.setOldValidatedDate(dateUtil.dateConvertionFromDBToJSP(vulnerability.get("VULN_VLD_DT") + ""));
                vulnerabilityDTO.setClosedDate(dateUtil.dateConvertionFromDBToJSP(vulnerability.get("VULN_CLOS_DT") + ""));
                vulnerabilityDTO.setClosedComments((String) vulnerability.get("CLOS_RSN_TXT"));

            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : vulnerabilityList : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "vulnerabilityList() :: " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl : vulnerabilityList");
        return vulnerabilityDTO;
    }

    /**
     * This method for change status of service(s) for an engagement
     *
     * @param clientEngagementCode
     * @param securityServiceCode
     * @param userId
     * @param status
     * @return int
     * @throws AppException
     */
    @Override
    public int updateServicesStatus(final String clientEngagementCode, final String securityServiceCode, final long userId, final int status)
            throws AppException {
        logger.info("Start: AnalystValidationDAOImpl : updateServicesStatus");
        int returnVal = 0;
        try {
            String updateServicesStatus = "{CALL Analyst_UpdateServiceStatusByEngServCode(?,?,?,?)}";
            returnVal = jdbcTemplate.queryForObject(updateServicesStatus,
                    new Object[]{
                        clientEngagementCode,
                        securityServiceCode,
                        userId,
                        status //Reviewed or Not Reviewed
                    }, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : updateServicesStatus: " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "updateServicesStatus(): " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl : updateServicesStatus");
        return returnVal;
    }

    @Override
    public VulnerabilityDTO getComplianceInformationByVulnerabilityKey(VulnerabilityDTO vulnerabilityDTO) throws AppException {
        logger.info("Start: AnalystValidationDAOImpl: getComplianceInformationByVulnerabilityKey() to get Compliance Information By Vulnerability Key");
        String procName;
        List<String> controlCodesList = new ArrayList<>();
        try {
            procName = "{CALL Analyst_GetClientVulnerabiltysecctrlByVulnkey(?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{vulnerabilityDTO.getInstanceIdentifier(), "A",
                vulnerabilityDTO.getOrgSchema()});
            List<ComplianceInfoDTO> complianceList = new ArrayList<>(resultList.size());

            // REG_CMPLN_CD, SECUR_CTL_FAM_CD,SECUR_CTL_FAM_NM,SECUR_OBJ_CD,SECUR_OBJ_NM,SECUR_CTL_NM,SECUR_CTL_DESC
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    ComplianceInfoDTO complianceInfoDTO = new ComplianceInfoDTO();
                    complianceInfoDTO.setComplicanceCode((String) resultMap.get("REG_CMPLN_CD"));
                    complianceInfoDTO.setFamilyCode((String) resultMap.get("SECUR_CTL_FAM_CD"));
                    complianceInfoDTO.setFamilyName((String) resultMap.get("SECUR_CTL_FAM_NM"));
                    complianceInfoDTO.setObjectiveCode((String) resultMap.get("SECUR_OBJ_CD"));
                    complianceInfoDTO.setObjectiveName((String) resultMap.get("SECUR_OBJ_NM"));
                    complianceInfoDTO.setControlName((String) resultMap.get("SECUR_CTL_NM"));
                    complianceInfoDTO.setControlDesc((String) resultMap.get("SECUR_CTL_DESC"));
                    complianceInfoDTO.setControlCode(CommonUtil.trimTheValue(resultMap.get("SECUR_CTL_CD")));
                    complianceInfoDTO.setCheckedHitrust((Integer) resultMap.get("ROW_STS_KEY"));
                    controlCodesList.add(CommonUtil.trimTheValue(resultMap.get("SECUR_CTL_CD")));
                    complianceList.add(complianceInfoDTO);

                }
            }
            vulnerabilityDTO.setCheckedHitrustValues(StringUtils.join(controlCodesList, ","));
            if (null != complianceList && !complianceList.isEmpty()) {
                vulnerabilityDTO.getCveInformationDTO().setComplianceList(complianceList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : getComplianceInformationByVulnerabilityKey : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "getComplianceInformationByVulnerabilityKey() :: " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl: getComplianceInformationByVulnerabilityKey() to get Compliance Information By Vulnerability Key");
        return vulnerabilityDTO;
    }

    @Override
    public VulnerabilityDTO getRiskDetailsByCvssScore(VulnerabilityDTO vulnerabilityDTO) throws AppException {
        logger.info("Start: AnalystValidationDAOImpl: getCVEInformationRiskDetails() to get CVEInformation Risk Details");
        try {
            String procName = "{CALL Analyst_GetRiskByCvssScore(?,?,?)}";
            List<CachedRowSet> rsSet = getCachedRiskDetails(procName, vulnerabilityDTO);
            int count = 1;
            Iterator rsItr = rsSet.iterator();
            while (rsItr.hasNext()) {
                CachedRowSet rowset = (CachedRowSet) rsItr.next();
                if (!rowset.wasNull()) {
                    ResultSet rs = rowset.getOriginal();
                    //Start: For CVSS Risk Severity 
                    if (count == 1) {
                        while (rs.next()) {
                            vulnerabilityDTO.getCveInformationDTO().setSeverityCode(rs.getString("VULN_SEV_CD"));
                            vulnerabilityDTO.getCveInformationDTO().setSeverityName(rs.getString("VULN_SEV_NM"));
                        }
                    }
                    //End: For CVSS Risk Severity 

                    //Start: For CVSS Risk Probability 
                    if (count == 2) {
                        while (rs.next()) {
                            vulnerabilityDTO.getCveInformationDTO().setProbabilityCode(rs.getString("RISK_PRBL_CD"));
                            vulnerabilityDTO.getCveInformationDTO().setProbabilityName(rs.getString("RISK_PRBL_NM"));
                        }
                    }
                    //End: For CVSS Risk Probability 

                    //Start: For CVSS Risk Impact 
                    if (count == 3) {
                        while (rs.next()) {
                            vulnerabilityDTO.getCveInformationDTO().setImpactCode(rs.getString("VULN_IMP_CD"));
                            vulnerabilityDTO.getCveInformationDTO().setImpactName(rs.getString("VULN_IMP_NM"));
                        }
                    }
                    //End: For CVSS Risk Impact 
                    count++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : getCVEInformationRiskDetails : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "getCVEInformationRiskDetails() :: " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl: getCVEInformationRiskDetails() to get CVEInformation Risk Details");
        return vulnerabilityDTO;
    }

    /**
     * This method for fetch multi result sets
     *
     * @param procName
     * @return List<CachedRowSet>
     * @throws AppException
     */
    private List<CachedRowSet> getCachedRiskDetails(final String procName, final VulnerabilityDTO vulnerabilityDTO) throws AppException {
        logger.info("Start: AnalystValidationDAOImpl: getCachedRiskDetails()");
        try {
            return jdbcTemplate.execute(new CallableStatementCreator() {
                @Override
                public CallableStatement createCallableStatement(java.sql.Connection con) throws SQLException {
                    return con.prepareCall(procName);
                }
            }, new CallableStatementCallback<List<CachedRowSet>>() {
                @Override
                public List<CachedRowSet> doInCallableStatement(CallableStatement cs) throws SQLException {
                    int _paramIndex = 0;
                    List<CachedRowSet> results = new ArrayList<CachedRowSet>();
                    cs.setObject(++_paramIndex, vulnerabilityDTO.getCveInformationDTO().getOverallScore());
                    cs.setObject(++_paramIndex, vulnerabilityDTO.getCveInformationDTO().getExpSubScore());
                    cs.setObject(++_paramIndex, vulnerabilityDTO.getCveInformationDTO().getImpSubScore());

                    boolean resultsAvailable = cs.execute();
                    while (resultsAvailable) {
                        ResultSet rs = cs.getResultSet();
                        CachedRowSet rowSet = new CachedRowSetImpl();
                        rowSet.populate(rs);

                        results.add(rowSet);
                        resultsAvailable = cs.getMoreResults();
                    }
                    logger.info("End: AnalystValidationDAOImpl: getCachedRiskDetails()");
                    return results;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : getCachedRiskDetails:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "getCachedRiskDetails() :: " + e.getMessage());
        }
    }

    /**
     * This method for fetch OS (operating system) List
     *
     * @return List<MasterLookUpDTO>
     * @throws AppException
     */
    @Override
    public List<MasterLookUpDTO> fetchOSList() throws AppException {
        logger.info("Start: AnalystValidationDAOImpl : fetchOSList");
        List<MasterLookUpDTO> osList = null;
        try {
            String rootCauseProc = "{CALL Analyst_ListOS()}";
            List<Map<String, Object>> osMap = jdbcTemplate.queryForList(rootCauseProc);
            osList = new ArrayList<>(osMap.size());

            for (Map<String, Object> operatingSystem : osMap) {
                MasterLookUpDTO masterLookUpDTO = new MasterLookUpDTO();
                masterLookUpDTO.setMasterLookupCode(operatingSystem.get("OS_KEY") + "");
                masterLookUpDTO.setLookUpEntityName((String) operatingSystem.get("OS_NM"));
                osList.add(masterLookUpDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : fetchOSList : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "fetchOSList() :: " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl : fetchOSList");
        return osList;
    }

    @Override
    public List<ComplianceInfoDTO> getHitrustCrossWalkInfoByCtrlCD(String checkedHitrustValues) throws AppException {
        logger.info("Start: AnalystValidationDAOImpl: getHitrustCrossWalkInfoByCtrlCD() to get Hitrust CrossWalk Info By CtrlCD");
        String procName;
        List<ComplianceInfoDTO> complianceList;
        try {
            procName = "{CALL Analyst_GetCROSSWALKBYCNTLCD(?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{checkedHitrustValues});
            complianceList = new ArrayList<>(resultList.size());

            //A.SECUR_CTL_MAP_KEY,A.SEC_REG_CMPLN_CD [REG_COMPLN_CD] C.REG_CMPLN_NM,A.SEC_SECUR_CTL_CD [SECUR_CTL_CD],B.SECUR_CTL_NM,
            // B.SECUR_CTL_FAM_CD,B.SECUR_CTL_FAM_NM,B.SECUR_OBJ_CD,B.SECUR_OBJ_NM
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    ComplianceInfoDTO complianceInfoDTO = new ComplianceInfoDTO();
                    complianceInfoDTO.setComplicanceCode((String) resultMap.get("REG_CMPLN_CD"));
                    complianceInfoDTO.setComplianceVersion((String) resultMap.get("REG_CMPLN_VER"));
                    complianceInfoDTO.setFamilyCode((String) resultMap.get("SECUR_CTL_FAM_CD"));
                    complianceInfoDTO.setFamilyName((String) resultMap.get("SECUR_CTL_FAM_NM"));
                    complianceInfoDTO.setObjectiveCode((String) resultMap.get("SECUR_OBJ_CD"));
                    complianceInfoDTO.setObjectiveName((String) resultMap.get("SECUR_OBJ_NM"));
                    complianceInfoDTO.setControlName((String) resultMap.get("SECUR_CTL_NM"));
                    complianceInfoDTO.setControlDesc((String) resultMap.get("SECUR_CTL_DESC"));
                    complianceInfoDTO.setControlCode(CommonUtil.trimTheValue(resultMap.get("SECUR_CTL_CD")));
                    complianceList.add(complianceInfoDTO);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : getHitrustCrossWalkInfoByCtrlCD : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "getHitrustCrossWalkInfoByCtrlCD() :: " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl: getHitrustCrossWalkInfoByCtrlCD() to get Hitrust CrossWalk Info By CtrlCD");
        return complianceList;
    }

    /**
     * This method is used find whether CVE ID is present in our database or
     * not. 0 for Not Found. 1 for Found.
     *
     * @param cveIdentifier
     * @return
     * @throws AppException
     */
    @Override
    public int checkForCveId(String cveIdentifier) throws AppException {
        logger.info("Start: AnalystValidationDAOImpl: checkForCveId()");
        String procName;
        int status = 0;
        try {
            procName = "{CALL Analyst_GetCVEInformationByID(?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{cveIdentifier});

            if (!resultList.isEmpty()) {
                status = 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : checkForCveId : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "checkForCveId() :: " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl: checkForCveId()");
        return status;
    }

    private List<CachedRowSet> getCachedVulnerabilities(final String procName, final VulnerabilityDTO vulnerabilityDTO) throws AppException {
        logger.info("Start: AnalystValidationDAOImpl: getCachedVulnerabilities()");
        try {
            return jdbcTemplate.execute(new CallableStatementCreator() {
                @Override
                public CallableStatement createCallableStatement(java.sql.Connection con) throws SQLException {
                    return con.prepareCall(procName);
                }
            }, new CallableStatementCallback<List<CachedRowSet>>() {
                @Override
                public List<CachedRowSet> doInCallableStatement(CallableStatement cs) throws SQLException {
                    int _paramIndex = 0;
                    List<CachedRowSet> results = new ArrayList<CachedRowSet>();
                    cs.setObject(++_paramIndex, vulnerabilityDTO.getClientEngagementDTO().getEngagementCode());
                    cs.setObject(++_paramIndex, vulnerabilityDTO.getPageNo());
                    cs.setObject(++_paramIndex, vulnerabilityDTO.getRowCount());
                    cs.setObject(++_paramIndex, vulnerabilityDTO.getSearchID() != null ? vulnerabilityDTO.getSearchID() : 0);
                    cs.setObject(++_paramIndex, vulnerabilityDTO.getVulnerabilityName() != null ? vulnerabilityDTO.getVulnerabilityName() : "");
                    cs.setObject(++_paramIndex, vulnerabilityDTO.getSecurityServiceObj().getSecurityServiceCode() != null ? vulnerabilityDTO.getSecurityServiceObj().getSecurityServiceCode() : "");
                    cs.setObject(++_paramIndex,0);
                    cs.setObject(++_paramIndex, vulnerabilityDTO.getIpAddress() != null ? vulnerabilityDTO.getIpAddress() : "");
                    cs.setObject(++_paramIndex, vulnerabilityDTO.getCveInformationDTO().getSeverityCode() != null ? vulnerabilityDTO.getCveInformationDTO().getSeverityCode() : "");
                    cs.setObject(++_paramIndex, vulnerabilityDTO.getStatusCode() != null ? vulnerabilityDTO.getStatusCode() : "");
                    cs.setObject(++_paramIndex, vulnerabilityDTO.getSoftwareName() != null ? vulnerabilityDTO.getSoftwareName() : "");
                    cs.setObject(++_paramIndex, vulnerabilityDTO.getOrgSchema() != null ? vulnerabilityDTO.getOrgSchema() : "");
                    cs.setObject(++_paramIndex, vulnerabilityDTO.getRootCauseCode() != null ? vulnerabilityDTO.getRootCauseCode() : "");
                    cs.setObject(++_paramIndex, vulnerabilityDTO.getSourceCode());
                    boolean resultsAvailable = cs.execute();
                    while (resultsAvailable) {
                        ResultSet rs = cs.getResultSet();
                        CachedRowSet rowSet = new CachedRowSetImpl();
                        rowSet.populate(rs);

                        results.add(rowSet);
                        resultsAvailable = cs.getMoreResults();
                    }
                    logger.info("End: AnalystValidationDAOImpl: getCachedVulnerabilities()");
                    return results;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : getCachedVulnerabilities:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "getCachedVulnerabilities() :: " + e.getMessage());
        }
    }

    /**
     *
     * @return @throws AppException
     */
    @Override
    public List<MasterLookUpDTO> analystSourceList() throws AppException {
        logger.info("Start: AnalystValidationDAOImpl : analystSourceList");
        List<MasterLookUpDTO> sourceList = null;
        try {
            String sourceProc = "{CALL Analyst_ListSource()}";
            List<Map<String, Object>> sourceMap = jdbcTemplate.queryForList(sourceProc);
            sourceList = new ArrayList<>(sourceMap.size());

            for (Map<String, Object> source : sourceMap) {
                MasterLookUpDTO masterLookUpDTO = new MasterLookUpDTO();
                masterLookUpDTO.setMasterLookupKey(Long.parseLong(source.get("MSTR_LKP_KEY") + ""));
                masterLookUpDTO.setLookUpEntityName((String) source.get("LKP_ENTY_NM"));
                sourceList.add(masterLookUpDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : analystSourceList : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "analystSourceList() :: " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl : analystSourceList");
        return sourceList;
    }

    /**
     *
     * @param secondaryCompliances
     * @param saveVulnerability
     * @param sessionDTO
     * @return
     * @throws AppException
     */
    @Override
    public int saveSecondaryComplianceInfo(List<ComplianceInfoDTO> secondaryCompliances, VulnerabilityDTO saveVulnerability, UserProfileDTO sessionDTO, int status) throws AppException {
        logger.info("Start: AnalystValidationDAOImpl : saveSecondaryComplianceInfo");
        int retVal = 0;
        try {
            String saveCompliancesProc = "{CALL Analyst_INSClientVulnerabiltysecctrl(?,?,?,?,?,?,?,?)}";
            String insertFlag = "I";

            for (ComplianceInfoDTO secCompliance : secondaryCompliances) {
                //logger.info(vulnerabilityId +", '"+secCompliance.getControlCode()+"',"+sessionDTO.getUserProfileKey()+",'"+secCompliance.getComplicanceCode()+"','"+secCompliance.getComplianceVersion()+"'");
                retVal = jdbcTemplate.queryForObject(saveCompliancesProc,
                        new Object[]{saveVulnerability.getInstanceIdentifier(),
                            CommonUtil.trimTheValue(secCompliance.getControlCode()),
                            sessionDTO.getUserProfileKey(),
                            CommonUtil.trimTheValue(secCompliance.getComplicanceCode()),
                            CommonUtil.trimTheValue(secCompliance.getComplianceVersion()),
                            status,
                            insertFlag,
                            saveVulnerability.getOrgSchema()
                        },
                        Integer.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : saveSecondaryComplianceInfo : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "saveSecondaryComplianceInfo() :: " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl : saveSecondaryComplianceInfo");
        return retVal;
    }

    @Override
    public int updateSecondaryComplianceInfo(List<ComplianceInfoDTO> secondaryCompliances, VulnerabilityDTO saveVulnerability, UserProfileDTO sessionDTO, int status) throws AppException {
        logger.info("Start: AnalystValidationDAOImpl : saveSecondaryComplianceInfo");
        int retVal = 0;
        try {
            String saveCompliancesProc = "{CALL Analyst_INSClientVulnerabiltysecctrl(?,?,?,?,?,?,?,?)}";
            String insertFlag = "U";

            for (ComplianceInfoDTO secCompliance : secondaryCompliances) {
                //logger.info(vulnerabilityId +", '"+secCompliance.getControlCode()+"',"+sessionDTO.getUserProfileKey()+",'"+secCompliance.getComplicanceCode()+"','"+secCompliance.getComplianceVersion()+"'");
                retVal = jdbcTemplate.queryForObject(saveCompliancesProc,
                        new Object[]{saveVulnerability.getInstanceIdentifier(),
                            CommonUtil.trimTheValue(secCompliance.getControlCode()),
                            sessionDTO.getUserProfileKey(),
                            CommonUtil.trimTheValue(secCompliance.getComplicanceCode()),
                            CommonUtil.trimTheValue(secCompliance.getComplianceVersion()),
                            status,
                            insertFlag,
                            saveVulnerability.getOrgSchema()
                        },
                        Integer.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : saveSecondaryComplianceInfo : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "saveSecondaryComplianceInfo() :: " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl : saveSecondaryComplianceInfo");
        return retVal;
    }

    /**
     * This method used to fetch manual vulnerability names
     *
     * @return List<String>
     * @throws AppException
     */
    @Override
    public List<VulnerabilityDTO> fetchManualVulnerabilityNames() throws AppException {
        logger.info("Start: AnalystValidationDAOImpl : fetchManualVulnerabilityNames");
        List<VulnerabilityDTO> vulnerabilityList = null;
        try {
            String manualVulnerabilitiesProc = "{CALL Analyst_ListManualVulnerabilities()}";
            vulnerabilityList = new ArrayList<>();
            List<Map<String, Object>> manualVulnerabilities = jdbcTemplate.queryForList(manualVulnerabilitiesProc);
            vulnerabilityList = new ArrayList<>(manualVulnerabilities.size());

            for (Map<String, Object> source : manualVulnerabilities) {
                VulnerabilityDTO vulnDto = new VulnerabilityDTO();
                vulnDto.setVulnerabilityName(source.get("VULN_NM") + "");
                vulnDto.setRootCauseCode(source.get("VULN_CATGY_CD") != null ? source.get("VULN_CATGY_CD") + "" : "");
                vulnerabilityList.add(vulnDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : fetchManualVulnerabilityNames : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "fetchManualVulnerabilityNames() :: " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl : fetchManualVulnerabilityNames");
        return vulnerabilityList;
    }

    /**
     * This method is used to fetch CVE list for CVE Lookup in Add/Edit
     * Vulnerability page
     *
     * @param vulnearabilityObj
     * @return
     * @throws AppException
     */
    @Override
    public List<CVEInformationDTO> fetchCVEList(VulnerabilityDTO vulnearabilityObj) throws AppException {
        logger.info("Start: AnalystValidationDAOImpl: fetchCVEList()");
        String procName;
        List<CVEInformationDTO> cveList;
        try {
            procName = "{CALL Analyst_ListCve(?,?,?,?,?)}";

            cveList = new ArrayList<>();
            List<CachedRowSet> rsSet = getCachedCVEs(procName, vulnearabilityObj);
            int totalCount = 0;
            int count = 1;
            Iterator rsItr = rsSet.iterator();
            while (rsItr.hasNext()) {
                CachedRowSet rowset = (CachedRowSet) rsItr.next();
                if (!rowset.wasNull()) {
                    ResultSet rs = rowset.getOriginal();
                    if (count == 1) {
                        while (rs.next()) {
                            totalCount = rs.getInt("TotalCount");
                        }
                    }

                    if (count == 2) {
                        while (rs.next()) {
                            CVEInformationDTO cveDTO = new CVEInformationDTO();
                            cveDTO.setCveID((String) rs.getString("CVE_ID").toUpperCase());
                            cveDTO.setCveDesc((String) rs.getString("CVE_DESC"));
                            cveDTO.setCreatedDate(dateUtil.dateConvertionFromDBToJSP(rs.getString("PUBL_DT") + ""));
                            if (rs.getString("LST_MOD_DT") != null) {
                                cveDTO.setUpdatedDate(dateUtil.dateConvertionFromDBToJSP(rs.getString("LST_MOD_DT") + ""));
                            }
                            cveDTO.setTotalCount(totalCount);
                            cveList.add(cveDTO);
                        }
                    }
                }
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : fetchCVEList : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "fetchCVEList() :: " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl: fetchCVEList()");
        return cveList;
    }

    private List<CachedRowSet> getCachedCVEs(final String procName, final VulnerabilityDTO vulnerabilityDTO) throws AppException {
        logger.info("Start: AnalystValidationDAOImpl: getCachedCVEs()");
        try {
            return jdbcTemplate.execute(new CallableStatementCreator() {
                @Override
                public CallableStatement createCallableStatement(java.sql.Connection con) throws SQLException {
                    return con.prepareCall(procName);
                }
            }, new CallableStatementCallback<List<CachedRowSet>>() {
                @Override
                public List<CachedRowSet> doInCallableStatement(CallableStatement cs) throws SQLException {
                    int _paramIndex = 0;
                    List<CachedRowSet> results = new ArrayList<CachedRowSet>();
                    cs.setObject(++_paramIndex, vulnerabilityDTO.getCveInformationDTO().getCveDesc());
                    cs.setObject(++_paramIndex, vulnerabilityDTO.getPageNo());
                    cs.setObject(++_paramIndex, vulnerabilityDTO.getRowCount());
                    cs.setObject(++_paramIndex, vulnerabilityDTO.getCveInformationDTO().getCveID());
                    cs.setObject(++_paramIndex, vulnerabilityDTO.getStatusCode()); //Flag used in procedure

                    boolean resultsAvailable = cs.execute();
                    while (resultsAvailable) {
                        ResultSet rs = cs.getResultSet();
                        CachedRowSet rowSet = new CachedRowSetImpl();
                        rowSet.populate(rs);

                        results.add(rowSet);
                        resultsAvailable = cs.getMoreResults();
                    }
                    logger.info("End: AnalystValidationDAOImpl: getCachedCVEs()");
                    return results;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : getCachedCVEs:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "getCachedCVEs() :: " + e.getMessage());
        }
    }

    @Override
    public List<String> getSecurityCtrlCodeByVulnerabilityKey(VulnerabilityDTO vulnerabilityDTO, String flag) throws AppException {
        logger.info("Start: AnalystValidationDAOImpl: getSecurityCtrlCodeByVulnerabilityKey()");
        String procName;
        List<String> controlCodesList = new ArrayList<>();
        try {
            procName = "{CALL Analyst_GetClientVulnerabiltysecctrlByVulnkey(?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{vulnerabilityDTO.getInstanceIdentifier(), flag, vulnerabilityDTO.getOrgSchema()});

            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    controlCodesList.add(CommonUtil.trimTheValue(resultMap.get("SECUR_CTL_CD")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : getSecurityCtrlCodeByVulnerabilityKey : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "getSecurityCtrlCodeByVulnerabilityKey() :: " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl: getSecurityCtrlCodeByVulnerabilityKey()");
        return controlCodesList;
    }

    @Override
    public List<OwaspDTO> fetchOwaspList() throws AppException {
        logger.info("Start: AnalystValidationDAOImpl : fetchOwaspList");
        List<OwaspDTO> owaspList = null;
        try {
            String servicesProc = "{CALL Analyst_ListOWASP_TOP_10()}";
            List<Map<String, Object>> servicesMap = jdbcTemplate.queryForList(servicesProc);
            owaspList = new ArrayList<>(servicesMap.size());

            for (Map<String, Object> service : servicesMap) {
                OwaspDTO owaspDTO = new OwaspDTO();
                owaspDTO.setOwaspId((Integer) service.get("OWASP_TOP_10_KEY"));
                owaspDTO.setOwaspCode((String) service.get("OWASP_CD"));
                owaspDTO.setOwaspName((String) service.get("OWASP_NM"));
                owaspDTO.setOwaspDescription((String) service.get("OWASP_DESC"));
                owaspList.add(owaspDTO);

            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : fetchOwaspList : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "fetchOwaspList() :: " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl : fetchOwaspList");
        return owaspList;
    }

    /*
     * Insert Finding Name and Vulnerability Category Code into VULN table.
     * Master table of Finding Names contains unique finding names.
     * 
     * @param vulnerabilityDTO
     * @param sessionDTO
     * @return
     * @throws AppException
     */
    @Override
    public int insertFindingName(VulnerabilityDTO vulnerabilityDTO, UserProfileDTO sessionDTO) throws AppException {
        logger.info("Start: AnalystValidationDAOImpl: insertFindingName");
        String procName = "";
        int returnVal = 0;
        try {
            String insertFlag = "I";
            procName = "{CALL INSVULN(?,?,?,?,?)}";
            returnVal = jdbcTemplate.queryForObject(procName,
                    new Object[]{
                        insertFlag,
                        CommonUtil.trimTheValue(vulnerabilityDTO.getVulnerabilityName()),
                        vulnerabilityDTO.getRootCauseCode(),
                        sessionDTO.getUserProfileKey(),
                        null
                    },
                    Integer.class);
        } catch (Exception e) {
            logger.debug("Exception occured: insertFindingName: " + e.getMessage());
            throw new AppException("Exception occured while executing the "
                    + "insertFindingName() :: " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl: insertFindingName");
        return returnVal;
    }

    /*
     * Find the number of records with a given Finding Name.
     *
     * @param findingName
     * @return
     * @throws AppException
     */
    @Override
    public int findingNameCount(String findingName) throws AppException {
        logger.info("Start : AnalystValidationDAOImpl : findingNameCount");
        int findingCnt = 0;
        String procName = "";
        try {
            procName = "{CALL Check_VULNName(?)}";
            List<Map<String, Object>> findingCntMap = jdbcTemplate.queryForList(procName,
                    new Object[]{findingName});
            for (Map<String, Object> findingObj : findingCntMap) {
                findingCnt = (Integer) findingObj.get("COUNT");
            }
        } catch (Exception e) {
            logger.debug("Exception occured : findingNameCount : " + e.getMessage());
            throw new AppException("Exception occured while executing the "
                    + "findingNameCount " + e.getMessage());
        }
        logger.info("End : AnalystValidationDAOImpl : findingNameCount");
        return findingCnt;
    }

    @Override
    public int updateFindingStatus(String flag, String orgSchema, long findingID, String statusCode, String closeReason, UserProfileDTO sessionDTO) throws AppException {
        logger.info("Start: AnalystValidationDAOImpl: updateFindingStatus");
        String procName = "";
        int returnVal = 0;
        try {
            procName = "{CALL Analyst_UpdateVulnerabilityStatus(?,?,?,?,?,?,?)}";
            returnVal = jdbcTemplate.queryForObject(procName,
                    new Object[]{
                        flag,
                        findingID,
                        statusCode,
                        sessionDTO.getUserProfileKey(),
                        null,
                        closeReason,
                        orgSchema
                    },
                    Integer.class);
        } catch (Exception e) {
            logger.debug("Exception occured: updateFindingStatus: " + e.getMessage());
            throw new AppException("Exception occured while executing the "
                    + "updateFindingStatus() :: " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl: updateFindingStatus");
        return 1;
    }
}
