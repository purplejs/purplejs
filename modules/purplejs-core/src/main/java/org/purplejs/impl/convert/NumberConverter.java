package org.purplejs.impl.convert;

import org.purplejs.convert.Converter;

public abstract class NumberConverter<T extends Number>
    implements Converter<T>
{
    private final Class<T> type;

    public NumberConverter( final Class<T> type )
    {
        this.type = type;
    }

    @Override
    public final Class<T> getType()
    {
        return this.type;
    }

    @Override
    public final T convert( final Object value )
    {
        if ( value instanceof Number )
        {
            return fromNumber( (Number) value );
        }

        return parse( value.toString() );
    }

    protected abstract T fromNumber( final Number value );

    protected abstract T parse( final String value );
}
