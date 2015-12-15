package org.purplejs.impl.convert;

import org.junit.Test;

public class DoubleConverterTest
    extends NumberConverterTest<Double, DoubleConverter>
{
    @Override
    protected DoubleConverter newConverter()
    {
        return new DoubleConverter();
    }

    @Test
    public void testFromString()
    {
        testFromString( 11d );
    }

    @Test
    public void testFromNumber()
    {
        testFromNumber( 11d );
    }
}
