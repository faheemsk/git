//$(window).load(function() {
//    $("#loading").fadeOut("slow");
//});
angular.module("UitkApp")


        .factory('breadcrumbNavigationViewModelNetwork', function() {
            return {
                id: 'breadcrumbId1',
                links: [
//                    {template: '<span><span class="cux-icon-home"></span>Home</span>', url: '#home'},
                    {template: '<span>Hartford Network</span>', url: '#item2'},
                    {template: '<span>Security Health Check</span>', url: '#item3'},
                    {template: '<span>Assessments</span>', url: '#item3'},
                    {template: '<span>Network Assessment</span>', url: '#item3'},
                ]

            }
        })



        .factory(
                'multiSortViewModel',
                function(globalSuccessMessageViewModel, globalErrorMessageViewModel) {


                    return  {
                        records: [],
                        totalRecordsCount: 0,
                        pagination: {
                            currentPageNumber: 1,
                            paginationWindow: 5,
                            recordsPerPage: 10
                        },
//                    onChange : function(filterCondition) {
//                        var that = this;
//                        patientService.query(filterCondition, function(result) {
//                            that.totalRecordsCount = result.totalRecordsCount;
//                            that.records = result.records;
//                        });
//                    },
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
                                columnId: 'rootCauseDetail',
                                label: 'Vulnerability Category',
                                showAlways: true,
                                sortOrder: 0,
                                sortable: true,
                                layoutOrder: 4,
                                enableSearch: true,
                                cellHeaderTemplate: [
                                    '<label for="rootCauseDetailFilter" class="oui-a11y-hidden">Filter by Vulnerability Category</label> ',
                                    '<input type="text" ng-model="column.searchInput" id="rootCauseDetailFilter"/> ',
                                ].join(''),
                                cellTemplate: '<span ng-bind="::record.rootCauseDetail"> </span>',
                                dataType: 'text',
                                style: 'width:15%'
                            },
                            {
                                columnId: 'description',
                                label: 'Description',
                                showAlways: true,
                                sortOrder: 0,
                                sortable: true,
                                layoutOrder: 5,
                                enableSearch: true,
                                cellHeaderTemplate: [
                                    '<label for="descriptionFilter" class="oui-a11y-hidden">Filter by Description</label> ',
                                    '<input type="text" ng-model="column.searchInput" id="descriptionFilter"/> '
                                ].join(''),
                                cellTemplate: '<span ng-bind="::record.description"> </span>',
                                dataType: 'text',
                                style: 'width:25%'
                            },
                            {
                                columnId: 'recommendation',
                                label: 'Recommendation',
                                showAlways: true,
                                sortOrder: 0,
                                sortable: true,
                                layoutOrder: 6,
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
                                columnId: 'hitrust',
                                label: 'HITRUST',
                                sortOrder: 0,
                                sortable: true,
                                layoutOrder: 7,
                                showColumnInTable: true,
                                enableSearch: true,
                                cellHeaderTemplate: [
                                    '<label for="hitrustFilter" class="oui-a11y-hidden">Filter by HITRUST</label> ',
                                    '<input type="text" ng-model="column.searchInput" id="hitrustFilter"/> '
                                ].join(''),
                                cellTemplate: '<a href="#" ng-click="model.popModal(record,$event,this)"><span ng-bind="::record.hitrust" > </span></a>',
                                // cellTemplate:'<a href="#" onclick="document.getElementById("uitkPopupId1_openModalBtn");" ng-click="toggleModal()"><span ng-bind="::record.hitrust" > </span></a>',
                                dataType: 'text',
                            },
                            {
                                columnId: 'hipaa',
                                label: 'HIPAA',
                                sortOrder: 0,
                                sortable: true,
                                layoutOrder: 8,
                                showColumnInTable: true,
                                enableSearch: true,
                                cellHeaderTemplate: [
                                    '<label for="hipaaFilter" class="oui-a11y-hidden">Filter by HIPAA</label> ',
                                    '<input type="text" ng-model="column.searchInput" id="hipaaFilter"/> '
                                ].join(''),
                                cellTemplate: '<span ng-bind="::record.hipaa"> </span>',
                                dataType: 'text'
                            },
                            {
                                columnId: 'nist',
                                label: 'NIST',
                                sortOrder: 0,
                                sortable: true,
                                layoutOrder: 9,
                                enableSearch: true,
                                cellHeaderTemplate: [
                                    '<label for="nistFilter" class="oui-a11y-hidden">Filter by HIPAA</label> ',
                                    '<input type="text" ng-model="column.searchInput" id="nistFilter"/> '
                                ].join(''),
                                cellTemplate: '<span ng-bind="::record.nist"> </span>',
                                dataType: 'text'
                            },
                            {
                                columnId: 'irs',
                                label: 'IRS 1075',
                                sortOrder: 0,
                                sortable: true,
                                layoutOrder: 10,
                                showColumnInTable: false,
                                enableSearch: true,
                                cellHeaderTemplate: [
                                    '<label for="irsFilter" class="oui-a11y-hidden">Filter by IRS 1075</label> ',
                                    '<input type="text" ng-model="column.searchInput" id="irsFilter"/> '
                                ].join(''),
                                cellTemplate: '<span ng-bind="::record.irs"> </span>',
                                dataType: 'text'
                            },
                            {
                                columnId: 'mars',
                                label: 'MARS-E',
                                sortOrder: 0,
                                sortable: true,
                                layoutOrder: 11,
                                showColumnInTable: false,
                                enableSearch: true,
                                cellHeaderTemplate: [
                                    '<label for="marsFilter" class="oui-a11y-hidden">Filter by MARS-E</label> ',
                                    '<input type="text" ng-model="column.searchInput" id="marsFilter"/> '
                                ].join(''),
                                cellTemplate: '<span ng-bind="::record.mars"> </span>',
                                dataType: 'text'
                            },
                            {
                                columnId: 'sox',
                                label: 'SOX',
                                sortOrder: 0,
                                sortable: true,
                                layoutOrder: 12,
                                showColumnInTable: false,
                                enableSearch: true,
                                cellHeaderTemplate: [
                                    '<label for="soxFilter" class="oui-a11y-hidden">Filter by SOX</label> ',
                                    '<input type="text" ng-model="column.searchInput" id="soxFilter"/> '
                                ].join(''),
                                cellTemplate: '<span ng-bind="::record.sox"> </span>',
                                dataType: 'text'
                            },
                            {
                                columnId: 'glba',
                                label: 'GLBA',
                                sortOrder: 0,
                                sortable: true,
                                layoutOrder: 13,
                                showColumnInTable: false,
                                enableSearch: true,
                                cellHeaderTemplate: [
                                    '<label for="glbaFilter" class="oui-a11y-hidden">Filter by GLBA</label> ',
                                    '<input type="text" ng-model="column.searchInput" id="glbaFilter"/> '
                                ].join(''),
                                cellTemplate: '<span ng-bind="::record.glba"> </span>',
                                dataType: 'text'
                            },
                            {
                                columnId: 'iso',
                                label: 'ISO27001',
                                sortOrder: 0,
                                sortable: true,
                                layoutOrder: 14,
                                showColumnInTable: false,
                                enableSearch: true,
                                cellHeaderTemplate: [
                                    '<label for="isoFilter" class="oui-a11y-hidden">Filter by ISO27001</label> ',
                                    '<input type="text" ng-model="column.searchInput" id="isoFilter"/> '
                                ].join(''),
                                cellTemplate: '<span ng-bind="::record.iso"> </span>',
                                dataType: 'text'
                            },
                            {
                                columnId: 'pci',
                                label: 'PCI',
                                sortOrder: 0,
                                sortable: true,
                                layoutOrder: 15,
                                showColumnInTable: false,
                                enableSearch: true,
                                cellHeaderTemplate: [
                                    '<label for="pciFilter" class="oui-a11y-hidden">Filter by PCI</label> ',
                                    '<input type="text" ng-model="column.searchInput" id="pciFilter"/> '
                                ].join(''),
                                cellTemplate: '<span ng-bind="::record.pci"> </span>',
                                dataType: 'text'
                            },
                            {
                                columnId: 'fedRamp',
                                label: 'FED RAMP',
                                sortOrder: 0,
                                sortable: true,
                                layoutOrder: 16,
                                showColumnInTable: false,
                                enableSearch: true,
                                cellHeaderTemplate: [
                                    '<label for="fedRampFilter" class="oui-a11y-hidden">Filter by FED RAMP</label> ',
                                    '<input type="text" ng-model="column.searchInput" id="fedRampFilter"/> '
                                ].join(''),
                                cellTemplate: '<span ng-bind="::record.fedRamp"> </span>',
                                dataType: 'text'
                            },
                            {
                                columnId: 'csa',
                                label: 'CSA CCM',
                                sortOrder: 0,
                                sortable: true,
                                layoutOrder: 17,
                                showColumnInTable: false,
                                enableSearch: true,
                                cellHeaderTemplate: [
                                    '<label for="csaFilter" class="oui-a11y-hidden">Filter by CSA CCM</label> ',
                                    '<input type="text" ng-model="column.searchInput" id="csaFilter"/> '
                                ].join(''),
                                cellTemplate: '<span ng-bind="::record.csa"> </span>',
                                dataType: 'text'
                            },
                            {
                                columnId: 'fisma',
                                label: 'FISMA Moderate',
                                sortOrder: 0,
                                sortable: true,
                                layoutOrder: 18,
                                showColumnInTable: false,
                                enableSearch: true,
                                cellHeaderTemplate: [
                                    '<label for="fismaFilter" class="oui-a11y-hidden">Filter by FISMA Moderate</label> ',
                                    '<input type="text" ng-model="column.searchInput" id="fismaFilter"/> '
                                ].join(''),
                                cellTemplate: '<span ng-bind="::record.fisma"> </span>',
                                dataType: 'text'
                            },
                        ],
                       links: [
                            '<a href="" class="ExportToCSVId" title="Export (CSV)" ng-click="model.popModalExport(record,$event,this)"  ><span  class="cux-icon-export ExportToCSVId" ></span> Export (CSV) </a>'
                            +'<a href="" ng-click="model.openShowHideColumnsDrawer()" ng-hide="model.recordOperationInProgress" ><a href="" class="ExportToCSVId" title="Show/Hide Regulations" ng-click="model.openShowHideColumnsDrawer()" ng-hide="model.recordOperationInProgress" ><span class="cux-icon-edit ExportToCSVId"></span> Show/Hide Regulations<span class="oui-a11y-hidden" > Enter the required fields and submit to create a new record </span> </a> <span ng-show="model.recordOperationInProgress" aria-disabled="true" ><span class="cux-icon-edit"></span> Show/Hide Regulations</span> '
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
                        modalShown: false,
                        popModal: function(record, event, scope) {
                            this.modalShown = true;//show modal
                            event.stopPropagation();//don't open or close expandable
                            scope.$emit('viewOfModal', record);//send the record to who ever is listening
                        },
                        modalShownExport: false,
                        popModalExport: function(record, event, scope) {
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

        .controller('UitkCtrl', function($scope, $http, $translate, breadcrumbNavigationViewModelNetwork, $timeout, multiSortViewModel, globalSuccessMessageViewModel, globalErrorMessageViewModel) {


            $scope.apiItems = []
            var appData = [];
            var ssidData = [];
            var rootData = [];
            var owaspViewData = [];
            var remediationPrioritiesData = [];
             var remediationPrioritiesValues = [];
             var remediationPrioritiesPercentage = [];
            $scope.hitrustList = [];
            $scope.selectedValue = [];
            $scope.items = [];
            $scope.items1 = [];
            $scope.items2 = [];
            $scope.items3 = [];
            $scope.callbackFunctionInController = function(argument) {
                console.log("callbackFunctionInController:" + argument);
            };


            $scope.breadcrumbViewModel1 = breadcrumbNavigationViewModelNetwork;
             var serviceName = $("#serviceName").val().replace(/\s/g, "");
//                alert( serviceName);

            $scope.$on('viewOfModal', function(event, data) {
                var hData = data.hitrust;
                $.ajax({
                    method: 'POST',
                    url: 'getHitrustInfoByCtrlCD.htm?hData=' + hData,
                    async: false,
                    cache: false,
                    contentType: 'application/json'

                    , success: function(reddata) {
                        $scope.hitrustList = reddata;
                    }
                });


            });

//            multiSortViewModel.onExport = function(filterCondition, initiateExport) {
//                $scope.phiVM.isDisplayed = true;
//                $scope.filterCondition = filterCondition;
//                $scope.initiateExport = initiateExport;
//            };

//            $scope.$on('phiConfirmation-OkClicked', function() {
//                if ($scope.phiVM.isAcknowledged) {
//                    patientService.query($scope.filterCondition, function(result) {
//                        $scope.initiateExport(result, 'FileName');
//                    });
//                }
//            });
//            $scope.data2 = [];
//            $.ajax({
//                method: 'POST',
//                url: 'engSerData.htm',
//                async: false,
//                cache: false,
//                contentType: 'application/json'
//
//                , success: function(data) {
////                    alert(JSON.stringify(data));
//                    $scope.data2 = data;
//
//                }
//            });
//            $scope.multiSortModel = multiSortViewModel;
//            multiSortViewModel.records = $scope.data2;
//            multiSortViewModel.totalRecordsCount = $scope.data2.length;
            $scope.multiSortModel = multiSortViewModel;
                  $http.post("engSerData.htm", "")
                        .then(function(response) {
                            multiSortViewModel.records = response.data;
                            multiSortViewModel.originalRecords = response.data;
                            multiSortViewModel.totalRecordsCount = response.data.length;
                             if(response.data.length === 0){
                             $(".ExportToCSVId").addClass("active");
                        }else{
                             $(".ExportToCSVId").removeClass("active");
                        }
                        });

            $scope.globalSuccessMessageModel = globalSuccessMessageViewModel;
            $scope.globalErrorMessageModel = globalErrorMessageViewModel;


//    multiSortViewModel.onExport = function (filterCondition) {
//
////                alert(JSON.stringify($scope.selected2));
//                if ($scope.selected2 == undefined) {
//                    window.open('csvExportAssessment.htm');
//
//                } else {
//                    window.open('csvExportAssessment.htm?&param=' + JSON.stringify($scope.selectedValue));
////                    $scope.phiVM.isDisplayed = true;
////                    $scope.filterCondition = filterCondition;
////                    $scope.initiateExport = initiateExport;
//                }
//            };
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

            $scope.showAllCheckBoxValues = function() {
                var allSelectedCities = $scope.items.filter(function(item) {
                    if (item.checked) {
                        return item;
                    }
                });
                var allSelectedCities1 = $scope.items1.filter(function(item) {
                    if (item.checked) {
                        return item;
                    }
                });
                var allSelectedCities2 = $scope.items2.filter(function(item) {
                    if (item.checked) {
                        return item;
                    }
                });
                var allSelectedCities3 = $scope.items3.filter(function(item) {
                    if (item.checked) {
                        return item;
                    }
                });
                $scope.checkedValues = allSelectedCities.concat(allSelectedCities1).concat(allSelectedCities2).concat(allSelectedCities3);
            };

            $scope.showAllCheckBoxValues();

            $scope.ExportCSV = function() {
                 $scope.multiSortModel.modalShownExport = false;
                $("#downloading").css({"display": "block"});
                if ($scope.selected2 === undefined) {
                   $http.post("csvExportAssessment.htm", $scope.checkedValues)
                            .then(function(response) {
//                                 alert(serviceName);
                                window.open("downloadCSVFile.htm?rName=_o3dU5kEz512AE3zmiLQ6g");
                         $("#downloading").css({"display": "none"});
                            });

                } else {
//                    window.open('csvExportAssessment.htm?&param=' + JSON.stringify($scope.selectedValue));
//                    alert(JSON.stringify($scope.selected2));
                    $scope.selected2 = $scope.selected2.concat($scope.checkedValues);
// window.open('exportCSVForCheckedColumns.htm?engkey='+ engkey+'&param='+JSON.stringify($scope.selectedTotal)+'&cp='+JSON.stringify($scope.checkedValues));
                    $http.post("csvExportAssessment.htm", $scope.selected2)
                            .then(function(response) {
                                window.open("downloadCSVFile.htm?rName=_o3dU5kEz512AE3zmiLQ6g");
                        $("#downloading").css({"display": "none"});
                            });

                }

            };


            $scope.obj = {modalShown: false};

            $scope.toggleModal = function() {
                $scope.obj.modalShown = !$scope.obj.modalShown;
            };

            $scope.obj = {modalShown2: false};

            $scope.toggleModal2 = function() {
                $scope.obj.modalShown2 = !$scope.obj.modalShown2;
            };
            $scope.obj = {modalShown7: false};
             $scope.toggleModal7 = function() {
                $scope.obj.modalShown7 = !$scope.obj.modalShown7;
            };

            $scope.Save = function() {//close Modal
                $scope.multiSortModel.modalShown = false;
                $scope.multiSortModel.modalShownExport = false;
            }
            $scope.Cancel = function() {//close Modal
                $scope.multiSortModel.modalShown = false;
//                $scope.activeItem.firstName = $scope.savedTempItem.firstName;
                $scope.multiSortModel.modalShownExport = false;
            }




            $scope.obj = {modalShown3: false};

            $scope.toggleModal3 = function() {
                $scope.obj.modalShown3 = !$scope.obj.modalShown3;
            };

            $scope.obj = {modalShown4: false};

            $scope.toggleModal4 = function() {
                $scope.obj.modalShown4 = !$scope.obj.modalShown4;
            };


            $scope.obj = {modalShown5: false};

            $scope.toggleModal5 = function() {
                $scope.obj.modalShown5 = !$scope.obj.modalShown5;
            };

            $scope.toggleModal6 = function() {
                $scope.obj.modalShown6 = !$scope.obj.modalShown6;
            };

            $scope.ssidFilter = function() {
  $("#loading").css({"display": "block"});
                $scope.selectedValue = $scope.selected2;
//                severityFilterChart($scope.selected2);
                applicationFilterChart($scope.selected2);
                ssidFilterChart($scope.selected2);
                rootCauseFilterChart($scope.selected2);
                owaspFilterChart($scope.selected2);
                applicationMostVulnerable($scope.selected2);
              
                $.ajax({
                    method: 'POST',
                    url: 'filterApplicationTopFindings.htm',
                    data: JSON.stringify($scope.selected2),
                    async: false,
                    cache: false,
                    contentType: 'application/json',
                    success: function(data) {
                        $("#vulFindingId").css({"display": "none"});
                        if (data.length > 0) {
                            $scope.topFingings = data;
                        } else {
                            $(".ndiv").empty();
                            $("<p align='center' class='tk-margin-top-5t' style='font-size: 10px;font-family: Verdana, sans'>No data to display.</p>").appendTo(".ndiv");
                        }


                    }
                });
                $.ajax({
                    method: 'POST',
                    url: 'applicationAllFilterData.htm',
                    data: JSON.stringify($scope.selected2),
                    async: false,
                    cache: false,
                    contentType: 'application/json',
                    success: function(data) {
                        if (data.categoryLabelList.length > 4) {
                            $("#uitkPopupId5_openModalBtn").css({"display": "block"});
                        } else {
                            $("#uitkPopupId5_openModalBtn").css({"display": "none"});
                        }
                        appData = data;
                        $scope.myDataSource4 = {
                            chart: {
                                caption: "",
                                subCaption: " ",
                                captionAlignment: "left",
                                captionFontColor: "#333333",
                                maxLabelWidthPercent: "150",
                                bgAlpha: "0",
                                plotSpacePercent: "100",
                                labelDisplay: "wrap",
                                maxLabelHeight: "50",
                                showTooltip: "1",
                                toolTipColor: "#666666",
                                toolTipBorderThickness: "1",
                                toolTipBgColor: "#ffffff",
                                toolTipBgAlpha: "80",
                                toolTipBorderRadius: "3",
                                toolTipPadding: "5",
                                numDivLines: "0",
                                baseFont: "arial",
                                showYAxisValues: "0",
                                labelFontSize: "12",
                                valueFontSize: "12",
                                labelPadding: "13",
                                labelFontColor: "#666666",
                                showPlotBorder: "0",
                                yAxisMaxValue: "110",
                                maxColWidth: "15",
                                usePlotGradientColor: "0",
                                showShadow: "0",
                                use3DLighting: "0",
                                showLabels: "1",
                                showValues: "1",
                                showLegend: "0",
                                numberSuffix: "",
                                bgcolor: "#ffffff",
                                borderColor: "#ffffff",
                                showpercentvalues: "0",
                                animation: "1",
                                bgratio: "0",
                                paletteColors: "#523995",
                                canvasBorderAlpha: "0",
                                canvasBorderColor: "#ffffff",
                                scrollheight: "10",
                                flatScrollBars: "1",
                                scrollShowButtons: "0",
                                scrollColor: "#cccccc",
                                showHoverEffect: "1"
                            },
                            "categories": [
                                {
                                    "category": appData.categoryLabelList
                                }
                            ],
                            "dataset": [
                                {
                                    "data": appData.categoryDataList
                                }
                            ]
                        }

                    }
                });

                $.ajax({
                    method: 'POST',
                    url: 'filterssidDetails.htm',
                    data: JSON.stringify($scope.selected2),
                    async: false,
                    cache: false,
                    contentType: 'application/json'

                    , success: function(data) {
                        if (data.categoryLabelList.length > 4) {
                            $("#uitkPopupId3_openModalBtn").css({"display": "block"});
                        } else {
                            $("#uitkPopupId3_openModalBtn").css({"display": "none"});
                        }
                        ssidData = data;
                        $scope.myDataSource2 = {
                            chart: {
                                caption: " ",
                                subCaption: " ",
                                captionAlignment: "left",
                                captionFontColor: "#333333",
                                maxLabelWidthPercent: "150",
                                bgAlpha: "0",
                                plotSpacePercent: "100",
                                labelDisplay: "wrap",
                                maxLabelHeight: "50",
                                showTooltip: "1",
                                toolTipColor: "#666666",
                                toolTipBorderThickness: "1",
                                toolTipBgColor: "#ffffff",
                                toolTipBgAlpha: "80",
                                toolTipBorderRadius: "3",
                                toolTipPadding: "5",
                                numDivLines: "0",
                                baseFont: "arial",
                                showYAxisValues: "0",
                                labelFontSize: "12",
                                valueFontSize: "12",
                                labelPadding: "13",
                                labelFontColor: "#666666",
                                showPlotBorder: "0",
                                yAxisMaxValue: "110",
                                maxColWidth: "15",
                                usePlotGradientColor: "0",
                                showShadow: "0",
                                use3DLighting: "0",
                                showLabels: "1",
                                showValues: "1",
                                showLegend: "0",
                                numberSuffix: "",
                                bgcolor: "#ffffff",
                                borderColor: "#ffffff",
                                showpercentvalues: "0",
                                animation: "1",
                                bgratio: "0",
                                paletteColors: "#1965ae",
                                canvasBorderAlpha: "0",
                                canvasBorderColor: "#ffffff",
                                scrollheight: "10",
                                flatScrollBars: "1",
                                scrollShowButtons: "0",
                                scrollColor: "#cccccc",
                                showHoverEffect: "1"
                            },
                            "categories": [
                                {
                                    "category": ssidData.categoryLabelList
                                }
                            ],
                            "dataset": [
                                {
                                    "data": ssidData.categoryDataList
                                }
                            ]


                        }
                    }
                });

                $.ajax({
                    method: 'POST',
                    url: 'filteerRootCauseData.htm',
                    data: JSON.stringify($scope.selected2),
                    async: false,
                    cache: false,
                    contentType: 'application/json'

                    , success: function(data) {
                        if (data.categoryLabelList.length > 4) {
                            $("#uitkPopupId2_openModalBtn").css({"display": "block"});
                        } else {
                            $("#uitkPopupId2_openModalBtn").css({"display": "none"});
                        }
                        rootData = data;
                        $scope.myDataSource1 = {
                            chart: {
                                caption: " ",
                                subCaption: " ",
                                captionAlignment: "left",
                                captionFontColor: "#333333",
                                maxLabelWidthPercent: "150",
                                bgAlpha: "0",
                                plotSpacePercent: "100",
                                labelDisplay: "wrap",
                                maxLabelHeight: "50",
                                showTooltip: "1",
                                toolTipColor: "#666666",
                                toolTipBorderThickness: "1",
                                toolTipBgColor: "#ffffff",
                                toolTipBgAlpha: "80",
                                toolTipBorderRadius: "3",
                                toolTipPadding: "5",
                                numDivLines: "0",
                                baseFont: "arial",
                                showYAxisValues: "0",
                                labelFontSize: "12",
                                valueFontSize: "12",
                                labelPadding: "13",
                                labelFontColor: "#666666",
                                showPlotBorder: "0",
                                yAxisMaxValue: "110",
                                maxColWidth: "15",
                                usePlotGradientColor: "0",
                                showShadow: "0",
                                use3DLighting: "0",
                                showLabels: "1",
                                showValues: "1",
                                showLegend: "0",
                                numberSuffix: "",
                                bgcolor: "#ffffff",
                                borderColor: "#ffffff",
                                showpercentvalues: "0",
                                animation: "1",
                                bgratio: "0",
                                paletteColors: "#523995",
                                canvasBorderAlpha: "0",
                                canvasBorderColor: "#ffffff",
                                scrollheight: "10",
                                flatScrollBars: "1",
                                scrollShowButtons: "0",
                                scrollColor: "#cccccc",
                                showHoverEffect: "1"
                            },
                            "categories": [
                                {
                                    "category": rootData.categoryLabelList
                                }
                            ],
                            "dataset": [
                                {
                                    "data": rootData.categoryDataList
                                }
                            ]
                        }
                    }
                });
                
                $.ajax({
                    method: 'POST',
                    url: 'filteerOwaspViewData.htm',
                    data: JSON.stringify($scope.selected2),
                    async: false,
                    cache: false,
                    contentType: 'application/json'

                    , success: function(data) {
                        if (data.categoryLabelList.length > 4) {
                            $("#uitkPopupId7_openModalBtn").css({"display": "block"});
                        } else {
                            $("#uitkPopupId7_openModalBtn").css({"display": "none"});
                        }
                        owaspViewData = data;
                        $scope.myDataSource7 = {
                            chart: {
                                caption: " ",
                                subCaption: " ",
                                captionAlignment: "left",
                                captionFontColor: "#333333",
                                maxLabelWidthPercent: "150",
                                bgAlpha: "0",
                                plotSpacePercent: "100",
                                labelDisplay: "wrap",
                                maxLabelHeight: "50",
                                showTooltip: "1",
                                toolTipColor: "#666666",
                                toolTipBorderThickness: "1",
                                toolTipBgColor: "#ffffff",
                                toolTipBgAlpha: "80",
                                toolTipBorderRadius: "3",
                                toolTipPadding: "5",
                                numDivLines: "0",
                                baseFont: "arial",
                                showYAxisValues: "0",
                                labelFontSize: "12",
                                valueFontSize: "12",
                                labelPadding: "13",
                                labelFontColor: "#666666",
                                showPlotBorder: "0",
                                yAxisMaxValue: "110",
                                maxColWidth: "15",
                                usePlotGradientColor: "0",
                                showShadow: "0",
                                use3DLighting: "0",
                                showLabels: "1",
                                showValues: "1",
                                showLegend: "0",
                                numberSuffix: "",
                                bgcolor: "#ffffff",
                                borderColor: "#ffffff",
                                showpercentvalues: "0",
                                animation: "1",
                                bgratio: "0",
                                paletteColors: "#523995",
                                canvasBorderAlpha: "0",
                                canvasBorderColor: "#ffffff",
                                scrollheight: "10",
                                flatScrollBars: "1",
                                scrollShowButtons: "0",
                                scrollColor: "#cccccc",
                                showHoverEffect: "1"
                            },
                            "categories": [
                                {
                                    "category": owaspViewData.categoryLabelList
                                }
                            ],
                            "dataset": [
                                {
                                    "data": owaspViewData.categoryDataList
                                }
                            ]
                        }
                    }
                });

                $.ajax({
                    method: 'POST',
                    url: 'filterRemPriorities.htm',
                    data: JSON.stringify($scope.selected2),
                    async: false,
                    cache: false,
                    contentType: 'application/json',
                    success: function(data) {
                            remediationPrioritiesData = data.categoryLabelList;
                    remediationPrioritiesValues = data.categoryDataList;
                    remediationPrioritiesPercentage = data.percentageCountList;
                        if (remediationPrioritiesData.length > 5) {
                            $("#uitkPopupId6_openModalBtn").css({"display": "block"});
                        } else {
                            $("#uitkPopupId6_openModalBtn").css({"display": "none"});
                        }
                         $scope.myDataSource5 = {
                "chart": {
                    caption: "",
                    captionAlignment: "left",
                    captionFontColor: "333333",
                    yAxisName: "No. of findings ",
                    bgAlpha: "0",
                    labelDisplay: "wrap",
                    maxLabelHeight: "50",
                    showTooltip: "1",
                    maxColWidth: "15",
                    toolTipColor: "666666",
                    toolTipBorderThickness: "1",
                    toolTipBgColor: "ffffff",
                    toolTipBgAlpha: "80",
                    toolTipBorderRadius: "2",
                    toolTipPadding: "5",
                    numDivLines: "0",
                    pYAxisNameFontSize: "14",
                    chartBottomMargin: "0",
                    chartLeftMargin: "0",
                    chartTopMargin: "0",
                    chartRightMargin: "0",
                    baseFont: "arial",
                    showYAxisValues: "0",
                    labelFontSize: "12",
                    valueFontSize: "12",
                    labelPadding: "13",
                    labelFontColor: "666666",
                    showPlotBorder: "0",
                    yAxisMaxValue: "60",
                    maxBarHeight: "15",
                    plotGradientcolor: "",
                    showShadow: "0",
                    use3DLighting: "0",
                    legendShadow: "0",
                    showLabels: "1",
                    showValues: "1",
                    showLegend: "0",
                    numberSuffix: "",
                    rightmargin: "0",
                    bgcolor: "ECF5FF",
                    bordercolor: "",
                    basefontcolor: "666666",
                    basefontsize: "10",
                    bgColor: "ffffff",
                    borderThickness: "0",
                    borderColor: "ffffff",
                    showpercentvalues: "0",
                    bgratio: "0",
                    showcanvasborder: "1",
                    canvasBorderColor: "ffffff",
                    showBorder: "0",
                    canvasBorderAlpha: "0",
                    animation: "1",
                    sNumberSuffix: "%",
                    paletteColors: "#66B2B1",
                },
                "categories": [
                    {
                        "category": remediationPrioritiesData
                    }
                ],
                "dataset": [
                    {
                        "seriesName": "",
                        "showValues": "1",
                        "data": remediationPrioritiesValues
                    },
                    {
                        "seriesName": "",
                        "renderAs": "line",
                        "parentyaxis": "S",
                        "color": "f8bd19",
                        "data": remediationPrioritiesPercentage
                    },
                ]
            }

                    }
                });

                $http.post("filterAssessmentFindings.htm", $scope.selected2)
                        .then(function(response) {
                            multiSortViewModel.records = response.data;
                            multiSortViewModel.originalRecords = response.data;
                            multiSortViewModel.totalRecordsCount = response.data.length;
                             if(response.data.length === 0){
                             $(".ExportToCSVId").addClass("active");
                        }else{
                             $(".ExportToCSVId").removeClass("active");
                        }
                        });
  remediationPrioritiesFilterChart($scope.selected2);
            };
            $.ajax({
                method: 'POST',
                url: 'ssidDropDownDetails.htm',
                async: false,
                cache: false,
                contentType: 'application/json'

                , success: function(data) {
                    $scope.multiSelect4 = data;
                }
            });

            $.ajax({
                method: 'POST',
                url: 'applicationDropDownDetails.htm',
                async: false,
                cache: false,
                contentType: 'application/json'

                , success: function(data) {
                    $scope.multiSelect5 = data;
                }
            });

            var rootData = [];
            $.ajax({
                method: 'GET',
                url: 'rootCauseData.htm',
                async: false,
                cache: false,
                contentType: 'application/json'

                , success: function(data) {
                    if (data.categoryLabelList.length > 4) {
                        $("#uitkPopupId2_openModalBtn").css({"display": "block"});
                    } else {
                        $("#uitkPopupId2_openModalBtn").css({"display": "none"});
                    }
                    rootData = data;

                }
            });
            $scope.myDataSource1 = {
                chart: {
                    caption: " ",
                    subCaption: " ",
                    captionAlignment: "left",
                    captionFontColor: "#333333",
                    maxLabelWidthPercent: "150",
                    bgAlpha: "0",
                    plotSpacePercent: "100",
                    labelDisplay: "wrap",
                    maxLabelHeight: "50",
                    showTooltip: "1",
                    toolTipColor: "#666666",
                    toolTipBorderThickness: "1",
                    toolTipBgColor: "#ffffff",
                    toolTipBgAlpha: "80",
                    toolTipBorderRadius: "3",
                    toolTipPadding: "5",
                    numDivLines: "0",
                    baseFont: "arial",
                    showYAxisValues: "0",
                    labelFontSize: "12",
                    valueFontSize: "12",
                    labelPadding: "13",
                    labelFontColor: "#666666",
                    showPlotBorder: "0",
                    yAxisMaxValue: "110",
                    maxColWidth: "15",
                    usePlotGradientColor: "0",
                    showShadow: "0",
                    use3DLighting: "0",
                    showLabels: "1",
                    showValues: "1",
                    showLegend: "0",
                    numberSuffix: "",
                    bgcolor: "#ffffff",
                    borderColor: "#ffffff",
                    showpercentvalues: "0",
                    animation: "1",
                    bgratio: "0",
                    paletteColors: "#523995",
                    canvasBorderAlpha: "0",
                    canvasBorderColor: "#ffffff",
                    scrollheight: "10",
                    flatScrollBars: "1",
                    scrollShowButtons: "0",
                    scrollColor: "#cccccc",
                    showHoverEffect: "1"
                },
                "categories": [
                    {
                        "category": rootData.categoryLabelList
                    }
                ],
                "dataset": [
                    {
                        "data": rootData.categoryDataList
                    }
                ]
            }
            
            var owaspViewData = [];
            $.ajax({
                method: 'GET',
                url: 'owaspData.htm',
                async: false,
                cache: false,
                contentType: 'application/json'

                , success: function(data) {
                    if (data.categoryLabelList.length > 4) {
                        $("#uitkPopupId7_openModalBtn").css({"display": "block"});
                    } else {
                        $("#uitkPopupId7_openModalBtn").css({"display": "none"});
                    }
                    owaspViewData = data;

                }
            });
            $scope.myDataSource7 = {
                chart: {
                    caption: " ",
                    subCaption: " ",
                    captionAlignment: "left",
                    captionFontColor: "#333333",
                    maxLabelWidthPercent: "150",
                    bgAlpha: "0",
                    plotSpacePercent: "100",
                    labelDisplay: "wrap",
                    maxLabelHeight: "50",
                    showTooltip: "1",
                    toolTipColor: "#666666",
                    toolTipBorderThickness: "1",
                    toolTipBgColor: "#ffffff",
                    toolTipBgAlpha: "80",
                    toolTipBorderRadius: "3",
                    toolTipPadding: "5",
                    numDivLines: "0",
                    baseFont: "arial",
                    showYAxisValues: "0",
                    labelFontSize: "12",
                    valueFontSize: "12",
                    labelPadding: "13",
                    labelFontColor: "#666666",
                    showPlotBorder: "0",
                    yAxisMaxValue: "110",
                    maxColWidth: "15",
                    usePlotGradientColor: "0",
                    showShadow: "0",
                    use3DLighting: "0",
                    showLabels: "1",
                    showValues: "1",
                    showLegend: "0",
                    numberSuffix: "",
                    bgcolor: "#ffffff",
                    borderColor: "#ffffff",
                    showpercentvalues: "0",
                    animation: "1",
                    bgratio: "0",
                    paletteColors: "#523995",
                    canvasBorderAlpha: "0",
                    canvasBorderColor: "#ffffff",
                    scrollheight: "10",
                    flatScrollBars: "1",
                    scrollShowButtons: "0",
                    scrollColor: "#cccccc",
                    showHoverEffect: "1"
                },
                "categories": [
                    {
                        "category": owaspViewData.categoryLabelList
                    }
                ],
                "dataset": [
                    {
                        "data": owaspViewData.categoryDataList
                    }
                ]
            }

            var mydata = [];
            $.ajax({
                method: 'GET',
                url: 'osData.htm',
                async: false,
                cache: false,
                contentType: 'application/json'

                , success: function(data) {
                    if (data.categoryLabelList.length > 4) {
                        $("#uitkPopupId1_openModalBtn").css({"display": "block"});
                    } else {
                        $("#uitkPopupId1_openModalBtn").css({"display": "none"});
                    }
                    mydata = data;

                }
            });
//           
            $scope.myDataSource = {
                chart: {
                    caption: " ",
                    subCaption: " ",
                    captionAlignment: "",
                    captionFontColor: "#333333",
                    bgAlpha: "0",
                    labelDisplay: "wrap",
                    maxLabelWidthPercent: "40",
                    showTooltip: "1",
                    toolTipColor: "#666666",
                    toolTipBorderThickness: "1",
                    toolTipBgColor: "#ffffff",
                    toolTipBgAlpha: "80",
                    toolTipBorderRadius: "3",
                    toolTipPadding: "5",
                    numDivLines: "0",
                    baseFont: "arial",
                    showYAxisValues: "0",
                    labelFontSize: "12",
                    valueFontSize: "12",
                    maxColWidth: "15",
                    labelPadding: "13",
                    labelFontColor: "#666666",
                    showPlotBorder: "0",
                    yAxisMaxValue: "50",
                    maxBarHeight: "20",
                    usePlotGradientColor: "0",
                    showShadow: "0",
                    use3DLighting: "0",
                    showLabels: "1",
                    showValues: "1",
                    showLegend: "0",
                    numberSuffix: "",
                    bgcolor: "#ffffff",
                    borderColor: "#ffffff",
                    showpercentvalues: "0",
                    animation: "1",
                    bgratio: "0",
                    paletteColors: "#1965ae",
                    canvasBorderAlpha: "0",
                    canvasBorderColor: "#ffffff",
                },
                "categories": [
                    {
                        "category": mydata.categoryLabelList
                    }
                ],
                "dataset": [
                    {
                        "data": mydata.categoryDataList
                    }
                ]

            }

//            var ssidData = [];
            $.ajax({
                method: 'GET',
                url: 'ssidDetails.htm',
                async: false,
                cache: false,
                contentType: 'application/json'

                , success: function(data) {
                    if (data.categoryLabelList.length > 4) {
                        $("#uitkPopupId3_openModalBtn").css({"display": "block"});
                    } else {
                        $("#uitkPopupId3_openModalBtn").css({"display": "none"});
                    }

                    ssidData = data;

                }
            });

            $scope.myDataSource2 = {
                chart: {
                    caption: " ",
                    subCaption: " ",
                    captionAlignment: "left",
                    captionFontColor: "#333333",
                    maxLabelWidthPercent: "150",
                    bgAlpha: "0",
                    plotSpacePercent: "60",
                    labelDisplay: "wrap",
                    maxLabelHeight: "50",
                    showTooltip: "1",
                    toolTipColor: "#666666",
                    toolTipBorderThickness: "1",
                    toolTipBgColor: "#ffffff",
                    toolTipBgAlpha: "80",
                    toolTipBorderRadius: "3",
                    toolTipPadding: "5",
                    numDivLines: "0",
                    baseFont: "arial",
                    showYAxisValues: "0",
                    labelFontSize: "12",
                    valueFontSize: "12",
                    labelPadding: "13",
                    labelFontColor: "#666666",
                    showPlotBorder: "0",
                    yAxisMaxValue: "110",
                    maxColWidth: "15",
                    usePlotGradientColor: "0",
                    showShadow: "0",
                    use3DLighting: "0",
                    showLabels: "1",
                    showValues: "1",
                    showLegend: "0",
                    numberSuffix: "",
                    bgcolor: "#ffffff",
                    borderColor: "#ffffff",
                    showpercentvalues: "0",
                    animation: "1",
                    bgratio: "0",
                    paletteColors: "#1965ae",
                    canvasBorderAlpha: "0",
                    canvasBorderColor: "#ffffff",
                    scrollheight: "10",
                    flatScrollBars: "1",
                    scrollShowButtons: "0",
                    scrollColor: "#cccccc",
                    showHoverEffect: "1"
                },
                "categories": [
                    {
                        "category": ssidData.categoryLabelList
                    }
                ],
                "dataset": [
                    {
                        "data": ssidData.categoryDataList
                    }
                ]


            }

            var hitrustData = [];
            $.ajax({
                method: 'GET',
                url: 'hitrustDetails.htm',
                async: false,
                cache: false,
                contentType: 'application/json'

                , success: function(data) {
                    if (data.categoryLabelList.length > 4) {
                        $("#uitkPopupId4_openModalBtn").css({"display": "block"});
                    } else {
                        $("#uitkPopupId4_openModalBtn").css({"display": "none"});
                    }
                    hitrustData = data;

                }
            });
            $scope.myDataSource3 = {
                chart: {
                    caption: " ",
                    subCaption: " ",
                    captionAlignment: "",
                    captionFontColor: "#333333",
                    bgAlpha: "0",
                    labelDisplay: "wrap",
                    maxLabelWidthPercent: "30",
                    showTooltip: "1",
                    toolTipColor: "#666666",
                    toolTipBorderThickness: "1",
                    toolTipBgColor: "#ffffff",
                    toolTipBgAlpha: "80",
                    toolTipBorderRadius: "3",
                    toolTipPadding: "5",
                    numDivLines: "0",
                    baseFont: "arial",
                    showYAxisValues: "0",
                    labelFontSize: "12",
                    valueFontSize: "12",
                    labelPadding: "13",
                    labelFontColor: "#666666",
                    showPlotBorder: "0",
                    yAxisMaxValue: "50",
                    maxColWidth: "15",
                    maxBarHeight: "20",
                    usePlotGradientColor: "0",
                    showShadow: "0",
                    use3DLighting: "0",
                    showLabels: "1",
                    showValues: "1",
                    showLegend: "0",
                    numberSuffix: "",
                    bgcolor: "#ffffff",
                    borderColor: "#ffffff",
                    showpercentvalues: "0",
                    animation: "1",
                    bgratio: "0",
                    paletteColors: "#078576",
                    canvasBorderAlpha: "0",
                    canvasBorderColor: "#ffffff",
                },
                "categories": [
                    {
                        "category": hitrustData.categoryLabelList
                    }
                ],
                "dataset": [
                    {
                        "data": hitrustData.categoryDataList
                    }
                ]
            }


            $.ajax({
                method: 'GET',
                url: 'applicationData.htm',
                async: false,
                cache: false,
                contentType: 'application/json'

                , success: function(data) {

                    if (data.categoryLabelList.length > 4) {
                        $("#uitkPopupId5_openModalBtn").css({"display": "block"});
                    } else {
                        $("#uitkPopupId5_openModalBtn").css({"display": "none"});
                    }
                    appData = data;

                }
            });
//           
            $scope.myDataSource4 = {
                chart: {
                    caption: "",
                    subCaption: " ",
                    captionAlignment: "left",
                    captionFontColor: "#333333",
                    maxLabelWidthPercent: "150",
                    bgAlpha: "0",
                    plotSpacePercent: "60",
                    labelDisplay: "wrap",
                    maxLabelHeight: "50",
                    showTooltip: "1",
                    toolTipColor: "#666666",
                    toolTipBorderThickness: "1",
                    toolTipBgColor: "#ffffff",
                    toolTipBgAlpha: "80",
                    toolTipBorderRadius: "3",
                    toolTipPadding: "5",
                    numDivLines: "0",
                    baseFont: "arial",
                    showYAxisValues: "0",
                    labelFontSize: "12",
                    valueFontSize: "12",
                    labelPadding: "13",
                    labelFontColor: "#666666",
                    showPlotBorder: "0",
                    yAxisMaxValue: "110",
                    maxColWidth: "15",
                    usePlotGradientColor: "0",
                    showShadow: "0",
                    use3DLighting: "0",
                    showLabels: "1",
                    showValues: "1",
                    showLegend: "0",
                    numberSuffix: "",
                    bgcolor: "#ffffff",
                    borderColor: "#ffffff",
                    showpercentvalues: "0",
                    animation: "1",
                    bgratio: "0",
                    paletteColors: "#523995",
                    canvasBorderAlpha: "0",
                    canvasBorderColor: "#ffffff",
                    scrollheight: "10",
                    flatScrollBars: "1",
                    scrollShowButtons: "0",
                    scrollColor: "#cccccc",
                    showHoverEffect: "1"
                },
                "categories": [
                    {
                        "category": appData.categoryLabelList
                    }
                ],
                "dataset": [
                    {
                        "data": appData.categoryDataList
                    }
                ]
            }

var remYAxis;
            $.ajax({
                method: 'POST',
                url: 'remPriorities.htm',
                async: false,
                cache: false,
                contentType: 'application/json'

                , success: function(data) {
//                    alert(JSON.stringify(data));
                    remediationPrioritiesData = data.categoryLabelList;
                    remediationPrioritiesValues = data.categoryDataList;
                    remediationPrioritiesPercentage = data.percentageCountList;
                    remYAxis=data.remYAxis;
                    if (remediationPrioritiesData.length > 5) {
                        $("#uitkPopupId6_openModalBtn").css({"display": "block"});
                    } else {
                        $("#uitkPopupId6_openModalBtn").css({"display": "none"});
                    }
                    
                   
                }
            });

            $scope.myDataSource5 = {
                "chart": {
                    caption: "",
                    captionAlignment: "left",
                    captionFontColor: "333333",
                    yAxisName: remYAxis,
                    bgAlpha: "0",
                    labelDisplay: "wrap",
                    maxLabelHeight: "50",
                    showTooltip: "1",
                    maxColWidth: "15",
                    toolTipColor: "666666",
                    toolTipBorderThickness: "1",
                    toolTipBgColor: "ffffff",
                    toolTipBgAlpha: "80",
                    toolTipBorderRadius: "2",
                    toolTipPadding: "5",
                    numDivLines: "0",
                    pYAxisNameFontSize: "14",
                    chartBottomMargin: "0",
                    chartLeftMargin: "0",
                    chartTopMargin: "0",
                    chartRightMargin: "0",
                    baseFont: "arial",
                    showYAxisValues: "0",
                    labelFontSize: "12",
                    valueFontSize: "12",
                    labelPadding: "13",
                    labelFontColor: "666666",
                    showPlotBorder: "0",
                    yAxisMaxValue: "60",
                    maxBarHeight: "15",
                    plotGradientcolor: "",
                    showShadow: "0",
                    use3DLighting: "0",
                    legendShadow: "0",
                    showLabels: "1",
                    showValues: "1",
                    showLegend: "0",
                    numberSuffix: "",
                    rightmargin: "0",
                    bgcolor: "ECF5FF",
                    bordercolor: "",
                    basefontcolor: "666666",
                    basefontsize: "10",
                    bgColor: "ffffff",
                    borderThickness: "0",
                    borderColor: "ffffff",
                    showpercentvalues: "0",
                    bgratio: "0",
                    showcanvasborder: "1",
                    canvasBorderColor: "ffffff",
                    showBorder: "0",
                    canvasBorderAlpha: "0",
                    animation: "1",
                    sNumberSuffix: "%",
                    paletteColors: "#66B2B1",
                },
                "categories": [
                    {
                        "category": remediationPrioritiesData
                    }
                ],
                "dataset": [
                    {
                        "seriesName": "",
                        "showValues": "1",
                        "data": remediationPrioritiesValues
                    },
                    {
                        "seriesName": "",
                        "renderAs": "line",
                        "parentyaxis": "S",
                        "color": "f8bd19",
                        "data": remediationPrioritiesPercentage
                    },
                ]
            }

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


function htmlDecode(input) {
    var e = document.createElement('div');
    e.innerHTML = input;
    return e.childNodes.length === 0 ? "" : e.childNodes[0].nodeValue;
}


FusionCharts.ready(function() {
    $.ajax("severityDetails.htm", {
//    dataType: "text",
        cache: false,
        contentType: 'application/json',
        accepts: {
            text: "application/json"
        },
        success: loadChart4
    });




});
// function severityFilterChart(obj) {
//        $.ajax("severityFilterDetails.htm", {
//            method: "POST",
//            data: JSON.stringify(obj),
//            contentType: 'application/json',
//            accepts: {
//                text: "application/json"
//            },
//            success: loadChart4
//        });
//    }

function loadChart4(jsonString) {
    var myChart4 = new FusionCharts({
        "type": "bar2d",
        "renderAt": "SecuritySeverity",
        "width": "100%",
        "height": "175",
        "dataFormat": "xml",
        "dataSource": htmlDecode(jsonString[0])

    }).render();

}



function applicationFilterChart(obj) {
    $.ajax("applicationFilterDetails.htm", {
        method: "POST",
        data: JSON.stringify(obj),
        cache: false,
        contentType: 'application/json',
        accepts: {
            text: "application/json"
        },
        success: loadChart5
    });
}


FusionCharts.ready(function() {
    $.ajax("topApplicationDetails.htm", {
//    dataType: "text",
        cache: false,
        contentType: 'application/json',
        accepts: {
            text: "application/json"
        },
        success: loadChart5
    });
});
function loadChart5(jsonString) {
    var myChart5 = new FusionCharts({
        "type": "bar2d",
        "renderAt": "SecurityApp",
        "width": "100%",
        "height": "175",
        "dataFormat": "xml",
        "dataSource": htmlDecode(jsonString[0])

    }).render();

}
function ssidFilterChart(obj) {
    $.ajax("filterTopssidFilterDetails.htm", {
        method: "POST",
        data: JSON.stringify(obj),
        cache: false,
        contentType: 'application/json',
        accepts: {
            text: "application/json"
        },
        success: loadChart6
    });
}

FusionCharts.ready(function() {
    $.ajax("topssidDetails.htm", {
//    dataType: "text",
        cache: false,
        contentType: 'application/json',
        accepts: {
            text: "application/json"
        },
        success: loadChart6
    });
});
function loadChart6(jsonString) {
    var myChart4 = new FusionCharts({
        "type": "bar2d",
        "renderAt": "ssid",
        "width": "100%",
        "height": "175",
        "dataFormat": "xml",
        "dataSource": htmlDecode(jsonString[0])

    }).render();

}



function rootCauseFilterChart(obj) {
    $.ajax("filterTopRootCauses.htm", {
        method: "POST",
        data: JSON.stringify(obj),
        cache: false,
        contentType: 'application/json',
        accepts: {
            text: "application/json"
        },
        success: loadChart7
    });
}

FusionCharts.ready(function() {
    $.ajax("topRootCauses.htm", {
//    dataType: "text",
        cache: false,
        contentType: 'application/json',
        accepts: {
            text: "application/json"
        },
        success: loadChart7
    });
});

function loadChart7(jsonString) {
    var myChart7 = new FusionCharts({
        "type": "bar2d",
        "renderAt": "SecurityRoot",
        "width": "100%",
        "height": "175",
        "dataFormat": "xml",
        "dataSource": htmlDecode(jsonString[0])

    }).render();

}





function applicationMostVulnerable(obj) {
    $.ajax("filterApplicationMostVulnerable.htm", {
        method: "POST",
        data: JSON.stringify(obj),
        cache: false,
        contentType: 'application/json',
        accepts: {
            text: "application/json"
        },
        success: loadChart9
    });
}

FusionCharts.ready(function() {
    $.ajax("applicationMostVulnerable.htm", {
//    dataType: "text",
        cache: false,
        contentType: 'application/json',
        accepts: {
            text: "application/json"
        },
        success: loadChart9
    });
});

function loadChart9(jsonString) {
    var myChart9 = new FusionCharts({
        "type": "stackedbar2d",
        "renderAt": "applicationMostVulnerable",
        "width": "100%",
        "height": "180",
        "dataFormat": "xml",
        "dataSource": htmlDecode(jsonString[0])

    }).render();

}

function remediationPrioritiesFilterChart(obj) {
    $.ajax("filterTopRemediationPriorities.htm", {
        method: "POST",
        data: JSON.stringify(obj),
        cache: false,
        contentType: 'application/json',
        accepts: {
            text: "application/json"
        },
        success: loadChart10
    });
}


FusionCharts.ready(function() {
    $.ajax("topRemediationPriorities.htm", {
//    dataType: "text",
        cache: false,
        contentType: 'application/json',
        accepts: {
            text: "application/json"
        },
        success: loadChart10
    });
});

function loadChart10(jsonString) {
    var myChart10 = new FusionCharts({
        "type": "mscombi2d",
        "renderAt": "priorityrem",
        "width": "100%",
        "height": "175",
        "dataFormat": "xml",
        "dataSource": htmlDecode(jsonString[0])
    }).render();
      $("#loading").css({"display": "none"});
}
FusionCharts.ready(function() {
    $.ajax("owaspChartData.htm", {
//    dataType: "text",
        cache: false,
        contentType: 'application/json',
        accepts: {
            text: "application/json"
        },
        success: loadChart11
    });
});
function owaspFilterChart(obj) {
    $.ajax("filterOwaspChartData.htm", {
        method: "POST",
        data: JSON.stringify(obj),
        cache: false,
        contentType: 'application/json',
        accepts: {
            text: "application/json"
        },
        success: loadChart11
    });
}
function loadChart11(jsonString) {
    var myChart11 = new FusionCharts({
        "type": "bar2d",
        "renderAt": "owaspId",
        "width": "100%",
        "height": "175",
        "dataFormat": "xml",
        "dataSource": htmlDecode(jsonString[0])

    }).render();

}