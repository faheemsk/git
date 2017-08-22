/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service.impl;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dao.impl.OrganizationDAOImpl;
import com.optum.oss.dto.OrganizationDTO;
import com.optum.oss.dto.OrganizationSourceDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.WebAppAdminMailHelper;
import com.optum.oss.service.OrganizationService;
import com.optum.oss.transact.dao.impl.OrganizationTransactDAOImpl;
import com.optum.oss.util.CommonUtil;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rpanuganti
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

    private static final Logger logger = Logger.getLogger(OrganizationServiceImpl.class);

    @Autowired
    private OrganizationDAOImpl organizationDao;

    @Autowired
    private OrganizationTransactDAOImpl organizationTransactDao;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private WebAppAdminMailHelper adminMailHelper;

    /**
     * @return List<OrganizationDTO>
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<OrganizationDTO> organizationWorkList() throws AppException {
        return organizationDao.organizationWorkList();
    }

    /**
     *
     * @param organizationDTO
     * @return int
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public int saveOrganization(OrganizationDTO organizationDTO) throws AppException {
        return organizationDao.saveOrganization(organizationDTO);

    }

    /**
     *
     * @param organizationKey
     * @return OrganizationDTO
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public OrganizationDTO getOrganizationDetailByOrgID(long organizationKey) throws AppException {
        OrganizationDTO organizationDTO = organizationDao.getOrganizationDetailByOrgID(organizationKey);
        List<OrganizationSourceDTO> sourceList = organizationDao.getOrganizationSourceInfoByOrgID(organizationKey);
        if (organizationDTO != null) {
            organizationDTO.setOrganizationSourceListObj(sourceList);
        }

        return organizationDTO;
    }

    /**
     *
     * @param organizationDTO
     * @return int
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public int updateOrganization(OrganizationDTO organizationDTO) throws AppException {
        return organizationDao.updateOrganization(organizationDTO);
    }

    /**
     * save Organization Source Information
     *
     * @param flag
     * @param organizationKey
     * @param sourceKey
     * @param sourceClientID
     * @param userID
     * @return int
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public int saveOrganizationAuthoritativeSourceInfo(String flag, long organizationKey, long sourceKey, String sourceClientID, long userID) throws AppException {
        return organizationDao.saveOrganizationAuthoritativeSourceInfo(flag, organizationKey, sourceKey, sourceClientID, userID);
    }

    /**
     * save Authoritative Source
     *
     * @param sourceType
     * @param sourceName
     * @param description
     * @param userID
     * @return int
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public int saveAuthoritativeSource(String sourceType, String sourceName, String description, long userID) throws AppException {
        return organizationDao.saveAuthoritativeSource(sourceType, sourceName, description, userID);
    }

    /**
     * update Organization Details
     *
     * @param organizationDTO
     * @return int
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public int updateOrganizationDetails(OrganizationDTO organizationDTO) throws AppException {
        return organizationTransactDao.updateOrganizationDetails(organizationDTO);
    }

    /**
     * Save Organization Details
     *
     * @param organizationDTO
     * @return int
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public int saveOrganizationDetails(OrganizationDTO organizationDTO, UserProfileDTO userSessionDto) throws AppException {
        int orgID = 0;
        int schemaKey = 0;
        int schemTableStatus = 0;
        int schemaViewStatus = 0;
        int orgSchemaIndiacatorStatus = 0;
        int schemETLTableStatus = 0;
        try {

            orgID = organizationTransactDao.saveOrganizationDeatils(organizationDTO);

            if (orgID > 0 && organizationDTO.getOrganizationType() != null && organizationDTO.getOrganizationType().equalsIgnoreCase(ApplicationConstants.DB_ORGANIZATIION_TYPE_CLIENT)) {
                //organization creation sucess log
                organizationDTO.setOrganizationKey(orgID);
                organizationDTO.setSchemaStatus(ApplicationConstants.DB_CONSTANT_ORGANIZATION_CREATED);
                organizationDao.createOrgSchemaLog(organizationDTO);

                //schema creation
                String schemaName = commonUtil.prepareSchemaNameFormat(organizationDTO.getOrganizationName(), orgID);
                organizationDTO.setSchemaName(schemaName);
                schemaKey = organizationTransactDao.createOrgSchema(organizationDTO);

                if (schemaKey > 0) {
                    //schema creation sucess log
                    organizationDTO.setSchemaKey(schemaKey);
                    organizationDTO.setSchemaStatus(ApplicationConstants.DB_CONSTANT_ORGANIZATION_SCHEMA_CREATED);
                    organizationDao.createOrgSchemaLog(organizationDTO);

                    schemTableStatus = organizationTransactDao.createOrgSchemaTables(organizationDTO);
                    if (schemTableStatus > 0) {
                        //schema table creation sucess log
                        organizationDTO.setSchemaStatus(ApplicationConstants.DB_CONSTANT_ORGANIZATION_SCHEMA_TABLES_CREATED);
                        organizationDao.createOrgSchemaLog(organizationDTO);

                        schemETLTableStatus = organizationTransactDao.createOrgSchemaETLTables(organizationDTO);
                        if (schemETLTableStatus > 0) {
                            //schema ETL table creation sucess log
                            organizationDTO.setSchemaStatus(ApplicationConstants.DB_CONSTANT_ORGANIZATION_SCHEMA_ETL_TABLES_CREATED);
                            organizationDao.createOrgSchemaLog(organizationDTO);

                            schemaViewStatus = organizationTransactDao.createOrgSchemaView(organizationDTO);
                            if (schemaViewStatus > 0) {
                                //schema table view creation sucess log
                                organizationDTO.setSchemaStatus(ApplicationConstants.DB_CONSTANT_ORGANIZATION_SCHEMA_VIEW_CREATED);
                                organizationDao.createOrgSchemaLog(organizationDTO);

                                //schema sucess indicator updation
                                organizationDTO.setSchemaIndicator(ApplicationConstants.DB_CONSTANT_ORGANIZATION_SCHEMA_INDICATOR_SUCESS);
                                orgSchemaIndiacatorStatus = organizationDao.updateOrgSchemaIndicator(organizationDTO);
                            }
                        }
                    }
                }

                if (orgSchemaIndiacatorStatus > 0) {
                    adminMailHelper.mail_ClientOrgOnBoarded(userSessionDto, organizationDTO);
                } else {
                    adminMailHelper.mail_ClientOrgOnNotBoarded(userSessionDto, organizationDTO);
                }
            }
        } catch (AppException e) {
            logger.error("Exception occurred : saveOrganizationDetails Service Impl:" + e.getMessage());
            organizationDTO.setSchemaLog(e + "");
            organizationDao.createOrgSchemaLog(organizationDTO);

        }

        return orgID;
    }

    /**
     * Checking Duplicate Organization
     *
     * @param organization
     * @return int
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public int validateOrganization(final String organization) throws AppException {
        return organizationDao.validateOrganization(organization);
    }

    /**
     * Checking Duplicate Authoritative Source
     *
     * @param authoritativeSource
     * @return int
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public int validateAuthoritativeSource(String authoritativeSource) throws AppException {
        return organizationDao.validateAuthoritativeSource(authoritativeSource);
    }

    /**
     * fetching Parent Organizations List
     *
     * @return List<OrganizationDTO>
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<OrganizationDTO> parentOrganizationList() throws AppException {
        List<OrganizationDTO> organizationList = organizationDao.parentOrganizationList();
        /*Sorting ArrayList<OrganizationDTO>  based on organization Name using Comparator*/
        Collections.sort(organizationList, new Comparator<OrganizationDTO>() {
            public int compare(OrganizationDTO org1, OrganizationDTO org2) {
                String orgName1 = org1.getOrganizationName();
                String orgName2 = org2.getOrganizationName();
                return orgName1.compareTo(orgName2);

            }
        });
        return organizationList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public int createOrgSchema(OrganizationDTO organizationDTO) throws AppException {
        return organizationDao.createOrgSchema(organizationDTO);
    }

    @Override
    public int onBoardingProcess(OrganizationDTO organizationDTO, UserProfileDTO userSessionDto) throws AppException {
        try {
            int orgSchemaIndiacatorStatus = 0;
            String schemaStatus = organizationDao.getOrgSchemaStatusByOrgID(organizationDTO.getOrganizationKey());
            if (schemaStatus != null && !schemaStatus.equals("")) {
                switch (schemaStatus) {

                    case ApplicationConstants.DB_CONSTANT_ORGANIZATION_SCHEMA_NOT_CREATED:

                        //schema creation
                        String schemaName = commonUtil.prepareSchemaNameFormat(organizationDTO.getOrganizationName(), organizationDTO.getOrganizationKey());
                        organizationDTO.setSchemaName(schemaName);
                        organizationDTO.setSchemaStatus(ApplicationConstants.DB_CONSTANT_ORGANIZATION_CREATED);
                        int schemaKey = organizationTransactDao.createOrgSchema(organizationDTO);

                        if (schemaKey > 0) {
                            //schema creation sucess log
                            organizationDTO.setSchemaKey(schemaKey);
                            organizationDTO.setSchemaStatus(ApplicationConstants.DB_CONSTANT_ORGANIZATION_SCHEMA_CREATED);
                            organizationDao.createOrgSchemaLog(organizationDTO);

                            //case ApplicationConstants.DB_CONSTANT_ORGANIZATION_SCHEMA_VIEW_CREATED:
                        } else {
                            break;
                        }

                    case ApplicationConstants.DB_CONSTANT_ORGANIZATION_SCHEMA_TABLES_NOT_CREATED:
                        organizationDTO.setSchemaStatus(ApplicationConstants.DB_CONSTANT_ORGANIZATION_SCHEMA_CREATED);
                        int schemTableStatus = organizationTransactDao.createOrgSchemaTables(organizationDTO);
                        if (schemTableStatus > 0) {
                            //schema table creation sucess log
                            organizationDTO.setSchemaStatus(ApplicationConstants.DB_CONSTANT_ORGANIZATION_SCHEMA_TABLES_CREATED);
                            organizationDao.createOrgSchemaLog(organizationDTO);
                        } else {
                            break;
                        }
                    case ApplicationConstants.DB_CONSTANT_ORGANIZATION_SCHEMA_ETL_TABLES_NOT_CREATED:
                        organizationDTO.setSchemaStatus(ApplicationConstants.DB_CONSTANT_ORGANIZATION_SCHEMA_TABLES_CREATED);
                        int schemETLTableStatus = organizationTransactDao.createOrgSchemaETLTables(organizationDTO);
                        if (schemETLTableStatus > 0) {
                            //schema table creation sucess log
                            organizationDTO.setSchemaStatus(ApplicationConstants.DB_CONSTANT_ORGANIZATION_SCHEMA_ETL_TABLES_CREATED);
                            organizationDao.createOrgSchemaLog(organizationDTO);
                        } else {
                            break;
                        }
                    case ApplicationConstants.DB_CONSTANT_ORGANIZATION_SCHEMA_VIEW_NOT_CREATED:
                        organizationDTO.setSchemaStatus(ApplicationConstants.DB_CONSTANT_ORGANIZATION_SCHEMA_ETL_TABLES_CREATED);
                        int schemaViewStatus = organizationTransactDao.createOrgSchemaView(organizationDTO);
                        if (schemaViewStatus > 0) {
                            //schema table view creation sucess log
                            organizationDTO.setSchemaStatus(ApplicationConstants.DB_CONSTANT_ORGANIZATION_SCHEMA_VIEW_CREATED);
                            organizationDao.createOrgSchemaLog(organizationDTO);
                        } else {
                            break;
                        }
                    case ApplicationConstants.DB_CONSTANT_ORGANIZATION_SCHEMA_INDICATOR_SUCESS_NOT_UPDATED:
                        //schema sucess indicator updation
                        organizationDTO.setSchemaIndicator(ApplicationConstants.DB_CONSTANT_ORGANIZATION_SCHEMA_INDICATOR_SUCESS);
                        orgSchemaIndiacatorStatus = organizationDao.updateOrgSchemaIndicator(organizationDTO);
                }
                if (orgSchemaIndiacatorStatus > 0) {
                    adminMailHelper.mail_ClientOrgOnBoarded(userSessionDto, organizationDTO);
                } else {
                    adminMailHelper.mail_ClientOrgOnNotBoarded(userSessionDto, organizationDTO);
                }
            }

        } catch (AppException e) {
            logger.error("Exception occurred : saveOrganizationDetails Service Impl:" + e.getMessage());
            organizationDTO.setSchemaLog(e + "");
            organizationDao.createOrgSchemaLog(organizationDTO);

        }
        return 1;
    }

}
