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
public class ApplicationConstants {

    public static final String APPLICATION_PATH = System.getProperty("OSS_APPLICATION_URL");
    public static final String APPLICATION_IMAGE_PATH = System.getProperty("OSS_APPLICATION_IP_ADDRESS");
    public static final String APP_PSWD_EXPIRY_DAYS = System.getProperty("OSS_PASSWORD_EXPIRY_DAYS");
    public static final String APP_PSWD_EXPIRY_NOTIF_DAYS = System.getProperty("OSS_PASSWORD_EXPIRY_NOTIF_START_DAY");

    public static final String ECG_FILENAME_SEPERATOR = "_";
    public static final String ECG_FILEID_SEPERATOR = "-";

    public static final String DB_INDICATOR_YES = "Y";
    public static final String DB_INDICATOR_NO = "N";

    public static final String EXCEPTION_TYPE_ERROR = "ERROR";
    public static final String EXCEPTION_TYPE_EXCEPTION = "EXCEPTION";

    public static final String DB_INDICATOR_INSERT = "I";
    public static final String DB_INDICATOR_UPDATE = "U";
    public static final String DB_INDICATOR_DELETE = "D";

    public static final long APP_STATUS_INDICATOR_YES = 1;
    public static final long APP_STATUS_INDICATOR_NO = 0;

    //When user tried 3rd attempt, then locked related message is appearing itself.So changed to USER_LOGIN_ATTEMPTS value.BugId: 20762
    public static final long USER_LOGIN_ATTEMPTS = 2;

    public static final long USER_FIRST_TIME_LOGIN_IND = 0;

    public static final String DB_CONSTANT_ROW_STATUS = "Row Status";
    public static final String DB_ROW_STATUS_ACTIVE = "Active";
    public static final String DB_ROW_STATUS_DE_ACTIVE = "Deactive";
    public static final String DB_ROW_STATUS_DELETE = "Delete";
    public static final Integer DB_ROW_STATUS_LOCKED = 1;
    public static final Integer DB_ROW_STATUS_UNLOCKED = 0;
    public static final Integer DB_ROW_STATUS_ACTIVE_VALUE = 1;
    public static final Integer DB_ROW_STATUS_DE_ACTIVE_VALUE = 2;
    public static final Integer DB_ROW_STATUS_DELETE_VALUE = 3;
    public static final Integer DB_ROW_STATUS_PUBLISHED_VALUE = 47;
    public static final Integer DB_ROW_STATUS_NOTPUBLISHED_VALUE = 48;

    public static final String DB_CONSTANT_PERMISSION_TYPE = "Permission Type";
    public static final String DB_PERMISSION_TYPE_MODULE = "Module";
    public static final String DB_PERMISSION_TYPE_MENU = "Menu";
    public static final String DB_PERMISSION_TYPE_SUBMENU = "Submenu";
    public static final String DB_PERMISSION_TYPE_PERMISSION = "Permission";

    public static final String DB_CONSTANT_ORGANIZATION_TYPE = "Organization Type";
    public static final String DB_ORGANIZATIION_TYPE_INTERNAL = "Internal";
    public static final String DB_ORGANIZATIION_TYPE_CLIENT = "Client";
    public static final String DB_ORGANIZATIION_TYPE_PARTNER = "Partner";

    public static final String DB_CONSTANT_ORGANIZATION_INDUSTRY = "Organization Industry";
    public static final String DB_ORGANIZATIION_INDUSTRY_LIFESCIENCES = "Life Sciences";
    public static final String DB_ORGANIZATIION_INDUSTRY_PAYER = "Payer";
    public static final String DB_ORGANIZATIION_INDUSTRY_PROVIDER = "Provider";

    public static final String DB_CONSTANT_SOURCE = "Source";
    public static final String DB_AUTHORITATIVE_SOURCE_SALESFORCE = "Sales Force";
    public static final String DB_AUTHORITATIVE_SOURCE_WEBAPP = "Web App";

    public static final String DB_CONSTANT_USER_TYPE = "User Type";
    public static final String DB_USER_TYPE_INTERNAL = "Internal";
    public static final String DB_USER_TYPE_CLIENT = "Client";
    public static final String DB_USER_TYPE_PARTNER = "Partner";
    public static final String DB_USER_TYPE_ADMIN = "Administrator";

    public static final String DB_CONSTANT_SECURITY_QUESTION = "Security Question";

