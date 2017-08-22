/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service.impl;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dao.impl.LoginDAOImpl;
import com.optum.oss.dao.impl.UserActivationDAOImpl;
import com.optum.oss.dto.UserApplicationRoleDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.dto.UserSecurityDetailsDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.LDAPAuthenticate;
import com.optum.oss.helper.WebAppAdminMailHelper;
import com.optum.oss.service.LoginService;
import com.optum.oss.transact.dao.impl.LoginTransactDAOImpl;
import com.optum.oss.util.CommonUtil;
import com.optum.oss.util.DateUtil;
import com.optum.oss.util.GenerateRandomNumber;
import com.optum.oss.util.GetSystemMacAddress;
import java.util.List;
import java.util.Properties;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author hpasupuleti
 */
@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = Logger.getLogger(LoginServiceImpl.class);

    /*
     Start : Setter getters for private variables
     */
    @Resource(name = "myProperties")
    private Properties myProperties;

    @Autowired
    private LoginDAOImpl loginDAO;

    @Autowired
    private LoginTransactDAOImpl loginTransactDAO;

    @Autowired
    private GetSystemMacAddress getMacAddr;

    @Autowired
    private LDAPAuthenticate ldapAuthenticate;

    @Autowired
    private GenerateRandomNumber generateRandom;

    @Autowired
    private DateUtil dateUtil;

    @Autowired
    WebAppAdminMailHelper webAppMailHelper;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private UserActivationDAOImpl useractivationDAO;

    /*
     End : Setter getters for private variables
     */
    /**
     *
     * @param userName
     * @return UserProfileDTO
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public UserProfileDTO fetchUserDetailsByUserName(final String userName) throws AppException {
        return loginDAO.fetchUserDetailsByUserName(userName);
    }

    /**
     *
     * @param userProfileKey
     * @return List<UserSecurityDetailsDTO>
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<UserSecurityDetailsDTO> fetchUserSecurityQuestionDetails(final long userProfileKey)
            throws AppException {
        return loginDAO.fetchUserSecurityQuestionDetails(userProfileKey);
    }

    /**
     *
     * @param userProfileKey
     * @return List<UserApplicationRoleDTO>
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<UserApplicationRoleDTO> fetchUserRolePermissionDetails(final long userProfileKey)
            throws AppException {
        return loginDAO.fetchUserRolePermissionDetails(userProfileKey);
    }

    /**
     *
     * @param userProfileKey
     * @param lockFlag
     * @return Updated row count as Integer
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int lockUnlockUserBasedOnUserID(final long userProfileKey, final long lockFlag)
            throws AppException {
        return loginDAO.lockUnlockUserBasedOnUserID(userProfileKey, lockFlag);
    }

    /**
     *
     * @param userProfileKey
     * @return Updated row count as Integer
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int updateUserLoginAttemptsBasedOnUserID(final long userProfileKey)
            throws AppException {
        return loginDAO.updateUserLoginAttemptsBasedOnUserID(userProfileKey);
    }

    /**
     *
     * @param userName
     * @param password
     * @return UserProfileDTO
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public UserProfileDTO validateUserLogin(final String userName, final String password)
            throws AppException {
        UserProfileDTO userProfileObj = loginTransactDAO.validateUserLogin(userName, password);

        return userProfileObj;
    }

    /**
     *
     * @param request
     * @param userProfileObj
     * @return UserProfileDTO
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public UserProfileDTO validateUserMACAddress(final HttpServletRequest request,
            final UserProfileDTO userProfileObj) throws AppException {
        try {
            String curMacAddress = getMacAddr.GetAddress("ip", request);

            if (StringUtils.isEmpty(userProfileObj.getUserMACAddress())) {
                userProfileObj.setUserMACAddress(curMacAddress);
                userProfileObj.setNewUserMACAddress(curMacAddress);
                loginTransactDAO.updateUserLoginDetails(userProfileObj);
            } else {
                if (userProfileObj.getUserMACAddress().equalsIgnoreCase(curMacAddress)) {
                    userProfileObj.setUserMACAddress(curMacAddress);
                    userProfileObj.setNewUserMACAddress(curMacAddress);
                } else {
                    userProfileObj.setNewUserMACAddress(curMacAddress);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : validateUserMACAddress:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "validateUserMACAddress() :: " + e.getMessage());
        }
        return userProfileObj;
    }

    /**
     *
     * @param userProfileKey
     * @param macAddress
     * @param lastLoginDateTime
     * @return int
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int updateUserLastLoginDateAndMacAddrBasedOnUserID(final long userProfileKey,
            final String macAddress, final String lastLoginDateTime)
            throws AppException {
        return loginDAO.updateUserLastLoginDateAndMacAddrBasedOnUserID(userProfileKey, macAddress, lastLoginDateTime);
    }

    /**
     *
     * @param userProfileObj
     * @return int
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int insertUserDetailsIntoUserSessionInfo(final UserProfileDTO userProfileObj)
            throws AppException {
        return loginDAO.insertUserDetailsIntoUserSessionInfo(userProfileObj);
    }

    /**
     *
     * @param userSecDtlsObj
     * @param userProfileObj
     * @param currentPassword
     * @param newPassword
     * @param confirmPassword
     * @return UserProfileDTO
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public UserProfileDTO insertUserSecurityDetails(final UserSecurityDetailsDTO userSecDtlsObj,
            final UserProfileDTO userProfileObj, final String currentPassword, final String newPassword,
            final String confirmPassword, final HttpServletRequest request) throws AppException {
        int secDtlsRet = 0;
        try{
        // VALIDATE USER EXISTING PASSWORD WITH ENTERED PASSWORD
        if (ldapAuthenticate.validateUserInLDAP(userProfileObj.getEmail(), currentPassword)) {
            // CHECK NEW PASSWORD CONTAINS AT LEAST $ CHARS SAME IN OLD PASSWORD 

            if (!commonUtil.isNewPWDContainsOldPWDChars(newPassword, currentPassword)) {

                // IF EXISTING PASSWORD IS VALID, VALIDATE NEW PASSWORD AND CONFIRM PASSWORD
                // UPDATE USER SECURITY QUESTION(s) DETAILS and UPDATE USER USER_VERF_IND TO 1 
                boolean changePassFlag = ldapAuthenticate.changeLDAPPassword(userProfileObj, newPassword);
                if (changePassFlag) {
                    secDtlsRet = loginTransactDAO.insertUserSecurityDetails(userProfileObj);
                    if (secDtlsRet == 1) {
                        userProfileObj.setUserValidateMessage("");
                        //KILL ACTIVE SESSION
                        loginDAO.updateUserSessionInfoByUserID(dateUtil.generateDBCurrentDateInString(),
                                userProfileObj.getUserProfileKey(),
                                ApplicationConstants.DB_INDICATOR_DELETE,
                                userProfileObj.getUserSessionID());

//                        //String curMacAddress = getMacAddr.GetAddress("mac", request);
                        String curMacAddress = getMacAddr.GetAddress("ip", request);
                        userProfileObj.setUserMACAddress(curMacAddress);
                        userProfileObj.setLastLoginDate(dateUtil.generateDBCurrentDateInString());

                        loginTransactDAO.updateUserLoginDetails(userProfileObj);
                        // SEND EMAIL TO ADMIN STATING THAT USER CHANGED HIS PASSWORD.
                        webAppMailHelper.mail_ToAdminWhenNewUserChangesPasswrod(userProfileObj.getFirstName() + " " + userProfileObj.getLastName(),
                                userProfileObj.getUserProfileKey());
                    } else {
                        userProfileObj.setUserValidateMessage(myProperties.getProperty("userconfig.error-profile-update"));
                    }
                } else {
                    userProfileObj.setUserValidateMessage(myProperties.getProperty("userconfig.error-password-update"));
                }
            } else {
                userProfileObj.setUserValidateMessage(myProperties.getProperty("userconfig.newpwd-oldpwd-commonchars"));

            }
        } else {
            userProfileObj.setUserValidateMessage(myProperties.getProperty("userconfig.incorrect-curpassword"));
        }
        } catch (AppException e) {
            logger.error(e.getMessage());
            String expMsg = e.getMessage();
            if (expMsg.equalsIgnoreCase(ApplicationConstants.OPENDJ_DISCONNECTED)) {
                userProfileObj.setUserValidateMessage(ApplicationConstants.OPENDJ_DISCONNECTED);
            }
        }

        return userProfileObj;
    }

    /**
     *
     * @param dbFlag
     * @param userProfileObj
     * @return int
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int updateUserSessionInfoByUserID(final String dbFlag, final UserProfileDTO userProfileObj)
            throws AppException {
        return loginDAO.updateUserSessionInfoByUserID(dateUtil.generateDBCurrentDateInString(),
                userProfileObj.getUserProfileKey(),
                dbFlag,
                userProfileObj.getUserSessionID());
    }

    /**
     *
     * @param userProfileObj
     * @return int
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int killUserActiveSessionDetails(final UserProfileDTO userProfileObj) throws AppException {
        return loginTransactDAO.killUserActiveSessionDetails(userProfileObj);
    }

    /**
     *
     * @param username
     * @return UserProfileDTO
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public UserProfileDTO fetchUserDetailsForForgotPassword(final String username) throws AppException {
        UserProfileDTO userProfileObj = new UserProfileDTO();
        userProfileObj = this.fetchUserDetailsByUserName(username);
        if (userProfileObj.getUserProfileKey() > 0) {
            //VALID USER DETAILS
            // CHECK IF USER IS LOCKED
            if (userProfileObj.getLockIndicator() == ApplicationConstants.APP_STATUS_INDICATOR_YES) {
                userProfileObj.setUserValidateMessage(myProperties.getProperty("forgotpass.locked-user"));
            } //CHECK IF USER IS IN-ACTIVE
            else if (userProfileObj.getRowStatusKey() == ApplicationConstants.DB_ROW_STATUS_DE_ACTIVE_VALUE) {
                userProfileObj.setUserValidateMessage(myProperties.getProperty("forgotpass.deactivated-user"));
            } else {
                userProfileObj.setUserValidateMessage("");
            }
        } else {
            userProfileObj.setUserValidateMessage(myProperties.getProperty("forgotpass.invalid-user"));
        }

        return userProfileObj;
    }

    /**
     *
     * @param userSecurityDtls
     * @param securityQuestion1
     * @param securityAnswer1
     * @param securityQuestion2
     * @param securityAnswer2
     * @return int
     * @throws AppException
     */
    @Override
    public int validateSecurityAnswersInForgotPassword(final List<UserSecurityDetailsDTO> userSecurityDtls,
            final String securityQuestion1, final String securityAnswer1,
            final String securityQuestion2, final String securityAnswer2) throws AppException {
        int retVal = 0;
        if (!userSecurityDtls.isEmpty()) {
            for (UserSecurityDetailsDTO securityDTO : userSecurityDtls) {
                if (securityDTO.getEncUserSecurityKey().trim().equals(securityQuestion1.trim())) {
                    if (securityDTO.getSecurityAnswer().trim().equals(securityAnswer1.trim())) {
                        retVal = 1;
                    } else {
                        retVal = 0;
                    }
                }
                if (securityDTO.getEncUserSecurityKey().trim().equals(securityQuestion2.trim())) {
                    if (securityDTO.getSecurityAnswer().trim().equals(securityAnswer2.trim())) {
                        retVal = 1;
                    } else {
                        retVal = 0;
                    }
                }
            }
        }

        return retVal;
    }

    /**
     *
     * @param userProfileObj
     * @return int
     * @throws AppException
     */
    @Override
    public int changeLDAPPAsswordForForgotPassword(final UserProfileDTO userProfileObj) throws AppException {
        int retVal = 0;
        String newPassword = generateRandom.generateResPasswordToken();
        // SET RUSER_FIRST_TIME_LOGIN_IND TO 0 FOR NAVIGATING TO CHANGEPASSWORD PAGE.
        int lockIndicator = Integer.valueOf(ApplicationConstants.APP_STATUS_INDICATOR_NO + "");
        int retV = useractivationDAO.updateUserStatus(userProfileObj.getUserProfileKey(), 0, ApplicationConstants.DB_ROW_STATUS_ACTIVE_VALUE);
        boolean bool = ldapAuthenticate.changeLDAPPassword(userProfileObj, newPassword);
        if (bool) {
            // MAIL SEND TO USER WITH NEW PASSWORD GENERATED
            webAppMailHelper.mail_UserAccountPassword(userProfileObj, newPassword, userProfileObj.getUserProfileKey());
            retVal = 1;
        } else {
            retVal = 0;
        }
        return retVal;
    }

    /**
     *
     * @param userEmail, password
     * @return String
     * @throws AppException
     */
    @Override
    public String profileValidateUserpassword(final String userEmail, final String password) throws AppException {
        try {
            if (ldapAuthenticate.validateUserInLDAP(userEmail, password)) {
                return ApplicationConstants.SUCCESS;
            } else {
                return myProperties.getProperty("userconfig.incorrect-curpassword");
            }
        } catch (AppException e) {
            logger.error(e.getMessage());
            String expMsg = e.getMessage();
            if (expMsg.equalsIgnoreCase(ApplicationConstants.OPENDJ_DISCONNECTED)) {
                return ApplicationConstants.OPENDJ_DISCONNECTED;
            } else {
                return myProperties.getProperty("userconfig.incorrect-curpassword");
            }

        }
    }

    /**
     *
     * @param userSecurityDtls
     * @param securityQuestion1
     * @param securityAnswer1
     * @return int
     * @throws AppException
     */
    @Override
    public int validateSecurityAnswersInVerifyUser(List<UserSecurityDetailsDTO> userSecurityDtls, String securityQuestion1, String securityAnswer1) throws AppException {
        int retVal = 0;
        if (!userSecurityDtls.isEmpty()) {
            for (UserSecurityDetailsDTO securityDTO : userSecurityDtls) {
                if (securityDTO.getEncUserSecurityKey().trim().equalsIgnoreCase(securityQuestion1.trim())) {
                    if (securityDTO.getSecurityAnswer().trim().equalsIgnoreCase(securityAnswer1.trim())) {
                        retVal = 1;
                    } else {
                        retVal = 0;
                    }
                }

            }
        }

        return retVal;
    }

    /**
     *
     * @param userProfileObj
     * @return int
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int updateUserMacAddressForNewDevice(final UserProfileDTO userProfileObj) throws AppException {
        return loginTransactDAO.updateUserLoginDetails(userProfileObj);
    }

    /**
     *
     * @param userId
     * @return long
     * @throws AppException
     */
    @Override
    public long updateUserLastLoginDate(final long userId) throws AppException {
        return loginDAO.updateUserLastLoginDate(userId);
    }

}
