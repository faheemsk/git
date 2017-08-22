/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {
    //Left menu activation
    $("#rpm").addClass("ux-vnav-selected");
    //Left menu activation

    $('.fixme').fixheadertable({
        colratio: [, , , 80],
        zebra: false,
        //height: 220,
        whiteSpace: 'normal',
        resizeCol: false
    });

    $("#searchRemediationWorklist").click(function() {
        document.searchRemediationForm.action = "searchremediation.htm";
        document.searchRemediationForm.submit();
    });


});
function fnViewremediation(Viewremediationengkey)
{
    $("#Viewremediationengkey").val(Viewremediationengkey);
    document.searchRemediationForm.action = "viewremediation.htm";
    document.searchRemediationForm.submit();
}