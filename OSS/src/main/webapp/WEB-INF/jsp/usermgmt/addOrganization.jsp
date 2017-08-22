<%-- 
    Document   : addorganization
    Created on : Apr 14, 2016, 4:23:06 PM
    Author     : rpanuganti
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<%@taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld"%>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="constants" />
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>IRMaaS</title>
        <!-- CSS : Include -->
        <jsp:include page="../includes/commoncss.jsp"/>
        <!-- CSS : Include -->
        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/appjs/addOrganization.js"/>"></script>
        <!-- JS : Include -->  

    </head>
    <BODY >
        <form name="addorganizationfrm" method="post" hidden></form>
        <!-- Start: Wrapper -->
        <div id="ux-wrapper"> 

            <!--Header starts here-->
            <jsp:include page="../includes/header.jsp"/>
            <!--Header ends here--> 

            <!-- Begin: Content -->

            <!-- Start: Breadcrumbs -->
            <ul class="ux-brdc ux-margin-left-1t" >

                <li><a href="#" onclick="headerFn('organizationWorkList.htm')" title="Manage Organizations">Manage Organizations</a></li>
                    <c:choose>
                        <c:when test="${organizationDetails.organizationKey>0}">
                        <li> Edit Organization</li>
                        </c:when>
                        <c:otherwise>
                        <li> Add Organization</li>
                        </c:otherwise>
                    </c:choose>
            </ul>
            <!-- End: Breadcrumbs -->

            <!--Left navigation starts here-->
            <jsp:include page="../includes/leftpanel.jsp"/>
            <!--Left navigation ends here--> 

            <!-- content starts -->

            <div class="ux-content">

                <spring:form name="saveOrganization"  commandName="orgobj" method="POST">

                    <c:choose>
                        <c:when test="${fn:length(organizationDetails.encOrganizationKey)>0}">
                            <h1>Edit Organization <div class="ux-float-right ">  
                                    <input name="" type="button" value="Update" title="Update" id="updateOrganization" class="ux-btn-default-action"  />  
                                </c:when>
                                <c:otherwise>
                                    <h1>Add Organization ${organizationDetails.encOrganizationKey}<div class="ux-float-right ">  
                                            <input name="" type="button" value="Save" title="Save" id="saveOrganization" class="ux-btn-default-action "  /> 
                                        </c:otherwise>
                                    </c:choose>
                                    <input name="" type="button" value="Cancel" title="Cancel" class="ux-btn-default-action " onclick="cancelOrganization()"/>
                                </div></h1>
                            <div class="ux-panl ux-float-left">
                                <div class="ux-panl-header ux-panl-with-underline">
                                    <h2>Organization Details</h2>
                                </div>
                                <div class="ux-panl-content ux-padding-top-none">
                                    <div class="ux-hform ">
                                        <p class="ux-form-required">
                                            <strong class="ux-labl-required">*</strong> Required
                                        </p>


                                        <div >
                                            <input name="encOrganizationKey" type="hidden" value="${organizationDetails.encOrganizationKey}"/>
                                            <input name="encOrganizationName" type="hidden" value="${organizationDetails.encOrganizationName}"/>
                                            <input name="organizationType" id="organizationTypeName" type="hidden" value=""/>

                                            <c:choose>
                                                <c:when test="${organizationDetails.organizationKey>0}">
                                                    <dl>
                                                        <dt><label>ID: </label>  ${organizationDetails.organizationKey}</dt>
                                                        <dd>
                                                        </dd>
                                                    </dl>
                                                </c:when>
                                                <c:otherwise>

                                                </c:otherwise>
                                            </c:choose> 
                                            <div class="ux-clear"></div>
                                            <dl>
                                                <dt><label>Parent Organization Name:</label> </dt>
                                                <dd> <select name="parentOrganizationKey" id="parentOrganizationKey">
                                                        <option value="0" title="Select">Select</option>

                                                        <c:forEach items="${organizationsList}" var="organizationsList">
                                                            <c:if test="${organizationsList.organizationKey != 1 && organizationsList.organizationKey != organizationDetails.organizationKey}">
                                                                <option title="${organizationsList.organizationName}" value="${organizationsList.organizationKey}" <c:if test="${organizationsList.organizationKey == organizationDetails.parentOrganizationKey}">selected</c:if> >${organizationsList.organizationName}</option>
                                                            </c:if>
                                                        </c:forEach>  

                                                    </select> 
                                                </dd>
