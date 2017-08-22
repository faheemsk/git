/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



$(document).ready(function() {
    //                    $('tr:odd').addClass('ux-tabl-alt-row');
    $("#ua").addClass("ux-vnav-selected");
    $("#mu").removeClass("ux-vnav-selected");
    $("#mo").removeClass("ux-vnav-selected");
    $("#mrandp").addClass("ux-vnav-submenu-closed");
    $("#mrandp").removeClass("ux-vnav-has-selected ");
    $('tr:odd').addClass('ux-tabl-alt-row');

    $('#dataTable').dataTable({
        ordering: true,
        paging: true,
        info: false,
        "aoColumns": [
            {"bSortable": true},
            {"bSortable": true},
            {"bSortable": true},
            {"bSortable": true},
            {"bSortable": true},
            {"bSortable": true},
            {"bSortable": false}

        ]
    }).columnFilter({
        sPlaceHolder: "head:after",
        aoColumns: [
            {type: "select"},
            {type: "text"},
            {type: "text"},
            {type: "text"},
            {type: "text"},
            {type: "select"},
            null
        ]

    });

    $("#dataTable_filter").css({"display": "none"});
});
$.fn.SearchFilterClear = function(obj, tableID) {

    var tabObj = $(tableID).dataTable().fnFilterClear("");
    var tds = $(obj).parent().parent().parent().children("td");
    $(tds).each(function() {
        var inputFeild = $(this).children().children("input").attr("type");
        if (typeof inputFeild != "undefined") {
            var textObj = $(this).children().children("input");
            $(textObj).val("");
            $(textObj).trigger('blur');
        }
        else {
            inputFeild = $(this).children().children("select");
            if (typeof inputFeild != "undefined") {
                $(inputFeild).val("select");
            }
        }
    });
};
var upKey = '';
var sKey = '';
function changeStatusPopup(key) {
    if (document.getElementById("rowStatusValue" + key).value === "Deactive")
    {
        $("#Deactive").show();
        $("#unlock").hide();
    }
    if (document.getElementById("rowStatusValue" + key).value === "Locked")
    {
        $("#Deactive").hide();
        $("#unlock").show();
    }
    upKey = document.getElementById("encUserProfileKey" + key).value;
    sKey = document.getElementById("encRowStatusValue" + key).value;
    popup(1);
}
$.fn.changeStatus = function() {
    document.changestatusform.action = "changeuserstatus.htm?upKey=" + upKey + "&sKey=" + sKey;
    document.changestatusform.submit();
};

