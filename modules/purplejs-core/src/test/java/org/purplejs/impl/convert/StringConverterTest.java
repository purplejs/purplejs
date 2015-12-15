package org.purplejs.impl.convert;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringConverterTest
    extends AbstractConverterTest<String, StringConverter>
{
    @Override
    protected StringConverter newConverter()
    {
        return new StringConverter();
    }

    @Test
    public void testSameType()
    {
        assertEquals( "test", this.converter.convert( "test" ) );
    }

    @Test
    public void testToString()
    {
        assertEquals( "true", this.converter.convert( true ) );
        assertEquals( "11", this.converter.convert( 11 ) );
    }
}
