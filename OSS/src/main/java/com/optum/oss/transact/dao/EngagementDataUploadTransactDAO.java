/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.transact.dao;

import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;

/**
 *
 * @author akeshavulu
 */
public interface EngagementDataUploadTransactDAO {
    
    public int saveEngagementDataUploadDetails(ClientEngagementDTO clientEngagementDTO, UserProfileDTO userDto) throws AppException;
    
}
