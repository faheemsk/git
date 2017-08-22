/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao;

import com.optum.oss.dto.AssessmentFindingsCountDTO;
import com.optum.oss.dto.RiskRegistryDashboardDTO;
import com.optum.oss.exception.AppException;
import java.util.List;

/**
 *
 * @author rpanuganti
 */
public interface RiskRegistryDashboardDAO {

    public AssessmentFindingsCountDTO fetchRiskRegistryFindingCount(String engCode, final String engSchema,final long userID) throws AppException;

    public RiskRegistryDashboardDTO registryDataByResponse(String engCode, final String engSchema,final long userID) throws AppException;

    public List<RiskRegistryDashboardDTO> registryDataByOwner(String engCode, final String engSchema,final long userID) throws AppException;

    public List<RiskRegistryDashboardDTO> registryDataBySeverity(String engCode, final String engSchema,final long userID) throws AppException;

    public List<RiskRegistryDashboardDTO> getRiskRegistryData(String engCode, final String engSchema, String sevCode,final long userID) throws AppException;
    
      public List riskRegistryDataForExportCSV(String engCode, final String engSchema, String flag,final long userID) throws AppException;

}
