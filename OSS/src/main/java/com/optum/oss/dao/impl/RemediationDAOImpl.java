/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.dao.impl;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dao.RemediationDAO;
import com.optum.oss.dto.CVEInformationDTO;
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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

/**
 *
 * @author sbhagavatula
 */
public class RemediationDAOImpl implements RemediationDAO{
    
    private static final Logger logger = Logger.getLogger(RemediationDAOImpl.class);

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
     *
     * @return
     * @throws AppException
     */
    @Override
    public List<MasterLookUpDTO> fetchSeverityList() throws AppException {
        logger.info("Start: RemediationDAOImpl : fetchSeverityList");
        List<MasterLookUpDTO> severityList = null;
        try {
            String procName = "{CALL analyst_ListSeverity(?,?)}";
            List<Map<String, Object>> severityMap = jdbcTemplate.queryForList(procName, new Object[]{"",""});
            severityList = new ArrayList<>(severityMap.size());

            for (Map<String, Object> severity : severityMap) {
                MasterLookUpDTO masterLookUpDTO = new MasterLookUpDTO();
                masterLookUpDTO.setMasterLookupCode(severity.get("VULN_SEV_CD") + "");
                masterLookUpDTO.setLookUpEntityName((String) severity.get("VULN_SEV_NM"));
                severityList.add(masterLookUpDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : fetchSeverityList : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "fetchSeverityList() :: " + e.getMessage());
        }
        logger.info("End: RemediationDAOImpl : fetchSeverityList");
        return severityList;
    }
    
    @Override
    public List<VulnerabilityDTO> findingLookup(RemediationDTO remediationDto) throws AppException {
        logger.info("Start: RemediationDAOImpl : findingLookup");
        List<VulnerabilityDTO> findingList = null;
        try {
            String procName = "{CALL RMTDN_GetFindingListSearchCriteria(?,?,?,?,?,?,?)}";
            List<Map<String, Object>> findingMap = jdbcTemplate.queryForList(procName, new Object[]{
                    remediationDto.getClientEngagementDTO().getEngagementCode(),
                    remediationDto.getOrgSchema(),
                    remediationDto.getSelSeverity(),
                    remediationDto.getSelSource(),
                    remediationDto.getSelService(),
                    remediationDto.getSelOsCategory(),
                    remediationDto.getSelVulCategory()
                });
            findingList = new ArrayList<>(findingMap.size());

            for (Map<String, Object> finding : findingMap) {
                 VulnerabilityDTO vulnerabilityDTO = new VulnerabilityDTO();
                vulnerabilityDTO.setInstanceIdentifier(finding.get("CLNT_VULN_INSTC_KEY") + "");
                vulnerabilityDTO.setEncInstanceIdentifier(encDecrypt.encrypt(finding.get("CLNT_VULN_INSTC_KEY") + ""));
                vulnerabilityDTO.setVulnerabilityName((String) finding.get("VULN_NM"));
                vulnerabilityDTO.setIpAddress((String) finding.get("IPADR"));

                CVEInformationDTO cveInformationDTO = new CVEInformationDTO();
                if (finding.get("CVE_ID") != null) {
                    cveInformationDTO.setCveIdentifier(finding.get("CVE_ID").toString().toUpperCase());
                } else {
                    cveInformationDTO.setCveIdentifier("");
                }

                vulnerabilityDTO.setCveInformationDTO(cveInformationDTO);
                
                vulnerabilityDTO.setAppURL((String) finding.get("APPL_URL"));
                findingList.add(vulnerabilityDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : findingLookup : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "findingLookup() :: " + e.getMessage());
        }
        logger.info("End: RemediationDAOImpl : findingLookup");
        return findingList;
    }

    @Override
    public List<MasterLookUpDTO> fetchOSCatList() throws AppException {
        logger.info("Start: RemediationDAOImpl : fetchOSCatList");
        List<MasterLookUpDTO> severityList = null;
        try {
            String procName = "{CALL RMTDNListOSCATGY()}";
            List<Map<String, Object>> osCatMap = jdbcTemplate.queryForList(procName);
            severityList = new ArrayList<>(osCatMap.size());

            for (Map<String, Object> osCategory : osCatMap) {
                MasterLookUpDTO masterLookUpDTO = new MasterLookUpDTO();
                masterLookUpDTO.setMasterLookupKey(Long.parseLong(osCategory.get("OS_CATGY_KEY")+""));
                masterLookUpDTO.setLookUpEntityName((String) osCategory.get("OS_CATGY_NM"));
                severityList.add(masterLookUpDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : fetchOSCatList : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "fetchOSCatList() :: " + e.getMessage());
        }
        logger.info("End: RemediationDAOImpl : fetchOSCatList");
        return severityList;
    }

    @Override
    public List<VulnerabilityDTO> findingsByMultipleIds(RemediationDTO remediationDto) throws AppException {
        logger.info("Start: RemediationDAOImpl : findingsByMultipleIds");
        List<VulnerabilityDTO> findingList = null;
        try {
            String procName = "{CALL RMTDN_FindingDetailsByID(?,?,?)}";
            List<Map<String, Object>> findingMap = jdbcTemplate.queryForList(procName, new Object[]{
                    remediationDto.getClientEngagementDTO().getEngagementCode(),
                    remediationDto.getSelectedInstances(),
                    remediationDto.getOrgSchema()
                });
            findingList = new ArrayList<>(findingMap.size());

            for (Map<String, Object> finding : findingMap) {
                VulnerabilityDTO vulnerabilityDTO = new VulnerabilityDTO();
                vulnerabilityDTO.setInstanceIdentifier(finding.get("CLNT_VULN_INSTC_KEY") + "");
                vulnerabilityDTO.setEncInstanceIdentifier(encDecrypt.encrypt(finding.get("CLNT_VULN_INSTC_KEY") + ""));
                vulnerabilityDTO.setVulnerabilityName((String) finding.get("VULN_NM"));
                vulnerabilityDTO.setIpAddress(finding.get("IPADR") == null?"":finding.get("IPADR")+"");
                vulnerabilityDTO.setAppURL(finding.get("APPL_URL") == null?"":finding.get("APPL_URL")+"");
                vulnerabilityDTO.getCveInformationDTO().setSeverityName(finding.get("VULN_SEV_NM")+"");
                vulnerabilityDTO.setSoftwareName(finding.get("SFTW_NM") == null?"":finding.get("SFTW_NM")+""); //Application Name
                vulnerabilityDTO.setCreatedDate(dateUtil.dateConvertionFromDBToJSP(finding.get("VULN_CREAT_DT") + ""));
                vulnerabilityDTO.setClosedDate(dateUtil.dateConvertionFromDBToJSP(finding.get("VULN_CLOS_DT") + ""));
                vulnerabilityDTO.setRootCauseName(finding.get("VULN_CATGY_NM") == null?"":finding.get("VULN_CATGY_NM")+""); //Vulnerability category name
                findingList.add(vulnerabilityDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : findingsByMultipleIds : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "findingsByMultipleIds() :: " + e.getMessage());
        }
        logger.info("End: RemediationDAOImpl : findingsByMultipleIds");
        return findingList;
    }

    @Override
    public int saveActionPlanInfo(RemediationDTO remediationDto, UserProfileDTO sessionDTO) throws AppException {
        logger.info("Start: RemediationDAOImpl : addRemediationPlan");
        int planId = 0;
        try {
            int rowStatusKey = ApplicationConstants.DB_ROW_STATUS_ACTIVE_VALUE;
            String insertFlag = "I";
            String procName = "{CALL RMDTN_INS_CLNT_RMDTN_PLN(?,?,?,?,?,?,?,?,?,?,"
                    + "?,?,?,?,?,?,?,?,?)}";
            planId = jdbcTemplate.queryForObject(procName,
                    new Object[]{0,
                        rowStatusKey,
                        remediationDto.getClientEngagementDTO().getClientID(),
                        remediationDto.getClientEngagementDTO().getEngagementCode(),
                        "0".equalsIgnoreCase(remediationDto.getRemediationOwnerID())?null:remediationDto.getRemediationOwnerID(),
                        "O",//Default status is OPEN 
                        remediationDto.getPlanTitle(),
                        remediationDto.getPlanDetails(),
                        dateUtil.dateConvertionFromJSPToDB(dateUtil.generateMailCurrentDate()),
                        null,
                        StringUtils.isEmpty(remediationDto.getStartDate())?null:remediationDto.getStartDate(),
                        StringUtils.isEmpty(remediationDto.getEndDate())?null:remediationDto.getEndDate(),
                        null,
                        remediationDto.getFindingsCount(),
                        "0", //Default closed findings ccout is 0(zero)
                        sessionDTO.getUserProfileKey(),
                        insertFlag,
                        remediationDto.getOrgSchema(),
                        remediationDto.getPlanAdjSeverityCode()
                    }, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : addRemediationPlan : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "addRemediationPlan() :: " + e.getMessage());
        }
        logger.info("End: RemediationDAOImpl : addRemediationPlan");
        return planId;
    }

    @Override
    public List<RemediationDTO> remediationPlanList(String engagementCode, String orgSchema, String flag,UserProfileDTO sessionDTO) throws AppException {
        logger.info("Start: RemediationDAOImpl : remediationPlanList");
        List<RemediationDTO> remediationList = null;
        try {
            String procName = "{CALL RMDTN_GetPlanListByEngmt(?,?,?,?)}";
            List<Map<String, Object>> remediationMap = jdbcTemplate.queryForList(procName, new Object[]{
                    engagementCode, orgSchema, sessionDTO.getUserProfileKey(), flag
                });
            remediationList = new ArrayList<>(remediationMap.size());

            for (Map<String, Object> remediation : remediationMap) {
                RemediationDTO remediationDTO = new RemediationDTO();
                remediationDTO.setPlanID(remediation.get("CLNT_RMDTN_PLN_KEY")+"");
                remediationDTO.setPlanTitle(remediation.get("RMDTN_PLN_NM")+"");
                remediationDTO.setPlanStatus(remediation.get("RMDTN_PLN_STS_NM")+"");
                remediationDTO.setCreatedDate(remediation.get("RMDTN_PLN_CREAT_DT") == null?"":dateUtil.dateConvertionFromDBToJSP(remediation.get("RMDTN_PLN_CREAT_DT")+""));
                remediationDTO.setNotifyDate(remediation.get("RMDTN_PLN_NTF_DT") == null?"":dateUtil.dateConvertionFromDBToJSP(remediation.get("RMDTN_PLN_NTF_DT")+""));
                remediationDTO.setDaysOpen(remediation.get("DAYSOPEN") == null?"":remediation.get("DAYSOPEN")+"");
                remediationDTO.setFindingsCount(remediation.get("ASSOC_VULN_TOT_CNT")+"");
                remediationDTO.setCompletion(remediation.get("Completion")+"");
                remediationList.add(remediationDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : remediationPlanList : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "remediationPlanList() :: " + e.getMessage());
        }
        logger.info("End: RemediationDAOImpl : remediationPlanList");
        return remediationList;
    }

    @Override
    public int saveRemediatePlanFindings(RemediationDTO remediationDto, UserProfileDTO sessionDTO) throws AppException {
        logger.info("Start: RemediationDAOImpl : saveRemediatePlanFindings");
        int planFindId = 0;
        try {
            List<String> assocFindingIds = new ArrayList(Arrays.asList(remediationDto.getSelectedInstances().split("\\s*,\\s*")));
            
            int rowStatusKey = ApplicationConstants.DB_ROW_STATUS_ACTIVE_VALUE;
            String insertFlag = "I";
            String procName = "{CALL RMDTN_INS_CLNT_VULN_RMDTN_PLN_ITM(?,?,?,?,?,?,?,?)}";
            
            for(String instanceId:assocFindingIds){
                planFindId = jdbcTemplate.queryForObject(procName,
                        new Object[]{
                            instanceId,
                            remediationDto.getPlanID(),
                            "O",
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
    public int savePlanNotes(RemediationDTO remediationDto, UserProfileDTO sessionDTO) throws AppException {
        logger.info("Start: RemediationDAOImpl : savePlanNotes");
        int planFindId = 0;
        try {
            
            String insertFlag = "I";
            String procName = "{CALL RMDTN_INS_CLNT_RMDTN_PLN_COMMT(?,?,?,?,?,?,?)}";
            
            for(String planNote:remediationDto.getPlanNotes()){
                String[] planNoteArr = planNote.split("-", 2);
                planFindId = jdbcTemplate.queryForObject(procName,
                        new Object[]{
                            0,
                            remediationDto.getPlanID(),
                            dateUtil.dateConvertionFromJSPToDB(planNoteArr[0]),
                            planNoteArr[1],
                            sessionDTO.getUserProfileKey(),
                            insertFlag,
                            remediationDto.getOrgSchema()
                        }, Integer.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : savePlanNotes : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "savePlanNotes() :: " + e.getMessage());
        }
        logger.info("End: RemediationDAOImpl : savePlanNotes");
        return planFindId;
    }

    @Override
    public RemediationDTO planInfoByPlanKey(String planKey, String orgSchema) throws AppException {
        logger.info("Start: RemediationDAOImpl : planInfoByPlanKey");
        RemediationDTO remediationDTO = null;
        try {
            String procName = "{CALL RMDTN_GetPlanInfoByKey(?,?)}";
            List<Map<String, Object>> planMap = jdbcTemplate.queryForList(procName, new Object[]{
                planKey, orgSchema
            });

            for (Map<String, Object> planInfo : planMap) {
                remediationDTO = new RemediationDTO();
                remediationDTO.setPlanID(planInfo.get("CLNT_RMDTN_PLN_KEY")+"");
                remediationDTO.setPlanTitle(planInfo.get("RMDTN_PLN_NM")+"");
                remediationDTO.setPlanStatus(planInfo.get("RMDTN_PLN_STS_NM")+"");
                remediationDTO.setPlanDetails(planInfo.get("RMDTN_PLN_DESC") == null?"":planInfo.get("RMDTN_PLN_DESC")+"");
                remediationDTO.setStartDate(planInfo.get("RMDTN_PLN_STRT_DT") == null?"":dateUtil.dateConvertionFromDBToJSP(planInfo.get("RMDTN_PLN_STRT_DT")+""));
                remediationDTO.setEndDate(planInfo.get("RMDTN_PLN_DUE_DT") == null?"":dateUtil.dateConvertionFromDBToJSP(planInfo.get("RMDTN_PLN_DUE_DT")+""));
                remediationDTO.setCreatedDate(planInfo.get("RMDTN_PLN_CREAT_DT") == null?"":dateUtil.dateConvertionFromDBToJSP(planInfo.get("RMDTN_PLN_CREAT_DT")+""));
                remediationDTO.setNotifyDate(planInfo.get("RMDTN_PLN_NTF_DT") == null?"":dateUtil.dateConvertionFromDBToJSP(planInfo.get("RMDTN_PLN_NTF_DT")+""));
                remediationDTO.setClosedDate(planInfo.get("RMDTN_PLN_CLOS_DT") == null?"":dateUtil.dateConvertionFromDBToJSP(planInfo.get("RMDTN_PLN_CLOS_DT")+""));
                remediationDTO.setRemediationOwnerID(planInfo.get("RMDTN_OWNR_USER_ID")+"");
                remediationDTO.setPlanAdjSeverityCode(planInfo.get("ADJ_RMDTN_PLN_SEV_CD")+""); 
                remediationDTO.setPlanAdjSeverityValue(planInfo.get("Adj")+""); //Adjacent Severity Value
                remediationDTO.setPlanSeverityValue(planInfo.get("PlnSEV") == null?"":planInfo.get("PlnSEV")+""); //Plan Severity
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : planInfoByPlanKey : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "planInfoByPlanKey() :: " + e.getMessage());
        }
        logger.info("End: RemediationDAOImpl : planInfoByPlanKey");
        return remediationDTO;
    }

    @Override
    public List<RemediationDTO> planNotesByPlanKey(String planKey, String orgSchema) throws AppException {
        logger.info("Start: RemediationDAOImpl : planNotesByPlanKey");
        List<RemediationDTO> planNotesList = null;
        try {
            String procName = "{CALL RMDTN_GetCOMMTByPlanKey(?,?)}";
            List<Map<String, Object>> planMap = jdbcTemplate.queryForList(procName, new Object[]{
                planKey, orgSchema
            });
            planNotesList = new ArrayList<>(planMap.size());
            for (Map<String, Object> planInfo : planMap) {
                RemediationDTO remediationDTO = new RemediationDTO();
                remediationDTO.setPlanComment(planInfo.get("COMMT_TXT")+"");
                remediationDTO.setCommentDate(planInfo.get("ENT_DT") == null?"":dateUtil.dateConvertionFromDBToJSP(planInfo.get("ENT_DT")+""));
                remediationDTO.setCreatedByUser(planInfo.get("Username")+"");
                planNotesList.add(remediationDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : planNotesByPlanKey : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "planNotesByPlanKey() :: " + e.getMessage());
        }
        logger.info("End: RemediationDAOImpl : planNotesByPlanKey");
        return planNotesList;
    }

    @Override
    public List<VulnerabilityDTO> findingsByPlanKey(String planKey, String orgSchema) throws AppException {
        logger.info("Start: RemediationDAOImpl : findingsByPlanKey");
        List<VulnerabilityDTO> findingList = null;
        try {
            String procName = "{CALL RMDTN_GetFindingsByPlanKey(?,?)}";
            List<Map<String, Object>> findingMap = jdbcTemplate.queryForList(procName, new Object[]{
                    planKey, orgSchema
                });
            findingList = new ArrayList<>(findingMap.size());

            for (Map<String, Object> finding : findingMap) {
                VulnerabilityDTO vulnerabilityDTO = new VulnerabilityDTO();
                vulnerabilityDTO.setInstanceIdentifier(finding.get("CLNT_VULN_INSTC_KEY") + "");
                vulnerabilityDTO.setEncInstanceIdentifier(encDecrypt.encrypt(finding.get("CLNT_VULN_INSTC_KEY") + ""));
                vulnerabilityDTO.setVulnerabilityName((String) finding.get("VULN_NM"));
                vulnerabilityDTO.setIpAddress(finding.get("IPADR") == null?"":finding.get("IPADR")+"");
                vulnerabilityDTO.setAppURL(finding.get("APPL_URL") == null?"":finding.get("APPL_URL")+"");
                vulnerabilityDTO.getCveInformationDTO().setSeverityName(finding.get("VULN_SEV_NM")+"");
                vulnerabilityDTO.setSoftwareName(finding.get("SFTW_NM") == null?"":finding.get("SFTW_NM")+""); //Application Name
                vulnerabilityDTO.setCreatedDate(dateUtil.dateConvertionFromDBToJSP(finding.get("CREAT_DT") + ""));
                vulnerabilityDTO.setClosedDate(dateUtil.dateConvertionFromDBToJSP(finding.get("CLOS_DT") + ""));
                vulnerabilityDTO.setRootCauseName(finding.get("VULN_CATGY_NM") == null?"":finding.get("VULN_CATGY_NM")+""); //Vulnerability category name
                vulnerabilityDTO.setStatusName(finding.get("VULN_INSTC_STS_NM") == null?"":finding.get("VULN_INSTC_STS_NM")+"");
                vulnerabilityDTO.setStatusCode(finding.get("VULN_INSTC_STS_CD") == null?"":finding.get("VULN_INSTC_STS_CD")+"");
                findingList.add(vulnerabilityDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : findingsByPlanKey : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "findingsByPlanKey() :: " + e.getMessage());
        }
        logger.info("End: RemediationDAOImpl : findingsByPlanKey");
        return findingList;
    }

    @Override
    public int updateActionPlanInfo(RemediationDTO remediationDto, UserProfileDTO sessionDTO) throws AppException {
        logger.info("Start: RemediationDAOImpl : updateActionPlanInfo");
        int planId = 0;
        try {
            int rowStatusKey = ApplicationConstants.DB_ROW_STATUS_ACTIVE_VALUE;
            String insertFlag = "U";
            String procName = "{CALL RMDTN_INS_CLNT_RMDTN_PLN(?,?,?,?,?,?,?,?,?,?,"
                    + "?,?,?,?,?,?,?,?,?)}";
            planId = jdbcTemplate.queryForObject(procName,
                    new Object[]{remediationDto.getPlanID(),
                        rowStatusKey,
                        remediationDto.getClientEngagementDTO().getClientID(),
                        remediationDto.getClientEngagementDTO().getEngagementCode(),
                        "0".equalsIgnoreCase(remediationDto.getRemediationOwnerID())?null:remediationDto.getRemediationOwnerID(),
                        "O",//Default status is OPEN 
                        remediationDto.getPlanTitle(),
                        remediationDto.getPlanDetails(),
                        null,
                        null,
                        StringUtils.isEmpty(remediationDto.getStartDate())?null:remediationDto.getStartDate(),
                        StringUtils.isEmpty(remediationDto.getEndDate())?null:remediationDto.getEndDate(),
                        null,
                        remediationDto.getFindingsCount(),
                        "0", //Default closed findings ccout is 0(zero)
                        sessionDTO.getUserProfileKey(),
                        insertFlag,
                        remediationDto.getOrgSchema(),
                        remediationDto.getPlanAdjSeverityCode()
                    }, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : updateActionPlanInfo : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "updateActionPlanInfo() :: " + e.getMessage());
        }
        logger.info("End: RemediationDAOImpl : updateActionPlanInfo");
        return planId;
    }

    @Override
    public int deletePlanFindings(RemediationDTO remediationDto) throws AppException {
        logger.info("Start: RemediationDAOImpl : deletePlanFindings");
        int deleteId = 0;
        try {
            List<String> removeFindingIds = new ArrayList(Arrays.asList(remediationDto.getRemoveInstances().split("\\s*,\\s*")));
            
            String deleteFlag = "D";
            String procName = "{CALL RMDTN_INS_CLNT_VULN_RMDTN_PLN_ITM(?,?,?,?,?,?,?,?)}";
            
            for(String instanceId:removeFindingIds){
                deleteId = jdbcTemplate.queryForObject(procName,
                        new Object[]{
                            instanceId,
                            remediationDto.getPlanID(),
                            ApplicationConstants.DB_CONSTANT_PLAN_ITEM_STATUS_OPEN,
                            null,
                            0,
                            0,
                            deleteFlag,
                            remediationDto.getOrgSchema()
                        }, Integer.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : deletePlanFindings : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "deletePlanFindings() :: " + e.getMessage());
        }
        logger.info("End: RemediationDAOImpl : deletePlanFindings");
        return deleteId;
    }

    @Override
    public int saveRiskRegistryInfo(RemediationDTO remediationDto, UserProfileDTO sessionDTO) throws AppException {
        logger.info("Start: RemediationDAOImpl : saveRiskRegistryInfo");
        int planId = 0;
        try {
            int rowStatusKey = ApplicationConstants.DB_ROW_STATUS_ACTIVE_VALUE;
            String insertFlag = "I";
            String procName = "{CALL RMDTN_INS_CLNT_RISK_RGST(?,?,?,?,?,?,?,?,?,?,"
                    + "?,?,?,?,?,?,?)}";
            planId = jdbcTemplate.queryForObject(procName,
                    new Object[]{0,
                        rowStatusKey,
                        remediationDto.getPlanID(),
                        null,//remediationDto.getAdjSeverityCode(),
                        sessionDTO.getUserProfileKey(),
                        dateUtil.generateDBCurrentDateTime(),// Entered Date
                        null,//remediationDto.getCompensationCtrl() == null?"":remediationDto.getCompensationCtrl(),
                        remediationDto.getNotifyDate(),
                        null,
                        null,
                        null,
                        null,
                        null,
                        0,
                        sessionDTO.getUserProfileKey(),
                        insertFlag,
                        remediationDto.getOrgSchema()
                    }, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : saveRiskRegistryInfo : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "saveRiskRegistryInfo() :: " + e.getMessage());
        }
        logger.info("End: RemediationDAOImpl : saveRiskRegistryInfo");
        return planId;
    }
    
    @Override
    public int saveRiskRigistryFindings(RemediationDTO remediationDto, UserProfileDTO sessionDTO, String riskId) throws AppException {
        logger.info("Start: RemediationDAOImpl : saveRiskRigistryFindings");
        int riskItemId = 0;
        try {
            List<String> assocFindingIds = new ArrayList(Arrays.asList(remediationDto.getRemoveInstances().split("\\s*,\\s*")));
            
            String insertFlag = "I";
            String procName = "{CALL RMDTN_INS_CLNT_RISK_RGST_ITM(?,?,?,?,?,?,?)}";
            
            for(String instanceId:assocFindingIds){
                try{
                riskItemId = jdbcTemplate.queryForObject(procName,
                        new Object[]{
                            riskId,
                            instanceId,
                            sessionDTO.getUserProfileKey(),
                            insertFlag,
                            remediationDto.getOrgSchema(),
                            remediationDto.getAdjSeverityCode(),
                            remediationDto.getCompensationCtrl()
                        }, Integer.class);
                }catch(Exception e){
                    
                }
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

    @Override
    public int deleteRemediationPlan(RemediationDTO remediationDto) throws AppException {
        logger.info("Start: RemediationDAOImpl : deleteRemediationPlan");
        int deleteId = 0;
        try {
            List<String> removePlanIds = new ArrayList(Arrays.asList(remediationDto.getRemoveInstances().split("\\s*,\\s*")));
            
            String procName = "{CALL RMDTN_DEL_RMDTN_PLN(?,?)}";
            
            for(String instanceId:removePlanIds){
                deleteId = jdbcTemplate.queryForObject(procName,
                        new Object[]{
                            instanceId,
                            remediationDto.getOrgSchema()
                        }, Integer.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : deletePlanFindings : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "deletePlanFindings() :: " + e.getMessage());
        }
        logger.info("End: RemediationDAOImpl : deletePlanFindings");
        return deleteId;
    }

    @Override
    public List<UserProfileDTO> remediationOwnerUsers(String flag, String engagementCode) throws AppException {
        List<UserProfileDTO> usersList = new ArrayList<>();
        String procName = "{CALL RMDTN_ListOwnerUsers(?,?)}";
        try
        {
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName,
                    new Object[]{flag,engagementCode});
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    UserProfileDTO userDTO = new UserProfileDTO();
                    userDTO.setFirstName((String)(resultMap.get("User Name")));
                    userDTO.setUserProfileKey(Long.parseLong(resultMap.get("USER_ID")+""));
                    usersList.add(userDTO);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
            logger.debug("Exceptionoccured : remediationOwnerUsers : "+e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "remediationOwnerUsers() :: "+e.getMessage());
        }
        return usersList;
    }
    
    @Override
    public int updatePlanItemStatus(RemediationDTO remediationDto,UserProfileDTO sessionDTO) throws AppException {
        logger.info("Start: RemediationDAOImpl : updatePlanItemStatus");
        int retVal = 0;
        try {
            List<String> removeFindingIds = new ArrayList(Arrays.asList(remediationDto.getRemoveInstances().split("\\s*,\\s*")));

            String procName = "{CALL RMDTN_UpdateFindingRmdtnStatus(?,?,?,?,?,?)}";
            
            for(String instanceId:removeFindingIds){
                retVal = jdbcTemplate.queryForObject(procName,
                        new Object[]{
                            instanceId,
                            remediationDto.getStatusCode(),
                            remediationDto.getClosedComments(),
                            sessionDTO.getUserProfileKey(),
                            remediationDto.getOrgSchema(),
                            ApplicationConstants.DB_CONSTANT_PLAN_ITEM_STATUS_CLOSED.equalsIgnoreCase(remediationDto.getStatusCode())?dateUtil.dateConvertionFromJSPToDB(dateUtil.generateMailCurrentDate()):null
                        }, Integer.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : updatePlanItemStatus : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "updatePlanItemStatus() :: " + e.getMessage());
        }
        logger.info("End: RemediationDAOImpl : updatePlanItemStatus");
        return retVal;
    }

    @Override
    public String getRegistryKeyByPlanKey(RemediationDTO remediationDto) throws AppException {
        logger.info("Start: RemediationDAOImpl : getRegistryKeyByPlanKey");
       String registryKey = null;
        try {
            String procName = "{CALL RMDTN_GetRegistKyeByPlanKey(?,?)}";
            List<Map<String, Object>> registryMap = jdbcTemplate.queryForList(procName, new Object[]{
                remediationDto.getPlanID(), remediationDto.getOrgSchema()
            });
            for (Map<String, Object> registry : registryMap) {
                registryKey = registry.get("CLNT_RISK_RGST_KEY")+"";
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : getRegistryKeyByPlanKey : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "getRegistryKeyByPlanKey() :: " + e.getMessage());
        }
        logger.info("End: RemediationDAOImpl : getRegistryKeyByPlanKey");
        return registryKey;
    }

    @Override
    public int updateRemediationNotifyDate(RemediationDTO remediationDto,UserProfileDTO sessionDTO) throws AppException {
        logger.info("Start: RemediationDAOImpl : updateRemediationNotifyDate");
        int retVal = 0;
        try {
            String procName = "{CALL RMDTN_UpdateRmdtnNtfDate(?,?,?)}";
            
            retVal = jdbcTemplate.queryForObject(procName,
                    new Object[]{
                        remediationDto.getPlanID(),
                        sessionDTO.getUserProfileKey(),
                        remediationDto.getOrgSchema()
                    }, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : updateRemediationNotifyDate : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "updateRemediationNotifyDate() :: " + e.getMessage());
        }
        logger.info("End: RemediationDAOImpl : updateRemediationNotifyDate");
        return retVal;
    }

    @Override
    public RemediationDTO planItemInfoByInstKey(RemediationDTO remediationDto) throws AppException {
        logger.info("Start: RemediationDAOImpl : planItemInfoByInstKey");
        RemediationDTO remediationDTO = null;
        try {
            String procName = "{CALL RMDTN_GetRmdPlanitmByInstcKey(?,?)}";
            List<Map<String, Object>> planItemMap = jdbcTemplate.queryForList(procName, new Object[]{
                remediationDto.getInstanceIdentifier(), remediationDto.getOrgSchema()
            });

            for (Map<String, Object> planItemInfo : planItemMap) {
                remediationDTO = new RemediationDTO();
                remediationDTO.setPlanID(planItemInfo.get("CLNT_RMDTN_PLN_KEY")+"");
                remediationDTO.setStatusCode(planItemInfo.get("VULN_INSTC_STS_CD")+"");
                remediationDTO.setClosedDate(planItemInfo.get("CLOS_DT") == null?"":dateUtil.dateConvertionFromDBToJSP(planItemInfo.get("CLOS_DT")+""));
                remediationDTO.setClosedComments(planItemInfo.get("CLOS_RSN_TXT") == null?"":planItemInfo.get("CLOS_RSN_TXT")+"");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : planItemInfoByInstKey : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "planItemInfoByInstKey() :: " + e.getMessage());
        }
        logger.info("End: RemediationDAOImpl : planItemInfoByInstKey");
        return remediationDTO;
    }
    
    @Override
    public List<MasterLookUpDTO> fetchRemediationStatus() throws AppException {
        logger.info("Start: RemediationDAOImpl : fetchRemediationStatus");
        List<MasterLookUpDTO> remStatusList = null;
        try {
            String procName = "{CALL RMDTN_VULN_RMDTN_STS()}";
            List<Map<String, Object>> statusMap = jdbcTemplate.queryForList(procName);
            remStatusList = new ArrayList<>(statusMap.size());

            for (Map<String, Object> severity : statusMap) {
                MasterLookUpDTO masterLookUpDTO = new MasterLookUpDTO();
                masterLookUpDTO.setMasterLookupCode(severity.get("VULN_RMDTN_STS_CD") + "");
                masterLookUpDTO.setLookUpEntityName((String) severity.get("VULN_RMDTN_STS_NM"));
                remStatusList.add(masterLookUpDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : fetchRemediationStatus : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "fetchRemediationStatus() :: " + e.getMessage());
        }
        logger.info("End: RemediationDAOImpl : fetchRemediationStatus");
        return remStatusList;
    }

    @Override
    public int updatePlanStatus(RemediationDTO remediationDto, UserProfileDTO sessionDTO, String flag) throws AppException {
        logger.info("Start: RemediationDAOImpl : updatePlanStatus");
        int retVal = 0;
        try {
            String procName = "{CALL RMDTN_UpdatePlanStatus(?,?,?,?)}";
            
            retVal = jdbcTemplate.queryForObject(procName,
                    new Object[]{
                        remediationDto.getPlanID(),
                        sessionDTO.getUserProfileKey(),
                        remediationDto.getOrgSchema(),
                        flag
                    }, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : updatePlanStatus : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "updatePlanStatus() :: " + e.getMessage());
        }
        logger.info("End: RemediationDAOImpl : updatePlanStatus");
        return retVal;
    }
    
    @Override
    public String generateRemdiationSeverity(RemediationDTO remediationDto) throws AppException {
        logger.info("Start: RemediationDAOImpl : generateRemdiationSeverity");
       String planSeverity = null;
        try {
            String procName = "{CALL RMDTN_GetPlanSeverity(?,?,?)}";
            List<Map<String, Object>> severityMap = jdbcTemplate.queryForList(procName, 
                    new Object[]{
                        remediationDto.getClientEngagementDTO().getEngagementCode(),
                        remediationDto.getOrgSchema(),
                        remediationDto.getPlanID()
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
            logger.debug("Exceptionoccured : generateRemdiationSeverity : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "generateRemdiationSeverity() :: " + e.getMessage());
        }
        logger.info("End: RemediationDAOImpl : generateRemdiationSeverity");
        return planSeverity;
    }
    
    @Override
    public int updatePlanSeverity(RemediationDTO remediationDto, UserProfileDTO sessionDTO, String planSeverity) throws AppException {
        logger.info("Start: RemediationDAOImpl : updatePlanSeverity");
        int retVal = 0;
        try {
            String procName = "{CALL RMDTN_UPD_CLNT_RMDTN_PLN_SEV(?,?,?,?,?)}";
            
            retVal = jdbcTemplate.queryForObject(procName,
                    new Object[]{
                        remediationDto.getPlanID(),
                        ApplicationConstants.DB_INDICATOR_UPDATE,
                        remediationDto.getOrgSchema(),
                        planSeverity,
                        sessionDTO.getUserProfileKey()
                    }, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : updatePlanSeverity : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "updatePlanSeverity() :: " + e.getMessage());
        }
        logger.info("End: RemediationDAOImpl : updatePlanSeverity");
        return retVal;
    }
    
}
