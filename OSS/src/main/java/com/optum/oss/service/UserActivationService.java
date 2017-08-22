/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service;

import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import java.util.List;

/**
 *
 * @author schanganti
 */
public interface UserActivationService {

    public List<UserProfileDTO> userActivationWorklist(long organizationKey) throws AppException;

    public int updateUserStatus(final String decUserID,final String decRowStatusKey,final long sessionUserID) throws AppException;

    public UserProfileDTO checkUserActiveSession(final String userEmail) throws AppException;
}
