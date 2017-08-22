/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.validators;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dto.ClientReportsUploadDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.ValidationHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author schanganti
 */
@Component
public class ClientReportsUploadValidator implements Validator {

    @Autowired
    private ValidationHelper validationHelper;

    @Override
    public boolean supports(Class clazz) {
        return ClientReportsUploadDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ClientReportsUploadDTO reportsDTO = (ClientReportsUploadDTO) target;
        String fileExt = "";
        try {
            if (reportsDTO.getServerSideFlag().equalsIgnoreCase("U")) {
//                if (!validationHelper.validateSelectEmptyData(String.valueOf(reportsDTO.getReportkey()))) {
//                    errors.rejectValue("reportkey", "error.clientreportupload.report-name");
//                }
                if (0 == reportsDTO.getReportkey()) {
                    errors.rejectValue("reportkey", "error.clientreportupload.report-name");
                }
                
                String fileName = reportsDTO.getTemplateupload().getOriginalFilename();
                if (!fileName.equalsIgnoreCase("")) {
                    fileExt = fileName.substring(fileName.indexOf(".") + 1, fileName.length());
                }
                if (!validationHelper.validateTextEmptyData(reportsDTO.getTemplateupload().getOriginalFilename())) {
                    errors.rejectValue("templateupload", "error.clientreportupload.file-upload-empty");
                } else if (!(fileExt.equalsIgnoreCase("xls") || fileExt.equalsIgnoreCase("xlsx") || fileExt.equalsIgnoreCase("csv") || fileExt.equalsIgnoreCase("xml")
                        || fileExt.equalsIgnoreCase("jpeg") || fileExt.equalsIgnoreCase("jpg") || fileExt.equalsIgnoreCase("png")
                        || fileExt.equalsIgnoreCase("bmp") || fileExt.equalsIgnoreCase("pdf") || fileExt.equalsIgnoreCase("doc")
                        || fileExt.equalsIgnoreCase("docx"))) {
                    errors.rejectValue("templateupload", "error.clientreportupload.file-upload-invalid-file-format");
                }
            } else if (reportsDTO.getServerSideFlag().equalsIgnoreCase("P")) {
                if (null == reportsDTO.getReportNameCheckId() || StringUtils.isEmpty(reportsDTO.getReportNameCheckId().toString())) {
                    errors.rejectValue("reportNameCheckId", "error.clientreportupload.publish-checkbox");
                }
            }

        } catch (Exception ex) {
            // Logger.getLogger(EngagementDataUploadValidator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
