/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service.impl;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dao.impl.UserActivationDAOImpl;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.LDAPAuthenticate;
import com.optum.oss.helper.WebAppAdminMailHelper;
import com.optum.oss.service.UserActivationService;
import com.optum.oss.util.EncryptDecrypt;
import com.optum.oss.util.GenerateRandomNumber;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author schanganti
 */
@Service
public class UserActivationServiceImpl implements UserActivationService {

    private static final Logger log = Logger.getLogger(UserActivationServiceImpl.class);
    @Autowired
    private UserActivationDAOImpl useractivationDAO;
    @Autowired
    private EncryptDecrypt encryptDecrypt;

    @Autowired
    private WebAppAdminMailHelper adminMailHelper;

    @Autowired
    private UserManagementServiceImpl manageUserService;

    @Autowired
    private GenerateRandomNumber generateRandom;

    @Autowired
    private LDAPAuthenticate ldapAuthenticate;

    /**
     *
     * @param organizationKey
     * @return List<UserProfileDTO>
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<UserProfileDTO> userActivationWorklist(long organizationKey) throws AppException {
        return useractivationDAO.userActivationWorklist(organizationKey);
    }

    /**
     *
     * @param decUserID
     * @param decRowStatusKey
     * @param sessionUserID
     * @return int
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public int updateUserStatus(final String decUserID, final String decRowStatusKey, final long sessionUserID) throws AppException {
        int retVal = 0;
        int lockIndicator = 0;
        try {
            String decUserProfileKey = encryptDecrypt.decrypt(decUserID + "");
            long decUserKey = Long.parseLong(decUserProfileKey);

            String decStatusKey = encryptDecrypt.decrypt(decRowStatusKey + "");

            lockIndicator = Integer.valueOf(ApplicationConstants.APP_STATUS_INDICATOR_NO + "");
            retVal = useractivationDAO.updateUserStatus(decUserKey, lockIndicator, ApplicationConstants.DB_ROW_STATUS_ACTIVE_VALUE);

            UserProfileDTO userProfileObj = new UserProfileDTO();
            userProfileObj = manageUserService.viewUserProfile(decUserKey);

            if (retVal > 0) {
                String newPass = generateRandom.generateResPasswordToken();
                ldapAuthenticate.changeLDAPPassword(userProfileObj, newPass);
                //if (decStatusKey.equalsIgnoreCase(ApplicationConstants.USER_STATUS_LOCKED)) {
                if (ApplicationConstants.USER_STATUS_LOCKED.equals(encryptDecrypt.decrypt(decRowStatusKey))) {
                    adminMailHelper.mail_UserAccountUnLocked(userProfileObj, sessionUserID, newPass);
                    retVal = 2;
                    //}else if (decStatusKey.equalsIgnoreCase(ApplicationConstants.USER_STATUS_DEACTIVE)) {
                } else if (ApplicationConstants.USER_STATUS_DEACTIVE.equals(encryptDecrypt.decrypt(decRowStatusKey))) {
                    adminMailHelper.mail_UserAccountReactivated(userProfileObj, sessionUserID, newPass);
                    adminMailHelper.mail_UserAccountPassword(userProfileObj, newPass, sessionUserID);
                    retVal = 3;
                }
                //
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("Exceptionoccured : updateUserStatus : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "updateUserStatus() :: " + e.getMessage());
        }

        return retVal;
    }

    /**
     *
     * @param userEmail
     * @return UserProfileDTO
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public UserProfileDTO checkUserActiveSession(final String userEmail) throws AppException {
        return useractivationDAO.checkUserActiveSession(userEmail);
    }

}
