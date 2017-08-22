/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.scheduler;

import com.optum.oss.dto.EmailNotificationDTO;
import com.optum.oss.service.impl.EmailServiceImpl;
import com.optum.oss.util.MailSenderUtil;
import java.util.List;
import java.util.Properties;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author hpasupuleti
 */
@Component
public class EmailScheduler {
    
    private static final Logger log = Logger.getLogger(EmailScheduler.class);
    
    private static final EmailScheduler lockObj = new EmailScheduler();
    
    /*
    Start : Autowiring of Classes
    */
    @Autowired
    private EmailServiceImpl emailService;
    
    @Autowired
    private MailSenderUtil mailSenderUtil;
    /*
    End : Autowiring of Classes
    */
    
    
    //@Scheduled(fixedDelay=120000) // 120 Secs
    @Scheduled(fixedDelay = 60000) // 60 Secs
    public void emailSchedulerSendMail() {
        log.info("Start : emailSchedulerSendMail");
        try {
            JavaMailSenderImpl sender = new JavaMailSenderImpl();
            sender.setHost(System.getProperty("OSS_SMTP_HOST"));
            if (true) {
                Properties sessionProperties = new Properties();
                sessionProperties.put("mail.smtp.auth", "true");
                Session mailSession = Session.getInstance(sessionProperties,
                        new javax.mail.Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                String MailUserName = System.getProperty("OSS_ADMIN_MAIL_ID");
                                String MailPassword = System.getProperty("OSS_ADMIN_MAIL_PASSWORD");
                                //log.info("MailUserName "+MailUserName+" MailPassword "+MailPassword);
                                if (MailUserName != null && MailPassword != null) {
                                    return new PasswordAuthentication(MailUserName, MailPassword);
                                } else {
                                    return null;
                                }
                            }
                        });
                sender.setSession(mailSession);
            }
            mailSenderUtil.setMailSender(sender);

            synchronized (lockObj) {
                List<EmailNotificationDTO> senderEmailList = emailService.fetchListOfEmailsToBeSent();
                if ((senderEmailList != null) && (senderEmailList.size() > 0)) {
                    log.info("In : emailSchedulerSendMail:senderEmailList:" + senderEmailList.size());
                    for (EmailNotificationDTO emailNotificationDto : senderEmailList) {
                        try {
                            mailSenderUtil.sendMailSingleRecepient(emailNotificationDto.getFromEmailId(),
                                    emailNotificationDto.getToEmailId(),
                                    emailNotificationDto.getEmailSubject(),
                                    emailNotificationDto.getEmailContent(),
                                    emailNotificationDto.getCcEmailId(),
                                    emailNotificationDto.getBccEmailId());
                            
                            emailService.updateEmailLog(1, 0, emailNotificationDto.getEmailLogId());
                            
                        }
                        catch (Exception e) {
                            log.info("Mail Send Failed...Updating the Email Log Table");
                          //  e.printStackTrace();
                            int resendCount = Integer.valueOf(emailNotificationDto.getResendCount())+1;
                            emailService.updateEmailLog(0,resendCount,
                                    emailNotificationDto.getEmailLogId());
                            
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            log.info("Exception occurred : emailSchdeulerSendMail:" + e.getMessage());
            e.printStackTrace();
        }
    }
}
