/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.dao.impl;

import com.optum.oss.dao.CVSSCalculatorDAO;
import com.optum.oss.dto.CVSSCalculatorDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.CVSSCalculatorHelper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author hpasupuleti
 */
public class CVSSCalculatorDAOImpl implements CVSSCalculatorDAO{
    
    
    private static final Logger logger = Logger.getLogger(CVSSCalculatorDAOImpl.class);

    private JdbcTemplate jdbcTemplate;
    /*
     Start : Setter getters for private variables
     */

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    /*
     End : Setter getters for private variables
     */
    /*
     Start : Autowired Classes
     */
    @Autowired
    private CVSSCalculatorHelper cvssHelper;
    /*
     End : Autowired Classes
     */
    
    /**
     * This method is used to Fetch all the Metric related information from the DB for Calculator
     * 
     * @param cvssVersion
     * @param metricNameVectorCode
     * @param metricValueVectorCode
     * @param cvssVector
     * @return  List<CVSSCalculatorDTO>
     * @throws AppException
     */
    @Override
    public List<CVSSCalculatorDTO> fetchAllCVSSMetricInformation(final String cvssVersion,final String metricNameVectorCode,
                    final String metricValueVectorCode,final String cvssVector) throws AppException
    {
        logger.info("Start: fetchAllCVSSMetricInformation");
        String procName="{CALL Analyst_GetCVSSMetricScore(?,?,?)}";
        List<CVSSCalculatorDTO> cvssMetricList = new ArrayList<>();
        try
        {
            logger.info("In: fetchAllCVSSMetricInformation:cvssVersion:"+cvssVersion);
            
            List<String> cvssVectorLi = cvssHelper.SplitCVSSVector(cvssVector);
            
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName,
                                new Object[]{cvssVersion,
                                            metricNameVectorCode,
                                            metricValueVectorCode});
            
            if(!resultList.isEmpty())
            {
                for(Map<String, Object> retMap : resultList)
                {
                    CVSSCalculatorDTO cvssCalcDTO = new CVSSCalculatorDTO();
                    
                    cvssCalcDTO.setCvssVersion((String)(retMap.get("CVSS_VER")));
                    cvssCalcDTO.setMetricGroupName((String)(retMap.get("MTRC_GRP_NM")));
                    cvssCalcDTO.setMetricName((String)(retMap.get("MTRC_NM")));
                    cvssCalcDTO.setMetricNameVectorNotation((String)(retMap.get("MTRC_VCTR_CD")));
                    cvssCalcDTO.setMetricValue((String)(retMap.get("MTRC_VAL_TXT")));
                    cvssCalcDTO.setMetricValueVectorNotation((String)(retMap.get("MTRC_VAL_VCTR_CD")));
                    cvssCalcDTO.setMetricScoreValue(((BigDecimal)(retMap.get("MTRC_VAL_SCOR"))).toString());
                    String metricNameValueNotation =  cvssCalcDTO.getMetricNameVectorNotation()+":"+
                                                cvssCalcDTO.getMetricValueVectorNotation();
                    cvssCalcDTO.setMetricNameValueVectorNotation(metricNameValueNotation);
                    cvssCalcDTO.setVectorOrder((Integer)(retMap.get("VCTR_ORDR")));
                    cvssCalcDTO.setDefaultChecked(0);
                    if (!cvssVectorLi.isEmpty()) {
                        if (cvssVectorLi.contains(metricNameValueNotation.trim())) {
                            cvssCalcDTO.setDefaultChecked(1);
                        } else {
                            cvssCalcDTO.setDefaultChecked(0);
                        }
                    }
                    
                    if(!StringUtils.isEmpty(cvssCalcDTO.getMetricName()))
                    {
                        String fMetricName = cvssCalcDTO.getMetricName().replaceAll("\\s+","");
                        cvssCalcDTO.setFormulaMetricName(fMetricName);
                    }
                    
                    cvssMetricList.add(cvssCalcDTO);
                }
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : fetchAllCVSSMetricInformation(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "fetchAllCVSSMetricInformation(): " + e.getMessage());
        }
        
        return cvssMetricList;
    }
    
}
