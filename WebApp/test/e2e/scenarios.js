'use strict';
var Q = require('q');

/* http://docs.angularjs.org/guide/dev_guide.e2e-testing */
describe('MoneyGiver', function() {

	it('should redirect / to /', function() {
		browser.get('/');
		browser.getLocationAbsUrl().then(function(url) {
			expect(url.split('#')[1]).toBe('/');
		});
	});
	it('should redirect from / to /createAccount after click', function() {
		browser.get('/');

		$('#createAccountBtn').click();
		browser.getLocationAbsUrl().then(function(url) {

			expect(url.split('#')[1]).toBe('/createAccount');
		});
	});

	it('should redirect from / to /createAccount', function() {
		browser.get('/#/createAccount');
		browser.getLocationAbsUrl().then(function(url) {

			expect(url.split('#')[1]).toBe('/createAccount');
		});
	});

	it('should redirect from / to /createAccount and show Rejestracja zajmię tylko kilka sekund', function() {
		browser.get('/');
		$('#createAccountBtn').click();
		expect(element(by.id('rejestr')).getText()).toEqual('Rejestracja zajmię tylko kilka sekund!');
	});


});

describe('create new account test suite', function() {
	var ptor;
	var loginRandom = Math.random();

	beforeEach(function() {
		ptor = protractor.getInstance();
		browser.get('/');
		$('#createAccountBtn').click();

		var login = ptor.findElement(protractor.By.id('inputEmail'));
		var pass = ptor.findElement(protractor.By.id('inputPassword'));

		login.sendKeys("test" + loginRandom);
		pass.sendKeys("testPass");

		$('#registerButton').click();
	});

	it('create new account', function() {
		browser.getLocationAbsUrl().then(function(url) {
			expect(url.split('#')[1]).toBe('/');
		});
	});
	it('create new account but with extisting login', function() {
		browser.getLocationAbsUrl().then(function(url) {
			expect(url.split('#')[1]).toBe('/createAccount');
		});

		expect($('#ifOccupiedLogin').isDisplayed()).toBeTruthy();

	})

});

describe('login & logout', function() {
	var ptor;
	var loginRandom = "test" + Math.random();

	beforeEach(function() {
		ptor = protractor.getInstance();


	});

	// afterEach(function(){
	// 	$("#logout").click();
	// })

	it('should login ', function() {
		browser.get('/');
		$('#createAccountBtn').click();

		var login = ptor.findElement(protractor.By.id('inputEmail'));
		var pass = ptor.findElement(protractor.By.id('inputPassword'));

		login.sendKeys(loginRandom);
		pass.sendKeys("testPass");

		$('#registerButton').click();

		var login = ptor.findElement(protractor.By.model('login'));
		var pass = ptor.findElement(protractor.By.model('pass'));

		login.sendKeys(loginRandom);
		pass.sendKeys("testPass");

		$('#login').click();

		browser.getLocationAbsUrl().then(function(url) {
			expect(url).toMatch('http:\/\/.*mainPanel#\/');
		});
	});

	it('should logout ', function() {
		browser.get('/');
		var cancelButton = element(by.css('[ng-click="cancel()"]'))
		cancelButton.click();

		$('#logout').click();

		browser.getLocationAbsUrl().then(function(url) {
			expect(url.split('#')[1]).toBe('/');
		});

	});

});
describe('login & logout', function() {
	var ptor;
	var loginRandom = "test" + Math.random();

	beforeEach(function() {
		ptor = protractor.getInstance();

		browser.get('/');
		var login = ptor.findElement(protractor.By.model('login'));
		var pass = ptor.findElement(protractor.By.model('pass'));

		login.sendKeys(loginRandom);
		pass.sendKeys("WrongPassword");

		$('#login').click();


	});
	it('should redirect to / after input wrong password ', function() {
		browser.getLocationAbsUrl().then(function(url) {
			expect(url.split('#')[1]).toBe('/');
		});

	});
	it('should redirect to / after input wrong login and password ', function() {
		browser.getLocationAbsUrl().then(function(url) {
			expect(url.split('#')[1]).toBe('/');
		});

	});


});

