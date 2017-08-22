/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service;

import com.optum.oss.dto.PermissionGroupDTO;
import com.optum.oss.dto.RoleDTO;
import com.optum.oss.exception.AppException;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author akeshavulu
 */
@Service
public interface ManageRolesAndPermissionsService {

    public List<RoleDTO> getManageRolesAndPermissionsWorkList(RoleDTO manageRolesAndPermissionsDTO) throws AppException;

    public List<PermissionGroupDTO> getRolesPermissionGroup() throws AppException;

    public int saveRoleDetails(RoleDTO roleDTO) throws AppException;
    
    public int editRoleDetails(RoleDTO roleDTO)throws AppException;

    public int deleteRoleDetails(RoleDTO roleDTO) throws AppException;
    
    public List<PermissionGroupDTO> fetchRolePermissionsGrpDetails(RoleDTO manageRolesAndPermissionsDTO)throws AppException;
    
    public RoleDTO fetchAppRoleDetails(final String encRoleKey)throws AppException;
    
    public int saveAppRoleDetails(RoleDTO roleDTO) throws AppException;
    
    public int savePermissionGrpDetails(RoleDTO roleDTO, int roleKey)throws AppException;
    
    public int saveAppRolePermissionGrpDetails(RoleDTO roleDTO) throws AppException;
    
    public int updateAppRoleDetails(RoleDTO roleDTO) throws AppException;
    
    public int updatePermissionGrpDetails(RoleDTO roleDTO)throws AppException;
    
    public int updateAppRolePermissionGrpDetails(RoleDTO roleDTO) throws AppException;
    
    public int fetchAppRoleByName(String groupName) throws AppException;

}
