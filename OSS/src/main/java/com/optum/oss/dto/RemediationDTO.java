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
public class RemediationDTO extends VulnerabilityDTO implements Serializable{
    
        
    private String encRiskRegistryId;
    private String encPlanID;
    
    
    private String selSeverity;
    private String selSource;
    private String selService;
    private String selOsCategory;
    private String selVulCategory;
    private String selectedInstances;
    private String removeInstances;
    
    private String planID;
    private String planTitle;
    private String planStatus;
    private String notifyDate;
    private String daysOpen;
    private String planCloseDate;
    private String planDetails;
    private String startDate;
    private String endDate;
    private String findingsCount;
    private String completion;
    private String[] planNotes;
    
    private String planComment;
    private String commentDate;
    
    private String adjSeverityCode;
    private String adjSeverityValue;
    private String compensationCtrl;
    private String remediationOwnerID;
    private String remediationOwner;
    private String remediationOwnerDept;
    
    private String planSeverityValue;
    private String planSeverityCode;
    private String planAdjSeverityValue;
    private String planAdjSeverityCode;
    
    private String planRegKey;
    
    /**
     * @return the selSeverity
     */
    public String getSelSeverity() {
        return selSeverity;
    }

    /**
     * @param selSeverity the selSeverity to set
     */
    public void setSelSeverity(String selSeverity) {
        this.selSeverity = selSeverity;
    }

    /**
     * @return the selSource
     */
    public String getSelSource() {
        return selSource;
    }

    /**
     * @param selSource the selSource to set
     */
    public void setSelSource(String selSource) {
        this.selSource = selSource;
    }

    /**
     * @return the selService
     */
    public String getSelService() {
        return selService;
    }

    /**
     * @param selService the selService to set
     */
    public void setSelService(String selService) {
        this.selService = selService;
    }

    /**
     * @return the selOsCategory
     */
    public String getSelOsCategory() {
        return selOsCategory;
    }

    /**
     * @param selOsCategory the selOsCategory to set
     */
    public void setSelOsCategory(String selOsCategory) {
        this.selOsCategory = selOsCategory;
    }

    /**
     * @return the selVulCategory
     */
    public String getSelVulCategory() {
        return selVulCategory;
    }

    /**
     * @param selVulCategory the selVulCategory to set
     */
    public void setSelVulCategory(String selVulCategory) {
        this.selVulCategory = selVulCategory;
    }

    /**
     * @return the selectedInstances
     */
    public String getSelectedInstances() {
        return selectedInstances;
    }

    /**
     * @param selectedInstances the selectedInstances to set
     */
    public void setSelectedInstances(String selectedInstances) {
        this.selectedInstances = selectedInstances;
    }

    /**
     * @return the planTitle
     */
    public String getPlanTitle() {
        return planTitle;
    }

    /**
     * @param planTitle the planTitle to set
     */
    public void setPlanTitle(String planTitle) {
        this.planTitle = planTitle;
    }

    /**
     * @return the planStatus
     */
    public String getPlanStatus() {
        return planStatus;
    }

    /**
     * @param planStatus the planStatus to set
     */
    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    /**
     * @return the notifyDate
     */
    public String getNotifyDate() {
        return notifyDate;
    }

    /**
     * @param notifyDate the notifyDate to set
     */
    public void setNotifyDate(String notifyDate) {
        this.notifyDate = notifyDate;
    }

    /**
     * @return the daysOpen
     */
    public String getDaysOpen() {
        return daysOpen;
    }

    /**
     * @param daysOpen the daysOpen to set
     */
    public void setDaysOpen(String daysOpen) {
        this.daysOpen = daysOpen;
    }

    /**
     * @return the planCloseDate
     */
    public String getPlanCloseDate() {
        return planCloseDate;
    }

    /**
     * @param planCloseDate the planCloseDate to set
     */
    public void setPlanCloseDate(String planCloseDate) {
        this.planCloseDate = planCloseDate;
    }

    /**
     * @return the planDetails
     */
    public String getPlanDetails() {
        return planDetails;
    }

    /**
     * @param planDetails the planDetails to set
     */
    public void setPlanDetails(String planDetails) {
        this.planDetails = planDetails;
    }

    /**
     * @return the removeInstances
     */
    public String getRemoveInstances() {
        return removeInstances;
    }

