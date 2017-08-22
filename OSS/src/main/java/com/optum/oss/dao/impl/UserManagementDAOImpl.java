/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao.impl;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dao.UserManagementDAO;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.OrganizationDTO;
import com.optum.oss.dto.PermissionGroupDTO;
import com.optum.oss.dto.RoleDTO;
import com.optum.oss.dto.UserApplicationRoleDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.util.EncryptDecrypt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author sbhagavatula
 */
public class UserManagementDAOImpl implements UserManagementDAO {

    private static final Logger logger = Logger.getLogger(UserManagementDAOImpl.class);

    /*
     Autowiring the required Class instances
     */
    @Autowired
    private EncryptDecrypt encryptDecrypt;
    /*
     Autowiring the required Class instances
     */

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
     *
     * @param organizationKey
     * @return List<UserProfileDTO>
     * @throws AppException
     */
    @Override
    public List<UserProfileDTO> fetchUserList(final long organizationKey) throws AppException {
        logger.info("Start: UserManagementDAOImpl : fetchUserList");
        List<UserProfileDTO> users = null;
        try {

            String usrProfileProc = "{CALL List_UserWorklistByOrg(?)}";
            List<Map<String, Object>> userList = jdbcTemplate.queryForList(usrProfileProc,
                    new Object[]{organizationKey});

            users = new ArrayList<>(userList.size());
            //UserProfileDTO [] userProfileArr = new UserProfileDTO[userList.size()];
            //int counter =0;
            for (Map<String, Object> userMap : userList) {

                UserProfileDTO userProfileDTO = new UserProfileDTO();
                userProfileDTO.setUserProfileKey((Integer) (userMap.get("USER_ID")));
                String encUserId = encryptDecrypt.encrypt(userMap.get("USER_ID") + "");
                userProfileDTO.setEncUserProfileKey(encUserId);
                userProfileDTO.setFirstName((String) (userMap.get("First Name")));
                userProfileDTO.setMiddleName((String) (userMap.get("MIDL_NM")));
                userProfileDTO.setLastName((String) (userMap.get("LST_NM")));
                userProfileDTO.setOrganizationKey((Integer) (userMap.get("ORG_KEY")));
                userProfileDTO.setEmail((String) (userMap.get("EMAIL_ID")));
                userProfileDTO.setTelephoneNumber((String) (userMap.get("TEL_NBR")));
                userProfileDTO.setRowStatusValue((String) (userMap.get("ROW_STS_KEY")));

                OrganizationDTO orgDTO = new OrganizationDTO();
                orgDTO.setOrganizationName((String) (userMap.get("ORG_NM")));
                userProfileDTO.setOrganizationObj(orgDTO);

                MasterLookUpDTO masterObj = new MasterLookUpDTO();
                masterObj.setLookUpEntityTypeName((String) (userMap.get("USER_TYP_KEY")));
                userProfileDTO.setUserTypeObj(masterObj);

                //  userProfileArr[counter] = userProfileDTO;
                // counter++;
                users.add(userProfileDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : fetchUserList : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "fetchUserList() :: " + e.getMessage());
        }
        logger.info("End: UserManagementDAOImpl : fetchUserList");
        return users;
    }

    /**
     *
     * @param userProfileDto
     * @param sessionDto
     * @return UserProfileDTO
     * @throws AppException
     */
    @Override
    public int saveUserProfile(UserProfileDTO userProfileDto, UserProfileDTO sessionDto) throws AppException {
        logger.info("Start: UserManagementDAOImpl : saveUserProfile");
        int userId = 0;
        try {
            String saveUserProc = "{CALL INS_USER_PRFL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            userId = jdbcTemplate.queryForObject(saveUserProc,
                    new Object[]{userProfileDto.getOrganizationKey(),
                        userProfileDto.getUserTypeKey(), userProfileDto.getRowStatusKey(),
                        userProfileDto.getFirstName(), userProfileDto.getLastName(),
                        !"".equalsIgnoreCase(userProfileDto.getMiddleName()) ? userProfileDto.getMiddleName() : null,
                        !"".equalsIgnoreCase(userProfileDto.getJobTitleName()) ? userProfileDto.getJobTitleName() : null,
                        userProfileDto.getEmail().trim(),
                        !"".equalsIgnoreCase(userProfileDto.getTelephoneNumber()) ? userProfileDto.getTelephoneNumber() : null,
                        0, 0, 0, sessionDto.getUserProfileKey(), null,
                        //  userProfileDto.getStatusComment()
                        !"".equalsIgnoreCase(userProfileDto.getStatusComment()) ? userProfileDto.getStatusComment() : null,
                        userProfileDto.getDepartment()
                    }, Integer.class);
            userProfileDto.setUserProfileKey(userId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : saveUserProfile : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "saveUserProfile() :: " + e.getMessage());
        }
        logger.info("End: UserManagementDAOImpl : saveUserProfile");
        return userId;
    }

