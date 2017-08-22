
var radioGroupApp = angular.module('uitk.component.uitkRadioGroup', []);
radioGroupApp
    .directive('uitkRadio', ["$compile", function ($compile) {
        return {
            restrict: 'E',
            replace: true,
            scope: {
                itemList: '=',
                groupName: '@',
                modelValue: '=',
                tkDescribedby: '=',
                tkLabelledby: '@',
                onChange: '&'
            },
            transclude: true,
            template: "<div><div role='group' aria-describedby='{{tkDescribedby}}' aria-labelledby='{{tkLabelledby}}'>" +
                        "<ul class='tk-form-radio'>" +
                        "<li ng-repeat='item in itemList' class='template-list-item'>" +
                        "<input type='radio' id='{{groupName + $index }}' ng-change='onChange();'  name='{{groupName}}' ng-model='$parent.modelValue'" +
                        "ng-disabled='item.disabled'   ng-attr-aria-disabled='{{item.disabled?true:undefined}}' ng-value='{{$index}}'/> " +
                        "<label for='{{groupName + $index }}' tabindex='-1'>{{item.label}}</label>" +
                        "</li>" +
                       "</ul></div></div>",
            link: function (scope, element, attrs, ctrl, transclude) {
                if (scope.itemList == undefined) {
                    var radioContents = transclude().filter(function (index, el) { return el.localName == "uitk:radio-content"; });
                    for (var i = 0; i < radioContents.length; i++) {
                        var tempTemplate = angular.element("<li class='template-list-item'>" +
                                        "<input class='compiled-list-item' type='radio' id='{{groupName}}" + i + "' ng-change='onChange();'  name='{{groupName}}' ng-model='modelValue'" +
                                        "ng-disabled='" + ($(radioContents[i]).attr('uitk-disabled') == "true" ? true : false) + "' ng-attr-aria-disabled='" + ($(radioContents[i]).attr('disabled') == true ? true : false) + "' ng-value='{{" + i + "}}'/> " +
                                        "<label for='{{groupName}}" + i + "'>" + $(radioContents[i]).attr('tk-label') + "</label>" +
                                        "</li>");
                        tempTemplate.find('label').parent().append(radioContents[i]);
                        element.find('ul').append($compile(tempTemplate)(scope));
                    }
                }
            }
        };
    }])
    .directive('uitkRadioContent', function () {
        return {
            restrict: 'AE'
        };
    });
