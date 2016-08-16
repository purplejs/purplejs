package io.purplejs.core.internal.util;

import java.util.function.Function;

public final class ConvertHelper
{
    public final static ConvertHelper INSTANCE = new ConvertHelper();

    private final static String TRUE_FLAGS = "true|yes|on|1";

    public <T> T convert( final Object value, final Class<T> type )
    {
        final Object result = doConvert( value, type );
        return result != null ? type.cast( result ) : null;
    }

    private Object doConvert( final Object value, final Class type )
    {
        if ( value == null )
        {
            return null;
        }

        if ( type.isInstance( value ) )
        {
            return value;
        }

        if ( type == Boolean.class )
        {
            return convertToBoolean( value );
        }
        else if ( type == Byte.class )
        {
            return convertToByte( value );
        }
        else if ( type == Double.class )
        {
            return convertToDouble( value );
        }
        else if ( type == Float.class )
        {
            return convertToFloat( value );
        }
        else if ( type == Integer.class )
        {
            return convertToInteger( value );
        }
        else if ( type == Long.class )
        {
            return convertToLong( value );
        }
        else if ( type == Short.class )
        {
            return convertToShort( value );
        }
        else if ( type == String.class )
        {
            return convertToString( value );
        }

        throw noSuchConverter( value.getClass(), type );
    }

    private Boolean convertToBoolean( final Object value )
    {
        if ( value instanceof Number )
        {
            return ( (Number) value ).intValue() != 0;
        }

        return TRUE_FLAGS.contains( value.toString() );
    }

    private <T> T convertToNumber( final Class<T> type, final Object value, final Function<Number, T> fromNumber,
                                   final Function<String, T> parser )
    {
        if ( value instanceof Number )
        {
            return fromNumber.apply( (Number) value );
        }

        try
        {
            return parser.apply( value.toString() );
        }
        catch ( final Exception e )
        {
            throw convertFailure( value.getClass(), type, e );
        }
    }

    private Byte convertToByte( final Object value )
    {
        return convertToNumber( Byte.class, value, Number::byteValue, Byte::parseByte );
    }

    private Double convertToDouble( final Object value )
    {
        return convertToNumber( Double.class, value, Number::doubleValue, Double::parseDouble );
    }

    private Float convertToFloat( final Object value )
    {
        return convertToNumber( Float.class, value, Number::floatValue, Float::parseFloat );
    }

    private Integer convertToInteger( final Object value )
    {
        return convertToNumber( Integer.class, value, Number::intValue, Integer::parseInt );
    }

    private Long convertToLong( final Object value )
    {
        return convertToNumber( Long.class, value, Number::longValue, Long::parseLong );
    }

    private Short convertToShort( final Object value )
    {
        return convertToNumber( Short.class, value, Number::shortValue, Short::parseShort );
    }

    private String convertToString( final Object value )
    {
        return value.toString();
    }

    private IllegalArgumentException noSuchConverter( final Class<?> source, final Class<?> target )
    {
        return new IllegalArgumentException( String.format( "No such converter for %s -> %s", source.getName(), target.getName() ) );
    }

    private IllegalArgumentException convertFailure( final Class<?> source, final Class<?> target, final Throwable cause )
    {
        return new IllegalArgumentException( String.format( "Failed to convert %s -> %s", source.getName(), target.getName() ), cause );
    }
}
