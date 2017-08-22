/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.transact.dao;

import com.optum.oss.dto.PermissionDTO;
import com.optum.oss.dto.PermissionGroupDTO;
import com.optum.oss.exception.AppException;
import java.util.List;

/**
 *
 * @author sathuluri
 */
public interface ManagePermissionGroupTransactDAO {

    public int saveOrUpdatePermissionGroup(final PermissionGroupDTO permissionGroupDTO, final List<PermissionDTO> oldPermissionsList) throws AppException;

    public int deletePermissionGroup(final PermissionGroupDTO perGroupDTO) throws AppException;
}
