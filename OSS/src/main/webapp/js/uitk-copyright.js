angular.module('uitk.component.uitkCopyright',['uitk.uitkUtility'])
.directive('uitkCopyright', function(){
	return {
		scope : {
			licensesUrl : '@',
			licensesInfoForReader : '@' 
		},
		restrict : 'E',
		replace : true,
		link: function($scope){
			$scope.currentYear = new Date().getFullYear();
		},
		template: "<p class='tk-copy'>&copy; {{currentYear}} Optum, Inc. - {{'All Rights Reserved.'| uitkTranslate }}"+
				"{{\"Your use of this product is governed by the terms of your company's agreement.\"| uitkTranslate}}"+
				"{{'You may not use or disclose this product,' | uitkTranslate}} " +
				"{{'or allow others to use it or disclose it, except as permitted by your agreement with' | uitkTranslate}} Optum. " +
				"{{'License information can be found here:' | uitkTranslate}} <a href='{{licensesUrl}}' " +
				"target='_blank'>{{'Licenses' | uitkTranslate}}<span class=\"oui-a11y-hidden\">{{licensesInfoForReader}}</span></a>.</p>"
	};
});



