<%-- 
    Document   : header
    Created on : Apr 4, 2016, 12:25:51 PM
    Author     : hpasupuleti
--%>

<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<!-- JSP : Include -->  
<jsp:include page="../usermgmt/sessionpopup.jsp"/>
<!-- JSP : Include --> 

<script type="text/javascript" src="<c:url value="/appjs/header.js"/>"></script>

<!--Header starts here-->
<c:set var="username" value=""/>
<c:set var="usertype" value=""/>
<c:set var="datediff" value=""/>
<c:set var="passwordExpiryDays" value="0"/>
<c:if test="${sessionScope.USER_SESSION != null}">
    <c:set var="userProfile" value="${sessionScope.USER_SESSION}"/>
    <c:set var="username" value="${userProfile.firstName} ${userProfile.lastName}"/>
    <c:set var="datediff" value="${userProfile.pswdExpiryFlag}"/>
    <c:set var="passwordExpiryDays" value="${userProfile.pswdExpiryDays}"/>
</c:if>
<input type="hidden" value="${username}" id="notename"/>
<form name="headerfrm" method="post" hidden></form>
<div class="ux-head">
    <div class="ux-head-cntr-logo"> 
        <span class="ux-head-logo"> 
            <img src="<c:url value="/images/OPTUM-LOGO-Security-Solutions_no-padding.png"/>" alt="Product Name" />
        </span> 
    </div>
    <div class="ux-head-cntr-navigation-global">
        <ul class="ux-unav">
            <li class="ux-unav-has-submenu"><span class="ux-head-welcome">Welcome, ${username}</span>
                <ul>
                    <li><a title="Profile" href="#" onclick="headerFn('confirmpassword.htm')">Profile</a></li>
                </ul>
            </li>
            <li><a href="#" onclick="headerFn('webHelp.htm')" title="Help">Help</a></li>
            <li><a href="<c:url value="/logout.htm"/>" title="Sign Out">Sign Out</a></li>
        </ul>
        <c:if test="${datediff =='Y'}">
            <div>
                <h4>Your Password is going to Expire in ${passwordExpiryDays} Days. Please Click Here to reset your password.</h4>
            </div>
        </c:if>
    </div>
    <div class="ux-head-cntr-navigation-primary">
        <ul class="ux-pnav">
        </ul>
    </div>

</div>

<!--Header ends here--> 
