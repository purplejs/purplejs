package io.purplejs.impl.cache;

final class ScriptExportEntry
{
    final Object value;

    final long timestamp;

    ScriptExportEntry( final Object value, final long timestamp )
    {
        this.value = value;
        this.timestamp = timestamp;
    }
}
