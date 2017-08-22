angular.module('uitk.component.uitkFooter', ['uitk.component.uitkNavigable'])
.directive('uitkFooter', ["$filter", "$compile", function($filter, $compile) {
    return {
      restrict: 'E',
      replace: true,
      transclude : true,
      link : function($scope) {
    	  $scope.currentYear = new Date().getFullYear();
          angular.element('footer > div').prepend($compile('<p> &copy; '+$scope.currentYear+' Optum, <span translate>Inc.</span> <span translate>All rights reserved.</span></p>')($scope));

      },
      template: '<footer role="contentinfo"><div ng-transclude ></div></footer>'
    };
}])
.directive('uitkFooterLinks', function(){
	return {
		restrict : 'E',
		replace : true,
		transclude : true,
		template : '<ul class="tk-foot-links" ng-transclude> </ul>'
	};
})

.directive('uitkFooterLink', function(){
	return {
		restrict : 'E',
		replace : true,
		scope : {
			url : '@',
			tkLabel : '@',
			tkDisabled : '=',
			tkTarget : '@',
			linkInfoForReader : '@' 
		},
		link: function(scope, element) {
			var tkTarget = scope.tkTarget;
			scope.disabledA11y = true;
	        if(tkTarget) {
	        	scope.tkTargetWindow = "_"+tkTarget;
	        	if(tkTarget === "blank"){
	        		scope.disabledA11y = false;
	        	}
	        }
		},
		template : [
					'<li>',
					'	<a ng-if="!tkDisabled" href="{{url}}" target="{{tkTargetWindow}}">{{tkLabel}} <span ng-if="!disabledA11y" class="oui-a11y-hidden">{{linkInfoForReader}}</span></a>',
					'   <span ng-if="tkDisabled" class="tk-foot-link-disabled">{{tkLabel}}</span>',
					'</li>'
		           ].join('')
	};
})
.directive('uitkFooterText', function(){
	return {
		restrict : 'E',
		replace : true,
		transclude : true, 
		template: '<p class="tk-foot-text" ng-transclude ></p>'
	};
});
