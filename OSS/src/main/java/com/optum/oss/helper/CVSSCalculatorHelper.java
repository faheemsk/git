/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.helper;

import com.optum.oss.dto.CVSSCalculatorDTO;
import com.optum.oss.exception.AppException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 *
 * @author hpasupuleti
 */
@Component
public class CVSSCalculatorHelper {
    
    private static final Logger logger = Logger.getLogger(CVSSCalculatorHelper.class);
    
    public List<String> SplitCVSSVector(final String cvssVector) throws AppException
    {
        List<String> retList = new ArrayList<>();
        if(!StringUtils.isEmpty(cvssVector))
        {
           String splitArr[]  =  cvssVector.split("/");
           for(String strValue : splitArr)
           {
               if(strValue.contains("("))
               {
                   strValue = strValue.replace("(", "");
               }
               if(strValue.contains(")"))
               {
                   strValue = strValue.replace(")", "");
               }
               
               retList.add(strValue.trim());
           }
        }
        return retList;
    }
    
    
    
    
    public Map<String,List<CVSSCalculatorDTO>> prepareCalculatorData
            (final List<CVSSCalculatorDTO> cvssMetricList) throws AppException
    {
        Map<String,List<CVSSCalculatorDTO>> cvssMetricMap = new LinkedHashMap<>();
        
        if(!cvssMetricList.isEmpty())
        {
            List<CVSSCalculatorDTO> cvssCalcLi = new ArrayList<>();
            String metricName="";
            String prevMetricName="";
            for(CVSSCalculatorDTO cvssCalcObj:cvssMetricList)
            {
                metricName = cvssCalcObj.getMetricName();
                
                if(metricName.equalsIgnoreCase(prevMetricName))
                {
                    cvssCalcLi.add(cvssCalcObj);
                }
                else
                {
                    prevMetricName = metricName;
                    cvssCalcLi = new ArrayList<>();
                    cvssCalcLi.add(cvssCalcObj);
                }
                cvssMetricMap.put(metricName, cvssCalcLi);
            }
        }
        //logger.info("prepareCalculatorData:cvssMetricMap>"+cvssMetricMap);
        return cvssMetricMap;
    }
}
