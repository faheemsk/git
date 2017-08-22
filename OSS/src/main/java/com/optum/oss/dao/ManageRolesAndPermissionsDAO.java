/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.dao;

import com.optum.oss.dto.PermissionGroupDTO;
import com.optum.oss.dto.RoleDTO;
import com.optum.oss.exception.AppException;
import java.util.List;

/**
 *
 * @author akeshavulu
 */
public interface ManageRolesAndPermissionsDAO {
    
    public List<RoleDTO> getManageRolesAndPermissionsWorkList(RoleDTO manageRolesAndPermissionsDTO)throws AppException;
    
    public List<PermissionGroupDTO> fetchRolePermissionsGrpDetails(RoleDTO manageRolesAndPermissionsDTO)throws AppException;
    
    public RoleDTO fetchAppRoleDetails(final long roleKey)throws AppException;
    
    public List<PermissionGroupDTO> getRolesPermissionGroup()throws AppException;
    
    public int saveRoleDetails(RoleDTO roleDTO)throws AppException;
    
    public int editRoleDetails(RoleDTO roleDTO)throws AppException;
    
    public int addRolePermissionGroup(PermissionGroupDTO permissionGroupDTO)throws AppException;
    
    public int deleteRoleDetails(RoleDTO roleDTO)throws AppException;
    
    public int saveAppRoleDetails(RoleDTO roleDTO) throws AppException;
    
    public int savePermissionGrpDetails(RoleDTO roleDTO, int roleKey)throws AppException;
    
    public int updateAppRoleDetails(RoleDTO roleDTO) throws AppException;
    
    public int updatePermissionGrpDetails(RoleDTO roleDTO)throws AppException;
    
    public int fetchAppRoleByName(String groupName) throws AppException;
    
    
}
