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
import com.optum.oss.dto.RemediationDTO;
import com.optum.oss.dto.SecurityServiceDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.dto.VulnerabilityDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.PermissionCheckHelper;
import com.optum.oss.model.SummaryDropDownModel;
import com.optum.oss.service.RiskRegistryService;
import com.optum.oss.service.impl.AnalystValidationServiceImpl;
import com.optum.oss.service.impl.ManageEngagementsServiceImpl;
import com.optum.oss.service.impl.MasterLookUpServiceImpl;
import com.optum.oss.service.impl.RemediationProjectManagerServiceImpl;
import com.optum.oss.service.impl.RemediationServiceImpl;
import com.optum.oss.service.impl.ReportsAndDashboardServiceImpl;
import com.optum.oss.util.CommonUtil;
import com.optum.oss.util.DateUtil;
import com.optum.oss.util.EncryptDecrypt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author sbhagavatula
 */
@Controller
public class RemediationController {
    
    private static final Logger logger = Logger.getLogger(RemediationController.class);

    @Resource(name = "myProperties")
    private Properties myProperties;
    /*
     *Begin: Autowiring the required Class instances
     */
    @Autowired
    private ManageEngagementsServiceImpl manageEngagementsServiceImpl;
    @Autowired
    private AnalystValidationServiceImpl analystValidationService;
    @Autowired
    private RemediationProjectManagerServiceImpl remediationProjectManagerService;
    @Autowired
    private EncryptDecrypt encDec;
    @Autowired
    PermissionCheckHelper permissionCheckHelper;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private MasterLookUpServiceImpl masterLookUpServiceImpl;
    @Autowired
    private RemediationServiceImpl remediationService;
    @Autowired
    private ReportsAndDashboardServiceImpl reportsAndDashboardService;
    @Autowired
    private DateUtil dateUtil;
    @Autowired
    private RiskRegistryService riskRegistryService;

    /*
     * End: Autowiring the required Class instances
     */
    /*
     START : LOG MESSAGES
     */
    private final String TRACE_USER_REMEDIATION_MODULE_WORKLIST = "User accessed remediation module worklist";
    private final String TRACE_USER_REMEDIATION_PLAN = "User accessed remediation plan";
    private final String TRACE_USER_FINDING_LOOKUP = "User accessed finding lookup";
    private final String TRACE_USER_SEARCH_REMEDIATION_MODULE_WORKLIST = "User accessed remediation search remediation module worklist";
    private final String TRACE_USER_ADD_REMEDIATION_PAGE = "User accessed add remediationpage";
    private final String TRACE_USER_ADD_REMEDIATION_PLAN = "User accessed add remediation plan";
    private final String TRACE_UPDATE_NOTIFY_DATE = "User accessed update notify date";
    private final String TRACE_USER_UPDATE_PLAN_ITEM_STATUS = "User accessed update plan item status";
    private final String TRACE_USER_EDIT_REMEDIATION_PAGE = "User accessed edit remediation page";
    /*
     END : LOG MESSAGES
     */
    
