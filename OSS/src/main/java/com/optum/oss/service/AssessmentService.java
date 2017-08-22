/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service;

import com.optum.oss.exception.AppException;
import com.optum.oss.model.AssessmentModel;
import com.optum.oss.model.FindingsModel;
import com.optum.oss.model.SecurityModel;
import com.optum.oss.model.SummaryDropDownModel;
import java.util.List;

/**
 *
 * @author rpanuganti
 */
public interface AssessmentService {

    public List<AssessmentModel> topOSCategories(String engagementCode, String serviceCode, String orgSchema) throws Exception;
    
     public SecurityModel oSCategoriesList(String engagementCode, String serviceCode, String orgSchema) throws AppException;

    public List<AssessmentModel> topRootCauses(AssessmentModel assessmentObj) throws Exception;
    
    public SecurityModel rootCausesList(AssessmentModel assessmentObj) throws Exception;

    public List<AssessmentModel> severityList(AssessmentModel assessmentObj) throws Exception;

    public List<AssessmentModel> ssidDetails(AssessmentModel assessmentObj) throws Exception;
    
    public SecurityModel ssidDetailsList(AssessmentModel assessmentObjorgSchema) throws Exception;

    public List topHosts(String engagementCode, String serviceCode, String orgSchema) throws AppException;

    public List<AssessmentModel> vulnerabilitiesbyFrequency(AssessmentModel assessmentObj) throws AppException;

    public List<FindingsModel> getEngagementServiceFindingsData(AssessmentModel assessmentObj) throws AppException;

    public List<AssessmentModel> topHitrustDetails(String engagementCode, String serviceCode, String orgSchema) throws AppException;
    
    public SecurityModel hitrustDetailsList(String engagementCode, String serviceCode, String orgSchema) throws AppException;

     public List<AssessmentModel> applicationDetails(AssessmentModel assessmentObj) throws AppException;
     
      public SecurityModel applicationDetailsList(AssessmentModel assessmentObj) throws AppException;
    
    public List<SummaryDropDownModel> ssidDropDownDetails(String engagementCode, String serviceCode, String orgSchema) throws Exception;

     public List<SummaryDropDownModel> applicatonDropDownDetails(String engagementCode, String serviceCode, String orgSchema) throws Exception;
     
     public List applicationMostVulnerable(AssessmentModel assessmentObj) throws AppException;
     
     public String mostVulnerableChartConstructionService(List list) throws AppException;
     
      public List getServiceFindingsDataForExportCSV(AssessmentModel assessmentObj,SecurityModel securityModel) throws AppException;
      
      public List<AssessmentModel> remediationPriorities(AssessmentModel assessmentObj) throws AppException;
      
      public SecurityModel remediationPrioritiesList(AssessmentModel assessmentObj) throws AppException;
      
      public List<AssessmentModel> owaspChartData(AssessmentModel assessmentObj) throws Exception;
      
      public SecurityModel owaspViewList(AssessmentModel assessmentObj) throws Exception;
}
