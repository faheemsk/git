/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.transact.dao.impl;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dao.impl.LoginDAOImpl;
import com.optum.oss.dto.UserApplicationRoleDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.dto.UserSecurityDetailsDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.LDAPAuthenticate;
import com.optum.oss.helper.WebAppAdminMailHelper;
import com.optum.oss.service.impl.LoginServiceImpl;
import com.optum.oss.service.impl.UserActivationServiceImpl;
import com.optum.oss.transact.dao.LoginTransactDAO;
import com.optum.oss.util.DateUtil;
import com.optum.oss.util.GenerateRandomNumber;
import com.optum.oss.util.SHAHashing;
import java.util.List;
import java.util.Properties;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author hpasupuleti
 */
public class LoginTransactDAOImpl implements LoginTransactDAO {

    private static final Logger logger = Logger.getLogger(LoginTransactDAOImpl.class);

    private JdbcTemplate jdbcTemplate;
    private TransactionTemplate transactionTemplate;

    /*
     Start : Setter getters for private variables
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public TransactionTemplate getTransactionTemplate() {
        return transactionTemplate;
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }
    /*
     End : Setter getters for private variables
     */

    /*
     Start : Autowiring the required Class instances
     */
    @Autowired
    private LoginServiceImpl loginService;

    @Autowired
    private LDAPAuthenticate ldapAuthenticate;

    @Autowired
    private LoginDAOImpl loginDao;

    @Autowired
    private SHAHashing sessionHash;

    @Autowired
    private UserActivationServiceImpl userActivationService;

    @Resource(name = "myProperties")
    private Properties myProperties;

    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private WebAppAdminMailHelper adminMailHelper;

    @Autowired
    private GenerateRandomNumber random;

    /*
     End : Autowiring the required Class instances
     */
    /*
     START : LOG MESSAGES
     */
    private final String TRACE_USER_EXIST_IN_DB = "User account avalialbe in DB";
    private final String TRACE_USER_PSWD_VALD = "User account valid in LDAP";
    private final String TRACE_USER_PSWD_INVLD = "User account is invalid LDAP";

