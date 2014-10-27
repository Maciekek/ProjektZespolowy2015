var serwer = require('../../serwer');

describe("database mongodb connection", function () {
    it("connected to db", function (done) {
        var err = null;
        var res = serwer.checkDbConnection(err);
        expect("OK").toBe(res);
        done();
    });
});