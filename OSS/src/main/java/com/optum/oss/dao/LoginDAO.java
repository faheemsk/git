/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao;

import com.optum.oss.dto.UserApplicationRoleDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.dto.UserSecurityDetailsDTO;
import com.optum.oss.exception.AppException;
import java.util.List;

/**
 *
 * @author hpasupuleti
 */
public interface LoginDAO {

    public UserProfileDTO fetchUserDetailsByUserName(final String userName) throws AppException;

    public List<UserSecurityDetailsDTO> fetchUserSecurityQuestionDetails(final long userProfileKey)
            throws AppException;

    public List<UserApplicationRoleDTO> fetchUserRolePermissionDetails(final long userProfileKey)
            throws AppException;

    public int lockUnlockUserBasedOnUserID(final long userProfileKey, final long lockFlag)
            throws AppException;

    public int updateUserLoginAttemptsBasedOnUserID(final long userProfileKey)
            throws AppException;

    public int updateUserLastLoginDateAndMacAddrBasedOnUserID(final long userProfileKey,
            final String macAddress, final String lastLoginDateTime)
            throws AppException;

    public int insertUserDetailsIntoUserSessionInfo(final UserProfileDTO userProfileObj)
            throws AppException;

    public int updateUserSessionInfoByUserID(final String lastAccessTime,
            final long userID, final String dbFlag, final String sessionID)
            throws AppException;
    
    public long updateUserLastLoginDate(final long userId) throws AppException ;
}
