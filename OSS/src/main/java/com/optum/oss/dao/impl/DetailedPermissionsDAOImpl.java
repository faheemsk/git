    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package com.optum.oss.dao.impl;

    import com.optum.oss.dao.DetailedPermissionsDAO;
    import com.optum.oss.dto.PermissionDTO;
    import com.optum.oss.exception.AppException;
    import com.optum.oss.util.DateUtil;
    import com.optum.oss.util.EncryptDecrypt;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Map;
    import org.apache.log4j.Logger;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.jdbc.core.JdbcTemplate;

    /**
     *
     * @author hpasupuleti
     */
    public class DetailedPermissionsDAOImpl implements DetailedPermissionsDAO {

        private static final Logger logger = Logger.getLogger(DetailedPermissionsDAOImpl.class);

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
        /*
         Start : Autowiring of Fields
         */
        @Autowired
        private DateUtil dateUtil;

        @Autowired
        private EncryptDecrypt encDecrypt;
        /*
         End : Autowiring of Fields
         */

        /**
         * Fetches the data from PERMSN Table based.
         *
         * @param groupID
         * @return List<MasterLookUpDTO>
         * @throws AppException
         */
        @Override
        public List<PermissionDTO> fetchViewDetailedPermissions(final long groupID)
                throws AppException {
            List<PermissionDTO> masterLookUpList = new ArrayList<>();
            try {
                String procName = "{CALL LIST_Permissions(?)}";
                List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName,
                        new Object[]{groupID});

                if (!resultList.isEmpty()) {
                    for (Map<String, Object> resultMap : resultList) {
                        PermissionDTO lookUpDTO = new PermissionDTO();

                        lookUpDTO.setPermissionKey((Integer) (resultMap.get("PERMSN_KEY")));
                        lookUpDTO.setEncPermissionKey(encDecrypt.encrypt(lookUpDTO.getPermissionKey() + ""));
                        lookUpDTO.setPermissionName((String) (resultMap.get("PERMSN_NM")));
                        lookUpDTO.setPermissionDescription((String) (resultMap.get("PERMSN_DESC")));
                        if (null != resultMap.get("UPDT_DT") && "null" != resultMap.get("UPDT_DT")) {
                            lookUpDTO.setModifiedDate(dateUtil.dateConvertionFromDBToJSP(resultMap.get("UPDT_DT").toString()));
                        }
                        masterLookUpList.add(lookUpDTO);

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Exception occurred : fetchViewDetailedPermissions:" + e.getMessage());
                throw new AppException("Exception Occured while Executing the "
                        + "fetchViewDetailedPermissions() :: " + e.getMessage());
            }
            return masterLookUpList;
        }

        @Override
        public int saveDefinition(PermissionDTO permissionDTO) throws AppException {

            int retVal = 0;
            try {
                String procName = "{CALL UPDATE_PermsnDescriptionByID(?,?,?)}";
                retVal = jdbcTemplate.queryForObject(procName,
                        new Object[]{permissionDTO.getPermissionKey(),
                            permissionDTO.getPermissionDescription(),
                            permissionDTO.getModifiedByUserID()}, Integer.class);

            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Exception occurred : saveDefinition:" + e.getMessage());
                throw new AppException("Exception Occured while Executing the "
                        + "saveDefinition() :: " + e.getMessage());
            }
            return retVal;
        }

    }
