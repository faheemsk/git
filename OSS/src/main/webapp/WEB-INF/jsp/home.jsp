<%-- 
    Document   : home
    Created on : Apr 4, 2016, 12:36:34 PM
    Author     : hpasupuleti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>IRMaaS : Home Page</title>
        <!-- CSS : Include -->
        <jsp:include page="includes/commoncss.jsp"/>
        <!-- CSS : Include -->
        <!-- JS : Include -->
        <jsp:include page="includes/commonjs.jsp"/>
        <!-- JS : Include -->  
    </head>
    <body>
        <div id="ux-wrapper">
            <!-- Begin : Header -->
            <jsp:include page="includes/header.jsp"/>
            <!-- End : Header -->
            
            <!-- Begin : Bread Crumbs -->
            <ul class="ux-brdc ux-margin-left-1t" >
                <li><a href="#">Home</a></li>
                <li>Add Role(s)</li>
            </ul>
            <!-- End :  Bread Crumbs -->
            
            <!-- Begin : Left Panel -->
            <jsp:include page="includes/leftpanel.jsp"/>
            <!-- End : Left Panel -->
            
            <!-- Begin: Content -->
            <div class="ux-content">
                
            </div>
            <!-- End : Content -->
            
            <!-- Begin: Footer -->
            <jsp:include page="includes/footer.jsp"/>
            <!-- End : Footer -->
        </div>
    </body>
</html>