    @RequestMapping(value = "/remediationhome")
    public ModelAndView remediationWorklist(HttpServletRequest request, HttpServletResponse response, Model model,
            RedirectAttributes redirectAttributes) {

        logger.info(TRACE_USER_REMEDIATION_MODULE_WORKLIST);
        ModelAndView modelView = new ModelAndView();
        UserProfileDTO userDto = new UserProfileDTO();
        Set<String> remediationPermissionSet = null;
        ManageServiceUserDTO manageServiceUserDTO = new ManageServiceUserDTO();
        try {
            HttpSession session = request.getSession();
            
            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return new ModelAndView("redirect:/logout.htm");
            }
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(TRACE_USER_REMEDIATION_MODULE_WORKLIST);

                remediationPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.REMEDIATION_MODULE);
                modelView.addObject("permissions", remediationPermissionSet);
            }
            if (permissionCheckHelper.checkUserSubMenuNotAdmin(userDto, PermissionConstants.REMEDIATION_MODULE)) {
                List<ManageServiceUserDTO> remediationWorklist = remediationProjectManagerService.fetchRemediationWorklist(userDto, manageServiceUserDTO);
                Map remediationMap = analystValidationService.engagementServicesListToMapConversion(remediationWorklist);
                modelView.addObject("engagementMap", remediationMap.get("engagementMap"));
                modelView.addObject("serviceMap", remediationMap.get("serviceMap"));
                modelView.addObject("organizationServieCount", remediationMap.get("organizationServieCount"));
                modelView.addObject("statusMap", remediationMap.get("statusMap"));
                modelView.setViewName("remediation/remediationhome");
            } else {
                modelView.setViewName("redirect:/logout.htm");
            }
        } catch (AppException e) {
            logger.error("Exception occurred : RemediationController: remediationWorklist:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        logger.info("End: RemediationController: remediationWorklist()");
        return modelView;
    }

    /**
     * Method for search remediation project manager work list
     *
     * @param request
     * @param response
     * @param remediationData
     * @param redirectAttributes
     * @param model
     * @return modelView
     */
    @RequestMapping(value = "/searchremediationhome")
    public ModelAndView searchRemediationWorkList(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute(value = "searchremediation") ManageServiceUserDTO remediationData,RedirectAttributes redirectAttributes,Model model) {

        logger.info(TRACE_USER_SEARCH_REMEDIATION_MODULE_WORKLIST);
        ModelAndView modelView = new ModelAndView();
        UserProfileDTO userDto = new UserProfileDTO();
        Set<String> remediationPermissionSet = null;
        try {
          
            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return new ModelAndView("redirect:/logout.htm");
            }
            if (request.getParameter("engkey") != null) {
                HttpSession session = request.getSession();
                if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                    userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                    MDC.put("user", userDto.getEmail());
                    MDC.put("ip", userDto.getUserMACAddress());
                    logger.trace(TRACE_USER_SEARCH_REMEDIATION_MODULE_WORKLIST);
                    remediationPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.REMEDIATION_MODULE);
                    modelView.addObject("permissions", remediationPermissionSet);
                }
                if (permissionCheckHelper.checkUserSubMenuNotAdmin(userDto, PermissionConstants.REMEDIATION_MODULE)) {
                    List<ManageServiceUserDTO> remediationWorklist = remediationProjectManagerService.fetchRemediationWorklist(userDto, remediationData);
                    Map remediationMap = analystValidationService.engagementServicesListToMapConversion(remediationWorklist);
                    modelView.addObject("engagementMap", remediationMap.get("engagementMap"));
                    modelView.addObject("serviceMap", remediationMap.get("serviceMap"));
                    modelView.addObject("organizationServieCount", remediationMap.get("organizationServieCount"));
                    modelView.addObject("statusMap", remediationMap.get("statusMap"));
                    modelView.addObject("remediationData", remediationData);
                    modelView.setViewName("remediation/remediationhome");
                } else {
                    modelView.setViewName("redirect:/logout.htm");
                }
            } else {
                modelView.setViewName("redirect:/logout.htm");
            }
        } catch (AppException e) {
            logger.error("Exception occurred : RemediationController: searchRemediationWorkList:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        logger.info("End: RemediationController: searchRemediationWorkList()");
        return modelView;
    }
    
    /**
     *
     * @param request
     * @param response
     * @param model
     * @param redirectAttributes
     * @param redirectEngagementCode
     * @return
     */
    @RequestMapping(value="/remediationplan")
    public String remediationPlan(HttpServletRequest request, HttpServletResponse response,
            Model model, RedirectAttributes redirectAttributes,
            @ModelAttribute(value = "redengkey") String redirectEngagementCode){
        logger.info(TRACE_USER_REMEDIATION_PLAN);
        UserProfileDTO userDto = new UserProfileDTO();
        Set<String> remediationPermissionSet = null;
        try {
            HttpSession session = request.getSession(); 
             if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return "redirect:/logout.htm";
            }
            String engagementCode ="";
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(TRACE_USER_REMEDIATION_PLAN);
                remediationPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.REMEDIATION_MODULE);
                model.addAttribute("permissions", remediationPermissionSet);
            }           
            if (redirectEngagementCode != null && !StringUtils.isEmpty(redirectEngagementCode)) {
                engagementCode = redirectEngagementCode;
            } else if (request.getParameter("engkey") != null && !StringUtils.isEmpty(request.getParameter("engkey"))) {
                engagementCode = request.getParameter("engkey");
            }      
            if(engagementCode != null && !StringUtils.isEmpty(engagementCode))
            {
                if (permissionCheckHelper.checkUserSubMenuNotAdmin(userDto, PermissionConstants.REMEDIATION_MODULE)) {
                    String decEngagementCode = "";
                    if (engagementCode != null && !engagementCode.equalsIgnoreCase("")) {
                        decEngagementCode = encDec.decrypt(engagementCode);
                    }
                    ClientEngagementDTO engagementDTO = manageEngagementsServiceImpl.viewEngagementByEngmtKey(decEngagementCode);
                    model.addAttribute("engagementDTO", engagementDTO);

                    String orgSchema = reportsAndDashboardService.getSchemaNameByEngKey(engagementCode);

                    List<RemediationDTO> remediationList = remediationService.remediationPlanList(decEngagementCode, orgSchema, userDto);
                    model.addAttribute("remediationList", remediationList);
                } else {
                    return "redirect:/logout.htm";
                }
            }else{
                return "redirect:/logout.htm";
            }
        } catch (AppException e) {
            logger.error("Exception occurred : RemediationController: viewRemediation:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        logger.info("End: RemediationController: viewRemediation()");
        return "remediation/remediationplan";
    }
    
    @RequestMapping(value = "/findinglookup")
    public String findingLookup(HttpServletRequest request, HttpServletResponse response,
             Model model,RedirectAttributes redirectAttributes, 
             @ModelAttribute RemediationDTO remediationDTO,
             @ModelAttribute(value = "redengkey") String encEngagementCode) {
        logger.trace(TRACE_USER_FINDING_LOOKUP);
        UserProfileDTO userDto = null;
        
        try{
             if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                Set<String> analystPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.REMEDIATION_MODULE);
                model.addAttribute("permissions", analystPermissionSet);
            
                logger.trace(TRACE_USER_FINDING_LOOKUP);
            }
            
//            if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.REMEDIATION_MODULE,
//                        PermissionConstants.ADD_FINDING)) {
                String decEngagementCode = "";
                if (encEngagementCode != null && !encEngagementCode.equalsIgnoreCase("")) {
                    decEngagementCode = encDec.decrypt(encEngagementCode);
                }
                
                ClientEngagementDTO engagementDTO = manageEngagementsServiceImpl.viewEngagementByEngmtKey(decEngagementCode);
                model.addAttribute("engagementDTO", engagementDTO);
                
                String orgSchema = reportsAndDashboardService.getSchemaNameByEngKey(encEngagementCode);
                
                model.addAttribute("remediationDTO", remediationDTO);
                
                List<VulnerabilityDTO> findingList = new ArrayList<>();
                if(encEngagementCode != null){
                    remediationDTO.getClientEngagementDTO().setEngagementCode(decEngagementCode);
                    remediationDTO.setOrgSchema(orgSchema);
                    findingList = remediationService.findingLookup(remediationDTO);
                    model.addAttribute("findingList", findingList);
                }
                
                //Services list along with their statuses for an engagement
                List<SecurityServiceDTO> servicesList = analystValidationService.analystEngagementServices(decEngagementCode);
                model.addAttribute("servicesList", servicesList);
                
                //Operating System dropdown list
                List<MasterLookUpDTO> osList = remediationService.fetchOSCatList();
                model.addAttribute("osList", osList);
                
                //Severity list
                List<MasterLookUpDTO> severityList = remediationService.fetchSeverityList();
                model.addAttribute("severityList",severityList);
                
                //FETCH SOURCE SYSTEMS
                List<MasterLookUpDTO> srcSystems = masterLookUpServiceImpl.fetchMasterLookUpEntitiesByEntityType(ApplicationConstants.DB_CONSTANT_SOURCE);
                model.addAttribute("srcSystems", srcSystems);
                
                //Vulnerability Category list
                List<MasterLookUpDTO> vulnerabilityCatList = analystValidationService.vulnerabilityCategoryList();
                model.addAttribute("vulnerabilityCatList", vulnerabilityCatList);
//            }else{
//                return "redirect:/logout.htm";
//            }
                
        } catch (AppException e) {
            logger.error("Exception occurred : RemediationController: findingLookup:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        logger.info("End: RemediationController: findingLookup()");
        return "remediation/findinglookup";
    }
    
    @RequestMapping(value = "/addremediationpage")
    public String addRemediationPage(HttpServletRequest request, HttpServletResponse response,
             Model model,RedirectAttributes redirectAttributes, 
             @ModelAttribute RemediationDTO remediationDTO,
             @ModelAttribute(value = "redengkey") String encEngagementCode) {
        logger.trace(TRACE_USER_ADD_REMEDIATION_PAGE);
        UserProfileDTO userDto = null;
        
        try{
             if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                Set<String> analystPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.REMEDIATION_MODULE);
                model.addAttribute("permissions", analystPermissionSet);
            
                logger.trace(TRACE_USER_ADD_REMEDIATION_PAGE);
            }
            
            if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.REMEDIATION_MODULE,
                        PermissionConstants.ADD_FINDING)) {
                if(remediationDTO.getSelectedInstances() == null || StringUtils.isEmpty(remediationDTO.getSelectedInstances())){
                    redirectAttributes.addFlashAttribute("remediationDTO", remediationDTO);
                    redirectAttributes.addFlashAttribute("selectOneFinding", myProperties.getProperty("remediationplanitem.delete.error-message"));
                    redirectAttributes.addFlashAttribute("redengkey", encEngagementCode);
                    return "redirect:/findinglookup.htm";
                }
                String decEngagementCode = "";
                if (encEngagementCode != null && !encEngagementCode.equalsIgnoreCase("")) {
                    decEngagementCode = encDec.decrypt(encEngagementCode);
                }
                
                ClientEngagementDTO engagementDTO = manageEngagementsServiceImpl.viewEngagementByEngmtKey(decEngagementCode);
                model.addAttribute("engagementDTO", engagementDTO);
                
                String orgSchema = reportsAndDashboardService.getSchemaNameByEngKey(encEngagementCode);
                
                List<String> removeFnds = null;
                List<String> selFnds = null;
                if(remediationDTO.getRemoveInstances() != null && !"".equalsIgnoreCase(remediationDTO.getRemoveInstances())){
                    removeFnds = new ArrayList(Arrays.asList(remediationDTO.getRemoveInstances().split("\\s*,\\s*")));
                    selFnds = new ArrayList(Arrays.asList(remediationDTO.getSelectedInstances().split("\\s*,\\s*")));
                    selFnds.removeAll(removeFnds);
                    remediationDTO.setSelectedInstances(StringUtils.join(selFnds, ","));
                }
                
                remediationDTO.getClientEngagementDTO().setEngagementCode(decEngagementCode);
                remediationDTO.setOrgSchema(orgSchema);
                List<VulnerabilityDTO> findingsList = remediationService.findingsByMultipleIds(remediationDTO);
                
                model.addAttribute("findingsList", findingsList);
                
                List<VulnerabilityDTO> findingList = new ArrayList<>();
                if(encEngagementCode != null){
                    remediationDTO.getClientEngagementDTO().setEngagementCode(decEngagementCode);
                    remediationDTO.setOrgSchema(orgSchema);
                    findingList = remediationService.findingLookup(remediationDTO);
                    model.addAttribute("findingList", findingList);
                }
                remediationDTO.setCreatedDate(dateUtil.generateMailCurrentDate());
                model.addAttribute("remediationDTO", remediationDTO);
                
                //Severity dropdown list
                List<SummaryDropDownModel> severityList = analystValidationService.analystSeverityList();
                model.addAttribute("severityList", severityList);
                
                //CLIENT USERS BASED ON CLIENT ORGANIZATION
                List<UserProfileDTO> clientUsers = remediationService.remediationOwnerUsers(ApplicationConstants.REMEDIATION_OWNER, decEngagementCode);
                model.addAttribute("clientUsers", clientUsers);
            }else{
                return "redirect:/logout.htm";
            }
                
        } catch (AppException e) {
            logger.error("Exception occurred : RemediationController: addRemediationPage:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        logger.info("End: RemediationController: addRemediationPage()");
        return "remediation/addremediationplan";
    }
    
    @RequestMapping(value = "/addremediationplan")
    public String addRemediationPlan(HttpServletRequest request, HttpServletResponse response,
             Model model,RedirectAttributes redirectAttributes, 
             @ModelAttribute RemediationDTO remediationDTO,
             @ModelAttribute(value = "redengkey") String encEngagementCode,
             BindingResult result) {
        logger.trace(TRACE_USER_ADD_REMEDIATION_PLAN);
        UserProfileDTO userDto = null;
        
        try{
            //planValidator.validate(remediationDTO, result);
             if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                Set<String> analystPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.REMEDIATION_MODULE);
                model.addAttribute("permissions", analystPermissionSet);
            
                logger.trace(TRACE_USER_ADD_REMEDIATION_PLAN);
            }
            
            if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.REMEDIATION_MODULE,
                        PermissionConstants.ADD_FINDING)) {
                String decEngagementCode = "";
                if (encEngagementCode != null && !encEngagementCode.equalsIgnoreCase("")) {
                    decEngagementCode = encDec.decrypt(encEngagementCode);
                }
                
                ClientEngagementDTO engagementDTO = manageEngagementsServiceImpl.viewEngagementByEngmtKey(decEngagementCode);
                model.addAttribute("engagementDTO", engagementDTO);
                
                String orgSchema = reportsAndDashboardService.getSchemaNameByEngKey(encEngagementCode);
                
                remediationDTO.setClientEngagementDTO(engagementDTO);
                remediationDTO.setOrgSchema(orgSchema);
                
                if (result.hasErrors()) {
                    redirectAttributes.addFlashAttribute("remediationDTO", remediationDTO);
                    redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.vulnerabilityDTO", result);
                    redirectAttributes.addFlashAttribute("redengkey", encEngagementCode);
                    return "redirect:/addremediationpage.htm";
                }
                    
                int addId = remediationService.addRemediationPlan(remediationDTO, userDto);
                
                if(addId>0)
                    redirectAttributes.addFlashAttribute("addUpdateSuccessMessage", myProperties.getProperty("remediationplan.add.success-message"));
                
                redirectAttributes.addAttribute("redengkey", encEngagementCode);
                redirectAttributes.addFlashAttribute("remediationDTO",remediationDTO);
            }else{
                return "redirect:/logout.htm";
            }
                
        } catch (AppException e) {
            logger.error("Exception occurred : RemediationController: addRemediationPlan:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        logger.info("End: RemediationController: addRemediationPlan()");
       return "redirect:/editremediationpage.htm";
    }
    
    @RequestMapping(value = "/editremediationpage")
    public String editRemediationPage(HttpServletRequest request, HttpServletResponse response,
             Model model,RedirectAttributes redirectAttributes, 
             @ModelAttribute RemediationDTO remediationDTO,
             @ModelAttribute(value = "redengkey") String encEngagementCode) {
        logger.trace(TRACE_USER_EDIT_REMEDIATION_PAGE);
        UserProfileDTO userDto = null;
        
        try{
             if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                Set<String> analystPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.REMEDIATION_MODULE);
                model.addAttribute("permissions", analystPermissionSet);
            
                logger.trace(TRACE_USER_EDIT_REMEDIATION_PAGE);
            }
            
            if (permissionCheckHelper.checkUserSubMenuNotAdmin(userDto, PermissionConstants.REMEDIATION_MODULE)) {
                String decEngagementCode = "";
                if (encEngagementCode != null && !encEngagementCode.equalsIgnoreCase("")) {
                    decEngagementCode = encDec.decrypt(encEngagementCode);
                }
                
                ClientEngagementDTO engagementDTO = manageEngagementsServiceImpl.viewEngagementByEngmtKey(decEngagementCode);
                model.addAttribute("engagementDTO", engagementDTO);
                
                String orgSchema = reportsAndDashboardService.getSchemaNameByEngKey(encEngagementCode);
                
                if (model.containsAttribute("remediateObj")) {
                    remediationDTO = (RemediationDTO) model.asMap().get("remediateObj");
                }

                remediationDTO.getClientEngagementDTO().setEngagementCode(decEngagementCode);
                remediationDTO.setOrgSchema(orgSchema);
                
                RemediationDTO planInfo = remediationService.planInfoByPlanKey(remediationDTO.getPlanID(), orgSchema);
                model.addAttribute("planInfo", planInfo);
                List<RemediationDTO> notesList = remediationService.planNotesByPlanKey(remediationDTO.getPlanID(), orgSchema);
                model.addAttribute("notesList", notesList);
                List<VulnerabilityDTO> planFindings = remediationService.findingsByPlanKey(remediationDTO.getPlanID(), orgSchema);
                model.addAttribute("planFindings", planFindings);
                
                
                //Severity dropdown list
                List<SummaryDropDownModel> severityList = analystValidationService.analystSeverityList();
                model.addAttribute("severityList", severityList);
                
                //Remediation status list
                List<MasterLookUpDTO> remStatusList = remediationService.fetchRemediationStatus();
                model.addAttribute("remStatusList", remStatusList);
                
                //CLIENT USERS BASED ON CLIENT ORGANIZATION
                List<UserProfileDTO> clientUsers = remediationService.remediationOwnerUsers(ApplicationConstants.REMEDIATION_OWNER, decEngagementCode);
                model.addAttribute("clientUsers", clientUsers);

            }else{
                return "redirect:/logout.htm";
            }
                
        } catch (AppException e) {
            logger.error("Exception occurred : RemediationController: editRemediationPage:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        logger.info("End: RemediationController: editRemediationPage()");
        return "remediation/editremediationplan";
    }
    
    @RequestMapping(value = "/addfindingstoplan")
    public String addFindingsToPlan(HttpServletRequest request, HttpServletResponse response,
             Model model,RedirectAttributes redirectAttributes, 
             @ModelAttribute RemediationDTO remediationDTO,
             @ModelAttribute(value = "redengkey") String encEngagementCode) {
        logger.trace(TRACE_USER_ADD_REMEDIATION_PAGE);
        UserProfileDTO userDto = null;
        
        try{
             if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                Set<String> analystPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.REMEDIATION_MODULE);
                model.addAttribute("permissions", analystPermissionSet);
            
                logger.trace(TRACE_USER_ADD_REMEDIATION_PAGE);
            }
            
            if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.REMEDIATION_MODULE,
                        PermissionConstants.ADD_FINDING)) {
                if(remediationDTO.getSelectedInstances() == null || StringUtils.isEmpty(remediationDTO.getSelectedInstances())){
                    redirectAttributes.addFlashAttribute("remediationDTO", remediationDTO);
                    redirectAttributes.addFlashAttribute("selectOneFinding", myProperties.getProperty("remediationplanitem.delete.error-message"));
                    redirectAttributes.addFlashAttribute("redengkey", encEngagementCode);
                    return "redirect:/findinglookup.htm";
                }
                
                String decEngagementCode = "";
                if (encEngagementCode != null && !encEngagementCode.equalsIgnoreCase("")) {
                    decEngagementCode = encDec.decrypt(encEngagementCode);
                }
                
                ClientEngagementDTO engagementDTO = manageEngagementsServiceImpl.viewEngagementByEngmtKey(decEngagementCode);
                model.addAttribute("engagementDTO", engagementDTO);
                
                String orgSchema = reportsAndDashboardService.getSchemaNameByEngKey(encEngagementCode);
                
                remediationDTO.getClientEngagementDTO().setEngagementCode(decEngagementCode);
                remediationDTO.setOrgSchema(orgSchema);
                int retVal = remediationService.addFindingsToPlan(remediationDTO, userDto);
                
                RemediationDTO planInfo = remediationService.planInfoByPlanKey(remediationDTO.getPlanID(), orgSchema);
                model.addAttribute("planInfo", planInfo);
                List<RemediationDTO> notesList = remediationService.planNotesByPlanKey(remediationDTO.getPlanID(), orgSchema);
                model.addAttribute("notesList", notesList);
                List<VulnerabilityDTO> planFindings = remediationService.findingsByPlanKey(remediationDTO.getPlanID(), orgSchema);
                model.addAttribute("planFindings", planFindings);
                
                //Remediation status list
                List<MasterLookUpDTO> remStatusList = remediationService.fetchRemediationStatus();
                model.addAttribute("remStatusList", remStatusList);
                
                //CLIENT USERS BASED ON CLIENT ORGANIZATION
                List<UserProfileDTO> clientUsers = remediationService.remediationOwnerUsers(ApplicationConstants.REMEDIATION_OWNER, decEngagementCode);
                model.addAttribute("clientUsers", clientUsers);
                
                //Severity dropdown list
                List<SummaryDropDownModel> severityList = analystValidationService.analystSeverityList();
                model.addAttribute("severityList", severityList);
                
                if(retVal > 0){
                    riskRegistryService.updateFindingCountRegistryAnsPlan(Integer.parseInt(remediationDTO.getPlanID()), planFindings.size(),
                            userDto.getUserProfileKey(), orgSchema, ApplicationConstants.DB_CONSTANT_REMEDIATION_FINDING_COUNT_UPDATE);
                }
            }else{
                return "redirect:/logout.htm";
            }
                
        } catch (AppException e) {
            logger.error("Exception occurred : RemediationController: addRemediationPage:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        logger.info("End: RemediationController: addRemediationPage()");
        return "remediation/editremediationplan";
    }
    
    @RequestMapping(value = "/updateremediationplan")
    public String updateRemediationPlan(HttpServletRequest request, HttpServletResponse response,
             Model model,RedirectAttributes redirectAttributes, 
             @ModelAttribute RemediationDTO remediationDTO,
             @ModelAttribute(value = "redengkey") String encEngagementCode,
             BindingResult result) {
        logger.trace(TRACE_USER_ADD_REMEDIATION_PLAN);
        UserProfileDTO userDto = null;
        //updatePlanValidator.validate(remediationDTO, result);
        try{
             if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                Set<String> analystPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.REMEDIATION_MODULE);
                model.addAttribute("permissions", analystPermissionSet);
            
                logger.trace(TRACE_USER_ADD_REMEDIATION_PLAN);
            }
            
            if (permissionCheckHelper.checkUserSubMenuNotAdmin(userDto, PermissionConstants.REMEDIATION_MODULE)) {
                String decEngagementCode = "";
                if (encEngagementCode != null && !encEngagementCode.equalsIgnoreCase("")) {
                    decEngagementCode = encDec.decrypt(encEngagementCode);
                }
                
                ClientEngagementDTO engagementDTO = manageEngagementsServiceImpl.viewEngagementByEngmtKey(decEngagementCode);
                model.addAttribute("engagementDTO", engagementDTO);
                
                String orgSchema = reportsAndDashboardService.getSchemaNameByEngKey(encEngagementCode);
                
                remediationDTO.setClientEngagementDTO(engagementDTO);
                remediationDTO.setOrgSchema(orgSchema);
                int updateId = remediationService.updateRemediationPlan(remediationDTO, userDto);
                
                if(updateId>0)
                    redirectAttributes.addFlashAttribute("addUpdateSuccessMessage", myProperties.getProperty("remediationplan.update.success-message"));
                
                redirectAttributes.addAttribute("redengkey", encEngagementCode);
                redirectAttributes.addFlashAttribute("remediationDTO",remediationDTO);
            }else{
                return "redirect:/logout.htm";
            }
                
        } catch (AppException e) {
            logger.error("Exception occurred : RemediationController: updateRemediationPlan:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        logger.info("End: RemediationController: updateRemediationPlan()");
        return "redirect:/editremediationpage.htm";
    }
    
    @RequestMapping(value = "/deleteplanfinding")
    public String deletePlanFinding(HttpServletRequest request, HttpServletResponse response,
             Model model,RedirectAttributes redirectAttributes, 
             @ModelAttribute RemediationDTO remediationDTO,
             @ModelAttribute(value = "redengkey") String encEngagementCode) {
        logger.trace(TRACE_USER_ADD_REMEDIATION_PLAN);
        UserProfileDTO userDto = null;
        
        try{
             if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                Set<String> analystPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.REMEDIATION_MODULE);
                model.addAttribute("permissions", analystPermissionSet);
            
                logger.trace(TRACE_USER_ADD_REMEDIATION_PLAN);
            }
            
            if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.REMEDIATION_MODULE,
                        PermissionConstants.DELETE_FINDING)) {
                String decEngagementCode = "";
                if (encEngagementCode != null && !encEngagementCode.equalsIgnoreCase("")) {
                    decEngagementCode = encDec.decrypt(encEngagementCode);
                }
                
                ClientEngagementDTO engagementDTO = manageEngagementsServiceImpl.viewEngagementByEngmtKey(decEngagementCode);
                model.addAttribute("engagementDTO", engagementDTO);
                
                String orgSchema = reportsAndDashboardService.getSchemaNameByEngKey(encEngagementCode);
                remediationDTO.getClientEngagementDTO().setEngagementCode(decEngagementCode);
                remediationDTO.setOrgSchema(orgSchema);
                model.addAttribute("remediateObj", remediationDTO);
                
                int deleteId = 0;
                if(remediationDTO.getRemoveInstances()!=null && !"".equalsIgnoreCase(remediationDTO.getRemoveInstances())){
                    deleteId = remediationService.deletePlanFindings(remediationDTO, userDto);
                    if(deleteId>0){
                       model.addAttribute("deleteSuccessMessage", myProperties.getProperty("remediationplanitem.delete.success-message"));
                    }
                }else{
                    model.addAttribute("deleteErrMessage", myProperties.getProperty("remediationplanitem.delete.error-message"));
                }
                
                
                remediationDTO.setOrgSchema(orgSchema);
                
                RemediationDTO planInfo = remediationService.planInfoByPlanKey(remediationDTO.getPlanID(), orgSchema);
                model.addAttribute("planInfo", planInfo);
                List<RemediationDTO> notesList = remediationService.planNotesByPlanKey(remediationDTO.getPlanID(), orgSchema);
                model.addAttribute("notesList", notesList);
                List<VulnerabilityDTO> planFindings = remediationService.findingsByPlanKey(remediationDTO.getPlanID(), orgSchema);
                model.addAttribute("planFindings", planFindings);
                
                //if(deleteId > 0){
                    riskRegistryService.updateFindingCountRegistryAnsPlan(Integer.parseInt(remediationDTO.getPlanID()), planFindings.size(),
                            userDto.getUserProfileKey(), orgSchema, ApplicationConstants.DB_CONSTANT_REMEDIATION_FINDING_COUNT_UPDATE);
                //}
                
                redirectAttributes.addAttribute("redengkey", encEngagementCode);
                
                //Remediation status list
                List<MasterLookUpDTO> remStatusList = remediationService.fetchRemediationStatus();
                model.addAttribute("remStatusList", remStatusList);
                
                //Severity dropdown list
                List<SummaryDropDownModel> severityList = analystValidationService.analystSeverityList();
                model.addAttribute("severityList", severityList);
                
                //CLIENT USERS BASED ON CLIENT ORGANIZATION
                List<UserProfileDTO> clientUsers = remediationService.remediationOwnerUsers(ApplicationConstants.REMEDIATION_OWNER, decEngagementCode);
                model.addAttribute("clientUsers", clientUsers);
            }else{
                return "redirect:/logout.htm";
            }
                
        } catch (AppException e) {
            logger.error("Exception occurred : RemediationController: deletePlanFinding:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        logger.info("End: RemediationController: deletePlanFinding()");
        return "remediation/editremediationplan";
    }
    
    @RequestMapping(value = "/statusrisk")
    public String statusRiskRegistered(HttpServletRequest request, HttpServletResponse response,
             Model model,RedirectAttributes redirectAttributes, 
             @ModelAttribute RemediationDTO remediationDTO,
             @ModelAttribute(value = "redengkey") String encEngagementCode) {
        logger.trace(TRACE_USER_ADD_REMEDIATION_PLAN);
        UserProfileDTO userDto = null;
        
        try{
             if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                Set<String> analystPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.REMEDIATION_MODULE);
                model.addAttribute("permissions", analystPermissionSet);
            
                logger.trace(TRACE_USER_ADD_REMEDIATION_PLAN);
            }
            
            if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.REMEDIATION_MODULE,
                        PermissionConstants.EDIT_FINDING)) {
                String decEngagementCode = "";
                if (encEngagementCode != null && !encEngagementCode.equalsIgnoreCase("")) {
                    decEngagementCode = encDec.decrypt(encEngagementCode);
                }
                
                ClientEngagementDTO engagementDTO = manageEngagementsServiceImpl.viewEngagementByEngmtKey(decEngagementCode);
                model.addAttribute("engagementDTO", engagementDTO);
                
                String orgSchema = reportsAndDashboardService.getSchemaNameByEngKey(encEngagementCode);
                remediationDTO.getClientEngagementDTO().setEngagementCode(decEngagementCode);
                remediationDTO.setOrgSchema(orgSchema);
                model.addAttribute("remediateObj", remediationDTO);
                
                if(remediationDTO.getRemoveInstances()!=null && !"".equalsIgnoreCase(remediationDTO.getRemoveInstances())){
                    int registryId = remediationService.saveRiskRegistry(remediationDTO,userDto);
                    if(registryId>0){
                        model.addAttribute("deleteSuccessMessage", myProperties.getProperty("remediationstatus.update.success-message"));
                    }
                }else{
                    model.addAttribute("deleteErrMessage", myProperties.getProperty("remediationplanitem.delete.error-message"));
                }
                
                RemediationDTO planInfo = remediationService.planInfoByPlanKey(remediationDTO.getPlanID(), orgSchema);
                model.addAttribute("planInfo", planInfo);
                List<RemediationDTO> notesList = remediationService.planNotesByPlanKey(remediationDTO.getPlanID(), orgSchema);
                model.addAttribute("notesList", notesList);
                List<VulnerabilityDTO> planFindings = remediationService.findingsByPlanKey(remediationDTO.getPlanID(), orgSchema);
                model.addAttribute("planFindings", planFindings);
                
                redirectAttributes.addAttribute("redengkey", encEngagementCode);
                
                //Remediation status list
                List<MasterLookUpDTO> remStatusList = remediationService.fetchRemediationStatus();
                model.addAttribute("remStatusList", remStatusList);
                
                //Severity dropdown list
                List<SummaryDropDownModel> severityList = analystValidationService.analystSeverityList();
                model.addAttribute("severityList", severityList);
                
                //CLIENT USERS BASED ON CLIENT ORGANIZATION
                List<UserProfileDTO> clientUsers = remediationService.remediationOwnerUsers(ApplicationConstants.REMEDIATION_OWNER, decEngagementCode);
                model.addAttribute("clientUsers", clientUsers);
            }else{
                return "redirect:/logout.htm";
            }
                
        } catch (AppException e) {
            logger.error("Exception occurred : RemediationController: statusRiskRegistered:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        logger.info("End: RemediationController: statusRiskRegistered()");
        return "remediation/editremediationplan";
    }
    
    @RequestMapping(value = "/removeplan")
    public String removeRemediationPlan(HttpServletRequest request, HttpServletResponse response,
             Model model,RedirectAttributes redirectAttributes, 
             @ModelAttribute RemediationDTO remediationDTO,
             @ModelAttribute(value = "redengkey") String encEngagementCode) {
        logger.trace(TRACE_USER_ADD_REMEDIATION_PLAN);
        UserProfileDTO userDto = null;
        
        try{
             if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                Set<String> analystPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.REMEDIATION_MODULE);
                model.addAttribute("permissions", analystPermissionSet);
            
                logger.trace(TRACE_USER_ADD_REMEDIATION_PLAN);
            }
            
            if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.REMEDIATION_MODULE,
                        PermissionConstants.DELETE_REMEDIATION)) {
                String decEngagementCode = "";
                if (encEngagementCode != null && !encEngagementCode.equalsIgnoreCase("")) {
                    decEngagementCode = encDec.decrypt(encEngagementCode);
                }
                
                ClientEngagementDTO engagementDTO = manageEngagementsServiceImpl.viewEngagementByEngmtKey(decEngagementCode);
                model.addAttribute("engagementDTO", engagementDTO);
                
                String orgSchema = reportsAndDashboardService.getSchemaNameByEngKey(encEngagementCode);
                remediationDTO.getClientEngagementDTO().setEngagementCode(decEngagementCode);
                remediationDTO.setOrgSchema(orgSchema);
                model.addAttribute("remediateObj", remediationDTO);
                
                if(remediationDTO.getRemoveInstances()!=null && !"".equalsIgnoreCase(remediationDTO.getRemoveInstances())){
                    int deleteId = remediationService.deleteRemediationPlan(remediationDTO, userDto);
                    if(deleteId>0){
                        redirectAttributes.addFlashAttribute("addUpdateSuccessMessage", myProperties.getProperty("remediationplan.delete.success-message"));
                    }
                }else{
                    redirectAttributes.addFlashAttribute("deleteErrMessage", myProperties.getProperty("remediationplan.delete.error-message"));
                }
                
                redirectAttributes.addAttribute("redengkey", encEngagementCode);
            }else{
                return "redirect:/logout.htm";
            }
                
        } catch (AppException e) {
            logger.error("Exception occurred : RemediationController: removeRemediationPlan:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        logger.info("End: RemediationController: removeRemediationPlan()");
        return "redirect:/remediationplan.htm";
    }
    
    @RequestMapping(value = "/editassocfinding")
    public String editAssocFindingPage(HttpServletRequest request, HttpServletResponse response,
             Model model,RedirectAttributes redirectAttributes, 
             @ModelAttribute RemediationDTO remediationDTO,
             @ModelAttribute(value = "redengkey") String encEngagementCode) {
        logger.trace(TRACE_USER_ADD_REMEDIATION_PAGE);
        UserProfileDTO userDto = null;
        
        try{
             if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                Set<String> analystPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.REMEDIATION_MODULE);
                model.addAttribute("permissions", analystPermissionSet);
            
                logger.trace(TRACE_USER_ADD_REMEDIATION_PAGE);
            }
            
            if (permissionCheckHelper.checkUserSubMenuNotAdmin(userDto, PermissionConstants.REMEDIATION_MODULE)) {
                String decEngagementCode = "";
                if (encEngagementCode != null && !encEngagementCode.equalsIgnoreCase("")) {
                    decEngagementCode = encDec.decrypt(encEngagementCode);
                }
                
                ClientEngagementDTO engagementDTO = manageEngagementsServiceImpl.viewEngagementByEngmtKey(decEngagementCode);
                model.addAttribute("engagementDTO", engagementDTO);
                
                String orgSchema = reportsAndDashboardService.getSchemaNameByEngKey(encEngagementCode);
                
                if (model.containsAttribute("remediateObj")) {
                    remediationDTO = (RemediationDTO) model.asMap().get("remediateObj");
                }

                remediationDTO.getClientEngagementDTO().setEngagementCode(decEngagementCode);
                remediationDTO.setOrgSchema(orgSchema);
                
                RemediationDTO planInfo = remediationService.planInfoByPlanKey(remediationDTO.getPlanID(), orgSchema);
                model.addAttribute("planInfo", planInfo);
                
                VulnerabilityDTO findingObj = remediationService.viewVulnerabilityDetails(Long.parseLong(remediationDTO.getInstanceIdentifier()+""), orgSchema);
                model.addAttribute("findingObj", findingObj);
                
                RemediationDTO findingStatus = remediationService.planItemInfoByInstKey(remediationDTO);
                model.addAttribute("findingStatus", findingStatus);
                
                //Remediation status list
                List<MasterLookUpDTO> remStatusList = remediationService.fetchRemediationStatus();
                model.addAttribute("remStatusList", remStatusList);
                
                //Severity dropdown list
                List<SummaryDropDownModel> severityList = analystValidationService.analystSeverityList();
                model.addAttribute("severityList", severityList);
                
                //CLIENT USERS BASED ON CLIENT ORGANIZATION
                List<UserProfileDTO> clientUsers = remediationService.remediationOwnerUsers(ApplicationConstants.REMEDIATION_OWNER, decEngagementCode);
                model.addAttribute("clientUsers", clientUsers);
            }else{
                return "redirect:/logout.htm";
            }
                
        } catch (AppException e) {
            logger.error("Exception occurred : RemediationController: editAssocFindingPage:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        logger.info("End: RemediationController: editAssocFindingPage()");
        return "remediation/editassocfinding";
    }
    
    @RequestMapping(value = "/updateplanitemstatus")
    public String updatePlanItemStatus(HttpServletRequest request, HttpServletResponse response,
             Model model,RedirectAttributes redirectAttributes, 
             @ModelAttribute RemediationDTO remediationDTO,
             @ModelAttribute(value = "redengkey") String encEngagementCode) {
        logger.trace(TRACE_USER_UPDATE_PLAN_ITEM_STATUS);
        UserProfileDTO userDto = null;
        
        try{
             if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                Set<String> analystPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.REMEDIATION_MODULE);
                model.addAttribute("permissions", analystPermissionSet);
            
                logger.trace(TRACE_USER_UPDATE_PLAN_ITEM_STATUS);
            }
            
            if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.REMEDIATION_MODULE,
                        PermissionConstants.EDIT_FINDING)) {
                String decEngagementCode = "";
                if (encEngagementCode != null && !encEngagementCode.equalsIgnoreCase("")) {
                    decEngagementCode = encDec.decrypt(encEngagementCode);
                }
                
                ClientEngagementDTO engagementDTO = manageEngagementsServiceImpl.viewEngagementByEngmtKey(decEngagementCode);
                model.addAttribute("engagementDTO", engagementDTO);
                
                String orgSchema = reportsAndDashboardService.getSchemaNameByEngKey(encEngagementCode);
                remediationDTO.getClientEngagementDTO().setEngagementCode(decEngagementCode);
                remediationDTO.setOrgSchema(orgSchema);
                model.addAttribute("remediateObj", remediationDTO);
                
                int retVal = remediationService.updatePlanItemStatus(remediationDTO, userDto);
                
                if(retVal>0){
                    if(ApplicationConstants.DB_CONSTANT_RISK_REGISTERED.equalsIgnoreCase(remediationDTO.getStatusCode())){
                        int registryId = remediationService.saveRiskRegistry(remediationDTO,userDto);
                    }
                    model.addAttribute("deleteSuccessMessage", myProperties.getProperty("remediationstatus.update.success-message"));
                }
                
                RemediationDTO planInfo = remediationService.planInfoByPlanKey(remediationDTO.getPlanID(), orgSchema);
                model.addAttribute("planInfo", planInfo);
                List<RemediationDTO> notesList = remediationService.planNotesByPlanKey(remediationDTO.getPlanID(), orgSchema);
                model.addAttribute("notesList", notesList);
                List<VulnerabilityDTO> planFindings = remediationService.findingsByPlanKey(remediationDTO.getPlanID(), orgSchema);
                model.addAttribute("planFindings", planFindings);
                
                redirectAttributes.addAttribute("redengkey", encEngagementCode);
                
                //Remediation status list
                List<MasterLookUpDTO> remStatusList = remediationService.fetchRemediationStatus();
                model.addAttribute("remStatusList", remStatusList);
                
                //Severity dropdown list
                List<SummaryDropDownModel> severityList = analystValidationService.analystSeverityList();
                model.addAttribute("severityList", severityList);
                
                //CLIENT USERS BASED ON CLIENT ORGANIZATION
                List<UserProfileDTO> clientUsers = remediationService.remediationOwnerUsers(ApplicationConstants.REMEDIATION_OWNER, decEngagementCode);
                model.addAttribute("clientUsers", clientUsers);
            }else{
                return "redirect:/logout.htm";
            }
                
        } catch (AppException e) {
            logger.error("Exception occurred : RemediationController: statusRiskRegistered:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        logger.info("End: RemediationController: statusRiskRegistered()");
        return "remediation/editremediationplan";
    }
    
    @RequestMapping(value = "/updatenotifydate")
    public String updatePlanNotifyDate(HttpServletRequest request, HttpServletResponse response,
             Model model,RedirectAttributes redirectAttributes, 
             @ModelAttribute RemediationDTO remediationDTO,
             @ModelAttribute(value = "redengkey") String encEngagementCode) {
        logger.trace(TRACE_UPDATE_NOTIFY_DATE);
        UserProfileDTO userDto = null;
        
        try{
             if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                Set<String> analystPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.REMEDIATION_MODULE);
                model.addAttribute("permissions", analystPermissionSet);
            
                logger.trace(TRACE_UPDATE_NOTIFY_DATE);
            }
            
            if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.REMEDIATION_MODULE,
                        PermissionConstants.EDIT_REMEDIATION)) {
                String decEngagementCode = "";
                if (encEngagementCode != null && !encEngagementCode.equalsIgnoreCase("")) {
                    decEngagementCode = encDec.decrypt(encEngagementCode);
                }
                
                ClientEngagementDTO engagementDTO = manageEngagementsServiceImpl.viewEngagementByEngmtKey(decEngagementCode);
                model.addAttribute("engagementDTO", engagementDTO);
                
                String orgSchema = reportsAndDashboardService.getSchemaNameByEngKey(encEngagementCode);
                remediationDTO.getClientEngagementDTO().setEngagementCode(decEngagementCode);
                remediationDTO.setOrgSchema(orgSchema);
                model.addAttribute("remediateObj", remediationDTO);
                
                int updateId = remediationService.updateRemediationPlan(remediationDTO, userDto);
                int retVal = remediationService.updateRemediationNotifyDate(remediationDTO, userDto);
                
                if(retVal>0){
                    model.addAttribute("deleteSuccessMessage", myProperties.getProperty("remediationstatus.notify.success-message"));
                }
                
                RemediationDTO planInfo = remediationService.planInfoByPlanKey(remediationDTO.getPlanID(), orgSchema);
                model.addAttribute("planInfo", planInfo);
                List<RemediationDTO> notesList = remediationService.planNotesByPlanKey(remediationDTO.getPlanID(), orgSchema);
                model.addAttribute("notesList", notesList);
                List<VulnerabilityDTO> planFindings = remediationService.findingsByPlanKey(remediationDTO.getPlanID(), orgSchema);
                model.addAttribute("planFindings", planFindings);
                
                redirectAttributes.addAttribute("redengkey", encEngagementCode);
                
                //Remediation status list
                List<MasterLookUpDTO> remStatusList = remediationService.fetchRemediationStatus();
                model.addAttribute("remStatusList", remStatusList);
                
                //Severity dropdown list
                List<SummaryDropDownModel> severityList = analystValidationService.analystSeverityList();
                model.addAttribute("severityList", severityList);
                
                //CLIENT USERS BASED ON CLIENT ORGANIZATION
                List<UserProfileDTO> clientUsers = remediationService.remediationOwnerUsers(ApplicationConstants.REMEDIATION_OWNER, decEngagementCode);
                model.addAttribute("clientUsers", clientUsers);
            }else{
                return "redirect:/logout.htm";
            }
                
        } catch (AppException e) {
            logger.error("Exception occurred : RemediationController: statusRiskRegistered:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        logger.info("End: RemediationController: statusRiskRegistered()");
        return "remediation/editremediationplan";
    }
    
    @RequestMapping(value = "/updateassocfindstatus")
    public String updateAssocFindingStatus(HttpServletRequest request, HttpServletResponse response,
             Model model,RedirectAttributes redirectAttributes, 
             @ModelAttribute RemediationDTO remediationDTO,
             @ModelAttribute(value = "redengkey") String encEngagementCode) {
        logger.trace(TRACE_USER_UPDATE_PLAN_ITEM_STATUS);
        UserProfileDTO userDto = null;
        
        try{
             if(!commonUtil.refreshCheck(request, redirectAttributes, model)){
                return "redirect:/logout.htm";
            }
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                Set<String> analystPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.REMEDIATION_MODULE);
                model.addAttribute("permissions", analystPermissionSet);
            
                logger.trace(TRACE_USER_UPDATE_PLAN_ITEM_STATUS);
            }
            
            if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.REMEDIATION_MODULE,
                        PermissionConstants.EDIT_FINDING)) {
                String decEngagementCode = "";
                if (encEngagementCode != null && !encEngagementCode.equalsIgnoreCase("")) {
                    decEngagementCode = encDec.decrypt(encEngagementCode);
                }
                
                ClientEngagementDTO engagementDTO = manageEngagementsServiceImpl.viewEngagementByEngmtKey(decEngagementCode);
                model.addAttribute("engagementDTO", engagementDTO);
                
                String orgSchema = reportsAndDashboardService.getSchemaNameByEngKey(encEngagementCode);
                remediationDTO.getClientEngagementDTO().setEngagementCode(decEngagementCode);
                remediationDTO.setOrgSchema(orgSchema);
                model.addAttribute("remediateObj", remediationDTO);
                
                int retVal = remediationService.updatePlanItemStatus(remediationDTO, userDto);
                
                if(retVal>0){
                    if(ApplicationConstants.DB_CONSTANT_RISK_REGISTERED.equalsIgnoreCase(remediationDTO.getStatusCode())){
                        int registryId = remediationService.saveRiskRegistry(remediationDTO,userDto);
                    }
                    redirectAttributes.addFlashAttribute("updateSuccessMessage", myProperties.getProperty("remediationstatus.update.success-message"));
                }
                
                redirectAttributes.addAttribute("redengkey", encEngagementCode);
                
                //Remediation status list
                List<MasterLookUpDTO> remStatusList = remediationService.fetchRemediationStatus();
                model.addAttribute("remStatusList", remStatusList);
                
                redirectAttributes.addAttribute("redengkey", encEngagementCode);
                redirectAttributes.addFlashAttribute("remediationDTO",remediationDTO);
            }else{
                return "redirect:/logout.htm";
            }
                
        } catch (AppException e) {
            logger.error("Exception occurred : RemediationController: statusRiskRegistered:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        logger.info("End: RemediationController: statusRiskRegistered()");
        return "redirect:/editassocfinding.htm";
    }
}
