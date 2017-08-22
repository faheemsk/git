/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.helper;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import java.util.Hashtable;
import java.util.Properties;
import javax.annotation.Resource;
import javax.naming.AuthenticationException;
import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.InvalidAttributeValueException;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 *
 * @author hpasupuleti
 */
@Component
public class LDAPAuthenticate {

    private static final Logger logger = Logger.getLogger(LDAPAuthenticate.class);

    @Resource(name = "myProperties")
    private Properties myProperties;

    final String url = System.getProperty("OSS_LDAP_URL");
    final String ldapCtxFactory = System.getProperty("OSS_LDAP_CTX_FACTORY");
    final String securityAuthentication = System.getProperty("OSS_LDAP_SECURITY_AUTHENTICATION");
    final String securityPrincipal = System.getProperty("OSS_LDAP_SECURITY_PRINCIPAL");
    final String securityCredentials = System.getProperty("OSS_LDAP_SECURITY_PASSWORD");
    final String openDSDN = System.getProperty("OSS_LDAP_BASE_DN");

    private DirContext prepareContext() throws AppException {
        DirContext authContext = null;
        try {
            Hashtable<String, String> authEnv = new Hashtable<>();
            authEnv.put(Context.INITIAL_CONTEXT_FACTORY, ldapCtxFactory.trim());
            authEnv.put(Context.PROVIDER_URL, url.trim());
            authEnv.put(Context.SECURITY_AUTHENTICATION, securityAuthentication.trim());
            authEnv.put(Context.SECURITY_PRINCIPAL, securityPrincipal.trim());
            authEnv.put(Context.SECURITY_CREDENTIALS, securityCredentials.trim());

            authContext = new InitialDirContext(authEnv);
        } catch (NamingException ne) {
            LDAPAuthenticate.logger.info("In validateUserInLDAP :Naming Exception Occurred:" + ne.getMessage());
        }

        return authContext;
    }

    private String getUid(String paramUser) throws Exception {
        DirContext authContext = this.prepareContext();
        String user = escapeLDAPSearchFilter(paramUser);
        String filter = "(uid=" + user + ")";
        SearchControls ctrl = new SearchControls();
        ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
        NamingEnumeration answer = authContext.search("", filter, ctrl);

        String dn = "";
        if (answer.hasMore()) {
            SearchResult result = (SearchResult) answer.next();
            dn = result.getNameInNamespace();
        } else {
            dn = "";
        }
        answer.close();
        return dn;
    }

    private Attributes prepareAttributesForAddUser(final UserProfileDTO userProfileDTO)
            throws AppException {
        Attributes entryList = new BasicAttributes(true);

        Attribute cn = new BasicAttribute("cn", userProfileDTO.getUserID() + "");
        Attribute sn = new BasicAttribute("sn", userProfileDTO.getLastName());
        Attribute givenName = new BasicAttribute("givenName", userProfileDTO.getFirstName());
        Attribute mail = new BasicAttribute("mail", userProfileDTO.getEmail());
        Attribute userPswd = new BasicAttribute("userPassword", userProfileDTO.getPassword());
        Attribute uid = new BasicAttribute("uid", userProfileDTO.getEmail());
        Attribute oc = new BasicAttribute("objectclass", "person");
        oc.add("top");
        oc.add("inetOrgPerson");

        entryList.put(cn);
        entryList.put(sn);
        entryList.put(givenName);
        entryList.put(mail);
        entryList.put(userPswd);
        entryList.put(uid);
        entryList.put(oc);

        return entryList;
    }

