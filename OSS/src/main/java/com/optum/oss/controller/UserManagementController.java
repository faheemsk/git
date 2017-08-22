/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.constants.PermissionConstants;
import com.optum.oss.constants.SessionConstants;
import com.optum.oss.dto.ExceptionDTO;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.OrganizationDTO;
import com.optum.oss.dto.PermissionGroupDTO;
import com.optum.oss.dto.RoleDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.dto.Views;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.PermissionCheckHelper;
import com.optum.oss.service.impl.LoginServiceImpl;
import com.optum.oss.service.impl.MasterLookUpServiceImpl;
import com.optum.oss.service.impl.UserManagementServiceImpl;
import com.optum.oss.util.CommonUtil;
import com.optum.oss.util.EncryptDecrypt;
import com.optum.oss.util.LoggerUtil;
import com.optum.oss.validators.UserManagementValidator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author sbhagavatula
 */
@Controller
public class UserManagementController {

    private static final Logger logger = Logger.getLogger(UserManagementController.class);

    /*
     Autowiring the required Class instances
     */
    @Resource(name = "myProperties")
    private Properties myProperties;

    @Autowired
    private UserManagementServiceImpl userManagementServiceImpl;
    @Autowired
    private MasterLookUpServiceImpl masterLookUpServiceImpl;
    @Autowired
    private EncryptDecrypt encryptDecrypt;
    @Autowired
    private LoginServiceImpl loginService;

    @Autowired
    PermissionCheckHelper permissionCheckHelper;

    @Autowired
    private LoggerUtil auditLogger;

    @Autowired
    private UserManagementValidator userValidator;
    @Autowired
    private CommonUtil commonUtil;
    /*
     START : LOG MESSAGES
     */
    private final String TRACE_USER_LIST = "User accessed userlist";
    private final String TRACE_USER_ADD_USER = "User accessed adduser";
    private final String TRACE_USER_UPDATE_USER = "User accessed updateuser";
    private final String TRACE_USER_EDIT_USER = "User accessed updateuser";
    private final String TRACE_USER_SAVE_USER = "User accessed saveuser";
    private final String TRACE_USER_VIEW_USER = "User accessed viewuser";
    private final String TRACE_USER_FETCH_ORG_USERTYPE = "User accessed fetchOrgByUserType";
    private final String TRACE_USER_FETCH_PERMISSION_GROUPS = "User accessed fetch permission groups";
    private final String TRACE_USER_VAL_USERNAME = "User accessed validate username";
    /*
     END: LOG MESSAGES

     /*
     Autowiring the required Class instances
     */

