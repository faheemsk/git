<%-- 
    Document   : analystvalidationworklist
    Created on : May 26, 2016, 5:07:16 PM
    Author     : sathuluri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
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
        <link rel="stylesheet" href="<c:url value="/appcss/analystvalidationworklist.css"/>" />
        <!-- CSS : Include -->
        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/appjs/analystvalidationworklist.js"/>"></script>
        <!-- JS : Include -->  
        <!-- Header : Include -->
        <jsp:include page="../includes/header.jsp"/>
        <!-- Header : Include -->
    </head>
    <body>
        <!-- Start: Wrapper -->
        <div id="ux-wrapper"> 
            <!-- Start: Breadcrumbs -->
            <ul class="ux-brdc ux-margin-left-1t" >
                <li>Optum Security Solutions</li>
                <li>Analyst Validation Module</li>
            </ul>
            <!-- End: Breadcrumbs -->

            <!-- Left Panel : Include -->
            <jsp:include page="../includes/leftpanel.jsp"/>
            <!-- Left Panel : Include -->

            <!-- Start: Content -->
            <div class="ux-content">
                <h1>Analyst Validation Module</h1>
                <c:if test="${null != reviewSuccessMessage}">
                    <div class="ux-msg-success">${reviewSuccessMessage}</div>
                </c:if>
                <form name="searchAnalystValidationForm" action="#" method="POST" id="searchAnalystValidationForm" commandName="">
                    <input type="hidden" name="cgenk" id="cgenk"/>
                    <input type="hidden" name="orgSchma" id="orgSchma"/>
                    <input type="hidden" name="pgcnt" id="pgcnt" value="1"/>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="fixme">
                        <thead>
                            <tr>
                                <th><strong>Client Name</strong></th>
                                <th><strong>Engagement Code</strong></th>
                                <th><strong>Engagement Name</strong></th>
                                <th><strong>Services</strong></th>
                                <th><strong>Service Start Date</strong></th>
                                <th><strong>Service End Date</strong></th>
                                <th><strong>Last Reviewed On</strong></th>
                                <th><strong>Review Status</strong></th>
                                <th style="text-align: center;"><strong>Action</strong></th>
                            </tr>
                            <tr>
                                <td class="ux-padding-left-1t ux-padding-bottom-1t">
                                    <input type="text" name="organizationName" value="${searchAnalystWorklist.organizationName}" class="ux-width-full-inclusive"/>
                                </td>
                                <td class="ux-padding-left-1t ux-padding-bottom-1t">
                                    <input type="text" name="clientEngagementCode" value="${searchAnalystWorklist.clientEngagementCode}"
                                           class="ux-width-full-inclusive"/>
                                </td>
                                <td class="ux-padding-left-1t ux-padding-bottom-1t">
                                    <input type="text" name="engagementName" value="${searchAnalystWorklist.engagementName}" class="ux-width-full-inclusive "/>
                                </td>
                                <td class="ux-padding-left-1t ux-padding-bottom-1t ux-width-15t">
                                    <input type="text" name="serviceName" value="${searchAnalystWorklist.serviceName}" class="ux-width-full-inclusive "/>
                                </td>
                                <td class="ux-padding-left-1t ux-padding-bottom-1t">
                                    <input type="text"  name="startDate" value="${searchAnalystWorklist.startDate}"
                                           class="ux-width-full-7t defaultActualPicker" readonly="true"/>
                                </td>
                                <td class="ux-padding-left-1t ux-padding-bottom-1t">
                                    <input type="text"  name="endDate" value="${searchAnalystWorklist.endDate}" class="ux-width-full-7t defaultActualPicker"
                                           readonly="true"/>
                                </td>
                                <td class="ux-padding-left-1t ux-padding-bottom-1t">
                                    <input type="text"  name="modifiedDate" value="${searchAnalystWorklist.modifiedDate}" 
                                           class="ux-width-full-7t defaultActualPicker" readonly="true"/>
                                </td>
                                           <td style="text-align: center;" class="ux-padding-left-1t ux-padding-bottom-1t">
                                    <!--Bug Id: IN:021190 : Modified Test box to Select dropdown for Review Status Search-->
                                    <select name="rowStatusValue" class="ux-width-full-inclusive">
                                        <option value="" <c:if test="${searchAnalystWorklist.rowStatusValue == ''}">selected</c:if>>Select</option>
                                        <option value="Reviewed" <c:if test="${searchAnalystWorklist.rowStatusValue == 'Reviewed'}">selected</c:if>>Reviewed</option>
                                        <option value="Not Reviewed" <c:if test="${searchAnalystWorklist.rowStatusValue == 'Not Reviewed'}">selected</c:if>>Not Reviewed</option>
                                    </select>
                                    <!--Bug Id: IN:021190 : Modified Test box to Select dropdown for Review Status Search-->
                                </td>
                                <td style="text-align: center;" class="ux-padding-left-1t ux-padding-bottom-1t">
                                    <a href="#" id="searchAnalystValidationWorklist">
                                        <img src="images/search.png" title="Search"/>
                                    </a>
                                    <a href="#" onclick="headerFn('analystvalidationworklist.htm')"><img src="images/refresh.png" title="Refresh"/></a>
                                </td>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${engagementMap.size()> 0}">
                                    <c:forEach var="engagementMap" items="${engagementMap}">
                                        <c:set var="orgCount" value="0"/>
                                        <c:set var="organizationDetails" value="${fn:split(engagementMap.key, constants.SYMBOL_SPLITING)}" />
                                        <c:set value="${organizationDetails[0]}" var="orgId"></c:set>
                                        <c:set value="${organizationDetails[1]}" var="orgName"></c:set>
                                        <c:set value="${organizationDetails[2]}" var="orgSchema"></c:set>
                                        
                                            <tr>
                                                <td rowspan="${organizationServieCount[engagementMap.key].size()}" valign="top">
                                                <c:out value="${orgName}" escapeXml="true"/>
                                            </td>

                                            <c:set var="engegementSet" value="${engagementMap.value}"/>
                                            <c:set var="engCount" value="0"/>
                                            <c:set var="colorCount" value="0"/>
                                            <c:forEach var="engagementCode" items="${engegementSet}">

                                                <c:set var="engDetails" value="${fn:split(engagementCode, constants.SYMBOL_SPLITING)}" />
                                                <c:set value="${engDetails[0]}" var="engID"></c:set>
                                                <c:set value="${engDetails[1]}" var="engName"></c:set>

                                                <c:set var="serviceList" value="${serviceMap[engagementCode]}"/>
                                                <c:if test="${engCount!=0}"> <tr> </c:if>
                                                    <td valign="top" rowspan="${serviceList.size()}">
                                                        <c:out value="${engID}" escapeXml="true"/>
                                                    </td>
                                                    <td rowspan="${serviceList.size()}" valign="top">
                                                        <c:out value="${engName}" escapeXml="true"/>
                                                    </td>
                                                    <c:set var="serCount" value="0"/>
                                                    <c:forEach var="serviceListIte" items="${serviceList}">
                                                        <c:if test="${serCount!=0}"> <tr
                                                            <c:if test="${colorCount %2 != 0}">style="background-color:#fbf9ba"</c:if> 
                                                            <c:if test="${colorCount %2 == 0}">style="background-color:none"</c:if> >  </c:if>
                                                                <td>
                                                                <c:out value="${serviceListIte.serviceName}" escapeXml="true"/>
                                                                <c:if test="${serviceListIte.fileLockIndicator == 1}">
                                                                    <span class="ux-icon-lock" title="Locked"></span>
                                                                </c:if>
                                                                <c:if test="${serviceListIte.fileLockIndicator != 1}">
                                                                    <span class="ux-icon-unlock" title="Unlocked"></span>
                                                                </c:if>
                                                            </td>
                                                            <td><c:out value="${serviceListIte.startDate}" escapeXml="true"/></td> 
                                                            <td><c:out value="${serviceListIte.endDate}" escapeXml="true"/></td>
                                                            <td><c:out value="${serviceListIte.modifiedDate}" escapeXml="true"/></td>
                                                            <td><c:out value="${serviceListIte.rowStatusValue}" escapeXml="true"/></td>
                                                            <c:if test="${serCount != 1}">
                                                                <td rowspan="${serviceList.size()}" valign=""
                                                                    style="border-right-width: 1px !important; vertical-align: middle; text-align: center">
                                                                    <c:if test="${permissions.contains(prmnsConstants.REVIEW_VULNERABILITY)}">
                                                                        <c:if test="${statusMap[engID] == 'Not Reviewed'}">
                                                                            <a href="javascript:void(0)"
                                                                               onclick="fnReviewFinding('${fn:trim(serviceListIte.encClientEngagementCode)}','${orgSchema}')"
                                                                               class="ux-icon-review" title="Review"/>
                                                                        </c:if>
                                                                    </c:if>
                                                                    <c:if test="${permissions.contains(prmnsConstants.VIEW_VULNERABILITY)}">
                                                                        <c:if test="${statusMap[engID] != 'Not Reviewed'}">
                                                                            <a href="javascript:void(0)"
                                                                               onclick="fnViewFinding('${fn:trim(serviceListIte.encClientEngagementCode)}','${orgSchema}')"
                                                                               class="ux-icon-view" title="View"/>
                                                                        </c:if>
                                                                    </c:if>
                                                                </td>
                                                            </c:if>
                                                            <c:if test="${serCount!=0}">   </tr> </c:if>
                                                        <c:if test="${serCount==0 && engCount==0 && orgCount==0}">    </tr> </c:if>
                                                        <c:set var="orgCount" value="1"/>
                                                        <c:set var="serCount" value="1"/>
                                                        <c:set var="colorCount" value="${colorCount + 1}"/>
                                                    </c:forEach>
                                                    <c:if test="${engCount !=0}">  </tr> </c:if>
                                                <c:set var="engCount" value="1"/>
                                            </c:forEach>                                           

                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="10" style="border-right-width: 1px !important;">
                                            <center >No matching records found</center>
                                        </td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </form>
            </div>
            <!-- End: Content -->
            <!-- Start: Footer -->
            <jsp:include page="../includes/footer.jsp"/>
            <!-- End: Footer -->
        </div>
        <!-- End: Wrapper -->
    </body>
</html>
