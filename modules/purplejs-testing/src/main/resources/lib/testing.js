/* global exports */

/**
 * Testing javascript functions.
 *
 * @example
 * // Require testing functions.
 * var t = require('/lib/testing');
 *
 * @module testing
 */

/**
 * Add a test to the test suite.
 *
 * @param {String} name Name of the test.
 * @param callback Actual test function.
 */
exports.test = function (name, callback) {
    __RUNNER__.test(name, callback);
};

/**
 * Executes a function before every test.
 *
 * @param callback Function to execute before test.
 */
exports.before = function (callback) {
    __RUNNER__.before(callback);
};

/**
 * Executes a function after every test.
 *
 * @param callback Function to execute after test.
 */
exports.after = function (callback) {
    __RUNNER__.after(callback);
};

/**
 * Mock a library for require.
 *
 * @param {String} path Full path for the require library.
 * @param object The actual mocked object.
 */
exports.mock = function (path, object) {
    __.registerMock(path, object);
};

/**
 * Assert that the value is true.
 *
 * @param actual Actual value to test.
 * @param message Optional message.
 */
exports.assertTrue = function (actual, message) {
    __RUNNER__.assertTrue(actual, message || '');
};

/**
 * Assert that the value is false.
 *
 * @param actual Actual value to test.
 * @param message Optional message.
 */
exports.assertFalse = function (actual, message) {
    __RUNNER__.assertFalse(actual, message || '');
};

/**
 * Assert that the expected == actual.
 *
 * @param expected Expected value.
 * @param actual Actual value to test.
 * @param message Optional message.
 */
exports.assertEquals = function (expected, actual, message) {
    __RUNNER__.assertEquals(expected, actual, message || '');
};

/**
 * Assert that the expected != actual.
 *
 * @param expected Expected value.
 * @param actual Actual value to test.
 * @param message Optional message.
 */
exports.assertNotEquals = function (expected, actual, message) {
    __RUNNER__.assertNotEquals(expected, actual, message || '');
};


/**
 * Assert that the JSON expected == actual.
 *
 * @param expected Expected value.
 * @param actual Actual value to test.
 * @param message Optional message.
 */
exports.assertJson = function (expected, actual, message) {
    var expectedJson = JSON.stringify(expected, null, 2);
    var actualJson = JSON.stringify(actual, null, 2);
    exports.assertEquals(expectedJson, actualJson, message);
};
