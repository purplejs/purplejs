function Router() {
    this.router = __.newBean('io.purplejs.router.internal.Router');
    this.filters = [];
}

Router.prototype.route = function (method, pattern, handler) {
    this.router.add(method.toUpperCase(), pattern, handler);
};

Router.prototype.get = function (pattern, handler) {
    this.route('GET', pattern, handler);
};

Router.prototype.post = function (pattern, handler) {
    this.route('POST', pattern, handler);
};

Router.prototype.delete = function (pattern, handler) {
    this.route('DELETE', pattern, handler);
};

Router.prototype.put = function (pattern, handler) {
    this.route('PUT', pattern, handler);
};

Router.prototype.head = function (pattern, handler) {
    this.route('HEAD', pattern, handler);
};

Router.prototype.all = function (pattern, handler) {
    this.route('*', pattern, handler);
};

Router.prototype.filter = function (filter) {
    this.filters.push(filter);
};

function handleRoute(scope, req) {
    var match = scope.router.matches(req.method, req.path);

    if (!match) {
        return {
            status: 404
        };
    }

    req.pathParams = {};
    match.appendPathParams(req.pathParams);
    return match.getHandler()(req);
}

function nextInChain(scope, filters) {
    var last = function (req) {
        return handleRoute(scope, req);
    };

    if (filters.length == 0) {
        return last;
    } else {
        return function (req) {
            var next = nextInChain(scope, filters.slice(1));
            return filters[0](req, next);
        }
    }
}

Router.prototype.dispatch = function (req) {
    return nextInChain(this, this.filters)(req);
};

module.exports = function () {
    return new Router();
};
