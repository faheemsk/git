/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author hpasupuleti
 */
//@Component
public class ClientEngagementDTO extends BaseDTO implements Serializable {

    private long clientEngagementKey;
    private String encClientEngagementKey;
    private String engagementName;
    private String engagementCode;
    private String encEngagementCode;
    private long clientID; //USED TO STORE THE CLIENT ORGANIZATION ID
    private long securityPackageKey;
    private SecurityPackageDTO securityPackageObj = new SecurityPackageDTO();
    private String agreementDate;
    private String estimatedStartDate;
    private String estimatedEndDate;
    private String engagementDesc;
    private long engagementStatusKey;
    private String engagementStatusName;
    private String engagementStatusComment;
    private List<SecurityServiceDTO> engagementServiceLi = new ArrayList<>();
    private long engagementLeadClientUserID;
    private long engagementLeadInternalUserID;
    private String engagementComments;
    private List<ClientEngagementSourceDTO> clientEngagementSourceLi = new ArrayList<>();
    private List<EngagementPartnerUserDTO> engagementPartnerUserLi = new ArrayList<>();
    private String clientName;
    private String parentClientName;
    private String engagementLeadClientUserName;
    //Start: Bug Id: IN021478 : For engagement client lead mail notification
    private String engagementLeadClientEmailId;
    //End: Bug Id: IN021478 : For engagement client lead mail notification
    private String engagementLeadInternalUserName;
    private String clientUserType;
    private String internalUserType;
    private String selectedServices;
    private String previousSelectedServices;
    //Below fields Added for Engagement Data upload
    private long sourceNameKey;
    private String sourceName;
    private long documentTypeKey;
    private String documentType;
    private String uploadFileName;
    private String uploadFilePath;
    private String uploadComments;
    private String securityServiceCode;
    private String encSecurityServiceCode;
    private String securityServiceName;
    private long serviceLockFlag;
    private MultipartFile templateupload;
    private long fileUploadID; 
    private String encfileUploadID;
    private boolean lockCheckbox;
    private String tempFilePath;
    private String userTypeFlag;
    private long fileStatusKey;
    private String uploadedFilesCount;
    
    private String encS;   
    private String encFileDownload;
    private long uploadFileSize;
    private String analystEmailID;
    private String leadEmailID;
    
    private String partnerUserValidMsg;
    private String publishedDate;
    /**
     * @return the clientEngagementKey
     */
    public long getClientEngagementKey() {
        return clientEngagementKey;
    }

    /**
     * @param clientEngagementKey the clientEngagementKey to set
     */
    public void setClientEngagementKey(long clientEngagementKey) {
        this.clientEngagementKey = clientEngagementKey;
    }

    /**
     * @return the encClientEngagementKey
     */
    public String getEncClientEngagementKey() {
        return encClientEngagementKey;
    }

