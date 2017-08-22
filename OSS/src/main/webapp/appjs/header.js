/* 
 * Create dynamic input hidden field
 */

function makeid() {
    var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    for (var i = 0; i < 5; i++)
        text += possible.charAt(Math.floor(Math.random() * possible.length));

    return text;
}

$(document).ready(function() {
    var uid = makeid();
    var fieldHTML = '<div><input type="hidden" name="mnval" value="' + uid + '"/></div>';
    $("form").each(function() {
        $(this).append(fieldHTML);
    });
});

function headerFn(action) {
    document.headerfrm.action = action;
    document.headerfrm.submit();
}


