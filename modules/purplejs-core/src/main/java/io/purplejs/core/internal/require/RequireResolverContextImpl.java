package io.purplejs.core.internal.require;

import java.util.List;

import com.google.common.collect.Lists;

import io.purplejs.core.require.RequireResolverContext;
import io.purplejs.core.resource.ResourceLoader;
import io.purplejs.core.resource.ResourcePath;

public final class RequireResolverContextImpl
    implements RequireResolverContext
{
    private final ResourceLoader loader;

    private final List<ResourcePath> scanned;

    private final ResourcePath base;

    public RequireResolverContextImpl( final ResourceLoader loader, final ResourcePath base )
    {
        this.loader = loader;
        this.scanned = Lists.newArrayList();
        this.base = base;
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
