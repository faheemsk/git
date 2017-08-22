/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.controller;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.constants.PermissionConstants;
import com.optum.oss.constants.SessionConstants;
import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.dto.ExceptionDTO;
import com.optum.oss.dto.ManageServiceUserDTO;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.service.impl.EngagementDataUploadServiceImpl;
import com.optum.oss.service.impl.MasterLookUpServiceImpl;
import com.optum.oss.util.CommonUtil;
import com.optum.oss.util.EncryptDecrypt;
import com.optum.oss.util.GenerateRandomNumber;
import com.optum.oss.util.LoggerUtil;
import com.optum.oss.validators.EngagementDataUploadValidator;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author akeshavulu
 */
@Controller
public class EngagementDataUploadController {

    private static final Logger logger = Logger.getLogger(EngagementDataUploadController.class);

    @Resource(name = "myProperties")
    private Properties myProperties;

    /*
     Start Autowiring the required Class instances
     */
    @Autowired
    private EngagementDataUploadServiceImpl EngagementDataUploadService;

    @Autowired
    private MasterLookUpServiceImpl masterLookUpService;

    @Autowired
    private EncryptDecrypt encDec;

    @Autowired
    private LoggerUtil auditLogger;

    @Autowired
    private GenerateRandomNumber generateRandom;
    
    @Autowired
    private EngagementDataUploadValidator engmntDataValidator;
    
    @Autowired
    private CommonUtil commonUtil;
    /*
     End Autowiring the required Class instances
     */
    /*
     START : LOG MESSAGES
     */
    private final String TRACE_USER_ENG_DATA_UPLOAD = "User accessed engagemenetDataUpload";
    private final String TRACE_USER_SAVE_UPLOAD_FILE = "User accessed saveUploadFile";
    private final String TRACE_USER_DOWNLOAD_FILE = "User accessed downloadFile";
    private final String TRACE_USER_DELETE_UPLOAD_FILES = "User accessed deleteUploadedFiles";
    private final String TRACE_USER_LOCK_UNLOCK_SERVICE_DATA = "User accessed lock unlock service data";
    private final String TRACE_USER_VIEW_SERVICE_DATA = "User accessed viewSericeData";
    private final String TRACE_USER_ENG_UPLOAD_WORKLIST = "User accessed upload worklist";
    private final String TRACE_USER_SEARCH_ENG_SERVICE_WORKLIST = "User accessed searchEngServiceWorkList";
    /*
     END: LOG MESSAGES
     */

    @InitBinder("engmntServiceDetails")
    private void engagementDataUploadBinder(WebDataBinder binder)
            throws Exception {
        binder.setAllowedFields(new String[]{
            "encS"
        });

    }
    
