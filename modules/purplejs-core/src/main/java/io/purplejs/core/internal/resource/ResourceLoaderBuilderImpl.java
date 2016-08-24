package io.purplejs.core.internal.resource;

import java.io.File;

import io.purplejs.core.resource.ResourceLoader;
import io.purplejs.core.resource.ResourceLoaderBuilder;

public final class ResourceLoaderBuilderImpl
    implements ResourceLoaderBuilder
{
    private ResourceLoader loader;

    public ResourceLoaderBuilderImpl()
    {
        this.loader = new NopResourceLoader();
    }

    @Override
    public ResourceLoaderBuilderImpl add( final ResourceLoader loader )
    {
        this.loader = new ChainedResourceLoader( this.loader, loader );
        return this;
    }

    @Override
    public ResourceLoaderBuilderImpl from( final File dir )
    {
        return add( new FileResourceLoader( dir ) );
    }

    @Override
    public ResourceLoaderBuilderImpl from( final ClassLoader loader )
    {
        return from( loader, null );
    }

    @Override
    public ResourceLoaderBuilderImpl from( final ClassLoader loader, final String basePath )
    {
        return add( new ClassLoaderResourceLoader( loader, basePath ) );
    }

    @Override
    public ResourceLoader build()
    {
        return this.loader;
    }
}
