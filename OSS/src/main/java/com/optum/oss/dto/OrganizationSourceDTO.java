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
public class OrganizationSourceDTO extends BaseDTO implements Serializable{

    private long organizationSourceKey;
    private long organizationKey;
    private long sourceKey;
    private MasterLookUpDTO sourceObj = new MasterLookUpDTO();
    private String sourceClientID;
    private String sourceName;

    /**
     * @return the organizationSourceKey
     */
    public long getOrganizationSourceKey() {
        return organizationSourceKey;
    }

    /**
     * @param organizationSourceKey the organizationSourceKey to set
     */
    public void setOrganizationSourceKey(long organizationSourceKey) {
        this.organizationSourceKey = organizationSourceKey;
    }

    /**
     * @return the organizationKey
     */
    public long getOrganizationKey() {
        return organizationKey;
    }

    /**
     * @param organizationKey the organizationKey to set
     */
    public void setOrganizationKey(long organizationKey) {
        this.organizationKey = organizationKey;
    }

    /**
     * @return the sourceKey
     */
    public long getSourceKey() {
        return sourceKey;
    }

    /**
     * @param sourceKey the sourceKey to set
     */
    public void setSourceKey(long sourceKey) {
        this.sourceKey = sourceKey;
    }

    /**
     * @return the sourceObj
     */
    public MasterLookUpDTO getSourceObj() {
        return sourceObj;
    }

    /**
     * @param sourceObj the sourceObj to set
     */
    public void setSourceObj(MasterLookUpDTO sourceObj) {
        this.sourceObj = sourceObj;
    }

    /**
     * @return the sourceClientID
     */
    public String getSourceClientID() {
        return sourceClientID;
    }

    /**
     * @param sourceClientID the sourceClientID to set
     */
    public void setSourceClientID(String sourceClientID) {
        this.sourceClientID = sourceClientID;
    }

    /**
     * @return the sourceName
     */
    public String getSourceName() {
        return sourceName;
    }

    /**
     * @param sourceName the sourceName to set
     */
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    }
