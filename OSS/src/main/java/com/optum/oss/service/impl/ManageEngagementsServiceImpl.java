/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service.impl;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.constants.PermissionConstants;
import com.optum.oss.constants.ReportsAndDashboardConstants;
import com.optum.oss.dao.impl.ManageEngagementsDAOImpl;
import com.optum.oss.dao.impl.MasterLookUpDAOImpl;
import com.optum.oss.dao.impl.UserManagementDAOImpl;
import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.dto.ClientEngagementSourceDTO;
import com.optum.oss.dto.EngagementPartnerUserDTO;
import com.optum.oss.dto.ManageServiceUserDTO;
import com.optum.oss.dto.RoleDTO;
import com.optum.oss.dto.SecurityPackageDTO;
import com.optum.oss.dto.SecurityServiceDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.ClientEngagementMailHelper;
import com.optum.oss.service.ManageEngagementsService;
import com.optum.oss.transact.dao.impl.ManageEngagementsTransactDAOImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
public class ManageEngagementsServiceImpl implements ManageEngagementsService {

    private static final Logger logger = Logger.getLogger(ManageEngagementsServiceImpl.class);

    /*
     Start: Autowiring the required Class instances
     */
    @Autowired
    private ManageEngagementsDAOImpl manageEngagementsDAO;
    @Autowired
    private ManageEngagementsTransactDAOImpl manageEngagementsTransactDAOImpl;
    @Autowired
    private MasterLookUpDAOImpl masterDAO;
    @Autowired
    private UserManagementServiceImpl userManagementServiceImpl;
    @Autowired
    private ClientEngagementMailHelper clientEngagementMailHelper;
    @Autowired
    private UserManagementDAOImpl userManagementDAO;
    /*
     End: Autowiring the required Class instances
     */

    @Override
    public int saveEngagementInfo(ClientEngagementDTO clientEngagementDTO, UserProfileDTO sessionDTO) throws AppException {
        int engStatus = masterDAO.fetchEntityIdByEntityName(ApplicationConstants.ENGMNT_STATUS_ENTITY_TYPE, ApplicationConstants.ENGMNT_STATUS_OPEN);
        clientEngagementDTO.setEngagementStatusKey(engStatus);
        return manageEngagementsDAO.saveEngagementInfo(clientEngagementDTO, sessionDTO);
    }

    @Override
    public List<SecurityPackageDTO> fetchSecurityPackages() throws AppException {
        return manageEngagementsDAO.fetchSecurityPackages();
    }

    @Override
    public List<UserProfileDTO> fetchUsersByOrgIDAndUserTypeID(long orgId, String userType, final String flag, List<String> mappedUsers) throws AppException {
        List<UserProfileDTO> userProfileList = manageEngagementsDAO.fetchUsersByOrgIDAndUserTypeID(orgId, userType, flag, mappedUsers);
        Collections.sort(userProfileList, new Comparator<UserProfileDTO>() {
            public int compare(UserProfileDTO profileDto1, UserProfileDTO profileDto2) {
                String firtstName1 = profileDto1.getFirstName().toUpperCase();
                String firstName2 = profileDto2.getFirstName().toUpperCase();
                return firtstName1.compareTo(firstName2);
            }
        });
        return userProfileList;
    }

    @Override
    public List<SecurityServiceDTO> fetchServicesByPackage(String packageID) throws AppException {
        return manageEngagementsDAO.fetchServicesByPackage(packageID);
    }

    @Override
    public int saveEngagementServices(ClientEngagementDTO clientEngagementDTO, UserProfileDTO sessionDTO) throws AppException {
        List<String> serviceCodes = Arrays.asList(clientEngagementDTO.getSelectedServices().split("\\s*,\\s*"));
        int clientEngagementServiceSatusKey = masterDAO.fetchEntityIdByEntityName(ApplicationConstants.DB_ROW_SERVICE_STATUS,
                ApplicationConstants.DB_ROW_STATUS_NOT_REVIEWED);
        clientEngagementDTO.setEngagementStatusKey(clientEngagementServiceSatusKey);
        return manageEngagementsDAO.saveEngagementServices(clientEngagementDTO, sessionDTO, serviceCodes);
    }

