/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.transact.dao.impl;

import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.dto.EngagementPartnerUserDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.ClientEngagementMailHelper;
import com.optum.oss.service.impl.ManageEngagementsServiceImpl;
import com.optum.oss.service.impl.UserManagementServiceImpl;
import com.optum.oss.transact.dao.ManageEngagementsTransactDAO;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author sbhagavatula
 */
public class ManageEngagementsTransactDAOImpl implements ManageEngagementsTransactDAO{
    
    private static final Logger logger = Logger.getLogger(ManageEngagementsTransactDAOImpl.class);

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
    private ManageEngagementsServiceImpl manageEngagementsServiceImpl;
    @Autowired
    private UserManagementServiceImpl userManagementServiceImpl;
    @Autowired
    private ClientEngagementMailHelper clientEngagementMailHelper;
    /**
     *
     * @param clientEngagementDTO
     * @param sessionDTO
     * @return
     * @throws AppException
     */
    @Override
    public int saveEngagement(final ClientEngagementDTO clientEngagementDTO, final UserProfileDTO sessionDTO) throws AppException {
        logger.info("Start: ManageEngagementsTransactDAOImpl : saveEngagement");
        try {
            return transactionTemplate.execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int engagementId = 0;
                    int sourceStat = 0;
                    int userStat = 0;
                    int serviceStat = 0;
                    int engagementsCount = 0;
                    try {
                        
                        //SAVE ENGAGEMENT DETAILS
                        engagementId = manageEngagementsServiceImpl.saveEngagementInfo(clientEngagementDTO, sessionDTO);
                        
                        //CONDITION CHECK FOR INSERT ENGAGEMENT DETAILS STATUS: SUCCESS IS engagementId > 0 AND FAILURE IF engagementId <= 0
                        if (engagementId > 0) {
                            clientEngagementDTO.setClientEngagementKey(engagementId);
                            //INSERT SOURCE SYSTEM AND SOURCE PROJECT DATA
                            sourceStat = manageEngagementsServiceImpl.saveEngagementSourceInfo(clientEngagementDTO, sessionDTO);
                            
                            //INSERT ASSIGNED USERS
                            //userStat = manageEngagementsServiceImpl.saveEngagementUsers(clientEngagementDTO, sessionDTO);
                            
                            //INSERT ENGAGEMENT SERVICES
                            serviceStat = manageEngagementsServiceImpl.saveEngagementServices(clientEngagementDTO, sessionDTO);
                            if (sourceStat <= 0 || serviceStat <= 0) {
                                transactionStatus.setRollbackOnly();
                                return -1;
                            } else {
                                /*
                                * BEGIN : MAIL SENT TO ALL USERS
                                */
//                                long internalUserId = clientEngagementDTO.getEngagementPartnerUserLi().get(1).getPartnerUserID();
//                                long clientUserId = clientEngagementDTO.getEngagementPartnerUserLi().get(0).getPartnerUserID();
//                                //Fetch user details of Internal user
//                                UserProfileDTO internalUserDTO = userManagementServiceImpl.fetchUserInfoByUserID(internalUserId);
//                                //Fetch user details of Client user
//                                UserProfileDTO clientUserDTO = userManagementServiceImpl.fetchUserInfoByUserID(clientUserId);
//                                //Fetch engagement details by engagementcode used to send emailcontent
//                                ClientEngagementDTO engagementObj = manageEngagementsServiceImpl.viewEngagementByEngmtKey(clientEngagementDTO.getEngagementCode());
//                                
//                                //Mail sent to internl user
//                                clientEngagementMailHelper.mailOnEngagementSave(internalUserDTO, engagementObj, sessionDTO);
//                                
//                                //Mail sent to client user
//                                clientEngagementMailHelper.mailToClientLead(clientUserDTO, engagementObj, sessionDTO);
//                                
//                                //Fetch Partner Users
//                                List<EngagementPartnerUserDTO> partnerUsers = clientEngagementDTO.getEngagementPartnerUserLi();
//                                int i = 0;
//                                for(EngagementPartnerUserDTO partnerDTO : partnerUsers){
//                                    if(i>1){
//                                        if(partnerDTO.getPartnerUserID() !=0 && !("").equalsIgnoreCase(partnerDTO.getSecurityServicesSelected())){
//                                            //Fetch user details of Partner user
//                                            UserProfileDTO partnerUserDTO = userManagementServiceImpl.fetchUserInfoByUserID(partnerDTO.getPartnerUserID());
//                                            //Mail sent to Partner user
//                                            clientEngagementMailHelper.mailToPartnerLead(partnerUserDTO, engagementObj, sessionDTO);
//                                        }
//                                    }
//                                    i++;
//                                }
                               /*
                                * END : MAIL SENT TO ALL USERS
                                */
                            }
                        } else {
                            //IF ENGAGEMENT DETAILS INSERTION FAILS, ROLL BACK ALL THE TRANSACTIONS 
                            transactionStatus.setRollbackOnly();
                            return -1;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:saveEngagement");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return engagementId;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : saveEngagement:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "saveEngagement() :: " + e.getMessage());
        }
    }

    /**
     *
     * @param clientEngagementDTO
     * @param sessionDTO
     * @param servicesToAdd
     * @param servicesToDelete
     * @param servicesToUpdate
     * @return
     * @throws AppException
     */
    @Override
    public int updateEngagement(final ClientEngagementDTO clientEngagementDTO, final UserProfileDTO sessionDTO,
            final List<String> servicesToAdd,  final List<String> servicesToDelete,
            final List<String> servicesToUpdate) throws AppException {
        logger.info("Start: ManageEngagementsTransactDAOImpl : updateEngagement");
        try {
            return transactionTemplate.execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int engagementId = 0;
                    int sourceStat = 0;
                    int userStat = 0;
                    int serviceStat = 0;
                    try {
                        //UPDATE ENGAGEMENT DETAILS
                        engagementId = manageEngagementsServiceImpl.updateEngagementInfo(clientEngagementDTO, sessionDTO);
                        
                        //CONDITION CHECK FOR INSERT ENGAGEMENT DETAILS STATUS: SUCCESS IS engagementId > 0 AND FAILURE IF engagementId <= 0
                        if (engagementId > 0) {
                            manageEngagementsServiceImpl.deleteEngagementSourceInfo(clientEngagementDTO.getEngagementCode(), sessionDTO);
                            //INSERT SOURCE SYSTEM AND SOURCE PROJECT DATA
                            sourceStat = manageEngagementsServiceImpl.saveEngagementSourceInfo(clientEngagementDTO, sessionDTO);
                            
                            //manageEngagementsServiceImpl.deleteEngagementUsers(clientEngagementDTO, clientEngagementDTO.getEngagementCode(), sessionDTO);
                            //INSERT ASSIGNED USERS
                            //userStat = manageEngagementsServiceImpl.saveEngagementUsers(clientEngagementDTO, sessionDTO);
                            
                            manageEngagementsServiceImpl.deleteEngagementServices(clientEngagementDTO.getEngagementCode(), servicesToDelete, "I");
                            //INSERT ENGAGEMENT SERVICES
                            serviceStat = manageEngagementsServiceImpl.saveNewEngagementServices(clientEngagementDTO, sessionDTO, servicesToAdd);
                            
                            //Update engagement services
                            manageEngagementsServiceImpl.updateEngagementServices(clientEngagementDTO, sessionDTO, servicesToUpdate);
                            
                            if (sourceStat <= 0) {
                                transactionStatus.setRollbackOnly();
                                return -1;
                            } else {
                                /*
                                * BEGIN : MAIL SENT TO ALL USERS
                                */
//                                long internalUserId = clientEngagementDTO.getEngagementPartnerUserLi().get(1).getPartnerUserID();
//                                long clientUserId = clientEngagementDTO.getEngagementPartnerUserLi().get(0).getPartnerUserID();
//                                //Fetch user details of Internal user
//                                UserProfileDTO internalUserDTO = userManagementServiceImpl.fetchUserInfoByUserID(internalUserId);
//                                //Fetch user details of Client user
//                                UserProfileDTO clientUserDTO = userManagementServiceImpl.fetchUserInfoByUserID(clientUserId);
//                                
//                                //Fetch engagement details by engagementcode used to send emailcontent
//                                ClientEngagementDTO engagementObj = manageEngagementsServiceImpl.viewEngagementByEngmtKey(clientEngagementDTO.getEngagementCode());
//                                //Mail sent to internl user
//                                clientEngagementMailHelper.mailOnEngagementUpdate(internalUserDTO, engagementObj, sessionDTO);
//                                
//                                //MAIL SENT TO CLIENT USER
//                                clientEngagementMailHelper.mailToClientLead(clientUserDTO, engagementObj, sessionDTO);
//                               
//                                //FETCH PARTNER USERS
//                                List<EngagementPartnerUserDTO> partnerUsers = clientEngagementDTO.getEngagementPartnerUserLi();
//                                String partnerUserIds = "";
//                                String delimiter = "";
//                                int i = 0;
//                                for(EngagementPartnerUserDTO partnerDTO : partnerUsers){
//                                    if(i>1){
//                                        if(partnerDTO.getPartnerUserID() !=0 && !("").equalsIgnoreCase(partnerDTO.getSecurityServicesSelected())){
//                                            //Fetch user details of Partner user
//                                            UserProfileDTO partnerUserDTO = userManagementServiceImpl.fetchUserInfoByUserID(partnerDTO.getPartnerUserID());
//                                            //Mail sent to Partner user
//                                            clientEngagementMailHelper.mailToPartnerLead(partnerUserDTO, engagementObj, sessionDTO);
//                                        }
//                                        partnerUserIds = partnerUserIds.concat(delimiter);
//                                        partnerUserIds = partnerUserIds.concat(partnerDTO.getPartnerUserID()+"");
//                                        delimiter = ",";
//                                    }
//                                    i++;
//                                }
//                                
//                                manageEngagementsServiceImpl.deleteEngagementPartnerUsers(clientEngagementDTO.getEngagementCode(), partnerUserIds);
                                /*
                                * END : MAIL SENT TO ALL USERS
                                */
                            }
                        } else {
                            //IF ENGAGEMENT DETAILS UPDATION FAILS, ROLL BACK ALL THE TRANSACTIONS 
                            transactionStatus.setRollbackOnly();
                            return -1;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:updateEngagement");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return engagementId;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : updateEngagement:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "updateEngagement() :: " + e.getMessage());
        }
    }
    
}
