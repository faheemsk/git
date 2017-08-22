angular.module('uitk.component.header', ['uitk.component.uitkNavigable','uitk.uitkUtility'])
.directive('uitkHeader', ["$location", function($location) {
	function controller($scope){
		$scope.redirectUrl = function(){
			if($scope.url){
				if($scope.url[0] === '#'){
					$location.path($scope.url.substring(1));
				}
				else{
					window.location = $scope.url;
				}
			}
		}
	}
	controller.$inject = ["$scope"];;

	    return {
      restrict: 'E',
      transclude: true,
      scope: {
    	  logo: "@",
    	  url: "@",
    	  altText: "@"

          },
      template: '<header class="tk-head" role="banner"><div class="tk-head-logo"><a ng-click="redirectUrl()" ng-href="{{url}}"><img ng-src="{{logo}}" alt="{{altText | uitkTranslate}}" /></a></div><div ng-transclude></div></header>',
      replace: true,
     controller: controller

          };
}]);

