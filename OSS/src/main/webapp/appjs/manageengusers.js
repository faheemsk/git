/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function() {
    
    $.fn.editeng = function(engCode) {
        document.engActionForm.action = "updateengagement.htm";
        document.engActionForm.cei.value = engCode;
        document.engActionForm.submit();
    }
    
    $.fn.mnguser = function(engCode) {
        document.engActionForm.action = "manageengagementusers.htm";
        document.engActionForm.cei.value = engCode;
        document.engActionForm.submit();
    }
    
    $.fn.vieweng = function(engCode) {
        //alert(engCode);
        document.engActionForm.action = "viewclientengagement.htm";
        document.engActionForm.cei.value = engCode;
        document.engActionForm.submit();
        //document.getElementById('engActionForm').submit();
    }
    
    $("#mu").removeClass("ux-vnav-selected");
    $("#mo").removeClass("ux-vnav-selected");
    $("#mrandp").addClass("ux-vnav-submenu-closed");
    $("#mrandp").removeClass("ux-vnav-has-selected ");
    $("#ua").removeClass("ux-vnav-selected");
    $("#emwl").addClass("ux-vnav-submenu-open");
    $("#emwl").addClass("ux-vnav-selected");
    $("#meu").addClass("ux-vnav-selected");

    $('.fixtable tr:even').addClass('ux-tabl-alt-row');
    $('.fixme tr:even').addClass('ux-tabl-alt-row');
    $(".datePicker").datepicker({
        dateFormat: 'mm/dd/yyyy',
         buttonImage: "<img class='ux-margin-left-min' title='Calendar' src='images/calendar01.gif' class='trigger'>",
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
        paging: true,
        info: false,
        "columnDefs": [
            {
                "targets": [ 7 ],
                "visible": false,
                "searchable": false
            }
        ],
        "order": [[ 7, "desc" ]],
        "aoColumns": [
            {"bSortable": true},
            {"bSortable": true},
            {"bSortable": true},
            {"bSortable": true},
            {"bSortable": true},
            {"bSortable": true},
            {"bSortable": false},
            {"bSortable": false}
        ]
    }).columnFilter({
        sPlaceHolder: "head:after",
        aoColumns: [
            {type: "text"},
            {type: "text"},
            {type: "text"},
            null,
            null,
            null,
            null,
            null
        ]
    });
    /*
     * For hiding global search from data table
     */
    $("#dataTable_filter").css({"display": "none"});
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
            //For clear the textbox data     
            document.getElementById("agreementDatePicker").value = "";
            document.getElementById("estimatedStartDatePicker").value = "";
            document.getElementById("estimatedEndDatePicker").value = "";
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

$.fn.dateFilter = function(obj, tableID) {
    var dateType = $(tableID).dataTable();
    var tableSettings = dateType.fnSettings();
    //For Agreement Date search
    var defaultActualPicker = $("#agreementDatePicker").val();
    tableSettings.aoPreSearchCols[ 3 ].sSearch = defaultActualPicker;
    //For Estimated Start Date search
    var defaultActualPicker = $("#estimatedStartDatePicker").val();
    tableSettings.aoPreSearchCols[ 4 ].sSearch = defaultActualPicker;
    //For Estimated End Date search
    var defaultActualPicker = $("#estimatedEndDatePicker").val();
    tableSettings.aoPreSearchCols[ 5 ].sSearch = defaultActualPicker;
    dateType.fnDraw();
}

function fnaddEngagement(addEngagement) {
    $("#addengagementFlag").val(addEngagement);
    document.engActionForm.action = "addengagement.htm";
    document.engActionForm.submit();
}
