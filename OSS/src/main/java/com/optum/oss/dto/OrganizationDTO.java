/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dto;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hpasupuleti
 */
//@Component
public class OrganizationDTO extends BaseDTO implements Serializable {

    @JsonView(Views.Public.class)
    private long organizationKey;
    private long parentOrganizationKey;
    private long organizationTypeKey;
    private MasterLookUpDTO organizationTypeObj = new MasterLookUpDTO();
    private long organizationIndustryKey;
    private MasterLookUpDTO organizationIndustryObj = new MasterLookUpDTO();
    @JsonView(Views.Public.class)
    private String organizationName;
    private String streetAddress1;
    private String streetAddress2;
    private String cityName;
    private String stateName;
    private String countryName;
    private String postalCode;
    private String organizationDescription;
    private List<OrganizationSourceDTO> organizationSourceListObj = new ArrayList<>();
    private String parentOrganizationName;
    private long sourceKeyArray[];
    private String sourceClientIDArray[];
    private String sourceTextArray[];
    private String encOrganizationKey;
    private String encOrganizationName;
    private long usersCount;
    private String deactiveReason;
    private String schemaName;
    private String organizationType;
    private long schemaKey;
    private String schemaStatus;
    private String schemaLog;
    private String schemaIndicator;

    /**
     * @return the organizationKey
     */
    public long getOrganizationKey() {
        return organizationKey;
    }

    /**
     * @param organizationKey the organizationKey to set
     */
    public void setOrganizationKey(long organizationKey) {
        this.organizationKey = organizationKey;
    }

    /**
     * @return the parentOrganizationKey
     */
    public long getParentOrganizationKey() {
        return parentOrganizationKey;
    }

    /**
     * @param parentOrganizationKey the parentOrganizationKey to set
     */
    public void setParentOrganizationKey(long parentOrganizationKey) {
        this.parentOrganizationKey = parentOrganizationKey;
    }

    /**
     * @return the parentOrganizationName
     */
    public String getParentOrganizationName() {
        return parentOrganizationName;
    }

    /**
     * @param parentOrganizationName the parentOrganizationName to set
     */
    public void setParentOrganizationName(String parentOrganizationName) {
        this.parentOrganizationName = parentOrganizationName;
    }

    /**
     * @return the organizationTypeKey
     */
    public long getOrganizationTypeKey() {
        return organizationTypeKey;
    }

    /**
     * @param organizationTypeKey the organizationTypeKey to set
     */
    public void setOrganizationTypeKey(long organizationTypeKey) {
        this.organizationTypeKey = organizationTypeKey;
    }

    /**
     * @return the organizationTypeObj
     */
    public MasterLookUpDTO getOrganizationTypeObj() {
        return organizationTypeObj;
    }

    /**
     * @param organizationTypeObj the organizationTypeObj to set
     */
    public void setOrganizationTypeObj(MasterLookUpDTO organizationTypeObj) {
        this.organizationTypeObj = organizationTypeObj;
    }

    /**
     * @return the organizationIndustryKey
     */
    public long getOrganizationIndustryKey() {
        return organizationIndustryKey;
    }

    /**
     * @param organizationIndustryKey the organizationIndustryKey to set
     */
    public void setOrganizationIndustryKey(long organizationIndustryKey) {
        this.organizationIndustryKey = organizationIndustryKey;
    }

    /**
     * @return the organizationIndustryObj
     */
    public MasterLookUpDTO getOrganizationIndustryObj() {
        return organizationIndustryObj;
    }

    /**
     * @param organizationIndustryObj the organizationIndustryObj to set
     */
    public void setOrganizationIndustryObj(MasterLookUpDTO organizationIndustryObj) {
        this.organizationIndustryObj = organizationIndustryObj;
    }

    /**
     * @return the organizationName
     */
    public String getOrganizationName() {
        return organizationName;
    }

    /**
     * @param organizationName the organizationName to set
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    /**
     * @return the streetAddress1
     */
    public String getStreetAddress1() {
        return streetAddress1;
    }

    /**
     * @param streetAddress1 the streetAddress1 to set
     */
    public void setStreetAddress1(String streetAddress1) {
        this.streetAddress1 = streetAddress1;
    }

    /**
     * @return the streetAddress2
     */
    public String getStreetAddress2() {
        return streetAddress2;
    }

