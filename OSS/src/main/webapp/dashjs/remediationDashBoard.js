var jsScope;
angular.module("UitkApp")

        .controller('UitkCtrl', function($scope, $http, $translate, $timeout) {
            jsScope = $scope;

            $("#loading").css({"display": "none"});

        })


function htmlDecode(input) {
    var e = document.createElement('div');
    e.innerHTML = input;
    return e.childNodes.length === 0 ? "" : e.childNodes[0].nodeValue;
}


FusionCharts.ready(function() {
    $.ajax("remediationPlanCompletion.htm", {
        cache: false,
        contentType: 'application/json',
        accepts: {
            text: "application/json"
        },
        success: remediationPlanCompletion
    });

    function remediationPlanCompletion(jsonString) {
        var myChart4 = new FusionCharts({
            "type": "pie2d",
            "renderAt": "riskreg1",
            "width": "100%",
            "height": "190",
            "dataFormat": "xml",
            "dataSource": htmlDecode(jsonString[0])

        }).render();
        $("#loading").css({"display": "none"});
    }
});
//
FusionCharts.ready(function() {
    $.ajax("remediationOwnerDataByPlanStatus.htm", {
//    dataType: "text",
        cache: false,
        contentType: 'application/json',
        accepts: {
            text: "application/json"
        },
        success: remediationOwnerDataByPlanStatus
    });

    function remediationOwnerDataByPlanStatus(jsonString) {
        var myChart4 = new FusionCharts({
            "type": "stackedbar2d",
            "renderAt": "remplansowner",
            "width": "100%",
            "height": "180",
            "dataFormat": "xml",
            "dataSource": htmlDecode(jsonString[0])

        }).render();
        $("#loading").css({"display": "none"});
    }
});
FusionCharts.ready(function() {
    $.ajax("remediationPlansByStatusAndSeverity.htm", {
//    dataType: "text",
        cache: false,
        contentType: 'application/json',
        accepts: {
            text: "application/json"
        },
        success: remediationPlansByStatusAndSeverity
    });

    function remediationPlansByStatusAndSeverity(jsonString) {
        var myChart4 = new FusionCharts({
            "type": "stackedcolumn2d",
            "renderAt": "remplans",
            "width": "100%",
            "height": "190",
            "dataFormat": "xml",
            "dataSource": htmlDecode(jsonString[0])

        }).render();
        $("#loading").css({"display": "none"});
    }
});




FusionCharts.ready(function() {
    $.ajax("remediationPlansByDaysAndSeverity.htm", {
//    dataType: "text",
        cache: false,
        contentType: 'application/json',
        accepts: {
            text: "application/json"
        },
        success: remediationPlansByDaysAndSeverity
    });

    function remediationPlansByDaysAndSeverity(jsonString) {
        var myChart4 = new FusionCharts({
             "type": "stackedbar2d",
             "renderAt": "remplanssev",
             "width": "100%",
             "height": "200",
             "dataFormat": "xml",
            "dataSource": htmlDecode(jsonString[0])

        }).render();
        $("#loading").css({"display": "none"});
    }
});