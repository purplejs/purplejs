var t = require('/lib/testing');

var num = 10;

t.before(function () {
    num += 10;
});

t.after(function () {
    num = 10;
});

t.test('lifecycle1', function () {
    t.assertEquals(20, num);
});

t.test('lifecycle2', function () {
    t.assertEquals(20, num);
});
