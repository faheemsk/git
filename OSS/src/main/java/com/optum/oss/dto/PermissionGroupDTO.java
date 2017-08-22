/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dto;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hpasupuleti
 */
//@Component
public class PermissionGroupDTO extends BaseDTO implements Serializable {

    private long permissionGroupKey;

    @JsonView(Views.Public.class)
    private String permissionGroupName;

    private String encPermissionGroupName;

    @JsonView(Views.Public.class)
    private String permissionGroupDesc;

    private String[] permissionGroupKeyChkIds;

    private String permissionGroupStatusValue;

    private List<PermissionDTO> permissionListObj = new ArrayList<>();

    private String encPermissionGroupKey;

    private String reason;
    
    private long appRoleKey; 
    
    private String appRoleName;
    /**
     * @return the permissionGroupKey
     */
    public long getPermissionGroupKey() {
        return permissionGroupKey;
    }

    /**
     * @param permissionGroupKey the permissionGroupKey to set
     */
    public void setPermissionGroupKey(long permissionGroupKey) {
        this.permissionGroupKey = permissionGroupKey;
    }

    /**
     * @return the permissionGroupName
     */
    public String getPermissionGroupName() {
        return permissionGroupName;
    }

    /**
     * @param permissionGroupName the permissionGroupName to set
     */
    public void setPermissionGroupName(String permissionGroupName) {
        this.permissionGroupName = permissionGroupName;
    }

    /**
     * @return the permissionGroupDesc
     */
    public String getPermissionGroupDesc() {
        return permissionGroupDesc;
    }

    /**
     * @param permissionGroupDesc the permissionGroupDesc to set
     */
    public void setPermissionGroupDesc(String permissionGroupDesc) {
        this.permissionGroupDesc = permissionGroupDesc;
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

    /**
     * @return the getPermissionGroupKeyChkIds
     */
    public String[] getPermissionGroupKeyChkIds() {
        return permissionGroupKeyChkIds;
    }

    /**
     * @param permissionGroupKeyChkIds the permissionGroupKeyChkIds to set
     */
    public void setPermissionGroupKeyChkIds(String[] permissionGroupKeyChkIds) {
        this.permissionGroupKeyChkIds = permissionGroupKeyChkIds;
    }

    /**
     * @return the permissionGroupStatusValue
     */
    public String getPermissionGroupStatusValue() {
        return permissionGroupStatusValue;
    }

    /**
     * @param permissionGroupStatusValue the permissionGroupStatusValue to set
     */
    public void setPermissionGroupStatusValue(String permissionGroupStatusValue) {
        this.permissionGroupStatusValue = permissionGroupStatusValue;
    }

    /**
     * @return the encPermissionGroupKey
     */
    public String getEncPermissionGroupKey() {
        return encPermissionGroupKey;
    }

    /**
     * @param encPermissionGroupKey the encPermissionGroupKey to set
     */
    public void setEncPermissionGroupKey(String encPermissionGroupKey) {
        this.encPermissionGroupKey = encPermissionGroupKey;
    }

    /**
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason the reason to set
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * @return the encPermissionGroupName
     */
    public String getEncPermissionGroupName() {
        return encPermissionGroupName;
    }

    /**
     * @param encPermissionGroupName the encPermissionGroupName to set
     */
    public void setEncPermissionGroupName(String encPermissionGroupName) {
        this.encPermissionGroupName = encPermissionGroupName;
    }

    public long getAppRoleKey() {
        return appRoleKey;
    }

    public void setAppRoleKey(long appRoleKey) {
        this.appRoleKey = appRoleKey;
    }

    public String getAppRoleName() {
        return appRoleName;
    }

    public void setAppRoleName(String appRoleName) {
        this.appRoleName = appRoleName;
    }
    
    }
