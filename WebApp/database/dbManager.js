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