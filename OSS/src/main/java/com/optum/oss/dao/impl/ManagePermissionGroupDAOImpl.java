/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao.impl;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dao.ManagePermissionGroupDAO;
import com.optum.oss.dto.PermissionDTO;
import com.optum.oss.dto.PermissionGroupDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.util.EncryptDecrypt;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author sathuluri
 */
public class ManagePermissionGroupDAOImpl implements ManagePermissionGroupDAO {

    private static final Logger logger = Logger.getLogger(ManagePermissionGroupDAOImpl.class);
    private JdbcTemplate jdbcTemplate;

    /*
     Start : Setter for private variables
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    /*
     End : Setter for private variables
     */

    /*
     Start Autowiring the required Class instances
     */
    @Autowired
    private EncryptDecrypt encryptDecrypt;
    /*
     End Autowiring the required Class instances
     */

    /**
     * This method for fetching Manage Permission Group worklist
     *
     * @return List<PermissionGroupDTO>
     * @throws AppException
     */
    @Override
    public List<PermissionGroupDTO> fetchPermissionGroupWorkList() throws AppException {
        logger.info("Start: fetchPermissionGroupWorkList() for Manage PermissionGroup WorkList");
        String workListProc = "{CALL LIST_PermissionGroups()}";
        List<PermissionGroupDTO> permissionGroupWorkList = new ArrayList<>();
        try {
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(workListProc, new Object[]{});
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    PermissionGroupDTO permissionGroupDTO = new PermissionGroupDTO();
                    permissionGroupDTO.setPermissionGroupKey((Integer) (resultMap.get("PERMSN_GRP_KEY")));
                    permissionGroupDTO.setEncPermissionGroupKey(encryptDecrypt.encrypt(permissionGroupDTO.getPermissionGroupKey() + ""));
                    permissionGroupDTO.setRowStatusKey((Integer) (resultMap.get("ROW_STS_KEY")));
                    permissionGroupDTO.setPermissionGroupName(resultMap.get("PERMSN_GRP_NM") + "");
                    permissionGroupDTO.setPermissionGroupDesc(resultMap.get("PERMSN_GRP_DESC") + "");
                    if (null != resultMap.get("USERNAME") && "null" != resultMap.get("USERNAME")) {
                        permissionGroupDTO.setModifiedByUser(resultMap.get("USERNAME") + "");
                    }
                    if (null != resultMap.get("UPDT_USER_ID") && "null" != resultMap.get("UPDT_USER_ID")) {
                        permissionGroupDTO.setModifiedByUserID(Long.parseLong(resultMap.get("UPDT_USER_ID") + ""));
                    }
                    if (null != resultMap.get("UpdateDate") && "null" != resultMap.get("UpdateDate")) {
                        permissionGroupDTO.setModifiedDate(resultMap.get("UpdateDate") + "");
                    }
                    permissionGroupDTO.setRowStatusValue(resultMap.get("ROWSTATUS") + "");
                    if (ApplicationConstants.DB_ROW_STATUS_ACTIVE.equalsIgnoreCase(resultMap.get("ROWSTATUS") + "")) {
                        permissionGroupDTO.setReason("");
                    } else if (ApplicationConstants.DB_ROW_STATUS_DE_ACTIVE.equalsIgnoreCase(resultMap.get("ROWSTATUS") + "")) {
                        if (null != resultMap.get("STS_COMMT_TXT") && "null" != resultMap.get("STS_COMMT_TXT")) {
                            permissionGroupDTO.setReason(resultMap.get("STS_COMMT_TXT") + "");
                        }
                    }
                    permissionGroupWorkList.add(permissionGroupDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : fetchPermissionGroupWorkList:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "fetchPermissionGroupWorkList(): " + e.getMessage());
        }
        logger.info("End: fetchPermissionGroupWorkList() for Manage PermissionGroup WorkList");
        return permissionGroupWorkList;
    }

    /**
     * This method used to insert permission group
     *
     * @param permissionGroupDTO
     * @return insertId
     * @throws AppException
     */
    @Override
    public int savePermissionGroup(final PermissionGroupDTO permissionGroupDTO) throws AppException {
        logger.info("Start: savePermissionGroup() for insert permission group");
        String savePermissionGroupProc = "{CALL INS_PERMSN_GRP(?,?,?,?,?)}";
        int insertId = 0;
        try {
            insertId = jdbcTemplate.queryForObject(savePermissionGroupProc, new Object[]{
                permissionGroupDTO.getRowStatusKey(),
                permissionGroupDTO.getPermissionGroupName(),
                permissionGroupDTO.getPermissionGroupDesc(),
                permissionGroupDTO.getCreatedByUserID(),
                !"".equalsIgnoreCase(permissionGroupDTO.getReason())?permissionGroupDTO.getReason():null}, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : savePermissionGroup:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "savePermissionGroup(): " + e.getMessage());
        }
        logger.info("End: savePermissionGroup() for insert permission group");
        return insertId;
    }

