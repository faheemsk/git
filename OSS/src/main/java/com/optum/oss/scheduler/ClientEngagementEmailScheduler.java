/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.scheduler;

import com.optum.oss.dto.ManageServiceUserDTO;
import com.optum.oss.helper.ClientEngagementMailHelper;
import com.optum.oss.service.UserProfileService;
import com.optum.oss.service.impl.EmailServiceImpl;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author sathuluri
 */
@Component
public class ClientEngagementEmailScheduler {

    private static final Logger log = Logger.getLogger(ClientEngagementEmailScheduler.class);

    private static final ClientEngagementEmailScheduler lockObj = new ClientEngagementEmailScheduler();

    /*
     Start : Autowiring of Classes
     */
    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private ClientEngagementMailHelper clientEngagementMailHelper;
    @Autowired
    private UserProfileService userProfileService;
    /*
     End : Autowiring of Classes
     */

    @Scheduled(cron = "0 30 1 * * *") // Daily Cron Job Runs at 01:30:00 Hrs
    //@Scheduled(fixedDelay = 300000) // 60 Secs
    public void clientEngagementEmailSchedulerSendMail() {
        log.info("Start : ClientEngagementEmailScheduler: clientEngagementEmailSchedulerSendMail");
        try {
            synchronized (lockObj) {
                List<ManageServiceUserDTO> serviceEngagementDueDateEmails = emailService.fetchListOfEngagementServiceDueDateHasPassedEmails();
                //Start: Send mails to engagement lead when an Engagement Service Due Date Passed.
                if (!serviceEngagementDueDateEmails.isEmpty()) {
                    for (ManageServiceUserDTO serviceDto : serviceEngagementDueDateEmails) {
                        clientEngagementMailHelper.mailToAssignedAnalystDueDatePassed(serviceDto);
                    }
                }
                //End: Send mails to engagement lead when an Engagement Service Due Date Passed.
                List<ManageServiceUserDTO> engagementDueDateApproachingEmails = emailService.fetchListOfEngagementDueDateApproachingEmails();
                //Start: Send mail to engagement lead when an Engagement Due Date Approached before 15 days.
                if (!engagementDueDateApproachingEmails.isEmpty()) {
                    for (ManageServiceUserDTO serviceDto : engagementDueDateApproachingEmails) {
                        clientEngagementMailHelper.mailToEngagementLeadDueDateApproached(serviceDto);
                    }
                }
                //End: Send mail to engagement lead when an Engagement Due Date Approached before 15 days.
                List<ManageServiceUserDTO> engagementDueDateEmails = emailService.fetchListOfEngagementDueDatePassedEmails();
                //Start: Send mail to engagement lead when an Engagement Lead Due Date Passed.
                if (!engagementDueDateEmails.isEmpty()) {
                    for (ManageServiceUserDTO serviceDto : engagementDueDateEmails) {
                        clientEngagementMailHelper.mailToEngagementLeadDueDatePassed(serviceDto);
                    }
                }
            }
            //End: Send mail to engagement lead when an Engagement Lead Due Date Passed.
        } catch (Exception e) {
            log.info("Exception occurred : clientEngagementEmailSchedulerSendMail:" + e.getMessage());
            e.printStackTrace();
        }
        log.info("End : ClientEngagementEmailScheduler: clientEngagementEmailSchedulerSendMail");
    }

}
