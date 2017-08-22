<!DOCTYPE HTML>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="constants" />
<un:useConstants className="com.optum.oss.constants.ReportsAndDashboardConstants" var="dbcons" />
<html lang="en-US" xmlns:ng="http://angularjs.org" >
    <head>

        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
        <meta content="text/html;charset=utf-8" http-equiv="Content-Type" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Optum Security Solutions</title>
        <jsp:include page="../dashboardcommons/commonIncludes.jsp"/> 
        <!--<script type="text/javascript" src="<c:url value="/appjs/editreports.js"/>"></script>-->
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
            <c:set var="userType" value="${userProfile.userTypeObj.lookUpEntityName}"/>

        </c:if>
        <c:if test="${sessionScope.engagementObject!=null}">
            <c:set var="engDetails" value="${sessionScope.engagementObject}"/>
            <c:set var="ePKName" value="${engDetails.securityPackageObj.securityPackageName}"/>
            <c:set var="eName" value="${engDetails.engagementName}"/>
            <c:set var="eCode" value="${engDetails.engagementCode}"/>
            <c:set var="engClientName" value="${engDetails.clientName}"/>
             <c:set var="engPublishDate" value="${engDetails.publishedDate}"/>
        </c:if>
        <c:if test="${sessionScope.from != null}">
            <c:set var="fromPage" value="${sessionScope.from}"/>
        </c:if>
          <c:if test="${sessionScope.publishFlag != null}">
            <c:set var="publishFlag" value="${sessionScope.publishFlag}"/>
        </c:if>
          <c:if test="${sessionScope.ODB != null}">
            <c:set var="ODB" value="${sessionScope.ODB}"/>
        </c:if>
        <div ng-controller="UitkCtrl" >
            <table class="tk-table-width" role="presentation">
                <tr>
                    <td class="tk-valign-top" ></td>
                    <td>
                        <div class="oui-heading"> 
                            <!--<div class="tk-margin-bottom-2t "> <uitk:breadcrumb view-model="breadcrumbViewModel"></uitk:breadcrumb></div>-->
                            <c:if test="${userType != constants.DB_USER_TYPE_CLIENT}">

                                <div class="tk-float-right tk-margin-right-min">
                                    <c:if test="${securityModel.engagementsDTO.engStatusKey != dbcons.DB_STATUS_KEY_AS_PUBLISHED && userType != constants.DB_USER_TYPE_PARTNER && publishFlag != constants.DB_CONSTANT_DASHBOARD_REMEDIATION}">
                                        <uitk-button class="" value="Publish to Client"  id="uitkPopupId2_openModalBtn" ng-click='toggleModal2()' title="Publish to Client"  enable-default="true" type="submit"></uitk-button>
                                        <uitk:message model="successMessageModel"></uitk:message>
                                        </c:if>
                                            <!--Start: Back Navigation to Finding List from Dashboard (Ref: US41394)-->
                                            <c:if test="${fromPage == constants.TO_REVIEW_VULNERABILITES_LISTPAGE}">
                                                <uitk-button class="" title="Back to Findings List" value="Back to Findings List"  onclick="dashboardHeaderFn('reviewvulnerability.htm')"  enable-default="true" type="submit"></uitk-button>
                                            </c:if>
                                            <c:if test="${fromPage == constants.TO_VIEW_VULNERABILITES_LISTPAGE}">
                                                <uitk-button class="" title="Back to Findings List" value="Back to Findings List"  onclick="dashboardHeaderFn('viewvulnerability.htm')"  enable-default="true" type="submit"></uitk-button>
                                            </c:if>
                                            <!--End: Back Navigation to Finding List from Dashboard (Ref: US41394)-->
                                                <c:if test="${fromPage == constants.TO_CLIENTREPORTSUPLOAD_LISTPAGE}">
                                            <uitk-button class="" title="Back to Client Reports Upload" value="Back to Client Reports Upload"  onclick="dashboardHeaderFn('reportsuploadworklist.htm')" enable-default="true" type="submit"></uitk-button>
                                            </c:if>
                                            <c:if test="${fromPage == null}">
                                                <uitk-button class="" title="Back" value="Back"  onclick="dashboardHeaderFn('userLandingPage.htm')" enable-default="true" type="submit"></uitk-button>
                                            </c:if>
                                </div>
                                <div class="tk-col-full ">
                                    <div class="tk-module ">
                                        <label>Client Name:</label> <c:out value="${engClientName}"/>
                                        <label class="tk-margin-left-3t">Engagement Name:</label> <c:out value="${eName}"/>
                                        <label class="tk-margin-left-3t">Engagement Code:</label> <c:out value="${eCode}"/>
                                        <span ng-if="publishedOn" id="published">
                                             <input type="hidden" ng-model="publishedOn" value="" >
                                            <label class="tk-margin-left-3t">Published On:</label> {{publishedOn}}
                                        </span>
                                        <c:if test='${engPublishDate != ""}'>
                                             <span  id="published">
                                            <label class="tk-margin-left-3t">Published On:</label> <c:out value="${engPublishDate}"/>
                                        </span>
                                        </c:if>
                                       

                                    </div>
                                </div>
                            </c:if>
                                <c:if test="${userType == constants.DB_USER_TYPE_CLIENT && ODB!=constants.CONSTANT_ONLY_DASHBOARD_PERMISSION}">
                                       <div class="tk-float-right tk-margin-right-min">
                                      <c:if test="${fromPage == null}">
                                                <uitk-button class="" title="Back" value="Back"  onclick="dashboardHeaderFn('userLandingPage.htm')" enable-default="true" type="submit"></uitk-button>
                                       </c:if>
                                       </div>
                                </c:if>
                            <!--                            <div class="tk-float-right tk-margin-right-min">
                                                            <uitk-button class="" value="Export CSV" 
                                                                         onclick="javascript:window.open('csvExport.htm?engkey=${fn:trim(securityModel.engagementsDTO.engagementKey)}')" 
                                                                         enable-default="true" type="submit"></uitk-button>
                                                        </div>-->

                            <h1 class="tk-font-bold">Security <c:out value="${ePKName}"/> - <span class="tk-font-normal tk-font-12"><c:out value="${eName}"/> (<c:out value="${eCode}"/>) </span>  </h1>
                            <div >
                                <div class="tk-grid tk-bottom-border" >
                                    <div class="tk-col-1-2">
                                        <div class="tk-module tk-padding-none tk-background-none tk-border-none">
                                            <input type="hidden" name="engagementsDTO.engagementKey" id="engkey" value="${securityModel.engagementsDTO.engagementKey}">
                                            <div class="tk-clear"></div>
                                            <div class="">
                                                <span class="tk-float-left tk-padding-halft tk-margin-left-5t"> 
                                                    <uitk:multiselect  id="id2" input-model="multiSelect2" 
                                                                       output-model="selected2"  button-label="name" item-label="name"
                                                                       max-height="16.667rem" input-width="8.5rem" flyoutwindow-width="16.667rem" max-labels="1"  helper-elements="all none"
                                                                       tick-property="ticked" default-label="Severity">
                                                    </uitk:multiselect>
                                                </span>
                                                <span class="tk-float-left tk-padding-halft"> 
                                                    <uitk:multiselect  id="id2" input-model="multiSelect3HITRUST"
                                                                       output-model="selected1" button-label="name" item-label="name"
                                                                       input-width="11.4rem" button-label="name" item-label="name"
                                                                       max-height="16.667rem" input-width="7.0rem" flyoutwindow-width="16.667rem" max-labels="1"  helper-elements="all none"
                                                                       tick-property="ticked" default-label="Vulnerability Category">
                                                    </uitk:multiselect> 
                                                </span>
                                                <span class="tk-float-left tk-padding-halft">
                                                    <uitk:multiselect  id="id2" input-model="multiSelect1"
                                                                       output-model="selected3" input-width="9.0rem" button-label="name" item-label="name"
                                                                       max-height="16.667rem" flyoutwindow-width="16.667rem" max-labels="1"  helper-elements="all none"
                                                                       tick-property="ticked" default-label="Services">
                                                    </uitk:multiselect> </span>
                                                <span class="tk-float-left tk-padding-halft">
                                                    <a href="#" class="cux-icon-filter tk-font-10" ng-click="newValue()" title="Filter"></a>
                                                    <a href="#" class="cux-icon-refresh tk-font-10" ng-click="refreshValue()" title="Refresh"></a>
                                                </span>

                                                <div id="chartDiv"></div>
                                                <div class="tk-clear"></div>
                                                <uitk:accordion id="demoAccordionId" model="accModel"></uitk:accordion>
                                                <div class="tk-clear"></div>

                                            </div>

                                        </div>
                                    </div>

                                    <div class="tk-col-1-2">
                                        <div class="tk-module tk-padding-none tk-background-none tk-border-none ">

                                            <div class="tk-clear"></div>
                                            <!-- Countbox Starts-->
                                            <div class="tk-margin-bottom-1t tk-margin-top-1t tk-float-left tk-width-full-inclusive">
                                                <div class="tk-gray-countbox">
                                                    <div class="firstbox tk-padding-halft">
                                                        <h5 align="center">Total Findings</h5>
                                                        <h3 align="center" class="count tk-graybox-heading1">
                                                            <c:out value="${summaryfindingcounts.totalCount}"/>
                                                        </h3>
                                                    </div>
                                                </div>

                                                <div class="boxelement">
                                                    <div class="critical"></div>
                                                    <p>CRITICAL</p>
                                                    <div class="ux-values count1"><c:out value="${summaryfindingcounts.criticalCount}"/></div>
                                                </div>

                                                <div class="boxelement">
                                                    <div class="high"></div>
                                                    <p>HIGH</p>
                                                    <div class="ux-values count2"><c:out value="${summaryfindingcounts.highCount}"/></div>
                                                </div>


                                                <div class="boxelement">
                                                    <div class="medium"></div>
                                                    <p>MEDIUM</p>
                                                    <div class="ux-values count3"><c:out value="${summaryfindingcounts.mediumCount}"/></div>
                                                </div>

                                                <div class="boxelement" style="margin-right:0%;">
                                                    <div class="low"></div>
                                                    <p>LOW</p>
                                                    <div class="ux-values count4"><c:out value="${summaryfindingcounts.lowCount}"/></div>
                                                </div>


                                            </div>


                                            <!--Count Box Ends -->
                                            <div class="tk-clear"></div>

                                            <div class="tk-left-border tk-padding-left-min">
                                                <!--<div align="left" class="tk-font-10">Finding Assessments </div>-->
                                                <div id="findiingsId"></div>

                                                <div class="tk-clear"></div>
                                                      <h5 class="tk-margin-left-1t">Top Vulnerability Categories</h5>
                     <div id="chart-container" align="right">Chart will load here</div>
                                            </div>

                                        </div>
                                    </div>

                                </div>
                                <div class="tk-clear"></div>
                                <h3 class="tk-float-left tk-margin-top-1t"><a id="one" name="one"></a>Findings</h3>


                                <!-- <uitk:tabs tk-model="tabsModel"></uitk:tabs>-->

                                <div class="tk-clear"></div>
                                <div>
                                    <!--<uitk:phi-confirmation id="phiConfirmation" view-model="phiVM"></uitk:phi-confirmation>-->
                                    <uitk:dynamic-table id="multiSort" model="multiSortModel"> </uitk:dynamic-table>
                                    <!--<uitk:dynamic-table model="columnShowHideModel" id="columnShowHide"> </uitk:dynamic-table>-->

                                </div>
                                <div>

                                    <uitk:dialog dialog-id='uitkPopupId1' dialog-role='dialog' header-text='HITRUST Compliance Details' show='multiSortModel.modalShown' ng-if='multiSortModel.modalShown'
                                                 confirm-dialog="false" default-width='50%' default-height="51%" trigger-element='#uitkPopupId1_openModalBtn'  >
                                        <table class="tk-dtbl tk-dtbl-reorderable-columns">
                                            <thead>
                                                <tr>
                                                    <th>Control Name</th>
                                                    <th>Description</th>
                                                    <th>Objective</th>
                                                    <th>Family Name</th>
                                            </thead>
                                            </tr>
                                            <tbody>
                                                <tr ng-model="hitrustList" ng-repeat="hitrust in hitrustList" ng-init="severityIndex = $index">
                                                    <td>{{hitrust.controlName}}</td>
                                                    <td> {{hitrust.description}}</td>
                                                    <td> {{hitrust.objectiveName}}</td>
                                                    <td>{{hitrust.familyName}}</td>
                                                </tr>
                                            </tbody>
                                        </table>

                                        <uitk:button type="button" value="Close" title="Close" enable-default="true" ng-click="Save('uitkPopupId1_closeLink')" custom-class='tk-width-6t '></uitk:button> 
                                        </table>
                                    </uitk:dialog>
                                    <uitk:dialog dialog-id='uitkPopupId2' dialog-role='dialog'
                                                 header-text='Confirmation' show='obj.modalShown2'
                                                 ng-if="obj.modalShown2"
                                                 confirm-dialog="false" tk-aria-describedby="dialogInsTextId"
                                                 default-width='45%' default-height="51%" trigger-element='#uitkPopupId2_openModalBtn'>
                                        <div align="center"> Are you sure you want to publish the engagement to Client?</div>
                                        <div align="center"> <uitk-button class="" value="Ok"  ng-click="publishEng();Save('uitkPopupId2_closeLink');showSuccessMessage($event)" enable-default="true"  type="submit" custom-class='tk-width-6t uitk-btn-close-dialog'></uitk-button>

                                            <uitk:button type="button"  value="Cancel" enable-default="true"
                                                         onclick="document.getElementById('uitkPopupId2_closeLink').click();"
                                                         custom-class='tk-width-6t uitk-btn-close-dialog'></uitk:button>
                                        </div>
                                    </uitk:dialog>
                                    <uitk:dialog dialog-id='uitkPopupId3' dialog-role='dialog' header-text='Finding' show='multiSortModel.modalShownExport' ng-if='multiSortModel.modalShownExport'
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
                                            <td style="vertical-align:top;">
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
                                </div>
                            </div>
                    </td>
                </tr>
            </table>

        </div>
    </div>
    <jsp:include page="../dashboardcommons/dashboardfooter.jsp"/>


    <script type="text/javascript" src="<c:url value="/dashjs/summary-template.js"/>"></script> 



</body>
</html>
