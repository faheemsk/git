/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.controller;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.constants.PermissionConstants;
import com.optum.oss.constants.ReportsAndDashboardConstants;
import com.optum.oss.constants.SessionConstants;
import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.dto.ClientReportsUploadDTO;
import com.optum.oss.dto.ExceptionDTO;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.PermissionCheckHelper;
import com.optum.oss.service.impl.ManageEngagementsServiceImpl;
import com.optum.oss.service.impl.ReportsServiceImpl;
import com.optum.oss.util.CommonUtil;
import com.optum.oss.util.DateUtil;
import com.optum.oss.util.EncryptDecrypt;
import com.optum.oss.validators.ClientReportsUploadValidator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
//import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author mrejeti
 */
@Controller
public class ReportsController {

    private static final Logger logger = Logger.getLogger(ReportsController.class);
    @Resource(name = "myProperties")
    private Properties myProperties;
    /*   
     logger messages configuration
     */
    private final String TRACE_RPRTS_UPLD_WRK_LIST = "User accessed client reports upload page";

    @Autowired
    private ReportsServiceImpl reportsService;

    @Autowired
    private ManageEngagementsServiceImpl manageEngagementsService;

    @Autowired
    PermissionCheckHelper permissionCheckHelper;

    @Autowired
    private EncryptDecrypt encDec;

    @Autowired
    private ClientReportsUploadValidator CliReportsValidator;
    
    @Autowired
    private DateUtil dateUtil;
    
    @Autowired
    private CommonUtil commonUtil;

    /**
     *
     * @param request
     * @param response
     * @param redirectAttributes
     * @param model
     * @return
     */
    @RequestMapping(value = "reportsuploadworklist")
    public ModelAndView reportsUploadWorklistPage(HttpServletRequest request, HttpServletResponse response,
            RedirectAttributes redirectAttributes, Model model) {
        logger.info(TRACE_RPRTS_UPLD_WRK_LIST);
        ModelAndView modelView = new ModelAndView();
        UserProfileDTO userDto = new UserProfileDTO();
        Set<String> permissionSet = null;
        ClientReportsUploadDTO clientReportsUploadDTO = new ClientReportsUploadDTO();

        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return new ModelAndView("redirect:/logout.htm");
            }
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(TRACE_RPRTS_UPLD_WRK_LIST);
                //  Map reportsWorkList = reportsServiceImpl.fetchClientReportsUploadWorkList(userDto);

