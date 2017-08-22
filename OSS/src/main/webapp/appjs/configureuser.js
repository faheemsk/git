/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function validateConfigUserPassword() {
    
    var q1 = $("#security-question-1 :selected").text();
    var q2 = $("#security-question-2 :selected").text();
    var q3 = $("#security-question-3 :selected").text();
    var a1 = $("#securityAnswer1").val();
    var a2 = $("#securityAnswer2").val();
    var a3 = $("#securityAnswer3").val();
    var currentPass = $("#currentPassword").val();
    var newPass = $("#newPassword").val();
    var confPass = $("#confirmPassword").val();
    flagArray = [];
    var flag = false;
    if (jQuery.trim(q1) === "----Select----") {
        $("#security-question-1").addClass("ux-form-field-error");
        $("#errorMsgSecurityQuestion1").html("<font color='red'>Please select Security Question 1.</font>");
        flagArray.push(false);
    } else {
        $("#security-question-1").removeClass("ux-form-field-error");
        $("#errorMsgSecurityQuestion1").html("");
        flagArray.push(true);
    }
    if (jQuery.trim(q2) === "----Select----") {
        $("#security-question-2").addClass("ux-form-field-error");
        $("#errorMsgSecurityQuestion2").html("<font color='red'>Please select Security Question 2.</font>");
        flagArray.push(false);
    } else {
        $("#security-question-2").removeClass("ux-form-field-error");
        $("#errorMsgSecurityQuestion2").html("");
        flagArray.push(true);
    }
    if (jQuery.trim(q3) === "----Select----") {
        $("#security-question-3").addClass("ux-form-field-error");
        $("#errorMsgSecurityQuestion3").html("<font color='red'>Please select Security Question 3.</font>");
        flagArray.push(false);
    } else {
        $("#security-question-3").removeClass("ux-form-field-error");
        $("#errorMsgSecurityQuestion3").html("");
        flagArray.push(true);
    }
    if (jQuery.trim(a1) === "") {
        $("#securityAnswer1").addClass("ux-form-field-error");
        $("#errorMsgSecurityAnswer1").html("<font color='red'>Please enter Answer.</font>");
        flagArray.push(false);
    } else {
        $("#securityAnswer1").removeClass("ux-form-field-error");
        $("#errorMsgSecurityAnswer1").html("");
        flagArray.push(true);
    }
    if (jQuery.trim(a2) === "") {
        $("#securityAnswer2").addClass("ux-form-field-error");
        $("#errorMsgSecurityAnswer2").html("<font color='red'>Please enter Answer.</font>");
        flagArray.push(false);
    } else {
        $("#securityAnswer2").removeClass("ux-form-field-error");
        $("#errorMsgSecurityAnswer2").html("");
        flagArray.push(true);
    }
    if (jQuery.trim(a3) === "") {
        $("#securityAnswer3").addClass("ux-form-field-error");
        $("#errorMsgSecurityAnswer3").html("<font color='red'>Please enter Answer.</font>");
        flagArray.push(false);
    } else {
        $("#securityAnswer3").removeClass("ux-form-field-error");
        $("#errorMsgSecurityAnswer3").html("");
        flagArray.push(true);
    }
    if (q1 !== "" && q2 !== "" && q3 !== "") {
        if (q1 === q2 || q1 === q3 || q2 === q3) {

            $("#errorMsgSameQuestion").html("<font color='red'>Same Security question cannot be selected more than once.</font>");
            flagArray.push(false);
        } else {
            $("#errorMsgSameQuestion").html("");
            flagArray.push(true);
        }
    }

    // current password validation
    if (jQuery.trim(currentPass) === "") {
        $("#currentPassword").addClass("ux-form-field-error");
        $("#errorMsgCurrentPassword").html("<font color='red'>Please enter Current Password.</font>");
        flagArray.push(false);

    } else {
        $("#currentPassword").removeClass("ux-form-field-error");
        $("#errorMsgCurrentPassword").html("");
        flagArray.push(true);
    }

    // new password validation
    if (jQuery.trim(newPass) === "") {
        $("#newPassword").addClass("ux-form-field-error");
        $("#errorMsgNewPassword").html("<font color='red'>Please enter New Password.</font>");
        flagArray.push(false);

    } else if (jQuery.trim(newPass).length < 8) {
        $("#newPassword").addClass("ux-form-field-error");
        $("#errorMsgNewPassword").html("<font color='red'> New Password must be between 8 and 25 characters.</font>");
        flagArray.push(false);

    } else if (passwordCheck($("#newPassword").val()) !== 3) {
        $("#newPassword").addClass("ux-form-field-error");
        $("#errorMsgNewPassword").html("<font color='red'>New Password must contain at least one upper case letter, one lower case letter, and one number.</font>");
        flagArray.push(false);
    } else {
        $("#newPassword").removeClass("ux-form-field-error");
        $("#errorMsgNewPassword").html("");
        flagArray.push(true);
    }

    // confirm password validation
    if (jQuery.trim(confPass) === "") {
        $("#confirmPassword").addClass("ux-form-field-error");
        $("#errorMsgConfirmPassword").html("<font color='red'>Please enter Confirm Password.</font>");
        flagArray.push(false);

    } else if (jQuery.trim(confPass).length < 8) {
        $("#confirmPassword").addClass("ux-form-field-error");
        $("#errorMsgConfirmPassword").html("<font color='red'> Confirm Password must be between 8 and 25 characters.</font>");
        flagArray.push(false);

    } else if (passwordCheck($("#confirmPassword").val()) !== 3) {
        $("#confirmPassword").addClass("ux-form-field-error");
        $("#errorMsgConfirmPassword").html("<font color='red'>Confirm Password must contain at least one upper case letter, one lower case letter, and one number.</font>");
        flagArray.push(false);
    } else {
        $("#confirmPassword").removeClass("ux-form-field-error");
        $("#errorMsgConfirmPassword").html("");
        flagArray.push(true);
    }
    // checking entered confirm password and new password are same or not
    if ((newPass !== "" && confPass !== "") && (newPass !== confPass)) {
        $("#confirmPassword").addClass("ux-form-field-error");
        $("#newPassword").addClass("ux-form-field-error");
        $("#errorMsgConfirmPassword").html("<font color='red'>Confirm Password does not match New Password.</font>");
        flagArray.push(false);

    }  // checking current  password and new password are same or not
    else if ((newPass !== "" && currentPass !== "") && (newPass === currentPass)) {

        $("#newPassword").addClass("ux-form-field-error");
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


    if (flag) {
        document.configUser.action = "./updconfigureuser.htm";
        document.configUser.submit();

    } else {
        return false;
    }
}
// to validate questionnaire
function validateQuestionnaire() {
    var q1 = $("#security-question-1 :selected").text();
    var q2 = $("#security-question-2 :selected").text();
    var q3 = $("#security-question-3 :selected").text();
    var a1 = $("#securityAnswer1").val();
    var a2 = $("#securityAnswer2").val();
    var a3 = $("#securityAnswer3").val();
    flagArray = [];
    var flag = false;
    if (jQuery.trim(q1) === "----Select----") {
        $("#security-question-1").addClass("ux-form-field-error");
        $("#errorMsgSecurityQuestion1").html("<font color='red'>Please select Security Question 1.</font>");
        flagArray.push(false);
    } else {
        $("#security-question-1").removeClass("ux-form-field-error");
        $("#errorMsgSecurityQuestion1").html("");
        flagArray.push(true);
    }
    if (jQuery.trim(q2) === "----Select----") {
        $("#security-question-2").addClass("ux-form-field-error");
        $("#errorMsgSecurityQuestion2").html("<font color='red'>Please select Security Question 2.</font>");
        flagArray.push(false);
    } else {
        $("#security-question-2").removeClass("ux-form-field-error");
        $("#errorMsgSecurityQuestion2").html("");
        flagArray.push(true);
    }
    if (jQuery.trim(q3) === "----Select----") {
        $("#security-question-3").addClass("ux-form-field-error");
        $("#errorMsgSecurityQuestion3").html("<font color='red'>Please select Security Question 3.</font>");
        flagArray.push(false);
    } else {
        $("#security-question-3").removeClass("ux-form-field-error");
        $("#errorMsgSecurityQuestion3").html("");
        flagArray.push(true);
    }
    if (jQuery.trim(a1) === "") {
        $("#securityAnswer1").addClass("ux-form-field-error");
        $("#errorMsgSecurityAnswer1").html("<font color='red'>Please enter Answer.</font>");
        flagArray.push(false);
    } else {
        $("#securityAnswer1").removeClass("ux-form-field-error");
        $("#errorMsgSecurityAnswer1").html("");
        flagArray.push(true);
    }
    if (jQuery.trim(a2) === "") {
        $("#securityAnswer2").addClass("ux-form-field-error");
        $("#errorMsgSecurityAnswer2").html("<font color='red'>Please enter Answer.</font>");
        flagArray.push(false);
    } else {
        $("#securityAnswer2").removeClass("ux-form-field-error");
        $("#errorMsgSecurityAnswer2").html("");
        flagArray.push(true);
    }
    if (jQuery.trim(a3) === "") {
        $("#securityAnswer3").addClass("ux-form-field-error");
        $("#errorMsgSecurityAnswer3").html("<font color='red'>Please enter Answer.</font>");
        flagArray.push(false);
    } else {
        $("#securityAnswer3").removeClass("ux-form-field-error");
        $("#errorMsgSecurityAnswer3").html("");
        flagArray.push(true);
    }
    if (q1 !== "" && q2 !== "" && q3 !== "") {
        if (q1 === q2 || q1 === q3 || q2 === q3) {

            $("#errorMsgSameQuestion").html("<font color='red'>Same Security question cannot be selected more than once.</font>");
            flagArray.push(false);
        } else {
            $("#errorMsgSameQuestion").html("");
            flagArray.push(true);
        }
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

function validatePasswords()
{
    var currentPass = $("#currentPassword").val();
    var newPass = $("#newPassword").val();
    var confPass = $("#confirmPassword").val();
    flagArray = [];
    var flag = false;


    // current password validation
    if (jQuery.trim(currentPass) === "") {
        $("#currentPassword").addClass("ux-form-field-error");
        $("#errorMsgCurrentPassword").html("<font color='red'>Please enter Current Password.</font>");
        flagArray.push(false);

    } else {
        $("#currentPassword").removeClass("ux-form-field-error");
        $("#errorMsgCurrentPassword").html("");
        flagArray.push(true);
    }

    // new password validation
    if (jQuery.trim(newPass) === "") {
        $("#newPassword").addClass("ux-form-field-error");
        $("#errorMsgNewPassword").html("<font color='red'>Please enter New Password.</font>");
        flagArray.push(false);

    } else if (jQuery.trim(newPass).length < 8) {
        $("#newPassword").addClass("ux-form-field-error");
        $("#errorMsgNewPassword").html("<font color='red'> New Password must be between 8 and 25 characters.</font>");
        flagArray.push(false);

    } else if (passwordCheck($("#newPassword").val()) !== 3) {
        $("#newPassword").addClass("ux-form-field-error");
        $("#errorMsgNewPassword").html("<font color='red'>New Password must contain at least one upper case letter, one lower case letter, and one number.</font>");
        flagArray.push(false);
    } else {
        $("#newPassword").removeClass("ux-form-field-error");
        $("#errorMsgNewPassword").html("");
        flagArray.push(true);
    }

    // confirm password validation
    if (jQuery.trim(confPass) === "") {
        $("#confirmPassword").addClass("ux-form-field-error");
        $("#errorMsgConfirmPassword").html("<font color='red'>Please enter Confirm Password.</font>");
        flagArray.push(false);

    } else if (jQuery.trim(confPass).length < 8) {
        $("#confirmPassword").addClass("ux-form-field-error");
        $("#errorMsgConfirmPassword").html("<font color='red'> Confirm Password must be between 8 and 25 characters.</font>");
        flagArray.push(false);

    } else if (passwordCheck($("#confirmPassword").val()) !== 3) {
        $("#confirmPassword").addClass("ux-form-field-error");
        $("#errorMsgConfirmPassword").html("<font color='red'>Confirm Password must contain at least one upper case letter, one lower case letter, and one number.</font>");
        flagArray.push(false);
    } else {
        $("#confirmPassword").removeClass("ux-form-field-error");
        $("#errorMsgConfirmPassword").html("");
        flagArray.push(true);
    }
    // checking entered confirm password and new password are same or not
    if ((newPass !== "" && confPass !== "") && (newPass !== confPass)) {
        $("#confirmPassword").addClass("ux-form-field-error");
        $("#newPassword").addClass("ux-form-field-error");
        $("#errorMsgConfirmPassword").html("<font color='red'>Confirm Password does not match New Password.</font>");
        flagArray.push(false);

    }  // checking current  password and new password are same or not
    else if ((newPass !== "" && currentPass !== "") && (newPass === currentPass)) {

        $("#newPassword").addClass("ux-form-field-error");
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