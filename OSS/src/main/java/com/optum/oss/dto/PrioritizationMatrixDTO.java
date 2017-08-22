/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dto;

import java.util.List;

/**
 *
 * @author vkosuri
 */
public class PrioritizationMatrixDTO {

    private long q1FindingsTotal;
    private long q2FindingsTotal;
    private long q3FindingsTotal;
    private long q4FindingsTotal;
    private List<String> q1Findings;
    private List<String> q2Findings;
    private List<String> q3Findings;
    private List<String> q4Findings;
    private String quadrant1FilterFindings;
    private String quadrant2FilterFindings;
    private String quadrant3FilterFindings;
    private String quadrant4FilterFindings;

    public String getQuadrant1FilterFindings() {
        return quadrant1FilterFindings;
    }

    public void setQuadrant1FilterFindings(String quadrant1FilterFindings) {
        this.quadrant1FilterFindings = quadrant1FilterFindings;
    }

    public String getQuadrant2FilterFindings() {
        return quadrant2FilterFindings;
    }

    public void setQuadrant2FilterFindings(String quadrant2FilterFindings) {
        this.quadrant2FilterFindings = quadrant2FilterFindings;
    }

    public String getQuadrant3FilterFindings() {
        return quadrant3FilterFindings;
    }

    public void setQuadrant3FilterFindings(String quadrant3FilterFindings) {
        this.quadrant3FilterFindings = quadrant3FilterFindings;
    }

    public String getQuadrant4FilterFindings() {
        return quadrant4FilterFindings;
    }

    public void setQuadrant4FilterFindings(String quadrant4FilterFindings) {
        this.quadrant4FilterFindings = quadrant4FilterFindings;
    }

    public long getQ1FindingsTotal() {
        return q1FindingsTotal;
    }

    public void setQ1FindingsTotal(long q1FindingsTotal) {
        this.q1FindingsTotal = q1FindingsTotal;
    }

    public long getQ2FindingsTotal() {
        return q2FindingsTotal;
    }

    public void setQ2FindingsTotal(long q2FindingsTotal) {
        this.q2FindingsTotal = q2FindingsTotal;
    }

    public long getQ3FindingsTotal() {
        return q3FindingsTotal;
    }

    public void setQ3FindingsTotal(long q3FindingsTotal) {
        this.q3FindingsTotal = q3FindingsTotal;
    }

    public long getQ4FindingsTotal() {
        return q4FindingsTotal;
    }

    public void setQ4FindingsTotal(long q4FindingsTotal) {
        this.q4FindingsTotal = q4FindingsTotal;
    }

    public List<String> getQ1Findings() {
        return q1Findings;
    }

    public void setQ1Findings(List<String> q1Findings) {
        this.q1Findings = q1Findings;
    }

    public List<String> getQ2Findings() {
        return q2Findings;
    }

    public void setQ2Findings(List<String> q2Findings) {
        this.q2Findings = q2Findings;
    }

    public List<String> getQ3Findings() {
        return q3Findings;
    }

    public void setQ3Findings(List<String> q3Findings) {
        this.q3Findings = q3Findings;
    }

    public List<String> getQ4Findings() {
        return q4Findings;
    }

    public void setQ4Findings(List<String> q4Findings) {
        this.q4Findings = q4Findings;
    }

}
