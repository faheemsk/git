<%-- 
    Document   : ForgotPassword
    Created on : Apr 23, 2016, 7:02:54 PM
    Author     : hpasupuleti
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<html>
    <head>
        <title>IRMaaS</title>
        <!-- CSS : Include -->
        <jsp:include page="../includes/commoncss.jsp"/>
        <!-- CSS : Include -->
        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/appjs/LoginJS.js"/>"></script>
        <!-- JS : Include -->  
    </head>
    <body>
        <!-- Start: Wrapper -->
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
            <div class="ux-content" >
                <h1 class="">Forgot Password</h1>
                <p class="ux-form-required ux-margin-top-1t"> <strong class="ux-labl-required">*</strong> Required </p>
                <c:if test="${(message !=null)&&(fn:length(message)>0)}">
                    <div  class="ux-msg-error ux-width-45t" id="valrr" >    
                        <c:out value="${message}"/>
                    </div>
                </c:if>
                <form name="forgotPassword" method="post">
                    <input type="hidden" name="chk" id="chk"/>
                    <div class="ux-padding-1t">
                        <label for="signInForm:user_name"><strong>Username :</strong></label> <span style="color:red">*</span>
                        <c:choose>
                            <c:when test="${divshow != null && divshow==true}">
                                <td>
                                    ${username}
                                    <input type="hidden" name="username" value="${username}" />
                                </td>
                            </c:when>
                            <c:otherwise>

                                <input id="username" type="text" name="username" value="${username}" maxlength="40" onFocus="this.value = this.value;" />
                                <span class="rf-msg ux-msg-error-inline" id="signInForm:j_idt75"></span>
                                <input name="checkButton" id="checkButton" type="button" 
                                       value="Check" title="Check" class="ux-btn-default-action"
                                       onclick="fnCheckUser('${fn:trim(Check)}')"/>
                                <input type="button" value="Back" 
                                               title="Back" id="updateProfile"
                                               onclick="window.location = './login.htm'" 
                                               class="ux-btn-default-action"/>

                                <label id="errorUserName" style="font-weight: bold" class="ux-margin-top-halft ux-margin-left-1t ux-msg-error-inline" ></label>


                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div id="securityChallengeQues" <c:if test="${divshow =='false'}">style="display: none"</c:if>>
                        <div class="ux-panl" >
                            <div class="ux-panl-header ux-panl-with-underline">
                                <h2>Security Challenge Questions</h2>
                            </div>
                            <div class="ux-panl-content">

                                <table>

                                    <tr>
                                        <td><label for="example-security-question-1">Question 1:</label><span style="color:red"></span></td>
                                        <input type="hidden" name="securityQ1" value="${securityDtls1.encUserSecurityKey}"/>
                                        <td> <input type="text" class="ux-width-32t" id="securityQuestion1"
                                                    name="securityQuestion1" value="${securityDtls1.securityQuestion}" 
                                                    readonly="true"/></td>
                                    </tr>
                                    <tr>
                                        <td><label for="example-answer-1">Answer to Question 1 :</label> <span style="color:red">*</span></td>
                                        <td><input type="text"  class="ux-width-23t" id="securityAnswer1" name="securityAnswer1" value="" />
                                            &nbsp;<label id="errorMsgSecurityAnswer1" class="ux-msg-error-inline"></label>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td><label for="example-security-question-2">Question 2:</label><span style="color:red"></span></td>
                                        <input type="hidden" name="securityQ2" value="${securityDtls2.encUserSecurityKey}"/>
                                        <td>  <input type="text" class="ux-width-32t" id="securityQuestion2" 
                                                     name="securityQuestion2" value="${securityDtls2.securityQuestion}" 
                                                     readonly="true"/> </td>
                                    </tr>
                                    <tr>
                                        <td><label for="example-answer-2">Answer to Question 2 : </label> <span style="color:red">*</span></td>
                                        <td><input type="text" class="ux-width-23t" id="securityAnswer2" name="securityAnswer2" value="" />
                                            &nbsp; <label id="errorMsgSecurityAnswer2" class="ux-msg-error-inline"></label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                        <td>
                                            <input type="hidden" value="${count}" name="count"/>
                                            <input name="submitAnswer" id="submitButton" type="button" 
                                                   value="Submit" title="Submit" class="ux-btn-default-action"
                                                   onclick="fnSubmitDetails('${fn:trim(Validate)}')"/> 
                                            <input name="" id="cancelButton" onclick="window.location.href = '<c:url value="login.htm"/>'" type="button" value="Cancel" title="Cancel"  class="ux-btn-default-action" />
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>        
                        <div class="ux-width-full-inclusive">

                            <p>
                                By Providing the valid information, your password will be reset and sent to the 
                                mail address specified in the application.
                            </p>

                        </div>
                    </div>
                </form>
            </div>
            <!-- Begin: Footer -->
            <jsp:include page="../includes/footer.jsp"/>
            <!-- End: Footer -->
        </div>
    </body>
</html>
