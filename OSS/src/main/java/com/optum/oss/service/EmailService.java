/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service;

import com.optum.oss.dto.EmailNotificationDTO;
import com.optum.oss.dto.ManageServiceUserDTO;
import com.optum.oss.exception.AppException;
import java.util.List;

/**
 *
 * @author hpasupuleti
 */
public interface EmailService {

    public EmailNotificationDTO getEmailNotificationDetailByType(final String notificationType)
            throws AppException;

    public int insertIntoEmailLog(final EmailNotificationDTO notificationDTO) throws AppException;

    public List<EmailNotificationDTO> fetchListOfEmailsToBeSent() throws AppException;

    public int updateEmailLog(final long emailSuccessFlag, final long resendCount,
            final long notificationID) throws AppException;
    
    public List<ManageServiceUserDTO> fetchListOfEngagementServiceDueDateHasPassedEmails() throws AppException;

    public List<ManageServiceUserDTO> fetchListOfEngagementDueDateApproachingEmails() throws AppException;

    public List<ManageServiceUserDTO> fetchListOfEngagementDueDatePassedEmails() throws AppException;

}
