/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.validators;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.controller.OrganizationController;
import com.optum.oss.dto.OrganizationDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.ValidationHelper;
import com.optum.oss.service.impl.OrganizationServiceImpl;
import com.optum.oss.util.EncryptDecrypt;
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
public class OrganizationValidator implements Validator {

    private static final Logger logger = Logger.getLogger(OrganizationValidator.class);

    @Autowired
    private ValidationHelper validationHelper;

    @Autowired
    private OrganizationServiceImpl organizationService;

    @Autowired
    private EncryptDecrypt encDec;

    @Override

    public boolean supports(Class clazz) {
        return OrganizationDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OrganizationDTO orgDTO = (OrganizationDTO) target;

        if (!validationHelper.validateTextEmptyData(orgDTO.getOrganizationName().trim())) {
            errors.rejectValue("organizationName", "error.organization.name");
        } else if (!validationHelper.validateDataLength(orgDTO.getOrganizationName().trim(), 100)) {
            errors.rejectValue("organizationName", "error.organization.name.length");
        } else {
            try {
                if (orgDTO.getEncOrganizationName() != null && !orgDTO.getEncOrganizationName().equalsIgnoreCase("")) {
                    String orgName = encDec.decrypt(orgDTO.getEncOrganizationName());
                    if (!orgName.equalsIgnoreCase(orgDTO.getOrganizationName())) {
                        int retVal = organizationService.validateOrganization(orgDTO.getOrganizationName());
                        if (retVal > 0) {
                            errors.rejectValue("organizationName", "organization.validate-message");
                        }
                    }

                } else {
                    int retVal = organizationService.validateOrganization(orgDTO.getOrganizationName());
                    if (retVal > 0) {
                        errors.rejectValue("organizationName", "organization.validate-message");
                    }
                }
            } catch (AppException e) {
                errors.rejectValue("organizationName", "organization.validate-message");
            }
        }

        if (!validationHelper.validateSelectEmptyData(orgDTO.getOrganizationTypeKey() + "")) {
            errors.rejectValue("organizationTypeKey", "error.organization.type");
        }

        if (!validationHelper.validateSelectEmptyData(orgDTO.getOrganizationIndustryKey() + "")) {
            errors.rejectValue("organizationIndustryKey", "error.organization.industry");
        }

        if (!validationHelper.validateTextEmptyData(orgDTO.getCountryName())) {
            errors.rejectValue("countryName", "error.organization.country");
        }

        if (!validationHelper.validateTextEmptyData(orgDTO.getStreetAddress1().trim())) {
            errors.rejectValue("streetAddress1", "error.organization.streetAddress1");
        } else if (!validationHelper.validateDataLength(orgDTO.getStreetAddress1(), 100)) {
            errors.rejectValue("streetAddress1", "error.organization.streetAddress1.length");
        }

        if (!validationHelper.validateDataLength(orgDTO.getStreetAddress2(), 100)) {
            errors.rejectValue("streetAddress2", "error.organization.streetAddress2.length");
        }

        if (!validationHelper.validateTextEmptyData(orgDTO.getCityName().trim())) {
            errors.rejectValue("cityName", "error.organization.city");
        } else if (!validationHelper.validateDataLength(orgDTO.getCityName(), 100)) {
            errors.rejectValue("cityName", "error.organization.city.length");
        } else if (!validationHelper.validateOnlyAlphabets(orgDTO.getCityName())) {
            errors.rejectValue("cityName", "error.organization.city.alphabets");
        }

        if (!validationHelper.validateTextEmptyData(orgDTO.getStateName())) {
            errors.rejectValue("stateName", "error.organization.state");
        }

        if (!validationHelper.validateTextEmptyData(orgDTO.getPostalCode().trim())) {
            errors.rejectValue("postalCode", "error.organization.postalCode");
        } else if (!validationHelper.validateDataLength(orgDTO.getPostalCode(), 10)) {
            errors.rejectValue("postalCode", "error.organization.postalCode.length");
        } else if (!validationHelper.validateAlphaNumeric(orgDTO.getPostalCode())) {
            errors.rejectValue("postalCode", "error.organization.postalCode.alphanumeric");
        }

        if (!validationHelper.validateDataLength(orgDTO.getOrganizationDescription().trim(), 1000)) {
            errors.rejectValue("organizationDescription", "error.organization.organizationDescription.length");
        }

        if (orgDTO.getRowStatusKey() == ApplicationConstants.DB_ROW_STATUS_DE_ACTIVE_VALUE) {
             if (!validationHelper.validateTextEmptyData(orgDTO.getDeactiveReason().trim())) {
                errors.rejectValue("deactiveReason", "error.organization.deactiveReason");
            } else if (!validationHelper.validateDataLength(orgDTO.getDeactiveReason(), 1000)) {
                errors.rejectValue("deactiveReason", "error.organization.deactiveReason.length");
            }
        }
        int sourceCount = 0;
        boolean sourceErrors = true;
        for (Long sourceKeyValue : orgDTO.getSourceKeyArray()) {
            if (!validationHelper.validateSelectEmptyData(sourceKeyValue + "")) {
                errors.rejectValue("sourceKeyArray[" + sourceCount + "]", "error.organization.authoritativeSource");
                sourceErrors = false;
            }
            sourceCount++;
        }

        int sourceIDCount = 0;
        for (String sourceID : orgDTO.getSourceClientIDArray()) {
            if (!validationHelper.validateDataLength(sourceID, 100)) {
                errors.rejectValue("sourceClientIDArray[" + sourceIDCount + "]", "error.organization.sourceOrganizationID");
                sourceErrors = false;
            }
            sourceIDCount++;
        }

//        int sourceIcCount = 0;
//
//        for (String sourceIDValue: orgDTO.getSourceClientIDArray()) {
//            if (!validationHelper.validateSelectEmptyData(sourceIDValue)) {
//                errors.rejectValue("sourceClientIDArray[" + sourceIcCount + "]", "error.organization.authoritativeSource");
//            }
//            sourceIcCount++;
//        }
        if (sourceErrors) {
            boolean retVal = compareAuthoritativeSourceDuplicate(orgDTO);
            if (!retVal) {
                errors.rejectValue("sourceKeyArray[00]", "error.organization.authoritativeSourceExists");
            }
        }

    }

    /* checking the duplication for authoratitiveSouce drop down */
    private boolean compareAuthoritativeSourceDuplicate(OrganizationDTO orgDTO) {
        long sourceValuesOne[] = orgDTO.getSourceKeyArray();
        long sourceValuesTwo[] = orgDTO.getSourceKeyArray();

        boolean retval = true;
        for (int i = 0; i < sourceValuesOne.length; i++) {

            for (int j = 0; j < sourceValuesTwo.length; j++) {
                if (i != j) {
                    if (sourceValuesOne[i] == sourceValuesTwo[j]) {
                        return false;
                    }
                }
            }

        }
        return retval;
    }

}
