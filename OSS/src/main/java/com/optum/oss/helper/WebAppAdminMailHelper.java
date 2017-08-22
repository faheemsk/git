/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.helper;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dto.EmailNotificationDTO;
import com.optum.oss.dto.OrganizationDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.service.impl.EmailServiceImpl;
import com.optum.oss.util.DateUtil;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * @author hpasupuleti
 */
@Component
@PropertySource(value = "classpath:application.properties",ignoreResourceNotFound = true)
public class WebAppAdminMailHelper {

    private static final Logger logger = Logger.getLogger(WebAppAdminMailHelper.class);
    
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
    
    private String imageHTMLPath()
    {
        String imgPath = appURL + "/images/OPTUM-LOGO-Security-Solutions_no-padding.png";
        String imgHtmlPath = "<img src='" + imgPath + "' alt='Product Name'/>";
        
        return imgHtmlPath;
    }
    
    // 1. Application creates an account and sends notification to the user
    public int mail_UserAccountCreated(final UserProfileDTO userProfileDTO,
            final long sessUserID) throws AppException {
        int retVal =0;
        try
        {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType
                        (ApplicationConstants.EMAIL_USER_ACCOUNT_CREATION);
            
            String content = notificationDTL.getEmailContent();
            String userName = userProfileDTO.getFirstName()+" "+userProfileDTO.getLastName();
            
            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$USER_NAME$$",userName);
            content = content.replace("$$USER_EMAIL$$",userProfileDTO.getEmail());
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());
            
            EmailNotificationDTO emailNotificationDTO = new EmailNotificationDTO();
            
            emailNotificationDTO.setEmailNotificationId(notificationDTL.getEmailNotificationId());
            emailNotificationDTO.setUserId(sessUserID);
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setFromEmailId(adminMailID);
            emailNotificationDTO.setToEmailId(userProfileDTO.getEmail());
            emailNotificationDTO.setCcEmailId("");
            emailNotificationDTO.setBccEmailId("");
            emailNotificationDTO.setCreatedDate("");
            emailNotificationDTO.setEmailSubject(notificationDTL.getEmailSubject());
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setEmailSuccessFlag(0);
            emailNotificationDTO.setResendCount(0);
            emailNotificationDTO.setErrorDescription("");
            
            retVal = emailService.insertIntoEmailLog(emailNotificationDTO);
            
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occurred : mail_UserAccountCreated:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mail_UserAccountCreated() :: " + e.getMessage());
        }
        return  retVal;
    }
    
    // 2.	Password sent to User
    public int mail_UserAccountPassword(final UserProfileDTO userProfileDTO,
            final String genPassword,final long sessUserID) throws AppException {
        int retVal =0;
        try
        {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType
                        (ApplicationConstants.EMAIL_USER_PWD_CREATION);
            
            String content = notificationDTL.getEmailContent();
            String userName = userProfileDTO.getFirstName()+" "+userProfileDTO.getLastName();
            
            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$USER_NAME$$",userName);
            content = content.replace("$$USER_PASSWORD$$",genPassword);
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());
            
            EmailNotificationDTO emailNotificationDTO = new EmailNotificationDTO();
            
            emailNotificationDTO.setEmailNotificationId(notificationDTL.getEmailNotificationId());
            emailNotificationDTO.setUserId(sessUserID);
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setFromEmailId(adminMailID);
            emailNotificationDTO.setToEmailId(userProfileDTO.getEmail());
            emailNotificationDTO.setCcEmailId("");
            emailNotificationDTO.setBccEmailId("");
            emailNotificationDTO.setCreatedDate("");
            emailNotificationDTO.setEmailSubject(notificationDTL.getEmailSubject());
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setEmailSuccessFlag(0);
            emailNotificationDTO.setResendCount(0);
            emailNotificationDTO.setErrorDescription("");
            
            retVal = emailService.insertIntoEmailLog(emailNotificationDTO);
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occurred : mail_UserAccountPassword:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mail_UserAccountPassword() :: " + e.getMessage());
        }
        return  retVal;
    }
    
    // 5. Profile updated by the User
    public int mail_UserAccountProfileUpdate(final UserProfileDTO sessionProfileDTO)
            throws AppException {
        int retVal =0;
        try
        {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType
                        (ApplicationConstants.MAIL_USER_PROFILE_UPDATE);
            
            String content = notificationDTL.getEmailContent();
            String userName = sessionProfileDTO.getFirstName()+" "+sessionProfileDTO.getLastName();
            
            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$USER_NAME_1$$",userName);
            content = content.replace("$$USER_NAME_2$$",userName);
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());
            
            EmailNotificationDTO emailNotificationDTO = new EmailNotificationDTO();
            
            emailNotificationDTO.setEmailNotificationId(notificationDTL.getEmailNotificationId());
            emailNotificationDTO.setUserId(sessionProfileDTO.getUserProfileKey());
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setFromEmailId(adminMailID);
            emailNotificationDTO.setToEmailId(sessionProfileDTO.getEmail());
            emailNotificationDTO.setCcEmailId("");
            emailNotificationDTO.setBccEmailId("");
            emailNotificationDTO.setCreatedDate("");
            emailNotificationDTO.setEmailSubject(notificationDTL.getEmailSubject());
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setEmailSuccessFlag(0);
            emailNotificationDTO.setResendCount(0);
            emailNotificationDTO.setErrorDescription("");
            
            retVal = emailService.insertIntoEmailLog(emailNotificationDTO);
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occurred : mail_UserAccountProfileOrPasswordUpdate:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mail_UserAccountProfileOrPasswordUpdate() :: " + e.getMessage());
        }
        return  retVal;
    }
    
    // 3.Change Password 
    public int mail_UserAccountPasswordUpdate(final UserProfileDTO sessionProfileDTO)
            throws AppException {
        int retVal =0;
        try
        {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType
                        (ApplicationConstants.MAIL_USER_PROFILE_PSWD_UPDATE);
            
            String content = notificationDTL.getEmailContent();
            String userName = sessionProfileDTO.getFirstName()+" "+sessionProfileDTO.getLastName();
            
            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$USER_NAME$$",userName);
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());
            
            EmailNotificationDTO emailNotificationDTO = new EmailNotificationDTO();
            
            emailNotificationDTO.setEmailNotificationId(notificationDTL.getEmailNotificationId());
            emailNotificationDTO.setUserId(sessionProfileDTO.getUserProfileKey());
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setFromEmailId(adminMailID);
            emailNotificationDTO.setToEmailId(sessionProfileDTO.getEmail());
            emailNotificationDTO.setCcEmailId("");
            emailNotificationDTO.setBccEmailId("");
            emailNotificationDTO.setCreatedDate("");
            emailNotificationDTO.setEmailSubject(notificationDTL.getEmailSubject());
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setEmailSuccessFlag(0);
            emailNotificationDTO.setResendCount(0);
            emailNotificationDTO.setErrorDescription("");
            
            retVal = emailService.insertIntoEmailLog(emailNotificationDTO);
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occurred : mail_UserAccountProfileOrPasswordUpdate:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mail_UserAccountProfileOrPasswordUpdate() :: " + e.getMessage());
        }
        return  retVal;
    }
    // 6. Account Deactivated
    public int mail_UserAccountDeactivated(final UserProfileDTO userProfileDTO,
            final long sessionUserID)
            throws AppException {
        int retVal =0;
        try
        {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType
                        (ApplicationConstants.EMAIL_USER_ACCOUNT_DEACTIVATED);
            
            String content = notificationDTL.getEmailContent();
            String userName = userProfileDTO.getFirstName()+" "+userProfileDTO.getLastName();
            
            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$USER_NAME_1$$",userName);
            content = content.replace("$$USER_NAME_2$$",userName);
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());
            
            EmailNotificationDTO emailNotificationDTO = new EmailNotificationDTO();
            
            emailNotificationDTO.setEmailNotificationId(notificationDTL.getEmailNotificationId());
            emailNotificationDTO.setUserId(sessionUserID);
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setFromEmailId(adminMailID);
            emailNotificationDTO.setToEmailId(userProfileDTO.getEmail());
            emailNotificationDTO.setCcEmailId("");
            emailNotificationDTO.setBccEmailId("");
            emailNotificationDTO.setCreatedDate("");
            emailNotificationDTO.setEmailSubject(notificationDTL.getEmailSubject());
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setEmailSuccessFlag(0);
            emailNotificationDTO.setResendCount(0);
            emailNotificationDTO.setErrorDescription("");
            
            retVal = emailService.insertIntoEmailLog(emailNotificationDTO);
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occurred : mail_UserAccountDeactivated:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mail_UserAccountDeactivated() :: " + e.getMessage());
        }
        return  retVal;
    }
    
    // 7.Account Reactivated
    public int mail_UserAccountReactivated(final UserProfileDTO userProfileDTO,
            final long sessionUserID, final String updPassword)
            throws AppException {
        int retVal =0;
        try
        {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType
                        (ApplicationConstants.EMAIL_USER_ACCOUNT_REACTIVATED);
            
            String content = notificationDTL.getEmailContent();
            String userName = userProfileDTO.getFirstName()+" "+userProfileDTO.getLastName();
            
            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$USER_NAME_1$$",userName);
            content = content.replace("$$USER_NAME_2$$",userName);
           // content = content.replace("$$PASSWORD$$","\""+updPassword+"\"");
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());
            
            EmailNotificationDTO emailNotificationDTO = new EmailNotificationDTO();
            
            emailNotificationDTO.setEmailNotificationId(notificationDTL.getEmailNotificationId());
            emailNotificationDTO.setUserId(sessionUserID);
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setFromEmailId(adminMailID);
            emailNotificationDTO.setToEmailId(userProfileDTO.getEmail());
            emailNotificationDTO.setCcEmailId("");
            emailNotificationDTO.setBccEmailId("");
            emailNotificationDTO.setCreatedDate("");
            emailNotificationDTO.setEmailSubject(notificationDTL.getEmailSubject());
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setEmailSuccessFlag(0);
            emailNotificationDTO.setResendCount(0);
            emailNotificationDTO.setErrorDescription("");
            
            retVal = emailService.insertIntoEmailLog(emailNotificationDTO);
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occurred : mail_UserAccountReactivated:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mail_UserAccountReactivated() :: " + e.getMessage());
        }
        return  retVal;
    }
    
    //-- 9.	Notify user account locked due to incorrect credentials or inactivity for 60 days or incorrectly answering security questions
    public int mail_UserAccountLocked(final UserProfileDTO userProfileDTO,
            final long sessionUserID)
            throws AppException {
        int retVal =0;
        try
        {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType
                        (ApplicationConstants.EMAIL_USER_ACCOUNT_LOCKED);
            
            String content = notificationDTL.getEmailContent();
            String userName = userProfileDTO.getFirstName()+" "+userProfileDTO.getLastName();
            
            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$USER_NAME_1$$",userName);
           // content = content.replace("$$USER_NAME_2$$",userName);
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());
            
            EmailNotificationDTO emailNotificationDTO = new EmailNotificationDTO();
            
            emailNotificationDTO.setEmailNotificationId(notificationDTL.getEmailNotificationId());
            emailNotificationDTO.setUserId(sessionUserID);
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setFromEmailId(adminMailID);
            emailNotificationDTO.setToEmailId(userProfileDTO.getEmail());
            emailNotificationDTO.setCcEmailId("");
            emailNotificationDTO.setBccEmailId("");
            emailNotificationDTO.setCreatedDate("");
            emailNotificationDTO.setEmailSubject(notificationDTL.getEmailSubject());
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setEmailSuccessFlag(0);
            emailNotificationDTO.setResendCount(0);
            emailNotificationDTO.setErrorDescription("");
            
            retVal = emailService.insertIntoEmailLog(emailNotificationDTO);
           this.mail_To_Admin_UserAccountLocked(userProfileDTO, sessionUserID);
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occurred : mail_UserAccountLocked:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mail_UserAccountLocked() :: " + e.getMessage());
        }
        return  retVal;
    }
    // 10. Notify Administrator when user account is locked due to incorrect credentials or 
    // inactivity for 60 days or incorrectly answering security questions
    private  int mail_To_Admin_UserAccountLocked(final UserProfileDTO userProfileDTO,
            final long sessionUserID)
            throws AppException {
        int retVal =0;
        try
        {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType
                        (ApplicationConstants.EMAIL_TO_ADMIN_USER_ACCOUNT_LOCKED);
            
            String content = notificationDTL.getEmailContent();
            String userName = userProfileDTO.getFirstName()+" "+userProfileDTO.getLastName();
            String subject = notificationDTL.getEmailSubject();
            subject = subject.replace("$$USER_NAME_1$$", userName);
            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$USER_NAME_1$$","Administrator");
            content = content.replace("$$LOCKED_USER_NAME$$",userName);
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());
            
            EmailNotificationDTO emailNotificationDTO = new EmailNotificationDTO();
            
            emailNotificationDTO.setEmailNotificationId(notificationDTL.getEmailNotificationId());
            emailNotificationDTO.setUserId(sessionUserID);
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setFromEmailId(adminMailID);
            emailNotificationDTO.setToEmailId(adminMailID);
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
            logger.debug("Exception occurred : mail_UserAccountLocked:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mail_UserAccountLocked() :: " + e.getMessage());
        }
        return  retVal;
    }
    
    // --11 Notify user when user account is unlocked 
    public int mail_UserAccountUnLocked(final UserProfileDTO userProfileDTO,
            final long sessionUserID, final String updPassword)
            throws AppException {
        int retVal =0;
        try
        {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType
                        (ApplicationConstants.EMAIL_USER_ACCOUNT_UNLOCKED);
            
            String content = notificationDTL.getEmailContent();
            String userName = userProfileDTO.getFirstName()+" "+userProfileDTO.getLastName();
            
            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$USER_NAME_1$$",userName);
            content = content.replace("$$USER_NAME_2$$",userName);
            content = content.replace("$$PASSWORD$$","\""+updPassword+"\"");
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());
            
            EmailNotificationDTO emailNotificationDTO = new EmailNotificationDTO();
            
            emailNotificationDTO.setEmailNotificationId(notificationDTL.getEmailNotificationId());
            emailNotificationDTO.setUserId(sessionUserID);
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setFromEmailId(adminMailID);
            emailNotificationDTO.setToEmailId(userProfileDTO.getEmail());
            emailNotificationDTO.setCcEmailId("");
            emailNotificationDTO.setBccEmailId("");
            emailNotificationDTO.setCreatedDate("");
            emailNotificationDTO.setEmailSubject(notificationDTL.getEmailSubject());
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setEmailSuccessFlag(0);
            emailNotificationDTO.setResendCount(0);
            emailNotificationDTO.setErrorDescription("");
            
            retVal = emailService.insertIntoEmailLog(emailNotificationDTO);
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occurred : mail_UserAccountUnLocked:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mail_UserAccountUnLocked() :: " + e.getMessage());
        }
        return  retVal;
    }
    
    // --12.	Notify user on logging in from different browser or device
    public int mail_UserLoggingInAnotherBrowser(final UserProfileDTO userProfileDTO,
            final long sessUserID) throws AppException {
        int retVal =0;
        try
        {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType
                        (ApplicationConstants.EMAIL_USER_LOGGEING_FROM_DIFFERENT_BROWSER);
            
            String content = notificationDTL.getEmailContent();
            String userName = userProfileDTO.getFirstName()+" "+userProfileDTO.getLastName();
            
            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$USER_NAME$$",userName);
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());
            
            EmailNotificationDTO emailNotificationDTO = new EmailNotificationDTO();
            
            emailNotificationDTO.setEmailNotificationId(notificationDTL.getEmailNotificationId());
            emailNotificationDTO.setUserId(sessUserID);
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setFromEmailId(adminMailID);
            emailNotificationDTO.setToEmailId(userProfileDTO.getEmail());
            emailNotificationDTO.setCcEmailId("");
            emailNotificationDTO.setBccEmailId("");
            emailNotificationDTO.setCreatedDate("");
            emailNotificationDTO.setEmailSubject(notificationDTL.getEmailSubject());
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setEmailSuccessFlag(0);
            emailNotificationDTO.setResendCount(0);
            emailNotificationDTO.setErrorDescription("");
            
            retVal = emailService.insertIntoEmailLog(emailNotificationDTO);
            
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occurred : mail_UserLoggingInAnotherBrowser:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mail_UserAccountCreated() :: " + e.getMessage());
        }
        return  retVal;
    }
    
    // -- 4.   Notify Administrator when user changes their Password .
    public int mail_ToAdminWhenNewUserChangesPasswrod(final String userName ,
            final long sessUserID) throws AppException {
        int retVal =0;
        try
        {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType
                        (ApplicationConstants.MAIL_TO_ADMIN_IF_USER_CHANGES_PSWD_FIRST_TIME);
            
            String content = notificationDTL.getEmailContent();
            //String userName = userProfileDTO.getFirstName()+" "+userProfileDTO.getLastName();
            String subject = notificationDTL.getEmailSubject();
            
            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$ADMIN_NAME$$","Administrator");
            content = content.replace("$$USER_NAME$$",userName);
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());
            
            subject = subject.replace("$$USER_NAME$$",userName);
            
            EmailNotificationDTO emailNotificationDTO = new EmailNotificationDTO();
            
            emailNotificationDTO.setEmailNotificationId(notificationDTL.getEmailNotificationId());
            emailNotificationDTO.setUserId(sessUserID);
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setFromEmailId(adminMailID);
            emailNotificationDTO.setToEmailId(adminMailID);
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
            logger.debug("Exception occurred : mail_UserLoggingInAnotherBrowser:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mail_UserAccountCreated() :: " + e.getMessage());
        }
        return  retVal;
    }
    
    /*Users list for sending password expiry alert email
    -- 8.	Notify account password expiration  */
    public int mailToUserForPasswordExpiryAlert(final UserProfileDTO userDTO) throws AppException {
        int retVal =0;
        try {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType
                        (ApplicationConstants.MAIL_ON_NOTIFY_ACCOUNT_PSWD_EXPIRATION);
            
            String content = notificationDTL.getEmailContent();
            String subject = notificationDTL.getEmailSubject();
            
            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$USER_NAME$$",userDTO.getFirstName()+' '+userDTO.getLastName());
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());
            
            Date resetDate = new SimpleDateFormat("MM/dd/yyyy").parse(userDTO.getPswdResetDate());

            Calendar cal = Calendar.getInstance();
            cal.setTime(resetDate);
            cal.add(Calendar.DATE, Integer.parseInt(ApplicationConstants.APP_PSWD_EXPIRY_DAYS)); //minus number would decrement the days
            Date expiryDate = cal.getTime();

            String expiryDtStr = new SimpleDateFormat("MM/dd/yyyy").format(expiryDate);
            
            content = content.replace("$$DATE_OF_EXPIRATION$$", expiryDtStr);
            
            EmailNotificationDTO emailNotificationDTO = new EmailNotificationDTO();
            
            emailNotificationDTO.setEmailNotificationId(notificationDTL.getEmailNotificationId());
            emailNotificationDTO.setUserId(userDTO.getUserProfileKey());
            emailNotificationDTO.setFromEmailId(adminMailID);
            emailNotificationDTO.setToEmailId(userDTO.getEmail());
            emailNotificationDTO.setCcEmailId("");
            emailNotificationDTO.setBccEmailId("");
            emailNotificationDTO.setCreatedDate("");
            emailNotificationDTO.setEmailSubject(subject);
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setEmailSuccessFlag(0);
            emailNotificationDTO.setResendCount(0);
            emailNotificationDTO.setErrorDescription("");
            
            retVal = emailService.insertIntoEmailLog(emailNotificationDTO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occurred : mailToUserForPasswordExpiryAlert:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mailToUserForPasswordExpiryAlert() :: " + e.getMessage());
        }
        return  retVal;
    }
    
    
     // 1. Application creates an account and sends notification to the user
    public int mail_ClientOrgOnBoarded(final UserProfileDTO userProfileDTO,final OrganizationDTO orgDto) throws AppException {
        int retVal =0;
        try
        {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType
                        (ApplicationConstants.MAIL_ADMIN_CLIENT_ONBOARD_SUCCESS);
            
            String content = notificationDTL.getEmailContent();
            String userName = userProfileDTO.getFirstName()+" "+userProfileDTO.getLastName();
            
            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$USER_NAME$$",userName);
            content = content.replace("$$CLIENT_ORG_NAME$$",orgDto.getOrganizationName());
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());
            
            String subject=notificationDTL.getEmailSubject();
            subject = subject.replace("$$CLIENT_ORG_NAME$$",orgDto.getOrganizationName());
            
            EmailNotificationDTO emailNotificationDTO = new EmailNotificationDTO();
            
            emailNotificationDTO.setEmailNotificationId(notificationDTL.getEmailNotificationId());
            emailNotificationDTO.setUserId(userProfileDTO.getUserProfileKey());
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
            logger.debug("Exception occurred : mail_ClientOrgOnBoarded:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mail_ClientOrgOnBoarded() :: " + e.getMessage());
        }
        return  retVal;
    }
    
     public int mail_ClientOrgOnNotBoarded(final UserProfileDTO userProfileDTO,final OrganizationDTO orgDto) throws AppException {
        int retVal =0;
        try
        {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType
                        (ApplicationConstants.MAIL_ADMIN_CLIENT_ONBOARD_FAIL);
            
            String content = notificationDTL.getEmailContent();
            String userName = userProfileDTO.getFirstName()+" "+userProfileDTO.getLastName();
            
            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$USER_NAME$$",userName);
            content = content.replace("$$CLIENT_ORG_NAME$$",orgDto.getOrganizationName());
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());
            
            String subject=notificationDTL.getEmailSubject();
            subject = subject.replace("$$CLIENT_ORG_NAME$$",orgDto.getOrganizationName());
            
            EmailNotificationDTO emailNotificationDTO = new EmailNotificationDTO();
            
            emailNotificationDTO.setEmailNotificationId(notificationDTL.getEmailNotificationId());
            emailNotificationDTO.setUserId(userProfileDTO.getUserProfileKey());
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
            logger.debug("Exception occurred : mail_ClientOrgOnNotBoarded:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mail_ClientOrgOnNotBoarded() :: " + e.getMessage());
        }
        return  retVal;
    }
}
