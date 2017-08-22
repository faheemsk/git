/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.transact.dao;

import com.optum.oss.dto.RoleDTO;
import com.optum.oss.exception.AppException;

/**
 *
 * @author akeshavulu
 */
public interface ManageRolesAndPermissionsTransactDAO {
    
    public int saveAppRolePermissionGrpDetails(RoleDTO roleDTO) throws AppException;
    
    public int updateAppRolePermissionGrpDetails(RoleDTO roleDTO) throws AppException;
    
}
