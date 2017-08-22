/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.dto;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sbhagavatula
 */
public class CVEInformationDTO extends BaseDTO implements Serializable{
    
    private String cveIdentifier;
    @JsonView(Views.Public.class)
    private String cveDesc;
    private BigDecimal srcBaseScore;//Source Vulnerability Base Score
    private BigDecimal srctemporalScore;//Source Vulnerability Base Score
    private BigDecimal baseScore;
    private BigDecimal impSubScore;
    private BigDecimal expSubScore;
    private BigDecimal temporalScore;
    private BigDecimal environmentalScore;
    private BigDecimal overallScore;
    @JsonView(Views.Public.class)
    private String cveID;
    @JsonView(Views.Public.class)
    private String updatedDate;
    private List<ComplianceInfoDTO> complianceList = new ArrayList<>();
    private Map<String, LinkedHashMap<String, ArrayList<ComplianceInfoDTO>>> totalComplianceMap = new LinkedHashMap<>();
    private Map<String, ArrayList<ComplianceInfoDTO>> complianceMap = new LinkedHashMap<>();
    
    private String severityCode;
    private String severityName;
    private String impactCode;
    private String impactName;
    private String probabilityCode;
    private String probabilityName;
    private String costEffortCode;
    private String costEffortName;
    private String vectorText;
    private String cweID;
    private Map<String, ArrayList<ComplianceInfoDTO>> hitrustCrossWalkMap = new LinkedHashMap<>();
    
    @JsonView(Views.Public.class)
    private int totalCount;

    /**
     * @return the cveIdentifier
     */
    public String getCveIdentifier() {
        return cveIdentifier;
    }

    /**
     * @param cveIdentifier the cveIdentifier to set
     */
    public void setCveIdentifier(String cveIdentifier) {
        this.cveIdentifier = cveIdentifier;
    }

    /**
     * @return the cveDesc
     */
    public String getCveDesc() {
        return cveDesc;
    }

    /**
     * @param cveDesc the cveDesc to set
     */
    public void setCveDesc(String cveDesc) {
        this.cveDesc = cveDesc;
    }
    /**
     * @return the temporalScore
     */
    public BigDecimal getTemporalScore() {
        return temporalScore;
    }

    /**
     * @param temporalScore the temporalScore to set
     */
    public void setTemporalScore(BigDecimal temporalScore) {
        this.temporalScore = temporalScore;
    }

    /**
     * @return the environmentalScore
     */
    public BigDecimal getEnvironmentalScore() {
        return environmentalScore;
    }

    /**
     * @param environmentalScore the environmentalScore to set
     */
    public void setEnvironmentalScore(BigDecimal environmentalScore) {
        this.environmentalScore = environmentalScore;
    }

    /**
     * @return the overallScore
     */
    public BigDecimal getOverallScore() {
        return overallScore;
    }

    /**
     * @param overallScore the overallScore to set
     */
    public void setOverallScore(BigDecimal overallScore) {
        this.overallScore = overallScore;
    }


    public String getCveID() {
        return cveID;
    }

