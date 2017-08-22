/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.constants;

import org.springframework.stereotype.Component;

/**
 *
 * @author hpasupuleti
 */
@Component
public class SessionConstants {
    
    public final static String USER_SESSION="USER_SESSION";
    
    //LDAP Constatns
    public final static String ERROR_CODE_19="The provided new password was found in the password history for the user";
    public final static String ERROR_CODE_49="Invalid Credentials";
    public final static String PSWD_UPDATE_SUCCESS ="success";
    
}
