var jsScope;
//$(window).load(function () {
////    $("#loading").fadeIn();
//});
//$(document).ajaxStart(function () {
//    $("#loading").css({"display": "block"});
//}).ajaxStop(function () {
//    $("#loading").css({"display": "none"});
//});
angular.module("UitkApp")
        .factory('breadcrumbNavigationViewModel', function () {
            return {
                id: 'breadcrumbId',
                links: [
                    {template: '<span><span class="cux-icon-home"></span>Home</span>', url: '#home'},
                    {template: '<span>Hartford Network</span>', url: '#item2'},
                    {template: '<span>Security Risk Triage</span>', url: '#item3'},
                    {template: '<span>Summary</span>', url: '#item3'}
                ]

            };
        })

        .factory(
                'multiSortViewModel',
                function (globalSuccessMessageViewModel, globalErrorMessageViewModel) {

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
//                        patientService1.query(filterCondition, function(result) {
//                            that.totalRecordsCount = result.totalRecordsCount;
//                            that.records = result.records;
//                        });
//                    },
                        columns: [
//                             {
//                                columnId: 'sNo',
//                                label: 'S No',
//                                showAlways: true,
//                                sortOrder: 1,
//                                sortable: true,
//                                layoutOrder: 1,
//                                enableSearch: true,
//                                cellHeaderTemplate: [
//                                    '<label for="sNoFilter" class="oui-a11y-hidden">Filter by No</label> ',
//                                    '<input type="text" ng-model="column.searchInput" id="sNoFilter"/> '
//                                ].join(''),
//                                cellTemplate: '<span ng-bind="::record.sNo"> </span>',
//                                dataType: 'text',
//                                style: 'width:6%'
//                            },
                            {
                                columnId: 'id',
                                label: 'ID',
                                showAlways: true,
                                sortOrder: 0,
                                sortable: true,
                                layoutOrder: 1,
                                enableSearch: true,
                                cellHeaderTemplate: [
                                    '<label for="idFilter" class="oui-a11y-hidden">Filter by ID</label> ',
                                    '<input type="text" ng-model="column.searchInput" id="idFilter"/> '
                                ].join(''),
                                cellTemplate: '<span ng-bind="::record.id"> </span>',
                                dataType: 'text',
                                style: 'width:7%'
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
                                dataType: 'text',
                                style: 'width:10%'
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
                                    '<input type="text" ng-model="column.searchInput" id="rootCauseDetailFilter"/> '
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
                                cellTemplate: '<a href="#" ng-click="model.popModal(record,$event,this)"><span ng-bind="::record.hitrust"> </span></a>',
                                dataType: 'text'
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
                            }
                        ],
                        links: [
                            '<a href="" class="ExportToCSVId" title="Export (CSV)" ng-click="model.popModalExport(record,$event,this)"  ><span  class="cux-icon-export ExportToCSVId" ></span> Export (CSV) </a>'
                            +'<a href="" ng-click="model.openShowHideColumnsDrawer()" ng-hide="model.recordOperationInProgress" ><a href="" class="ExportToCSVId" title="Show/Hide Regulations" ng-click="model.openShowHideColumnsDrawer()" ng-hide="model.recordOperationInProgress" ><span class="cux-icon-edit ExportToCSVId"></span> Show/Hide Regulations<span class="oui-a11y-hidden" > Enter the required fields and submit to create a new record </span> </a> <span ng-show="model.recordOperationInProgress" aria-disabled="true" ><span class="cux-icon-edit"></span> Show/Hide Regulations</span> '
                        ],
                        //multiSortColumns : {sortBy: ["firstName","lastName"], sortOrder: [1,-1]},
                        //isMultiSortApplied : true,
                        recordOperationInProgress: true,
                        showErrorMessage: function () {
                            globalErrorMessageViewModel.content = "<span> Multi sort drawer can not be opened when add, edit or hide column functionality is in progress. </span>";
                            globalErrorMessageViewModel.visible = true;
                        },
                        showSuccessMessage: function (message) {
                            globalSuccessMessageViewModel.content = '<span>' + message + '</span>';
                            globalSuccessMessageViewModel.visible = true;
                        },
                        modalShown: false,
                        popModal: function (record, event, scope) {
                            this.modalShown = true;//show modal
                            event.stopPropagation();//don't open or close expandable
                            scope.$emit('viewOfModal', record);//send the record to who ever is listening
                        },
                        modalShownExport: false,
	        popModalExport: function (record, event, scope) {
                this.modalShownExport = true;//show modal
	            event.stopPropagation();//don't open or close expandable
	            scope.$emit('viewOfModalExport', record);//send the record to who ever is listening
	        }
                    };
                })

        .factory('globalSuccessMessageViewModel', function () {
            return {
                id: 'global-success-message',
                messageType: 'success',
                content: '<span>This is success message</span>',
                visible: false,
                messageRole: 'alertdialog',
                ariaAttributes: true
            };
        })
        .factory('globalErrorMessageViewModel', function () {
            return {
                id: 'global-error-message',
                messageType: 'error',
                content: '<span>This is error message</span>',
                visible: false,
                messageRole: 'alert'
            };
        })
        /*
         .controller('UitkCtrl', function ($scope, $http, $translate, primaryNavViewModel, 
         breadcrumbNavigationViewModel, secondaryNavigationViewModel, multiSortViewModel, 
         globalSuccessMessageViewModel, globalErrorMessageViewModel) {
         */
        .controller('UitkCtrl', function ($scope, $http, $translate,
                multiSortViewModel, breadcrumbNavigationViewModel,
                globalSuccessMessageViewModel, globalErrorMessageViewModel) {

            jsScope = $scope;
            $scope.apiItems = [];
            var engkey = $("#engkey").val();
            $scope.selected2 = [];
            $scope.selected1 = [];
            $scope.selected3 = [];
            $scope.selectedTotal = [];
            $scope.hitrustList = [];
            $scope.items = [];
            $scope.items1 = [];
            $scope.items2 = [];
            $scope.items3 = [];
            var findingId = "";
            $scope.callbackFunctionInController = function (argument) {
                console.log("callbackFunctionInController:" + argument);
            };
          
            $scope.breadcrumbViewModel = breadcrumbNavigationViewModel;

            $scope.$on('viewOfModal', function (event, data) {
                var hData = data.hitrust;
                $.ajax({
                    method: 'POST',
                    url: 'getHitrustInfoByCtrlCD.htm?hData=' + hData,
                    async: false,
                    contentType: 'application/json'

                    , success: function (reddata) {
                         $scope.hitrustList = reddata;
                    }
                });


            });
            $.ajax({
                method: 'POST',
                url: 'getSummaryServices.htm?engkey=' + engkey,
                async: false,
                contentType: 'application/json'

                , success: function (data) {
                    $scope.multiSelect1 = data.serviceListSummary;
                    $scope.multiSelect2 = data.severityListSummary;
                    $scope.multiSelect3HITRUST = data.catagoryListSummary;

                }
            });
            $scope.multiSortModel = multiSortViewModel;
            $http.post("getsummaryTableData.htm?engkey=" + engkey, $scope.selectedTotal)
                    .then(function (response) {
                        multiSortViewModel.records = response.data;
                        multiSortViewModel.originalRecords = response.data;
                        multiSortViewModel.totalRecordsCount = response.data.length;
                            $("#loading").css({"display": "none"});
                             if(response.data.length === 0){
                             $(".ExportToCSVId").addClass("active");
                        }else{
                             $(".ExportToCSVId").removeClass("active");
                        }
//                             $("#loading").fadeOut();
                    });

//            multiSortViewModel.onExport = function (filterCondition) {
//                window.open('csvExport.htm?engkey='+ engkey+'&param='+JSON.stringify($scope.selectedTotal));
//            };
            $scope.publishEng = function () {
                $http.post("publishEngagment.htm?engkey=" + engkey, "")
                        .then(function (response) {
                        $scope.publishedOn = response.data;
                            $("#uitkPopupId2_openModalBtn").css({"display": "none"});
                        });
//                 window.location.href = "publishEngagment.htm?engKey=" + $("#engkey").val();
            };
            
            $scope.successMessageModel = {
                id: 'success-message',
                messageType: 'success',
                content: '<span>Engagement has been successfully published</span>',
                visible: false,
                messageRole: 'alertdialog',
                ariaAttributes: true,
                headingLevel: '2',
                messageVisibleTime: 10000

            };

            $scope.showSuccessMessage = function (e) {
                $scope.successMessageModel.visible = true;
                $scope.successMessageModel.activeItem = e.target;
            };


            $scope.Save = function () {//close Modal
		    $scope.multiSortModel.modalShown = false;
                    $scope.obj.modalShown2 = false;
                     $scope.multiSortModel.modalShownExport = false;
		};

            $scope.getFindingDetailsang = function (obj,sObj) {
                findingId=obj;
//                alert(findingId);
                $scope.selectedTotal = [];
                if (($scope.selected1))
                    $scope.selectedTotal = $scope.selectedTotal.concat($scope.selected1);
                if (($scope.selected2))
                    $scope.selectedTotal = $scope.selectedTotal.concat($scope.selected2);
                if (($scope.selected3))
                    $scope.selectedTotal = $scope.selectedTotal.concat($scope.selected3);

                $http.post("getsummaryTableData.htm?engkey=" + engkey + '&fndId=' + obj+ '&sNo=' + sObj, $scope.selectedTotal)
                        .then(function (response) {
                              $("#loading").css({"display": "none"});
                            multiSortViewModel.records = response.data;
                            multiSortViewModel.originalRecords = response.data;
                            multiSortViewModel.totalRecordsCount = response.data.length;
                             if(response.data.length === 0){
                             $(".ExportToCSVId").addClass("active");
                        }else{
                             $(".ExportToCSVId").removeClass("active");
                        }
                        });
            };

            $scope.newValue = function () {
                findingId = "";
                 $("#loading").css({"display": "block"});
                $scope.selectedTotal = [];
                if (($scope.selected1))
                    $scope.selectedTotal = $scope.selectedTotal.concat($scope.selected1);
                if (($scope.selected2))
                    $scope.selectedTotal = $scope.selectedTotal.concat($scope.selected2);
                if (($scope.selected3))
                    $scope.selectedTotal = $scope.selectedTotal.concat($scope.selected3);

                filterChart($scope.selectedTotal);
                clearFields();
                $http.post("getsummaryTableData.htm?engkey=" + engkey, $scope.selectedTotal)
                        .then(function (response) {
                            $("#loading").css({"display": "none"});
                            multiSortViewModel.records = response.data;
                            multiSortViewModel.originalRecords = response.data;
                            multiSortViewModel.totalRecordsCount = response.data.length;
                              if(response.data.length === 0){
                             $(".ExportToCSVId").addClass("active");
                        }else{
                             $(".ExportToCSVId").removeClass("active");
                        }
                        });
               
            };

            $scope.refreshValue = function () {
                findingId = "";
                 $("#loading").css({"display": "block"});
                $scope.selectedTotal = [];
                $.ajax({
                    method: 'POST',
                    url: 'getSummaryServices.htm?engkey=' + engkey,
                    async: false,
                    contentType: 'application/json'

                    , success: function (data) {
                        $scope.multiSelect1 = data.serviceListSummary;
                        $scope.multiSelect2 = data.severityListSummary;
                        $scope.multiSelect3HITRUST = data.catagoryListSummary;

                    }
                });



                filterChart($scope.selectedTotal);
                clearFields();
                $http.post("getsummaryTableData.htm?engkey=" + engkey, $scope.selectedTotal)
                        .then(function (response) {
                            $("#loading").css({"display": "none"});
                            multiSortViewModel.records = response.data;
                            multiSortViewModel.originalRecords = response.data;
                            multiSortViewModel.totalRecordsCount = response.data.length;
                             if(response.data.length === 0){
                             $(".ExportToCSVId").addClass("active");
                        }else{
                             $(".ExportToCSVId").removeClass("active");
                        }
                        });
            };
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
//                 alert(findingId);
                $scope.multiSortModel.modalShownExport = false;
                $("#downloading").css({"display": "block"});
                $scope.TotalSelected = [];
                $scope.TotalSelected = $scope.TotalSelected.concat($scope.selectedTotal);
                $scope.TotalSelected = $scope.TotalSelected.concat($scope.checkedValues);
//                                alert(JSON.stringify($scope.TotalSelected));
                $http.post("exportCSVForCheckedColumns.htm?engkey=" + engkey+ '&fndId=' + findingId, $scope.TotalSelected)
                        .then(function (response) {
                            window.open("downloadCSVFile.htm?rName=S17FOy7OLecjshe3XLzguw");
                            $("#downloading").css({"display": "none"});

                        });

            };
            
            $scope.accModel = {
                panels: [
                    {title: 'Vulnerability Categories', templateUrl: 'dashjs/VulnCategory.jsp', open: false,
                        links: [
                            //{text:'by Date', callBack:$scope.clickMe, disabled:true},
                            // {text:'by Payer', callBack:$scope.clickMe},
                            //{text:'<span class="cux-icon-settings" ><span class="oui-a11y-hidden" >settings</span></span>', callBack:function(){alert("inline Call");}}
                        ]
                   }
                ]
            };

            $scope.obj = {modalShown2: false};

            $scope.toggleModal2 = function () {
                $scope.obj.modalShown2 = !$scope.obj.modalShown2;
            };
            $scope.Cancel = function () {//close Modal
		    $scope.multiSortModel.modalShownExport = false;
//		    $scope.activeItem.firstName = $scope.savedTempItem.firstName;
		};

            $scope.globalSuccessMessageModel = globalSuccessMessageViewModel;
            $scope.globalErrorMessageModel = globalErrorMessageViewModel;

        })

        .config(['uitkConsumerConfigsProvider', '$translateProvider', function (uitkConsumerConfigsProvider, $translateProvider) {
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
                            "Dynamic Table Demo - Multi-Select": "Tabla dinÃ¡mica Demo - Multi -Select",
                            "First Name": "Nombre",
                            "Last Name": "Apellido",
                            "Gender": "GÃ©nero",
                            "Email": "Correo",
                            "No records found.": "No se encontrarÃ³n archivos.",
                            "Show": "Show",
                            "per page": "Por pÃ¡gina",
                            "First": "Primero",
                            "Previous": "Anterior",
                            "Next": "Siguiente",
                            "Last": "Ãºltimo",
                            "Total Records:": "Registros totales:",
                            "Clear All Filters": "Borrar todos los filtros",
                            "Export (CSV)": "ExportaciÃ³n(CSVâ€‹â€‹)",
                            "Duplicate column id names found": "nombres de ID de columna duplicados encontrados",
                            "Only single column sort is supported": "SÃ³lo se admite una sola columna de clasificaciÃ³n",
                            " is not supported": " no es apoyado",
                            "Restore Default Sort Order": "Restaurar orden de clasificaciÃ³n predeterminado",
                            "Sort by": "Ordenar por",
                            "Then by": "Entonces por",
                            "Remove": "retirar",
                            "Choose column(s) to sort": "Elija la columna (s) para ordenar",
                            "Add another sort column": "AÃ±adir otra columna de clasificaciÃ³n"
                        });


                $translateProvider.preferredLanguage('en_US');
                $translateProvider.useSanitizeValueStrategy('escaped');

            }]);

