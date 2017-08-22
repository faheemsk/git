/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service.impl;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dao.impl.RemediationProjectManagerDaoImpl;
import com.optum.oss.dto.ManageServiceUserDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.dto.VulnerabilityDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.service.RemediationProjectManagerService;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 *
 * @author sathuluri
 */
@Service
public class RemediationProjectManagerServiceImpl implements RemediationProjectManagerService {

    private static final Logger logger = Logger.getLogger(RemediationProjectManagerServiceImpl.class);

    @Autowired
    private RemediationProjectManagerDaoImpl remediationProjectManagerDao;

    /**
     * This method for fetch remediation project manager work list
     *
     * @param userDto
     * @param manageServiceUserDTO
     * @return List<ManageServiceUserDTO>
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<ManageServiceUserDTO> fetchRemediationWorklist(final UserProfileDTO userDto,
            final ManageServiceUserDTO manageServiceUserDTO) throws AppException {
        logger.info("Start: RemediationProjectManagerServiceImpl: fetchRemediationWorklist() to fetch remediation project manager worklist");
        long organizationKey = 0;
        if ((userDto.getUserTypeObj().getLookUpEntityName()).equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_CLIENT)) {
            organizationKey = userDto.getOrganizationKey();
            manageServiceUserDTO.setOrganizationKey(userDto.getOrganizationKey());
        }
        if (null == manageServiceUserDTO.getOrganizationName()) {
            manageServiceUserDTO.setOrganizationName("");
        }
        if (null == manageServiceUserDTO.getClientEngagementCode()) {
            manageServiceUserDTO.setClientEngagementCode("");
        }
        if (null == manageServiceUserDTO.getEngagementName()) {
            manageServiceUserDTO.setEngagementName("");
        }
        List<ManageServiceUserDTO> remediationList = new ArrayList<>();
        if (!((userDto.getUserTypeObj().getLookUpEntityName()).equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_PARTNER))) {
            manageServiceUserDTO.setUserKey(userDto.getUserProfileKey());
            remediationList = remediationProjectManagerDao.fetchRemediationWorklist(manageServiceUserDTO);
        }
        logger.info("End: RemediationProjectManagerServiceImpl: fetchRemediationWorklist() to fetch remediation project manager worklist");
        return remediationList;
    }

    /**
     * This method for fetch finding work list
     *
     * @param engagementCode
     * @return List<VulnerabilityDTO>
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<VulnerabilityDTO> findingList(final String engagementCode) throws AppException {
        logger.info("Start: RemediationProjectManagerServiceImpl: findingList() to fetch finding worklist");
        List<VulnerabilityDTO> findingList = remediationProjectManagerDao.findingList(engagementCode);
        logger.info("End: RemediationProjectManagerServiceImpl: findingList() to fetch finding worklist");
        return findingList;
    }

    /**
     * This method for update remediation finding status
     *
     * @param vulnerabilityDTO
     * @return int
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int updateFindingStatus(final VulnerabilityDTO vulnerabilityDTO) throws AppException {
        logger.info("Start: RemediationProjectManagerServiceImpl: findingList() to fetch finding worklist");
        List<VulnerabilityDTO> findingStatusList = this.converStringToDto(vulnerabilityDTO);
        int retVal = remediationProjectManagerDao.updateFindingStatus(findingStatusList);
        logger.info("End: RemediationProjectManagerServiceImpl: findingList() to fetch finding worklist");
        return retVal;
    }

    /**
     * This method for split String value to Dto's
     *
     * @param vulnerabilityDTO
     * @return int
     * @throws AppException
     */
    public List<VulnerabilityDTO> converStringToDto(final VulnerabilityDTO vulnerabilityDTO) throws AppException {
        logger.info("Start: RemediationProjectManagerServiceImpl: converStringToDto() to split String data to Dto");
        List<VulnerabilityDTO> findingStatusList = new ArrayList<>();
        Map<String, VulnerabilityDTO> findingStatusMap = new LinkedHashMap<>();
        VulnerabilityDTO dto = null;

        if (null != vulnerabilityDTO) {
            if (!StringUtils.isEmpty(vulnerabilityDTO.getStatusCode())) {
                String[] findingStatus = vulnerabilityDTO.getStatusCode().split(",");
                for (String s : findingStatus) {
                    if (!StringUtils.isEmpty(s)) {
                        String[] statusArray = s.split("@");
                        if (findingStatusMap.containsKey(statusArray[1])) {
                            dto = findingStatusMap.get(statusArray[1]);
                            dto.setStatusCode(statusArray[0]);
                            dto.setInstanceIdentifier(statusArray[1]);
                        } else {
                            dto = new VulnerabilityDTO();
                            dto.setStatusCode(statusArray[0]);
                            dto.setInstanceIdentifier(statusArray[1]);
                            findingStatusMap.put(statusArray[1], dto);
                        }
                    }
                }
            }
        }
        if (!findingStatusMap.isEmpty()) {
            findingStatusList = new ArrayList(findingStatusMap.values());
        }
        logger.info("End: RemediationProjectManagerServiceImpl: converStringToDto() to split String data to Dto");
        return findingStatusList;
    }
}