    public static final String APP_CONSTANT_SAVE = "SAVE";
    public static final String APP_CONSTANT_SUBMIT = "SUBMIT";
    public static final String APP_CONSTANT_CHECK = "CHECK";
    public static final String APP_CONSTANT_VALIDATE = "VALIDATE";

    public static final String DELETE_FLAG = "Delete";
    public static final String UPDATE_FLAG = "Update";
    public static final String SAVE_FLAG = "Save";
    
    
     public static final String MAIL_ADMIN_CLIENT_ONBOARD_SUCCESS = "MAIL_ADMIN_CLIENT_ONBOARD_SUCCESS ";
     public static final String MAIL_ADMIN_CLIENT_ONBOARD_FAIL = "MAIL_ADMIN_CLIENT_ONBOARD_FAIL";
    
    public static final String EMAIL_USER_ACCOUNT_CREATION = "MAIL_USER_ACCOUNT_CREATION";
    public static final String EMAIL_USER_PWD_CREATION = "MAIL_USER_PASSWORD_CREATION";
    public static final String MAIL_USER_PROFILE_UPDATE = "MAIL_USER_PROFILE_PASSWORD_UPDATE"; //  FOR PROFILE UPDATE
    public static final String MAIL_USER_PROFILE_PSWD_UPDATE = "MAIL_USER_PROFILE_PASSWORD_CHANGED"; // FOR PASSWORD UPDATE

    public static final String EMAIL_USER_ACCOUNT_DEACTIVATED = "MAIL_USER_ACCOUNT_DEACTIVATED";
    public static final String EMAIL_USER_ACCOUNT_REACTIVATED = "MAIL_USER_ACCOUNT_REACTIVATED";
    public static final String EMAIL_USER_ACCOUNT_LOCKED = "MAIL_USER_ACCOUNT_LOCKED";
    public static final String EMAIL_USER_ACCOUNT_UNLOCKED = "MAIL_USER_ACCOUNT_UNLOCKED";
    public static final String EMAIL_USER_LOGGEING_FROM_DIFFERENT_BROWSER = "EMAIL_USER_LOGGEING_FROM_DIFFERENT_BROWSER";
    public static final String MAIL_TO_ADMIN_IF_USER_CHANGES_PSWD_FIRST_TIME = "MAIL_TO_ADMIN_IF_USER_CHANGES_PASSWORD_FIRST_TIME";
    public static final String MAIL_ON_NOTIFY_ACCOUNT_PSWD_EXPIRATION = "MAIL_ON_NOTIFY_ACCOUNT_PASSWORD_EXPIRATION";
    public static final String EMAIL_TO_ADMIN_USER_ACCOUNT_LOCKED = "MAIL_TO_ADMIN_USER_ACCOUNT_LOCKED";

    //CLIENT ENGAGEMENT CONSTANTS
    public static final String ENGMNT_STATUS_ENTITY_TYPE = "Engagement Status";
    public static final String ENGMNT_STATUS_OPEN = "Open";

    // - CLIENT ENGAGEMENT CREATION MAIL CONSTATNS
    public static final String MAIL_ENGMNT_CREATION = "MAIL_ENGMNT_CREATION";
    public static final String MAIL_ENGMNT_UPDATION = "MAIL_ENGMNT_UPDATION";
    public static final String MAIL_ASSIGN_CLIENT_ENGMNT_LEAD = "MAIL_ASSIGN_CLIENT_ENGMNT_LEAD";
    public static final String MAIL_ASSIGN_PARTNER_ENGMNT_LEAD = "MAIL_ASSIGN_PARTNER_ENGMNT_LEAD";
    public static final String MAIL_ASSIGN_USER_SCHDULE = "MAIL_ASSIGN_USER_SCHDULE";
    public static final String MAIL_REASSIGN_USER_SCHDULE = "MAIL_REASSIGN_USER_SCHDULE";
    public static final String MAIL_ENGMNT_DUE_DATE_APRCHD = "MAIL_ENGMNT_DUE_DATE_APRCHD";
    public static final String MAIL_ENGMNT_DUE_DATE_PASSED = "MAIL_ENGMNT_DUE_DATE_PASSED";
    public static final String MAIL_TO_ENGMNT_LEAD_DUE_DATE_PASSED = "MAIL_TO_ENGMNT_LEAD_DUE_DATE_PASSED";
    public static final String MAIL_TO_ASSIGNED_USER_SERVICE_DUE_DATE_PASSED = "MAIL_TO_ASSIGNED_USER_SERVICE_DUE_DATE_PASSED";

