/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service.impl;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dao.impl.CVSSCalculatorDAOImpl;
import com.optum.oss.dto.CVSSCalculatorDTO;
import com.optum.oss.dto.CVSSMetricsDTO;
import com.optum.oss.dto.VulnerabilityDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.CVSSCalculatorHelper;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.NumberUtils;
import org.apache.commons.math3.util.Precision;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hpasupuleti
 */
@Service
public class CVSSCalculatorServiceImpl {

    private static final Logger logger = Logger.getLogger(CVSSCalculatorServiceImpl.class);

    /*
     Start: Autowiring the required Class instances
     */
    @Autowired
    private CVSSCalculatorDAOImpl cvssCalculatorDAO;

    @Autowired
    private CVSSCalculatorHelper cvssCalcHelper;
    
    @Autowired
    private AnalystValidationServiceImpl analystValidService;
    
    /*
     End: Autowiring the required Class instances
     */
    final String cvssVersion = ApplicationConstants.CVSS_CALCULATOR_VERSION;

    final int DOUBLE_PRECISION = 1;

    public Map<String, List<CVSSCalculatorDTO>> prepareCVSSCalculatorData(final String metricNameVectorCode,
            final String metricValueVectorCode, final String cvssVector) throws AppException {
        Map<String, List<CVSSCalculatorDTO>> cvssMetricMap = new LinkedHashMap<>();
        try {
            List<CVSSCalculatorDTO> cvssMetricList = cvssCalculatorDAO.fetchAllCVSSMetricInformation(cvssVersion, metricNameVectorCode, metricValueVectorCode, cvssVector);

            if (!cvssMetricList.isEmpty()) {
                cvssMetricMap = cvssCalcHelper.prepareCalculatorData(cvssMetricList);
            } else {
                logger.debug("Exception occured : prepareCVSSCalculatorData():"
                        + "Problem fetching Metric Information.");
                throw new AppException("Exception occured : prepareCVSSCalculatorData():"
                        + "Problem fetching Metric Information.");
            }

        } catch (AppException e) {
            logger.debug("Exception occured : prepareCVSSCalculatorData(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "prepareCVSSCalculatorData(): " + e.getMessage());
        }

        return cvssMetricMap;
    }

    public VulnerabilityDTO CalculateCVSSMetricValues(final CVSSMetricsDTO cvssMetricObj,
            VulnerabilityDTO vulnerabilityDTO) throws AppException {
        double baseScr = 0;
        double tempoScr = 0;
        double enviScr = 0;

        double oImpact = 0;
        double cImpact = 0;
        double iImpact = 0;
        double aImpact = 0;

        double expScr = 0;
        double av = 0;
        double ac = 0;
        double au = 0;

        /**
         * *******************************************************
         */
        // CALCULATE IMPACT METRIC SUB SCORE
        /**
         * *******************************************************
         */
        if (NumberUtils.isNumber(cvssMetricObj.getConfidentialityImpact())) {
            cImpact = Double.parseDouble(cvssMetricObj.getConfidentialityImpact());
        }
        if (NumberUtils.isNumber(cvssMetricObj.getIntegrityImpact())) {
            iImpact = Double.parseDouble(cvssMetricObj.getIntegrityImpact());
        }
        if (NumberUtils.isNumber(cvssMetricObj.getAvailabilityImpact())) {
            aImpact = Double.parseDouble(cvssMetricObj.getAvailabilityImpact());
        }

        oImpact = 10.41 * (1 - (1 - cImpact) * (1 - iImpact) * (1 - aImpact));

        oImpact = Precision.round(oImpact, DOUBLE_PRECISION);
        /**
         * *******************************************************
         */
        /*########################################################*/

        /**
         * *******************************************************
         */
        // CALCULATE EXPLOITABILITY METRIC SUB SCORE
        /**
         * *******************************************************
         */
        if (NumberUtils.isNumber(cvssMetricObj.getAccessVector())) {
            av = Double.parseDouble(cvssMetricObj.getAccessVector());
        }
        if (NumberUtils.isNumber(cvssMetricObj.getAccessComplexity())) {
            ac = Double.parseDouble(cvssMetricObj.getAccessComplexity());
        }
        if (NumberUtils.isNumber(cvssMetricObj.getAuthentication())) {
            au = Double.parseDouble(cvssMetricObj.getAuthentication());
        }

        expScr = 20 * ac * au * av;

        expScr = Precision.round(expScr, DOUBLE_PRECISION);

        /**
         * *******************************************************
         */
        /*########################################################*/
        /**
         * *******************************************************
         */
        // CALCULATE BASE SCORE
        /**
         * *******************************************************
         */
        double fImpact = 0;

        if (oImpact <= 0) {
            fImpact = 0;
        } else {
            fImpact = 1.176;
        }

        baseScr = (0.6 * oImpact + 0.4 * expScr - 1.5) * fImpact;

        baseScr = Precision.round(baseScr, DOUBLE_PRECISION);

        /**
         * *******************************************************
         */
        /*########################################################*/
        /**
         * *******************************************************
         */
        // CALCULATE TEMPORAL SCORE
        /**
         * *******************************************************
         */
        double eValue = 0;
        double rlValue = 0;
        double rcValue = 0;

        if (NumberUtils.isNumber(cvssMetricObj.getExploitability())) {
            eValue = Double.parseDouble(cvssMetricObj.getExploitability());
        }
        if (NumberUtils.isNumber(cvssMetricObj.getRemediationLevel())) {
            rlValue = Double.parseDouble(cvssMetricObj.getRemediationLevel());
        }
        if (NumberUtils.isNumber(cvssMetricObj.getReportConfidence())) {
            rcValue = Double.parseDouble(cvssMetricObj.getReportConfidence());
        }

        tempoScr = baseScr * eValue * rlValue * rcValue;

        tempoScr = Precision.round(tempoScr, DOUBLE_PRECISION);

        /**
         * *******************************************************
         */
        /*########################################################*/
        /**
         * *******************************************************
         */
        // CALCULATE ENVIRONMENTAL SCORE
        /**
         * *******************************************************
         */
        double confReq = 0;
        double integReq = 0;
        double availReq = 0;
        double targDist = 0;
        double collDamage = 0;

        double adjImpt = 0;
        
        double adjBaseScore=0;
        double adjTempScore=0;
        double adjFnImpact=0;

        if (NumberUtils.isNumber(cvssMetricObj.getConfidentialityRequirement())) {
            confReq = Double.parseDouble(cvssMetricObj.getConfidentialityRequirement());
        }
        if (NumberUtils.isNumber(cvssMetricObj.getIntegrityRequirement())) {
            integReq = Double.parseDouble(cvssMetricObj.getIntegrityRequirement());
        }
        if (NumberUtils.isNumber(cvssMetricObj.getAvailabilityRequirement())) {
            availReq = Double.parseDouble(cvssMetricObj.getAvailabilityRequirement());
        }
        if (NumberUtils.isNumber(cvssMetricObj.getCollateralDamagePotential())) {
            collDamage = Double.parseDouble(cvssMetricObj.getCollateralDamagePotential());
        }
        if (NumberUtils.isNumber(cvssMetricObj.getTargetDistribution())) {
            targDist = Double.parseDouble(cvssMetricObj.getTargetDistribution());
        }

        adjImpt = 10.41 * (1 - (1 - (cImpact * confReq)) * (1 - (iImpact * integReq)) * (1 - (availReq * aImpact)));

        adjImpt = Precision.round(adjImpt, DOUBLE_PRECISION);

        if (adjImpt > 10) {
            adjImpt = 10;
        }

        if(adjImpt <= 0)
        {
            adjFnImpact = 0;
        }
        else
        {
            adjFnImpact = 1.176;
        }
        
        adjBaseScore = (0.6 * adjImpt + 0.4 * expScr - 1.5) * adjFnImpact;
        adjBaseScore = Precision.round(adjBaseScore, DOUBLE_PRECISION);
        
        adjTempScore = adjBaseScore * eValue * rlValue * rcValue;
        adjTempScore = Precision.round(adjTempScore, DOUBLE_PRECISION);

        double modImpt = 10 - adjTempScore;

        enviScr = (adjTempScore + (modImpt * collDamage)) * targDist;

        enviScr = Precision.round(enviScr, DOUBLE_PRECISION);
        /**
         * *******************************************************
         */
        /*########################################################*/
        double overallScr = 0;

        if (enviScr > 0) {
            overallScr = enviScr;
        } else if (tempoScr > 0) {
            overallScr = tempoScr;
        } else {
            overallScr = baseScr;
        }

        logger.info("In CalculateCVSSMetricValues:baseScr>" + baseScr);
        logger.info("In CalculateCVSSMetricValues:tempoScr>" + tempoScr);
        logger.info("In CalculateCVSSMetricValues:enviScr>" + enviScr);
        logger.info("In CalculateCVSSMetricValues:overallScr>" + overallScr);

        /*
         * IF ATLEAST ONE OF Confidentiality Impact, Integrity Impact, Availability Impact IS SELECTED
         * THEN ONLY WE WILL HAVE IMPACT SUBSCORE 
        */
        if (NumberUtils.isNumber(cvssMetricObj.getConfidentialityImpact()) ||
                NumberUtils.isNumber(cvssMetricObj.getIntegrityImpact()) ||
                NumberUtils.isNumber(cvssMetricObj.getAvailabilityImpact())) {
            vulnerabilityDTO.getCveInformationDTO().setImpSubScore(BigDecimal.valueOf(oImpact));
        } else {
            vulnerabilityDTO.getCveInformationDTO().setImpSubScore(null);
        }
        
        /*
         * IF ATLEAST ONE OF Access Vector, Access Complexity, Authentication IS SELECTED
         * THEN ONLY WE WILL HAVE Exploitability Subscore 
        */
        if (NumberUtils.isNumber(cvssMetricObj.getAccessVector()) ||
                NumberUtils.isNumber(cvssMetricObj.getAccessComplexity()) ||
                NumberUtils.isNumber(cvssMetricObj.getAuthentication())) {
            vulnerabilityDTO.getCveInformationDTO().setExpSubScore(BigDecimal.valueOf(expScr));
        } else {
            vulnerabilityDTO.getCveInformationDTO().setExpSubScore(null);
        }
        
        /*
         * IF ATLEAST ONE OF IMPACT SUBSCORE AND Exploitability Subscore EXISTS
         * THEN ONLY WE HAVE BASE SCORE
        */
        if (vulnerabilityDTO.getCveInformationDTO().getImpSubScore() != null &&
                vulnerabilityDTO.getCveInformationDTO().getExpSubScore()!= null) {
            vulnerabilityDTO.getCveInformationDTO().setBaseScore(BigDecimal.valueOf(baseScr));
        } else {
            vulnerabilityDTO.getCveInformationDTO().setImpSubScore(null);
            vulnerabilityDTO.getCveInformationDTO().setExpSubScore(null);
            vulnerabilityDTO.getCveInformationDTO().setBaseScore(null);
        }

        if (NumberUtils.isNumber(cvssMetricObj.getExploitability()) ||
                NumberUtils.isNumber(cvssMetricObj.getRemediationLevel()) ||
                NumberUtils.isNumber(cvssMetricObj.getReportConfidence())) {
            vulnerabilityDTO.getCveInformationDTO().setTemporalScore(BigDecimal.valueOf(tempoScr));
        } else {
            vulnerabilityDTO.getCveInformationDTO().setTemporalScore(null);
        }
        
        if (NumberUtils.isNumber(cvssMetricObj.getConfidentialityRequirement()) ||
                NumberUtils.isNumber(cvssMetricObj.getIntegrityRequirement()) ||
                NumberUtils.isNumber(cvssMetricObj.getAvailabilityRequirement()) ||
                NumberUtils.isNumber(cvssMetricObj.getTargetDistribution()) ||
                NumberUtils.isNumber(cvssMetricObj.getCollateralDamagePotential())) {
            vulnerabilityDTO.getCveInformationDTO().setEnvironmentalScore(BigDecimal.valueOf(enviScr));
        } else {
            vulnerabilityDTO.getCveInformationDTO().setEnvironmentalScore(null);
        }
        
        if (vulnerabilityDTO.getCveInformationDTO().getEnvironmentalScore() != null) {
            vulnerabilityDTO.getCveInformationDTO().setOverallScore(BigDecimal.valueOf(enviScr));
        } else if (vulnerabilityDTO.getCveInformationDTO().getTemporalScore() != null) {
            vulnerabilityDTO.getCveInformationDTO().setOverallScore(BigDecimal.valueOf(tempoScr));
        } else if (vulnerabilityDTO.getCveInformationDTO().getBaseScore() != null){
             vulnerabilityDTO.getCveInformationDTO().setOverallScore(BigDecimal.valueOf(baseScr));
        } else {
            vulnerabilityDTO.getCveInformationDTO().setOverallScore(null);
        }
        //vulnerabilityDTO.getCveInformationDTO().setBaseScore(BigDecimal.valueOf(baseScr));
        //vulnerabilityDTO.getCveInformationDTO().setTemporalScore(BigDecimal.valueOf(tempoScr));
        //vulnerabilityDTO.getCveInformationDTO().setEnvironmentalScore(BigDecimal.valueOf(enviScr));
        //vulnerabilityDTO.getCveInformationDTO().setOverallScore(BigDecimal.valueOf(overallScr));

        //vulnerabilityDTO.getCveInformationDTO().setImpSubScore(BigDecimal.valueOf(oImpact));
        //vulnerabilityDTO.getCveInformationDTO().setExpSubScore(BigDecimal.valueOf(expScr));
        
        vulnerabilityDTO.setVectorText(cvssMetricObj.getCVSSVector());
        
        if (vulnerabilityDTO.getCveInformationDTO().getOverallScore() != null
                || vulnerabilityDTO.getCveInformationDTO().getExpSubScore() != null
                || vulnerabilityDTO.getCveInformationDTO().getImpSubScore() != null) {
            vulnerabilityDTO = analystValidService.getRiskDetailsByCvssScore(vulnerabilityDTO);
        } else {
            vulnerabilityDTO.getCveInformationDTO().setSeverityCode(null);
            vulnerabilityDTO.getCveInformationDTO().setSeverityName(null);
            vulnerabilityDTO.getCveInformationDTO().setProbabilityCode(null);
            vulnerabilityDTO.getCveInformationDTO().setProbabilityName(null);
            vulnerabilityDTO.getCveInformationDTO().setImpactCode(null);
            vulnerabilityDTO.getCveInformationDTO().setImpactName(null);
        }
        
        return vulnerabilityDTO;
    }

}
