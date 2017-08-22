/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.listner;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.helper.GetUploadedFileDetailsDAO;
import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.service.impl.EngagementDataUploadServiceImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author hpasupuleti
 */
//@Component
//
//public class DirectoryWatch {
//
//    private static Logger log = Logger.getLogger(DirectoryWatch.class.getName());
//    
//    private WatchService watcher;
//    private Map<WatchKey, Path> keys;
//    private boolean recursive;
//    private boolean trace = false;
//
//    private static final String srcDirPath = "D:\\test2\\";  // out folder 
//    private String targetDirPath = "Z:\\UPLOAD\\";//Shared folder
//    
//    @Autowired
//    private EngagementDataUploadServiceImpl EngagementDataUploadService;
//    
//    
//    @SuppressWarnings("unchecked")
//    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
//        return (WatchEvent<T>) event;
//    }
//
//    /**
//     * Register the given directory with the WatchService
//     */
//    private void register(Path dir) throws IOException {
//        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
//        if (trace) {
//            Path prev = keys.get(key);
//            if (prev == null) {
//                log.info("register:" + dir + "\n");
//            } else {
//                if (!dir.equals(prev)) {
//                    log.info("update: " + prev + " -> " + dir + "\n");
//                }
//            }
//        }
//        keys.put(key, dir);
//    }
//
//    /**
//     * Register the given directory, and all its sub-directories, with the
//     * WatchService.
//     */
//    private void registerAll(final Path start) throws IOException {
//        // register directory and sub-directories  
//        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
//            @Override
//            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
//                    throws IOException {
//                register(dir);
//                return FileVisitResult.CONTINUE;
//            }
//        });
//    }
//
//    /**
//     * Creates a WatchService and registers the given directory
//     *
//     * @return
//     */
//    public DirectoryWatch() throws IOException {
//        log.info("Inside DirectoryWatch Constructor");
//        Path dir = Paths.get(srcDirPath);
//        boolean recursive = true;
//        this.watcher = FileSystems.getDefault().newWatchService();
//        this.keys = new HashMap<WatchKey, Path>();
//        this.recursive = recursive;
//        if (recursive) {
//            log.info("Scanning " + dir + " ...\n");
//            registerAll(dir);
//            log.info("Done.");
//        } else {
//            register(dir);
//        }
//        // enable trace after initial registration  
//        this.trace = true;
//        processEvents();
//    }
//
//    /**
//     * Process all events for keys queued to the watcher
//     *
//     * @throws FileNotFoundException
//     */
//    @PostConstruct
//    void processEvents() throws FileNotFoundException,IOException {
//        log.info("Inside DirectoryWatch:processEvents>>>");
//       
//        for (;;) {
//            // wait for key to be signalled  
//            WatchKey key;
//            try {
//                key = watcher.take();
//            } catch (InterruptedException x) {
//                return;
//            }
//            Path dir = keys.get(key);
//            if (dir == null) {
//                log.error("WatchKey not recognized!!");
//                continue;
//            }
//            for (WatchEvent<?> event : key.pollEvents()) {
//                WatchEvent.Kind kind = event.kind();
//                // TBD - provide example of how OVERFLOW event is handled  
//                if (kind == OVERFLOW) {
//                    continue;
//                }
//                // Context for directory entry event is the file name of entry  
//                WatchEvent<Path> ev = cast(event);
//                Path name = ev.context();
//                Path child = dir.resolve(name);
//                // print out event  
//                log.info(event.kind().name() + ":" + child + "\n");
//                // if directory is created, and watching recursively, then  
//                // register it and its sub-directories  
//                if (recursive && (kind == ENTRY_CREATE)) {
//                    String fileName = event.context().toString();
//                    log.info("Inside DirectoryWatch:processEvents>>>ENTRY_CREATE:fileName:"+fileName);
//                    try {
//                         moveToDestFolder(fileName);
//                    } catch (Exception x) {
//                        // ignore to keep sample readbale  
//                    }
//                }
//                
//                if (recursive && (kind == ENTRY_MODIFY)) {
//                    String fileName = event.context().toString();
//                    log.info("DirectoryWatch:processEvents>>>ENTRY_MODIFY:fileName:"+fileName);
//                    moveToDestFolder(fileName);
//
//                }
//            }
//            // reset key and remove from set if directory no longer accessible  
//            boolean valid = key.reset();
//            if (!valid) {
//                keys.remove(key);
//                // all directories are inaccessible  
//                if (keys.isEmpty()) {
//                    break;
//                }
//            }
//        }
//    }
//    
//    private void moveToDestFolder(final String fileName) {
//         GetUploadedFileDetailsDAO uploadedFileDAO = new GetUploadedFileDetailsDAO();
//        log.info("DirectoryWatch:moveToDestFolder:fileName:>>>" + fileName);
//        String outFileDir = srcDirPath + File.separator + fileName;
//        File uploadedFile = new File(outFileDir);
//        String arrEngCodeFileName[] = fileName.split("@");
//        if (uploadedFile.exists()) {
//            try {
//
//                double fileSizeInBytes = uploadedFile.length();
//                log.info("DirectoryWatch:moveToDestFolder:fileSizeInBytes:>>>" + fileSizeInBytes);
//                ClientEngagementDTO clientEngmntDTO = new ClientEngagementDTO();
////                            clientEngmntDTO = EngagementDataUploadService.fetchUploadedFileDetails(arrEngCodeFileName[1], arrEngCodeFileName[0]);
//                log.info("DirectoryWatch:moveToDestFolder:arrEngCodeFileName[0]:>>>" + arrEngCodeFileName[0]);
//                log.info("DirectoryWatch:moveToDestFolder:arrEngCodeFileName[1]:>>>" + arrEngCodeFileName[1]);
//                clientEngmntDTO = uploadedFileDAO.fetchUploadedFileInfo(arrEngCodeFileName[1], arrEngCodeFileName[0]);
//                if (clientEngmntDTO != null) {
//                    InputStream in = null;
//                    OutputStream out = null;
//                    if (fileSizeInBytes == clientEngmntDTO.getUploadFileSize()) {
//                        try {
//                            String targetFolderPath = targetDirPath + File.separator + clientEngmntDTO.getClientName() + File.separator
//                                    + clientEngmntDTO.getEngagementCode() + clientEngmntDTO.getSecurityServiceCode();
//                            
//                            log.info("DirectoryWatch:moveToDestFolder:targetDirPath>>>" + targetFolderPath);
//                            
//                            if (!(new File(targetFolderPath).exists())) {
//                                // Create one directory
//                                boolean success = (new File(targetFolderPath)).mkdirs();
//                            }
//                            String targetFile = targetFolderPath + File.separator + arrEngCodeFileName[1];
//                            in = new FileInputStream(outFileDir);
//                            out = new FileOutputStream(targetFile);
//                            byte[] buffer = new byte[1024];
//                            int length;
//                            while ((length = in.read(buffer)) > 0) {
//                                out.write(buffer, 0, length);
//                            }
////                                        int retVal = EngagementDataUploadService.updateUploadFileStatus(arrEngCodeFileName[1], arrEngCodeFileName[0],
////                                                ApplicationConstants.DB_CONSTANT_FILE_STATUS_KEY_NEW);
//                            int retVal = uploadedFileDAO.updateFileStatus(arrEngCodeFileName[1], arrEngCodeFileName[0],
//                                    ApplicationConstants.DB_CONSTANT_FILE_STATUS_KEY_NEW);
//                        } catch (IOException e) {
//                            //  e.printStackTrace();
//                        } finally {
//                                      //  in.close();
//                            //  out.close();
//                        }
//                    }
//                }
//            } catch (AppException e) {
//                //   e.printStackTrace();
//            }
//        }
//
//    }
//
//}

