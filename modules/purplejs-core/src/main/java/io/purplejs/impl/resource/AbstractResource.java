package io.purplejs.impl.resource;

import io.purplejs.resource.Resource;
import io.purplejs.resource.ResourcePath;

abstract class AbstractResource
    implements Resource
{
    private final ResourcePath path;

    public AbstractResource( final ResourcePath path )
    {
        this.path = path;
    }

    @Override
    public final ResourcePath getPath()
    {
        return this.path;
    }
}
