/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.transact.dao;

import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import java.util.List;

/**
 *
 * @author sbhagavatula
 */
public interface ManageEngagementsTransactDAO {

    public int saveEngagement(final ClientEngagementDTO clientEngagementDTO, final UserProfileDTO sessionDTO) throws AppException;

    public int updateEngagement(final ClientEngagementDTO clientEngagementDTO, final UserProfileDTO sessionDTO,
            List<String> serviceToAdd, List<String> serviceToDelete, List<String> servicesToUpdate) throws AppException;

}
