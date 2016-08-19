package io.purplejs.core.internal.resolver;

import java.util.List;

import com.google.common.collect.Lists;

import io.purplejs.core.resource.ResourceLoader;
import io.purplejs.core.resource.ResourcePath;

public final class RequirePathResolver
    implements ResourcePathResolver
{
    private final ResourceLoader loader;

    private final ResourcePath baseDir;

    public RequirePathResolver( final ResourceLoader loader, final ResourcePath baseDir )
    {
        this.loader = loader;
        this.baseDir = baseDir;
    }

    @Override
    public ResourcePath resolve( final String path )
    {
        if ( path.startsWith( "/" ) || path.startsWith( "./" ) || path.startsWith( "../" ) )
        {
            return resolveFileOrDir( this.baseDir, path );
        }

        return resolveSearchPath( path );
    }

    private boolean exists( final ResourcePath path )
    {
        return this.loader.exists( path );
    }

    private ResourcePath resolveFileOrDir( final ResourcePath path )
    {
        final ResourcePath resolved = resolveAsFile( path );
        if ( resolved != null )
        {
            return resolved;
        }

        return resolveAsDir( path );
    }


    private ResourcePath resolveFileOrDir( final ResourcePath dir, final String path )
    {
        return resolveFileOrDir( dir.resolve( path ) );
    }

    private ResourcePath resolveAsFile( final ResourcePath file )
    {
        if ( exists( file ) )
        {
            return file;
        }

        final ResourcePath path1 = ResourcePath.from( file + ".js" );
        if ( exists( path1 ) )
        {
            return path1;
        }

        final ResourcePath path2 = ResourcePath.from( file + ".json" );
        if ( exists( path2 ) )
        {
            return path2;
        }

        return null;
    }

    private ResourcePath resolveAsDir( final ResourcePath dir )
    {
        final ResourcePath path1 = ResourcePath.from( dir + "/index.js" );
        if ( exists( path1 ) )
        {
            return path1;
        }

        final ResourcePath path2 = ResourcePath.from( dir + "/index.json" );
        if ( exists( path2 ) )
        {
            return path2;
        }

        return null;
    }

    private ResourcePath resolveSearchPath( final String path )
    {
        for ( final ResourcePath searchPath : findSearchPaths() )
        {
            final ResourcePath resourcePath = resolveFileOrDir( searchPath, path );
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
