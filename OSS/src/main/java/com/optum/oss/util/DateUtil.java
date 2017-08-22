/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.util;

import com.optum.oss.exception.AppException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 *
 * @author hpasupuleti
 */
@Component
public class DateUtil {

    private static final Logger logger  = Logger.getLogger(DateUtil.class);

    
    public String generateDBEmptyDate() {
        return "0000-00-00 00:00:00";
    }
    
    public Date generateDBCurrentDate() {
        String str = "";
        Date retDate = null;
        DateUtil.logger.info("Start : generateCurrentDate() Method");
        try {
            Date d = new Date();

            DateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            str = sd.format(d);

            retDate = sd.parse(str);

        } catch (ParseException e) {
            DateUtil.logger.error("Exception Occured while Executing the "
                    + "generateCurrentDate() :: " + e.getMessage());
            DateUtil.logger.debug("Exception Occured while Executing the "
                    + "generateCurrentDate() :: " + e.getMessage());
        }
        DateUtil.logger.info("End : generateCurrentDate() Method");
        return retDate;
    }
    
    public String generateDBCurrentDateTime() {
        String str = "";
        DateUtil.logger.info("Start : generateDBCurrentDateTime() Method");
        try {
            Date d = new Date();

            DateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            str = sd.format(d);

        } catch (Exception e) {
            DateUtil.logger.error("Exception Occured while Executing the "
                    + "generateDBCurrentDateTime() :: " + e.getMessage());
            DateUtil.logger.debug("Exception Occured while Executing the "
                    + "generateDBCurrentDateTime() :: " + e.getMessage());
        }
        DateUtil.logger.info("End : generateCurrentDate() Method");
        return str;
    }
    
    public String generateDBCurrentDateInString() {
        String str = "";
        DateUtil.logger.info("Start : generateCurrentDateString() Method");
        try {
            Date d = new Date();

            DateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            str = sd.format(d);

        } catch (Exception e) {
            DateUtil.logger.error("Exception Occured while Executing the "
                    + "generateCurrentDateString() :: " + e.getMessage());
            DateUtil.logger.debug("Exception Occured while Executing the "
                    + "generateCurrentDateString() :: " + e.getMessage());
        }
        DateUtil.logger.info("End : generateCurrentDate() Method");
        return str;
    }
    
    public String generateCurrentYear() {
        String str = "";
        DateUtil.logger.info("Start : generateCurrentYear() Method");
        try {
            Date d = new Date();

            DateFormat sd = new SimpleDateFormat("yyyy");

            str = sd.format(d);

        } catch (Exception e) {
            DateUtil.logger.error("Exception Occured while Executing the "
                    + "generateCurrentDate() :: " + e.getMessage());
            DateUtil.logger.debug("Exception Occured while Executing the "
                    + "generateCurrentDate() :: " + e.getMessage());
        }
        DateUtil.logger.info("End : generateCurrentDate() Method");
        return str;
    }

    public  String convertTimestampToString(final Timestamp paramTimestamp) {
        return paramTimestamp.toString();
    }

    public String dateConvertionFromJSPToDB(String strDate) {
        String convertedDate = "";
        try {
            Date date = new SimpleDateFormat("MM/dd/yyyy").parse(strDate);
            convertedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        } catch (ParseException e) {
            e.getMessage();
        }
        return convertedDate;
    }
    
