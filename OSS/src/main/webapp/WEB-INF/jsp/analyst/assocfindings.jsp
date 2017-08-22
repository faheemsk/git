<%-- 
    Document   : assocfindings
    Created on : Feb 6, 2017, 2:46:52 PM
    Author     : sbhagavatula
--%>

<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<un:useConstants className="com.optum.oss.constants.PermissionConstants" var="prmnsConstants" />
<script type="text/javascript" src="<c:url value="/js/jquery.simplePagination.js"/>"></script>
<link href="<c:url value="/css/simplePagination.css"/>" type="text/css" rel="stylesheet"/>

<script type="text/javascript">
    $(document).ready(function() {
        
        $('.fixme').fixheadertable({
            zebra: false,
            <c:if test="${vulnerabilityList[0].vulCount > 10}">
                height: 250,
            </c:if>
            whiteSpace: 'normal', resizeCol: false
        });
     });
     
     $("#searchFindings").click(function() {
        document.searEditForm.action = "editroadmap.htm";
        document.searEditForm.submit();
    });

    jQuery(function($) {
        <c:if test="${fn:length(vulnerabilityList) > 0}">
          var numItems = ${vulnerabilityList[0].vulCount};
        </c:if>
        var perPage = ${searchVuln.rowCount};

        // now setup pagination
        $("#pagination").pagination({
            items: numItems,
            itemsOnPage: perPage,
            cssStyle: "light-theme",
            currentPage: ${pageNo},
            onPageClick: function(pageNumber) { // this is where the magic happens
                $("#pgcnt").val(pageNumber);
                document.searEditForm.action = "editroadmap.htm";
                document.searEditForm.submit();

            }
        });
    });
</script>