<!--                                                <dd><input type="text" name="parentOrganizationName" id="parentOrganizationName" value="${organizationDetails.parentOrganizationName}" />
                                                   <label id="errorParentOrganizationName" class="error" style="color: red;float:left"></label> 
                                               </dd>-->
                                            </dl>
                                            <dl>
                                                <dt><label>Organization Name:</label> <span>*</span></dt>
                                                <dd><input type="text" name="organizationName" id="organizationName" value="${organizationDetails.organizationName}"/>
                                                    <label id="errorOrganizationName" class="error" style="color: red;float:left"></label> 
                                                    <spring:errors cssClass="error"  path="organizationName" cssStyle="color: red; display: block; font-weight: bold"/>
                                                </dd>
                                            </dl>
                                            <dl>
                                                <dt><label>Organization Type:</label> <span>*</span></dt>
                                                <dd>
                                                    <select name="organizationTypeKey" id="organizationTypeKey" <c:if test="${organizationDetails.organizationKey >0}"> disabled</c:if>>
                                                            <option value="0" title="Select">Select</option>
                                                        <c:forEach items="${organizationTypeList}" var="organizationType">
                                                            <option title="${organizationType.lookUpEntityName}" value="${organizationType.masterLookupKey}" <c:if test="${organizationDetails.organizationTypeObj.masterLookupKey==organizationType.masterLookupKey || organizationType.masterLookupKey==organizationDetails.organizationTypeKey}">selected</c:if>>${organizationType.lookUpEntityName}</option>
                                                        </c:forEach>  
                                                    </select>
                                                    <label id="errorOrganizationTypeKey" class="error" style="color: red;float:left"></label> 
                                                    <spring:errors cssClass="error"  path="organizationTypeKey" cssStyle="color: red; display: block; font-weight: bold"/>
                                                </dd>
                                            </dl>
                                            <dl>
                                                <dt><label>Organization Industry:</label> <span>*</span></dt>
                                                <dd>
                                                    <select name="organizationIndustryKey" id="organizationIndustryKey">
                                                        <option value="0" title="Select">Select</option>
                                                        <c:forEach items="${organizationIndustryList}" var="organizationIndustry">
                                                            <option title="${organizationIndustry.lookUpEntityName}" value="${organizationIndustry.masterLookupKey}" <c:if test="${organizationDetails.organizationIndustryObj.masterLookupKey==organizationIndustry.masterLookupKey || organizationIndustry.masterLookupKey==organizationDetails.organizationIndustryKey}">selected</c:if>>${organizationIndustry.lookUpEntityName}</option>
                                                        </c:forEach>  
                                                    </select>
                                                    <label id="errorOrganizationIndustryKey" class="error" style="color: red;float:left"></label> 
                                                    <spring:errors cssClass="error"  path="organizationIndustryKey" cssStyle="color: red; display: block; font-weight: bold"/>
                                                </dd>
                                            </dl>
                                            <div class="ux-clear"></div>
                                            <dl>
                                                <dt><label>Country:</label> <span>*</span></dt>
                                                <dd><select name="countryName" id="countryName" onchange="$.fn.fetchStatesByCountry(this.value)">
                                                        <option value="" title="Select">Select</option>
                                                        <c:forEach items="${countriesList}" var="countries">
                                                            <option title="${countries}" value="${countries}" <c:if test="${countries==organizationDetails.countryName}">selected</c:if>>${countries}</option>
                                                        </c:forEach>
                                                    </select>
                                                    <label id="errorCountryName" class="error" style="color: red;float:left"></label> 
                                                    <spring:errors cssClass="error"  path="countryName" cssStyle="color: red; display: block; font-weight: bold"/>
                                                </dd>
                                            </dl>

                                            <dl>
                                                <dt><label>Street Address Line 1:</label> <span>*</span> </dt>
                                                <dd>
                                                    <input type="text" value="${organizationDetails.streetAddress1}" name="streetAddress1" id="streetAddress1"/>
                                                    <label id="errorStreetAddress1" class="error" style="color: red;float:left"></label> 
                                                    <spring:errors cssClass="error"  path="streetAddress1" cssStyle="color: red; display: block; font-weight: bold"/>
                                                </dd>
                                            </dl>

                                            <dl>
                                                <dt><label>Street Address Line 2:</label>  </dt>
                                                <dd>
                                                    <input type="text" value="${organizationDetails.streetAddress2}" name="streetAddress2" id="streetAddress2"/>
                                                    <label id="errorStreetAddress2" class="error" style="color: red;float:left"></label> 
                                                    <spring:errors cssClass="error"  path="streetAddress2" cssStyle="color: red; display: block; font-weight: bold"/>
                                                </dd>
                                            </dl>

                                            <dl>
                                                <dt><label>City:</label> <span>*</span> </dt>
                                                <dd>
                                                    <input type="text" value="${organizationDetails.cityName}" name="cityName" id="cityName"/>
                                                    <label id="errorCityName" class="error" style="color: red;float:left"></label> 
                                                    <spring:errors cssClass="error"  path="cityName" cssStyle="color: red; display: block; font-weight: bold"/>
                                                </dd>
                                            </dl>
                                            <div class="ux-clear"></div>
                                            <dl>
                                                <dt><label>State:</label> <span>*</span></dt>
                                                <dd>
                                                    <c:choose>
                                                        <c:when test="${organizationDetails.organizationKey>0}">
                                                            <select name="stateName" id="stateName">
                                                                <option value="" title="Select">Select</option>
                                                                <c:forEach items="${statesList}" var="states">
                                                                    <option value="${states}" title="${states}" <c:if test="${states==organizationDetails.stateName}">selected</c:if>>${states}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <select name="stateName" id="stateName">
                                                                <option value="" title="Select">Select</option>
                                                                <c:forEach items="${statesList}" var="states">
                                                                    <option title="${states}" value="${states}" <c:if test="${states==organizationDetails.stateName}">selected</c:if> >${states}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </c:otherwise>
                                                    </c:choose>

                                                    <label id="errorStateName" class="error" style="color: red;float:left"></label> 
                                                    <spring:errors cssClass="error"  path="stateName" cssStyle="color: red; display: block; font-weight: bold"/>
                                                </dd>
                                            </dl>


                                            <dl>
                                                <dt><label>Zip Code:</label> <span>*</span></dt>
                                                <dd>
                                                    <input type="text" name="postalCode" id="postalCode" value="${organizationDetails.postalCode}"/>
                                                    <label id="errorPostalCode" class="error" style="color: red;float:left"></label> 
                                                    <spring:errors cssClass="error"  path="postalCode" cssStyle="color: red; display: block; font-weight: bold"/>
                                                </dd>
                                            </dl>
                                            <dl >
                                                <dt><label> Notes:</label> </dt>
                                                <dd>
                                                    <textarea name="organizationDescription" id="organizationDescription">${organizationDetails.organizationDescription}</textarea>
                                                    <label id="errorOrganizationDescription" class="error" style="color: red;float:left"></label> 
                                                    <spring:errors cssClass="error"  path="organizationDescription" cssStyle="color: red; display: block; font-weight: bold"/>
                                                </dd>
                                            </dl>

                                            <dl>
                                                <dt><label>Status:</label>  <span>*</span></dt>
                                                <dd>
                                                    <c:choose>
                                                        <c:when test="${organizationDetails.organizationKey>0}">
                                                            <c:forEach items="${rowStatusList}" var="rowStatus" varStatus="status">
                                                                <c:if test="${rowStatus.masterLookupKey!=constants.DB_ROW_STATUS_DELETE_VALUE}">
                                                                    <input name="rowStatusKey" type="radio" id="status${rowStatus.masterLookupKey}" type="radio" value="${rowStatus.masterLookupKey}" <c:if test="${organizationDetails.rowStatusKey == rowStatus.masterLookupKey}">checked="true"</c:if> /> ${rowStatus.lookUpEntityName}
                                                                </c:if>
                                                            </c:forEach> 
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:forEach items="${rowStatusList}" var="rowStatus" varStatus="status">
                                                                <c:if test="${rowStatus.masterLookupKey!=constants.DB_ROW_STATUS_DELETE_VALUE}">
                                                                    <input name="rowStatusKey" type="radio" id="status${rowStatus.masterLookupKey}" type="radio" value="${rowStatus.masterLookupKey}" <c:if test="${rowStatus.masterLookupKey==constants.DB_ROW_STATUS_ACTIVE_VALUE || organizationDetails.rowStatusKey == constants.DB_ROW_STATUS_DE_ACTIVE_VALUE}">checked="true"</c:if> /> ${rowStatus.lookUpEntityName}
                                                                </c:if>
                                                            </c:forEach> 
                                                        </c:otherwise>
                                                    </c:choose>

                                                    <label id="errorStatus" class="error" style="color: red;float:left"></label> 
                                                </dd>
                                            </dl>
                                            <dl class="LongTxt" id="reason" <c:if test="${organizationDetails.rowStatusKey == 0 || organizationDetails.rowStatusKey == constants.DB_ROW_STATUS_ACTIVE_VALUE}">style="display:none;"</c:if>>
                                                    <dt >
                                                        <label>Reason for Deactivation: <span>*</span> </label>
                                                    </dt>
                                                    <dd>
                                                        <textarea class="TextareaLong ux-height-5t" id="deactiveReason" name="deactiveReason">${organizationDetails.deactiveReason}</textarea>
                                                    <label id="errordeactiveReason" class="error" style="color: red;float:left"></label> 
                                                    <spring:errors cssClass="error"  path="deactiveReason" cssStyle="color: red; display: block; font-weight: bold"/>
                                                </dd>
                                            </dl>
                                        </div>

                                    </div>
                                </div>

                            </div>
                            <div class="ux-panl ux-float-left client" >
                                <div class="ux-panl-header ux-panl-with-underline">
                                    <h2>Source Information</h2>
                                </div>

                                <div class="ux-panl-content ux-margin-top-1t" id="sourceValidationID">
                                    <dt>
                                        <label id="errorGlobalAothoritativeSource" class="error" style="color: red;float:left"></label>
                                        <spring:errors cssClass="error"  path="sourceKeyArray[00]" cssStyle="color: red; display: block; font-weight: bold"/>
                                    </dt>
                                    <!--<dt><label id="errorGlobalAothoritativeSourceDP" class="error" style="color: red;float:left"></label></dt>-->
                                    <div class="ux-hform">

                                        <c:choose>
                                            <c:when test="${organizationDetails.organizationKey>0}">
                                                <c:set value="${fn:length(organizationDetails.organizationSourceListObj)}" var="len">  </c:set>
                                                <input type="hidden" value="${len}" id="listSize"/>
                                                <c:forEach items="${organizationDetails.organizationSourceListObj}" var="sourceList" varStatus="count">
                                                    <c:choose>
                                                        <c:when test="${count.index==0}">
                                                            <dl>
                                                                <dt><label>Authoritative Source:</label> <span>*</span> </dt>
                                                                <dd>
                                                                    <select name="sourceKeyArray" id="authorativeSourceKey${count.index}">
                                                                        <option value="0" title="Select">Select</option>
                                                                        <c:forEach items="${authoritativeSourceList}" var="authoritativeSource">
                                                                            <option title="${authoritativeSource.lookUpEntityName}" value="${authoritativeSource.masterLookupKey}" <c:if test="${authoritativeSource.masterLookupKey==sourceList.sourceKey}">selected</c:if>>${authoritativeSource.lookUpEntityName}</option>
                                                                        </c:forEach> 
                                                                    </select>
                                                                    <label id="errorauthorativeSourceKey${count.index}" class="error" style="color: red;float:left"></label> 
                                                                    <span></span>
                                                                    <div class="sourceText ux-margin-top-min" id="sourceText${count.index}"></div>
                                                                </dd>
                                                            </dl>
                                                            <dl>
                                                                <dt><label>Source Organization ID:</label>  </dt>
                                                                <dd><input type="text" value="${sourceList.sourceClientID}" name="sourceClientIDArray" id="sourceOrganizationKey${count.index}"/>
                                                                    <label id="errorsourceOrganizationKey${count.index}" class="error" style="color: red;float:left"></label> 
                                                                </dd>
                                                            </dl>


                                                            <dl>
                                                                <dt>&nbsp;</dt>
                                                                <c:if test="${count.index==0}">
                                                                    <dd><a href="#" class="add" title="Add Additional Source">+ Add Additional Source</a></dd>
                                                                </c:if>
                                                            </dl>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div class='labeltype ux-hform ux-margin-top-none ux-margin-left-none' id='label${count.index}'>  
                                                                <dl><dt></dt><dd>
                                                                        <select name='sourceKeyArray' id='authorativeSourceKey${count.index}' >
                                                                            <option value="0" title="Select">Select</option>
                                                                            <c:forEach items="${authoritativeSourceList}" var="authoritativeSource">
                                                                                <option title="${authoritativeSource.lookUpEntityName}" value="${authoritativeSource.masterLookupKey}" <c:if test="${authoritativeSource.masterLookupKey==sourceList.sourceKey}">selected</c:if>>${authoritativeSource.lookUpEntityName}</option>
                                                                            </c:forEach> 
                                                                        </select>
                                                                        <label id="errorauthorativeSourceKey${count.index}" class="error" style="color: red;float:left"></label> 
                                                                        <div class='sourceText ux-margin-top-min' id='sourceText${count.index}'></div>
                                                                    </dd>

                                                                </dl>
                                                                <div class='sourceTextLabel${count.index}' id='sourceTextID${count.index}'></div>
                                                                <dl><dt></dt><dd><input type='text' name='sourceClientIDArray' id='sourceOrganizationKey${count.index}' value='${sourceList.sourceClientID}' placeholder=''/>
                                                                        <label id="errorsourceOrganizationKey${count.index}" class="error" style="color: red;float:left"></label> 
                                                                    </dd>
                                                                </dl>

                                                                <div class='remove ux-float-left ux-margin-top-min'><a href='javascript:$.fn.removeAuthorotativeSource (${count.index})'><img src='images/Delete_c.png' /></a>
                                                                </div>    
                                                            </div>
                                                        </c:otherwise>

                                                    </c:choose></c:forEach>

                                            </c:when>
                                            <c:otherwise>
                                                <div class="ux-hform">
                                                    <!--<input type="hidden" value="1" id="listSize"/>-->
                                                    <input type="hidden" value="${fn:length(organizationDetails.sourceKeyArray)+1}" id="listSize"/>
                                                      <c:set var="elen" value="0"/>
                                                      <c:if test="${fn:length(organizationDetails.sourceKeyArray)> 0}">
                                                        <c:set var="elen" value="${fn:length(organizationDetails.sourceKeyArray)-1}"/>
                                                    </c:if>
                                                    
                                                    <c:forEach begin="0" end="${elen}" varStatus="count">
                                                        <c:choose>
                                                            <c:when test="${count.index == 0}">
                                                                <dl>
                                                                    <dt><label>Authoritative Source:</label> <span>*</span> </dt>
                                                                    <dd>
                                                                        <select name="sourceKeyArray" id="authorativeSourceKey0">
                                                                            <option value="0" selected title="Select">Select</option>
                                                                            <c:forEach items="${authoritativeSourceList}" var="authoritativeSource">
                                                                                <option title="${authoritativeSource.lookUpEntityName}" value="${authoritativeSource.masterLookupKey}" <c:if test="${authoritativeSource.masterLookupKey == organizationDetails.sourceKeyArray[count.index]}"> selected</c:if>>${authoritativeSource.lookUpEntityName}</option>
                                                                            </c:forEach> 
                                                                        </select>
                                                                        <label id="errorauthorativeSourceKey0" class="error" style="color: red;float:left"></label> 
                                                                         <spring:errors cssClass="error"  path="sourceKeyArray[${count.index}]" cssStyle="color: red; display: block; font-weight: bold"/>
                                                                        <span></span>
                                                                        <div class="sourceText ux-margin-top-min" id="sourceText0"></div> 
                                                                    </dd>
                                                                </dl>



                                                                <dl>
                                                                    <dt><label>Source Organization ID:</label>  </dt>
                                                                    <dd><input type="text" value="${organizationDetails.sourceClientIDArray[count.index]}" name="sourceClientIDArray" id="sourceOrganizationKey0"/>
                                                                        <label id="errorsourceOrganizationKey0" class="error" style="color: red;float:left"></label> 
                                                                         <spring:errors cssClass="error"  path="sourceClientIDArray[${count.index}]" cssStyle="color: red; display: block; font-weight: bold"/>
                                                                    </dd>
                                                                </dl>

                                                                <dl>
                                                                    <dt>&nbsp;</dt>
                                                                    <dd><a href="#" class="add" title="Add Additional Source">+ Add Additional Source</a></dd>
                                                                </dl>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <div class='labeltype ux-hform ux-margin-top-none ux-margin-left-none' id='label${count.index}'>  
                                                                    <dl>
                                                                        <dt> </dt>
                                                                        <dd>
                                                                            <select name="sourceKeyArray" id="authorativeSourceKey${count.index}">
                                                                                <option value="0" selected title="Select">Select</option>
                                                                                <c:forEach items="${authoritativeSourceList}" var="authoritativeSource">
                                                                                    <option title="${authoritativeSource.lookUpEntityName}" value="${authoritativeSource.masterLookupKey}" <c:if test="${authoritativeSource.masterLookupKey == organizationDetails.sourceKeyArray[count.index]}"> selected</c:if>>${authoritativeSource.lookUpEntityName}</option>
                                                                                </c:forEach> 
                                                                            </select>
                                                                            <label id="errorauthorativeSourceKey${count.index}" class="error" style="color: red;float:left"></label> 
                                                                            <spring:errors cssClass="error"  path="sourceKeyArray[${count.index}]" cssStyle="color: red; display: block; font-weight: bold"/>
                                                                            <span></span>
                                                                            <div class="sourceText ux-margin-top-min" id="sourceText${count.index}"></div> 
                                                                        </dd>
                                                                    </dl>

                                                                    <dl>
                                                                        <dt></dt>
                                                                        <dd><input type="text" value="${organizationDetails.sourceClientIDArray[count.index]}" name="sourceClientIDArray" id="sourceOrganizationKey${count.index}"/>
                                                                            <label id="errorsourceOrganizationKey0" class="error" style="color: red;float:left"></label> 
                                                                            <spring:errors cssClass="error"  path="sourceClientIDArray[${count.index}]" cssStyle="color: red; display: block; font-weight: bold"/>
                                                                        </dd>
                                                                    </dl>
                                                                    <div class='remove ux-float-left ux-margin-top-min'><a href='javascript:$.fn.removeAuthorotativeSource (${count.index})'><img src='images/Delete_c.png' /></a>
                                                                    </div>  
                                                                </div>
                                                            </c:otherwise>

                                                        </c:choose>
                                                    </c:forEach>

                                                </c:otherwise>
                                            </c:choose>

                                            <div class="labeltype" id="label">

                                            </div>
                                        </div>
                                    </div>

                                </div>

                            </div>
                        </div>
                    </spring:form>
            </div>
            <!-- content ends --> 

            <!-- Begin: Footer -->
            <jsp:include page="../includes/footer.jsp"/>
            <!-- End: Footer --> 
        </div>
        <!-- End: Wrapper --> 

    </BODY>
</html>
