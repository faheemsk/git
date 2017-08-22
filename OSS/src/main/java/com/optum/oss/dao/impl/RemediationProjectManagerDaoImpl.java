/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao.impl;

import com.optum.oss.dao.RemediationProjectManagerDao;
import com.optum.oss.dto.CVEInformationDTO;
import com.optum.oss.dto.ManageServiceUserDTO;
import com.optum.oss.dto.SecurityServiceDTO;
import com.optum.oss.dto.VulnerabilityDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.util.EncryptDecrypt;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author sathuluri
 */
public class RemediationProjectManagerDaoImpl implements RemediationProjectManagerDao {

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
    public List<ManageServiceUserDTO> fetchRemediationWorklist(final ManageServiceUserDTO manageServiceUserDTO) throws AppException {

        logger.info("Start: RemediationProjectManagerDaoImpl: fetchRemediationWorklist() to fetch Remediation Project Manager worklist");
        String analystWorklistProc = "{CALL Report_GetRemediationPMWorkList(?,?,?,?,?)}";
        List<ManageServiceUserDTO> serviceList = null;
        try {

            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(analystWorklistProc, new Object[]{
                manageServiceUserDTO.getOrganizationKey(),
                manageServiceUserDTO.getOrganizationName(),
                manageServiceUserDTO.getClientEngagementCode(),
                manageServiceUserDTO.getEngagementName(),
                manageServiceUserDTO.getUserKey()
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

    /**
     * This method for fetch finding work list
     *
     * @param engagementCode
     * @return List<VulnerabilityDTO>
     * @throws AppException
     */
    @Override
    public List<VulnerabilityDTO> findingList(final String engagementCode) throws AppException {
        logger.info("Start: RemediationProjectManagerDaoImpl: findingList() to fetch finding worklist");
        List<VulnerabilityDTO> findingList = null;
        try {
            String vulnerabilityProc = "{CALL Report_GetRemediationFindingListByEngmtcd(?)}";
            List<Map<String, Object>> vulnerabilityMap = jdbcTemplate.queryForList(vulnerabilityProc,
                    new Object[]{engagementCode});
            findingList = new ArrayList<>(vulnerabilityMap.size());

            for (Map<String, Object> vulnerability : vulnerabilityMap) {
                VulnerabilityDTO vulnerabilityDTO = new VulnerabilityDTO();
                vulnerabilityDTO.setInstanceIdentifier(vulnerability.get("CLNT_VULN_INSTC_KEY") + "");
                vulnerabilityDTO.setEncInstanceIdentifier(encDecrypt.encrypt(vulnerability.get("CLNT_VULN_INSTC_KEY") + ""));
                vulnerabilityDTO.setVulnerabilityName(vulnerability.get("VULN_NM") + "");
                vulnerabilityDTO.setSourceCode((Integer) vulnerability.get("VULN_SRC_KEY"));
                vulnerabilityDTO.setSourceName(vulnerability.get("LKP_ENTY_NM") + "");

                SecurityServiceDTO serviceDto = new SecurityServiceDTO();
                serviceDto.setSecurityServiceCode(vulnerability.get("SECUR_SRVC_CD") + "");
                serviceDto.setSecurityServiceName(vulnerability.get("SECUR_SRVC_NM") + "");
                vulnerabilityDTO.setSecurityServiceObj(serviceDto);

                CVEInformationDTO cveInformationDTO = new CVEInformationDTO();
                if (null != vulnerability.get("VULN_SEV_NM")) {
                    cveInformationDTO.setSeverityName(vulnerability.get("VULN_SEV_NM") + "");
                }
                cveInformationDTO.setSeverityCode(vulnerability.get("VULN_SEV_CD") + "");
                vulnerabilityDTO.setCveInformationDTO(cveInformationDTO);

                vulnerabilityDTO.setStatusName(vulnerability.get("VULN_INSTC_STS_NM") + "");
                findingList.add(vulnerabilityDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : RemediationProjectManagerDaoImpl: findingList() : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "findingList() :: " + e.getMessage());
        }
        logger.info("Start: RemediationProjectManagerDaoImpl: findingList() to fetch finding worklist");
        return findingList;
    }

    /**
     * This method for update remediation finding status
     *
     * @param findingStatusList
     * @return int
     * @throws AppException
     */
    @Override
    public int updateFindingStatus(final List<VulnerabilityDTO> findingStatusList) throws AppException {
        logger.info("Start: RemediationProjectManagerDaoImpl : updateFindingStatus");
        int returnVal = 0;
        int count = 0;
        try {
            String updateServicesStatus = "{CALL Report_UpdateVlunarabilityStatus(?,?)}";
            if (!findingStatusList.isEmpty()) {
                for (VulnerabilityDTO dto : findingStatusList) {
                    if (!dto.getStatusCode().equalsIgnoreCase("Select")) {
                        returnVal = jdbcTemplate.queryForObject(updateServicesStatus,
                                new Object[]{
                                    encDecrypt.decrypt(dto.getInstanceIdentifier()),
                                    dto.getStatusCode()}, Integer.class);
                        count = count + returnVal;
                    }
                }
            }
            logger.info("Total no. of findings updated: " + count);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : RemediationProjectManagerDaoImpl: updateFindingStatus: " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "updateFindingStatus(): " + e.getMessage());
        }
        logger.info("End: RemediationProjectManagerDaoImpl : updateFindingStatus");
        return returnVal;
    }
}
