/* global Java */
/* global resolve */
var assert = Java.type('org.junit.Assert');

exports.test = function () {
    var relative = resolve('./test.html').toString();
    assert.assertEquals('/app/resolve/other/test.html', relative);

    var absolute = resolve('/app/test.html').toString();
    assert.assertEquals('/app/test.html', absolute);
};
