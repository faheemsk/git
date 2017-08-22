/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao.impl;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dao.ManageRolesAndPermissionsDAO;
import com.optum.oss.dto.PermissionGroupDTO;
import com.optum.oss.dto.RoleDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.util.EncryptDecrypt;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author akeshavulu
 */
public class ManageRolesAndPermissionsDAOImpl implements ManageRolesAndPermissionsDAO {

    private static final Logger logger = Logger.getLogger(LoginDAOImpl.class);
    private JdbcTemplate jdbcTemplate;

    private TransactionTemplate transactionTemplate;

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
     Start Autowiring the required Class instances
     */
    @Autowired
    private EncryptDecrypt encryptDecrypt;
    /*
     End Autowiring the required Class instances
     */

    /**
     * @param manageRolesAndPermissionsDTO
     * @return List<RoleDTO>
     * @throws AppException
     */
    @Override
    public List<RoleDTO> getManageRolesAndPermissionsWorkList(RoleDTO manageRolesAndPermissionsDTO) throws AppException {

        RoleDTO manageRolesPermissionsDTO = null;
        List<RoleDTO> manageRolesPermissionsList = new ArrayList<RoleDTO>();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String updatedDate = "";
        try {
            String rolesListProc = "{CALL LIST_AppRole()}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(rolesListProc);
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    manageRolesPermissionsDTO = new RoleDTO();
                    manageRolesPermissionsDTO.setAppRoleKey(Long.parseLong(resultMap.get("APPL_ROLE_KEY") + ""));
                    manageRolesPermissionsDTO.setEncAppRoleKey(encryptDecrypt.encrypt(manageRolesPermissionsDTO.getAppRoleKey()+""));
                    manageRolesPermissionsDTO.setAppRoleName((resultMap.get("APPL_ROLE_NM") + ""));
                    manageRolesPermissionsDTO.setAppRoleDescription((resultMap.get("APPL_ROLE_DESC") + ""));
                    manageRolesPermissionsDTO.setModifiedByUser((resultMap.get("USERNAME") + ""));
                    if (null != resultMap.get("UPDT_DT")) {
                        updatedDate = format.format(resultMap.get("UPDT_DT"));
                    }
                    //  manageRolesPermissionsDTO.setModifiedDate((resultMap.get("UPDT_DT") + ""));
                    manageRolesPermissionsDTO.setModifiedDate(updatedDate);
                    manageRolesPermissionsDTO.setRowStatusKey(Long.parseLong(resultMap.get("ROW_STS_KEY") + ""));
                    manageRolesPermissionsDTO.setAppRoleComments((resultMap.get("STS_COMMT_TXT") + ""));
                    manageRolesPermissionsList.add(manageRolesPermissionsDTO);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : getManageRolesAndPermissionsWorkList:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "getManageRolesAndPermissionsWorkList() :: " + e.getMessage());
        }

