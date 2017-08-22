<%-- 
    Document   : updateuser
    Created on : Apr 19, 2016, 6:46:16 PM
    Author     : sbhagavatula
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="constants" />
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld" %>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>IRMaaS</title>
        <!-- CSS : Include -->
        <jsp:include page="../includes/commoncss.jsp"/>
        <link rel="stylesheet" href="<c:url value="/appcss/User.css"/>" />
        <link rel="stylesheet" href="<c:url value="/css/multiple-select.css"/>" />
        <!-- CSS : Include -->
        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/appjs/UserJS.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/js/jquery.multiple.select.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/appjs/CommonUtils.js"/>"></script>
         <script type="text/javascript" src="<c:url value="/js/ddaccordion.js"/>"></script>
        <!-- JS : Include -->  
        <script type="text/javascript">
       ddaccordion.init({
	headerclass: "submenuheader", //Shared CSS class name of headers group
	contentclass: "submenu", //Shared CSS class name of contents group
	revealtype: "click", //Reveal content when user clicks or onmouseover the header? Valid value: "click", "clickgo", or "mouseover"
	mouseoverdelay: 200, //if revealtype="mouseover", set delay in milliseconds before header expands onMouseover
	collapseprev: true, //Collapse previous content (so only one open at any time)? true/false 
	defaultexpanded: [0], //index of content(s) open by default [index1, index2, etc] [] denotes no content
	onemustopen: false, //Specify whether at least one header should be open always (so never all headers closed)
	animatedefault: false, //Should contents open by default be animated into view?
	persiststate: true, //persist state of opened contents within browser session?
	toggleclass: ["ux-accordion-panl-open", "ux-accordion-panl-closed"], //Two CSS classes to be applied to the header when it's collapsed and expanded, respectively ["class1", "class2"]
	togglehtml: ["prefix", "", ""], //Additional HTML added to the header when it's collapsed and expanded, respectively  ["position", "html1", "html2"] (see docs)
	animatespeed: "fast", //speed of animation: integer in milliseconds (ie: 200), or keywords "fast", "normal", or "slow"
	oninit:function(headers, expandedindices){ //custom code to run when headers have initalized
		//do nothing
	},
	onopenclose:function(header, index, state, isuseractivated){ //custom code to run whenever a header is opened or closed
		//do nothing
	}
})
</script>
    </head>
    <body>
        <un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="constants" />
        <form name="addusrfrm" method="post" hidden></form>
        <div id="ux-wrapper">
            <!-- Header : Include -->
            <jsp:include page="../includes/header.jsp"/>
            <!-- Header : Include -->

            <!-- Start: Breadcrumbs -->
            <ul class="ux-brdc ux-margin-left-1t" >
                
                <li><a title="Manage Users" href="#" onclick="headerFn('userlist.htm')" >Manage Users</a></li>
                <li>Edit User</li>
            </ul>
            <!-- End: Breadcrumbs -->
            
            <!-- Left Panel : Include -->
            <jsp:include page="../includes/leftpanel.jsp"/>
            <!-- Left Panel : Include -->

            <!-- Begin: Content -->
            <div class="ux-content">
                <spring:form commandName="upduserobj" name="editUserForm"  action="" method="POST">
                <h1>Edit User <div class="ux-float-right ">
                        <input name="" type="button" value="Update" title="Update" id="userUpdateButton" class="ux-btn-default-action  "  />
                        <input name="" type="button" value="Cancel" title="Cancel" class="ux-btn-default-action" onclick="backToUserFn()"/>
                    </div>
                </h1>
                <div class="ux-panl ux-float-left">
                    <div class="ux-panl-header ux-panl-with-underline">
                        <h2> User Details</h2>
                    </div>
                    <div class="ux-panl-content ux-padding-top-none">
                        <div class="ux-hform">
                            
                            <p class="ux-form-required">
                                <strong class="ux-labl-required">*</strong> Required
                            </p>
                            <div class="ux-clear"></div>
                            <dl>
                                <dt><label>ID:</label> </dt>
                                <dd>
                                    <input type="text" name="userProfileKey" readonly value="${editUserProfile.userProfileKey}" Placeholder="" class="nobrder" />
                                     <input type="hidden" name="encUserProfileKey"  value="${editUserProfile.encUserProfileKey}" Placeholder="" class="nobrder" />
                                </dd>
                            </dl>
                            <dl>
                                <dt><label> User Type: <span>*</span></label> </dt>
                                <dd><c:set value="${editUserProfile.userTypeObj.masterLookupKey}" var="selectedUserTypeKey"/>
                                    <select id="userTypeID" name="userTypeKey" class="UpdtaeUserTypeKey">
                                        <option title="Select" value="0">Select</option>
                                        <c:forEach var="masterDTO" items="${userTypes}">
                                            <c:choose>
                                                <c:when test="${selectedUserTypeKey!=null && selectedUserTypeKey eq masterDTO.masterLookupKey || editUserProfile.userTypeKey ==  masterDTO.masterLookupKey}">
                                                    <option value="${masterDTO.masterLookupKey}" selected="selected" title="${masterDTO.lookUpEntityName}">${masterDTO.lookUpEntityName}</option>  
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${masterDTO.masterLookupKey}" title="${masterDTO.lookUpEntityName}">${masterDTO.lookUpEntityName}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>
                                </dd>
                                <label id="errorUserTypeId" class="ux-msg-error-under"></label>
                                <spring:errors cssClass="error"  path="userTypeKey" cssStyle="color: red; display: block; font-weight: bold"/>
                            </dl>
                            <dl>
                                <dt><label> Organization Name:  <span>*</span></label> </dt>
                                <dd>
                                <select id="partner" name="organizationKey">
                                    <option value="0" title="Select">Select</option>
                                    <!--<option value="${editUserProfile.organizationKey}" selected="selected" title="${editUserProfile.organizationObj.organizationName}">${editUserProfile.organizationObj.organizationName}</option>-->  
                                    <c:forEach var="organizationObj" items="${organizationList}">
                                        <c:choose>
                                            <c:when test="${editUserProfile!=null && editUserProfile.organizationKey eq organizationObj.organizationKey}">
                                                <option value="${organizationObj.organizationKey}" selected="selected" title="${organizationObj.organizationName}">${organizationObj.organizationName}</option>  
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${organizationObj.organizationKey}" title="${organizationObj.organizationName}">${organizationObj.organizationName}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                                </dd>
                                <label id="errorOrgId" class="ux-msg-error-under"></label>
                                <spring:errors cssClass="error"  path="organizationKey" cssStyle="color: red; display: block; font-weight: bold"/>
                            </dl>
                            <div class="ux-clear"></div>
                            <dl>
                                <dt><label> First Name: <span>*</span></label> </dt>
                                <dd><input type="text" name="firstName" id="userFirstNameId"  value="${editUserProfile.firstName}" Placeholder="" />  </dd>
                                <label id="errorUserFirstNameId" class="ux-msg-error-under"></label>
                                <spring:errors cssClass="error"  path="firstName" cssStyle="color: red; display: block; font-weight: bold"/>
                            </dl>
                            <dl>
                                <dt><label> Middle Name: </label> </dt>
                                <dd><input type="text" name="middleName" id="userMiddleNameId"  value="${editUserProfile.middleName}" Placeholder="" />  </dd>
                                <label id="errorUserMiddleNameId" class="ux-msg-error-under"></label>
                                <spring:errors cssClass="error"  path="middleName" cssStyle="color: red; display: block; font-weight: bold"/>
                            </dl>
                            <dl>
                                <dt><label> Last Name: <span>*</span></label> </dt>
                                <dd><input type="text" name="lastName" id="userLastNameId"  value="${editUserProfile.lastName}" Placeholder="" />  </dd>
                                <label id="errorUserLastNameId" class="ux-msg-error-under"></label>
                                <spring:errors cssClass="error"  path="lastName" cssStyle="color: red; display: block; font-weight: bold"/>
                            </dl>
                            <dl>
                                <dt><label> Job Title: </label> </dt>
                                <dd><input type="text" name="jobTitleName" id="jobTitleNameId"  value="${editUserProfile.jobTitleName}" Placeholder="" />  </dd>
                                <label id="errorJobTitleNameId" class="ux-msg-error-under"></label>
                                <spring:errors cssClass="error"  path="jobTitleName" cssStyle="color: red; display: block; font-weight: bold"/>
                            </dl>
                            <dl>
                                <dt><label> Email ID: <span>*</span></label> </dt>
                                <dd><input type="text" name="email" id="userEmailId" readonly value="${editUserProfile.email}" Placeholder="" style="background-color: #ddd;" />  </dd>
                            </dl>
                            <dl>
                                <dt><label> Phone Number: </label> </dt>
                                <dd><input type="text" name="telephoneNumber" id="userPhoneId"  value="${editUserProfile.telephoneNumber}" Placeholder="" />  </dd>
                                <label id="errorUserPhoneId" class="ux-msg-error-under"></label>
                                <spring:errors cssClass="error"  path="telephoneNumber" cssStyle="color: red; display: block; font-weight: bold"/>
                            </dl>

                              <dl>
                                    <dt><label> Department: <span>*</span></label> </dt>
                                    <dd><input type="text" name="department" id="departmentId" value="${editUserProfile.department}" Placeholder="" />  </dd>
                                    <label id="errorDepartmentId" class="ux-msg-error-under"></label>
                                    <spring:errors cssClass="error"  path="department" cssStyle="color: red; display: block; font-weight: bold"/>
                                </dl>
                            
