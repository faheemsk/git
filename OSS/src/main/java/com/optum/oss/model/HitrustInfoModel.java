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
public class HitrustInfoModel {
    
    private String controlName;
    private String description;
    private String objectiveName;
    private String familyName;

    /**
     * @return the controlName
     */
    public String getControlName() {
        return controlName;
    }

    /**
     * @param controlName the controlName to set
     */
    public void setControlName(String controlName) {
        this.controlName = controlName;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the objectiveName
     */
    public String getObjectiveName() {
        return objectiveName;
    }

    /**
     * @param objectiveName the objectiveName to set
     */
    public void setObjectiveName(String objectiveName) {
        this.objectiveName = objectiveName;
    }

    /**
     * @return the familyName
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * @param familyName the familyName to set
     */
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }
    
}
