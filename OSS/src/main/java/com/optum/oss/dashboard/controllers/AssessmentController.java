/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dashboard.controllers;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.constants.ReportsAndDashboardConstants;
import com.optum.oss.constants.SessionConstants;
import com.optum.oss.dto.EngagementsDTO;
import com.optum.oss.dto.ExceptionDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.CSVFileCreationHelper;
import com.optum.oss.model.AssessmentModel;
import com.optum.oss.model.FindingsModel;
import com.optum.oss.model.SecurityModel;
import com.optum.oss.model.SummaryDropDownModel;
import com.optum.oss.service.impl.AssessmentServiceImpl;
import com.optum.oss.service.impl.ReportsAndDashboardServiceImpl;
import com.optum.oss.util.CommonUtil;
import com.optum.oss.util.EncryptDecrypt;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author rpanuganti
 */
@Controller
public class AssessmentController {

    private static final Logger logger = Logger.getLogger(AssessmentController.class);
    /*
     Start : Autowiring the required Class instances
     */
    @Resource(name = "myProperties")
    private Properties myProperties;

    @Autowired
    private AssessmentServiceImpl assessmentService;

    @Autowired
    private ReportsAndDashboardServiceImpl reportsAndDashboardService;

    @Autowired
    private ReportsAndDashboardServiceImpl serviceImpl;

    @Autowired
    private CSVFileCreationHelper csvFileCreation;

    @Autowired
    private EncryptDecrypt encryptDecrypt;

    @Autowired
    private CommonUtil commonUtil;
    /*
     End : Autowiring the required Class instances
     */

    @RequestMapping(value = "/assessmentTop5Carts.htm", method = RequestMethod.GET)
    public @ResponseBody
    List<String> assessmentTop5Carts(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String engCode = "";
        String serviceCode = "";
        String data = "";
        List<String> retLi = new LinkedList<String>();
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if (null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                orgSchema = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
            }

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }
            List list = assessmentService.topHosts(engCode, serviceCode, orgSchema);
            data = assessmentService.mostVulnerableChartConstructionService(list);
            if (!StringUtils.isEmpty(data)) {
                data = commonUtil.encodeHTMLEntities(data);
                retLi.add(data);
                return retLi;
            }
        } catch (Exception e) {
            logger.debug("Exceptionoccured : osData() : " + e.getMessage());

        }
        return retLi;
    }

    @RequestMapping(value = "/osData.htm", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SecurityModel osData(HttpServletRequest req, HttpServletResponse res) {
        List<AssessmentModel> engList = new ArrayList<AssessmentModel>();
        String engCode = "";
        String serviceCode = "";
        HttpSession session = req.getSession();
        SecurityModel data = null;
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if (null != req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                orgSchema = req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
            
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
            }

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }
            data = assessmentService.oSCategoriesList(engCode, serviceCode, orgSchema);
        } catch (Exception e) {
            logger.debug("Exceptionoccured : osData() : " + e.getMessage());
        }
        return data;
    }

    @RequestMapping(value = "/rootCauseData.htm", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SecurityModel rootCauseData(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String engCode = "";
        String serviceCode = "";
        AssessmentModel assessmentModel = new AssessmentModel();
        List<AssessmentModel> rootList = null;
        SecurityModel data = null;
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if (null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                orgSchema = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
            
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
            }

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }
            assessmentModel.setEngagementCode(engCode);
            assessmentModel.setServiceCode(serviceCode);
            assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
            assessmentModel.setSelectedString(ReportsAndDashboardConstants.DB_CONTANT_SELECTED_VALUE);
            assessmentModel.setOrgSchema(orgSchema);
            data = assessmentService.rootCausesList(assessmentModel);
        } catch (Exception e) {
            logger.debug("Exceptionoccured : rootCauseData() : " + e.getMessage());
        }
        return data;
    }

    @RequestMapping(value = "/topRootCauses.htm", method = RequestMethod.GET)
    public @ResponseBody
    List<String> topRootCauses(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String rootString = "";
        String engCode = "";
        String serviceCode = "";
        AssessmentModel assessmentModel = new AssessmentModel();
        String data = "";
        List<String> retLi = new LinkedList<String>();
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if (null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                orgSchema = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
            
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
            }

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }
            assessmentModel.setEngagementCode(engCode);
            assessmentModel.setServiceCode(serviceCode);
            assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
            assessmentModel.setSelectedString(ReportsAndDashboardConstants.DB_CONTANT_SELECTED_VALUE);
            assessmentModel.setOrgSchema(orgSchema);
            List<AssessmentModel> rootList = assessmentService.topRootCauses(assessmentModel);
            int count = 0;
            String colorCode = "";
            for (AssessmentModel assessmentDto : rootList) {
                if (count == 0) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_ROOTCAUSE_ONE;
                } else if (count == 1) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_ROOTCAUSE_TWO;
                } else if (count == 2) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_ROOTCAUSE_THREE;
                } else if (count == 3) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_ROOTCAUSE_FOUR;
                }
                rootString = rootString + "<set value='" + assessmentDto.getValue() + "'  label='" + assessmentDto.getLabel() + "' color='" + colorCode + "'/>";
                count++;
                if (count == 4) {
                    break;
                }
            }
            data = "<chart caption='' captionFontColor='333333' captionAlignment='left'  bgAlpha='0' labelDisplay='wrap' maxLabelWidthPercent='30' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='10' chartRightMargin='0'  baseFont='arial'    showYAxisValues='0' labelFontSize='12' valueFontSize='12' labelPadding='13'  labelFontColor='6666666' showPlotBorder='0' yAxisMaxValue='60' maxBarHeight='15' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix=''  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' bgratio='0' showcanvasborder='1' canvasBorderAlpha='0' canvasBorderColor='ffffff' animation='1'> "
                    + rootString + "</chart>";
            if (!StringUtils.isEmpty(data)) {
                data = commonUtil.encodeHTMLEntities(data);
                retLi.add(data);
                return retLi;
            }
        } catch (Exception e) {
            logger.debug("Exceptionoccured : topRootCauses() : " + e.getMessage());

        }
        return retLi;
    }

    @RequestMapping(value = "/severityDetails.htm", method = RequestMethod.GET)
    public @ResponseBody
    List<String> severityDetails(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String severityString = "";
        String rootString = "";
        String engCode = "";
        String serviceCode = "";
        String data = "";
        List<String> retLi = new LinkedList<String>();
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if (null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                orgSchema = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
            
            AssessmentModel assessmentObj = new AssessmentModel();
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
            }

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }
            assessmentObj.setEngagementCode(engCode);
            assessmentObj.setServiceCode(serviceCode);
            assessmentObj.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
            assessmentObj.setSelectedString(ReportsAndDashboardConstants.DB_CONTANT_SELECTED_VALUE);
            assessmentObj.setOrgSchema(orgSchema);
            List<AssessmentModel> rootList = assessmentService.severityList(assessmentObj);
            for (AssessmentModel assessmentDto : rootList) {
                String colorCode = "";
                if (ReportsAndDashboardConstants.FINDING_CRITICAL.equalsIgnoreCase(assessmentDto.getLabel())) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_CRITICAL;
                } else if (ReportsAndDashboardConstants.FINDING_HIGH.equalsIgnoreCase(assessmentDto.getLabel())) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_HIGH;
                } else if (ReportsAndDashboardConstants.FINDING_MEDIUM.equalsIgnoreCase(assessmentDto.getLabel())) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_MEDIUM;
                } else {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_LOW;
                }
                severityString = severityString + "<set value='" + assessmentDto.getValue() + "'  label='" + assessmentDto.getLabel() + "' color='" + colorCode + "'/>";
            }
            data = "<chart caption='' captionAlignment='left' captionFontColor='333333' bgAlpha='0' labelDisplay='wrap' maxLabelWidthPercent='40' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='10' chartRightMargin='0'   baseFont='arial'    showYAxisValues='0' labelFontSize='12' valueFontSize='12' labelPadding='13'  labelFontColor='6666666' showPlotBorder='0' yAxisMaxValue='60' maxBarHeight='15' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix=''  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' bgratio='0' showcanvasborder='1' canvasBorderAlpha='0' canvasBorderColor='ffffff' animation='1'>   "
                    + severityString + "</chart>";
            if (!StringUtils.isEmpty(data)) {
                data = commonUtil.encodeHTMLEntities(data);
                retLi.add(data);
                return retLi;
            }
        } catch (Exception e) {
            logger.debug("Exceptionoccured : severityDetails() : " + e.getMessage());

        }
        return retLi;
    }

    @RequestMapping(value = "/topOSDetails.htm", method = RequestMethod.GET)
    public @ResponseBody
    List<String> topOSDetails(HttpServletRequest request, HttpServletResponse response) {
        String osString = "";
        String engCode = "";
        String serviceCode = "";
        HttpSession session = request.getSession();
        List<String> retLi = new LinkedList<String>();
        String data = "";
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if (null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                orgSchema = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
            
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
            }

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }
            List<AssessmentModel> rootList = assessmentService.topOSCategories(engCode, serviceCode, orgSchema);
            int count = 0;
            String colorCode = "";

            for (AssessmentModel assessmentDto : rootList) {
                if (count == 0) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_OS_ONE;
                } else if (count == 1) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_OS_TWO;
                } else if (count == 2) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_OS_THREE;
                } else if (count == 3) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_OS_FOUR;
                }
                osString = osString + "<set value='" + assessmentDto.getValue() + "'  label='" + assessmentDto.getLabel() + "' color='" + colorCode + "'/>";
                count++;
                if (count == 4) {
                    break;
                }
            }
            data = "<chart caption='' captionAlignment='left' captionFontColor='333333' bgAlpha='0' labelDisplay='wrap' maxLabelWidthPercent='40' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='10' chartRightMargin='0'    baseFont='arial'    showYAxisValues='0' labelFontSize='12' valueFontSize='12' labelPadding='13'  labelFontColor='6666666' showPlotBorder='0' yAxisMaxValue='60' maxBarHeight='15' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix=''  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' bgratio='0' showcanvasborder='1' canvasBorderAlpha='0' canvasBorderColor='ffffff' animation='1'> "
                    + osString + "</chart>";
            if (!StringUtils.isEmpty(data)) {
                data = commonUtil.encodeHTMLEntities(data);
                retLi.add(data);
            }
        } catch (Exception e) {
            logger.debug("Exceptionoccured : topOSDetails() : " + e.getMessage());
        }
        return retLi;
    }

    @RequestMapping(value = "/topssidDetails.htm", method = RequestMethod.GET)
    public @ResponseBody
    List<String> topssidDetails(HttpServletRequest request, HttpServletResponse response) {
        String osString = "";
        String engCode = "";
        String serviceCode = "";
        HttpSession session = request.getSession();
        String data = "";
        List<String> retLi = new LinkedList<String>();
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if (null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                orgSchema = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
            
            AssessmentModel assessmentModel = new AssessmentModel();
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
            }

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }
            assessmentModel.setEngagementCode(engCode);
            assessmentModel.setServiceCode(serviceCode);
            assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
            assessmentModel.setSelectedString(ReportsAndDashboardConstants.DB_CONTANT_SELECTED_VALUE);
            assessmentModel.setOrgSchema(orgSchema);
            List<AssessmentModel> ssidList = assessmentService.ssidDetails(assessmentModel);
            int count = 0;
            String colorCode = "";
            for (AssessmentModel assessmentDto : ssidList) {
                if (count == 0) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_OS_ONE;
                } else if (count == 1) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_OS_TWO;
                } else if (count == 2) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_OS_THREE;
                } else if (count == 3) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_OS_FOUR;
                }
                osString = osString + "<set value='" + assessmentDto.getValue() + "'  label='" + assessmentDto.getLabel() + "' color='" + colorCode + "'/>";
                count++;
                if (count == 4) {
                    break;
                }
            }
