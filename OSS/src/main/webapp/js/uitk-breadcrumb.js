
(function () {
    var uitkBreadcrumbDirective = function($sce, $timeout, uitkExceptionService){
        return {
            restrict : 'E',
            replace : true,
            transclude : false,
            controller: ["$scope", "$element", function($scope,$element){

            }],
            link: function(scope, element) {
                if(!scope.viewModel.id){
                    uitkExceptionService.throwException('InvalidIdException','Id is required attribute');
                }

                scope.linksLength = scope.viewModel.links.length;
                scope.showAllItems = true;

                scope.getTrustedTextTemplate = function(template) {
                    return $sce.trustAsHtml(template);
                };

                scope.show = function(){
                    scope.showAllItems = true;
                    $timeout(function(){
                        angular.element('li a').eq(scope.maxLinks - 2).focus();
                    });
                };

                scope.$watch('viewModel.links', function(newValue){
                    if(!newValue){
                        scope.linksLength = newValue.length;
                        scope.showEllipses();
                    }
                },true);

                scope.showEllipses = function(){
                    scope.showAllItems = true;
                    $timeout(function(){

                        var elementWidth = element[0].getBoundingClientRect().width;
                        var liElements = element.find('li');
                        var lastLiIndex = scope.viewModel.links.length - 1;

                        var array = new Array();
                        _.forEach(liElements, function(element){
                            array.push(element.getBoundingClientRect());
                        });

                        var index = 2;

                        var widthSum = array[0].width + array[1].width + array[lastLiIndex].width + array[lastLiIndex-1].width + 48; 
                        while(widthSum < elementWidth && index <= lastLiIndex-2){
                            widthSum = widthSum + array[index].width;
                            index = index + 1;
                        }
                        if((scope.viewModel.links.length == 4) || (index == scope.viewModel.links.length - 2 && widthSum < elementWidth)){
                            scope.showAllItems = true;
                        } else {
                            scope.maxLinks = index+1;
                            scope.showAllItems = false;
                        }
                    });
                }
                scope.showEllipses();
            },
            scope : {
                viewModel:"="
            },
            template: ['<ul class="oui-crum" role="navigation" id="{{viewModel.id}}">',
                            '<li ng-repeat="link in viewModel.links" ng-if="($index < 2 || $index < (maxLinks - 2)) && $index < (linksLength - 2)"><a ng-href="{{link.url}}" ng-bind-html="getTrustedTextTemplate(link.template);"></a></li>',
                            '<li ng-if="!showAllItems && linksLength > maxLinks"><a href="" ng-click="show();"><span aria-hidden="true">[...]</span><span class="oui-a11y-hidden">Click to expand link items</span></a></li>',
                            '<li ng-repeat="link in viewModel.links" ng-if="showAllItems && $index >= 2 && $index >= (maxLinks - 2) && $index <=(linksLength- 3)"><a ng-href="{{link.url}}" ng-bind-html="getTrustedTextTemplate(link.template);"></a></li>',
                            '<li ng-repeat="link in viewModel.links" ng-if="$index >=(linksLength - 2)"><a ng-href="{{link.url}}" ng-if="!$last" ng-bind-html="getTrustedTextTemplate(link.template);"></a><span ng-if="$last" ng-bind-html="getTrustedTextTemplate(link.template);"></span></li>',
                        '</ul>'
            ].join('')
        };
    };

    uitkBreadcrumbDirective.$inject = ['$sce','$timeout','uitkExceptionService'];

    angular.module('uitk.component.uitkBreadcrumb',['uitk.uitkUtility'])
        .directive('uitkBreadcrumb', uitkBreadcrumbDirective);
})();
