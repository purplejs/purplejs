package io.purplejs.http;

import java.util.Map;
import java.util.Optional;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public final class Headers
{
    private final Map<String, String> map;

    public Headers()
    {
        this.map = Maps.newHashMap();
    }

    public Optional<String> get( final String key )
    {
        final String value = this.map.get( key );
        return Optional.ofNullable( value );
    }

    public void set( final String key, final String value )
    {
        this.map.put( key, value );
    }

    public void remove( final String key )
    {
        this.map.remove( key );
    }

    public Map<String, String> asMap()
    {
        return ImmutableMap.copyOf( this.map );
    }
}
