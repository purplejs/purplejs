package org.purplejs.impl.resource;

import org.purplejs.resource.Resource;
import org.purplejs.resource.ResourceNotFoundException;
import org.purplejs.resource.ResourcePath;
import org.purplejs.resource.ResourceResolver;

final class NopResourceResolver
    implements ResourceResolver
{
    @Override
    public Resource resolve( final ResourcePath path )
    {
        throw new ResourceNotFoundException( path );
    }
}
