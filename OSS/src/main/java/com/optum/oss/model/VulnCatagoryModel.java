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
public class VulnCatagoryModel {
    private String vulnCatID="";
    private double xPosition;
    private double yPosition;

    /**
     * @return the vulnCatID
     */
    public String getVulnCatID() {
        return vulnCatID;
    }

    /**
     * @param vulnCatID the vulnCatID to set
     */
    public void setVulnCatID(String vulnCatID) {
        this.vulnCatID = vulnCatID;
    }

    /**
     * @return the xPosition
     */
    public double getxPosition() {
        return xPosition;
    }

    /**
     * @param xPosition the xPosition to set
     */
    public void setxPosition(double xPosition) {
        this.xPosition = xPosition;
    }

    /**
     * @return the yPosition
     */
    public double getyPosition() {
        return yPosition;
    }

    /**
     * @param yPosition the yPosition to set
     */
    public void setyPosition(double yPosition) {
        this.yPosition = yPosition;
    }
    
    
}
