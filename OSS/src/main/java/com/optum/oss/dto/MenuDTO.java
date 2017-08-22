/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.dto;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author hpasupuleti
 */
//@Component
public class MenuDTO extends BaseDTO implements Serializable{
    
    private long menuKey;
    private String menuName;
    private Set<SubMenuDTO> subMenuObjSet = new TreeSet<>();
    private long subMenuExistsFlag;
    private String encMenuKey;
    private String encMenuName;
    private String appLink;
    private String htmlID;

    /**
     * @return the menuKey
     */
    public long getMenuKey() {
        return menuKey;
    }

    /**
     * @param menuKey the menuKey to set
     */
    public void setMenuKey(long menuKey) {
        this.menuKey = menuKey;
    }

    /**
     * @return the menuName
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * @param menuName the menuName to set
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

   

    /**
     * @return the subMenuExistsFlag
     */
    public long getSubMenuExistsFlag() {
        return subMenuExistsFlag;
    }

    /**
     * @param subMenuExistsFlag the subMenuExistsFlag to set
     */
    public void setSubMenuExistsFlag(long subMenuExistsFlag) {
        this.subMenuExistsFlag = subMenuExistsFlag;
    }

    /**
     * @return the encMenuKey
     */
    public String getEncMenuKey() {
        return encMenuKey;
    }

    /**
     * @param encMenuKey the encMenuKey to set
     */
    public void setEncMenuKey(String encMenuKey) {
        this.encMenuKey = encMenuKey;
    }

    /**
     * @return the encMenuName
     */
    public String getEncMenuName() {
        return encMenuName;
    }

    /**
     * @param encMenuName the encMenuName to set
     */
    public void setEncMenuName(String encMenuName) {
        this.encMenuName = encMenuName;
    }

    /**
     * @return the appLink
     */
    public String getAppLink() {
        return appLink;
    }

    /**
     * @param appLink the appLink to set
     */
    public void setAppLink(String appLink) {
        this.appLink = appLink;
    }

    /**
     * @return the subMenuObjSet
     */
    public Set<SubMenuDTO> getSubMenuObjSet() {
        return subMenuObjSet;
    }

    /**
     * @param subMenuObjSet the subMenuObjSet to set
     */
    public void setSubMenuObjSet(Set<SubMenuDTO> subMenuObjSet) {
        this.subMenuObjSet = subMenuObjSet;
    }

    /**
     * @return the htmlID
     */
    public String getHtmlID() {
        return htmlID;
    }

    /**
     * @param htmlID the htmlID to set
     */
    public void setHtmlID(String htmlID) {
        this.htmlID = htmlID;
    }
    
    
}
