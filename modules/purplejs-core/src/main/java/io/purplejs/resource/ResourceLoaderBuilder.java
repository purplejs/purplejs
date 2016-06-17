package io.purplejs.resource;

import java.io.File;

import io.purplejs.impl.resource.ResourceLoaderBuilderImpl;

public interface ResourceLoaderBuilder
{
    ResourceLoaderBuilder add( ResourceLoader resolver );

    ResourceLoaderBuilder from( File dir );

    ResourceLoaderBuilder from( ClassLoader loader );

    ResourceLoaderBuilder from( ClassLoader loader, String basePath );

    ResourceLoader build();

    // TODO: Rename to newBuilder
    static ResourceLoaderBuilder create()
    {
        return new ResourceLoaderBuilderImpl();
    }
}
