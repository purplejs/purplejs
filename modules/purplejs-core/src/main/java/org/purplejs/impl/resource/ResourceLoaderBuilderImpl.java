package org.purplejs.impl.resource;

import java.io.File;

import org.purplejs.resource.ResourceLoader;
import org.purplejs.resource.ResourceLoaderBuilder;

public final class ResourceLoaderBuilderImpl
    implements ResourceLoaderBuilder
{
    private ResourceLoader loader;

    public ResourceLoaderBuilderImpl()
    {
        this.loader = new NopResourceLoader();
    }

    @Override
    public ResourceLoaderBuilderImpl add( final ResourceLoader resolver )
    {
        this.loader = new ChainedResourceLoader( this.loader, resolver );
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
