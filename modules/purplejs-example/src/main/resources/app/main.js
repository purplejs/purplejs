var http = require('/lib/http');

exports.get = function (req) {
    return {
        body: req
    };
};

exports.post = function (req) {



    return {
        body: http.bodyAsJson()
    };
};
