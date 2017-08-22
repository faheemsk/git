/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.transact.dao.impl;

import com.optum.oss.constants.ApplicationConstants;
import static com.optum.oss.constants.ApplicationConstants.DB_INDICATOR_INSERT;
import com.optum.oss.dao.impl.OrganizationDAOImpl;
import com.optum.oss.dto.OrganizationDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.service.impl.OrganizationServiceImpl;
import com.optum.oss.transact.dao.OrganizationTransactDAO;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author rpanuganti
 */
public class OrganizationTransactDAOImpl implements OrganizationTransactDAO {

    private static final Logger logger = Logger.getLogger(OrganizationTransactDAOImpl.class);

    private JdbcTemplate jdbcTemplate;
    private JdbcTemplate jdbcTemplateSchema;
    private TransactionTemplate transactionTemplate;
    private TransactionTemplate transactionTemplateSchema;

    /*
     Start : Setter getters for private variables
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setJdbcTemplateSchema(JdbcTemplate jdbcTemplateSchema) {
        this.jdbcTemplateSchema = jdbcTemplateSchema;
    }

    public TransactionTemplate getTransactionTemplate() {
        return transactionTemplate;
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    public TransactionTemplate getTransactionTemplateSchema() {
        return transactionTemplateSchema;
    }

    public void setTransactionTemplateSchema(TransactionTemplate transactionTemplateSchema) {
        this.transactionTemplateSchema = transactionTemplateSchema;
    }
    
    /*
     End : Setter getters for private variables
     */
    /*
     Start : Autowiring the required Class instances
     */
    @Autowired
    private OrganizationServiceImpl organizationService;
    
        @Autowired
    private OrganizationDAOImpl organizationDao;

