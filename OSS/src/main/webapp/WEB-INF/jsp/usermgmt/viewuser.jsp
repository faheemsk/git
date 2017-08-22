<%-- 
    Document   : viewuser
    Created on : Apr 18, 2016, 12:28:21 PM
    Author     : sbhagavatula
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
        <link rel="stylesheet" href="<c:url value="/appcss/User.css"/>" />
        <!-- CSS : Include -->
        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/appjs/UserJS.js"/>"></script>
        <!-- JS : Include -->
    </head>
    <body>
        <form name="viewusrfrm" method="post" hidden>
            <input type="hidden" name="mnval" value="A1Dh0c"/>
        </form>
        <div id="ux-wrapper">
            <!-- Header : Include -->
            <jsp:include page="../includes/header.jsp"/>
            <!-- Header : Include -->

            <!-- Start: Breadcrumbs -->
            <ul class="ux-brdc ux-margin-left-1t" >
                
                <li><a title="Manage Users" href="#" onclick="backFrmViewFn()">Manage Users</a></li>
                <li>View User</li>
            </ul>
            <!-- End: Breadcrumbs -->
            
            <!-- Left Panel : Include -->
            <jsp:include page="../includes/leftpanel.jsp"/>
            <!-- Left Panel : Include -->

            <!-- Begin: Content -->
            <div class="ux-content">
                <h1>View User <div class="ux-float-right ">  <input name="" type="button" value="Back" title="Back" class="ux-btn-default-action  " onclick="backFrmViewFn()" /> </div></h1>
                <div class="ux-panl ux-float-left">
                    <div class="ux-panl-header ux-panl-with-underline">
                        <h2> User Details</h2>
                    </div>
                    <div class="ux-panl-content ux-padding-top-none">
                        <div class="ux-reportform">
                            <dl>
                                <dt><label>ID:</label> </dt>
                                <dd>${viewUserProfile.userProfileKey}</dd>
                            </dl>
                            <dl>
                                <dt><label> User Type: </label> </dt>
                                <dd>${viewUserProfile.userTypeObj.lookUpEntityTypeName}</dd>
                            </dl>
                            <dl id="int">
                                <dt><label> Organization Name: </label> </dt>
                                <dd>${viewUserProfile.organizationObj.organizationName}</dd>
                            </dl>
                            
                            <div class="ux-clear"></div>
                            <dl>
                                <dt><label> First Name: </label> </dt>
                                <dd>${viewUserProfile.firstName}</dd>
                            </dl>
                            <dl>
                                <dt><label> Middle Name: </label> </dt>
                                <dd>${viewUserProfile.middleName}</dd>
                            </dl>
                            <dl>
                                <dt><label> Last Name: </label> </dt>
                                <dd>${viewUserProfile.lastName}</dd>
                            </dl>
                            <dl>
                                <dt><label> Job Title: </label> </dt>
                                <dd>&nbsp;${viewUserProfile.jobTitleName}</dd>
                            </dl>
                            <dl>
                                <dt><label> Email ID: </label> </dt>
                                <dd>${viewUserProfile.email}</dd>
                            </dl>
                            <dl>
                                <dt><label> Phone Number: </label> </dt>
                                <dd>${viewUserProfile.telephoneNumber}</dd>
                            </dl>

                            <dl id="puser">
                                <dt>
                                    <label>Assign Roles: </label>
                                </dt>
                                <c:set value="${viewUserProfile.userApplicationRoleListObj}" var="rolesList"/>
                                <dd>
                                    <c:set value="" var="delim"/>
                                    <c:forEach var="entry" items="${userRolesMap}">${delim}${entry.value}<c:set value=", " var="delim"/></c:forEach>
                                </dd>
                            </dl>  
                                  <dl>
                                <dt><label>Department: </label></dt>
                                <dd>${viewUserProfile.department}</dd>
                            </dl>
                             <div class="ux-clear"></div>
                            <dl>
                                <dt><label> Status: </label></dt>
                                <dd>${viewUserProfile.rowStatusValue}</dd>
                            </dl>

                        </div>
                    </div>
                </div>

                <div class="ux-clear"></div>
                <div class="ux-panl ux-margin-top-1t " >

                    <div class="ux-panl-header ux-panl-with-underline">
                        <h2>Permission Group</h2>
                    </div>

                    <div class="ux-panl-content ">
                        <table width="100%" class="ux-tabl-data">
                            <thead>
                                <tr>
                                    <th>Permission Group</th>
                                    <th>Permission Group Definition</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${permissionGroupMap}" var="permissionGrp">
                                    <c:set value="${permissionGrp.value}" var="permissionGrpObj"/>
                                    <tr>
                                        <td>${permissionGrpObj.permissionGroupName}</td>
                                        <td>${permissionGrpObj.permissionGroupDesc}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>

                    </div>
                </div>
            </div>
            <!-- End: Content -->

            <!-- Footer : Include -->
            <jsp:include page="../includes/footer.jsp"/>
            <!-- Footer : Include -->
        </div>

    </body>
</html>



