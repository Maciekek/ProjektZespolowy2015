'use strict';

/* App Module */

var moneyGiverApp = angular.module('moneyGiverApp', [
	'ngRoute',
	'moneyGiverAppControllers',
    //'moneyGiverAppServices'
]);

moneyGiverApp.config(['$routeProvider',
	function($routeProvider) {
		$routeProvider.
		when('/', {
			templateUrl: 'partials/mainPage.html',
			controller: 'MainBoxController'
		}).
		when('/createAccount', {
			templateUrl: 'partials/createAccount.html',
			controller: 'CreateAccountController',
            //service: 'CreateAccountService'
		}).
		otherwise({
			redirectTo: '/error'
		});
	}
]);