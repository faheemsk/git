/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.transact.dao.impl;

import com.optum.oss.dto.PermissionDTO;
import com.optum.oss.dto.PermissionGroupDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.service.impl.ManagePermissionGroupServiceImpl;
import com.optum.oss.transact.dao.ManagePermissionGroupTransactDAO;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author sathuluri
 */
public class ManagePermissionGroupTransactDAOImpl implements ManagePermissionGroupTransactDAO {

    private static final Logger logger = Logger.getLogger(ManagePermissionGroupTransactDAOImpl.class);

    private TransactionTemplate transactionTemplate;

    @Autowired
    private ManagePermissionGroupServiceImpl managePermissionGroupServiceImpl;

    /*
     Start : Setter getters for private variables
     */
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
     * This method used for save or update permission group
     *
     * @param permissionGroupDTO
     * @param oldPermissionsList
     * @return retVal
     * @throws AppException
     */
    @Override
    public int saveOrUpdatePermissionGroup(final PermissionGroupDTO permissionGroupDTO, final List<PermissionDTO> oldPermissionsList) throws AppException {
        try {
            logger.info("Start: saveOrUpdatePermissionGroup() for add or update permission group");
            return transactionTemplate.execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    logger.info("Start: Transaction started for saveOrUpdatePermissionGroup");
                    int retVal = 0;
                    int insRetVal = 0;
                    int updateRetVal = 0;
                    int delPermRetVal = 0;
                    try {
                        //Start: save permission group data
                        if (0 == permissionGroupDTO.getPermissionGroupKey()) {
                            insRetVal = managePermissionGroupServiceImpl.savePermissionGroupDetails(permissionGroupDTO);
                            permissionGroupDTO.setPermissionGroupKey(insRetVal);
                        } //End: save permission group data
                        //Start: update permission group data
                        else if (0 < permissionGroupDTO.getPermissionGroupKey()) {
                            updateRetVal = managePermissionGroupServiceImpl.updatePermissionGroupDetails(permissionGroupDTO);
                        }
                        //End: update permission group data
                        if ((insRetVal > 0) || (updateRetVal >= 0)) {
                            //Start: delete already saved permissions when update with new permissions
                            if (!oldPermissionsList.isEmpty()) {
                                delPermRetVal = managePermissionGroupServiceImpl.deletePermissionsForPermissionGroup(oldPermissionsList, (int) permissionGroupDTO.getPermissionGroupKey(),
                                        permissionGroupDTO.getCreatedByUserID());
                            }
                            //End: delete already saved permissions when update with new permissions

                            //Start: save permissions for permission group
                            if (!(permissionGroupDTO.getPermissionListObj()).isEmpty() && delPermRetVal >= 0) {
                                delPermRetVal = managePermissionGroupServiceImpl.savePermissionsForPermissionGroup(permissionGroupDTO);
                                retVal = 1;
                            } //End: save permissions for permission group
                            else {
                                transactionStatus.setRollbackOnly();
                                retVal = -1;
                            }
                        } else {
                            transactionStatus.setRollbackOnly();
                            retVal = -1;
                        }
                        logger.info("End: Transaction for saveOrUpdatePermissionGroup");
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:saveOrUpdatePermissionGroup");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    logger.info("End: saveOrUpdatePermissionGroup() for add new permission group");
                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : saveOrUpdatePermissionGroup:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "saveOrUpdatePermissionGroup(): " + e.getMessage());
        }
    }

    /**
     * This method for delete permission group
     *
     * @param perGroupDTO
     * @return retVal
     * @throws AppException
     */
    @Override
    public int deletePermissionGroup(final PermissionGroupDTO perGroupDTO) throws AppException {
        logger.info("Start: deletePermissionGroup() to delete permission group");
        try {
            return transactionTemplate.execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 0;
                    try {
                        //Start: update permission group with statuskey as 3 for delete permission group
                        if (0 < perGroupDTO.getPermissionGroupKey()) {
                            retVal = managePermissionGroupServiceImpl.deletePermissionGroupDetails(perGroupDTO);
                        }
                        //End: update permission group with statuskey as 3 for delete permission group
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:deletePermissionGroup");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : deletePermissionGroup:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "deletePermissionGroup(): " + e.getMessage());
        }
    }
}
