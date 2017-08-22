/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.transact.dao;

import com.optum.oss.dto.ClientReportsUploadDTO;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import java.util.List;

/**
 *
 * @author mrejeti
 */
public interface ReportsDAO {

    public List<ClientReportsUploadDTO> fetchClientReportsUploadWorkList(final UserProfileDTO userDto, ClientReportsUploadDTO clientReportsUploadDTO) throws AppException;

    public int saveUploadReportFile(ClientReportsUploadDTO saveReports, UserProfileDTO userDto) throws AppException;

    public List<MasterLookUpDTO> fetchReportNames(String engagementCode) throws AppException;

    public List<ClientReportsUploadDTO> fetchUploadFileList(String engagementCode) throws AppException;

//    public int updateSaveReportFile(ClientReportsUploadDTO saveReports, UserProfileDTO userDto) throws AppException;

    public int deleteUploadedFileDetails(long rowstatuskey) throws AppException;

    public int publishReportFile(List<ClientReportsUploadDTO> reportsList, UserProfileDTO userDto) throws AppException;
    
    public int updateEngagmentStatusToPublish(final String engKey,final long engStatusKey )throws AppException;

    public List<ClientReportsUploadDTO> searchClientReportsUploadWorkList(UserProfileDTO userDto, ClientReportsUploadDTO clientReportsUploadDTO)
            throws AppException;
    
    public int validateDuplicateFileName(String fileName, long reportkey, String engCode) throws AppException; 
}
