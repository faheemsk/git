/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.dao;

import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.RemediationDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.dto.VulnerabilityDTO;
import com.optum.oss.exception.AppException;
import java.util.List;

/**
 *
 * @author sbhagavatula
 */
public interface RemediationDAO {
    
    public List<MasterLookUpDTO> fetchSeverityList() throws AppException;
    public List<MasterLookUpDTO> fetchOSCatList() throws AppException;
    public List<VulnerabilityDTO> findingLookup(RemediationDTO remediationDto) throws AppException;
    public List<VulnerabilityDTO> findingsByMultipleIds(RemediationDTO remediationDto) throws AppException;
    public int saveActionPlanInfo(RemediationDTO remediationDto,UserProfileDTO sessionDTO)throws AppException;
    public List<RemediationDTO> remediationPlanList(String engagementCode, String orgSchema, String flag,UserProfileDTO sessionDTO) throws AppException;
    public int saveRemediatePlanFindings(RemediationDTO remediationDto,UserProfileDTO sessionDTO) throws AppException;
    public int savePlanNotes(RemediationDTO remediationDto,UserProfileDTO sessionDTO) throws AppException;
    public RemediationDTO planInfoByPlanKey(String planKey, String orgSchema) throws AppException;
    public List<RemediationDTO> planNotesByPlanKey(String planKey, String orgSchema) throws AppException;
    public List<VulnerabilityDTO> findingsByPlanKey(String planKey, String orgSchema) throws AppException;
    public int updateActionPlanInfo(RemediationDTO remediationDto,UserProfileDTO sessionDTO)throws AppException;
    public int deletePlanFindings(RemediationDTO remediationDto)throws AppException;
    public int saveRiskRegistryInfo(RemediationDTO remediationDto,UserProfileDTO sessionDTO)throws AppException;
    public int saveRiskRigistryFindings(RemediationDTO remediationDto, UserProfileDTO sessionDTO, String riskId) throws AppException;
    public int deleteRemediationPlan(RemediationDTO remediationDto)throws AppException;
    public int updatePlanItemStatus(RemediationDTO remediationDto,UserProfileDTO sessionDTO)throws AppException;
    public String getRegistryKeyByPlanKey(RemediationDTO remediationDto)throws AppException;
    public int updateRemediationNotifyDate(RemediationDTO remediationDto,UserProfileDTO sessionDTO)throws AppException;
    public RemediationDTO planItemInfoByInstKey(RemediationDTO remediationDto) throws AppException;
    public List<MasterLookUpDTO> fetchRemediationStatus() throws AppException;
    public int updatePlanStatus(RemediationDTO remediationDto,UserProfileDTO sessionDTO,String flag)throws AppException;
    public List<UserProfileDTO> remediationOwnerUsers(String flag, String engagementCode) throws AppException;
    public String generateRemdiationSeverity(RemediationDTO remediationDto) throws AppException;
    public int updatePlanSeverity(RemediationDTO remediationDto, UserProfileDTO sessionDTO, String planSeverity) throws AppException;
}
