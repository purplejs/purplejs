/* global __ */

var helper = __.newBean('io.purplejs.http.impl.lib.RequestHelper');
helper.init(__.registry);

function rawRequest() {
    return helper.getRequest();
}

function isJsonType(type) {
    return helper.isJsonType(type);
}

function isTextType(type) {
    return helper.isTextType(type);
}

function isTextBody() {
    return helper.isTextBody();
}

function isJsonBody() {
    return helper.isJsonBody();
}

function isMultipartBody() {
    return helper.isMultipartBody();
}

function bodyAsStream() {
    return helper.getBodyAsStream();
}

function bodyAsString() {
    return helper.getBodyAsString();
}

function bodyAsJson() {
    if (isJsonBody()) {
        return JSON.parse(bodyAsString());
    }
}

exports.rawRequest = rawRequest;
exports.isJsonType = isJsonType;
exports.isTextType = isTextType;
exports.isTextBody = isTextBody;
exports.isJsonBody = isJsonBody;
exports.isMultipartBody = isMultipartBody;
exports.bodyAsStream = bodyAsStream;
exports.bodyAsString = bodyAsString;
exports.bodyAsJson = bodyAsJson;
