package org.purplejs.impl.executor;

import org.purplejs.resource.ResourceLoader;
import org.purplejs.resource.ResourcePath;

final class ResourceResolver
{
    private final static String SCRIPT_SUFFIX = ".js";

    private final static String DEFAULT_RESOURCE = "/index.js";

    private final ResourceLoader loader;

    private final ResourcePath basePath;

    public ResourceResolver( final ResourceLoader loader, final ResourcePath basePath )
    {
        this.loader = loader;
        this.basePath = basePath;
    }

    public ResourcePath resolve( final String path )
    {
        if ( path.startsWith( "/" ) )
        {
            return resolveAbsolute( path );
        }

        return resolveRelative( path );
    }

    private ResourcePath resolveAbsolute( final String path )
    {
        return this.basePath.resolve( path );
    }

    private ResourcePath resolveRelative( final String path )
    {
        return this.basePath.resolve( "../" + path );
    }

    public ResourcePath resolveJs( final String path )
    {
        if ( !path.endsWith( SCRIPT_SUFFIX ) )
        {
            final ResourcePath resolved = resolveJs( path + SCRIPT_SUFFIX );
            if ( exists( resolved ) )
            {
                return resolved;
            }
            else
            {
                return resolveJs( path + DEFAULT_RESOURCE );
            }
        }

        if ( path.startsWith( "/" ) )
        {
            return resolveAbsolute( path );
        }

        if ( path.startsWith( "../" ) || path.startsWith( "./" ) )
        {
            return resolveRelative( path );
        }

        final ResourcePath key = resolveRelative( path );
        if ( exists( key ) )
        {
            return key;
        }

        return resolveLibScan( this.basePath.resolve( ".." ), path );
    }

    private ResourcePath resolveLibScan( final ResourcePath parent, final String path )
    {
        if ( parent.isRoot() )
        {
            return parent.resolve( "/lib/" + path );
        }

        final ResourcePath key = parent.resolve( "lib/" + path );
        if ( exists( key ) )
        {
            return key;
        }

        return resolveLibScan( parent.resolve( ".." ), path );
    }

    private boolean exists( final ResourcePath key )
    {
        return this.loader.exists( key );
    }
}
