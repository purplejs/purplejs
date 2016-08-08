package io.purplejs.http;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public final class Parameters
{
    private final Multimap<String, String> map;

    public Parameters()
    {
        this.map = HashMultimap.create();
    }

    public Collection<String> get( final String key )
    {
        return this.map.get( key );
    }

    public Optional<String> getFirst( final String key )
    {
        final Collection<String> values = get( key );
        return values.isEmpty() ? Optional.empty() : Optional.of( values.iterator().next() );
    }

    public void put( final String key, final String value )
    {
        this.map.put( key, value );
    }

    public void remove( final String key )
    {
        this.map.removeAll( key );
    }

    public Map<String, Collection<String>> asMap()
    {
        return this.map.asMap();
    }
}
