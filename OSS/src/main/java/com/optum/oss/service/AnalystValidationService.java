/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service;

import com.optum.oss.dto.CVEInformationDTO;
import com.optum.oss.dto.ComplianceInfoDTO;
import com.optum.oss.dto.ManageServiceUserDTO;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.OwaspDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.dto.SecurityServiceDTO;
import com.optum.oss.dto.VulnerabilityDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.model.SummaryDropDownModel;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author sbhagavatula
 */
public interface AnalystValidationService {

    public Map fetchAnalystValidationWorklist(final UserProfileDTO userDto, final Set<String> permissionSet,
            final ManageServiceUserDTO manageServiceUserDTO) throws AppException;

    public List<VulnerabilityDTO> vulnerabilityList(VulnerabilityDTO vulnerabilityDTOs) throws AppException;

    public VulnerabilityDTO viewVulnerabilityDetails(long vulnerabilityKey, String orgSchema) throws AppException;

    public List<SecurityServiceDTO> analystEngagementServices(String engagementCode) throws AppException;

    public int deleteVulnerability(final VulnerabilityDTO vulnerabilityDto,long userId) throws AppException;

    public List<MasterLookUpDTO> analystStatusList(String flag) throws AppException;

    public List<MasterLookUpDTO> analystCostEffortList() throws AppException;

    public List<MasterLookUpDTO> vulnerabilityCategoryList() throws AppException;
    
    public List<SummaryDropDownModel> analystSeverityList() throws AppException;
    
    public List<MasterLookUpDTO> analystSourceList() throws AppException;
    
       public int updateFindingStatus(String flag,String orgSchema, long findingID, String statusCode,String closeReason,UserProfileDTO sessionDTO) throws AppException;

    /**
     *
     * @param cVEInformationDTO
     * @return
     * @throws AppException
     */
    public VulnerabilityDTO addCVEInformation(VulnerabilityDTO vulnerabilityDTO) throws AppException;

    public int saveVulnerabilityInfo(final VulnerabilityDTO saveVulnerability, final UserProfileDTO sessionDTO) throws AppException;

    public int saveVulnerability(final VulnerabilityDTO saveVulnerability, final UserProfileDTO sessionDTO) throws AppException;
    
    public int updateVulnerabilityInfo(final VulnerabilityDTO saveVulnerability, final UserProfileDTO sessionDTO) throws AppException;
    
    public int updateVulnerability(final VulnerabilityDTO saveVulnerability, final UserProfileDTO sessionDTO) throws AppException;

    public int saveComplianceInfo(final VulnerabilityDTO saveVulnerability, final UserProfileDTO sessionDTO) throws AppException;

    public int deleteComplianceInfo(final long vulnerabilityKey) throws AppException;

    public VulnerabilityDTO addHitrustToVulnerability(VulnerabilityDTO vulnerabilityDTO) throws AppException;

    public Map<String, LinkedHashMap<String, ArrayList<ComplianceInfoDTO>>> fetchComplianceInformation() throws AppException;

    public List<VulnerabilityDTO> vulnerabilityDetailsForExcelExport(final VulnerabilityDTO vulnerabilityDTO) throws AppException;
    
    public int updateServicesStatus(final VulnerabilityDTO vulnerabilityDTO, final UserProfileDTO userDto) throws AppException;
    
    public VulnerabilityDTO getRiskDetailsByCvssScore(VulnerabilityDTO vulnerabilityDTO) throws AppException;
    
    public VulnerabilityDTO fetchVulnerabilityBasicDetails(VulnerabilityDTO vulnerabilityDTO) throws AppException;
    
    public List<MasterLookUpDTO> fetchOSList() throws AppException;
    
    public int checkForCveId(final String cveIdentifier) throws AppException;
    
    public List<VulnerabilityDTO> fetchManualVulnerabilityNames() throws AppException;
    
    public List<CVEInformationDTO> fetchCVEList(VulnerabilityDTO vulnerabilityObj) throws AppException;
    
    public List<OwaspDTO> fetchOwaspList() throws AppException;
}
