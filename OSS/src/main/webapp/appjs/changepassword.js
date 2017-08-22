/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {

    if ($("#m_password"))
    {
        $("#m_password").addClass("ux-vnav-selected ux-vnav-has-selected");
        $("#mprofile").removeClass("ux-vnav-submenu-closed");
        $("#mprofile").addClass("ux-vnav-has-selected ux-vnav-submenu-open");
    }
    
    $("#mnvalprofile").val(makeid());
});


function fnValidatePasswords()
{
    var currentPass = $("#currentPswd").val();
    var newPass = $("#newPswd").val();
    var confPass = $("#confirmPswd").val();
    flagArray = [];
    var flag = false;


    // current password validation
    if (jQuery.trim(currentPass) === "") {
        $("#currentPswd").addClass("ux-form-field-error");
        $("#errorMsgCurrentPassword").html("<font color='red'>Please enter Current Password.</font>");
        flagArray.push(false);

    } else {
        $("#currentPswd").removeClass("ux-form-field-error");
        $("#errorMsgCurrentPassword").html("");
        flagArray.push(true);
    }

    // new password validation
    if (jQuery.trim(newPass) === "") {
        $("#newPswd").addClass("ux-form-field-error");
        $("#errorMsgNewPassword").html("<font color='red'>Please enter New Password.</font>");
        flagArray.push(false);

    } else if (jQuery.trim(newPass).length < 8) {
        $("#newPswd").addClass("ux-form-field-error");
        $("#errorMsgNewPassword").html("<font color='red'> New Password must be between 8 and 25 characters.</font>");
        flagArray.push(false);

    } else if (passwordCheck($("#newPswd").val()) !== 3) {
        $("#newPswd").addClass("ux-form-field-error");
        $("#errorMsgNewPassword").html("<font color='red'>New Password must contain at least one upper case letter, one lower case letter, and one number.</font>");
        flagArray.push(false);
    } else {
        $("#newPswd").removeClass("ux-form-field-error");
        $("#errorMsgNewPassword").html("");
        flagArray.push(true);
    }

    // confirm password validation
    if (jQuery.trim(confPass) === "") {
        $("#confirmPswd").addClass("ux-form-field-error");
        $("#errorMsgConfirmPassword").html("<font color='red'>Please enter Confirm Password.</font>");
        flagArray.push(false);

    } else if (jQuery.trim(confPass).length < 8) {
        $("#confirmPswd").addClass("ux-form-field-error");
        $("#errorMsgConfirmPassword").html("<font color='red'> Confirm Password must be between 8 and 25 characters.</font>");
        flagArray.push(false);

    } else if (passwordCheck($("#confirmPswd").val()) !== 3) {
        $("#confirmPswd").addClass("ux-form-field-error");
        $("#errorMsgConfirmPassword").html("<font color='red'>Confirm Password must contain at least one upper case letter, one lower case letter, and one number.</font>");
        flagArray.push(false);
    } else {
        $("#confirmPswd").removeClass("ux-form-field-error");
        $("#errorMsgConfirmPassword").html("");
        flagArray.push(true);
    }
    // checking entered confirm password and new password are same or not
    if ((newPass !== "" && confPass !== "") && (newPass !== confPass)) {
        $("#confirmPswd").addClass("ux-form-field-error");
        $("#newPswd").addClass("ux-form-field-error");
        $("#errorMsgConfirmPassword").html("<font color='red'>Confirm Password does not match New Password.</font>");
        flagArray.push(false);

    }  // checking current  password and new password are same or not
    else if ((newPass !== "" && currentPass !== "") && (newPass === currentPass)) {

        $("#newPswd").addClass("ux-form-field-error");
        $("#errorMsgNewPassword").html("<font color='red'>Current Password and New Password cannot be the same.</font>");
        flagArray.push(false);
    } else {
        flagArray.push(true);
    }

    for (var i = 0; i < flagArray.length; i++) {
        if (flagArray[i] === false) {
            flag = false;
            break;
        } else {
            flag = true;
        }
    }

    return flag;
}


function passwordCheck(password)
{
    var indX = 0;
    var password = password;
    var LOWER = /[a-z]/;
    var UPPER = /[A-Z]/;
    var DIGIT = /[0-9]/;
    var lower = LOWER.test(password);
    var upper = UPPER.test(password);
    var digit = DIGIT.test(password);
    if (lower) {
        indX++;
    }
    if (upper) {
        indX++;
    }
    if (digit) {
        indX++;
    }

    return indX;
}

function fnChangePassword()
{
    if (fnValidatePasswords())
    {
        document.changepass.action="upchangepassword.htm"
        document.changepass.submit();
    }
}

function fnExpChangePassword()
{    
    if (fnValidatePasswords())
    { 
        document.changepass.action="expchangepassUpdate.htm"
        document.changepass.submit();
    }
}

function fnConfirmPassword() {
    var currentPass = $("#currentPassword").val();
    flagArray = [];
    var flag = false;
    // current password validation
    if (jQuery.trim(currentPass) === "") {
        $("#currentPassword").addClass("ux-form-field-error");
        $("#errorMsgCurrentPassword").html("<font color='red'>Please enter Password.</font>");
        flagArray.push(false);

    } else {
        $("#currentPassword").removeClass("ux-form-field-error");
        $("#errorMsgCurrentPassword").html("");
        flagArray.push(true);
    }
    for (var i = 0; i < flagArray.length; i++) {
        if (flagArray[i] === false) {
            flag = false;
            break;
        } else {
            flag = true;
        }
    }
    if (flag) {
        //alert("hi");
        document.changepass.action="validateProfileConfirmpassword.htm"
        document.changepass.submit();
    }
}