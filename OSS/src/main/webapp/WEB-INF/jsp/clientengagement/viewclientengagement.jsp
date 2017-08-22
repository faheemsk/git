<%-- 
    Document   : viewclientengagement
    Created on : May 2, 2016, 4:37:17 PM
    Author     : sathuluri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld" %>
<%@taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
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
        <!-- CSS : Include -->
        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/appjs/clientengagementsworklist.js"/>"></script>
        <!-- JS : Include -->
        <!-- Header : Include -->
        <jsp:include page="../includes/header.jsp"/>
        <!-- Header : Include -->
        <style>
            .ux-panl{
                display: inline-block;
            }
        </style>
    </head>
    <body>
        <!-- Start: Wrapper -->
        <div id="ux-wrapper"> 
            <!-- Start: Breadcrumbs -->
            <ul class="ux-brdc ux-margin-left-1t" >

                <li><a title="Client Engagement" href="#" onclick="headerFn('engagementlist.htm')">Client Engagement</a></li>
                <li><a title="Manage Engagements" href="#" onclick="headerFn('engagementlist.htm')">Manage Engagements</a></li>
                <li>View Engagement</li>
            </ul>
            <!-- End: Breadcrumbs -->
            <!--Left navigation starts here-->
            <jsp:include page="../includes/leftpanel.jsp"/>
            <!--Left navigation ends here--> 
            <!-- Start: Content -->
            <div class="ux-content">
                <h1>View Engagement
                    <span class="ux-float-right">
                        <input type="button" value="Back" title="Back" class="ux-btn-default-action" onclick="headerFn('engagementlist.htm')"/>
                    </span>
                </h1>
                <div class="ux-panl">
                    <div class="ux-panl-header ux-panl-with-underline">
                        <h2>Engagement Information </h2>
                    </div>
                    <div class="ux-panl-content ux-padding-top-none">
                        <div class="ux-reportform">
                            <div class="ux-clear"></div>
                            <dl>
                                <dt>
                                    <label>Client Name: </label>
                                </dt>
                                <dd>
                                    ${clientEngagementDTO.clientName}
                                </dd>
                            </dl>
                            <dl>
                                <dt>
                                    <label>Product: </label>
                                </dt>
                                <dd>
                                    ${clientEngagementDTO.securityPackageObj.securityPackageName}
                                </dd>
                            </dl>
                            <dl>
                                <dt>
                                    <label>Agreement Date: </label>
                                </dt>
                                <dd>
                                    ${clientEngagementDTO.agreementDate}
                                </dd>
                            </dl>
                            <dl>
                                <dt>
                                    <label>Engagement Code: </label>
                                </dt>
                                <dd>
                                    ${clientEngagementDTO.engagementCode}
                                </dd>
                            </dl>
                            <dl>
                                <dt>
                                    <label>Engagement Name: </label>
                                </dt>
                                <dd>
                                    ${clientEngagementDTO.engagementName}
                                </dd>
                            </dl>
                            <dl>
                                <dt>
                                    <label>Estimated Start Date: </label>
                                </dt>
                                <dd>
                                    ${clientEngagementDTO.estimatedStartDate}
                                </dd>
                            </dl>

                            <dl>
                                <dt>
                                    <label> Estimated End Date: </label>
                                </dt>
                                <dd>
                                    ${clientEngagementDTO.estimatedEndDate}
                                </dd>
                            </dl>
                        </div>
                        <div class="ux-clear"></div>
                        <table class="ux-tabl-data fixtable">
                            <thead>
                                <tr>
                                    <th>Source System</th>
                                    <th>Source Project ID</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="sourceDto" items="${clientEngagementDTO.clientEngagementSourceLi}">
                                    <tr>
                                        <td>
                                            ${sourceDto.clientEngSourceName}
                                        </td>
                                        <td>
                                            ${sourceDto.clientEngSourceCode}
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="ux-panl"> 
                    <div class="ux-panl-header ux-panl-with-underline">
                        <h2>Services </h2>
                    </div>
                    <div class="ux-panl-content ">
                        <div id="healthcheck">
                            <table class="ux-tabl-data fixme">
                                <thead>
                                    <tr>
                                        <th>Service Name</th>
                                        <th>Service Start Date</th>
                                        <th>Service End Date</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="services" items="${clientEngagementDTO.engagementServiceLi}">
                                        <tr>
                                            <td>${services.securityServiceName}</td>
                                            <td>${services.serviceStartDate}</td>
                                            <td>${services.serviceEndDate}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div id="triage"></div>
                    </div>
                </div>
                <div class="ux-panl  client" >
                    <div class="ux-panl-header ux-panl-with-underline">
                        <h2>Assigned Engagement Users</h2>
                    </div>
                    <div class="ux-panl-content ">
                        <table id="table"  width="100%" class="ux-tabl-data">
                        <thead>
                            <tr>
                                <th class="ux-width-full-1t-half">User Type</th>
                                <th class="ux-width-full-1t-half">Role</th>
                                <th class="ux-width-full-2t-half">User</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${engusrlist}" var="userProfileDTO">
                                <tr>
                                    <td>${userProfileDTO.userTypeObj.lookUpEntityName}</td>
                                    <td>${userProfileDTO.appRoleName}</td>
                                    <td>${userProfileDTO.firstName}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    </div>
                </div>
                <div class="ux-panl  client" >
                    <div class="ux-panl-header ux-panl-with-underline">
                        <h2>Assigned Service Users</h2>
                    </div>
                    <div class="ux-panl-content ">
                        <table id="table" class="ux-tabl-data">
                            <thead>
                                <tr>
                                    <th>User Type</th>
                                    <th>Role</th>
                                    <th>Organization</th>
                                    <th>User<br/></th>
                                    <th class="ux-width-12t">Start Date<br/>
                                    </th>
                                    <th class="ux-width-12t">End Date<br/>
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                
                                <c:if test="${fn:length(clientEngagementDTO.engagementServiceLi) > 0}">
                                    <c:forEach var="engagementServiceDto" items="${clientEngagementDTO.engagementServiceLi}">
                                        <tr class="ux-tabl-alt-row" id="serviceIdValue${count}">
                                            <td colspan="7"><strong>${engagementServiceDto.securityServiceName}</strong> - 
                                                <span >Service Start Date: ${engagementServiceDto.serviceStartDate},  
                                                    Service End Date: ${engagementServiceDto.serviceEndDate} </span>
                                            </td>
                                        </tr>
                                        <c:forEach items="${srvusrlist}" var="servUserDTO">
                                            <c:if test="${servUserDTO.secureServiceCode eq engagementServiceDto.securityServiceCode}">
                                            <tr>
                                                <td>${servUserDTO.userTypeValue}</td>
                                                <td>${servUserDTO.roleName}</td>
                                                <td>${servUserDTO.organizationName}</td>
                                                <td>${servUserDTO.userName}</td>
                                                <td>${servUserDTO.startDate}</td>
                                                <td>${servUserDTO.endDate}</td>
                                            </tr>
                                            </c:if>
                                        </c:forEach>
                                    </c:forEach>
                                   
                                    
                                    <c:if test="${fn:length(clientEngagementDTO.engagementServiceLi) <= 0}">
                                        <tr>
                                            <td valign="top" colspan="6" class="dataTables_empty">No matching records found</td>
                                        </tr>
                                    </c:if>
                                </c:if>
                                
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="ux-panl  client" >
                    <div class="ux-panl-header ux-panl-with-underline">
                        <h2>Comments</h2>
                    </div>
                    <div class="ux-panl-content ">
                        ${clientEngagementDTO.engagementStatusComment}
                    </div>
                </div>
                <div class="ux-float-right ">
                    <input type="button" value="Back" title="Back" class="ux-btn-default-action" onclick="headerFn('engagementlist.htm')"/>
                </div>
            </div>
            <!-- End: Content -->
            <!-- Start: Footer -->
            <jsp:include page="../includes/footer.jsp"/>
            <!-- End: Footer -->
        </div>
        <!-- End: Wrapper -->
    </body>
</html>