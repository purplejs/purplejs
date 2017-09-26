package io.purplejs.core.resource;

import java.util.List;

public interface ResourceResolverContext
{
    ResourceResolverMode getMode();

    ResourcePath getBase();

    List<ResourcePath> getScanned();

    boolean exists( ResourcePath path );
}
