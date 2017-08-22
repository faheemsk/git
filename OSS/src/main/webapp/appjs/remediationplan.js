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
    
    $("#notifyDateId").datepicker({
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
            {visible: false},
            {"bSortable": false},
            {"bSortable": true},
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
            null,
            {type: "text"},
            {type: "text"},
            {type: "select"},
            null,
            null,
            {type: "text"},
            {type: "text"},
            {type: "text"},
            null
        ]
    });
    
    /*For hiding global search from data table */
    $("#dataTable_filter").css({"display": "none"});
    
    $("#addPlan").click(function() {
        document.remediationPlan.action = "findinglookup.htm";
        document.remediationPlan.submit();
    });
    
    $("#removePlan").click(function() {
        document.remediationPlan.action = "removeplan.htm";
        document.remediationPlan.submit();
    });
    
});

$.fn.creationDateFilter = function(obj, tableID) {
    var dateType = $(tableID).dataTable();
    var tableSettings = dateType.fnSettings();
    var defaultActualPicker = $("#creationDateId").val();
    tableSettings.aoPreSearchCols[ 5 ].sSearch = defaultActualPicker;
    dateType.fnDraw();
};

$.fn.notifyDateFilter = function(obj, tableID) {
    var dateType = $(tableID).dataTable();
    var tableSettings = dateType.fnSettings();
    var defaultActualPicker = $("#notifyDateId").val();
    tableSettings.aoPreSearchCols[ 6 ].sSearch = defaultActualPicker;
    dateType.fnDraw();
};

$.fn.SearchFilterClearRemeList = function(obj, tableID) {
    $(tableID).dataTable().fnFilterClear("");
    document.getElementById("creationDateId").value = "";
    document.getElementById("notifyDateId").value = "";
    
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

function editRemediationPlan(planKey) {
    $("#planKeyId").val(planKey);
    document.remediationPlan.action = "editremediationpage.htm";
    document.remediationPlan.submit();
}

function checkAll(headCheckbox) {
    var checkboxes = document.getElementsByTagName('input');
    if (headCheckbox.checked) {
        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].type === 'checkbox') {
                checkboxes[i].checked = true;
            }
        }
    } else {
        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].type === 'checkbox') {
                checkboxes[i].checked = false;
            }
        }
    }
}