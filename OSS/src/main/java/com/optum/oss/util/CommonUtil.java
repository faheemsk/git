/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.util;

import com.optum.oss.exception.AppException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author hpasupuleti
 */
@Component
public class CommonUtil {

    public String replaceSpaceAndSpecialChars(final String paramString) {
        String returnString = "";
        Pattern pattern = Pattern.compile("[^a-z A-Z 0-9]");
        Matcher matcher = pattern.matcher(paramString);
        returnString = matcher.replaceAll("");
        returnString = returnString.replaceAll("\\s+", "_");

        return returnString;
    }

    public boolean isNewPWDContainsOldPWDChars(final String newPWD, final String oldPWD) {
        String str = oldPWD;
        String newString = newPWD;
        int count = 0;
        char chrArr[] = newString.toCharArray();
        for (char c : chrArr) {
            if (str.contains(c + "")) {
                count++;
            }
        }
        if (count >= 4) {
            return true;
        } else {
            return false;
        }
    }

    public static String convertToCommaDelimited(String[] list) {
        StringBuffer ret = new StringBuffer("");
        for (int i = 0; list != null && i < list.length; i++) {
            ret.append(list[i]);
            if (i < list.length - 1) {
                ret.append(',');
            }
        }
        return ret.toString();
    }

    public static String replaceCommaByCommaAndSpace(final Object obj) {
        String repValue = "";
        if (obj != null) {
            repValue = obj.toString();
            repValue = repValue.replaceAll(",", ", ");

        } else {
            return repValue;
        }
        return repValue;
    }

    public String encodeHTMLEntities(String inputString) throws AppException {
        if (inputString.contains("<")) {
            inputString = inputString.replaceAll("<", "&lt;");
        }
        if (inputString.contains(">")) {
            inputString = inputString.replaceAll(">", "&gt;");
        }
        if (inputString.contains("\"")) {
            inputString = inputString.replaceAll("\"", "&quot;");
        }
        if (inputString.contains("'")) {
            inputString = inputString.replaceAll("<", "&#x27;");
        }
        if (inputString.contains("/")) {
            inputString = inputString.replaceAll("/", "&#x2F;");
        }
        if (inputString.contains("&")) {
            inputString = inputString.replaceAll("<", "&amp;");
        }
        return inputString;
    }

    public static String trimTheValue(final Object obj) {
        String repValue = "";
        if (obj != null) {
            repValue = obj.toString().trim();
        } else {
            return repValue;
        }
        return repValue;
    }

    public static String replaceNullWithEmpty(final Object obj) {
        String repValue = "";
        if (obj != null) {
            repValue = obj.toString().trim();
        } else {
            return repValue;
        }
        return repValue;
    }

    public static double percentageCount(double value, double totalCount) {
        double result = (value/totalCount);
        result=result*100;
        return result;
    }
    
    public boolean refreshCheck(HttpServletRequest request, 
            RedirectAttributes redirectAttributes,
            Model model){
        boolean flag = false;
        if (request.getParameter("mnval") != null || model.asMap().get("mnval") != null) {
            if(!(request.getSession().getAttribute("mnval")+"").equalsIgnoreCase(request.getParameter("mnval"))){
                flag = true;
                redirectAttributes.addFlashAttribute("mnval", "mnval");
            }
            request.getSession().setAttribute("mnval", request.getParameter("mnval"));
        }
        return flag;
    }
    
      public static String removeHtmlTagsFromString(final Object obj) throws AppException {
        String repValue = "";
        if (obj != null) {
            repValue = CommonUtil.deCodeHTMLEntities(obj.toString());
            repValue = repValue.replaceAll("&nbsp;"," ");
            repValue = repValue.replaceAll("\\<.*?>","");
            repValue = repValue.trim();
        } else {
            return repValue;
        }
        return repValue;
    }
      
      public static String deCodeHTMLEntities(String inputString) throws AppException {
        if (inputString.contains("&lt;")) {
            inputString = inputString.replaceAll("&lt;", "<");
        }
        if (inputString.contains("&gt;")) {
            inputString = inputString.replaceAll("&gt;", ">");
        }
        if (inputString.contains("&quot;")) {
             inputString = inputString.replaceAll("&quot;", "\"");
        }
        if (inputString.contains("&#x27;")) {
              inputString = inputString.replaceAll("&#x27;", "<");
        }
        if (inputString.contains("&#x2F;")) {
            inputString = inputString.replaceAll("&#x2F;", "/");
        }
//        if (inputString.contains("&")) {
//            inputString = inputString.replaceAll("<", "&amp;");
//            inputString = inputString.replaceAll("<", "&amp;");
//        }
        return inputString;
    }
      
    public static String prepareSchemaNameFormat(String orgName, long orgKey) throws AppException {

        String s =orgName;

        //Removing Space
        s = s.replace(" ", "");

        if (s.length() >= 4) {
            s = s.substring(0, 3);
        } else {
            s = s;
        }

        //Appending  organizaion Key to organization name 
        s = s.concat(String.valueOf(orgKey));

        return s;
    }

}
