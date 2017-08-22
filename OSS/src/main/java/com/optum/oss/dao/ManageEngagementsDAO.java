/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao;

import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.dto.ManageServiceUserDTO;
import com.optum.oss.dto.SecurityPackageDTO;
import com.optum.oss.dto.SecurityServiceDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import java.util.List;

/**
 *
 * @author sbhagavatula
 */
public interface ManageEngagementsDAO {

    public int saveEngagementInfo(final ClientEngagementDTO clientEngagementDTO, final UserProfileDTO sessionDTO) throws AppException;

    public int updateEngagementInfo(final ClientEngagementDTO clientEngagementDTO, final UserProfileDTO sessionDTO) throws AppException;

    public int saveEngagementServices(ClientEngagementDTO clientEngagementDTO, UserProfileDTO sessionDTO, List<String> services) throws AppException;

    public int deleteEngagementServices(final String engagementKey, List<String> servicesToDelete, String flag) throws AppException;

    public int saveEngagementUsers(UserProfileDTO engagementUsrObj, UserProfileDTO sessionDTO, String engCode) throws AppException;

    public int deleteEngagementUsers(final String assignKey) throws AppException;

    public int saveEngagementSourceInfo(ClientEngagementDTO clientEngagementDTO, UserProfileDTO sessionDTO) throws AppException;

    public int deleteEngagementSourceInfo(final String engagementKey, UserProfileDTO sessionDTO) throws AppException;

    public List<SecurityPackageDTO> fetchSecurityPackages() throws AppException;

    public List<UserProfileDTO> fetchUsersByOrgIDAndUserTypeID(final long orgId, final String userType, final String flag, List<String> mappedUsers)
            throws AppException;

    public List<SecurityServiceDTO> fetchServicesByPackage(final String packageID) throws AppException;

    public List<ClientEngagementDTO> fetchEngagementsWorklist(final long organizationKey, final long userProfileKey, final String fetchFlag) throws AppException;

    public ClientEngagementDTO viewEngagementByEngmtKey(final ClientEngagementDTO engagementDTO) throws AppException;

    public ClientEngagementDTO fetchSecurityServicesByEngmtId(final ClientEngagementDTO engagementDTO) throws AppException;

    public List<UserProfileDTO> getClientEngagementAssignedUserByEngmtKey(final String clientEngagementCode) throws AppException;

    public ClientEngagementDTO getClientEngagementSourceDetailsByEngmtKey(final ClientEngagementDTO engagementDTO) throws AppException;

    public int getEngagementCountForEngCode(final ClientEngagementDTO engagementDTO) throws AppException;

    public int deleteEngagement(ClientEngagementDTO clientEngagementDTO, UserProfileDTO sessionDTO) throws AppException;

    public List<ManageServiceUserDTO> fetchServiceUsers(final ClientEngagementDTO engagementDTO) throws AppException;

    public int saveOrUpdateManageUserToService(final ManageServiceUserDTO manageServiceUserDTO, final UserProfileDTO sessionUserDTO) throws AppException;

    public ClientEngagementDTO searchAssignedUsersToService(final ManageServiceUserDTO manageServiceUserDTO, final ClientEngagementDTO clientEngagementDTO)
            throws AppException;

    public int updateEngagementServices(ClientEngagementDTO clientEngagementDTO, UserProfileDTO sessionDTO,
            List<String> servicesToUpdate) throws AppException;

    public int deleteEngagementPartnerUsers(final String engagementKey, final String partnerUserIds) throws AppException;
    
    public int activateEngagement(String clientEngCode) throws AppException;
    
    public int deleteAssignedUserToService(final ManageServiceUserDTO manageServiceUserDTO) throws AppException;
    
    public List<String> fetchMappedAnalystUsersByEngkeyAndUserTypeID(String userType, final String engkey) throws AppException;
    
    public List<String> fetchMappedAnalystUsersByService(final String engKey, final String userType, final String serviceCode, final String flag)
            throws AppException;
    
    public List<UserProfileDTO> fetchUsersByRole(final UserProfileDTO userObj)throws AppException;
    
    public int saveServiceUser(final UserProfileDTO serviceUserObj, final UserProfileDTO sessionUserDTO, String engagementCode) throws AppException;
    
    public int deleteServiceUsers(String assignKey) throws AppException;
}
