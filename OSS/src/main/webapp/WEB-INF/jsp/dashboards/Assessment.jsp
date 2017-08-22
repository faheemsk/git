
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
                            <!--                            <div class="tk-margin-bottom-2t "> <uitk:breadcrumb view-model="breadcrumbViewModel1"></uitk:breadcrumb></div>-->

                            <h1 class="tk-font-bold">Security <c:out value="${ePKName}"/> - <span class="tk-font-normal tk-font-12"><c:out value="${eName}"/> (<c:out value="${eCode}"/>) </span>  </h1>
                            <div class="tk-clear"></div>


                            <div class="tk-margin-top-1t">
                                <!--<uitk:panel model="panelViewModel"></uitk:panel>-->

                                <h2 class="">${serviceName} </h2>
                                <input type="hidden" id="serviceName" value="${serviceName}">
                                <div class="tk-margin-bottom-1t tk-float-left tk-countbox">
                                    <!--                                    <input type="hidden" model="controlName" id="controlName">
                                                                                <input type="hidden" model="description" id="description">
                                                                                <input type="hidden" model="objectiveName" id="objectiveName">
                                                                                <input type="hidden" model="familyName" id="familyName">-->
                                    <div class="tk-gray-countbox">
                                        <div class="firstbox tk-padding-halft">
                                            <h5>Findings</h5>
                                            <h3  class="count tk-graybox-heading">${assessmentCount.totalCount}</h3>

                                        </div>
                                    </div>

                                    <div class="boxelement">
                                        <div class="critical"></div>
                                        <p>CRITICAL</p>
                                        <div class="ux-values count1">${assessmentCount.criticalCount}</div>
                                    </div>

                                    <div class="boxelement">
                                        <div class="high"></div>
                                        <p>HIGH</p>
                                        <div class="ux-values count2">${assessmentCount.highCount}</div>
                                    </div>


                                    <div class="boxelement">
                                        <div class="medium"></div>
                                        <p>MEDIUM</p>
                                        <div class="ux-values count3">${assessmentCount.mediumCount}</div>
                                    </div>

                                    <div class="boxelement" style="margin-right:0%;">
                                        <div class="low"></div>
                                        <p>LOW</p>
                                        <div class="ux-values count4">${assessmentCount.lowCount}</div>
                                    </div>


                                </div>
                                            <div class="">
                                <c:if test="${service == constants.SERVICE_CODE_WIRELESS_RISK_ASSESSMENT}">
                                    <span class="tk-float-left ">
                                        <uitk:multiselect  id="id2" input-model="multiSelect4"
                                                           output-model="selected2" button-label="name" item-label="name"
                                                           max-height="16.667rem" flyoutwindow-width="16.667rem" max-labels="1"  helper-elements="all none"
                                                           tick-property="ticked" default-label="Select SSID">
                                        </uitk:multiselect>
                                    </span>
                                       <span class="tk-float-left tk-margin-left-1t">
                                        <a href="#" class="cux-icon-filter tk-font-10" ng-click="ssidFilter()" title="Filter"></a>
                                        <!--<a href="#" class="cux-icon-refresh tk-font-10" ng-click="refreshValue()" title="Refresh"></a>-->
                                    </span>
                                </c:if>
                                <c:if test="${service == constants.SERVICE_CODE_APPLICATION_VULNERABILITY_ASSESSMENT}">        
                                    <span class="tk-float-left ">
                                        <uitk:multiselect  id="id3" input-model="multiSelect5"
                                                           output-model="selected2" button-label="name" item-label="name"
                                                           max-height="16.667rem" flyoutwindow-width="16.667rem" max-labels="1"  helper-elements="all none"
                                                           tick-property="ticked" default-label="Select Application">
                                        </uitk:multiselect>
                                    </span>
                                    <span class="tk-float-left tk-margin-left-1t">
                                        <a href="#" class="cux-icon-filter tk-font-10" ng-click="ssidFilter()" title="Filter"></a>
                                        <!--<a href="#" class="cux-icon-refresh tk-font-10" ng-click="refreshValue()" title="Refresh"></a>-->
                                    </span>
                                </c:if>
                                            </div>



                                <div class="tk-clear"></div>
                                <div class="tk-grid tk-border-top" >