//         relace chart opetions with ssid chart  
            data = "<chart caption='' captionAlignment='left' captionFontColor='333333' bgAlpha='0' labelDisplay='wrap' maxLabelWidthPercent='25' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='10' chartRightMargin='0'    baseFont='arial'    showYAxisValues='0' labelFontSize='12' valueFontSize='12' labelPadding='13'  labelFontColor='6666666' showPlotBorder='0' yAxisMaxValue='100' maxBarHeight='15' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix=''  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' bgratio='0' showcanvasborder='1' canvasBorderAlpha='0' canvasBorderColor='ffffff' animation='1'>"
                    + osString + "</chart>";
            if (!StringUtils.isEmpty(data)) {
                data = commonUtil.encodeHTMLEntities(data);
                retLi.add(data);
                return retLi;
            }
        } catch (Exception e) {
            logger.debug("Exceptionoccured : topssidDetails() : " + e.getMessage());

        }
        return retLi;
    }

    @RequestMapping(value = "/ssidDetails.htm", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SecurityModel ssidDetails(HttpServletRequest request, HttpServletResponse response) {
        String engCode = "";
        String serviceCode = "";
        HttpSession session = request.getSession();
        AssessmentModel assessmentModel = new AssessmentModel();
        SecurityModel data = null;
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if (null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                orgSchema = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
            
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
            }

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }
            assessmentModel.setEngagementCode(engCode);
            assessmentModel.setServiceCode(serviceCode);
            assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
            assessmentModel.setSelectedString(ReportsAndDashboardConstants.DB_CONTANT_SELECTED_VALUE);
            assessmentModel.setOrgSchema(orgSchema);

            data = assessmentService.ssidDetailsList(assessmentModel);
        } catch (Exception e) {
            logger.debug("Exceptionoccured : ssidDetails() : " + e.getMessage());
        }
        return data;
    }

    @RequestMapping(value = "/topHitrustDetails.htm", method = RequestMethod.GET)
    public @ResponseBody
    List<String> topHitrustDetails(HttpServletRequest request, HttpServletResponse response) {
        String osString = "";
        String engCode = "";
        String serviceCode = "";
        HttpSession session = request.getSession();
        String data = "";
        List<String> retLi = new LinkedList<String>();
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if (null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                orgSchema = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
            
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
            }

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }

            List<AssessmentModel> ssidList = assessmentService.topHitrustDetails(engCode, serviceCode, orgSchema);
            int count = 0;
            String colorCode = "";
            for (AssessmentModel assessmentDto : ssidList) {
                if (count == 0) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_HITRUST_ONE;
                } else if (count == 1) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_HITRUST_TWO;
                } else if (count == 2) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_HITRUST_THREE;
                } else if (count == 3) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_HITRUST_FOUR;
                }
                osString = osString + "<set value='" + assessmentDto.getValue() + "'  label='" + assessmentDto.getLabel() + "' color='" + colorCode + "'/>";
                count++;
                if (count == 4) {
                    break;
                }
            }

//         relace chart opetions with ssid chart  
            data = "<chart caption='' captionFontColor='333333' captionAlignment='left' bgAlpha='0'  labelDisplay='wrap' maxLabelWidthPercent='24' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='10' chartRightMargin='0'  baseFont='arial'    showYAxisValues='0' labelFontSize='12' valueFontSize='12' labelPadding='13'  labelFontColor='6666666' showPlotBorder='0' yAxisMaxValue='60' maxBarHeight='15' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix=''  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' bgratio='0' showcanvasborder='1' canvasBorderAlpha='0' canvasBorderColor='ffffff' animation='1'> "
                    + osString + "</chart>";
            if (!StringUtils.isEmpty(data)) {
                data = commonUtil.encodeHTMLEntities(data);
                retLi.add(data);
                return retLi;
            }
        } catch (Exception e) {
            logger.debug("Exceptionoccured : topHitrustDetails() : " + e.getMessage());

        }
        return retLi;
    }

    @RequestMapping(value = "/hitrustDetails.htm", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SecurityModel hitrustDetails(HttpServletRequest request, HttpServletResponse response) {
        String osString = "";
        String engCode = "";
        String serviceCode = "";
        HttpSession session = request.getSession();
        List<AssessmentModel> ssidList = null;
        SecurityModel data = null;
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if (null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                orgSchema = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
            
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
            }

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }

            data = assessmentService.hitrustDetailsList(engCode, serviceCode, orgSchema);
        } catch (Exception e) {
            logger.debug("Exceptionoccured : hitrustDetails() : " + e.getMessage());
        }
        return data;
    }

    @RequestMapping(value = "/assessmentPage")
    public ModelAndView toAssessmentPage(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("engcd") String engcd,@ModelAttribute("srvcd") String srvcd) {
        HttpSession session = request.getSession();
        AssessmentModel assessmentModel = new AssessmentModel();
        ModelAndView modelAndView = new ModelAndView();
        String serviceCode = "";
        String engCode = "";
        String orgSchema = "";
        try {
              if (session.getAttribute(SessionConstants.USER_SESSION) == null) {
                return new ModelAndView("/login");
            }
            if ((srvcd != null && !srvcd.equalsIgnoreCase("")) || (engcd != null && !engcd.equalsIgnoreCase(""))) {
                serviceCode = encryptDecrypt.decrypt(srvcd);
                 engCode = encryptDecrypt.decrypt(engcd);
                 
                   session.setAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE, engCode);
            session.setAttribute(ReportsAndDashboardConstants.SERVICE_CODE, serviceCode);

            assessmentModel.setEngagementCode(engCode);
            assessmentModel.setServiceCode(serviceCode);
            assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
            assessmentModel.setSelectedString(ReportsAndDashboardConstants.DB_CONTANT_SELECTED_VALUE);
            
            if(null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)){
                orgSchema = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
            assessmentModel.setOrgSchema(orgSchema);
            modelAndView.addObject("vulnerabilities", assessmentService.vulnerabilitiesbyFrequency(assessmentModel));
            modelAndView.addObject("assessmentCount", reportsAndDashboardService.fetchAssessmentsFindliingCounts(engCode, serviceCode,orgSchema));
            modelAndView.addObject("service", serviceCode);

            if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_APPLICATION_VULNERABILITY_ASSESSMENT)) {
                modelAndView.addObject("serviceName", ReportsAndDashboardConstants.SERVICE_APPLICATION_VULNERABILITY_ASSESSMENT);
            } else if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_NETWORK_VULNERABILITY_ASSESSMENT)) {
                modelAndView.addObject("serviceName", ReportsAndDashboardConstants.SERVICE_NETWORK_VULNERABILITY_ASSESSMENT);

            } else if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_PENETRATION_TESTING)) {
                modelAndView.addObject("serviceName", ReportsAndDashboardConstants.SERVICE_PENETRATION_TESTING);

            } else if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_LIMITED_REDTEAM_ASSESSMENT)) {
                modelAndView.addObject("serviceName", ReportsAndDashboardConstants.SERVICE_LIMITED_REDTEAM_ASSESSMENT);

            } else if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_SECURITY_RISK_ASSESSMENT_CODE)) {
                modelAndView.addObject("serviceName", ReportsAndDashboardConstants.SERVICE_SECURITY_RISK_ASSESSMENT_CODE);
            } else if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_THREATHUNTING)) {
                modelAndView.addObject("serviceName", ReportsAndDashboardConstants.SERVICE_THREATHUNTING);
            } else if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_WIRELESS_RISK_ASSESSMENT)) {
                modelAndView.addObject("serviceName", ReportsAndDashboardConstants.SERVICE_WIRELESS_RISK_ASSESSMENT);
            }

            modelAndView.setViewName("dashboards/Assessment");
            } else{
                   modelAndView.setViewName("redirect:/logout.htm");
            }
