<%-- 
    Document   : footer
    Created on : Apr 4, 2016, 12:25:57 PM
    Author     : hpasupuleti
    Modified on: Apr 13, 2016, 03:30 PM
    Modifiedby : mrejeti
    Modification : year added. title added for terms of use and privacy policy
--%>

<%@taglib  prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib  prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="year" value="${now}" pattern="yyyy" />
<!-- Begin: Footer -->

<div id="ux-ftr">
    <ul class="ux-ftr-links">
        <li><a href="<c:url value="termsofuse.htm"/>" title="Terms of Use" target="_blank">Terms of Use</a></li>
        <li><a href="<c:url value="privacypolicy.htm"/>" title="Privacy Policy" target="_blank">Privacy Policy</a></li>
        
    </ul>
    <p>
        &copy; Optum ${year}, Inc. - All Rights Reserved
        <c:if test="${sessionScope.BUILD_VERSION_SESSION != null}">
            <span id="buildVersion" class="color_white" style="float: right;" title=" BUILD # : ${sessionScope.BUILD_VERSION_SESSION}">
                &nbsp;  
                &nbsp;  
                &nbsp;  
            </span>
        </c:if>
    </p>
</div>
<!-- End: Footer --> 