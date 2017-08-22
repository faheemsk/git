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
    $("#avm").addClass("ux-vnav-selected");
    //Left menu activation
    
    $('.fixme').fixheadertable({
        //caption : 'My header is fixed !',
        //colratio: [120, 120, 100, 230, , , , 107, 60],
        zebra: false,
        //height: 500,
        whiteSpace: 'normal',
        resizeCol: false
    });

    $('.defaultActualPicker').datepicker({
        defaultDate: new Date(), buttonImage: ' <img class="ux-margin-left-min" src="images/calendar01.gif" alt="Popup" class="trigger" title="Calendar">'});

    /*
     * For hiding global search from data table
     */
    $("#dataTable_filter").css({"display": "none"});
    $("#removeSortingForCheckbox").removeClass("sorting_asc");

    $("#searchAnalystValidationWorklist").click(function() {
        document.searchAnalystValidationForm.action = "searchanalystvalidationworklist.htm";
        document.searchAnalystValidationForm.submit();
    });
    
    $("#searchRemediationWorklist").click(function() {
        document.searchRemediationForm.action = "searchremediation.htm";
        document.searchRemediationForm.submit();
    });
});

$.fn.SearchFilterClear = function(obj, tableID) {
    var dateType = $('#dataTable').dataTable();
    var tabObj = $(tableID).dataTable().fnFilterClear("");
    var tds = $(obj).parent().parent().parent().children("td");
    $(tds).each(function() {
        var inputFeild = $(this).children().children("input").attr("type");
        if (typeof inputFeild != "undefined") {
            var textObj = $(this).children().children("input");
            $(textObj).val("");
            var tableSettings = dateType.fnSettings();
            dateType.fnDraw();
            $(textObj).trigger('blur');
        }
        else {
            inputFeild = $(this).children().children("select");
            if (typeof inputFeild != "undefined") {
                $(inputFeild).val("select");
            }
        }
    });
}

function fnReviewFinding(enc,schema) {
    $("#cgenk").val(enc);
    $("#orgSchma").val(schema);
//    alert(schema);
    document.searchAnalystValidationForm.action = "reviewvulnerability.htm";
    document.searchAnalystValidationForm.submit();
}

function fnViewFinding (enc,schema) {
    $("#cgenk").val(enc);
    $("#orgSchma").val(schema);
    document.searchAnalystValidationForm.action = "viewvulnerability.htm";
    document.searchAnalystValidationForm.submit();
    
}
