<%-- 
    Document   : commonIncludes
    Created on : Jul 14, 2016, 11:28:48 AM
    Author     : hpasupuleti
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<script type="text/javascript" src="<c:url value="/js/jquery.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/angular.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/angular-translate.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/lodash.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/uitk-consumer-configs.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/uitk-utility.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/uitk-navigable.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/ng-csv.js"/>"></script>
<script src="<c:url value="/js/api-main-attributes-list.js"/>"></script>
<!--[if lte IE 9]>
<script async src="js/console-polyfill.js"></script>
<![endif]-->
<!-- icons now in uitk-defaults.css -->
<!--<link rel="stylesheet" href="css/uitk.min.css">-->
<link rel="icon" href="images/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon" />
<script type="text/javascript" src="<c:url value="/js/angular-resource.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/shCore.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/shBrushXml.js"/>"></script>
<link type="text/css" rel="stylesheet" href="<c:url value="/css/shCore.css"/>" />
<link type="text/css" rel="stylesheet" href="<c:url value="/css/shThemeDefault.css"/>" />
<link type="text/css" rel="stylesheet"  href="<c:url value="/css/uitk.min.css"/>">
<script type="text/javascript">
    SyntaxHighlighter.all();
</script>


<script type="text/javascript" src="<c:url value="/js/uitk.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/uitk_api.js"/>"></script>

<script type="text/javascript" src="<c:url value="/js/fusioncharts-new.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/fusioncharts.charts.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/angular-fusioncharts.min.js"/>"></script>
 <style>
            #loading {
   width: 100%;
   height: 100%;
   top: 0px;
   left: 0px;
   position: fixed;
   display: block;
   opacity: 0.7;
   background-color: #ccc;
   z-index: 99;
   text-align: center;
}

#loading-content {
  position: absolute;
  top: 45%;
  left: 45%;
  text-align: center;
  z-index: 100;
}
           #downloading {
   width: 100%;
   height: 100%;
   top: 0px;
   left: 0px;
   position: fixed;
   display: block;
   opacity: 0.7;
   background-color: #ccc;
   z-index: 99;
   text-align: center;
}

#downloading-content {
  position: absolute;
  top: 45%;
  left: 45%;
  text-align: center;
  z-index: 100;
}

/*.hide{
  display: none;
}
 .loader {
	position: fixed;
	left: 0px;
	top: 0px;
	width: 100%;
	height: 100%;
	z-index: 9999;
	background: url('../images/loader.gif') 50% 50% no-repeat rgb(249,249,249);
}*/
        </style>