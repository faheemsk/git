/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(window).load(function() {
    $(".loader").fadeOut("slow");
});

$(document).ready(function(){
    
    $(".refinetree").treeTable();
    
    $('#dataTable').dataTable({
        ordering: false,
        paging: true,
        info: false,
        "aoColumns": [
            {"bSortable": false},
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
            null,
            {type: "text"},
            {type: "text"},
            {type: "text"},
            {type: "text"},
            {type: "text"},
            null
        ]
    });
    
//    $("#headCheckbox").click(function(){
//        alert('aaa');
//       $('.checkboxGroup').attr('checked', this.checked); 
//    });
//    
//    $(".checkboxGroup").click(function(){
//        alert('sadsaa');
//       if($(".checkboxGroup").length == $(".case:checked").length){
//           $("#headCheckbox").attr("checked","checked");
//       } else {
//           $("#headCheckbox").removeAttr("checked");
//       }
//    });
    
});

$.fn.SearchFilterFindingList = function(obj, tableID) {
    $(tableID).dataTable().fnFilterClear("");
   
    var tds = $(obj).parent().parent().parent().children("td");
    $(tds).each(function() {
        var inputFeild = $(this).children().children("input").attr("type");
        if (typeof inputFeild !== "undefined") {
            var textObj = $(this).children().children("input");
            $(textObj).val("");
            $(textObj).trigger('blur');
        } else {
            inputFeild = $(this).children().children("select");
            if (typeof inputFeild !== "undefined") {
                $(inputFeild).val("");
            }
        }
    });
};

function cancelLookup(){
    document.findingLookupFrm.action = "remediationplan.htm";
    document.findingLookupFrm.submit();
}

function addRemediation(){
    //alert($('input:checkbox[name="selectedInstances"]').length);
//    if(){
//        
//    }else{
//        alert($('input[name=removeInstances]').attr('checked'));
//    }
if(findingCheck()){
    document.findingLookupFrm.action = "addremediationpage.htm";
    document.findingLookupFrm.submit();
    }
}

function addFindingToPlan(){
if(findingCheck()){
    document.findingLookupFrm.action = "addfindingstoplan.htm";
    document.findingLookupFrm.submit();
    }
}

function addFindingsToPlanAndRegistry() {
    if(findingCheck()){
    document.findingLookupFrm.action = "insPlanRegItems.htm";
    document.findingLookupFrm.submit();
}
}

function redirectToRegistryDetails() {
    document.findingLookupFrm.action = "riskRegistrySummary.htm";
    document.findingLookupFrm.submit();
}


//function checkAll(headCheckbox) {
//    var checkboxes = document.getElementsByClassName('checkboxGroup');
//    if (headCheckbox.checked) {
//        for (var i = 0; i < checkboxes.length; i++) {
//            if (checkboxes[i].type === 'checkbox') {
//                checkboxes[i].checked = true;
//            }
//        }
//    } else {
//        for (var i = 0; i < checkboxes.length; i++) {
//            if (checkboxes[i].type === 'checkbox') {
//                checkboxes[i].checked = false;
//            }
//        }
//    }
//}

function singleCheckbox(rowCheckbox) {
    var selId = "";
    var sep = "";
    $("input:checked",$('#dataTable').dataTable().fnGetNodes()).each(function(){
        selId += sep;
        selId += $(this).val();
        sep = ",";
    });
     $("#selInstanceDatatable").val(selId);
     
}

function findingCheck() {
    var flag = false;
    var checkboxes = new Array();
    $('.checkboxGroup').each(function() {
        if (this.checked) {
            var id = $(this).attr('id');
            checkboxes.push(id);
            flag = true;
            $("#errorCheckboxValidation").html("");
        }
    });
    if (!flag) {
        $("#errorCheckboxValidation").html("<font class='ux-msg-error-under'  color='red'>Please select at least one Finding</font>");
        return false;
    } else {
         return true;
    }
}