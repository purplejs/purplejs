package org.purplejs.impl.value;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.purplejs.value.ScriptExports;
import org.purplejs.value.ScriptValue;

import static org.junit.Assert.*;

public class ScriptExportsImplTest
{
    private ScriptValue value;

    private ScriptValue func;

    private ScriptExports exports;

    private ScriptValue callResult;

    @Before
    public void setup()
    {
        this.value = Mockito.mock( ScriptValue.class );
        this.func = Mockito.mock( ScriptValue.class );
        this.callResult = Mockito.mock( ScriptValue.class );

        Mockito.when( this.func.isFunction() ).thenReturn( true );
        Mockito.when( this.func.call( ) ).thenReturn( this.callResult );
        Mockito.when( this.value.getMember( "exists" ) ).thenReturn( this.func );

        this.exports = new ScriptExportsImpl( value );
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
    }
}
