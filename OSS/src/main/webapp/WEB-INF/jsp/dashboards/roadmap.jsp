
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
                            
                            <h1 class="tk-font-bold">Security <c:out value="${ePKName}"/> - <span class="tk-font-normal tk-font-12"><c:out value="${eName}"/> (<c:out value="${eCode}"/>) </span>  </h1>
                            <div class="tk-clear"></div>
                            <div class="tk-margin-top-1t">
                                <h2 class="">Security Architecture Roadmap 

                                </h2>
                                <div class="tk-clear"></div>
                                <div class="tk-grid ">
                                    <div class="tk-col-3-2">
                                        <div class="tk-module tk-background-none tk-border-none tk-margin-top-1t">

                                            <div id="RoadMap" align="center">   </div>
                                            <div align="center" id="Labels1"><img src="images/Labels1.jpg"></div>
                                        </div>
                                    </div>
                                </div> 

                                <div class="tk-clear"></div>
                                <h3 class="tk-float-left tk-margin-top-1t"><a id="one" name="one"></a>Findings - <span id="catNameID">Access Control</span></h3> <span class="tk-float-left  tk-margin-left-1t" style="margin-top:18px"><a href="javascript:totalRoadMapFindingData()" title="View All">(View All)</a></span>

                                <div class="tk-clear"></div>
                                <div>

                                    <uitk:dynamic-table id="multiSort1" model="multiSortModel4"> </uitk:dynamic-table>
                                    <!--<uitk:dynamic-table model="columnShowHideModel" id="columnShowHide"> </uitk:dynamic-table>-->

                                </div>

                                <div>
                                </div>  

                                <!-- <uitk:tabs tk-model="tabsModel"></uitk:tabs>--> 

                                <!-- <uitk:tabs tk-model="tabsModel"></uitk:tabs>--> 
                                <uitk:dialog dialog-id='uitkPopupId3' dialog-role='dialog' header-text='Finding' show='multiSortModel4.modalShownExport' ng-if='multiSortModel4.modalShownExport'
                                             confirm-dialog="false" default-width='70%' default-height="71%" trigger-element=''>

                                    <table class="tk-dtbl tk-border-top-none" cellpadding="5"  >
                                        <tr>
                                            <td>
                                        <uitk:checkbox item-list="items" id="findingChk" group-name="findingChk"
                                                       tk-labelledby="checkBoxGroupLabel" on-change="showAllCheckBoxValues()">
                                        </uitk:checkbox>

                                        </td>
                                        <td>
                                        <uitk:checkbox item-list="items1" id="findingChk1" group-name="findingChk1"
                                                       tk-labelledby="checkBoxGroupLabel" on-change="showAllCheckBoxValues()">
                                        </uitk:checkbox>
                                        </td>

                                        <td>
                                        <uitk:checkbox item-list="items2" id="findingChk2" group-name="findingChk2"
                                                       tk-labelledby="checkBoxGroupLabel" on-change="showAllCheckBoxValues()">
                                        </uitk:checkbox>
                                        </td>
                                        <td>
                                        <uitk:checkbox item-list="items3" id="findingChk3" group-name="findingChk3"
                                                       tk-labelledby="checkBoxGroupLabel" on-change="showAllCheckBoxValues()">
                                        </uitk:checkbox>
                                        </td>
                                        </tr>
                                    </table>
                                    <span  class="close1"> <uitk:button value="Export to CSV" enable-default="true"
                                                                        type="button"
                                                                        uitk-btn-disabled="false" title="Export to CSV" ng-click="ExportCSV()" ></uitk:button></span>

                                    <span  class="close1">  <uitk:button value="Cancel" enable-default="true"
                                                                         type="button"
                                                                         uitk-btn-disabled="false" title="Cancel" ng-click="Cancel('uitkPopupId2_closeLink')" ></uitk:button></span>


                                </uitk:dialog>
                            </div>
                        </div></td>
                </tr>
            </table>

        </div>
 <jsp:include page="../dashboardcommons/dashboardfooter.jsp"/>


    <script type="text/javascript" src="<c:url value="/dashjs/roadMap.js"/>"></script> 

</body>
</html>
