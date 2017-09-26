package io.purplejs.core.internal.resolver;

import java.util.List;

import com.google.common.collect.Lists;

import io.purplejs.core.resource.ResourceLoader;
import io.purplejs.core.resource.ResourcePath;
import io.purplejs.core.resource.ResourceResolverContext;
import io.purplejs.core.resource.ResourceResolverMode;

public final class ResourceResolverContextImpl
    implements ResourceResolverContext
{
    private final ResourceResolverMode mode;

    private final ResourceLoader loader;

    private final List<ResourcePath> scanned;

    private final ResourcePath base;

    public ResourceResolverContextImpl( final ResourceResolverMode mode, final ResourceLoader loader, final ResourcePath base )
    {
        this.mode = mode;
        this.loader = loader;
        this.scanned = Lists.newArrayList();
        this.base = base;
    }

    @Override
    public ResourceResolverMode getMode()
    {
        return this.mode;
    }

    @Override
    public ResourcePath getBase()
    {
        return this.base;
    }

    @Override
    public List<ResourcePath> getScanned()
    {
        return this.scanned;
    }

    @Override
    public boolean exists( final ResourcePath path )
    {
        this.scanned.add( path );
        return this.loader.exists( path );
    }
}
