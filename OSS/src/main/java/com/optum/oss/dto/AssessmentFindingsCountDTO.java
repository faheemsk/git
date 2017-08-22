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
public class AssessmentFindingsCountDTO {

    private long totalCount;
    private long criticalCount;
    private long highCount;
    private long mediumCount;
    private long lowCount;
    private long riskRegistryCount;
    private long remediationPlanCount;
    
    
    public AssessmentFindingsCountDTO(){
        
    }

    public AssessmentFindingsCountDTO(long totalCount, long criticalCount, long highCount, long mediumCount, long lowCount) {
        this.totalCount = totalCount;
        this.criticalCount = criticalCount;
        this.highCount = highCount;
        this.mediumCount = mediumCount;
        this.lowCount = lowCount;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getCriticalCount() {
        return criticalCount;
    }

    public void setCriticalCount(long criticalCount) {
        this.criticalCount = criticalCount;
    }

    public long getHighCount() {
        return highCount;
    }

    public void setHighCount(long highCount) {
        this.highCount = highCount;
    }

    public long getMediumCount() {
        return mediumCount;
    }

    public void setMediumCount(long mediumCount) {
        this.mediumCount = mediumCount;
    }

    public long getLowCount() {
        return lowCount;
    }

    public void setLowCount(long lowCount) {
        this.lowCount = lowCount;
    }

    public long getRiskRegistryCount() {
        return riskRegistryCount;
    }

    public void setRiskRegistryCount(long riskRegistryCount) {
        this.riskRegistryCount = riskRegistryCount;
    }

    public long getRemediationPlanCount() {
        return remediationPlanCount;
    }

    public void setRemediationPlanCount(long remediationPlanCount) {
        this.remediationPlanCount = remediationPlanCount;
    }


}
