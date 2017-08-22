/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service.impl;

import com.optum.oss.dao.impl.MasterLookUpDAOImpl;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.service.MasterLookUpService;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author hpasupuleti
 */
@Service
public class MasterLookUpServiceImpl implements MasterLookUpService {

    private static final Logger logger = Logger.getLogger(MasterLookUpServiceImpl.class);

    /*
     Start : Autowiring the required Class instances
     */
    @Autowired
    private MasterLookUpDAOImpl masterDAO;

    /*
     End : Autowiring the required Class instances
     */
    /**
     * Fetches the data from MSTR_LKP Table based on Entity Type Name.
     *
     * @param entityType
     * @return List<MasterLookUpDTO>
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<MasterLookUpDTO> fetchMasterLookUpEntitiesByEntityType(final String entityType)
            throws AppException {
        return masterDAO.fetchMasterLookUpEntitiesByEntityType(entityType);
    }

    /**
     * @param country
     * @return List
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List fetchCountries() throws AppException {
        return masterDAO.fetchCountries();
    }

    /**
     * fetch States by Country
     *
     * @param country
     * @return List<String>
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<String> fetchStatesByCountry(String country) throws AppException {
        return masterDAO.fetchStatesByCountry(country);
    }

    /**
     * Used to fetch Entity ID by Entity Type and Entity Name
     * 
     * @param entityTye
     * @param entityName
     * @return
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public int fetchEntityIdByEntityName(String entityTye, String entityName) throws AppException {
        return masterDAO.fetchEntityIdByEntityName(entityTye, entityName);
    }

}
