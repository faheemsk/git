/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.constants;

import org.springframework.stereotype.Component;

/**
 *
 * @author hpasupuleti
 */
@Component
public class PermissionConstants {

    /*
     Start : Module Permission(s) Name
     */
    public static final String ADMINISTRATION_MODULE = "Administration";
    public static final String CLIENT_ENGAGEMENT_MODULE = "Client Engagement";
    public static final String DATA_UPLOAD_MODULE = "Engagement Data Upload";
    public static final String ANALYST_MODULE = "Analyst";
    public static final String REPORTING_MODULE = "Reporting";
    /*
     End : Module Permission(s) Name
     */

    /*
     Start : Menu Permission(s) Name
     */
    public static final String MANAGE_ROLES_PERMISSIONS_MENU = "Manage Roles & Permissions";
    public static final String MANAGE_CLIENT_INFO_MENU = "Manage Organizations";
    public static final String MANAGE_PARTNER_INFO_MENU = "Manage Users";
    public static final String USER_ACTIVATION_MENU = "User Activation";
    public static final String CLIENT_ENGAGEMENT = "Client Engagement";
    public static final String ENGAGEMENT_DATA_UPLOAD_MENU = "Engagement Data Upload";
    public static final String ANALYST_VALIDATION_MODULE_MENU = "Analyst Validation Module";
    public static final String REMEDIATION_MODULE = "Remediation Module";
    public static final String RISK_REGISTRY_MODULE = "Risk Registry Module";
    public static final String REPORTS_MENU = "Reports";
    /*
     End : Menu Permission(s) Name
     */

    /*
     Start : Sub-menu Permission(s) Name
     */
    public static final String MANAGE_ROLES_SUB_MENU = "Manage Roles";
    public static final String MANAGE_PERMISSION_GROUP_SUB_MENU = "Manage Permission Groups";
    public static final String VIEW_DETAILED_PERMISSIONS_SUB_MENU = "View Detailed Permissions";
    public static final String MANAGE_ORGANIZATIONS_SUB_MENU = "Manage Organizations Sub Menu";
    public static final String MANAGE_USERS_SUB_MENU = "Manage Users Sub menu";
    public static final String USER_ACTIVATION_SUB_MENU = "User Activation Sub menu";
    public static final String MANAGE_ENGAGEMENTS_SUB_MENU = "Manage Engagements";
    public static final String MANAGE_ENGAGEMENT_USERS_SUB_MENU = "Manage Engagement Users";
    /*
     End : Sub-menu Permission(s) Name
     */

    /*
     Start : Manage Role(s) Permissions
     */
    public static final String ADD_ROLE = "Add Role";
    public static final String UPDATE_ROLE = "Update Role";
    public static final String VIEW_ROLE = "View Role";
    public static final String REMOVE_ROLE = "Delete Role";

    /*
     End : Manage Role(s) Permissions
     */
    /*
     Start : Manage Permission Group(s) Permissions
     */
    public static final String ADD_PERMISSION_GROUP = "Add Permission Group";
    public static final String UPDATE_PERMISSION_GROUP = "Update Permission Group";
    public static final String REMOVE_PERMISSION_GROUP = "Delete Permission Group";
    public static final String VIEW_PERMISSION_GROUP = "View Permission Group";
    /*
     End : Manage Permission Group(s) Permissions
     */

    /*
     Start : View Detailed Permission(s) Permissions
     */
    public static final String VIEW_DETAILED_PERMISSIONS = "View Detailed Permissions";
    public static final String EDIT_DETAILED_PERMISSIONS = "Edit Detailed Permission Description";
    /*
     End : View Detailed Permission(s) Permissions
     */

    /*
     Start : Manage Organization(s)Permissions
     */
    public static final String ADD_ORGANIZATION = "Add Organization";
    public static final String UPDATE_ORGANIZATION = "Update Organization";
    public static final String VIEW_ORGANIZATION = "View Organization";
    /*
     End : Manage Organization(s)Permissions
     */
    /*
     Start : Manage User(s)Permissions
     */
    public static final String ADD_USER = "Add User";
    public static final String UPDATE_USER = "Update User";
    public static final String VIEW_USER = "View User";
    /*
     End : Manage User(s)Permissions
     */

