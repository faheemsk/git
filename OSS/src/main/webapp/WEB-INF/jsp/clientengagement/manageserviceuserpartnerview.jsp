<%-- 
    Document   : manageserviceuserpartnerview
    Created on : May 11, 2016, 6:34:49 PM
    Author     : sathuluri
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
                <li><a title="Manage Engagements" href="#" onclick="headerFn('engagementlist.htm')">Manage Engagements</a></li>
                <li>Manage Users to Services</li>
            </ul>
            <!-- End: Breadcrumbs -->
            <!--Start: Left navigation-->
            <jsp:include page="../includes/leftpanel.jsp"/>
            <!--End: Left navigation-->
            
            <!-- Popup Starts -->
            <div id='blackcover'> </div>
            <div id='popupbox' class="ux-modl">
                <div class="ux-modl-header">
                    <a href="javascript:void(0)" onclick='popup(0)' title="Close">Close</a>
                    <h6>Notification</h6>
                </div>
                <div class="ux-modl-content">
                    <div align="center">
                        <h3>Are you sure you want to unassign the user from this service?</h3>
                    </div>
                    <div class="ux-margin-1t  " align="center">
                        <input type="button" title="Ok" value="Ok" class="ux-btn-default-action ux-width-7t" onclick="fnDeleteAssignedUser('partner')" id="msgshow"/>
                        <input type="button" title="Cancel" value="Cancel" class="ux-btn-default-action ux-width-7t" onclick='popup(0)' /> 
                    </div>
                </div>
            </div>
            <!-- Popup Ends -->
            
            <!--Start: Confirmation popup to force delete-->
            <!-- Popup Starts -->
