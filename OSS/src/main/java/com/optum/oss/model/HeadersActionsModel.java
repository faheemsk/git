/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.model;

/**
 *
 * @author mrejeti
 */
public class HeadersActionsModel {
    private String headerName;
    private String actionName;

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public HeadersActionsModel(String headerName, String actionName) {
        this.headerName = headerName;
        this.actionName = actionName;
    }
    
    
}
