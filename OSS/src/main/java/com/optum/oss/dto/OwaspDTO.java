/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dto;

/**
 *
 * @author mrejeti
 */
public class OwaspDTO {

    private Integer owaspId;
    private String owaspCode;
    private String owaspName;
    private String owaspDescription;
    private String owaspDate;
    private long owaspCurrentIndicator;

    /**
     * @return the owaspId
     */
    public Integer getOwaspId() {
        return owaspId;
    }

    /**
     * @param owaspId the owaspId to set
     */
    public void setOwaspId(Integer owaspId) {
        this.owaspId = owaspId;
    }

    /**
     * @return the owaspCode
     */
    public String getOwaspCode() {
        return owaspCode;
    }

    /**
     * @param owaspCode the owaspCode to set
     */
    public void setOwaspCode(String owaspCode) {
        this.owaspCode = owaspCode;
    }

    /**
     * @return the owaspName
     */
    public String getOwaspName() {
        return owaspName;
    }

    /**
     * @param owaspName the owaspName to set
     */
    public void setOwaspName(String owaspName) {
        this.owaspName = owaspName;
    }

    /**
     * @return the owaspDescription
     */
    public String getOwaspDescription() {
        return owaspDescription;
    }

    /**
     * @param owaspDescription the owaspDescription to set
     */
    public void setOwaspDescription(String owaspDescription) {
        this.owaspDescription = owaspDescription;
    }

    /**
     * @return the owaspDate
     */
    public String getOwaspDate() {
        return owaspDate;
    }

    /**
     * @param owaspDate the owaspDate to set
     */
    public void setOwaspDate(String owaspDate) {
        this.owaspDate = owaspDate;
    }

    /**
     * @return the owaspCurrentIndicator
     */
    public long getOwaspCurrentIndicator() {
        return owaspCurrentIndicator;
    }

    /**
     * @param owaspCurrentIndicator the owaspCurrentIndicator to set
     */
    public void setOwaspCurrentIndicator(long owaspCurrentIndicator) {
        this.owaspCurrentIndicator = owaspCurrentIndicator;
    }

}