    // - ENAGAGEMENT DATA UPLOAD MAIL CONSTANTS
    public static final String MAIL_TO_ENGMNTPARTNER_LEAD_FILE_UPLOAD = "MAIL_TO_ENGMNTPARTNER_LEAD_FILE_UPLOAD";
    public static final String MAIL_TO_ENGMNTPARTNER_LEAD_ON_ALL_SERVICES_UPLOAD = "MAIL_TO_ENGMNTPARTNER_LEAD_ON_ALL_SERVICES_UPLOAD";
    public static final String MAIL_TO_ENGMNT_ANALYST_ON_LOCK_SERVICE = "MAIL_TO_ENGMNT_ANALYST_ON_LOCK_SERVICE";
    public static final String MAIL_TO_ENGMNT_ANALYST_ON_UNLOCK_SERVICE = "MAIL_TO_ENGMNT_ANALYST_ON_UNLOCK_SERVICE";
    public static final String MAIL_TO_ANALYST_ON_MALICIOUS_FILE_UPLOAD = "MAIL_TO_FILE_UPLOADED_USER_ON_MALICIOUS_FILE";
    public static final String MAIL_TO_LEAD_USER_ON_MALICIOUS_FILE_UPLOAD = "MAIL_TO_LEAD_USER_ON_MALICIOUS_FILE_UPLOAD";

    // ENGAGMENT PUBLISH MAIL CONSTATNS
    public static final String MAIL_TO_ENGAGEMENT_PARTNER_CLIENT_LEAD_PUBLISHED = "MAIL_ENGMT_PUBLISHED";
    
    // - REMEDIATION MAIL CONSTANTS
    public static final String MAIL_REMEDIATION_NOTIFICATION = "MAIL_REMEDIATION_NOTIFICATION";

    
    //RISK REGISTRY MAIL CONSTANTS
    public static final String MAIL_TO_RISK_REGISTRY_OWNER_ON_NOTIFICATION_DATE = "MAIL_RISK_REGISTRY_NOTIFICATION";
    
    public static final String UPLOADED_FILES_EXISTING = "1";
    public static final String UPLOADED_FILES_NOT_EXISTING = "0";

    public static final String DB_CONSTANT_FILE_STATUS = "File Status";
    public static final String DB_CONSTANT_FILE_STATUS_NEW = "New";
    public static final Integer DB_CONSTANT_FILE_STATUS_KEY_NEW = 36;
    public static final String DB_CONSTANT_FILE_STATUS_TO_BE_PROCESSED = "To Be Processed";
    public static final Integer DB_CONSTANT_FILE_STATUS_KEY_TO_BE_PROCESSED = 37;
    public static final String DB_CONSTANT_FILE_STATUS_SCAN_INPROGRESS = "Scan in Progress";
    public static final Integer DB_CONSTANT_FILE_STATUS_KEY_SCAN_INPROGRESS = 65;
    public static final String DB_CONSTANT_FILE_STATUS_SCAN_FAILED = "Scan Failure";
    public static final Integer DB_CONSTANT_FILE_STATUS_KEY_SCAN_FAILED = 66;

    public static final Integer REPORT_FILE_STATUS_KEY_SCAN_INPROGRESS = 69;
    public static final Integer REPORT_FILE_STATUS_KEY_SCAN_FAILED = 70;

    public static final String DEFAULT_COUNTRY = "United States of America";

    public static final String SUCCESS = "success";

    public static final Integer VERIFY_SECURITY_QUESTIONS_COUNT = 1;

    public static final String DB_CONSTANT_USER_TYPE_LEAD = "L"; //User Type flags for Engagement Leads
    public static final String DB_CONSTANT_USER_TYPE_CLIENT = "C"; //User Type flags for Client Users
    public static final String DB_CONSTANT_USER_TYPE_ANALYST = "A";
    public static final String DB_CONSTANT_ENGAGEMENT_LEAD = "E";
    public static final String DB_CONSTANT_PARTNER_LEAD = "P";//User Type flags for Engagement Analysts
    public static final String DB_CONSTANT_ENG_LEAD_ENG_ANALYST = "B";//User Type flags for Engagement Analysts
    public static final String DB_CONSTANT_REMEDIATION_OWNER = "R";
    public static final String DB_CONSTANT_REGISTRY_OWNER = "G";
   public static final String DB_CONSTANT_REGISTRY_OWNER_FLAG = "RO";
    public static final String DB_CONSTANT_REMEDIATION_ANALYST_FLAG= "RA";

