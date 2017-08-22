angular.module('uitk.component.uitkPanel',['uitk.component.uitkSlideAnimation','uitk.component.uitkNavigable','uitk.uitkUtility'])
.directive('uitkPanel', ["$compile", "uitkExceptionService", function($compile, uitkExceptionService){

		return {
		restrict : 'E',
		replace : true,
		scope : {
			model : '=',
            scope : '='
		},
		template: [
		           "<div class='tk-panl {{model.styleClass}}' id='{{model.id}}' ng-class='openStyle' ng-style=\"{'width': '{{model.panelWidth}}'}\" >",
		           "<div class='tk-panl-header {{model.headerClass}} tk-panl-header-w-actions' ng-click='togglePanel($event)'>",
		           		"<h2 ng-if='model.title && !model.titleH3'>",
			           		"<a ng-if='model.collapsible' class='tk-panl-helper' href='#' aria-expanded='{{model.open}}' aria-controls='{{model.id}}Content'>",
			           			"<span ng-if='model.open' class='cux-icon-carrot_down'></span>",
			           			"<span ng-if='!model.open' class='cux-icon-carrot_right'></span>",	
			           			"<span uitk-panel-compile-header='model.title'></span>",
			           		"</a>",
			           		"<span ng-if='!model.collapsible'><span uitk-panel-compile-header='model.title'></span></span>",
		           		"</h2>",
		           		"<h3 ng-if='model.title && model.titleH3'>",
			           		"<a ng-if='model.collapsible' class='tk-panl-helper' href='#' aria-expanded='{{model.open}}' aria-controls='{{model.id}}Content'>",
				           		"<span ng-if='model.open' class='cux-icon-carrot_down'></span>",
				           		"<span ng-if='!model.open' class='cux-icon-carrot_right'></span>",
				           		"<span uitk-panel-compile-header='model.title'></span>",
			           		"</a>",
			           		"<span ng-if='!model.collapsible'><span uitk-panel-compile-header='model.title'></span></span>",
			           	"</h3>",
			           	"<ul ng-show='model.showActionLinks' ng-click='$event.stopPropagation();'>",
			           		"<li ng-repeat='link in model.links' class='liclass'>",
				           		"<a ng-hide='link.disabled' ng-href='{{link.url}}' ng-click='linkClick(link, $event);' uitk-navigable='true'>",
				           			"<span uitk-panel-compile-link-text='link'></span>",
								"</a>",
								"<span ng-hide='!link.disabled' aria-disabled='true' role='link' class='tk-panl-disabled-link {{link.iconClass}}'><span uitk-panel-compile-link-text='link'></span></span>",
			           		"</li>",
			           	"</ul>",
			       "</div>",
			       "<div uitk-slide-show='model.open' uitk-slide-show-duration='500' class='tk-panl-content-wrapper'>",
			       		"<div class='tk-panl-content {{model.contentClass}}' ng-class=\"{'tk-panl-content-overflow-auto':model.panelHeight || model.panels}\" ",
                        "ng-style=\"{'max-height':'{{model.panelHeight}}','width':'{{model.panelWidth}}'}\" id='{{model.id}}Content' >",
			       			"<ng-include ng-if='!model.panels' src=\"model.templateUrl\" class='panelClass' ></ng-include>",
			       			"<uitk-panel ng-repeat='panel in model.panels' model='panel' class='panelClass'>{{panel.title}}</uitk-panel>",
			       		"</div>",
			       "</div>",
			       "</div>"].join(""),			       
		controller: ['$scope', '$element', function ($scope) {

			$scope.togglePanel = function(event) {
				if ($scope.model.collapsible === undefined || $scope.model.collapsible === false) 
					return;
				event.preventDefault();
				event.stopPropagation();
				$scope.model.open = !$scope.model.open;
			}

			$scope.linkClick = function(link){
				if(link.callBack){
					link.callBack.call();
				}
			}

		}],
		compile: function(tElement) {
			var contents = tElement.contents().remove();
			var compiledContents;
			return function($scope, iElement) {
				if(!compiledContents) {
					compiledContents = $compile(contents);
				}
				compiledContents($scope, function(clone) {
					iElement.append(clone);
				});

			if(!$scope.model){
                uitkExceptionService.throwException('EmptyModelException','Model is undefined');
	        }

			if(!$scope.model.id){
                uitkExceptionService.throwException('InvalidIdException','Id is required attribute');
	        }

			if(!$scope.model.title){
                uitkExceptionService.throwException('InvalidTitleException','Title is required attribute');
	        }

			if($scope.model.links){
				_.forEach($scope.model.links, function(link){
					if(!link.text){
                        uitkExceptionService.throwException('InvalidLinkTextException','Link Text is required attribute');
					}
				});
			}

						if($scope.model.links && $scope.model.showActionLinks === undefined){
				$scope.model.showActionLinks = true;
			}

			if($scope.model.open === undefined){
				$scope.model.open = false;
			}

						if ($scope.model.collapsible === true) {
				if ($scope.model.open === true) {
					$scope.openStyle =  'tk-panl-open';
				} else {
					$scope.openStyle =  'tk-panl-closed';
				}
			} else {
				 var headerBlock = iElement[0].querySelector('.tk-panl-header');
				 if(headerBlock){
					 headerBlock.removeAttribute("ng-click");
				 }
			}
		}

				}
	};
}])
.directive('uitkPanelCompileHeader', ["$compile", function ($compile) {
	  return function($scope, $element) {
		  $compile($scope.model.title)($scope, function(clone){
	    	if(!clone.selector) {
	    		$element.append(clone);
	    	} else {
	    		$element.append(clone.selector);
	    	}
	    });
	  };
}])
.directive('uitkPanelCompileLinkText', ["$compile", function ($compile) {
	  return function($scope, $element) {
		  $compile($scope.link.text)($scope, function(clone){
	    	if(!clone.selector) {
			  $element.append(clone);
	    	} else {
	    		$element.append(clone.selector);
	    	}
	    });
	  };
}]);