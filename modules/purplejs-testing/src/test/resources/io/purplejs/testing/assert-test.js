var t = require('/lib/testing');

t.test('assert', function () {
    t.assertFalse(false);
    t.assertTrue(true);
    t.assertEquals(1, 1);
    t.assertNotEquals(1, 2);
    t.assertEquals('1', '1');
    t.assertNotEquals('1', '2');
    t.assertJson({}, {});
});

t.test('assert with message', function () {
    t.assertFalse(false, 'message');
    t.assertTrue(true, 'message');
    t.assertEquals(1, 1, 'message');
    t.assertNotEquals(1, 2, 'message');
    t.assertEquals('1', '1', 'message');
    t.assertNotEquals('1', '2', 'message');
    t.assertJson({}, {}, 'message');
});
