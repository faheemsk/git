/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.service.impl;

import com.optum.oss.constants.ReportsAndDashboardConstants;
import com.optum.oss.dao.impl.ManageEngagementsDAOImpl;
import com.optum.oss.dao.impl.RoadMapDAOImpl;
import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.RoadMapDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.model.FindingsModel;
import com.optum.oss.model.SecurityModel;
import com.optum.oss.service.RoadMapService;
import com.optum.oss.util.EncryptDecrypt;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sbhagavatula
 */
@Service
public class RoadMapServiceImpl implements RoadMapService{
    
    public static final Logger logger = Logger.getLogger(RoadMapServiceImpl.class);
    
    @Autowired
    private ManageEngagementsDAOImpl manageEngagementsDAO;
    @Autowired
    private RoadMapDAOImpl roadMapDAO;
    @Autowired
    private EncryptDecrypt encDec;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public ClientEngagementDTO engagementInfoByEngCode(RoadMapDTO roadMapDTO) throws AppException {
        roadMapDTO.getClientEngagementDTO().setEngagementCode(encDec.decrypt(roadMapDTO.getClientEngagementDTO().getEncEngagementCode()));
        roadMapDTO.getClientEngagementDTO().setOrgSchema(roadMapDTO.getOrgSchema());
        return manageEngagementsDAO.viewEngagementByEngmtKey(roadMapDTO.getClientEngagementDTO());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<RoadMapDTO> roadmapCategoryByEngCode(RoadMapDTO roadMapDTO) throws AppException {
        roadMapDTO.setCsaDomainCode("");
        roadMapDTO.setRootCauseCode("");
        return roadMapDAO.fetchRoadmapCategory(roadMapDTO);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int createRoadmapCategory(RoadMapDTO roadMapDTO, UserProfileDTO userDto) throws AppException {
        int retval = 0;
        roadMapDTO.getClientEngagementDTO().setEngagementCode(encDec.decrypt(roadMapDTO.getClientEngagementDTO().getEncEngagementCode()));
        List<RoadMapDTO> roadmapList = roadMapDAO.generateRoadmapCategory(roadMapDTO);
        //Start: Delete roadmap categories
        List<RoadMapDTO> roadMapDelList = new ArrayList<>();
        roadMapDelList.add(roadMapDTO);
        roadMapDAO.saveUpdateRoadmapCategory(roadMapDelList, userDto, "D");
        //End: Delete roadmap categories
        
        //Insert roadmap categories
        retval = roadMapDAO.saveUpdateRoadmapCategory(roadmapList, userDto, "I");
        return retval;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<RoadMapDTO> fetchRoadmapInfo(RoadMapDTO roadMapDTO) throws AppException {
        return roadMapDAO.fetchRoadmapCategory(roadMapDTO);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<MasterLookUpDTO> timeLineList() throws AppException {
        return roadMapDAO.timeLineList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int updateRoadmapCategory(RoadMapDTO roadMapDTO, UserProfileDTO userDto) throws AppException {
        roadMapDTO.getClientEngagementDTO().setEngagementCode(encDec.decrypt(roadMapDTO.getClientEngagementDTO().getEncEngagementCode()));
        List<RoadMapDTO> roadMapList = new ArrayList<>();
        roadMapList.add(roadMapDTO);
        return roadMapDAO.saveUpdateRoadmapCategory(roadMapList, userDto, "U");
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<FindingsModel> getFindingsByCategory(SecurityModel securityModel) throws AppException {
        return roadMapDAO.getFindingsByCategory(securityModel);
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<FindingsModel> roadMapDataForExportCSV(SecurityModel securityModel) throws AppException {
        return roadMapDAO.roadMapDataForExportCSV(securityModel);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public String fetchRoadMapSummaryChartData(RoadMapDTO roadMapDTO) throws AppException {
        logger.info("START: RoadMapServiceImpl : fetchRoadMapSummaryChartData");
        String dataString = "";
        roadMapDTO.setCsaDomainCode("");
        roadMapDTO.setRootCauseCode("");
        
        LinkedHashSet<String> csaDomainCodeSet = new LinkedHashSet<String>();
        LinkedHashSet<String> csaDomainNameSet = new LinkedHashSet<String>();
        ArrayList<String> timelineCodeList = new ArrayList<String>();
try{
        List<RoadMapDTO> categeryList = roadMapDAO.fetchRoadmapCategory(roadMapDTO);
        
     if(categeryList!=null && categeryList.size()>0){   
        for (RoadMapDTO rmDTO : categeryList) {
            csaDomainCodeSet.add(rmDTO.getCsaDomainCode());
            csaDomainNameSet.add(rmDTO.getCsaDomainName());
        }
        
//        List<MasterLookUpDTO> timelineCodeListDTO=roadMapDAO.timeLineList();
//        for (MasterLookUpDTO masterLookUpDTO : timelineCodeListDTO) {
//            timelineCodeList.add(masterLookUpDTO.getMasterLookupCode());
//        }
        //Time Lince Codes adding to the list
        timelineCodeList.add("S");
        timelineCodeList.add("M");
        timelineCodeList.add("L");
        
        
       LinkedHashMap<String,String> categoryPositionMap=roadMapChartCategoryPositionMap(csaDomainCodeSet, timelineCodeList);
       String domainStringData=roadMapChartDomainPosition(csaDomainNameSet);
       String critialCatString="";
       String highCatString="";
       String mediumCatString="";
       String lowCatString="";
       
       float chartBuubleScale=ReportsAndDashboardConstants.ROADMAP_HIGH_COST_EFFORT_BUBBLE_SIZE;
       boolean highExist=false;
       boolean mediumExist=false;
       boolean lowExist=false;
       
       float x_axis=0.0f;
       float y_axis=0.0f;
       
        for (RoadMapDTO categoryDTO : categeryList) {
            String severity = categoryDTO.getCveInformationDTO().getSeverityName();
            String costEffort=categoryDTO.getCveInformationDTO().getCostEffortName();
            String categoryName = categoryDTO.getRootCauseName();
            String categotyID=categoryDTO.getRootCauseCode();
            String mapKey=categoryDTO.getCsaDomainCode()+""+categoryDTO.getTimeLineCode()+""+categotyID;
            String x_y_postion=categoryPositionMap.get(mapKey);
            
            String arr[]=x_y_postion.split(ReportsAndDashboardConstants.ROAD_MAP_CHART_X_AND_Y_SPLIT_CONSTANT);
            x_axis = Float.parseFloat(arr[0]);
            y_axis = Float.parseFloat(arr[1]);
            
            float z_axis = 0.15f;
            if (costEffort.equalsIgnoreCase(ReportsAndDashboardConstants.COST_EFFORT_HIGH)) {
                z_axis = (float) ReportsAndDashboardConstants.ROADMAP_HIGH_COST_EFFORT_BUBBLE_SIZE;
                highExist=true;
            } else if (costEffort.equalsIgnoreCase(ReportsAndDashboardConstants.COST_EFFORT_MEDIUM)) {
                z_axis = (float) ReportsAndDashboardConstants.ROADMAP_MEDIUM_COST_EFFORT_BUBBLE_SIZE;
                mediumExist=true;
            } else if (costEffort.equalsIgnoreCase(ReportsAndDashboardConstants.COST_EFFORT_LOW)) {
                z_axis = (float) ReportsAndDashboardConstants.ROADMAP_LOW_COST_EFFORT_BUBBLE_SIZE;
                lowExist=true;
            }
            
            //bubble scale based on the Cost Effort
            if(highExist && mediumExist && lowExist){
            chartBuubleScale=(float)ReportsAndDashboardConstants.ROADMAP_HIGH_COST_EFFORT_BUBBLE_SIZE;
            }else if(highExist && mediumExist){
            chartBuubleScale=(float)ReportsAndDashboardConstants.ROADMAP_HIGH_COST_EFFORT_BUBBLE_SIZE;
            }else if(highExist && lowExist){
            chartBuubleScale=(float)ReportsAndDashboardConstants.ROADMAP_HIGH_COST_EFFORT_BUBBLE_SIZE;
            }else if(mediumExist && lowExist){
            chartBuubleScale=(float) ReportsAndDashboardConstants.ROADMAP_MEDIUM_COST_EFFORT_BUBBLE_SIZE;
            }else if(highExist){
            chartBuubleScale=(float) ReportsAndDashboardConstants.ROADMAP_HIGH_COST_EFFORT_BUBBLE_SIZE;
            }else if(mediumExist){
            chartBuubleScale=(float) ReportsAndDashboardConstants.ROADMAP_MEDIUM_COST_EFFORT_BUBBLE_SIZE;
            }else if(lowExist){
            chartBuubleScale=(float) ReportsAndDashboardConstants.ROADMAP_LOW_COST_EFFORT_BUBBLE_SIZE;
            }
            
            if (severity.equalsIgnoreCase(ReportsAndDashboardConstants.FINDING_CRITICAL)) {
                critialCatString = critialCatString + "<set x='" + x_axis + "' y='" + y_axis + "' z='"+z_axis+"' name='" + categoryName + "' link=\"JavaScript:categoryDetailsByID("+categotyID+",'"+categoryName+"')\" />";
            } else if (severity.equalsIgnoreCase(ReportsAndDashboardConstants.FINDING_HIGH)) {
                highCatString = highCatString + "<set x='" + x_axis + "' y='" + y_axis + "' z='"+z_axis+"' name='" + categoryName + "' link=\"JavaScript:categoryDetailsByID("+categotyID+",'"+categoryName+"')\" />";
            } else if (severity.equalsIgnoreCase(ReportsAndDashboardConstants.FINDING_MEDIUM)) {
                mediumCatString = mediumCatString + "<set x='" + x_axis + "' y='" + y_axis + "' z='"+z_axis+"' name='" + categoryName + "' link=\"JavaScript:categoryDetailsByID("+categotyID+",'"+categoryName+"')\" />";
            } else if (severity.equalsIgnoreCase(ReportsAndDashboardConstants.FINDING_LOW)) {
                lowCatString = lowCatString + "<set x='" + x_axis + "' y='" + y_axis + "' z='"+z_axis+"' name='" + categoryName + "' link=\"JavaScript:categoryDetailsByID("+categotyID+",'"+categoryName+"')\" />";
            }
            
        }

        dataString = "<chart canvasBgColor='#bfbfbf,#d9d9d9,#ffffff'   basefont='arial'  valueFontSize='1' valueFontColor='cccccc' plottooltext='$name'  chartBottomMargin='0' bgAlpha='0' chartLeftMargin='0'  labelPadding='-10' alignCaptionWithCanvas='1' bubblescale='"+chartBuubleScale+"' canvasbgalpha='100' canvasbgangle='-90' alternateHGridColor='d9d9d9'  showAlternateHGridColor='1' xAxisName='' yAxisName='' showLegend='0' legendbgalpha='0' legendborderalpha='0' legendshadow='1'  usePlotGradientColor='1' plotFillAlpha='100' plotBorderAlpha='100' plotBorderThickness='2' use3DLighting='0'  basefontsize='12' xAxisNamePadding='15' xAxisLabelDisplay='auto'  captionhorizontalpadding='0' rotateyaxisname='1' centerYaxisName='0' divLineDashed='1' numDivLines='2' showAlternateVGridColor='0'  numberPrefix=''  showLabels='1' showValues='0'   bgColor='ffffff' borderThickness='' showYAxisValues='0'  canvasBorderThickness='1' canvasBorderColor='cccccc' borderColor='ffffff' yAxisMaxValue='12' xAxisMinValue='0' xAxisMaxValue='12'>"
                + "<categories verticalLineColor='a6a6a6'  verticalLineAlpha='100'>"+domainStringData+"</categories>"
                + "<dataSet color='a32a2e' seriesName='Critical' showValues='0' >" +critialCatString+ "</dataSet>"
                + " <dataSet color='e87722' seriesName='High' showValues='0'>" +highCatString+ "</dataSet>"
                + "<dataSet color='f2b411' seriesName='Medium'  showValues='0'>" +mediumCatString+ "</dataSet>"
                + "<dataSet color='627D32'  seriesName='Low' showValues='0'>" +lowCatString+ "</dataSet>"
                + "<trendlines>"
                + "<line startValue='2.0' endValue='2.0'  color='666666'  thickness='0' displayValue='Short Term' alpha='100' showOnTop='0'/>"
                + "<line startValue='6.0' endValue='6.0'  color='666666'  thickness='0' displayValue='Medium Term' alpha='100' showOnTop='0'/>"
                + " <line startValue='10.0' endValue='10.0'  color='666666'  thickness='0' displayValue='Long Term' alpha='100' showOnTop='0'/>"
                + "</trendlines>"
                + "<vTrendlines>"
                + "<line startValue='1.6' endValue='1.6' color='666666' displayValue='Low' trendValueAlpha='100'  thickness='0' alpha='0' />"
                + "</vTrendlines>"
                + "</chart>";
        
     }else{
     dataString = "<chart canvasBgColor='#bfbfbf,#d9d9d9,#ffffff'   basefont='arial'  valueFontSize='1' valueFontColor='cccccc' plottooltext='$name'  chartBottomMargin='0' bgAlpha='0' chartLeftMargin='0'  labelPadding='-10' alignCaptionWithCanvas='1' bubblescale='0.15' canvasbgalpha='100' canvasbgangle='-90' alternateHGridColor='d9d9d9'  showAlternateHGridColor='1' xAxisName='' yAxisName='' showLegend='0' legendbgalpha='0' legendborderalpha='0' legendshadow='1'  usePlotGradientColor='1' plotFillAlpha='100' plotBorderAlpha='100' plotBorderThickness='2' use3DLighting='0'  basefontsize='12' xAxisNamePadding='15' xAxisLabelDisplay='auto'  captionhorizontalpadding='0' rotateyaxisname='1' centerYaxisName='0' divLineDashed='1' numDivLines='2' showAlternateVGridColor='0'  numberPrefix=''  showLabels='1' showValues='0'   bgColor='ffffff' borderThickness='' showYAxisValues='0'  canvasBorderThickness='1' canvasBorderColor='cccccc' borderColor='ffffff' yAxisMaxValue='12' xAxisMaxValue='12'>"
                + "</chart>";
     
     }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : fetchRoadMapSummaryChartData : " + e.getMessage());
            dataString = "<chart canvasBgColor='#bfbfbf,#d9d9d9,#ffffff'   basefont='arial'  valueFontSize='1' valueFontColor='cccccc' plottooltext='$name'  chartBottomMargin='0' bgAlpha='0' chartLeftMargin='0'  labelPadding='-10' alignCaptionWithCanvas='1' bubblescale='0.15' canvasbgalpha='100' canvasbgangle='-90' alternateHGridColor='d9d9d9'  showAlternateHGridColor='1' xAxisName='' yAxisName='' showLegend='0' legendbgalpha='0' legendborderalpha='0' legendshadow='1'  usePlotGradientColor='1' plotFillAlpha='100' plotBorderAlpha='100' plotBorderThickness='2' use3DLighting='0'  basefontsize='12' xAxisNamePadding='15' xAxisLabelDisplay='auto'  captionhorizontalpadding='0' rotateyaxisname='1' centerYaxisName='0' divLineDashed='1' numDivLines='2' showAlternateVGridColor='0'  numberPrefix=''  showLabels='1' showValues='0'   bgColor='ffffff' borderThickness='' showYAxisValues='0'  canvasBorderThickness='1' canvasBorderColor='cccccc' borderColor='ffffff' yAxisMaxValue='12' xAxisMaxValue='12'>"
                    + "</chart>";
        }
logger.info("END: RoadMapServiceImpl : fetchRoadMapSummaryChartData");
        return dataString;
    }
    
        private static LinkedHashMap<String,String> roadMapChartCategoryPositionMap(Set<String> csaDomainCodeSet,List<String> timelineCodeList) {

            List<String> csaDomainCodeList = new ArrayList<>(csaDomainCodeSet);
            
        LinkedHashMap<String,String> x_y_positions_map = new LinkedHashMap<String,String>();
        
        int domainSize = csaDomainCodeList.size();;
        int x_axis_max = ReportsAndDashboardConstants.ROAD_MAP_CHART_X_AXIS_MAX_VALUE;

//      Divide Quadrants
        float domain_x_axis_count = (float) x_axis_max / domainSize;
        float space_bubble_x_axis = (float) domain_x_axis_count / 6;
        
        float x_axis_min = 0;
        float y_axis_min = 0;
        
        int timelineSize = timelineCodeList.size();
        float y_axis_max =  ReportsAndDashboardConstants.ROAD_MAP_CHART_Y_AXIS_MAX_VALUE;
        
        float domain_y_axis_count = (float) y_axis_max / timelineSize;
        float space_bubble_y_axis = (float) domain_y_axis_count / 7;

        float x_axis_position_current = 0.0f;
        float x_axis_position = 0.0f;
        String s = "";
        int count = 0;
        for (int i = 0; i < domainSize; i++) {
            //Domain Start
            String domainCode=csaDomainCodeList.get(i);
            float y_axis_position = 0.0f;
            x_axis_position_current = x_axis_position_current;
            
            for (int j = 0; j < timelineSize; j++) {
                //TIME LINE START 
                int categoryID = 1;
                String timeLIneCode=timelineCodeList.get(j);
                for (int y_count = 0; y_count <= 6; y_count++) {
                    //YAXIS-POSITION
                    y_axis_position = y_axis_position + space_bubble_y_axis;
                    if (y_count < 6) {
                        x_axis_position = x_axis_position_current;
                        for (int x_count = 0; x_count <= 5; x_count++) {
                            //XAXIS POSITION
                            x_axis_position = x_axis_position + space_bubble_x_axis;
                            if (x_count < 5) {
//                                qmap.put(i + "" + j + "" + categoryID, "(" + x_axis_position + "," + y_axis_position + ")");
                                x_y_positions_map.put(domainCode+""+timeLIneCode+""+ categoryID, x_axis_position +ReportsAndDashboardConstants.ROAD_MAP_CHART_X_AND_Y_SPLIT_CONSTANT + y_axis_position);
                                categoryID++;
                                count++;
                            }

                        }
                    }

                }

            }
            x_axis_position_current = x_axis_position;

        }
        return x_y_positions_map;
    }

        
         public static String roadMapChartDomainPosition(Set<String> csaDomainCodeSet) {
             
             List<String> csaDomainCodeList = new ArrayList<>(csaDomainCodeSet);

             int domainSize = csaDomainCodeList.size();
             double pos = 0;
             int x_axis_max = ReportsAndDashboardConstants.ROAD_MAP_CHART_X_AXIS_MAX_VALUE;

             float eachDoaminSize = (float) x_axis_max / domainSize;
             float space_bubble_x_axis = (float) eachDoaminSize / 6;
             float val = 0;
             String s = "";
             int domainIndex=0;
             for (int count = 1; count <= domainSize - 1; count++) {
                 String domianName=csaDomainCodeList.get(domainIndex);
                 val = val + eachDoaminSize;
                 float linepos = val - eachDoaminSize;
                 pos = (space_bubble_x_axis * 3) + linepos;
                 s = s + "<category label='"+domianName + "' x='" + pos + "'/>"
                         + "<category label=''  x='" + val + "' sL='1' showVerticalLine='1' lineDashed='1'/>";
                 domainIndex++;
             }
              String domianName=csaDomainCodeList.get(domainIndex);
             val = val + eachDoaminSize;
             float linepos = val - eachDoaminSize;
             pos = (space_bubble_x_axis * 3) + linepos;
             s = s + "<category label='"+domianName+"' x='" + pos + "'/>";
             return s;
    }
}
