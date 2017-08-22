/*
 *** DO NOT MODIFY THE COPYRIGHT HEADER MANUALLY ***
 *** Release process will update accordingly ***
 * Copyright (c) Optum 2015 - All Rights Reserved.
 * @version 3.8.0
 */
(function () {
    var EventsProto = function (scope, emit, broadcast, internalBroadcast) {
        this.scope = scope;
        this.emit = emit;
        this.broadcast = broadcast;
        this.internalBroadcast = internalBroadcast;
    };

    /**
     * uitkEvents wraps broadcasting service to purpose of controlling if on or off.
     */
    var uitkEvents = function (uitkConsumerConfigs, $rootScope, $log) {
        //NOTE: $on is not implemented here since it doesn't effect the configs

        var supressLog = (uitkConsumerConfigs.LOGCONFIG.errorLevel === 'test');

        //check to see if all events are turn off.
        if (!uitkConsumerConfigs.USEEVENTS.all) {
            return { setScope: function () { return false; } };
        }
        //define the scope
        var scope = $rootScope;
        /**
         * quick short hand
         * @param {object} object
         * @returns {string} 
         */
        var toString = function (object) {
            function replacer(key, value) {
                if (key && key.startsWith && (key.startsWith("$") || key === "parent" || key === "children")) {
                    return "---";
                }
                return value;
            };

            var str = JSON.stringify(object, replacer);

            return str;
            //return angular.toJson(object);
        };
        /**
         * if in verbose or debug mode show additional info
         * @param {object} object
         * @returns {string} 
         */
        var debugging = function (object) {
            if ("verbose" === uitkConsumerConfigs.LOGCONFIG.errorLevel || "debug" === uitkConsumerConfigs.LOGCONFIG.errorLevel) {
                return toString(object); //angular.toJson(object);
            } else {
                return "";
            }
        };

        /**
        * braodcast 
        * @param {string} componentName - The name of the component one is broadcasting as.
        * @param {string} name - name of the event to broadcast
        * @param {*} args - Optional one or more arguments which will be passed onto the event listeners
        * @param {*} debugDetails - additional verbose info passed in about how component is being used. 
        */
        var emit = function (componentName, name, args, debugDetails) {
            if (!uitkConsumerConfigs.USEEVENTS[componentName]) {
                return null;//broadcast for this componentsName is disabled.
            }

            if (!supressLog) {
                $log.info(componentName, " Emit event: ", name, toString(args), debugging(debugDetails));
            }
            return scope.$emit(name, args);
        };

        /**
         * braodcast 
         * @param {string} componentName - The name of the component one is broadcasting as.
         * @param {string} name - name of the event to broadcast
         * @param {*} args - Optional one or more arguments which will be passed onto the event listeners
         * @param {*} debugDetails - additional verbose info passed in about how component is being used. 
         */
        var broadcast = function (componentName, name, args, debugDetails) {
            if (!uitkConsumerConfigs.USEEVENTS[componentName]) {
                return null;//broadcast for this componentsName is disabled.
            }
            if (!supressLog) {
                $log.info(componentName, " Broadcast event: ", name, toString(args), debugging(debugDetails));
            }
            return scope.$broadcast(name, args);
        };
        /**
      * Force braodcast or emit
      * @param {string} braodcast or emit - the action to broadcast
      * @param {string} name - name of the event to broadcast
      * @param {*} args - Optional one or more arguments which will be passed onto the event listeners
      * @param {*} debugDetails - additional verbose info passed in about how component is being used. 
      */
        var internalBroadcast = function (BEName, name, args, debugDetails) {
            if (!supressLog) {
                $log.info(BEName + " event: ", name, toString(args), debugging(debugDetails));
            }
            if (BEName === 'broadcast') {
                return scope.$broadcast(name, args);
            } else {
                return scope.$emit(name, args);
            }
        };

        /**
         * scope to be used by broadcast service.
         * @param {object} scope
         */
        var setScope = function (targetScope) {
            scope = targetScope || $rootScope;
            //$log.info("Event scope set: ", toString(scope));
            return new EventsProto(scope, emit, broadcast, internalBroadcast);
        };
        return { setScope: setScope };
    };
    uitkEvents.$inject = ["uitkConsumerConfigs", "$rootScope", "$log"];

    /**
     * Make use of log4javacript to be used to overwrite $log
     * @returns {object} 
     */
    var uitkLogServiceProvider = function () {
        //no execption if log4javascript is not included.
        if (typeof log4javascript === "undefined") {
            return null;
        }

        var logger = log4javascript.getLogger();
        log4javascript.setShowStackTraces(true);
        var logLevelMap = {
            'all': log4javascript.Level.ALL,
            'fatal': log4javascript.Level.FATAL,
            'error': log4javascript.Level.ERROR,
            'warn': log4javascript.Level.WARN,
            'info': log4javascript.Level.INFO,
            'debug': log4javascript.Level.DEBUG,
            'trace': log4javascript.Level.TRACE,
            'off': log4javascript.Level.OFF
        };

        function registerAppendersForLogLevel(appenders, logLevel) {
            appenders.forEach(function (appender) {
                switch (appender.appender) {
                    case "AjaxAppender":
                        var ajaxAppender = new log4javascript.AjaxAppender(appender.loggingServiceUrl);
                        ajaxAppender.setThreshold(logLevelMap[logLevel]);
                        ajaxAppender.setLayout(new log4javascript.JsonLayout(false, true));
                        ajaxAppender.addHeader("Content-Type", "application/json;charset=utf-8");
                        ajaxAppender.addHeader("X-CSRF-HEADER", "X-CSRF-TOKEN");
                        ajaxAppender.addHeader("X-CSRF-PARAM", "_csrf");
                        ajaxAppender.addHeader("X-CSRF-TOKEN", window.csrfToken);
                        logger.addAppender(ajaxAppender);
                        break;
                    case "AlertAppender":
                    case "PopUpAppender":
                    case "InPageAppender":
                    case "BrowserConsoleAppender":
                        var appenderInstance = Object.create(log4javascript[appender.appender].prototype);
                        appenderInstance.constructor.apply(appenderInstance, []);
                        appenderInstance.setThreshold(logLevelMap[logLevel]);
                        if (appender.pattern) {
                            appenderInstance.setLayout(new log4javascript.PatternLayout(appender.pattern));
                        }
                        logger.addAppender(appenderInstance);

                        break;
                    default:
                        var errorAppender = {
                            name: "InvalidAppenderException",
                            message: "Log Appender '" + appender.appender + "' not supported."
                        };
                        console.error(errorAppender);
                }
            });
        }

        return {
            registerAppendersForLogLevel: registerAppendersForLogLevel,
            getLogger: function () {
                return logger;
            }
        };
    };

    /**
     * Implentation to use log4javascript overloading $log of angularjs 
     * @param {object} uitkLogServiceProvider
     * @param {object} $injector
     * @returns {object} 
     */
    var $logOverload = function (uitkLogServiceProvider, uitkConsumerConfigs) {
        //TODO: Add support for server strings [support of log4js] and console objects.
        //This may require us to look at appenders for log4js and if console, display the object otherwise display the string.
        //this must work with exceptionService. 
        /**
         * set the configs for log4js
         * @param {object} uitkLogConfig
         * @returns {object}  
         */
        this.setLogConfigs = function (uitkLogConfig) {
            //log4javacript is not enabled use basic log
            if (typeof uitkLogServiceProvider.registerAppendersForLogLevel === 'undefined')
                return console;

            //loop through all configs of log4js and register configs. 
            for (var logLevel in uitkLogConfig) {
                if (uitkLogConfig.hasOwnProperty(logLevel)) {
                    uitkLogServiceProvider.registerAppendersForLogLevel(uitkLogConfig[logLevel], logLevel);
                }
            }

            //set log object to be returned. 
            var log = {};
            //get all registered loggers. 
            var logger = uitkLogServiceProvider.getLogger();
            //find registered loggers and init the level called.
            ['fatal', 'error', 'warn', 'info', 'debug', 'trace'].forEach(function (currentLogLevel) {
                //make sure currentlogLevel is a string
                var logLevel = currentLogLevel.toString();
                //set properties on log by logLevel
                log[logLevel] = console.log;
                //execute logLevel called
                log[logLevel] = function () {
                    logger[logLevel].apply(logger, Array.prototype.slice.call(arguments));
                };
            });
            return log;
        };
        return this.setLogConfigs(uitkConsumerConfigs.LOGCONFIG.log4javascriptConfig);
    };
    $logOverload.$inject = ["uitkLogServiceProvider", "uitkConsumerConfigs"];

    var uitkTools = function ($log) {
        var _this = this;
        _this.RandomId = function (length) {
            var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz'.split('');
            if (!length) {
                length = Math.floor(Math.random() * chars.length);
            }
            var str = '';
            for (var i = 0; i < length; i++) {
                str += chars[Math.floor(Math.random() * chars.length)];
            }
            return str;
        };

        _this.ComponentId = function (id, viewModel, element, name) {
            if (_.isUndefined(viewModel)) {
                $log.error("model provided is undefined " + name);
                return '';
            }

            var checkViewModel = function (id) {
                if (id) {
                    element.attr("id", id);
                    return true;
                }
                return false;
            };

            var checkAttribute = function (id) {
                if (id) {
                    return true;
                }
                return false;
            }
         
            if (!checkViewModel(viewModel.id)) {
                if (!checkAttribute(id)) {
                    $log.warn("No unique id has been provided for component " + name);
                    var uniqueId = _this.RandomId(5);
                    element.attr("id", uniqueId);
                    return uniqueId;
                }
            }
            return id;
        };
    };
    uitkTools.$inject = ['$log'];

    /*
     * Exception service for UITK which will translate message in user selected language and throw exception
     * This service will check if tranlsation module is present in app or not.
     */
    var uitkExceptionService = function ($injector) {

        var throwException = function (name, message) {

            var pascalFilter;

            if ($injector.has('$translate')) {
                pascalFilter = $injector.get('$translate');
                pascalFilter(message).then(function (data) {
                    throw {
                        name: name,
                        message: data
                    };
                }, function () {
                    throw {
                        name: name,
                        message: message
                    };
                });
            }
            else {
                throw {
                    name: name,
                    message: message
                };
            }
        };
        return {
            throwException: throwException
        }
    };

    uitkExceptionService.$inject = ["$injector"];

    /*
     * Translate filter for UITK which will check if angular-translate is available or not.
     */
    var uitkTranslateFilter = function ($filter, $log, uitkConsumerConfigs) {
        var pascalFilter;
        var supressLog = (uitkConsumerConfigs.LOGCONFIG.errorLevel === 'test');
        try {
            pascalFilter = $filter('translate');
        } catch (exception) {
            if (!supressLog) {
                $log.info("No translate filter defined: " + exception);
            }
            
        }

        return function (inputStr) {
            return pascalFilter ? pascalFilter(inputStr) : inputStr;
        }
    };

    uitkTranslateFilter.$inject = ["$filter", "$log", "uitkConsumerConfigs"];

    /*
     * This service is used to create dynamic live region and announce the message
     */
    var uitkLiveRegionService = function ($timeout, $document) {
        var timerB = null;
        var alertMessage = function (message, alertOff) {
            var div = document.createElement("div");
            angular.element(div).attr("id", "announceThisLive");
            angular.element(div).attr("role", "alert");
            angular.element(div).attr("aria-live", "polite");
            angular.element(div).attr("aria-relevant", "additions");
            angular.element(div).attr("aria-atomic", "false");
            angular.element(div).attr("class", "oui-a11y-hidden");
            if (angular.element("#announceThisLive").length === 0) {
                $document.find('body').append(div);
            }

            $timeout(function () {
                angular.element("#announceThisLive").html("");
                angular.element("#announceThisLive").append("<div>" + message + "</div>");
            }, 100);
            if (timerB) {
                $timeout.cancel(timerB);
            }
            timerB = $timeout(function () {
                angular.element("body #announceThisLive").attr("aria-live", "off");
                angular.element("body #announceThisLive").attr("role", "presentation");
                $timeout(function () {
                    angular.element("body #announceThisLive").remove();
                }, 100);
            }, 5000);
        };
        return {
            alertMessage: alertMessage
        }
    };

    /*
     * The purpose of this directive is to allow only numbers.
     * @return transformedInput
     */
    var uitkNumbersOnly = function() {
        function link(scope, element, attr, ngModelCtrl) {
            function userInput(input) {
                if (input) {
                    //Replace all other characters with empty string
                    var transformedInput = input.replace(/[^0-9]/g, '');

                    if (transformedInput !== input) {
                        ngModelCtrl.$setViewValue(transformedInput); //Update the view value
                        ngModelCtrl.$render(); //Called as view needs to be updated
                    }
                    //Return transformedInput
                    return transformedInput;
                }
                return undefined;
            }
            ngModelCtrl.$parsers.push(userInput);
        };

        return {
            restrict: 'EA',
            require: 'ngModel', // get a hold of NgModelController
            link: link
        };
    };

    uitkLiveRegionService.$inject = ["$timeout", "$document"];

    /**
     * @author Clint Small -> for dev questions :)
     * @module {uitk.uitkConfigs} This is configuration set for entire toolkit and can be adjusted for application requirements. 
     * This module will provide global tools required by the toolkit 
     */
    angular.module("uitk.uitkUtility", ["uitk.uitkConfigs"])
    .service("uitkEvents", uitkEvents)
    .service("uitkLogServiceProvider", uitkLogServiceProvider)
    .service("uitkExceptionService", uitkExceptionService)
    .service("$log", $logOverload)
    .service("uitkTools", uitkTools)
    .service("uitkLiveRegionService", uitkLiveRegionService)
    .filter("uitkTranslate", uitkTranslateFilter)
    .directive("uitkNumbersOnly", uitkNumbersOnly);
})();
