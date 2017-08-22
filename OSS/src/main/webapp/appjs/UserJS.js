/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function backToUserFn() {
    document.addusrfrm.action = "userlist.htm";
    document.addusrfrm.submit();
}

function backFrmViewFn() {
    document.viewusrfrm.action = "userlist.htm";
    document.viewusrfrm.submit();
}


function roleDelFn(roleKey) {
    $("#delRoleKeyID").val(roleKey);
    document.saveUserForm.action = "getUserDetails.htm";
    document.saveUserForm.submit();
}


function updateUserRoleDelFn(roleKey) {
    $("#delRoleKeyID").val(roleKey);
    document.editUserForm.action = "getUserDetails.htm";
    document.editUserForm.submit();
}
$(document).ready(function() {
    var userTypeKey= $("#userTypeID").val();
    
    $("#mu").addClass("ux-vnav-selected");
    $("#mo").removeClass("ux-vnav-selected");
    $("#mrandp").addClass("ux-vnav-submenu-closed");
    $("#mrandp").removeClass("ux-vnav-has-selected ");
    $("#ua").removeClass("ux-vnav-selected");

    $('.ux-tabl-data tbody tr:odd').addClass('ux-tabl-alt-row');

    $("#selectedRole").change(function() {
        fetchPermissionGrps();
    });

    $(".AddUserTypeKey").change(function() {
    
            var roleValues = $('[name="selectedRole"]').map(function() {
            return this.value
        }).get()
        if(roleValues.length>0){
                $("#userTypeID").val(userTypeKey);
          alert("User is associated with another user type. Please delete the roles assigned to change the user type.");
        }else{
//        fetchOrgByUserType();
        document.saveUserForm.action = "getUserDetails.htm";
        document.saveUserForm.submit();
    }
    });
    
    
       $(".UpdtaeUserTypeKey").change(function() {
            var roleValues = $('[name="selectedRole"]').map(function() {
            return this.value
        }).get()
        
        if(roleValues.length>0){
          $("#userTypeID").val(userTypeKey); 
          alert("User is associated with another user type. Please delete the roles assigned to change the user type.");
        }else{
//        fetchOrgByUserType();
        document.editUserForm.action = "getUserDetails.htm";
        document.editUserForm.submit();
    }
    });
    
    $(".Assignroleidbutton").click(function() {
        var id =$('select#appRoleKeyID option:selected').val();
        if (id > 0) {
            document.saveUserForm.action = "getUserDetails.htm";
            document.saveUserForm.submit();
        } else {
            $("#invalidRolecomboId").html(""); 
            $(".ms-choice").addClass("ms-choice-error");
            $("#errorSelectedRole").html("Please select at least one Role");
        }
    });
    
    $(".UpdateAssignroleidbutton").click(function() {
         var id =$('select#appRoleKeyID option:selected').val();
           if (id > 0) {
        document.editUserForm.action = "getUserDetails.htm";
        document.editUserForm.submit();
               } else {
                    $("#invalidRolecomboId").html("");  
            $(".ms-choice").addClass("ms-choice-error");
            $("#errorSelectedRole").html("Please select at least one Role");
        }
    });
    
    

    $("#userUpdateButton").click(function() {
        if (saveUserFormValidate()) {
            document.editUserForm.action = "edituser.htm";
            document.editUserForm.submit();
        }
    });

    $("#userAddButton").click(function() {
        if (saveUserFormValidate()) {
            if(validateUserName()){
            document.saveUserForm.action = "saveuser.htm";
            document.saveUserForm.submit();
            }
        }
    });
    
    $("#selectedRole").multipleSelect({
        filter: false,
        multiple: true,
        multipleWidth: 160,
        placeholder: "Select"
    });

    /*
     * For hiding global search from data table
     */
    $("#dataTable_filter").css({"display": "none"});


    /*Reason field*/
//    $("#addReason").css({"display": "block"})

    $("#deactive").click(function()
    {
        $("#addReason").show();

    });
    $("#active").click(function()
    {
        $("#addReason").hide();
        $("#deactiveReason").val("");
    });
    /*Reason field*/

    /*Reason field*/
    $("#newAddReason").hide();

    $("#deactive").click(function()
    {
        $("#editReason").show();
        $("#newAddReason").show();

    });
    $("#active").click(function()
    {
        $("#newAddReason").hide();
        $("#editReason").hide();
    });
    /*Reason field*/
});

