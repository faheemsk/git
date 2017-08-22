/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao.impl;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dao.EngagementDataUploadDAO;
import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.dto.ClientEngagementSourceDTO;
import com.optum.oss.dto.ManageServiceUserDTO;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.util.DateUtil;
import com.optum.oss.util.EncryptDecrypt;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author akeshavulu
 */
public class EngagementDataUploadDAOImpl implements EngagementDataUploadDAO {
    
    private static final Logger logger = Logger.getLogger(EngagementDataUploadDAOImpl.class);
    
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
    @Autowired
    private EncryptDecrypt encryptDecrypt;
    
    @Autowired
    private DateUtil dateUtil;
    
    @Override
    public ClientEngagementDTO fetchServiceEngagementDetailsByEgmntCode(String engagementCode, String serviceCode, long userID) throws AppException {
        ClientEngagementDTO clientEngagementDTO = null;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String updatedDate = "";
        String serviceEstStartDate = "";
        String serviceEstEndDate = "";
        String agreementDate = "";
        try {
            String serviceDetailsProc = "{CALL Get_EngageServiceByServiceID(?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(serviceDetailsProc, new Object[]{engagementCode, serviceCode, userID});
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    clientEngagementDTO = new ClientEngagementDTO();
                    clientEngagementDTO.setClientName((resultMap.get("ORG_NM") + ""));
                    clientEngagementDTO.setClientID(Long.parseLong(resultMap.get("CLNT_ORG_KEY") + ""));
                    clientEngagementDTO.setEngagementCode((resultMap.get("CLNT_ENGMT_CD") + ""));
                    clientEngagementDTO.setEngagementName((resultMap.get("CLNT_ENGMT_NM") + ""));
                    clientEngagementDTO.getSecurityPackageObj().setSecurityPackageName(resultMap.get("SECUR_PKG_NM") + "");
                    clientEngagementDTO.setSecurityServiceName((resultMap.get("SECUR_SRVC_NM") + ""));
                    clientEngagementDTO.setSecurityServiceCode((resultMap.get("SECUR_SRVC_CD") + ""));
                    if (ApplicationConstants.DB_CONSTANT_FILE_UNLOCK_INDICATOR == Long.parseLong(resultMap.get("FL_LCK_IND") + "")) {
                        clientEngagementDTO.setLockCheckbox(false);
                    } else {
                        clientEngagementDTO.setLockCheckbox(true);
                    }
                    if (null != resultMap.get("SRVC_EST_STRT_DT")) {
                        serviceEstStartDate = format.format(resultMap.get("SRVC_EST_STRT_DT"));
                    }
                    if (null != resultMap.get("SRVC_EST_END_DT")) {
                        serviceEstEndDate = format.format(resultMap.get("SRVC_EST_END_DT"));
                    }
                    if (null != resultMap.get("AGR_DT")) {
                        agreementDate = format.format(resultMap.get("AGR_DT"));
                    }
                    clientEngagementDTO.setEstimatedStartDate(serviceEstStartDate);
                    clientEngagementDTO.setEstimatedEndDate(serviceEstEndDate);
                    clientEngagementDTO.setAgreementDate(agreementDate);
                    
                    clientEngagementDTO.setServiceLockFlag(Long.parseLong(resultMap.get("FL_LCK_IND") + ""));
                    clientEngagementDTO.setCreatedByUserID(userID);
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : fetchServiceEngagementDetailsByEgmntCode:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "fetchServiceEngagementDetailsByEgmntCode() :: " + e.getMessage());
        }
        
