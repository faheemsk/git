/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao.impl;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dao.EmailDAO;
import com.optum.oss.dto.EmailNotificationDTO;
import com.optum.oss.dto.ManageServiceUserDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.util.DateUtil;
import com.sun.rowset.CachedRowSetImpl;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.sql.rowset.CachedRowSet;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author hpasupuleti
 */
public class EmailDAOImpl implements EmailDAO {

    private static final Logger logger = Logger.getLogger(EmailDAOImpl.class);

    private JdbcTemplate jdbcTemplate;

    /*
     Start : Setter getters for private variables
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /*
     End : Setter getters for private variables
     */
    /*
     Start : Autowiring the Fields
     */
    @Autowired
    private DateUtil dateUtil;
    /*
     End : Autowiring the Fields
     */

    /**
     *
     * @param notificationType
     * @return EmailNotificationDTO
     * @throws AppException
     */
    @Override
    public EmailNotificationDTO getEmailNotificationDetailByType(final String notificationType)
            throws AppException {
        EmailNotificationDTO notificationDTO = new EmailNotificationDTO();
        try {
            String procName = "{CALL GetEmailNotificationByType(?)}";

            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName,
                    new Object[]{notificationType});
            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    notificationDTO.setEmailNotificationId((Integer) (resultMap.get("NTF_MSG_KEY")));
                    notificationDTO.setEmailSubject((String) (resultMap.get("MSG_SBJ_TXT")));
                    notificationDTO.setEmailContent((String) (resultMap.get("MSG_CNTN_TXT")));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : getEmailNotificationDetailByType:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "getEmailNotificationDetailByType() :: " + e.getMessage());
        }
        return notificationDTO;
    }

    /**
     *
     * @param notificationDTO
     * @return int
     * @throws AppException
     */
    @Override
    public int insertIntoEmailLog(final EmailNotificationDTO notificationDTO) throws AppException {
        int retVal = 0;
        try {
            String procName = "{CALL INS_USER_EMAIL_LOG(?,?,?,?,?,?,?,?,?,?,?,?)}";
            retVal = jdbcTemplate.queryForObject(procName,
                    new Object[]{notificationDTO.getEmailNotificationId(),
                        notificationDTO.getUserId(),
                        notificationDTO.getFromEmailId(),
                        notificationDTO.getToEmailId(),
                        notificationDTO.getCcEmailId(),
                        notificationDTO.getBccEmailId(),
                        null,
                        notificationDTO.getEmailSubject(),
                        notificationDTO.getEmailContent(),
                        notificationDTO.getEmailSuccessFlag(),
                        notificationDTO.getResendCount(),
                        notificationDTO.getErrorDescription()},
                    Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : insertIntoEmailLog:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "insertIntoEmailLog() :: " + e.getMessage());
        }
        return retVal;
    }

    /**
     *
     * @return List<EmailNotificationDTO>
     * @throws AppException
     */
    @Override
    public List<EmailNotificationDTO> fetchListOfEmailsToBeSent() throws AppException {
        List<EmailNotificationDTO> notificationList = new ArrayList<>();
        try {
            String procName = "{CALL LIST_UserNotSentEmails()}";

            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName);

            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    EmailNotificationDTO notificationDTO = new EmailNotificationDTO();

                    notificationDTO.setEmailNotificationId((Integer) (resultMap.get("NTF_MSG_KEY")));
                    notificationDTO.setEmailLogId(Long.parseLong(resultMap.get("USER_EMAIL_LOG")+""));
                    notificationDTO.setUserId((Integer) (resultMap.get("USER_ID")));
                    notificationDTO.setFromEmailId((String) (resultMap.get("FROM_EMAIL_ID")));
                    notificationDTO.setToEmailId((String) (resultMap.get("TO_EMAIL_ID")));
                    notificationDTO.setResendCount((Integer) (resultMap.get("RESND_CNT")));
                    notificationDTO.setEmailSubject((String) (resultMap.get("EMAIL_MSG_SBJ_TXT")));
                    notificationDTO.setEmailContent((String) (resultMap.get("EMAIL_MSG_CNTN_TXT")));

                    notificationList.add(notificationDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : fetchListOfEmailsToBeSent:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "fetchListOfEmailsToBeSent() :: " + e.getMessage());
        }

        return notificationList;
    }

    /**
     *
     * @param emailSuccessFlag
     * @param resendCount
     * @param notificationID
     * @return int
     * @throws AppException
     */
    @Override
    public int updateEmailLog(final long emailSuccessFlag, final long resendCount,
            final long notificationID) throws AppException {
        int retVal = 0;
        try {
            String procName = "{CALL UPDATE_UserEmailLogByKey(?,?,?,?)}";

            //retVal = jdbcTemplate.update(procName,
            String curDate = null;
            if (emailSuccessFlag == 1) {
                curDate = dateUtil.generateDBCurrentDateInString();
            }
            jdbcTemplate.update(procName,
                    new Object[]{emailSuccessFlag,
                        resendCount, notificationID,
                        curDate});

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : updateEmailLog:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "updateEmailLog() :: " + e.getMessage());
        }
        return retVal;
    }

    /**
     * This method for fetch list of email's for engagement service due date has
     * passed
     *
     * @return List<ManageServiceUserDTO>
     * @throws AppException
     */
    @Override
    public List<ManageServiceUserDTO> fetchListOfEngagementServiceDueDateHasPassedEmails() throws AppException {
        logger.info("Start: EmailDAOImpl: fetchListOfEngagementServiceDueDateHasPassedEmails()");
        List<ManageServiceUserDTO> serviceList = new ArrayList<>();
        try {
            String serviceDueDatePassed = "{CALL GetEngagementServiceduedatehaspassedEmials()}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(serviceDueDatePassed);

            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    if ((int) resultMap.get("Analyst UserType") == (int) resultMap.get("Lead UserType")) {
                        ManageServiceUserDTO manageServiceDto = new ManageServiceUserDTO();

                        manageServiceDto.setClientEngagementCode(resultMap.get("CLNT_ENGMT_CD") + "");
                        manageServiceDto.setSecureServiceCode(resultMap.get("SECUR_SRVC_CD") + "");
                        manageServiceDto.setServiceName(resultMap.get("SECUR_SRVC_NM") + "");
                        manageServiceDto.setUserTypeKey((Integer) resultMap.get("Analyst UserType"));
                        manageServiceDto.setEngagementLeadName(resultMap.get("Lead Name") + "");
                        manageServiceDto.setAnalystName(resultMap.get("Analyst") + "");
                        manageServiceDto.setServiceEstimatedStartDate(resultMap.get("SRVC_EST_STRT_DT") + "");
                        manageServiceDto.setServiceEstimatedEndDate(resultMap.get("SRVC_EST_END_DT") + "");
                        manageServiceDto.setAnalystEmail(resultMap.get("Analyst Email") + "");
                        manageServiceDto.setOrganizationName(resultMap.get("ORG_NM") + "");

                        serviceList.add(manageServiceDto);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : fetchListOfEngagementServiceDueDateHasPassedEmails:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "fetchListOfEngagementServiceDueDateHasPassedEmails() :: " + e.getMessage());
        }
        logger.info("End: EmailDAOImpl: fetchListOfEngagementServiceDueDateHasPassedEmails()");
        return serviceList;
    }

    /**
     * This method for fetch list of email's for engagement due date approaching
     * before 15 days
     *
     * @return List<EmailNotificationDTO>
     * @throws AppException
     */
    @Override
    public List<ManageServiceUserDTO> fetchListOfEngagementDueDateApproachingEmails() throws AppException {
        logger.info("Start: EmailDAOImpl: fetchListOfEngagementDueDateApproachingEmails()");
        List<ManageServiceUserDTO> serviceList = new ArrayList<>();
        try {
            String procName = "{CALL GetEngagementduedateisapproachingEmail()}";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName);

            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    if (!ApplicationConstants.DB_USER_TYPE_CLIENT.equalsIgnoreCase(resultMap.get("User Type") + "")) {
                        ManageServiceUserDTO manageServiceDto = new ManageServiceUserDTO();

                        manageServiceDto.setClientEngagementCode(resultMap.get("CLNT_ENGMT_CD") + "");
                        manageServiceDto.setClientEngagementName(resultMap.get("CLNT_ENGMT_NM") + "");
                        manageServiceDto.setEngagementEstimatedEndDate(resultMap.get("ENGMT_EST_END_DT") + "");
                        manageServiceDto.setEngagementLeadName(resultMap.get("Engagement Lead") + "");
                        manageServiceDto.setEngagementLeadEmail(resultMap.get("Engagement Lead Email") + "");
                        manageServiceDto.setAnalystName(resultMap.get("Analyst") + "");
                        manageServiceDto.setAnalystEmail(resultMap.get("AnalystEmail") + "");

                        serviceList.add(manageServiceDto);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : fetchListOfEngagementDueDateApproachingEmails:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "fetchListOfEngagementDueDateApproachingEmails() :: " + e.getMessage());
        }
        logger.info("End: EmailDAOImpl: fetchListOfEngagementDueDateApproachingEmails()");
        return serviceList;
    }

    /**
     * This method for fetch list of email's for engagement due date has passed
     *
     * @return List<EmailNotificationDTO>
     * @throws AppException
     */
    @Override
    public List<ManageServiceUserDTO> fetchListOfEngagementDueDatePassedEmails() throws AppException {
        logger.info("Start: EmailDAOImpl: fetchListOfEngagementDueDatePassedEmails()");
        List<ManageServiceUserDTO> serviceList = new ArrayList<>();
        try {
            String procName = "{CALL GetEngagementduedatehaspassed()}";
            List<CachedRowSet> rsSet = getCachedRowsetList(procName);

            int count = 1;
            Iterator rsItr = rsSet.iterator();
            while (rsItr.hasNext()) {
                CachedRowSet rowset = (CachedRowSet) rsItr.next();
                if (!rowset.wasNull()) {
                    ResultSet rs = rowset.getOriginal();
                    //Start: For Internal / Partner Engagement Lead email's
                    if (count == 1) {
                        while (rs.next()) {
                            if (!ApplicationConstants.DB_USER_TYPE_CLIENT.equalsIgnoreCase(rs.getString("User Type"))) {
                                ManageServiceUserDTO manageServiceDto = new ManageServiceUserDTO();

                                manageServiceDto.setClientEngagementCode(rs.getString("CLNT_ENGMT_CD"));
                                manageServiceDto.setUserTypeKey(rs.getLong("USER_TYP_KEY"));
                                manageServiceDto.setUserName(rs.getString("Engagement Lead"));
                                manageServiceDto.setAnalystEmail(rs.getString("Engagement Lead email"));

                                serviceList.add(manageServiceDto);
                            }
                        }
                    }
                    //End: For Engagement Lead email's

                    //Start: For Internal / Partner Analyst email's
                    if (count == 2) {
                        while (rs.next()) {
                            ManageServiceUserDTO manageServiceDto = new ManageServiceUserDTO();

                            manageServiceDto.setClientEngagementCode(rs.getString("CLNT_ENGMT_CD"));
                            manageServiceDto.setUserTypeKey(rs.getLong("USER_TYP_KEY"));
                            manageServiceDto.setUserName(rs.getString("Analyst Name"));
                            manageServiceDto.setAnalystEmail(rs.getString("Analyst Email"));

                            serviceList.add(manageServiceDto);
                        }
                    }
                    //End: For Internal / Partner Analyst email's
                    count++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : fetchListOfEngagementDueDatePassedEmails:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "fetchListOfEngagementDueDatePassedEmails() :: " + e.getMessage());
        }
        logger.info("End: EmailDAOImpl: fetchListOfEngagementDueDatePassedEmails()");
        return serviceList;
    }

    /**
     * This method for fetch multi result sets
     *
     * @param procName
     * @return List<CachedRowSet>
     * @throws AppException
     */
    private List<CachedRowSet> getCachedRowsetList(final String procName) throws AppException {
        logger.info("Start: EmailDAOImpl: getCachedRowsetList()");
        try {
            return jdbcTemplate.execute(new CallableStatementCreator() {
                @Override
                public CallableStatement createCallableStatement(java.sql.Connection con) throws SQLException {
                    return con.prepareCall(procName);
                }
            }, new CallableStatementCallback<List<CachedRowSet>>() {
                @Override
                public List<CachedRowSet> doInCallableStatement(CallableStatement cs) throws SQLException {
                    List<CachedRowSet> results = new ArrayList<CachedRowSet>();

                    boolean resultsAvailable = cs.execute();
                    while (resultsAvailable) {
                        ResultSet rs = cs.getResultSet();
                        CachedRowSet rowSet = new CachedRowSetImpl();
                        rowSet.populate(rs);

                        results.add(rowSet);
                        resultsAvailable = cs.getMoreResults();
                    }
                    logger.info("End: EmailDAOImpl: getCachedRowsetList()");
                    return results;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred : getCachedRowsetList:" + e.getMessage());
            throw new AppException("Exception Occured while Executing the "
                    + "getCachedRowsetList() :: " + e.getMessage());
        }
    }
}
