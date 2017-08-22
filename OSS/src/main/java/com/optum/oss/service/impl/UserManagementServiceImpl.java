/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service.impl;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dao.impl.UserManagementDAOImpl;
import com.optum.oss.dto.OrganizationDTO;
import com.optum.oss.dto.PermissionGroupDTO;
import com.optum.oss.dto.RoleDTO;
import com.optum.oss.dto.UserApplicationRoleDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.LDAPAuthenticate;
import com.optum.oss.helper.WebAppAdminMailHelper;
import com.optum.oss.service.UserManagementService;
import com.optum.oss.transact.dao.impl.UsermanagementTransactDAOImpl;
import com.optum.oss.util.GenerateRandomNumber;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
public class UserManagementServiceImpl implements UserManagementService {

    private static final Logger logger = Logger.getLogger(UserManagementServiceImpl.class);
    /*
     Start: Autowiring the required Class instances
     */
    @Autowired
    private UserManagementDAOImpl userManagementDAO;
    @Autowired
    private UsermanagementTransactDAOImpl userManagementTransatDAO;
    @Autowired
    private WebAppAdminMailHelper adminMailHelper;
    @Autowired
    private GenerateRandomNumber generateRandom;
    @Autowired
    private LDAPAuthenticate ldapAuthenticate;
    /*
     End: Autowiring the required Class instances
     */

