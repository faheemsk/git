<%-- 
    Document   : Exception
    Created on : Oct 23, 2013, 2:52:14 PM
    Author     : hpasupuleti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<!DOCTYPE html>
<jsp:include page="includes/header.jsp"/>
<head>
    <jsp:include page="includes/commoncss.jsp"/>
    <jsp:include page="includes/commonjs.jsp"/>
    <title>IRMaaS</title>
</head>
<jsp:include page="includes/leftpanel.jsp"/>
<div class="ux-content">
    <c:if test="${sessionScope.USER_SESSION==null}">
        <c:redirect url="/login.htm" />            
    </c:if>
    <form method="post" name="exceptionForm">
        <un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="constants" />
        <c:set var="message" value=""/>
        <c:set var="code" value=""/>
        <c:set var="type" value="Exception"/>

        <c:if test="${requestScope.exceptionBean != null}">
            <c:set var="message"  value="${requestScope.exceptionBean.exceptionMessage}"/>
            <c:set var="code"  value="${requestScope.exceptionBean.exceptionCode}"/>
            <c:set var="type" value="${requestScope.exceptionBean.exceptionType}"/>
        </c:if>

        <c:choose>
            <c:when test="${type == constants.EXCEPTION_TYPE_ERROR}">
                <h1>Error Occurred </h1>
            </c:when>
            <c:otherwise>
                <h1>Exception Occurred </h1>
            </c:otherwise>
        </c:choose>


        <div class="ux-float-left ux-width-full-inclusive">
            <div class="ux-panl ux-float-left ">
                <c:choose>
                    <c:when test="${type == constants.EXCEPTION_TYPE_ERROR}">
                        <div class="ux-panl-header ux-panl-with-underline">
                            <h2>Error Details </h2>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="ux-panl-header ux-panl-with-underline">
                            <h2>Exception Details </h2>
                        </div>
                    </c:otherwise>
                </c:choose>
                <div class="ux-panl-content" >
                    <div class="ux-read-form" id="editdata">
                        <dl>
                            <dt>
                            <label><c:out value="${type} - ${code}"/></label>
                            </dt>
                        </dl>
                        <br><br><br><br>
                        <dl>
                            <dt>
                            <label><c:out value="Message-"/></label>
                            </dt>
                            <dd>
                                <div class="ux-panl-content">
                                    <div class="ux-float-left" > 
                                        <span>
                                            <c:out value="${message}" escapeXml="false"/>
                                        </span>
                                    </div>
                                </div>
                            </dd>
                        </dl>


                    </div>
                </div>
            </div>
        </div>
    </form>
</div>

<jsp:include page="includes/footer.jsp"/>
