package org.purplejs.http.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.purplejs.http.Parameters;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;

public final class ParametersImpl
    implements Parameters
{
    private final Multimap<String, String> map;

    public ParametersImpl()
    {
        this.map = HashMultimap.create();
    }

    @Override
    public final Collection<String> get( final String key )
    {
        final Collection<String> values = this.map.get( key );
        return values != null ? values : Collections.emptyList();
    }

    @Override
    public final Optional<String> getFirst( final String key )
    {
        final Collection<String> values = get( key );
        return values.isEmpty() ? Optional.empty() : Optional.of( values.iterator().next() );
    }

    public final void put( final String key, final String value )
    {
        this.map.put( key, value );
    }

    public void remove( final String key )
    {
        this.map.removeAll( key );
    }

    @Override
    public Map<String, Collection<String>> asMap()
    {
        return ImmutableMap.copyOf( this.map.asMap() );
    }
}
