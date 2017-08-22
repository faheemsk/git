/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
});
 
function validateForm() {
    var username = $("#user_name").val();
    var password = $("#password").val();
    flagArray = [];
    var flag = false;
    var emailRegExp = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
    if (jQuery.trim(username) === "") {
        $("#user_name").addClass("ux-form-field-error");
        $("#errorUserName").html("<font color='red'>Please enter the registered Email ID.</font>");
        flagArray.push(false);

    } else if (!jQuery.trim(username).match(emailRegExp)) {
        $("#user_name").addClass("ux-form-field-error");
        $("#errorUserName").html("<font color='red'>Please enter a valid Email ID<br>e.g. name@domain.com.</font>");
        flagArray.push(false);
    }else {
        $("#user_name").removeClass("ux-form-field-error");
        $("#errorUserName").html("");
        flagArray.push(true);
    }
    if (jQuery.trim(password) === "") {
        $("#password").addClass("ux-form-field-error");
        $("#errorPassword").html("<font color='red'>Please enter Password.</font>");
        flagArray.push(false);
    }
    else
    {
        $("#password").removeClass("ux-form-field-error");
        $("#errorPassword").html("");
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

function login(id){
    $(id).keyup(function(event){
        event.stopImmediatePropagation();
        if(event.keyCode == 13){
            loginValidate();
        }
    });
}

function loginValidate()
{
    $("#blrd").html("");
    if(validateForm())
    {
           document.loginForm.action="validatelogin.htm";
           document.loginForm.submit();
    }
    else
    {
        return false;
    }
}

function fnKillActiveSession()
{
    document.loginForm.action="killactivesession.htm";
    document.loginForm.submit();
}


function fnLoginRedirect()
{
    document.loginForm.action="login.htm";
    document.loginForm.submit();
}
//
//function checkActiveSession(userEmail)
//{
//    var flag = true;
//    if (flag) {
//        jQuery.ajax({
//            type: "POST",
//            contentType: "text/plain",
//            url: "checkactivesession.htm?user_name=" + userEmail,
//            datatype: "text",
//            success: function(data) {
//                if (data !== "No") {
//                    flag = false;
//                    $("#warnPopContent").html(data);
//                    popup(1);
//                    return flag;
//                }
//                else
//                {
//                    return flag;
//                }
//            },
//            error: function(e) {
//            }
//        });
//    }
//    return flag
//}

/*
 * ************************************************
 * FORGOT PASSWORD VALIDATIONS
 * ************************************************
 */

function fnValidateFPUserName()
{
    var username = $("#username").val();
    flagArray = [];
    var flag = false;
    var emailRegExp = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
    if (jQuery.trim(username) === "") {
        $("#username").addClass("ux-form-field-error");
        $("#errorUserName").html("<font color='red'>Please enter the registered Email ID.</font>");
        flagArray.push(false);

    } else if (!jQuery.trim(username).match(emailRegExp)) {
        $("#username").addClass("ux-form-field-error");
        $("#errorUserName").html("<font color='red'>Please enter a valid Email ID. e.g. name@domain.com.</font>");
        flagArray.push(false);
    }else {
        $("#username").removeClass("ux-form-field-error");
        $("#errorUserName").html("");
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

function fnValidateFPAnswerDetails()
{
    var ans1 = $("#securityAnswer1").val();
    var ans2 = $("#securityAnswer2").val();
    flagArray = [];
    var flag = false;
    if (jQuery.trim(ans1) === "") {
        $("#securityAnswer1").addClass("ux-form-field-error");
        $("#errorMsgSecurityAnswer1").html("<font color='red'>Please enter Answer.</font>");
        flagArray.push(false);
    } else {
        $("#user_name").removeClass("ux-form-field-error");
        $("#errorMsgSecurityAnswer1").html("");
        flagArray.push(true);
    }
    if (jQuery.trim(ans2) === "") {
        $("#securityAnswer2").addClass("ux-form-field-error");
        $("#errorMsgSecurityAnswer2").html("<font color='red'>Please enter Answer.</font>");
        flagArray.push(false);
    } else {
        $("#securityAnswer2").removeClass("ux-form-field-error");
        $("#errorMsgSecurityAnswer2").html("");
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

function fnValidateVUAnswerDetails()
{
    var ans1 = $("#securityAnswer1").val();
    flagArray = [];
    var flag = false;
    if (jQuery.trim(ans1) === "") {
        $("#securityAnswer1").addClass("ux-form-field-error");
        $("#errorMsgSecurityAnswer1").html("<font color='red'>Please enter Answer.</font>");
        flagArray.push(false);
    } else {
        $("#securityAnswer1").removeClass("ux-form-field-error");
        $("#errorMsgSecurityAnswer1").html("");
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
function fnCheckUser(butval)
{
    if(fnValidateFPUserName())
    {
        $("#chk").val(butval);
        document.forgotPassword.action="actforgotpassword.htm";
        document.forgotPassword.submit();
    }
}


function fnSubmitDetails(butval)
{   $("#valrr").html("");
    $("#valrr").removeClass("ux-msg-error");

    if(fnValidateFPAnswerDetails())
    {
        $("#chk").val(butval);
        document.forgotPassword.action="actforgotpassword.htm";
        document.forgotPassword.submit();
    }
}

function fnSubmitverifyDetails()
{   $("#valrr").html("");
    $("#valrr").removeClass("ux-msg-error");
 if(fnValidateVUAnswerDetails())
    {
        document.configUser.action="validateverifyuser.htm";
        document.configUser.submit();
    }
}

// WEB HELP
function openWebHelp(path) {
    var encValue = $("#encUserPrf").val();
    jQuery.ajax({
        type: "POST",
        async: false,
        contentType: "text/plain",
        url: "checkactivesession.htm?user_name=" + encValue,
        datatype: "text",
        success: function(data) {
            if (data === "No") {
                window.location.href = '<c:url value="logout.htm"/>';
            }
            else
            {
                var windowObjectReference;
                var strWindowFeatures = "menubar=no,location=no,resizable=yes,titlebar=no,toolbar=no,scrollbars=no,status=no,width=1000,height=500";
                windowObjectReference = window.open(path, "CNN_WindowName", strWindowFeatures);
            }
        },
        error: function(e) {
            window.location.href = '<c:url value="logout.htm"/>';
        }
    });
}


function showKeyCode(e) {
   
    var keycode;
    if (window.event)
        keycode = window.event.keyCode;
    else if (e)
        keycode = e.which;
   
    // Mozilla firefox
    if ($.browser.mozilla) {
        if (keycode == 116 || (e.ctrlKey && keycode == 82)) {
            if (e.preventDefault) {
                e.preventDefault();
                e.stopPropagation();
            }
        }
    }
    // IE
    else if ($.browser.msie) {
        if (keycode == 116 || (window.event.ctrlKey && keycode == 82)) {
            window.event.returnValue = false;
            window.event.keyCode = 0;
            window.status = "Refresh is disabled";
        }
    }
    else {
        switch (e.keyCode) {

            case 116: // 'F5'
                event.returnValue = false;
                event.keyCode = 0;
                window.status = "Refresh is disabled";
                break;

        }
    }
    //

}
	//function noBack() { window.history.forward(); }
