/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.dto;

import java.io.Serializable;

/**
 *
 * @author hpasupuleti
 */
//@Component
public class CVSSCalculatorDTO extends BaseDTO implements Serializable{
    
    private String cvssVersion;
    private String metricGroupName;
    private String metricName;
    private String metricNameVectorNotation;
    private String metricValue;
    private String metricValueVectorNotation;
    private String metricScoreValue;
    private String metricNameValueVectorNotation;
    private Integer defaultChecked;
    private Integer vectorOrder;
    private String formulaMetricName;

    /**
     * @return the cvssVersion
     */
    public String getCvssVersion() {
        return cvssVersion;
    }

    /**
     * @param cvssVersion the cvssVersion to set
     */
    public void setCvssVersion(String cvssVersion) {
        this.cvssVersion = cvssVersion;
    }

    /**
     * @return the metricGroupName
     */
    public String getMetricGroupName() {
        return metricGroupName;
    }

    /**
     * @param metricGroupName the metricGroupName to set
     */
    public void setMetricGroupName(String metricGroupName) {
        this.metricGroupName = metricGroupName;
    }

    /**
     * @return the metricName
     */
    public String getMetricName() {
        return metricName;
    }

    /**
     * @param metricName the metricName to set
     */
    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    /**
     * @return the metricNameVectorNotation
     */
    public String getMetricNameVectorNotation() {
        return metricNameVectorNotation;
    }

    /**
     * @param metricNameVectorNotation the metricNameVectorNotation to set
     */
    public void setMetricNameVectorNotation(String metricNameVectorNotation) {
        this.metricNameVectorNotation = metricNameVectorNotation;
    }

    /**
     * @return the metricValue
     */
    public String getMetricValue() {
        return metricValue;
    }

    /**
     * @param metricValue the metricValue to set
     */
    public void setMetricValue(String metricValue) {
        this.metricValue = metricValue;
    }

    /**
     * @return the metricValueVectorNotation
     */
    public String getMetricValueVectorNotation() {
        return metricValueVectorNotation;
    }

    /**
     * @param metricValueVectorNotation the metricValueVectorNotation to set
     */
    public void setMetricValueVectorNotation(String metricValueVectorNotation) {
        this.metricValueVectorNotation = metricValueVectorNotation;
    }

    /**
     * @return the metricScoreValue
     */
    public String getMetricScoreValue() {
        return metricScoreValue;
    }

    /**
     * @param metricScoreValue the metricScoreValue to set
     */
    public void setMetricScoreValue(String metricScoreValue) {
        this.metricScoreValue = metricScoreValue;
    }

    /**
     * @return the metricNameValueVectorNotation
     */
    public String getMetricNameValueVectorNotation() {
        return metricNameValueVectorNotation;
    }

    /**
     * @param metricNameValueVectorNotation the metricNameValueVectorNotation to set
     */
    public void setMetricNameValueVectorNotation(String metricNameValueVectorNotation) {
        this.metricNameValueVectorNotation = metricNameValueVectorNotation;
    }

    /**
     * @return the defaultChecked
     */
    public Integer getDefaultChecked() {
        return defaultChecked;
    }

    /**
     * @param defaultChecked the defaultChecked to set
     */
    public void setDefaultChecked(Integer defaultChecked) {
        this.defaultChecked = defaultChecked;
    }

    /**
     * @return the vectorOrder
     */
    public Integer getVectorOrder() {
        return vectorOrder;
    }

    /**
     * @param vectorOrder the vectorOrder to set
     */
    public void setVectorOrder(Integer vectorOrder) {
        this.vectorOrder = vectorOrder;
    }

    /**
     * @return the formulaMetricName
     */
    public String getFormulaMetricName() {
        return formulaMetricName;
    }

    /**
     * @param formulaMetricName the formulaMetricName to set
     */
    public void setFormulaMetricName(String formulaMetricName) {
        this.formulaMetricName = formulaMetricName;
    }
    
}
