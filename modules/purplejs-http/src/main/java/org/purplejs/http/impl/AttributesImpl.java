package org.purplejs.http.impl;

import java.util.Map;
import java.util.Optional;

import org.purplejs.http.Attributes;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public final class AttributesImpl
    implements Attributes
{
    private final Map<String, Object> map;

    public AttributesImpl()
    {
        this.map = Maps.newHashMap();
    }

    @Override
    public Optional<Object> get( final String key )
    {
        final Object value = this.map.get( key );
        return Optional.ofNullable( value );
    }

    @Override
    public void set( final String key, final Object value )
    {
        this.map.put( key, value );
    }

    @Override
    public void remove( final String key )
    {
        this.map.remove( key );
    }

    @Override
    public <T> Optional<T> get( final Class<T> key )
    {
        final Object value = this.map.get( key.getName() );
        if ( value == null )
        {
            return Optional.empty();
        }

        return Optional.of( key.cast( value ) );
    }

    @Override
    public <T> void set( final Class<T> key, final T value )
    {
        set( key.getName(), value );
    }

    @Override
    public <T> void remove( final Class<T> key )
    {
        remove( key.getName() );
    }

    @Override
    public Map<String, Object> asMap()
    {
        return ImmutableMap.copyOf( this.map );
    }
}
