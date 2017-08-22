/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao;

import com.optum.oss.dto.CVSSCalculatorDTO;
import com.optum.oss.exception.AppException;
import java.util.List;

/**
 *
 * @author hpasupuleti
 */
public interface CVSSCalculatorDAO {

    public List<CVSSCalculatorDTO> fetchAllCVSSMetricInformation(final String cvssVersion,
            final String metricNameVectorCode,
            final String metricValueVectorCode,
            final String cvssVector) throws AppException;

}
