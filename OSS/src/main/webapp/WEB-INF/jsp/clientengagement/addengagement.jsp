<%-- 
    Document   : updateengagement
    Created on : May 9, 2016, 6:57:47 PM
    Author     : sbhagavatula
--%>

<%@taglib  uri="/WEB-INF/tlds/spring-form.tld" prefix="form" %>
<%@taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="constants" />
<un:useConstants className="com.optum.oss.constants.PermissionConstants" var="prmnsConstants" />
<!DOCTYPE html>
<html>
    <HEAD>
        <title>IRMaaS</title>
        <!-- CSS : Include -->
        <jsp:include page="../includes/commoncss.jsp"/>
        <link rel="stylesheet" href="<c:url value="/appcss/Editengagement.css"/>"/>
        <!-- CSS : Include -->
        <!-- JS : Include -->
        <script src="js/nicEdit.js" type="text/javascript"></script>
        <jsp:include page="../includes/commonjs.jsp"/>

        <script type="text/javascript" src="<c:url value="/appjs/addengagement.js"/>"></script>
        
        <!-- JS : Include -->
        <c:set var="userType" value="${constants.DB_USER_TYPE_ADMIN}"/>
        <c:if test="${sessionScope.USER_SESSION != null}">
            <c:set var="userProfile" value="${sessionScope.USER_SESSION}"/>
            <c:set var="userType" value="${userProfile.userTypeObj.lookUpEntityName}"/>
        </c:if>
        <style>
            .ux-panl{
                display: inline-block;
            }
        </style>
    </HEAD>
    <BODY >
        <c:set var="titleVal" value=""/>
        <c:choose>
            <c:when test="${redirectPage == 'updatepage'}">
                <c:set var="titleVal" value="Update Engagement"/>
            </c:when>
            <c:otherwise>
                <c:set var="titleVal" value="Add Engagement"/>
            </c:otherwise>
        </c:choose>
        <!-- Start: Wrapper -->
        <div id="ux-wrapper"> 

            <!-- Header : Include -->
            <jsp:include page="../includes/header.jsp"/>
            <!-- Header : Include -->

            <!-- Start: Breadcrumbs -->
            <ul class="ux-brdc ux-margin-left-1t" >
                <li><a title="Client Engagement" href="#">Client Engagement</a></li>
                <li><a title="Manage Engagements" href="#" onclick="headerFn('engagementlist.htm')">Manage Engagements</a></li>
                <li><c:out value="${titleVal}"/></li>
            </ul>
            <!-- End: Breadcrumbs -->

            <!-- Left Panel : Include -->
            <jsp:include page="../includes/leftpanel.jsp"/>
            <!-- Left Panel : Include --> 
            <!-- Popup Starts -->
            <div id='blackcover'> </div>
            <div id='popupbox' class="ux-modl">
                <div class="ux-modl-header">
                    <a href="#" onclick='popup(0)' title="Close">Close</a>
                    <h6>Notification</h6>
                </div>

                <div class="ux-modl-content">
                    <div align="center">
                        <h3>Are you sure you want to delete the engagement?</h3>
                    </div>
                    <div class="ux-margin-1t  " align="center">
                        <input type="button" title="OK" value="OK"  class="ux-btn-default-action ux-width-7t ux-btn-default-action delete" id="msgshow"/>
                        <input type="button" title="Cancel" value="Cancel" class="ux-btn-default-action ux-width-7t" onclick='popup(0)' /> 
                    </div>
                </div>
            </div>
            <!-- Popup Ends -->
            <c:if test="${engStatusMessage eq 'Active' || engStatusMessage eq 'Inactive'}">
            <div id='blackcover'> </div>
            <div id='popupbox1' class="ux-modl" hidden>
                <div class="ux-modl-header">
                    <a href="#" onclick='popupEngagement(0)' title="Close">Close</a>
                    <h6>Notification</h6>
                </div>

                <div class="ux-modl-content">
                    <div align="center">
                        <h3>
                            <c:if test="${engStatusMessage eq 'Active'}">
                                Multiple engagements cannot be created for the selected information.
                                An engagement already exists for ${updateClientDTO.clientName} with ${updateClientDTO.securityPackageObj.securityPackageName} 
                                on ${updateClientDTO.agreementDate} with engagement code ${updateClientDTO.engagementCode}. 
                                Would you like to modify the details?
                            </c:if>
                            <c:if test="${engStatusMessage eq 'Inactive'}">
                                Multiple engagements cannot be created for the selected information.
                                An engagement already exists for ${updateClientDTO.clientName} with ${updateClientDTO.securityPackageObj.securityPackageName} 
                                on ${updateClientDTO.agreementDate} with engagement code ${updateClientDTO.engagementCode}. 
                                Would you like to activate it?
                            </c:if>
                        </h3>
                    </div>
                    <div class="ux-margin-1t  " align="center">
                        <c:if test="${engStatusMessage eq 'Active'}">
                            <input type="button" title="OK" value="OK"  class="ux-btn-default-action ux-width-7t ux-btn-default-action edit-engagement" onclick='popupEngagement(0)'/>
                        </c:if>
                        <c:if test="${engStatusMessage eq 'Inactive'}">
                            <input type="button" title="OK" value="OK"  class="ux-btn-default-action ux-width-7t ux-btn-default-action eng-activate"/>
                        </c:if>
                        <input type="button" title="Cancel" value="Cancel" class="ux-btn-default-action ux-width-7t" onclick='popupEngagement(0)' />
                    </div>
                </div>
            </div>
            </c:if>
            <form action="" name="activateform" method="GET" hidden>
                <input type="hidden" value="${updateClientDTO.encEngagementCode}" name="cei"/>
            </form>
            <!-- content starts -->
            <div class="ux-content">
                <h1><c:out value="${titleVal}"/>

                    <div class="ux-float-right ">
                        <!-- Check for permissions and display respective action -->
                        <c:choose>
                            <c:when test="${redirectPage == 'updatepage'}">
                                <c:if test="${permissions.contains(prmnsConstants.DELETE_ENGAGEMENT)}">
                                    <input name="" type="button" value="Delete" title="Delete" class="ux-btn-default-action deleteEngmtBtn"/>
                                </c:if>

                                <c:if test="${permissions.contains(prmnsConstants.EDIT_ENGAGEMENT)}">
                                    <input name="" type="button" value="Save" title="Save" class="ux-btn-default-action updateEngmtBtn"/>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${permissions.contains(prmnsConstants.ADD_ENGAGEMENT)}">
                                    <input name="" type="button" value="Save" title="Save" class="ux-btn-default-action addEngmtBtn"/>
                                </c:if>
                            </c:otherwise>
                        </c:choose>

                        <input name="" type="button" value="Cancel" title="Cancel" class="ux-btn-default-action cancelbtn"/>
                    </div>
                </h1>
                
                <c:if test="${engStatusMessage != null}">
                    <div class="ux-msg-success ux-margin-bottom-min" id="msg">${engStatusMessage}</div>
                </c:if>
                    
                        <c:if test="${engStatusMessage != null}">
                            <script type="text/javascript">
                                popupEngagement(1);
                            </script>
                        </c:if>
                <form:form action="" name="saveEngagementForm"  commandName="saveEngagement" method="POST">
                    
                    <div class="ux-panl ">
                        <div class="ux-panl-header ux-panl-with-underline">
                            <h2>Engagement Information ${engStatusMessage}</h2>
                        </div>
                        <div class="ux-panl-content ux-padding-top-1t">
                            <div class="ux-FormStyleLeftAlign">
                                <div class="ux-clear"></div>
                                <p class="ux-form-required">
                                    <strong class="ux-labl-required ">*</strong> Required
                                </p>
                                <c:if test="${redirectPage == 'updatepage'}">
                                    <input type="hidden" value="${updateClientDTO.clientEngagementKey}" name="clientEngagementKey"/>
                                </c:if>
                                <c:set value="${fn:length(updateClientDTO.clientEngagementSourceLi)}" var="sourceLength"/>
                                <input type="hidden" value="${sourceLength}" id="sourceSize"/>
                                <c:choose>
                                    <c:when test="${sourceLength == 0}">
                                        <dl>
                                            <dt>
                                            <label>Source System 1: <span>*</span></label>
                                            </dt>
                                            <dd>
                                                <select class="ux-width-33t sourceSel" id="ss0" name="clientEngagementSourceLi[0].sourceKey">
                                                    <option value="0">Select</option>
                                                    <c:forEach items="${srcSystems}" var="masterDTO">
                                                        <option value="${masterDTO.masterLookupKey}">${masterDTO.lookUpEntityName}</option>
                                                    </c:forEach>
                                                </select>
                                                <!--Bug Id: IN020885 (Added tool tip)-->
                                                <a href="#" class="addsource" title="Add Source">+ Add Source </a><br />
                                                <label id="errorSrcSystemID0" class="ux-msg-error-under"></label>
                                                <form:errors path="clientEngagementSourceLi[0].sourceKey" cssStyle="color: red;width:190px; font-weight: bold"/>
                                            </dd>
                                        </dl>
                                        <dl>
                                            <dt>
                                            <label>Source Project ID 1: <span>*</span></label>
                                            </dt>
                                            <dd>
                                                <input type="text" value="" id="sp0" class="ux-width-32t" name="clientEngagementSourceLi[0].clientEngSourceCode" onkeyup="limiter(this, 100)"></input><br />
                                                <label id="errorSrcProjID0" class="ux-msg-error-under"></label>
                                                <form:errors path="clientEngagementSourceLi[0].clientEngSourceCode" cssStyle="color: red;width:190px; font-weight: bold"/>
                                            </dd>

                                        </dl>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="systemCount" value="1"/>
                                        <c:set var="sysSrcCount" value="0"/>
                                        <c:forEach items="${updateClientDTO.clientEngagementSourceLi}" var="clientEngagementSourceDTO">
                                            <div id="source${systemCount}">
                                                <dl>
                                                    <dt>
                                                    <label>Source System ${systemCount}: <span>*</span></label>
                                                    </dt>
                                                    <dd>
                                                        <select class="ux-width-33t sourceSel" id="ss${sysSrcCount}" name="clientEngagementSourceLi[${sysSrcCount}].sourceKey">
                                                            <option value="0">Select</option>
                                                            <c:forEach items="${srcSystems}" var="masterDTO">
                                                                <c:choose>
                                                                    <c:when test="${clientEngagementSourceDTO != null && clientEngagementSourceDTO.sourceKey eq masterDTO.masterLookupKey}">
                                                                        <option value="${masterDTO.masterLookupKey}" selected>${masterDTO.lookUpEntityName}</option>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <option value="${masterDTO.masterLookupKey}">${masterDTO.lookUpEntityName}</option>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:forEach>
                                                        </select> 
                                                        <c:if test="${sysSrcCount!= 0}">
                                                            <a href='javascript:$.fn.removeSourceSystem (${systemCount});'><img src='images/Delete_c.png' title='Delete'/></a>
                                                            </c:if>
                                                            <c:if test="${sysSrcCount== 0}">
                                                            <!--Fixed for Bug Id: IN020885 (Added tool tip)-->
                                                            <a href="#" class="addsource" title="Add Source">+ Add Source </a>
                                                        </c:if>
                                                        <br/><label id="errorSrcSystemID${sysSrcCount}" class="ux-msg-error-under"></label>
                                                        <form:errors path="clientEngagementSourceLi[${sysSrcCount}].sourceKey" cssStyle="color: red;width:190px; font-weight: bold"/>
                                                    </dd>
                                                </dl>
                                                <dl>
                                                    <dt>
                                                    <label>Source Project ID ${systemCount}: <span>*</span></label>
                                                    </dt>
                                                    <dd>
                                                        <input type="text" id="sp${sysSrcCount}" value="${clientEngagementSourceDTO.clientEngSourceCode}" class="ux-width-32t" name="clientEngagementSourceLi[${sysSrcCount}].clientEngSourceCode" onkeyup="limiter(this, 100, '', '')"></input> 
                                                        <br/><label id="errorSrcProjID${sysSrcCount}" class="ux-msg-error-under"></label>
                                                        <form:errors path="clientEngagementSourceLi[${sysSrcCount}].clientEngSourceCode" cssStyle="color: red;width:190px; font-weight: bold"/>
                                                    </dd>
                                                </dl>
                                            </div>
                                            <c:set var="systemCount" value="${systemCount+1}"/>
                                            <c:set var="sysSrcCount" value="${sysSrcCount+1}"/>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                                <div class="sourceid">

                                </div>
                                <dl>
                                    <dt>
                                    <label>Client Name: <c:if test="${redirectPage != 'updatepage'}"><span>*</span></c:if></label>
                                        </dt>
                                        <dd>
                                        <c:choose>
                                            <c:when test="${redirectPage == 'updatepage'}">
                                                ${updateClientDTO.clientName}
                                            </c:when>
                                            <c:otherwise>
                                                <select class="ux-width-33t" name="clientID" id="clientID" onchange="clientEngagementCode()">
                                                    <option value="0">Select</option>
                                                    <c:forEach items="${clientOrgList}" var="clientOrgDTO">
                                                        <c:choose>
                                                            <c:when test="${updateClientDTO != null && clientOrgDTO.organizationKey eq updateClientDTO.clientID}">
                                                                <option value="${clientOrgDTO.organizationKey}" selected>${clientOrgDTO.organizationName}</option>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <option value="${clientOrgDTO.organizationKey}">${clientOrgDTO.organizationName}</option>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                                </select>
                                            </c:otherwise>
                                        </c:choose>
                                        <br/><label id="errorClientID" class="ux-msg-error-under"></label>
                                        <form:errors path="clientID" cssStyle="color: red;width:190px; font-weight: bold"/>
                                    </dd>
                                </dl>
                                <dl>
                                    <dt>
                                    <label>Product: <c:if test="${redirectPage != 'updatepage'}"><span>*</span></c:if></label>
                                        </dt>
                                        <dd>
                                        <c:choose>
                                            <c:when test="${redirectPage == 'updatepage'}">
                                                ${updateClientDTO.securityPackageObj.securityPackageName}
                                            </c:when>
                                            <c:otherwise>
                                                <select id="product" name="securityPackageObj.securityPackageCode" 
                                                        <c:choose>
                                                            <c:when test="${validationFlag eq 'valid'}">
                                                                onchange="javascript:$.fn.fnForPrdChangeValid()"
                                                            </c:when>
                                                            <c:otherwise>
                                                                onchange="javascript:$.fn.fnForPrdChange()"
                                                            </c:otherwise>
                                                        </c:choose>
                                                        
                                                        >
                                                    <option value="0" selected>Select</option>
                                                    <c:forEach items="${securityPkgs}" var="packageDTO">
                                                        <c:choose>
                                                            <c:when test="${updateClientDTO != null && packageDTO.securityPackageCode eq updateClientDTO.securityPackageObj.securityPackageCode}">
                                                                <option value="${packageDTO.securityPackageCode}" selected>${packageDTO.securityPackageName}</option>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <option value="${packageDTO.securityPackageCode}">${packageDTO.securityPackageName}</option>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                                </select>
                                            </c:otherwise>
                                        </c:choose>
                                        <br/><label id="errorproduct" class="ux-msg-error-under"></label>
                                        <form:errors path="securityPackageObj.securityPackageCode" cssStyle="color: red;width:190px; font-weight: bold"/>
                                    </dd>
                                </dl>
                                <dl>
                                    <dt>
                                    <label>Agreement Date: <c:if test="${redirectPage != 'updatepage'}"><span>*</span></c:if></label>
                                        </dt>
                                        <dd>
                                        <c:choose>
                                            <c:when test="${redirectPage == 'updatepage'}">
                                                ${updateClientDTO.agreementDate}
                                                <input type="hidden" readonly value="${updateClientDTO.agreementDate}" name="agreementDate"/>
                                            </c:when>
                                            <c:otherwise>
                                                <input type="text" readonly value="${updateClientDTO.agreementDate}" class="defaultActualPicker" name="agreementDate" id="agreementDateID"/>
                                            </c:otherwise>
                                        </c:choose>
                                        <!--<input type="text" value="${updateClientDTO.agreementDate}" class="defaultActualPicker" name="agreementDate" id="agreementDateID"/>-->
                                        <br/><label id="errorAgreementDateID" class="ux-msg-error-under"></label>
                                        <form:errors path="agreementDate" cssStyle="color: red;width:190px; font-weight: bold"/>
                                    </dd>
                                </dl>
                                <dl>
                                    <dt>
                                    <label>Engagement Code: </label>
                                    </dt>
                                    <dd>
                                        <input type="text" value="${updateClientDTO.engagementCode}" readonly class="ux-width-31t inputgreyedout" name="engagementCode" id="engagementCodeID"/>
                                    </dd>
                                </dl>
                                <div class="ux-clear"></div>
                                <dl>
                                    <dt>
                                    <label>Engagement Name: <span>*</span></label>
                                    </dt>
                                    <dd>
                                        <input type="text" value="${updateClientDTO.engagementName}" class="ux-width-31t" name="engagementName" id="engagementNameID"/>
                                        <br/><label id="errorEngagementNameID" class="ux-msg-error-under"></label>
                                        <form:errors path="engagementName" cssStyle="color: red;width:190px; font-weight: bold"/>
                                    </dd>
                                </dl>

                                <dl>
                                    <dt>
                                    <label>Estimated Start Date: <span>*</span></label>
                                    </dt>
                                    <dd>
                                        <input type="text" readonly value="${updateClientDTO.estimatedStartDate}" class="defaultActualPicker" name="estimatedStartDate" id="estimatedStartDateID"/>
                                        <br/><label id="errorStartDateID" class="ux-msg-error-under"></label>
                                        <form:errors path="estimatedStartDate" cssStyle="color: red;width:190px; font-weight: bold"/>
                                    </dd>
                                </dl>

                                <dl>
                                    <dt>
                                    <label> Estimated End Date: <span>*</span></label>
                                    </dt>
                                    <dd>
                                        <input type="text" readonly value="${updateClientDTO.estimatedEndDate}" class="defaultActualPicker" name="estimatedEndDate" id="estimatedEndDateID"/>
                                        <br/><label id="errorEndDateID" class="ux-msg-error-under"></label>
                                        <form:errors path="estimatedEndDate" cssStyle="color: red;width:190px; font-weight: bold"/>
                                    </dd>
                                </dl>
                            </div>
                            <div class="ux-clear"></div>

                        </div>
                    </div>

                    <div class="ux-panl "> 
                        <div class="ux-panl-header ux-panl-with-underline">
                            <h2>Services </h2>
                        </div>
                        <div class="ux-panl-content ">

                            <div>
                                <label id="errorServicesSelect" class="ux-msg-error-under"></label>
                                <form:errors path="selectedServices" cssStyle="color: red;width:190px; font-weight: bold"/>
                                <input type="hidden" value="${updateClientDTO.selectedServices}" id="previousSelectedServices" name="previousSelectedServices"/>
                                <input type="hidden" value="${updateClientDTO.selectedServices}" id="selectedServices" name="selectedServices"/>
                                <table class="ux-tabl-data">
                                    <thead>
                                        <tr>
                                            <th><input name="check" type="checkbox" value="" id="headCheckbox" <c:if test="${redirectPage != 'updatepage'}">disabled="true"</c:if> onchange="checkAllServices(this)"/></th>
                                                <th>Service Name</th>
                                                <th>Service Start Date</th>
                                                <th>Service End Date</th>
                                            </tr>
                                        </thead>
                                        <tbody id="prodServices">

                                        <c:set var="servicesCount" value="0"/>
                                        <c:set var="checkedStat" value="false"/>

                                        <c:forEach items="${servicesList}" var="securityServiceDTO">
                                            <c:forEach items="${updateClientDTO.engagementServiceLi}" var="selectedServiceDTO">
                                                <c:if test="${selectedServiceDTO.securityServiceCode eq securityServiceDTO.securityServiceCode}">
                                                    <tr>
                                                        <td>     
                                                            <input type="checkbox" <c:if test="${selectedServiceDTO.fileCount > 0}">disabled</c:if> id="${securityServiceDTO.securityServiceCode}" class="checkboxGroup" onchange="serviceSelected(this)" checked/>
                                                        </td>
                                                        <td>${securityServiceDTO.securityServiceName}</td>
                                                        <c:choose>
                                                            <c:when test="${selectedServiceDTO.fileCount > 0}">
                                                                <td>
                                                                    <input type="text" readonly id="sd${securityServiceDTO.securityServiceCode}" value="${selectedServiceDTO.serviceStartDate}" name="engagementServiceLi[${servicesCount}].serviceStartDate"/>
                                                                </td>
                                                                <td>
                                                                    <input type="text" readonly id="ed${securityServiceDTO.securityServiceCode}" value="${selectedServiceDTO.serviceEndDate}" name="engagementServiceLi[${servicesCount}].serviceEndDate"/>
                                                                </td>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <td><input type="text" readonly id="sd${securityServiceDTO.securityServiceCode}" value="${selectedServiceDTO.serviceStartDate}" class="defaultActualPicker ux-width-6t" name="engagementServiceLi[${servicesCount}].serviceStartDate"/>
                                                                    <label id="errorSD${securityServiceDTO.securityServiceCode}" class="ux-msg-error-under"></label></td>
                                                                <td><input type="text" readonly id="ed${securityServiceDTO.securityServiceCode}" value="${selectedServiceDTO.serviceEndDate}" class="defaultActualPicker ux-width-6t" name="engagementServiceLi[${servicesCount}].serviceEndDate"/>
                                                                    <label id="errorED${securityServiceDTO.securityServiceCode}" class="ux-msg-error-under"></label></td>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        
                                                        
                                                    </tr>
                                                    <c:set var="checkedStat" value="true"/>
                                                </c:if>
                                            </c:forEach>
                                            <c:if test="${!checkedStat}">
                                                <tr>
                                                    <td>
                                                        <input type="checkbox" id="${securityServiceDTO.securityServiceCode}" class="checkboxGroup" onchange="serviceSelected(this)"/>
                                                    </td>
                                                    <td>${securityServiceDTO.securityServiceName}</td>
                                                    <td><input type="text" readonly id="sd${securityServiceDTO.securityServiceCode}" value="" class="defaultActualPicker ux-width-6t" name="engagementServiceLi[${servicesCount}].serviceStartDate"/>
                                                        <label id="errorSD${securityServiceDTO.securityServiceCode}" class="ux-msg-error-under"></label>
                                                        <form:errors path="engagementServiceLi[${servicesCount}].serviceStartDate" cssStyle="color: red;width:190px; font-weight: bold"/>
                                                    </td>
                                                    <td><input type="text" readonly id="ed${securityServiceDTO.securityServiceCode}" value="" class="defaultActualPicker ux-width-6t" name="engagementServiceLi[${servicesCount}].serviceEndDate"/>
                                                        <label id="errorED${securityServiceDTO.securityServiceCode}" class="ux-msg-error-under"></label>
                                                        <form:errors path="engagementServiceLi[${servicesCount}].serviceEndDate" cssStyle="color: red;width:190px; font-weight: bold"/>
                                                    </td>
                                                </tr>
                                            </c:if>
                                        <input type="hidden" name="engagementServiceLi[${servicesCount}].securityServiceCode" value="${securityServiceDTO.securityServiceCode}"/>
                                        <c:set var="servicesCount" value="${servicesCount+1}"/>
                                        <c:set var="checkedStat" value="false"/>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    
                    <div class="ux-panl ">
                        <div class="ux-panl-header ux-panl-with-underline">
                            <h2>Comments</h2>
                        </div>
                        <div class="ux-panl-content">
                            <div class="ux-hform ux-margin-top-none">
                                <dl>
                                    <dt></dt>
                                    <dd>
                                        <input type="hidden" name="engagementStatusComment" id="engagementComment"/>
                                        <textarea class="TextareaLong ux-height-8t ux-width-58t"  id="engagementStatusID" >${updateClientDTO.engagementStatusComment}</textarea>
                                    </dd>
                                    <label id="errorEngagementStatusID" class="ux-msg-error-under ux-width-42t"></label>
                                    <form:errors path="engagementStatusComment" cssStyle="color: red;width:190px; font-weight: bold" class="ux-width-42t"/>
                                </dl>
                            </div>
                        </div>
                    </div>

                    <div class="ux-float-right ">
                        <!-- Check for permissions and display respective action -->
                        <c:choose>
                            <c:when test="${redirectPage == 'updatepage'}">
                                <c:if test="${permissions.contains(prmnsConstants.DELETE_ENGAGEMENT)}">
                                    <input name="" type="button" value="Delete" title="Delete" class="ux-btn-default-action deleteEngmtBtn"/>
                                </c:if>
                                <c:if test="${permissions.contains(prmnsConstants.EDIT_ENGAGEMENT)}">
                                    <input name="" type="button" value="Save" title="Save" class="ux-btn-default-action updateEngmtBtn"/>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${permissions.contains(prmnsConstants.ADD_ENGAGEMENT)}">
                                    <input name="" type="button" value="Save" title="Save" class="ux-btn-default-action addEngmtBtn"/>
                                </c:if>
                            </c:otherwise>
                        </c:choose>

                        <input name="" type="button" value="Cancel" title="Cancel" class="ux-btn-default-action cancelbtn"/>
                    </div>
                </form:form>
            </div>
            <!-- content ends --> 

            <!-- Footer : Include -->
            <jsp:include page="../includes/footer.jsp"/>
            <!-- Footer : Include -->
        </div>
        <!-- End: Wrapper --> 
        <script>
            var headCheckbox1 = document.getElementById('headCheckbox');
            var checkboxResult1 = [];
            var selectedCheckboxes1 = [];
            var disabledcount = [];
            $('.checkboxGroup').each(function() {
                var id = $(this).attr('id');
                checkboxResult1.push(id);
                if (this.checked) {
                    selectedCheckboxes1.push(id);
                }
                if (this.disabled) {
                    disabledcount.push(id);
                }
            });
            
            if (selectedCheckboxes1.length === checkboxResult1.length && !headCheckbox1.checked) {
                if(checkboxResult1.length !== 0) {
                    headCheckbox1.checked = true;
                }
            }
            if (disabledcount.length === checkboxResult1.length) {
                headCheckbox1.disabled = true;
            }
        </script>
    </BODY>
</HTML>

