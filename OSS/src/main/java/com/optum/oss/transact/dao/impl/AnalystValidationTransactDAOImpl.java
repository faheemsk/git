/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.transact.dao.impl;

import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.dto.VulnerabilityDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.service.impl.AnalystValidationServiceImpl;
import com.optum.oss.transact.dao.AnalystValidationTransactDAO;
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
public class AnalystValidationTransactDAOImpl implements AnalystValidationTransactDAO{
    
    private static final Logger logger = Logger.getLogger(AnalystValidationTransactDAOImpl.class);

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
    private AnalystValidationServiceImpl analystValidationService;
    /*
     End : Autowiring the required Class instances
     */

    /**
     *
     * @param saveVulnerability
     * @param sessionDTO
     * @return
     * @throws AppException
     */
    @Override
    public int saveVulnerability(final VulnerabilityDTO saveVulnerability, final UserProfileDTO sessionDTO) throws AppException {
        try{
            return transactionTemplate.execute(new TransactionCallback<Integer>() {

                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int vulKey = 0;
                    try {
                        //INSERT VULNERABILITY DATA
                       vulKey = analystValidationService.saveVulnerabilityInfo(saveVulnerability, sessionDTO);
                       if(vulKey > 0){
                           saveVulnerability.setInstanceIdentifier(vulKey+"");
                           
                           //INSERT COMPLIANCES FOR A VULNERABILITY - THERE MAY OR MAY NOT BE RECORDS TO DELETE - OPTIONAL
                           analystValidationService.saveComplianceInfo(saveVulnerability, sessionDTO);
                       }else{
                           transactionStatus.setRollbackOnly();
                           return -1;
                       }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:saveVulnerability");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return vulKey;
                }
            
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : saveVulnerability:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "saveVulnerability() :: " + e.getMessage());
        }
    }
    
    /**
     *
     * @param saveVulnerability
     * @param sessionDTO
     * @return
     * @throws AppException
     */
    @Override
    public int updateVulnerability(final VulnerabilityDTO saveVulnerability, final UserProfileDTO sessionDTO) throws AppException {
        try{
            return transactionTemplate.execute(new TransactionCallback<Integer>() {

                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int vulKey = 0;
                    try {
                        //UPDATE VULNERABILITY DATA
                       vulKey = analystValidationService.updateVulnerabilityInfo(saveVulnerability, sessionDTO);
                       if(vulKey > 0){
                           //DELETE ALL COMPLIANCES FOR A VULNERABILITY - THERE MAY OR MAY NOT BE RECORDS TO DELETE - OPTIONAL
                           //analystValidationService.deleteComplianceInfo(Long.parseLong(saveVulnerability.getInstanceIdentifier()));
                           
                           //INSERT COMPLIANCES FOR A VULNERABILITY - THERE MAY OR MAY NOT BE RECORDS TO DELETE - OPTIONAL
                           analystValidationService.saveComplianceInfo(saveVulnerability, sessionDTO);
                       }else{
                           transactionStatus.setRollbackOnly();
                           return -1;
                       }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:updateVulnerability");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return vulKey;
                }
            
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : updateVulnerability:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "updateVulnerability() :: " + e.getMessage());
        }
    }
    
}
