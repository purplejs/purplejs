package org.purplejs.impl.resource;

import org.purplejs.resource.Resource;
import org.purplejs.resource.ResourceNotFoundException;
import org.purplejs.resource.ResourcePath;
import org.purplejs.resource.ResourceResolver;

final class ChainedResourceResolver
    implements ResourceResolver
{
    private final ResourceResolver resolver;

    private final ResourceResolver next;

    public ChainedResourceResolver( final ResourceResolver resolver, final ResourceResolver next )
    {
        this.resolver = resolver;
        this.next = next;
    }

    @Override
    public Resource resolve( final ResourcePath path )
    {
        try
        {
            return this.resolver.resolve( path );
        }
        catch ( final ResourceNotFoundException e )
        {
            return this.next.resolve( path );
        }
    }
}
