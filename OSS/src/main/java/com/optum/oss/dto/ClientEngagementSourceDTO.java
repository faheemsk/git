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
public class ClientEngagementSourceDTO extends BaseDTO implements Serializable{
    
    private long clientEngSourceKey;
    private long sourceKey; // REFERENCE TO MASTER LOOK UP
    private String clientEngSourceCode;
    private String clientEngSourceName;

    /**
     * @return the clientEngSourceKey
     */
    public long getClientEngSourceKey() {
        return clientEngSourceKey;
    }

    /**
     * @param clientEngSourceKey the clientEngSourceKey to set
     */
    public void setClientEngSourceKey(long clientEngSourceKey) {
        this.clientEngSourceKey = clientEngSourceKey;
    }

    /**
     * @return the sourceKey
     */
    public long getSourceKey() {
        return sourceKey;
    }

    /**
     * @param sourceKey the sourceKey to set
     */
    public void setSourceKey(long sourceKey) {
        this.sourceKey = sourceKey;
    }

    /**
     * @return the clientEngSourceCode
     */
    public String getClientEngSourceCode() {
        return clientEngSourceCode;
    }

    /**
     * @param clientEngSourceCode the clientEngSourceCode to set
     */
    public void setClientEngSourceCode(String clientEngSourceCode) {
        this.clientEngSourceCode = clientEngSourceCode;
    }

    /**
     * @return the clientEngSourceName
     */
    public String getClientEngSourceName() {
        return clientEngSourceName;
    }

    /**
     * @param clientEngSourceName the clientEngSourceName to set
     */
    public void setClientEngSourceName(String clientEngSourceName) {
        this.clientEngSourceName = clientEngSourceName;
    }
}
