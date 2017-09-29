package io.purplejs.core.internal.require

import io.purplejs.core.require.RequireResolver
import io.purplejs.core.require.RequireResolverContext
import io.purplejs.core.resource.ResourceLoader
import io.purplejs.core.resource.ResourcePath
import spock.lang.Specification

abstract class AbstractRequireResolverTest
    extends Specification
{
    protected ResourceLoader loader

    protected RequireResolver resolver

    protected RequireResolverContext context

    def setup()
    {
        this.loader = Mock( ResourceLoader.class )
        configure()
    }

    protected static ResourcePath toPath( final String path )
    {
        return ResourcePath.from( path )
    }

    protected void mockExists( final String path )
    {
        this.loader.exists( toPath( path ) ) >> true
    }

    protected ResourcePath resolve( final String dir, final String path )
    {
        this.context = new RequireResolverContextImpl( this.loader, toPath( dir ) )
        return this.resolver.resolve( this.context, path )
    }

    protected abstract void configure()
}