    @Override
    public int saveNewEngagementServices(ClientEngagementDTO clientEngagementDTO, UserProfileDTO sessionDTO, List<String> services) throws AppException {
        int clientEngagementServiceSatusKey = masterDAO.fetchEntityIdByEntityName(ApplicationConstants.DB_ROW_SERVICE_STATUS,
                ApplicationConstants.DB_ROW_STATUS_NOT_REVIEWED);
        clientEngagementDTO.setEngagementStatusKey(clientEngagementServiceSatusKey);
        return manageEngagementsDAO.saveEngagementServices(clientEngagementDTO, sessionDTO, services);
    }

    @Override
    public int saveEngagementUsers(UserProfileDTO engagementUsrObj, UserProfileDTO sessionDTO, String engCode) throws AppException {
        int retval = manageEngagementsDAO.saveEngagementUsers(engagementUsrObj, sessionDTO, engCode);

        //Fetch engagement details by engagementcode used to send emailcontent
        ClientEngagementDTO engagementObj = viewEngagementByEngmtKey(engCode);

        if (retval > 0) {
            if (ApplicationConstants.USER_TYPE_INTERNAL_KEY == engagementUsrObj.getUserTypeKey()) {
                //Fetch user details of Internal user
                UserProfileDTO internalUserDTO = userManagementServiceImpl.fetchUserInfoByUserID(engagementUsrObj.getUserProfileKey());
                //Mail sent to internl user
                clientEngagementMailHelper.mailOnEngagementSave(internalUserDTO, engagementObj, sessionDTO);
            } else if (ApplicationConstants.USER_TYPE_CLIENT_KEY == engagementUsrObj.getUserTypeKey()) {
                //Fetch user details of Client user
                UserProfileDTO clientUserDTO = userManagementServiceImpl.fetchUserInfoByUserID(engagementUsrObj.getUserProfileKey());
                //Mail sent to client user
                clientEngagementMailHelper.mailToClientLead(clientUserDTO, engagementObj, sessionDTO);
            }
        }
        return retval;
    }

    @Override
    public int saveEngagementSourceInfo(ClientEngagementDTO clientEngagementDTO, UserProfileDTO sessionDTO) throws AppException {
        return manageEngagementsDAO.saveEngagementSourceInfo(clientEngagementDTO, sessionDTO);
    }

    @Override
    public int saveEngagement(ClientEngagementDTO clientEngagementDTO, UserProfileDTO sessionDTO) throws AppException {
        int engagementsCount = getEngagementCountForEngCode(clientEngagementDTO);
        if (engagementsCount > 0) {
            ClientEngagementDTO localObj = new ClientEngagementDTO();
            localObj.setEngagementCode(clientEngagementDTO.getEngagementCode());
            ClientEngagementDTO cEngDTO = manageEngagementsDAO.viewEngagementByEngmtKey(localObj);
            clientEngagementDTO.setEncEngagementCode(cEngDTO.getEncEngagementCode());
            //Fix for issue IN021786 : The client name and package name are set to the object here
            clientEngagementDTO.setClientName(cEngDTO.getClientName());
            clientEngagementDTO.getSecurityPackageObj().setSecurityPackageName(cEngDTO.getSecurityPackageObj().getSecurityPackageName());
            //ACTIVE ENGAGEMENT
            if (cEngDTO.getRowStatusKey() == ApplicationConstants.DB_ROW_STATUS_ACTIVE_VALUE) {
                return -2;
            }
            //INACTIVE(DELETED ENGAGEMENT) ENGAGEMENT - STATUS FOR DELETE IS 3
            if (cEngDTO.getRowStatusKey() == ApplicationConstants.DB_ROW_STATUS_DELETE_VALUE) {
                return -3;
            }
        }
        return manageEngagementsTransactDAOImpl.saveEngagement(clientEngagementDTO, sessionDTO);
    }