    /**
     * @param encClientEngagementKey the encClientEngagementKey to set
     */
    public void setEncClientEngagementKey(String encClientEngagementKey) {
        this.encClientEngagementKey = encClientEngagementKey;
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

    /**
     * @return the engagementCode
     */
    public String getEngagementCode() {
        return engagementCode;
    }

    /**
     * @param engagementCode the engagementCode to set
     */
    public void setEngagementCode(String engagementCode) {
        this.engagementCode = engagementCode;
    }

    /**
     * @return the clientID
     */
    public long getClientID() {
        return clientID;
    }

    /**
     * @param clientID the clientID to set
     */
    public void setClientID(long clientID) {
        this.clientID = clientID;
    }

    /**
     * @return the securityPackageKey
     */
    public long getSecurityPackageKey() {
        return securityPackageKey;
    }

    /**
     * @param securityPackageKey the securityPackageKey to set
     */
    public void setSecurityPackageKey(long securityPackageKey) {
        this.securityPackageKey = securityPackageKey;
    }

    /**
     * @return the securityPackageObj
     */
    public SecurityPackageDTO getSecurityPackageObj() {
        return securityPackageObj;
    }

    /**
     * @param securityPackageObj the securityPackageObj to set
     */
    public void setSecurityPackageObj(SecurityPackageDTO securityPackageObj) {
        this.securityPackageObj = securityPackageObj;
    }

    /**
     * @return the agreementDate
     */
    public String getAgreementDate() {
        return agreementDate;
    }

    /**
     * @param agreementDate the agreementDate to set
     */
    public void setAgreementDate(String agreementDate) {
        this.agreementDate = agreementDate;
    }

    /**
     * @return the estimatedStartDate
     */
    public String getEstimatedStartDate() {
        return estimatedStartDate;
    }

    /**
     * @param estimatedStartDate the estimatedStartDate to set
     */
    public void setEstimatedStartDate(String estimatedStartDate) {
        this.estimatedStartDate = estimatedStartDate;
    }

    /**
     * @return the estimatedEndDate
     */
    public String getEstimatedEndDate() {
        return estimatedEndDate;
    }

    /**
     * @param estimatedEndDate the estimatedEndDate to set
     */
    public void setEstimatedEndDate(String estimatedEndDate) {
        this.estimatedEndDate = estimatedEndDate;
    }

    /**
     * @return the engagementDesc
     */
    public String getEngagementDesc() {
        return engagementDesc;
    }

    /**
     * @param engagementDesc the engagementDesc to set
     */
    public void setEngagementDesc(String engagementDesc) {
        this.engagementDesc = engagementDesc;
    }

    /**
     * @return the engagementStatusKey
     */
    public long getEngagementStatusKey() {
        return engagementStatusKey;
    }

    /**
     * @param engagementStatusKey the engagementStatusKey to set
     */
    public void setEngagementStatusKey(long engagementStatusKey) {
        this.engagementStatusKey = engagementStatusKey;
    }

    /**
     * @return the engagementStatusName
     */
    public String getEngagementStatusName() {
        return engagementStatusName;
    }

    /**
     * @param engagementStatusName the engagementStatusName to set
     */
    public void setEngagementStatusName(String engagementStatusName) {
        this.engagementStatusName = engagementStatusName;
    }

    /**
     * @return the engagementStatusComment
     */
    public String getEngagementStatusComment() {
        return engagementStatusComment;
    }

    /**
     * @param engagementStatusComment the engagementStatusComment to set
     */
    public void setEngagementStatusComment(String engagementStatusComment) {
        this.engagementStatusComment = engagementStatusComment;
    }

    /**
     * @return the engagementServiceLi
     */
    public List<SecurityServiceDTO> getEngagementServiceLi() {
        return engagementServiceLi;
    }

    /**
     * @param engagementServiceLi the engagementServiceLi to set
     */
    public void setEngagementServiceLi(List<SecurityServiceDTO> engagementServiceLi) {
        this.engagementServiceLi = engagementServiceLi;
    }

    /**
     * @return the engagementLeadClientUserID
     */
    public long getEngagementLeadClientUserID() {
        return engagementLeadClientUserID;
    }

    /**
     * @param engagementLeadClientUserID the engagementLeadClientUserID to set
     */
    public void setEngagementLeadClientUserID(long engagementLeadClientUserID) {
        this.engagementLeadClientUserID = engagementLeadClientUserID;
    }

    /**
     * @return the engagementLeadInternalUserID
     */
    public long getEngagementLeadInternalUserID() {
        return engagementLeadInternalUserID;
    }

    /**
     * @param engagementLeadInternalUserID the engagementLeadInternalUserID to
     * set
     */
    public void setEngagementLeadInternalUserID(long engagementLeadInternalUserID) {
        this.engagementLeadInternalUserID = engagementLeadInternalUserID;
    }

    /**
     * @return the engagementComments
     */
    public String getEngagementComments() {
        return engagementComments;
    }

    /**
     * @param engagementComments the engagementComments to set
     */
    public void setEngagementComments(String engagementComments) {
        this.engagementComments = engagementComments;
    }

    /**
     * @return the clientEngagementSourceLi
     */
    public List<ClientEngagementSourceDTO> getClientEngagementSourceLi() {
        return clientEngagementSourceLi;
    }

    /**
     * @param clientEngagementSourceLi the clientEngagementSourceLi to set
     */
    public void setClientEngagementSourceLi(List<ClientEngagementSourceDTO> clientEngagementSourceLi) {
        this.clientEngagementSourceLi = clientEngagementSourceLi;
    }

    /**
     * @return the engagementPartnerUserLi
     */
    public List<EngagementPartnerUserDTO> getEngagementPartnerUserLi() {
        return engagementPartnerUserLi;
    }

    /**
     * @param engagementPartnerUserLi the engagementPartnerUserLi to set
     */
    public void setEngagementPartnerUserLi(List<EngagementPartnerUserDTO> engagementPartnerUserLi) {
        this.engagementPartnerUserLi = engagementPartnerUserLi;
    }

    /**
     * @return the clientName
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * @param clientName the clientName to set
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * @return the parentClientName
     */
    public String getParentClientName() {
        return parentClientName;
    }

    /**
     * @param parentClientName the parentClientName to set
     */
    public void setParentClientName(String parentClientName) {
        this.parentClientName = parentClientName;
    }

    /**
     * @return the engagementLeadClientUserName
     */
    public String getEngagementLeadClientUserName() {
        return engagementLeadClientUserName;
    }

    /**
     * @param engagementLeadClientUserName the engagementLeadClientUserName to
     * set
     */
    public void setEngagementLeadClientUserName(String engagementLeadClientUserName) {
        this.engagementLeadClientUserName = engagementLeadClientUserName;
    }

    /**
     * @return the engagementLeadInternalUserName
     */
    public String getEngagementLeadInternalUserName() {
        return engagementLeadInternalUserName;
    }

    /**
     * @param engagementLeadInternalUserName the engagementLeadInternalUserName
     * to set
     */
    public void setEngagementLeadInternalUserName(String engagementLeadInternalUserName) {
        this.engagementLeadInternalUserName = engagementLeadInternalUserName;
    }

    /**
     * @return the clientUserType
     */
    public String getClientUserType() {
        return clientUserType;
    }

    /**
     * @param clientUserType the clientUserType to set
     */
    public void setClientUserType(String clientUserType) {
        this.clientUserType = clientUserType;
    }

    /**
     * @return the internalUserType
     */
    public String getInternalUserType() {
        return internalUserType;
    }

    /**
     * @param internalUserType the internalUserType to set
     */
    public void setInternalUserType(String internalUserType) {
        this.internalUserType = internalUserType;
    }

    /**
     * @return the selectedServices
     */
    public String getSelectedServices() {
        return selectedServices;
    }

    /**
     * @param selectedServices the selectedServices to set
     */
    public void setSelectedServices(String selectedServices) {
        this.selectedServices = selectedServices;
    }

    /**
     * @return the previousSelectedServices
     */
    public String getPreviousSelectedServices() {
        return previousSelectedServices;
    }

    /**
     * @param previousSelectedServices the previousSelectedServices to set
     */
    public void setPreviousSelectedServices(String previousSelectedServices) {
        this.previousSelectedServices = previousSelectedServices;
    }

    /**
     * @return the sourceNameKey
     */
    public long getSourceNameKey() {
        return sourceNameKey;
    }

    /**
     * @param sourceNameKey the sourceNameKey to set
     */
    public void setSourceNameKey(long sourceNameKey) {
        this.sourceNameKey = sourceNameKey;
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

    /**
     * @return the documentTypeKey
     */
    public long getDocumentTypeKey() {
        return documentTypeKey;
    }

    /**
     * @param documentTypeKey the documentTypeKey to set
     */
    public void setDocumentTypeKey(long documentTypeKey) {
        this.documentTypeKey = documentTypeKey;
    }

    /**
     * @return the documentType
     */
    public String getDocumentType() {
        return documentType;
    }

    /**
     * @param documentType the documentType to set
     */
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    /**
     * @return the uploadFileName
     */
    public String getUploadFileName() {
        return uploadFileName;
    }

    /**
     * @param uploadFileName the uploadFileName to set
     */
    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    /**
     * @return the uploadFilePath
     */
    public String getUploadFilePath() {
        return uploadFilePath;
    }

    /**
     * @param uploadFilePath the uploadFilePath to set
     */
    public void setUploadFilePath(String uploadFilePath) {
        this.uploadFilePath = uploadFilePath;
    }

    /**
     * @return the uploadComments
     */
    public String getUploadComments() {
        return uploadComments;
    }

    /**
     * @param uploadComments the uploadComments to set
     */
    public void setUploadComments(String uploadComments) {
        this.uploadComments = uploadComments;
    }

    /**
     * @return the encEngagementCode
     */
    public String getEncEngagementCode() {
        return encEngagementCode;
    }

    /**
     * @param encEngagementCode the encEngagementCode to set
     */
    public void setEncEngagementCode(String encEngagementCode) {
        this.encEngagementCode = encEngagementCode;
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
     * @return the serviceLockFlag
     */
    public long getServiceLockFlag() {
        return serviceLockFlag;
    }

    /**
     * @param serviceLockFlag the serviceLockFlag to set
     */
    public void setServiceLockFlag(long serviceLockFlag) {
        this.serviceLockFlag = serviceLockFlag;
    }

    /**
     * @return the templateupload
     */
    public MultipartFile getTemplateupload() {
        return templateupload;
    }

    /**
     * @param templateupload the templateupload to set
     */
    public void setTemplateupload(MultipartFile templateupload) {
        this.templateupload = templateupload;
    }

    /**
     * @return the fileUploadID
     */
    public long getFileUploadID() {
        return fileUploadID;
    }

    /**
     * @param fileUploadID the fileUploadID to set
     */
    public void setFileUploadID(long fileUploadID) {
        this.fileUploadID = fileUploadID;
    }

    /**
     * @return the encfileUploadID
     */
    public String getEncfileUploadID() {
        return encfileUploadID;
    }

    /**
     * @param encfileUploadID the encfileUploadID to set
     */
    public void setEncfileUploadID(String encfileUploadID) {
        this.encfileUploadID = encfileUploadID;
    }

    /**
     * @return the lockCheckbox
     */
    public boolean isLockCheckbox() {
        return lockCheckbox;
    }

    /**
     * @param lockCheckbox the lockCheckbox to set
     */
    public void setLockCheckbox(boolean lockCheckbox) {
        this.lockCheckbox = lockCheckbox;
    }

    /**
     * @return the tempFilePath
     */
    public String getTempFilePath() {
        return tempFilePath;
    }

    /**
     * @param tempFilePath the tempFilePath to set
     */
    public void setTempFilePath(String tempFilePath) {
        this.tempFilePath = tempFilePath;
    }

    /**
     * @return the userTypeFlag
     */
    public String getUserTypeFlag() {
        return userTypeFlag;
    }

    /**
     * @param userTypeFlag the userTypeFlag to set
     */
    public void setUserTypeFlag(String userTypeFlag) {
        this.userTypeFlag = userTypeFlag;
    }

    /**
     * @return the fileStatusKey
     */
    public long getFileStatusKey() {
        return fileStatusKey;
    }

    /**
     * @param fileStatusKey the fileStatusKey to set
     */
    public void setFileStatusKey(long fileStatusKey) {
        this.fileStatusKey = fileStatusKey;
    }

    /**
     * @return the uploadedFilesCount
     */
    public String getUploadedFilesCount() {
        return uploadedFilesCount;
    }

    /**
     * @param uploadedFilesCount the uploadedFilesCount to set
     */
    public void setUploadedFilesCount(String uploadedFilesCount) {
        this.uploadedFilesCount = uploadedFilesCount;
    }

    /**
     * @return the encSecurityServiceCode
     */
    public String getEncSecurityServiceCode() {
        return encSecurityServiceCode;
    }

    /**
     * @param encSecurityServiceCode the encSecurityServiceCode to set
     */
    public void setEncSecurityServiceCode(String encSecurityServiceCode) {
        this.encSecurityServiceCode = encSecurityServiceCode;
    }

    /**
     * @return the encS
     */
    public String getEncS() {
        return encS;
    }

    /**
     * @param encS the encS to set
     */
    public void setEncS(String encS) {
        this.encS = encS;
    }

    /**
     * @return the encFileDownload
     */
    public String getEncFileDownload() {
        return encFileDownload;
    }

    /**
     * @param encFileDownload the encFileDownload to set
     */
    public void setEncFileDownload(String encFileDownload) {
        this.encFileDownload = encFileDownload;
    }

    /**
     * @return the uploadFileSize
     */
    public long getUploadFileSize() {
        return uploadFileSize;
    }

    /**
     * @param uploadFileSize the uploadFileSize to set
     */
    public void setUploadFileSize(long uploadFileSize) {
        this.uploadFileSize = uploadFileSize;
    }

    /**
     * @return the analystEmailID
     */
    public String getAnalystEmailID() {
        return analystEmailID;
    }

    /**
     * @param analystEmailID the analystEmailID to set
     */
    public void setAnalystEmailID(String analystEmailID) {
        this.analystEmailID = analystEmailID;
    }

    /**
     * @return the leadEmailID
     */
    public String getLeadEmailID() {
        return leadEmailID;
    }

    /**
     * @param leadEmailID the leadEmailID to set
     */
    public void setLeadEmailID(String leadEmailID) {
        this.leadEmailID = leadEmailID;
    }

    /**
     * @return the partnerUserValidMsg
     */
    public String getPartnerUserValidMsg() {
        return partnerUserValidMsg;
    }

    /**
     * @param partnerUserValidMsg the partnerUserValidMsg to set
     */
    public void setPartnerUserValidMsg(String partnerUserValidMsg) {
        this.partnerUserValidMsg = partnerUserValidMsg;
    }

    /**
     * @return the engagementLeadClientEmailId
     */
    public String getEngagementLeadClientEmailId() {
        return engagementLeadClientEmailId;
    }

    /**
     * @param engagementLeadClientEmailId the engagementLeadClientEmailId to set
     */
    public void setEngagementLeadClientEmailId(String engagementLeadClientEmailId) {
        this.engagementLeadClientEmailId = engagementLeadClientEmailId;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }
    
}