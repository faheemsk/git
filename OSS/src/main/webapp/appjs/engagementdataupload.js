/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(window).load(function() {
    $(".loader").fadeOut("slow");
})

$(document).ready(function() {

    $("#mu").removeClass("ux-vnav-selected");
    $("#mo").removeClass("ux-vnav-selected");
    $("#mrandp").addClass("ux-vnav-submenu-closed");
    $("#mrandp").removeClass("ux-vnav-has-selected ");
    $("#ua").removeClass("ux-vnav-selected");
    $("#emwl").addClass("ux-vnav-submenu-closed");
    $("#emwl").removeClass("ux-vnav-selected");
    $("#meng").removeClass("ux-vnav-selected");

    $("#edu").addClass("ux-vnav-selected");

    $("#defaultActualPicker").datepicker({
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
        "order": [[3, "none"]],
        ordering: true,
        paging: true,
        info: false,
        "aoColumns": [
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
            {type: "text"},
            null,
            {type: "text"},
            null
        ]

    });

    $("#dataTable_filter").css({"display": "none"});

    $('tr:odd').addClass('ux-tabl-alt-row');

    $("#ux-outline-example").click(function() {
        $(".ux-snav").fadeOut().fadeIn();
    });


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
            document.getElementById("defaultActualPicker").value = "";
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
    //For Last Updated Date search
    var defaultActualPicker = $("#defaultActualPicker").val();
    tableSettings.aoPreSearchCols[ 3 ].sSearch = defaultActualPicker;
    dateType.fnDraw();
}

$(document).ready(function() {
    $("#ux-outline-example").click(function() {
        $(".ux-snav").fadeOut().fadeIn();
    });

    // if($("#lockCheckbox").is(':checked')) {
    // 	$('.uploadData').hide();
    // 	}
//    $("#lockCheckbox").on('change', function() {
//
//        if (this.checked) {
//            $('.uploadData').hide();
//        } else {
//            $('.uploadData').show();
//        }
//        ;
//    });

    $("#engagementUpload11").click(function() {
        var docName = $("#documentTypeKey :selected").text().trim();
        var sourceName = $("#sourceNameKey :selected").val();
        var documentTypeName = $("#documentTypeKey :selected").val();
        var returnstring = false;
        var flagArray = new Array();
        var flag = false;
        var templateupload = $("#templateupload").val();
        var fileExtension = templateupload.substring(templateupload.lastIndexOf(".") + 1, templateupload.length);
        var filename = templateupload.substring(0, templateupload.lastIndexOf("."));

        if (templateupload == '') {
            $("#errorMsgUpload").html("Please Upload a File");
            flagArray.push(false);
        } else if ($('#templateupload').get(0).files.length === 0) {
            $("#errorMsgUpload").html("No data exists in the file. Please upload a file with valid data");
            flagArray.push(false);
        } else if (fileExtension == "xlsx" || fileExtension == "xls" || fileExtension == "csv" || fileExtension == "xml" ||
                fileExtension == "jpeg" || fileExtension == "JPG" || fileExtension == "png" || fileExtension == "bmp"
                || fileExtension == "pdf" || fileExtension == "doc" || fileExtension == "docx") {
            $("#errorMsgUpload").html("");
            flagArray.push(true);
        }
        else {
            $("#errorMsgUpload").html("Invalid file format. Please upload XML, JPEG, JPG, PNG, BMP, PDF, doc, docx format file");
            flagArray.push(false);
        }

        if (docName == 'Any Other Data' && (fileExtension == "xlsx" || fileExtension == "xls" || fileExtension == "csv")) {
            $("#errorMsgUpload").html("");
            flagArray.push(true);
        } else {
            $("#errorMsgUpload").html("Invalid file format. Please upload xls, xlsx, CSV format file");
            flagArray.push(false);
        }

        if (jQuery.trim(sourceName) === "0") {
            $("#sourceNameKey").addClass("ux-form-field-error");
            $("#errorMsgSourceName").html("Please select Finding Source Name");
            flagArray.push(false);
        } else {
            $("#sourceNameKey").removeClass("ux-form-field-error");
            $("#errorMsgSourceName").html("");
            flagArray.push(true);
        }
        if (jQuery.trim(documentTypeName) === "0") {
            $("#documentTypeKey").addClass("ux-form-field-error");
            $("#errorMsgDocType").html("Please select Document Type");
            flagArray.push(false);
        } else {
            $("#documentTypeKey").removeClass("ux-form-field-error");
            $("#errorMsgDocType").html("");
            flagArray.push(true);
        }
        if ($('#uploadComments').val().trim().length > 1000) {
            $("#uploadComments").addClass("ux-form-field-error");
            $("#errorMsgUploadComments").html("Comments cannot exceed 1000 characters");
            flagArray.push(false);
        } else {
            flagArray.push(true);
            $("#errorMsgUploadComments").html("");
            $("#uploadComments").removeClass("ux-form-field-error");
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
        //save upload data
        if (returnstring) {
            document.uploadFileDetails.action = "saveUploadFile.htm";
            document.uploadFileDetails.submit();
        }
    });


});


//$(function() {
//    $('.defaultActualPicker').datepick({
//        defaultDate: new Date(), showTrigger: '<img src="images/calendar01.gif" alt="Popup" class="trigger">'});
//
//});




ddaccordion.init({
    headerclass: "submenuheader", //Shared CSS class name of headers group
    contentclass: "submenu", //Shared CSS class name of contents group
    revealtype: "click", //Reveal content when user clicks or onmouseover the header? Valid value: "click", "clickgo", or "mouseover"
    mouseoverdelay: 200, //if revealtype="mouseover", set delay in milliseconds before header expands onMouseover
    collapseprev: true, //Collapse previous content (so only one open at any time)? true/false 
    defaultexpanded: [0], //index of content(s) open by default [index1, index2, etc] [] denotes no content
    onemustopen: false, //Specify whether at least one header should be open always (so never all headers closed)
    animatedefault: false, //Should contents open by default be animated into view?
    persiststate: true, //persist state of opened contents within browser session?
    toggleclass: ["ux-accordion-panl-open", "ux-accordion-panl-closed"], //Two CSS classes to be applied to the header when it's collapsed and expanded, respectively ["class1", "class2"]
    togglehtml: ["prefix", "", ""], //Additional HTML added to the header when it's collapsed and expanded, respectively  ["position", "html1", "html2"] (see docs)
    animatespeed: "fast", //speed of animation: integer in milliseconds (ie: 200), or keywords "fast", "normal", or "slow"
    oninit: function(headers, expandedindices) { //custom code to run when headers have initalized
        //do nothing
    },
    onopenclose: function(header, index, state, isuseractivated) { //custom code to run whenever a header is opened or closed
        //do nothing
    }
});

$(document).ready(function() {

    $('.fixme').fixheadertable({
        //caption : 'My header is fixed !',
        colratio: [100, 150, 150, , 125, 100],
        whiteSpace: 'normal',
        resizeCol: false,
    });
});

$(document).ready(function() {
    $("#rowdel").click(function()
    {
        $("#row1").hide();

    });
    $("#uploaded").hide();
    $("#upload").click(function()
    {
        $("#uploaded").show();

    });
    $("#uploaded1").hide();
    $("#upload1").click(function()
    {
        $("#uploaded1").show();

    });
    $("#uploaded2").hide();
    $("#upload2").click(function()
    {
        $("#uploaded2").show();

    });
    $("#delete1").click(function()
    {
        $("#filedetails1").hide();

    });
    $("#delete").click(function()
    {
        $("#filedetails").hide();

    });

});


function strReplaceAll(string, Find, Replace) {
    try {
        return string.replace(new RegExp(Find, "gi"), Replace);
    } catch (ex) {
        return string;
    }
}

//function deleteUploadFile(uploadfileId) {
//    var encFileUpload = $("#encfileUploadID" + uploadfileId).val();
//    document.uploadFileDetails.action = "deleteUploadedFiles.htm?encfileUploadID=" + encFileUpload;
//    document.uploadFileDetails.submit();
//}

function deleteFile(uploadfileId) {
    var encFileUpload = document.getElementById("encfileUploadID" + uploadfileId).value;
    $("#encfileUploadID").val(encFileUpload);
    javascript:deletepopup(1);
}

function deleteUploadFile() {
    //var encFileUpload = $("#encFileDeleteID").val();
    document.uploadFileDetails.action = "deleteUploadedFiles.htm";
    document.uploadFileDetails.submit();
}

function lockUnlockSave(fileCount) {
    var returnstring = false;
    var flagArray = new Array();
    if ($('#lockCheckbox').is(":checked")) {
        if (fileCount <= 0) {
            $("#errorMsglock").html("To lock the service data at least one document must be uploaded for the service");
            flagArray.push(false);
        } else {
            $("#errorMsglock").html("");
            flagArray.push(true);
        }
    } else {
        $("#errorMsglock").html("");
        flagArray.push(true);
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
    // alert("returnstring::"+returnstring);
    if (returnstring) {
        document.uploadFileDetails.action = "lockUnlockServiceData.htm";
        document.uploadFileDetails.submit();
    }
}


function uploadFileSave(engmntCode, serviceCode) {
    var docName = $("#documentTypeKey :selected").text().trim();
    var sourceName = $("#sourceNameKey :selected").val();
    var documentTypeName = $("#documentTypeKey :selected").val();
    var returnstring = false;
    var flagArray = new Array();
    var flag = false;
    var pattern = /^[a-zA-Z0-9_\-.\s]*$/;
    var templateupload = $("#templateupload").val();
    var fileExtension = templateupload.substring(templateupload.lastIndexOf(".") + 1, templateupload.length);
    //  alert(engmntCode+""+serviceCode+"----"+templateupload+"FileExt::"+fileExtension);
    var stats = 1;
    if (templateupload === '') {
        $("#errorMsgUpload").html("Please Upload a File");
        flagArray.push(false);
    } else {
//        if (!validateAppFileName(templateupload, engmntCode, serviceCode)) {
//            //  $("#errorMsgUpload").html("");
//            flagArray.push(false);
//            stats = 0;
//        } else {
//            $("#errorMsgUpload").html("");
//            flagArray.push(true);
//        }
       
        if (fileExtension === "xml" || fileExtension === "jpeg" || fileExtension === "JPG" || fileExtension === "png"
                || fileExtension === "bmp" || fileExtension === "pdf" || fileExtension === "doc" || fileExtension === "docx"
                || fileExtension === "xlsx" || fileExtension === "xls" || fileExtension === "csv" || fileExtension === "jpg"
                || fileExtension === "JPEG" || fileExtension === "PNG" || fileExtension === "PDF" || fileExtension === "BMP"
                || fileExtension === "DOC" || fileExtension === "DOCX") {
            if (stats === 1) {
//                if (pattern.test(templateupload)) {
//                    $("#errorMsgUpload").html("");
//                    flagArray.push(true);
//                } else {
//                    $("#errorMsgUpload").html("Please upload a file with valid name");
//                    flagArray.push(false);
//                }
                  $("#errorMsgUpload").html("");
                   flagArray.push(true);  
                
            }
        } else {
            $("#errorMsgUpload").html("Invalid file format. Please upload xls, xlsx, CSV, XML, JPEG, JPG, PNG, BMP, PDF, doc, docx format file");
            flagArray.push(false);
        }
    }

    if (docName === 'Data') {
        if (fileExtension === "xlsx" || fileExtension === "xls" || fileExtension === "csv") {
            if (stats === 1) {
//                if(pattern.test(templateupload)){
//                    $("#errorMsgUpload").html("");
//                    flagArray.push(true);
//                }else{
//                    $("#errorMsgUpload").html("Please upload a file with valid name");
//                    flagArray.push(false);
//                }
                $("#errorMsgUpload").html("");
                flagArray.push(true);
            }
        } else {
            $("#errorMsgUpload").html("Invalid file format. Please upload file in xls, xlsx or CSV format");
            flagArray.push(false);
        }
    }



    if (jQuery.trim(sourceName) === "0") {
        $("#sourceNameKey").addClass("ux-form-field-error");
        $("#errorMsgSourceName").html("Please select Finding Source Name");
        flagArray.push(false);
    } else {
        $("#sourceNameKey").removeClass("ux-form-field-error");
        $("#errorMsgSourceName").html("");
        flagArray.push(true);
    }
    if (jQuery.trim(documentTypeName) === "0") {
        $("#documentTypeKey").addClass("ux-form-field-error");
        $("#errorMsgDocType").html("Please select Document Type");
        flagArray.push(false);
    } else {
        $("#documentTypeKey").removeClass("ux-form-field-error");
        $("#errorMsgDocType").html("");
        flagArray.push(true);
    }
    if ($('#uploadComments').val().trim().length > 1000) {
        $("#uploadComments").addClass("ux-form-field-error");
        $("#errorMsgUploadComments").html("Comments cannot exceed 1000 characters");
        flagArray.push(false);
    } else {
        flagArray.push(true);
        $("#errorMsgUploadComments").html("");
        $("#uploadComments").removeClass("ux-form-field-error");
    }
    for (var i = 0; i < flagArray.length; i++) {
        if (flagArray[i] === false) {
            returnstring = flagArray[i];
            flagArray = new Array();
            break;
        } else {
            returnstring = true;
        }
    }
    //save upload data
    if (returnstring) {
        document.uploadFileDetails.action = "saveUploadFile.htm";
        document.uploadFileDetails.submit();
    }
    


}



//function downloadFile(ufileName, ufilePath) {
//    var filePath = strReplaceAll(ufilePath, "~", "\\");
//    $("#uploadFileName").val(ufileName);
//    $("#uploadFilePath").val(filePath);
//    document.fileuploaddetails.action = "downloadFile.htm";
//    document.fileuploaddetails.submit();
//}

function downloadFile(fileId) {
    var encFileDwnld = $("#encFileDownload" + fileId).val();
    $("#encFileDownload").val(encFileDwnld);
//    document.fileuploaddetails.action = "downloadFile.htm?encFileDownload="+encFileDwnld;
  //  document.fileuploaddetails.action = "downloadFile.htm?encFileDownload="+fileDwndStr;
//    document.fileuploaddetails.action = "downloadFile.htm";
//    document.fileuploaddetails.submit();
}


function cancelBtn() {
    $("#sourceNameKey").val("0");
    $("#documentTypeKey").val("0");
    $("#uploadComments").val("");
    $("#templateupload").val("");

    $("#errorMsgSourceName").html("");
    $("#errorMsgDocType").html("");
    $("#errorMsgUpload").html("");
    $("#errorMsgUploadComments").html("");

    $("#sourceNameKey").removeClass("ux-form-field-error");
    $("#documentTypeKey").removeClass("ux-form-field-error");
}

function checkboxvalidate(lockStatus) {
    var checkStatus = $('#lockCheckbox').is(":checked");
    if (lockStatus === 'true') {
        $('.uploadData').hide();
    }
    if (lockStatus === 'false') {
        if (checkStatus) {
            $('.uploadData').hide();
        } else {
            $('.uploadData').show();
        }
    }

}


function deletepopup(sw) {
    if (sw == 1) {
        // Show popup
        document.getElementById('blackcover').style.visibility = 'visible';
        document.getElementById('dpopupbox').style.visibility = 'visible';
        document.getElementById('blackcover').style.display = 'block';
        document.getElementById('dpopupbox').style.display = 'block';
    } else {
// Hide popup
        document.getElementById('blackcover').style.visibility = 'hidden';
        document.getElementById('dpopupbox').style.visibility = 'hidden';
        document.getElementById('blackcover').style.display = 'none';
        document.getElementById('dpopupbox').style.display = 'none';
    }
}