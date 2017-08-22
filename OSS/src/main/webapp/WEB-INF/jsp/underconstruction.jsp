<%-- 
    Document   : UnderConstruction
    Created on : Oct 31, 2014, 12:55:26 PM
    Author     : vrai
--%>


<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<%@taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld"%>
<html>
    <head>
        <jsp:include page="usermgmt/IRMCommonJSIncludes.jsp"/>
        <script src="<c:url value="/validationJS/AssessmentJS.js"/>" type="text/javascript"></script>
    </head>
    <body>
        <jsp:include page="usermgmt/IRMHeader.jsp"/>
        <div id="ux-wrapper">
            <!--Left navigation starts here-->
            <%-- <jsp:include page="ProjectManagerLeftPannel.jsp"/>--%>
            <!--Left navigation ends here-->
            <div class="ux-content">
                <table class="ux-height-33t" width="100%">
                    <tr>
                        <td align="center"><h2>Page Under Construction. <br>
                                <br>
                                <img src="<c:url value="/images/mainenance.png"/>" width="130" height="129" alt="">    	</h2></td>
                    </tr>
                </table>
            </div>
        </div>
        <jsp:include page="usermgmt/IRMFooter.jsp"/>
    </body>
</body>
</html>