<%-- 
    Document   : Useractivation
    Created on : Apr 13, 2016, 2:35:13 PM
    Author     : schanganti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<un:useConstants className="com.optum.oss.constants.PermissionConstants" var="prmnConstants" />
<un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"/>
<head>

    <title>IRMaaS</title>
    <!-- CSS : Include -->
    <jsp:include page="../includes/commoncss.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/jquery.dataTables.css"/>" />
    <link rel="stylesheet" href="<c:url value="/appcss/useractivation.css"/>" />
    <!-- CSS : Include -->
    <!-- JS : Include -->
    <jsp:include page="../includes/commonjs.jsp"/>
    <script type="text/javascript" src="<c:url value="/appjs/UseractivationJS.js"/>"></script>
    <!-- JS : Include -->
    <c:set var="userType" value="${constants.DB_USER_TYPE_ADMIN}"/>
    <c:if test="${sessionScope.USER_SESSION != null}">
        <c:set var="userProfile" value="${sessionScope.USER_SESSION}"/>
        <c:set var="userType" value="${userProfile.userTypeObj.lookUpEntityName}"/>
    </c:if>
</head>
<BODY >
    <!-- Popup Starts -->
    <div id='blackcover'> </div>
    <div id='popupbox' class="ux-modl">
        <div class="ux-modl-header">
            <a href="#" onclick='popup(-1)'>Close</a>
            <h6>Notification</h6>
        </div>

        <div class="ux-modl-content">
            <div align="center" id="unlock">
                <h3>Are you sure you want to unlock?</h3>
            </div>
            <div align="center" id="Deactive">
                <h3>Are you sure you want to make this account active?</h3>
            </div>
            <div class="ux-margin-1t  " align="center">
                <input type="button" value="Ok"  title="Ok" class="ux-btn-default-action ux-width-7t" onclick='$.fn.changeStatus()'/> 
                <input type="button" title="Cancel" value="Cancel" class="ux-btn-default-action ux-width-7t" onclick='popup(0)' /> 
            </div>
        </div>
    </div>
    <!-- Popup Ends -->

    <!-- Start: Wrapper -->
    <div id="ux-wrapper">

        <!--Header starts here-->
        <jsp:include page="../includes/header.jsp"/>
        <!--Header ends here-->

        <!-- Begin: Secondary Nav -->

        <!-- End: Secondary Nav -->

        <!-- Begin: Content -->
        <ul class="ux-brdc ux-margin-left-1t" >
            
            <li><a title="User Activation" href="#" onclick="headerFn('userworklist.htm')">User Activation</a></li> 
            <li>User Activation</li>
        </ul>

        <!--Left navigation starts here-->
        <jsp:include page="../includes/leftpanel.jsp"/>
        <!--Left navigation ends here--> 

        <!-- content starts -->
        <div class="ux-content">

            <form action=""  name="changestatusform" method="POST">
            </form>

            <h1>User Activation</h1>
            <c:if test="${null != successMessage}">
                <div class="ux-msg-success">${successMessage}</div>
            </c:if>
            <% session.setAttribute("successMessage", null);%>
            <div class="ux-float-left ux-width-full-inclusive">
                <table id="dataTable" class="display">
                    <thead>
                        <tr>
                            <td>All</td>
                            <td</td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td>All</td>
                            <td><span><a href="#" onclick="$.fn.SearchFilterClear(this, '#dataTable')"><img src="images/refresh.png" title="Refresh"/></a></span></td>

                        </tr>
                        <tr>
                            <th>User Type</th>
                            <th>Organization Name</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Email ID</th>
                            <th>Status</th>
                            <th>Action</th>

                        </tr>
                    </thead>
                    <tbody>
                        <c:if test="${worklist.size()!=0}">
                            <c:set var="count" value="1"/>
                            <c:forEach items="${worklist}" var="userDTO">
                                <tr>
                                    <td>${userDTO.userTypeObj.lookUpEntityName}</td>
                                    <td>${userDTO.organizationObj.organizationName}</td>
                                    <td>${userDTO.firstName}</td>
                                    <td>${userDTO.lastName}</td>
                                    <td>${userDTO.email}</td>
                                    <td>${userDTO.rowStatusValue}</td>
                                    <input type="hidden" id="encUserProfileKey${count}" value="${userDTO.encUserProfileKey}"/>
                                    <input type="hidden" id="encRowStatusValue${count}" value="${userDTO.encRowStatusValue}"/>
                                    <input type="hidden" id="rowStatusValue${count}" value="${userDTO.rowStatusValue}"/>
                                    <c:if test="${userDTO.rowStatusValue=='Locked'}">
                                        <td>
                                            <!--Check user role permission and display respective action-->
                                            <c:choose>
                                                <c:when test="${userType==constants.DB_USER_TYPE_ADMIN}">
                                                    <span><a href="#" class="ux-icon-unlock" title="Unlock" onclick="changeStatusPopup('${count}')"></a></span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:if test="${permissionSet.contains(prmnConstants.UPDATE_USER_ACTIVATION)}">
                                                        <span><a href="#" class="ux-icon-unlock" title="Unlock" onclick="changeStatusPopup('${count}')"></a></span>
                                                        </c:if>
                                                    </c:otherwise>
                                                </c:choose>
                                        </td>
                                    </c:if>
                                    <c:if test="${userDTO.rowStatusValue=='Deactive'}">
                                        <td>
                                            <!--Check user role permission and display respective action-->
                                            <c:choose>
                                                <c:when test="${userType==constants.DB_USER_TYPE_ADMIN}">
                                                    <span><a href="#" class="ux-icon-active" title="Active" onclick="changeStatusPopup('${count}')"></a></span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:if test="${permissionSet.contains(prmnConstants.UPDATE_USER_ACTIVATION)}">
                                                        <span><a href="#" class="ux-icon-active" title="Active" onclick="changeStatusPopup('${count}')"></a></span>
                                                        </c:if>
                                                    </c:otherwise>
                                                </c:choose>
                                        </td>
                                    </c:if> 
                                </tr>
                                <c:set var="count" value="${count + 1}"/>
                            </c:forEach>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- content ends -->

        <!-- Begin: Footer -->
        <jsp:include page="../includes/footer.jsp"/>
        <!-- End: Footer -->
    </div>
    <!-- End: Wrapper -->


    <!-- END JavaScript Includes -->

</BODY>


</html>

