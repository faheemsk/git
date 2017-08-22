<%-- 
    Document   : addpermissiongroup
    Created on : Apr 13, 2016, 10:19:59 AM
    Author     : sathuluri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld" %>
<%@taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=9; IE=10; IE=EDGE" />
        <meta name="viewport" content="width=device-width" />
        <title>IRMaaS</title>
        <!-- CSS : Include -->
        <jsp:include page="../includes/commoncss.jsp"/>
        <link rel="stylesheet" href="<c:url value="/appcss/addpermissiongroup.css"/>"/>
        <!-- CSS : Include -->
        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/appjs/addpermissiongroup.js"/>"></script>
        <!-- JS : Include -->  
        <!-- Header : Include -->
        <jsp:include page="../includes/header.jsp"/>
        <!-- Header : Include -->  
    </head>
    <body>
        <form name="permissnfrm" method="post" hidden></form>
        <!-- Start: Wrapper -->
        <div id="ux-wrapper"> 
            <!-- Start: Breadcrumbs -->
            <ul class="ux-brdc ux-margin-left-1t" >

                <li><a href="#" onclick="headerFn('manageRolepage.htm')" title="Manage Roles & Permissions">Manage Roles & Permissions</a></li>
                <li><a href="#" onclick="headerFn('permissiongroupworklist.htm')" title="Manage Permission Groups">Manage Permission Groups</a></li>
                    <c:if test="${permissionGroupDTO.permissionGroupKey <= 0}">
                    <li>Add  Permission Group</li>
                    </c:if>
                    <c:if test="${permissionGroupDTO.permissionGroupKey > 0}">
                    <li>Update Permission Group</li>
                    </c:if>
            </ul>
            <!-- End: Breadcrumbs -->

            <!--Start: Left navigation-->
            <jsp:include page="../includes/leftpanel.jsp"/>
            <!--End: Left navigation-->

            <!-- content starts -->
            <div class="ux-content">
                <h1><c:if test="${permissionGroupDTO.permissionGroupKey > 0}">Update Permission Group</c:if>
                    <c:if test="${permissionGroupDTO.permissionGroupKey <= 0}">Add Permission Group</c:if>
                        <div class="ux-float-right ux-margin-bottom-1t ">
                        <c:if test="${permissionGroupDTO.permissionGroupKey > 0}">
                            <input name="" type="button" value="Update" title="Update" class="ux-btn-default-action " onclick="saveUpdatePermissionGroup();
                                   " />
                        </c:if>
                        <c:if test="${permissionGroupDTO.permissionGroupKey <= 0}">
                            <input name="" type="button" value="Save" title="Save" class="ux-btn-default-action " onclick="saveUpdatePermissionGroup();
                                   " />
                        </c:if>
                        <input name="" type="button" value="Cancel" title="Cancel" class="ux-btn-default-action " onclick="cancelPermissionGroup()"/></div>
                </h1>
                <spring:form name="saveOrUpdatePermissionGroup" action="#" method="POST" id="saveOrUpdatePermissionGroup" commandName="permissionGroupDTO">
                    <div class="ux-panl ux-float-left" id="role">
                        <div class="ux-panl-header ux-panl-with-underline">
                            <h2> Permission Group Details</h2>
                        </div>
                        <div class="ux-panl-content ux-padding-top-none">
                            <div class="ux-FormStyleLeftAlign">
                                <p class="ux-form-required ux-margin-top-1t"> <strong class="ux-labl-required">*</strong> Required </p>
                                <input type="hidden" <c:if test="${permissionGroupDTO.encPermissionGroupKey != null}">value="${permissionGroupDTO.encPermissionGroupKey}"</c:if> 
                                       name="encPermissionGroupKey"/>
                                <c:if test="${permissionGroupDTO.permissionGroupKey > 0}">
                                    <dl>
                                        <dt>
                                            <strong>ID:</strong>
                                            <dd>
                                                ${permissionGroupDTO.permissionGroupKey}
                                            </dd>
                                        </dt>
                                    </dl>
                                </c:if>

                                <dl>
                                    <dt>
                                        <strong>Permission Group Name:</strong>
                                        <span>*</span></dt>
                                    <dd>
                                        <c:if test="${permissionGroupDTO.permissionGroupKey > 0}">
                                            <input type="text" id="permissionGroupNameValue" class="ux-width-24t" value="${permissionGroupDTO.permissionGroupName}" name="permissionGroupName"/>
                                                            <input type="hidden" id="originalGroupName" name="encPermissionGroupName" value="${permissionGroupDTO.encPermissionGroupName}"/>
                                                            <br/>
                                                            <label id="errorGroupNameValue" class="ux-width-40t"/>

                                            </c:if>
                                            <c:if test="${permissionGroupDTO.permissionGroupKey <= 0}">
                                                <input type="text" id="permissionGroupNameValue" class="ux-width-24t" value="${permissionGroupDTO.permissionGroupName}" name="permissionGroupName"/>
                                                                <br/>
                                                                <label id="errorGroupNameValue" class="ux-width-40t"/>
                                                </c:if>
                                                <spring:errors cssClass="error"  path="permissionGroupName" cssStyle="color: red;width:330px; display: block; font-weight: bold"/>
                                            </dd>
                                        </dl>

                                        <dl class="LongTxt">
                                            <dt>
                                                <strong>Permission Group Definition:</strong>
                                                <span>*</span>
                                            </dt>
                                            <dd>
                                                <c:if test="${permissionGroupDTO.permissionGroupKey > 0}">
                                                    <textarea class="ux-width-25t ux-height-5t" id="permissionGroupDefinitionValue" name="permissionGroupDesc">${permissionGroupDTO.permissionGroupDesc}</textarea><br/>
                                                    <label id="errorGroupDescValue" class="ux-width-40t"/>

                                                </c:if>
                                                <c:if test="${permissionGroupDTO.permissionGroupKey <= 0}">
                                                    <textarea class="ux-width-25t ux-height-5t" id="permissionGroupDefinitionValue" name="permissionGroupDesc">${permissionGroupDTO.permissionGroupDesc}</textarea><br/>
                                                    <label id="errorGroupDescValue" class="ux-width-40t"/>
                                                </c:if>
                                                <spring:errors cssClass="error"  path="permissionGroupDesc" cssStyle="color: red;width:250px; display: block; font-weight: bold"/>
                                            </dd>
                                        </dl>

                                        <dl>
                                            <dt>
                                                <strong> Status:</strong>
                                                <span>*</span></dt>
                                            <dd>
                                                <c:if test="${permissionGroupDTO.permissionGroupKey <= 0}">
                                                    <c:forEach var="lookUpDTO" items="${lookUpList}">
                                                        <c:if test="${lookUpDTO.lookUpEntityName != constants.DB_ROW_STATUS_DELETE}">
                                                            <c:if test="${lookUpDTO.lookUpEntityName == permissionGroupDTO.permissionGroupStatusValue}">
                                                                <input name="permissionGroupStatusValue" type="radio" value="${lookUpDTO.lookUpEntityName}" checked="checked" class="statusValue" id="${lookUpDTO.masterLookupKey}"/>
                                                            </c:if>
                                                            <c:if test="${lookUpDTO.lookUpEntityName != permissionGroupDTO.permissionGroupStatusValue}">
                                                                <input name="permissionGroupStatusValue" type="radio" value="${lookUpDTO.lookUpEntityName}" class="statusValue" id="${lookUpDTO.masterLookupKey}"/>
                                                            </c:if>
                                                            ${lookUpDTO.lookUpEntityName}
                                                        </c:if>
                                                    </c:forEach>
                                                </c:if>
                                                <c:if test="${permissionGroupDTO.permissionGroupKey > 0}">
                                                    <c:forEach var="lookUpDTO" items="${lookUpList}">
                                                        <c:if test="${lookUpDTO.lookUpEntityName != constants.DB_ROW_STATUS_DELETE}">
                                                            <c:if test="${lookUpDTO.lookUpEntityName == permissionGroupDTO.permissionGroupStatusValue}">
                                                                <input name="permissionGroupStatusValue" type="radio" value="${lookUpDTO.lookUpEntityName}" checked="checked" class="statusValue" id="${lookUpDTO.masterLookupKey}"/>
                                                            </c:if>
                                                            <c:if test="${lookUpDTO.lookUpEntityName != permissionGroupDTO.permissionGroupStatusValue}">
                                                                <input name="permissionGroupStatusValue" type="radio" value="${lookUpDTO.lookUpEntityName}" class="statusValue" id="${lookUpDTO.masterLookupKey}"/>
                                                            </c:if>
                                                            ${lookUpDTO.lookUpEntityName}
                                                        </c:if>
                                                    </c:forEach>
                                                </c:if><br/>
                                                <label id="errorGroupStatus" class="ux-width-40t"/>
                                                <spring:errors cssClass="error"  path="permissionGroupStatusValue" cssStyle="color: red;width:250px; display: block; font-weight: bold"/>
                                            </dd>
                                        </dl>

                                        <dl class="LongTxt" id="rs" style="display: none">
                                                <dt>
                                                    <strong>Reason for deactivation:<span> * </span> </strong>
                                                </dt>
                                                <dd>
                                                    <c:if test="${permissionGroupDTO.permissionGroupKey <= 0}">
                                                        <textarea class="ux-width-25t ux-height-5t" id="reasonValue" name="reason">${permissionGroupDTO.reason}</textarea><br/>
                                                        <label id="errordeactiveReason" class="error"></label>
                                                    </c:if>
                                                    <c:if test="${permissionGroupDTO.permissionGroupKey > 0}">
                                                        <textarea class="ux-width-25t ux-height-5t" id="reasonValue" name="reason">${permissionGroupDTO.reason}</textarea><br/>
                                                        <label id="errordeactiveReason" class="error"></label> 
                                                    </c:if>
                                                    <spring:errors cssClass="error"  path="reason" cssStyle="color: red;width:330px; display: block; font-weight: bold"/>
                                                </dd>
                                            </dl>
                                            <div class="ux-clear"></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="ux-float-left ux-width-full-inclusive">
                                    <div class="ux-panl ux-float-left" id="role">
                                        <div class="ux-panl-header ux-panl-with-underline">
                                            <h2>Detailed Permissions</h2>
                                        </div>
                                        <div class="ux-panl-content ux-padding-top-none">
                                            <label class="" id="errorPermissionCheckbox"></label>
                                            <span class="ux-float-left ux-margin-top-1t ux-margin-bottom-1t ux-form-required">Select Permissions to Assign to Group</span>
                                            <spring:errors cssClass="error"  path="permissionGroupKeyChkIds" cssStyle="color: red;width:230px; display: block; font-weight: bold"/>
                                            <div class="ux-clear"></div>
                                            <table  id="dataTable"  width="100%" class="ux-tabl-data">
                                                <thead>
                                                    <tr>
                                                        <td></td>
                                                        <td></td>
                                                        <td></td>
                                                    </tr>
                                                    <tr>
                                                        <!--IN:020650: For remove sorting for checkbox column-->
                                                        <th id="removeSortingForCheckbox"><input name="check" type="checkbox" value="" id="headCheckbox" onchange="checkAll(this)"/></th>
                                                        <!--IN:020650: For remove sorting for checkbox column-->
                                                        <th class="ux-width-full-3t">Permission Name</th>
                                                        <th>Permission Definition</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:if test="${permissionGroupDTO.permissionGroupKey <= 0}">
                                                        <c:forEach var="permissionWorkList" items="${allPermissions}">
                                                            <tr>
                                                                <c:set var="checkBool" value="0"/>
                                                                <c:if test="${permissionIDs != null}">
                                                                    <c:forEach var="permKey" items="${permissionIDs}">
                                                                        <c:if test="${permKey == permissionWorkList.permissionKey}">
                                                                            <c:set var="checkBool" value="1"/>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </c:if>

                                                                <td>
                                                                    <c:choose>
                                                                        <c:when test="${checkBool == 0}">
                                                                            <input type="checkbox" id="${permissionWorkList.permissionKey}" 
                                                                                   name="permissionGroupKeyChkIds"
                                                                                   class="checkboxGroup" 
                                                                                   value="${permissionWorkList.permissionKey}@${permissionWorkList.moduleId}@${permissionWorkList.menuId}@${permissionWorkList.subMenuId}" onchange="singleCheckbox(this)"/>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <input type="checkbox" id="${permissionWorkList.permissionKey}"
                                                                                   name="permissionGroupKeyChkIds"
                                                                                   class="checkboxGroup" 
                                                                                   value="${permissionWorkList.permissionKey}@${permissionWorkList.moduleId}@${permissionWorkList.menuId}@${permissionWorkList.subMenuId}" onchange="singleCheckbox(this)" checked/>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </td>
                                                                <td id="permissionName${permissionWorkList.permissionKey}">${permissionWorkList.permissionName}</td>
                                                                <td id="permissionDescription${permissionWorkList.permissionKey}">${permissionWorkList.permissionDescription}</td>
                                                            </tr>
                                                        </c:forEach>
                                                    </c:if>
                                                    <c:if test="${permissionGroupDTO.permissionGroupKey > 0}">
                                                        <c:if test="${(allPermissions != null) && (fn:length(allPermissions)>0)}">
                                                            <c:forEach var="savedpermissionsList" items="${allPermissions}">
                                                                <tr>
                                                                    <td>
                                                                        <c:set var="editCheckBool" value="0"/>
                                                                        <c:choose>
                                                                            <c:when test="${permissionIDs != null}">
                                                                                <c:forEach var="permIds" items="${permissionIDs}">
                                                                                    <c:if test="${permIds == savedpermissionsList.permissionKey}">
                                                                                        <c:set var="editCheckBool" value="1"/>
                                                                                    </c:if>
                                                                                </c:forEach>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <c:if test="${savedpermissionsList.permissionTypeKey == 1}">
                                                                                    <c:set var="editCheckBool" value="1"/>
                                                                                </c:if>
                                                                            </c:otherwise>
                                                                        </c:choose>

                                                                        <c:choose>
                                                                            <c:when test="${editCheckBool == 1}">
                                                                                <input type="checkbox" id="${savedpermissionsList.permissionKey}" 
                                                                                       name="permissionGroupKeyChkIds" class="checkboxGroup"
                                                                                       checked value="${savedpermissionsList.permissionKey}@${savedpermissionsList.moduleId}@${savedpermissionsList.menuId}@${savedpermissionsList.subMenuId}" onchange="singleCheckbox(this)"/>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <input type="checkbox" id="${savedpermissionsList.permissionKey}" 
                                                                                       name="permissionGroupKeyChkIds" class="checkboxGroup" 
                                                                                       value="${savedpermissionsList.permissionKey}@${savedpermissionsList.moduleId}@${savedpermissionsList.menuId}@${savedpermissionsList.subMenuId}" onchange="singleCheckbox(this)"/>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </td>

                                                                    <td id="permissionName${savedpermissionsList.permissionKey}">${savedpermissionsList.permissionName}</td>
                                                                    <td id="permissionDescription${savedpermissionsList.permissionKey}">${savedpermissionsList.permissionDescription}</td>
                                                                </tr>
                                                            </c:forEach>
                                                        </c:if>
                                                    </c:if>
                                                </tbody>
                                            </table>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </spring:form>
                        <!-- content ends -->

                        <!-- Start: Footer -->
                        <jsp:include page="../includes/footer.jsp"/>
                        <!-- End: Footer -->
                    </div>
                    <!-- End: Wrapper -->
                    <script type="text/javascript" src="<c:url value="/appjs/editheadercheckbox.js"/>"></script>
                </body>
            </html>