function getFindingDetails(obj,sObj) {
     $("#loading").css({"display": "block"});
    jsScope.getFindingDetailsang(obj,sObj);

}

function clearFields() {
    $("#risklevelFilter").val('');
     $('input[type="text"][id*="Filter"]').each(function () {
         $(this).val('');
//        alert($(this).val(''));
     });
}
FusionCharts.ready(function () {
    var engkey = $("#engkey").val();
    $.ajax({
        method: 'POST',
        url: 'getCharts.htm?engkey=' + engkey,
        async: false,
        contentType: 'application/json'

        , success: loadChart
    });


});
function filterChart(obj) {
    var engkey = $("#engkey").val();
    $.ajax("getFilterCharts.htm?engkey=" + engkey, {
        method: "POST",
        data: JSON.stringify(obj),
        contentType: 'application/json',
        accepts: {
            text: "application/json"
        },
        success: loadChart
    });
}
function loadChart(jsonString) {
//    alert(JSON.stringify(jsonString.chartTEXT));
    $("#chartDiv").append('<div id=' + jsonString.chartName + ' align="center">');
    var myChart = new FusionCharts({
        "type": "bubble",
        "renderAt": jsonString.chartName,
        "width": "100%",
        "height": "455",
        "dataFormat": "xml",
        "dataSource": jsonString.chartTEXT

    });
    myChart.render();
}
FusionCharts.ready(function () {
    var engKey = $("#engkey").val();
    $.ajax("getFindingsChart.htm?engKey=" + engKey, {
//    dataType: "text",
        cache: false,
        contentType: 'application/json',
        accepts: {
            text: "application/json"
        },
        success: loadChart
    });
    
      $.ajax({
        method: 'POST',
        url: 'getSummaryTopRootCauseChart.htm?engkey=' + engKey,
        async: false,
        contentType: 'application/json'

        , success: loadScrollColumnChart
    });

    function loadChart(jsonString) {
        jQuery.each(jsonString, function (index, item) {
            $("#findiingsId").append('<div class="tk-width-full-1t-min tk-float-left"><div id=' + item.chartName + ' align="left"> </div>');
            var myChart1 = new FusionCharts({
                "type": "doughnut2d",
                "renderAt": item.chartName,
                "width": "100%",
                "height": "160",
                "dataFormat": "xml",
                "dataSource": item.chartTEXT

            }).render();
        });

    }

});
function loadScrollColumnChart(jsonString){
//    alert(jsonString.categoryLabelList.length);
    if(jsonString.categoryLabelList.length === 0){
        $("#chart-container").html("<p style='text-align: center;margin-top: 80px;color: rgb(102, 102, 102);font-family: Verdana, sans;font-size: 10px;'>No data to display.</p>");
    }
    else{
var revenueChart = new FusionCharts({
    type: 'scrollColumn2d',
    renderAt: 'chart-container',
    width: '100%',
    height: '250',
    dataFormat: 'json',
    dataSource: {
        "chart": {
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
            yAxisMaxValue: "130",
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
                "category": jsonString.categoryLabelList
            }
        ],
        "dataset": [
            {
                "data": jsonString.categoryDataList
            }
        ]
    }
});

revenueChart.render();
}
}


