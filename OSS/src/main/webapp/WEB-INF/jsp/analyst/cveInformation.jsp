<%-- 
    Document   : cveInformation
    Created on : May 26, 2016, 5:15:54 PM
    Author     : spatepuram
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  uri="/WEB-INF/tlds/spring-form.tld" prefix="form" %>
<%@taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="constants" />
<script type="text/javascript" src="<c:url value="/appjs/cveinfoJS.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery.simplePagination.js"/>"></script>
<link href="<c:url value="/css/simplePagination.css"/>" type="text/css" rel="stylesheet"/>
<!-- JS : Include --> 

<script type="text/javascript">
    $(document).ready(function() {
        
        $('.fixme5').fixheadertable({             
            //colratio    : [10,115,,150,150],     
            zebra:true,
            pager : false,
        });
        
        $("#addCVEIdToPg").hide();
        $("#cvepagination").hide();
        $("#darrow").hide();
        $("#uarrow").hide();
        var sortkey = '';
        var sortcve = '';
        $.fn.cveLookup = function(pageNumber,flag){
            var keyword = '';
            var selCVEid = '';
            if(flag == 'A'){
                keyword = sortkey;
                selCVEid = sortcve;
                $("#darrow").hide();
                $("#uarrow").show();
            } else  if(flag == 'D'){
                 keyword = $("#cveDesc").val();
                selCVEid = $("#selCVEid").val();
                sortkey = keyword;
                sortcve = selCVEid;
                $("#darrow").show();
                $("#uarrow").hide();
            }else{
                keyword = $("#cveDesc").val();
                selCVEid = $("#selCVEid").val();
                sortkey = keyword;
                sortcve = selCVEid;
                $("#darrow").show();
                $("#uarrow").show();
            }
            $("#cvePopErrMessage").html("");
            $("#searchErrMessage").html("");
            //alert(keyword + '-' +selCVEid);
            
            if($.trim(keyword).length > 0 || $.trim(selCVEid).length > 0){
                $("#cveTable tbody").empty();
                var loadrow = "<tr style='height:300px'><td colspan='5'><center><img src='<c:url value="/images/loading.gif"/>'/></center></td></tr>";
                $("#cveTable tbody").append(loadrow);
                jQuery.ajax({
                    type: "POST",
                    contentType: "application/json",
                    url: "fetchcvelist.htm?keyword="+encodeURIComponent(keyword)+"&cveid="+$.trim(selCVEid)+"&pgno="+pageNumber+"&flag="+flag,
                    datatype: "json",
                    async: false,
                    success: function(data) {
                        $("#cveTable tbody").empty();
                        var trow = "";
                        if(data.length > 0){
                            for(var i=0;i<data.length;i++){
                                var cbox = "<input type='radio' name='cveid' value="+data[i].cveID+" />"
                                trow = "<tr><td style='width:4% !important'>"+cbox+"</td><td style='width:15% !important'>"+data[i].cveID+"</td><td style='width:48%'>"+data[i].cveDesc+"</td><td style='width:15%'>"+data[i].createdDate+"</td><td style='width:15%'>"+data[i].updatedDate+"</td></tr>";
                                $("#cveTable tbody").append(trow);
                            }
                        }else{
                            trow = "<tr><td colspan='5'><center>No matching records found</center></td></tr>";
                            $("#cveTable tbody").append(trow);
                        }
                        
                        if(data.length > 0 && data[0].totalCount > 0){
                            $("#addCVEIdToPg").show();
                            $("#cvepagination").show();
                        }else{
                            $("#addCVEIdToPg").hide();
                             $("#cvepagination").hide();
                        }
                        
                        // now setup pagination
                        $("#cvepagination").pagination({
                            items: data[0].totalCount,
                            itemsOnPage: 100,
                            cssStyle: "light-theme",
                            currentPage: pageNumber,
                            onPageClick: function(pageNumber) { 
                                $("#cveTable tbody").empty();
                                var loadrow = "<tr style='height:300px'><td colspan='5'><center><img src='<c:url value="/images/loading.gif"/>'/></center></td></tr>";
                                $("#cveTable tbody").append(loadrow);
                                $.fn.cveLookup(pageNumber,flag);
                            }
                        });
                     },
                     error: function(e) {
                     
                         
                     }
                 });
            }else{
                $("#searchErrMessage").html("Please enter CVE ID or Keyword");
            }
        }
        
    });
    
