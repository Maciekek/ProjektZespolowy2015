'use strict';

/* Controllers */

var moneyGiverApp = angular.module('moneyGiverAppControllers', []);

var modalDialogSetting = function(_$modal) {
	var modalInstance = _$modal.open({
		templateUrl: 'dialog.html',
		controller: 'ModalInstanceCtrl',
		resolve: {
			items: function() {
				return;
			}
		}
	});

};

moneyGiverApp.controller('MainPanelController', ['$scope', '$http', '$modal',
	function($scope, $http, $modal) {

		$http.get('/getUserData').
		success(function(userAccount) {
			$scope.userLogin = userAccount.userName;
			console.log("incoming:  " + userAccount.income);

			if (userAccount.firstLogin) {
				modalDialogSetting($modal);
			}
		});
		$http.get('/calculateRemainingMoneyBadge').
		success(function(userAmount) {
			$scope.remainingMoneyBadge = userAmount.remainingMoneyBadge;
			$scope.spentMoneyBadge = userAmount.spentMoneyBadge;
			console.log("remainingMoneyBadge:  " + userAmount.remainingMoneyBadge);
			console.log("spentMoneyBadge:  " + userAmount.spentMoneyBadge);
		});
	}
]);

moneyGiverApp.controller('ModalInstanceCtrl', function($scope, $http, $modalInstance) {
	console.log("ModalInstanceCtrl");
	$scope.monthlyObligations = [{}];

	$scope.save = function() {
		$http.post('/saveFirstUserPreference', {
			"userIncome": $scope.monthlyIncoming,
			"userMonthlyObligations": $scope.monthlyObligations
		}).
		success(function(data) {
			console.log(data);
		});
		var monthlyObligations = $scope.monthlyObligations;
		console.log(monthlyObligations);

		$modalInstance.close();
	};

	$scope.cancel = function() {
		$modalInstance.dismiss();
	};
	$scope.addFields = function() {

		$scope.monthlyObligations.push({});

	};

});

moneyGiverApp.controller('addPaymentCtrl', function($scope, $http) {
	console.log("AddPaymentCtrl");
	$scope.newPayments = [{}];

	$scope.addPayments = function() {

		//temporary
		if (!$scope.newPayments["0"].name || !$scope.newPayments["0"]) {
			alert("Uzupełnij pola");
		} else {
			$scope.newPayments.push({});
		}
		//***
	}

	$scope.saveNewPayments = function() {
		console.log($scope.newPayments);
		$http.post('/addUserNewPayments', {
			"newPayments": $scope.newPayments
		}).
		success(function(data) {
			console.log(data);
		});

	}

});

moneyGiverApp.controller('changePasswordCtrl', function($scope, $http){

    $scope.changePassword = function() {
        $http.post('/changePassword', {
            "password": {
                "oldPassword": $scope.inputOldPassword,
                "newPassword": $scope.inputNewPassword,
                "newPasswordSecond": $scope.inputNewPasswordSecond
            }
        }).
        success(function(data) {
            console.log(data);
            $scope.result = data;
        });
    }
});