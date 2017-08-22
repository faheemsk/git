/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.scheduler;

import com.optum.oss.service.UserProfileService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author rpanuganti
 */
@Component
public class LoginAttemptScheduler {

    private static final Logger log = Logger.getLogger(LoginAttemptScheduler.class);
    private static final LoginAttemptScheduler lockObj = new LoginAttemptScheduler();
    @Autowired
    private UserProfileService userProfileService;

      @Scheduled(cron = "0 0 0 * * *")
     public void updateLoginAttemptCount() {
        log.info("Start : LoginAttemptScheduler Class : updateLoginAttemptCount");
        try {
            synchronized (lockObj) {

                userProfileService.updateUserLoginAttempts(); 

            }
        } catch (Exception e) {
            log.info("Exception occurred : LoginAttemptScheduler Class:" + e.getMessage());
            e.printStackTrace();
        }

        log.info("End :  LoginAttemptScheduler Class: updateLoginAttemptCount");
    }
}
