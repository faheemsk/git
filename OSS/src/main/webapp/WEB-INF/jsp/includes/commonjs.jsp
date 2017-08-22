<%-- 
    Document   : commonjs
    Created on : Apr 4, 2016, 12:26:09 PM
    Author     : hpasupuleti
--%>

<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>

<%--<script type="text/javascript" src="<c:url value="/js/jquery-1.8.2.min.js"/>"></script>--%>
<script type="text/javascript" src="<c:url value="/js/jquery-1.10.2.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery-ui-1.11.4.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/responsive-layout.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/ux.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery.fixheadertable.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery-migrate-1.0.0.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/popupbox.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery.plugin.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery.datepick.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery.treeTable.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery.tablesorter.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery.dataTables.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery.dataTables.columnFilter.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/fnFilterClear.js"/>"></script>

<%-- COMMON VALIDATION(s)--%>
<script type="text/javascript" src="<c:url value="/appjs/CommonUtils.js"/>"></script>
<%-- COMMON VALIDATION(s)--%>
<script>
    function preventBack() {
        window.history.forward();
    }
    setTimeout("preventBack()", 0);
</script>
