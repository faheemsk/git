<%-- 
    Document   : reports
    Created on : Jun 28, 2016, 6:23:16 PM
    Author     : vkosuri
--%>

<!DOCTYPE HTML>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
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
             /* For browser Compatability */
.tk-res-grid {
                max-width:100%!important;
                min-width:1349px!important;
                
}
        </style>
    </head>
    <body ng-app="UitkApp"  class="ng-scope tk-res-grid">
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
        <div ng-c
        <!--End: Back to Finding List (Ref: US41393)-->
        <div ng-controller="UitkCtrl" > 
            <table class="tk-table-width" role="presentation">
                <tr>
                    <!-- Components secondary navigation -->
                    <td class="tk-valign-top" >

                    </td>


                    <td>
                        <div class="oui-heading">
                            <!--Start: Back to Finding List (Ref: US41393)-->
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
                            <!--End: Back to Finding List (Ref: US41393)-->
                            <!--<div class="tk-margin-bottom-2t "> <uitk:breadcrumb view-model="breadcrumbViewModel5"></uitk:breadcrumb></div>-->

                            <h1 class="tk-font-bold">Security <c:out value="${ePKName}"/> - <span class="tk-font-normal tk-font-12"><c:out value="${eName}"/> (<c:out value="${eCode}"/>) </span>  </h1>
                            <div class="tk-clear"></div>


                            <div class="tk-margin-top-1t">
                                <!--<uitk:panel model="panelViewModel"></uitk:panel>-->

                                <h2 class="">Reports</h2>







                                <div class="tk-clear"></div>

                                <div class="">
                                    <uitk:dynamic-table id="multiSort" model="multiSortModel1"> </uitk:dynamic-table>
                                </div>


                                <!-- <uitk:tabs tk-model="tabsModel"></uitk:tabs>-->



                            </div>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <jsp:include page="../dashboardcommons/dashboardfooter.jsp"/>


    <!--<script type="text/javascript" src="js/test-data.js"></script>-->
      <script type="text/javascript" src="<c:url value="/js/reports-triage.js"/>"></script> 
      <script type="text/javascript" src="<c:url value="/js/uitk-dynamic-table.js"/>"></script> 


</body>
</html>

