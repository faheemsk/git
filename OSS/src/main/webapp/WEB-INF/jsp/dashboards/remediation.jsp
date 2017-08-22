<%-- 
    Document   : remediation
    Created on : Jul 16, 2016, 4:54:18 PM
    Author     : sathuluri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="applicationConstants" />
<!DOCTYPE html>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta content="text/html;charset=utf-8" http-equiv="Content-Type" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Optum Security Solutions</title>
<html lang="en-US" xmlns:ng="http://angularjs.org" ng-app="UitkApp"  class="ng-scope">
    <head>

        <meta charset="utf-8" />
        <title>Optum Security Solutions</title>
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
    <body ng-app="UitkApp"  class="ng-scope tk-res-grid">
        <jsp:include page="../dashboardcommons/dashboardheader.jsp"/> 

        <c:if test="${sessionScope.USER_SESSION != null}">
            <c:set var="userProfile" value="${sessionScope.USER_SESSION}"/>
            <c:set var="userOrgName" value="${userProfile.organizationObj.organizationName}"/>
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
        <!--End: Back to Finding List (Ref: US41393)-->
        <div ng-controller="UitkCtrl" > 
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
                            <!--<div class="tk-margin-bottom-2t "> <uitk:breadcrumb view-model="breadcrumbViewModel9"></uitk:breadcrumb></div>-->
                            <h1 class="tk-font-bold">Security <c:out value="${ePKName}"/> - <span class="tk-font-normal tk-font-12"><c:out value="${eName}"/> (<c:out value="${eCode}"/>) </span>  </h1>
                            <div class="tk-clear"></div>
                            <div class="tk-margin-top-none tk-col-ful">
                                <input type="hidden" id="engkey" value="${engagementKey}">

                                <h2 class="">Remediation </h2>
                                <div class="tk-float-right ">
                                    <label class="tk-padding-top-min tk-float-left">Service: &nbsp</label>
                                    <span class="tk-float-right">
                                        <uitk:multiselect  id="autocomplete_id1" input-model="selectedService"
                                                           output-model="selected1" button-label="name" item-label="name"
                                                           max-height="16.667rem" input-width="17.667rem" flyoutwindow-width="20.667rem" helper-elements="filter"
                                                           selection-mode="single" tick-property="ticked" default-label="Select"
                                                           on-item-click="filterDataTableByService()">
                                        </uitk:multiselect>
                                        <a href="#" class="cux-icon-refresh tk-font-10" ng-click="refreshServiceFilter()" title="Refresh"></a>
                                    </span>
                                </div>
                                <div class="tk-clear"></div>
                                <div id="remediationChartId" class="tk-grid  tk-margin-top-1t"></div>
                                <div class="tk-clear"></div>
                                <div >
                                    <!--<uitk:phi-confirmation id="phiConfirmation" view-model="phiVM"></uitk:phi-confirmation>-->
                                    <uitk:dynamic-table id="multiSort" model="multiSortModel3"></uitk:dynamic-table>
                                </div>
                            </div>
                        </div>
                                <uitk:dialog dialog-id='uitkPopupId3' dialog-role='dialog' header-text='Finding' show='multiSortModel3.modalShownExport' ng-if='multiSortModel3.modalShownExport'
                                                 confirm-dialog="false" default-width='70%' default-height="71%" trigger-element='#uitkPopupId2_openModalBtn'>

                                        <table class="tk-border-top-none tk-dtbl" cellpadding="5"  >
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
                                                                             uitk-btn-disabled="false" title="Cancel" ng-click="Save('uitkPopupId2_closeLink')" ></uitk:button></span>


                                    </uitk:dialog>
                    </td>
                </tr>
            </table>
        </div>
        <jsp:include page="../dashboardcommons/dashboardfooter.jsp"/>

        <script type="text/javascript" src="<c:url value="/dashjs/remediation.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/dashjs/remediationcharts.js"/>"></script>
    </body>
</html>

