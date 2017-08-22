<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<html>
    <head>
        <META content="IE=9" http-equiv="X-UA-Compatible">
            <META content="text/html; charset=utf-8" http-equiv="Content-Type">
                <title>IRMaaS</title>
                <!-- CSS : Include -->
                <jsp:include page="../includes/commoncss.jsp"/>
                <link rel="stylesheet" href="<c:url value="/css/jquery.dataTables.css"/>" />
                <link rel="stylesheet" href="<c:url value="/appcss/engagementdataupload.css"/>" />
                <!-- CSS : Include -->
                <!-- JS : Include -->
                <jsp:include page="../includes/commonjs.jsp"/>
                <script type="text/javascript" src="<c:url value="/appjs/engagementdataupload.js"/>"></script>
                <!-- JS : Include -->
                </head>
                <body>
                    <!-- Start: Wrapper -->
                    <div id="ux-wrapper">
                        <!--Header starts here-->
                        <jsp:include page="../includes/header.jsp"/>
                        <!--Header ends here-->
                        <!-- Begin: Content -->
                        <ul class="ux-brdc ux-margin-left-1t" >
                            <li><a title="Engagement Data upload" href="#" onclick="headerFn('engUploadWorkList.htm')">Engagement Data upload</a></li>
                            <li>View Document</li>
                        </ul>

                        <!--Left navigation starts here-->
                        <jsp:include page="../includes/leftpanel.jsp"/>
                        <!--Left navigation ends here-->

                        <!-- content starts -->
                        <div class="ux-content">
                            <h1>View Document <span class="ux-float-right"> <input type="button" title="Back" value="Back" class="ux-btn-default-action" onclick="headerFn('engUploadWorkList.htm')"/>
                                </span></h1>
                                <%--<spring:form id="uploadFileDetails" name="uploadFileDetails" modelAttribute="uploadFileDetails" action="" method="post" enctype="multipart/form-data">--%>
                                <div class="ux-panl ux-float-left">
                                    <div class="ux-panl-header ux-panl-with-underline">
                                        <h2>Service Engagement Information</h2>
                                    </div>
                                    <div class="ux-panl-content">
                                        <div class="ux-hform">
                                            <dl>
                                                <dt>
                                                    <label>Client Name:</label>
                                                </dt>
                                                <!--<dd>ABC Healthcare</dd>-->
                                                <dd>${clientEngagementDTO.clientName}</dd>
                                            </dl>

                                            <dl>
                                                <dt><label>Engagement Code: </label></dt>
                                                <!--<dd>ABC01-HC-20160604</dd>-->
                                                <dd>${clientEngagementDTO.engagementCode}</dd>
                                            </dl>
                                            <dl>
                                                <dt>
                                                    <label>Engagement Name:</label>
                                                </dt>
                                                <!--<dd>Engagement1</dd>-->
                                                <dd>${clientEngagementDTO.engagementName}</dd>
                                            </dl>

                                            <dl>
                                                <dt>
                                                    <label>Product:</label>
                                                </dt>
                                                <!--<dd>Health Check </dd>-->
                                                <dd>${clientEngagementDTO.securityPackageObj.securityPackageName}</dd>
                                            </dl>
                                            <dl>
                                                <dt>
                                                    <label>Service Name:</label>
                                                </dt>
                                                <!--<dd>Application Vulnerability Assessment </dd>-->
                                                <dd>${clientEngagementDTO.securityServiceName}</dd>
                                            </dl>
                                            <dl>
                                                <dt>
                                                    <label>Service Start Date:</label>
                                                </dt>
                                                <!--<dd>03/15/16</dd>-->
                                                <dd>${clientEngagementDTO.estimatedStartDate}</dd>
                                            </dl>
                                            <dl>
                                                <dt>
                                                    <label>Service End Date:</label>
                                                </dt>
                                                <!--<dd>03/30/16</dd>-->
                                                <dd>${clientEngagementDTO.estimatedEndDate}</dd>
                                            </dl>

                                            <dl>
                                                <dt><label>Agreement Date: </label></dt>
                                                <!--<dd>03/30/16 </dd>-->
                                                <dd>${clientEngagementDTO.agreementDate}</dd>
                                            </dl>
                                            <%--   <c:if test="${permissionSet.contains(prmnsConstants.ADD_ROLE)}">
                                            <input type="button" value="Add Role" title="Add Role" class="ux-btn-default-action" onclick="addRolePage()"  />
</c:if> --%>
                                           <div class="ux-clear"></div>
                                            <dl>
                                                <dt><label>Lock Service Data: </label></dt>
                                                <dd>
                                                    <c:choose>
                                                        <c:when test="${clientEngagementDTO.lockCheckbox}">
                                                            <input type="checkbox" id="lockCheckbox" name="lockCheckbox" checked="true" disabled="true"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input type="checkbox" id="lockCheckbox" name="lockCheckbox" disabled="true"/>
                                                        </c:otherwise>
                                                    </c:choose>

                                                </dd>
                                            </dl>
                                        </div>
                                    </div>
                                </div>
                                <div class="ux-panl ux-float-left ux-width-full-inclusive">
                                    <div class="ux-panl-header ux-panl-with-underline">
                                        <h2>Document Upload</h2>
                                    </div>
                                    <div class="ux-panl-content">
                                        <div >
                                            <table class="ux-tabl-data">
                                                <thead>
                                                    <tr>
                                                        <th>Finding Source Name</th>
                                                        <th>Document Type</th>
                                                        <th>File Name</th>
                                                        <th>Updated On</th>
                                                        <th>Last Updated By</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:if test="${fn:length(uploadedFilesList) gt 0}">
                                                        <c:forEach var="fileDetails" items="${uploadedFilesList}">
                                                            <tr id="row1">
                                                                <td>${fileDetails.sourceName}</td>
                                                                <td>${fileDetails.documentType}</td>
                                                                <!--<td><a href="#" onclick="downloadFile('${fileDetails.uploadFileName}', '${fileDetails.tempFilePath}')" title="${fileDetails.uploadFileName}">${fileDetails.uploadFileName}</a></td>-->
                                                                <td><a href="<c:url value="/downloadFile.htm?encFileDownload=${fileDetails.encFileDownload}"/>" title="${fileDetails.uploadFileName}" >${fileDetails.uploadFileName}</a></td>
                                                                <td>${fileDetails.modifiedDate}</td>
                                                                <td>${fileDetails.modifiedByUser}</td>
                                                            </tr>
                                                        </c:forEach>
                                                    </c:if>
                                                </tbody>		
                                            </table>
                                            <spring:form name="fileuploaddetails" action="#"  id="fileuploaddetails" commandName="fileuploaddetails">
                                                <input type="hidden" name="uploadFileName" id="uploadFileName" value=""/>
                                                <input type="hidden" name="uploadFilePath" id="uploadFilePath" value=""/>
                                            </spring:form>
                                        </div>
                                    </div>
                                </div>

                            <%--</spring:form>--%>
                        </div>
                        <!-- content ends -->
                        <!-- Begin: Footer -->
                        <jsp:include page="../includes/footer.jsp"/>
                        <!-- End: Footer -->
                    </div>
                    <!-- End: Wrapper -->
                </body>
                </html>
