/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao;

import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import java.util.List;

/**
 *
 * @author schanganti
 */
public interface UserActivationDAO {

    public List<UserProfileDTO> userActivationWorklist(long organizationKey) throws AppException;

    public int updateUserStatus(long UserID, int LockIndicator, int statusIndicator) throws AppException;

    public UserProfileDTO checkUserActiveSession(final String userEmail) throws AppException;
}
