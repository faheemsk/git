/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.transact.dao.impl;

import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.LDAPAuthenticate;
import com.optum.oss.helper.WebAppAdminMailHelper;
import com.optum.oss.service.impl.UserManagementServiceImpl;
import com.optum.oss.transact.dao.UserManagementTransactDAO;
import com.optum.oss.util.GenerateRandomNumber;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author sbhagavatula
 */
public class UsermanagementTransactDAOImpl implements UserManagementTransactDAO {

    private static final Logger logger = Logger.getLogger(UsermanagementTransactDAOImpl.class);

    @Autowired
    private LDAPAuthenticate lDAPAuthenticate;
    @Autowired
    private GenerateRandomNumber randonNumber;

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
    private UserManagementServiceImpl userManagementServiceImpl;
    
    @Autowired
    private WebAppAdminMailHelper adminMailHelper;

    /**
     *
     * @param userProfileDto
     * @param sessionDto
     * @return UserProfileDTO
     * @throws AppException
     */
    @Override
    public int saveUserDetails(final UserProfileDTO userProfileDto, final UserProfileDTO sessionDto) throws AppException {
        logger.info("Start: UsermanagementTransactDAOImpl : saveUserDetails");
        try {
            return transactionTemplate.execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 0, roleStat = 0;
                    try {
                        //SAVE USER DETAILS
                        retVal = userManagementServiceImpl.saveUserProfile(userProfileDto, sessionDto);
                        //GENERATE RANDON PASSWORD
                        String randomPass = randonNumber.generateResPasswordToken();
                        userProfileDto.setPassword(randomPass);
                        //CONDITION CHECK FOR INSERT USER DETAILS STATUS: SUCCESS IS retVal > 0 AND FAILURE IF retVal <= 0
                        if (retVal > 0) {
                            //CREATE USER IN LDAP SERVER
                            if (lDAPAuthenticate.createLDAPUser(userProfileDto)) {
                                userProfileDto.setUserProfileKey(retVal);
                                //INSERT ASSIGNED ROLES
                                roleStat = userManagementServiceImpl.saveUserApplicationRole(userProfileDto, sessionDto);
                                if (roleStat <= 0) {
                                    transactionStatus.setRollbackOnly();
                                    return -1;
                                } else {
                                    // MAIL SEND TO USER FOR USER AND PASSWORD CREATION
                                    int res1 = adminMailHelper.mail_UserAccountCreated(userProfileDto, sessionDto.getUserProfileKey());

                                    int res2 = adminMailHelper.mail_UserAccountPassword(userProfileDto, randomPass, sessionDto.getUserProfileKey());

                                    if (res1 <= 0 && res2 <= 0) {
                                        transactionStatus.setRollbackOnly();
                                        return -1;
                                    }
                                }
                            }
                            else {
                                //IF USER DETAILS INSERTION FAILS, ROLL BACK ALL THE TRANSACTIONS 
                                transactionStatus.setRollbackOnly();
                                return -1;
                            }
                        } else {
                            //IF USER DETAILS INSERTION FAILS, ROLL BACK ALL THE TRANSACTIONS 
                            transactionStatus.setRollbackOnly();
                            return -1;
                        }
                    } catch (AppException e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:saveUserDetails");
                         transactionStatus.setRollbackOnly();
                        return -2;
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:saveUserDetails");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : saveUserDetails:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "saveUserDetails() :: " + e.getMessage());
        }
    }

    /**
     *
     * @param userProfileDto
     * @return UserProfileDTO
     * @throws AppException
     */
    @Override
    public int updateUserDetails(final UserProfileDTO userProfileDto, final UserProfileDTO sessionDto) throws AppException {
        logger.info("Start: UsermanagementTransactDAOImpl : updateUserDetails");
        try {
            return transactionTemplate.execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 0, deletesRows = 0, roleStat = 0;
                    try {
                        //UPDATE USER DETAILS BASED ON USERPROFILE KEY
                        retVal = userManagementServiceImpl.updateUserProfile(userProfileDto,sessionDto);
                        //CONDITION CHECK FOR UPDATE USER DETAILS STATUS: SUCCESS IS retVal > 0 AND FAILURE IF retVal <= 0
                        if (retVal > 0) {
                            deletesRows = userManagementServiceImpl.deleteUserApplicationRoles(userProfileDto.getUserProfileKey(),sessionDto);
                            if (deletesRows >= 0) {
                                userManagementServiceImpl.saveUserApplicationRole(userProfileDto,sessionDto);
                            } else {
                                transactionStatus.setRollbackOnly();
                                return -1;
                            }
                        } else {
                            //IF USER DETAILS UPDATION FAILS, ROLL BACK ALL THE TRANSACTIONS
                            transactionStatus.setRollbackOnly();
                            return -1;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:updateUserDetails");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : updateUserDetails:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "updateUserDetails() :: " + e.getMessage());
        }
    }

}