    /**
     * @param streetAddress2 the streetAddress2 to set
     */
    public void setStreetAddress2(String streetAddress2) {
        this.streetAddress2 = streetAddress2;
    }

    /**
     * @return the cityName
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * @param cityName the cityName to set
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * @return the stateName
     */
    public String getStateName() {
        return stateName;
    }

    /**
     * @param stateName the stateName to set
     */
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    /**
     * @return the countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * @param countryName the countryName to set
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * @return the postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode the postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return the organizationDescription
     */
    public String getOrganizationDescription() {
        return organizationDescription;
    }

    /**
     * @param organizationDescription the organizationDescription to set
     */
    public void setOrganizationDescription(String organizationDescription) {
        this.organizationDescription = organizationDescription;
    }

    /**
     * @return the organizationSourceListObj
     */
    public List<OrganizationSourceDTO> getOrganizationSourceListObj() {
        return organizationSourceListObj;
    }

    /**
     * @param organizationSourceListObj the organizationSourceListObj to set
     */
    public void setOrganizationSourceListObj(List<OrganizationSourceDTO> organizationSourceListObj) {
        this.organizationSourceListObj = organizationSourceListObj;
    }

    /**
     * @return the sourceKeyArray
     */
    public long[] getSourceKeyArray() {
        return sourceKeyArray;
    }

    /**
     * @param sourceKeyArray the sourceKeyArray to set
     */
    public void setSourceKeyArray(long[] sourceKeyArray) {
        this.sourceKeyArray = sourceKeyArray;
    }

    /**
     * @return the sourceClientIDArray
     */
    public String[] getSourceClientIDArray() {
        return sourceClientIDArray;
    }

    /**
     * @param sourceClientIDArray the sourceClientIDArray to set
     */
    public void setSourceClientIDArray(String[] sourceClientIDArray) {
        this.sourceClientIDArray = sourceClientIDArray;
    }

    /**
     * @return the sourceTextArray
     */
    public String[] getSourceTextArray() {
        return sourceTextArray;
    }

    /**
     * @param sourceTextArray the sourceTextArray to set
     */
    public void setSourceTextArray(String[] sourceTextArray) {
        this.sourceTextArray = sourceTextArray;
    }

    /**
     * @return the encOrganizationKey
     */
    public String getEncOrganizationKey() {
        return encOrganizationKey;
    }

    /**
     * @param encOrganizationKey the encOrganizationKey to set
     */
    public void setEncOrganizationKey(String encOrganizationKey) {
        this.encOrganizationKey = encOrganizationKey;
    }

    /**
     * @return the deactiveReason
     */
    public String getDeactiveReason() {
        return deactiveReason;
    }

    /**
     * @param deactiveReason the deactiveReason to set
     */
    public void setDeactiveReason(String deactiveReason) {
        this.deactiveReason = deactiveReason;
    }
    /**
     * @return the usersCount
     */
    public long getUsersCount() {
        return usersCount;
    }

    /**
     * @param usersCount the usersCount to set
     */
    public void setUsersCount(long usersCount) {
        this.usersCount = usersCount;
    }

    /**
     * @return the encOrganizationName
     */
    public String getEncOrganizationName() {
        return encOrganizationName;
    }

    /**
     * @param encOrganizationName the encOrganizationName to set
     */
    public void setEncOrganizationName(String encOrganizationName) {
        this.encOrganizationName = encOrganizationName;
    }

    /**
     * @return the schemaName
     */
    public String getSchemaName() {
        return schemaName;
    }

    /**
     * @param schemaName the schemaName to set
     */
    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(String organizationType) {
        this.organizationType = organizationType;
    }

    public long getSchemaKey() {
        return schemaKey;
    }

    public void setSchemaKey(long schemaKey) {
        this.schemaKey = schemaKey;
    }

    public String getSchemaStatus() {
        return schemaStatus;
    }

    public void setSchemaStatus(String schemaStatus) {
        this.schemaStatus = schemaStatus;
    }

    public String getSchemaLog() {
        return schemaLog;
    }

    public void setSchemaLog(String schemaLog) {
        this.schemaLog = schemaLog;
    }

    public String getSchemaIndicator() {
        return schemaIndicator;
    }

    public void setSchemaIndicator(String schemaIndicator) {
        this.schemaIndicator = schemaIndicator;
    }


}
