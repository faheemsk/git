/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.validators;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.ValidationHelper;
import com.optum.oss.service.impl.EngagementDataUploadServiceImpl;
import com.optum.oss.util.EncryptDecrypt;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author akeshavulu
 */
@Component
public class EngagementDataUploadValidator implements Validator {

    @Autowired
    private ValidationHelper validationHelper;

    @Autowired
    private EngagementDataUploadServiceImpl EngagementDataUploadService;

    @Autowired
    private EncryptDecrypt encDec;

    @Override
    public boolean supports(Class clazz) {
        return ClientEngagementDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ClientEngagementDTO clientEngagementDTO = (ClientEngagementDTO) target;
        String fileExt = "";
        String fileName = clientEngagementDTO.getTemplateupload().getOriginalFilename();
        List<ClientEngagementDTO> uploadedFilesList = new ArrayList();
        String fileNamePattern = "^[a-zA-Z0-9.\\-_ ]{0,255}$";
        Pattern fNamePatrn = Pattern.compile(fileNamePattern);
        Matcher fnameMatchr = fNamePatrn.matcher(fileName);
        if (!clientEngagementDTO.getUploadedFilesCount().equalsIgnoreCase("1")) {

            if (!validationHelper.validateSelectEmptyData(String.valueOf(clientEngagementDTO.getSourceNameKey()))) {
                errors.rejectValue("sourceNameKey", "fileuploadValidate.finding-source-name-empty");
            }
            if (!validationHelper.validateSelectEmptyData(String.valueOf(clientEngagementDTO.getDocumentTypeKey()))) {
                errors.rejectValue("documentTypeKey", "fileuploadValidate.document-type-name-empty");
            }
            if (!validationHelper.validateDataLength(clientEngagementDTO.getUploadComments(), 1000)) {
                errors.rejectValue("uploadComments", "fileuploadValidate.upload-comments-exceeds");
            }
            if (!validationHelper.validateTextEmptyData(clientEngagementDTO.getTemplateupload().getOriginalFilename())) {
                errors.rejectValue("templateupload", "fileuploadValidate.file-upload-empty");
            } else {
                try {
                    int duplicateFileName = EngagementDataUploadService.validateDuplicateFileName(fileName,
                            encDec.decrypt(clientEngagementDTO.getEncEngagementCode()),
                            encDec.decrypt(clientEngagementDTO.getEncSecurityServiceCode()));
                    if (duplicateFileName > 0) {
                        errors.rejectValue("templateupload", "fileuploadValidate.file-upload-duplicate-file");
                    } else if (clientEngagementDTO.getDocumentTypeKey() == ApplicationConstants.DB_CONSTANT_DATA_TYPE_DATA) {

                        if (!fileName.equalsIgnoreCase("")) {
                            fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
                        }
                        if (!(fileExt.equalsIgnoreCase("xls") || fileExt.equalsIgnoreCase("xlsx") || fileExt.equalsIgnoreCase("csv"))) {
                            errors.rejectValue("templateupload", "fileuploadValidate.document-type-invalid-file-format");
                        }else {
                            if (!fnameMatchr.find()) {
                                errors.rejectValue("templateupload", "fileuploadValidate.file-name-validate");
                            }
                        }
                        

                    } else {

                        //   String fileName = clientEngagementDTO.getTemplateupload().getOriginalFilename();
                        if (!fileName.equalsIgnoreCase("")) {
                            fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
                        }
                        if (!(fileExt.equalsIgnoreCase("xls") || fileExt.equalsIgnoreCase("xlsx") || fileExt.equalsIgnoreCase("csv") || fileExt.equalsIgnoreCase("xml")
                                || fileExt.equalsIgnoreCase("jpeg") || fileExt.equalsIgnoreCase("jpg") || fileExt.equalsIgnoreCase("png")
                                || fileExt.equalsIgnoreCase("bmp") || fileExt.equalsIgnoreCase("pdf") || fileExt.equalsIgnoreCase("doc")
                                || fileExt.equalsIgnoreCase("docx"))) {
                            errors.rejectValue("templateupload", "fileuploadValidate.file-upload-invalid-file-format");
                        }else {
                            if (!fnameMatchr.find()) {
                                errors.rejectValue("templateupload", "fileuploadValidate.file-name-validate");
                            }
                        
                        }
                        
                    }

                } catch (AppException ex) {
                    // Logger.getLogger(EngagementDataUploadValidator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else {
            try {
                clientEngagementDTO.setEngagementCode(encDec.decrypt(clientEngagementDTO.getEncEngagementCode()));
                clientEngagementDTO.setSecurityServiceCode(encDec.decrypt(clientEngagementDTO.getEncSecurityServiceCode()));
                uploadedFilesList = EngagementDataUploadService.fetchUploadedFilesDetails(clientEngagementDTO);
                if(null!=uploadedFilesList && clientEngagementDTO.isLockCheckbox() == true){
                    if(uploadedFilesList.size() <= 0){
                        errors.rejectValue("lockCheckbox", "fileuploadValidate.lock-service-data");
                    }
                }
            } catch (AppException ex) {
              //  Logger.getLogger(EngagementDataUploadValidator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
