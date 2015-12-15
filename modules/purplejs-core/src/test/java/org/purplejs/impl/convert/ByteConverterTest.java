package org.purplejs.impl.convert;

import org.junit.Test;

public class ByteConverterTest
    extends NumberConverterTest<Byte, ByteConverter>
{
    @Override
    protected ByteConverter newConverter()
    {
        return new ByteConverter();
    }

    @Test
    public void testFromString()
    {
        testFromString( (byte) 11 );
    }

    @Test
    public void testFromNumber()
    {
        testFromNumber( (byte) 11 );
    }
}
