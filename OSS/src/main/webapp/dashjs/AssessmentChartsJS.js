/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
FusionCharts.ready(function() {
//    var myChart4 = new FusionCharts({
//        "type": "bar2d",
//        "renderAt": "SecuritySeverity",
//        "width": "100%",
//        "height": "175",
//        "dataFormat": "xml",
//        "dataSource": "<chart caption='Severity' captionAlignment='left' captionFontColor='333333' bgAlpha='0' labelDisplay='wrap' maxLabelWidthPercent='40' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='10' chartRightMargin='0'   baseFont='arial'    showYAxisValues='0' labelFontSize='12' valueFontSize='12' labelPadding='13'  labelFontColor='6666666' showPlotBorder='0' yAxisMaxValue='60' maxBarHeight='15' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix=''  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' bgratio='0' showcanvasborder='0' animation='1'>   <set value='20' label='Critical' color='A32A2E'/> <set value='40' label='High' color='E87722' /> <set value='10' label='Medium' color='F2B411'  /> <set value='150' label='Low' color='627D32'  />   </chart>"
//    });
//    myChart4.render();






//    var myChart4 = new FusionCharts({
//        "type": "bar2d",
//        "renderAt": "SecurityOS",
//        "width": "100%",
//        "height": "175",
//        "dataFormat": "xml",
//        "dataSource": "<chart caption='Top 4 Operating systems' captionAlignment='left' captionFontColor='333333' bgAlpha='0' labelDisplay='wrap' maxLabelWidthPercent='40' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='10' chartRightMargin='0'    baseFont='arial'    showYAxisValues='0' labelFontSize='12' valueFontSize='12' labelPadding='13'  labelFontColor='6666666' showPlotBorder='0' yAxisMaxValue='60' maxBarHeight='15' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix=''  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' bgratio='0' showcanvasborder='0' animation='1'>   <set value='40' label='Windows' color='1965ae'/> <set value='30' label='Linux' color='4b8ab0' /> <set value='20' label='Mac' color='66b2b1'  /> <set value='10' label='OS Undetermined' color='a0d3c0'  />   </chart>"
//    });
//    myChart4.render();
//    var myChart4 = new FusionCharts({
//        "type": "bar2d",
//        "renderAt": "SecurityRoot",
//        "width": "100%",
//        "height": "175",
//        "dataFormat": "xml",
//        "dataSource": "<chart caption='Top 4 Root causes ' captionFontColor='333333' captionAlignment='left'  bgAlpha='0' labelDisplay='wrap' maxLabelWidthPercent='40' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='10' chartRightMargin='0'  baseFont='arial'    showYAxisValues='0' labelFontSize='12' valueFontSize='12' labelPadding='13'  labelFontColor='6666666' showPlotBorder='0' yAxisMaxValue='60' maxBarHeight='15' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix=''  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' bgratio='0' showcanvasborder='0' animation='1'>  <set value='45' label='Lack of patch management process/Patch validation' color='523995'/> <set value='30' label='Lack of secure code - parameter policy/Validation' color='6a6ab3' /> <set value='15'  label='Lack of security configuration policy/Validation' color='8d99cd'  /> <set value='10'  label='Lack of security configuration policy/Validation' color='bbc0e9'  /></chart>"
//    });
//    myChart4.render();
//    var myChart4 = new FusionCharts({
//        "type": "stackedbar2d",
//        "renderAt": "topfivehosts",
//        "width": "100%",
//        "height": "180",
//        "dataFormat": "xml",
//        "dataSource": "<chart caption='' captionFontColor='333333' stack100Percent='1' captionAlignment='left'  bgAlpha='0' labelDisplay='wrap' maxLabelWidthPercent='40' showTooltip='1' toolTipColor= '666666' toolTipBorderThickness= '1' toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='0' chartRightMargin='0'  baseFont='arial'    showYAxisValues='0' labelFontSize='12' valueFontSize='12' labelPadding='13'  labelFontColor='6666666' showPlotBorder='0' yAxisMaxValue='60' plotSpacePercent='30' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='0' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix=''  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' bgratio='0' showcanvasborder='0' animation='1'>  <categories><category label='192.168.234.3'/><category label='192.168.235.4'/><category label='192.168.236.5'/><category label='192.168.237.6'/><category label='192.168.238.7'/></categories><dataset seriesName='Critical' color='A32A2E'><set value='3'/><set value='0'/><set value='1'/><set value='0'/><set value='0'/></dataset><dataset seriesName='High' color='E87722'><set value='29'/><set value='0'/><set value='8'/><set value='0'/><set value='0'/></dataset><dataset seriesName='Medium' color='F2B411'><set value='46'/><set value='33'/><set value='15'/><set value='19'/><set value='18'/></dataset><dataset seriesName='Low' color='627D32'><set value='5'/><set value='7'/><set value='4'/><set value='4'/><set value='5'/></dataset></chart>"
//    });
//    myChart4.render();

//var myChart4 = new FusionCharts({
//    "type": "pareto2d",
//    "renderAt": "priorityrem",
//    "width": "100%",
//    "height": "175",
//    "dataFormat": "xml",
//    "dataSource": "<chart caption='' captionAlignment='left' captionFontColor='333333' pYAxisNameFontSize='14' pYAxisName='No. of Affected Hosts ' bgAlpha='0'  labelDisplay='wrap' maxLabelHeight='50' showTooltip='1' maxColWidth='15' toolTipColor= '666666' toolTipBorderThickness= '1'  toolTipBgColor= 'ffffff' toolTipBgAlpha= '80' toolTipBorderRadius= '2' toolTipPadding= '5' numDivLines='0' chartBottomMargin='0' chartLeftMargin='0' chartTopMargin='0' chartRightMargin='0'   baseFont='arial'    showYAxisValues='0' labelFontSize='12' valueFontSize='12' labelPadding='13'  labelFontColor='6666666' showPlotBorder='0' yAxisMaxValue='190' maxBarHeight='15' plotGradientcolor='' showShadow='0'  use3DLighting='0' legendShadow='0' showLabels='1' showValues='1' showLegend='0' legendBorderAlpha='0' legendPosition='right'    numberSuffix=''  rightmargin='0' bgcolor='ECF5FF'  bordercolor='' basefontcolor='666666' basefontsize='10' bgColor='ffffff' borderThickness='0' borderColor='ffffff'  showpercentvalues='0' bgratio='0' showcanvasborder='1' canvasBorderColor='ffffff'  showBorder='0'  canvasBorderAlpha='0'  animation='1'>   <set value='300' label='VMware ESX Server Service Console Vulnerability' color='f47b29'/> <set value='160' label='IBM Tivoli Storage Manager Multiple Vulnerabilities' color='a22b38' /> <set value='90' label='Apple Mac OS X Unspecified Java Vulnerability' color='f47b29'  /> <set value='150' label='Cisco ASA TLS Fragment DoS' color='a22b38'  /> <set value='110' label='OpenSUSE Moodle Prior to 1.9.8/1.8.12 Multiple Vulnerabilities' color='f47b29'  />   </chart>"
//  });  myChart4.render();

});

