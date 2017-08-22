/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* 
 Created on : May 03, 2016, 3:47:11 PM
 Author     : sbhagavatula
 */
var serData;
//var validationCount = 0;

var validationArr = new Array();
var partnerUsersCount = 0;


function popupEngagement(sw) {
    if (sw == 1) {
        // Show popup
        document.getElementById('blackcover').style.visibility = 'visible';
        document.getElementById('popupbox1').style.visibility = 'visible';
        document.getElementById('blackcover').style.display = 'block';
        document.getElementById('popupbox1').style.display = 'block';
    } else {
// Hide popup
        document.getElementById('blackcover').style.visibility = 'hidden';
        document.getElementById('popupbox1').style.visibility = 'hidden';
        document.getElementById('blackcover').style.display = 'none';
        document.getElementById('popupbox1').style.display = 'none';
    }
}

$(document).ready(function() {
    
    $("#mu").removeClass("ux-vnav-selected");
    $("#mo").removeClass("ux-vnav-selected");
    $("#mrandp").addClass("ux-vnav-submenu-closed");
    $("#mrandp").removeClass("ux-vnav-has-selected ");
    $("#ua").removeClass("ux-vnav-selected");
    $("#emwl").addClass("ux-vnav-submenu-open");
    $("#emwl").addClass("ux-vnav-selected");
    $("#meng").addClass("ux-vnav-selected");

    $("#agreementDateID").datepicker({
        dateFormat: 'mm/dd/yyyy',
        buttonImage: '<img src="images/calendar01.gif" alt="Calendar" title="Calendar" class="trigger ux-margin-left-min ux-type-midline">',
        onSelect: function(dates) {
            clientEngagementCode();
        }
    });
    
    //Activate the engagement
    $(".eng-activate").click(function() {
        document.activateform.action = "activateengagement.htm";
        document.activateform.submit();
    });
    
    //Edit the engagement
    $(".edit-engagement").click(function() {
        document.activateform.action = "updateengagement.htm";
        document.activateform.submit();
    });

    //Save Action
    $(".addEngmtBtn").click(function() {
        if (nicEditors.findEditor("engagementStatusID").getContent() === '<br>')
        {  
            $("#engagementComment").val("");
        } else {
            $("#engagementComment").val(nicEditors.findEditor("engagementStatusID").getContent());
        }
        if (saveEngagementFormValidate()) {
            document.saveEngagementForm.action = "saveengagement.htm";
            document.saveEngagementForm.submit();
        }
    });

    //Update Action
    $(".updateEngmtBtn").click(function() {
        if (nicEditors.findEditor("engagementStatusID").getContent() === '<br>')
        {
            $("#engagementComment").val("");
        } else {
            $("#engagementComment").val(nicEditors.findEditor("engagementStatusID").getContent());
        }
        if (updateEngagementFormValidate()) {
            document.saveEngagementForm.action = "engagementupdate.htm";
            document.saveEngagementForm.submit();
        }
    });

    //Delete Action
    $(".deleteEngmtBtn").click(function() {
//        alert("cal popup");
        //document.saveEngagementForm.action = "deleteengagement.htm";
      //  document.saveEngagementForm.submit();
       javascript:popup(1);
    });
    $(".delete").click(function() {
//        alert("hh");
        document.saveEngagementForm.action = "deleteengagement.htm";
      document.saveEngagementForm.submit();
       
    });
      
    $(".cancelbtn").click(function() {
        document.saveEngagementForm.action = "engagementlist.htm";
        document.saveEngagementForm.submit();
    });

    //Begin: Onchange function for Products
    $("#product").change(function() {
        var headCheckbox = document.getElementById('headCheckbox');
        headCheckbox.checked = false;
        $('#services1').empty();
        fetchServicesByProd();
    });

    $.fn.fnForPrdChange = function(){
        var headCheckbox = document.getElementById('headCheckbox');
        headCheckbox.checked = false;
        $('#services1').empty();
        
        fetchServicesByProd();
        clientEngagementCode();
    }
    //End: Onchange function for Products
    
    //Begin: Onchange function for Products at Validation
    $.fn.fnForPrdChangeValid = function(){
        var headCheckbox = document.getElementById('headCheckbox');
        headCheckbox.checked = false;
        $('#services1').empty();
        fetchServicesByProd();
        clientEngagementCode();
    }
    //End: Onchange function for Products at Validation


    //Begin: Function to fetch services based on selected Product
    function fetchServicesByProd()
    {
        var pkgKey = $('select#product option:selected').val();
        if (pkgKey !== '0') {
            $("#headCheckbox").removeAttr("disabled");
            jQuery.ajax({
                type: "POST",
                contentType: "application/json",
                url: "fetchservices.htm?packageKey=" + pkgKey,
                datatype: "json",
                async: false,
                success: function(data) {
                    serData = data;
                    $('#prodServices').empty(); //remove all child nodes
                    for (var i = 0; i < data.length; i++) {
                        var serviceKey = data[i].securityServiceCode;
                        var serviceName = data[i].securityServiceName;

                        var html = '<tr >';
                        $('tr:even').addClass('ux-tabl-alt-row');
                        html += '<td><input type="checkbox" id="' + serviceKey + '" class="checkboxGroup" onchange="serviceSelected(this)"/></td>';
                        html += '<td>' + serviceName + '</td>';
                        html += '<td valign="top"><input type="text" readonly value="" id="sd' + serviceKey + '" class="defaultActualPicker" name="engagementServiceLi[' + i + '].serviceStartDate"/><label id="errorSD' + serviceKey + '" class="ux-msg-error-under"></label></td>';
                        html += '<td valign="top"><input type="text" readonly value="" id="ed' + serviceKey + '" class="defaultActualPicker" name="engagementServiceLi[' + i + '].serviceEndDate"/><label id="errorED' + serviceKey + '" class="ux-msg-error-under"></label></td>';
                        html += '</tr>';
                        html += '<input type="hidden" name="engagementServiceLi[' + i + '].securityServiceCode" value="' + serviceKey + '"/>';
                        var newOption = $(html);
                        $('#prodServices').append(newOption);
                    }
                },
                error: function(e) {
                    alert("Error - Session Expired/No Result");
                }
            });
            $('.defaultActualPicker').datepicker({
                defaultDate: new Date(), buttonImage: '<img src="images/calendar01.gif" alt="Calendar" title="Calendar" class="trigger ux-margin-left-min ux-type-midline">'});


        } else {
            $('#prodServices').empty();
            $("#headCheckbox").attr("disabled", true);
        }
    }
    //End: Function to fetch services based on selected Product

    $('.fixme').fixheadertable({
        //caption: 'Security Packages & Services',
        colratio: [50, 400, 180, 180],
        resizeCol: true,
    });

    //Begin: Add System Source
    var sourceCount = 0;
    var loopCount = $("#sourceSize").val();
    if (loopCount == 0) {
        loopCount = 1;
        sourceCount = 2;
        validationArr.push(0);
    } else {
        sourceCount = +loopCount + 1;
        for (var k = 0; k < loopCount; k++) {
            validationArr.push(k);
        }
    }

    $('.addsource').click(function() {
        var srcDropDownValues = "";
        $("#ss0 option").each(function() {
            srcDropDownValues += "<option value='" + $(this).val() + "'>" + $(this).text() + "</option>";
        });
        var authoritativeHtml = "<div id=source" + sourceCount + "><dl><dt><label>Source System " + sourceCount + ": <span>*</span></label></dt>";
        authoritativeHtml += "<dd><select  class='ux-width-33t' id='ss" + loopCount + "' name='clientEngagementSourceLi[" + loopCount + "].sourceKey' >" + srcDropDownValues + "</select>";
        authoritativeHtml += "&nbsp;&nbsp; <a href='javascript:$.fn.removeSourceSystem (" + sourceCount + ");'><img src='images/Delete_c.png' title='Delete'/></a>";
        authoritativeHtml += "<br/><label id='errorSrcSystemID" + loopCount + "' class='ux-msg-error-under'></label></dd></dl>";
        authoritativeHtml += "<dl><dt><label>Source Project ID " + sourceCount + ": <span>*</span></label></dt>";
        authoritativeHtml += "<dd><input type='text' id='sp" + loopCount + "' name='clientEngagementSourceLi[" + loopCount + "].clientEngSourceCode' class='ux-width-32t' value='' onkeyup='limiter(this, 100)'/>";
        authoritativeHtml += "<br/><label id='errorSrcProjID" + loopCount + "' class='ux-msg-error-under'></label></dd></dl></div>";

        $('.sourceid:last').before(authoritativeHtml);

        validationArr.push(loopCount);

        sourceCount++;
        loopCount++;
        //validationCount = loopCount;
    });
    //End: Add System Source

    //Begin: Delete added System Source
    $.fn.removeSourceSystem = function(id) {
        $('#source' + id).remove();
        var newArr = new Array();
        for (var i = 0; i <= validationArr.length - 1; i++) {
            newArr.push(validationArr[i]);
        }
        validationArr = new Array();
        var newId = id;
        newId = newId - 1;
        for (var i = 0; i <= newArr.length - 1; i++) {
            if (newArr[i] != newId) {
                validationArr.push(newArr[i]);
            }
        }

        //sourceCount--;
        //loopCount--;
        //validationCount = loopCount;
    }

    function removeByValue(arr, val) {
        for (var i = 0; i < arr.length; i++) {
            if (arr[i] == val) {
                arr.splice(i, 1);
                break;
            }
        }
    }

    if (!Array.prototype.remove) {
        Array.prototype.remove = function(val) {
            var i = this.indexOf(val);
            return i > -1 ? this.splice(i, 1) : [];
        };
    }
    //End: Delete added System Source


});

