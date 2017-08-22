/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao.impl;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dao.OrganizationDAO;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.OrganizationDTO;
import com.optum.oss.dto.OrganizationSourceDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.util.EncryptDecrypt;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author rpanuganti
 */
public class OrganizationDAOImpl implements OrganizationDAO {

    private static final Logger logger = Logger.getLogger(OrganizationDAOImpl.class);

    private JdbcTemplate jdbcTemplate;
    
     private JdbcTemplate jdbcTemplateSchema;

    /*
     Start : Setter getters for private variables
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplateSchema() {
        return jdbcTemplateSchema;
    }

    public void setJdbcTemplateSchema(JdbcTemplate jdbcTemplateSchema) {
        this.jdbcTemplateSchema = jdbcTemplateSchema;
    }
    
    /*
     End : Setter getters for private variables
     */

    /*
     Start Autowiring the required Class instances
     */
    @Autowired
    private EncryptDecrypt encryptDecrypt;
    /*
     End Autowiring the required Class instances
     */

    /**
     * @return List<OrganizationDTO>
     * @throws AppException
     */
    @Override
    public List<OrganizationDTO> organizationWorkList() throws AppException {
        String procName;
        List<OrganizationDTO> organizationList = null;
        OrganizationDTO organizationDTO;
        try {
            procName = "{CALL Get_OrgDetailsByID(?)}";
            organizationList = new ArrayList<OrganizationDTO>();
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName,
                    new Object[]{0});
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    organizationDTO = new OrganizationDTO();
                    organizationDTO.setOrganizationKey((Integer) resultMap.get("ORG_KEY"));
                    organizationDTO.setParentOrganizationName((String) resultMap.get("PAR_ORG_NM"));
                    organizationDTO.setParentOrganizationKey((Integer) resultMap.get("PAR_ORG_KEY"));
                    organizationDTO.setEncOrganizationKey(encryptDecrypt.encrypt(organizationDTO.getOrganizationKey() + ""));
                    organizationDTO.setOrganizationName((String) resultMap.get("ORG_NM"));
                    organizationDTO.setCityName((String) resultMap.get("CTY_NM"));
                    organizationDTO.setCountryName((String) resultMap.get("CNTRY_NM,"));
                    organizationDTO.setRowStatusValue((String) resultMap.get("OrgStatus"));
                    organizationDTO.setRowStatusKey((Integer) resultMap.get("ROW_STS_KEY"));
                    organizationDTO.setSchemaName((String) resultMap.get("ORG_SCHM"));

                    organizationDTO.setSchemaIndicator((String) resultMap.get("CREAT_ORG_SCHM_IND"));
                    MasterLookUpDTO m = new MasterLookUpDTO();
                    m.setLookUpEntityName((String) resultMap.get("LKP_ENTY_NM"));
                    organizationDTO.setOrganizationTypeObj(m);
                    if (organizationDTO.getRowStatusKey() == ApplicationConstants.DB_ROW_STATUS_ACTIVE_VALUE) {
                        String ind = organizationDTO.getSchemaIndicator();
                        String orgType=(String) resultMap.get("LKP_ENTY_NM");
                        if (orgType!=null && !orgType.equals("") && orgType.equalsIgnoreCase(ApplicationConstants.DB_ORGANIZATIION_TYPE_CLIENT) && ind != null && ind.equalsIgnoreCase(ApplicationConstants.DB_CONSTANT_ORGANIZATION_SCHEMA_INDICATOR_FAILURE)) {
                            organizationDTO.setRowStatusValue(ApplicationConstants.DB_CONSTANT_ORGANIZATION_SCHEMA_NOT_ONBOARDED);
                        }
                    }
                  
                    organizationList.add(organizationDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : getOrganizationDetailByOrgID():" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "getOrganizationDetailByOrgID() :: " + e.getMessage());
        }

        return organizationList;
    }