    /**
     * This method for fetching Manage Engagements work list
     *
     * @param userDto
     * @param permissionSet
     * @return List<ClientEngagementDTO>
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<ClientEngagementDTO> fetchEngagementsWorklist(final UserProfileDTO userDto, Set<String> permissionSet) throws AppException {
        long organizationKey = 0;
        long userProfileKey = 0;
        String fetchFlag = "";
        if (permissionSet.contains(PermissionConstants.ADD_ENGAGEMENT) || permissionSet.contains(PermissionConstants.EDIT_ENGAGEMENT)) {
            organizationKey = userDto.getOrganizationKey();
            fetchFlag = "C"; //user type is Coordinator
        } else if ((userDto.getUserTypeObj().getLookUpEntityName()).equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_INTERNAL)
                || (userDto.getUserTypeObj().getLookUpEntityName()).equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_PARTNER)) {
            userProfileKey = userDto.getUserProfileKey();
            fetchFlag = "L"; //user type is Internal / Partner lead
        }
        return manageEngagementsDAO.fetchEngagementsWorklist(organizationKey, userProfileKey, fetchFlag);
    }

    /**
     * This method for view Engagements details by client engagement key
     *
     * @param clientEngagementCode
     * @return ClientEngagementDTO
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public ClientEngagementDTO viewEngagementByEngmtKey(final String clientEngagementCode) throws AppException {
        ClientEngagementDTO clientEngagementDTO = new ClientEngagementDTO();
        clientEngagementDTO.setEngagementCode(clientEngagementCode);
        clientEngagementDTO = manageEngagementsDAO.viewEngagementByEngmtKey(clientEngagementDTO);
        clientEngagementDTO = manageEngagementsDAO.fetchSecurityServicesByEngmtId(clientEngagementDTO);
        clientEngagementDTO = manageEngagementsDAO.getClientEngagementSourceDetailsByEngmtKey(clientEngagementDTO);
        //clientEngagementDTO = manageEngagementsDAO.getClientEngagementAssignedUserByEngmtKey(clientEngagementDTO);
        return clientEngagementDTO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<UserProfileDTO> clientEngagementUsers(final String clientEngagementCode) throws AppException {
        List<UserProfileDTO> engUsrsList = manageEngagementsDAO.getClientEngagementAssignedUserByEngmtKey(clientEngagementCode);
        return engUsrsList;
    }

    @Override
    public int updateEngagementInfo(ClientEngagementDTO clientEngagementDTO, UserProfileDTO sessionDTO) throws AppException {
        int engStatus = masterDAO.fetchEntityIdByEntityName(ApplicationConstants.ENGMNT_STATUS_ENTITY_TYPE, ApplicationConstants.ENGMNT_STATUS_OPEN);
        clientEngagementDTO.setEngagementStatusKey(engStatus);
        return manageEngagementsDAO.updateEngagementInfo(clientEngagementDTO, sessionDTO);
    }

    @Override
    public int deleteEngagementServices(String engagementKey, List<String> servicesToDelete, String flag) throws AppException {
        return manageEngagementsDAO.deleteEngagementServices(engagementKey, servicesToDelete, flag);
    }

    @Override
    public int deleteEngagementUsers(String assignKey) throws AppException {
        return manageEngagementsDAO.deleteEngagementUsers(assignKey);
    }

    @Override
    public int deleteEngagementSourceInfo(String engagementKey, UserProfileDTO sessionDTO) throws AppException {
        return manageEngagementsDAO.deleteEngagementSourceInfo(engagementKey, sessionDTO);
    }

    @Override
    public int updateEngagement(ClientEngagementDTO clientEngagementDTO, UserProfileDTO sessionDTO) throws AppException {
        logger.info("End: ManageEngagementsServiceImpl : updateEngagement");
        List<String> selectedCodes = null;
        List<String> serviceToDelete = null;
        List<String> servicesToUpdate = null;
        try {
            selectedCodes = new ArrayList(Arrays.asList(clientEngagementDTO.getSelectedServices().split("\\s*,\\s*")));
            List<String> prevSelectedCodes = new ArrayList(Arrays.asList(clientEngagementDTO.getPreviousSelectedServices().split("\\s*,\\s*")));

            prevSelectedCodes.removeAll(selectedCodes);//Delete services
            serviceToDelete = new ArrayList<>();
            serviceToDelete.addAll(prevSelectedCodes);

            prevSelectedCodes = new ArrayList(Arrays.asList(clientEngagementDTO.getPreviousSelectedServices().split("\\s*,\\s*")));
            if (prevSelectedCodes.size() > 0) {
                selectedCodes.removeAll(prevSelectedCodes);//Service to Add
            }

            servicesToUpdate = new ArrayList<>();
            prevSelectedCodes.removeAll(serviceToDelete);
            servicesToUpdate.addAll(prevSelectedCodes);//Services toupdate

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : updateEngagement : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "updateEngagement() :: " + e.getMessage());
        }
        logger.info("End: ManageEngagementsServiceImpl : updateEngagementInfo");
        return manageEngagementsTransactDAOImpl.updateEngagement(clientEngagementDTO, sessionDTO,
                selectedCodes, serviceToDelete, servicesToUpdate);
    }

    @Override
    public int updateEngagementServices(ClientEngagementDTO clientEngagementDTO, UserProfileDTO sessionDTO,
            List<String> servicesToUpdate) throws AppException {
        return manageEngagementsDAO.updateEngagementServices(clientEngagementDTO, sessionDTO, servicesToUpdate);
    }

    @Override
    public int getEngagementCountForEngCode(ClientEngagementDTO engagementDTO) throws AppException {
        return manageEngagementsDAO.getEngagementCountForEngCode(engagementDTO);
    }

    @Override
    public int deleteEngagement(ClientEngagementDTO clientEngagementDTO, UserProfileDTO sessionDTO) throws AppException {
        return manageEngagementsDAO.deleteEngagement(clientEngagementDTO, sessionDTO);
    }

    /**
     * This method for view manage users to service
     *
     * @param clientEngagementCode
     * @param userDto
     * @return ClientEngagementDTO
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public ClientEngagementDTO viewManageServiceUser(final String clientEngagementCode, final UserProfileDTO userDto) throws AppException {
        ClientEngagementDTO clientEngagementDTO = new ClientEngagementDTO();
        clientEngagementDTO.setEngagementCode(clientEngagementCode);
        clientEngagementDTO.setCreatedByUserID(userDto.getUserProfileKey());
        if ((userDto.getUserTypeObj().getLookUpEntityName()).equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_INTERNAL)) {
            clientEngagementDTO.setClientEngagementKey(16);
        } else if ((userDto.getUserTypeObj().getLookUpEntityName()).equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_PARTNER)) {
            clientEngagementDTO.setClientEngagementKey(18);
        }
        clientEngagementDTO = manageEngagementsDAO.viewEngagementByEngmtKey(clientEngagementDTO);
//        clientEngagementDTO = manageEngagementsDAO.fetchAssignedUsersForSecurityService(clientEngagementDTO);

        EngagementPartnerUserDTO engagementPartnerUserDTO = new EngagementPartnerUserDTO();
        List<EngagementPartnerUserDTO> partnerList = new ArrayList<>();
        engagementPartnerUserDTO.setPartnerUserOrgName(userDto.getOrganizationObj().getOrganizationName());
        partnerList.add(engagementPartnerUserDTO);
        clientEngagementDTO.setEngagementPartnerUserLi(partnerList);

        return clientEngagementDTO;
    }

    /**
     * This method for save or update manage users to service
     *
     * @param manageServiceUserDTO
     * @param sessionUserDTO
     * @return int
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int saveOrUpdateManageUserToService(final ManageServiceUserDTO manageServiceUserDTO, final UserProfileDTO sessionUserDTO) throws AppException {
        manageServiceUserDTO.setCreatedByUserID(sessionUserDTO.getUserProfileKey());
        return manageEngagementsDAO.saveOrUpdateManageUserToService(manageServiceUserDTO, sessionUserDTO);
    }

    /**
     * This method for search or refresh assigned users to service
     *
     * @param manageServiceUserDTO
     * @param sessionUserDTO
     * @return ClientEngagementDTO
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public ClientEngagementDTO searchAssignedUsersForService(final ManageServiceUserDTO manageServiceUserDTO, final UserProfileDTO sessionUserDTO)
            throws AppException {
        ClientEngagementDTO clientEngagementDTO = new ClientEngagementDTO();
        clientEngagementDTO.setEngagementCode(manageServiceUserDTO.getClientEngagementCode());
        clientEngagementDTO.setCreatedByUserID(sessionUserDTO.getUserProfileKey());
        clientEngagementDTO = manageEngagementsDAO.viewEngagementByEngmtKey(clientEngagementDTO);
        if ((sessionUserDTO.getUserTypeObj().getLookUpEntityName()).equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_INTERNAL)) {
            clientEngagementDTO.setClientEngagementKey(16);
        } else if ((sessionUserDTO.getUserTypeObj().getLookUpEntityName()).equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_PARTNER)) {
            clientEngagementDTO.setClientEngagementKey(18);
        }
        manageServiceUserDTO.setCreatedByUserID(sessionUserDTO.getUserProfileKey());
        clientEngagementDTO = manageEngagementsDAO.searchAssignedUsersToService(manageServiceUserDTO, clientEngagementDTO);

        EngagementPartnerUserDTO engagementPartnerUserDTO = new EngagementPartnerUserDTO();
        List<EngagementPartnerUserDTO> partnerList = new ArrayList<>();
        engagementPartnerUserDTO.setPartnerUserOrgName(sessionUserDTO.getOrganizationObj().getOrganizationName());
        partnerList.add(engagementPartnerUserDTO);
        clientEngagementDTO.setEngagementPartnerUserLi(partnerList);
        return clientEngagementDTO;
    }

    /**
     *
     * @param engagementKey
     * @param partnerUserIds
     * @return
     * @throws AppException
     */
    @Override
    public int deleteEngagementPartnerUsers(String engagementKey, String partnerUserIds) throws AppException {
        return manageEngagementsDAO.deleteEngagementPartnerUsers(engagementKey, partnerUserIds);
    }

