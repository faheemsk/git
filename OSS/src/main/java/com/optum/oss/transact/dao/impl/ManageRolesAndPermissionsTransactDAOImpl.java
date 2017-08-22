/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.transact.dao.impl;

import com.optum.oss.dao.impl.ManageRolesAndPermissionsDAOImpl;
import com.optum.oss.dto.RoleDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.service.impl.ManageRolesAndPermissionsServiceImpl;
import com.optum.oss.transact.dao.ManageRolesAndPermissionsTransactDAO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author akeshavulu
 */
public class ManageRolesAndPermissionsTransactDAOImpl implements ManageRolesAndPermissionsTransactDAO {

    private static final Logger logger = Logger.getLogger(LoginTransactDAOImpl.class);

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
    private ManageRolesAndPermissionsServiceImpl manageRolesAndPermissionsService;

    @Autowired
    private ManageRolesAndPermissionsDAOImpl manageRolesAndPermissionsDAO;
    /*
     End : Autowiring the required Class instances
     */

    @Override
    public int saveAppRolePermissionGrpDetails(final RoleDTO roleDTO) throws AppException {

        try {
            return transactionTemplate.execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 1;
                    try {

                        // loginDao.insertUserSecurityDetails(userSecDtlsObj);
                        retVal = manageRolesAndPermissionsService.saveAppRoleDetails(roleDTO);
                        if (retVal > 0) {
                            retVal = manageRolesAndPermissionsService.savePermissionGrpDetails(roleDTO, retVal);
                        } else {
                            transactionStatus.setRollbackOnly();
                            retVal = -1;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:updateUserLoginDetails");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : saveAppRolePermissionGrpDetails:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "saveAppRolePermissionGrpDetails() :: " + e.getMessage());
        }
    }

    @Override
    public int updateAppRolePermissionGrpDetails(final RoleDTO roleDTO) throws AppException {
        
         try {
            return transactionTemplate.execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 1;
                    try {
                        retVal = manageRolesAndPermissionsService.updateAppRoleDetails(roleDTO);
                        if (retVal > 0) {
                            retVal = manageRolesAndPermissionsService.updatePermissionGrpDetails(roleDTO);
                        } else {
                            transactionStatus.setRollbackOnly();
                            retVal = -1;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:updateUserLoginDetails");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : updateAppRolePermissionGrpDetails:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "updateAppRolePermissionGrpDetails() :: " + e.getMessage());
        }
    }

}
