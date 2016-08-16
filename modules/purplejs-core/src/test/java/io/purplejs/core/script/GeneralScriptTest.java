package io.purplejs.core.script;

import org.junit.Test;

import io.purplejs.core.json.JsonSerializable;
import io.purplejs.core.resource.ResourcePath;
import io.purplejs.core.value.ScriptExports;

import static org.junit.Assert.*;

public class GeneralScriptTest
    extends AbstractScriptTest
{
    @Test
    public void empty()
    {
        final ResourcePath path = ResourcePath.from( "/empty-test.js" );
        final ScriptExports exports = run( path );

        assertNotNull( exports );
        assertEquals( path, exports.getResource() );
        assertFalse( exports.hasMethod( "hello" ) );
    }

    @Test
    public void executeExported()
    {
        final ResourcePath path = ResourcePath.from( "/export-test.js" );
        final ScriptExports exports = run( path );

        assertNotNull( exports );
        assertEquals( path, exports.getResource() );
        assertTrue( exports.hasMethod( "hello" ) );
        assertEquals( "Hello World!", exports.executeMethod( "hello", "World" ).getValue() );
    }

    @Test
    public void executeExported_objectArg()
    {
        final ResourcePath path = ResourcePath.from( "/export-test.js" );
        final ScriptExports exports = run( path );

        assertNotNull( exports );
        assertEquals( path, exports.getResource() );
        assertTrue( exports.hasMethod( "helloObject" ) );

        final JsonSerializable arg = gen -> {
            gen.map();
            gen.value( "name", "World" );
            gen.end();
        };

        assertEquals( "Hello World!", exports.executeMethod( "helloObject", arg ).getValue() );
    }

    @Test
    public void cacheTest()
    {
        final ResourcePath path = ResourcePath.from( "/empty-test.js" );

        final ScriptExports exports1 = run( path );
        final ScriptExports exports2 = run( path );
        assertNotNull( exports2 );
        assertSame( exports1.getValue().getValue(), exports2.getValue().getValue() );
    }
}