                Map map = reportsService.fetchClientReportsUploadWorkList(userDto, clientReportsUploadDTO);
                permissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.CLIENT_REPORTS_UPLOAD);
                modelView.addObject("permissions", permissionSet);
                modelView.addObject("engagementMap", map.get("engagementMap"));
                modelView.addObject("serviceMap", map.get("serviceMap"));
                modelView.addObject("organizationServieCount", map.get("organizationServieCount"));
            } else {
                modelView.setViewName("redirect:/logout.htm");
            }
        } catch (Exception e) {
            logger.error("Exception occurred : ReportsController: reportsUploadWorklistPage:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        //clientreportsuploadsworklist
        modelView.setViewName("reportsanddashboards/clientreportsuploadsworklist");
        return modelView;
    }

    @InitBinder("reportworklist")
    protected void searchClientuploadworklist(WebDataBinder binder)
            throws Exception {
        binder.setAllowedFields(new String[]{"organizationName",
            "clientEngagementCode", "clientEngagementName", "reportName", "fileName", "updateDate", "status"});
    }

    /**
     * This method for search analyst validation work list
     *
     * @param request
     * @param response
     * @param reportworklist
     * @param redirectAttributes
     * @param model
     * @return modelView
     */
    @RequestMapping(value = "/searchclientreportuploadworklist")
    public ModelAndView searchclientreportuploadworklist(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute(value = "reportworklist") ClientReportsUploadDTO reportworklist,
            RedirectAttributes redirectAttributes, Model model) {
        ModelAndView modelView = new ModelAndView();
        UserProfileDTO userDto = new UserProfileDTO();
        Set<String> clientPermissionSet = null;

        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return new ModelAndView("redirect:/logout.htm");
            }

            if (request.getParameter("ec") != null) {
                HttpSession session = request.getSession();
                if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                    userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                    MDC.put("user", userDto.getEmail());
                    MDC.put("ip", userDto.getUserMACAddress());
                    clientPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.CLIENT_REPORTS_UPLOAD);

                    modelView.addObject("permissions", clientPermissionSet);

                    Map analystWorklist = reportsService.searchClientReportsUploadWorkList(userDto, reportworklist);
                    modelView.addObject("engagementMap", analystWorklist.get("engagementMap"));
                    modelView.addObject("serviceMap", analystWorklist.get("serviceMap"));
                    modelView.addObject("organizationServieCount", analystWorklist.get("organizationServieCount"));
                    modelView.addObject("statusMap", analystWorklist.get("statusMap"));
                    modelView.addObject("searchClientReportWorklist", reportworklist);
                    modelView.setViewName("reportsanddashboards/clientreportsuploadsworklist");
                } else {
                    modelView.setViewName("redirect:/logout.htm");
                }
            } else {
                modelView.setViewName("redirect:/logout.htm");
            }
        } catch (AppException e) {
            logger.error("Exception occurred : ReportsController: searchclientreportuploadworklist:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        logger.info("End: ReportsController: searchclientreportuploadworklist() to search client report upload worklist");
        return modelView;
    }

    /**
     * This method for add client report upload
     *
     * @param request
     * @param response
     * @param ec
     * @param model
     * @param fileNameExists
     * @param redirectAttributes
     * @return String
     */
    @RequestMapping(value = "/addReports")
    public String addReportupload(HttpServletRequest request, HttpServletResponse response, Model model,
            @ModelAttribute (value = "ec") String ec, @ModelAttribute (value = "fileNameExists") String fileNameExists,
            RedirectAttributes redirectAttributes) {
//        ModelAndView modelView = new ModelAndView();
        UserProfileDTO userDto = new UserProfileDTO();
        Set<String> clientPermissionSet = null;
        String engCode = "";

        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            
            if ((request.getParameter("ec") != null && !StringUtils.isEmpty(request.getParameter("ec"))) || !StringUtils.isEmpty(ec)) {
                if(!StringUtils.isEmpty(ec)) {
                    engCode = ec;
                } else {
                    engCode = request.getParameter("ec");
                }
                if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                    userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                    MDC.put("user", userDto.getEmail());
                    MDC.put("ip", userDto.getUserMACAddress());

                    if (model.containsAttribute("saveReports")) {
                        ClientReportsUploadDTO reportDto = (ClientReportsUploadDTO) model.asMap().get("saveReports");

//                    model.addAttribute("engStatusMessage", 
//                            model.asMap().get("engStatusMessage"));
//                    model.addAttribute("org.springframework.validation.BindingResult.saveReports", 
//                            model.asMap().get("org.springframework.validation.BindingResult.saveReports"));
                        model.addAttribute("saveReports", reportDto);
                    }
                    if (fileNameExists != null) {
                        String fileErr = fileNameExists;
                        model.addAttribute("fileNameExists", fileErr);
                    }

                    engCode = encDec.decrypt(engCode);
                    clientPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.CLIENT_REPORTS_UPLOAD);

                    model.addAttribute("permissions", clientPermissionSet);

                    ClientEngagementDTO clientEngagementDTO = manageEngagementsService.viewEngagementByEngmtKey(engCode);
                    model.addAttribute("clientEngagementDTO", clientEngagementDTO);

                    //For fetch Report Names drop down
                    List<MasterLookUpDTO> reportNames = reportsService.fetchReportNames(engCode);
                    model.addAttribute("reportNames", reportNames);

                    //For fetch upload file list
                    List<ClientReportsUploadDTO> uploadFileList = reportsService.fetchUploadFileList(engCode);
                    model.addAttribute("uploadFileList", uploadFileList);
                } else {
                    return "redirect:/logout.htm";
                }
            } else {
                return "redirect:/logout.htm";
            }
        } catch (Exception e) {
            logger.error("Exception occurred : ReportsController: addReportupload:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return "/exception";
        }
//        modelView.setViewName("reportsanddashboards/addreports");
        return "reportsanddashboards/addreports";

    }

    /**
     * This method for edit client report upload
     *
     * @param request
     * @param response
     * @param ec
     * @param model
     * @param redirectAttributes
     * @param fileNameExists
     * @return String
     */
    @RequestMapping(value = "/editReports")
    public String editReportupload(HttpServletRequest request, HttpServletResponse response,Model model,
            @ModelAttribute (value = "ec") String ec, @ModelAttribute (value = "fileNameExists") String fileNameExists,
            RedirectAttributes redirectAttributes) {
        //ModelAndView modelView = new ModelAndView();
        UserProfileDTO userDto = new UserProfileDTO();
        Set<String> clientPermissionSet = null;
        String engCode = "";

        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            
            if ((request.getParameter("ec") != null && !StringUtils.isEmpty(request.getParameter("ec"))) || !StringUtils.isEmpty(ec)) {
                if(!StringUtils.isEmpty(ec)) {
                    engCode = ec;
                } else {
                    engCode = request.getParameter("ec");
                }
                if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                    userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                    MDC.put("user", userDto.getEmail());
                    MDC.put("ip", userDto.getUserMACAddress());

                    if (model.containsAttribute("saveReports")) {
                        ClientReportsUploadDTO reportDto = (ClientReportsUploadDTO) model.asMap().get("saveReports");

//                    model.addAttribute("engStatusMessage", 
//                            model.asMap().get("engStatusMessage"));
//                    model.addAttribute("org.springframework.validation.BindingResult.saveReports", 
//                            model.asMap().get("org.springframework.validation.BindingResult.saveReports"));
                        model.addAttribute("saveReports", reportDto);
                    }
                    if (fileNameExists != null) {
                        String fileErr = fileNameExists;
                        model.addAttribute("fileNameExists", fileErr);
                    }
                    engCode = encDec.decrypt(engCode);
                    //
                    clientPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.CLIENT_REPORTS_UPLOAD);
                    model.addAttribute("permissions", clientPermissionSet);
                    //For fetch engagement information
                    ClientEngagementDTO clientEngagementDTO = manageEngagementsService.viewEngagementByEngmtKey(engCode);
                    model.addAttribute("clientEngagementDTO", clientEngagementDTO);

                    //For fetch Report Names drop down
                    List<MasterLookUpDTO> reportNames = reportsService.fetchReportNames(engCode);
                    model.addAttribute("reportNames", reportNames);

                    //For fetch upload file list
                    List<ClientReportsUploadDTO> uploadFileList = reportsService.fetchUploadFileList(engCode);
                    model.addAttribute("uploadFileList", uploadFileList);
                } else {
                    return "redirect:/logout.htm";
                }
            } else {
                return "redirect:/logout.htm";
            }
           
        } catch (Exception e) {
            logger.error("Exception occurred : ReportsController: editReports:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        //modelView.setViewName("reportsanddashboards/editreports");
        return "reportsanddashboards/editreports";

    }

    @InitBinder("saveReports")
    protected void saveUploadedFile(WebDataBinder binder)
            throws Exception {
        binder.setAllowedFields(new String[]{"reportkey", "encClientEngagementCode",
            "status", "templateupload", "reportNameCheckId", "redirectFlag", "organizationName", "clientEngagementName", "serverSideFlag", "reportName"});
    }

    @RequestMapping(value = "/downloadreportFile")
    public void clientreportsFiledownload(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "fp") String fp) {

        String filenamedocument = "";
        String pathfromdownload = "";

        String encFilePath = "";

        try {
            
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                UserProfileDTO userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userDto.getEmail(), userDto.getUserMACAddress(), " User accessed engagementDataFiledownload page"));
//                logger.trace(TRACE_USER_DOWNLOAD_FILE);

                String destFolder = System.getProperty("OSS_UPLOAD_FOLDER");
                if (!StringUtils.isEmpty(fp)) {
                    encFilePath = encDec.decrypt(fp);
                }
