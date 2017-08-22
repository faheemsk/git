(function () {


    var uitkBarChartDirective = function ($window, uitkExceptionService) {
        return{
            restrict: 'E',
            replace: 'true',
            scope: {
                viewModel : '='
            },
            link: function (scope, element, attrs){

                var LABEL_PADDING_DESKTOP = 7;
                var LABEL_PADDING_TABLET = 11;
                var EXTRA_MARGIN_TABLET = 16;
                var TABLET_SCREEN_SIZE = 992;

                if(!scope.viewModel.id){
                    uitkExceptionService.throwException(
                        'InvalidIdException',
                        'Id is required attribute'
                    );
                }

                if(scope.viewModel.chartType !== 'horizontal' && scope.viewModel.chartType !== 'vertical'){
                    uitkExceptionService.throwException(
                        'NoChartTypeException',
                        'Chart type is mandatory'
                    );
                    return;
                }

                if(scope.viewModel.decimalPlaces === undefined){
                    scope.viewModel.decimalPlaces = 2;
                }

                scope.viewModel.heading = scope.viewModel.heading || scope.viewModel.id;
                scope.viewModel.description = scope.viewModel.description
                                              || 'This is a ' + scope.viewModel.chartType + ' chart component.';

                if(!scope.viewModel.width){
                   scope.viewModel.width = 600;
                }

                if(!scope.viewModel.height){
                    scope.viewModel.height = 400;
                }

                scope.contentKeyupHandler = function() {
                    scope.showMe = true;
                };

                scope.callBackHideDialog = function() {
                    scope.showMe = false;
                    angular.element('#'+scope.viewModel.id+'_dialog_openModalBtn').focus();
                };

                scope.mouseover = function(){
                    var tooltipElement = angular.element(".nvtooltip.xy-tooltip.nv-pointer-events-none");
                    if(tooltipElement.length !== 0){
                        tooltipElement.attr("aria-hidden","true");
                        scope.setAriaHidden = true;
                    }
                }

                scope.$watch('data', function (data){
                    nv.addGraph(function() {
                        var chart;

                        if (scope.viewModel.chartType === 'horizontal'){
                            chart = nv.models.multiBarHorizontalChart().groupSpacing(0.38)
                                .showValues(scope.viewModel.showValues);
                        }
                        else if (scope.viewModel.chartType === 'vertical'){
                            chart = nv.models.multiBarChart().groupSpacing(0.38)
                                .reduceXTicks(scope.viewModel.reduceXTicks)

                        }

                        chart.showControls(scope.viewModel.showControls)
                            .showLegend(scope.viewModel.showLegend)
                            .duration(scope.viewModel.duration ? scope.viewModel.duration : 100);

                        if(scope.viewModel.margin) {
                            chart.margin(scope.viewModel.margin);
                        }

                        if(typeof scope.viewModel.xContent === 'function') {
                            chart.x(function(d) {
                                return scope.viewModel.xContent(d);
                            });
                        } else {
                            chart.x(function(d) {
                                return d.label;
                            });
                        }

                        if(typeof scope.viewModel.yContent === 'function') {
                            chart.y(function(d) {
                                return scope.viewModel.yContent(d);
                            });
                        } else {
                            chart.y(function(d) {
                                return d.value;
                            });
                        }

                        if(typeof scope.viewModel.yAxisFormat === 'function') {
                            chart.yAxis.tickFormat(scope.viewModel.yAxisFormat());
                        }

                        if(typeof scope.viewModel.xAxisFormat === 'function') {
                            chart.xAxis.tickFormat(scope.viewModel.xAxisFormat());
                        }

                        chart.xAxis.axisLabel(scope.viewModel.xAxisLabel);
                        chart.yAxis.axisLabel(scope.viewModel.yAxisLabel);

                        chart.yAxis.tickPadding(LABEL_PADDING_DESKTOP);
                        chart.xAxis.tickPadding(LABEL_PADDING_DESKTOP);

                        if(scope.viewModel.margin) {
                            var leftMargin = scope.viewModel.margin.left;
                            var leftMarginTab = scope.viewModel.margin.left + EXTRA_MARGIN_TABLET;
                        }

                        if($window.innerWidth < TABLET_SCREEN_SIZE){
                            chart.yAxis.tickPadding(LABEL_PADDING_TABLET);
                            chart.xAxis.tickPadding(LABEL_PADDING_TABLET);

                            updateLeftMargin(leftMarginTab, chart);
                        }

                        if(typeof scope.viewModel.tooltipContent === 'function') {
                            chart.tooltip.contentGenerator(function(key, x, y, e, graph) {
                                return scope.viewModel.tooltipContent(key, x, y, e, graph);
                            });
                        }

                        chart.tooltip.enabled(scope.viewModel.tooltips);

                        chart.width(scope.viewModel.width).height(scope.viewModel.height);
                        angular.element('#'+scope.viewModel.id)
                            .css({'min-width':scope.viewModel.width, 'min-height':scope.viewModel.height});

                        if(typeof scope.viewModel.chartOptions === 'function'){
                            scope.viewModel.chartOptions(chart);
                        }

                        angular.element($window).on('resize', function (){
                            if($window.innerWidth < TABLET_SCREEN_SIZE){
                                chart.yAxis.tickPadding(LABEL_PADDING_TABLET);
                                chart.xAxis.tickPadding(LABEL_PADDING_TABLET);

                                updateLeftMargin(leftMarginTab, chart);
                            }
                            else{
                                chart.yAxis.tickPadding(LABEL_PADDING_DESKTOP);
                                chart.xAxis.tickPadding(LABEL_PADDING_DESKTOP);

                                updateLeftMargin(leftMargin, chart);
                            }
                        });

                        d3.select('#' + scope.viewModel.id +' svg').datum(scope.viewModel.data)
                            .attr('width', scope.viewModel.width)
                            .attr('height', scope.viewModel.height)
                            .call(chart);

                        nv.utils.windowResize(chart.update);

                        return chart;
                    });
                });

                function updateLeftMargin(leftMargin, chart){
                    if(scope.viewModel.margin) {
                        scope.viewModel.margin.left = leftMargin;
                        chart.margin(scope.viewModel.margin);
                    }
                }
            },
            templateUrl: 'template/uitkBarChartTemplate.html'
        };
    };

    uitkBarChartDirective.$inject = ['$window','uitkExceptionService'];

    angular.module('uitk.component.uitkBarChart', ['uitk.component.uitkButton','uitk.component.uitkDialog','uitk.uitkUtility'])
        .directive('uitkBarChart', uitkBarChartDirective);
})();

