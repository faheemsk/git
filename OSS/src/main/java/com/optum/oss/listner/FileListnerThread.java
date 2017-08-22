/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.listner;

import java.util.concurrent.ExecutorService;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author hpasupuleti
 */
//@Component
//public class FileListnerThread {
//    
//    private static final Logger logger = Logger.getLogger(FileListnerThread.class);
//    
////    @Autowired
////    private FileUploadListner uploadListner;
//    
//    private ExecutorService executorService;
//    private volatile boolean stopThread = false;
//
//    @PostConstruct
//    public void init() {
////        BasicThreadFactory factory = new BasicThreadFactory.Builder()
////                .namingPattern("FileUpload-Thread-%d").build();
////
////        executorService = Executors.newSingleThreadExecutor(factory);
////        executorService.execute(new DirectoryWatchTask());
//        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
//        
//        taskExecutor.execute(new DirectoryWatchTask());
//                
//    }
//    
//    
//    @PreDestroy
//    public void beandestroy() {
//        this.stopThread = true;
//
//        if (executorService != null) {
//            try {
//                // wait 1 second for closing all threads
//                executorService.awaitTermination(1, TimeUnit.SECONDS);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        }
//    }
//    
//}
