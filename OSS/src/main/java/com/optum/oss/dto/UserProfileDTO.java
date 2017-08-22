/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dto;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author hpasupuleti
 */
//@Component
public class UserProfileDTO extends BaseDTO implements Serializable {

    @JsonView(Views.Public.class)
    private long userProfileKey;
    private String encUserProfileKey;
    private long organizationKey;
    private long userTypeKey;
    private MasterLookUpDTO userTypeObj = new MasterLookUpDTO();
    private OrganizationDTO organizationObj = new OrganizationDTO();
    private String userID;
    private String pswd;
    @JsonView(Views.Public.class)
    private String firstName;
    private String middleName;
    private String lastName;
    private String jobTitleName;
    private String email;
    private String telephoneNumber;
    private long userVerifiedIndicator;
    private long lockIndicator;
    private long loginAttemptCount;
    private String lastLoginDate;
    private String pswdResetDate;
    private List<UserSecurityDetailsDTO> userSecurityQuestionsListObj = new ArrayList<>();
    private List<UserApplicationRoleDTO> userApplicationRoleListObj = new ArrayList<>();
    private String userSessionID;
    private String userMACAddress;
    private String newUserMACAddress;
    private String oldEmail; // THIS IS USED WHEN WE TRY TO UPDATE USER NAME/EMAIL DETAILS FROM APPLICATION.
    private String dataExistsFlag; // THIS IS USED TO SET VALUE WHETHER THE DATA EXISTS AFTER QUERYING OR NOT
    private String userValidateMessage;//THIS IS USED TO SET THE REQUEST/SESSION MESSAGE 
    //WHILE ADDING/UPDATING/LOGIN Process
    private String lastActionDateTime; // THIS IS USED TO CHECK THE ACTIVE SESSION FOR USER
    // WHAT IS THE LAST ACTION PERFORMED DATE TIME FOR LOGGED IN USER
    private char activeSessionFlag;// THIS IS USED TO CHECK FOR USER ACTIVE SESSION
    private char pswdExpiryFlag;// THIS IS USED TO CHECK FOR USER PASSWORD EXPIRY FLAG
    private long pswdExpiryDays;// THIS IS USED TO ASSIGN THE NO OF DAYS LEFT FOR PASSWORD EXPIRY
    private String statusComment;
    private List<MenuDTO> menuList = new ArrayList<>();
    private Map<String, Set<String>> subMenuPermissionMap = new HashMap<>();
    private Set<String> moduleSet = new LinkedHashSet<>();
    private String encUserProfileProperty; // THIS IS USER TO SET ANY USERPROFILEDTO PROPERTY IN ENCRYPT AND DECRYPT MODE.

    private String selectedRole[];
    private long appRoleKey;
    private String appRoleName;
    private long delRoleKey;
    private String department;
    
    private char userEngSrv;
    private String selServCode;
    private SecurityServiceDTO serviceDto = new SecurityServiceDTO();

    /**
     * @return the userProfileKey
     */
    public long getUserProfileKey() {
        return userProfileKey;
    }

    /**
     * @param userProfileKey the userProfileKey to set
     */
    public void setUserProfileKey(long userProfileKey) {
        this.userProfileKey = userProfileKey;
    }

    /**
     * @return the organizationKey
     */
    public long getOrganizationKey() {
        return organizationKey;
    }

    /**
     * @param organizationKey the organizationKey to set
     */
    public void setOrganizationKey(long organizationKey) {
        this.organizationKey = organizationKey;
    }

    /**
     * @return the userTypeKey
     */
    public long getUserTypeKey() {
        return userTypeKey;
    }

    /**
     * @param userTypeKey the userTypeKey to set
     */
    public void setUserTypeKey(long userTypeKey) {
        this.userTypeKey = userTypeKey;
    }

    /**
     * @return the userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the middleName
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * @param middleName the middleName to set
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the jobTitleName
     */
    public String getJobTitleName() {
        return jobTitleName;
    }

