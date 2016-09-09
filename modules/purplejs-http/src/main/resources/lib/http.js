/* global exports */

/**
 * Http related javascript functions.
 *
 * @example
 * // Require http functions.
 * var http = require('/lib/http');
 *
 * @module http
 */

// Require core functions.
var core = require('/lib/core');

// Create a new instance of the helper and initialize with a request provider.
var helper = __.newBean('io.purplejs.http.internal.lib.HttpLibHelper');
helper.requestProvider = __.getProvider('io.purplejs.http.Request');

function bodyAsText() {
    return core.readText(helper.getRequest().body);
}

function bodyAsJson() {
    if (helper.isJsonBody()) {
        return JSON.parse(bodyAsText());
    }

    return undefined;
}

/**
 * Returns the current wrapped request. This is a Java object and cannot be serialized to JSON.
 *
 * @example
 * // Return a Java representation of the current request.
 * var request = http.getRequest();
 *
 * @returns {*} Current request as a Java object.
 */
exports.getRequest = function () {
    return helper.getRequest();
};

/**
 * Returns true if the request-body is json type.
 *
 * @return {boolean} true if the request-body is json.
 */
exports.isJsonBody = function () {
    return helper.isJsonBody();
};

/**
 * Returns the body as a text string.
 *
 * @return {string} body as a text string.
 */
exports.bodyAsText = function () {
    return bodyAsText();
};

/**
 * Returns the body as json.
 *
 * @return {*} body as json.
 */
exports.bodyAsJson = function () {
    return bodyAsJson();
};

/**
 * Returns the body as a binary stream.
 *
 * @return {*} body as a binary stream.
 */
exports.bodyAsStream = function () {
    return helper.getRequest().body;
};

/**
 * Returns true if the request-body is a multipart form.
 *
 * @return {boolean} if request-body is a multipart form.
 */
exports.isMultipart = function () {
    return helper.isMultipart();
};

/**
 * Returns multipart form if the content-type is multipart.
 *
 * @return {*} the multipart form.
 */
exports.getMultipartForm = function () {
    return __.toNativeObject(helper.getMultipartForm());
};

/**
 * Returns multipart item for name and optional index..
 *
 * @param {string} name Name of the multipart item.
 * @param {number?} index Optional index if it's multiple items with same name.
 * @return {*} the named multipart item.
 */
exports.getMultipartItem = function (name, index) {
    return __.toNativeObject(helper.getMultipartItem(name, index || 0));
};
