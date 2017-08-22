/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service;

import com.optum.oss.dto.AssessmentFindingsCountDTO;
import com.optum.oss.dto.RemediationDashboardDTO;
import com.optum.oss.exception.AppException;
import java.util.List;

/**
 *
 * @author rpanuganti
 */
public interface RemediationDashboardService {

    public AssessmentFindingsCountDTO fetchRemediationDashBoardFindingCount(String engCode, final String engSchema,final long userID) throws AppException;

    public String remediationPlanCompletion(String engCode, final String engSchema,final long userID) throws AppException;

    public String remediationOwnerDataByPlanStatus(String engCode, final String engSchema,final long userID) throws AppException;

    public String remediationPlansByStatusAndSeverity(String engCode, final String engSchema,final long userID) throws AppException;
    
    public String remediationPlansByDaysAndSeverity(String engCode, final String engSchema,final long userID) throws AppException;
}
