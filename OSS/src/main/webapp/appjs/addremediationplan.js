/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(window).load(function() {
    $(".loader").fadeOut("slow");
});

$(document).ready(function(){
    
    $('#dataTable').dataTable({
        ordering: false,
        paging: false,
        info: false,
        "aoColumns": [
            {"bSortable": false},
            {"bSortable": true},
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
            {type: "text"},
            {type: "text"},
            {type: "text"},
            {type: "text"},
            {type: "text"},
            {type: "text"},
            {type: "text"},
            {type: "text"},
            {type: "text"},
            null
        ]
    });
    
    /*For hiding global search from data table */
    $("#dataTable_filter").css({"display": "none"});
    
    $("#addFinding").click(function() {
        document.addRemediationFrm.action = "findinglookup.htm";
        document.addRemediationFrm.submit();
    });
    
//    $("#removeFinding").click(function() {
//        document.addRemediationFrm.action = "addremediationpage.htm";
//        document.addRemediationFrm.submit();
//    });
//    
    $("#savePlan").click(function() {
        if(saveRemediationPlan()){
            document.addRemediationFrm.action = "addremediationplan.htm";
            document.addRemediationFrm.submit();
        }
    });
    
    $("#startDateId").datepicker({
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
    
    $("#endDateId").datepicker({
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
    
    $("#addNote").click(function() {
        if($("#planNotes").val()!== ''){
            var today = new Date();
            var month = today.getMonth()+1;
            var day = today.getDate();

            var now = (month<10?'0':'')+month+'/'+(day<10?'0':'')+day+'/'+today.getFullYear();
            $("#addedNotes").prepend("<option>"+now+" - "+$("#planNotes").val()+"</option>");
            $("#hiddenNotes").append("<option selected>"+now+" - "+$("#planNotes").val()+"</option>");
            $("#planNotes").val("");
            $("#planNotes").removeClass("ux-form-field-error");
            $("#planNotesID").html("");
        }else{
            $("#planNotes").addClass("ux-form-field-error");
            $("#planNotesID").html("Please enter Plan Notes");
        }
    });
    
       $("#removeFinding").click(function() {
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
            document.addRemediationFrm.action = "addremediationpage.htm";
         document.addRemediationFrm.submit();
        }
    });
});

$.fn.SearchFilterFindingList = function(obj, tableID) {
    $(tableID).dataTable().fnFilterClear("");
   
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

function cancelLookup(){
    document.addRemediationFrm.action = "remediationplan.htm";
    document.addRemediationFrm.submit();
}

//Save Remediation Plan
var flagValidTotal = new Array();
var returnstring = false;
function saveRemediationPlan() {
    flagValidTotal = new Array();
    if ($.trim($('#pTitleID').val()) != '') {
        flagValidTotal.push(true);
        $("#pTitleID").removeClass("ux-form-field-error");
        $("#planTitleID").html("");
    } else {
        $("#pTitleID").addClass("ux-form-field-error");
        $("#planTitleID").html("Please enter Plan Title");
        flagValidTotal.push(false);
    }
    
    if ($.trim($('#planDetailsId').val()) != '') {
        //Closed comments length  validation checking
        if (jQuery.trim($('#planDetailsId').val()).length > 1000) {
            $("#planDetailsId").addClass("ux-form-field-error");
            $("#errorPlanDetailsId").html("Action Plan Details cannot exceed 1000 characters");
            flagArray.push(false);
        }else{
            flagValidTotal.push(true);
            $("#planDetailsId").removeClass("ux-form-field-error");
            $("#errorPlanDetailsId").html("");
        }
    } else {
        $("#planDetailsId").addClass("ux-form-field-error");
        $("#errorPlanDetailsId").html("Please enter Action Plan Details");
        flagValidTotal.push(false);
    }
    
//    if ($.trim($('select#planSeverityId option:selected').val()) != 0) {
//        flagValidTotal.push(true);
//        $("#planSeverityId").removeClass("ux-form-field-error");
//        $("#planSeverityErrId").html("");
//    } else {
//        $("#planSeverityId").addClass("ux-form-field-error");
//        $("#planSeverityErrId").html("Please select Adjusted Severity");
//        flagValidTotal.push(false);
//    }
    
    var strtdat = new Date($("#startDateId").val());
    var endate = new Date($("#endDateId").val());
    var today = new Date();
    
    if($("#startDateId").val() !== ""){
        if (endate >= strtdat) {
            flagValidTotal.push(true);
            $("#endDateId").removeClass("ux-form-field-error");
            $("#errorEndDateID").html("");
        } else {
            $("#endDateId").addClass("ux-form-field-error");
            $("#errorEndDateID").html("End Date cannot be earlier than Start Date");
            flagValidTotal.push(false);
        }
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