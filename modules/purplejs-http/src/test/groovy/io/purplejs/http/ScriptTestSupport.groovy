package io.purplejs.http

import io.purplejs.core.Engine
import io.purplejs.core.EngineBinder
import io.purplejs.core.EngineBuilder
import io.purplejs.core.RunMode
import io.purplejs.core.mock.MockResource
import io.purplejs.core.mock.MockResourceLoader
import io.purplejs.core.resource.ResourceLoaderBuilder
import io.purplejs.core.resource.ResourcePath
import io.purplejs.core.value.ScriptExports
import spock.lang.Specification

abstract class ScriptTestSupport
    extends Specification
{
    private Engine engine;

    private MockResourceLoader resourceLoader;

    public final void setup()
    {
        final EngineBuilder builder = EngineBuilder.newBuilder();
        configureEngine( builder );

        this.engine = builder.build();

        RunMode.TEST.set();
    }

    public final void cleanup()
    {
        this.engine.dispose();
    }

    private void configureEngine( final EngineBuilder builder )
    {
        this.resourceLoader = new MockResourceLoader();

        final ResourceLoaderBuilder resourceLoaderBuilder = ResourceLoaderBuilder.newBuilder();
        resourceLoaderBuilder.from( getClass().getClassLoader() );
        resourceLoaderBuilder.add( this.resourceLoader );

        builder.resourceLoader( resourceLoaderBuilder.build() );
        builder.module { binder -> configureModule( binder ) };
    }

    protected void configureModule( final EngineBinder binder )
    {
        binder.globalVariable( "t", this );
    }

    protected final MockResource file( final String path, final String content )
    {
        return this.resourceLoader.addResource( path, content.trim() );
    }

    protected final ScriptExports run( final String path )
    {
        return this.engine.require( ResourcePath.from( path ) );
    }

    public void assertEquals( final Object expected, final Object actual )
    {
        assert expected == actual;
    }
}
