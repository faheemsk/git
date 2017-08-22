<%-- 
    Document   : history
    Created on : May 16, 2016, 1:02:09 PM
    Author     : schanganti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<un:useConstants className="com.optum.oss.constants.PermissionConstants" var="prmnConstants" />
<un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"/>
    <head>
        <title>IRMaaS</title>
        <!-- CSS : Include -->
        <jsp:include page="../includes/commoncss.jsp"/>
        <link rel="stylesheet" href="<c:url value="/appcss/history.css"/>" />
        <!-- CSS : Include -->
        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        
        <!-- JS : Include -->
    </head>
    
    <body>
         <div id="ux-wrapper">

        <!--Header starts here-->
        <jsp:include page="../includes/header.jsp"/>
        <!--Header ends here-->

        <!-- Begin: Secondary Nav -->

        <!-- End: Secondary Nav -->

        <!-- Begin: Content -->
        <ul class="ux-brdc ux-margin-left-1t" >
            <li><a title="" href=".htm">history</a></li> 
            <li>history</li>
        </ul>
        
        <!--Left navigation starts here-->
        <jsp:include page="../includes/leftpanel.jsp"/>
        <!--Left navigation ends here--> 

        <!-- content starts -->
        <div class="ux-content">

            <form action=""  name="" method="POST">
            </form>
            <h1>History</h1>
              <table id="dataTable" class="display">
                    <thead>
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>

                        </tr>
                        <tr>
                            <th>Document Name</th>
                            <th>Created On</th>
                            <th>Created By</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
        
        
  
        
        
        
         </tbody>
                </table>
              </div>
        </div>
             <!-- content ends -->

        <!-- Begin: Footer -->
        <jsp:include page="../includes/footer.jsp"/>
        <!-- End: Footer -->
    </div>
    <!-- End: Wrapper -->


    <!-- END JavaScript Includes -->
    </body>
</html>
