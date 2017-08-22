<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld" %>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="constants" />
<un:useConstants className="com.optum.oss.constants.PermissionConstants" var="permissionConstants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
    <head>

        <title>IRMaaS</title>
        <jsp:include page="../includes/commoncss.jsp"/>
        <link rel="stylesheet" href="<c:url value="/css/jquery.dataTables.css"/>" />
        <jsp:include page="../includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/appjs/ViewPermission.js"/>"></script>
        <link href="<c:url value="/appcss/viewpermissions.css"/>" rel="stylesheet"/>
        <c:set var="userType" value="${constants.DB_USER_TYPE_ADMIN}"/>
        <c:if test="${sessionScope.USER_SESSION != null}">
            <c:set var="userProfile" value="${sessionScope.USER_SESSION}"/>
            <c:set var="userType" value="${userProfile.userTypeObj.lookUpEntityName}"/>
        </c:if>

    </head>
    <BODY >
        <div id="ux-wrapper"> 

            <!--Header starts here-->
            <jsp:include page="../includes/header.jsp"/>
            <!--Header ends here-->
            <ul class="ux-brdc ux-margin-left-1t">

                <li><a title="Manage Roles & Permissions" href="#" onclick="headerFn('manageRolepage.htm')">Manage Roles & Permissions</a></li>
                <li>View Detailed Permissions</li>
            </ul>

            <jsp:include page="../includes/leftpanel.jsp"/>
            <div class="ux-content">
                <h1>View Detailed Permissions</h1>
                <c:if test="${valError!=null}">
                    <div  class="ux-msg-error" >    
                        <c:out value="${valError}"/>
                    </div>
                </c:if>
                <c:if test="${successMsg!=null}">
                  
                    <div  class="ux-msg-success" >    
                        <c:out value="${successMsg}"/>
                    </div>
                </c:if>

                <!--                <form name="viewdetailedpermission" method="post">
                                    <input type="hidden" name="permissionDescription" id="hdndefination"/>
                                    <input type="hidden" name="permissionKey" id="hdnpermissionKey"/></form>-->
                <div class="ux-float-left ux-width-full-inclusive">
                    <spring:form id="viewdetailedpermission" name="viewdetailedpermission" action="savedefinition.htm" commandName="permissionDto">
                        <input type="hidden" name="permissionDescription" value="" id="permissionDescription"/>
                        <input type="hidden" name="encPermissionKey" value="" id="encPermissionKey"/>
                        <table class="display" id="permissionlist">
                        <thead>
                            <tr>
                                <td class="uid">   </td>
                                <td></td>
                                <td> </td>
                                <td class="dateid"><input type="text" onchange="$.fn.dateFilter(this, '#permissionlist')" id="lastupdate" readonly class="ux-width-7t"/></td>
                                <td><span><a href="#" onclick="$.fn.SearchFilterClear(this, '#permissionlist')" title="Refresh"><img src="images/refresh.png" />
                                        </a></span>
                                </td>
                            </tr>
                            <tr>
                                <th>ID   </th>
                                <th> Permission Name  </th>
                                <th class="ux-width-full-3t">Definition </th>
                                <th>Last Updated Date</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:set var="count" value="0"/>
                            <c:forEach var="masterLookUpList" items="${masterLookUpList}">
                                <c:set var="count" value="${count+1}"/>
                                <tr>
                                        <td>${count}</td>
                                        <td>${masterLookUpList.permissionName}</td>
                                        <input type="hidden" id="description${masterLookUpList.permissionKey}"  value="${masterLookUpList.permissionDescription}"/>
                                        <td><span class="edit" id="ed${masterLookUpList.permissionKey}">${masterLookUpList.permissionDescription}</span> 
                                            <span class="save" id="sv${masterLookUpList.permissionKey}">
                                                <textarea class="ux-width-20t ux-height-5t" id="editTxt${masterLookUpList.permissionKey}">${masterLookUpList.permissionDescription}</textarea></span>
                                                <c:choose>
                                                    <c:when test="${userType==constants.DB_USER_TYPE_ADMIN}">
                                                    <a href="javascript:void(0);" id="edimg${masterLookUpList.permissionKey}" class="ux-icon-edit edit" title="Edit" onclick="fnEdit('${masterLookUpList.permissionKey}')"></a> 
                                                </c:when>
                                                <c:otherwise>
                                                    <c:if test="${null != permissionSet}">
                                                        <c:if test="${permissionSet.contains(permissionConstants.EDIT_DETAILED_PERMISSIONS)}">
                                                            <a href="javascript:void(0);" id="edimg${masterLookUpList.permissionKey}" class="ux-icon-edit edit" title="Edit" onclick="fnEdit('${masterLookUpList.permissionKey}')"></a> 
                                                        </c:if>
                                                    </c:if>
                                                </c:otherwise>
                                            </c:choose>
<!--                                            <input type="submit" id="svimg${masterLookUpList.permissionKey}" class="ux-icon-save save" title="Save" onclick="save('${masterLookUpList.permissionKey}', '${fn:trim(masterLookUpList.encPermissionKey)}','${masterLookUpList.permissionKey}')"></input>-->

                                            <a href="#" id="svimg${masterLookUpList.permissionKey}" 
                                               class="ux-icon-save save" title="Save" 
                                               onclick="save('${masterLookUpList.permissionKey}', '${fn:trim(masterLookUpList.encPermissionKey)}', '${masterLookUpList.permissionKey}')"></a>
                                            <label id="errorMessage${masterLookUpList.permissionKey}"/>
                                        </td>
                                        <td>${masterLookUpList.modifiedDate}</td> 
                                        <td></td> 
                                </tr>
                            </c:forEach>
                        </tbody>
                        </table>
                    </spring:form>
                </div>
            </div>


            <!--Begin: Footer--> 
            <jsp:include page="../includes/footer.jsp"/>
            <!--End: Footer-->  
        </div>

    </BODY>
</HTML>