    /*
     Start : User ActivationPermissions
     */
    public static final String VIEW_USER_ACTIVATION = "View User Activation";
    public static final String UPDATE_USER_ACTIVATION = "Update User Activation";
    /*
     End : User ActivationPermissions
     */
    /* Start: Client Engagement Permissions
     */
    public static final String ADD_ENGAGEMENT = "Add Engagement";
    public static final String EDIT_ENGAGEMENT = "Edit Engagement";
    public static final String VIEW_ENGAGEMENT = "View Engagement";
    public static final String DELETE_ENGAGEMENT = "Delete Engagement";

    public static final String ADD_USER_TO_SERVICE = "Add User to Service";
    public static final String EDIT_USER_TO_SERVICE = "Edit User to Service";
    public static final String VIEW_USER_TO_SERVICE = "View User to Service";
    public static final String DELETE_USER_TO_SERVICE = "Delete User to Service";

    /* End: Client Engagement Permissions
     */

    /*
     Start : User Type
     */
    public static final String USER_TYPE_ADMINISTRATOR = "Administrator";
    /*
     End : User Type
     */

    /* Start:Engagement Data Upload Permissions
     */
    public static final String ADD_DOCUMENT_UPLOAD = "Add Document upload";
    public static final String EDIT_DOCUMENT_UPLOAD = "Edit Document upload";
    public static final String VIEW_DOCUMENT_UPLOAD = "View Document upload";
    public static final String DELETE_DOCUMENT_UPLOAD = "Delete Document upload";
    public static final String LOCK_SERVICE_DATA = "Lock Service Data";
    public static final String UNLOCK_SERVICE_DATA = "Unlock Service Data";
    /*End: Engagement Data Upload Permissions
     */

    /* Start:Analyst Validation Module Permissions
     */
    public static final String REVIEW_VULNERABILITY = "Review Vulnerabilities";
    public static final String VIEW_VULNERABILITY = "View Vulnerabilities";
    /* End:Analyst Validation Module Permissions
     */

    /*
     Start : Client reports upload permissions
     */
//    public static final String CLIENT_REPORTS_UPLOAD="Client Reports Upload";
    public static final String CLIENT_REPORTS_UPLOAD="Reporting Documents";
    public static final String UPLOAD_CLIENT_REPORTS = "Upload Client Reports";
    public static final String VIEW_CLIENT_REPORTS = "View Client Reports";
    public static final String PUBLISH_REPORTS = "Publish or Unpublish Reports";
    public static final String VIEW_DASHBOARD = "View Dashboard";
    public static final String PUBLISH_DASHBOARD = "Publish Dashboard";
    /*
     End : Client reports upload permissions
     */

    /*
     Start : Remidiation permissions
     */
    public static final String REMEDIATE_PROJECT_MANAGER = "Remediation Tracking";
    public static final String REMEDIATE = "Remediate";
    public static final String VIEW_REMEDIATE = "View Remediation";

    /*
     End : Remidiation permissions
     */
    
    /*
     Start : Remidiation Module Permissions
     */
    public static final String ADD_REMEDIATION = "Add Remediation";
    public static final String EDIT_REMEDIATION = "Edit Remediation";
    public static final String DELETE_REMEDIATION = "Delete Remediation";
    public static final String ADD_FINDING = "Add Finding";
    public static final String EDIT_FINDING = "Edit Finding";
    public static final String DELETE_FINDING = "Delete Finding";
    public static final String ADD_NOTES = "Add Notes";
    /*
     End : Remidiation Module permissions
     */
    
    
      /*
     Start : Risk Registry Module Permissions
     */
    public static final String EDIT_REGISTRY = "Edit Registry";
    public static final String ADD_FINDING_REGISTRY = "Add Finding";
    public static final String EDIT_FINDING_REGISTRY = "Edit Finding";
    public static final String DELETE_FINDING_REGISTRY = "Delete Finding";
    public static final String ACCEPT_REGISTRY = "Accept Registry";
       /*
     End : Risk Registry Module permissions
     */
    
    /*
     Start Reporting Module Permissions
     */
    public static final String DASHBOARD = "Dashboard";
    public static final String EXECUTIVE_REMEDIATION_DASHBOARD = "Remediation Dashboard";
    public static final String EXECUTIVE_REGISTRY_DASHBOARD = "Risk Registry Dashboard";
    public static final String PERSONAL_REMEDIATION_DASHBOARD = "Personal Remediation Dashboard";
    public static final String PERSONAL_REGISTRY_DASHBOARD = "Personal Risk Registry Dashboard";

    /*
     End Reporting Module Permissions
     */
}
