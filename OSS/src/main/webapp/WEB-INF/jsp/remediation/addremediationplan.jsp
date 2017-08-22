<%-- 
    Document   : addremediationplan
    Created on : Mar 6, 2017, 3:01:28 AM
    Author     : sbhagavatula
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
<%@taglib prefix="form" uri="/WEB-INF/tlds/spring-form.tld" %>
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
        <script type="text/javascript" src="<c:url value="/appjs/addremediationplan.js"/>"></script>
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
                <li><a href="#" onclick="headerFn('remediationhome.htm')" title="Remediation Module">Remediation Module</a></li>
                <li><a href="#" onclick="cancelLookup()" title="Remediation Plan">Remediation Plan</a></li>
                <li>Add Remediation Plan</li> 
            </ul>
            <!-- End: Breadcrumbs -->

            
            <!-- content starts -->
            <div class="ux-content">
                <form:form name="addRemediationFrm" action="" commandName="remediationDTO" method="POST">
                <h1 class="ux-float-left ux-width-full-4t ux-margin-top-1t"> Add Remediation Plan</h1> 
                <span class="ux-float-right ux-padding-1t">
                    <input type="button" value="Save" class="ux-btn-default-action " id="savePlan"/>
                    <input type="button" value="Cancel" class="ux-btn-default-action " onclick="cancelLookup()" />
                    <input type="button" value="Back" class="ux-btn-default-action " onclick="cancelLookup()" />
                </span>

                <div class="ux-clear"></div>
                    <div class="ux-panl ux-float-left">
                    <div class="ux-panl-header ux-panl-with-underline">
                      <h2 class="ux-float-left ux-width-full-3t-half ux-padding-halft">Action Plan Information </h2>
                    </div>
                    <div class="ux-panl-content">
                        <div class="ux-clear"></div>
                        <div class="ux-hform">
                        <!--<div class="ux-form-required ux-margin-bottom-1t">* Required</div>-->
                            <dl class="ux-width-28t">
                                <dt><label>Plan Title:</label> <span>*</span></dt>
                                <dd><input type="text" value="" name="planTitle" class="ux-width-24t" id="pTitleID"/>
                                </dd>
                                <label id="planTitleID" class="ux-msg-error-under"></label>
                            </dl>
                            <dl class="ux-width-10t">
                                <dt><label>Plan Status: </label></dt>
                                <dd>Open</dd>
                                <label id="" class="ux-msg-error-under"></label>
                            </dl>
                            <dl class="ux-width-10t">
                                <dt><label>Create Date: </label></dt>
                                <dd>${remediationDTO.createdDate}</dd>
                                <label id="" class="ux-msg-error-under"></label>
                            </dl>
                            <dl class="ux-width-12t">
                                <dt><label>Notification Date:</label></dt>
                                <dd id="nd"></dd>
                                <label id="" class="ux-msg-error-under"></label>
                            </dl>
                            <dl class="ux-width-8t">
                                <dt><label>Days Open:</label></dt>
                                <dd id="do"></dd>
                                <label id="" class="ux-msg-error-under"></label>
                            </dl>
                            <dl class="ux-width-8t">
                                <dt><label>Close Date: </label></dt>
                                <dd></dd>
                                <label id="" class="ux-msg-error-under"></label>
                            </dl>
                                
                            <div class="ux-clear"></div>
                            
                            <dl class="ux-width-10t">
                                <dt><label>Plan Severity: </label></dt>
                                <dd>${planInfo.planSeverityValue}</dd>
                            </dl>

                            <dl class="ux-width-14t">
                                <dt><label>Adjusted Severity: </label></dt>
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
                                        <option>Select</option>
                                        <option selected="selected">Client</option>
                                    </select>
                                </dd>
                                <label id="" class="ux-msg-error-under"></label>
                            </dl>

                            <dl  class="ux-width-12t">
                                <dt><label>Remediation  Owner: </label></dt>
                                <dd>

                                    <select class="ux-width-9t" name="remediationOwnerID" id="remOwnerID">
                                        <option value="0">Select</option>
                                        <c:forEach items="${clientUsers}" var="userDTO">
                                            <option value="${userDTO.userProfileKey}">${userDTO.firstName}</option>
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
                                <dd><input type="text" value="" class="ux-width-5t" id="startDateId" name="startDate"/></dd>
                                <label id="errorStartDateID" class="ux-msg-error-under"></label>
                            </dl>
                            <dl class="ux-width-9t">
                                <dt><label>End Date:</label></dt>
                                <dd><input type="text" value="" class="ux-width-5t" id="endDateId" name="endDate"/></dd>
                                <label id="errorEndDateID" class="ux-msg-error-under"></label>
                            </dl>
                            <div class="ux-clear"></div>
                            <dl class="LongTxt">
                                <dt><label>Action Plan Details: <span>*</span></label></dt>
                                <dd><textarea class="TextareaLong ux-height-5t" name="planDetails" id="planDetailsId"></textarea></dd>
                                <label id="errorPlanDetailsId" class="ux-msg-error-under"></label>
                             </dl>
                            <dl class="ux-width-40t">
                                <dt><label>Plan Notes: </label></dt>
                                <dd><textarea class="TextareaLong ux-height-5t" id="planNotes"></textarea> <input type="button" class="ux-btn" value="Add" id="addNote"/>            	
                                    <select class="TextareaLong ux-height-4t ux-margin-top-min" multiple="multiple" id="addedNotes">

                                    </select>
                                    <select multiple="multiple" id="hiddenNotes" name="planNotes" hidden>

                                    </select>
                                    <form:errors path="planNotes" cssStyle="color: red;width:190px; font-weight: bold"/>
                                </dd>
                                <label id="planNotesID" class="ux-msg-error-under"></label>
                            </dl>
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
                        <div class="ux-float-right ux-padding-bottom-min">
                            <a href="#" id="addFinding" title="Add">&nbsp;
                                <span class="SmallTxt VerticalAlignMiddle">+ Add</span>
                            </a>
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <a id="removeFinding" type="button" href="#" title="Remove">&nbsp;
                                <span class="SmallTxt VerticalAlignMiddle">- Remove</span>
                            </a>
                        </div>
                          <div class="ux-clear"></div>
                            <a name="vul1" id="vul1"></a>
                         <label id="errorCheckboxValidation" style="color: red" class="ux-float-left"></label>
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
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td><span><a href="#" onclick="$.fn.SearchFilterFindingList(this, '#dataTable')" title="Refresh"><img src="images/refresh.png" /></a></span></td>
                                </tr>
                                <tr>
                                    <th class="ux-width-2t"><input name="removeInstances" class="ux-width-1t" type="checkbox" id="headCheckbox" onchange="checkAll(this)"/></th>
                                    <th><strong>Finding ID</strong></th>
                                    <th><strong>Finding Name</strong></th>
                                    <th><strong>Severity</strong></th>
                                    <th><strong>Remediation Status</strong></th>
                                    <th><strong>IP Address</strong></th>
                                    <th><strong>Application Name</strong></th>
                                    <th><strong>Detection Date</strong></th>
                                    <th><strong>Closure Date</strong></th>
                                    <th><strong>Vulnerability Category</strong></th>
                                    <th><strong>Action</strong></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="finding" items="${findingsList}" >
                                    <tr>
                                        <td><input name="removeInstances" type="checkbox" class="checkboxGroup"  onchange="singleCheckbox(this)" value="${finding.instanceIdentifier}"/></td>
                                        <td><c:out value="${finding.instanceIdentifier}" escapeXml="true"/></td>
                                        <td><c:out value="${finding.vulnerabilityName}" escapeXml="true"/></td>
                                        <td><c:out value="${finding.cveInformationDTO.severityName}" escapeXml="true"/></td>
                                        <td><c:out value="" escapeXml="true"/></td>
                                        <td><c:out value="${finding.ipAddress}" escapeXml="true"/></td>
                                        <td><c:out value="${finding.softwareName}" escapeXml="true"/></td>
                                        <td><c:out value="${finding.createdDate}" escapeXml="true"/></td>
                                        <td><c:out value="${finding.closedDate}" escapeXml="true"/></td>
                                        <td><c:out value="${finding.rootCauseName}" escapeXml="true"/></td>
                                        <td></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>

                </div>
                    <input type="hidden" value="${engagementDTO.encEngagementCode}" name="redengkey"/>
                </form:form>
            </div>
            <!-- content ends -->

            <!-- Footer : Include -->
            <jsp:include page="../includes/footer.jsp"/>
            <!-- Footer : Include --> 
        </div>
        <!-- End: Wrapper --> 
    </body>
</html>
