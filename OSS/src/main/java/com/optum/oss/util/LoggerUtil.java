/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.util;

import com.optum.oss.exception.AppException;
import org.springframework.stereotype.Component;

/**
 *
 * @author mrejeti
 */
@Component
public class LoggerUtil {

    public String prepareLog(final String userEmailID, final String ipAddress, final String message) throws AppException {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(ipAddress);
        strBuilder.append(",");
        strBuilder.append(userEmailID);
        strBuilder.append(",");
        strBuilder.append(message);
        return strBuilder.toString();
    }

}
