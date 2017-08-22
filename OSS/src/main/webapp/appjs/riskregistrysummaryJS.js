/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(window).load(function() {
    $(".loader").fadeOut("slow");
});

$(document).ready(function() {

    $("#accordion").accordion({
        collapsible: true
    });

    $("#creationDateId").datepicker({
        dateFormat: 'mm/dd/yyyy',
        buttonImage: '<img class="ux-margin-left-min" title="Calendar" src="images/calendar01.gif" class="trigger">',
        onSelect: function(dates) {
            if ("createEvent" in document) {  //NON IE browsers
                var evt = document.createEvent("HTMLEvents");
                evt.initEvent("change", false, true);
                this.dispatchEvent(evt);
            }
            else {  //IE
                var evt = document.createEventObject();
                this.fireEvent("onchange", evt);
            }
        }
    });


    $('#dataTable').dataTable({
        ordering: true,
        paging: false,
        info: false,
        "aoColumns": [
            {"visible": false},
            {"bSortable": true},
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
            null,
            {type: "text"},
            {type: "text"},
            {type: "text"},
            {type: "text"},
            {type: "text"},
            null,
            {type: "text"},
            null
        ]
    });

    /*For hiding global search from data table */
    $("#dataTable_filter").css({"display": "none"});

});

$.fn.fnRiskRegistryDetails = function(riskRegistryID) {
    $("#riskRegisterID").val(riskRegistryID);
    document.riskRegistrySummary.action = "riskRegistryDetails.htm";
    document.riskRegistrySummary.submit();
};


$.fn.creationDateFilter = function(obj, tableID) {
    var dateType = $(tableID).dataTable();
    var tableSettings = dateType.fnSettings();
    var defaultActualPicker = $("#creationDateId").val();
    tableSettings.aoPreSearchCols[ 5 ].sSearch = defaultActualPicker;
    dateType.fnDraw();
};


$.fn.SearchFilterClearRegistryList = function(obj, tableID) {
    $(tableID).dataTable().fnFilterClear("");
    document.getElementById("creationDateId").value = "";

    var tds = $(obj).parent().parent().parent().children("td");
    $(tds).each(function() {
        var inputFeild = $(this).children().children("input").attr("type");
        if (typeof inputFeild !== "undefined") {
            var textObj = $(this).children().children("input");
            $(textObj).val("");
            $(textObj).trigger('blur');
        } else {
            inputFeild = $(this).children().children("select");
            if (typeof inputFeild !== "undefined") {
                $(inputFeild).val("");
            }
        }
    });
};