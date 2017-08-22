/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service.impl;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.constants.ApplicationMenuSubMenuEnum;
import com.optum.oss.constants.ApplicationMenuSubMenuIDEnum;
import com.optum.oss.dao.impl.UserProfileDAOImpl;
import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.dto.MenuDTO;
import com.optum.oss.dto.PermissionDTO;
import com.optum.oss.dto.SubMenuDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.LDAPAuthenticate;
import com.optum.oss.helper.WebAppAdminMailHelper;
import com.optum.oss.service.UserProfileService;
import com.optum.oss.util.CommonUtil;
import com.optum.oss.util.DateUtil;
import com.optum.oss.util.EncryptDecrypt;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author hpasupuleti
 */
@Service
public class UserProfileServiceImpl implements UserProfileService {

    private static final Logger logger = Logger.getLogger(UserProfileServiceImpl.class);

    /*
     Start : Setter getters for private variables
     */
    @Resource(name = "myProperties")
    private Properties myProperties;

    @Autowired
    private LDAPAuthenticate ldapAuthenticate;

    @Autowired
    private UserProfileDAOImpl userProfileDAO;

    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private EncryptDecrypt encDecrypt;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private WebAppAdminMailHelper webAppMailHelper;
    /*
     End : Setter getters for private variables
     */

    /**
     *
     * @param currentPassword
     * @param newPassword
     * @param userProfileObj
     * @return String
     * @throws AppException
     */
    
    @Override
    public String expChangePassUpdate(final String currentPassword, final String newPassword,
            final UserProfileDTO userProfileObj) throws AppException {
        String changePass = "";
        String retChangePass = "";
        // CHECK NEW PASSWORD CONTAINS AT LEAST $ CHARS SAME IN OLD PASSWORD 
try{
        if (!commonUtil.isNewPWDContainsOldPWDChars(newPassword, currentPassword)) {
            if (ldapAuthenticate.validateUserInLDAP(userProfileObj.getEmail(), currentPassword)) {
                changePass = ldapAuthenticate.changeLDAPPasswordByUser(userProfileObj, newPassword);
                if (StringUtils.isEmpty(changePass)) {
                    userProfileDAO.updatePasswordResetDateTimeOnUserID(userProfileObj.getUserProfileKey());
                } else {
                    retChangePass = changePass;
                }
            } else {
                // INVALID CURRENT PASSWORD.
                retChangePass = myProperties.getProperty("userconfig.incorrect-curpassword");
            }
        } else {
            retChangePass = myProperties.getProperty("userconfig.newpwd-oldpwd-commonchars");
        }
        } catch (Exception e) {
            String expMsg = e.getMessage();
            if (expMsg.equalsIgnoreCase(ApplicationConstants.OPENDJ_DISCONNECTED)) {
                retChangePass=ApplicationConstants.OPENDJ_DISCONNECTED;
            }

        }
        return retChangePass;
    }

