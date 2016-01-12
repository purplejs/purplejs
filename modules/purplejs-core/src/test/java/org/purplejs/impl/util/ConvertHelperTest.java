package org.purplejs.impl.util;

import java.util.Date;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConvertHelperTest
{
    @Test(expected = IllegalArgumentException.class)
    public void convertError()
    {
        ConvertHelper.convert( "abc", Integer.class );
    }

    @Test(expected = IllegalArgumentException.class)
    public void noSuchConverterError()
    {
        ConvertHelper.convert( "abc", Date.class );
    }

    @Test
    public void convertNull()
    {
        final Boolean value = ConvertHelper.convert( null, Boolean.class );
        assertNull( value );
    }

    @Test
    public void convertBoolean()
    {
        final Boolean value1 = ConvertHelper.convert( "true", Boolean.class );
        assertNotNull( value1 );
        assertEquals( true, value1 );

        final Boolean value2 = ConvertHelper.convert( true, Boolean.class );
        assertNotNull( value2 );
        assertEquals( true, value2 );

        final Boolean value3 = ConvertHelper.convert( 1, Boolean.class );
        assertNotNull( value3 );
        assertEquals( true, value3 );
    }

    @Test
    public void convertByte()
    {
        final Byte value1 = ConvertHelper.convert( "11", Byte.class );
        assertNotNull( value1 );
        assertEquals( new Byte( (byte) 11 ), value1 );

        final Byte value2 = ConvertHelper.convert( (byte) 11, Byte.class );
        assertNotNull( value2 );
        assertEquals( new Byte( (byte) 11 ), value2 );
    }

    @Test
    public void convertDouble()
    {
        final Double value1 = ConvertHelper.convert( "11", Double.class );
        assertNotNull( value1 );
        assertEquals( 11, value1, 0 );

        final Double value2 = ConvertHelper.convert( 11d, Double.class );
        assertNotNull( value2 );
        assertEquals( 11, value2, 0 );
    }

    @Test
    public void convertFloat()
    {
        final Float value1 = ConvertHelper.convert( "11", Float.class );
        assertNotNull( value1 );
        assertEquals( 11, value1, 0 );

        final Float value2 = ConvertHelper.convert( 11f, Float.class );
        assertNotNull( value2 );
        assertEquals( 11, value2, 0 );
    }

    @Test
    public void convertInteger()
    {
        final Integer value1 = ConvertHelper.convert( "11", Integer.class );
        assertNotNull( value1 );
        assertEquals( new Integer( 11 ), value1 );

        final Integer value2 = ConvertHelper.convert( 11, Integer.class );
        assertNotNull( value2 );
        assertEquals( new Integer( 11 ), value2 );

        final Integer value3 = ConvertHelper.convert( 11L, Integer.class );
        assertNotNull( value3 );
        assertEquals( new Integer( 11 ), value3 );
    }

    @Test
    public void convertLong()
    {
        final Long value1 = ConvertHelper.convert( "11", Long.class );
        assertNotNull( value1 );
        assertEquals( new Long( 11 ), value1 );

        final Long value2 = ConvertHelper.convert( 11L, Long.class );
        assertNotNull( value2 );
        assertEquals( new Long( 11 ), value2 );
    }

    @Test
    public void convertShort()
    {
        final Short value1 = ConvertHelper.convert( "11", Short.class );
        assertNotNull( value1 );
        assertEquals( new Short( (short) 11 ), value1 );

        final Short value2 = ConvertHelper.convert( (short) 11, Short.class );
        assertNotNull( value2 );
        assertEquals( new Short( (short) 11 ), value2 );
    }

    @Test
    public void convertString()
    {
        final String value1 = ConvertHelper.convert( 11, String.class );
        assertEquals( "11", value1 );

        final String value2 = ConvertHelper.convert( "11", String.class );
        assertEquals( "11", value2 );
    }
}