<!--                            <dl id="puser">
                                <dt>
                                    <label>Assign Roles: <span style="color:red">*</span></label>
                                </dt>
                                <dd><c:set value="${userRolesMap}" var="selectedRoleMap"/>
                                    <select id="selectedRole" name="selectedRole" multiple="multiple">
                                        <c:choose>
                                            <c:when test="${modelFlag == 1}">
                                                <c:forEach items="${roleList}" var="roleDTO">
                                                    <option value="${roleDTO.appRoleKey}" title="${roleDTO.appRoleName}" <c:forEach items="${editUserProfile.selectedRole}" var="roleID"><c:if test="${roleDTO.appRoleKey == roleID}"> selected="selected"</c:if></c:forEach>    >${roleDTO.appRoleName}</option>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach items="${roleList}" var="roleDTO">
                                                    <c:set value="0" var="selectedFlag"/>
                                                    <c:forEach var="entry" items="${selectedRoleMap}">
                                                        <c:if test="${entry.key eq roleDTO.appRoleKey}">
                                                            <c:set value="1" var="selectedFlag"/>
                                                            <option value="${roleDTO.appRoleKey}" selected="selected" title="${roleDTO.appRoleName}">${roleDTO.appRoleName}</option>
                                                        </c:if>
                                                    </c:forEach>

                                                    <c:if test="${selectedFlag eq 0}">
                                                        <option value="${roleDTO.appRoleKey}" title="${roleDTO.appRoleName}">${roleDTO.appRoleName}</option>
                                                    </c:if>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>

                                    </select>
                                </dd>
                                <label id="errorSelectedRole" class="ux-msg-error-under"></label>
                                <spring:errors cssClass="error"  path="selectedRole[0]" cssStyle="color: red; display: block; font-weight: bold"/>
                            </dl>  -->
                            <dl>
                                <dt><label> Status: <span>*</span></label></dt>
                                <dd><input name="rowStatusKey" type="radio" value="1" <c:if test="${editUserProfile.rowStatusKey eq constants.DB_ROW_STATUS_ACTIVE_VALUE}" >checked="checked"</c:if> id="active"/> Active
                                    <input name="rowStatusKey" type="radio" value="2" <c:if test="${editUserProfile.rowStatusKey eq constants.DB_ROW_STATUS_DE_ACTIVE_VALUE}" >checked="checked"</c:if> id="deactive"/> Deactive
                                </dd>
                            </dl>
                                
                            <c:choose>
                                <c:when test="${editUserProfile.rowStatusKey eq constants.DB_ROW_STATUS_DE_ACTIVE_VALUE}">
                                    <dl  class="LongTxt ux-margin-bottom-4t" id="editReason">
                                        <dt>
                                            <label>Reason for deactivation: <span>*</span> </label>
                                        </dt>
                                        <dd>
                                            <textarea class="TextareaLong ux-height-5t" name="statusComment" id="deactiveReason" >${editUserProfile.statusComment}</textarea>
                                         <label id="errordeactiveReason" class="error" style="color: red;float:left"></label> 
                                        <spring:errors cssClass="error"  path="statusComment" cssStyle="color: red; display: block; font-weight: bold"/>
                                        </dd>
                                    </dl>
                                </c:when>
                                <c:otherwise>
                                    <dl  class="LongTxt ux-margin-bottom-4t" id="newAddReason">
                                        <dt>
                                            <label>Reason for deactivation: <span>*</span> </label>
                                        </dt>
                                        <dd>
                                            <textarea class="TextareaLong ux-height-5t" name="statusComment" id="deactiveReason" ></textarea>
                                        <label id="errordeactiveReason" class="error" style="color: red;float:left"></label> 
                                        </dd>
                                    </dl>
                                </c:otherwise>
                            </c:choose>
                                 <div class="ux-clear"></div>
                                     <c:if test="${rolePermissionGroupMap.size()> 0}">
                                    <dl class="ux-width-25t" id="AssignDropdowndiv1" >
                                        <dt class="ux-width-25t"><label>Assign Role: <span>*</span>
                                            </label> <div class="ux-float-right ux-margin-bottom-1t ux-margin-top-1t"> 
                                                <input name="" type="button"  value="Add"  class="ux-btn-default-action UpdateAssignroleidbutton" />
                                            </div>
                                        </dt>
                                        <dd class="ux-width-25t">
                                            <select id="appRoleKeyID" name="appRoleKey">
                                                <option value="0" title="Select">Select</option>
                                                <c:forEach items="${roleList}" var="roleDTO">
                                                    <option value="${roleDTO.appRoleKey}" title="${roleDTO.appRoleName}">${roleDTO.appRoleName}</option>
                                                </c:forEach>
                                            </select>
                                        </dd>
                                         <label id="errorSelectedRole" class="ux-msg-error-under"></label>
                                         <label style="color: red">  ${roleDuplicateError}</label>
                                         <spring:errors cssClass="error"  path="selectedRole[0]" cssStyle="color: red; display: block; font-weight: bold"/>
                                    </dl>
                                     </c:if>
                                            </div>
                                 <c:if test="${permissionGroupMap.size()> 0}">
                                            <div id="WebappadminDiv" class="ux-width-full-inclusive">
                                                <table class="ux-tabl-data">
                                                    <thead>
                                                        <tr>
                                                            <th class="ux-width-full-2t ">Role Name</th>
                                                            <th class="ux-width-full-3t">Permission Group</th>
                                                            <th class="ux-width-full-5t"> Definition</th>
                                                            <th class="ux-width-full-1t">Action</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach var="permissionGroupMap" items="${permissionGroupMap}">
                                                            <tr>
                                                                <c:set var="roleDetails" value="${fn:split(permissionGroupMap.key, constants.SYMBOL_SPLITING)}" />
                                                                <c:set value="${roleDetails[0]}" var="roleKey"></c:set>
                                                                <c:set value="${roleDetails[1]}" var="roleName"></c:set>
                                                                <c:set var="permissionGrpList" value="${permissionGroupMap.value}"/>
                                                                <td rowspan="${permissionGrpList.size()}">${roleName}</td>
                                                                <c:set var="count1" value="0"/>
                                                                <c:forEach var="permissionGrpDTO" items="${permissionGrpList}">
                                                                    <c:if test="${count1 !=0}"> <tr> </c:if>
                                                                        <td >${permissionGrpDTO.permissionGroupName}</td>
                                                                        <td>${permissionGrpDTO.permissionGroupDesc}</td>
                                                                        <c:if test="${count1 !=0}"> </tr> </c:if>
                                                                        <c:if test="${count1 ==0}">   <td rowspan="${permissionGrpList.size()}"><a href="#" class="ux-icon-delete" title="Delete" id="roleDelId" onclick="updateUserRoleDelFn(${roleKey})">Delete</a>  </td></c:if>
                                                                    <c:set var="count1" value="1"/>
                                                                </c:forEach>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </c:if>
                            </div>
                                            </div>
                                    <c:forEach var="selectedRolesArr" items="${selectedRolesList}">
                                        <input type="hidden" value="${selectedRolesArr}" name="selectedRole"/>
                                    </c:forEach> 
                                    <input type="hidden" value="0" name="delRoleKey" id="delRoleKeyID"/>
                <div class="ux-clear"></div>
