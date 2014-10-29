var dbConnectionHandler = function(status) {
	if (status) {
		console.dir("Brak polaczenia z baza");
	} else {
		console.log('Połączenie z bazą udane!');	
	}
	return status;
}
exports.dbConnectionHandler = dbConnectionHandler;

