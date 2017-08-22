angular.module('uitk.component.uitkPrimaryNavigation', ['uitk.component.uitkSlideAnimation','uitk.component.uitkNavigable','uitk.uitkUtility'])
.directive('uitkPrimaryNavigation', ['$compile','$document','$timeout','$location','$sce','$filter','uitkExceptionService',function($compile,$document,$timeout,$location,$sce,$filter, uitkExceptionService){

		function controller($scope){
		$scope.model.level = $scope.$parent.model ? $scope.$parent.model.level + 1 : 1;

		var nonDisabledLinks = $scope.model.links.filter(function(link){ return !link.disabled});
		var firstNonDisabledLink = nonDisabledLinks[0];
		firstNonDisabledLink.firstLinkInDropDown = true;
		var lastNonDisabledLink = nonDisabledLinks[nonDisabledLinks.length - 1];
		lastNonDisabledLink.lastLinkInDropDown = true;

				if($scope.model.level === 1){

			$scope.model.menuVisible = true;
			$document.on("click", function ($event) {
				 if(!$event.target.keepPrimaryDropdownFlag){
					 $scope.$apply(function(){
						 $scope.hideAllMenus();
					 });
				 }
		     });


									var touchInterface = 'ontouchstart' in window || navigator.msMaxTouchPoints;
			 if(!touchInterface){
				 $timeout(function(){
					 var totalWidth = $(".tk-pnav li:last").offset().left -  $(".tk-pnav li:first").offset().left + $(".tk-pnav li:last").width();
					 var reducedWidth = 2;
					 var dLeft = $('.tk-pnav li:first').offset().left;
					 var dWidth = $('.tk-pnav li:first').width() + reducedWidth;
					 $('.tk-pnav-hover-div').css({visibility:"hidden"});
					 $('.tk-pnav-hover-div').stop().animate({left:dLeft, width:dWidth},{duration:'slow'});

									$(".tk-pnav > li").on("mouseover",function(){
						var position = $(this).offset().left;
						if($('.tk-head').length > 0){
							position = position - $('.tk-head').offset().left - 1;
						}
						var width = $(this).width()+ reducedWidth; 
						$('.tk-pnav-hover-div').css({visibility:"visible"});
						$('.tk-pnav-hover-div').stop().animate({left:position, width:width},{duration:'slow'});
					});

					$(".tk-pnav").mouseleave(
						function(){
							$('.tk-pnav-hover-div').css({visibility:"hidden"});
						}		
					);

					 				 },1000);
			 }

		}

		$scope.hideAllMenus = function(){
			var linkWithDropDowns = $scope.model.links;

			while(linkWithDropDowns.length > 0) {
				linkWithDropDowns = linkWithDropDowns.filter(function(link){ return link.dropDown; }); 
				var allDropdownUnderLink = linkWithDropDowns.map(function(link){ return link.dropDown; }); 
				allDropdownUnderLink.forEach(function(dropDown) { dropDown.menuVisible = false; }) 
				linkWithDropDowns = allDropdownUnderLink
				 						.filter(function(dropDown){ return dropDown.links; })
				 						.map(function(dropDown){ return dropDown.links; })
				 						.reduce(function(allLink, links){ return allLink.concat(links);}, []);
			}
		};

				$scope.expandMenuOrRedirectToLink = function($event, item){
			$event.target.keepPrimaryDropdownFlag = true;

			var linksWithDropDown = $scope.model.links.filter(function(link) { return link !== item; }).filter(function(link){ return link.dropDown;});
			linksWithDropDown.forEach(function(link) {
				link.dropDown.menuVisible = false;
				link.dropDown.links.forEach(function(link) {
					if(link.dropDown) {
						link.dropDown.menuVisible = false;
					}
				});
			});

			if(item.dropDown) {
				item.dropDown.menuVisible = item.dropDown.menuVisible?!item.dropDown.menuVisible:true;

								if(item.dropDown.menuVisible) { 
					item.dropDown.setOnFocus(); 
					if(item.dropDown.level === 3){
						$scope.checkMenuPosition();
					}
				}
				else{
					item.dropDown.links.forEach(function(subItem) {
						if(subItem.dropDown){
							subItem.dropDown.menuVisible = false;
						}
					});
				}
			}

			if(item.url){
				if(item.url[0] === '#'){
					$location.path(item.url.substring(1));
				}
				else{
					window.location = item.url;
				}

				if($scope.model.level > 1){
					var scope = $scope;
					while(scope.$parent.model !== undefined){
						scope = scope.$parent;
					}

					scope.model.links.forEach(function(link){link.selected = false;});
					scope.model.links.forEach(function(link){if(link.dropDown && link.dropDown.menuVisible){link.selected = true;}});
				}
				else{
					$scope.model.links.forEach(function(link){link.selected = false;});
					item.selected = true;
				}

				scope = $scope;
				while(scope.model.level !== 1 ){
					scope.model.menuVisible = false;
					scope = scope.$parent.$parent.$parent;
				}

				$('.tk-pnav-hover-div').css({visibility:"hidden"});

			}

					};	

				$scope.hideParentMenu = function(event, item) {
            if ( event.which === 27 ) {
                if ( $scope.model.level > 1 ) {
                    $scope.model.menuVisible = false; 
                    $scope.focusItem.focusMe = true;

                }
            }

			else if($scope.model.level > 1 && (event.which === 9) && !event.shiftKey){
				if(item.lastLinkInDropDown ) {
					if(!item.dropDown) {
						$scope.model.menuVisible = false;
						if($scope.$parent.$parent.item.lastLinkInDropDown) {
							$scope.$parent.$parent.$parent.model.menuVisible= false;
						}
					}	
					else if(!item.dropDown.menuVisible) {  
						$scope.model.menuVisible = false;
					}	
				}
			}
			else if($scope.model.level > 1 && (event.which === 9) && event.shiftKey && item.firstLinkInDropDown) {
				$scope.model.menuVisible = false;
			}
		};

				$scope.isExpanded = function(item){
			if(item.dropDown){
				if(item.dropDown.menuVisible){
					return true;
				}
				else { 
					return false;
				}
			}
			else{
				return undefined;
			}
		};

				$scope.getTrustedTextTemplate = function(item) {
            return $sce.trustAsHtml(item.textTemplate);
        };

		if ($scope.model.level > 1 && $scope.focusItem) {
            $scope.focusItem.focusMe = false;
        }
	}
	controller.$inject = ["$scope"];

	return {
		restrict : 'E',
		replace : true,
		transclude : true,
		scope : {
			model : '=',
            focusItem : '='
		},
		controller : controller,
		compile:function(tElement) {
            var contents = tElement.contents().remove();
            var compiledContents;
            return function(scope, iElement) {

            	if(!compiledContents) {
                    compiledContents = $compile(contents);
                }
                compiledContents(scope, function(clone, scope) {
                         iElement.append(clone); 
                         if(scope.model.level === 1){ 
                        	 iElement.after("<div id=\"pnavhorizontalslider\" class=\"tk-pnav-hover-div\"></div>");
                         }
                });

				scope.model.links.forEach(function(link){ if(link.url && link.dropDown){
					uitkExceptionService.throwException('InvalidLinkException','Link can not have both url and dropDown element');
				}});

                                scope.model.setOnFocus = function() {
        			$timeout(function(){ iElement.find('a')[0].focus(); }, 750) ;
        		};

        		        		scope.model.links.forEach(function(link,index){
        			link.setFocus = function(){
        				var otherElement = iElement.find('ul a');
        				$timeout(function(){ iElement.find('a').not(function(index, element){
        					return _.includes(otherElement, element);
        				})[index].focus(); }) ;
        			}
        		});

        		        		scope.checkMenuPosition = function(){
        			var elementRightPosition = iElement[0].getBoundingClientRect().right;
        			var elementWidth = iElement[0].getBoundingClientRect().width;
        			var screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
        			if(screenWidth < elementRightPosition + elementWidth){
        				scope.model.links.forEach(function(link){if(link.dropDown){link.dropDown.menuPosition = true;}})
        			}
        		};
            };
        },
		template : [
					'<ul ng-class=\'{"tk-pnav-submenu-open": model.menuVisible,"tk-pnav-to-the-left": model.menuPosition, "tk-pnav": model.level===1 }\' uitk-slide-show="model.menuVisible" uitk-slide-show-duration="500">',
					'	<li ng-repeat="item in model.links" ng-class=\'{"tk-pnav-selected": item.selected, "tk-pnav-disabled": item.disabled}\' ng-if="!item.hidden">',
					'	<a href="javascript:void(0)" ng-if="!item.disabled" ng-click="expandMenuOrRedirectToLink($event,item)" ng-keydown="hideParentMenu($event, item)" uitk-compile-link="item" apply-parent-focus>',
                    '   <span ng-if="item.dropDown && model.level >= 1" class="oui-a11y-hidden">Has Submenu.</span>',
                    '   <span ng-if="item.dropDown && model.level === 1" class="cux-icon-carrot_down"></span>',
                    '       <span ng-if="item.dropDown && model.level === 2" class="cux-icon-carrot_right"></span>',
                    '       <span class="oui-a11y-hidden">',
                    '       <span ng-if="item.dropDown && model.level >= 1 && isExpanded(item)">Expanded</span>',
                    '       <span ng-if="item.dropDown && model.level >= 1 && !isExpanded(item)">Collapsed</span>',
                    '   </span>',
					'   </a>',
					'	<span ng-click="$event.stopPropagation();" role="link" aria-disabled="true" ng-if="item.disabled" ng-bind-html="getTrustedTextTemplate(item)"></span>',
					'	<uitk-primary-navigation ng-if="item.dropDown" model="item.dropDown" focus-item="item"></uitk-primary-navigation>',
					'	</li>',
					'</ul>'
		           ].join('')

			};
}])

    .directive('applyParentFocus', function() {
        return {
            link : function(scope, element) {
                if ( scope.item ) {
                    scope.$watch('item.focusMe', function(a, b) {
                        if ( scope.item.focusMe ) {
                            element.focus();
                            scope.item.focusMe = false;
                        }
                    })
                }
            }
        }
    })

.directive('uitkCompileLink', ["$compile", function ($compile) {
	  return function($scope, $element) {
		  $compile($scope.item.textTemplate)($scope, function(clone){
			  if(!clone.selector){
				  $element.prepend(clone);
			  }
			  else{
				  $element.prepend(clone.selector);
			  }
	    });
	  };
}]);
