var assert = Java.type('org.junit.Assert');

var array = [1, 2, 3];
assert.assertEquals(true, array instanceof Array);

var arrayJson = JSON.stringify(array);
assert.assertEquals('[1,2,3]', arrayJson);

var arrayResult = require('./library.js').testArray(array);
assert.assertEquals('[1,2,3]', arrayResult);

var object = {a: 1, b: 2, c: 3};
var objectJson = JSON.stringify(object);
assert.assertEquals('{"a":1,"b":2,"c":3}', objectJson);

var objectResult = require('./library.js').testObject(object);
assert.assertEquals('{"a":1,"b":2,"c":3}', objectResult);
