package org.purplejs.convert;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConvertersTest
{
    private Converters converters;

    @Before
    public void setup()
    {
        this.converters = new Converters();
    }

    @Test(expected = ConvertException.class)
    public void convertError()
    {
        this.converters.convert( "abc", Integer.class );
    }

    @Test
    public void convertOrNull()
    {
        final Integer value1 = this.converters.convertOrNull( "123", Integer.class );
        assertEquals( new Integer( 123 ), value1 );

        final Integer value2 = this.converters.convertOrNull( "abc", Integer.class );
        assertNull( value2 );
    }

    @Test
    public void convertOrDefault()
    {
        final int value1 = this.converters.convertOrDefault( "123", Integer.class, 1 );
        assertEquals( 123, value1 );

        final int value2 = this.converters.convertOrDefault( "abc", Integer.class, 1 );
        assertEquals( 1, value2 );
    }

    @Test
    public void convertNull()
    {
        final Boolean value = this.converters.convert( null, Boolean.class );
        assertNull( value );
    }

    @Test
    public void convertBoolean()
    {
        final boolean value1 = this.converters.convert( "true", boolean.class );
        assertEquals( true, value1 );

        final boolean value2 = this.converters.convert( "true", Boolean.class );
        assertEquals( true, value2 );
    }

    @Test
    public void convertByte()
    {
        final byte value1 = this.converters.convert( "11", byte.class );
        assertEquals( 11, value1 );

        final byte value2 = this.converters.convert( "11", Byte.class );
        assertEquals( 11, value2 );
    }

    @Test
    public void convertDouble()
    {
        final double value1 = this.converters.convert( "11", double.class );
        assertEquals( 11, value1, 0 );

        final double value2 = this.converters.convert( "11", Double.class );
        assertEquals( 11, value2, 0 );
    }

    @Test
    public void convertFloat()
    {
        final float value1 = this.converters.convert( "11", float.class );
        assertEquals( 11, value1, 0 );

        final float value2 = this.converters.convert( "11", Float.class );
        assertEquals( 11, value2, 0 );
    }

    @Test
    public void convertInteger()
    {
        final int value1 = this.converters.convert( "11", int.class );
        assertEquals( 11, value1 );

        final int value2 = this.converters.convert( "11", Integer.class );
        assertEquals( 11, value2 );
    }

    @Test
    public void convertLong()
    {
        final long value1 = this.converters.convert( "11", long.class );
        assertEquals( 11, value1 );

        final long value2 = this.converters.convert( "11", Long.class );
        assertEquals( 11, value2 );
    }

    @Test
    public void convertShort()
    {
        final short value1 = this.converters.convert( "11", short.class );
        assertEquals( 11, value1 );

        final short value2 = this.converters.convert( "11", Short.class );
        assertEquals( 11, value2 );
    }

    @Test
    public void convertString()
    {
        final String value = this.converters.convert( 11, String.class );
        assertEquals( "11", value );
    }
}
