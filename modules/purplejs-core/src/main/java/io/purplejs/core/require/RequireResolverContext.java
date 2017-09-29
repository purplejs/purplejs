package io.purplejs.core.require;

import java.util.List;

import io.purplejs.core.resource.ResourcePath;

public interface RequireResolverContext
{
    ResourcePath getBase();

    List<ResourcePath> getScanned();

    boolean exists( ResourcePath path );
}
