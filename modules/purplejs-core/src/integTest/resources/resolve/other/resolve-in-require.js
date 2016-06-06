var assert = Java.type('org.junit.Assert');

exports.test = function () {
    var relative = resolve('./test.html').toString();
    assert.assertEquals('/resolve/other/test.html', relative);

    var absolute = resolve('/test.html').toString();
    assert.assertEquals('/test.html', absolute);
};
