/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




$(document).ready(function() {
    $('.fixme').fixheadertable({
//caption : 'My header is fixed !',
        //colratio: [150,, 150, 150, , 100, 135, 80],
        zebra: false,
        //height:300,
        whiteSpace: 'normal',
        resizeCol: false,
    });
     $("#cru").addClass("ux-vnav-selected");

});

$(document).ready(function() {
    $("#review11").hide();
    $("#chk2").click(function()
    {
        if ($("#chk2").is(":checked"))
        {
            $("#review2").hide();
            $("#review11").show();
            $("#review1").hide();
        }
        else {
            $("#review2").show();
            $("#review11").hide();
            $("#review1").show();
        }
    });
    $("#chk3").click(function()
    {
        if ($("#chk3").is(":checked"))
        {
            $("#review3").hide();
        }
        else {
            $("#review3").show();
        }
    });
    $("#review").hide();
    $("#showreview").click(function()
    {
        $("#review").show();

    });

    $("#searchclientreportuploadworklist").click(function() {
        document.searchclientreportuploadworklistForm.action = "searchclientreportuploadworklist.htm";
        document.searchclientreportuploadworklistForm.submit();
    });
    
    $('.defaultActualPicker').datepicker({
        defaultDate: new Date(), buttonImage: ' <img class="ux-margin-left-min" src="images/calendar01.gif" alt="Popup" class="trigger" title="Calendar">'});
});

function fnAddReport(ec) {
    $("#ec").val(ec);
    document.searchclientreportuploadworklistForm.action = "addReports.htm";
    document.searchclientreportuploadworklistForm.submit();
}
function fnEditReport(ec) {
    $("#ec").val(ec);
    document.searchclientreportuploadworklistForm.action = "editReports.htm";
    document.searchclientreportuploadworklistForm.submit();
}