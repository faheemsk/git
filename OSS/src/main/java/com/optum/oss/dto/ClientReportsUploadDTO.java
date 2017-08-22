/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dto;

import java.io.Serializable;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author mrejeti
 */
public class ClientReportsUploadDTO extends BaseDTO implements Serializable {

    private long reportkey;
    private long organizationKey;
    private String organizationName;
    private String encSecureServiceCode;
    private String clientEngagementCode;
    private String encClientEngagementCode;
    private String encSecureServiceName;
    private String clientEngagementName;
    private String reportName;
    private String fileName;
    private String encFileName;
    private String filePath;
    private String encFilePath;
    private String updateDate;
    private String status;
    private MultipartFile templateupload;
    private long uploadFileKey;
    private String encUploadFileKey;
    private String[] reportNameCheckId;
    private String redirectFlag;
    private String serverSideFlag;
    private long fileSize;
    private int fileStatusKey;
    /**
     * @return the reportkey
     */
    public long getReportkey() {
        return reportkey;
    }

    /**
     * @param reportkey the reportkey to set
     */
    public void setReportkey(long reportkey) {
        this.reportkey = reportkey;
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
     * @return the encSecureServiceCode
     */
    public String getEncSecureServiceCode() {
        return encSecureServiceCode;
    }

    /**
     * @param encSecureServiceCode the encSecureServiceCode to set
     */
    public void setEncSecureServiceCode(String encSecureServiceCode) {
        this.encSecureServiceCode = encSecureServiceCode;
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
     * @return the encSecureServiceName
     */
    public String getEncSecureServiceName() {
        return encSecureServiceName;
    }

    /**
     * @param encSecureServiceName the encSecureServiceName to set
     */
    public void setEncSecureServiceName(String encSecureServiceName) {
        this.encSecureServiceName = encSecureServiceName;
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
     * @return the reportName
     */
    public String getReportName() {
        return reportName;
    }

    /**
     * @param reportName the reportName to set
     */
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the encFileName
     */
    public String getEncFileName() {
        return encFileName;
    }

    /**
     * @param encFileName the encFileName to set
     */
    public void setEncFileName(String encFileName) {
        this.encFileName = encFileName;
    }

    /**
     * @return the filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath the filePath to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return the encFilePath
     */
    public String getEncFilePath() {
        return encFilePath;
    }

    /**
     * @param encFilePath the encFilePath to set
     */
    public void setEncFilePath(String encFilePath) {
        this.encFilePath = encFilePath;
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
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
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
     * @return the uploadFileKey
     */
    public long getUploadFileKey() {
        return uploadFileKey;
    }

    /**
     * @param uploadFileKey the uploadFileKey to set
     */
    public void setUploadFileKey(long uploadFileKey) {
        this.uploadFileKey = uploadFileKey;
    }

    /**
     * @return the encUploadFileKey
     */
    public String getEncUploadFileKey() {
        return encUploadFileKey;
    }

    /**
     * @param encUploadFileKey the encUploadFileKey to set
     */
    public void setEncUploadFileKey(String encUploadFileKey) {
        this.encUploadFileKey = encUploadFileKey;
    }

    /**
     * @return the ReportNameCheckId
     */
    public String[] getReportNameCheckId() {
        return reportNameCheckId;
    }

    /**
     * @param ReportNameCheckId the ReportNameCheckId to set
     */
    public void setReportNameCheckId(String[] reportNameCheckId) {
        this.reportNameCheckId = reportNameCheckId;
    }

    /**
     * @return the encClientEngagementCode
     */
    public String getEncClientEngagementCode() {
        return encClientEngagementCode;
    }

    /**
     * @param encClientEngagementCode the encClientEngagementCode to set
     */
    public void setEncClientEngagementCode(String encClientEngagementCode) {
        this.encClientEngagementCode = encClientEngagementCode;
    }

    /**
     * @return the redirectFlag
     */
    public String getRedirectFlag() {
        return redirectFlag;
    }

    /**
     * @param redirectFlag the redirectFlag to set
     */
    public void setRedirectFlag(String redirectFlag) {
        this.redirectFlag = redirectFlag;
    }

    public String getServerSideFlag() {
        return serverSideFlag;
    }

    public void setServerSideFlag(String serverSideFlag) {
        this.serverSideFlag = serverSideFlag;
    }
     public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * @return the fileStatusKey
     */
    public int getFileStatusKey() {
        return fileStatusKey;
    }

    /**
     * @param fileStatusKey the fileStatusKey to set
     */
    public void setFileStatusKey(int fileStatusKey) {
        this.fileStatusKey = fileStatusKey;
    }
    
}
