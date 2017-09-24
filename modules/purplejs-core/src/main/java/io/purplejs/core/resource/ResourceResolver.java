package io.purplejs.core.resource;

public interface ResourceResolver
{
    ResourceResult resolve( ResourcePath base, String path );

    ResourceResult resolveRequire( ResourcePath base, String path );
}
