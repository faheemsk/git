<%-- 
    Document   : roadmapsummary
    Created on : Feb 1, 2017, 3:35:12 PM
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
        <link rel="stylesheet" href="<c:url value="/appcss/roadmapsummary.css"/>" />
        <!-- CSS : Include -->

        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/js/ddaccordion.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/appjs/roadmapsummary.js"/>"></script>
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
                <li><a href="#" onclick="headerFn('analystvalidationworklist.htm')" title="Analyst Validation Module">Analyst Validation Module</a></li>
                <li><a href="#" onclick="fnBackToFindings()" title="Review Finding List">Review Finding List</a></li>
                <li>Roadmap</li>
            </ul>
            <!-- End: Breadcrumbs -->

            
            <!-- content starts -->
            <div class="ux-content">
                <h1 class="ux-float-left ux-width-full-4t ux-margin-top-1t">Roadmap Summary</h1>
                <span class="ux-float-right ux-padding-1t">
                    <input type="button" value="Back" title="Back" class="ux-btn-default-action " onclick="fnBackToFindings()" />
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
                        <h2 class="ux-float-left ux-width-full-3t-half ux-padding-halft">Roadmap Vulnerabilities Category </h2>
                        <div class="ux-float-right ux-padding-halft"> 
                            <form name="createRoadmap" id="createRoadmap" method="post" action="createroadmapcat.htm">
                                <input type="hidden" value="${engagementDTO.encEngagementCode}" name="clientEngagementDTO.encEngagementCode" id="clientEngagementDTO.encEngagementCode"/>
                                <input type="hidden" value="${engagementDTO.orgSchema}" name="orgSchema" id="orgSchema"/>
                                <input type="hidden" value="${engagementDTO.clientID}" name="clientEngagementDTO.clientID" id="clientID"/>
                                <input type="submit" value="Create/Refresh" title="Create/Refresh" class="ux-btn-default-action" />
                            </form> 
                        </div>
                        
                    </div>
                    
                <form name="editRoadmap" commandName="vulnerabilityDTOs" method="POST">
                    <input type="hidden" value="${engagementDTO.encEngagementCode}" name="cgenk"/>
                    <input type="hidden" value="${engagementDTO.orgSchema}" name="orgSchma"/>
                    <input type="hidden" value="1" name="pgcnt" id="pgcnt"/>
                    <input type="hidden" value="${pageNo}" name="pgno"/>
                    <input type="hidden" value="" name="csacode" id="csacode"/>
                    <input type="hidden" value="" name="catcode" id="catcode"/>
                    
                    <!--Start: panel content-->
                    <div class="ux-panl-content">
                        <input type="hidden" value="${engagementDTO.encEngagementCode}" name="clientEngagementDTO.encEngagementCode" id="encEngagementCode"/>
                        <div class="ux-clear"></div>
                        <table  id="dataTable"  width="100%" class="display">
                            <thead>
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td>All</td>
                                    <td>All</td>
                                    <td>All</td>
                                    <td><input type="text" onchange="$.fn.effectiveDateFilter(this, '#dataTable')" id="effectiveDateId"  class="ux-width-5t" readonly="true"/></td>
                                    <td><input type="text" onchange="$.fn.pubDateFilter(this, '#dataTable')" id="pubDateId"  class="ux-width-5t" readonly="true"/></td>
                                    <td><span><a href="#" onclick="$.fn.SearchFilterClearRoadmap(this, '#dataTable')" title="Refresh"><img src="images/refresh.png" /></a></span></td>
                                </tr>
                                <tr>
                                    <th><strong>Vulnerability Category</strong></th>
                                    <th><strong>CSA Domain</strong></th>
                                    <th><strong>Severity</strong></th>
                                    <th><strong>Cost Effort</strong></th>
                                    <th><strong>Timeline</strong></th>
                                    <th class="ux-width-8t"><strong>Effective Date</strong></th>
                                    <th class="ux-width-8t"><strong>Published Date</strong></th>
                                    <th><strong>Action</strong></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="roadmapDTO" items="${roadmapList}" >
                                    <tr>
                                        <td><c:out value="${roadmapDTO.rootCauseName}" escapeXml="true"/></td>
                                        <td><c:out value="${roadmapDTO.csaDomainName}" escapeXml="true"/></td>
                                        <td><c:out value="${roadmapDTO.cveInformationDTO.severityName}" escapeXml="true"/></td>
                                        <td><c:out value="${roadmapDTO.cveInformationDTO.costEffortName}" escapeXml="true"/></td>
                                        <td><c:out value="${roadmapDTO.timeLineName}" escapeXml="true"/></td>
                                        <td><c:out value="${roadmapDTO.effectiveDate}" escapeXml="true"/></td>
                                        <td><c:out value="${roadmapDTO.clientEngagementDTO.publishedDate}" escapeXml="true"/></td>
                                        <td>
                                            <a href="#" onclick="fnEditRoadmap('${fn:trim(engagementDTO.encEngagementCode)}','${engagementDTO.orgSchema}',
                                                        '${roadmapDTO.csaDomainCode}','${roadmapDTO.rootCauseCode}')"
                                               title="Edit" class="ux-icon-edit">Edit</a>
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