function validateUserName()
{
    var flag = true;
    if (flag) {
        var userEmail = $.trim($("#userEmailId").val());
        jQuery.ajax({
            type: "POST",
            contentType: "text/plain",
            url: "validateusername.htm?userID=" + userEmail,
            datatype: "text",
            async: false,
            success: function(data) {
                if (data != "No") {
                    flag = false;
                    $("#errorUserEmailId").html(data);
                }
            },
            error: function(e) {
                alert("Error - Session Expired/No Result");
            }
        });
    }
    return flag;
}

function fetchOrgByUserType()
{
    var userType = $('select#userTypeID option:selected').val();
    if (userType !== '0') {
        jQuery.ajax({
            type: "POST",
            contentType: "application/json",
            url: "fetchOrgByUserType.htm?userTypeKey=" + userType,
            datatype: "json",
            async: false,
            success: function(data) {
                $('#partner').empty(); //remove all child nodes
                for (var i = 0; i < data.length; i++) {
                    var orgNm = data[i].organizationName;
                    var orgKey = data[i].organizationKey;
                    var newOption = $('<option title="' + orgNm + '" value="' + orgKey + '">' + orgNm + '</option>');
                    $('#partner').append(newOption);
                }
            },
            error: function(e) {
                alert("Error - Session Expired/No Result");
            }
        });
    } else {
        $('#partner').empty(); //remove all child nodes
        $('#partner').append('<option title="Select" value="0">Select</option>');
    }
}

function fetchPermissionGrps()
{
    var appRoleKey = $('#selectedRole').val();
    jQuery.ajax({
        type: "POST",
        contentType: "application/json",
        url: "fetchpermissiongroups.htm?appRoleKey=" + appRoleKey,
        datatype: "json",
        async: false,
        success: function(data) {
            $('#groupId').empty(); //remove all child nodes
            for (var i = 0; i < data.length; i++) {
                var groupNm = data[i].permissionGroupName;
                var groupDesc = data[i].permissionGroupDesc;
                var newOption = $('<tr><td>' + groupNm + '</td><td>' + groupDesc + '</td></tr>');
                $('#groupId').append(newOption);
            }
        },
        error: function(e) {
            alert("Error - Session Expired/No Result");
        }
    });

}


//Validations

