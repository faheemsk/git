/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao;

import com.optum.oss.dto.AssessmentFindingsCountDTO;
import com.optum.oss.dto.RemediationDashboardDTO;
import com.optum.oss.dto.RiskRegistryDashboardDTO;
import com.optum.oss.exception.AppException;
import java.util.List;

/**
 *
 * @author rpanuganti
 */
public interface RemediationDashboardDAO {

    public AssessmentFindingsCountDTO fetchRemediationDashBoardFindingCount(String engCode, final String engSchema,final long userID) throws AppException;

    public RemediationDashboardDTO remediationPlanCompletion(String engCode, final String engSchema,final long userID) throws AppException;

    public List<RemediationDashboardDTO> remediationOwnerDataByPlanStatus(String engCode, final String engSchema,final long userID) throws AppException;

    public List<RemediationDashboardDTO> remediationPlansByStatusAndSeverity(String engCode, final String engSchema,final long userID) throws AppException;

    public List<RemediationDashboardDTO> remediationPlansByDaysAndSeverity(String engCode, final String engSchema,final long userID) throws AppException;
}
