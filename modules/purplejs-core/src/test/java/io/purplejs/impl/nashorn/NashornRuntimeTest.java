package io.purplejs.impl.nashorn;

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
}
