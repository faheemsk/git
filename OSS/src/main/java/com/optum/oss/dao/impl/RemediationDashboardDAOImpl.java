/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao.impl;

import com.optum.oss.dao.RemediationDashboardDAO;
import com.optum.oss.dto.AssessmentFindingsCountDTO;
import com.optum.oss.dto.RemediationDashboardDTO;
import com.optum.oss.dto.RiskRegistryDashboardDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.util.DateUtil;
import com.optum.oss.util.EncryptDecrypt;
import java.math.BigDecimal;
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
public class RemediationDashboardDAOImpl implements RemediationDashboardDAO {

    public static final Logger logger = Logger.getLogger(RemediationDashboardDAOImpl.class);

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

    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private EncryptDecrypt encryptDecrypt;

    @Override
    public AssessmentFindingsCountDTO fetchRemediationDashBoardFindingCount(String engCode, String engSchema,long userID) throws AppException {
        logger.info("Start: RemediationDashboardDAOImpl: fetchRemediationDashBoardFindingCount Method");
        AssessmentFindingsCountDTO findingCountDTO = null;
        try {
            String procName = "{CALL RMDTN_ReportRemediation(?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName,
                    new Object[]{engCode, engSchema, userID
                    });
            if (null != resultList && resultList.size() > 0) {
                for (Map<String, Object> resultMap : resultList) {
                    findingCountDTO = new AssessmentFindingsCountDTO();
                    findingCountDTO.setCriticalCount((Integer) resultMap.get("Critical"));
                    findingCountDTO.setHighCount((Integer) resultMap.get("High"));
                    findingCountDTO.setMediumCount((Integer) resultMap.get("Medium"));
                    findingCountDTO.setLowCount((Integer) resultMap.get("Low"));
                    findingCountDTO.setRemediationPlanCount((Integer) resultMap.get("ActionPlan"));
                    findingCountDTO.setTotalCount(resultMap.get("Instances") == null?0:(Integer) resultMap.get("Instances"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : RemediationDashboardDAOImpl: fetchRemediationDashBoardFindingCount(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "RemediationDashboardDAOImpl: fetchRemediationDashBoardFindingCount(): " + e.getMessage());
        }

        logger.info("End: RemediationDashboardDAOImpl: fetchRemediationDashBoardFindingCount Method");
        return findingCountDTO;
    }

    @Override
    public RemediationDashboardDTO remediationPlanCompletion(String engCode, final String engSchema,final long userID) throws AppException {
        logger.info("Start: RemediationDashboardDAOImpl: remediationPlanCompletion Method");
        RemediationDashboardDTO registryDashboardDTO = null;
        try {
            String procName = "{CALL [RMDTN_ReportRemediationComplete](?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName,
                    new Object[]{engCode, engSchema, userID
                    });
            if (null != resultList && resultList.size() > 0) {
                for (Map<String, Object> resultMap : resultList) {
                    registryDashboardDTO = new RemediationDashboardDTO();
                    registryDashboardDTO.setPlanStatus_Closed(((BigDecimal) resultMap.get("CLOSED")).intValue());
                    registryDashboardDTO.setPlanStatus_Open(((BigDecimal) resultMap.get("OPEN")).intValue());
                    registryDashboardDTO.setPlanStatus_Inprogress(((BigDecimal) resultMap.get("Inprogress")).intValue());
                    registryDashboardDTO.setOpenCount((Integer) resultMap.get("OpenCount"));
                    registryDashboardDTO.setInprogressCount((Integer) resultMap.get("InprogressCount"));
                    registryDashboardDTO.setClosedCount((Integer) resultMap.get("CloseCount"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : RemediationDashboardDAOImpl: remediationPlanCompletion(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "RemediationDashboardDAOImpl: remediationPlanCompletion(): " + e.getMessage());
        }
        return registryDashboardDTO;
    }

    @Override
    public List<RemediationDashboardDTO> remediationOwnerDataByPlanStatus(String engCode, final String engSchema,final long userID) throws AppException {
        logger.info("Start: RemediationDashboardDAOImpl: registryDataByOwner Method");
        List<RemediationDashboardDTO> remediationList = null;
        try {
            String procName = "{CALL [RMDTN_ReportRemediationOwner](?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName,
                    new Object[]{engCode, engSchema,userID
                    });
            if (null != resultList && resultList.size() > 0) {
                remediationList = new ArrayList<RemediationDashboardDTO>();
                for (Map<String, Object> resultMap : resultList) {
                    RemediationDashboardDTO remediationDashboardDTO = new RemediationDashboardDTO();
                    remediationDashboardDTO.setPlanOwner((String) resultMap.get("UserName"));
                    remediationDashboardDTO.setPlanStatus_Open((Integer) resultMap.get("Open"));
                    remediationDashboardDTO.setPlanStatus_Inprogress((Integer) resultMap.get("Inprogress"));
                    remediationDashboardDTO.setPlanStatus_Closed((Integer) resultMap.get("Closed"));
                    remediationDashboardDTO.setPlanCount((Integer) resultMap.get("UserCount"));
                    remediationList.add(remediationDashboardDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : RemediationDashboardDAOImpl: remediationOwnerDataByPlanStatus(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "RemediationDashboardDAOImpl: remediationOwnerDataByPlanStatus(): " + e.getMessage());
        }

        logger.info("End: RemediationDashboardDAOImpl: remediationOwnerDataByPlanStatus Method");
        return remediationList;
    }

    @Override
    public List<RemediationDashboardDTO> remediationPlansByStatusAndSeverity(String engCode, final String engSchema,final long userID) throws AppException {
        logger.info("Start: RemediationDashboardDAOImpl: remediationPlansByStatusAndSeverity Method");
        List<RemediationDashboardDTO> remediationList = null;
        try {
            String procName = "{CALL [RMDTN_ReportRemediationSeveirty](?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName,
                    new Object[]{engCode, engSchema, userID
                    });
            if (null != resultList && resultList.size() > 0) {
                remediationList = new ArrayList<RemediationDashboardDTO>();
                for (Map<String, Object> resultMap : resultList) {
                    RemediationDashboardDTO remediationDashboardDTO = new RemediationDashboardDTO();
                    remediationDashboardDTO.setRemediationPlanStatus((String) resultMap.get("RMDTN_PLN_STS_NM"));
                    remediationDashboardDTO.setRiskSeverityCount_Critical((Integer) resultMap.get("Critical"));
                    remediationDashboardDTO.setRiskSeverityCount_High((Integer) resultMap.get("High"));
                    remediationDashboardDTO.setRiskSeverityCount_Medium((Integer) resultMap.get("Medium"));
                    remediationDashboardDTO.setRiskSeverityCount_Low((Integer) resultMap.get("Low"));
                    remediationList.add(remediationDashboardDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : RemediationDashboardDAOImpl: remediationPlansByStatusAndSeverity(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "RemediationDashboardDAOImpl: remediationPlansByStatusAndSeverity(): " + e.getMessage());
        }

        logger.info("End: RemediationDashboardDAOImpl: remediationPlansByStatusAndSeverity Method");
        return remediationList;
    }
    
    @Override
    public List<RemediationDashboardDTO> remediationPlansByDaysAndSeverity(String engCode, final String engSchema,final long userID) throws AppException {
        logger.info("Start: RemediationDashboardDAOImpl: remediationPlansByDaysAndSeverity Method");
        List<RemediationDashboardDTO> remediationList = null;
        try {
            String procName = "{CALL [RMDTN_ReportRemediationNotification](?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName,
                    new Object[]{engCode, engSchema,userID
                    });
            if (null != resultList && resultList.size() > 0) {
                remediationList = new ArrayList<RemediationDashboardDTO>();
                for (Map<String, Object> resultMap : resultList) {
                    RemediationDashboardDTO remediationDashboardDTO = new RemediationDashboardDTO();
                    remediationDashboardDTO.setDaysOpenRange((String) resultMap.get("PlanDuration"));
                    remediationDashboardDTO.setRiskSeverityCount_Critical((Integer) resultMap.get("Critical"));
                    remediationDashboardDTO.setRiskSeverityCount_High((Integer) resultMap.get("High"));
                    remediationDashboardDTO.setRiskSeverityCount_Medium((Integer) resultMap.get("Medium"));
                    remediationDashboardDTO.setRiskSeverityCount_Low((Integer) resultMap.get("Low"));
                    remediationList.add(remediationDashboardDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : RemediationDashboardDAOImpl: remediationPlansByDaysAndSeverity(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "RemediationDashboardDAOImpl: remediationPlansByDaysAndSeverity(): " + e.getMessage());
        }

        logger.info("End: RemediationDashboardDAOImpl: remediationPlansByDaysAndSeverity Method");
        return remediationList;
    }

}
