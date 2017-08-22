<%-- 
    Document   : manageusers
    Created on : Apr 13, 2016, 2:47:20 PM
    Author     : sbhagavatula
--%>
<%
try
{
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld"%>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="constants" />
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<%@taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>IRMaaS</title>
        <!-- CSS : Include -->
        <jsp:include page="../includes/commoncss.jsp"/>
        <link rel="stylesheet" href="<c:url value="/css/jquery.dataTables.css"/>" />
        <!-- CSS : Include -->
        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/appjs/UserListJS.js"/>"></script>
        <!-- JS : Include -->  
        <c:set var="userType" value="${constants.DB_USER_TYPE_ADMIN}"/>
        <c:if test="${sessionScope.USER_SESSION != null}">
            <c:set var="userProfile" value="${sessionScope.USER_SESSION}"/>
            <c:set var="userType" value="${userProfile.userTypeObj.lookUpEntityName}"/>
        </c:if>
    </head>
    <body>
        <un:useConstants className="com.optum.oss.constants.PermissionConstants" var="prmnsConstants" />
        <form name="mnguserfrm" method="post" hidden></form>
        <div id="ux-wrapper">
            <!-- Header : Include -->
            <jsp:include page="../includes/header.jsp"/>
            <!-- Header : Include -->

            <!-- Start: Breadcrumbs -->
            <ul class="ux-brdc ux-margin-left-1t" >
                <li><a title="Manage Users" href="#">Manage Users</a></li>
                <li>Manage Users</li>
            </ul>
            <!-- End: Breadcrumbs -->

            <!-- Left Panel : Include -->
            <jsp:include page="../includes/leftpanel.jsp"/>
            <!-- Left Panel : Include -->

            <!-- Begin: Content -->
            <div class="ux-content">
                <h1>Manage Users <span class="ux-float-right">
                    <!--  Based on permissions, 'Add User' button is displayed -->
                    <c:choose>
                        <c:when test="${userType==constants.DB_USER_TYPE_ADMIN}">
                            <input type="button" value="Add User" title="Add User" class="ux-btn-default-action" onclick="addUserFn()"  />
                        </c:when>
                        <c:otherwise>
                            <c:if test="${permissions.contains(prmnsConstants.ADD_USER)}">
                                <input type="button" value="Add User" title="Add User" class="ux-btn-default-action" onclick="addUserFn()"  />
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                </span></h1>
                <c:if test="${statusMessage != null}">
                    <div class="ux-msg-success ux-margin-bottom-min" id="msg">${statusMessage}</div>
                </c:if>
                <div class="ux-float-left ux-width-full-inclusive">
                    <spring:form id="ManageUser" name="ManageUser" action="#" > 
                        <input type="hidden" value="" name="userProfileKey" id="userProfileKey"/>
                    </spring:form>
                    <table id="dataTable"  width="100%" class="display">
                        <thead>
                            <tr>
                                <td class="uid"></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td>All</td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td>All</td>
                                <td ><span><a href="#" onclick="$.fn.SearchFilterClear(this, '#dataTable')" title="Refresh"><img src="images/refresh.png" /></a></span></td>
                            </tr>
                            <tr>
                                <th>ID</th>
                                <th>First Name</th>
                                <th>Middle Name</th>
                                <th>Last Name</th>
                                <th>User Type</th>
                                <th>Organization Name</th>
                                <th>Email ID</th>
                                <th>Phone Number</th>
                                <th>Status</th>
                                <th width="70">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${userList}" var="userProfileDTO">
                                <tr>
                                    <td>${userProfileDTO.userProfileKey}</td>
                                    <td>${userProfileDTO.firstName}</td>
                                    <td>${userProfileDTO.middleName}</td>
                                    <td>${userProfileDTO.lastName}</td>
                                    <td>${userProfileDTO.userTypeObj.lookUpEntityTypeName}</td>
                                    <td>${userProfileDTO.organizationObj.organizationName}</td>
                                    <td>${userProfileDTO.email}</td>
                                    <td>${userProfileDTO.telephoneNumber}</td>
                                    <td>${userProfileDTO.rowStatusValue}</td>
                                    <td >
                                        <!--  Based on permissions, 'Edit' button is displayed -->
                                        <c:choose>
                                            <c:when test="${userType==constants.DB_USER_TYPE_ADMIN}">
                                                <!--<a href="<c:url value="/updateuser.htm?userProfileKey=${userProfileDTO.encUserProfileKey}"/>" class="ux-icon-edit" title="Edit">Edit</a>-->
                                                <!--<a href="<c:url value="/viewuser.htm?userProfileKey=${userProfileDTO.encUserProfileKey}"/>" class="ux-icon-view" title="View">View</a>-->
                                                <a href="javascript:void(0)" onclick="$.fn.updateUser('${fn:trim(userProfileDTO.encUserProfileKey)}')" class="ux-icon-edit" title="Edit">Edit</a>
                                                <a href="javascript:void(0)" onclick="$.fn.viewUser('${fn:trim(userProfileDTO.encUserProfileKey)}')" class="ux-icon-view" title="View">View</a>
                                            </c:when>
                                            <c:otherwise>
                                                <c:if test="${permissions.contains(prmnsConstants.UPDATE_USER)}">
                                                   <a href="javascript:void(0)" onclick="$.fn.updateUser('${fn:trim(userProfileDTO.encUserProfileKey)}')" class="ux-icon-edit" title="Edit">Edit</a>
                                                    <!--<a href="<c:url value="/updateuser.htm?userProfileKey=${userProfileDTO.encUserProfileKey}"/>" class="ux-icon-edit" title="Edit">Edit</a>-->
                                                </c:if>
                                                 <c:if test="${permissions.contains(prmnsConstants.VIEW_USER)}">
                                                     <!--<a href="<c:url value="/viewuser.htm?userProfileKey=${userProfileDTO.encUserProfileKey}"/>" class="ux-icon-view" title="View">View</a>-->
                                                  <a href="javascript:void(0)" onclick="$.fn.viewUser('${fn:trim(userProfileDTO.encUserProfileKey)}')" class="ux-icon-view" title="View">View</a>
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
            <!-- End: Content -->

            <!-- Footer : Include -->
            <jsp:include page="../includes/footer.jsp"/>
            <!-- Footer : Include -->
        </div>

    </body>
</html>
<%
}
catch(Exception e)
{
    e.printStackTrace();
}
%>
