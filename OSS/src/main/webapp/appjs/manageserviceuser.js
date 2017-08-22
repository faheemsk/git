/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {
    

    $("#mu").removeClass("ux-vnav-selected");
    $("#mo").removeClass("ux-vnav-selected");
    $("#mrandp").addClass("ux-vnav-submenu-closed");
    $("#mrandp").removeClass("ux-vnav-has-selected ");
    $("#ua").removeClass("ux-vnav-selected");
    $("#emwl").addClass("ux-vnav-submenu-open");
    $("#emwl").addClass("ux-vnav-selected");
    $("#meu").addClass("ux-vnav-selected");
    
    $('#dataTable').dataTable({
        "order": [[0, "desc"]],
        ordering: false,
        paging: false,
        info: false,
        "aoColumns": [
            {"bSortable": true},
            {"bSortable": true},
            {"bSortable": true},
            {"bSortable": false}
        ]
    }).columnFilter({
        sPlaceHolder: "head:after",
        aoColumns: [
            {type: "select"},
            {type: "text"},
            {type: "text"},
            null
        ]
    });
    
    /*
     * For hiding global search from data table
     */
    $("#dataTable_filter").css({"display": "none"});
    
    
    
    $('#servDataTable').dataTable({
        "order": [[0, "desc"]],
        ordering: false,
        paging: false,
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
//       ,
//        "drawCallback": function ( settings ) {
//            var api = this.api();
//            var rows = api.rows( {page:'current'} ).nodes();
//            var last=null;
// 
//            api.column(5, {page:'current'} ).data().each( function ( group, i ) {
//               // alert(last);
//                if ( last !== group ) {
//                    $(rows).eq( i ).before(
//                        '<tr class="group"><td colspan="7">'+group+'</td></tr>'
//                    );
// 
//                    last = group;
//                }
//            } );
//        }
    }).columnFilter({
        sPlaceHolder: "head:after",
        aoColumns: [
            {type: "select"},
            {type: "text"},
            {type: "text"},
            null,
            null,
            {type: "select"},
            null
        ]
    });
    
    
    
    
    /*
     * For hiding global search from data table
     */
    $("#servDataTable_filter").css({"display": "none"});
    
    
    $(".engUserTypeKey").change(function() {
        document.engUserForm.action = "manageengagementusers.htm#usrfrm";
        document.engUserForm.submit();
    });
    
    $(".srvUserTypeKey").change(function() {
        document.serviceUserForm.action = "manageengagementusers.htm#srvfrm";
        document.serviceUserForm.submit();
    });
    
    $("#startDateId").datepicker({
        minDate:0,
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
    
    $("#endDateId").datepicker({
        minDate:0,
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
    
    $("#dstartDateId").datepicker({
        minDate:0,
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
    
    $("#dendDateId").datepicker({
        minDate:0,
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
    
    
    $("#engUserAdd").click(function(){
        if(saveEngagementUserValidation()){
            document.engUserForm.action = "addengagementusers.htm#usrfrm";
            document.engUserForm.submit();
        }
    });
    
    $("#srvUserAdd").click(function(){
        if(saveServiceUserValidation()){
            document.serviceUserForm.action = "addserviceusers.htm#srvfrm";
            document.serviceUserForm.submit();
        }
    });
    


    
    
    //Start: Below code for refresh in internal view page
    $("#fnRefreshInternalView").click(function()
    {
        document.searchAssignUserFormForInternal.action = "manageserviceuser.htm";
        document.searchAssignUserFormForInternal.submit();
    });
    //End: Below code for refresh in internal view page
    //
    //Start: Below code for refresh in partner view page
    $("#fnRefreshPartnerView").click(function()
    {
        document.searchAssignUserFormForPartner.action = "manageserviceuser.htm";
        document.searchAssignUserFormForPartner.submit();
    });
    //End: Below code for refresh in partner view page
    
    //Start: Reload / Reset the page when click on cancel in Internal view
    $("#fnCancelInternalReload").click(function() {
        document.cancelReloadFormInternal.action = "manageserviceuser.htm";
        document.cancelReloadFormInternal.submit();
    });
    //End: Reload / Reset the page when click on cancel in Internal view
    //
    //Start: Reload / Reset the page when click on cancel in Partner view
    $("#fnCancelPartnerReload").click(function() {
        document.cancelReloadFormPartner.action = "manageserviceuser.htm";
        document.cancelReloadFormPartner.submit();
    });
    //End: Reload / Reset the page when click on cancel in Partner view
});

$.fn.startDateFilter = function(obj, tableID) {
    var dateType = $(tableID).dataTable();
    var tableSettings = dateType.fnSettings();
    var defaultActualPicker = $("#dstartDateId").val();
    tableSettings.aoPreSearchCols[ 3 ].sSearch = defaultActualPicker;
    dateType.fnDraw();
};

$.fn.endDateFilter = function(obj, tableID) {
    var dateType = $(tableID).dataTable();
    var tableSettings = dateType.fnSettings();
    var defaultActualPicker = $("#dendDateId").val();
    tableSettings.aoPreSearchCols[ 4 ].sSearch = defaultActualPicker;
    dateType.fnDraw();
};

$.fn.SearchFilterClear = function(obj, tableID) {
    var dateType = $(tableID).dataTable();
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
};


function deleteEngUser(assignId){
    $("#assignKeyId").val(assignId);
    document.deleteEngUsrForm.submit();
}

function deleteServiceUser(assignId){
    $("#assignSrvKeyId").val(assignId);
    document.deleteSrvUsrForm.submit();
}

//Save Engagement User Validation
var flagValidTotal = new Array();
var returnstring = false;
function saveEngagementUserValidation() {
    flagValidTotal = new Array();

    if ($.trim($('select#userTypeID option:selected').val()) != 0) {
        flagValidTotal.push(true);
        $("#userTypeID").removeClass("ux-form-field-error");
        $("#errUserTypeID").html("");
    } else {
        $("#userTypeID").addClass("ux-form-field-error");
        $("#errUserTypeID").html("Please select User Type");
        flagValidTotal.push(false);
    }

    if ($.trim($('select#appRoleKeyID option:selected').val()) != 0) {
        flagValidTotal.push(true);
        $("#appRoleKeyID").removeClass("ux-form-field-error");
        $("#errAppRoleKeyID").html("");
    } else {
        $("#appRoleKeyID").addClass("ux-form-field-error");
        $("#errAppRoleKeyID").html("Please select Role");
        flagValidTotal.push(false);
    }
    
    if ($.trim($('select#engUserID option:selected').val()) != 0) {
        flagValidTotal.push(true);
        $("#engUserID").removeClass("ux-form-field-error");
        $("#errEngUserID").html("");
    } else {
        $("#engUserID").addClass("ux-form-field-error");
        $("#errEngUserID").html("Please select User");
        flagValidTotal.push(false);
    }
   
    for (var i = 0; i < flagValidTotal.length; i++) {
        if (flagValidTotal[i] == false) {
            returnstring = flagValidTotal[i];
            flagValidTotal = new Array();
            break;
        } else {
            returnstring = true;
        }
    }
    return returnstring;
}

//Save Engagement User Validation
var sflagValidTotal = new Array();
var sreturnstring = false;
function saveServiceUserValidation() {
    sflagValidTotal = new Array();

    if ($.trim($('select#suserTypeID option:selected').val()) != 0) {
        sflagValidTotal.push(true);
        $("#suserTypeID").removeClass("ux-form-field-error");
        $("#errsUserTypeID").html("");
    } else {
        $("#suserTypeID").addClass("ux-form-field-error");
        $("#errsUserTypeID").html("Please select User Type");
        sflagValidTotal.push(false);
    }

    if ($.trim($('select#ptnrRoleKeyID option:selected').val()) != 0) {
        sflagValidTotal.push(true);
        $("#ptnrRoleKeyID").removeClass("ux-form-field-error");
        $("#errPtnrRoleKeyID").html("");
    } else {
        $("#ptnrRoleKeyID").addClass("ux-form-field-error");
        $("#errPtnrRoleKeyID").html("Please select Role");
        sflagValidTotal.push(false);
    }
    
    if ($.trim($('select#partnerOrg option:selected').val()) != 0) {
        sflagValidTotal.push(true);
        $("#partnerOrg").removeClass("ux-form-field-error");
        $("#errPartnerOrg").html("");
    } else {
        $("#partnerOrg").addClass("ux-form-field-error");
        $("#errPartnerOrg").html("Please select Organization");
        sflagValidTotal.push(false);
    }
    
    if ($.trim($('select#srvUser option:selected').val()) != 0) {
        sflagValidTotal.push(true);
        $("#srvUser").removeClass("ux-form-field-error");
        $("#errSrvUserID").html("");
    } else {
        $("#srvUser").addClass("ux-form-field-error");
        $("#errSrvUserID").html("Please select User");
        sflagValidTotal.push(false);
    }
    
    var stDate = new Date($('#startDateId').val());
    var edDate = new Date($('#endDateId').val());

    if ($.trim($('#startDateId').val()).length > 0) {
       if (stDate > edDate) {
            $("#startDateId").addClass("ux-form-field-error");
            $("#errStartDateId").html("Service Start Date cannot be later than Service End Date");
            sflagValidTotal.push(false);
        } else {
            sflagValidTotal.push(true);
            $("#startDateId").removeClass("ux-form-field-error");
            $("#errStartDateId").html("");
        }

    } else {
        $("#startDateId").addClass("ux-form-field-error");
        $("#errStartDateId").html("Please select Service Start Date");
        sflagValidTotal.push(false);
    }

    if ($.trim($('#endDateId').val()).length > 0) {
//       if (edDate > eenddate) {
//            $("#endDateId").addClass("ux-form-field-error");
//            $("#errEndDateId").html("Service End Date cannot be later than  Estimated End Date");
//            sflagValidTotal.push(false);
//        } else {
//            sflagValidTotal.push(true);
//            $("#endDateId").removeClass("ux-form-field-error");
//            $("#errEndDateId").html("");
//        }

    } else {
        $("#endDateId").addClass("ux-form-field-error");
        $("#errEndDateId").html("Please select Service End Date");
        sflagValidTotal.push(false);
    }
   
   var count = $("[type='checkbox']:checked").length;
   if(count <= 0){
        //$("#servicesLst").addClass("ux-form-field-error");
        $("#errservicesLstID").html("Please select atleast one service");
        sflagValidTotal.push(false);
   }else{
        sflagValidTotal.push(true);
        //$("#servicesLst").removeClass("ux-form-field-error");
        $("#errservicesLstID").html("");
   }
   
    for (var i = 0; i < sflagValidTotal.length; i++) {
        if (sflagValidTotal[i] == false) {
            sreturnstring = sflagValidTotal[i];
            sflagValidTotal = new Array();
            break;
        } else {
            sreturnstring = true;
        }
    }
    return sreturnstring;
}