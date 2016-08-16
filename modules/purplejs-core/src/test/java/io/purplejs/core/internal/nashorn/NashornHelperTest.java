package io.purplejs.core.internal.nashorn;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.script.ScriptEngine;

import org.junit.Before;
import org.junit.Test;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.runtime.ScriptRuntime;

import static org.junit.Assert.*;

public class NashornHelperTest
{
    private ScriptEngine engine;

    @Before
    public void setUp()
    {
        this.engine = createEngine();
    }

    private ScriptEngine createEngine()
    {
        final ClassLoader classLoader = getClass().getClassLoader();
        final NashornRuntimeFactory runtimeFactory = new NashornRuntimeFactory();

        return runtimeFactory.newRuntime( classLoader ).getEngine();
    }

    @Test
    public void newScriptEngine()
    {
        final ScriptEngine engine1 = createEngine();
        assertNotNull( engine1 );

        final ScriptEngine engine2 = createEngine();
        assertNotNull( engine2 );

        assertNotSame( engine1, engine2 );
    }

    @Test
    public void isUndefined()
    {
        assertFalse( NashornHelper.INSTANCE.isUndefined( 11 ) );
        assertTrue( NashornHelper.INSTANCE.isUndefined( null ) );
        assertTrue( NashornHelper.INSTANCE.isUndefined( ScriptRuntime.UNDEFINED ) );
    }

    @Test
    public void isDateType()
        throws Exception
    {
        final Object value1 = execute( "var result = {}; result;" );
        assertFalse( NashornHelper.INSTANCE.isDateType( value1 ) );

        final Object value2 = execute( "var result = new Date(); result;" );
        assertTrue( NashornHelper.INSTANCE.isDateType( value2 ) );
    }

    @Test
    public void toDate()
        throws Exception
    {
        final ScriptObjectMirror value =
            (ScriptObjectMirror) execute( "var result = new Date(Date.parse('1995-11-12T22:24:25Z')); result;" );
        final Date date = NashornHelper.INSTANCE.toDate( value );

        final SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ssZ" );
        format.setTimeZone( TimeZone.getTimeZone( "UTC" ) );

        assertEquals( "1995-11-12T22:24:25+0000", format.format( date ) );
    }

    @Test
    public void isArrayType()
        throws Exception
    {
        final Object value1 = execute( "var result = 11; result;" );
        assertFalse( NashornHelper.INSTANCE.isArrayType( value1 ) );

        final Object value2 = execute( "var result = {}; result;" );
        assertFalse( NashornHelper.INSTANCE.isArrayType( value2 ) );

        final Object value3 = execute( "var result = []; result;" );
        assertTrue( NashornHelper.INSTANCE.isArrayType( value3 ) );
    }

    @Test
    public void isObjectType()
        throws Exception
    {
        final Object value1 = execute( "var result = 11; result;" );
        assertFalse( NashornHelper.INSTANCE.isObjectType( value1 ) );

        final Object value2 = execute( "var result = []; result;" );
        assertFalse( NashornHelper.INSTANCE.isObjectType( value2 ) );

        final Object value3 = execute( "var result = {}; result;" );
        assertTrue( NashornHelper.INSTANCE.isObjectType( value3 ) );
    }

    @Test
    public void addToArray()
        throws Exception
    {
        final ScriptObjectMirror array = (ScriptObjectMirror) execute( "var result = []; result;" );
        assertEquals( 0, array.size() );

        NashornHelper.INSTANCE.addToArray( array, 10 );
        assertEquals( 1, array.size() );
    }

    @Test
    public void addToObject()
        throws Exception
    {
        final ScriptObjectMirror object = (ScriptObjectMirror) execute( "var result = {}; result;" );
        assertEquals( 0, object.keySet().size() );

        NashornHelper.INSTANCE.addToObject( object, "a", 10 );
        assertEquals( 1, object.keySet().size() );
    }

    private Object execute( final String script )
        throws Exception
    {
        return this.engine.eval( script );
    }
}
