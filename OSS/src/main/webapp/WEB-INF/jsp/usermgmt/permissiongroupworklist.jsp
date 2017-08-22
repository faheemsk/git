<%-- 
    Document   : permissiongroupworklist
    Created on : Apr 13, 2016, 10:19:59 AM
    Author     : sathuluri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld" %>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="constants" />
<un:useConstants className="com.optum.oss.constants.PermissionConstants" var="permissionConstants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=9; IE=10; IE=EDGE" />
        <meta name="viewport" content="width=device-width" />
        <title>IRMaaS</title>
        <!-- CSS : Include -->
        <jsp:include page="../includes/commoncss.jsp"/>
        <link rel="stylesheet" href="<c:url value="/css/jquery.dataTables.css"/>" />
        <link rel="stylesheet" href="<c:url value="/appcss/permissiongroupworklist.css"/>"/>
        <!-- CSS : Include -->
        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/appjs/permissiongroupworklist.js"/>"></script>
        <!-- JS : Include -->  
        <!-- Header : Include -->
        <jsp:include page="../includes/header.jsp"/>
        <!-- Header : Include -->
        <!-- Start: User session data -->
        <c:set var="userType" value="${constants.DB_USER_TYPE_ADMIN}"/>
        <c:if test="${sessionScope.USER_SESSION != null}">
            <c:set var="userProfile" value="${sessionScope.USER_SESSION}"/>
            <c:set var="userType" value="${userProfile.userTypeObj.lookUpEntityName}"/>
        </c:if>
        <!-- End: User session data -->
    </head>
    <body>
        <!-- Start: Wrapper -->
        <div id="ux-wrapper"> 
            <!-- Start: Breadcrumbs -->
            <ul class="ux-brdc ux-margin-left-1t" >

                <li><a title="Manage Roles & Permissions" href="#" onclick="headerFn('manageRolepage.htm')">Manage Roles & Permissions</a></li>
                <li>Manage Permission Groups</li>
            </ul>
            <!-- End: Breadcrumbs -->

            <!--Start: Left navigation-->
            <jsp:include page="../includes/leftpanel.jsp"/>
            <!--End: Left navigation-->

            <!-- Popup Starts -->
            <div id='blackcover'> </div>
            <div id='popupbox' class="ux-modl">
                <div class="ux-modl-header">
                    <a href="#" onclick='popup(0)' title="Close">Close</a>
                    <h6>Notification</h6>
                </div>

                <div class="ux-modl-content">
                    <div align="center">
                        <h3>Are you sure you want to delete the permission group?</h3>
                    </div>
                    <div class="ux-margin-1t  " align="center">
                        <input type="button" title="Ok" value="Ok" class="ux-btn-default-action ux-width-7t" onclick="deleteGroup()" id="msgshow"/>
                        <input type="button" title="Cancel" value="Cancel" class="ux-btn-default-action ux-width-7t" onclick='popup(0)' /> 
                    </div>
                </div>
            </div>
            <!-- Popup Ends -->

            <!-- Start: Content -->
            <div class="ux-content">
                <spring:form name="managePermissionGroupForm" method="post" commandName="permissionGroupDTO">
                <h1>Manage Permission Groups
                    <span class="ux-float-right">
                        <!-- Start: Based on permission to show add permission group button -->
                        <c:choose>
                            <c:when test="${userType==constants.DB_USER_TYPE_ADMIN}">
                                <input type="button" value="Add Permission Group" title="Add Permission Group" class="ux-btn-default-action" 
                                       onclick="addpermissiongrouppage()"/>
                            </c:when>

                            <c:otherwise>
                                <c:if test="${null != permissionSet}">
                                    <c:if test="${permissionSet.contains(permissionConstants.ADD_PERMISSION_GROUP)}">
                                        <input type="button" value="Add Permission Group" title="Add Permission Group" class="ux-btn-default-action" 
                                               onclick="addpermissiongrouppage()"/>
                                    </c:if>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                        <!-- Start: Based on permission to show add permission group button -->
                    </span>
                </h1>
                 <c:if test="${null != successMessage && saveFlag == constants.SAVE_FLAG}">
                    <div class="ux-msg-success">" ${sessionPermissionGroupName} "  ${successMessage}</div>
                </c:if>
                <c:if test="${null != successMessage && updateFlag == constants.UPDATE_FLAG}">
                    <div class="ux-msg-success">" ${sessionPermissionGroupName} "  ${successMessage}</div>
                </c:if>
                <c:if test="${null != successMessage && deleteFlag == constants.DELETE_FLAG}">
                    <div class="ux-msg-success">" ${sessionPermissionGroupName} "  ${successMessage}</div>
                </c:if>
                <div class="ux-float-left ux-width-full-inclusive">
                    <input type="hidden" name="encGroupKey" id="encGroupKey"/>
                    <table id="dataTable"  width="100%" class="display">
                        <thead>
                            <tr>
                                <td class="uid"></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td class="dateid"><input type="text" onchange="$.fn.dateFilter(this, '#dataTable')" id="defaultActualPicker" maxlength="10" readonly="true"/></td>
                                <td>All</td>
                                <td><span><a href="#" onclick="$.fn.SearchFilterClear(this, '#dataTable')" title="Refresh"><img src="images/refresh.png" />
                                        </a></span>
                                </td>
                            </tr>
                            <tr>
                                <th>ID</th>
                                <th>Permission Group Name</th>
                                <th class="ux-width-full-3t">Permission Group Definition</th>
                                <th>Updated By</th>
                                <th class="ux-width-10t">Last Updated Date</th>
                                <th>Status</th>
                                <th class="ux-width-10t">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:set var="count" value="1"/>
                            <c:forEach var="permissionGroupValues" items="${permissionGroupWorkList}">
                                <tr>
                                    <input type="hidden" id="groupId${count}"
                                           value="${permissionGroupValues.encPermissionGroupKey}"/>
                                    <input type="hidden" id="groupName${count}"
                                           value="${permissionGroupValues.permissionGroupName}"/>
                                    <td>${permissionGroupValues.permissionGroupKey}</td>
                                    <td>${permissionGroupValues.permissionGroupName}</td>
                                    <td>${permissionGroupValues.permissionGroupDesc}</td>
                                    <td>${permissionGroupValues.modifiedByUser}</td>
                                    <td>${permissionGroupValues.modifiedDate}</td>
                                    <c:if test="${permissionGroupValues.rowStatusValue == constants.DB_ROW_STATUS_ACTIVE}">
                                        <td>${constants.DB_ROW_STATUS_ACTIVE}</td>
                                    </c:if>
                                    <c:if test="${permissionGroupValues.rowStatusValue == constants.DB_ROW_STATUS_DE_ACTIVE}">
                                        <td>${constants.DB_ROW_STATUS_DE_ACTIVE}</td>
                                    </c:if>

                                    <td>
                                        <!-- Start: Based on permission to show the add / edit / delete button(s) -->
                                        <c:choose>
                                            <c:when test="${userType==constants.DB_USER_TYPE_ADMIN}">
                                                <a href="javascript:void(0)" class="ux-icon-edit" 
                                                   onclick="editpermissiongroupvalues('${fn:trim(permissionGroupValues.encPermissionGroupKey)}')"
                                                   title="Edit">Edit</a>
                                                <a href="javascript:void(0)" class="ux-icon-delete"
                                                   onclick="deletepermissiongroupvalues('${fn:trim(permissionGroupValues.encPermissionGroupKey)}', '${permissionGroupValues.permissionGroupName}')" 
                                                   title="Delete">Delete</a>
                                                <a href="javascript:void(0)" 
                                                   onclick="fnViewPermissionGroup('${fn:trim(permissionGroupValues.encPermissionGroupKey)}')"
                                                   class="ux-icon-view" title="View">View</a>
                                            </c:when>
                                            <c:otherwise>
                                                <c:if test="${null != permissionSet}">
                                                    <c:if test="${permissionSet.contains(permissionConstants.UPDATE_PERMISSION_GROUP)}">
                                                        <a href="javascript:void(0)" class="ux-icon-edit" 
                                                           onclick="editpermissiongroupvalues('${fn:trim(permissionGroupValues.encPermissionGroupKey)}')"
                                                           title="Edit">Edit</a>
                                                    </c:if>
                                                    <c:if test="${permissionSet.contains(permissionConstants.REMOVE_PERMISSION_GROUP)}">
                                                        <a href="javascript:void(0)" class="ux-icon-delete"
                                                           onclick="deletepermissiongroupvalues('${fn:trim(permissionGroupValues.encPermissionGroupKey)}', '${permissionGroupValues.permissionGroupName}')"
                                                           title="Delete">Delete</a>
                                                    </c:if>
                                                    <c:if test="${permissionSet.contains(permissionConstants.VIEW_PERMISSION_GROUP)}">
                                                        <a href="javascript:void(0)" 
                                                           onclick="fnViewPermissionGroup('${fn:trim(permissionGroupValues.encPermissionGroupKey)}')"
                                                           class="ux-icon-view" title="View">View</a>
                                                    </c:if>
                                                </c:if>
                                            </c:otherwise>
                                        </c:choose>
                                        <!-- End: Based on permission to show the add / edit / delete button(s) -->
                                    </td>
                                </tr>
                                <c:set var="count" value="${count + 1}"/>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                </spring:form>
            </div>
            <!-- End: Content -->

            <!-- Start: Form Details -->
            <%--<spring:form name="editPermissionGroup" action="#" method="POST" id="editPermissionGroup" commandName="permissionGroupDTO"/>--%>
            <%--<spring:form name="addPermissionGroupPage" action="#" method="POST" id="addPermissionGroupPage" commandName="permissionGroupDTO"/>--%>
            <spring:form name="deletePermissionGroup" action="#" method="POST" id="deletePermissionGroup" commandName="delPermissionGroupObj">
                <input type="hidden" name="encPermissionGroupKey" id="permissionGroupKeyForDelete" value=""/>
                <input type="hidden" name="permissionGroupName" id="permissionGroupNameForDelete" value=""/>
            </spring:form>
            <!-- End: Form Details -->

            <!-- Start: Footer -->
            <jsp:include page="../includes/footer.jsp"/>
            <!-- End: Footer -->
        </div>
        <!-- End: Wrapper -->
    </body>
</html>
