<%-- 
    Document   : expuserchangepassword
    Created on : Apr 24, 2016, 5:20:26 PM
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
        <jsp:include page="../includes/commoncss.jsp"/>
        <!-- CSS : Include -->
        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/appjs/header.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/appjs/changepassword.js"/>"></script>
        <!-- JS : Include -->  
    </head>
    <body>
        <div id="ux-wrapper">
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
            
            <div class="ux-content ux-margin-top-2t">
                <c:if test="${(ermessage !=null)&&(fn:length(ermessage)>0)}">
                    <div  class="ux-msg-error ux-width-45t" >    
                        <c:out value="${ermessage}"/>
                    </div>
                </c:if>
                <c:if test="${(sucmessage !=null)&&(fn:length(sucmessage)>0)}">
                    <div  class="ux-msg-success ux-width-45t" >    
                        <c:out value="${sucmessage}"/>
                    </div>
                </c:if>
                <form name="changepass" method="post" autocomplete="off">
                <div class="ux-panl">
                    <table class="ux-tabl-form-multicol" style="width: auto; display: table;">
                        <tr>
                            <td colspan="2">
                                <h2 class="ux-margin-bottom-none">Change Password</h2>
                            </td>
                        </tr>
                        <tr>
                            <td><label for="currentPswd">Current Password:</label><span style="color:red">*</span></td>
                            <td><input id="currentPswd" type="password" name="currentPswd" maxlength="25" />
                                <label id="errorMsgCurrentPassword" class="ux-msg-error-inline"></label>
                            </td>
                        </tr>
                        <tr>
                            <td><label for="newPswd">New Password:</label><span style="color:red">*</span></td>
                            <td><input id="newPswd" type="password" name="newPswd" maxlength="40"/>
                                <label id="errorMsgNewPassword" class="ux-msg-error-inline"></label>
                            </td>
                        </tr>
                        <tr>
                            <td><label for="confirmPswd">Confirm Password:</label><span style="color:red">*</span></td>
                            <td><input id="confirmPswd" type="password" name="confirmPswd" maxlength="40" onFocus="this.value = this.value;" />
                                <label id="errorMsgConfirmPassword" class="ux-msg-error-inline"></label>
                            </td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td><input type="button" value="Save Changes" 
                                       title="Save Changes" id="updateProfile"
                                       onclick="fnExpChangePassword()" 
                                       class="ux-btn-default-action ux-width-9t"/>
                                &nbsp;
                                <input type="button" value="Cancel" 
                                       title="Cancel"
                                       onclick="window.location.href='<c:url value="/logout.htm"/>'" 
                                       class="ux-btn-default-action ux-width-9t"/>
                            </td>
                        </tr>
                    </table>
                </div>
                                       </form>
                <div class="ux-panl">
                    <table class="ux-tabl-form-multicol" style="width: auto; display: table;">
                        <tr>
                            <td colspan="2">
                                <h2 class="ux-margin-bottom-none">Password Guidelines</h2>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <ul class="ux-list-guidelines">
                                   <p>
                                    Passwords must :
                                </p>
                                    <li>be between 8 and 25 characters.</li>
                                    <li>must contain at least three of the four characters listed below :<br/><br/>
                                        <ul style="list-style-type:circle">
                                            <li>lowercase letter</li>
                                            <li> uppercase letter</li>
                                            <li>numeric (0-9)</li>
                                            <li>special characters (e.g.-, ?, % ,*, $, etc.)</li>
                                        </ul>
                                    </li>
                                    <li>be case-sensitive.</li>
                                         <li> not contain your username.</li>
                                       <li>  not be the same as any of your previous 10 passwords.  </li>
                                  
                                </ul>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <!-- End: Content -->
            <jsp:include page="../includes/footer.jsp"/>
        </div>

    </body>
</html>