    public void setCveID(String cveID) {
        this.cveID = cveID;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<ComplianceInfoDTO> getComplianceList() {
        return complianceList;
    }

    public void setComplianceList(List<ComplianceInfoDTO> complianceList) {
        this.complianceList = complianceList;
    }

    public Map<String, LinkedHashMap<String, ArrayList<ComplianceInfoDTO>>> getTotalComplianceMap() {
        return totalComplianceMap;
    }

    public void setTotalComplianceMap(Map<String, LinkedHashMap<String, ArrayList<ComplianceInfoDTO>>> totalComplianceMap) {
        this.totalComplianceMap = totalComplianceMap;
    }

    public Map<String, ArrayList<ComplianceInfoDTO>> getComplianceMap() {
        return complianceMap;
    }

    public void setComplianceMap(Map<String, ArrayList<ComplianceInfoDTO>> complianceMap) {
        this.complianceMap = complianceMap;
    }

    /**
     * @return the impSubScore
     */
    public BigDecimal getImpSubScore() {
        return impSubScore;
    }

    /**
     * @param impSubScore the impSubScore to set
     */
    public void setImpSubScore(BigDecimal impSubScore) {
        this.impSubScore = impSubScore;
    }

    /**
     * @return the expSubScore
     */
    public BigDecimal getExpSubScore() {
        return expSubScore;
    }

    /**
     * @param expSubScore the expSubScore to set
     */
    public void setExpSubScore(BigDecimal expSubScore) {
        this.expSubScore = expSubScore;
    }

    /**
     * @return the severityCode
     */
    public String getSeverityCode() {
        return severityCode;
    }

    /**
     * @param severityCode the severityCode to set
     */
    public void setSeverityCode(String severityCode) {
        this.severityCode = severityCode;
    }

    /**
     * @return the severityName
     */
    public String getSeverityName() {
        return severityName;
    }

    /**
     * @param severityName the severityName to set
     */
    public void setSeverityName(String severityName) {
        this.severityName = severityName;
    }

    /**
     * @return the impactCode
     */
    public String getImpactCode() {
        return impactCode;
    }

    /**
     * @param impactCode the impactCode to set
     */
    public void setImpactCode(String impactCode) {
        this.impactCode = impactCode;
    }

    /**
     * @return the impactName
     */
    public String getImpactName() {
        return impactName;
    }

    /**
     * @param impactName the impactName to set
     */
    public void setImpactName(String impactName) {
        this.impactName = impactName;
    }

    /**
     * @return the probabilityCode
     */
    public String getProbabilityCode() {
        return probabilityCode;
    }

    /**
     * @param probabilityCode the probabilityCode to set
     */
    public void setProbabilityCode(String probabilityCode) {
        this.probabilityCode = probabilityCode;
    }

    /**
     * @return the probabilityName
     */
    public String getProbabilityName() {
        return probabilityName;
    }

    /**
     * @param probabilityName the probabilityName to set
     */
    public void setProbabilityName(String probabilityName) {
        this.probabilityName = probabilityName;
    }

    /**
     * @return the costEffortCode
     */
    public String getCostEffortCode() {
        return costEffortCode;
    }

    /**
     * @param costEffortCode the costEffortCode to set
     */
    public void setCostEffortCode(String costEffortCode) {
        this.costEffortCode = costEffortCode;
    }

    /**
     * @return the costEffortName
     */
    public String getCostEffortName() {
        return costEffortName;
    }

    /**
     * @param costEffortName the costEffortName to set
     */
    public void setCostEffortName(String costEffortName) {
        this.costEffortName = costEffortName;
    }

    /**
     * @return the vectorText
     */
    public String getVectorText() {
        return vectorText;
    }

    /**
     * @param vectorText the vectorText to set
     */
    public void setVectorText(String vectorText) {
        this.vectorText = vectorText;
    }
    /**
     * @return the cweID
     */
    public String getCweID() {
        return cweID;
    }

    /**
     * @param cweID the cweID to set
     */
    public void setCweID(String cweID) {
        this.cweID = cweID;
    }

    /**
     * @return the baseScore
     */
    public BigDecimal getBaseScore() {
        return baseScore;
    }

    /**
     * @param baseScore the baseScore to set
     */
    public void setBaseScore(BigDecimal baseScore) {
        this.baseScore = baseScore;
    }

    /**
     * @return the hitrustCrossWalkMap
     */
    public Map<String, ArrayList<ComplianceInfoDTO>> getHitrustCrossWalkMap() {
        return hitrustCrossWalkMap;
    }

    /**
     * @param hitrustCrossWalkMap the hitrustCrossWalkMap to set
     */
    public void setHitrustCrossWalkMap(Map<String, ArrayList<ComplianceInfoDTO>> hitrustCrossWalkMap) {
        this.hitrustCrossWalkMap = hitrustCrossWalkMap;
    }

    /**
     * @return the srcBaseScore
     */
    public BigDecimal getSrcBaseScore() {
        return srcBaseScore;
    }

    /**
     * @param srcBaseScore the srcBaseScore to set
     */
    public void setSrcBaseScore(BigDecimal srcBaseScore) {
        this.srcBaseScore = srcBaseScore;
    }

    /**
     * @return the totalCount
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount the totalCount to set
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * @return the srctemporalScore
     */
    public BigDecimal getSrctemporalScore() {
        return srctemporalScore;
    }

    /**
     * @param srctemporalScore the srctemporalScore to set
     */
    public void setSrctemporalScore(BigDecimal srctemporalScore) {
        this.srctemporalScore = srctemporalScore;
    }
    
    
    
}
