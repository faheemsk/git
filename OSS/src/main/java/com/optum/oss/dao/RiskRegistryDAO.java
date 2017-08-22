/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao;

import com.optum.oss.dto.ManageServiceUserDTO;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.RemediationDTO;
import com.optum.oss.dto.RiskRegisterDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.dto.VulnerabilityDTO;
import com.optum.oss.exception.AppException;
import java.util.List;

/**
 *
 * @author rpanuganti
 */
public interface RiskRegistryDAO {

    public List<ManageServiceUserDTO> fetchRiskRegistryWorklist(UserProfileDTO userDto, final ManageServiceUserDTO manageServiceUserDTO) throws AppException;

    public List<RiskRegisterDTO> riskRegistryList(String engagementCode, String orgSchema, final long userID, String flag) throws AppException;

    public RiskRegisterDTO riskRegistryDetailsById(String orgSchema, int riskRegisterID) throws AppException;

    public List<VulnerabilityDTO> riskRegistryFindingsListByID(int registryID, String orgSchema) throws AppException;

    public int updateRiskRegistryDetails(final RiskRegisterDTO riskRegistryDto, final UserProfileDTO usersSessionDTO) throws AppException;

    public int removeRiskRegistryItems(String[] items, int RegistryID, String orgSchema) throws AppException;

    public List<MasterLookUpDTO> fetchRiskResponseList() throws AppException;

    public int updateRiskAcceptanceDetails(RiskRegisterDTO riskRegistryDto, UserProfileDTO usersSessionDTO) throws AppException;

    public int updateFindingCountRegistryAnsPlan(int key, int findingCount, long userID, String orgSchema, String flag) throws AppException;
    
    public int updateRiskRegistryNotifyDate(RiskRegisterDTO registryDto,UserProfileDTO sessionDTO) throws AppException;
    
    public String generateRiskSeverity(String engCode, String orgSchema, String registryID) throws AppException;
    
    public int updateRiskRegistrySeverity(RiskRegisterDTO riskRegistryDto, UserProfileDTO usersSessionDTO) throws AppException;
    
    public VulnerabilityDTO riskRegistryFindingInfo(int instanceId, String orgSchema) throws AppException;
    
    public int updateRiskRegistryFinding(RiskRegisterDTO riskRegistryDto, UserProfileDTO usersSessionDTO) throws AppException;
}
