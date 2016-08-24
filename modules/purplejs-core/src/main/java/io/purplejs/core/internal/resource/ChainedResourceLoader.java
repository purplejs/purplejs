package io.purplejs.core.internal.resource;

import io.purplejs.core.resource.Resource;
import io.purplejs.core.resource.ResourceLoader;
import io.purplejs.core.resource.ResourcePath;

final class ChainedResourceLoader
    implements ResourceLoader
{
    private final ResourceLoader loader;

    private final ResourceLoader next;

    ChainedResourceLoader( final ResourceLoader loader, final ResourceLoader next )
    {
        this.loader = loader;
        this.next = next;
    }

    @Override
    public Resource loadOrNull( final ResourcePath path )
    {
        final Resource resource = this.loader.loadOrNull( path );
        if ( resource != null )
        {
            return resource;
        }

        return this.next.loadOrNull( path );
    }
}
