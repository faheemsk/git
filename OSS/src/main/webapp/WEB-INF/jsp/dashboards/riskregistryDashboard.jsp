<%-- 
    Document   : riskregistryDashboard
    Created on : Apr 7, 2017, 12:41:54 AM
    Author     : rpanuganti
--%>

<!DOCTYPE HTML>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<un:useConstants className="com.optum.oss.constants.ReportsAndDashboardConstants" var="constants" />
<un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="applicationConstants" />
<html lang="en-US" xmlns:ng="http://angularjs.org" ng-app="UitkApp"  class="ng-scope">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
        <meta content="text/html;charset=utf-8" http-equiv="Content-Type" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

         <title> Optum Security Solutions </title>
        <jsp:include page="../dashboardcommons/commonIncludes.jsp"/> 
           <style>
            .tk-border-top-none{
	border-top:0px solid #ccc!important;	
}
.active {
       pointer-events: none;
       cursor: default;
       color: #999!important;
    }
     /* For browser Compatability */
.tk-res-grid {
                max-width:100%!important;
                min-width:1349px!important;
                
}
        </style>
    </head>
<body  ng-app="UitkApp" class="ng-scope tk-res-grid">
        <jsp:include page="../dashboardcommons/dashboardheader.jsp"/> 
          <c:if test="${sessionScope.USER_SESSION != null}">
            <c:set var="userProfile" value="${sessionScope.USER_SESSION}"/>
            <c:set var="userType" value="${userProfile.userTypeObj.lookUpEntityName}"/>
        </c:if>
        <c:if test="${sessionScope.engagementObject!=null}">
            <c:set var="engDetails" value="${sessionScope.engagementObject}"/>
            <c:set var="ePKName" value="${engDetails.securityPackageObj.securityPackageName}"/>
            <c:set var="eName" value="${engDetails.engagementName}"/>
            <c:set var="eCode" value="${engDetails.engagementCode}"/>
        </c:if>
        <!--Start: Back to Finding List (Ref: US41393)-->
        <c:if test="${sessionScope.from != null}">
            <c:set var="fromPage" value="${sessionScope.from}"/>
        </c:if>
          <c:if test="${sessionScope.ODB != null}">
            <c:set var="ODB" value="${sessionScope.ODB}"/>
        </c:if>
        <div ng-controller="UitkCtrl" class="tk-wrapper"> 


            <table class="tk-table-width" role="presentation">
                <tr> 
                    <!-- Components secondary navigation -->
                    <td class="tk-valign-top" ></td>
                    <td><div class="oui-heading">
                            <h1 class="tk-font-bold">Security <c:out value="${ePKName}"/> - <span class="tk-font-normal " <c:out value="${ePKName}"/> - <span class="tk-font-normal tk-font-12"><c:out value="${eName}"/> (<c:out value="${eCode}"/>) </span>  </h1>
                            <div class="tk-clear"></div>
                            <div class="tk-margin-top-none tk-col-ful">
                                <c:if test="${userType != applicationConstants.DB_USER_TYPE_CLIENT}">
                                    <div class="tk-float-right tk-margin-right-min">
                                        <c:if test="${fromPage == applicationConstants.TO_REVIEW_VULNERABILITES_LISTPAGE}">
                                            <uitk-button class="" title="Back to Findings List" value="Back to Findings List"  onclick="dashboardHeaderFn('reviewvulnerability.htm')"  enable-default="true" type="submit"></uitk-button>
                                            </c:if>
                                            <c:if test="${fromPage == applicationConstants.TO_VIEW_VULNERABILITES_LISTPAGE}">
                                            <uitk-button class="" title="Back to Findings List" value="Back to Findings List"  onclick="dashboardHeaderFn('viewvulnerability.htm')"  enable-default="true" type="submit"></uitk-button>
                                            </c:if>
                                            <c:if test="${fromPage == applicationConstants.TO_CLIENTREPORTSUPLOAD_LISTPAGE}">
                                            <uitk-button class="" title="Back to Client Reports Upload" value="Back to Client Reports Upload"  onclick="dashboardHeaderFn('reportsuploadworklist.htm')" enable-default="true" type="submit"></uitk-button>
                                            </c:if>
                                            <c:if test="${fromPage == null}">
                                            <uitk-button class="" title="Back" value="Back"  onclick="dashboardHeaderFn('userLandingPage.htm')" enable-default="true" type="submit"></uitk-button>
                                            </c:if>
                                    </div>
                                </c:if>
                                <c:if test="${userType == applicationConstants.DB_USER_TYPE_CLIENT && ODB!=applicationConstants.CONSTANT_ONLY_DASHBOARD_PERMISSION}">
                                     <div class="tk-float-right tk-margin-right-min">
                                         <c:if test="${fromPage == null}">
                                             <uitk-button class="" title="Back" value="Back"  onclick="dashboardHeaderFn('userLandingPage.htm')" enable-default="true" type="submit"></uitk-button>
                                             </c:if>
                                     </div>
                                 </c:if>
                                <h2 class="">Risk Registry <span class="tk-float-right"></span></h2>
                                <div class="tk-clear"></div>
                                <!-- Countbox Starts-->
                                <div class="tk-margin-bottom-1t tk-margin-top-1t tk-float-left tk-countbox"> 
                                    <div class="tk-gray-countbox">
                                        <div class="firstbox tk-padding-halft">
                                            <h5 align="center" style="font-size:10px">Risk Register</h5>
                                            <h3 align="center" class="count tk-graybox-heading1"><a style="color: #7f7f7f" href="#" ng-click="getRegistryDataBySeverity('')"><c:out  value="${registryFindingCount.riskRegistryCount}"/><a></a></h3>
                                            <h5 align="center" style="font-size:9px"><c:out  value="${registryFindingCount.totalCount}"/> Instances</h5>
                                        </div>
                                    </div>
                                    <div class="boxelement">
                                        <div class="critical"></div>
                                        <p>CRITICAL</p>
                                        <div class="ux-values count1"><a style="color: #7f7f7f" href="#" ng-click="getRegistryDataBySeverity('C')"><c:out  value="${registryFindingCount.criticalCount}"/></a></div>
                                    </div>

                                    <div class="boxelement">
                                        <div class="high"></div>
                                         <p>HIGH</p>
                                        <div class="ux-values count2"><a style="color: #7f7f7f" href="#" ng-click="getRegistryDataBySeverity('H')"><c:out  value="${registryFindingCount.highCount}"/> </a> </div>
                                    </div>


                                    <div class="boxelement">
                                        <div class="medium"></div>
                                        <p>MEDIUM</p>
                                        <div class="ux-values count3"><a style="color: #7f7f7f" href="#" ng-click="getRegistryDataBySeverity('M')"><c:out  value="${registryFindingCount.mediumCount}"/></a></div>
                                    </div>

                                    <div class="boxelement" style="margin-right:0%;">
                                        <div class="low"></div>
                                        <p>LOW</p>
                                        <div class="ux-values count4"><a style="color: #7f7f7f" href="#" ng-click="getRegistryDataBySeverity('L')"><c:out  value="${registryFindingCount.lowCount}"/></a></div>
                                    </div>


                                </div>


                                <!--Count Box Ends -->
                                <div class="tk-clear"></div>
                                <div class="tk-grid " >
                                    <div class="tk-col-1-3">
                                        <div class="tk-module tk-background-none tk-border-none">
                                            <h5 class="tk-margin-left-8t tk-margin-bottom-2t">Risk Registry by Risk Response</h5>
                                            <div id="riskreg1" align="left">Chart will load here</div>

                                        </div>
                                    </div>
                                    <div class="tk-col-1-3">
                                        <div class="tk-module tk-background-none tk-border-none">
                                            <h5 class="tk-margin-left-11t tk-margin-bottom-2t">Risk Registry by Owner</h5>
                                            <div id="riskregowner" align="left">Chart will load here</div>

                                        </div>
                                    </div>
                                    <div class="tk-col-1-3">
                                        <div class="tk-module tk-background-none tk-border-none">
                                            <h5 class="tk-margin-left-8t tk-margin-bottom-2t">Risk Registry by Severity</h5>
                                            <div id="riskregsev" align="left">Chart will load here</div>

                                        </div>
                                    </div>

                                </div>
                                <div class="tk-clear"></div>
                                <div >

                                    <uitk:dynamic-table id="multiSort" model="multiSortModel5"></uitk:dynamic-table> 
                                </div>
                            </div>
                    </td>
                </tr>
            </table>
        </div>
    <jsp:include page="../dashboardcommons/dashboardfooter.jsp"/>
    
     <script type="text/javascript" src="<c:url value="/dashjs/riskregistryDashboard.js"/>"></script> 

</body>
</html>

