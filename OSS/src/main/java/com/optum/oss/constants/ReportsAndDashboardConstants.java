/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.constants;

/**
 *
 * @author mrejeti
 */
public class ReportsAndDashboardConstants {
 
    /// HEADER ACTION NAMES
    public final static String SUMMARY_ACTION_URL = "toEngSummeryPage.htm";
    public final static String ASSESSMENT_ACTION_URL = "switchAssessment.htm";
    public final static String THREAT_HUNTING_ACTION_URL = "toThreatHuntingPage.htm";
    public final static String REPORTS_ACTION_URL = "toReportsPage.htm";
    public final static String PRIORITIZATION_ACTION_URL = "toPrioritizationPage.htm";
    public final static String ROAD_MAP_ACTION_URL = "toRoadMapPage.htm";
    public final static String REMEDIATION_ACTION_URL = "toRemediationPage.htm";
    public final static String ROADMAP_ACTION_URL = "toRoadMap.htm";
    public final static String RISK_REGISTRY_ACTION_URL = "toRegistryDashBoard.htm";
    public final static String REMEDIATION_DASHBOARD_ACTION_URL = "toRemediationDashBoard.htm";

    // SESSION OBJECTS
    public final static String ENGAGEMENT_PACKAGE_TYPE = "engPkgType";
    public final static String ENGAGEMENT_KEY = "engKey";
    public final static String ENGAGEMENT_NAME = "engName";
    public final static String ENGAGEMENT_PACKAGE_NAME = "engPkgName";
    public final static String ENGAGEMENT_CODE="engCode";
    public final static String ENGAGEMENT_CLIENT_ORG_NAME="clientOrgName";
    public final static String ENGAGEMENT_OBJECT = "engagementObject";
    //PACKAGE TYPES
    public final static String PACKAGE_TYPE_AS_HC = "HC";
    public final static String PACKAGE_TYPE_AS_TR = "TR";
    public final static String PACKAGE_TYPE_AS_RT = "RT";
    public final static String PACKAGE_TYPE_AS_GW = "GW";
    public final static String PACKAGE_TYPE_AS_SW = "SW";

    //Service Code Constants
    public final static String SERVICE_CODE = "scode";
    public final static String SERVICE_ENG_CODE = "sengode";
    public final static String SERVICE_CODE_ARCHITECTURE_COMPLIANCE_ASSESSMENT = "AC";
    public final static String SERVICE_CODE_APPLICATION_VULNERABILITY_ASSESSMENT = "AV";
    public final static String SERVICE_CODE_NETWORK_VULNERABILITY_ASSESSMENT = "NV";
    public final static String SERVICE_CODE_PENETRATION_TESTING = "PT";
    public final static String SERVICE_CODE_LIMITED_REDTEAM_ASSESSMENT= "RT";
    public final static String SERVICE_CODE_SECURITY_RISK_ASSESSMENT_CODE= "SR";
    public final static String SERVICE_CODE_THREATHUNTING= "TH";
    public final static String SERVICE_CODE_WIRELESS_RISK_ASSESSMENT= "WR";
    
    //Service Names Constants
    public final static String SERVICE_ARCHITECTURE_COMPLIANCE_ASSESSMENT = "Architecture Compliance Assessment";
    public final static String SERVICE_APPLICATION_VULNERABILITY_ASSESSMENT = "Application";
    public final static String SERVICE_NETWORK_VULNERABILITY_ASSESSMENT = "Network";
    public final static String SERVICE_PENETRATION_TESTING = "Penetration Testing";
    public final static String SERVICE_LIMITED_REDTEAM_ASSESSMENT = "Red Team";
    public final static String SERVICE_SECURITY_RISK_ASSESSMENT_CODE = "Risk Assessment";
    public final static String SERVICE_THREATHUNTING = "Threat Hunting (Cyber Intelligence Services)";
    public final static String SERVICE_WIRELESS_RISK_ASSESSMENT = "Wireless";
    public final static String SERVICE_NAME_THREATHUNTING = "Threat Hunting";
    
