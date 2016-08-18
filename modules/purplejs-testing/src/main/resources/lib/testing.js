var Assert = Java.type('org.junit.Assert');

exports.test = function (name, callback) {
    __RUNNER__.test(name, callback);
};

exports.before = function (callback) {
    __RUNNER__.before(callback);
};

exports.after = function (callback) {
    __RUNNER__.after(callback);
};

exports.mock = function (path, object) {
    __.registerMock(path, object);
};

exports.assertEquals = function (expected, actual) {
    Assert.assertEquals(expected, actual);
};
