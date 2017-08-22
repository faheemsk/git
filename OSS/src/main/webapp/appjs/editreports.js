/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(function() {
//    $('.defaultActualPicker').datepick({
//        defaultDate: new Date(), showTrigger: '<img src="images/calendar01.gif" alt="Popup" class="trigger">'});

});

function showDate(date) {
    alert('The date chosen is ' + date);
}

$(document).ready(function() {
    $("#accordion").accordion({
        collapsible: true,
        active: false
    });

    $('.fixme').fixheadertable({
//caption : 'My header is fixed !',
        colratio: [50, , 200, 170, 170, 120],
        zebra: true,
        whiteSpace: 'normal',
        resizeCol: false,
    });
    $("#cru").addClass("ux-vnav-selected");

    $("#uploadFile").click(function() {
        $("#errorCheckboxValidation").html("");
        $("#serversideflag").val("U");
        var flagArray = new Array();
        var returnstring = false;
        var reportValue = $("#addrep").val();
        var templateupload = $("#templateupload").val();
        var docName = $("#documentTypeKey :selected").text().trim();
        var fileExtension = templateupload.substring(templateupload.lastIndexOf(".") + 1, templateupload.length);
        var filename = templateupload.substring(0, templateupload.lastIndexOf("."));
        if (reportValue === "0") {
            $("#errorReportName").html("Please select Report Name");
            flagArray.push(false);
        } else {
            $("#errorReportName").html("");
            flagArray.push(true);
        }
        if (templateupload === '' || templateupload === undefined) {
            $("#errorMsgUpload").html("Please upload a file");
            flagArray.push(false);
        } else if (fileExtension == "xlsx" || fileExtension == "xls" || fileExtension == "csv" || fileExtension == "xml" ||
                fileExtension == "jpeg" || fileExtension == "jpg" || fileExtension == "JPG" || fileExtension == "png" || fileExtension == "bmp"
                || fileExtension == "pdf" || fileExtension == "doc" || fileExtension == "docx") {
            $("#errorMsgUpload").html("");
            flagArray.push(true);
        }
        else {
            $("#errorMsgUpload").html("Invalid file format. Please upload xls, xlsx, CSV, XML, JPEG, JPG, PNG, BMP, PDF, doc, docx format file");
            flagArray.push(false);
        }


        for (var i = 0; i < flagArray.length; i++) {
            if (flagArray[i] == false) {
                returnstring = flagArray[i];
                flagArray = new Array();
                break;
            } else {
                returnstring = true;
            }
        }
        $("#reportName").val($("#addrep :selected").text());
         $("#serversideflag").val("U");
//        if (returnstring) {
//            document.saveUploadReportFile.action = "updateSaveReportFile.htm";
//            document.saveUploadReportFile.submit();
//        }
        if (returnstring) {
            document.saveUploadReportFile.action = "ecgupdateSaveReportFile.htm";
            document.saveUploadReportFile.submit();
        }
    });
    
    //Start: Reload / Reset the page when click on cancel
    $("#fnCancelEditReload").click(function() {
        var encEngagementCodeValue = $("#clientEngagementCodeValue").val();
        $("#ec").val(encEngagementCodeValue);
        document.toCancelReloadEditForm.action = "editReports.htm";
        document.toCancelReloadEditForm.submit();
    });
    //End: Reload / Reset the page when click on cancel
});

function deleteEditUploadedFile(fileId) {
    //alert("fileId::"+fileId);
    var encFileId = $("#encUploadFileID" + fileId).val();
    var clientEngagementCodeValue = $("#clientEngagementCodeValue").val();
    $("#encUploadFileKey").val(encFileId);
    $("#encClientEngagementCode").val(clientEngagementCodeValue);
    //alert("clientEngagementCodeValue::"+clientEngagementCodeValue);
    javascript:popup(1);
}
function editdeleteuploadfilelist(fileId) {
    document.fileuploaddetails.action = "deleteReportsUploadedFiles.htm";
    document.fileuploaddetails.submit();

}

function editpublishreportfile() {
//    alert("filename");
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
        $("#errorReportName").html("");
        $("#errorMsgUpload").html("");
        $("#errorCheckboxValidation").html("Please select at least one Report");
        flag = false;
    }
    return flag;
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

function updateReportStatus(count) {
    var unPublishedValue = $("#unPublishedValue" + count).val();
    $("#status").val(unPublishedValue);
    document.publishReportForm.action = "publishReportFile.htm";
    document.publishReportForm.submit();

}
function toDashboardPage(from) {
//    window.location.href = "toengagementhomepage.htm?engKey=" + $("#encEngagementCode").val() + "&from=" + from;
    $("#from").val(from);
    document.toDashboardPageForm.action = "toengagementhomepage.htm";
    document.toDashboardPageForm.submit();
}
function publishEng() {
    window.location.href = "publishEngagment.htm?engKey=" + $("#engkey").val();
}

function publishReport() {
    $("#serversideflagpublish").val("P");
    if (editpublishreportfile()) {
        document.publishReportForm.action = "publishReportFile.htm";
        document.publishReportForm.submit();
    }
}