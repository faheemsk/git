/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.dto;

import java.io.Serializable;

/**
 *
 * @author sbhagavatula
 */
public class ComplianceInfoDTO extends BaseDTO implements Serializable{
    
    private String complicanceCode;
    private String complianceVersion;
    private String familyCode;
    private String familyName;
    private String familyDescription;
    private String objectiveCode;
    private String objectiveName;
    private String objectiveDesc;
    private String controlName;
    private String controlDesc;
    private String controlType;
    private String topicText;
    private String controlCode;
    
    private int checkedHitrust;

    /**
     * @return the familyCode
     */
    public String getFamilyCode() {
        return familyCode;
    }

    /**
     * @param familyCode the familyCode to set
     */
    public void setFamilyCode(String familyCode) {
        this.familyCode = familyCode;
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

    /**
     * @return the familyDescription
     */
    public String getFamilyDescription() {
        return familyDescription;
    }

    /**
     * @param familyDescription the familyDescription to set
     */
    public void setFamilyDescription(String familyDescription) {
        this.familyDescription = familyDescription;
    }

    /**
     * @return the objectiveCode
     */
    public String getObjectiveCode() {
        return objectiveCode;
    }

    /**
     * @param objectiveCode the objectiveCode to set
     */
    public void setObjectiveCode(String objectiveCode) {
        this.objectiveCode = objectiveCode;
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
     * @return the objectiveDesc
     */
    public String getObjectiveDesc() {
        return objectiveDesc;
    }

    /**
     * @param objectiveDesc the objectiveDesc to set
     */
    public void setObjectiveDesc(String objectiveDesc) {
        this.objectiveDesc = objectiveDesc;
    }

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
     * @return the controlDesc
     */
    public String getControlDesc() {
        return controlDesc;
    }

    /**
     * @param controlDesc the controlDesc to set
     */
    public void setControlDesc(String controlDesc) {
        this.controlDesc = controlDesc;
    }

    /**
     * @return the controlType
     */
    public String getControlType() {
        return controlType;
    }

    /**
     * @param controlType the controlType to set
     */
    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    /**
     * @return the topicText
     */
    public String getTopicText() {
        return topicText;
    }

    /**
     * @param topicText the topicText to set
     */
    public void setTopicText(String topicText) {
        this.topicText = topicText;
    }

    /**
     * @return the complicanceCode
     */
    public String getComplicanceCode() {
        return complicanceCode;
    }

    /**
     * @param complicanceCode the complicanceCode to set
     */
    public void setComplicanceCode(String complicanceCode) {
        this.complicanceCode = complicanceCode;
    }

    /**
     * @return the controlCode
     */
    public String getControlCode() {
        return controlCode;
    }

    /**
     * @param controlCode the controlCode to set
     */
    public void setControlCode(String controlCode) {
        this.controlCode = controlCode;
    }

    /**
     * @return the complianceVersion
     */
    public String getComplianceVersion() {
        return complianceVersion;
    }

    /**
     * @param complianceVersion the complianceVersion to set
     */
    public void setComplianceVersion(String complianceVersion) {
        this.complianceVersion = complianceVersion;
    }

    /**
     * @return the checkedHitrust
     */
    public int getCheckedHitrust() {
        return checkedHitrust;
    }

    /**
     * @param checkedHitrust the checkedHitrust to set
     */
    public void setCheckedHitrust(int checkedHitrust) {
        this.checkedHitrust = checkedHitrust;
    }
    
    
}
