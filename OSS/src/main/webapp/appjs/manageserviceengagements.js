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
    $("#emwl").addClass("ux-vnav-submenu-closed");
    $("#emwl").removeClass("ux-vnav-selected");
    $("#meng").removeClass("ux-vnav-selected");

    $("#edu").addClass("ux-vnav-selected");
    
    $(".example").treeTable();
    $('.fixme').fixheadertable({
        //caption : 'My header is fixed !',
       colratio: [60, 120, 120, , 120, 120, 120, ],
        zebra: false,
        //height: 400,
        whiteSpace: 'normal',
        resizeCol: false,
    });

    $("#searchButton").click(function() {
        document.servicesSearchForm.action = "searchEngServiceWorkList.htm";
        document.servicesSearchForm.submit();
    });
    
});

$(document).ready(function() {
    $("#infresults").hide();
    $("#showinf").click(function()
    {
        $("#infresults").show();

    });
    $("#searchresults").hide();
    $("#SearchId").click(function()
    {
        $("#searchresults").show();

    });
});

$(function() {
    $('.defaultActualPicker').datepicker({
        defaultDate: new Date(), buttonImage: '<img src="images/calendar01.gif" title="Calendar" alt="Popup" class="ux-margin-left-min trigger">'});

});

function showDate(date) {
    alert('The date chosen is ' + date);
}

function navigateToEditPage(eid) {
        $("#encS").val(eid);
        //alert(eid);
          document.servicesSearchForm.action = "engagemenetDataUpload.htm";
        document.servicesSearchForm.submit();
        
        
    }
function viewPage(eid){
    $("#encS").val(eid);
        //alert(eid);
          document.servicesSearchForm.action = "viewSericeData.htm";
        document.servicesSearchForm.submit();
        
}