/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function cancelOrganization(){
    document.addorganizationfrm.action = "organizationWorkList.htm";
    document.addorganizationfrm.submit();
}

function backFrmViewOrganization(){
    document.vieworganizationfrm.action = "organizationWorkList.htm";
    document.vieworganizationfrm.submit();
}

function onBoareProcess(){
    document.vieworganizationfrm.action = "onBoardingProcess.htm";
    document.vieworganizationfrm.submit();
}


/*validation for add organizatin */

$(document).ready(function() {
    $("#mo").addClass("ux-vnav-selected");

    $("#mrandp").addClass("ux-vnav-submenu-closed");
    $("#mrandp").removeClass("ux-vnav-has-selected ");
    $("#mu").removeClass("ux-vnav-selected");
    $("#ua").removeClass("ux-vnav-selected");

    var organizationNameOnload = $("#organizationName").val();

    $("#saveOrganization").click(function() {

        var orgValidationStatus = $.fn.saveOrganization();
        var orgSourceValidationStatus = $.fn.saveAuthoritativeSource();
        var validateOrganization = $.fn.validateOrganization();

        if (orgValidationStatus && orgSourceValidationStatus && validateOrganization) {
           var val= $("#organizationTypeKey option:selected").text()
           $("#organizationTypeName").val(val);
            document.saveOrganization.action = "saveOrganization.htm";
            document.saveOrganization.submit();
        }
    });

    $("#updateOrganization").click(function() {
        var validateOrganization = true;
        var organizationName = $("#organizationName").val();

        var orgValidationStatus = $.fn.saveOrganization();
        var orgSourceValidationStatus = $.fn.saveAuthoritativeSource();

        if (organizationName.toUpperCase() === organizationNameOnload.toUpperCase()) {

            validateOrganization = true;

        } else {
            validateOrganization = $.fn.validateOrganization();

        }
        if (orgValidationStatus && orgSourceValidationStatus && validateOrganization) {
            $('#organizationTypeKey').removeAttr('disabled');
            document.saveOrganization.action = "saveOrganization.htm";
            document.saveOrganization.submit();
        }
    });



    var organizationName = $("#organizationName").val();

    $("#status2").click(function()
    {

        $("#reason").css({"display": "block"})

    });
    $("#status1").click(function()
    {
        $("#deactiveReason").val("");
        $("#reason").css({"display": "none"})
    });

    var count = $("#listSize").val();


    $('.add').click(function() {
        var srcDropDownValues;
        $("#authorativeSourceKey0 option").each(function() {
            srcDropDownValues += "<option  value='" + $(this).val() + "' title='"+$(this).text()+"'>" + $(this).text() + "</option>";
        });
        var authoritativeHtml = "<div class='labeltype ux-hform ux-margin-top-none ux-margin-left-none' id='label" + count + "'>  <dl><dt></dt><dd><select name='sourceKeyArray' onchange='$.fn.autrotativeSource(" + count + ")' id='authorativeSourceKey" + count + "' >" + srcDropDownValues + "</select>  <label id='errorauthorativeSourceKey" + count + "' class='error' style='color: red;float:left'></label> <div class='sourceText ux-margin-top-min' id='sourceText" + count + "'></div> </dd></dl><div class='sourceTextLabel" + count + "' id='sourceTextID" + count + "'></div><dl><dt></dt><dd><input type='text' name='sourceClientIDArray' id='sourceOrganizationKey" + count + "' value='' placeholder=''/><label id='errorsourceOrganizationKey" + count + "' class='error' style='color: red;float:left'></label> </dd></dl><div class='remove ux-float-left ux-margin-top-min'><a href='javascript:$.fn.removeAuthorotativeSource (" + count + ");'><img src='images/Delete_c.png' title='Delete'/></a></div>    </div></div>";
        $('.labeltype:last').before(authoritativeHtml);
        count++;
    });
});
$.fn.saveOrganization = function() {
    var flagValidTotal = new Array();
    var returnstring = false;

//    var parentOrganizationName = $.trim($("#parentOrganizationName").val());
    var organizationName = $.trim($("#organizationName").val());
    var organizationTypeKey = $("#organizationTypeKey").val();
    var organizationIndustryKey = $("#organizationIndustryKey").val();
    var countryName = $("#countryName").val();
    var stateName = $("#stateName").val();
    var streetAddress1 = $.trim($("#streetAddress1").val());
    var streetAddress2 = $.trim($("#streetAddress2").val());
    var cityName = $.trim($("#cityName").val());
    var postalCode = $.trim($("#postalCode").val());
    var organizationDescription = $.trim($("#organizationDescription").val());
    var status = $("#status").val();
    var statusValue = $("input[name=rowStatusKey]:checked").val();
    var deactiveReason = $.trim($("#deactiveReason").val());

    var alpahaReg = /^[A-Za-z\s]+$/;

//    if (jQuery.trim(parentOrganizationName).length > 100) {
//        $("#errorParentOrganizationName").html("Parent Organization Name cannot exceed 100 characters");
//         $("#parentOrganizationName").addClass("ux-form-field-error");
//        flagValidTotal.push(false);
//    } else {
//        flagValidTotal.push(true);
//        $("#errorParentOrganizationName").html("");
//        $("#parentOrganizationName").removeClass("ux-form-field-error");
//    }

    if (organizationName == '') {
        $("#errorOrganizationName").html("Please enter Organization Name");
        $("#organizationName").addClass("ux-form-field-error");
        flagValidTotal.push(false);
    } else if (jQuery.trim(organizationName).length > 100) {
        $("#errorOrganizationName").html("Organization Name cannot exceed 100 characters");
        $("#organizationName").addClass("ux-form-field-error");
        flagValidTotal.push(false);
    } else {
        flagValidTotal.push(true);
        $("#errorOrganizationName").html("");
        $("#organizationName").removeClass("ux-form-field-error");
    }

    if (organizationTypeKey == 0) {
        $("#errorOrganizationTypeKey").html("Please select Organization Type");
        $("#organizationTypeKey").addClass("ux-form-field-error");
        flagValidTotal.push(false);
    } else {
        flagValidTotal.push(true);
        $("#errorOrganizationTypeKey").html("");
        $("#organizationTypeKey").removeClass("ux-form-field-error");
    }

    if (organizationIndustryKey == 0) {
        $("#errorOrganizationIndustryKey").html("Please select Organization Industry");
        $("#organizationIndustryKey").addClass("ux-form-field-error");
        flagValidTotal.push(false);
    } else {
        flagValidTotal.push(true);
        $("#errorOrganizationIndustryKey").html("");
        $("#organizationIndustryKey").removeClass("ux-form-field-error");
    }

    if (countryName == '') {
        $("#errorCountryName").html("Please select Country");
        $("#countryName").addClass("ux-form-field-error");
        flagValidTotal.push(false);
    } else {
        flagValidTotal.push(true);
        $("#errorCountryName").html("");
        $("#countryName").removeClass("ux-form-field-error");
    }

    if (stateName == '') {
        $("#errorStateName").html("Please select State");
        $("#stateName").addClass("ux-form-field-error");
        flagValidTotal.push(false);
    } else {
        flagValidTotal.push(true);
        $("#errorStateName").html("");
        $("#stateName").removeClass("ux-form-field-error");
    }

    if (streetAddress1 == '') {
        $("#errorStreetAddress1").html("Please enter Street Address Line 1");
        $("#streetAddress1").addClass("ux-form-field-error");
        flagValidTotal.push(false);
    } else if (jQuery.trim(streetAddress1).length > 100) {
        $("#errorStreetAddress1").html("Street Address Line 1 cannot exceed 100 characters");
        $("#streetAddress1").addClass("ux-form-field-error");
        flagValidTotal.push(false);
    } else {
        flagValidTotal.push(true);
        $("#errorStreetAddress1").html("");
        $("#streetAddress1").removeClass("ux-form-field-error");
    }

    if (streetAddress2 == '') {
        $("#streetAddress2").val('');
    } else if (jQuery.trim(streetAddress2).length > 100) {
        $("#errorStreetAddress2").html("Street Address Line 2 cannot exceed 100 characters");
        $("#streetAddress2").addClass("ux-form-field-error");
        flagValidTotal.push(false);
    } else {
        flagValidTotal.push(true);
        $("#errorStreetAddress2").html("");
        $("#streetAddress2").removeClass("ux-form-field-error");
    }


    if (cityName == '') {
        $("#errorCityName").html("Please enter city");
        $("#cityName").addClass("ux-form-field-error");
        flagValidTotal.push(false);
    } else if (!cityName.match(alpahaReg)) {
        $("#errorCityName").html("City only accepts alphabets");
        $("#cityName").addClass("ux-form-field-error");
        flagValidTotal.push(false);
    } else if (jQuery.trim(cityName).length > 100) {
        $("#errorCityName").html("City cannot exceed 100 characters");
        $("#cityName").addClass("ux-form-field-error");
        flagValidTotal.push(false);
    } else {
        flagValidTotal.push(true);
        $("#errorCityName").html("");
        $("#cityName").removeClass("ux-form-field-error");
    }

    if (postalCode == '') {
        $("#errorPostalCode").html("Please enter ZIP Code");
        $("#postalCode").addClass("ux-form-field-error");
        flagValidTotal.push(false);
    } else if (jQuery.trim(postalCode).length > 10) {
        $("#errorPostalCode").html("ZIP Code cannot exceed 10 characters");
        $("#postalCode").addClass("ux-form-field-error");
        flagValidTotal.push(false);
    } else if (!jQuery.trim(postalCode).match(/^[a-zA-Z0-9\s]+$/)) {
        $("#errorPostalCode").html("Zip Code only accepts alphanumeric characters");
        $("#postalCode").addClass("ux-form-field-error");
        flagValidTotal.push(false);
    } else {
        flagValidTotal.push(true);
        $("#errorPostalCode").html("");
        $("#postalCode").removeClass("ux-form-field-error");
    }

    if (organizationDescription == '') {
        $("#organizationDescription").val(' ');
    } else if (jQuery.trim(organizationDescription).length > 1000) {
        $("#errorOrganizationDescription").html("Notes cannot exceed 1000 characters");
        $("#organizationDescription").addClass("ux-form-field-error");
        flagValidTotal.push(false);
    } else {
        flagValidTotal.push(true);
        $("#errorOrganizationDescription").html("");
        $("#organizationDescription").removeClass("ux-form-field-error");
    }
    if (statusValue == 2) {
        if (deactiveReason === '') {
            $("#errordeactiveReason").html("Please enter Reason for Deactivation");
            $("#deactiveReason").addClass("ux-form-field-error");
            flagValidTotal.push(false);
        } else if (jQuery.trim(deactiveReason).length > 1000) {
            $("#errordeactiveReason").html("Reason for Deactivation cannot exceed 1000 characters");
            $("#deactiveReason").addClass("ux-form-field-error");
            flagValidTotal.push(false);
        } else {
            flagValidTotal.push(true);
            $("#errordeactiveReason").html("");
            $("#deactiveReason").removeClass("ux-form-field-error");
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

$.fn.saveAuthoritativeSource = function() {
    var flagValidTotal = new Array();
    var returnstring = false;

    var retValSourceTextDupicate = $.fn.findAuthoritativeSourceTextDuplicates();
    var retValSourceDropDownDupicate = $.fn.findAuthoritativeSourceDropDownDuplicates();
    if (retValSourceTextDupicate && retValSourceDropDownDupicate) {
        $("#errorGlobalAothoritativeSource").html("");
    } else {
        flagValidTotal.push(false);
    }

    $('#sourceValidationID select').each(
            function(index) {
                var input = $(this);
                var inputName = input.attr('name');
                var inputVal = input.val();
                var inputID = input.attr('id');
                var selectValue = $("#" + inputID).val();
                if (selectValue == 0) {
                    $("#error" + inputID).html("Please select Authoritative Source");
                    $("#" + inputID).addClass("ux-form-field-error");
                    flagValidTotal.push(false);
                } else {
                    flagValidTotal.push(true);
                    $("#error" + inputID).html("");
                    $("#" + inputID).removeClass("ux-form-field-error");
                }
            }
    );


    $("#sourceValidationID input[type='text'][name='sourceClientIDArray'] ").each(
            function(index) {
                var input = $(this);
                var inputName = input.attr('name');
//                var inputVal = input.val();
                var inputID = input.attr('id');
                var inputValue = $.trim($("#" + inputID).val());
                if (inputName == 'sourceClientIDArray') {
                    if (inputValue == '') {
                        $("#" + inputID).val(' ');
                    }

                    if (jQuery.trim(inputValue).length > 100) {
                        $("#error" + inputID).html("Source Organization ID cannot exceed 100 characters");
                        $("#" + inputID).addClass("ux-form-field-error");
                        flagValidTotal.push(false);
                    } else {
                        flagValidTotal.push(true);
                        $("#error" + inputID).html("");
                        $("#" + inputID).removeClass("ux-form-field-error");
                    }
                }

            }
    );


    $("#sourceValidationID input[type='text'][name='sourceTextArray'] ").each(
            function(index) {
                var input = $(this);
                var inputName = input.attr('name');
                var inputID = input.attr('id');
                var inputValue = $.trim($("#" + inputID).val());
                if (inputName == 'sourceTextArray') {
                    if (inputValue == '') {
                        $("#error" + inputID).html("Please enter the Authority Source Name");
                        $("#" + inputID).addClass("ux-form-field-error");
                        flagValidTotal.push(false);
                    } else if (jQuery.trim(inputValue).length > 100) {
                        $("#error" + inputID).html("Authoritative Source cannot exceed 100 characters");
                        $("#" + inputID).addClass("ux-form-field-error");
                        flagValidTotal.push(false);
                    } else {
                        $("#error" + inputID).html("");
                        $("#" + inputID).removeClass("ux-form-field-error");
                        if (retValSourceTextDupicate && retValSourceDropDownDupicate) {
                            var retVal = $.fn.validateAuthoritativeSource(inputValue, inputID);
                            if (retVal) {
                                flagValidTotal.push(true);
                            } else {
                                flagValidTotal.push(false);
                            }
                        }
                    }
                }

            }
    );




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


$.fn.fetchStatesByCountry = function(country) {
    var options = "<option value=''>Select</option>";
    if (true) {
//        alert(country);
        jQuery.ajax({
            type: "POST",
            contentType: "application/json",
            url: "fectchStates.htm?country=" + country,
            datatype: "json",
            async: false,
            success: function(data) {
                for (var i = 0; i < data.length; i++) {
                    options += "<option value='" + data[i] + "'>" + data[i] + "</option>";
                }
                $("#stateName").html(options);
            },
            error: function(e) {
//                alert("Error - Session Expired/No Result");
            }
        });
    }
    else {

    }
}


$.fn.removeAuthorotativeSource = function(id) {
    if (confirm("Are you sure you want to remove")) {
        $('#label' + id).remove();
    }
}

$.fn.sourceTextRemoveFunction = function(id) {
    $('#sourceTextDivID' + id).remove();
}



$.fn.autrotativeSource = function(id) {
    var selectValue = $("#authorativeSourceKey" + id).val();
    if (selectValue != -1) {
        $.fn.sourceTextRemoveFunction(id);
    } else {
        $.fn.sourceTextRemoveFunction(id);
        var sourceText = "<div id='sourceTextDivID" + id + "'><dl><dd><input type='text' value=''  name='sourceTextArray' id='authorativeSource" + id + "'><label id='errorauthorativeSource" + id + "' class='error' style='color: red;float:left'></label> </dd></dl></div>";
        $('#sourceText' + id).after(sourceText);
    }

};

$.fn.validateOrganization = function() {
    var flag = true;
    var organization = $.trim($("#organizationName").val());
    if (organization != '') {
        jQuery.ajax({
            type: "POST",
            contentType: "text/plain",
            async: false,
            url: "validateOrganization.htm?organization=" + organization,
            datatype: "text",
            success: function(data) {
                if (data !== "No") {
                    flag = false;
                    $("#errorOrganizationName").html("");
                    $("#errorOrganizationName").html(data);
                } else {
                    flag = true;
//                    $("#errorOrganizationName").html("");
                }
            },
            error: function(e) {
                alert("Error - Session Expired/No Result");
            }
        });
    }
    return flag;
};


$.fn.validateAuthoritativeSource = function(source, sourceID) {
    var flag = true;
    if (flag) {
        jQuery.ajax({
            type: "POST",
            contentType: "text/plain",
            url: "validateAuthoritativeSource.htm?authoritativeSource=" + source,
            async: false,
            datatype: "text",
            success: function(data) {
                if (data !== "No") {
                    flag = false;
                    $("#error" + sourceID).html(data);
                    $("#" + sourceID).addClass("ux-form-field-error");
                } else {
                    $("#error" + sourceID).html("");
                    $("#" + sourceID).removeClass("ux-form-field-error");
                    flag = true;
                }
            },
            error: function(e) {
                alert("Error - Session Expired/No Result");
            }
        });
    }
    return flag;
};




$.fn.findAuthoritativeSourceTextDuplicates = function() {
    var value = true;
    var count = 0;
    var valuesOne = $('input[name="sourceTextArray"]').map(function() {
        return this.value
    }).get()
    var valuesTwo = valuesOne;

    if (count == 0) {
        for (var i = 0; i < valuesOne.length; i++) {
            if (valuesOne[i].length > 0) {
                for (var j = 0; j < valuesTwo.length; j++) {
                    if (i != j) {
                        if (valuesOne[i].toUpperCase() == valuesTwo[j].toUpperCase()) {
                            $("#errorGlobalAothoritativeSource").html("Authority Source Name already exists");
                            count++;
                            value = false;
                            break;
                        }
                    }
                }
                if (!value) {
                    break;
                }
            }
        }
    }
//    if (value) {
//        $("#errorGlobalAothoritativeSource").html("");
//    }
    return value;
};

$.fn.findAuthoritativeSourceDropDownDuplicates = function() {
    var value = true;
    var count = 0;
    var valuesOne = $('select[name="sourceKeyArray"]').map(function() {
        return this.value
    }).get()
    var valuesTwo = valuesOne;


    if (count == 0) {
        for (var i = 0; i < valuesOne.length; i++) {
            if (valuesOne[i] != 0 && valuesOne[i] != -1) {
                for (var j = 0; j < valuesTwo.length; j++) {
                    if (i != j) {
                        if (valuesOne[i] == valuesTwo[j]) {
                            $("#errorGlobalAothoritativeSource").html("Authority Source Name already exists");
                            count++;
                            value = false;
                            break;
                        }
                    }
                }
                if (!value) {
                    break;
                }
            }
        }
    }
//    if (value) {
//        $("#errorGlobalAothoritativeSource").html("");
//    }
    return value;
};