/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service.impl;

import com.optum.oss.dao.impl.AssessmentDAOImpl;
import com.optum.oss.exception.AppException;
import com.optum.oss.model.AssessmentModel;
import com.optum.oss.model.FindingsModel;
import com.optum.oss.model.SecurityModel;
import com.optum.oss.model.SummaryDropDownModel;
import com.optum.oss.service.AssessmentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rpanuganti
 */
@Service
public class AssessmentServiceImpl implements AssessmentService {

    @Autowired
    private AssessmentDAOImpl assessmentDAO;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<AssessmentModel> topOSCategories(String engagementCode, String serviceCode, String orgSchema) throws Exception {
        return assessmentDAO.topOSCategories(engagementCode, serviceCode, orgSchema);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<AssessmentModel> topRootCauses(AssessmentModel assessmentObj) throws Exception {
        return assessmentDAO.topRootCauses(assessmentObj);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<AssessmentModel> severityList(AssessmentModel assessmentObj) throws Exception {
        return assessmentDAO.severityList(assessmentObj);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List topHosts(String engagementCode, String serviceCode, String orgSchema) throws AppException {
        return assessmentDAO.topHosts(engagementCode, serviceCode, orgSchema);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List vulnerabilitiesbyFrequency(AssessmentModel assessmentObj) throws AppException {
        return assessmentDAO.vulnerabilitiesbyFrequency(assessmentObj);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<AssessmentModel> ssidDetails(AssessmentModel assessmentObj) throws Exception {
        return assessmentDAO.ssidDetails(assessmentObj);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<FindingsModel> getEngagementServiceFindingsData(AssessmentModel assessmentObj) throws AppException {
        return assessmentDAO.getEngagementServiceFindingsData(assessmentObj);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<AssessmentModel> topHitrustDetails(String engagementCode, String serviceCode, String orgSchema) throws AppException {
        return assessmentDAO.topHitrustDetails(engagementCode, serviceCode, orgSchema);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<AssessmentModel> applicationDetails(AssessmentModel assessmentObj) throws AppException {
        return assessmentDAO.applicationDetails(assessmentObj);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<SummaryDropDownModel> ssidDropDownDetails(String engagementCode, String serviceCode, String orgSchema) throws Exception {
        return assessmentDAO.ssidDropDownDetails(engagementCode, serviceCode, orgSchema);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<SummaryDropDownModel> applicatonDropDownDetails(String engagementCode, String serviceCode, String orgSchema) throws Exception {
        return assessmentDAO.applicatonDropDownDetails(engagementCode, serviceCode, orgSchema);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List applicationMostVulnerable(AssessmentModel assessmentObj) throws AppException {
        return assessmentDAO.applicationMostVulnerable(assessmentObj);
    }

    @Override
    public String mostVulnerableChartConstructionService(List list) throws AppException {
        String hostName = "";
        String critical = "";
        String high = "";
        String medium = "";
        String low = "";
        String dataString = "";
        int rowCount = 0;
        try {
            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {

                    List<String> data = (List<String>) list.get(i);
                    rowCount = data.size();
                    for (String string : data) {
                        hostName = hostName + "<category label='" + string + "'/>";
                    }
                }
                if (i == 1) {
                    List<Integer> data = (List<Integer>) list.get(i);
                    for (Integer string : data) {
                        int showvalue = 1;
                        if (string == 0) {
                            showvalue = 0;
                        }
                        critical = critical + "<set value='" + string + "' showValue='" + showvalue + "'/>";
                    }
                }
                if (i == 2) {
                    List<Integer> data = (List<Integer>) list.get(i);
                    for (Integer string : data) {
                        int showvalue = 1;
                        if (string == 0) {
                            showvalue = 0;
                        }
                        high = high + "<set value='" + string + "' showValue='" + showvalue + "'/>";
                    }
                }
                if (i == 3) {
                    List<Integer> data = (List<Integer>) list.get(i);
                    for (Integer string : data) {
                        int showvalue = 1;
                        if (string == 0) {
                            showvalue = 0;
                        }
                        medium = medium + "<set value='" + string + "' showValue='" + showvalue + "'/>";
                    }
                }
                if (i == 4) {
                    List<Integer> data = (List<Integer>) list.get(i);
                    for (Integer string : data) {
                        int showvalue = 1;
                        if (string == 0) {
                            showvalue = 0;
                        }
                        low = low + "<set value='" + string + "' showValue='" + showvalue + "'/>";
                    }
                }
            }
            if (rowCount == 0) {
                dataString = "<chart caption='' maxBarHeight='25' captionFontColor='333333' stack100Percent='1' plottooltext='$seriesName, No.of Findings:$value' captionAlignment='left' bgAlpha='0'  labelDisplay='wrap' maxLabelWidthPercent='40' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='0' chartRightMargin='0'  baseFont='arial'    showYAxisValues='0' labelFontSize='12' valueFontSize='12' labelPadding='13'  labelFontColor='6666666' showPlotBorder='0' yAxisMaxValue='60'  plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='0' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix=''  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' showPercentInToolTip='0' bgratio='0' showcanvasborder='0' animation='1'> </chart>";
            } else {
                dataString = "<chart caption='' maxBarHeight='25' captionFontColor='333333' stack100Percent='1' valueFontColor='#ffffff' plottooltext='$seriesName, No.of Findings:$value' captionAlignment='left' bgAlpha='0'  labelDisplay='wrap' maxLabelWidthPercent='40' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='0' chartRightMargin='0'  baseFont='arial'    showYAxisValues='0' labelFontSize='12' valueFontSize='12' labelPadding='13'  labelFontColor='6666666' showPlotBorder='0' yAxisMaxValue='60'  plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1q' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix=''  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' showPercentInToolTip='0' bgratio='0' showcanvasborder='0' animation='1'> "
                        + "<categories>" + hostName + " </categories>"
                        + "<dataset seriesName='Critical' color='A32A2E'> " + critical + "   </dataset>"
                        + "<dataset seriesName='High' color='E87722'>    " + high + "    </dataset>"
                        + "<dataset seriesName='Medium' color='F2B411'>  " + medium + "    </dataset>"
                        + "<dataset seriesName='Low' color='627D32'>     " + low + "    </dataset>"
                        + "</chart>";
            }

        } catch (Exception e) {

        }
        return dataString;

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<FindingsModel> getServiceFindingsDataForExportCSV(AssessmentModel assessmentObj, SecurityModel securityModel) throws AppException {
        return assessmentDAO.getServiceFindingsDataForExportCSV(assessmentObj, securityModel);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<AssessmentModel> remediationPriorities(AssessmentModel assessmentObj) throws AppException {
        return assessmentDAO.remediationPriorities(assessmentObj);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public SecurityModel applicationDetailsList(AssessmentModel assessmentObj) throws AppException {
        return assessmentDAO.applicationDetailsList(assessmentObj);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public SecurityModel hitrustDetailsList(String engagementCode, String serviceCode, String orgSchema) throws AppException {
        return assessmentDAO.hitrustDetailsList(engagementCode, serviceCode, orgSchema);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public SecurityModel rootCausesList(AssessmentModel assessmentObj) throws Exception {
        return assessmentDAO.rootCausesList(assessmentObj);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public SecurityModel oSCategoriesList(String engagementCode, String serviceCode, String orgSchema) throws AppException {
        return assessmentDAO.oSCategoriesList(engagementCode, serviceCode, orgSchema);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public SecurityModel ssidDetailsList(AssessmentModel assessmentObj) throws Exception {
        return assessmentDAO.ssidDetailsList(assessmentObj);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public SecurityModel remediationPrioritiesList(AssessmentModel assessmentObj) throws AppException {
        return assessmentDAO.remediationPrioritiesList(assessmentObj);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<AssessmentModel> owaspChartData(AssessmentModel assessmentObj) throws Exception {
        return assessmentDAO.owaspChartData(assessmentObj);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public SecurityModel owaspViewList(AssessmentModel assessmentObj) throws Exception {
        return assessmentDAO.owaspViewList(assessmentObj);
    }
}
