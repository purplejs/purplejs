exports.get = function (req) {
    if (!req.webSocket) {
        return {
            body: 'Hello Script!'
        };
    }

    return {
        webSocket: {}
    }
};
