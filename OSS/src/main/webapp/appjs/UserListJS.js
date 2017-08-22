
function addUserFn() {
    document.mnguserfrm.action = "adduser.htm";
    document.mnguserfrm.submit();
}

$(document).ready(function() {
    
    $("#mu").addClass("ux-vnav-selected");
    $("#mo").removeClass("ux-vnav-selected");
    $("#mrandp").addClass("ux-vnav-submenu-closed");
    $("#mrandp").removeClass("ux-vnav-has-selected ");
    $("#ua").removeClass("ux-vnav-selected");
    
    
    $('#dataTable').dataTable({
        "order": [[0, "desc"]],
        ordering: true,
        paging: true,
        info: false,
        "aoColumns": [
            {"bSortable": true},
            {"bSortable": true},
            {"bSortable": true},
            {"bSortable": true},
            {"bSortable": true},
            {"bSortable": true},
            {"bSortable": true},
            {"bSortable": true},
            {"bSortable": true},
            {"bSortable": false}
        ]
    }).columnFilter({
        sPlaceHolder: "head:after",
        aoColumns: [
            {type: "text"},
            {type: "text"},
            {type: "text"},
            {type: "text"},
            {type: "select"},
            {type: "text"},
            {type: "text"},
            {type: "text"},
            {type: "select"},
            null
        ]
    });
    
    /*
     * For hiding global search from data table
     */
    $("#dataTable_filter").css({"display": "none"});
    
     
             $.fn.updateUser = function(idone) {
       $("#userProfileKey").val(idone);
        document.ManageUser.action = "updateuser.htm";
        document.ManageUser.submit();
    }
     $.fn.viewUser = function(idone) {
       $("#userProfileKey").val(idone);
        document.ManageUser.action = "viewuser.htm";
        document.ManageUser.submit();
    }
});

$.fn.SearchFilterClear = function(obj, tableID) {
    var dateType = $('#dataTable').dataTable();
    var tabObj = $(tableID).dataTable().fnFilterClear("");
    var tds = $(obj).parent().parent().parent().children("td");
    $(tds).each(function() {
        var inputFeild = $(this).children().children("input").attr("type");
        if (typeof inputFeild != "undefined") {
            var textObj = $(this).children().children("input");
            $(textObj).val("");
            var tableSettings = dateType.fnSettings();
            //For clear the textbox data     
            $(textObj).trigger('blur');
        }
        else {
            inputFeild = $(this).children().children("select");
            if (typeof inputFeild != "undefined") {
                $(inputFeild).val("select");
            }
        }
    });
}
           