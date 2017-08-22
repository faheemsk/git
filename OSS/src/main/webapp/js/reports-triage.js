$(window).load(function () {
    $("#loading").fadeOut("slow");
});
angular.module("UitkApp")

        .factory('breadcrumbNavigationViewModelRep', function() {
            return {
                id: 'breadcrumbId5',
                links: [
                    {template: '<span><span class="cux-icon-home"></span>Home</span>', url: '#home'},
                    {template: '<span>Hartford Network</span>', url: '#item2'},
                    {template: '<span>Security Risk Triage</span>', url: '#item3'},
                    {template: '<span>Reports</span>', url: '#item3'},
                ]

            }
        })
        .factory('multiSortViewModelRep',
                function(globalSuccessMessageViewModel, globalErrorMessageViewModel) {

                    return  {
                        pagination: {
                            currentPageNumber: 1,
                            paginationWindow: 5,
                            recordsPerPage: 10
                        },
                        columns: [
                            /*{
                             columnId:'multiSelect',
                             layoutOrder:1,
                             style: "width: 5%;",
                             align: "center",
                             cellHeaderTemplate: [
                             '<label for="selectAllCheckboxId1" class="oui-a11y-hidden">Select All Checkbox</label> ',
                             '<input type="checkbox" ng-model="model.selectAllChecked" id="selectAllCheckboxId1" ng-change="model.onSelectAllRows(model.selectAllChecked);"/> '
                             ].join(''),
                             cellTemplate:['<label for="{{record.name}},{{record.filename}},{{record.action}}" class="oui-a11y-hidden">Select Row</label> ',
                             '<input type="checkbox" id="{{record.name}},{{record.filename}},{{record.action}}" ng-model="record.selected" ng-click="model.onRowSelect($event, record, record.selected);"/>'].join(''),
                             },*/
                            {
                                columnId: 'name',
                                label: 'Name',
                                showAlways: true,
                                sortOrder: 0,
                                sortable: true,
                                layoutOrder: 2,
                                enableSearch: true,
                                cellHeaderTemplate: [
                                    '<label for="nameFilter" class="oui-a11y-hidden">Filter by Name</label> ',
                                    '<input type="text" ng-model="column.searchInput" id="nameFilter"/> '
                                ].join(''),
                                cellTemplate: '<span ng-bind="::record.name"> </span>',
                                dataType: 'text',
                                style: 'width:25%'
                            },
                            {
                                columnId: 'filename',
                                label: 'File Name',
                                showAlways: true,
                                sortOrder: 0,
                                sortable: true,
                                layoutOrder: 3,
                                enableSearch: true,
                                cellHeaderTemplate: [
                                    '<label for="filenameFilter" class="oui-a11y-hidden">Filter by File Name</label> ',
                                    '<input type="text" ng-model="column.searchInput" id="filenameFilter"/> '
                                ].join(''),
                                cellTemplate: '<span ng-bind="::record.filename"> </span>',
                                dataType: 'text'
                            },
                            {
                                columnId: 'action',
                                label: 'Action',
                                sortOrder: 0,
                                showAlways: true,
                               // sortable: true,
                                layoutOrder: 4,
                                enableSearch: true,
                                searchByItemType: 'exact',
                                cellTemplate: ['<a href="#" ng-click="model.fnDownLoad(record);" title="Download"><span  class="cux-icon-download">',
                                    '<span class="oui-a11y-hidden">Icon Person</span>',
                                    '</span></a>',
                                ].join('')

                            }

                        ],
//                        links: ['<a href="" ng-click="model.openMultiSortDrawer()" ng-hide="model.recordOperationInProgress" ><span class="cux-icon-sort"></span> Sort Columns </a> <span ng-show="model.recordOperationInProgress" aria-disabled="true" ><span class="cux-icon-sort"></span> Sort Columns</span> ',
//                        ],
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

        .controller('UitkCtrl', function($scope, $translate, breadcrumbNavigationViewModelRep, multiSortViewModelRep, globalSuccessMessageViewModel, globalErrorMessageViewModel) {


            $scope.apiItems = []
            $scope.callbackFunctionInController = function(argument) {
                console.log("callbackFunctionInController:" + argument);
            };
//            $scope.primaryNavigationModel = primaryNavViewModel;
//            $scope.secondaryNavigationModel = secondaryNavigationViewModel;
            $scope.breadcrumbViewModel5 = breadcrumbNavigationViewModelRep;

            $scope.data = [];
            $scope.multiSortModel1 = multiSortViewModelRep;
            $.ajax({
                method: 'GET',
                url: 'getReports.htm',
                async: false,
                contentType: 'application/json'

                , success: function(data) {
                    $scope.data = data;

                }
            });
            multiSortViewModelRep.records = $scope.data;
            multiSortViewModelRep.totalRecordsCount = $scope.data.length;

            $scope.globalSuccessMessageModel = globalSuccessMessageViewModel;
            $scope.globalErrorMessageModel = globalErrorMessageViewModel;

            $scope.apiItems = [{
                    attrib: 'id',
                    txt: 'text',
                    req: 'true',
                    desc: 'Id of global navigation'
                }, {
                    attrib: 'selectedIndex',
                    txt: 'Text',
                    req: 'true',
                    desc: 'This is the index of the tab that needs to be selected by default'
                }, {
                    attrib: 'id',
                    txt: 'Text',
                    req: 'false',
                    desc: 'Id of the tab panel'
                }, {
                    attrib: 'tabs',
                    txt: 'Array',
                    req: 'false',
                    desc: 'Array of Objects like {title: "title of a tab", templateurl:" url of a tab content " , disabled:" true / false", focusElement: "Selector expression of the element which need to be focused on activation. Tab content receives focus by default."}'
                }, {
                    attrib: 'text-template',
                    txt: 'text',
                    req: 'true',
                    desc: 'HTML Template for menu item'
                }, {
                    attrib: 'url',
                    txt: 'text',
                    req: 'false',
                    desc: 'Either #url for client routing or absolute/relative url for server redirect'
                }, {
                    attrib: 'disable-link',
                    txt: 'boolean',
                    req: 'false',
                    desc: 'Whether menu item is disabled or not'
                }, {
                    attrib: 'has-sub-menu',
                    txt: 'boolean',
                    req: 'false',
                    desc: 'whether menu has submenu or not'
                }, {
                    attrib: 'profile-name',
                    txt: 'boolean',
                    req: 'false',
                    desc: 'True if menu requires Person icon'
                }, {
                    attrib: 'callback-function',
                    txt: 'function',
                    req: 'false',
                    desc: 'Callback function for navigation link'
                }, {
                    attrib: 'output-model',
                    txt: '$scope variable. Array of objects.',
                    req: 'true',
                    desc: 'Will list all the selected items. If you enable grouping, this WILL NOT include the group headers. Also, this model is OUTPUT ONLY. If you want to programmatically change things, do it in the INPUT-MODEL.'
                }, {
                    attrib: 'button-label',
                    txt: 'String. Separate multiple values by space.',
                    req: 'true',
                    desc: 'input-model properties that you want to display on the button.'
                }, {
                    attrib: 'default-label',
                    txt: 'String',
                    req: 'true',
                    desc: 'Default Label for the dropdown component'
                },
                {
                    attrib: 'onExport',
                    txt: 'Function',
                    req: 'false',
                    desc: 'If mentioned it will activate "Export to CSV/XLS" link which will help download entire data set keeping filtering conditions in mind'
                },
                {
                    attrib: 'directive-id',
                    txt: 'String',
                    req: 'false',
                    desc: "Name or id for your directive, to be attached to a span element (parent of the button elemnet). Useful if you want to debug from within the directive code and you use more than one directive in one page. Example (from within the multi-select.js): console.log( 'Currently active multi-select: ' + $scope.directiveId );"
                }, {
                    attrib: 'helper-elements',
                    txt: 'String. Separate multiple values with space. Order of values does not matter.',
                    req: 'false',
                    desc: 'Define which helper buttons (Select all, none, reset, filter box) to be displayed.'
                }, {
                    attrib: 'is-disabled',
                    txt: '$scope boolean expression, or string ( "true" or "false" )',
                    req: 'false',
                    desc: 'Will disable or enable all checkboxes except stated otherwise in "disable-property" above. It\'s similar with ng-disabled.'
                }, {
                    attrib: 'item-label',
                    txt: 'String. Separate multiple values by space.',
                    req: 'true',
                    desc: 'input-model properties that you use as item label.'
                }, {
                    attrib: 'max-labels',
                    txt: 'Number',
                    req: 'true',
                    desc: 'Maximum number of items that will be displayed in the dropdown button. If not specified, will display all selected items.'
                }, {
                    attrib: 'orientation',
                    txt: 'String. Available values - "vertical" | "horizontal". Default value is "vertical".',
                    req: 'false',
                    desc: 'Orientation of the item list.'
                }, {
                    attrib: 'selection-mode',
                    txt: 'String. Available values - "single" | "multiple". Default value is "multiple".',
                    req: 'false',
                    desc: 'Single or multiple selection. If not specified, the default will be "multiple".'
                }, {
                    attrib: 'tick-property',
                    txt: 'String',
                    req: 'true',
                    desc: 'input-model property with a boolean value that represents whether a checkbox is ticked or not.'
                }, {
                    attrib: 'disable-property',
                    txt: 'String',
                    req: 'false',
                    desc: 'Input-model property with a boolean value that represent whether an item is disabled or enabled. This gives you granular control over each checkbox, and it has higher priority over the "is-disabled" attribute explained later.'
                }, {
                    attrib: 'max-height',
                    txt: 'Integer-parseable string or string which comply with CSS\' height unit (such as "100", "100px")',
                    req: 'false',
                    desc: 'Set the maximum height of the selection item list area.'
                }, {
                    attrib: 'input-width',
                    txt: 'Integer-parseable string or string which comply with CSS\' height unit (such as "100", "100px")',
                    req: 'true',
                    desc: 'Set the width of the input textbox of the dropdown.'
                }, {
                    attrib: 'flyoutwindow-width',
                    txt: 'Integer-parseable string or string which comply with CSS\' height unit (such as "100", "100px")',
                    req: 'true',
                    desc: 'Set the width of the selection item list area.'
                }, {
                    attrib: 'on-close',
                    txt: '$scope function',
                    req: 'false',
                    desc: 'A $scope function to call on multi-select close (be it by clicking the button or clicking outside the multi-select element). You need to define this function in your controller.'
                }, {
                    attrib: 'on-item-click',
                    txt: '$scope function',
                    req: 'true',
                    desc: 'A $scope function to call when user click on an item. Triggered AFTER the item is clicked. You need to define this function in your controller.'
                }, {
                    attrib: 'on-open',
                    txt: '$scope function',
                    req: 'true',
                    desc: 'A $scope function to call on multi-select open. You need to define this function in your controller.'
                },
                {
                    attrib: 'openShowHideColumnsDrawer',
                    txt: 'Function',
                    req: 'false',
                    desc: 'Opens show/hide columns drawer.'
                },
                {
                    attrib: 'onMultiSort',
                    txt: 'Function',
                    req: 'false',
                    desc: 'Called when Multi Sorting is applied. Default version calls onChange method.'
                }, {
                    attrib: 'multiSortColumns',
                    txt: 'Object',
                    req: 'false',
                    desc: 'It has two properties sortBy and sortOrder. sortBy is array which specifies list of columns for multi-column sort, sortOrder is array which specifies sort order(1 for ascending, -1 for descending) of columns specified in sortBy.'
                }, {
                    attrib: 'isMultiSortApplied',
                    txt: 'Boolean',
                    req: 'false',
                    desc: 'true if multi sort applied to table, default value is false'
                }, {
                    attrib: 'openMultiSortDrawer',
                    txt: 'Function',
                    req: 'false',
                    desc: 'Opens multi sort drawer'
                }, {
                    attrib: 'closeMultiSortDrawer',
                    txt: 'Function',
                    req: 'false',
                    desc: 'Closes multi sort drawer'
                }, {
                    attrib: 'saveMultiSortColumns',
                    txt: 'Function',
                    req: 'false',
                    desc: 'This function saves selected column of multi sort drawer to view model and call onChange method with multi sort parameter'
                }, {
                    attrib: 'showSuccessMessage',
                    txt: 'Function',
                    req: 'false',
                    desc: 'This function gets called when component needs to display success message(ex., When Multi-Column Sort applied to table)'
                },
                {
                    attrib: 'dialog-id',
                    txt: 'Text',
                    req: 'true',
                    desc: 'ID field for Dialog popup'
                },
                {
                    attrib: 'dialog-role',
                    txt: 'Text',
                    req: 'true',
                    desc: 'Used by the NVDA readers for accessibility. Role can be "alert" or "dialog". '
                },
                {
                    attrib: 'header-text',
                    txt: 'Text',
                    req: 'true',
                    desc: 'Header of the dialog popup'
                },
                {
                    attrib: 'show',
                    txt: 'Boolean',
                    req: 'true',
                    desc: 'Boolean variable used to toggle the display of dialog popup'
                },
                {
                    attrib: 'default-width',
                    txt: '%',
                    req: 'false',
                    desc: 'Used to mention the default width of the dialog popup'
                },
                {
                    attrib: 'default-height',
                    txt: '%',
                    req: 'false',
                    desc: 'Used to mention the default height of the dialog popup'
                },
                {
                    attrib: 'confirm-dialog',
                    txt: 'Boolean',
                    req: 'true',
                    desc: 'Show confirmation box when dialog is closed'
                },
                {
                    attrib: 'trigger-element',
                    txt: 'Text',
                    req: 'false',
                    desc: 'ID of the element triggers the dialog'
                },
                {
                    attrib: 'tk-aria-describedby',
                    txt: 'Text',
                    req: 'false',
                    desc: 'ARIA attribute to announce instructional text when mentioned for the dialog.'
                },
                {
                    attrib: 'call-back-hide',
                    txt: 'Function',
                    req: 'false',
                    desc: 'A callback method that is called while the dialog hides'
                },
                {
                    attrib: 'call-back-show',
                    txt: 'Function',
                    req: 'false',
                    desc: 'A callback method that is called when the dialog is shown'
                },
                {
                    attrib: 'tk-zindex',
                    txt: 'Number',
                    req: 'false',
                    desc: 'zIndex of the dialog'
                }
            ];

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

       