<%-- 
    Document   : viewpermissiongroup
    Created on : May 2, 2016, 3:02:59 PM
    Author     : sathuluri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=9; IE=10; IE=EDGE" />
        <meta name="viewport" content="width=device-width" />
        <title>IRMaaS</title>
        <!-- CSS : Include -->
        <jsp:include page="../includes/commoncss.jsp"/>
        <link rel="stylesheet" href="<c:url value="/appcss/addpermissiongroup.css"/>"/>
        <!-- CSS : Include -->
        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/appjs/addpermissiongroup.js"/>"></script>
        <!-- JS : Include -->  
        <!-- Header : Include -->
        <jsp:include page="../includes/header.jsp"/>
        <!-- Header : Include -->  
    </head>
    <body>
        <form name="permissnfrm" method="post" hidden></form>
        <!-- Start: Wrapper -->
        <div id="ux-wrapper"> 
            <!-- Start: Breadcrumbs -->
            <ul class="ux-brdc ux-margin-left-1t" >
                <li><a href="#" onclick="headerFn('manageRolepage.htm')" title="Manage Roles & Permissions">Manage Roles & Permissions</a></li>
                <li><a href="#" onclick="cancelPermissionGroup()" title="Manage Permission Groups">Manage Permission Groups</a></li>
                <li>View Permission Group</li>
            </ul>
            <!-- End: Breadcrumbs -->

            <!--Start: Left navigation-->
            <jsp:include page="../includes/leftpanel.jsp"/>
            <!--End: Left navigation-->

            <!-- content starts -->
            <div class="ux-content">
                <h1>View Permission Group
                    <div class="ux-float-right ux-margin-bottom-1t ">
                        <input name="" type="button" value="Back" title="Back" class="ux-btn-default-action" 
                               onclick="cancelPermissionGroup()"/></div>
                </h1>
                <div class="ux-panl ux-float-left" id="role">
                    <div class="ux-panl-header ux-panl-with-underline">
                        <h2> Permission Group Details</h2>
                    </div>
                    <div class="ux-panl-content ux-padding-top-none">
                        <div class="ux-FormStyleLeftAlign">
                            <dl>
                                <dt>
                                    <strong>ID:</strong>
                                    <dd>
                                        ${permissionGroupDTO.permissionGroupKey}
                                    </dd>
                                </dt>
                            </dl>
                            <dl>
                                <dt>
                                    <strong>Permission Group Name:</strong>
                                </dt>
                                <dd>
                                    ${permissionGroupDTO.permissionGroupName}
                                </dd>
                            </dl>

                            <dl class="LongTxt">
                                <dt>
                                    <strong>Permission Group Definition:</strong>
                                </dt>
                                <dd>
                                    ${permissionGroupDTO.permissionGroupDesc}
                                </dd>
                            </dl>
                            <dl>
                                <dt>
                                    <strong> Status:</strong>
                                </dt>
                                <dd>
                                    ${permissionGroupDTO.permissionGroupStatusValue}
                                </dd>
                            </dl>
                            <div class="ux-clear"></div>
                        </div>
                    </div>
                </div>
                <div class="ux-float-left ux-width-full-inclusive">
                    <div class="ux-panl ux-float-left" id="role">
                        <div class="ux-panl-header ux-panl-with-underline">
                            <h2>Detailed Permissions</h2>
                        </div>
                        <div class="ux-panl-content ux-padding-top-none">
                            <div class="ux-clear"></div>
                            <table  id="dataTable"  width="100%" class="ux-tabl-data">
                                <thead>
                                    <tr>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <th id="removeSortingForCheckbox"><input name="check" type="checkbox" disabled="true" /></th>
                                        <th class="ux-width-full-3t">Permission Name</th>
                                        <th>Permission Definition</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="savedpermissionsList" items="${permissionGroupDTO.permissionListObj}">
                                        <c:choose>
                                            <c:when test="${savedpermissionsList.permissionTypeKey == 1}">
                                                <tr>
                                                    <td><input type="checkbox" disabled="true" id="${savedpermissionsList.permissionKey}" class="checkboxGroup" onchange="singleCheckbox(this)" checked="true"/></td>
                                                    <td>${savedpermissionsList.permissionName}</td>
                                                    <td>${savedpermissionsList.permissionDescription}</td>
                                                </tr>
                                            </c:when>
                                            <c:otherwise>
                                                <tr>
                                                    <td><input type="checkbox" disabled="true" id="${savedpermissionsList.permissionKey}" class="checkboxGroup" onchange="singleCheckbox(this)"/></td>
                                                    <td>${savedpermissionsList.permissionName}</td>
                                                    <td>${savedpermissionsList.permissionDescription}</td>
                                                </tr>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <!-- content ends -->
            <!-- Start: Footer -->
            <jsp:include page="../includes/footer.jsp"/>
            <!-- End: Footer -->
        </div>
        <!-- End: Wrapper -->
    </body>
</html>