    /**
     * @param jobTitleName the jobTitleName to set
     */
    public void setJobTitleName(String jobTitleName) {
        this.jobTitleName = jobTitleName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the telephoneNumber
     */
    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    /**
     * @param telephoneNumber the telephoneNumber to set
     */
    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    /**
     * @return the loginAttemptCount
     */
    public long getLoginAttemptCount() {
        return loginAttemptCount;
    }

    /**
     * @param loginAttemptCount the loginAttemptCount to set
     */
    public void setLoginAttemptCount(long loginAttemptCount) {
        this.loginAttemptCount = loginAttemptCount;
    }

    /**
     * @return the lastLoginDate
     */
    public String getLastLoginDate() {
        return lastLoginDate;
    }

    /**
     * @param lastLoginDate the lastLoginDate to set
     */
    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    

    /**
     * @return the userSecurityQuestionsListObj
     */
    public List<UserSecurityDetailsDTO> getUserSecurityQuestionsListObj() {
        return userSecurityQuestionsListObj;
    }

    /**
     * @param userSecurityQuestionsListObj the userSecurityQuestionsListObj to
     * set
     */
    public void setUserSecurityQuestionsListObj(List<UserSecurityDetailsDTO> userSecurityQuestionsListObj) {
        this.userSecurityQuestionsListObj = userSecurityQuestionsListObj;
    }

    /**
     * @return the userApplicationRoleListObj
     */
    public List<UserApplicationRoleDTO> getUserApplicationRoleListObj() {
        return userApplicationRoleListObj;
    }

    /**
     * @param userApplicationRoleListObj the userApplicationRoleListObj to set
     */
    public void setUserApplicationRoleListObj(List<UserApplicationRoleDTO> userApplicationRoleListObj) {
        this.userApplicationRoleListObj = userApplicationRoleListObj;
    }

    /**
     * @return the encUserProfileKey
     */
    public String getEncUserProfileKey() {
        return encUserProfileKey;
    }

    /**
     * @param encUserProfileKey the encUserProfileKey to set
     */
    public void setEncUserProfileKey(String encUserProfileKey) {
        this.encUserProfileKey = encUserProfileKey;
    }

    /**
     * @return the userTypeObj
     */
    public MasterLookUpDTO getUserTypeObj() {
        return userTypeObj;
    }

    /**
     * @param userTypeObj the userTypeObj to set
     */
    public void setUserTypeObj(MasterLookUpDTO userTypeObj) {
        this.userTypeObj = userTypeObj;
    }

    /**
     * @return the userSessionID
     */
    public String getUserSessionID() {
        return userSessionID;
    }

    /**
     * @param userSessionID the userSessionID to set
     */
    public void setUserSessionID(String userSessionID) {
        this.userSessionID = userSessionID;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return pswd;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.pswd = password;
    }

    /**
     * @return the oldEmail
     */
    public String getOldEmail() {
        return oldEmail;
    }

    /**
     * @param oldEmail the oldEmail to set
     */
    public void setOldEmail(String oldEmail) {
        this.oldEmail = oldEmail;
    }

    /**
     * @return the dataExistsFlag
     */
    public String getDataExistsFlag() {
        return dataExistsFlag;
    }

    /**
     * @param dataExistsFlag the dataExistsFlag to set
     */
    public void setDataExistsFlag(String dataExistsFlag) {
        this.dataExistsFlag = dataExistsFlag;
    }

    /**
     * @return the userVerifiedIndicator
     */
    public long getUserVerifiedIndicator() {
        return userVerifiedIndicator;
    }

    /**
     * @param userVerifiedIndicator the userVerifiedIndicator to set
     */
    public void setUserVerifiedIndicator(long userVerifiedIndicator) {
        this.userVerifiedIndicator = userVerifiedIndicator;
    }

    /**
     * @return the lockIndicator
     */
    public long getLockIndicator() {
        return lockIndicator;
    }

    /**
     * @param lockIndicator the lockIndicator to set
     */
    public void setLockIndicator(long lockIndicator) {
        this.lockIndicator = lockIndicator;
    }

    /**
     * @return the userValidateMessage
     */
    public String getUserValidateMessage() {
        return userValidateMessage;
    }

    /**
     * @param userValidateMessage the userValidateMessage to set
     */
    public void setUserValidateMessage(String userValidateMessage) {
        this.userValidateMessage = userValidateMessage;
    }

    /**
     * @return the userMACAddress
     */
    public String getUserMACAddress() {
        return userMACAddress;
    }

    /**
     * @param userMACAddress the userMACAddress to set
     */
    public void setUserMACAddress(String userMACAddress) {
        this.userMACAddress = userMACAddress;
    }

    /**
     * @return the newUserMACAddress
     */
    public String getNewUserMACAddress() {
        return newUserMACAddress;
    }

    /**
     * @param newUserMACAddress the newUserMACAddress to set
     */
    public void setNewUserMACAddress(String newUserMACAddress) {
        this.newUserMACAddress = newUserMACAddress;
    }

    /**
     * @return the organizationObj
     */
    public OrganizationDTO getOrganizationObj() {
        return organizationObj;
    }

    /**
     * @param organizationObj the organizationObj to set
     */
    public void setOrganizationObj(OrganizationDTO organizationObj) {
        this.organizationObj = organizationObj;
    }

    /**
     * @return the lastActionDateTime
     */
    public String getLastActionDateTime() {
        return lastActionDateTime;
    }

    /**
     * @param lastActionDateTime the lastActionDateTime to set
     */
    public void setLastActionDateTime(String lastActionDateTime) {
        this.lastActionDateTime = lastActionDateTime;
    }

    /**
     * @return the activeSessionFlag
     */
    public char getActiveSessionFlag() {
        return activeSessionFlag;
    }

    /**
     * @param activeSessionFlag the activeSessionFlag to set
     */
    public void setActiveSessionFlag(char activeSessionFlag) {
        this.activeSessionFlag = activeSessionFlag;
    }

    

    /**
     * @return the statusComment
     */
    public String getStatusComment() {
        return statusComment;
    }

    /**
     * @param statusComment the statusComment to set
     */
    public void setStatusComment(String statusComment) {
        this.statusComment = statusComment;
    }

    /**
     * @return the menuList
     */
    public List<MenuDTO> getMenuList() {
        return menuList;
    }

    /**
     * @param menuList the menuList to set
     */
    public void setMenuList(List<MenuDTO> menuList) {
        this.menuList = menuList;
    }

    /**
     * @return the subMenuPermissionMap
     */
    public Map<String, Set<String>> getSubMenuPermissionMap() {
        return subMenuPermissionMap;
    }

    /**
     * @param subMenuPermissionMap the subMenuPermissionMap to set
     */
    public void setSubMenuPermissionMap(Map<String, Set<String>> subMenuPermissionMap) {
        this.subMenuPermissionMap = subMenuPermissionMap;
    }

    /**
     * @return the moduleSet
     */
    public Set<String> getModuleSet() {
        return moduleSet;
    }

    /**
     * @param moduleSet the moduleSet to set
     */
    public void setModuleSet(Set<String> moduleSet) {
        this.moduleSet = moduleSet;
    }

    /**
     * @return the encUserProfileProperty
     */
    public String getEncUserProfileProperty() {
        return encUserProfileProperty;
    }

    /**
     * @param encUserProfileProperty the encUserProfileProperty to set
     */
    public void setEncUserProfileProperty(String encUserProfileProperty) {
        this.encUserProfileProperty = encUserProfileProperty;
    }

    /**
     * @return the selectedRole
     */
    public String[] getSelectedRole() {
        return selectedRole;
    }

    /**
     * @param selectedRole the selectedRole to set
     */
    public void setSelectedRole(String[] selectedRole) {
        this.selectedRole = selectedRole;
    }

    /**
     * @return the pswdExpiryFlag
     */
    public char getPswdExpiryFlag() {
        return pswdExpiryFlag;
    }

    /**
     * @param pswdExpiryFlag the pswdExpiryFlag to set
     */
    public void setPswdExpiryFlag(char pswdExpiryFlag) {
        this.pswdExpiryFlag = pswdExpiryFlag;
    }

    /**
     * @return the pswdExpiryDays
     */
    public long getPswdExpiryDays() {
        return pswdExpiryDays;
    }

    /**
     * @param pswdExpiryDays the pswdExpiryDays to set
     */
    public void setPswdExpiryDays(long pswdExpiryDays) {
        this.pswdExpiryDays = pswdExpiryDays;
    }

    /**
     * @return the pswdResetDate
     */
    public String getPswdResetDate() {
        return pswdResetDate;
    }

    /**
     * @param pswdResetDate the pswdResetDate to set
     */
    public void setPswdResetDate(String pswdResetDate) {
        this.pswdResetDate = pswdResetDate;
    }

    public long getAppRoleKey() {
        return appRoleKey;
    }

    public void setAppRoleKey(long appRoleKey) {
        this.appRoleKey = appRoleKey;
    }

    public long getDelRoleKey() {
        return delRoleKey;
    }

    public void setDelRoleKey(long delRoleKey) {
        this.delRoleKey = delRoleKey;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * @return the userEngSrv
     */
    public char getUserEngSrv() {
        return userEngSrv;
    }

    /**
     * @param userEngSrv the userEngSrv to set
     */
    public void setUserEngSrv(char userEngSrv) {
        this.userEngSrv = userEngSrv;
    }

    /**
     * @return the selServCode
     */
    public String getSelServCode() {
        return selServCode;
    }

    /**
     * @param selServCode the selServCode to set
     */
    public void setSelServCode(String selServCode) {
        this.selServCode = selServCode;
    }

    /**
     * @return the appRoleName
     */
    public String getAppRoleName() {
        return appRoleName;
    }

    /**
     * @param appRoleName the appRoleName to set
     */
    public void setAppRoleName(String appRoleName) {
        this.appRoleName = appRoleName;
    }

    /**
     * @return the serviceDto
     */
    public SecurityServiceDTO getServiceDto() {
        return serviceDto;
    }

    /**
     * @param serviceDto the serviceDto to set
     */
    public void setServiceDto(SecurityServiceDTO serviceDto) {
        this.serviceDto = serviceDto;
    }

 

    }