//                String arrReqParams[] = encFilePath.split("@");
//                filenamedocument = arrReqParams[0];
//                pathfromdownload = arrReqParams[1];
                pathfromdownload = encFilePath;

                // construct the complete absolute path of the file
                if (pathfromdownload != null) {
                    String fullPath = pathfromdownload;

                    File downloadFile = new File(fullPath);
                    String orginalDownloadFile = downloadFile.getName();
                    response.setHeader("Content-Disposition", "attachment;filename= \"" + orginalDownloadFile + "\"");
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
            }
        } catch (Exception ie) {
            logger.debug("Exception Occured while Executing the clientreportsFiledownload Action :: " + ie.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), ie);
            // return new ModelAndView("/exception", "exceptionBean", exception);
        }
    }

    @InitBinder("saveUploadReportFile")
    protected void deleteReport(WebDataBinder binder)
            throws Exception {
        binder.setAllowedFields(new String[]{"redirectFlag",
            "encUploadFileKey", "encClientEngagementCode"});
    }

    /**
     * This method for Delete report files
     *
     * @param request
     * @param response
     * @param clientReportsUploadDTO
     * @param model
     * @param redirectAttributes
     * @return modelAndView
     */
    @RequestMapping(value = "/deleteReportsUploadedFiles")
    public String deleteUploadedFileDetails(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute("saveUploadReportFile") ClientReportsUploadDTO clientReportsUploadDTO, Model model, RedirectAttributes redirectAttributes) {
        //ModelAndView modelAndView = new ModelAndView();
        List<ClientReportsUploadDTO> uploadedFilesList = new ArrayList<>();
        String retStr = "";
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            
            HttpSession session = request.getSession();
            UserProfileDTO userDto = new UserProfileDTO();
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                String uplofileKey = "";
                if (request.getParameter("encUploadFileKey") != null) {
                    uplofileKey = request.getParameter("encUploadFileKey") + "";

                }

                long uploadFilekey = Long.parseLong(encDec.decrypt(uplofileKey));

                int retval = reportsService.deleteUploadedFileDetails(uploadFilekey);
                model.addAttribute("uploadedFilesList", uploadedFilesList);
                if (!uploadedFilesList.isEmpty()) {
                    model.addAttribute("uploadedFilesListSize", uploadedFilesList.size());
                }
            } else {
                retStr = "redirect:/logout.htm";
            }
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the deleteUploadedFileDetails Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            //return new ModelAndView("/exception", "exceptionBean", exception);
            retStr = "/exception"; 
        }
        //modelAndView.setViewName("redirect:/editReports.htm?ec=" + clientReportsUploadDTO.getEncClientEngagementCode());
        if ("E".equalsIgnoreCase(clientReportsUploadDTO.getRedirectFlag())) {
            //modelAndView.setViewName("redirect:/editReports.htm?ec=" + clientReportsUploadDTO.getEncClientEngagementCode());
            redirectAttributes.addFlashAttribute("ec", clientReportsUploadDTO.getEncClientEngagementCode());
            retStr = "redirect:/editReports.htm";
        } else if ("A".equalsIgnoreCase(clientReportsUploadDTO.getRedirectFlag())) {
            //modelAndView.setViewName("redirect:/addReports.htm?ec=" + clientReportsUploadDTO.getEncClientEngagementCode());
            redirectAttributes.addFlashAttribute("ec", clientReportsUploadDTO.getEncClientEngagementCode());
            retStr = "redirect:/addReports.htm";
        }

        // modelAndView.setViewName("/reportsanddashboards/addreports");
        return retStr;

    }

    /**
     * This method for Delete report files
     *
     * @param request
     * @param response
     * @param saveReports
     * @param model
     * @param redirectAttributes
     * @param result
     * @return String
     */
    @RequestMapping(value = "/publishReportFile", method = RequestMethod.POST)
    public String publishReportFile(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute("saveReports") ClientReportsUploadDTO saveReports, Model model, 
            RedirectAttributes redirectAttributes, BindingResult result) {
        //ModelAndView modelView = new ModelAndView();
        UserProfileDTO userDto = new UserProfileDTO();
        HttpSession session = request.getSession();
        String redirectPage = "";
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            
            CliReportsValidator.validate(saveReports, result);
            if (result.hasErrors()) {
                redirectAttributes.addFlashAttribute("saveReports", saveReports);
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.saveReports", result);
                redirectAttributes.addAttribute("ec", saveReports.getEncClientEngagementCode());
                if ("A".equalsIgnoreCase(saveReports.getRedirectFlag())) {
                    return "redirect:/addReports.htm";
                } else if ("E".equalsIgnoreCase(saveReports.getRedirectFlag())) {
                    return "redirect:/editReports.htm";
                }
            }

            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
            }
            String notPublished[] = saveReports.getStatus().split("@");
            int retval = reportsService.publishReportFile(saveReports, userDto);
            if (retval == 1 && StringUtils.isEmpty(saveReports.getStatus())) {
                redirectAttributes.addFlashAttribute("successMessage", myProperties.get("Clientreportupload.reort-name-published-validate"));
            }
            if (!StringUtils.isEmpty(saveReports.getStatus()) && Integer.parseInt(notPublished[1]) == ApplicationConstants.DB_ROW_STATUS_PUBLISHED_VALUE && retval == 1) {
                redirectAttributes.addFlashAttribute("successMessage", myProperties.get("Clientreportupload.reort-name-Not-published-validate"));
            }
            redirectAttributes.addFlashAttribute("ec", saveReports.getEncClientEngagementCode());

            redirectAttributes.addFlashAttribute("ec", saveReports.getEncClientEngagementCode());
            if ("E".equalsIgnoreCase(saveReports.getRedirectFlag())) {
                //modelView.setViewName("redirect:/editReports.htm?ec=" + saveReports.getEncClientEngagementCode());
                redirectPage = "redirect:/editReports.htm";
            } else if ("A".equalsIgnoreCase(saveReports.getRedirectFlag())) {
                //modelView.setViewName("redirect:/addReports.htm?ec=" + saveReports.getEncClientEngagementCode());
                redirectPage = "redirect:/addReports.htm";
            }
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the deleteUploadedFileDetails Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            redirectPage = "/exception";
        }

        return redirectPage;
    }

