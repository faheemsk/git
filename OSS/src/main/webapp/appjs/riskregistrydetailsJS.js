/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(window).load(function() {
    $(".loader").fadeOut("slow");
});

$(document).ready(function() {

    $("#detectionDateID").datepicker({
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

    $("#closureDateID").datepicker({
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
        paging: true,
        info: false,
        "aoColumns": [
            {"bSortable": false},
            {"bSortable": true},
            {"bSortable": true},
           // {"bSortable": true},
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
           // {type: "text"},
            {type: "text"},
            {type: "text"},
            {type: "text"},
            null,
            null,
            {type: "text"},
            null
        ]
    });

    /*For hiding global search from data table */
    $("#dataTable_filter").css({"display": "none"});


    $("#removeRegistryItems").click(function() {
        var flag = false;
        var checkboxes = new Array();
        $('.checkboxGroup').each(function() {
            if (this.checked) {
                var id = $(this).attr('id');
                checkboxes.push(id);
                flag = true;
                $("#errorCheckboxValidation").html("");
            }
        });
        if (!flag) {
            $("#errorCheckboxValidation").html("<font class='ux-msg-error-under' style='margin-top: -16px;' color='red'>Please select at least one Finding</font>");
            return false;
        } else {
            document.riskRegistryForm.action = "removeRegistryItems.htm";
            document.riskRegistryForm.submit();
        }
    });



    $(".addFinding").click(function() {
        document.riskRegistryForm.action = "findinglookup.htm";
        document.riskRegistryForm.submit();
    });

//    
//    $("#saveRegistry").click(function() {
//        document.addRemediationFrm.action = "addremediationplan.htm";
//        document.addRemediationFrm.submit();
//    });

    $("#riskRegistryOwnerID").change(function() {
        var dbownerid = $("#dbRiskOwnerId").val();
        var remonrid = $("#riskRegistryOwnerID").val();
        //alert($("#dbOwnerId").val()+"-"+$("#remOwnerID").val());
        
        if(remonrid != 0){
            if(dbownerid !== remonrid){
                $("#NotifyButtonID").removeClass("ux-btn-disabled");
                $("#NotifyButtonID").addClass("ux-btn-default-action");
                $("#NotifyButtonID").attr("value", "Save & Notify");
                $("#NotifyButtonID").removeAttr("disabled");
                $("#saveRegistry").attr("type","hidden");
            }else{
                $("#NotifyButtonID").removeClass("ux-btn-default-action");
                $("#NotifyButtonID").addClass("ux-btn-disabled");
                $("#NotifyButtonID").attr("value", "Notify");
                $("#NotifyButtonID").attr("disabled", "true");
                $("#saveRegistry").attr("type","button");
            }
        }else{
            $("#NotifyButtonID").removeClass("ux-btn-default-action");
            $("#NotifyButtonID").addClass("ux-btn-disabled");
            $("#NotifyButtonID").attr("disabled", "true");
            $("#NotifyButtonID").attr("value", "Notify");
            $("#saveRegistry").attr("type","button");
        }
        
    });


});
$.fn.detectionDateFilter = function(obj, tableID) {
    var dateType = $(tableID).dataTable();
    var tableSettings = dateType.fnSettings();
    var defaultActualPicker = $("#detectionDateID").val();
    tableSettings.aoPreSearchCols[ 7 ].sSearch = defaultActualPicker;
    dateType.fnDraw();
};
$.fn.colsureDateFilter = function(obj, tableID) {
    var dateType = $(tableID).dataTable();
    var tableSettings = dateType.fnSettings();
    var defaultActualPicker = $("#closureDateID").val();
    tableSettings.aoPreSearchCols[ 8 ].sSearch = defaultActualPicker;
    dateType.fnDraw();
};



$.fn.SearchFilterFindingList = function(obj, tableID) {
    $(tableID).dataTable().fnFilterClear("");
    document.getElementById("detectionDateID").value = "";
    document.getElementById("closureDateID").value = "";
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

function backToRiskRegistrySummary() {
    document.riskRegistryForm.action = "riskRegistrySummary.htm";
    document.riskRegistryForm.submit();
}
function registryNotifyUPdate() {
    if ($.fn.riskRegistryNitify()) {
        document.riskRegistryForm.action = "updRegNotify.htm";
        document.riskRegistryForm.submit();
    }
}

function updateRiskRegistryDetails() {
    if ($.fn.riskRegistryUpdate()) {
        document.riskRegistryForm.action = "updateRiskRegistryDetails.htm";
        document.riskRegistryForm.submit();
    }
}
function riskAcceptanceDetailsUpdate() {
    if ($.fn.riskAcceptanceUpdate()) {
        document.riskRegistryForm.action = "updateriskAcceptanceDetails.htm";
        document.riskRegistryForm.submit();
    }
}

function checkAll(headCheckbox) {
    var checkboxes = document.getElementsByClassName('checkboxGroup');
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

function singleCheckbox(rowCheckbox) {
    var checkboxResult = [];
    var selectedCheckboxes = [];
    var headCheckbox = document.getElementById('headCheckbox');
    $('.checkboxGroup').each(function() {
        if (this.checked) {
            var id = $(this).attr('id');
            selectedCheckboxes.push(id);
        }
    });
    $('.checkboxGroup').each(function() {
        var id = $(this).attr('id');
        checkboxResult.push(id);
    });

    if (headCheckbox.checked && !rowCheckbox.checked) {
        headCheckbox.checked = false;
    }
    else if (selectedCheckboxes.length === checkboxResult.length && !headCheckbox.checked) {
        headCheckbox.checked = true;
    }
}

$.fn.riskRegistryUpdate = function() {
    var flagValidTotal = new Array();
    var returnstring = false;

    var adjustedSeverityID = $("#adjustedSeverityID").val();
    var compensationControlID = $.trim($("#compensationControlID").val());
    var riskRegistryOwner = $.trim($("#riskRegistryOwnerID").val());
//
//    if (riskRegistryOwner == 0) {
//        $("#errorriskRegistryOwnerID").html("Please select Risk Register Owner");
//        $("#riskRegistryOwnerID").addClass("ux-form-field-error");
//        flagValidTotal.push(false);
//    } else {
//        flagValidTotal.push(true);
//        $("#errorriskRegistryOwnerID").html("");
//        $("#riskRegistryOwnerID").removeClass("ux-form-field-error");
//    }

//    if (adjustedSeverityID == '') {
//        $("#erroradjustedSeverityID").html("Please select Adjusted Severity");
//        $("#adjustedSeverityID").addClass("ux-form-field-error");
//        flagValidTotal.push(false);
//    } else {
//        flagValidTotal.push(true);
//        $("#erroradjustedSeverityID").html("");
//        $("#adjustedSeverityID").removeClass("ux-form-field-error");
//    }

    if (compensationControlID == '') {
        $("#errorcompensationControlID").html("Please enter Compensating Control");
        $("#compensationControlID").addClass("ux-form-field-error");
        flagValidTotal.push(false);
    } else if (compensationControlID.length > 1000) {
        $("#errorcompensationControlID").html("Compensating Control cannot exceed 1000 characters");
        $("#compensationControlID").addClass("ux-form-field-error");
        flagValidTotal.push(false);
    } else {
        flagValidTotal.push(true);
        $("#errorcompensationControlID").html("");
        $("#compensationControlID").removeClass("ux-form-field-error");
    }
    for (var i = 0; i < flagValidTotal.length; i++) {
        if (flagValidTotal[i] == false) {
            returnstring = flagValidTotal[i];
            flagValidTotal = new Array();
            break;
        } else {
            returnstring = true;
        }
    }
    return returnstring;

}


$.fn.riskAcceptanceUpdate = function() {
    var flagValidTotal = new Array();
    var returnstring = false;

    var riskResponseCode = $("#riskResponseCode").val();
    var justificationText = $.trim($("#riskResponseJustificationID").val());
    var acceptedBy = $.trim($("#acceptedByID").val());
    if (riskResponseCode == '') {
        $("#errorriskResponseCodeID").html("Please select Risk Response");
        $("#riskResponseCode").addClass("ux-form-field-error");
        flagValidTotal.push(false);
    } else {
        flagValidTotal.push(true);
        $("#errorriskResponseCodeID").html("");
        $("#riskResponseCode").removeClass("ux-form-field-error");
    }

 if (justificationText == '') {
        $("#errorRiskResponseJustificationID").html("Please enter Risk Response Justification");
        $("#riskResponseJustificationID").addClass("ux-form-field-error");
        flagValidTotal.push(false);
    }else if (justificationText.length > 1000) {
        $("#errorRiskResponseJustificationID").html("Risk Response Justification cannot exceed 1000 characters");
        $("#riskResponseJustificationID").addClass("ux-form-field-error");
        flagValidTotal.push(false);
    } else {
        flagValidTotal.push(true);
        $("#errorRiskResponseJustificationID").html("");
        $("#riskResponseJustificationID").removeClass("ux-form-field-error");
    }

    if (acceptedBy.length > 100) {
        $("#errorAcceptedByID").html("Accepted By cannot exceed 100 characters");
        $("#acceptedByID").addClass("ux-form-field-error");
        flagValidTotal.push(false);
    } else {
        flagValidTotal.push(true);
        $("#errorAcceptedByID").html("");
        $("#acceptedByID").removeClass("ux-form-field-error");
    }

    for (var i = 0; i < flagValidTotal.length; i++) {
        if (flagValidTotal[i] == false) {
            returnstring = flagValidTotal[i];
            flagValidTotal = new Array();
            break;
        } else {
            returnstring = true;
        }
    }
    return returnstring;

}



$.fn.riskRegistryNitify = function() {
    var flagValidTotal = new Array();
    var returnstring = false;

    var riskRegistryOwner = $.trim($("#riskRegistryOwnerID").val());

    if (riskRegistryOwner == 0) {
        $("#errorriskRegistryOwnerID").html("Please select Risk Register Owner");
        $("#riskRegistryOwnerID").addClass("ux-form-field-error");
        flagValidTotal.push(false);
    } else {
        flagValidTotal.push(true);
        $("#errorriskRegistryOwnerID").html("");
        $("#riskRegistryOwnerID").removeClass("ux-form-field-error");
    }

    for (var i = 0; i < flagValidTotal.length; i++) {
        if (flagValidTotal[i] == false) {
            returnstring = flagValidTotal[i];
            flagValidTotal = new Array();
            break;
        } else {
            returnstring = true;
        }
    }
    return returnstring;

}
//function fnShowNotify() {
//alert("YES");
//    var onloadValue = $.trim($("#onloadRiskRegisterID").val());
//    var riskRegistryOwner = $.trim($("#riskRegistryOwnerID").val());
//    if (onloadValue === riskRegistryOwner) {
//        alert("equal");
//        $("#NotifyButtonID").css({"display": "none"});
//    } else {
//        alert("not equal");
//        $("#NotifyButtonID").css({"display": "block"});
//    }
//}

function riskRegistryFinding(findId){
    $("#findingID").val(findId);
    document.riskRegistryForm.action = "riskRegistryFindDetails.htm";
    document.riskRegistryForm.submit();
}

function backToRiskRegitryDet(){
    document.riskRegistryForm.action = "riskRegistryDetails.htm";
    document.riskRegistryForm.submit();
}

function updateRiskRegistryFinding(){
    if ($.fn.riskRegistryFindingUpdate()) {
        document.riskRegistryForm.action = "updateRiskRegistryFinding.htm";
        document.riskRegistryForm.submit();
    }
}

$.fn.riskRegistryFindingUpdate = function() {
    var flagValidTotal = new Array();
    var returnstring = false;

    var compensationControlID = $.trim($("#compCrtlId").val());

    if ($.trim($('select#adjSeverityId option:selected').val()) != 0) {
        flagValidTotal.push(true);
        $("#adjSeverityId").removeClass("ux-form-field-error");
        $("#adjSevErrId").html("");
    } else {
        $("#adjSeverityId").addClass("ux-form-field-error");
        $("#adjSevErrId").html("Please select Adjusted Severity");
        flagValidTotal.push(false);
    }
    
    if (compensationControlID == '') {
        $("#compCtrlErrId").html("Please enter Compensating Control");
        $("#compCrtlId").addClass("ux-form-field-error");
        flagValidTotal.push(false);
    } else if (compensationControlID.length > 1000) {
        $("#compCtrlErrId").html("Compensating Control cannot exceed 1000 characters");
        $("#compCrtlId").addClass("ux-form-field-error");
        flagValidTotal.push(false);
    } else {
        flagValidTotal.push(true);
        $("#compCtrlErrId").html("");
        $("#compCrtlId").removeClass("ux-form-field-error");
    }
    for (var i = 0; i < flagValidTotal.length; i++) {
        if (flagValidTotal[i] == false) {
            returnstring = flagValidTotal[i];
            flagValidTotal = new Array();
            break;
        } else {
            returnstring = true;
        }
    }
    return returnstring;

}
