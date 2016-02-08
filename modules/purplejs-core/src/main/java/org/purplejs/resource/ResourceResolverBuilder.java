package org.purplejs.resource;

import java.io.File;

import org.purplejs.impl.resource.ResourceResolverBuilderImpl;

public interface ResourceResolverBuilder
{
    ResourceResolverBuilder add( ResourceResolver resolver );

    ResourceResolverBuilder from( File dir );

    ResourceResolverBuilder from( ClassLoader loader );

    ResourceResolverBuilder from( ClassLoader loader, String basePath );

    ResourceResolver build();

    static ResourceResolverBuilder create()
    {
        return new ResourceResolverBuilderImpl();
    }
}
