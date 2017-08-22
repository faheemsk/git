<%-- 
    Document   : engagementslandingpage
    Created on : Jun 24, 2016, 12:02:26 PM
    Author     : mrejeti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Index2</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
        <meta content="text/html;charset=utf-8" http-equiv="Content-Type" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

    </head>
    <body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
        <!-- Save for Web Slices (Index2.psd - Slices: 03, 09, 12, 17, Index2_07) -->
        <table id="Table_01"width="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td style="background-color:#666;width:100%; height:23px; text-align:right; border-bottom:1px solid #ccc;border-right:1px solid #ccc">
                    <img src="images/Index2_03.jpg" style="text-align:right"></td>
            </tr>
            <tr>
                <td style="background-image:url(<c:url value="images/Index2_07.jpg"/>); background-repeat:repeat-x; height:74px">
                    <table>
                        <tr><td align="left" valign="top" style="margin-top:10px">
                                <img src="images/Index2_12.jpg" title="Remarks on the Quantum-Gravity effects of Bean Pole diversification in Mononucleosis patients in Developing countries under Economic Conditions Prevalent during the Second half of the Twentieth Century, and Related Papers: a Summary. Remarks on the Quantum-Gravity effects of Bean Pole diversification in Mononucleosis patients in Developing countries under Economic Conditions Prevalent during the Second half of the Twentieth Century, and Related Papers: a Summary. Remarks on the Quantum-Gravity effects of Bean Pole diversification in Mononucleosis patients in Developing countries under Economic Conditions Prevalent during the Second half of the Twentieth Century, and Related Papers: a Summary. Remarks on the Quantum-Gravity effects of Bean Pole diversification in Mononucleosis patients in Developing countries under Economic Conditions Prevalent during the Second half of the Twentieth Century, and Related Papers: a Summary. Remarks on the Quantum-Gravity effects of Bean Pole diversification in Mononucleosis patients in Developing countries under Economic Conditions Prevalent during the Second half of the Twentieth Century, and Related Papers: a Summary. Remarks on the Quantum-Gravity effects of 1Bean Pole diversification in Mononucleosis patients in Developing countries under Economic Conditions Prevalent during the Second half of the Twentieth Century, and Related Papers: a Summary"></td>
                            <td style="width:100%"></td>
                            <td><img src="images/Index2_07-04.png"></td></tr>
                    </table>
                </td>

            </tr>
            <tr>  <td style="text-align:center">
                    <img src="images/Index2_17.jpg" border="0" usemap="#Map" style="text-align:center"/>
                </td></tr>

            <tr>  <td style="text-align:left; background-color:#fff;height:50px">
                    <img src="images/footer.png" style="text-align:left"/>
                </td></tr>
        </table>
        <!-- End Save for Web Slices -->

        <map name="Map">
            <c:forEach var="engagement" items="${engagementslist}"> sksks
                <area shape="rect" coords="72,141,293,160" href="<c:url value="/toengagementhomepage.htm?engCode=123"/>"> ${engagement.engagmentName}
            </c:forEach>
        </map>
    </body>
</html>
