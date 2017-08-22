/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dto;

import java.io.Serializable;

/**
 *
 * @author sathuluri
 *
 * This DTO used for Manage Users to Services page in client engagement module
 */
//@Component
public class ManageServiceUserDTO extends BaseDTO implements Serializable {

    private long manageServiceUserKey;
    private long roleKey;
    private String roleName;
    private long userTypeKey;
    private String userTypeValue;
    private long organizationKey;
    private String organizationName;
    private long userKey;
    private String userName;
    private String startDate;
    private String endDate;
    private String serviceName;
    private String secureServiceCode;
    private String encSecureServiceCode;
    private String clientEngagementCode;
    private String encClientEngagementCode;
    private String clientEngagementEndDate;
    private long oldAssignedUserKey;

    //Added properties for Email notifications due date passed / approached
    private String engagementLeadName;
    private String engagementLeadEmail;
    private String analystName;
    private String analystEmail;
    private String serviceEstimatedStartDate;
    private String serviceEstimatedEndDate;
    private String clientEngagementName;
    private String engagementEstimatedEndDate;
    private int fileLockIndicator;
    private String userFlag;
    private String updateDate;
    private int fileCount;
    private String engagementName;
    
    private String encString;
    private String requestSalt;

    /**
     * @return the roleKey
     */
    public long getRoleKey() {
        return roleKey;
    }

    /**
     * @param roleKey the roleKey to set
     */
    public void setRoleKey(long roleKey) {
        this.roleKey = roleKey;
    }

    /**
     * @return the roleName
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * @param roleName the roleName to set
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * @return the userTypeKey
     */
    public long getUserTypeKey() {
        return userTypeKey;
    }

    /**
     * @param userTypeKey the userTypeKey to set
     */
    public void setUserTypeKey(long userTypeKey) {
        this.userTypeKey = userTypeKey;
    }

    /**
     * @return the userTypeValue
     */
    public String getUserTypeValue() {
        return userTypeValue;
    }

