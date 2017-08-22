/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {
        
    //Left menu activation
    $("#mu").removeClass("ux-vnav-selected");
    $("#mo").removeClass("ux-vnav-selected");
    $("#mrandp").addClass("ux-vnav-submenu-closed");
    $("#mrandp").removeClass("ux-vnav-has-selected ");
    $("#ua").removeClass("ux-vnav-selected");
    $("#emwl").addClass("ux-vnav-submenu-closed");
    $("#emwl").removeClass("ux-vnav-selected");
    $("#meng").removeClass("ux-vnav-selected");
    $("#edu").removeClass("ux-vnav-selected");
    $("#avm").removeClass("ux-vnav-selected");
    $("#rpm").removeClass("ux-vnav-selected");
    $("#rmdml").addClass("ux-vnav-selected");
    //Left menu activation

    $('.fixme').fixheadertable({
        colratio: [, , , 80],
        zebra: false,
        //height: 220,
        whiteSpace: 'normal',
        resizeCol: false
    });

    $("#searchRemediationWorklist").click(function() {
        document.searchRemediationForm.action = "searchremediationhome.htm";
        document.searchRemediationForm.submit();
    });


});
function remediationPlan(Viewremediationengkey)
{
    $("#Viewremediationengkey").val(Viewremediationengkey);
    document.searchRemediationForm.action = "remediationplan.htm";
    document.searchRemediationForm.submit();
}


