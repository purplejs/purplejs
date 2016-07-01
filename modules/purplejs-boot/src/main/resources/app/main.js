var httpLib = require('/lib/http');

exports.service = function (req) {

    print(httpLib.isMultipartBody());
    
    return {
        status: 200,
        body: req,
        contentType: 'application/json'
    };

};
