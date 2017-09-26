package io.purplejs.core.resource;

import io.purplejs.core.internal.resolver.ResourceResolverBuilderImpl;

public interface ResourceResolverBuilder
{
    ResourceResolverBuilder rootPath( String... path );

    ResourceResolverBuilder searchPath( String... path );

    ResourceResolver build();

    static ResourceResolverBuilder newBuilder()
    {
        return new ResourceResolverBuilderImpl();
    }
}
