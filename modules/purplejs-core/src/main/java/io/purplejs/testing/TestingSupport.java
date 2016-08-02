package io.purplejs.testing;

import org.junit.After;
import org.junit.Before;

import io.purplejs.Engine;
import io.purplejs.EngineBinder;
import io.purplejs.EngineBuilder;
import io.purplejs.EngineModule;
import io.purplejs.resource.ResourceLoaderBuilder;
import io.purplejs.resource.ResourcePath;
import io.purplejs.value.ScriptExports;
import io.purplejs.value.ScriptValue;

public abstract class TestingSupport
    implements EngineModule
{
    protected Engine engine;

    protected boolean runDisposer = true;

    @Before
    public final void setUp()
    {
        final EngineBuilder builder = EngineBuilder.newBuilder();
        configure( builder );

        this.engine = builder.build();
    }

    @After
    public final void tearDown()
    {
        if ( this.runDisposer )
        {
            this.engine.dispose();
        }
    }

    protected void configure( final EngineBuilder builder )
    {
        builder.module( this );

        final ResourceLoaderBuilder resourceLoaderBuilder = ResourceLoaderBuilder.newBuilder();
        configure( resourceLoaderBuilder );

        builder.resourceLoader( resourceLoaderBuilder.build() );
    }

    protected void configure( final ResourceLoaderBuilder builder )
    {
        builder.from( getClass().getClassLoader() );
    }

    @Override
    public void configure( final EngineBinder binder )
    {
        binder.globalVariable( "__TEST__", this );
    }

    protected final ScriptExports run( final String path )
    {
        return run( ResourcePath.from( path ) );
    }

    protected final ScriptExports run( final ResourcePath path )
    {
        return this.engine.require( path );
    }

    protected final ScriptValue run( final String path, final String func )
    {
        return run( ResourcePath.from( path ), func );
    }

    protected final ScriptValue run( final ResourcePath path, final String func )
    {
        final ScriptExports exports = run( path );
        return exports.executeMethod( func );
    }
}
