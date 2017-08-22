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
public enum ApplicationMenuSubMenuEnum {
    
   // DB MENUNAME (LEFTMENU ACTION)
    Manage_Roles("manageRolepage.htm"),
    Manage_Permission_Groups("permissiongroupworklist.htm"),
    View_Detailed_Permissions("viewpermission.htm"),
    Manage_Organizations("organizationWorkList.htm"),
    Manage_Users("userlist.htm"),
    User_Activation("userworklist.htm"),   
    Manage_Engagements("engagementlist.htm"),
    Engagement_Data_Upload("engUploadWorkList.htm"),
    Analyst_Validation_Module("analystvalidationworklist.htm"),
//    Client_Reports_Upload("reportsuploadworklist.htm"),
    Reporting_Documents("reportsuploadworklist.htm"),
    Remediation_Tracking("remediationworklist.htm"),
//    Dashboard("dashboardhomecontroller.htm"),
    Reports("dashboardhomecontroller.htm"),
    Remediation_Module("remediationhome.htm"),
    Risk_Registry_Module("registryList.htm"),
    Manage_Engagement_Users("manageengusers.htm");
   
    private final String menuRepresentation;
    
    private ApplicationMenuSubMenuEnum(String menuRepresentation) {
        this.menuRepresentation = menuRepresentation;
    }

    @Override 
    public String toString() {
         return menuRepresentation;
    }
}
