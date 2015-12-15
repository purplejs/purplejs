package org.purplejs.impl.convert;

import org.purplejs.convert.Converter;

public final class BooleanConverter
    implements Converter<Boolean>
{
    private final static String TRUE_FLAGS = "true|yes|on|1";

    @Override
    public Class<Boolean> getType()
    {
        return Boolean.class;
    }

    @Override
    public Boolean convert( final Object value )
    {
        if ( value instanceof Boolean )
        {
            return (Boolean) value;
        }

        if ( value instanceof Number )
        {
            return ( (Number) value ).intValue() != 0;
        }

        return TRUE_FLAGS.contains( value.toString() );
    }
}