    /**
     * NAVIGATES TO THE ENGAGEMENT DATA UPLOAD PAGE
     *
     * @param request
     * @param response
     * @param clientEngagementDTO
     * @param redirectAttributes
     * @param model
     * @return ModelAndView
     */
    @RequestMapping(value = "/engagemenetDataUpload")
    public ModelAndView engagementDataUpload(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute("engmntServiceDetails") ClientEngagementDTO clientEngagementDTO,
            Model model, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        List<ClientEngagementDTO> uploadedFilesList = new ArrayList();
        Set<String> clientEngmntPermissionSet = new HashSet<>();
        Set<String> engmntDataUploadPermissionSet = new HashSet<>();
        String emptyString = null;
        Set<String> allPermissionSet = new HashSet<>();
        try {
            
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return new ModelAndView("redirect:/logout.htm");
            }
            if (model.containsAttribute("uploadFileDetails")) {
                ClientEngagementDTO uploadedDetails = (ClientEngagementDTO) model.asMap().get("uploadFileDetails");
                clientEngagementDTO.setEncS(uploadedDetails.getEncS());
                modelAndView.addObject("uploadFileDetails", uploadedDetails);
                modelAndView.addObject("redirectFlag", "Yes");
            }
            HttpSession session = request.getSession();
            UserProfileDTO userDto = new UserProfileDTO();
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userDto.getEmail(), userDto.getUserMACAddress(), " User accessed engagement DataUploadengagement DataUpload page"));
                logger.trace(TRACE_USER_ENG_DATA_UPLOAD);
                engmntDataUploadPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.ENGAGEMENT_DATA_UPLOAD_MENU);
                clientEngmntPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU);
                if (clientEngmntPermissionSet != null) {
                    allPermissionSet.addAll(clientEngmntPermissionSet);
                }
                if (engmntDataUploadPermissionSet != null) {
                    allPermissionSet.addAll(engmntDataUploadPermissionSet);
                }
            }
            String reqRand = "";
            if (session.getAttribute("requestRandom") != null) {
                reqRand = (String) session.getAttribute("requestRandom");
              //  session.setAttribute("requestRandom", null);
            }
            if (!StringUtils.isEmpty(clientEngagementDTO.getEncS())) {
                String encString = encDec.decrypt(clientEngagementDTO.getEncS());
                if (encString.contains("@")) {
                    String arrReqParams[] = encString.split("@");
                    if (arrReqParams.length == 3) {
                        String reqToken = arrReqParams[2];
                        if (reqRand.equalsIgnoreCase(reqToken)) {
                            clientEngagementDTO.setEngagementCode(arrReqParams[0]);
                            clientEngagementDTO.setSecurityServiceCode(arrReqParams[1]);
                            clientEngagementDTO.setCreatedByUserID(userDto.getUserProfileKey());
                            String encS = clientEngagementDTO.getEncS();
                            modelAndView.addObject("encryptedEngagementCode", encDec.encrypt(clientEngagementDTO.getEngagementCode()));
                            modelAndView.addObject("encryptedSecurityServiceCode", encDec.encrypt(clientEngagementDTO.getSecurityServiceCode()));

                            clientEngagementDTO = EngagementDataUploadService.fetchServiceEngagementDetailsByEgmntCode(clientEngagementDTO.getEngagementCode(),
                                    clientEngagementDTO.getSecurityServiceCode(), clientEngagementDTO.getCreatedByUserID());
                            List<MasterLookUpDTO> sourceNameList = masterLookUpService.fetchMasterLookUpEntitiesByEntityType(ApplicationConstants.DB_CONSTANT_FINDING_SOURCE_NAME);
                            List<MasterLookUpDTO> documentTypeList = masterLookUpService.fetchMasterLookUpEntitiesByEntityType(ApplicationConstants.DB_CONSTANT_DOCUMENT_TYPE);
                            clientEngagementDTO.setCreatedByUserID(userDto.getUserProfileKey());
                            clientEngagementDTO.setEncS(encS);
                            if (!allPermissionSet.isEmpty()) {
                                if (allPermissionSet.contains(PermissionConstants.ADD_USER_TO_SERVICE) || allPermissionSet.contains(PermissionConstants.EDIT_USER_TO_SERVICE)) {
                                    if (userDto.getUserTypeObj().getLookUpEntityName().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_INTERNAL)) {
                                        clientEngagementDTO.setUserTypeFlag(ApplicationConstants.USER_TYPE_ENGAGEMENT_LEAD);
                                    } else if (userDto.getUserTypeObj().getLookUpEntityName().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_PARTNER)) {
                                        clientEngagementDTO.setUserTypeFlag(ApplicationConstants.USER_TYPE_PARTNER_LEAD);
                                    }
                                } else {
                                    if (userDto.getUserTypeObj().getLookUpEntityName().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_INTERNAL)) {
                                        clientEngagementDTO.setUserTypeFlag(ApplicationConstants.USER_TYPE_ENGAGEMENT_ANALYST);
                                    }
                                    if (userDto.getUserTypeObj().getLookUpEntityName().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_PARTNER)) {
                                        clientEngagementDTO.setUserTypeFlag(ApplicationConstants.USER_TYPE_PARTNER_USER);
                                    }
                                }
                            }
                            uploadedFilesList = EngagementDataUploadService.fetchUploadedFilesDetails(clientEngagementDTO);

                            if (null != uploadedFilesList) {
                                modelAndView.addObject("uploadedFilesListSize", uploadedFilesList.size() + "");
                            }

                            modelAndView.addObject("uploadedFilesList", uploadedFilesList);
                            modelAndView.addObject("clientEngagementDTO", clientEngagementDTO);
                            modelAndView.addObject("sourceNameList", sourceNameList);
                            modelAndView.addObject("documentTypeList", documentTypeList);
                            modelAndView.addObject("engmntDataUploadPermissionSet", engmntDataUploadPermissionSet);
                            modelAndView.addObject("clientEngmntPermissionSet", clientEngmntPermissionSet);
                            modelAndView.addObject("allPermissionSet", allPermissionSet);
                            request.setAttribute("successMessage", emptyString);
                        } else {
                            logger.debug("URL Parameter Tampered ");
                           // ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message-badreq"));
                           // return new ModelAndView("/exception", "exceptionBean", exception);
                            return new ModelAndView("redirect:/logout.htm");
                        }
                    } else {
                        logger.debug("URL Parameter Tampered ");
                        //ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message-badreq"));
                        //return new ModelAndView("/exception", "exceptionBean", exception);
                        return new ModelAndView("redirect:/logout.htm");
                    }
                } else {
                    logger.debug("URL Parameter Tampered ");
                  //  ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message-badreq"));
                   // return new ModelAndView("/exception", "exceptionBean", exception);
                    return new ModelAndView("redirect:/logout.htm");
                }
            } else {
                logger.debug("URL Parameter Tampered ");
                //ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message-badreq"));
                //return new ModelAndView("/exception", "exceptionBean", exception);
                return new ModelAndView("redirect:/logout.htm"); 
            }

        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the engagementDataUpload Action :: " + e.getMessage());
           // ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            //return new ModelAndView("/exception", "exceptionBean", exception);
            return new ModelAndView("redirect:/logout.htm");
        }

        modelAndView.setViewName("/engagementdataupload/engagementdataupload");
        return modelAndView;
    }

    
    /**
     * DOWNLOAD FILE
     *
     * @param request
     * @param response
     * @param clientEngagementDTO
     * @return ModelAndView
     */
    @RequestMapping(value = "/downloadFile")
    public void engagementDataFiledownload(HttpServletRequest request, HttpServletResponse response) {

        String filenamedocument = "";
        String pathfromdownload = "";
        
        String encFileNamePath = "";

        try {
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                UserProfileDTO userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userDto.getEmail(), userDto.getUserMACAddress(), " User accessed engagementDataFiledownload page"));
                logger.trace(TRACE_USER_DOWNLOAD_FILE);
            }

             String destFolder = System.getProperty("OSS_UPLOAD_FOLDER");
               if (null != request.getParameter("encFileDownload")) {
                encFileNamePath = request.getParameter("encFileDownload")+"";
            }
             String decString = encDec.decrypt(encFileNamePath);
             String arrReqParams[] = decString.split("@");  
             filenamedocument = arrReqParams[0];
             pathfromdownload = arrReqParams[1];
             pathfromdownload = destFolder + pathfromdownload;

            // construct the complete absolute path of the file
            if (pathfromdownload != null) {
                String fullPath = pathfromdownload;

                File downloadFile = new File(fullPath);
                response.setHeader("Content-Disposition", "attachment;filename= \"" + filenamedocument + "\"");
                FileInputStream fin = new FileInputStream(downloadFile);

                int size = fin.available();
                response.setContentLength(size);
                byte[] ab = new byte[size];
                OutputStream os = response.getOutputStream();
                int bytesread;
                do {
                    bytesread = fin.read(ab, 0, size);
                    if (bytesread > -1) {
                        os.write(ab, 0, bytesread);
                    }
                } while (bytesread > -1);
 
                fin.close();
                os.flush();
                os.close();
            }

        } catch (Exception ie) {
            logger.debug("Exception Occured while Executing the engagementDataFiledownload Action :: " + ie.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), ie);
            //return new ModelAndView("/exception", "exceptionBean", exception);
        }

        //return new ModelAndView("/engagementdataupload/engagementdataupload");
        
    }

    @InitBinder("uploadFileDetails")
    private void deleteUploadedFileAndLckUnlkDetailsBinder(WebDataBinder binder)
            throws Exception {
        binder.setAllowedFields(new String[]{"encS",
            "lockCheckbox",
            "sourceNameKey",
            "documentTypeKey",
            "uploadComments",
            "templateupload",
            "encEngagementCode",
            "encSecurityServiceCode"
        });

    }
    
    /**
     * TO DELETE A FILE FROM THE UPLOADED FILES WORKLIST
     *
     * @param request
     * @param response
     * @param clientEngagementDTO
     * @param redirectAttributes
     * @param model
     * @return ModelAndView
     */
    @RequestMapping(value = "/deleteUploadedFiles")
    public ModelAndView deleteUploadedFileDetails(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute("uploadFileDetails") ClientEngagementDTO clientEngagementDTO, RedirectAttributes redirectAttributes, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        List<ClientEngagementDTO> uploadedFilesList = new ArrayList<>();
        Set<String> clientEngmntPermissionSet = new HashSet<>();
        Set<String> engmntDataUploadPermissionSet = new HashSet<>();
        Set<String> allPermissionSet = new HashSet<>();
        int retVal = 0;
        int fileUploadId = 0;
        String encFileID = "";
        try {
            HttpSession session = request.getSession();
            
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return new ModelAndView("redirect:/logout.htm");
            }
            UserProfileDTO userDto = new UserProfileDTO();
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());

