/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.exception;

/**
 *
 * @author hpasupuleti
 */
public class AppException extends Exception{
    
    public AppException()
    {
        super();
    }
    
    public AppException(String messString)
    {
        super(messString);
    }
    
    public AppException(String messString,Throwable throwClass)
    {
        super(messString,throwClass);
    }
    
}
