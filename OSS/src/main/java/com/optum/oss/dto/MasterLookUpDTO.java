/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.dto;

import java.io.Serializable;

/**
 *
 * @author hpasupuleti
 */
//@Component
public class MasterLookUpDTO extends BaseDTO implements Serializable{
    
    private long masterLookupKey;
    private String lookUpEntityTypeName;
    private String lookUpEntityName;
    private String lookUpEntityDescription;
    private String masterLookupCode; //Code is used as reference instead of key

    /**
     * @return the masterLookupKey
     */
    public long getMasterLookupKey() {
        return masterLookupKey;
    }

    /**
     * @param masterLookupKey the masterLookupKey to set
     */
    public void setMasterLookupKey(long masterLookupKey) {
        this.masterLookupKey = masterLookupKey;
    }

    /**
     * @return the lookUpEntityTypeName
     */
    public String getLookUpEntityTypeName() {
        return lookUpEntityTypeName;
    }

    /**
     * @param lookUpEntityTypeName the lookUpEntityTypeName to set
     */
    public void setLookUpEntityTypeName(String lookUpEntityTypeName) {
        this.lookUpEntityTypeName = lookUpEntityTypeName;
    }

    /**
     * @return the lookUpEntityName
     */
    public String getLookUpEntityName() {
        return lookUpEntityName;
    }

    /**
     * @param lookUpEntityName the lookUpEntityName to set
     */
    public void setLookUpEntityName(String lookUpEntityName) {
        this.lookUpEntityName = lookUpEntityName;
    }

    /**
     * @return the lookUpEntityDescription
     */
    public String getLookUpEntityDescription() {
        return lookUpEntityDescription;
    }

    /**
     * @param lookUpEntityDescription the lookUpEntityDescription to set
     */
    public void setLookUpEntityDescription(String lookUpEntityDescription) {
        this.lookUpEntityDescription = lookUpEntityDescription;
    }

    /**
     * @return the masterLookupCode
     */
    public String getMasterLookupCode() {
        return masterLookupCode;
    }

    /**
     * @param masterLookupCode the masterLookupCode to set
     */
    public void setMasterLookupCode(String masterLookupCode) {
        this.masterLookupCode = masterLookupCode;
    }
    
    
}
