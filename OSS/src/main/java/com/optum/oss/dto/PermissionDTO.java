/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.dto;

import java.io.Serializable;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author hpasupuleti
 */
//@Component
public class PermissionDTO extends BaseDTO implements Serializable{
 
    private long permissionKey;
    private String encPermissionKey;
    private long parentPermissionKey;
    private long permissionTypeKey;
    private MasterLookUpDTO permissionTypeObj = new MasterLookUpDTO();
    private String permissionName;
    
    @NotBlank(message = "Please provide brief description of permission. Description should be at least one character and cannot exceed 1000 characters") 
    @Size(min = 0, max = 1000, message = "Definition description cannot exceed 1000 characters")
    private String permissionDescription;
    
    private String displayTest;
    private long childExistsIndicator;
    private long sequenceOrder;
    private long moduleId;
    private long menuId;
    private long subMenuId;
    private String moduleName;
    private String menuName;
    private String subMenuName;

    /**
     * @return the permissionKey
     */
    public long getPermissionKey() {
        return permissionKey;
    }

    /**
     * @param permissionKey the permissionKey to set
     */
    public void setPermissionKey(long permissionKey) {
        this.permissionKey = permissionKey;
    }

    /**
     * @return the parentPermissionKey
     */
    public long getParentPermissionKey() {
        return parentPermissionKey;
    }

    /**
     * @param parentPermissionKey the parentPermissionKey to set
     */
    public void setParentPermissionKey(long parentPermissionKey) {
        this.parentPermissionKey = parentPermissionKey;
    }

    /**
     * @return the permissionTypeKey
     */
    public long getPermissionTypeKey() {
        return permissionTypeKey;
    }

    /**
     * @param permissionTypeKey the permissionTypeKey to set
     */
    public void setPermissionTypeKey(long permissionTypeKey) {
        this.permissionTypeKey = permissionTypeKey;
    }

    /**
     * @return the permissionTypeObj
     */
    public MasterLookUpDTO getPermissionTypeObj() {
        return permissionTypeObj;
    }

    /**
     * @param permissionTypeObj the permissionTypeObj to set
     */
    public void setPermissionTypeObj(MasterLookUpDTO permissionTypeObj) {
        this.permissionTypeObj = permissionTypeObj;
    }

    /**
     * @return the permissionName
     */
    public String getPermissionName() {
        return permissionName;
    }

    /**
     * @param permissionName the permissionName to set
     */
    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    /**
     * @return the permissionDescription
     */
    public String getPermissionDescription() {
        return permissionDescription;
    }

    /**
     * @param permissionDescription the permissionDescription to set
     */
    public void setPermissionDescription(String permissionDescription) {
        this.permissionDescription = permissionDescription;
    }

    /**
     * @return the displayTest
     */
    public String getDisplayTest() {
        return displayTest;
    }

    /**
     * @param displayTest the displayTest to set
     */
    public void setDisplayTest(String displayTest) {
        this.displayTest = displayTest;
    }

    /**
     * @return the childExistsIndicator
     */
    public long getChildExistsIndicator() {
        return childExistsIndicator;
    }

    /**
     * @param childExistsIndicator the childExistsIndicator to set
     */
    public void setChildExistsIndicator(long childExistsIndicator) {
        this.childExistsIndicator = childExistsIndicator;
    }

    /**
     * @return the sequenceOrder
     */
    public long getSequenceOrder() {
        return sequenceOrder;
    }

    /**
     * @param sequenceOrder the sequenceOrder to set
     */
    public void setSequenceOrder(long sequenceOrder) {
        this.sequenceOrder = sequenceOrder;
    }

    /**
     * @return the moduleId
     */
    public long getModuleId() {
        return moduleId;
    }

    /**
     * @param moduleId the moduleId to set
     */
    public void setModuleId(long moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * @return the menuId
     */
    public long getMenuId() {
        return menuId;
    }

    /**
     * @param menuId the menuId to set
     */
    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    /**
     * @return the subMenuId
     */
    public long getSubMenuId() {
        return subMenuId;
    }

    /**
     * @param subMenuId the subMenuId to set
     */
    public void setSubMenuId(long subMenuId) {
        this.subMenuId = subMenuId;
    }

    /**
     * @return the encPermissionKey
     */
    public String getEncPermissionKey() {
        return encPermissionKey;
    }

    /**
     * @param encPermissionKey the encPermissionKey to set
     */
    public void setEncPermissionKey(String encPermissionKey) {
        this.encPermissionKey = encPermissionKey;
    }

    /**
     * @return the moduleName
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * @param moduleName the moduleName to set
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * @return the menuName
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * @param menuName the menuName to set
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    /**
     * @return the subMenuName
     */
    public String getSubMenuName() {
        return subMenuName;
    }

    /**
     * @param subMenuName the subMenuName to set
     */
    public void setSubMenuName(String subMenuName) {
        this.subMenuName = subMenuName;
    }
 }
