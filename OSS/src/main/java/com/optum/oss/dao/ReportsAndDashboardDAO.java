/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao;

import com.optum.oss.dto.AssessmentFindingsCountDTO;
import com.optum.oss.dto.EngagementsDTO;
import com.optum.oss.model.HitrustInfoModel;
import com.optum.oss.dto.PrioritizationMatrixDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.model.AssessmentServicesModel;
import com.optum.oss.model.ChartModel;
import com.optum.oss.model.FindingsModel;
import com.optum.oss.model.RemediationChartModel;
import com.optum.oss.model.RemidiationDataModel;
import com.optum.oss.model.ReportsModel;
import com.optum.oss.model.SecurityModel;
import com.optum.oss.model.SummaryDropDownModel;
import java.util.List;

/**
 *
 * @author mrejeti
 */
public interface ReportsAndDashboardDAO {

    public List<EngagementsDTO> fetchEngagments(final String flag, final long clintOrgId, final long userTypeId, final long userProfileKey) throws AppException;

    public List<AssessmentServicesModel> fetchAssessmentsServicesListByEngagment(final String engCode) throws AppException;

    public AssessmentFindingsCountDTO fetchAssessmentsFindliingCounts(String engCode, final String serviceCode,final String engSchema) throws AppException;

    public List<ReportsModel> fetchEngagementReports(String engagementCode) throws AppException;

    public List<SummaryDropDownModel> fetchServicesListByEngagment(SecurityModel securityModel) throws AppException;

    public List<SummaryDropDownModel> fetchObjectiveListForSummary() throws AppException;

    public List<SummaryDropDownModel> fetchSeverityListForSummary(SecurityModel securityModel) throws AppException;

    public List<ChartModel> getFindingsDoughnutData(SecurityModel securityModel) throws AppException;

    public List<ChartModel> getSummaryBubbleChartData(SecurityModel securityModel) throws AppException;

    public List<FindingsModel> getEngagementFindingsData(SecurityModel securityModel) throws AppException;

    public List<FindingsModel> fetchPrioritizationMatrixGridFindnigs(String engCode,String orgSchema) throws AppException;

    public PrioritizationMatrixDTO fetchPrioritizatioMatrixQuadrantValues(String engCode, String serviceCode,String orgSchema) throws AppException;

    public List<HitrustInfoModel> getHitrustInfoByCtrlCD(String hitrustCd) throws AppException;

    public List<RemediationChartModel> fetchTopRemediationCharts(final String engKey, final String serviceCode,String orgSchema) throws AppException;

    public List<RemidiationDataModel> fetchRemediationVulnerabilities(final String engKey, final String serviceCode,String orgSchema) throws AppException;

    public List getEngagementFindingsDataForExportCSV(SecurityModel securityModel) throws AppException;

    public EngagementsDTO getEngagementDetailsForExportCSV(final SecurityModel securityModel) throws AppException;

    public List<FindingsModel> fetchPrioritizationFilterFindings(final String engKey, String findingId,String orgSchema) throws AppException;
    
    public SecurityModel getSummaryTopRootCauseChart(SecurityModel securityModel) throws AppException;
    
    public List getMatrixFindingsDataForExportCSV(SecurityModel securityModel) throws AppException;
    
    public List<SummaryDropDownModel> fetchRootCauseListForSummary(SecurityModel securityModel) throws AppException;
    
    public String getSchemaNameByEngKey(String engKey) throws AppException;
}