    public boolean validateUserInLDAP(final String username, final String pswd)
            throws AppException {
        logger.info("Start: validateUserInLDAP");
        boolean returnVal = true;
//        try {
//            DirContext authContext = this.prepareContext();
//            if (authContext != null) {
//                String dn = ApplicationConstants.LDAP_USER_COMPARE_CONSTANT + username + "," + openDSDN;
//
//                authContext.addToEnvironment(Context.SECURITY_AUTHENTICATION, securityAuthentication);
//                authContext.addToEnvironment(Context.SECURITY_PRINCIPAL, dn);
//                authContext.addToEnvironment(Context.SECURITY_CREDENTIALS, pswd);
//
//                if (!StringUtils.isEmpty(username)) {
//                    String lookUpStr = ApplicationConstants.LDAP_USER_COMPARE_CONSTANT + username;
//                    Object a = authContext.lookup(lookUpStr);
//                } else {
//                    returnVal = false;
//                }
//            } else {
//                returnVal = false;
//            }
//            LDAPAuthenticate.logger.info("In LDAPAuthenticate : returnVal:" + returnVal);
//            String dn = getUid(username);
//            returnVal = !StringUtils.isEmpty(dn);
        String dn = ApplicationConstants.LDAP_USER_COMPARE_CONSTANT + username + "," + openDSDN;

        Hashtable<String, String> authEnv = new Hashtable<>();
        authEnv.put(Context.INITIAL_CONTEXT_FACTORY, ldapCtxFactory.trim());
        authEnv.put(Context.PROVIDER_URL, url.trim());
        authEnv.put(Context.SECURITY_AUTHENTICATION, securityAuthentication.trim());
        authEnv.put(Context.SECURITY_PRINCIPAL, dn);
            authEnv.put(Context.SECURITY_CREDENTIALS,pswd);

        try {
            DirContext authContext = new InitialDirContext(authEnv);
        } catch (AuthenticationException authEx) {
            LDAPAuthenticate.logger.info("In validateUserInLDAP :Authentication Failed:" + authEx.getMessage());
            returnVal = false;
        } catch (CommunicationException commEx) {
            LDAPAuthenticate.logger.info("In validateUserInLDAP :Authentication Failed:" + commEx.getMessage());
            throw new AppException(ApplicationConstants.OPENDJ_DISCONNECTED);
        } catch (NamingException ne) {
            LDAPAuthenticate.logger.info("In validateUserInLDAP :Naming Exception Occurred:" + ne.getMessage());
            returnVal = false;
            //returnVal = true;
        }
        return returnVal;
    }

   

    public boolean createLDAPUser(final UserProfileDTO userProfileDTO) throws AppException {
        boolean userCreate = true;
        try {
            DirContext authContext = this.prepareContext();
            if (authContext != null) {
                String userId = ApplicationConstants.LDAP_USER_COMPARE_CONSTANT + userProfileDTO.getEmail();

                authContext.createSubcontext(userId, prepareAttributesForAddUser(userProfileDTO));
            } else {
                throw new AppException();
            }
        } catch (AppException e) {
            e.printStackTrace();
           // throw new AppException();
        } catch (Exception e) {

            e.printStackTrace();
            userCreate = false;
        }
        return userCreate;
    }

    public boolean changeLDAPPassword(final UserProfileDTO userProfileDTO, final String newPswd) {
        boolean changePass = true;
        logger.info("Start:changeLDAPPassword:");
        try {
            DirContext authContext = this.prepareContext();
            if (authContext != null) {
                String userDN = ApplicationConstants.LDAP_USER_COMPARE_CONSTANT + userProfileDTO.getEmail();
                Attribute attr = new BasicAttribute("userPassword", newPswd);
                ModificationItem item = new ModificationItem(2, attr);
                ModificationItem[] items = new ModificationItem[1];
                items[0] = item;
                authContext.modifyAttributes(userDN, items);
            }
        } catch (Exception e) {
            e.printStackTrace();
            changePass = false;
        } finally {
            logger.info("In:createLDAPUser:Finally:userCreate:" + changePass);
            return changePass;
        }
    }

    public String changeLDAPPasswordByUser(final UserProfileDTO userProfileDTO, final String newPswd) {
        String changePass = "";
        logger.info("Start:changeLDAPPassword:");
        try {
            DirContext authContext = this.prepareContext();
            if (authContext != null) {
                String userDN = ApplicationConstants.LDAP_USER_COMPARE_CONSTANT + userProfileDTO.getEmail();
                Attribute attr = new BasicAttribute("userPassword", newPswd);
                ModificationItem item = new ModificationItem(2, attr);
                ModificationItem[] items = new ModificationItem[1];
                items[0] = item;
                authContext.modifyAttributes(userDN, items);
            }
        } catch (InvalidAttributeValueException iv) {
            //LDAP: error code 19 - The provided new password was found in the password history for the user]
            changePass = myProperties.getProperty("userconfig.invalid-password-history");
        } catch (Exception e) {
            changePass = myProperties.getProperty("userconfig.incorrect-curpswd");
        } finally {
            logger.info("In:changeLDAPPasswordByUser:Finally:changePass:" + changePass);
            return changePass;
        }
    }

    private String escapeLDAPSearchFilter(String filter) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < filter.length(); i++) {
            char curChar = filter.charAt(i);
            switch (curChar) {
                case '\\':
                    sb.append("\\5c");
                    break;
                case '*':
                    sb.append("\\2a");
                    break;
                case '(':
                    sb.append("\\28");
                    break;
                case ')':
                    sb.append("\\29");
                    break;
                case '\u0000':
                    sb.append("\\00");
                    break;
                default:
                    sb.append(curChar);
            }
        }
        return sb.toString();
    }

}
