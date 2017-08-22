/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.validators;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dto.PermissionGroupDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.ValidationHelper;
import com.optum.oss.service.impl.ManagePermissionGroupServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author sathuluri
 */
@Component
public class ManagePermissionGroupValidator implements Validator {

    private static final Logger logger = Logger.getLogger(ManagePermissionGroupValidator.class);

    @Autowired
    private ValidationHelper validationHelper;

    @Autowired
    private ManagePermissionGroupServiceImpl managePermissionGroupService;

    @Override
    public boolean supports(Class clazz) {
        return PermissionGroupDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.info("Start: validate() for validate PermissionGroupDTO while Add / Update permission group");
        PermissionGroupDTO permissionGroupDTO = (PermissionGroupDTO) target;

        //Start: Validation for permission group name
        if (!validationHelper.validateTextEmptyData(StringUtils.trim(permissionGroupDTO.getPermissionGroupName()))) {
            errors.rejectValue("permissionGroupName", "error.permissiongroup.name");
        } else if (!validationHelper.validateDataLength(StringUtils.trim(permissionGroupDTO.getPermissionGroupName()), 100)) {
            errors.rejectValue("permissionGroupName", "error.permissiongroup.name.length");
        } else {
            try {
                if (!StringUtils.isEmpty(permissionGroupDTO.getPermissionGroupName())) {
                    if (!StringUtils.isEmpty(permissionGroupDTO.getEncPermissionGroupName())) {
                        String permissionGroupName = permissionGroupDTO.getEncPermissionGroupName();
                        if (!permissionGroupName.equalsIgnoreCase(permissionGroupDTO.getPermissionGroupName())) {
                            int retVal = managePermissionGroupService.fetchPermissionGroupByName(permissionGroupDTO.getPermissionGroupName());
                            if (retVal > 0) {
                                errors.rejectValue("permissionGroupName", "permissiongroup.name.check");
                            }
                        }
                    } else {
                        int retVal = managePermissionGroupService.fetchPermissionGroupByName(permissionGroupDTO.getPermissionGroupName());
                        if (retVal > 0) {
                            errors.rejectValue("permissionGroupName", "permissiongroup.name.check");
                        }
                    }
                }
            } catch (AppException e) {
                errors.rejectValue("permissionGroupName", "permissiongroup.name.check");
            }
        }
        //End: Validation for permission group name

        //End: Validation for permission group description
        if (!validationHelper.validateTextEmptyData(StringUtils.trim(permissionGroupDTO.getPermissionGroupDesc()))) {
            errors.rejectValue("permissionGroupDesc", "error.permissiongroup.description");
        } else if (!validationHelper.validateDataLength(StringUtils.trim(permissionGroupDTO.getPermissionGroupDesc()), 1000)) {
            errors.rejectValue("permissionGroupDesc", "error.permissiongroup.description.length");
        }
        //End: Validation for permission group description

        //Start: Validation for permission group status
        if (!validationHelper.validateTextEmptyData(StringUtils.trim(permissionGroupDTO.getPermissionGroupStatusValue()))) {
            errors.rejectValue("permissionGroupStatusValue", "error.permissiongroup.status");
        }
        //End: Validation for permission group status

        //Start: Validation for permission group reason for deactivation
        if (!validationHelper.validateTextEmptyData(StringUtils.trim(permissionGroupDTO.getReason()))
                && (permissionGroupDTO.getPermissionGroupStatusValue() != null && !ApplicationConstants.DB_ROW_STATUS_ACTIVE.equalsIgnoreCase(permissionGroupDTO.getPermissionGroupStatusValue()))) {
            errors.rejectValue("reason", "error.permissiongroup.reason");
        } else if (!validationHelper.validateDataLength(StringUtils.trim(permissionGroupDTO.getReason()), 1000)) {
            errors.rejectValue("reason", "error.permissiongroup.reason.length");
        }
        //End: Validation for permission group reason for deactivation

        //Start: Validation for permission group reason for deactivation
        if (permissionGroupDTO.getPermissionGroupKeyChkIds() == null) {
            errors.rejectValue("permissionGroupKeyChkIds", "error.permissiongroup.permissionschk");
        }
        //Start: Validation for permission group reason for deactivation

        logger.info("End: validate() for validate PermissionGroupDTO while Add / Update permission group");
    }
}
