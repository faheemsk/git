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
public class UserSecurityDetailsDTO extends BaseDTO implements Serializable{
    
    private long userSecurityKey;
    private String encUserSecurityKey;
    private long userProfileKey;
    private long securityQuestionKey;
    private String securityQuestion;
    private String securityAnswer;
    private long sequenceOrder;

    /**
     * @return the userSecurityKey
     */
    public long getUserSecurityKey() {
        return userSecurityKey;
    }

    /**
     * @param userSecurityKey the userSecurityKey to set
     */
    public void setUserSecurityKey(long userSecurityKey) {
        this.userSecurityKey = userSecurityKey;
    }

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
     * @return the securityQuestionKey
     */
    public long getSecurityQuestionKey() {
        return securityQuestionKey;
    }

    /**
     * @param securityQuestionKey the securityQuestionKey to set
     */
    public void setSecurityQuestionKey(long securityQuestionKey) {
        this.securityQuestionKey = securityQuestionKey;
    }

    /**
     * @return the securityAnswer
     */
    public String getSecurityAnswer() {
        return securityAnswer;
    }

    /**
     * @param securityAnswer the securityAnswer to set
     */
    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    /**
     * @return the sequenceOrder
     */
    public long getSequenceOrder() {
        return sequenceOrder;
    }

    /**
     * @param sequenceOrder the sequenceOrder to set
     */
    public void setSequenceOrder(long sequenceOrder) {
        this.sequenceOrder = sequenceOrder;
    }

    /**
     * @return the securityQuestion
     */
    public String getSecurityQuestion() {
        return securityQuestion;
    }

    /**
     * @param securityQuestion the securityQuestion to set
     */
    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    /**
     * @return the encUserSecurityKey
     */
    public String getEncUserSecurityKey() {
        return encUserSecurityKey;
    }

    /**
     * @param encUserSecurityKey the encUserSecurityKey to set
     */
    public void setEncUserSecurityKey(String encUserSecurityKey) {
        this.encUserSecurityKey = encUserSecurityKey;
    }
    
    
}
