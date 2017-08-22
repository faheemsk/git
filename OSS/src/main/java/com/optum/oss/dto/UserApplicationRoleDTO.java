/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hpasupuleti
 */
//@Component
public class UserApplicationRoleDTO extends BaseDTO implements Serializable{
    
    private long userApplicationRoleKey;
    private long userProfileKey;
    private long appRoleKey;
    private RoleDTO appRoleObj = new RoleDTO();
    private List<PermissionDTO> permissionListObj = new ArrayList<>();

    /**
     * @return the userApplicationRoleKey
     */
    public long getUserApplicationRoleKey() {
        return userApplicationRoleKey;
    }

    /**
     * @param userApplicationRoleKey the userApplicationRoleKey to set
     */
    public void setUserApplicationRoleKey(long userApplicationRoleKey) {
        this.userApplicationRoleKey = userApplicationRoleKey;
    }

    /**
     * @return the userProfileKey
     */
    public long getUserProfileKey() {
        return userProfileKey;
    }

    /**
     * @param userProfileKey the userProfileKey to set
     */
    public void setUserProfileKey(long userProfileKey) {
        this.userProfileKey = userProfileKey;
    }

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
     * @return the appRoleObj
     */
    public RoleDTO getAppRoleObj() {
        return appRoleObj;
    }

    /**
     * @param appRoleObj the appRoleObj to set
     */
    public void setAppRoleObj(RoleDTO appRoleObj) {
        this.appRoleObj = appRoleObj;
    }

    /**
     * @return the permissionListObj
     */
    public List<PermissionDTO> getPermissionListObj() {
        return permissionListObj;
    }

    /**
     * @param permissionListObj the permissionListObj to set
     */
    public void setPermissionListObj(List<PermissionDTO> permissionListObj) {
        this.permissionListObj = permissionListObj;
    }
    
    
}
