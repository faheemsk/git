/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service;

import com.optum.oss.dto.PermissionDTO;
import com.optum.oss.dto.PermissionGroupDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import java.util.List;

/**
 *
 * @author sathuluri
 */
public interface ManagePermissionGroupService {

    public List<PermissionGroupDTO> fetchPermissionGroupWorkList() throws AppException;

    public int savePermissionGroupDetails(final PermissionGroupDTO permissionGroupDTO) throws AppException;

    public int saveOrUpdatePermissionGroup(final PermissionGroupDTO permissionGroupDTO, final List<PermissionDTO> oldPermissionsList,
            final UserProfileDTO userDto, final String[] permissionData) throws AppException;

    public int updatePermissionGroupDetails(final PermissionGroupDTO permissionGroupDTO) throws AppException;

    public int savePermissionsForPermissionGroup(final PermissionGroupDTO permissionGroupDTO) throws AppException;

    public List<PermissionDTO> fetchPermissionsForPermissionGroup(final PermissionGroupDTO permissionGroupDTO) throws AppException;

    public int deletePermissionsForPermissionGroup(final List<PermissionDTO> permissionsList, final int retVal, final long userID) throws AppException;

    public int deletePermissionGroup(final PermissionGroupDTO perGroupDTO) throws AppException;

    public int deletePermissionGroupDetails(final PermissionGroupDTO perGroupDTO) throws AppException;

    public PermissionGroupDTO filterDetailedPermissionsForPermissionGroup(PermissionGroupDTO permissionGroupDTO, List<PermissionDTO> permissionList) throws AppException;

    public PermissionGroupDTO fetchPermissionGroupById(long groupKeyId) throws AppException;

    public int fetchPermissionGroupByName(String groupName) throws AppException;
}
