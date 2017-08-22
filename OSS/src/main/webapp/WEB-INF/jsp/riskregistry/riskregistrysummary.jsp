<%-- 
    Document   : remediationplan
    Created on : Mar 1, 2017, 5:55:46 AM
    Author     : sbhagavatula
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
        <link rel="stylesheet" href="<c:url value="/css/jquery.dataTables.css"/>" />
        <!-- CSS : Include -->

        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/js/ddaccordion.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/appjs/riskregistrysummaryJS.js"/>"></script>
        <!-- JS : Include -->

    </head>
    <body>
        <div class="loader"></div>
        <!-- Start: Wrapper -->
        <div id="ux-wrapper"> 

            <!-- Header : Include -->
            <jsp:include page="../includes/header.jsp"/>
            <!-- Header : Include -->

            <!-- Start: Breadcrumbs -->
            <ul class="ux-brdc ux-margin-left-1t" >
                <li>Optum Security Solutions</li>
                <li><a href="#" onclick="headerFn('registryList.htm')" title="Risk Registry Module">Risk Registry Module</a></li>
                <li>Risk Register Summary</li>
            </ul>
            <!-- End: Breadcrumbs -->

            
            <!-- content starts -->
            <div class="ux-content">
                <h1 class="ux-float-left ux-width-full-4t ux-margin-top-1t">Risk Registry Summary</h1>
                <span class="ux-float-right ux-padding-1t">
                    <form name="" commandName="" method="POST" action="registryList.htm">
                    <input type="submit" value="Back" title="Back" class="ux-btn-default-action" />
                    </form>
                </span>
                <div class="ux-clear"></div>
                
                <c:if test="${null != roadmapStatusMessage}">
                    <div class="ux-msg-success"><c:out value="${roadmapStatusMessage}" escapeXml="true"/></div>
                </c:if>
    
                <!-- Engagement Information -->
                <div id="accordion" >
                    <h3>Engagement Information</h3>
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
                                <dl>
                                    <dt>
                                    <label>Agreement Date: </label>
                                    </dt>
                                    <dd><c:out value="${engagementDTO.agreementDate}" escapeXml="true"></c:out></dd>
                            </dl>
                        </div>
                    </div>
                </div>
                <!-- Engagement Information -->

                <div class="ux-clear"></div>
                
                <!-- Roadmap Vulnerabilities Category -->
                <div class="ux-panl ux-margin-top-1t">
                    <div class="ux-panl-header ux-panl-with-underline">
                        <h2 class="ux-float-left ux-width-full-3t-half ux-padding-halft">Risk Registry List</h2>
                        <div class="ux-float-right ux-padding-halft"> 
                            
                        </div>
                        
                    </div>
                    
                <form name="riskRegistrySummary" commandName="" method="POST">
                    <input type="hidden" value="${engagementDTO.encEngagementCode}" name="engkey"/>
                    <input type="hidden" value="${engagementDTO.orgSchema}" name="orgSchma"/>
                    <input type="hidden" value="" name="riskRegisterID" id="riskRegisterID"/>
                    
                    <!--Start: panel content-->
                    <div class="ux-panl-content">
                        <input type="hidden" value="${engagementDTO.encEngagementCode}" name="clientEngagementDTO.encEngagementCode" id="encEngagementCode"/>
                        <!--<div class="ux-clear"></div>-->
                        <table  id="dataTable"  width="100%" class="display">
                            <thead>
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td><input type="text" onchange="$.fn.creationDateFilter(this, '#dataTable')" id="creationDateId"  class="ux-width-5t" readonly="true"/></td>
                                    <td></td>
                                    <td><span><a href="#" onclick="$.fn.SearchFilterClearRegistryList(this, '#dataTable')" title="Refresh"><img src="images/refresh.png" /></a></span></td>
                                </tr>
                                <tr>
                                    <th></th>
                                    <th><strong>Risk Register ID</strong></th>
                                    <th><strong>Adjusted Severity</strong></th>
                                    <th><strong>Action Plan Title</strong></th>
                                    <th><strong>Instances</strong></th>
                                    <th><strong>Risk Response</strong></th>
                                    <th><strong>Date</strong></th>
                                    <th><strong>Accepted by</strong></th>
                                    <th><strong>Action</strong></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="riskRegistryDTO" items="${riskRegistryList}" >
                                    <tr>
                                        <td></td>
                                        <td><c:out value="${riskRegistryDTO.riskRegisterID}" escapeXml="true"/></td>
                                        <td><c:out value="${riskRegistryDTO.adjustedSeverity}" escapeXml="true"/></td>
                                        <td><c:out value="${riskRegistryDTO.planTitle}" escapeXml="true"/></td>
                                        <td><c:out value="${riskRegistryDTO.findingsCount}" escapeXml="true"/></td>
                                        <td><c:out value="${riskRegistryDTO.riskResponse}" escapeXml="true"/></td>
                                        <td><c:out value="${riskRegistryDTO.createdDate}" escapeXml="true"/></td>
                                        <td><c:out value="${riskRegistryDTO.acceptedBy}" escapeXml="true"/></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${empty riskRegistryDTO.riskResponseCode}">
                                                    <a href="#" onclick="$.fn.fnRiskRegistryDetails('${fn:trim(riskRegistryDTO.riskRegisterID)}')"
                                                       title="Edit" class="ux-icon-edit">Edit</a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a href="#" onclick="$.fn.fnRiskRegistryDetails('${fn:trim(riskRegistryDTO.riskRegisterID)}')"
                                                       title="View" class="ux-icon-view">View</a>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </form>

            </div>
            <!-- Roadmap Vulnerabilities Category -->
                
            </div>
            <!-- content ends --> 

            <!-- Footer : Include -->
            <jsp:include page="../includes/footer.jsp"/>
            <!-- Footer : Include --> 
        </div>
        <!-- End: Wrapper --> 
    </body>
</html>
