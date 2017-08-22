<%-- 
    Document   : engagementinformation
    Created on : May 26, 2016, 5:22:03 PM
    Author     : vkosuri
--%>

<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="constants"/>
<un:useConstants className="com.optum.oss.constants.PermissionConstants" var="permissionConstants" />
<script type="text/javascript" src="<c:url value="/appjs/vulengagementinfoJS.js"/>"></script>
<script type="text/javascript" src="<c:url value="/appjs/addreports.js"/>"></script>
<h1 class="ux-float-left ux-width-full-4t ux-margin-top-1t">Review Finding List</h1>

<span class="ux-float-right ux-padding-1t">
    <select class="ms ux-margin-top-min ux-width-15t" multiple="multiple" name="securityServiceObj.securityServiceCode">
        <c:forEach var="serviceDTO" items="${servicesList}">
            <c:choose>
                <c:when test="${serviceDTO.reviewStatus eq constants.DB_ROW_STATUS_REVIEWED}">
                    <option value="${serviceDTO.securityServiceCode}" selected="true">${serviceDTO.securityServiceName}</option>
                </c:when>
                <c:otherwise>
                    <option value="${serviceDTO.securityServiceCode}">${serviceDTO.securityServiceName}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </select>
    <input type="submit" value="Review Complete" title="Review Complete" class="ux-btn-default-action " onclick="window.location.href = ''" />

    <c:if test="${permissions!=null && (permissions.contains(permissionConstants.ADD_USER_TO_SERVICE)||permissions.contains(permissionConstants.EDIT_USER_TO_SERVICE))}">
        <input type="button" value="Roadmap" title="Roadmap" class="ux-btn-default-action" onclick="toRoadmap()"/>
    </c:if>
    <c:if test="${clientreportspermission!=null && clientreportspermission.contains(permissionConstants.VIEW_DASHBOARD)}">
        <input type="button" value="View Dashboard" title="View Dashboard" class="ux-btn-default-action" onclick="javascript:toDashboardPage('${constants.TO_REVIEW_VULNERABILITES_LISTPAGE}')"></input>
    </c:if>
    <input type="button" value="Back" title="Back" class="ux-btn-default-action " onclick="headerFn('analystvalidationworklist.htm')" />
</span>
<!--Bug Id: IN021204 : For display success message below header-->
<div class="ux-clear"></div>
<c:if test="${null != deleteSuccessMessage}">
    <div class="ux-msg-success"><c:out value="${deleteSuccessMessage}" escapeXml="true"/></div>
</c:if>
<c:if test="${addSuccessMessage != null || !empty addSuccessMessage}">
    <div class="ux-msg-success msg"><c:out value="${addSuccessMessage}" escapeXml="true" /></div>
</c:if>
<!--Bug Id: IN021204 : For display success message below header-->
<div class="ux-clear"></div>
<!--<div class="ux-accordion-panl">-->
<div id="accordion" class="ux-margin-top-2t" >
    <!--<h2><a class="menuitem submenuheader " href="#" > Engagement Information</a></h2>-->
    <h3>Engagement Information</h3>
    <!--</div>-->
    <!--<div class="submenu ux-padding-halft " >-->
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
<!--</div>-->
<script>
    $(".update").click(function() {
        $(".msg").toggle(  );
    });

    $(".hisotry").click(function() {
        $(".show").toggle(  );
    });
</script> 

