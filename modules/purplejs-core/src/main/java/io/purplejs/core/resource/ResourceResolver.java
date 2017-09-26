package io.purplejs.core.resource;

public interface ResourceResolver
{
    ResourcePath resolve( ResourceResolverContext context, String path );
}
