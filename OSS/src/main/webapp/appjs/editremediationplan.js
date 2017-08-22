/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(window).load(function() {
    $(".loader").fadeOut("slow");
});

$(document).ready(function(){
//    
//     document.getElementById('blackcover3').style.visibility = 'hidden';
//    document.getElementById('popupbox3').style.visibility = 'hidden';
//    document.getElementById('blackcover3').style.display = 'none';
//    document.getElementById('popupbox3').style.display = 'none';
    
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
            {type: "select"},
            {type: "select"},
            {type: "text"},
            {type: "text"},
            null,
            null,
            {type: "text"},
            null
        ]
    });
    
    $("#detectionDateId").datepicker({
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
    
    $("#closureDateId").datepicker({
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
    
    /*For hiding global search from data table */
    $("#dataTable_filter").css({"display": "none"});
    
    $("#addFinding").click(function() {
        document.editRemediationFrm.action = "findinglookup.htm";
        document.editRemediationFrm.submit();
    });
    
    $("#removeFinding").click(function() {
        document.addRemediationFrm.action = "addremediationpage.htm";
        document.addRemediationFrm.submit();
    });
    
    $("#savePlan").click(function() {
        if(updateRemediationPlan()){
            document.editRemediationFrm.action = "updateremediationplan.htm";
            document.editRemediationFrm.submit();
        }
    });
    
    $("#savePlanNts").click(function() {
        document.editRemediationFrm.action = "updateremediationplan.htm";
        document.editRemediationFrm.submit();
    });
    
    $("#startDateId").datepicker({
        minDate:0,
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
//            $("#endDateId").datepicker({
//                minDate:$("#startDateId").val()
//            });
        }
    });
    
    $("#endDateId").datepicker({
        minDate:0,
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
            var notename = $("#notename").val();

            var now = (month<10?'0':'')+month+'/'+(day<10?'0':'')+day+'/'+today.getFullYear();
            $("#addedNotes").prepend("<option>"+now+" - "+notename+" - "+$("#planNotes").val()+"</option>");
            $("#hiddenNotes").append("<option selected>"+now+" - "+$("#planNotes").val()+"</option>");
            $("#planNotes").val("");
            
            $("#planNotes").removeClass("ux-form-field-error");
            $("#planNotesID").html("");
        }else{
            $("#planNotes").addClass("ux-form-field-error");
            $("#planNotesID").html("Please enter Plan Notes");
        }
    });
    
    $("#delPlanFinding").click(function() {
        var count = $("[name='removeInstances']:checked").length;
        var size = $("#totalFindCount").val();
        if (count < size) {
            document.editRemediationFrm.action = "deleteplanfinding.htm";
            document.editRemediationFrm.submit();
        } else {
            $("#findSelection").html("");
            $("#findSelection").html("Remediation Plan should contain atleast one Association Finding");
        }
    });
    
    $("#saveRisk").click(function() {
        if(saveRiskValidate()){
            document.editRemediationFrm.action = "statusrisk.htm";
            document.editRemediationFrm.submit();
        }
    });
    
    $("#saveInProgress").click(function() {
        document.editRemediationFrm.action = "updateplanitemstatus.htm";
        document.editRemediationFrm.submit();
    });
    
     $("#saveOpen").click(function() {
        document.editRemediationFrm.action = "updateplanitemstatus.htm";
        document.editRemediationFrm.submit();
    });
    
    $("#saveClosed").click(function() {
        if(saveClosedValidate()){
            document.editRemediationFrm.action = "updateplanitemstatus.htm";
            document.editRemediationFrm.submit();
        }
    });
    
    $("#notify").click(function() {
        if(notifyValidation()){
            document.editRemediationFrm.action = "updatenotifydate.htm";
            document.editRemediationFrm.submit();
        }
    });
    
     $("#remOwnerID").change(function() {
        var dbownerid = $("#dbOwnerId").val();
        var remonrid = $("#remOwnerID").val();
        //alert($("#dbOwnerId").val()+"-"+$("#remOwnerID").val());
        
        if(remonrid != 0){
            if(dbownerid !== remonrid){
                $("#notify").removeClass("ux-btn-disabled");
                $("#notify").addClass("ux-btn-default-action");
                $("#notify").attr("value", "Save & Notify");
                $("#notify").removeAttr("disabled");
                $("#savePlan").attr("type","hidden");
            }else{
                $("#notify").removeClass("ux-btn-default-action");
                $("#notify").addClass("ux-btn-disabled");
                $("#notify").attr("value", "Notify");
                $("#notify").attr("disabled", "true");
                $("#savePlan").attr("type","button");
            }
        }else{
            $("#notify").removeClass("ux-btn-default-action");
            $("#notify").addClass("ux-btn-disabled");
            $("#notify").attr("disabled", "true");
            $("#notify").attr("value", "Notify");
            $("#savePlan").attr("type","button");
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

$.fn.detectionDateFilter = function(obj, tableID) {
    var dateType = $(tableID).dataTable();
    var tableSettings = dateType.fnSettings();
    var defaultActualPicker = $("#detectionDateId").val();
    tableSettings.aoPreSearchCols[ 7 ].sSearch = defaultActualPicker;
    dateType.fnDraw();
};

$.fn.closureDateFilter = function(obj, tableID) {
    var dateType = $(tableID).dataTable();
    var tableSettings = dateType.fnSettings();
    var defaultActualPicker = $("#closureDateId").val();
    tableSettings.aoPreSearchCols[ 8 ].sSearch = defaultActualPicker;
    dateType.fnDraw();
};

function toRemediationPlan(){
    document.editRemediationFrm.action = "remediationplan.htm";
    document.editRemediationFrm.submit();
}
function toRemediationHome(){
    document.editRemediationFrm.action = "remediationhome.htm";
    document.editRemediationFrm.submit();
}

function editAssocFinding(findId){
    $(".loader").show();
    $("#findingId").val(findId);
    document.editRemediationFrm.action = "editassocfinding.htm";
    document.editRemediationFrm.submit();
}

function MM_jumpMenu(obj){ 
    var count = $("[name='removeInstances']:checked").length;
    
    if(count>0){
        $("#findSelection").text("");
        popup($("select[name='statusCode'] option:selected").val());
        $("#statCdId").val($("select[name='statusCode'] option:selected").val());
    }else{
        $("#statCdId").val("0");
        $("#findSelection").text("Please select atleast one finding");
    }
}

/* Start:Popup box */
function popup(sw) {
    if(sw == 0){
        //$("#statCdId").val("0");
    }
    
    if (sw == 'RI') {
        // Show popup
        document.getElementById('blackcover').style.visibility = 'visible';
        document.getElementById('popupbox').style.visibility = 'visible';
        document.getElementById('blackcover').style.display = 'block';
        document.getElementById('popupbox').style.display = 'block';
        $("#inProgressCnt").text($("[name='removeInstances']:checked").length);
    } else {
        // Hide popup
        document.getElementById('blackcover').style.visibility = 'hidden';
        document.getElementById('popupbox').style.visibility = 'hidden';
        document.getElementById('blackcover').style.display = 'none';
        document.getElementById('popupbox').style.display = 'block';
    }
    if (sw == 'RR') {
        $('select#adjSeverityId option:selected').val("0");
        $('#compCrtlId').val('');
        $("#adjSeverityId").removeClass("ux-form-field-error");
        $("#adjSevErrId").html("");
        // Show popup
        document.getElementById('blackcover1').style.visibility = 'visible';
        document.getElementById('popupbox1').style.visibility = 'visible';
        document.getElementById('blackcover1').style.display = 'block';
        document.getElementById('popupbox1').style.display = 'block';


    } else {
        // Hide popup
        document.getElementById('blackcover1').style.visibility = 'hidden';
        document.getElementById('popupbox1').style.visibility = 'hidden';
        document.getElementById('blackcover1').style.display = 'none';
        document.getElementById('popupbox1').style.display = 'none';
    }
    if (sw == 'C') {
        // Show popup
        document.getElementById('blackcover2').style.visibility = 'visible';
        document.getElementById('popupbox2').style.visibility = 'visible';
        document.getElementById('blackcover2').style.display = 'block';
        document.getElementById('popupbox2').style.display = 'block';
        $("#closedCnt").text($("[name='removeInstances']:checked").length);
    } else {
        // Hide popup
        document.getElementById('blackcover2').style.visibility = 'hidden';
        document.getElementById('popupbox2').style.visibility = 'hidden';
        document.getElementById('blackcover2').style.display = 'none';
        document.getElementById('popupbox2').style.display = 'none';
    }
    if (sw == 'RO') {
        // Show popup
        document.getElementById('blackcover3').style.visibility = 'visible';
        document.getElementById('popupbox3').style.visibility = 'visible';
        document.getElementById('blackcover3').style.display = 'block';
        document.getElementById('popupbox3').style.display = 'block';
        $("#openCnt").text($("[name='removeInstances']:checked").length);
    } else {
        // Hide popup
        document.getElementById('blackcover3').style.visibility = 'hidden';
        document.getElementById('popupbox3').style.visibility = 'hidden';
        document.getElementById('blackcover3').style.display = 'none';
        document.getElementById('popupbox3').style.display = 'none';
    }
}
/* End:Popup box */

//Save Remediation Plan
var flagValidTotal = new Array();
var returnstring = false;
function updateRemediationPlan() {
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
    
    if ($.trim($('select#planSeverityId option:selected').val()) != 0) {
        flagValidTotal.push(true);
        $("#planSeverityId").removeClass("ux-form-field-error");
        $("#planSeverityErrId").html("");
    } else {
        $("#planSeverityId").addClass("ux-form-field-error");
        $("#planSeverityErrId").html("Please select Adjusted Severity");
        flagValidTotal.push(false);
    }
    
    var strtdat = new Date($("#startDateId").val());
    var endate = new Date($("#endDateId").val());
    var today = new Date();
    
//    if (strtdat >= today) {
//        flagValidTotal.push(true);
//        $("#startDateId").removeClass("ux-form-field-error");
//        $("#errorStartDateID").html("");
//    } else {
//        $("#startDateId").addClass("ux-form-field-error");
//        $("#errorStartDateID").html("Start Date cannot be earlier than Today");
//        flagValidTotal.push(false);
//    }
//    
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

//save risk registered status
var flagValidTotal2 = new Array();
var returnstring2 = false;
function saveRiskValidate() {
    flagValidTotal2 = new Array();

    if ($.trim($('select#adjSeverityId option:selected').val()) != 0) {
        flagValidTotal2.push(true);
        $("#adjSeverityId").removeClass("ux-form-field-error");
        $("#adjSevErrId").html("");
    } else {
        $("#adjSeverityId").addClass("ux-form-field-error");
        $("#adjSevErrId").html("Please select Adjusted Severity");
        flagValidTotal2.push(false);
    }
    
    if ($.trim($('#compCrtlId').val()) != '') {
        flagValidTotal2.push(true);
        $("#compCrtlId").removeClass("ux-form-field-error");
        $("#compCtrlErrId").html("");
    } else {
        $("#compCrtlId").addClass("ux-form-field-error");
        $("#compCtrlErrId").html("Please enter Compensation Control");
        flagValidTotal2.push(false);
    }
    
    for (var i = 0; i < flagValidTotal2.length; i++) {
        if (flagValidTotal2[i] == false) {
            returnstring2 = flagValidTotal2[i];
            flagValidTotal2 = new Array();
            break;
        } else {
            returnstring2 = true;
        }
    }
    return returnstring2;
}

//save closed status
var flagValidTotal3 = new Array();
var returnstring3 = false;
function saveClosedValidate() {
    flagValidTotal3 = new Array();
    
    if ($.trim($('#clsdCommtId').val()) != '') {
        flagValidTotal3.push(true);
        $("#clsdCommtId").removeClass("ux-form-field-error");
        $("#clsdCommtErrId").html("");
    } else {
        $("#clsdCommtId").addClass("ux-form-field-error");
        $("#clsdCommtErrId").html("Please enter Closure Reason");
        flagValidTotal3.push(false);
    }
    
    for (var i = 0; i < flagValidTotal3.length; i++) {
        if (flagValidTotal3[i] == false) {
            returnstring3 = flagValidTotal3[i];
            flagValidTotal2 = new Array();
            break;
        } else {
            returnstring3 = true;
        }
    }
    return returnstring3;
}

//Notify validation
var flagValidTotal4 = new Array();
var returnstring4 = false;
function notifyValidation() {
    flagValidTotal4 = new Array();
    
    var strtdat = new Date($("#startDateId").val());
    var endate = new Date($("#endDateId").val());
    var today = new Date();

    if ($.trim($('#startDateId').val()) != '') {
        flagValidTotal4.push(true);
        $("#startDateId").removeClass("ux-form-field-error");
        $("#errorStartDateID").html("");
    } else {
        $("#startDateId").addClass("ux-form-field-error");
        $("#errorStartDateID").html("Please enter Start Date");
        flagValidTotal4.push(false);
    }
    
    if ($.trim($('#endDateId').val()) != '') {
        if (endate >= strtdat) {
            flagValidTotal.push(true);
            $("#endDateId").removeClass("ux-form-field-error");
            $("#errorEndDateID").html("");
        } else {
            $("#endDateId").addClass("ux-form-field-error");
            $("#errorEndDateID").html("End Date cannot be earlier than Start Date");
            flagValidTotal.push(false);
        }
    } else {
        $("#endDateId").addClass("ux-form-field-error");
        $("#errorEndDateID").html("Please enter End Date");
        flagValidTotal4.push(false);
    }
    
    if ($.trim($('select#remOwnerID option:selected').val()) != 0) {
        flagValidTotal4.push(true);
        $("#remOwnerID").removeClass("ux-form-field-error");
        $("#ownerID").html("");
    } else {
        $("#remOwnerID").addClass("ux-form-field-error");
        $("#ownerID").html("Please select Remediation Owner");
        flagValidTotal4.push(false);
    }
    
    for (var i = 0; i < flagValidTotal4.length; i++) {
        if (flagValidTotal4[i] == false) {
            returnstring4 = flagValidTotal4[i];
            flagValidTotal4 = new Array();
            break;
        } else {
            returnstring4 = true;
        }
    }
    return returnstring4;
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

