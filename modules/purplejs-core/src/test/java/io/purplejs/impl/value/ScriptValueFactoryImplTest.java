package io.purplejs.impl.value;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.script.ScriptEngine;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.gson.JsonNull;

import io.purplejs.impl.nashorn.NashornRuntime;
import io.purplejs.impl.nashorn.NashornRuntimeFactory;
import io.purplejs.value.ScriptValue;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.runtime.ScriptRuntime;

import static org.junit.Assert.*;

public class ScriptValueFactoryImplTest
{
    private ScriptValueFactory factory;

    private ScriptEngine engine;

    @Before
    public void setUp()
    {
        final NashornRuntime runtime = new NashornRuntimeFactory().newRuntime( getClass().getClassLoader() );
        this.engine = runtime.getEngine();

        this.factory = new ScriptValueFactoryImpl( runtime );
    }

    @Test
    public void newValue_null()
    {
        final ScriptValue value = this.factory.newValue( null );
        assertNull( value );
    }

    @Test
    public void newValue_scalar()
    {
        final ScriptValue value = this.factory.newValue( "2" );

        assertNotNull( value );
        assertEquals( false, value.isArray() );
        assertEquals( false, value.isFunction() );
        assertEquals( false, value.isObject() );
        assertEquals( true, value.isValue() );

        assertEquals( "2", value.getValue() );
        assertEquals( "2", value.getRaw() );
        assertEquals( new Integer( 2 ), value.getValue( Integer.class ) );

        assertNonArray( value );
        assertNonObject( value );
        assertNonFunction( value );

        assertEquals( "true", this.factory.newValue( true ).toJson().toString() );
        assertEquals( "12", this.factory.newValue( 12 ).toJson().toString() );
        assertEquals( "\"test\"", this.factory.newValue( "test" ).toJson().toString() );
        assertEquals( "\"UTF-8\"", this.factory.newValue( Charsets.UTF_8 ).toJson().toString() );
    }

    @Test
    public void newValue_undefined()
    {
        final ScriptValue value = this.factory.newValue( ScriptRuntime.UNDEFINED );
        assertNull( value );
    }

    @Test
    public void newValue_function()
        throws Exception
    {
        final Object obj = execute( "var result = function(a, b) { return a + b; }; result;" );
        final ScriptValue value = this.factory.newValue( obj );

        assertNotNull( value );
        assertEquals( false, value.isArray() );
        assertEquals( true, value.isFunction() );
        assertEquals( false, value.isObject() );
        assertEquals( false, value.isValue() );
        assertNotNull( value.getRaw() );
        assertTrue( value.getRaw() instanceof ScriptObjectMirror );

        assertNonValue( value );
        assertNonArray( value );
        assertNonObject( value );

        final ScriptValue result = value.call( 10, 11 );
        assertNotNull( result );
        assertEquals( true, result.isValue() );
        assertEquals( 21.0, result.getValue() );

        assertEquals( JsonNull.INSTANCE, value.toJson() );
    }

    @Test
    public void newValue_array()
        throws Exception
    {
        final Object obj = execute( "var result = ['1', '2', undefined]; result;" );
        final ScriptValue value = this.factory.newValue( obj );

        assertNotNull( value );
        assertEquals( true, value.isArray() );
        assertEquals( false, value.isFunction() );
        assertEquals( false, value.isObject() );
        assertEquals( false, value.isValue() );
        assertNotNull( value.getRaw() );
        assertTrue( value.getRaw() instanceof ScriptObjectMirror );

        assertNonValue( value );
        assertNonObject( value );
        assertNonFunction( value );

        assertNotNull( value.getArray() );
        assertEquals( 2, value.getArray().size() );
        assertEquals( "1", value.getArray().get( 0 ).getValue() );
        assertEquals( "2", value.getArray().get( 1 ).getValue() );

        assertEquals( "[\"1\",\"2\"]", value.toJson().toString() );
    }

    @Test
    public void newValue_object()
        throws Exception
    {
        final Object obj = execute( "var result = {'a':1, 'b':2}; result;" );
        final ScriptValue value = this.factory.newValue( obj );

        assertNotNull( value );
        assertEquals( false, value.isArray() );
        assertEquals( false, value.isFunction() );
        assertEquals( true, value.isObject() );
        assertEquals( false, value.isValue() );
        assertNotNull( value.getRaw() );
        assertTrue( value.getRaw() instanceof ScriptObjectMirror );

        assertNonValue( value );
        assertNonArray( value );
        assertNonFunction( value );

        assertNotNull( value.getKeys() );
        assertEquals( 2, value.getKeys().size() );
        assertEquals( "a,b", Joiner.on( "," ).join( value.getKeys() ) );

        assertNotNull( value.getMember( "a" ) );
        assertEquals( 1, value.getMember( "a" ).getValue() );
        assertTrue( value.hasMember( "a" ) );

        assertEquals( "{\"a\":1,\"b\":2}", value.toJson().toString() );
    }

    @Test
    public void newValue_date()
        throws Exception
    {
        final Object obj = execute( "var result = new Date(Date.parse('1995-11-12T22:24:25Z')); result;" );
        final ScriptValue value = this.factory.newValue( obj );

        assertNotNull( value );
        assertEquals( false, value.isArray() );
        assertEquals( false, value.isFunction() );
        assertEquals( false, value.isObject() );
        assertEquals( true, value.isValue() );

        final SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ssZ" );
        format.setTimeZone( TimeZone.getTimeZone( "UTC" ) );

        final Date date = (Date) value.getValue();
        assertEquals( "1995-11-12T22:24:25+0000", format.format( date ) );
    }

    private void assertNonValue( final ScriptValue value )
    {
        assertNull( value.getValue() );
        assertNull( value.getValue( Integer.class ) );
    }

    private void assertNonArray( final ScriptValue value )
    {
        assertNotNull( value.getArray() );
        assertEquals( 0, value.getArray().size() );
    }

    private void assertNonObject( final ScriptValue value )
    {
        assertNotNull( value.getKeys() );
        assertEquals( 0, value.getKeys().size() );

        assertNull( value.getMember( "test" ) );
        assertFalse( value.hasMember( "test" ) );
    }

    private void assertNonFunction( final ScriptValue value )
    {
        assertNull( value.call( "a", "b" ) );
    }

    private Object execute( final String script )
        throws Exception
    {
        return this.engine.eval( script );
    }
}
