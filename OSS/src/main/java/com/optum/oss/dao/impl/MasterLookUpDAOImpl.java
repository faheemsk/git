/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao.impl;

import com.optum.oss.constants.PermissionConstants;
import com.optum.oss.dao.MasterLookUpDAO;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.exception.AppException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author hpasupuleti
 */
public class MasterLookUpDAOImpl implements MasterLookUpDAO {

    private static final Logger logger = Logger.getLogger(MasterLookUpDAOImpl.class);

    private JdbcTemplate jdbcTemplate;
    /*
     Start : Setter getters for private variables
     */

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    /*
     End : Setter getters for private variables
     */

    /**
     * Fetches the data from MSTR_LKP Table based on Entity Type Name.
     *
     * @param entityType
     * @return List<MasterLookUpDTO>
     * @throws AppException
     */
    @Override
    public List<MasterLookUpDTO> fetchMasterLookUpEntitiesByEntityType(final String entityType)
            throws AppException {
        List<MasterLookUpDTO> masterLookUpList = new ArrayList<>();
        try {
            String procName = "{CALL LIST_MasterLookupByEntityType(?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName,
                    new Object[]{entityType});

            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    MasterLookUpDTO lookUpDTO = new MasterLookUpDTO();
                    // DO NOT INCLUDE ADMINISTRATOR USER TYPE
                    if (null != resultMap.get("LKP_ENTY_TYP_NM") && !StringUtils.endsWithIgnoreCase(
                            PermissionConstants.USER_TYPE_ADMINISTRATOR, (String) resultMap.get("LKP_ENTY_NM"))) {
                        lookUpDTO.setMasterLookupKey((Integer) (resultMap.get("MSTR_LKP_KEY")));
                        lookUpDTO.setLookUpEntityTypeName((String) (resultMap.get("LKP_ENTY_TYP_NM")));
                        lookUpDTO.setLookUpEntityName((String) (resultMap.get("LKP_ENTY_NM")));
                        lookUpDTO.setLookUpEntityDescription((String) (resultMap.get("LKP_ENTY_DESC")));

                        masterLookUpList.add(lookUpDTO);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : fetchMasterLookUpEntitiesByEntityType:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "fetchMasterLookUpEntitiesByEntityType() :: " + e.getMessage());
        }
        return masterLookUpList;
    }

    /**
     * fetch Countries
     *
     * @param @return List
     * @throws AppException
     */
    @Override
    public List fetchCountries() throws AppException {
        List countryList = new ArrayList();
        String procName = "{CALL List_Country()}";
        try {
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName,
                    new Object[]{});
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    countryList.add((String) resultMap.get("CNTRY_NM"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : fetchCountries():" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "fetchCountries() :: " + e.getMessage());
        }
        return countryList;
    }

    /**
     * fetch States by Country
     *
     * @param country
     * @return List<String>
     * @throws AppException
     */
    @Override
    public List<String> fetchStatesByCountry(String country) throws AppException {
        List<String> countryList = new ArrayList<String>();
        String procName = "{CALL List_StateByCountry(?)}";
        try {
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName,
                    new Object[]{country});
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    countryList.add((String) resultMap.get("ST_NM"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : fetchStatesByCountry():" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "fetchStatesByCountry() :: " + e.getMessage());
        }
        return countryList;
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
    public int fetchEntityIdByEntityName(String entityTye, String entityName) throws AppException {
        logger.info("Start: MasterLookUpDAOImpl : fetchEntityIdByEntityName");
        int entityId = 0;
        try {
            String entityIdProc = "{CALL GetEntityIDByName(?,?)}";
            List<Map<String, Object>> entityIdMap = jdbcTemplate.queryForList(entityIdProc, 
                    new Object[]{ entityTye, entityName });

            for (Map<String, Object> entityIdObj : entityIdMap) {
                entityId = (Integer) entityIdObj.get("MSTR_LKP_KEY");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : fetchEntityIdByEntityName : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "fetchEntityIdByEntityName() :: " + e.getMessage());
        }
        logger.info("End: MasterLookUpDAOImpl : fetchEntityIdByEntityName");
        return entityId;
    }

}
