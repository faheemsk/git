/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service;

import com.optum.oss.dto.PermissionDTO;
import com.optum.oss.exception.AppException;
import java.util.List;

/**
 *
 * @author bpilla
 */
public interface DetailedPermissionsService {

    public List<PermissionDTO> fetchViewDetailedPermissions(final long groupID)
            throws AppException;

    public int saveDefinition(final String permissionDesc, final String encPermissionKey) 
            throws AppException;

}
