/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.validators;

import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.dto.ClientEngagementSourceDTO;
import com.optum.oss.dto.EngagementPartnerUserDTO;
import com.optum.oss.dto.SecurityServiceDTO;
import com.optum.oss.dto.VulnerabilityDTO;
import com.optum.oss.helper.ValidationHelper;
import com.optum.oss.util.DateUtil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author sbhagavatula
 */
@Component
public class AddEngagementValidator implements Validator{
    
    private static final Logger logger = Logger.getLogger(AddEngagementValidator.class);

    @Autowired
    private ValidationHelper validationHelper;
    @Autowired
    private DateUtil dateUtil;
    
    @Override
    public boolean supports(Class clazz) {
        return VulnerabilityDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ClientEngagementDTO clientEngagementDTO = (ClientEngagementDTO) target;
        
        List<ClientEngagementSourceDTO> sourceList = clientEngagementDTO.getClientEngagementSourceLi();
        try {
            int i = 0;
            for(ClientEngagementSourceDTO sourceDTO : sourceList){
                if(sourceDTO.getClientEngSourceCode() != null){
                    if(!validationHelper.validateSelectEmptyData(sourceDTO.getSourceKey()+"")){
                        errors.rejectValue("clientEngagementSourceLi["+i+"].sourceKey", "clienteng.source");
                    }
                    if(!validationHelper.validateTextEmptyData(sourceDTO.getClientEngSourceCode()+"")){
                        errors.rejectValue("clientEngagementSourceLi["+i+"].clientEngSourceCode", "clienteng.project");
                    } else if (!validationHelper.validateDataLength(sourceDTO.getClientEngSourceCode(), 100)) {
                        errors.rejectValue("clientEngagementSourceLi["+i+"].clientEngSourceCode", "clienteng.project.length");
                    }
                    i++;
                }
            }

            if(!validationHelper.validateSelectEmptyData(clientEngagementDTO.getClientID()+"")){
                errors.rejectValue("clientID", "clienteng.clientname");
            }

            if(!validationHelper.validateSelectEmptyData(clientEngagementDTO.getSecurityPackageObj().getSecurityPackageCode()+"")){
                errors.rejectValue("securityPackageObj.securityPackageCode", "clienteng.product");
            }

            if (!validationHelper.validateTextEmptyData(clientEngagementDTO.getAgreementDate())) {
                errors.rejectValue("agreementDate", "clienteng.agreementdate");
            }

            if (!validationHelper.validateTextEmptyData(clientEngagementDTO.getEngagementName())) {
                errors.rejectValue("engagementName", "clienteng.engagementname");
            } else if (!validationHelper.validateDataLength(clientEngagementDTO.getEngagementName(), 100)) {
                errors.rejectValue("engagementName", "error.engagementname.length");
            }

            Date d = new Date();
            DateFormat sd = new SimpleDateFormat("MM/dd/yyyy");
            String todayDate = sd.format(d);

            if (!validationHelper.validateTextEmptyData(clientEngagementDTO.getEstimatedStartDate())) {
                errors.rejectValue("estimatedStartDate", "clienteng.startdate");
            } else if(dateUtil.convertionFromStringToDate(clientEngagementDTO.getEstimatedStartDate()).
                    compareTo(dateUtil.convertionFromStringToDate(todayDate)) < 0){
                errors.rejectValue("estimatedStartDate", "clienteng.startdate.validate");
            } else if(dateUtil.convertionFromStringToDate(clientEngagementDTO.getEstimatedStartDate()).
                    compareTo(dateUtil.convertionFromStringToDate(clientEngagementDTO.getAgreementDate())) < 0){
                errors.rejectValue("estimatedStartDate", "clienteng.startdate.validate1");
            }

            if (!validationHelper.validateTextEmptyData(clientEngagementDTO.getEstimatedEndDate())) {
                errors.rejectValue("estimatedEndDate", "clienteng.enddate");
            } else if(dateUtil.convertionFromStringToDate(clientEngagementDTO.getEstimatedEndDate()).
                    compareTo(dateUtil.convertionFromStringToDate(clientEngagementDTO.getEstimatedStartDate())) < 0){
                errors.rejectValue("estimatedEndDate", "clienteng.enddate.validate");
            }

            if (!validationHelper.validateTextEmptyData(clientEngagementDTO.getSelectedServices())) {
                errors.rejectValue("selectedServices", "clienteng.selectedservices");
            }

            List<SecurityServiceDTO> servicesList = clientEngagementDTO.getEngagementServiceLi();
            List<String> serviceCodes = Arrays.asList(clientEngagementDTO.getSelectedServices().split("\\s*,\\s*"));

            int k = 0;
            for (SecurityServiceDTO securityServiceDTO : servicesList) {
                boolean flag = false;
                if (serviceCodes.contains(securityServiceDTO.getSecurityServiceCode())) {
                    if (!validationHelper.validateTextEmptyData(securityServiceDTO.getServiceStartDate())) {
                        errors.rejectValue("selectedServices", "clienteng.servicestartdate");
                        flag = true;
                    } else if(dateUtil.convertionFromStringToDate(securityServiceDTO.getServiceStartDate()).
                            compareTo(dateUtil.convertionFromStringToDate(clientEngagementDTO.getEstimatedStartDate())) < 0){
                        errors.rejectValue("selectedServices", "clienteng.servicestartdate.validate1");
                        flag = true;
                    } else if(dateUtil.convertionFromStringToDate(securityServiceDTO.getServiceStartDate()).
                            compareTo(dateUtil.convertionFromStringToDate(clientEngagementDTO.getEstimatedEndDate())) > 0){
                        errors.rejectValue("selectedServices", "clienteng.servicestartdate.validate2");
                        flag = true;
                    }

                    if (!validationHelper.validateTextEmptyData(securityServiceDTO.getServiceEndDate())) {
                        errors.rejectValue("selectedServices", "clienteng.serviceenddate");
                        flag = true;
                    } else if(dateUtil.convertionFromStringToDate(securityServiceDTO.getServiceEndDate()).
                            compareTo(dateUtil.convertionFromStringToDate(clientEngagementDTO.getEstimatedStartDate())) < 0){
                        errors.rejectValue("selectedServices", "clienteng.serviceenddate.validate1");
                        flag = true;
                    } else if(dateUtil.convertionFromStringToDate(securityServiceDTO.getServiceEndDate()).
                            compareTo(dateUtil.convertionFromStringToDate(clientEngagementDTO.getEstimatedEndDate())) > 0){
                        errors.rejectValue("selectedServices", "clienteng.serviceenddate.validate2");
                        flag = true;
                    } else if(dateUtil.convertionFromStringToDate(securityServiceDTO.getServiceEndDate()).
                            compareTo(dateUtil.convertionFromStringToDate(securityServiceDTO.getServiceStartDate())) < 0){
                        errors.rejectValue("selectedServices", "clienteng.serviceenddate.validate3");
                        flag = true;
                    }

                    if(flag) break;
    //                if (!validationHelper.validateTextEmptyData(securityServiceDTO.getServiceStartDate())) {
    //                    errors.rejectValue("engagementServiceLi["+k+"].serviceStartDate", "clienteng.servicestartdate");
    //                } else if(securityServiceDTO.getServiceStartDate().compareTo(clientEngagementDTO.getEstimatedStartDate()) < 0){
    //                    errors.rejectValue("engagementServiceLi["+k+"].serviceStartDate", "clienteng.servicestartdate.validate1");
    //                } else if(securityServiceDTO.getServiceStartDate().compareTo(clientEngagementDTO.getEstimatedEndDate()) > 0){
    //                    errors.rejectValue("engagementServiceLi["+k+"].serviceStartDate", "clienteng.servicestartdate.validate2");
    //                }
    //                if (!validationHelper.validateTextEmptyData(securityServiceDTO.getServiceEndDate())) {
    //                    errors.rejectValue("engagementServiceLi["+k+"].serviceEndDate", "clienteng.serviceenddate");
    //                } else if(securityServiceDTO.getServiceEndDate().compareTo(clientEngagementDTO.getEstimatedStartDate()) < 0){
    //                    errors.rejectValue("engagementServiceLi["+k+"].serviceEndDate", "clienteng.serviceenddate.validate1");
    //                } else if(securityServiceDTO.getServiceEndDate().compareTo(clientEngagementDTO.getEstimatedEndDate()) > 0){
    //                    errors.rejectValue("engagementServiceLi["+k+"].serviceEndDate", "clienteng.serviceenddate.validate2");
    //                } else if(securityServiceDTO.getServiceEndDate().compareTo(securityServiceDTO.getServiceStartDate()) < 0){
    //                    errors.rejectValue("engagementServiceLi["+k+"].serviceEndDate", "clienteng.serviceenddate.validate3");
    //                }
                }
                k++;
            }

            if (!validationHelper.validateDataLength(clientEngagementDTO.getEngagementStatusComment(), 1000)) {
                errors.rejectValue("engagementStatusComment", "clienteng.comments.length");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : AddEngagementValidator: validate(): " + e.getMessage());
        }
    }
    
}
