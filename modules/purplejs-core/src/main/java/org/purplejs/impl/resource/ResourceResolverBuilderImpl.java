package org.purplejs.impl.resource;

import java.io.File;

import org.purplejs.resource.ResourceResolver;
import org.purplejs.resource.ResourceResolverBuilder;

public final class ResourceResolverBuilderImpl
    implements ResourceResolverBuilder
{
    private ResourceResolver resolver;

    public ResourceResolverBuilderImpl()
    {
        this.resolver = new NopResourceResolver();
    }

    @Override
    public ResourceResolverBuilderImpl add( final ResourceResolver resolver )
    {
        this.resolver = new ChainedResourceResolver( this.resolver, resolver );
        return this;
    }

    @Override
    public ResourceResolverBuilderImpl from( final File dir )
    {
        return add( new FileResourceResolver( dir ) );
    }

    @Override
    public ResourceResolverBuilderImpl from( final ClassLoader loader )
    {
        return from( loader, null );
    }

    @Override
    public ResourceResolverBuilderImpl from( final ClassLoader loader, final String basePath )
    {
        return add( new ClassLoaderResourceResolver( loader, basePath ) );
    }

    @Override
    public ResourceResolver build()
    {
        return this.resolver;
    }
}
