/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {

    $("#m_personal_info").addClass("ux-vnav-selected ux-vnav-has-selected");
    $("#mprofile").removeClass("ux-vnav-submenu-closed");
    $("#mprofile").addClass("ux-vnav-has-selected ux-vnav-submenu-open");

});

function fnValidateQuestionnaire() {
    var q1 = $("#security-question-1 :selected").text().trim();
    var q2 = $("#security-question-2 :selected").text().trim();
    var q3 = $("#security-question-3 :selected").text().trim();
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
    if (q1 !== "----Select----" && q2 !== "----Select----" && q3 !== "----Select----") {
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

function fnSavePersonnelInfo()
{
    if(fnValidateQuestionnaire())
    {
        document.configUser.action="updpersonnelinfo.htm";
        document.configUser.submit();
    }
}