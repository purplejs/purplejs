/* global exports */

var core = require('/lib/core');

var helper = __.newBean('io.purplejs.http.internal.lib.HttpLibHelper');
helper.init(__);

function getRequest() {
    return helper.getRequest();
}

function isJsonBody() {
    return helper.isJsonBody();
}

function bodyAsStream() {
    return getRequest().body;
}

function bodyAsText() {
    return core.readText(bodyAsStream());
}

function bodyAsJson() {
    if (isJsonBody()) {
        return JSON.parse(bodyAsText());
    }

    return undefined;
}

// Returns the current request
exports.getRequest = getRequest;

// Returns true if body is of type text.
exports.isJsonBody = isJsonBody;

// Returns body as text if applicable.
exports.bodyAsText = bodyAsText;

// Returns body as text if applicable.
exports.bodyAsJson = bodyAsJson;

// Returns body as stream if applicable.
exports.bodyAsStream = bodyAsStream;

exports.isMultipart = function () {
    return helper.isMultipart();
};

exports.getMultipartForm = function () {
    return __.toNativeObject(helper.getMultipartForm());
};

exports.getMultipartItem = function (name, index) {
    return __.toNativeObject(helper.getMultipartItem(name, index || 0));
};
