/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service.impl;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.constants.PermissionConstants;
import com.optum.oss.dao.impl.AnalystValidationDAOImpl;
import com.optum.oss.dao.impl.MasterLookUpDAOImpl;
import com.optum.oss.dao.impl.ReportsAndDashboardDAOImpl;
import com.optum.oss.dto.CVEInformationDTO;
import com.optum.oss.dto.ComplianceInfoDTO;
import com.optum.oss.dto.ManageServiceUserDTO;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.OwaspDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.dto.SecurityServiceDTO;
import com.optum.oss.dto.VulnerabilityDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.model.SecurityModel;
import com.optum.oss.model.SummaryDropDownModel;
import com.optum.oss.service.AnalystValidationService;
import com.optum.oss.transact.dao.impl.AnalystValidationTransactDAOImpl;
import com.optum.oss.util.EncryptDecrypt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
public class AnalystValidationServiceImpl implements AnalystValidationService {

    private static final Logger logger = Logger.getLogger(AnalystValidationServiceImpl.class);

    /*
     Start: Autowiring the required Class instances
     */
    @Autowired
    private AnalystValidationDAOImpl analystValidationDAO;
    @Autowired
    private AnalystValidationTransactDAOImpl analystValidationTransactDAO;
    @Autowired
    private EncryptDecrypt encDec;
    @Autowired
    private MasterLookUpDAOImpl masterDAO;
    @Autowired
    private ReportsAndDashboardDAOImpl reportsDAOImpl;
    
    /*
     End: Autowiring the required Class instances
     */

