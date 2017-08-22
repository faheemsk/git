/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
    $("#rs").hide();

    $("#mpg").addClass("ux-vnav-selected");
    $("#mo").removeClass("ux-vnav-selected");
    $("#mrandp").addClass("ux-vnav-submenu-open");
    $("#mrandp").addClass("ux-vnav-has-selected ");
    $("#mu").removeClass("ux-vnav-selected");
    $("#ua").removeClass("ux-vnav-selected");


    $('tr:odd').addClass('ux-tabl-alt-row');

    if ($('input:radio[class=statusValue]:checked').val() === 'Deactive') {
        $("#rs").show();
    }
    $(".statusValue").click(function()
    {
        var statusValue = $('input:radio[class=statusValue]:checked').val();
        if (statusValue === 'Deactive') {
            $("#rs").show();
            $("#reasonValue").val("");
        } else if (statusValue === 'Active') {
            $("#rs").hide();
        }
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
            {"bSortable": true}
        ]
    }).columnFilter({
        sPlaceHolder: "head:after",
        aoColumns: [
            null,
            null,
            null
        ]
    });
    /*
     * For hiding global search from data table
     */
    $("#dataTable_filter").css({"display": "none"});
    /*IN:020650: For remove sorting for checkbox column*/
    $("#removeSortingForCheckbox").removeClass("sorting_desc");
    /*IN:020650: For remove sorting for checkbox column*/
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

function saveOrUpdateManagePermissionGroup() {
    var checkboxes = new Array();
    var flag = false;
    var permissionGroupNameValue = $("#permissionGroupNameValue").val().trim();
    var permissionGroupDefinitionValue = $("#permissionGroupDefinitionValue").val().trim();
    var statusValue = $('input:radio[class=statusValue]:checked').val();
    var originalGroupName = $("#originalGroupName").val();
    var reasonValue = $("#reasonValue").val().trim();

    //Start: Validation for Detailed Permissions
    $('.checkboxGroup').each(function() {
        if (this.checked) {
            var id = $(this).attr('id');
            checkboxes.push(id);
            flag = true;
            $("#errorPermissionCheckbox").html("");
        }
    });
    if (!flag) {
        $("#errorPermissionCheckbox").html("<font class='ux-msg-error-under' color='red'>Please select at least one Permission</font>");
        flag = false;
    }
    //End: Validation for Detailed Permissions
    //Start: Validation for Permission Group Details
    if (permissionGroupNameValue === '') {
        $("#permissionGroupNameValue").addClass("ux-form-field-error");
        $("#errorGroupNameValue").html("<font color='red'>Please enter Permission Group Name</font>");
        flag = false;
    }
    if (permissionGroupNameValue !== '') {
        $("#permissionGroupNameValue").removeClass("ux-form-field-error");
        $("#errorGroupNameValue").html("");
    }
    if (permissionGroupDefinitionValue === '') {
        $("#permissionGroupDefinitionValue").addClass("ux-form-field-error");
        $("#errorGroupDescValue").html("<font color='red'>Please enter Permission Group Definition</font>");
        flag = false;
    }
    if (permissionGroupDefinitionValue !== '') {
        $("#permissionGroupDefinitionValue").removeClass("ux-form-field-error");
        $("#errorGroupDescValue").html("");
    }
    if (permissionGroupNameValue.length > 100) {
        $("#permissionGroupNameValue").addClass("ux-form-field-error");
        $("#errorGroupNameValue").html("<font color='red'>Permission Group Name cannot exceed 100 characters</font>");
        flag = false;
    }
    if (permissionGroupNameValue.length < 100 && $("#errorGroupNameValue").html() === '') {
        $("#permissionGroupNameValue").removeClass("ux-form-field-error");
        $("#errorGroupNameValue").html("");
    }
    if (permissionGroupDefinitionValue.length > 1000) {
        $("#permissionGroupDefinitionValue").addClass("ux-form-field-error");
        $("#errorGroupDescValue").html("<font color='red'>Definition cannot exceed 1000 characters</font>");
        flag = false;
    }
    if (permissionGroupDefinitionValue.length < 1000 && $("#errorGroupDescValue").html() === '') {
        $("#permissionGroupDefinitionValue").removeClass("ux-form-field-error");
        $("#errorGroupDescValue").html("");
    }
    if (statusValue === undefined) {
        $("#errorGroupStatus").html("<font color='red'>Please select Status</font>");
        flag = false;
    } else {
        $("#errorGroupStatus").html("");
    }
    if (statusValue === 'Deactive') {
        if (jQuery.trim(reasonValue) === '') {
            $("#errordeactiveReason").html("<font color='red'>Please enter Reason for Deactivation</font>");
            $("#reasonValue").addClass("ux-form-field-error");
            flag = false;
        } else if (jQuery.trim(reasonValue).length > 1000) {
            $("#errordeactiveReason").html("<font color='red'>Reason for Deactivation cannot exceed 1000 characters</font>");
            $("#reasonValue").addClass("ux-form-field-error");
            flag = false;
        } else {
            $("#errordeactiveReason").html("");
            $("#reasonValue").removeClass("ux-form-field-error");
        }
    } else {
        $("#errordeactiveReason").html("");
        $("#reasonValue").removeClass("ux-form-field-error");
    }
    if (!flag) {
        return false;
    }
    //End: Validation for Permission Group Details

    if (originalGroupName !== permissionGroupNameValue) {
        jQuery.ajax({
            type: "POST",
            contentType: "text/plain",
            async: false,
            url: "validatepermissiongroupname.htm?permsnGrpNm=" + permissionGroupNameValue,
            datatype: "text",
            success: function(data) {
                if (data != "No") {
                    flag = false;
                    $("#errorGroupNameValue").html("<span class='ux-msg-error-under' style='font-weight: bold'>" + data + "</span>");
                    flag = false;
                } else {
                    $("#errorGroupNameValue").html("");
                    flag = true;
                }
            },
            error: function(e) {
                return false;
                //alert("Error - Session Expired/No Result");
            }
        });
    } else {
        flag = true;
    }

    return flag;
}

function saveUpdatePermissionGroup() {
    if (saveOrUpdateManagePermissionGroup()) {
        document.forms['saveOrUpdatePermissionGroup'].action = "saveorupdatepermissiongroup.htm";
        document.forms['saveOrUpdatePermissionGroup'].submit();
    }
}

function cancelPermissionGroup() {
    document.permissnfrm.action = "permissiongroupworklist.htm";
    document.permissnfrm.submit();
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