<!--Start: panel-->
<div class="ux-panl ux-margin-top-1t">
    <form name="searEditForm" commandName="vulnerabilityDTOs" method="POST">
        <input type="hidden" value="${engagementDTO.encEngagementCode}" name="cgenk"/>
        <input type="hidden" value="${engagementDTO.orgSchema}" name="orgSchma"/>
        <input type="hidden" value="1" name="pgcnt" id="pgcnt"/>
        <input type="hidden" value="${pageNo}" name="pgno"/>
        <input type="hidden" value="${roadmapDTO.csaDomainCode}" name="csacode" id="csacode"/>
        <input type="hidden" value="${roadmapDTO.rootCauseCode}" name="catcode" id="catcode"/>
        <div class="ux-panl-header ux-panl-with-underline">
            <h2>Associated Findings</h2>
        </div>
        <!--Start: panel content-->
        <div class="ux-panl-content">
            
            <input type="hidden" value="${engagementDTO.encEngagementCode}" name="clientEngagementDTO.encEngagementCode" id="encEngagementCode"/>
            <div class="ux-clear"></div>
            <label id="errorCheckboxValidation" style="color: red" class="ux-float-left"></label>
            <input type="hidden" value="0" name="encInstanceIdentifier" id="encInstanceIdentifier"/>
            <table id="" class="fixme" >
                <thead>
                    <tr>
                        <th><strong>Finding ID</strong></th>
                        <th><strong>Finding Name</strong></th>
                        <th><strong>Service</strong></th>
                        <th ><strong>Source</strong></th>
                        <th ><strong>IP Address</strong></th>
                        <th ><strong>Application Name</strong></th>
                        <th ><strong>Severity</strong></th>
                        <th ><strong>Status</strong></th>
                        <th ><strong>Action</strong></th>
                    </tr>
                    <tr>
                        <td class="ux-padding-left-1t">
                            <input type="text" name="searchID" value="${searchVuln.searchID}"
                                   onkeypress="return numbersonly(this, event)" class="ux-width-6t" maxlength="10"/>
                        </td>
                        <td class="ux-padding-left-1t ">
                            <input type="text" name="vulnerabilityName" value="${searchVuln.vulnerabilityName}" class="ux-width-full-inclusive " onkeyup="limiter(this, 200, '', '')"/>
                        </td>
                        <td class="ux-padding-left-1t ">
                            <select id="securityServiceID" name="securityServiceObj.securityServiceCode" class="ux-width-full-inclusive">
                                <option value="">Select</option>
                                <c:forEach var="serviceDTO" items="${servicesList}">
                                    <c:if test="${serviceDTO.reviewStatus ne constants.DB_ROW_STATUS_REVIEWED}">
                                        <option value="${serviceDTO.securityServiceCode}" <c:if test="${serviceDTO.securityServiceCode eq searchVuln.securityServiceObj.securityServiceCode}">selected</c:if> >${serviceDTO.securityServiceName}</option>
                                    </c:if>
                                </c:forEach>
                            </select> 
                        </td>
                        <td class="ux-padding-left-1t ">
                            <select class="ux-width-full-inclusive" name="sourceCode" id="statusCodeID">     
                                <option value="0">Select</option>
                                <c:forEach var="sourceDTO" items="${sourceList}">
                                    <option value="${sourceDTO.masterLookupKey}" <c:if test="${searchVuln.sourceCode eq sourceDTO.masterLookupKey}">selected</c:if> >${sourceDTO.lookUpEntityName}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td class="ux-padding-left-1t ">
                            <input type="text"  name="ipAddress" value="${searchVuln.ipAddress}" class="ux-width-full-inclusive"
                                   onkeyup="limiter(this, 35, '', '')"/>
                        </td>
                        <td class="ux-padding-left-1t">
                            <input type="text" name="softwareName" value="${searchVuln.softwareName}" class="ux-width-full-inclusive"/>
                        </td>
                        <td class="ux-padding-left-1t ">
                            <select class="ux-width-full-inclusive" name="cveInformationDTO.severityCode" id="statusCodeID">     
                                <option value="">Select</option>
                                <c:forEach var="severityDTO" items="${severityList}">
                                    <option value="${severityDTO.id}" <c:if test="${searchVuln.cveInformationDTO.severityCode eq severityDTO.id}">selected</c:if> >${severityDTO.name}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td class="ux-padding-left-1t ">
                        </td>
                        <td  class="ux-padding-left-1t ux-padding-bottom-halft">
                            <a href="#" id="searchFindings">
                                <img src="images/search.png" title="Search"/>
                            </a>
                            <a href="javascript:void(0)" id="fnRefreshFindings"><img src="images/refresh.png" title="Refresh"/></a>
                        </td>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="count" value="0"/>
                    <c:choose>
                        <c:when test="${fn:length(vulnerabilityList) > 0}">
                            <c:forEach var="vulnerabilityDTO" items="${vulnerabilityList}" begin="0" end="100" >
                                <input type="hidden" value="${vulnerabilityDTO.encInstanceIdentifier}" id="encIdentifier${count}" name="instanceIdentifier"/>
                                <tr <c:if test="${count %2 != 0}">style="background-color:#fbf9ba"</c:if> >
                                    <td>
                                        <c:out value="${vulnerabilityDTO.instanceIdentifier}" escapeXml="true"/>
                                    </td>
                                    <td><c:out value="${vulnerabilityDTO.vulnerabilityName}" escapeXml="true"/></td>
                                    <td><c:out value="${vulnerabilityDTO.securityServiceObj.securityServiceName}" escapeXml="true"/></td>
                                    <td><c:out value="${vulnerabilityDTO.sourceName}" escapeXml="true"/></td>
                                    <td><c:out value="${vulnerabilityDTO.ipAddress}" escapeXml="true"/></td>
                                    <td><c:out value="${vulnerabilityDTO.softwareName}" escapeXml="true"/></td>
                                    <td><c:out value="${vulnerabilityDTO.cveInformationDTO.severityName}" escapeXml="true"/></td>
                                    <td><c:out value="${vulnerabilityDTO.statusName}" escapeXml="true"/></td>
                                    <td>
                                        
                                        <c:if test="${vulnerabilityDTO.rowStatusValue eq 'Not Reviewed'}">
                                            <a href="#" title="Edit" id="editVulnerabilityID" class="ux-icon-edit"
                                               onclick="editFinding(${count})"></a>
                                        </c:if>
                                    </td>
                                </tr>
                                <c:set var="count" value="${count+1}"/>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <td colspan="9">
                                <center>No matching records found</center>
                            </td>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
            <div id="pagination"></div>
        </div>
        <!--End: panel content-->
    </form>
    <form action="" name="refreshFindingListForm" commandName="vulnerabilityDTOs" method="POST">
        <input type="hidden" value="${engagementDTO.encEngagementCode}" name="cgenk"/>
        <input type="hidden" value="${engagementDTO.orgSchema}" name="orgSchma"/>
        <input type="hidden" value="1" name="pgcnt" id="pgcnt"/>
        <input type="hidden" value="${roadmapDTO.csaDomainCode}" name="csacode" id="csacode"/>
        <input type="hidden" value="${roadmapDTO.rootCauseCode}" name="catcode" id="catcode"/>
    </form>
</div>
<!--End: panel-->