    /**
     *
     * @param organizationKey
     * @return List<UserProfileDTO>
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<UserProfileDTO> fetchUserList(final long organizationKey) throws AppException {
        return userManagementDAO.fetchUserList(organizationKey);
    }

    /**
     *
     * @param userProfileDto
     * @param sessionDto
     * @return UserProfileDTO
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int saveUserProfile(UserProfileDTO userProfileDto, UserProfileDTO sessionDto) throws AppException {
        return userManagementDAO.saveUserProfile(userProfileDto, sessionDto);
    }

    /**
     *
     * @param userProfileKey
     * @return UserProfileDTO
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public UserProfileDTO viewUserProfile(long userProfileKey) throws AppException {
        return userManagementDAO.viewUserProfile(userProfileKey);
    }

    /**
     *
     * @param userProfileKey
     * @return List<OrganizationDTO>
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<OrganizationDTO> fetchOrgListByUserType(long userProfileKey) throws AppException {
         List<OrganizationDTO> orgListByUserType = userManagementDAO.fetchOrgListByUserType(userProfileKey);
         Collections.sort(orgListByUserType, new Comparator<OrganizationDTO>(){

             @Override
             public int compare(OrganizationDTO orgDto1, OrganizationDTO orgDto2) {
                String orgName1 = orgDto1.getOrganizationName().toUpperCase();
                String orgName2 = orgDto2.getOrganizationName().toUpperCase();
                return orgName1.compareTo(orgName2);
             }
         });
        return orgListByUserType;
    }

    /**
     *
     * @return List<OrganizationDTO>
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<RoleDTO> fetchRolesList(final long userTypeKey) throws AppException {
        return userManagementDAO.fetchRolesList(userTypeKey);
    }

    /**
     *
     * @param userProfileKey
     * @return List<RoleDTO>
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public Map<Integer, String> fetchRolesByUser(long userProfileKey) throws AppException {
        return userManagementDAO.fetchRolesByUser(userProfileKey);
    }

    /**
     *
     * @param userProfileDto
     * @param sessionUserDTO
     * @param selectedRoles
     * @return UserProfileDTO
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int saveUserDetails(UserProfileDTO userProfileDto,
            UserProfileDTO sessionUserDTO, String[] selectedRoles) throws AppException {
        int retVal = 0;
        try {
            List<UserApplicationRoleDTO> appRoleDto = new ArrayList<>();
            for (String roleKey : selectedRoles) {
                UserApplicationRoleDTO userRole = new UserApplicationRoleDTO();
                userRole.setUserApplicationRoleKey(Long.parseLong(roleKey));
                appRoleDto.add(userRole);
            }
            userProfileDto.setUserApplicationRoleListObj(appRoleDto);
            retVal = userManagementTransatDAO.saveUserDetails(userProfileDto, sessionUserDTO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : updateUserDetails:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "updateUserDetails() :: " + e.getMessage());
        }
        return retVal;
    }

    /**
     *
     * @param userProfileDto
     * @return Inserted id as Integer
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int saveUserApplicationRole(UserProfileDTO userProfileDto, UserProfileDTO sessionDto) throws AppException {
        return userManagementDAO.saveUserApplicationRole(userProfileDto, sessionDto);
    }

    /**
     *
     * @param userProfileKey
     * @param sessionDto
     * @return Deleted row count as Integer
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int deleteUserApplicationRoles(long userProfileKey, UserProfileDTO sessionDto) throws AppException {
        return userManagementDAO.deleteUserApplicationRoles(userProfileKey, sessionDto);
    }

    /**
     *
     * @param userProfileDto
     * @param sessionDto
     * @return Updated row count as Integer
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int updateUserProfile(final UserProfileDTO userProfileDto, final UserProfileDTO sessionDto) throws AppException {
        return userManagementDAO.updateUserProfile(userProfileDto, sessionDto);
    }

    /**
     *
     * @param userProfileDto
     * @param sessionDto
     * @param selectedRoles
     * @return updated row count as Integer
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int updateUserDetails(UserProfileDTO userProfileDto, UserProfileDTO sessionDto, String[] selectedRoles) throws AppException {
        List<UserApplicationRoleDTO> appRoleDto = new ArrayList<>();
        
        UserProfileDTO userExistingDetails = new UserProfileDTO();
        userExistingDetails = viewUserProfile(userProfileDto.getUserProfileKey());
            
        for (String roleKey : selectedRoles) {
            UserApplicationRoleDTO userRole = new UserApplicationRoleDTO();
            userRole.setUserApplicationRoleKey(Long.parseLong(roleKey));
            appRoleDto.add(userRole);
        }
        userProfileDto.setUserApplicationRoleListObj(appRoleDto);
        int retVal = userManagementTransatDAO.updateUserDetails(userProfileDto, sessionDto);
        if (retVal > 0) {
            if (userProfileDto.getRowStatusKey() == ApplicationConstants.DB_ROW_STATUS_DE_ACTIVE_VALUE && userExistingDetails.getRowStatusKey() != ApplicationConstants.DB_ROW_STATUS_DE_ACTIVE_VALUE) {
                adminMailHelper.mail_UserAccountDeactivated(userProfileDto, sessionDto.getUserProfileKey());
            } else if (userProfileDto.getRowStatusKey() == ApplicationConstants.DB_ROW_STATUS_ACTIVE_VALUE && userExistingDetails.getRowStatusKey() != ApplicationConstants.DB_ROW_STATUS_ACTIVE_VALUE) {
                String newPass = generateRandom.generateResPasswordToken();
                ldapAuthenticate.changeLDAPPassword(userProfileDto, newPass);
                adminMailHelper.mail_UserAccountReactivated(userProfileDto, sessionDto.getUserProfileKey(), newPass);
                adminMailHelper.mail_UserAccountPassword(userProfileDto, newPass, sessionDto.getUserProfileKey());
            }
        }
        return retVal;
    }

    /**
     *
     * @param userProfileKey
     * @return Map<Integer, PermissionGroupDTO>
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public Map<Integer, PermissionGroupDTO> fetchPermissionGroupsByUserID(long userProfileKey) throws AppException {
        return userManagementDAO.fetchPermissionGroupsByUserID(userProfileKey);
    }

    /**
     *
     * @param appRoleKeys
     * @return List<PermissionGroupDTO>
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<PermissionGroupDTO> fetchPermissionGroupByRole(String appRoleKeys) throws AppException {
        return userManagementDAO.fetchPermissionGroupByRole(appRoleKeys);
    }

    /**
     * Fetch user details based on user profile key
     * 
     * @param userID
     * @return
     * @throws AppException
     */
    @Override
    public UserProfileDTO fetchUserInfoByUserID(long userID) throws AppException {
        return userManagementDAO.fetchUserInfoByUserID(userID);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public Map<String, List<PermissionGroupDTO>> fetchPermissionMapGroupByRole(String appRoleKeys) throws AppException {
        return userManagementDAO.fetchPermissionMapGroupByRole(appRoleKeys);
    }

    

}
