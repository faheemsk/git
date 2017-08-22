/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service.impl;

import com.optum.oss.dao.impl.DetailedPermissionsDAOImpl;
import com.optum.oss.dto.PermissionDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.service.DetailedPermissionsService;
import com.optum.oss.util.EncryptDecrypt;
import java.util.List;
import org.apache.commons.lang.NumberUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author bpilla
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author hpasupuleti
 */
@Service
public class DetailedPermissionsServiceImpl implements DetailedPermissionsService {

    private static final Logger logger = Logger.getLogger(MasterLookUpServiceImpl.class);

    /*
     Start : Autowiring the required Class instances
     */
    @Autowired
    private DetailedPermissionsDAOImpl detailedPermissionsDAO;
    
    @Autowired
    private EncryptDecrypt encDecrypt;

    /*
     End : Autowiring the required Class instances
     */
    /**
     * Fetches the data from MSTR_LKP Table based on Entity Type Name.
     *
     * @param groupID
     * @return List<MasterLookUpDTO>
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<PermissionDTO> fetchViewDetailedPermissions(final long groupID) throws AppException{
        return detailedPermissionsDAO.fetchViewDetailedPermissions(groupID);
    }

    /**
     *
     * @param permissionDesc
     * @param encPermissionKey
     * @return
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int saveDefinition(final String permissionDesc, final String encPermissionKey) 
            throws AppException {
        PermissionDTO  permissionDTO = new PermissionDTO();
        long permissionKey=0;
        if(!StringUtils.isEmpty(encPermissionKey))
        {
            String decPermissionKey = encDecrypt.decrypt(encPermissionKey);
            if(NumberUtils.isNumber(decPermissionKey))
            {
                permissionKey = Long.parseLong(decPermissionKey);
            }
            permissionDTO.setPermissionKey(permissionKey);
            permissionDTO.setPermissionDescription(permissionDesc);
            
            return detailedPermissionsDAO.saveDefinition(permissionDTO);
        }
        else
        {
            return -1;
        }
    }

}
