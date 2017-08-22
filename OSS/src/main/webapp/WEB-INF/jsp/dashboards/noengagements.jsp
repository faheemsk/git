<%-- 
    Document   : noengagements
    Created on : Jul 19, 2016, 5:42:42 PM
    Author     : mrejeti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="constants" />
<un:useConstants className="com.optum.oss.constants.PermissionConstants" var="permissionConstants" />
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
        <meta content="text/html;charset=utf-8" http-equiv="Content-Type" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

            <title>IRMaaS</title>
            <!-- CSS : Include -->
            <jsp:include page="../includes/commoncss.jsp"/>
            <!-- CSS : Include -->
            <!-- JS : Include -->
            <jsp:include page="../includes/commonjs.jsp"/>
            <!-- JS : Include -->
            <!-- Header : Include -->
            <jsp:include page="../includes/header.jsp"/>
            <script type="text/javascript">
    var logoutFlag = true;
    var success = false;  
    
    $(document).ready(function () { 
        
        var myEvent = window.attachEvent || window.addEventListener; 
        var winevent = window.attachEvent ? 'onbeforeunload' : 'beforeunload';
        
        window.addEventListener('click', function (e) {
            if (e.target.className.toString().indexOf('paginate_button')!== -1
                        || e.target.className.toString().indexOf('datepicker')!== -1
                        || e.target.nodeName.indexOf('IMG')!== -1
                        || e.target.nodeName.indexOf('TH')!== -1
                        || e.target.nodeName.indexOf('DIV')!== -1
                        || e.target.type == 'text'
                        || e.target.type == 'textarea'
                        || e.target.type == 'radio'
                        || e.target.type == 'checkbox'
                        || e.target.nodeName.indexOf('SELECT')!== -1){
                   console.log('not href');
                   logoutFlag = true;
            }else{
                logoutFlag = false;
            }
            
            var hval = '';
            if( e.target.nodeName.indexOf('IMG')!== -1){
                var atitle = e.target.getAttribute('title');
                if(atitle == 'Search'){ //For fixed table
                    logoutFlag = false;
                }else if(atitle == 'Refresh'){
                    hval = e.target.parentNode.getAttribute('href');
                    if(hval == '#' || hval == ''){
                        logoutFlag = true;
                    }else{
                         logoutFlag = false;
                    }
                }

            }
        },true);
        
        myEvent(winevent, function (e) {
            console.log(logoutFlag+ '-window');
            logout();
        },true);
        
    });
        
    function logout(){
        if (logoutFlag === true) { 
            $.ajax( { 
                type: "POST",
                traditional: true, 
                dataType: "", 
                async: false, 
                url: 'refreshlogout.htm',   
                success: function (data) {
                    if (data == "success") {
                       console.log(data);
                    }
                }
            });
        }
    }
    
    //find first parent with tagName [tagname]
    function findParent(tagname,el){
      if ((el.nodeName || el.tagName).toLowerCase()===tagname.toLowerCase()){
        return el;
      }
      while (el = el.parentNode){
        if ((el.nodeName || el.tagName).toLowerCase()===tagname.toLowerCase()){
          return el;
        }
      }
      return null;
    }
        
        function clkfn(){
            logoutFlag = false;
        }
        
</script>
    </head>
    <body>
        <!-- Start: Wrapper -->
        <div id="ux-wrapper">
            <ul class="ux-brdc ux-margin-left-1t" >
               <li><a href="#" onclick="headerFn('userLandingPage.htm')">Home</a></li>
                <li>Dashboard</li>
            </ul>
            <%--<jsp:include page="../includes/leftpanel.jsp"/>--%>
            <div class="ux-modl-content">
                <div class="ux-content"> 
                    <div align="center">
                        </br></br></br>
                        <h2>Engagement(s) has not been published yet. Data will be displayed once it is published.</h2>
                    </div>
                </div>
            </div>
            <jsp:include page="../includes/footer.jsp"/>
    </body>
</html>