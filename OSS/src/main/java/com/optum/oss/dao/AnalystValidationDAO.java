/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao;

import com.optum.oss.dto.CVEInformationDTO;
import com.optum.oss.dto.ComplianceInfoDTO;
import com.optum.oss.dto.ManageServiceUserDTO;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.OwaspDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.dto.SecurityServiceDTO;
import com.optum.oss.dto.VulnerabilityDTO;
import com.optum.oss.exception.AppException;
import java.util.List;

/**
 *
 * @author sbhagavatula
 */
public interface AnalystValidationDAO {

    public List<ManageServiceUserDTO> fetchAnalystValidationWorklist(final UserProfileDTO userDto, final String pvcFlag,
            final ManageServiceUserDTO manageServiceUserDTO) throws AppException;

    public List<VulnerabilityDTO> vulnerabilityList(VulnerabilityDTO vulnerabilityDTOs) throws AppException;

    public VulnerabilityDTO viewVulnerabilityDetails(long vulnerabilityKey,String orgSchema) throws AppException;

    public List<SecurityServiceDTO> analystEngagementServices(String engagementCode) throws AppException;

    public int deleteVulnerability(final List<VulnerabilityDTO> vulnerabilityList, long userId) throws AppException;

    public List<MasterLookUpDTO> analystStatusList(String flag) throws AppException;

    public List<MasterLookUpDTO> analystCostEffortList() throws AppException;

    public List<MasterLookUpDTO> vulnerabilityCategoryList() throws AppException;

    public List<MasterLookUpDTO> analystSourceList() throws AppException;

    // public CVEInformationDTO addCVEInformation(CVEInformationDTO cVEInformationDTO) throws AppException;
    public int saveVulnerabilityInfo(final VulnerabilityDTO saveVulnerability, final UserProfileDTO sessionDTO) throws AppException;

    public int updateVulnerabilityInfo(final VulnerabilityDTO saveVulnerability, final UserProfileDTO sessionDTO) throws AppException;

    public int saveComplianceInfo(final VulnerabilityDTO saveVulnerability, final List<String> selectedCompliances, final UserProfileDTO sessionDTO) throws AppException;

    public int saveSecondaryComplianceInfo(final List<ComplianceInfoDTO> secondaryCompliances, VulnerabilityDTO saveVulnerability, final UserProfileDTO sessionDTO, int status) throws AppException;

    public int deleteComplianceInfo(final long vulnerabilityKey) throws AppException;

    public int updateComplianceStatus(VulnerabilityDTO saveVulnerability, List<String> updtCompliances, UserProfileDTO sessionDTO, int status) throws AppException;

    public List<ComplianceInfoDTO> getComplianceInformation() throws AppException;

    public VulnerabilityDTO getCVEInformationByCveID(VulnerabilityDTO vulnerabilityDTO) throws AppException;

    public VulnerabilityDTO getComplianceInformationByCveID(VulnerabilityDTO vulnerabilityDTO) throws AppException;

    public VulnerabilityDTO getComplianceInformationByVulnerabilityKey(VulnerabilityDTO vulnerabilityDTO) throws AppException;

    public int updateServicesStatus(final String clientEngagementCode, final String securityServiceCode, final long userId, final int status)
            throws AppException;

    public VulnerabilityDTO getRiskDetailsByCvssScore(VulnerabilityDTO vulnerabilityDTO) throws AppException;

    public List<MasterLookUpDTO> fetchOSList() throws AppException;

    public List<ComplianceInfoDTO> getHitrustCrossWalkInfoByCtrlCD(String checkedHitrustValues) throws AppException;

    public int checkForCveId(final String cveIdentifier) throws AppException;

    public List<VulnerabilityDTO> fetchManualVulnerabilityNames() throws AppException;

    public List<CVEInformationDTO> fetchCVEList(VulnerabilityDTO vulnerabilityObj) throws AppException;

    public List<String> getSecurityCtrlCodeByVulnerabilityKey(VulnerabilityDTO vulnerabilityDTO, String flag) throws AppException;

    public int updateSecondaryComplianceInfo(List<ComplianceInfoDTO> secondaryCompliances, VulnerabilityDTO saveVulnerability, UserProfileDTO sessionDTO, int status) throws AppException;

    public List<OwaspDTO> fetchOwaspList() throws AppException;
    
    public int insertFindingName(VulnerabilityDTO vulnerabilityDTO, UserProfileDTO sessionDTO) throws AppException;
    
    public int findingNameCount(String findingName) throws AppException;
    
    public int updateFindingStatus(String flag,String orgSchema,long findingID,String statusCode,String closeReason,UserProfileDTO sessionDTO) throws AppException;
}
