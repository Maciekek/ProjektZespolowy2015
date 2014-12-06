var Q = require('q');
var monk = require('monk');
var db = monk('localhost:27017/moneyGiver');

var calculateAvailableFunds = function(userIncome, userMonthlyObligations) {
	var availableFunds = userIncome;
	userMonthlyObligations.forEach(function(monthlyObligation) {
		availableFunds -= monthlyObligation.value;
	});

	return availableFunds;
}

var calculateExpenses = function(userMonthlyObligations) {
	var userExpenses = 0;
	userMonthlyObligations.forEach(function(monthlyObligation) {
		userExpenses += monthlyObligation.value;
	});
	return userExpenses;
}

var setTimeInformation = function(payment) {
	var date = new Date();
	payment.paymentMonth = date.getMonth() + 1;
	payment.paymentDay = date.getDay();
	payment.paymentTime = date.getHours() + ":" + date.getMinutes();
	return payment;
};



var dbConnectionHandler = function(status) {
	if (status) {
		console.dir("Brak polaczenia z baza");
	} else {
		console.log('Połączenie z bazą udane!');
	}
	return status;
}
exports.dbConnectionHandler = dbConnectionHandler;

var getUserAccountByLogin = function(userName) {
	var deferred = Q.defer();
	var collection = db.get('userAccount');
	collection.findOne({
		"userName": userName
	}, function(err, userAccount) {
		if (err) {
			deferred.reject();

		} else {
			deferred.resolve(userAccount);
		}
	});
	return deferred.promise;
}
exports.getUserAccountByLogin = getUserAccountByLogin;

var checkIfUserLoginIsFree = function(login) {
	var deferred = Q.defer();
	var collection = db.get('userAccount');
	collection.findOne({
		"userName": login
	}, function(err, item) {
		if (item === null) {
			deferred.resolve();

		} else {
			deferred.reject();


		}
	});
	return deferred.promise;

}
exports.checkIfUserLoginIsFree = checkIfUserLoginIsFree;

var createAccount = function(login, pass) {
	var deferred = Q.defer();
	var collection = db.get('userAccount');
	collection.insert({
		"userName": login,
		"password": pass,
		"firstLogin": true,
		"allPayments": []
	}, function(err, doc) {
		if (err) {
			console.log(err + "!!!!!");
			deferred.reject();
		} else {
			console.log("OK");
			deferred.resolve();
		}
	});
	return deferred.promise;
}
exports.createAccount = createAccount;

var findUserPassword = function(username) {
	var deferred = Q.defer();
	var collection = db.get('userAccount');
	var userAccount = {};
	console.log("findUserPassword");
	collection.findOne({
		userName: username
	}, function(err, user) {
		if (user === null) {
			deferred.reject();
		} else {
			userAccount = {
				userName: user.userName,
				password: user.password,
				firstLogin: user.firstLogin
			}
			deferred.resolve(userAccount);
		}
	});
	return deferred.promise;
}
exports.findUserPassword = findUserPassword;

var saveUserFinanceConfiguration = function(userName, userIncome, userMonthlyObligations) {
	var collection = db.get('userAccount');

	getUserAccountByLogin(userName).then(function(userAccount) {
		userAccount.firstLogin = false;
		userAccount.income = userIncome;
		userAccount.monthlyObligations = userMonthlyObligations;

		userAccount.availableFunds = calculateAvailableFunds(userIncome, userMonthlyObligations);
		userAccount.userExpenses = calculateExpenses(userMonthlyObligations);

		collection.update({
			"userName": userName
		}, userAccount, function(err, item) {
			console.log(item);
		})
	});
}
exports.saveUserFinanceConfiguration = saveUserFinanceConfiguration;

var saveNewUserPayments = function(newPayments, userName) {
	var collection = db.get('userAccount');

	getUserAccountByLogin(userName).then(function(userAccount) {
		newPayments.forEach(function(payment){
			setTimeInformation(payment);
			userAccount["allPayments"].push(payment);
			userAccount.availableFunds -= payment.count;
			userAccount.userExpenses += payment.count;
		});
		collection.update({
			"userName": userName
		}, userAccount, function(err, item) {
			console.log(item);
		})
	});

}
exports.saveNewUserPayments = saveNewUserPayments;