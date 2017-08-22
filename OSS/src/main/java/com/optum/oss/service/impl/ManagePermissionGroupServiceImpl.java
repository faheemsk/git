/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service.impl;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dao.impl.ManagePermissionGroupDAOImpl;
import com.optum.oss.dto.PermissionDTO;
import com.optum.oss.dto.PermissionGroupDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.service.ManagePermissionGroupService;
import com.optum.oss.transact.dao.impl.ManagePermissionGroupTransactDAOImpl;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 *
 * @author sathuluri
 */
@Service
public class ManagePermissionGroupServiceImpl implements ManagePermissionGroupService {

    private static final Logger logger = Logger.getLogger(ManagePermissionGroupServiceImpl.class);

    @Autowired
    private ManagePermissionGroupDAOImpl managePermissionGroupDAO;

    @Autowired
    private ManagePermissionGroupTransactDAOImpl managePermissionGroupTransactDAO;

    /**
     * This method for fetching Manage Permission Group work list
     *
     * @return List<PermissionGroupDTO>
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<PermissionGroupDTO> fetchPermissionGroupWorkList() throws AppException {
        return managePermissionGroupDAO.fetchPermissionGroupWorkList();
    }

    /**
     * This method for insert new permission group
     *
     * @param permissionGroupDTO
     * @return insertId
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int savePermissionGroupDetails(final PermissionGroupDTO permissionGroupDTO) throws AppException {
        return managePermissionGroupDAO.savePermissionGroup(permissionGroupDTO);
    }

    /**
     * This method used to insert or update permission group
     *
     * @param permissionGroupDTO
     * @param oldPermissionsList
     * @param userDto
     * @param permissionData
     * @return insertId
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int saveOrUpdatePermissionGroup(final PermissionGroupDTO permissionGroupDTO, final List<PermissionDTO> oldPermissionsList,
            final UserProfileDTO userDto, final String[] permissionData) throws AppException {
        permissionGroupDTO.setCreatedByUserID(userDto.getUserProfileKey());
        if (0 < permissionGroupDTO.getPermissionGroupKey()) {
            permissionGroupDTO.setModifiedByUserID(userDto.getUserProfileKey());
        }
        if (ApplicationConstants.DB_ROW_STATUS_ACTIVE.equalsIgnoreCase(permissionGroupDTO.getPermissionGroupStatusValue())) {
            permissionGroupDTO.setRowStatusKey(ApplicationConstants.DB_ROW_STATUS_ACTIVE_VALUE);
        } else if (ApplicationConstants.DB_ROW_STATUS_DE_ACTIVE.equalsIgnoreCase(permissionGroupDTO.getPermissionGroupStatusValue())) {
            permissionGroupDTO.setRowStatusKey(ApplicationConstants.DB_ROW_STATUS_DE_ACTIVE_VALUE);
        }
        //Start: Parsing String data to DTO
        List<PermissionDTO> permisstionList = this.convertingStringDTO(permissionData);
        //End: Parsing String data to DTO
        permissionGroupDTO.setPermissionListObj(permisstionList);
        return managePermissionGroupTransactDAO.saveOrUpdatePermissionGroup(permissionGroupDTO, oldPermissionsList);
    }

    /**
     * This method used to update permission group
     *
     * @param permissionGroupDTO
     * @return updateId
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int updatePermissionGroupDetails(final PermissionGroupDTO permissionGroupDTO) throws AppException {
        return managePermissionGroupDAO.updatePermissionGroup(permissionGroupDTO);
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
        return managePermissionGroupDAO.savePermissionsForPermissionGroup(permissionGroupDTO);
    }

    /**
     * This method for permissions work list for permission group
     *
     * @param permissionGroupDTO
     * @return PermissionGroupDTO
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public List<PermissionDTO> fetchPermissionsForPermissionGroup(final PermissionGroupDTO permissionGroupDTO) throws AppException {
        List<PermissionDTO> permissionWorkList = managePermissionGroupDAO.fetchPermissionsForPermissionGroup(permissionGroupDTO.getPermissionGroupKey());
        return permissionWorkList;
    }

    /**
     * This method for delete permissions for permission group
     *
     * @param permissionsList
     * @param retVal
     * @param userID
     * @return permissionDeleteId
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int deletePermissionsForPermissionGroup(final List<PermissionDTO> permissionsList, final int retVal, final long userID) throws AppException {
        return managePermissionGroupDAO.deletePermissionsForPermissionGroup(permissionsList, retVal, userID);
    }

    /**
     * This method for delete permission group
     *
     * @param perGroupDTO
     * @return deleteId
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int deletePermissionGroup(final PermissionGroupDTO perGroupDTO) throws AppException {
        return managePermissionGroupTransactDAO.deletePermissionGroup(perGroupDTO);
    }

    /**
     * This method for delete permission group
     *
     * @param perGroupDTO
     * @return deleteId
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int deletePermissionGroupDetails(final PermissionGroupDTO perGroupDTO) throws AppException {
        return managePermissionGroupDAO.deletePermissionGroup(perGroupDTO);
    }

    /**
     * This method for converting string data to DTO
     *
     * @param permissionData
     * @return List<PermissionDTO>
     * @throws AppException
     */
    private List<PermissionDTO> convertingStringDTO(final String[] permissionData) throws AppException {
        List<PermissionDTO> permissionList = new ArrayList<>();
        logger.info("Start: convertingStringDTO");
        try {
            if (null != permissionData) {
                for (String s : permissionData) {
                    if (!StringUtils.isEmpty(s)) {
                        String[] permission = s.split("@");
                        if (null != permission && 4 == permission.length) {
                            PermissionDTO permissionDto = new PermissionDTO();
                            permissionDto.setPermissionKey(Long.parseLong(permission[0]));
                            permissionDto.setModuleId(Long.parseLong(permission[1]));
                            permissionDto.setMenuId(Long.parseLong(permission[2]));
                            permissionDto.setSubMenuId(Long.parseLong(permission[3]));
                            permissionList.add(permissionDto);
                        }
                    }
                }
            }
            logger.info("After converting size of dto: " + permissionList.size());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : convertingStringDTO:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "convertingStringDTO() : " + e.getMessage());
        }
        logger.info("End: convertingStringDTO");
        return permissionList;
    }

    /**
     * This method for filtering permissions for permission group to avoid
     * duplicates
     *
     * @param permissionGroupDTO
     * @param permissionList
     * @return PermissionGroupDTO
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public PermissionGroupDTO filterDetailedPermissionsForPermissionGroup(PermissionGroupDTO permissionGroupDTO, List<PermissionDTO> permissionList) throws AppException {
        List<PermissionDTO> permissionsList = new ArrayList<>();
        permissionGroupDTO.setPermissionListObj(permissionList);

        if (0 < permissionGroupDTO.getPermissionGroupKey()) {
            Map<Long, PermissionDTO> permissionsMap = new LinkedHashMap<>();

            List<PermissionDTO> totalPermissionsList = managePermissionGroupDAO.fetchPermissionsForPermissionGroup(0);
            for (PermissionDTO savedPermissionDTO : permissionList) {
                for (PermissionDTO totalPermissionDTO : totalPermissionsList) {
                    if (savedPermissionDTO.getPermissionKey() == totalPermissionDTO.getPermissionKey()) {
                        totalPermissionDTO.setPermissionTypeKey(1);
                        permissionsMap.put(totalPermissionDTO.getPermissionKey(), totalPermissionDTO);
                    } else {
                        if (!permissionsMap.containsKey(totalPermissionDTO.getPermissionKey())) {
                            permissionsMap.put(totalPermissionDTO.getPermissionKey(), totalPermissionDTO);
                        }
                    }
                }
            }
            for (Map.Entry entry : permissionsMap.entrySet()) {
                permissionsList.add((PermissionDTO) entry.getValue());
            }
            permissionGroupDTO.setPermissionListObj(permissionsList);
        }
        return permissionGroupDTO;
    }

    /**
     * This method for fetching permission group by permission group id
     *
     * @param groupKeyId
     * @return PermissionGroupDTO
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public PermissionGroupDTO fetchPermissionGroupById(long groupKeyId) throws AppException {
        return managePermissionGroupDAO.fetchPermissionGroupById(groupKeyId);
    }

    /**
     * This method for fetching permission group by permission group name
     *
     * @param groupName
     * @return PermissionGroupDTO
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int fetchPermissionGroupByName(String groupName) throws AppException {
        return managePermissionGroupDAO.fetchPermissionGroupById(groupName);
    }

    /**
     * This method for converting string data to array
     *
     * @param permissionKeyChkIds
     * @return String[]
     * @throws AppException
     */
    public String[] convertingStringToArray(final String[] permissionKeyChkIds) throws AppException {
        logger.info("Start: convertingStringToArray");
        String permisChkIds[] = new String[permissionKeyChkIds.length];
        int count = 0;
        try {
            for (String permsIds : permissionKeyChkIds) {
                if (!StringUtils.isEmpty(permsIds)) {
                    String[] permission = permsIds.split("@");
                    if (null != permission) {
                        permisChkIds[count] = permission[0];
                        count++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : convertingStringToArray:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "convertingStringToArray() : " + e.getMessage());
        }
        logger.info("End: convertingStringToArray");
        return permisChkIds;
    }
}
