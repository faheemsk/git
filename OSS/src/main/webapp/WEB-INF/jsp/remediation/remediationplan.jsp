<%-- 
    Document   : remediationplan
    Created on : Mar 1, 2017, 5:55:46 AM
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
        <link rel="stylesheet" href="<c:url value="/appcss/remediationplan.css"/>" />
        <!-- CSS : Include -->

        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/js/ddaccordion.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/appjs/remediationplan.js"/>"></script>
        <!-- JS : Include -->

    </head>
    <body>
        <un:useConstants className="com.optum.oss.constants.PermissionConstants" var="prmnsConstants" />
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
                <li>Remediation Plan</li>
            </ul>
            <!-- End: Breadcrumbs -->

            
            <!-- content starts -->
            <div class="ux-content">
                <h1 class="ux-float-left ux-width-full-4t ux-margin-top-1t">Remediation Plan</h1>
                <span class="ux-float-right ux-padding-1t">
                    <form name="" commandName="" method="POST" action="remediationhome.htm">
                    <input type="submit" value="Back" title="Back" class="ux-btn-default-action" />
                    </form>
                </span>
                <div class="ux-clear"></div>
                
                <c:if test="${addUpdateSuccessMessage != null || !empty addUpdateSuccessMessage}">
                    <div class="ux-msg-success msg"><c:out value="${addUpdateSuccessMessage}" escapeXml="true" /></div>
                </c:if>
                
                <!-- Engagement Information -->
                <div id="accordion" >
                    <h3>Engagement Information</h3>
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
                <!-- Engagement Information -->

                <div class="ux-clear"></div>
                
                <!-- Roadmap Vulnerabilities Category -->
                <div class="ux-panl ux-margin-top-1t">
                    <div class="ux-panl-header ux-panl-with-underline">
                        <h2 class="ux-float-left ux-width-full-3t-half ux-padding-halft">Remediation Plan List</h2>
                        <div class="ux-float-right ux-padding-halft"> 
                            
                        </div>
                        
                    </div>
                    
                <form name="remediationPlan" commandName="" method="POST">
                    <input type="hidden" value="${engagementDTO.encEngagementCode}" name="redengkey"/>
                    <input type="hidden" value="${engagementDTO.orgSchema}" name="orgSchma"/>
                    <input type="hidden" value="" name="planID" id="planKeyId"/>
                    
                    <!--Start: panel content-->
                    <div class="ux-panl-content">
                        <!--<div class="ux-float-left ux-padding-bottom-min">-->
                            <label id="" class="ux-msg-error-under">${deleteErrMessage}</label>
                        <!--</div>-->
                        <div class="ux-float-right ux-padding-bottom-min">
                            <c:if test="${permissions.contains(prmnsConstants.ADD_REMEDIATION)}">
                            <a href="#" id="addPlan" title="Add">&nbsp;
                                <span class="SmallTxt VerticalAlignMiddle">+ Add</span>
                            </a>
                            </c:if>
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <c:if test="${permissions.contains(prmnsConstants.DELETE_REMEDIATION)}">
                            <a id="removePlan" type="button" href="#" title="Remove">&nbsp;
                                <span class="SmallTxt VerticalAlignMiddle">- Remove</span>
                            </a>
                            </c:if>
                        </div>
                        <input type="hidden" value="${engagementDTO.encEngagementCode}" name="clientEngagementDTO.encEngagementCode" id="encEngagementCode"/>
                        <div class="ux-clear"></div>
                        <table  id="dataTable"  width="100%" class="display">
                            <thead>
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td>All</td>
                                    <td><input type="text" onchange="$.fn.creationDateFilter(this, '#dataTable')" id="creationDateId"  class="ux-width-5t" readonly="true"/></td>
                                    <td><input type="text" onchange="$.fn.notifyDateFilter(this, '#dataTable')" id="notifyDateId"  class="ux-width-5t" readonly="true"/></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td><span><a href="#" onclick="$.fn.SearchFilterClearRemeList(this, '#dataTable')" title="Refresh"><img src="images/refresh.png" /></a></span></td>
                                </tr>
                                <tr>
                                    <th></th>
                                    <th id="removeSortingForCheckbox"><input type="checkbox" id="headCheckbox" style="width: 26px !important;" onchange="checkAll(this)"/></th>
                                    <th><strong>Plan ID</strong></th>
                                    <th><strong>Plan Title</strong></th>
                                    <th><strong>Plan Status</strong></th>
                                    <th><strong>Creation Date</strong></th>
                                    <th><strong>Notify Date</strong></th>
                                    <th><strong>Days Open</strong></th>
                                    <th><strong>Instances</strong></th>
                                    <th><strong>Completion</strong></th>
                                    <th><strong>Action</strong></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="remediationDTO" items="${remediationList}" >
                                    <tr>
                                        <td></td>
                                        <td><input type="checkbox" value="${remediationDTO.planID}" style="width: 26px !important;" name="removeInstances"/></td>
                                        <td><c:out value="${remediationDTO.planID}" escapeXml="true"/></td>
                                        <td><c:out value="${remediationDTO.planTitle}" escapeXml="true"/></td>
                                        <td><c:out value="${remediationDTO.planStatus}" escapeXml="true"/></td>
                                        <td><c:out value="${remediationDTO.createdDate}" escapeXml="true"/></td>
                                        <td><c:out value="${remediationDTO.notifyDate}" escapeXml="true"/></td>
                                        <td><c:out value="${remediationDTO.daysOpen}" escapeXml="true"/></td>
                                        <td><c:out value="${remediationDTO.findingsCount}" escapeXml="true"/></td>
                                        <td><c:out value="${remediationDTO.completion}" escapeXml="true"/></td>
                                        <td>
                                            <a href="#" onclick="editRemediationPlan('${fn:trim(remediationDTO.planID)}')"
                                               title="Edit" class="ux-icon-edit">Edit</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </form>

            </div>
            <!-- Roadmap Vulnerabilities Category -->
                
            </div>
            <!-- content ends --> 

            <!-- Footer : Include -->
            <jsp:include page="../includes/footer.jsp"/>
            <!-- Footer : Include --> 
        </div>
        <!-- End: Wrapper --> 
    </body>
</html>
