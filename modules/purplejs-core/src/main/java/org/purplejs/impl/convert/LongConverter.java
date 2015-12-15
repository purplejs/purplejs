package org.purplejs.impl.convert;

public final class LongConverter
    extends NumberConverter<Long>
{
    public LongConverter()
    {
        super( Long.class );
    }

    @Override
    protected Long fromNumber( final Number value )
    {
        return value.longValue();
    }

    @Override
    protected Long parse( final String value )
    {
        return Long.parseLong( value );
    }
}
