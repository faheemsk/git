<%-- 
    Document   : vulnerability
    Created on : May 27, 2016, 11:58:29 AM
    Author     : schanganti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld" %>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">   


    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=9; IE=10; IE=EDGE" />
        <meta name="viewport" content="width=device-width" />
        <title>IRMaaS</title>
        <!-- CSS : Include -->
        <jsp:include page="../includes/commoncss.jsp"/>
        <link rel="stylesheet" href="<c:url value="/appcss/vulnerability.css"/>"/>
        <!-- CSS : Include -->
        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/appjs/vulnerability.js"/>"></script>
        <!-- JS : Include -->  
        <!-- Header : Include -->
        <jsp:include page="../includes/header.jsp"/>
        <!-- Header : Include -->  
    </head>
    <body>
        <div id="showaddvul">

            <h2 class="ux-padding-bottom-1t">Add New Vulnerability</h2>
            <div class="ux-clear"></div>
            <div class="ux-panl ux-float-left">
                <div class="ux-panl-header ux-panl-with-underline" >
                    <h2> Vulnerability Details</h2>
                </div>
                <div class=" ux-panl-content " >
                    <div class="ux-hform">
                        <dl>
                            <dt>
                                <label title="The unique identifier for each row within the Security Intelligence Hub">Vulnerability Instance:</label>
                            </dt>
                            <dd><input type="text" value="" /></dd>
                        </dl>
                        <dl>
                            <dt>
                                <label title="Security Assessment Service defines a capability and associates to specific tests or procedure that will be used to complete the capability.">Service: <span>*</span></label>
                            </dt>
                            <dd><select>
                                    <option>Select</option>
                                    <option>A</option>
                                    <option>B</option>
                                    <option>C</option>
                                </select>  
                            </dd>
                        </dl>
                        <dl>
                            <dt>
                                <label title="Source or system where the data was created.">Source:</label>
                            </dt>
                            <dd>Manual 
                            </dd>
                        </dl>

                        <dl>
                            <dt>
                                <label title="IP address in the format XXX.XXX.XXX.XXX given to the device">IP Address:<span>*</span></label>
                            </dt>
                            <dd><input type="text" value="" /></dd>
                        </dl>

                        <dl>
                            <dt>
                                <label title="The application or software that the vulnerability is applicable.">Application Name:</label>
                            </dt>
                            <dd><input type="text" value="" /></dd>
                        </dl>







                        <dl>
                            <dt>
                                <label title="Host name given to the device in subject">Host:<span>*</span></label>
                            </dt>
                            <dd><input type="text" value="" /> </dd>
                        </dl>
                        <dl>
                            <dt>
                                <label title="Network domain the device is located">Domain:<span>*</span></label>
                            </dt>
                            <dd><input type="text" value="" />  </dd>
                        </dl>
                        <dl>
                            <dt>
                                <label title="The URL for the application that was scanned to find the vulnerability.">URL:<span>*</span></label>
                            </dt>
                            <dd><input type="text" value="" /></dd>
                        </dl>

                        <dl>
                            <dt>
                                <label title="Operating system used on the device in subject">Operating System:
                                </label>
                            </dt>
                            <dd><input type="text" value="" /></dd>
                        </dl>


                        <dl>
                            <dt>
                                <label title="A media access control address (MAC address), also called physical address, is a unique identifier assigned to network interfaces for communications on the physical network segment. MAC addresses are used as a network address for most IEEE 802 network technologies">MAC Address:</label>
                            </dt>
                            <dd><input type="text" value="" /></dd>
                        </dl>
                        <dl>
                            <dt>
                                <label title="The port on which the vulnerability is found.">Port:</label>
                            </dt>
                            <dd><input type="text" value="" /> </dd>
                        </dl>
                    </div>
                </div>
            </div>
            <div class="ux-panl ux-float-left">
                <div class="ux-panl-header ux-panl-with-underline">
                    <h2>Vulnerability Information</h2>
                </div>
                <div class="ux-panl-content">
                    <div class="ux-hform">
                        <dl class="ux-width-27t">
                            <dt>
                                <label>Vulnerability Name:</label>
                            </dt>
                            <dd>
                                <input name="Vulnerability Name" class="ux-width-25t" type="text" value="" />
                            </dd>
                        </dl>
                        <dl >
                            <dt>
                                <label>Vulnerability Create Date:</label>
                            </dt>
                            <dd>
                                <input type="text" value="" class="defaultActualPicker" />
                            </dd>
                        </dl>
                        <div class="ux-clear"></div>
                        <dl class="ux-width-35t">
                            <dt>
                                <label>Vulnerability Description:</label>
                            </dt>
                            <dd>
                                <textarea name="Risk Description" class="ux-width-70t" cols="12" rows="4"></textarea>
                            </dd>
                        </dl>
                        <div class="ux-clear"></div>
                        <dl class="ux-width-30t">
                            <dt>
                                <label>Techincal details: </label>
                            </dt>
                            <dd>
                                <textarea name="Risk Description" class="ux-width-70t" cols="12" rows="4"></textarea>
                            </dd>
                        </dl>
                        <div class="ux-clear"></div>
                        <dl class="ux-width-30t">
                            <dt>
                                <label>Impact Detail:</label>
                            </dt>
                            <dd>
                                <textarea name="Risk Description" class="ux-width-70t" cols="12" rows="4"></textarea>
                            </dd>
                        </dl>
                        <div class="ux-clear"></div>
                        <dl class="ux-width-30t">
                            <dt>
                                <label>Recommendation:</label>
                            </dt>
                            <dd>
                                <textarea name="Risk Description" class="ux-width-70t" cols="12" rows="4"></textarea>
                            </dd>
                        </dl>
                        <div class="ux-clear"></div>
                        <dl class="ux-width-27t">
                            <dt>
                                <label>Root Cause Detail:</label>
                            </dt>
                            <dd>
                                <select class="ux-width-25t route" >
                                    <option>Select</option>
                                    <option>Route Cause1</option>
                                    <option>Route Cause2</option>
                                    <option>Route Cause2</option>
                                    <option>Others</option>
                                </select><br />
<!--                                <span class="others ux-float-left ux-margin-top-min"><input type="text" value=""  class="ux-width-24t" /></span>-->
                            </dd>
                        </dl>

                        <dl>
                            <dt><label>Status:</label></dt>
                            <dd><select class="ux-width-25t" >
                                    <option>Select</option>
                                    <option>False Positive</option>
                                    <option>Open</option>
                                    <option>Duplicate</option>
                                    <option>Closed</option>
                                    <option>Close with Exception</option>
                                </select></dd>
                        </dl> 
                    </div>
                </div>
            </div>
            
            <a name="addcve" id="addcve"></a>
            <div class="ux-panl ux-float-left">
                <div class="ux-panl-header ux-panl-with-underline">

                    </body>
                    </html>
