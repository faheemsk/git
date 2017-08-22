/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao;

import com.optum.oss.dto.PermissionDTO;
import com.optum.oss.dto.PermissionGroupDTO;
import com.optum.oss.exception.AppException;
import java.util.List;

/**
 *
 * @author sathuluri
 */
public interface ManagePermissionGroupDAO {

    public List<PermissionGroupDTO> fetchPermissionGroupWorkList() throws AppException;

    public int savePermissionGroup(final PermissionGroupDTO permissionGroupDTO) throws AppException;

    public int updatePermissionGroup(final PermissionGroupDTO permissionGroupDTO) throws AppException;

    public int savePermissionsForPermissionGroup(final PermissionGroupDTO permissionGroupDTO) throws AppException;

    public List<PermissionDTO> fetchPermissionsForPermissionGroup(final long permissionGroupKey) throws AppException;

    public int deletePermissionsForPermissionGroup(final List<PermissionDTO> permissionsList, final long retVal, final long userID) throws AppException;

    public int deletePermissionGroup(final PermissionGroupDTO perGroupDTO) throws AppException;

    public PermissionGroupDTO fetchPermissionGroupById(long groupKeyId) throws AppException;

    public int fetchPermissionGroupById(String groupName) throws AppException;
}