    public static final String DB_CONSTANT_FINDING_SOURCE_NAME = "Source";
    public static final String DB_CONSTANT_DOCUMENT_TYPE = "Document Type";
    public static final Integer DB_CONSTANT_FILE_LOCK_INDICATOR = 1;
    public static final Integer DB_CONSTANT_FILE_UNLOCK_INDICATOR = 0;
    public static final Integer DB_CONSTANT_Service_LOCK_INDICATOR = 1;
    public static final Integer DB_CONSTANT_Service_UNLOCK_INDICATOR = 0;
    public static final String EMPTY_STRING = "";
    public static final String SYMBOL_SPLITING = "@";
    public static final Integer DB_CONSTANT_DATA_TYPE_DATA = 42;

    public static final String USER_TYPE_ENGAGEMENT_LEAD = "EL";
    public static final String USER_TYPE_PARTNER_LEAD = "PL";
    public static final String USER_TYPE_CLIENT_LEAD = "CL";
    public static final String USER_TYPE_ENGAGEMENT_ANALYST = "EA";
    public static final String USER_TYPE_PARTNER_USER = "PU";

    /*
     START : CVSS CALCUALATOR METRIC CONSTANTS
     */
    public static final String CVSS_CALCULATOR_VERSION = "2.0";
    public static final String METRIC_NAME_AV = "Access Vector";
    public static final String METRIC_NAME_AC = "Access Complexity";
    public static final String METRIC_NAME_Au = "Authentication";
    public static final String METRIC_NAME_C = "Confidentiality Impact";
    public static final String METRIC_NAME_I = "Integrity Impact";
    public static final String METRIC_NAME_A = "Availability Impact";
    public static final String METRIC_NAME_E = "Exploitability";
    public static final String METRIC_NAME_RL = "Remediation Level";
    public static final String METRIC_NAME_RC = "Report Confidence";
    public static final String METRIC_NAME_CDP = "Collateral Damage Potential";
    public static final String METRIC_NAME_TD = "Target Distribution";
    public static final String METRIC_NAME_CR = "Confidentiality Requirement";
    public static final String METRIC_NAME_IR = "Integrity Requirement";
    public static final String METRIC_NAME_AR = "Availability Requirement";

    public static final String DB_ROW_STATUS_NOT_REVIEWED = "Not Reviewed";
    public static final String DB_ROW_STATUS_REVIEWED = "Reviewed";
    public static final String DB_ROW_SERVICE_STATUS = "Service Status";
    public static final String DB_SOURCE_NAME = "Manual";
    public static final String DB_OTHER_ROOT_CAUSE_CODE = "OTH";

    /*
     END : CVSS CALCUALATOR METRIC CONSTANTS
     */
    public static final String LDAP_USER_COMPARE_CONSTANT = "uid=";

    private final String USER_AGENT = "Mozilla/5.0";

    public static final String APP_URL = "https://ecgpe.healthtechnologygroup.com";
    public static final String ECG_UPLOAD_URL = System.getProperty("ECG_UPLOAD_URL");
    //public static final String APP_STG_URL="https://ecgse.healthtechnologygroup.com";
    //public static final String APP_PORT="443";
    public static final String ECG_UPLOAD_PORT = System.getProperty("ECG_UPLOAD_PORT");
    public static final String HOST = "149.111.141.221";
    public static final String USER_NAME = "ih0093s";
    //public static final String STG_USER_NAME="ih300f6";
    public static final String ECG_UPLOAD_USER = System.getProperty("ECG_UPLOAD_USER");
    public static final String USER_PSWD = "blXA06xJ";
    //public static final String STG_USER_PSWD="27mkIXoK";
    public static final String ECG_UPLOAD_PSWD = System.getProperty("ECG_UPLOAD_PSWD");

    public static final String SFTP_HOST = "ecgpe.healthtechnologygroup.com";
    public static final String SFTP_USER_NAME = "is00945";
    public static final String SFTP_USER_PSWD = "lhi8BD92";

    /* CVE Constants */
    public static final int COMPLIANCE_ACTIVE = 1;
    public static final int COMPLIANCE_INACTIVE = 2;
    public static final int COMPLIANCE_DELETE = 3;
    public static final int COMPLIANCE_FROM_CVE_ID = 4;
    public static final int COMPLIANCE_FROM_LOOKUP = 5;

