/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao;

import com.optum.oss.dto.PermissionDTO;
import com.optum.oss.exception.AppException;
import java.util.List;

/**
 *
 * @author bpilla
 */
public interface DetailedPermissionsDAO {

    public List<PermissionDTO> fetchViewDetailedPermissions(final long groupID) throws AppException;

    public int saveDefinition(PermissionDTO permissionDTO) throws AppException;
}
