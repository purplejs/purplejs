function Router() {
    this.routes = {};
}

Router.prototype.get = function (path, handler) {
};

Router.prototype.service = function (req) {
    return {
        body: {}
    };
};


module.exports.newRouter = function () {
    return new Router();
};
