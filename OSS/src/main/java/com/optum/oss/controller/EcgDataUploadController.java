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
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.service.impl.EngagementDataUploadServiceImpl;
import com.optum.oss.service.impl.MasterLookUpServiceImpl;
import com.optum.oss.util.CheckSecureFileCopy;
import com.optum.oss.util.CommonUtil;
import com.optum.oss.util.EncryptDecrypt;
import com.optum.oss.util.GenerateRandomNumber;
import com.optum.oss.util.LoggerUtil;
import com.optum.oss.validators.EngagementDataUploadValidator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.annotation.Resource;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
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
import org.apache.commons.io.IOUtils;
/**
 *
 * @author akeshavulu
 */
@Controller
public class EcgDataUploadController {

    private static final Logger logger = Logger.getLogger(EcgDataUploadController.class);

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
    private EngagementDataUploadValidator engmntDataValidator;

    @Autowired
    private CheckSecureFileCopy chkSecureFile;
    
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

    @InitBinder("uploadFileDetails")
    private void saveUploadedFileDetailsBinder(WebDataBinder binder)
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
     * SAVE THE DETAILS OF UPLOADED FILE
     *
     * @param request
     * @param response
     * @param clientEngagementDTO
     * @return ModelAndView
     */
    @RequestMapping(value = "/saveUploadFile")
    public String saveUploadedFileDetails(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute(value = "uploadFileDetails") ClientEngagementDTO clientEngagementDTO,
            BindingResult result, RedirectAttributes redirAttrs, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        List<ClientEngagementDTO> uploadedFilesList = new ArrayList();
        String fileName = "";
        String taskFlpathDB = "";
        String taskFlpathActLoc = "";
        String destFolder = "";
        String ecgUpload = "NO";
        long fileSize = 0;
        Set<String> clientEngmntPermissionSet = new HashSet<>();
        Set<String> engmntDataUploadPermissionSet = new HashSet<>();
        Set<String> allPermissionSet = new HashSet<>();
        try {

            HttpSession session = request.getSession();
            if(!commonUtil.refreshCheck(request, redirAttrs, model)){
                return "redirect:/logout.htm";
            }
            UserProfileDTO userDto = new UserProfileDTO();
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userDto.getEmail(), userDto.getUserMACAddress(), " User accessed saveUploadedFileDetails page"));
                logger.trace(TRACE_USER_SAVE_UPLOAD_FILE);
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
            clientEngagementDTO.setUploadedFilesCount(ApplicationConstants.UPLOADED_FILES_NOT_EXISTING);
            engmntDataValidator.validate(clientEngagementDTO, result);

            if (result.hasErrors()) {
                clientEngagementDTO.setEngagementCode(encDec.decrypt(clientEngagementDTO.getEncEngagementCode()));
                clientEngagementDTO.setSecurityServiceCode(encDec.decrypt(clientEngagementDTO.getEncSecurityServiceCode()));
                model.addAttribute("encryptedEngagementCode", clientEngagementDTO.getEncEngagementCode());
                model.addAttribute("encryptedSecurityServiceCode", clientEngagementDTO.getEncSecurityServiceCode());

                redirAttrs.addFlashAttribute("uploadFileDetails", clientEngagementDTO);
                redirAttrs.addFlashAttribute("org.springframework.validation.BindingResult.uploadFileDetails", result);

                return "redirect:/engagemenetDataUpload.htm?encS=" + clientEngagementDTO.getEncS();
            }

