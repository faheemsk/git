/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.service;

import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.RoadMapDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.model.FindingsModel;
import com.optum.oss.model.SecurityModel;
import java.util.List;

/**
 *
 * @author sbhagavatula
 */
public interface RoadMapService {
    
    public ClientEngagementDTO engagementInfoByEngCode(final RoadMapDTO roadMapDTO) throws AppException;
    
    public List<RoadMapDTO> roadmapCategoryByEngCode(final RoadMapDTO roadMapDTO) throws AppException;
    
    public int createRoadmapCategory(final RoadMapDTO roadMapDTO, final UserProfileDTO userDto) throws AppException;
    
    public List<RoadMapDTO> fetchRoadmapInfo(final RoadMapDTO roadMapDTO) throws AppException;
    
    public List<MasterLookUpDTO> timeLineList() throws AppException;
    
    public int updateRoadmapCategory(final RoadMapDTO roadMapDTO, final UserProfileDTO userDto) throws AppException;
    
      public String fetchRoadMapSummaryChartData(final RoadMapDTO roadMapDTO) throws AppException;
    
    public List<FindingsModel> roadMapDataForExportCSV(SecurityModel securityModel) throws AppException;
    
    public List<FindingsModel> getFindingsByCategory(SecurityModel securityModel) throws AppException;
    
}
