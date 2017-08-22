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
public class SecurityPackageDTO extends BaseDTO implements Serializable{
    
    private long securityPackageKey;
    private String encSecurityPackageKey;
    private String securityPackageCode;
    private String securityPackageName;
    private String encSecurityPackageCode;
    private String encSecurityPackageName;
    
    private String securityPackageDesc;
    private List<SecurityServiceDTO> securityServiceLi = new ArrayList<>();

    /**
     * @return the securityPackageKey
     */
    public long getSecurityPackageKey() {
        return securityPackageKey;
    }

    /**
     * @param securityPackageKey the securityPackageKey to set
     */
    public void setSecurityPackageKey(long securityPackageKey) {
        this.securityPackageKey = securityPackageKey;
    }

    /**
     * @return the encSecurityPackageKey
     */
    public String getEncSecurityPackageKey() {
        return encSecurityPackageKey;
    }

    /**
     * @param encSecurityPackageKey the encSecurityPackageKey to set
     */
    public void setEncSecurityPackageKey(String encSecurityPackageKey) {
        this.encSecurityPackageKey = encSecurityPackageKey;
    }

    /**
     * @return the securityPackageCode
     */
    public String getSecurityPackageCode() {
        return securityPackageCode;
    }

    /**
     * @param securityPackageCode the securityPackageCode to set
     */
    public void setSecurityPackageCode(String securityPackageCode) {
        this.securityPackageCode = securityPackageCode;
    }

    /**
     * @return the securityPackageName
     */
    public String getSecurityPackageName() {
        return securityPackageName;
    }

    /**
     * @param securityPackageName the securityPackageName to set
     */
    public void setSecurityPackageName(String securityPackageName) {
        this.securityPackageName = securityPackageName;
    }

    /**
     * @return the securityPackageDesc
     */
    public String getSecurityPackageDesc() {
        return securityPackageDesc;
    }

    /**
     * @param securityPackageDesc the securityPackageDesc to set
     */
    public void setSecurityPackageDesc(String securityPackageDesc) {
        this.securityPackageDesc = securityPackageDesc;
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
     * @return the encSecurityPackageCode
     */
    public String getEncSecurityPackageCode() {
        return encSecurityPackageCode;
    }

    /**
     * @param encSecurityPackageCode the encSecurityPackageCode to set
     */
    public void setEncSecurityPackageCode(String encSecurityPackageCode) {
        this.encSecurityPackageCode = encSecurityPackageCode;
    }

    /**
     * @return the encdSecurityPackageName
     */
    public String getEncSecurityPackageName() {
        return encSecurityPackageName;
    }

    /**
     * @param encdSecurityPackageName the encdSecurityPackageName to set
     */
    public void setEncSecurityPackageName(String encSecurityPackageName) {
        this.encSecurityPackageName = encSecurityPackageName;
    }
    
    
}
