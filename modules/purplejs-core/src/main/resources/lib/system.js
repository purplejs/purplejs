var context = __executionContext;

exports.newBean = function (clz) {
    return context.newBean(clz);
};

exports.getResourceLoader = function () {
    return context.environment.resourceLoader;
};

exports.registerMock = function (path, mock) {
    context.registerMock(path, mock);
};

exports.toNativeObject = function (value) {
    return context.toNativeObject(value);
};

exports.getEngine = function () {
    return context.engine;
};

exports.disposer = function (disposer) {
    context.disposer(disposer);
};

exports.resolve = function (path) {
    return context.callingScript.parent.resolve(path).toString();
};
