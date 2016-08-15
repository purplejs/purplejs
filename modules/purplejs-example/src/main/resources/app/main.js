// var http = require('/lib/http');

exports.get = function (req) {
    print(req.uri);

    return {
        body: 'ss'
    };
};

/*
exports.post = function (req) {



    return {
        body: http.bodyAsJson()
    };
};
*/
