/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service.impl;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dao.impl.RemediationDAOImpl;
import com.optum.oss.dao.impl.RiskRegistryDAOImpl;
import com.optum.oss.dto.ManageServiceUserDTO;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.RemediationDTO;
import com.optum.oss.dto.RiskRegisterDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.dto.VulnerabilityDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.RemediationMailHelper;
import com.optum.oss.service.RiskRegistryService;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rpanuganti
 */
@Service
public class RiskRegistryServiceImpl implements RiskRegistryService {

    private static final Logger logger = Logger.getLogger(RemediationProjectManagerServiceImpl.class);

    @Autowired
    private RiskRegistryDAOImpl riskRegistryDao;
    @Autowired
    private UserManagementServiceImpl userManagementServiceImpl;
    @Autowired
    private RemediationMailHelper remediationMailHelper;
    @Autowired
    private AnalystValidationServiceImpl analystValidationService;
    @Autowired
    private RemediationServiceImpl remediationService;
    @Autowired
    private RemediationDAOImpl remediationDAO;

    /**
     * This method for fetch Risk Registry work list
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
    public List<ManageServiceUserDTO> fetchRiskRegistryWorklist(UserProfileDTO userDto, ManageServiceUserDTO manageServiceUserDTO) throws AppException {

        logger.info("Start: RemediationProjectManagerServiceImpl: fetchRiskRegistryWorklist()");
        long organizationKey = 0;
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
        remediationList = riskRegistryDao.fetchRiskRegistryWorklist(userDto, manageServiceUserDTO);
        logger.info("End: RemediationProjectManagerServiceImpl: fetchRiskRegistryWorklist()");
        return remediationList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<RiskRegisterDTO> riskRegistryList(String engagementCode, String orgSchema, long userID, String flag) throws AppException {
        return riskRegistryDao.riskRegistryList(engagementCode, orgSchema, userID, flag);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public RiskRegisterDTO riskRegistryDetailsById(String orgSchema, int riskRegisterID) throws AppException {
        return riskRegistryDao.riskRegistryDetailsById(orgSchema, riskRegisterID);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<VulnerabilityDTO> riskRegistryFindingsListByID(int registryID, String orgSchema) throws AppException {

        return riskRegistryDao.riskRegistryFindingsListByID(registryID, orgSchema);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public int updateRiskRegistryDetails(RiskRegisterDTO riskRegistryDto, UserProfileDTO usersSessionDTO) throws AppException {
        return riskRegistryDao.updateRiskRegistryDetails(riskRegistryDto, usersSessionDTO);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public int removeRiskRegistryItems(String[] items, int RegistryID, String orgSchema,final UserProfileDTO usersSessionDTO) throws AppException {
        int retVal = 0;
        String findingStatusCode=ApplicationConstants.DB_CONSTANT_REMEDIATION_OPEN;
        try {
            if(items!=null && items.length>0){
            retVal = riskRegistryDao.removeRiskRegistryItems(items, RegistryID, orgSchema);
            
                for (String findingID : items) {
                    long fndID=0;
                    if (findingID != null && !findingID.equalsIgnoreCase("")) {
                        fndID = Long.parseLong(findingID);
                    }
                    analystValidationService.updateFindingStatus(ApplicationConstants.FINDING_UPDATE_STATUS_DB_FLAG, orgSchema,fndID, findingStatusCode, "", usersSessionDTO);
                }
                
                    RiskRegisterDTO riskDTO = riskRegistryDao.riskRegistryDetailsById(orgSchema, RegistryID);
                    RemediationDTO remDTO = new RemediationDTO();
                    remDTO.setPlanID(riskDTO.getPlanID());
                    remDTO.setOrgSchema(orgSchema);
                    remediationService.updatePlanStatus(remDTO, usersSessionDTO);
            }
        } catch (AppException e) {
                        logger.debug("Exception occured :RiskRegistryServiceImpl updateRiskRegistryItemsByRiskRegistryResponse() : " + e.getMessage());
        }
        return retVal;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public int saveRemediatePlanFindings(RemediationDTO remediationDto, UserProfileDTO sessionDTO) throws AppException {
        return riskRegistryDao.saveRemediatePlanFindings(remediationDto, sessionDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public int saveRiskRigistryFindings(RemediationDTO remediationDto, UserProfileDTO sessionDTO) throws AppException {
        return riskRegistryDao.saveRiskRigistryFindings(remediationDto, sessionDTO);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<MasterLookUpDTO> fetchRiskResponseList() throws AppException {
        return riskRegistryDao.fetchRiskResponseList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public int updateRiskAcceptanceDetails(RiskRegisterDTO riskRegistryDto, UserProfileDTO usersSessionDTO) throws AppException {
        return riskRegistryDao.updateRiskAcceptanceDetails(riskRegistryDto, usersSessionDTO);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public int updateFindingCountRegistryAnsPlan(int key, int findingCount, long userID, String orgSchema, String flag) throws AppException {
        return riskRegistryDao.updateFindingCountRegistryAnsPlan(key, findingCount, userID, orgSchema, flag);
    }

    @Override
    public int updateRiskRegistryNotifyDate(RiskRegisterDTO registryDto, UserProfileDTO sessionDTO) throws AppException {
        int retVal = 0;
        try {
            UserProfileDTO userDTO = userManagementServiceImpl.fetchUserInfoByUserID(registryDto.getRiskRegistryOwnerID());
            RiskRegisterDTO registerDTO = riskRegistryDao.riskRegistryDetailsById(registryDto.getOrgSchema(), registryDto.getRiskRegisterID());
            retVal = riskRegistryDao.updateRiskRegistryNotifyDate(registryDto, sessionDTO);
            remediationMailHelper.mailOnRegistryNotification(userDTO, registerDTO, sessionDTO);
        } catch (Exception e) {

        }
        return retVal;
    }
    
     @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public int updateRiskRegistryItemsByRiskRegistryResponse(RiskRegisterDTO riskRegistryDto, UserProfileDTO usersSessionDTO) {
        logger.info("End: RiskRegistryServiceImpl : updateRiskRegistryItemsByRiskRegistryResponse()");
        String findingStatusCode = "";
        int retval=0;
        try {
            List<VulnerabilityDTO> findingsList = riskRegistryFindingsListByID(riskRegistryDto.getRiskRegisterID(), riskRegistryDto.getOrgSchema());

                    findingStatusCode = getFindingStatusCodeByRiskRegistryStatusCode(riskRegistryDto.getRiskResponseCode());
            if (findingsList != null && findingsList.size() > 0) {

                for (VulnerabilityDTO vulnerabilityDTO : findingsList) {
                    try{
                   retval= analystValidationService.updateFindingStatus(ApplicationConstants.FINDING_UPDATE_STATUS_DB_FLAG, riskRegistryDto.getOrgSchema(),Integer.parseInt(vulnerabilityDTO.getInstanceIdentifier()), findingStatusCode, "", usersSessionDTO);
                    }catch(Exception e){
                           logger.debug("Exception occured :RiskRegistryServiceImpl updateRiskRegistryItemsByRiskRegistryResponse() : " + e.getMessage());
                    }
                }
                
            }
             remediationService.updatePlanStatus(riskRegistryDto, usersSessionDTO);
//            remediationDAO.updatePlanStatus(riskRegistryDto, usersSessionDTO,ApplicationConstants.DB_CONSTANT_PLAN_ITEM_STATUS_CLOSED);

        } catch (AppException e) {
            logger.debug("Exception occured :RiskRegistryServiceImpl updateRiskRegistryItemsByRiskRegistryResponse() : " + e.getMessage());
        }
        logger.info("End: RiskRegistryServiceImpl : updateRiskRegistryItemsByRiskRegistryResponse()");
        return retval;
    }
    
    @Override
    public String getFindingStatusCodeByRiskRegistryStatusCode(String riskRegistryStatusCode) {

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("A", ApplicationConstants.DB_CONSTANT_RISK_ACCEPTED);
        map.put("M", ApplicationConstants.DB_CONSTANT_RISK_MITIGATE);
        map.put("S", ApplicationConstants.DB_CONSTANT_RISK_SHARED);
        map.put("T", ApplicationConstants.DB_CONSTANT_RISK_TRANSFER);
        map.put("V", ApplicationConstants.DB_CONSTANT_RISK_AVOID);

        String findingStatusCode = map.get(riskRegistryStatusCode);

        return findingStatusCode;

    }

    @Override
    public String generateRiskSeverity(String engCode, String orgSchema, String registryID) throws AppException {
        return riskRegistryDao.generateRiskSeverity(engCode, orgSchema, registryID);
    }

    @Override
    public int updateRiskRegistrySeverity(RiskRegisterDTO riskRegistryDto, UserProfileDTO usersSessionDTO) throws AppException {
        return riskRegistryDao.updateRiskRegistrySeverity(riskRegistryDto, usersSessionDTO);
    }

    @Override
    public VulnerabilityDTO riskRegistryFindingInfo(int instanceId, String orgSchema) throws AppException {
        return riskRegistryDao.riskRegistryFindingInfo(instanceId, orgSchema);
    }

    @Override
    public int updateRiskRegistryFinding(RiskRegisterDTO riskRegistryDto, UserProfileDTO usersSessionDTO) throws AppException {
        return riskRegistryDao.updateRiskRegistryFinding(riskRegistryDto, usersSessionDTO);
    }

}