FusionCharts.ready(function() {
    $.ajax("assessmentTop5Carts.htm", {
//    dataType: "text",
        cache: false,
        contentType: 'application/json',
        accepts: {
            text: "application/json"
        },
        success: loadChart1
    });

    function loadChart1(jsonString) {
        var myChart4 = new FusionCharts({
            "type": "stackedbar2d",
            "renderAt": "topfivehosts",
            "width": "100%",
            "height": "180",
            "dataFormat": "xml",
            "dataSource": htmlDecode(jsonString[0])

        }).render();

    }


});



function htmlDecode(input){
  var e = document.createElement('div');
  e.innerHTML = input;
  return e.childNodes.length === 0 ? "" : e.childNodes[0].nodeValue;
}

FusionCharts.ready(function() {
    $.ajax("topOSDetails.htm", {
//    dataType: "text",
        cache: false,
        contentType: 'application/json',
        accepts: {
            text: "application/json"
        },
        success: loadChart3
    });

    function loadChart3(jsonString) {
        var myChart4 = new FusionCharts({
            "type": "bar2d",
            "renderAt": "SecurityOS",
            "width": "100%",
            "height": "175",
            "dataFormat": "xml",
            "dataSource": htmlDecode(jsonString[0])

        }).render();

    }


});



FusionCharts.ready(function() {
    $.ajax("topHitrustDetails.htm", {
//    dataType: "text",
        cache: false,
        contentType: 'application/json',
        accepts: {
            text: "application/json"
        },
        success: loadChart4
    });

    function loadChart4(jsonString) {
        var myChart4 = new FusionCharts({
            "type": "bar2d",
            "renderAt": "hitrust",
            "width": "100%",
            "height": "175",
            "dataFormat": "xml",
            "dataSource":  htmlDecode(jsonString[0])

        }).render();

    }


});
