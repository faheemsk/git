/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.controller;

import com.optum.oss.constants.PermissionConstants;
import com.optum.oss.constants.SessionConstants;
import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.dto.ExceptionDTO;
import com.optum.oss.dto.ManageServiceUserDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.dto.VulnerabilityDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.PermissionCheckHelper;
import com.optum.oss.service.impl.AnalystValidationServiceImpl;
import com.optum.oss.service.impl.ManageEngagementsServiceImpl;
import com.optum.oss.service.impl.RemediationProjectManagerServiceImpl;
import com.optum.oss.util.CommonUtil;
import com.optum.oss.util.EncryptDecrypt;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author sathuluri
 */
@Controller
public class RemediationProjectManagerController {

    private static final Logger logger = Logger.getLogger(RemediationProjectManagerController.class);

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
    /*
     * End: Autowiring the required Class instances
     */
    /*
     START : LOG MESSAGES
     */
    private final String TRACE_USER_REMEDIATION_MANAGER_WORKLIST = "User accessed remediation project manager worklist";
    private final String TRACE_USER_SEARCH_REMEDIATION_MANAGER_WORKLIST = "User accessed remediation search project manager worklist";
    private final String INFO_USER_REMEDIATION_MANAGER_WORKLIST = "User accessed remediation project manager worklist";
    private final String INFO_USER_SEARCH_REMEDIATION_MANAGER_WORKLIST = "User accessed remediation search project manager worklist";
    private final String INFO_USER_VIEW_REMEDIATION = "User accessed view remediation details";
    private final String INFO_USER_UPDATE_REMEDIATION_FINDING_STATUS = "User accessed to update remediation finding status";
    /*
     END : LOG MESSAGES
     */

