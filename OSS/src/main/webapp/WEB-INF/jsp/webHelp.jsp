<%-- 
    Document   : webHelp
    Created on : May 12, 3030, 4:48:22 PM
    Author     : spatepuram
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  uri="/WEB-INF/tlds/spring-form.tld" prefix="form" %>
<%@taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="constants" />
<un:useConstants className="com.optum.oss.constants.PermissionConstants" var="prmnsConstants" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>IRMaaS : Home Page</title>
        <!-- CSS : Include -->
        <jsp:include page="includes/commoncss.jsp"/>
        <!-- CSS : Include -->
        <!-- JS : Include -->
        <jsp:include page="includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/appjs/LoginJS.js"/>"></script>
        <!-- JS : Include -->  
        <c:if test="${sessionScope.USER_SESSION != null}">
            <c:set var="userProfile" value="${sessionScope.USER_SESSION}"/>
        </c:if>

         
        <c:set var="userType" value="${constants.DB_USER_TYPE_ADMIN}"/>
        <c:if test="${sessionScope.USER_SESSION != null}">
            <c:set var="userProfile" value="${sessionScope.USER_SESSION}"/>
            <c:set var="userType" value="${userProfile.userTypeObj.lookUpEntityName}"/>
        </c:if>
    </head>
    <body>
        <div id="ux-wrapper">
            <!-- Begin : Header -->
            <jsp:include page="includes/header.jsp"/>
            <!-- End : Header -->

            <!-- Begin : Bread Crumbs -->
            <ul class="ux-brdc ux-margin-left-1t" >
                <c:choose>
                    <c:when test="${userType == constants.DB_USER_TYPE_ADMIN}">
                        <li><a href="#" onclick="headerFn('manageRolepage.htm')">Home</a></li>
                        </c:when>
                        <c:otherwise>
                        <li><a href="#" onclick="headerFn('userLandingPage.htm')">Home</a></li>
                        </c:otherwise>
                    </c:choose>

                <li>User Guide</li>
            </ul>
            <!-- End :  Bread Crumbs -->
            <!-- Begin: Content -->
            <div class="ux-content">
                <h1>User Guide</h1>
                <div class="ux-panl ux-float-left">
                    <div class="ux-panl-header ux-panl-with-underline">
                        <h2>User Guides</h2>
                    </div>
                    <div class="ux-panl-content">
                        <div class="ux-hform">
                            <input id="encUserPrf" type="hidden" value="${userProfile.encUserProfileProperty}" />
                            <c:if test="${userType == constants.DB_USER_TYPE_ADMIN}">
                                <dl class="ux-width-30t ux-height-4t">
                                        <dt>
                                        <a href="#" onclick="openWebHelp('/OSS Web Application Administrator Guide_1.0.0.0/OSS_Web_Application_Administrator_Guide.htm');" title="Administration">
                                            <span class="ux-float-left"> <img src="<c:url value="/images/admin.png"/>" width="24" height="24" alt="" /></span> <span class="ux-float-left ux-margin-top-min ux-margin-left-1t" style="color:#0064a4">Administration</span></a>
                                        </dt>
                                    </dl>
                            </c:if>
                            <c:forEach var="moduleSet" items="${userProfile.moduleSet}">
                 <c:if test="${fn:contains(moduleSet, prmnsConstants.ADMINISTRATION_MODULE)}">
                                    <dl class="ux-width-30t ux-height-4t">
                                        <dt>
                                        <a href="#" onclick="openWebHelp('/OSS Web Application Administrator Guide_1.0.0.0/OSS_Web_Application_Administrator_Guide.htm');" title="Administration">
                                            <span class="ux-float-left"> <img src="<c:url value="/images/admin.png"/>" width="24" height="24" alt="" /></span> <span class="ux-float-left ux-margin-top-min ux-margin-left-1t" style="color:#0064a4">Administration</span></a>
                                        </dt>
                                    </dl>
                                </c:if>
                                <c:if test="${fn:contains(moduleSet, prmnsConstants.CLIENT_ENGAGEMENT_MODULE)}">
                                    <dl class="ux-width-30t ux-height-4t">
                                        <dt>
                                        <a href="#" onclick="openWebHelp('/OSS_Client Engagement User Guide_1.0.0.0/OSS_Client_Engagement_Guide.htm');"title="Client Engagement">
                                            <span class="ux-float-left"> <img src="<c:url value="/images/client-engagement.png"/>" width="20" height="20" alt="" /></span> <span class="ux-float-left ux-margin-top-min ux-margin-left-1t" style="color:#0064a4">Client Engagement</span></a>
                                        </dt>
                                    </dl>
                                </c:if>
                                <c:if test="${fn:contains(moduleSet, prmnsConstants.DATA_UPLOAD_MODULE)}">
                                    <dl class="ux-width-30t ux-height-4t">
                                        <dt>
                                        <a href="#" onclick="openWebHelp('/OSS_Engagement Data Upload User Guide_1.0.0.0/OSS_Engagement_Data_Upload_Guide.htm');" title="Engagement Data Upload">
                                            <span class="ux-float-left"> <img src="<c:url value="/images/engagement-dataupload.png"/>" width="20" height="20" alt="" /></span> <span class="ux-float-left ux-margin-top-min ux-margin-left-1t" style="color:#0064a4">Engagement Data Upload</span></a>
                                        </dt>
                                    </dl>
                                </c:if>
                                <c:if test="${fn:contains(moduleSet, prmnsConstants.ANALYST_MODULE)}">
                                    <dl class="ux-width-30t ux-height-4t">
                                        <dt>
                                        <a href="#" onclick="openWebHelp('/OSS_Analyst Validation Module User Guide_1.0.0.0/OSS_Analyst_Validation_Module_Guide.htm');" title="Analyst">
                                            <span class="ux-float-left"> <img src="<c:url value="/images/Analyst.png"/>" width="24" height="24" alt="" /></span> <span class="ux-float-left ux-margin-top-min ux-margin-left-1t" style="color:#0064a4">Analyst</span></a>
                                        </dt>
                                    </dl>
                                </c:if>
                                <c:if test="${fn:contains(moduleSet, prmnsConstants.REPORTING_MODULE)}">
                                    <dl class="ux-width-30t ux-height-4t">
                                        <dt>
                                        <a href="#" onclick="openWebHelp('/OSS_Dashboards and Reports User Guide_1.0.0.0/OSS_Dashboards_and_Reports_Guide.htm');" title="Reporting">
                                            <span class="ux-float-left"> <img src="<c:url value="/images/reporting.png"/>" width="20" height="20" alt="" /></span> <span class="ux-float-left ux-margin-top-min ux-margin-left-1t" style="color:#0064a4">Reporting</span></a>
                                        </dt>
                                    </dl>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>

                </div>
            </div>
            <!-- End : Content -->

            <!-- Begin: Footer -->
            <jsp:include page="includes/footer.jsp"/>
            <!-- End : Footer -->
        </div>
    </body>
</html>
