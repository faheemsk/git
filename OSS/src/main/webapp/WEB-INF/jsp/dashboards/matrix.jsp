<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<!DOCTYPE HTML>
<title>Optum Security Solutions</title>
<html lang="en-US" xmlns:ng="http://angularjs.org" ng-app="UitkApp"  class="ng-scope">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta content="text/html;charset=utf-8" http-equiv="Content-Type" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <head>
        <style>
             /* For browser Compatability */
.tk-res-grid {
                max-width:100%!important;
                min-width:1349px!important;
                
}
        </style>
    </head>
    <body>
        <c:if test="${sessionScope.engagementObject!=null}">
            <c:set var="engDetails" value="${sessionScope.engagementObject}"/>
            <c:set var="ePKName" value="${engDetails.securityPackageObj.securityPackageName}"/>
            <c:set var="eName" value="${engDetails.engagementName}"/>
            <c:set var="eCode" value="${engDetails.engagementCode}"/>
        </c:if>

        <jsp:include page="../dashboardcommons/dashboardheader.jsp"/> 
        <table class="tk-table-width" role="presentation">
            <tr>
                <!-- Components secondary navigation -->
                <td class="tk-valign-top" >
                </td>
                <td>
                    <div class="oui-heading">

                        <!--                        <div class="tk-margin-bottom-2t "> <uitk:breadcrumb view-model="breadcrumbViewModel4"></uitk:breadcrumb></div> -->

                        <h1 class="tk-font-bold">Security <c:out value="${ePKName}"/> - <span class="tk-font-normal tk-font-12"><c:out value="${eName}"/> (<c:out value="${eCode}"/>) </span>  </h1>
                        <div class="tk-clear"></div>

                        <div class="">
                            <!--  <uitk:panel model="panelViewModel"></uitk:panel>-->
                            <h2 class="">Prioritization Matrix</h2>
                            <div class="tk-width-52t quadrant" style="margin:0 auto; position:relative;">
                                <div style="position:absolute; right:5px; top:-20px;"><a href="#">Show All Findings</a></div>

                                <span style="position:absolute; left:30px; top:90px;"><h5 class="tk-colr-gray-3 no-bold">High</h5></span>
                                <div class="tk-width-23t tk-panl-content tk-float-left tk-brdr-gray-0 box" style="margin-left:6em;">
                                    <h4 class="tk-colr-orange tk-margin-bottom-halft">High Impact / Low Cost</h4>
                                    <!-- <h5 class="tk-colr-orange tk-margin-top-none">Low Hanging Fruit</h5>
                                    <p>12 Findings are found</p> -->
                                    <div class="tk-width-5t tk-colr-bg-turquoise-5 fndgs fndgs1"><p>Findings</p><span>${quadrantData.q1FindingsTotal}</span></div>
                                    <ol class="tk-list-bulleted-text tk-margin-top-2t">
                                        <c:set var="count1" value="0"/>
                                        <c:if test="${count1 < 5}">
                                            <c:forEach items="${quadrantData.q1Findings}" var="q1Findings">
                                                <li>${q1Findings}</li>
                                                    <c:set var="count1" value="${count1+1}"/>
                                                </c:forEach>
                                            </c:if>
                                    </ol>
                                    <span class="tk-float-right"><a href="#">View All</a>	</span>
                                </div>

                                <span class="vertical-text" style="position:absolute; left:0; top:210px;"><h4 class="tk-colr-gray-4">Impact</h4></span>
                                <span style="position:absolute; left:30px; top:280px;"><h5 class="tk-colr-gray-3 no-bold">Low</h5></span>
                                <div class="tk-width-23t tk-panl-content tk-float-left tk-brdr-gray-0 box">
                                    <h4 class="tk-colr-orange tk-margin-bottom-halft">High Impact / High Cost</h4>
                                    <!-- <h5 class="tk-colr-orange tk-margin-top-none">Requires Enterprise Commitment</h5>
                                    <p>10 Findings are found</p> -->
                                    <div class="tk-width-5t tk-colr-bg-blue-5 fndgs fndgs2 "><p>Findings</p><span>${quadrantData.q2FindingsTotal}</span></div>
                                    <ol class="tk-list-bulleted-text tk-margin-top-2t">
                                        <c:set var="count1" value="0"/>
                                        <c:if test="${count1 < 5}">
                                            <c:forEach items="${quadrantData.q2Findings}" var="q1Findings">
                                                <li>${q1Findings}</li>
                                                    <c:set var="count1" value="${count1+1}"/>
                                                </c:forEach>
                                            </c:if>
                                    </ol>
                                    <span class="tk-float-right"><a href="#">View All</a>	</span>
                                </div>
                                <span class="tk-clear"></span>
                                <span style="position:absolute; left:220px; top:380px;"><h5 class="tk-colr-gray-3 no-bold">Low</h5></span>        
                                <div class="tk-width-23t tk-panl-content tk-float-left tk-brdr-gray-0 box" style="margin-left:6em;">
                                    <h4 class="tk-colr-orange tk-margin-bottom-halft">Low Impact / Low Cost</h4>
                                    <!-- <h5 class="tk-colr-orange tk-margin-top-none">Small Wins</h5>
                                    <p>8 Findings are found</p> -->
                                    <div class="tk-width-5t tk-colr-bg-yellow-5 fndgs fndgs3"><p>Findings</p><span>${quadrantData.q3FindingsTotal}</span></div>
                                    <ol class="tk-list-bulleted-text tk-margin-top-2t">
                                        <c:set var="count1" value="0"/>
                                        <c:if test="${count1 < 5}">
                                            <c:forEach items="${quadrantData.q3Findings}" var="q1Findings">
                                                <li>${q1Findings}</li>
                                                    <c:set var="count1" value="${count1+1}"/>
                                                </c:forEach>
                                            </c:if>
                                    </ol>
                                    <span class="tk-float-right"><a href="#">View All</a>	</span>
                                </div>
                                <span style="position:absolute; left:480px; top:380px;"><h5 class="tk-colr-gray-3 no-bold">High</h5></span> 
                                <div class="tk-width-23t tk-panl-content tk-float-left tk-brdr-gray-0 box">
                                    <h4 class="tk-colr-orange tk-margin-bottom-halft">Low Impact / High Cost</h4>
                                    <!-- <h5 class="tk-colr-orange tk-margin-top-none">More Analysis Required</h5>
                                    <p>7 Findings are found</p> -->
                                    <div class="tk-width-5t tk-colr-bg-orange-5 fndgs fndgs4"><p>Findings</p><span>${quadrantData.q4FindingsTotal}</span></div>
                                    <ol class="tk-list-bulleted-text tk-margin-top-2t">
                                        <c:set var="count1" value="0"/>
                                        <c:if test="${count1 < 5}">
                                            <c:forEach items="${quadrantData.q4Findings}" var="q1Findings">
                                                <li>${q1Findings}</li>
                                                    <c:set var="count1" value="${count1+1}"/>
                                                </c:forEach>
                                            </c:if>
                                    </ol>
                                    <span class="tk-float-right"><a href="#">View All</a>	</span>
                                </div>
                                <span style="position:absolute; left:330px; top:400px;"><h4 class="tk-colr-gray-4">Cost/Effort</h4></span>
                            </div> 
                        </div>

                        <div class="tk-clear"></div>
                        <div class="tk-clear"></div>
                        <h3 class="tk-float-left tk-margin-top-none"><a id="one" name="one"></a>Details</h3>
                        <!-- <uitk:tabs tk-model="tabsModel"></uitk:tabs>-->
                        <div class="tk-clear"></div>
                        <div>
                            <!--<uitk:phi-confirmation id="phiConfirmation" view-model="phiVM"></uitk:phi-confirmation>-->
                            <uitk:dynamic-table id="multiSort1" model="multiSortModel4"> </uitk:dynamic-table>
                            <!--<uitk:dynamic-table model="columnShowHideModel" id="columnShowHide"> </uitk:dynamic-table>-->
                        </div>
                    </div>

                    </div>
                </td>
            </tr>
        </table>
    </div>
    <jsp:include page="../dashboardcommons/dashboardfooter.jsp"/>
    <script type="text/javascript" src="<c:url value="/dashjs/matrix-triage.js"/>"></script> 
</body>
</html>
