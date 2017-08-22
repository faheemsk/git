/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.model;

/**
 *
 * @author vkosuri
 */
public class PrioritizationMatrixModel {

    private String id;
    private String finding;
    private String risklevel;
    private String recommendation;
    private String description;

    public PrioritizationMatrixModel(String id, String finding, String risklevel, String description, String recommendation) {
        this.id = id;
        this.finding = finding;
        this.risklevel = risklevel;
        this.recommendation = recommendation;
        this.description = description;
    }

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

}
