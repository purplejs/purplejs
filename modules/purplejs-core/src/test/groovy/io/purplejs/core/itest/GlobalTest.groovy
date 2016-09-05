package io.purplejs.core.itest

import io.purplejs.core.EngineBinder

class GlobalTest
    extends AbstractCoreITest
{
    @Override
    protected void configureModule( final EngineBinder binder )
    {
        super.configureModule( binder );
        binder.globalVariable( "globalVar", "hello" );
    }

    def "global variable"()
    {
        setup:
        file( '/test.js', '''
            t.assertEquals("hello", globalVar);
        ''' );

        when:
        def exports = run( '/test.js' );

        then:
        exports != null;
    }

    def "test array"()
    {
        setup:
        file( '/test.js', '''
            var array = [1, 2, 3];
            t.assertEquals(true, array instanceof Array);

            var arrayJson = JSON.stringify(array);
            t.assertEquals('[1,2,3]', arrayJson);
        ''' );

        when:
        def exports = run( '/test.js' );

        then:
        exports != null;
    }

    def "test object"()
    {
        setup:
        file( '/test.js', '''
            var object = {a: 1, b: 2, c: 3};
            var objectJson = JSON.stringify(object);
            t.assertEquals('{"a":1,"b":2,"c":3}', objectJson);
        ''' );

        when:
        def exports = run( '/test.js' );

        then:
        exports != null;
    }

    def "test array from exports"()
    {
        setup:
        file( '/library.js', '''
            exports.test = function (value) {
                t.assertEquals(true, value instanceof Array);
                return JSON.stringify(value);
            };
        ''' );

        file( '/test.js', '''
            var array = [1, 2, 3];

            var arrayResult = require('./library.js').test(array);
            t.assertEquals('[1,2,3]', arrayResult);
        ''' );

        when:
        def exports = run( '/test.js' );

        then:
        exports != null;
    }


    def "test object from exports"()
    {
        setup:
        file( '/library.js', '''
            exports.test = function (value) {
                return JSON.stringify(value);
            };
        ''' );

        file( '/test.js', '''
            var object = {a: 1, b: 2, c: 3};

            var objectResult = require('./library.js').test(object);
            t.assertEquals('{"a":1,"b":2,"c":3}', objectResult);
        ''' );

        when:
        def exports = run( '/test.js' );

        then:
        exports != null;
    }

    def "test execute export"()
    {
        setup:
        file( '/test.js', '''
            exports.func = function() {
                this.a = 11;

                t.assertNotEquals(undefined, this);
                return this.a;
            };
        ''' );

        when:
        def exports = run( '/test.js' );
        def result = exports.executeMethod( 'func' );

        then:
        result.getValue() == 11;
    }
}
