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

			if (userAccount.firstLogin) {
				modalDialogSetting($modal);
			}
		});

	}
]);

moneyGiverApp.controller('ModalInstanceCtrl', function($scope, $http,$modalInstance) {
	$scope.save = function() {
		$http.post('/saveFirstUserPreference', {"test":"test"}).
		success(function  (data) {
			console.log(data);
		});
		$modalInstance.close();
	};

	$scope.cancel = function() {
		$modalInstance.dismiss();
	};
});