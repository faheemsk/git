<%-- 
    Document   : changepassword
    Created on : Apr 24, 2016, 2:10:46 PM
    Author     : hpasupuleti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld" %>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="constants" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>IRMaaS</title>
        <!-- CSS : Include -->
        <jsp:include page="../includes/commoncss.jsp"/>
        <!-- CSS : Include -->
        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        <!-- JS : Include -->  
        <script type="text/javascript" src="<c:url value="/appjs/changepassword.js"/>"></script>
        <c:set var="userType" value="${constants.DB_USER_TYPE_ADMIN}"/>
        <c:if test="${sessionScope.USER_SESSION != null}">
            <c:set var="userProfile" value="${sessionScope.USER_SESSION}"/>
            <c:set var="userType" value="${userProfile.userTypeObj.lookUpEntityName}"/>
        </c:if>
    </head>
    <body>
        <div id="ux-wrapper">
            <!-- Begin : Header -->
            <jsp:include page="../includes/header.jsp"/>
            <!-- End : Header -->

            <!-- Begin : Bread Crumbs -->
            <ul class="ux-brdc ux-margin-left-1t" >
                   <c:choose>
                    <c:when test="${userType==constants.DB_USER_TYPE_ADMIN}">
                        <li><a title="Home" href="<c:url value="manageRolepage.htm"/>">Home</a></li>
                        </c:when>
                        <c:otherwise>
                      <li><a title="Home" href="<c:url value="userLandingPage.htm"/>">Home</a></li>
                        </c:otherwise>
                    </c:choose>
                <li>Change Password</li>
            </ul>
            <!-- End :  Bread Crumbs -->

            <!-- Begin : Left Panel -->
            <jsp:include page="../includes/profileleftpanel.jsp"/>
            <!-- End : Left Panel -->

            <!-- Begin: Content -->
            <div class="ux-content">
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
                <form name="changepass" method="post">
                    <div class="ux-panl">
                        <div class="ux-panl-header ux-panl-with-underline">
                            <h2>Change Password</h2>
                        </div>
                        <div class="ux-panl-content">
                            <table class="ux-tabl-form-multicol" style="width: auto; display: table;">
                                <tr>
                                    <td><label for="currentPswd">Current Password:</label><span style="color:red"> * </span></td>
                                    <td><input id="currentPswd" type="password" name="currentPswd" maxlength="25" onFocus="this.value = this.value;" />
                                        <label id="errorMsgCurrentPassword" class="ux-msg-error-inline"></label>
                                    </td>
                                </tr>
                                <tr>
                                    <td><label for="newPswd">New Password:</label><span style="color:red"> * </span></td>
                                    <td><input id="newPswd" type="password" name="newPswd" maxlength="40" onFocus="this.value = this.value;" />
                                        <label id="errorMsgNewPassword" class="ux-msg-error-inline"></label>
                                    </td>
                                </tr>
                                <tr>
                                    <td><label for="confirmPswd">Confirm Password:</label><span style="color:red"> * </span></td>
                                    <td><input id="confirmPswd" type="password" name="confirmPswd" maxlength="40" onFocus="this.value = this.value;" />
                                        <label id="errorMsgConfirmPassword" class="ux-msg-error-inline"></label>
                                    </td>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td><input type="button" value="Save Changes" 
                                               title="Save Changes" id="updateProfile"
                                               onclick="fnChangePassword()" 
                                               class="ux-btn-default-action ux-width-9t"/>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    <div class="ux-panl">
                        <div class="ux-panl-header ux-panl-with-underline">
                            <h2>Password Guidelines</h2>
                        </div>

                        <table class="ux-tabl-form-multicol" style="width: auto; display: table;">
                            <tr>
                                <td colspan="2">
                                    <ul class="ux-list-guidelines">
                                        <p>Passwords must :</p>
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
                                        <li>  not be the same as any of your previous 10 passwords.</li>
                                    </ul>
                                </td>
                            </tr>
                        </table>
                    </div>
                </form>
            </div>
            <!-- End : Content -->

            <!-- Begin: Footer -->
            <jsp:include page="../includes/footer.jsp"/>
            <!-- End : Footer -->
        </div>
    </body>
</html>
