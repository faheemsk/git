<%-- 
    Document   : organizationworklist
    Created on : Apr 13, 2016, 2:02:49 PM
    Author     : rpanuganti
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="constants" />
<un:useConstants className="com.optum.oss.constants.PermissionConstants" var="permissionConstants" />
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<%@taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>IRMaaS</title>
        <!-- CSS : Include -->
        <jsp:include page="../includes/commoncss.jsp"/>
        <link rel="stylesheet" href="<c:url value="/css/jquery.dataTables.css"/>" />
        <link rel="stylesheet" href="<c:url value="/appcss/organizationWorklist.css"/>" />
        <!-- CSS : Include -->
        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/appjs/organizationWorklist.js"/>"></script>
        <!-- JS : Include -->  
        <!-- Start : css modification for data table -->  
        <style>


        </style>
        <!-- End : css modification for data table -->  
        <c:set var="userType" value="${constants.DB_USER_TYPE_ADMIN}"/>
        <c:if test="${sessionScope.USER_SESSION != null}">
            <c:set var="userProfile" value="${sessionScope.USER_SESSION}"/>
            <c:set var="userType" value="${userProfile.userTypeObj.lookUpEntityName}"/>
        </c:if>
    </head>
    <body>
        <form name="organizationfrm" method="post" hidden></form>
        <!-- Start: Wrapper -->
        <div id="ux-wrapper"> 

            <!--Header starts here-->
            <jsp:include page="../includes/header.jsp"/>
            <!--Header ends here--> 



            <!-- Start: Breadcrumbs -->
            <ul class="ux-brdc ux-margin-left-1t" >
                <li><a title="Manage Organizations" href="#">Manage Organizations</a></li>
                <li> Manage Organizations</li>
            </ul>
            <!-- End: Breadcrumbs -->

            <!--Left navigation starts here-->
            <jsp:include page="../includes/leftpanel.jsp"/>
            <!--Left navigation ends here--> 

            <!-- content starts -->
            <div class="ux-content">
                <h1>Manage Organizations <span class="ux-float-right">
                        <!-- permission checking -->
                        <c:choose>
                            <c:when test="${userType==constants.DB_USER_TYPE_ADMIN}">
                                <input type="button" value="Add Organization" title="Add Organization" class="ux-btn-default-action" onclick="addOrganization()" />
                            </c:when>
                            <c:otherwise>
                                <c:if test="${permissionSet!=null}">
                                    <c:if test="${permissionSet.contains(permissionConstants.ADD_ORGANIZATION)}">
                                        <input type="button" value="Add Organization" title="Add Organization" class="ux-btn-default-action" onclick="addOrganization()" />
                                    </c:if>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                        <!-- permission checking -->
                    </span></h1>
                    <c:if test="${statusMessage != null}">
                    <div class="ux-msg-success ux-margin-bottom-min" id="msg">${statusMessage}</div>
                </c:if>
                <div class="ux-float-left ux-width-full-inclusive">
                   <spring:form id="OrgWorkList" name="OrgWorkList" action="#">
                        <input type="hidden" id="orgID" name="orgID" value=""></input>
                    </spring:form>
                    <table class="display" id="dataTable">
                        <thead>
                            <tr>
                                <td class="uid"> </td>
                                <td > </td>
                                <td > </td>
                                <td >All</td>
                                <td >All</td>
                                <td ><span><a href="#" onclick="$.fn.SearchFilterClear(this, '#dataTable')"><img src="images/refresh.png" title="Refreshh"/></a></span></td>
                            </tr>
                            <tr>
                                <th>ID </th>
                                <th>Parent Organization Name</th>
                                <th>Organization Name  </th>
                                <th>Organization Type </th>
                                <th>Status</th>
                                <th width="100" align="center">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${organizationDetails}" var="organizationDetails">
                                <tr>
                                    <td>${organizationDetails.organizationKey}</td> 
                                    <td>${organizationDetails.parentOrganizationName}</td>
                                    <td>${organizationDetails.organizationName}</td>
                                    <td>${organizationDetails.organizationTypeObj.lookUpEntityName}</td>
                                    <td>${organizationDetails.rowStatusValue}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${userType==constants.DB_USER_TYPE_ADMIN}">
                                                <!--<a href="<c:url value="/editOrganizationPage.htm?orgID=${organizationDetails.encOrganizationKey}"/>" class="ux-icon-edit" title="Edit"> Edit </a>--> 
                                                <a href="javascript:void(0)" onclick="$.fn.editOrg('${fn:trim(organizationDetails.encOrganizationKey)}')" class="ux-icon-edit" title="Edit"> Edit </a> 
                                                <!--<a href="<c:url value="/getOrgDetails.htm?orgID=${organizationDetails.encOrganizationKey}"/>" class="ux-icon-view" title="View">View</a>--> 
                                                <a href="javascript:void(0)" onclick="$.fn.orgDetails('${fn:trim(organizationDetails.encOrganizationKey)}')" class="ux-icon-view" title="View">View</a> 
                                            </c:when>
                                            <c:otherwise>
                                                <c:if test="${permissionSet!=null}">
                                                    <c:choose>
                                                        <c:when test="${organizationDetails.rowStatusValue==constants.DB_CONSTANT_ORGANIZATION_SCHEMA_NOT_ONBOARDED}">
                                                             <c:if test="${permissionSet.contains(permissionConstants.VIEW_ORGANIZATION)}">
                                                                <a href="javascript:void(0)" onclick="$.fn.orgDetails('${fn:trim(organizationDetails.encOrganizationKey)}')" class="ux-icon-view" title="View">View</a> 
                                                            </c:if>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:if test="${permissionSet.contains(permissionConstants.UPDATE_ORGANIZATION)}">
                                                      <!--<a href="<c:url value="/editOrganizationPage.htm?orgID=${organizationDetails.encOrganizationKey}"/>" class="ux-icon-edit" title="Edit"> Edit </a>--> 
                                                                <a href="javascript:void(0)" onclick="$.fn.editOrg('${fn:trim(organizationDetails.encOrganizationKey)}')" class="ux-icon-edit" title="Edit"> Edit </a> 
                                                            </c:if>
                                                            <c:if test="${permissionSet.contains(permissionConstants.VIEW_ORGANIZATION)}">
                                                                <a href="javascript:void(0)" onclick="$.fn.orgDetails('${fn:trim(organizationDetails.encOrganizationKey)}')" class="ux-icon-view" title="View">View</a> 
                                                               <!--<a href="<c:url value="/getOrgDetails.htm?orgID=${organizationDetails.encOrganizationKey}"/>" class="ux-icon-view" title="View">View</a>--> 
                                                            </c:if>
                                                        </c:otherwise>
                                                    </c:choose>
                                                  
                                                </c:if>
                                            </c:otherwise>
                                        </c:choose>

                                    </td>
                                </tr>
                            </c:forEach>

                        </tbody>
                    </table>
                </div>
            </div>
            <!-- content ends --> 

            <!-- Begin: Footer -->
            <jsp:include page="../includes/footer.jsp"/>
            <!-- End: Footer --> 
        </div>
        <!-- Start: Wrapper -->
    </body>
</html>
