/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.helper;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dto.EmailNotificationDTO;
import com.optum.oss.dto.RemediationDTO;
import com.optum.oss.dto.RiskRegisterDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.service.impl.EmailServiceImpl;
import com.optum.oss.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * @author sbhagavatula
 */
@Component
@PropertySource(value = "classpath:application.properties",ignoreResourceNotFound = true)
public class RemediationMailHelper {
    
    private static final Logger logger = Logger.getLogger(RemediationMailHelper.class);
    
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
    private final String adminMailID = System.getProperty("OSS_ADMIN_MAIL_ID");
    private final String appURL = System.getProperty("OSS_APPLICATION_URL"); //http://192.168.149.20:8080/IRMaaS
    
    private String imageHTMLPath(){
        String imgPath = appURL + "/images/OPTUM-LOGO-Security-Solutions_no-padding.png";
        String imgHtmlPath = "<img src='" + imgPath + "' alt='Product Name'/>";
        
        return imgHtmlPath;
    }
    
    public int mailOnRemediationNotify(UserProfileDTO userProfileDTO, RemediationDTO remediationDTO, 
            UserProfileDTO sessionDTO) throws AppException {
        logger.info("Start: RemediationMailHelper : mailOnRemediationNotify");
        int retVal =0;
        try
        {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType
                        (ApplicationConstants.MAIL_REMEDIATION_NOTIFICATION);
            
            String subject = notificationDTL.getEmailSubject();
            subject = subject.replace("$$PLAN_ID$$",remediationDTO.getPlanID());
            
            String content = notificationDTL.getEmailContent();
            String userName = userProfileDTO.getFirstName()+" "+userProfileDTO.getLastName();
            
            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$USER_NAME$$",userName);
            content = content.replace("$$USER_EMAIL$$",userProfileDTO.getEmail());
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());
            
            content = content.replace("$$PLAN_ID$$",remediationDTO.getPlanID());
            content = content.replace("$$PLAN_TITLE$$",remediationDTO.getPlanTitle());
            content = content.replace("$$DUE_DATE$$",remediationDTO.getEndDate());
            
            EmailNotificationDTO emailNotificationDTO = new EmailNotificationDTO();
            
            emailNotificationDTO.setEmailNotificationId(notificationDTL.getEmailNotificationId());
            emailNotificationDTO.setUserId(sessionDTO.getUserProfileKey());
            emailNotificationDTO.setEmailSubject(subject);
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setFromEmailId(adminMailID);
            emailNotificationDTO.setToEmailId(userProfileDTO.getEmail());
            emailNotificationDTO.setCcEmailId("");
            emailNotificationDTO.setBccEmailId("");
            emailNotificationDTO.setCreatedDate("");
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setEmailSuccessFlag(0);
            emailNotificationDTO.setResendCount(0);
            emailNotificationDTO.setErrorDescription("");
            
            retVal = emailService.insertIntoEmailLog(emailNotificationDTO);
            
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occurred : mailOnRemediationNotify:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mailOnRemediationNotify() :: " + e.getMessage());
        }
        logger.info("End: RemediationMailHelper : mailOnRemediationNotify");
        return  retVal;
    }
    
     public int mailOnRegistryNotification( UserProfileDTO userProfileDTO,RiskRegisterDTO riskRegisterDTO,UserProfileDTO sessDTO) throws AppException {
        int retVal =0;
        try
        {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType
                        (ApplicationConstants.MAIL_TO_RISK_REGISTRY_OWNER_ON_NOTIFICATION_DATE);
            
            String content = notificationDTL.getEmailContent();
            String userName = userProfileDTO.getFirstName()+" "+userProfileDTO.getLastName();
            
            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$RISK_REGISTRY_ID$$", riskRegisterDTO.getRiskRegisterID()+"");
            content = content.replace("$$PLAN_TITLE$$", riskRegisterDTO.getPlanTitle());
            content = content.replace("$$DUE_DATE$$", riskRegisterDTO.getStartDate());
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());
            content = content.replace("$$USER_NAME$$",userName);
            
            String subject=notificationDTL.getEmailSubject();
            subject = subject.replace("$$RISK_REGISTRY_ID$$", riskRegisterDTO.getRiskRegisterID()+"");
            
            EmailNotificationDTO emailNotificationDTO = new EmailNotificationDTO();
            
            emailNotificationDTO.setEmailNotificationId(notificationDTL.getEmailNotificationId());
            emailNotificationDTO.setUserId(sessDTO.getUserProfileKey());
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setFromEmailId(adminMailID);
            emailNotificationDTO.setToEmailId(userProfileDTO.getEmail());
            emailNotificationDTO.setCcEmailId("");
            emailNotificationDTO.setBccEmailId("");
            emailNotificationDTO.setCreatedDate("");
            emailNotificationDTO.setEmailSubject(subject);
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setEmailSuccessFlag(0);
            emailNotificationDTO.setResendCount(0);
            emailNotificationDTO.setErrorDescription("");
            
            retVal = emailService.insertIntoEmailLog(emailNotificationDTO);
            
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occurred : mailOnRegistryNotification:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mailOnRegistryNotification() :: " + e.getMessage());
        }
        return  retVal;
    }
}