<!--                                    <div class="tk-col-1-3">
                                        <div class="tk-module tk-background-none tk-border-none">
                                            	<h5 class="tk-margin-left-4t tk-margin-top-1t">Severity</h5>
                                            <div id="SecuritySeverity" align="left">Chart will load here</div>
                                        </div>
                                    </div>-->
                                    <c:if test="${service == constants.SERVICE_CODE_NETWORK_VULNERABILITY_ASSESSMENT || service==constants.SERVICE_CODE_PENETRATION_TESTING}">
                                        <div class="tk-col-1-3">
                                            <div class="tk-module tk-background-none tk-border-none">
                                                 <h5 class="tk-margin-left-4t tk-margin-top-1t">Operating Systems</h5>
                                                <div id="SecurityOS" align="left">Chart will load here</div>
                                                <span class="tk-float-left tk-margin-left-9t"><a href="#" id="uitkPopupId1_openModalBtn" title="View All" ng-click='toggleModal()'>View All</a></span>
                                            </div>
                                        </div>
                                    </c:if>
                                    <c:if test="${service == constants.SERVICE_CODE_APPLICATION_VULNERABILITY_ASSESSMENT}">
                                        <div class="tk-col-1-3">
                                            <div class="tk-module tk-background-none tk-border-none">
                                                <h5 class="tk-margin-left-6t tk-margin-top-1t">Application</h5>
                                                <div id="SecurityApp" align="left">Chart will load here</div>
                                                <span class="tk-float-left tk-margin-left-9t"><a href="#" id="uitkPopupId5_openModalBtn" title="View All" ng-click='toggleModal5()'>View All</a></span>
                                            </div>
                                        </div>
                                    </c:if>
                                    <c:if test="${service == constants.SERVICE_CODE_WIRELESS_RISK_ASSESSMENT}">
                                        <div class="tk-col-1-3">
                                            <div class="tk-module tk-background-none tk-border-none">
                                                  <h5 class="tk-margin-left-4t tk-margin-top-1t">SSID</h5>
                                                <div id="ssid" align="left">Chart will load here</div>
                                                <span class="tk-float-left tk-margin-left-9t"><a href="#" id="uitkPopupId3_openModalBtn" title="View All"  ng-click='toggleModal3()'> View All</a></span>
                                                </div>
                                            </div>
                                    </c:if>
<c:if test="${service == constants.SERVICE_CODE_APPLICATION_VULNERABILITY_ASSESSMENT || service == constants.SERVICE_CODE_NETWORK_VULNERABILITY_ASSESSMENT || service == constants.SERVICE_CODE_WIRELESS_RISK_ASSESSMENT || service == constants.SERVICE_CODE_LIMITED_REDTEAM_ASSESSMENT}">
    <div class="tk-col-1-3">
        <div class="tk-module tk-background-none tk-border-none">
            <h5 class="tk-margin-left-8t tk-margin-top-1t">Remediation Priorities</h5>
            <div id="priorityrem" align="left">Chart will load here</div>
            <div class="tk-float-right "><a href="#" id="uitkPopupId6_openModalBtn" title="View More" ng-click='toggleModal6()'>View More</a></div>
        </div>
    </div>
