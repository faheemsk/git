/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.util;

import com.optum.oss.dto.EmailNotificationDTO;
import com.optum.oss.exception.AppException;
import java.util.Date;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

/**
 *
 * @author srkora
 */
@Component
public class MailSenderUtil {

    private JavaMailSenderImpl mailSender;

    private SimpleMailMessage simpleMailMessage;

    private final String bccEmailID = System.getProperty("OSS_BCC_EMAIL_ID");

    public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
        this.simpleMailMessage = simpleMailMessage;
    }

    public void setMailSender(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }
    //public void sendMail(StringBuffer template)

    private static final Logger log = Logger.getLogger(MailSenderUtil.class);

    public void sendMailSingleRecepient(final String from, final String to,
            final String subject, final String mailContent, final String cc, final String bcc) throws AppException {
        SimpleMailMessage message = new SimpleMailMessage();
        EmailNotificationDTO dto = new EmailNotificationDTO();
        //MailSenderUtil.log.info("In:sendMailSingleRecepient:from:" + from);
        //MailSenderUtil.log.info("In:sendMailSingleRecepient:to:" + to);
        MailSenderUtil.log.info("In:sendMailSingleRecepient:bccEmailID:" + bccEmailID);
        MailSenderUtil.log.info("In:sendMailSingleRecepient:ccEmailID:" + cc);
        try {
            dto.setEmailContent(mailContent);
            dto.setFromEmailId(from);
            dto.setEmailSubject(subject);

            mailSender.send(new MimeMessagePreparator() {

                @Override
                public void prepare(MimeMessage mimeMessage) throws MessagingException {
                    MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                    message.setFrom(from);
                    message.setTo(to);
                    message.setBcc(bccEmailID);
                    message.setSubject(subject);
                    message.setText(mailContent, true);
                    message.setSentDate(new Date());
                    if ((cc != null) && (cc.length() > 0)) {
                        message.setCc(cc);
                    }
                    if ((bcc != null) && (!StringUtils.isEmpty(bcc))) {
                        message.setBcc(bcc);
                    }
                }
            });
            dto.setEmailSuccessFlag(1);
        } catch (Exception e) {
            dto.setEmailSuccessFlag(0);
            throw new AppException("Exception Occured while Executing the "
                    + "sendMailSingleRecepient() :: " + e.getMessage());
        }
    }

    public void sendMailMultipleRecepients(final String from, final String[] to,
            final String subject, final String mailContent, final String[] cc,
            final String bcc) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(to);
        message.setBcc(bccEmailID);
        message.setSubject(subject);
        message.setText(mailContent);
        message.setSentDate(new Date());
        if ((cc != null) && (cc.length > 0)) {
            message.setCc(cc);
        }
        if ((bcc != null) && (!StringUtils.isEmpty(bcc))) {
            message.setBcc(bcc);
        }
        mailSender.send(message);
    }
}
