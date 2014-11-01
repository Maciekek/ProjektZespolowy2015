'use strict';

/* Controllers */

var moneyGiverApp = angular.module('moneyGiverAppControllers', ['moneyGiverAppServices']);

moneyGiverApp.controller('MainBoxController', ['$scope', '$http',
	function($scope, $http) {
		console.log("I`m MainBoxController");

	}
]);
moneyGiverApp.controller('CreateAccountController', ['$scope', 'CreateAccountService',
    function($scope, CreateAccountService) {
        console.log("I'm in createAcconut...");
        $scope.register = function(){
            console.log('function register');
            $scope.answer = CreateAccountService.insert();
        }

    }
]);