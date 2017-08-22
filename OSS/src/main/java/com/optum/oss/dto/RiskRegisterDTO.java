/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dto;

/**
 *
 * @author rpanuganti
 */
public class RiskRegisterDTO extends RemediationDTO {

    private int riskRegisterID;
    private String adjustedSeverity;
    private String compensationControl;
    private String riskResponse;
    private String riskResponseCode;
    private String riskResponseJustification;
    private String acceptedBy;
    private String acceptedDate;
    private String[] riskItemIds;
    private String orgSchema;
    private int riskRegistryOwnerID;
    private String riskRegistryOwner;
    private String registryOwnerDept;
    private String registryNotifyDate;
    
    /**
     * @return the riskRegisterID
     */
    public int getRiskRegisterID() {
        return riskRegisterID;
    }

    /**
     * @param riskRegisterID the riskRegisterID to set
     */
    public void setRiskRegisterID(int riskRegisterID) {
        this.riskRegisterID = riskRegisterID;
    }

    /**
     * @return the adjustedSeverity
     */
    public String getAdjustedSeverity() {
        return adjustedSeverity;
    }

    /**
     * @param adjustedSeverity the adjustedSeverity to set
     */
    public void setAdjustedSeverity(String adjustedSeverity) {
        this.adjustedSeverity = adjustedSeverity;
    }

    /**
     * @return the compensationControl
     */
    public String getCompensationControl() {
        return compensationControl;
    }

    /**
     * @param compensationControl the compensationControl to set
     */
    public void setCompensationControl(String compensationControl) {
        this.compensationControl = compensationControl;
    }

    /**
     * @return the riskResponse
     */
    public String getRiskResponse() {
        return riskResponse;
    }

    /**
     * @param riskResponse the riskResponse to set
     */
    public void setRiskResponse(String riskResponse) {
        this.riskResponse = riskResponse;
    }

    /**
     * @return the acceptedBy
     */
    public String getAcceptedBy() {
        return acceptedBy;
    }

    /**
     * @param acceptedBy the acceptedBy to set
     */
    public void setAcceptedBy(String acceptedBy) {
        this.acceptedBy = acceptedBy;
    }

    /**
     * @return the riskItemIds
     */
    public String[] getRiskItemIds() {
        return riskItemIds;
    }

    /**
     * @param riskItemIds the riskItemIds to set
     */
    public void setRiskItemIds(String[] riskItemIds) {
        this.riskItemIds = riskItemIds;
    }

    public String getRiskResponseCode() {
        return riskResponseCode;
    }

    public void setRiskResponseCode(String riskResponseCode) {
        this.riskResponseCode = riskResponseCode;
    }

    public String getRiskResponseJustification() {
        return riskResponseJustification;
    }

    public void setRiskResponseJustification(String riskResponseJustification) {
        this.riskResponseJustification = riskResponseJustification;
    }

    public String getAcceptedDate() {
        return acceptedDate;
    }

    public void setAcceptedDate(String acceptedDate) {
        this.acceptedDate = acceptedDate;
    }

    public int getRiskRegistryOwnerID() {
        return riskRegistryOwnerID;
    }

    public void setRiskRegistryOwnerID(int riskRegistryOwnerID) {
        this.riskRegistryOwnerID = riskRegistryOwnerID;
    }

    public String getRiskRegistryOwner() {
        return riskRegistryOwner;
    }

    public void setRiskRegistryOwner(String riskRegistryOwner) {
        this.riskRegistryOwner = riskRegistryOwner;
    }

    public String getRegistryOwnerDept() {
        return registryOwnerDept;
    }

    public void setRegistryOwnerDept(String registryOwnerDept) {
        this.registryOwnerDept = registryOwnerDept;
    }

    public String getRegistryNotifyDate() {
        return registryNotifyDate;
    }

    public void setRegistryNotifyDate(String registryNotifyDate) {
        this.registryNotifyDate = registryNotifyDate;
    }

    
}
