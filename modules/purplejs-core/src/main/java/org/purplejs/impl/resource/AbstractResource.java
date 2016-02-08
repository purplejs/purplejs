package org.purplejs.impl.resource;

import org.purplejs.resource.Resource;
import org.purplejs.resource.ResourcePath;

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
