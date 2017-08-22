var jsScope;
angular.module("UitkApp")

        .factory('multiSortViewModelRR',function() {

                    return  {
                        clearAllFilters: true,
                        records: [],
                        totalRecordsCount: 0,
                        pagination: {
                            currentPageNumber: 1,
                            paginationWindow: 5,
                            recordsPerPage: 10
                        },
                        columns: [
                            {
                                columnId: 'riskRegisterId',
                                label: 'Risk Register ID',
                                 sortOrder: 1,
                                layoutOrder: 1,
                                sortable: true,
                                enableSearch: true,
                                cellHeaderTemplate: [
                                    '<label for="riskregisteridFilter" class="oui-a11y-hidden">Filter by Risk Register ID</label> ',
                                    '<input type="text" ng-model="column.searchInput" id="riskregisteridFilter"/> '
                                ].join(''),
                                cellTemplate: '<span ng-bind="::record.riskRegisterId" class="tk-dtbl-as-table-cell"> </span>',
                                  dataType: 'text'
                            },
                            {
                                columnId: 'planTitle',
                                label: 'Plan Title',
                                layoutOrder: 2,
                                sortable: true,
                                enableSearch: true,
                                cellHeaderTemplate: [
                                    '<label for="plantitleFilter" class="oui-a11y-hidden">Filter by Plan Title</label> ',
                                    '<input type="text" ng-model="column.searchInput" id="plantitleFilter"/> '
                                ].join(''),
                                cellTemplate: '<span ng-bind="::record.planTitle" class="tk-dtbl-as-table-cell"> </span>'
                            },
                            {
                                columnId: 'planStatus',
                                label: 'Plan Status',
                                sortable: true,
                                layoutOrder: 3,
                                enableSearch: true,
                                cellHeaderTemplate: [
                                    '<label for="planstatusFilter" class="oui-a11y-hidden">Filter by Plan Status</label> ',
                                    '<select ng-model="column.searchInput" id="planstatusFilter">',
                                    '	<option value="">All</option>',
                                    '	<option value="InProgress">InProgress</option>',
                                    '	<option value="Open">Open</option>',
                                    '	<option value="Closed">Closed</option>',
                                    '</select> '
                                ].join(''),
                                cellTemplate: '<span ng-bind="::record.planStatus" class="tk-dtbl-as-table-cell"> </span>'
                            },
                            /*{ 
                             columnId:'createdate', 
                             label:'Create Date',
                             sortable:true,
                             layoutOrder:4, 
                             enableSearch : true, 
                             cellHeaderTemplate: [
                             <label for="createdateFilter" class="oui-a11y-hidden">Filter by Create Date</label> ',
                             <input type="text" ng-model="column.searchInput" id="createdateFilter"/> '
                             ].join(''),
                             cellTemplate:'<span ng-bind="::record.createdate" class="tk-dtbl-as-table-cell"> </span>' 
                             },
                             { 
                             columnId:'notifydate', 
                             label:'Notify Date',
                             sortable:true,
                             layoutOrder:5, 
                             enableSearch : true, 
                             cellHeaderTemplate: [
                             <label for="notifydateFilter" class="oui-a11y-hidden">Filter by Notify Date</label> ',
                             <input type="text" ng-model="column.searchInput" id="notifydateFilter"/> '
                             ].join(''),
                             cellTemplate:'<span ng-bind="::record.notifydate" class="tk-dtbl-as-table-cell"> </span>' 
                             },*/
                            {
                                columnId: 'daysOpen',
                                label: 'Days Open',
                                sortable: true,
                                layoutOrder: 4,
                                enableSearch: true,
                                cellHeaderTemplate: [
                                    '<label for="daysopenFilter" class="oui-a11y-hidden">Filter by Days Open</label> ',
                                    '<input type="text" ng-model="column.searchInput" id="daysopenFilter"/> '
                                ].join(''),
                                cellTemplate: '<span ng-bind="::record.daysOpen" class="tk-dtbl-as-table-cell"> </span>'
                            },
                            {
                                columnId: 'instances',
                                label: 'Instances',
                                sortable: true,
                                layoutOrder: 5,
                                enableSearch: true,
                                cellHeaderTemplate: [
                                    '<label for="instancesFilter" class="oui-a11y-hidden">Filter by Instances</label> ',
                                    '<input type="text" ng-model="column.searchInput" id="instancesFilter"/> '
                                ].join(''),
                                cellTemplate: '<span ng-bind="::record.instances" class="tk-dtbl-as-table-cell"> </span>'
                            },
                            {
                                columnId: 'completion',
                                label: 'Completion',
                                sortable: true,
                                layoutOrder: 6,
                                enableSearch: true,
                                cellHeaderTemplate: [
                                    '<label for="completionFilter" class="oui-a11y-hidden">Filter by Completion</label> ',
                                    '<input type="text" ng-model="column.searchInput" id="completionFilter"/> '
                                ].join(''),
                                cellTemplate: '<span ng-bind="::record.completion" class="tk-dtbl-as-table-cell"> </span>'
                            }
                        ],
                        links: ['<a href="" ng-click="model.popModalExport(record,$event,this)"  ><span class="cux-icon-export"></span> Export (CSV) </a>  '


                        ],
                        
                        modalShownExport: false,
                        popModalExport: function(record, event, scope) {
                            jsScope.ExportCSV();
                        }
//		records : riskregistry,
//		totalRecordsCount : riskregistry.length
                    };


                })



        .controller('UitkCtrl', function($scope, $http, $translate, $timeout, multiSortViewModelRR) {
            jsScope = $scope;
            var severityCode = "";
            
            $scope.multiSortModel5 = multiSortViewModelRR;

            $scope.getRiskRegistryData = function() {
                $http.post("getRiskRegistryData.htm")
                        .then(function(response) {
                            multiSortViewModelRR.records = response.data;
                            multiSortViewModelRR.originalRecords = response.data;
                            multiSortViewModelRR.totalRecordsCount = response.data.length;
//                            if (response.data.length === 0) {
//                                $(".ExportToCSVId").addClass("active");
//                                $("#Labels1").css({"display": "none"});
//                            } else {
//                                $(".ExportToCSVId").removeClass("active");
//                            }
                            $("#loading").css({"display": "none"});

                        });

            };
            jsScope.getRiskRegistryData();

            //START Export CSV FILE
            $scope.ExportCSV = function() {
//                $scope.multiSortModel4.modalShownExport = false;
                $("#downloading").css({"display": "block"});
                $http.post("registryMapDataForCSVDownload.htm")
                        .then(function(response) {
                            window.open("downloadCSVFile.htm?rName=9d5B4HSRkdvq115Oz1ei_g");
                            $("#downloading").css({"display": "none"});

                        });

            };



            $scope.getRegistryDataBySeverity = function(obj) {
                severityCode = obj;
                $http.post("getRiskRegistryData.htm?sevCode=" + severityCode)
                        .then(function(response) {
                            multiSortViewModelRR.records = response.data;
                            multiSortViewModelRR.originalRecords = response.data;
                            multiSortViewModelRR.totalRecordsCount = response.data.length;
                            $("#loading").css({"display": "none"});
                        });

            };

            $("#loading").css({"display": "none"});

        })


