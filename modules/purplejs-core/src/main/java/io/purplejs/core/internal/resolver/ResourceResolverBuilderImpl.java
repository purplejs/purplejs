package io.purplejs.core.internal.resolver;

import java.util.List;

import com.google.common.collect.Lists;

import io.purplejs.core.resource.ResourcePath;
import io.purplejs.core.resource.ResourceResolver;
import io.purplejs.core.resource.ResourceResolverBuilder;

public final class ResourceResolverBuilderImpl
    implements ResourceResolverBuilder
{
    private final List<ResourcePath> rootPaths;

    private final List<ResourcePath> searchPaths;

    public ResourceResolverBuilderImpl()
    {
        this.rootPaths = Lists.newArrayList();
        this.searchPaths = Lists.newArrayList();
    }

    @Override
    public ResourceResolverBuilder rootPath( final String... path )
    {
        addPaths( this.rootPaths, path );
        return this;
    }

    @Override
    public ResourceResolverBuilder searchPath( final String... path )
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
    public ResourceResolver build()
    {
        if ( this.rootPaths.isEmpty() )
        {
            this.rootPaths.add( ResourcePath.ROOT );
        }

        if ( this.searchPaths.isEmpty() )
        {
            this.searchPaths.add( ResourcePath.from( "/lib" ) );
        }

        return new ResourceResolverImpl( this.rootPaths, this.searchPaths );
    }
}