    //Service DB Constants
    public final static String DB_CONTANT_SERVICE_SSID = "SS";
    public final static String DB_CONTANT_SERVICE_APPLICATION = "AP";
    public final static String DB_CONTANT_SERVICE_ALL = "AL";
    public final static String DB_CONTANT_SELECTED_VALUE = "";
     public final static String DB_CONTANT_SERVICE_NETWORK = "NW";
    
    //COLOR CONSTANTS Severity
    public final static String COLOR_CODE_CRITICAL = "A32A2E";
    public final static String COLOR_CODE_HIGH = "E87722";
    public final static String COLOR_CODE_MEDIUM = "F2B411";
    public final static String COLOR_CODE_LOW = "627D32";
    
     //COLOR CONSTANTS
    public final static String FINDING_CRITICAL = "CRITICAL";
    public final static String FINDING_HIGH = "HIGH";
    public final static String FINDING_MEDIUM = "MEDIUM";
    public final static String FINDING_LOW = "LOW";

    //COLOR CONSTANTS RootCause
    public final static String COLOR_CODE_ROOTCAUSE_ONE = "523995";
    public final static String COLOR_CODE_ROOTCAUSE_TWO = "6a6ab3";
    public final static String COLOR_CODE_ROOTCAUSE_THREE = "8d99cd";
    public final static String COLOR_CODE_ROOTCAUSE_FOUR = "bbc0e9";
    
    
    //COLOR CONSTANTS RootCause
    public final static String COLOR_CODE_HITRUST_ONE = "078576";
    public final static String COLOR_CODE_HITRUST_TWO = "66a36f";
    public final static String COLOR_CODE_HITRUST_THREE = "9cc366";
    public final static String COLOR_CODE_HITRUST_FOUR = "cbda9a";
    
    
    //COLOR CONSTANTS RootCause
    public final static String COLOR_CODE_OS_ONE = "1965ae";
    public final static String COLOR_CODE_OS_TWO = "4b8ab0";
    public final static String COLOR_CODE_OS_THREE = "66b2b1";
    public final static String COLOR_CODE_OS_FOUR = "a0d3c0";
    
     //COLOR CONSTANTS Remediation Priorities  
       public final static String COLOR_CODE_Remediation_ONE = "66b2b1";
    
    // DB STATUS CONSTANTS
    public final static String DB_STATUS_AS_PUBLISHED = "Published";
    public final static long DB_STATUS_KEY_AS_PUBLISHED = 68;
    
    public final static String APP_SSID = "appSSID";
    
    public final static String ENG_SCHEMA = "engSchema";
    
    //Download csv Constants
    public final static String DOWNLOAD_ASSESSMENT = "Assessment";
    public final static String DOWNLOAD_SUMMARY = "Summary";
    public final static String DOWNLOAD_REMEDIATION = "Remediation";
    public final static String DOWNLOAD_PRIORITIZATIONMATRIX = "PrioritizationMatrix";
    public final static String DOWNLOAD_ROADMAP = "RoadMap";
    public final static String DOWNLOAD_RISKREGISTRY = "RiskRegistry";
    
        
    //Road MAP COnstants
    public final static int ROAD_MAP_CHART_X_AXIS_MAX_VALUE = 12;
    public final static int ROAD_MAP_CHART_Y_AXIS_MAX_VALUE= 12;
    public final static String ROAD_MAP_CHART_X_AND_Y_SPLIT_CONSTANT ="@";
    
      //COLOR CONSTANTS
    public final static String COST_EFFORT_HIGH = "HIGH";
    public final static String COST_EFFORT_MEDIUM = "MEDIUM";
    public final static String COST_EFFORT_LOW = "LOW";
  
    //COLOR CONSTANTS
    public final static float ROADMAP_HIGH_COST_EFFORT_BUBBLE_SIZE =0.15f;
    public final static float ROADMAP_MEDIUM_COST_EFFORT_BUBBLE_SIZE =0.11f;
    public final static float ROADMAP_LOW_COST_EFFORT_BUBBLE_SIZE =0.07f;
}