        return manageRolesPermissionsList;
    }

    /**
     * @param roleDTO
     * @return int
     * @throws AppException
     */
    @Override
    public int saveRoleDetails(RoleDTO roleDTO) throws AppException {
        int saveRoleRetVal = 0;
        int savePrnGrpRetVal = 0;
        String pvcFlag = "I";
        try {
            String prnGroupIDS = "";
            String saveRoleDetailsprocName = "{CALL INS_APPL_ROLE(?,?,?,?)}";
            String savePermissionGrpProcName = "{CALL INS_APPL_ROLE_PERMSN_GRP(?,?,?,?,?)}";
            if (roleDTO.getPrnGrpIDs().endsWith(",")) {
                prnGroupIDS = roleDTO.getPrnGrpIDs().substring(0, roleDTO.getPrnGrpIDs().length() - 1);
            }
            StringTokenizer prmnIDs = new StringTokenizer(prnGroupIDS, ",");
            String rowStatusVal = roleDTO.getRowStatusValue();
            if (rowStatusVal.equalsIgnoreCase("active")) {
                roleDTO.setRowStatusKey(1);
            } else if (rowStatusVal.equalsIgnoreCase("deactive")) {
                roleDTO.setRowStatusKey(2);
            }
            saveRoleRetVal = jdbcTemplate.queryForObject(saveRoleDetailsprocName, new Object[]{roleDTO.getRowStatusKey(), roleDTO.getAppRoleName(),
                roleDTO.getAppRoleDescription(), roleDTO.getCreatedByUserID()}, Integer.class);

            while (prmnIDs.hasMoreElements()) {

                savePrnGrpRetVal = jdbcTemplate.queryForObject(savePermissionGrpProcName, new Object[]{saveRoleRetVal, prmnIDs.nextElement(),
                    roleDTO.getRowStatusKey(), roleDTO.getCreatedByUserID(), pvcFlag}, Integer.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : saveRoleDetails:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "saveRoleDetails() :: " + e.getMessage());
        }
        return saveRoleRetVal;
    }

    /**
     * @param roleDTO
     * @return int
     * @throws AppException
     */
    @Override
    public int deleteRoleDetails(RoleDTO roleDTO) throws AppException {
        String procName = "";
        String pvcFlag = "D";
        int deletePrnGrpRetVal = 0;
        try {

            //String savePermissionGrpProcName = "{CALL INS_APPL_ROLE_PERMSN_GRP(?,?,?,?,?)}";
            //  deletePrnGrpRetVal = jdbcTemplate.queryForObject(savePermissionGrpProcName, new Object[]{roleDTO.getAppRoleKey(), 0,0,0,pvcFlag},
            //           Integer.class);
            String savePermissionGrpProcName = "{CALL UPDATE_AppRolePermsnGrpStatusByID(?)}";
            deletePrnGrpRetVal = jdbcTemplate.queryForObject(savePermissionGrpProcName, new Object[]{roleDTO.getAppRoleKey()}, Integer.class);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : deleteRoleDetails:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "deleteRoleDetails() :: " + e.getMessage());
        }
        return deletePrnGrpRetVal;
    }

    /**
     * @param prmnGroupDTO
     * @return List<PermissionGroupDTO>
     * @throws AppException
     */
    @Override
    public List<PermissionGroupDTO> getRolesPermissionGroup() throws AppException {
        PermissionGroupDTO permissionGroupDTO = null;
        List<PermissionGroupDTO> permissionGroupList = new ArrayList<PermissionGroupDTO>();
        try {
            String rolesListProc = "{CALL LIST_PermissionGroups()}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(rolesListProc);
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    if(Long.parseLong(resultMap.get("ROW_STS_KEY") + "") == ApplicationConstants.DB_ROW_STATUS_ACTIVE_VALUE){
                        permissionGroupDTO = new PermissionGroupDTO();
                        permissionGroupDTO.setPermissionGroupKey(Long.parseLong(resultMap.get("PERMSN_GRP_KEY") + ""));
                        permissionGroupDTO.setRowStatusKey(Long.parseLong(resultMap.get("ROW_STS_KEY") + ""));
                        permissionGroupDTO.setPermissionGroupName((resultMap.get("PERMSN_GRP_NM") + ""));
                        permissionGroupDTO.setPermissionGroupDesc((resultMap.get("PERMSN_GRP_DESC") + ""));
                        permissionGroupList.add(permissionGroupDTO);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : getRolesPermissionGroup:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "getRolesPermissionGroup() :: " + e.getMessage());
        }
        return permissionGroupList;
    }

    /**
     * @param permissionGroupDTO
     * @return int
     * @throws AppException
     */
    @Override
    public int addRolePermissionGroup(PermissionGroupDTO permissionGroupDTO) throws AppException {
        int retVal = 0;
        try {

            String savePermissionGrpProcName = "{CALL INS_APPL_ROLE_PERMSN_GRP(?,?,?,?)}";

            //   retVal = jdbcTemplate.queryForObject(savePermissionGrpProcName, new Object[]{permissionGroupDTO., roleDTO.getAppRoleName(),
            //       roleDTO.getAppRoleDescription(),roleDTO.getCreatedByUserID()}, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : addRolePermissionGroup:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "addRolePermissionGroup() :: " + e.getMessage());
        }
        return retVal;
    }

    /**
     * @param manageRolesAndPermissionsDTO
     * @return List<PermissionGroupDTO>
     * @throws AppException
     */
    @Override
    public List<PermissionGroupDTO> fetchRolePermissionsGrpDetails(RoleDTO manageRolesAndPermissionsDTO) throws AppException {
        PermissionGroupDTO permissionsGrpDTO = null;
        List<PermissionGroupDTO> permissionsGrpList = new ArrayList<PermissionGroupDTO>();
        String appRoleKey = String.valueOf(manageRolesAndPermissionsDTO.getAppRoleKey());
        LinkedHashMap<Long, PermissionGroupDTO> selectedPrmnGrpMap = new LinkedHashMap<Long, PermissionGroupDTO>();
        try {
            String rolesListProc = "{CALL LIST_PermissionGroupsByRole(?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(rolesListProc, new Object[]{appRoleKey});
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    permissionsGrpDTO = new PermissionGroupDTO();
                    Long prmnGrpKey = Long.parseLong(resultMap.get("PERMSN_GRP_KEY") + "");
                    permissionsGrpDTO.setPermissionGroupKey(Long.parseLong(resultMap.get("PERMSN_GRP_KEY") + ""));
                    permissionsGrpDTO.setRowStatusKey(Long.parseLong(resultMap.get("ROW_STS_KEY") + ""));
                    permissionsGrpDTO.setPermissionGroupName((resultMap.get("PERMSN_GRP_NM") + ""));
                    permissionsGrpDTO.setPermissionGroupDesc((resultMap.get("PERMSN_GRP_DESC") + ""));
                    permissionsGrpList.add(permissionsGrpDTO);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : fetchRolePermissionsGrpDetails:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "fetchRolePermissionsGrpDetails() :: " + e.getMessage());
        }

        return permissionsGrpList;
    }

    /**
     * @param roleKey
     * @return List<RoleDTO>
     * @throws AppException
     */
    @Override
    public RoleDTO fetchAppRoleDetails(final long roleKey) throws AppException {

        RoleDTO manageRolesPermissionsDTO = null;
      
        try {
            String rolesListProc = "{CALL GetAppRoleDetailsByID(?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(rolesListProc, new Object[]{roleKey});
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    manageRolesPermissionsDTO = new RoleDTO();
                    
                    manageRolesPermissionsDTO.setAppRoleKey(Long.parseLong(resultMap.get("APPL_ROLE_KEY") + ""));
                    manageRolesPermissionsDTO.setEncAppRoleKey
                                    (encryptDecrypt.encrypt(manageRolesPermissionsDTO.getAppRoleKey()+""));
                    manageRolesPermissionsDTO.setRowStatusKey(Long.parseLong(resultMap.get("ROW_STS_KEY") + ""));
                    manageRolesPermissionsDTO.setAppRoleName((resultMap.get("APPL_ROLE_NM") + ""));
                    manageRolesPermissionsDTO.setAppRoleDescription((resultMap.get("APPL_ROLE_DESC") + ""));
                    manageRolesPermissionsDTO.setAppRoleComments(resultMap.get("STS_COMMT_TXT") + "");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : fetchAppRoleDetails:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "fetchAppRoleDetails() :: " + e.getMessage());
        }

        return manageRolesPermissionsDTO;
    }

    /**
     * @param roleDTO
     * @return int
     * @throws AppException
     */
    @Override
    public int editRoleDetails(RoleDTO roleDTO) throws AppException {
        int updatedRoleRetVal = 0;
        int savePrnGrpRetVal = 0;
        String pvcFlagInsert = "I";
        String pvcFlagDelete = "D";
        try {
            String prnGroupIDS = "";
            String saveRoleDetailsprocName = "{CALL UPDATE_AppRoleByID(?,?,?,?,?)}";

            String updateDeletePermissionGrpProcName = "{CALL INS_APPL_ROLE_PERMSN_GRP(?,?,?,?,?)}";

            savePrnGrpRetVal = jdbcTemplate.queryForObject(updateDeletePermissionGrpProcName, new Object[]{roleDTO.getAppRoleKey(), 0,
                0, 0, pvcFlagDelete}, Integer.class);

            if (roleDTO.getPrnGrpIDs().endsWith(",")) {
                prnGroupIDS = roleDTO.getPrnGrpIDs().substring(0, roleDTO.getPrnGrpIDs().length() - 1);
            }
            StringTokenizer prmnIDs = new StringTokenizer(prnGroupIDS, ",");
            String rowStatusVal = roleDTO.getRowStatusValue();
            if (rowStatusVal.equalsIgnoreCase("active")) {
                roleDTO.setRowStatusKey(1);
            } else if (rowStatusVal.equalsIgnoreCase("deactive")) {
                roleDTO.setRowStatusKey(2);
            }
            updatedRoleRetVal = jdbcTemplate.queryForObject(saveRoleDetailsprocName, new Object[]{roleDTO.getAppRoleKey(), roleDTO.getRowStatusKey(),
                roleDTO.getAppRoleName(), roleDTO.getAppRoleDescription(), roleDTO.getCreatedByUserID()}, Integer.class);

            while (prmnIDs.hasMoreElements()) {

                savePrnGrpRetVal = jdbcTemplate.queryForObject(updateDeletePermissionGrpProcName, new Object[]{roleDTO.getAppRoleKey(), prmnIDs.nextElement(),
                    roleDTO.getRowStatusKey(), roleDTO.getCreatedByUserID(), pvcFlagInsert}, Integer.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : editRoleDetails:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "editRoleDetails() :: " + e.getMessage());
        }
        return updatedRoleRetVal;
    }

    /**
     *
     * @param roleDTO
     * @return int
     * @throws AppException
     */
    @Override
    public int saveAppRoleDetails(RoleDTO roleDTO) throws AppException {
        int saveRoleRetVal = 0;
        String pvcFlag = "I";
        try {
            String saveRoleDetailsprocName = "{CALL INS_APPL_ROLE(?,?,?,?,?)}";
            String rowStatusVal = roleDTO.getRowStatusValue();
            if (rowStatusVal.equalsIgnoreCase("active")) {
                roleDTO.setRowStatusKey(ApplicationConstants.DB_ROW_STATUS_ACTIVE_VALUE);
                roleDTO.setAppRoleComments("");
            } else if (rowStatusVal.equalsIgnoreCase("deactive")) {
                roleDTO.setRowStatusKey(ApplicationConstants.DB_ROW_STATUS_DE_ACTIVE_VALUE);
            }
            saveRoleRetVal = jdbcTemplate.queryForObject(saveRoleDetailsprocName, new Object[]{roleDTO.getRowStatusKey(), roleDTO.getAppRoleName(),
                roleDTO.getAppRoleDescription(), roleDTO.getCreatedByUserID(), 
                !"".equalsIgnoreCase(roleDTO.getAppRoleComments())?roleDTO.getAppRoleComments():null}, Integer.class);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : saveAppRoleDetails:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "saveAppRoleDetails() :: " + e.getMessage());
        }
        return saveRoleRetVal;
    }

    /**
     *
     * @param roleDTO
     * @param roleKey
     * @return int
     * @throws AppException
     */
    @Override
    public int savePermissionGrpDetails(RoleDTO roleDTO, int roleKey) throws AppException {
        int saveRoleRetVal = 0;
        int savePrnGrpRetVal = 0;
        String pvcFlag = "I";
        try {
            String savePermissionGrpProcName = "{CALL INS_APPL_ROLE_PERMSN_GRP(?,?,?,?,?)}";
            Long[] permissionGrpIDs = roleDTO.getPermissionGroupIDs();
            /*
            String prnGroupIDS = "";
            if (roleDTO.getPrnGrpIDs().endsWith(",")) {
                prnGroupIDS = roleDTO.getPrnGrpIDs().substring(0, roleDTO.getPrnGrpIDs().length() - 1);
            }
            StringTokenizer prmnIDs = new StringTokenizer(prnGroupIDS, ",");
            String rowStatusVal = roleDTO.getRowStatusValue();
            while (prmnIDs.hasMoreElements()) 
                savePrnGrpRetVal = jdbcTemplate.queryForObject(savePermissionGrpProcName, new Object[]{roleKey, Long.valueOf(prmnIDs.nextToken()),
                    roleDTO.getRowStatusKey(), roleDTO.getCreatedByUserID(), pvcFlag}, Integer.class);
            }
            */
            if (permissionGrpIDs.length > 0) {
                for (Long permGrpID : permissionGrpIDs) {
                    if (permGrpID != null) {
                        savePrnGrpRetVal = jdbcTemplate.queryForObject(savePermissionGrpProcName, new Object[]{roleKey,
                            Long.valueOf(permGrpID),
                            roleDTO.getRowStatusKey(), roleDTO.getCreatedByUserID(),
                            pvcFlag}, Integer.class);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : savePermissionGrpDetails:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "savePermissionGrpDetails() :: " + e.getMessage());
        }
        return savePrnGrpRetVal;
    }

    /**
     *
     * @param roleDTO
     * @return int
     * @throws AppException
     */
    @Override
    public int updateAppRoleDetails(RoleDTO roleDTO) throws AppException {
        int updatedRoleRetVal = 0;
        try {
            String saveRoleDetailsprocName = "{CALL UPDATE_AppRoleByID(?,?,?,?,?,?)}";
            String rowStatusVal = roleDTO.getRowStatusValue();
            if (rowStatusVal.equalsIgnoreCase("active")) {
                roleDTO.setRowStatusKey(ApplicationConstants.DB_ROW_STATUS_ACTIVE_VALUE);
                roleDTO.setAppRoleComments("");
            } else if (rowStatusVal.equalsIgnoreCase("deactive")) {
                roleDTO.setRowStatusKey(ApplicationConstants.DB_ROW_STATUS_DE_ACTIVE_VALUE);
            }
            updatedRoleRetVal = jdbcTemplate.queryForObject(saveRoleDetailsprocName, new Object[]{roleDTO.getAppRoleKey(), roleDTO.getRowStatusKey(),
                roleDTO.getAppRoleName(), roleDTO.getAppRoleDescription(), roleDTO.getCreatedByUserID(), 
                !"".equalsIgnoreCase(roleDTO.getAppRoleComments())?roleDTO.getAppRoleComments():null}, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : updateAppRoleDetails:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "updateAppRoleDetails() :: " + e.getMessage());
        }
        return updatedRoleRetVal;
    }

    /**
     *
     * @param roleDTO
     * @param roleKey
     * @return int
     * @throws AppException
     */
    @Override
    public int updatePermissionGrpDetails(RoleDTO roleDTO) throws AppException {
        int savePrnGrpRetVal = 0;
        String pvcFlagInsert = "I";
        String pvcFlagDelete = "D";
        try {
            String updateDeletePermissionGrpProcName = "{CALL INS_APPL_ROLE_PERMSN_GRP(?,?,?,?,?)}";
            savePrnGrpRetVal = jdbcTemplate.queryForObject(updateDeletePermissionGrpProcName, new Object[]{roleDTO.getAppRoleKey(), 0,
                0, 0, pvcFlagDelete}, Integer.class);

            Long[] permissionGrpIDs = roleDTO.getPermissionGroupIDs();
            if(permissionGrpIDs.length>0)
            {
                for(Long permGrpID : permissionGrpIDs)
                {
                    if(permGrpID != null)
                    {
                        savePrnGrpRetVal = jdbcTemplate.queryForObject(updateDeletePermissionGrpProcName, new Object[]{roleDTO.getAppRoleKey(), 
                            Long.valueOf(permGrpID),
                            roleDTO.getRowStatusKey(), roleDTO.getCreatedByUserID(),
                            pvcFlagInsert}, Integer.class);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : updatePermissionGrpDetails:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "updatePermissionGrpDetails() :: " + e.getMessage());
        }
        return savePrnGrpRetVal;
    }

    @Override
    public int fetchAppRoleByName(String roleName) throws AppException {
        logger.info("Start: fetchAppRoleByName() ");
        try {
            String fetchAppRoleName = "{CALL Check_ROLEName(?)}";
            int retVal = jdbcTemplate.queryForObject(fetchAppRoleName, new Object[]{roleName}, Integer.class);
            return retVal;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : fetchAppRoleByName:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "fetchAppRoleByName(): " + e.getMessage());
        }
    }

}