//            if (engcd != null && !engcd.equalsIgnoreCase("")) {
//               
//            }
          
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "AssessmentController Controller:: " + e.getMessage());

            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/engSerData", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    public @ResponseBody
    List<FindingsModel> getEngagementServiceFindingsData(HttpServletRequest request, HttpServletResponse res) {
        AssessmentModel assessmentModel = new AssessmentModel();
        List<FindingsModel> findingsModels = new ArrayList<>();
        HttpSession session = request.getSession();
        String serviceCode = "";
        String engCode = "";
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if(null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)){
                orgSchema = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
            }

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }
            assessmentModel.setEngagementCode(engCode);
            assessmentModel.setServiceCode(serviceCode);
            assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
            assessmentModel.setSelectedString(ReportsAndDashboardConstants.DB_CONTANT_SELECTED_VALUE);
            assessmentModel.setOrgSchema(orgSchema);
            findingsModels = assessmentService.getEngagementServiceFindingsData(assessmentModel);
        } catch (Exception e) {
            logger.debug("Exceptionoccured : getEngagementServiceFindingsData() : " + e.getMessage());
        }

        return findingsModels;

    }

    @RequestMapping(value = "/toThreatHuntingPage")
    public ModelAndView toThreatHuntingPage(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        AssessmentModel assessmentModel = new AssessmentModel();
        ModelAndView modelAndView = new ModelAndView();
        String engCode = "";
        String orgSchema = "";
        try {
               if (session.getAttribute(SessionConstants.USER_SESSION) == null) {
                return new ModelAndView("/login");
            }
            if (request.getSession().getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_KEY) != null) {
                engCode = encryptDecrypt.decrypt(request.getSession().getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_KEY).toString());
            }

            session.setAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE, engCode);
            session.setAttribute(ReportsAndDashboardConstants.SERVICE_CODE, ReportsAndDashboardConstants.SERVICE_CODE_THREATHUNTING);

            assessmentModel.setEngagementCode(engCode);
            assessmentModel.setServiceCode(ReportsAndDashboardConstants.SERVICE_CODE_THREATHUNTING);
            assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
            assessmentModel.setSelectedString(ReportsAndDashboardConstants.DB_CONTANT_SELECTED_VALUE);
            assessmentModel.setOrgSchema(orgSchema);
            
            if(null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)){
                orgSchema = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
            modelAndView.addObject("vulnerabilities", assessmentService.vulnerabilitiesbyFrequency(assessmentModel));
            modelAndView.addObject("assessmentCount", reportsAndDashboardService.fetchAssessmentsFindliingCounts(engCode, ReportsAndDashboardConstants.SERVICE_CODE_THREATHUNTING,orgSchema));
            modelAndView.addObject("service", ReportsAndDashboardConstants.SERVICE_CODE_THREATHUNTING);
            modelAndView.addObject("serviceName", ReportsAndDashboardConstants.SERVICE_NAME_THREATHUNTING);

            modelAndView.setViewName("dashboards/threatHunting");
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the "
                    + "toThreatHuntingPage() :: " + e.getMessage());

            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/applicationData.htm", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SecurityModel applicationData(HttpServletRequest req, HttpServletResponse res) {

        SecurityModel data = null;
        String engCode = "";
        String serviceCode = "";
        HttpSession session = req.getSession();
        AssessmentModel assessmentModel = new AssessmentModel();
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if(null != req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)){
                orgSchema = req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
            
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
            }

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }
            assessmentModel.setEngagementCode(engCode);
            assessmentModel.setServiceCode(serviceCode);
            assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
            assessmentModel.setSelectedString(ReportsAndDashboardConstants.DB_CONTANT_SELECTED_VALUE);
            assessmentModel.setOrgSchema(orgSchema);
            data = assessmentService.applicationDetailsList(assessmentModel);
        } catch (Exception e) {
            logger.debug("Exceptionoccured : applicationData() : " + e.getMessage());
        }
        return data;
    }

    @RequestMapping(value = "/topApplicationDetails.htm", method = RequestMethod.GET)
    public @ResponseBody
    List<String> topApplicationDetails(HttpServletRequest request, HttpServletResponse response) {
        String osString = "";
        String engCode = "";
        String serviceCode = "";
        AssessmentModel assessmentModel = new AssessmentModel();
        HttpSession session = request.getSession();
        List<String> retLi = new LinkedList<String>();
        String data = "";
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if(null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)){
                orgSchema = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
       
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
            }

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }
            assessmentModel.setEngagementCode(engCode);
            assessmentModel.setServiceCode(serviceCode);
            assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
            assessmentModel.setSelectedString(ReportsAndDashboardConstants.DB_CONTANT_SELECTED_VALUE);
            assessmentModel.setOrgSchema(orgSchema);
            List<AssessmentModel> applicationList = assessmentService.applicationDetails(assessmentModel);
            int count = 0;
            String colorCode = "";
            for (AssessmentModel assessmentDto : applicationList) {
                if (count == 0) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_OS_ONE;
                } else if (count == 1) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_OS_TWO;
                } else if (count == 2) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_OS_THREE;
                } else if (count == 3) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_OS_FOUR;
                }
                osString = osString + "<set value='" + assessmentDto.getValue() + "'  label='" + assessmentDto.getLabel() + "' color='" + colorCode + "'/>";
                count++;
                if (count == 4) {
                    break;
                }
            }
            data = "<chart caption='' captionAlignment='left' captionFontColor='333333' bgAlpha='0' labelDisplay='wrap' maxLabelWidthPercent='40' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='10' chartRightMargin='0'    baseFont='arial'    showYAxisValues='0' labelFontSize='12' valueFontSize='12' labelPadding='13'  labelFontColor='6666666' showPlotBorder='0' yAxisMaxValue='60' maxBarHeight='15' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix=''  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' bgratio='0' showcanvasborder='1' canvasBorderAlpha='0' canvasBorderColor='ffffff' animation='1'>"
                    + osString + "</chart>";
            if (!StringUtils.isEmpty(data)) {
                data = commonUtil.encodeHTMLEntities(data);
                retLi.add(data);
                return retLi;
            }
        } catch (Exception e) {
            logger.debug("Exceptionoccured : applicationData() : " + e.getMessage());

        }
        return retLi;
    }

    @RequestMapping(value = "/ssidDropDownDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<SummaryDropDownModel> ssidDropDownDetails(HttpServletRequest request, HttpServletResponse res) {
        List<SummaryDropDownModel> ssidDropDownList = null;
        String engCode = "";
        String serviceCode = "";
        HttpSession session = request.getSession();
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if(null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)){
                orgSchema = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
       
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
            }
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }
            ssidDropDownList = assessmentService.ssidDropDownDetails(engCode, serviceCode, orgSchema);
        } catch (Exception e) {
            logger.debug("Exceptionoccured : ssidDropDownDetails() : " + e.getMessage());
        }
        return ssidDropDownList;
    }

    @RequestMapping(value = "/applicationDropDownDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<SummaryDropDownModel> applicationDropDownDetails(HttpServletRequest request, HttpServletResponse res) {
        List<SummaryDropDownModel> ssidDropDownList = null;
        String engCode = "";
        String serviceCode = "";
        HttpSession session = request.getSession();
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if(null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)){
                orgSchema = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
       
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
            }
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }
            ssidDropDownList = assessmentService.applicatonDropDownDetails(engCode, serviceCode, orgSchema);
        } catch (Exception e) {
            logger.debug("Exceptionoccured : applicationDropDownDetails() : " + e.getMessage());
        }
        return ssidDropDownList;
    }

    @RequestMapping(value = "/filterAssessmentFindings.htm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<FindingsModel> filterAssessmentFindings(HttpServletRequest request, HttpServletResponse res, @RequestBody List<SummaryDropDownModel> summaryDropDownModel) {
        HttpSession session = request.getSession();
        String engCode = "";
        String serviceCode = "";
        AssessmentModel assessmentModel = new AssessmentModel();
        List<String> ssidTextList = new ArrayList<>();
        List<FindingsModel> findingData = null;
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if(null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)){
                orgSchema = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
       
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
            }

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }

            assessmentModel.setEngagementCode(engCode);
            assessmentModel.setServiceCode(serviceCode);
            for (SummaryDropDownModel sModel : summaryDropDownModel) {
                ssidTextList.add(sModel.getId() + "");

            }
            assessmentModel.setSelectedString(StringUtils.join(ssidTextList, ","));
            if (assessmentModel.getSelectedString().equalsIgnoreCase("")) {
                assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
            } else {

                if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_APPLICATION_VULNERABILITY_ASSESSMENT)) {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_APPLICATION);
                } else if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_WIRELESS_RISK_ASSESSMENT)) {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_SSID);
                } else {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
                }

            }
            assessmentModel.setOrgSchema(orgSchema);
            findingData = assessmentService.getEngagementServiceFindingsData(assessmentModel);
        } catch (Exception e) {
            logger.debug("Exceptionoccured : severityFilterDetails() : " + e.getMessage());
        }
        return findingData;
    }

