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
                <li><a href="#" onclick="backToRiskRegitryDet()" title="Risk Registry Details">Risk Registry Details</a></li>
                <li>Risk Registry Finding Details</li>
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
                    <h1 class="ux-float-left ux-width-full-4t ux-margin-top-1t">Risk Registry Finding Details</h1> <span class="ux-float-right ux-padding-1t">
                        <input type="button" title="Save" id="saveRegistry" value="Save" class="ux-btn-default-action" id="save" onclick="updateRiskRegistryFinding()" />
                        <input type="button" title="Back" value="Back" class="ux-btn-default-action" onclick="backToRiskRegitryDet()"/>
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
                                <input type="hidden" name="riskRegisterID" id="riskRegisterID" value="${riskRegistryDetails.riskRegisterID}"/>
                                <input type="hidden" name="encRiskRegistryId" id="encRiskRegistryId" value="${riskRegistryDetails.encRiskRegistryId}"/>
                                <input type="hidden" name="planID" id="" value="${riskRegistryDetails.planID}"/>
                                <input type="hidden" name="encPlanID" id="encPlanID" value="${riskRegistryDetails.encPlanID}"/>
                                <input type="hidden" name="onloadRiskRegisterID" id="onloadRiskRegisterID" value="${riskRegistryDetails.riskRegistryOwnerID}"/>
                                <input type="hidden" name="instanceIdentifier" id="findingID" value="${riskFinding.instanceIdentifier}"/>
                            <div class="ux-hform">
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
                                    <dt><label>Registry Severity:</label></dt>
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
                        </div>
                    </div>
                    <div class="ux-clear"></div>
                    <div class="ux-panl ux-float-left">
                        <div class="ux-panl-header ux-panl-with-underline">
                            <h2 class="ux-float-left ux-width-full-3t-half ux-padding-halft">Risk Acceptance </h2>

                        </div>
                        <div class="ux-panl-content">

                            <div class="ux-clear"></div>
                                   
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
                                    <dt><label>Accepted By:</label></dt>
                                    <dd>${riskRegistryDetails.acceptedBy}</dd>
                                </dl>

                            </div>
                                   
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
                                <!--<div class="ux-form-required ux-margin-bottom-1t">* Required</div>-->
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
                    <div class="ux-panl ux-float-left">
                        <div class="ux-panl-header ux-panl-with-underline">
                            <h2 class="ux-float-left ux-width-full-3t-half ux-padding-halft">Finding Details</h2>
                        </div>
                        <div class="ux-panl-content">
                            <div class="ux-clear"></div>
                            <div class="ux-hform">
                                <dl class="ux-width-6t">
                                    <dt><label>Finding ID:</label></dt>
                                    <dd><c:out value="${riskFinding.instanceIdentifier}" escapeXml="true"/></dd>
                                </dl>
                                <dl class="ux-width-27t">
                                    <dt><label>Finding Name:</label></dt>
                                    <dd><c:out value="${riskFinding.vulnerabilityName}" escapeXml="true"/></dd>
                                </dl>

                                <dl class="ux-width-12t">
                                    <dt><label>Severity:</label></dt>
                                    <dd><c:out value="${riskFinding.cveInformationDTO.severityName}" escapeXml="true"/></dd>
                                </dl>
                                <div class="ux-clear"></div>
                                
                                <c:choose>
                                    <c:when test="${empty riskRegistryDetails.riskResponseCode}">
                                        <dl>
                                            <dt><label>Adjusted Severity: <span>*</span></label></dt>
                                            <dd>
                                                <select name="adjSeverityCode" id="adjSeverityId">
                                                    <option value="0">Select</option>
                                                    <c:forEach var="severityDTO" items="${severityList}">
                                                        <c:choose>
                                                            <c:when test="${riskFinding != null && severityDTO.id eq riskFinding.adjSeverityCode}">
                                                                <option value="${severityDTO.id}" selected>${severityDTO.name}</option>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <option value="${severityDTO.id}">${severityDTO.name}</option>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                                </select>
                                            </dd>
                                            <label id="adjSevErrId" class="ux-msg-error-under"></label>
                                        </dl>

                                        <dl class="LongTxt">
                                            <dt><label>Compensating Control: <span>*</span></label></dt>
                                            <dd><textarea class="TextareaLong ux-height-5t" name="CompensationControl" id="compCrtlId">${riskFinding.compensatingCtrl}</textarea></dd>
                                            <label id="compCtrlErrId" class="ux-msg-error-under"></label>
                                        </dl>
                                    </c:when>
                                    <c:otherwise>
                                        <dl>
                                            <dt><label>Adjusted Severity: <span>*</span></label></dt>
                                            <dd>${riskFinding.adjSeverityName}</dd>
                                        </dl>

                                        <dl class="LongTxt">
                                            <dt><label>Compensating Control: <span>*</span></label></dt>
                                            <dd>${riskFinding.compensatingCtrl}</dd>
                                        </dl>
                                    </c:otherwise>
                                </c:choose>
                                
                            </div>
                        </div>
                    </div>
                </form>
                    <span class="ux-float-right">
                        <input type="button" title="Save" id="saveRegistry" value="Save" class="ux-btn-default-action" id="save" onclick="updateRiskRegistryFinding()" />
                        <input type="button" title="Back" value="Back" class="ux-btn-default-action" onclick="backToRiskRegitryDet()"/>
                    </span>
                    <div class="ux-clear"></div>
            </div>
            <!-- content ends --> 
            <!-- Begin: Footer -->
            <jsp:include page="../includes/footer.jsp"/>
            <!-- End: Footer --> 
        </div>
        <!-- End: Wrapper --> 

    </body>
</html>
