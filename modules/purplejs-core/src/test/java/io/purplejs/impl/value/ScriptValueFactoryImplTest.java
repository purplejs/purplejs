package io.purplejs.impl.value;

import javax.script.Bindings;
import javax.script.ScriptEngine;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.gson.JsonNull;

import io.purplejs.impl.util.NashornHelper;
import io.purplejs.value.ScriptValue;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.internal.runtime.Undefined;

import static org.junit.Assert.*;

public class ScriptValueFactoryImplTest
{
    private ScriptValueFactory factory;

    private ScriptMethodInvoker invoker;

    private ScriptEngine engine;

    @Before
    public void setup()
    {
        this.invoker = Mockito.mock( ScriptMethodInvoker.class );
        this.factory = new ScriptValueFactoryImpl( this.invoker );
        this.engine = NashornHelper.getScriptEngine( getClass().getClassLoader() );
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
        final ScriptValue value = this.factory.newValue( Undefined.getEmpty() );
        assertNull( value );
    }

    @Test
    public void newValue_function()
    {
        final JSObject obj = Mockito.mock( JSObject.class );
        Mockito.when( obj.isFunction() ).thenReturn( true );

        final ScriptValue value = this.factory.newValue( obj );

        assertNotNull( value );
        assertEquals( false, value.isArray() );
        assertEquals( true, value.isFunction() );
        assertEquals( false, value.isObject() );
        assertEquals( false, value.isValue() );

        assertNonValue( value );
        assertNonArray( value );
        assertNonObject( value );

        Mockito.when( this.invoker.invoke( Mockito.same( obj ), Mockito.any() ) ).thenReturn( "a+b" );

        final ScriptValue result = value.call( "a", "b" );
        assertNotNull( result );
        assertEquals( true, result.isValue() );
        assertEquals( "a+b", result.getValue() );

        assertEquals( JsonNull.INSTANCE, value.toJson() );
    }

    @Test
    public void newValue_array()
        throws Exception
    {
        final Object obj = execute( "result = ['1', '2'];" );
        final ScriptValue value = this.factory.newValue( obj );

        assertNotNull( value );
        assertEquals( true, value.isArray() );
        assertEquals( false, value.isFunction() );
        assertEquals( false, value.isObject() );
        assertEquals( false, value.isValue() );

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
        final Object obj = execute( "result = {'a':1, 'b':2};" );
        final ScriptValue value = this.factory.newValue( obj );

        assertNotNull( value );
        assertEquals( false, value.isArray() );
        assertEquals( false, value.isFunction() );
        assertEquals( true, value.isObject() );
        assertEquals( false, value.isValue() );

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
        final Bindings bindings = this.engine.createBindings();
        this.engine.eval( script, bindings );
        return bindings.get( "result" );
    }
}
