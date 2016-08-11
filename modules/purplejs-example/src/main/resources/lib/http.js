var io = require('/lib/io');

// Find request provider
var requestProvider = __.getProvider('io.purplejs.http.Request');

// Get current request
function request() {
    return requestProvider.get();
}

function isJsonBody() {
    var type = request().contentType.toString();
    return isJsonType(type);
}

function isJsonType(type) {
    return type.equals("application/json");
}

function bodyAsStream() {
    return request().body;
}

function bodyAsText() {
    return io.streamAsText(bodyAsStream());
}

function bodyAsJson() {
    if (isJsonBody()) {
        return JSON.parse(bodyAsText());
    }

    return undefined;
}

// Returns the current request
module.exports.request = request;

// Returns true if body is of type text.
module.exports.isJsonBody = isJsonBody;

// Returns body as text if applicable.
module.exports.bodyAsText = bodyAsText;

// Returns body as text if applicable.
module.exports.bodyAsJson = bodyAsJson;

// Returns body as stream if applicable.
module.exports.bodyAsStream = bodyAsStream;
