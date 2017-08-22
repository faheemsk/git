/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dto;

import java.io.Serializable;
import org.springframework.stereotype.Component;

/**
 *
 * @author hpasupuleti
 */
@Component
public class ExceptionDTO implements Serializable{
    
    
    private String exceptionCode;
    private String exceptionMessage;
    private Exception exceptionObj;
    private String exceptionType;

    public ExceptionDTO()
    {
         
    }
    
    public ExceptionDTO(final String paramExceptionCode,final String paramExceptionMessage)
    {
        this.exceptionCode=paramExceptionCode;
        this.exceptionMessage=paramExceptionMessage;
    }
    public ExceptionDTO(final String paramExceptionCode,
                        final String paramExceptionMessage,
                        final String paramExceptionType)
    {
        this.exceptionCode=paramExceptionCode;
        this.exceptionMessage=paramExceptionMessage;
        this.exceptionType=paramExceptionType;
    }
    public ExceptionDTO(final String paramExceptionCode,final String paramExceptionMessage,final Exception paramException)
    {
        this.exceptionCode=paramExceptionCode;
        this.exceptionMessage=paramExceptionMessage;
        this.exceptionObj = paramException;
    }
    
     
    
    /**
     * @return the exceptionCode
     */
    public String getExceptionCode() {
        return exceptionCode;
    }

    /**
     * @param exceptionCode the exceptionCode to set
     */
    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    /**
     * @return the exceptionMessage
     */
    public String getExceptionMessage() {
        return exceptionMessage;
    }

    /**
     * @param exceptionMessage the exceptionMessage to set
     */
    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    /**
     * @return the exceptionObj
     */
    public Exception getExceptionObj() {
        return exceptionObj;
    }

    /**
     * @param exceptionObj the exceptionObj to set
     */
    public void setExceptionObj(Exception exceptionObj) {
        this.exceptionObj = exceptionObj;
    }

    /**
     * @return the exceptionType
     */
    public String getExceptionType() {
        return exceptionType;
    }

    /**
     * @param exceptionType the exceptionType to set
     */
    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    
    
}
