/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao.impl;

import com.optum.oss.dao.RiskRegistryDashboardDAO;
import com.optum.oss.dto.AssessmentFindingsCountDTO;
import com.optum.oss.dto.RiskRegistryDashboardDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.util.CommonUtil;
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
public class RiskRegistryDashboardDAOImpl implements RiskRegistryDashboardDAO {

    public static final Logger logger = Logger.getLogger(RiskRegistryDashboardDAOImpl.class);

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
    public AssessmentFindingsCountDTO fetchRiskRegistryFindingCount(String engCode, final String engSchema,final long userID) throws AppException {
        logger.info("Start: RiskRegistryDashboardDAOImpl: fetchRiskRegistryFindingCount Method");
        AssessmentFindingsCountDTO findingCountDTO = null;
        try {
            String procName = "{CALL RMDTN_ReportRiksRegistry(?,?,?,?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName,
                    new Object[]{engCode, "", "", "", engSchema,userID
                    });
            if (null != resultList && resultList.size() > 0) {
                for (Map<String, Object> resultMap : resultList) {
                    findingCountDTO = new AssessmentFindingsCountDTO();
                    findingCountDTO.setCriticalCount((Integer) resultMap.get("Critical"));
                    findingCountDTO.setHighCount((Integer) resultMap.get("High"));
                    findingCountDTO.setMediumCount((Integer) resultMap.get("Medium"));
                    findingCountDTO.setLowCount((Integer) resultMap.get("Low"));
                    findingCountDTO.setRiskRegistryCount((Integer) resultMap.get("Risk"));
                    findingCountDTO.setTotalCount((Integer) resultMap.get("items"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : RiskRegistryDashboardDAOImpl: fetchRiskRegistryFindingCount(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "AnalystValidationDAOImpl: fetchAnalystValidationWorklist(): " + e.getMessage());
        }

        logger.info("End: RiskRegistryDashboardDAOImpl: fetchRiskRegistryFindingCount Method");
        return findingCountDTO;
    }

    @Override
    public RiskRegistryDashboardDTO registryDataByResponse(String engCode, final String engSchema,final long userID) throws AppException {
        logger.info("Start: RiskRegistryDashboardDAOImpl: registryDataByResponse Method");
        RiskRegistryDashboardDTO registryDashboardDTO = null;
        try {
            String procName = "{CALL [RMDTN_ReportRiksRegistryResponse](?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName,
                    new Object[]{engCode, engSchema,userID
                    });
            if (null != resultList && resultList.size() > 0) {
                for (Map<String, Object> resultMap : resultList) {
                    registryDashboardDTO = new RiskRegistryDashboardDTO();
                    registryDashboardDTO.setRegistry_Closed(((BigDecimal) resultMap.get("CLOSED")).intValue());
                    registryDashboardDTO.setRegistry_Open(((BigDecimal) resultMap.get("OPEN")).intValue());
                    registryDashboardDTO.setOpenCount((Integer) resultMap.get("Opencount"));
                    registryDashboardDTO.setClosedCount((Integer) resultMap.get("Closecount"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : RiskRegistryDashboardDAOImpl: registryDataByResponse(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "AnalystValidationDAOImpl: registryDataByResponse(): " + e.getMessage());
        }

        logger.info("End: RiskRegistryDashboardDAOImpl: registryDataByResponse Method");
        return registryDashboardDTO;
    }

    @Override
    public List<RiskRegistryDashboardDTO> registryDataByOwner(String engCode, final String engSchema,final long userID) throws AppException {
        logger.info("Start: RiskRegistryDashboardDAOImpl: registryDataByOwner Method");
        List<RiskRegistryDashboardDTO> registryDataOwenrList = null;
        try {
            String procName = "{CALL RMDTN_ReportRiksRegistryOwner(?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName,
                    new Object[]{engCode, engSchema,userID
                    });
            if (null != resultList && resultList.size() > 0) {
                registryDataOwenrList = new ArrayList<RiskRegistryDashboardDTO>();
                for (Map<String, Object> resultMap : resultList) {
                    RiskRegistryDashboardDTO registryDashboardDTO = new RiskRegistryDashboardDTO();
                    registryDashboardDTO.setRegistryOwner((String) resultMap.get("UserName"));
                    registryDashboardDTO.setRiskResponseCount_Accept((Integer) resultMap.get("Accepted"));
                    registryDashboardDTO.setRiskResponseCount_mitigated((Integer) resultMap.get("Mitigate"));
                    registryDashboardDTO.setRiskResponseCount_Transfer((Integer) resultMap.get("Transfer"));
                    registryDashboardDTO.setRiskResponseCount_share((Integer) resultMap.get("Share"));
                    registryDashboardDTO.setRiskResponseCount_avoid((Integer) resultMap.get("Avoid"));
                    registryDashboardDTO.setRegistryCount((Integer) resultMap.get("UserCount"));
                    registryDataOwenrList.add(registryDashboardDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : RiskRegistryDashboardDAOImpl: registryDataByOwner(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "AnalystValidationDAOImpl: registryDataByOwner(): " + e.getMessage());
        }

        logger.info("End: RiskRegistryDashboardDAOImpl: registryDataByOwner Method");
        return registryDataOwenrList;
    }

    @Override
    public List<RiskRegistryDashboardDTO> registryDataBySeverity(String engCode, final String engSchema,final long userID) throws AppException {
        logger.info("Start: RiskRegistryDashboardDAOImpl: registryDataByOwner Method");
        List<RiskRegistryDashboardDTO> registryDataOwenrList = null;
        try {
            String procName = "{CALL [RMDTN_ReportRiksRegistrySeverity](?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName,
                    new Object[]{engCode, engSchema,userID
                    });
            if (null != resultList && resultList.size() > 0) {
                registryDataOwenrList = new ArrayList<RiskRegistryDashboardDTO>();
                for (Map<String, Object> resultMap : resultList) {
                    RiskRegistryDashboardDTO registryDashboardDTO = new RiskRegistryDashboardDTO();
                    registryDashboardDTO.setRegistryOwner((String) resultMap.get("UserName"));
                    registryDashboardDTO.setRiskSeverityCount_Critical((Integer) resultMap.get("Critical"));
                    registryDashboardDTO.setRiskSeverityCount_High((Integer) resultMap.get("High"));
                    registryDashboardDTO.setRiskSeverityCount_Medium((Integer) resultMap.get("Medium"));
                    registryDashboardDTO.setRiskSeverityCount_Low((Integer) resultMap.get("Low"));
                    registryDashboardDTO.setRegistryCount((Integer) resultMap.get("UserCount"));

                    registryDataOwenrList.add(registryDashboardDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : RiskRegistryDashboardDAOImpl: registryDataByOwner(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "AnalystValidationDAOImpl: registryDataByOwner(): " + e.getMessage());
        }

        logger.info("End: RiskRegistryDashboardDAOImpl: registryDataByOwner Method");
        return registryDataOwenrList;
    }

    @Override
    public List<RiskRegistryDashboardDTO> getRiskRegistryData(String engCode, final String engSchema,String sevCode,final long userID) throws AppException {
        logger.info("Start: RoadMapDAOImpl : fetchRoadmapCategory");
        List<RiskRegistryDashboardDTO> registryList = null;
        try {
            registryList = new ArrayList<>();

            String proc = "{CALL [RMDTN_ReportRegistryDetails](?,?,?,?)}";
            List<Map<String, Object>> registryMap = jdbcTemplate.queryForList(proc,
                    new Object[]{engCode, engSchema, sevCode,userID});
            for (Map<String, Object> registryData : registryMap) {
                RiskRegistryDashboardDTO registryDTO = new RiskRegistryDashboardDTO();

                registryDTO.setRiskRegisterId( registryData.get("CLNT_RISK_RGST_KEY")+"");
                registryDTO.setPlanTitle(registryData.get("RMDTN_PLN_NM") + "");
                registryDTO.setPlanStatus(registryData.get("RMDTN_PLN_STS_NM") + "");
                registryDTO.setCompletion(registryData.get("Completion")+"%");
                registryDTO.setDaysOpen(registryData.get("Days Open") == null ? "" : registryData.get("Days Open") + "");
                registryDTO.setInstances( registryData.get("Instances")+"");
                registryList.add(registryDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : getRiskRegistryData : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "getRiskRegistryData() :: " + e.getMessage());
        }
        logger.info("End: RoadMapDAOImpl : getRiskRegistryData");
        return registryList;
    }

    @Override
    public List riskRegistryDataForExportCSV(String engCode, final String engSchema, String flag,final long userID) throws AppException {
        logger.info("Start: RoadMapDAOImpl: riskRegistryDataForExportCSV()");
        String procName = "{CALL RMDTN_ReportRegistryDetails(?,?,?,?)}";
        List dbhlist = new ArrayList();
        dbhlist.add("CLNT_RISK_RGST_KEY");
        dbhlist.add("RMDTN_PLN_NM");
        dbhlist.add("RMDTN_PLN_STS_NM");
        dbhlist.add("Days Open");
        dbhlist.add("Instances");
        dbhlist.add("Completion");

        List csvlist = new ArrayList();
        csvlist.add("Risk Register ID");
        csvlist.add("Plan Title");
        csvlist.add("Plan Status");
        csvlist.add("Days Open");
        csvlist.add("Instances");
        csvlist.add("Completion");
//        List csvlist = securityModel.getCsvHeaders();

        List finalList = new ArrayList();
        finalList.add(csvlist);
        try {
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{engCode, engSchema, flag,userID});
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
            logger.debug("Exception occured : RoadMapDAOImpl: riskRegistryDataForExportCSV(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "RoadMapDAOImpl: riskRegistryDataForExportCSV(): " + e.getMessage());
        }
        logger.info("End: RoadMapDAOImpl: riskRegistryDataForExportCSV()");
        return finalList;
    }

}