    /**
     * This method for fetch Analyst Validation work list
     *
     * @param userDto
     * @param permissionSet
     * @param manageServiceUserDTO
     * @return Map
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public Map fetchAnalystValidationWorklist(final UserProfileDTO userDto, final Set<String> permissionSet, ManageServiceUserDTO manageServiceUserDTO)
            throws AppException {
        logger.info("Start: AnalystValidationServiceImpl: fetchAnalystValidationWorklist() to fetch Analyst Validation worklist");
        String pvcFlag = "";
        if((permissionSet.contains(PermissionConstants.ADD_USER_TO_SERVICE) || permissionSet.contains(PermissionConstants.EDIT_USER_TO_SERVICE)) &&
                (permissionSet.contains(PermissionConstants.ADD_DOCUMENT_UPLOAD) || permissionSet.contains(PermissionConstants.EDIT_DOCUMENT_UPLOAD)))
        {
            pvcFlag = ApplicationConstants.DB_CONSTANT_ENG_LEAD_ENG_ANALYST;
        }else if (permissionSet.contains(PermissionConstants.ADD_USER_TO_SERVICE) || permissionSet.contains(PermissionConstants.EDIT_USER_TO_SERVICE)) {
            pvcFlag = "E";
        } else if (permissionSet.contains(PermissionConstants.ADD_DOCUMENT_UPLOAD) || permissionSet.contains(PermissionConstants.EDIT_DOCUMENT_UPLOAD)) {
            pvcFlag = "A";
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
        if (null == manageServiceUserDTO.getServiceName()) {
            manageServiceUserDTO.setServiceName("");
        }
        if (null == manageServiceUserDTO.getStartDate()) {
            manageServiceUserDTO.setStartDate("");
        }
        if (null == manageServiceUserDTO.getEndDate()) {
            manageServiceUserDTO.setEndDate("");
        }
        if (null == manageServiceUserDTO.getModifiedDate()) {
            manageServiceUserDTO.setModifiedDate("");
        }
        if (null == manageServiceUserDTO.getRowStatusValue()) {
            manageServiceUserDTO.setRowStatusValue("");
        }
        
        List<ManageServiceUserDTO> manageUserServiceList = new ArrayList<>();
        if(pvcFlag != ""){
            manageUserServiceList = analystValidationDAO.fetchAnalystValidationWorklist(userDto, pvcFlag, manageServiceUserDTO);
        }
        
        logger.info("End: AnalystValidationServiceImpl: fetchAnalystValidationWorklist() to fetch Analyst Validation worklist");
        return engagementServicesListToMapConversion(manageUserServiceList);
    }

    /**
     * This method for convert List data to Map
     *
     * @param manageServiceList
     * @return Map
     */
    public Map engagementServicesListToMapConversion(List<ManageServiceUserDTO> manageServiceList) {
        logger.info("Start: AnalystValidationServiceImpl: engagementServicesListToMapConversion() to convert list to map");
        Map<String, LinkedHashSet<String>> engagementMap = new LinkedHashMap<>();
        Map<String, ArrayList<String>> serviceCountMap = new LinkedHashMap<>();
        Map<String, ArrayList<ManageServiceUserDTO>> serviceMap = new LinkedHashMap<>();
        Map<String, String> statusMap = new LinkedHashMap<>();

        /*Start engagement Map Construction*/
        for (ManageServiceUserDTO manageServiceUserDTO : manageServiceList) {

            if (manageServiceUserDTO.getRowStatusValue().equalsIgnoreCase(ApplicationConstants.DB_ROW_STATUS_NOT_REVIEWED)) {
                statusMap.put(manageServiceUserDTO.getClientEngagementCode(), ApplicationConstants.DB_ROW_STATUS_NOT_REVIEWED);
            }
            if (engagementMap.containsKey(manageServiceUserDTO.getOrganizationKey() + ApplicationConstants.SYMBOL_SPLITING
                    + manageServiceUserDTO.getOrganizationName() + ApplicationConstants.SYMBOL_SPLITING + manageServiceUserDTO.getOrgSchema())) {

                LinkedHashSet<String> engagementSet = engagementMap.get(manageServiceUserDTO.getOrganizationKey()
                        + ApplicationConstants.SYMBOL_SPLITING + manageServiceUserDTO.getOrganizationName() + 
                        ApplicationConstants.SYMBOL_SPLITING + manageServiceUserDTO.getOrgSchema());
                engagementSet.add(manageServiceUserDTO.getClientEngagementCode() + ApplicationConstants.SYMBOL_SPLITING
                        + manageServiceUserDTO.getEngagementName());
                /* services Count for Each Organization */
                ArrayList<String> serviceCount = serviceCountMap.get(manageServiceUserDTO.getOrganizationKey()
                        + ApplicationConstants.SYMBOL_SPLITING + manageServiceUserDTO.getOrganizationName() + 
                        ApplicationConstants.SYMBOL_SPLITING + manageServiceUserDTO.getOrgSchema());
                serviceCount.add(manageServiceUserDTO.getClientEngagementCode());

            } else {
                LinkedHashSet<String> engagementSet = new LinkedHashSet<>();
                engagementSet.add(manageServiceUserDTO.getClientEngagementCode() + ApplicationConstants.SYMBOL_SPLITING
                        + manageServiceUserDTO.getEngagementName());

                ArrayList<String> serviceCount = new ArrayList<>();
                serviceCount.add(manageServiceUserDTO.getClientEngagementCode());

                engagementMap.put(manageServiceUserDTO.getOrganizationKey() + ApplicationConstants.SYMBOL_SPLITING
                        + manageServiceUserDTO.getOrganizationName() + ApplicationConstants.SYMBOL_SPLITING + manageServiceUserDTO.getOrgSchema()
                        , engagementSet);
                serviceCountMap.put(manageServiceUserDTO.getOrganizationKey() + ApplicationConstants.SYMBOL_SPLITING
                        + manageServiceUserDTO.getOrganizationName() + ApplicationConstants.SYMBOL_SPLITING + manageServiceUserDTO.getOrgSchema()
                        , serviceCount);
            }

        }
        /*End engagement Map Construction*/

        /*Start Engagement Service Map Construction*/
        for (ManageServiceUserDTO manageServiceUserDTO : manageServiceList) {

            if (serviceMap.containsKey(manageServiceUserDTO.getClientEngagementCode() + ApplicationConstants.SYMBOL_SPLITING
                    + manageServiceUserDTO.getEngagementName())) {

                ArrayList<ManageServiceUserDTO> serviceList = serviceMap.get(manageServiceUserDTO.getClientEngagementCode()
                        + ApplicationConstants.SYMBOL_SPLITING + manageServiceUserDTO.getEngagementName());
                serviceList.add(manageServiceUserDTO);

            } else {
                ArrayList<ManageServiceUserDTO> serviceList = new ArrayList<>();

                serviceList.add(manageServiceUserDTO);
                serviceMap.put(manageServiceUserDTO.getClientEngagementCode() + ApplicationConstants.SYMBOL_SPLITING
                        + manageServiceUserDTO.getEngagementName(), serviceList);
            }
        }
        /*End Engagement Service Map Construction*/
        Map map = new LinkedHashMap();
        map.put("engagementMap", engagementMap);
        map.put("serviceMap", serviceMap);
        map.put("organizationServieCount", serviceCountMap);
        map.put("statusMap", statusMap);
        logger.info("End: AnalystValidationServiceImpl: engagementServicesListToMapConversion() to convert list to map");
        return map;
    }

