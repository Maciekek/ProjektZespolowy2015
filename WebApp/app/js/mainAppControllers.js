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
			console.log("incoming:  " +  userAccount.income );

			if (userAccount.firstLogin) {
				modalDialogSetting($modal);
			}
		});
        $http.get('/calculateRemainingMoneyBadge').
            success(function(userAmount) {
                $scope.remainingMoneyBadge = userAmount.remainingMoneyBadge;
                $scope.spentMoneyBadge = userAmount.spentMoneyBadge;
                console.log("remainingMoneyBadge:  " +  userAmount.remainingMoneyBadge );
                console.log("spentMoneyBadge:  " +  userAmount.spentMoneyBadge );
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
		
		console.log("addFields");
		$scope.monthlyObligations.push({});

	};

});