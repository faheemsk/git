/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service.impl;

import com.optum.oss.constants.ExportCSVConstants;
import com.optum.oss.dao.impl.ManageEngagementsDAOImpl;
import com.optum.oss.dao.impl.ReportsAndDashboardDAOImpl;
import com.optum.oss.dto.AssessmentFindingsCountDTO;
import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.dto.EngagementsDTO;
import com.optum.oss.dto.PrioritizationMatrixDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.ReportsAndDashboardHelper;
import com.optum.oss.model.AssessmentServicesModel;
import com.optum.oss.model.ChartModel;
import com.optum.oss.model.FindingsModel;
import com.optum.oss.model.HitrustInfoModel;
import com.optum.oss.model.RemediationChartModel;
import com.optum.oss.model.RemidiationDataModel;
import com.optum.oss.model.ReportsModel;
import com.optum.oss.model.SecurityModel;
import com.optum.oss.model.SummaryDropDownModel;
import com.optum.oss.model.VulnCatagoryModel;
import com.optum.oss.service.ReportsAndDashboardService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mrejeti
 */
@Service
public class ReportsAndDashboardServiceImpl implements ReportsAndDashboardService {

    @Autowired
    private ReportsAndDashboardDAOImpl daoImpl;
    
    @Autowired
    private ReportsAndDashboardHelper reportsAndDashboardHelper;

