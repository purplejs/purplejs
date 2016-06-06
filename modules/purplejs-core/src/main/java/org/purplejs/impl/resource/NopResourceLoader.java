package org.purplejs.impl.resource;

import org.purplejs.resource.Resource;
import org.purplejs.resource.ResourceLoader;
import org.purplejs.resource.ResourcePath;

final class NopResourceLoader
    implements ResourceLoader
{
    @Override
    public Resource loadOrNull( final ResourcePath path )
    {
        return null;
    }
}
