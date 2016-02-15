package org.purplejs.impl.runtime;

import java.util.Map;

import org.purplejs.resource.Resource;
import org.purplejs.resource.ResourcePath;

import com.google.common.collect.Maps;

final class ScriptExportsCache
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
        this.cache.put( key, new ScriptExportEntry( key, value, resource.getLastModified() ) );
    }

    public boolean isModified( final Resource resource )
    {
        final ResourcePath key = resource.getPath();
        final ScriptExportEntry entry = this.cache.get( key );
        return entry == null || resource.getLastModified() > entry.timestamp;
    }
}
