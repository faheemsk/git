/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.service.impl;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.constants.PermissionConstants;
import com.optum.oss.dao.AnalystValidationDAO;
import com.optum.oss.dao.impl.RemediationDAOImpl;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.RemediationDTO;
import com.optum.oss.dto.RiskRegisterDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.dto.VulnerabilityDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.RemediationMailHelper;
import com.optum.oss.service.RemediationService;
import com.optum.oss.service.RiskRegistryService;
import com.optum.oss.util.DateUtil;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sbhagavatula
 */
@Service
public class RemediationServiceImpl implements RemediationService{
    
    @Autowired
    private RemediationDAOImpl remedioationDao;
    @Autowired
    private RiskRegistryService riskRegistryService;
    @Autowired
    private DateUtil dateUtil;
    @Autowired
    private AnalystValidationDAO analystValidationDAO; 
    @Autowired
    private RemediationMailHelper remediationMailHelper;
    @Autowired
    private UserManagementServiceImpl userManagementServiceImpl;
    @Autowired
    private AnalystValidationServiceImpl analystValidationService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<MasterLookUpDTO> fetchSeverityList() throws AppException {
        return remedioationDao.fetchSeverityList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<VulnerabilityDTO> findingLookup(RemediationDTO remediationDto) throws AppException {
        remediationDto.setSelSource(remediationDto.getSelSource()== null?"":remediationDto.getSelSource());
        remediationDto.setSelVulCategory(remediationDto.getSelVulCategory() == null?"":remediationDto.getSelVulCategory());
        remediationDto.setSelSeverity(remediationDto.getSelSeverity() == null?"":remediationDto.getSelSeverity());
        remediationDto.setSelService(remediationDto.getSelService() == null?"":remediationDto.getSelService());
        remediationDto.setSelOsCategory(remediationDto.getSelOsCategory() == null?"":remediationDto.getSelOsCategory());
        
        List<VulnerabilityDTO> findingsList = new ArrayList<>();
        
//        if(!("".equalsIgnoreCase(remediationDto.getSelSource()) &&
//            "".equalsIgnoreCase(remediationDto.getSelVulCategory()) &&
//            "".equalsIgnoreCase(remediationDto.getSelOsCategory()) &&
//            "".equalsIgnoreCase(remediationDto.getSelSeverity()) &&
//            "".equalsIgnoreCase(remediationDto.getSelService()))){
            findingsList = remedioationDao.findingLookup(remediationDto);
//        }
        return findingsList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<MasterLookUpDTO> fetchOSCatList() throws AppException {
        return remedioationDao.fetchOSCatList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<VulnerabilityDTO> findingsByMultipleIds(RemediationDTO remediationDto) throws AppException {
        return remedioationDao.findingsByMultipleIds(remediationDto);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int addRemediationPlan(RemediationDTO remediationDto, UserProfileDTO sessionDTO) throws AppException {
        List<String> assocFindingIds = new ArrayList(Arrays.asList(remediationDto.getSelectedInstances().split("\\s*,\\s*")));
        String findingsCount = assocFindingIds.size()+"";
        remediationDto.setFindingsCount(findingsCount);
        int planId = remedioationDao.saveActionPlanInfo(remediationDto, sessionDTO);
        if(planId > 0){
            remediationDto.setPlanID(planId+"");
            if(remediationDto.getPlanNotes() != null && remediationDto.getPlanNotes().length > 0)
                remedioationDao.savePlanNotes(remediationDto, sessionDTO);
            addFindingsToPlan(remediationDto, sessionDTO);
        }
        String adjSeverity = generateRemdiationSeverity(remediationDto);
        remediationDto.setPlanAdjSeverityCode(adjSeverity);
        remedioationDao.updateActionPlanInfo(remediationDto, sessionDTO);
        return planId;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<RemediationDTO> remediationPlanList(String engagementCode, String orgSchema,UserProfileDTO sessionDTO) throws AppException {
        List<RemediationDTO> remediationList = new VirtualFlow.ArrayLinkedList<>();
        Set<String> permissionSet = sessionDTO.getSubMenuPermissionMap().get(PermissionConstants.REMEDIATION_MODULE);
        if(permissionSet.contains(PermissionConstants.ADD_FINDING)){
            //For Remediation Analyst and Remediation Coordinator
            remediationList =  remedioationDao.remediationPlanList(engagementCode, orgSchema,"AL",sessionDTO);
        }else{
            //For Remediation Owner
            remediationList =  remedioationDao.remediationPlanList(engagementCode, orgSchema,"RO",sessionDTO);
        }
        return remediationList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public RemediationDTO planInfoByPlanKey(String planKey, String orgSchema) throws AppException {
        RemediationDTO remediationObj = null;
        String daysOpen = "";
        try {
            remediationObj = remedioationDao.planInfoByPlanKey(planKey, orgSchema);
            //Calcuation for Days Open
            if(!"".equalsIgnoreCase(remediationObj.getNotifyDate())){
                if("".equalsIgnoreCase(remediationObj.getClosedDate())){
                    //Current date minus notify date
                    daysOpen = dateUtil.calculateDateDiffInDays(remediationObj.getNotifyDate())+"";
                }else{
                        //Closed date minus notify date
                        DateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String notifyDateStr = dateUtil.dateConvertionFromJSPToDB(remediationObj.getNotifyDate());
                        Date notifyDate = sd.parse(notifyDateStr);

                        String closedDateStr = dateUtil.dateConvertionFromJSPToDB(remediationObj.getClosedDate());
                        Date closedDate = sd.parse(closedDateStr);

                        long diff = closedDate.getTime() - notifyDate.getTime();
                        long diffDays = diff / (24 * 60 * 60 * 1000);
                        daysOpen = diffDays+"";
                }
            }
            remediationObj.setDaysOpen(daysOpen);
            
            //Start:To get the registry key for plan key if exists
            remediationObj.setPlanID(planKey);
            remediationObj.setOrgSchema(orgSchema);
            String registryKey = remedioationDao.getRegistryKeyByPlanKey(remediationObj);
            remediationObj.setPlanRegKey(registryKey);
            //End:To get the registry key for plan key if exists
        } catch (ParseException ex) {
            Logger.getLogger(RemediationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AppException e){
            throw new AppException("Exception occured while Excecuting the "
                    + "planInfoByPlanKey() :: " + e.getMessage());
        }
        return remediationObj;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<RemediationDTO> planNotesByPlanKey(String planKey, String orgSchema) throws AppException {
        return remedioationDao.planNotesByPlanKey(planKey, orgSchema);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<VulnerabilityDTO> findingsByPlanKey(String planKey, String orgSchema) throws AppException {
        return remedioationDao.findingsByPlanKey(planKey, orgSchema);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int addFindingsToPlan(RemediationDTO remediationDto, UserProfileDTO sessionDTO) throws AppException {
        int retVal = remedioationDao.saveRemediatePlanFindings(remediationDto, sessionDTO);
                
        if(retVal > 0){
            String planSeverity = generateRemdiationSeverity(remediationDto);
            updatePlanSeverity(remediationDto, sessionDTO, planSeverity);
            
            List<String> assocFindingIds = new ArrayList(Arrays.asList(remediationDto.getSelectedInstances().split("\\s*,\\s*")));
            for(String instanceId:assocFindingIds){
                analystValidationService.updateFindingStatus("U", remediationDto.getOrgSchema(), Long.parseLong(instanceId), ApplicationConstants.DB_CONSTANT_REMEDIATION_OPEN, remediationDto.getClosedComments(), sessionDTO);
            }
        }
        return retVal;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int updateRemediationPlan(RemediationDTO remediationDto, UserProfileDTO sessionDTO) throws AppException {
        int retVal = 0;
        Set<String> remediationPermissionSet = sessionDTO.getSubMenuPermissionMap().get(PermissionConstants.REMEDIATION_MODULE);
        if(remediationPermissionSet.contains(PermissionConstants.ADD_REMEDIATION)){
            retVal = remedioationDao.updateActionPlanInfo(remediationDto, sessionDTO);
        }else{
            retVal = 1;
        }
        if(retVal>0 && (remediationDto.getPlanNotes() != null && remediationDto.getPlanNotes().length > 0))
            retVal = remedioationDao.savePlanNotes(remediationDto, sessionDTO);
        return retVal;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int deletePlanFindings(RemediationDTO remediationDto, UserProfileDTO sessionDTO) throws AppException {
        
        int retVal = remedioationDao.deletePlanFindings(remediationDto);
        
        if(retVal > 0){
            String planSeverity = generateRemdiationSeverity(remediationDto);
            updatePlanSeverity(remediationDto, sessionDTO, planSeverity);
            
            List<String> removeFindingIds = new ArrayList(Arrays.asList(remediationDto.getRemoveInstances().split("\\s*,\\s*")));
            
            for(String instanceId:removeFindingIds){
                analystValidationService.updateFindingStatus("U", remediationDto.getOrgSchema(), Long.parseLong(instanceId), ApplicationConstants.DB_CONSTANT_FINDING_VALIDATE_STATUS_CODE, remediationDto.getClosedComments(), sessionDTO);
            }
            
        }
        return retVal;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int saveRiskRegistry(RemediationDTO remediationDto,UserProfileDTO sessionDTO)throws AppException {
        int retVal = 0;
        String regKey = "";
        
        String registryKey = remedioationDao.getRegistryKeyByPlanKey(remediationDto);
        
        if(registryKey == null){
            retVal = remedioationDao.saveRiskRegistryInfo(remediationDto, sessionDTO);
            regKey = retVal+"";
        }else{
            regKey = registryKey;
        }
        
        remedioationDao.saveRiskRigistryFindings(remediationDto, sessionDTO, regKey);
        
        
        /* GENERATE RISK REGISTRY SEVERITY */
        String riskSeverityCd = riskRegistryService.generateRiskSeverity(remediationDto.getClientEngagementDTO().getEngagementCode(), 
                remediationDto.getOrgSchema(), regKey);
        /* GENERATE RISK REGISTRY SEVERITY */
        
        /* UPDATE RISK REGISTRY SEVERITY*/
        RiskRegisterDTO riskDTO = new RiskRegisterDTO();
        riskDTO.setAdjSeverityCode(riskSeverityCd);
        riskDTO.setRiskRegisterID(Integer.parseInt(regKey));
        riskDTO.setOrgSchema(remediationDto.getOrgSchema());
                
        riskRegistryService.updateRiskRegistrySeverity(riskDTO, sessionDTO);
        /* UPDATE RISK REGISTRY SEVERITY*/
        
        List<VulnerabilityDTO> findingsList = riskRegistryService.riskRegistryFindingsListByID(Integer.parseInt(regKey), remediationDto.getOrgSchema());
        riskRegistryService.updateFindingCountRegistryAnsPlan(Integer.parseInt(regKey), findingsList.size(), sessionDTO.getUserProfileKey(), remediationDto.getOrgSchema(), ApplicationConstants.DB_CONSTANT_REGISTRY_FINDING_COUNT_UPDATE);
        
        List<String> removeFindingIds = new ArrayList(Arrays.asList(remediationDto.getRemoveInstances().split("\\s*,\\s*")));
        for(String instanceId:removeFindingIds){
            analystValidationService.updateFindingStatus("U", remediationDto.getOrgSchema(), Long.parseLong(instanceId), remediationDto.getStatusCode(), remediationDto.getClosedComments(), sessionDTO);
        }
       
        updatePlanStatus(remediationDto, sessionDTO);
        return retVal;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int deleteRemediationPlan(RemediationDTO remediationDto, UserProfileDTO sessionDTO) throws AppException {
        
        List<String> removePlanIds = new ArrayList(Arrays.asList(remediationDto.getRemoveInstances().split("\\s*,\\s*")));
        for(String instanceId:removePlanIds){
            List<VulnerabilityDTO> planFindings = findingsByPlanKey(instanceId, remediationDto.getOrgSchema());
            for (VulnerabilityDTO vulnerabilityDTO : planFindings) {
                analystValidationService.updateFindingStatus("U", remediationDto.getOrgSchema(), Long.parseLong(vulnerabilityDTO.getInstanceIdentifier()), ApplicationConstants.DB_CONSTANT_FINDING_VALIDATE_STATUS_CODE, remediationDto.getClosedComments(), sessionDTO);
            }
        }

        return remedioationDao.deleteRemediationPlan(remediationDto);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int updatePlanItemStatus(RemediationDTO remediationDto,UserProfileDTO sessionDTO)throws AppException{
        int planStat = 0;
        
        //planStat = remedioationDao.updatePlanItemStatus(remediationDto, sessionDTO);
        
        String flag = "U";
        if(ApplicationConstants.DB_CONSTANT_FINDING_CLOSED_STATUS_CODE.equalsIgnoreCase(remediationDto.getStatusCode())){
            flag = "C";
        }
        
        List<String> removeFindingIds = new ArrayList(Arrays.asList(remediationDto.getRemoveInstances().split("\\s*,\\s*")));
        for(String instanceId:removeFindingIds){
            planStat = analystValidationService.updateFindingStatus(flag, remediationDto.getOrgSchema(), Long.parseLong(instanceId), remediationDto.getStatusCode(), remediationDto.getClosedComments(), sessionDTO);
        }
                
        updatePlanStatus(remediationDto, sessionDTO);
        
        return planStat;
    }
    
    @Override
    public void updatePlanStatus(RemediationDTO remediationDTO, UserProfileDTO sessionDTO) throws AppException {
        
        int openVulCount=0;
        int closedVulCount=0;
        int vulListSize=0;
        int inprogressVulCount=0;
        
        List<VulnerabilityDTO> planFindings = findingsByPlanKey(remediationDTO.getPlanID(), remediationDTO.getOrgSchema());
        
        if (planFindings != null && planFindings.size() > 0) {
            vulListSize = planFindings.size();
            for (VulnerabilityDTO vulnerabilityDTO : planFindings) {
                if (ApplicationConstants.DB_CONSTANT_REMEDIATION_OPEN.equalsIgnoreCase(vulnerabilityDTO.getStatusCode())) {
                    openVulCount++;
                } else if (ApplicationConstants.DB_CONSTANT_REMEDIATION_IN_PROGRESS.equalsIgnoreCase(vulnerabilityDTO.getStatusCode()) || ApplicationConstants.DB_CONSTANT_RISK_REGISTERED.equalsIgnoreCase(vulnerabilityDTO.getStatusCode())) {
                    inprogressVulCount++;
                } else if (ApplicationConstants.DB_CONSTANT_FINDING_CLOSED_STATUS_CODE.equalsIgnoreCase(vulnerabilityDTO.getStatusCode()) ||
                        ApplicationConstants.DB_CONSTANT_RISK_ACCEPTED.equalsIgnoreCase(vulnerabilityDTO.getStatusCode()) ||
                        ApplicationConstants.DB_CONSTANT_RISK_TRANSFER.equalsIgnoreCase(vulnerabilityDTO.getStatusCode()) ||
                        ApplicationConstants.DB_CONSTANT_RISK_SHARED.equalsIgnoreCase(vulnerabilityDTO.getStatusCode()) ||
                        ApplicationConstants.DB_CONSTANT_RISK_AVOID.equalsIgnoreCase(vulnerabilityDTO.getStatusCode())) {
                    closedVulCount++;
                }
            }
        }
        
        if (planFindings.size() > 0) {
            if (closedVulCount == vulListSize) {
                riskRegistryService.updateFindingCountRegistryAnsPlan(Integer.parseInt(remediationDTO.getPlanID() + ""), 0,
                        sessionDTO.getUserProfileKey(), remediationDTO.getOrgSchema(), ApplicationConstants.DB_CONSTANT_CLOSED_FLAG);
                remedioationDao.updatePlanStatus(remediationDTO, sessionDTO, ApplicationConstants.DB_CONSTANT_PLAN_ITEM_STATUS_CLOSED);
            } else if (openVulCount == vulListSize) {
                remedioationDao.updatePlanStatus(remediationDTO, sessionDTO, ApplicationConstants.DB_CONSTANT_PLAN_ITEM_STATUS_OPEN);
            } else {
                remedioationDao.updatePlanStatus(remediationDTO, sessionDTO, ApplicationConstants.DB_CONSTANT_PLAN_ITEM_STATUS_INPROGRESS);
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int updateRemediationNotifyDate(RemediationDTO remediationDto, UserProfileDTO sessionDTO) throws AppException {
        int retVal = 0;
        retVal = remedioationDao.updateRemediationNotifyDate(remediationDto, sessionDTO);
        //Fetch user details of Client user
        UserProfileDTO remediationOwnerDTO = userManagementServiceImpl.fetchUserInfoByUserID(Long.parseLong(remediationDto.getRemediationOwnerID()+""));
        if(retVal>0){
            remediationMailHelper.mailOnRemediationNotify(remediationOwnerDTO, remediationDto, sessionDTO);
        }
        return retVal;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public RemediationDTO planItemInfoByInstKey(RemediationDTO remediationDto) throws AppException {
        return remedioationDao.planItemInfoByInstKey(remediationDto);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<MasterLookUpDTO> fetchRemediationStatus() throws AppException {
        
        List<MasterLookUpDTO> remStatusList = analystValidationService.analystStatusList(ApplicationConstants.STATUS_REMEDIATION);
        
        List<MasterLookUpDTO> riskStatusList = analystValidationService.analystStatusList(ApplicationConstants.STATUS_RISK_REGISTRY);
        for(MasterLookUpDTO masterDto : riskStatusList){
            if(masterDto.getMasterLookupCode().equalsIgnoreCase("RR")){
                riskStatusList.clear();
                riskStatusList.add(masterDto);
                break;
            }
        }
        remStatusList.addAll(riskStatusList);
        return remStatusList;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public VulnerabilityDTO viewVulnerabilityDetails(long vulnerabilityKey, String orgSchema) throws AppException {
        return analystValidationDAO.viewVulnerabilityDetails(vulnerabilityKey, orgSchema);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<UserProfileDTO> remediationOwnerUsers(String flag, String engagementCode) throws AppException {
        return remedioationDao.remediationOwnerUsers(flag, engagementCode);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public String generateRemdiationSeverity(RemediationDTO remediationDto) throws AppException {
        return remedioationDao.generateRemdiationSeverity(remediationDto);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int updatePlanSeverity(RemediationDTO remediationDto, UserProfileDTO sessionDTO, String planSeverity) throws AppException {
        return remedioationDao.updatePlanSeverity(remediationDto, sessionDTO, planSeverity);
    }
}
