<%-- 
    Document   : profileconfirmpassword
    Created on : May 3, 2016, 5:21:26 PM
    Author     : mrejeti
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
                        <li><a title="Home" href="#" onclick="headerFn('manageRolepage.htm')">Home</a></li>
                        </c:when>
                        <c:otherwise>
                        <li><a href="#" onclick="headerFn('userLandingPage.htm')">Home</a></li>
                        </c:otherwise>
                    </c:choose>
                <li>Confirm Password</li>
            </ul>
            <!-- End :  Bread Crumbs -->

            <!-- Begin : Left Panel -->
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
                <form name="changepass" method="post"  autocomplete="off">
                    <input type="hidden" value="" name="mnval" id="mnvalprofile">
                    <div class="ux-panl">
                        <div class="ux-panl-header ux-panl-with-underline">
                            <h2>Confirm Password</h2>
                        </div>
                        <div class="ux-panl-content">
                            <table class="ux-tabl-form-multicol" style="width: auto; display: table;">
                                <tr>
                                    <td><label for="currentPassword">Password: </label><span style="color:red">*</span></td>
                                    <td><input id="currentPassword" type="password" name="currentPassword" maxlength="25" onFocus="this.value = this.value;" />
                                        <label id="errorMsgCurrentPassword" class="ux-msg-error-inline"></label>
                                    </td>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td><input type="button" value="Validate" 
                                               title="Validate" id="updateProfile"
                                               onclick="fnConfirmPassword()" 
                                               class="ux-btn-default-action ux-width-9t"/>
                                    </td>
                                </tr>
                            </table>
                        </div>
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
