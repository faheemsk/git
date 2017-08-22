<%-- 
    Document   : remediationworklist
    Created on : Jul 8, 2016, 12:36:07 PM
    Author     : sbhagavatula
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
        <link rel="stylesheet" href="<c:url value="/appcss/remediationworklist.css"/>" />
        <!-- CSS : Include -->
        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/appjs/remediationworklist.js"/>"></script>
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
                <li>Remediation Tracking</li>
            </ul>
            <!-- End: Breadcrumbs -->

            <!-- Left Panel : Include -->
            <jsp:include page="../includes/leftpanel.jsp"/>
            <!-- Left Panel : Include -->

            <!-- Start: Content -->
            <div class="ux-content">
                <h1>Remediation Tracking</h1>
                <form name="searchRemediationForm" action="#" method="POST" id="searchRemediationForm">
                    <input type="hidden" id="Viewremediationengkey" name="engkey"></input>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="fixme">
                        <thead>
                            <tr>
                                <th><strong>Client Name</strong></th>
                                <th><strong>Engagement Code</strong></th>
                                <th><strong>Engagement Name</strong></th>
                                <th><strong>Action</strong></th>
                            </tr>
                            <tr>
                                <td class="ux-padding-left-1t ux-padding-bottom-1t">
                                    <input type="text" name="organizationName" value="${remediationData.organizationName}" class="ux-width-12t "/>
                                </td>
                                <td class="ux-padding-left-1t ux-padding-bottom-1t">
                                    <input type="text" name="clientEngagementCode" value="${remediationData.clientEngagementCode}"
                                           class="ux-width-10t "/>
                                </td>
                                <td class="ux-padding-left-1t ux-padding-bottom-1t">
                                    <input type="text" name="engagementName" value="${remediationData.engagementName}" class="ux-width-12t "/>
                                </td>
                                <td class="ux-padding-left-1t ux-padding-bottom-1t">
                                    <a href="#" id="searchRemediationWorklist">
                                        <img src="images/search.png" title="Search"/>
                                    </a>
                                    <a href="#" onclick="headerFn('remediationworklist.htm')"><img src="images/refresh.png" title="Refresh"/></a>
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
                                                <c:if test="${engCount!=0}"> <tr <c:if test="${colorCount %2 != 0}">style="background-color:#fbf9ba"</c:if>> </c:if>
                                                            <td valign="top" >
                                                        <c:out value="${engID}" escapeXml="true"/>
                                                    </td>
                                                    <td valign="top">
                                                        <c:out value="${engName}" escapeXml="true"/>
                                                    </td>

                                                    <c:set var="serCount" value="0"/>
                                                    <c:forEach var="serviceListIte" items="${serviceList}">
                                                        <c:if test="${serCount!=0}"><tr></c:if>
                                                            <c:if test="${serCount==0}">
                                                                <td valign="top" style="border-right-width: 1px !important;">
                                                                    <c:if test="${permissions.contains(prmnsConstants.REMEDIATE)}">
                                                                        <a href="javascript:void(0)"
                                                                           title="Remediation" class="ux-icon-remediation" onclick='fnViewremediation("${fn:trim(serviceListIte.encClientEngagementCode)}")'>Remediation</a>
                                                                    </c:if>
                                                                </td> 
                                                            </c:if>
                                                            <c:if test="${engCount==0 }">    </tr> </c:if>
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

