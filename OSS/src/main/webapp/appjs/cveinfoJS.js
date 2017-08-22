/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




$(document).ready(function() {
    
   
    $('#hitrustDataTable').dataTable({
        ordering: false,
        paging: false,
        info: false,
        "sScrollY": "300"
    });
    
    $('.dataTables_scrollHeadInner,.dataTables_scrollHeadInner table').width('100%');

    if ($("#checkedHitrustValues").val() !== "") {
        var chkValues = $("#checkedHitrustValues").val();
        var chkArray = chkValues.split(',');
        $.each(chkArray, function(index, item) {
            $('input[type="checkbox"][class^="objectiveCheck"]').each(function(i) {
                if ($(this).attr('id') === item) {
                    $(this).attr('checked', true);
                }

            });

        });
    }
    
    $('input[type="checkbox"][class^="familyCheckAll"]').each(function () {
        var checkboxResult = [];
        var selectedCheckboxes = [];
        var fvar = $(this).attr('fId');
        var familyCheckBox = document.getElementById('familyCheckBox' + fvar);
        $('.objectiveCheck' + fvar).each(function () {
            if (this.checked) {
                var id = $(this).attr('id');
                selectedCheckboxes.push(id);
            }
        });
        $('.objectiveCheck' + fvar).each(function () {
            var id = $(this).attr('id');
            checkboxResult.push(id);
        });
        if (selectedCheckboxes.length === checkboxResult.length && !familyCheckBox.checked) {
            familyCheckBox.checked = true;
        }

    });


    $("#cvsscalcbtn").click(function()
    {
        if (nicEditors.findEditor("vulnerabilityDescription").getContent() === '<br>'){
            $("#vulnerabilityDescription").val("");
        } else {
            $("#vulnerabilityDescription").val(nicEditors.findEditor("vulnerabilityDescription").getContent());
        }

        if (nicEditors.findEditor("technicalDetails").getContent() === '<br>'){
            $("#technicalDetails").val("");
        } else {
            $("#technicalDetails").val(nicEditors.findEditor("technicalDetails").getContent());
        }

        if (nicEditors.findEditor("impactDetail").getContent() === '<br>'){
            $("#impactDetail").val("");
        } else {
            $("#impactDetail").val(nicEditors.findEditor("impactDetail").getContent());
        }

        if (nicEditors.findEditor("recommendationId").getContent() === '<br>'){
            $("#recommendationId").val("");
        } else {
            $("#recommendationId").val(nicEditors.findEditor("recommendationId").getContent());
        }
        document.addnewvulnerability.action = "vcalculator.htm";
        document.addnewvulnerability.submit();
    });


});

function getCveInfo() {
    if (nicEditors.findEditor("vulnerabilityDescription").getContent() === '<br>'){
        $("#vulnerabilityDescription").val("");
    } else {
        $("#vulnerabilityDescription").val(nicEditors.findEditor("vulnerabilityDescription").getContent());
    }

    if (nicEditors.findEditor("technicalDetails").getContent() === '<br>'){
        $("#technicalDetails").val("");
    } else {
        $("#technicalDetails").val(nicEditors.findEditor("technicalDetails").getContent());
    }

    if (nicEditors.findEditor("impactDetail").getContent() === '<br>'){
        $("#impactDetail").val("");
    } else {
        $("#impactDetail").val(nicEditors.findEditor("impactDetail").getContent());
    }

    if (nicEditors.findEditor("recommendationId").getContent() === '<br>'){
        $("#recommendationId").val("");
    } else {
        $("#recommendationId").val(nicEditors.findEditor("recommendationId").getContent());
    }

    $('notSelectedIds').val("");
    if($("#atcveId").val() === ""){
         $("#errorMsgAtcveId").html("Please enter the CVE ID");
         $("#cveHubMessage").remove();
    }
    else{
    document.addnewvulnerability.action = "addCVEInformation.htm#cvein";
    document.addnewvulnerability.submit();
}
}
function familyCheckAll(familyCheckbox, familyCode) {
    if (familyCheckbox.checked) {
        $('.objectiveCheck' + familyCode).each(function() {
            this.checked = true;
        });
    } else {
        $('.objectiveCheck' + familyCode).each(function() {
            this.checked = false;
        });
    }
}

function objectiveSingleCheck(objCheck, familyCode) {

    var checkboxResult = [];
    var selectedCheckboxes = [];
    var familyCheckBox = document.getElementById('familyCheckBox' + familyCode);
    $('.objectiveCheck' + familyCode).each(function() {
        if (this.checked) {
            var id = $(this).attr('id');
            selectedCheckboxes.push(id);
        }
    });
    $('.objectiveCheck' + familyCode).each(function() {
        var id = $(this).attr('id');
        checkboxResult.push(id);
    });

    if (familyCheckBox.checked && !objCheck.checked) {
        familyCheckBox.checked = false;
    }
    else if (selectedCheckboxes.length === checkboxResult.length && !familyCheckBox.checked) {
        familyCheckBox.checked = true;
    }
}

/*
 * CALCULATOR RELATED SCRIPTS
 * ADDED BY HEMACHANDRA
 // */

