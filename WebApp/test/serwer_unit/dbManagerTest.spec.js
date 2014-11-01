var dbManager = require('../../database/dbManager');
var Q = require('q');

describe("database mongodb connection", function() {
    it("connected to db", function(done) {
        var err;
        var res = dbManager.dbConnectionHandler(err);

        expect(err).toEqual(res);
        done();
    });
    it("connected to db failure", function(done) {
        var err = "ERROR";
        var res = dbManager.dbConnectionHandler(err);

        expect(res).toEqual(err);
        done();
    });


});