    /*
     END : LOG MESSAGES
     */
    /**
     *
     * @param userName
     * @param password
     * @return UserProfileDTO
     * @throws AppException
     */
    @Override
    public UserProfileDTO validateUserLogin(final String userName, final String password)
            throws AppException {
        UserProfileDTO userProfileObj = new UserProfileDTO();

        try {
            // CHECK USER DETAILS IF EXISTS
            userProfileObj = loginService.fetchUserDetailsByUserName(userName);
            if (userProfileObj.getDataExistsFlag().equalsIgnoreCase(ApplicationConstants.DB_INDICATOR_YES)) {
                //USER NAME EXISTS ... CHECK THE PASSWORD
                // CHECK IF THE USER IS LOCKED
                MDC.put("user", userName);
                MDC.put("ip", "");
                logger.trace(TRACE_USER_EXIST_IN_DB);

                if (userProfileObj.getLockIndicator() == ApplicationConstants.APP_STATUS_INDICATOR_YES) {
                    // USER IS LOCKED...
                    userProfileObj.setUserValidateMessage(myProperties.getProperty("uservalidate.account-locked"));
                } // CHECK IF THE USER IS DEACTIVE
                else if (userProfileObj.getRowStatusKey() == Integer.valueOf(ApplicationConstants.DB_ROW_STATUS_DE_ACTIVE_VALUE)) {
                    // USER IS DEACTIVE...
                    userProfileObj.setUserValidateMessage(myProperties.getProperty("uservalidate.user-deactive"));
                } else if (userProfileObj.getOrganizationObj().getRowStatusKey() == Integer.valueOf(ApplicationConstants.DB_ROW_STATUS_DE_ACTIVE_VALUE)) {
                    // OGANIZATION IS DEACTIVE...
                    userProfileObj.setUserValidateMessage(myProperties.getProperty("uservalidate.organization-deactive"));
                } else if (ldapAuthenticate.validateUserInLDAP(userName, password)) {
                    //IF PASSWORD IS VALID... PREPARE THE USER OBJECT
                    MDC.put("user", userName);
                    MDC.put("ip", "");
                    logger.trace(TRACE_USER_PSWD_VALD);
                    userProfileObj.setUserValidateMessage("");
                    // CHECK FOR USER ACTIVE SESSION
                    UserProfileDTO userDto = userActivationService.checkUserActiveSession(userName);
                    String concurrentSession = "";
                    if (System.getProperty("OSS_ALLOW_CONCURRENT_SESSION") != null) {
                        concurrentSession = System.getProperty("OSS_ALLOW_CONCURRENT_SESSION");
                    }

                    // FETCH USER RELATED SECURITY INFORMATION
                    List<UserSecurityDetailsDTO> securityDetailsList = loginService.
                            fetchUserSecurityQuestionDetails(userProfileObj.getUserProfileKey());

                    if (!securityDetailsList.isEmpty()) {
                        userProfileObj.setUserSecurityQuestionsListObj(securityDetailsList);
                    }

                    // FETCH USER RELATED PERMISSION(S) INFORMATION
                    List<UserApplicationRoleDTO> userRolesList = loginService.
                            fetchUserRolePermissionDetails(userProfileObj.getUserProfileKey());

                    if (!userRolesList.isEmpty()) {
                        userProfileObj.setUserApplicationRoleListObj(userRolesList);
                    }

                    //CHECK USER PASSWORD EXPIRY NOTIFICATION
                    if (!StringUtils.isEmpty(userProfileObj.getPswdResetDate())) {
                        long dateDiff = dateUtil.calculateDateDiffWithCurrentDateTime(userProfileObj.getPswdResetDate());

                        long notifStartDate = Integer.valueOf(ApplicationConstants.APP_PSWD_EXPIRY_DAYS)
                                - Integer.valueOf(ApplicationConstants.APP_PSWD_EXPIRY_NOTIF_DAYS);

                        if (dateDiff >= Integer.valueOf(ApplicationConstants.APP_PSWD_EXPIRY_DAYS)) {
                            userProfileObj.setPswdExpiryDays(0);
                            userProfileObj.setPswdExpiryFlag('C');
                        } else if (dateDiff >= notifStartDate
                                && dateDiff < Integer.valueOf(ApplicationConstants.APP_PSWD_EXPIRY_DAYS)) {
                            int diffDays = Integer.valueOf(ApplicationConstants.APP_PSWD_EXPIRY_DAYS)
                                    - Integer.valueOf(dateDiff + "");
                            userProfileObj.setPswdExpiryDays(diffDays);
                            userProfileObj.setPswdExpiryFlag('Y');
                        } else {
                            userProfileObj.setPswdExpiryDays(0);
                            userProfileObj.setPswdExpiryFlag('N');
                        }
                    }

                    // INSERT USER LOGIN DETAILS TO USER SESSION INFO
                    // CHECK FOR ACTIVE SESSION AND ENABLE FLAG
                    if (concurrentSession.equalsIgnoreCase("false")) {
                        logger.info("Test message2 :: "+userDto.getUserSessionID());
                        if (!StringUtils.isEmpty(userDto.getUserSessionID())) {
                            userProfileObj.setActiveSessionFlag('Y');
                            userProfileObj.setUserSessionID(sessionHash.generateSHAHash(userName + random.generatePasswordToken()));
                        } else {
                            userProfileObj.setActiveSessionFlag('N');
                            userProfileObj.setUserSessionID(sessionHash.generateSHAHash(userName + random.generatePasswordToken()));
                            int retVal = loginService.insertUserDetailsIntoUserSessionInfo(userProfileObj);
                        }
                    } else {
                        userProfileObj.setActiveSessionFlag('N');
                        userProfileObj.setUserSessionID(sessionHash.generateSHAHash(userName + random.generatePasswordToken()));
                        int retVal = loginService.insertUserDetailsIntoUserSessionInfo(userProfileObj);
                    }

                } else {
                    // USER PASSWORD WAS INCORRECT
                    MDC.put("user", userName);
                    MDC.put("ip", "");
                    logger.trace(TRACE_USER_PSWD_INVLD);
                    if (userProfileObj.getLoginAttemptCount() == ApplicationConstants.USER_LOGIN_ATTEMPTS) {
                        // UPDATE LOGIN ATTEMPTS TO '0' & 
                        loginService.updateUserLoginAttemptsBasedOnUserID(userProfileObj.getUserProfileKey());

                        //LOCK THE USER BY UPDATING LOCKED INDICATOR
                        loginService.lockUnlockUserBasedOnUserID(userProfileObj.getUserProfileKey(),
                                ApplicationConstants.APP_STATUS_INDICATOR_YES);

                        userProfileObj.setUserValidateMessage(myProperties.getProperty("uservalidate.account-locked"));

                        // Start : MAIL SEND TO ADMIN - USER LOCKED.
                        adminMailHelper.mail_UserAccountLocked(userProfileObj, userProfileObj.getUserProfileKey());
                        // End : MAIL SEND TO ADMIN - USER LOCKED.
                    } else {
                        // UPDATE THE LOGIN ATTEMPTS IN THE USER PROFILE
                        loginService.updateUserLoginAttemptsBasedOnUserID(userProfileObj.getUserProfileKey());
                        userProfileObj.setUserValidateMessage(myProperties.getProperty("uservalidate.invalid-pswd"));
                    }
                    
                    }
            } //IF USER NAME DOESNOT EXISTS
            else {
                // USER NAME WAS INCORRECT
                userProfileObj.setUserValidateMessage(myProperties.getProperty("uservalidate.invalid-credentials"));
            }
        } catch(AppException e){
            logger.error("Exception occurred : validateUserLogin:" + e.getMessage());
            String expMsg = e.getMessage();
            if (expMsg.equalsIgnoreCase(ApplicationConstants.OPENDJ_DISCONNECTED)) {
                userProfileObj.setUserValidateMessage(ApplicationConstants.OPENDJ_DISCONNECTED);
            }
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : validateUserLogin:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "validateUserLogin() :: " + e.getMessage());
        }

