var serwer = require('../../serwer');

describe("database mongodb connection", function () {
    it("connected to db", function (done) {
        var err = null;
        var res = serwer.checkDbConnection(err);
        expect("OK").toBe(res);
        done();
    });
});

describe("database mongodb connection - check result if error", function () {
    it("connected to db failure", function (done) {
        var err = new Error("Database connection error");
        expect(serwer.checkDbConnection(err)).toThrow(new Error(err));
        done();
    });
});