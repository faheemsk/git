/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hpasupuleti
 */
//@Component
public class EngagementPartnerUserDTO extends BaseDTO implements Serializable{
    
    private long engPartnerUserKey;
    private String encEngPartnerUserKey;
    private long partnerUserOrgID;
    private long partnerUserID;
    private List<SecurityServiceDTO> securityServiceLi = new ArrayList<>();
    private String securityServicesSelected;
    private String partnerUserName;
    private String partnerUserOrgName;
    //Start: Bug Id: IN021478 : For engagement client lead mail notification
    private String partnerLeadEmailId;
    //End: Bug Id: IN021478 : For engagement client lead mail notification

    /**
     * @return the engPartnerUserKey
     */
    public long getEngPartnerUserKey() {
        return engPartnerUserKey;
    }

    /**
     * @param engPartnerUserKey the engPartnerUserKey to set
     */
    public void setEngPartnerUserKey(long engPartnerUserKey) {
        this.engPartnerUserKey = engPartnerUserKey;
    }

    /**
     * @return the encEngPartnerUserKey
     */
    public String getEncEngPartnerUserKey() {
        return encEngPartnerUserKey;
    }

    /**
     * @param encEngPartnerUserKey the encEngPartnerUserKey to set
     */
    public void setEncEngPartnerUserKey(String encEngPartnerUserKey) {
        this.encEngPartnerUserKey = encEngPartnerUserKey;
    }

    /**
     * @return the partnerUserOrgID
     */
    public long getPartnerUserOrgID() {
        return partnerUserOrgID;
    }

    /**
     * @param partnerUserOrgID the partnerUserOrgID to set
     */
    public void setPartnerUserOrgID(long partnerUserOrgID) {
        this.partnerUserOrgID = partnerUserOrgID;
    }

    /**
     * @return the partnerUserID
     */
    public long getPartnerUserID() {
        return partnerUserID;
    }

    /**
     * @param partnerUserID the partnerUserID to set
     */
    public void setPartnerUserID(long partnerUserID) {
        this.partnerUserID = partnerUserID;
    }

    /**
     * @return the securityServiceLi
     */
    public List<SecurityServiceDTO> getSecurityServiceLi() {
        return securityServiceLi;
    }

    /**
     * @param securityServiceLi the securityServiceLi to set
     */
    public void setSecurityServiceLi(List<SecurityServiceDTO> securityServiceLi) {
        this.securityServiceLi = securityServiceLi;
    }

    /**
     * @return the securityServicesSelected
     */
    public String getSecurityServicesSelected() {
        return securityServicesSelected;
    }

    /**
     * @param securityServicesSelected the securityServicesSelected to set
     */
    public void setSecurityServicesSelected(String securityServicesSelected) {
        this.securityServicesSelected = securityServicesSelected;
    }

    /**
     * @return the partnerUserName
     */
    public String getPartnerUserName() {
        return partnerUserName;
    }

    /**
     * @param partnerUserName the partnerUserName to set
     */
    public void setPartnerUserName(String partnerUserName) {
        this.partnerUserName = partnerUserName;
    }

    /**
     * @return the partnerUserOrgName
     */
    public String getPartnerUserOrgName() {
        return partnerUserOrgName;
    }

    /**
     * @param partnerUserOrgName the partnerUserOrgName to set
     */
    public void setPartnerUserOrgName(String partnerUserOrgName) {
        this.partnerUserOrgName = partnerUserOrgName;
    }

    /**
     * @return the partnerLeadEmailId
     */
    public String getPartnerLeadEmailId() {
        return partnerLeadEmailId;
    }

    /**
     * @param partnerLeadEmailId the partnerLeadEmailId to set
     */
    public void setPartnerLeadEmailId(String partnerLeadEmailId) {
        this.partnerLeadEmailId = partnerLeadEmailId;
    }
}
