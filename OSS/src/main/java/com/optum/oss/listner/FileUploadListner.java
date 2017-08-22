/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.listner;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.service.impl.EngagementDataUploadServiceImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 *
 * @author Administrator
 */

//@Component
//@Configuration
public class FileUploadListner {

//    @Autowired
//    private EngagementDataUploadServiceImpl EngagementDataUploadService;
//
//    private static String displayString = "";
//    private static final String dirPath = "D:\\OSS_UPLOAD\\";
//    private static final String dirPath = "D:\\uploadFolder\\";  // out folder 
//    private String targetDirPath = "Z:\\UPLOAD\\";//Shared folder
//    private String outFileDir = dirPath;
//    InputStream in = null;
//    OutputStream out = null;
//    ClientEngagementDTO clientEngmntDTO = new ClientEngagementDTO();
//
//    @Async
//    public void watchDirectory() throws IOException,
//            InterruptedException,
//            AppException {
//
//        Path ecgOutFolder = Paths.get(dirPath);
//        String eventString = "";
//        WatchService watchService = FileSystems.getDefault().newWatchService();
//        ecgOutFolder.register(watchService,
//                StandardWatchEventKinds.ENTRY_CREATE,
//                StandardWatchEventKinds.ENTRY_DELETE,
//                StandardWatchEventKinds.ENTRY_MODIFY);
//
//        boolean valid = true;
//        do {
//            WatchKey watchKey = watchService.take();
//            for (WatchEvent event : watchKey.pollEvents()) {
//                if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {
//                    String fileName = event.context().toString();
//                    eventString = "File Created:" + fileName;
//                }
//                if (StandardWatchEventKinds.ENTRY_DELETE.equals(event.kind())) {
//                    String fileName = event.context().toString();
//                    eventString = "File Deleted:" + fileName;
//                }
//                if (StandardWatchEventKinds.ENTRY_MODIFY.equals(event.kind())) {
//                    String fileName = event.context().toString();
//                    eventString = "File Modified:" + fileName;
//
//                    outFileDir = outFileDir + File.separator + fileName;
//                    File uploadedFile = new File(outFileDir);
//                    String arrEngCodeFileName[] = fileName.split("@");
//                    if (uploadedFile.exists()) {
//                        //uploadedFile.createNewFile();
//
//                        double fileSizeInBytes = uploadedFile.length();
//                        clientEngmntDTO = EngagementDataUploadService.fetchUploadedFileDetails(arrEngCodeFileName[1], arrEngCodeFileName[0]);
//                        if (clientEngmntDTO != null) {
//                            if (fileSizeInBytes == clientEngmntDTO.getUploadFileSize()) {
//                                try {
//                                    targetDirPath = targetDirPath + File.separator + clientEngmntDTO.getClientName() + File.separator
//                                            + clientEngmntDTO.getEngagementCode() + clientEngmntDTO.getSecurityServiceCode();
//                                    if (!(new File(targetDirPath).exists())) {
//                                        // Create one directory
//                                        boolean success = (new File(targetDirPath)).mkdirs();
//                                    }
//                                    targetDirPath = targetDirPath + File.separator + arrEngCodeFileName[1];
//                                    in = new FileInputStream(outFileDir);
//                                    out = new FileOutputStream(targetDirPath);
//                                    byte[] buffer = new byte[1024];
//                                    int length;
//                                    while ((length = in.read(buffer)) > 0) {
//                                        out.write(buffer, 0, length);
//                                    }
//                                    int retVal = EngagementDataUploadService.updateUploadFileStatus(arrEngCodeFileName[1], arrEngCodeFileName[0],
//                                            ApplicationConstants.DB_CONSTANT_FILE_STATUS_KEY_NEW);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                } finally {
//                                    in.close();
//                                    out.close();
//                                }
//                            }
//                        }
//                    }
//                }
//                //   System.out.println("eventString>>" + eventString);
//
//            }
//            valid = watchKey.reset();
//
//        } while (valid);
//    }

}
