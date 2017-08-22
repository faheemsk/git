/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service.impl;

import com.optum.oss.dao.impl.RiskRegistryDashboardDAOImpl;
import com.optum.oss.dto.AssessmentFindingsCountDTO;
import com.optum.oss.dto.RiskRegistryDashboardDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.service.RiskRegistryDashboardService;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author rpanuganti
 */
@Service
public class RiskRegistryDashboardServiceImpl implements RiskRegistryDashboardService {

    private static final Logger logger = Logger.getLogger(RemediationProjectManagerServiceImpl.class);

    @Autowired
    private RiskRegistryDashboardDAOImpl riskRegistryDashboardDAO;

    @Override
    public AssessmentFindingsCountDTO fetchRiskRegistryFindingCount(String engCode, String engSchema,long userID) throws AppException {
        return riskRegistryDashboardDAO.fetchRiskRegistryFindingCount(engCode, engSchema,userID);
    }

    @Override
    public String registryDataByResponse(String engCode, String engSchema,long userID) throws AppException {
        RiskRegistryDashboardDTO registryDashboardDTO = riskRegistryDashboardDAO.registryDataByResponse(engCode, engSchema,userID);
        String dataString = "";

        if (registryDashboardDTO != null) {
            dataString = "<chart caption='' captionAlignment='left' captionFontColor='333333' showxaxisline='1' xaxislinecolor='#cccccc' bgAlpha='0' labelDisplay='wrap' maxLabelWidthPercent='40' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='0' chartRightMargin='0'    baseFont='arial' showYAxisValues='0' labelFontSize='12' valueFontSize='12' labelPadding='13' enableSmartLabels='1'    smartLabelClearance='-10'  labelFontColor='6666666' showPlotBorder='0' yAxisMaxValue='60' maxBarHeight='15' plotGradientcolor='' startingangle='-110' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix='%'  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' bgratio='0' showcanvasborder='1' canvasBorderAlpha='0' canvasBorderColor='ffffff'  animation='1'>"
                    + "<set value='" + registryDashboardDTO.getRegistry_Open() + "%' label='Open ("+registryDashboardDTO.getOpenCount()+")' color='078576'/> "
                    + "<set value='" + registryDashboardDTO.getRegistry_Closed() + "%' label='Closed ("+registryDashboardDTO.getClosedCount()+")' color='66a36f'  /> "
                    + " </chart>";
        } else {
            dataString = "<chart caption='' captionAlignment='left' captionFontColor='333333' showxaxisline='1' xaxislinecolor='#cccccc' bgAlpha='0' labelDisplay='wrap' maxLabelWidthPercent='40' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='0' chartRightMargin='0'    baseFont='arial' showYAxisValues='0' labelFontSize='12' valueFontSize='12' labelPadding='13' enableSmartLabels='1'    smartLabelClearance='-10'  labelFontColor='6666666' showPlotBorder='0' yAxisMaxValue='60' maxBarHeight='15' plotGradientcolor='' startingangle='-110' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix='%'  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' bgratio='0' showcanvasborder='1' canvasBorderAlpha='0' canvasBorderColor='ffffff'  animation='1'>"
                    + " </chart>";
        }

        return dataString;
    }

