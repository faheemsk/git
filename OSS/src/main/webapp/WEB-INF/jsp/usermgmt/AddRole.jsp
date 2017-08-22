<%-- 
    Document   : AddRole
    Created on : Apr 12, 2016, 5:43:28 PM
    Author     : akeshavulu
--%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@taglib  uri="/WEB-INF/tlds/spring-form.tld" prefix="form" %>
<%@taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<HTML xmlns="http://www.w3.org/1999/xhtml">
    <HEAD>
        <title>IRMaaS</title>
        <!-- CSS : Include -->
        <jsp:include page="../includes/commoncss.jsp"/>
        <link rel="stylesheet" href="<c:url value="/appcss/AddRole.css"/>" />
        <!-- CSS : Include -->
        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/appjs/AddRoleJS.js"/>"></script>
        <!-- JS : Include -->
        <style type="text/css">
            .ux-tabl-data tbody tr{
                height:50px;
            }
        </style>
    </HEAD>
    <BODY >
        <!-- Start: Wrapper -->
        <div id="ux-wrapper"> 
            <!--Header starts here-->
            <jsp:include page="../includes/header.jsp"/>
            <!--Header ends here--> 

            <!-- Start: Breadcrumbs -->
            <ul class="ux-brdc ux-margin-left-1t" >
                
                <li><a href="#" title="Manage Roles & Permissions" onclick="headerFn('manageRolepage.htm')">Manage Roles & Permissions</a></li>
                <li><a href="#" title="Manage Roles" onclick="headerFn('manageRolepage.htm')">Manage Roles</a></li>
                    <c:if test="${roleDTO.appRoleKey <= 0}">
                    <li>Add Role</li>
                    </c:if>
                    <c:if test="${roleDTO.appRoleKey > 0}">
                    <li>Edit Role</li>
                    </c:if>
            </ul>
            <!-- End: Breadcrumbs -->

            <!--Left navigation starts here-->
            <jsp:include page="../includes/leftpanel.jsp"/>
            <!--Left navigation ends here--> 

            <!-- content starts -->
            <div class="ux-content">
                <h1>  
                    <c:if test="${roleDTO.appRoleKey != 0}">Update Role</c:if>
                    <c:if test="${roleDTO.appRoleKey == 0}">Add Role</c:if><div class="ux-float-right ux-margin-bottom-1t "> 
                        <c:if test="${roleDTO.appRoleKey == 0}">
                            <input name="" type="button" value="Save"  title="Save" id="saverole" class="ux-btn-default-action " onclick="saveRoleDetails()" /> 
                        </c:if>
                        <c:if test="${roleDTO.appRoleKey != 0}">
                            <input name="" type="button" value="Update"  title="Update" id="updaterole" class="ux-btn-default-action " onclick="updateRolePrmnGrpDetails()" />
                        </c:if>
                        <input name="" type="button" value="Cancel"  title="Cancel" class="ux-btn-default-action " onclick="addRoleCancel()"/></div>
                </h1>
                <!--form starts-->        
                <spring:form id="addrole" name="addrole" commandName="addrole">
                    <div class="ux-panl ux-float-left" id="role">
                        <div class="ux-panl-header ux-panl-with-underline">
                            <h2> Role Details</h2>
                        </div>
                        <div class="ux-panl-content ux-padding-top-none">
                            <div class="ux-FormStyleLeftAlign">
                                <p class="ux-form-required ux-margin-top-1t"> <strong class="ux-labl-required">*</strong> Required </p>
                                <c:if test="${roleDTO.appRoleKey > 0}">
                                    <dl id="roleID">
                                        <dt>
                                            <label>ID:</label>
                                        </dt>
                                        <dd>
                                            ${roleDTO.appRoleKey}
                                        </dd>
                                    </dl>
                                    
                                </c:if>
                                <spring:hidden path="encAppRoleKey" htmlEscape="true" id="encAppRoleKey" />
                                <dl>
                                    <dt>
                                        <label>Role Name: <span>*</span></label>
                                    </dt>
                                    <dd>
                                        <!--<input type="text" name="appRoleName"  id="rolename"  value="" Placeholder="" />-->
                                        <spring:input type="text" path="appRoleName" htmlEscape="true" id="appRoleName" cssClass="ux-width-24t" />
                                        <spring:errors cssClass="error"  path="appRoleName" cssStyle="color: red;width:190px; display: block; font-weight: bold"/>
                                        <c:if test="${roleDTO.appRoleKey > 0}">
                                            <input type="hidden" id="existingRoleName" value="${roleDTO.appRoleName}"/>
                                        </c:if>
                                        <span id="errorMsgRoleName" class="error" style="color: red;width:190px; display: block; font-weight: bold"  ></span>
                                    </dd>

                                </dl>
                                <dl class="LongTxt" >
                                    <dt>
                                        <label>Role  Definition: <span>*</span></label>
                                    </dt>
                                    <dd>
                                        <!--<textarea class="ux-width-25t ux-height-5t" name="appRoleDescription" id="roledescription"></textarea>-->
                                        <spring:textarea cssClass="ux-width-25t ux-height-5t" id="appRoleDescription" htmlEscape="true" style="font-size:12px; color:#333; font-weight:normal;" path="appRoleDescription" />
                                        <span id="errorMsgRoleDescription" class="error" style="color: red;width:190px; display: block; font-weight: bold"></span>
                                        <spring:errors cssClass="error"  path="appRoleDescription" cssStyle="color: red;width:190px; display: block; font-weight: bold"/>
                                    </dd>

                                </dl>
                                <dl>
                                    <dt >
                                        <label > Status: <span>*</span></label>
                                    </dt>
                                    <dd>
                                        <c:if test="${roleDTO.appRoleKey == 0}">
                                            <spring:radiobutton  id="activeradio" path="rowStatusValue" name="appRoleStatus111" checked="true" value="active" /> 
                                            Active 
                                        </c:if>
                                        <c:if test="${roleDTO.appRoleKey > 0}">
                                            <spring:radiobutton  id="activeradio" path="rowStatusValue" name="appRoleStatus111" value="active" /> 
                                            Active 
                                        </c:if>
                                        <spring:radiobutton id="deactiveradio" path="rowStatusValue" name="appRoleStatus" value="deactive" /> 
                                        Deactive
                                        <span id="errorMsgStatus" class="error" style="color: red;width:190px; display: block; font-weight: bold"  ></span>
                                    </dd>
                                        
                                </dl>
                                <div class="ux-clear"></div>

                                <dl class="LongTxt" id="reason" style="display: none">
                                        <dt>
                                            <label>Reason for Deactivation: <span>*</span></label>
                                        </dt>
                                        <dd>
                                        <spring:textarea cssClass="ux-width-25t ux-height-5t" id="appRoleComments" htmlEscape="true"  path="appRoleComments" />
                                        <span id="errorMsgReasonDeactive" class="error" style="color: red;width:215px; display: block; font-weight: bold"  ></span>
                                        <spring:errors cssClass="error"  path="appRoleComments" cssStyle="color: red;width:190px; display: block; font-weight: bold"/>
                                    </dd>
                                </dl> 
                                <div class="ux-clear"></div>
                            </div>
                        </div>
                    </div>
                    <div class="ux-float-left ux-width-full-inclusive">
                        <div class="ux-panl ux-float-left" id="role">
                            <div class="ux-panl-header ux-panl-with-underline">
                                <h2> Grant Role Permission Groups</h2>
                            </div>
                            <div class="ux-panl-content ux-padding-top-none">
                                <span class="ux-float-left ux-margin-top-1t ux-form-required ux-margin-bottom-1t">Select Permission Groups to Assign to Role</span>
                                <span id="errorMsgPrmnGrp" class="error" style="color: red;width:190px; display: block; font-weight: bold"  ></span>
                                <spring:errors cssClass="error"  path="permissionGroupIDs" cssStyle="color: red;width:190px; display: block; font-weight: bold"/>
                                <div class="ux-clear"></div>

                                <table  id="dataTable" class="ux-tabl-data">
                                    <thead>
                                        <tr>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <th id="hchk">
                                                <input name="headcheck" type="checkbox" value=""  
                                                       id="headCheckbox" onchange="checkAll(this)"/></th>
                                            <th class="ux-width-full-3t">Permission Group</th>
                                            <th>Permission Group Definition</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:if test="${roleDTO.appRoleKey == 0}">
                                            <c:set var="loopCount" value="0"/>
                                            <c:forEach var="rolePermissionGroupsVals" items="${permissionGroupList}">
                                                <tr>
                                                   <%--<td><input type="checkbox" id="${rolePermissionGroupsVals.permissionGroupKey}" name="prnGrpID" class="checkboxGroup" onchange="singleCheckbox(this)" value="${rolePermissionGroupsVals.permissionGroupKey}"/></td>--%>
                                                   
                                                   <c:set var="checkBool" value="0"/>
                                                   <c:if test="${permissionGroupIDs != null}">
                                                       <c:forEach var="permGroup" items="${permissionGroupIDs}">
                                                           <c:if test="${permGroup == rolePermissionGroupsVals.permissionGroupKey}">
                                                               <c:set var="checkBool" value="1"/>
                                                           </c:if>
                                                       </c:forEach>
                                                   </c:if>
                                                   
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${checkBool == 0}">
                                                                <input type="checkbox" id="prnGrpID" 
                                                                       name="permissionGroupIDs"
                                                                       class="checkboxGroup" 
                                                                       value="${rolePermissionGroupsVals.permissionGroupKey}"/>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type="checkbox" id="prnGrpID" 
                                                                       name="permissionGroupIDs"
                                                                       class="checkboxGroup" 
                                                                       value="${rolePermissionGroupsVals.permissionGroupKey}" checked/>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td id="permissionName${rolePermissionGroupsVals.permissionGroupKey}">${rolePermissionGroupsVals.permissionGroupName}</td>
                                                    <td id="roleDescription${rolePermissionGroupsVals.permissionGroupKey}">${rolePermissionGroupsVals.permissionGroupDesc}</td>
                                                    <td></td>
                                                </tr>
                                                <c:set var="loopCount" value="${loopCount+1}"/>
                                            </c:forEach>
                                        </c:if>
                                        <c:if test="${roleDTO.appRoleKey != 0}">
                                            <c:if test="${(allPermissionGroupList != null) && (fn:length(allPermissionGroupList)>0)}">
                                                <c:set var="loopCount" value="0"/>
                                                <c:forEach var="editPermissionGroupsVals" items="${allPermissionGroupList}">
                                                    <tr>
                                                        <td>
                                                            <c:set var="editCheckBool" value="0"/>
                                                            <c:choose>
                                                                <c:when test="${permissionGroupIDs != null}">
                                                                    <c:forEach var="permGroup" items="${permissionGroupIDs}">
                                                                        <c:if test="${permGroup == editPermissionGroupsVals.permissionGroupKey}">
                                                                            <c:set var="editCheckBool" value="1"/>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <c:if test="${(editPrmnGrpList != null)&&(fn:length(editPrmnGrpList)>0)}">
                                                                        <c:forEach var="selPermGrp" items="${editPrmnGrpList}">
                                                                            <c:if test="${selPermGrp.permissionGroupKey==editPermissionGroupsVals.permissionGroupKey}">
                                                                                <c:set var="editCheckBool" value="1"/>
                                                                            </c:if>
                                                                        </c:forEach>
                                                                    </c:if>
                                                                </c:otherwise>
                                                            </c:choose>
                                                            
                                                            
                                                            <c:choose>
                                                                <c:when test="${editCheckBool == 1}">
                                                                    <input type="checkbox" id="prnGrpID" 
                                                                           name="permissionGroupIDs" class="checkboxGroup"
                                                                           checked value="${editPermissionGroupsVals.permissionGroupKey}"/>

                                                                </c:when>
                                                                <c:otherwise>
                                                                    <input type="checkbox" id="prnGrpID" 
                                                                           name="permissionGroupIDs" class="checkboxGroup" 
                                                                           value="${editPermissionGroupsVals.permissionGroupKey}"/>
                                                                </c:otherwise>
                                                            </c:choose>
                                                                
                                                        </td>
                                                        <td id="permissionName${editPermissionGroupsVals.permissionGroupKey}">${editPermissionGroupsVals.permissionGroupName}</td>
                                                        <td id="roleDescription${editPermissionGroupsVals.permissionGroupKey}">${editPermissionGroupsVals.permissionGroupDesc}</td>
                                                        <td></td>
                                                    </tr>
                                                    <c:set var="loopCount" value="${loopCount+1}"/>
                                                </c:forEach>
                                            </c:if>
                                        </c:if>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!-- Tabbed Panel -->
                    <c:if test="${roleDTO.appRoleKey != 0}">
                        <input type="hidden" name="appRoleKey" id="appRoleKey" value="${roleDTO.appRoleKey}"/>
                    </c:if>
                </spring:form>
                <!--form Ends-->
            </div>
            <!-- content ends --> 
            <!-- Begin: Footer -->
            <jsp:include page="../includes/footer.jsp"/>
            <!-- End: Footer --> 
        </div>
        <!-- End: Wrapper --> 
        <script type="text/javascript" src="<c:url value="/appjs/editheadercheckbox.js"/>"></script>
    </BODY>
</HTML>
