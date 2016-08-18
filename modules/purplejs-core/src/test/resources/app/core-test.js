/* global Java */
var assert = Java.type('org.junit.Assert');

// Require lib
var core = require('/lib/core');

// some assert methods
function assertEqualsInt(expected, actual) {
    assert['assertEquals(long,long)'](expected, actual);
}

// Test newStream
exports.testNewStream = function () {
    var stream = core.newStream('hello');
    assert.assertTrue(stream != undefined);
    assertEqualsInt(5, core.streamSize(stream));
};

// Test streamSize
exports.testStreamSize = function () {
    var stream = core.newStream('hello');
    assertEqualsInt(5, core.streamSize(stream));
    assertEqualsInt(0, core.streamSize('test'));
};

// Test readText
exports.testReadText = function () {
    var stream = core.newStream('hello');
    assert.assertEquals('hello', core.readText(stream));
    assert.assertEquals('', core.readText('hello'));
};

// Test readLines
exports.testReadLines = function () {
    var stream = core.newStream('1\n2\n3');

    var lines = core.readLines(stream);
    assert.assertEquals('["1","2","3"]', JSON.stringify(lines));
    assert.assertEquals('[]', JSON.stringify(core.readLines('')));
};
