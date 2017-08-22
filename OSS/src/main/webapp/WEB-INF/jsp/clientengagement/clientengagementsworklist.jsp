<%-- 
    Document   : clientengagementsworklist
    Created on : May 2, 2016, 2:16:59 PM
    Author     : sathuluri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld" %>
<%@taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="constants" />
<un:useConstants className="com.optum.oss.constants.PermissionConstants" var="prmnsConstants" />
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
        <link rel="stylesheet" href="<c:url value="/appcss/clientengagementsworklist.css"/>" />
        <!-- CSS : Include -->
        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/appjs/clientengagementsworklist.js"/>"></script>
        <!-- JS : Include -->  
        <!-- Header : Include -->
        <jsp:include page="../includes/header.jsp"/>
        <!-- Header : Include -->
        <c:set var="userType" value="${constants.DB_USER_TYPE_ADMIN}"/>
        <c:if test="${sessionScope.USER_SESSION != null}">
            <c:set var="userProfile" value="${sessionScope.USER_SESSION}"/>
            <c:set var="userType" value="${userProfile.userTypeObj.lookUpEntityName}"/>
        </c:if>
    </head>
    <body>
        <form action="" name="engActionForm" method="POST" hidden>
            <input type="hidden" value="" name="cei"/>
            <input type="hidden" id="addengagementFlag"  name="addengagementFlag" value=""/>
        </form>
        <!-- Start: Wrapper -->
        <div id="ux-wrapper"> 
            <!-- Start: Breadcrumbs -->
            <ul class="ux-brdc ux-margin-left-1t" >
                <li><a title="Client Engagement" href="#">Client Engagement</a></li>
                <li>Manage Engagements</li>
            </ul>
            <!-- End: Breadcrumbs -->

            <!-- Left Panel : Include -->
            <jsp:include page="../includes/leftpanel.jsp"/>
            <!-- Left Panel : Include -->

            <!-- Start: Content -->
            <div class="ux-content">
                <h1>Manage Engagements
                    <span class="ux-float-right">
                        <!-- Check for permissions and display respective action -->
                        <c:if test="${permissions.contains(prmnsConstants.ADD_ENGAGEMENT)}">
                            <input type="button" value="Add Engagement" title="Add Engagement" class="ux-btn-default-action" 
                                  onclick='fnaddEngagement("addengagementFlag")'  />
                        </c:if>
                    </span>
                </h1>
                
                <div class="ux-float-left ux-width-full-inclusive">
                    <table id="dataTable"  width="100%" class="display">
                        <thead>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td class="commondate">
                                    <input type="text" onchange="$.fn.dateFilter(this, '#dataTable')" class="datePicker" id="agreementDatePicker" maxlength="10" readonly="true"/>
                                </td>
                                <td class="commondate">
                                    <input type="text" onchange="$.fn.dateFilter(this, '#dataTable')" class="datePicker" id="estimatedStartDatePicker" maxlength="10" readonly="true"/>
                                </td>
                                <td class="commondate">
                                    <input type="text" onchange="$.fn.dateFilter(this, '#dataTable')" class="datePicker" id="estimatedEndDatePicker" maxlength="10" readonly="true"/>
                                </td>
                                <td><span><a href="#" onclick="$.fn.SearchFilterClear(this, '#dataTable')" title="Refresh"><img src="images/refresh.png"/>
                                        </a></span>
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                                <th class="ux-width-full-1t-half">Engagement Code</th>
                                <th class="ux-width-full-1t-half">Parent Client Name</th>
                                <th class="ux-width-full-1t-half">Client Name</th>
                                <th class="ux-width-10t">Agreement Date</th>
                                <th class="ux-width-10t">Estimated Start Date</th>
                                <th class="ux-width-10t">Estimated End Date</th>
                                <th class="ux-width-1t">Action</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="engagementDTO" items="${engagementsList}">
                                <tr>
                                    <td>
                                        <c:choose>
                                            <c:when test="${permissions.contains(prmnsConstants.VIEW_ENGAGEMENT)}">
                                                <a href="#" onclick='$.fn.vieweng("${fn:trim(engagementDTO.encEngagementCode)}")' title="${engagementDTO.engagementCode}">${engagementDTO.engagementCode}</a>
                                            </c:when>
                                            <c:otherwise>
                                                ${engagementDTO.engagementCode}
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:if test="${engagementDTO.parentClientName ne 'null' && engagementDTO.parentClientName ne null}">
                                            ${engagementDTO.parentClientName}
                                        </c:if>
                                    </td>
                                    <td>${engagementDTO.clientName}</td>
                                    <td>${engagementDTO.agreementDate}</td>
                                    <td>${engagementDTO.estimatedStartDate}</td>
                                    <td>${engagementDTO.estimatedEndDate}</td>
                                    <td>
                                        <c:if test="${permissions.contains(prmnsConstants.EDIT_ENGAGEMENT)}">
                                            <a href="#" onclick='$.fn.editeng("${fn:trim(engagementDTO.encEngagementCode)}")' class="ux-icon-edit" onclick="" title="Edit">Edit</a>
                                        </c:if>
                                    </td>
                                    <td>${engagementDTO.modifiedDate}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
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