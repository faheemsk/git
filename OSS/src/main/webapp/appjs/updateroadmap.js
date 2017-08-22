/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(window).load(function() {
    $(".loader").fadeOut("slow");
})

$(document).ready(function() {

    //Update Roadmap
    $("#updateRoadmap").click(function() {
        if(validateUpdateRoadmap()){
            document.updateroadmap.submit();
        }
    });
    
    $("#searchFindings").click(function()
    {
        var enccode = $("#encEngagementCode").val();
        var pgno = $("#pgcnt").val();
        document.searEditForm.action = "editroadmap.htm?cgenk=" + enccode + "&pgcnt=" + pgno;
        document.searEditForm.submit();
    });
    
    $("#fnRefreshFindings").click(function()
    {
        document.refreshFindingListForm.action = "editroadmap.htm";
        document.refreshFindingListForm.submit();
    });
    
    $("#reviewVulnerabilityBreadcrumb").click(function()
    {
        $("#pgname").val("");
        document.cancelReviewVulnerabilityForm.action = "reviewvulnerability.htm";
        document.cancelReviewVulnerabilityForm.submit();
    });
    
});

//Start: For navigation when click on Cancel button
function toRoadmapSummary() {
    document.toRoadmapPage.action = "roadmapsummary.htm";
    document.toRoadmapPage.submit();
}
//End: For navigation when click on Cancel button

function toFindingsList() {
    document.updateroadmap.action = "reviewvulnerability.htm";
    document.updateroadmap.submit();
}

function editFinding(vk) {
    var encInstanceIdentifier = $("#encIdentifier" + vk).val();
    $("#encInstanceIdentifier").val(encInstanceIdentifier);
    document.searEditForm.action = "editvulnerability.htm";
    document.searEditForm.submit();
}


function viewFinding(vk) {
    var encInstanceIdentifier = $("#encIdentifier" + vk).val();
    $("#encInstanceIdentifier").val(encInstanceIdentifier);
    document.removeVulnerabilityForm.action = "viewreviewvulnerability.htm";
    document.removeVulnerabilityForm.submit();
}

function validateUpdateRoadmap(){
    flagArray = [];
    
    var comments = $("#roadmapComments").val();
    if (jQuery.trim(comments).length > 1000) {
        $("#errorRoadmapComments").html("Notes cannot exceed 1000 characters");
        flagArray.push(false);
    } else if (jQuery.trim(comments).length == 0) {
        $("#errorRoadmapComments").html("Please enter Notes");
        flagArray.push(false);
    } else {
        $("#errorRoadmapComments").html("");
        flagArray.push(true);
    }
    
    for (var i = 0; i < flagArray.length; i++) {
        if (flagArray[i] === false) {
            flag = flagArray[i];
            flagArray = new Array();
            break;
        } else {
            flag = true;
        }
    }
    return flag;
}