function htmlDecode(input) {
    var e = document.createElement('div');
    e.innerHTML = input;
    return e.childNodes.length === 0 ? "" : e.childNodes[0].nodeValue;
}


FusionCharts.ready(function() {
    $.ajax("registryDataByResponse.htm", {
//    dataType: "text",
        cache: false,
        contentType: 'application/json',
        accepts: {
            text: "application/json"
        },
        success: loadResponseChart
    });

    function loadResponseChart(jsonString) {
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

FusionCharts.ready(function() {
    $.ajax("registryDataByOwner.htm", {
//    dataType: "text",
        cache: false,
        contentType: 'application/json',
        accepts: {
            text: "application/json"
        },
        success: loadRiskOwnerChart
    });

    function loadRiskOwnerChart(jsonString) {
        var myChart4 = new FusionCharts({
            "type": "stackedbar2d",
            "renderAt": "riskregowner",
            "width": "100%",
            "height": "220",
            "dataFormat": "xml",
            "dataSource": htmlDecode(jsonString[0])

        }).render();
        $("#loading").css({"display": "none"});
    }
});
FusionCharts.ready(function() {
    $.ajax("registryDataBySeverity.htm", {
//    dataType: "text",
        cache: false,
        contentType: 'application/json',
        accepts: {
            text: "application/json"
        },
        success: loadRiskSeverityChart
    });

    function loadRiskSeverityChart(jsonString) {
        var myChart4 = new FusionCharts({
            "type": "stackedbar2d",
            "renderAt": "riskregsev",
            "width": "100%",
            "height": "200",
            "dataFormat": "xml",
            "dataSource": htmlDecode(jsonString[0])

        }).render();
        $("#loading").css({"display": "none"});
    }
});
function clearTableFields() {
    $("#risklevelFilter").val('');
    $("#costeffortFilter").val('');
     $('input[type="text"][id*="Filter"]').each(function () {
         $(this).val('');
     });
}