exports.service = function (req) {

    return {
        status: 200,
        body: 'Hello',
        contentType: 'text/plain',
        headers: {
            'X-Header': "value"
        },
        cookies: {
            'cookie1': 'value1'
        }
    };

};
