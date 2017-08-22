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
public class RiskRegistryDashboardDTO {

    private int registry_Open;
    private int registry_Closed;
    private int registryCount;
    private String registryOwner;
    private String riskResponse;
    private int riskResponseCount_Accept;
    private int riskResponseCount_Transfer;
    private int riskResponseCount_mitigated;
    private int riskResponseCount_share;
    private int riskResponseCount_avoid;

    private String riskRegistrySeverity;
    private int riskSeverityCount_Critical;
    private int riskSeverityCount_High;
    private int riskSeverityCount_Medium;
    private int riskSeverityCount_Low;

    private String riskRegisterId;
    private String planTitle;
    private String PlanStatus;
    private String daysOpen;
    private String instances;
    private String completion;
    
    private int openCount;
    private int inprogressCount;
    private int closedCount;

    public int getRegistry_Open() {
        return registry_Open;
    }

    public void setRegistry_Open(int registry_Open) {
        this.registry_Open = registry_Open;
    }

    public int getRegistry_Closed() {
        return registry_Closed;
    }

    public void setRegistry_Closed(int registry_Closed) {
        this.registry_Closed = registry_Closed;
    }

    public int getRegistryCount() {
        return registryCount;
    }

    public void setRegistryCount(int registryCount) {
        this.registryCount = registryCount;
    }

    public String getRegistryOwner() {
        return registryOwner;
    }

    public void setRegistryOwner(String registryOwner) {
        this.registryOwner = registryOwner;
    }

    public String getRiskResponse() {
        return riskResponse;
    }

    public void setRiskResponse(String riskResponse) {
        this.riskResponse = riskResponse;
    }

    public int getRiskResponseCount_Accept() {
        return riskResponseCount_Accept;
    }

    public void setRiskResponseCount_Accept(int riskResponseCount_Accept) {
        this.riskResponseCount_Accept = riskResponseCount_Accept;
    }

    public int getRiskResponseCount_Transfer() {
        return riskResponseCount_Transfer;
    }

    public void setRiskResponseCount_Transfer(int riskResponseCount_Transfer) {
        this.riskResponseCount_Transfer = riskResponseCount_Transfer;
    }

    public int getRiskResponseCount_mitigated() {
        return riskResponseCount_mitigated;
    }

    public void setRiskResponseCount_mitigated(int riskResponseCount_mitigated) {
        this.riskResponseCount_mitigated = riskResponseCount_mitigated;
    }

    public int getRiskResponseCount_share() {
        return riskResponseCount_share;
    }

    public void setRiskResponseCount_share(int riskResponseCount_share) {
        this.riskResponseCount_share = riskResponseCount_share;
    }

    public int getRiskResponseCount_avoid() {
        return riskResponseCount_avoid;
    }

    public void setRiskResponseCount_avoid(int riskResponseCount_avoid) {
        this.riskResponseCount_avoid = riskResponseCount_avoid;
    }

    public String getRiskRegistrySeverity() {
        return riskRegistrySeverity;
    }

    public void setRiskRegistrySeverity(String riskRegistrySeverity) {
        this.riskRegistrySeverity = riskRegistrySeverity;
    }

    public int getRiskSeverityCount_Critical() {
        return riskSeverityCount_Critical;
    }

    public void setRiskSeverityCount_Critical(int riskSeverityCount_Critical) {
        this.riskSeverityCount_Critical = riskSeverityCount_Critical;
    }

    public int getRiskSeverityCount_High() {
        return riskSeverityCount_High;
    }

    public void setRiskSeverityCount_High(int riskSeverityCount_High) {
        this.riskSeverityCount_High = riskSeverityCount_High;
    }

    public int getRiskSeverityCount_Medium() {
        return riskSeverityCount_Medium;
    }

    public void setRiskSeverityCount_Medium(int riskSeverityCount_Medium) {
        this.riskSeverityCount_Medium = riskSeverityCount_Medium;
    }

    public int getRiskSeverityCount_Low() {
        return riskSeverityCount_Low;
    }

    public void setRiskSeverityCount_Low(int riskSeverityCount_Low) {
        this.riskSeverityCount_Low = riskSeverityCount_Low;
    }


    public String getPlanTitle() {
        return planTitle;
    }

    public void setPlanTitle(String planTitle) {
        this.planTitle = planTitle;
    }

    public String getPlanStatus() {
        return PlanStatus;
    }

    public void setPlanStatus(String PlanStatus) {
        this.PlanStatus = PlanStatus;
    }

    public String getDaysOpen() {
        return daysOpen;
    }

    public void setDaysOpen(String daysOpen) {
        this.daysOpen = daysOpen;
    }

    public String getRiskRegisterId() {
        return riskRegisterId;
    }

    public void setRiskRegisterId(String riskRegisterId) {
        this.riskRegisterId = riskRegisterId;
    }

    public String getInstances() {
        return instances;
    }

    public void setInstances(String instances) {
        this.instances = instances;
    }

    public String getCompletion() {
        return completion;
    }

    public void setCompletion(String completion) {
        this.completion = completion;
    }

    public int getOpenCount() {
        return openCount;
    }

    public void setOpenCount(int openCount) {
        this.openCount = openCount;
    }

    public int getInprogressCount() {
        return inprogressCount;
    }

    public void setInprogressCount(int inprogressCount) {
        this.inprogressCount = inprogressCount;
    }

    public int getClosedCount() {
        return closedCount;
    }

    public void setClosedCount(int closedCount) {
        this.closedCount = closedCount;
    }
    
    

}
