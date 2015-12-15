package org.purplejs.impl.convert;

import org.junit.Test;

public class ShortConverterTest
    extends NumberConverterTest<Short, ShortConverter>
{
    @Override
    protected ShortConverter newConverter()
    {
        return new ShortConverter();
    }

    @Test
    public void testFromString()
    {
        testFromString( (short) 11 );
    }

    @Test
    public void testFromNumber()
    {
        testFromNumber( (short) 11 );
    }
}
