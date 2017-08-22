
(function() {

    var uitkDynamicTableService = function($compile, $rootScope, $timeout) {
        return {
            render: function(model) {
                var isolatedScope = $rootScope.$new(true);
                model.isExporting = true;
                isolatedScope.exportModel = model;
                $timeout(function() {
                    isolatedScope.$destroy();
                }, 500);
                return $compile("<uitk:dynamic-table view-model='exportModel'></uitk:dynamic-table>")(isolatedScope);
            }
        };
    };

    var uitkDynamicTable = function($timeout, uitkDynamicTableService, uitkDynamicTableExporter, $filter, uitkExceptionService, columnCombinations, uitkLiveRegionService) {
        function controller($scope, tableSortEnum, uitkEvents, $attrs, uitkTools, $element) {
            if ($scope.viewModel) {
                $scope.model = $scope.viewModel;
            }

            $scope.usingCRUD = false;
            if ($scope.model.crudOptions) {
                if (!_.has($scope.model.crudOptions, 'notificationMessage') || !_.has($scope.model.crudOptions, 'saveRecord')) {
                    uitkExceptionService.throwException("CRUDOptionsException", "notificationMessage (message object) and saveRecord (call back function that returns a promise) are required when using crudOptions on viewModel");
                } else {
                    $scope.usingCRUD = true;
                    $scope.model.columnInput = {};
                }
            }

            $scope.componentId = uitkTools.ComponentId($attrs.id, $scope.model, $element, 'Dynamic Table');
            $scope.page = {'pageNumber': 0};

            $scope.preLoadedData = [];
            $scope.model.originalRecords = [];
            $scope.model.selectedRecords = [];
            $scope.model.record = [];
            $scope.model.selectedRecordCount = 0;

            $scope.customOnSortIsDefined = ($scope.model.onSort) ? true : false;
            $scope.customOnSearchIsDefined = ($scope.model.onSearch) ? true : false;
            $scope.customOnPaginateIsDefined = ($scope.model.onPaginate) ? true : false;
            $scope.customOnEditRowIsDefined = ($scope.model.onEditRow) ? true : false;

            if ($scope.model.records.length > 0 && !$scope.model.isExporting) {
                $scope.preLoadedData = _.clone($scope.model.records);
                $scope.model.records = [];
                $scope.model.totalRecordsCount = 0;
            }

            function getSearchConditions() {
                var searchConditions = $scope.model.columns.reduce(function(conditions, column) {
                    if (column.enableSearch === true && column.searchInput) {
                        conditions.searchBy.push(column.columnId);
                        conditions.searchInput.push(column.searchInput);
                    }
                    return conditions;
                }, {searchBy: [], searchInput: []});
                return searchConditions.searchBy.length > 0 ? searchConditions : {};
            }

            function getSortConditions(columnId) {
                if (typeof columnId === "object" || $scope.model.isMultiSortApplied) {
                    return _.cloneDeep($scope.model.multiSortColumns, true);
                }
                else if (columnId) {
                    var sortColumn = _.find($scope.model.columns, {columnId: columnId});
                    var currentSortState = sortColumn.sortOrder;
                    $scope.model.columns.forEach(function(column) {
                        column.sortOrder = 0;
                    });
                    sortColumn.sortOrder = currentSortState === 0 ? 1 : -currentSortState;
                    return {'sortBy': [columnId], 'sortOrder': [sortColumn.sortOrder]};
                }
                else {
                    var sortedColumn = _.find($scope.model.columns, function(column) {
                        return column.sortable === true && column.sortOrder !== 0;
                    });
                    return sortedColumn ? {
                        'sortBy': [sortedColumn.columnId],
                        'sortOrder': [sortedColumn.sortOrder]
                    } : {};
                }
            }
            function getPaginationParams(requestedPageNumber) {
                if (_.isObject($scope.model.pagination)) {
                    $scope.model.pagination.currentPageNumber = requestedPageNumber;
                    $scope.page.pageNumber = requestedPageNumber;
                    return {'pageNumber': requestedPageNumber, 'recordsPerPage': $scope.table.recordsPerPage()};
                }
                else {
                    return {};
                }
            }

            function getQueryForAllConditions(requestedPageNumber, sortColumn) {
                return _.assign(getPaginationParams(requestedPageNumber), getSortConditions(sortColumn), getSearchConditions());
            }


            $scope.model.onChange = $scope.model.onChange || function(filterConditions) {
                uitkEvents.setScope($scope).emit('dynamicTable', $scope.componentId + '-controller-onChange', filterConditions);


                if ($scope.preLoadedData.length > 0) {
                    $scope.model.records = $scope.preLoadedData;
                    $scope.model.totalRecordsCount = $scope.preLoadedData.length;
                }

                if ($scope.updateOriginalRecords === true) {
                    $scope.model.records = _.clone($scope.model.originalRecords);
                    $scope.model.totalRecordsCount = $scope.model.records.length;
                    $scope.updateOriginalRecords = false;
                }


                if ($scope.model.originalRecords.length === 0) {
                    $scope.model.originalRecords = _.clone($scope.model.records);
                }


                if ($scope.customOnSortIsDefined === false) {
                    $scope.table.onSortEvent(filterConditions);
                }
                if ($scope.customOnPaginateIsDefined === false) {
                    $scope.table.onPaginateEvent(filterConditions);
                }
                if ($scope.customOnSearchIsDefined === false) {
                    $scope.table.onSearchEvent(filterConditions);
                }
            };


            $scope.model.onSort = $scope.model.onSort || function(columnId) {
                $scope.model.isMultiSortApplied = false;
                $scope.model.onChange(getQueryForAllConditions(1, columnId));
            };
            $scope.model.fnDownLoad = function(record) {
//                alert(JSON.stringify(record));
                window.open('downloadReportFile.htm?filename=' + record.filePath);

            };

            $scope.model.onRowSelect = $scope.model.onRowSelect || function(event, record, selected) {
                if ($scope.model.enableMultiSelect) {
                    $scope.model.onChange(getQueryForAllConditions($scope.model.pagination.currentPageNumber), $scope.table.onRowSelectEvent(event, record, selected));
                }
            };

            $scope.model.onSelectAllRows = $scope.model.onSelectAllRows || function(selectAll) {
                if ($scope.model.enableMultiSelect) {
                    $scope.model.onChange(getQueryForAllConditions($scope.model.pagination.currentPageNumber), $scope.table.onSelectAllRowsEvent(selectAll));
                }
            };

            $scope.model.onPaginate = $scope.model.onPaginate || function(requestedPageNumber) {
                $scope.page.pageNumberError = false;
                $scope.model.onChange(getQueryForAllConditions(requestedPageNumber));
            };

            $scope.model.onSearch = $scope.model.onSearch || function() {
                if ($scope.model.loadPersistedModel !== true) {
                    $scope.model.onChange(getQueryForAllConditions(1));
                }
            };

            $scope.model.onMultiSort = $scope.model.onMultiSort || function() {
                $scope.model.onChange(getQueryForAllConditions(1, $scope.model.multiSortColumns));
            };

            $scope.model.onLoad = $scope.model.onLoad || function(refresh) {

                if ($scope.model.loadPersistedModel && typeof $scope.model.getPersistedModel == 'function') {
                    loadViewModel();
                }
                else {
                    if (refresh) {
                        $scope.model.onChange(_.assign({refresh: true}, getQueryForAllConditions($scope.model.pagination ? $scope.model.pagination.currentPageNumber : 0)));
                    } else {
                        $scope.model.onChange(getQueryForAllConditions(1));
                    }
                }
                return refresh;
            };

            $scope.model.getPersistingModel = function() {
                var persistingModel = {
                    componentId: $scope.componentId
                };
                var that = this;
                _.forEach(['pagination', 'isMultiSortApplied', 'multiSortColumns'], function(property) {
                    if (!_.isUndefined(that[property])) {
                        persistingModel[property] = _.cloneDeep(that[property]);
                    }
                });
                persistingModel.columns = [];
                _.forEach(that.columns, function(column) {
                    var persistingModelColumn = {};
                    _.forEach(['columnId', 'sortOrder', 'sortable', 'layoutOrder', 'enableSearch', 'searchInput', 'showColumnInTable'], function(property) {
                        if (!_.isUndefined(column[property])) {
                            persistingModelColumn[property] = column[property];
                        }
                    });
                    persistingModel.columns.push(persistingModelColumn);
                });
                return persistingModel;
            }

            function loadViewModel() {
                var persistedViewModel = $scope.model.getPersistedModel($scope.componentId);
                persistedViewModel.$promise.then(function(result) {
                    if (result.pagination) {
                        for (property in result.pagination) {
                            $scope.model.pagination[property] = result.pagination[property];
                        }
                    }
                    if (result.columns) {
                        _.forEach($scope.model.columns, function(column) {
                            var persistedColumn = _.find(result.columns, function(obj) {
                                return obj.columnId === column.columnId;
                            });
                            if (persistedColumn) {
                                _.forEach(['sortOrder', 'sortable', 'layoutOrder', 'enableSearch', 'searchInput', 'showColumnInTable'], function(property) {
                                    column[property] = persistedColumn[property];
                                });
                            }
                        });
                    }

                    $scope.model.isMultiSortApplied = result.isMultiSortApplied;
                    if (result.multiSortColumns) {
                        $scope.model.multiSortColumns = _.cloneDeep(result.multiSortColumns);
                    }
                    $timeout(function() {
                        $scope.model.loadPersistedModel = false;
                        $scope.model.onChange(_.assign({refresh: true}, getQueryForAllConditions($scope.model.pagination ? $scope.model.pagination.currentPageNumber : 0)));
                    });
                });
            }

            $scope.table = {
                onSortEvent: function(filterCondition) {

                    var filteredData = _.clone($scope.model.originalRecords).slice();

                    if (filterCondition.sortBy && filterCondition.sortBy.length > 0) {
                        filterCondition.sortBy.forEach(function(sortByField, index) {
                            var sortOrder = filterCondition.sortOrder[index];
                            var colOption = _.find($scope.model.columns, 'combination');

                            if (typeof colOption !== 'undefined') {
                                var combinedColumnId = colOption.columnId;
                                if (combinedColumnId === sortByField && sortOrder) {
                                    filteredData = _.sortBy(filteredData, function(row) {
                                        return columnCombinations(colOption.combination, row);
                                    });
                                    filteredData = sortOrder === -1 ? filteredData.reverse() : filteredData;
                                } else if (sortByField && sortOrder) {
                                    filteredData = _.sortBy(filteredData, sortByField);
                                    filteredData = sortOrder === -1 ? filteredData.reverse() : filteredData;
                                }
                            }
                            else {
                                if (sortByField && sortOrder) {
                                    filteredData = _.sortBy(filteredData, sortByField);
                                    filteredData = sortOrder === -1 ? filteredData.reverse() : filteredData;
                                }
                            }

                        });

                        $scope.model.totalRecordsCount = filteredData.length;
                        $scope.model.records = filteredData;
                    }
                },
                onSearchEvent: function(filterCondition) {
                    var filteredData = _.clone($scope.model.originalRecords).slice();

                    if (filterCondition.searchBy && filterCondition.searchBy.length > 0) {

                        filterCondition.searchBy.forEach(function(searchByItem, index) {
                            var searchExact = false,
                                    searchType = 'exact',
                                    columnToSearchByType = _.find($scope.model.columns, {searchByItemType: searchType});

                            if (typeof columnToSearchByType != 'undefined') {
                                searchExact = searchByItem.toLowerCase() === columnToSearchByType.columnId && columnToSearchByType.searchByItemType === searchType;
                            }

                            if (searchExact) {
                                filteredData = filteredData.filter(function(record) {
                                    return record[searchByItem].toLowerCase() === filterCondition.searchInput[index].toLowerCase();
                                });
                            } else {
                                filteredData = filteredData.filter(function(record) {
                                    return _.includes(record[searchByItem].toLowerCase(), filterCondition.searchInput[index].toLowerCase());
                                });
                            }
                        });

                        $scope.model.totalRecordsCount = filteredData.length;
                        $scope.model.records = filteredData;
                    }
                },
                onRowSelectEvent: function(event, record, selected) {
                    var model = $scope.model, availableRecords = _.clone($scope.model.originalRecords).slice(), allSelected = true;

                    if (typeof selected === 'undefined') {
                        if (event.target.tagName === 'A' || event.target.tagName === 'INPUT') {
                            return;
                        }
                        record.selected = !record.selected;
                    }

                    var itemAlreadyExists = _.some(model.selectedRecords, function(item) {
                        return item === record;
                    });

                    if (itemAlreadyExists) {
                        model.selectedRecords.splice(record, 1);
                    } else {
                        model.selectedRecords.push(record);
                    }

                    if (availableRecords.length !== model.selectedRecords.length) {
                        allSelected = false;
                    } else {
                        allSelected = true;
                    }
                    model.selectAllChecked = allSelected;
                    model.totalRecordsCount = availableRecords.length;
                    model.records = availableRecords;
                    model.selectedRecordCount = model.selectedRecords.length;
                },
                onSelectAllRowsEvent: function(selectAll) {
                    var model = $scope.model, availableRecords = _.clone($scope.model.originalRecords).slice();
                    model.selectedRecords = [];

                    for (var row = 0; row < availableRecords.length; row++) {
                        if (selectAll) {
                            availableRecords[row].selected = true;
                            model.selectedRecords.push(availableRecords[row]);
                        } else {
                            if (availableRecords[row].selected) {
                                availableRecords[row].selected = false;
                                model.selectedRecords = [];
                            }
                        }
                    }
                    model.totalRecordsCount = availableRecords.length;
                    model.records = availableRecords;
                    model.selectedRecordCount = model.selectedRecords.length;
                },
                onPaginateEvent: function(filterCondition) {

                    var filteredData = _.clone($scope.model.records).slice();
                    if (filterCondition.pageNumber && filterCondition.recordsPerPage) {
                        $scope.model.records = filteredData.splice(filterCondition.pageNumber > 1 ? ((filterCondition.pageNumber - 1) * filterCondition.recordsPerPage) : 0, filterCondition.recordsPerPage);
                    }
                    ;

                },
                showPagination: function() {
                    return $scope.model.pagination;
                },
                recordsPerPage: function() {
                    return $scope.model.pagination ? $scope.model.pagination.recordsPerPage : $scope.model.totalRecordsCount;
                },
                totalPagesCount: function() {
                    var quotient = parseInt($scope.model.totalRecordsCount / $scope.table.recordsPerPage(), 10);
                    var remainder = ((($scope.model.totalRecordsCount === 0 ? 1 : $scope.model.totalRecordsCount) % $scope.table.recordsPerPage()) > 0 ? 1 : 0);
                    return quotient + remainder;
                },
                hasPreviousPage: function() {
                    return $scope.model.pagination.currentPageNumber === 1 ? false : true;
                },
                previousPageNumber: function() {
                    return $scope.model.pagination.currentPageNumber - 1;
                },
                hasNextPage: function() {
                    return this.totalPagesCount() !== $scope.model.pagination.currentPageNumber;
                },
                nextPageNumber: function() {
                    return $scope.model.pagination.currentPageNumber + 1;
                },
                lastPageNumber: function() {
                    return this.totalPagesCount();
                },
                sortOrderDescription: function(sortOrder) {
                    switch (sortOrder) {
                        case -1:
                            return tableSortEnum.DECENDING;
                        case 1:
                            return tableSortEnum.ASCENDING;
                        default:
                            return tableSortEnum.NONE;
                    }
                },
                clearAllFilters: function() {
                    ['pagination', 'columns', 'clearAllFilters'].forEach(function(attr) {
                        if ($scope.model.__init__[attr] && $scope.model.__init__[attr] instanceof Array) {
                            $scope.model.__init__[attr].forEach(function(el, index) {
                                for (var prop in el) {
                                    if (attr === 'columns' && prop !== 'layoutOrder') {
                                        $scope.model[attr][index][prop] = $scope.model.__init__[attr][index][prop];
                                    }
                                }
                            });
                        } else if ($scope.model.__init__[attr] && $scope.model.__init__[attr] instanceof Object) {
                            for (var prop in $scope.model.__init__[attr])
                                $scope.model[attr][prop] = $scope.model.__init__[attr][prop];
                        } else if ($scope.model.__init__[attr]) {
                            $scope.model[attr] = $scope.model.__init__[attr];
                        }
                    });
                    $scope.model.onChange(getQueryForAllConditions(1));
                },
                export: function() {
                    var filterConditions = _.assign(getSortConditions(), getSearchConditions());
                    filterConditions.pageNumber = 1;
                    filterConditions.recordsPerPage = $scope.model.totalRecordsCount;
                    $scope.model.onExport(filterConditions, function(result, fileName) {
                        var model = angular.copy($scope.model);
                        model.pagination = {currentPageNumber: 1, recordsPerPage: $scope.model.totalRecordsCount};
                        model.records = result.records;
                        model.totalRecordsCount = result.totalRecordsCount;
                        _.remove(model.columns, function(currentColumn) {
                            return currentColumn.excludeFromExport;
                        });
                        var content = uitkDynamicTableService.render(model);

                        $timeout(function() {
                            uitkDynamicTableExporter(content, fileName);
                        });
                        return model;
                    });
                },
                exportNestedData: function() {
                    var filterConditions = _.assign(getSortConditions(), getSearchConditions());
                    filterConditions.pageNumber = 1;
                    filterConditions.recordsPerPage = $scope.model.totalRecordsCount;
                    $scope.model.onExportNestedData(filterConditions);
                    return filterConditions;
                }
            };

            if (_.isObject($scope.model.pagination)) {
                $scope.model.pagination.currentPageNumber = $scope.model.pagination.currentPageNumber || 1;
                $scope.model.pagination.paginationWindow = $scope.model.pagination.paginationWindow || 5;
                $scope.model.pagination.recordsPerPage = $scope.model.pagination.recordsPerPage || 10;
                $scope.model.pagination.recordsPerPageChoices = $scope.model.pagination.recordsPerPageChoices || [10, 25, 50, 75, 100];
            }

            $scope.model.records = $scope.model.records || [];

            $scope.model.columns.forEach(function(column) {
                if (column.enableSearch === true) {
                    var columnIdRef = column.columnId;
                    column.searchInput = column.searchInput ? column.searchInput : '';
                    $scope.$watch(function($scope) {
                        var col = _.find($scope.model.columns, {columnId: columnIdRef});
                        if (col) {
                            return col.searchInput;
                        }
                    }, _.after(2, $scope.model.onSearch));
                }
                if (column.sortable === true && !_.includes([1, 0, -1], column.sortOrder)) {
                    column.sortOrder = 0;
                }
            });

            if (!$scope.model.__init__) {
                $scope.model.__init__ = {};
                ['pagination', 'columns', 'clearAllFilters', 'multiSortColumns'].forEach(function(attr) {
                    if ($scope.model[attr]) {
                        $scope.model.__init__[attr] = angular.copy($scope.model[attr]);
                    }
                });

                if ($scope.model.__init__.pagination && $scope.model.__init__.pagination.recordsPerPage) {
                    delete $scope.model.__init__.pagination.recordsPerPage;
                }
            }

            $scope.isRowsSelected = false;
            if ($scope.model) {
                $scope.$watch('model.selectedRecordCount', function(value) {
                    if (value > 0) {
                        $scope.isRowsSelected = true;
                    } else {
                        $scope.isRowsSelected = false;
                    }
                }, true);
            }
        }
        controller.$inject = ["$scope", "tableSortEnum", "uitkEvents", "$attrs", "uitkTools", "$element"];

        function link($scope, $element) {

            if ($element[0].id) {
                $scope.rowsDropdownId = $element[0].id + "_rppOptions";
            }

            if (!_.isEqual($scope.model.columns, _.uniq($scope.model.columns, 'columnId'))) {
                $element.html('Error : DuplicateColumnIdException');
                uitkExceptionService.throwException(
                        'DuplicateColumnIdException',
                        'Duplicate column id names found'
                        );
            }
            var sortedColumnCount = _.filter($scope.model.columns, function(column) {
                return column.sortable === true && column.sortOrder !== 0;
            }).length;
            if (sortedColumnCount > 1) {
                $element.html('Error : MultipleColumnSortConfigurationError');
                uitkExceptionService.throwException(
                        'MultipleColumnSortConfigurationError',
                        'Use multiSortColumns for multi column sort'
                        );
            }

            var allowedDataType = ['character', 'number', 'date', 'text', 'icon', 'checkbox'];
            _.forEach($scope.model.columns, function(column) {
                if (column.dataType && !_.includes(allowedDataType, column.dataType)) {
                    uitkExceptionService.throwException(
                            'DataTypeNotSupportedException',
                            column.dataType + ' is not supported'
                            );
                }
            });

            if ($scope.model.records.length === 0) {
                $scope.model.onLoad(true);
            }

            if ($scope.model.pagination) {
                $scope.pageNumberIsBad = function(pageNumber) {
                    return pageNumber != $scope.model.pagination.currentPageNumber && pageNumber < 0;
                };
                $scope.isPageNumberIsGood = function(pageNumber) {
                    return pageNumber != $scope.model.pagination.currentPageNumber && pageNumber > 0;
                };
                $scope.isPageNumberEqualToCurrent = function(pageNumber) {
                    return pageNumber === $scope.model.pagination.currentPageNumber;
                };

                $scope.page.pageNumber = $scope.model.pagination.currentPageNumber;
                $scope.page.pageNumberDescribedBy = $scope.componentId + '_pageNumber';

            }
            ;


            $scope.showTableOptions = $scope.model.clearAllFilters === true || $scope.model.onExport || $scope.model.onExportNestedData || ($scope.model.links && $scope.model.links.length !== 0);
            $scope.isFiltersClear = $scope.model.clearAllFilters === true;

            $scope.isFiltersApplied = function() {
                var filterApplied = false;
                _.forEach($scope.model.columns, function(column) {
                    if (column.searchInput !== "" && column.searchInput !== undefined) {
                        filterApplied = true;
                        return false;
                    }

                });
                return filterApplied;
            };
            $scope.isColumnSortable = function(column) {
                return column.sortable === true;
            };
            $scope.sortOrderEqualTo = function(column, order) {
                switch (order) {
                    case 1:
                        return column.sortOrder === order;
                        break;
                    case -1:
                        return column.sortOrder === order;
                        break;
                    case 0:
                        return column.sortOrder === order;
                        break;
                    default:
                        return column.sortOrder !== 0;
                        break;
                }
            };

            $scope.isMultiSortColumn = function(column) {
                var returnFlag = false;
                if ($scope.model.isMultiSortApplied) {
                    _.forEach($scope.model.multiSortColumns.sortBy, function(sortByColumn) {
                        if (sortByColumn === column.columnId) {
                            returnFlag = true;
                            return false;
                        }
                    });
                }
                return returnFlag;
            };

            $scope.multiSortOrderEqualTo = function(column, order) {
                var returnFlag = false;
                _.forEach($scope.model.multiSortColumns.sortBy, function(sortByColumn, index) {
                    if (sortByColumn === column.columnId && $scope.model.multiSortColumns.sortOrder[index] === order) {
                        returnFlag = true;
                        return false;
                    }
                });
                return returnFlag;
            };

            $scope.pageNumberEvent = function(event) {
                var key = event.keyCode || event.which;
                var pageNumber;

                if ($scope.page.pageNumber) {
                    pageNumber = parseInt($scope.page.pageNumber, 10);
                }

                if (key == 13 || event.type === "blur") {
                    if (pageNumber === undefined || pageNumber < 1 || pageNumber > $scope.table.totalPagesCount()) {
                        $scope.page.pageNumberError = true;
                        $scope.page.pageNumberDescribedBy = $scope.componentId + '_pageError';
                        uitkLiveRegionService.alertMessage("Enter a valid number, one that is in the page range", true);
                        return;
                    } else {
                        $scope.page.pageNumberError = false;
                        $scope.page.pageNumberDescribedBy = $scope.componentId + '_pageNumber';
                        if (pageNumber != $scope.model.pagination.currentPageNumber) {
                            $scope.model.onPaginate(pageNumber);
                            uitkLiveRegionService.alertMessage("showing page " + $scope.page.pageNumber + "of " + $scope.table.totalPagesCount(), true);
                        }
                    }
                }
            };

            $scope.rowSelectedandEditing = $scope.model.onRowSelect && $scope.customOnEditRowIsDefined || $scope.usingCRUD;


            if ($scope.model.fixedHeader) {
                var $uitk_table, $uitk_table_ths, $uitk_table_lastRow, offsetTop, offsetBottom;

                $(window).scroll(
                        function() {
                            if (!$uitk_table) {
                                $uitk_table = angular.element("#" + $scope.componentId + " .tk-dtbl").first();
                                $uitk_table_ths = angular.element($uitk_table).find("th");
                                $uitk_table_lastRow = angular.element($uitk_table, "tbody > tr:last-child > td:first-child");
                                offsetTop = angular.element($uitk_table).offset().top;
                                offsetBottom = angular.element($uitk_table_lastRow).offset().top + angular.element($uitk_table_lastRow).outerHeight();
                            }

                            var winScroll = $(window).scrollTop();

                            if (winScroll - offsetTop > 0 && winScroll < offsetBottom) {
                                $uitk_table_ths.css({
                                    "position": "relative",
                                    "top": (winScroll - offsetTop - 1),
                                    "background-color": "#f3f3f3",
                                    "z-index": 2
                                });
                            } else {
                                $uitk_table_ths.css("top", 0);
                            }
                        }
                );
            }

            _.each($scope.model.columns, function(column) {
                if (column.showAlways === undefined) {
                    column.showAlways = false;
                }
                if (column.showColumnInTable === undefined) {
                    column.showColumnInTable = true;
                }
                return column;
            });

        }

        return {
            restrict: 'E',
            replace: true,
            transclude: true,
            scope: {
                model: '=',
                viewModel: '='
            },
            controller: controller,
            link: link,
            templateUrl: function(element, attrs) {
                return attrs.templateUrl || 'template/uitk-dynamic-table.html';
            }

        };
    };

    uitkDynamicTableService.$inject = ['$compile', '$rootScope', '$timeout'];
    uitkDynamicTable.$inject = ['$timeout', 'uitkDynamicTableService', 'uitkDynamicTableExporter', '$filter', 'uitkExceptionService', 'columnCombinations', 'uitkLiveRegionService'];

    angular.module('uitk.component.uitkDynamicTable', [
        'uitk.component.uitkNavigable',
        'uitk.component.ngCsv',
        'uitk.uitkUtility', 'uitk.component.uitkRadioGroup', 'uitk.component.uitkSelect'])
            .factory('uitkDynamicTableService', uitkDynamicTableService)
            .directive('uitkDynamicTable', uitkDynamicTable)
            .constant('tableSortEnum', {
                DECENDING: 'descending',
                ASCENDING: 'ascending',
                NONE: 'none'
            });

})();
(function() {
    var uitkDynamicTableColumnDraggable = function($parse, $timeout, uitkEvents) {
        function link($scope, $element, $attrs) {
            var isDraggable = false;
            if ($attrs.uitkDynamicTableColumnDraggable) {
                isDraggable = $parse($attrs.uitkDynamicTableColumnDraggable)($scope) ? true : false;
            }
            function croReleaseDraggingState(event) {
                var diff = new Date() - event.data.time;
                if (diff < 200) {
                    $timeout.cancel(event.data.startDragging);
                } else {
                    releaseDraggingState($(event.data.$th));
                }
            }
            function preventTextSelection() {
                return false;
            }
            function supportsTransitions() {
                var b = document.body || document.documentElement,
                        s = b.style,
                        p = 'transition';

                if (typeof s[p] === 'string') {
                    return true;
                }

                var v = ['Moz', 'webkit', 'Webkit', 'Khtml', 'O', 'Ms'];
                p = p.charAt(0).toUpperCase() + p.substr(1);

                for (var i = 0; i < v.length; i++) {
                    if (typeof s[v[i] + p] === 'string') {
                        return true;
                    }
                }

                return false;
            }

            if (isDraggable) {

                $element.on("mousedown", function(event) {
                    var $that = $(this);
                    $timeout.cancel(startDragging);
                    var time = new Date();
                    var columnModel = _.find($scope.model.columns, {columnId: $(this).attr("aria-label")});

                    var startDragging = $timeout(function() {
                        if (!columnModel.resizeInProgress) {
                            setDraggingState($that, event);
                        }
                    }, 200);

                    $("body").on("mouseup",
                            {
                                $th: $that,
                                time: time,
                                startDragging: startDragging
                            },
                    croReleaseDraggingState);
                });
            }

            var thCoordinatesX = [];
            var thCoordinatesY = [];

            function setInitialState($th) {
                $th.parents("tr").find(".tk-dtbl-cro-dragging").remove();
                $th.parents("tr").find(".tk-dtbl-cro-ghost-overlay").remove();
                $th.parents("tr").find("th").removeClass("tk-dtbl-cro-ghost");
                $("body").off("mouseup", croReleaseDraggingState);
                $("body").removeClass("tk-dtbl-cro-invalid-target");
                $("*").off("selectstart", preventTextSelection)
            }

            function setDraggingState($th, event) {

                var $ghost = $("<table class='tk-dtbl tk-dtbl-cro-dragging tk-dtbl-cro-no-transition'><thead><tr></tr></thead></table>");
                var $content = $th.clone();
                var $siblings = $th.siblings("th");
                $ghost.find("tr").append($content);
                $th.prepend($ghost);
                $th.addClass("tk-dtbl-cro-ghost");
                var offset = $ghost.offset();

                $content.css("height", $th.height());

                $ghost.css("top", 0);
                $ghost.css("left", 0);

                var $overlay = $("<div class='tk-dtbl-cro-ghost-overlay'></div>");
                $overlay.width($th.innerWidth());
                $overlay.height($th.innerHeight());
                $overlay.insertAfter($ghost);

                $("body").addClass("oui-util-non-selectable-text tk-dtbl-cro-invalid-target");

                $ghost.focus();

                var horizontalDelta = event.pageX - offset.left;
                $th.attr("data-cro-cursor-delta", horizontalDelta);

                var verticalDelta = event.pageY - offset.top;
                $th.attr("data-cro-cursor-delta-y", verticalDelta);

                $("body").on("mousemove", {
                    $th: $th
                }, draggingState)

                $ghost.on("transitionend", function() {
                    $ghost.addClass("tk-dtbl-cro-no-transition");
                })

                $("*").on("selectstart", preventTextSelection);

                for (var i = 0; i < $siblings.length; i++) {
                    thCoordinatesX[i] = $siblings.eq(i).offset().left;
                }
                thCoordinatesX[thCoordinatesX.length] = $siblings.last().offset().left + $siblings.last().outerWidth();
                thCoordinatesY[0] = $th.offset().top;
                thCoordinatesY[1] = $th.offset().top + $th.outerHeight();

                if (!supportsTransitions()) {
                    $ghost.trigger("transitionend");
                }
            }

            function releaseDraggingState($th) {

                var $ghost = $th.find(".tk-dtbl-cro-dragging");

                $("body").removeClass("oui-util-non-selectable-text");
                $ghost.removeClass("tk-dtbl-cro-no-transition");

                $("body").off("mousemove", draggingState);


                if ($th.siblings(".tk-dtbl-cro-target-before").length > 0) {
                    var $target = $th.siblings(".tk-dtbl-cro-target-before").first();
                    var newLeft = 0;
                    var leftToRight = ($target.offset().left > $th.offset().left) ? true : false;
                    if (leftToRight) {
                        newLeft = ($target.offset().left - $th.offset().left - $th.outerWidth());
                    } else {
                        newLeft = ($target.offset().left - $th.offset().left);
                    }
                    $ghost.css({
                        "top": 0,
                        "left": newLeft
                    })
                    $ghost.on("transitionend", function() {
                        moveColumn($th, $target, true);
                        setInitialState($th);
                        removeTargets();
                        $("[data-cro-cursor-delta]").removeAttr("data-cro-cursor-delta");
                        $("[data-cro-cursor-delta]").removeAttr("data-cro-cursor-delta-y");
                    });
                } else if ($th.siblings(".tk-dtbl-cro-target-after").length > 0) {
                    $target = $th.siblings(".tk-dtbl-cro-target-after").first();
                    newLeft = 0;
                    leftToRight = ($target.offset().left > $th.offset().left) ? true : false;
                    if (leftToRight) {
                        newLeft = ($target.offset().left - $th.offset().left + $target.outerWidth() - $th.outerWidth());
                    } else {
                        newLeft = ($target.offset().left - $th.offset().left + $target.outerWidth());
                    }
                    $ghost.css({
                        "top": 0,
                        "left": newLeft
                    })
                    $ghost.on("transitionend", function() {
                        moveColumn($th, $target, false);
                        setInitialState($th);
                        removeTargets();
                        $("[data-cro-cursor-delta]").removeAttr("data-cro-cursor-delta");
                    })
                } else {
                    $ghost.css({
                        "left": 0,
                        "top": 0
                    });
                    $ghost.on("transitionend", function() {
                        setInitialState($th);
                        $("[data-cro-cursor-delta]").removeAttr("data-cro-cursor-delta");
                    });

                    if (parseInt($ghost.css("left"), 10) === 0 &&
                            parseInt($ghost.css("top"), 10) === 0) {
                        $ghost.trigger("transitionend");
                    }
                }

                if (!supportsTransitions()) {
                    $ghost.trigger("transitionend");
                }
            }

            function draggingState(event) {
                var $th = event.data.$th;

                var $ghost = $th.find(".tk-dtbl-cro-dragging");
                var newLeft = event.pageX - $th.offset().left - $th.attr("data-cro-cursor-delta");
                var newTop = event.pageY - $th.offset().top - $th.attr("data-cro-cursor-delta-y");
                $ghost.css("left", newLeft);
                $ghost.css("top", newTop);

                var x = event.pageX;
                var y = event.pageY;
                if ((x > thCoordinatesX[0]) &&
                        (x < thCoordinatesX[thCoordinatesX.length - 1]) &&
                        (y > thCoordinatesY[0]) &&
                        (y < thCoordinatesY[thCoordinatesY.length - 1])) {
                    var i = 0;
                    while (x > thCoordinatesX[i]) {
                        i++;
                    }
                    --i;
                    potentialTargetEntry($th.siblings("th").eq(i), $th, x);
                } else {
                    removeTargets();
                }
            }
            function potentialTargetEntry($th, $og, x) {

                var midpoint = .5 * $th.innerWidth();
                midpoint += $th.offset().left;
                var before = (x < midpoint) ? true : false;

                if ((before && $th.prev("th.tk-dtbl-cro-ghost").length > 0) ||
                        (!before && $th.next("th.tk-dtbl-cro-ghost").length > 0)) {
                    removeTargets();
                    return;
                }

                var classToAdd = (before) ? "tk-dtbl-cro-target-before" : "tk-dtbl-cro-target-after";
                if ((before && $og.hasClass("tk-dtbl-cro-target-before")) ||
                        (!before && $og.hasClass("tk-dtbl-cro-target-after"))) {
                    return;
                }
                removeTargets();
                $th.addClass(classToAdd);
                if ($th.find(".tk-dtbl-cro-target").length < 1) {
                    var $target = $("<div class='tk-dtbl-cro-target'></div>");
                    $target.height($th.innerHeight());
                    $th.prepend($target);
                }
            }

            function removeTargets() {
                $(".tk-dtbl-cro-target-after, .tk-dtbl-cro-target-before").removeClass("tk-dtbl-cro-target-after tk-dtbl-cro-target-before");
                $(".tk-dtbl-cro-target").remove();
            }

            function moveColumn($th, $target, before) {

                var draggedColumn = _.find($scope.model.columns, {columnId: $($th).attr("aria-label")});
                var dropColumn = _.find($scope.model.columns, {columnId: $($target).attr("aria-label")});
                var dropColumnLayoutOrder = dropColumn.layoutOrder;
                var componentId = $scope.$parent.componentId;

                $scope.$apply(function() {
                    if (dropColumnLayoutOrder >= 1 && draggedColumn.layoutOrder > dropColumn.layoutOrder && !before) {
                        dropColumnLayoutOrder++;
                    } else if (dropColumnLayoutOrder <= $scope.model.columns.length && draggedColumn.layoutOrder < dropColumn.layoutOrder && before) {
                        dropColumnLayoutOrder--;
                    }

                    if (draggedColumn.layoutOrder > dropColumn.layoutOrder) {
                        $scope.model.columns.forEach(function(column) {
                            if (column.layoutOrder >= dropColumnLayoutOrder && column.layoutOrder < draggedColumn.layoutOrder) {
                                column.layoutOrder++;
                            }
                        });
                    } else if (draggedColumn.layoutOrder < dropColumn.layoutOrder) {
                        $scope.model.columns.forEach(function(column) {
                            if (column.layoutOrder <= dropColumnLayoutOrder && column.layoutOrder > draggedColumn.layoutOrder) {
                                column.layoutOrder--;
                            }
                        });
                    }

                    draggedColumn.layoutOrder = dropColumnLayoutOrder;
                });

                uitkEvents.setScope().internalBroadcast("broadcast", componentId + "-dragColumns");
            }
            function guh() {
                $("*").removeAttr("ng-class ng-repeat ng-attr-style");
            }


        }

        return {
            restrict: 'A',
            replace: false,
            scope: false,
            link: link
        };
    };

    uitkDynamicTableColumnDraggable.$inject = ['$parse', '$timeout', 'uitkEvents'];

    angular.module('uitk.component.uitkDynamicTable')
            .directive('uitkDynamicTableColumnDraggable', uitkDynamicTableColumnDraggable);

})();

