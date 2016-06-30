package io.purplejs.impl.executor;

import javax.script.ScriptEngine;

import org.junit.Before;
import org.junit.Test;

import io.purplejs.resource.ResourcePath;

import static org.junit.Assert.*;

public class ScriptContextImplTest
{
    private ResourcePath resource;

    private ScriptContextImpl context;

    @Before
    public void setup()
    {
        this.resource = ResourcePath.from( "/a/b/test.js" );
        this.context = new ScriptContextImpl( this.resource );
    }

    @Test
    public void getAttribute()
    {
        assertNull( this.context.getAttribute( "test" ) );

        final Object fileName = this.context.getAttribute( ScriptEngine.FILENAME );
        assertEquals( this.resource.getPath(), fileName );
    }
}