<!--            <div id='blackcover1'> </div>
            <div id='popupbox1' class="ux-modl">
                <div class="ux-modl-header">
                    <a href="javascript:void(0)" onclick='fnForceDeleteUser(0)' title="Close">Close</a>
                    <h6>Notification</h6>
                </div>
                <div class="ux-modl-content">
                    <div align="center">
                        <h3>The assigned user mapped with upload files / findings. Are you sure you want to delete?</h3>
                    </div>
                    <div class="ux-margin-1t" align="center">
                        <input type="button" title="Ok" value="Ok" class="ux-btn-default-action ux-width-7t" onclick="" id="msgshow"/>
                        <input type="button" title="Cancel" value="Cancel" class="ux-btn-default-action ux-width-7t" onclick='fnForceDeleteUser(0)'/> 
                    </div>
                </div>
            </div>-->
            <!-- Popup Ends -->
            <!--End: Confirmation popup to force delete-->
            
            <!-- Start: Content -->
            <div class="ux-content">
                <h1>Manage Users to Services</h1>
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
                                <dd><c:if test="${clientEngagementDTO.parentClientName ne 'null'}">
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
                            <dl id="partnerOrgName">
                                <dt><label>Partner Organization:</label></dt>
                                <dd>${clientEngagementDTO.engagementPartnerUserLi[0].partnerUserOrgName}</dd>
                            </dl>
                        </div>
                    </div>
                </div>
                <div class="ux-panl ux-width-full-inclusive" id="internalUserView">
                    <div class="ux-panl-header ux-panl-with-underline">
                        <h2>Service List</h2>
                    </div>
                    <div class="ux-panl-content">
                        <table id="table" class="ux-tabl-data">
                            <thead>
                                <spring:form action="" name="searchAssignUserFormForPartner"  commandName="searchManageServiceUserDto" method="POST">
                                    <tr>
                                        <th class="ux-width-8t">Role</th>
                                        <th>User<br/><input type="text" class="ux-width-full-8t" id="searchUserName" name="userName" value="${searchUserName}"/></th>
                                        <th>Start Date<br/><input type="text" id="searchStartDate" maxlength="10" readonly="true" class="ux-width-full-5t headerDatePick"
                                                                  name="startDate" value="${searchStartDate}"/></th>
                                        <th>End Date<br/><input type="text" id="searchEndDate" maxlength="10" readonly="true" class="ux-width-full-5t headerDatePick"
                                                                name="endDate" value="${searchEndDate}"/></th>
                                        <th class="ux-width-full-1t ux-padding-left-min">Action<br/><span>
                                                <a href="javascript:void(0)" type="button" onclick="$.fn.SearchFilterManageUsersToServicePartnerView()" title="Search">
                                                    <img src="images/search.png" />
                                                </a>
                                                <a href="javascript:void(0)" id="fnRefreshPartnerView" title="Refresh">
                                                    <img src="images/refresh.png" /> 
                                                </a>
                                            </span></th>
                                        <input type="hidden" name="clientEngagementCode" value="${clientEngagementDTO.engagementCode}"/>
                                        <input type="hidden" name="cei" value="${clientEngagementDTO.encEngagementCode}"/>
                                    </tr>
                                </spring:form>
                            </thead>
                            <tbody id="engagementAnalystRow">
                                <c:set var="count" value="1"/>
                                <c:set var="countForDynamicRow" value="1"/>
                                <c:if test="${fn:length(clientEngagementDTO.engagementServiceLi) > 0}">
                                    <c:forEach var="engagementServiceDto" items="${clientEngagementDTO.engagementServiceLi}">
                                        <input type="hidden" value="" id="assignedPartnerUser${engagementServiceDto.securityServiceCode}"/>
                                        <tr class="ux-tabl-alt-row" id="serviceIdValue${count}">
                                            <td colspan="7"><strong>${engagementServiceDto.securityServiceName}</strong> - 
                                                <span >Service Start Date: ${engagementServiceDto.serviceStartDate},  
                                                    Service End Date: ${engagementServiceDto.serviceEndDate} </span>
                                                <input type="hidden" id="${engagementServiceDto.securityServiceCode}"
                                                       value="${engagementServiceDto.securityServiceCode}"/>
                                                <!-- Check for permissions and display respective action -->
                                                <c:if test="${null != permissions}">
                                                    <c:if test="${permissions.contains(permissionConstants.ADD_USER_TO_SERVICE)}">
                                                        <span class="ux-float-right">
                                                            <a href="javascript:void(0)" title="Add User to Service" class="showrow" 
                                                               onclick="dynamicAddUser(${countForDynamicRow}, ${count}, 'partner', '${engagementServiceDto.securityServiceCode}')">
                                                                + Add User to Service</a>
                                                        </span>
                                                    </c:if>
                                                </c:if>
                                            </td>
                                        </tr>
                                        <!-- Check for permissions and display respective action -->
                                        <c:if test="${null != permissions}">
                                            <c:if test="${permissions.contains(permissionConstants.ADD_USER_TO_SERVICE)}">
                                                <c:if test="${fn:length(engagementServiceDto.manageServiceUserLi) <= 0}">
                                                    <tr id="${engagementServiceDto.securityServiceCode}DefaultRowPartner${count}">
                                                        <td class="ux-width-8t">Engagement Analyst</td>
                                                        <td><select class="ux-width-full-8t" id="${engagementServiceDto.securityServiceCode}userlistPartner${count}">
                                                                <option selected="selected" title="Select" value="0">Select</option>
                                                                <c:forEach var="partnerUsers" items="${partnerUsersList}">
                                                                    <c:if test="${sessionScope.USER_SESSION.userProfileKey != partnerUsers.userProfileKey}" >
                                                                        <c:choose>
                                                                            <c:when test="${partnerUsers != null && partnerUsers.firstName eq manageServiceUserDto.userName}">
                                                                                <option value="${partnerUsers.userProfileKey}" selected>${partnerUsers.firstName}</option>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <option value="${partnerUsers.userProfileKey}">${partnerUsers.firstName}</option>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                            <label id="${engagementServiceDto.securityServiceCode}errorPartnerUser${count}" 
                                                                   class="ux-width-10t"></label>
                                                        </td>
                                                        <td><input type="text" class="headerDatePick ux-width-full-5t" 
                                                                   id="${engagementServiceDto.securityServiceCode}startdatePartner${count}" readonly="true"/>
                                                            <br/>
                                                            <label id="${engagementServiceDto.securityServiceCode}errorPartnerStartDate${count}" 
                                                                   class="ux-width-10t">
                                                            </label>
                                                        </td>
                                                        <td><input type="text" class="headerDatePick ux-width-full-5t" 
                                                                   id="${engagementServiceDto.securityServiceCode}enddatePartner${count}" readonly="true"/>
                                                            <label id="${engagementServiceDto.securityServiceCode}errorPartnerEndDate${count}" 
                                                                   class="ux-width-10t">
                                                            </label>
                                                        </td>
                                                        <td>
                                                            <a href="javascript:void(0)" class="ux-icon-save" id="saverow" title="Save" 
                                                               onclick="saveUserData(${count}, 'partner', '${engagementServiceDto.securityServiceCode}');
                                                                       return true;">Save</a>
                                                        </td>
                                                    </tr>
                                                </c:if>
                                            </c:if>
                                        </c:if>
                                        <c:if test="${fn:length(engagementServiceDto.manageServiceUserLi) > 0}">
                                            <input type="hidden" value="" id="assignedPartnerUsersList${engagementServiceDto.securityServiceCode}"/>
                                            <c:forEach var="manageServiceUserDto" items="${engagementServiceDto.manageServiceUserLi}">
                                                <tr id="${engagementServiceDto.securityServiceCode}DefaultRowPartner${count}" style="display: none">
                                                    <td class="ux-width-8t">Engagement Analyst</td>
                                                    <td><select class="ux-width-full-8t" id="${engagementServiceDto.securityServiceCode}userlistPartner${count}">                                                            
                                                        </select>
                                                        <label id="${engagementServiceDto.securityServiceCode}errorPartnerUser${count}" 
                                                               class="ux-width-10t"></label>
                                                    </td>
                                                    <td><input type="text" class="headerDatePick ux-width-full-5t" 
                                                               id="${engagementServiceDto.securityServiceCode}startdatePartner${count}" readonly="true"/>
                                                        <br/>
                                                        <label id="${engagementServiceDto.securityServiceCode}errorPartnerStartDate${count}" 
                                                               class="ux-width-10t">
                                                        </label>
                                                    </td>
                                                    <td><input type="text" class="headerDatePick ux-width-full-5t" 
                                                               id="${engagementServiceDto.securityServiceCode}enddatePartner${count}" readonly="true"/>
                                                        <label id="${engagementServiceDto.securityServiceCode}errorPartnerEndDate${count}" 
                                                               class="ux-width-10t">
                                                        </label>
                                                    </td>
                                                    <td>
                                                        <a href="javascript:void(0)" class="ux-icon-save" id="saverow" title="Save" 
                                                           onclick="saveUserData(${count}, 'partner', '${engagementServiceDto.securityServiceCode}');
                                                                   return true;">Save</a>
                                                    </td>
                                                </tr>

                                                <tr id="${engagementServiceDto.securityServiceCode}editRowPartner${count}">
                                                    <input type="hidden" id="manageServiceUserKey${count}" value="${manageServiceUserDto.manageServiceUserKey}"/>
                                                    <td>Engagement Analyst</td>
                                                    <td id="userValuePartner${count}">${manageServiceUserDto.userName}</td>
                                                    <input type="hidden" value="${manageServiceUserDto.userKey}" id="partnerOldAssignedUserId${count}"/>
                                                    <input type="hidden" value="${manageServiceUserDto.userName}" id="oldAssignedPartnerUserName${count}"/>
                                                    <td id="startDateValuePartner${count}">${manageServiceUserDto.startDate}</td>
                                                    <td id="endDateValuePartner${count}">${manageServiceUserDto.endDate}</td>
                                                    <!-- Check for permissions and display respective action -->
                                                    <c:if test="${null != permissions}">
                                                        <c:if test="${permissions.contains(permissionConstants.EDIT_USER_TO_SERVICE)}">
                                                            <td>
                                                                <a href="javascript:void(0)" class="ux-icon-edit" id="editrow${count}" title="Edit" onclick="editUserData(${count}, 'partner', '${engagementServiceDto.securityServiceCode}', '${manageServiceUserDto.userKey}', '${manageServiceUserDto.organizationKey}')">Edit</a>
                                                                <a href="javascript:void(0)" class="ux-icon-delete" id="deleterow" title="Delete" 
                                                                   onclick="fnDeleteUser(${count}, 'partner', '${engagementServiceDto.securityServiceCode}', '${engagementServiceDto.securityServiceName}', '${manageServiceUserDto.userName}');">Delete</a>
                                                            </td>
                                                        </c:if>
                                                    </c:if>
                                                </tr>
                                                <c:set var="count" value="${count + 1}"/>
                                            </c:forEach>
                                        </c:if>
                                        <c:set var="count" value="${count + 1}"/>
                                        <tr id="${engagementServiceDto.securityServiceCode}${countForDynamicRow}" style="display: none"/>
                                        <c:set var="countForDynamicRow" value="${countForDynamicRow + 1}"/>
                                    </c:forEach>
                                    <input type="hidden" value="${count}" id="countValue"/>
                                </c:if>
                                <c:if test="${fn:length(clientEngagementDTO.engagementServiceLi) <= 0}">
                                    <tr>
                                        <td valign="top" colspan="6" class="dataTables_empty">No matching records found</td>
                                    </tr>
                                </c:if>
                                
                                <!-- Start: (For defect : DE10518)-->
                                <c:forEach var="mappedUsers" items="${mappedUsersLists}">
                                    <select class="ux-width-full-8t" id="${mappedUsers[0].rowStatusValue}MappedPartnerUserList" style="display: none">
                                        <option selected="selected" title="Select" value="0">Select</option>
                                        <c:forEach var="mappedUser" items="${mappedUsers}">
                                            <c:if test="${sessionScope.USER_SESSION.userProfileKey != mappedUser.userProfileKey}" >
                                                <option value="${mappedUser.userProfileKey}">${mappedUser.firstName}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </c:forEach>
                                <!-- End: (For defect : DE10518)-->
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="ux-float-right">
                    <input type="button" value="Back" title="Back" class="ux-btn-default-action" onclick="headerFn('engagementlist.htm')"/>
                    <input type="button" value="Cancel" title="Cancel" class="ux-btn-default-action" id="fnCancelPartnerReload"></input>
                </div>
            </div>
            <!-- End: Content -->
            <spring:form action="" name="saveAssignUserFormPartner"  commandName="manageServiceUserDto" method="POST">
                <input type="hidden" name="userKey" id="userKey" value=""/>
                <input type="hidden" name="startDate" id="startDate" value=""/>
                <input type="hidden" name="endDate" id="endDate" value=""/>
                <input type="hidden" name="manageServiceUserKey" id="manageServiceUserKey" value="0"/>
                <input type="hidden" name="secureServiceCode" id="secureServiceCode" value=""/>
                <input type="hidden" name="clientEngagementCode" id="clientEngagementCode" value=""/>
                <input type="hidden" name="rowStatusKey" id="rowStatusKey" value="1"/>
                <input type="hidden" name="clientEngagementEndDate" id="clientEngagementEndDate" value="0"/>
                <input type="hidden" name="oldAssignedUserKey" id="oldAssignedUserKey" value="0"/>
                <input type="hidden" name="serviceName" id="serviceName" value=""/>
                <input type="hidden" name="userName" id="userName" value=""/>
            </spring:form>

            <spring:form action="" name="cancelReloadFormPartner" method="POST">
                <input type="hidden" name="cei" id="cei" value="${clientEngagementDTO.encEngagementCode}"/>
            </spring:form>
            <!-- Start: Footer -->
            <jsp:include page="../includes/footer.jsp"/>
            <!-- End: Footer -->
        </div>
        <!-- End: Wrapper -->
    </body>
</html>