    /**
     *
     * @param currentPassword
     * @param newPassword
     * @param userProfileObj
     * @return int
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public String updateChangedUserPassword(final String currentPassword, final String newPassword,
            final UserProfileDTO userProfileObj)
            throws AppException {
        int retVal = 0;
        long diffDays = 0;
        String retChangePass = "";
        try{
        // CHECK NEW PASSWORD CONTAINS AT LEAST $ CHARS SAME IN OLD PASSWORD 
        if (!commonUtil.isNewPWDContainsOldPWDChars(newPassword, currentPassword)) {
            if (!StringUtils.isEmpty(userProfileObj.getPswdResetDate())) {
                String passResetDT = userProfileObj.getPswdResetDate();
                diffDays = dateUtil.calculateDateDiffInDays(passResetDT);
            }

            String passCheck = "true";
            if (System.getProperty("OSS_CHECK_PASSWORD_POLICY") != null) {
                passCheck = System.getProperty("OSS_CHECK_PASSWORD_POLICY");
            }
            if (passCheck.equalsIgnoreCase("false")) {
                diffDays = 2;
            }

            if (diffDays > 1) {
                String changePass = "";
                if (ldapAuthenticate.validateUserInLDAP(userProfileObj.getEmail(), currentPassword)) {
                    changePass = ldapAuthenticate.changeLDAPPasswordByUser(userProfileObj, newPassword);
                    if (StringUtils.isEmpty(changePass)) {
                    // MAIL SEND TO USER WITH NEW PASSWORD GENERATED
                        // UPDATE PASSWORD RESET DATE TIME IN USER PROFILE
                        retVal = userProfileDAO.updatePasswordResetDateTimeOnUserID(userProfileObj.getUserProfileKey());
                    } else {
                        retChangePass = changePass;
                    }
                } else {
                    // INVALID CURRENT PASSWORD.
                    retChangePass = myProperties.getProperty("userconfig.incorrect-curpassword");
                }
            } else {
                // IF USER IS TRYING TO CHANGE THE PASSWORD WITH IN 24 HRS
                retChangePass = myProperties.getProperty("userconfig.change-pass-failed");
            }
            if (StringUtils.isEmpty(retChangePass)) {
                //  IF PASSWROD CHANGE IS SUCCESS THEN SEND EMAIL STATING THAT PASSWORD HAS BEEN UPDATED SUCCESSFULLY
                webAppMailHelper.mail_UserAccountPasswordUpdate(userProfileObj);
            }
        } else {
            retChangePass = myProperties.getProperty("userconfig.newpwd-oldpwd-commonchars");
        }
        }catch(AppException e){
                String expMsg = e.getMessage();
            if (expMsg.equalsIgnoreCase(ApplicationConstants.OPENDJ_DISCONNECTED)) {
                retChangePass=ApplicationConstants.OPENDJ_DISCONNECTED;
            }
        }
        return retChangePass;
    }

    /**
     *
     * @param currentPassword
     * @param newPassword
     * @param userProfileObj
     * @return int
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int updateExpiredUserPassword(final String currentPassword, final String newPassword,
            final UserProfileDTO userProfileObj)
            throws AppException {
        int retVal = 0;

        if (ldapAuthenticate.validateUserInLDAP(userProfileObj.getEmail(), currentPassword)) {
            boolean bool = ldapAuthenticate.changeLDAPPassword(userProfileObj, newPassword);
            if (bool) {
                // MAIL SEND TO USER WITH NEW PASSWORD GENERATED
                // UPDATE PASSWORD RESET DATE TIME IN USER PROFILE
                retVal = userProfileDAO.updatePasswordResetDateTimeOnUserID(userProfileObj.getUserProfileKey());
            } else {
                retVal = 0;
            }
        } else {
            // INVALID CURRENT PASSWORD.
            retVal = -1;
        }

        return retVal;
    }

    /**
     *
     * @param userProfileObj
     * @param sessUserProfileObj
     * @return int
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int updateUserPersonnelInformation(final UserProfileDTO userProfileObj,
            final UserProfileDTO sessUserProfileObj) throws AppException {
        int retVal = userProfileDAO.updateUserPersonnelInformation(userProfileObj, sessUserProfileObj);
        if (retVal > 0) {
            //USER PROFILE SUCCESSFULLY UPDATED. SEND EMAIL STATING THAT profile has been updated
            webAppMailHelper.mail_UserAccountProfileUpdate(sessUserProfileObj);
        }
        return retVal;
    }

    /**
     *
     * @param userProfileObj
     * @return UserProfileDTO
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public UserProfileDTO prepareUserPermissions(final UserProfileDTO userProfileObj) throws AppException {
        try {
            List<PermissionDTO> permissionList = userProfileDAO.getUserPermissionsByID(userProfileObj.getUserProfileKey());

            Set<String> moduleSet = new LinkedHashSet<>();
            Set<String> initSubMenuSet = new TreeSet<>();

            Set<MenuDTO> menuSet = new TreeSet<>(new Comparator<MenuDTO>() {
                @Override
                public int compare(MenuDTO o1, MenuDTO o2) {
                    if (o1.getMenuKey() == o2.getMenuKey()) {
                        return 0;
                    }
                    return 1;
                }
            });

            List<MenuDTO> menuList = new ArrayList<>();

            if (!permissionList.isEmpty()) {
                for (PermissionDTO permissionObj : permissionList) {
                    if (!StringUtils.isEmpty(permissionObj.getModuleName())) {
                        moduleSet.add(permissionObj.getModuleName());
                    }
                    if (!StringUtils.isEmpty(permissionObj.getSubMenuName())) {
                        initSubMenuSet.add(permissionObj.getSubMenuName());
                    }
                    /*
                     Start : Preparing Menu List along with Main Menu Links
                     */
                    if (!StringUtils.isEmpty(permissionObj.getMenuName())) {
                        MenuDTO menuObj = new MenuDTO();
                        menuObj.setMenuKey(permissionObj.getMenuId());
                        menuObj.setEncMenuKey(encDecrypt.encrypt(permissionObj.getMenuId() + ""));
                        menuObj.setMenuName(permissionObj.getMenuName());
                        menuObj.setEncMenuName(encDecrypt.encrypt(permissionObj.getMenuName()));
                        menuObj.setSubMenuExistsFlag(permissionObj.getChildExistsIndicator());
                        String enumMenu = commonUtil.replaceSpaceAndSpecialChars(permissionObj.getMenuName());
                        if (permissionObj.getChildExistsIndicator() <= 0) {
                            String menuLink = ApplicationMenuSubMenuEnum.valueOf(enumMenu).toString();
                            menuObj.setAppLink(menuLink);
                        }
                        String menuID = ApplicationMenuSubMenuIDEnum.valueOf(enumMenu).toString();
                        menuObj.setHtmlID(menuID);

                        menuSet.add(menuObj);
                    }
                    /*
                     End : Preparing Menu List along with Main Menu Links
                     */
                }
                userProfileObj.setModuleSet(moduleSet);
                /*
                 Start : Preparing Sub-Menu List along with Main Menu Links
                 */

                Iterator<MenuDTO> menuItr = menuSet.iterator();
                while (menuItr.hasNext()) {
                    MenuDTO menuObj = menuItr.next();
                    String menuName = menuObj.getMenuName();
                    if (menuObj.getSubMenuExistsFlag() > 0) {
                        /*
                         Start: Creating new  SubMenu Set Object for Each Menu
                         */
                        Set<SubMenuDTO> subMenuSet = new TreeSet<>(new Comparator<SubMenuDTO>() {
                            @Override
                            public int compare(SubMenuDTO o1, SubMenuDTO o2) {
                                if (o1.getSubMenuKey() == o2.getSubMenuKey()) {
                                    return 0;
                                }
                                return 1;
                            }
                        });
                        /*
                         End: Creating new SubMenu Set Object for Each Menu
                         */
                        for (PermissionDTO permissionObj : permissionList) {
                            if (permissionObj.getMenuName().equalsIgnoreCase(menuName)) {
                                SubMenuDTO subMenuObj = new SubMenuDTO();
                                subMenuObj.setSubMenuKey(permissionObj.getSubMenuId());
                                subMenuObj.setEncSubMenuKey(encDecrypt.encrypt(subMenuObj.getSubMenuKey() + ""));
                                subMenuObj.setSubMenuName(permissionObj.getSubMenuName());
                                subMenuObj.setEncSubMenuName(encDecrypt.encrypt(subMenuObj.getSubMenuName()));
                                //String enumSubMenu = permissionObj.getSubMenuName().replaceAll("\\s+", "_");
                                String enumSubMenu = commonUtil.replaceSpaceAndSpecialChars(permissionObj.getSubMenuName());
                                String subMenuLink = ApplicationMenuSubMenuEnum.valueOf(enumSubMenu).toString();
                                subMenuObj.setAppLink(subMenuLink);
                                String subMenuID = ApplicationMenuSubMenuIDEnum.valueOf(enumSubMenu).toString();
                                subMenuObj.setHtmlID(subMenuID);

                                subMenuSet.add(subMenuObj);
                            }
                        }
                        menuObj.setSubMenuObjSet(subMenuSet);
                    }
                    menuList.add(menuObj);
                }

                /*
                 start  moving the dashboard menu to end index
                 */
                 int count = 0;
                for (MenuDTO menuDTO : menuList) {
                    String menuName = menuDTO.getMenuName();
                    if (menuName.equalsIgnoreCase("Reports")) {
                        menuList.remove(count);
                        menuList.add(menuDTO);
                        break;
                    }
                    count++;
                }

                /*
                 end   moving the dashboard menu to end index
                 */
                userProfileObj.setMenuList(menuList);

                /*
                 End : Preparing Sub-Menu List along with Main Menu Links
                 */

                /*
                 Start : Preparing Sub-Menu Wise Permission Map.
                 */
                Map<String, Set<String>> permissionMap = new HashMap<>();

                if (!initSubMenuSet.isEmpty()) {
                    Iterator<String> initSubMenuItr = initSubMenuSet.iterator();
                    while (initSubMenuItr.hasNext()) {
                        String subMenuName = initSubMenuItr.next();
                        Set<String> permissionSet = new TreeSet<>();
                        String mapMenuName = "";
                        for (PermissionDTO permissionObj : permissionList) {
                            if (permissionObj.getSubMenuName().equalsIgnoreCase(subMenuName)) {
                                //IF THE CHILD FLAG IS '0' THEN ITS A MAIN MENU 
                                // SO REPLACE THE SubMenu NAME WITH MAIN MENU
                                if (permissionObj.getChildExistsIndicator() <= 0) {
                                    mapMenuName = permissionObj.getMenuName();
                                } else {
                                    mapMenuName = subMenuName;
                                }
                                permissionSet.add(permissionObj.getPermissionName());
                            }
                        }
                        permissionMap.put(mapMenuName, permissionSet);
                    }
                }
                userProfileObj.setSubMenuPermissionMap(permissionMap);

                /*
                 End : Preparing Sub-Menu Wise Permission Map.
                 */
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : prepareUserPermissions : " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "prepareUserPermissions() :: " + e.getMessage());
        }

        return userProfileObj;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<UserProfileDTO> getUsersForPwdExpiryAlert(int days,int expiryDays) throws AppException {
        return userProfileDAO.getUsersForPwdExpiryAlert(days,expiryDays);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.READ_COMMITTED)
    public List<ClientEngagementDTO> getServiceDueDatePassed() throws AppException {
        return userProfileDAO.getServiceDueDatePassed();
    }

    @Override
    public void updateUserLoginAttempts() throws AppException {
             userProfileDAO.updateUserLoginAttempts();
    }

}