var flagValidTotal = new Array();
var returnstring = false;
function saveUserFormValidate() {
    var reg = /^[0-9()-]+$/;
    var mailformat = /^\w+([.-_]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/;
    var alpahaReg = /^[A-Za-z\s]+$/;
    var mailCharExp = /^[A-Za-z0-9.-_@]+$/;
    var mailRegX = /^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$/;
    var alphaNum = /^[A-Za-z0-9_@./#&+-\s]*$/;

    var specialChars = "<>!#$%^&*()+[]{}?:;|'\"\\,/~`="
    var check = function(string) {
        for (i = 0; i < specialChars.length; i++) {
            if (string.indexOf(specialChars[i]) > -1) {
                //alert(specialChars[i]);
                return false;
            }
            if (string.indexOf(' ') >= 0) {
                //alert(specialChars[i]);
                return false;
            }
        }
        return true;
    }

    if ($('select#partner option:selected').val().length != 0)
    {
        if ($.trim($('select#partner option:selected').val()) != 0) {
            flagValidTotal.push(true);
            $("#partner").removeClass("ux-form-field-error");
            $("#errorOrgId").html("");
        } else {

            $("#partner").addClass("ux-form-field-error");
            $("#errorOrgId").html("Please select Organization Name");
            flagValidTotal.push(false);
        }
    }

    if ($('select#userTypeID option:selected').val().length != 0)
    {
        if ($.trim($('select#userTypeID option:selected').val()) != 0) {
            flagValidTotal.push(true);
            $("#userTypeID").removeClass("ux-form-field-error");
            $("#errorUserTypeId").html("");
        } else {

            $("#userTypeID").addClass("ux-form-field-error");
            $("#errorUserTypeId").html("Please select User Type");
            flagValidTotal.push(false);
        }
    }

    if ($.trim($("#userFirstNameId").val()).length > 0) {

        if ($.trim($("#userFirstNameId").val()).match(alpahaReg)) {

            if ($.trim($("#userFirstNameId").val()).length <= 100) {
                flagValidTotal.push(true);
                $("#userFirstNameId").removeClass("ux-form-field-error");
                $("#errorUserFirstNameId").html("");
            } else {
                $("#userFirstNameId").addClass("ux-form-field-error");
                $("#errorUserFirstNameId").html("First Name cannot exceed 100 characters");
                flagValidTotal.push(false);
            }

        } else {
            $("#userFirstNameId").addClass("ux-form-field-error");
            $("#errorUserFirstNameId").html("First Name only accepts alphabets");
            flagValidTotal.push(false);
        }

    } else {
        $("#userFirstNameId").addClass("ux-form-field-error");
        $("#errorUserFirstNameId").html("Please enter First Name");
        flagValidTotal.push(false);
    }


    if ($.trim($("#userMiddleNameId").val()).length > 0) {
        if ($.trim($("#userMiddleNameId").val()).match(alpahaReg)) {

            if ($.trim($("#userMiddleNameId").val()).length <= 100) {
                flagValidTotal.push(true);
                $("#userMiddleNameId").removeClass("ux-form-field-error");
                $("#errorUserMiddleNameId").html("");
            } else {
                $("#userMiddleNameId").addClass("ux-form-field-error");
                $("#errorUserMiddleNameId").html("Middle Name cannot exceed 100 characters");
                flagValidTotal.push(false);
            }

        } else {
            $("#userMiddleNameId").addClass("ux-form-field-error");
            $("#errorUserMiddleNameId").html("Middle Name only accepts alphabets");
            flagValidTotal.push(false);
        }
    } else {
        $("#userMiddleNameId").removeClass("ux-form-field-error");
        $("#errorUserMiddleNameId").html("");
        flagValidTotal.push(true);
    }

    if ($.trim($("#userLastNameId").val()).length > 0) {

        if ($.trim($("#userLastNameId").val()).match(alpahaReg)) {

            if ($.trim($("#userLastNameId").val()).length <= 100) {
                flagValidTotal.push(true);
                $("#userLastNameId").removeClass("ux-form-field-error");
                $("#errorUserLastNameId").html("");
            } else {
                $("#userLastNameId").addClass("ux-form-field-error");
                $("#errorUserLastNameId").html("Last Name cannot exceed 100 characters");
                flagValidTotal.push(false);
            }

        } else {
            $("#userLastNameId").addClass("ux-form-field-error");
            $("#errorUserLastNameId").html("Last Name only accepts alphabets");
            flagValidTotal.push(false);
        }

    } else {
        $("#userLastNameId").addClass("ux-form-field-error");
        $("#errorUserLastNameId").html("Please enter Last Name");
        flagValidTotal.push(false);
    }

    if ($.trim($("#jobTitleNameId").val()).length > 0) {

        if ($.trim($("#jobTitleNameId").val()).match(alphaNum)) {

            if ($.trim($("#jobTitleNameId").val()).length <= 100) {
                flagValidTotal.push(true);
                $("#jobTitleNameId").removeClass("ux-form-field-error");
                $("#errorJobTitleNameId").html("");
            } else {
                $("#jobTitleNameId").addClass("ux-form-field-error");
                $("#errorJobTitleNameId").html("Job Title cannot exceed 100 characters");
                flagValidTotal.push(false);
            }

        }

    } else {
        $("#jobTitleNameId").removeClass("ux-form-field-error");
        $("#errorJobTitleNameId").html("");
        flagValidTotal.push(true);
    }

    var email = $("#userEmailId").val();

    var atpos = email.indexOf("@");
    var atposLastIndx = email.lastIndexOf("@");
    var dotposLastIndx = email.lastIndexOf(".");
    var dotpos = email.indexOf(".");
    if (jQuery.trim(email) == "") {
        $("#userEmailId").addClass("ux-form-field-error");
        $("#errorUserEmailId").html("Please enter Email ID");
        flagValidTotal.push(false);
    }

    else if (jQuery.trim(email).length >= 200) {
        $("#userEmailId").addClass("ux-form-field-error");
        $("#errorUserEmailId").html("Email ID cannot exceed 200 characters");
        flagValidTotal.push(false);

    }
    else if (jQuery.trim(email).match(mailRegX)) {
        $("#userEmailId").addClass("ux-form-field-error");
        $("#errorUserEmailId").html("Invaid Email ID");
        flagValidTotal.push(false);

    }
    else if (atpos < 1 || dotposLastIndx < atpos + 2 || dotposLastIndx + 2 >= email.length) {
        $("#userEmailId").addClass("ux-form-field-error");
        $("#errorUserEmailId").html("Email ID only accepts alphanumeric and the following special characters -, _, ., @");
        flagValidTotal.push(false);
    }
    else if (check(jQuery.trim(email)) == false) {
        $("#userEmailId").addClass("ux-form-field-error");
        $("#errorUserEmailId").html("Please enter a valid Email ID");
        flagValidTotal.push(false);
    }
    else if ((atpos != atposLastIndx)) {
        $("#userEmailId").addClass("ux-form-field-error");
        $("#errorUserEmailId").html("Please enter a valid Email ID");
        flagValidTotal.push(false);
    }
    else {
        $("#userEmailId").removeClass("ux-form-field-error");
        $("#errorUserEmailId").html("");
        flagValidTotal.push(true);
    }

    var phoneNumber = $.trim($("#userPhoneId").val());
    if (phoneNumber != "") {
        if (reg.test(phoneNumber)) {
            if (phoneNumber.length >= 10) {
                if ((phoneNumber.length <= 20)) {
                    $("#userPhoneId").removeClass("ux-form-field-error");
                    $("#errorUserPhoneId").html("");
                    flagValidTotal.push(true);
                } else {
                    $("#userPhoneId").addClass("ux-form-field-error");
                    $("#errorUserPhoneId").html("Phone Number cannot exceed 20 characters");
                    flagValidTotal.push(false);
                }
            } else {
                $("#userPhoneId").addClass("ux-form-field-error");
                $("#errorUserPhoneId").html("Please enter a valid Phone Number");
                flagValidTotal.push(false);
            }
        } else {
            $("#userPhoneId").addClass("ux-form-field-error");
            $("#errorUserPhoneId").html("Phone Number only accepts numbers");
            flagValidTotal.push(false);
        }
    } else {
        $("#userPhoneId").removeClass("ux-form-field-error");
        $("#errorUserPhoneId").html("");
        flagValidTotal.push(true);
    }

 if ($.trim($("#departmentId").val()).length > 0) {

        if ($.trim($("#departmentId").val()).match(alpahaReg)) {

            if ($.trim($("#departmentId").val()).length <= 100) {
                flagValidTotal.push(true);
                $("#departmentId").removeClass("ux-form-field-error");
                $("#errorDepartmentId").html("");
            } else {
                $("#departmentId").addClass("ux-form-field-error");
                $("#errorDepartmentId").html("Department cannot exceed 100 characters");
                flagValidTotal.push(false);
            }

        } else {
            $("#departmentId").addClass("ux-form-field-error");
            $("#errorDepartmentId").html("Department only accepts alphabets");
            flagValidTotal.push(false);
        }

    } else {
        $("#departmentId").addClass("ux-form-field-error");
        $("#errorDepartmentId").html("Please enter Department");
        flagValidTotal.push(false);
    }


    var roleValues = $('[name="selectedRole"]').map(function() {
        return this.value
    }).get()
        if (roleValues.length > 0) {
        flagValidTotal.push(true);
        $(".ms-choice").removeClass("ms-choice-error");
        $("#errorSelectedRole").html("");
    } else {
        $(".ms-choice").addClass("ms-choice-error");
        $("#errorSelectedRole").html("Please select at least one Role");
        flagValidTotal.push(false);
    }
//    if ($.trim($('#selectedRole').val()) !== '') {
//        flagValidTotal.push(true);
//        $(".ms-choice").removeClass("ms-choice-error");
//        $("#errorSelectedRole").html("");
//    } else {
//        $(".ms-choice").addClass("ms-choice-error");
//        $("#errorSelectedRole").html("Please select at least one Role");
//        flagValidTotal.push(false);
//    }
    var statusValue = $("input[name=rowStatusKey]:checked").val();
    var deactiveReason = $("#deactiveReason").val().trim();
    if (statusValue == 2) {
        if (jQuery.trim(deactiveReason) === '') {
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
