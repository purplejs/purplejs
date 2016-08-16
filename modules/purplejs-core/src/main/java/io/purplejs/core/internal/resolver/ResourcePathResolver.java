package io.purplejs.core.internal.resolver;

import io.purplejs.core.resource.ResourcePath;

public interface ResourcePathResolver
{
    ResourcePath resolve( String path );
}