    @RequestMapping(value = "/userlist")
    public ModelAndView userList(final HttpServletRequest request,
            final HttpServletResponse response,
            RedirectAttributes redirectAttributes, Model model) {
        HttpSession session = request.getSession();
        ModelAndView modelView = new ModelAndView();
        List<UserProfileDTO> users = null;
        try {
            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return new ModelAndView("redirect:/logout.htm");
            }

            UserProfileDTO sessionUserDTO = new UserProfileDTO();
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                sessionUserDTO = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", sessionUserDTO.getEmail());
                MDC.put("ip", sessionUserDTO.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(sessionUserDTO.getEmail(),sessionUserDTO.getUserMACAddress(), "userList page"));

                logger.trace(TRACE_USER_LIST);
                Set<String> permissionSet = sessionUserDTO.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_PARTNER_INFO_MENU);
                modelView.addObject("permissions", permissionSet);
            }
            //FETCH USERS LIST
            users = userManagementServiceImpl.fetchUserList(0);
            if (permissionCheckHelper.checkUserMenu(sessionUserDTO, PermissionConstants.MANAGE_PARTNER_INFO_MENU)) {
                modelView.addObject("userList", users);
                modelView.setViewName("usermgmt/manageusers");
            } else {
                modelView.setViewName("redirect:/logout.htm");
            }

        } catch (AppException e) {
            logger.debug("Exceptionoccured : userList : " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }

        return modelView;
    }

    /**
     *
     * @param request
     * @param response
     * @param redirectAttributes
     * @param model
     * @return
     */
    @RequestMapping(value = "/adduser")
    public String renderAddUser(final HttpServletRequest request,
            final HttpServletResponse response,
            RedirectAttributes redirectAttributes, Model model) {
//        ModelAndView modelView = new ModelAndView();
        HttpSession session = request.getSession();
        UserProfileDTO userDto = new UserProfileDTO();
        String retString = "";
        try {
            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return "redirect:/logout.htm";
            }

            if (model.containsAttribute("userobj")) {
                userDto = (UserProfileDTO) model.asMap().get("userobj");
                model.addAttribute("userobj", userDto);
                List<OrganizationDTO> organizationList = userManagementServiceImpl.fetchOrgListByUserType(userDto.getUserTypeKey());
                model.addAttribute("organizationList", organizationList);
//                List<PermissionGroupDTO> permissionGroupMap = userManagementServiceImpl.fetchPermissionGroupByRole(CommonUtil.convertToCommaDelimited(userDto.getSelectedRole()));
//                model.addAttribute("permissionGroupMap", permissionGroupMap);
                
                Map<String, List<PermissionGroupDTO>> permissionGroupMap = userManagementServiceImpl.fetchPermissionMapGroupByRole(CommonUtil.convertToCommaDelimited(userDto.getSelectedRole()));
                model.addAttribute("permissionGroupMap", permissionGroupMap);

                // FETCH ROLES BY USER TYPE KEY
                List<RoleDTO> roleList = userManagementServiceImpl.fetchRolesList(userDto.getUserTypeKey());
                model.addAttribute("roleList", roleList);

                //STORING THE ROLES IN A LIST 
                List<String> rolesCoversionToString = new ArrayList<>();
                for (RoleDTO roleDTO : roleList) {
                    rolesCoversionToString.add(roleDTO.getAppRoleKey() + "");
                }
                
                // CONVERTING THE LIST TO THE STRING ARRAY
                String[] userTypeRoles = (String[]) rolesCoversionToString.toArray(new String[rolesCoversionToString.size()]);

                //CONVERTING STRING ARRAY TO ARRAY LIST
                List<String> selectedRolesList = new ArrayList<String>();
                for (String role : userDto.getSelectedRole()) {
                    if (role != null && !role.equalsIgnoreCase("")) {
                        selectedRolesList.add(role);
                    }
                }

                model.addAttribute("selectedRolesList", selectedRolesList);

                //REMOVING ADDED ROLES FROM THE ALL ROLES LIST 
                Iterator<RoleDTO> itr = roleList.iterator();
                while (itr.hasNext()) {
                    RoleDTO roleDTO = itr.next();
                    if (selectedRolesList.contains(roleDTO.getAppRoleKey() + "")) {
                        itr.remove();
                    }
                }
                Map<String, List<PermissionGroupDTO>> permissionGroupMap1 = userManagementServiceImpl.fetchPermissionMapGroupByRole(CommonUtil.convertToCommaDelimited(userTypeRoles));
                model.addAttribute("rolePermissionGroupMap", permissionGroupMap1);

            } else {
                model.addAttribute("userobj", userDto);
            }

            //FETCH USER TYPES
            List<MasterLookUpDTO> userTypes = masterLookUpServiceImpl.fetchMasterLookUpEntitiesByEntityType(ApplicationConstants.DB_CONSTANT_USER_TYPE);
            model.addAttribute("userTypes", userTypes);

            //FETCH ALL THE ROLES
            //List<RoleDTO> roleList = userManagementServiceImpl.fetchRolesList(16);
            userDto = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
            MDC.put("user", userDto.getEmail());
            MDC.put("ip", userDto.getUserMACAddress());
//            logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User accessed renderAddUser page"));
            logger.trace(TRACE_USER_ADD_USER);
            if (permissionCheckHelper.checkUserPermission(userDto, PermissionConstants.MANAGE_PARTNER_INFO_MENU, PermissionConstants.ADD_USER)) {
                //model.addAttribute("roleList", roleList);
//                modelView.setViewName("usermgmt/adduser");
                retString = "usermgmt/adduser";

            } else {
//                modelView.setViewName("redirect:/logout.htm");
                retString = "redirect:/logout.htm";
            }

            //  modelView.addObject("roleList", roleList);
        } catch (AppException e) {
            logger.debug("Exceptionoccured : renderAddUser : " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
//            return new ModelAndView("/exception", "exceptionBean", exception);
            model.addAttribute("exceptionBean", exception);
            retString = "/exception";
        }
        //  modelView.setViewName("usermgmt/adduser");
        return retString;
    }

    @InitBinder("userobj")
    protected void saveUserProfileBinder(WebDataBinder binder)
            throws Exception {
        binder.setAllowedFields(new String[]{"userTypeKey",
            "organizationKey",
            "firstName",
            "middleName",
            "lastName",
            "jobTitleName",
            "email",
            "telephoneNumber",
            "selectedRole",
            "statusComment",
            "rowStatusKey",
                "appRoleKey",
                "delRoleKey",
                "department"
        });
    }

    @RequestMapping(value = "/saveuser", method = RequestMethod.POST)
    public String saveUserProfile(@ModelAttribute(value = "userobj") UserProfileDTO userProfileDTO, final HttpServletRequest request,
            final HttpServletResponse response, RedirectAttributes redirectAttributes, BindingResult bindResult,
            Model model) {
        HttpSession session = request.getSession();
        String retString = "";
//        ModelAndView modelView = new ModelAndView();
        try {
            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return "redirect:/logout.htm";
            }

            userValidator.validate(userProfileDTO, bindResult);
            if (bindResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("userobj", userProfileDTO);
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userobj", bindResult);
                return "redirect:/adduser.htm";
            }

            UserProfileDTO sessionUserDTO = new UserProfileDTO();
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                sessionUserDTO = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", sessionUserDTO.getEmail());
                MDC.put("ip", sessionUserDTO.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(sessionUserDTO.getEmail(),sessionUserDTO.getUserMACAddress(), " User accessed saveUserProfile page"));
                logger.trace(TRACE_USER_SAVE_USER);
            }
            //ASSIGNED ROLES FOR USER
            String[] selectedRoles = request.getParameterValues("selectedRole");

            //INSERT USER DETAILS AND USER APPLICATION ROLES
            int retVal = userManagementServiceImpl.saveUserDetails(userProfileDTO, sessionUserDTO, selectedRoles);
            if (retVal > 0) {
                redirectAttributes.addFlashAttribute("statusMessage",
                        myProperties.getProperty("useradd.success-message"));
                retString = "redirect:userlist.htm";
//                modelView.setViewName("redirect:userlist.htm");
            } else {
                if (retVal == -2) {
                    /*Redirecting to error page  when opendj is disconnected while user creation*/
                    loginService.updateUserSessionInfoByUserID(ApplicationConstants.DB_INDICATOR_DELETE, sessionUserDTO);
                    return "/error";
                } else {
                    ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"));
//                return new ModelAndView("/exception", "exceptionBean", exception);
                    retString = "/exception";
                }

            }
        } catch (AppException e) {
            logger.debug("Exceptionoccured : saveUser : " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
//            return new ModelAndView("/exception", "exceptionBean", exception);
            retString = "/exception";
        }
        return retString;
    }

    @InitBinder("upduserobj")
    protected void updateUserProfileBinder(WebDataBinder binder)
            throws Exception {
        binder.setAllowedFields(new String[]{"userProfileKey",
            "userTypeKey",
            "encUserProfileKey",
            "organizationKey",
            "firstName",
            "middleName",
            "lastName",
            "jobTitleName",
            "email",
            "telephoneNumber",
            "selectedRole",
            "statusComment",
            "rowStatusKey",
                "appRoleKey",
                "delRoleKey",
                "department"
        });
    }

    @RequestMapping(value = "/edituser", method = RequestMethod.POST)
    public String updateUserProfile(@ModelAttribute(value = "upduserobj") UserProfileDTO userProfileDTO, final HttpServletRequest request,
            final HttpServletResponse response, RedirectAttributes redirectAttributes, BindingResult bindResult,
            Model model) {
        HttpSession session = request.getSession();
//        ModelAndView modelView = new ModelAndView();
        String retString = "";
        try {
            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return "redirect:/logout.htm";
            }

            userValidator.validate(userProfileDTO, bindResult);
            if (bindResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("upduserobj", userProfileDTO);
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.upduserobj", bindResult);
                redirectAttributes.addAttribute("userProfileKey", userProfileDTO.getEncUserProfileKey());
                return "redirect:/updateuser.htm";
            }
            UserProfileDTO sessionUserDTO = new UserProfileDTO();
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                sessionUserDTO = (UserProfileDTO) session.getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", sessionUserDTO.getEmail());
                MDC.put("ip", sessionUserDTO.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(sessionUserDTO.getEmail(),sessionUserDTO.getUserMACAddress(), " User accessed validate verify user page"));
                logger.trace(TRACE_USER_EDIT_USER);
            }
            //ASSIGNED ROLES FOR USER
            String[] selectedRoles = request.getParameterValues("selectedRole");

            //UPDATE USER DETAILS AND APPLICATION ROLES
            int retVal = userManagementServiceImpl.updateUserDetails(userProfileDTO, sessionUserDTO, selectedRoles);
            if (retVal > 0) {
                redirectAttributes.addFlashAttribute("statusMessage",
                        myProperties.getProperty("userupdate.success-message"));
//                modelView.setViewName("redirect:userlist.htm");
                retString = "redirect:userlist.htm";
            } else {
                ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), ApplicationConstants.EXCEPTION_TYPE_ERROR);
