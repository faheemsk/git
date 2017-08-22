/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao.impl;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dao.ManageEngagementsDAO;
import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.dto.ClientEngagementSourceDTO;
import com.optum.oss.dto.ManageServiceUserDTO;
import com.optum.oss.dto.SecurityPackageDTO;
import com.optum.oss.dto.SecurityServiceDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.ClientEngagementMailHelper;
import com.optum.oss.service.impl.UserManagementServiceImpl;
import com.optum.oss.util.DateUtil;
import com.optum.oss.util.EncryptDecrypt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 *
 * @author sbhagavatula
 */
public class ManageEngagementsDAOImpl implements ManageEngagementsDAO {

    private static final Logger logger = Logger.getLogger(ManageEngagementsDAOImpl.class);

    /*
     Autowiring the required Class instances
     */
    @Autowired
    private EncryptDecrypt encryptDecrypt;

    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private ClientEngagementMailHelper clientEngagementMailHelper;

    @Autowired
    private UserManagementServiceImpl userManagementServiceImpl;
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
     * Used to save engagement details
     *
     * @param clientEngagementDTO
     * @param sessionDTO
     * @return
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int saveEngagementInfo(final ClientEngagementDTO clientEngagementDTO,
            final UserProfileDTO sessionDTO) throws AppException {
        logger.info("Start: ManageEngagementsDAOImpl : saveClientEngagementDetails");
        int engagementId = 0;
        try {
            int rosStatusKey = ApplicationConstants.DB_ROW_STATUS_ACTIVE_VALUE;
            int engagementStatusKey = 1;
            String saveEngagementProc = "{CALL INS_CLNT_ENGMT(?,?,?,?,?,?,?,?,?,?,?,?)}";
            engagementId = jdbcTemplate.queryForObject(saveEngagementProc,
                    new Object[]{clientEngagementDTO.getClientID(),
                        clientEngagementDTO.getSecurityPackageObj().getSecurityPackageCode(),
                        clientEngagementDTO.getEngagementStatusKey(), rosStatusKey,
                        clientEngagementDTO.getEngagementCode(), clientEngagementDTO.getEngagementName(),
                        clientEngagementDTO.getAgreementDate(), clientEngagementDTO.getEstimatedStartDate(),
                        clientEngagementDTO.getEstimatedEndDate(), clientEngagementDTO.getEngagementDesc(),
                        // clientEngagementDTO.getEngagementStatusComment(),
                        !"".equalsIgnoreCase(clientEngagementDTO.getEngagementStatusComment()) ? clientEngagementDTO.getEngagementStatusComment() : null,
                        sessionDTO.getUserProfileKey()
                    }, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : saveClientEngagementDetails : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "saveClientEngagementDetails() :: " + e.getMessage());
        }
        logger.info("End: ManageEngagementsDAOImpl : saveClientEngagementDetails");
        return engagementId;
    }

