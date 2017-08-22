/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service;

import com.optum.oss.dto.ClientReportsUploadDTO;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mrejeti
 */
public interface ReportsService {

    public Map fetchClientReportsUploadWorkList(final UserProfileDTO userDto, final ClientReportsUploadDTO clientReportsUploadDTO) throws AppException;

    public int saveUploadReportFile(final ClientReportsUploadDTO saveReports, final UserProfileDTO userDto) throws AppException;

    public List<MasterLookUpDTO> fetchReportNames(final String engagementCode) throws AppException;
    
    public List<ClientReportsUploadDTO> fetchUploadFileList(final String engagementCode)throws AppException;
    
//   public int updatesaveReportFile(final ClientReportsUploadDTO saveReports, final UserProfileDTO userDto) throws AppException;
    
    public int deleteUploadedFileDetails(long rowstatuskey) throws AppException;
    
     public int publishReportFile(final ClientReportsUploadDTO saveReports, final UserProfileDTO userDto) throws AppException;
     
     public int updateEngagmentStatusToPublish(final String engKey,final long engStatusKey )throws AppException;
     
    public Map searchClientReportsUploadWorkList(final UserProfileDTO userDto, final ClientReportsUploadDTO clientReportsUploadDTO) throws AppException;
  
    public int validateDuplicateFileName(String fileName, long reportkey,String engCode) throws AppException; 
   
          
}