    @Autowired
    private ManageEngagementsDAOImpl manageEngDaoImpl;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<EngagementsDTO> fetchEngagments(String flag,long clintOrgId, long userTypeId, long userProfileKey) throws AppException {
        return daoImpl.fetchEngagments(flag, clintOrgId, userTypeId, userProfileKey);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<AssessmentServicesModel> fetchAssessmentsServicesListByEngagment(String engCode) throws AppException {
        return daoImpl.fetchAssessmentsServicesListByEngagment(engCode);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public AssessmentFindingsCountDTO fetchAssessmentsFindliingCounts(String engCode, final String serviceCode,final String engSchema) throws AppException {
        return daoImpl.fetchAssessmentsFindliingCounts(engCode, serviceCode,engSchema);
    }

    /**
     * @param engagementCode
     * @return List<ReportsModel>
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<ReportsModel> fetchEngagementReports(String engagementCode) throws AppException {
        return daoImpl.fetchEngagementReports(engagementCode);

    }

    /**
     * @param engCode
     * @param serviceCode
     * @return List<FindingsModel>
     * @throws AppException
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<FindingsModel> fetchPrioritizationMatrixGridFindnigs(String engCode,String orgSchema) throws AppException {
        return daoImpl.fetchPrioritizationMatrixGridFindnigs(engCode,orgSchema);

    }

    /**
     * @param engCode
     * @param serviceCode
     * @return prioritizationMatrixDTO
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public PrioritizationMatrixDTO fetchPrioritizatioMatrixQuadrantValues(String engCode, String serviceCode,String orgSchema) throws AppException {
        return daoImpl.fetchPrioritizatioMatrixQuadrantValues(engCode, serviceCode,orgSchema);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public SecurityModel fetchSummaryDropDownValues(SecurityModel securityModel) throws AppException {
        securityModel.setSeverityListSummary(daoImpl.fetchSeverityListForSummary(securityModel));
//        securityModel.setCatagoryListSummary(daoImpl.fetchObjectiveListForSummary());
        securityModel.setCatagoryListSummary(daoImpl.fetchRootCauseListForSummary(securityModel));
        securityModel.setServiceListSummary(daoImpl.fetchServicesListByEngagment(securityModel));

        return securityModel;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<SecurityModel> getFindingsDoughnutData(SecurityModel securityModel) throws AppException {
        List<ChartModel> chartList = daoImpl.getFindingsDoughnutData(securityModel);
        List<SecurityModel> securityDoughnutList = new ArrayList<>();
        if(null != chartList  && !chartList.isEmpty()){
        for (ChartModel chartModel : chartList) {
            SecurityModel sModel = new SecurityModel();
//            String chartTEXt = "<chart caption='' numDivLines='0' chartBottomMargin='0' bgAlpha='0' chartLeftMargin='0' labelFontSize='12' valueFontSize='12' chartTopMargin='0' chartRightMargin='0'  doughnutRadius='42' centerLabelPadding='-10' enableSmartLabels='1'  baseFont='arial'  smartLabelClearance='-15' centerLabelFontSize='11' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' defaultcenterlabel='" + chartModel.getServiceName() + "' centerlabel='" + chartModel.getServiceName() + "' centerlabelbold='1'  showYAxisValues='0' showPlotBorder='0' yAxisMaxValue='170' plotSpacePercent='50' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='0' showValues='1' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix=''  rightmargin='0' bgcolor='ECF5FF' bgalpha='70' bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff' canvasBorderThickness='1' canvasBorderColor='d7dcd2' showpercentvalues='0' bgratio='0' startingangle='90' animation='1'>    <set value='" + chartModel.getLow() + "' label='Low' color='627D32'  /> <set value='" + chartModel.getMedium() + "' label='Medium' color='F2B411'  /> <set value='" + chartModel.getHigh() + "' label='High' color='E87722' /> <set value='" + chartModel.getCritical() + "' label='Critical' color='A32A2E'/>  </chart>";
//            String chartTEXt = "<chart caption='' numDivLines='0' showTooltip='1' bgAlpha='0'  plottooltext='$label, No.of Findings:$value' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5'  doughnutRadius='60' pieRadius='80' centerLabelPadding='-10'  labelFontSize='12' valueFontSize='12' enableSmartLabels='1'  baseFont='arial'  smartLabelClearance='-15' centerLabelFontSize='12'  defaultcenterlabel='" + chartModel.getServiceName() + "' centerlabel='" + chartModel.getServiceName() + "' centerlabelbold='1'  showYAxisValues='0' showPlotBorder='0' yAxisMaxValue='170' plotSpacePercent='50' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='0' showValues='1' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix='' numberPrefix=''  rightmargin='0'  bgalpha='70' bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff' canvasBorderThickness='1' canvasBorderColor='d7dcd2' showpercentvalues='0' bgratio='0' startingangle='90' animation='1'>";
//            String chartTEXt = "<chart caption='" + chartModel.getServiceName() + "' numDivLines='0' showTooltip='1' captionOnTop='0' bgAlpha='0'  plottooltext='$label, No.of Findings:$value' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5'  doughnutRadius='30' pieRadius='43'  labelFontBold='0' captionFontBold='0' centerLabelPadding='-10'  labelFontSize='10' valueFontSize='10' enableSmartLabels='1'  baseFont='arial'  smartLabelClearance='-28' centerLabelFontSize='14'  defaultcenterlabel='" + chartModel.getTotalFindings() + "' centerlabel='" + chartModel.getTotalFindings()+ "' centerlabelbold='0'   showYAxisValues='0' showPlotBorder='0' yAxisMaxValue='170' plotSpacePercent='50' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='0' showValues='1' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix='' numberPrefix=''  rightmargin='0' bgcolor='ECF5FF' bgalpha='70' bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff' canvasBorderThickness='1' canvasBorderColor='d7dcd2' showpercentvalues='0' bgratio='0' startingangle='90' animation='1'>";
            String chartTEXt ="<chart caption='" + chartModel.getServiceName() + "' maxLabelWidthPercent='2' captionFontSize='11'  numDivLines='0' captionOnTop='0'  doughnutRadius='30' pieRadius='43' plottooltext='$label, No.of Findings:$value' bgAlpha='0' enableSmartLabels='1' labelFontSize='10' valueFontSize='10'  baseFont='arial'  smartLabelClearance='-25'  showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' labelFontBold='0' captionFontBold='0' captiondisplay='wrap' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' centerLabelFontSize='14'  defaultcenterlabel='" + chartModel.getTotalFindings() + "' centerlabel='" + chartModel.getTotalFindings() + "' labeldisplay='wrap'  centerlabelbold='0'  showYAxisValues='0' showPlotBorder='0' yAxisMaxValue='170' plotSpacePercent='50' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='0' showValues='1' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix=''  rightmargin='0'  bgalpha='70' bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff' canvasBorderThickness='1' canvasBorderColor='d7dcd2' showpercentvalues='0' bgratio='0' startingangle='90' animation='1'>";
            if (!chartModel.getLow().equalsIgnoreCase("0")) {
                chartTEXt = chartTEXt + " <set value='" + chartModel.getLow() + "' label='Low' color='627D32'  />";
            }
            if (!chartModel.getMedium().equalsIgnoreCase("0")) {
                chartTEXt = chartTEXt + " <set value='" + chartModel.getMedium() + "' label='Medium' color='F2B411'  /> ";
            }
            if (!chartModel.getHigh().equalsIgnoreCase("0")) {
                chartTEXt = chartTEXt + " <set value='" + chartModel.getHigh() + "' label='High' color='E87722' />";
            }
            if (!chartModel.getCritical().equalsIgnoreCase("0")) {
                chartTEXt = chartTEXt + "<set value='" + chartModel.getCritical() + "' label='Critical' color='A32A2E'/>";
            }

            chartTEXt = chartTEXt + "  </chart>";
            String output = chartModel.getServiceName().replaceAll("\\s+", "");
            sModel.setChartName(output);
            sModel.setChartTEXT(chartTEXt);
            securityDoughnutList.add(sModel);
        }
        }
        return securityDoughnutList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public SecurityModel getSummaryBubbleChartData(SecurityModel securityModel) throws AppException {
        List<ChartModel> chartList = daoImpl.getSummaryBubbleChartData(securityModel);
        String criticalSet = "";
//        String highSet = "";
//        String meduimSet = "";
//        String lowSet = "";
       HashMap qPostionMap =  reportsAndDashboardHelper.quadrantPositionsCreation();
       if(null != chartList){
        for (ChartModel chartList1 : chartList) {
           String quadrantNo = reportsAndDashboardHelper.quadrantSelection(chartList1.getImpactXScore(), chartList1.getProbYScore());
           HashMap quadMap = (HashMap)qPostionMap.get(quadrantNo);
           VulnCatagoryModel vcModel =(VulnCatagoryModel)quadMap.get(Integer.parseInt(chartList1.getVulnCatyCD()));
            criticalSet = criticalSet + "<set x='" + vcModel.getxPosition() + "' y='" + vcModel.getyPosition() + "' z='1' toolText='"+chartList1.getSeverityLevel()+", "+chartList1.getFindingId()+"' name='"+chartList1.getVulnCatyCD()+"' color='000000'  link='JavaScript:getFindingDetails(" + chartList1.getVulnCatyCD() + ","+chartList1.getEllipsedFindingId() +")'/>";
        }
       }

        String chartTEXt = "<chart canvasbgcolor='067c01,ffd200,f40500' is3D='0' basefont='arial' minBubbleRadius='5' valueFontSize='10' valueFontColor='cccccc' plottooltext=''  chartBottomMargin='0' bgAlpha='0' chartLeftMargin='0' labelPadding='-10' alignCaptionWithCanvas='1' canvasbgalpha='100' canvasbgangle='-45' alternateHGridColor='ffffff' showAlternateHGridColor='0'  xAxisName='Business Impact ' yAxisName='Probability ' showLegend='0' legendbgalpha='0' legendborderalpha='0' legendshadow='1'  usePlotGradientColor='0' plotFillAlpha='100'  plotBorderAlpha='70' plotBorderThickness='1' use3DLighting='1' basefontsize='12' xAxisNamePadding='15' xAxisLabelDisplay='auto'  captionhorizontalpadding='0' rotateyaxisname='1' centerYaxisName='0'  numDivLines='2' showAlternateVGridColor='0'  numberPrefix='' bubbleScale='0.20' showLabels='1' showValues='0' showXAxisValues='0' showYAxisValues='0'  yAxisMaxValue='12' xAxisMaxValue='12' bgColor='ffffff' borderThickness=''  canvasBorderThickness='1' canvasBorderColor='cccccc' borderColor='ffffff' >"
                + "<categories verticalLineColor='cccccc'  verticalLineAlpha='100'>"
                + "<category label='Minor' x='1.7' />"
                + "<category label=''  x='4' sL='1' showVerticalLine='1'/>"
                + "<category label='Moderate' x='6' />"
                + "<category label='' x='8' sL='1' showLabels='1' showVerticalLine='1'/>"
                + "<category label='Major' x='9.8' /></categories>"
                + "<dataSet color='A32A2E' seriesName='Critical' showValues='1' >"
                + criticalSet + "</dataSet>"
//                + "<dataSet color='E87722' seriesName='High' showValues='1'>"
//                + highSet + "</dataSet>"
//                + "<dataSet color='F2B411' seriesName='Medium'  showValues='1'>"
//                + meduimSet + "</dataSet>"
//                + "<dataSet color='627D32'  seriesName='Low' showValues='1'>"
//                + lowSet + "</dataSet>"
                + "<trendlines>"
                + "<line startValue='1.9' endValue='1.9'  color='666666'  thickness='0' displayValue='Unlikely' alpha='100' showOnTop='0'/>"
                + "<line startValue='4' endValue='4'  color='cccccc'  thickness='1' displayValue=' ' alpha='100' showOnTop='0'/>"
                + "<line startValue='5.8' endValue='5.8'  color='666666'  thickness='0' displayValue='Possible' alpha='100' showOnTop='0'/>"
                + "<line startValue='8' endValue='8'  color='cccccc'  thickness='1' displayValue=' ' alpha='100' showOnTop='0'/> "
                + "<line startValue='9.8' endValue='9.8'  color='666666'  thickness='0' displayValue='Likely' alpha='100' showOnTop='0'/>"
                + "</trendlines>"
                + "</chart>";

        securityModel.setChartName("chartDivBubbleTriage");
        securityModel.setChartTEXT(chartTEXt);

        return securityModel;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<FindingsModel> getEngagementFindingsData(SecurityModel securityModel) throws AppException {
        return daoImpl.getEngagementFindingsData(securityModel);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<HitrustInfoModel> getHitrustInfoByCtrlCD(String hitrustCd) throws AppException {
        return daoImpl.getHitrustInfoByCtrlCD(hitrustCd);
    }

    /**
     * This method for fetch service based remediation charts
     *
     * @param engKey
     * @param dropDownModel
     * @return List<RemediationModel>
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<RemediationChartModel> fetchTopRemediationCharts(final String engKey, final List<SummaryDropDownModel> dropDownModel,String orgSchema)
            throws AppException {
        String serviceCode = "";
        if (!dropDownModel.isEmpty()) {
            for (SummaryDropDownModel model : dropDownModel) {
                serviceCode = model.getId();
            }
        }
        return daoImpl.fetchTopRemediationCharts(engKey, serviceCode,orgSchema);
    }

    /**
     * This method for fetch vulnerabilities based on engagement code for
     * remediation
     *
     * @param engKey
     * @param dropDownModel
     * @return List<RemediationModel>
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<RemidiationDataModel> fetchRemediationVulnerabilities(final String engKey, final List<SummaryDropDownModel> dropDownModel,String orgSchema)
            throws AppException {
        String serviceCode = "";
        if (!dropDownModel.isEmpty()) {
            for (SummaryDropDownModel model : dropDownModel) {
                serviceCode = model.getId();
            }
        }
        return daoImpl.fetchRemediationVulnerabilities(engKey, serviceCode,orgSchema);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List getEngagementFindingsDataForExportCSV(SecurityModel securityModel) throws AppException {
        return daoImpl.getEngagementFindingsDataForExportCSV(securityModel);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public EngagementsDTO getEngagementDetailsForExportCSV(SecurityModel securityModel) throws AppException {
        return daoImpl.getEngagementDetailsForExportCSV(securityModel);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public ClientEngagementDTO fetchEngagmentDetailsByEngKey(final String engKey) throws AppException {

        ClientEngagementDTO dto = new ClientEngagementDTO();
        dto.setEngagementCode(engKey);
        return manageEngDaoImpl.viewEngagementByEngmtKey(dto);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<FindingsModel> fetchPrioritizationFilterFindings(String engKey, String findingId,String orgSchema) throws AppException {
        return daoImpl.fetchPrioritizationFilterFindings(engKey, findingId,orgSchema);

    }

    /**
     * This method for prioritizationFindingscsvExport
     *
     * @param engKey
     * @param findingId
     * @return List<RemediationModel>
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List prioritizationFindingscsvExport(SecurityModel securityModel) throws AppException {
       return daoImpl.getMatrixFindingsDataForExportCSV(securityModel);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public SecurityModel getSummaryTopRootCauseChart(SecurityModel securityModel) throws AppException {
         return daoImpl.getSummaryTopRootCauseChart(securityModel);
    }

    @Override
    public SecurityModel getExportCSVCheckBoxes() throws AppException {
         SecurityModel securityModel = new SecurityModel();
        List exportList = new ArrayList();
        List exportList1 = new ArrayList();
        List exportList2 = new ArrayList();
        List exportList3 = new ArrayList();

        SummaryDropDownModel summaryDropDownModel1 = new SummaryDropDownModel(ExportCSVConstants.ID, ExportCSVConstants.ID_DB, true);
        exportList.add(summaryDropDownModel1);
        SummaryDropDownModel summaryDropDownModel2 = new SummaryDropDownModel(ExportCSVConstants.FINDING, ExportCSVConstants.FINDING_DB, true);
        exportList.add(summaryDropDownModel2);
        SummaryDropDownModel summaryDropDownModel3 = new SummaryDropDownModel(ExportCSVConstants.DESCRIPTION, ExportCSVConstants.DESCRIPTION_DB, true);
        exportList.add(summaryDropDownModel3);
        SummaryDropDownModel summaryDropDownModel4 = new SummaryDropDownModel(ExportCSVConstants.RECOMMENDATION, ExportCSVConstants.RECOMMENDATION_DB, true);
        exportList.add(summaryDropDownModel4);
        SummaryDropDownModel summaryDropDownModel5 = new SummaryDropDownModel(ExportCSVConstants.SERVICE, ExportCSVConstants.SERVICE_DB, true);
        exportList.add(summaryDropDownModel5);
        SummaryDropDownModel summaryDropDownModel6 = new SummaryDropDownModel(ExportCSVConstants.SOURCE, ExportCSVConstants.SOURCE_DB, true);
        exportList.add(summaryDropDownModel6);
        SummaryDropDownModel summaryDropDownModel7 = new SummaryDropDownModel(ExportCSVConstants.COST_EFFORT, ExportCSVConstants.COST_EFFORT_DB, true);
        exportList.add(summaryDropDownModel7);
        SummaryDropDownModel summaryDropDownModel8 = new SummaryDropDownModel(ExportCSVConstants.DOMAIN, ExportCSVConstants.DOMAIN_DB, true);
        exportList.add(summaryDropDownModel8);
        SummaryDropDownModel summaryDropDownModel9 = new SummaryDropDownModel(ExportCSVConstants.IP_ADDRESS, ExportCSVConstants.IP_ADDRESS_DB, true);
        exportList.add(summaryDropDownModel9);
        SummaryDropDownModel summaryDropDownModel10 = new SummaryDropDownModel(ExportCSVConstants.STATUS, ExportCSVConstants.STATUS_DB, true);
        exportList.add(summaryDropDownModel10);
        SummaryDropDownModel summaryDropDownModel11 = new SummaryDropDownModel(ExportCSVConstants.SCAN_IDENTIFIER, ExportCSVConstants.SCAN_IDENTIFIER_DB, true);
        exportList.add(summaryDropDownModel11);
        SummaryDropDownModel summaryDropDownModel12 = new SummaryDropDownModel(ExportCSVConstants.SCAN_START_DATE, ExportCSVConstants.SCAN_START_DATE_DB, true);
        exportList.add(summaryDropDownModel12);
        
       
        SummaryDropDownModel summaryDropDownModel13 = new SummaryDropDownModel(ExportCSVConstants.SCAN_END_DATE, ExportCSVConstants.SCAN_END_DATE_DB, true);
        exportList1.add(summaryDropDownModel13);
        SummaryDropDownModel summaryDropDownModel14 = new SummaryDropDownModel(ExportCSVConstants.SOFTWARE_NAME, ExportCSVConstants.SOFTWARE_NAME_DB, true);
        exportList1.add(summaryDropDownModel14);
        SummaryDropDownModel summaryDropDownModel15 = new SummaryDropDownModel(ExportCSVConstants.HOST, ExportCSVConstants.HOST_DB, true);
        exportList1.add(summaryDropDownModel15);
        SummaryDropDownModel summaryDropDownModel16 = new SummaryDropDownModel(ExportCSVConstants.URL, ExportCSVConstants.URL_DB, true);
        exportList1.add(summaryDropDownModel16);
         SummaryDropDownModel summaryDropDownModel17 = new SummaryDropDownModel(ExportCSVConstants.OPERATING_SYSTEM, ExportCSVConstants.OPERATING_SYSTEM_DB, true);
        exportList1.add(summaryDropDownModel17);
        SummaryDropDownModel summaryDropDownModel18 = new SummaryDropDownModel(ExportCSVConstants.NETBIOS, ExportCSVConstants.NETBIOS_DB, true);
        exportList1.add(summaryDropDownModel18);
        SummaryDropDownModel summaryDropDownModel19 = new SummaryDropDownModel(ExportCSVConstants.MAC_ADDRESS, ExportCSVConstants.MAC_ADDRESS_DB, true);
        exportList1.add(summaryDropDownModel19);
        SummaryDropDownModel summaryDropDownModel20 = new SummaryDropDownModel(ExportCSVConstants.PORT, ExportCSVConstants.PORT_DB, true);
        exportList1.add(summaryDropDownModel20);
        SummaryDropDownModel summaryDropDownModel21 = new SummaryDropDownModel(ExportCSVConstants.TECHINCAL_DETAILS, ExportCSVConstants.TECHINCAL_DETAILS_DB, true);
        exportList1.add(summaryDropDownModel21);
        SummaryDropDownModel summaryDropDownModel22 = new SummaryDropDownModel(ExportCSVConstants.IMPACT_DETAIL, ExportCSVConstants.IMPACT_DETAIL_DB, true);
        exportList1.add(summaryDropDownModel22);
        SummaryDropDownModel summaryDropDownModel23 = new SummaryDropDownModel(ExportCSVConstants.ROOT_CAUSE_DETAIL, ExportCSVConstants.ROOT_CAUSE_DETAIL_DB, true);
        exportList1.add(summaryDropDownModel23);
        SummaryDropDownModel summaryDropDownModel24 = new SummaryDropDownModel(ExportCSVConstants.OWASP_TOP_TEN, ExportCSVConstants.OWASP_TOP_TEN_DB, true);
        exportList1.add(summaryDropDownModel24);
       
        
        SummaryDropDownModel summaryDropDownModel25 = new SummaryDropDownModel(ExportCSVConstants.CVE_ID, ExportCSVConstants.CVE_ID_DB, true);
        exportList2.add(summaryDropDownModel25);
        SummaryDropDownModel summaryDropDownModel26 = new SummaryDropDownModel(ExportCSVConstants.CVE_DESCRIPTION, ExportCSVConstants.CVE_DESCRIPTION_DB, true);
        exportList2.add(summaryDropDownModel26);
        SummaryDropDownModel summaryDropDownModel27 = new SummaryDropDownModel(ExportCSVConstants.OVERALL_SCORE, ExportCSVConstants.OVERALL_SCORE_DB, true);
        exportList2.add(summaryDropDownModel27);
        SummaryDropDownModel summaryDropDownModel28 = new SummaryDropDownModel(ExportCSVConstants.BASE_SCORE, ExportCSVConstants.BASE_SCORE_DB, true);
        exportList2.add(summaryDropDownModel28);
        SummaryDropDownModel summaryDropDownModel29 = new SummaryDropDownModel(ExportCSVConstants.TEMPORAL_SCORE, ExportCSVConstants.TEMPORAL_SCORE_DB, true);
        exportList2.add(summaryDropDownModel29);
        SummaryDropDownModel summaryDropDownModel30 = new SummaryDropDownModel(ExportCSVConstants.ENVIRONMENTAL_SCORE, ExportCSVConstants.ENVIRONMENTAL_SCORE_DB, true);
        exportList2.add(summaryDropDownModel30);
        SummaryDropDownModel summaryDropDownModel31 = new SummaryDropDownModel(ExportCSVConstants.SEVERITY, ExportCSVConstants.SEVERITY_DB, true);
        exportList2.add(summaryDropDownModel31);
        SummaryDropDownModel summaryDropDownModel32 = new SummaryDropDownModel(ExportCSVConstants.PROBABILITY, ExportCSVConstants.PROBABILITY_DB, true);
        exportList2.add(summaryDropDownModel32);
        SummaryDropDownModel summaryDropDownModel33 = new SummaryDropDownModel(ExportCSVConstants.IMPACT, ExportCSVConstants.IMPACT_DB, true);
        exportList2.add(summaryDropDownModel33);
        SummaryDropDownModel summaryDropDownModel34 = new SummaryDropDownModel(ExportCSVConstants.HITRUST, ExportCSVConstants.HITRUST_DB, true);
        exportList2.add(summaryDropDownModel34);
        SummaryDropDownModel summaryDropDownModel35 = new SummaryDropDownModel(ExportCSVConstants.HIPAA, ExportCSVConstants.HIPAA_DB, true);
        exportList2.add(summaryDropDownModel35);
        SummaryDropDownModel summaryDropDownModel36 = new SummaryDropDownModel(ExportCSVConstants.NIST, ExportCSVConstants.NIST_DB, true);
        exportList2.add(summaryDropDownModel36);
        
       
        SummaryDropDownModel summaryDropDownModel37 = new SummaryDropDownModel(ExportCSVConstants.IRS, ExportCSVConstants.IRS_DB, true);
        exportList3.add(summaryDropDownModel37);
        SummaryDropDownModel summaryDropDownModel38 = new SummaryDropDownModel(ExportCSVConstants.MARS, ExportCSVConstants.MARS_DB, true);
        exportList3.add(summaryDropDownModel38);
        SummaryDropDownModel summaryDropDownModel39 = new SummaryDropDownModel(ExportCSVConstants.SOX, ExportCSVConstants.SOX_DB, true);
        exportList3.add(summaryDropDownModel39);
        SummaryDropDownModel summaryDropDownModel40 = new SummaryDropDownModel(ExportCSVConstants.ISO, ExportCSVConstants.ISO_DB, true);
        exportList3.add(summaryDropDownModel40);
        SummaryDropDownModel summaryDropDownModel41 = new SummaryDropDownModel(ExportCSVConstants.PCI, ExportCSVConstants.PCI_DB, true);
        exportList3.add(summaryDropDownModel41);
        SummaryDropDownModel summaryDropDownModel42 = new SummaryDropDownModel(ExportCSVConstants.FISMA, ExportCSVConstants.FISMA_DB, true);
        exportList3.add(summaryDropDownModel42);
        SummaryDropDownModel summaryDropDownModel43 = new SummaryDropDownModel(ExportCSVConstants.GLBA, ExportCSVConstants.GLBA_DB, true);
        exportList3.add(summaryDropDownModel43);
        SummaryDropDownModel summaryDropDownModel44 = new SummaryDropDownModel(ExportCSVConstants.FED_RAMP, ExportCSVConstants.FED_RAMP_DB, true);
        exportList3.add(summaryDropDownModel44);
        SummaryDropDownModel summaryDropDownModel45 = new SummaryDropDownModel(ExportCSVConstants.CSA, ExportCSVConstants.CSA_DB, true);
        exportList3.add(summaryDropDownModel45);
        

        securityModel.setExportCSVcheckboxListOne(exportList);
        securityModel.setExportCSVcheckboxListTwo(exportList1);
        securityModel.setExportCSVcheckboxListThree(exportList2);
        securityModel.setExportCSVcheckboxListFour(exportList3);

        return securityModel;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public String getSchemaNameByEngKey(String engKey) throws AppException {
        return daoImpl.getSchemaNameByEngKey(engKey);
    }
    
    
}