    public String dateConvertionFromDBToJSP(String strDate) {
        String convertedDate = "";
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDate);
            convertedDate = new SimpleDateFormat("MM/dd/yyyy").format(date);
        } catch (ParseException e) {
            e.getMessage();
        }
        return convertedDate;
    }
    
    public String getPastMonthsFromCurrentDate(final int noOfMonths) throws AppException
    {
        Calendar cal = Calendar.getInstance();
        cal.add(cal.MONTH, -noOfMonths);
        cal.add(cal.DAY_OF_MONTH, 1);
        long calTime = cal.getTimeInMillis();
        
        Date d = new Date(calTime);
        DateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sd.format(d);

        return str;
    }
    public String getPastMonthDateFromCurrentDate(final int noOfMonths) throws AppException
    {
        Calendar cal = Calendar.getInstance();
        cal.add(cal.MONTH, -noOfMonths);
        cal.add(cal.DAY_OF_MONTH, 1);
        long calTime = cal.getTimeInMillis();
        
        Date d = new Date(calTime);
        DateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sd.format(d);

        return str;
    }
    public String getPastDaysFromCurrentDate(final int noOfDays) throws AppException
    {
        Calendar cal = Calendar.getInstance();
        cal.add(cal.DAY_OF_WEEK, -noOfDays);        
        long calTime = cal.getTimeInMillis();        
        Date d = new Date(calTime);
        DateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sd.format(d);
        return str;
    }  
    
    public String getFutureDaysFromCurrentDate(final int noOfDays) throws AppException
    {
        Calendar cal = Calendar.getInstance();
        cal.add(cal.DAY_OF_WEEK, noOfDays);        
        long calTime = cal.getTimeInMillis();        
        Date d = new Date(calTime);
        DateFormat sd = new SimpleDateFormat("MM/dd/yyyy");
        String str = sd.format(d);
        return str;
    }  
    
 
    public String generateMailCurrentDate() {
        String str = "";
        Date retDate = null;
        DateUtil.logger.info("Start : generateMailCurrentDate() Method");
        try {
            Date d = new Date();
            DateFormat sd = new SimpleDateFormat("MM/dd/yyyy");
            str = sd.format(d);
        } catch (Exception e) {
            DateUtil.logger.error("Exception Occured while Executing the "
                    + "generateMailCurrentDate() :: " + e.getMessage());
            DateUtil.logger.debug("Exception Occured while Executing the "
                    + "generateMailCurrentDate() :: " + e.getMessage());
        }
        DateUtil.logger.info("End : generateMailCurrentDate() Method");
        return str;
    }
   
    public String generateMailCurrentTime()
    {
        String str = "";
        try
        {
            Calendar cal = Calendar.getInstance();
            DateFormat sd = new SimpleDateFormat("HH:mm:ss");
            str = sd.format(cal.getTime());
        }
        catch (Exception e) {
            DateUtil.logger.error("Exception Occured while Executing the "
                    + "generateMailCurrentTime() :: " + e.getMessage());
            DateUtil.logger.debug("Exception Occured while Executing the "
                    + "generateMailCurrentTime() :: " + e.getMessage());
        }
        DateUtil.logger.info("End : generateMailCurrentTime() Method");
        return str;
    }
    
    
    public long calculateDateDiffWithCurrentDateTime(final String paramDBDate) throws AppException {
        long diffDays = 0;
        try {
            Date curDateTime = this.generateDBCurrentDate();
            String convParamDBDate = this.dateConvertionFromJSPToDB(paramDBDate);
            DateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date dbDate = sd.parse(convParamDBDate);

            long diff = curDateTime.getTime() - dbDate.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            diffDays = diff / (24 * 60 * 60 * 1000);
            
           
        } catch (ParseException e) {
            throw new AppException("Exception Occured while Executing the "
                    + "calculateDateDiffWithCurrentDateTime() :: " + e.getMessage());
        }
        return diffDays;
    }
    
    
    public long calculateDateDiffInDays(final String paramDBDate) throws AppException {
        long diffDays = 0;
        try {
            Date curDateTime = this.generateDBCurrentDate();
            String convParamDBDate = this.dateConvertionFromJSPToDB(paramDBDate);
            DateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dbDate = sd.parse(convParamDBDate);
            long diff = curDateTime.getTime() - dbDate.getTime();
            diffDays = diff / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            throw new AppException("Exception Occured while Executing the "
                    + "calculateDateDiffInHours() :: " + e.getMessage());
        }
        return diffDays;
    }
    
    //Start: This method for coverting JSP string date to Date
    public Date convertionFromStringToDate(final String date) throws AppException {
        Date convertedDate = null;
        try {
            DateFormat sd = new SimpleDateFormat("MM/dd/yyyy");
            convertedDate = sd.parse(date);
        } catch (ParseException e) {
            throw new AppException("Exception Occured while Executing the "
                    + "convertionFromStringToDate() :: " + e.getMessage());
        }
        return convertedDate;
    }
    //End: This method for coverting JSP string date to Date
    
        
}
