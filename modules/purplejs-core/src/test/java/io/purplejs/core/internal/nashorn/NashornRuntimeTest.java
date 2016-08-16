package io.purplejs.core.internal.nashorn;

import org.junit.Before;
import org.junit.Test;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

import static org.junit.Assert.*;

public class NashornRuntimeTest
{
    private NashornRuntime runtime;

    @Before
    public void setUp()
    {
        final ClassLoader classLoader = getClass().getClassLoader();
        this.runtime = new NashornRuntimeFactory().newRuntime( classLoader );
    }

    @Test
    public void getEngine()
    {
        assertNotNull( this.runtime.getEngine() );
    }

    @Test
    public void newJsArray()
    {
        final ScriptObjectMirror value = this.runtime.newJsArray();
        assertNotNull( value );
        assertEquals( "Array", value.getClassName() );
    }

    @Test
    public void newJsObject()
    {
        final ScriptObjectMirror value = this.runtime.newJsObject();
        assertNotNull( value );
        assertEquals( "Object", value.getClassName() );
    }

    @Test
    public void toJsonString()
    {
        final ScriptObjectMirror array = this.runtime.newJsArray();
        final String value1 = this.runtime.toJsonString( array );
        assertEquals( "[]", value1 );

        final ScriptObjectMirror object = this.runtime.newJsObject();
        final String value2 = this.runtime.toJsonString( object );
        assertEquals( "{}", value2 );
    }
}