    /*
     End : Autowiring the required Class instances
     */
    /**
     * Save Organization Details
     *
     * @param organizationDTO
     * @return int
     * @throws AppException
     */
    @Override
    public int saveOrganizationDeatils(final OrganizationDTO organizationDTO) throws AppException {
        try {
            return transactionTemplate.execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 1;
                       int orgKey=0;
                    try {
                     
                        /* saving  orgdetails  */
                        orgKey = organizationService.saveOrganization(organizationDTO);
                        if (orgKey > 0) {
                            int count = 0;
                            long sourceKey[] = organizationDTO.getSourceKeyArray();
                            String sourceID[] = organizationDTO.getSourceClientIDArray();
                            String otherSourceText[] = organizationDTO.getSourceTextArray();

                            for (int i = 0; i < sourceKey.length; i++) {
                                long skey = sourceKey[i];
                                String sid = "";
                                if (sourceID.length != 0) {
                                    sid = sourceID[i];
                                }
                                /* saving Other Source Text in the Master table*/
                                if (skey == -1) {
                                    if (count < otherSourceText.length) {
                                        String txt = otherSourceText[count];
                                        /* saving  new Source  */
                                        int retSourceKey = organizationService.saveAuthoritativeSource(ApplicationConstants.DB_CONSTANT_SOURCE, txt, "", organizationDTO.getCreatedByUserID());
                                        if (retSourceKey > 0) {
                                            /* saving  organization authoratative Source Info  */
                                            retVal = organizationService.saveOrganizationAuthoritativeSourceInfo(ApplicationConstants.DB_INDICATOR_INSERT, orgKey, retSourceKey, sid, organizationDTO.getCreatedByUserID());
                                        } else {
                                            transactionStatus.setRollbackOnly();
                                            retVal = -1;
                                        }
                                        count++;
                                    }

                                } else {
                                    /* saving  organization authoratative Source Info  */
                                    retVal = organizationService.saveOrganizationAuthoritativeSourceInfo(ApplicationConstants.DB_INDICATOR_INSERT, orgKey, skey, sid, organizationDTO.getCreatedByUserID());
                                }

                            }
                        } else {
                            transactionStatus.setRollbackOnly();
                            retVal = -1;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:saveOrganizationDeatils");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return orgKey;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : saveOrganizationDeatils:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "saveOrganizationDeatils() :: " + e.getMessage());
        }
    }

    /**
     * Update Organization Details
     *
     * @param organizationDTO
     * @return int
     * @throws AppException
     */
    @Override
    public int updateOrganizationDetails(final OrganizationDTO organizationDTO) throws AppException {
        try {
            return transactionTemplate.execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 1;
                    try {
                        long orgKey = organizationDTO.getOrganizationKey();
                        /* updating  orgdetails  */

                        retVal = organizationService.updateOrganization(organizationDTO);
                        if (retVal > 0) {

                            /* delete the Organization Source Infromation from the ORG_REL_ID Table */
                            retVal = organizationService.saveOrganizationAuthoritativeSourceInfo(ApplicationConstants.DB_INDICATOR_DELETE, orgKey, 1, "", organizationDTO.getCreatedByUserID());

                            if (retVal > 0) {
                                int count = 0;
                                long sourceKey[] = organizationDTO.getSourceKeyArray();
                                String sourceID[] = organizationDTO.getSourceClientIDArray();
                                String otherSourceText[] = organizationDTO.getSourceTextArray();

                                for (int i = 0; i < sourceKey.length; i++) {
                                    long skey = sourceKey[i];
                                    String sid = "";
                                    if (sourceID.length != 0) {
                                        sid = sourceID[i];
                                    }
                                    /* saving Other Source Text in the Master table*/
                                    if (skey == -1) {
                                        if (count < otherSourceText.length) {
                                            String txt = otherSourceText[count];
                                            /* saving  new Source  */
                                            int retSourceKey = organizationService.saveAuthoritativeSource(ApplicationConstants.DB_CONSTANT_SOURCE, txt, "", organizationDTO.getCreatedByUserID());
                                            if (retSourceKey > 0) {
                                                /* saving  organization authoratative Source Info  */
                                                retVal = organizationService.saveOrganizationAuthoritativeSourceInfo(ApplicationConstants.DB_INDICATOR_INSERT, orgKey, retSourceKey, sid, organizationDTO.getCreatedByUserID());
                                            } else {
                                                transactionStatus.setRollbackOnly();
                                                retVal = -1;
                                            }
                                            count++;
                                        }
                                    } else {
                                        /* saving  organization authoratative Source Info  */
                                        retVal = organizationService.saveOrganizationAuthoritativeSourceInfo(ApplicationConstants.DB_INDICATOR_INSERT, orgKey, skey, sid, organizationDTO.getCreatedByUserID());
                                    }

                                }

                            } else {
                                transactionStatus.setRollbackOnly();
                                retVal = -1;
                            }
                        } else {
                            transactionStatus.setRollbackOnly();
                            retVal = -1;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:updateOrganizationDetails");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : updateOrganizationDetails:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "updateOrganizationDetails() :: " + e.getMessage());
        }
    }

    @Override
    public int createOrgSchema(final OrganizationDTO organizationDTO) throws AppException {
        try {
            return transactionTemplateSchema.execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 0;
                    try {
                        retVal = organizationDao.createOrgSchema(organizationDTO);
                        if (retVal > 0) {
                        } else {
                            transactionStatus.setRollbackOnly();
                            retVal = -1;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:createOrgSchema");
                          try {
                            transactionStatus.setRollbackOnly();
                            organizationDTO.setSchemaLog(e + "");
                            organizationDTO.setSchemaStatus(ApplicationConstants.DB_CONSTANT_ORGANIZATION_SCHEMA_NOT_CREATED);
                            organizationDao.createOrgSchemaLog(organizationDTO);
                        } catch (AppException ex) {
                            e.printStackTrace();
                            logger.error("Exception occurred : createOrgSchema:" + e.getMessage());
                        }
                        
                        return -1;
                    }
                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : createOrgSchema:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "createOrgSchema() :: " + e.getMessage());
        }
    }
    
    
    public int createOrgSchemaTables(final OrganizationDTO organizationDTO) throws AppException {
        try {
            return transactionTemplateSchema.execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 0;
                    try {
                        retVal = organizationDao.createOrgSchemaTables(organizationDTO);
                        if (retVal > 0) {
                            
                        } else {
                            transactionStatus.setRollbackOnly();
                            retVal = -1;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:createOrgSchemaTables");
                         try {
                            transactionStatus.setRollbackOnly();
                            organizationDTO.setSchemaLog(e + "");
                            organizationDTO.setSchemaStatus(ApplicationConstants.DB_CONSTANT_ORGANIZATION_SCHEMA_TABLES_NOT_CREATED);
                            organizationDao.createOrgSchemaLog(organizationDTO);
                        } catch (AppException ex) {
                            e.printStackTrace();
                            logger.error("Exception occurred : createOrgSchemaTables:" + e.getMessage());
                        }
                        return -1;
                    }
                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : createOrgSchemaTables:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "createOrgSchemaTables() :: " + e.getMessage());
        }
    }
    
    public int createOrgSchemaETLTables(final OrganizationDTO organizationDTO) throws AppException {
        try {
            return transactionTemplateSchema.execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 0;
                    try {
                        retVal = organizationDao.createOrgSchemaETLTables(organizationDTO);
                        if (retVal > 0) {
                            
                        } else {
                            transactionStatus.setRollbackOnly();
                            retVal = -1;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:createOrgSchemaETLTables");
                         try {
                            transactionStatus.setRollbackOnly();
                            organizationDTO.setSchemaLog(e + "");
                            organizationDTO.setSchemaStatus(ApplicationConstants.DB_CONSTANT_ORGANIZATION_SCHEMA_ETL_TABLES_NOT_CREATED);
                            organizationDao.createOrgSchemaLog(organizationDTO);
                        } catch (AppException ex) {
                            e.printStackTrace();
                            logger.error("Exception occurred : createOrgSchemaETLTables:" + e.getMessage());
                        }
                        return -1;
                    }
                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : createOrgSchemaTables:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "createOrgSchemaTables() :: " + e.getMessage());
        }
    }
    
     public int createOrgSchemaView(final OrganizationDTO organizationDTO) throws AppException {
        try {
            return transactionTemplateSchema.execute(new TransactionCallback<Integer>()  {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 0;
                    try {
                        retVal = organizationDao.createOrgSchemaView(organizationDTO);
                        if (retVal > 0) {

                        } else {
                            transactionStatus.setRollbackOnly();
                            retVal = -1;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception occurred : createOrgSchemaView:" + e.getMessage());
                        try {
                            transactionStatus.setRollbackOnly();
                            organizationDTO.setSchemaLog(e + "");  
                            organizationDTO.setSchemaStatus(ApplicationConstants.DB_CONSTANT_ORGANIZATION_SCHEMA_VIEW_NOT_CREATED);
                            organizationDao.createOrgSchemaLog(organizationDTO);
                        } catch (AppException ex) {
                            e.printStackTrace();
                            logger.error("Exception occurred : createOrgSchemaView:" + e.getMessage());
                        }
                        return -1;
                    }
                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : createOrgSchemaView:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "createOrgSchemaView() :: " + e.getMessage());
        }
    }

}
