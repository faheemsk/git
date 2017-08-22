/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao;

import com.optum.oss.dto.ManageServiceUserDTO;
import com.optum.oss.dto.VulnerabilityDTO;
import com.optum.oss.exception.AppException;
import java.util.List;

/**
 *
 * @author sathuluri
 */
public interface RemediationProjectManagerDao {

    public List<ManageServiceUserDTO> fetchRemediationWorklist(final ManageServiceUserDTO manageServiceUserDTO) throws AppException;

    public List<VulnerabilityDTO> findingList(final String engagementCode) throws AppException;

    public int updateFindingStatus(final List<VulnerabilityDTO> findingStatusList) throws AppException;
}
