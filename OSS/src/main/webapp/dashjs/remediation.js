var jsScope;
$(window).load(function () {
    $("#loading").fadeOut("slow");
});
angular.module("UitkApp")
        .factory('breadcrumbNavigationViewModelRem', function() {
            return {
                id: 'breadcrumbId9',
                links: [
                    {template: '<span><span class="cux-icon-home"></span>Home</span>', url: '#home'},
                    {template: '<span>Hartford Network</span>', url: '#item2'},
                    {template: '<span>Security Risk Triage</span>', url: '#item3'},
                    {template: '<span>Remediation</span>', url: '#item3'},
                ]

            }
        })
        .factory('multiSortViewModelRem',
                function() {

                    return  {
                        records: [],
                        totalRecordsCount: 0,
                        pagination: {
                            currentPageNumber: 1,
                            paginationWindow: 5,
                            recordsPerPage: 10
                        },
                        columns: [
                            {
                                columnId: 'riskLevel',
                                label: 'Severity',
                                showAlways: true,
                                sortOrder: 1,
                                layoutOrder: 1,
                                sortable: true,
                                enableSearch: true,
                                cellHeaderTemplate: [
                                    '<label for="riskLevelFilter" class="oui-a11y-hidden">Filter by Severity</label> ',
                                    '<select ng-model="column.searchInput" id="riskLevelFilter">',
                                    '	<option value="">All</option>',
                                    '	<option value="Critical">Critical</option>',
                                    '	<option value="High">High</option>',
                                    '	<option value="Medium">Medium</option>',
                                    '	<option value="Low">Low</option>',
                                    '</select> '
                                ].join(''),
                                cellTemplate: '<span ng-bind="::record.riskLevel" class="tk-dtbl-as-table-cell"> </span>',
                            },
                            {
                                columnId: 'status',
                                label: 'Status',
                                showAlways: true,
                                sortOrder: 0,
                                layoutOrder: 2,
                                sortable: true,
                                enableSearch: true,
                                cellHeaderTemplate: [
                                    '<label for="statusFilter" class="oui-a11y-hidden">Filter by Status</label> ',
                                    '<select ng-model="column.searchInput" id="statusFilter">',
                                    '	<option value="">All</option>',
                                    '	<option value="Open">Open</option>',
                                    '	<option value="Closed">Closed</option>',
                                    '</select> '
                                ].join(''),
                                cellTemplate: '<span ng-bind="::record.status" class="tk-dtbl-as-table-cell"> </span>'
                            },
                            {
                                columnId: 'vulnerabilities',
                                label: 'Vulnerabilities',
                                showAlways: true,
                                sortOrder: 0,
                                sortable: true,
                                layoutOrder: 3,
                                enableSearch: true,
                                cellHeaderTemplate: [
                                    '<label for="vulnerabilitiesFilter" class="oui-a11y-hidden">Filter by Vulnerabilities</label> ',
                                    '<input type="text" ng-model="column.searchInput" id="vulnerabilitiesFilter"/> '
                                ].join(''),
                                cellTemplate: '<span ng-bind="::record.vulnerabilities" class="tk-dtbl-as-table-cell"> </span>'
                            }

                        ],
		links: [
                            '<a href="" class="ExportToCSVId" title="Export (CSV)" ng-click="model.popModalExport(record,$event,this)"  ><span  class="cux-icon-export ExportToCSVId" ></span> Export (CSV) </a>'
//                            +'<span ng-show="model.recordOperationInProgress" aria-disabled="true" ><span class="cux-icon-edit"></span> Export (CSV)</span>'
                          //  +'<a href="" ng-click="model.openShowHideColumnsDrawer()" ng-hide="model.recordOperationInProgress" ><a href="" title="Show/Hide Regulations" ng-click="model.openShowHideColumnsDrawer()" ng-hide="model.recordOperationInProgress" ><span class="cux-icon-edit"></span> Show/Hide Regulations<span class="oui-a11y-hidden" > Enter the required fields and submit to create a new record </span> </a> <span ng-show="model.recordOperationInProgress" aria-disabled="true" ><span class="cux-icon-edit"></span> Show/Hide Regulations</span> '
                        ],
                        recordOperationInProgress: true,
                        modalShownExport: false,
	        popModalExport: function (record, event, scope) {
                this.modalShownExport = true;//show modal
	            event.stopPropagation();//don't open or close expandable
	            scope.$emit('viewOfModalExport', record);//send the record to who ever is listening
	        }
                    };
                })
        .factory('globalSuccessMessageViewModel', function() {
            return {
                id: 'global-success-message',
                messageType: 'success',
                content: '<span>This is success message</span>',
                visible: false,
                messageRole: 'alertdialog',
                ariaAttributes: true
            }
        })
        .factory('globalErrorMessageViewModel', function() {
            return {
                id: 'global-error-message',
                messageType: 'error',
                content: '<span>This is error message</span>',
                visible: false,
                messageRole: 'alert'
            }
        })
        .controller('UitkCtrl', function($scope, $http, $translate, breadcrumbNavigationViewModelRem,
                multiSortViewModelRem, globalSuccessMessageViewModel, globalErrorMessageViewModel) {

            $scope.apiItems = [];
             $scope.items = [];
            $scope.items1 = [];
            $scope.items2 = [];
            $scope.items3 = [];
            var engkey = $("#engkey").val();
            $scope.selected1 = [];
            $scope.callbackFunctionInController = function(argument) {
                console.log("callbackFunctionInController:" + argument);
            };


            $scope.breadcrumbViewModel9 = breadcrumbNavigationViewModelRem;


//            multiSortViewModelRem.onExport = function(filterCondition, initiateExport) {
//                $scope.phiVM.isDisplayed = true;
//                $scope.filterCondition = filterCondition;
//                $scope.initiateExport = initiateExport;
//            };

            $.ajax({
                method: 'POST',
                url: 'getSummaryServices.htm?engkey=' + engkey,
                async: false,
                contentType: 'application/json'
                , success: function(data) {
                    $scope.selectedService = data.serviceListSummary;
                }
            });
            $scope.selectedTotal = [];
            $scope.multiSortModel3 = multiSortViewModelRem;
            $http.post("fetchremediationvulnerabilites.htm", $scope.selectedTotal)
                    .then(function(response) {
                        multiSortViewModelRem.records = response.data;
                        multiSortViewModelRem.originalRecords = response.data;
                        multiSortViewModelRem.totalRecordsCount = response.data.length;
                           if(response.data.length === 0){
                             $(".ExportToCSVId").addClass("active");
                        }else{
                             $(".ExportToCSVId").removeClass("active");
                        }
                    });
                    
//            multiSortViewModelRem.onExport = function(filterCondition) {
//                window.open('csvExport.htm?engkey=' + engkey + '&param=' + JSON.stringify($scope.selected1));
//            };
            
            $scope.filterDataTableByService = function() {
                $scope.selectedTotal = $scope.selectedTotal.concat($scope.selected1);

                filterChartsByService($scope.selectedTotal);
                $http.post("fetchremediationvulnerabilites.htm", $scope.selectedTotal)
                        .then(function(response) {
                            multiSortViewModelRem.records = response.data;
                            multiSortViewModelRem.originalRecords = response.data;
                            multiSortViewModelRem.totalRecordsCount = response.data.length;
                               if(response.data.length === 0){
                             $(".ExportToCSVId").addClass("active");
                        }else{
                             $(".ExportToCSVId").removeClass("active");
                        }
                        });
            };

            $scope.refreshServiceFilter = function() {
                $.ajax({
                    method: 'POST',
                    url: 'getSummaryServices.htm?engkey=' + engkey,
                    async: false,
                    contentType: 'application/json'
                    , success: function(data) {
                        $scope.selectedService = data.serviceListSummary;
                    }
                });

                $scope.selectedTotal = [];
                filterChartsByService($scope.selectedTotal);
                $http.post("fetchremediationvulnerabilites.htm", $scope.selectedTotal)
                        .then(function(response) {
                            multiSortViewModelRem.records = response.data;
                            multiSortViewModelRem.originalRecords = response.data;
                            multiSortViewModelRem.totalRecordsCount = response.data.length;
                               if(response.data.length === 0){
                             $(".ExportToCSVId").addClass("active");
                        }else{
                             $(".ExportToCSVId").removeClass("active");
                        }
                        });
            };
            
            $scope.checkedValues =[];
           $scope.$on('viewOfModalExport', function (event, dataSet) {
                $.ajax({
                    method: 'POST',
                    url: 'getExportCSVCheckBoxes.htm',
                    async: false,
                    contentType: 'application/json'

                    , success: function (data) {
                        $scope.items = data.exportCSVcheckboxListOne;
                        $scope.items1 = data.exportCSVcheckboxListTwo;
                        $scope.items2 = data.exportCSVcheckboxListThree;
                        $scope.items3 = data.exportCSVcheckboxListFour;

                    }

                });
                $scope.showAllCheckBoxValues();

            });
            
            $scope.showAllCheckBoxValues = function () {
                 var allSelectedCities = $scope.items.filter(function (item) {
                    if (item.checked) {
                        return item;
                    }
                });
                   var allSelectedCities1 = $scope.items1.filter(function (item) {
                    if (item.checked) {
                        return item;
                    }
                });
                   var allSelectedCities2 = $scope.items2.filter(function (item) {
                    if (item.checked) {
                        return item;
                    }
                });
                   var allSelectedCities3 = $scope.items3.filter(function (item) {
                    if (item.checked) {
                        return item;
                    }
                });
                $scope.checkedValues = allSelectedCities.concat(allSelectedCities1).concat(allSelectedCities2).concat(allSelectedCities3);
            };

            $scope.showAllCheckBoxValues();

            $scope.ExportCSV = function () {
                   $scope.multiSortModel3.modalShownExport = false;
                $("#downloading").css({"display": "block"});
                  $scope.TotalSelected=[];
               $scope.TotalSelected = $scope.TotalSelected.concat($scope.selected1);
                $scope.TotalSelected = $scope.TotalSelected.concat($scope.checkedValues);
//                  alert(JSON.stringify($scope.TotalSelected));
                $http.post("exportCSVForCheckedColumns.htm?engkey=" + engkey, $scope.TotalSelected)
                        .then(function (response) {
                            window.open("downloadCSVFile.htm?rName=C0IXWljMa_gZQc7Qz__d1w");
                           $("#downloading").css({"display": "none"});
                        });

            };
               $scope.Cancel = function () {//close Modal
		    $scope.multiSortModel3.modalShownExport = false;
		};
                 $scope.Save = function () {//close Modal
                     $scope.multiSortModel3.modalShownExport = false;
		};

            $scope.globalSuccessMessageModel = globalSuccessMessageViewModel;
            $scope.globalErrorMessageModel = globalErrorMessageViewModel;
        })

        .config(['uitkConsumerConfigsProvider', '$translateProvider', function(uitkConsumerConfigsProvider, $translateProvider) {
                uitkConsumerConfigsProvider.setErrorLevel("debug");
                $translateProvider
                        .translations('en_US', {
                            "Dynamic Table Demo - Multi-Select": "Dynamic Table Demo - Multi-Select",
                            "First Name": "First Name",
                            "Last Name": "Last Name",
                            "Gender": "Gender",
                            "Email": "Email",
                            "No records found.": "No records found.",
                            "Show": "Show",
                            "per page": "per page",
                            "First": "First",
                            "Previous": "Previous",
                            "Next": "Next",
                            "Last": "Last",
                            "Total Records:": "Total Records:",
                            "Clear All Filters": "Clear All Filters",
                            "Export (CSV)": "Export (CSV)",
                            "Duplicate column id names found": "Duplicate column id names found",
                            "Only single column sort is supported": "Only single column sort is supported",
                            " is not supported": " is not supported",
                            "Select columns to make them show/hide.": "Select columns to make them show/hide.",
                            "Restore Default Sort Order": "Restore Default Sort Order",
                            "Sort by": "Sort by",
                            "Then by": "Then by",
                            "Remove": "Remove",
                            "Choose column(s) to sort": "Choose column(s) to sort",
                            "Add another sort column": "Add another sort column"
                        })
                        .translations('es_ES', {
                            "Dynamic Table Demo - Multi-Select": "Tabla dinámica Demo - Multi -Select",
                            "First Name": "Nombre",
                            "Last Name": "Apellido",
                            "Gender": "Género",
                            "Email": "Correo",
                            "No records found.": "No se encontrarón archivos.",
                            "Show": "Show",
                            "per page": "Por página",
                            "First": "Primero",
                            "Previous": "Anterior",
                            "Next": "Siguiente",
                            "Last": "último",
                            "Total Records:": "Registros totales:",
                            "Clear All Filters": "Borrar todos los filtros",
                            "Export (CSV)": "Exportación(CSV​​)",
                            "Duplicate column id names found": "nombres de ID de columna duplicados encontrados",
                            "Only single column sort is supported": "Sólo se admite una sola columna de clasificación",
                            " is not supported": " no es apoyado",
                            "Restore Default Sort Order": "Restaurar orden de clasificación predeterminado",
                            "Sort by": "Ordenar por",
                            "Then by": "Entonces por",
                            "Remove": "retirar",
                            "Choose column(s) to sort": "Elija la columna (s) para ordenar",
                            "Add another sort column": "Añadir otra columna de clasificación"
                        });

                $translateProvider.preferredLanguage('en_US');
                $translateProvider.useSanitizeValueStrategy('escaped');

            }]);

FusionCharts.ready(function() {
    var selectedTotal = [];
    $.ajax("fetchremediationcharts.htm", {
        method: 'POST',
        data: JSON.stringify(selectedTotal),
        contentType: 'application/json',
        accepts: {
            text: "application/json"
        },
        success: serviceChart
    });

});

function filterChartsByService(obj) {
    $('#remediationChartId').empty();
    $.ajax("fetchremediationcharts.htm", {
        method: "POST",
        data: JSON.stringify(obj),
        contentType: 'application/json',
        accepts: {
            text: "application/json"
        },
        success: serviceChart
    });
}

function serviceChart(jsonString) {
    jQuery.each(jsonString, function(index, item) {
        $("#remediationChartId").append(' <div class="tk-col-1-5"><div class="tk-background-none " ><div id=' + item.serviceName + ' align="left">Chart will load here</div></div></div>');
        var myChart1 = new FusionCharts({
            "type": "mscombi2d",
            "renderAt": item.serviceName,
            "width": "100%",
            "height": "340",
            "dataFormat": "xml",
            "dataSource": item.remediationChartText

        }).render();
    });
}