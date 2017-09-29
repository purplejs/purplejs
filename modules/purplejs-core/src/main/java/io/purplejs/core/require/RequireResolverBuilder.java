package io.purplejs.core.require;

import io.purplejs.core.internal.require.RequireResolverBuilderImpl;

public interface RequireResolverBuilder
{
    RequireResolverBuilder rootPath( String... path );

    RequireResolverBuilder searchPath( String... path );

    RequireResolver build();

    static RequireResolverBuilder newBuilder()
    {
        return new RequireResolverBuilderImpl();
    }
}
