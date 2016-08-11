package io.purplejs.impl.cache;

import io.purplejs.resource.Resource;

final class ScriptExportEntry
{
    private final Resource resource;

    final Object value;

    private final long timestamp;

    ScriptExportEntry( final Resource resource, final Object value )
    {
        this.resource = resource;
        this.value = value;
        this.timestamp = this.resource.getLastModified();
    }

    boolean isExpired()
    {
        return this.resource.getLastModified() > this.timestamp;
    }
}