    /**
     *
     * @param vulnerabilityDTO
     * @return
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<VulnerabilityDTO> vulnerabilityList(VulnerabilityDTO vulnerabilityDTO) throws AppException {
        List<VulnerabilityDTO> vulnerabilityList = new ArrayList();
        if(!StringUtils.isEmpty(vulnerabilityDTO.getSearchID()) &&
                Long.parseLong(vulnerabilityDTO.getSearchID()) > ApplicationConstants.MAX_FINDING_ID) {
            return vulnerabilityList;
        }
        return analystValidationDAO.vulnerabilityList(vulnerabilityDTO);
    }

    /**
     *
     * @param vulnerabilityKey
     * @return
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public VulnerabilityDTO viewVulnerabilityDetails(long vulnerabilityKey, String orgSchema) throws AppException {
        VulnerabilityDTO vulnerabilityDTO = analystValidationDAO.viewVulnerabilityDetails(vulnerabilityKey, orgSchema);
        vulnerabilityDTO.setOrgSchema(orgSchema);
        vulnerabilityDTO = analystValidationDAO.getComplianceInformationByVulnerabilityKey(vulnerabilityDTO);
        if (null != vulnerabilityDTO.getCveInformationDTO().getComplianceList() && !vulnerabilityDTO.getCveInformationDTO().getComplianceList().isEmpty()) {
            Map map = hitrustCrossWalkListToMapConversion(vulnerabilityDTO.getCveInformationDTO().getComplianceList());
            vulnerabilityDTO.getCveInformationDTO().setComplianceMap(map);
            
            StringBuilder checkedHitrustIds = new StringBuilder();
            String seperator = "";
            for (ComplianceInfoDTO complianceDto : vulnerabilityDTO.getCveInformationDTO().getComplianceList()) {
                if (complianceDto.getCheckedHitrust() == ApplicationConstants.COMPLIANCE_ACTIVE) {
                    checkedHitrustIds.append(seperator);
                    checkedHitrustIds.append(complianceDto.getControlCode());
                    seperator = ",";
                }
            }
            List<ComplianceInfoDTO> hitrustCrossWalk = analystValidationDAO.getHitrustCrossWalkInfoByCtrlCD(checkedHitrustIds.toString());
            Map hitrustMap = hitrustCrossWalkListToMapConversion(hitrustCrossWalk);
            vulnerabilityDTO.getCveInformationDTO().setHitrustCrossWalkMap(hitrustMap);
        }
        return vulnerabilityDTO;
    }

    /**
     *
     * @param engagementCode
     * @return
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<SecurityServiceDTO> analystEngagementServices(String engagementCode) throws AppException {
        return analystValidationDAO.analystEngagementServices(engagementCode);
    }

    /**
     * This method for delete vulnerability
     *
     * @param vulnerabilityDto
     * @return int
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int deleteVulnerability(final VulnerabilityDTO vulnerabilityDto,long userId) throws AppException {
        logger.info("Start: AnalystValidationServiceImpl: deleteVulnerability() to delete vulnerability");
        List<VulnerabilityDTO> vulnerabilityList = new ArrayList();
        if (vulnerabilityDto.getInstanceIdentifier().contains(",")) {
            String[] vulnerabilityInstances = vulnerabilityDto.getInstanceIdentifier().split(",");
            for (String instance : vulnerabilityInstances) {
                VulnerabilityDTO dto = new VulnerabilityDTO();
                dto.setInstanceIdentifier(instance);
                dto.setOrgSchema(vulnerabilityDto.getOrgSchema());
                vulnerabilityList.add(dto);
            }
        } else {
            vulnerabilityList.add(vulnerabilityDto);
        }
        logger.info("End: AnalystValidationServiceImpl: deleteVulnerability() to delete vulnerability");
        return analystValidationDAO.deleteVulnerability(vulnerabilityList,userId);
    }

    /**
     * Analyst Status List (Drop down)
     *
     * @param flag
     * @return @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<MasterLookUpDTO> analystStatusList(String flag) throws AppException {
        return analystValidationDAO.analystStatusList(flag);
    }
    /**
     * Analyst Status List (Drop down)
     *
     * @return @throws AppException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<MasterLookUpDTO> analystStatusConditionalList(String statusCode) throws AppException {
        return analystValidationDAO.analystStatusConditionalList(statusCode);
    }

    /**
     * Cost Effort List (Drop down)
     *
     * @return @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<MasterLookUpDTO> analystCostEffortList() throws AppException {
        return analystValidationDAO.analystCostEffortList();
    }

    /**
     * Vulnerability Category List (Drop down)
     *
     * @return @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<MasterLookUpDTO> vulnerabilityCategoryList() throws AppException {
        return analystValidationDAO.vulnerabilityCategoryList();
    }

    /**
     * This method for add CVE Information
     *
     * @param vulnerabilityDTO
     * @return VulnerabilityDTO
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public VulnerabilityDTO addCVEInformation(VulnerabilityDTO vulnerabilityDTO) throws AppException {
        logger.info("Start: AnalystValidationServiceImpl: addCVEInformation() to add CVE Information");
        List<ComplianceInfoDTO> complianceList = analystValidationDAO.getComplianceInformation();
        vulnerabilityDTO = analystValidationDAO.getCVEInformationByCveID(vulnerabilityDTO);  
        if (vulnerabilityDTO.getCveInfoExist() == 1) {
            if (vulnerabilityDTO.getCveInformationDTO().getOverallScore() != null
                    || vulnerabilityDTO.getCveInformationDTO().getExpSubScore() != null
                    || vulnerabilityDTO.getCveInformationDTO().getImpSubScore() != null) {
                vulnerabilityDTO = analystValidationDAO.getRiskDetailsByCvssScore(vulnerabilityDTO);
            } else {
                vulnerabilityDTO.getCveInformationDTO().setSeverityCode(null);
                vulnerabilityDTO.getCveInformationDTO().setSeverityName(null);
                vulnerabilityDTO.getCveInformationDTO().setProbabilityCode(null);
                vulnerabilityDTO.getCveInformationDTO().setProbabilityName(null);
                vulnerabilityDTO.getCveInformationDTO().setImpactCode(null);
                vulnerabilityDTO.getCveInformationDTO().setImpactName(null);
            }
        }
        vulnerabilityDTO = analystValidationDAO.getComplianceInformationByCveID(vulnerabilityDTO);
        vulnerabilityDTO.getCveInformationDTO().setTotalComplianceMap(ComplianceInformationListToMapConversion(complianceList));
        Map map = hitrustCrossWalkListToMapConversion(vulnerabilityDTO.getCveInformationDTO().getComplianceList());
        vulnerabilityDTO.getCveInformationDTO().setComplianceMap(map);
        List<ComplianceInfoDTO> hitrustCrossWalk = analystValidationDAO.getHitrustCrossWalkInfoByCtrlCD(vulnerabilityDTO.getCheckedHitrustValues());
        Map hitrustMap = hitrustCrossWalkListToMapConversion(hitrustCrossWalk);
        vulnerabilityDTO.getCveInformationDTO().setHitrustCrossWalkMap(hitrustMap);
        logger.info("End: AnalystValidationServiceImpl: addCVEInformation() to add CVE Information");
        return vulnerabilityDTO;
    }

    /**
     * This method for convert List data to Map
     *
     * @param complainceList
     * @return Map
     */
    public Map<String, LinkedHashMap<String, ArrayList<ComplianceInfoDTO>>> ComplianceInformationListToMapConversion(List<ComplianceInfoDTO> complainceList) {

        Map<String, LinkedHashMap<String, ArrayList<ComplianceInfoDTO>>> complianceMap = new LinkedHashMap<>();
        Map<String, ArrayList<ComplianceInfoDTO>> complianceFamilyMap = new LinkedHashMap<>();

        for (ComplianceInfoDTO cInfoDTO : complainceList) {

            if (complianceMap.containsKey(cInfoDTO.getComplicanceCode()+ApplicationConstants.SYMBOL_SPLITING + cInfoDTO.getComplianceVersion())) {
                complianceFamilyMap = complianceMap.get(cInfoDTO.getComplicanceCode()+ApplicationConstants.SYMBOL_SPLITING + cInfoDTO.getComplianceVersion());

                if (complianceFamilyMap.containsKey(cInfoDTO.getFamilyCode() + ApplicationConstants.SYMBOL_SPLITING + cInfoDTO.getFamilyName())) {
                    ArrayList<ComplianceInfoDTO> familyObjectiveList = complianceFamilyMap.get(cInfoDTO.getFamilyCode() + ApplicationConstants.SYMBOL_SPLITING + cInfoDTO.getFamilyName());
                    familyObjectiveList.add(cInfoDTO);

                } else {
                    ArrayList<ComplianceInfoDTO> familyObjectiveList = new ArrayList();
                    familyObjectiveList.add(cInfoDTO);
                    complianceFamilyMap.put(cInfoDTO.getFamilyCode() + ApplicationConstants.SYMBOL_SPLITING + cInfoDTO.getFamilyName(), familyObjectiveList);

                }

            } else {

                if (complianceFamilyMap.containsKey(cInfoDTO.getFamilyCode() + ApplicationConstants.SYMBOL_SPLITING + cInfoDTO.getFamilyName())) {
                    ArrayList<ComplianceInfoDTO> familyObjectiveList = complianceFamilyMap.get(cInfoDTO.getFamilyCode() + ApplicationConstants.SYMBOL_SPLITING + cInfoDTO.getFamilyName());
                    familyObjectiveList.add(cInfoDTO);

                } else {
                    ArrayList<ComplianceInfoDTO> familyObjectiveList = new ArrayList();
                    familyObjectiveList.add(cInfoDTO);
                    complianceFamilyMap.put(cInfoDTO.getFamilyCode() + ApplicationConstants.SYMBOL_SPLITING + cInfoDTO.getFamilyName(), familyObjectiveList);
                }
            }
            complianceMap.put(cInfoDTO.getComplicanceCode()+ApplicationConstants.SYMBOL_SPLITING + cInfoDTO.getComplianceVersion(), (LinkedHashMap<String, ArrayList<ComplianceInfoDTO>>) complianceFamilyMap);
        }

        return complianceMap;
    }
    /**
     * Save vulnerability information Transaction method
     *
     * @param saveVulnerability
     * @param sessionDTO
     * @return
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int saveVulnerability(VulnerabilityDTO saveVulnerability, UserProfileDTO sessionDTO) throws AppException {
        int retVal = 0;
        int cveIdCount = analystValidationDAO.checkForCveId(saveVulnerability.getCveInformationDTO().getCveIdentifier());
        if (cveIdCount == 0) {
            saveVulnerability.getCveInformationDTO().setCveIdentifier(ApplicationConstants.EMPTY_STRING);
        }
         int findingCnt = analystValidationDAO.findingNameCount(saveVulnerability.getVulnerabilityName());
        if(findingCnt == 0 && !saveVulnerability.getRootCauseCode().isEmpty()){
            analystValidationDAO.insertFindingName(saveVulnerability, sessionDTO);
        }
        retVal = analystValidationTransactDAO.saveVulnerability(saveVulnerability, sessionDTO);
       
        return retVal;
    }

    /**
     * Save compliances for a vulnerability
     *
     * @param saveVulnerability
     * @param sessionDTO
     * @return
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int saveComplianceInfo(VulnerabilityDTO saveVulnerability, UserProfileDTO sessionDTO) throws AppException {
        List<String> selectedCompliances = new ArrayList<>();
        List<String> lookupCheckedCompliances = new ArrayList<>();
        List<String> uncheckedComp = new ArrayList<>();
        if(!saveVulnerability.getCheckedHitrustValues().isEmpty()){
            selectedCompliances = new ArrayList(Arrays.asList(saveVulnerability.getCheckedHitrustValues().split("\\s*,\\s*")));
            lookupCheckedCompliances = new ArrayList(Arrays.asList(saveVulnerability.getCheckedHitrustValues().split("\\s*,\\s*")));
        }
        
        List<String> prevCheckedCompliances = analystValidationDAO.getSecurityCtrlCodeByVulnerabilityKey(saveVulnerability, "T");
        //Duplicate list of above list (prevCheckedCompliances)
        List<String> toCheckInsDup = new ArrayList<>();
        toCheckInsDup.addAll(prevCheckedCompliances);
        
        //Duplicate list of above list (prevCheckedCompliances)
        List<String> dupCheckedCompliances = new ArrayList<>();
        dupCheckedCompliances.addAll(prevCheckedCompliances);

        //Here 'selectedCompliances' list contains newly ADDED compliances
        selectedCompliances.removeAll(prevCheckedCompliances);
        //Here 'prevCheckedCompliances' list contains DELETED compliances
        prevCheckedCompliances.removeAll(lookupCheckedCompliances);
        //Old records to reactivate
        dupCheckedCompliances.removeAll(prevCheckedCompliances);
        
        //Change rowstatuskey to 3 (DELETE) for Primary Compliances
        analystValidationDAO.updateComplianceStatus(saveVulnerability, prevCheckedCompliances, sessionDTO, ApplicationConstants.COMPLIANCE_DELETE);
        
        //Change rowstatuskey to 1 (ACTIVE) for Primary Compliances
        analystValidationDAO.updateComplianceStatus(saveVulnerability, dupCheckedCompliances, sessionDTO, ApplicationConstants.COMPLIANCE_ACTIVE);
        
        //Insert new Primary Compliances to DB
        int retval = analystValidationDAO.saveComplianceInfo(saveVulnerability, selectedCompliances, sessionDTO);

        
        //Change rowstatuskey to 3 (DELETE) for Secondary Compliances
        List<ComplianceInfoDTO> toDeleteSecondaryComp = analystValidationDAO.getHitrustCrossWalkInfoByCtrlCD(stringFromList(prevCheckedCompliances));
        analystValidationDAO.updateSecondaryComplianceInfo(toDeleteSecondaryComp, saveVulnerability, sessionDTO, ApplicationConstants.COMPLIANCE_DELETE);
        
        //To inactivate secondary compliances based on Page level selection(Unchecked primary compliances)
        List<ComplianceInfoDTO> toInactiveSecondaryComp = analystValidationDAO.getHitrustCrossWalkInfoByCtrlCD(saveVulnerability.getDftChkHitrust());
        analystValidationDAO.updateSecondaryComplianceInfo(toInactiveSecondaryComp, saveVulnerability, sessionDTO, ApplicationConstants.COMPLIANCE_INACTIVE);

        //Change rowstatuskey to 1 (ACTIVE) for Secondary Compliances
        List<String> actSecComps = new ArrayList<>();
        if(!saveVulnerability.getCheckedHitrustValues().isEmpty()){
        Collections.addAll(actSecComps, saveVulnerability.getDftChkHitrust().split(","));
        }
        dupCheckedCompliances.removeAll(actSecComps);
        List<ComplianceInfoDTO> toActivateSecondaryComp = analystValidationDAO.getHitrustCrossWalkInfoByCtrlCD(stringFromList(dupCheckedCompliances));
        analystValidationDAO.updateSecondaryComplianceInfo(toActivateSecondaryComp, saveVulnerability, sessionDTO, ApplicationConstants.COMPLIANCE_ACTIVE);
        
        //Get secondary compliances existing in database for that vulnerability
        List<String> prevCheckedSecCompliances = analystValidationDAO.getSecurityCtrlCodeByVulnerabilityKey(saveVulnerability, "S");
        List<ComplianceInfoDTO> insertSecondaryComp = analystValidationDAO.getHitrustCrossWalkInfoByCtrlCD(stringFromList(selectedCompliances));
        
        List<ComplianceInfoDTO> newSecCompList = new ArrayList<>();
        for(ComplianceInfoDTO compObj : insertSecondaryComp){
            if(!prevCheckedSecCompliances.contains(compObj.getControlCode())){
                newSecCompList.add(compObj);
            }
        }
        //Insert Secondary Compliances
        analystValidationDAO.saveSecondaryComplianceInfo(newSecCompList, saveVulnerability, sessionDTO, ApplicationConstants.COMPLIANCE_ACTIVE);
        return retval;
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
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int deleteComplianceInfo(long vulnerabilityKey) throws AppException {
        return analystValidationDAO.deleteComplianceInfo(vulnerabilityKey);
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
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int saveVulnerabilityInfo(VulnerabilityDTO saveVulnerability, UserProfileDTO sessionDTO) throws AppException {
        String engagementCode = saveVulnerability.getClientEngagementDTO().getEncEngagementCode();
        if (engagementCode != null && !engagementCode.equalsIgnoreCase("")) {
            String decEngagementCode = encDec.decrypt(engagementCode);
            saveVulnerability.getClientEngagementDTO().setEngagementCode(decEngagementCode);
        }
        int sourceKey = masterDAO.fetchEntityIdByEntityName(ApplicationConstants.DB_CONSTANT_SOURCE, ApplicationConstants.DB_SOURCE_NAME);
        saveVulnerability.setSourceCode(sourceKey);
        return analystValidationDAO.saveVulnerabilityInfo(saveVulnerability, sessionDTO);
    }

    /**
     * addHitrustToVulnerability
     *
     * @param vulnerabilityDTO
     * @return
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public VulnerabilityDTO addHitrustToVulnerability(VulnerabilityDTO vulnerabilityDTO) throws AppException {
        List<ComplianceInfoDTO> complianceList = analystValidationDAO.getComplianceInformation();
        List<String> cKList = new ArrayList();
        Collections.addAll(cKList, vulnerabilityDTO.getCheckedHitrustValues().split(","));
        /* if hitrust values passed with , separated and to avoid duplicates*/
         Set chkHashSet = new HashSet(cKList);
         cKList = new ArrayList(chkHashSet);
        List<ComplianceInfoDTO> checkedList = new ArrayList<>();
        for (ComplianceInfoDTO clDTO : complianceList) {

            for (String checkedList1 : cKList) {
                if (checkedList1.equalsIgnoreCase(clDTO.getControlCode())) {
                    checkedList.add(clDTO);
                }
            }

        }
        vulnerabilityDTO.setComplianceList(checkedList);
        Map map = hitrustCrossWalkListToMapConversion(vulnerabilityDTO.getComplianceList());
        vulnerabilityDTO.getCveInformationDTO().setComplianceMap(map);
        List<ComplianceInfoDTO> hitrustCrossWalk = analystValidationDAO.getHitrustCrossWalkInfoByCtrlCD(vulnerabilityDTO.getCheckedHitrustValues());
        Map hitrustMap = hitrustCrossWalkListToMapConversion(hitrustCrossWalk);
        vulnerabilityDTO.getCveInformationDTO().setHitrustCrossWalkMap(hitrustMap);
        return vulnerabilityDTO;
    }

    /**
     * fetchComplianceInformation for hitrust
     *
     * @return
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public Map<String, LinkedHashMap<String, ArrayList<ComplianceInfoDTO>>> fetchComplianceInformation() throws AppException {
        List<ComplianceInfoDTO> complianceList = new ArrayList<>();
        Map<String, LinkedHashMap<String, ArrayList<ComplianceInfoDTO>>> retMap = new HashMap<>();
        complianceList = analystValidationDAO.getComplianceInformation();
        if (!complianceList.isEmpty()) {
            retMap = ComplianceInformationListToMapConversion(complianceList);
        }
        return retMap;
    }

    /**
     *
     * @param saveVulnerability
     * @param sessionDTO
     * @return
     * @throws AppException
     */
    @Override
    public int updateVulnerabilityInfo(VulnerabilityDTO saveVulnerability, UserProfileDTO sessionDTO) throws AppException {
        String decInstanceIdentifier = "";
        if (saveVulnerability.getEncInstanceIdentifier() != null
                && !saveVulnerability.getEncInstanceIdentifier().equalsIgnoreCase("")) {
            decInstanceIdentifier = encDec.decrypt(saveVulnerability.getEncInstanceIdentifier());
            saveVulnerability.setInstanceIdentifier(decInstanceIdentifier);
        }
        if(saveVulnerability.getStatusCode().equalsIgnoreCase("C") || saveVulnerability.getStatusCode().equalsIgnoreCase("CE")){
        } else {
            saveVulnerability.setClosedDate("");
            saveVulnerability.setClosedComments("");
        }
        return analystValidationDAO.updateVulnerabilityInfo(saveVulnerability, sessionDTO);
    }

    /**
     * This method for fetch vulnerability details based on vulnerability
     * instance for excel export
     *
     * @param vulnerabilityDTO
     * @return List<VulnerabilityDTO>
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<VulnerabilityDTO> vulnerabilityDetailsForExcelExport(final VulnerabilityDTO vulnerabilityDTO) throws AppException {
        logger.info("Start: AnalystValidationServiceImpl: vulnerabilityDetailsForExcelExport() to fetch vulnerability(ies) for excel export");
        List<VulnerabilityDTO> vulnerabilityList = new ArrayList<>();
        if (vulnerabilityDTO.getInstanceIdentifier().contains(",")) {
            String[] vulnerabilityInstances = vulnerabilityDTO.getInstanceIdentifier().split(",");
            for (String instance : vulnerabilityInstances) {
                VulnerabilityDTO exportExcelDto = analystValidationDAO.viewVulnerabilityDetails(Long.parseLong(encDec.decrypt(instance)),vulnerabilityDTO.getOrgSchema());
                vulnerabilityList.add(exportExcelDto);
            }
        } else {
            VulnerabilityDTO exportExcelDto = analystValidationDAO.viewVulnerabilityDetails(
                    Long.parseLong(encDec.decrypt(vulnerabilityDTO.getInstanceIdentifier())),vulnerabilityDTO.getOrgSchema());
            vulnerabilityList.add(exportExcelDto);
        }
        logger.info("End: AnalystValidationServiceImpl: vulnerabilityDetailsForExcelExport() to fetch vulnerability(ies) for excel export");
        return vulnerabilityList;
    }

    /**
     * This method for change status of service(s) for an engagement
     *
     * @param vulnerabilityDTO
     * @param userDto
     * @return int
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class}, isolation = Isolation.DEFAULT)
    public int updateServicesStatus(final VulnerabilityDTO vulnerabilityDTO, final UserProfileDTO userDto) throws AppException {
        logger.info("Start: AnalystValidationServiceImpl: updateServicesStatus() to update service status");
        List<String> serviceCodes = null;
        List<SecurityServiceDTO> servicesList = analystValidationDAO.analystEngagementServices(
                vulnerabilityDTO.getClientEngagementDTO().getEngagementCode());
        if (null != vulnerabilityDTO.getSecurityServiceObj() && null != vulnerabilityDTO.getSecurityServiceObj().getSecurityServiceCode()) {
            serviceCodes = Arrays.asList(vulnerabilityDTO.getSecurityServiceObj().getSecurityServiceCode().split("\\s*,\\s*"));
        }
        int retVal = 0;

        for (SecurityServiceDTO serviceDto : servicesList) {
            if (null != serviceCodes && !serviceCodes.isEmpty() && serviceCodes.contains(serviceDto.getSecurityServiceCode())) {
                int statusKey = masterDAO.fetchEntityIdByEntityName(ApplicationConstants.DB_ROW_SERVICE_STATUS,
                        ApplicationConstants.DB_ROW_STATUS_REVIEWED);
                retVal = analystValidationDAO.updateServicesStatus(vulnerabilityDTO.getClientEngagementDTO().getEngagementCode(),
                        serviceDto.getSecurityServiceCode(), userDto.getUserProfileKey(), statusKey);
            } else {
                int statusKey = masterDAO.fetchEntityIdByEntityName(ApplicationConstants.DB_ROW_SERVICE_STATUS,
                        ApplicationConstants.DB_ROW_STATUS_NOT_REVIEWED);
                retVal = analystValidationDAO.updateServicesStatus(vulnerabilityDTO.getClientEngagementDTO().getEngagementCode(),
                        serviceDto.getSecurityServiceCode(), userDto.getUserProfileKey(), statusKey);
            }
        }
        logger.info("End: AnalystValidationServiceImpl: updateServicesStatus() to update service status");
        return retVal;
    }

   /**
     * getRiskDetailsByCvssScore for CVSS Risk Details
     *
     * @param vulnerabilityDTO
     * @return vulnerabilityDTO
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public VulnerabilityDTO getRiskDetailsByCvssScore(VulnerabilityDTO vulnerabilityDTO) throws AppException {
        return analystValidationDAO.getRiskDetailsByCvssScore(vulnerabilityDTO);
    }

    /**
     * Basic details of vulnerability in Update vulnerability
     *
     * @param vulnerabilityDTO
     * @return
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public VulnerabilityDTO fetchVulnerabilityBasicDetails(VulnerabilityDTO vulnerabilityDTO) throws AppException {
        VulnerabilityDTO vulDTO = analystValidationDAO.viewVulnerabilityDetails
                    (Long.valueOf(vulnerabilityDTO.getInstanceIdentifier()),vulnerabilityDTO.getOrgSchema());
        
        vulnerabilityDTO.setInstanceIdentifier(vulnerabilityDTO.getInstanceIdentifier());
        vulnerabilityDTO.getClientEngagementDTO().setSecurityServiceName
                   (vulDTO.getClientEngagementDTO().getSecurityServiceName());
        vulnerabilityDTO.setSourceName(vulDTO.getSourceName());
        vulnerabilityDTO.setIpAddress(vulDTO.getIpAddress());
        vulnerabilityDTO.setScanIdentifier(vulDTO.getScanIdentifier());
        vulnerabilityDTO.setScanStartDate(vulDTO.getScanStartDate());
        vulnerabilityDTO.setScanEndDate(vulDTO.getScanEndDate());
        vulnerabilityDTO.setSoftwareName(vulDTO.getSoftwareName());
        vulnerabilityDTO.setHostName(vulDTO.getHostName());
        vulnerabilityDTO.setDomainName(vulDTO.getDomainName());
        vulnerabilityDTO.setAppURL(vulDTO.getAppURL());
        vulnerabilityDTO.setOperatingSystem(vulDTO.getOperatingSystem());
        vulnerabilityDTO.setNetbiosName(vulDTO.getNetbiosName());
        vulnerabilityDTO.setMacAddress(vulDTO.getMacAddress());
        vulnerabilityDTO.setPortNumber(vulDTO.getPortNumber());
        vulnerabilityDTO.setThreatCategory(vulDTO.getThreatCategory());
        vulnerabilityDTO.setCreatedDate(vulDTO.getCreatedDate());
        vulnerabilityDTO.getCveInformationDTO().setSrcBaseScore(vulDTO.getCveInformationDTO().getSrcBaseScore());
        return vulnerabilityDTO;
    }

    /**
     * Update vulnerability (Transact DAO calling method)
     * @param saveVulnerability
     * @param sessionDTO
     * @return
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int updateVulnerability(VulnerabilityDTO saveVulnerability, UserProfileDTO sessionDTO) throws AppException {
        int cveIdCount = analystValidationDAO.checkForCveId(saveVulnerability.getCveInformationDTO().getCveIdentifier());
        if (cveIdCount == 0) {
            saveVulnerability.getCveInformationDTO().setCveIdentifier(ApplicationConstants.EMPTY_STRING);
        }
        int retVal = analystValidationTransactDAO.updateVulnerability(saveVulnerability, sessionDTO);
        int findingCnt = analystValidationDAO.findingNameCount(saveVulnerability.getVulnerabilityName());
        if(retVal>0 && findingCnt == 0 && !saveVulnerability.getRootCauseCode().isEmpty()){
            analystValidationDAO.insertFindingName(saveVulnerability, sessionDTO);
        }
        return retVal;
    }

    /**
     * This method for fetch OS (operating system) List
     *
     * @return List<MasterLookUpDTO>
     * @throws AppException
     */
    @Override
    public List<MasterLookUpDTO> fetchOSList() throws AppException {
        return analystValidationDAO.fetchOSList();
    }
    
    /**
     * This method for convert List data to Map
     *
     * @param complainceList
     * @return Map
     */
    public Map<String, ArrayList<ComplianceInfoDTO>> hitrustCrossWalkListToMapConversion(List<ComplianceInfoDTO> complainceList) {

//        Map<String, LinkedHashMap<String, ArrayList<ComplianceInfoDTO>>> complianceMap = new LinkedHashMap<>();
        Map<String, ArrayList<ComplianceInfoDTO>> complianceFamilyMap = new LinkedHashMap<>();

        for (ComplianceInfoDTO cInfoDTO : complainceList) {

            if(complianceFamilyMap.containsKey(cInfoDTO.getComplicanceCode())){
                ArrayList<ComplianceInfoDTO> familyList = complianceFamilyMap.get(cInfoDTO.getComplicanceCode());
                familyList.add(cInfoDTO);
                
            }else{
                ArrayList<ComplianceInfoDTO> familyList = new ArrayList<ComplianceInfoDTO>();
                familyList.add(cInfoDTO);
                complianceFamilyMap.put(cInfoDTO.getComplicanceCode(), familyList);
            }
        }

        return complianceFamilyMap;
    }

    /**
     * This method is used find whether CVE ID is present in our database or not
     *
     * @param cveIdentifier
     * @return
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int checkForCveId(String cveIdentifier) throws AppException {
        return analystValidationDAO.checkForCveId(cveIdentifier);
    }

    @Override
    public List<SummaryDropDownModel> analystSeverityList() throws AppException {
         SecurityModel securityModel = new SecurityModel();
        return reportsDAOImpl.fetchSeverityListForSummary(securityModel);
    }

    @Override
    public List<MasterLookUpDTO> analystSourceList() throws AppException {
        return analystValidationDAO.analystSourceList();
    }
    
    /**
     * This method used to fetch manual vulnerability names
     *
     * @return List<String>
     * @throws AppException
     */
    @Override
    public List<VulnerabilityDTO> fetchManualVulnerabilityNames() throws AppException {
        return analystValidationDAO.fetchManualVulnerabilityNames();
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<CVEInformationDTO> fetchCVEList(VulnerabilityDTO vulnerabilityObj) throws AppException {
        return analystValidationDAO.fetchCVEList(vulnerabilityObj);
    }
    
    /* This method is used to build coma(,) seperated string from List */
    public String stringFromList(List<String> complianceList) {
        String checkedComp = "";
        String limiter = "";
        if (null != complianceList) {
            for (String s : complianceList) {
                checkedComp += limiter;
                checkedComp += s;
                limiter = ",";
            }
        }
        return checkedComp;
    }

    @Override
    public List<OwaspDTO> fetchOwaspList() throws AppException {
        return analystValidationDAO.fetchOwaspList();
    }

    @Override
    public int updateFindingStatus(String flag,String orgSchema, long findingID, String statusCode,String closeReason,UserProfileDTO sessionDTO) throws AppException {
            return analystValidationDAO.updateFindingStatus(flag,orgSchema, findingID, statusCode,closeReason,sessionDTO);
    }
}