//    @RequestMapping(value = "/severityFilterDetails.htm", method = RequestMethod.POST)
//    public @ResponseBody
//    List<String> severityFilterDetails(HttpServletRequest request, HttpServletResponse response, @RequestBody List<SummaryDropDownModel> summaryDropDownModel) {
//        HttpSession session = request.getSession();
//        String severityString = "";
//        String engCode = "";
//        String serviceCode = "";
//        AssessmentModel assessmentModel = new AssessmentModel();
//        String data = "";
//        List<String> retLi = new LinkedList<String>();
//        try {
//            List<String> ssidTextList = new ArrayList<>();
//
//            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
//                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
//
//            }
//
//            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
//                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
//
//            }
//            assessmentModel.setEngagementCode(engCode);
//            assessmentModel.setServiceCode(serviceCode);
//            for (SummaryDropDownModel sModel : summaryDropDownModel) {
//                ssidTextList.add(sModel.getId() + "");
//
//            }
//            assessmentModel.setSelectedString(StringUtils.join(ssidTextList, ","));
//            if (assessmentModel.getSelectedString().equalsIgnoreCase("")) {
//                assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
//            } else {
//
//                if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_APPLICATION_VULNERABILITY_ASSESSMENT)) {
//                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_APPLICATION);
//                } else if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_WIRELESS_RISK_ASSESSMENT)) {
//                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_SSID);
//                } else {
//                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
//                }
//
//            }
//
//            List<AssessmentModel> rootList = assessmentService.severityList(assessmentModel);
//            for (AssessmentModel assessmentDto : rootList) {
//                String colorCode = "";
//                if (ReportsAndDashboardConstants.FINDING_CRITICAL.equalsIgnoreCase(assessmentDto.getLabel())) {
//                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_CRITICAL;
//                } else if (ReportsAndDashboardConstants.FINDING_HIGH.equalsIgnoreCase(assessmentDto.getLabel())) {
//                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_HIGH;
//                } else if (ReportsAndDashboardConstants.FINDING_MEDIUM.equalsIgnoreCase(assessmentDto.getLabel())) {
//                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_MEDIUM;
//                } else {
//                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_LOW;
//                }
//                severityString = severityString + "<set value='" + assessmentDto.getValue() + "'  label='" + assessmentDto.getLabel() + "' color='" + colorCode + "'/>";
//            }
//            data = "<chart caption='' captionAlignment='left' captionFontColor='333333' bgAlpha='0' labelDisplay='wrap' maxLabelWidthPercent='40' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='10' chartRightMargin='0'   baseFont='arial'    showYAxisValues='0' labelFontSize='12' valueFontSize='12' labelPadding='13'  labelFontColor='6666666' showPlotBorder='0' yAxisMaxValue='60' maxBarHeight='15' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix=''  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' bgratio='0' showcanvasborder='1' canvasBorderAlpha='0' canvasBorderColor='ffffff' animation='1'>   "
//                    + severityString + "</chart>";
//            if (!StringUtils.isEmpty(data)) {
//                data = commonUtil.encodeHTMLEntities(data);
//                retLi.add(data);
//                return retLi;
//            }
//        } catch (Exception e) {
//            logger.debug("Exceptionoccured : severityFilterDetails() : " + e.getMessage());
//
//        }
//        return retLi;
//    }
    @RequestMapping(value = "/applicationFilterDetails.htm", method = RequestMethod.POST)
    public @ResponseBody
    List<String> applicationFilterDetails(HttpServletRequest request, HttpServletResponse response, @RequestBody List<SummaryDropDownModel> summaryDropDownModel) {
        HttpSession session = request.getSession();
        String appString = "";
        String engCode = "";
        String serviceCode = "";
        AssessmentModel assessmentModel = new AssessmentModel();
        List<String> retLi = new LinkedList<String>();
        String data = "";
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if(null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)){
                orgSchema = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
       
            List<String> ssidTextList = new ArrayList<>();

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);

            }

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);

            }
            assessmentModel.setEngagementCode(engCode);
            assessmentModel.setServiceCode(serviceCode);
            for (SummaryDropDownModel sModel : summaryDropDownModel) {
                ssidTextList.add(sModel.getId() + "");

            }
            assessmentModel.setSelectedString(StringUtils.join(ssidTextList, ","));
            if (assessmentModel.getSelectedString().equalsIgnoreCase("")) {
                assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
            } else {

                if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_APPLICATION_VULNERABILITY_ASSESSMENT)) {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_APPLICATION);
                } else {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
                }

            }
            assessmentModel.setOrgSchema(orgSchema);
            List<AssessmentModel> applicationList = assessmentService.applicationDetails(assessmentModel);
            int count = 0;
            String colorCode = "";
            for (AssessmentModel assessmentDto : applicationList) {
                if (count == 0) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_OS_ONE;
                } else if (count == 1) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_OS_TWO;
                } else if (count == 2) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_OS_THREE;
                } else if (count == 3) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_OS_FOUR;
                }
                appString = appString + "<set value='" + assessmentDto.getValue() + "'  label='" + assessmentDto.getLabel() + "' color='" + colorCode + "'/>";
                count++;
                if (count == 4) {
                    break;
                }
            }
            data = "<chart caption='' captionAlignment='left' captionFontColor='333333' bgAlpha='0' labelDisplay='wrap' maxLabelWidthPercent='40' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='10' chartRightMargin='0'    baseFont='arial'    showYAxisValues='0' labelFontSize='12' valueFontSize='12' labelPadding='13'  labelFontColor='6666666' showPlotBorder='0' yAxisMaxValue='60' maxBarHeight='15' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix=''  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' bgratio='0' showcanvasborder='1' canvasBorderAlpha='0' canvasBorderColor='ffffff' animation='1'>"
                    + appString + "</chart>";
            if (!StringUtils.isEmpty(data)) {
                data = commonUtil.encodeHTMLEntities(data);
                retLi.add(data);
                return retLi;
            }
        } catch (Exception e) {
            logger.debug("Exceptionoccured : applicationFilterDetails() : " + e.getMessage());

        }
        return retLi;
    }

    @RequestMapping(value = "/applicationAllFilterData.htm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SecurityModel applicationAllFilterData(HttpServletRequest req, HttpServletResponse res, @RequestBody List<SummaryDropDownModel> summaryDropDownModel) {

        List<AssessmentModel> engList = new ArrayList<AssessmentModel>();
        String engCode = "";
        String serviceCode = "";
        HttpSession session = req.getSession();
        AssessmentModel assessmentModel = new AssessmentModel();
        List<String> ssidTextList = new ArrayList<>();
        SecurityModel data = null;
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if(null != req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)){
                orgSchema = req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
       
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
            }

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }
            assessmentModel.setEngagementCode(engCode);
            assessmentModel.setServiceCode(serviceCode);
            for (SummaryDropDownModel sModel : summaryDropDownModel) {
                ssidTextList.add(sModel.getId() + "");

            }
            assessmentModel.setSelectedString(StringUtils.join(ssidTextList, ","));
            if (assessmentModel.getSelectedString().equalsIgnoreCase("")) {
                assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
            } else {

                if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_APPLICATION_VULNERABILITY_ASSESSMENT)) {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_APPLICATION);
                } else {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
                }

            }
            assessmentModel.setOrgSchema(orgSchema);
            data = assessmentService.applicationDetailsList(assessmentModel);
        } catch (Exception e) {
            logger.debug("Exceptionoccured : applicationAllFilterData() : " + e.getMessage());
        }
        return data;
    }

    @RequestMapping(value = "/filterTopssidFilterDetails.htm", method = RequestMethod.POST)
    public @ResponseBody
    List<String> filterTopssidFilterDetails(HttpServletRequest request, HttpServletResponse response, @RequestBody List<SummaryDropDownModel> summaryDropDownModel) {
        String osString = "";
        String engCode = "";
        String serviceCode = "";
        HttpSession session = request.getSession();
        List<String> ssidTextList = new ArrayList<>();
        AssessmentModel assessmentModel = new AssessmentModel();
        String data = "";
        List<String> retLi = new LinkedList<String>();
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if(null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)){
                orgSchema = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
       
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
            }

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }
            assessmentModel.setEngagementCode(engCode);
            assessmentModel.setServiceCode(serviceCode);
            for (SummaryDropDownModel sModel : summaryDropDownModel) {
                ssidTextList.add(sModel.getId() + "");

            }
            assessmentModel.setSelectedString(StringUtils.join(ssidTextList, ","));
            if (assessmentModel.getSelectedString().equalsIgnoreCase("")) {
                assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
            } else {

                if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_WIRELESS_RISK_ASSESSMENT)) {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_SSID);
                } else {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
                }

            }
            assessmentModel.setOrgSchema(orgSchema);
            List<AssessmentModel> ssidList = assessmentService.ssidDetails(assessmentModel);
            int count = 0;
            String colorCode = "";
            for (AssessmentModel assessmentDto : ssidList) {
                if (count == 0) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_OS_ONE;
                } else if (count == 1) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_OS_TWO;
                } else if (count == 2) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_OS_THREE;
                } else if (count == 3) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_OS_FOUR;
                }
                osString = osString + "<set value='" + assessmentDto.getValue() + "'  label='" + assessmentDto.getLabel() + "' color='" + colorCode + "'/>";
                count++;
                if (count == 4) {
                    break;
                }
            }
