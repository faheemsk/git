/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dto;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

/**
 *
 * @author hpasupuleti
 */
//@Component
public class SubMenuDTO extends BaseDTO implements Serializable, Comparator<SubMenuDTO> {

    private long subMenuKey;
    private String encSubMenuKey;
    private String subMenuName;
    private String encSubMenuName;
    private String appLink;
    private String htmlID;

    /**
     * @return the subMenuKey
     */
    public long getSubMenuKey() {
        return subMenuKey;
    }

    /**
     * @param subMenuKey the subMenuKey to set
     */
    public void setSubMenuKey(long subMenuKey) {
        this.subMenuKey = subMenuKey;
    }

    /**
     * @return the subMenuName
     */
    public String getSubMenuName() {
        return subMenuName;
    }

    /**
     * @param subMenuName the subMenuName to set
     */
    public void setSubMenuName(String subMenuName) {
        this.subMenuName = subMenuName;
    }

    /**
     * @return the encSubMenuKey
     */
    public String getEncSubMenuKey() {
        return encSubMenuKey;
    }

    /**
     * @param encSubMenuKey the encSubMenuKey to set
     */
    public void setEncSubMenuKey(String encSubMenuKey) {
        this.encSubMenuKey = encSubMenuKey;
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if (obj instanceof SubMenuDTO) {
            SubMenuDTO temp = (SubMenuDTO) obj;
            if (this.subMenuKey == temp.subMenuKey && this.subMenuName == temp.subMenuName) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (int) (this.subMenuKey ^ (this.subMenuKey >>> 32));
        hash = 79 * hash + Objects.hashCode(this.subMenuName);
        return hash;
    }

    @Override
    public int compare(SubMenuDTO o1, SubMenuDTO o2) {
        if (o1.getSubMenuKey() == o2.getSubMenuKey()) {
            return 0;
        }
        return 1;
    }

    /**
     * @return the encSubMenuName
     */
    public String getEncSubMenuName() {
        return encSubMenuName;
    }

    /**
     * @param encSubMenuName the encSubMenuName to set
     */
    public void setEncSubMenuName(String encSubMenuName) {
        this.encSubMenuName = encSubMenuName;
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
