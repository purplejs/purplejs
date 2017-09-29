package io.purplejs.core.itest

class MockScriptTest
    extends AbstractCoreITest
{
    def "test mocking"()
    {
        setup:
        file( '/library.js', '''
            exports.hello = function () {
                return 'Hello World';
            };
        ''' );

        file( '/test.js', '''
            var system = require('/lib/system');
            var lib = require('./library');

            t.assertEquals('Hello World', lib.hello());

            system.registerMock('/library.js', {
                hello: function () {
                    return 'Hello Mock';
                }
            });

            lib = require('./library');
            t.assertEquals('Hello Mock', lib.hello());
        ''' );

        when:
        def exports = run( '/test.js' );

        then:
        exports != null;
    }
}
