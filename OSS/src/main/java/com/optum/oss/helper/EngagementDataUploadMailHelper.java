/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.helper;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.dto.EmailNotificationDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.service.impl.EmailServiceImpl;
import com.optum.oss.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * @author akeshavulu
 */
@Component
@PropertySource(value = "classpath:application.properties",ignoreResourceNotFound = true)
public class EngagementDataUploadMailHelper {
    
     private static final Logger logger = Logger.getLogger(EngagementDataUploadMailHelper.class);
    
    /*
     Start : Autowiring of Fields
     */
    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private EmailServiceImpl emailService;
    /*
     End : Autowiring of Fields
     */
   
    @Value("{application-name}")
    private String applicationName; 
    
    private final String adminMailID = System.getProperty("OSS_ADMIN_MAIL_ID");
    private final String appURL = System.getProperty("OSS_APPLICATION_URL");
    
    private String imageHTMLPath(){
        String imgPath = appURL + "/images/OPTUM-LOGO-Security-Solutions_no-padding.png";
        String imgHtmlPath = "<img src='" + imgPath + "' alt='Product Name'/>";
        
        return imgHtmlPath;
    }
    
     /*
    * Send mail to Engagement/Partner Lead when file is uploaded for a service.
    --22.	Notification to Engagement Analyst upon Successful File Upload
    */
    public int mailOnEngagementFileUpload(UserProfileDTO userProfileDTO, ClientEngagementDTO engagementUploadDTO, 
            UserProfileDTO sessionDTO,String fileName) throws AppException {
        logger.info("Start: ClientEngagementMailHelper : mailOnEngagementSave");
        int retVal =0;
        try
        {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType
                        (ApplicationConstants.MAIL_TO_ENGMNTPARTNER_LEAD_FILE_UPLOAD);
            
            String subject = notificationDTL.getEmailSubject();
            subject = subject.replace("$$ENG_CODE$$",engagementUploadDTO.getEngagementCode());
            subject = subject.replace("$$SERVICE_NAME$$",engagementUploadDTO.getSecurityServiceName());
            
            String content = notificationDTL.getEmailContent();
            String userName = userProfileDTO.getFirstName();
            
            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$ENG_LEAD$$",userName);
            content = content.replace("$$USER_EMAIL$$",userProfileDTO.getEmail());
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());
            
            content = content.replace("$$ENG_CODE$$",engagementUploadDTO.getEngagementCode());
            content = content.replace("$$FILE_NAME$$",fileName);
            content = content.replace("$$SERVICE_NAME$$",engagementUploadDTO.getSecurityServiceName());
            content = content.replace("$$CLIENT_USER$$",sessionDTO.getFirstName()+" "+sessionDTO.getLastName()); 
            
            EmailNotificationDTO emailNotificationDTO = new EmailNotificationDTO();
            
            emailNotificationDTO.setEmailNotificationId(notificationDTL.getEmailNotificationId());
            emailNotificationDTO.setUserId(sessionDTO.getUserProfileKey());
            emailNotificationDTO.setEmailSubject(subject);
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setFromEmailId(adminMailID);
            emailNotificationDTO.setToEmailId(userProfileDTO.getEmail());
            emailNotificationDTO.setCcEmailId("");
            emailNotificationDTO.setBccEmailId("");
           emailNotificationDTO.setCreatedDate(dateUtil.generateDBCurrentDateInString()+"");
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setEmailSuccessFlag(0);
            emailNotificationDTO.setResendCount(0);
            emailNotificationDTO.setErrorDescription("");
            
            retVal = emailService.insertIntoEmailLog(emailNotificationDTO);
            
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occurred : mailOnEngagementSave:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mailOnEngagementSave() :: " + e.getMessage());
        }
        logger.info("End: ClientEngagementMailHelper : mailOnEngagementSave");
        return  retVal;
    }
    
    
      /*
    * Send mail to Engagement Lead when all the services are locked.
    --23.	Notification of Engagement File Upload
    */
    public int mailToEngmntLeadAllServicesLocked(UserProfileDTO userProfileDTO, ClientEngagementDTO engagementUploadDTO, String htmlFileContent,
            UserProfileDTO sessionDTO) throws AppException {
        logger.info("Start: ClientEngagementMailHelper : mailOnEngagementSave");
        int retVal =0;
        try
        {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType
                        (ApplicationConstants.MAIL_TO_ENGMNTPARTNER_LEAD_ON_ALL_SERVICES_UPLOAD);
            
            String subject = notificationDTL.getEmailSubject();
            subject = subject.replace("$$ENG_CODE$$",engagementUploadDTO.getEngagementCode());
            
            String content = notificationDTL.getEmailContent();
            String userName = userProfileDTO.getFirstName()+" "+userProfileDTO.getLastName();
            
            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$ENG_LEAD$$",userName);
           // content = content.replace("$$USER_EMAIL$$",userProfileDTO.getEmail());
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());
            
            content = content.replace("$$ENG_CODE$$",engagementUploadDTO.getEngagementCode());
            content = content.replace("$$HTML_CONTENT_OF_FILE$$",htmlFileContent);// 
           // content = content.replace("$$SERVICE_NAME$$",engagementUploadDTO.getSecurityServiceCode());
            content = content.replace("$$USER_NAME$$",sessionDTO.getFirstName()+" "+sessionDTO.getLastName());
            
            EmailNotificationDTO emailNotificationDTO = new EmailNotificationDTO();
            
            emailNotificationDTO.setEmailNotificationId(notificationDTL.getEmailNotificationId());
            emailNotificationDTO.setUserId(sessionDTO.getUserProfileKey());
            emailNotificationDTO.setEmailSubject(subject);
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setFromEmailId(adminMailID);
            emailNotificationDTO.setToEmailId(userProfileDTO.getEmail());
            emailNotificationDTO.setCcEmailId("");
            emailNotificationDTO.setBccEmailId("");
            emailNotificationDTO.setCreatedDate(dateUtil.generateDBCurrentDateInString()+"");
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setEmailSuccessFlag(0);
            emailNotificationDTO.setResendCount(0);
            emailNotificationDTO.setErrorDescription("");
            
            retVal = emailService.insertIntoEmailLog(emailNotificationDTO);
            
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occurred : mailOnEngagementSave:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mailOnEngagementSave() :: " + e.getMessage());
        }
        logger.info("End: ClientEngagementMailHelper : mailOnEngagementSave");
        return  retVal;
    }
    
      /*
    * Send mail to Engagement Analyst when a service is locked.
    --26.	Mail sent to Engagement Analyst on locking Service 
    */
    public int mailToEngmntAnalystServiceLocked(UserProfileDTO userProfileDTO, ClientEngagementDTO engagementUploadDTO,
            UserProfileDTO sessionDTO) throws AppException {
        logger.info("Start: ClientEngagementMailHelper : mailOnEngagementSave");
        int retVal = 0;
        try {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType(ApplicationConstants.MAIL_TO_ENGMNT_ANALYST_ON_LOCK_SERVICE);

            String subject = notificationDTL.getEmailSubject();
            subject = subject.replace("$$SERVICE_NAME$$", engagementUploadDTO.getSecurityServiceName());
            subject = subject.replace("$$ENG_CODE$$", engagementUploadDTO.getEngagementCode());

            String content = notificationDTL.getEmailContent();
            String userName = userProfileDTO.getFirstName();

            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$ENG_ANALYST$$", userName);
            // content = content.replace("$$USER_EMAIL$$",userProfileDTO.getEmail());
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());

            content = content.replace("$$SERVICE_NAME$$", engagementUploadDTO.getSecurityServiceName());
            content = content.replace("$$ENG_CODE$$", engagementUploadDTO.getEngagementCode());
           // content = content.replace("$$SERVICE_NAME$$",engagementUploadDTO.getSecurityServiceCode());
            // content = content.replace("$$USER_NAME$$",sessionDTO.getFirstName()+" "+sessionDTO.getLastName());

            EmailNotificationDTO emailNotificationDTO = new EmailNotificationDTO();

            emailNotificationDTO.setEmailNotificationId(notificationDTL.getEmailNotificationId());
            emailNotificationDTO.setUserId(sessionDTO.getUserProfileKey());
            emailNotificationDTO.setEmailSubject(subject);
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setFromEmailId(adminMailID);
            emailNotificationDTO.setToEmailId(userProfileDTO.getEmail());
            emailNotificationDTO.setCcEmailId("");
            emailNotificationDTO.setBccEmailId("");
            emailNotificationDTO.setCreatedDate(dateUtil.generateDBCurrentDateInString()+"");
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setEmailSuccessFlag(0);
            emailNotificationDTO.setResendCount(0);
            emailNotificationDTO.setErrorDescription("");

            retVal = emailService.insertIntoEmailLog(emailNotificationDTO);

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occurred : mailOnEngagementSave:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mailOnEngagementSave() :: " + e.getMessage());
        }
        logger.info("End: ClientEngagementMailHelper : mailOnEngagementSave");
        return retVal;
    }

    /*
     * Send mail to Engagement Analyst when a service is unlocked.
    --27.	Mail sent to Engagement Analyst on unlocking Service 
     */
    public int mailToEngmntAnalystServiceUnlocked(UserProfileDTO userProfileDTO, ClientEngagementDTO engagementUploadDTO,
            UserProfileDTO sessionDTO) throws AppException {
        logger.info("Start: ClientEngagementMailHelper : mailOnEngagementSave");
        int retVal = 0;
        try {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType(ApplicationConstants.MAIL_TO_ENGMNT_ANALYST_ON_UNLOCK_SERVICE);

            String subject = notificationDTL.getEmailSubject();
            subject = subject.replace("$$SERVICE_NAME$$", engagementUploadDTO.getSecurityServiceName());
            subject = subject.replace("$$ENG_CODE$$", engagementUploadDTO.getEngagementCode());

            String content = notificationDTL.getEmailContent();
            String userName = userProfileDTO.getFirstName();

            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$ENG_ANALYST$$", userName);
            // content = content.replace("$$USER_EMAIL$$",userProfileDTO.getEmail());
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());

            content = content.replace("$$SERVICE_NAME$$", engagementUploadDTO.getSecurityServiceName());
            content = content.replace("$$ENG_CODE$$", engagementUploadDTO.getEngagementCode());

            EmailNotificationDTO emailNotificationDTO = new EmailNotificationDTO();

            emailNotificationDTO.setEmailNotificationId(notificationDTL.getEmailNotificationId());
            emailNotificationDTO.setUserId(sessionDTO.getUserProfileKey());
            emailNotificationDTO.setEmailSubject(subject);
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setFromEmailId(adminMailID);
            emailNotificationDTO.setToEmailId(userProfileDTO.getEmail());
            emailNotificationDTO.setCcEmailId("");
            emailNotificationDTO.setBccEmailId("");
            emailNotificationDTO.setCreatedDate(dateUtil.generateDBCurrentDateInString()+"");
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setEmailSuccessFlag(0);
            emailNotificationDTO.setResendCount(0);
            emailNotificationDTO.setErrorDescription("");

            retVal = emailService.insertIntoEmailLog(emailNotificationDTO);

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occurred : mailOnEngagementSave:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mailOnEngagementSave() :: " + e.getMessage());
        }
        logger.info("End: ClientEngagementMailHelper : mailOnEngagementSave");
        return retVal;
    }
    
    
     /*
    * Send mail to engagement analyst/partner user when file is uploaded for a service.
    --24 Notify user - file check failure due to malicious file upload.
    */
    public int mailToAnalystOnMaliciousFileUpload(ClientEngagementDTO engagementUploadDTO) throws AppException {
        logger.info("Start: ClientEngagementMailHelper : mailOnEngagementSave");
        int retVal =0;
        try
        {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType
                        (ApplicationConstants.MAIL_TO_ANALYST_ON_MALICIOUS_FILE_UPLOAD);
            
            String subject = notificationDTL.getEmailSubject();
            subject = subject.replace("$$ENG_CODE$$",engagementUploadDTO.getEngagementCode());
            
            String content = notificationDTL.getEmailContent();
            String userName = engagementUploadDTO.getCreatedByUser();
            String fileName = engagementUploadDTO.getUploadFileName();
            
            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$ANALYST$$",userName);
            content = content.replace("$$FILE_NAME$$",fileName);
            content = content.replace("$$DATE_TIME_STAMP$$",engagementUploadDTO.getCreatedDate());
            content = content.replace("$$ENG_CODE$$",engagementUploadDTO.getEngagementCode());
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());
            
            EmailNotificationDTO emailNotificationDTO = new EmailNotificationDTO();
            
            emailNotificationDTO.setEmailNotificationId(notificationDTL.getEmailNotificationId());
            emailNotificationDTO.setUserId(engagementUploadDTO.getCreatedByUserID());
            emailNotificationDTO.setEmailSubject(subject);
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setFromEmailId(adminMailID);
            emailNotificationDTO.setToEmailId(engagementUploadDTO.getAnalystEmailID());
            emailNotificationDTO.setCcEmailId("");
            emailNotificationDTO.setBccEmailId("");
            emailNotificationDTO.setCreatedDate(dateUtil.generateDBCurrentDateInString()+"");
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setEmailSuccessFlag(0);
            emailNotificationDTO.setResendCount(0);
            emailNotificationDTO.setErrorDescription("");
            
            retVal = emailService.insertIntoEmailLog(emailNotificationDTO);
            //this.mailToLeadOnMaliciousFileUpload( engagementUploadDTO);
            
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occurred : mailOnMaliciousFileUpload:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mailOnMaliciousFileUpload() :: " + e.getMessage());
        }
        logger.info("End: ClientEngagementMailHelper : mailOnMaliciousFileUpload");
        return  retVal;
    }
    
    
     /*
    * Send mail to engagement lead/partner lead when file is uploaded for a service.
    --25.	Notify Engagement Lead on file check failure due to malicious file upload.
    */
    public int mailToLeadOnMaliciousFileUpload( ClientEngagementDTO engagementUploadDTO) throws AppException {
        logger.info("Start: ClientEngagementMailHelper : mailToLeadOnMaliciousFileUpload");
        int retVal =0;
        try
        {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType
                        (ApplicationConstants.MAIL_TO_LEAD_USER_ON_MALICIOUS_FILE_UPLOAD);
            
            String subject = notificationDTL.getEmailSubject();
            subject = subject.replace("$$ENG_CODE$$",engagementUploadDTO.getEngagementCode());
            
            String content = notificationDTL.getEmailContent();
            String userName = engagementUploadDTO.getEngagementLeadClientUserName();
            
            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$LEAD$$",userName);
            content = content.replace("$$FILE_NAME$$",engagementUploadDTO.getUploadFileName());
            content = content.replace("$$ANALYST$$",userName + " ("+engagementUploadDTO.getEngagementLeadClientEmailId()+")");
            content = content.replace("$$DATE_TIME_STAMP$$",engagementUploadDTO.getCreatedDate());
            content = content.replace("$$ENG_CODE$$",engagementUploadDTO.getEngagementCode());
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());
            
            EmailNotificationDTO emailNotificationDTO = new EmailNotificationDTO();
            
            emailNotificationDTO.setEmailNotificationId(notificationDTL.getEmailNotificationId());
            emailNotificationDTO.setUserId(engagementUploadDTO.getEngagementLeadClientUserID());
            emailNotificationDTO.setEmailSubject(subject);
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setFromEmailId(adminMailID);
            emailNotificationDTO.setToEmailId(engagementUploadDTO.getEngagementLeadClientEmailId());
            emailNotificationDTO.setCcEmailId("");
            emailNotificationDTO.setBccEmailId("");
            emailNotificationDTO.setCreatedDate(dateUtil.generateDBCurrentDateInString()+"");
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setEmailSuccessFlag(0);
            emailNotificationDTO.setResendCount(0);
            emailNotificationDTO.setErrorDescription("");
            
            retVal = emailService.insertIntoEmailLog(emailNotificationDTO);
            
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occurred : mailToLeadOnMaliciousFileUpload:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mailToLeadOnMaliciousFileUpload() :: " + e.getMessage());
        }
        logger.info("End: ClientEngagementMailHelper : mailToLeadOnMaliciousFileUpload");
        return  retVal;
    }
}
