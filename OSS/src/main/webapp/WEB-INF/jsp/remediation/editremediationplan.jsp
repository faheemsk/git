<%-- 
    Document   : addremediationplan
    Created on : Mar 6, 2017, 3:01:28 AM
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
        <script type="text/javascript" src="<c:url value="/appjs/editremediationplan.js"/>"></script>
        <!-- JS : Include -->

    </head>
    <body>
        <un:useConstants className="com.optum.oss.constants.PermissionConstants" var="prmnsConstants" />
        <un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="appConstants" />
        
        <spring:form name="editRemediationFrm" action="" method="POST">
        <!-- Popup Starts -->
        <div id='blackcover'> </div>
        <div id='popupbox' class="ux-modl">
            <div class="ux-modl-header">
                <a href="#" onclick='popup(0)'>Close</a>
                <h6>Associated Findings  Status Change – In Progress</h6>
            </div>

            <div class="ux-modl-content">
                <h3>Number of finding select for In Progress: <span id="inProgressCnt"></span>  </h3>

                <div class="ux-margin-1t ux-margin-left-11t ux-float-right">
                    <input type="button" value="Save" class="ux-btn-default-action ux-width-7t" onclick='popup(0)' id="saveInProgress"/> <input type="button" value="Cancel" class="ux-btn-default-action ux-width-7t" onclick='popup(0)'/>
                </div>
            </div>
        </div>
        <!-- Popup Ends -->

        <!-- Popup Starts -->
        <div id='blackcover1'> </div>
        <div id='popupbox1' class="ux-modl">
            <div class="ux-modl-header">
                <a href="#" onclick='popup(0)'>Close</a>
                <h6>Risk Registry</h6>
            </div>

            <div class="ux-modl-content">
                <div class="ux-hform">
                    <c:if test="${planInfo.planRegKey != null || !empty planInfo.planRegKey}">
                    <dl>
                        <dt><label>Risk Register ID:</label></dt>
                        <dd>${planInfo.planRegKey}</dd>
                    </dl>
                    </c:if>
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
                    <div class="ux-clear"></div>
                    <dl class="LongTxt">
                        <dt><label>Compensating Control: <span>*</span></label></dt>
                        <dd><textarea class="TextareaLong ux-height-5t" name="compensationCtrl" id="compCrtlId"></textarea></dd>
                        <label id="compCtrlErrId" class="ux-msg-error-under"></label>
                    </dl>
                </div>

                <div class="ux-margin-1t ux-margin-left-11t ux-float-right">
                    <input type="button" value="Save" class="ux-btn-default-action ux-width-7t" onclick='' id="saveRisk"/> <input type="button" value="Cancel" class="ux-btn-default-action ux-width-7t" onclick='popup(0)'/>
                </div>
            </div>
        </div>
        <!-- Popup Ends -->
        
        <!-- Popup Starts -->
        <div id='blackcover2'> </div>
        <div id='popupbox2' class="ux-modl">
            <div class="ux-modl-header">
                <a href="#" onclick='popup(0)'>Close</a>
                <h6>Associated Findings  Status Change – Closed</h6>
            </div>

            <div class="ux-modl-content">
                <h3>Number of finding select for closure: <span id="closedCnt"></span> </h3>

                <div class="ux-hform">
                    <dl class="LongTxt">
                        <dt><label>Closure Reason: <span>*</span></label></dt>
                        <dt><textarea class="TextareaLong ux-height-5t" name="closedComments" id="clsdCommtId"></textarea></dt>
                        <label id="clsdCommtErrId" class="ux-msg-error-under"></label>
                    </dl>
                </div>

                <div class="ux-margin-1t ux-margin-left-11t ux-float-right">
                    <input type="button" value="Save" class="ux-btn-default-action ux-width-7t" onclick='' id="saveClosed"/> <input type="button" value="Cancel" class="ux-btn-default-action ux-width-7t" onclick='popup(0)'/>
                </div>
            </div>
        </div>
        <!-- Popup Ends -->
        
        <!-- Popup Starts -->
        <div id='blackcover3'> </div>
        <div id='popupbox3' class="ux-modl">
            <div class="ux-modl-header">
                <a href="#" onclick='popup(0)'>Close</a>
                <h6>Associated Findings  Status Change – Open</h6>
            </div>

            <div class="ux-modl-content">
                <h3>Number of finding select for Open: <span id="openCnt"></span>  </h3>

                <div class="ux-margin-1t ux-margin-left-11t ux-float-right">
                    <input type="button" value="Save" class="ux-btn-default-action ux-width-7t" onclick='popup(0)' id="saveOpen"/> <input type="button" value="Cancel" class="ux-btn-default-action ux-width-7t" onclick='popup(0)'/>
                </div>
            </div>
        </div>
        <!-- Popup Ends -->
        
        <div class="loader"></div>
        <!-- Start: Wrapper -->
        <div id="ux-wrapper"> 

            <!-- Header : Include -->
            <jsp:include page="../includes/header.jsp"/>
            <!-- Header : Include -->

            <!-- Start: Breadcrumbs -->
            <ul class="ux-brdc ux-margin-left-1t" >
                <li>Optum Security Solutions</li>
                <li> <a href="#" onclick="toRemediationHome()" title="Remediation Module">Remediation Module</a> </li>
                <li><a href="#" onclick="toRemediationPlan()" title="Remediation Plan">Remediation Plan</a></li>
                <li>Edit Remediation Plan</li> 
            </ul>
            <!-- End: Breadcrumbs -->

            <!-- content starts -->
            <div class="ux-content">
                
                <h1 class="ux-float-left ux-width-full-4t ux-margin-top-1t"> Edit Remediation Plan</h1> 
                <span class="ux-float-right ux-padding-1t">
                    
                    <c:if test="${planInfo.planStatus ne appConstants.DB_CONSTANT_FINDING_CLOSED_STATUS_VALUE}">
                    <%--<c:if test="${planInfo.notifyDate == null || empty planInfo.notifyDate}">--%>
                        <c:if test="${permissions.contains(prmnsConstants.EDIT_REMEDIATION)}">
                            <input type="button" value="Notify" class="ux-btn-disabled" id="notify" />
                        </c:if>
                    <%--</c:if>--%>
                    
                    
                    <c:choose>
                        <c:when test="${permissions.contains(prmnsConstants.EDIT_REMEDIATION)}">
                            <input type="button" value="Save" class="ux-btn-default-action " id="savePlan"/>
                        </c:when>
                        <c:otherwise>
                            <input type="button" value="Save" class="ux-btn-default-action " id="savePlanNts"/>
                        </c:otherwise>
                    </c:choose>
                    </c:if>
                            
                    <input type="button" value="Cancel" class="ux-btn-default-action " onclick="toRemediationPlan()" />
                    <input type="button" value="Back" class="ux-btn-default-action " onclick="toRemediationPlan()" />
                </span>

                <div class="ux-clear"></div>
                
                <c:if test="${addUpdateSuccessMessage != null || !empty addUpdateSuccessMessage}">
                    <div class="ux-msg-success msg"><c:out value="${addUpdateSuccessMessage}" escapeXml="true" /></div>
                </c:if>
                <c:if test="${deleteSuccessMessage != null || !empty deleteSuccessMessage}">
                    <div class="ux-msg-success msg"><c:out value="${deleteSuccessMessage}" escapeXml="true" /></div>
                </c:if>
                    
                    <div class="ux-panl ux-float-left">
                    <div class="ux-panl-header ux-panl-with-underline">
                      <h2 class="ux-float-left ux-width-full-3t-half ux-padding-halft">Action Plan Information </h2>
                    </div>
                    <div class="ux-panl-content">
                        <div class="ux-clear"></div>
                        <div class="ux-hform">
                        <!--<div class="ux-form-required ux-margin-bottom-1t">* Required</div>-->
                        <c:choose>
                            <c:when test="${permissions.contains(prmnsConstants.EDIT_REMEDIATION) && planInfo.planStatus ne appConstants.DB_CONSTANT_FINDING_CLOSED_STATUS_VALUE}">
                                <dl class="ux-width-8t">
                                    <dt><label>Plan ID:</label></dt>
                                    <dd>${planInfo.planID}</dd>
                                </dl>
                                <dl class="ux-width-28t">
                                    <dt><label>Plan Title:</label> <span>*</span></dt>
                                    <dd><input type="text" value="${planInfo.planTitle}" name="planTitle" class="ux-width-24t" id="pTitleID"/>
                                    </dd>
                                    <label id="planTitleID" class="ux-msg-error-under"></label>
                                </dl>
                                <dl class="ux-width-10t">
                                    <dt><label>Plan Status: </label></dt>
                                    <dd>${planInfo.planStatus}</dd>
                                </dl>
                                <dl class="ux-width-10t">
                                    <dt><label>Create Date: </label></dt>
                                    <dd>${planInfo.createdDate}</dd>
                                </dl>
                                <dl class="ux-width-12t">
                                    <dt><label>Notification Date:</label></dt>
                                    <dd id="nd">${planInfo.notifyDate}</dd>
                                </dl>
                                <dl class="ux-width-8t">
                                    <dt><label>Days Open:</label></dt>
                                    <dd id="do">${planInfo.daysOpen}</dd>
                                </dl>
                                <dl class="ux-width-8t">
                                    <dt><label>Close Date: </label></dt>
                                    <dd>${planInfo.planCloseDate}</dd>
                                </dl>
                                
                                <div class="ux-clear"></div>

                                <dl class="ux-width-10t">
                                    <dt><label>Plan Severity: </label></dt>
                                    <dd>${planInfo.planSeverityValue}</dd>
                                </dl>

                                <dl class="ux-width-14t">
                                    <dt><label>Adjusted Severity: <span>*</span></label></dt>
                                    <dd>
                                        <select name="planAdjSeverityCode" id="planSeverityId"  class="ux-width-10t">
                                            <option value="0">Select</option>
                                            <c:forEach var="severityDTO" items="${severityList}">
                                                 <c:choose>
                                                    <c:when test="${planInfo != null && severityDTO.id eq planInfo.planAdjSeverityCode}">
                                                        <option value="${severityDTO.id}" selected>${severityDTO.name}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${severityDTO.id}">${severityDTO.name}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </dd>
                                    <label id="planSeverityErrId" class="ux-msg-error-under"></label>
                                </dl>
                                
                                <dl class="ux-width-12t">
                                    <dt><label>Organization Type:</label></dt>
                                    <dd>

                                        <select class="ux-width-9t">
                                            <option selected="selected">Client</option>

                                        </select>
                                    </dd>
                                </dl>
                                <input type="hidden" value="${planInfo.remediationOwnerID}" id="dbOwnerId"/>
                                <dl class="ux-width-12t">
                                    <dt><label>Remediation  Owner:</label></dt>
                                    <dd>
                                        <select class="ux-width-9t" name="remediationOwnerID" id="remOwnerID">
                                            <option value="0">Select</option>
                                            <c:forEach items="${clientUsers}" var="userDTO">
                                                <c:choose>
                                                    <c:when test="${planInfo != null && userDTO.userProfileKey eq planInfo.remediationOwnerID}">
                                                        <option value="${userDTO.userProfileKey}" selected>${userDTO.firstName}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${userDTO.userProfileKey}">${userDTO.firstName}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </dd>
                                    <label id="ownerID" class="ux-msg-error-under"></label>
                                </dl>
                                <dl class="ux-width-11t">
                                    <dt><label>Group/Department:</label></dt>
                                    <dd>IT Engineering</dd>
                                </dl>
                                
                                <dl class="ux-width-9t">
                                    <dt><label>Start Date:</label></dt>
                                    <dd><input type="text" value="${planInfo.startDate}" class="ux-width-5t" id="startDateId" name="startDate"/></dd>
                                    <label id="errorStartDateID" class="ux-msg-error-under"></label>
                                </dl>
                                <dl class="ux-width-9t">
                                    <dt><label>End Date:</label></dt>
                                    <dd><input type="text" value="${planInfo.endDate}" class="ux-width-5t" id="endDateId" name="endDate"/></dd>
                                    <label id="errorEndDateID" class="ux-msg-error-under"></label>
                                </dl>
                                <div class="ux-clear"></div>
                                
                                <dl class="LongTxt">
                                    <dt><label>Action Plan Details: <span>*</span></label></dt>
                                    <dd class="ux-height-6t"><textarea class="TextareaLong ux-height-5t" name="planDetails" id="planDetailsId">${planInfo.planDetails}</textarea></dd>
                                    <label id="errorPlanDetailsId" class="ux-msg-error-under"></label>
                                </dl>
                                
                            </c:when>
                            <c:otherwise>
                                <dl class="ux-width-8t">
                                    <dt><label>Plan ID:</label></dt>
                                    <dd>${planInfo.planID}</dd>
                                </dl>
                                <dl class="ux-width-28t">
                                    <dt><label>Plan Title:</label></dt>
                                    <dd>${planInfo.planTitle}<input type="hidden" value="${planInfo.planTitle}" name="planTitle" id="pTitleID"/></dd>
                                </dl>
                                <dl class="ux-width-10t">
                                    <dt><label>Plan Status: </label></dt>
                                    <dd>${planInfo.planStatus}</dd>
                                </dl>
                                <dl class="ux-width-10t">
                                    <dt><label>Create Date: </label></dt>
                                    <dd>${planInfo.createdDate}</dd>
                                </dl>
                                <dl class="ux-width-12t">
                                    <dt><label>Notification Date:</label></dt>
                                    <dd id="nd">${planInfo.notifyDate}</dd>
                                </dl>
                                <dl class="ux-width-8t">
                                    <dt><label>Days Open:</label></dt>
                                    <dd id="do">${planInfo.daysOpen}</dd>
                                </dl>
                                <dl class="ux-width-8t">
                                    <dt><label>Close Date: </label></dt>
                                    <dd>${planInfo.planCloseDate}</dd>
                                </dl>
                                
                                <div class="ux-clear"></div>
                                
                                <dl class="ux-width-10t">
                                    <dt><label>Plan Severity: </label></dt>
                                    <dd>${planInfo.planSeverityValue}</dd>
                                </dl>

                                <dl class="ux-width-14t">
                                    <dt><label>Adjusted Severity: </label></dt>
                                    <dd>${planInfo.planAdjSeverityValue}</dd>
                                </dl>
                                
                                <dl class="ux-width-12t">
                                    <dt><label>Organization Type:</label></dt>

                                    <dd>Client</dd>
                                </dl>
                                <dl class="ux-width-12t">
                                    <dt><label>Remediation  Owner:</label></dt>
                                    <dd><select class="ux-width-9t" hidden name="remediationOwnerID" id="remOwnerID">
                                            <option value="${planInfo.remediationOwnerID}" selected>Select</option>
                                        </select>
                                        <c:forEach items="${clientUsers}" var="userDTO">
                                            <c:if test="${planInfo != null && userDTO.userProfileKey eq planInfo.remediationOwnerID}">
                                                ${userDTO.firstName}
                                            </c:if>
                                        </c:forEach>
                                    </dd>
                                </dl>
                                <dl class="ux-width-11t">
                                    <dt><label>Group/Department:</label></dt>
                                    <dd>IT Engineering</dd>
                                </dl>
                                
                                <dl class="ux-width-9t">
                                    <dt><label>Start Date:</label></dt>
                                    <dd>${planInfo.startDate}<input type="hidden" value="${planInfo.startDate}" name="startDate" /></dd>
                                </dl>
                                <dl class="ux-width-9t">
                                    <dt><label>End Date:</label></dt>
                                    <dd>${planInfo.endDate}<input type="hidden" value="${planInfo.endDate}" name="endDate" /></dd>
                                </dl>
                                <div class="ux-clear"></div>
                                <dl class="LongTxt">
                                    <dt><label>Action Plan Details:</label></dt>
                                    <dd class="ux-height-5t">${planInfo.planDetails}</dd>
                                </dl>
                                
                            </c:otherwise>
                        </c:choose>
                        
                        <c:choose>
                            <c:when test="${permissions.contains(prmnsConstants.ADD_NOTES) && planInfo.planStatus ne appConstants.DB_CONSTANT_FINDING_CLOSED_STATUS_VALUE}">
                                <dl class="ux-width-40t">
                                    <dt><label>Plan Notes: </label></dt>
                                    <dd><textarea class="TextareaLong ux-height-5t" id="planNotes"></textarea> <input type="button" class="ux-btn" value="Add" id="addNote"/>            	
                                        <select class="TextareaLong ux-height-4t ux-margin-top-min" multiple="multiple" id="addedNotes">
                                            <c:forEach var="note" items="${notesList}" >
                                                <option>${note.commentDate} - ${note.createdByUser} - ${note.planComment}</option>
                                            </c:forEach>
                                        </select>
                                        <select multiple="multiple" id="hiddenNotes" name="planNotes" hidden>

                                        </select>
                                    </dd>
                                    <label id="planNotesID" class="ux-msg-error-under"></label>
                                </dl>
                            </c:when>
                            <c:otherwise>
                                <dl class="ux-width-40t">
                                    <dt><label>Plan Notes:</label></dt>
                                    <dd>
                                        <select class="TextareaLong ux-height-4t ux-margin-top-min" multiple="multiple" id="addedNotes">
                                            <c:forEach var="note" items="${notesList}" >
                                                <option>${note.commentDate} - ${note.createdByUser} - ${note.planComment}</option>
                                            </c:forEach>
                                        </select>
                                    </dd>
                                </dl>
                            </c:otherwise>
                        </c:choose>
                            
                            <div class="ux-clear"></div>

                        </div>

                    </div>
                  </div>
                
                <div class="ux-clear"></div>
                
                <div class="ux-panl" >
                    <div class="ux-panl-header ux-panl-with-underline">
                    <h2>Association Findings</h2>
                    </div>
                    <input type="hidden" value="${remediationDTO.selectedInstances}" name="selectedInstances"/>
                    <div class="ux-panl-content">
                        <div class="ux-float-left">
                            <c:if test="${permissions.contains(prmnsConstants.EDIT_FINDING) && planInfo.planStatus ne appConstants.DB_CONSTANT_FINDING_CLOSED_STATUS_VALUE}">
                            <label>Select Status:</label>
                            <select onchange="MM_jumpMenu(this)" name="statusCode" id="statCdId">
                                <option value="0">Select </option>
                                <c:forEach var="remStatus" items="${remStatusList}">
                                    <!--<option value="${remStatus.masterLookupCode}" >${remStatus.lookUpEntityName}</option>-->
                                    <c:choose>
                                        <c:when test="${remStatus.masterLookupCode eq appConstants.DB_CONSTANT_FINDING_CLOSED_STATUS_CODE && empty planInfo.notifyDate}">
                                            
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${remStatus.masterLookupCode}" >${remStatus.lookUpEntityName}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                            </c:if>
                            <label id="findSelection" class="ux-msg-error-under">${deleteErrMessage}</label>
                        </div>
                        <div class="ux-float-right ux-padding-bottom-min">
                            <c:if test="${planInfo.notifyDate == null || empty planInfo.notifyDate}">
                                <c:if test="${permissions.contains(prmnsConstants.ADD_FINDING) && planInfo.planStatus ne appConstants.DB_CONSTANT_FINDING_CLOSED_STATUS_VALUE}">
                                <a href="#" id="addFinding" title="Add">&nbsp;
                                    <span class="SmallTxt VerticalAlignMiddle">+ Add</span>
                                </a>
                                </c:if>
                            </c:if>
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <c:if test="${permissions.contains(prmnsConstants.DELETE_FINDING) && planInfo.planStatus ne appConstants.DB_CONSTANT_FINDING_CLOSED_STATUS_VALUE}">
                            <a id="delPlanFinding" type="button" href="#" title="Remove">&nbsp;
                                <span class="SmallTxt VerticalAlignMiddle">- Remove</span>
                            </a>
                            </c:if>
                        </div>
                        <table  id="dataTable"  width="100%" class="display ux-padding-top-halft">
                            <thead>
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td><input type="text" onchange="$.fn.detectionDateFilter(this, '#dataTable')" id="detectionDateId"  class="ux-width-5t" readonly="true"/></td>
                                    <td><input type="text" onchange="$.fn.closureDateFilter(this, '#dataTable')" id="closureDateId"  class="ux-width-5t" readonly="true"/></td>
                                    <td></td>
                                    <td><span><a href="#" onclick="$.fn.SearchFilterFindingList(this, '#dataTable')" title="Refresh"><img src="images/refresh.png" /></a></span></td>
                                </tr>
                                <tr>
                                    <th class="ux-width-2t"><c:if test="${planInfo.planStatus ne appConstants.DB_CONSTANT_FINDING_CLOSED_STATUS_VALUE}"><input type="checkbox" id="headCheckbox" style="width: 26px !important;" onchange="checkAll(this)"/></c:if></th>
                                    <th><strong>Finding ID</strong></th>
                                    <th><strong>Finding Name</strong></th>
                                    <th><strong>Severity</strong></th>
                                    <th><strong>Status</strong></th>
                                    <th><strong>IP Address</strong></th>
                                    <th><strong>Application Name</strong></th>
                                    <th><strong>Detection Date</strong></th>
                                    <th><strong>Closure Date</strong></th>
                                    <th><strong>Vulnerability Category</strong></th>
                                    <th><strong>Action</strong></th>
                                </tr>
                            </thead>
                            <tbody>
                            
                                <c:forEach var="finding" items="${planFindings}" >
                                    <c:set var="status" value="false" />
                                    <c:if test="${finding.statusCode eq appConstants.DB_CONSTANT_RISK_ACCEPTED || finding.statusCode eq appConstants.DB_CONSTANT_RISK_SHARED
                                        || finding.statusCode eq appConstants.DB_CONSTANT_RISK_TRANSFER || finding.statusCode eq appConstants.DB_CONSTANT_RISK_AVOID
                                        || finding.statusCode eq appConstants.DB_CONSTANT_RISK_MITIGATE || planInfo.planStatus eq appConstants.DB_CONSTANT_FINDING_CLOSED_STATUS_VALUE}">
                                        <c:set var="status" value="true" />
                                    </c:if>
                                    <tr>
                                        <td><c:if test="${!status}"><input name="removeInstances" type="checkbox" value="${finding.instanceIdentifier}"/> </c:if> </td>
                                        <td><c:out value="${finding.instanceIdentifier}" escapeXml="true"/></td>
                                        <td><c:out value="${finding.vulnerabilityName}" escapeXml="true"/></td>
                                        <td><c:out value="${finding.cveInformationDTO.severityName}" escapeXml="true"/></td>
                                        <td><c:out value="${finding.statusName}" escapeXml="true"/></td>
                                        <td><c:out value="${finding.ipAddress}" escapeXml="true"/></td>
                                        <td><c:out value="${finding.softwareName}" escapeXml="true"/></td>
                                        <td><c:out value="${finding.createdDate}" escapeXml="true"/></td>
                                        <td><c:out value="${finding.closedDate}" escapeXml="true"/></td>
                                        <td><c:out value="${finding.rootCauseName}" escapeXml="true"/></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${permissions.contains(prmnsConstants.EDIT_FINDING) && !status}">
                                                <a href="#" onclick="editAssocFinding('${fn:trim(finding.instanceIdentifier)}')"
                                                   title="Edit" class="ux-icon-edit">Edit</a>
                                                </c:when>
                                                <c:otherwise>
                                                <a href="#" onclick="editAssocFinding('${fn:trim(finding.instanceIdentifier)}')"
                                                   title="View" class="ux-icon-view">View</a>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>

                </div>
                        
                            <input type="hidden" value="${planFindings.size()}" name="totalFindCount" id="totalFindCount"/>
                    <input type="hidden" value="${engagementDTO.encEngagementCode}" name="redengkey"/>
                    <input type="hidden" value="${planInfo.planID}" name="planID"/>
                    <input type="hidden" value="${fn:length(planFindings)}" name="findingsCount"/>
                    <input type="hidden" value="" name="instanceIdentifier" id="findingId"/>
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
