/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {

    $("#mr").addClass("ux-vnav-selected");
    $("#ua").removeClass("ux-vnav-selected");
    $("#mu").removeClass("ux-vnav-selected");
    $("#mo").removeClass("ux-vnav-selected");
    $("#mrandp").addClass("ux-vnav-submenu-open");
    $("#mrandp").addClass("ux-vnav-has-selected ");

    $('tr:odd').addClass('ux-tabl-alt-row');

    if ($('input:radio[id=deactiveradio]:checked').val() === 'deactive') {
        $("#reason").show();
    } else {
        $("#appRoleComments").html("NA");
    }
    
    $("#deactiveradio").click(function()
    {
        $("#appRoleComments").html("");
        $("#reason").show();

    });
    $("#activeradio").click(function()
    {
        $("#appRoleComments").html("NA");
        $("#reason").hide();

    });


    $('#dataTable').dataTable({
        "order": [[0, "desc"]],
        ordering: true,
        paging: false,
        "sScrollY": "250",
        info: false,
        "aoColumns": [
            {"bSortable": false},
            {"bSortable": true},
            {"bSortable": true},
            {"bSortable": false}
        ]
    }).columnFilter({
        sPlaceHolder: "head:after",
        aoColumns: [
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

    $("#hchk").removeClass("sorting_desc");
});


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



function saveRoleDetails() {
    
    var prnGrpIDs = "";
    var roleName = $.trim($("#appRoleName").val());
    var roleDesc = $.trim($("#appRoleDescription").val());
    var reasonDeactivation = $.trim($("#appRoleComments").val());
    var existingRoleName = $("#existingRoleName").val();
    var checkedList = $('input[id=prnGrpID]:checked');
    $(checkedList).each(function() {
        prnGrpIDs += $(this).val() + ",";
    });
    $("#prnGrpIDs").val(prnGrpIDs);
    var isChecked = $("input[name=rowStatusValue]:checked").length;
    var selRadioVal = $("input[name=rowStatusValue]:checked").val();
    var flagValidTotal = new Array();
    var returnstring = false;

    if (jQuery.trim(roleName) == "") {
        $("#appRoleName").addClass("ux-form-field-error");
        $("#errorMsgRoleName").html("Please enter Role Name");
        flagValidTotal.push(false);
    } else if ($('#appRoleName').val().trim().length > 100) {
        $("#appRoleName").addClass("ux-form-field-error");
        $("#errorMsgRoleName").html("Role Name cannot exceed 100 characters");
        flagValidTotal.push(false);
    } else if (!validateAppRoleName(existingRoleName)) {
        $("#appRoleName").addClass("ux-form-field-error");
        flagValidTotal.push(false);
        //("#errorMsgRoleName").html("Role name already exists");
    } else {
        flagValidTotal.push(true);
        $("#errorMsgRoleName").html("");
        $("#appRoleName").removeClass("ux-form-field-error");
    }

    if (jQuery.trim(roleDesc) == "") {
        $("#appRoleDescription").addClass("ux-form-field-error");
        $("#errorMsgRoleDescription").html("Please enter Role Definition");
        flagValidTotal.push(false);
    } else if ($('#appRoleDescription').val().trim().length > 1000) {
        $("#appRoleDescription").addClass("ux-form-field-error");
        $("#errorMsgRoleDescription").html("Definition cannot exceed 1000 characters");
        flagValidTotal.push(false);
    } else {
        flagValidTotal.push(true);
        $("#errorMsgRoleDescription").html("");
         $("#appRoleDescription").removeClass("ux-form-field-error");
    }

    if (isChecked <= 0) {
        $("#errorMsgStatus").html(" Please select Status");
        flagValidTotal.push(false);
    } else {
        flagValidTotal.push(true);
        $("#errorMsgStatus").html("");
    }
    if (selRadioVal === 'deactive') {
        if (jQuery.trim(reasonDeactivation) == "") {
            $("#appRoleComments").addClass("ux-form-field-error");
            $("#errorMsgReasonDeactive").html("Please enter Reason for Deactivation");
            flagValidTotal.push(false);
        } else if ($('#appRoleComments').val().trim().length > 1000) {
            $("#appRoleComments").addClass("ux-form-field-error");
            $("#errorMsgReasonDeactive").html("Reason for Deactivation cannot exceed 1000 characters");
            flagValidTotal.push(false);
        } else {
            flagValidTotal.push(true);
            $("#errorMsgReasonDeactive").html("");
            $("#appRoleComments").removeClass("ux-form-field-error");
        }
    }
    if (prnGrpIDs.length <= 0) {
        $("#errorMsgPrmnGrp").html("Please select Permission Group");
        flagValidTotal.push(false);
    } else {
        flagValidTotal.push(true);
        $("#errorMsgPrmnGrp").html("");
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
    
    //if (returnstring) {
    if (returnstring) {
        document.addrole.action = "saveroledetails.htm";
        document.addrole.submit();
    }

}


function updateRolePrmnGrpDetails() {


    var prnGrpIDs = "";
    var roleName = $.trim($("#appRoleName").val());
    var roleDesc = $.trim($("#appRoleDescription").val());
    var existingRoleName = $("#existingRoleName").val();
    var reasonDeactivation = $.trim($("#appRoleComments").val());
    var checkedList = $('input[id=prnGrpID]:checked');
    $(checkedList).each(function() {
        prnGrpIDs += $(this).val() + ",";
    });
    $("#prnGrpIDs").val(prnGrpIDs);
    var isChecked = $("input[name=rowStatusValue]:checked").length;
    var selRadioVal = $("input[name=rowStatusValue]:checked").val();
    var flagValidTotal = new Array();
    var returnstring = false;

    if (jQuery.trim(roleName) == "") {
        $("#appRoleName").addClass("ux-form-field-error");
        $("#errorMsgRoleName").html("Please enter Role Name");
        flagValidTotal.push(false);
    } else if ($('#appRoleName').val().trim().length > 100) {
        $("#appRoleName").addClass("ux-form-field-error");
        $("#errorMsgRoleName").html("Role Name cannot exceed 100 characters");
        flagValidTotal.push(false);
    } else if (!validateAppRoleName(existingRoleName)) {
        $("#appRoleName").addClass("ux-form-field-error");
        flagValidTotal.push(false);
        //("#errorMsgRoleName").html("Role name already exists");
    } else {
        flagValidTotal.push(true);
        $("#errorMsgRoleName").html("");
         $("#appRoleName").removeClass("ux-form-field-error");
    }

    if (jQuery.trim(roleDesc) == "") {
        $("#appRoleDescription").addClass("ux-form-field-error");
        $("#errorMsgRoleDescription").html("Please enter Role Definition");
        flagValidTotal.push(false);
    } else if ($('#appRoleDescription').val().trim().length > 1000) {
        $("#appRoleDescription").addClass("ux-form-field-error");
        $("#errorMsgRoleDescription").html("Definition cannot exceed 1000 characters");
        flagValidTotal.push(false);
    } else {
        flagValidTotal.push(true);
        $("#errorMsgRoleDescription").html("");
        $("#appRoleDescription").removeClass("ux-form-field-error");
    }

    if (isChecked <= 0) {
        $("#errorMsgStatus").html(" Please select Status");
        flagValidTotal.push(false);
    } else {
        flagValidTotal.push(true);
        $("#errorMsgStatus").html("");
    }
    
    if (selRadioVal === 'deactive') {
        if (jQuery.trim(reasonDeactivation) == "") {
            $("#appRoleComments").addClass("ux-form-field-error");
            $("#errorMsgReasonDeactive").html("Please enter Reason for Deactivation");
            flagValidTotal.push(false);
        } else if ($('#appRoleComments').val().trim().length > 1000) {
            $("#appRoleComments").addClass("ux-form-field-error");
            $("#errorMsgReasonDeactive").html("Reason for Deactivation cannot exceed 1000 characters");
            flagValidTotal.push(false);
        } else {
            flagValidTotal.push(true);
            $("#errorMsgReasonDeactive").html("");
            $("#appRoleComments").removeClass("ux-form-field-error");
        }
    }

    if (prnGrpIDs.length <= 0) {
        $("#errorMsgPrmnGrp").html("Please select Permission Group");
        flagValidTotal.push(false);
    } else {
        flagValidTotal.push(true);
        $("#errorMsgPrmnGrp").html("");
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

    if (returnstring) {
        document.addrole.action = "updateRolePrmnGrpDetails.htm";
        document.addrole.submit();
    }

}



function addRoleCancel() {
    document.forms[0].action = "manageRolepage.htm";
    document.forms[0].submit();
}

function validateAppRoleName(existingRoleName) {
    var appRoleName = $.trim($("#appRoleName").val());
    var returnflag = true;
    if (existingRoleName !== appRoleName) {
        jQuery.ajax({
            type: "POST",
            contentType: "text/plain",
            url: "validateapprolename.htm?appRoleName=" + appRoleName,
            async: false,
            datatype: "text",
            success: function(data) {
                if (data != "No") {
                    returnflag = false;
                    $("#errorMsgRoleName").html("" + data + "");
                } else {
                    returnflag = true;
                    $("#errorMsgRoleName").html("");
                }

            },
            error: function(e) {
                //   alert("Error - Session Expired/No Result");
            }
        });
    }
    return returnflag;
}
;