angular.module("uitk.component.uitkBarChart").run(["$templateCache", function($templateCache) {$templateCache.put("template/uitkBarChartTemplate.html","<div class=\"tk-barchart\" id=\"{{viewModel.id}}\">\n\n    <uitk:dialog dialog-id=\'{{viewModel.id}}_dialog\' dialog-role=\'alertdialog\'\n                 header-text=\'{{viewModel.heading}} Chart Data\'\n                 show=\'showMe\'\n                 ng-if=\"showMe\"\n                 call-back-hide=\"callBackHideDialog()\"\n                 confirm-dialog=\"false\" tk-aria-describedby=\"dialogInsTextId\"\n                 default-width=\'25%\' default-height=\"51%\" trigger-element=\'#{{viewModel.id}}_dialog_openModalBtn\'>\n        <div class=\"tk-margin-bottom-1t\" id=\"{{viewModel.id}}_data\" tabindex=\"-1\">{{viewModel.description}}</div>\n        <table aria-describedby=\"{{viewModel.id}}_data\">\n            <tr>\n                <th scope=\"col\" class=\"tk-accessible-chart-label-column\">Label</th>\n                <th scope=\"col\">Value</th>\n            </tr>\n            <tr ng-repeat=\"item in viewModel.data[0].values\">\n                <td class=\"tk-accessible-chart-label-column\">{{item.label}}</td>\n                <td class=\"tk-accessible-chart-value-column\">{{item. value| number:viewModel.decimalPlaces }}</td>\n            </tr>\n        </table>\n        <uitk:button type=\"button\" style=\"float:left;\" value=\"Close\" enable-default=\"true\"\n                      ng-click=\"callBackHideDialog();\" custom-class=\'uitk-width-6t uitk-btn-close-dialog\'></uitk:button>\n\n    </uitk:dialog>\n\n    <div id=\"{{viewModel.id}}_label\" tabindex=\"-1\"><h2>{{viewModel.heading}}</h2></div>\n    <span class=\"oui-a11y-hidden\" id=\"{{viewModel.id}}_desc\">Simple {{viewModel.chartType}} bar chart.  Select the “Show data for this chart” button for data in table format.</span>\n    <button id=\"{{viewModel.id}}_dialog_openModalBtn\" class=\"tk-barchart-showdata-button\" ng-click=\"contentKeyupHandler()\">Show data for this chart</button>\n    <svg aria-hidden=\"true\" ng-mouseover=\"!setAriaHidden && mouseover();\" aria-labelledby=\"{{viewModel.id}}_label\" aria-describedby=\"{{viewModel.id}}_desc\" role=\"img\" alt={{viewModel.description}}></svg>\n</div>");}]);