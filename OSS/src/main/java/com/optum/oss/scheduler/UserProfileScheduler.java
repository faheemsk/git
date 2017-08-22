/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.scheduler;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.helper.WebAppAdminMailHelper;
import com.optum.oss.service.UserProfileService;
import com.optum.oss.service.impl.EmailServiceImpl;
import java.util.List;
import org.springframework.stereotype.Component;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
/**
 *
 * @author sbhagavatula
 */
@Component
public class UserProfileScheduler {
    
    private static final Logger log = Logger.getLogger(UserProfileScheduler.class);
    
    private static final UserProfileScheduler lockObj = new UserProfileScheduler();

    /*
     Start : Autowiring of Classes
     */
    @Autowired
    private WebAppAdminMailHelper webAdminHelper;

    @Autowired
    private UserProfileService userProfileService;
    /*
     End : Autowiring of Classes
     */

    @Scheduled(cron = "0 30 0 * * *") // Daily Cron Job Runs at 00:30:00 Hrs    cron = "0 30 23 * * *"
    //@Scheduled(fixedDelay = 200000) // 60 Secs
    public void usersListForPasswordExpiryAlert() {
        log.info("Start : UserProfileScheduler: usersListForPasswordExpiryAlert");
        try {
            synchronized (lockObj) {
                int expiry_days = Integer.parseInt(ApplicationConstants.APP_PSWD_EXPIRY_DAYS);
                int notify_days = Integer.parseInt(ApplicationConstants.APP_PSWD_EXPIRY_NOTIF_DAYS);
                int days = expiry_days - notify_days;
                List<UserProfileDTO> usersList = userProfileService.getUsersForPwdExpiryAlert(days,expiry_days);

                if (!usersList.isEmpty()) {
                    for (UserProfileDTO usrProfDto : usersList) {
                        webAdminHelper.mailToUserForPasswordExpiryAlert(usrProfDto);
                    }
                }
            }
        } catch (Exception e) {
            log.info("Exception occurred : usersListForPasswordExpiryAlert:" + e.getMessage());
            e.printStackTrace();
        }
        log.info("End : UserProfileScheduler: usersListForPasswordExpiryAlert");
    }
}
