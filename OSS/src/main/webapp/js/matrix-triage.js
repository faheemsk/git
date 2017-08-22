$(window).load(function () {
    $("#loading").fadeOut("slow");
});
angular.module("UitkApp")

//Dynamic Breadcrumb navigation for reports
        .factory('breadcrumbNavigationViewModelThreat', function() {
            return {
                id: 'breadcrumbId8',
                links: [
                    {template: '<span><span class="cux-icon-home"></span>Home</span>', url: '#home'},
                    {template: '<span>Hartford Network</span>', url: '#item2'},
                    {template: '<span>Security Risk Triage</span>', url: '#item3'},
                    {template: '<span>Prioritization Matrix</span>', url: '#item3'},
                ]

            }
        })
        .factory(
                'multiSortViewModelMatrix',
                function(globalSuccessMessageViewModel, globalErrorMessageViewModel) {

                    return  {
                        pagination: {
                            currentPageNumber: 1,
                            paginationWindow: 5,
                            recordsPerPage: 10
                        },
//                        onChange: function(filterCondition) {
//                            var that = this;
//                            patientService2.query(filterCondition, function(result) {
//                                that.totalRecordsCount = result.totalRecordsCount;
//                                that.records = result.records;
//                            });
//                        },
                        columns: [
                            {
                                columnId: 'id',
                                label: 'ID',
                                showAlways: true,
                                sortOrder: 1,
                                sortable: true,
                                layoutOrder: 1,
                                enableSearch: true,
                                cellHeaderTemplate: [
                                    '<label for="idFilter" class="oui-a11y-hidden">Filter by ID</label> ',
                                    '<input type="text" ng-model="column.searchInput" id="idFilter"/> '
                                ].join(''),
                                cellTemplate: '<span ng-bind="::record.id"> </span>',
                                dataType: 'text',
                                style: 'width:5%'
                            },
                            {
                                columnId: 'finding',
                                label: 'Finding',
                                showAlways: true,
                                sortOrder: 0,
                                sortable: true,
                                layoutOrder: 2,
                                enableSearch: true,
                                cellHeaderTemplate: [
                                    '<label for="findingFilter" class="oui-a11y-hidden">Filter by Finding</label> ',
                                    '<input type="text" ng-model="column.searchInput" id="findingFilter"/> '
                                ].join(''),
                                cellTemplate: '<span ng-bind="::record.finding"> </span>',
                                dataType: 'text'
                            },
                            {
                                columnId: 'risklevel',
                                label: 'Severity',
                                sortOrder: 0,
                                showAlways: true,
                                sortable: true,
                                layoutOrder: 3,
                                enableSearch: true,
                                searchByItemType: 'exact',
                                cellHeaderTemplate: [
                                    '<label for="risklevelFilter" class="oui-a11y-hidden">Filter by Severity</label> ',
                                    '<select ng-model="column.searchInput" id="risklevelFilter">',
                                    '	<option value="">All</option>',
                                    '	<option value="Critical">Critical</option>',
                                    '	<option value="High">High</option>',
                                    '	<option value="Medium">Medium</option>',
                                    '	<option value="Low">Low</option>',
                                    '</select> '
                                ].join(''),
                                cellTemplate: '<span ng-bind="::record.risklevel"> </span>',
                                dataType: 'text',
                                style: 'width:8%'
                            },
                            {
                                columnId: 'recommendation',
                                label: 'Recommendation',
                                showAlways: true,
                                sortOrder: 0,
                                sortable: true,
                                layoutOrder: 5,
                                enableSearch: true,
                                cellHeaderTemplate: [
                                    '<label for="recommendationFilter" class="oui-a11y-hidden">Filter by Recommendation</label> ',
                                    '<input type="text" ng-model="column.searchInput" id="recommendationFilter"/> '
                                ].join(''),
                                cellTemplate: '<span ng-bind="::record.recommendation"> </span>',
                                dataType: 'text',
                                style: 'width:25%'
                            },
                            {
                                columnId: 'description',
                                label: 'Description',
                                showAlways: true,
                                sortOrder: 0,
                                sortable: true,
                                layoutOrder: 4,
                                enableSearch: true,
                                cellHeaderTemplate: [
                                    '<label for="description" class="oui-a11y-hidden">Filter by Status</label> ',
                                    '<input type="text" ng-model="column.searchInput" id="description"/> '
                                ].join(''),
                                cellTemplate: '<span ng-bind="::record.description"> </span>',
                                dataType: 'text',
                                style: 'width:25%'
                            },
                        ],
                         links: ['<a href="" class="ExportToCSVId" title="Export (CSV)" ng-click="model.popModalExport(record,$event,this)"  ><span  class="cux-icon-export ExportToCSVId" ></span> Export (CSV) </a>',
                          ],
                        //multiSortColumns : {sortBy: ["firstName","lastName"], sortOrder: [1,-1]},
                        //isMultiSortApplied : true,
                        recordOperationInProgress: true,
                        showErrorMessage: function() {
                            globalErrorMessageViewModel.content = "<span> Multi sort drawer can not be opened when add, edit or hide column functionality is in progress. </span>";
                            globalErrorMessageViewModel.visible = true;
                        },
                        showSuccessMessage: function(message) {
                            globalSuccessMessageViewModel.content = '<span>' + message + '</span>';
                            globalSuccessMessageViewModel.visible = true;
                        },
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

        .controller('UitkCtrl', function($scope,$http, $translate,
                breadcrumbNavigationViewModelThreat, $timeout,
                multiSortViewModelMatrix, globalSuccessMessageViewModel, globalErrorMessageViewModel) {

            var csvFilterFindings = "";
            var findingCountValue = "";
            $scope.apiItems = [];
            $scope.items = [];
            $scope.items1 = [];
            $scope.items2 = [];
            $scope.items3 = [];
            $scope.callbackFunctionInController = function(argument) {
                console.log("callbackFunctionInController:" + argument);
            };

            $scope.data2 = [];

            $.ajax({
                method: 'GET',
                url: 'fetchPrioritizationFindings.htm',
                async: false,
                contentType: 'application/json'

                , success: function(data) {
                    $scope.data2 = data;
                         if(data.length === 0){
                             $(".ExportToCSVId").addClass("active");
                         }else{
                              $(".ExportToCSVId").removeClass("active");
                         }
                    /*Declare the scope defination as String value,converting the JSON object to String value{using bellow Syntax} */
                    // $scope.data2 = JSON.stringify(data);
                }
            });

            $scope.multiSortModel4 = multiSortViewModelMatrix;
            multiSortViewModelMatrix.records = $scope.data2;

            $scope.checkedValues = [];
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
//$scope.checkedValues = allSelectedCities;
            };

            $scope.showAllCheckBoxValues();
            $scope.ExportCSV = function () {
//                 alert("findingId");
                $scope.multiSortModel4.modalShownExport = false;
                $("#downloading").css({"display": "block"});
                $scope.TotalSelected = [];
//                $scope.TotalSelected = $scope.TotalSelected.concat($scope.selectedTotal);
                $scope.TotalSelected = $scope.TotalSelected.concat($scope.checkedValues);
//                                alert(JSON.stringify($scope.TotalSelected));
                $http.post("prioritizationFindingscsvExport.htm?quadrantvalues="+ csvFilterFindings, $scope.TotalSelected)
                        .then(function (response) {
                            window.open("downloadCSVFile.htm?rName=HEMtRg617j6LhzjRg7inGOIyyhP9SxmQeH0XN7GAMYw");
                            $("#downloading").css({"display": "none"});

                        });

            };
              $scope.Cancel = function () {//close Modal
		    $scope.multiSortModel4.modalShownExport = false;
//		    $scope.activeItem.firstName = $scope.savedTempItem.firstName;
		};
                 $scope.Save = function () {//close Modal
                     $scope.multiSortModel4.modalShownExport = false;
		};

            /*Start Export(CSV) File */
//            multiSortViewModelMatrix.onExport = function(filterCondition) {
//                if (findingCountValue === '0' || findingCountValue === "")
//                {
//                    alert("No data exists for the specified criteria");
//                } else {
//                    window.open('prioritizationFindingscsvExport.htm?quadrantvalues=' + csvFilterFindings);
//                }
//            };
            /*End Export(CSV) File */


            $scope.breadcrumbViewModel4 = breadcrumbNavigationViewModelThreat;

            $scope.globalSuccessMessageModel = globalSuccessMessageViewModel;
            $scope.globalErrorMessageModel = globalErrorMessageViewModel;

            /*Start 'View All' findings binding to the each quadrature */
            $scope.obj = {modalShown1: false};
            $scope.quadrature1 = function() {
                $scope.obj.modalShown1 = !$scope.obj.modalShown1;
            };

            $scope.obj = {modalShown2: false};
            $scope.quadrature2 = function() {
                $scope.obj.modalShown2 = !$scope.obj.modalShown2;
            };

            $scope.obj = {modalShown3: false};
            $scope.quadrature3 = function() {
                $scope.obj.modalShown3 = !$scope.obj.modalShown3;
            };

            $scope.obj = {modalShown4: false};
            $scope.quadrature4 = function() {
                $scope.obj.modalShown4 = !$scope.obj.modalShown4;
            };
            /*End 'View All' findings binding to the each quadrature */


            /*Start  click each quadrature findings then change the Finding data table */
            $scope.changeFirstquadratureFinding = function(findingIds, findingCount) {
                csvFilterFindings = findingIds;
                findingCountValue = findingCount;
                $scope.data2 = [];
                $.ajax({
                    method: 'POST',
                    url: 'fetchPrioritizationFilterFindings.htm?findingIds=' + findingIds,
                    async: false,
                    contentType: 'application/json'

                    , success: function(data) {
                        $scope.data2 = data;
                         if(data.length === 0){
                             $(".ExportToCSVId").addClass("active");
                         }else{
                              $(".ExportToCSVId").removeClass("active");
                         }

                    }
                });
                $scope.multiSortModel4 = multiSortViewModelMatrix;
                multiSortViewModelMatrix.records = $scope.data2;
                multiSortViewModelMatrix.originalRecords = $scope.data2;
                multiSortViewModelMatrix.totalRecordsCount = $scope.data2.length;
            };
            /*End click each quadrature findings then change the Finding data table */


            /*Start 'Show All Findings ' function */
            $scope.toggleModal = function() {
                findingCountValue ="";
                $scope.data2 = [];
                $.ajax({
                    method: 'GET',
                    url: 'fetchPrioritizationFindings.htm',
                    async: false,
                    contentType: 'application/json'
                    , success: function(data) {
                        $scope.data2 = data;
                         if(data.length === 0){
                             $(".ExportToCSVId").addClass("active");
                         }else{
                              $(".ExportToCSVId").removeClass("active");
                         }
                    }
                });

                $scope.multiSortModel4 = multiSortViewModelMatrix;
                multiSortViewModelMatrix.records = $scope.data2;
                multiSortViewModelMatrix.originalRecords = $scope.data2;
                multiSortViewModelMatrix.totalRecordsCount = $scope.data2.length;
                /*End 'Show All Findings ' function */
            };
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
                            "Export (CSV)": "Exportación(CSV)",
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


