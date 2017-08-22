/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.validators;

import com.optum.oss.dto.PermissionGroupDTO;
import com.optum.oss.helper.ValidationHelper;
import java.util.Properties;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author hpasupuleti
 */
public class ManagePermissionsValidator implements Validator{
    
    @Autowired
    private ValidationHelper validationHelper;
    
    @Resource(name = "myProperties")
    private Properties myProperties;
    
    @Override
    public boolean supports(Class clazz) {
      return PermissionGroupDTO.class.equals(clazz);
    }
    
    
    @Override
    public void validate(Object target, Errors errors) {
        PermissionGroupDTO permGroupDTO = (PermissionGroupDTO) target;
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, null, null);
        
        if(!validationHelper.validateTextEmptyData(permGroupDTO.getPermissionGroupName()))
        {
            errors.rejectValue("permissionGroupName", myProperties.getProperty("permgrp-name-mandatory"));
        }
        
        
    }
    
}
