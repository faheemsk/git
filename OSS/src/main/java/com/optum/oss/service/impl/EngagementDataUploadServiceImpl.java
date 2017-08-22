/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service.impl;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.constants.PermissionConstants;
import com.optum.oss.dao.impl.EngagementDataUploadDAOImpl;
import com.optum.oss.dao.impl.MasterLookUpDAOImpl;
import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.dto.ManageServiceUserDTO;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.EngagementDataUploadMailHelper;
import com.optum.oss.service.EngagementDataUploadService;
import com.optum.oss.transact.dao.impl.EngagementDataUploadTransactDAOImpl;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author akeshavulu
 */
@Service
public class EngagementDataUploadServiceImpl implements EngagementDataUploadService {

    private static final Logger logger = Logger.getLogger(EngagementDataUploadServiceImpl.class);

    @Resource(name = "myProperties")
    private Properties myProperties;

    /*
     Start Autowiring the required Class instances
     */
    @Autowired
    EngagementDataUploadDAOImpl engagementDataUploadDAO;

    @Autowired
    private MasterLookUpDAOImpl masterDAO;

    @Autowired
    EngagementDataUploadTransactDAOImpl engagementDataUploadTransactDAO;

    @Autowired
    private EngagementDataUploadMailHelper engmentDataUploadMailHelper;
    
    @Autowired
    private MasterLookUpServiceImpl masterLookUpServiceImpl;

