/*
 *** DO NOT MODIFY THE COPYRIGHT HEADER MANUALLY ***
 * Copyright (c) Optum 2015 - All Rights Reserved.
 *** Release process will update accordingly ***
 * @version 3.8.0
 */
angular.module('uitk.component.uitkNavigable',[])
.directive('uitkNavigable', function($parse){
	
    function link($scope, $element, $attrs) {
       
		var isNavigable = true;
		if($attrs.uitkNavigable){
		    isNavigable = $parse($attrs.uitkNavigable)($scope) ? true : false;
     	}
        $scope.keysToTrigger = ($attrs.keyCodes) ? JSON.parse($attrs.keyCodes) : [];
		if (isNavigable) {
			$element.attr('tabindex', 0);
			if($element.attr('ng-click')) {
				var fn = $parse($element.attr('ng-click'));
				$element.on('keydown', function (event) {
				    if (event.which === 13 || event.which === 32 || angular.element.inArray(event.which, $scope.keysToTrigger) > -1) { // enter or space key is pressed
						$scope.$apply(function() {fn($scope, {$event:event});});
						event.preventDefault();
					}
				});
			}
		}
	}
	
	return {
		restrict : 'A',
		replace : false,
		scope : false,
		link : link
	};
});