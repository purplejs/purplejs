package io.purplejs.core.internal.require;

import java.util.List;

import com.google.common.collect.Lists;

import io.purplejs.core.resource.ResourcePath;
import io.purplejs.core.require.RequireResolver;
import io.purplejs.core.require.RequireResolverBuilder;

public final class RequireResolverBuilderImpl
    implements RequireResolverBuilder
{
    private final List<ResourcePath> rootPaths;

    private final List<ResourcePath> searchPaths;

    public RequireResolverBuilderImpl()
    {
        this.rootPaths = Lists.newArrayList();
        this.searchPaths = Lists.newArrayList();
    }

    @Override
    public RequireResolverBuilder rootPath( final String... path )
    {
        addPaths( this.rootPaths, path );
        return this;
    }

    @Override
    public RequireResolverBuilder searchPath( final String... path )
    {
        addPaths( this.searchPaths, path );
        return this;
    }

    private void addPaths( final List<ResourcePath> list, final String... paths )
    {
        addPaths( list, Lists.newArrayList( paths ) );
    }

    private void addPaths( final List<ResourcePath> list, final List<String> paths )
    {
        paths.forEach( e -> list.add( ResourcePath.from( e ) ) );
    }

    @Override
    public RequireResolver build()
    {
        if ( this.rootPaths.isEmpty() )
        {
            this.rootPaths.add( ResourcePath.ROOT );
        }

        if ( this.searchPaths.isEmpty() )
        {
            this.searchPaths.add( ResourcePath.from( "/lib" ) );
        }

        return new RequireResolverImpl( this.rootPaths, this.searchPaths );
    }
}
