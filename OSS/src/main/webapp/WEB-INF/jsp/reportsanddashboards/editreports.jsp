<%-- 
    Document   : editreports
    Created on : Jul 5, 2016, 11:26:04 AM
    Author     : schanganti
--%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="constants" />
<un:useConstants className="com.optum.oss.constants.PermissionConstants" var="permissionConstants" />
<html>
    <head>
        <META content="IE=9" http-equiv="X-UA-Compatible">
            <META content="text/html; charset=utf-8" http-equiv="Content-Type">
                <title>IRMaaS</title>
                <!-- CSS : Include -->
                <jsp:include page="../includes/commoncss.jsp"/>
                <link rel="stylesheet" href="<c:url value="/appcss/editreports.css"/>" />
                <link rel="stylesheet" href="<c:url value="/css/jquery.dataTables.css"/>" />
                <!-- CSS : Include -->
                <!-- JS : Include -->
                <jsp:include page="../includes/commonjs.jsp"/>
                <script type="text/javascript" src="<c:url value="/appjs/editreports.js"/>"></script>
                <!-- JS : Include -->
                <!-- Header : Include -->
                <jsp:include page="../includes/header.jsp"/>
                <!-- Header : Include -->

                </head>
                <body>
                    <!-- Start: Wrapper -->
                    <div id="ux-wrapper">

                        <!-- Begin: Secondary Nav -->

                        <!-- End: Secondary Nav --> 

                        <!-- Begin: Content -->                   
                        <ul class="ux-brdc ux-margin-left-1t" >
                             <li>Optum Security Solutions</li>
                             <li><a href="#" onclick="headerFn('reportsuploadworklist.htm')">Client Reports Upload</a></li> 
                            <li>Edit Report</li> 
                        </ul>

                        <jsp:include page="../includes/leftpanel.jsp"/>
                        <!-- Popup Starts -->
                        <div id='blackcover'> </div>
                        <div id='popupbox' class="ux-modl">
                            <div class="ux-modl-header">
                                <a href="#" onclick='popup(0)' title="Close">Close</a>
                                <h6>Notification</h6>
                            </div>

                            <div class="ux-modl-content">
                                <div align="center">
                                    <h3>Are you sure you want to delete the Report?</h3>
                                </div>
                                <div class="ux-margin-1t  " align="center">
                                    <input type="button" title="OK" value="OK" class="ux-btn-default-action ux-width-7t" onclick="editdeleteuploadfilelist()" id="msgshow"/>
                                    <input type="button" title="Cancel" value="Cancel" class="ux-btn-default-action ux-width-7t" onclick='popup(0)' /> 
                                </div>
                            </div>
                        </div>
                        <!-- Popup Ends -->

                        <!-- content starts -->
                        <div class="ux-content"> 
                            <h1>Edit Reports <span class="ux-float-right">
                                        <c:if test="${permissions.contains(permissionConstants.VIEW_DASHBOARD)}">
                                            <input type="button" value="View Dashboard" title="View Dashboard" class="ux-btn-default-action" onclick="javascript:toDashboardPage('${constants.TO_CLIENTREPORTSUPLOAD_LISTPAGE}')"></input>
                                        </c:if>
                                    <input type="button" value="Back" title="Back" class="ux-btn-default-action" onclick="headerFn('reportsuploadworklist.htm')" /> </span></h1>
                                    <c:if test="${null != successMessage}">
                                <div class="ux-msg-success">${successMessage}</div>
                            </c:if>
                            <form name="saveUploadReportFile" action="#" method="POST" id="saveUploadReportFile" commandName="saveReports"  enctype="multipart/form-data">
                                <input type="hidden" value="" name="serverSideFlag" id="serversideflag"/>
                                <div id="accordion" class="ux-margin-bottom-1t">
                                    <h3 class="ui-accordion-header ui-test ui-accordion-icons ui-corner-all">Engagement Information</h3>
                                    <div class="ux-accordion-panl">
                                        <!--                                    <div class="ux-panl-content">-->
                                        <div class="ux-hform">

                                            <dl>
                                                <dt>
                                                    <label>Client Name:</label>
                                                </dt>
                                                <dd>${clientEngagementDTO.clientName}</dd>
                                                <input type="hidden" value="${clientEngagementDTO.clientName}" name="organizationName"/>
                                            </dl>

                                            <dl>
                                                <dt><label>Engagement Code: </label></dt>
                                                <dd>${clientEngagementDTO.engagementCode}</dd>
                                                <input type="hidden" value="${clientEngagementDTO.encEngagementCode}" name="encClientEngagementCode" id="clientEngagementCodeValue"/>
                                                <!-- Start: below values are using for navigation to dashboard page -->
                                                <input type="hidden" value="${clientEngagementDTO.encEngagementCode}" name="encEngagementCode" id="encEngagementCode"/>
                                                <input type="hidden" value="${clientEngagementDTO.securityPackageObj.encSecurityPackageCode}" name="encSecurityPackageCode" id="encSecurityPackageCode"/>
                                                <input type="hidden" value="${clientEngagementDTO.securityPackageObj.securityPackageName}" name="securityPackageName" id="securityPackageName"/>
                                                <!-- End : below values are using for navigation to dashboard page -->
                                            </dl>
                                            <dl>
                                                <dt>
                                                    <label>Engagement Name:</label>
                                                </dt>
                                                <dd>${clientEngagementDTO.engagementName}</dd>
                                                <input type="hidden" value="${clientEngagementDTO.engagementName}" name="clientEngagementName"/>
                                            </dl>

                                            <dl>
                                                <dt>
                                                    <label>Product:</label>
                                                </dt>
                                                <dd>${clientEngagementDTO.securityPackageObj.securityPackageName}</dd>
                                            </dl>

                                        </div>
                                    </div>
                                </div>
                                <div class="ux-panl ux-float-left">
                                    <div class="ux-panl-header ux-panl-with-underline">
                                        <h2>Reports Upload</h2>

                                        <p class="ux-form-required">
                                            <strong class="ux-labl-required ">*</strong> Required
                                        </p>
                                    </div><div class="ux-clear"></div>
                                    <div class="ux-panl-content">
                                        <div class="ux-hform">

                                            <dl class="LongTxt">
                                                <dt>
                                                    <label>Report Name:<span> * </span></label>
                                                </dt>
                                                <dd>
                                                    <input type="hidden" name="reportName" value="" id="reportName"/>
                                                    <select class="ux-width-23t" id="addrep" name="reportkey">
                                                        <!--<option>Select</option>-->
                                                        <option value="0" title="Select">Select</option>
                                                        <c:forEach var="reportName" items="${reportNames}">
                                                            <option value="${reportName.masterLookupKey}" <c:if test="${saveReports.reportkey == reportName.masterLookupKey}">selected</c:if>>
                                                                ${reportName.lookUpEntityName}</option>
                                                        </c:forEach>
                                                    </select>
                                                    <span id="errorReportName" class="error" style="color: red;width:190px; display: block; font-weight: bold"></span>
                                                    <spring:errors cssClass="error"  path="saveReports.reportkey" cssStyle="color: red;width:330px; display: block; font-weight: bold"/> 
                                                    <!--<input type="text" value="" class="ux-margin-top-halft ux-width-31t" id="rep" />-->
                                                </dd>
                                            </dl>
                                            <div class="ux-clear"></div>
                                            <dl class="LongTxt">
                                                <dt><label>Upload File:<span> * </span> </label></dt>
                                                <dd> <div id="multipleupload"><input name="templateupload" type="file" id="templateupload"></input></div>
                                                    <c:choose>
                                                        <c:when test="${fileNameExists!='' && null!=fileNameExists}">
                                                            <span id="errorMsgUpload" class="error" style="color: red;width:190px; display: block; font-weight: bold">${fileNameExists}</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span id="errorMsgUpload" class="error" style="color: red;width:190px; display: block; font-weight: bold"></span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <!--<span id="errorMsgUpload" class="error" style="color: red;width:190px; display: block; font-weight: bold"></span>-->
                                                </dd>
                                            </dl>
                                            <span class=" ux-float-left ux-margin-top-1t"><input type="button" value="Upload" title="Upload" class="ux-btn" id="uploadFile" /></span>
                                            <spring:errors cssClass="error"  path="saveReports.templateupload" cssStyle="color: red;width:190px; display: block; font-weight: bold"/>

                                        </div>
                                        <input type="hidden" value="E" name="redirectFlag"/>
                                        </form>
                                        <div id="reportlist">
                                            <form name="publishReportForm" action="" method="POST" id="publishReportForm" commandName="saveReports">
                                                <input type="hidden" value="" name="serverSideFlag" id="serversideflagpublish"/> 
                                                <input type="hidden" value="${clientEngagementDTO.encEngagementCode}" name="encClientEngagementCode"/>
                                                <c:if test="${fn:length(uploadFileList) > 0 }">
                                                    <input type="hidden" value="E" name="redirectFlag"/>
                                                    <!--<label id="errorCheckboxValidation" style="color: red;" class="ux-float-left top"></label>-->
                                                    <span id="errorCheckboxValidation" class="error" style="color: red;width:250px; display: block; font-weight: bold; margin-bottom: -6px;"></span>
                                                    <spring:errors cssClass="error"  path="saveReports.reportNameCheckId" cssStyle="color: red;width:190px; display: block; font-weight: bold"/>
                                                    <table class="ux-tabl-data">
                                                        <thead>

                                                            <tr>
                                                                <th><input type="checkbox" id="headCheckbox" onchange="checkAll(this)"/></th>
                                                                <th>Report Name</th>
                                                                <th>File Name</th>
                                                                <th>Updated On</th>
                                                                <th>Last Updated by</th>
                                                                <th>Status</th>
                                                                <th>Action</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <input type="hidden" id="status" name="status" value=""/>
                                                            <c:set var="count" value="0"/>
                                                            <c:forEach var="serviceListIte" items="${uploadFileList}">
                                                                <tr>
                                                                    <td><c:choose>
                                                                            <c:when test="${serviceListIte.rowStatusKey == constants.DB_ROW_STATUS_PUBLISHED_VALUE || serviceListIte.rowStatusKey == constants.REPORT_FILE_STATUS_KEY_SCAN_INPROGRESS}">
                                                                                <input type="hidden" id="unPublishedValue${count}"
                                                                                       value="${serviceListIte.uploadFileKey}@${serviceListIte.rowStatusKey}@${serviceListIte.reportName}@${serviceListIte.fileName}@${serviceListIte.filePath}@${serviceListIte.rowStatusKey}" <c:if test="${serviceListIte.rowStatusKey == constants.DB_ROW_STATUS_PUBLISHED_VALUE}">checked</c:if> onchange="singleCheckbox(this)"/>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <input class="checkboxGroup" type="checkbox" name="reportNameCheckId" 
                                                                                       value="${serviceListIte.uploadFileKey}@${serviceListIte.rowStatusKey}@${serviceListIte.reportName}@${serviceListIte.fileName}@${serviceListIte.filePath}@${serviceListIte.rowStatusKey}" <c:if test="${serviceListIte.rowStatusKey == constants.DB_ROW_STATUS_PUBLISHED_VALUE}">checked</c:if> onchange="singleCheckbox(this)"/>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </td>

                                                                    <td>${serviceListIte.reportName}</td>
                                                                    <c:choose>
                                                                        <c:when test="${serviceListIte.rowStatusKey == constants.REPORT_FILE_STATUS_KEY_SCAN_INPROGRESS || 
                                                                                    fileDetails.rowStatusKey == constants.REPORT_FILE_STATUS_KEY_SCAN_FAILED}">
                                                                            <td>${serviceListIte.fileName}</td>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                            <td><a href="downloadreportFile.htm?fp=${serviceListIte.encFilePath}" title="${serviceListIte.fileName}">${serviceListIte.fileName}</a></td>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                    <!--<td><a href="downloadreportFile.htm?fp=${serviceListIte.encFilePath}" title="${serviceListIte.fileName}">${serviceListIte.fileName}</a></td>-->
                                                                    <td>${serviceListIte.updateDate}</td>
                                                                    <td>${serviceListIte.modifiedByUser}</td>
                                                                    <td>${serviceListIte.status}</td>
                                                                    <td>
                                                                        <c:if test="${serviceListIte.rowStatusKey != constants.DB_ROW_STATUS_PUBLISHED_VALUE}">
                                                                            <c:choose>
                                                                                <%--<c:when test="${serviceListIte.rowStatusKey == constants.DB_CONSTANT_FILE_STATUS_KEY_NEW}">--%>
                                                                                <c:when test="${serviceListIte.rowStatusKey == constants.DB_ROW_STATUS_NOTPUBLISHED_VALUE}">
                                                                                    <a href="#" onclick="deleteEditUploadedFile('${serviceListIte.uploadFileKey}')" title="Delete" class="ux-icon-delete">Delete</a>
                                                                                </c:when>
                                                                                <c:otherwise>
                                                                                    <c:if test="${serviceListIte.rowStatusKey != constants.REPORT_FILE_STATUS_KEY_SCAN_INPROGRESS}">
                                                                                        <a href="#" onclick="deleteEditUploadedFile('${serviceListIte.uploadFileKey}')" title="Delete" class="ux-icon-delete">Delete</a>
                                                                                    </c:if>
                                                                                </c:otherwise>
                                                                            </c:choose>
                                                                            <!--<a href="#" onclick="deleteEditUploadedFile('${serviceListIte.uploadFileKey}')" title="Delete" class="ux-icon-delete">Delete</a>-->
                                                                        </c:if>
                                                                        <c:if test="${serviceListIte.rowStatusKey == constants.DB_ROW_STATUS_PUBLISHED_VALUE}">
                                                                            <a href="#" onclick="updateReportStatus(${count})" title="Unpublish" class="ux-icon-unpublish">Unpublish</a>
                                                                        </c:if>
                                                                    </td>
                                                                    <input type="hidden" name="encUploadFileID${serviceListIte.uploadFileKey}" id="encUploadFileID${serviceListIte.uploadFileKey}" 
                                                                           value="${serviceListIte.encUploadFileKey}"/>
                                                                </tr>
                                                                <c:set var="count" value="${count+1}"/>
                                                            </c:forEach>
                                                        </tbody>
                                                    </table>
                                                </c:if>
                                            </form>

                                        </div>
                                    </div>
                                </div>
                                <!--                                <div class="ux-clear"></div>-->
                                <!--                            <div class="ux-msg- message" id="success">Report has been published successfully.</div>-->
                                <!--                            <div class="ux-float-right"><input type="button" value="Save"  class="ux-btn-default-action" /> -->
                                <c:if test="${permissions.contains(permissionConstants.PUBLISH_REPORTS)}">
                                    <c:if test="${fn:length(uploadFileList) > 0 }">
                                    <input name="" type="button" value="Publish" title="Publish" class="ux-btn-default-action" onclick="publishReport();"/>
                                    </c:if>
                                </c:if>
                                    <input type="button" value="Cancel" title="Cancel" class="ux-btn-default-action " id="fnCancelEditReload"/>
                                <!--</form>-->
                                <form name="fileuploaddetails" action="#"  id="fileuploaddetails" method="post">
                                    <!--<input type="hidden" name="uploadFileName" id="uploadFileName" value=""/>-->
                                    <input type="hidden" name="encUploadFileKey" id="encUploadFileKey" value=""/>                               
                                    <input type="hidden" name="encClientEngagementCode" id="encClientEngagementCode" value=""/>
                                    <input type="hidden" value="E" name="redirectFlag"/>
                                </form> 
                                 <form name="toDashboardPageForm" action="#"  id="toDashboardPageForm" method="post">
                                    <input type="hidden" value="${clientEngagementDTO.encEngagementCode}" name="engKey" id="engKey"/>
                                    <input type="hidden" name="from" id="from"/> 
                                </form>
                                <form name="toCancelReloadEditForm" action="#"  id="toCancelReloadEditForm" method="post">
                                    <input type="hidden" name="ec" id="ec"/>
                                </form>
                        </div> 
                        </div>
                    </div> 
                    <!-- content ends -->
                    <!-- Start: Footer -->
                    <jsp:include page="../includes/footer.jsp"/>
                    <!-- End: Footer -->

                    </div>
                    <!-- End: Wrapper -->                




                </body>
                </html>
