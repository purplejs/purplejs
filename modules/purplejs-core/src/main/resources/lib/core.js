/* global exports */

/**
 * Core javascript functions.
 *
 * @module lib/core
 */

/**
 * Read text from a stream.
 *
 * @param stream Stream to read text from.
 * @returns {string} Returns the text read from stream.
 */
exports.readText = function (stream) {
};

/**
 * Read lines from a stream.
 *
 * @param stream Stream to read lines from.
 * @returns {string[]} Returns lines as an array.
 */
exports.readLines = function (stream) {
};

/**
 * Process lines from a stream.
 *
 * @param stream Stream to read lines from.
 * @param {function} func Callback function to be called for each line.
 */
exports.processLines = function (stream, func) {
};

/**
 * Returns the size of a stream.
 *
 * @param stream Stream to get size of.
 * @returns {number} Returns the size of a stream.
 */
exports.streamSize = function (stream) {
};

/**
 * Returns a new stream from a string.
 *
 * @param {string} text String to create a stream of.
 * @returns {*} A new stream.
 */
exports.newStream = function (text) {
};

/**
 * Returns the mime-type from a name or extension.
 *
 * @param {string} name Name of file or extension.
 * @returns {string} Mime-type of name or extension.
 */
exports.mimeType = function (name) {
};

/**
 * Loads a resource.
 *
 * @param {*} key Resource key, string or url to load.
 * @returns {*} A stream for the resource or undefined.
 */
exports.loadResource = function (key) {
};