//         relace chart opetions with ssid chart  
            data = "<chart caption='' captionAlignment='left' captionFontColor='333333' bgAlpha='0' labelDisplay='wrap' maxLabelWidthPercent='25' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='10' chartRightMargin='0'    baseFont='arial'    showYAxisValues='0' labelFontSize='12' valueFontSize='12' labelPadding='13'  labelFontColor='6666666' showPlotBorder='0' yAxisMaxValue='100' maxBarHeight='15' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix=''  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' bgratio='0' showcanvasborder='1' canvasBorderAlpha='0' canvasBorderColor='ffffff' animation='1'>"
                    + osString + "</chart>";
            if (!StringUtils.isEmpty(data)) {
                data = commonUtil.encodeHTMLEntities(data);
                retLi.add(data);
                return retLi;
            }
        } catch (Exception e) {
            logger.debug("Exceptionoccured : filterTopssidFilterDetails() : " + e.getMessage());

        }
        return retLi;
    }

    @RequestMapping(value = "/filterssidDetails.htm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SecurityModel filterssidDetails(HttpServletRequest request, HttpServletResponse response, @RequestBody List<SummaryDropDownModel> summaryDropDownModel) {
        String engCode = "";
        String serviceCode = "";
        HttpSession session = request.getSession();
        AssessmentModel assessmentModel = new AssessmentModel();
        List<String> ssidTextList = new ArrayList<>();
        SecurityModel data = null;
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if(null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)){
                orgSchema = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
       
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
            }

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }
            assessmentModel.setEngagementCode(engCode);
            assessmentModel.setServiceCode(serviceCode);

            for (SummaryDropDownModel sModel : summaryDropDownModel) {
                ssidTextList.add(sModel.getId() + "");

            }
            assessmentModel.setSelectedString(StringUtils.join(ssidTextList, ","));
            if (assessmentModel.getSelectedString().equalsIgnoreCase("")) {
                assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
            } else {

                if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_WIRELESS_RISK_ASSESSMENT)) {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_SSID);
                } else {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
                }

            }
            assessmentModel.setOrgSchema(orgSchema);
            data = assessmentService.ssidDetailsList(assessmentModel);
        } catch (Exception e) {
            logger.debug("Exceptionoccured : filterssidDetails() : " + e.getMessage());
        }
        return data;
    }

    @RequestMapping(value = "/filteerRootCauseData.htm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SecurityModel filteerRootCauseData(HttpServletRequest request, HttpServletResponse response, @RequestBody List<SummaryDropDownModel> summaryDropDownModel) {
        HttpSession session = request.getSession();
        String engCode = "";
        String serviceCode = "";
        AssessmentModel assessmentModel = new AssessmentModel();
        List<String> ssidTextList = new ArrayList<>();
        List<AssessmentModel> rootList = null;
        SecurityModel data = null;
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if (null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                orgSchema = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
            
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
            }

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }

            assessmentModel.setEngagementCode(engCode);
            assessmentModel.setServiceCode(serviceCode);
            for (SummaryDropDownModel sModel : summaryDropDownModel) {
                ssidTextList.add(sModel.getId() + "");

            }
            assessmentModel.setSelectedString(StringUtils.join(ssidTextList, ","));
            if (assessmentModel.getSelectedString().equalsIgnoreCase("")) {
                assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
            } else {

                if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_APPLICATION_VULNERABILITY_ASSESSMENT)) {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_APPLICATION);
                } else if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_WIRELESS_RISK_ASSESSMENT)) {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_SSID);
                } else {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
                }

            }
            assessmentModel.setOrgSchema(orgSchema);
            data = assessmentService.rootCausesList(assessmentModel);
        } catch (Exception e) {
            logger.debug("Exceptionoccured : filteerRootCauseData() : " + e.getMessage());
        }
        return data;
    }

    @RequestMapping(value = "/filterTopRootCauses.htm", method = RequestMethod.POST)
    public @ResponseBody
    List<String> filterTopRootCauses(HttpServletRequest request, HttpServletResponse response, @RequestBody List<SummaryDropDownModel> summaryDropDownModel) {
        HttpSession session = request.getSession();
        String rootString = "";
        String engCode = "";
        String serviceCode = "";
        AssessmentModel assessmentModel = new AssessmentModel();
        List<String> ssidTextList = new ArrayList<>();
        String data = "";
        List<String> retLi = new LinkedList<String>();
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if (null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                orgSchema = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
            
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
            }

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }

            assessmentModel.setEngagementCode(engCode);
            assessmentModel.setServiceCode(serviceCode);
            for (SummaryDropDownModel sModel : summaryDropDownModel) {
                ssidTextList.add(sModel.getId() + "");

            }
            assessmentModel.setSelectedString(StringUtils.join(ssidTextList, ","));
            if (assessmentModel.getSelectedString().equalsIgnoreCase("")) {
                assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
            } else {

                if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_APPLICATION_VULNERABILITY_ASSESSMENT)) {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_APPLICATION);
                } else if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_WIRELESS_RISK_ASSESSMENT)) {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_SSID);
                } else {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
                }

            }
            assessmentModel.setOrgSchema(orgSchema);
            List<AssessmentModel> rootList = assessmentService.topRootCauses(assessmentModel);
            int count = 0;
            String colorCode = "";
            for (AssessmentModel assessmentDto : rootList) {
                if (count == 0) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_ROOTCAUSE_ONE;
                } else if (count == 1) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_ROOTCAUSE_TWO;
                } else if (count == 2) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_ROOTCAUSE_THREE;
                } else if (count == 3) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_ROOTCAUSE_FOUR;
                }
                rootString = rootString + "<set value='" + assessmentDto.getValue() + "'  label='" + assessmentDto.getLabel() + "' color='" + colorCode + "'/>";
                count++;
                if (count == 4) {
                    break;
                }
            }
            data = "<chart caption='' captionFontColor='333333' captionAlignment='left'  bgAlpha='0' labelDisplay='wrap' maxLabelWidthPercent='40' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='10' chartRightMargin='0'  baseFont='arial'    showYAxisValues='0' labelFontSize='12' valueFontSize='12' labelPadding='13'  labelFontColor='6666666' showPlotBorder='0' yAxisMaxValue='60' maxBarHeight='15' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix=''  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' bgratio='0' showcanvasborder='1' canvasBorderAlpha='0' canvasBorderColor='ffffff' animation='1'> "
                    + rootString + "</chart>";
            if (!StringUtils.isEmpty(data)) {
                data = commonUtil.encodeHTMLEntities(data);
                retLi.add(data);
                return retLi;
            }
        } catch (Exception e) {
            logger.debug("Exceptionoccured : filterTopRootCauses() : " + e.getMessage());

        }
        return retLi;
    }

    @RequestMapping(value = "/applicationMostVulnerable.htm", method = RequestMethod.GET)
    public @ResponseBody
    List<String> applicationMostVulnerable(HttpServletRequest request, HttpServletResponse response) {
        String hostName = "";
        String critical = "";
        String high = "";
        String medium = "";
        String low = "";
        HttpSession session = request.getSession();
        String engCode = "";
        String serviceCode = "";
        String data = "";
        List<String> retLi = new LinkedList<String>();
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if (null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                orgSchema = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
            
            AssessmentModel assessmentModel = new AssessmentModel();
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
            }

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }

            assessmentModel.setEngagementCode(engCode);
            assessmentModel.setServiceCode(serviceCode);
            assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
            assessmentModel.setSelectedString(ReportsAndDashboardConstants.DB_CONTANT_SELECTED_VALUE);
            assessmentModel.setOrgSchema(orgSchema);
            List list = assessmentService.applicationMostVulnerable(assessmentModel);
            data = assessmentService.mostVulnerableChartConstructionService(list);
            if (!StringUtils.isEmpty(data)) {
                data = commonUtil.encodeHTMLEntities(data);
                retLi.add(data);
                return retLi;
            }
        } catch (Exception e) {
            logger.debug("Exceptionoccured : applicationMostVulnerable() : " + e.getMessage());
        }
        return retLi;
    }

    @RequestMapping(value = "/filterApplicationMostVulnerable.htm", method = RequestMethod.POST)
    public @ResponseBody
    List<String> filterApplicationMostVulnerable(HttpServletRequest request, HttpServletResponse response, @RequestBody List<SummaryDropDownModel> summaryDropDownModel) {
        String hostName = "";
        String critical = "";
        String high = "";
        String medium = "";
        String low = "";
        HttpSession session = request.getSession();
        String engCode = "";
        String serviceCode = "";
        AssessmentModel assessmentModel = new AssessmentModel();
        List<String> ssidTextList = new ArrayList<>();
        String dataString = "";
        List<String> retLi = new LinkedList<String>();
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if (null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                orgSchema = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
            
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
            }

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }

            assessmentModel.setEngagementCode(engCode);
            assessmentModel.setServiceCode(serviceCode);
            for (SummaryDropDownModel sModel : summaryDropDownModel) {
                ssidTextList.add(sModel.getId() + "");

            }
            assessmentModel.setSelectedString(StringUtils.join(ssidTextList, ","));
            if (assessmentModel.getSelectedString().equalsIgnoreCase("")) {
                assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
            } else {

                if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_APPLICATION_VULNERABILITY_ASSESSMENT)) {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_APPLICATION);
                } else {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
                }

            }
            assessmentModel.setOrgSchema(orgSchema);
            List list = assessmentService.applicationMostVulnerable(assessmentModel);
            dataString = assessmentService.mostVulnerableChartConstructionService(list);
            if (!StringUtils.isEmpty(dataString)) {
                dataString = commonUtil.encodeHTMLEntities(dataString);
                retLi.add(dataString);
                return retLi;
            }
        } catch (Exception e) {
            logger.debug("Exceptionoccured : filterApplicationMostVulnerable() : " + e.getMessage());
        }
        return retLi;
    }

    @RequestMapping(value = "/filterApplicationTopFindings.htm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<AssessmentModel> filterApplicationTopFindings(HttpServletRequest request, HttpServletResponse response, @RequestBody List<SummaryDropDownModel> summaryDropDownModel) {
        HttpSession session = request.getSession();
        String engCode = "";
        String serviceCode = "";
        AssessmentModel assessmentModel = new AssessmentModel();
        List<String> ssidTextList = new ArrayList<>();
        List<AssessmentModel> rootList = null;
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if (null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                orgSchema = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
            
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
            }

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }

            assessmentModel.setEngagementCode(engCode);
            assessmentModel.setServiceCode(serviceCode);
            for (SummaryDropDownModel sModel : summaryDropDownModel) {
                ssidTextList.add(sModel.getId() + "");

            }
            assessmentModel.setSelectedString(StringUtils.join(ssidTextList, ","));
            if (assessmentModel.getSelectedString().equalsIgnoreCase("")) {
                assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
            } else {

                if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_APPLICATION_VULNERABILITY_ASSESSMENT)) {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_APPLICATION);
                } else {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
                }

            }
            assessmentModel.setOrgSchema(orgSchema);
            rootList = assessmentService.vulnerabilitiesbyFrequency(assessmentModel);
        } catch (Exception e) {
            logger.debug("Exceptionoccured : filterApplicationMostVulnerable() : " + e.getMessage());
        }
        return rootList;
    }

    @RequestMapping(value = "/csvExportAssessment.htm")
    public void assessmentDataCsvFiledownload(HttpServletRequest request, HttpServletResponse response, @RequestBody List<SummaryDropDownModel> summaryDropDownModel) {
        SecurityModel securityModel = new SecurityModel();
        HttpSession session = request.getSession();
        String engCode = "";
        String serviceCode = "";
        AssessmentModel assessmentModel = new AssessmentModel();
        List dbHeaders = new ArrayList();
        List csvHeaders = new ArrayList();
            
        try {
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
                securityModel.getEngagementsDTO().setEngagementKey(encryptDecrypt.encrypt(engCode));
            }

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }

            assessmentModel.setEngagementCode(engCode);
            assessmentModel.setServiceCode(serviceCode);
            List<String> selectedTextList = new ArrayList<>();
            for (SummaryDropDownModel summaryModel1 : summaryDropDownModel) {
                String maker = summaryModel1.getMaker();
                if (maker.equalsIgnoreCase(ReportsAndDashboardConstants.APP_SSID)) {
                    selectedTextList.add(summaryModel1.getId());
                } else {
                    dbHeaders.add(summaryModel1.getMaker());
                    csvHeaders.add(summaryModel1.getLabel());
                }
            }

            if (selectedTextList.size() > 0) {
                assessmentModel.setSelectedString(StringUtils.join(selectedTextList, ","));
            } else {
                assessmentModel.setSelectedString("");
            }

