<%-- 
    Document   : organizationDetails
    Created on : Apr 19, 2016, 12:08:29 PM
    Author     : rpanuganti
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="constants" />
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>IRMaaS</title>
        <!-- CSS : Include -->
        <jsp:include page="../includes/commoncss.jsp"/>
        <!-- CSS : Include -->
        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/appjs/addOrganization.js"/>"></script>
        <!-- JS : Include -->  
    </head>
    <BODY >
        <form name="vieworganizationfrm" method="post" hidden>
             <input type="hidden" value="${organizationDetails.organizationKey}" name="orgID" id="orgID"/>
        </form>
        <!-- Start: Wrapper -->
        <div id="ux-wrapper"> 

            <!--Header starts here-->
            <jsp:include page="../includes/header.jsp"/>
            <!--Header ends here--> 

            <!-- Begin: Secondary Nav --> 

            <!-- End: Secondary Nav --> 

            <!-- Start: Breadcrumbs -->
            <ul class="ux-brdc ux-margin-left-1t" >

                <li><a title="Manage Organizations" href='#' onclick="headerFn('organizationWorkList.htm')">Manage Organizations</a></li>
                <li> View Organization</li>
            </ul>
            <!-- End: Breadcrumbs -->

            <!--Left navigation starts here-->
            <jsp:include page="../includes/leftpanel.jsp"/>
            <!--Left navigation ends here--> 

            <!-- content starts -->
            <div class="ux-content">



                <h1>View Organization <div class=" ux-float-right ">  
                        <c:if test="${organizationDetails.rowStatusValue==constants.DB_CONSTANT_ORGANIZATION_SCHEMA_NOT_ONBOARDED}">    <input name="" type="button" value="Continue Onboarding"  title="Continue OnboardingA" class="ux-btn-default-action" onclick="onBoareProcess()" /> </c:if>
                        <input name="" type="button" value="Back"  title="Back" class="ux-btn-default-action" onclick="backFrmViewOrganization()" /> 
                    </div>
                </h1>
                <div class="ux-panl ux-float-left">
                    <div class="ux-panl-header ux-panl-with-underline">
                        <h2>Organization Details</h2>
                    </div>
                    <div class="ux-panl-content ux-padding-top-none">
                        <div class="ux-hform ">

                            <dl>
                                <dt><label>ID:</label> </dt>
                                <dd>${organizationDetails.organizationKey}</dd>
                            </dl>    <dl>
                                <dt><label>Parent Organization Name:</label> </dt>
                                <dd>${organizationDetails.parentOrganizationName}</dd>
                            </dl>
                            <dl>
                                <dt><label>Organization Name:</label> </dt>
                                <dd>${organizationDetails.organizationName}</dd>
                            </dl>
                            <dl>
                                <dt><label>Organization Type:</label> </dt>
                                <dd>${organizationDetails.organizationTypeObj.lookUpEntityName}</dd>
                            </dl>
                            <dl>
                                <dt><label>Organization Industry:</label> </dt>
                                <dd>${organizationDetails.organizationIndustryObj.lookUpEntityName}</dd>
                            </dl>

                            <dl>
                                <dt><label>Country:</label> </dt>
                                <dd>${organizationDetails.countryName}</dd>
                            </dl>
                            <dl>
                                <dt><label>Street Address Line 1:</label> </dt>
                                <dd>${organizationDetails.streetAddress1}</dd>
                            </dl>

                            <dl>
                                <dt><label>Street Address Line 2:</label> </dt>
                                <dd>${organizationDetails.streetAddress2}</dd>
                            </dl>
                            <div class="ux-clear"></div>
                            <dl>
                                <dt><label>City:</label> </dt>
                                <dd>${organizationDetails.cityName}</dd>
                            </dl>

                            <dl>
                                <dt><label>State:</label> </dt>
                                <dd>${organizationDetails.stateName}</dd>
                            </dl>



                            <dl>
                                <dt><label>Zip Code:</label> </dt>
                                <dd>${organizationDetails.postalCode}</dd>
                            </dl>
                            <dl >
                                <dt><label>Notes:</label> </dt>
                                <dd>${organizationDetails.organizationDescription}</dd>
                            </dl>
                            <div class="ux-clear"></div>
                            <dl>
                                <dt><label>Status:</label>  </dt>
                                <dd>${organizationDetails.rowStatusValue}</dd>
                            </dl>
                            <c:if test="${organizationDetails.rowStatusKey== constants.DB_ROW_STATUS_DE_ACTIVE_VALUE}">
                                <dl>
                                    <dt><label>Reason for Deactivation:</label>  </dt>
                                    <dd>${organizationDetails.deactiveReason}</dd>
                                </dl>
                            </c:if>
                        </div>
                    </div>

                </div>

                <div class="ux-panl ux-float-left client" >
                    <div class="ux-panl-header ux-panl-with-underline">
                        <h2>Source Information</h2>
                    </div>
                    <div class="ux-panl-content ux-margin-top-1t">

                        <c:forEach items="${organizationDetails.organizationSourceListObj}" var="sourceList">
                            <div class="ux-hform">
                                <dl>
                                    <dt><label>Authoritative Source:</label> </dt>
                                    <dd>${sourceList.sourceName}
                                        <span></span>
                                    </dd>
                                </dl>

                                <dl>
                                    <dt><label>Source Organization ID:</label>  </dt>
                                    <dd>${sourceList.sourceClientID}</dd>
                                </dl>

                            </div>
                        </c:forEach>


                    </div>



                </div>









            </div>
            <!-- content ends --> 

            <!-- Begin: Footer -->
            <jsp:include page="../includes/footer.jsp"/>
            <!-- End: Footer --> 
        </div>
        <!-- End: Wrapper --> 


    </BODY>
</html>
