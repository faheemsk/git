/*
 *** DO NOT MODIFY THE COPYRIGHT HEADER MANUALLY ***
 * Copyright (c) Optum 2015 - All Rights Reserved.
 *** Release process will update accordingly ***
 * @version 3.8.0
 */
angular.module('uitk.component.uitkSlideAnimation',[])
.directive(
            "uitkSlideShow",
            function() {
                
                function link( $scope, element, attributes ) {
                    var expression = attributes.uitkSlideShow;  // truthy expression to watch.
                    var durationType = parseInt(attributes.uitkSlideShowDuration, 10);
                    var duration = (angular.element.isNumeric(durationType))? durationType: ( attributes.uitkSlideShowDuration || "fast" ); // default duration to fast if slide show duration is not provided.
                   
                   
                    
                    //default display of the element based on the link time value of the model we are watching.
                    
                    if ( ! $scope.$eval( expression ) ) {
                        element.hide();
                    }
                    
                    //watch for the expression 
                    $scope.$watch(
                        expression,
                        function( newValue ) {
                            if ( newValue ) {
                                element
                                    .stop( true, true )
                                    .slideDown( duration );
                            // Hide element.
                            } else {
                                element
                                    .stop( true, true )
                                    .slideUp( duration );
                            }
                        }
                    );
                }

                // Return the directive configuration.
                return({
                    link: link,
                    restrict: "A"
                });

            }
        );


 