//                return new ModelAndView("/exception", "exceptionBean", exception);
                retString = "/exception";
            }
        } catch (AppException e) {
            logger.debug("Exceptionoccured : saveUser : " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
//            return new ModelAndView("/exception", "exceptionBean", exception);
            retString = "/exception";
        }
        return retString;
    }

    @RequestMapping(value = "/updateuser")
    public String renderUpdateUser(final HttpServletRequest request,
            final HttpServletResponse response, Model model,
            RedirectAttributes redirectAttributes) {
//        ModelAndView modelView = new ModelAndView();
        UserProfileDTO userProfileObj = null;
        HttpSession session = request.getSession();
        UserProfileDTO userDto = new UserProfileDTO();
        String retString = "";
        String userProfileKey = "";
        List<String> selectedRolesList=null;
         long userTypeKey=0;
        try {
            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return "redirect:/logout.htm";
            }

            if (request.getParameter("userProfileKey") != null) {
                userProfileKey = request.getParameter("userProfileKey");
            } else {
                if (model.containsAttribute("userProfileKey")) {
                    userProfileKey = (String) model.asMap().get("userProfileKey");
                } else {

                    return retString = "redirect:/logout.htm";
                }
            }

            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
                // permissionSet = userDto.getSubMenuPermissionMap().get(PermissionConstants.MANAGE_ROLES_SUB_MENU);
//               logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User accessed renderUpdateUser page")); 
                logger.trace(TRACE_USER_UPDATE_USER);
            }
            //DECRYPTING USER PROFILE KEY
            String decUserProfileKey = encryptDecrypt.decrypt(userProfileKey + "");
            long decUserKey = Long.parseLong(decUserProfileKey);

            //FETCH USER TYPES
            List<MasterLookUpDTO> userTypes = masterLookUpServiceImpl.fetchMasterLookUpEntitiesByEntityType(ApplicationConstants.DB_CONSTANT_USER_TYPE);
            model.addAttribute("userTypes", userTypes);

            //FETCH ALL THE ROLES
