package io.purplejs.core.internal.require;

import java.util.List;

import com.google.common.collect.Lists;

import io.purplejs.core.require.RequireResolver;
import io.purplejs.core.require.RequireResolverContext;
import io.purplejs.core.resource.ResourcePath;

final class RequireResolverImpl
    implements RequireResolver
{
    private final List<ResourcePath> rootPaths;

    private final List<ResourcePath> searchPaths;

    RequireResolverImpl( final List<ResourcePath> rootPaths, final List<ResourcePath> searchPaths )
    {
        this.rootPaths = Lists.newArrayList( rootPaths );
        this.searchPaths = Lists.newArrayList( searchPaths );
    }

    @Override
    public ResourcePath resolve( final RequireResolverContext context, final String path )
    {
        if ( path.startsWith( "/" ) )
        {
            return resolveRoot( context, context.getBase(), path );
        }
        else if ( path.startsWith( "./" ) || path.startsWith( "../" ) )
        {
            return resolveFileOrDir( context, context.getBase(), path );
        }
        else
        {
            return resolveSearchPath( context, path );
        }
    }

    private ResourcePath resolveRoot( final RequireResolverContext context, final ResourcePath base, final String path )
    {
        for ( final ResourcePath rootPath : this.rootPaths )
        {
            final ResourcePath resolved = resolveFileOrDir( context, base, rootPath.getPath() + "/" + path );
            if ( resolved != null )
            {
                return resolved;
            }
        }

        return null;
    }

    private ResourcePath resolveFileOrDir( final RequireResolverContext context, final ResourcePath path )
    {
        final ResourcePath resolved = resolveAsFile( context, path );
        if ( resolved != null )
        {
            return resolved;
        }

        return resolveAsDir( context, path );
    }

    private ResourcePath resolveFileOrDir( final RequireResolverContext context, final ResourcePath dir, final String path )
    {
        return resolveFileOrDir( context, dir.resolve( path ) );
    }

    private ResourcePath resolveAsFile( final RequireResolverContext context, final ResourcePath file )
    {
        if ( context.exists( file ) )
        {
            return file;
        }

        final ResourcePath path1 = ResourcePath.from( file + ".js" );
        if ( context.exists( path1 ) )
        {
            return path1;
        }

        final ResourcePath path2 = ResourcePath.from( file + ".json" );
        if ( context.exists( path2 ) )
        {
            return path2;
        }

        return null;
    }

    private ResourcePath resolveAsDir( final RequireResolverContext context, final ResourcePath dir )
    {
        final ResourcePath path1 = ResourcePath.from( dir + "/index.js" );
        if ( context.exists( path1 ) )
        {
            return path1;
        }

        final ResourcePath path2 = ResourcePath.from( dir + "/index.json" );
        if ( context.exists( path2 ) )
        {
            return path2;
        }

        return null;
    }

    private ResourcePath resolveSearchPath( final RequireResolverContext context, final String path )
    {
        for ( final ResourcePath searchPath : this.searchPaths )
        {
            final ResourcePath resourcePath = resolveFileOrDir( context, searchPath, path );
            if ( resourcePath != null )
            {
                return resourcePath;
            }
        }

        return null;
    }
}
