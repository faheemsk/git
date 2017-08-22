/*
 * Copyright (c) Optum 2015 - All Rights Reserved.
 */
(function () {
    var uitkDynamicTableApiList = function() {
        return {
            restrict: 'E',
            templateUrl: 'api-documentation/api-main-attributes-list.html'
        };
    };
    angular.module('uitk.dynamicTableApiList', [])
        .controller('dynamicTableApiListController', ['$scope', function($scope) {
            $scope.apiItemsList = [
                {
                    attrib : 'columns',
                    txt : 'Array',
                    req : 'true',
                    desc : 'Array of Table Column Model object'
                },{
                    attrib : 'columns.columnId',
                    txt : 'Text',
                    req : 'true',
                    desc : 'Unique column identifier used internally to get context of given column'
                },{
                    attrib : 'columns.label',
                    txt : 'Text',
                    req : 'true',
                    desc : 'Column header label'
                },{
                    attrib : 'columns.sortable',
                    txt : 'Boolean',
                    req : 'false',
                    desc : 'Makes column sortable. Default is false.'
                },{
                    attrib : 'columns.sortOrder',
                    txt : 'Number',
                    req : 'false',
                    desc : '1 for ascending, 0 for nothing, -1 for descending order of Sorting'
                },{
                    attrib : 'columns.draggable',
                    txt : 'Boolean',
                    req : 'false',
                    desc : 'If set to true, makes column draggable'
                },{
                    attrib : 'columns.layoutOrder',
                    txt : 'Number',
                    req : 'false',
                    desc : 'Columns will be shown by ascending order of "layoutOrder" given'
                },{
                    attrib : 'columns.enableSearch',
                    txt : 'Boolean',
                    req : 'false',
                    desc : 'Enables column level searching. Default is false.'
                },{
                    attrib : 'columns.searchInput',
                    txt : 'Text',
                    req : 'false',
                    desc : 'Search text binding at column level. Default is empty string'
                },{
                    attrib : 'columns.showAlways',
                    txt : 'Boolean',
                    req : 'false',
                    desc : 'Enables column show/hide functionality. Default is false.'
                },{
                    attrib : 'columns.showColumnInTable',
                    txt : 'Boolean',
                    req : 'false',
                    desc : 'This flag is used to toggle show/hide column in table'
                },{
                    attrib : 'columns.cellHeaderTemplate',
                    txt : 'HTML',
                    req : 'false',
                    desc : 'Additional custom HTML header template for given column which would be shown below column header label'
                },{
                    attrib : 'columns.cellTemplate',
                    txt : 'HTML',
                    req : 'false',
                    desc : 'Custom HTML template to show data under given column. It may be used to support cell editing'
                },{
                    attrib : 'columns.inputTemplate',
                    txt : 'HTML',
                    req : 'false',
                    desc : 'Custom HTML template to accept data under given column. It may be used to support add new record(s)'
                },{
                    attrib : 'columns.dataType',
                    txt : 'Text',
                    req : 'false',
                    desc : 'Data format for data to be shown given column to apply styles. Possible values are "text", "number", "date", "character", "icon".'
                },{
                    attrib : 'columns.style',
                    txt : 'CSS',
                    req : 'false',
                    desc : 'Inline style for given column'
                },{
                    attrib : 'columns.excludeFromExport',
                    txt : 'Boolean',
                    req : 'false',
                    desc : 'Flag used to exclude this column while the data being exported to CSV'
                },{
                    attrib : 'columns.combination',
                    txt : 'Array',
                    req : 'false',
                    desc : 'Developer supplied combination of fields to use for validation. This property must be added along with column.validationPattern or the validation will not work. This property also needs to be used for the Sorting to function the correct way.'
                },{
                    attrib : 'columns.validationPattern',
                    txt : 'Regex',
                    req : 'false',
                    desc : 'Developer supplied regex. This property is used for validating the content entered by end-users in the addRow and editRow functions.'
                },{
                    attrib : 'columns.searchByItemType',
                    txt : 'String',
                    req : 'false',
                    desc : 'Allows the developer to set how the search is performed. Options include "exact" and "contains". "contains" is the default for client side table content filtering, and will search table content for words that contain the search criteria. "exact" will search the table content for an exact matching of the search criteria.'
                },{
                    attrib : 'subColumns',
                    txt : 'Array',
                    req : 'false',
                    desc : 'Array of Table Sub-Column Model object'
                },{
                    attrib : 'subColumns.columnId',
                    txt : 'Text',
                    req : 'false',
                    desc : 'Unique column identifier used internally to get context of given column'
                },{
                    attrib : 'subColumns.label',
                    txt : 'Text',
                    req : 'false',
                    desc : 'Column header label'
                },{
                    attrib : 'subColumns.layoutOrder',
                    txt : 'Number',
                    req : 'false',
                    desc : 'Columns will be shown by ascending order of "layoutOrder" given'
                },{
                    attrib : 'subColumns.cellTemplate',
                    txt : 'HTML',
                    req : 'false',
                    desc : 'Custom HTML template to show data under given column. It may be used to support cell editing'
                },{
                    attrib : 'pagination',
                    txt : 'JSON',
                    req : 'false',
                    desc : 'Pagination Object. If not mentioned, pagination section will not be shown.'
                },{
                    attrib : 'pagination.currentPageNumber',
                    txt : 'Number',
                    req : 'false',
                    desc : 'The current page number.Default is 1.'
                },{
                    attrib : 'pagination.recordsPerPage',
                    txt : 'Number',
                    req : 'false',
                    desc : 'The maximum number of records per page.Default is 10.'
                },{
                    attrib : 'pagination.recordsPerPageChoices',
                    txt : 'Array',
                    req : 'false',
                    desc : 'The array of numbers which represents possible choices user would be able to make for recordsPerPage. Default is [10, 25, 50, 75, 100].'
                },{
                    attrib : 'pagination.paginationWindow',
                    txt : 'Number',
                    req : 'false',
                    desc : 'The maximum number of adajcent page links to show including current page link.Default is 5.'
                },{
                    attrib : 'records',
                    txt : 'Array',
                    req : 'false',
                    desc : 'Array of records for the table'
                },{
                    attrib : 'totalRecordsCount',
                    txt : 'Number',
                    req : 'false',
                    desc : 'Total number of records for given set of filter condition values'
                },{
                    attrib : 'rowTemplate',
                    txt : 'HTML',
                    req : 'false',
                    desc : 'Custom HTML template to represent a row. It will override "cellTemplate" values for all columns. It can be used to support row editing, expandable rows, nested tables etc.'
                },{
                    attrib : 'onChange',
                    txt : 'Function',
                    req : 'false',
                    desc : 'Called when any of filtering conditions such as Pagination, Sorting, Searching  params changes. As of release 3.7 this function has been moved to the master code.'
                },{
                    attrib : 'onPaginate',
                    txt : 'Function',
                    req : 'false',
                    desc : 'Called when Pagination actions invoked by user. Default version calls onChange method. As of release 3.7 this function has been moved to the master code.'
                },{
                    attrib : 'onSort',
                    txt : 'Function',
                    req : 'false',
                    desc : 'Called when Sorting action happens for any sortable column. Default version calls onChange method. As of release 3.7 the modal functionality for deleting a record has been moved to the master code.'
                },{
                    attrib : 'onMultiSort',
                    txt : 'Function',
                    req : 'false',
                    desc : 'Called when Multi Sorting is applied. Default version calls onChange method.'
                },{
                    attrib : 'onSearch',
                    txt : 'Function',
                    req : 'false',
                    desc : 'Called when Searching action happens for any column.  Default version calls onChange method.'
                },{
                    attrib : 'onLoad',
                    txt : 'Function',
                    req : 'false',
                    desc : 'Called when component loads. Default version calls onChange method. Call this method with "true" as argument to refresh the table'
                },{
                    attrib : 'onExport',
                    txt : 'Function',
                    req : 'false',
                    desc : 'If mentioned it will activate "Export to CSV/XLS" link which will help download entire data set keeping filtering conditions in mind'
                },{
                    attrib : 'onAddRow',
                    txt : 'Function',
                    req : 'false',
                    desc : 'Get called when "Submit" button of Add row drawer is clicked. As of release 3.7 this function has been moved to the master code.'
                },{
                    attrib : 'onAddRowCancel',
                    txt : 'Function',
                    req : 'false',
                    desc : 'Get called when "Cancel" button of Add row drawer is clicked. As of release 3.7 this function has been moved to the master code.'
                },{
                    attrib : 'onEditRow',
                    txt : 'Function',
                    req : 'false',
                    desc : 'Gets called when "Edit" button of row drawer is clicked. As of release 3.7 this function has been moved to the master code.'
                },{
                    attrib : 'onEditRowCancel',
                    txt : 'Function',
                    req : 'false',
                    desc : 'Get called when "Cancel" button of Edit row drawer is clicked. As of release 3.7 this function has been moved to the master code.'
                },{
                    attrib : 'onDelete',
                    txt : 'Function',
                    req : 'false',
                    desc : 'Get called when "Delete Row" button is clicked. As of release 3.7 this function has been moved to the master code.'
                },{
                    attrib : 'onDeleteReset',
                    txt : 'Function',
                    req : 'false',
                    desc : 'Get called after a record has been deleted. As of release 3.7 this function has been moved to the master code.'
                },{
                    attrib : 'onRecordDelete',
                    txt : 'Function',
                    req : 'false',
                    desc : 'Get called when a record has been deleted. As of release 3.7 this function has been moved to the master code.'
                },{
                    attrib : 'openDrawer',
                    txt : 'Function',
                    req : 'false',
                    desc : 'Get called when a record is being added or edited. As of release 3.7 this function has been moved to the master code.'
                },{
                    attrib : 'openDrawerForRecord',
                    txt : 'Function',
                    req : 'false',
                    desc : 'Opens the add or edit drawer for the selected record. As of release 3.7 this function has been moved to the master code.'
                },{
                    attrib : 'multiSortColumns',
                    txt : 'Object',
                    req : 'false',
                    desc : 'It has two properties sortBy and sortOrder. sortBy is array which specifies list of columns for multi-column sort, sortOrder is array which specifies sort order(1 for ascending, -1 for descending) of columns specified in sortBy.'
                },{
                    attrib : 'isMultiSortApplied',
                    txt : 'Boolean',
                    req : 'false',
                    desc : 'true if multi sort applied to table, default value is false'
                },{
                    attrib : 'openMultiSortDrawer',
                    txt : 'Function',
                    req : 'false',
                    desc : 'Opens multi sort drawer'
                },{
                    attrib : 'closeMultiSortDrawer',
                    txt : 'Function',
                    req : 'false',
                    desc : 'Closes multi sort drawer'
                },{
                    attrib : 'saveMultiSortColumns',
                    txt : 'Function',
                    req : 'false',
                    desc : 'This function saves selected column of multi sort drawer to view model and call onChange method with multi sort parameter'
                },{
                    attrib : 'recordOperationInProgress',
                    txt : 'Boolean',
                    req : 'false',
                    desc : 'Flags whether there is an operation already running on a record when a user tries to perform another operation. As of release 3.7 this function has been moved to the master code.'
                },{
                    attrib : 'clearAllFilters',
                    txt : 'Boolean',
                    req : 'false',
                    desc : 'If true, it will activate "Clear All Filters" link which will help clear filtering conditions added by end user after component load'
                },{
                    attrib : 'fixedHeader',
                    txt : 'Boolean',
                    req : 'false',
                    desc : 'If true, header row will be fixed during window scrolling'
                },
                {
                    attrib : 'links',
                    txt : 'Array',
                    req : 'false',
                    desc : 'Links Array. If not mentioned, links section will not be shown.'
                },{
                    attrib : 'linkClick',
                    txt : 'Function',
                    req : 'false',
                    desc : 'Called when any link in the links array is clicked.'
                },{
                    attrib : 'modalShown',
                    txt : 'Boolean',
                    req : 'false',
                    desc : 'Allows the developer to control when a modal is shown. For example, showing a deleted record modal. As of release 3.7 the modal functionality for deleting a record has been moved to the master code.'
                },{
                    attrib : 'crudOptions',
                    txt : 'JSON',
                    req : 'false',
                    desc : 'Called when any link in the links array is clicked.'
                },{
                    attrib : 'crudOptions.notificationMessage',
                    txt : 'JSON',
                    req : 'false',
                    desc : 'This is used to display a message if a row has been sucessfully been saved, added, edited, deleted. If using crudOptions, then this is required. If you do not add this then an error will be thrown.'
                },{
                    attrib : 'crudOptions.saveRecord',
                    txt : 'Function',
                    req : 'false',
                    desc : 'Called when a record is saved. This must return a promise. If a promise is not returned then the function will not work and an error will be thrown. If using crudOptions, then this is required.'
                },{
                    attrib : 'crudOptions.deleteRecord',
                    txt : 'Function',
                    req : 'false',
                    desc : 'Called when a record is deleted. This must return a promise. If a promise is not returned then the function will not work and an error will be thrown. If using crudOptions, then this is required.'
                },{
                    attrib : 'enableMultiSelect',
                    txt : 'Boolean',
                    req : 'false',
                    desc : 'If true, will enable the Multi Select functionality for the table. Default is false. If using crudOptions, then this is required.'
                },{
                    attrib : 'showErrorMessage',
                    txt : 'Function',
                    req : 'false',
                    desc : 'If you try to update a second record, while you are already in the process of updating one, then an error will be thrown. In order to make the error visible on the UI the developer needs to add this function to the model.'
                },{
                    attrib : 'showSuccessMessage',
                    txt : 'Function',
                    req : 'false',
                    desc : 'This function gets called when component needs to display success message(ex., When Multi-Column Sort applied to table)'
                },{
                    attrib : 'loadPersistedModel',
                    txt : 'Boolean',
                    req : 'false',
                    desc : 'It is used to enable server state management functionality. If specified true, Component will request for persisted model using getPersistedModel method and load it to current model. Default value false.'
                },{
                    attrib : 'getPersistedModel',
                    txt : 'Function',
                    req : 'false',
                    desc : 'This method is used by component to load persisted view model from server. Consumer needs to define this method in view model and it should return promise'
                },{
                    attrib : 'getPersistingModel',
                    txt : 'Function',
                    req : 'false',
                    desc : 'This method is defined in component and it is used to get/extract Persisting model from current view model. When user performs any action like sorting, searching, it needs to persist this model to server so that on next load application can retrieve it.'
                },{
                    attrib : 'rowDraggable',
                    txt : 'boolean',
                    req : 'false',
                    desc : 'To enable row drag and drop functionality'
                }
            ];
        }])
    .directive('uitkDynamicTableApiList', uitkDynamicTableApiList)
})();