/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.model;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dto.EngagementsDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author spatepuram
 */
//@Component
public class SecurityModel {

//    private List<ServiceModel> serviceList = new ArrayList<>();
//    private List<ServiceModel> catagoryList = new ArrayList<>();
//    private List<ServiceModel> severityList = new ArrayList<>();
    private List<FindingsModel> findingList = new ArrayList<>();
    private String chartName;
    private String chartTEXT;
    private String severityText;
    private String categoryText;
    private String serviceText;
    private EngagementsDTO engagementsDTO = new EngagementsDTO();
    private List<SummaryDropDownModel> serviceListSummary = new ArrayList<>();
    private List<SummaryDropDownModel> catagoryListSummary = new ArrayList<>();
    private List<SummaryDropDownModel> severityListSummary = new ArrayList<>();
    private String findingId;
    List<AssessmentModel> categoryLabelList = new ArrayList<AssessmentModel>();
    List<AssessmentModel> categoryDataList = new ArrayList<AssessmentModel>();
    List<AssessmentModel> percentageCountList = new ArrayList<AssessmentModel>();
    private String remYAxis;
    List<SummaryDropDownModel> exportCSVcheckboxListOne = new ArrayList<>();
    List<SummaryDropDownModel> exportCSVcheckboxListTwo = new ArrayList<>();
    List<SummaryDropDownModel> exportCSVcheckboxListThree = new ArrayList<>();
    List<SummaryDropDownModel> exportCSVcheckboxListFour = new ArrayList<>();
    List csvDBHeaders = new ArrayList();
    List csvHeaders = new ArrayList();
    private String findingSno = "";
    private String engSchemaName=ApplicationConstants.EMPTY_STRING;
    private String categoryCode;
    
    public String getChartTEXT() {
        return chartTEXT;
    }

    public void setChartTEXT(String chartTEXT) {
        this.chartTEXT = chartTEXT;
    }

    /**
     * @return the serviceList
     */
//    public List<ServiceModel> getServiceList() {
//        return serviceList;
//    }
//
//    /**
//     * @param serviceList the serviceList to set
//     */
//    public void setServiceList(List<ServiceModel> serviceList) {
//        this.serviceList = serviceList;
//    }
//
//    /**
//     * @return the catagoryList
//     */
//    public List<ServiceModel> getCatagoryList() {
//        return catagoryList;
//    }
//
//    /**
//     * @param catagoryList the catagoryList to set
//     */
//    public void setCatagoryList(List<ServiceModel> catagoryList) {
//        this.catagoryList = catagoryList;
//    }
//
//    /**
//     * @return the severityList
//     */
//    public List<ServiceModel> getSeverityList() {
//        return severityList;
//    }
//
//    /**
//     * @param severityList the severityList to set
//     */
//    public void setSeverityList(List<ServiceModel> severityList) {
//        this.severityList = severityList;
//    }

    /**
     * @return the chartName
     */
    public String getChartName() {
        return chartName;
    }

    /**
     * @param chartName the chartName to set
     */
    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    /**
     * @return the findingList
     */
    public List<FindingsModel> getFindingList() {
        return findingList;
    }

    /**
     * @param findingList the findingList to set
     */
    public void setFindingList(List<FindingsModel> findingList) {
        this.findingList = findingList;
    }

    /**
     * @return the engagementsDTO
     */
    public EngagementsDTO getEngagementsDTO() {
        return engagementsDTO;
    }

    /**
     * @param engagementsDTO the engagementsDTO to set
     */
    public void setEngagementsDTO(EngagementsDTO engagementsDTO) {
        this.engagementsDTO = engagementsDTO;
    }

    /**
     * @return the severityText
     */
    public String getSeverityText() {
        return severityText;
    }

    /**
     * @param severityText the severityText to set
     */
    public void setSeverityText(String severityText) {
        this.severityText = severityText;
    }

    /**
     * @return the categoryText
     */
    public String getCategoryText() {
        return categoryText;
    }

    /**
     * @param categoryText the categoryText to set
     */
    public void setCategoryText(String categoryText) {
        this.categoryText = categoryText;
    }

    /**
     * @return the serviceText
     */
    public String getServiceText() {
        return serviceText;
    }

    /**
     * @param serviceText the serviceText to set
     */
    public void setServiceText(String serviceText) {
        this.serviceText = serviceText;
    }

