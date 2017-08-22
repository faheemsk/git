/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.validators;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.ValidationHelper;
import com.optum.oss.service.impl.LoginServiceImpl;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author rpanuganti
 */
@Component
public class UserManagementValidator implements Validator {

    private static final Logger logger = Logger.getLogger(UserManagementValidator.class);

    @Autowired
    private ValidationHelper validationHelper;

    @Autowired
    private LoginServiceImpl loginService;

    @Override
    public boolean supports(Class clazz) {
        return UserProfileDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserProfileDTO usetDTO = (UserProfileDTO) target;

        if (!validationHelper.validateSelectEmptyData(usetDTO.getOrganizationKey() + "")) {
            errors.rejectValue("organizationKey", "error.user.organization");
        }

        if (!validationHelper.validateSelectEmptyData(usetDTO.getUserTypeKey() + "")) {
            errors.rejectValue("userTypeKey", "error.user.type");
        }

        if (!validationHelper.validateTextEmptyData(usetDTO.getFirstName().trim())) {
            errors.rejectValue("firstName", "error.user.firstName");
        } else if (!validationHelper.validateDataLength(usetDTO.getFirstName(), 100)) {
            errors.rejectValue("firstName", "error.user.firstName.length");
        } else if (!validationHelper.validateOnlyAlphabets(usetDTO.getFirstName())) {
            errors.rejectValue("firstName", "error.user.firstName.format");
        }

        if (!usetDTO.getMiddleName().equalsIgnoreCase("") && usetDTO.getMiddleName() != null) {
            if (!validationHelper.validateDataLength(usetDTO.getMiddleName().trim(), 100)) {
                errors.rejectValue("middleName", "error.user.middleName.length");
            } else if (!validationHelper.validateOnlyAlphabets(usetDTO.getMiddleName())) {
                errors.rejectValue("middleName", "error.user.middleName.format");
            }
        }

        if (!validationHelper.validateTextEmptyData(usetDTO.getLastName().trim())) {
            errors.rejectValue("lastName", "error.user.lastName");
        } else if (!validationHelper.validateDataLength(usetDTO.getLastName(), 100)) {
            errors.rejectValue("lastName", "error.user.lastName.length");
        } else if (!validationHelper.validateOnlyAlphabets(usetDTO.getLastName())) {
            errors.rejectValue("lastName", "error.user.lastName.format");
        }

        if (!validationHelper.validateDataLength(usetDTO.getJobTitleName().trim(), 100)) {
            errors.rejectValue("jobTitleName", "error.user.jobTitle");
        }

        if (!validationHelper.validateTextEmptyData(usetDTO.getEmail().trim())) {
            errors.rejectValue("email", "error.user.emailID");
        } else if (!validationHelper.validateEmail(usetDTO.getEmail())) {
            errors.rejectValue("email", "error.user.emailID.invalid");
        } else if (!validationHelper.validateDataLength(usetDTO.getEmail(), 200)) {
            errors.rejectValue("email", "error.user.emailID.length");
        } else if (usetDTO.getUserProfileKey() <= 0) {
            UserProfileDTO userDto = null;
            try {
                userDto = loginService.fetchUserDetailsByUserName(usetDTO.getEmail());
                if (userDto.getUserProfileKey() != 0) {
                    errors.rejectValue("email", "useradd.email-exists");
                }
            } catch (AppException ex) {
            }

        }

        if (!usetDTO.getTelephoneNumber().equalsIgnoreCase("") && usetDTO.getTelephoneNumber() != null) {
            if (!validationHelper.validatePhone(usetDTO.getTelephoneNumber())) {
                errors.rejectValue("telephoneNumber", "error.user.telephoneNumber.format");
            } else if (!validationHelper.validateDataLength(usetDTO.getTelephoneNumber(), 20)) {
                errors.rejectValue("telephoneNumber", "error.user.telephoneNumber.length");
            } else if (!validationHelper.validateDataLength(usetDTO.getTelephoneNumber(), 10, 20)) {
                errors.rejectValue("telephoneNumber", "error.user.telephoneNumber");
            }
        }
        
        if (!validationHelper.validateTextEmptyData(usetDTO.getDepartment().trim())) {
            errors.rejectValue("department", "error.user.department");
        } else if (!validationHelper.validateDataLength(usetDTO.getDepartment(), 100)) {
            errors.rejectValue("department", "error.user.department.length");
        } else if (!validationHelper.validateOnlyAlphabets(usetDTO.getDepartment())) {
            errors.rejectValue("department", "error.user.department.format");
        }
        
        if (usetDTO.getSelectedRole() != null) {
            if (usetDTO.getSelectedRole().length <= 0) {
                errors.rejectValue("selectedRole[0]", "error.user.role");
            }
        } else {
            errors.rejectValue("selectedRole[0]", "error.user.role");
        }

        if (usetDTO.getRowStatusKey() == ApplicationConstants.DB_ROW_STATUS_DE_ACTIVE_VALUE) {
            if (!validationHelper.validateTextEmptyData(usetDTO.getStatusComment().trim())) {
                errors.rejectValue("statusComment", "error.user.statusComment");
            } else if (!validationHelper.validateDataLength(usetDTO.getStatusComment(), 1000)) {
                errors.rejectValue("statusComment", "error.user.statusComment.length");
            }
        }

    }

}
