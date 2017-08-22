/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service;

import com.optum.oss.dto.ManageServiceUserDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.dto.VulnerabilityDTO;
import com.optum.oss.exception.AppException;
import java.util.List;

/**
 *
 * @author sathuluri
 */
public interface RemediationProjectManagerService {

    public List<ManageServiceUserDTO> fetchRemediationWorklist(final UserProfileDTO userDto,
            final ManageServiceUserDTO manageServiceUserDTO) throws AppException;

    public List<VulnerabilityDTO> findingList(final String engagementCode) throws AppException;

    public int updateFindingStatus(final VulnerabilityDTO vulnerabilityDTO) throws AppException;
}
