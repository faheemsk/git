/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.dto;

import java.io.Serializable;

/**
 *
 * @author hpasupuleti
 */
public class CVSSMetricsDTO implements Serializable{
 
    private String ConfidentialityImpact;
    private String IntegrityImpact;
    private String AvailabilityImpact;
    private String AccessVector;
    private String AccessComplexity;
    private String Authentication;
    private String RemediationLevel;
    private String Exploitability;
    private String ReportConfidence;
    private String ConfidentialityRequirement;
    private String IntegrityRequirement;
    private String AvailabilityRequirement;
    private String TargetDistribution;
    private String CollateralDamagePotential;
    
    private String BaseScore;
    private String TemporalScore;
    private String EnvironmentalScore;
    private String CVSSVector;
    /**
     * @return the ConfidentialityImpact
     */
    public String getConfidentialityImpact() {
        return ConfidentialityImpact;
    }

    /**
     * @param ConfidentialityImpact the ConfidentialityImpact to set
     */
    public void setConfidentialityImpact(String ConfidentialityImpact) {
        this.ConfidentialityImpact = ConfidentialityImpact;
    }

    /**
     * @return the IntegrityImpact
     */
    public String getIntegrityImpact() {
        return IntegrityImpact;
    }

    /**
     * @param IntegrityImpact the IntegrityImpact to set
     */
    public void setIntegrityImpact(String IntegrityImpact) {
        this.IntegrityImpact = IntegrityImpact;
    }

    /**
     * @return the AvailabilityImpact
     */
    public String getAvailabilityImpact() {
        return AvailabilityImpact;
    }

    /**
     * @param AvailabilityImpact the AvailabilityImpact to set
     */
    public void setAvailabilityImpact(String AvailabilityImpact) {
        this.AvailabilityImpact = AvailabilityImpact;
    }

    /**
     * @return the AccessVector
     */
    public String getAccessVector() {
        return AccessVector;
    }

    /**
     * @param AccessVector the AccessVector to set
     */
    public void setAccessVector(String AccessVector) {
        this.AccessVector = AccessVector;
    }

    /**
     * @return the AccessComplexity
     */
    public String getAccessComplexity() {
        return AccessComplexity;
    }

    /**
     * @param AccessComplexity the AccessComplexity to set
     */
    public void setAccessComplexity(String AccessComplexity) {
        this.AccessComplexity = AccessComplexity;
    }

    /**
     * @return the Authentication
     */
    public String getAuthentication() {
        return Authentication;
    }

    /**
     * @param Authentication the Authentication to set
     */
    public void setAuthentication(String Authentication) {
        this.Authentication = Authentication;
    }

    /**
     * @return the RemediationLevel
     */
    public String getRemediationLevel() {
        return RemediationLevel;
    }

    /**
     * @param RemediationLevel the RemediationLevel to set
     */
    public void setRemediationLevel(String RemediationLevel) {
        this.RemediationLevel = RemediationLevel;
    }

    /**
     * @return the Exploitability
     */
    public String getExploitability() {
        return Exploitability;
    }

    /**
     * @param Exploitability the Exploitability to set
     */
    public void setExploitability(String Exploitability) {
        this.Exploitability = Exploitability;
    }

    /**
     * @return the ReportConfidence
     */
    public String getReportConfidence() {
        return ReportConfidence;
    }

    /**
     * @param ReportConfidence the ReportConfidence to set
     */
    public void setReportConfidence(String ReportConfidence) {
        this.ReportConfidence = ReportConfidence;
    }

    /**
     * @return the ConfidentialityRequirement
     */
    public String getConfidentialityRequirement() {
        return ConfidentialityRequirement;
    }

    /**
     * @param ConfidentialityRequirement the ConfidentialityRequirement to set
     */
    public void setConfidentialityRequirement(String ConfidentialityRequirement) {
        this.ConfidentialityRequirement = ConfidentialityRequirement;
    }

    /**
     * @return the IntegrityRequirement
     */
    public String getIntegrityRequirement() {
        return IntegrityRequirement;
    }

    /**
     * @param IntegrityRequirement the IntegrityRequirement to set
     */
    public void setIntegrityRequirement(String IntegrityRequirement) {
        this.IntegrityRequirement = IntegrityRequirement;
    }

    /**
     * @return the AvailabilityRequirement
     */
    public String getAvailabilityRequirement() {
        return AvailabilityRequirement;
    }

    /**
     * @param AvailabilityRequirement the AvailabilityRequirement to set
     */
    public void setAvailabilityRequirement(String AvailabilityRequirement) {
        this.AvailabilityRequirement = AvailabilityRequirement;
    }

    /**
     * @return the TargetDistribution
     */
    public String getTargetDistribution() {
        return TargetDistribution;
    }

    /**
     * @param TargetDistribution the TargetDistribution to set
     */
    public void setTargetDistribution(String TargetDistribution) {
        this.TargetDistribution = TargetDistribution;
    }

    /**
     * @return the CollateralDamagePotential
     */
    public String getCollateralDamagePotential() {
        return CollateralDamagePotential;
    }

    /**
     * @param CollateralDamagePotential the CollateralDamagePotential to set
     */
    public void setCollateralDamagePotential(String CollateralDamagePotential) {
        this.CollateralDamagePotential = CollateralDamagePotential;
    }

    /**
     * @return the BaseScore
     */
    public String getBaseScore() {
        return BaseScore;
    }

    /**
     * @param BaseScore the BaseScore to set
     */
    public void setBaseScore(String BaseScore) {
        this.BaseScore = BaseScore;
    }

    /**
     * @return the TemporalScore
     */
    public String getTemporalScore() {
        return TemporalScore;
    }

    /**
     * @param TemporalScore the TemporalScore to set
     */
    public void setTemporalScore(String TemporalScore) {
        this.TemporalScore = TemporalScore;
    }

    /**
     * @return the EnvironmentalScore
     */
    public String getEnvironmentalScore() {
        return EnvironmentalScore;
    }

    /**
     * @param EnvironmentalScore the EnvironmentalScore to set
     */
    public void setEnvironmentalScore(String EnvironmentalScore) {
        this.EnvironmentalScore = EnvironmentalScore;
    }

    /**
     * @return the CVSSVector
     */
    public String getCVSSVector() {
        return CVSSVector;
    }

    /**
     * @param CVSSVector the CVSSVector to set
     */
    public void setCVSSVector(String CVSSVector) {
        this.CVSSVector = CVSSVector;
    }
    
    
}