<c:if test="${rolePermissionGroupMap.size()> 0}">
                        <div class="ux-panl PermsnGroup">

                            <div class="ux-panl-header ux-panl-with-underline">
                                <h2>Available Role(s) for the selected User Type</h2>
                            </div>
                            <div class="ux-panl-content ">
                                <c:forEach var="rolePermissionGroupMap" items="${rolePermissionGroupMap}">
                                    <c:set var="roleDetails" value="${fn:split(rolePermissionGroupMap.key, constants.SYMBOL_SPLITING)}" />
                                    <c:set value="${roleDetails[0]}" var="roleKey"></c:set>
                                    <c:set value="${roleDetails[1]}" var="roleName"></c:set>

                                        <div class="ux-accordion-panl ux-width-full-inclusive ux-margin-top-1t" id="PermsnGroup1">
                                            <div class="ux-accordion-panl-header">
                                                <h2><a class="menuitem submenuheader " href="#" >${roleName}</a> 
                                            </h2>
                                        </div>
                                        <div class="submenu ux-padding-1t ux-width-full-inclusive">
                                            <table class="ux-tabl-data">
                                                <thead>
                                                    <tr>
                                                        <th>Permission Group</th>
                                                        <th>Definition</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:set var="permissionGrpList" value="${rolePermissionGroupMap.value}"/>
                                                    <c:forEach var="permissionGrpDTO" items="${permissionGrpList}">
                                                        <tr class="Adminrow">
                                                            <td>${permissionGrpDTO.permissionGroupName}</td>
                                                            <td>${permissionGrpDTO.permissionGroupDesc}</td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>

                        </div>
                    </c:if>

                </spring:form>
            </div>
            <!-- End: Content -->

            <!-- Footer : Include -->
            <jsp:include page="../includes/footer.jsp"/>
            <!-- Footer : Include -->
        </div>
       
    </body>
</html>


