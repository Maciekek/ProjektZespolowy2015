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
		otherwise({
			redirectTo: '/error'
		});
	}
]);