    @Override
    public String registryDataByOwner(String engCode, String engSchema,long userID) throws AppException {
        List<RiskRegistryDashboardDTO> registryDataOwenrList = riskRegistryDashboardDAO.registryDataByOwner(engCode, engSchema,userID);
        String dataString = "";
        String accept = "";
        String transfer = "";
        String mitigated = "";
        String share = "";
        String avoid = "";
        String owner = "";
       int totalCount=0;
 if(registryDataOwenrList != null){
     
     for (RiskRegistryDashboardDTO riskRegistryDashboardDTO : registryDataOwenrList) {

         totalCount = totalCount + riskRegistryDashboardDTO.getRegistryCount();
     }

        for (RiskRegistryDashboardDTO registryDashboardDTO : registryDataOwenrList) {
            
            int percentage=(int)((registryDashboardDTO.getRegistryCount()*100)/totalCount);

            if (registryDashboardDTO.getRegistryOwner() != null) {
                owner = owner + "<category label='"+registryDashboardDTO.getRegistryOwner()+" ("+registryDashboardDTO.getRegistryCount()+") "+percentage+"%'/>";
            }

            if (registryDashboardDTO.getRiskResponseCount_Accept() > 0) {
                accept = accept + "<set value='" + registryDashboardDTO.getRiskResponseCount_Accept() + "'/>";
            } else {
                accept = accept + "<set value=''/>";
            }
            if (registryDashboardDTO.getRiskResponseCount_Transfer() > 0) {
                transfer = transfer + "<set value='" + registryDashboardDTO.getRiskResponseCount_Transfer() + "'/>";
            } else {
                transfer = transfer + "<set value=''/>";
            }
            if (registryDashboardDTO.getRiskResponseCount_mitigated() > 0) {
                mitigated = mitigated + "<set value='" + registryDashboardDTO.getRiskResponseCount_mitigated() + "'/>";
            } else {
                mitigated = mitigated + "<set value=''/>";
            }
            if (registryDashboardDTO.getRiskResponseCount_share() > 0) {
                share = share + "<set value='" + registryDashboardDTO.getRiskResponseCount_share() + "'/>";
            } else {
                share = share + "<set value=''/>";
            }
            if (registryDashboardDTO.getRiskResponseCount_avoid() > 0) {
                avoid = avoid + "<set value='" + registryDashboardDTO.getRiskResponseCount_avoid() + "'/>";
            } else {
                avoid = avoid + "<set value=''/>";
            }

        }
}
        if (registryDataOwenrList != null && registryDataOwenrList.size()>0) {
            dataString = "<chart caption='' captionFontColor='333333' stack100Percent='1' exportEnabled='0' showxaxisline='1' xaxislinecolor='#cccccc'  captionAlignment='left'  bgAlpha='0' labelDisplay='wrap' maxLabelWidthPercent='40' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='0' chartRightMargin='0'  baseFont='arial' valueFontColor='#ffffff'   showYAxisValues='0' labelFontSize='12' valueFontSize='12' labelPadding='13'  labelFontColor='666666' showPlotBorder='0' yAxisMaxValue='60'  plotSpacePercent='60' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1' showLegend='1' legendBorderAlpha='0' legendPosition='bottom'    numberSuffix='' numberPrefix=''  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' showPercentInToolTip='0' bgratio='0' showcanvasborder='1' canvasBorderAlpha='0' canvasBorderColor='ffffff' animation='1'>"
                    + "<categories>" + owner + "</categories>"
                    + "<dataset seriesName='Accept' color='078576'>" + accept + "</dataset>"
                    + "<dataset seriesName='Transfer' color='66a36f' >" + transfer + "</dataset>"
                    + "<dataset seriesName='Mitigated' color='9cc366' > " + mitigated + "</dataset>"
                    + "<dataset seriesName='Share' color='cbda9a' >" + share + "</dataset>"
                    + "<dataset seriesName='Avoid' color='dde7bf'>" + avoid + "</dataset>"
                    + "</chart>";
        } else {
            dataString = "<chart caption='' captionFontColor='333333' stack100Percent='1' exportEnabled='0' showxaxisline='1' xaxislinecolor='#cccccc'  captionAlignment='left'  bgAlpha='0' labelDisplay='wrap' maxLabelWidthPercent='40' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='0' chartRightMargin='0'  baseFont='arial' valueFontColor='#ffffff'   showYAxisValues='0' labelFontSize='12' valueFontSize='12' labelPadding='13'  labelFontColor='666666' showPlotBorder='0' yAxisMaxValue='60'  plotSpacePercent='60' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1' showLegend='1' legendBorderAlpha='0' legendPosition='bottom'    numberSuffix='' numberPrefix=''  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' showPercentInToolTip='0' bgratio='0' showcanvasborder='1' canvasBorderAlpha='0' canvasBorderColor='ffffff' animation='1'>"
                    + "</chart>";
        }
        return dataString;
    }

