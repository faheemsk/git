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
public class RemediationDashboardDTO  extends RiskRegistryDashboardDTO{

    private int remediationPalnID;
    private String remediationPlanStatus;
    private String daysOpenRange;
    private int planCount;
    private String planOwner;
    private int planStatus_Open;
    private int planStatus_Inprogress;
    private int planStatus_RiskRegistered;
    private int planStatus_RiskAcccepted;
    private int planStatus_Closed;

    public int getRemediationPalnID() {
        return remediationPalnID;
    }

    public void setRemediationPalnID(int remediationPalnID) {
        this.remediationPalnID = remediationPalnID;
    }

    public String getRemediationPlanStatus() {
        return remediationPlanStatus;
    }

    public void setRemediationPlanStatus(String remediationPlanStatus) {
        this.remediationPlanStatus = remediationPlanStatus;
    }

    public String getDaysOpenRange() {
        return daysOpenRange;
    }

    public void setDaysOpenRange(String daysOpenRange) {
        this.daysOpenRange = daysOpenRange;
    }

    public int getPlanCount() {
        return planCount;
    }

    public void setPlanCount(int planCount) {
        this.planCount = planCount;
    }

    public String getPlanOwner() {
        return planOwner;
    }

    public void setPlanOwner(String planOwner) {
        this.planOwner = planOwner;
    }

    
    public int getPlanStatus_Open() {
        return planStatus_Open;
    }

    public void setPlanStatus_Open(int planStatus_Open) {
        this.planStatus_Open = planStatus_Open;
    }

    public int getPlanStatus_Inprogress() {
        return planStatus_Inprogress;
    }

    public void setPlanStatus_Inprogress(int planStatus_Inprogress) {
        this.planStatus_Inprogress = planStatus_Inprogress;
    }

    public int getPlanStatus_RiskRegistered() {
        return planStatus_RiskRegistered;
    }

    public void setPlanStatus_RiskRegistered(int planStatus_RiskRegistered) {
        this.planStatus_RiskRegistered = planStatus_RiskRegistered;
    }

    public int getPlanStatus_RiskAcccepted() {
        return planStatus_RiskAcccepted;
    }

    public void setPlanStatus_RiskAcccepted(int planStatus_RiskAcccepted) {
        this.planStatus_RiskAcccepted = planStatus_RiskAcccepted;
    }

    public int getPlanStatus_Closed() {
        return planStatus_Closed;
    }

    public void setPlanStatus_Closed(int planStatus_Closed) {
        this.planStatus_Closed = planStatus_Closed;
    }

}
