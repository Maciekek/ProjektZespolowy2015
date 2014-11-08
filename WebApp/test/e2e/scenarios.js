'use strict';

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
	});

	it('create new account', function() {
		browser.get('/');
		$('#createAccountBtn').click();

		var login = ptor.findElement(protractor.By.id('inputEmail'));
		var pass = ptor.findElement(protractor.By.id('inputPassword'));

		login.sendKeys("test" + loginRandom);
		pass.sendKeys("testPass");

		$('#registerButton').click();

		browser.getLocationAbsUrl().then(function(url) {
			expect(url.split('#')[1]).toBe('/');
		});


	});
	it('create new account but with extisting login', function() {
		browser.get('/');
		$('#createAccountBtn').click();

		var login = ptor.findElement(protractor.By.id('inputEmail'));
		var pass = ptor.findElement(protractor.By.id('inputPassword'));

		login.sendKeys("test" + loginRandom);
		pass.sendKeys("testPass");

		$('#registerButton').click();

		browser.getLocationAbsUrl().then(function(url) {
			expect(url.split('#')[1]).toBe('/createAccount');
		});

		expect($('#ifOccupiedLogin').isDisplayed()).toBeTruthy();

	})

})
describe('login & logout', function() {
	var ptor;
	var loginRandom = "test" + Math.random();

	beforeEach(function() {
		ptor = protractor.getInstance();
	});
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

describe('login check', function() {
	var ptor;
	var loginRandom = "test" + Math.random();

	beforeEach(function() {
		ptor = protractor.getInstance();
	});

	it('create new account', function() {
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

	it('after correct login dialog shoud display', function() {
		browser.get('/');
		expect($('#dialog').isDisplayed()).toBeTruthy();

	});

	it('after close dialog, dialog should`t be display', function() {
		browser.get('/');
		var cancelButton = element(by.css('[ng-click="cancel()"]'))

		cancelButton.click();
		expect(element(by.css('[id="dialog"]')).isPresent()).toBeFalsy();

	});

	it('after close dialog, userName on MainPanel should be display', function() {
		browser.get('/');
		var cancelButton = element(by.css('[ng-click="cancel()"]'))

		cancelButton.click();
		expect($('#login').isDisplayed()).toBeTruthy();

	});
	it('after close dialog, userName on MainPanel and it should display login corectly', function() {
		browser.get('/');
		var cancelButton = element(by.css('[ng-click="cancel()"]'))

		cancelButton.click();

		var loginField = element(by.id('login'));
		expect(loginField.getText()).toEqual(loginRandom);

	});

	it('should close dialog by click on save button', function() {
		browser.get('/');
		var saveButton = element(by.css('[ng-click="save()"]'))

		saveButton.click();
		expect(element(by.css('[id="dialog"]')).isPresent()).toBeFalsy();


		$('#logout').click();
	});

	it('should close dialog by click on save button', function() {
		browser.get('/');
		var login = ptor.findElement(protractor.By.model('login'));
		var pass = ptor.findElement(protractor.By.model('pass'));

		login.sendKeys(loginRandom);
		pass.sendKeys("testPass");

		$('#login').click();

		expect(element(by.css('[id="dialog"]')).isPresent()).toBeFalsy();

		$('#logout').click();

	});
});


describe('create new account and check login', function() {
	var ptor;
	var loginRandom = Math.random();

	beforeEach(function() {
		ptor = protractor.getInstance();
	});

	it('create new account', function() {
		browser.get('/');
		$('#createAccountBtn').click();

		var login = ptor.findElement(protractor.By.id('inputEmail'));
		var pass = ptor.findElement(protractor.By.id('inputPassword'));

		login.sendKeys("test" + loginRandom);
		pass.sendKeys("testPass");

		$('#registerButton').click();

		var login = ptor.findElement(protractor.By.model('login'));
		var pass = ptor.findElement(protractor.By.model('pass'));

		login.sendKeys("test" + loginRandom);
		pass.sendKeys("testPass");

		$('#login').click();

		browser.getLocationAbsUrl().then(function(url) {
			console.log("URL " + url);
			expect(url).toMatch('http:\/\/.*mainPanel#\/');
		});

	});



})