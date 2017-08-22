var jsScope;
angular.module("UitkApp")

        .factory(
                'multiSortViewModelMatrix',
                function(globalSuccessMessageViewModel, globalErrorMessageViewModel) {

                    return  {
                        clearAllFilters: true,
                        records: [],
                        totalRecordsCount: 0,
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
                                    '<label for="risklevelFilter" class="oui-a11y-hidden">Filter by Risk Level</label> ',
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
                                columnId: 'costEffort',
                                label: 'Cost Effort',
                                sortOrder: 0,
                                showAlways: true,
                                sortable: true,
                                layoutOrder: 4,
                                enableSearch: true,
                                searchByItemType: 'exact',
                                cellHeaderTemplate: [
                                    '<label for="costeffortFilter" class="oui-a11y-hidden">Filter by Cost/Effort</label> ',
                                    '<select ng-model="column.searchInput" id="costeffortFilter">',
                                    '	<option value="">All</option>',
                                    '	<option value="High">High</option>',
                                    '	<option value="Medium">Medium</option>',
                                    '	<option value="Low">Low</option>',
                                    '</select> '
                                ].join(''),
                                cellTemplate: '<span ng-bind="::record.costEffort"> </span>',
                                dataType: 'text',
                                style: 'width:9%'
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
                                    '<label for="descriptionFilter" class="oui-a11y-hidden">Filter by Status</label> ',
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
                        ],
                        links: [
                            '<a href="" class="ExportToCSVId" title="Export (CSV)" ng-click="model.popModalExport(record,$event,this)"  ><span  class="cux-icon-export ExportToCSVId" ></span> Export (CSV) </a>'
                        ],
                        //multiSortColumns : {sortBy: ["firstName","lastName"], sortOrder: [1,-1]},
                        //isMultiSortApplied : true,
//                        recordOperationInProgress: true,
                        showErrorMessage: function() {
                            globalErrorMessageViewModel.content = "<span> Multi sort drawer can not be opened when add, edit or hide column functionality is in progress. </span>";
                            globalErrorMessageViewModel.visible = true;
                        },
                        showSuccessMessage: function(message) {
                            globalSuccessMessageViewModel.content = '<span>' + message + '</span>';
                            globalSuccessMessageViewModel.visible = true;
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

        .controller('UitkCtrl', function($scope, $http, $translate, $timeout, multiSortViewModelMatrix, globalSuccessMessageViewModel, globalErrorMessageViewModel) {
            $("#loading").css({"display": "block"});
            var allFindingsText = "All";
            document.getElementById("catNameID").innerHTML = allFindingsText;
            jsScope = $scope;
            $scope.checkedValues = [];
            $scope.items  = [];
            $scope.items1 = [];
            $scope.items2 = [];
            $scope.items3 = [];
            var categoryCode = "";

            $scope.multiSortModel4 = multiSortViewModelMatrix;

            $scope.getRoadMapFindingsData = function() {
                categoryCode="";
                document.getElementById("catNameID").innerHTML=allFindingsText;
                $http.post("getRoadMapFindingsData.htm")
                        .then(function(response) {
                            multiSortViewModelMatrix.records = response.data;
                            multiSortViewModelMatrix.originalRecords = response.data;
                            multiSortViewModelMatrix.totalRecordsCount = response.data.length;
                            
                            if (response.data.length === 0) {
                                $(".ExportToCSVId").addClass("active");
                                $("#Labels1").css({"display": "none"});
                            } else {
                                $(".ExportToCSVId").removeClass("active");
                            }
                            $("#loading").css({"display": "none"});  

                        });
                      
            };
            jsScope.getRoadMapFindingsData();


            $scope.getFindingsByCategory = function(obj,catName) {
                categoryCode = obj;
                var categoryName=catName;
                document.getElementById("catNameID").innerHTML=categoryName;
                $http.post("getRoadMapFindingsData.htm?catCode=" + obj)
                        .then(function(response) {
                            multiSortViewModelMatrix.records = response.data;
                            multiSortViewModelMatrix.originalRecords = response.data;
                            multiSortViewModelMatrix.totalRecordsCount = response.data.length;
                    $("#loading").css({"display": "none"});
                        });
                
            };
            

            //START Export To CSV Check Boxes
            $scope.$on('viewOfModalExport', function(event, dataSet) {
                $.ajax({
                    method: 'POST',
                    url: 'getExportCSVCheckBoxes.htm',
                    async: false,
                    contentType: 'application/json'

                    , success: function(data) {
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


            $scope.Cancel = function() {//close Modal
                $scope.multiSortModel4.modalShownExport = false;
            }
//END Export To CSV Check Boxes

//START Export CSV FILE
            $scope.ExportCSV = function() {
//                 alert(categoryCode);
                $scope.multiSortModel4.modalShownExport = false;
                $("#downloading").css({"display": "block"});
                $http.post("roadMapDataForCSVDownload.htm?catCode=" + categoryCode, $scope.checkedValues)
                        .then(function(response) {
                            window.open("downloadCSVFile.htm?rName=Yfnkpe04r7-wB4p55EeOGg");
                            $("#downloading").css({"display": "none"});

                        });

            };
//END EXPORT CSV FILE

//End Clear the table filter values

        })

function categoryDetailsByID(obj,catName) {
    $("#loading").css({"display": "block"});
    clearTableFields();
    jsScope.getFindingsByCategory(obj,catName);
}
function totalRoadMapFindingData() {
    $("#loading").css({"display": "block"});
   clearTableFields();
    jsScope.getRoadMapFindingsData();
}


//Start Clear the table filter values
function clearTableFields() {
    $("#risklevelFilter").val('');
    $("#costeffortFilter").val('');
     $('input[type="text"][id*="Filter"]').each(function () {
         $(this).val('');
     });
}

function htmlDecode(input) {
    var e = document.createElement('div');
    e.innerHTML = input;
    return e.childNodes.length === 0 ? "" : e.childNodes[0].nodeValue;
}

FusionCharts.ready(function() {
    $.ajax("roadMapData.htm", {
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
            "type": "bubble",
            "renderAt": "RoadMap",
            "width": "100%",
            "height": "510",
            "dataFormat": "xml",
            "dataSource": htmlDecode(jsonString[0])

        }).render();
        $("#loading").css({"display": "none"});
    }


});


