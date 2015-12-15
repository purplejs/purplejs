package org.purplejs.impl.convert;

import org.junit.Test;

public class IntegerConverterTest
    extends NumberConverterTest<Integer, IntegerConverter>
{
    @Override
    protected IntegerConverter newConverter()
    {
        return new IntegerConverter();
    }

    @Test
    public void testFromString()
    {
        testFromString( 11 );
    }

    @Test
    public void testFromNumber()
    {
        testFromNumber( 11 );
    }
}
