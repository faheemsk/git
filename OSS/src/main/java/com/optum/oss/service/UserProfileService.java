/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service;

import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import java.util.List;

/**
 *
 * @author hpasupuleti
 */
public interface UserProfileService {

    public String updateChangedUserPassword(final String currentPassword, final String newPassword,
            final UserProfileDTO userProfileObj)
            throws AppException;

    public int updateExpiredUserPassword(final String currentPassword, final String newPassword,
            final UserProfileDTO userProfileObj)
            throws AppException;

    public int updateUserPersonnelInformation(final UserProfileDTO userProfileObj,
            final UserProfileDTO sessUserProfileObj) throws AppException;

    public UserProfileDTO prepareUserPermissions(final UserProfileDTO userProfileObj)
            throws AppException;
    
    public String expChangePassUpdate(final String currentPassword, final String newPassword,
            final UserProfileDTO userProfileObj) throws AppException;
    
    public List<UserProfileDTO> getUsersForPwdExpiryAlert(final int days,final int expiryDays) throws AppException;
    
     public List<ClientEngagementDTO> getServiceDueDatePassed() throws AppException;
     
     public void updateUserLoginAttempts() throws AppException;

}
