/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.helper;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.dto.EmailNotificationDTO;
import com.optum.oss.dto.EngagementPartnerUserDTO;
import com.optum.oss.dto.ManageServiceUserDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.service.impl.EmailServiceImpl;
import com.optum.oss.util.DateUtil;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Used to send emails for client engagement module
 *
 * @author sbhagavatula
 */
@Component
@PropertySource(value = "classpath:application.properties",ignoreResourceNotFound = true)
public class ClientEngagementMailHelper {
    
    private static final Logger logger = Logger.getLogger(ClientEngagementMailHelper.class);
    
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
    
    /*
    * Send mail to Internal User when an engagement is created.
    -- 13.	Mail sent on Engagement creation
    */
    public int mailOnEngagementSave(UserProfileDTO userProfileDTO, ClientEngagementDTO engagementDTO, 
            UserProfileDTO sessionDTO) throws AppException {
        logger.info("Start: ClientEngagementMailHelper : mailOnEngagementSave");
        int retVal =0;
        try
        {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType
                        (ApplicationConstants.MAIL_ENGMNT_CREATION);
            
            String subject = notificationDTL.getEmailSubject();
            subject = subject.replace("$$ENG_CODE$$",engagementDTO.getEngagementCode());
            
            String content = notificationDTL.getEmailContent();
            String userName = userProfileDTO.getFirstName()+" "+userProfileDTO.getLastName();
            
            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$USER_NAME$$",userName);
            content = content.replace("$$USER_EMAIL$$",userProfileDTO.getEmail());
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());
            
            content = content.replace("$$ENG_CODE$$",engagementDTO.getEngagementCode());
            content = content.replace("$$CLNT_ORG_NAME$$",engagementDTO.getClientName());
            content = content.replace("$$PKG_NAME$$",engagementDTO.getSecurityPackageObj().getSecurityPackageName());
            content = content.replace("$$ENG_STRT_DATE$$",engagementDTO.getEstimatedStartDate());
            
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
            logger.debug("Exception occurred : mailOnEngagementSave:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mailOnEngagementSave() :: " + e.getMessage());
        }
        logger.info("End: ClientEngagementMailHelper : mailOnEngagementSave");
        return  retVal;
    }
    
    /*
    * Send mail to Internal User when an engagement is updated.
    -- 14.	Mail sent on updating Engagement
    */
    public int mailOnEngagementUpdate(UserProfileDTO userProfileDTO, ClientEngagementDTO engagementDTO, 
            UserProfileDTO sessionDTO) throws AppException {
        logger.info("Start: ClientEngagementMailHelper : mailOnEngagementUpdate");
        int retVal =0;
        try {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType
                        (ApplicationConstants.MAIL_ENGMNT_UPDATION);
            
            String subject = notificationDTL.getEmailSubject();
            subject = subject.replace("$$ENG_CODE$$",engagementDTO.getEngagementCode());
            
            String content = notificationDTL.getEmailContent();
            String userName = userProfileDTO.getFirstName()+" "+userProfileDTO.getLastName();
            
            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$USER_NAME$$",userName);
            content = content.replace("$$USER_EMAIL$$",userProfileDTO.getEmail());
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());
            
            content = content.replace("$$ENG_CODE$$",engagementDTO.getEngagementCode());
            content = content.replace("$$CLNT_ORG_NAME$$",engagementDTO.getClientName());
            content = content.replace("$$PKG_NAME$$",engagementDTO.getSecurityPackageObj().getSecurityPackageName());
            content = content.replace("$$ENG_STRT_DATE$$",engagementDTO.getEstimatedStartDate());
            
            String updatedUserName = sessionDTO.getFirstName()+" "+sessionDTO.getLastName() + " ("+ sessionDTO.getEmail() + ")";
            content = content.replace("$$UPDATED_USER_NAME$$",updatedUserName);
            
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
            logger.debug("Exception occurred : mailOnEngagementUpdate:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mailOnEngagementUpdate() :: " + e.getMessage());
        }
        logger.info("End: ClientEngagementMailHelper : mailOnEngagementUpdate");
        return  retVal;
    }
    
    /*
    * Send mail to Client Lead when an engagement is created or updated.
    -- 15.	Mail sent on assigning Client Engagement Lead
    */
    public int mailToClientLead(UserProfileDTO userProfileDTO, ClientEngagementDTO engagementDTO, 
            UserProfileDTO sessionDTO) throws AppException {
        logger.info("Start: ClientEngagementMailHelper : mailToClientLead");
        int retVal =0;
        try {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType
                        (ApplicationConstants.MAIL_ASSIGN_CLIENT_ENGMNT_LEAD);
            
            String subject = notificationDTL.getEmailSubject();
            subject = subject.replace("$$ENG_CODE$$",engagementDTO.getEngagementCode());
            
            String content = notificationDTL.getEmailContent();
            String userName = userProfileDTO.getFirstName()+" "+userProfileDTO.getLastName();
            
            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$USER_NAME$$",userName);
            content = content.replace("$$USER_EMAIL$$",userProfileDTO.getEmail());
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());
            
            content = content.replace("$$ENG_CODE$$",engagementDTO.getEngagementCode());
            content = content.replace("$$HELPDESK_EMAIL$$","");
            content = content.replace("$$CONTACT_NUMBER$$","");
            
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
            logger.debug("Exception occurred : mailToClientLead:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mailToClientLead() :: " + e.getMessage());
        }
        logger.info("End: ClientEngagementMailHelper : mailToClientLead");
        return  retVal;
    }
    
    /*
    * Send mail to Partner Engagement Lead when an engagement is created or updated.
    -- 16.	Mail sent on assigning Partner Engagement Lead
    */
    public int mailToPartnerLead(UserProfileDTO userProfileDTO, ClientEngagementDTO engagementDTO, 
            UserProfileDTO sessionDTO) throws AppException {
        logger.info("Start: ClientEngagementMailHelper : mailToPartnerLead");
        int retVal =0;
        try {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType
                        (ApplicationConstants.MAIL_ASSIGN_PARTNER_ENGMNT_LEAD);
            
            String subject = notificationDTL.getEmailSubject();
            subject = subject.replace("$$ENG_CODE$$",engagementDTO.getEngagementCode());
            
            String content = notificationDTL.getEmailContent();
            String userName = userProfileDTO.getFirstName()+" "+userProfileDTO.getLastName();
            
            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$USER_NAME$$",userName);
            content = content.replace("$$USER_EMAIL$$",userProfileDTO.getEmail());
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());
            
            content = content.replace("$$ENG_CODE$$",engagementDTO.getEngagementCode());
            content = content.replace("$$HELPDESK_EMAIL$$","");
            content = content.replace("$$CONTACT_NUMBER$$","");
            
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
            logger.debug("Exception occurred : mailToPartnerLead:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mailToPartnerLead() :: " + e.getMessage());
        }
        logger.info("End: ClientEngagementMailHelper : mailToPartnerLead");
        return  retVal;
    }
    
    /*
    * Send mail to Analyst User when an assigned user to service in Manage Users to Services.
    -- 17.	Mail sent to assigned users on scheduling Engagement
    */
    public int mailToAssignedUser(UserProfileDTO assignedAnalystUserDto, ClientEngagementDTO engagementDTO, 
            UserProfileDTO sessionDTO) throws AppException {
        logger.info("Start: ClientEngagementMailHelper : mailToAssignedUser");
        int retVal =0;
        try {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType
                        (ApplicationConstants.MAIL_ASSIGN_USER_SCHDULE);
            
            String subject = notificationDTL.getEmailSubject();
            subject = subject.replace("$$ENG_CODE$$",engagementDTO.getEngagementCode());
            
            String content = notificationDTL.getEmailContent();
            String userName = assignedAnalystUserDto.getFirstName()+" "+assignedAnalystUserDto.getLastName();
            String engagementLeadName = sessionDTO.getFirstName()+" "+sessionDTO.getLastName() ;
            
            //Start: Fixed for Bug Id: IN:020994
            Date date = new SimpleDateFormat("MM/dd/yyyy").parse(engagementDTO.getEstimatedEndDate());
            String engagementEndDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
            //End: Fixed for Bug Id: IN:020994
            
            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$USER_NAME$$",userName);
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());
            
            content = content.replace("$$ENG_CODE$$",engagementDTO.getEngagementCode());
            content = content.replace("$$ENGMENT_SCHEDULED_BY$$",engagementLeadName + " ("+ sessionDTO.getEmail() + ")");//engagement scheduled by (engagement lead)
            content = content.replace("$$ENGMENT_LEAD$$",engagementLeadName);//engagement lead name
            content = content.replace("$$ENGMENT_END_DATE$$",engagementEndDate);//Engagement Estimated End Date
            
            EmailNotificationDTO emailNotificationDTO = new EmailNotificationDTO();
            emailNotificationDTO.setEmailNotificationId(notificationDTL.getEmailNotificationId());
            emailNotificationDTO.setUserId(sessionDTO.getUserProfileKey());
            emailNotificationDTO.setEmailSubject(subject);
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setFromEmailId(adminMailID);
            emailNotificationDTO.setToEmailId(assignedAnalystUserDto.getEmail());
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
            logger.debug("Exception occurred : mailToAssignedUser:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mailToAssignedUser() :: " + e.getMessage());
        }
        logger.info("End: ClientEngagementMailHelper : mailToAssignedUser");
        return  retVal;
    }
    
    /*
    * Send mail to previous assigned Analyst User when an reassigned user to service in Manage Users to Services.
    -- 18 Mail sent to previously assigned users on reassigning Engagement
    */
    public int mailToReAssignedUser(UserProfileDTO reassignedAnalystUserDto, ManageServiceUserDTO manageServiceDto, 
            UserProfileDTO sessionDTO, UserProfileDTO oldAssignedUserDto) throws AppException {
        logger.info("Start: ClientEngagementMailHelper : mailToReAssignedUser");
        int retVal =0;
        try {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType
                        (ApplicationConstants.MAIL_REASSIGN_USER_SCHDULE);
            
            String subject = notificationDTL.getEmailSubject();
            subject = subject.replace("$$ENG_CODE$$",manageServiceDto.getClientEngagementCode());
            
            String content = notificationDTL.getEmailContent();
            String reAssignedUserName = reassignedAnalystUserDto.getFirstName()+" "+reassignedAnalystUserDto.getLastName() + " ("+ reassignedAnalystUserDto.getEmail() + ")";
            String olduserName = oldAssignedUserDto.getFirstName()+" "+oldAssignedUserDto.getLastName();
            //String loginUserName = sessionDTO.getFirstName()+" "+sessionDTO.getLastName();
            
            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$ENG_CODE$$",manageServiceDto.getClientEngagementCode());
            content = content.replace("$$ANLST_OR_PRTNR$$", "Analyst");
            content = content.replace("$$ASSIGNED_TO$$", reAssignedUserName);//reassigned username
            content = content.replace("$$USER_NAME$$",olduserName);//old assigned username
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());
            
            content = content.replace("$$ASSIGNED_BY$$", reAssignedUserName);//reassigned username
            
            EmailNotificationDTO emailNotificationDTO = new EmailNotificationDTO();
            emailNotificationDTO.setEmailNotificationId(notificationDTL.getEmailNotificationId());
            emailNotificationDTO.setUserId(sessionDTO.getUserProfileKey());
            emailNotificationDTO.setEmailSubject(subject);
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setFromEmailId(adminMailID);
            emailNotificationDTO.setToEmailId(oldAssignedUserDto.getEmail());
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
            logger.debug("Exception occurred : mailToAssignedUser:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mailToAssignedUser() :: " + e.getMessage());
        }
        logger.info("End: ClientEngagementMailHelper : mailToReAssignedUser");
        return  retVal;
    }
    
    /*
     * Send mail to assigned analyst user when an Engagement Service Due Date Passed.
    -- 19.	Mail sent to assigned Analyst/Partner when Engagement Service due date has passed
     */
    public int mailToAssignedAnalystDueDatePassed(ManageServiceUserDTO manageServiceDto) throws AppException {
        logger.info("Start: ClientEngagementMailHelper : mailToAssignedAnalystDueDatePassed");
        int retVal = 0;
        try {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType(ApplicationConstants.MAIL_ENGMNT_DUE_DATE_PASSED);

            String subject = notificationDTL.getEmailSubject();
            subject = subject.replace("$$ENG_CODE$$", manageServiceDto.getClientEngagementCode());

            String content = notificationDTL.getEmailContent();

            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$USER_NAME$$", manageServiceDto.getAnalystName());
            content = content.replace("$$ENG_CODE$$", manageServiceDto.getClientEngagementCode() + " + ");
            content = content.replace("$$SERVICE_NAME$$", manageServiceDto.getServiceName());
            content = content.replace("$$ENGMENT_LEAD$$", manageServiceDto.getOrganizationName());
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());

            EmailNotificationDTO emailNotificationDTO = new EmailNotificationDTO();
            emailNotificationDTO.setEmailNotificationId(notificationDTL.getEmailNotificationId());
            emailNotificationDTO.setUserId(1);//user id as admin
            emailNotificationDTO.setEmailSubject(subject);
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setFromEmailId(adminMailID);
            emailNotificationDTO.setToEmailId(manageServiceDto.getAnalystEmail());
            emailNotificationDTO.setCcEmailId("");
            emailNotificationDTO.setBccEmailId("");
            emailNotificationDTO.setCreatedDate("");
            emailNotificationDTO.setEmailSuccessFlag(0);
            emailNotificationDTO.setResendCount(0);
            emailNotificationDTO.setErrorDescription("");

            retVal = emailService.insertIntoEmailLog(emailNotificationDTO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occurred : mailToAssignedAnalystDueDatePassed:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mailToAssignedAnalystDueDatePassed() :: " + e.getMessage());
        }
        logger.info("End: ClientEngagementMailHelper : mailToAssignedAnalystDueDatePassed");
        return retVal;
    }

    /*
     * Send mail to engagement lead when an Engagement Due Date Approached before 15 days.
    -- 20.	Mail sent to Engagement Lead when Engagement due date is approaching
     */
    public int mailToEngagementLeadDueDateApproached(ManageServiceUserDTO manageServiceDto) throws AppException {
        logger.info("Start: ClientEngagementMailHelper : mailToEngagementLeadDueDateApproached");
        int retVal = 0;
        try {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType(ApplicationConstants.MAIL_ENGMNT_DUE_DATE_APRCHD);

            String subject = notificationDTL.getEmailSubject();
            subject = subject.replace("$$ENG_CODE$$", manageServiceDto.getClientEngagementCode());

            String content = notificationDTL.getEmailContent();
            //String loginUserName = sessionDTO.getFirstName()+" "+sessionDTO.getLastName();

            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$USER_NAME$$", manageServiceDto.getEngagementLeadName());
            content = content.replace("$$ENG_CODE$$", manageServiceDto.getClientEngagementCode());
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());

            EmailNotificationDTO emailNotificationDTO = new EmailNotificationDTO();
            emailNotificationDTO.setEmailNotificationId(notificationDTL.getEmailNotificationId());
            emailNotificationDTO.setUserId(1);//user id as admin
            emailNotificationDTO.setEmailSubject(subject);
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setFromEmailId(adminMailID);
            emailNotificationDTO.setToEmailId(manageServiceDto.getEngagementLeadEmail());
            emailNotificationDTO.setCcEmailId("");
            emailNotificationDTO.setBccEmailId("");
            emailNotificationDTO.setCreatedDate("");
            emailNotificationDTO.setEmailSuccessFlag(0);
            emailNotificationDTO.setResendCount(0);
            emailNotificationDTO.setErrorDescription("");

            retVal = emailService.insertIntoEmailLog(emailNotificationDTO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occurred : mailToEngagementLeadDueDateApproached:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mailToEngagementLeadDueDateApproached() :: " + e.getMessage());
        }
        logger.info("End: ClientEngagementMailHelper : mailToEngagementLeadDueDateApproached");
        return retVal;
    }

    /*
     * Send mail to engagement lead when an Engagement Lead Due Date Passed.
    --21.	Mail sent to Engagement Lead when Engagement due date has passed
     */
    public int mailToEngagementLeadDueDatePassed(ManageServiceUserDTO manageServiceDto) throws AppException {
        logger.info("Start: ClientEngagementMailHelper : mailToEngagementLeadDueDatePassed");
        int retVal = 0;
        try {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType(ApplicationConstants.MAIL_TO_ENGMNT_LEAD_DUE_DATE_PASSED);

            String subject = notificationDTL.getEmailSubject();
            subject = subject.replace("$$ENG_CODE$$", manageServiceDto.getClientEngagementCode());

            String content = notificationDTL.getEmailContent();

            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$USER_NAME$$", manageServiceDto.getUserName());//Here user name is for Internal / Partner Analyst or Engagement Lead
            content = content.replace("$$ENG_CODE$$", manageServiceDto.getClientEngagementCode());
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());

            EmailNotificationDTO emailNotificationDTO = new EmailNotificationDTO();
            emailNotificationDTO.setEmailNotificationId(notificationDTL.getEmailNotificationId());
            emailNotificationDTO.setUserId(1);//user id as admin
            emailNotificationDTO.setEmailSubject(subject);
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setFromEmailId(adminMailID);
            emailNotificationDTO.setToEmailId(manageServiceDto.getAnalystEmail());//Here email is for Internal / Partner Analyst or Engagement Lead
            emailNotificationDTO.setCcEmailId("");
            emailNotificationDTO.setBccEmailId("");
            emailNotificationDTO.setCreatedDate("");
            emailNotificationDTO.setEmailSuccessFlag(0);
            emailNotificationDTO.setResendCount(0);
            emailNotificationDTO.setErrorDescription("");

            retVal = emailService.insertIntoEmailLog(emailNotificationDTO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occurred : mailToEngagementLeadDueDatePassed:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mailToEngagementLeadDueDatePassed() :: " + e.getMessage());
        }
        logger.info("End: ClientEngagementMailHelper : mailToEngagementLeadDueDatePassed");
        return retVal;
    }
    
    /*
     * SEND EMAIL TO CLIENT ENGAGMENT LEAD OF FINAL REPORT PUBLICATION
    28.	Mail sent to Partner Engagement Lead and Client Engagement Lead of final report publication
     */
    public int mailToClientEngagementLeadOfEngagementPublish(ClientEngagementDTO clientEngagementDTO) throws AppException {
        logger.info("Start: ClientEngagementMailHelper : mailToClientAndPartnerEngagementLeadOfEngagementPublish");
        int retVal = 0;
        try {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType(ApplicationConstants.MAIL_TO_ENGAGEMENT_PARTNER_CLIENT_LEAD_PUBLISHED);

            String subject = notificationDTL.getEmailSubject();
            subject = subject.replace("$$ENG_CODE$$", clientEngagementDTO.getEngagementCode());

            String content = notificationDTL.getEmailContent();

            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$USER_NAME$$", clientEngagementDTO.getEngagementLeadClientUserName());
            content = content.replace("$$ENG_CODE$$", clientEngagementDTO.getEngagementCode());
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());

            EmailNotificationDTO emailNotificationDTO = new EmailNotificationDTO();
            emailNotificationDTO.setEmailNotificationId(notificationDTL.getEmailNotificationId());
            emailNotificationDTO.setUserId(1);//user id as admin
            emailNotificationDTO.setEmailSubject(subject);
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setFromEmailId(adminMailID);
            emailNotificationDTO.setToEmailId(clientEngagementDTO.getEngagementLeadClientEmailId());//client lead email
            emailNotificationDTO.setCcEmailId("");
            emailNotificationDTO.setBccEmailId("");
            emailNotificationDTO.setCreatedDate("");
            emailNotificationDTO.setEmailSuccessFlag(0);
            emailNotificationDTO.setResendCount(0);
            emailNotificationDTO.setErrorDescription("");

            retVal = emailService.insertIntoEmailLog(emailNotificationDTO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occurred : mailToClientAndPartnerEngagementLeadOfEngagementPublish:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mailToClientAndPartnerEngagementLeadOfEngagementPublish() :: " + e.getMessage());
        }
        logger.info("End: ClientEngagementMailHelper : mailToClientAndPartnerEngagementLeadOfEngagementPublish");
        return retVal;
    }

    /*
     * SEND EMAIL TO PARTNER ENGAGMENT LEAD OF FINAL REPORT PUBLICATION
    28.	Mail sent to Partner Engagement Lead and Client Engagement Lead of final report publication
     */
    public int mailToPartnerEngagementLeadOfEngagementPublish(ClientEngagementDTO clientEngagementDTO, List<EngagementPartnerUserDTO> partnersLeadList) throws AppException {
        logger.info("Start: ClientEngagementMailHelper : mailToPartnerEngagementLeadOfEngagementPublish");
        int retVal = 0;
        try {
            if (!partnersLeadList.isEmpty()) {
                for (EngagementPartnerUserDTO partnerDto : partnersLeadList) {
                    EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType(ApplicationConstants.MAIL_TO_ENGAGEMENT_PARTNER_CLIENT_LEAD_PUBLISHED);

                    String subject = notificationDTL.getEmailSubject();
                    subject = subject.replace("$$ENG_CODE$$", clientEngagementDTO.getEngagementCode());

                    String content = notificationDTL.getEmailContent();

                    content = content.replace("$$IMG$$", imageHTMLPath());
                    content = content.replace("$$USER_NAME$$", partnerDto.getPartnerUserName());
                    content = content.replace("$$ENG_CODE$$", clientEngagementDTO.getEngagementCode());
                    content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());

                    EmailNotificationDTO emailNotificationDTO = new EmailNotificationDTO();
                    emailNotificationDTO.setEmailNotificationId(notificationDTL.getEmailNotificationId());
                    emailNotificationDTO.setUserId(1);//user id as admin
                    emailNotificationDTO.setEmailSubject(subject);
                    emailNotificationDTO.setEmailContent(content);
                    emailNotificationDTO.setFromEmailId(adminMailID);
                    emailNotificationDTO.setToEmailId(partnerDto.getPartnerLeadEmailId());//partner lead email
                    emailNotificationDTO.setCcEmailId("");
                    emailNotificationDTO.setBccEmailId("");
                    emailNotificationDTO.setCreatedDate("");
                    emailNotificationDTO.setEmailSuccessFlag(0);
                    emailNotificationDTO.setResendCount(0);
                    emailNotificationDTO.setErrorDescription("");

                    retVal = emailService.insertIntoEmailLog(emailNotificationDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occurred : mailToPartnerEngagementLeadOfEngagementPublish:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mailToPartnerEngagementLeadOfEngagementPublish() :: " + e.getMessage());
        }
        logger.info("End: ClientEngagementMailHelper : mailToPartnerEngagementLeadOfEngagementPublish");
        return retVal;
    }
    
    /*
     * Send mail to analyst/partner when an Service Due Date Passed.
     */
    public int mailWhenServiceDueDatePassed(ClientEngagementDTO clientEngagementDTO) throws AppException {
        logger.info("Start: ClientEngagementMailHelper : mailWhenServiceDueDatePassed");
        int retVal = 0;
        try {
            EmailNotificationDTO notificationDTL = emailService.getEmailNotificationDetailByType(ApplicationConstants.MAIL_TO_ASSIGNED_USER_SERVICE_DUE_DATE_PASSED);

            String subject = notificationDTL.getEmailSubject();
            String content = notificationDTL.getEmailContent();

            content = content.replace("$$IMG$$", imageHTMLPath());
            content = content.replace("$$ANALYST_USERNAME$$", clientEngagementDTO.getCreatedByUser());
            content = content.replace("$$ENG_CODE$$", clientEngagementDTO.getEngagementCode());
            content = content.replace("$$SERVICE_NAME$$", clientEngagementDTO.getSecurityServiceName());
            content = content.replace("$$ENGMNT_LEAD_NAME$$", clientEngagementDTO.getEngagementLeadClientUserName());
            content = content.replace("$$YEAR$$", dateUtil.generateCurrentYear());

            EmailNotificationDTO emailNotificationDTO = new EmailNotificationDTO();
            emailNotificationDTO.setEmailNotificationId(notificationDTL.getEmailNotificationId());
            emailNotificationDTO.setUserId(clientEngagementDTO.getCreatedByUserID());//user id as admin
            emailNotificationDTO.setEmailSubject(subject);
            emailNotificationDTO.setEmailContent(content);
            emailNotificationDTO.setFromEmailId(adminMailID);
            emailNotificationDTO.setToEmailId(clientEngagementDTO.getAnalystEmailID());
            emailNotificationDTO.setCcEmailId("");
            emailNotificationDTO.setBccEmailId("");
            emailNotificationDTO.setCreatedDate("");
            emailNotificationDTO.setEmailSuccessFlag(0);
            emailNotificationDTO.setResendCount(0);
            emailNotificationDTO.setErrorDescription("");

            retVal = emailService.insertIntoEmailLog(emailNotificationDTO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occurred : mailWhenServiceDueDatePassed:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "mailWhenServiceDueDatePassed() :: " + e.getMessage());
        }
        logger.info("End: ClientEngagementMailHelper : mailWhenServiceDueDatePassed");
        return retVal;
    }
}
