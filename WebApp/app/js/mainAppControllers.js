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

var addObligationPopup = function(_$modal) {
    var modalInstance = _$modal.open({
        templateUrl: '../partials/addObligation.html',
        controller: 'addNewObligationsCtrl',
        resolve: {
            items: function() {
                return;
            }
        }
    });

};


moneyGiverApp.factory("userFinanceFactory", function() {
	var remainingMoneyBadge = 0,
		spentMoneyBadge = 0;

	function getFinanceData() {
		return [remainingMoneyBadge, spentMoneyBadge];
	}

	function setFinanceData(userFinanceCalculated) {
		remainingMoneyBadge = userFinanceCalculated.remainingMoneyBadge;
		spentMoneyBadge = userFinanceCalculated.spentMoneyBadge;
	}
	return {
		getFinanceData: getFinanceData,
		setFinanceData: setFinanceData,
	}
});

moneyGiverApp.controller('MainPanelController', ['$scope', '$http', '$modal', 'userFinanceFactory',
	function($scope, $http, $modal, userFinanceFactory) {

		$scope.$watch(function() {
			return userFinanceFactory.getFinanceData();
		}, function(value) {
			$scope.remainingMoneyBadge = value[0];
			$scope.spentMoneyBadge = value[1];
		}, true);


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

moneyGiverApp.controller('ModalInstanceCtrl', function($scope, $http, $modalInstance, userFinanceFactory) {
	console.log("ModalInstanceCtrl");
	$scope.monthlyObligations = [{}];

	$scope.save = function() {
		$http.post('/saveFirstUserPreference', {
			"userIncome": $scope.monthlyIncoming,
			"userMonthlyObligations": $scope.monthlyObligations
		}).
		success(function(userFinanceCalculated) {
			userFinanceFactory.setFinanceData(userFinanceCalculated);
		}).error(function() {
			console.log("FAIL");
		});

		$modalInstance.close();
	};

	$scope.cancel = function() {
		$modalInstance.dismiss();
	};
	$scope.addFields = function() {

		$scope.monthlyObligations.push({});

	};

});

moneyGiverApp.controller('addNewObligationsCtrl', function($scope, $http, $modalInstance, userFinanceFactory) {
    console.log("addNewObligationsCtrl");
    $scope.monthlyObligations = [{}];

    $scope.save = function() {
        $http.post('/saveNewObligations', {
            "userMonthlyObligations": $scope.monthlyObligations
        }).
            success(function(userFinanceCalculated) {
                userFinanceFactory.setFinanceData(userFinanceCalculated);
            }).error(function() {
                console.log("FAIL");
            });

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

moneyGiverApp.controller('changePasswordCtrl', function($scope, $http) {

	$scope.changePassword = function() {
		$http.post('/changePassword', {
			"password": {
				"oldPassword": $scope.inputOldPassword,
				"newPassword": $scope.inputNewPassword,
				"newPasswordSecond": $scope.inputNewPasswordSecond
			}
		}).
		success(function(data) {
			$scope.result = data;
		});
	}
});

moneyGiverApp.controller('accountSettingCtrl', function($scope, $http, $modal) {


	$http.get('/getAccountSetting').
	success(function(userFinance) {
		$scope.obligations = userFinance.monthlyObligations;
		$scope.income = userFinance.income;

	});

	$scope.updateIncome = function() {
		$http.post('/updateIncome', {
			"newIncome": $scope.income
		}).
		success(function(data) {
			console.log(data);
			$scope.res = data;
		});
	}

	$scope.updateObligations = function() {
		$http.post('/updateObligations', {
			"obligations": $scope.obligations
		}).
		success(function(data) {
			console.log(data);
			$scope.res = data;
		});
	}

    $scope.displayAddObligationPopup = function() {
        addObligationPopup($modal);
    }

	$scope.removeObligation = function(index) {
		$scope.obligations.splice(index, 1);
		$http.post('/updateObligations', {
			"obligations": $scope.obligations
		}).
		success(function(data) {
			console.log(data);
			$scope.res = data;
		});
	}

});


moneyGiverApp.controller('financeHistoryCtrl', function($scope, $http) {
	$http.get('/getFinanceHistory').
	success(function(userHistory) {
		$scope.payments = userHistory.payments;
		$scope.predicate     = 'name'; // set the default sort type
  		$scope.reverse  = false;  // set the default sort order
		console.log(userHistory.payments);
		console.log("financeHistoryCtrl");
	});
});