    /**
     *
     * @param userProfileDto
     * @param sessionDto
     * @return UserProfileDTO
     * @throws AppException
     */
    @Override
    public int updateUserProfile(UserProfileDTO userProfileDto, final UserProfileDTO sessionDto) throws AppException {
        logger.info("Start: UserManagementDAOImpl : updateUserProfile");
        int retVal = 0;
        try {
            String saveUserProc = "{CALL UPDATE_USER_PRFL(?,?,?,?,?,?,?,?,?,?,?,?)}";
            retVal = jdbcTemplate.queryForObject(saveUserProc,
                    new Object[]{userProfileDto.getUserProfileKey(), userProfileDto.getOrganizationKey(),
                        userProfileDto.getUserTypeKey(), userProfileDto.getRowStatusKey(),
                        userProfileDto.getFirstName(), userProfileDto.getLastName(),
                        !"".equalsIgnoreCase(userProfileDto.getMiddleName()) ? userProfileDto.getMiddleName() : null,
                        !"".equalsIgnoreCase(userProfileDto.getJobTitleName()) ? userProfileDto.getJobTitleName() : null,
                        !"".equalsIgnoreCase(userProfileDto.getTelephoneNumber()) ? userProfileDto.getTelephoneNumber() : null,
                        sessionDto.getUserProfileKey(),
                        !"".equalsIgnoreCase(userProfileDto.getStatusComment()) ? userProfileDto.getStatusComment() : null,
                        userProfileDto.getDepartment()
                    }, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : updateUserProfile : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "updateUserProfile() :: " + e.getMessage());
        }
        logger.info("End: UserManagementDAOImpl : updateUserProfile");
        return retVal;
    }

    /**
     *
     * @param userProfileKey
     * @return UserProfileDTO
     * @throws AppException
     */
    @Override
    public UserProfileDTO viewUserProfile(long userProfileKey) throws AppException {
        logger.info("Start: UserManagementDAOImpl : viewUserProfile");
        UserProfileDTO userProfileDTO = null;
        try {
            userProfileDTO = new UserProfileDTO();
            String usrProfileProc = "{CALL GetUserDetailsByID(?)}";
            List<Map<String, Object>> userList = jdbcTemplate.queryForList(usrProfileProc,
                    new Object[]{userProfileKey});

            for (Map<String, Object> userMap : userList) {
                userProfileDTO.setUserProfileKey((Integer) (userMap.get("ID")));
                String encUserId = encryptDecrypt.encrypt(userProfileDTO.getUserProfileKey() + "");
                userProfileDTO.setEncUserProfileKey(encUserId);
                userProfileDTO.setFirstName((String) (userMap.get("First Name")));
                userProfileDTO.setMiddleName((String) (userMap.get("Middle Name")));
                userProfileDTO.setLastName((String) (userMap.get("Last Name")));
                userProfileDTO.setOrganizationKey((Integer) (userMap.get("ORG_KEY")));
                userProfileDTO.setEmail((String) (userMap.get("EMAIL ID")));
                userProfileDTO.setTelephoneNumber((String) (userMap.get("Phone Number")));
                userProfileDTO.setRowStatusValue((String) (userMap.get("Status")));
                userProfileDTO.setRowStatusKey((Integer) (userMap.get("ROW_STS_KEY")));
                userProfileDTO.setJobTitleName((String) (userMap.get("Job Title")));
                userProfileDTO.setDepartment((String) (userMap.get("DEPT_NM")));
                OrganizationDTO orgDTO = new OrganizationDTO();
                orgDTO.setOrganizationName((String) (userMap.get("Organization Name")));
                orgDTO.setOrganizationKey((Integer) (userMap.get("ORG_KEY")));
                userProfileDTO.setOrganizationObj(orgDTO);

                MasterLookUpDTO masterObj = new MasterLookUpDTO();
                masterObj.setMasterLookupKey((Integer) (userMap.get("USER_TYP_KEY")));
                masterObj.setLookUpEntityTypeName((String) (userMap.get("User Type")));
                userProfileDTO.setUserTypeObj(masterObj);

                userProfileDTO.setStatusComment((String) (userMap.get("STS_COMMT_TXT")));

            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : fetchUserList : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "viewUserProfile() :: " + e.getMessage());
        }
        logger.info("End: UserManagementDAOImpl : viewUserProfile");
        return userProfileDTO;
    }

