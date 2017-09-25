package io.purplejs.core.internal.resolver;

import io.purplejs.core.resource.ResourcePath;

public final class StandardPathResolver
    implements ResourcePathResolver
{
    @Override
    public ResourcePathResult resolve( final ResourcePath base, final String path )
    {
        final ResourcePathResult result = new ResourcePathResult();
        result.setFound( base.resolve( path ) );
        return result;
    }
}