    @Override
    public String registryDataBySeverity(String engCode, String engSchema,long userID) throws AppException {
        List<RiskRegistryDashboardDTO> registryDataOwenrList = riskRegistryDashboardDAO.registryDataBySeverity(engCode, engSchema,userID);
        String dataString = "";
        String critical = "";
        String high = "";
        String medium = "";
        String low = "";
        String owner = "";
        int totalCount=0;
        if(registryDataOwenrList != null){
            
                
     for (RiskRegistryDashboardDTO riskRegistryDashboardDTO : registryDataOwenrList) {

         totalCount = totalCount + riskRegistryDashboardDTO.getRegistryCount();
     }
        for (RiskRegistryDashboardDTO registryDashboardDTO : registryDataOwenrList) {
int percentage=(int)((registryDashboardDTO.getRegistryCount()*100)/totalCount);
            if (registryDashboardDTO.getRegistryOwner() != null) {
                owner = owner + "<category label='"+registryDashboardDTO.getRegistryOwner()+" ("+registryDashboardDTO.getRegistryCount()+") "+percentage+"%'/>";
            }else{
                owner = owner + "<category label=' Unassigned ("+registryDashboardDTO.getRegistryCount()+") "+percentage+"%'/>";
            }

            if (registryDashboardDTO.getRiskSeverityCount_Critical() > 0) {
                critical = critical + "<set value='" + registryDashboardDTO.getRiskSeverityCount_Critical() + "'/>";
            } else {
                critical = critical + "<set value=''/>";
            }
            if (registryDashboardDTO.getRiskSeverityCount_High() > 0) {
                high = high + "<set value='" + registryDashboardDTO.getRiskSeverityCount_High() + "'/>";
            } else {
                high = high + "<set value=''/>";
            }
            if (registryDashboardDTO.getRiskSeverityCount_Medium() > 0) {
                medium = medium + "<set value='" + registryDashboardDTO.getRiskSeverityCount_Medium() + "'/>";
            } else {
                medium = medium + "<set value=''/>";
            }
            if (registryDashboardDTO.getRiskSeverityCount_Low() > 0) {
                low = low + "<set value='" + registryDashboardDTO.getRiskSeverityCount_Low() + "'/>";
            } else {
                low = low + "<set value=''/>";
            }

        }
        }
        if (registryDataOwenrList != null && registryDataOwenrList.size()>0) {
            dataString = "<chart caption='' captionFontColor='333333' stack100Percent='1' exportEnabled='0' showxaxisline='1' xaxislinecolor='#cccccc' captionAlignment='left'  bgAlpha='0' labelDisplay='wrap' maxLabelWidthPercent='40' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='0' chartRightMargin='0'  baseFont='arial' valueFontColor='#ffffff'   showYAxisValues='0' labelFontSize='12' valueFontSize='12' labelPadding='13'  labelFontColor='666666' showPlotBorder='0' yAxisMaxValue='60'  plotSpacePercent='60' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1' showLegend='1' legendBorderAlpha='0' legendPosition='bottom'    numberSuffix='' numberPrefix=''  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' showPercentInToolTip='0' bgratio='0' showcanvasborder='1' canvasBorderAlpha='0' canvasBorderColor='ffffff' animation='1'> "
                    + "<categories>" + owner + "</categories>"
                    + " <dataset seriesName='Critical' color='931919'>" + critical + "</dataset>"
                    + " <dataset seriesName='High' color='f68222' >" + high + "</dataset>"
                    + " <dataset seriesName='Medium' color='ffc000' > " + medium + "</dataset>"
                    + "<dataset seriesName='Low' color='698335'>" + low + "</dataset>"
                    + "</chart>";
        } else {
            dataString = "<chart caption='' captionFontColor='333333' stack100Percent='1' exportEnabled='0' showxaxisline='1' xaxislinecolor='#cccccc' captionAlignment='left'  bgAlpha='0' labelDisplay='wrap' maxLabelWidthPercent='40' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='0' chartRightMargin='0'  baseFont='arial' valueFontColor='#ffffff'   showYAxisValues='0' labelFontSize='12' valueFontSize='12' labelPadding='13'  labelFontColor='666666' showPlotBorder='0' yAxisMaxValue='60'  plotSpacePercent='60' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1' showLegend='1' legendBorderAlpha='0' legendPosition='bottom'    numberSuffix='' numberPrefix=''  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' showPercentInToolTip='0' bgratio='0' showcanvasborder='1' canvasBorderAlpha='0' canvasBorderColor='ffffff' animation='1'> "
                    + "</chart>";
        }
        return dataString;
    }

    @Override
    public List<RiskRegistryDashboardDTO> getRiskRegistryData(String engCode, String engSchema,String sevCode,long userID) throws AppException {
        return riskRegistryDashboardDAO.getRiskRegistryData(engCode, engSchema,sevCode,userID);
    }

    @Override
    public List riskRegistryDataForExportCSV(String engCode, String engSchema, String flag,long userID) throws AppException {
        return riskRegistryDashboardDAO.riskRegistryDataForExportCSV(engCode, engSchema, flag,userID);
    }

}