//                logger.trace(auditLogger.prepareLog(userDto.getEmail(), userDto.getUserMACAddress(), " User accessed deleteUploadedFileDetails page"));
                logger.trace(TRACE_USER_DELETE_UPLOAD_FILES);
                engmntDataUploadPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.ENGAGEMENT_DATA_UPLOAD_MENU);
                clientEngmntPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU);
            }

            if (clientEngmntPermissionSet != null) {
                allPermissionSet.addAll(clientEngmntPermissionSet);
            }
            if (engmntDataUploadPermissionSet != null) {
                allPermissionSet.addAll(engmntDataUploadPermissionSet);
            }

            if (null != request.getParameter("encfileUploadID")) {
                encFileID = request.getParameter("encfileUploadID") + "";
            }
            int decryptFileID = 0;
            if (encFileID != null && !encFileID.equalsIgnoreCase("")) {
                decryptFileID = Integer.parseInt(encDec.decrypt(encFileID));
            }
            
            
            clientEngagementDTO.setEngagementCode(encDec.decrypt(clientEngagementDTO.getEncEngagementCode()));
            clientEngagementDTO.setSecurityServiceCode(encDec.decrypt(clientEngagementDTO.getEncSecurityServiceCode()));
            clientEngagementDTO.setCreatedByUserID(userDto.getUserProfileKey());
            modelAndView.addObject("encryptedEngagementCode", clientEngagementDTO.getEncEngagementCode());
            modelAndView.addObject("encryptedSecurityServiceCode", clientEngagementDTO.getEncSecurityServiceCode());
            String encS = clientEngagementDTO.getEncS();
            clientEngagementDTO = EngagementDataUploadService.fetchServiceEngagementDetailsByEgmntCode(clientEngagementDTO.getEngagementCode(),
                    clientEngagementDTO.getSecurityServiceCode(), clientEngagementDTO.getCreatedByUserID());
            clientEngagementDTO.setEncS(encS);
            List<MasterLookUpDTO> sourceNameList = masterLookUpService.fetchMasterLookUpEntitiesByEntityType(ApplicationConstants.DB_CONSTANT_FINDING_SOURCE_NAME);
            List<MasterLookUpDTO> documentTypeList = masterLookUpService.fetchMasterLookUpEntitiesByEntityType(ApplicationConstants.DB_CONSTANT_DOCUMENT_TYPE);
            modelAndView.addObject("clientEngagementDTO", clientEngagementDTO);
            modelAndView.addObject("sourceNameList", sourceNameList);
            modelAndView.addObject("documentTypeList", documentTypeList);
            modelAndView.addObject("engmntDataUploadPermissionSet", engmntDataUploadPermissionSet);
            modelAndView.addObject("clientEngmntPermissionSet", clientEngmntPermissionSet);
            modelAndView.addObject("allPermissionSet", allPermissionSet);

            clientEngagementDTO.setCreatedByUserID(userDto.getUserProfileKey());

            if (allPermissionSet.contains(PermissionConstants.ADD_USER_TO_SERVICE) || allPermissionSet.contains(PermissionConstants.EDIT_USER_TO_SERVICE)) {
                if (userDto.getUserTypeObj().getLookUpEntityName().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_INTERNAL)) {
                    clientEngagementDTO.setUserTypeFlag(ApplicationConstants.USER_TYPE_ENGAGEMENT_LEAD);
                } else if (userDto.getUserTypeObj().getLookUpEntityName().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_PARTNER)) {
                    clientEngagementDTO.setUserTypeFlag(ApplicationConstants.USER_TYPE_PARTNER_LEAD);
                }
            } else {
                if (userDto.getUserTypeObj().getLookUpEntityName().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_INTERNAL)) {
                    clientEngagementDTO.setUserTypeFlag(ApplicationConstants.USER_TYPE_ENGAGEMENT_ANALYST);
                }
                if (userDto.getUserTypeObj().getLookUpEntityName().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_PARTNER)) {
                    clientEngagementDTO.setUserTypeFlag(ApplicationConstants.USER_TYPE_PARTNER_USER);
                }
            }
            
           
            retVal = EngagementDataUploadService.deleteUploadedFileDetails(decryptFileID);
            if (retVal > 0) {
                request.setAttribute("successMessage", myProperties.getProperty("filedelete.success-message"));
            }
            
            uploadedFilesList = EngagementDataUploadService.fetchUploadedFilesDetails(clientEngagementDTO);
            modelAndView.addObject("uploadedFilesList", uploadedFilesList);
            if (null != uploadedFilesList) {
                modelAndView.addObject("uploadedFilesListSize", uploadedFilesList.size());
            }
            