(function() {

    var uitkCompileLabel = function($compile) {
        return function($scope, $element) {
            $compile($scope.column.label)($scope, function(clone) {
                if (!clone.selector) {
                    $element.append(clone);
                } else {
                    $element.append(clone.selector);
                }
            });
        };
    };

    var uitkCompileCellTemplate = function($compile) {
        var compiledTemplate = _.memoize(function(value) {
            return $compile(value);
        });

        return function($scope, $element, $attrs) {
            if (angular.isDefined($scope.link)) {
                if (typeof $scope.link === 'string') {
                    $compile($scope.link)($scope, function(clone) {
                        $element.append(clone);
                    });
                } else {
                    var cur = '<a tabindex=0 href="' + $scope.link.href
                            + '" title="' + $scope.link.title
                            + '" ng-click="' + $scope.link.click + '">'
                            + $scope.link.text + '</a>';
                    var comp = $compile(cur)($scope);
                    $element.append(comp);
                }
            }
            else if (angular.isUndefined($scope.currentRecord)) {
                compiledTemplate($scope.column[$attrs.uitkCompileCellTemplate])($scope, function(clone) {
                    $element.append(clone);
                });
            }
            else {
                $scope.record = $scope.currentRecord;
                compiledTemplate($scope.model.rowTemplate)($scope, function(clone) {
                    $element.append(clone);
                });
            }
        };
    };

    var uitkResizableColumn = function($document, $timeout) {
        function link($scope, $element) {
            var column = $element.parent();
            var table = column.parent().parent().parent();
            var padding = 20 + 2;
            var startX = 0, tableWidth = 0;
            var min_width = 0;
            var columnModel = _.find($scope.model.columns, {columnId: $scope.column.columnId});
            columnModel.resizeInProgress = false;
            columnModel.setWidth = function(width) {
                column.css({'width': width + 'px'});
            };
            columnModel.getWidth = function() {
                return parseInt(column.css('width'), 10);
            };
            columnModel.isWidthModifiable = function(width) {
                return width > min_width;
            };

            $timeout(function() {
                if ($.isFunction(column.innerWidth)) {
                    column.css({'width': (column.innerWidth()) + 'px'});
                }

                min_width = (column.prop('offsetWidth') - padding) / 2;
            });

            $element.on('mousedown', function(event) {
                event.preventDefault();
                startX = event.screenX;
                column = $element.parent();
                table = column.parent().parent().parent();
                min_width = (column.prop('offsetWidth') - padding) / 2;
                tableWidth = table.prop('offsetWidth');
                $document.on('mousemove', mousemove);
                $document.on('mouseup', mouseup);
                $scope.$apply(function() {
                    columnModel.resizeInProgress = true;
                });

            });

            function mousemove(event) {
                var cursorChangedBy = (event.screenX - startX);
                var width = parseInt(column.css('width'), 10) + cursorChangedBy;
                var nextColumnModel = _.chain($scope.model.columns).filter(function(col) {
                    return col.layoutOrder > columnModel.layoutOrder;
                }).min('layoutOrder').value();
                if (width > min_width && nextColumnModel.isWidthModifiable(nextColumnModel.getWidth() - cursorChangedBy)) {
                    nextColumnModel.setWidth(nextColumnModel.getWidth() - cursorChangedBy);
                    columnModel.setWidth(width);
                    table.css({'width': tableWidth + 'px'});
                    startX = event.screenX;
                }
            }

            function mouseup() {
                $scope.$apply(function() {
                    columnModel.resizeInProgress = false;
                });
                $document.off('mousemove', mousemove);
                $document.off('mouseup', mouseup);
            }
        }

        return {
            scope: false,
            link: link
        };
    };

    var uitkSelectable = function($parse) {

        function link($scope, $element, $attrs) {
            var isSelectable = true;
            if ($attrs.uitkSelectable) {
                isSelectable = $parse($attrs.uitkSelectable)($scope) ? true : false;
            }
            if (isSelectable) {
                $element.attr('tabindex', 0);
                if ($element.attr('ng-click')) {
                    var fn = $parse($element.attr('ng-click'));
                    $element.on('keydown', keydownHandler(fn));
                    $element.on('keyup', keyupHandler(fn));
                }
            }
        }
        ;

        function keydownHandler(event, fn) {
            if (event.which === 13 || event.which === 32) {
                $scope.$apply(function() {
                    fn($scope, {$event: event});
                });
            }
            if (event.which === 32) {
                event.preventDefault();
            }
        }
        ;

        function keyupHandler(event, fn) {
            if (event.which === 32) {
                $scope.$apply(function() {
                    fn($scope, {$event: event});
                });
                event.preventDefault();
            }
        }
        ;

        return {
            restrict: 'A',
            replace: false,
            scope: false,
            link: link
        };
    };


    var uitkDrawerContent = function(drawerSlide, $filter) {

        function link($scope, $element) {

            var rowUniqueId = $scope.componentId + '_' + ((typeof $scope.rowRecord !== 'undefined') ? $scope.rowRecord.index : '');

            $scope.model.recordOperationInProgress = false;

            $scope.$on(rowUniqueId + "-openDrawer", function(e, v) {
                drawerSlide($element);
            });

            $scope.$on(rowUniqueId + "-closeDrawer", function(e, v) {
                drawerSlide($element, true);
            });


        }
        ;

        return {
            restrict: 'A',
            replace: true,
            scope: {
                model: "=",
                componentId: "=",
                isEditing: "=",
                rowRecord: '='
            },
            templateUrl: "template/openDrawerContent.html",
            link: link
        };
    };


    var uitkDrawerAction = function(drawerSlide, uitkEvents, $log, columnCombinations) {
        function link($scope, $element) {

            var rowUniqueId = $scope.componentId + '_' + ((typeof $scope.rowRecord !== 'undefined') ? $scope.rowRecord.index : '');
            $scope.$on(rowUniqueId + "-openDrawer", function(e, v) {
                drawerSlide($element);
            });

            $scope.$on(rowUniqueId + "-closeDrawer", function(e, v) {
                drawerSlide($element, true);
            });

            $scope.model.recordOperationInProgress = false;

            var resetColumnInputError = function() {
                $scope.model.columnInput.error = {};
            };

            var resetColumns = function(record) {
                angular.forEach($scope.model.columns, function(e, i) {
                    if (_.isObject(record)) {
                        if (e.combination) {
                            if (e.combination.length > 0) {
                                $scope.model.columnInput[e.columnId] = columnCombinations(e.combination, record);
                            }
                        } else {
                            $scope.model.columnInput[e.columnId] = record[e.columnId];
                        }
                        ;
                    }
                    else {
                        $scope.model.columnInput[e.columnId] = "";
                    }

                });
                resetColumnInputError();
            };

            var saveAndUpdateRecord = function($scope, actualRecord) {
                var _model = $scope.model;

                var val = function(e) {
                    var validate = (e.validationPattern);
                    if (typeof validate !== 'undefined') {
                        validate = validate.test(_model.columnInput[e.columnId].toString());
                    } else {
                        validate = true;
                    }
                    return validate;
                };

                angular.forEach(_model.columns, function(e, i) {
                    delete _model.columnInput.error[e.columnId];
                    if (typeof e.validationPattern !== 'undefined' && _.isEmpty(_model.columnInput[e.columnId].toString()) || (_model.columnInput[e.columnId] && !val(e))) {
                        _model.columnInput.error[e.columnId] = true;
                    }
                });

                if (_.isEmpty(_model.columnInput.error)) {
                    if (!_model.crudOptions.saveRecord) {
                        $log.warn("saveRecord method not provided on viewModel. (Note:saveRecord should return a promise.)");
                        return null;
                    }
                    var prommiseToSave = _model.crudOptions.saveRecord(_model.columnInput);
                    if (typeof prommiseToSave.then !== 'function') {
                        $log.warn("No promise was provided therefore no external saving");
                        return null;
                    }

                    prommiseToSave.then(function(recordInfo) {
                        if (recordInfo.record === null) {
                            if (_model.crudOptions.notificationMessage) {
                                _model.crudOptions.notificationMessage.content = "<span>" + recordInfo.message + "</span>";
                                _model.crudOptions.notificationMessage.messageType = "error";
                                _model.crudOptions.notificationMessage.visible = true;
                            }
                            return null;
                        }

                        if (_model.crudOptions.notificationMessage) {
                            _model.crudOptions.notificationMessage.content = "<span>" + recordInfo.message + "</span>";
                            _model.crudOptions.notificationMessage.visible = true;
                        }

                        resetColumns();

                        uitkEvents.setScope().internalBroadcast("broadcast", _model.recordUniqueId + "-closeDrawer", _model.recordUniqueId);

                        if (typeof (actualRecord) === 'undefined') {
                            _model.originalRecords.push(recordInfo.record);
                        }
                        else {
                            var foundIdx = _.findIndex(_model.originalRecords, actualRecord);

                            if (foundIdx > -1) {
                                _model.originalRecords[foundIdx] = recordInfo.record;
                            }
                            else {
                                _model.originalRecords.push(recordInfo.record);
                            }
                        }
                        $scope.updateOriginalRecords = true;
                        _model.onLoad(true);
                        _model.recordOperationInProgress = false;
                    }, function(reason) {
                    });
                }
                return true;
            };

            var deleteRecord = function($scope, actualRecord) {
                var _model = $scope.model;

                _model.recordOperationInProgress = true;

                var foundIdx = _.findIndex(_model.originalRecords, actualRecord);

                if (foundIdx > -1) {
                    var removedArray = _.pullAt(_model.originalRecords, foundIdx);
                    if (!_model.crudOptions.deleteRecord) {
                        $log.warn("deleteRecord method not provided on viewModel. (Note:deleteRecord should return a promise.)");
                        return null;
                    }
                    var prommiseToDelete = _model.crudOptions.deleteRecord(actualRecord);
                    if (typeof prommiseToDelete.then !== 'function') {
                        $log.warn("No promise was provided therefore no external saving");
                        return null;
                    }

                    prommiseToDelete.then(function(recordInfo) {
                        if (recordInfo.record === null) {
                            if (_model.crudOptions.notificationMessage) {
                                _model.crudOptions.notificationMessage.content = "<span>" + recordInfo.message + "</span>";
                                _model.crudOptions.notificationMessage.messageType = "error";
                                _model.crudOptions.notificationMessage.visible = true;
                            }
                            return null;
                        }

                        if (_model.crudOptions.notificationMessage) {
                            _model.crudOptions.notificationMessage.content = "<span>" + recordInfo.message + "</span>";
                            _model.crudOptions.notificationMessage.visible = true;
                        }
                    }, function(reason) {
                    });

                    _model.modalShown = false;

                    _model.onLoad(true);
                    _model.recordOperationInProgress = false;

                }
            };

            $scope.model.openDrawer = $scope.model.openDrawer || function(record) {
                resetColumns();
                if ($scope.model.recordOperationInProgress) {
                    $scope.model.showErrorMessage();
                    return;
                }

                var recordUniqueId = rowUniqueId;
                if (typeof record !== 'undefined') {
                    recordUniqueId = $scope.componentId + '_' + record.index;
                }
                $scope.model.recordUniqueId = recordUniqueId;

                uitkEvents.setScope().internalBroadcast("broadcast", recordUniqueId + "-openDrawer", recordUniqueId);

                resetColumnInputError();
                if (_.isObject(record)) {
                    resetColumns(record);

                    record.hideRecord = true;

                    $scope.isEditing = true;
                }
                else {
                    $scope.isEditing = false;
                }

                $scope.model.recordOperationInProgress = true;
                return true;
            };

            $scope.model.modifyRow = function(isEditing, isCancelling, record, event) {
                if (isCancelling) {
                    if (isEditing) {
                        return $scope.model.onEditRowCancel(record, event);
                    } else {
                        return $scope.model.onAddRowCancel();
                    }
                }
                else {
                    if (isEditing) {
                        return $scope.model.onEditRow(record, event);
                    } else {
                        return $scope.model.onAddRow();
                    }
                }
            };

            $scope.model.onEditRow = $scope.model.onEditRow || function(record) {
                saveAndUpdateRecord($scope, record);
                record.hideRecord = false;
                return true;
            };

            $scope.model.onAddRow = $scope.model.onAddRow || function() {
                return saveAndUpdateRecord($scope);
            };

            $scope.model.onAddRowCancel = $scope.model.onAddRowCancel || function() {
                resetColumns();
                uitkEvents.setScope().internalBroadcast("broadcast", $scope.model.recordUniqueId + "-closeDrawer", $scope.model.recordUniqueId);
                $scope.model.recordOperationInProgress = false;
                return true;
            };

            $scope.model.onEditRowCancel = $scope.model.onEditRowCancel || function(record) {
                resetColumns(record);
                uitkEvents.setScope().internalBroadcast("broadcast", $scope.model.recordUniqueId + "-closeDrawer", $scope.model.recordUniqueId);
                $scope.model.recordOperationInProgress = false;
                record.hideRecord = false;
                return true;
            };

            $scope.model.onDelete = $scope.model.onDelete || function(record) {
                if ($scope.model.recordOperationInProgress) {
                    $scope.model.showErrorMessage();
                    return;
                }

                $scope.model.modalShown = true;
                if ($scope.model.selectedRecords.length == 0 && _.isObject(record)) {
                    $scope.model.record = record;
                }
                return true;
            };

            $scope.model.onDeleteConfirm = function() {

                if ($scope.model.selectedRecords.length > 0) {
                    angular.forEach($scope.model.selectedRecords, function(key, value) {
                        deleteRecord($scope, key);
                    });
                    $scope.model.selectedRecords = [];
                }
                else {
                    if (_.isObject($scope.model.record)) {
                        deleteRecord($scope, $scope.model.record);
                    }
                    else {
                        $scope.model.modalShown = false;
                    }
                }
                return true;
            }

            $scope.model.onDeleteCancel = function() {
                $scope.model.modalShown = false;
                return true;
            };
        }
        ;

        return {
            restrict: 'A',
            replace: true,
            scope: {
                model: "=",
                componentId: "=",
                isEditing: "=",
                rowRecord: '='
            },
            templateUrl: "template/openDrawerAction.html",
            link: link
        };
    };

    var uitkMultiSortDrawerContent = function(drawerSlide) {

        function link($scope, $element) {

            $scope.dropDownItems = [];

            $scope.textRadioItems = [{
                    label: 'A to Z',
                    value: 'ascending'
                }, {
                    label: 'Z to A',
                    value: 'descending'
                }];

            $scope.numberRadioItems = [{
                    label: '0 to 9',
                    value: 'ascending'
                }, {
                    label: '9 to 0',
                    value: 'descending'
                }];

            $scope.dateRadioItems = [{
                    label: 'Newest to Oldest',
                    value: 'ascending'
                }, {
                    label: 'Oldest to Newest',
                    value: 'descending'
                }];

            $scope.addAnotherColumn = function() {
                var multiSortColumn = {
                    sortOrder: 0
                };
                $scope.model.multiSortColumnsInDrawer ? $scope.model.multiSortColumnsInDrawer.push(multiSortColumn) : $scope.model.multiSortColumnsInDrawer = [multiSortColumn];
            };

            $scope.removeColumn = function(index) {
                $scope.model.multiSortColumnsInDrawer.splice(index, 1);
            };

            var componentId = $scope.componentId;

            $scope.$on(componentId + "-openMultiSortDrawer", function(e, v) {
                $scope.initMultiSortDrawer();
                drawerSlide($element);
            });

            $scope.$on(componentId + "-closeMultiSortDrawer", function(e, v) {
                drawerSlide($element, true);
            });

            $scope.restoreDefault = function() {

                $scope.model.multiSortColumnsInDrawer = [];
                if ($scope.model.__init__.multiSortColumns && $scope.model.__init__.multiSortColumns.sortBy) {
                    _.forEach($scope.model.__init__.multiSortColumns.sortBy, function(obj, index) {
                        var selectedIndex = _.findIndex($scope.dropDownItems, function(object) {
                            return object.value == obj;
                        });
                        $scope.model.multiSortColumnsInDrawer.push({
                            selectedColumn: $scope.dropDownItems[selectedIndex],
                            sortOrder: $scope.model.__init__.multiSortColumns.sortOrder[index] === 1 ? 0 : 1
                        });
                    });
                }

                if ($scope.model.multiSortColumnsInDrawer.length === 0) {
                    var newColumn = {
                        selectedColumn: $scope.dropDownItems[0],
                        sortOrder: 0
                    };
                    $scope.model.multiSortColumnsInDrawer = [newColumn];
                }
            };

            $scope.initMultiSortDrawer = function() {

                $scope.dropDownItems = [];
                _.each(_.sortBy($scope.model.columns, 'layoutOrder'), function(column) {
                    if (column.sortable == false && column.showColumnInTable == true) {
                        $scope.dropDownItems.push({'label': column.label + " (not sortable)", value: column.columnId, isDisabled: true, dataType: column.dataType});
                    }
                    else if (column.showColumnInTable == true) {
                        $scope.dropDownItems.push({'label': column.label, value: column.columnId, dataType: column.dataType});
                    }

                });

                $scope.model.multiSortColumnsInDrawer = [];
                if ($scope.model.multiSortColumns && $scope.model.multiSortColumns.sortBy) {
                    _.forEach($scope.model.multiSortColumns.sortBy, function(obj, index) {
                        var selectedIndex = _.findIndex($scope.dropDownItems, function(object) {
                            return object.value == obj;
                        });
                        $scope.model.multiSortColumnsInDrawer.push({
                            selectedColumn: $scope.dropDownItems[selectedIndex],
                            sortOrder: $scope.model.multiSortColumns.sortOrder[index] === 1 ? 0 : 1
                        });
                    });
                }

                if ($scope.model.multiSortColumnsInDrawer.length === 0) {
                    var newColumn = {
                        selectedColumn: $scope.dropDownItems[0],
                        sortOrder: 0
                    };
                    $scope.model.multiSortColumnsInDrawer = [newColumn];
                }
            };

        }
        ;

        return {
            restrict: 'A',
            replace: true,
            scope: {
                model: "=",
                componentId: "="
            },
            templateUrl: "template/multiSortDrawerContent.html",
            link: link
        };
    };

    var uitkMultiSortDrawerAction = function(drawerSlide, uitkEvents) {

        function link($scope, $element) {

            var componentId = $scope.componentId;

            $scope.$on(componentId + "-openMultiSortDrawer", function(e, v) {
                drawerSlide($element);
            });

            $scope.$on(componentId + "-closeMultiSortDrawer", function(e, v) {
                drawerSlide($element, true);
            });

            $scope.model.openMultiSortDrawer = $scope.model.openMultiSortDrawer || function(event) {

                if ($scope.model.recordOperationInProgress) {
                    if (typeof $scope.model.showErrorMessage == 'function') {
                        $scope.model.showErrorMessage();
                    }
                    return;
                }
                uitkEvents.setScope().internalBroadcast("broadcast", componentId + "-openMultiSortDrawer");

                $scope.model.recordOperationInProgress = true;
                return true;
            };

            $scope.model.closeMultiSortDrawer = $scope.model.closeMultiSortDrawer || function(event) {
                uitkEvents.setScope().internalBroadcast("broadcast", componentId + "-closeMultiSortDrawer");

                $scope.model.recordOperationInProgress = false;
                return true;
            };

            $scope.model.saveMultiSortColumns = $scope.model.saveMultiSortColumns || function() {

                var sortBy = [];
                var sortOrder = [];
                _.forEach($scope.model.multiSortColumnsInDrawer, function(obj) {
                    sortBy.push(obj.selectedColumn.value);
                    sortOrder.push(obj.sortOrder === 0 ? 1 : -1);
                });
                $scope.model.multiSortColumns = {sortBy: sortBy, sortOrder: sortOrder};
                $scope.model.multiSortColumnsInDrawer = {};


                if (!$scope.model.isMultiSortApplied) {
                    $scope.model.isMultiSortApplied = true;
                    _.forEach($scope.model.columns, function(column) {
                        if (column.sortOrder === -1 || column.sortOrder === 1) {
                            column.sortOrder = 0;
                        }
                    });
                }
                $scope.model.onMultiSort();

                if (typeof $scope.model.showSuccessMessage == 'function') {
                    $scope.model.showSuccessMessage('Your column sort preference were successfully saved.');
                }
                $scope.model.closeMultiSortDrawer();
            };

        }
        ;

        return {
            restrict: 'A',
            replace: true,
            scope: {
                model: "=",
                componentId: "="
            },
            templateUrl: "template/multiSortDrawerAction.html",
            link: link
        };
    };

    var uitkShowHideColumnsDrawerContent = function(drawerSlide, $filter) {

        function link($scope, $element) {
            var componentId = $scope.componentId;

            $scope.$on(componentId + "-openShowHideColumnsDrawer", function(e, v) {
                drawerSlide($element);
            });

            $scope.$on(componentId + "-closeShowHideColumnsDrawer", function(e, v) {
                drawerSlide($element, true);
            });

            $scope.$on(componentId + "-dragColumns", function(e, v) {
                $scope.model.showHideFilterColumns = angular.copy($scope.model.columns);
            });

            var orderBy = $filter('orderBy');
            $scope.order = function(predicate) {
                $scope.predicate = predicate;
                $scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
                $scope.columnsList = orderBy($scope.columnsList, predicate, $scope.reverse);
            };

            $scope.model.showHideFilterColumns = angular.copy($scope.model.columns);


            var resetFilterColumns = angular.copy($scope.model.columns);

            $scope.restoreDefault = function() {
                $scope.model.columns = angular.copy(resetFilterColumns);
            }

        }
        ;

        return {
            restrict: 'A',
            replace: true,
            scope: {
                model: "=",
                componentId: "=",
                isEditing: "="
            },
            templateUrl: "template/openColumnsDrawerContent.html",
            link: link
        };
    };

    var uitkShowHideColumnsDrawerAction = function(drawerSlide, uitkEvents) {

        function link($scope, $element) {
            var componentId = $scope.componentId;

            $scope.$on(componentId + "-openShowHideColumnsDrawer", function(e, v) {
                drawerSlide($element);
            });

            $scope.$on(componentId + "-closeShowHideColumnsDrawer", function(e, v) {
                drawerSlide($element, true);
            });

            $scope.model.openShowHideColumnsDrawer = $scope.model.openShowHideColumnsDrawer || function(record) {

                angular.element('.tk-dtbl-reorderable-columns').css('width', 'auto');
                angular.element('.tk-dtbl-reorderable-columns > tr > th').css('width', 'auto');

                uitkEvents.setScope().internalBroadcast("broadcast", componentId + "-openShowHideColumnsDrawer");

                $scope.model.recordOperationInProgress = true;
                return true;
            };

            $scope.model.showHideColumnDrawerCancel = $scope.model.showHideColumnDrawerCancel || function() {
                uitkEvents.setScope().internalBroadcast("broadcast", componentId + "-closeShowHideColumnsDrawer");
                $scope.model.recordOperationInProgress = false;
                return true;
            };

            $scope.model.modifyColumn = $scope.model.modifyColumn || function(isCancelling, event) {

                if (isCancelling) {
                    $scope.model.columns = angular.copy($scope.model.showHideFilterColumns);

                }
                else {
                    $scope.model.showHideFilterColumns = angular.copy($scope.model.columns);
                    if (typeof $scope.model.showSuccessMessage == 'function') {
                        $scope.model.showSuccessMessage('Your column preferences were successfully changed. Drag columns left or right to change the order displayed.');
                    }
                    uitkEvents.setScope().internalBroadcast("broadcast", componentId + "-showHideColumns");
                }
                return $scope.model.showHideColumnDrawerCancel();
            };

        }
        ;

        return {
            restrict: 'A',
            replace: true,
            scope: {
                model: "=",
                componentId: "=",
                isEditing: "="
            },
            templateUrl: "template/openColumnsDrawerAction.html",
            link: link
        };
    };



    var drawerSlide = function() {
        return function($element, slidingUp) {
            var ele = angular.element("td > div", $element);
            if (slidingUp) {
                angular.element(ele).slideUp(400, "linear", function() {
                    angular.element(ele).slideUp(400, "linear", function() {
                        angular.element("a:first", ele).focus();
                    });
                });
            } else {
                angular.element(ele).slideDown(400, "linear", function() {
                    angular.element(ele).slideDown(400, "linear", function() {
                        angular.element("input:first", ele).focus();
                    });
                });
            }

        }
    };

    var displayColumnFilter = function() {
        return function(columns) {
            var filtered = [];
            _.each(columns, function(column) {
                if (column.showColumnInTable) {
                    filtered.push(column);
                }
            });
            return filtered;
        };
    }


    var columnCombinations = function() {
        return function(list, record) {

            var result = "";
            angular.forEach(list, function(value, index) {
                var pat = /\{(.*)\}/i
                var newVal = value.match(pat);
                if (newVal.length > 0) {
                    var recordPiece = value.replace(newVal[0], record[newVal[1]]);
                    result += recordPiece;
                }
            });
            return result;
        }
    };

    uitkMultiSortDrawerContent.$inject = ["drawerSlide"];
    uitkMultiSortDrawerAction.$inject = ["drawerSlide", "uitkEvents"];

    uitkShowHideColumnsDrawerContent.$inject = ["drawerSlide", "$filter"];
    uitkShowHideColumnsDrawerAction.$inject = ["drawerSlide", "uitkEvents"];

    uitkDrawerAction.$inject = ["drawerSlide", "uitkEvents", "$log", "columnCombinations"];
    uitkDrawerContent.$inject = ["drawerSlide", "$filter"];

    uitkCompileLabel.$inject = ['$compile'];
    uitkCompileCellTemplate.$inject = ['$compile'];

    uitkResizableColumn.$inject = ['$document', '$timeout'];
    uitkSelectable.$inject = ['$parse'];

    angular.module('uitk.component.uitkDynamicTable')
            .filter("displayColumnFilter", displayColumnFilter)
            .directive('uitkCompileLabel', uitkCompileLabel)
            .directive('uitkCompileCellTemplate', uitkCompileCellTemplate)
            .directive('uitkResizableColumn', uitkResizableColumn)
            .directive('uitkSelectable', uitkSelectable)
            .directive('uitkDrawerContent', uitkDrawerContent)
            .directive('uitkDrawerAction', uitkDrawerAction)
            .directive('uitkMultiSortDrawerContent', uitkMultiSortDrawerContent)
            .directive('uitkMultiSortDrawerAction', uitkMultiSortDrawerAction)
            .directive('uitkShowHideColumnsDrawerContent', uitkShowHideColumnsDrawerContent)
            .directive('uitkShowHideColumnsDrawerAction', uitkShowHideColumnsDrawerAction).factory('drawerSlide', drawerSlide).factory('columnCombinations', columnCombinations);

    angular.module('uitk.component.uitkDynamicTableDirective', []);
})();

