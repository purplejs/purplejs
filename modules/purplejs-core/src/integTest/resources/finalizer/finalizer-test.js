var executed = false;

__.finalizer(function () {
    executed = true;
});

exports.isExecuted = function () {
    return executed;
};
