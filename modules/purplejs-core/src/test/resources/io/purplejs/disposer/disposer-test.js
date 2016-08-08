/* global __ */

var executed = false;

__.disposer(function () {
    executed = true;
});

exports.isExecuted = function () {
    return executed;
};