//    @RequestMapping(value = "/publishEngagment", method = RequestMethod.GET)
//    public ModelAndView publishEngagment(HttpServletRequest request, HttpServletResponse response) {
//        String engKey = request.getParameter("engKey");
//        //logger.info("Engagement publish method called..." + engKey);
//        ModelAndView modelView = new ModelAndView();
//        try {
//            if (null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_KEY)) {
//                reportsService.updateEngagmentStatusToPublish(encDec.decrypt(request.getSession().getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_KEY).toString()),
//                        ReportsAndDashboardConstants.DB_STATUS_KEY_AS_PUBLISHED);
//            }
//        } catch (AppException e) {
//            logger.debug("Exception Occured while Executing the deleteUploadedFileDetails Action :: " + e.getMessage());
//            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
//            return new ModelAndView("/exception", "exceptionBean", exception);
//        }
//        modelView.setViewName("redirect:/toengagementhomepage.htm?engKey=" + engKey);
//        return modelView;
//    }
    
    @RequestMapping(value = "/publishEngagment.htm", method = RequestMethod.POST)
    public @ResponseBody
    String publishEngagment(HttpServletRequest request, HttpServletResponse response,
            RedirectAttributes redirectAttributes, Model model) throws AppException {
         String engKey = request.getParameter("engKey");
         String publishedDate = "";
         HttpSession session = request.getSession();
        //logger.info("Engagement publish method called..." + engKey);
        try {
//            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
//                return "redirect:/logout.htm";
//            }
            
            if (null != request.getSession().getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_KEY)) {
               int retval = reportsService.updateEngagmentStatusToPublish(encDec.decrypt(request.getSession().getAttribute(ReportsAndDashboardConstants.ENGAGEMENT_KEY).toString()),
                        ReportsAndDashboardConstants.DB_STATUS_KEY_AS_PUBLISHED);
                if(retval > 0){
                    publishedDate = dateUtil.generateMailCurrentDate();
                }
            }
        } catch (AppException e) {
            logger.debug("Exception Occured while Executing the deleteUploadedFileDetails Action :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
           // return new ModelAndView("/exception", "exceptionBean", exception);
        }
        
        return publishedDate;

    }
    
    /**
     * This method for ECG update report files
     *
     * @param request
     * @param response
     * @param saveReports
     * @param redirectAttributes
     * @param model
     * @return String
     */
    @RequestMapping(value = "/ecgupdateSaveReportFile", method = RequestMethod.POST)
    public String ecgUpdateSaveReportFile(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute("saveReports") ClientReportsUploadDTO saveReports, RedirectAttributes redirectAttributes, Model model) {
        UserProfileDTO userDto = new UserProfileDTO();
        boolean success = false;
        String ecgUpload = "";
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());

                if (!StringUtils.isEmpty(saveReports.getEncClientEngagementCode())) {
                    saveReports.setClientEngagementCode(encDec.decrypt(saveReports.getEncClientEngagementCode()));
                }
                if (null != saveReports.getTemplateupload() && null != saveReports.getTemplateupload().getOriginalFilename()) {
                    String fileName = saveReports.getTemplateupload().getOriginalFilename();
                    String fileNamePattern = "^[a-zA-Z0-9.\\-_ ]{0,255}$";
                    Pattern fNamePatrn = Pattern.compile(fileNamePattern);
                    Matcher fnameMatchr = fNamePatrn.matcher(fileName);
                    saveReports.setFileName(fileName);

                    String destFolder = System.getProperty("OSS_UPLOAD_FOLDER");
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
                    /*ADDED CODE FOR PATH MANIPULATION SECURITY VULNERABILITY*/

                    File convFile = new File(saveReports.getTemplateupload().getOriginalFilename());
                    convFile.createNewFile();
                    FileOutputStream fos = new FileOutputStream(convFile);
                    //fos.write(saveReports.getTemplateupload().getBytes());
                    IOUtils.copy(saveReports.getTemplateupload().getInputStream(), fos);
                    fos.close();

                    if (!"".equals(fileName) && fileName != null) {
                         if (!fnameMatchr.find()) {
//                                errors.rejectValue("templateupload", "fileuploadValidate.file-name-validate");
                              redirectAttributes.addFlashAttribute("fileNameExists", myProperties.getProperty("fileuploadValidate.file-name-validate"));
                               redirectAttributes.addFlashAttribute("ec", saveReports.getEncClientEngagementCode());
                            }
                         else{
                        String taskFilepath = destFolder + File.separator + saveReports.getOrganizationName()
                                + File.separator + saveReports.getClientEngagementCode() + File.separator + saveReports.getClientEngagementName();
                        /*ADDED CODE FOR PATH MANIPULATION SECURITY VULNERABILITY*/
                        if (taskFilepath.contains("..\\")) {
                            taskFilepath = taskFilepath.replace("..\\", "");
                        }
                        if (taskFilepath.contains("../")) {
                            taskFilepath = taskFilepath.replace("../", "");
                        }
                        if (taskFilepath.contains("./")) {
                            taskFilepath = taskFilepath.replace("./", "");
                        }
                        /*ADDED CODE FOR PATH MANIPULATION SECURITY VULNERABILITY*/

                        if (!(new File(taskFilepath).exists())) {
                            // Create one directory
                            success = (new File(taskFilepath)).mkdirs();

                        }
                        taskFilepath += File.separator + fileName;
                        saveReports.setFilePath(taskFilepath);
                        saveReports.setFileSize(saveReports.getTemplateupload().getSize());
                        int duplicateFileIndicator = reportsService.validateDuplicateFileName(fileName, saveReports.getReportkey(),
                                encDec.decrypt(saveReports.getEncClientEngagementCode()));
                        if (duplicateFileIndicator > 0) {
                            logger.info("Duplicate file uploaded");
                            redirectAttributes.addFlashAttribute("fileNameExists", myProperties.getProperty("appfile.name.check"));
                             redirectAttributes.addFlashAttribute("ec", saveReports.getEncClientEngagementCode());
                        } else {
                            
                            int retVal = reportsService.saveUploadReportFile(saveReports, userDto);
                            if (retVal > 0) {

                                if (System.getProperty("ECG_UPLOAD") != null) {
                                    ecgUpload = System.getProperty("ECG_UPLOAD");
                                }
                                if ("YES".equalsIgnoreCase(ecgUpload)) {
                                    sendPost(retVal, fileName, convFile);
                                } else {
                                    File desfile = new File(taskFilepath);
                                    saveReports.getTemplateupload().transferTo(desfile);
                                }

                                redirectAttributes.addFlashAttribute("successMessage", myProperties.get("Clientreportupload.repot_file-name-updated-validate"));
                            }
                            redirectAttributes.addFlashAttribute("ec", saveReports.getEncClientEngagementCode());
                        }
                    }
                    }
                }
            } else {
                return "redirect:/logout.htm";
            }
        } catch (Exception e) {
            logger.error("Exception occurred : ReportsController: ecgUpdateSaveReportFile:" + e.getMessage());
            e.printStackTrace();
            model.addAttribute("exceptionBean", e);
            return "/exception";
        }
        return "redirect:/editReports.htm";

    }
    
    
    /**
     * This method for ECG save upload files
     *
     * @param request
     * @param response
     * @param saveReports
     * @param redirectAttributes
     * @param model
     * @return String
     */
    @RequestMapping(value = "/ecgsaveuploadreportfile", method = RequestMethod.POST)
    public String ecgSaveUploadReportFile(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute("saveReports") ClientReportsUploadDTO saveReports, RedirectAttributes redirectAttributes, Model model) {
        //List<String> fileNames = new ArrayList<String>();
        //ModelAndView modelView = new ModelAndView();
        UserProfileDTO userDto = new UserProfileDTO();
        boolean success = false;
        String ecgUpload ="";
//        if(serverSideFlag=)
        try {
            if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());

                if (!StringUtils.isEmpty(saveReports.getEncClientEngagementCode())) {
                    saveReports.setClientEngagementCode(encDec.decrypt(saveReports.getEncClientEngagementCode()));
                }

                if (null != saveReports.getTemplateupload() && null != saveReports.getTemplateupload().getOriginalFilename()) {
                    String fileName = saveReports.getTemplateupload().getOriginalFilename();
                    String fileNamePattern = "^[a-zA-Z0-9.\\-_ ]{0,255}$";
                    Pattern fNamePatrn = Pattern.compile(fileNamePattern);
                    Matcher fnameMatchr = fNamePatrn.matcher(fileName);
                    saveReports.setFileName(fileName);

                    String destFolder = System.getProperty("OSS_UPLOAD_FOLDER");
                    
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
                    /*ADDED CODE FOR PATH MANIPULATION SECURITY VULNERABILITY*/
                
                    File convFile = new File(saveReports.getTemplateupload().getOriginalFilename());
                    convFile.createNewFile();
                    FileOutputStream fos = new FileOutputStream(convFile);
                    //fos.write(saveReports.getTemplateupload().getBytes());
                    IOUtils.copy(saveReports.getTemplateupload().getInputStream(), fos);
                    fos.close();
                    

                    if (!"".equals(fileName) && fileName != null) {
                         if (!fnameMatchr.find()) {
//                                errors.rejectValue("templateupload", "fileuploadValidate.file-name-validate");
                              redirectAttributes.addFlashAttribute("fileNameExists", myProperties.getProperty("fileuploadValidate.file-name-validate"));
                               redirectAttributes.addFlashAttribute("ec", saveReports.getEncClientEngagementCode());
                            }
                         else{
                        String taskFilepath = destFolder + File.separator + saveReports.getOrganizationName()
                                + File.separator + saveReports.getClientEngagementCode() + File.separator + saveReports.getClientEngagementName();
                        
                        /*ADDED CODE FOR PATH MANIPULATION SECURITY VULNERABILITY*/
                        if (taskFilepath.contains("..\\")) {
                            taskFilepath = taskFilepath.replace("..\\", "");
                        }
                        if (taskFilepath.contains("../")) {
                            taskFilepath = taskFilepath.replace("../", "");
                        }
                        if (taskFilepath.contains("./")) {
                            taskFilepath = taskFilepath.replace("./", "");
                        }
                        /*ADDED CODE FOR PATH MANIPULATION SECURITY VULNERABILITY*/
                        
                        if (!(new File(taskFilepath).exists())) {
                            // Create one directory
                            success = (new File(taskFilepath)).mkdirs();

                        }
                        taskFilepath += File.separator + fileName;
                        // createDocumentDTO.setFilePath(taskFilepath);
                        saveReports.setFilePath(taskFilepath);
                        saveReports.setFileSize(saveReports.getTemplateupload().getSize());
                         int duplicateFileIndicator = reportsService.validateDuplicateFileName(fileName, saveReports.getReportkey(),
                                encDec.decrypt(saveReports.getEncClientEngagementCode()));
                        if (duplicateFileIndicator > 0) {
                            logger.info("Duplicate file uploaded");
                            redirectAttributes.addFlashAttribute("fileNameExists", myProperties.getProperty("appfile.name.check"));
                             redirectAttributes.addFlashAttribute("ec", saveReports.getEncClientEngagementCode());
                        }else {
                            int retVal = reportsService.saveUploadReportFile(saveReports, userDto);
                            if (retVal > 0) {

                                if (System.getProperty("ECG_UPLOAD") != null) {
                                    ecgUpload = System.getProperty("ECG_UPLOAD");
                                }
                                if ("YES".equalsIgnoreCase(ecgUpload)) {
                                    sendPost(retVal, fileName, convFile);
                                } else {
                                    File desfile = new File(taskFilepath);
                                    saveReports.getTemplateupload().transferTo(desfile);
                                }
                                redirectAttributes.addFlashAttribute("successMessage", myProperties.get("Clientreportupload.repot_file-name-upload-validate"));
                            }
                             redirectAttributes.addFlashAttribute("ec", saveReports.getEncClientEngagementCode());
                        }
                    }
                    }
                }
            } else {
                return "redirect:/logout.htm";
            }
        } catch (Exception e) {
            logger.error("Exception occurred : ReportsController: saveUploadReportFile:" + e.getMessage());
            e.printStackTrace();
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        //modelView.setViewName("redirect:/addReports.htm?ec=" + saveReports.getEncClientEngagementCode());
        return "redirect:/addReports.htm";

    }
    
    
    
    
    private void sendPost(int fileID, final String fileName, final File convFile) throws Exception {

        disableSslVerification();
        final String USER_AGENT = "Mozilla/5.0";
        String url = "";
        if (!org.springframework.util.StringUtils.isEmpty(ApplicationConstants.ECG_UPLOAD_URL)
                && !org.springframework.util.StringUtils.isEmpty(ApplicationConstants.ECG_UPLOAD_PORT)) {
            url = ApplicationConstants.ECG_UPLOAD_URL.trim() + ":" + ApplicationConstants.ECG_UPLOAD_PORT.trim();

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
            in.close();
        }

        // print result
        // System.out.println(response.toString());
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

}