    /**
     * This method used to update permission group
     *
     * @param permissionGroupDTO
     * @return updateId
     * @throws AppException
     */
    @Override
    public int updatePermissionGroup(final PermissionGroupDTO permissionGroupDTO) throws AppException {
        logger.info("Start: updatePermissionGroup() for update permission group");
        String updatePermissionGroupProc = "{CALL UPDATE_PermsnGrpByID(?,?,?,?,?,?)}";
        int updateId = 0;
        try {
            updateId = jdbcTemplate.queryForObject(updatePermissionGroupProc, new Object[]{
                permissionGroupDTO.getPermissionGroupKey(),
                permissionGroupDTO.getRowStatusKey(),
                permissionGroupDTO.getPermissionGroupName(),
                permissionGroupDTO.getPermissionGroupDesc(),
                permissionGroupDTO.getModifiedByUserID(),
                !"".equalsIgnoreCase(permissionGroupDTO.getReason())?permissionGroupDTO.getReason():null}, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : updatePermissionGroup:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "updatePermissionGroup(): " + e.getMessage());
        }
        logger.info("End: updatePermissionGroup() for update permission group");
        return updateId;
    }

    /**
     * This method used to insert permissions for permission group
     *
     * @param permissionGroupDTO
     * @return insertPermissionId
     * @throws AppException
     */
    @Override
    public int savePermissionsForPermissionGroup(final PermissionGroupDTO permissionGroupDTO) throws AppException {
        logger.info("Start: savePermissions() for insert permissions");
        String savePermissionsProc = "{CALL INS_PERMSN_GRP_ASSOC(?,?,?,?,?,?,?,?)}";
        int insertPermissionId = 0;
        try {
            if (!permissionGroupDTO.getPermissionListObj().isEmpty()) {
                for (PermissionDTO permissionDTO : permissionGroupDTO.getPermissionListObj()) {
                    insertPermissionId = jdbcTemplate.queryForObject(savePermissionsProc, new Object[]{
                        permissionGroupDTO.getPermissionGroupKey(),
                        1, //permission row status key
                        permissionDTO.getPermissionKey(),
                        permissionDTO.getModuleId(),
                        permissionDTO.getMenuId(),
                        permissionDTO.getSubMenuId(),
                        permissionGroupDTO.getCreatedByUserID(),
                        "I" //insert flag
                    }, Integer.class);
                    insertPermissionId = insertPermissionId + 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : savePermissions:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "savePermissions(): " + e.getMessage());
        }
        logger.info("End: updatePermissionGroup() for insert permissions");
        return insertPermissionId;
    }

    /**
     * This method for fetch permissions work list for permission group
     *
     * @param permissionGroupKey
     * @return List<PermissionDTO>
     * @throws AppException
     */
    @Override
    public List<PermissionDTO> fetchPermissionsForPermissionGroup(final long permissionGroupKey) throws AppException {
        logger.info("Start: fetchPermissions() to fetch permissions worklist");
        String workListProc = "{CALL LIST_Permissions(?)}";
        List<PermissionDTO> permissionsWorkList = new ArrayList<>();
        try {
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(workListProc, new Object[]{(int) permissionGroupKey});
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    PermissionDTO permissionDTO = new PermissionDTO();
                    permissionDTO.setPermissionKey((Integer) (resultMap.get("PERMSN_KEY")));
                    permissionDTO.setPermissionName(resultMap.get("PERMSN_NM") + "");
                    permissionDTO.setPermissionDescription(resultMap.get("PERMSN_DESC") + "");
                    permissionDTO.setDisplayTest(resultMap.get("DSPL_TXT") + "");
                    permissionDTO.setChildExistsIndicator((Integer) (resultMap.get("CHLD_XST_IND")));
                    permissionDTO.setSequenceOrder((Integer) (resultMap.get("SEQ_ORDR")));
                    permissionDTO.setModuleId((Integer) (resultMap.get("ModuleID")));
                    permissionDTO.setMenuId((Integer) (resultMap.get("MenuID")));
                    permissionDTO.setSubMenuId((Integer) (resultMap.get("SubmenuID")));
                    permissionsWorkList.add(permissionDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : fetchPermissions:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "fetchPermissions(): " + e.getMessage());
        }
        logger.info("End: fetchPermissions() to fetch permissions worklist");
        return permissionsWorkList;
    }

    /**
     * This method for delete permissions for permission group
     *
     * @param permissionsList
     * @param permsnGroupKey
     * @param userID
     * @return deleteRetVal
     * @throws AppException
     */
    @Override
    public int deletePermissionsForPermissionGroup(final List<PermissionDTO> permissionsList, final long permsnGroupKey, final long userID) throws AppException {
        logger.info("Start: deletePermissions() to delete permissions");
        String deletePermissionProc = "{CALL INS_PERMSN_GRP_ASSOC(?,?,?,?,?,?,?,?)}";
        int deleteRetVal = 0;
        try {
            if (!permissionsList.isEmpty()) {
                for (PermissionDTO permissionDTO : permissionsList) {
                    deleteRetVal = jdbcTemplate.queryForObject(deletePermissionProc, new Object[]{
                        permsnGroupKey,
                        1, //permission row status key
                        permissionDTO.getPermissionKey(),
                        permissionDTO.getModuleId(),
                        permissionDTO.getMenuId(),
                        permissionDTO.getSubMenuId(),
                        userID,
                        "D" //delete flag
                    }, Integer.class);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : deletePermissions:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "deletePermissions(): " + e.getMessage());
        }
        logger.info("End: deletePermissions() to delete permissions");
        return deleteRetVal;
    }

    /**
     * This method for delete permission group
     *
     * @param perGroupDTO
     * @return long
     * @throws AppException
     */
    @Override
    public int deletePermissionGroup(final PermissionGroupDTO perGroupDTO) throws AppException {
        logger.info("Start: deletePermissionGroup() to delete permission group");
        try {
            String deletePermissionGroupProc = "{CALL UPDATE_PermsnGrpStatusByID(?,?)}";
            int retVal = 0;
            //Start: update permission group with statuskey as 3 for delete permission group
            if (0 < perGroupDTO.getPermissionGroupKey()) {
                retVal = jdbcTemplate.queryForObject(deletePermissionGroupProc, new Object[]{
                    "D", //delete flag
                    perGroupDTO.getPermissionGroupKey()}, Integer.class);
            }
            //End: update permission group with statuskey as 3 for delete permission group
            return retVal;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : deletePermissionGroup:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "deletePermissionGroup(): " + e.getMessage());
        }
    }

    /**
     * This method for fetch manage permission group details by permission group
     * key
     *
     * @param groupKeyId
     * @return permissionGroupDTO
     * @throws AppException
     */
    @Override
    public PermissionGroupDTO fetchPermissionGroupById(long groupKeyId) throws AppException {
        logger.info("Start: fetchPermissionGroupById() to delete permission group");
        try {
            PermissionGroupDTO permissionGroupDTO = new PermissionGroupDTO();
            String fetchPermissionGroupById = "{CALL LIST_PermissionGroupsByPermGropID(?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(fetchPermissionGroupById, new Object[]{groupKeyId});
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    permissionGroupDTO.setPermissionGroupKey((Integer) (resultMap.get("PERMSN_GRP_KEY")));
                    permissionGroupDTO.setEncPermissionGroupKey(encryptDecrypt.encrypt(permissionGroupDTO.getPermissionGroupKey() + ""));
                    permissionGroupDTO.setRowStatusKey((Integer) (resultMap.get("ROW_STS_KEY")));
                    permissionGroupDTO.setPermissionGroupName(resultMap.get("PERMSN_GRP_NM") + "");
                    permissionGroupDTO.setEncPermissionGroupName(resultMap.get("PERMSN_GRP_NM") + "");
                    permissionGroupDTO.setPermissionGroupDesc(resultMap.get("PERMSN_GRP_DESC") + "");
                    permissionGroupDTO.setPermissionGroupStatusValue(resultMap.get("ROWSTATUS") + "");
                    if (1 == Long.parseLong(resultMap.get("ROW_STS_KEY") + "")) {
                        permissionGroupDTO.setReason("");
                    } else if (2 == Long.parseLong(resultMap.get("ROW_STS_KEY") + "")) {
                        if (null != resultMap.get("STS_COMMT_TXT") && "null" != resultMap.get("STS_COMMT_TXT")) {
                            permissionGroupDTO.setReason(resultMap.get("STS_COMMT_TXT") + "");
                        }
                    }
                }
            }
            return permissionGroupDTO;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : fetchPermissionGroupById:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "fetchPermissionGroupById(): " + e.getMessage());
        }
    }

    /**
     * This method for fetch manage permission group details by permission group
     * name
     *
     * @param groupName
     * @return retVal
     * @throws AppException
     */
    @Override
    public int fetchPermissionGroupById(String groupName) throws AppException {
        logger.info("Start: fetchPermissionGroupById() to delete permission group");
        try {
            String fetchPermissionGroupById = "{CALL Check_PERMSN_GRPName(?)}";
            int retVal = jdbcTemplate.queryForObject(fetchPermissionGroupById, new Object[]{groupName}, Integer.class);
            return retVal;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : fetchPermissionGroupById:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "fetchPermissionGroupById(): " + e.getMessage());
        }
    }
}