</script>

<!-- Popup Starts -->
<div id='blackcover'> </div>
<div id='popupbox' class="ux-modl">
    <div class="ux-modl-header">
        <a href="#compliance" onclick='popup(0);cancelHitrust();' title="Close">Close</a>
        <h6>Control Lookup</h6>
    </div>

    <div class="ux-modl-content">
        <c:choose>
            <c:when test="${cveCompInfo.size()> 0}">
                <c:forEach var="cveCompInfo" items="${cveCompInfo}">
                    <div class="ux-float-left ux-padding-top-halft">
                        <c:set var="complianceVar" value="${fn:split(cveCompInfo.key, constants.SYMBOL_SPLITING)}" />
                        <c:set value="${complianceVar[0]}" var="complianceCode"></c:set>
                        <c:set value="${complianceVar[1]}" var="complianceVersion"></c:set>
                        <input type="hidden" value="${complianceCode}"  name="complianceInfoDTO.complicanceCode" />
                        <input type="hidden" value="${complianceVersion}"  name="complianceInfoDTO.complianceVersion" />
                        <h2>${complianceCode}</h2>
                    </div>
                    <c:set var="cveFamilyControl" value="${cveCompInfo.value}" />
                    <!--                <div class="ux-float-right">
                                        Keyword Search: <input type="text" value="" /> <input type="button" value="Search" class="ux-btn-default-action" />
                                    </div>-->
                    <div class="ux-clear"></div>
                    <table class="display" id="hitrustDataTable">
                        <thead>
                            <tr>
                                <th>Family Control</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${cveFamilyControl.size()> 0}">
                                    <c:forEach var="cveFamilyControl" items="${cveFamilyControl}" varStatus="CVDTOIndex">
                                        <tr id="ex0-node-${CVDTOIndex.index}">
                                            <c:set var="familyCodeName" value="${fn:split(cveFamilyControl.key, constants.SYMBOL_SPLITING)}" />
                                            <c:set value="${familyCodeName[0]}" var="familyCode"></c:set>
                                            <c:set value="${familyCodeName[1]}" var="familyName"></c:set>
                                            <td class="ux-padding-left-2t">${familyCode}&nbsp;${familyName}</td>


                                        </tr> <tr id="ex0-node-${CVDTOIndex.index}-1" class="child-of-ex0-node-${CVDTOIndex.index}">
                                            <td >

                                                <table class="ux-tabl-data">

                                                    <tr>
                                                        <th class="ux-padding-1t" width="5%"><input type="checkbox" id="familyCheckBox${fn:replace(familyCode, ".","-")}" fId="${fn:replace(familyCode, ".","-")}" class="familyCheckAll${fn:replace(familyCode, ".","-")}" onchange="familyCheckAll(this, '${fn:replace(familyCode, ".","-")}')" /></th>
                                                        <th class="ux-padding-1t" width="25%">Objective </th>
                                                        <th class="ux-padding-1t" width="25%">Control</th>
                                                        <th class="ux-padding-1t">Description</th>
                                                    </tr>

                                                    <tbody>
                                                        <%--<c:set var="cveCompInfoList" value="${cveCompInfo.value}"/>--%>

                                                        <c:forEach var="cveCompInfoList" items="${cveFamilyControl.value}">
                                                            <tr>
                                                                <td><input type="checkbox" id="${cveCompInfoList.controlCode}" oId="${fn:replace(familyCode, ".","-")}"  class="objectiveCheck${fn:replace(familyCode, ".","-")}" onchange="objectiveSingleCheck(this, '${fn:replace(familyCode, ".","-")}')"   /></td>
                                                                <td>${cveCompInfoList.objectiveCode}&nbsp;${cveCompInfoList.objectiveName}</td>
                                                                <td>${cveCompInfoList.controlCode}&nbsp;${cveCompInfoList.controlName}</td>
                                                                <td>${cveCompInfoList.controlDesc}</td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>

                                                </table>


                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                            </c:choose></tbody>
                    </table>
                </c:forEach>
            </c:when>
        </c:choose>
        <div class="ux-clear"></div>
        <div class="ux-margin-1t ux-margin-left-11t ux-float-right">
            <input type="hidden" id="checkedHitrustValues" value="${vulnerabilityDTO.checkedHitrustValues}" name="checkedHitrustValues" />
            <input type="button" value="Add" title="Add" class="ux-btn-default-action " onclick='popup(0);
                    addHitrust();' id="showcontrols"/> <input type="button" value="Cancel" title="Cancel" class="ux-btn-default-action " onclick='popup(0);cancelHitrust();'/>
        </div>
    </div>
