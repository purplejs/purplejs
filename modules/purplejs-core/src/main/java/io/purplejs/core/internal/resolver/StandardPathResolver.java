package io.purplejs.core.internal.resolver;

import io.purplejs.core.resource.ResourcePath;

public final class StandardPathResolver
    implements ResourcePathResolver
{
    private final ResourcePath baseDir;

    public StandardPathResolver( final ResourcePath baseDir )
    {
        this.baseDir = baseDir;
    }

    @Override
    public ResourcePath resolve( final String path )
    {
        return this.baseDir.resolve( path );
    }
}
