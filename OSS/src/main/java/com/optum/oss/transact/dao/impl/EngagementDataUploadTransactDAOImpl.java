/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.transact.dao.impl;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.EngagementDataUploadMailHelper;
import com.optum.oss.service.impl.EngagementDataUploadServiceImpl;
import com.optum.oss.transact.dao.EngagementDataUploadTransactDAO;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author akeshavulu
 *
 */
public class EngagementDataUploadTransactDAOImpl implements EngagementDataUploadTransactDAO {

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
    private EngagementDataUploadServiceImpl EngagementDataUploadService;

    @Autowired
    private EngagementDataUploadMailHelper engmentDataUploadMailHelper;

    /*
     End : Autowiring the required Class instances
     */
    @Override
    public int saveEngagementDataUploadDetails(final ClientEngagementDTO clientEngagementDTO, final UserProfileDTO userDto) throws AppException {
        try {
            return transactionTemplate.execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 1;
                    try {

                        retVal = EngagementDataUploadService.saveEngagementDataUploadDetails(clientEngagementDTO);
//                        //mail to Engagement/Partner Lead when file is uploaded for a service
//                            List<UserProfileDTO> userDetailsList = EngagementDataUploadService.engagementLeadDetailsByCode(clientEngagementDTO.getEngagementCode());
//                            if (null != userDetailsList) {
//                                for (UserProfileDTO userProfileDto : userDetailsList) {
//                                    if (userProfileDto.getUserID().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_INTERNAL)) {
//                                        engmentDataUploadMailHelper.mailOnEngagementFileUpload(userProfileDto, clientEngagementDTO, userDto);
//                                    }
//                                    if (userProfileDto.getUserID().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_PARTNER)) {
//                                        engmentDataUploadMailHelper.mailOnEngagementFileUpload(userProfileDto, clientEngagementDTO, userDto);
//                                    }
//                                }
//                            }

                        if (retVal < 0) {
                            transactionStatus.setRollbackOnly();
                            retVal = -1;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:saveEngagementDataUploadDetails");
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


}