    /**
     * @param userTypeValue the userTypeValue to set
     */
    public void setUserTypeValue(String userTypeValue) {
        this.userTypeValue = userTypeValue;
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
     * @return the organizationName
     */
    public String getOrganizationName() {
        return organizationName;
    }

    /**
     * @param organizationName the organizationName to set
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    /**
     * @return the userKey
     */
    public long getUserKey() {
        return userKey;
    }

    /**
     * @param userKey the userKey to set
     */
    public void setUserKey(long userKey) {
        this.userKey = userKey;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the serviceName
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @param serviceName the serviceName to set
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * @return the manageServiceUserKey
     */
    public long getManageServiceUserKey() {
        return manageServiceUserKey;
    }

    /**
     * @param manageServiceUserKey the manageServiceUserKey to set
     */
    public void setManageServiceUserKey(long manageServiceUserKey) {
        this.manageServiceUserKey = manageServiceUserKey;
    }

    /**
     * @return the secureServiceCode
     */
    public String getSecureServiceCode() {
        return secureServiceCode;
    }

    /**
     * @param secureServiceCode the secureServiceCode to set
     */
    public void setSecureServiceCode(String secureServiceCode) {
        this.secureServiceCode = secureServiceCode;
    }

    /**
     * @return the clientEngagementCode
     */
    public String getClientEngagementCode() {
        return clientEngagementCode;
    }

    /**
     * @param clientEngagementCode the clientEngagementCode to set
     */
    public void setClientEngagementCode(String clientEngagementCode) {
        this.clientEngagementCode = clientEngagementCode;
    }

    /**
     * @return the clientEngagementEndDate
     */
    public String getClientEngagementEndDate() {
        return clientEngagementEndDate;
    }

    /**
     * @param clientEngagementEndDate the clientEngagementEndDate to set
     */
    public void setClientEngagementEndDate(String clientEngagementEndDate) {
        this.clientEngagementEndDate = clientEngagementEndDate;
    }

    /**
     * @return the oldAssignedUserKey
     */
    public long getOldAssignedUserKey() {
        return oldAssignedUserKey;
    }

    /**
     * @param oldAssignedUserKey the oldAssignedUserKey to set
     */
    public void setOldAssignedUserKey(long oldAssignedUserKey) {
        this.oldAssignedUserKey = oldAssignedUserKey;
    }

    /**
     * @return the engagementLeadName
     */
    public String getEngagementLeadName() {
        return engagementLeadName;
    }

    /**
     * @param engagementLeadName the engagementLeadName to set
     */
    public void setEngagementLeadName(String engagementLeadName) {
        this.engagementLeadName = engagementLeadName;
    }

    /**
     * @return the engagementLeadEmail
     */
    public String getEngagementLeadEmail() {
        return engagementLeadEmail;
    }

    /**
     * @param engagementLeadEmail the engagementLeadEmail to set
     */
    public void setEngagementLeadEmail(String engagementLeadEmail) {
        this.engagementLeadEmail = engagementLeadEmail;
    }

    /**
     * @return the analystName
     */
    public String getAnalystName() {
        return analystName;
    }

    /**
     * @param analystName the analystName to set
     */
    public void setAnalystName(String analystName) {
        this.analystName = analystName;
    }

    /**
     * @return the analystEmail
     */
    public String getAnalystEmail() {
        return analystEmail;
    }

    /**
     * @param analystEmail the analystEmail to set
     */
    public void setAnalystEmail(String analystEmail) {
        this.analystEmail = analystEmail;
    }

    /**
     * @return the serviceEstimatedStartDate
     */
    public String getServiceEstimatedStartDate() {
        return serviceEstimatedStartDate;
    }

    /**
     * @param serviceEstimatedStartDate the serviceEstimatedStartDate to set
     */
    public void setServiceEstimatedStartDate(String serviceEstimatedStartDate) {
        this.serviceEstimatedStartDate = serviceEstimatedStartDate;
    }

    /**
     * @return the serviceEstimatedEndDate
     */
    public String getServiceEstimatedEndDate() {
        return serviceEstimatedEndDate;
    }

    /**
     * @param serviceEstimatedEndDate the serviceEstimatedEndDate to set
     */
    public void setServiceEstimatedEndDate(String serviceEstimatedEndDate) {
        this.serviceEstimatedEndDate = serviceEstimatedEndDate;
    }

    /**
     * @return the clientEngagementName
     */
    public String getClientEngagementName() {
        return clientEngagementName;
    }

    /**
     * @param clientEngagementName the clientEngagementName to set
     */
    public void setClientEngagementName(String clientEngagementName) {
        this.clientEngagementName = clientEngagementName;
    }

    /**
     * @return the engagementEstimatedEndDate
     */
    public String getEngagementEstimatedEndDate() {
        return engagementEstimatedEndDate;
    }

    /**
     * @param engagementEstimatedEndDate the engagementEstimatedEndDate to set
     */
    public void setEngagementEstimatedEndDate(String engagementEstimatedEndDate) {
        this.engagementEstimatedEndDate = engagementEstimatedEndDate;
    }

    /**
     * @return the fileLockIndicator
     */
    public int getFileLockIndicator() {
        return fileLockIndicator;
    }

    /**
     * @param fileLockIndicator the fileLockIndicator to set
     */
    public void setFileLockIndicator(int fileLockIndicator) {
        this.fileLockIndicator = fileLockIndicator;
    }

    /**
     * @return the userFlag
     */
    public String getUserFlag() {
        return userFlag;
    }

    /**
     * @param userFlag the userFlag to set
     */
    public void setUserFlag(String userFlag) {
        this.userFlag = userFlag;
    }

    /**
     * @return the updateDate
     */
    public String getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate the updateDate to set
     */
    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * @return the fileCount
     */
    public int getFileCount() {
        return fileCount;
    }

    /**
     * @param fileCount the fileCount to set
     */
    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    /**
     * @return the engagementName
     */
    public String getEngagementName() {
        return engagementName;
    }

    /**
     * @param engagementName the engagementName to set
     */
    public void setEngagementName(String engagementName) {
        this.engagementName = engagementName;
    }

    public String getEncSecureServiceCode() {
        return encSecureServiceCode;
    }

    public void setEncSecureServiceCode(String encSecureServiceCode) {
        this.encSecureServiceCode = encSecureServiceCode;
    }

    public String getEncClientEngagementCode() {
        return encClientEngagementCode;
    }

    public void setEncClientEngagementCode(String encClientEngagementCode) {
        this.encClientEngagementCode = encClientEngagementCode;
    }

    /**
     * @return the encString
     */
    public String getEncString() {
        return encString;
    }

    /**
     * @param encString the encString to set
     */
    public void setEncString(String encString) {
        this.encString = encString;
    }

    /**
     * @return the requestSalt
     */
    public String getRequestSalt() {
        return requestSalt;
    }

    /**
     * @param requestSalt the requestSalt to set
     */
    public void setRequestSalt(String requestSalt) {
        this.requestSalt = requestSalt;
    }
    
    

}
