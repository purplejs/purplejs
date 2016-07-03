/* global Java */
var assert = Java.type('org.junit.Assert');

var moment = require('./moment.js');
var date = moment("12-25-1995", "MM-DD-YYYY");
assert.assertEquals('1995', date.format('YYYY'));
