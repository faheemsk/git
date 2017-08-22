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
        <!-- CSS : Include -->

        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/js/ddaccordion.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/appjs/riskregistrydetailsJS.js"/>"></script>
        <!-- JS : Include -->

    </head>
    <body>
        <un:useConstants className="com.optum.oss.constants.PermissionConstants" var="prmnsConstants" />
        <un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="appConstants" />
        <!-- Start: Wrapper -->
        <div id="ux-wrapper"> 


            <!-- Header : Include -->
            <jsp:include page="../includes/header.jsp"/>
            <!-- Header : Include -->


            <!-- Begin: Content -->
            <ul class="ux-brdc ux-margin-left-1t" >
                <li>Optum Security Solutions</li>

                <li><a href="#"  onclick="headerFn('registryList.htm')" title="Risk Registry Module">Risk Registry Module</a></li>
                <li><a href="#"  onclick="backToRiskRegistrySummary()" title="Risk Registry Summary">Risk Register Summary</a></li>
                <c:if test="${empty riskRegistryDetails.riskResponseCode}"><li><a href="#" class="addFinding" title="Finding Lookup">Finding Lookup</a></li></c:if>  
                <li>Risk Registry Details</li>
            </ul>

            <!--Left navigation starts here--> 
            <!--<ul class="ux-vnav ux-width-16t">
           
              
          <li class="ux-vnav-selected"><a href="Analyst-Validation-and-Remediation.html">Analyst Validation Module</a>
              
              </li>
              </ul>--> 
            <!--Left navigation ends here--> 

            <!-- content starts -->
            <div class="ux-content">
                <form name="riskRegistryForm" action="" id="" method="POST">
                    <h1 class="ux-float-left ux-width-full-4t ux-margin-top-1t"> Risk Registry Details</h1> <span class="ux-float-right ux-padding-1t">
                       <c:if test="${empty riskRegistryDetails.riskResponseCode}">
                        <c:choose>
                            <c:when test="${permissions.contains(prmnsConstants.EDIT_REGISTRY)}">
                                <input type="button" id="NotifyButtonID" title="Notify" value="Notify" class="ux-btn-default-action" onclick="registryNotifyUPdate()"/>
                                <input type="button" title="Save" id="saveRegistry" value="Save" class="ux-btn-default-action" id="save" onclick="updateRiskRegistryDetails()" />
                            </c:when>
                            <c:otherwise>
                                <input type="button" title="Save" id="saveRegistry" value="Save" class="ux-btn-default-action" id="save" onclick="riskAcceptanceDetailsUpdate()" />
                            </c:otherwise>
                        </c:choose>
                       </c:if>
                        <!--<input type="button"  title="Cancel" value="Cancel" class="ux-btn-default-action" onclick="backToRiskRegistrySummary()" />-->
                        <input type="button" title="Back" value="Back" class="ux-btn-default-action" onclick="backToRiskRegistrySummary()"/>
                    </span>
                    <div class="ux-clear"></div>
                    <c:if test="${statusMessage != null}">
                        <div class="ux-msg-success ux-margin-bottom-min" id="msg">${statusMessage}</div>
                    </c:if>

                    <div class="ux-clear"></div>
                    <div class="ux-panl ux-float-left">
                        <div class="ux-panl-header ux-panl-with-underline">
                            <h2 class="ux-float-left ux-width-full-3t-half ux-padding-halft">Risk Register </h2>

                        </div>
                        <div class="ux-panl-content">

                            <div class="ux-clear"></div>

                            <input type="hidden" name="engkey" value="<c:out value="${engagementDTO.encEngagementCode}" escapeXml="true"></c:out>"/>
                            <input type="hidden" name="redengkey" value="<c:out value="${engagementDTO.encEngagementCode}" escapeXml="true"></c:out>"/>
                            <input type="hidden" name="riskRegisterID" id="riskRegisterID" value="${riskRegistryDetails.riskRegisterID}"/>
                            <input type="hidden" name="encRiskRegistryId" id="encRiskRegistryId" value="${riskRegistryDetails.encRiskRegistryId}"/>
                            <input type="hidden" name="planID" id="" value="${riskRegistryDetails.planID}"/>
                            <input type="hidden" name="encPlanID" id="encPlanID" value="${riskRegistryDetails.encPlanID}"/>
                            <input type="hidden" name="onloadRiskRegisterID" id="onloadRiskRegisterID" value="${riskRegistryDetails.riskRegistryOwnerID}"/>
                            <input type="hidden" name="findingID" id="findingID" value=""/>
                            <c:choose>
                                <c:when test="${(permissions.contains(prmnsConstants.EDIT_REGISTRY)) && (empty riskRegistryDetails.riskResponseCode)}">
                                    <div class="ux-hform">
                                        <div class="ux-form-required ux-margin-bottom-1t">* Required</div>
                                        <dl class="ux-width-10t">
                                            <dt><label>Risk Register ID:</label></dt>
                                            <dd><c:out value="${riskRegistryDetails.riskRegisterID}" escapeXml="true"/></dd>
                                        </dl>
                                        <dl class="ux-width-10t">
                                            <dt><label>Created Date: </label></dt>
                                            <dd><c:out value="${riskRegistryDetails.createdDate}" escapeXml="true"/></dd>
                                        </dl>
                                        <dl class="ux-width-10t">
                                            <dt><label>Notification Date:</label></dt>
                                            <dd id="nd">${riskRegistryDetails.registryNotifyDate}</dd>
                                        </dl>
                                        <dl class="ux-width-10t">
                                            <dt><label>Registry Severity: </label></dt>
                                            <dd>${riskRegistryDetails.adjustedSeverity}</dd>
                                        </dl>

                                        <dl class="LongTxt">
                                            <dt><label>Compensating Control: <span>*</span></label></dt>
                                            <dd><textarea name="compensationControl" id="compensationControlID" class="TextareaLong ux-height-5t"><c:out value="${riskRegistryDetails.compensationControl}" escapeXml="true"/></textarea>
                                                <label id="errorcompensationControlID" class="error" style="color: red;float:left"></label> 
                                            </dd>
                                        </dl>
                                        <div class="ux-clear"></div>
                                        <dl class="ux-width-10t">
                                            <dt><label>Organization Type:</label></dt>
                                            <dd>

                                                <select class="ux-width-9t" name="orgType" id="orgTypeID">
                                                    <option selected="selected">Client</option>

                                                </select>
                                            </dd>
                                        </dl>
                                        <input type="hidden" value="${riskRegistryDetails.riskRegistryOwnerID}" id="dbRiskOwnerId"/>
                                        <dl  class="ux-width-10t">
                                            <dt><label>Risk Register Owner:</label></dt>
                                            <dd>
                                                <select class="ux-width-9t" name="riskRegistryOwnerID" id="riskRegistryOwnerID" >
                                                    <option value="0">Select</option>
                                                    <c:forEach items="${clientUsers}" var="userDTO">
                                                        <c:choose>
                                                            <c:when test="${riskRegistryDetails != null && userDTO.userProfileKey eq riskRegistryDetails.riskRegistryOwnerID}">
                                                                <option value="${userDTO.userProfileKey}" selected>${userDTO.firstName}</option>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <option value="${userDTO.userProfileKey}">${userDTO.firstName}</option>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                                </select>
                                                <label id="errorriskRegistryOwnerID" class="error" style="color: red;float:left"></label> 
                                            </dd>
                                        </dl>
                                        <dl class="ux-width-10t">
                                            <dt><label>Group/Department:</label>
                                            </dt>
                                            <dd><c:out value="${riskRegistryDetails.registryOwnerDept}" escapeXml="true"/></dd>
                                        </dl>

                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="ux-hform">
                                        <!--<div class="ux-form-required ux-margin-bottom-1t">* Required</div>-->
                                        <dl class="ux-width-10t">
                                            <dt><label>Risk Register ID:</label></dt>
                                            <dd><c:out value="${riskRegistryDetails.riskRegisterID}" escapeXml="true"/></dd>
                                        </dl>
                                        <dl class="ux-width-10t">
                                            <dt><label>Created Date: </label></dt>
                                            <dd><c:out value="${riskRegistryDetails.createdDate}" escapeXml="true"/></dd>
                                        </dl>
                                        <dl class="ux-width-10t">
                                            <dt><label>Notification Date:</label></dt>
                                            <dd id="nd">${riskRegistryDetails.registryNotifyDate}</dd>
                                        </dl>
                                        <dl class="ux-width-10t">
                                            <dt><label>Registry Severity: </label></dt>
                                            <dd>${riskRegistryDetails.adjustedSeverity}</dd>
                                        </dl>

                                        <dl class="LongTxt">
                                            <dt><label>Compensating Control:</label></dt>
                                            <dd><c:out value="${riskRegistryDetails.compensationControl}" escapeXml="true"/>
                                            </dd>
                                        </dl>
                                        <div class="ux-clear"></div>
                                        <dl class="ux-width-10t">
                                            <dt><label>Organization Type:</label></dt>
                                            <dd>
                                                Client
                                            </dd>
                                        </dl>

                                        <dl  class="ux-width-10t">
                                            <dt><label>Risk Register Owner:</label></dt>
                                            <dd>
                                                          <c:out value="${riskRegistryDetails.riskRegistryOwner}" escapeXml="true"/>  
                                            </dd>
                                        </dl>
                                        <dl class="ux-width-10t">
                                            <dt><label>Group/Department:</label>
                                            </dt>
                                            <dd><c:out value="${riskRegistryDetails.registryOwnerDept}" escapeXml="true"/></dd>
                                        </dl>

                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="ux-clear"></div>
                    <div class="ux-panl ux-float-left">
                        <div class="ux-panl-header ux-panl-with-underline">
                            <h2 class="ux-float-left ux-width-full-3t-half ux-padding-halft">Risk Acceptance </h2>

                        </div>
                        <div class="ux-panl-content">

                            <div class="ux-clear"></div>
                            <c:choose>
                                <c:when test="${permissions.contains(prmnsConstants.EDIT_REGISTRY)}">
                                    <div class="ux-hform">
                                        <dl class="ux-width-10t">
                                            <dt><label>Risk Response:</label></dt>
                                            <dd><c:out value="${riskRegistryDetails.riskResponse}" escapeXml="true"/></dd>
                                        </dl>


                                        <dl class="ux-width-10t">
                                            <dt><label>Accepted by:</label></dt>
                                            <dd><c:out value="${riskRegistryDetails.acceptedBy}" escapeXml="true"/></dd>
                                        </dl>
                                        <dl class="ux-width-10t">
                                            <dt><label>Date:</label></dt>
                                            <dd>${riskRegistryDetails.acceptedDate}</dd>
                                        </dl>
                                        <dl class="ux-width-10t">
                                            <dt><label>Risk Recommendation:</label></dt>
                                            <dd>${riskRegistryDetails.riskResponseJustification}</dd>
                                        </dl>

                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${empty riskRegistryDetails.riskResponseCode}">
                                            <div class="ux-hform">
                                                <div class="ux-form-required ux-margin-bottom-1t">* Required</div>
                                                <dl class="ux-width-12t">
                                                    <dt><label>Risk Response: <span>*</span></label></dt>
                                                    <dd>
                                                        <select class="ux-width-9t" name="riskResponseCode" id="riskResponseCode">
                                                            <option value="" title="Select">Select</option>
                                                            <c:forEach items="${riskResponseList}" var="riskResponseList">
                                                                <option title="${riskResponseList.masterLookupCode}" value="${riskResponseList.masterLookupCode}" <c:if test="${riskRegistryDetails.riskResponseCode==riskResponseList.masterLookupCode}">selected</c:if>>${riskResponseList.lookUpEntityName}</option>
                                                            </c:forEach>  
                                                        </select>
                                                        <label id="errorriskResponseCodeID" class="error" style="color: red;float:left"></label> 
                                                    </dd>
                                                </dl>


                                                <dl class="LongTxt">
                                                    <dt><label>Risk Response Justification: <span>*</span></label></dt>
                                                    <dd><textarea class="TextareaLong ux-height-5t" name="riskResponseJustification" id="riskResponseJustificationID">${riskRegistryDetails.riskResponseJustification}</textarea>
                                                      <label id="errorRiskResponseJustificationID" class="error" style="color: red;float:left"></label> 
                                                    </dd>
                                             
                                                </dl>

                                                <dl class="ux-width-15t">
                                                    <dt><label>Accepted By (Type Name):</label></dt>
                                                    <dd><input type="text" value="${riskRegistryDetails.acceptedBy}" name="acceptedBy" id="acceptedByID" ></input>
                                                    <label id="errorAcceptedByID" class="error" style="color: red;float:left"></label> 
                                                    </dd>
                                                </dl>

                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="ux-hform">
                                                <dl class="ux-width-12t">
                                                    <dt><label>Risk Response:</label></dt>
                                                    <dd>
                                                            <c:forEach items="${riskResponseList}" var="riskResponseList">
                                                                <c:if test="${riskRegistryDetails.riskResponseCode==riskResponseList.masterLookupCode}">${riskResponseList.lookUpEntityName}</c:if>
                                                            </c:forEach>  
                                                        <label id="errorriskResponseCodeID" class="error" style="color: red;float:left"></label> 
                                                    </dd>
                                                </dl>


                                                <dl class="LongTxt">
                                                    <dt><label>Risk Response Justification:</label></dt>
                                                    <dd>${riskRegistryDetails.riskResponseJustification}</dd>
                                                </dl>

                                                <dl class="ux-width-10t">
                                                    <dt><label>Accepted By (Type Name):</label></dt>
                                                    <dd>${riskRegistryDetails.acceptedBy}</dd>
                                                </dl>

                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                   
                                </c:otherwise>
                            </c:choose>

                        </div>
                    </div>
                    <div class="ux-clear"></div>
                    <div class="ux-panl ux-float-left">
                        <div class="ux-panl-header ux-panl-with-underline">
                            <h2 class="ux-float-left ux-width-full-3t-half ux-padding-halft">Action Plan Information </h2>

                        </div>
                        <div class="ux-panl-content">

                            <div class="ux-clear"></div>
                            <div class="ux-hform">
                                <dl class="ux-width-6t">
                                    <dt><label>Plan ID:</label></dt>
                                    <dd><c:out value="${riskRegistryDetails.planID}" escapeXml="true"/></dd>
                                </dl>
                                <dl class="ux-width-27t">
                                    <dt><label>Plan Title:</label></dt>
                                    <dd><c:out value="${riskRegistryDetails.planTitle}" escapeXml="true"/></dd>
                                </dl>

                                <dl class="ux-width-12t">
                                    <dt><label>Create Date:</label></dt>
                                    <dd><c:out value="${riskRegistryDetails.createdDate}" escapeXml="true"/></dd>
                                </dl>
                                <dl class="ux-width-12t">
                                    <dt><label>Notification Date:</label></dt>
                                    <dd><c:out value="${riskRegistryDetails.notifyDate}" escapeXml="true"/></dd>
                                </dl>
                                <div class="ux-clear"></div>
                                <dl class="ux-width-33t">
                                    <dt><label>Action Plan Details:</label></dt>
                                    <dd><c:out value="${riskRegistryDetails.planDetails}" escapeXml="true"/> </dd>
                                </dl>
                                <dl class="ux-width-12t">
                                    <dt><label>Organization Type:</label></dt>
                                    <dd>Client</dd>
                                </dl>

                                <dl  class="ux-width-12t">
                                    <dt><label>Remediation  Owner:</label></dt>
                                    <dd>${riskRegistryDetails.remediationOwner}</dd>
                                </dl>
                                <dl class="ux-width-12t">
                                    <dt><label>Group/Department:</label></dt>
                                    <dd>${riskRegistryDetails.remediationOwnerDept}</dd>
                                </dl>
                                <div class="ux-clear"></div>

                            </div>

                        </div>
                    </div>

                    <div class="ux-clear"></div>
                    <div class="ux-panl">
                        <div class="ux-panl-header ux-panl-with-underline">
                            <h2 class="ux-float-left ux-width-full-3t-half ux-padding-halft">Association Findings </h2>

                        </div>
                        <div class="ux-panl-content">
                              <c:if test="${empty riskRegistryDetails.riskResponseCode}">
                            <c:if test="${permissions.contains(prmnsConstants.ADD_FINDING_REGISTRY)}">
                                <div class="ux-float-right ux-padding-bottom-min">
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <a id="removeRegistryItems" type="button" href="#" title="Remove">&nbsp;
                                        <span class="SmallTxt VerticalAlignMiddle">- Remove</span>
                                    </a>
                                </div>
                            </c:if>
                              </c:if>
                            <div class="ux-clear"></div>
                            <a name="vul1" id="vul1"></a>
                            <label id="errorCheckboxValidation" style="color: red" class="ux-float-left"></label>
                            <table  id="dataTable"  width="100%" class="display">
                                <thead>
                                    <tr>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