</c:if>
                                    <div class="tk-col-1-3">
                                        <div class="tk-module tk-background-none tk-border-none">
                                            <h5 class="tk-margin-left-10t tk-margin-top-1t">Vulnerability Category</h5>
                                            <div id="SecurityRoot" align="left">Chart will load here</div>
                                            <span class="tk-float-left tk-margin-left-17t"><a href="#" id="uitkPopupId2_openModalBtn" title="View All" ng-click='toggleModal2()'>View All</a></span>
                                        </div>
                                    </div>
                                    <c:if test="${service == constants.SERVICE_CODE_SECURITY_RISK_ASSESSMENT_CODE}">             
                                        <div class="tk-col-1-3">
                                            <div class="tk-module tk-background-none tk-border-none">
                                                 <h5 class="tk-margin-left-4t tk-margin-top-1t">HITRUST</h5>
                                                <div id="hitrust" align="left">Chart will load here</div>
                                                <span class="tk-float-left"><a href="#" id="uitkPopupId4_openModalBtn" title="View All" ng-click='toggleModal4()'> View All</a></span>

                                            </div>
                                        </div>
                                    </c:if> 
                                </div>
                                <div class="tk-grid tk-border-top  ">
                                    <c:if test="${service == constants.SERVICE_CODE_NETWORK_VULNERABILITY_ASSESSMENT}">

                                        <div class="tk-col-2-3">
                                            <div class="tk-module tk-background-none tk-border-none tk-margin-top-1t">
                                                <h3>Most Vulnerable</h3>
                                                <div id="topfivehosts" align="left">Chart will load here</div>
                                            </div>
                                        </div>
                                    </c:if>
                                    <c:if test="${service==constants.SERVICE_CODE_APPLICATION_VULNERABILITY_ASSESSMENT}">

                                        <div class="tk-col-2-3">
                                            <div class="tk-module tk-background-none tk-border-none tk-margin-top-1t">
                                                <h3>Most Vulnerable</h3>
                                                <div id="applicationMostVulnerable" align="left">Chart will load here</div>
                                            </div>
                                        </div>
                                    </c:if>
                                    <c:if test="${service == constants.SERVICE_CODE_NETWORK_VULNERABILITY_ASSESSMENT}">
                                        <div class="tk-col-1-3">
                                            <div class="tk-module tk-background-none tk-height-19t tk-border-none tk-margin-top-1t tk-left-border" id=aaa">
                                                <h3>Top Findings</h3>
                                                <div id="vulFindingId">
                                                    <c:choose>
                                                        <c:when test="${fn:length(vulnerabilities) gt 0}">
                                                            <c:forEach items="${vulnerabilities}" var="finding">
                                                                <p>${finding.label}</p>
                                                            </c:forEach>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <p align="center" class="tk-margin-top-5t" style="font-size: 10px;font-family: Verdana, sans">No data to display.</p>
                                                        </c:otherwise>
                                                    </c:choose>
                                                 
                                                </div>
                                                <div ng-model="topFingings" ng-repeat="vul in topFingings" ng-init="$index" >
                                                    <p>{{vul.label}}</p>
                                                </div>
                                                <div class="ndiv"></div>

                                            </div>
                                            </div> 
                                        </c:if>
                                    <c:if test="${service==constants.SERVICE_CODE_APPLICATION_VULNERABILITY_ASSESSMENT}">
                                        <div class="tk-col-1-3">
                                                 <div class="tk-module tk-background-none tk-height-19t tk-border-none tk-margin-top-1t tk-left-border">
                                              <div class="tk-module tk-background-none tk-border-none">
                                            <h5 class="tk-margin-left-10t tk-margin-top-1t">OWASP Top 10</h5>
                                            <div id="owaspId" align="left">Chart will load here</div>
                                            <span class="tk-float-left tk-margin-left-17t"><a href="#" id="uitkPopupId7_openModalBtn" title="View All" ng-click='toggleModal7()'>View All</a></span>
                                        </div>
                                                 </div>
                                            </div>
                                    </c:if>
                                                

                                    <div class="tk-clear"></div>
                                    <h3 class="tk-float-left tk-margin-top-1t"><a id="one" name="one"></a>Findings</h3>
                                    <div class="tk-clear"></div>

                                    <div>
                                        <!--<uitk:phi-confirmation id="phiConfirmation" view-model="phiVM"></uitk:phi-confirmation>-->
                                        <uitk:dynamic-table id="multiSort" model="multiSortModel"> </uitk:dynamic-table>
                                    </div>

                                    <!-- <uitk:tabs tk-model="tabsModel"></uitk:tabs>-->



                                </div>
                            </div>
                    </td>
                </tr>
            </table>

            <div> 
                <uitk:dialog dialog-id='uitkPopupId6' dialog-role='dialog'
                             header-text='Remediation Priorities' show='obj.modalShown6'
                             ng-if="obj.modalShown6"
                             confirm-dialog="false" tk-aria-describedby="dialogInsTextId"
                             default-width='65%' default-height="51%" trigger-element='#uitkPopupId6_openModalBtn'>
                    <fusioncharts  width="100%" height="400"  type="mscombi2d"   datasource="{{myDataSource5}}"></fusioncharts>
                    <uitk:button type="button" style="float:left;" value="Close" title="Close" enable-default="true"
                                 onclick="document.getElementById('uitkPopupId6_closeLink').click();"
                                 custom-class='uitk-width-6t uitk-btn-close-dialog'></uitk:button>

                </uitk:dialog>
                
                <uitk:dialog dialog-id='uitkPopupId1' dialog-role='dialog'
                               header-text='Operating System' show='obj.modalShown'
                               ng-if="obj.modalShown"
                               confirm-dialog="false" tk-aria-describedby="dialogInsTextId"
                               default-width='45%' default-height="51%" trigger-element='#uitkPopupId1_openModalBtn'>
                    <fusioncharts  width="100%" height="400"  type="scrollColumn2d"   datasource="{{myDataSource}}"></fusioncharts>
                    <uitk:button type="button" style="float:left;" value="Close" title="Close" enable-default="true"
                                 onclick="document.getElementById('uitkPopupId1_closeLink').click();"
                                 custom-class='uitk-width-6t uitk-btn-close-dialog'>

                    </uitk:button>

                </uitk:dialog>

                <uitk:dialog dialog-id='uitkPopupId2' dialog-role='dialog'
                             header-text='Vulnerability Category' show='obj.modalShown2'
                             ng-if="obj.modalShown2"
                             confirm-dialog="false" tk-aria-describedby="dialogInsTextId"
                             default-width='45%' default-height="51%" trigger-element='#uitkPopupId2_openModalBtn'>
                    <fusioncharts  width="100%" height="400"  type="scrollColumn2d"   datasource="{{myDataSource1}}"></fusioncharts>
                    <uitk:button type="button" style="float:left;" value="Close" title="Close" enable-default="true"
                                 onclick="document.getElementById('uitkPopupId2_closeLink').click();"
                                 custom-class='uitk-width-6t uitk-btn-close-dialog'></uitk:button>

                </uitk:dialog>
                <uitk:dialog dialog-id='uitkPopupId7' dialog-role='dialog'
                             header-text='OWASP Top 10' show='obj.modalShown7'
                             ng-if="obj.modalShown7"
                             confirm-dialog="false" tk-aria-describedby="dialogInsTextId"
                             default-width='45%' default-height="51%" trigger-element='#uitkPopupId7_openModalBtn'>
                    <fusioncharts  width="100%" height="400"  type="scrollColumn2d"   datasource="{{myDataSource7}}"></fusioncharts>
                    <uitk:button type="button" style="float:left;" value="Close" title="Close" enable-default="true"
                                 onclick="document.getElementById('uitkPopupId7_closeLink').click();"
                                 custom-class='uitk-width-6t uitk-btn-close-dialog'></uitk:button>

                </uitk:dialog>

                <uitk:dialog dialog-id='uitkPopupId3' dialog-role='dialog'
                             header-text='SSID' show='obj.modalShown3'
                             ng-if="obj.modalShown3"
                             confirm-dialog="false" tk-aria-describedby="dialogInsTextId"
                             default-width='45%' default-height="51%" trigger-element='#uitkPopupId3_openModalBtn'>
                    <fusioncharts  width="100%" height="400"  type="scrollColumn2d"   datasource="{{myDataSource2}}"></fusioncharts>
                    <uitk:button type="button" style="float:left;" value="Close" title="Close" enable-default="true"
                                 onclick="document.getElementById('uitkPopupId3_closeLink').click();"
                                 custom-class='uitk-width-6t uitk-btn-close-dialog'></uitk:button>

                </uitk:dialog>

                <uitk:dialog dialog-id='uitkPopupId4' dialog-role='dialog'
                             header-text='HITRUST' show='obj.modalShown4'
                             ng-if="obj.modalShown4"
                             confirm-dialog="false" tk-aria-describedby="dialogInsTextId"
                             default-width='45%' default-height="51%" trigger-element='#uitkPopupId4_openModalBtn'>
                    <fusioncharts  width="100%" height="400"  type="scrollColumn2d"   datasource="{{myDataSource3}}"></fusioncharts>
                    <uitk:button type="button" style="float:left;" value="Close" title="Close" enable-default="true"
                                 onclick="document.getElementById('uitkPopupId4_closeLink').click();"
                                 custom-class='uitk-width-6t uitk-btn-close-dialog'></uitk:button>

                </uitk:dialog>
                <uitk:dialog dialog-id='uitkPopupId5' dialog-role='dialog'
                             header-text='Application' show='obj.modalShown5'
                             ng-if="obj.modalShown5"
                             confirm-dialog="false" tk-aria-describedby="dialogInsTextId"
                             default-width='45%' default-height="51%" trigger-element='#uitkPopupId5_openModalBtn'>
                    <fusioncharts  width="100%" height="400"  type="scrollColumn2d"   datasource="{{myDataSource4}}"></fusioncharts>
                    <uitk:button type="button" style="float:left;" value="Close" title="Close" enable-default="true"
                                 onclick="document.getElementById('uitkPopupId5_closeLink').click();"
                                 custom-class='uitk-width-6t uitk-btn-close-dialog'>

                    </uitk:button>

                </uitk:dialog>
                <div>

                    <uitk:dialog dialog-id='uitkPopupId1' dialog-role='dialog' header-text='HITRUST Compliance Details' show='multiSortModel.modalShown' ng-if='multiSortModel.modalShown'
                                 confirm-dialog="false" default-width='50%' default-height="51%" trigger-element='#uitkPopupId1_openModalBtn'>

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
                        <uitk:button type="button" value="Close" enable-default="true" ng-click="Save('uitkPopupId1_closeLink')" custom-class='tk-width-6t '></uitk:button> 
                    </uitk:dialog>
                    <uitk:dialog dialog-id='uitkPopupId3' dialog-role='dialog' header-text='Finding' show='multiSortModel.modalShownExport' ng-if='multiSortModel.modalShownExport'
                                                 confirm-dialog="false" default-width='70%' default-height="71%" trigger-element='#uitkPopupId2_openModalBtn'>

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
                                                                             uitk-btn-disabled="false" title="Cancel" ng-click="Save('uitkPopupId2_closeLink')" ></uitk:button></span>


                                    </uitk:dialog>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="../dashboardcommons/dashboardfooter.jsp"/>


    <!--<script type="text/javascript" src="js/test-data.js"></script>-->
    <script type="text/javascript" src="<c:url value="/dashjs/AssessmentJS.js"/>"></script> 

    <script type="text/javascript" src="<c:url value="/dashjs/AssessmentChartsJS.js"/>"></script>


</body>
</html>
