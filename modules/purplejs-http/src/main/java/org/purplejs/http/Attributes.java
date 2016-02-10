package org.purplejs.http;

import java.util.Map;
import java.util.Optional;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public final class Attributes
{
    private final Map<String, Object> map;

    public Attributes()
    {
        this.map = Maps.newHashMap();
    }

    public Optional<Object> get( final String key )
    {
        final Object value = this.map.get( key );
        return Optional.ofNullable( value );
    }

    public void set( final String key, final Object value )
    {
        this.map.put( key, value );
    }

    public void remove( final String key )
    {
        this.map.remove( key );
    }

    public <T> Optional<T> get( final Class<T> key )
    {
        final Object value = this.map.get( key.getName() );
        if ( value == null )
        {
            return Optional.empty();
        }

        return Optional.of( key.cast( value ) );
    }

    public <T> void set( final Class<T> key, final T value )
    {
        set( key.getName(), value );
    }

    public <T> void remove( final Class<T> key )
    {
        remove( key.getName() );
    }

    public Map<String, Object> asMap()
    {
        return ImmutableMap.copyOf( this.map );
    }
}