            clientEngagementDTO.setEngagementCode(encDec.decrypt(clientEngagementDTO.getEncEngagementCode()));
            clientEngagementDTO.setSecurityServiceCode(encDec.decrypt(clientEngagementDTO.getEncSecurityServiceCode()));
            clientEngagementDTO.setCreatedByUserID(userDto.getUserProfileKey());
            String toEncS = clientEngagementDTO.getEngagementCode() + "@" + clientEngagementDTO.getSecurityServiceCode();
            model.addAttribute("encryptedEngagementCode", clientEngagementDTO.getEncEngagementCode());
            model.addAttribute("encryptedSecurityServiceCode", clientEngagementDTO.getEncSecurityServiceCode());
            clientEngagementDTO.setCreatedByUserID(userDto.getUserProfileKey());
            ClientEngagementDTO clientEngmntDTO = EngagementDataUploadService.fetchServiceEngagementDetailsByEgmntCode(clientEngagementDTO.getEngagementCode(),
                    clientEngagementDTO.getSecurityServiceCode(), clientEngagementDTO.getCreatedByUserID());
            if (allPermissionSet != null) {
                if (allPermissionSet.contains(PermissionConstants.ADD_USER_TO_SERVICE) || allPermissionSet.contains(PermissionConstants.EDIT_USER_TO_SERVICE)) {
                    if (userDto.getUserTypeObj().getLookUpEntityName().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_INTERNAL)) {
                        clientEngmntDTO.setUserTypeFlag(ApplicationConstants.USER_TYPE_ENGAGEMENT_LEAD);
                    } else if (userDto.getUserTypeObj().getLookUpEntityName().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_PARTNER)) {
                        clientEngmntDTO.setUserTypeFlag(ApplicationConstants.USER_TYPE_PARTNER_LEAD);
                    }
                } else {
                    if (userDto.getUserTypeObj().getLookUpEntityName().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_INTERNAL)) {
                        clientEngmntDTO.setUserTypeFlag(ApplicationConstants.USER_TYPE_ENGAGEMENT_ANALYST);
                    }
                    if (userDto.getUserTypeObj().getLookUpEntityName().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_PARTNER)) {
                        clientEngmntDTO.setUserTypeFlag(ApplicationConstants.USER_TYPE_PARTNER_USER);
                    }
                }
            }
            String reqToken = "";
            if (session.getAttribute("requestRandom") != null) {
                reqToken = (String) session.getAttribute("requestRandom");
                //session.setAttribute("requestRandom", null);
            }
            String encS = toEncS + "@" + reqToken;
            clientEngmntDTO.setEncS(encDec.encrypt(encS));

            fileName = clientEngagementDTO.getTemplateupload().getOriginalFilename();
            destFolder = System.getProperty("OSS_UPLOAD_FOLDER");
            fileSize = clientEngagementDTO.getTemplateupload().getSize();
            /*ADDED CODE FOR PATH MANIPULATION SECURITY VULNERABILITY*/
            if (destFolder.contains("..\\")) {
                destFolder = destFolder.replace("..\\", "");
            }
            if (destFolder.contains("../")) {
                destFolder = destFolder.replace("../", "");
            }
            if (destFolder.contains("./")) {
                destFolder = destFolder.replace("./", "");
            }

            
                File convFile = new File(clientEngagementDTO.getTemplateupload().getOriginalFilename());
                convFile.createNewFile();
                FileOutputStream fos = new FileOutputStream(convFile);
                IOUtils.copy(clientEngagementDTO.getTemplateupload().getInputStream(), fos);
                //fos.write(clientEngagementDTO.getTemplateupload().getBytes());
                fos.close();

                //    InputStream is = clientEngagementDTO.getTemplateupload().getInputStream();
                if (!"".equals(fileName) && fileName != null) {

                    taskFlpathDB = File.separator + clientEngmntDTO.getClientName() + File.separator
                            + clientEngagementDTO.getEngagementCode() + File.separator + clientEngagementDTO.getSecurityServiceCode();

                    taskFlpathActLoc = destFolder + File.separator + clientEngmntDTO.getClientName() + File.separator
                            + clientEngagementDTO.getEngagementCode() + File.separator + clientEngagementDTO.getSecurityServiceCode();

                    /*ADDED CODE FOR PATH MANIPULATION SECURITY VULNERABILITY*/
                    if (taskFlpathActLoc.contains("..\\")) {
                        taskFlpathActLoc = taskFlpathActLoc.replace("..\\", "");
                    }
                    if (taskFlpathActLoc.contains("../")) {
                        taskFlpathActLoc = taskFlpathActLoc.replace("../", "");
                    }
                    if (taskFlpathActLoc.contains("./")) {
                        taskFlpathActLoc = taskFlpathActLoc.replace("./", "");
                    }
                    if (taskFlpathActLoc.contains("..")) {
                        taskFlpathActLoc = taskFlpathActLoc.replace("..", "");
                    }
                    /*ADDED CODE FOR PATH MANIPULATION SECURITY VULNERABILITY*/

                    if (!(new File(taskFlpathActLoc).exists())) {
                        // Create one directory
                        boolean success = (new File(taskFlpathActLoc)).mkdirs();
                    }
                    taskFlpathDB += File.separator + fileName;
                    taskFlpathActLoc += File.separator + fileName;
                    clientEngagementDTO.setUploadFilePath(taskFlpathDB);
                    clientEngagementDTO.setUploadFileName(fileName);
//                File desfile = new File(taskFlpathActLoc);
//                clientEngagementDTO.getTemplateupload().transferTo(desfile);
                    if (System.getProperty("ECG_UPLOAD") != null) {
                        ecgUpload = System.getProperty("ECG_UPLOAD");
                    }
//                    if ("YES".equalsIgnoreCase(ecgUpload)) {
//                        sendPost(fileName, convFile, clientEngagementDTO.getEngagementCode());
//                    } else {
//                        File desfile = new File(taskFlpathActLoc);
//                        clientEngagementDTO.getTemplateupload().transferTo(desfile);
//                    }
                    clientEngagementDTO.setClientID(clientEngmntDTO.getClientID());
                    clientEngagementDTO.setClientName(clientEngmntDTO.getClientName());
                    clientEngagementDTO.setSecurityServiceName(clientEngmntDTO.getSecurityServiceName());
                    clientEngagementDTO.setUploadFileSize(fileSize);
                    // code to check if there is a file with same name
                    int appRoleNameCheck = EngagementDataUploadService.validateDuplicateFileName(fileName, clientEngagementDTO.getEngagementCode(),
                            clientEngagementDTO.getSecurityServiceCode());
                    if (appRoleNameCheck > 0) {
//                    modelAndView.addObject("fileNameExists", myProperties.getProperty("appfile.name.check"));
                        model.addAttribute("fileNameExists", myProperties.getProperty("appfile.name.check"));
                    } else {
                        int retVal = EngagementDataUploadService.saveUploadedFileDetails(clientEngagementDTO, userDto,fileName);
                        if (retVal > 0) {
                            if ("YES".equalsIgnoreCase(ecgUpload)) {
                                sendPost(fileName, convFile, clientEngagementDTO.getEngagementCode(),retVal);
                            } else {
                                File desfile = new File(taskFlpathActLoc);
                                clientEngagementDTO.getTemplateupload().transferTo(desfile);
                            }
                            request.setAttribute("successMessage", myProperties.getProperty("fileupload.success-message"));
                            //   redirectAttributes.addFlashAttribute("successMessage", myProperties.getProperty("fileupload.success-message"));
                            request.setAttribute("saveFlag", ApplicationConstants.SAVE_FLAG);
                        }
//                    modelAndView.addObject("fileNameExists", "");
                        model.addAttribute("fileNameExists", "");
                    }
                }
                uploadedFilesList = EngagementDataUploadService.fetchUploadedFilesDetails(clientEngmntDTO);
                if (null != uploadedFilesList) {
//                modelAndView.addObject("uploadedFilesListSize", uploadedFilesList.size());
                    model.addAttribute("uploadedFilesListSize", uploadedFilesList.size());
                }
