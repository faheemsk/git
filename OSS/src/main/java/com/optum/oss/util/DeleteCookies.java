/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 *
 * @author hpasupuleti
 */
@Component
public class DeleteCookies {

    private static final Logger logger = Logger.getLogger(DeleteCookies.class);

    public void eraseCookies(HttpServletRequest request, HttpServletResponse response) {
        logger.info("DeleteCookies Start Erasing Cookies::::");
        try {
            if (request.getCookies() != null) {
                if (request.getCookies().length > 0) {
                    logger.info("Erasing Cookies>Cookies Length:" + request.getCookies().length);

                    for (Cookie cookie : request.getCookies()) {
                        //logger.info("Erasing Cookies>Cookie Name:" + cookie.getName());
                        cookie.setMaxAge(0);
                        cookie.setValue("");
                        response.addCookie(cookie);
                    }
                }
            }
        } catch (Exception e) {
            logger.info("Exception occured in Erasing Cookies::::");
            e.getMessage();
        }
        logger.info("DeleteCookies End Erasing Cookies::::");
    }

}
