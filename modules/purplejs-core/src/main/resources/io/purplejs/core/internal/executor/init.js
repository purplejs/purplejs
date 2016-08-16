(function (__, require, resolve, log) {
    var exports = {};

    var module = {};
    module.id = __.resource.toString();

    Object.defineProperty(module, 'exports', {
        get: function () {
            return exports;
        },
        set: function (value) {
            exports = value;
        }
    });

    /*__script__*/
    return module.exports;
});