/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.model;

/**
 *
 * @author spatepuram
 */
//@Component
public class ChartModel {
    private String serviceName;
    private String critical;
    private String medium;
    private String high;
    private String low;
    private String severityLevel;
    private String impactXScore;
    private String probYScore;
    private String findingId;
    private String ellipsedFindingId;
    private String totalFindings;
    private String vulnCatyCD;

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
     * @return the critical
     */
    public String getCritical() {
        return critical;
    }

    /**
     * @param critical the critical to set
     */
    public void setCritical(String critical) {
        this.critical = critical;
    }

    /**
     * @return the medium
     */
    public String getMedium() {
        return medium;
    }

    /**
     * @param medium the medium to set
     */
    public void setMedium(String medium) {
        this.medium = medium;
    }

    /**
     * @return the high
     */
    public String getHigh() {
        return high;
    }

    /**
     * @param high the high to set
     */
    public void setHigh(String high) {
        this.high = high;
    }

    /**
     * @return the low
     */
    public String getLow() {
        return low;
    }

    /**
     * @param low the low to set
     */
    public void setLow(String low) {
        this.low = low;
    }

    /**
     * @return the severityLevel
     */
    public String getSeverityLevel() {
        return severityLevel;
    }

    /**
     * @param severityLevel the severityLevel to set
     */
    public void setSeverityLevel(String severityLevel) {
        this.severityLevel = severityLevel;
    }

    /**
     * @return the impactXScore
     */
    public String getImpactXScore() {
        return impactXScore;
    }

    /**
     * @param impactXScore the impactXScore to set
     */
    public void setImpactXScore(String impactXScore) {
        this.impactXScore = impactXScore;
    }

    /**
     * @return the probYScore
     */
    public String getProbYScore() {
        return probYScore;
    }

    /**
     * @param probYScore the probYScore to set
     */
    public void setProbYScore(String probYScore) {
        this.probYScore = probYScore;
    }

    /**
     * @return the findingId
     */
    public String getFindingId() {
        return findingId;
    }

    /**
     * @param findingId the findingId to set
     */
    public void setFindingId(String findingId) {
        this.findingId = findingId;
    }

    /**
     * @return the totalFindings
     */
    public String getTotalFindings() {
        return totalFindings;
    }

    /**
     * @param totalFindings the totalFindings to set
     */
    public void setTotalFindings(String totalFindings) {
        this.totalFindings = totalFindings;
    }

    public String getEllipsedFindingId() {
        return ellipsedFindingId;
    }

    public void setEllipsedFindingId(String ellipsedFindingId) {
        this.ellipsedFindingId = ellipsedFindingId;
    }

    public String getVulnCatyCD() {
        return vulnCatyCD;
    }

    public void setVulnCatyCD(String vulnCatyCD) {
        this.vulnCatyCD = vulnCatyCD;
    }
    
    
}