describe('Login check with user configuration panel - only cancel button', function() {
	var loginRandom = "loginRandom" + Math.random();
	var testRandom = "test" + Math.random();
	var ptor;
	beforeEach(function() {
		ptor = protractor.getInstance();

		browser.get('/');
		$('#createAccountBtn').click();

		var login = ptor.findElement(protractor.By.id('inputEmail'));
		var pass = ptor.findElement(protractor.By.id('inputPassword'));

		login.sendKeys(loginRandom);
		pass.sendKeys(testRandom);

		$('#registerButton').click();

		browser.get('/');
		var login = ptor.findElement(protractor.By.model('login'));
		var pass = ptor.findElement(protractor.By.model('pass'));

		login.sendKeys(loginRandom);
		pass.sendKeys(testRandom);

		$('#login').click();
	});



	it('after correct login dialog shoud display', function() {
		browser.get('/');
		expect($('#dialog').isDisplayed()).toBeTruthy();

		var cancelButton = element(by.css('[ng-click="cancel()"]'))

		browser.wait(function() {
			return ptor.isElementPresent(cancelButton);
		}, 30000);

		cancelButton.click();

		expect(element(by.css('[id="dialog"]')).isPresent()).toBeFalsy();

		$("#logout").click();

	});


	it('after close dialog, userName on MainPanel should be display', function() {
		browser.get('/');
		var cancelButton = element(by.css('[ng-click="cancel()"]'))

		browser.wait(function() {
			return ptor.isElementPresent(cancelButton);
		}, 30000);

		cancelButton.click();

		expect($('#login').isDisplayed()).toBeTruthy();

		$("#logout").click();

	});
	it('after close dialog, userName on MainPanel and it should display login corectly', function() {
		browser.get('/');
		var cancelButton = element(by.css('[ng-click="cancel()"]'))

		browser.wait(function() {
			return ptor.isElementPresent(cancelButton);
		}, 30000);
 		
		cancelButton.click();

		var loginField = element(by.id('login'));

		// browser.wait(element(by.id('login')).isPresent,300);
		
		expect(loginField.getText()).toEqual("Witaj, " + loginRandom);
		
		$('#logout').click();

	});



});
describe('Login check with user configuration panel - only save button', function() {
	var loginRandom = "loginRandom" + Math.random();
	var testRandom = "test" + Math.random();

	it('should close dialog by click on save button', function() {
		var ptor = protractor.getInstance();

		browser.get('/');
		$('#createAccountBtn').click();

		var login = ptor.findElement(protractor.By.id('inputEmail'));
		var pass = ptor.findElement(protractor.By.id('inputPassword'));

		login.sendKeys(loginRandom);
		pass.sendKeys(testRandom);

		$('#registerButton').click();

		browser.get('/');
		var login = ptor.findElement(protractor.By.model('login'));
		var pass = ptor.findElement(protractor.By.model('pass'));

		login.sendKeys(loginRandom);
		pass.sendKeys(testRandom);

		$('#login').click();

		browser.get('/');
		var saveButton = element(by.css('[ng-click="save()"]'))

		saveButton.click();
		expect(element(by.css('[id="dialog"]')).isPresent()).toBeFalsy();

		$('#logout').click();

		browser.get('/');

		var login = ptor.findElement(protractor.By.model('login'));
		var pass = ptor.findElement(protractor.By.model('pass'));

		login.sendKeys(loginRandom);
		pass.sendKeys(testRandom);

		$('#login').click();

		expect(element(by.css('[id="dialog"]')).isPresent()).toBeFalsy();

		$('#logout').click();

	});
});

describe("should configure account ", function() {
	var ptor;
	var loginRandom = Math.random();

	beforeEach(function() {
		ptor = protractor.getInstance();
		browser.get('/');
		$('#createAccountBtn').click();

		var login = ptor.findElement(protractor.By.id('inputEmail'));
		var pass = ptor.findElement(protractor.By.id('inputPassword'));

		login.sendKeys(loginRandom);
		pass.sendKeys("testPass");

		$('#registerButton').click();

		var login = ptor.findElement(protractor.By.model('login'));
		var pass = ptor.findElement(protractor.By.model('pass'));

		login.sendKeys(loginRandom);
		pass.sendKeys("testPass");

		$('#login').click();


	});

	afterEach(function() {

	})
	it("Montly obligation should equal to 1", function() {

		element.all(by.repeater('monthlyObligation in monthlyObligations')).then(function(arr) {
			expect(arr.length).toEqual(1);
		});

		$("#cancel").click();
		$("#logout").click();


	});

	it("Montly obligation should equal to 3", function() {
		$("#addFields").click();
		$("#addFields").click();

		element.all(by.repeater('monthlyObligation in monthlyObligations')).then(function(arr) {
			expect(arr.length).toEqual(3);
		});
	});

});