    /**
     *
     * @param organizationKey
     * @return OrganizationDTO
     * @throws AppException
     */
    @Override
    public OrganizationDTO getOrganizationDetailByOrgID(long organizationKey) throws AppException {

        String procName;
        OrganizationDTO organizationDTO = null;
        try {
            procName = "{CALL Get_OrgDetailsByID(?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName,
                    new Object[]{organizationKey});
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    organizationDTO = new OrganizationDTO();
                    organizationDTO.setOrganizationKey((Integer) resultMap.get("ORG_KEY"));
                    organizationDTO.setEncOrganizationKey(encryptDecrypt.encrypt(organizationDTO.getOrganizationKey() + ""));
                    organizationDTO.setOrganizationName((String) resultMap.get("ORG_NM"));
                    organizationDTO.setEncOrganizationName(encryptDecrypt.encrypt(organizationDTO.getOrganizationName() + ""));
                    organizationDTO.setRowStatusValue((String) resultMap.get("OrgStatus"));
                    organizationDTO.setParentOrganizationName((String) resultMap.get("PAR_ORG_NM"));
                    organizationDTO.setParentOrganizationKey((Integer) resultMap.get("PAR_ORG_KEY"));
                    organizationDTO.setCountryName((String) resultMap.get("CNTRY_NM"));
                    organizationDTO.setStateName((String) resultMap.get("ST_NM"));
                    organizationDTO.setCityName((String) resultMap.get("CTY_NM"));
                    organizationDTO.setStreetAddress1((String) resultMap.get("STR_ADR_1"));
                    organizationDTO.setStreetAddress2((String) resultMap.get("STR_ADR_2"));
                    organizationDTO.setPostalCode((String) resultMap.get("PST_CD"));
                    organizationDTO.setRowStatusKey((Integer) resultMap.get("ROW_STS_KEY"));
                    organizationDTO.setSchemaIndicator((String) resultMap.get("CREAT_ORG_SCHM_IND"));
                    organizationDTO.setSchemaName((String) resultMap.get("ORG_SCHM")); 
                    organizationDTO.setOrganizationDescription((String) resultMap.get("ORG_DESC"));
                    organizationDTO.setDeactiveReason((String) resultMap.get("STS_COMMT_TXT"));
                    organizationDTO.setUsersCount((Integer) resultMap.get("UserCount"));
                    MasterLookUpDTO orgTypeObj = new MasterLookUpDTO();
                    orgTypeObj.setLookUpEntityName((String) resultMap.get("Org Type Name"));
                    orgTypeObj.setMasterLookupKey((Integer) resultMap.get("ORG_TYP_KEY"));
                    organizationDTO.setOrganizationTypeObj(orgTypeObj);
                    
                    if (organizationDTO.getRowStatusKey() == ApplicationConstants.DB_ROW_STATUS_ACTIVE_VALUE) {
                        String ind = organizationDTO.getSchemaIndicator();
                        String orgType = (String) resultMap.get("Org Type Name");
                        if (orgType != null && !orgType.equals("") && orgType.equalsIgnoreCase(ApplicationConstants.DB_ORGANIZATIION_TYPE_CLIENT) && ind != null && ind.equalsIgnoreCase(ApplicationConstants.DB_CONSTANT_ORGANIZATION_SCHEMA_INDICATOR_FAILURE)) {
                            organizationDTO.setRowStatusValue(ApplicationConstants.DB_CONSTANT_ORGANIZATION_SCHEMA_NOT_ONBOARDED);
                        }
                    }
                    

                    MasterLookUpDTO orgIndustryObj = new MasterLookUpDTO();
                    orgIndustryObj.setMasterLookupKey((Integer) resultMap.get("ORG_INDUS_KEY"));
                    orgIndustryObj.setLookUpEntityName((String) resultMap.get("OrgIndustry"));
                    
                    
                    organizationDTO.setOrganizationIndustryObj(orgIndustryObj);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : getOrganizationDetailByOrgID():" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "getOrganizationDetailByOrgID() :: " + e.getMessage());
        }
        return organizationDTO;
    }

    /**
     *
     * @param organizationKey
     * @return List<OrganizationSourceDTO>
     * @throws AppException
     */
    @Override
    public List<OrganizationSourceDTO> getOrganizationSourceInfoByOrgID(long organizationKey) throws AppException {
        OrganizationSourceDTO organizationSourceDTO = null;
        List<OrganizationSourceDTO> sourceList = null;
        try {
            sourceList = new ArrayList<OrganizationSourceDTO>();
            String procName = "{CALL Get_OrgRelDetailsByID(?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName,
                    new Object[]{organizationKey});
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    organizationSourceDTO = new OrganizationSourceDTO();
                    organizationSourceDTO.setSourceKey((Integer) resultMap.get("SRC_KEY"));
                    organizationSourceDTO.setSourceClientID((String) resultMap.get("SRC_CLNT_ID"));
                    organizationSourceDTO.setSourceName((String) resultMap.get("SourceName"));
                    
                    sourceList.add(organizationSourceDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : getOrganizationSourceInfoByOrgID():" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "getOrganizationSourceInfoByOrgID() :: " + e.getMessage());
        }
        return sourceList;
    }

    /**
     * save organization
     *
     * @param organizationDTO
     * @return int
     * @throws AppException
     */
    @Override
    public int saveOrganization(final OrganizationDTO organizationDTO) throws AppException {
        try {
            int retVal = 0;
            String insOrgProc = "{CALL INS_ORG(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            retVal = jdbcTemplate.queryForObject(insOrgProc, new Object[]{organizationDTO.getParentOrganizationKey(), organizationDTO.getOrganizationTypeKey(), organizationDTO.getOrganizationIndustryKey(), organizationDTO.getRowStatusKey(), organizationDTO.getOrganizationName().trim(), organizationDTO.getStreetAddress1().trim(),
                !"".equalsIgnoreCase(organizationDTO.getStreetAddress2().trim()) ? organizationDTO.getStreetAddress2() : null,
                organizationDTO.getCityName().trim(), organizationDTO.getStateName(), organizationDTO.getCountryName(), organizationDTO.getPostalCode().trim(),
                !"".equalsIgnoreCase(organizationDTO.getOrganizationDescription().trim()) ? organizationDTO.getOrganizationDescription() : null,
                organizationDTO.getCreatedByUserID(),
                !"".equalsIgnoreCase(organizationDTO.getDeactiveReason().trim()) ? organizationDTO.getDeactiveReason().trim() : null
            }, Integer.class);
            return retVal;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : saveOrganization:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "saveOrganization() :: " + e.getMessage());
        }
    }

    /**
     * update organization
     *
     * @param organizationDTO
     * @return int
     * @throws AppException
     */
    @Override
    public int updateOrganization(final OrganizationDTO organizationDTO) throws AppException {
        try {
            int retVal = 0;
            String updOrgProc = "{CALL UPDATE_OrgByID(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            retVal = jdbcTemplate.queryForObject(updOrgProc, new Object[]{organizationDTO.getOrganizationKey(), organizationDTO.getRowStatusKey(), organizationDTO.getParentOrganizationKey(), organizationDTO.getOrganizationTypeKey(), organizationDTO.getOrganizationIndustryKey(), organizationDTO.getOrganizationName().trim(),
                organizationDTO.getStreetAddress1().trim(),
                !"".equalsIgnoreCase(organizationDTO.getStreetAddress2().trim()) ? organizationDTO.getStreetAddress2() : null,
                // organizationDTO.getStreetAddress2().trim(), 
                organizationDTO.getCityName().trim(), organizationDTO.getStateName(), organizationDTO.getCountryName(), organizationDTO.getPostalCode().trim(),
                !"".equalsIgnoreCase(organizationDTO.getOrganizationDescription().trim()) ? organizationDTO.getOrganizationDescription() : null,
                //organizationDTO.getOrganizationDescription().trim(),
                organizationDTO.getCreatedByUserID(),
                !"".equalsIgnoreCase(organizationDTO.getDeactiveReason().trim()) ? organizationDTO.getDeactiveReason().trim() : null
            }, Integer.class);
            return retVal;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : updateOrganization:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "updateOrganization() :: " + e.getMessage());
        }
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
    public int saveOrganizationAuthoritativeSourceInfo(final String flag, final long organizationKey, final long sourceKey, final String sourceClientID, final long userID) throws AppException {
        try {
            int retVal = 0;
            String insRelProc = "{CALL INS_ORG_RELID(?,?,?,?,?)}";
            retVal = jdbcTemplate.queryForObject(insRelProc, new Object[]{flag, organizationKey, sourceKey,
                !"".equalsIgnoreCase(sourceClientID) ? sourceClientID : null,
                // sourceClientID, 
                userID}, Integer.class);

            return retVal;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : saveOrganizationAuthoritativeSourceInfo:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "saveOrganizationAuthoritativeSourceInfo() :: " + e.getMessage());
        }
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
    public int saveAuthoritativeSource(final String sourceType, final String sourceName, final String description, final long userID) throws AppException {
        int retVal = 0;
        try {
            String insMstrProc = "{CALL INS_MSTR_LKP(?,?,?,?)}";
            retVal = jdbcTemplate.queryForObject(insMstrProc, new Object[]{sourceType, sourceName, description, userID}, Integer.class);
            return retVal;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : saveAuthoritativeSource:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "saveAuthoritativeSource() :: " + e.getMessage());
        }
    }

    /**
     * Checking Duplicate Organization
     *
     * @param organization
     * @return int
     * @throws AppException
     */
    @Override
    public int validateOrganization(final String organization) throws AppException {

        try {
            String proc = "{CALL Check_OrgNameByName(?)}";
            int retVal = jdbcTemplate.queryForObject(proc, new Object[]{organization}, Integer.class);
            return retVal;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : validateOrganization:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "validateOrganization() :: " + e.getMessage());
        }
    }

    /**
     * Checking Duplicate Authoritative Source
     *
     * @param authoritativeSource
     * @return int
     * @throws AppException
     */
    @Override
    public int validateAuthoritativeSource(String authoritativeSource) throws AppException {
        try {
            String proc = "{CALL Check_AuthoritativeSourceName(?)}";
            int retVal = jdbcTemplate.queryForObject(proc, new Object[]{authoritativeSource}, Integer.class);
            return retVal;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : validateAuthoritativeSource:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "validateAuthoritativeSource() :: " + e.getMessage());
        }
    }

        /**
     * fetching Parent Organizations List
     * 
     * @return List<OrganizationDTO>
     * @throws AppException
     */
    @Override
    public List<OrganizationDTO> parentOrganizationList() throws AppException {
        String procName;
        List<OrganizationDTO> organizationList = null;
        OrganizationDTO organizationDTO;
        try {
            procName = "{CALL Get_OrgDetailsByID(?)}";
            organizationList = new ArrayList<OrganizationDTO>();
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName,
                    new Object[]{0});
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    organizationDTO = new OrganizationDTO();
                    organizationDTO.setOrganizationKey((Integer) resultMap.get("ORG_KEY"));
                    organizationDTO.setParentOrganizationName((String) resultMap.get("PAR_ORG_NM"));
                    organizationDTO.setParentOrganizationKey((Integer) resultMap.get("PAR_ORG_KEY"));
                    organizationDTO.setEncOrganizationKey(encryptDecrypt.encrypt(organizationDTO.getOrganizationKey() + ""));
                    organizationDTO.setOrganizationName((String) resultMap.get("ORG_NM"));
                    organizationDTO.setCityName((String) resultMap.get("CTY_NM"));
                    organizationDTO.setCountryName((String) resultMap.get("CNTRY_NM,"));
                    organizationDTO.setRowStatusValue((String) resultMap.get("OrgStatus"));
                    organizationDTO.setRowStatusKey((Integer) resultMap.get("ROW_STS_KEY"));
                    MasterLookUpDTO m = new MasterLookUpDTO();
                    m.setLookUpEntityName((String) resultMap.get("LKP_ENTY_NM"));
                    organizationDTO.setOrganizationTypeObj(m);
                    if (organizationDTO.getParentOrganizationKey() <= 0 && organizationDTO.getRowStatusKey() == ApplicationConstants.DB_ROW_STATUS_ACTIVE_VALUE) {
                        organizationList.add(organizationDTO);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : parentOrganizationList():" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "parentOrganizationList() :: " + e.getMessage());
        }

        return organizationList;
    }

    @Override
    public int createOrgSchema(OrganizationDTO organizationDTO) throws AppException {
        int retVal = 0;
        try {
            String insMstrProc = "{CALL CreateSchema(?,?)}";
            retVal = jdbcTemplateSchema.queryForObject(insMstrProc, new Object[]{organizationDTO.getSchemaName(), organizationDTO.getOrganizationKey()}, Integer.class);
            return retVal;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : createOrgSchema:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "createOrgSchema() :: " + e.getMessage());
        }

    }

    @Override
    public int createOrgSchemaTables(OrganizationDTO organizationDTO) throws AppException {
        int retVal = 0;
        try {
            String insMstrProc = "{CALL CreateSchemaTables(?,?)}";
            retVal = jdbcTemplateSchema.queryForObject(insMstrProc, new Object[]{organizationDTO.getSchemaName(), organizationDTO.getOrganizationKey()}, Integer.class);
            return retVal;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : createOrgSchemaTables:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "createOrgSchemaTables() :: " + e.getMessage());
        }
    }
    
    @Override
    public int createOrgSchemaETLTables(OrganizationDTO organizationDTO) throws AppException{
        int retVal = 0;
        try {
            String insMstrProc = "{CALL CreateSchemaETLTables(?,?)}";
            retVal = jdbcTemplateSchema.queryForObject(insMstrProc, new Object[]{organizationDTO.getSchemaName(), organizationDTO.getOrganizationKey()}, Integer.class);
            return retVal;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : createOrgSchemaETLTables:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "createOrgSchemaETLTables() :: " + e.getMessage());
        }
    }
            
    @Override
    public int createOrgSchemaView(OrganizationDTO organizationDTO) throws AppException {
        int retVal = 0;
        try {
            String insMstrProc = "{CALL CreateSchemaView(?,?)}";
           retVal= jdbcTemplateSchema.queryForObject(insMstrProc, new Object[]{organizationDTO.getSchemaName(), organizationDTO.getOrganizationKey()}, Integer.class);
            return retVal;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : createOrgSchemaView:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "createOrgSchemaView() :: " + e.getMessage());
        }
    }

    @Override
    public int createOrgSchemaLog(OrganizationDTO organizationDTO) throws AppException {
        int retVal = 0;
        try {
             String insMstrProc = "{CALL INSCLNT_SCHM_LOG(?,?,?,?)}";
                 retVal = jdbcTemplate.queryForObject(insMstrProc, new Object[]{"I", organizationDTO.getOrganizationKey(), organizationDTO.getSchemaStatus(), organizationDTO.getSchemaLog()}, Integer.class);
            return retVal;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : createOrgSchemaView:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "createOrgSchemaView() :: " + e.getMessage());
        }
        
        

    }
    

    @Override
    public int updateOrgSchemaIndicator(OrganizationDTO organizationDTO) throws AppException {
        int retVal = 0;
        try {
            String insMstrProc = "{CALL UPDOrgSchmaInd(?,?,?)}";
            retVal = jdbcTemplate.queryForObject(insMstrProc, new Object[]{organizationDTO.getOrganizationKey(),organizationDTO.getCreatedByUserID(), organizationDTO.getSchemaIndicator()}, Integer.class);
            return retVal;
        } catch (Exception e) {
            organizationDTO.setSchemaStatus(ApplicationConstants.DB_CONSTANT_ORGANIZATION_SCHEMA_INDICATOR_SUCESS_NOT_UPDATED);
            e.printStackTrace();
            logger.error("Exception occurred : updateOrgSchemaIndicator:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "updateOrgSchemaIndicator() :: " + e.getMessage());
        }
        
        

    }
//    public int createOrgSchemaLog1(OrganizationDTO organizationDTO) throws AppException {
//        int retVal = 0;
//        try {
//            String insMstrProc = "{CALL INSSCHMA_LOG(?,?,?,?,?)}";
//            retVal = jdbcTemplateSchema.queryForObject(insMstrProc, new Object[]{"I", organizationDTO.getSchemaKey(), organizationDTO.getOrganizationKey(), organizationDTO.getSchemaStatus(), organizationDTO.getSchemaLog()}, Integer.class);
//            return retVal;
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("Exception occurred : createOrgSchemaView:" + e.getMessage());
//            throw new AppException("Exception Occured while Executing the "
//                    + "createOrgSchemaView() :: " + e.getMessage());
//        }
//    }
    
    public String getOrgSchemaStatusByOrgID(long organizationKey) throws AppException {
        String status = null;
        try {
            String procName = "{CALL GetOrgSchmaLogByID(?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName,
                    new Object[]{organizationKey});
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    status = (String) resultMap.get("SCHM_STS_DESC");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : getOrgSchemaStatusByOrgID():" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "getOrgSchemaStatusByOrgID() :: " + e.getMessage());
        }
        return status;
    }
}
