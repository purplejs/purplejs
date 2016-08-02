package io.purplejs.impl.resolver;

import io.purplejs.resource.ResourcePath;

public interface ResourcePathResolver
{
    ResourcePath resolve( String path );
}
