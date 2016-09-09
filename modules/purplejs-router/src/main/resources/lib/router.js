/* global exports */

/**
 * Router javascript functions.
 *
 * @example
 * // Creates a new router.
 * var router = require('/lib/router')();
 *
 * @module router
 */

/**
 * Creates a new router.
 *
 * @constructor
 */
function Router() {
    this.router = __.newBean('io.purplejs.router.internal.Router');
    this.filters = [];
}

/**
 * Adds a route to this router.
 *
 * @example
 * // Add a route.
 * router.route('GET', '/persons', function(req) {
 *  ...
 * });
 *
 * @param {String} method Method to match. '*' for everyone.
 * @param {String} pattern Path pattern to match.
 * @param handler Handler to execute on match.
 */
Router.prototype.route = function (method, pattern, handler) {
    this.router.add(method.toUpperCase(), pattern, handler);
};

/**
 * Adds a route that matches GET method.
 *
 * @example
 * // Add a route.
 * router.get('/persons', function(req) {
 *  ...
 * });
 *
 * @param {String} pattern Path pattern to match.
 * @param handler Handler to execute on match.
 */
Router.prototype.get = function (pattern, handler) {
    this.route('GET', pattern, handler);
};

/**
 * Adds a route that matches POST method.
 *
 * @example
 * // Add a route.
 * router.post('/persons', function(req) {
 *  ...
 * });
 *
 * @param {String} pattern Path pattern to match.
 * @param handler Handler to execute on match.
 */
Router.prototype.post = function (pattern, handler) {
    this.route('POST', pattern, handler);
};

/**
 * Adds a route that matches DELETE method.
 *
 * @example
 * // Add a route.
 * router.delete('/persons/{id}', function(req) {
 *  ...
 * });
 *
 * @param {String} pattern Path pattern to match.
 * @param handler Handler to execute on match.
 */
Router.prototype.delete = function (pattern, handler) {
    this.route('DELETE', pattern, handler);
};

/**
 * Adds a route that matches PUT method.
 *
 * @example
 * // Add a route.
 * router.put('/persons/{id}', function(req) {
 *  ...
 * });
 *
 * @param {String} pattern Path pattern to match.
 * @param handler Handler to execute on match.
 */
Router.prototype.put = function (pattern, handler) {
    this.route('PUT', pattern, handler);
};

/**
 * Adds a route that matches HEAD method.
 *
 * @example
 * // Add a route.
 * router.head('/persons', function(req) {
 *  ...
 * });
 *
 * @param {String} pattern Path pattern to match.
 * @param handler Handler to execute on match.
 */
Router.prototype.head = function (pattern, handler) {
    this.route('HEAD', pattern, handler);
};

/**
 * Adds a route that matches all methods.
 *
 * @example
 * // Add a route.
 * router.all('/persons/{id}', function(req) {
 *  ...
 * });
 *
 * @param {String} pattern Path pattern to match.
 * @param handler Handler to execute on match.
 */
Router.prototype.all = function (pattern, handler) {
    this.route('*', pattern, handler);
};

/**
 * Adds a filter to this router.
 *
 * @example
 * // Add a filter.
 * router.filter(function(req, next) {
 *  return next(req);
 * });
 *
 * @param filter Filter handler to execute.
 */
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

/**
 * Dispatch the request to this router.
 *
 * @example
 * // Delegate to the router.
 * exports.service = function(req) {
 *   return router.dispatch(req);
 * };
 *
 * @param req Actual request..
 * @returns Response output.
 */
Router.prototype.dispatch = function (req) {
    return nextInChain(this, this.filters)(req);
};

/**
 * Creates a new router instance.
 *
 * @return {Router} a new router instance.
 */
module.exports = function () {
    return new Router();
};
