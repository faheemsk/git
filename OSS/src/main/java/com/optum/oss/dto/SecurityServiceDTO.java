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
public class SecurityServiceDTO extends BaseDTO implements Serializable{
    
    private long securityServiceKey;
    private String encSecurityServiceKey;
    @JsonView(Views.Public.class)
    private String securityServiceCode;
    @JsonView(Views.Public.class)
    private String securityServiceName;
    private String securityServiceDesc;
    private String serviceStartDate;
    private String serviceEndDate;
    private long userTypeID;
    private long userID;
    private List<ManageServiceUserDTO> manageServiceUserLi = new ArrayList<>();
    
    private String reviewStatus;
    private String fileCount; //Files uploaded for the service

    /**
     * @return the securityServiceKey
     */
    public long getSecurityServiceKey() {
        return securityServiceKey;
    }

    /**
     * @param securityServiceKey the securityServiceKey to set
     */
    public void setSecurityServiceKey(long securityServiceKey) {
        this.securityServiceKey = securityServiceKey;
    }

    /**
     * @return the encSecurityServiceKey
     */
    public String getEncSecurityServiceKey() {
        return encSecurityServiceKey;
    }

    /**
     * @param encSecurityServiceKey the encSecurityServiceKey to set
     */
    public void setEncSecurityServiceKey(String encSecurityServiceKey) {
        this.encSecurityServiceKey = encSecurityServiceKey;
    }

    /**
     * @return the securityServiceCode
     */
    public String getSecurityServiceCode() {
        return securityServiceCode;
    }

    /**
     * @param securityServiceCode the securityServiceCode to set
     */
    public void setSecurityServiceCode(String securityServiceCode) {
        this.securityServiceCode = securityServiceCode;
    }

    /**
     * @return the securityServiceName
     */
    public String getSecurityServiceName() {
        return securityServiceName;
    }

    /**
     * @param securityServiceName the securityServiceName to set
     */
    public void setSecurityServiceName(String securityServiceName) {
        this.securityServiceName = securityServiceName;
    }

    /**
     * @return the securityServiceDesc
     */
    public String getSecurityServiceDesc() {
        return securityServiceDesc;
    }

    /**
     * @param securityServiceDesc the securityServiceDesc to set
     */
    public void setSecurityServiceDesc(String securityServiceDesc) {
        this.securityServiceDesc = securityServiceDesc;
    }

    /**
     * @return the serviceStartDate
     */
    public String getServiceStartDate() {
        return serviceStartDate;
    }

    /**
     * @param serviceStartDate the serviceStartDate to set
     */
    public void setServiceStartDate(String serviceStartDate) {
        this.serviceStartDate = serviceStartDate;
    }

    /**
     * @return the serviceEndDate
     */
    public String getServiceEndDate() {
        return serviceEndDate;
    }

    /**
     * @param serviceEndDate the serviceEndDate to set
     */
    public void setServiceEndDate(String serviceEndDate) {
        this.serviceEndDate = serviceEndDate;
    }

    /**
     * @return the userTypeID
     */
    public long getUserTypeID() {
        return userTypeID;
    }

    /**
     * @param userTypeID the userTypeID to set
     */
    public void setUserTypeID(long userTypeID) {
        this.userTypeID = userTypeID;
    }

    /**
     * @return the userID
     */
    public long getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(long userID) {
        this.userID = userID;
    }

    /**
     * @return the manageServiceUserLi
     */
    public List<ManageServiceUserDTO> getManageServiceUserLi() {
        return manageServiceUserLi;
    }

    /**
     * @param manageServiceUserLi the manageServiceUserLi to set
     */
    public void setManageServiceUserLi(List<ManageServiceUserDTO> manageServiceUserLi) {
        this.manageServiceUserLi = manageServiceUserLi;
    }

    /**
     * @return the reviewStatus
     */
    public String getReviewStatus() {
        return reviewStatus;
    }

    /**
     * @param reviewStatus the reviewStatus to set
     */
    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    /**
     * @return the fileCount
     */
    public String getFileCount() {
        return fileCount;
    }

    /**
     * @param fileCount the fileCount to set
     */
    public void setFileCount(String fileCount) {
        this.fileCount = fileCount;
    }
}
