package io.purplejs.http.itest

import io.purplejs.core.Engine
import io.purplejs.core.EngineBinder
import io.purplejs.core.EngineBuilder
import io.purplejs.core.mock.MockResource
import io.purplejs.core.mock.MockResourceLoader
import io.purplejs.core.resource.ResourceLoaderBuilder
import io.purplejs.core.resource.ResourcePath
import io.purplejs.http.Request
import io.purplejs.http.Response
import io.purplejs.http.handler.HttpHandlerFactory
import io.purplejs.http.mock.MockRequest
import spock.lang.Specification

abstract class AbstractIntegrationTest
    extends Specification
{
    private Engine engine;

    private MockResourceLoader resourceLoader;

    private HttpHandlerFactory handlerFactory;

    protected MockRequest request;

    public final void setup()
    {
        final EngineBuilder builder = EngineBuilder.newBuilder();
        configureEngine( builder );

        this.engine = builder.build();
        this.handlerFactory = this.engine.getInstance( HttpHandlerFactory.class );

        this.request = new MockRequest();
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

    protected final Response serve( final String path, final Request request )
    {
        return serve( ResourcePath.from( path ), request );
    }

    protected final Response serve( final ResourcePath path, final Request request )
    {
        return this.handlerFactory.newHandler( path ).serve( request );
    }

    public void assertEquals( final Object expected, final Object actual )
    {
        assert expected == actual;
    }

    protected final void script( final String content )
    {
        file( '/test.js', content );
    }

    protected final Response serve( final Request request )
    {
        return serve( '/test.js', request );
    }

    protected final Response serve()
    {
        return serve( '/test.js', this.request );
    }
}