        return clientEngagementDTO;
        
    }
    // A.CLNT_ENGMT_CD,A.SECUR_SRVC_CD,A.ROW_STS_KEY,A.SRVC_ENGMT_STS_KEY,A.SRVC_EST_STRT_DT,A.SRVC_EST_END_DT,A.FL_LCK_IND,A.CREAT_DT,D.ORG_NM,E.CLNT_ENGMT_NM

    @Override
    public int saveEngagementDataUploadDetails(ClientEngagementDTO clientEngmtDTO) throws AppException {
        int retVal = 0;
        String serviceCode = "";
        int fileStatus = 0;
        try {
            if (System.getProperty("ECG_UPLOAD") != null) {
                if ("Yes".equalsIgnoreCase(System.getProperty("ECG_UPLOAD"))) {
                    fileStatus = ApplicationConstants.DB_CONSTANT_FILE_STATUS_KEY_SCAN_INPROGRESS;
                } else {
                    fileStatus = ApplicationConstants.DB_CONSTANT_FILE_STATUS_KEY_NEW;
                }
            }
            String saveUploadFileDetailsprocName = "{CALL INS_APPL_FL_UPLOAD_LOG(?,?,?,?,?,?,?,?,?,?,?,?)}";
            retVal = jdbcTemplate.queryForObject(saveUploadFileDetailsprocName, new Object[]{ApplicationConstants.DB_ROW_STATUS_ACTIVE_VALUE,
                clientEngmtDTO.getClientID(), clientEngmtDTO.getEngagementCode(), clientEngmtDTO.getSecurityServiceCode(),
                clientEngmtDTO.getSourceNameKey(), clientEngmtDTO.getDocumentTypeKey(), clientEngmtDTO.getCreatedByUserID(),
                clientEngmtDTO.getUploadFileName(), clientEngmtDTO.getUploadFilePath(),
                !"".equalsIgnoreCase(clientEngmtDTO.getUploadComments())?clientEngmtDTO.getUploadComments():null, 
                fileStatus,String.valueOf(clientEngmtDTO.getUploadFileSize())}, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : saveEngagementDataUploadDetails:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "saveEngagementDataUploadDetails() :: " + e.getMessage());
        }
        return retVal;
    }

