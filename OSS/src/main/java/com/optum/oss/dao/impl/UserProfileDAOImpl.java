/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao.impl;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dao.UserProfileDAO;
import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.dto.PermissionDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.dto.UserSecurityDetailsDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.util.DateUtil;
import com.optum.oss.util.EncryptDecrypt;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author hpasupuleti
 */
public class UserProfileDAOImpl implements UserProfileDAO {

    private static final Logger logger = Logger.getLogger(UserProfileDAOImpl.class);

    /*
     Autowiring the required Class instances
     */
    @Autowired
    private EncryptDecrypt encryptDecrypt;
    @Autowired
    private DateUtil dateUtil;
    /*
     Autowiring the required Class instances
     */

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

    /**
     *
     * @param userID
     * @return int
     * @throws AppException
     */
    @Override
    public int updatePasswordResetDateTimeOnUserID(final long userID) throws AppException {
        int retVal = 0;
        String procName = "{CALL UPDATE_UserPasswordDetailsbyUID(?)}";
        try {
            retVal = jdbcTemplate.queryForObject(procName, new Object[]{userID}, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : updatePasswordResetDateTimeOnUserID : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "updatePasswordResetDateTimeOnUserID() :: " + e.getMessage());
        }
        return retVal;
    }

    /**
     *
     * @param userProfileObj
     * @param sessUserProfileObj
     * @return int
     * @throws AppException
     */
    @Override
    public int updateUserPersonnelInformation(final UserProfileDTO userProfileObj,
            final UserProfileDTO sessUserProfileObj) throws AppException {
        try {
            return transactionTemplate.execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 0;
                    int count = 0;
                    try {
                        String procName = "{CALL UPDATE_UserSecurDtlByID(?,?,?,?,?,?,?)}";
                        if ((!userProfileObj.getUserSecurityQuestionsListObj().isEmpty())
                                && !(sessUserProfileObj.getUserSecurityQuestionsListObj().isEmpty())) {
                            if (userProfileObj.getUserSecurityQuestionsListObj().size()
                                    == sessUserProfileObj.getUserSecurityQuestionsListObj().size()) {

                                for (int i = 0; i < userProfileObj.getUserSecurityQuestionsListObj().size(); i++) {
                                    UserSecurityDetailsDTO sessUserSecurityDTO
                                            = sessUserProfileObj.getUserSecurityQuestionsListObj().get(i);
                                    UserSecurityDetailsDTO userSecurityDTO
                                            = userProfileObj.getUserSecurityQuestionsListObj().get(i);

                                    count = jdbcTemplate.queryForObject(procName,
                                            new Object[]{sessUserSecurityDTO.getUserSecurityKey(),
                                                ApplicationConstants.DB_ROW_STATUS_ACTIVE_VALUE,
                                                sessUserProfileObj.getUserProfileKey(),
                                                userSecurityDTO.getSecurityQuestionKey(),
                                                userSecurityDTO.getSecurityAnswer(),
                                                i + 1,//SEQUENCE ORDER NO
                                                sessUserProfileObj.getUserProfileKey()//UPDATED USER ID
                                            },
                                            Integer.class);
                                    if (count < 0) {
                                        transactionStatus.setRollbackOnly();
                                        retVal = -1;
                                    } else {
                                        retVal += count;
                                    }
                                }

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:updateUserPersonnelInformation");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : updateUserPersonnelInformation : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "updateUserPersonnelInformation() :: " + e.getMessage());
        }
    }

    /**
     *
     * @param userProfileKey
     * @return List<PermissionDTO>
     * @throws AppException
     */
    @Override
    public List<PermissionDTO> getUserPermissionsByID(final long userProfileKey) throws AppException {
        List<PermissionDTO> permissionList = new ArrayList<>();
        String procName = "{CALL GetUserPermissionsByName(?)}";
        try {
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName,
                    new Object[]{userProfileKey});
            // CHECK USER DETAILS IF EXISTS
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {

                    PermissionDTO permissionDTO = new PermissionDTO();

                    permissionDTO.setPermissionKey((Integer) (resultMap.get("PERMSN_KEY")));
                    permissionDTO.setPermissionName((String) (resultMap.get("PERMSN_NM")));
                    permissionDTO.setPermissionDescription((String) (resultMap.get("PERMSN_DESC")));
                    permissionDTO.setDisplayTest((String) (resultMap.get("DSPL_TXT")));
                    permissionDTO.setChildExistsIndicator((Integer) (resultMap.get("CHLD_XST_IND")));
                    permissionDTO.setSequenceOrder((Integer) (resultMap.get("SEQ_ORDR")));
                    permissionDTO.setModuleId((Integer) (resultMap.get("ModuleID")));
                    permissionDTO.setMenuId((Integer) (resultMap.get("MenuID")));
                    permissionDTO.setSubMenuId((Integer) (resultMap.get("SubmenuID")));
                    permissionDTO.setModuleName((String) (resultMap.get("ModuleName")));
                    permissionDTO.setMenuName((String) (resultMap.get("MenuName")));
                    permissionDTO.setSubMenuName((String) (resultMap.get("SubmenuName")));

                    permissionList.add(permissionDTO);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : getUserPermissionsByID : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "getUserPermissionsByID() :: " + e.getMessage());
        }

        return permissionList;
    }

    @Override
    public List<UserProfileDTO> getUsersForPwdExpiryAlert(int days, int expiryDays) throws AppException {
        List<UserProfileDTO> usersList = new ArrayList<>();
        String procName = "{CALL List_PasswordResetUsers(?,?)}";
        try {
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName,
                    new Object[]{days, expiryDays});
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {

                    UserProfileDTO userDTO = new UserProfileDTO();
                    userDTO.setFirstName((String) (resultMap.get("FST_NM")));
                    userDTO.setLastName((String) (resultMap.get("LST_NM")));
                    userDTO.setPswdResetDate(dateUtil.dateConvertionFromDBToJSP(resultMap.get("PSWD_RSET_DT") + ""));
                    userDTO.setEmail((String) (resultMap.get("EMAIL_ID")));
                    userDTO.setUserProfileKey(Long.parseLong(resultMap.get("USER_ID") + ""));
                    usersList.add(userDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : getUsersForPwdExpiryAlert : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "getUsersForPwdExpiryAlert() :: " + e.getMessage());
        }
        return usersList;
    }

    @Override
    public List<ClientEngagementDTO> getServiceDueDatePassed() throws AppException {
        List<ClientEngagementDTO> dueDatePassedServiceList = new ArrayList();
        String procName = "{CALL GetServiceenddatepassedUsers()}";
        try {

            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName);
            // CHECK USER DETAILS IF EXISTS
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    ClientEngagementDTO dueDatePassedDTO = new ClientEngagementDTO();
                    dueDatePassedDTO.setEngagementCode(resultMap.get("CLNT_ENGMT_CD") + "");
                    dueDatePassedDTO.setSecurityServiceCode(resultMap.get("SECUR_SRVC_CD") + "");
                    dueDatePassedDTO.setSecurityServiceName(resultMap.get("SECUR_SRVC_NM") + "");
                    dueDatePassedDTO.setUserTypeFlag(resultMap.get("USER_TYP_KEY") + "");
                    dueDatePassedDTO.setCreatedByUser(resultMap.get("UserName") + "");
                    dueDatePassedDTO.setEstimatedStartDate(resultMap.get("SRVC_EST_STRT_DT") + "");
                    dueDatePassedDTO.setEstimatedEndDate(resultMap.get("SRVC_EST_END_DT") + "");
                    dueDatePassedDTO.setAnalystEmailID(resultMap.get("User Email") + "");
                    dueDatePassedDTO.setEngagementLeadClientUserName(resultMap.get("EngLeadName") + "");
                    dueDatePassedDTO.setCreatedByUserID(Long.parseLong(resultMap.get("USER_ID") + ""));
                    dueDatePassedServiceList.add(dueDatePassedDTO);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : getServiceDueDatePassed : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "getServiceDueDatePassed() :: " + e.getMessage());
        }

        return dueDatePassedServiceList;
    }

    /**
     *
     * @param @return void
     * @throws AppException
     */
    @Override
    public void updateUserLoginAttempts() throws AppException {
        int retVal = 0;
        String procName ="{CALL UPDATE_LoginCount()}";
        try {
            jdbcTemplate.execute(procName);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : updateUserLoginAttempts : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "updateUserLoginAttempts() :: " + e.getMessage());
        }
    }
}
