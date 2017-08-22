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
public class FindingsModel {
    
    private String id;
    private String finding;
    private String risklevel;
    private String recommendation;
    private String description;
    private String hitrust;
    private String hipaa;
    private String nist;
    private String irs;
    private String mars;
    private String sox;
    private String iso;
    private String pci;
    private String csa;
    private String fisma;
    private String glba;
    private String fedRamp;
    
    private String service;
    private String source;
    private String domain;
    private String ipAddress;
    private String status;
    private String scanIdentifier;
    private String scanStartDate;
    private String scanEndDate;
    private String softwareName;
    private String host;
    private String url;
    private String operatingSystem;
    private String netBios;
    private String macAddress;
    private String port;
    private String threatCategory;
    private String technicalDetails;
    private String impactDetail;
    private String rootCauseDetail;
    private String cveId;
    private String cveDescription;
    private String baseScore;
    private String temporalScore;
    private String environmentalScore;
    private String severity;
    private String probability;
    private String impact;
    private String costEffort;
    private String overAllScore;
    private String sNo;

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFinding() {
        return finding;
    }

    public void setFinding(String finding) {
        this.finding = finding;
    }

    public String getRisklevel() {
        return risklevel;
    }

    public void setRisklevel(String risklevel) {
        this.risklevel = risklevel;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getHitrust() {
        return hitrust;
    }

    public void setHitrust(String hitrust) {
        this.hitrust = hitrust;
    }

    public String getHipaa() {
        return hipaa;
    }

    public void setHipaa(String hipaa) {
        this.hipaa = hipaa;
    }

    public String getNist() {
        return nist;
    }

    public void setNist(String nist) {
        this.nist = nist;
    }

    public String getIrs() {
        return irs;
    }

    public void setIrs(String irs) {
        this.irs = irs;
    }

    public String getMars() {
        return mars;
    }

    public void setMars(String mars) {
        this.mars = mars;
    }

    public String getSox() {
        return sox;
    }

    public void setSox(String sox) {
        this.sox = sox;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getPci() {
        return pci;
    }

    public void setPci(String pci) {
        this.pci = pci;
    }

    public String getCsa() {
        return csa;
    }

    public void setCsa(String csa) {
        this.csa = csa;
    }

    public String getFisma() {
        return fisma;
    }

    public void setFisma(String fisma) {
        this.fisma = fisma;
    }

    /**
     * @return the glba
     */
    public String getGlba() {
        return glba;
    }

    /**
     * @param glba the glba to set
     */
    public void setGlba(String glba) {
        this.glba = glba;
    }

    /**
     * @return the fedRamp
     */
    public String getFedRamp() {
        return fedRamp;
    }

    /**
     * @param fedRamp the fedRamp to set
     */
    public void setFedRamp(String fedRamp) {
        this.fedRamp = fedRamp;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getScanIdentifier() {
        return scanIdentifier;
    }

    public void setScanIdentifier(String scanIdentifier) {
        this.scanIdentifier = scanIdentifier;
    }

    public String getScanStartDate() {
        return scanStartDate;
    }

    public void setScanStartDate(String scanStartDate) {
        this.scanStartDate = scanStartDate;
    }

    public String getScanEndDate() {
        return scanEndDate;
    }

    public void setScanEndDate(String scanEndDate) {
        this.scanEndDate = scanEndDate;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getNetBios() {
        return netBios;
    }

    public void setNetBios(String netBios) {
        this.netBios = netBios;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getThreatCategory() {
        return threatCategory;
    }

    public void setThreatCategory(String threatCategory) {
        this.threatCategory = threatCategory;
    }

    public String getTechnicalDetails() {
        return technicalDetails;
    }

    public void setTechnicalDetails(String technicalDetails) {
        this.technicalDetails = technicalDetails;
    }

    public String getImpactDetail() {
        return impactDetail;
    }

    public void setImpactDetail(String impactDetail) {
        this.impactDetail = impactDetail;
    }

    public String getRootCauseDetail() {
        return rootCauseDetail;
    }

    public void setRootCauseDetail(String rootCauseDetail) {
        this.rootCauseDetail = rootCauseDetail;
    }

    public String getCveId() {
        return cveId;
    }

    public void setCveId(String cveId) {
        this.cveId = cveId;
    }

    public String getCveDescription() {
        return cveDescription;
    }

    public void setCveDescription(String cveDescription) {
        this.cveDescription = cveDescription;
    }

    public String getBaseScore() {
        return baseScore;
    }

    public void setBaseScore(String baseScore) {
        this.baseScore = baseScore;
    }

    public String getTemporalScore() {
        return temporalScore;
    }

    public void setTemporalScore(String temporalScore) {
        this.temporalScore = temporalScore;
    }

    public String getEnvironmentalScore() {
        return environmentalScore;
    }

    public void setEnvironmentalScore(String environmentalScore) {
        this.environmentalScore = environmentalScore;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getProbability() {
        return probability;
    }

    public void setProbability(String probability) {
        this.probability = probability;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public String getCostEffort() {
        return costEffort;
    }

    public void setCostEffort(String costEffort) {
        this.costEffort = costEffort;
    }

    public String getOverAllScore() {
        return overAllScore;
    }

    public void setOverAllScore(String overAllScore) {
        this.overAllScore = overAllScore;
    }

    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }
    
    
}