    /**
     * Method for fetch remediation project manager work list
     *
     * @param request
     * @param response
     * @param model
     * @param redirectAttributes
     * @return modelView
     */
    @RequestMapping(value = "/remediationworklist")
    public ModelAndView remediationWorklist(HttpServletRequest request, HttpServletResponse response, Model model,
            RedirectAttributes redirectAttributes) {

        logger.info(INFO_USER_REMEDIATION_MANAGER_WORKLIST);
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
                logger.trace(TRACE_USER_REMEDIATION_MANAGER_WORKLIST);

                remediationPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.REMEDIATE_PROJECT_MANAGER);
                modelView.addObject("permissions", remediationPermissionSet);
            }
            if (permissionCheckHelper.checkUserSubMenuNotAdmin(userDto, PermissionConstants.REMEDIATE_PROJECT_MANAGER)) {
                List<ManageServiceUserDTO> remediationWorklist = remediationProjectManagerService.fetchRemediationWorklist(userDto, manageServiceUserDTO);
                Map remediationMap = analystValidationService.engagementServicesListToMapConversion(remediationWorklist);
                modelView.addObject("engagementMap", remediationMap.get("engagementMap"));
                modelView.addObject("serviceMap", remediationMap.get("serviceMap"));
                modelView.addObject("organizationServieCount", remediationMap.get("organizationServieCount"));
                modelView.addObject("statusMap", remediationMap.get("statusMap"));
                modelView.setViewName("analyst/remediationworklist");
            } else {
                modelView.setViewName("redirect:/logout.htm");
            }
        } catch (AppException e) {
            logger.error("Exception occurred : RemediationProjectManagerController: remediationWorklist:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        logger.info("End: RemediationProjectManagerController: remediationWorklist()");
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
    @RequestMapping(value = "/searchremediation")
    public ModelAndView searchRemediationWorkList(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute(value = "searchremediation") ManageServiceUserDTO remediationData,RedirectAttributes redirectAttributes,Model model) {

        logger.info(INFO_USER_SEARCH_REMEDIATION_MANAGER_WORKLIST);
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
                    logger.trace(TRACE_USER_SEARCH_REMEDIATION_MANAGER_WORKLIST);
                    remediationPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.REMEDIATE_PROJECT_MANAGER);
                    modelView.addObject("permissions", remediationPermissionSet);
                }
                if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.REMEDIATE_PROJECT_MANAGER,
                        PermissionConstants.REMEDIATE)) {
                    List<ManageServiceUserDTO> remediationWorklist = remediationProjectManagerService.fetchRemediationWorklist(userDto, remediationData);
                    Map remediationMap = analystValidationService.engagementServicesListToMapConversion(remediationWorklist);
                    modelView.addObject("engagementMap", remediationMap.get("engagementMap"));
                    modelView.addObject("serviceMap", remediationMap.get("serviceMap"));
                    modelView.addObject("organizationServieCount", remediationMap.get("organizationServieCount"));
                    modelView.addObject("statusMap", remediationMap.get("statusMap"));
                    modelView.addObject("remediationData", remediationData);
                    modelView.setViewName("analyst/remediationworklist");
                } else {
                    modelView.setViewName("redirect:/logout.htm");
                }
            } else {
                modelView.setViewName("redirect:/logout.htm");
            }
        } catch (AppException e) {
            logger.error("Exception occurred : RemediationProjectManagerController: searchRemediationWorkList:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }
        logger.info("End: RemediationProjectManagerController: searchRemediationWorkList()");
        return modelView;
    }

    /**
     * Method for view remediation with findings work list
     *
     * @param request
     * @param response
     * @param engagementCode
     * @param redirectEngagementCode
     * @param model
     * @param redirectAttributes
     * @return modelView
     */
    @RequestMapping(value = "/viewremediation")
    public String viewRemediation(HttpServletRequest request, HttpServletResponse response,
             Model model,RedirectAttributes redirectAttributes, @ModelAttribute(value = "redengkey") String redirectEngagementCode) {

        logger.info(INFO_USER_VIEW_REMEDIATION);
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
                logger.trace(INFO_USER_VIEW_REMEDIATION);
                remediationPermissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.REMEDIATE_PROJECT_MANAGER);
                model.addAttribute("permissions", remediationPermissionSet);
            }           
            if (redirectEngagementCode != null && !StringUtils.isEmpty(redirectEngagementCode)) {
                engagementCode = redirectEngagementCode;
            } else if (request.getParameter("engkey") != null && !StringUtils.isEmpty(request.getParameter("engkey"))) {
                engagementCode = request.getParameter("engkey");
            }      
            if(engagementCode != null && !StringUtils.isEmpty(engagementCode))
            {
            if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.REMEDIATE_PROJECT_MANAGER,
                    PermissionConstants.REMEDIATE)) {
                String decEngagementCode = "";
                if (engagementCode != null && !engagementCode.equalsIgnoreCase("")) {
                    decEngagementCode = encDec.decrypt(engagementCode);
                }
                ClientEngagementDTO engagementDTO = manageEngagementsServiceImpl.viewEngagementByEngmtKey(decEngagementCode);
                model.addAttribute("engagementDTO", engagementDTO);

                List<VulnerabilityDTO> findingList = remediationProjectManagerService.findingList(decEngagementCode);
                model.addAttribute("findingList", findingList);
            } else {
                return "redirect:/logout.htm";
            }
            }else{
                return "redirect:/logout.htm";
            }
        } catch (AppException e) {
            logger.error("Exception occurred : RemediationProjectManagerController: viewRemediation:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        logger.info("End: RemediationProjectManagerController: viewRemediation()");
        return "analyst/viewremediation";
    }

    @InitBinder("updatefindingstatus")
    protected void modifyFindingStatus(WebDataBinder binder)
            throws Exception {
        binder.setAllowedFields(new String[]{"clientEngagementDTO.encEngagementCode",
            "statusCode"});
    }

    /**
     * Method for update finding status
     *
     * @param request
     * @param response
     * @param updateFindingStatus
     * @param redirectAttributes
     * @param model
     * @return modelView
     */
    @RequestMapping(value = "/updatefindingstatus", method = RequestMethod.POST)
    public String updateFindingStatus(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute(value = "updatefindingstatus") VulnerabilityDTO updateFindingStatus, RedirectAttributes redirectAttributes, Model model) {

        logger.info(INFO_USER_UPDATE_REMEDIATION_FINDING_STATUS);
        UserProfileDTO userDto = new UserProfileDTO();
        try {
            HttpSession session = request.getSession();
            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return "redirect:/logout.htm";
            }
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                logger.trace(INFO_USER_VIEW_REMEDIATION);
            }
            if (permissionCheckHelper.checkUserPermissionNotAdmin(userDto, PermissionConstants.REMEDIATE_PROJECT_MANAGER,
                    PermissionConstants.REMEDIATE)) {
                int retVal = remediationProjectManagerService.updateFindingStatus(updateFindingStatus);
                if (retVal > 0) {
                    redirectAttributes.addFlashAttribute("successMessage",
                            myProperties.getProperty("remediation.finding.status.update-success-message"));
                }
                redirectAttributes.addFlashAttribute("redengkey", updateFindingStatus.getClientEngagementDTO().getEncEngagementCode());
            } else {
                return "redirect:/logout.htm";
            }
        } catch (AppException e) {
            logger.error("Exception occurred : RemediationProjectManagerController: updateFindingStatus:" + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            return "/exception";
        }
        logger.info("End: RemediationProjectManagerController: updateFindingStatus()");
        return "redirect:/viewremediation.htm";
    }
}
