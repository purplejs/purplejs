package io.purplejs.core.internal.json

import io.purplejs.core.internal.nashorn.NashornRuntime
import io.purplejs.core.internal.nashorn.NashornRuntimeFactory
import io.purplejs.core.json.JsonGenerator
import io.purplejs.core.json.JsonSerializable
import io.purplejs.core.value.ScriptValue
import spock.lang.Specification

class ScriptJsonGeneratorTest
    extends Specification
{
    private NashornRuntime runtime;

    def setup()
    {
        final ClassLoader classLoader = getClass().getClassLoader();
        this.runtime = new NashornRuntimeFactory().newRuntime( classLoader );
    }

    def "should be array"()
    {
        when:
        serialize( { gen -> gen.value( 11 ); } );

        then:
        thrown IllegalArgumentException;
    }

    def "should be object"()
    {
        when:
        serialize( { gen -> gen.value( "a", 11 ); } );

        then:
        thrown IllegalArgumentException;
    }

    def "serialize map"()
    {
        when:
        def json = serialize( { gen ->
            gen.map();
            gen.value( "a", 1 );
            gen.value( "z", null );

            gen.map( "b" );
            gen.end();

            gen.array( "c" );
            gen.end();

            gen.value( "d", new JsonSerializable() {
                @Override
                void serialize( final JsonGenerator gen1 )
                {
                    gen1.map();
                    gen1.value( "a", true );
                    gen1.end();
                }
            } );

            gen.end();

            // One extra to check for safety
            gen.end();
        } );

        then:
        json == '{"a":1,"b":{},"c":[],"d":{"a":true}}';
    }

    def "serialize array"()
    {
        when:
        def json = serialize( { gen ->
            gen.array();
            gen.value( 1 );

            gen.map();
            gen.end();

            gen.array();
            gen.end();

            gen.value( new JsonSerializable() {
                @Override
                void serialize( final JsonGenerator gen1 )
                {
                    gen1.map();
                    gen1.value( "a", true );
                    gen1.end();
                }
            } );

            gen.end();
        } );

        then:
        json == '[1,{},[],{"a":true}]';
    }

    def "serialize ScriptValue"()
    {
        setup:
        def value = Mock( ScriptValue.class );
        value.raw >> 11;

        when:
        def json = serialize( { gen ->
            gen.map();
            gen.value( 'a', value );
            gen.end();
        } );

        then:
        json == '{"a":11}';
    }

    private String serialize( final JsonSerializable serializable )
    {
        final ScriptJsonGenerator generator = new ScriptJsonGenerator( this.runtime );
        serializable.serialize( generator );

        final Object root = generator.getRoot();
        return this.runtime.toJsonString( root );
    }
}
