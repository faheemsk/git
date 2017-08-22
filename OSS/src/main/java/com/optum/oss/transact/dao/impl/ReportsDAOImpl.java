/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.transact.dao.impl;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dao.impl.DetailedPermissionsDAOImpl;
import com.optum.oss.dto.ClientReportsUploadDTO;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.transact.dao.ReportsDAO;
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
 * @author mrejeti
 */
public class ReportsDAOImpl implements ReportsDAO {

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

    @Autowired
    private EncryptDecrypt encryptDecrypt;
    
    @Autowired
    DateUtil dateUtil;
    
    @Override
    public List<ClientReportsUploadDTO> fetchClientReportsUploadWorkList(UserProfileDTO userDto, ClientReportsUploadDTO clientReportsUploadDTO) throws AppException {
        logger.info("Fetching client reports upload work list in daoimpl");
        String clientworklistproc = "{CALL Report_GetReportWorkList(?)}";
        List<ClientReportsUploadDTO> reportsDto = null;
        // List<Map<String, Object>> resultList = jdbcTemplate.queryForList(clientworklistproc, new Object[]{});
        reportsDto = new ArrayList<>();
        String pvcFlag = "E";
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String dateUpdate = "";
        try {

            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(clientworklistproc, new Object[]{
                //pvcFlag,
                userDto.getUserProfileKey()
//                clientReportsUploadDTO.getOrganizationName(),
//                clientReportsUploadDTO.getClientEngagementCode(),
//                clientReportsUploadDTO.getClientEngagementName(),
//                clientReportsUploadDTO.getReportName(),
//                clientReportsUploadDTO.getFileName(),
//                clientReportsUploadDTO.getUpdateDate(),
//                clientReportsUploadDTO.getStatus()
            });
            clientReportsUploadDTO.getCreatedDate();
            reportsDto = new ArrayList<>(resultList.size());

            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    ClientReportsUploadDTO reportsDTO = new ClientReportsUploadDTO();
                    reportsDTO.setOrganizationName(resultMap.get("Client NAME") + "");
                    reportsDTO.setClientEngagementCode(resultMap.get("CLNT_ENGMT_CD") + "");
                    reportsDTO.setEncClientEngagementCode(encryptDecrypt.encrypt(resultMap.get("CLNT_ENGMT_CD") + ""));
                    //reportsDTO.setEncClientEngagementCode(encryptDecrypt.encrypt(resultMap.get("CLNT_ENGMT_CD") + ""));
                    reportsDTO.setClientEngagementName(resultMap.get("CLNT_ENGMT_NM") + "");
                    if(!(resultMap.get("LKP_ENTY_NM") + "").equalsIgnoreCase("Scan Failure")) {
                        if (resultMap.get("RPT_NM") != null) {
                            reportsDTO.setReportName(resultMap.get("RPT_NM") + "");
                        } else {
                            reportsDTO.setReportName("");
                        }
                        if (resultMap.get("FL_NM") != null) {
                            reportsDTO.setFileName(resultMap.get("FL_NM") + "");
                        } else {
                            reportsDTO.setFileName("");
                        }

                        if (resultMap.get("UPDT_DT") != null) {
                            dateUpdate = format.format(resultMap.get("UPDT_DT"));
                            reportsDTO.setUpdateDate(dateUpdate);
        //                        reportsDTO.setUpdateDate(resultMap.get("UPDT_DT") + "");
                        } else {
                            reportsDTO.setUpdateDate("");
                        }

                        if (resultMap.get("LKP_ENTY_NM") != null) {
                            reportsDTO.setStatus(resultMap.get("LKP_ENTY_NM") + "");
                        } else {
                            reportsDTO.setStatus("");
                        }
                        if (resultMap.get("CREAT_DT") != null) {
                            reportsDTO.setCreatedDate(resultMap.get("CREAT_DT") + "");
                        } else {
                            reportsDTO.setCreatedDate("");
                        }
                    } else {
                        reportsDTO.setStatus("");
                        reportsDTO.setUpdateDate("");
                        reportsDTO.setFileName("");
                        reportsDTO.setReportName("");
                        reportsDTO.setCreatedDate("");
                    }

                    reportsDto.add(reportsDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : ReportsDAOImpl: fetchClientReportsUploadWorkList(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "ReportsDAOImpl: fetchClientReportsUploadWorkList(): " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl: fetchClientReportsUploadWorkList() to fetch Client Reports UploadWorkList");
        return reportsDto;
    }

    @Override
    public int saveUploadReportFile(ClientReportsUploadDTO saveReports, UserProfileDTO userDto) throws AppException {
        logger.info("Start: fetchSavedata: Fetching save data");
    //    String saveReportNames = "{CALL Reports_INSReportFileUploadLog(?,?,?,?,?,?,?,?,?,?)}";
        String saveReportNames = "{CALL Reports_INSReportFileUploadLog(?,?,?,?,?,?,?,?,?)}";
        int retVal = 0;
        int fileStatus = 0;
        try {
            if (System.getProperty("ECG_UPLOAD") != null) {
                if ("Yes".equalsIgnoreCase(System.getProperty("ECG_UPLOAD"))) {
                    fileStatus = ApplicationConstants.REPORT_FILE_STATUS_KEY_SCAN_INPROGRESS;
                } else {
                    fileStatus = ApplicationConstants.DB_CONSTANT_FILE_STATUS_KEY_NEW;
                }
            }
            retVal = jdbcTemplate.queryForObject(saveReportNames, new Object[]{ApplicationConstants.DB_ROW_STATUS_ACTIVE_VALUE,
                userDto.getOrganizationKey(),
                saveReports.getClientEngagementCode(),
                saveReports.getReportkey(),
             //   ApplicationConstants.DB_ROW_STATUS_NOTPUBLISHED_VALUE, //Report Status key
                fileStatus, //Report Status key
                saveReports.getFileName(),
                saveReports.getFilePath(),
                userDto.getUserProfileKey(),
                saveReports.getFileSize()
            //    fileStatus
            }, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : ReportsDAOImpl: fetchReportNames(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "ReportsDAOImpl: fetchReportNames(): " + e.getMessage());
        }
        logger.info("End: fetchsaveddata: Fetching saved data");
        return retVal;
    }

    @Override
    public List<MasterLookUpDTO> fetchReportNames(String engagementCode) throws AppException {
        logger.info("Start: fetchReportNames: Fetching report names drop down");
        String fetchReportNames = "{CALL Report_ListReportNames(?)}";
        List<MasterLookUpDTO> reportNames = null;
        try {

            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(fetchReportNames, new Object[]{engagementCode});
            reportNames = new ArrayList<>(resultList.size());

            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    MasterLookUpDTO lookUpDto = new MasterLookUpDTO();
                    lookUpDto.setLookUpEntityName(resultMap.get("RPT_NM") + "");
                    lookUpDto.setMasterLookupKey((Integer) resultMap.get("RPT_NM_KEY"));
                    reportNames.add(lookUpDto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : ReportsDAOImpl: fetchReportNames(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "ReportsDAOImpl: fetchReportNames(): " + e.getMessage());
        }
        logger.info("End: fetchReportNames: Fetching report names drop down");
        return reportNames;
    }

    @Override
    public List<ClientReportsUploadDTO> fetchUploadFileList(String engagementCode) throws AppException {

        String fetchReportNames = "{CALL Report_ListReportFileUpload(?)}";
        List<ClientReportsUploadDTO> uploadist = null;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String updatedDate ="";
        try {

            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(fetchReportNames, new Object[]{engagementCode});
            uploadist = new ArrayList<>(resultList.size());

            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {

                    ClientReportsUploadDTO uploadDto = new ClientReportsUploadDTO();
                    uploadDto.setUploadFileKey(Long.parseLong(resultMap.get("RPT_FL_UPLOAD_LOG_KEY") + ""));
                    uploadDto.setEncUploadFileKey(encryptDecrypt.encrypt(uploadDto.getUploadFileKey() + ""));
                    uploadDto.setReportName(resultMap.get("RPT_NM") + "");
                    if (null != resultMap.get("UpdatedOn")) {
                        updatedDate = format.format(resultMap.get("UpdatedOn"));
                    }
                    uploadDto.setUpdateDate(updatedDate);
                    uploadDto.setModifiedByUser(resultMap.get("UpdatedBy") + "");
                    uploadDto.setFileName(resultMap.get("FL_NM") + "");
                    uploadDto.setEncFileName(encryptDecrypt.encrypt(resultMap.get("FL_NM") + ""));
                    uploadDto.setFilePath(resultMap.get("FL_FLDR_PTH") + "");
                    uploadDto.setEncFilePath(encryptDecrypt.encrypt(resultMap.get("FL_FLDR_PTH") + ""));
                    uploadDto.setRowStatusKey(Long.parseLong(resultMap.get("RPT_STS_KEY") + ""));
                    uploadDto.setStatus(resultMap.get("LKP_ENTY_NM") + "");
                    if (resultMap.get("UPDT_USER_ID") != null) {
                        uploadDto.setModifiedByUserID(Long.parseLong(resultMap.get("UPDT_USER_ID") + ""));
                    }
                  //  uploadDto.setFileStatusKey(Integer.parseInt(resultMap.get("FL_STS_KEY") + ""));
                    uploadist.add(uploadDto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : ReportsDAOImpl: fetchUploadFileList(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "ReportsDAOImpl: fetchUploadFileList(): " + e.getMessage());
        }
        logger.info("End: fetchUploadFileList: Fetching upload ");
        return uploadist;

    }

    @Override
    public int publishReportFile(List<ClientReportsUploadDTO> saveReports, UserProfileDTO userDto) throws AppException {
        logger.info("Start: fetchSavedata: Fetching save data");

        String updatesaveReportFile = "{CALL Reports_UpdateReportFileUploadLog(?,?,?,?,?)}";
        int retVal = 0;
        try {
//            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(updatesaveReportFile, new Object[]{});
            if (!saveReports.isEmpty()) {
                for (ClientReportsUploadDTO dto : saveReports) {
                    retVal = jdbcTemplate.queryForObject(updatesaveReportFile, new Object[]{
                        dto.getUploadFileKey(),
                        ApplicationConstants.DB_ROW_STATUS_ACTIVE_VALUE,
                        Integer.parseInt(dto.getStatus()), //Report Status key
                        userDto.getUserProfileKey(),
                        "U"
                    }, Integer.class);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : ReportsDAOImpl: fetchReportNames(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "ReportsDAOImpl: fetchReportNames(): " + e.getMessage());
        }
        logger.info("End: fetchsaveddata: Fetching saved data");
        return retVal;

    }

    @Override
    public int deleteUploadedFileDetails(long uploadFilekey) throws AppException {
        logger.info("Start: deleteUploadedFileDetails: delete file");

        String updatesaveReportFile = "{CALL Reports_UpdateReportFileUploadLog(?,?,?,?,?)}";
        int retVal = 0;
        try {
            retVal = jdbcTemplate.queryForObject(updatesaveReportFile, new Object[]{uploadFilekey, "", "", "", "D"}, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : ReportsDAOImpl: deleteUploadedFileDetails(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "ReportsDAOImpl: deleteUploadedFileDetails(): " + e.getMessage());
        }
        logger.info("End: deleteUploadedFileDetails: delete file");
        return retVal;

    }

    public int updateEngagmentStatusToPublish(final String engKey, final long engStatusKey) throws AppException {
        logger.info("Start: updateEngagmentStatusToPublish: ");

        String updatesaveReportFile = "{CALL UPDATE_EngagementPublishStatus(?,?)}";
        int retVal = 0;
        try {
            retVal = jdbcTemplate.queryForObject(updatesaveReportFile, new Object[]{engKey, engStatusKey}, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : ReportsDAOImpl: updateEngagmentStatusToPublish(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "ReportsDAOImpl: deleteUploadedFileDetails(): " + e.getMessage());
        }
        logger.info("End: updateEngagmentStatusToPublish: ");
        return retVal;
    }

    /**
     * This method for search / filter Client Reports Upload work list
     *
     * @param userDto
     * @param clientReportsUploadDTO
     * @return List<ClientReportsUploadDTO>
     * @throws AppException
     */
    @Override
    public List<ClientReportsUploadDTO> searchClientReportsUploadWorkList(UserProfileDTO userDto, ClientReportsUploadDTO clientReportsUploadDTO)
            throws AppException {
        logger.info("Start: AnalystValidationDAOImpl: searchClientReportsUploadWorkList()filter / search client reports upload work list in daoimpl");
        String clientworklistproc = "{CALL Report_GetReportWorkListSearch(?,?,?,?,?,?,?,?)}";
        List<ClientReportsUploadDTO> reportsDto = null;
        reportsDto = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String updatedDate ="";
        try {
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(clientworklistproc, new Object[]{
                userDto.getUserProfileKey(),
                clientReportsUploadDTO.getOrganizationName(),
                clientReportsUploadDTO.getClientEngagementCode(),
                clientReportsUploadDTO.getClientEngagementName(),
                clientReportsUploadDTO.getReportName(),
                clientReportsUploadDTO.getFileName(),
                clientReportsUploadDTO.getUpdateDate(),
                clientReportsUploadDTO.getStatus()
            });
            reportsDto = new ArrayList<>(resultList.size());

            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    ClientReportsUploadDTO reportsDTO = new ClientReportsUploadDTO();
                    reportsDTO.setOrganizationName(resultMap.get("Client NAME") + "");
                    reportsDTO.setClientEngagementCode(resultMap.get("CLNT_ENGMT_CD") + "");
                    reportsDTO.setEncClientEngagementCode(encryptDecrypt.encrypt(resultMap.get("CLNT_ENGMT_CD") + ""));
                    reportsDTO.setClientEngagementName(resultMap.get("CLNT_ENGMT_NM") + "");
                    if (resultMap.get("RPT_NM") != null) {
                        reportsDTO.setReportName(resultMap.get("RPT_NM") + "");
                    } else {
                        reportsDTO.setReportName("");
                    }
                    if (resultMap.get("FL_NM") != null) {
                        reportsDTO.setFileName(resultMap.get("FL_NM") + "");
                    } else {
                        reportsDTO.setFileName("");
                    }

                    if (resultMap.get("UPDT_DT") != null) {
                        updatedDate = format.format(format.parse(resultMap.get("UPDT_DT")+""));
                        reportsDTO.setUpdateDate(updatedDate);
                    } else {
                        reportsDTO.setUpdateDate("");
                    }

                    if (resultMap.get("LKP_ENTY_NM") != null) {
                        reportsDTO.setStatus(resultMap.get("LKP_ENTY_NM") + "");
                    } else {
                        reportsDTO.setStatus("");
                    }
                    if (resultMap.get("CREAT_DT") != null) {
                        reportsDTO.setCreatedDate(resultMap.get("CREAT_DT") + "");
                    } else {
                        reportsDTO.setCreatedDate("");
                    }

                    reportsDto.add(reportsDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : ReportsDAOImpl: searchClientReportsUploadWorkList(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "ReportsDAOImpl: fetchClientReportsUploadWorkList(): " + e.getMessage());
        }
        logger.info("End: AnalystValidationDAOImpl: searchClientReportsUploadWorkList() to filter / search Client Reports UploadWorkList");
        return reportsDto;
    }
    
    @Override
    public int validateDuplicateFileName(String fileName, long reportkey,String engCode) throws AppException {
        logger.info("Start: validateDuplicateFileName() ");
        try {
            String duplicateFileNameProc = "{CALL Check_FileExistbyReportName(?,?,?)}";
            int retVal = jdbcTemplate.queryForObject(duplicateFileNameProc, new Object[]{fileName, reportkey,engCode}, Integer.class);
            return retVal;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : validateDuplicateFileName:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "validateDuplicateFileName(): " + e.getMessage());
        }
    }
}
