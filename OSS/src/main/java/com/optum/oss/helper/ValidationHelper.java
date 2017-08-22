/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 *
 * @author hpasupuleti
 */
@Component
public class ValidationHelper {
    
    
    
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    private static final String ALPHA_NUMERIC_PATTERN="^[a-zA-Z0-9]*$";
    
    private static final String ONLY_ALPHA_PATTERN="^[A-Za-z\\s]+$";
    
    private static final String PHONE_NUMBER_PATTERN="^[0-9()-]+$";
    
    private static final String SPECIAL_CHARS_PATTERN = "<>!#$%^&*()+[]{}?:;|'\\\"\\\\,/~`=";
    
    private static final String NUMERIC_PATTERN="^[0-9]*$";
    
    private static final String IPADDRESS_PATTERN = 
		"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    
    /*
    method : validateTextEmptyData
    param  : paramValue
    return : boolean
    Description : Checks whether the provided String is Empty or Not. If its empty method will return 'false'
    */
    public boolean validateTextEmptyData(final String paramValue)
    {
        boolean bool = true;
        if(StringUtils.isEmpty(paramValue))
        {
            bool = false;
        }
        return bool;
    }
    
    /*
    method : validateSelectEmptyData
    param  : paramValue
    return : boolean
    Description : Checks whether the provided Select Box is Empty or Not. If its value is '0' method will return 'false'
    Pre-condition : By default -Select- option in Select box need to be provided with '0' as default value.
    */
    public boolean validateSelectEmptyData(final String paramValue)
    {
        boolean bool = true;
        if(!(StringUtils.isEmpty(paramValue)) && (paramValue.equalsIgnoreCase("0")))
        {
            bool = false;
        }
        return bool;
    }
    
    /*
    method : validateEmail
    param  : paramEmail
    return : boolean
    Description : Checks whether the provided Email is valid or Not. If its empty method will return 'false'
    */
    public boolean validateEmail(final String paramEmail)
    {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(paramEmail);
        return matcher.matches();
    }
    
    /*
    method : validateSpecialCharacters
    param  : paramValue
    return : boolean
    Description : Checks whether the provided String is having any restricted Special characters. 
                  If 'YES' method will return 'false'
    */
    public boolean validateSpecialCharacters(final String paramValue)
    {
        boolean bool = true;
        for(char c : SPECIAL_CHARS_PATTERN.toCharArray())
        {
            if(paramValue.contains(c+""))
            {
                bool = false;
            }
        }
        return bool;
    }
    
    /*
    method : validateDataLength
    param  : paramValue, paramLength
    return : boolean
    Description : Checks whether the provided String length is matching with the provided length. 
                  If not method will return 'false'
    */
    public boolean validateDataLength(final String paramValue, final int paramLength)
    {
        boolean bool = true;
        if(paramValue.length() > paramLength)
        {
            bool = false;
        }
        return bool;
    }
    
    /*
    method : validateDataLength
    param  : paramValue, paramLength,paramMaxLength
    return : boolean
    Description : Checks whether the provided String length is between the provided min & max length. 
                  If not method will return 'false'
    */
    public boolean validateDataLength(final String paramValue, final int paramMinLength,final int paramMaxLength)
    {
        boolean bool = false;
        if((paramValue.length() >= paramMinLength) && (paramValue.length() <= paramMaxLength))
        {
            bool = true;
        }
        return bool;
    }
    
    /*
    method : validatePhone
    param  : paramPhone
    return : boolean
    Description : Checks whether the provided Phone Number is valid or Not. If its not valid method will return 'false'
    */
    public boolean validatePhone(final String paramPhone)
    {
        Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(paramPhone);
        return matcher.matches();
    }
    
    /*
    method : validateOnlyAlphabets
    param  : paramValue
    return : boolean
    Description : Checks whether the provided value contains only alphabets or Not. 
                  If its not method will return 'false'
    */
    public boolean validateOnlyAlphabets(final String paramValue)
    {
        Pattern pattern = Pattern.compile(ONLY_ALPHA_PATTERN);
        Matcher matcher = pattern.matcher(paramValue);
        return matcher.matches();
    }
    
    /*
    method : validateAlphaNumeric
    param  : paramValue
    return : boolean
    Description : Checks whether the provided value contains only alpha numeric values or Not. 
                  If its not method will return 'false'
    */
    public boolean validateAlphaNumeric(final String paramValue)
    {
        Pattern pattern = Pattern.compile(ALPHA_NUMERIC_PATTERN);
        Matcher matcher = pattern.matcher(paramValue);
        return matcher.matches();
    }
    
    /*
    method : validateNumeric
    param  : paramValue
    return : boolean
    Description : Checks whether the provided value contains only numeric values or Not. 
                  If its not method will return 'false'
    */
    public boolean validateNumeric(final String paramValue)
    {
        Pattern pattern = Pattern.compile(NUMERIC_PATTERN);
        Matcher matcher = pattern.matcher(paramValue);
        return matcher.matches();
    }
    
    /*
    method : validateIPAddress
    param  : paramValue
    return : boolean
    Description : Checks whether the provided value is valid ip or Not. 
                  If its not method will return 'false'
    */
    public boolean validateIPAddress(final String paramValue)
    {
        Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
        Matcher matcher = pattern.matcher(paramValue);
        return matcher.matches();
    }
    
}
