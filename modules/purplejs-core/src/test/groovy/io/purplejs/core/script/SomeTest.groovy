package io.purplejs.core.script

import io.purplejs.core.*
import io.purplejs.core.mock.MockResourceLoader
import io.purplejs.core.resource.ResourceLoaderBuilder
import io.purplejs.core.resource.ResourcePath
import spock.lang.Specification

class SomeTest
    extends Specification
    implements EngineModule
{
    private Engine engine;

    private MockResourceLoader resourceLoader;

    def setup()
    {
        final EngineBuilder builder = EngineBuilder.newBuilder();
        configureEngine( builder );

        this.engine = builder.build();

        RunMode.TEST.set();
    }

    def cleanup()
    {
        this.engine.dispose();
    }

    private void configureEngine( final EngineBuilder builder )
    {
        this.resourceLoader = new MockResourceLoader();

        builder.module( this );

        final ResourceLoaderBuilder resourceLoaderBuilder = ResourceLoaderBuilder.newBuilder();
        resourceLoaderBuilder.from( getClass().getClassLoader() );
        resourceLoaderBuilder.add( this.resourceLoader );

        builder.resourceLoader( resourceLoaderBuilder.build() );
    }

    @Override
    public void configure( final EngineBinder binder )
    {
        binder.globalVariable( "__TEST__", this );
    }

    protected final void file( final String path, final String content )
    {
        this.resourceLoader.addResource( path, content );
    }

    protected final void run( final String path )
    {
        this.engine.require( ResourcePath.from( path ) );
    }

    def "test something"()
    {
        setup:
        file( '/a/b.js', '''
            log.debug('debug message');
            log.info('%s message', 'info');
            log.warning('%s %s', 'warning', 'message');
            log.error('error %s', 'message');
        ''' );

        when:
        run( '/a/b.js' );

        then: true;
    }
}
