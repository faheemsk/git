/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var oImpact = 0;
var cImpact = 0;
var iImpact = 0;
var aImpact = 0;

var expSc = 0;
var av = 0;
var ac = 0;
var au = 0;

var expScr = 0;
var remeScr = 0;
var reptConf = 0;


var adjImpt = 0;

var confReq = 0;
var integReq = 0;
var availReq = 0;
var targDist = 0;
var collDamage = 0;


var baseScr = 0;
var tempScr = 0;
var envScr = 0;

$(document).ready(function() {
    
    $("#iSubScore").html("NA");
    $("#expSubScre").html("NA");
    $("#baseScr").html("NA");
    $("#iTempScr").html("NA");
    $("#iModSubScore").html("NA");
    $("#iEnvScr").html("NA");


    
    fnCalculateImpactSS();
    fnCalculateExpSS();
    fnCalcBaseSS();
    fnCalcTempSS();
    fnCalculateEnvSS();
    
    $("input[name=ConfidentialityImpact]").click(function() {
        fnCalculateImpactSS();
        fnCalcBaseSS();
    });
    $("input[name=IntegrityImpact]").click(function() {
        fnCalculateImpactSS();
        fnCalcBaseSS();
    });
    $("input[name=AvailabilityImpact]").click(function() {
        fnCalculateImpactSS();
        fnCalcBaseSS();
    });


    $("input[name=AccessVector]").click(function() {
        fnCalculateExpSS();
        fnCalcBaseSS();
    });
    $("input[name=AccessComplexity]").click(function() {
        fnCalculateExpSS();
        fnCalcBaseSS();
    });
    $("input[name=Authentication]").click(function() {
        fnCalculateExpSS();
        fnCalcBaseSS();
    });


    $("input[name=Exploitability]").click(function() {
        fnCalcTempSS();
    });
    $("input[name=RemediationLevel]").click(function() {
        fnCalcTempSS();
    });
    $("input[name=ReportConfidence]").click(function() {
        fnCalcTempSS();
    });


    $("input[name=ConfidentialityRequirement]").click(function() {
        fnCalculateEnvSS();
    });
    $("input[name=IntegrityRequirement]").click(function() {
        fnCalculateEnvSS();
    });
    $("input[name=AvailabilityRequirement]").click(function() {
        fnCalculateEnvSS();
    });

    $("input[name=TargetDistribution]").click(function() {
        fnCalculateEnvSS();
    });
    $("input[name=CollateralDamagePotential]").click(function() {
        fnCalculateEnvSS();
    });


    $("#clrFrm").click(function() {

        $("input:radio[name='ConfidentialityImpact']").each(function() {
            this.checked = false;
        });
        $("input:radio[name='IntegrityImpact']").each(function() {
            this.checked = false;
        });
        $("input:radio[name='AvailabilityImpact']").each(function() {
            this.checked = false;
        });

        $("input:radio[name='AccessVector']").each(function() {
            this.checked = false;
        });
        $("input:radio[name='AccessComplexity']").each(function() {
            this.checked = false;
        });
        $("input:radio[name='Authentication']").each(function() {
            this.checked = false;
        });

        $("input[name=Exploitability]").each(function() {
            this.checked = false;
        });
        $("input[name=RemediationLevel]").each(function() {
            this.checked = false;
        });
        $("input[name=ReportConfidence]").each(function() {
            this.checked = false;
        });


        $("input[name=ConfidentialityRequirement]").each(function() {
            this.checked = false;
        });
        $("input[name=IntegrityRequirement]").each(function() {
            this.checked = false;
        });
        $("input[name=AvailabilityRequirement]").each(function() {
            this.checked = false;
        });

        $("input[name=TargetDistribution]").each(function() {
            this.checked = false;
        });
        $("input[name=CollateralDamagePotential]").each(function() {
            this.checked = false;
        });
        
        $("input:radio").attr("checked", false);
        $("#iSubScore").html("NA");
        $("#expSubScre").html("NA");
        $("#baseScr").html("NA");
        $("#iTempScr").html("NA");
        $("#iModSubScore").html("NA");
        $("#iEnvScr").html("NA");
        $("#basVctr").html("");
        $("#CVSSVector").val("");//To clear the vector text
        
        document.getElementById("calcForm").reset();

    });

    $("#backToReview").click(function()
    {
        document.calcform.action="bkfrmcalculator.htm";
        document.calcform.submit();
    });
    
    $("#updScr").click(function()
    {
        document.calcform.action="updcalcvals.htm";
        document.calcform.submit();
    });
    
    $("#shwEquations").click(function()
    {
        javascript:popup(1);
    });
    
    //Start: Breadcrumb navigation to Review Vulnerability page
    $("#fnReviewBreadCrumb").click(function()
    {
        document.calcform.action="reviewvulnerability.htm";
        document.calcform.submit();
    });
    //End: Breadcrumb navigation to Review Vulnerability page

});

