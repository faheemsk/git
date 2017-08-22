/*
 * Copyright (c) Optum 2015 - All Rights Reserved.
 */

(function () {

    /**
     *
     * @returns {{restrict: string, transclude: boolean, scope: {heading: string, tkOptions: string}, controller: Function, template: string}}
     */
    var uitkApi = function($window) {
        return {
            restrict: 'E',
            transclude: true,
            scope: {heading: '@',tkOptions:'='},
            controller: function($scope) {

                var panels = $scope.panels = [];

                $scope.tkOptions = $scope.tkOptions || {};
                if($scope.tkOptions.showHomeQaLink === undefined){
                    $scope.tkOptions.showHomeQaLink = true;
                }

                $scope.select = function(panel) {
                    angular.forEach(panels, function(panel) {
                        panel.selected = false;
                    });
                    panel.selected = true;
                };

                this.addPanel = function(panel) {
                    if (panels.length === 0) {
                        $scope.select(panel);
                    }
                    panels.push(panel);
                };

                //Checks if demo page is home-qa.html or home-qa-dev.html, based on that it will set url
                $scope.relativePathPrefix = "../../";
                $scope.url = "../../home-dev.html";
                if($window.location.pathname.indexOf("/dist") > -1){
                    $scope.relativePathPrefix = "../../../";
                    $scope.url = "../../../home-qa.html";
                }
            },
            template:
            '<link rel="stylesheet" ng-href="{{relativePathPrefix}}uitk-core/css/uitk_dev-only.css" >'+
            '<h1>{{heading}}</h1>' +
            '<a ng-href="{{url}}" class="tk-margin-bottom-1t" ng-show="tkOptions.showHomeQaLink">Go to home page</a>' +
            '<div class="tk-api"><a ng-repeat="panel in panels" href="" ng-class="{\'tk-api-selected\':panel.selected}" ng-click="select(panel)">{{panel.label}}</a></div>'
            +'<div ng-transclude></div>'
        };
    };

    /**
     *
     * @returns {{require: string, restrict: string, transclude: boolean, scope: {label: string}, link: Function, template: string}}
     */
    var uitkApiPanel = function() {
        return {
            require: '^uitkApi',
            restrict: 'E',
            transclude: true,
            scope: {
                label: '@'
            },
            link: function(scope, element, attrs, aCtrl) {
                aCtrl.addPanel(scope);
            },
            template: '<div ng-show="selected" ng-transclude></div>'
        };
    };

    /**
     *
     * @returns {{restrict: string, scope: {heading: string, items: string}, template: string}}
     */
    var uitkApiGrid = function() {
        return {
            restrict: 'E',
            scope: {heading:'@', items: '='},
            template:
            '<div class="tk-padding-bottom-1t">'+
            '<h3>{{heading}}</h3>'+
            '<div class="tk-data-table-container">'+
            '  <table>'+
            '    <thead>'+
            '      <tr>'+
            '        <th>Attribute</th>'+
            '        <th>Value</th>'+
            '        <th>Required</th>'+
            '        <th>Description</th>'+
            '      </tr>'+
            '    </thead>'+
            '    <tbody>'+
            '      <tr ng-repeat="a in items">'+
            '        <th scope="row">{{a.attrib}}</td>'+
            '        <td>{{a.txt}}</td>'+
            '        <td>{{a.req}}</td>'+
            '        <td>{{a.desc}}</td>'+
            '      </tr>'+
            '    </tbody>'+
            '  </table>'+
            '</div>'+
            '</div>'
        };
    };

    angular.module('uitk.api', [])
        .directive('uitkApi', uitkApi)
        .directive('uitkApiPanel', uitkApiPanel)
        .directive('uitkApiGrid', uitkApiGrid)
})();