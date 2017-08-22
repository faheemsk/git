<%-- 
    Document   : dashboardfooter
    Created on : Jun 23, 2016, 3:46:18 PM
    Author     : mrejeti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<uitk:footer>
    <uitk:footer-links>
        <!--        <uitk:footer-link tk-label="Terms of Use" url="termsofuse.htm" title="Terms of Use" tk-target="_blank"></uitk:footer-link>
                <uitk:footer-link tk-label="Privacy Policy" url="privacypolicy.htm" title="Privacy Policy" tk-target="_blank"></uitk:footer-link>-->
        <a href="termsofuse.htm" title="Terms of Use" target="_blank">Terms of Use</a>  |  
        <a href="privacypolicy.htm" title="Privacy Policy" target="_blank">Privacy Policy</a>
    </uitk:footer-links>
    <uitk:footer-text>
        Your use of this product is governed by the terms of your company's agreement. You may not use or disclose this product or allow others to use it or disclose it, except as permitted by your agreement with Optum.
    </uitk:footer-text>

</uitk:footer>

<uitk:busy-indicator model="busyIndicatorModel"></uitk:busy-indicator>
<uitk:session-timeout model="sessionTimeoutModel"></uitk:session-timeout>