    /**
     * Used to fetch all security packages
     *
     * @return
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<SecurityPackageDTO> fetchSecurityPackages() throws AppException {
        logger.info("Start: ManageEngagementsDAOImpl : fetchSecurityPackages");
        List<SecurityPackageDTO> packagesList = null;
        try {
            packagesList = new ArrayList<>();
            String packagesProc = "{CALL LIST_SecurPkg()}";
            List<Map<String, Object>> packagesMap = jdbcTemplate.queryForList(packagesProc);

            for (Map<String, Object> securityPackage : packagesMap) {
                SecurityPackageDTO securityPackageDTO = new SecurityPackageDTO();
                securityPackageDTO.setSecurityPackageCode((String) securityPackage.get("SECUR_PKG_CD"));
                securityPackageDTO.setSecurityPackageName((String) securityPackage.get("SECUR_PKG_NM"));

                packagesList.add(securityPackageDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : fetchSecurityPackages : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "fetchSecurityPackages() :: " + e.getMessage());
        }
        logger.info("End: ManageEngagementsDAOImpl : fetchSecurityPackages");
        return packagesList;
    }

    /**
     * Used to fetch Users by Organization and User Type
     *
     * @param orgId
     * @param userType
     * @param flag
     * @param mappedUsers
     * @return
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<UserProfileDTO> fetchUsersByOrgIDAndUserTypeID(final long orgId, final String userType, final String flag, List<String> mappedUsers)
            throws AppException {
        logger.info("Start: ManageEngagementsDAOImpl : fetchUsersByOrgIDAndUserTypeID");
        List<UserProfileDTO> users = null;
        try {
            users = new ArrayList<>();
            String usrProfileProc = "{CALL List_UserByOrgIDandUserType(?,?,?)}";
            List<Map<String, Object>> userList = jdbcTemplate.queryForList(usrProfileProc,
                    new Object[]{orgId, userType, flag});

            for (Map<String, Object> userMap : userList) {
                if(!mappedUsers.contains(userMap.get("User Name") + "")) {
                    UserProfileDTO userProfileDTO = new UserProfileDTO();
                    userProfileDTO.setUserProfileKey((Integer) (userMap.get("USER_ID")));
                    userProfileDTO.setFirstName((String) (userMap.get("User Name")));

                    users.add(userProfileDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : fetchUsersByOrgIDAndUserTypeID : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "fetchUsersByOrgIDAndUserTypeID() :: " + e.getMessage());
        }
        logger.info("End: ManageEngagementsDAOImpl : fetchUsersByOrgIDAndUserTypeID");
        return users;
    }
    
    /**
     *
     * @param userObj
     * @return
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<UserProfileDTO> fetchUsersByRole(final UserProfileDTO userObj)
            throws AppException {
        logger.info("Start: ManageEngagementsDAOImpl : fetchUsersByRole");
        List<UserProfileDTO> users = null;
        try {
            users = new ArrayList<>();
            String usrProfileProc = "{CALL GetUserDetailsByRole(?,?,?)}";
            List<Map<String, Object>> userList = jdbcTemplate.queryForList(usrProfileProc,
                    new Object[]{userObj.getAppRoleKey(), userObj.getUserTypeKey(),
                    userObj.getOrganizationKey()});

            for (Map<String, Object> userMap : userList) {
                UserProfileDTO userProfileDTO = new UserProfileDTO();
                userProfileDTO.setUserProfileKey((Integer) (userMap.get("ID")));
                userProfileDTO.setFirstName((String) (userMap.get("First Name")));
                userProfileDTO.setLastName((String) (userMap.get("Last Name")));
                users.add(userProfileDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : fetchUsersByRole : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "fetchUsersByRole() :: " + e.getMessage());
        }
        logger.info("End: ManageEngagementsDAOImpl : fetchUsersByRole");
        return users;
    }

    /**
     * Used to fetch services by package id
     *
     * @param packageID
     * @return
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<SecurityServiceDTO> fetchServicesByPackage(String packageID) throws AppException {
        logger.info("Start: ManageEngagementsDAOImpl : fetchServicesByPackage");
        List<SecurityServiceDTO> services = null;
        try {
            services = new ArrayList<>();
            String usrProfileProc = "{CALL LIST_ServicesByPkgkey(?)}";
            List<Map<String, Object>> userList = jdbcTemplate.queryForList(usrProfileProc,
                    new Object[]{packageID});

            for (Map<String, Object> userMap : userList) {
                SecurityServiceDTO securityServiceDTO = new SecurityServiceDTO();
                securityServiceDTO.setSecurityServiceCode((String) (userMap.get("SECUR_SRVC_CD")));
                securityServiceDTO.setSecurityServiceName((String) (userMap.get("SECUR_SRVC_NM")));

                services.add(securityServiceDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : fetchServicesByPackage : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "fetchServicesByPackage() :: " + e.getMessage());
        }
        logger.info("End: ManageEngagementsDAOImpl : fetchServicesByPackage");
        return services;
    }

    /**
     *
     * @param clientEngagementDTO
     * @param sessionDTO
     * @param services
     * @return
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int saveEngagementServices(ClientEngagementDTO clientEngagementDTO,
            UserProfileDTO sessionDTO, List<String> services) throws AppException {
        logger.info("Start: ManageEngagementsDAOImpl : saveEngagementServices");
        int statId = 0;
        try {
            String saveServicesProc = "{CALL INS_CLNT_SECUR_SRVC_ENGMT(?,?,?,?,?,?,?,?)}";
            List<SecurityServiceDTO> servicesList = clientEngagementDTO.getEngagementServiceLi();
            int lockInd = 0;
            //List<String> serviceCodes = Arrays.asList(clientEngagementDTO.getSelectedServices().split("\\s*,\\s*"));
            for (SecurityServiceDTO securityServiceDTO : servicesList) {
                if (services.contains(securityServiceDTO.getSecurityServiceCode())) {
                    statId = jdbcTemplate.queryForObject(saveServicesProc,
                            new Object[]{clientEngagementDTO.getEngagementCode(),
                                securityServiceDTO.getSecurityServiceCode(),
                                clientEngagementDTO.getEngagementStatusKey(),
                                ApplicationConstants.DB_ROW_STATUS_ACTIVE_VALUE,
                                securityServiceDTO.getServiceStartDate(),
                                securityServiceDTO.getServiceEndDate(),
                                lockInd,
                                sessionDTO.getUserProfileKey()
                            }, Integer.class);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : saveEngagementServices : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "saveEngagementServices() :: " + e.getMessage());
        }
        logger.info("End: ManageEngagementsDAOImpl : saveEngagementServices");
        return statId;
    }

    /**
     *
     * @param engagementUsrObj
     * @param sessionDTO
     * @param engCode
     * @return
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int saveEngagementUsers(UserProfileDTO engagementUsrObj,UserProfileDTO sessionDTO,String engCode) throws AppException {
        logger.info("Start: ManageEngagementsDAOImpl : saveEngagementUsers");
        int statId = 0;
        try {
            String saveAssignUserProc = "{CALL INS_CLNT_ENGMT_USER_ASGN(?,?,?,?,?,?,?)}";
            //GET USER APP ROLE KEY
            long userAppRoleKey = getUserAppRoleKey(engagementUsrObj.getUserProfileKey(), engagementUsrObj.getAppRoleKey());
            statId = jdbcTemplate.queryForObject(saveAssignUserProc,
                    new Object[]{ApplicationConstants.DB_ROW_STATUS_ACTIVE_VALUE,
                        engCode,
                        engagementUsrObj.getUserProfileKey(),
                        null,
                        sessionDTO.getUserProfileKey(),
                        userAppRoleKey,
                        engagementUsrObj.getAppRoleKey()
                    }, Integer.class);

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : saveEngagementUsers : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "saveEngagementUsers() :: " + e.getMessage());
        }
        logger.info("End: ManageEngagementsDAOImpl : saveEngagementUsers");
        return statId;
    }
    
    public long getUserAppRoleKey(final long userId, final long appRoleKey)
            throws AppException {
        logger.info("Start: ManageEngagementsDAOImpl : getUserAppRoleKey");
        List<UserProfileDTO> users = null;
        long usrAppRoleKey = 0;
        try {
            users = new ArrayList<>();
            String usrProfileProc = "{CALL GetUserAppRoleKey(?,?)}";
            List<Map<String, Object>> userList = jdbcTemplate.queryForList(usrProfileProc,
                    new Object[]{userId, appRoleKey});

            for (Map<String, Object> userMap : userList) {
                usrAppRoleKey = Long.parseLong(userMap.get("USER_APPL_ROLE_KEY") + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : getUserAppRoleKey : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "getUserAppRoleKey() :: " + e.getMessage());
        }
        logger.info("End: ManageEngagementsDAOImpl : getUserAppRoleKey");
        return usrAppRoleKey;
    }

    /**
     *
     * @param clientEngagementDTO
     * @param sessionDTO
     * @return
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int saveEngagementSourceInfo(ClientEngagementDTO clientEngagementDTO,
            UserProfileDTO sessionDTO) throws AppException {
        logger.info("Start: ManageEngagementsDAOImpl : saveEngagementSourceInfo");
        int statId = 0;
        try {
            String saveEngagementSourceProc = "{CALL INS_CLNT_REL_ENGMT_INDENTIFIER(?,?,?,?)}";
            List<ClientEngagementSourceDTO> sourceInfoList = clientEngagementDTO.getClientEngagementSourceLi();

            for (ClientEngagementSourceDTO clientEngagementSourceDTO : sourceInfoList) {
                if (clientEngagementSourceDTO.getSourceKey() > 0) {
                    statId = jdbcTemplate.queryForObject(saveEngagementSourceProc,
                            new Object[]{clientEngagementDTO.getEngagementCode(),
                                clientEngagementSourceDTO.getSourceKey(),
                                clientEngagementSourceDTO.getClientEngSourceCode(),
                                sessionDTO.getUserProfileKey()
                            }, Integer.class);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : saveEngagementSourceInfo : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "saveEngagementSourceInfo() :: " + e.getMessage());
        }
        logger.info("End: ManageEngagementsDAOImpl : saveEngagementSourceInfo");
        return statId;
    }

    /**
     * This method for fetching manage engagements work list
     *
     * @param organizationKey
     * @param userProfileKey
     * @param fetchFlag
     * @return List<ClientEngagementDTO>
     * @throws AppException
     */
    @Override
    public List<ClientEngagementDTO> fetchEngagementsWorklist(final long organizationKey, final long userProfileKey, final String fetchFlag)
            throws AppException {
        logger.info("Start: ManageEngagementsDAOImpl : fetchEngagementsWorklist");
        String workListProc = "{CALL LIST_ManageEngagements(?,?)}";
        List<ClientEngagementDTO> clientEngagementList = new ArrayList<>();
        try {
            if(!fetchFlag.equalsIgnoreCase("")){
                List<Map<String, Object>> resultList = jdbcTemplate.queryForList(workListProc, new Object[]{
                    userProfileKey,
                    fetchFlag});
                if (!resultList.isEmpty()) {
                    for (Map<String, Object> resultMap : resultList) {
                        ClientEngagementDTO clientEngagementDTO = new ClientEngagementDTO();
                        clientEngagementDTO.setEngagementCode(resultMap.get("Engagement Code") + "");
                        clientEngagementDTO.setEncEngagementCode(encryptDecrypt.encrypt(clientEngagementDTO.getEngagementCode()));
                        clientEngagementDTO.setParentClientName(resultMap.get("Parent Client Name") + "");
                        clientEngagementDTO.setClientName(resultMap.get("Client Name") + "");
                        clientEngagementDTO.setAgreementDate(dateUtil.dateConvertionFromDBToJSP(resultMap.get("Agreement Date") + ""));
                        clientEngagementDTO.setEstimatedStartDate(dateUtil.dateConvertionFromDBToJSP(resultMap.get("Estimated Start Date") + ""));
                        clientEngagementDTO.setEstimatedEndDate(dateUtil.dateConvertionFromDBToJSP(resultMap.get("Estimated End Date") + ""));
                        clientEngagementDTO.setEngagementStatusName(resultMap.get("Status") + "");
                        if (null == resultMap.get("UPDT_DT") || "null" == resultMap.get("UPDT_DT")) {
                            clientEngagementDTO.setModifiedDate(dateUtil.dateConvertionFromDBToJSP(resultMap.get("CREAT_DT") + ""));
                        } else {
                            clientEngagementDTO.setModifiedDate(dateUtil.dateConvertionFromDBToJSP(resultMap.get("UPDT_DT") + ""));
                        }
                        clientEngagementList.add(clientEngagementDTO);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : fetchEngagementsWorklist : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "fetchEngagementsWorklist() :: " + e.getMessage());
        }
        logger.info("End: ManageEngagementsDAOImpl : fetchEngagementsWorklist");
        return clientEngagementList;
    }

    /**
     * This method for view engagement details by client engagement key
     *
     * @param engagementDto
     * @return ClientEngagementDTO
     * @throws AppException
     */
    @Override
    public ClientEngagementDTO viewEngagementByEngmtKey(final ClientEngagementDTO engagementDto) throws AppException {
        logger.info("Start: ManageEngagementsDAOImpl : viewEngagementByEngmtKey");
        String viewEngagementProc = "{CALL Get_ClntEngmtByID(?)}";

        try {
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(viewEngagementProc, new Object[]{engagementDto.getEngagementCode()});
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    engagementDto.setEngagementStatusKey((Integer) resultMap.get("ENGMT_STS_KEY"));
                    engagementDto.setRowStatusValue(resultMap.get("ROW_STS_VAL") + "");
                    engagementDto.setRowStatusKey(Long.parseLong(resultMap.get("ROW_STS_KEY")+""));
                    engagementDto.setEngagementCode(resultMap.get("CLNT_ENGMT_CD") + "");
                    engagementDto.setEncEngagementCode(encryptDecrypt.encrypt(engagementDto.getEngagementCode()));
                    engagementDto.setEngagementName(resultMap.get("CLNT_ENGMT_NM") + "");
                    engagementDto.setAgreementDate(dateUtil.dateConvertionFromDBToJSP(resultMap.get("AGR_DT") + ""));
                    engagementDto.setEstimatedStartDate(dateUtil.dateConvertionFromDBToJSP(resultMap.get("ENGMT_STRT_DT") + ""));
                    engagementDto.setEstimatedEndDate(dateUtil.dateConvertionFromDBToJSP(resultMap.get("ENGMT_EST_END_DT") + ""));
                     engagementDto.setEngagementDesc(resultMap.get("CLNT_ENGMT_DESC") + "");
                    if (resultMap.get("ENGMT_COMMT") != null) {
                        engagementDto.setEngagementStatusComment(resultMap.get("ENGMT_COMMT") + "");
                    } else {
                        engagementDto.setEngagementStatusComment("");
                    }
                       // engagementDto.setEngagementStatusComment(resultMap.get("ENGMT_COMMT") + ""); 
                    engagementDto.setClientName(resultMap.get("ClientName") + "");
                    engagementDto.setClientID((Integer) resultMap.get("CLNT_ORG_KEY"));
                    engagementDto.setParentClientName(resultMap.get("Parent Client Name") + "");
                    engagementDto.setEncRowStatusValue(resultMap.get("OrgType") + "");
                    SecurityPackageDTO packageDto = new SecurityPackageDTO();
                    packageDto.setSecurityPackageName(resultMap.get("SECUR_PKG_NM") + "");
                    packageDto.setSecurityPackageCode(resultMap.get("SECUR_PKG_CD") + "");
                    packageDto.setEncSecurityPackageName(resultMap.get("SECUR_PKG_NM") + "");
                    packageDto.setEncSecurityPackageCode(encryptDecrypt.encrypt(resultMap.get("SECUR_PKG_CD") + ""));
                    engagementDto.setPublishedDate(dateUtil.dateConvertionFromDBToJSP(resultMap.get("CLNT_PUBL_DT") + ""));
                    engagementDto.setSecurityPackageObj(packageDto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : viewEngagementByEngmtKey : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "viewEngagementByEngmtKey() :: " + e.getMessage());
        }
        logger.info("End: ManageEngagementsDAOImpl : viewEngagementByEngmtKey");
        return engagementDto;
    }

    /**
     * This method for fetching security services by client engagement key
     *
     * @param engagementDto
     * @return ClientEngagementDTO
     * @throws AppException
     */
    @Override
    public ClientEngagementDTO fetchSecurityServicesByEngmtId(final ClientEngagementDTO engagementDto) throws AppException {
        logger.info("Start: ManageEngagementsDAOImpl : fetchSecurityServicesByEngmtId");
        String fetchSecurityServicesProc = "{CALL Get_ClntSecurSrvcEngmtByID(?)}";
        List<SecurityServiceDTO> securityServicesList = new ArrayList<>();
        String separator = "";
        String selectedServices = "";
        try {
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(fetchSecurityServicesProc, new Object[]{engagementDto.getEngagementCode()});
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    SecurityServiceDTO securityService = new SecurityServiceDTO();
                    securityService.setSecurityServiceCode(resultMap.get("SECUR_SRVC_CD") + "");
                    securityService.setRowStatusKey((Integer) resultMap.get("ROW_STS_KEY"));
                    securityService.setServiceStartDate(dateUtil.dateConvertionFromDBToJSP(resultMap.get("SRVC_EST_STRT_DT") + ""));
                    securityService.setServiceEndDate(dateUtil.dateConvertionFromDBToJSP(resultMap.get("SRVC_EST_END_DT") + ""));
                    securityService.setCreatedDate(resultMap.get("CREAT_DT") + "");
                    securityService.setCreatedByUserID((Integer) resultMap.get("CREAT_USER_ID"));
                    if (null != resultMap.get("UPDT_DT") && "null" != resultMap.get("UPDT_DT")) {
                        securityService.setModifiedDate(resultMap.get("UPDT_DT") + "");
                    }
                    if (null != resultMap.get("UPDT_USER_ID") && "null" != resultMap.get("UPDT_USER_ID")) {
                        securityService.setModifiedByUserID((Integer) resultMap.get("UPDT_USER_ID"));
                    }
                    securityService.setSecurityServiceName(resultMap.get("SECUR_SRVC_NM") + "");
                    securityServicesList.add(securityService);

                    securityService.setFileCount(resultMap.get("FileCount") + "");
                    //Build coma seperated security service codes
                    selectedServices += separator;
                    selectedServices += securityService.getSecurityServiceCode();
                    separator = ",";
                }
                //Added for fetching selected services coma seperated
                engagementDto.setSelectedServices(selectedServices);

                engagementDto.setEngagementServiceLi(securityServicesList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : fetchSecurityServicesByEngmtId : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "fetchSecurityServicesByEngmtId() :: " + e.getMessage());
        }
        logger.info("End: ManageEngagementsDAOImpl : fetchSecurityServicesByEngmtId");
        return engagementDto;
    }

    /**
     * This method for fetching client engagement assigned user details by
     * engagement key
     *
     * @param engagementDto
     * @return ClientEngagementDTO
     * @throws AppException
     */
    @Override
    public List<UserProfileDTO> getClientEngagementAssignedUserByEngmtKey(final String clientEngagementCode) throws AppException {
        logger.info("Start: ManageEngagementsDAOImpl : getClientEngagementAssignedUserByEngmtKey");
        String fetchSecurityServicesProc = "{CALL Get_ClntEngmtUserAsgnByID(?)}";
        List<UserProfileDTO> engUsersList = new ArrayList<>();
        try {
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(fetchSecurityServicesProc, new Object[]{clientEngagementCode});
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    UserProfileDTO engagementUserDTO = new UserProfileDTO();
                    engagementUserDTO.getUserTypeObj().setLookUpEntityName(resultMap.get("User Type") + "");
                    engagementUserDTO.setFirstName(resultMap.get("User Name") + "");
                    engagementUserDTO.setAppRoleName(resultMap.get("APPL_ROLE_NM") + "");
                    engagementUserDTO.setAppRoleKey((Integer) resultMap.get("APPL_ROLE_KEY"));
                    engagementUserDTO.setUserID(resultMap.get("USER_ID")+"");
                    engagementUserDTO.setUserProfileKey((Integer) resultMap.get("CLNT_ENGMT_USER_ASGN_KEY"));
                    
                    engUsersList.add(engagementUserDTO);
                        
                }
            }
            //engagementDto.setEngagementPartnerUserLi(engUsersList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : getClientEngagementAssignedUserByEngmtKey : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "getClientEngagementAssignedUserByEngmtKey() :: " + e.getMessage());
        }
        logger.info("End: ManageEngagementsDAOImpl : getClientEngagementAssignedUserByEngmtKey");
        return engUsersList;
    }

    /**
     * This method for fetching client engagement source details by engagement
     * key
     *
     * @param engagementDto
     * @return ClientEngagementDTO
     * @throws AppException
     */
    @Override
    public ClientEngagementDTO getClientEngagementSourceDetailsByEngmtKey(final ClientEngagementDTO engagementDto) throws AppException {
        logger.info("Start: ManageEngagementsDAOImpl : getClientEngagementSourceDetailsByEngmtKey");
        String engagementSourceProc = "{CALL Get_ClntRelEngmtIndenByID(?)}";
        List<ClientEngagementSourceDTO> sourceList = new ArrayList<>();
        try {
            List<Map<String, Object>> resultListForSourceDTO = jdbcTemplate.queryForList(engagementSourceProc, new Object[]{engagementDto.getEngagementCode()});
            if (!resultListForSourceDTO.isEmpty()) {
                for (Map<String, Object> resultMap : resultListForSourceDTO) {
                    ClientEngagementSourceDTO clientEngagementSourceDTO = new ClientEngagementSourceDTO();
                    clientEngagementSourceDTO.setClientEngSourceCode(resultMap.get("SRC_REL_ENGMT_ID") + "");
                    clientEngagementSourceDTO.setSourceKey((Integer) resultMap.get("SRC_KEY"));
                    clientEngagementSourceDTO.setClientEngSourceName(resultMap.get("ServiceName") + "");
                    sourceList.add(clientEngagementSourceDTO);
                }
                engagementDto.setClientEngagementSourceLi(sourceList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : getClientEngagementSourceDetailsByEngmtKey : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "getClientEngagementSourceDetailsByEngmtKey() :: " + e.getMessage());
        }
        logger.info("End: ManageEngagementsDAOImpl : getClientEngagementSourceDetailsByEngmtKey");
        return engagementDto;
    }

    /**
     * Used to update engagement details based on engagement code
     *
     * @param clientEngagementDTO
     * @param userSessionDto
     * @return
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int updateEngagementInfo(ClientEngagementDTO clientEngagementDTO, UserProfileDTO userSessionDto) throws AppException {
        logger.info("Start: ManageEngagementsDAOImpl : updateEngagementInfo");
        int returnVal = 0;
        try {
            String updateEngProc = "{CALL UPDATE_CLNT_ENGMT(?,?,?,?,?,?,?,?,?)}";
            returnVal = jdbcTemplate.queryForObject(updateEngProc,
                    new Object[]{clientEngagementDTO.getEngagementCode(), clientEngagementDTO.getEngagementStatusKey(),
                        ApplicationConstants.DB_ROW_STATUS_ACTIVE_VALUE, clientEngagementDTO.getEngagementName(),
                        clientEngagementDTO.getEstimatedStartDate(), clientEngagementDTO.getEstimatedEndDate(),
                        clientEngagementDTO.getEngagementDesc(),
                        //clientEngagementDTO.getEngagementStatusComment(),
                        !"".equalsIgnoreCase(clientEngagementDTO.getEngagementStatusComment()) ? clientEngagementDTO.getEngagementStatusComment() : null,
                        userSessionDto.getUserProfileKey()}, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : updateEngagementInfo : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "updateEngagementInfo() :: " + e.getMessage());
        }
        logger.info("End: ManageEngagementsDAOImpl : updateEngagementInfo");
        return returnVal;
    }

    /**
     * Used to delete engagement services based on engagement code
     *
     * @param engagementKey
     * @param flag
     * @return
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int deleteEngagementServices(String engagementKey, List<String> servicesToDelete, String flag) throws AppException {
        logger.info("Start: ManageEngagementsDAOImpl : deleteEngagementServices");
        int returnVal = 0;
        try {
            String deleteEngagementProc = "{CALL DEL_CLNTSECURSRVCENGMT(?,?,?,?)}";
            for (String service : servicesToDelete) {
                returnVal = jdbcTemplate.queryForObject(deleteEngagementProc, new Object[]{engagementKey,
                    service, 0, flag}, Integer.class); //0 is user id as updated proc with added new parameter
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : deleteEngagementServices : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "deleteEngagementServices() :: " + e.getMessage());
        }
        logger.info("End: ManageEngagementsDAOImpl : deleteEngagementServices");
        return returnVal;
    }

    /**
     * Used to delete engagement users(Client, Internal and Partner) based on
     * engagement code
     *
     * @param assignKey
     * @return
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int deleteEngagementUsers(String assignKey) throws AppException {
        logger.info("Start: ManageEngagementsDAOImpl : deleteEngagementUsers");
        int returnVal = 0;
        try {
            String deleteEngagementProc = "{CALL DelClntEngmtUsrAssgnyID(?)}";
            returnVal = jdbcTemplate.queryForObject(deleteEngagementProc, new Object[]{assignKey}, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : deleteEngagementUsers : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "deleteEngagementUsers() :: " + e.getMessage());
        }
        logger.info("End: ManageEngagementsDAOImpl : deleteEngagementUsers");
        return returnVal;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int deleteServiceUsers(String assignKey) throws AppException {
        logger.info("Start: ManageEngagementsDAOImpl : deleteServiceUsers");
        int returnVal = 0;
        try {
            String deleteEngagementProc = "{CALL DEL_USER_CLNT_SRVC_ASGN(?)}";
            returnVal = jdbcTemplate.queryForObject(deleteEngagementProc, new Object[]{assignKey}, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : deleteServiceUsers : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "deleteServiceUsers() :: " + e.getMessage());
        }
        logger.info("End: ManageEngagementsDAOImpl : deleteServiceUsers");
        return returnVal;
    }

    /**
     * Used to delete engagement source system and source project id based on
     * engagement code
     *
     * @param engagementKey
     * @param sessionDTO
     * @return
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int deleteEngagementSourceInfo(String engagementKey, UserProfileDTO sessionDTO) throws AppException {
        logger.info("Start: ManageEngagementsDAOImpl : deleteEngagementSourceInfo");
        int returnVal = 0;
        try {
            String deleteEngagementProc = "{CALL DEL_CLNTRELENGMTINDENTIFIER(?)}";
            returnVal = jdbcTemplate.queryForObject(deleteEngagementProc, new Object[]{engagementKey}, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : deleteEngagementSourceInfo : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "deleteEngagementSourceInfo() :: " + e.getMessage());
        }
        logger.info("End: ManageEngagementsDAOImpl : deleteEngagementSourceInfo");
        return returnVal;
    }

    /**
     * used to get the engagement code count based on Organization name, Package
     * and agreement date
     *
     * @param engagementDTO
     * @return
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public int getEngagementCountForEngCode(ClientEngagementDTO engagementDTO) throws AppException {
        logger.info("Start: ManageEngagementsDAOImpl : getEngagementCountForEngCode");
        int engCount = 0;
        try {
            String engCountProc = "{CALL GetEngagementCount(?,?,?)}";
            List<Map<String, Object>> engCountMap = jdbcTemplate.queryForList(engCountProc,
                    new Object[]{engagementDTO.getClientID(),
                        engagementDTO.getSecurityPackageObj().getSecurityPackageCode(),
                        engagementDTO.getAgreementDate()
                    });

            for (Map<String, Object> engagementCountObj : engCountMap) {
                engCount = (Integer) engagementCountObj.get("COUNT");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : getEngagementCountForEngCode : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "getEngagementCountForEngCode() :: " + e.getMessage());
        }
        logger.info("End: ManageEngagementsDAOImpl : getEngagementCountForEngCode");
        return engCount;
    }

    /**
     * Updating the engagement status to Inactive. This method changes the
     * engagement services, users also to inactive.
     *
     * @param clientEngagementDTO
     * @param sessionDTO
     * @return
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int deleteEngagement(ClientEngagementDTO clientEngagementDTO, UserProfileDTO sessionDTO) throws AppException {
        logger.info("Start: ManageEngagementsDAOImpl : deleteEngagement");
        int returnVal = 0;
        try {
            String deleteEngagementProc = "{CALL UPDATE_EngagementStatus(?,?)}";
            returnVal = jdbcTemplate.queryForObject(deleteEngagementProc,
                    new Object[]{clientEngagementDTO.getEngagementCode(),
                    ApplicationConstants.DB_ROW_STATUS_DELETE_VALUE}, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : deleteEngagement : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "deleteEngagement() :: " + e.getMessage());
        }
        logger.info("End: ManageEngagementsDAOImpl : deleteEngagement");
        return returnVal;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int activateEngagement(String clientEngCode) throws AppException {
        logger.info("Start: ManageEngagementsDAOImpl : activateEngagement");
        int returnVal = 0;
        try {
            String deleteEngagementProc = "{CALL UPDATE_EngagementStatus(?,?)}";
            returnVal = jdbcTemplate.queryForObject(deleteEngagementProc,
                    new Object[]{clientEngCode, 
                        ApplicationConstants.DB_ROW_STATUS_ACTIVE_VALUE}, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : activateEngagement : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "deleteEngagement() :: " + e.getMessage());
        }
        logger.info("End: ManageEngagementsDAOImpl : activateEngagement");
        return returnVal;
    }

    /**
     * This method for fetching assigned users to services
     *
     * @param engagementDTO
     * @return ClientEngagementDTO
     * @throws AppException
     */
    @Override
    public List<ManageServiceUserDTO> fetchServiceUsers(final ClientEngagementDTO engagementDTO) throws AppException {
        logger.info("Start: ManageEngagementsDAOImpl : fetchServiceUsers");
        String fetchAssignedUsers = "{CALL LIST_UserClntSrvcAssn(?,?,?,?,?)}";
        List<ManageServiceUserDTO> srvUsersList = new ArrayList<>();
        try {
            List<Map<String, Object>> resultListForSourceDTO = jdbcTemplate.queryForList(fetchAssignedUsers, new Object[]{
                engagementDTO.getEngagementCode(),
                0, //user type key
                "", //user name
                "", //user start date
                "" //user end date
            });
            if (!resultListForSourceDTO.isEmpty()) {
                for (Map<String, Object> resultMap : resultListForSourceDTO) {
                    if (null != resultMap.get("ORG_TYP_KEY")){
                        ManageServiceUserDTO manageServiceUserDto = new ManageServiceUserDTO();
                        manageServiceUserDto.setManageServiceUserKey((Integer) resultMap.get("USER_CLNT_SRVC_ASGN_KEY"));
                        manageServiceUserDto.setUserKey((Integer) resultMap.get("USER_ID"));
                        manageServiceUserDto.setUserName(resultMap.get("USERNAME") + "");
                        manageServiceUserDto.setRowStatusKey((Integer) resultMap.get("ROW_STS_KEY"));
                        manageServiceUserDto.setStartDate(dateUtil.dateConvertionFromDBToJSP(resultMap.get("USER_STRT_DT") + ""));
                        manageServiceUserDto.setEndDate(dateUtil.dateConvertionFromDBToJSP(resultMap.get("USER_END_DT") + ""));
                        if (null != resultMap.get("ORG_TYP_KEY") && !"null".equalsIgnoreCase(resultMap.get("ORG_TYP_KEY") + "")) {
                            manageServiceUserDto.setUserTypeKey((Integer) resultMap.get("ORG_TYP_KEY"));
                        }
                        manageServiceUserDto.setUserTypeValue(resultMap.get("UserType") + "");
                        manageServiceUserDto.setOrganizationName(resultMap.get("ORG_NM") + "");
                        if (null != resultMap.get("ORG_KEY") && !"null".equalsIgnoreCase(resultMap.get("ORG_KEY") + "")) {
                            manageServiceUserDto.setOrganizationKey((Integer) resultMap.get("ORG_KEY"));
                        }
                        manageServiceUserDto.setSecureServiceCode(resultMap.get("SECUR_SRVC_CD") + "");
                        manageServiceUserDto.setServiceName(resultMap.get("SECUR_SRVC_NM") + "");
                        manageServiceUserDto.setRoleName(resultMap.get("APPL_ROLE_NM") + ""); 
                        manageServiceUserDto.setRoleKey((Integer) resultMap.get("APPL_ROLE_KEY"));
                        srvUsersList.add(manageServiceUserDto);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : fetchServiceUsers : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "fetchServiceUsers() :: " + e.getMessage());
        }
        logger.info("End: ManageEngagementsDAOImpl : fetchServiceUsers");
        return srvUsersList;
    }
    
    @Override
    public int saveServiceUser(final UserProfileDTO serviceUserObj, final UserProfileDTO sessionUserDTO, String engagementCode) throws AppException {
        logger.info("Start: ManageEngagementsDAOImpl : saveServiceUser");
        String saveManageUser = "{CALL INS_USER_CLNT_SRVC_ASGN(?,?,?,?,?,?,?,?,?)}";
        int retVal = 0;
        try {
            List<String> serviceCodesList = Arrays.asList(serviceUserObj.getSelServCode().split("\\s*,\\s*"));
            //GET USER APP ROLE KEY
            long userAppRoleKey = getUserAppRoleKey(serviceUserObj.getUserProfileKey(), serviceUserObj.getAppRoleKey());
            for (String serviceCode : serviceCodesList) {
                if(!StringUtils.isEmpty(serviceCode)){
                    retVal = jdbcTemplate.queryForObject(saveManageUser, new Object[]{
                        ApplicationConstants.DB_ROW_STATUS_ACTIVE_VALUE, //Row Status Key
                        serviceUserObj.getUserProfileKey(),
                        serviceUserObj.getServiceDto().getServiceStartDate(),
                        serviceUserObj.getServiceDto().getServiceEndDate(),
                        sessionUserDTO.getUserProfileKey(),
                        serviceCode,
                        engagementCode,
                        userAppRoleKey,
                        serviceUserObj.getAppRoleKey()
                    }, Integer.class);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : saveOrUpdateManageUserToService : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "saveOrUpdateManageUserToService() :: " + e.getMessage());
        }
        logger.info("End: ManageEngagementsDAOImpl : saveOrUpdateManageUserToService");
        return retVal;
    }

    /**
     * This method for save or update manage user to service
     *
     * @param manageServiceUserDTO
     * @param sessionUserDTO
     * @return retVal
     * @throws AppException
     */
    @Override
    public int saveOrUpdateManageUserToService(final ManageServiceUserDTO manageServiceUserDTO, final UserProfileDTO sessionUserDTO) throws AppException {
        logger.info("Start: ManageEngagementsDAOImpl : saveOrUpdateManageUserToService");
        String saveManageUser = "{CALL INS_USER_CLNT_SRVC_ASGN(?,?,?,?,?,?,?)}";
        String updateManageUserProc = "{CALL UPDATE_USERCLNTSRVCASGN(?,?,?,?,?,?,?,?)}";
        int retVal = 0;
        try {
            if (manageServiceUserDTO.getManageServiceUserKey() <= 0) {
                retVal = jdbcTemplate.queryForObject(saveManageUser, new Object[]{
                    1, //Row Status Key
                    manageServiceUserDTO.getUserKey(),
                    manageServiceUserDTO.getStartDate(),
                    manageServiceUserDTO.getEndDate(),
                    manageServiceUserDTO.getCreatedByUserID(),
                    manageServiceUserDTO.getSecureServiceCode(),
                    manageServiceUserDTO.getClientEngagementCode()
                }, Integer.class);
                if (retVal > 0) {
                    //Fetch user details of assigned user
                    UserProfileDTO assignedAnalystUserDto = userManagementServiceImpl.fetchUserInfoByUserID(manageServiceUserDTO.getUserKey());
                    //Mail sent to assigned user
                    //clientEngagementMailHelper.mailToAssignedUser(assignedAnalystUserDto, manageServiceUserDTO, sessionUserDTO);
                }
            } else if (manageServiceUserDTO.getManageServiceUserKey() > 0) {
                retVal = jdbcTemplate.queryForObject(updateManageUserProc, new Object[]{
                    manageServiceUserDTO.getManageServiceUserKey(),
                    manageServiceUserDTO.getUserKey(),
                    manageServiceUserDTO.getStartDate(),
                    manageServiceUserDTO.getEndDate(),
                    manageServiceUserDTO.getSecureServiceCode(),
                    manageServiceUserDTO.getClientEngagementCode(),
                    manageServiceUserDTO.getRowStatusKey(),
                    manageServiceUserDTO.getCreatedByUserID()
                }, Integer.class);
                if (retVal > 0) {
                    //Fetch user details of previous assigned user details
                    UserProfileDTO oldAssignedUserDto = userManagementServiceImpl.fetchUserInfoByUserID(manageServiceUserDTO.getOldAssignedUserKey());
                    //Fetch user details of reassigned user details
                    UserProfileDTO reassignedAnalystUserDto = userManagementServiceImpl.fetchUserInfoByUserID(manageServiceUserDTO.getUserKey());
                    //Mail sent to previous assigned user
                    clientEngagementMailHelper.mailToReAssignedUser(reassignedAnalystUserDto, manageServiceUserDTO, sessionUserDTO, oldAssignedUserDto);
                    //Mail sent to reassigned user
                    //clientEngagementMailHelper.mailToAssignedUser(reassignedAnalystUserDto, manageServiceUserDTO, sessionUserDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : saveOrUpdateManageUserToService : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "saveOrUpdateManageUserToService() :: " + e.getMessage());
        }
        logger.info("End: ManageEngagementsDAOImpl : saveOrUpdateManageUserToService");
        return retVal;
    }

    /**
     * This method for search or refresh assigned users for internal view
     *
     * @param manageServiceUserDTO
     * @param clientEngagementDTO
     * @return ClientEngagementDTO
     * @throws AppException
     */
    @Override
    public ClientEngagementDTO searchAssignedUsersToService(final ManageServiceUserDTO manageServiceUserDTO, final ClientEngagementDTO clientEngagementDTO) throws AppException {
        logger.info("Start: ManageEngagementsDAOImpl : searchAssignedUsersToService");
        String fetchAssignedUsers = "{CALL LIST_UserClntSrvcAssn(?,?,?,?,?,?,?)}";
        List<SecurityServiceDTO> serviceList = new ArrayList<>();
        Map<String, SecurityServiceDTO> fileMap = new LinkedHashMap<>();
        try {
            List<Map<String, Object>> resultListForSourceDTO = jdbcTemplate.queryForList(fetchAssignedUsers, new Object[]{
                clientEngagementDTO.getClientEngagementKey(),//user type key internal or partner
                manageServiceUserDTO.getClientEngagementCode(),
                manageServiceUserDTO.getCreatedByUserID(),
                manageServiceUserDTO.getUserTypeKey(),
                manageServiceUserDTO.getUserName(),
                manageServiceUserDTO.getStartDate(),
                manageServiceUserDTO.getEndDate()
            });
            if (!resultListForSourceDTO.isEmpty()) {
                for (Map<String, Object> resultMap : resultListForSourceDTO) {
                    if (fileMap.containsKey(resultMap.get("SECUR_SRVC_NM") + "")) {
                        SecurityServiceDTO serviceDto = fileMap.get(resultMap.get("SECUR_SRVC_NM") + "");
                        List<ManageServiceUserDTO> manageUserList = serviceDto.getManageServiceUserLi();
                        if ((Integer) resultMap.get("USER_CLNT_SRVC_ASGN_KEY") > 0) {
                            ManageServiceUserDTO manageServiceUserDto = new ManageServiceUserDTO();
                            manageServiceUserDto.setManageServiceUserKey((Integer) resultMap.get("USER_CLNT_SRVC_ASGN_KEY"));
                            manageServiceUserDto.setUserKey((Integer) resultMap.get("USER_ID"));
                            manageServiceUserDto.setUserName(resultMap.get("USERNAME") + "");
                            manageServiceUserDto.setRowStatusKey((Integer) resultMap.get("ROW_STS_KEY"));
                            manageServiceUserDto.setStartDate(dateUtil.dateConvertionFromDBToJSP(resultMap.get("USER_STRT_DT") + ""));
                            manageServiceUserDto.setEndDate(dateUtil.dateConvertionFromDBToJSP(resultMap.get("USER_END_DT") + ""));
                            if (null != resultMap.get("ORG_TYP_KEY") && !"null".equalsIgnoreCase(resultMap.get("ORG_TYP_KEY") + "")) {
                                manageServiceUserDto.setUserTypeKey((Integer) resultMap.get("ORG_TYP_KEY"));
                            }
                            manageServiceUserDto.setUserTypeValue(resultMap.get("UserType") + "");
                            manageServiceUserDto.setOrganizationName(resultMap.get("ORG_NM") + "");
                            if (null != resultMap.get("ORG_KEY") && !"null".equalsIgnoreCase(resultMap.get("ORG_KEY") + "")) {
                                manageServiceUserDto.setOrganizationKey((Integer) resultMap.get("ORG_KEY"));
                            }
                            manageServiceUserDto.setServiceName(resultMap.get("SECUR_SRVC_NM") + "");
                            manageUserList.add(manageServiceUserDto);
                        }
                        serviceDto.setManageServiceUserLi(manageUserList);
                        serviceDto.setEncSecurityServiceKey(resultMap.get("UserType") + "");
                        if (manageUserList.size() > 0 || manageServiceUserDTO.getUserTypeKey() == 0) {
                            fileMap.put(resultMap.get("SECUR_SRVC_NM") + "", serviceDto);
                        }
                    } else {
                        List<ManageServiceUserDTO> manageUserList = new ArrayList<>();
                        if ((Integer) resultMap.get("USER_CLNT_SRVC_ASGN_KEY") > 0) {
                            ManageServiceUserDTO manageServiceUserDto = new ManageServiceUserDTO();
                            manageServiceUserDto.setManageServiceUserKey((Integer) resultMap.get("USER_CLNT_SRVC_ASGN_KEY"));
                            manageServiceUserDto.setUserKey((Integer) resultMap.get("USER_ID"));
                            manageServiceUserDto.setUserName(resultMap.get("USERNAME") + "");
                            manageServiceUserDto.setRowStatusKey((Integer) resultMap.get("ROW_STS_KEY"));
                            manageServiceUserDto.setStartDate(dateUtil.dateConvertionFromDBToJSP(resultMap.get("USER_STRT_DT") + ""));
                            manageServiceUserDto.setEndDate(dateUtil.dateConvertionFromDBToJSP(resultMap.get("USER_END_DT") + ""));
                            if (null != resultMap.get("ORG_TYP_KEY") && !"null".equalsIgnoreCase(resultMap.get("ORG_TYP_KEY") + "")) {
                                manageServiceUserDto.setUserTypeKey((Integer) resultMap.get("ORG_TYP_KEY"));
                            }
                            manageServiceUserDto.setUserTypeValue(resultMap.get("UserType") + "");
                            manageServiceUserDto.setOrganizationName(resultMap.get("ORG_NM") + "");
                            if (null != resultMap.get("ORG_KEY") && !"null".equalsIgnoreCase(resultMap.get("ORG_KEY") + "")) {
                                manageServiceUserDto.setOrganizationKey((Integer) resultMap.get("ORG_KEY"));
                            }
                            manageServiceUserDto.setServiceName(resultMap.get("SECUR_SRVC_NM") + "");
                            manageUserList.add(manageServiceUserDto);
                        }
                        SecurityServiceDTO serviceDto = new SecurityServiceDTO();
                        serviceDto.setSecurityServiceCode(resultMap.get("SECUR_SRVC_CD") + "");
                        serviceDto.setSecurityServiceName(resultMap.get("SECUR_SRVC_NM") + "");
                        serviceDto.setServiceStartDate(dateUtil.dateConvertionFromDBToJSP(resultMap.get("SRVC_EST_STRT_DT") + ""));
                        serviceDto.setServiceEndDate(dateUtil.dateConvertionFromDBToJSP(resultMap.get("SRVC_EST_END_DT") + ""));
                        //serviceDto.setCreatedByUserID((Integer) resultMap.get("ORG_KEY"));
                        serviceDto.setEncSecurityServiceKey(resultMap.get("UserType") + "");
                        serviceDto.setManageServiceUserLi(manageUserList);
                        if (manageUserList.size() > 0 || (manageServiceUserDTO.getUserTypeKey() != 0 && clientEngagementDTO.getClientEngagementKey() != 18)) {
                            fileMap.put(resultMap.get("SECUR_SRVC_NM") + "", serviceDto);
                        }
                    }
                }
                for (Map.Entry<String, SecurityServiceDTO> entry : fileMap.entrySet()) {
                    SecurityServiceDTO entryDto = entry.getValue();
                    serviceList.add(entryDto);
                }
            }
            clientEngagementDTO.setEngagementServiceLi(serviceList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : searchAssignedUsersToService : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "searchAssignedUsersToService() :: " + e.getMessage());
        }
        logger.info("End: ManageEngagementsDAOImpl : searchAssignedUsersToService");
        return clientEngagementDTO;
    }

    /**
     * Used to update engagement service dates when updating the engagement
     *
     * @param clientEngagementDTO
     * @param sessionDTO
     * @param servicesToUpdate
     * @return
     * @throws AppException
     */
    @Override
    public int updateEngagementServices(ClientEngagementDTO clientEngagementDTO,
            UserProfileDTO sessionDTO, List<String> servicesToUpdate) throws AppException {
        logger.info("Begin: ManageEngagementsDAOImpl : updateEngagementServices");
        int returnVal = 0;
        try {
            List<SecurityServiceDTO> servicesList = clientEngagementDTO.getEngagementServiceLi();
            String updateServiceProc = "{CALL UPDATE_EngagementServicedatebyEngservcode(?,?,?,?)}";

            for (SecurityServiceDTO securityServiceDTO : servicesList) {
                if (servicesToUpdate.contains(securityServiceDTO.getSecurityServiceCode())) {
                    returnVal = jdbcTemplate.queryForObject(updateServiceProc,
                            new Object[]{clientEngagementDTO.getEngagementCode(),
                                securityServiceDTO.getSecurityServiceCode(),
                                securityServiceDTO.getServiceStartDate(),
                                securityServiceDTO.getServiceEndDate()
                            }, Integer.class);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : updateEngagementServices : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "updateEngagementServices() :: " + e.getMessage());
        }
        logger.info("End: ManageEngagementsDAOImpl : updateEngagementServices");
        return returnVal;
    }

    /**
     * Used to delete partner users from child tables
     *
     * @param engagementKey
     * @param partnerUserIds
     * @return
     * @throws AppException
     */
    @Override
    public int deleteEngagementPartnerUsers(String engagementKey, String partnerUserIds) throws AppException {
        logger.info("Start: ManageEngagementsDAOImpl : deleteEngagementPartnerUsers");
        int returnVal = 0;
        try {
            String deletePartnerUsersProc = "{CALL DEL_PartnerAnlistByEngCode(?,?)}";
            returnVal = jdbcTemplate.queryForObject(deletePartnerUsersProc, new Object[]{engagementKey,
                partnerUserIds}, Integer.class);

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : deleteEngagementPartnerUsers : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "deleteEngagementPartnerUsers() :: " + e.getMessage());
        }
        logger.info("End: ManageEngagementsDAOImpl : deleteEngagementPartnerUsers");
        return returnVal;
    }
    
    /**
     * This method for delete assigned analyst user to service in Manage Users
     * to Service page
     *
     * @param manageServiceUserDTO
     * @return int
     * @throws AppException
     */
    @Override
    public int deleteAssignedUserToService(final ManageServiceUserDTO manageServiceUserDTO) throws AppException {
        logger.info("Start: ManageEngagementsDAOImpl : deleteAssignedUserToService");
        int returnVal = 0;
        try {
            String deleteAnalystUserProc = "{CALL DEL_CLNTSECURSRVCENGMT(?,?,?,?)}";
            returnVal = jdbcTemplate.queryForObject(deleteAnalystUserProc, new Object[]{
                manageServiceUserDTO.getClientEngagementCode(),
                manageServiceUserDTO.getSecureServiceCode(),
                manageServiceUserDTO.getUserKey(),
                "U"
            }, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : deleteAssignedUserToService : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "deleteAssignedUserToService() :: " + e.getMessage());
        }
        logger.info("End: ManageEngagementsDAOImpl : deleteAssignedUserToService");
        return returnVal;
    }
    
    /**
     * This method to fetch mapped analyst users based on engagement code and
     * user type
     *
     * @param userType
     * @param engkey
     * @return List<String>
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<String> fetchMappedAnalystUsersByEngkeyAndUserTypeID(String userType, final String engkey) throws AppException {
        logger.info("Start: ManageEngagementsDAOImpl : fetchMappedUsersByEngkeyAndUserTypeID");
        List<String> mappedAnalystUsers = null;
        try {
            mappedAnalystUsers = new ArrayList<>();
            String mappedUsersProc = "{CALL GetMappedAnalystsByEngmtCode(?,?)}";
            List<Map<String, Object>> userList = jdbcTemplate.queryForList(mappedUsersProc,
                    new Object[]{userType, engkey});

            for (Map<String, Object> userMap : userList) {
                String userName = userMap.get("User Name") + "";
                mappedAnalystUsers.add(userName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : fetchMappedUsersByEngkeyAndUserTypeID : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "fetchMappedUsersByEngkeyAndUserTypeID() :: " + e.getMessage());
        }
        logger.info("End: ManageEngagementsDAOImpl : fetchMappedUsersByEngkeyAndUserTypeID");
        return mappedAnalystUsers;
    }

    /**
     * This method to fetch mapped analyst users based on service
     *
     * @param engKey
     * @param userType
     * @param serviceCode
     * @param flag
     * @return List<String>
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<String> fetchMappedAnalystUsersByService(final String engKey, final String userType, final String serviceCode, final String flag)
            throws AppException {
        logger.info("Start: ManageEngagementsDAOImpl : fetchMappedAnalystUsersByService");
        List<String> mappedAnalystUsers = null;
        try {
            mappedAnalystUsers = new ArrayList<>();
            String mappedAnalystUsersProc = "{CALL List_UserByService(?,?,?,?)}";
            List<Map<String, Object>> userList = jdbcTemplate.queryForList(mappedAnalystUsersProc,
                    new Object[]{engKey, userType, serviceCode, flag});

            for (Map<String, Object> userMap : userList) {
                String userName = userMap.get("User Name") + "";
                mappedAnalystUsers.add(userName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : fetchMappedAnalystUsersByService : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "fetchMappedAnalystUsersByService() :: " + e.getMessage());
        }
        logger.info("End: ManageEngagementsDAOImpl : fetchMappedAnalystUsersByService");
        return mappedAnalystUsers;
    }
}
