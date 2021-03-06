'use strict';

/* Controllers */

var moneyGiverLoginApp = angular.module('moneyGiverLoginAppControllers', []);

moneyGiverLoginApp.controller('MainBoxController', ['$scope', '$http',
	function($scope, $http) {
		console.log("I`m MainBoxController");

	}
]);


moneyGiverLoginApp.controller('createAccountController', ['$scope', '$location', '$http',
	function($scope, $location, $http) {
		$scope.OccupiedLogin = true;

		$scope.register = function() {
			var userName = $scope.login;
			var pass = $scope.pass;

			console.log(userName + " " + pass);
			$http.post('/registerNewUser', {
				"userName": userName,
				"pass": pass
			}).
			success(function(data) {
				/*data == "true " because data is string
					this code is to change, but not today...
				*/
				if (data === "true") {
					$location.path("/");
				} else {
					$scope.OccupiedLogin = false;
				}
			});
		};
	}
]);