</div>
<!-- Popup Ends -->

<!-- CVE Popup Begins -->
<div id='popupbox1' class="ux-modl">
    <div class="ux-modl-header">
        <a href="#cvein" onclick='cvepopup(0);' title="Close">Close</a>
        <h6>CVE Lookup</h6>
    </div>
    
    <div class="ux-margin-1t">
       CVE ID: <input type="text" id="selCVEid" value="" class="ux-width-12t" onkeypress="return blockSpecialChar(event)" />&nbsp; (OR) &nbsp;
        Keyword: <input type="text" name="cveDesc" value="" class="ux-width-12t" id="cveDesc"/>
        <input type="button" value="Search" title="Search" class="ux-btn-default-action" onclick='javascript:$.fn.cveLookup(1,"D");' onkeyup="limiter(this, 200, '', '')"/>
    </div>
    <label id="searchErrMessage" class="ux-msg-error-under ux-margin-1t"></label>
    <label id="cvePopErrMessage" class="ux-msg-error-under ux-margin-1t"></label>
    <div class="ux-float-left ux-width-full-inclusive" >
        <table id="cveTable" class="fixme5">
            <thead>
                <tr>
                    <th style="width:3% !important;"></th>
                    <th style="width:15% !important;">CVE ID</th>
                    <th style="width:45% !important;">Summary</th>
                    <th style="width:15% !important;">Published Date<a href="#"><img src="images/down_arrow.png" id="darrow" onclick='javascript:$.fn.cveLookup(1,"A");' /></a> <a href="#"><img src="images/up_arrow.png" id="uarrow" onclick='javascript:$.fn.cveLookup(1,"D");' /></a></th>
                    <th style="width:14% !important;">Last Update </th>
                    <th style="width:2% !important;"></th>
                </tr>
            </thead>
            <tbody>
                
            </tbody>
        </table>
        <div id="cvepagination" class="ux-margin-top-1t ux-margin-left-halft"></div>
    </div>
    
    <div class="ux-modl-content">
        <div class="ux-clear"></div>
        <div class="ux-margin-1t ux-margin-left-11t ux-float-right">
            <c:choose>
                <c:when test="${pageName eq 'UPDATEPAGE' && vulnerabilityDTO.cveInformationDTO.cveIdentifier != null && vulnerabilityDTO.cveInformationDTO.cveIdentifier != ''}">
                    <input type="button" value="Update" title="Update" class="ux-btn-default-action " onclick='addCVEtoPage();' id="addCVEIdToPg"/> 
                </c:when>
                    <c:otherwise>
                        <input type="button" value="Add" title="Add" class="ux-btn-default-action " onclick='addCVEtoPage();' id="addCVEIdToPg"/> 
                    </c:otherwise>
            </c:choose>
            <input type="button" value="Cancel" title="Cancel" class="ux-btn-default-action " onclick='cvepopup(0);'/>
        </div>
    </div>
</div>
<!-- CVE Popup Ends -->       
            
<!-- Begin : Header -->
<!-- End : Header -->

