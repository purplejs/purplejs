package io.purplejs.core.require;

import io.purplejs.core.resource.ResourcePath;

public interface RequireResolver
{
    ResourcePath resolve( RequireResolverContext context, String path );
}
