/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author hpasupuleti
 */
//@Component
public class RoleDTO extends BaseDTO implements Serializable{
    
    private long appRoleKey;
    
    @NotBlank(message = "Please enter Role Name")
    @Size(min=0, max=100,message = "Role Name cannot exceed 100 characters") 
    private String appRoleName;
    
    @NotBlank(message = "Please enter Role Definition")
    @Size(min=0, max=1000,message = "Definition cannot exceed 1000 characters") 
    private String appRoleDescription;
    
    @NotBlank(message = "Please enter Reason for Deactivation")
    @Size(min=0, max=1000,message = "Reason for Deactivation cannot exceed 1000 characters") 
    private String appRoleComments;
    
    
    private List<PermissionGroupDTO> permissionGroupListObj = new ArrayList<>();
    
    @NotNull(message = "Please select Permission Group")
    @Size(min = 1,message = "Please select Permission Group")
    private Long[] permissionGroupIDs;
    
    private List<RoleDTO> manageRolesPermissionListObj = new ArrayList<>();
    
    private String prnGrpIDs;
    
    private String encAppRoleKey;
    /**
     * @return the appRoleKey
     */
    public long getAppRoleKey() {
        return appRoleKey;
    }

    /**
     * @param appRoleKey the appRoleKey to set
     */
    public void setAppRoleKey(long appRoleKey) {
        this.appRoleKey = appRoleKey;
    }

    /**
     * @return the appRoleName
     */
    public String getAppRoleName() {
        return appRoleName;
    }

    /**
     * @param appRoleName the appRoleName to set
     */
    public void setAppRoleName(String appRoleName) {
        this.appRoleName = appRoleName;
    }

    /**
     * @return the appRoleDescription
     */
    public String getAppRoleDescription() {
        return appRoleDescription;
    }

    /**
     * @param appRoleDescription the appRoleDescription to set
     */
    public void setAppRoleDescription(String appRoleDescription) {
        this.appRoleDescription = appRoleDescription;
    }

    /**
     * @return the permissionGroupListObj
     */
    public List<PermissionGroupDTO> getPermissionGroupListObj() {
        return permissionGroupListObj;
    }

    /**
     * @param permissionGroupListObj the permissionGroupListObj to set
     */
    public void setPermissionGroupListObj(List<PermissionGroupDTO> permissionGroupListObj) {
        this.permissionGroupListObj = permissionGroupListObj;
    }

    public List<RoleDTO> getManageRolesPermissionListObj() {
        return manageRolesPermissionListObj;
    }

    public void setManageRolesPermissionListObj(List<RoleDTO> manageRolesPermissionListObj) {
        this.manageRolesPermissionListObj = manageRolesPermissionListObj;
    }

    public String getPrnGrpIDs() {
        return prnGrpIDs;
    }

    public void setPrnGrpIDs(String prnGrpIDs) {
        this.prnGrpIDs = prnGrpIDs;
    }

    /**
     * @return the appRoleComments
     */
    public String getAppRoleComments() {
        return appRoleComments;
    }

     /**
     * @param appRoleComments to set
     */
    public void setAppRoleComments(String appRoleComments) {
        this.appRoleComments = appRoleComments;
    }

    /**
     * @return the encAppRoleKey
     */
    public String getEncAppRoleKey() {
        return encAppRoleKey;
    }
     /**
     * @param encAppRoleKey the encAppRoleKey to set
     */
    public void setEncAppRoleKey(String encAppRoleKey) {
        this.encAppRoleKey = encAppRoleKey;
    }

    /**
     * @return the permissionGroupIDs
     */
    public Long[] getPermissionGroupIDs() {
        return permissionGroupIDs;
    }

    /**
     * @param permissionGroupIDs the permissionGroupIDs to set
     */
    public void setPermissionGroupIDs(Long[] permissionGroupIDs) {
        this.permissionGroupIDs = permissionGroupIDs;
    }
    
    
   
}