<!-- Begin : Bread Crumbs -->
<!-- End :  Bread Crumbs -->
<!-- Begin: Content -->
<div class="ux-panl ux-float-left"><a href="#" id="cvein"></a>
    <div class="ux-panl-header ux-panl-with-underline"> 
        <h2>CVE Information</h2>
    </div>
    <div class="ux-panl-content">
        <span class="ux-margin-left-1t"> Note : Please enter the complete CVE ID. For example, CVE-2016-7119. If partial CVE ID is entered, the details cannot be fetched.</span>

        <div class="ux-hform">
            <dl class="LongTxt">
                <dt><label>CVE ID: </label></dt>
                <dd> 
                    <input type="text" value="${vulnerabilityDTO.cveInformationDTO.cveIdentifier}" id="atcveId" name="cveInformationDTO.cveIdentifier" onkeypress="return blockSpecialChar(event)"/>
                    &nbsp;<c:choose><c:when test="${pageName eq 'UPDATEPAGE' && vulnerabilityDTO.cveInformationDTO.cveIdentifier != null && vulnerabilityDTO.cveInformationDTO.cveIdentifier != ''}"><input type="button" class="ux-btn-default-action" onclick="getCveInfo()" value="Update" title="Update" class="ux-btn showcve"  /></c:when><c:otherwise><input type="button" onclick="getCveInfo()" value="Add" title="Add" class="ux-btn showcve ux-btn-default-action"  /></c:otherwise></c:choose>
                    <input type="button" value="CVE Lookup" class="ux-btn-default-action ux-margin-left-1t" title="CVE Lookup" onclick="javascript:cvepopup(1);"/>
                    <br/><form:errors path="cveInformationDTO.cveIdentifier" cssStyle="color: red;width:190px; font-weight: bold"/>
                </dd>
                <!-- Display message if CVE ID not present in our database-->
                <c:if test="${!empty cveHubMessage}">
                    <label id="cveHubMessage" class="ux-msg-error-under">${cveHubMessage}</label>
                </c:if>
                <label id="errorMsgAtcveId" class="ux-msg-error-under"></label>
            </dl> 
            <div class="ux-clear"></div>
            <c:if test="${vulnerabilityDTO.cveInformationDTO.cveIdentifier != null && vulnerabilityDTO.cveInformationDTO.cveIdentifier != '' && empty cveHubMessage}">
                <span class="cve">
                    <h3 class="ux-margin-bottom-1t">CVE Details</h3>
                    <dl style="vertical-align: top">
                        <dt><label>CWE ID:</label></dt>
                        <input type="hidden" name="cveInformationDTO.cweID" value="${vulnerabilityDTO.cveInformationDTO.cweID}"/>
                        <dd><c:out value="${vulnerabilityDTO.cveInformationDTO.cweID}" escapeXml="true"></c:out></dd>
                        </dl> 
                        <dl style="vertical-align: top">
                            <dt><label>Updated Date:</label></dt>
                            <input type="hidden" name="cveInformationDTO.updatedDate" value="${vulnerabilityDTO.cveInformationDTO.updatedDate}"/>
                        <dd><c:out escapeXml="true" value="${vulnerabilityDTO.cveInformationDTO.updatedDate}"></c:out></dd>
                        </dl>
                        <dl style="vertical-align: top" class="ux-width-30t" >
                            <dt><label>CVE Summary:</label></dt>
                            
                        <dd><c:out value="${vulnerabilityDTO.cveInformationDTO.cveDesc}" escapeXml="true"></c:out></dd>
                        </dl>
                    </span> 
            </c:if>
            <input type="hidden" name="cveInformationDTO.cveDesc" value="${vulnerabilityDTO.cveInformationDTO.cveDesc}"/>
            <input type="hidden" name="cveInformationDTO.vectorText" value="${vulnerabilityDTO.vectorText}"/>
            <input type="hidden" name="cveInformationDTO.impSubScore" value="${vulnerabilityDTO.cveInformationDTO.impSubScore}"/>
            <input type="hidden" name="cveInformationDTO.expSubScore" value="${vulnerabilityDTO.cveInformationDTO.expSubScore}"/>
        </div>



    </div>
