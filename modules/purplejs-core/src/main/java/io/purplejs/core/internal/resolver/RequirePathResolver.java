package io.purplejs.core.internal.resolver;

import java.util.List;

import com.google.common.collect.Lists;

import io.purplejs.core.resource.ResourceLoader;
import io.purplejs.core.resource.ResourcePath;

public final class RequirePathResolver
    implements ResourcePathResolver
{
    private final ResourceLoader loader;

    private final List<ResourcePath> searchPaths;

    private final List<ResourcePath> rootPaths;

    public RequirePathResolver( final ResourceLoader loader )
    {
        this.loader = loader;
        this.rootPaths = Lists.newArrayList();
        this.searchPaths = Lists.newArrayList();

        setRootPaths( "/" );
        setSearchPaths( "/lib" );
    }

    @Override
    public ResourcePathResult resolve( final ResourcePath base, final String path )
    {
        final ResourcePathResult result = new ResourcePathResult();
        if ( path.startsWith( "/" ) )
        {
            result.setFound( resolveRoot( result, base, path ) );
        }
        else if ( path.startsWith( "./" ) || path.startsWith( "../" ) )
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

    private ResourcePath resolveRoot( final ResourcePathResult result, final ResourcePath base, final String path )
    {
        for ( final ResourcePath rootPath : this.rootPaths )
        {
            final ResourcePath resolved = resolveFileOrDir( result, base, rootPath.getPath() + "/" + path );
            if ( resolved != null )
            {
                return resolved;
            }
        }

        return null;
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
        for ( final ResourcePath searchPath : this.searchPaths )
        {
            final ResourcePath resourcePath = resolveFileOrDir( result, searchPath, path );
            if ( resourcePath != null )
            {
                return resourcePath;
            }
        }

        return null;
    }

    public void setRootPaths( final String... paths )
    {
        this.rootPaths.clear();
        for ( final String path : paths )
        {
            this.rootPaths.add( ResourcePath.from( path ) );
        }
    }

    public void setSearchPaths( final String... paths )
    {
        this.searchPaths.clear();
        for ( final String path : paths )
        {
            this.searchPaths.add( ResourcePath.from( path ) );
        }
    }
}
