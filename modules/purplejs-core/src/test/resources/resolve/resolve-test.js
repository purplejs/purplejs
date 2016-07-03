/* global Java */
/* global resolve */
var assert = Java.type('org.junit.Assert');

require('other/resolve-in-require').test();
require('other/resolve-in-require').test();

var relative = resolve('./test.html').toString();
assert.assertEquals('/resolve/test.html', relative);

var absolute = resolve('/test.html').toString();
assert.assertEquals('/test.html', absolute);
