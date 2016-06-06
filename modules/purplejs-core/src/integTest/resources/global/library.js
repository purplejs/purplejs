var assert = Java.type('org.junit.Assert');

exports.testArray = function (value) {
    assert.assertEquals('Array should be instanceof Array', true, value instanceof Array);
    return JSON.stringify(value);
};

exports.testObject = function (value) {
    return JSON.stringify(value);
};
