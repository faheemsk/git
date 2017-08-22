<%-- 
    Document   : verifyuser
    Created on : Apr 18, 2016, 10:25:21 AM
    Author     : hpasupuleti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>IRMaaS</title>
        <!-- CSS : Include -->
        
        <jsp:include page="../includes/commoncss.jsp"/>
        <!-- CSS : Include -->
        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        <jsp:include page="../usermgmt/sessionpopup.jsp"/>
        <script type="text/javascript" src="<c:url value="/appjs/LoginJS.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/appjs/header.js"/>"></script>
        <!-- JS : Include -->  
    </head>
    <body>
        <div id="ux-wrapper">
            <!-- Begin : Header -->
            <div class="ux-head">
                <div class="ux-head-cntr-logo">
                    <span class="ux-head-logo">
                        <img src="<c:url value="/images/OPTUM-LOGO-Security-Solutions_no-padding.png"/>" alt="Product Name" />
                    </span>
                </div>
            </div>
            <!-- End : Header -->

            <!-- Begin: Content -->
            <div class="ux-content">
                <form action="#" method="post" 
                      name="configUser" id="configUserId">
                    <h1>Security Information</h1>
                   <p class="ux-form-required ux-margin-top-1t"> <strong class="ux-labl-required">*</strong> Required </p>
                         <c:if test="${(errorMessage !=null)&&(fn:length(errorMessage)>0)}">
                    <div  class="ux-msg-error ux-width-45t" id="valrr" >    
                        <c:out value="${errorMessage}"/>
                    </div>
                </c:if>
                    <div class="ux-panl">
                        <table class="ux-tabl-form-multicol" style="width: auto; display: table;">
                            <tr><td><h2 class="ux-margin-bottom-none">Security Profile</h2></td></tr>
                            <tr>
                                <td><label for="example-security-question-1">Question:</label></td>
                                <input type="hidden" name="securityQ1" value="${securityDtls1.encUserSecurityKey}"/>
                                <td>
                                    <input type="text" class="ux-width-32t" id="securityQuestion1"
                                                    name="securityQuestion1" value="${securityDtls1.securityQuestion}" 
                                                    readonly="true"/></label>
                                </td>
                            </tr>
                            <tr>
                                <td><label for="example-answer-1">Answer to Question: </label><span style="color:red">*</span></td>
                                <td><input type="text"  class="ux-width-23t" id="securityAnswer1" name="securityAnswer1" value="" onkeyup="limiter(this, 100, '', '')" />
                                    <label id="errorMsgSecurityAnswer1" class="ux-msg-error-inline"></label>
                                </td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <input type="hidden" value="${count}" name="count"/>
                                <td><input type="button" value="Verify" title="Verify" id="verifyProfile" onclick="fnSubmitverifyDetails()" class="ux-btn-default-action ux-width-9t"/>&nbsp;
                                    <input type="button" value="Cancel" title="Cancel" id="cancel"  name="cancel" onclick="window.location.href = '<c:url value="/logout.htm"/>'" class="ux-btn-default-action ux-width-7t"/>
                                </td>
                            </tr>
                        </table>
                    </div> 
                </form>
            </div>
            <!-- End: Content -->
            <!-- Begin: Footer -->
            <jsp:include page="../includes/footer.jsp"/>
            <!-- End : Footer -->
        </div>

    </body>
</html>
