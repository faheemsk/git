/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.validators;

import com.optum.oss.exception.AppException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 *
 * @author sathuluri
 */
@Component
public class LoginValidator {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public String validateLogin(final String username, final String paswd) throws AppException {
        //Pattern  pattern = Pattern.compile(VALID_EMAIL_ADDRESS_REGEX);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(username.trim());
        String validateMessage = "";
        if (StringUtils.isEmpty(username)) {
            validateMessage = "Please enter the registered Email ID.";
        }
        else if(!StringUtils.isEmpty(username)){
            try {
                InternetAddress emailAddr = new InternetAddress(username);
                emailAddr.validate();
            } catch (AddressException ex) {
                ex.getMessage();
                validateMessage = "Please enter a valid Email ID";
            }
        }
        else if (!matcher.matches()) {
            validateMessage = "Please enter a valid Email ID";
        }
        else if (StringUtils.isEmpty(paswd)) {
            validateMessage = "Please enter Password.";
        }

        return validateMessage;
    }

}