</div>
<a name="map" id="map"></a>

<div class="ux-clear"></div>
<div class="ux-panl ux-float-left ux-margin-right-1t scorepanelbox 
     <c:if test="${vulnerabilityDTO.sourceName ne 'Manual' && pageName ne 'ADDPAGE'}">
        scorepanelbox1
     </c:if>
     " >
    <div class="ux-panl-header ux-panl-with-underline">
        <h2 style="display:inline; position:relative; top:5px;">CVSS Scoring <span class="ux-float-right ux-margin-top-min "><input type="button" value="CVSS Score Calculator"  title="CVSS Score Calculator" class="ux-btn-default-action" 
                                                                                                                  id="cvsscalcbtn"/></span></h2>
    </div>
    <!--       <button>&raquo;</button>
    -->
    <div class="ux-FormStyleLeftAlign scorepanelform" >
        <c:if test="${vulnerabilityDTO.sourceName ne 'Manual' && pageName ne 'ADDPAGE'}">
                <dl class="ux-width-30t">
                    <dt>
                    <label>Source Vulnerability Base Score:</label>
                    </dt>
                    <dd><c:out value="${vulnerabilityDTO.cveInformationDTO.srcBaseScore}"></c:out></dd>
                </dl>
                </c:if>
        <dl class="ux-width-30t">
            <dt >

            <label>Base Score: <span class="mandStart">*</span></label>
            </dt>
            <dd>
                <span class="cve"><input name="cveInformationDTO.baseScore" id="baseScoreId" type="text" readonly="true" value="${vulnerabilityDTO.cveInformationDTO.baseScore}" /></span>
            </dd>
            <label id="errorMsgBaseScoreId" class="ux-msg-error-under ux-float-left" style="width:100%;"></label>
        </dl>
        <span class="ux-float-left ux-width-30t"><form:errors path="cveInformationDTO.baseScore" cssStyle="color: red; font-weight: bold"/></span>
        <dl  class="ux-width-30t">
            <dt>
            <label>Temporal Score:</label>
            </dt>
            <dd>
                <span class="cve"><input name="cveInformationDTO.temporalScore" readonly="true" type="text"  value="${vulnerabilityDTO.cveInformationDTO.temporalScore}" /></span>
            </dd>
        </dl>
        <dl class="ux-width-30t" >
            <dt >
            <label>Environmental Score:</label>
            </dt>
            <dd>
                <span class="cve"><input name="cveInformationDTO.environmentalScore" readonly="true" type="text"  value="${vulnerabilityDTO.cveInformationDTO.environmentalScore}" /></span>
            </dd>
        </dl>
        <dl class="ux-width-30t" >
            <dt >
            <label>Overall Score: <span class="mandStart">*</span></label>
            </dt>
            <dd>
                <span class="cve"><input name="cveInformationDTO.overallScore" id="overallScoreId" readonly="true" type="text"  value="${vulnerabilityDTO.cveInformationDTO.overallScore}" /></span>
            </dd>
            <label id="errorMsgOverallScoreId"></label>
        </dl>
        <input type="hidden" name="vectorText" value="${vulnerabilityDTO.vectorText}"/>



    </div>
    
    <div class="ux-float-right ux-padding-1t 
         <c:if test="${vulnerabilityDTO.sourceName ne 'Manual' && pageName ne 'ADDPAGE'}">
             ux-margin-top-1t
         </c:if>
    ">  
        
    </div>
