/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao;

import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.dto.ManageServiceUserDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import java.util.List;

/**
 *
 * @author akeshavulu
 */
public interface EngagementDataUploadDAO {

    public ClientEngagementDTO fetchServiceEngagementDetailsByEgmntCode(final String engagementCode, String serviceCode, long userID) throws AppException;

    public int saveEngagementDataUploadDetails(ClientEngagementDTO clientEngagementDTO) throws AppException;

    public List<ClientEngagementDTO> fetchUploadedFilesDetails(ClientEngagementDTO clientEngagementDTO) throws AppException;
    
    public ClientEngagementDTO fetchUploadedFileDetails(String fileName, String engmntCode) throws AppException;

    public int deleteUploadedFilesDetails(int fileID) throws AppException;

    public int updateLockUnlockServiceData(final String engagementCode, String serviceCode, long lockIndicator) throws AppException;

    public List<ManageServiceUserDTO> engagementDataUploadWorkList(final UserProfileDTO userProfileDTO, final String userFlag,final String reqToken) throws AppException;

    public List<ManageServiceUserDTO> searchEngagementServicesWorkList(final UserProfileDTO userProfileDTO, final ManageServiceUserDTO manageServiceUserDTO,final String reqToken) throws AppException;
    
    public int validateDuplicateFileName(String fileName, String engmntCode, String serviceCode) throws AppException;
    
    public List<UserProfileDTO> engagementLeadDetailsByCode(String engagementCode) throws AppException;
    
    public int emailNotificationAllServicesLocked(String engagementCode) throws AppException;
    
    public List<UserProfileDTO> engagementAnalystDetails(String engagementCode, String serviceCode) throws AppException;
    
   // public int updateUploadFileStatus(String fileName, String engmntCode, int statusKey) throws AppException;
    
    public int uploadFileCountByStatus(ClientEngagementDTO clientEngagementDTO, int status) throws AppException;
    
    public List<ClientEngagementDTO> getUploadedFileDetailsByEngagementId(final String engagementId);
}
