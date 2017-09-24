package io.purplejs.core.resource;

public interface ResourceResolver
{
    ResourceResult resolve( String path );

    ResourceResult resolveRequire( String path );
}
