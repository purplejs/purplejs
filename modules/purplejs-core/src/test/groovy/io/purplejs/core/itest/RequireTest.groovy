package io.purplejs.core.itest

class RequireTest
    extends AbstractIntegrationTest
{
    def "require relative"()
    {
        setup:
        file( '/a/b/other.js', '''
            exports.hello = function() {
                return 'Hello World';
            };
        ''' );

        file( '/a/test.js', '''
            var other = require('./b/other');
            t.assertEquals('Hello World', other.hello());
        ''' );

        when:
        def exports = run( '/a/test.js' );

        then:
        exports != null;
    }

    def "require absolute"()
    {
        setup:
        file( '/a/b/other.js', '''
            exports.hello = function() {
                return 'Hello World';
            };
        ''' );

        file( '/a/test.js', '''
            var other = require('/a/b/other');
            t.assertEquals('Hello World', other.hello());
        ''' );

        when:
        def exports = run( '/a/test.js' );

        then:
        exports != null;
    }

    def "require json"()
    {
        setup:
        file( '/a/test.json', '''
            {
                "a": 1,
                "b": 2
            }
        ''' );

        file( '/a/test.js', '''
            var json = require('./test.json');
            t.assertEquals('{"a":1,"b":2}', JSON.stringify(json));
        ''' );

        when:
        def exports = run( '/a/test.js' );

        then:
        exports != null;
    }
}