//                        A.ORG_KEY,ORG_NM,CLNT_ENGMT_CD,SECUR_SRVC_CD,
//			A.SRC_KEY,[dbo].[fnGetMasterLkpNameByID](A.SRC_KEY)SourceName,
//			A.DOC_TYP_KEY,[dbo].[fnGetMasterLkpNameByID](A.DOC_TYP_KEY) DocumentType,
//			A.UPLOAD_USER_ID,[dbo].[fnGetUserNameByID](A.UPLOAD_USER_ID) UploadedUser,
//			FL_NM,FL_FLDR_PTH,FL_COMMT,FL_UPLOAD_DT
    @Override
    public List<ClientEngagementDTO> fetchUploadedFilesDetails(ClientEngagementDTO clientEngagementDTO) throws AppException {
        ClientEngagementDTO clientEngmntDTO = null;
        List<ClientEngagementDTO> uploadedFilesList = new ArrayList<ClientEngagementDTO>();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String updatedDate = "";
        String tempFilePath = "";
        try {
            String uploadedFilesListProc = "{CALL Get_UploadedFiles(?,?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(uploadedFilesListProc, new Object[]{clientEngagementDTO.getEngagementCode(),
                clientEngagementDTO.getSecurityServiceCode(), clientEngagementDTO.getCreatedByUserID(), clientEngagementDTO.getUserTypeFlag()});
            //  List<Map<String, Object>> resultList = jdbcTemplate.queryForList(uploadedFilesListProc, new Object[]{"ABC-HC-20160518_1", "AV", 8});
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    clientEngmntDTO = new ClientEngagementDTO();
                    clientEngmntDTO.setSourceName((resultMap.get("SourceName") + ""));
                    clientEngmntDTO.setDocumentType((resultMap.get("DocumentType") + ""));
                    clientEngmntDTO.setUploadFileName((resultMap.get("FL_NM") + ""));
                    clientEngmntDTO.setUploadFilePath((resultMap.get("FL_FLDR_PTH") + ""));
                    String encString = clientEngmntDTO.getUploadFileName()+"@"+clientEngmntDTO.getUploadFilePath();
                    clientEngmntDTO.setEncFileDownload(encryptDecrypt.encrypt(encString));
                    tempFilePath = clientEngmntDTO.getUploadFilePath().replace('\\', '~');
                    clientEngmntDTO.setTempFilePath(tempFilePath);
                    clientEngmntDTO.setFileUploadID(Long.parseLong(resultMap.get("APPL_FL_UPLOAD_LOG_KEY") + ""));
                    clientEngmntDTO.setEncfileUploadID(encryptDecrypt.encrypt(resultMap.get("APPL_FL_UPLOAD_LOG_KEY") + ""));
                    if (null != resultMap.get("FL_UPLOAD_DT")) {
                        updatedDate = format.format(resultMap.get("FL_UPLOAD_DT"));
                    }
                    clientEngmntDTO.setModifiedDate(updatedDate);
                    clientEngmntDTO.setModifiedByUser(resultMap.get("UploadedUser") + "");
                    clientEngmntDTO.setFileStatusKey(Long.parseLong(resultMap.get("FL_STS_KEY") + ""));
                    clientEngmntDTO.setUploadFileSize(Long.parseLong(resultMap.get("FL_SZ")+""));
                    uploadedFilesList.add(clientEngmntDTO);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : fetchUploadedFilesDetails:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "fetchUploadedFilesDetails() :: " + e.getMessage());
        }
        return uploadedFilesList;
    }
    
    @Override
    public int deleteUploadedFilesDetails(int fileID) throws AppException {
        int retVal = 0;
        try {
            String saveUploadFileDetailsprocName = "{CALL DEL_UploadfilebyID(?)}";
            
            retVal = jdbcTemplate.queryForObject(saveUploadFileDetailsprocName, new Object[]{fileID}, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : saveEngagementDataUploadDetails:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "saveEngagementDataUploadDetails() :: " + e.getMessage());
        }
        return retVal;
    }
    
    @Override
    public int updateLockUnlockServiceData(String engagementCode, String serviceCode, long lockIndicator) throws AppException {
        
        int retVal = 0;
        try {
            String updateLockUnlockprocName = "{CALL UPDATE_LockUnlockService(?,?,?)}";
            
            retVal = jdbcTemplate.queryForObject(updateLockUnlockprocName, new Object[]{engagementCode, serviceCode, lockIndicator}, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : updateLockUnlockServiceData:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "updateLockUnlockServiceData() :: " + e.getMessage());
        }
        return retVal;
    }
    
    @Override
    public List<ManageServiceUserDTO> engagementDataUploadWorkList(final UserProfileDTO userProfileDTO, 
            final String userFlag,final String reqToken) throws AppException {
        String procName;
        List<String> dupServList = new ArrayList<>();
        List<ManageServiceUserDTO> serviceList = new ArrayList<>();
        int count = 1;
        String userTypeFlag=userFlag;
        try {
            procName = "{CALL GetEngmtUploadWorkList(?,?,?,?,?,?,?,?,?,?)}";
            if(ApplicationConstants.DB_CONSTANT_ENG_LEAD_ENG_ANALYST.equalsIgnoreCase(userFlag)){
                count = 2;
            }
            for(int i=0;i<count;i++){
                if(count==2){
                    if(i==0){
                        userTypeFlag = ApplicationConstants.DB_CONSTANT_ENGAGEMENT_LEAD;
                    }else if(i==1){
                        userTypeFlag = ApplicationConstants.DB_CONSTANT_USER_TYPE_ANALYST;
                    }
                }
                 List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName,
                    new Object[]{userProfileDTO.getUserProfileKey(), ApplicationConstants.EMPTY_STRING, ApplicationConstants.EMPTY_STRING, 
                        ApplicationConstants.EMPTY_STRING, ApplicationConstants.EMPTY_STRING, ApplicationConstants.EMPTY_STRING, 
                        ApplicationConstants.EMPTY_STRING, 1, userTypeFlag,ApplicationConstants.EMPTY_STRING});
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    ManageServiceUserDTO serviceDto = new ManageServiceUserDTO();
                    serviceDto.setOrganizationKey((Integer) resultMap.get("CLNT_ORG_KEY"));
                    serviceDto.setOrganizationName((String) resultMap.get("Client NAME"));
                    serviceDto.setClientEngagementCode((String) resultMap.get("CLNT_ENGMT_CD"));
                    serviceDto.setEngagementName((String) resultMap.get("CLNT_ENGMT_NM"));
                    serviceDto.setServiceName((String) resultMap.get("SECUR_SRVC_NM"));
                    serviceDto.setSecureServiceCode((String) resultMap.get("SECUR_SRVC_CD"));
                    serviceDto.setStartDate(dateUtil.dateConvertionFromDBToJSP(resultMap.get("Start Date") + ""));
                    serviceDto.setEndDate(dateUtil.dateConvertionFromDBToJSP(resultMap.get("End Date") + ""));
                    serviceDto.setFileLockIndicator((Integer) resultMap.get("FL_LCK_IND"));
                    serviceDto.setFileCount((Integer) resultMap.get("FileCount"));
                    serviceDto.setUpdateDate(resultMap.get("UpdatedDate") == null ? "" : dateUtil.dateConvertionFromDBToJSP(resultMap.get("UpdatedDate") + ""));
                    serviceDto.setUserTypeValue(userFlag);
                    
                    serviceDto.setEncSecureServiceCode(encryptDecrypt.encrypt(serviceDto.getSecureServiceCode()));
                    serviceDto.setEncClientEngagementCode(encryptDecrypt.encrypt(serviceDto.getClientEngagementCode()));
                    
                    String encString = serviceDto.getClientEngagementCode()+"@"+serviceDto.getSecureServiceCode()+"@"+reqToken;
                    serviceDto.setEncString(encryptDecrypt.encrypt(encString));
                    
                    if(i==1){
                        if(!dupServList.contains(resultMap.get("CLNT_ENGMT_CD") + "" + resultMap.get("SECUR_SRVC_CD"))){
                            serviceList.add(serviceDto);
                        }
                    }else{
                        serviceList.add(serviceDto);
                    }
                    dupServList.add(resultMap.get("CLNT_ENGMT_CD") + "" + resultMap.get("SECUR_SRVC_CD"));
                }
            }
            
            }
           
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : engagementDataUploadWorkList():" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "engagementDataUploadWorkList() :: " + e.getMessage());
        }
        return serviceList;
    }
    
    @Override
    public List<ManageServiceUserDTO> searchEngagementServicesWorkList(final UserProfileDTO userProfileDTO, 
            final ManageServiceUserDTO manageServiceUserDTO,final String reqToken) throws AppException {
        String procName;
        List<ManageServiceUserDTO> serviceList = new ArrayList<ManageServiceUserDTO>();
        List<String> dupServList = new ArrayList<>();
        try {
            procName = "{CALL GetEngmtUploadWorkList(?,?,?,?,?,?,?,?,?,?)}";
            int count = 1;
            String userTypeFlag=manageServiceUserDTO.getUserFlag();
            
            if (ApplicationConstants.DB_CONSTANT_ENG_LEAD_ENG_ANALYST.equalsIgnoreCase(manageServiceUserDTO.getUserFlag())) {
                count = 2;
            }
            for (int i = 0; i < count; i++) {
                if (count == 2) {
                    if (i == 0) {
                        userTypeFlag = ApplicationConstants.DB_CONSTANT_ENGAGEMENT_LEAD;
                    } else if (i == 1) {
                        userTypeFlag = ApplicationConstants.DB_CONSTANT_USER_TYPE_ANALYST;
                    }
                }
                List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName,
                        new Object[]{userProfileDTO.getUserProfileKey(), manageServiceUserDTO.getOrganizationName(),
                            manageServiceUserDTO.getClientEngagementCode(), manageServiceUserDTO.getEngagementName(),
                            manageServiceUserDTO.getServiceName(), manageServiceUserDTO.getStartDate(), manageServiceUserDTO.getEndDate(),
                            1, userTypeFlag, manageServiceUserDTO.getUpdateDate()});
                if (!resultList.isEmpty()) {
                    for (Map<String, Object> resultMap : resultList) {
                        ManageServiceUserDTO serviceDto = new ManageServiceUserDTO();
                        serviceDto.setOrganizationKey((Integer) resultMap.get("CLNT_ORG_KEY"));
                        serviceDto.setOrganizationName((String) resultMap.get("Client NAME"));
                        serviceDto.setClientEngagementCode((String) resultMap.get("CLNT_ENGMT_CD"));
                        serviceDto.setEngagementName((String) resultMap.get("CLNT_ENGMT_NM"));
                        serviceDto.setServiceName((String) resultMap.get("SECUR_SRVC_NM"));
                        serviceDto.setSecureServiceCode((String) resultMap.get("SECUR_SRVC_CD"));
                        serviceDto.setStartDate(dateUtil.dateConvertionFromDBToJSP(resultMap.get("Start Date") + ""));
                        serviceDto.setEndDate(dateUtil.dateConvertionFromDBToJSP(resultMap.get("End Date") + ""));
                        serviceDto.setFileLockIndicator((Integer) resultMap.get("FL_LCK_IND"));
                        serviceDto.setFileCount((Integer) resultMap.get("FileCount"));
                        serviceDto.setUpdateDate(resultMap.get("UpdatedDate") == null ? "" : dateUtil.dateConvertionFromDBToJSP(resultMap.get("UpdatedDate") + ""));
                        serviceDto.setUserTypeValue(manageServiceUserDTO.getUserFlag());

                        serviceDto.setEncSecureServiceCode(encryptDecrypt.encrypt(serviceDto.getSecureServiceCode()));
                        serviceDto.setEncClientEngagementCode(encryptDecrypt.encrypt(serviceDto.getClientEngagementCode()));

                        String encString = serviceDto.getClientEngagementCode() + "@" + serviceDto.getSecureServiceCode() + "@" + reqToken;
                        serviceDto.setEncString(encryptDecrypt.encrypt(encString));

                        if (i == 1) {
                            if (!dupServList.contains(resultMap.get("CLNT_ENGMT_CD") + "" + resultMap.get("SECUR_SRVC_CD"))) {
                                serviceList.add(serviceDto);
                            }
                        } else {
                            serviceList.add(serviceDto);
                        }
                        dupServList.add(resultMap.get("CLNT_ENGMT_CD") + "" + resultMap.get("SECUR_SRVC_CD"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : searchEngagementServicesWorkList():" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "searchEngagementServicesWorkList() :: " + e.getMessage());
        }
        return serviceList;
    }
    
    @Override
    public int validateDuplicateFileName(String fileName, String engmntCode, String serviceCode) throws AppException {
        logger.info("Start: fetchAppRoleByName() ");
        try {
            String fetchAppRoleName = "{CALL Check_FileExistbyEngservicecode(?,?,?)}";
            int retVal = jdbcTemplate.queryForObject(fetchAppRoleName, new Object[]{fileName, engmntCode, serviceCode}, Integer.class);
            return retVal;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : fetchAppRoleByName:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "fetchAppRoleByName(): " + e.getMessage());
        }
    }
    
    @Override
    public List<UserProfileDTO> engagementLeadDetailsByCode(String engagementCode) throws AppException {
        String procName;
        List<UserProfileDTO> userList = new ArrayList<UserProfileDTO>();
        try {
            procName = "{CALL GetLeadDeatilsByEngandSerID(?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName,
                    new Object[]{engagementCode});
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    UserProfileDTO userDto = new UserProfileDTO();
                    userDto.setUserProfileKey((Integer) resultMap.get("Engagment LeadID"));
                    userDto.setEmail((String) resultMap.get("Engagement Lead Email"));
                    userDto.setFirstName((String) resultMap.get("Engagement Lead Name"));
                    userDto.setUserID((String) resultMap.get("UserType"));
                    MasterLookUpDTO dto = new MasterLookUpDTO();
                    dto.setLookUpEntityName((String) resultMap.get(""));
                    userDto.setUserTypeObj(dto);
                    userList.add(userDto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : engagementLeadDetailsByCode():" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "engagementLeadDetailsByCode() :: " + e.getMessage());
        }
        return userList;
    }

    @Override
    public int emailNotificationAllServicesLocked(String engagementCode) throws AppException {
       logger.info("Start: emailNotificationAllServicesLocked() ");
        try {
            String allServicesLockProcName = "{CALL GetLockallservicemail(?)}";
            int retVal = jdbcTemplate.queryForObject(allServicesLockProcName, new Object[]{engagementCode}, Integer.class);
            return retVal;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : emailNotificationAllServicesLocked:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "emailNotificationAllServicesLocked(): " + e.getMessage());
        }
    }

    @Override
    public List<UserProfileDTO> engagementAnalystDetails(String engagementCode, String serviceCode) throws AppException {
        String procName;
        List<UserProfileDTO> engagementAnalystUserList = new ArrayList<UserProfileDTO>();
        try {
            procName = "{CALL GetAnalystDeatilsByEngandSerID(?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName,
                    new Object[]{engagementCode,serviceCode});
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    UserProfileDTO userDto = new UserProfileDTO();
                    userDto.setUserProfileKey((Integer) resultMap.get("AnalystID"));
                    userDto.setEmail((String) resultMap.get("Analyst email"));
                    userDto.setFirstName((String) resultMap.get("Analyst Name"));
                    //MasterLookUpDTO dto = new MasterLookUpDTO();
                    //dto.setLookUpEntityName((String) resultMap.get(""));
                    //userDto.setUserTypeObj(dto);
                    engagementAnalystUserList.add(userDto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : engagementAnalystDetails():" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "engagementAnalystDetails() :: " + e.getMessage());
        }
        return engagementAnalystUserList;
    }
    
    @Override
    public ClientEngagementDTO fetchUploadedFileDetails(String fileName, String engmntCode) throws AppException {
        ClientEngagementDTO clientEngmntDTO = null;
        try {
            String uploadedFilesListProc = "{CALL Get_UploadedFileSize(?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(uploadedFilesListProc, new Object[]{fileName, engmntCode});
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    clientEngmntDTO = new ClientEngagementDTO();
                    clientEngmntDTO.setUploadFileSize(Long.parseLong(resultMap.get("FL_SZ")+""));
                    clientEngmntDTO.setFileStatusKey(Long.parseLong(resultMap.get("FL_STS_KEY") + ""));
                    clientEngmntDTO.setEngagementCode(resultMap.get("CLNT_ENGMT_CD")+"");
                    clientEngmntDTO.setSecurityServiceCode(resultMap.get("SECUR_SRVC_CD")+"");
                    clientEngmntDTO.setClientName(resultMap.get("ORG_NM")+"");
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : fetchUploadedFilesDetails:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "fetchUploadedFilesDetails() :: " + e.getMessage());
        }
        return clientEngmntDTO;
    }

//    @Override
//    public int updateUploadFileStatus(String fileName, String engmntCode, int statusKey) throws AppException {
//        int retVal = 0;
//        try {
//            String updateUploadFileStatusProcName = "{CALL UpdateFileStatus(?,?,?)}";
//            
//            retVal = jdbcTemplate.queryForObject(updateUploadFileStatusProcName, new Object[]{fileName, engmntCode, statusKey}, Integer.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("Exception occurred : updateUploadFileStatus:" + e.getMessage());
//            throw new AppException("Exception Occured while Executing the "
//                    + "updateUploadFileStatus() :: " + e.getMessage());
//        }
//        return retVal;
//    }

    @Override
    public int uploadFileCountByStatus(ClientEngagementDTO clientEngagementDTO, int status) throws AppException {
        logger.info("Start: uploadFileCountByStatus()");
        int fileCount = 0;
        try {
            String uploadedFilesCountProc = "{CALL Get_UploadedFileCountByStatus(?,?,?,?,?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(uploadedFilesCountProc, 
                    new Object[]{clientEngagementDTO.getEngagementCode(),
                                clientEngagementDTO.getSecurityServiceCode(), 
                                clientEngagementDTO.getCreatedByUserID(), 
                                status,
                                clientEngagementDTO.getUserTypeFlag()
                    }
            );
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    fileCount = Integer.parseInt(resultMap.get("DocCount")+"");
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : uploadFileCountByStatus:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "uploadFileCountByStatus() :: " + e.getMessage());
        }
        return fileCount;
    }
    
    public List<ClientEngagementDTO> getUploadedFileDetailsByEngagementId(final String engagementId){
        List<ClientEngagementDTO> filesList = new ArrayList<ClientEngagementDTO>();
        String serviceDetailsProc = "{CALL GetUploadedFilesbyEngcode(?)}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(serviceDetailsProc, new Object[]{engagementId});
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                   ClientEngagementDTO clientEngagementDTO = new ClientEngagementDTO();
                   clientEngagementDTO.setUploadFileName((String)resultMap.get("FL_NM"));
                   clientEngagementDTO.setCreatedByUser((String)resultMap.get("UploadedUser") + "( " + resultMap.get("EMAIL_ID")+" )");
                }
            }
          return filesList;
    }
}
