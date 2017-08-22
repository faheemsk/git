/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.model;

/**
 *
 * @author sathuluri
 */
public class RemediationChartModel {

    private String serviceName;
    private String criticalCount;
    private String highCount;
    private String mediumCount;
    private String lowCount;
    private String criticalOpenCount;
    private String highOpenCount;
    private String mediumOpenCount;
    private String lowOpenCount;
    private String remediationChartText;

    public RemediationChartModel(String serviceName, String criticalCount, String highCount, String mediumCount, String lowCount, String criticalOpenCount, String highOpenCount, String mediumOpenCount, String lowOpenCount) {
        this.serviceName = serviceName;
        this.criticalCount = criticalCount;
        this.highCount = highCount;
        this.mediumCount = mediumCount;
        this.lowCount = lowCount;
        this.criticalOpenCount = criticalOpenCount;
        this.highOpenCount = highOpenCount;
        this.mediumOpenCount = mediumOpenCount;
        this.lowOpenCount = lowOpenCount;
    }

    public RemediationChartModel() {

    }

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
     * @return the criticalCount
     */
    public String getCriticalCount() {
        return criticalCount;
    }

    /**
     * @param criticalCount the criticalCount to set
     */
    public void setCriticalCount(String criticalCount) {
        this.criticalCount = criticalCount;
    }

    /**
     * @return the highCount
     */
    public String getHighCount() {
        return highCount;
    }

    /**
     * @param highCount the highCount to set
     */
    public void setHighCount(String highCount) {
        this.highCount = highCount;
    }

    /**
     * @return the mediumCount
     */
    public String getMediumCount() {
        return mediumCount;
    }

    /**
     * @param mediumCount the mediumCount to set
     */
    public void setMediumCount(String mediumCount) {
        this.mediumCount = mediumCount;
    }

    /**
     * @return the lowCount
     */
    public String getLowCount() {
        return lowCount;
    }

    /**
     * @param lowCount the lowCount to set
     */
    public void setLowCount(String lowCount) {
        this.lowCount = lowCount;
    }

    /**
     * @return the criticalOpenCount
     */
    public String getCriticalOpenCount() {
        return criticalOpenCount;
    }

    /**
     * @param criticalOpenCount the criticalOpenCount to set
     */
    public void setCriticalOpenCount(String criticalOpenCount) {
        this.criticalOpenCount = criticalOpenCount;
    }

    /**
     * @return the highOpenCount
     */
    public String getHighOpenCount() {
        return highOpenCount;
    }

    /**
     * @param highOpenCount the highOpenCount to set
     */
    public void setHighOpenCount(String highOpenCount) {
        this.highOpenCount = highOpenCount;
    }

    /**
     * @return the mediumOpenCount
     */
    public String getMediumOpenCount() {
        return mediumOpenCount;
    }

    /**
     * @param mediumOpenCount the mediumOpenCount to set
     */
    public void setMediumOpenCount(String mediumOpenCount) {
        this.mediumOpenCount = mediumOpenCount;
    }

    /**
     * @return the lowOpenCount
     */
    public String getLowOpenCount() {
        return lowOpenCount;
    }

    /**
     * @param lowOpenCount the lowOpenCount to set
     */
    public void setLowOpenCount(String lowOpenCount) {
        this.lowOpenCount = lowOpenCount;
    }

    /**
     * @return the remediationChartText
     */
    public String getRemediationChartText() {
        return remediationChartText;
    }

    /**
     * @param remediationChartText the remediationChartText to set
     */
    public void setRemediationChartText(String remediationChartText) {
        this.remediationChartText = remediationChartText;
    }
}