    /*
     End Autowiring the required Class instances
     */
    /**
     *
     * @param engagementCode
     * @return ClientEngagementDTO
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public ClientEngagementDTO fetchServiceEngagementDetailsByEgmntCode(String engagementCode, String serviceCode, long userID) throws AppException {

        return engagementDataUploadDAO.fetchServiceEngagementDetailsByEgmntCode(engagementCode, serviceCode, userID);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public int saveUploadedFileDetails(ClientEngagementDTO clientEngagementDTO, UserProfileDTO userDto,String fileName) throws AppException {
        int retVal = 0;
        retVal = engagementDataUploadTransactDAO.saveEngagementDataUploadDetails(clientEngagementDTO, userDto);
        if (retVal > 0) {
            //mail to Engagement/Partner Lead when file is uploaded for a service
            List<UserProfileDTO> userDetailsList = engagementDataUploadDAO.engagementLeadDetailsByCode(clientEngagementDTO.getEngagementCode());
            if (null != userDetailsList) {
                for (UserProfileDTO userProfileDto : userDetailsList) {
                    if (userProfileDto.getUserID().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_INTERNAL)) {
                        engmentDataUploadMailHelper.mailOnEngagementFileUpload(userProfileDto, clientEngagementDTO, userDto,fileName);
                    }
                    if (userProfileDto.getUserID().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_PARTNER)) {
                        engmentDataUploadMailHelper.mailOnEngagementFileUpload(userProfileDto, clientEngagementDTO, userDto,fileName);
                    }
                }
            }

        }
        return retVal;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<MasterLookUpDTO> fetchMasterLookUpEntitiesByEntityType(String entityType) throws AppException {
        return masterDAO.fetchMasterLookUpEntitiesByEntityType(entityType);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public int saveEngagementDataUploadDetails(ClientEngagementDTO clientEngagementDTO) throws AppException {
        return engagementDataUploadDAO.saveEngagementDataUploadDetails(clientEngagementDTO);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<ClientEngagementDTO> fetchUploadedFilesDetails(ClientEngagementDTO clientEngagementDTO) throws AppException {
        List<ClientEngagementDTO> uploadedFilesList = new ArrayList<ClientEngagementDTO>();
        uploadedFilesList = engagementDataUploadDAO.fetchUploadedFilesDetails(clientEngagementDTO);
        return uploadedFilesList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public int deleteUploadedFileDetails(int fileID) throws AppException {
        return engagementDataUploadDAO.deleteUploadedFilesDetails(fileID);
    }

//    @Override
//    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
//            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
//            isolation = Isolation.READ_COMMITTED)
//    public int updateLockUnlockServiceData(String engagementCode, String serviceCode, long lockIndicator) throws AppException {
//        int retVal = 0;
//        List<UserProfileDTO> userDetailsList = new ArrayList<>();
//        retVal = engagementDataUploadDAO.updateLockUnlockServiceData(engagementCode, serviceCode, lockIndicator);
//        if (lockIndicator == ApplicationConstants.DB_CONSTANT_Service_LOCK_INDICATOR) {
//            int returnValue = engagementDataUploadDAO.emailNotificationAllServicesLocked(engagementCode);
//            if (returnValue == ApplicationConstants.DB_CONSTANT_Service_LOCK_INDICATOR) {
//
//                userDetailsList = engagementDataUploadDAO.engagementLeadDetailsByCode(engagementCode);
//                if (null != userDetailsList) {
//                    for (UserProfileDTO userProfileDto : userDetailsList) {
//                        if (userProfileDto.getUserID().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_INTERNAL)) {
//                            engmentDataUploadMailHelper.mailOnEngagementFileUpload(userProfileDto, clientEngagementDTO, userDto);
//                        }
//                    }
//                }
//
//                engmentDataUploadMailHelper.mailToEngmntLeadAllServicesLocked(null, null, null);
//            }
//        }
//
//        return retVal;
//    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public Map engagementDataUploadWorkList(final UserProfileDTO userProfileDTO,final String reqToken) throws AppException {
        String userFlag = ApplicationConstants.DB_CONSTANT_USER_TYPE_ANALYST;
        Set<String> permissionSetEngagement = userProfileDTO.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_ENGAGEMENT_USERS_SUB_MENU);
        Set<String> permissionSetDataUpload = userProfileDTO.getSubMenuPermissionMap().get(PermissionConstants.ENGAGEMENT_DATA_UPLOAD_MENU);

        /*Condition Checking for the Lead((Internal/Partnet) */
        if (permissionSetEngagement != null) {
            if ((permissionSetEngagement.contains(PermissionConstants.ADD_USER_TO_SERVICE) || permissionSetEngagement.contains(PermissionConstants.EDIT_USER_TO_SERVICE))) {

                if (userProfileDTO.getUserTypeObj().getLookUpEntityName().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_INTERNAL)) {
                    
                    if (permissionSetDataUpload != null) {
                        if(permissionSetDataUpload.contains(PermissionConstants.ADD_DOCUMENT_UPLOAD)){
                             userFlag = ApplicationConstants.DB_CONSTANT_ENG_LEAD_ENG_ANALYST;
                        }else{
                         userFlag = ApplicationConstants.DB_CONSTANT_ENGAGEMENT_LEAD;
                    }
                    }else{
                         userFlag = ApplicationConstants.DB_CONSTANT_ENGAGEMENT_LEAD;
                    }
                }
                if (userProfileDTO.getUserTypeObj().getLookUpEntityName().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_PARTNER)) {
                    userFlag = ApplicationConstants.DB_CONSTANT_PARTNER_LEAD;
                }

            } else {
                userFlag = ApplicationConstants.DB_CONSTANT_USER_TYPE_ANALYST;
            }
            /*Condition Checking Analyst */
        } else if (permissionSetDataUpload != null) {
            userFlag = ApplicationConstants.DB_CONSTANT_USER_TYPE_ANALYST;
        }

        List<ManageServiceUserDTO> list = engagementDataUploadDAO.engagementDataUploadWorkList(userProfileDTO, userFlag,reqToken);
        return engagementServicesListToMapConversion(list);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public Map searchEngagementServicesWorkList(final UserProfileDTO userProfileDTO, final ManageServiceUserDTO manageServiceUserDTOData,final String reqToken) throws AppException {
        String userFlag = ApplicationConstants.DB_CONSTANT_USER_TYPE_ANALYST;
        Set<String> permissionSetEngagement = userProfileDTO.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_ENGAGEMENT_USERS_SUB_MENU);
        Set<String> permissionSetDataUpload = userProfileDTO.getSubMenuPermissionMap().get(PermissionConstants.ENGAGEMENT_DATA_UPLOAD_MENU);

        if (permissionSetEngagement != null) {
            if ((permissionSetEngagement.contains(PermissionConstants.ADD_USER_TO_SERVICE) || permissionSetEngagement.contains(PermissionConstants.EDIT_USER_TO_SERVICE))) {
                if (userProfileDTO.getUserTypeObj().getLookUpEntityName().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_INTERNAL)) {

                    if (permissionSetDataUpload != null) {
                        if (permissionSetDataUpload.contains(PermissionConstants.ADD_DOCUMENT_UPLOAD)) {
                            userFlag = ApplicationConstants.DB_CONSTANT_ENG_LEAD_ENG_ANALYST;
                        } else {
                            userFlag = ApplicationConstants.DB_CONSTANT_ENGAGEMENT_LEAD;
                        }
                    } else {
                        userFlag = ApplicationConstants.DB_CONSTANT_ENGAGEMENT_LEAD;
                    }
                }
                if (userProfileDTO.getUserTypeObj().getLookUpEntityName().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_PARTNER)) {
                    userFlag = ApplicationConstants.DB_CONSTANT_PARTNER_LEAD;
                }

            } else {
                userFlag = ApplicationConstants.DB_CONSTANT_USER_TYPE_ANALYST;
            }
            /*Condition Checking Analyst */
        } else if (permissionSetDataUpload != null) {
            userFlag = ApplicationConstants.DB_CONSTANT_USER_TYPE_ANALYST;
        }
        manageServiceUserDTOData.setUserFlag(userFlag);
        if(null == manageServiceUserDTOData.getOrganizationName()) {
            manageServiceUserDTOData.setOrganizationName("");
        }
        if(null == manageServiceUserDTOData.getClientEngagementCode()) {
            manageServiceUserDTOData.setClientEngagementCode("");
        }
        if(null == manageServiceUserDTOData.getEngagementName()) {
            manageServiceUserDTOData.setEngagementName("");
        }
        if(null == manageServiceUserDTOData.getServiceName()) {
            manageServiceUserDTOData.setServiceName("");
        }
        if(null == manageServiceUserDTOData.getStartDate()) {
            manageServiceUserDTOData.setStartDate("");
        }
        if(null == manageServiceUserDTOData.getEndDate()) {
            manageServiceUserDTOData.setEndDate("");
        }
        if(null == manageServiceUserDTOData.getUpdateDate()) {
            manageServiceUserDTOData.setUpdateDate("");
        }
        List<ManageServiceUserDTO> list = engagementDataUploadDAO.searchEngagementServicesWorkList(userProfileDTO, manageServiceUserDTOData, reqToken);

        return engagementServicesListToMapConversion(list);
    }

    public Map engagementServicesListToMapConversion(List<ManageServiceUserDTO> list) {

        Map<String, LinkedHashSet<String>> engagementMap = new LinkedHashMap<String, LinkedHashSet<String>>();
        Map<String, ArrayList<String>> serviceCountMap = new LinkedHashMap<String, ArrayList<String>>();
        Map<String, ArrayList<ManageServiceUserDTO>> serviceMap = new LinkedHashMap<String, ArrayList<ManageServiceUserDTO>>();

        /*Start engagement Map Construction*/
        for (ManageServiceUserDTO manageServiceUserDTO : list) {

            if (engagementMap.containsKey(manageServiceUserDTO.getOrganizationKey() + ApplicationConstants.SYMBOL_SPLITING + manageServiceUserDTO.getOrganizationName())) {

                LinkedHashSet<String> engagementSet = engagementMap.get(manageServiceUserDTO.getOrganizationKey() + ApplicationConstants.SYMBOL_SPLITING + manageServiceUserDTO.getOrganizationName());
                engagementSet.add(manageServiceUserDTO.getClientEngagementCode() + ApplicationConstants.SYMBOL_SPLITING + manageServiceUserDTO.getEngagementName());
                /* services Count for Each Organization */
                ArrayList<String> serviceCount = serviceCountMap.get(manageServiceUserDTO.getOrganizationKey() + ApplicationConstants.SYMBOL_SPLITING + manageServiceUserDTO.getOrganizationName());
                serviceCount.add(manageServiceUserDTO.getClientEngagementCode());

            } else {
                LinkedHashSet<String> engagementSet = new LinkedHashSet<String>();
                engagementSet.add(manageServiceUserDTO.getClientEngagementCode() + ApplicationConstants.SYMBOL_SPLITING + manageServiceUserDTO.getEngagementName());

                ArrayList<String> serviceCount = new ArrayList<String>();
                serviceCount.add(manageServiceUserDTO.getClientEngagementCode());

                engagementMap.put(manageServiceUserDTO.getOrganizationKey() + ApplicationConstants.SYMBOL_SPLITING + manageServiceUserDTO.getOrganizationName(), engagementSet);
                serviceCountMap.put(manageServiceUserDTO.getOrganizationKey() + ApplicationConstants.SYMBOL_SPLITING + manageServiceUserDTO.getOrganizationName(), serviceCount);
            }

        }
        /*End engagement Map Construction*/

        /*Start Engagement Service Map Construction*/
        for (ManageServiceUserDTO manageServiceUserDTO : list) {

            if (serviceMap.containsKey(manageServiceUserDTO.getClientEngagementCode() + ApplicationConstants.SYMBOL_SPLITING + manageServiceUserDTO.getEngagementName())) {

                ArrayList<ManageServiceUserDTO> serviceList = serviceMap.get(manageServiceUserDTO.getClientEngagementCode() + ApplicationConstants.SYMBOL_SPLITING + manageServiceUserDTO.getEngagementName());
                serviceList.add(manageServiceUserDTO);

            } else {
                ArrayList<ManageServiceUserDTO> serviceList = new ArrayList<ManageServiceUserDTO>();

                serviceList.add(manageServiceUserDTO);
                serviceMap.put(manageServiceUserDTO.getClientEngagementCode() + ApplicationConstants.SYMBOL_SPLITING + manageServiceUserDTO.getEngagementName(), serviceList);
            }
        }
        /*End Engagement Service Map Construction*/
        Map map = new LinkedHashMap();
        map.put("engagementMap", engagementMap);
        map.put("serviceMap", serviceMap);
        map.put("organizationServieCount", serviceCountMap);

        return map;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int validateDuplicateFileName(String fileName, String engmntCode, String serviceCode) throws AppException {
        return engagementDataUploadDAO.validateDuplicateFileName(fileName, engmntCode, serviceCode);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<UserProfileDTO> engagementLeadDetailsByCode(String engagementCode) throws AppException {
        return engagementDataUploadDAO.engagementLeadDetailsByCode(engagementCode);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int emailNotificationAllServicesLocked(String engagementCode) throws AppException {
        return engagementDataUploadDAO.emailNotificationAllServicesLocked(engagementCode);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public int updateLockUnlockServiceData(ClientEngagementDTO clientEngagementDTO, UserProfileDTO userDto) throws AppException {
        int retVal = 0;
        long serviceDataLockIndicator = 0;
        List<UserProfileDTO> userDetailsList = new ArrayList<>();
        if (clientEngagementDTO.isLockCheckbox()) {
            serviceDataLockIndicator = ApplicationConstants.DB_CONSTANT_FILE_LOCK_INDICATOR;

        } else {
            serviceDataLockIndicator = ApplicationConstants.DB_CONSTANT_FILE_UNLOCK_INDICATOR;
        }
        retVal = engagementDataUploadDAO.updateLockUnlockServiceData(clientEngagementDTO.getEngagementCode(),
                clientEngagementDTO.getSecurityServiceCode(), serviceDataLockIndicator);

        if (retVal == ApplicationConstants.DB_CONSTANT_FILE_LOCK_INDICATOR) {
            //send mail to engagement analyst when service is locked
            List<UserProfileDTO> engmntAnalystUserDetailsList = engagementDataUploadDAO.engagementAnalystDetails(clientEngagementDTO.getEngagementCode(),
                    clientEngagementDTO.getSecurityServiceCode());
            for (UserProfileDTO userProfileDTO : engmntAnalystUserDetailsList) {
                engmentDataUploadMailHelper.mailToEngmntAnalystServiceLocked(userProfileDTO, clientEngagementDTO, userDto);
            }

        } else {
            //send mail to engagement analyst when service is unlocked
            List<UserProfileDTO> engmntAnalystUserDetailsList = engagementDataUploadDAO.engagementAnalystDetails(clientEngagementDTO.getEngagementCode(),
                    clientEngagementDTO.getSecurityServiceCode());
            for (UserProfileDTO userProfileDTO : engmntAnalystUserDetailsList) {
                engmentDataUploadMailHelper.mailToEngmntAnalystServiceUnlocked(userProfileDTO, clientEngagementDTO, userDto);
            }
        }

        if (serviceDataLockIndicator == ApplicationConstants.DB_CONSTANT_Service_LOCK_INDICATOR) {
            int returnValue = engagementDataUploadDAO.emailNotificationAllServicesLocked(clientEngagementDTO.getEngagementCode());
            if (returnValue == ApplicationConstants.DB_CONSTANT_Service_LOCK_INDICATOR) {
                userDetailsList = engagementDataUploadDAO.engagementLeadDetailsByCode(clientEngagementDTO.getEngagementCode());
                if (null != userDetailsList) {
                    for (UserProfileDTO userProfileDto : userDetailsList) {
                        if (userProfileDto.getUserID().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_INTERNAL)) {
                            List<ClientEngagementDTO> fileList = engagementDataUploadDAO.getUploadedFileDetailsByEngagementId(clientEngagementDTO.getEngagementCode());
                            StringBuilder htmlcontent = new StringBuilder("<tr><td>");
                            int itr = 1;
                            for (ClientEngagementDTO dto : fileList) {
                                htmlcontent.append(itr + "." + "</td><td>" + dto.getUploadFileName() + "</td><td>" + dto.getCreatedByUser() + "</td></tr>");
                                itr++;
                            }
                            engmentDataUploadMailHelper.mailToEngmntLeadAllServicesLocked(userProfileDto, clientEngagementDTO, htmlcontent.toString(), userDto);
                        }
                    }
                }
            }
        }

        return retVal;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public ClientEngagementDTO fetchUploadedFileDetails(String fileName, String engmntCode) throws AppException {
        return engagementDataUploadDAO.fetchUploadedFileDetails(fileName, engmntCode);
    }

//    @Override
//    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
//            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
//            isolation = Isolation.READ_COMMITTED)
//    public int updateUploadFileStatus(String fileName, String engmntCode, int statusKey) throws AppException {
//        return engagementDataUploadDAO.updateUploadFileStatus(fileName, engmntCode, statusKey);
//    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public int scanInProgressFileCount(ClientEngagementDTO clientEngagementDTO) throws AppException {
        int status = masterLookUpServiceImpl.fetchEntityIdByEntityName(ApplicationConstants.DB_CONSTANT_FILE_STATUS, ApplicationConstants.DB_CONSTANT_FILE_STATUS_SCAN_INPROGRESS);;
        return engagementDataUploadDAO.uploadFileCountByStatus(clientEngagementDTO, status);
    }
}
