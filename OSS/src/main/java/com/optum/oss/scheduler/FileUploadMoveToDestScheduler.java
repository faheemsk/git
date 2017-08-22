/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.scheduler;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.helper.GetUploadedFileDetailsDAO;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.optum.oss.dto.ClientEngagementDTO;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
//import org.apache.commons.vfs2.FileObject;
//import org.apache.commons.vfs2.FileSystemOptions;
//import org.apache.commons.vfs2.Selectors;
//import org.apache.commons.vfs2.impl.StandardFileSystemManager;
//import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;

/**
 *
 * @author akeshavulu
 */
@Component
public class FileUploadMoveToDestScheduler {

//    private static Logger log = Logger.getLogger(FileUploadMoveToDestScheduler.class);
//
////    @Scheduled(cron = "0 0 0/2 * * *")//runs at every two hours
//    @Scheduled(cron = "0 */3 * * * *")//runs at every 10 mins
//    public void getUploadedFilesDetails() {
//        if (System.getProperty("ECG_UPLOAD") != null && "Yes".equalsIgnoreCase(System.getProperty("ECG_UPLOAD"))) {
//            long ecgTimeIntvl = 0;
//            String timeUnits = "";
//            String flag = "D";
//            GetUploadedFileDetailsDAO uploadedFileDAO = new GetUploadedFileDetailsDAO();
//            if (System.getProperty("ECG_SCAN_PASSED_TIME_INTERVAL") != null) {
//                ecgTimeIntvl = Long.parseLong(System.getProperty("ECG_SCAN_PASSED_TIME_INTERVAL") + "");
//            }
//            if (System.getProperty("ECGUPLOAD_Time_UNITS") != null) {
//                timeUnits = System.getProperty("ECGUPLOAD_Time_UNITS") + "";
//            }
//            List<ClientEngagementDTO> uploadedFilesList = new ArrayList();
//            try {
//                //DB call to fetch all files with status as scan failed.
//                uploadedFilesList = uploadedFileDAO.fetchScanFailedFilesInfo(timeUnits, ecgTimeIntvl, flag);
//                for (ClientEngagementDTO fileDetailsDTO : uploadedFilesList) {
////                    String EngCodefileName = fileDetailsDTO.getEngagementCode() + ApplicationConstants.ECG_FILENAME_SEPERATOR + fileDetailsDTO.getUploadFileName();
//                    String EngCodefileName = fileDetailsDTO.getFileUploadID() + ApplicationConstants.ECG_FILEID_SEPERATOR + 
//                            fileDetailsDTO.getEngagementCode() + ApplicationConstants.ECG_FILENAME_SEPERATOR + fileDetailsDTO.getUploadFileName();
//                    sftpConnect(EngCodefileName);
//                }
//            } catch (Exception e) {
//                log.error("Exception occurred : getUploadedFilesDetails sftpConnect failed:" + e.getMessage());
//            }
//        }
//
//    }
//
//    public void sftpConnect(final String fileName) {
//        GetUploadedFileDetailsDAO uploadedFileDAO = new GetUploadedFileDetailsDAO();
//        StandardFileSystemManager manager = new StandardFileSystemManager();
//
//        try {
//            ClientEngagementDTO clientEngmntDTO = new ClientEngagementDTO();
//            //String arrEngCodeFileName = fileName.indexOf(ApplicationConstants.ECG_FILENAME_SEPERATOR);
//            
//            String fileID = fileName.substring(0, fileName.indexOf(ApplicationConstants.ECG_FILEID_SEPERATOR));
//
//            String engCodefileName = fileName.substring(fileName.indexOf(ApplicationConstants.ECG_FILEID_SEPERATOR) + 1, fileName.length());
//
////            String engCode = fileName.substring(0,fileName.indexOf(ApplicationConstants.ECG_FILENAME_SEPERATOR));
////            String fName = fileName.substring(fileName.indexOf(ApplicationConstants.ECG_FILENAME_SEPERATOR)+1,fileName.length());
//            
//            String engCode = engCodefileName.substring(0, engCodefileName.indexOf(ApplicationConstants.ECG_FILENAME_SEPERATOR));
//            String fName = engCodefileName.substring(engCodefileName.indexOf(ApplicationConstants.ECG_FILENAME_SEPERATOR) + 1, engCodefileName.length());
//            
//            String fileIDFileName = fileID + ApplicationConstants.ECG_FILENAME_SEPERATOR + fName;
//            
////            clientEngmntDTO = uploadedFileDAO.fetchUploadedFileInfo(fName,engCode);
//            clientEngmntDTO = uploadedFileDAO.fetchUploadedFileInfo(fName, Integer.parseInt(fileID));
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
//             if (System.getProperty("ECG_OUT_SFTP_PATH") != null) {
//                ecgOutPath = System.getProperty("ECG_OUT_SFTP_PATH"); // SFTP out folder
//            }
//
//             /*ADDED CODE FOR PATH MANIPULATION SECURITY VULNERABILITY*/
//            if (targetDirPath.contains("..\\")) {
//                targetDirPath = targetDirPath.replace("..\\", "");
//            }
//            if (targetDirPath.contains("../")) {
//                targetDirPath = targetDirPath.replace("../", "");
//            }
//            if (targetDirPath.contains("./")) {
//                targetDirPath = targetDirPath.replace("./", "");
//            }
//            if (targetDirPath.contains("..")) {
//                targetDirPath = targetDirPath.replace("..", "");
//            }
//            /*ADDED CODE FOR PATH MANIPULATION SECURITY VULNERABILITY*/
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
////            String sftpUri = "sftp://" + userId + ":" + pwd + "@" + serverAddress + "" + ecgOutPath + fileName;
//            String sftpUri = "sftp://" + userId + ":" + pwd + "@" + serverAddress + "" + ecgOutPath + fileIDFileName;
//
//            // Create remote file object
//            FileObject remoteFile = manager.resolveFile(sftpUri, opts);
//            if (remoteFile.exists()) {
//                long remoteFileSize = remoteFile.getContent().getSize();
//
//                // Copy local file to shared folder
//                if (remoteFileSize == clientEngmntDTO.getUploadFileSize()) {
//
//                    final String targetFolderPath = targetDirPath + File.separator + clientEngmntDTO.getClientName() + File.separator
//                            + clientEngmntDTO.getEngagementCode() + File.separator + clientEngmntDTO.getSecurityServiceCode();
//                    if (!(new File(targetFolderPath).exists())) {
//                        // Create one directory
//                        boolean success = (new File(targetFolderPath)).mkdirs();
//                    }
//                    final String targetFile = targetFolderPath + File.separator + fName;
//                    // Create local file object
//                    File file = new File(targetFile);
//                    FileObject localFile = manager.resolveFile(file.getAbsolutePath());
//                    localFile.copyFrom(remoteFile, Selectors.SELECT_SELF);
//                    int retVal = uploadedFileDAO.updateFileStatus(fName, engCode, ApplicationConstants.DB_CONSTANT_FILE_STATUS_KEY_NEW, Integer.parseInt(fileID));
//                    remoteFile.delete();// To delete file from SFTP folder
//                }
//            }
//        } catch (Exception e) {
//              log.error("Exception occurred : getUploadedFilesDetails sftpConnect failed:" + e.getMessage());
//        } finally {
//            manager.close();
//        }
//    }
//
}
