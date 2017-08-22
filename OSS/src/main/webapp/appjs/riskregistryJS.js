/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function() {
     $("#riskreg").addClass("ux-vnav-selected");
    $('.fixme').fixheadertable({
        colratio: [, , , 80],
        zebra: false,
        //height: 220,
        whiteSpace: 'normal',
        resizeCol: false
    });

    $("#searchRiskRegistryList").click(function() {
        document.searchRegistryList.action = "searchRegistryList.htm";
        document.searchRegistryList.submit();
    });
    
    
    
});


function riskRegistrySummary(engKey)
{
    $("#riskEngKey").val(engKey);
    document.searchRegistryList.action = "riskRegistrySummary.htm";
    document.searchRegistryList.submit();
}