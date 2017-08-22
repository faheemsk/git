<%-- 
    Document   : personnelinformation
    Created on : Apr 24, 2016, 2:11:57 PM
    Author     : hpasupuleti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld" %>
<%@taglib prefix="form" uri="/WEB-INF/tlds/spring-form.tld" %>
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
        <script type="text/javascript" src="<c:url value="/appjs/personnelinfo.js"/>"></script>
        <!-- JS : Include -->  
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
                        <li><a title="Home" href="#" onclick="headerFn('manageRolepage.htm')">Home</a></li>
                        </c:when>
                        <c:otherwise>
                        <li><a title="Home" href="#" onclick="headerFn('userLandingPage.htm')">Home</a></li>
                        </c:otherwise>
                    </c:choose>

                <li>Personal Information</li>
            </ul>
            <!-- End :  Bread Crumbs -->

            <!-- Begin : Left Panel -->
            <jsp:include page="../includes/profileleftpanel.jsp"/>
            <!-- End : Left Panel -->

            <!-- Begin: Content -->
            <div class="ux-content">
                <c:if test="${(valmessage !=null)&&(fn:length(valmessage)>0)}">
                    <div  class="ux-msg-error ux-width-45t" >    
                        <c:out value="${valmessage}"/>
                    </div>
                </c:if>
                <c:if test="${(sucmessage !=null)&&(fn:length(sucmessage)>0)}">
                    <div  class="ux-msg-success ux-width-45t" >    
                        <c:out value="${sucmessage}"/>
                    </div>
                </c:if>

                <div class="ux-panl">
                    <div class="ux-panl-header ux-panl-with-underline">
                        <h2>Personal Information</h2>
                    </div>
                    <div class="ux-panl-content">
                        <table class="ux-tabl-form-multicol" style="width: auto; display: table;">
                            <c:if test="${sessionScope.USER_SESSION != null}">
                                <c:set var="userProfile" value="${sessionScope.USER_SESSION}"/>

                                <tr>
                                    <td><label for="example-first-name">First Name:</label></td>
                                    <td><c:out value="${userProfile.firstName}"/></td>
                                </tr>
                                <tr>
                                    <td><label for="example-last-name">Last Name:</label></td>
                                    <td>${userProfile.lastName}</td>
                                </tr>
                                <tr>
                                    <td><label for="example-username">Username:</label></td>
                                    <td>${userProfile.email}</td>
                                </tr>
                                <c:set var="count" value="1"/>
                                <c:if test="${fn:length(userProfile.userSecurityQuestionsListObj)>0}">
                                    <c:forEach items="${userProfile.userSecurityQuestionsListObj}" var="securityDtl">
                                        <tr> <td><label for="example-security-question-1">Question ${count}:</label></td>
                                            <td>${securityDtl.securityQuestion}</td>
                                        </tr>
                                        <tr> <td><label for="example-answer-1">Answer to Question ${count}:</label></td>
                                            <td>${securityDtl.securityAnswer}</td>
                                        </tr>
                                        <c:set var="count" value="${count+1}"/>
                                    </c:forEach>
                                </c:if>
                            </c:if>
                        </table>
                    </div>
                </div>

                <div class="ux-clear">  </div>      
                <form:form action="#" method="post" onsubmit=""
                           name="configUser" commandName="updUserInfo">
                    <input type="hidden" value="a2qa2s1" name="mnval">
                    <div class="ux-panl">
                        <div class="ux-panl-header ux-panl-with-underline">
                            <h2>Security Profile</h2>
                        </div>
                        <div class="ux-panl-content ux-padding-top-none">
                            <div class="ux-FormStyleLeftAlign">
                                <p class="ux-form-required ux-margin-top-1t"> <strong class="ux-labl-required">*</strong> Required </p>
                                <div class="ux-panl-content">
                                    <table class="ux-tabl-form-multicol" style="width: auto; display: table;">
                                        <tr><td colspan="2"><label id="errorMsgSameQuestion" class="ux-msg-error-inline"></label></td></tr>
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
                                            <td><label for="example-answer-1">Answer to Question 1:</label><span style="color:red"> * </span></td>
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
                                            <td><label for="example-answer-3">Answer to Question 3:</label><span style="color:red"> * </span></td>
                                            <td><input type="text" class="ux-width-23t" id="securityAnswer3" 
                                                       name="userSecurityQuestionsListObj[2].securityAnswer" 
                                                       value="" onkeyup="limiter(this, 100, '', '')" />
                                                <label id="errorMsgSecurityAnswer3" class="ux-msg-error-inline"></label>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>&nbsp;</td>
                                            <td><input type="button" value="Save Changes" 
                                                       title="Save Changes" id="updateProfile"
                                                       onclick="fnSavePersonnelInfo()" 
                                                       class="ux-btn-default-action ux-width-9t"/>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </form:form>
                <!-- End : Content -->

            </div>
            <!-- Begin: Footer -->
            <jsp:include page="../includes/footer.jsp"/>
            <!-- End : Footer -->
        </div>
    </body>
</html>
