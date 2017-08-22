/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.constants;

/**
 *
 * @author hpasupuleti
 */
public enum ApplicationMenuSubMenuIDEnum {
    
    Manage_Roles_Permissions("mrandp"),
    Manage_Roles("mr"),
    Manage_Permission_Groups("mpg"),
    View_Detailed_Permissions("vdp"),
    Manage_Organizations("mo"),
    Manage_Users("mu"),
    User_Activation("ua"),
    Client_Engagement("emwl"),
    Manage_Engagements("meng"),
    Engagement_Data_Upload("edu"),
    Analyst_Validation_Module("avm"),
//    Client_Reports_Upload("cru"),
    Reporting_Documents("cru"),
    Remediation_Tracking("rpm"),
//    Dashboard("dshbrd"),
    Reports("dshbrd"),
    Remediation_Module("rmdml"),
    Risk_Registry_Module("riskreg"),
    Manage_Engagement_Users("meu");
   
    private final String menuRepresentation;
    
    private ApplicationMenuSubMenuIDEnum(String menuRepresentation) {
        this.menuRepresentation = menuRepresentation;
    }

    @Override 
    public String toString() {
         return menuRepresentation;
    }
    
}