//            if (loctStat == false && uploadedFilesList.size() > 1) {
//                retVal = EngagementDataUploadService.deleteUploadedFileDetails(decryptFileID);
//                if (retVal > 0) {
//                    request.setAttribute("successMessage", myProperties.getProperty("filedelete.success-message"));
//                }
//            } else {
//                request.setAttribute("lockDeleteMessage", myProperties.getProperty("lockdelete.message"));
//            }
            
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the deleteUploadedFileDetails Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        modelAndView.setViewName("/engagementdataupload/engagementdataupload");
        return modelAndView;
    }

    /**
     * TO LOCK OR UNLOCK A SERVICE DATA
     *
     * @param request
     * @param response
     * @param redirectAttributes
     * @param clientEngagementDTO
     * @param result
     * @param model
     * @return ModelAndView
     */
    @RequestMapping(value = "lockUnlockServiceData")
    public String lockUnlockServiceData(HttpServletRequest request, HttpServletResponse response, 
            RedirectAttributes redirectAttributes,
            @ModelAttribute("uploadFileDetails") ClientEngagementDTO clientEngagementDTO, 
            BindingResult result, Model model) {
        Set<String> clientEngmntPermissionSet = new HashSet<>();
        Set<String> engmntDataUploadPermissionSet = new HashSet<>();
        Set<String> allPermissionSet = new HashSet<>();
        try {
            
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            boolean lockStat = clientEngagementDTO.isLockCheckbox();
            HttpSession session = request.getSession();
            UserProfileDTO userDto = new UserProfileDTO();
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());

//                logger.trace(auditLogger.prepareLog(userDto.getEmail(), userDto.getUserMACAddress(), " User accessed lockUnlockServiceData page"));
                logger.trace(TRACE_USER_LOCK_UNLOCK_SERVICE_DATA);
                engmntDataUploadPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.ENGAGEMENT_DATA_UPLOAD_MENU);
                clientEngmntPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU);
                if (clientEngmntPermissionSet != null) {
                    allPermissionSet.addAll(clientEngmntPermissionSet);
                }
                if (engmntDataUploadPermissionSet != null) {
                    allPermissionSet.addAll(engmntDataUploadPermissionSet);
                }
            }
            
             clientEngagementDTO.setCreatedByUserID(userDto.getUserProfileKey());
            if (!allPermissionSet.isEmpty()) {
                if (allPermissionSet.contains(PermissionConstants.ADD_USER_TO_SERVICE) || allPermissionSet.contains(PermissionConstants.EDIT_USER_TO_SERVICE)) {
                    if (userDto.getUserTypeObj().getLookUpEntityName().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_INTERNAL)) {
                        clientEngagementDTO.setUserTypeFlag(ApplicationConstants.USER_TYPE_ENGAGEMENT_LEAD);
                    } else if (userDto.getUserTypeObj().getLookUpEntityName().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_PARTNER)) {
                        clientEngagementDTO.setUserTypeFlag(ApplicationConstants.USER_TYPE_PARTNER_LEAD);
                    }
                } else {
                    if (userDto.getUserTypeObj().getLookUpEntityName().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_INTERNAL)) {
                        clientEngagementDTO.setUserTypeFlag(ApplicationConstants.USER_TYPE_ENGAGEMENT_ANALYST);
                    }
                    if (userDto.getUserTypeObj().getLookUpEntityName().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_PARTNER)) {
                        clientEngagementDTO.setUserTypeFlag(ApplicationConstants.USER_TYPE_PARTNER_USER);
                    }
                }
            }
            clientEngagementDTO.setUploadedFilesCount(ApplicationConstants.UPLOADED_FILES_EXISTING);
            engmntDataValidator.validate(clientEngagementDTO, result);
            
            if (result.hasErrors()) {
                clientEngagementDTO.setEngagementCode(encDec.decrypt(clientEngagementDTO.getEncEngagementCode()));
                clientEngagementDTO.setSecurityServiceCode(encDec.decrypt(clientEngagementDTO.getEncSecurityServiceCode()));
                clientEngagementDTO.setEncS(clientEngagementDTO.getEncS());
                redirectAttributes.addFlashAttribute("uploadFileDetails", clientEngagementDTO);
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.uploadFileDetails", result);

                return "redirect:/engagemenetDataUpload.htm";
            }

            clientEngagementDTO.setEngagementCode(encDec.decrypt(clientEngagementDTO.getEncEngagementCode()));
            clientEngagementDTO.setSecurityServiceCode(encDec.decrypt(clientEngagementDTO.getEncSecurityServiceCode()));
            clientEngagementDTO.setCreatedByUserID(userDto.getUserProfileKey());