<!--                                        <td></td>-->
                                        <td></td>
                                        <td></td>
                                        <td><input type="text" onchange="$.fn.detectionDateFilter(this, '#dataTable')" id="detectionDateID"  class="ux-width-5t" readonly="true"/></td>
                                        <td><input type="text" onchange="$.fn.colsureDateFilter(this, '#dataTable')" id="closureDateID"  class="ux-width-5t" readonly="true"/></td>
                                        <td></td>
                                        <td><span><a href="#" onclick="$.fn.SearchFilterFindingList(this, '#dataTable')" title="Refresh"><img src="images/refresh.png" /></a></span></td>
                                    </tr>
                                    <tr>
                                        <th class="ux-width-2t"><c:if test="${empty riskRegistryDetails.riskResponseCode}">  <c:if test="${permissions.contains(prmnsConstants.ADD_FINDING_REGISTRY)}"><input name="removeInstances" class="ux-width-1t" type="checkbox" id="headCheckbox" onchange="checkAll(this)"/></c:if></c:if></th>
                                        <th><strong>Finding ID</strong></th>
                                        <th><strong>Finding Name</strong></th>
                                        <th><strong>Severity</strong></th>
                                        <th><strong>Adjusted Severity</strong></th>
                                        <th><strong>Finding Status</strong></th>
