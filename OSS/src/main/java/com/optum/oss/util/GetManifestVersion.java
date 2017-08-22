    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.util;

import java.util.Iterator;
import java.util.Properties;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

/**
 *
 * @author hpasupuleti
 */
@Component
public class GetManifestVersion implements ServletContextAware {

    private static final Logger log = Logger.getLogger(GetManifestVersion.class);

    // ----------------------------------- PRIVATE ATTRIBUTES
    private static String CURRENT_VERSION = null;
    private static GetManifestVersion instance = null;
    private static ServletContext servletContext = null;

    // ------------------------------------- PUBLIC FUNCTIONS
    private GetManifestVersion() {
    }

    public static synchronized String getVersion() {
        if (instance == null) {
            try {
                instance = new GetManifestVersion();
                String name = "/META-INF/MANIFEST.MF";
                Properties props = new Properties();
                props.load(servletContext.getResourceAsStream(name));
                
                for (Iterator iterator = props.keySet().iterator(); iterator.hasNext();) {
                    Object key = iterator.next();
                    Object value = props.get(key);
                    System.out.println(key + "=" + value);
                }

                GetManifestVersion.CURRENT_VERSION = (String) props.get("Build-Version");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return GetManifestVersion.CURRENT_VERSION;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        GetManifestVersion.servletContext = servletContext;
    }

}