    /*Navigation constants*/
    public static final String TO_REVIEW_VULNERABILITES_LISTPAGE = "reviewvulist";//Back Navigation to Finding List from Dashboard (Ref: US41394)
    public static final String TO_VIEW_VULNERABILITES_LISTPAGE = "viewvulist";//Back Navigation to Finding List from Dashboard (Ref: US41394)
    public static final String TO_CLIENTREPORTSUPLOAD_LISTPAGE = "creports";

    public static final String OS_LINUX = "LINUX";
    public static final String OS_WINDOWS = "WINDOWS";
    public static final String OS_SOLARIS = "SOLARIS";
    public static final String OS_MAC_OS = "MAC";

    public static final Integer CVE_COUNT_PER_PAGE = 100;

    public static final String OPENDJ_DISCONNECTED = "OpenDj Connection is Closed";
    
    //This number is max 10 digit number accept DB for finding instance field
    public static final long MAX_FINDING_ID = 2147483647;
    
    public static final String DB_CONSTANT_DASHBOARD_REMEDIATION = "R";
    public static final String DB_CONSTANT_DASHBOARD_NON_REMEDIATION = "U";
    public static final String CONSTANT_ONLY_DASHBOARD_PERMISSION = "ODB";
    
    public static final String USER_STATUS_LOCKED = "Locked";
    public static final String USER_STATUS_DEACTIVE = "Deactive";
    
     public static final String DB_CONSTANT_ORGANIZATION_CREATED = "ORG_CREATED";
     public static final String DB_CONSTANT_ORGANIZATION_SCHEMA_CREATED = "ORG_SCHEMA_CREATED";
     public static final String DB_CONSTANT_ORGANIZATION_SCHEMA_TABLES_CREATED = "ORG_SCHEMA_TABLES_CREATED";
     public static final String DB_CONSTANT_ORGANIZATION_SCHEMA_VIEW_CREATED = "ORG_SCHEMA_VIEW_CREATED";
     public static final String DB_CONSTANT_ORGANIZATION_SCHEMA_ETL_TABLES_CREATED = "ORG_SCHEMA_ETL_TABLES_CREATED";
      
      
    public static final String DB_CONSTANT_ORGANIZATION_NOT_CREATED = "ORG_NOT_CREATED";
    public static final String DB_CONSTANT_ORGANIZATION_SCHEMA_NOT_CREATED = "ORG_SCHEMA_NOT_CREATED";
    public static final String DB_CONSTANT_ORGANIZATION_SCHEMA_TABLES_NOT_CREATED = "ORG_SCHEMA_TABLES_NOT_CREATED";
    public static final String DB_CONSTANT_ORGANIZATION_SCHEMA_VIEW_NOT_CREATED = "ORG_SCHEMA_VIEW_NOT_CREATED";
    public static final String DB_CONSTANT_ORGANIZATION_SCHEMA_ETL_TABLES_NOT_CREATED = "ORG_SCHEMA_ETL_TABLES_NOT_CREATED";
    
    public static final String DB_CONSTANT_ORGANIZATION_SCHEMA_INDICATOR_SUCESS_UPDATED = "ORG_SCHEMA_INDICATOR_SUCESS_UPDATED";
    public static final String DB_CONSTANT_ORGANIZATION_SCHEMA_INDICATOR_SUCESS_NOT_UPDATED = "ORG_SCHEMA_INDICATOR_SUCESS_NOT_UPDATED";
    
      
    public static final String DB_CONSTANT_ORGANIZATION_SCHEMA_INDICATOR_SUCESS = "Y";
    public static final String DB_CONSTANT_ORGANIZATION_SCHEMA_INDICATOR_FAILURE = "N";
    
      public static final String DB_CONSTANT_ORGANIZATION_SCHEMA_NOT_ONBOARDED= "Not Onboarded";
      
      public static final String DB_CONSTANT_REMEDIATION_STATTUS= "Risk Registered";
      public static final String DB_CONSTANT_REMEDIATION_ACCEPTED_STATUS= "Risk Accepted";
      public static final String DB_CONSTANT_REGISTRY_FINDING_COUNT_UPDATE= "RG";
      public static final String DB_CONSTANT_REMEDIATION_FINDING_COUNT_UPDATE = "RM";
       public static final String DB_CONSTANT_REGISTRY_FINDING_STATUS_UPDATE= "RI";
      
    /*REMEDIATION PLAN STATUS*/
    public static final String DB_CONSTANT_PLAN_ITEM_STATUS_CLOSED = "C";
    public static final String DB_CONSTANT_PLAN_ITEM_STATUS_INPROGRESS = "P";
    public static final String DB_CONSTANT_PLAN_ITEM_STATUS_OPEN = "O";
    
