package org.purplejs.impl.convert;

public final class ShortConverter
    extends NumberConverter<Short>
{
    public ShortConverter()
    {
        super( Short.class );
    }

    @Override
    protected Short fromNumber( final Number value )
    {
        return value.shortValue();
    }

    @Override
    protected Short parse( final String value )
    {
        return Short.parseShort( value );
    }
}
