// JavaScript Document
function popup(sw) {
    if (sw == 1) {
        // Show popup
        document.getElementById('blackcover').style.visibility = 'visible';
        document.getElementById('popupbox').style.visibility = 'visible';
        document.getElementById('blackcover').style.display = 'block';
        document.getElementById('popupbox').style.display = 'block';
    } else {
// Hide popup
        document.getElementById('blackcover').style.visibility = 'hidden';
        document.getElementById('popupbox').style.visibility = 'hidden';
        document.getElementById('blackcover').style.display = 'none';
        document.getElementById('popupbox').style.display = 'none';
    }
}


function poploadimage(sw) {
    if (sw == 1) {
        // Show popup
        document.getElementById('blackcover1').style.visibility = 'visible';
        document.getElementById('loading').style.visibility = 'visible';
        document.getElementById('blackcover1').style.display = 'block';
        document.getElementById('loading').style.display = 'block';
    } else {
// Hide popup
        document.getElementById('blackcover1').style.visibility = 'hidden';
        document.getElementById('loading').style.visibility = 'hidden';
        document.getElementById('blackcover1').style.display = 'none';
        document.getElementById('loading').style.display = 'none';
    }
}

function deletePopup(sw) {
    if (sw === 1) {
        // Show popup
        document.getElementById('blackcover2').style.visibility = 'visible';
        document.getElementById('popupbox2').style.visibility = 'visible';
        document.getElementById('blackcover2').style.display = 'block';
        document.getElementById('popupbox2').style.display = 'block';
    } else {
    // Hide popup
        document.getElementById('blackcover2').style.visibility = 'hidden';
        document.getElementById('popupbox2').style.visibility = 'hidden';
        document.getElementById('blackcover2').style.display = 'none';
        document.getElementById('popupbox2').style.display = 'none';
    }
}