/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.transact.dao;

import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;

/**
 *
 * @author hpasupuleti
 */
public interface LoginTransactDAO {

    public UserProfileDTO validateUserLogin(final String userName, final String password)
            throws AppException;

    public int insertUserSecurityDetails(final UserProfileDTO userProfileObj) throws AppException;

    public int updateUserLoginDetails(final UserProfileDTO userProfileObj) throws AppException;
    
    public int killUserActiveSessionDetails(final UserProfileDTO userProfileObj) throws AppException ;
}
