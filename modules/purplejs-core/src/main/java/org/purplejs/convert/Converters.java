package org.purplejs.convert;

import java.util.Map;
import java.util.ServiceLoader;

import com.google.common.collect.Maps;

public final class Converters
{
    private final Map<Class, Converter> map;

    public Converters()
    {
        this.map = Maps.newHashMap();
        registerAll();
    }

    private void registerAll()
    {
        registerAll( ServiceLoader.load( Converter.class ) );
    }

    private void registerAll( final Iterable<Converter> list )
    {
        list.forEach( this::register );
    }

    public void register( final Converter<?> converter )
    {
        this.map.put( converter.getType(), converter );
    }

    @SuppressWarnings("unchecked")
    public <S, T> T convert( final S source, final Class<T> toType )
    {
        return (T) doConvert( source, toType );
    }

    public <S, T> T convertOrNull( final S source, final Class<T> toType )
    {
        try
        {
            return convert( source, toType );
        }
        catch ( final ConvertException e )
        {
            return null;
        }
    }

    public <S, T> T convertOrDefault( final S source, final Class<T> toType, final T defValue )
    {
        final T value = convertOrNull( source, toType );
        return value != null ? value : defValue;
    }

    private Object doConvert( final Object source, final Class type )
    {
        if ( source == null )
        {
            return null;
        }

        final Class<?> toType = resolveWrapperIfNeeded( type );
        if ( toType.isInstance( source ) )
        {
            return source;
        }

        final Converter converter = findConverter( source, toType );
        return doConvert( converter, source );
    }

    private Object doConvert( final Converter converter, final Object source )
    {
        try
        {
            return converter.convert( source );
        }
        catch ( final ConvertException e )
        {
            throw e;
        }
        catch ( final Exception e )
        {
            throw ConvertException.convertFailure( source.getClass(), converter.getType(), e );
        }
    }

    private Converter findConverter( final Object source, final Class type )
    {
        final Converter converter = this.map.get( type );
        if ( converter != null )
        {
            return converter;
        }

        throw ConvertException.noSuchConverter( source.getClass(), type );
    }

    private Class<?> resolveWrapperIfNeeded( final Class<?> type )
    {
        if ( type == boolean.class )
        {
            return Boolean.class;
        }

        if ( type == byte.class )
        {
            return Byte.class;
        }

        if ( type == char.class )
        {
            return Character.class;
        }

        if ( type == double.class )
        {
            return Double.class;
        }

        if ( type == float.class )
        {
            return Float.class;
        }

        if ( type == int.class )
        {
            return Integer.class;
        }

        if ( type == long.class )
        {
            return Long.class;
        }

        if ( type == short.class )
        {
            return Short.class;
        }

        return type;
    }
}