(function() {

    var uitkDynamicTableRowDraggable = function($parse, $timeout, uitkEvents) {

        function link($scope, $element, $attrs) {
            var isDraggable = false;
            if ($attrs.uitkDynamicTableRowDraggable) {
                isDraggable = $parse($attrs.uitkDynamicTableRowDraggable)($scope) ? true : false;
            }

            function croReleaseDraggingState(event) {
                var diff = new Date() - event.data.time;
                if (diff < 200) {
                    $timeout.cancel(event.data.startDragging);
                } else {
                    releaseDraggingState(event, $(event.data.$tr), $(event.data.$originalTr));
                }
            }

            function supportsTransitions() {
                var b = document.body || document.documentElement,
                        s = b.style,
                        p = 'transition';

                if (typeof s[p] === 'string') {
                    return true;
                }

                var v = ['Moz', 'webkit', 'Webkit', 'Khtml', 'O', 'Ms'];
                p = p.charAt(0).toUpperCase() + p.substr(1);

                for (var i = 0; i < v.length; i++) {
                    if (typeof s[v[i] + p] === 'string') {
                        return true;
                    }
                }

                return false;
            }

            if (isDraggable) {
                $element.on("mousedown", function(event) {
                    if ($(event.target).is("a")) {
                        return;
                    }
                    var $that = $(this);
                    $timeout.cancel(startDragging);
                    var time = new Date();

                    var startDragging = $timeout(function() {
                        $scope.offsetTop = $that.offset().top - $that.parent().parent().offset().top;
                        setDraggingState($that, event);
                    }, 200);

                    $("body").on("mouseup",
                            function() {
                                var diff = new Date() - time;
                                if (diff < 200) {
                                    $timeout.cancel(startDragging);
                                }
                            });
                });
            }

            var trCoordinatesX = [];
            var trCoordinatesY = [];

            function setDraggingState($tr, event) {
                var $ghost = $tr.clone(true);
                var $siblings = $tr.parent().children();
                var $originals = $tr.children();
                $ghost.children().each(function(index)
                {
                    $(this).width($originals.eq(index).width());
                    return $ghost;
                });
                $ghost.addClass("tk-row-drag");
                $($ghost).insertBefore($tr);
                $("body").on("mouseup",
                        {
                            $tr: $ghost,
                            $originalTr: $tr
                        },
                croReleaseDraggingState);

                $tr.addClass("tk-dtbl-cro-ghost");
                var offset = $ghost.offset();

                $ghost.css("height", $tr.height());

                $ghost.css("top", event.pageY - offset.top);
                $ghost.css("left", 0);

                $("body").addClass("oui-util-non-selectable-text tk-dtbl-cro-invalid-target");

                $ghost.focus();

                var horizontalDelta = event.pageX - offset.left;
                $ghost.attr("data-cro-cursor-delta", horizontalDelta);

                var verticalDelta = event.pageY - offset.top;
                $ghost.attr("data-cro-cursor-delta-y", verticalDelta);

                $("body").on("mousemove", {
                    $tr: $ghost
                }, draggingState)

                $ghost.on("transitionend", function() {
                    $ghost.addClass("tk-dtbl-cro-no-transition");
                })

                $("*").on("selectstart", function() {
                    return false;
                });

                for (var i = 0; i < $siblings.length; i++) {
                    trCoordinatesY[i] = $siblings.eq(i).offset().top;
                }
                trCoordinatesY[trCoordinatesY.length] = $siblings.last().offset().top + $siblings.last().outerHeight();
                trCoordinatesX[0] = $tr.offset().left;
                trCoordinatesX[1] = $tr.offset().left + $tr.outerWidth();

                if (!supportsTransitions()) {
                    $ghost.trigger("transitionend");
                }
            }

            function releaseDraggingState(event, $ghost, $originalTr) {
                var $tr = event.data.$tr;
                $("body").off("mousemove", draggingState);
                $("body").off("mouseup", croReleaseDraggingState);

                $ghost.children().each(function(index)
                {
                    $(this).removeAttr("style");
                    return $ghost;
                });

                $ghost.removeAttr("style");
                $ghost.removeClass("tk-row-drag");

                $ghost.remove();
                $originalTr.removeClass("tk-dtbl-cro-ghost");

                var y = event.pageY;

                if ((y > trCoordinatesY[0]) && (y < trCoordinatesY[trCoordinatesY.length - 1])) {
                    var i = 0;
                    while (y > trCoordinatesY[i]) {
                        i++;
                    }
                    ;
                    i--;
                    var middle = (trCoordinatesY[i] + trCoordinatesY[i + 1]) / 2
                    if (y < middle) {
                        moveRow($scope.index, (i - 6));
                    }
                    else {
                        moveRow($scope.index, (i - 6 + 1));
                    }
                }
                $("body").removeClass("oui-util-non-selectable-text tk-dtbl-cro-invalid-target");

                if (!supportsTransitions()) {
                    $ghost.trigger("transitionend");
                }
            }

            function moveRow(startPosition, endPosition) {
                $scope.$apply(function() {
                    uitkEvents.setScope().internalBroadcast("broadcast", $scope.componentId + "-dragRows", {startPosition: startPosition, endPosition: endPosition});
                    $scope.model.records.splice(endPosition, 0, $scope.model.records[startPosition]);
                    if (startPosition < endPosition) {
                        $scope.model.records.splice(startPosition, 1);
                    }
                    else if (startPosition > endPosition) {
                        $scope.model.records.splice(startPosition + 1, 1);
                    }
                });
            }

            function draggingState(event) {
                var $tr = event.data.$tr;
                var $ghost = $tr;
                var newTop = event.pageY - $tr.parent().parent().offset().top;
                $ghost.css("left", 0);
                $ghost.css("top", newTop);
            }
        }

        return {
            restrict: 'A',
            replace: false,
            scope: {
                model: '=',
                index: '=',
                componentId: '@'
            },
            link: link

        };
    };

    uitkDynamicTableRowDraggable.$inject = ['$parse', '$timeout', 'uitkEvents'];

    angular.module('uitk.component.uitkDynamicTable')
            .directive('uitkDynamicTableRowDraggable', uitkDynamicTableRowDraggable);

})();
angular.module("uitk.component.uitkDynamicTable").run(["$templateCache", function($templateCache) {
        $templateCache.put("template/multiSortDrawerAction.html", "<tr class=\"tk-dtbl-add-row-buttons\">\n    <td colspan=\"{{model.columns.length}}\" role=\"presentation\">\n        <div class=\"tk-dtbl-add-row-button-constrainer tk-drawer-action-buttons\" ng-class=\"{edit:isEditing,add:!isEditing}\">\n            <div class=\"tk-dtbl-add-row-button-container\">\n                <span class=\"tk-dtbl-add-row-button-trim\"></span>\n                <span class=\"tk-dtbl-add-row-button-notch\">\n                    <input type=\"button\" class=\"tk-btn-default-action tk-width-10t\" ng-click=\"model.saveMultiSortColumns(model.multiSortColumnsInDrawer, $event)\"\n                           value=\"Save\" role=\"button\" aria-label=\"Save changes Multi Sort\" />\n                    <input type=\"button\" class=\"tk-btn-default-action tk-width-10t\"\n                            value=\"Cancel\" role=\"button\" ng-click=\"model.closeMultiSortDrawer($event)\"\n                           aria-label=\"Cancel changes to multi sort\" />\n                </span>\n            </div>\n        </div>\n    </td>\n</tr>\n");
        $templateCache.put("template/multiSortDrawerContent.html", "<tr class=\"tk-dtbl-multi-sort-row tk-dtbl-header-row\">\n    <td colspan=\"{{model.columns.length}}\">\n        <div class=\"tk-multi-sort-container\">\n        <div>\n            <span translate>Choose column(s) to sort</span>\n            <a class=\"restore-default\" href=\"\" ng-click=\"restoreDefault();\"><span class=\"cux-icon-undo\"></span> <span translate>Restore Default Sort Order</span></a>\n        </div>\n        <div class=\"column-container\">\n            <table>\n                <tr ng-repeat=\"multiSortColumn in model.multiSortColumnsInDrawer\" >\n                    <td ng-if=\"$first\" translate>Sort by</td>\n                    <td ng-if=\"!$first\" translate>Then by</td>\n                    <td><uitk:select item-list=\"dropDownItems\" selected-value=\"multiSortColumn.selectedColumn\"></uitk:select></td>\n                    <td class=\"tk-sorted-column-data\" ng-if=\"multiSortColumn.selectedColumn.dataType === \'text\' || multiSortColumn.selectedColumn.dataType === \'character\'\"><uitk:radio class=\"oui-rfrm-checkboxes\" item-list=\'textRadioItems\' group-name=\'sortOrderGroup{{$index}}\' model-value=\'multiSortColumn.sortOrder\'></uitk:radio></td>\n                    <td class=\"tk-sorted-column-data\" ng-if=\"multiSortColumn.selectedColumn.dataType === \'number\'\"><uitk:radio class=\"oui-rfrm-checkboxes\" item-list=\'numberRadioItems\' group-name=\'sortOrderGroup{{$index}}\' model-value=\'multiSortColumn.sortOrder\'></uitk:radio></td>\n                    <td class=\"tk-sorted-column-data\" ng-if=\"multiSortColumn.selectedColumn.dataType === \'date\'\"><uitk:radio class=\"oui-rfrm-checkboxes\" item-list=\'dateRadioItems\' group-name=\'sortOrderGroup{{$index}}\' model-value=\'multiSortColumn.sortOrder\'></uitk:radio></td>\n                    <td><a ng-if=\"!$first\" href=\"\" ng-click=\"removeColumn($index);\"><span class=\"cux-icon-remove\"></span> <span translate>Remove</span></a></td>\n                </tr>\n            </table>\n        </div>\n        <div class=\"add-column\">\n            <span ng-click=\"addAnotherColumn();\"><a href=\"\"><span class=\"cux-icon-add2\"></span> <span translate>Add another sort column</span></a></span>\n        </div>\n        </div>\n    </td>\n</tr>");
        $templateCache.put("template/openColumnsDrawerAction.html", "<tr class=\"tk-dtbl-add-row-buttons\">\n    <td colspan=\"{{model.columns.length}}\" role=\"presentation\">\n        <div class=\"tk-dtbl-add-row-button-constrainer tk-show-hide-action\" ng-class=\"{edit:isEditing,add:!isEditing}\">\n            <div class=\"tk-dtbl-add-row-button-container\">\n                <span class=\"tk-dtbl-add-row-button-trim\"></span>\n                <span class=\"tk-dtbl-add-row-button-notch\">\n                    <input type=\"button\" class=\"tk-btn-default-action tk-width-10t\" ng-click=\"model.modifyColumn(false, $event)\" value=\"Save\" role=\"button\" aria-label=\"{{(isEditing)?\'Save changes to record\':\'Create new record\'}}\" />\n                    <input type=\"button\" class=\"tk-btn-default-action tk-width-10t\" ng-click=\"model.modifyColumn(true, $event)\" value=\"Cancel\" role=\"button\" aria-label=\"{{(isEditing)?\'Reset or Cancel changes to record\':\'Cancel new record creation\'}}\" />\n                </span>\n            </div>\n        </div>\n    </td>\n</tr>\n");
        $templateCache.put("template/openColumnsDrawerContent.html", "<tr class=\"tk-dtbl-add-row tk-dtbl-header-row\">\n    <td colspan=\"{{model.columns.length}}\">\n        <div class=\"tk-dtbl-add-row-constrainer tk-show-hide-container\">\n        	<div class=\"tk-filter-container\">\n	        	<div class=\"tk-grid\">\n	            	<div class=\"tk-col-1-4\" translate>Select columns to make them show/hide.</div>\n	            	<div class=\"tk-col-3-4 tk-text-align-right\"><a ng-click=\"restoreDefault();\" class=\"restore-default\"><span class=\"cux-icon-undo\"></span> <span translate>Restore Defaults</span></a></div>\n	            </div>\n	        	<div class=\"tk-grid\">\n	            	<div class=\"tk-col-1-4\">\n	            		<div><span translate>Sort by: Column Order</span> | <a ng-click=\"order(\'label\')\" translate>Alphanumeric</a></div>\n	            		<div class=\"oui-util-scroll-vertical filterColumnsList\">\n	            			<ul class=\"sortoptions\">\n					          <li ng-repeat=\"option in model.columns | orderBy:predicate:reverse\" ng-class=\"{\'tk-show-hide-column-selected\' : (option.showColumnInTable && !option.showAlways)}\">\n					            <input type=\"checkbox\" ng-model=\"option.showColumnInTable\" ng-style=\"{\'visibility\': !option.showAlways?\'visible\':\'hidden\'}\">\n					            <label>{{option.label}} <span ng-if=\"option.showAlways\">(always visible)</span></label>\n					          </li>\n					        </ul>\n	            		</div>\n	            	</div>\n	            	<div class=\"tk-col-3-4\"></div>\n	            </div>\n            </div>\n        </div>\n    </td>\n</tr>\n");
        $templateCache.put("template/openDrawerAction.html", "<tr class=\"tk-dtbl-add-row-buttons\">\n    <td colspan=\"{{model.columns.length}}\" role=\"presentation\">\n        <div class=\"tk-dtbl-add-row-button-constrainer\" ng-class=\"{edit:isEditing,add:!isEditing}\">\n            <div class=\"tk-dtbl-add-row-button-container\">\n                <span class=\"tk-dtbl-add-row-button-trim\"></span>\n                <span class=\"tk-dtbl-add-row-button-notch\">\n                    <input type=\"button\" class=\"tk-btn-default-action tk-width-10t\" ng-click=\"model.modifyRow(isEditing,false,rowRecord, $event)\"\n                           value=\"Save\" role=\"button\" aria-label=\"{{(isEditing)?\'Save changes to record\':\'Create new record\'}}\" />\n                    <input type=\"button\" class=\"tk-btn-default-action tk-width-10t\"\n                           ng-click=\"model.modifyRow(isEditing,true,rowRecord, $event)\" value=\"Cancel\" role=\"button\"\n                           aria-label=\"{{(isEditing)?\'Reset or Cancel changes to record\':\'Cancel new record creation\'}}\" />\n                </span>\n            </div>\n        </div>\n    </td>\n</tr>\n");
        $templateCache.put("template/openDrawerContent.html", "<tr class=\"tk-dtbl-add-row tk-dtbl-header-row\">\n    <td ng-repeat=\"column in model.columns | displayColumnFilter | orderBy:\'layoutOrder\'\">\n        <div class=\"tk-dtbl-add-row-constrainer\" ng-class=\"{edit:isEditing,add:!isEditing}\" >\n            <div ng-if=\"$index == 0\"  class=\"tk-dtbl-add-row-container\">\n                <span class=\"tk-dtbl-add-row-required\"  uitk-compile-cell-template=\"inputTemplate\"> </span>\n            </div>\n\n            <div ng-if=\"$index > 0\"  class=\"tk-dtbl-add-row-container\"  uitk-compile-cell-template=\"inputTemplate\">\n\n            </div>\n\n        </div>\n    </td>\n</tr>\n");
        $templateCache.put("template/uitk-dynamic-table.html", "<div class=\"tk-dtbl-container\">\n    <span ng-if=\"table.showPagination()\" aria-label=\"total number of records\"> {{ \"Total Records:\" | uitkTranslate}} {{ model.totalRecordsCount }} </span>\n    <span class=\"oui-a11y-hidden\" id=\"{{componentId}}_pageNumber\"> showing page {{page.pageNumber}} of {{table.totalPagesCount()}}</span>\n        <div ng-if=\"table.showPagination()\" class=\"pagination\" role=\"paginator\" ng-class=\"{ \'tk-pageinput-error-display\' : page.pageNumberError }\">\n            <label style=\"font-weight: normal;\" for=\"{{rowsDropdownId}}\">{{ \"Show\" | uitkTranslate}}</label>\n            <select id=\"{{rowsDropdownId}}\" ng-model=\"model.pagination.recordsPerPage\" ng-change=\"model.onPaginate(1)\"\n                    ng-disabled=\"model.totalRecordsCount === 0\"\n                    ng-options=\"value for value in model.pagination.recordsPerPageChoices\" aria-label=\"number of records per page\">\n            </select> {{ \"per page\" | uitkTranslate}}\n            <ul>\n                <li>\n                    <a href=\"\" ng-if=\"table.hasPreviousPage()\" ng-click=\"model.onPaginate(1)\" uitk-navigable  title=\"First Page\">\n                        <span class=\"cux-icon-rewind\"></span> {{ \"First\" | uitkTranslate}}\n                    </a>\n                    <span ng-if=\"!table.hasPreviousPage()\" class=\"cux-icon-rewind\" aria-disabled=\"true\"></span>\n                    <span ng-if=\"!table.hasPreviousPage()\" aria-disabled=\"true\"> {{ \"First\" | uitkTranslate}} </span>\n                </li>\n                <li>\n                    <a href=\"\" ng-if=\"table.hasPreviousPage()\" ng-click=\"model.onPaginate(table.previousPageNumber())\"\n                       uitk-navigable  title=\"Previous Page\">\n                        <span class=\"cux-icon-carrot_left\"></span> {{ \"Previous\" | uitkTranslate}}\n                    </a>\n                    <span ng-if=\"!table.hasPreviousPage()\" class=\"cux-icon-carrot_left\" aria-disabled=\"true\"></span>\n                    <span ng-if=\"!table.hasPreviousPage()\" aria-disabled=\"true\">{{ \"Previous\" | uitkTranslate}}</span>\n                </li>\n            </ul>\n            <span class=\"tk-pageinput\">\n                <label style=\"font-weight: normal;\" for=\"{{componentId}}_pageInput\"> {{ \"Page\" | uitkTranslate}} </label>\n                <input type=\'text\' id=\"{{componentId}}_pageInput\" ng-model=\"page.pageNumber\" ng-class=\"{ \'tk-pageinput-error\' : page.pageNumberError }\" class=\'tk-width-3t tk-text-align-right\' uitk-navigable uitk-numbers-only\n                       aria-required=\"true\" aria-invalid=\"{{page.pageNumberError}}\"  aria-describedby=\"{{page.pageNumberDescribedBy}}\" ng-keydown=\"pageNumberEvent($event)\" ng-blur=\"pageNumberEvent($event)\"/>\n                <span> {{ \"of\" | uitkTranslate}} {{table.totalPagesCount()}}</span>\n                <div class=\"tk-pageerror-message\" aria-hidden=\"{{page.pageNumberError}}\" id=\"{{componentId}}_pageError\" aria-describedby=\"Enter a valid number, one that is in the page range.\" id=\"{{componentId}}_pageNumberError\" ng-if=\"page.pageNumberError\">Enter a valid number, one that is in the page range.</div>\n            </span>\n            <ul>\n                <li>\n                    <a href=\"\" ng-if=\"table.hasNextPage()\" ng-click=\"model.onPaginate(table.nextPageNumber())\"\n                       uitk-navigable title=\"Next Page\">\n                        {{ \"Next\" | uitkTranslate}} <span class=\"cux-icon-carrot_right\"></span>\n                    </a>\n                    <span ng-if=\"!table.hasNextPage()\" aria-disabled=\"true\"> {{ \"Next\" | uitkTranslate}} </span>\n                    <span ng-if=\"!table.hasNextPage()\" class=\"cux-icon-carrot_right\" aria-disabled=\"true\"></span>\n                </li>\n                <li>\n                    <a href=\"\" ng-if=\"table.hasNextPage()\" ng-click=\"model.onPaginate(table.lastPageNumber())\"\n                       uitk-navigable title=\"Last Page\">\n                        {{ \"Last\" | uitkTranslate}} <span class=\"cux-icon-forward\"> </span>\n                    </a>\n                    <span ng-if=\"!table.hasNextPage()\" aria-disabled=\"true\"> {{ \"Last\" | uitkTranslate}} </span>\n                    <span ng-if=\"!table.hasNextPage()\" class=\"cux-icon-forward\" aria-disabled=\"true\"></span>\n                </li>\n            </ul>\n        </div>\n\n    <ul ng-if=\"showTableOptions\"\n        class=\"table-action-container\">\n        <li ng-if=\"isFiltersClear\">\n            <a href=\"\" ng-if=\"isFiltersApplied()\" ng-click=\"table.clearAllFilters()\"><span class=\"cux-icon-filter\"></span> {{\"Clear All Filters\" | uitkTranslate}}</a>\n            <span href=\"\" ng-if=\"!isFiltersApplied()\" aria-role=\"button\" aria-disabled=\"true\"><span class=\"cux-icon-filter\"></span> {{\"Clear All Filters\" | uitkTranslate}}</span>\n        </li>\n\n        <li ng-if=\"model.onExport\">\n            <a href=\"\" title=\"Export to CSV\" ng-click=\"table.export()\"><span class=\"cux-icon-export\"></span> {{\"Export (CSV)\" | uitkTranslate}}</a>\n        </li>\n\n        <li ng-if=\"model.onExportNestedData\">\n            <a href=\"\" title=\"Export to Excel\" ng-click=\"table.exportNestedData()\"><span class=\"cux-icon-export\"></span> {{\"Export (Excel)\" | uitkTranslate}}</a>\n        </li>\n\n        <li ng-repeat=\"link in model.links\" uitk-compile-cell-template=\"link\"></li>\n    </ul>\n\n    <table class=\"tk-dtbl tk-dtbl-reorderable-columns\" role=\"grid\"\n           ng-class=\"{ \'tk-dtbl-expandable\' : model.rowTemplate }\" >\n        <thead>\n        <tr>\n            <th ng-repeat=\"column in model.columns | displayColumnFilter | orderBy:\'layoutOrder\'\"\n                ng-class=\"{\'tk-dtbl-non-reorderable-column-cursor\':!column.draggable, \'tk-dtbl-cell-dotted-right-border\' : column.resizeInProgress, \'tk-dtbl-cro-target-border\' : column.dropInProgress, \'tk-hide-column\': column.cellName === \'multiSelectColumn\' && !model.enableMultiSelect }\"\n                uitk-dynamic-table-column-draggable=\"{{column.draggable}}\"\n                \n                ng-attr-style=\"{{column.style}}\"\n                aria-label=\"{{column.columnId}}\" aria-sort=\"{{ table.sortOrderDescription(column.sortOrder) }}\"\n                align=\"{{column.align}}\">\n                <span ng-if=\"!model.rowTemplate\" uitk-resizable-column ng-class=\"{\'resizable\' : !$last }\">&nbsp;</span>\n                <a ng-if=\"isColumnSortable(column) && (!isMultiSortColumn(column))\" uitk-navigable=\"isColumnSortable(column)\"\n                   class=\"tk-dtbl-as-table-cell {{column.dataType}}\" ng-click=\"model.onSort(column.columnId)\" >\n                    <span class=\"overflow\" uitk-compile-label=\"column.label\">  </span>\n                    <span ng-class=\"{ \'cux-icon-carrot_up\' : sortOrderEqualTo(column,1), \'cux-icon-carrot_down\': sortOrderEqualTo(column,-1),\'cux-icon-sort\' : column.sortable && sortOrderEqualTo(column,0) }\">\n                    </span>\n\n                    <span ng-if=\"sortOrderEqualTo(column,\'not 0\')\" class=\"oui-a11y-hidden\" > , (sorted {{ table.sortOrderDescription(column.sortOrder) }})</span>\n                    <span ng-if=\"sortOrderEqualTo(column,0)\" class=\"oui-a11y-hidden\" > , (sortable)</span>\n                </a>\n                <a ng-if=\"isColumnSortable(column) && (isMultiSortColumn(column))\" uitk-navigable=\"isColumnSortable(column)&& isMultiSortColumn(column)\"\n                   class=\"tk-dtbl-as-table-cell {{column.dataType}}\" aria-disabled=\"true\">\n                    <span class=\"overflow\" uitk-compile-label=\"column.label\">  </span>\n                    <span ng-class=\"{ \'cux-icon-carrot_up\' : multiSortOrderEqualTo(column,1), \'cux-icon-carrot_down\': multiSortOrderEqualTo(column,-1)}\">\n                    </span>\n                </a>\n\n\n                <span ng-if=\"!column.sortable\" class=\"tk-dtbl-as-table-cell {{column.dataType}}\"\n                      uitk-compile-label=\"column.label\"></span>\n                <div ng-if=\"column.cellHeaderTemplate\" uitk-compile-cell-template=\"cellHeaderTemplate\"> </div>\n            </th>\n        </tr>\n        </thead>\n\n        <tbody ng-if=\"!model.rowTemplate && model.totalRecordsCount !== 0\">\n\n        <!-- Begins Show/Hide Columns -->\n        <tr uitk-show-hide-columns-drawer-content model=\"model\" component-id=\"componentId\"></tr>\n        <tr uitk-show-hide-columns-drawer-action model=\"model\" component-id=\"componentId\"></tr>\n        <!-- Ends Show/Hide Columns -->\n\n        <!-- Begins multiSelectDrawer -->\n        <tr uitk-multi-sort-drawer-content model=\"model\" component-id=\"componentId\"></tr>\n        <tr uitk-multi-sort-drawer-action model=\"model\" component-id=\"componentId\"></tr>\n\n        <!-- Begins onAddRow -->\n        <tr uitk-drawer-content model=\"model\" component-id=\"componentId\"></tr>\n        <tr uitk-drawer-action model=\"model\" component-id=\"componentId\"></tr>\n        <!-- Ends onAddRow -->\n        <tr ng-if=\"!model.onRowSelect\" ng-repeat=\"record in model.records | limitTo : table.recordsPerPage()\" role=\"row\">\n            <td uitk-compile-cell-template=\"cellTemplate\"\n                ng-repeat=\"column in model.columns | displayColumnFilter | orderBy:\'layoutOrder\'\"\n                ng-class=\"{ \'tk-dtbl-cell-dotted-right-border\' : column.resizeInProgress }\" align=\"{{column.align}}\"\n                class=\"tk-dtbl-cell {{column.dataType}}\">\n            </td>\n        </tr>\n        <tr ng-if=\"model.onRowSelect && !customOnEditRowIsDefined && !usingCRUD\" class=\"tk-row-order\" id=\"row_{{$index}}\" ng-repeat=\"record in model.records | limitTo : table.recordsPerPage()\"\n            ng-class=\"{\'tk-row-highlight\': record.selected, \'tk-dtbl-reorderable-row-cursor\': model.rowDraggable}\"\n            aria-selected=\"{{record.selected}}\" role=\"row\" aria-label=\"{{record.firstName}} {{record.lastName}}\"\n            ng-click=\"model.onRowSelect($event, record);\" uitk-selectable uitk-dynamic-table-row-draggable=\"model.rowDraggable\" model=\"model\" index=\"$index\" component-id=\"{{componentId}}\">\n            <td uitk-compile-cell-template=\"cellTemplate\"\n                ng-repeat=\"column in model.columns | displayColumnFilter | orderBy:\'layoutOrder\'\"  align=\"{{column.align}}\"\n                ng-class=\"{ \'tk-dtbl-cell-dotted-right-border\' : column.resizeInProgress }\"\n                class=\"tk-dtbl-cell {{column.dataType}}\">\n            </td>\n        </tr>\n\n        <tr ng-if=\"rowSelectedandEditing\" ng-hide=\"record.hideRecord\" id=\"{{componentId+\'_\'+$index}}\"\n            ng-repeat-start=\"record in model.records | limitTo : table.recordsPerPage()\" ng-init=\"record.index =$index;\"\n            ng-class=\"{\'tk-row-highlight\': record.selected}\" aria-selected=\"{{record.selected}}\"\n            aria-label=\"{{record.firstName}} {{record.lastName}}\"\n            role=\"row\" ng-click=\"model.onRowSelect($event, record);\" uitk-selectable>\n            <td uitk-compile-cell-template=\"cellTemplate\"\n                ng-repeat=\"column in model.columns | displayColumnFilter | orderBy:\'layoutOrder\'\"  align=\"{{column.align}}\"\n                ng-class=\"{ \'tk-dtbl-cell-dotted-right-border\' : column.resizeInProgress, \'tk-hide-column\': column.cellName === \'multiSelectColumn\' && !model.enableMultiSelect }\"\n                class=\"tk-dtbl-cell {{column.dataType}}\">\n            </td>\n        </tr>\n        <!-- Begins onEditRow -->\n        <tr ng-if=\"rowSelectedandEditing\" uitk-drawer-content model=\"model\" component-id=\"componentId\" is-editing=\"true\" row-record=\"record\"  ></tr>\n        <tr ng-show=\"model.recordOperationInProgress\" ng-if=\"rowSelectedandEditing\" uitk-drawer-action model=\"model\" component-id=\"componentId\" is-editing=\"true\" row-record=\"record\" ng-repeat-end></tr>\n        <!-- Ends onEditRow -->\n\n        </tbody>\n\n        <tbody ng-if=\"model.rowTemplate && model.totalRecordsCount !== 0\">\n        <tr uitk-compile-cell-template ng-repeat=\"currentRecord in model.records | limitTo : table.recordsPerPage()\">\n        </tr>\n        </tbody>\n\n        <tbody ng-if=\"model.totalRecordsCount === 0\">\n        <tr class=\"tk-dtbl-add-row tk-dtbl-header-row\">\n            <td ng-repeat=\"column in model.columns | orderBy:\'layoutOrder\'\">\n                <div class=\"tk-dtbl-add-row-constrainer\">\n                    <div ng-if=\"$index == 0\"  class=\"tk-dtbl-add-row-container\">\n                        <span class=\"tk-dtbl-add-row-required\"  uitk-compile-cell-template=\"inputTemplate\"> </span>\n                    </div>\n\n                    <div ng-if=\"$index > 0\"  class=\"tk-dtbl-add-row-container\" uitk-compile-cell-template=\"inputTemplate\">\n                    </div>\n                </div>\n            </td>\n        </tr>\n\n        <tr class=\"tk-dtbl-add-row-buttons tk-dtbl-header-row\">\n            <td colspan=\"{{model.columns.length}}\" role=\"presentation\">\n                <div class=\"tk-dtbl-add-row-button-constrainer\">\n                    <div class=\"tk-dtbl-add-row-button-container\">\n                        <span class=\"tk-dtbl-add-row-button-trim\"></span>\n                        <span class=\"tk-dtbl-add-row-button-notch\">\n                            <input type=\"button\" class=\"tk-btn-default-action tk-width-10t\" ng-click=\"model.onAddRow()\"\n                                   value=\"Save\" role=\"button\" aria-label=\"Create new record\" />\n                            <input type=\"button\" class=\"tk-btn-default-action tk-width-10t\" ng-click=\"model.onAddRowCancel()\"\n                                   value=\"Cancel\" role=\"button\" aria-label=\"Cancel new record creation\" />\n                        </span>\n                    </div>\n                </div>\n            </td>\n        </tr>\n\n        <tr>\n            <td colspan=\"{{model.columns.length}}\" class=\"tk-dtbl-cell tk-dtbl-no-records\">\n                {{ \"No records found.\" | uitkTranslate }}\n            </td>\n        </tr>\n        </tbody>\n    </table>\n\n    <!-- Confirm modal dialog for delete -->\n    <uitk:dialog ng-if=\"model.modalShown\" dialog-id=\'deleteDialog\' dialog-role=\'dialog\'\n                 header-text=\'Confirm Delete\' show=\'model.modalShown\' default-width=\'45%\'\n                 default-height=\"51%\" call-back-hide=\'model.onDeleteCancel()\'>\n\n        <span>Are you sure you want to delete record(s)? This action can\'t be undone. </span>\n        <br/>\n\n        <uitk:button type=\"button\" style=\"float:left;\" value=\"Delete\"\n                     enable-default=\"true\"\n                     ng-click=\"model.onDeleteConfirm();\"\n                     custom-class=\'uitk-width-6t uitk-btn-close-dialog\'></uitk:button>\n\n        <uitk:button type=\"button\" style=\"float:left;\" value=\"Cancel\"\n                     enable-default=\"true\"\n                     onclick=\"document.getElementById(\'deleteDialog_closeLink\').click();\"\n\n                     custom-class=\'uitk-width-6t uitk-btn-close-dialog\'></uitk:button>\n    </uitk:dialog>\n</div>");
    }]);