<%-- 
    Document   : updateroadmap
    Created on : Feb 6, 2017, 2:04:44 PM
    Author     : sbhagavatula
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
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
        <link rel="stylesheet" href="<c:url value="/appcss/reviewvulnerability.css"/>" />
        <link rel="stylesheet" href="<c:url value="/css/tablednd.css"/>" type="text/css"/>
        <!-- CSS : Include -->

        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/js/ddaccordion.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/appjs/updateroadmap.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/js/jquery.tablednd.0.7.min.js"/>"></script>
        <!-- JS : Include -->

    </head>
    <body>
        <!-- This form is used to redirect to Roadmap Page. Here we are passing encrypted engagement code. -->
        <form name="toRoadmapPage" id="toRoadmapPage" method="post" action="">
            <input type="hidden" value="${engagementDTO.encEngagementCode}" name="clientEngagementDTO.encEngagementCode" id="clientEngagementDTO.encEngagementCode"/>
            <input type="hidden" value="${engagementDTO.orgSchema}" name="orgSchema" id="orgSchema"/>
        </form>
                
        <div class="loader"></div>
        <!-- Start: Wrapper -->
        <div id="ux-wrapper"> 

            <!-- Header : Include -->
            <jsp:include page="../includes/header.jsp"/>
            <!-- Header : Include -->

            <!-- Start: Breadcrumbs -->
            <ul class="ux-brdc ux-margin-left-1t" >
                <li>Optum Security Solutions</li>
                <li><a href="#" onclick="headerFn('analystvalidationworklist.htm')" title="Analyst Validation Module">Analyst Validation Module</a></li>
                <li><a href="#" onclick="toFindingsList()" title="Review Finding List">Review Finding List</a></li>
                <li>Roadmap</li>
            </ul>
            <!-- End: Breadcrumbs -->



            <!-- content starts -->
            <div class="ux-content">
                <!-- Engagement Information -->
                <h1 class="ux-float-left ux-width-full-4t ux-margin-top-1t">Roadmap Summary</h1> 
                <form name="updateroadmap" id="updateroadmap" method="post" action="updateroadmapcat.htm">
                    <input type="hidden" value="${engagementDTO.encEngagementCode}" name="clientEngagementDTO.encEngagementCode" id="clientEngagementDTO.encEngagementCode"/>
                    <input type="hidden" value="${engagementDTO.encEngagementCode}" name="cgenk" id="cgenk"/>
                    <input type="hidden" value="${pgcnt}" name="pgcnt" id="pgcnt"/>
                    <input type="hidden" value="${engagementDTO.orgSchema}" name="orgSchma" id="orgSchma"/>
                    <input type="hidden" value="${engagementDTO.orgSchema}" name="orgSchema" id="orgSchema"/>
                    <input type="hidden" value="${roadmapDTO.rootCauseCode}" name="rootCauseCode" id="rootCauseCode"/>
                    <input type="hidden" value="${engagementDTO.clientID}" name="clientEngagementDTO.clientID" id="clientID"/>
                    <span class="ux-float-right ux-padding-1t">
                        <input type="button" value="Update" title="Update" class="ux-btn-default-action" id="updateRoadmap"/>

                        <input type="button" value="Cancel" title="Cancel" class="ux-btn-default-action " onclick="toRoadmapSummary()" />
                    </span>
                <div class="ux-clear"></div>
                <c:if test="${addSuccessMessage != null || !empty addSuccessMessage}">
                    <div class="ux-msg-success msg"><c:out value="${addSuccessMessage}" escapeXml="true" /></div>
                </c:if>
                
                <div class="ux-panl ux-float-left">
                  <div class="ux-panl-header ux-panl-with-underline" >
                    <h2> Roadmap Vulnerability Category</h2>
                  </div>
                  <div class=" ux-panl-content" >
                  <span class="ux-form-required">*Required</span>
                    <div class="ux-hform">

                      <dl>
                        <dt>
                          <label>Effective Date:</label>
                        </dt>
                        <dd><c:out value="${roadmapDTO.effectiveDate}" escapeXml="true"/></dd>
                      </dl>
                      <dl >
                        <dt>
                          <label>CSA Domain: </label>
                        </dt>
                        <dd><c:out value="${roadmapDTO.csaDomainName}" escapeXml="true"/></dd>
                      </dl>
                      <dl >
                        <dt>
                          <label>Vulnerability Category: </label>
                        </dt>
                        <dd><c:out value="${roadmapDTO.rootCauseName}" escapeXml="true"/></dd>
                      </dl>
                      <dl>
                        <dt>
                          <label>Severity: </label>
                        </dt>
                        <dd><c:out value="${roadmapDTO.cveInformationDTO.severityName}" escapeXml="true"/></dd>
                      </dl>
                      <dl>
                        <dt>
                          <label>Published Date:</label>
                        </dt>
                        <dd><c:out value="${roadmapDTO.clientEngagementDTO.publishedDate}" escapeXml="true"/></dd>
                      </dl>
                      <div class="ux-clear"></div>
                      <dl>
                        <dt>
                          <label>Cost/Effort: </label>
                        </dt>
                        <dd> 
                            <select class="ux-width-full-inclusive" name="cveInformationDTO.costEffortCode" id="csteffrtCode">     
                                <option value="">Select</option>
                                <c:forEach var="costeffortDTO" items="${costEffortList}">
                                    <option value="${costeffortDTO.masterLookupCode}" <c:if test="${roadmapDTO.cveInformationDTO.costEffortCode eq costeffortDTO.masterLookupCode}">selected</c:if> >${costeffortDTO.lookUpEntityName}</option>
                                </c:forEach>
                            </select>
                        </dd>
                      </dl>
                      <dl>
                        <dt>
                          <label>Timeline: </label>
                        </dt>
                        <dd> 
                            <select class="ux-width-full-inclusive" name="timeLineCode" id="timeLineCode">     
                                <option value="">Select</option>
                                <c:forEach var="timeLineDTO" items="${timeLineList}">
                                    <option value="${timeLineDTO.masterLookupCode}" <c:if test="${roadmapDTO.timeLineCode eq timeLineDTO.masterLookupCode}">selected</c:if> >${timeLineDTO.lookUpEntityName}</option>
                                </c:forEach>
                            </select>
                        </dd>
                      </dl>
                      <dl class="LongTxt">
                        <dt>
                          <label>Notes: <span>*</span></label>
                        </dt>
                        <dd> <textarea class="TextareaLong ux-height-5t" name="comments" id="roadmapComments">${roadmapDTO.comments}</textarea> </dd>
                        <label id="errorRoadmapComments" class="ux-msg-error-under"></label>
                      </dl>
                    </div>
                  </div>
                </div>
                </form>
                <!-- Engagement Information -->

                <div class="ux-clear"></div>
                <!-- Vulnerability List -->
                <jsp:include page="assocfindings.jsp"/>
                <!-- Vulnerability List -->
                
            </div>
            <!-- content ends --> 

            <!-- Footer : Include -->
            <jsp:include page="../includes/footer.jsp"/>
            <!-- Footer : Include --> 
        </div>
        <!-- End: Wrapper --> 
    </body>
</html>
