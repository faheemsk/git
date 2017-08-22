/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service;

import com.optum.oss.dto.UserApplicationRoleDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.dto.UserSecurityDetailsDTO;
import com.optum.oss.exception.AppException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author hpasupuleti
 */
public interface LoginService {

    public UserProfileDTO fetchUserDetailsByUserName(final String userName) throws AppException;

    public List<UserSecurityDetailsDTO> fetchUserSecurityQuestionDetails(final long userProfileKey)
            throws AppException;

    public List<UserApplicationRoleDTO> fetchUserRolePermissionDetails(final long userProfileKey)
            throws AppException;

    public int lockUnlockUserBasedOnUserID(final long userProfileKey, final long lockFlag)
            throws AppException;

    public int updateUserLoginAttemptsBasedOnUserID(final long userProfileKey)
            throws AppException;

    public UserProfileDTO validateUserLogin(final String userName, final String password)
            throws AppException;

    public UserProfileDTO validateUserMACAddress(final HttpServletRequest request,
            final UserProfileDTO userProfileObj) throws AppException;

    public int updateUserLastLoginDateAndMacAddrBasedOnUserID(final long userProfileKey,
            final String macAddress, final String lastLoginDateTime)
            throws AppException;

    public int insertUserDetailsIntoUserSessionInfo(final UserProfileDTO userProfileObj)
            throws AppException;

    public UserProfileDTO insertUserSecurityDetails(final UserSecurityDetailsDTO userSecDtlsObj,
            final UserProfileDTO userProfileObj, final String currentPassword, final String newPassword,
            final String confirmPassword,final HttpServletRequest request) throws AppException;

    public int updateUserSessionInfoByUserID(final String dbFlag, final UserProfileDTO userProfileObj)
            throws AppException;

    public int killUserActiveSessionDetails(final UserProfileDTO userProfileObj)
            throws AppException;

    public UserProfileDTO fetchUserDetailsForForgotPassword(final String username)
            throws AppException;

    public int validateSecurityAnswersInForgotPassword(final List<UserSecurityDetailsDTO> userSecurityDtls,
            final String securityQuestion1, final String securityAnswer1,
            final String securityQuestion2, final String securityAnswer2) throws AppException;

    public int changeLDAPPAsswordForForgotPassword(final UserProfileDTO userProfileObj)
            throws AppException;
    
    public String profileValidateUserpassword(final String userEmail,final String password)throws AppException;
    
     public int validateSecurityAnswersInVerifyUser(final List<UserSecurityDetailsDTO> userSecurityDtls,
            final String securityQuestion1, final String securityAnswer1) throws AppException;
     
     public int updateUserMacAddressForNewDevice(final UserProfileDTO userProfileObj)
            throws AppException;

     public long updateUserLastLoginDate(final long userId) throws AppException;
}
