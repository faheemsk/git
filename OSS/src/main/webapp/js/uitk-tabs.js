(function () {
    var TABS_SWITCH_TIMEOUT = 100;
    var uitkTabs =  function () {
        return {
            restrict: 'E',
            replace: true,
            transclude: true,
            scope:{
                tkModel:'='
            },
            link: function($scope, element, attrs, ctrl){

                if(!$scope.tkModel.id) {
                    $scope.tkModel.id='tabs_'+ctrl.randomId(6);
                }

                $scope.isSelected = function(index){
                    if($scope.tkModel.selectedIndex === index){
                        return true;
                    }
                    return false;
                }
            },
            controller: ["$scope", "$timeout", function($scope, $timeout) {

                $scope.selectTab = function (tab, index) {
                    if(!tab.disabled) {
                        angular.element('#' + $scope.tkModel.id + '_tab' + $scope.tkModel.selectedIndex + '_tab').attr('aria-selected',false);

                        if($scope.tkModel.selectedIndex !== index) {
                            var prevActiveElem = angular.element('div.tk-tpnl ul li')[$scope.tkModel.selectedIndex];
                            angular.element(prevActiveElem).removeClass('tk-tpnl-selected');
                        }

                        angular.element('#' + $scope.tkModel.id + '_tab' + index + '_tab').attr('aria-selected',true);

                        $scope.tkModel.selectedIndex = index;

                        if(tab.focusElement) {
                            $timeout(function(){
                                angular.element(tab.focusElement).focus();
                            }, TABS_SWITCH_TIMEOUT);
                        } else {
                            $timeout(function(){
                                var tabContentPanel = angular.element('#'+$scope.tkModel.id+'_tab'+index+'_tabpanel');
                                tabContentPanel.focus();
                            }, TABS_SWITCH_TIMEOUT);
                        }


                    }
                };

                $scope.tkModel.selectedIndex = ($scope.tkModel.selectedIndex % $scope.tkModel.tabs.length) || 0;
                var index = $scope.tkModel.selectedIndex;
                var tab = $scope.tkModel.tabs[index];
                for(var i=index; tab.disabled && i<$scope.tkModel.tabs.length; i++) {
                    index = i;
                    tab = $scope.tkModel.tabs[i];
                }
                if(!tab.disabled) {
                    $scope.tkModel.selectedIndex = index;
                    $scope.templateUrl = tab.templateurl;
                    $scope.selectTab($scope.tkModel.tabs[$scope.tkModel.selectedIndex], $scope.tkModel.selectedIndex);
                }

                this.randomId = function (length) {
                    var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz'.split('');
                    if (! length) {
                        length = Math.floor(Math.random() * chars.length);
                    }
                    var str = '';
                    for (var i = 0; i < length; i++) {
                        str += chars[Math.floor(Math.random() * chars.length)];
                    }
                    return str;
                };

                $scope.tabKeyupHandler = function(pane, index, event){
                    var pressedKey = event.keyCode;

                    switch(pressedKey) {
                        case 39:
                        case 40:
                            var nextTab;
                            var count = 0;
                            if($scope.tkModel.selectedIndex !== index) {
                                var prevActiveElem = angular.element('div.tk-tpnl ul li')[index];
                                angular.element(prevActiveElem).removeClass('tk-tpnl-selected');
                            }
                            do { 
                                index = (index + 1) % $scope.tkModel.tabs.length;
                                nextTab = $scope.tkModel.tabs[index];
                                count++;
                            } while(nextTab.disabled && count <= $scope.tkModel.tabs.length);
                            angular.element('#' + $scope.tkModel.id + '_tab' + index + '_tab').focus();
                            var activeElem = angular.element('div.tk-tpnl ul li')[index];
                            angular.element(activeElem).addClass('tk-tpnl-selected');
                            break;
                        case 37:
                        case 38:
                            var previousTab;
                            var count = 0;
                            if($scope.tkModel.selectedIndex !== index) {
                                var prevActiveElem = angular.element('div.tk-tpnl ul li')[index];
                                angular.element(prevActiveElem).removeClass('tk-tpnl-selected');
                            }
                            do { 
                                index = (index - 1 >= 0) ? index - 1 : $scope.tkModel.tabs.length-1;
                                previousTab = $scope.tkModel.tabs[index];
                                count++;
                            } while(previousTab.disabled && count <= $scope.tkModel.tabs.length);
                            angular.element('#' + $scope.tkModel.id + '_tab' + index + '_tab').focus();
                            var activeElem = angular.element('div.tk-tpnl ul li')[index];
                            angular.element(activeElem).addClass('tk-tpnl-selected');
                            break;
                    };
                };

                $scope.contentKeyupHandler = function(index, event) {
                    if (event.ctrlKey && event.keyCode === 38) { 
                        $timeout(function(){
                            angular.element('#' + $scope.tkModel.id + '_tab' + index + '_tab').focus();
                        });
                    }
                };

                $scope.$watch('tkModel.selectedIndex',function(){
                    $scope.templateUrl = $scope.tkModel.tabs[$scope.tkModel.selectedIndex].templateurl;
                });

            }],
            template:[
                '<div id="{{tkModel.id}}" class="tk-tpnl" aria-describedby="{{tkModel.id}}_desc">',
                '<div class="oui-a11y-hidden" id="{{tkModel.id}}_desc" aria-hidden="false" tabindex="-1">Use arrow keys to move between tabs, enter or spacebar to activate</div>',
                '<ul role="tablist" aria-label="{{tkModel.ariaLabel}}">',
                '<li role="presentation" ng-class="{\'tk-tpnl-selected\':isSelected($index), \'tk-tpnl-disabled\':pane.disabled}" ng-repeat="pane in tkModel.tabs" >',
                '<span id="{{tkModel.id+\'_tab\'+$index+\'_tab\'}}" role="tab" ng-click="selectTab(pane,$index)" aria-selected="false"',
                'aria-expanded="{{tkModel.selectedIndex === $index}}" ng-keyup="tabKeyupHandler(pane, $index, $event);" aria-disabled="{{pane.disabled}}" uitk-navigable="!pane.disabled" tabindex="{{(tkModel.selectedIndex === $index)?0:-1}}" aria-controls="{{tkModel.id+\'_tab\'+$index+\'_tabpanel\'}}">{{ pane.title }}</span>',
                '</li>',
                '</ul>',
                '<div class="tk-tpnl-content" role="presentation">',
                    '<div id="{{tkModel.id+\'_tab\'+$index+\'_tabpanel\'}}" ng-keyup="contentKeyupHandler($index, $event);" ng-keydown="contentKeydownHandler( $event);" aria-labelledby="{{tkModel.id+\'_tab\'+$index+\'_tab\'}}" tabindex="{{isSelected($index)?0:-1}}" role="tabpanel"  ng-include="templateUrl">',
                '</div>',
                '</div>',
                '</div>'
            ].join('')
        };
    };

    angular.module('uitk.component.tabs',['uitk.component.uitkNavigable'])
        .directive('uitkTabs', uitkTabs);
})();