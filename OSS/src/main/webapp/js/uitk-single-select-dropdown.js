
(function () {
    var uitkSelectDirective = function (uitkExceptionService) {
        return {
            restrict: 'E',
            replace: true,
            transclude: true,
            link: function (scope) {

                if (scope.itemList.length == 0) {
                    uitkExceptionService.throwException('ListItemLengthCannotBeZero','List item array length cannot be zero');
                }

                if (scope.checkFieldValidation === undefined) {
                    scope.checkFieldValidation = false;
                }

                scope.$watch("selectedValue", function (newValue, oldValue) {
                    if (!(newValue === scope.itemList[0])) {
                        scope.checkFieldValidation = true;
                    } else {
                        scope.checkFieldValidation = false;
                    }
                }, true);

                if (!scope.selectedValue) {
                    scope.selectedValue = scope.itemList[0];
                }

            },
            scope: {
                itemList: '=', 
                selectedValue: '=', 
                tkRequired: '@',     
                tkErrorClass: '=',
                onChange: '&',
                tkName:'@',
                checkFieldValidation: '=' 
            },
            template: function (ele,attr) {

                                if (!attr.name) {
                    return "<select ng-model='selectedValue'  name='{{tkName}}' ng-change='onChange(selectedValue)'  class='tk-sngl-dpwn' ng-style=\"{'width': 'auto'}\" ng-options='item.label disable when item.isDisabled for item in itemList' ng-attr-aria-invalid='{{tkErrorClass === undefined ? false : tkErrorClass}}' n-required='(tkRequired && checkFieldValidation) ? true : false' aria-required='{{(tkRequired ) ? true : false}}'>" +
                     "</select>"
                }
               return "<select ng-model='selectedValue' ng-change='onChange(selectedValue)'  class='tk-sngl-dpwn' ng-style=\"{'width': 'auto'}\" ng-options='item.label disable when item.isDisabled for item in itemList' ng-attr-aria-invalid='{{tkErrorClass === undefined ? false : tkErrorClass}}' ng-required='(tkRequired && checkFieldValidation) ? true : false' aria-required='{{(tkRequired ) ? true : false}}'>" +
            "</select>"

            }
        };
    };

    uitkSelectDirective.$inject = ['uitkExceptionService'];

    angular.module('uitk.component.uitkSelect',['uitk.uitkUtility'])
        .directive('uitkSelect', uitkSelectDirective);
})();