//            modelAndView.addObject("uploadedFilesList", uploadedFilesList);
                model.addAttribute("uploadedFilesList", uploadedFilesList);

                List<MasterLookUpDTO> sourceNameList = masterLookUpService.fetchMasterLookUpEntitiesByEntityType(ApplicationConstants.DB_CONSTANT_FINDING_SOURCE_NAME);
                List<MasterLookUpDTO> documentTypeList = masterLookUpService.fetchMasterLookUpEntitiesByEntityType(ApplicationConstants.DB_CONSTANT_DOCUMENT_TYPE);

                model.addAttribute("clientEngagementDTO", clientEngmntDTO);
                model.addAttribute("sourceNameList", sourceNameList);
                model.addAttribute("documentTypeList", documentTypeList);
                model.addAttribute("engmntDataUploadPermissionSet", engmntDataUploadPermissionSet);
                model.addAttribute("clientEngmntPermissionSet", clientEngmntPermissionSet);
                model.addAttribute("allPermissionSet", allPermissionSet);
                return "/engagementdataupload/engagementdataupload";

        } catch (Exception e) {
            logger.debug("Exception Occured while Executing the saveUploadedFileDetails Action :: " + e.getMessage());
           // ExceptionDTO exception = new ExceptionDTO("", myProperties.getProperty("failure-message"), e);
            //return new ModelAndView("/exception", "exceptionBean", exception);
           // model.addAttribute("exceptionBean", exception);
            return "redirect:/logout.htm";
        }
