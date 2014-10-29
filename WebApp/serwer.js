'use strict';

var express = require('express');
var app = express();
var bodyParser = require("body-parser");
var mongo = require('mongodb');
var monk = require('monk');
var dbManager = require('./database/dbManager.js');
var port = 3000;

mongo.connect('mongodb://localhost:27017/moneyGiver', dbManager.dbConnectionHandler);

app.use(express.static(__dirname + '/app'));
app.use(bodyParser.urlencoded({
	'extended': 'true'
}));
app.use(bodyParser.json());
app.use(bodyParser.json({
	type: 'application/vnd.api+json'
}));

app.listen(port);
console.log("app listen on port " + port);


app.get('/', function(req, res) {
	console.log("test");
	res.sendfile("./app/index.html");
});

app.get('/createAccount', function(req, res) {
	console.log("test");
	res.sendfile("./app/partials/createAccount.html");
});
