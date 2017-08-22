<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>

<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<un:useConstants className="com.optum.oss.constants.ApplicationConstants" var="constants" />
<un:useConstants className="com.optum.oss.constants.PermissionConstants" var="permissionConstants" />
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
              <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=9; IE=10; IE=EDGE" />
        <meta name="viewport" content="width=device-width" />
        <title>IRMaaS</title>
        <!-- CSS : Include -->
        <jsp:include page="../includes/commoncss.jsp"/>
        <link rel="stylesheet" href="<c:url value="/appcss/manageserviceengagements.css"/>" />
        <!-- CSS : Include -->
        <!-- JS : Include -->
        <jsp:include page="../includes/commonjs.jsp"/>
        <script type="text/javascript" src="<c:url value="/appjs/manageserviceengagements.js"/>"></script>
        <!-- JS : Include -->  
        <c:set var="userType" value="${constants.DB_USER_TYPE_ADMIN}"/>
        <c:if test="${sessionScope.USER_SESSION != null}">
            <c:set var="userProfile" value="${sessionScope.USER_SESSION}"/>
            <c:set var="userType" value="${userProfile.userTypeObj.lookUpEntityName}"/>
        </c:if>
    </head>
    <BODY >
        <!-- Start: Wrapper -->
        <div id="ux-wrapper">

            <!--Header starts here-->

            <jsp:include page="../includes/header.jsp"/>
            <!--Header ends here-->

            <!-- Begin: Secondary Nav -->

            <!-- End: Secondary Nav -->

            <!-- Begin: Content -->
            <ul class="ux-brdc ux-margin-left-1t" >
                <li>Engagement Data Upload</li>
            </ul>

            <!--Left navigation starts here-->
            <jsp:include page="../includes/leftpanel.jsp"/>
            <!--Left navigation ends here-->

            <!-- content starts -->
            <div class="ux-content">
                <c:set var="serviceMap" value="${serviceMap}"/>
                <c:set var="organizationServieCount" value="${organizationServieCount}"/>
                
                <h1>Engagement Data Upload</h1>
                <c:if test="${null != successMessage}">
                    <div style="width: 1000px"class="ux-msg-success">${successMessage}</div>
                </c:if>
                      <spring:form action="" name="servicesSearchForm"  commandName="manageServiceUserDTO" method="POST">
                          <input type="hidden"  id="encS" name="encS" />
                <table id="myTable" width="100%" border="0" cellspacing="0" cellpadding="0" class="fixme example">
                    <thead>
                        <tr>
                            <th>Client Name </th>
                            <th>Engagement Code</th>
                            <th>Engagement Name</th>
                            <th>Services </th>
                            <th>Service Start Date  </th>
                            <th>Service  End Date </th>
                            <th>Last Updated On</th>
                            <th>Action</th>
                        </tr>
                      
                            <tr>
                                <td class="ux-padding-left-1t ux-padding-bottom-1t"><input type="text" name="organizationName" value="${manageServiceUserDTO.organizationName}" class="ux-width-full-9t "/></td>
                                <td class="ux-padding-left-1t ux-padding-bottom-1t"><input type="text" name="clientEngagementCode" value="${manageServiceUserDTO.clientEngagementCode}" class="ux-width-full-9t "/></td>
                                <td class="ux-padding-left-1t ux-padding-bottom-1t"><input type="text" name="engagementName" value="${manageServiceUserDTO.engagementName}" class="ux-width-full-9t"/></td>
                                <td class="ux-padding-left-1t ux-padding-bottom-1t"><input type="text" name="serviceName" value="${manageServiceUserDTO.serviceName}" class="ux-width-full-9t"/></td>
                                <td class="ux-padding-left-1t ux-padding-bottom-1t"><input type="text"  readonly name="startDate" value="${manageServiceUserDTO.startDate}" class="ux-width-full-7t defaultActualPicker"/></td>
                                <td class="ux-padding-left-1t ux-padding-bottom-1t"><input type="text"  readonly name="endDate" value="${manageServiceUserDTO.endDate}" class="ux-width-full-7t defaultActualPicker"/></td>
                                <td class="ux-padding-left-1t ux-padding-bottom-1t"><input type="text" readonly name="updateDate"  class="ux-width-full-7t defaultActualPicker" value="${manageServiceUserDTO.updateDate}"/></td>
                                <td class="ux-padding-left-1t ux-padding-bottom-1t"><a href="#"><img src="images/search.png" title="Search" id="searchButton"/></a> <a href="#"><img src="images/refresh.png" title="Refresh" onclick="headerFn('engUploadWorkList.htm')"/></a></td>
                            </tr>
                        
                    </thead>
                    <tbody>
                       
                        <c:choose>
                            <c:when test="${engagementMap.size()> 0}">
                                <c:forEach var="engagementMap" items="${engagementMap}">
                                    <c:set var="orgCount" value="0"/>
                                    <c:set var="organizationDetails" value="${fn:split(engagementMap.key, constants.SYMBOL_SPLITING)}" />
                                    <c:set value="${organizationDetails[0]}" var="orgId"></c:set>
                                    <c:set value="${organizationDetails[1]}" var="orgName"></c:set>
                                        <tr>
                                            <td rowspan="${organizationServieCount[engagementMap.key].size()}">${orgName}</td>

                                        <c:set var="engegementSet" value="${engagementMap.value}"/>
                                        <c:set var="engCount" value="0"/>
                                        <c:forEach var="engagementCode" items="${engegementSet}">

                                            <c:set var="engDetails" value="${fn:split(engagementCode, constants.SYMBOL_SPLITING)}" />
                                            <c:set value="${engDetails[0]}" var="engID"></c:set>
                                            <c:set value="${engDetails[1]}" var="engName"></c:set>

                                            <c:set var="serviceList" value="${serviceMap[engagementCode]}"/>
                                            <c:if test="${engCount!=0}"> <tr> </c:if>
                                                <td rowspan="${serviceList.size()}">${engID}</td>
                                                <td rowspan="${serviceList.size()}">${engName}</td>
                                                <c:set var="serCount" value="0"/>
                                                <c:set var="colorCount" value="0"/>
                                                <c:forEach var="serviceListIte" items="${serviceList}">

                                                    <c:if test="${serCount!=0}"> 
                                                        <tr <c:if test="${colorCount == 1}">style="background-color:#fbf9ba"</c:if> 
                                                                                            <c:if test="${colorCount == 2}">style="background-color:#fde6d1"</c:if> 
                                                                                            <c:if test="${colorCount == 3}">style="background-color:none"</c:if> > 

                                                        </c:if>
                                                        <td>${serviceListIte.serviceName}
                                                            <c:if test="${serviceListIte.fileLockIndicator == constants.DB_CONSTANT_FILE_UNLOCK_INDICATOR}"><span class="ux-icon-unlock" title="Unlocked"></span></c:if>
                                                            <c:if test="${serviceListIte.fileLockIndicator == constants.DB_CONSTANT_FILE_LOCK_INDICATOR}"><span class="ux-icon-lock" title="Locked"></span></c:if>
                                                            </td>
                                                            <td>${serviceListIte.startDate}</td> 
                                                        <td>${serviceListIte.endDate}</td>
                                                        <td>${serviceListIte.updateDate}</td>
                                                        <td style="border-right-width: 1px !important;"> 
                                                            
                                                            <c:choose>
                                                                <c:when test="${userType==constants.DB_USER_TYPE_ADMIN}">
                                                                    <a href="#" onclick="headerFn('upload.html')" title="Upload" class="ux-icon-upload" >Upload</a>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <c:if test="${permissionSet!=null}">
                                                                        <c:if test="${serviceListIte.fileLockIndicator == constants.DB_CONSTANT_FILE_UNLOCK_INDICATOR}">
                                                                            <c:choose>
                                                                                <c:when test="${serviceListIte.fileCount == 0}">
                                                                                    <c:choose>
                                                                                        <c:when test="${permissionSet.contains(permissionConstants.ADD_DOCUMENT_UPLOAD)}">
                                                                                            <a href="javascript:void(0)"  onclick='navigateToEditPage("${fn:trim(serviceListIte.encString)}")' title="Upload" class="ux-icon-upload" >Upload</a>
                                                                                        </c:when>
                                                                                    </c:choose>
                                                                                </c:when>
                                                                                <c:otherwise>
                                                                                    <c:choose>
                                                                                        <c:when test="${permissionSet.contains(permissionConstants.EDIT_DOCUMENT_UPLOAD)}">
                                                                                            <a href="javascript:void(0)"  onclick='navigateToEditPage("${fn:trim(serviceListIte.encString)}")'  title="Update" class="ux-icon-update1" >Update</a>
                                                                                        </c:when>
                                                                                        <c:when test="${permissionSet.contains(permissionConstants.VIEW_DOCUMENT_UPLOAD)}">
                                                                                            <a href="javascript:void(0)"  onclick='viewPage("${fn:trim(serviceListIte.encString)}")'  title="View" class="ux-icon-view" >View</a>
                                                                                        </c:when>
                                                                                    </c:choose>
                                                                                </c:otherwise>
                                                                            </c:choose>


                                                                        </c:if>
                                                                        <c:if test="${serviceListIte.fileLockIndicator == constants.DB_CONSTANT_FILE_LOCK_INDICATOR}">
                                                                            <c:choose>
                                                                                <c:when test="${permissionSet.contains(permissionConstants.UNLOCK_SERVICE_DATA)}">
                                                                                    <c:choose>
                                                                                        <c:when test="${serviceListIte.fileCount == 0}">
                                                                                            <c:choose>
                                                                                                <c:when test="${permissionSet.contains(permissionConstants.ADD_DOCUMENT_UPLOAD)}">
                                                                                                    <a href="javascript:void(0)"  onclick='navigateToEditPage("${fn:trim(serviceListIte.encString)}")'  title="Upload" class="ux-icon-upload" >Upload</a>
                                                                                                </c:when>
                                                                                            </c:choose>
                                                                                        </c:when>
                                                                                        <c:otherwise>
                                                                                            <c:choose>
                                                                                                <c:when test="${permissionSet.contains(permissionConstants.EDIT_DOCUMENT_UPLOAD)}">
                                                                                                    <a href="javascript:void(0)"  onclick='navigateToEditPage("${fn:trim(serviceListIte.encString)}")' title="Update" class="ux-icon-update1" >Update</a>
                                                                                                </c:when>
                                                                                                <c:when test="${permissionSet.contains(permissionConstants.VIEW_DOCUMENT_UPLOAD)}">
                                                                                                    <a href="javascript:void(0)"  onclick='viewPage("${fn:trim(serviceListIte.encString)}")'   title="View" class="ux-icon-view" >View</a>
                                                                                                </c:when>
                                                                                            </c:choose>
                                                                                        </c:otherwise>
                                                                                    </c:choose>
                                                                                </c:when>
                                                                                <c:otherwise>
                                                                                    <a href="javascript:void(0)"  onclick='viewPage("${fn:trim(serviceListIte.encString)}")'   title="View" class="ux-icon-view" >View</a>
                                                                                </c:otherwise>

                                                                            </c:choose>



                                                                        </c:if>


                                                                    </c:if>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <c:if test="${serCount!=0}">   </tr> </c:if>
                                                    <c:if test="${serCount==0 && engCount==0 && orgCount==0}">    </tr> </c:if>
                                                    <c:set var="orgCount" value="1"/>
                                                    <c:set var="serCount" value="1"/>
                                                    <c:choose>
                                                        <c:when test="${colorCount < 3}">
                                                            <c:set var="colorCount" value="${colorCount+1}"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:set var="colorCount" value="1"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                                <c:if test="${engCount !=0}">  </tr> </c:if>
                                            <c:set var="engCount" value="1"/>
                                        </c:forEach>                                           

                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="8" style="border-right-width: 1px !important;">
                                        <center >No matching records found</center>
                                    </td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
                            </spring:form>
            </div>
            <!-- content ends -->

            <!-- Begin: Footer -->
            <jsp:include page="../includes/footer.jsp"/>
            <!-- End: Footer -->
        </div>
        <!-- End: Wrapper -->


    </BODY></html>
