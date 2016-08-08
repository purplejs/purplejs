
exports.service = function (req) {

    return {
        status: 200,
        body: req,
        contentType: 'application/json'
    };

};
