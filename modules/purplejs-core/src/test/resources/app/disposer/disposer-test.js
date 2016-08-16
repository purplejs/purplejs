/* global __ */
var assert = Java.type('org.junit.Assert');

var executed = false;

__.disposer(function () {
    executed = true;
});

assert.assertFalse(executed);
__.engine.dispose();
assert.assertTrue(executed);