    /**
     * @return the serviceListSummary
     */
    public List<SummaryDropDownModel> getServiceListSummary() {
        return serviceListSummary;
    }

    /**
     * @param serviceListSummary the serviceListSummary to set
     */
    public void setServiceListSummary(List<SummaryDropDownModel> serviceListSummary) {
        this.serviceListSummary = serviceListSummary;
    }

    /**
     * @return the catagoryListSummary
     */
    public List<SummaryDropDownModel> getCatagoryListSummary() {
        return catagoryListSummary;
    }

    /**
     * @param catagoryListSummary the catagoryListSummary to set
     */
    public void setCatagoryListSummary(List<SummaryDropDownModel> catagoryListSummary) {
        this.catagoryListSummary = catagoryListSummary;
    }

    /**
     * @return the severityListSummary
     */
    public List<SummaryDropDownModel> getSeverityListSummary() {
        return severityListSummary;
    }

    /**
     * @param severityListSummary the severityListSummary to set
     */
    public void setSeverityListSummary(List<SummaryDropDownModel> severityListSummary) {
        this.severityListSummary = severityListSummary;
    }

    /**
     * @return the findingId
     */
    public String getFindingId() {
        return findingId;
    }

    /**
     * @param findingId the findingId to set
     */
    public void setFindingId(String findingId) {
        this.findingId = findingId;
    }

    public List<AssessmentModel> getCategoryLabelList() {
        return categoryLabelList;
    }

    public void setCategoryLabelList(List<AssessmentModel> categoryLabelList) {
        this.categoryLabelList = categoryLabelList;
    }

    public List<AssessmentModel> getCategoryDataList() {
        return categoryDataList;
    }

    public void setCategoryDataList(List<AssessmentModel> categoryDataList) {
        this.categoryDataList = categoryDataList;
    }

    public List<SummaryDropDownModel> getExportCSVcheckboxListOne() {
        return exportCSVcheckboxListOne;
    }

    public void setExportCSVcheckboxListOne(List<SummaryDropDownModel> exportCSVcheckboxListOne) {
        this.exportCSVcheckboxListOne = exportCSVcheckboxListOne;
    }

    public List<SummaryDropDownModel> getExportCSVcheckboxListTwo() {
        return exportCSVcheckboxListTwo;
    }

    public void setExportCSVcheckboxListTwo(List<SummaryDropDownModel> exportCSVcheckboxListTwo) {
        this.exportCSVcheckboxListTwo = exportCSVcheckboxListTwo;
    }

    public List<SummaryDropDownModel> getExportCSVcheckboxListThree() {
        return exportCSVcheckboxListThree;
    }

    public void setExportCSVcheckboxListThree(List<SummaryDropDownModel> exportCSVcheckboxListThree) {
        this.exportCSVcheckboxListThree = exportCSVcheckboxListThree;
    }

    public List<SummaryDropDownModel> getExportCSVcheckboxListFour() {
        return exportCSVcheckboxListFour;
    }

    public void setExportCSVcheckboxListFour(List<SummaryDropDownModel> exportCSVcheckboxListFour) {
        this.exportCSVcheckboxListFour = exportCSVcheckboxListFour;
    }


    public List getCsvDBHeaders() {
        return csvDBHeaders;
    }

    public void setCsvDBHeaders(List csvDBHeaders) {
        this.csvDBHeaders = csvDBHeaders;
    }

    public List getCsvHeaders() {
        return csvHeaders;
    }

    public void setCsvHeaders(List csvHeaders) {
        this.csvHeaders = csvHeaders;
    }

    public List<AssessmentModel> getPercentageCountList() {
        return percentageCountList;
    }

    public void setPercentageCountList(List<AssessmentModel> percentageCountList) {
        this.percentageCountList = percentageCountList;
    }

    public String getRemYAxis() {
        return remYAxis;
    }

    public void setRemYAxis(String remYAxis) {
        this.remYAxis = remYAxis;
    }

    public String getFindingSno() {
        return findingSno;
    }

    public void setFindingSno(String findingSno) {
        this.findingSno = findingSno;
    }

    public String getEngSchemaName() {
        return engSchemaName;
    }

    public void setEngSchemaName(String engSchemaName) {
        this.engSchemaName = engSchemaName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }
    
    
}
