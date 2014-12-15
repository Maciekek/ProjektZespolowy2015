'use strict';

/* App Module */
var moneyGiverApp = angular.module('moneyGiverApp', [
	'ngRoute',
	"ui.bootstrap",
	'moneyGiverAppControllers'
]);


moneyGiverApp.config(['$routeProvider',
	function($routeProvider) {
		$routeProvider.
		when('/', {
			templateUrl: 'partials/centerPanelMainPage.html',
		}).
		when('/addPayment', {
			templateUrl: 'partials/addPayment.html',
		}).
        when('/changePassword', {
            templateUrl: 'partials/changePassword.html',
        }).
		when('/financeSetting', {
            templateUrl: 'partials/financeSettings.html',
        }).
        when('/financeHistory', {
            templateUrl: 'partials/financeHistory.html',
        }).
		otherwise({
			redirectTo: '/error'
		});
	}
]);