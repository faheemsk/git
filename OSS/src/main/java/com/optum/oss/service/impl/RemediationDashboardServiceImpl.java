/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service.impl;

import com.optum.oss.dao.impl.RemediationDashboardDAOImpl;
import com.optum.oss.dao.impl.RiskRegistryDAOImpl;
import com.optum.oss.dto.AssessmentFindingsCountDTO;
import com.optum.oss.dto.RemediationDashboardDTO;
import com.optum.oss.dto.RiskRegistryDashboardDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.service.RemediationDashboardService;
import java.util.List;
import org.apache.log4j.Logger;
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
public class RemediationDashboardServiceImpl implements RemediationDashboardService {

    private static final Logger logger = Logger.getLogger(RemediationDashboardServiceImpl.class);

    @Autowired
    private RemediationDashboardDAOImpl remediationDashboardDAO;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public AssessmentFindingsCountDTO fetchRemediationDashBoardFindingCount(String engCode, String engSchema,long userID) throws AppException {
        return remediationDashboardDAO.fetchRemediationDashBoardFindingCount(engCode, engSchema,userID);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public String remediationPlanCompletion(String engCode, String engSchema,long userID) throws AppException {
        RemediationDashboardDTO registryDashboardDTO = remediationDashboardDAO.remediationPlanCompletion(engCode, engSchema,userID);

        String dataString = "";

        if (registryDashboardDTO != null) {
            dataString = "<chart caption='' captionAlignment='left' captionFontColor='333333' showxaxisline='1' xaxislinecolor='#cccccc' bgAlpha='0' labelDisplay='wrap' maxLabelWidthPercent='40' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='0' chartRightMargin='0'    baseFont='arial' showYAxisValues='0' labelFontSize='12' valueFontSize='12' labelPadding='13' enableSmartLabels='1'    smartLabelClearance='-10'  labelFontColor='6666666' showPlotBorder='0' yAxisMaxValue='60' maxBarHeight='15' plotGradientcolor='' startingangle='-110' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix='%'  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' bgratio='0' showcanvasborder='1' canvasBorderAlpha='0' canvasBorderColor='ffffff'  animation='1'>"
                     + "<set value='" + registryDashboardDTO.getPlanStatus_Open() + "%' label='Open ("+registryDashboardDTO.getOpenCount()+")' color='078576'/> "
                    + "<set value='" + registryDashboardDTO.getPlanStatus_Inprogress()+ "%' label='Inprogress ("+registryDashboardDTO.getInprogressCount()+")' color='627D32'/> "
                    + "<set value='" + registryDashboardDTO.getPlanStatus_Closed() + "%' label='Closed ("+registryDashboardDTO.getClosedCount()+")' color='66a36f'  /> "
                    + " </chart>";
        } else {
            dataString = "<chart caption='' captionAlignment='left' captionFontColor='333333' showxaxisline='1' xaxislinecolor='#cccccc' bgAlpha='0' labelDisplay='wrap' maxLabelWidthPercent='40' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='0' chartRightMargin='0'    baseFont='arial' showYAxisValues='0' labelFontSize='12' valueFontSize='12' labelPadding='13' enableSmartLabels='1'    smartLabelClearance='-10'  labelFontColor='6666666' showPlotBorder='0' yAxisMaxValue='60' maxBarHeight='15' plotGradientcolor='' startingangle='-110' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix='%'  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' bgratio='0' showcanvasborder='1' canvasBorderAlpha='0' canvasBorderColor='ffffff'  animation='1'>"
                    + " </chart>";
        }

        return dataString;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public String remediationOwnerDataByPlanStatus(String engCode, String engSchema,long userID) throws AppException {

        List<RemediationDashboardDTO> remediationDatList = remediationDashboardDAO.remediationOwnerDataByPlanStatus(engCode, engSchema,userID);
        String dataString = "";
        String open = "";
        String inprogress = "";
        String closed = "";
        String owner = "";
        int totalCount = 0;
        if (remediationDatList != null) {

            for (RemediationDashboardDTO remediationDTO : remediationDatList) {

                totalCount = totalCount + remediationDTO.getPlanCount();
            }

            for (RemediationDashboardDTO remediationDTO : remediationDatList) {

                int percentage = (int) ((remediationDTO.getPlanCount() * 100) / totalCount);

                if (remediationDTO.getPlanOwner() != null) {
                    owner = owner + "<category label='" + remediationDTO.getPlanOwner() + " (" + remediationDTO.getPlanCount() + ") " + percentage + "%'/>";
                }else{
                      owner = owner + "<category label=' Unassigned (" + remediationDTO.getPlanCount() + ") " + percentage + "%'/>";
                }

                if (remediationDTO.getPlanStatus_Open() > 0) {
                    open = open + "<set value='" + remediationDTO.getPlanStatus_Open() + "'/>";
                } else {
                    open = open + "<set value=''/>";
                }
                if (remediationDTO.getPlanStatus_Inprogress() > 0) {
                    inprogress = inprogress + "<set value='" + remediationDTO.getPlanStatus_Inprogress() + "'/>";
                } else {
                    inprogress = inprogress + "<set value=''/>";
                }
                if (remediationDTO.getPlanStatus_Closed() > 0) {
                    closed = closed + "<set value='" + remediationDTO.getPlanStatus_Closed() + "'/>";
                } else {
                    closed = closed + "<set value=''/>";
                }

            }
        }
        if (remediationDatList != null && remediationDatList.size() > 0) {
            dataString = "<chart caption='' captionFontColor='333333' stack100Percent='1' exportEnabled='0' showxaxisline='1' xaxislinecolor='#cccccc'  captionAlignment='left'  bgAlpha='0' labelDisplay='wrap' maxLabelWidthPercent='40' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='0' chartRightMargin='0'  baseFont='arial' valueFontColor='#ffffff'   showYAxisValues='0' labelFontSize='12' valueFontSize='12' labelPadding='13'  labelFontColor='666666' showPlotBorder='0' yAxisMaxValue='60'  plotSpacePercent='60' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1' showLegend='1' legendBorderAlpha='0' legendPosition='bottom'    numberSuffix='' numberPrefix=''  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' showPercentInToolTip='0' bgratio='0' showcanvasborder='1' canvasBorderAlpha='0' canvasBorderColor='ffffff' animation='1'> "
                    + "<categories>" + owner + "</categories>"
                    + "<dataset seriesName='Open' color='078576'>" + open + "</dataset>"
                    + "<dataset seriesName='InProgress' color='66a36f' >" + inprogress + "</dataset>"
//                    + "<dataset seriesName='Risk Registered' color='9cc366' > " + riskRegister + "</dataset>"
//                    + "<dataset seriesName='Risk Accepted' color='cbda9a' >" + riskAccept + "</dataset>"
                    + "<dataset seriesName='Closed' color='dde7bf'>" + closed + "</dataset>"
                    + "</chart>";
        } else {
            dataString = "<chart caption='' captionFontColor='333333' stack100Percent='1' exportEnabled='0' showxaxisline='1' xaxislinecolor='#cccccc'  captionAlignment='left'  bgAlpha='0' labelDisplay='wrap' maxLabelWidthPercent='40' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='0' chartRightMargin='0'  baseFont='arial' valueFontColor='#ffffff'   showYAxisValues='0' labelFontSize='12' valueFontSize='12' labelPadding='13'  labelFontColor='666666' showPlotBorder='0' yAxisMaxValue='60'  plotSpacePercent='60' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1' showLegend='1' legendBorderAlpha='0' legendPosition='bottom'    numberSuffix='' numberPrefix=''  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' showPercentInToolTip='0' bgratio='0' showcanvasborder='1' canvasBorderAlpha='0' canvasBorderColor='ffffff' animation='1'> "
                    + "</chart>";
        }
        return dataString;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public String remediationPlansByStatusAndSeverity(String engCode, String engSchema,long userID) throws AppException {
        List<RemediationDashboardDTO> remediationDatList = remediationDashboardDAO.remediationPlansByStatusAndSeverity(engCode, engSchema,userID);
        String dataString = "";
        String critical = "";
        String high = "";
        String medium = "";
        String low = "";
        String closed = "";
        String planStatus = "";
        int totalCount = 0;
        int chartMaxHeight = 0;
        int finalChartMaxHeight = 0;
        if (remediationDatList != null) {

            for (RemediationDashboardDTO remediationDTO : remediationDatList) {
                chartMaxHeight = 0;
//                 int percentage = (int) ((remediationDTO.getPlanCount() * 100) / totalCount);
                if (remediationDTO.getRemediationPlanStatus() != null) {
                    planStatus = planStatus + "<category label='" + remediationDTO.getRemediationPlanStatus() + "'/>";
                }

                if (remediationDTO.getRiskSeverityCount_Critical() > 0) {
                    critical = critical + "<set value='" + remediationDTO.getRiskSeverityCount_Critical() + "'/>";
                    chartMaxHeight = chartMaxHeight + remediationDTO.getRiskSeverityCount_Critical();
                } else {
                    critical = critical + "<set value=''/>";
                }
                if (remediationDTO.getRiskSeverityCount_High() > 0) {
                    high = high + "<set value='" + remediationDTO.getRiskSeverityCount_High() + "'/>";
                    chartMaxHeight = chartMaxHeight + remediationDTO.getRiskSeverityCount_High();
                } else {
                    high = high + "<set value=''/>";
                }
                if (remediationDTO.getRiskSeverityCount_Medium() > 0) {
                    medium = medium + "<set value='" + remediationDTO.getRiskSeverityCount_Medium() + "'/>";
                    chartMaxHeight = chartMaxHeight + remediationDTO.getRiskSeverityCount_Medium();
                } else {
                    medium = medium + "<set value=''/>";
                }
                if (remediationDTO.getRiskSeverityCount_Low() > 0) {
                    low = low + "<set value='" + remediationDTO.getRiskSeverityCount_Low() + "'/>";
                    chartMaxHeight = chartMaxHeight + remediationDTO.getRiskSeverityCount_Low();
                } else {
                    low = low + "<set value=''/>";
                }
//                finalChartMaxHeight = chartMaxHeight;

                if (chartMaxHeight > finalChartMaxHeight) {
                    finalChartMaxHeight = chartMaxHeight;
                }

            }
        }
        finalChartMaxHeight = finalChartMaxHeight + 2;
        if (remediationDatList != null && remediationDatList.size() > 0) {
            dataString = "<chart caption='' captionFontColor='333333'  exportEnabled='0' showxaxisline='1' showyaxisline='1' xaxislinecolor='#cccccc' yaxislinecolor='#cccccc'  captionAlignment='left'  bgAlpha='0' labelDisplay='wrap' maxLabelWidthPercent='40' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='1' chartRightMargin='0'  baseFont='arial'   showYAxisValues='1' labelFontSize='12' valueFontSize='12' labelPadding='13'  labelFontColor='666666' showPlotBorder='0' yAxisMaxValue='"+finalChartMaxHeight+"'  plotSpacePercent='75' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1' showLegend='0' showSum='1'  legendBorderAlpha='0' legendPosition='right'    numberSuffix='' numberPrefix=''  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' showPercentInToolTip='0' bgratio='0' showcanvasborder='1' canvasBorderAlpha='0' canvasBorderColor='ffffff' animation='1'> "
                    + "<categories>" + planStatus + "</categories>"
                    + "<dataset seriesName='Critical' color='931919' >" + critical + "</dataset>"
                    + "<dataset seriesName='High' color='f68222' >" + high + "</dataset>"
                    + "<dataset seriesName='Medium' color='ffc000' > " + medium + "</dataset>"
                    + "<dataset seriesName='Low' color='698335'>" + low + "</dataset>"
                    + "</chart>";
        } else {
            dataString = "<chart caption='' captionFontColor='333333'  exportEnabled='0' showxaxisline='1' showyaxisline='1' xaxislinecolor='#cccccc' yaxislinecolor='#cccccc'  captionAlignment='left'  bgAlpha='0' labelDisplay='wrap' maxLabelWidthPercent='40' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='1' chartRightMargin='0'  baseFont='arial'   showYAxisValues='1' labelFontSize='12' valueFontSize='12' labelPadding='13'  labelFontColor='666666' showPlotBorder='0' yAxisMaxValue='60'  plotSpacePercent='75' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1' showLegend='0' showSum='1'  legendBorderAlpha='0' legendPosition='right'    numberSuffix='' numberPrefix=''  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' showPercentInToolTip='0' bgratio='0' showcanvasborder='1' canvasBorderAlpha='0' canvasBorderColor='ffffff' animation='1'> "
                    + "</chart>";
        }
        return dataString;
    }

    @Override
    public String remediationPlansByDaysAndSeverity(String engCode, String engSchema,long userID) throws AppException {
       List<RemediationDashboardDTO> remediationDatList = remediationDashboardDAO.remediationPlansByDaysAndSeverity(engCode, engSchema,userID);
        String dataString = "";
        String critical = "";
        String high = "";
        String medium = "";
        String low = "";
        String daysOpen = "";
        if (remediationDatList != null) {

            for (RemediationDashboardDTO remediationDTO : remediationDatList) {
                if (remediationDTO.getDaysOpenRange() != null) {
                    daysOpen = daysOpen + "<category label='" + remediationDTO.getDaysOpenRange()+ " Days'/>";
                }

                if (remediationDTO.getRiskSeverityCount_Critical() > 0) {
                    critical = critical + "<set value='" + remediationDTO.getRiskSeverityCount_Critical() + "'/>";
                } else {
                    critical = critical + "<set value=''/>";
                }
                if (remediationDTO.getRiskSeverityCount_High() > 0) {
                    high = high + "<set value='" + remediationDTO.getRiskSeverityCount_High() + "'/>";
                } else {
                    high = high + "<set value=''/>";
                }
                if (remediationDTO.getRiskSeverityCount_Medium() > 0) {
                    medium = medium + "<set value='" + remediationDTO.getRiskSeverityCount_Medium() + "'/>";
                } else {
                    medium = medium + "<set value=''/>";
                }
                if (remediationDTO.getRiskSeverityCount_Low() > 0) {
                    low = low + "<set value='" + remediationDTO.getRiskSeverityCount_Low() + "'/>";
                } else {
                    low = low + "<set value=''/>";
                }

            }
        }
        if (remediationDatList != null && remediationDatList.size() > 0) {
            dataString = "<chart caption='' captionFontColor='333333' stack100Percent='1' exportEnabled='0' showxaxisline='1' xaxislinecolor='#cccccc'  captionAlignment='left'  bgAlpha='0' labelDisplay='wrap' maxLabelWidthPercent='40' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='0' chartRightMargin='0'  baseFont='arial'    showYAxisValues='0'  labelFontSize='12' valueFontSize='12' labelPadding='13'  labelFontColor='666666' showPlotBorder='0' yAxisMaxValue='60'  plotSpacePercent='40' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1' showLegend='1' legendBorderAlpha='0' legendPosition='bottom'  showSum='1'   numberSuffix='' numberPrefix=''  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' showPercentInToolTip='0' bgratio='0' showcanvasborder='1' canvasBorderAlpha='0' canvasBorderColor='ffffff' animation='1'>"
                    + "<categories>" + daysOpen + "</categories>"
                    + "<dataset seriesName='Critical' color='931919' >" + critical + "</dataset>"
                    + "<dataset seriesName='High' color='f68222' >" + high + "</dataset>"
                    + "<dataset seriesName='Medium' color='ffc000' > " + medium + "</dataset>"
                    + "<dataset seriesName='Low' color='698335'>" + low + "</dataset>"
                    + "</chart>";
        } else {
            dataString = "<chart caption='' captionFontColor='333333' stack100Percent='1' exportEnabled='0' showxaxisline='1' xaxislinecolor='#cccccc'  captionAlignment='left'  bgAlpha='0' labelDisplay='wrap' maxLabelWidthPercent='40' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='0' chartRightMargin='0'  baseFont='arial'    showYAxisValues='0'  labelFontSize='12' valueFontSize='12' labelPadding='13'  labelFontColor='666666' showPlotBorder='0' yAxisMaxValue='60'  plotSpacePercent='40' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1' showLegend='1' legendBorderAlpha='0' legendPosition='bottom'  showSum='1'   numberSuffix='' numberPrefix=''  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' showPercentInToolTip='0' bgratio='0' showcanvasborder='1' canvasBorderAlpha='0' canvasBorderColor='ffffff' animation='1'>"
                    + "</chart>";
        }
        return dataString;
    }

}
