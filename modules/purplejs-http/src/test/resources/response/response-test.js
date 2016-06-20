exports.emtpy = function () {
    return {};
};

exports.statusOnly = function () {
    return {
        status: 201
    }
};

exports.all = function () {
    return {
        status: 200,
        body: 'text',
        contentType: 'text/plain',
        headers: {
            'X-Header-1': 'value1',
            'X-Header-2': 'value2'
        }
    }
};

exports.redirect = function () {
    return {
        redirect: 'http://foo.bar'
    }
};

exports.wrong = function () {
    return {
        headers: []
    }
};

exports.textBody = function () {
    return {
        body: 'text',
    }
};

exports.functionBody = function () {
    return {
        body: function () {
            return 'text-in-function'
        }
    }
};

exports.argBody = function (obj) {
    return {
        body: obj
    }
};

exports.jsonArrayBody = function () {
    return {
        body: [1, 2, 3, {
            a: 1,
            b: 2,
            c: 3
        }]
    }
};

exports.jsonObjectBody = function () {
    return {
        body: {
            a: 1,
            b: 2,
            c: 3,
            d: [1, 2, 3]
        }
    }
};

exports.cookies = function () {
    return {
        cookies: {
            "cookie1": "value1",
            "cookie2": {
                "value": "value2",
                "path": "/a/b",
                "domain": "foo.com",
                "comment": "a cookie",
                "maxAge": 100,
                "secure": true,
                "httpOnly": true
            }
        }
    }
};


