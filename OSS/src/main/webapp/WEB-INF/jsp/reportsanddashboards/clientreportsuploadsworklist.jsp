
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
        <link rel="stylesheet" href="<c:url value="/appcss/clientreportuploadworklist.css"/>" />
        <!-- CSS : Include -->
        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/appjs/clientreportuploadworklist.js"/>"></script>
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
                <li>Reporting Documents</li> 
            </ul>
            <!-- End: Breadcrumbs -->

            <!-- Left Panel : Include -->
            <jsp:include page="../includes/leftpanel.jsp"/>
            <!-- Left Panel : Include -->

            <!-- Start: Content -->
            <div class="ux-content">
                <h1>Reporting Documents</h1> 
                <c:if test="${null != reviewSuccessMessage}">
                    <div class="ux-msg-success">${reviewSuccessMessage}</div>
                </c:if>
                <form name="searchclientreportuploadworklistForm" action="#" method="POST" id="searchclientreportuploadworklistForm" commandName="">
                    <input type="hidden" name="ec" id="ec"/>
                    <%--<c:set var="notPublsh" value="${constants.DB_ROW_STATUS_NOTPUBLISHED_VALUE}"/>--%>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="fixme">
                        <thead>
                            <tr>
                                <th><strong>Client Name</strong></th>
                                <th><strong>Engagement Code</strong></th>
                                <th><strong>Engagement Name</strong></th>
                                <th><strong>Report Name</strong></th>
                                <th><strong>File Name </strong></th>
                                <th><strong>Updated On </strong></th>
                                <th><strong>Status</strong></th>
                                <th><strong>Action</strong></th>

                            </tr>
                            <tr>
                                <td class="ux-padding-left-1t ux-padding-bottom-1t">
                                    <input type="text" name="organizationName" value="${searchClientReportWorklist.organizationName}" class="ux-width-full-inclusive "/>
                                </td>
                                <td class="ux-padding-left-1t ux-padding-bottom-1t">
                                    <input type="text" name="clientEngagementCode" value="${searchClientReportWorklist.clientEngagementCode}"class="ux-width-full-inclusive"/>

                                </td>
                                <td class="ux-padding-left-1t ux-padding-bottom-1t">
                                    <input type="text" name="clientEngagementName" value="${searchClientReportWorklist.clientEngagementName}" class="ux-width-full-inclusive"/>
                                </td>
                                <td class="ux-padding-left-1t ux-padding-bottom-1t ux-width-15t">
                                    <input type="text" name="reportName" value="${searchClientReportWorklist.reportName}" class="ux-width-full-inclusive"/>
                                </td>
                                <td class="ux-padding-left-1t ux-padding-bottom-1t ux-width-15t">
                                    <input type="text" name="fileName" value="${searchClientReportWorklist.fileName}" class="ux-width-full-inclusive"/>
                                </td>
                                <td class="ux-padding-left-1t ux-padding-bottom-1t">
                                    <input type="text" name="updateDate" value="${searchClientReportWorklist.updateDate}" class="ux-width-full-6t defaultActualPicker"
                                           readonly="true"/>
                                </td>

                                <td class="ux-padding-left-1t ux-padding-bottom-1t">
                                    <select name="status" class="ux-width-full-inclusive">
                                        <option value="" <c:if test="${searchClientReportWorklist.status == ''}">selected</c:if>>Select</option>
                                        <option value="Published" <c:if test="${searchClientReportWorklist.status == 'Published'}">selected</c:if>>Published</option>
                                        <option value="Not Published" <c:if test="${searchClientReportWorklist.status == 'Not Published'}">selected</c:if>>Not Published</option>
                                        </select>
                                    </td>
                                    <td class="ux-padding-left-1t ux-padding-bottom-1t">
                                        <a href="#" id="searchclientreportuploadworklist">
                                            <img src="images/search.png" title="Search"/>
                                        </a>
                                        <a href="#" onclick="headerFn('reportsuploadworklist.htm')"><img src="images/refresh.png" title="Refresh"/></a>
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
                                                                <c:out value="${serviceListIte.reportName}" escapeXml="true"/>
                                                            </td>
                                                            <td><c:out value="${serviceListIte.fileName}" escapeXml="true"/></td> 
                                                            <td>
                                                                <%--<c:if test="${serviceListIte.status == 'Not Published'}">
                                                                    <c:out value="${serviceListIte.createdDate}" escapeXml="true"/>
                                                                </c:if>--%>
                                                                <%--<c:if test="${serviceListIte.status == 'Published'}">--%>
                                                                <c:out value="${serviceListIte.updateDate}" escapeXml="true"/>
                                                                <%--</c:if>--%>
                                                            </td>
                                                            <td>
                                                                <c:out value="${serviceListIte.status}" escapeXml="true"/>
                                                            </td>
                                                            <c:if test="${serCount != 1}">
                                                                <td rowspan="${serviceList.size()}" valign="top" style="border-right-width: 1px !important;">

                                                                    <c:if test="${permissions.contains(prmnsConstants.UPLOAD_CLIENT_REPORTS)}">
                                                                        <c:choose>
                                                                            <c:when test="${serviceListIte.reportName == ''}">
                                                                                <a href="javascript:void(0)" onclick="fnAddReport('${fn:trim(serviceListIte.encClientEngagementCode)}')" class="ux-icon-add" title="Add">Add</a>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <a href="javascript:void(0)" onclick="fnEditReport('${fn:trim(serviceListIte.encClientEngagementCode)}')" class="ux-icon-edit" title="Edit">Edit</a>
                                                                            </c:otherwise>
                                                                        </c:choose>
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
                                        <td align="center" colspan="8" style="border-right-width: 1px !important;">
                                            <center >     No matching records found</center>
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