        return userProfileObj;
    }

    /**
     *
     * @param userSecDtlsObj
     * @return int
     * @throws AppException
     */
    @Override
    public int insertUserSecurityDetails(final UserProfileDTO userSecDtlsObj) throws AppException {
        try {
            return transactionTemplate.execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 1;
                    try {
                        loginDao.insertUserSecurityDetails(userSecDtlsObj);
                        loginDao.updateUserVerifiedIndicator(userSecDtlsObj.getUserProfileKey());
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:updateUserLoginDetails");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : updateUserLoginDetails:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "updateUserLoginDetails() :: " + e.getMessage());
        }
    }

    /**
     *
     * @param userProfileObj
     * @return int
     * @throws AppException
     */
    @Override
    public int updateUserLoginDetails(final UserProfileDTO userProfileObj) throws AppException {
        try {
            return transactionTemplate.execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 0;
                    try {
                        int last_login_ret = loginService.updateUserLastLoginDateAndMacAddrBasedOnUserID(userProfileObj.getUserProfileKey(),
                                userProfileObj.getUserMACAddress(),
                                userProfileObj.getLastLoginDate());

//                        int user_sess_ret = loginService.insertUserDetailsIntoUserSessionInfo(userProfileObj);
//
//                        if (!(last_login_ret > 0) && !(user_sess_ret > 0)) {
//                            transactionStatus.setRollbackOnly();
//                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:updateUserLoginDetails");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : updateUserLoginDetails:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "updateUserLoginDetails() :: " + e.getMessage());
        }
    }

    /**
     *
     * @param userProfileObj
     * @return
     * @throws AppException
     */
    @Override
    public int killUserActiveSessionDetails(final UserProfileDTO userProfileObj) throws AppException {
        try {
            return transactionTemplate.execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 0;
                    try {
                        loginService.updateUserSessionInfoByUserID(ApplicationConstants.DB_INDICATOR_DELETE, userProfileObj);

                        retVal = loginService.insertUserDetailsIntoUserSessionInfo(userProfileObj);

                        if (!(retVal > 0)) {
                            transactionStatus.setRollbackOnly();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:killUserActiveSessionDetails");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : killUserActiveSessionDetails:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "killUserActiveSessionDetails() :: " + e.getMessage());
        }
    }
}
