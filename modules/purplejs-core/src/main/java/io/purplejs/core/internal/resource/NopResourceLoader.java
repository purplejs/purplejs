package io.purplejs.core.internal.resource;

import io.purplejs.core.resource.Resource;
import io.purplejs.core.resource.ResourceLoader;
import io.purplejs.core.resource.ResourcePath;

final class NopResourceLoader
    implements ResourceLoader
{
    @Override
    public Resource loadOrNull( final ResourcePath path )
    {
        return null;
    }
}
