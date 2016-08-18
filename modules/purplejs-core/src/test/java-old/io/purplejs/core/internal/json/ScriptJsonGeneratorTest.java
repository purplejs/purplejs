package io.purplejs.core.internal.json;

import org.junit.Before;
import org.junit.Test;

import io.purplejs.core.internal.nashorn.NashornRuntime;
import io.purplejs.core.internal.nashorn.NashornRuntimeFactory;
import io.purplejs.core.json.JsonSerializable;

import static org.junit.Assert.*;

public class ScriptJsonGeneratorTest
{
    private NashornRuntime runtime;

    @Before
    public void setUp()
    {
        final ClassLoader classLoader = getClass().getClassLoader();
        this.runtime = new NashornRuntimeFactory().newRuntime( classLoader );
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldBeArray()
    {
        serialize( ( gen ) -> {
            gen.value( 11 );
        } );
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldBeObject()
    {
        serialize( ( gen ) -> {
            gen.value( "a", 11 );
        } );
    }

    @Test
    public void serialize_map()
    {
        final String json = serialize( ( gen ) -> {
            gen.map();
            gen.value( "a", 1 );
            gen.value( "z", null );

            gen.map( "b" );
            gen.end();

            gen.array( "c" );
            gen.end();

            gen.value( "d", (JsonSerializable) gen1 -> {
                gen1.map();
                gen1.value( "a", true );
                gen1.end();
            } );

            gen.end();

            // One extra to check for safety
            gen.end();
        } );

        assertEquals( "{\"a\":1,\"b\":{},\"c\":[],\"d\":{\"a\":true}}", json );
    }

    @Test
    public void serialize_array()
    {
        final String json = serialize( ( gen ) -> {
            gen.array();
            gen.value( 1 );

            gen.map();
            gen.end();

            gen.array();
            gen.end();

            gen.value( (JsonSerializable) gen1 -> {
                gen1.map();
                gen1.value( "a", true );
                gen1.end();
            } );

            gen.end();
        } );

        assertEquals( "[1,{},[],{\"a\":true}]", json );
    }

    private String serialize( final JsonSerializable serializable )
    {
        final ScriptJsonGenerator generator = new ScriptJsonGenerator( this.runtime );
        serializable.serialize( generator );

        final Object root = generator.getRoot();
        return this.runtime.toJsonString( root );
    }
}
