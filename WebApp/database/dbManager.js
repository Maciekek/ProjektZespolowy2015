var Q = require('q');
var monk = require('monk');
var db = monk('localhost:27017/moneyGiver');

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
		console.log(userAccount);
		userAccount.firstLogin = false;
		userAccount.income = userIncome;
		userAccount.monthlyObligations = userMonthlyObligations;
		console.log(userAccount);
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
		newPayments.forEach(function(newPayment) {
			userAccount["allPayments"].push(newPayment);
		});
		collection.update({
			"userName": userName
		}, userAccount, function(err, item) {
			console.log(item);
		})
	});

}
exports.saveNewUserPayments = saveNewUserPayments;

var updatePassword = function(userName, password) {
    var deferred = Q.defer();
    var collection = db.get('userAccount');

    getUserAccountByLogin(userName).then(function(userAccount) {
        userAccount.password = password;
        collection.update({
            "userName": userName
        }, userAccount, function(err) {
            if (err) {
                deferred.reject();

            } else {
                deferred.resolve();
            }
        });
    });
    return deferred.promise;
}

exports.updatePassword = updatePassword;

var updateIncome = function(userName, newIncome) {
    var deferred = Q.defer();
    var collection = db.get('userAccount');

    getUserAccountByLogin(userName).then(function(userAccount) {
        userAccount.income = newIncome;
        collection.update({
            "userName": userName
        }, userAccount, function(err) {
            if (err) {
                deferred.reject();

            } else {
                deferred.resolve();
            }
        });
    });
    return deferred.promise;
}

exports.updateIncome = updateIncome;

var updateObligations = function(userName, obligations) {
    var deferred = Q.defer();
    var collection = db.get('userAccount');

    getUserAccountByLogin(userName).then(function(userAccount) {
        userAccount.monthlyObligations = obligations;
        collection.update({
            "userName": userName
        }, userAccount, function(err) {
            if (err) {
                deferred.reject();

            } else {
                deferred.resolve();
            }
        });
    });
    return deferred.promise;
}

exports.updateObligations = updateObligations;

