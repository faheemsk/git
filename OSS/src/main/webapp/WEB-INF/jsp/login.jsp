<%-- 
    Document   : login
    Created on : Apr 4, 2016, 12:36:50 PM
    Author     : hpasupuleti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>IRMaaS</title>
        <!-- CSS : Include -->
        <jsp:include page="includes/commoncss.jsp"/>
        <!-- CSS : Include -->
        <!-- JS : Include -->
        <jsp:include page="includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/appjs/LoginJS.js"/>"></script>
        <!-- JS : Include -->  
    </head>
        <body onkeydown="return showKeyCode(event)" oncontextmenu="return false" >
<!--          onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload=""-->
          
        <div id="ux-wrapper" >
            <!-- Begin : Header -->
            <div class="ux-head">
                <div class="ux-head-cntr-logo">
                    <span class="ux-head-logo">
                        <img src="<c:url value="/images/OPTUM-LOGO-Security-Solutions_no-padding.png"/>" alt="Product Name" />
                    </span>
                </div>
            </div>
            <!-- End : Header -->

            <!-- Begin: Content -->
            <div class="ux-content">
                <form action="#" method="post" 
                      name="loginForm" id="loginFormId">
                    <table  class="ux-tabl-form-sip" autocomplete="off">
                        <c:choose>
                            <c:when test="${actmessage != null}">
                                <tr>
                                    <td colspan="2">
                                        <div  class="ux-msg-error ux-width-25t" >    
                                            <c:out value="${actmessage}"/>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <input type="button" value="Yes" title="Yes" style="margin-top:0;" 
                                               class="ux-btn-default-action ux-width-7t"  
                                               onclick="fnKillActiveSession()"/>
                                        <input type="button" value="No" title="No" style="margin-top:0;" 
                                               class="ux-btn-default-action ux-width-7t"  
                                               onclick="fnLoginRedirect()"/>
                                    </td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <tr style="background:#fff;"> 
                                    <td colspan="2">
                                        <p class="ux-colr-red">
                                            <span class="rf-msgs " id=""></span>
                                        </p>
                                        <h1 class="ux-margin-none">Sign In</h1>

                                    </td>
                                </tr>
                                <tr>
                                    <input style="display: none" type="text" name="fakeusernameremembered"/>
                                        <input style="display: none" type="password" name="fakepasswordremembered"/>
                                    <td><label for="user_name">Username:</label></td>
                                    <td><input id="user_name" type="text" name="user_name" maxlength="100" onFocus="this.value = this.value;" onkeypress="login(user_name)"/><span class="rf-msg ux-msg-error-inline" id="signInForm:j_idt75"></span>
                                        <label id="errorUserName" class="ux-width-40t"></label></td>
                                </tr>
                                <tr style="background:#fff;">
                                    <td><label for="password">Password:</label></td>
                                    <td><input id="password" type="password" name="password" value="" maxlength="100" onkeypress="login(password)"/><span class="rf-msg ux-msg-error-inline ux-margin-left-1t" id="signInForm:j_idt79"></span>
                                        <label id="errorPassword" class="ux-width-40t"></label></td>
                                </tr>

                                <tr>
                                    <td></td>
                                    <td>
                                        <input type="button" name="" value="Sign In" title="Sign In" style="margin-top:0;" 
                                               class="ux-btn-default-action ux-width-7t"  
                                               onclick="loginValidate()"
                                               />
                                        <br />
                                        <a id="forgotSignIn" name="signInForm:forgotSignIn" href="<c:url value="forgotpassword.htm"/>"
                                           class="ux-margin-top-5t">
                                            Forgot your sign-in information? 
                                        </a>
                                    </td>
                                </tr>
                            </c:otherwise>
                        </c:choose>

                    </table>
                </form>

                <c:if test="${(valmessage !=null)&&(fn:length(valmessage)>0)}">
                    <div align="center" id="blrd" class="ux-margin-bottom-3t">  
                        <span class="ux-msg-error ux-padding-bottom-1t " >    
                            <c:out value="${valmessage}"/>
                        </span>
                    </div>
                    <div class="ux-clear"></div>       

                </c:if>
                <c:if test="${sessionInactiveMessage !=null }">
                    <div align="center" id="blrd" class="ux-margin-bottom-3t">  
                        <span class="ux-msg-error ux-padding-bottom-1t " >    
                            <c:out value="${sessionInactiveMessage}"/>
                        </span>
                    </div>
                    <div class="ux-clear"></div>  
                </c:if>
                <c:if test="${(sucmessage !=null)&&(fn:length(sucmessage)>0)}">
                    <div align="center"  class="ux-margin-bottom-3t">  
                        <span class="ux-msg-success ux-padding-bottom-1t " >    
                            <c:out value="${sucmessage}"/>
                        </span>
                    </div>
                    <div class="ux-clear"></div> 
                </c:if>
                <div class="ux-width-35t ux-centered-block">
                    <p>
                        By logging into Optum Security Solutions you are acknowledging that you are an authorized user; that unauthorized or improper use of Optum Security Solutions may result in disciplinary action, as well as civil and criminal penalties; that Optum Security Solutions usage may be monitored, recorded, and subject to audit; that use of Optum Security Solutions indicates consent to monitoring and recording. The use of Optum Security Solutions by individual(s) or entity may allow access to information that is privileged, confidential and exempt from disclosure under applicable law, including the Health Insurance Portability and Accountability Act (PL 104-191).
                    </p>
                </div>
            </div>
            <!-- End: Content -->
            <jsp:include page="includes/footer.jsp"/>
        </div>

    </body>
</html>
