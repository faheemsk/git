<%-- 
    Document   : VulnAcc
    Created on : Dec 27, 2016, 1:57:39 PM
    Author     : spatepuram
--%>

<%--<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
 </body>
</html>--%>
<%@taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
      <table class="" style="border-top:0px solid #000">

	<tbody >
        <c:if test="${sessionScope.VulnCat != null}">
        <c:set var="count" value="0"></c:set>
        <c:forEach items="${sessionScope.VulnCat}"  var="vuln">
             <c:if test="${count % 4 ==0}"><tr></c:if>
             <td  style="padding:7px; padding-top: 0px;vertical-align: top">${vuln.id}</td>
             <td style="vertical-align: top">${vuln.name}</td>
            <c:set var="count" value="${count+1}"></c:set>
        </c:forEach> </c:if>
<!--    	<tr>
        	<td style="padding:7px">1</td>
            <td>Data Loss222</td>
            <td>2</td>
            <td>Asset Ownership</td>
            <td>3</td>
            <td>Denial of Service</td>
            <td>4</td>
            <td>Malware</td>
        </tr>
        <tr>
        	<td style="padding:7px">5</td>
            <td>Data Centre</td>
            <td>6</td>
            <td>M&A-Post Integration Maintenance</td>
            <td>7</td>
            <td>Session Hijacking</td>
            <td>8</td>
            <td>Security Path Management</td>
        </tr>
        <tr>
        	<td style="padding:7px">9</td>
            <td>SQL Injection</td>
            <td>10</td>
            <td>Cross Site-Scripting</td>
            <td>11</td>
            <td>Unsecure Cookie Use</td>
            <td>12</td>
            <td>Insecure Interfaces and APIs</td>
        </tr>-->
    </tbody>

</table>
   