    /**
     * @param removeInstances the removeInstances to set
     */
    public void setRemoveInstances(String removeInstances) {
        this.removeInstances = removeInstances;
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
     * @return the planID
     */
    public String getPlanID() {
        return planID;
    }

    /**
     * @param planID the planID to set
     */
    public void setPlanID(String planID) {
        this.planID = planID;
    }

    /**
     * @return the findingsCount
     */
    public String getFindingsCount() {
        return findingsCount;
    }

    /**
     * @param findingsCount the findingsCount to set
     */
    public void setFindingsCount(String findingsCount) {
        this.findingsCount = findingsCount;
    }

    /**
     * @return the completion
     */
    public String getCompletion() {
        return completion;
    }

    /**
     * @param completion the completion to set
     */
    public void setCompletion(String completion) {
        this.completion = completion;
    }

    /**
     * @return the planNotes
     */
    public String[] getPlanNotes() {
        return planNotes;
    }

    /**
     * @param planNotes the planNotes to set
     */
    public void setPlanNotes(String[] planNotes) {
        this.planNotes = planNotes;
    }

    /**
     * @return the planComment
     */
    public String getPlanComment() {
        return planComment;
    }

    /**
     * @param planComment the planComment to set
     */
    public void setPlanComment(String planComment) {
        this.planComment = planComment;
    }

    /**
     * @return the commentDate
     */
    public String getCommentDate() {
        return commentDate;
    }

    /**
     * @param commentDate the commentDate to set
     */
    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    /**
     * @return the adjSeverityCode
     */
    public String getAdjSeverityCode() {
        return adjSeverityCode;
    }

    /**
     * @param adjSeverityCode the adjSeverityCode to set
     */
    public void setAdjSeverityCode(String adjSeverityCode) {
        this.adjSeverityCode = adjSeverityCode;
    }

    /**
     * @return the adjSeverityValue
     */
    public String getAdjSeverityValue() {
        return adjSeverityValue;
    }

    /**
     * @param adjSeverityValue the adjSeverityValue to set
     */
    public void setAdjSeverityValue(String adjSeverityValue) {
        this.adjSeverityValue = adjSeverityValue;
    }

    /**
     * @return the compensationCtrl
     */
    public String getCompensationCtrl() {
        return compensationCtrl;
    }

    /**
     * @param compensationCtrl the compensationCtrl to set
     */
    public void setCompensationCtrl(String compensationCtrl) {
        this.compensationCtrl = compensationCtrl;
    }

    /**
     * @return the remediationOwnerID
     */
    public String getRemediationOwnerID() {
        return remediationOwnerID;
    }

    /**
     * @param remediationOwnerID the remediationOwnerID to set
     */
    public void setRemediationOwnerID(String remediationOwnerID) {
        this.remediationOwnerID = remediationOwnerID;
    }

    /**
     * @return the planRegKey
     */
    public String getPlanRegKey() {
        return planRegKey;
    }

    /**
     * @param planRegKey the planRegKey to set
     */
    public void setPlanRegKey(String planRegKey) {
        this.planRegKey = planRegKey;
    }

    /**
     * @return the encRiskRegistryId
     */
    public String getEncRiskRegistryId() {
        return encRiskRegistryId;
    }

    /**
     * @param encRiskRegistryId the encRiskRegistryId to set
     */
    public void setEncRiskRegistryId(String encRiskRegistryId) {
        this.encRiskRegistryId = encRiskRegistryId;
    }

    /**
     * @return the encPlanID
     */
    public String getEncPlanID() {
        return encPlanID;
    }

    /**
     * @param encPlanID the encPlanID to set
     */
    public void setEncPlanID(String encPlanID) {
        this.encPlanID = encPlanID;
    }

    /**
     * @return the remediationOwner
     */
    public String getRemediationOwner() {
        return remediationOwner;
    }

    /**
     * @param remediationOwner the remediationOwner to set
     */
    public void setRemediationOwner(String remediationOwner) {
        this.remediationOwner = remediationOwner;
    }

    /**
     * @return the remediationOwnerDept
     */
    public String getRemediationOwnerDept() {
        return remediationOwnerDept;
    }

    /**
     * @param remediationOwnerDept the remediationOwnerDept to set
     */
    public void setRemediationOwnerDept(String remediationOwnerDept) {
        this.remediationOwnerDept = remediationOwnerDept;
    }

    /**
     * @return the planSeverityValue
     */
    public String getPlanSeverityValue() {
        return planSeverityValue;
    }

    /**
     * @param planSeverityValue the planSeverityValue to set
     */
    public void setPlanSeverityValue(String planSeverityValue) {
        this.planSeverityValue = planSeverityValue;
    }

    /**
     * @return the planSeverityCode
     */
    public String getPlanSeverityCode() {
        return planSeverityCode;
    }

    /**
     * @param planSeverityCode the planSeverityCode to set
     */
    public void setPlanSeverityCode(String planSeverityCode) {
        this.planSeverityCode = planSeverityCode;
    }

    /**
     * @return the planAdjSeverityValue
     */
    public String getPlanAdjSeverityValue() {
        return planAdjSeverityValue;
    }

    /**
     * @param planAdjSeverityValue the planAdjSeverityValue to set
     */
    public void setPlanAdjSeverityValue(String planAdjSeverityValue) {
        this.planAdjSeverityValue = planAdjSeverityValue;
    }

    /**
     * @return the planAdjSeverityCode
     */
    public String getPlanAdjSeverityCode() {
        return planAdjSeverityCode;
    }

    /**
     * @param planAdjSeverityCode the planAdjSeverityCode to set
     */
    public void setPlanAdjSeverityCode(String planAdjSeverityCode) {
        this.planAdjSeverityCode = planAdjSeverityCode;
    }
    
    
}
