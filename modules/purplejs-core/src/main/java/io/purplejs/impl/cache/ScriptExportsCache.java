package io.purplejs.impl.cache;

import java.util.Map;

import com.google.common.collect.Maps;

import io.purplejs.resource.Resource;
import io.purplejs.resource.ResourcePath;

public final class ScriptExportsCache
{
    private final Map<ResourcePath, ScriptExportEntry> cache;

    public ScriptExportsCache()
    {
        this.cache = Maps.newConcurrentMap();
    }

    public Object get( final ResourcePath key )
    {
        final ScriptExportEntry entry = this.cache.get( key );
        return entry != null ? entry.value : null;
    }

    public void put( final Resource resource, final Object value )
    {
        final ResourcePath key = resource.getPath();
        this.cache.put( key, new ScriptExportEntry( resource, value ) );
    }

    public void clear()
    {
        this.cache.clear();
    }

    public boolean isExpired()
    {
        for ( final ScriptExportEntry entry : this.cache.values() )
        {
            if ( entry.isExpired() )
            {
                return true;
            }
        }

        return false;
    }
}