//            List<RoleDTO> roleList = userManagementServiceImpl.fetchRolesList();
//            model.addAttribute("roleList", roleList);
            
            //FETCH USER DETAILS BY USER PROFILE KEY
            if (model.containsAttribute("upduserobj")) {
                userProfileObj = (UserProfileDTO) model.asMap().get("upduserobj");
                model.addAttribute("editUserProfile", userProfileObj);
                //FETCH USER ROLES BY USER PROFILE KEY

//                List<PermissionGroupDTO> permissionGroupMap = userManagementServiceImpl.fetchPermissionGroupByRole(CommonUtil.convertToCommaDelimited(userProfileObj.getSelectedRole()));
//                model.addAttribute("permissionGroupMap", permissionGroupMap);
                
                
            Map<String, List<PermissionGroupDTO>> permissionGroupMap = userManagementServiceImpl.fetchPermissionMapGroupByRole(CommonUtil.convertToCommaDelimited(userProfileObj.getSelectedRole()));
            model.addAttribute("permissionGroupMap", permissionGroupMap);
                //CONVERTING STRING ARRAY TO ARRAY LIST
                selectedRolesList = new ArrayList<String>();
                if (userProfileObj.getSelectedRole() != null) {
                    for (String role : userProfileObj.getSelectedRole()) {
                        if (role != null && !role.equalsIgnoreCase("")) {
                            selectedRolesList.add(role);
                        }
                    }
                }
                
               model.addAttribute("selectedRolesList", selectedRolesList);
                
                //FETCH ORGANIZATION LIST BY USER TYPE
                userTypeKey = userProfileObj.getUserTypeKey();
                List<OrganizationDTO> organizationList = userManagementServiceImpl.fetchOrgListByUserType(userTypeKey);
                model.addAttribute("organizationList", organizationList);
                model.addAttribute("modelFlag", 1);

            } else {
                userProfileObj = userManagementServiceImpl.viewUserProfile(decUserKey);
                model.addAttribute("editUserProfile", userProfileObj);
                //FETCH USER ROLES BY USER PROFILE KEY
                Map<Integer, String> userRolesMap = userManagementServiceImpl.fetchRolesByUser(decUserKey);
//                model.addAttribute("userRolesMap", userRolesMap);
                
                Set<Integer> s = userRolesMap.keySet();
                String[] selectedRoles = new String[s.size()];
                selectedRolesList=new ArrayList<String>();
                int count = 0;
                for (Integer role : s) {
                    selectedRoles[count] = role + "";
                    selectedRolesList.add(role+"");
                    count++;
                }
                
                 
                 model.addAttribute("selectedRolesList", selectedRoles);
                 
           Map<String, List<PermissionGroupDTO>> permissionGroupMap = userManagementServiceImpl.fetchPermissionMapGroupByRole(CommonUtil.convertToCommaDelimited(selectedRoles));
            model.addAttribute("permissionGroupMap", permissionGroupMap);

                //FETCH ORGANIZATION LIST BY USER TYPE
                 userTypeKey = userProfileObj.getUserTypeObj().getMasterLookupKey();
                List<OrganizationDTO> organizationList = userManagementServiceImpl.fetchOrgListByUserType(userTypeKey);
                model.addAttribute("organizationList", organizationList);
                
                 userTypeKey = userProfileObj.getUserTypeObj().getMasterLookupKey();
                //FETCH PERMISSION GROUPS BY USER PROFILE KEY
//                Map<Integer, PermissionGroupDTO> permissionGroupMap = userManagementServiceImpl.fetchPermissionGroupsByUserID(decUserKey);
//                model.addAttribute("permissionGroupMap", permissionGroupMap);
            }
            
       
             //FETCH ALL THE ROLES BY USERTYPE KEY
            List<RoleDTO> roleList = userManagementServiceImpl.fetchRolesList(userTypeKey);
            model.addAttribute("roleList", roleList);
            List<String> rolesCoversionToString = new ArrayList<>();
            for (RoleDTO roleDTO : roleList) {
                rolesCoversionToString.add(roleDTO.getAppRoleKey() + "");
            }
            //REMOVING ADDED ROLES FROM THE ALL ROLES LIST
            Iterator<RoleDTO> itr = roleList.iterator();
            while (itr.hasNext()) {
                RoleDTO roleDTO = itr.next();
                if (selectedRolesList.contains(roleDTO.getAppRoleKey()+"")) {
                    itr.remove();
                }
            }
            String[] userTypeRoles = (String[]) rolesCoversionToString.toArray(new String[rolesCoversionToString.size()]);

            Map<String, List<PermissionGroupDTO>> permissionGroupMap1 = userManagementServiceImpl.fetchPermissionMapGroupByRole(CommonUtil.convertToCommaDelimited(userTypeRoles));
            model.addAttribute("rolePermissionGroupMap", permissionGroupMap1);
            
            if (permissionCheckHelper.checkUserPermission(userDto, PermissionConstants.MANAGE_PARTNER_INFO_MENU, PermissionConstants.UPDATE_USER)) {
//                modelView.setViewName("usermgmt/updateuser");
                retString = "usermgmt/updateuser";
            } else {
//                modelView.setViewName("redirect:/logout.htm");
                retString = "redirect:/logout.htm";
            }
        } catch (AppException e) {
            logger.debug("Exceptionoccured : viewUser : " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
//            return new ModelAndView("/exception", "exceptionBean", exception);
            model.addAttribute("exceptionBean", exception);
            retString = "/exception";
        }

        //   modelView.setViewName("usermgmt/updateuser");
        return retString;
    }

    @RequestMapping(value = "/viewuser")
    public ModelAndView viewUserProfile(final HttpServletRequest request,
            final HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) {
        ModelAndView modelView = new ModelAndView();
        UserProfileDTO userProfileObj = null;
        String userProfileKey = "";
        try {
            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return new ModelAndView("redirect:/logout.htm");
            }

            //CHECK WHETHER THE USER PROFILE KEY IS NULL
            if (request.getParameter("userProfileKey") != null && !request.getParameter("userProfileKey").equals("")) {
                userProfileKey = request.getParameter("userProfileKey");
            } else {
                modelView.setViewName("redirect:/logout.htm");
                return modelView;
            }

            if (!"".equalsIgnoreCase(userProfileKey)) {
                String decUserProfileKey = encryptDecrypt.decrypt(userProfileKey + "");
                long decUserKey = Long.parseLong(decUserProfileKey);

                userProfileObj = userManagementServiceImpl.viewUserProfile(decUserKey);
                modelView.addObject("viewUserProfile", userProfileObj);

                Map<Integer, String> userRolesMap = userManagementServiceImpl.fetchRolesByUser(decUserKey);
                modelView.addObject("userRolesMap", userRolesMap);

                Map<Integer, PermissionGroupDTO> permissionGroupMap = userManagementServiceImpl.fetchPermissionGroupsByUserID(decUserKey);
                UserProfileDTO userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
//                 logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User accessed viewUserProfile page"));
                logger.trace(TRACE_USER_VIEW_USER);
                if (permissionCheckHelper.checkUserPermission(userDto, PermissionConstants.MANAGE_PARTNER_INFO_MENU,
                        PermissionConstants.VIEW_USER)) {
                    modelView.addObject("permissionGroupMap", permissionGroupMap);
                    modelView.setViewName("usermgmt/viewuser");
                } else {
                    modelView.setViewName("redirect:/logout.htm");
                }

            } else {
                ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), ApplicationConstants.EXCEPTION_TYPE_ERROR);
                return new ModelAndView("/exception", "exceptionBean", exception);
            }
        } catch (AppException e) {
            logger.debug("Exceptionoccured : viewUser : " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            return new ModelAndView("/exception", "exceptionBean", exception);
        }

        return modelView;
    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/fetchOrgByUserType")
    public @ResponseBody
    List<OrganizationDTO> fetchOrgByUserType(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("userTypeKey") long userTypeKey) {
        List<OrganizationDTO> userTypes = null;
        try {
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                UserProfileDTO userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User accessedfetchOrgBy UserType page"));
                logger.trace(TRACE_USER_FETCH_ORG_USERTYPE);
            }
            userTypes = userManagementServiceImpl.fetchOrgListByUserType(userTypeKey);

        } catch (Exception e) {
            logger.debug("Exceptionoccured : fetchOrgByUserType : " + e.getMessage());
        }
        return userTypes;
    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/fetchpermissiongroups")
    public @ResponseBody
    List<PermissionGroupDTO> fetchPermissionGroupByRole(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("appRoleKey") String appRoleKeys) {
        List<PermissionGroupDTO> permissionRoleGrps = null;
        try {
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                UserProfileDTO userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);

//                logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User accessed fetchPermissionGroupByRole page"));
                logger.trace(TRACE_USER_FETCH_PERMISSION_GROUPS);
            }
            permissionRoleGrps = new ArrayList<>();
            permissionRoleGrps = userManagementServiceImpl.fetchPermissionGroupByRole(appRoleKeys);

        } catch (Exception e) {
            logger.debug("Exceptionoccured : fetchPermissionGroupByRole : " + e.getMessage());
        }
        return permissionRoleGrps;
    }

    @RequestMapping(value = "/validateusername")
    public @ResponseBody
    String validateUserName(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("userID") String userID) {
        String message = "";
        try {
            if (request.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                UserProfileDTO userDto = (UserProfileDTO) request.getSession().getAttribute(SessionConstants.USER_SESSION);
                MDC.put("user", userDto.getEmail());
                MDC.put("ip", userDto.getUserMACAddress());
//                logger.trace(auditLogger.prepareLog(userDto.getEmail(),userDto.getUserMACAddress(), " User accessed validate UserName page"));
                logger.trace(TRACE_USER_VAL_USERNAME);
            }
            UserProfileDTO userDto = loginService.fetchUserDetailsByUserName(userID);
            if (userDto.getUserProfileKey() != 0) {
                message = myProperties.getProperty("useradd.email-exists");
            } else {
                message = "No";
            }
        } catch (Exception e) {
            logger.debug("Exceptionoccured : fetchPermissionGroupByRole : " + e.getMessage());
        }
        return message;
    }

    @RequestMapping(value = "/getUserDetails", method = RequestMethod.POST)
    public String getUserDetails(@ModelAttribute(value = "upduserobj") UserProfileDTO userProfileDTO, final HttpServletRequest request,
            final HttpServletResponse response, RedirectAttributes redirectAttributes, BindingResult bindResult,
            Model model) {
        String retString = "";
        String selectedRoles[] = null;
        List<String> selectedRolesList = new ArrayList<String>();
        long decUserKey = 0;
        boolean addValue = false;
        String userProfileKey = "0";
        try {

            if (!commonUtil.refreshCheck(request, redirectAttributes, model)) {
                return "redirect:/logout.htm";
            }

             //FETCH USER TYPES 
            List<MasterLookUpDTO> userTypes = masterLookUpServiceImpl.fetchMasterLookUpEntitiesByEntityType(ApplicationConstants.DB_CONSTANT_USER_TYPE);
            model.addAttribute("userTypes", userTypes);

            //FETCH ALL THE ROLES BY USER TYPES 
            List<RoleDTO> roleList = userManagementServiceImpl.fetchRolesList(userProfileDTO.getUserTypeKey());
            model.addAttribute("roleList", roleList);

            //FETCH ASSOCIATED ORGANIZATIONS TYPES  BY USER TYPES 
            List<OrganizationDTO> orgList = userManagementServiceImpl.fetchOrgListByUserType(userProfileDTO.getUserTypeKey());
            model.addAttribute("organizationList", orgList);

            //LOGIC FOR COMBINATION CHECKING
            String s = myProperties.getProperty(userProfileDTO.getAppRoleKey() + "");
            if (s != null && userProfileDTO.getSelectedRole() != null && s.length() > 0) {
                String roles[] = s.split(",");
                List<String> roleComboList = new ArrayList<String>(Arrays.asList(roles));
                List<String> selectedRolesListCheck = new ArrayList<String>(Arrays.asList(userProfileDTO.getSelectedRole()));
                selectedRolesListCheck.removeAll(roleComboList);
                if (selectedRolesListCheck.size() > 0) {
                    addValue = false;
                } else {
                    addValue = true;

                }

            }

            //LOGIC FOR ADDING AND REMOVING ROLES BASED ON COMBINATION
            if (addValue) {
                if (userProfileDTO.getAppRoleKey() > 0) {
                    if (userProfileDTO.getSelectedRole() != null) {
                        selectedRolesList = new ArrayList<String>(Arrays.asList(userProfileDTO.getSelectedRole()));
                        if (selectedRolesList.contains(userProfileDTO.getAppRoleKey() + "")) {
                            roleList.remove(userProfileDTO.getAppRoleKey() + "");
                        } else {
                            selectedRolesList.add(userProfileDTO.getAppRoleKey() + "");

                        }
                    } else {
                        selectedRolesList.add(userProfileDTO.getAppRoleKey() + "");
                    }
                }
            } else {

                if (userProfileDTO.getAppRoleKey() > 0) {
                    if (userProfileDTO.getSelectedRole() != null) {
                        for (String rolesOne : userProfileDTO.getSelectedRole()) {
                            selectedRolesList.add(rolesOne);
                            model.addAttribute("roleDuplicateError",  myProperties.getProperty("role.invalid.combination"));
                        }
                    } else {
                        selectedRolesList.add(userProfileDTO.getAppRoleKey() + "");
                    }
                }
            }

            //DELETE ROLE BY APP ROLE KEY
            if (userProfileDTO.getDelRoleKey() > 0) {
                selectedRolesList = new ArrayList<String>(Arrays.asList(userProfileDTO.getSelectedRole()));
                selectedRolesList.remove(userProfileDTO.getDelRoleKey() + "");
            }

            //CONVERTING SELECTED ROLES LIST TO ARRAY
            if (selectedRolesList != null && selectedRolesList.size() > 0) {
                selectedRoles = (String[]) selectedRolesList.toArray(new String[selectedRolesList.size()]);
            }


            model.addAttribute("selectedRolesList", selectedRoles);
            Map<String, List<PermissionGroupDTO>> permissionGroupMap = userManagementServiceImpl.fetchPermissionMapGroupByRole(CommonUtil.convertToCommaDelimited(selectedRoles));
            model.addAttribute("permissionGroupMap", permissionGroupMap);
            
            List<String> rolesCoversionToString = new ArrayList<>(); 
            for (RoleDTO roleDTO : roleList) {
                rolesCoversionToString.add(roleDTO.getAppRoleKey() + "");
            }

            //REMOVING ADDED ROLES FROM THE ALL ROLES LIST
            Iterator<RoleDTO> itr = roleList.iterator();
            while (itr.hasNext()) {
                RoleDTO roleDTO = itr.next();
                if (selectedRolesList.contains(roleDTO.getAppRoleKey() + "")) {
                    itr.remove();
                }
            }

            String[] userTypeRoles = (String[]) rolesCoversionToString.toArray(new String[roleList.size()]);
            Map<String, List<PermissionGroupDTO>> permissionGroupMap1 = userManagementServiceImpl.fetchPermissionMapGroupByRole(CommonUtil.convertToCommaDelimited(userTypeRoles));
            model.addAttribute("rolePermissionGroupMap", permissionGroupMap1);

            model.addAttribute("userobj", userProfileDTO);

            
            if (request.getParameter("encUserProfileKey") != null) {
                userProfileKey = request.getParameter("encUserProfileKey");
                //DECRYPTING USER PROFILE KEY
                String decUserProfileKey = encryptDecrypt.decrypt(userProfileKey + "");
                decUserKey = Long.parseLong(decUserProfileKey);
            }

            //NAVIGATIN TO ADD OR UPDATE PAGE BY USER ID
            if (decUserKey > 0) {
                model.addAttribute("editUserProfile", userProfileDTO);
                retString = "usermgmt/updateuser";
            } else {
                model.addAttribute("userobj", userProfileDTO);
                retString = "usermgmt/adduser";
            }

        } catch (AppException e) {
            logger.debug("Exceptionoccured : renderAddUser : " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:402", myProperties.getProperty("failure-message"), e);
            model.addAttribute("exceptionBean", exception);
            retString = "/exception";
        }

        return retString;
    }

}
