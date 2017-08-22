/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dto;

import java.io.Serializable;

/**
 *
 * @author hpasupuleti 
 */
//@Component
public class EmailNotificationDTO extends BaseDTO implements Serializable{
    
    private String emailInsertFlag;
    private long emailLogId;
    private long emailNotificationId;
    private long userId;
    private String fromEmailId; 
    private String toEmailId;
    private String ccEmailId;
    private String  [] ccEmailArray;
    private String  [] toEmailArray;
    private String bccEmailId;
    private String emailSubject;
    private String emailContent;
    private int emailSuccessFlag;
    private int resendCount;
    private String entryDate;
    private String errorDescription;
    private long orgId;

    /**
     * @return the emailInsertFlag
     */
    public String getEmailInsertFlag() {
        return emailInsertFlag;
    }

    /**
     * @param emailInsertFlag the emailInsertFlag to set
     */
    public void setEmailInsertFlag(String emailInsertFlag) {
        this.emailInsertFlag = emailInsertFlag;
    }

    /**
     * @return the emailLogId
     */
    public long getEmailLogId() {
        return emailLogId;
    }

    /**
     * @param emailLogId the emailLogId to set
     */
    public void setEmailLogId(long emailLogId) {
        this.emailLogId = emailLogId;
    }

    /**
     * @return the emailNotificationId
     */
    public long getEmailNotificationId() {
        return emailNotificationId;
    }

    /**
     * @param emailNotificationId the emailNotificationId to set
     */
    public void setEmailNotificationId(long emailNotificationId) {
        this.emailNotificationId = emailNotificationId;
    }

    /**
     * @return the userId
     */
    public long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * @return the fromEmailId
     */
    public String getFromEmailId() {
        return fromEmailId;
    }

    /**
     * @param fromEmailId the fromEmailId to set
     */
    public void setFromEmailId(String fromEmailId) {
        this.fromEmailId = fromEmailId;
    }

    /**
     * @return the toEmailId
     */
    public String getToEmailId() {
        return toEmailId;
    }

    /**
     * @param toEmailId the toEmailId to set
     */
    public void setToEmailId(String toEmailId) {
        this.toEmailId = toEmailId;
    }

    /**
     * @return the ccEmailId
     */
    public String getCcEmailId() {
        return ccEmailId;
    }

    /**
     * @param ccEmailId the ccEmailId to set
     */
    public void setCcEmailId(String ccEmailId) {
        this.ccEmailId = ccEmailId;
    }

    /**
     * @return the ccEmailArray
     */
    public String[] getCcEmailArray() {
        return ccEmailArray;
    }

    /**
     * @param ccEmailArray the ccEmailArray to set
     */
    public void setCcEmailArray(String[] ccEmailArray) {
        this.ccEmailArray = ccEmailArray;
    }

    /**
     * @return the toEmailArray
     */
    public String[] getToEmailArray() {
        return toEmailArray;
    }

    /**
     * @param toEmailArray the toEmailArray to set
     */
    public void setToEmailArray(String[] toEmailArray) {
        this.toEmailArray = toEmailArray;
    }

    /**
     * @return the bccEmailId
     */
    public String getBccEmailId() {
        return bccEmailId;
    }

    /**
     * @param bccEmailId the bccEmailId to set
     */
    public void setBccEmailId(String bccEmailId) {
        this.bccEmailId = bccEmailId;
    }

    /**
     * @return the emailSubject
     */
    public String getEmailSubject() {
        return emailSubject;
    }

    /**
     * @param emailSubject the emailSubject to set
     */
    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    /**
     * @return the emailContent
     */
    public String getEmailContent() {
        return emailContent;
    }

    /**
     * @param emailContent the emailContent to set
     */
    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    /**
     * @return the emailSuccessFlag
     */
    public int getEmailSuccessFlag() {
        return emailSuccessFlag;
    }

    /**
     * @param emailSuccessFlag the emailSuccessFlag to set
     */
    public void setEmailSuccessFlag(int emailSuccessFlag) {
        this.emailSuccessFlag = emailSuccessFlag;
    }

    /**
     * @return the resendCount
     */
    public int getResendCount() {
        return resendCount;
    }

    /**
     * @param resendCount the resendCount to set
     */
    public void setResendCount(int resendCount) {
        this.resendCount = resendCount;
    }

    /**
     * @return the entryDate
     */
    public String getEntryDate() {
        return entryDate;
    }

    /**
     * @param entryDate the entryDate to set
     */
    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    /**
     * @return the errorDescription
     */
    public String getErrorDescription() {
        return errorDescription;
    }

    /**
     * @param errorDescription the errorDescription to set
     */
    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    /**
     * @return the orgId
     */
    public long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }
    
    

           
}
