/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.scheduler;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.helper.EngagementDataUploadMailHelper;
import com.optum.oss.helper.GetUploadedFileDetailsDAO;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
//import org.apache.commons.vfs2.FileObject;
//import org.apache.commons.vfs2.FileSystemOptions;
//import org.apache.commons.vfs2.Selectors;
//import org.apache.commons.vfs2.impl.StandardFileSystemManager;
//import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author akeshavulu
 */
@Component
public class ReportsFileUploadECGScheduler {

    private static final Logger logger = Logger.getLogger(FileUploadECGScheduler.class);

    private static final ReportsFileUploadECGScheduler lockObj = new ReportsFileUploadECGScheduler();

    /*
     Start : Autowiring of Classes
     */
    @Autowired
    private EngagementDataUploadMailHelper engmentDataUploadMailHelper;
//
//    /*
//     End : Autowiring of Classes
//     */
//    // @Scheduled(cron = "0 0 0/2 * * *")//runs at every couple of hours
//    @Scheduled(cron = "0 */3 * * * *")//runs at every 3 mins
//    public void checkFileStatus() {
//        logger.info("Start ReportsFileUploadECGScheduler");
//        if (System.getProperty("ECG_UPLOAD") != null && "Yes".equalsIgnoreCase(System.getProperty("ECG_UPLOAD"))) {
//            long ecgFailedTimeIntvl = 0;
//            long ecgPassedTimeIntvl = 0;
//            String timeUnits = "";
//            String flag = "R";
//            GetUploadedFileDetailsDAO uploadedFileDAO = new GetUploadedFileDetailsDAO();
//            if (System.getProperty("ECG_SCAN_FAILED_TIME_INTERVAL") != null) {
//                ecgFailedTimeIntvl = Long.parseLong(System.getProperty("ECG_SCAN_FAILED_TIME_INTERVAL") + "");
//            }
//            if (System.getProperty("ECG_SCAN_PASSED_TIME_INTERVAL") != null) {
//                ecgPassedTimeIntvl = Long.parseLong(System.getProperty("ECG_SCAN_PASSED_TIME_INTERVAL") + "");
//            }
//            if (System.getProperty("ECGUPLOAD_Time_UNITS") != null) {
//                timeUnits = System.getProperty("ECGUPLOAD_Time_UNITS") + "";
//            }
//            List<ClientEngagementDTO> scanFailedFilesList = new ArrayList<ClientEngagementDTO>();
//            List<ClientEngagementDTO> scanPassedFilesList = new ArrayList<ClientEngagementDTO>();
//
//            try {
//
//                scanPassedFilesList = uploadedFileDAO.fetchReportsScanFilesInfo(timeUnits, ecgPassedTimeIntvl, flag);
//                for (ClientEngagementDTO fileDetailsDTO : scanPassedFilesList) {
//                    String fileIDFileName = fileDetailsDTO.getFileUploadID() + ApplicationConstants.ECG_FILENAME_SEPERATOR + fileDetailsDTO.getUploadFileName();
//                    try{
//                        sftpConnect(fileIDFileName);
//                    }catch(Exception e){
//                        logger.error("Exception occurred : sftpConnect Failed:" + e.getMessage());
//                    }
//                }
//
//                synchronized (lockObj) {
//                    //DB call to fetch all files with status as scan failed.
//                    scanFailedFilesList = uploadedFileDAO.fetchReportsScanFilesInfo(timeUnits, ecgFailedTimeIntvl, flag);
//
//                    //Mail to user who upload malicious file
//                    if (scanFailedFilesList != null) {
//                        for (ClientEngagementDTO scanFailedFilesDTO : scanFailedFilesList) {
//                            engmentDataUploadMailHelper.mailToAnalystOnMaliciousFileUpload(scanFailedFilesDTO);
//                        }
//                    }
//                    //DB call to change file status to scan failed.
//                    uploadedFileDAO.updateFileStatusToScanFailed(timeUnits, ecgFailedTimeIntvl, flag);
//                }
//
//            } catch (Exception e) {
//                logger.error("Exception occurred : ReportsFileUploadECGScheduler:" + e.getMessage());
//            }
//        }
//
//    }
//
//    public void sftpConnect(final String fileName) {
//        logger.info("sftpConnect method called");
//        GetUploadedFileDetailsDAO uploadedFileDAO = new GetUploadedFileDetailsDAO();
//        StandardFileSystemManager manager = new StandardFileSystemManager();
//
//        try {
//            ClientEngagementDTO clientEngmntDTO = new ClientEngagementDTO();
//            String arrEngCodeFileName[] = fileName.split(ApplicationConstants.ECG_FILENAME_SEPERATOR);
//            String fileID = arrEngCodeFileName[0];
//            String fName = arrEngCodeFileName[1];
//            clientEngmntDTO = uploadedFileDAO.fetchReportsUploadedFileInfo(fileID);
//            String serverAddress = "";
//            String userId = "";
//            String pwd = "";
//            String targetDirPath = "";
//            String ecgOutPath = "";
//
//            if (System.getProperty("SFTP_SERVER_ADDRESS") != null) {
//                serverAddress = System.getProperty("SFTP_SERVER_ADDRESS");
//            }
//            if (System.getProperty("SFTP_USER_ID") != null) {
//                userId = System.getProperty("SFTP_USER_ID");
//            }
//            if (System.getProperty("SFTP_PASSWORD") != null) {
//                pwd = System.getProperty("SFTP_PASSWORD");
//            }
//
//            if (System.getProperty("OSS_UPLOAD_FOLDER") != null) {
//                targetDirPath = System.getProperty("OSS_UPLOAD_FOLDER"); //Shared folder
//            }
//            if (System.getProperty("ECG_OUT_SFTP_PATH") != null) {
//                ecgOutPath = System.getProperty("ECG_OUT_SFTP_PATH"); // SFTP out folder
//            }
//
//            //Initializes the file manager
//            manager.init();
//
//            //Setup our SFTP configuration
//            FileSystemOptions opts = new FileSystemOptions();
//            SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(
//                    opts, "no");
//            SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, true);
//            SftpFileSystemConfigBuilder.getInstance().setTimeout(opts, 10000);
//
//            //Create the SFTP URI using the host name, userid, password,  remote path and file name
//            String sftpUri = "sftp://" + userId + ":" + pwd + "@" + serverAddress + "" + ecgOutPath + fileName;
//
//            // Create remote file object
//            FileObject remoteFile = manager.resolveFile(sftpUri, opts);
//            if (remoteFile.exists()) {
//                long remoteFileSize = remoteFile.getContent().getSize();
//
//                // Copy local file to shared folder
//                if (remoteFileSize == clientEngmntDTO.getUploadFileSize()) {
//
////                    String targetFolderPath = targetDirPath + File.separator + clientEngmntDTO.getClientName() + File.separator
////                            + clientEngmntDTO.getEngagementCode() + File.separator + clientEngmntDTO.getSecurityServiceCode();
//                    String targetFolderPath = clientEngmntDTO.getUploadFilePath();
//                    String fileFolderPath = targetFolderPath.substring(0, targetFolderPath.lastIndexOf(File.separator));
//                    //logger.info("fileFolderPath --------" + fileFolderPath);
//                    if (!(new File(fileFolderPath).exists())) {
//                        // Create one directory
//                        boolean success = (new File(fileFolderPath)).mkdirs();
//                    }
//                    String targetFile = fileFolderPath + File.separator + fName;
//                    // Create local file object
//                    File file = new File(targetFile);
//                    FileObject localFile = manager.resolveFile(file.getAbsolutePath());
//                    localFile.copyFrom(remoteFile, Selectors.SELECT_SELF);
//                 //   int retVal = uploadedFileDAO.updateReportsFileStatus(fileID, ApplicationConstants.DB_CONSTANT_FILE_STATUS_KEY_NEW);
//                    int retVal = uploadedFileDAO.updateReportsFileStatus(fileID, ApplicationConstants.DB_ROW_STATUS_NOTPUBLISHED_VALUE);
//                    remoteFile.delete();// To delete file from SFTP folder
//                }
//            }
//        } catch (Exception e) {
//          logger.error("Exception occurred : sftpConnect Failed:" + e.getMessage());
//        } finally {
//            manager.close();
//        }
//    }

}
