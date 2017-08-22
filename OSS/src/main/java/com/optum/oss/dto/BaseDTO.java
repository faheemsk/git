/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.dto;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author hpasupuleti
 */
//@Component
public class BaseDTO implements Serializable{
    
    private long createdByUserID;
    private String createdByUser;
    private long modifiedByUserID;
    private String modifiedByUser;
    @JsonView(Views.Public.class)
    private String createdDate;
    private String modifiedDate;
    private String deleteIndicator;
    private long rowStatusKey;
    private String rowStatusValue;
    private String encRowStatusValue;
    private String orgSchema;

    /**
     * @return the createdByUserID
     */
    public long getCreatedByUserID() {
        return createdByUserID;
    }

    /**
     * @param createdByUserID the createdByUserID to set
     */
    public void setCreatedByUserID(long createdByUserID) {
        this.createdByUserID = createdByUserID;
    }

    /**
     * @return the createdByUser
     */
    public String getCreatedByUser() {
        return createdByUser;
    }

    /**
     * @param createdByUser the createdByUser to set
     */
    public void setCreatedByUser(String createdByUser) {
        this.createdByUser = createdByUser;
    }

    /**
     * @return the modifiedByUserID
     */
    public long getModifiedByUserID() {
        return modifiedByUserID;
    }

    /**
     * @param modifiedByUserID the modifiedByUserID to set
     */
    public void setModifiedByUserID(long modifiedByUserID) {
        this.modifiedByUserID = modifiedByUserID;
    }

    /**
     * @return the modifiedByUser
     */
    public String getModifiedByUser() {
        return modifiedByUser;
    }

    /**
     * @param modifiedByUser the modifiedByUser to set
     */
    public void setModifiedByUser(String modifiedByUser) {
        this.modifiedByUser = modifiedByUser;
    }

   
    /**
     * @return the deleteIndicator
     */
    public String getDeleteIndicator() {
        return deleteIndicator;
    }

    /**
     * @param deleteIndicator the deleteIndicator to set
     */
    public void setDeleteIndicator(String deleteIndicator) {
        this.deleteIndicator = deleteIndicator;
    }

    /**
     * @return the rowStatusKey
     */
    public long getRowStatusKey() {
        return rowStatusKey;
    }

    /**
     * @param rowStatusKey the rowStatusKey to set
     */
    public void setRowStatusKey(long rowStatusKey) {
        this.rowStatusKey = rowStatusKey;
    }

    /**
     * @return the rowStatusValue
     */
    public String getRowStatusValue() {
        return rowStatusValue;
    }

    /**
     * @param rowStatusValue the rowStatusValue to set
     */
    public void setRowStatusValue(String rowStatusValue) {
        this.rowStatusValue = rowStatusValue;
    }

    /**
     * @return the createdDate
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the modifiedDate
     */
    public String getModifiedDate() {
        return modifiedDate;
    }

    /**
     * @param modifiedDate the modifiedDate to set
     */
    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getEncRowStatusValue() {
        return encRowStatusValue;
    }

    public void setEncRowStatusValue(String encRowStatusValue) {
        this.encRowStatusValue = encRowStatusValue;
    }
     /**
     * @return the orgSchema
     */
    public String getOrgSchema() {
        return orgSchema;
    }

    /**
     * @param orgSchema the orgSchema to set
     */
    public void setOrgSchema(String orgSchema) {
        this.orgSchema = orgSchema;
    }


    
    
}
