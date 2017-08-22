<%-- 
    Document   : ViewRole
    Created on : May 2, 2016, 2:56:15 PM
    Author     : akeshavulu
--%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@taglib  uri="/WEB-INF/tlds/spring-form.tld" prefix="form" %>
<%@taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="constants" />
<HTML xmlns="http://www.w3.org/1999/xhtml">
    <HEAD>
        <title>IRMaaS</title>
        <!-- CSS : Include -->
        <jsp:include page="../includes/commoncss.jsp"/>
        <link rel="stylesheet" href="<c:url value="/appcss/AddRole.css"/>" />
        <!-- CSS : Include -->
        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/appjs/AddRoleJS.js"/>"></script>
        <!-- JS : Include -->
    </HEAD>
    <BODY >
        <!-- Start: Wrapper -->
        <div id="ux-wrapper"> 
            <!--Header starts here-->
            <jsp:include page="../includes/header.jsp"/>
            <!--Header ends here--> 

            <!-- Start: Breadcrumbs -->
            <ul class="ux-brdc ux-margin-left-1t" >

                <li><a href="#" title="Manage Roles & Permissions" onclick="headerFn('manageRolepage.htm')">Manage Roles & Permissions</a></li>
                <li><a href="#" title="Manage Roles" onclick="headerFn('manageRolepage.htm')">Manage Roles</a></li>
                <li>View Role</li>
            </ul>
            <!-- End: Breadcrumbs -->

            <!--Left navigation starts here-->
            <jsp:include page="../includes/leftpanel.jsp"/>
            <!--Left navigation ends here--> 

            <!-- content starts -->
            <div class="ux-content">
                <h1>  
                    View Role<div class="ux-float-right ux-margin-bottom-1t "> 

                        <input name="" type="button" value="Back"  title="Back" class="ux-btn-default-action " onclick="addRoleCancel()"/></div>
                </h1>
                <!--form starts-->        
                <spring:form id="addrole" name="addrole" commandName="addrole">
                    <div class="ux-panl ux-float-left" id="role">
                        <div class="ux-panl-header ux-panl-with-underline">
                            <h2> Role Details</h2>
                        </div>
                        <div class="ux-panl-content ux-padding-top-none">
                            <div class="ux-FormStyleLeftAlign">
                                <dl id="roleID">
                                    <dt>
                                        <label>ID:</label>
                                    </dt>
                                    <dd>
                                        ${roleDTO.appRoleKey}
                                    </dd>
                                </dl>
                                <dl>
                                    <dt>
                                        <label>Role Name: </label>
                                    </dt>
                                    <dd>
                                        ${roleDTO.appRoleName}
                                    </dd>

                                </dl>
                                <dl class="LongTxt" >
                                    <dt >
                                        <label >Role  Definition:</label>
                                    </dt>
                                    <dd>
                                        ${roleDTO.appRoleDescription}
                                    </dd>

                                </dl>
                                <dl>
                                    <dt >
                                        <label > Status:</label>
                                    </dt>
                                    <dd>
                                        ${roleDTO.rowStatusValue}
                                    </dd>

                                </dl>
                                <div class="ux-clear"></div>
                            </div>
                        </div>
                    </div>
                    <div class="ux-float-left ux-width-full-inclusive">
                        <div class="ux-panl ux-float-left" id="role">
                            <div class="ux-panl-header ux-panl-with-underline">
                                <h2> Grant Role Permission Groups</h2>
                            </div>
                            <div class="ux-panl-content ux-padding-top-none">
                                <div class="ux-clear"></div>

                                <table  id="dataTable" class="ux-tabl-data">
                                    <thead>
                                        <tr>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <th id="hchk"><input name="check" type="checkbox" value="" disabled="true" id="headCheckbox" onchange="checkAll(this)"/></th>
                                            <th class="ux-width-full-3t">Permission Group</th>
                                            <th>Role Definition</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:if test="${(allPermissionGroupList != null) && (fn:length(allPermissionGroupList)>0)}">
                                            <c:forEach var="rolePermissionGroupsVals" items="${allPermissionGroupList}">
                                                <tr>
                                                    <td>
                                                        <c:set var="checkBool" value="0"/>
                                                        <c:if test="${editPrmnGrpList != null}">
                                                            <c:forEach var="selPermGrp" items="${editPrmnGrpList}">
                                                                <c:if test="${selPermGrp.permissionGroupKey==rolePermissionGroupsVals.permissionGroupKey}">
                                                                    <c:set var="checkBool" value="1"/>
                                                                </c:if>
                                                            </c:forEach>
                                                        </c:if>
                                                        
                                                   
                                                        <c:choose>
                                                            <c:when test="${checkBool == 1}">
                                                                <input disabled="true" type="checkbox" name="prnGrpID" class="checkboxGroup" checked value="${editPermissionGroupsVals.permissionGroupKey}" disabled="true"/>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input disabled="true" type="checkbox" name="prnGrpID" class="checkboxGroup" value="${editPermissionGroupsVals.permissionGroupKey}"/>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        
                                                    </td>
                                                <td>${rolePermissionGroupsVals.permissionGroupName}</td>
                                                <td>${rolePermissionGroupsVals.permissionGroupDesc}</td>
                                                <td></td>
                                                </tr>
                                            </c:forEach>
                                        </c:if>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!-- Tabbed Panel -->
                </spring:form>
                <!--form Ends-->
            </div>
            <!-- content ends --> 
            <!-- Begin: Footer -->
            <jsp:include page="../includes/footer.jsp"/>
            <!-- End: Footer --> 
        </div>
        <!-- End: Wrapper --> 
        <script type="text/javascript" src="<c:url value="/appjs/editheadercheckbox.js"/>"></script>
    </BODY>
</HTML>
