/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(window).load(function() {
    $(".loader").fadeOut("slow");
});

$(document).ready(function(){
    $("#adjustedId").hide();
    $("#closureid").hide();
    $("#clsdateid").hide();
            
    $("#updateStatus").click(function() {
        if(updateRemediationStatus()){
            document.editFindingStatus.action = "updateassocfindstatus.htm";
            document.editFindingStatus.submit();
        }
    });
    
    $("#statusCdId").change(function() {
        if($("#statusCdId").val() === 'RR'){
            $("#adjSeverityId").removeClass("ux-form-field-error");
            $("#adjSevErrId").html("");
            $("#compCrtlId").removeClass("ux-form-field-error");
            $("#compCtrlErrId").html("");
            $('#compCrtlId').val("");
            $('#adjSeverityId').val("0");
            
            $("#adjustedId").show();
            $("#closureid").hide();
            $("#clsdateid").hide();
        }else if($("#statusCdId").val() === 'C'){
            $("#clsdCommId").removeClass("ux-form-field-error");
            $("#commErrID").html("");
            $('#clsdCommId').val("");
                
            $("#adjustedId").hide();
            $("#closureid").show();
            $("#clsdateid").show();
        }else{
            $("#adjustedId").hide();
            $("#closureid").hide();
            $("#clsdateid").hide();
        }
    });
});

function toRemediationPlan(){
    document.editFindingStatus.action = "remediationplan.htm";
    document.editFindingStatus.submit();
}

function toEditRemediationPlan() {
    document.editFindingStatus.action = "editremediationpage.htm";
    document.editFindingStatus.submit();
}

//update associated finding status
var flagValidTotal = new Array();
var returnstring = false;
function updateRemediationStatus() {
    flagValidTotal = new Array();
    
    if ($.trim($('select#statusCdId option:selected').val()) != 0) {
        flagValidTotal.push(true);
        $("#statusCdId").removeClass("ux-form-field-error");
        $("#statusErrID").html("");
    } else {
        $("#statusCdId").addClass("ux-form-field-error");
        $("#statusErrID").html("Please select Finding Remediation Status");
        flagValidTotal.push(false);
    }
    
    if($('select#statusCdId option:selected').val() == 'C'){
        if ($.trim($('#clsdCommId').val()) != '') {
            //Closed comments length  validation checking
            if (jQuery.trim($('#clsdCommId').val()).length > 1000) {
                $("#clsdCommId").addClass("ux-form-field-error");
                $("#commErrID").html("Reason for Closure cannot exceed 1000 characters");
                flagArray.push(false);
            }else{
                flagValidTotal.push(true);
                $("#clsdCommId").removeClass("ux-form-field-error");
                $("#commErrID").html("");
            }
        } else {
            $("#clsdCommId").addClass("ux-form-field-error");
            $("#commErrID").html("Please enter Reason for Closure");
            flagValidTotal.push(false);
        }
    }else{
        $("#clsdCommId").removeClass("ux-form-field-error");
        $("#commErrID").html("");
    }
    
    if($('select#statusCdId option:selected').val() == 'RR'){
        if ($.trim($('select#adjSeverityId option:selected').val()) != 0) {
            flagValidTotal.push(true);
            $("#adjSeverityId").removeClass("ux-form-field-error");
            $("#adjSevErrId").html("");
        } else {
            $("#adjSeverityId").addClass("ux-form-field-error");
            $("#adjSevErrId").html("Please select Adjusted Severity");
            flagValidTotal.push(false);
        }

        if ($.trim($('#compCrtlId').val()) != '') {
            //Closed comments length  validation checking
            if (jQuery.trim($('#compCrtlId').val()).length > 1000) {
                $("#compCrtlId").addClass("ux-form-field-error");
                $("#compCtrlErrId").html("Compensating Control cannot exceed 1000 characters");
                flagArray.push(false);
            }else{
                flagValidTotal.push(true);
                $("#compCrtlId").removeClass("ux-form-field-error");
                $("#compCtrlErrId").html("");
            }
        } else {
            $("#compCrtlId").addClass("ux-form-field-error");
            $("#compCtrlErrId").html("Please enter Compensating Control");
            flagValidTotal.push(false);
        }
    }else{
        $("#adjSeverityId").removeClass("ux-form-field-error");
        $("#adjSevErrId").html("");
        $("#compCrtlId").removeClass("ux-form-field-error");
        $("#compCtrlErrId").html("");
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


