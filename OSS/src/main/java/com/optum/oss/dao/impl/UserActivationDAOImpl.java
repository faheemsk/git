/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao.impl;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dao.UserActivationDAO;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.util.DateUtil;
import com.optum.oss.util.EncryptDecrypt;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author schanganti
 */
public class UserActivationDAOImpl implements UserActivationDAO {

    private static final Logger logger = Logger.getLogger(UserActivationDAOImpl.class);
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
     Start : Autowiring the required Class instances
     */
    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private EncryptDecrypt encryptDecrypt;
    /*
     End : Autowiring the required Class instances
     */
    /*
     @parm  organizationKey
     @return List<UserProfileDTO>
     @throws AppException
     */

    @Override
    public List<UserProfileDTO> userActivationWorklist(long organizationKey) throws AppException {
        String procName = "";
        List<UserProfileDTO> uaList = null;
        UserProfileDTO userDTO = null;

        try {
            procName = "{CALL List_UserWorklistByOrg(?)}";
            uaList = new ArrayList<UserProfileDTO>();
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName,
                    new Object[]{organizationKey});
            if (!resultList.isEmpty()) {
                for (Map<String, Object> rowMap : resultList) {
                    userDTO = new UserProfileDTO();
                    // FETCHING LOCKED AND DEACTIVATED USERS LIST
                    if (((Integer) (rowMap.get("LCK_IND"))).equals(ApplicationConstants.DB_ROW_STATUS_LOCKED)
                            || rowMap.get("ROW_STS_KEY").equals(ApplicationConstants.DB_ROW_STATUS_DE_ACTIVE)) {

                        MasterLookUpDTO master = new MasterLookUpDTO();
                        master.setLookUpEntityName((String) rowMap.get("USER_TYP_KEY"));
                        userDTO.setUserTypeObj(master);
                        userDTO.setUserProfileKey((Integer) rowMap.get("USER_ID"));
                        userDTO.setEncUserProfileKey(encryptDecrypt.encrypt(userDTO.getUserProfileKey() + ""));
                        userDTO.getOrganizationObj().setOrganizationName((String) rowMap.get("ORG_NM"));
                        userDTO.setFirstName((String) rowMap.get("First Name"));
                        userDTO.setLastName((String) rowMap.get("LST_NM"));
                        userDTO.setEmail((String) rowMap.get("EMAIL_ID"));
                        if (((Integer) (rowMap.get("LCK_IND"))).equals(ApplicationConstants.DB_ROW_STATUS_LOCKED)) {
                            userDTO.setRowStatusValue("Locked");
                            userDTO.setEncRowStatusValue(encryptDecrypt.encrypt(userDTO.getRowStatusValue()));
                        } else if (rowMap.get("ROW_STS_KEY").equals(ApplicationConstants.DB_ROW_STATUS_DE_ACTIVE)) {
                            userDTO.setRowStatusValue(rowMap.get("ROW_STS_KEY") + "");
                            userDTO.setEncRowStatusValue(encryptDecrypt.encrypt(userDTO.getRowStatusValue()));
                        }
                        uaList.add(userDTO);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occurred : userActivationWorklist:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "userActivationWorklist() :: " + e.getMessage());
        }
        return uaList;
    }
    /*
     @parm  UserID,LockIndicator,RowStatusKey
     @return int
     @throws AppException
     */

    @Override
    public int updateUserStatus(long UserID, int lockIndicator, int statusIndicator) throws AppException {
        String procName = "";
        List<UserProfileDTO> uaList = null;
        UserProfileDTO userDTO = null;
        int retVal = 0;
        try {
            procName = "{CALL UPDATE_UnlockUser(?,?,?)}";

            retVal = jdbcTemplate.queryForObject(procName, new Object[]{UserID, lockIndicator,
                statusIndicator}, Integer.class);

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occurred : updateUserStatus:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
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
    public UserProfileDTO checkUserActiveSession(final String userEmail) throws AppException {
        String procName = "{CALL GetUserSessLogByID(?)}";
        UserProfileDTO userProfileObj = new UserProfileDTO();
        int retVal = 0;
        try {

            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName,
                    new Object[]{userEmail});
            if (!resultList.isEmpty()) {
                for (Map<String, Object> rowMap : resultList) {
                    userProfileObj.setUserSessionID((String) (rowMap.get("SESS_ID")));
                    if (rowMap.get("LST_ACT_DT") != null) {
                        String strLastAccTime = dateUtil.convertTimestampToString((Timestamp) (rowMap.get("LST_ACT_DT")));
                        userProfileObj.setLastActionDateTime(strLastAccTime);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occurred : checkUserActiveSession:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "checkUserActiveSession() :: " + e.getMessage());
        }
        return userProfileObj;
    }

}