function fnCalculateImpactSS()
{
    if ($("input[name='ConfidentialityImpact']").is(':checked'))
    {
        cImpact = $('input[name=ConfidentialityImpact]:checked').val();
    }
    if ($("input[name='IntegrityImpact']").is(':checked'))
    {
        iImpact = $('input[name=IntegrityImpact]:checked').val();
    }
    if ($("input[name='AvailabilityImpact']").is(':checked'))
    {
        aImpact = $('input[name=AvailabilityImpact]:checked').val();
    }

    //alert("cImpact:"+cImpact+"<<>>iImpact:"+iImpact+"<<>>aImpact:"+aImpact);

    var impscCnt = $("#impsc :radio:checked").length;
    if(impscCnt===0){
        $("#iSubScore").html("NA");
    }
    else
    {
        oImpact = 10.41 * (1 - (1 - cImpact) * (1 - iImpact) * (1 - aImpact));
        oImpact = oImpact.toFixed(1);
        $("#iSubScore").html(oImpact);
    }
    //$("#iSubScore").html("");
    //$("#iSubScore").html(oImpact);
}

function fnCalculateExpSS()
{
    if ($("input[name='AccessVector']").is(':checked'))
    {
        av = $('input[name=AccessVector]:checked').val();
    }
    if ($("input[name='AccessComplexity']").is(':checked'))
    {
        ac = $('input[name=AccessComplexity]:checked').val();
    }
    if ($("input[name='Authentication']").is(':checked'))
    {
        au = $('input[name=Authentication]:checked').val();
    }

    //alert("au:"+au+"<<>>ac:"+ac+"<<>>av:"+av);

    
    
    var expscCnt = $("#expsc :radio:checked").length;
    if(expscCnt===0){
        $("#expSubScre").html("NA");
    }
    else
    {
        expSc = 20 * ac * au * av;
        expSc = expSc.toFixed(1);
        $("#expSubScre").html(expSc);
    }
    //$("#expSubScre").html("");
    //$("#expSubScre").html(expSc);

}

function fnCalcBaseSS()
{
    var impscCnt = $("#impsc :radio:checked").length;
    var expscCnt = $("#expsc :radio:checked").length;
    if(impscCnt!==0 && expscCnt!==0)
    {
       // alert('in');
        var fImpact = 0;
        if (oImpact <= 0)
        {
            fImpact = 0;
        }
        else
        {
            fImpact = 1.176;
        }
        baseScr = (0.6 * oImpact + 0.4 * expSc - 1.5) * fImpact;
        baseScr = baseScr.toFixed(1);
        $("#baseScr").html(baseScr);
    }
    else
    {
        $("#baseScr").html("NA");
    }
    //$("#baseScr").html("");
   //$("#baseScr").html(baseScr);

    fnBuildVector();

    fnCalcTempSS();
    fnCalculateEnvSS();
}


function fnCalcTempSS()
{
    if ($("input[name='Exploitability']").is(':checked'))
    {
        expScr = $('input[name=Exploitability]:checked').val();
    }
    if ($("input[name='RemediationLevel']").is(':checked'))
    {
        remeScr = $('input[name=RemediationLevel]:checked').val();
    }
    if ($("input[name='ReportConfidence']").is(':checked'))
    {
        reptConf = $('input[name=ReportConfidence]:checked').val();
    }

    //alert("expScr:"+expScr+"<<>>remeScr:"+remeScr+"<<>>reptConf:"+reptConf);

    var tempscCnt = $("#tempsc :radio:checked").length; 
    if(tempscCnt===0){
        $("#iTempScr").html("NA");
    }
    else
    {
        tempScr = baseScr * expScr * remeScr * reptConf;
        tempScr = tempScr.toFixed(1);

        $("#iTempScr").html("");
        $("#iTempScr").html(tempScr);
    }
    
    fnBuildVector();
}


function fnCalculateEnvSS()
{
    if ($("input[name='ConfidentialityRequirement']").is(':checked'))
    {
        confReq = $('input[name=ConfidentialityRequirement]:checked').val();
    }
    if ($("input[name='IntegrityRequirement']").is(':checked'))
    {
        integReq = $('input[name=IntegrityRequirement]:checked').val();
    }
    if ($("input[name='AvailabilityRequirement']").is(':checked'))
    {
        availReq = $('input[name=AvailabilityRequirement]:checked').val();
    }

    if ($("input[name='TargetDistribution']").is(':checked'))
    {
        targDist = $('input[name=TargetDistribution]:checked').val();
    }
    if ($("input[name='CollateralDamagePotential']").is(':checked'))
    {
        collDamage = $('input[name=CollateralDamagePotential]:checked').val();
    }

    //alert("confReq:"+confReq+"<<>>integReq:"+integReq+"<<>>availReq:"+availReq);
    //alert("collDamage::"+collDamage);
    adjImpt = 10.41 * (1 - (1 - (cImpact * confReq)) * (1 - (iImpact * integReq)) * (1 - (availReq * aImpact)));

    if (adjImpt >= 10)
    {
        adjImpt = 10;
    }
    adjImpt = adjImpt.toFixed(1);
    //alert("adjImpt::"+adjImpt);
    var fnAdjImpt=0;
    if(adjImpt > 0)
    {
        fnAdjImpt = 1.176;
    }
    else
    {
        fnAdjImpt = 0;
    }

    var adjBaseScore = (0.6 * adjImpt + 0.4 * expSc - 1.5) * fnAdjImpt;
    adjBaseScore = adjBaseScore.toFixed(1);
    //alert("adjBaseScore::"+adjBaseScore);

    var adjTempScore = adjBaseScore * expScr * remeScr * reptConf;
    adjTempScore = adjTempScore.toFixed(1);
    //alert("adjTempScore::"+adjTempScore);
    
    var modImpt = 10 - adjTempScore;
    envScr = (+ adjTempScore + (modImpt * collDamage)) * targDist;
    //alert("envScr::"+envScr);
    envScr = envScr.toFixed(1);
        
    var imsubmodCnt = $("#imsubmod :radio:checked").length; 
    if(imsubmodCnt===0){
        $("#iModSubScore").html("NA");
    }
    else
    {
        $("#iModSubScore").html("");
        $("#iModSubScore").html(adjImpt);
    }
    
    var envscCnt = $("#envsc :radio:checked").length; 
    if(envscCnt===0){
        $("#iEnvScr").html("NA");
    }
    else
    {
        $("#iEnvScr").html("");
        $("#iEnvScr").html(envScr);
    }
    
    fnBuildVector();
}

