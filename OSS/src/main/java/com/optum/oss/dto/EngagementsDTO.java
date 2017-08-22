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
public class EngagementsDTO {

    private String engagementKey;
    private String engagmentName;
    private String engagementPkgType;
    private String engagementPkgName;
    private long engStatusKey;
    private String engStatusValue;
    private String clientName;
    private String agreementDate;
    
    public EngagementsDTO() {
    }
    
    
    public String getEngagementPkgName() {
        return engagementPkgName;
    }

    public void setEngagementPkgName(String engagementPkgName) {
        this.engagementPkgName = engagementPkgName;
    }

    public String getEngagementKey() {
        return engagementKey;
    }

    public void setEngagementKey(String engagementKey) {
        this.engagementKey = engagementKey;
    }

    public String getEngagmentName() {
        return engagmentName;
    }

    public void setEngagmentName(String engagmentName) {
        this.engagmentName = engagmentName;
    }

    public String getEngagementPkgType() {
        return engagementPkgType;
    }

    public void setEngagementPkgType(String engagementPkgType) {
        this.engagementPkgType = engagementPkgType;
    }

    public EngagementsDTO(String engagementKey, String engagmentName, String engagementPkgType, String engagementPkgName) {
        this.engagementKey = engagementKey;
        this.engagmentName = engagmentName;
        this.engagementPkgType = engagementPkgType;
        this.engagementPkgName = engagementPkgName;
    }

    public long getEngStatusKey() {
        return engStatusKey;
    }

    public void setEngStatusKey(long engStatusKey) {
        this.engStatusKey = engStatusKey;
    }

    public String getEngStatusValue() {
        return engStatusValue;
    }

    public void setEngStatusValue(String engStatusValue) {
        this.engStatusValue = engStatusValue;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getAgreementDate() {
        return agreementDate;
    }

    public void setAgreementDate(String agreementDate) {
        this.agreementDate = agreementDate;
    }

}