//            if (clientEngagementDTO.isLockCheckbox()) {
//                serviceDataLockIndicator = ApplicationConstants.DB_CONSTANT_FILE_LOCK_INDICATOR;
//
//            } else {
//                serviceDataLockIndicator = ApplicationConstants.DB_CONSTANT_FILE_UNLOCK_INDICATOR;
//            }
//            int retVal = EngagementDataUploadService.updateLockUnlockServiceData(clientEngagementDTO.getEngagementCode(),
//                    clientEngagementDTO.getSecurityServiceCode(), serviceDataLockIndicator);
            ClientEngagementDTO clientEngmntDTO = EngagementDataUploadService.fetchServiceEngagementDetailsByEgmntCode(clientEngagementDTO.getEngagementCode(),
                    clientEngagementDTO.getSecurityServiceCode(), clientEngagementDTO.getCreatedByUserID());
            clientEngagementDTO.setClientID(clientEngmntDTO.getClientID());
            clientEngagementDTO.setClientName(clientEngmntDTO.getClientName());
            clientEngagementDTO.setSecurityServiceName(clientEngmntDTO.getSecurityServiceName());
            
            int retVal = 0;
            int scanInProgFileCount = EngagementDataUploadService.scanInProgressFileCount(clientEngagementDTO);
            
            
            if(lockStat == false){
                retVal = EngagementDataUploadService.updateLockUnlockServiceData(clientEngagementDTO, userDto);
            } else if(lockStat == true){
                if(scanInProgFileCount == 0){
                   retVal = EngagementDataUploadService.updateLockUnlockServiceData(clientEngagementDTO, userDto);
                } else {
                    redirectAttributes.addFlashAttribute("lockDeleteMessage", myProperties.getProperty("sericelock.message"));

                    clientEngagementDTO.setEngagementCode(encDec.decrypt(clientEngagementDTO.getEncEngagementCode()));
                    clientEngagementDTO.setSecurityServiceCode(encDec.decrypt(clientEngagementDTO.getEncSecurityServiceCode()));
                    clientEngagementDTO.setEncS(clientEngagementDTO.getEncS());
                    //clientEngagementDTO.setLockCheckbox(lockStat);
                    redirectAttributes.addFlashAttribute("uploadFileDetails", clientEngagementDTO);
                    redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.uploadFileDetails", result);

                    return "redirect:/engagemenetDataUpload.htm";
                }
            }
            /*
             * ADDED THE BELOW 'IF' CONDITION TO CHECK WHETHER THERE IS A CHANGE IN THE SERIVE LOCK.
             * I.E., FROM LOCKED TO UNLOCKED OR UNLOCKED TO UNLOCKED. 
             */
            if (clientEngmntDTO.isLockCheckbox() != clientEngagementDTO.isLockCheckbox()) {
                if (retVal == ApplicationConstants.DB_CONSTANT_FILE_LOCK_INDICATOR) {
                    //request.setAttribute("successMessage", myProperties.getProperty("servicedatalock.success-message"));
                    redirectAttributes.addFlashAttribute("successMessage", myProperties.getProperty("servicedatalock.success-message"));
                } else {
                    redirectAttributes.addFlashAttribute("successMessage", myProperties.getProperty("servicedataunlock.success-message"));
                }
            }
            
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the lockUnlockServiceData Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            //return new ModelAndView("/exception", "exceptionBean", exception);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
//        modelAndView.setViewName("redirect:/engUploadWorkList.htm");
//        return modelAndView;
        return "redirect:/engUploadWorkList.htm";

    }

    /**
     * METHOD TO VIEW ENGAGEMENT SERVICE DETAILS
     *
     * @param request
     * @param response
     * @param clientEngagementDTO
     * @param redirectAttributes
     * @param model
     * @return ModelAndView
     */
    @RequestMapping(value = "viewSericeData")
    public ModelAndView viewServiceDataDetails(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute("uploadFileDetails") ClientEngagementDTO clientEngagementDTO, RedirectAttributes redirectAttributes, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        List<ClientEngagementDTO> uploadedFilesList = new ArrayList();
        Set<String> clientEngmntPermissionSet = new HashSet<>();
        Set<String> engmntDataUploadPermissionSet = new HashSet<>();
        Set<String> allPermissionSet = new HashSet<>();
        try {
            HttpSession session = request.getSession();
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return new ModelAndView("redirect:/logout.htm");
            }
            UserProfileDTO userDto = new UserProfileDTO();
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userDto.getEmail(), userDto.getUserMACAddress(), " User accessed viewServiceDataDetails page"));
                logger.trace(TRACE_USER_VIEW_SERVICE_DATA);
                engmntDataUploadPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.ENGAGEMENT_DATA_UPLOAD_MENU);
                clientEngmntPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU);
            }
            if (clientEngmntPermissionSet != null) {
                allPermissionSet.addAll(clientEngmntPermissionSet);
            }
            if (engmntDataUploadPermissionSet != null) {
                allPermissionSet.addAll(engmntDataUploadPermissionSet);
            }

            String reqRand = "";
            if (session.getAttribute("requestRandom") != null) {
                reqRand = (String) session.getAttribute("requestRandom");
              //  session.setAttribute("requestRandom", null);
            }
            if (!StringUtils.isEmpty(clientEngagementDTO.getEncS())) {
                String encString = encDec.decrypt(clientEngagementDTO.getEncS());
                if (encString.contains("@")) {
                    String arrReqParams[] = encString.split("@");
                    if (arrReqParams.length == 3) {
                        String reqToken = arrReqParams[2];
                        if (reqRand.equalsIgnoreCase(reqToken)) {
                            clientEngagementDTO.setEngagementCode(arrReqParams[0]);
                            clientEngagementDTO.setSecurityServiceCode(arrReqParams[1]);
                            clientEngagementDTO.setCreatedByUserID(userDto.getUserProfileKey());

                            clientEngagementDTO = EngagementDataUploadService.fetchServiceEngagementDetailsByEgmntCode(clientEngagementDTO.getEngagementCode(),
                                    clientEngagementDTO.getSecurityServiceCode(), clientEngagementDTO.getCreatedByUserID());
                            clientEngagementDTO.setCreatedByUserID(userDto.getUserProfileKey());
                            if (!allPermissionSet.isEmpty()) {
                                if (allPermissionSet.contains(PermissionConstants.ADD_USER_TO_SERVICE) || allPermissionSet.contains(PermissionConstants.EDIT_USER_TO_SERVICE)) {
                                    if (userDto.getUserTypeObj().getLookUpEntityName().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_INTERNAL)) {
                                        clientEngagementDTO.setUserTypeFlag(ApplicationConstants.USER_TYPE_ENGAGEMENT_LEAD);
                                    } else if (userDto.getUserTypeObj().getLookUpEntityName().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_PARTNER)) {
                                        clientEngagementDTO.setUserTypeFlag(ApplicationConstants.USER_TYPE_PARTNER_LEAD);
                                    }
                                } else {
                                    if (userDto.getUserTypeObj().getLookUpEntityName().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_INTERNAL)) {
                                        clientEngagementDTO.setUserTypeFlag(ApplicationConstants.USER_TYPE_ENGAGEMENT_ANALYST);
                                    }
                                    if (userDto.getUserTypeObj().getLookUpEntityName().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_PARTNER)) {
                                        clientEngagementDTO.setUserTypeFlag(ApplicationConstants.USER_TYPE_PARTNER_USER);
                                    }
                                }
                            }
                            uploadedFilesList = EngagementDataUploadService.fetchUploadedFilesDetails(clientEngagementDTO);

                            modelAndView.addObject("uploadedFilesList", uploadedFilesList);
                            modelAndView.addObject("clientEngagementDTO", clientEngagementDTO);
                        } else {
                            logger.debug("URL Parameter Tampered ");
                            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message-badreq"));
                            return new ModelAndView("/exception", "exceptionBean", exception);
                        }
                    } else {
                        logger.debug("URL Parameter Tampered ");
                        ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message-badreq"));
                        return new ModelAndView("/exception", "exceptionBean", exception);
                    }
                } else {
                    logger.debug("URL Parameter Tampered ");
                    ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message-badreq"));
                    return new ModelAndView("/exception", "exceptionBean", exception);
                }
            } else {
                logger.debug("URL Parameter Tampered ");
                ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message-badreq"));
                return new ModelAndView("/exception", "exceptionBean", exception);
            }

        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the viewServiceDataDetails Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }

        modelAndView.setViewName("/engagementdataupload/viewEngagement");
        return modelAndView;
    }

    /**
     * ENGAGEMENT DATA UPLOAD WORK LIST
     *
     * @param request
     * @param response
     * @param redirectAttributes
     * @param model
     * @return ModelAndView
     */
    @RequestMapping(value = "/engUploadWorkList")
    public ModelAndView engagementDataUploadWorkList(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,
            Model model) {
        ModelAndView modelAndView = new ModelAndView();
        try {

            HttpSession session = request.getSession();
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return new ModelAndView("redirect:/logout.htm");
            }
            session.setAttribute("requestRandom", null);
            UserProfileDTO userDto = new UserProfileDTO();
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userDto.getEmail(), userDto.getUserMACAddress(), " User accessed engagement DataUploadWorkList page"));
                logger.trace(TRACE_USER_ENG_UPLOAD_WORKLIST);

                Set<String> uploadpermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.ENGAGEMENT_DATA_UPLOAD_MENU);
                Set<String> engagementPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_ENGAGEMENTS_SUB_MENU);
                Set<String> permissionSet = new HashSet<>();
                if (uploadpermissionSet != null) {
                    permissionSet.addAll(uploadpermissionSet);
                }
                if (engagementPermissionSet != null) {
                    permissionSet.addAll(engagementPermissionSet);
                }
                modelAndView.addObject("permissionSet", permissionSet);
            }
            String reqToken = generateRandom.generateToken();

            Map map = EngagementDataUploadService.engagementDataUploadWorkList(userDto, reqToken);
            modelAndView.addObject("engagementMap", map.get("engagementMap"));
            modelAndView.addObject("serviceMap", map.get("serviceMap"));
            modelAndView.addObject("organizationServieCount", map.get("organizationServieCount"));
            session.setAttribute("requestRandom", reqToken);

        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the engagementUploadDataWorkList Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        modelAndView.setViewName("/engagementdataupload/engagementDataUploadWorkList");
        return modelAndView;
    }

    /**
     * ENGAGEMENT DATA UPLOAD SERARCH WORK LIST
     *
     * @param request
     * @param response
     * @param manageServiceUserDTO
     * @param redirectAttributes
     * @param model
     * @return ModelAndView
     */
    @RequestMapping(value = "/searchEngServiceWorkList")
    public ModelAndView searchEngagementServicesWorkList(@ModelAttribute(value = "manageServiceUserDTO") ManageServiceUserDTO manageServiceUserDTO,
            HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        try {
           
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return new ModelAndView("redirect:/logout.htm");
            }
            if (request.getParameter("encS") != null) {
                HttpSession session = request.getSession();
                UserProfileDTO userDto = new UserProfileDTO();
                if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                    userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);

//                logger.trace(auditLogger.prepareLog(userDto.getEmail(), userDto.getUserMACAddress(), " User accessed searchEngagementServicesWorkList page"));
                    logger.trace(TRACE_USER_SEARCH_ENG_SERVICE_WORKLIST);
                    Set<String> uploadpermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.ENGAGEMENT_DATA_UPLOAD_MENU);
                    Set<String> engagementPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_ENGAGEMENT_USERS_SUB_MENU);
                    Set<String> permissionSet = new HashSet<>();
                    if (uploadpermissionSet != null) {
                        permissionSet.addAll(uploadpermissionSet);
                    }
                    if (engagementPermissionSet != null) {
                        permissionSet.addAll(engagementPermissionSet);
                    }
                    modelAndView.addObject("permissionSet", permissionSet);
                }
                String reqToken = generateRandom.generateToken();
                Map map = EngagementDataUploadService.searchEngagementServicesWorkList(userDto, manageServiceUserDTO, reqToken);

                modelAndView.addObject("engagementMap", map.get("engagementMap"));
                modelAndView.addObject("serviceMap", map.get("serviceMap"));
                modelAndView.addObject("organizationServieCount", map.get("organizationServieCount"));
                modelAndView.addObject("manageServiceUserDTO", manageServiceUserDTO);
                session.setAttribute("requestRandom", reqToken);
            } else {
                return new ModelAndView("redirect:/logout.htm");
            }
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the searchEngagementServicesWorkList Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        modelAndView.setViewName("/engagementdataupload/engagementDataUploadWorkList");
        return modelAndView;
    }

    /**
     * ENGAGEMENT DATA UPLOAD VALIDATE UPLOAD FILE NAME
     *
     * @param request
     * @param appFileName
     * @param engmntCode
     * @param serviceCode
     * @return ModelAndView
     */
//    @RequestMapping(value = "/validatefilename")
//    public @ResponseBody
//    String validateApFileName(final HttpServletRequest request, @RequestParam("appFileName") String appFileName,
//            @RequestParam("engmntCode") String engmntCode, @RequestParam("serviceCode") String serviceCode) {
//        logger.info("Start: validateApFileName() ");
//        String message = "";
//        try {
//            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
//                UserProfileDTO userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
//                logger.trace(auditLogger.prepareLog(userDto.getEmail(), userDto.getUserMACAddress(), " User accessed validateApFileName page"));
//            }
//            int appRoleNameCheck = EngagementDataUploadService.validateDuplicateFileName(appFileName, engmntCode, serviceCode);
//            if (appRoleNameCheck > 0) {
//                message = myProperties.getProperty("appfile.name.check");
//            } else {
//                message = "No";
//            }
//
//        } catch (AppException e) {
//            logger.error("Exceptionoccured : validateApFileName: " + e.getMessage());
//        }
//        return message;
//    }
}
