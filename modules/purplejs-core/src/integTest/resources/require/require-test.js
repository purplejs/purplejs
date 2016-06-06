var assert = Java.type('org.junit.Assert');

var other = require('other');
assert.assertEquals('Hello World!', other.hello('World'));

other = require('./other.js');
assert.assertEquals('Hello World!', other.hello('World'));

other = require('/require/other.js');
assert.assertEquals('Hello World!', other.hello('World'));

var util = require('util');
assert.assertEquals('Hello from Lib!', util.hello());

var all1 = require('./export/all1');
assert.assertEquals('1', all1.a);
assert.assertEquals('2', all1.b);

var all2 = require('export/all2');
assert.assertEquals('1', all2.a);
assert.assertEquals('2', all2.b);
