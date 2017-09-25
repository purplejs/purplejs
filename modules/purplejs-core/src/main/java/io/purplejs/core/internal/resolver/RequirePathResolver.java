package io.purplejs.core.internal.resolver;

import java.util.List;

import com.google.common.collect.Lists;

import io.purplejs.core.resource.ResourceLoader;
import io.purplejs.core.resource.ResourcePath;

public final class RequirePathResolver
    implements ResourcePathResolver
{
    private final ResourceLoader loader;

    public RequirePathResolver( final ResourceLoader loader )
    {
        this.loader = loader;
    }

    @Override
    public ResourcePathResult resolve( final ResourcePath base, final String path )
    {
        final ResourcePathResult result = new ResourcePathResult();
        if ( path.startsWith( "/" ) || path.startsWith( "./" ) || path.startsWith( "../" ) )
        {
            result.setFound( resolveFileOrDir( result, base, path ) );
        }
        else
        {
            result.setFound( resolveSearchPath( result, path ) );
        }

        return result;
    }

    private boolean exists( final ResourcePathResult result, final ResourcePath path )
    {
        result.addScanned( path );
        return this.loader.exists( path );
    }

    private ResourcePath resolveFileOrDir( final ResourcePathResult result, final ResourcePath path )
    {
        final ResourcePath resolved = resolveAsFile( result, path );
        if ( resolved != null )
        {
            return resolved;
        }

        return resolveAsDir( result, path );
    }


    private ResourcePath resolveFileOrDir( final ResourcePathResult result, final ResourcePath dir, final String path )
    {
        return resolveFileOrDir( result, dir.resolve( path ) );
    }

    private ResourcePath resolveAsFile( final ResourcePathResult result, final ResourcePath file )
    {
        if ( exists( result, file ) )
        {
            return file;
        }

        final ResourcePath path1 = ResourcePath.from( file + ".js" );
        if ( exists( result, path1 ) )
        {
            return path1;
        }

        final ResourcePath path2 = ResourcePath.from( file + ".json" );
        if ( exists( result, path2 ) )
        {
            return path2;
        }

        return null;
    }

    private ResourcePath resolveAsDir( final ResourcePathResult result, final ResourcePath dir )
    {
        final ResourcePath path1 = ResourcePath.from( dir + "/index.js" );
        if ( exists( result, path1 ) )
        {
            return path1;
        }

        final ResourcePath path2 = ResourcePath.from( dir + "/index.json" );
        if ( exists( result, path2 ) )
        {
            return path2;
        }

        return null;
    }

    private ResourcePath resolveSearchPath( final ResourcePathResult result, final String path )
    {
        for ( final ResourcePath searchPath : findSearchPaths() )
        {
            final ResourcePath resourcePath = resolveFileOrDir( result, searchPath, path );
            if ( resourcePath != null )
            {
                return resourcePath;
            }
        }

        return null;
    }

    private static List<ResourcePath> findSearchPaths()
    {
        final List<ResourcePath> paths = Lists.newArrayList();
        paths.add( ResourcePath.from( "/lib" ) );
        return paths;
    }
}
