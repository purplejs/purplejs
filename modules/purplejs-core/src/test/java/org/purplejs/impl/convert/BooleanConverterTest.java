package org.purplejs.impl.convert;

import org.junit.Test;

import static org.junit.Assert.*;

public class BooleanConverterTest
    extends AbstractConverterTest<Boolean, BooleanConverter>
{
    @Override
    protected BooleanConverter newConverter()
    {
        return new BooleanConverter();
    }

    @Test
    public void testSameType()
    {
        assertEquals( true, this.converter.convert( true ) );
    }

    @Test
    public void testFromString()
    {
        assertEquals( true, this.converter.convert( "true" ) );
        assertEquals( true, this.converter.convert( "on" ) );
        assertEquals( true, this.converter.convert( "yes" ) );
        assertEquals( true, this.converter.convert( "1" ) );
        assertEquals( false, this.converter.convert( "yessir" ) );
        assertEquals( false, this.converter.convert( "false" ) );
    }

    @Test
    public void testFromNumber()
    {
        assertEquals( true, this.converter.convert( 11 ) );
        assertEquals( true, this.converter.convert( 2L ) );
        assertEquals( false, this.converter.convert( (byte) 0 ) );
    }
}