</div>
<div class="ux-panl ux-float-left  scorepanelbox
     <c:if test="${vulnerabilityDTO.sourceName ne 'Manual' && pageName ne 'ADDPAGE'}">
        scorepanelbox1
     </c:if>
     " >
    <div class="ux-panl-header ux-panl-with-underline">
        <h2>Risk</h2>
    </div>
    <div class="ux-FormStyleLeftAlign scorepanelform">
        <dl class="ux-width-30t">
            <dt>
            <label>Severity: <span class="mandStart">*</span></label>
            </dt>
            <dd>
                <input type="hidden" name="cveInformationDTO.severityCode" value="${vulnerabilityDTO.cveInformationDTO.severityCode}"/>
                <input type="hidden" name="cveInformationDTO.severityName" value="${vulnerabilityDTO.cveInformationDTO.severityName}"/>
                <span class="cve"><c:out value="${vulnerabilityDTO.cveInformationDTO.severityName}" escapeXml="true"></c:out></span>
                </dd>
            </dl>
            <dl class="ux-width-30t">
                <dt>
                <label>Probability: <span class="mandStart">*</span></label>
                </dt>
                <dd>
                    <input type="hidden" name="cveInformationDTO.probabilityCode" value="${vulnerabilityDTO.cveInformationDTO.probabilityCode}"/>
                <input type="hidden" name="cveInformationDTO.probabilityName" value="${vulnerabilityDTO.cveInformationDTO.probabilityName}"/>
                <span class="cve"><c:out value="${vulnerabilityDTO.cveInformationDTO.probabilityName}" escapeXml="true"></c:out></span>
                </dd>
            </dl>
            <dl class="ux-width-30t">
                <dt>
                <label>Impact: <span class="mandStart">*</span></label>
                </dt>
                <dd>
                    <input type="hidden" name="cveInformationDTO.impactCode" value="${vulnerabilityDTO.cveInformationDTO.impactCode}"/>
                <input type="hidden" name="cveInformationDTO.impactName" value="${vulnerabilityDTO.cveInformationDTO.impactName}"/>
                <span class="cve"><c:out value="${vulnerabilityDTO.cveInformationDTO.impactName}" escapeXml="true"></c:out></span>
                </dd>
            </dl>
            <dl class="ux-width-30t">
                <dt>
                <label>Cost Effort: <span class="mandStart">*</span></label>
                </dt>
                <dd>
                    <span class="cve">
                        <select  id = "costEfforDetail" name="cveInformationDTO.costEffortCode"  onchange="" >

                            <option value="">Select</option>
                        <c:forEach var="costEffortDTO" items="${costEffortList}">
                            <option value="${costEffortDTO.masterLookupCode}"<c:if test="${vulnerabilityDTO.cveInformationDTO.costEffortCode eq costEffortDTO.masterLookupCode}">selected</c:if>>${costEffortDTO.lookUpEntityName}</option>
                        </c:forEach>
                    </select>
                </span>
                <br/><form:errors path="cveInformationDTO.costEffortCode" cssStyle="color: red;width:190px; font-weight: bold"/>
            </dd>
            <br />
            <label id="errorMsgCostEfforDetail" class="ux-msg-error-under ux-float-right" style="width:54%;"></label>
        </dl>
    </div>
</div>

<div class="ux-clear"></div>

