package io.purplejs.core.itest

import io.purplejs.core.json.JsonGenerator
import io.purplejs.core.json.JsonSerializable

class GeneralScriptTest
    extends AbstractCoreITest
{
    def "empty script"()
    {
        setup:
        file( '/test.js', '''
        ''' );

        when:
        def exports = run( '/test.js' );

        then:
        exports != null;
        exports.getResource().path == '/test.js';
        !exports.hasMethod( 'hello' );
    }

    def "export mix"()
    {
        setup:
        file( '/test.js', '''
            this.a = 15;
            exports.b = 16;
            module.exports.c = 17;
        ''' );

        when:
        def exports = run( '/test.js' );

        then:
        exports != null;
        exports.value.hasMember( 'a' );
        exports.value.hasMember( 'b' );
        exports.value.hasMember( 'c' );
    }

    def "export everything"()
    {
        setup:
        file( '/test.js', '''
            module.exports = {
                a: 15
            };
        ''' );

        when:
        def exports = run( '/test.js' );

        then:
        exports != null;
        exports.value.hasMember( 'a' );
    }

    def "execute exported"()
    {
        setup:
        file( '/test.js', '''
            exports.hello = function (name) {
                return "Hello " + name + "!";
            };
        ''' );

        when:
        def exports = run( '/test.js' );

        then:
        exports != null;
        exports.hasMethod( 'hello' );
        exports.executeMethod( 'hello', 'World' ).getValue() == 'Hello World!';
    }

    def "execute exported with object argument"()
    {
        setup:
        file( '/test.js', '''
            exports.hello = function (arg) {
                return "Hello " + arg.name + "!";
            };
        ''' );

        def arg = new JsonSerializable() {
            @Override
            void serialize( final JsonGenerator gen )
            {
                gen.map();
                gen.value( "name", "World" );
                gen.end();
            }
        }

        when:
        def exports = run( '/test.js' );

        then:
        exports != null;
        exports.hasMethod( 'hello' );
        exports.executeMethod( 'hello', arg ).getValue() == 'Hello World!';
    }

    def "test script cache"()
    {
        setup:
        file( '/test.js', '''
        ''' );

        when:
        def exports1 = run( '/test.js' );
        def exports2 = run( '/test.js' );

        then:
        exports1 != null;
        exports2 != null;
        exports1.value.value == exports2.value.value;
    }
}