//            if (request.getParameter("param") != null) {
//                String param = request.getParameter("param");
//
//                List<String> selectedTextList = new ArrayList<>();
//                JSONArray jsonarray = new JSONArray(param);
//                for (int i = 0; i < jsonarray.length(); i++) {
//                    JSONObject jsonobject = jsonarray.getJSONObject(i);
//                    selectedTextList.add(jsonobject.getString("id"));
//                }
//                assessmentModel.setSelectedString(StringUtils.join(selectedTextList, ","));
//            } else {
//                assessmentModel.setSelectedString("");
//            }
            if (assessmentModel.getSelectedString().equalsIgnoreCase("")) {
                assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
            } else {

                if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_APPLICATION_VULNERABILITY_ASSESSMENT)) {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_APPLICATION);
                } else if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_WIRELESS_RISK_ASSESSMENT)) {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_SSID);
                } else {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
                }

            }
            securityModel.setCsvDBHeaders(dbHeaders);
            securityModel.setCsvHeaders(csvHeaders);
             if(null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)){
                securityModel.setEngSchemaName(request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString());
            }
            List listOfFindings = assessmentService.getServiceFindingsDataForExportCSV(assessmentModel, securityModel);
            EngagementsDTO engagementsDTO = serviceImpl.getEngagementDetailsForExportCSV(securityModel);

            csvFileCreation.prepareCSVFile(listOfFindings, engagementsDTO);

//            response.setContentType("text/csv");
//            response.setHeader("Content-Disposition", "attachment; filename=\"FindingDetails_.csv\"");
//
//            FileInputStream fin = new FileInputStream(downloadFile);
//
//            int size = fin.available();
//            response.setContentLength(size);
//            byte[] ab = new byte[size];
//            OutputStream os = response.getOutputStream();
//            int bytesread;
//            do {
//                bytesread = fin.read(ab, 0, size);
//                if (bytesread > -1) {
//                    os.write(ab, 0, bytesread);
//                }
//            } while (bytesread > -1);
//
//            fin.close();
//            os.flush();
//            os.close();
        } catch (Exception ie) {
            logger.debug("Exception Occured while Executing the assessmentDataCsvFiledownload Action :: " + ie.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), ie);
        }

    }	

    @RequestMapping(value = "/topRemediationPriorities.htm", method = RequestMethod.GET)
    public @ResponseBody
    List<String> topRemediationPriorities(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String rootString = "";
        String engCode = "";
        String serviceCode = "";
        AssessmentModel assessmentModel = new AssessmentModel();
        String data = "";
        List<String> retLi = new LinkedList<String>();
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if (null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                orgSchema = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
            
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
            }

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }
            assessmentModel.setEngagementCode(engCode);
            assessmentModel.setServiceCode(serviceCode);
            if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_NETWORK_VULNERABILITY_ASSESSMENT)) {
                assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_NETWORK);
            } else {
                assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
            }
            assessmentModel.setSelectedString(ReportsAndDashboardConstants.DB_CONTANT_SELECTED_VALUE);
            assessmentModel.setOrgSchema(orgSchema);
            List<AssessmentModel> remList = assessmentService.remediationPriorities(assessmentModel);
            int count = 0;
            String label = "";
            String value = "";
            String percentage = "";
            int valueSum=0;
            
            for (AssessmentModel assessmentDto : remList) {
                label=label+"<category label='"+assessmentDto.getLabel()+"'/>";
                value=value+"<set value='"+assessmentDto.getValue()+"' color='"+ReportsAndDashboardConstants.COLOR_CODE_Remediation_ONE +"' label=''/>";
                valueSum=valueSum+Integer.parseInt(assessmentDto.getValue());
                
                percentage=percentage+"<set value='"+CommonUtil.percentageCount(valueSum, assessmentDto.getTotalCount())+"'/>";
//                rootString = rootString + "<set value='" + assessmentDto.getValue() + "'  label='" + assessmentDto.getLabel() + "' color='" + ReportsAndDashboardConstants.COLOR_CODE_Remediation_ONE + "'/>";
                count++;
                if (count == 5) {
                    break;
                }
            }

            if (count > 0) {
                if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_NETWORK_VULNERABILITY_ASSESSMENT)) {
                    data = "<chart caption='' captionAlignment='center' captionFontColor='333333' yAxisName='No. of Affected Hosts' yAxisMaxValue='10' showxaxisline='0' xaxislinecolor='#cccccc' baseFont='arial' basefontsize='10'  labelFontSize='12' valueFontSize='12' labelPadding='13' sNumberSuffix='%' numDivLines='0' maxLabelHeight='30  ' maxColWidth='15' bgcolor='FFFFFF' plotgradientcolor='' showalternatehgridcolor='0' baseFontSize='11' showplotborder='0' divlinecolor='CCCCCC' showvalues='1' showcanvasborder='1' borderThickness='0'  canvasBorderThickness='1' canvasBorderColor='ffffff' pyaxisname='' syaxisname='' numberprefix='' labeldisplay=''  canvasborderalpha='0' showlegend='0' legendshadow='0' legendborderalpha='0' showborder='0' borderAlpha='0' showVLineLabelBorder='0' showPlotBorder='1'  plotBorderAlpha='0' showYAxisValues='0' showAlternateVGridColor='0'>"
                            + "<categories>" + label + "</categories>"
                            + "<dataset seriesname='' >" + value + "</dataset>"
                            + "<dataset seriesname='Open' parentyaxis='S' renderas='Line' color='f8bd19'>" + percentage + "</dataset>"
                            + "</chart>";
                } else {
                    data = "<chart caption='' captionAlignment='center' captionFontColor='333333' yAxisName='No. of findings' yAxisMaxValue='10' showxaxisline='0' xaxislinecolor='#cccccc' baseFont='arial' basefontsize='10'  labelFontSize='12' valueFontSize='12' labelPadding='13' sNumberSuffix='%' numDivLines='0' maxLabelHeight='30  ' maxColWidth='15' bgcolor='FFFFFF' plotgradientcolor='' showalternatehgridcolor='0' baseFontSize='11' showplotborder='0' divlinecolor='CCCCCC' showvalues='1' showcanvasborder='1' borderThickness='0'  canvasBorderThickness='1' canvasBorderColor='ffffff' pyaxisname='' syaxisname='' numberprefix='' labeldisplay=''  canvasborderalpha='0' showlegend='0' legendshadow='0' legendborderalpha='0' showborder='0' borderAlpha='0' showVLineLabelBorder='0' showPlotBorder='1'  plotBorderAlpha='0' showYAxisValues='0' showAlternateVGridColor='0'>"
                            + "<categories>" + label + "</categories>"
                            + "<dataset seriesname='' >" + value + "</dataset>"
                            + "<dataset seriesname='Open' parentyaxis='S' renderas='Line' color='f8bd19'>" + percentage + "</dataset>"
                            + "</chart>";
//                data = "<chart caption='' captionAlignment='left' captionFontColor='333333' pYAxisNameFontSize='14' pYAxisName='No. of findings' bgAlpha='0'  labelDisplay='wrap' maxLabelHeight='50' showTooltip='1' maxColWidth='15' toolTipColor= '666666' toolTipBorderThickness= '1'  toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='0' chartRightMargin='0'   baseFont='arial'    showYAxisValues='0' labelFontSize='12' valueFontSize='12' labelPadding='13'  labelFontColor='6666666' showPlotBorder='0' yAxisMaxValue='190' maxBarHeight='15' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix=''  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' bgratio='0' showcanvasborder='1' canvasBorderColor='ffffff'  showBorder='0'  canvasBorderAlpha='0'  animation='1'> "
//                        + rootString + "</chart>";
                }
            } else {
                data = "<chart caption='' captionAlignment='center' captionFontColor='333333' yAxisName='No. of findings' yAxisMaxValue='10' showxaxisline='0' xaxislinecolor='#cccccc' baseFont='arial' basefontsize='10'  labelFontSize='12' valueFontSize='12' labelPadding='13' sNumberSuffix='%' numDivLines='0' maxLabelHeight='30  ' maxColWidth='15' bgcolor='FFFFFF' plotgradientcolor='' showalternatehgridcolor='0' baseFontSize='11' showplotborder='0' divlinecolor='CCCCCC' showvalues='1' showcanvasborder='1' borderThickness='0'  canvasBorderThickness='1' canvasBorderColor='ffffff' pyaxisname='' syaxisname='' numberprefix='' labeldisplay=''  canvasborderalpha='0' showlegend='0' legendshadow='0' legendborderalpha='0' showborder='0' borderAlpha='0' showVLineLabelBorder='0' showPlotBorder='1'  plotBorderAlpha='0' showYAxisValues='0' showAlternateVGridColor='0'></chart>";
            }
            if (!StringUtils.isEmpty(data)) {
                data = commonUtil.encodeHTMLEntities(data);
                retLi.add(data);
                return retLi;
            }
        } catch (Exception e) {
            logger.debug("Exceptionoccured : topRemediationPriorities() : " + e.getMessage());

        }
        return retLi;
    }

    @RequestMapping(value = "/remPriorities.htm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SecurityModel remediationPriorities(HttpServletRequest req, HttpServletResponse res) {
        
        List<AssessmentModel> engList = new ArrayList<AssessmentModel>();
        String engCode = "";
        String serviceCode = "";
        HttpSession session = req.getSession();
        AssessmentModel assessmentModel = new AssessmentModel();
        SecurityModel data = null;
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if (null != req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                orgSchema = req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
            
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
            }
            
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }
            assessmentModel.setEngagementCode(engCode);
            assessmentModel.setServiceCode(serviceCode);
            if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_NETWORK_VULNERABILITY_ASSESSMENT)) {
                assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_NETWORK);
                assessmentModel.setRemYAxis("No. of Affected Hosts ");
            } else {
                assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
                assessmentModel.setRemYAxis("No. of findings");
            }
            assessmentModel.setSelectedString(ReportsAndDashboardConstants.DB_CONTANT_SELECTED_VALUE);
            assessmentModel.setOrgSchema(orgSchema);
            data = assessmentService.remediationPrioritiesList(assessmentModel);
            data.setRemYAxis(assessmentModel.getRemYAxis());
