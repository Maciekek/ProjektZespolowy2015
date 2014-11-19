'use strict';

/* App Module */

var moneyGiverLoginApp = angular.module('moneyGiverLoginApp', [
	'ngRoute',
	'moneyGiverLoginAppControllers'
]);



moneyGiverLoginApp.config(['$routeProvider',
	function($routeProvider) {
		$routeProvider.
		when('/', {
			templateUrl: 'partials/mainPage.html',
			controller: 'MainBoxController'
		}).
		when('/createAccount', {
			templateUrl: 'partials/createAccount.html',
			controller: 'createAccountController'
		}).
		otherwise({
			redirectTo: '/error'
		});
	}
]);
