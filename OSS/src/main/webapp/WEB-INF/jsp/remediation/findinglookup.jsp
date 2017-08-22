<%-- 
    Document   : findinglookup
    Created on : Mar 2, 2017, 3:22:01 AM
    Author     : sbhagavatula
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
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
        <link rel="stylesheet" href="<c:url value="/appcss/findinglookup.css"/>" />
        <!-- CSS : Include -->

        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/js/ddaccordion.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/appjs/findinglookup.js"/>"></script>
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
            <c:choose>
                <c:when test="${empty remediationDTO.encRiskRegistryId}">
                        <ul class="ux-brdc ux-margin-left-1t" >
                        <li>Optum Security Solutions</li>
                        <li><a href="#" onclick="headerFn('remediationworklist.htm')" title="Remediation Module">Remediation Module</a></li>
                        <li><a href="#" onclick="cancelLookup()" title="Remediation Plan">Remediation Plan</a></li>
                        <li>Finding Lookup</li> 
                </c:when>
                <c:otherwise>
                       </ul>
                    <ul class="ux-brdc ux-margin-left-1t" >
                        <li>Optum Security Solutions</li>
                        <li><a href="#"  onclick="headerFn('registryList.htm')" title="Risk Registry Module">Risk Registry Module</a></li>
                        <li><a href="#"  onclick="redirectToRegistryDetails()" title="Risk Registry Summary">Risk Register Summary</a></li>
                        <li>Finding Lookup</li>
                    </ul>
                </c:otherwise>
            </c:choose>
            
            <!-- End: Breadcrumbs -->

            
            <!-- content starts -->
            <div class="ux-content">
                
                <h1>Finding Lookup </h1>

                <div class="ux-clear"></div>
                <div class="ux-panl ux-float-left ux-width-full-2t ux-margin-top-1t ux-margin-right-1t">
                    <form name="findingLookup" method="POST" action="findinglookup.htm">
                        <input type="hidden" value="${engagementDTO.encEngagementCode}" name="redengkey"/>
                          <input type="hidden" value="${remediationDTO.encRiskRegistryId}" name="encRiskRegistryId"/>
                        <input type="hidden" value="${remediationDTO.encPlanID}" name="encPlanID"/>
                    <div class="ux-panl-header ux-panl-with-underline">
                    <!--<h2>Refine by</h2><input type="submit" value="Search" class="ux-btn-default-action"/>-->
                    <h2 class="ux-float-left ux-margin-top-1t">Refine by</h2>
                    <span class="ux-float-right ux-padding-top-halft">
                        <input type="submit" value="Refine" title="Refine" class="ux-btn-default-action"/>
                    </span>
                    </div>

                    <div class="ux-panl-content ux-padding-left-none">
                        <table width="93%" cellspacing="0"  class="refinetree ux-margin-left-2t ux-padding-min" cellpadding="5" >
                          <thead>
                            </thead>
                          <tbody>

                              <tr id="ex0-node-1" class="expanded">
                              <td width="42%" class="BoldTxt ux-colr-bg-gray-0"><label for="textfield"></label>
                                <label>Severity</label>
                              </td>
                            </tr>
                           
                            <c:forEach items="${severityList}" var="masterDTO">
                                <tr id="ex0-node-1-1" class="child-of-ex0-node-1">
                                    <td class="BoldTxt"><label for="textfield2">
                                            <input type="checkbox" readonly="readonly" value="${masterDTO.masterLookupCode}" name="selSeverity" 
                                                   <c:if test="${fn:contains(remediationDTO.selSeverity,masterDTO.masterLookupCode)}">checked</c:if> />
                                            ${masterDTO.lookUpEntityName}</label>
                                    </td>
                                </tr>
                            </c:forEach>

                           <tr id="ex0-node-2" class="expanded">
                              <td width="42%" class="BoldTxt ux-colr-bg-gray-0"><label for="textfield"></label>
                                <label>Source</label>
                              </td>
                            </tr>
                            <c:if test="${!empty remediationDTO.selSource}">
                                <c:set var="selSrc" value="${','}${remediationDTO.selSource}${','}"/>
                            </c:if>
                            <c:forEach items="${srcSystems}" var="masterDTO">
                                <c:set var="masterSrcKey" value="${','}${masterDTO.masterLookupKey}${','}"/>
                                <tr id="ex0-node-2-1" class="child-of-ex0-node-2">
                                    <td class="BoldTxt"><label for="textfield2">
                                            <input type="checkbox" readonly="readonly" value="${masterDTO.masterLookupKey}" name="selSource"
                                                   <c:if test="${fn:contains(selSrc,masterSrcKey)}">checked</c:if> />
                                            ${masterDTO.lookUpEntityName}</label>
                                    </td>
                                </tr>
                            </c:forEach>
                                
                            <tr id="ex0-node-3" class="expanded">
                                <td width="42%" class="BoldTxt ux-colr-bg-gray-0"><label for="textfield"></label>
                                    <label>Services</label>
                                </td>
                            </tr>
                            <c:if test="${!empty remediationDTO.selService}">
                                <c:set var="selSrv" value="${','}${remediationDTO.selService}${','}"/>
                            </c:if>
                            <c:forEach var="serviceDTO" items="${servicesList}">
                                <c:set var="masterSrvCode" value="${','}${serviceDTO.securityServiceCode}${','}"/>
                                <tr id="ex0-node-3-1" class="child-of-ex0-node-3">
                                    <td class="BoldTxt"><label for="textfield2">
                                            <input type="checkbox" readonly="readonly"  name="selService" value="${serviceDTO.securityServiceCode}" 
                                                   <c:if test="${fn:contains(selSrv,masterSrvCode)}">checked</c:if> />
                                            ${serviceDTO.securityServiceName}</label>
                                    </td>
                                </tr>
                            </c:forEach>
                                    
                            <tr id="ex0-node-4" class="expanded">
                                <td width="42%" class="BoldTxt ux-colr-bg-gray-0"><label for="textfield"></label>
                                    <label>OS (Category)</label>
                                </td>
                            </tr>
                            <c:if test="${!empty remediationDTO.selOsCategory}">
                                <c:set var="selOsCat" value="${','}${remediationDTO.selOsCategory}${','}"/>
                            </c:if>
                             <c:forEach var="operatingSystem" items="${osList}">
                                 <c:set var="masterOsKey" value="${','}${operatingSystem.masterLookupKey}${','}"/>
                                 <tr id="ex0-node-4-1" class="child-of-ex0-node-4">
                                    <td class="BoldTxt"><label for="textfield2">
                                            <input type="checkbox" readonly="readonly" value="${operatingSystem.masterLookupKey}" name="selOsCategory"
                                                   <c:if test="${fn:contains(selOsCat,masterOsKey)}">checked</c:if> />
                                            ${operatingSystem.lookUpEntityName}</label>
                                    </td>
                                </tr>
                            </c:forEach>

                            <tr id="ex0-node-5" class="expanded">
                                <td width="42%" class="BoldTxt ux-colr-bg-gray-0"><label for="textfield"></label>
                                    <label>Vul. Category</label>
                                </td>
                            </tr>
                            <c:if test="${!empty remediationDTO.selVulCategory}">
                                <c:set var="selVulCat" value="${','}${remediationDTO.selVulCategory}${','}"/>
                            </c:if>
                            <c:forEach var="vulDTO" items="${vulnerabilityCatList}">
                                <c:set var="masterVulCd" value="${','}${vulDTO.masterLookupCode}${','}"/>
                                <tr id="ex0-node-5-1" class="child-of-ex0-node-5">
                                    <td class="BoldTxt"><label for="textfield2">
                                            <input type="checkbox" readonly="readonly"  name="selVulCategory" value="${vulDTO.masterLookupCode}" 
                                                   <c:if test="${fn:contains(selVulCat,masterVulCd)}">checked</c:if> />
                                            ${vulDTO.lookUpEntityName}</label>
                                    </td>
                                </tr>
                            </c:forEach>
                              
                            </tbody>
                        </table>
                    </div>
                    <input type="hidden" value="${remediationDTO.selectedInstances}" name="selectedInstances"/>
                    <input type="hidden" value="${remediationDTO.planID}" name="planID"/>
                    </form>
                </div> 

                <div class="ux-panl ux-float-left ux-width-full-7-min ux-margin-top-1t" >

                    <div class="ux-panl-header ux-panl-with-underline">
                    <h2>Finding List</h2>
                    </div>
                    <form name="findingLookupFrm" action="" method="POST">
                        <c:if test="${!empty remediationDTO.selectedInstances}">
                            <c:set var="selInstances" value="${','}${remediationDTO.selectedInstances}${','}"/>
                        </c:if>
                        
                    <div class="ux-panl-content">
                        <label id="" class="ux-msg-error-under">${selectOneFinding}</label>
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
                                    <td><span><a href="#" onclick="$.fn.SearchFilterFindingList(this, '#dataTable')" title="Refresh"><img src="images/refresh.png" /></a></span></td>
                                </tr>
                                <tr>
                                    <!--<th class="ux-width-2t"><input name="check" class="ux-width-1t" type="checkbox" id="headCheckbox" onchange="checkAll(this)"/></th>-->
                                    <th class="ux-width-2t"></th>
                                    <th><strong>Finding ID</strong></th>
                                    <th><strong>Finding Name</strong></th>
                                    <th><strong>CVE</strong></th>
                                    <th><strong>IP Address</strong></th>
                                    <th><strong>URL</strong></th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="finding" items="${findingList}" >
                                    <c:set var="fndId" value="${','}${finding.instanceIdentifier}${','}"/>
                                    <c:choose>
                                        <c:when test="${fn:contains(selInstances,fndId)}">
                                            <input name="selectedInstances" type="checkbox" value="${finding.instanceIdentifier}" checked hidden/>
                                        </c:when>
                                        <c:otherwise>
                                            <tr>
                                                <td><input class="checkboxGroup"  type="checkbox" value="${finding.instanceIdentifier}" onchange="singleCheckbox(this)"/></td>
                                                <td><c:out value="${finding.instanceIdentifier}" escapeXml="true"/></td>
                                                <td><c:out value="${finding.vulnerabilityName}" escapeXml="true"/></td>
                                                <td><c:out value="${finding.cveInformationDTO.cveIdentifier}" escapeXml="true"/></td>
                                                <td><c:out value="${finding.ipAddress}" escapeXml="true"/></td>
                                                <td><c:out value="${finding.appURL}" escapeXml="true"/></td>
                                                <td></td>
                                            </tr>
                                        </c:otherwise>
                                    </c:choose>
                                        
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                        <input type="hidden" value="${remediationDTO.planID}" name="planID"/>
                        <input type="hidden" value="${engagementDTO.encEngagementCode}" name="redengkey"/>
                        <input type="hidden" value="${remediationDTO.encRiskRegistryId}" name="encRiskRegistryId"/>
                        <input type="hidden" value="${remediationDTO.encPlanID}" name="encPlanID"/>
                        <input name="selectedInstances" type="hidden" value="" id="selInstanceDatatable"/>
                    </form>
                </div>
                  <div class="ux-float-right">
                      <c:choose>
                          <c:when test="${empty remediationDTO.encRiskRegistryId}">
                               <c:choose>
                                  <c:when test="${empty remediationDTO.planID}">
                                      <input type="button" value="Add" class="ux-btn-default-action " onclick="addRemediation()" /> 
                                  </c:when>
                                  <c:otherwise>
                                      <input type="button" value="Add" class="ux-btn-default-action " onclick="addFindingToPlan()" /> 
                                  </c:otherwise>
                              </c:choose>
                              <input type="button" value="Cancel" class="ux-btn-default-action" onclick="cancelLookup()"/>
                          </c:when>
                          <c:otherwise>
                              <input type="button" value="Add" class="ux-btn-default-action " title="Add" onclick="addFindingsToPlanAndRegistry()" /> 
                              <input type="button" value="Cancel" class="ux-btn-default-action" title="Cancel" onclick="redirectToRegistryDetails()"/>
                          </c:otherwise>
                      </c:choose>
                      
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
