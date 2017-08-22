/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function addOrganization(){
    document.organizationfrm.action = "addOrganization.htm";
    document.organizationfrm.submit();
}

$(document).ready(function() {
    /* Start :: data table coulumn structure */
    $("#mo").addClass("ux-vnav-selected");

    $("#mrandp").addClass("ux-vnav-submenu-closed");
    $("#mrandp").removeClass("ux-vnav-has-selected ");
    $("#mu").removeClass("ux-vnav-selected");
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
            {"bSortable": false}
        ]
    }).columnFilter({
        sPlaceHolder: "head:after",
        aoColumns: [
            {type: "text"},
            {type: "text"},
            {type: "text"},
            {type: "select"},
            {type: "select"},
            null//  {type: "date-range"},
        ]});
    $("#dataTable_filter").css({"display": "none"});
    /* End :: data table coulumn structure */

    /*Start datatable Refresh WorkList function*/
    $.fn.SearchFilterClear = function(obj, tableID) {
        var tabObj = $(tableID).dataTable().fnFilterClear("");
        var tds = $(obj).parent().parent().parent().children("td");
        $(tds).each(function() {
            var inputFeild = $(this).children().children("input").attr("type");
            if (typeof inputFeild != "undefined") {
                var textObj = $(this).children().children("input");
                $(textObj).val("");
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
    $.fn.editOrg = function(idone) {
       $("#orgID").val(idone);
        document.OrgWorkList.action = "editOrganizationPage.htm";
        document.OrgWorkList.submit();
    }
     $.fn.orgDetails = function(idone) {
       $("#orgID").val(idone);
        document.OrgWorkList.action = "getOrgDetails.htm";
        document.OrgWorkList.submit();
    }
    /*End datatable Refresh WorkList function*/
});