function fnBuildVector()
{
    var chkBool = 0;
    var vectorBld ='';
    if ($("input[name='AccessVector']").is(':checked'))
    {
        chkBool++;
        vectorBld+= $('input[type=radio][name=AccessVector]:checked').attr('id');
    }
    if ($("input[name='AccessComplexity']").is(':checked'))
    {
        chkBool++;
        vectorBld+= "/"+$('input[type=radio][name=AccessComplexity]:checked').attr('id');
    }
    if ($("input[name='Authentication']").is(':checked'))
    {
        chkBool++;
        vectorBld+= "/"+$('input[type=radio][name=Authentication]:checked').attr('id');
    }
    if ($("input[name='ConfidentialityImpact']").is(':checked'))
    {
        chkBool++;
        vectorBld+= "/"+$('input[type=radio][name=ConfidentialityImpact]:checked').attr('id');
    }
    if ($("input[name='IntegrityImpact']").is(':checked'))
    {
        chkBool++;
        vectorBld+= "/"+$('input[type=radio][name=IntegrityImpact]:checked').attr('id');
    }
    if ($("input[name='AvailabilityImpact']").is(':checked'))
    {
        chkBool++;
        vectorBld+= "/"+$('input[type=radio][name=AvailabilityImpact]:checked').attr('id');
    }
    
    if(chkBool >=6)
    {
        $("#basVctr").html("");
        $("#basVctr").html('('+vectorBld+')');
        
        if ($("input[name='Exploitability']").is(':checked'))
        {
            vectorBld+= "/"+$('input[type=radio][name=Exploitability]:checked').attr('id');
            $("#basVctr").html("");
            $("#basVctr").html('('+vectorBld+')');
        }
        if ($("input[name='RemediationLevel']").is(':checked'))
        {
            vectorBld+= "/"+$('input[type=radio][name=RemediationLevel]:checked').attr('id');
            $("#basVctr").html("");
            $("#basVctr").html('('+vectorBld+')');
        }
        if ($("input[name='ReportConfidence']").is(':checked'))
        {
            vectorBld+= "/"+$('input[type=radio][name=ReportConfidence]:checked').attr('id');
            $("#basVctr").html("");
            $("#basVctr").html('('+vectorBld+')');
        }
        
        if ($("input[name='CollateralDamagePotential']").is(':checked'))
        {
            vectorBld+= "/"+$('input[type=radio][name=CollateralDamagePotential]:checked').attr('id');
            $("#basVctr").html("");
            $("#basVctr").html('('+vectorBld+')');
        }
        
        if ($("input[name='TargetDistribution']").is(':checked'))
        {
            vectorBld+= "/"+$('input[type=radio][name=TargetDistribution]:checked').attr('id');
            $("#basVctr").html("");
            $("#basVctr").html('('+vectorBld+')');
        }
        
        if ($("input[name='ConfidentialityRequirement']").is(':checked'))
        {
            vectorBld+= "/"+$('input[type=radio][name=ConfidentialityRequirement]:checked').attr('id');
            $("#basVctr").html("");
            $("#basVctr").html('('+vectorBld+')');
        }
        if ($("input[name='IntegrityRequirement']").is(':checked'))
        {
            vectorBld+= "/"+$('input[type=radio][name=IntegrityRequirement]:checked').attr('id');
            $("#basVctr").html("");
            $("#basVctr").html('('+vectorBld+')');
        }
        if ($("input[name='AvailabilityRequirement']").is(':checked'))
        {
            vectorBld+= "/"+$('input[type=radio][name=AvailabilityRequirement]:checked').attr('id');
            $("#basVctr").html("");
            $("#basVctr").html('('+vectorBld+')');
        }
        
        $("#CVSSVector").val('');
        $("#CVSSVector").val('('+vectorBld+')');
        
    }
}