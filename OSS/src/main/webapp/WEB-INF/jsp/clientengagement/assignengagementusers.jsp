<%-- 
    Document   : assignengagementusers
    Created on : Apr 28, 2017, 6:53:25 AM
    Author     : sbhagavatula
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld" %>
<%@taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="constants" />
<un:useConstants className="com.optum.oss.constants.PermissionConstants" var="permissionConstants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=9; IE=10; IE=EDGE" />
        <meta name="viewport" content="width=device-width" />
        <title>IRMaaS</title>
        <!-- CSS : Include -->
        <jsp:include page="../includes/commoncss.jsp"/>
        <link rel="stylesheet" href="<c:url value="/css/jquery.dataTables.css"/>" />
        <link rel="stylesheet" href="<c:url value="/appcss/clientengagementsworklist.css"/>" />
        <!-- CSS : Include -->
        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/appjs/manageserviceuser.js"/>"></script>
        <!-- JS : Include -->  
        <!-- Header : Include -->
        <jsp:include page="../includes/header.jsp"/>
        <style>
            .ux-panl{
                display: inline-block;
            }
        </style>
        
        <!-- Header : Include -->
    </head>
    <body>
        
        <!-- Start: Wrapper -->
        <div id="ux-wrapper"> 
            <!-- Start: Breadcrumbs -->
            <ul class="ux-brdc ux-margin-left-1t" >

                <li><a title="Client Engagement" href="#" onclick="headerFn('engagementlist.htm')">Client Engagement</a></li>
                <li><a title="Manage Engagement Users" href="#" onclick="headerFn('manageengusers.htm')">Manage Engagement Users</a></li>
                <li>Manage Users</li>
            </ul>
            <!-- End: Breadcrumbs -->
            
            <!--Start: Left navigation-->
            <jsp:include page="../includes/leftpanel.jsp"/>
            <!--End: Left navigation-->
            
            <!-- Start: Content -->
            <div class="ux-content">
                <h1>Manage Users
                    <div class="ux-float-right ">
                        <input name="" type="button" value="Back" title="Back" class="ux-btn-default-action" onclick="headerFn('manageengusers.htm')"  id="showpaclist"/>
                    </div>
                </h1>
                <c:if test="${null != manageserviceusersuccessMessage && manageserviceusersaveflag == constants.SAVE_FLAG}">
                    <div class="ux-msg-success">${manageserviceusersuccessMessage}</div>
                </c:if>
                <c:if test="${null != manageserviceusersuccessMessage && manageserviceusersaveflag == constants.UPDATE_FLAG}">
                    <div class="ux-msg-success">${manageserviceusersuccessMessage}</div>
                </c:if>
                <c:if test="${null != manageserviceusersuccessMessage && manageserviceusersaveflag == constants.DELETE_FLAG}">
                    <div class="ux-msg-success">${manageserviceusersuccessMessage}</div>
                </c:if>
                <div class="ux-panl ux-width-full-inclusive">
                    <div class="ux-panl-header ux-panl-with-underline">
                        <h2>Engagement Information</h2>
                    </div>
                    <div class="ux-panl-content">
                        <div class="ux-hform">
                            <dl>
                                <dt><label>Engagement Code:</label></dt>
                                <dd id="engagementCodeValue">${clientEngagementDTO.engagementCode}</dd>
                            </dl>
                            <dl>
                                <dt><label>Parent Client Name:</label></dt>
                                <dd>
                                    <c:if test="${clientEngagementDTO.parentClientName ne 'null'}">
                                        ${clientEngagementDTO.parentClientName}
                                    </c:if>
                                </dd>
                            </dl>
                            <dl>
                                <dt><label>Client Name:</label></dt>
                                <dd>${clientEngagementDTO.clientName}</dd>
                            </dl>
                            <dl>
                                <dt><label>Engagement Name:</label></dt>
                                <dd>${clientEngagementDTO.engagementName}</dd>
                            </dl>
                            <dl>
                                <dt><label>Agreement Date:</label></dt>
                                <dd>${clientEngagementDTO.agreementDate}</dd>
                            </dl>
                            <dl>
                                <dt><label>Engagement Start Date:</label></dt>
                                <dd id="engagementStartDate">${clientEngagementDTO.estimatedStartDate}</dd>
                            </dl>
                            <dl>
                                <dt><label>Engagement End Date:</label></dt>
                                <dd id="engagementEndDate">${clientEngagementDTO.estimatedEndDate}</dd>
                            </dl>
                        </div>
                    </div>
                </div>
                
                <c:if test="${sessionScope.USER_SESSION != null}">
                    <c:set var="userProf" value="${sessionScope.USER_SESSION}"/>
                    <c:set var="userTypek" value="${userProf.userTypeKey}"/>
                </c:if>
                            <!--${userProf.userTypeKey}-->
                <c:if test="${userTypek != constants.USER_TYPE_PARTNER_KEY}">
                <div class="ux-panl ux-width-full-inclusive" id="usrfrm">
                    <div class="ux-panl-header ux-panl-with-underline">
                        <h2>Assign Engagement Users</h2>
                    </div>
                    <div class="ux-panl-content">
                        <div class="ux-hform">
                        <spring:form name="engUserForm"  action="" method="POST" modelAttribute="enguserobj">
                                    <input type="hidden" value="${clientEngagementDTO.encEngagementCode}" name="cei" />
                                    <input type="hidden" value="E" name="userEngSrv"/>
                            <dl>
                                <dt><label>User Type: <span>*</span></label></dt>
                                <dd>
                                    <select id="userTypeID" name="userTypeKey" class="engUserTypeKey ux-width-12t">
                                        <option value="0" title="Select">Select</option>
                                        <c:forEach items="${userTypes}" var="masterDTO">
                                            <c:if test="${masterDTO.lookUpEntityName ne constants.DB_ORGANIZATIION_TYPE_PARTNER}">
                                            <option value="${masterDTO.masterLookupKey}" title="${masterDTO.lookUpEntityName}" 
                                                    <c:if test="${enguserobj.userTypeKey == masterDTO.masterLookupKey}">selected</c:if>>${masterDTO.lookUpEntityName}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                    <br/><label id="errUserTypeID" class="ux-msg-error-under"></label>
                                </dd>
                           </dl>
                             <dl>
                                <dt><label>Role: <span>*</span></label></dt>
                                <dd>
                                    <select id="appRoleKeyID" name="appRoleKey" class="engUserTypeKey ux-width-12t">
                                        <option value="0" title="Select">Select</option>
                                        <c:forEach items="${engRoleList}" var="roleDTO">
                                                <c:if test="${permissions.contains(permissionConstants.ADD_ENGAGEMENT)}">
                                                <option value="${roleDTO.appRoleKey}" title="${roleDTO.appRoleName}" 
                                                        <c:if test="${enguserobj.appRoleKey == roleDTO.appRoleKey}">selected</c:if>>${roleDTO.appRoleName}</option>
                                                </c:if>
                                                <c:if test="${!permissions.contains(permissionConstants.ADD_ENGAGEMENT) && roleDTO.appRoleName ne constants.ROLE_OPTUM_ENGAGEMENT_LEAD}">
                                                <option value="${roleDTO.appRoleKey}" title="${roleDTO.appRoleName}" 
                                                        <c:if test="${enguserobj.appRoleKey == roleDTO.appRoleKey}">selected</c:if>>${roleDTO.appRoleName}</option>
                                                </c:if>
                                        </c:forEach>
                                    </select>
                                    <br/><label id="errAppRoleKeyID" class="ux-msg-error-under"></label>
                                </dd>
                             </dl>
                             <dl>
                                <dt><label>User: <span>*</span></label></dt>
                                <dd>
                                    <select id="engUserID" name="userProfileKey" class="ux-width-14t">
                                        <option title="Select" value="0">Select</option>
                                        <c:forEach items="${engUsrList}" var="userDTO">
                                                <option value="${userDTO.userProfileKey}" >${userDTO.firstName} ${userDTO.lastName}</option>
                                        </c:forEach>
                                    </select>
                                    <br/><label id="errEngUserID" class="ux-msg-error-under"></label>
                                </dd>
                             </dl>
                             <dl>
                                <dt><label>&nbsp;</label></dt>
                                <dd>
                                    <input name="" type="button" value="Save" title="Save" class="ux-btn-default-action" id="engUserAdd"/>
                                </dd>
                            </dl>
                            </spring:form>
                            <div class="ux-clear"></div>
                            <label id="errorClientUserID" class="ux-msg-error-under">${userexists}</label>
                        </div>
                        
                        <table id="table"  width="100%" class="ux-tabl-data">
                        <thead>
                            <tr>
                                <th class="ux-width-full-1t-half">User Type</th>
                                <th class="ux-width-full-1t-half">Role</th>
                                <th class="ux-width-full-2t-half">User</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${engusrlist}" var="userProfileDTO">
                                <tr>
                                    <td>${userProfileDTO.userTypeObj.lookUpEntityName}</td>
                                    <td>${userProfileDTO.appRoleName}</td>
                                    <td>${userProfileDTO.firstName}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${userProfileDTO.appRoleName ne constants.ROLE_OPTUM_ENGAGEMENT_LEAD}">
                                                <a href="javascript:void(0)" class="ux-icon-delete" id="" title="Delete" 
                                                    onclick="deleteEngUser('${userProfileDTO.userProfileKey}')">Delete</a>
                                            </c:when>
                                            <c:otherwise>
                                                <c:if test="${permissions.contains(permissionConstants.ADD_ENGAGEMENT)}">
                                                <a href="javascript:void(0)" class="ux-icon-delete" id="" title="Delete" 
                                                   onclick="deleteEngUser('${userProfileDTO.userProfileKey}')">Delete</a>
                                                </c:if>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    </div>
                </div>
                </c:if>
                            
                <spring:form name="deleteEngUsrForm"  action="delengagementusers.htm" method="POST" modelAttribute="deluserobj">
                    <input type="hidden" value="${clientEngagementDTO.encEngagementCode}" name="cei" />
                    <input type="hidden" value="${userProfileDTO.userProfileKey}" name="userProfileKey" id="assignKeyId"/>
                </spring:form>   
                <div class="ux-panl ux-width-full-inclusive" id="srvfrm">
                    <div class="ux-panl-header ux-panl-with-underline">
                        <h2>Assign Service Users</h2>
                    </div>
                    <div class="ux-panl-content">
                    <div class="ux-hform">
                    <spring:form modelAttribute="srvuserobj" name="serviceUserForm"  action="" method="POST">
                        <input type="hidden" value="S" name="userEngSrv"/>
                        <input type="hidden" value="${srvuserobj.selServCode}" name="selServCode" id="engSrvCode"/>
                        <input type="hidden" value="${clientEngagementDTO.encEngagementCode}" name="cei" />

                        <!--Start: Form to Add Service User-->
                        <dl style="vertical-align:top" class="ux-width-22t">
                            <dt><label>User Type: <span>*</span></label></dt>
                            <dd>
                                <select id="suserTypeID" name="userTypeKey" class="srvUserTypeKey">
                                    <option value="0" title="Select">Select</option>
                                    <c:forEach items="${userTypes}" var="masterDTO">
                                        <c:if test="${masterDTO.lookUpEntityName ne constants.DB_ORGANIZATIION_TYPE_CLIENT}">
                                            <c:if test="${userTypek == constants.USER_TYPE_INTERNAL_KEY}">
                                            <option value="${masterDTO.masterLookupKey}" title="${masterDTO.lookUpEntityName}" <c:if test="${srvuserobj.userTypeKey == masterDTO.masterLookupKey}">selected</c:if>>${masterDTO.lookUpEntityName}</option>
                                            </c:if>
                                            <c:if test="${userTypek == constants.USER_TYPE_PARTNER_KEY && masterDTO.lookUpEntityName eq constants.DB_ORGANIZATIION_TYPE_PARTNER}">
                                            <option value="${masterDTO.masterLookupKey}" title="${masterDTO.lookUpEntityName}" <c:if test="${srvuserobj.userTypeKey == masterDTO.masterLookupKey}">selected</c:if>>${masterDTO.lookUpEntityName}</option>
                                            </c:if>
                                        </c:if>
                                    </c:forEach>
                                </select>
                                <br/><label id="errsUserTypeID" class="ux-msg-error-under"></label>
                            </dd>
                        </dl>
                        <dl style="vertical-align:top">
                            <dt><label>Role: <span>*</span></label></dt>
                            <dd>
                                <select id="ptnrRoleKeyID" name="appRoleKey" class="srvUserTypeKey ux-width-12t">
                                    <option value="0" title="Select">Select</option>
                                    <c:forEach items="${srvRoleList}" var="roleDTO">
                                        <c:if test="${userTypek == constants.USER_TYPE_INTERNAL_KEY}">
                                        <option value="${roleDTO.appRoleKey}" title="${roleDTO.appRoleName}" <c:if test="${srvuserobj.appRoleKey == roleDTO.appRoleKey}">selected</c:if>>${roleDTO.appRoleName}</option>
                                        </c:if>
                                        <c:if test="${userTypek == constants.USER_TYPE_PARTNER_KEY && roleDTO.appRoleName eq 'Partner Engagement Analyst'}">
                                        <option value="${roleDTO.appRoleKey}" title="${roleDTO.appRoleName}" <c:if test="${srvuserobj.appRoleKey == roleDTO.appRoleKey}">selected</c:if>>${roleDTO.appRoleName}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                                <br/><label id="errPtnrRoleKeyID" class="ux-msg-error-under"></label>
                            </dd>
                        </dl>
                        <dl style="vertical-align:top">
                            <dt><label>Organization: <span>*</span></label></dt>
                            <dd>
                                <select id="partnerOrg" name="organizationKey" class="srvUserTypeKey ux-width-14t" <c:if test="${srvuserobj.userTypeKey == constants.USER_TYPE_INTERNAL_KEY}">disabled</c:if> >
                                    <c:choose>
                                        <c:when test="${srvuserobj.userTypeKey == constants.USER_TYPE_INTERNAL_KEY}">
                                            <option value="1" title="Select" selected>Optum</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="0" title="Select">Select</option>
                                            <c:forEach items="${partnerOrgList}" var="partnerOrgDTO"> 
                                                <c:choose>
                                                    <c:when test="${srvuserobj != null && srvuserobj.organizationKey eq partnerOrgDTO.organizationKey}">
                                                        <option value="${partnerOrgDTO.organizationKey}" selected >${partnerOrgDTO.organizationName}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${partnerOrgDTO.organizationKey}">${partnerOrgDTO.organizationName}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                    
                                </select>
                                <br/><label id="errPartnerOrg" class="ux-msg-error-under"></label>
                            </dd>
                        </dl>
                        <dl style="vertical-align:top">
                            <dt><label>User: <span>*</span></label></dt>
                            <dd>
                                <select id="srvUser" name="userProfileKey" class="ux-width-14t">
                                    <option title="Select" value="0">Select</option>
                                    <c:forEach items="${srvUsrList}" var="userDTO">
                                        <option value="${userDTO.userProfileKey}" >${userDTO.firstName} ${userDTO.lastName}</option>
                                    </c:forEach>
                                </select>
                                <br/><label id="errSrvUserID" class="ux-msg-error-under"></label>
                            </dd>
                        </dl>
                        <div class="ux-clear"></div>
                        <dl style="vertical-align:top" class="ux-width-22t">
                            <dt><label>Services: <span>*</span></label></dt>
                            <dd>
                                <div class="listbox ux-width-20t" id="servicesLst">
                                    <ul id="">
                                        <c:forEach items="${clientEngagementDTO.engagementServiceLi}" var="serviceDto">
                                            <li>
                                                <label id="list1" ><input type="checkbox" value="${serviceDto.securityServiceCode}" name="selServCode"/>
                                                    ${serviceDto.securityServiceName}
                                                </label>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                                <label id="errservicesLstID" class="ux-msg-error-under"></label>
                            </dd>
                        </dl>
                         <dl style="vertical-align:top">
                            <dt><label>Start Date: <span>*</span></label></dt>
                            <dd>
                                <input type="text" value="" class="ux-width-5t" id="startDateId" name="serviceDto.serviceStartDate"/>
                                <br/><label id="errStartDateId" class="ux-msg-error-under"></label>
                            </dd>
                        </dl>
                        <dl style="vertical-align:top">
                            <dt><label>End Date: <span>*</span></label></dt>
                            <dd>
                                <input type="text" value="" class="ux-width-5t" id="endDateId" name="serviceDto.serviceEndDate"/>
                                <br/><label id="errEndDateId" class="ux-msg-error-under"></label>
                            </dd>
                        </dl>
                        <dl>
                            <dt><label>&nbsp;</label></dt>
                            <dd>
                                <input name="" type="button" value="Save" title="Save" class="ux-btn-default-action" id="srvUserAdd"/>
                            </dd>
                        </dl>
                        </tr>
                        <!--End: Form to Add Service User-->
                    </spring:form>
                        <div class="ux-clear"></div>
                        <label id="errorClientUserID" class="ux-msg-error-under">${srvuserexists}</label>
                    </div>     
                        <spring:form name="deleteSrvUsrForm"  action="delserviceusers.htm" method="POST" modelAttribute="deluserobj">
                            <input type="hidden" value="${clientEngagementDTO.encEngagementCode}" name="cei" />
                            <input type="hidden" value="" name="userProfileKey" id="assignSrvKeyId"/>
                        </spring:form> 
                        <table id="table" class="ux-tabl-data">
                            <thead>
                                <tr>
                                    <th>User Type</th>
                                    <th>Role</th>
                                    <th>Organization</th>
                                    <th>User<br/></th>
                                    <th class="ux-width-12t">Start Date<br/>
                                    </th>
                                    <th class="ux-width-12t">End Date<br/>
                                    </th>
                                    <th class="ux-width-full-1t ux-padding-left-min">Action<br/></th>
                                    <input type="hidden" name="clientEngagementCode" value="${clientEngagementDTO.engagementCode}"/>
                                    <input type="hidden" name="cei" value="${clientEngagementDTO.encEngagementCode}"/>
                                </tr>
                            </thead>
                            <tbody>
                                
                                <c:if test="${fn:length(clientEngagementDTO.engagementServiceLi) > 0}">
                                    <c:forEach var="engagementServiceDto" items="${clientEngagementDTO.engagementServiceLi}">
                                        <tr class="ux-tabl-alt-row" id="serviceIdValue${count}">
                                            <td colspan="7"><strong>${engagementServiceDto.securityServiceName}</strong> - 
                                                <span >Service Start Date: ${engagementServiceDto.serviceStartDate},  
                                                    Service End Date: ${engagementServiceDto.serviceEndDate} </span>
                                            </td>
                                        </tr>
                                        <c:forEach items="${srvusrlist}" var="servUserDTO">
                                            <c:if test="${servUserDTO.secureServiceCode eq engagementServiceDto.securityServiceCode}">
                                            <tr>
                                                <td>${servUserDTO.userTypeValue}</td>
                                                <td>${servUserDTO.roleName}</td>
                                                <td>${servUserDTO.organizationName}</td>
                                                <td>${servUserDTO.userName}</td>
                                                <td>${servUserDTO.startDate}</td>
                                                <td>${servUserDTO.endDate}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${userTypek != constants.USER_TYPE_PARTNER_KEY}">
                                                            <a href="javascript:void(0)" class="ux-icon-delete" id="" title="Delete" 
                                                                onclick="deleteServiceUser('${servUserDTO.manageServiceUserKey}')">Delete</a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:if test="${servUserDTO.roleName eq 'Partner Engagement Analyst'}">
                                                                <a href="javascript:void(0)" class="ux-icon-delete" id="" title="Delete" 
                                                                    onclick="deleteServiceUser('${servUserDTO.manageServiceUserKey}')">Delete</a>
                                                            </c:if>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                            </c:if>
                                        </c:forEach>
                                    </c:forEach>
                                    
                                    
                                    
                                    <c:if test="${fn:length(clientEngagementDTO.engagementServiceLi) <= 0}">
                                        <tr>
                                            <td valign="top" colspan="6" class="dataTables_empty">No matching records found</td>
                                        </tr>
                                    </c:if>
                                </c:if>
                                
                            </tbody>
                        </table>
                    </div>
                </div>
                            
                <div class="ux-float-right ">
                    <input name="" type="button" value="Back" title="Back" class="ux-btn-default-action" onclick="headerFn('manageengusers.htm')"  id="showpaclist"/>
                    <!--<input name="" type="button" value="Cancel" title="Cancel" class="ux-btn-default-action" id="fnCancelInternalReload"/>-->
                </div>
            </div>
            <!-- End: Content -->
           
            <!-- Start: Footer -->
            <jsp:include page="../includes/footer.jsp"/>
            <!-- End: Footer -->
        </div>
        <!-- End: Wrapper -->
    </body>
</html>
