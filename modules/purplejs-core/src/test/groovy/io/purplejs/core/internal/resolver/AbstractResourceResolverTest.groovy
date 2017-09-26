package io.purplejs.core.internal.resolver

import io.purplejs.core.resource.*
import spock.lang.Specification

abstract class AbstractResourceResolverTest
    extends Specification
{
    protected ResourceLoader loader

    protected ResourceResolver resolver

    protected ResourceResolverContext context

    protected ResourceResolverMode mode

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
        this.context = new ResourceResolverContextImpl( this.mode, this.loader, toPath( dir ) )
        return this.resolver.resolve( this.context, path )
    }

    protected abstract void configure()
}
