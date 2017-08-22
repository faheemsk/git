<%-- 
    Document   : ManageRoles
    Created on : Apr 12, 2016, 5:40:26 PM
    Author     : akeshavulu
--%>
<%@taglib  uri="/WEB-INF/tlds/spring-form.tld" prefix="form" %>
<%@taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<%--<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>--%>
<un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="constants" />
<un:useConstants className="com.optum.oss.constants.PermissionConstants" var="prmnsConstants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">

<HEAD>

    <title>IRMaaS</title>
    <!-- CSS : Include -->
    <jsp:include page="../includes/commoncss.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/jquery.dataTables.css"/>" />
    <link rel="stylesheet" href="<c:url value="/appcss/ManageRoles.css"/>" />
    <!-- CSS : Include -->

    <!-- JS : Include -->
    <jsp:include page="../includes/commonjs.jsp"/>
    <script type="text/javascript" src="<c:url value="/appjs/ManageRolesJS.js"/>"></script>
    <!-- JS : Include -->  
    <c:set var="userType" value="${constants.DB_USER_TYPE_ADMIN}"/>
    <c:if test="${sessionScope.USER_SESSION != null}">
        <c:set var="userProfile" value="${sessionScope.USER_SESSION}"/>
        <c:set var="userType" value="${userProfile.userTypeObj.lookUpEntityName}"/>
    </c:if>
