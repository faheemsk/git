<%-- 
    Document   : sessionpopup
    Created on : May 31, 2016, 3:39:57 PM
    Author     : rpanuganti
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- CSS : Include -->
<link rel="stylesheet" href="<c:url value="/appcss/sessionpopup.css"/>" />
<!-- CSS : Include -->
<script type="text/javascript" src="<c:url value="/appjs/sessionExtends.js"/>"></script>
<!-- Start: popup for session notification -->
<input type="hidden" name="defaulttime" id="defaulttime" />
<div id='blackcover10' style="z-index: 15;"></div>
<div id='popupbox10' class="ux-modl" style="z-index: 15; ">
    <div class="ux-modl-header">
        <h6>Notification</h6>
    </div>

    <div class="ux-modl-content">
        <div align="center">
            <h3><span id="conformTextdd">Your session is about to end in 5 minutes.</span></h3>
        </div>
        <div align="center" class="ux-margin-1t ux-margin-left-1t">
            <input type="button" value="OK" title="OK"  id="ProceedAnyWay" class="ux-btn-default-action ux-width-7t" onclick='sessionpopup(-10);'/> 
        </div>
    </div>
</div>
<!--End: popup for session notification -->
<!-- Start: popup for session expired -->
<div id='popupbox20' class="ux-modl" style="z-index: 15; ">
    <div class="ux-modl-header">
        <h6>Notification</h6>
    </div>

    <div class="ux-modl-content">
        <div align="center">
            <h3><span id="conformTextdd">Your session has ended due to inactivity.</span></h3>
        </div>
        <div align="center" class="ux-margin-1t ux-margin-left-1t">
            <input type="button" value="OK" title="OK"  id="ProceedAnyWay" 
                   class="ux-btn-default-action ux-width-7t" onclick='location.href = "<c:url value="/sessionInactive.htm"/>"'/> 

        </div>
    </div>
</div>
<!-- End: popup for session expired -->