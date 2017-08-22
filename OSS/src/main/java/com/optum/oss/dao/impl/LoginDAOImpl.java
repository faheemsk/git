/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao.impl;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dao.LoginDAO;
import com.optum.oss.dto.UserApplicationRoleDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.dto.UserSecurityDetailsDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.util.EncryptDecrypt;
import com.optum.oss.util.DateUtil;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author hpasupuleti
 */
public class LoginDAOImpl implements LoginDAO {

    private static final Logger logger = Logger.getLogger(LoginDAOImpl.class);

    private JdbcTemplate jdbcTemplate;

    /*
     Start : Setter getters for private variables
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /*
     End : Setter getters for private variables
     */

    /*
     Autowiring the required Class instances
     */
    @Autowired
    private EncryptDecrypt encryptDecrypt;

    @Autowired
    private DateUtil dateUtil;

    /**
     *
     * @param userName
     * @return UserProfileDTO
     * @throws AppException
     */
    @Override
    public UserProfileDTO fetchUserDetailsByUserName(final String userName) throws AppException {
        UserProfileDTO userProfileObj = new UserProfileDTO();
        try {
            String usrProfileProc = "{CALL GetUserDetailsByName(?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(usrProfileProc,
                    new Object[]{userName.trim()});
            // CHECK USER DETAILS IF EXISTS
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    userProfileObj.setDataExistsFlag(ApplicationConstants.DB_INDICATOR_YES);

                    userProfileObj.setUserProfileKey((Integer) (resultMap.get("USER_ID")));
                    userProfileObj.setEncUserProfileKey(encryptDecrypt.encrypt(userProfileObj.getUserProfileKey() + ""));
                    userProfileObj.setOrganizationKey((Integer) (resultMap.get("ORG_KEY")));
                    userProfileObj.setUserTypeKey((Integer) (resultMap.get("USER_TYP_KEY")));
                    userProfileObj.getUserTypeObj().setLookUpEntityName((String) (resultMap.get("USER_TYPE")));
                    userProfileObj.getOrganizationObj().setRowStatusKey((Integer) (resultMap.get("ORG_STS_KEY")));
                    userProfileObj.setFirstName((String) (resultMap.get("FST_NM")));
                    userProfileObj.setLastName((String) (resultMap.get("LST_NM")));
                    userProfileObj.getOrganizationObj().setOrganizationName((String) resultMap.get("ORG_NM"));
                    if (resultMap.get("MIDL_NM") != null) {
                        userProfileObj.setMiddleName((String) (resultMap.get("MIDL_NM")));
                    } else {
                        userProfileObj.setMiddleName("");
                    }

                    if (resultMap.get("JOB_TITL_NM") != null) {
                        userProfileObj.setJobTitleName((String) (resultMap.get("JOB_TITL_NM")));
                    } else {
                        userProfileObj.setJobTitleName("");
                    }

                    userProfileObj.setEmail((String) (resultMap.get("EMAIL_ID")));
                    if (resultMap.get("TEL_NBR") != null) {
                        userProfileObj.setTelephoneNumber((String) (resultMap.get("TEL_NBR")));
                    } else {
                        userProfileObj.setTelephoneNumber("");
                    }

                    userProfileObj.setLockIndicator((Integer) (resultMap.get("LCK_IND")));
                    if (resultMap.get("LOGIN_ATMPT_CNT") != null) {
                        userProfileObj.setLoginAttemptCount((Integer) (resultMap.get("LOGIN_ATMPT_CNT")));
                    } else {
                        userProfileObj.setLoginAttemptCount(0);
                    }
                    if (resultMap.get("ROW_STS_KEY") != null) {
                        userProfileObj.setRowStatusKey((Integer) (resultMap.get("ROW_STS_KEY")));
                    } else {
                        userProfileObj.setRowStatusKey(0);
                    }
                    if ((resultMap.get("LST_LOGIN_DT")) != null) {
                        String convDate = dateUtil.convertTimestampToString((Timestamp) (resultMap.get("LST_LOGIN_DT")));
                        userProfileObj.setLastLoginDate(dateUtil.dateConvertionFromDBToJSP(convDate));
                    } else {
                        userProfileObj.setLastLoginDate("");
                    }
                    if ((resultMap.get("PSWD_RSET_DT")) != null) {
                        String convDate = dateUtil.convertTimestampToString((Timestamp) (resultMap.get("PSWD_RSET_DT")));
                        userProfileObj.setPswdResetDate(dateUtil.dateConvertionFromDBToJSP(convDate));
                    } else {
                        userProfileObj.setPswdResetDate("");
                    }
                    userProfileObj.setUserVerifiedIndicator((Integer) (resultMap.get("USER_VERF_IND")));

                    if (resultMap.get("MAC_ADR_NM") != null) {
                        userProfileObj.setUserMACAddress((String) (resultMap.get("MAC_ADR_NM")));
                    } else {
                        userProfileObj.setUserMACAddress("");
                    }

                }
            } else {
                userProfileObj.setDataExistsFlag(ApplicationConstants.DB_INDICATOR_NO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : fetchUserDetailsByUserName:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "fetchUserDetailsByUserName() :: " + e.getMessage());
        }

        return userProfileObj;
    }

    /**
     *
     * @param userProfileKey
     * @return List<UserSecurityDetailsDTO>
     * @throws AppException
     */
    @Override
    public List<UserSecurityDetailsDTO> fetchUserSecurityQuestionDetails(final long userProfileKey)
            throws AppException {
        String usrSecurityProc = "{CALL GetUserSecurityDetailsByName(?)}";
        List<UserSecurityDetailsDTO> securityDetailsList = new ArrayList<>();
        try {
            List<Map<String, Object>> usrQuesList = jdbcTemplate.queryForList(usrSecurityProc, new Object[]{userProfileKey});
            if (!usrQuesList.isEmpty()) {
                for (Map<String, Object> securityMap : usrQuesList) {
                    UserSecurityDetailsDTO securityDetailsDTO = new UserSecurityDetailsDTO();

                    securityDetailsDTO.setUserSecurityKey((Integer) (securityMap.get("USER_SECUR_DTL_KEY")));
                    securityDetailsDTO.setEncUserSecurityKey(encryptDecrypt.encrypt
                                            (securityDetailsDTO.getUserSecurityKey()+""));
                    securityDetailsDTO.setSecurityQuestionKey((Integer) (securityMap.get("SECUR_QUES_KEY")));
                    securityDetailsDTO.setSecurityQuestion((String) (securityMap.get("SECUR_QUES")));
                    securityDetailsDTO.setSecurityAnswer((String) (securityMap.get("ANS_TXT")));
                    securityDetailsDTO.setSequenceOrder((Integer) (securityMap.get("SEQ_ORDR_NBR")));

                    securityDetailsList.add(securityDetailsDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : fetchUserSecurityQuestionDetails:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "fetchUserSecurityQuestionDetails() :: " + e.getMessage());
        }

        return securityDetailsList;
    }

    /**
     *
     * @param userProfileKey
     * @return List<UserApplicationRoleDTO>
     * @throws AppException
     */
    @Override
    public List<UserApplicationRoleDTO> fetchUserRolePermissionDetails(final long userProfileKey)
            throws AppException {
        String usrRolePermsProc = "";
        List<UserApplicationRoleDTO> userRolesList = new ArrayList<>();
        try {
//            List<Map<String, Object>> usrRoleList = jdbcTemplate.queryForList(usrRolePermsProc, new Object[]{userProfileKey});
//            if (!usrRoleList.isEmpty()) {
//                for (Map<String, Object> rolesMap : usrRoleList) {
//
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : fetchUserRolePermissionDetails:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "fetchUserRolePermissionDetails() :: " + e.getMessage());
        }

        return userRolesList;
    }

    /**
     *
     * @param userProfileKey
     * @param lockFlag
     * @return int
     * @throws AppException
     */
    @Override
    public int lockUnlockUserBasedOnUserID(final long userProfileKey, final long lockFlag)
            throws AppException {
        int retVal = 0;
        String proc = "{CALL UPDATE_LockedIndicatorInUserProfile(?,?)}";
        try {
            retVal = jdbcTemplate.update(proc,
                    new Object[]{userProfileKey, lockFlag});
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : lockUnlockUserBasedOnUserID:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "lockUnlockUserBasedOnUserID() :: " + e.getMessage());
        }

        return retVal;
    }

    /**
     *
     * @param userProfileKey
     * @return int
     * @throws AppException
     */
    @Override
    public int updateUserLoginAttemptsBasedOnUserID(final long userProfileKey)
            throws AppException {
        int retVal = 0;
        String proc = "{CALL UPDATE_LoginAttemptsinUserProfile(?)}";
        try {
            retVal = jdbcTemplate.update(proc,
                    new Object[]{userProfileKey});
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : updateUserLoginAttemptsBasedOnUserID:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "updateUserLoginAttemptsBasedOnUserID() :: " + e.getMessage());
        }

        return retVal;
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
    public int updateUserLastLoginDateAndMacAddrBasedOnUserID(final long userProfileKey,
            final String macAddress, final String lastLoginDateTime)
            throws AppException {
        int retVal = 0;
        String proc = "{CALL UPDATE_LastloginbyUserid(?,?,?)}";
        try {
            retVal = jdbcTemplate.update(proc,
                    new Object[]{userProfileKey, lastLoginDateTime, macAddress});
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : updateUserLastLoginDateAndMacAddrBasedOnUserID:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "updateUserLastLoginDateAndMacAddrBasedOnUserID() :: " + e.getMessage());
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
    public int insertUserDetailsIntoUserSessionInfo(final UserProfileDTO userProfileObj)
            throws AppException {
        int retVal = 0;
        String procName = "{CALL INS_USER_SESS_LOG(?,?,?,?)}";
        try
        {
            retVal = jdbcTemplate.queryForObject(procName, 
                        new Object[]{userProfileObj.getUserProfileKey(),
                        userProfileObj.getEmail(),
                        userProfileObj.getUserSessionID(),
                        dateUtil.generateDBCurrentDateInString()},Integer.class);
            
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : insertUserDetailsIntoUserSessionInfo:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "insertUserDetailsIntoUserSessionInfo() :: " + e.getMessage());
        }
        return retVal;
    }

    /**
     *
     * @param lastAccessTime
     * @param userID
     * @param dbFlag
     * @param sessionID
     * @return int
     * @throws AppException
     */
    @Override
    public int updateUserSessionInfoByUserID(final String lastAccessTime,
            final long userID,final String dbFlag,final String sessionID)
            throws AppException {
        int retVal = 0;
        String procName = "{CALL UPDATE_USER_SESS_LOG(?,?,?,?)}";
        try
        {
            retVal = jdbcTemplate.update(procName,
                            new Object[]{dbFlag,
                            userID,
                            "",
                            sessionID});
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : updateUserSessionInfoByUserID:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "updateUserSessionInfoByUserID() :: " + e.getMessage());
        }
        return retVal;
    }
    /**
     *
     * @param userProfileObj
     * @return int
     * @throws AppException
     */
    public int insertUserSecurityDetails(final UserProfileDTO userProfileObj) throws AppException {
        int retVal = 0;
        String proc = "{CALL INS_USER_SECUR_DTL(?,?,?,?,?,?)}"; // USER ID, SECUR_QUES_KEY, ANS_TXT, SEQ_ORDR_NBR, CREAT_USER_ID
        try {
            if (!userProfileObj.getUserSecurityQuestionsListObj().isEmpty()) {
                for (UserSecurityDetailsDTO dto : userProfileObj.getUserSecurityQuestionsListObj()) {
                    jdbcTemplate.queryForObject(proc, new Object[]{1, userProfileObj.getUserProfileKey(), dto.getSecurityQuestionKey(),
                        dto.getSecurityAnswer(), 0, userProfileObj.getUserProfileKey()}, Integer.class);
                }
            }
            retVal = 1;
        } catch (Exception e) {
            retVal = 0;
            e.printStackTrace();
            logger.error("Exception occurred : insertUserSecurityDetails:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "insertUserSecurityDetails() :: " + e.getMessage());
        }
        return retVal;

    }
    /**
     *
     * @param userId
     * @return long
     * @throws AppException
     */
    public int updateUserVerifiedIndicator(final long userId) throws AppException {
        int retVal = 0;
        String proc = "{CALL UPDATE_VERIFICATIONINDICATORBYUSERID(?)}"; // USER ID
        try {
            jdbcTemplate.queryForObject(proc, new Object[]{userId}, Integer.class);
            retVal = 1;
        } catch (Exception e) {
            retVal = 0;
            e.printStackTrace();
            logger.error("Exception occurred : updateUserVerifiedIndicator:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "updateUserVerifiedIndicator() :: " + e.getMessage());
        }
        return retVal;

    }
    
    /**
     *
     * @param userId
     * @return long
     * @throws AppException
     */
    @Override
    public long updateUserLastLoginDate(final long userId) throws AppException {
        int retVal = 0;
        String proc = "{CALL UPDATE_LastLogindtbyUserid(?)}"; // USER ID
        try {
            jdbcTemplate.update(proc, new Object[]{userId});
            retVal = 1;
        } catch (Exception e) {
            retVal = 0;
            logger.error("Exception occurred : updateUserVerifiedIndicator:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "updateUserVerifiedIndicator() :: " + e.getMessage());
        }
        return retVal;

    }
}