</HEAD>
<BODY>
    <!-- Popup Starts -->
    <div id='blackcover'> </div>
    <div id='popupbox' class="ux-modl">
        <div class="ux-modl-header">
            <a href="#" onclick='popup(0)' title="Close">Close</a>
            <h6>Notification</h6>
        </div>

        <div class="ux-modl-content">
            <div align="center">
                <h3>Are you sure you want to delete the role?</h3>

            </div>
            <div class="ux-margin-1t  " align="center">
                <input type="button" value="Ok" title="Ok" class="ux-btn-default-action ux-width-7t" onclick="removeRole()" id="msgshow"/> 
                <input type="button" value="Cancel" title="Cancel" class="ux-btn-default-action ux-width-7t" onclick='popup(0)' /> 
            </div>
        </div>
    </div>
    <!-- Popup Ends -->
    <!-- Start: Wrapper -->
    <div id="ux-wrapper"> 
        <!--Header starts here-->
        <jsp:include page="../includes/header.jsp"/>
        <!--Header ends here--> 
        <!-- Begin: Content -->
        <ul class="ux-brdc ux-margin-left-1t" >

            <li><a title="Manage Roles & Permissions" href="#" onclick="headerFn('manageRolepage.htm')">Manage Roles & Permissions</a></li>
            <li>Manage Roles</li>
        </ul>

        <!--Left navigation starts here-->
        <jsp:include page="../includes/leftpanel.jsp"/>
        <!--Left navigation ends here--> 

        <!-- content starts -->
        <div class="ux-content">
            <form name="manageRoleForm" method="post">
                <h1>Manage Roles <span class="ux-float-right"> 
                        <!-- Check for permissions and display respective action -->
                        <!--<form name="rolewl" action="#" method="POST" id="editRole" >-->
                        <c:choose>
                            <c:when test="${userType == constants.DB_USER_TYPE_ADMIN}">
                                <input type="button" value="Add Role" title="Add Role" class="ux-btn-default-action" onclick="addRolePage()"  />
                            </c:when>
                            <c:otherwise>
                                <c:if test="${permissionSet.contains(prmnsConstants.ADD_ROLE)}">
                                    <input type="button" value="Add Role" title="Add Role" class="ux-btn-default-action" onclick="addRolePage()"  />
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                        <!--</form>-->
                    </span></h1>
                    <c:if test="${null != successMessage}">
                    <div class="ux-msg-success">" ${appRoleName} " ${successMessage}</div>
                </c:if>
                <!--jquery data table starts here-->
                <div class="ux-float-left ux-width-full-inclusive">
                    <input type="hidden" name="ed" id="ed"/>
                    <table id="dataTable"  width="100%" class="display">
                        <thead>
                            <tr>
                                <td class="uid"></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td class="dateid"><input type="text" onchange="$.fn.dateFilter(this, '#dataTable')" id="defaultActualPicker" maxlength="10" readonly="true"/> </td>
                                <td>All</td>
                                <td><span><a href="#" onclick="$.fn.SearchFilterClear(this, '#dataTable')" title="Refresh"><img src="<c:url value="/images/refresh.png" />" /></a></span></td>
                            </tr>
                            <tr>
                                <th>ID</th>
                                <th>Role Name</th>
                                <th class="ux-width-full-3t">Role Definition </th>
                                <th>Updated By</th>
                                <th class="ux-width-10t">Last Updated Date</th>
                                <th>Status</th>
                                <th class="ux-width-6t">Action </th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="rolePermissionVals" items="${manageRolePermissionWorkList}">
                                <tr>
                                    <td>${rolePermissionVals.appRoleKey}</td>
                                    <td>${rolePermissionVals.appRoleName}</td>
                                    <td>${rolePermissionVals.appRoleDescription}</td>
                                    <td>${rolePermissionVals.modifiedByUser}</td>
                                    <td>${rolePermissionVals.modifiedDate}</td>
                                    <c:if test="${rolePermissionVals.rowStatusKey == constants.DB_ROW_STATUS_ACTIVE_VALUE}">
                                        <td>${constants.DB_ROW_STATUS_ACTIVE}</td>
                                    </c:if>
                                    <c:if test="${rolePermissionVals.rowStatusKey == constants.DB_ROW_STATUS_DE_ACTIVE_VALUE}">
                                        <td>${constants.DB_ROW_STATUS_DE_ACTIVE}</td>
                                    </c:if>
                                    <td>
                                        <!-- Check for permissions and display respective action -->
                                        <%--onclick="editRole('${fn:trim(rolePermissionVals.encAppRoleKey)}')"--%>
                                        <c:choose>
                                            <c:when test="${userType==constants.DB_USER_TYPE_ADMIN}">
                                                <a href="javascript:void(0)" class="ux-icon-edit" title="Edit" 
                                                   onclick='fnEditRole("${fn:trim(rolePermissionVals.encAppRoleKey)}")'>Edit</a> 
                                                <a href="javascript:void(0)"
                                                   onclick='deleteRole("${fn:trim(rolePermissionVals.encAppRoleKey)}")'
                                                   class="ux-icon-delete" title="Delete">Delete</a>
                                                <a href="javascript:void(0)"
                                                   onclick='fnViewRole("${fn:trim(rolePermissionVals.encAppRoleKey)}")'
                                                   class="ux-icon-view" title="View">View</a>
                                            </c:when>
                                            <c:otherwise>
                                                <c:if test="${permissionSet.contains(prmnsConstants.UPDATE_ROLE)}">
                                                    <a href="javascript:void(0)" onclick='fnEditRole("${fn:trim(rolePermissionVals.encAppRoleKey)}")'
                                                       class="ux-icon-edit" title="Edit">Edit</a> 
                                                </c:if>
                                                <c:if test="${permissionSet.contains(prmnsConstants.REMOVE_ROLE)}">
                                                    <a href="javascript:void(0)"
                                                       onclick='deleteRole("${fn:trim(rolePermissionVals.encAppRoleKey)}")'
                                                       class="ux-icon-delete" title="Delete">Delete</a>
                                                </c:if>
                                                <c:if test="${permissionSet.contains(prmnsConstants.VIEW_ROLE)}">
                                                    <a href="javascript:void(0)" onclick='fnViewRole("${fn:trim(rolePermissionVals.encAppRoleKey)}")'
                                                       class="ux-icon-view" title="View">View</a>
                                                </c:if>
                                            </c:otherwise>
                                        </c:choose>
                                        <input type="hidden" name="encAppRoleKeyh" id="encAppRoleKeyh${rolePermissionVals.appRoleKey}" value="${rolePermissionVals.encAppRoleKey}"/>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <!--jquery data table ends here-->
            
            <script>
                <%--<c:url value="/fetchEditAppRoleDetails.htm?ed=${rolePermissionVals.encAppRoleKey}"/>--%>
                
            </script>
            
        </form>
    </div>
    <!-- content ends -->

    <!-- Begin: Footer -->
    <jsp:include page="../includes/footer.jsp"/>
    <!-- End: Footer --> 
</div>
<!-- End: Wrapper --> 
</BODY>
<HTML>

