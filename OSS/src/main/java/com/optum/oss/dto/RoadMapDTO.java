/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.dto;

import java.io.Serializable;

/**
 *
 * @author sbhagavatula
 */
public class RoadMapDTO extends VulnerabilityDTO implements Serializable{
    
    private String csaDomainName;
    private String timeLineName;
    private String csaDomainCode;
    private String timeLineCode;
    private String effectiveDate;
    private String comments;

    /**
     * @return the csaDomainName
     */
    public String getCsaDomainName() {
        return csaDomainName;
    }

    /**
     * @param csaDomainName the csaDomainName to set
     */
    public void setCsaDomainName(String csaDomainName) {
        this.csaDomainName = csaDomainName;
    }

    /**
     * @return the timeLineName
     */
    public String getTimeLineName() {
        return timeLineName;
    }

    /**
     * @param timeLineName the timeLineName to set
     */
    public void setTimeLineName(String timeLineName) {
        this.timeLineName = timeLineName;
    }

    /**
     * @return the csaDomainCode
     */
    public String getCsaDomainCode() {
        return csaDomainCode;
    }

    /**
     * @param csaDomainCode the csaDomainCode to set
     */
    public void setCsaDomainCode(String csaDomainCode) {
        this.csaDomainCode = csaDomainCode;
    }

    /**
     * @return the timeLineCode
     */
    public String getTimeLineCode() {
        return timeLineCode;
    }

    /**
     * @param timeLineCode the timeLineCode to set
     */
    public void setTimeLineCode(String timeLineCode) {
        this.timeLineCode = timeLineCode;
    }

    /**
     * @return the effectiveDate
     */
    public String getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * @param effectiveDate the effectiveDate to set
     */
    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    
}
