package io.purplejs.http;

import java.util.Collection;

import com.google.common.collect.ForwardingMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public final class Parameters
    extends ForwardingMultimap<String, String>
{
    private final Multimap<String, String> map;

    public Parameters()
    {
        this.map = HashMultimap.create();
    }

    @Override
    protected Multimap<String, String> delegate()
    {
        return this.map;
    }

    public String getFirst( final String key )
    {
        final Collection<String> values = get( key );
        return values.isEmpty() ? null : values.iterator().next();
    }

    public void remove( final String key )
    {
        this.map.removeAll( key );
    }
}
