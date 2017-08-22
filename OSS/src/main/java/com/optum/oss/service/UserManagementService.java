/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.service;

import com.optum.oss.dto.OrganizationDTO;
import com.optum.oss.dto.PermissionGroupDTO;
import com.optum.oss.dto.RoleDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 *
 * @author sbhagavatula
 */
@Service
public interface UserManagementService {
    
    public List<UserProfileDTO> fetchUserList(final long organizationKey) throws AppException;
    
    public int saveUserProfile(UserProfileDTO userProfileDto, UserProfileDTO sessionDto) throws AppException;
    
    public UserProfileDTO viewUserProfile(final long userProfileKey) throws AppException;
    
    public List<OrganizationDTO> fetchOrgListByUserType(final long userProfileKey) throws AppException;
    
    public List<RoleDTO> fetchRolesList(final long userTypeKey) throws AppException;
    
    public Map<Integer, String> fetchRolesByUser(final long userProfileKey) throws AppException;
    
    public int saveUserDetails(UserProfileDTO userProfileDto, UserProfileDTO sessionUserDTO, String[] selectedRoles) throws AppException;
    
    public int saveUserApplicationRole(final UserProfileDTO userProfileDto, final UserProfileDTO sessionDto) throws AppException;
    
    public int deleteUserApplicationRoles(final long userProfileKey, final UserProfileDTO sessionDto) throws AppException;
    
    public int updateUserProfile(final UserProfileDTO userProfileDto, final UserProfileDTO sessionDto) throws AppException;
    
    public int updateUserDetails(final UserProfileDTO userProfileDto, final UserProfileDTO sessionDto, String[] selectedRoles) throws AppException;
    
    public Map<Integer, PermissionGroupDTO> fetchPermissionGroupsByUserID(final long userProfileKey) throws AppException;
    
    public List<PermissionGroupDTO> fetchPermissionGroupByRole(final String appRoleKey) throws AppException;
    
    public UserProfileDTO fetchUserInfoByUserID(final long userID) throws AppException;
    
    public Map<String, List<PermissionGroupDTO>> fetchPermissionMapGroupByRole(final String appRoleKeys) throws AppException;
    
}