//            assessmentModel.setRemList(engList);
        } catch (Exception e) {
            logger.debug("Exceptionoccured : remediationPriorities() : " + e.getMessage());
        }
        return data;
    }

    @RequestMapping(value = "/filterTopRemediationPriorities.htm", method = RequestMethod.POST)
    public @ResponseBody
    List<String> filterTopRemediationPriorities(HttpServletRequest request, HttpServletResponse response, @RequestBody List<SummaryDropDownModel> summaryDropDownModel) {
        HttpSession session = request.getSession();
        String rootString = "";
        String engCode = "";
        String serviceCode = "";
        AssessmentModel assessmentModel = new AssessmentModel();
        String data = "";
        List<String> retLi = new LinkedList<String>();
        List<String> ssidTextList = new ArrayList<>();
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if (null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                orgSchema = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
            
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
            }

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }
            assessmentModel.setEngagementCode(engCode);
            assessmentModel.setServiceCode(serviceCode);
            for (SummaryDropDownModel sModel : summaryDropDownModel) {
                ssidTextList.add(sModel.getId() + "");

            }
            assessmentModel.setSelectedString(StringUtils.join(ssidTextList, ","));
            if (assessmentModel.getSelectedString().equalsIgnoreCase("")) {
                assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
            } else {

                if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_APPLICATION_VULNERABILITY_ASSESSMENT)) {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_APPLICATION);
                } else if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_WIRELESS_RISK_ASSESSMENT)) {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_SSID);
                } else {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
                }

            }
            assessmentModel.setOrgSchema(orgSchema);
            List<AssessmentModel> remList = assessmentService.remediationPriorities(assessmentModel);
            int count = 0;
            String colorCode = "";
                String label = "";
            String value = "";
            String percentage = "";
            int valueSum=0;
            for (AssessmentModel assessmentDto : remList) {
                label=label+"<category label='"+assessmentDto.getLabel()+"'/>";
                value=value+"<set value='"+assessmentDto.getValue()+"' color='"+ReportsAndDashboardConstants.COLOR_CODE_Remediation_ONE +"' label=''/>";
                valueSum=valueSum+Integer.parseInt(assessmentDto.getValue());
                
                percentage=percentage+"<set value='"+CommonUtil.percentageCount(valueSum, assessmentDto.getTotalCount())+"'/>";
//                rootString = rootString + "<set value='" + assessmentDto.getValue() + "'  label='" + assessmentDto.getLabel() + "' color='" + ReportsAndDashboardConstants.COLOR_CODE_Remediation_ONE + "'/>";
                count++;
                if (count == 5) {
                    break;
                }
            }
            if (count > 0) {
                if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_NETWORK_VULNERABILITY_ASSESSMENT)) {
                    data = "<chart caption='' captionAlignment='center' captionFontColor='333333' yAxisName='No. of Affected Hosts' yAxisMaxValue='10' showxaxisline='0' xaxislinecolor='#cccccc' baseFont='arial' basefontsize='10'  labelFontSize='12' valueFontSize='12' labelPadding='13' sNumberSuffix='%' numDivLines='0' maxLabelHeight='30  ' maxColWidth='15' bgcolor='FFFFFF' plotgradientcolor='' showalternatehgridcolor='0' baseFontSize='11' showplotborder='0' divlinecolor='CCCCCC' showvalues='1' showcanvasborder='1' borderThickness='0'  canvasBorderThickness='1' canvasBorderColor='ffffff' pyaxisname='' syaxisname='' numberprefix='' labeldisplay=''  canvasborderalpha='0' showlegend='0' legendshadow='0' legendborderalpha='0' showborder='0' borderAlpha='0' showVLineLabelBorder='0' showPlotBorder='1'  plotBorderAlpha='0' showYAxisValues='0' showAlternateVGridColor='0'>"
                            + "<categories>" + label + "</categories>"
                            + "<dataset seriesname='' >" + value + "</dataset>"
                            + "<dataset seriesname='Open' parentyaxis='S' renderas='Line' color='f8bd19'>" + percentage + "</dataset>"
                            + "</chart>";
                } else {
                    data = "<chart caption='' captionAlignment='center' captionFontColor='333333' yAxisName='No. of findings' yAxisMaxValue='10' showxaxisline='0' xaxislinecolor='#cccccc' baseFont='arial' basefontsize='10'  labelFontSize='12' valueFontSize='12' labelPadding='13' sNumberSuffix='%' numDivLines='0' maxLabelHeight='30  ' maxColWidth='15' bgcolor='FFFFFF' plotgradientcolor='' showalternatehgridcolor='0' baseFontSize='11' showplotborder='0' divlinecolor='CCCCCC' showvalues='1' showcanvasborder='1' borderThickness='0'  canvasBorderThickness='1' canvasBorderColor='ffffff' pyaxisname='' syaxisname='' numberprefix='' labeldisplay=''  canvasborderalpha='0' showlegend='0' legendshadow='0' legendborderalpha='0' showborder='0' borderAlpha='0' showVLineLabelBorder='0' showPlotBorder='1'  plotBorderAlpha='0' showYAxisValues='0' showAlternateVGridColor='0'>"
                            + "<categories>" + label + "</categories>"
                            + "<dataset seriesname='' >" + value + "</dataset>"
                            + "<dataset seriesname='Open' parentyaxis='S' renderas='Line' color='f8bd19'>" + percentage + "</dataset>"
                            + "</chart>";
//                data = "<chart caption='' captionAlignment='left' captionFontColor='333333' pYAxisNameFontSize='14' pYAxisName='No. of findings' bgAlpha='0'  labelDisplay='wrap' maxLabelHeight='50' showTooltip='1' maxColWidth='15' toolTipColor= '666666' toolTipBorderThickness= '1'  toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='0' chartRightMargin='0'   baseFont='arial'    showYAxisValues='0' labelFontSize='12' valueFontSize='12' labelPadding='13'  labelFontColor='6666666' showPlotBorder='0' yAxisMaxValue='190' maxBarHeight='15' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix=''  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' bgratio='0' showcanvasborder='1' canvasBorderColor='ffffff'  showBorder='0'  canvasBorderAlpha='0'  animation='1'> "
//                        + rootString + "</chart>";
                }
            } else {
                data = "<chart caption='' captionAlignment='center' captionFontColor='333333' yAxisName='No. of Affected Hosts' yAxisMaxValue='10' showxaxisline='1' xaxislinecolor='#cccccc' baseFont='arial' basefontsize='10'  labelFontSize='12' valueFontSize='12' labelPadding='13' sNumberSuffix='%' numDivLines='0' maxLabelHeight='30  ' maxColWidth='15' bgcolor='FFFFFF' plotgradientcolor='' showalternatehgridcolor='0' baseFontSize='11' showplotborder='0' divlinecolor='CCCCCC' showvalues='1' showcanvasborder='1' borderThickness='0'  canvasBorderThickness='1' canvasBorderColor='ffffff' pyaxisname='' syaxisname='' numberprefix='' labeldisplay=''  canvasborderalpha='0' showlegend='0' legendshadow='0' legendborderalpha='0' showborder='0' borderAlpha='0' showVLineLabelBorder='0' showPlotBorder='1'  plotBorderAlpha='0' showYAxisValues='0' showAlternateVGridColor='0'></chart>";
            }
            if (!StringUtils.isEmpty(data)) {
                data = commonUtil.encodeHTMLEntities(data);
                retLi.add(data);
                return retLi;
            }
        } catch (Exception e) {
            logger.debug("Exceptionoccured : filterTopRemediationPriorities() : " + e.getMessage());

        }
        return retLi;
    }

    @RequestMapping(value = "/filterRemPriorities.htm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SecurityModel filterRemediationPriorities(HttpServletRequest req, HttpServletResponse res, @RequestBody List<SummaryDropDownModel> summaryDropDownModel) {

        List<AssessmentModel> engList = new ArrayList<AssessmentModel>();
        String engCode = "";
        String serviceCode = "";
        HttpSession session = req.getSession();
        AssessmentModel assessmentModel = new AssessmentModel();
        List<String> ssidTextList = new ArrayList<>();
        SecurityModel data = null;
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if (null != req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                orgSchema = req.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
            
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
            }

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }
            assessmentModel.setEngagementCode(engCode);
            assessmentModel.setServiceCode(serviceCode);
            for (SummaryDropDownModel sModel : summaryDropDownModel) {
                ssidTextList.add(sModel.getId() + "");

            }
            assessmentModel.setSelectedString(StringUtils.join(ssidTextList, ","));
            if (assessmentModel.getSelectedString().equalsIgnoreCase("")) {
                assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
            } else {

                if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_APPLICATION_VULNERABILITY_ASSESSMENT)) {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_APPLICATION);
                } else if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_WIRELESS_RISK_ASSESSMENT)) {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_SSID);
                } else {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
                }

            }
            assessmentModel.setOrgSchema(orgSchema);
            data = assessmentService.remediationPrioritiesList(assessmentModel);
        } catch (Exception e) {
            logger.debug("Exceptionoccured : filterRemediationPriorities() : " + e.getMessage());
        }
        return data;
    }
    
    @RequestMapping(value = "/owaspChartData.htm", method = RequestMethod.GET)
    public @ResponseBody
    List<String> owaspChartData(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String rootString = "";
        String engCode = "";
        String serviceCode = "";
        AssessmentModel assessmentModel = new AssessmentModel();
        String data = "";
        List<String> retLi = new LinkedList<String>();
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if (null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                orgSchema = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
            
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
            }

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }
            assessmentModel.setEngagementCode(engCode);
            assessmentModel.setServiceCode(serviceCode);
            assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
            assessmentModel.setSelectedString(ReportsAndDashboardConstants.DB_CONTANT_SELECTED_VALUE);
            assessmentModel.setOrgSchema(orgSchema);
            List<AssessmentModel> rootList = assessmentService.owaspChartData(assessmentModel);
            int count = 0;
            String colorCode = "";
            for (AssessmentModel assessmentDto : rootList) {
                if (count == 0) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_ROOTCAUSE_ONE;
                } else if (count == 1) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_ROOTCAUSE_TWO;
                } else if (count == 2) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_ROOTCAUSE_THREE;
                } else if (count == 3) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_ROOTCAUSE_FOUR;
                }
                rootString = rootString + "<set value='" + assessmentDto.getValue() + "'  label='" + assessmentDto.getLabel() + "' color='" + colorCode + "'/>";
                count++;
                if (count == 4) {
                    break;
                }
            }
            data = "<chart caption='' captionFontColor='333333' captionAlignment='left'  bgAlpha='0' labelDisplay='wrap' maxLabelWidthPercent='30' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='10' chartRightMargin='0'  baseFont='arial'    showYAxisValues='0' labelFontSize='12' valueFontSize='12' labelPadding='13'  labelFontColor='6666666' showPlotBorder='0' yAxisMaxValue='60' maxBarHeight='15' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix=''  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' bgratio='0' showcanvasborder='1' canvasBorderAlpha='0' canvasBorderColor='ffffff' animation='1'> "
                    + rootString + "</chart>";
            if (!StringUtils.isEmpty(data)) {
                data = commonUtil.encodeHTMLEntities(data);
                retLi.add(data);
                return retLi;
            }
        } catch (Exception e) {
            logger.debug("Exceptionoccured : topRootCauses() : " + e.getMessage());

        }
        return retLi;
    }
    
    @RequestMapping(value = "/filterOwaspChartData.htm", method = RequestMethod.POST)
    public @ResponseBody
    List<String> filterOwaspChartData(HttpServletRequest request, HttpServletResponse response, @RequestBody List<SummaryDropDownModel> summaryDropDownModel) {
        HttpSession session = request.getSession();
        String rootString = "";
        String engCode = "";
        String serviceCode = "";
        AssessmentModel assessmentModel = new AssessmentModel();
        List<String> ssidTextList = new ArrayList<>();
        String data = "";
        List<String> retLi = new LinkedList<String>();
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if (null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                orgSchema = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
            
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
            }

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }

            assessmentModel.setEngagementCode(engCode);
            assessmentModel.setServiceCode(serviceCode);
            for (SummaryDropDownModel sModel : summaryDropDownModel) {
                ssidTextList.add(sModel.getId() + "");

            }
            assessmentModel.setSelectedString(StringUtils.join(ssidTextList, ","));
            if (assessmentModel.getSelectedString().equalsIgnoreCase("")) {
                assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
            } else {

                if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_APPLICATION_VULNERABILITY_ASSESSMENT)) {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_APPLICATION);
                } else if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_WIRELESS_RISK_ASSESSMENT)) {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_SSID);
                } else {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
                }

            }
            assessmentModel.setOrgSchema(orgSchema);
            List<AssessmentModel> rootList = assessmentService.owaspChartData(assessmentModel);
            int count = 0;
            String colorCode = "";
            for (AssessmentModel assessmentDto : rootList) {
                if (count == 0) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_ROOTCAUSE_ONE;
                } else if (count == 1) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_ROOTCAUSE_TWO;
                } else if (count == 2) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_ROOTCAUSE_THREE;
                } else if (count == 3) {
                    colorCode = ReportsAndDashboardConstants.COLOR_CODE_ROOTCAUSE_FOUR;
                }
                rootString = rootString + "<set value='" + assessmentDto.getValue() + "'  label='" + assessmentDto.getLabel() + "' color='" + colorCode + "'/>";
                count++;
                if (count == 4) {
                    break;
                }
            }
            data = "<chart caption='' captionFontColor='333333' captionAlignment='left'  bgAlpha='0' labelDisplay='wrap' maxLabelWidthPercent='40' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='10' chartRightMargin='0'  baseFont='arial'    showYAxisValues='0' labelFontSize='12' valueFontSize='12' labelPadding='13'  labelFontColor='6666666' showPlotBorder='0' yAxisMaxValue='60' maxBarHeight='15' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix=''  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' bgratio='0' showcanvasborder='1' canvasBorderAlpha='0' canvasBorderColor='ffffff' animation='1'> "
                    + rootString + "</chart>";
            if (!StringUtils.isEmpty(data)) {
                data = commonUtil.encodeHTMLEntities(data);
                retLi.add(data);
                return retLi;
            }
        } catch (Exception e) {
            logger.debug("Exceptionoccured : filterTopRootCauses() : " + e.getMessage());

        }
        return retLi;
    }
    
    @RequestMapping(value = "/owaspData.htm", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SecurityModel owaspData(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String engCode = "";
        String serviceCode = "";
        AssessmentModel assessmentModel = new AssessmentModel();
        List<AssessmentModel> rootList = null;
        SecurityModel data = null;
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if (null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                orgSchema = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
            
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
            }

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }
            assessmentModel.setEngagementCode(engCode);
            assessmentModel.setServiceCode(serviceCode);
            assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
            assessmentModel.setSelectedString(ReportsAndDashboardConstants.DB_CONTANT_SELECTED_VALUE);
            assessmentModel.setOrgSchema(orgSchema);
            data = assessmentService.owaspViewList(assessmentModel);
        } catch (Exception e) {
            logger.debug("Exceptionoccured : rootCauseData() : " + e.getMessage());
        }
        return data;
    }
    
    @RequestMapping(value = "/filteerOwaspViewData.htm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SecurityModel filteerOwaspViewData(HttpServletRequest request, HttpServletResponse response, @RequestBody List<SummaryDropDownModel> summaryDropDownModel) {
        HttpSession session = request.getSession();
        String engCode = "";
        String serviceCode = "";
        AssessmentModel assessmentModel = new AssessmentModel();
        List<String> ssidTextList = new ArrayList<>();
        List<AssessmentModel> rootList = null;
        SecurityModel data = null;
        String orgSchema = ApplicationConstants.EMPTY_STRING;
        try {
            if (null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA)) {
                orgSchema = request.getSession().getAttribute(ReportsAndDashboardConstants.ENG_SCHEMA).toString();
            }
            
            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE) != null) {
                engCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_ENG_CODE);
            }

            if (session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE) != null) {
                serviceCode = (String) session.getAttribute(ReportsAndDashboardConstants.SERVICE_CODE);
            }

            assessmentModel.setEngagementCode(engCode);
            assessmentModel.setServiceCode(serviceCode);
            for (SummaryDropDownModel sModel : summaryDropDownModel) {
                ssidTextList.add(sModel.getId() + "");

            }
            assessmentModel.setSelectedString(StringUtils.join(ssidTextList, ","));
            if (assessmentModel.getSelectedString().equalsIgnoreCase("")) {
                assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
            } else {

                if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_APPLICATION_VULNERABILITY_ASSESSMENT)) {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_APPLICATION);
                } else if (serviceCode.equalsIgnoreCase(ReportsAndDashboardConstants.SERVICE_CODE_WIRELESS_RISK_ASSESSMENT)) {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_SSID);
                } else {
                    assessmentModel.setAssessmentFlag(ReportsAndDashboardConstants.DB_CONTANT_SERVICE_ALL);
                }

            }
            assessmentModel.setOrgSchema(orgSchema);
            data = assessmentService.owaspViewList(assessmentModel);
        } catch (Exception e) {
            logger.debug("Exceptionoccured : filteerRootCauseData() : " + e.getMessage());
        }
        return data;
    }
}
