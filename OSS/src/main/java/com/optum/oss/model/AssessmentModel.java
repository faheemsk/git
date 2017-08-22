/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.model;

import com.optum.oss.dto.BaseDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rpanuganti
 */
public class AssessmentModel extends BaseDTO {

    private String label;
    private String value;
    private String engagementCode;
    private String serviceCode;
    private String assessmentFlag;
    private String selectedString;
    private int totalCount;
    private double percentage;

    List<AssessmentModel> remList=new ArrayList<AssessmentModel>();
    private String remYAxis;
    

    public AssessmentModel(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public AssessmentModel() {

    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getEngagementCode() {
        return engagementCode;
    }

    public void setEngagementCode(String engagementCode) {
        this.engagementCode = engagementCode;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getAssessmentFlag() {
        return assessmentFlag;
    }

    public void setAssessmentFlag(String assessmentFlag) {
        this.assessmentFlag = assessmentFlag;
    }

    public String getSelectedString() {
        return selectedString;
    }

    public void setSelectedString(String selectedString) {
        this.selectedString = selectedString;
    }

    public List<AssessmentModel> getRemList() {
        return remList;
    }

    public void setRemList(List<AssessmentModel> remList) {
        this.remList = remList;
    }

    public String getRemYAxis() {
        return remYAxis;
    }

    public void setRemYAxis(String remYAxis) {
        this.remYAxis = remYAxis;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    }
