var mongo = require('mongodb');
var monk = require('monk');

describe("database mongodb connection", function () {
    it("mongodb connection", function (done) {
        var db = monk('localhost:27017/test');
        should.exists(db);
        done();
    });
});

describe("Simple db operations - insert, find and drop test", function () {
    var db, orders = {};

    before(function (done) {
        // Get db and collection
        db = monk('localhost:27017/test');
        orders = db.get("orders");

        // drop the collection, just to be sure
        orders.drop();

        // add some documents
        for (var i = 1; i < 101; i++) {
            orders.insert({
                description: "Order #" + i,
                paid: true,
                customerId: "12345" + i
            });
        }

        done();
    });

    after(function (done) {
        monk('localhost:27017/test')
            .get("orders")
            .drop(function (err) {
                if (err) return done(err);
            });
        done();
    });

    it("can return everyting", function (done) {
        orders.find({}, {}, function (err, docs) {
            if (err) return done(err);
            docs.should.be.instanceof(Array)
                .and.have.lengthOf(100);
            done();
        });
    });
    it("can return just a single document", function (done) {
        orders.findOne({customerId: "123456"}, function (err, doc) {
            if (err) return done(err);
            doc.customerId.should.be.exactly("123456");
            done();
        });
    });
    it("can filter the fields you return", function (done) {
        orders.findOne(
            {customerId: "123456"},
            "customerId paid",
            function (err, doc) {
                if (err) return done(err);
                doc.customerId.should.be.exactly("123456");
                doc.paid.should.be.true;
                should.not.exists(doc.description);
                done();
            }
        );
    });
    it("you can sort it, with the options parameter", function (done) {
        orders.findOne(
            {},
            { sort: [
                ['description', 'desc']
            ]},
            function (err, doc) {
                if (err) return done(err);
                doc.description.should.be.exactly("Order #99");
                done();
            }
        );
    });
    it("you can page it, with the options parameter", function (done) {
        orders.find(
            {},
            { limit: 20, skip: 50},
            function (err, docs) {
                if (err) return done(err);
                docs.should.be.instanceof(Array)
                    .and.have.lengthOf(20);
                docs[0].description.should.be.exactly("Order #51");
                done();
            }
        );
    });
});
