<%-- 
    Document   : configureuser
    Created on : Apr 15, 2016, 6:11:32 PM
    Author     : hpasupuleti
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@taglib prefix="form" uri="/WEB-INF/tlds/spring-form.tld" %>
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
        <script type="text/javascript" src="<c:url value="/appjs/configureuser.js"/>"></script>
        <!-- JS : Include -->  
        <style>    
            .ux-head-cntr-logo {
                -moz-box-sizing: border-box;
                -box-sizing: border-box;
                -webkit-box-sizing: border-box;
                box-sizing: border-box;
                height: 88px;
                /* padding: 13px; */
                padding-right: 26px;
                padding-left: 13px;
                float: left;
            }
            .ux-head-logo {
                display: table-cell;
                vertical-align: bottom;
                height: 50px;
            }
        </style>
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
            <div class="ux-content">
                <form:form action="#" method="post" onsubmit=""
                           name="configUser" commandName="configUserObj">
                    <h1>Personal Information</h1>

                    <!--<p class="ux-form-required"> All fields required </p>-->
                    <p class="ux-form-required ux-margin-top-1t"> <strong class="ux-labl-required">*</strong> Required </p>
                    <c:if test="${errorMessage != null}">
                        <p class="ux-form-required">
                            <div  class="ux-msg-error ux-width-40t" >    
                                ${errorMessage}
                            </div>
                        </p>
                    </c:if>

                    <div class="ux-panl">
                        <table class="ux-tabl-form-multicol" style="width: auto; display: table;">
                            <tr><td><h2 class="ux-margin-bottom-none">Personal Information</h2></td></tr>
                            <tr>
                                <c:set var="userProfile" value="0"/>
                                <c:if test="${sessionScope.USER_SESSION != null}">
                                    <c:set var="userProfile" value="${sessionScope.USER_SESSION}"/>
                                </c:if>

                                <td><label for="first-name">First Name:</label></td>
                                <td>${userProfile.firstName}</td>
                            </tr>
                            <tr>
                                <td><label for="last-name">Last Name:</label></td>
                                <td>${userProfile.lastName}</td>
                            </tr>
                            <tr>
                                <td><label for="username">Username:</label></td>
                                <td>${userProfile.email}</td>
                            </tr>
                            <tr>

                                <td><label for="currentPassword">Current Password:</label><span style="color:red">*</span></td>
                                <td><input id="currentPassword" type="password" name="currentPwd" maxlength="25"/>
                                    <label id="errorMsgCurrentPassword" class="ux-msg-error-inline"></label>
                                </td>
                            </tr>
                            <tr>
                                <td><label for="newPassword">New Password:</label><span style="color:red">*</span></td>
                                <td><input id="newPassword" type="password" name="newPwd" maxlength="40"/>
                                    <label id="errorMsgNewPassword" class="ux-msg-error-inline"></label>
                                </td>
                            </tr>
                            <tr>
                                <td><label for="confirmPassword">Confirm Password:</label><span style="color:red">*</span></td>
                                <td><input id="confirmPassword" type="password" name="confirmPwd" maxlength="40"/>
                                    <label id="errorMsgConfirmPassword" class="ux-msg-error-inline"></label>
                                </td>
                            </tr>
                        </table>
                    </div>

                    <div class="ux-clear">  </div>      

                    <div class="ux-panl">
                        <table class="ux-tabl-form-multicol" style="width: auto; display: table;">
                            <tr><td><h2 class="ux-margin-bottom-none">Security Profile</h2>
                                </td></tr>
                            <tr><td colspan="2">
                                    <label id="errorMsgSameQuestion" class="ux-msg-error-inline"></label>
                                </td></tr>
                            <tr>
                                <td><label for="security-question-1">Question 1:</label>
                                    <span style="color:red">*</span>
                                </td>
                                <td>
                                    <select name="userSecurityQuestionsListObj[0].securityQuestionKey" 
                                            id="security-question-1">
                                        <option value="0">----Select----</option>
                                        <c:if test="${securityQuestionLi != null}">
                                            <c:forEach items="${securityQuestionLi}" var="lookUpObj">
                                                <option value="${lookUpObj.masterLookupKey}" title="${lookUpObj.lookUpEntityName}">
                                                    ${lookUpObj.lookUpEntityName}
                                                </option>
                                            </c:forEach>
                                        </c:if>
                                    </select>
                                    <label id="errorMsgSecurityQuestion1" class="ux-msg-error-inline"></label>
                                </td>
                            </tr>
                            <tr>
                                <td><label for="example-answer-1">Answer to Question 1:</label><span style="color:red">*</span></td>
                                <td><input type="text"  class="ux-width-23t" id="securityAnswer1" 
                                           name="userSecurityQuestionsListObj[0].securityAnswer" 
                                           value="" onkeyup="limiter(this, 100, '', '')" />
                                    <label id="errorMsgSecurityAnswer1" class="ux-msg-error-inline"></label>
                                </td>
                            </tr>
                            <tr>
                                <td><label for="security-question-2">Question 2:</label>
                                    <span style="color:red">*</span>
                                </td>
                                <td>
                                    <select name="userSecurityQuestionsListObj[1].securityQuestionKey" 
                                            id="security-question-2">
                                        <option value="0">----Select----</option>
                                        <c:if test="${securityQuestionLi != null}">
                                            <c:forEach items="${securityQuestionLi}" var="lookUpObj">
                                                <option value="${lookUpObj.masterLookupKey}" title="${lookUpObj.lookUpEntityName}">
                                                    ${lookUpObj.lookUpEntityName}
                                                </option>
                                            </c:forEach>
                                        </c:if>
                                    </select>
                                    <label id="errorMsgSecurityQuestion2" class="ux-msg-error-inline"></label>
                                </td>

                            </tr>
                            <tr>
                                <td><label for="example-answer-2">Answer to Question 2:</label>
                                    <span style="color:red">*</span>
                                </td>
                                <td><input type="text" class="ux-width-23t" id="securityAnswer2" 
                                           name="userSecurityQuestionsListObj[1].securityAnswer"
                                           value="" onkeyup="limiter(this, 100, '', '')" />
                                    <label id="errorMsgSecurityAnswer2" class="ux-msg-error-inline"></label>
                                </td>
                            </tr>
                            <tr>
                                <td><label for="security-question-3">Question 3:</label>
                                    <span style="color:red">*</span>
                                </td>
                                <td>
                                    <select name="userSecurityQuestionsListObj[2].securityQuestionKey" 
                                            id="security-question-3">
                                        <option value="0">----Select----</option>
                                        <c:if test="${securityQuestionLi != null}">
                                            <c:forEach items="${securityQuestionLi}" var="lookUpObj">
                                                <option value="${lookUpObj.masterLookupKey}" title="${lookUpObj.lookUpEntityName}">
                                                    ${lookUpObj.lookUpEntityName}
                                                </option>
                                            </c:forEach>
                                        </c:if>
                                    </select>
                                    <label id="errorMsgSecurityQuestion3" class="ux-msg-error-inline"></label>
                                </td>

                            </tr>
                            <tr>
                                <td><label for="example-answer-3">Answer to Question 3:</label><span style="color:red">*</span></td>
                                <td><input type="text" class="ux-width-23t" id="securityAnswer3" 
                                           name="userSecurityQuestionsListObj[2].securityAnswer" 
                                           value="" onkeyup="limiter(this, 100, '', '')" />
                                    <label id="errorMsgSecurityAnswer3" class="ux-msg-error-inline"></label>
                                </td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td><input type="button" value="Save Changes" title="Save Changes" id="updateProfile" name="updateProfile" onclick="validateConfigUserPassword();" class="ux-btn-default-action ux-width-9t"/>&nbsp;
                                    <input type="button" value="Cancel" title="Cancel" id="cancel"  name="cancel" onclick="window.location.href = '<c:url value="/login.htm"/>'" class="ux-btn-default-action ux-width-7t"/>
                                </td>
                            </tr>
                        </table>
                    </div> 

                    <div class="ux-clear"></div>            
                    <table class="ux-tabl-form-multicol" style="width: auto; display: table;">
                        <tr>
                            <td>
                                <h2>Password Guidelines</h2>
                                <p>
                                    Passwords must :
                                </p>
                                <ul class="ux-list-guidelines">
                                    <li>be between 8 and 25 characters.</li>
                                    <li>must contain at least three of the four characters listed below :<br/><br/>
                                        <ul style="list-style-type:circle">
                                            <li>lowercase letter</li>
                                            <li> uppercase letter</li>
                                            <li>numeric (0-9)</li>
                                            <li>special characters (e.g.-, ?, % ,*, $, etc.)</li>
                                        </ul></li>
                                    <li>be case-sensitive.</li>
                                    <li> not contain your username.</li>
                                    <li>  not be the same as any of your previous 10 passwords.</li>
                                </ul>
                            </td>
                        </tr>
                    </table>
                </form:form>
            </div>
            <!-- End: Content -->
            <!-- Begin: Footer -->
            <jsp:include page="../includes/footer.jsp"/>
            <!-- End : Footer -->
        </div>

    </body>
</html>
