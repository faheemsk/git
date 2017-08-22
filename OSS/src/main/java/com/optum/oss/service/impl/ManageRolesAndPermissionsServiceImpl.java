/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service.impl;

import com.optum.oss.dao.impl.ManageRolesAndPermissionsDAOImpl;
import com.optum.oss.dto.PermissionGroupDTO;
import com.optum.oss.dto.RoleDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.service.ManageRolesAndPermissionsService;
import com.optum.oss.transact.dao.impl.ManageRolesAndPermissionsTransactDAOImpl;
import com.optum.oss.util.EncryptDecrypt;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author akeshavulu
 */
@Service
public class ManageRolesAndPermissionsServiceImpl implements ManageRolesAndPermissionsService {

    @Autowired
    private ManageRolesAndPermissionsDAOImpl manageRolesAndPermissionsDAO;
    
     @Autowired
     private ManageRolesAndPermissionsTransactDAOImpl manageRolesAndPermissionsTransactDAO;
     
     @Autowired
     private EncryptDecrypt encDec;
    
     /**
    *   @param manageRolesAndPermissionsDTO
    *   @return List<RoleDTO>
    *   @throws AppException
    */

    @Override
    public List<RoleDTO> getManageRolesAndPermissionsWorkList(RoleDTO manageRolesAndPermissionsDTO)throws AppException {
        List<RoleDTO> manageRolesPermissionsList = new ArrayList<RoleDTO>();
        manageRolesPermissionsList = manageRolesAndPermissionsDAO.getManageRolesAndPermissionsWorkList(manageRolesAndPermissionsDTO);
        return manageRolesPermissionsList;
    }

    /**
    *   @param RoleDTO
    *   @return int
    *   @throws AppException
    */
    @Override
    public int saveRoleDetails(RoleDTO roleDTO)throws AppException  {
        int successValue = manageRolesAndPermissionsDAO.saveRoleDetails(roleDTO);

        return successValue;
    }

    /**
    *   @param RoleDTO
    *   @return int
    *   @throws AppException
    */
    @Override
    public int deleteRoleDetails(RoleDTO roleDTO)throws AppException  {
         int successValue = manageRolesAndPermissionsDAO.deleteRoleDetails(roleDTO);
         return successValue;
    }

   /**
    *   @param PermissionGroupDTO
    *   @return List<PermissionGroupDTO>
    *   @throws AppException
    */
    @Override
    public List<PermissionGroupDTO> getRolesPermissionGroup() throws AppException {
         List<PermissionGroupDTO> PermissionGroupList = new ArrayList<PermissionGroupDTO>();
        PermissionGroupList = manageRolesAndPermissionsDAO.getRolesPermissionGroup();
        return PermissionGroupList;
    }

   /**
    *   @param RoleDTO
    *   @return List<PermissionGroupDTO>
    *   @throws AppException
    */
    @Override
    public List<PermissionGroupDTO> fetchRolePermissionsGrpDetails(RoleDTO manageRolesAndPermissionsDTO) throws AppException {
        List<PermissionGroupDTO> PermissionGroupList = new ArrayList<PermissionGroupDTO>();
        PermissionGroupList = manageRolesAndPermissionsDAO.fetchRolePermissionsGrpDetails(manageRolesAndPermissionsDTO);
        return PermissionGroupList;
    }
    
    /**
    *   @param encRoleKey    
    *   @return List<RoleDTO>
    *   @throws AppException
    */

    @Override
    public RoleDTO fetchAppRoleDetails(final String encRoleKey) throws AppException {
        long decryptRoleKey = 0;
        if (!StringUtils.isEmpty(encRoleKey)) {
            decryptRoleKey = Long.parseLong(encDec.decrypt(encRoleKey));
        }
        return manageRolesAndPermissionsDAO.fetchAppRoleDetails(decryptRoleKey);
    }

   /**
    *   @param RoleDTO
    *   @return int
    *   @throws AppException
    */
    @Override
    public int editRoleDetails(RoleDTO roleDTO) throws AppException {
        int successValue = manageRolesAndPermissionsDAO.editRoleDetails(roleDTO);
        return successValue;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public int saveAppRoleDetails(RoleDTO roleDTO) throws AppException {
       int successValue = manageRolesAndPermissionsDAO.saveAppRoleDetails(roleDTO);
        return successValue;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public int savePermissionGrpDetails(RoleDTO roleDTO, int roleKey) throws AppException {
        return  manageRolesAndPermissionsDAO.savePermissionGrpDetails(roleDTO, roleKey);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public int saveAppRolePermissionGrpDetails(RoleDTO roleDTO) throws AppException {
        return manageRolesAndPermissionsTransactDAO.saveAppRolePermissionGrpDetails(roleDTO);
        
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public int updateAppRoleDetails(RoleDTO roleDTO) throws AppException {
         return  manageRolesAndPermissionsDAO.updateAppRoleDetails(roleDTO);
        
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public int updatePermissionGrpDetails(RoleDTO roleDTO) throws AppException {
       return manageRolesAndPermissionsDAO.updatePermissionGrpDetails(roleDTO);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public int updateAppRolePermissionGrpDetails(RoleDTO roleDTO) throws AppException {
        roleDTO.setAppRoleKey(Long.valueOf(encDec.decrypt(roleDTO.getEncAppRoleKey())));
        return manageRolesAndPermissionsTransactDAO.updateAppRolePermissionGrpDetails(roleDTO);
    }

    

 

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int fetchAppRoleByName(String appRoleName) throws AppException {
        return manageRolesAndPermissionsDAO.fetchAppRoleByName(appRoleName);
    }
}
