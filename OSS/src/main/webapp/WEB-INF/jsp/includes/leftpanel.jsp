<%-- 
    Document   : leftpanel
    Created on : Apr 15, 2016, 1:53:42 PM
    Author     : hpasupuleti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld" %>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="constants" />

<c:set var="userType" value="${constants.DB_USER_TYPE_ADMIN}"/>
<c:set var="menuList" value=""/> 
<c:if test="${sessionScope.USER_SESSION != null}">
    <c:set var="userProfile" value="${sessionScope.USER_SESSION}"/>
    <c:set var="userType" value="${userProfile.userTypeObj.lookUpEntityName}"/>
    <c:set var="menuList" value="${userProfile.menuList}"/> 
</c:if>

<script type="text/javascript" src="<c:url value="/appjs/leftpanel.js"/>"></script>

<form action="" name="menuform" method="POST" hidden>
    <input type="hidden" value="" name="mnval" id="mnval"/>
</form>
<c:choose>
    <%-- ///////////////////////////////////////////////////////////////////////////--%>
    <%-- START : IF THE USER IS AN ADMINISTRATOR--%>
    <%-- ///////////////////////////////////////////////////////////////////////////--%>
    <c:when test="${userType == constants.DB_USER_TYPE_ADMIN}">
        <ul class="ux-vnav ux-width-16t">
            <li id="mrandp" class="ux-vnav-has-selected"><a href="#">Manage Roles & Permissions</a>
                <ul>
                    <li id="mr" ><a href="#" onclick="menuclk('manageRolepage.htm')" title="Manage Roles">Manage Roles</a></li>
                    <li id="mpg"><a href="#" onclick="menuclk('permissiongroupworklist.htm')" title="Manage Permission Groups">Manage Permission Groups</a></li>
                    <li id="vdp"><a href="#" onclick="menuclk('viewpermission.htm')" title="View Detailed Permissions">View Detailed Permissions</a></li>
                </ul>
            </li>

            <li id="mo"><a href="#" onclick="menuclk('organizationWorkList.htm')" title="Manage Organizations">Manage Organizations </a></li>
            <li id="mu"><a href="#" onclick="menuclk('userlist.htm')" title="Manage Users">Manage Users</a></li>
            <li id="ua"><a href="#" onclick="menuclk('userworklist.htm')" title="User Activation">User Activation</a></li>
<!--            <li id="emwl" class="ux-vnav-has-selected"><a href="#">Client Engagements</a>
                <ul>
                    <li id="meng"><a href="<c:url value="engagementlist.htm"/>" title="Manage Engagements">Manage Engagements</a></li>
                </ul>
            </li>-->
            
        </ul>
    </c:when>
    <%-- ///////////////////////////////////////////////////////////////////////////--%>
    <%-- END : IF THE USER IS AN ADMINISTRATOR--%>
    <%-- ///////////////////////////////////////////////////////////////////////////--%>
    
    <%-- ///////////////////////////////////////////////////////////////////////////--%>
    <%-- START : IF THE USER IS NOT AN ADMINISTRATOR--%>
    <%-- ///////////////////////////////////////////////////////////////////////////--%>
    <c:otherwise>
        <c:if test="${(menuList != null)&&(fn:length(menuList)>0)}">
            <ul class="ux-vnav ux-width-16t">
                <c:forEach var="menuObj" items="${menuList}">
                    <c:set var="menuID" value="${menuObj.htmlID}"/>
                    <c:choose>
                        <%-- /////////////////////////////////////////////////////// --%>
                        <%-- START : IF THE MENU IS A MAIN MENU--%>
                        <%-- /////////////////////////////////////////////////////// --%>
                        <c:when test="${menuObj.subMenuExistsFlag<=0}">
                            <li id="${menuID}">
                                <a href="#" title="${menuObj.menuName}" onclick="menuclk('${menuObj.appLink}')">
                                    ${menuObj.menuName}
                                </a>
                            </li>
                        </c:when>
                        <%-- /////////////////////////////////////////////////////// --%>
                        <%-- END : IF THE MENU IS A MAIN MENU--%>
                        <%-- /////////////////////////////////////////////////////// --%>
                        
                        <%-- /////////////////////////////////////////////////////// --%>
                        <%-- START : IF THE MENU IS HAVING SUB MENUS--%>
                        <%-- /////////////////////////////////////////////////////// --%>
                        <c:otherwise>
                            <li id="${menuID}">
                                <a href="#">${menuObj.menuName}</a>
                                <ul>
                                    <c:if test="${(menuObj.subMenuObjSet != null)
                                              &&(fn:length(menuObj.subMenuObjSet)>0)}">
                                        <c:forEach items="${menuObj.subMenuObjSet}" var="subMenuObj">
                                            <c:set var="subMenuID" value="${subMenuObj.htmlID}"/>
                                            <li id="${subMenuID}">
                                                <a href="#" title="${subMenuObj.subMenuName}" onclick="menuclk('${subMenuObj.appLink}')">
                                                    ${subMenuObj.subMenuName}
                                                </a>
                                            </li>
                                        </c:forEach>
                                    </c:if>
                                </ul>
                            </li>
                        </c:otherwise>
                        <%-- /////////////////////////////////////////////////////// --%>
                        <%-- END : IF THE MENU IS HAVING SUB MENUS--%>
                        <%-- /////////////////////////////////////////////////////// --%>
                    </c:choose>
                </c:forEach>
            </ul>
        </c:if>
    </c:otherwise>
    <%-- ///////////////////////////////////////////////////////////////////////////--%>
    <%-- END : IF THE USER IS NOT AN ADMINISTRATOR--%>
    <%-- ///////////////////////////////////////////////////////////////////////////--%>
</c:choose>