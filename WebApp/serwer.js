'use strict';

var express = require('express');
var app = express();
var bodyParser = require("body-parser");
var mongo = require('mongodb');
var monk = require('monk');
var dbManager = require('./database/dbManager.js');
var Q = require('q');
var port = 3000;
var socketIo = require('socket.io');
var connect = require('connect');
var passport = require('passport');
var LocalStrategy = require('passport-local').Strategy;
var passportSocketIo = require('passport.socketio');
var sessionStore = new connect.session.MemoryStore();
var httpServer = require("http").createServer(app);
var io = socketIo.listen(httpServer);
var cookieParser = require('cookie-parser');
var session = require('express-session');

mongo.connect('mongodb://localhost:27017/moneyGiver', dbManager.dbConnectionHandler);

app.use(express.static(__dirname + '/app'));
app.use(bodyParser.urlencoded({
	'extended': 'true'
}));
app.use(bodyParser.json());
app.use(bodyParser.json({
	type: 'application/vnd.api+json'
}));

var sessionSecret = 'sessionSecret';
var sessionKey = 'connect.sid';
var server;
var sio;


function isAuthenticated(req, res, next) {
	if (req.user) {
		next();
	} else {
		res.redirect('/letLogin');
	}
}

passport.serializeUser(function(user, done) {
	done(null, user);
});

passport.deserializeUser(function(obj, done) {
	done(null, obj);
});

passport.use(new LocalStrategy(
	function(username, password, done) {
		return dbManager.findUserPassword(username).then(function(userAccount) {
			if (password === userAccount.password) {
				console.log("Udane logowanie...");
				return done(null, {
					userName: userAccount.userName,
					firstLogin: userAccount.firstLogin,
				});
			} else {
				console.log("Złe dane");
				return done(null, false);
			}
		}).catch(function(error) {
			console.log("Złe dane");
			return done(null, false);

		});
	}
));
app.use(bodyParser.urlencoded({
	extended: true
}));
app.use(session({
	store: sessionStore,
	key: sessionKey,
	secret: sessionSecret,
	resave: true,
	saveUninitialized: true
}));
app.use(passport.initialize());
app.use(passport.session());

console.log("app listen on port " + port);


app.post('/login', function(req, res) {
	passport.authenticate('local', function(err, user, info) {
		if (!user) {
			console.log("error złe dane");
			return res.redirect('/');
		}
		req.logIn(user, function(err) {
			res.redirect('/mainPanel');
		});
	})(req, res);
});

var onAuthorizeSuccess = function(data, accept) {
	console.log('Udane połączenie z socket.io');
	accept(null, true);
};

var onAuthorizeFail = function(data, message, error, accept) {
	if (error) {
		console.log("Nieudane logowanie");
		throw new Error(message);
	}
	console.log('Nieudane połączenie z socket.io:', message);
	accept(null, false);
};

io.set('authorization', passportSocketIo.authorize({
	passport: passport,
	cookieParser: cookieParser,
	key: sessionKey,
	secret: sessionSecret,
	store: sessionStore,
	success: onAuthorizeSuccess,
	fail: onAuthorizeFail
}));

app.get('/', isAuthenticated, function(req, res, next) {
	res.sendfile("./app/mainPanel.html");
});

app.get('/letLogin', function(req, res) {
	res.sendfile("./app/loginPanel.html");
});
app.get('/dialog.html', function(req, res) {
	res.sendfile("./app/js/dialog.html");
});

app.post('/saveFirstUserPreference', function(req, res) {
	dbManager.saveUserFinanceConfiguration(req.user.userName,
		req.body.userIncome, req.body.userMonthlyObligations);

	res.send({});
});

app.get('/mainPanel', isAuthenticated, function(req, res) {
	res.sendfile("./app/mainPanel.html");
});

app.get('/createAccount', function(req, res) {
	res.sendfile("./app/partials/createAccount.html");
});

app.post('/userCredentials', function(req, res) {
	var json = {
		"userCredentials": {}
	};
	json["userCredentials"] = {
		"login": "admin",
		"password": "admin"
	};

	res.json(json);
});

app.get('/getUserData', function(req, res) {
	dbManager.getUserAccountByLogin(req.user.userName).then(function(userAccount) {
		res.json(userAccount);
	});
	
});

app.get('/logout', function(req, res) {
	req.logout();
	res.redirect('/');
});

app.post('/registerNewUser', function(req, res) {
	dbManager.checkIfUserLoginIsFree(req.body.userName).then(function() {
		dbManager.createAccount(req.body.userName, req.body.pass).then(function() {
			res.send(true);
		});
	}).
	catch(function(error) {
		res.send(false);

	});
});

app.get('/calculateRemainingMoneyBadge', function(req, res) {
	var userAmount = {
		spentMoneyBadge: 0,
		remainingMoneyBadge: 0
	};
	dbManager.getUserAccountByLogin(req.user.userName).then(function(userAccount) {
		userAccount.monthlyObligations.forEach(function(entry) {
			userAmount.spentMoneyBadge += entry.value;
		});
		userAccount.allPayments.forEach(function(payment) {
			userAmount.spentMoneyBadge += payment.count;

		})
		userAmount.remainingMoneyBadge = userAccount.income - userAmount.spentMoneyBadge;
		res.json(userAmount);

	});
});

app.post('/changePassword', function(req, res) {
        dbManager.getUserAccountByLogin(req.user.userName).then(function(userAccount) {
        if(userAccount.password !== req.body.password.oldPassword){
            res.send("Stare hasło nie zgadza się!");
        }
        else{
            if(req.body.password.newPassword !== req.body.password.newPasswordSecond){
                res.send("Nowe hasła są różne!");
            }
            else{
                dbManager.updatePassword(req.user.userName, req.body.password.newPassword).then(function(){
                    res.send("Hasło zmienione!");
                });
            }
        }
    });
});

app.post("/addUserNewPayments", function(req, res) {
	console.log(req.body);
	dbManager.saveNewUserPayments(req.body.newPayments, req.user.userName);
});
httpServer.listen(port, function() {
	console.log('Express server listening on port ' + port);
});