<!--                                        <th><strong>Application Name</strong></th>-->
                                        <th><strong>Detection Date</strong></th>
                                        <th><strong>Closure Date</strong></th>
                                        <th><strong>Vulnerability Category</strong></th>
                                        <th><strong>Action</strong></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="finding" items="${findingsList}" >
                                        <tr>
                                            <td><c:if test="${empty riskRegistryDetails.riskResponseCode}">  <c:if test="${permissions.contains(prmnsConstants.ADD_FINDING_REGISTRY)}"><input name="riskItemIds" class="checkboxGroup" type="checkbox" value="${finding.instanceIdentifier}"  onchange="singleCheckbox(this)"/></c:if></c:if></td>
                                            <td><c:out value="${finding.instanceIdentifier}" escapeXml="true"/></td>
                                            <td><c:out value="${finding.vulnerabilityName}" escapeXml="true"/></td>
                                            <td><c:out value="${finding.cveInformationDTO.severityName}" escapeXml="true"/></td>
                                            <td><c:out value="${finding.adjSeverityName}" escapeXml="true"/></td>
                                            <td><c:out value="${finding.statusName}" escapeXml="true"/></td>
<!--                                            <td><c:out value="${finding.softwareName}" escapeXml="true"/></td>-->
                                            <td><c:out value="${finding.createdDate}" escapeXml="true"/></td>
                                            <td><c:out value="${finding.closedDate}" escapeXml="true"/></td>
                                            <td><c:out value="${finding.rootCauseName}" escapeXml="true"/></td>
                                            <td>
                                                <a href="javascript:void(0)" title="Risk Registry" class="ux-icon-edit" 
                                                   onclick='riskRegistryFinding("${fn:trim(finding.instanceIdentifier)}")'>Finding Details</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>

                        </div>
                    </div>
                </form>
            </div>
            <!-- content ends --> 
            <!-- Begin: Footer -->
            <jsp:include page="../includes/footer.jsp"/>
            <!-- End: Footer --> 
        </div>
        <!-- End: Wrapper --> 

    </body>
</html>
