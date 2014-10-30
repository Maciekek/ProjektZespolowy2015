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

	it('should redirect from / to /createAccount and show Rejestracja zajmię tylko kilka sekund', function(){
		browser.get('/');
		$('#createAccountBtn').click();
		expect(element(by.id('rejestr')).getText()).toEqual('Rejestracja zajmię tylko kilka sekund!');
	});


});