//This function is used to Add CVE to the Add/Edit Vulnerability page
function addCVEtoPage(){
    if (nicEditors.findEditor("vulnerabilityDescription").getContent() === '<br>'){
        $("#vulnerabilityDescription").val("");
    } else {
        $("#vulnerabilityDescription").val(nicEditors.findEditor("vulnerabilityDescription").getContent());
    }

    if (nicEditors.findEditor("technicalDetails").getContent() === '<br>'){
        $("#technicalDetails").val("");
    } else {
        $("#technicalDetails").val(nicEditors.findEditor("technicalDetails").getContent());
    }

    if (nicEditors.findEditor("impactDetail").getContent() === '<br>'){
        $("#impactDetail").val("");
    } else {
        $("#impactDetail").val(nicEditors.findEditor("impactDetail").getContent());
    }

    if (nicEditors.findEditor("recommendationId").getContent() === '<br>'){
        $("#recommendationId").val("");
    } else {
        $("#recommendationId").val(nicEditors.findEditor("recommendationId").getContent());
    }
    
    var selectedCVE = $('input[name=cveid]:checked').val();

    if(!$("input:radio[name=cveid]").is(":checked")){
         $("#cvePopErrMessage").html("Please select the CVE ID");
    }else{
        cvepopup(0);
        $("#atcveId").val(selectedCVE);
        document.addnewvulnerability.action = "addCVEInformation.htm#cvein";
        document.addnewvulnerability.submit();
    }
}

function addHitrust() {
    if (nicEditors.findEditor("vulnerabilityDescription").getContent() === '<br>'){
        $("#vulnerabilityDescription").val("");
    } else {
        $("#vulnerabilityDescription").val(nicEditors.findEditor("vulnerabilityDescription").getContent());
    }

    if (nicEditors.findEditor("technicalDetails").getContent() === '<br>'){
        $("#technicalDetails").val("");
    } else {
        $("#technicalDetails").val(nicEditors.findEditor("technicalDetails").getContent());
    }

    if (nicEditors.findEditor("impactDetail").getContent() === '<br>'){
        $("#impactDetail").val("");
    } else {
        $("#impactDetail").val(nicEditors.findEditor("impactDetail").getContent());
    }

    if (nicEditors.findEditor("recommendationId").getContent() === '<br>'){
        $("#recommendationId").val("");
    } else {
        $("#recommendationId").val(nicEditors.findEditor("recommendationId").getContent());
    }
    selControlCodes();
    var values = new Array();
//    if($("#checkedHitrustValues").val() !== ""){
//        values.push($("#checkedHitrustValues").val());
//    }

    $('input[type="checkbox"][class^="objectiveCheck"]').each(function(i) {
        if (this.checked) {
            var id = $(this).attr('id');
            values.push(id);
        }

    });
    $("#checkedHitrustValues").val(values);
    document.addnewvulnerability.action = "addHitrust.htm#compliance";
    document.addnewvulnerability.submit();
}

function cancelHitrust() {
    selControlCodes();
    document.addnewvulnerability.action = "addHitrust.htm#compliance";
    document.addnewvulnerability.submit();
}

function findHitrustLookup(){
    if (nicEditors.findEditor("vulnerabilityDescription").getContent() === '<br>'){
        $("#vulnerabilityDescription").val("");
    } else {
        $("#vulnerabilityDescription").val(nicEditors.findEditor("vulnerabilityDescription").getContent());
    }

    if (nicEditors.findEditor("technicalDetails").getContent() === '<br>'){
        $("#technicalDetails").val("");
    } else {
        $("#technicalDetails").val(nicEditors.findEditor("technicalDetails").getContent());
    }

    if (nicEditors.findEditor("impactDetail").getContent() === '<br>'){
        $("#impactDetail").val("");
    } else {
        $("#impactDetail").val(nicEditors.findEditor("impactDetail").getContent());
    }

    if (nicEditors.findEditor("recommendationId").getContent() === '<br>'){
        $("#recommendationId").val("");
    } else {
        $("#recommendationId").val(nicEditors.findEditor("recommendationId").getContent());
    }
    selControlCodes();
    var hitrustLookupID = $("#hitrustLookupID").val();
//    var re = new RegExp("^(\d{0,2}(\.[a-z]{0,1}))$");
     var values = new Array();
    if($("#checkedHitrustValues").val() !== ""){
        values.push($("#checkedHitrustValues").val());
    }
var re = /^(\d{0,2}(\.[a-z]{0,1}))$/;
if (re.test(hitrustLookupID)) {
        values.push(hitrustLookupID);
         $("#checkedHitrustValues").val(values);
         
     document.addnewvulnerability.action = "addHitrust.htm#compliance";
    document.addnewvulnerability.submit();
} else {
    $("#hitrusterrorId").html("Please enter a valid Family Control Code");

//     alert("Please Enter Valid Family Control Code.");
}

}

function selControlCodes(){
    var delim = "";
    var selVal = "";
    $('.selectedCtlCd').each(function() {
        if (!this.checked) {
            selVal += delim;
            var id = $(this).attr('value');
            selVal += id;
            delim = ",";
        }
    });
    $('#notSelectedIds').val(selVal);
}

function cvepopup(sw) {
    $("#cveTable tbody").empty();
    $("#cvepagination").hide();
    $("#addCVEIdToPg").hide();
    $("#cveDesc").val("");  
    $("#selCVEid").val("");
    $("#cvePopErrMessage").html("");
    $("#searchErrMessage").html("");
    
    $("#darrow").hide();
    $("#uarrow").hide();
        
    if (sw == 1) {
        // Show popup
        document.getElementById('blackcover').style.visibility = 'visible';
        document.getElementById('popupbox1').style.visibility = 'visible';
        document.getElementById('blackcover').style.display = 'block';
        document.getElementById('popupbox1').style.display = 'block';
    } else {
        // Hide popup
        document.getElementById('blackcover').style.visibility = 'hidden';
        document.getElementById('popupbox1').style.visibility = 'hidden';
        document.getElementById('blackcover').style.display = 'none';
        document.getElementById('popupbox1').style.display = 'none';
    }
}
function blockSpecialChar(e){
        var k;
        document.all ? k = e.keyCode : k = e.which;
       
        return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || k == 32 || (k >= 48 && k <= 57) || k == 45);
    }           