    public static final String DB_CONSTANT_CLOSED_FLAG = "CD";
    /*REMEDIATION PLAN STATUS*/
    
    /* Finding Status Codes */
    public static final String DB_CONSTANT_FINDING_CLOSED_STATUS_CODE = "C";
    public static final String DB_CONSTANT_FINDING_CLOSED_STATUS_VALUE = "Closed";
    public static final String DB_CONSTANT_FINDING_CLOSED_WITH_EXCEPTION_STATUS_CODE = "CE";
    public static final String DB_CONSTANT_FINDING_DUPLICATE_STATUS_CODE = "D";
    public static final String DB_CONSTANT_FINDING_FALSE_POSITIVE_STATUS_CODE = "FP";
    public static final String DB_CONSTANT_FINDING_OPEN_STATUS_CODE = "O";
    public static final String DB_CONSTANT_FINDING_VALIDATE_STATUS_CODE = "V";
    /* Finding Status Codes */
    
    /*REMEDIATION STATUS*/
    public static final String DB_CONSTANT_REMEDIATION_OPEN = "RO";
    public static final String DB_CONSTANT_REMEDIATION_IN_PROGRESS = "RI";
    /*REMEDIATION STATUS*/
    
    /*RISK REGISTRY*/
    public static final String DB_CONSTANT_RISK_REGISTERED = "RR";
    public static final String DB_CONSTANT_RISK_ACCEPTED = "RA";
    public static final String DB_CONSTANT_RISK_SHARED = "RS";
    public static final String DB_CONSTANT_RISK_TRANSFER = "RT";
    public static final String DB_CONSTANT_RISK_AVOID = "RV";
    public static final String DB_CONSTANT_RISK_MITIGATE= "RM";
    /*RISK REGISTRY*/
    
    /* ENGAGEMENT TOXIC COMBINATION CONSTANTS */
    public static final int OPTUM_ENGAGEMENT_LEAD_KEY = 4;
    public static final int USER_TYPE_INTERNAL_KEY = 16;
    public static final int USER_TYPE_CLIENT_KEY = 17;
    public static final int USER_TYPE_PARTNER_KEY = 18;
    /* ENGAGEMENT TOXIC COMBINATION CONSTANTS */
    
    /*ROLE KEYS*/
    public static final int PARTNER_LEAD_KEY = 5;
    public static final int PARTNER_ANALYST_KEY = 6;
    /*ROLE KEYS*/
    
    /*ROLES*/
    public static final String ROLE_WEB_APP_ADMIN = "Web App Admin";
    public static final String ROLE_ENGAGEMENT_COORDINATOR = "Engagement Coordinator";
    public static final String ROLE_OPTUM_ENGAGEMENT_LEAD = "Optum Engagement Lead";
    public static final String ROLE_OPTUM_ENGAGEMENT_ANALYST = "Optum Engagement Analyst";
    public static final String ROLE_REMEDIATION_COORDINATOR = "Remediation Coordinator";
    public static final String ROLE_REMEDIATION_ANALYST = "Remediation Analyst";
    public static final String ROLE_REMEDIATION_OWNER = "Remediation Owner";
    public static final String ROLE_RISKREGISTRY_OWNER = "Risk Register Owner";
    /*ROLES*/
    
    public static final String REMEDIATION_OWNER = "R";
    public static final String RISK_OWNER = "K";
    
    /*SEVERITY CONSTANTS*/
    public static final String SEVERITY_CRITICAL = "C";
    public static final String SEVERITY_HIGH = "H";
    public static final String SEVERITY_MEDIUM = "M";
    public static final String SEVERITY_LOW = "L";
    /*SEVERITY CONSTANTS*/
    
    /*STATUS CONSTANTS*/
    public static final String STATUS_ALL = "A";
    public static final String STATUS_VULNERABILITY = "V";
    public static final String STATUS_REMEDIATION = "R";
    public static final String STATUS_RISK_REGISTRY = "K";
    /*STATUS CONSTANTS*/

    /*FINDING UPDATE STATUS CONSTANTS*/
    public static final String FINDING_UPDATE_STATUS_DB_FLAG = "U";
    /*FINDING UPDATE STATUS CONSTANTS */
    
   public static final String DB_CONSTANT_SCAN_TOOL = "Scan Tool";
   public static final String DB_CONSTANT_MANUAL = "Manual";
}
