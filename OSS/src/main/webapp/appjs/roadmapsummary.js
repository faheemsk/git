/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(window).load(function() {
    $(".loader").fadeOut("slow");
});

$(document).ready(function(){
    
    $("#accordion").accordion({
        collapsible: true
    });
    
    $("#effectiveDateId").datepicker({
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
    
    $("#pubDateId").datepicker({
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
        ordering: false,
        paging: false,
        info: false,
        "aoColumns": [
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
            {type: "text"},
            {type: "text"},
            {type: "select"},
            {type: "select"},
            {type: "select"},
            null,
            null,
            null
        ]
    });
    
    /*For hiding global search from data table */
    $("#dataTable_filter").css({"display": "none"});
    
});

$.fn.effectiveDateFilter = function(obj, tableID) {
    var dateType = $(tableID).dataTable();
    var tableSettings = dateType.fnSettings();
    var defaultActualPicker = $("#effectiveDateId").val();
    tableSettings.aoPreSearchCols[ 5 ].sSearch = defaultActualPicker;
    dateType.fnDraw();
};

$.fn.pubDateFilter = function(obj, tableID) {
    var dateType = $(tableID).dataTable();
    var tableSettings = dateType.fnSettings();
    var defaultActualPicker = $("#pubDateId").val();
    tableSettings.aoPreSearchCols[ 6 ].sSearch = defaultActualPicker;
    dateType.fnDraw();
};

function fnEditRoadmap(enc,schema,csacode,catcode) {
    $("#cgenk").val(enc);
    $("#orgSchma").val(schema);
    $("#csacode").val(csacode);
    $("#catcode").val(catcode);
    document.editRoadmap.action = "editroadmap.htm";
    document.editRoadmap.submit();
}

function fnBackToFindings() {
    document.editRoadmap.action = "reviewvulnerability.htm";
    document.editRoadmap.submit();
}

$.fn.SearchFilterClearRoadmap = function(obj, tableID) {
    $(tableID).dataTable().fnFilterClear("");
    document.getElementById("effectiveDateId").value = "";
    document.getElementById("pubDateId").value = "";
    
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
}