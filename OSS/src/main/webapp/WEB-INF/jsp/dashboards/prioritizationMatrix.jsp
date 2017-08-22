<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="applicationConstants" />
<!DOCTYPE HTML>
<title>Optum Security Solutions</title>
<html lang="en-US" xmlns:ng="http://angularjs.org" >
    <head>
         <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta content="text/html;charset=utf-8" http-equiv="Content-Type" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
        .oui-heading .quadrant{
         width: 80%; margin:0 auto; position:relative; 
        }
        .oui-heading .quadrant .box{ 
        color:#333; 
        min-height: 180px;
        position:relative;
        padding:10px;
        } 
        .oui-heading .quadrant .box h4{ 
        font-size:15px; line-height:100%; 
        } 
        .oui-heading .quadrant .box h5{ 
        font-size:14px; line-height:100%; font-weight:normal; 
        } 

        .vertical-text { 
        transform: rotate(270deg); 
        transform-origin: left top 0; 
        } 
        .fndgs{
                     text-align: center;
                     padding: 5px 0;
                     position: absolute;
                     color: #fff !important;
                                                                     right: 0; top: 0;
        }
        .fndgs:hover{
             cursor:pointer; border:1px #F47B29 solid;  
        }
        .fndgs p{ margin-bottom: 8px; }
        .fndgs span{font-size: 18px;}

        .tk-list-bulleted-text li{
                     line-height: 140%;
                     list-style: outside;
                     margin: 0 0 0 20px;
                                                                     font-size:13px;
        }
        .tk-view-all{
                     position: relative;
         left: 230px;
         top: 15px;
        }
        .tk-box {
         width:40%;
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
                            <!--                            <div class="tk-margin-bottom-2t "> <uitk:breadcrumb view-model="breadcrumbViewModel4"></uitk:breadcrumb></div> -->

                            <h1 class="tk-font-bold">Security <c:out value="${ePKName}"/> - <span class="tk-font-normal tk-font-12"><c:out value="${eName}"/> (<c:out value="${eCode}"/>) </span>  </h1>
                            <div class="tk-clear"></div>

                            <div class="">
                                <!--  <uitk:panel model="panelViewModel"></uitk:panel>-->
                                <h2 class="">Prioritization Matrix</h2>


                                <div class="quadrant">
                                    <div style="position:absolute; right:13%; top:-20px;" ><a href="#"  id="uitkPopupId1_openModalBtn" ng-click='toggleModal()'>Show All Findings</a></div>

                                    <span style="position:absolute; left:30px; top:90px;"><h5 class="tk-colr-gray-3 no-bold">High</h5></span>
                                    <div class="tk-box  tk-float-left tk-brdr-gray-0 box" style="margin-left:6em;height:190px">
                                        <h4 class="tk-colr-orange tk-margin-bottom-halft">High Impact / Low Cost</h4>
                                        <!-- <h5 class="tk-colr-orange tk-margin-top-none">Low Hanging Fruit</h5>
                                        <p>12 Findings are found</p> -->
                                        <div class="tk-width-5t tk-colr-bg-turquoise-5 fndgs" ng-click='changeFirstquadratureFinding("${quadrantData.quadrant1FilterFindings}","${quadrantData.q1FindingsTotal}")'><p>Findings</p><span>${quadrantData.q1FindingsTotal}</span></div>

                                        <ol class="tk-list-bulleted-text tk-margin-top-2t">
                                            <c:set var="count1" value="0"/>

                                            <c:forEach items="${quadrantData.q1Findings}" var="q1Findings">
                                                <c:if test="${count1 < 5}">
                                                    <c:choose>
                                                        <c:when test="${fn:length(q1Findings) > 40}">
                                                        <li title="${q1Findings}">${q1Findings.substring(0,40)}....</li>
                                                        </c:when>
                                                        <c:otherwise>
                                                    <li>${q1Findings}</li>
                                                        </c:otherwise>
                                                        </c:choose>
                                                        <c:set var="count1" value="${count1+1}"/>
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${fn:length(quadrantData.q1Findings) >5}">   
                                                <span class="tk-float-right tk-viewall"><a href="#" id="uitkPopupId2_openModalBtn" ng-click='quadrature1()'>View All </a> </span></c:if>
                                        </div>

                                        <span class="vertical-text" style="position:absolute; left:0; top:210px;"><h4 class="tk-colr-gray-4">Impact</h4></span>
                                        <span style="position:absolute; left:30px; top:260px;"><h5 class="tk-colr-gray-3 no-bold">Low</h5></span>
                                        <div class="tk-box  tk-float-left tk-brdr-gray-0 box" style="height:190px">
                                            <h4 class="tk-colr-orange tk-margin-bottom-halft">High Impact / High Cost</h4>
                                            <!-- <h5 class="tk-colr-orange tk-margin-top-none">Requires Enterprise Commitment</h5>
                                            <p>10 Findings are found</p> -->
                                            <div class="tk-width-5t tk-colr-bg-blue-5 fndgs" ng-click='changeFirstquadratureFinding("${quadrantData.quadrant2FilterFindings}","${quadrantData.q2FindingsTotal}")'><p>Findings</p><span>${quadrantData.q2FindingsTotal}</span></div>
                                        <ol class="tk-list-bulleted-text tk-margin-top-2t">
                                            <c:set var="count1" value="0"/>
                                            <c:forEach items="${quadrantData.q2Findings}" var="q2Findings">
                                                
                                                <c:if test="${count1 < 5}">
                                                    <c:choose>
                                                        <c:when test="${fn:length(q2Findings) > 40}">
                                                        <li title="${q2Findings}">${q2Findings.substring(0,40)}....</li>
                                                        </c:when>
                                                        <c:otherwise>
                                                    <li>${q2Findings}</li>
                                                        </c:otherwise>
                                                        </c:choose>
                                                    <c:set var="count1" value="${count1+1}"/>
                                                </c:if>
                                                </c:forEach>

                                        </ol>
                                        <c:if test="${fn:length(quadrantData.q2Findings) >5}"> 
                                            <span class="tk-float-right tk-viewall"><a href="#"  id="uitkPopupId1_openModalBtn" ng-click='quadrature2()'>View All </a> </span></c:if>
                                        </div>
                                        <span class="tk-clear"></span>
                                        <span style="position:absolute; left:24%; top:390px;"><h5 class="tk-colr-gray-3 no-bold">Low</h5></span>        
                                        <div class="tk-box tk-float-left tk-brdr-gray-0 box" style="margin-left:6em; height:190px">
                                            <h4 class="tk-colr-orange tk-margin-bottom-halft">Low Impact / Low Cost</h4>
                                            <!-- <h5 class="tk-colr-orange tk-margin-top-none">Small Wins</h5>
                                            <p>8 Findings are found</p> -->
                                            <div class="tk-width-5t tk-colr-bg-yellow-5 fndgs"  ng-click='changeFirstquadratureFinding("${quadrantData.quadrant3FilterFindings}","${quadrantData.q3FindingsTotal}")'><p>Findings</p><span>${quadrantData.q3FindingsTotal}</span></div>
                                        <ol class="tk-list-bulleted-text tk-margin-top-2t">
                                            <c:set var="count1" value="0"/>
                                            <c:forEach items="${quadrantData.q3Findings}" var="q3Findings">
                                                <c:if test="${count1 < 5}">
                                                    <c:choose>
                                                        <c:when test="${fn:length(q3Findings) > 40}">
                                                            <li title="${q3Findings}">${q3Findings.substring(0,40)}....</li>
                                                        </c:when>
                                                        <c:otherwise>
                                                    <li>${q3Findings}</li>
                                                    </c:otherwise>
                                                    </c:choose>
                                                        <c:set var="count1" value="${count1+1}"/>
                                                    </c:if>
                                                </c:forEach>
                                        </ol>
                                        <c:if test="${fn:length(quadrantData.q3Findings) >5}">   
                                            <span class="tk-float-right tk-viewall"><a href="#"  id="uitkPopupId1_openModalBtn" ng-click='quadrature3()'>View All </a> </span></c:if>
                                        </div>
                                        <span style="position:absolute; left:66%; top:390px;"><h5 class="tk-colr-gray-3 no-bold">High</h5></span> 
                                        <div class="tk-box tk-float-left tk-brdr-gray-0 box" style="height:190px">
                                            <h4 class="tk-colr-orange tk-margin-bottom-halft">Low Impact / High Cost</h4>
                                            <!-- <h5 class="tk-colr-orange tk-margin-top-none">More Analysis Required</h5>
                                            <p>7 Findings are found</p> -->
                                            <div class="tk-width-5t tk-colr-bg-orange-5 fndgs" ng-click='changeFirstquadratureFinding("${quadrantData.quadrant4FilterFindings}","${quadrantData.q4FindingsTotal}")'><p>Findings</p><span>${quadrantData.q4FindingsTotal}</span></div>
                                        <ol class="tk-list-bulleted-text tk-margin-top-2t">
                                            <c:set var="count1" value="0"/>
                                            <c:forEach items="${quadrantData.q4Findings}" var="q4Findings">
                                                <c:if test="${count1 < 5}">
                                                    <c:choose>
                                                        <c:when test="${fn:length(q4Findings) > 40}">
                                                        <li title="${q4Findings}">${q4Findings.substring(0,40)}....</li>
                                                        </c:when>
                                                        <c:otherwise>
                                                    <li>${q4Findings}</li>
                                                        </c:otherwise>
                                                        </c:choose>
                                                        <c:set var="count1" value="${count1+1}"/>
                                                    </c:if>
                                                </c:forEach>
                                        </ol>
                                        <c:if test="${fn:length(quadrantData.q4Findings) >5}">
                                            <span class="tk-float-right tk-viewall"><a href="#"  id="uitkPopupId1_openModalBtn" ng-click='quadrature4()'>View All </a> </span></c:if>
                                        </div>
                                        <span style="position:absolute; left:43%; top:400px;"><h4 class="tk-colr-gray-4">Cost/Effort</h4></span>
                                    </div> 
                                </div>

                                <div class="tk-clear"></div>
                                <div class="tk-clear"></div>
                                <h3 class="tk-float-left tk-margin-top-none" style="position:relative; top: 45px;"><a id="one" name="one"></a>Findings</h3>
                                <div class="tk-clear"></div>
                                <div>
                                    <!--<uitk:phi-confirmation id="phiConfirmation" view-model="phiVM"></uitk:phi-confirmation>-->
                                    <uitk:dynamic-table id="multiSort1" model="multiSortModel4"> </uitk:dynamic-table>
                                    <!--<uitk:dynamic-table model="columnShowHideModel" id="columnShowHide"> </uitk:dynamic-table>-->
                                </div>
                            </div>
                        <%--Start 'View All' findings binding to the each quadrature --%>
                        <div> 
                            <uitk:dialog dialog-id='uitkPopupId2' dialog-role='dialog'
                                         header-text='High Impact - Low Cost' show='obj.modalShown1'
                                         ng-if="obj.modalShown1"
                                         confirm-dialog="false" tk-aria-describedby="dialogInsTextId"
                                         default-width='45%' default-height="51%" trigger-element='#uitkPopupId2_openModalBtn'>
                                <ol class="tk-list-bulleted-text tk-margin-top-1t">
                                    <c:forEach items="${quadrantData.q1Findings}" var="q1Findings">
                                        <li>${q1Findings}</li>
                                        </c:forEach>
                                </ol>
                                <uitk:button type="button" style="float:left;" value="Close"  enable-default="true"
                                             onclick="document.getElementById('uitkPopupId2_closeLink').click();"
                                             custom-class='uitk-width-6t uitk-btn-close-dialog' title="Close" ></uitk:button>
                            </uitk:dialog>

                        </div>
                        <div> 
                            <uitk:dialog dialog-id='uitkPopupId2' dialog-role='dialog'
                                         header-text='High Impact - Low Cost' show='obj.modalShown2'
                                         ng-if="obj.modalShown2"
                                         confirm-dialog="false" tk-aria-describedby="dialogInsTextId"
                                         default-width='45%'   default-height="51%" trigger-element='#uitkPopupId2_openModalBtn'>
                                <ol class="tk-list-bulleted-text tk-margin-top-1t">
                                    <c:forEach items="${quadrantData.q2Findings}" var="q2Findings">
                                        <li>${q2Findings}</li>
                                        </c:forEach>
                                </ol>
                                <uitk:button type="button" style="float:left;" value="Close"  enable-default="true"
                                             onclick="document.getElementById('uitkPopupId2_closeLink').click();"
                                             custom-class='uitk-width-6t uitk-btn-close-dialog' title="Close" ></uitk:button>
                            </uitk:dialog>

                        </div>
                        <div> 
                            <uitk:dialog dialog-id='uitkPopupId2' dialog-role='dialog'
                                         header-text='High Impact - Low Cost' show='obj.modalShown3'
                                         ng-if="obj.modalShown3"
                                         confirm-dialog="false" tk-aria-describedby="dialogInsTextId"
                                         default-width='45%' default-height="51%" trigger-element='#uitkPopupId2_openModalBtn'>
                                <ol class="tk-list-bulleted-text tk-margin-top-1t">
                                    <c:forEach items="${quadrantData.q3Findings}" var="q3Findings">
                                        <li>${q3Findings}</li>
                                        </c:forEach>
                                </ol>
                                <uitk:button type="button" style="float:left;" value="Close"  enable-default="true"
                                             onclick="document.getElementById('uitkPopupId2_closeLink').click();"
                                             custom-class='uitk-width-6t uitk-btn-close-dialog' title="Close" ></uitk:button>
                            </uitk:dialog>

                        </div>
                        <div> 
                            <uitk:dialog dialog-id='uitkPopupId2' dialog-role='dialog'
                                         header-text='High Impact - Low Cost' show='obj.modalShown4'
                                         ng-if="obj.modalShown4"
                                         confirm-dialog="false" tk-aria-describedby="dialogInsTextId"
                                         default-width='45%'  default-height="51%" trigger-element='#uitkPopupId2_openModalBtn'>
                                <ol class="tk-list-bulleted-text tk-margin-top-1t">
                                    <c:forEach items="${quadrantData.q4Findings}" var="q4Findings">
                                        <li>${q4Findings}</li>
                                        </c:forEach>
                                </ol>
                                <uitk:button type="button" style="float:left;" value="Close"  enable-default="true"
                                             onclick="document.getElementById('uitkPopupId2_closeLink').click();"
                                             custom-class='uitk-width-6t uitk-btn-close-dialog' title="Close" ></uitk:button>
                            </uitk:dialog>
                            <%--End 'View All' findings binding to the each quadrature --%>
                            <uitk:dialog dialog-id='uitkPopupId3' dialog-role='dialog' header-text='Finding' show='multiSortModel4.modalShownExport' ng-if='multiSortModel4.modalShownExport'
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

                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <jsp:include page="../dashboardcommons/dashboardfooter.jsp"/>
    <script type="text/javascript" src="<c:url value="/js/matrix-triage.js"/>"></script> 
</body>
</html>
