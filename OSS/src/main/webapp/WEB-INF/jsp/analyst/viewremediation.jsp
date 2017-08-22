<%-- 
    Document   : viewremediation
    Created on : Jul 11, 2016, 4:49:18 PM
    Author     : sathuluri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=9; IE=10; IE=EDGE" />
        <meta name="viewport" content="width=device-width" />
        <title>IRMaaS</title>
        <!-- CSS : Include -->
        <jsp:include page="../includes/commoncss.jsp"/>
        <link rel="stylesheet" href="<c:url value="/appcss/viewremediation.css"/>" />
        <!-- CSS : Include -->

        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/appjs/viewremediation.js"/>"></script>
        <!-- JS : Include -->

    </head>
    <body>
        <!-- Start: Wrapper -->
        <div id="ux-wrapper"> 

            <!-- Header : Include -->
            <jsp:include page="../includes/header.jsp"/>
            <!-- Header : Include -->

            <!-- Start: Breadcrumbs -->
            <ul class="ux-brdc ux-margin-left-1t" >
                <li>Optum Security Solutions</li>
                <li><a href="#" onclick="headerFn('remediationworklist.htm')" title="Remediation Tracking">Remediation Tracking</a></li>
                <li>Remediation</li>
            </ul>
            <!-- End: Breadcrumbs -->

            <!-- Left Panel : Include -->
            <jsp:include page="../includes/leftpanel.jsp"/>
            <!-- Left Panel : Include -->
            <!-- content starts -->
            <div class="ux-content">

                <h1>Remediation
                    <span class="ux-float-right ux-padding-bottom-none">
                        <input type="button" value="Back" title="Back" class="ux-btn-default-action" onclick="headerFn('remediationworklist.htm')"/>
                    </span></h1>
                <c:if test="${null != successMessage}">
                    <div class="ux-msg-success"><c:out value="${successMessage}" escapeXml="true"/></div>
                </c:if>
                <div id="accordion" class="ux-margin-bottom-1t">
                    <h3 class="ui-accordion-header ui-test ui-accordion-icons ui-corner-all">Engagement Information</h3>
                    <div class="ux-accordion-panl">
                        <div class="ux-hform">
                            <dl>
                                <dt>
                                    <label>Client Name:</label>
                                </dt>
                                <dd><c:out value="${engagementDTO.clientName}" escapeXml="true"></c:out></dd>
                                </dl>
                                <dl>
                                    <dt>
                                        <label>Engagement Code: </label>
                                    </dt>
                                    <dd><c:out value="${engagementDTO.engagementCode}" escapeXml="true"></c:out></dd>
                                </dl>
                                <dl>
                                    <dt>
                                        <label>Engagement Name:</label>
                                    </dt>
                                    <dd><c:out value="${engagementDTO.engagementName}" escapeXml="true"></c:out></dd>
                                </dl>
                                <dl>
                                    <dt>
                                        <label>Product:</label>
                                    </dt>
                                    <dd><c:out value="${engagementDTO.securityPackageObj.securityPackageName}" escapeXml="true"></c:out></dd>
                                </dl>
                                <div class="ux-clear"></div>
                                <dl>
                                    <dt>
                                        <label>Agreement Date: </label>
                                    </dt>
                                    <dd><c:out value="${engagementDTO.agreementDate}" escapeXml="true"></c:out></dd>
                                </dl>
                            </div>
                        </div>
                    </div>
                <spring:form id="updateFindingStatusForm" name="updateFindingStatusForm" action="" commandName="updatefindingstatus" method="POST">
                    <input type="hidden" name="clientEngagementDTO.encEngagementCode" value="${engagementDTO.encEngagementCode}" id="enc"/>
                    <div class="ux-panl ux-float-left">
                        <div class="ux-panl-header ux-panl-with-underline">
                            <h2 class="ux-float-left ux-width-full-9t-half ux-padding-halft">Finding List 
                                <div class="ux-float-right">
                                    <c:if test="${fn:length(findingList) > 0 }">
                                        <input type="button" value="Update" title="Update" onclick="updateFindingStatus()" class="ux-btn ux-float-right ux-btn-default-action"/>
                                    </c:if>
                                </div>
                            </h2>
                        </div>
                        <div class="ux-panl-content">
                            <div class="ux-clear"></div>
                            <table id="dataTable">
                                <thead>
                                    <tr>
                                        <td></td>
                                        <td></td>
                                        <td>Select</td>
                                        <td>Select</td>
                                        <td>Select</td>
                                        <td>Select</td>
                                        <td>
                                            <span><a href="javascript:void(0)" onclick="$.fn.SearchFilterClear(this, '#dataTable')" title="Refresh"><img src="images/refresh.png" />
                                                </a>
                                            </span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>Finding ID</th>
                                        <th>Finding Name</th>
                                        <th>Service</th>
                                        <th>Source</th>
                                        <th>Severity</th>
                                        <th>Status</th>
                                        <th class="ux-width-1t">Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:set value="0" var="count"/>
                                    <c:forEach var="finding" items="${findingList}">
                                        <tr>
                                            <input type="hidden" value="${finding.encInstanceIdentifier}" id="eii${count}"/>
                                            <td>
                                                <c:out value="${finding.instanceIdentifier}" escapeXml="true"/>
                                            </td>
                                            <td><c:out value="${finding.vulnerabilityName}" escapeXml="true"/></td>
                                            <td><c:out value="${finding.securityServiceObj.securityServiceName}" escapeXml="true"/></td>
                                            <td><c:out value="${finding.sourceName}" escapeXml="true"/></td>
                                            <td><c:out value="${finding.cveInformationDTO.severityName}" escapeXml="true"/></td>
                                            <td><c:out value="${finding.statusName}" escapeXml="true"/></td>
                                            <td>
                                                <select name="statusCode" id="statusCode${count}" onchange="changeStatusValue('${count}')">
                                                    <option value="">Select</option>
                                                    <option value="" <c:if test="${finding.statusName == 'Validated'}">selected</c:if> class="statusValue${count}">Validated</option>
                                                    <option value="" <c:if test="${finding.statusName == 'Closed'}">selected</c:if> class="statusValue${count}">Closed</option>
                                                </select>
                                            </td>
                                            </tr>
                                                <c:set value="${count+1}" var="count"/>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <!-- ux-panl content ends --> 
                    </div>
                </spring:form>             
            </div>
            <!-- content ends --> 

            <!-- Footer : Include -->
            <jsp:include page="../includes/footer.jsp"/>
            <!-- Footer : Include --> 

        </div>
        <!-- End: Wrapper -->
    </body>
</html>