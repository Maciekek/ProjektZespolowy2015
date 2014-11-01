'use strict';

/* Services */
var moneyGiverAppService = angular.module('moneyGiverAppServices', []);

moneyGiverAppService.service('CreateAccountService', function() {
   return {
       insert: function() {
           return "Udało się!";
       }
   };
});


