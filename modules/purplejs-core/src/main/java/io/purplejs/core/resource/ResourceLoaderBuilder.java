package io.purplejs.core.resource;

import java.io.File;

public interface ResourceLoaderBuilder
{
    ResourceLoaderBuilder add( ResourceLoader loader );

    ResourceLoaderBuilder from( File dir );

    ResourceLoaderBuilder from( ClassLoader loader );

    ResourceLoaderBuilder from( ClassLoader loader, String basePath );

    ResourceLoader build();

    static ResourceLoaderBuilder newBuilder()
    {
        return new ResourceLoaderBuilderImpl();
    }
}
