<%-- 
    Document   : dashboardheader
    Created on : Jun 23, 2016, 3:46:05 PM
    Author     : mrejeti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
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
        if (logoutFlag == true) { 
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
<script type="text/javascript" src="<c:url value="/dashjs/headerJS.js"/>"></script>
<div class="tk-wrapper">
<c:if test="${sessionScope.USER_SESSION != null}">
    <c:set var="userProfile" value="${sessionScope.USER_SESSION}"/>
    <c:set var="username" value="${userProfile.firstName} ${userProfile.lastName}"/>
</c:if>
<div ng-controller="IRMaasDHeadCtrl"> 
    <uitk:global-navigation id="GlobalNavigationMenu">
        <uitk:skip-link allow-accesskeys-support='true' skip-link-title='Access keys: T = Top of page; M = Main content' skip-link-text="Skip to Main Content"></uitk:skip-link>
        <uitk:global-navigation-link  text-template="Welcome ${username}" profile-name="true" has-sub-menu="false">

            <uitk:global-navigation-drop-down>

                <uitk:global-navigation-link text-template="Profile" onClick="dashboardHeaderFn('confirmpassword.htm')"></uitk:global-navigation-link>
            </uitk:global-navigation-drop-down>
        </uitk:global-navigation-link>
        <uitk:global-navigation-link text-template="<span class='cux-icon-help' aria-hidden='true'></span><span class='tk-margin-left-min'>Help</span>" onClick="dashboardHeaderFn('webHelp.htm')"></uitk:global-navigation-link>
        <uitk:global-navigation-link text-template="Sign Out" url="<c:url value="/logout.htm"/>" ></uitk:global-navigation-link>
    </uitk:global-navigation>
    <uitk:header logo="<c:url value="/images/OPTUM-LOGO-Security-Solutions_no-padding.png"/>" alt-text="Optum Security Solutions">
        <uitk:primary-navigation model="primaryNavigationModel"></uitk:primary-navigation>
        <div>
            <uitk:secondary-navigation model = "secondaryNavigationModel"></uitk:secondary-navigation>
        </div>
    </uitk:header>
</div>
          <div id="loading">
            <div id="loading-content">
                <img   src = "<c:url value="/images/loader.gif"></c:url>" alt = "Loading indicator">
            </div>
        </div>
            <div style="display: none" id="downloading">
            <div id="downloading-content">
                <img   src = "<c:url value="/images/downloading.gif"></c:url>" alt = "downloading indicator">
            </div>
        </div>
        <form name="dashboardHeaderfrm" method="post" hidden>
            <!--Start: Back Navigation to Finding List from Dashboard (Ref: US41394)-->
            <input type="hidden" name="cgenk" value="${sessionScope.encEngCodeForBack}"/>
            <input type="hidden" name="orgSchma" value="${sessionScope.engSchema}"/>
            <input type="hidden" name="pgcnt" value="1"/>
            <!--End: Back Navigation to Finding List from Dashboard (Ref: US41394)-->
        </form>