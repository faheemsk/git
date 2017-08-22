/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.dao;

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
public interface RoadMapDAO {
    
    public List<RoadMapDTO> fetchRoadmapCategory(final RoadMapDTO roadMapDTO) throws AppException;
    
    public List<RoadMapDTO> generateRoadmapCategory(final RoadMapDTO roadMapDTO) throws AppException;
    
    public int saveUpdateRoadmapCategory(final List<RoadMapDTO> roadmapList, final UserProfileDTO userDto,
            final String flag) throws AppException;
    
     public List<MasterLookUpDTO> timeLineList() throws AppException;
     
    public List<FindingsModel> roadMapDataForExportCSV(SecurityModel securityModel) throws AppException;
    
    public List<FindingsModel> getFindingsByCategory(SecurityModel securityModel) throws AppException;
    
}
