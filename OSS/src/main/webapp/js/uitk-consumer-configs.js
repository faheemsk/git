/*
 *** DO NOT MODIFY THE COPYRIGHT HEADER MANUALLY ***
 *** Release process will update accordingly ***
 * Copyright (c) Optum 2015 - All Rights Reserved.
 * @version 3.8.0
 */
(function () {
    'use strict';

    var uitkConsumerConfigs = function () {
        //################################################-CONFIGS STATIC OBJECT-###############################
        var configs = {
            LOGCONFIG: {
                //set the error level to be used by the $log factory. See uitk-logger for error levels, set to info/test/debug for internal debugging
                errorLevel: "test",
                //log4javascript configuration for logging.
                log4javascriptConfig: {
                    'error': [
                             /*{
                                 appender: 'AjaxAppender'
                                 //todo: Specify the url if you are saving errors.
                                 //loggingServiceUrl: "/" + location.href.split('/')[3] + "/log"
                             },*/
                             {
                                 appender: 'BrowserConsoleAppender',
                                 pattern: "[%-5p] %d %c - %m%n"
                             }
                    ],
                    'all': [
                                 {
                                     appender: 'BrowserConsoleAppender',
                                     pattern: "[%-5p] %d %c - %m%n"
                                 }
                    ]
                }
            },
            USEEVENTS: { //control whether events are used globally or individually. Option set to give end-consumer maximum flexibility.
                all: true,
                accordion: true,
                authorization: true,
                autocomplete: true,
                button: true,
                calendar: true,
                checkboxGroup: true,
                dialog: true,
                dialogProgressBar: true,
                dynamicTable: true,
                fileUpload: true,
                footer: true,
                globalNavigation: true,
                grids: true,
                header: true,
                help: true,
                label: true,
                license: true,
                message: true,
                multiSelectDropdown: true,
                panel: true,
                phiConfirmation: true,
                picklist: true,
                primaryNavigation: true,
                progressBar: true,
                radioSelectGroup: true,
                secondaryNavigation: true,
                sessionTimeout: true,
                settingMenu: true,
                singleSelectDropdown: true,
                tabs: true,
                textarea: true,
                texteditor: true,
                textfield: true,
                tooltip: true,
                tree: true,
                verticalNavigation: true,
                formLayout:true

            }, //when true events will be broadcast by all components.
            SUPPORTEDBROWSERS : {
                'Internet Explorer': [9,10,11],
                'Chrome' : [48],
                'Firefox': [38,44],
                'iPad'   : [1,2,3,4,5,6,7,8,9],
                'Opera'  : [28],
                'Safari' : [8]
            },
            UNSUPPORTEDBROWSERS : {
                'Internet Explorer':[4,5,6,7,8],
                'Safari': [4]
            }

        }
        //################################################-CONFIGS GET/SET METHODS-###############################
        /**
         * 
         * @param {string} level - info/verbose/debug
         */
        this.setErrorLevel = function (level) {
            configs.LOGCONFIG.errorLevel = level;
        }

        /**
         * Set supported and unsupported browsers
         * @param supportedBrowsers
         * @param unsupportedBrowsers
         */
        this.setBrowsers = function(supportedBrowsers, unsupportedBrowsers) {
            if ( _.isObject(supportedBrowsers) ) {
                configs.SUPPORTEDBROWSERS = supportedBrowsers;
            }
            if ( _.isObject(unsupportedBrowsers) ) {
                configs.UNSUPPORTEDBROWSERS = unsupportedBrowsers;
            }
        }

        /**
         * 
         * @param {object} log4javascriptConfig - appenders defined by log4javascript errorlevel info/warn/debug/fatal/error
         */
        this.setLog4JavaScriptConfig = function (log4javascriptConfig) {
            configs.LOGCONFIG.log4javascriptConfig = log4javascriptConfig;
        }

        /**
         * 
         * @param {string} component - name of component to be changed
         * @param {bool} state - true/false
         */
        this.setEventActiveByComponent = function (component,state) {
            configs.USEEVENTS[component] = state;
        }
        this.$get = function () {
            return configs
        }
        return this;
    }

    angular.module('uitk.uitkConfigs', [])
    .provider('uitkConsumerConfigs', uitkConsumerConfigs);
})();