//        modelAndView.setViewName("/engagementdataupload/engagementdataupload");
//        return modelAndView;
    }

    private void disableSslVerification() {
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }

            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Exception occurred : disableSslVerification Failed:" + e.getMessage());
        } catch (KeyManagementException e) {
            logger.error("Exception occurred : disableSslVerification Failed:" + e.getMessage());
        }
    }

    private void sendPost(final String fileName, final File convFile, String engmntCode, int fileID) throws Exception {

        disableSslVerification();
        final String USER_AGENT = "Mozilla/5.0";
        String url = "";
        if (!StringUtils.isEmpty(ApplicationConstants.ECG_UPLOAD_URL)
                && !StringUtils.isEmpty(ApplicationConstants.ECG_UPLOAD_PORT)) {
            url = ApplicationConstants.ECG_UPLOAD_URL.trim() + ":" + ApplicationConstants.ECG_UPLOAD_PORT.trim();

//            String remoteFileName = "/in/" + engmntCode + ApplicationConstants.ECG_FILENAME_SEPERATOR + fileName;
            String remoteFileName = "/in/" + fileID + ApplicationConstants.ECG_FILENAME_SEPERATOR + fileName;

            URL obj = new URL(url + remoteFileName);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            Thread.sleep(3000);
            // add request header
            con.setRequestProperty("User-Agent", USER_AGENT);
            String userpass = ApplicationConstants.ECG_UPLOAD_USER.trim() + ":" + ApplicationConstants.ECG_UPLOAD_PSWD.trim();
            String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
            con.setRequestProperty("Authorization", basicAuth);

            String boundaryString = "===" + System.currentTimeMillis() + "===";

            // Indicate that we want to write to the HTTP request body
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.addRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundaryString);

            // Indicate that we want to write some data as the HTTP request body
            con.setDoOutput(true);

            OutputStream outputStreamToRequestBody = con.getOutputStream();
            BufferedWriter httpRequestBodyWriter = new BufferedWriter(new OutputStreamWriter(outputStreamToRequestBody));

            httpRequestBodyWriter.write("\n--" + boundaryString + "\n");
            httpRequestBodyWriter.write("Content-Disposition: form-data;" + "name=\"" + remoteFileName + "\";" + "filename=\""
//                    + engmntCode + ApplicationConstants.ECG_FILENAME_SEPERATOR + fileName + "\"" + "\nContent-Type: binary\n\n");
                    + fileID + ApplicationConstants.ECG_FILENAME_SEPERATOR + fileName + "\"" + "\nContent-Type: binary\n\n");
            httpRequestBodyWriter.flush();

            // Write the actual file contents
            FileInputStream inputStreamToLogFile = new FileInputStream(convFile);

            int bytesRead;
            byte[] dataBuffer = new byte[1024];
            while ((bytesRead = inputStreamToLogFile.read(dataBuffer)) != -1) {
                outputStreamToRequestBody.write(dataBuffer, 0, bytesRead);
            }

            outputStreamToRequestBody.flush();

            // Mark the end of the multipart http request
            httpRequestBodyWriter.write("\n--" + boundaryString + "--\n");
            httpRequestBodyWriter.flush();

            // Close the streams
            outputStreamToRequestBody.close();
            httpRequestBodyWriter.close();

            int responseCode = con.getResponseCode();
            logger.info("\nSending 'POST' request to URL : " + url);
            logger.info("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            inputStreamToLogFile.close();
            in.close();
        }

        // print result
        // System.out.println(response.toString());
    }

}
