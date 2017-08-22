angular.module('uitk.component.uitkGlobalNavigation', ['uitk.component.uitkSlideAnimation', 'uitk.component.uitkNavigable','uitk.uitkUtility'])
.directive('uitkGlobalNavigation', ["$document", function($document){

		function controller($scope, uitkTools, $attrs, $element){
		$scope.name = "GlobalNavigation";
		$scope.links = [];

		$scope.addLink = function(link) {
			$scope.links.push(link);
		}

	}
	controller.$inject = ["$scope", "uitkTools", "$attrs", "$element"];

	function link($scope, $element, $attrs, ctrl, $transclude){

				 $scope.setSelectedUrl = function(scope){$scope.selectedUrl = scope;};

		 		 $scope.getSelectedUrl = function(){return $scope.selectedUrl;};


		$transclude($scope, function(content) {
			$element.append(content);
		});
		 $document.on("click", function ($event) {
			 if(!$event.target.keepGlobalDropdownFlag){
				 $scope.$apply(function(){
					 $scope.hideAllMenus();
				 });
			 }
	     });

		 		 $scope.hideAllMenus = function(){
				 var linkWithDropDowns = $scope.links;
				 while(linkWithDropDowns.length > 0) {
					 linkWithDropDowns = linkWithDropDowns.filter(function(link){ return link.hasOwnProperty('dropDown') }); 
					 var allDropdownUnderLink = linkWithDropDowns.map(function(link){ return link.dropDown; }); 
					 allDropdownUnderLink.forEach(function(dropDown) { dropDown.menuVisible = false; }) 
					 linkWithDropDowns = allDropdownUnderLink
					 						.filter(function(dropDown){ return dropDown.hasOwnProperty('links') })
					 						.map(function(dropDown){ return dropDown.links; })
					 						.reduce(function(allLink, links){ return allLink.concat(links);}, []);
				 }

				 				 if($scope.getSelectedUrl()){
						$scope.getSelectedUrl().selected = false;
				}
		 };
	}

		return {
		restrict : 'E',
		replace : true,
		transclude : true,
		scope : true,
		template : "<ul class='tk-gnav'></ul>",
		controller : controller,
		link : link
	};
}])
.directive('uitkGlobalNavigationPlainText', function(){
	function controller($scope, uitkTools, $attrs, $element){
		$scope.plainText = '';

		$scope.addPlainText = function(text) {
			$scope.plainText = text;
		}
	}
	controller.$inject = ["$scope", "uitkTools", "$attrs", "$element"];
	function link($scope, $element, $attrs, ctrl, $transclude){
		["textTemplate","profileName"].forEach(function(attr) {
			$scope[attr] = $attrs[attr];
		});
		$scope.addPlainText($scope);
		$transclude($scope, function() {
			$element.find('div').replaceWith($scope.textTemplate);
		});
	}

	return {
		restrict : 'E',
		replace : true,
		transclude : true,
		require : '^uitkGlobalNavigation',
		scope : true,
		templateUrl: function (element, attrs) {
			return attrs.templateUrl || 'template/plainTextContent.html';
		},
		link : link,
		controller: controller
	};
})
.directive('uitkGlobalNavigationLink', ["$timeout", "$location", "$sce", "uitkExceptionService", function($timeout, $location,$sce, uitkExceptionService){

		function controller($scope, uitkEvents, $attrs, $element, uitkTools){

			if($scope.url && $scope.hasSubMenu) {
			   uitkExceptionService.throwException("UnsupportedLinkException","Link cannot have both URL and HasSubMenu attributes");
			}

				$scope.hrefPlaceHolder = function() {
		};

		$scope.addDropDown = function(dropDown) {
			$scope.dropDown = dropDown;
		}





				$scope.expandMenuOrRedirectToLink = function($event){
			$scope.$eval($scope.callbackFunction);

			$event.target.keepGlobalDropdownFlag = true;

			if($scope.$parent.hasOwnProperty('name') && $scope.name === "GlobalNavigation"){
				if($scope.getSelectedUrl()){
					$scope.getSelectedUrl().selected = false;
				}
				$scope.setSelectedUrl($scope);
				$scope.selected = true;
			}

			var linksWithDropDown = $scope.links.filter(function(link) { return link !== $scope; }).filter(function(link){ return link.hasOwnProperty('dropDown');});
			linksWithDropDown.forEach(function(link) {

				link.dropDown.links.forEach(function(link) {

					if(link.dropDown) {
						link.dropDown.menuVisible = false;
					}
				});
				link.dropDown.menuVisible = false;
			});
			if($scope.hasOwnProperty('dropDown')) {
				$scope.dropDown.menuVisible = !$scope.dropDown.menuVisible;
				if($scope.dropDown.menuVisible) {
					$scope.dropDown.links[0].setOnFocus();
				}
				else {
					$scope.dropDown.links.forEach(function(dropDown) {
						dropDown.dropDown.menuVisible = false;
					});

					if($scope.getSelectedUrl()){
						$scope.getSelectedUrl().selected = false;
					}
				}
			}

			if($scope.url){
				if($scope.url[0] === '#'){
					$location.path($scope.url.substring(1));
				}
				else {
					window.location = $scope.url;
				}

			}

			if($scope.callbackFunction !== undefined){
				$scope.hideAllMenus();
			}
		};

				$scope.hasChildSubMenu = function() {
			return $scope.hasOwnProperty('dropDown') ? true :false;
		}


				$scope.hasExpanded = function(){
			if(!$scope.hasOwnProperty('dropDown'))
			{
				return undefined;
			}
			return $scope.dropDown.menuVisible;
		}

		$scope.hideParentMenu = function(event){
			if(event.which === 27) {
				var focus = $scope.$parent.setOnFocus();
				if($scope.$parent.hasChildSubMenu()) {
					if(focus instanceof Function) {
						$scope.$parent.setOnFocus();
						$scope.dropDown.menuVisible = false;
					} else {
						$scope.$parent.$parent.setOnFocus();
						$scope.$parent.dropDown.menuVisible = false;
					}
				}
			}
			if(event.which === 9) {
				if(!event.shiftKey && $scope.hasOwnProperty('lastLinkInDropDown') && $scope.lastLinkInDropDown && !$scope.hasOwnProperty('dropDown')){
					$scope.dropDown.menuVisible = false;

					if($scope.$parent.$parent.$parent.hasOwnProperty('name') && $scope.getSelectedUrl()){
						$scope.getSelectedUrl().selected = false;
					}

					if($scope.$parent.lastLinkInDropDown) {
						$scope.$parent.$parent.$parent.menuVisible= false;
						if($scope.$parent.$parent.$parent.$parent.$parent.hasOwnProperty('name') && $scope.getSelectedUrl()){
							$scope.getSelectedUrl().selected = false;
						}
					}
				} else if(!event.shiftKey && $scope.hasOwnProperty('lastLinkInDropDown') && !$scope.lastLinkInDropDown && !$scope.dropDown.menuVisible) {  
					$scope.$parent.dropDown.menuVisible = false;
					if($scope.$parent.$parent.$parent.hasOwnProperty('name') && $scope.getSelectedUrl()){
						$scope.getSelectedUrl().selected = false;
					}
				} else if(event.shiftKey && $scope.hasOwnProperty('firstLinkInDropDown') && $scope.firstLinkInDropDown ) {

					$scope.$parent.menuVisible = false;

					if($scope.$parent.$parent.$parent.hasOwnProperty('name') && $scope.getSelectedUrl()){
						$scope.getSelectedUrl().selected = false;
					}
				}
				else if(!event.shiftKey && $scope.hasOwnProperty('lastLinkInDropDown') && $scope.lastLinkInDropDown) {
					$scope.$parent.menuVisible = false;
				}
			}
 		};

 			    $scope.getTrustedTextTemplate = function() {
            return $sce.trustAsHtml($scope.textTemplate);
        };
 	}
 	controller.$inject = ["$scope", "uitkEvents", "$attrs", "$element", "uitkTools"];


	function link($scope, $element, $attrs, ctrl, $transclude){
	    [ "textTemplate", "url" , "disableLink", "hasSubMenu", "profileName","callbackFunction"].forEach (function(attr) { $scope[attr] = $attrs[attr]; } );
		$scope.setOnFocus = function() { 
			$timeout(function(){
				$element.find('a')[0].focus();
			}, 750);
		};
	    $scope.addLink($scope);

	    $transclude($scope, function(content) {
			$element.find('div').replaceWith(content);
		});
	}

		return {
		restrict : 'E',
		replace : true,
		transclude : true,
		require : '^uitkGlobalNavigation',
		scope : true,
		templateUrl: function (element, attrs) {
			return attrs.templateUrl || 'template/linkContent.html';
		},
		controller : controller,
		link : link
	};
}])
.directive('uitkGlobalNavigationDropDown', function(){
	function controller($scope, uitkTools, $attrs, $element){
		$scope.links = [];
		$scope.menuVisible = false;
		$scope.menuPosition = false;
		$scope.addLink = function(link) {
			$scope.links.push(link);
		}
	}
	controller.$inject = ["$scope", "uitkTools", "$attrs", "$element"];

	function link($scope, $element, $attrs, ctrl, $transclude){
		$scope.addDropDown($scope);
		$transclude($scope, function(content) {
			$element.append(content);
		});

		var nonDisabledLinks = $scope.links.filter(function(link){ return !link.disableLink});
		var firstNonDisabledLink = nonDisabledLinks[0];
		firstNonDisabledLink.firstLinkInDropDown = true;
		var lastNonDisabledLink = nonDisabledLinks[nonDisabledLinks.length - 1];
		lastNonDisabledLink.lastLinkInDropDown = true;

				$scope.checkMenuPosition = function(){
			var elementRightPosition = $element[0].getBoundingClientRect().right;
			var elementWidth = $element[0].getBoundingClientRect().width;
			var screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
			if(screenWidth < elementRightPosition + elementWidth){
				$scope.dropDown.links.forEach(function(link){if(link.hasOwnProperty('dropDown')){link.dropDown.menuPosition = true;}})
			}
		}
	}

		return {
		restrict : 'E',
		replace : true,
		transclude : true,
		require : '^uitkGlobalNavigationLink',
		scope : true,
		templateUrl: function (element, attrs) {
			return attrs.templateUrl || 'template/dropDownContent.html';
		},
		controller : controller,
		link : link
	};
})
.directive('uitkCompileTextTemplate', ["$compile", function ($compile) {
	  return function($scope, $element) {
		  $compile($scope.textTemplate)($scope, function(clone){
	    	if(!clone.selector) {
			  $element.append(clone);
	    	} else {
	    		$element.append(clone.selector);
	    	}
	    });
	  };
}]);
angular.module("uitk.component.uitkGlobalNavigation").run(["$templateCache", function($templateCache) {$templateCache.put("template/dropDownContent.html","<ul ng-class=\'{\"menuVisible\": menuVisible, \"tk-gnav-to-the-left\": menuPosition}\' uitk-slide-show=\'menuVisible\' uitk-slide-show-duration=\'500\'></ul>");
$templateCache.put("template/linkContent.html","<li ng-class=\"{\'hasSubMenu\': hasSubMenu || hasChildSubMenu() ,\'disabled\': disableLink}\">\n    <a href=\"javascript:void(0)\" ng-class=\"{\'select-active\': selected}\" uitk-navigable=\"disableLink !== \'true\'\" ng-if=\"disableLink !== \'true\'\" ng-click=\"expandMenuOrRedirectToLink($event)\" ng-keydown=\"hideParentMenu($event)\">\n        <span ng-if=\"profileName\" ng-class=\"{\'cux-icon-person\': profileName}\" aria-hidden=\"true\"></span>\n        <span uitk-compile-text-template=\"textTemplate\"></span>\n        <span ng-if=\"hasSubMenu || hasChildSubMenu()\">\n            <span class=\"oui-a11y-hidden\">Has Submenu.</span>\n            <span aria-hidden=\"true\">\n                <span ng-if=\"hasSubMenu\">\n                    <span class=\"cux-icon-carrot_down\"></span>\n                </span>\n                <span ng-if=\"hasChildSubMenu() && !hasSubMenu\">\n                    <span class=\"cux-icon-carrot_right\"></span>\n                </span>\n            </span>\n            <span class=\"oui-a11y-hidden\">\n                <span ng-if=\"!hasExpanded()\">Collapsed</span>\n                <span ng-if=\"hasExpanded()\">Expanded</span>\n            </span>\n        </span>\n    </a>\n    <a href=\"javascript:void(0)\" ng-click=\"$event.stopPropagation();\" ng-if=\"disableLink === \'true\'\" ng-class=\"{\'disabled\': disableLink}\" aria-disabled=\"{{disableLink}}\" uitk-compile-text-template=\"textTemplate\"></a>\n    <div></div>\n</li>");
$templateCache.put("template/plainTextContent.html","<li class=\'tk-nav-text\'>\n    <span>\n        <span ng-if=\'profileName\' ng-class=\'{\"cux-icon-person\": profileName}\' aria-hidden=\'true\'></span>\n        <div uitk-compile-text-template=\'textTemplate\'></div>\n    </span>\n</li>");}]);