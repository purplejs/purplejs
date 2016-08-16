var t = require('/lib/testing');

var i = 0;

t.before(function () {
    i = 100;
});

t.after(function () {
    i = -1;
});

t.test('mytest #1', function (test) {
    print(test);
});

t.test('mytest #2', function () {
    print(i);
});
