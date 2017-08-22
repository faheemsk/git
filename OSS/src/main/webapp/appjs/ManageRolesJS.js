/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {
    //   $("#defaultActualPicker").datepick({showTrigger: '<img src="images/calendar01.gif" alt="Popup" class="trigger" title="Calendar">'});
    $("#mr").addClass("ux-vnav-selected");
    $("#ua").removeClass("ux-vnav-selected");
    $("#mu").removeClass("ux-vnav-selected");
    $("#mo").removeClass("ux-vnav-selected");
    $("#mrandp").addClass("ux-vnav-submenu-open");
    $("#mrandp").addClass("ux-vnav-has-selected ");
    
    
    $("#defaultActualPicker").datepicker({
        dateFormat: 'mm/dd/yyyy',
        buttonImage: '<img class="ux-margin-left-min" title="Calendar" src="images/calendar01.gif" class="trigger">',
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


    $('#dataTable').dataTable({
        "order": [[0, "desc"] ],
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
            {"bSortable": false}
        ]
    }).columnFilter({
        sPlaceHolder: "head:after",
        aoColumns: [
            {type: "text"},
            {type: "text"},
            {type: "text"},
            {type: "text"},
            null,
            {type: "select"},
            null
        ]

    });

    $("#dataTable_filter").css({"display": "none"});

    $('tr:odd').addClass('ux-tabl-alt-row');
    
     $("#ux-outline-example").click(function() {
                $(".ux-snav").fadeOut().fadeIn();
            });
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
            document.getElementById("defaultActualPicker").value = "";
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
$.fn.dateFilter = function(obj, tableID) {
    var dateType = $(tableID).dataTable();
    var tableSettings = dateType.fnSettings();
    //For Last Updated Date search
    var defaultActualPicker = $("#defaultActualPicker").val();
    tableSettings.aoPreSearchCols[ 4 ].sSearch = defaultActualPicker;
    dateType.fnDraw();
}

function deleteRole(ed) {
//    $("#appRoleKey").val(key);
//    $("#appRoleName").val(appRoleName);
//   var encRoleKey =  $("#encAppRoleKeyh"+key).val();
//   $("#encAppRoleKey").val(encRoleKey);
    $("#ed").val(ed);
    popup(1);
}

function removeRole() {
    document.manageRoleForm.action = "deleteAppRole.htm";
    document.manageRoleForm.submit();
}



function gotoManageRoleworklist() {
    document.forms[0].action = "addRolepage.htm";
    document.forms[0].submit();
    //  window.location.href = '/OSS/addRolepage.htm'
}

function addRolePage(){
    document.manageRoleForm.action = "addRolepage.htm";
    document.manageRoleForm.submit();
}

function fnEditRole(ed)
{
    $("#ed").val(ed);
    document.manageRoleForm.action = "fetchEditAppRoleDetails.htm";
    document.manageRoleForm.submit();
}

function fnViewRole(ed) {
    $("#ed").val(ed);
    document.manageRoleForm.action = "viewAppRoleDetails.htm";
    document.manageRoleForm.submit();
}