//Function for selected services
function serviceSelected(rowCheckbox)
{
    var checkboxResult = [];
    var selectedCheckboxes = [];
    var selService = "";
    var sep = "";
    $('#selectedServices').val("");
    var headCheckbox = document.getElementById('headCheckbox');
    $('.checkboxGroup').each(function() {
        var id = $(this).attr('id');
        if (this.checked) {
            selectedCheckboxes.push(id);
            selService += sep;
            selService += id
            sep = ",";
            $('#selectedServices').val(selService);
        } else {
            $("#sd" + id).removeClass("ux-form-field-error");
            $("#errorSD" + id).html("");
            flagValidTotal.push(false);

            $("#ed" + id).removeClass("ux-form-field-error");
            $("#errorED" + id).html("");
            flagValidTotal.push(false);
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



function checkAllServices(headCheckbox) {
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
                if(checkboxes[i].disabled != true){
                    checkboxes[i].checked = false;
                }
            }
        }
    }
    var selectedCheckboxes = [];
    var selService = "";
    var sep = "";
    $('.checkboxGroup').each(function() {
        var id = $(this).attr('id');
        if (this.checked) {
            selectedCheckboxes.push(id);

            selService += sep;
            selService += id
            sep = ",";
            $('#selectedServices').val(selService);
        }
    });
}



$(function() {
    $('.defaultActualPicker').datepicker({
        defaultDate: new Date(), buttonImage: '<img src="images/calendar01.gif" alt="Calendar" title="Calendar" class="trigger ux-margin-left-min ux-type-midline">'
    });
});

function clientEngagementCode() {

    var prdCode = "";
    var newDate = "";

    var selectedClientText = $("#clientID option:selected").text();
    var parsedClientText = selectedClientText.replace(/ /g, '');
    var clientCode = parsedClientText.substring(0, 3);
    
    var selectedPrdText = $("#product option:selected").val();
    if (selectedPrdText != '0') {
        var parsedPrdText = selectedPrdText.replace(/ /g, '');
        prdCode = parsedPrdText.substring(0, 3);
    }

    var selectedDateText = $("#agreementDateID").val();
    if (selectedDateText != "") {
        var dateAr = selectedDateText.split('/');
        newDate = dateAr[2] + '' + dateAr[0] + '' + dateAr[1];
    }

    if (clientCode != "" && prdCode != "" && newDate != "") {
        $('#engagementCodeID').val(clientCode.toUpperCase() + "-" + $("#clientID option:selected").val() + "-" + prdCode.toUpperCase() + "-" + newDate);
    }
}

//Save Engagement Validation
var flagValidTotal = new Array();
var returnstring = false;
function saveEngagementFormValidate() {
    flagValidTotal = new Array();
    var alphaNum = /^[A-Za-z0-9_@./#&+-\s]*$/;

    //alert(validationCount);
    if (validationArr.length == 0) {
        if ($.trim($('select#ss0 option:selected').val()) != 0) {
            flagValidTotal.push(true);
            $("#ss0").removeClass("ux-form-field-error");
            $("#errorSrcSystemID0").html("");
        } else {

            $("#ss0").addClass("ux-form-field-error");
            $("#errorSrcSystemID0").html("Please select Source System");
            flagValidTotal.push(false);
        }

        if ($.trim($('#sp0').val()) != 0) {
            flagValidTotal.push(true);
            $("#sp0").removeClass("ux-form-field-error");
            $("#errorSrcProjID0").html("");
        } else {

            $("#sp0").addClass("ux-form-field-error");
            $("#errorSrcProjID0").html("Please enter Source Project ID");
            flagValidTotal.push(false);
        }
    }

    for (var i = 0; i < validationArr.length; i++) {
        if ($.trim($('select#ss' + validationArr[i] + ' option:selected').val()) != 0) {
            flagValidTotal.push(true);
            $("#ss" + validationArr[i]).removeClass("ux-form-field-error");
            $("#errorSrcSystemID" + i).html("");
        } else {

            $("#ss" + validationArr[i]).addClass("ux-form-field-error");
            $("#errorSrcSystemID" + validationArr[i]).html("Please select Source System");
            flagValidTotal.push(false);
        }

        if ($.trim($('#sp' + validationArr[i]).val()) != 0) {
            flagValidTotal.push(true);
            $("#sp" + validationArr[i]).removeClass("ux-form-field-error");
            $("#errorSrcProjID" + validationArr[i]).html("");
        } else {

            $("#sp" + validationArr[i]).addClass("ux-form-field-error");
            $("#errorSrcProjID" + validationArr[i]).html("Please enter Source Project ID");
            flagValidTotal.push(false);
        }
    }

    if ($('select#clientID option:selected').val().length != 0)
    {
        if ($.trim($('select#clientID option:selected').val()) != 0) {
            flagValidTotal.push(true);
            $("#clientID").removeClass("ux-form-field-error");
            $("#errorClientID").html("");
        } else {

            $("#clientID").addClass("ux-form-field-error");
            $("#errorClientID").html("Please select Client Name");
            flagValidTotal.push(false);
        }
    }

    if ($('select#product option:selected').val().length != 0)
    {
        if ($.trim($('select#product option:selected').val()) != 0) {
            flagValidTotal.push(true);
            $("#product").removeClass("ux-form-field-error");
            $("#errorproduct").html("");
        } else {

            $("#product").addClass("ux-form-field-error");
            $("#errorproduct").html("Please select at least one Product");
            flagValidTotal.push(false);
        }
    }

    if ($.trim($('#agreementDateID').val()) != 0) {
        flagValidTotal.push(true);
        $("#agreementDateID").removeClass("ux-form-field-error");
        $("#errorAgreementDateID").html("");
    } else {

        $("#agreementDateID").addClass("ux-form-field-error");
        $("#errorAgreementDateID").html("Please select Agreement Date");
        flagValidTotal.push(false);
    }

    if ($.trim($("#engagementNameID").val()).length > 0) {

        if ($.trim($("#engagementNameID").val()).match(alphaNum)) {

            if ($.trim($("#engagementNameID").val()).length <= 100) {
                flagValidTotal.push(true);
                $("#engagementNameID").removeClass("ux-form-field-error");
                $("#errorEngagementNameID").html("");
            } else {
                $("#engagementNameID").addClass("ux-form-field-error");
                $("#errorEngagementNameID").html("Engagement Name cannot exceed 100 characters");
                flagValidTotal.push(false);
            }

        } else {
            $("#engagementNameID").addClass("ux-form-field-error");
            $("#errorEngagementNameID").html("Engagement Name only accepts alphabets");
            flagValidTotal.push(false);
        }

    } else {
        $("#engagementNameID").addClass("ux-form-field-error");
        $("#errorEngagementNameID").html("Please enter Engagement Name");
        flagValidTotal.push(false);
    }

    var agrdate = new Date($("#agreementDateID").val());
    var estartdate = new Date($("#estimatedStartDateID").val());
    var today = new Date();
    today.setHours(0, 0, 0, 0);
    if ($.trim($("#estimatedStartDateID").val()).length > 0) {
        
        if (estartdate < today) {
            $("#estimatedStartDateID").addClass("ux-form-field-error");
            $("#errorStartDateID").html("Estimated Start Date cannot be earlier than today's date");
            flagValidTotal.push(false);
        } else if (estartdate < agrdate){
            $("#estimatedStartDateID").addClass("ux-form-field-error");
            $("#errorStartDateID").html("Estimated Start Date cannot be earlier than Agreement Date");
            flagValidTotal.push(false);
        } else {
            flagValidTotal.push(true);
            $("#estimatedStartDateID").removeClass("ux-form-field-error");
            $("#errorStartDateID").html("");
        }

    } else {
        $("#estimatedStartDateID").addClass("ux-form-field-error");
        $("#errorStartDateID").html("Please select Estimated Start Date");
        flagValidTotal.push(false);
    }

    var eenddate = new Date($("#estimatedEndDateID").val());
    if ($.trim($("#estimatedEndDateID").val()).length > 0) {
        if (eenddate >= estartdate) {
            flagValidTotal.push(true);
            $("#estimatedEndDateID").removeClass("ux-form-field-error");
            $("#errorEndDateID").html("");
        } else {
            $("#estimatedEndDateID").addClass("ux-form-field-error");
            $("#errorEndDateID").html("Estimated End Date cannot be earlier than the Estimated Start Date");
            flagValidTotal.push(false);
        }

    } else {
        $("#estimatedEndDateID").addClass("ux-form-field-error");
        $("#errorEndDateID").html("Please select Estimated End Date");
        flagValidTotal.push(false);
    }

    var checkCount = 0;
    $('.checkboxGroup').each(function() {
        if (this.checked) {
            checkCount++;
            var id = $(this).attr('id');
            var stDate = new Date($('#sd' + id).val());
            var edDate = new Date($('#ed' + id).val());

            if ($.trim($('#sd' + id).val()).length > 0) {
                if (stDate < estartdate) {
                    $("#sd" + id).addClass("ux-form-field-error");
                    $("#errorSD" + id).html("Service Start Date cannot be earlier than Estimated Start Date");
                    flagValidTotal.push(false);
                } else if (stDate > eenddate) {
                    $("#sd" + id).addClass("ux-form-field-error");
                    $("#errorSD" + id).html("Service Start Date cannot be later than  Estimated End Date");
                    flagValidTotal.push(false);
                } else {
                    flagValidTotal.push(true);
                    $("#sd" + id).removeClass("ux-form-field-error");
                    $("#errorSD" + id).html("");
                }

            } else {
                $("#sd" + id).addClass("ux-form-field-error");
                $("#errorSD" + id).html("Please select Service Start Date");
                flagValidTotal.push(false);
            }

            if ($.trim($('#ed' + id).val()).length > 0) {
                if (edDate < stDate) {
                    $("#ed" + id).addClass("ux-form-field-error");
                    $("#errorED" + id).html("Service End Date cannot be earlier than the Estimated Start Date");
                    flagValidTotal.push(false);
                } else if (edDate > eenddate) {
                    $("#ed" + id).addClass("ux-form-field-error");
                    $("#errorED" + id).html("Service End Date cannot be later than  Estimated End Date");
                    flagValidTotal.push(false);
                } else {
                    flagValidTotal.push(true);
                    $("#ed" + id).removeClass("ux-form-field-error");
                    $("#errorED" + id).html("");
                }

            } else {
                $("#ed" + id).addClass("ux-form-field-error");
                $("#errorED" + id).html("Please select Service End Date");
                flagValidTotal.push(false);
            }
        }
    });

    if (checkCount == 0) {
        $("#errorServicesSelect").html("Please select at least one Service Name");
        flagValidTotal.push(false);
    } else {
        $("#errorServicesSelect").html("");
        flagValidTotal.push(true);
    }

    if ($.trim($($("#engagementComment").val()).text()).length <= 1000) {
        flagValidTotal.push(true);
        $("#engagementStatusID").removeClass("ux-form-field-error");
        $("#errorEngagementStatusID").html("");
    } else {
        $("#engagementStatusID").addClass("ux-form-field-error");
        $("#errorEngagementStatusID").html("Comments cannot exceed 1000 characters");
        flagValidTotal.push(false);
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

//Update Engagement Validation
var flagValidTotalUp = new Array();
var returnstringUp = false;
function updateEngagementFormValidate() {
    flagValidTotalUp = new Array();
    var alphaNum = /^[A-Za-z0-9_@./#&+-\s]*$/;

    //alert(validationArr);
    if (validationArr.length == 0) {
        if ($.trim($('select#ss0 option:selected').val()) != 0) {
            flagValidTotalUp.push(true);
            $("#ss0").removeClass("ux-form-field-error");
            $("#errorSrcSystemID0").html("");
        } else {

            $("#ss0").addClass("ux-form-field-error");
            $("#errorSrcSystemID0").html("Please select Source System");
            flagValidTotalUp.push(false);
        }

        if ($.trim($('#sp0').val()) != 0) {
            flagValidTotalUp.push(true);
            $("#sp0").removeClass("ux-form-field-error");
            $("#errorSrcProjID0").html("");
        } else {

            $("#sp0").addClass("ux-form-field-error");
            $("#errorSrcProjID0").html("Please enter Source Project ID");
            flagValidTotalUp.push(false);
        }
    }

    for (var i = 0; i < validationArr.length; i++) {
        if ($.trim($('select#ss' + validationArr[i] + ' option:selected').val()) != 0) {
            flagValidTotalUp.push(true);
            $("#ss" + validationArr[i]).removeClass("ux-form-field-error");
            $("#errorSrcSystemID" + validationArr[i]).html("");
        } else {

            $("#ss" + validationArr[i]).addClass("ux-form-field-error");
            $("#errorSrcSystemID" + validationArr[i]).html("Please select Source System");
            flagValidTotalUp.push(false);
        }

        if ($.trim($('#sp' + validationArr[i]).val()) != 0) {
            flagValidTotalUp.push(true);
            $("#sp" + validationArr[i]).removeClass("ux-form-field-error");
            $("#errorSrcProjID" + validationArr[i]).html("");
        } else {

            $("#sp" + validationArr[i]).addClass("ux-form-field-error");
            $("#errorSrcProjID" + validationArr[i]).html("Please enter Source Product ID");
            flagValidTotalUp.push(false);
        }
    }

    if ($.trim($("#engagementNameID").val()).length > 0) {

        if ($.trim($("#engagementNameID").val()).match(alphaNum)) {

            if ($.trim($("#engagementNameID").val()).length <= 100) {
                flagValidTotalUp.push(true);
                $("#engagementNameID").removeClass("ux-form-field-error");
                $("#errorEngagementNameID").html("");
            } else {
                $("#engagementNameID").addClass("ux-form-field-error");
                $("#errorEngagementNameID").html("Engagement Name cannot exceed 100 characters");
                flagValidTotalUp.push(false);
            }

        } else {
            $("#engagementNameID").addClass("ux-form-field-error");
            $("#errorEngagementNameID").html("Engagement Name only accepts alphabets");
            flagValidTotalUp.push(false);
        }

    } else {
        $("#engagementNameID").addClass("ux-form-field-error");
        $("#errorEngagementNameID").html("Please enter Engagement Name");
        flagValidTotalUp.push(false);
    }

    var estartdate = new Date($("#estimatedStartDateID").val());
    var today = new Date();
    
    var agrdate = new Date($("#agreementDateID").val());
    if ($.trim($("#estimatedStartDateID").val()).length > 0) {
        if (estartdate < agrdate){
            $("#estimatedStartDateID").addClass("ux-form-field-error");
            $("#errorStartDateID").html("Estimated Start Date cannot be earlier than Agreement Date");
            flagValidTotalUp.push(false);
        } else {
            flagValidTotalUp.push(true);
            $("#estimatedStartDateID").removeClass("ux-form-field-error");
            $("#errorStartDateID").html("");
        }
    } else {
        $("#estimatedStartDateID").addClass("ux-form-field-error");
        $("#errorStartDateID").html("Please select Engagement Start Date");
        flagValidTotalUp.push(false);
    }

    var eenddate = new Date($("#estimatedEndDateID").val());
    if ($.trim($("#estimatedEndDateID").val()).length > 0) {
        if (eenddate >= estartdate) {
            flagValidTotalUp.push(true);
            $("#estimatedEndDateID").removeClass("ux-form-field-error");
            $("#errorEndDateID").html("");
        } else {
            $("#estimatedEndDateID").addClass("ux-form-field-error");
            $("#errorEndDateID").html("Estimated End Date cannot be earlier than the  Estimated Start Date");
            flagValidTotalUp.push(false);
        }

    } else {
        $("#estimatedEndDateID").addClass("ux-form-field-error");
        $("#errorEndDateID").html("Please select  Estimated End Date");
        flagValidTotalUp.push(false);
    }

    var checkCount = 0;
    $('.checkboxGroup').each(function() {
        if (this.checked) {
            checkCount++;
            var id = $(this).attr('id');

            var stDate = new Date($('#sd' + id).val());
            var edDate = new Date($('#ed' + id).val());

            if ($.trim($('#sd' + id).val()).length > 0) {
                if (stDate < estartdate) {
                    $("#sd" + id).addClass("ux-form-field-error");
                    $("#errorSD" + id).html("Service Start Date cannot be earlier than Estimated Start Date");
                    flagValidTotalUp.push(false);
                } else if (stDate > eenddate) {
                    $("#sd" + id).addClass("ux-form-field-error");
                    $("#errorSD" + id).html("Service Start Date cannot be later than  Estimated End Date");
                    flagValidTotalUp.push(false);
                } else {
                    flagValidTotalUp.push(true);
                    $("#sd" + id).removeClass("ux-form-field-error");
                    $("#errorSD" + id).html("");
                }

            } else {
                $("#sd" + id).addClass("ux-form-field-error");
                $("#errorSD" + id).html("Please select Service Start Date");
                flagValidTotalUp.push(false);
            }

            if ($.trim($('#ed' + id).val()).length > 0) {
                if (edDate < stDate) {
                    $("#ed" + id).addClass("ux-form-field-error");
                    $("#errorED" + id).html("Service End Date cannot be earlier than the Estimated Start Date");
                    flagValidTotalUp.push(false);
                } else if (edDate > eenddate) {
                    $("#ed" + id).addClass("ux-form-field-error");
                    $("#errorED" + id).html("Service End Date cannot be later than  Estimated End Date");
                    flagValidTotalUp.push(false);
                } else {
                    flagValidTotalUp.push(true);
                    $("#ed" + id).removeClass("ux-form-field-error");
                    $("#errorED" + id).html("");
                }

            } else {
                $("#ed" + id).addClass("ux-form-field-error");
                $("#errorED" + id).html("Please select Service End Date");
                flagValidTotalUp.push(false);
            }
        }

    });

    if (checkCount == 0) {
        $("#errorServicesSelect").html("Please select at least one Service Name");
        flagValidTotalUp.push(false);
    } else {
        $("#errorServicesSelect").html("");
        flagValidTotalUp.push(true);
    }

    if ($.trim($($("#engagementComment").val()).text()).length <= 1000) {
        flagValidTotalUp.push(true);
        $("#engagementStatusID").removeClass("ux-form-field-error");
        $("#errorEngagementStatusID").html("");
    } else {
        $("#engagementStatusID").addClass("ux-form-field-error");
        $("#errorEngagementStatusID").html("Comments cannot exceed 1000 characters");
        flagValidTotalUp.push(false);
    }

    for (var i = 0; i < flagValidTotalUp.length; i++) {
        if (flagValidTotalUp[i] == false) {
            returnstringUp = flagValidTotalUp[i];
            flagValidTotalUp = new Array();
            break;
        } else {
            returnstringUp = true;
        }
    }
    return returnstringUp;
}

//Rich text editor
bkLib.onDomLoaded(function() {
    new nicEditor({buttonList: ['bold', 'italic', 'underline', 'left', 'center', 'right', 'justify', 'ol', 'ul',
            'subscript', 'superscript', 'strikethrough', 'removeformat', 'indent', 'outdent', 'hr', 'forecolor', 'bgcolor', 'link', 'unlink', 'fontSize', 'fontFamily', 'fontFormat']}).panelInstance('engagementStatusID');


});


