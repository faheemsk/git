<%-- 
    Document   : profileleftpanel
    Created on : Apr 24, 2016, 11:09:05 AM
    Author     : hpasupuleti
--%>

<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<ul class="ux-vnav ux-width-16t">
    <li id="mprofile"><a href="#">Manage Profile</a>
        <ul >
            <li id="m_personal_info">
                <a href="#" onclick="headerFn('personnelinfo.htm')">Personal Information</a>
            </li>
            <li id="m_password">
                <a href="#" onclick="headerFn('changepassword.htm')"> Change Password</a>
            </li>
        </ul>
    </li>
</ul>


