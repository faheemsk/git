/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.model;

/**
 *
 * @author mrejeti
 * THIS MODEL IS TO DISPLAY THE ASSESSMENTS LIST FOR THE ENGAGMENTS - IN HEADER SECTION AFTER THE SUMMARY LINK
 */
public class AssessmentServicesModel {

    
    private String serviceCode;
    private String serviceName;
    private String actionURL;

    public AssessmentServicesModel(String serviceCode, String serviceName, String actionURL) {
        this.serviceCode = serviceCode;
        this.serviceName = serviceName;
        this.actionURL = actionURL;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getActionURL() {
        return actionURL;
    }

    public void setActionURL(String actionURL) {
        this.actionURL = actionURL;
    }

}
