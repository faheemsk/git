/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function sessionpopup(sw)
{
    /*Start: popup for session notification*/
    if (sw == 10) {
        // Show popup 
        document.getElementById('blackcover10').style.visibility = 'visible';
        document.getElementById('popupbox10').style.visibility = 'visible';
        document.getElementById('blackcover10').style.display = 'block';
        document.getElementById('popupbox10').style.display = 'block';
    } else if (sw == -10) {
// Hide popup
        document.getElementById('blackcover10').style.visibility = 'hidden';
        document.getElementById('popupbox10').style.visibility = 'hidden';
        document.getElementById('blackcover10').style.display = 'none';
        document.getElementById('popupbox10').style.display = 'none';
    }
    /*End: popup for session notification*/
    /*Start: popup for session expired*/
    else if (sw == 20) {
        // Show popup 
        document.getElementById('blackcover10').style.visibility = 'visible';
        document.getElementById('popupbox20').style.visibility = 'visible';
        document.getElementById('blackcover10').style.display = 'block';
        document.getElementById('popupbox20').style.display = 'block';
    } else if (sw == -20) {
// Hide popup
        document.getElementById('blackcover10').style.visibility = 'hidden';
        document.getElementById('popupbox20').style.visibility = 'hidden';
        document.getElementById('blackcover10').style.display = 'none';
        document.getElementById('popupbox20').style.display = 'none';
    }
    /*End: popup for session expired*/
}


$(document).ready(function() {
    /* setting default time */
    $.ajax({
        type: "POST",
        url: "getSessionTime.htm",
        async: false,
        success: function(data) {
            var stime = data / 60;
            $("#defaulttime").val(stime);
        },
        error: function(e) {

        }
    });
    var lefttime = ($("#defaulttime").val());

    setInterval(function() {
        lefttime = $("#defaulttime").val() - 1;
        $("#defaulttime").val(lefttime);
        if (lefttime > 0) {

            if (lefttime == 5) {
                sessionpopup(10);
            }

        } else {
            sessionpopup(-10);
            sessionpopup(20);
        }

    }, 60000);
});

