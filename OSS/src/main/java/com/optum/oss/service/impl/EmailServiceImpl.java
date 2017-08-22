/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.service.impl;

import com.optum.oss.dao.impl.EmailDAOImpl;
import com.optum.oss.dto.EmailNotificationDTO;
import com.optum.oss.dto.ManageServiceUserDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.service.EmailService;
import java.util.List;
import java.util.Properties;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author hpasupuleti
 */
@Service
public class EmailServiceImpl implements EmailService{
    
    private static final Logger logger = Logger.getLogger(EmailServiceImpl.class);

    /*
     Start : Autowiring of Classes
     */
   
    @Autowired
    private EmailDAOImpl emailDAO;
    
    /*
     End : Autowiring of Classes
     */
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public EmailNotificationDTO getEmailNotificationDetailByType(final String notificationType) 
            throws AppException
    {
        return emailDAO.getEmailNotificationDetailByType(notificationType);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int insertIntoEmailLog(final EmailNotificationDTO notificationDTO) throws AppException
    {
        return emailDAO.insertIntoEmailLog(notificationDTO);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.SERIALIZABLE)
    public List<EmailNotificationDTO> fetchListOfEmailsToBeSent() throws AppException
    {
        return emailDAO.fetchListOfEmailsToBeSent();
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int updateEmailLog(final long emailSuccessFlag, final long resendCount,
            final long notificationID) throws AppException
    {
        return emailDAO.updateEmailLog(emailSuccessFlag, resendCount, notificationID);
    }
    
    /**
     * This method for fetch list of email's for engagement service due date has
     * passed
     *
     * @return List<ManageServiceUserDTO>
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.SERIALIZABLE)
    public List<ManageServiceUserDTO> fetchListOfEngagementServiceDueDateHasPassedEmails() throws AppException {
        return emailDAO.fetchListOfEngagementServiceDueDateHasPassedEmails();
    }

    /**
     * This method for fetch list of email's for engagement due date approaching
     * before 15 days
     *
     * @return List<EmailNotificationDTO>
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.SERIALIZABLE)
    public List<ManageServiceUserDTO> fetchListOfEngagementDueDateApproachingEmails() throws AppException {
        return emailDAO.fetchListOfEngagementDueDateApproachingEmails();
    }

    /**
     * This method for fetch list of email's for engagement due date has passed
     *
     * @return List<EmailNotificationDTO>
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.SERIALIZABLE)
    public List<ManageServiceUserDTO> fetchListOfEngagementDueDatePassedEmails() throws AppException {
        return emailDAO.fetchListOfEngagementDueDatePassedEmails();
    }
}
