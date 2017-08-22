<%-- 
    Document   : editassocfinding
    Created on : Mar 16, 2017, 4:42:45 AM
    Author     : sbhagavatula
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
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
        <link rel="stylesheet" href="<c:url value="/appcss/addremediationplan.css"/>" />
        <!-- CSS : Include -->

        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/js/ddaccordion.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/appjs/editassocfinding.js"/>"></script>
        <!-- JS : Include -->

    </head>
    <body>
        <un:useConstants className="com.optum.oss.constants.PermissionConstants" var="prmnsConstants" />
        <un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="appConstants" />
        <div class="loader"></div>
        <!-- Start: Wrapper -->
        <div id="ux-wrapper"> 

            <!-- Header : Include -->
            <jsp:include page="../includes/header.jsp"/>
            <!-- Header : Include -->

            <!-- Start: Breadcrumbs -->
            <ul class="ux-brdc ux-margin-left-1t" >
                <li>Optum Security Solutions</li>
                <li><a href="#" onclick="headerFn('remediationhome.htm')" title="Remediation Module">Remediation Module</a></li>
                <li><a href="#" onclick="toRemediationPlan()" title="Remediation Plan">Remediation Plan</a></li>
                <li><a href="#" onclick="toEditRemediationPlan()" title="Edit Remediation Plan">Edit Remediation Plan</a></li> 
                <li>Finding Remediation Status</li>
            </ul>
            <!-- End: Breadcrumbs -->

            <!-- content starts -->
            <div class="ux-content">
                <h1 class="ux-float-left ux-width-full-4t ux-margin-top-1t"> Finding Remediation Status</h1> <span class="ux-float-right ux-padding-1t">

                    <input type="button" value="Update" class="ux-btn-default-action" id="updateStatus"/>
                    <input type="button" value="Cancel" class="ux-btn-default-action" onclick="toEditRemediationPlan()" />
                    <input type="button" value="Back" class="ux-btn-default-action" onclick="toEditRemediationPlan()" />
                </span>
                <div class="ux-clear"></div>
                <c:if test="${updateSuccessMessage != null || !empty updateSuccessMessage}">
                    <div class="ux-msg-success msg"><c:out value="${updateSuccessMessage}" escapeXml="true" /></div>
                </c:if>
                    
                <div class="ux-panl ux-float-left">
                    <div class="ux-panl-header ux-panl-with-underline">
                        <h2 class="ux-float-left ux-width-full-3t-half ux-padding-halft">Action Plan Information </h2>

                    </div>
                    <div class="ux-panl-content">

                        <div class="ux-clear"></div>
                        <div class="ux-hform">
                            <dl>
                                <dt><label>Action Plan ID:</label></dt>
                                <dd>${planInfo.planID}</dd>
                            </dl>

                            <dl>
                                <dt><label>Action Plan Title:</label></dt>
                                <dd>${planInfo.planTitle}</dd>
                            </dl>

                            <dl >
                                <dt><label>Create Date:</label></dt>
                                <dd>${planInfo.createdDate}</dd>
                            </dl>
                            <dl >
                                <dt><label>Notification Date:</label></dt>
                                <dd>${planInfo.notifyDate}</dd>
                            </dl>
                        </div>

                    </div>
                </div>

                <div class="ux-panl ux-float-left">
                    <div class="ux-panl-header ux-panl-with-underline">
                        <h2 class="ux-float-left ux-width-full-3t-half ux-padding-halft">Finding Details </h2>
                    </div>
                    <div class="ux-panl-content">

                        <div class="ux-clear"></div>
                        <div class="ux-hform">
                            <dl>
                                <dt><label>Finding ID:</label></dt>
                                <dd>${findingObj.instanceIdentifier}</dd>
                            </dl>

                            <dl>
                                <dt><label>Finding Name:</label></dt>
                                <dd>${findingObj.vulnerabilityName}</dd>
                            </dl>
                            <dl class="ux-width-10t">
                                <dt><label>Severity:</label></dt>
                                <dd>${findingObj.cveInformationDTO.severityName}</dd>
                            </dl>
                            <dl class="ux-width-10t">
                                <dt><label>CVE:</label></dt>
                                <dd>${findingObj.cveInformationDTO.cveIdentifier}</dd>
                            </dl>
                            <dl class="ux-width-10t">
                                <dt><label>Source:</label></dt>
                                <dd>${findingObj.sourceName}</dd>
                            </dl>
                            <dl class="ux-width-10t">
                                <dt><label>Service:</label></dt>
                                <dd>${findingObj.securityServiceObj.securityServiceName}</dd>
                            </dl>
                            <dl class="ux-width-10t">
                                <dt><label>Detection Date:</label></dt>
                                <dd>${findingObj.createdDate}</dd>
                            </dl>
                            <div class="ux-clear"></div>

                            <dl>
                                <dt><label>IP:</label></dt>
                                <dd>${findingObj.ipAddress}</dd>
                            </dl>
                            <dl >
                                <dt><label>Host Name:</label></dt>
                                <dd>${findingObj.hostName}</dd>
                            </dl>
                            <dl>
                                <dt><label>OS:</label></dt>
                                <dd>${findingObj.operatingSystem}</dd>
                            </dl>
                            <dl>
                                <dt><label>Application Name:</label></dt>
                                <dd>${findingObj.softwareName}</dd>
                            </dl>
                            <dl>
                                <dt><label>URL:</label></dt>
                                <dd>${findingObj.appURL}</dd>
                            </dl>
                            <div class="ux-clear"></div>
                            <dl class="LongTxt">
                                <dt><label>Finding Description:</label></dt>
                                <dd>${findingObj.description}</dd>
                            </dl>
                            <dl class="LongTxt">
                                <dt><label>Recommended Remediation:</label></dt>
                                <dd>${findingObj.recommendation}</dd>
                            </dl>
                        </div>

                    </div>
                </div>
                <div class="ux-panl ux-float-left">
                    <div class="ux-panl-header ux-panl-with-underline">
                        <h2 class="ux-float-left ux-width-full-3t-half ux-padding-halft">Finding Status </h2>

                    </div>
                    <div class="ux-panl-content">
                        <form name="editFindingStatus" method="post">
                            <input type="hidden" value="sdfa" name="mnval"/>
                        <div class="ux-clear"></div>
                        <div class="ux-hform">
                            <c:set var="status" value="false" />
                            <c:if test="${findingStatus.statusCode eq appConstants.DB_CONSTANT_RISK_ACCEPTED || findingStatus.statusCode eq appConstants.DB_CONSTANT_RISK_SHARED
                                        || findingStatus.statusCode eq appConstants.DB_CONSTANT_RISK_TRANSFER || findingStatus.statusCode eq appConstants.DB_CONSTANT_RISK_AVOID
                                        || findingStatus.statusCode eq appConstants.DB_CONSTANT_RISK_MITIGATE|| planInfo.planStatus eq appConstants.DB_CONSTANT_FINDING_CLOSED_STATUS_VALUE}">
                                 <c:set var="status" value="true" />
                            </c:if>
                            
                            <c:choose>
                                <c:when test="${permissions.contains(prmnsConstants.EDIT_FINDING) && !status}">
                                    <dl>
                                        <dt><label>Finding Remediation Status:</label></dt>
                                        <dd>
                                            <select name="statusCode" id="statusCdId" >
                                                <option value="0">Select</option>
                                                <c:forEach var="remStatus" items="${remStatusList}">
                                                    <option value="${remStatus.masterLookupCode}" <c:if test="${remStatus.masterLookupCode eq findingStatus.statusCode}">selected</c:if>>${remStatus.lookUpEntityName}</option>
                                                </c:forEach>
                                            </select>
                                        </dd>
                                        <label id="statusErrID" class="ux-msg-error-under"></label>
                                    </dl>

                                    <dl class="LongTxt" id="closureid">
                                        <dt><label>Reason for Closure: <span>*</span></label></dt>
                                        <dd><textarea class="TextareaLong ux-height-5t" name="closedComments" id="clsdCommId">${findingStatus.closedComments}</textarea></dd>
                                        <label id="commErrID" class="ux-msg-error-under"></label>
                                    </dl>
                                    
                                    <div id="adjustedId">
                                    <dl>
                                        <dt><label>Adjusted Severity: <span>*</span></label></dt>
                                        <dd>
                                            <select name="adjSeverityCode" id="adjSeverityId">
                                                <option value="0">Select</option>
                                                <c:forEach var="severityDTO" items="${severityList}">
                                                    <option value="${severityDTO.id}">${severityDTO.name}</option>
                                                </c:forEach>
                                            </select>
                                        </dd>
                                        <label id="adjSevErrId" class="ux-msg-error-under"></label>
                                    </dl>
                                    
                                    <dl class="LongTxt">
                                        <dt><label>Compensating Control: <span>*</span></label></dt>
                                        <dd><textarea class="TextareaLong ux-height-5t" name="compensationCtrl" id="compCrtlId"></textarea></dd>
                                        <label id="compCtrlErrId" class="ux-msg-error-under"></label>
                                    </dl>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <dl>
                                        <dt><label>Finding Remediation Status: </label></dt>
                                        <dd>${findingObj.statusName}</dd>
                                    </dl>

                                    <dl class="LongTxt">
                                        <dt><label>Reason for Closure:</label></dt>
                                        <dd>${findingStatus.closedComments}</dd>
                                    </dl>
                                </c:otherwise>
                            </c:choose>
                            
                                
                                
                            <dl id="clsdateid">
                                <dt><label>Closed Date:</label></dt>
                                <dd>${findingStatus.closedDate}</dd>
                            </dl>

                        </div>
                        <input type="hidden" value="${findingObj.instanceIdentifier}" name="instanceIdentifier"/>
                        <input type="hidden" value="${findingObj.instanceIdentifier}" name="removeInstances"/>
                        <input type="hidden" value="${engagementDTO.encEngagementCode}" name="redengkey"/>
                        <input type="hidden" value="${planInfo.planID}" name="planID"/>
                        </form>
                    </div>
                </div>
                
            </div>
            <!-- content ends --> 

            <!-- Footer : Include -->
            <jsp:include page="../includes/footer.jsp"/>
            <!-- Footer : Include --> 
        </div>
        <!-- End: Wrapper --> 
    </body>
</html>

