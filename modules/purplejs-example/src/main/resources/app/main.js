function Route(method, path, handler) {
    this.method = method;
    this.path = path;
    this.handler = handler;
}

Route.prototype.matchesPath = function (req) {
    return (req.path === this.path);
};

Route.prototype.matches = function (req) {
    var flag = (req.method === this.method);

    if (this.method === '*') {
        flag = true;
    }

    flag &= this.matchesPath(req);
    return flag;
};

Route.prototype.service = function (req) {
    return this.handler(req);
};

function Router() {
    this.routes = [];
}

Router.prototype.pushRoute = function (method, path, handler) {
    this.routes.push(new Route(method, path, handler));

    this.routes.sort(function (a, b) {
        return b.path.length - a.path.length;
    });
};

Router.prototype.all = function (path, handler) {
    this.pushRoute('*', path, handler);
};

Router.prototype.get = function (path, handler) {
    this.pushRoute('GET', path, handler);
};

Router.prototype.head = function (path, handler) {
    this.pushRoute('HEAD', path, handler);
};

Router.prototype.post = function (path, handler) {
    this.pushRoute('POST', path, handler);
};

Router.prototype.findRoute = function (req) {
    for (var i = 0; i < this.routes.length; i++) {
        var route = this.routes[i];
        if (route.matches(req)) {
            return route;
        }
    }

    return undefined;
};

Router.prototype.service = function (req) {
    /*
     var route = this.findRoute(req);

     if (route) {
     return route.service(req);
     }

     return {
     status: 404
     };
     */

    return {
        body: {
            routes: this.routes
        }
    }
};


function newRouter() {
    return new Router();
}


var router = newRouter();

router.get('/a/b/c', function (req) {
    return { 
        body: '/a/b/c'
    };
});

router.get('/a/b', function (req) {
    return {
        body: '/a/b'
    };
});

module.exports.service = function (req) {
    return router.service(req);
};

