package org.purplejs.impl.convert;

import org.junit.Test;
import org.purplejs.convert.Converter;

import static org.junit.Assert.*;

public abstract class NumberConverterTest<V extends Number, C extends Converter<V>>
    extends AbstractConverterTest<V, C>
{
    protected final void testFromString( final V num )
    {
        assertEquals( num, this.converter.convert( num.toString() ) );
    }

    @Test(expected = NumberFormatException.class)
    public final void testParseError()
    {
        this.converter.convert( "abc" );
    }

    protected final void testFromNumber( final V num )
    {
        assertEquals( num, this.converter.convert( (short) 11 ) );
        assertEquals( num, this.converter.convert( 11 ) );
        assertEquals( num, this.converter.convert( 11L ) );
        assertEquals( num, this.converter.convert( 11.0f ) );
        assertEquals( num, this.converter.convert( 11.0d ) );
    }
}
