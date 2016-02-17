package org.purplejs.impl.cache;

import org.purplejs.resource.ResourcePath;

final class ScriptExportEntry
{
    protected final ResourcePath key;

    protected final Object value;

    protected final long timestamp;

    public ScriptExportEntry( final ResourcePath key, final Object value, final long timestamp )
    {
        this.key = key;
        this.value = value;
        this.timestamp = timestamp;
    }
}
