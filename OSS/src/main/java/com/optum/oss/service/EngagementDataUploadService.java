/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service;

import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.dto.ManageServiceUserDTO;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author akeshavulu
 */
public interface EngagementDataUploadService {

    public ClientEngagementDTO fetchServiceEngagementDetailsByEgmntCode(final String engagementCode, final String serviceCode, long userID) throws AppException;

    public int saveUploadedFileDetails(ClientEngagementDTO clientEngagementDTO, UserProfileDTO userDto,String fileName) throws AppException;

    public int saveEngagementDataUploadDetails(ClientEngagementDTO clientEngagementDTO) throws AppException;

    public List<MasterLookUpDTO> fetchMasterLookUpEntitiesByEntityType(final String entityType) throws AppException;

    public List<ClientEngagementDTO> fetchUploadedFilesDetails(ClientEngagementDTO clientEngagementDTO) throws AppException;

    public int deleteUploadedFileDetails(int fileID) throws AppException;

   // public int updateLockUnlockServiceData(final String engagementCode, String serviceCode, long lockIndicator) throws AppException;
    
    public int updateLockUnlockServiceData(ClientEngagementDTO clientEngagementDTO, UserProfileDTO userDto) throws AppException;

    public Map engagementDataUploadWorkList(final UserProfileDTO userProfileDTO,final String reqToken) throws AppException;

    public Map searchEngagementServicesWorkList(final UserProfileDTO userProfileDTO, final ManageServiceUserDTO manageServiceUserDTO,final String reqToken) throws AppException;

    public int validateDuplicateFileName(String fileName, String engmntCode, String serviceCode) throws AppException;

    public List<UserProfileDTO> engagementLeadDetailsByCode(String engagementCode) throws AppException;

    public int emailNotificationAllServicesLocked(String engagementCode) throws AppException;
    
    public ClientEngagementDTO fetchUploadedFileDetails(String fileName, String engmntCode) throws AppException;
    
    //public int updateUploadFileStatus(String fileName, String engmntCode, int statusKey) throws AppException;

    public int scanInProgressFileCount(ClientEngagementDTO clientEngagementDTO) throws AppException;
}
