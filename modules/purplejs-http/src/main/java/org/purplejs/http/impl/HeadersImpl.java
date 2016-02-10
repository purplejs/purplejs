package org.purplejs.http.impl;

import java.util.Map;
import java.util.Optional;

import org.purplejs.http.Headers;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.net.MediaType;

public final class HeadersImpl
    implements Headers
{
    private final Map<String, String> map;

    public HeadersImpl()
    {
        this.map = Maps.newHashMap();
    }

    @Override
    public Optional<String> get( final String key )
    {
        final String value = this.map.get( key );
        return Optional.ofNullable( value );
    }

    @Override
    public Optional<MediaType> getContentType()
    {
        return get( "Content-Type" ).map( MediaType::parse );
    }

    public void set( final String key, final String value )
    {
        this.map.put( key, value );
    }

    public void remove( final String key )
    {
        this.map.remove( key );
    }

    @Override
    public Map<String, String> asMap()
    {
        return ImmutableMap.copyOf( this.map );
    }
}
