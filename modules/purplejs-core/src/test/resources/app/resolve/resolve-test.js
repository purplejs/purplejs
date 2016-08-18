/* global Java */
/* global resolve */
var assert = Java.type('org.junit.Assert');

require('./other/resolve-in-require').test();
require('./other/resolve-in-require').test();

var relative = resolve('./test.html').toString();
assert.assertEquals('/app/resolve/test.html', relative);

var absolute = resolve('/app/test.html').toString();
assert.assertEquals('/app/test.html', absolute);
