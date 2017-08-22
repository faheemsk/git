/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


angular.module("UitkApp", ['uitk.component.uitkGlobalNavigation','uitk.component.uitkCheckboxGroup', 'uitk.component.header',
    'uitk.component.uitkPhiConfirmationDialog','uitk.component.sessionTimeout',
    'uitk.component.uitkPrimaryNavigation',
    'uitk.component.uitkBreadcrumb', 'uitk.component.tabs', 'uitk.component.uitkTextField',
    'uitk.component.uitkLabel', 'uitk.component.uitkButton', 'uitk.component.uitkFooter',
    'uitk.component.uitkCopyright', 'uitk.uitkUtility', 'uitk.component.uitkSecondaryNavigation',
    'uitk.component.uitkMultiSelect', 'uitk.click', 'uitk.component.uitkDynamicTable',
    'uitk.api', 'pascalprecht.translate', 'uitk.dynamicTableApiList',
    'uitk.component.uitkAccordion', 'uitk.component.uitkDialog',
    'uitk.component.uitkPhiConfirmationDialog', 'uitk.component.uitkTextField',
    'uitk.component.uitkLabel', 'uitk.component.uitkButton', 'uitk.component.uitkSelect',
    'uitk.component.uitkBarChart', 'ng-fusioncharts','uitk.component.uitkMessage'])

        .factory('primaryNavViewModel', function() {
            var resDATA = [];
            $.ajax({
                type: "GET",
                url: "getEngagement.htm",
                async: false,
                contentType: 'application/json',
                cache: false,
                success: function(data) {

                    angular.forEach(data, function(value, index) {
                        //  alert("1"+value.engagmentName);
                        resDATA.push({textTemplate: '<span>' + value.engagmentName + '</span>', 
                            url: 'switchEngagment.htm?engType=' + value.engagementPkgType 
                                    + '&engKey=' + value.engagementKey+ '&pkgName=' + value.engagementPkgName})
                    })

                }
            });
            return {
                id: 'PrimaryNavigation',
                links: [
                    {
                        textTemplate: '<span>Engagements</span>', selected: 'true',
                        dropDown: {
                            links: resDATA
                                    //menuVisible
                                    //menuPosition
                        }
                    },
                ]
            };
        })

        .factory('secondaryNavigationViewModel', function() {
            var headDATA = [];
            var assessmentData = [];

            $.ajax({
                type: "GET",
                url: "getAssessments.htm",
                async: false,
                contentType: 'application/json',
                cache: false,
                success: function(data) {
                    angular.forEach(data, function(value, index) {
//                        alert("2"+value.serviceName);
                        assessmentData.push({textTemplate: '<span>' + value.serviceName + '</span>', url: value.actionURL})
                    })

                }
            });
            $.ajax({
                type: "GET",
                url: "getHeaders.htm",
                async: false,
                contentType: 'application/json',
                cache: false,
                success: function(data) {
                    angular.forEach(data, function(value, index) {
//                         alert("3"+value.headerName);
                        if (value.headerName === "Assessments") {
                            headDATA.push({textTemplate: '<span>' + value.headerName + '</span>', dropDown: {links: assessmentData}})
                        } 
//                        else if(value.headerName === "Summary") {
//                            headDATA.push({textTemplate: '<span>' + value.headerName + '</span>', url: value.actionName, selected : true})
//                        } 
                        else{
                             headDATA.push({textTemplate: '<span>' + value.headerName + '</span>', url: value.actionName});
                        }
                    })

                }
            });

            return {
                id: 'SecondaryNavigation',
                links:
                        headDATA
            }
        })

        

        .controller('IRMaasDHeadCtrl', function($scope, primaryNavViewModel,
                secondaryNavigationViewModel) {

            $scope.primaryNavigationModel = primaryNavViewModel;
            $scope.secondaryNavigationModel = secondaryNavigationViewModel;
        });
        
   function makeid() {
    var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    for (var i = 0; i < 5; i++)
        text += possible.charAt(Math.floor(Math.random() * possible.length));

    return text;
}

$(document).ready(function() {
    var uid = makeid();
    var fieldHTML = '<div><input type="hidden" name="mnval" value="' + uid + '"/></div>';
    $("form").each(function() {
        $(this).append(fieldHTML);
    });
});

function dashboardHeaderFn(action) {
    document.dashboardHeaderfrm.action = action;
    document.dashboardHeaderfrm.submit();
}