    /**
     *
     * @param organizationTypeKey
     * @return List<OrganizationDTO>
     * @throws AppException
     */
    @Override
    public List<OrganizationDTO> fetchOrgListByUserType(long organizationTypeKey) throws AppException {
        logger.info("Start: UserManagementDAOImpl : fetchOrgListByUserType");
        List<OrganizationDTO> organizationList = null;
        try {
            organizationList = new ArrayList<>();
            String orgListProc = "{CALL LIST_OrglistByUserType(?)}";
            List<Map<String, Object>> orgList = jdbcTemplate.queryForList(orgListProc,
                    new Object[]{organizationTypeKey});

            for (Map<String, Object> orgMap : orgList) {
                OrganizationDTO organizationDTO = new OrganizationDTO();
                organizationDTO.setOrganizationKey((Integer) (orgMap.get("ORG_KEY")));
                organizationDTO.setOrganizationName((String) (orgMap.get("ORG_NM")));

                organizationList.add(organizationDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : fetchOrgListByUserType : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "fetchOrgListByUserType() :: " + e.getMessage());
        }
        logger.info("End: UserManagementDAOImpl : fetchOrgListByUserType");
        return organizationList;
    }

    /**
     *
     * @return List<RoleDTO>
     * @throws AppException
     */
    @Override
    public List<RoleDTO> fetchRolesList(final long userTypeKey) throws AppException {
        logger.info("Start: UserManagementDAOImpl : fetchRolesList");
        List<RoleDTO> roleList = null;
        try {
            roleList = new ArrayList<>();
            String roleListProc = "{CALL LIST_Roles(?)}";
            List<Map<String, Object>> roleListResult = jdbcTemplate.queryForList(roleListProc, new Object[]{userTypeKey});

            for (Map<String, Object> roleMap : roleListResult) {
                RoleDTO roleDTO = new RoleDTO();
                roleDTO.setAppRoleName((String) (roleMap.get("APPL_ROLE_NM")));
                roleDTO.setAppRoleKey((Integer) (roleMap.get("APPL_ROLE_KEY")));
                roleList.add(roleDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : fetchRolesList : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "fetchRolesList() :: " + e.getMessage());
        }
        logger.info("End: UserManagementDAOImpl : fetchRolesList");
        return roleList;
    }

    /**
     *
     * @param userProfileKey
     * @return Map<Integer, String>
     * @throws AppException
     */
    @Override
    public Map<Integer, String> fetchRolesByUser(long userProfileKey) throws AppException {
        logger.info("Start: UserManagementDAOImpl : fetchRolesByUser");
        Map<Integer, String> userRolesMap = new HashMap<>();
        try {
            String roleListProc = "{CALL GetUserRolenamesByID(?)}";
            List<Map<String, Object>> roleListResult = jdbcTemplate.queryForList(roleListProc,
                    new Object[]{userProfileKey});

            for (Map<String, Object> roleMap : roleListResult) {
                userRolesMap.put((Integer) (roleMap.get("APPL_ROLE_KEY")), (String) (roleMap.get("APPL_ROLE_NM")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : fetchRolesByUser : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "fetchRolesByUser() :: " + e.getMessage());
        }
        logger.info("End: UserManagementDAOImpl : fetchRolesByUser");
        return userRolesMap;
    }

    /**
     *
     * @param userProfileDto
     * @param sessionDto
     * @return Inserted role id
     * @throws AppException
     */
    @Override
    public int saveUserApplicationRole(UserProfileDTO userProfileDto, UserProfileDTO sessionDto) throws AppException {
        logger.info("Start: UserManagementDAOImpl : saveUserApplicationRole");
        int retVal = 0;
        try {
            List<UserApplicationRoleDTO> appRolesList = userProfileDto.getUserApplicationRoleListObj();
            String saveUserRoleProc = "{CALL INS_USER_APPL_ROLE(?,?,?,?,?)}";

            for (UserApplicationRoleDTO userApplicationRoleDTO : appRolesList) {
                retVal = jdbcTemplate.queryForObject(saveUserRoleProc,
                        new Object[]{"I", ApplicationConstants.DB_ROW_STATUS_ACTIVE_VALUE,
                            userApplicationRoleDTO.getUserApplicationRoleKey(),
                            userProfileDto.getUserProfileKey(),
                            sessionDto.getUserProfileKey()
                        }, Integer.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : saveUserApplicationRole : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "saveUserApplicationRole() :: " + e.getMessage());
        }
        logger.info("End: UserManagementDAOImpl : saveUserApplicationRole");
        return retVal;
    }

    /**
     *
     * @param userProfileKey
     * @param sessionDto
     * @return Deleted row count
     * @throws AppException
     */
    @Override
    public int deleteUserApplicationRoles(long userProfileKey, UserProfileDTO sessionDto) throws AppException {
        logger.info("Start: UserManagementDAOImpl : deleteUserApplicationRoles");
        int deleteId = 0;
        try {
            String deleteUserRoleProc = "{CALL INS_USER_APPL_ROLE(?,?,?,?,?)}";
            deleteId = jdbcTemplate.queryForObject(deleteUserRoleProc,
                    new Object[]{"D", 0, 0, userProfileKey, sessionDto.getUserProfileKey()}, Integer.class);

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : deleteUserApplicationRoles : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "deleteUserApplicationRoles() :: " + e.getMessage());
        }
        logger.info("End: UserManagementDAOImpl : deleteUserApplicationRoles");
        return deleteId;
    }

    /**
     *
     * @param userProfileKey
     * @return Map<Integer, PermissionGroupDTO>
     * @throws AppException
     */
    @Override
    public Map<Integer, PermissionGroupDTO> fetchPermissionGroupsByUserID(long userProfileKey) throws AppException {
        logger.info("Start: UserManagementDAOImpl : fetchPermissionGroupsByUserID");
        Map<Integer, PermissionGroupDTO> permissionGroupsMap = new HashMap<>();
        try {
            String permissionGroupsProc = "{CALL LIST_PermissionGroupsByUserID(?)}";
            List<Map<String, Object>> permissionGroupsResult = jdbcTemplate.queryForList(permissionGroupsProc,
                    new Object[]{userProfileKey});

            for (Map<String, Object> roleMap : permissionGroupsResult) {
                PermissionGroupDTO permissionGrpObj = new PermissionGroupDTO();
                permissionGrpObj.setPermissionGroupKey((Integer) (roleMap.get("PERMSN_GRP_KEY")));
                permissionGrpObj.setPermissionGroupName((String) (roleMap.get("PERMSN_GRP_NM")));
                permissionGrpObj.setPermissionGroupDesc((String) (roleMap.get("PERMSN_GRP_DESC")));

                permissionGroupsMap.put((Integer) (roleMap.get("PERMSN_GRP_KEY")), permissionGrpObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : fetchPermissionGroupsByUserID : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "fetchPermissionGroupsByUserID() :: " + e.getMessage());
        }
        logger.info("End: UserManagementDAOImpl : fetchPermissionGroupsByUserID");
        return permissionGroupsMap;
    }

    /**
     *
     * @param appRoleKeys
     * @return List<PermissionGroupDTO>
     * @throws AppException
     */
    @Override
    public List<PermissionGroupDTO> fetchPermissionGroupByRole(String appRoleKeys) throws AppException {
        logger.info("Start: UserManagementDAOImpl : fetchPermissionGroupByRole");
        List<PermissionGroupDTO> permissionGroupsMap = null;
        try {
            permissionGroupsMap = new ArrayList<>();
            String permissionGroupsProc = "{CALL LIST_PermissionGroupsByRole(?)}";
            List<Map<String, Object>> permissionGroupsResult = jdbcTemplate.queryForList(permissionGroupsProc,
                    new Object[]{appRoleKeys});

            for (Map<String, Object> roleMap : permissionGroupsResult) {
                PermissionGroupDTO permissionGrpObj = new PermissionGroupDTO();
                permissionGrpObj.setPermissionGroupKey((Integer) (roleMap.get("PERMSN_GRP_KEY")));
                permissionGrpObj.setPermissionGroupName((String) (roleMap.get("PERMSN_GRP_NM")));
                permissionGrpObj.setPermissionGroupDesc((String) (roleMap.get("PERMSN_GRP_DESC")));

                permissionGroupsMap.add(permissionGrpObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : fetchPermissionGroupByRole : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "fetchPermissionGroupByRole() :: " + e.getMessage());
        }
        logger.info("End: UserManagementDAOImpl : fetchPermissionGroupByRole");
        return permissionGroupsMap;
    }

    /**
     * Fetch user details based on user profile key
     *
     * @param userID
     * @return
     * @throws AppException
     */
    @Override
    public UserProfileDTO fetchUserInfoByUserID(long userID) throws AppException {
        logger.info("Start: UserManagementDAOImpl : fetchUserInfoByUserID");
        UserProfileDTO userDTO = new UserProfileDTO();
        try {
            String userProc = "{CALL GetEmailIDbyUserID(?)}";
            List<Map<String, Object>> userList = jdbcTemplate.queryForList(userProc,
                    new Object[]{userID});

            for (Map<String, Object> userMap : userList) {
                userDTO.setEmail((String) (userMap.get("EMAIL_ID")));
                userDTO.setFirstName((String) (userMap.get("First Name")));
                userDTO.setMiddleName((String) (userMap.get("Middle Name")));
                userDTO.setLastName((String) (userMap.get("Last Name")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : fetchUserInfoByUserID : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "fetchUserInfoByUserID() :: " + e.getMessage());
        }
        logger.info("End: UserManagementDAOImpl : fetchUserInfoByUserID");
        return userDTO;
    }

    public Map<String, List<PermissionGroupDTO>> fetchPermissionMapGroupByRole(String appRoleKeys) throws AppException {
        logger.info("Start: UserManagementDAOImpl : fetchPermissionMapGroupByRole");
        Map<String, List<PermissionGroupDTO>> permissionGroupsMap = new LinkedHashMap<String, List<PermissionGroupDTO>>();
        List<PermissionGroupDTO> permissionGroupsList = null;
        try {

            String permissionGroupsProc = "{CALL LIST_PermissionGroupsByRole(?)}";
            List<Map<String, Object>> permissionGroupsResult = jdbcTemplate.queryForList(permissionGroupsProc,
                    new Object[]{appRoleKeys});

            for (Map<String, Object> roleMap : permissionGroupsResult) {
                PermissionGroupDTO permissionGrpDTO = new PermissionGroupDTO();
                permissionGrpDTO.setPermissionGroupKey((Integer) (roleMap.get("PERMSN_GRP_KEY")));
                permissionGrpDTO.setPermissionGroupName((String) (roleMap.get("PERMSN_GRP_NM")));
                permissionGrpDTO.setPermissionGroupDesc((String) (roleMap.get("PERMSN_GRP_DESC")));
                permissionGrpDTO.setAppRoleKey((Integer) (roleMap.get("APPL_ROLE_KEY")));
                permissionGrpDTO.setAppRoleName((String) (roleMap.get("APPL_ROLE_NM")));

                if (permissionGroupsMap.containsKey(permissionGrpDTO.getAppRoleKey() + ApplicationConstants.SYMBOL_SPLITING + permissionGrpDTO.getAppRoleName())) {
                    permissionGroupsList = permissionGroupsMap.get(permissionGrpDTO.getAppRoleKey() + ApplicationConstants.SYMBOL_SPLITING + permissionGrpDTO.getAppRoleName());
                    permissionGroupsList.add(permissionGrpDTO);
                } else {
                    permissionGroupsList = new ArrayList<>();
                    permissionGroupsList.add(permissionGrpDTO);
                    permissionGroupsMap.put(permissionGrpDTO.getAppRoleKey() + ApplicationConstants.SYMBOL_SPLITING + permissionGrpDTO.getAppRoleName(), permissionGroupsList);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : fetchPermissionMapGroupByRole : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "fetchPermissionMapGroupByRole() :: " + e.getMessage());
        }
        logger.info("End: UserManagementDAOImpl : fetchPermissionMapGroupByRole");
        return permissionGroupsMap;
    }
}
