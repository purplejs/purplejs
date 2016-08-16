package io.purplejs.core.internal.value;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import io.purplejs.core.resource.ResourcePath;
import io.purplejs.core.value.ScriptExports;
import io.purplejs.core.value.ScriptValue;

import static org.junit.Assert.*;

public class ScriptExportsImplTest
{
    private ResourcePath resource;

    private ScriptValue value;

    private ScriptExports exports;

    private ScriptValue callResult;

    @Before
    public void setUp()
    {
        this.resource = ResourcePath.from( "/a/b" );
        this.value = Mockito.mock( ScriptValue.class );
        final ScriptValue func = Mockito.mock( ScriptValue.class );
        this.callResult = Mockito.mock( ScriptValue.class );

        Mockito.when( func.isFunction() ).thenReturn( true );
        Mockito.when( func.call() ).thenReturn( this.callResult );
        Mockito.when( this.value.getMember( "exists" ) ).thenReturn( func );
        Mockito.when( this.value.getMember( "number" ) ).thenReturn( new ScriptValueFactoryImpl( null ).newValue( 1 ) );

        this.exports = new ScriptExportsImpl( this.resource, this.value );
    }

    @Test
    public void getResource()
    {
        assertEquals( this.resource, this.exports.getResource() );
    }

    @Test
    public void getValue()
    {
        assertSame( this.value, this.exports.getValue() );
    }

    @Test
    public void hasMethod()
    {
        assertFalse( this.exports.hasMethod( "notExists" ) );
        assertTrue( this.exports.hasMethod( "exists" ) );
    }

    @Test
    public void executeMethod()
    {
        final ScriptValue result1 = this.exports.executeMethod( "notExists" );
        assertNull( result1 );

        final ScriptValue result2 = this.exports.executeMethod( "exists" );
        assertSame( this.callResult, result2 );

        final ScriptValue result3 = this.exports.executeMethod( "number" );
        assertNull( result3 );
    }
}
