package io.purplejs.impl.resource;

import io.purplejs.resource.Resource;
import io.purplejs.resource.ResourceLoader;
import io.purplejs.resource.ResourcePath;

final class NopResourceLoader
    implements ResourceLoader
{
    @Override
    public Resource loadOrNull( final ResourcePath path )
    {
        return null;
    }
}