<div class="ux-panl ux-float-left"><a name="compliance"  id="compliance"></a>
    <div class="ux-panl-header ux-panl-with-underline">
        <h2>Compliance Information </h2>
    </div>
    <div class="ux-panl-content">
        <span class="ux-float-left ux-margin-bottom-1t"> <strong>HITRUST Lookup:</strong> <input type="text" id="hitrustLookupID" value="" />
            <input type="button" value="Add" class="ux-btn-default-action" title="Add" onclick="javascript:findHitrustLookup();"/> 
            <input type="button" value="Control Lookup" class="ux-btn-default-action ux-margin-left-1t" title="Control Lookup" onclick="javascript:popup(1);"/>
        </span>
        <div class="ux-clear"></div>
        <span class="ux-float-left ux-margin-bottom-1t"><label id="hitrusterrorId" class="ux-msg-error-under"></label></span>
        <div class="ux-clear"></div>
        <script>
            $(document).ready(function() {
                $("#ciaccordion").accordion({
                    collapsible: true,
                    heightStyle: "content"
                });
            });
        </script>
        <div  id="ciaccordion" class="">
            <c:choose>
                <c:when test="${comMap.size()> 0}">
                    <c:forEach var="comMap" items="${comMap}">
                        <h3>${comMap.key} </h3>
                        <div class="ux-accordion-panl">
                                <c:set var="FamilyMapValue" value="${comMap.value}" />
                                <input type="hidden" value="${vulnerabilityDTO.dftChkHitrust}" id="notSelectedIds" name="dftChkHitrust"/>
                                <table class="ux-tabl-data valignTop" id="complianceTable">
                                    <thead>
                                        <tr>
                                            <th class="ux-padding-1t" width="5%"></th>
                                            <th class="ux-padding-1t" width="20%">Control</th>
                                            <th class="ux-padding-1t" width="40%">Control description</th>
                                            <th class="ux-padding-1t" width="20%">Objective</th>
                                            <th class="ux-padding-1t" width="15%">Family</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:set var="selCodes" value="${vulnerabilityDTO.dftChkHitrust}" />
                                        <c:forEach var="FamilyMapVar" items="${FamilyMapValue}">
                                            <tr>
                                                <td>
                                                    <input type="checkbox" class="selectedCtlCd" value="${FamilyMapVar.controlCode}" 
                                                    <c:choose>
                                                       <c:when test="${FamilyMapVar.checkedHitrust eq constants.COMPLIANCE_ACTIVE}">
                                                               checked="true"
                                                       </c:when>
                                                       <c:otherwise>
                                                               <c:if test="${FamilyMapVar.checkedHitrust eq constants.COMPLIANCE_FROM_LOOKUP && !fn:contains(selCodes, FamilyMapVar.controlCode)}">
                                                                       checked="true"
                                                               </c:if>
                                                               <c:if test="${FamilyMapVar.checkedHitrust eq constants.COMPLIANCE_FROM_CVE_ID}">
                                                                       checked="true"
                                                               </c:if>
                                                       </c:otherwise>
                                                    </c:choose>
                                                    onchange="selControlCodes()"/>
                                                </td>
                                                <td>${FamilyMapVar.controlCode}&nbsp;${FamilyMapVar.controlName}</td>
                                                <td>${FamilyMapVar.controlDesc}</td>
                                                <td>${FamilyMapVar.objectiveCode}&nbsp;${FamilyMapVar.objectiveName}</td>
                                                <td>${FamilyMapVar.familyCode}&nbsp;${FamilyMapVar.familyName}</td>

                                            </tr>
                                        </c:forEach>
                                    </tbody></table>
                        </div>
                    </c:forEach>
                </c:when>
            </c:choose>
            <c:choose>
                <c:when test="${crossWalkMap.size()> 0}">
                    <c:forEach var="crossWalkMap" items="${crossWalkMap}">
                        <h3>${crossWalkMap.key}</h3>
                        <c:set var="crossWalkMapValue" value="${crossWalkMap.value}" />
                        <div class="ux-accordion-panl">
                            <div class="submenu ux-padding-halft" >  
                                <table class="ux-tabl-data valignTop" id="complianceTable">
                                    <thead>
                                        <tr>
                                            <th class="ux-padding-1t" width="25%">Control</th>
                                            <th class="ux-padding-1t" width="40%">Control description</th>
                                            <th class="ux-padding-1t" width="20%">Objective</th>
                                            <th class="ux-padding-1t" width="15%">Family</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:choose>
                                            <c:when test="${crossWalkMapValue.size()> 0}">
                                                <c:forEach var="crossWalkMapVar" items="${crossWalkMapValue}">
                                                    <tr>
                                                        <td>${crossWalkMapVar.controlCode}&nbsp;${crossWalkMapVar.controlName}</td>
                                                        <td>${crossWalkMapVar.controlDesc}</td>
                                                        <td>${crossWalkMapVar.objectiveCode}&nbsp;${crossWalkMapVar.objectiveName}</td>
                                                        <td>${crossWalkMapVar.familyCode}&nbsp;${crossWalkMapVar.familyName}</td>
                                                    </tr>
                                                </c:forEach>
                                            </c:when>
                                        </c:choose>

                                    </tbody></table>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise></c:otherwise>
            </c:choose>
        </div>
    </div>
</div>