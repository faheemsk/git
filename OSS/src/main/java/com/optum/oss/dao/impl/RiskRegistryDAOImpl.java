/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao.impl;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.constants.PermissionConstants;
import com.optum.oss.dao.RiskRegistryDAO;
import com.optum.oss.dto.ManageServiceUserDTO;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.RemediationDTO;
import com.optum.oss.dto.RiskRegisterDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.dto.VulnerabilityDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.util.DateUtil;
import com.optum.oss.util.EncryptDecrypt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author rpanuganti
 */
public class RiskRegistryDAOImpl implements RiskRegistryDAO {

    private static final Logger logger = Logger.getLogger(RemediationProjectManagerDaoImpl.class);

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
     * This method for fetch Remediation Project Manager work list
     *
     * @param manageServiceUserDTO
     * @return List<ManageServiceUserDTO>
     * @throws AppException
     */
    @Override
    public List<ManageServiceUserDTO> fetchRiskRegistryWorklist(UserProfileDTO userProfileDTO, final ManageServiceUserDTO manageServiceUserDTO) throws AppException {

        logger.info("Start: RemediationProjectManagerDaoImpl: fetchRemediationWorklist() to fetch Remediation Project Manager worklist");
        String analystWorklistProc = "{CALL [RMDTN_GetRiskRegstWorklist](?,?,?,?,?,?)}";
        List<ManageServiceUserDTO> serviceList = null;
        try {
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(analystWorklistProc, new Object[]{
                userProfileDTO.getUserProfileKey(),manageServiceUserDTO.getOrgSchema(),manageServiceUserDTO.getUserFlag(),
                manageServiceUserDTO.getOrganizationName(),
                manageServiceUserDTO.getClientEngagementCode(),
                manageServiceUserDTO.getEngagementName()
            });
            serviceList = new ArrayList<>(resultList.size());

            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    ManageServiceUserDTO serviceUserDTO = new ManageServiceUserDTO();
                    serviceUserDTO.setClientEngagementCode(resultMap.get("CLNT_ENGMT_CD") + "");
                    serviceUserDTO.setEncClientEngagementCode(encDecrypt.encrypt(resultMap.get("CLNT_ENGMT_CD") + ""));
                    serviceUserDTO.setOrganizationKey((Integer) resultMap.get("CLNT_ORG_KEY"));
                    serviceUserDTO.setOrganizationName(resultMap.get("ORG_NM") + "");
                    serviceUserDTO.setEngagementName(resultMap.get("CLNT_ENGMT_NM") + "");
                    serviceUserDTO.setRowStatusValue("");

                    serviceList.add(serviceUserDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : RemediationProjectManagerDaoImpl: fetchRemediationWorklist(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "AnalystValidationDAOImpl: fetchAnalystValidationWorklist(): " + e.getMessage());
        }
        logger.info("End: RemediationProjectManagerDaoImpl: fetchRemediationWorklist() to fetch Analyst Validation worklist");
        return serviceList;
    }

    @Override
    public List<RiskRegisterDTO> riskRegistryList(String engagementCode, String orgSchema, final long userID,String flag) throws AppException {
        logger.info("Start: RiskRegistryDAOImpl : riskRegistryList");
        List<RiskRegisterDTO> riskregistryList = null;
        try {
            String procName = "{CALL RMDTN_GetRiskRegistrylist(?,?,?,?)}";
            List<Map<String, Object>> riskRegisterMap = jdbcTemplate.queryForList(procName, new Object[]{
                engagementCode, userID, orgSchema,flag
            });
            riskregistryList = new ArrayList<>(riskRegisterMap.size());

            for (Map<String, Object> riskregister : riskRegisterMap) {
                RiskRegisterDTO riskRegisterDTO = new RiskRegisterDTO();
                riskRegisterDTO.setRiskRegisterID((Integer) riskregister.get("CLNT_RISK_RGST_KEY"));
                riskRegisterDTO.setAdjustedSeverity((String) riskregister.get("VULN_SEV_NM"));
                riskRegisterDTO.setPlanID(riskregister.get("CLNT_RMDTN_PLN_KEY") + "");
                riskRegisterDTO.setFindingsCount(riskregister.get("RISK_RGST_ITM_TOT_CNT")+"");
                riskRegisterDTO.setPlanTitle(riskregister.get("RMDTN_PLN_NM") + ""); 
                riskRegisterDTO.setRiskResponse(riskregister.get("RISK_RSPN_NM") == null ? "" : riskregister.get("RISK_RSPN_NM") + "");
                riskRegisterDTO.setRiskResponseCode(riskregister.get("RISK_RSPN_CD") == null ? "" : riskregister.get("RISK_RSPN_CD") + "");
                riskRegisterDTO.setAcceptedBy(riskregister.get("ACPT_USER_SGN_TXT") == null ? "" : riskregister.get("ACPT_USER_SGN_TXT") + "");
                riskRegisterDTO.setCreatedDate(riskregister.get("ACPT_DT") == null ? "" : dateUtil.dateConvertionFromDBToJSP(riskregister.get("ACPT_DT") + ""));

                riskregistryList.add(riskRegisterDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : riskRegistryList : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "remediationPlanList() :: " + e.getMessage());
        }
        logger.info("End: RiskRegistryDAOImpl : riskRegistryList");
        return riskregistryList;
    }

    @Override
    public RiskRegisterDTO riskRegistryDetailsById(String orgSchema, int riskRegisterID) throws AppException {
        logger.info("Start: RiskRegistryDAOImpl : riskRegistryDetailsById");
        RiskRegisterDTO riskRegisterDTO = new RiskRegisterDTO();
        try {
            String procName = "{CALL [RMDTN_GetRiskInfoByKey](?,?)}";
            List<Map<String, Object>> riskRegisterMap = jdbcTemplate.queryForList(procName, new Object[]{
                riskRegisterID, orgSchema
            });
            for (Map<String, Object> riskregister : riskRegisterMap) {
                riskRegisterDTO.setRiskRegisterID((Integer) riskregister.get("CLNT_RISK_RGST_KEY"));
                riskRegisterDTO.setEncRiskRegistryId(encDecrypt.encrypt(riskRegisterDTO.getRiskRegisterID() + ""));
                riskRegisterDTO.setAdjustedSeverity((String) riskregister.get("VULN_SEV_NM"));
                riskRegisterDTO.setCompensationControl((String) riskregister.get("SUM_COMP_CTL_TXT"));
                riskRegisterDTO.setRiskRegistryOwnerID((Integer)riskregister.get("ACPT_USER_ID") == null ? 0 : (Integer)riskregister.get("ACPT_USER_ID")); 
                riskRegisterDTO.setPlanID(riskregister.get("CLNT_RMDTN_PLN_KEY") + "");
                riskRegisterDTO.setEncPlanID(encDecrypt.encrypt(riskRegisterDTO.getPlanID() + ""));
                riskRegisterDTO.setPlanTitle(riskregister.get("RMDTN_PLN_NM") + "");
                riskRegisterDTO.setPlanDetails(riskregister.get("RMDTN_PLN_DESC") + "");
                riskRegisterDTO.setRiskResponseCode( riskregister.get("RISK_RSPN_CD") == null ? "" : riskregister.get("RISK_RSPN_CD") + "");
                riskRegisterDTO.setRiskResponse(riskregister.get("RISK_RSPN_NM") == null ? "" : riskregister.get("RISK_RSPN_NM") + ""); 
                riskRegisterDTO.setAcceptedBy( riskregister.get("ACPT_USER_SGN_TXT") == null ? "" : riskregister.get("ACPT_USER_SGN_TXT") + "");
                riskRegisterDTO.setRiskResponseJustification(riskregister.get("RISK_JSTFY_TXT") == null ? "" : riskregister.get("RISK_JSTFY_TXT") + "");
                riskRegisterDTO.setAcceptedDate(riskregister.get("ACPT_DT") == null ? "" : dateUtil.dateConvertionFromDBToJSP(riskregister.get("ACPT_DT") + ""));
                riskRegisterDTO.setCreatedDate(riskregister.get("RMDTN_PLN_CREAT_DT") == null ? "" : dateUtil.dateConvertionFromDBToJSP(riskregister.get("RMDTN_PLN_CREAT_DT") + ""));
                riskRegisterDTO.setNotifyDate(riskregister.get("RMDTN_PLN_NTF_DT") == null ? "" : dateUtil.dateConvertionFromDBToJSP(riskregister.get("RMDTN_PLN_NTF_DT") + ""));
                riskRegisterDTO.setRegistryNotifyDate(riskregister.get("NTF_DT") == null ? "" : dateUtil.dateConvertionFromDBToJSP(riskregister.get("NTF_DT") + ""));
                riskRegisterDTO.setStartDate(riskregister.get("RMDTN_PLN_STRT_DT") == null ? "" : dateUtil.dateConvertionFromDBToJSP(riskregister.get("RMDTN_PLN_STRT_DT") + ""));
                riskRegisterDTO.setRemediationOwner((riskregister.get("RMDTN_OWNR_FST_NM") == null ? "" : riskregister.get("RMDTN_OWNR_FST_NM"))+""+" "+(riskregister.get("RMDTN_OWNR_LST_NM") == null ? "" : riskregister.get("RMDTN_OWNR_LST_NM")+"") );
                riskRegisterDTO.setRemediationOwnerDept(riskregister.get("RMDTN_OWNR_DEPT_NM") == null ? "" : riskregister.get("RMDTN_OWNR_DEPT_NM") + "");
              
                riskRegisterDTO.setRiskRegistryOwner((riskregister.get("REG_OWNR_FST_NM") == null ? "" : riskregister.get("REG_OWNR_FST_NM"))+""+" "+(riskregister.get("REG_OWNR_LST_NM") == null ? "" : riskregister.get("REG_OWNR_LST_NM")+"") );
                riskRegisterDTO.setRegistryOwnerDept(riskregister.get("REG_OWNR_DEPT_NM") == null ? "" : riskregister.get("REG_OWNR_DEPT_NM") + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : riskRegistryDetailsById : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "riskRegistryDetailsById() :: " + e.getMessage());
        }
        logger.info("End: RiskRegistryDAOImpl : riskRegistryDetailsById");
        return riskRegisterDTO;

    }

    @Override
    public List<VulnerabilityDTO> riskRegistryFindingsListByID(int registryID, String orgSchema) throws AppException {
        logger.info("Start: RiskRegistryDAOImpl : riskRegistryFindingsListByID");
        List<VulnerabilityDTO> findingList = null;
        try {
            String procName = "{CALL RMDTN_GetFindingsByRegistKey(?,?)}";
            List<Map<String, Object>> findingMap = jdbcTemplate.queryForList(procName, new Object[]{
                registryID, orgSchema
            });
            findingList = new ArrayList<>(findingMap.size());

            for (Map<String, Object> finding : findingMap) {
                VulnerabilityDTO vulnerabilityDTO = new VulnerabilityDTO();
                vulnerabilityDTO.setInstanceIdentifier(finding.get("CLNT_VULN_INSTC_KEY") + "");
                vulnerabilityDTO.setEncInstanceIdentifier(encDecrypt.encrypt(finding.get("CLNT_VULN_INSTC_KEY") + ""));
                vulnerabilityDTO.setVulnerabilityName((String) finding.get("VULN_NM"));
                vulnerabilityDTO.setStatusName(finding.get("VULN_INSTC_STS_NM") == null ? "" : finding.get("VULN_INSTC_STS_NM") + "");
                vulnerabilityDTO.setIpAddress(finding.get("IPADR") == null ? "" : finding.get("IPADR") + "");
//              vulnerabilityDTO.setAppURL(finding.get("APPL_URL") == null?"":finding.get("APPL_URL")+"");
                vulnerabilityDTO.getCveInformationDTO().setSeverityName(finding.get("VULN_SEV_NM") + "");
                vulnerabilityDTO.setSoftwareName(finding.get("SFTW_NM") == null ? "" : finding.get("SFTW_NM") + ""); //Application Name
                vulnerabilityDTO.setCreatedDate(finding.get("VULN_CREAT_DT") == null ? "" : dateUtil.dateConvertionFromDBToJSP(finding.get("VULN_CREAT_DT") + ""));
                vulnerabilityDTO.setClosedDate(finding.get("VULN_CLOS_DT") == null ? "" : dateUtil.dateConvertionFromDBToJSP(finding.get("VULN_CLOS_DT") + ""));
                vulnerabilityDTO.setRootCauseName(finding.get("VULN_CATGY_NM") == null ? "" : finding.get("VULN_CATGY_NM") + ""); //Vulnerability category name
                vulnerabilityDTO.setAdjSeverityName(finding.get("ADJ_SEV_NM") == null? "":finding.get("ADJ_SEV_NM") + "");
                findingList.add(vulnerabilityDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : riskRegistryFindingsListByID : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "riskRegistryFindingsListByID() :: " + e.getMessage());
        }
        logger.info("End: RiskRegistryDAOImpl : riskRegistryFindingsListByID");
        return findingList;
    }

    @Override
    public int updateRiskRegistryDetails(RiskRegisterDTO riskRegistryDto, UserProfileDTO usersSessionDTO) throws AppException {
        logger.info("Start: RiskRegistryDAOImpl : updateRiskRegistryDetails");
        int retVal = 0;
        String ownerID = null;
        if (riskRegistryDto.getRiskRegistryOwnerID() == 0) {
            ownerID = null;
        } else {

            ownerID = riskRegistryDto.getRiskRegistryOwnerID() + "";
        }
        try {
            String insMstrProc = "{CALL RMDTN_UpdateRiskRegistry(?,?,?,?,?,?,?)}";
            retVal = jdbcTemplate.queryForObject(insMstrProc, new Object[]{riskRegistryDto.getRiskRegisterID(), riskRegistryDto.getAdjustedSeverity(), riskRegistryDto.getCompensationControl(),
                ownerID, usersSessionDTO.getUserProfileKey(), riskRegistryDto.getOrgSchema(),"U"}, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : updateRiskRegistryDetails:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "updateRiskRegistryDetails() :: " + e.getMessage());
        }
        logger.info("End: RiskRegistryDAOImpl : updateRiskRegistryDetails");
        return retVal;
    }

    @Override
    public int updateRiskAcceptanceDetails(RiskRegisterDTO riskRegistryDto, UserProfileDTO usersSessionDTO) throws AppException {
        logger.info("Start: RiskRegistryDAOImpl : updateRiskAcceptanceDetails");
        int retVal = 0;
        try {
            String flag = "U";
            String insMstrProc = "{CALL [RMDTN_INS_CLNT_RISK_RGST](?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            retVal = jdbcTemplate.queryForObject(insMstrProc, new Object[]{riskRegistryDto.getRiskRegisterID(), 1, riskRegistryDto.getPlanID(), "", 7, null, "",
                null, usersSessionDTO.getUserProfileKey(), null, riskRegistryDto.getAcceptedBy(), riskRegistryDto.getRiskResponseCode(), riskRegistryDto.getRiskResponseJustification(), 1, usersSessionDTO.getUserProfileKey(), flag, riskRegistryDto.getOrgSchema()}, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : updateRiskAcceptanceDetails:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "updateRiskAcceptanceDetails() :: " + e.getMessage());
        }
        logger.info("End: RiskRegistryDAOImpl : updateRiskAcceptanceDetails");
        return retVal;
    }

    @Override
    public int removeRiskRegistryItems(String[] items, int RegistryID, String orgSchema) throws AppException {
        logger.info("Start: RiskRegistryDAOImpl : removeRiskRegistryItems");
        int returnVal = 0, deletedCount = 0;
        try {
            String deleteVulnerabilityProc = "{CALL RMDTN_DEL_RISK_RGST_ITM(?,?,?)}";
            for (String fid : items) {
                returnVal = jdbcTemplate.queryForObject(deleteVulnerabilityProc,
                        new Object[]{
                            //                            Integer.parseInt(encDecrypt.decrypt(s)), RegistryID, orgSchema
                            RegistryID, fid, orgSchema
                        }, Integer.class);
                deletedCount++;
            }
            logger.info("Number of RiskRegistryItems Removed: " + deletedCount);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : removeRiskRegistryItems: " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "removeRiskRegistryItems(): " + e.getMessage());
        }
        logger.info("End: RiskRegistryDAOImpl : removeRiskRegistryItems");
        return returnVal;
    }

    public int saveRiskRigistryFindings(RemediationDTO remediationDto, UserProfileDTO sessionDTO) throws AppException {
        logger.info("Start: RemediationDAOImpl : saveRiskRigistryFindings");
        int riskItemId = 0;
        try {
            List<String> assocFindingIds = new ArrayList(Arrays.asList(remediationDto.getSelectedInstances().split("\\s*,\\s*")));
            String riskId = encDecrypt.decrypt(remediationDto.getEncRiskRegistryId());

            String insertFlag = "I";
            String procName = "{CALL RMDTN_INS_CLNT_RISK_RGST_ITM(?,?,?,?,?)}";

            for (String instanceId : assocFindingIds) {
                riskItemId = jdbcTemplate.queryForObject(procName,
                        new Object[]{
                            riskId,
                            instanceId,
                            sessionDTO.getUserProfileKey(),
                            insertFlag,
                            remediationDto.getOrgSchema()
                        }, Integer.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : saveRiskRigistryFindings : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "saveRiskRigistryFindings() :: " + e.getMessage());
        }
        logger.info("End: RemediationDAOImpl : saveRiskRigistryFindings");
        return riskItemId;
    }

    public int saveRemediatePlanFindings(RemediationDTO remediationDto, UserProfileDTO sessionDTO) throws AppException {
        logger.info("Start: RemediationDAOImpl : saveRemediatePlanFindings");
        int planFindId = 0;
        try {
            List<String> assocFindingIds = new ArrayList(Arrays.asList(remediationDto.getSelectedInstances().split("\\s*,\\s*")));

            int rowStatusKey = ApplicationConstants.DB_ROW_STATUS_ACTIVE_VALUE;
            String insertFlag = "I";
            String procName = "{CALL RMDTN_INS_CLNT_VULN_RMDTN_PLN_ITM(?,?,?,?,?,?,?,?)}";

            for (String instanceId : assocFindingIds) {
                planFindId = jdbcTemplate.queryForObject(procName,
                        new Object[]{
                            instanceId,
                            remediationDto.getPlanID(),
                            "R",
                            null,
                            rowStatusKey,
                            sessionDTO.getUserProfileKey(),
                            insertFlag,
                            remediationDto.getOrgSchema()
                        }, Integer.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : saveRemediatePlanFindings : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "saveRemediatePlanFindings() :: " + e.getMessage());
        }
        logger.info("End: RemediationDAOImpl : saveRemediatePlanFindings");
        return planFindId;
    }

    @Override
    public List<MasterLookUpDTO> fetchRiskResponseList()
            throws AppException {
        List<MasterLookUpDTO> masterLookUpList = new ArrayList<>();
        try {
            String procName = "{CALL RMDTN_ListRiskRSPN()}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName,
                    new Object[]{});

            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    MasterLookUpDTO lookUpDTO = new MasterLookUpDTO();
                    lookUpDTO.setMasterLookupCode((String) (resultMap.get("RISK_RSPN_CD")));
                    lookUpDTO.setLookUpEntityName((String) (resultMap.get("RISK_RSPN_NM")));
                    masterLookUpList.add(lookUpDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : fetchRiskResponseList:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "fetchRiskResponseList() :: " + e.getMessage());
        }
        return masterLookUpList;
    }
    
    
    public int updateFindingCountRegistryAnsPlan(int key,int findingCount,long userID, String orgSchema,String flag) throws AppException {
        logger.info("Start: RiskRegistryDAOImpl : updateFindingCountRegistryAnsPlan");
        int returnVal = 0, deletedCount = 0;
        try {
            String deleteVulnerabilityProc = "{CALL RMDTN_UpdateItemsCount(?,?,?,?,?)}";
                returnVal = jdbcTemplate.queryForObject(deleteVulnerabilityProc,
                        new Object[]{
                            key, findingCount,userID, orgSchema,flag
                        }, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : updateFindingCountRegistryAnsPlan: " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "updateFindingCountRegistryAnsPlan(): " + e.getMessage());
        }
        logger.info("End: RiskRegistryDAOImpl : updateFindingCountRegistryAnsPlan");
        return returnVal;
    }
    
    public int updateRiskRegistryNotifyDate(RiskRegisterDTO registryDto,UserProfileDTO sessionDTO) throws AppException {
        logger.info("Start: RiskRegistryDAOImpl : updateRiskRegistryNotifyDate()");
        int retVal = 0;
        try {
            String procName = "{CALL [RMDTN_UpdRegistryUserDate](?,?,?,?,?)}";
            
            retVal = jdbcTemplate.queryForObject(procName,
                    new Object[]{
                        registryDto.getRiskRegisterID(),
                        registryDto.getRiskRegistryOwnerID(),
                        sessionDTO.getUserProfileKey(),
                        registryDto.getOrgSchema(),
                        "U"
                    }, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : updateRiskRegistryNotifyDate : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "updateRiskRegistryNotifyDate() :: " + e.getMessage());
        }
        logger.info("End: RiskRegistryDAOImpl : updateRiskRegistryNotifyDate()");
        return retVal;
    }
    
    @Override
    public String generateRiskSeverity(String engCode, String orgSchema, String registryID) throws AppException {
        logger.info("Start: RiskRegistryDAOImpl : generateRiskSeverity");
       String planSeverity = null;
        try {
            String procName = "{CALL RMDTN_GetRegistryPlanSeverity(?,?,?)}";
            List<Map<String, Object>> severityMap = jdbcTemplate.queryForList(procName, 
                    new Object[]{
                        engCode,
                        orgSchema,
                        registryID
                    });
            for (Map<String, Object> severity : severityMap) {
                if(Integer.parseInt(severity.get("Critical")+"") == 1){
                    planSeverity = ApplicationConstants.SEVERITY_CRITICAL;
                }else if(Integer.parseInt(severity.get("High")+"") == 1){
                    planSeverity = ApplicationConstants.SEVERITY_HIGH;
                }else if(Integer.parseInt(severity.get("Medium")+"") == 1){
                    planSeverity = ApplicationConstants.SEVERITY_MEDIUM;
                }else if(Integer.parseInt(severity.get("Low")+"") == 1){
                    planSeverity = ApplicationConstants.SEVERITY_LOW;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : generateRiskSeverity : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "generateRemdiationSevergenerateRiskSeverityity() :: " + e.getMessage());
        }
        logger.info("End: RiskRegistryDAOImpl : generateRiskSeverity");
        return planSeverity;
    }
    
    @Override
    public int updateRiskRegistrySeverity(RiskRegisterDTO riskRegistryDto, UserProfileDTO usersSessionDTO) throws AppException {
        logger.info("Start: RemediationDAOImpl : updateRiskRegistrySeverity");
        int retVal = 0;
        String ownerID = null;
        if (riskRegistryDto.getRiskRegistryOwnerID() == 0) {
            ownerID = null;
        } else {

            ownerID = riskRegistryDto.getRiskRegistryOwnerID() + "";
        }
        try {
            String insMstrProc = "{CALL RMDTN_UpdateRiskRegistry(?,?,?,?,?,?,?)}";
            retVal = jdbcTemplate.queryForObject(insMstrProc, new Object[]{riskRegistryDto.getRiskRegisterID(), riskRegistryDto.getAdjSeverityCode(), riskRegistryDto.getCompensationControl(),
                ownerID, usersSessionDTO.getUserProfileKey(), riskRegistryDto.getOrgSchema(),"S"}, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : updateRiskRegistrySeverity:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "updateRiskRegistrySeverity() :: " + e.getMessage());
        }
        logger.info("End: RemediationDAOImpl : updateRiskRegistrySeverity");
        return retVal;
    }
    
    @Override
    public VulnerabilityDTO riskRegistryFindingInfo(int instanceId, String orgSchema) throws AppException {
        logger.info("Start: RiskRegistryDAOImpl : riskRegistryFindingsListByID");
        VulnerabilityDTO vulnerabilityDTO = new VulnerabilityDTO();
        try {
            String procName = "{CALL RMDTN_GetRegItmsByInstcKey(?,?)}";
            List<Map<String, Object>> findingMap = jdbcTemplate.queryForList(procName, new Object[]{
                instanceId, orgSchema
            });

            for (Map<String, Object> finding : findingMap) {
                vulnerabilityDTO.setInstanceIdentifier(finding.get("CLNT_VULN_INSTC_KEY") + "");
                vulnerabilityDTO.setEncInstanceIdentifier(encDecrypt.encrypt(finding.get("CLNT_VULN_INSTC_KEY") + ""));
                vulnerabilityDTO.setVulnerabilityName((String) finding.get("VULN_NM"));
                vulnerabilityDTO.setStatusName(finding.get("VULN_INSTC_STS_NM") == null ? "" : finding.get("VULN_INSTC_STS_NM") + "");
                vulnerabilityDTO.getCveInformationDTO().setSeverityName(finding.get("VULN_SEV_NM") + "");
                vulnerabilityDTO.setAdjSeverityName(finding.get("ADJ_RMDTN_PLN_SEV_NM") == null? "":finding.get("ADJ_RMDTN_PLN_SEV_NM") + "");
                vulnerabilityDTO.setAdjSeverityCode(finding.get("ADJ_SEV_CD") + "");
                vulnerabilityDTO.setCompensatingCtrl(finding.get("COMP_CTL_TXT") + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : riskRegistryFindingsListByID : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "riskRegistryFindingsListByID() :: " + e.getMessage());
        }
        logger.info("End: RiskRegistryDAOImpl : riskRegistryFindingsListByID");
        return vulnerabilityDTO;
    }
    
    @Override
    public int updateRiskRegistryFinding(RiskRegisterDTO riskRegistryDto, UserProfileDTO usersSessionDTO) throws AppException {
        logger.info("Start: RemediationDAOImpl : updateRiskRegistryFinding");
        int retVal = 0;
        
        try {
            String insMstrProc = "{CALL RMDTN_UpdateRiskRegistryITMS(?,?,?,?,?,?,?)}";
            retVal = jdbcTemplate.queryForObject(insMstrProc, 
                    new Object[]{riskRegistryDto.getRiskRegisterID(),
                        riskRegistryDto.getInstanceIdentifier(), 
                        riskRegistryDto.getAdjSeverityCode(), 
                        riskRegistryDto.getCompensationControl(),
                        usersSessionDTO.getUserProfileKey(), 
                        riskRegistryDto.getOrgSchema(),
                        "U"}, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : updateRiskRegistryFinding:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "updateRiskRegistryFinding() :: " + e.getMessage());
        }
        logger.info("End: RemediationDAOImpl : updateRiskRegistryFinding");
        return retVal;
    }
}
