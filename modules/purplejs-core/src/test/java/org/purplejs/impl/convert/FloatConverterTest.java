package org.purplejs.impl.convert;

import org.junit.Test;

public class FloatConverterTest
    extends NumberConverterTest<Float, FloatConverter>
{
    @Override
    protected FloatConverter newConverter()
    {
        return new FloatConverter();
    }

    @Test
    public void testFromString()
    {
        testFromString( 11f );
    }

    @Test
    public void testFromNumber()
    {
        testFromNumber( 11f );
    }
}