    @Override
    public ClientEngagementDTO prepareEngagementDataForRetaing(ClientEngagementDTO clientEngagementDTO) throws AppException {

        List<ClientEngagementSourceDTO> sourceList = clientEngagementDTO.getClientEngagementSourceLi();
        List<ClientEngagementSourceDTO> newSourceList = new ArrayList<>();
        for (ClientEngagementSourceDTO sourceDTO : sourceList) {
            if (sourceDTO.getClientEngSourceCode() != null) {
                newSourceList.add(sourceDTO);
            }
        }
        clientEngagementDTO.setClientEngagementSourceLi(newSourceList);

        List<SecurityServiceDTO> masterServicesList = fetchServicesByPackage(clientEngagementDTO.getSecurityPackageObj().getSecurityPackageCode());
        Map<String, String> servicceMap = new HashMap();
        for (SecurityServiceDTO serDto : masterServicesList) {
            servicceMap.put(serDto.getSecurityServiceCode(), serDto.getSecurityServiceName());
        }

        //List<EngagementPartnerUserDTO> usersList = clientEngagementDTO.getEngagementPartnerUserLi();
        List<SecurityServiceDTO> selServices = new ArrayList<>();
        List<String> serviceCodes = Arrays.asList(clientEngagementDTO.getSelectedServices().split("\\s*,\\s*"));
        List<SecurityServiceDTO> servicesList = clientEngagementDTO.getEngagementServiceLi();
        for (SecurityServiceDTO securityServiceDTO : servicesList) {
            if (serviceCodes.contains(securityServiceDTO.getSecurityServiceCode())) {
                securityServiceDTO.setSecurityServiceName(servicceMap.get(securityServiceDTO.getSecurityServiceCode()));
                selServices.add(securityServiceDTO);
            }
        }
        clientEngagementDTO.setEngagementServiceLi(selServices);

//        int i = 0;
//        List<EngagementPartnerUserDTO> partnerUsersList = new ArrayList<>();
//        for (EngagementPartnerUserDTO partUser : usersList) {
//            if (i == 0) {
//                clientEngagementDTO.setEngagementLeadClientUserID(partUser.getPartnerUserID());
//            }
//            if (i == 1) {
//                clientEngagementDTO.setEngagementLeadInternalUserID(partUser.getPartnerUserID());
//            }
//            if (i > 1 && partUser.getPartnerUserID() > 0) {
//                EngagementPartnerUserDTO engPartDTO = new EngagementPartnerUserDTO();
//                engPartDTO.setPartnerUserID(partUser.getPartnerUserID());
//                engPartDTO.setPartnerUserOrgID(partUser.getPartnerUserOrgID());
//                engPartDTO.setSecurityServicesSelected(partUser.getSecurityServicesSelected());
//
//                partnerUsersList.add(engPartDTO);
//            }
//            i++;
//        }
//        clientEngagementDTO.setEngagementPartnerUserLi(partnerUsersList);
        return clientEngagementDTO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int activateEngagement(String clientEngCode) throws AppException {
        return manageEngagementsDAO.activateEngagement(clientEngCode);
    }

    /**
     * This method for delete assigned analyst user to service in Manage Users
     * to Service page
     *
     * @param manageServiceUserDTO
     * @return int
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int deleteAssignedUserToService(final ManageServiceUserDTO manageServiceUserDTO) throws AppException {
//        boolean retFlag = true;
//        int retVal = 0;
//        //Start: Check for engagement uploaded files
//        ClientEngagementDTO engagementDTO = new ClientEngagementDTO();
//        engagementDTO.setEngagementCode(manageServiceUserDTO.getClientEngagementCode());
//        engagementDTO.setSecurityServiceCode(manageServiceUserDTO.getSecureServiceCode());
//        engagementDTO.setCreatedByUserID(manageServiceUserDTO.getUserKey());
//        engagementDTO.setUserTypeFlag("PL");
//        List<ClientEngagementDTO> engUploadFileList = engagementDataUploadDAO.fetchUploadedFilesDetails(engagementDTO);
//        //End: Check for engagement uploaded files
//        
//        //Start: Check for findings
//        VulnerabilityDTO vulnDTO = new VulnerabilityDTO();
//        vulnDTO.setClientEngagementDTO(engagementDTO);
//        vulnDTO.setPageNo(1);
//        vulnDTO.setRowCount(100);
//        SecurityServiceDTO serviceDTO = new SecurityServiceDTO();
//        serviceDTO.setSecurityServiceCode(manageServiceUserDTO.getSecureServiceCode());
//        vulnDTO.setSecurityServiceObj(serviceDTO);
//        List<VulnerabilityDTO> findingList = analystValidationDAO.vulnerabilityList(vulnDTO);
//        //End: Check for findings
//        
//        if(engUploadFileList.size() > 0) {
//            retFlag = false;
//            retVal = -1;
//        } else if(findingList.size() > 0) {
//            retFlag = false;
//            retVal = -1;
//        }

//        if(retFlag) {
//        }
        return manageEngagementsDAO.deleteAssignedUserToService(manageServiceUserDTO);
    }

    /**
     * This method to fetch mapped analyst users based on engagement code and
     * user type
     *
     * @param userType
     * @param engkey
     * @return List<String>
     * @throws AppException
     */
    @Override
    public List<String> fetchMappedAnalystUsersByEngkeyAndUserTypeID(String userType, final String engkey) throws AppException {
        return manageEngagementsDAO.fetchMappedAnalystUsersByEngkeyAndUserTypeID(userType, engkey);
    }

    /**
     * This method to fetch mapped analyst users based on service
     *
     * @param engKey
     * @param userType
     * @param serviceCode
     * @param flag
     * @return List<String>
     * @throws AppException
     */
    @Override
    public List<String> fetchMappedAnalystUsersByService(final String engKey, final String userType, final String serviceCode, final String flag)
            throws AppException {
        return manageEngagementsDAO.fetchMappedAnalystUsersByService(engKey, userType, serviceCode, flag);
    }

    /**
     * This method to remove mapped users from total user list
     *
     * @param mappedUsers
     * @param totalUsers
     * @param serviceCode
     * @return List<UserProfileDTO>
     * @throws AppException
     */
    public List<UserProfileDTO> filterMappedUsersFromTotalList(List<String> mappedUsers, List<UserProfileDTO> totalUsers, String serviceCode)
            throws AppException {
        logger.info("Start: ManageEngagementsServiceImpl : filterMappedUsersFromTotalList");
        List<UserProfileDTO> usersAfterFilter = new ArrayList<>();
        try {
            for (UserProfileDTO user : totalUsers) {
                if (!mappedUsers.contains(user.getFirstName())) {
                    UserProfileDTO userDto = new UserProfileDTO();
                    userDto.setUserProfileKey(user.getUserProfileKey());
                    userDto.setFirstName(user.getFirstName());
                    userDto.setRowStatusValue(serviceCode); //set service code as row status value for checking purpose in jsp
                    usersAfterFilter.add(userDto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : filterMappedUsersFromTotalList:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "filterMappedUsersFromTotalList() : " + e.getMessage());
        }
        logger.info("Start: ManageEngagementsServiceImpl:  filterMappedUsersFromTotalList");
        return usersAfterFilter;
    }

    @Override
    public List<UserProfileDTO> fetchUsersByRole(UserProfileDTO userObj) throws AppException {
        return manageEngagementsDAO.fetchUsersByRole(userObj);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int saveServiceUser(UserProfileDTO serviceUserObj, UserProfileDTO sessionUserDTO, String engagementCode) throws AppException {
        int retval = manageEngagementsDAO.saveServiceUser(serviceUserObj, sessionUserDTO, engagementCode);

        //Fetch engagement details by engagementcode used to send emailcontent
        ClientEngagementDTO engagementObj = viewEngagementByEngmtKey(engagementCode);

        if (retval > 0) {
            if (ApplicationConstants.USER_TYPE_INTERNAL_KEY == serviceUserObj.getUserTypeKey()) {
                //Fetch user details of Internal user
                UserProfileDTO internalUserDTO = userManagementServiceImpl.fetchUserInfoByUserID(serviceUserObj.getUserProfileKey());
                //Mail sent to internl user
                clientEngagementMailHelper.mailOnEngagementSave(internalUserDTO, engagementObj, sessionUserDTO);
            } else if (ApplicationConstants.USER_TYPE_PARTNER_KEY == serviceUserObj.getUserTypeKey()) {
                if (ApplicationConstants.PARTNER_LEAD_KEY == serviceUserObj.getAppRoleKey()) {
                    //Fetch user details of Partner user
                    UserProfileDTO partnerUserDTO = userManagementServiceImpl.fetchUserInfoByUserID(serviceUserObj.getUserProfileKey());
                    //Mail sent to Partner user
                    clientEngagementMailHelper.mailToPartnerLead(partnerUserDTO, engagementObj, sessionUserDTO);
                } else if (ApplicationConstants.PARTNER_ANALYST_KEY == serviceUserObj.getAppRoleKey()) {
                    //Fetch user details of assigned user
                    UserProfileDTO assignedAnalystUserDto = userManagementServiceImpl.fetchUserInfoByUserID(serviceUserObj.getUserProfileKey());
                    //Mail sent to assigned user
                    clientEngagementMailHelper.mailToAssignedUser(assignedAnalystUserDto, engagementObj, sessionUserDTO);
                }

            }
        }
        return retval;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<ManageServiceUserDTO> fetchServiceUsers(final String clientEngagementCode) throws AppException {
        ClientEngagementDTO clientEngagementDTO = new ClientEngagementDTO();
        clientEngagementDTO.setEngagementCode(clientEngagementCode);
        List<ManageServiceUserDTO> serviceUsrsList = manageEngagementsDAO.fetchServiceUsers(clientEngagementDTO);
        return serviceUsrsList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int deleteServiceUsers(String assignKey) throws AppException {
        return manageEngagementsDAO.deleteServiceUsers(assignKey);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<RoleDTO> fetchEngRolesList(final long userTypeKey, String pkgCode) throws AppException {
        List<RoleDTO> rolesList = userManagementDAO.fetchRolesList(userTypeKey);
        List<RoleDTO> filteredList = new ArrayList<>();
        for (RoleDTO roleDto : rolesList) {
            if (ApplicationConstants.ROLE_WEB_APP_ADMIN.equalsIgnoreCase(roleDto.getAppRoleName())
                    || ApplicationConstants.ROLE_ENGAGEMENT_COORDINATOR.equalsIgnoreCase(roleDto.getAppRoleName())
                    || ApplicationConstants.ROLE_OPTUM_ENGAGEMENT_ANALYST.equalsIgnoreCase(roleDto.getAppRoleName())) {

            } else {
                if ((pkgCode.equalsIgnoreCase(ReportsAndDashboardConstants.PACKAGE_TYPE_AS_HC) || pkgCode.equalsIgnoreCase(ReportsAndDashboardConstants.PACKAGE_TYPE_AS_TR)) && userTypeKey == ApplicationConstants.USER_TYPE_CLIENT_KEY) {
                    if (roleDto.getAppRoleName().equalsIgnoreCase(ApplicationConstants.ROLE_REMEDIATION_OWNER) || roleDto.getAppRoleName().equalsIgnoreCase(ApplicationConstants.ROLE_RISKREGISTRY_OWNER)) {

                    } else {
                        filteredList.add(roleDto);
                    }

                } else {
                    filteredList.add(roleDto);
                }
            }
        }
        return filteredList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<RoleDTO> fetchServceRolesList(final long userTypeKey) throws AppException {
        List<RoleDTO> rolesList = userManagementDAO.fetchRolesList(userTypeKey);
        List<RoleDTO> filteredList = new ArrayList<>();
        for (RoleDTO roleDto : rolesList) {
            if (ApplicationConstants.ROLE_WEB_APP_ADMIN.equalsIgnoreCase(roleDto.getAppRoleName())
                    || ApplicationConstants.ROLE_ENGAGEMENT_COORDINATOR.equalsIgnoreCase(roleDto.getAppRoleName())
                    || ApplicationConstants.ROLE_OPTUM_ENGAGEMENT_LEAD.equalsIgnoreCase(roleDto.getAppRoleName())
                    || ApplicationConstants.ROLE_REMEDIATION_COORDINATOR.equalsIgnoreCase(roleDto.getAppRoleName())
                    || ApplicationConstants.ROLE_REMEDIATION_ANALYST.equalsIgnoreCase(roleDto.getAppRoleName())) {

            } else {
                filteredList.add(roleDto);
            }
        }
        return filteredList;
    }

}
