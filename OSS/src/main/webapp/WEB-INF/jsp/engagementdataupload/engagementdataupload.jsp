<%-- 
    Document   : engagementdataupload
    Created on : May 13, 2016, 4:07:40 PM
    Author     : akeshavulu
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
                <link rel="stylesheet" href="<c:url value="/css/jquery.dataTables.css"/>" />
                <link rel="stylesheet" href="<c:url value="/appcss/engagementdataupload.css"/>" />
                <!-- CSS : Include -->
                <!-- JS : Include -->
                <jsp:include page="../includes/commonjs.jsp"/>
                <script type="text/javascript" src="<c:url value="/appjs/engagementdataupload.js"/>"></script>
                <!-- JS : Include -->
                <c:set var="userType" value="${constants.DB_USER_TYPE_ADMIN}"/>
                <c:if test="${sessionScope.USER_SESSION != null}">
                    <c:set var="userProfile" value="${sessionScope.USER_SESSION}"/>
                    <c:set var="userType" value="${userProfile.userTypeObj.lookUpEntityName}"/>
                </c:if>
                <style>
                    .ux-panl{
                        display: inline-block;
                    }
                </style>
                </head>
                <body>
                    <!-- Popup Starts -->
                    <div id='blackcover'> </div>
                    <div id='popupbox' class="ux-modl">
                        <div class="ux-modl-header">
                            <a href="#" onclick='popup(0)' title="Close">Close</a>
                            <h6>Notification</h6>
                        </div>

                        <div class="ux-modl-content">
                            <div align="center">
                                <h3><c:out value="${lockDeleteMessage}" escapeXml="true"/></h3>
                            </div>
                            <div class="ux-margin-1t  " align="center">
                                <input type="button" title="OK" value="OK" class="ux-btn-default-action ux-width-7t" onclick='popup(0)' /> 
                            </div>
                        </div>
                    </div>
                    <!-- Popup Ends -->
                    <c:if test="${lockDeleteMessage != null}">
                        <script type="text/javascript">
                            popup(1);
                        </script>
                    </c:if>
                        
                        
                    <!-- Start: Wrapper -->
                    <div class="loader"></div>
                    <div id="ux-wrapper">
                        <!--Header starts here-->
                        <jsp:include page="../includes/header.jsp"/>
                        <!--Header ends here-->
                        <!-- Begin: Content -->
                        <ul class="ux-brdc ux-margin-left-1t" >
                            <li><a title="Engagement Data Upload" href="#" onclick="headerFn('engUploadWorkList.htm')">Engagement Data upload</a></li>
                                <c:choose>
                                    <c:when test="${fn:length(uploadedFilesList) gt 0}">
                                    <li>Document Update</li>
                                    </c:when>
                                    <c:otherwise>
                                    <li>Document Upload</li>
                                    </c:otherwise>
                                </c:choose>
                        </ul>

                        <!--Left navigation starts here-->
                        <jsp:include page="../includes/leftpanel.jsp"/>
                        <!--Left navigation ends here-->
                        
                        <!-- Popup Starts -->
                        <div id='blackcover'> </div>
                        <div id='dpopupbox' class="ux-modl">
                            <div class="ux-modl-header">
                                <a href="#" onclick='deletepopup(0)' title="Close">Close</a>
                                <h6>Notification</h6>
                            </div>

                            <div class="ux-modl-content">
                                <div align="center">
                                    <h3>Are you sure you want to delete the file?</h3>
                                </div>
                                <div class="ux-margin-1t  " align="center">
                                    <input type="button" title="OK" value="OK" class="ux-btn-default-action ux-width-7t" onclick="deleteUploadFile()" id="msgshow"/>
                                    <input type="button" title="Cancel" value="Cancel" class="ux-btn-default-action ux-width-7t" onclick='deletepopup(0)' /> 
                                </div>
                            </div>
                        </div>
                        <!-- Popup Ends -->

                        <!-- content starts -->
                        <div class="ux-content">

                            <c:choose>
                                <c:when test="${fn:length(uploadedFilesList) gt 0}">
                                    <h1>Document Update</h1>
                                </c:when>
                                <c:otherwise>
                                    <h1>Document Upload</h1>
                                </c:otherwise>
                            </c:choose>
                            <c:if test="${null != successMessage}">
                                <div style="width: 1000px"class="ux-msg-success">${successMessage}</div>
                            </c:if>
                            <spring:form id="uploadFileDetails" name="uploadFileDetails" modelAttribute="uploadFileDetails" action="" 
                                         method="post" enctype="multipart/form-data">
                                <div class="ux-panl">
                                    <div class="ux-panl-header ux-panl-with-underline">
                                        <h2>Service Engagement Information</h2>
                                    </div>
                                    <input type="hidden" name="encS" value="${clientEngagementDTO.encS}"/>
                                    <div class="ux-panl-content">
                                        <div class="ux-hform">
                                            <dl>
                                                <dt>
                                                    <label>Client Name:</label>
                                                </dt>
                                                <dd>${clientEngagementDTO.clientName}</dd>
                                            </dl>

                                            <dl>
                                                <dt><label>Engagement Code: </label></dt>
                                                <dd>${clientEngagementDTO.engagementCode}</dd>
                                            </dl>
                                            <dl>
                                                <dt>
                                                    <label>Engagement Name:</label>
                                                </dt>
                                                <dd>${clientEngagementDTO.engagementName}</dd>
                                            </dl>

                                            <dl>
                                                <dt>
                                                    <label>Product:</label>
                                                </dt>
                                                <dd>${clientEngagementDTO.securityPackageObj.securityPackageName}</dd>
                                            </dl>
                                            <dl>
                                                <dt>
                                                    <label>Service Name:</label>
                                                </dt>
                                                <dd>${clientEngagementDTO.securityServiceName}</dd>
                                            </dl>
                                            <dl>
                                                <dt>
                                                    <label>Service Start Date:</label>
                                                </dt>
                                                <dd>${clientEngagementDTO.estimatedStartDate}</dd>
                                            </dl>
                                            <dl>
                                                <dt>
                                                    <label>Service End Date:</label>
                                                </dt>
                                                <dd>${clientEngagementDTO.estimatedEndDate}</dd>
                                            </dl>

                                            <dl>
                                                <dt><label>Agreement Date: </label></dt>
                                                <dd>${clientEngagementDTO.agreementDate}</dd>
                                            </dl>
                                            <div class="ux-clear"></div>
                                            <dl>
                                                <c:if test="${clientEngagementDTO.lockCheckbox}">
                                                    <c:if test="${allPermissionSet.contains(permissionConstants.UNLOCK_SERVICE_DATA)}">
                                                        <dt><label>Unlock Service Data: </label></dt>
                                                        <dd> 
                                                            <input type="checkbox" id="lockCheckbox" name="lockCheckbox" checked="true" onclick="checkboxvalidate('${clientEngagementDTO.lockCheckbox}')"/> 

                                                        </dd>
                                                        <label id="errorMsglock" class="error" style="color: red;float:left"></label>
                                                    </c:if>
                                                </c:if>

                                                <c:if test="${not clientEngagementDTO.lockCheckbox}">
                                                    <c:if test="${allPermissionSet.contains(permissionConstants.LOCK_SERVICE_DATA)}">
                                                        <dt><label>Lock Service Data: </label></dt>
                                                        <dd>   <input type="checkbox" id="lockCheckbox" name="lockCheckbox" onclick="checkboxvalidate('${clientEngagementDTO.lockCheckbox}')" />

                                                        </dd>
                                                        <label id="errorMsglock" class="error" style="color: red;float:left"></label>
                                                        <spring:errors cssClass="error"  path="lockCheckbox" cssStyle="color: red;width:200px; display: block; font-weight: bold"/> 
                                                    </c:if>
                                                </c:if>
                                            </dl>
                                        </div>
                                    </div>
                                </div>
                                <div class="ux-panl ux-width-full-inclusive">
                                    <div class="ux-panl-header ux-panl-with-underline">
                                        <h2>Document Upload</h2>
                                    </div>
                                    <div class="ux-panl-content">

                                        <div class="ux-hform">
                                            <c:choose>
                                                <c:when test="${clientEngagementDTO.lockCheckbox}">
                                                    <div class="uploadData" id="uploadengData" <c:if test="${clientEngagementDTO.lockCheckbox}">style="display:none"</c:if>>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="uploadData" id="uploadengData">
                                                </c:otherwise>
                                            </c:choose>
                                                
                                                    <p class="ux-form-required ux-margin-top-1t"> <strong class="ux-labl-required">*</strong> Required </p>
                                                    <dl>
                                                        <dt><label>Finding Source Name: <span>*</span></label></dt>
                                                        <dd>
                                                            <select name="sourceNameKey" id="sourceNameKey">
                                                                <option value="0" title="Select">Select</option>
                                                                <c:if test="${sourceNameList != null}">
                                                                    <c:choose>
                                                                        <c:when test="${redirectFlag == 'Yes'}">
                                                                            <c:set var="selectedSrcVal" value="${uploadFileDetails.sourceNameKey}"/>
                                                                            <c:forEach items="${sourceNameList}" var="sourceName">
                                                                                <option title="${sourceName.lookUpEntityName}" value="${sourceName.masterLookupKey}" <c:if test="${sourceName.masterLookupKey == selectedSrcVal}"> selected="selected"</c:if> >${sourceName.lookUpEntityName}</option>
                                                                            </c:forEach> 
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <c:forEach items="${sourceNameList}" var="sourceName">
                                                                                <option title="${sourceName.lookUpEntityName}" value="${sourceName.masterLookupKey}" >${sourceName.lookUpEntityName}</option>
                                                                            </c:forEach> 
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:if>
                                                            </select>
                                                            <spring:errors cssClass="error"  path="sourceNameKey" cssStyle="color: red;width:200px; display: block; font-weight: bold"/>    
                                                            <label id="errorMsgSourceName" class="error" style="color: red;float:left"></label> 
                                                        </dd>
                                                    </dl>
                                                <dl>
                                                    <dt><label>Document Type: <span>*</span></label></dt>
                                                    <dd>
                                                        <select name="documentTypeKey" id="documentTypeKey">
                                                            <option value="0" title="Select">Select</option>
                                                            <c:if test="${documentTypeList != null}">
                                                                <c:choose>
                                                                    <c:when test="${redirectFlag == 'Yes'}">
                                                                        <c:set var="selectedValue" value="${uploadFileDetails.documentTypeKey}"/>
                                                                        <c:forEach items="${documentTypeList}" var="documentType">
                                                                            <option title="${documentType.lookUpEntityName}" value="${documentType.masterLookupKey}" <c:if test="${documentType.masterLookupKey == selectedValue}"> selected="selected"</c:if>>${documentType.lookUpEntityName}</option>
                                                                        </c:forEach> 
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <c:forEach items="${documentTypeList}" var="documentType">
                                                                            <option title="${documentType.lookUpEntityName}" value="${documentType.masterLookupKey}">${documentType.lookUpEntityName}</option>
                                                                        </c:forEach> 
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:if>
                                                        </select>
                                                        <spring:errors cssClass="error"  path="documentTypeKey" cssStyle="color: red;width:190px; display: block; font-weight: bold"/>
                                                        <label id="errorMsgDocType" class="error" style="color: red;float:left"></label> 
                                                    </dd>
                                                </dl>
                                                <dl>
                                                    <dt><label>Comments:</label></dt>
                                                    <c:choose>
                                                        <c:when test="${redirectFlag == 'Yes'}">
                                                             <dd><textarea name="uploadComments" id="uploadComments" >${uploadFileDetails.uploadComments}</textarea></dd>
                                                        </c:when>
                                                        <c:otherwise>
                                                           <dd><textarea name="uploadComments" id="uploadComments" ></textarea></dd>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    
                                                    <spring:errors cssClass="error"  path="uploadComments" cssStyle="color: red;width:190px; display: block; font-weight: bold"/>
                                                    <label id="errorMsgUploadComments" class="error" style="color: red;float:left"></label> 
                                                </dl>
                                                <div class="ux-clear"></div>
                                                <dl class="LongTxt">
                                                    <dt><label>Upload File: <span>*</span></label></dt>
                                                    <dd> 
                                                                 <input name="templateupload" id="templateupload" type="file"  />
                                                        <c:choose>
                                                            <c:when test="${fileNameExists!='' && null!=fileNameExists}">
                                                                <span id="errorMsgUpload" class="error" style="color: red;width:190px; display: block; font-weight: bold">${fileNameExists}</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span id="errorMsgUpload" class="error" style="color: red;width:190px; display: block; font-weight: bold"></span>
                                                                <spring:errors cssClass="error"  path="templateupload" cssStyle="color: red;width:190px; display: block; font-weight: bold"/>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </dd>
                                                </dl>
                                                <dl>
                                                    <dd style="padding-top: 15px;"> <input type="button" title="Upload" value="Upload" class="ux-btn" id="engagementUpload" onclick="uploadFileSave('${clientEngagementDTO.engagementCode}', '${clientEngagementDTO.securityServiceCode}')" /></dd>
                                                </dl>
                                                <input type="hidden" name="encEngagementCode" id="encEngagementCode" value="${encryptedEngagementCode}"/>
                                                <input type="hidden" name="encSecurityServiceCode" id="encSecurityServiceCode" value="${encryptedSecurityServiceCode}"/>
                                                <!--<input type="hidden" name="uploadedFilesCount" id="uploadedFilesCount" value="${uploadedFilesListSize}"/>-->
                                            </div>
                                            <div class="ux-clear"></div>
                                        </div>
                                        <!--Jquery Data table Start-->
                                        <div class="ux-float-left ux-width-full-inclusive">
                                            <table id="dataTable"  class="display">
                                                <thead>
                                                    <tr>
                                                        <td></td>
                                                        <td></td>
                                                        <td></td>
                                                        <!--<td></td>-->
                                                        <td class="dateid"><input type="text" onchange="$.fn.dateFilter(this, '#dataTable')" id="defaultActualPicker" maxlength="10" style="padding-right: 99px;" readonly="true"/> </td>
                                                        <td></td>
                                                        <td><span><a href="javascript:void(0)" onclick="$.fn.SearchFilterClear(this, '#dataTable')" title="Refresh"><img src="<c:url value="/images/refresh.png" />" /></a></span></td>
                                                    </tr>
                                                    <tr>
                                                        <th class="ux-width-10t">Finding Source Name</th>
                                                        <th class="ux-width-10t">Document Type</th>
                                                        <th class="ux-width-15t">File Name</th>
                                                        <!--                                                        <th class="ux-width-15t">Source File Path</th>                                                           -->
                                                        <th class="ux-width-20t">Updated On</th>
                                                        <th>Last Updated By</th>
                                                        <th class="ux-width-5t">Action </th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:if test="${fn:length(uploadedFilesList) gt 0}">
                                                        <c:forEach var="fileDetails" items="${uploadedFilesList}">
                                                            <tr id="row1">
                                                                <td>${fileDetails.sourceName}</td>
                                                                <td>${fileDetails.documentType}</td>
                                                                <%--<td><a href="#" title="${fileDetails.uploadFileName}" onclick="downloadFile('${fileDetails.uploadFileName}', '${fileDetails.tempFilePath}')">${fileDetails.uploadFileName}</a></td>--%>
                                                                <c:choose>
                                                                    <c:when test="${fileDetails.fileStatusKey == constants.DB_CONSTANT_FILE_STATUS_KEY_SCAN_INPROGRESS || 
                                                                                    fileDetails.fileStatusKey == constants.DB_CONSTANT_FILE_STATUS_KEY_SCAN_FAILED}">
                                                                        <td>${fileDetails.uploadFileName}</td>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <!--<td><a href="#" title="${fileDetails.uploadFileName}" onclick="downloadFile('${fileDetails.fileUploadID}')">${fileDetails.uploadFileName}</a></td>-->
                                                                        <td><a href="<c:url value="/downloadFile.htm?encFileDownload=${fileDetails.encFileDownload}"/>" title="${fileDetails.uploadFileName}" >${fileDetails.uploadFileName}</a></td>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                                
                                                                    <%--  <td>${fileDetails.uploadFilePath}</td>--%>
                                                                 <input type="hidden" name="encFileDownload${fileDetails.fileUploadID}" id="encFileDownload${fileDetails.fileUploadID}" value="${fileDetails.encFileDownload}"/>
                                                                <td>${fileDetails.modifiedDate}</td>
                                                                <td>${fileDetails.modifiedByUser}</td>
                                                                <c:choose>
                                                                    <c:when test="${fileDetails.fileStatusKey == constants.DB_CONSTANT_FILE_STATUS_KEY_NEW}">
                                                                        <!--<td><a href="#" class="ux-icon-delete" id="rowdel" title="Remove" onclick="deleteUploadFile(${fileDetails.fileUploadID})" >Remove</a> </td>-->
                                                                        <c:choose>
                                                                            <c:when test="${clientEngagementDTO.lockCheckbox == true && fn:length(uploadedFilesList) == 1}">
                                                                                <td></td>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <td><a href="#" class="ux-icon-delete" id="rowdel" title="Remove" onclick="deleteFile(${fileDetails.fileUploadID})" >Remove</a> </td>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <!--<td><span class="ux-icon-delete" id="rowdel" title="Remove" >Remove</span> </td>-->
                                                                        <c:choose>
                                                                            <c:when test="${fileDetails.fileStatusKey == constants.DB_CONSTANT_FILE_STATUS_KEY_SCAN_INPROGRESS}">
                                                                                <td> </td>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <!--<td><span class="ux-icon-delete" id="rowdel" title="Remove" onclick="deleteUploadFile(${fileDetails.fileUploadID})" >Remove</span> </td>-->
                                                                                <c:choose>
                                                                                    <c:when test="${clientEngagementDTO.lockCheckbox == true && fn:length(uploadedFilesList) == 1}">
                                                                                        <td></td>
                                                                                    </c:when>
                                                                                    <c:otherwise>
                                                                                        <td><a href="#" class="ux-icon-delete" id="rowdel" title="Remove" onclick="deleteFile(${fileDetails.fileUploadID})" >Remove</a> </td>
                                                                                    </c:otherwise>
                                                                                </c:choose>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                                <input type="hidden" name="encfileUploadID${fileDetails.fileUploadID}" id="encfileUploadID${fileDetails.fileUploadID}" value="${fileDetails.encfileUploadID}"/>
                                                            </tr>
                                                        </c:forEach>
                                                    </c:if>
                                                </tbody>
                                            </table>
                                        </div>
                                        <!--Jquery Data table End-->
                                    </div>
                                </div>
                                <div class="ux-float-right">
                                    <c:choose>
                                        <c:when test="${fn:length(uploadedFilesList) gt 0}">
                                            <c:if test="${clientEngagementDTO.lockCheckbox}">
                                                <c:if test="${allPermissionSet.contains(permissionConstants.UNLOCK_SERVICE_DATA)}">
                                                    <input type="button" value="Update" title="Update" class="ux-btn-default-action " onclick="lockUnlockSave('${uploadedFilesListSize}')" />
                                                </c:if>
                                            </c:if>
                                            <c:if test="${not clientEngagementDTO.lockCheckbox}">
                                                <c:if test="${allPermissionSet.contains(permissionConstants.LOCK_SERVICE_DATA)}">
                                                    <input type="button" value="Update" title="Update" class="ux-btn-default-action " onclick="lockUnlockSave('${uploadedFilesListSize}')" />
                                                </c:if>
                                            </c:if>        
                                        </c:when>
                                        <c:otherwise>
                                            <c:if test="${clientEngagementDTO.lockCheckbox}">
                                                <c:if test="${allPermissionSet.contains(permissionConstants.UNLOCK_SERVICE_DATA)}">
                                                    <input type="button" value="Save"  title="Save" class="ux-btn-default-action " onclick="lockUnlockSave('${uploadedFilesListSize}')" />
                                                </c:if>
                                            </c:if>
                                            <c:if test="${not clientEngagementDTO.lockCheckbox}">
                                                <c:if test="${allPermissionSet.contains(permissionConstants.LOCK_SERVICE_DATA)}">
                                                    <input type="button" value="Save"  title="Save" class="ux-btn-default-action " onclick="lockUnlockSave('${uploadedFilesListSize}')" />
                                                </c:if>
                                            </c:if> 

                                        </c:otherwise>
                                    </c:choose>
                                    <input type="button" value="Cancel" title="Cancel" id="cancelUpload" class="ux-btn-default-action " onclick="cancelBtn()"/>
                                </div>
                                 <input type="hidden" name="encfileUploadID" id="encfileUploadID" value=""/>                    
                            </spring:form>
                                    <form name="fileuploaddetails" action="#"  id="fileuploaddetails" method="post">
                                <!--<input type="hidden" name="uploadFileName" id="uploadFileName" value=""/>-->
                                <input type="hidden" name="encFileDownload" id="encFileDownload" value=""/>                               
                            </form>

                        </div>
                        <!-- content ends -->
                        <!-- Begin: Footer -->
                        <jsp:include page="../includes/footer.jsp"/>
                        <!-- End: Footer -->
                    </div>
                    <!-- End: Wrapper -->
                </body>
                </html>
