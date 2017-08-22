/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {

    $("#vdp").addClass("ux-vnav-selected");

    $("#mo").removeClass("ux-vnav-selected");
    $("#mrandp").addClass("ux-vnav-submenu-open");
    $("#mrandp").addClass("ux-vnav-has-selected ");
    $("#mu").removeClass("ux-vnav-selected");
    $("#ua").removeClass("ux-vnav-selected");

    $("#lastupdate").datepicker({
        dateFormat: 'mm/dd/yyyy',
        buttonImage: "<img class='ux-margin-left-min' title='Calendar' src='images/calendar01.gif' class='trigger'>",
        onSelect: function(dates) {
            if ("createEvent" in document) {  //NON IE browsers
                var evt = document.createEvent("HTMLEvents");
                evt.initEvent("change", false, true);
                this.dispatchEvent(evt);
            }
            else {  //IE
                var evt = document.createEventObject();
                this.fireEvent("onchange", evt);
            }
        }
    });
    $(".save").hide();
    $('#permissionlist').dataTable({
        ordering: true,
        paging: true,
        info: false,
        "aoColumns": [
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
            null,
            null
        ]
    });
    /*
     * For hiding global search from data table
     */
    $("#permissionlist_filter").css({"display": "none"});
});
function fnEdit(id)
{
    $("#sv" + id).show();
    $("#ed" + id).hide();
    $("#svimg" + id).show();
    $("#edimg" + id).hide();
    $("#editTxt" + id).val(document.getElementById("description"+id).value);
}
function save(id, encid,formid)
{
    $("#encPermissionKey").val(encid);
    $("#permissionDescription").val($("#editTxt"+formid).val());    
    
    //window.location = "savedefinition.htm?encPermissionKey="+encid+"&permissionDescription="+$("#editTxt"+formid).val();
    document.forms['viewdetailedpermission'].action = "savedefinition.htm";
    document.forms['viewdetailedpermission'].submit();
}
$.fn.dateFilter = function(obj, tableID) {
    var dateType = $(tableID).dataTable();
    var tableSettings = dateType.fnSettings();
    //For Last Updated Date search
    var defaultActualPicker = $("#lastupdate").val();
    tableSettings.aoPreSearchCols[ 3 ].sSearch = defaultActualPicker;
    dateType.fnDraw();
}

$.fn.SearchFilterClear = function(obj, tableID) {
    var dateType = $('#permissionlist').dataTable();
    var tabObj = $(tableID).dataTable().fnFilterClear("");
    var tds = $(obj).parent().parent().parent().children("td");
    $(tds).each(function() {
        var inputFeild = $(this).children().children("input").attr("type");
        if (typeof inputFeild != "undefined") {
            var textObj = $(this).children().children("input");
            $(textObj).val("");
            var tableSettings = dateType.fnSettings();
            //For clear the textbox data     
            document.getElementById("lastupdate").value = "";
            dateType.fnDraw();
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