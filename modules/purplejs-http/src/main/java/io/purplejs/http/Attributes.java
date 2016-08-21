package io.purplejs.http;

import java.util.Map;

import com.google.common.collect.ForwardingMap;
import com.google.common.collect.Maps;

public final class Attributes
    extends ForwardingMap<String, Object>
{
    private final Map<String, Object> map;

    public Attributes()
    {
        this.map = Maps.newHashMap();
    }

    @Override
    protected Map<String, Object> delegate()
    {
        return this.map;
    }

    public <T> T get( final Class<T> key )
    {
        final Object value = this.map.get( key.getName() );
        if ( value == null )
        {
            return null;
        }

        return key.cast( value );
    }

    public <T> void put( final Class<T> key, final T value )
    {
        put( key.getName(), value );
    }

    public <T> void remove( final Class<T> key )
    {
        remove( key.getName() );
    }
}
