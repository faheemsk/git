/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.dto;

import java.io.Serializable;

/**
 *
 * @author hpasupuleti
 */
//@Component
public class ModuleDTO extends BaseDTO implements Serializable{
    
    private long moduleKey;
    private String moduleName;

    /**
     * @return the moduleKey
     */
    public long getModuleKey() {
        return moduleKey;
    }

    /**
     * @param moduleKey the moduleKey to set
     */
    public void setModuleKey(long moduleKey) {
        this.moduleKey = moduleKey;
    }

    /**
     * @return the moduleName
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * @param moduleName the moduleName to set
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
    
}
