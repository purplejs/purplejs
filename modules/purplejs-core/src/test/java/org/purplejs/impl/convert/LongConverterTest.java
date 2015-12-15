package org.purplejs.impl.convert;

import org.junit.Test;

public class LongConverterTest
    extends NumberConverterTest<Long, LongConverter>
{
    @Override
    protected LongConverter newConverter()
    {
        return new LongConverter();
    }

    @Test
    public void testFromString()
    {
        testFromString( 11L );
    }

    @Test
    public void testFromNumber()
    {
        testFromNumber( 11L );
    }
}
