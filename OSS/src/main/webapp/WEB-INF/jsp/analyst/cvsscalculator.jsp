<%-- 
    Document   : cvsscalculator
    Created on : Jun 6, 2016, 5:01:42 PM
    Author     : hpasupuleti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="/WEB-INF/tlds/spring-form.tld" prefix="form"%>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=9; IE=10; IE=EDGE" />
        <meta name="viewport" content="width=device-width" />
        <title>IRMaaS</title>
        <!-- CSS : Include -->
        <jsp:include page="../includes/commoncss.jsp"/>
        <link rel="stylesheet" href="<c:url value="/appcss/analystvalidationworklist.css"/>" />
        <!-- CSS : Include -->
        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/appjs/analystvalidationworklist.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/appjs/cvsscalcJS.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/js/popupbox.js"/>"></script>
        <!-- JS : Include -->  

    </head>
    <!-- Start: Wrapper -->
    <div id="ux-wrapper"> 

        <!-- Header : Include -->
        <jsp:include page="../includes/header.jsp"/>
        <!-- Header : Include -->

        <!-- Start: Breadcrumbs -->
        <ul class="ux-brdc ux-margin-left-1t" >
            <li>Optum Security Solutions</li>
            <li><a href="#" onclick="headerFn('analystvalidationworklist.htm')" title="Analyst Validation Module">Analyst Validation Module</a></li>
            <li><a href="#" id="fnReviewBreadCrumb" title="Review Vulnerability List">Review Vulnerability List</a></li>
            <li>CVSS Score Calculator</li>
        </ul>
        <!-- End: Breadcrumbs -->

        <!-- Popup Starts -->
        <div id='blackcover'> </div>
        <div id='popupbox' class="ux-modl">
            <div class="ux-modl-header">
                <a href="#" onclick='popup(0)' title="Close">Close</a>
                <h6>CVSS Equations</h6>
            </div>
            <div class="ux-modl-content">
                <div align="left">
                    <h2>Base Score</h2>
                </div>
                <div align="left">
                    <h3>Base Score = (0.6*Impact Subscore +0.4*Exploitability Subscore-1.5)*f(Impact)</h3>
                    <br/>
                    <h3>Impact Subscore = 10.41 * (1 - (1 - Confidentiality Impact) * (1 - Integrity Impact) * (1 - Availability Impact))</h3>
                    <br/>
                    <h3>Exploitability Subscore = 20 * Access Complexity * Authentication * Access Vector</h3>
                    <br/>
                    <h3>f(Impact) = 0 if Impact Subscore=0; 1.176 otherwise</h3>
                    <br/>
                    <br/>
                </div>
                <div align="left">
                    <h2>Temporal Score</h2>
                </div>
                <div align="left">
                    <h3>Base Score * Exploitability * Remediation Level * Report Confidence</h3>
                    <h3></h3>
                    <br/>
                    <br/>
                </div>
                <div align="left">
                    <h2>Environmental Score</h2>
                </div>
                <div align="left">
                    <h3>(Adjusted Temporal + (10 - Adjusted Temporal) * Collateral Damage Potential) * TargetDistribution</h3>
                    <br/>
                    <h3>Adjusted Temporal = Temporal Score recomputed with the Impact sub-equation 
                        <br/>
                        replaced with the below Adjusted Impact equation.
                    </h3>
                    <br/>
                    <h3>Adjusted Impact = Min(10, 10.41 * (1 - (1 - Confidentiality Impact * Confidentiality Requirement) 
                             <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                             * (1 - Integrity Impact * Integrity Requirement) 
                             <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                             * (1 - Availability Impact  * Availability Requirement)))
                    </h3>
                    <br/>
                    <br/>
                </div>
                
                <div class="ux-margin-1t  " align="center">
                    <input type="button" title="Cancel" value="Cancel" 
                           class="ux-btn-default-action ux-width-7t" 
                           onclick='popup(0)'/> 
                </div>
            </div>
        </div>
        <!-- Popup Ends -->
        
        <!-- content starts -->
        <form:form name="calcform" action="" id="calcForm" commandName="cvssMetricObj" method="post">
            <input type="hidden" value="${sessionScope.vulnerabilityObj.clientEngagementDTO.encEngagementCode}" name="cgenk"/>
            <input type="hidden" value="${pgcnt}" name="pgcnt" id="pgcnt"/>
            <input type="hidden" value="${orgSchma}" name="orgSchma" id="orgSchma"/>
            <div class="ux-content">
                <h1>CVSS Score Calculator 
                    <span class="ux-float-right">
                        <input type="button" value="Show Equations" title="Show Equations" class="ux-btn-default-action " 
                               id="shwEquations" />
                        <input type="button" value="Back" title="Back" class="ux-btn-default-action " 
                               id="backToReview" />
                    </span>
                </h1>
                <div class="ux-clear"></div>
                <div align="center" class="ux-margin-bottom-1t">
                    <h3>CVSS v2 Vector 
                        <span style="font-weight:normal" id="basVctr">
                            <c:out value="${sessionScope.vulnerabilityObj.vectorText}" escapeXml="false"/>
                        </span>
                        <input type="hidden" name="CVSSVector" id="CVSSVector"/>
                    </h3>
                </div>
                <div class="ux-clear"></div>

                <!-- START : BASE SCORE METRICS -->
                <div class="ux-panl ux-float-left">
                    <div class="ux-panl-header ux-panl-with-underline">
                        <h2 class="ux-float-left ux-padding-top-halft" >Base Score Metrics</h2>
                        <span class="ux-float-right" style="padding-top:12px">
                                    <strong>CVSS Base Score : <span id="baseScr">0</span></strong>
                        </span> 
                    </div>
                    <div class="ux-panl-content ">
                        <!-- Start : Impact Metrics -->
                        <div class="ux-panl ux-float-left ux-margin-bottom-1t" style="width:49.3%">
                            <div class="ux-panl-header ux-panl-with-underline" >
                                <h3 class="ux-width-full-2t ux-float-left " style="padding-top:10px">
                                    Impact Metrics 
                                </h3>
                                <span class="ux-float-right" style="padding-top:12px">
                                    <strong>Impact Subscore : <span id="iSubScore">0</span></strong>
                                </span> 
                            </div>
                            <div class="ux-panl-content" id="impsc">
                                <div class="cvss-metrics-row ">
                                    <c:if test="${not empty cvssDetailMap[constants.METRIC_NAME_C]}">
                                        <fieldset class="ux-margin-min ux-float-left" style="width:28.5%">
                                            <legend>${constants.METRIC_NAME_C} (C)*</legend>
                                            <div class="cvss-radio">
                                                <c:forEach items="${cvssDetailMap[constants.METRIC_NAME_C]}" var="cvssObj">
                                                    <span>
                                                        <a href="javascript:void(0);">
                                                            <c:choose>
                                                                <c:when test="${cvssObj.defaultChecked == 1}">
                                                                    <input type="radio" id="${cvssObj.metricNameValueVectorNotation}" 
                                                                           name="${cvssObj.formulaMetricName}" 
                                                                           value="${cvssObj.metricScoreValue}" 
                                                                           title="${cvssObj.metricNameValueVectorNotation}"
                                                                           checked/>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <input type="radio" id="${cvssObj.metricNameValueVectorNotation}" 
                                                                           name="${cvssObj.formulaMetricName}" 
                                                                           value="${cvssObj.metricScoreValue}" 
                                                                           title="${cvssObj.metricNameValueVectorNotation}" />
                                                                </c:otherwise>
                                                            </c:choose>

                                                            <label for="${cvssObj.metricNameValueVectorNotation}">
                                                                ${cvssObj.metricValue}&nbsp;&nbsp;(${cvssObj.metricNameValueVectorNotation})
                                                            </label>
                                                        </a>
                                                    </span>
                                                </c:forEach>
                                            </div>
                                        </fieldset>
                                    </c:if>
                                </div>
                                <div class="cvss-metrics-row ">
                                    <c:if test="${not empty cvssDetailMap[constants.METRIC_NAME_I]}">
                                        <fieldset class=" ux-margin-min ux-float-left" style="width:28.5%">
                                            <legend>${constants.METRIC_NAME_I} (I)*</legend>
                                            <div class="cvss-radio">
                                                <c:forEach items="${cvssDetailMap[constants.METRIC_NAME_I]}" var="cvssObj">
                                                    <span>
                                                        <a href="javascript:void(0);">
                                                            <c:choose>
                                                                <c:when test="${cvssObj.defaultChecked == 1}">
                                                                    <input type="radio" id="${cvssObj.metricNameValueVectorNotation}" 
                                                                           name="${cvssObj.formulaMetricName}" 
                                                                           value="${cvssObj.metricScoreValue}" 
                                                                           title="${cvssObj.metricNameValueVectorNotation}"
                                                                           checked/>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <input type="radio" id="${cvssObj.metricNameValueVectorNotation}" 
                                                                           name="${cvssObj.formulaMetricName}" 
                                                                           value="${cvssObj.metricScoreValue}" 
                                                                           title="${cvssObj.metricNameValueVectorNotation}" />
                                                                </c:otherwise>
                                                            </c:choose>

                                                            <label for="${cvssObj.metricNameValueVectorNotation}">
                                                                ${cvssObj.metricValue}&nbsp;&nbsp;(${cvssObj.metricNameValueVectorNotation})
                                                            </label>
                                                        </a>
                                                    </span>
                                                </c:forEach>
                                            </div>
                                        </fieldset>
                                    </c:if>
                                </div>
                                <div class="cvss-metrics-row ">
                                    <c:if test="${not empty cvssDetailMap[constants.METRIC_NAME_A]}">
                                        <fieldset class=" ux-margin-min ux-float-left" style="width:28.5%">
                                            <legend>${constants.METRIC_NAME_A} (A)*</legend>
                                            <div class="cvss-radio">
                                                <c:forEach items="${cvssDetailMap[constants.METRIC_NAME_A]}" var="cvssObj">
                                                    <span>
                                                        <a href="javascript:void(0);">
                                                            <c:choose>
                                                                <c:when test="${cvssObj.defaultChecked == 1}">
                                                                    <input type="radio" id="${cvssObj.metricNameValueVectorNotation}" 
                                                                           name="${cvssObj.formulaMetricName}" 
                                                                           value="${cvssObj.metricScoreValue}" 
                                                                           title="${cvssObj.metricNameValueVectorNotation}"
                                                                           checked/>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <input type="radio" id="${cvssObj.metricNameValueVectorNotation}" 
                                                                           name="${cvssObj.formulaMetricName}" 
                                                                           value="${cvssObj.metricScoreValue}" 
                                                                           title="${cvssObj.metricNameValueVectorNotation}" />
                                                                </c:otherwise>
                                                            </c:choose>

                                                            <label for="${cvssObj.metricNameValueVectorNotation}">
                                                                ${cvssObj.metricValue}&nbsp;&nbsp;(${cvssObj.metricNameValueVectorNotation})
                                                            </label>
                                                        </a>
                                                    </span>
                                                </c:forEach>
                                            </div>
                                        </fieldset>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                        <!-- End : Impact Metrics -->

                        <!-- Start : Exploitability Metrics  -->
                        <div class="ux-panl ux-float-left  ux-margin-left-1t" style="width:49.3%">
                            <div class="ux-panl-header ux-panl-with-underline" >
                                <h3 class="ux-width-full-3t ux-float-left " style="padding-top:10px">
                                    Exploitability Metrics 
                                </h3>
                                <span class="ux-float-right" style="padding-top:12px">
                                    <strong>Exploitability Subscore : <span id="expSubScre">0</span></strong>
                                </span> 
                            </div>
                            <div class="ux-panl-content" id="expsc">
                                <div class="cvss-metrics-row ">
                                    <c:if test="${not empty cvssDetailMap[constants.METRIC_NAME_AV]}">
                                        <fieldset class="ux-margin-min ux-float-left" style="width:29%">
                                            <legend>${constants.METRIC_NAME_AV} (AV)*</legend>
                                            <div class="cvss-radio">
                                                <c:forEach items="${cvssDetailMap[constants.METRIC_NAME_AV]}" var="cvssObj">
                                                    <span>
                                                        <a href="javascript:void(0);">
                                                            <c:choose>
                                                                <c:when test="${cvssObj.defaultChecked == 1}">
                                                                    <input type="radio" id="${cvssObj.metricNameValueVectorNotation}" 
                                                                           name="${cvssObj.formulaMetricName}" 
                                                                           value="${cvssObj.metricScoreValue}" 
                                                                           title="${cvssObj.metricNameValueVectorNotation}"
                                                                           checked/>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <input type="radio" id="${cvssObj.metricNameValueVectorNotation}" 
                                                                           name="${cvssObj.formulaMetricName}" 
                                                                           value="${cvssObj.metricScoreValue}" 
                                                                           title="${cvssObj.metricNameValueVectorNotation}" />
                                                                </c:otherwise>
                                                            </c:choose>

                                                            <label for="${cvssObj.metricNameValueVectorNotation}">
                                                                ${cvssObj.metricValue}&nbsp;&nbsp;(${cvssObj.metricNameValueVectorNotation})
                                                            </label>
                                                        </a>
                                                    </span>
                                                </c:forEach>
                                            </div>
                                        </fieldset>
                                    </c:if>
                                </div>
                                <div class="cvss-metrics-row ">
                                    <c:if test="${not empty cvssDetailMap[constants.METRIC_NAME_AC]}">
                                        <fieldset class="ux-margin-min ux-float-left" style="width:25%">
                                            <legend>${constants.METRIC_NAME_AC} (AC)*</legend>
                                            <div class="cvss-radio">
                                                <c:forEach items="${cvssDetailMap[constants.METRIC_NAME_AC]}" var="cvssObj">
                                                    <span>
                                                        <a href="javascript:void(0);">
                                                            <c:choose>
                                                                <c:when test="${cvssObj.defaultChecked == 1}">
                                                                    <input type="radio" id="${cvssObj.metricNameValueVectorNotation}" 
                                                                           name="${cvssObj.formulaMetricName}" 
                                                                           value="${cvssObj.metricScoreValue}" 
                                                                           title="${cvssObj.metricNameValueVectorNotation}"
                                                                           checked/>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <input type="radio" id="${cvssObj.metricNameValueVectorNotation}" 
                                                                           name="${cvssObj.formulaMetricName}" 
                                                                           value="${cvssObj.metricScoreValue}" 
                                                                           title="${cvssObj.metricNameValueVectorNotation}" />
                                                                </c:otherwise>
                                                            </c:choose>

                                                            <label for="${cvssObj.metricNameValueVectorNotation}">
                                                                ${cvssObj.metricValue}&nbsp;&nbsp;(${cvssObj.metricNameValueVectorNotation})
                                                            </label>
                                                        </a>
                                                    </span>
                                                </c:forEach>
                                            </div>
                                        </fieldset>
                                    </c:if>
                                </div>
                                <div class="cvss-metrics-row ">
                                    <c:if test="${not empty cvssDetailMap[constants.METRIC_NAME_Au]}">
                                        <fieldset class="ux-width-full-3t ux-margin-min ux-float-left">
                                            <legend>${constants.METRIC_NAME_Au} (Au)*</legend>
                                            <div class="cvss-radio">
                                                <c:forEach items="${cvssDetailMap[constants.METRIC_NAME_Au]}" var="cvssObj">
                                                    <span>
                                                        <a href="javascript:void(0);">
                                                            <c:choose>
                                                                <c:when test="${cvssObj.defaultChecked == 1}">
                                                                    <input type="radio" id="${cvssObj.metricNameValueVectorNotation}" 
                                                                           name="${cvssObj.formulaMetricName}" 
                                                                           value="${cvssObj.metricScoreValue}" 
                                                                           title="${cvssObj.metricNameValueVectorNotation}"
                                                                           checked/>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <input type="radio" id="${cvssObj.metricNameValueVectorNotation}" 
                                                                           name="${cvssObj.formulaMetricName}" 
                                                                           value="${cvssObj.metricScoreValue}" 
                                                                           title="${cvssObj.metricNameValueVectorNotation}" />
                                                                </c:otherwise>
                                                            </c:choose>

                                                            <label for="${cvssObj.metricNameValueVectorNotation}">
                                                                ${cvssObj.metricValue}&nbsp;&nbsp;(${cvssObj.metricNameValueVectorNotation})
                                                            </label>
                                                        </a>
                                                    </span>
                                                </c:forEach>
                                            </div>
                                        </fieldset>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- END : BASE SCORE METRICS -->
                <div class="ux-clear"></div>
                <!-- START : TEMPORAL SCORE METRICS -->
                <div class="ux-panl ux-float-left ux-width-full-inclusive ux-padding-bottom-1t">
                    <div class="ux-panl-header ux-panl-with-underline">
                        <h2 class="ux-float-left ux-padding-top-halft" >Temporal Score Metrics</h2>
                        <span class="ux-float-right" style="padding-top:12px">
                            <strong>CVSS Temporal Score : <span id="iTempScr">0</span></strong>
                        </span> 
                    </div>
                    <div class="ux-panl-content" id="tempsc">
                        <div class="cvss-metrics-row ">
                            <c:if test="${not empty cvssDetailMap[constants.METRIC_NAME_E]}">
                                <fieldset class="ux-width-full-3t ux-margin-min ux-float-left">
                                    <legend>${constants.METRIC_NAME_E} (E)</legend>
                                    <div class="cvss-radio">
                                        <c:forEach items="${cvssDetailMap[constants.METRIC_NAME_E]}" var="cvssObj">
                                            <span>
                                                <a href="javascript:void(0);">
                                                    <c:choose>
                                                        <c:when test="${cvssObj.defaultChecked == 1}">
                                                            <input type="radio" id="${cvssObj.metricNameValueVectorNotation}" 
                                                                   name="${cvssObj.formulaMetricName}" 
                                                                   value="${cvssObj.metricScoreValue}" 
                                                                   title="${cvssObj.metricNameValueVectorNotation}"
                                                                   checked/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input type="radio" id="${cvssObj.metricNameValueVectorNotation}" 
                                                                   name="${cvssObj.formulaMetricName}" 
                                                                   value="${cvssObj.metricScoreValue}" 
                                                                   title="${cvssObj.metricNameValueVectorNotation}" />
                                                        </c:otherwise>
                                                    </c:choose>

                                                    <label for="${cvssObj.metricNameValueVectorNotation}">
                                                        ${cvssObj.metricValue}&nbsp;&nbsp;(${cvssObj.metricNameValueVectorNotation})
                                                    </label>
                                                </a>
                                            </span>
                                        </c:forEach>
                                    </div>
                                </fieldset>
                            </c:if>
                        </div>
                        <div class="cvss-metrics-row ">
                            <c:if test="${not empty cvssDetailMap[constants.METRIC_NAME_RL]}">
                                <fieldset class="ux-width-full-3t ux-margin-min ux-float-left">
                                    <legend>${constants.METRIC_NAME_RL} (RL)</legend>
                                    <div class="cvss-radio">
                                        <c:forEach items="${cvssDetailMap[constants.METRIC_NAME_RL]}" var="cvssObj">
                                            <span>
                                                <a href="javascript:void(0);">
                                                    <c:choose>
                                                        <c:when test="${cvssObj.defaultChecked == 1}">
                                                            <input type="radio" id="${cvssObj.metricNameValueVectorNotation}" 
                                                                   name="${cvssObj.formulaMetricName}" 
                                                                   value="${cvssObj.metricScoreValue}" 
                                                                   title="${cvssObj.metricNameValueVectorNotation}"
                                                                   checked/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input type="radio" id="${cvssObj.metricNameValueVectorNotation}" 
                                                                   name="${cvssObj.formulaMetricName}" 
                                                                   value="${cvssObj.metricScoreValue}" 
                                                                   title="${cvssObj.metricNameValueVectorNotation}" />
                                                        </c:otherwise>
                                                    </c:choose>

                                                    <label for="${cvssObj.metricNameValueVectorNotation}">
                                                        ${cvssObj.metricValue}&nbsp;&nbsp;(${cvssObj.metricNameValueVectorNotation})
                                                    </label>
                                                </a>
                                            </span>
                                        </c:forEach>
                                    </div>
                                </fieldset>
                            </c:if>
                        </div>
                        <div class="cvss-metrics-row ">
                            <c:if test="${not empty cvssDetailMap[constants.METRIC_NAME_RC]}">
                                <fieldset class="ux-width-full-3t ux-margin-min ux-float-left">
                                    <legend>${constants.METRIC_NAME_RC} (RC)</legend>
                                    <div class="cvss-radio">
                                        <c:forEach items="${cvssDetailMap[constants.METRIC_NAME_RC]}" var="cvssObj">
                                            <span>
                                                <a href="javascript:void(0);">
                                                    <c:choose>
                                                        <c:when test="${cvssObj.defaultChecked == 1}">
                                                            <input type="radio" id="${cvssObj.metricNameValueVectorNotation}" 
                                                                   name="${cvssObj.formulaMetricName}" 
                                                                   value="${cvssObj.metricScoreValue}" 
                                                                   title="${cvssObj.metricNameValueVectorNotation}"
                                                                   checked/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input type="radio" id="${cvssObj.metricNameValueVectorNotation}" 
                                                                   name="${cvssObj.formulaMetricName}" 
                                                                   value="${cvssObj.metricScoreValue}" 
                                                                   title="${cvssObj.metricNameValueVectorNotation}" />
                                                        </c:otherwise>
                                                    </c:choose>

                                                    <label for="${cvssObj.metricNameValueVectorNotation}">
                                                        ${cvssObj.metricValue}&nbsp;&nbsp;(${cvssObj.metricNameValueVectorNotation})
                                                    </label>
                                                </a>
                                            </span>
                                        </c:forEach>
                                    </div>
                                </fieldset>
                            </c:if>
                        </div>
                    </div>
                </div>
                <!-- END : TEMPORAL SCORE METRICS -->

                <!-- START : ENVIRONMENTAL SCORE METRICS -->
                <div class="ux-panl ux-float-left ux-width-full-inclusive ux-padding-bottom-1t">
                    <div class="ux-panl-header ux-panl-with-underline">
                        <h2 class="ux-float-left ux-padding-top-halft" >Environmental Score Metrics</h2>
                        <span class="ux-float-right" style="padding-top:12px">
                            <strong>CVSS Environmental Score : <span id="iEnvScr">0</span></strong>
                        </span> 
                    </div>
                    <div class="ux-panl-content">
                        
                        <div class="ux-panl ux-float-left  ux-margin-left-1t ux-padding-bottom-1t" style="width:98%" >
                            <div class="ux-panl-header ux-panl-with-underline" >
                                <h3 class="ux-width-full-4t ux-float-left " style="padding-top:10px">
                                    General Modifiers
                                </h3>
                                <span class="ux-float-right" style="padding-top:12px">
                                    <strong>Modified Impact Subscore : <span id="iModSubScore">0</span></strong>
                                </span> 
                            </div>
                            <div class="ux-panl-content" id="envsc">
                                <div class="cvss-metrics-row ">
                                    <c:if test="${not empty cvssDetailMap[constants.METRIC_NAME_TD]}">
                                        <fieldset class="ux-width-full-3t ux-margin-min ux-float-left">
                                            <legend>${constants.METRIC_NAME_TD} (TD)</legend>
                                            <div class="cvss-radio">
                                                <c:forEach items="${cvssDetailMap[constants.METRIC_NAME_TD]}" var="cvssObj">
                                                    <span>
                                                        <a href="javascript:void(0);">
                                                            <c:choose>
                                                                <c:when test="${cvssObj.defaultChecked == 1}">
                                                                    <input type="radio" id="${cvssObj.metricNameValueVectorNotation}" 
                                                                           name="${cvssObj.formulaMetricName}" 
                                                                           value="${cvssObj.metricScoreValue}" 
                                                                           title="${cvssObj.metricNameValueVectorNotation}"
                                                                           checked/>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <input type="radio" id="${cvssObj.metricNameValueVectorNotation}" 
                                                                           name="${cvssObj.formulaMetricName}" 
                                                                           value="${cvssObj.metricScoreValue}" 
                                                                           title="${cvssObj.metricNameValueVectorNotation}" />
                                                                </c:otherwise>
                                                            </c:choose>

                                                            <label for="${cvssObj.metricNameValueVectorNotation}">
                                                                ${cvssObj.metricValue}&nbsp;&nbsp;(${cvssObj.metricNameValueVectorNotation})
                                                            </label>
                                                        </a>
                                                    </span>
                                                </c:forEach>
                                            </div>
                                        </fieldset>
                                    </c:if>
                                </div>
                                <div class="cvss-metrics-row ">
                                    <c:if test="${not empty cvssDetailMap[constants.METRIC_NAME_CDP]}">
                                        <fieldset class="ux-width-full-3t ux-margin-min ux-float-left">
                                            <legend>${constants.METRIC_NAME_CDP} (CDP)</legend>
                                            <div class="cvss-radio">
                                                <c:forEach items="${cvssDetailMap[constants.METRIC_NAME_CDP]}" var="cvssObj">
                                                    <span>
                                                        <a href="javascript:void(0);">
                                                            <c:choose>
                                                                <c:when test="${cvssObj.defaultChecked == 1}">
                                                                    <input type="radio" id="${cvssObj.metricNameValueVectorNotation}" 
                                                                           name="${cvssObj.formulaMetricName}" 
                                                                           value="${cvssObj.metricScoreValue}" 
                                                                           title="${cvssObj.metricNameValueVectorNotation}"
                                                                           checked/>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <input type="radio" id="${cvssObj.metricNameValueVectorNotation}" 
                                                                           name="${cvssObj.formulaMetricName}" 
                                                                           value="${cvssObj.metricScoreValue}" 
                                                                           title="${cvssObj.metricNameValueVectorNotation}" />
                                                                </c:otherwise>
                                                            </c:choose>

                                                            <label for="${cvssObj.metricNameValueVectorNotation}">
                                                                ${cvssObj.metricValue}&nbsp;&nbsp;(${cvssObj.metricNameValueVectorNotation})
                                                            </label>
                                                        </a>
                                                    </span>
                                                </c:forEach>
                                            </div>
                                        </fieldset>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                        
                        <div class="ux-panl ux-float-left" style="width:100%">
                            <div class="ux-panl-header ux-panl-with-underline" >
                                <h3 class="ux-width-full-4t ux-float-left " style="padding-top:10px">
                                    Impact Subscore Modifiers
                                </h3>
                            </div>
                        </div>
                        
                        <div class="ux-clear"></div>
                        <div class="ux-panl-content " id="imsubmod">
                            <div class="cvss-metrics-row ">
                                <c:if test="${not empty cvssDetailMap[constants.METRIC_NAME_CR]}">
                                    <fieldset class="ux-width-full-3t ux-margin-min ux-float-left">
                                        <legend>${constants.METRIC_NAME_CR} (CR)</legend>
                                        <div class="cvss-radio">
                                            <c:forEach items="${cvssDetailMap[constants.METRIC_NAME_CR]}" var="cvssObj">
                                                <span>
                                                    <a href="javascript:void(0);">
                                                        <c:choose>
                                                            <c:when test="${cvssObj.defaultChecked == 1}">
                                                                <input type="radio" id="${cvssObj.metricNameValueVectorNotation}" 
                                                                       name="${cvssObj.formulaMetricName}" 
                                                                       value="${cvssObj.metricScoreValue}" 
                                                                       title="${cvssObj.metricNameValueVectorNotation}"
                                                                       checked/>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type="radio" id="${cvssObj.metricNameValueVectorNotation}" 
                                                                       name="${cvssObj.formulaMetricName}" 
                                                                       value="${cvssObj.metricScoreValue}" 
                                                                       title="${cvssObj.metricNameValueVectorNotation}" />
                                                            </c:otherwise>
                                                        </c:choose>

                                                        <label for="${cvssObj.metricNameValueVectorNotation}">
                                                            ${cvssObj.metricValue}&nbsp;&nbsp;(${cvssObj.metricNameValueVectorNotation})
                                                        </label>
                                                    </a>
                                                </span>
                                            </c:forEach>
                                        </div>
                                    </fieldset>
                                </c:if>
                            </div>
                            <div class="cvss-metrics-row ">
                                <c:if test="${not empty cvssDetailMap[constants.METRIC_NAME_IR]}">
                                    <fieldset class="ux-width-full-3t ux-margin-min ux-float-left">
                                        <legend>${constants.METRIC_NAME_IR} (IR)</legend>
                                        <div class="cvss-radio">
                                            <c:forEach items="${cvssDetailMap[constants.METRIC_NAME_IR]}" var="cvssObj">
                                                <span>
                                                    <a href="javascript:void(0);">
                                                        <c:choose>
                                                            <c:when test="${cvssObj.defaultChecked == 1}">
                                                                <input type="radio" id="${cvssObj.metricNameValueVectorNotation}" 
                                                                       name="${cvssObj.formulaMetricName}" 
                                                                       value="${cvssObj.metricScoreValue}" 
                                                                       title="${cvssObj.metricNameValueVectorNotation}"
                                                                       checked/>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type="radio" id="${cvssObj.metricNameValueVectorNotation}" 
                                                                       name="${cvssObj.formulaMetricName}" 
                                                                       value="${cvssObj.metricScoreValue}" 
                                                                       title="${cvssObj.metricNameValueVectorNotation}" />
                                                            </c:otherwise>
                                                        </c:choose>

                                                        <label for="${cvssObj.metricNameValueVectorNotation}">
                                                            ${cvssObj.metricValue}&nbsp;&nbsp;(${cvssObj.metricNameValueVectorNotation})
                                                        </label>
                                                    </a>
                                                </span>
                                            </c:forEach>
                                        </div>
                                    </fieldset>
                                </c:if>
                            </div>
                            <div class="cvss-metrics-row ">
                                <c:if test="${not empty cvssDetailMap[constants.METRIC_NAME_AR]}">
                                    <fieldset class="ux-width-full-3t ux-margin-min ux-float-left">
                                        <legend>${constants.METRIC_NAME_AR} (AR)</legend>
                                        <div class="cvss-radio">
                                            <c:forEach items="${cvssDetailMap[constants.METRIC_NAME_AR]}" var="cvssObj">
                                                <span>
                                                    <a href="javascript:void(0);">
                                                        <c:choose>
                                                            <c:when test="${cvssObj.defaultChecked == 1}">
                                                                <input type="radio" id="${cvssObj.metricNameValueVectorNotation}" 
                                                                       name="${cvssObj.formulaMetricName}" 
                                                                       value="${cvssObj.metricScoreValue}" 
                                                                       title="${cvssObj.metricNameValueVectorNotation}"
                                                                       checked/>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type="radio" id="${cvssObj.metricNameValueVectorNotation}" 
                                                                       name="${cvssObj.formulaMetricName}" 
                                                                       value="${cvssObj.metricScoreValue}" 
                                                                       title="${cvssObj.metricNameValueVectorNotation}" />
                                                            </c:otherwise>
                                                        </c:choose>

                                                        <label for="${cvssObj.metricNameValueVectorNotation}">
                                                            ${cvssObj.metricValue}&nbsp;&nbsp;(${cvssObj.metricNameValueVectorNotation})
                                                        </label>
                                                    </a>
                                                </span>
                                            </c:forEach>
                                        </div>
                                    </fieldset>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="ux-clear"></div>
                <div class="ux-float-right">
                    <input name="" type="button" value="Update Scores" title="Update Scores" id="updScr"  
                           class="ux-btn-default-action" />
                    <input name="" type="button" value="Clear Form" title="Clear Form"
                           class="ux-btn ux-btn-default-action" 
                           id="clrFrm"/>
                </div>

                <!-- END : ENVIRONMENTAL SCORE METRICS -->

            </div>
        </form:form>
        <!-- content ends -->

        <!-- Footer : Include -->
        <jsp:include page="../includes/footer.jsp"/>
        <!-- Footer : Include --> 
    </div>
    <!-- End Wrapper -->

</html>
