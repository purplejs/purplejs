package io.purplejs.script;

import org.junit.After;
import org.junit.Before;

import io.purplejs.core.Engine;
import io.purplejs.core.EngineBinder;
import io.purplejs.core.EngineBuilder;
import io.purplejs.core.EngineModule;
import io.purplejs.core.RunMode;
import io.purplejs.core.resource.ResourceLoaderBuilder;
import io.purplejs.core.resource.ResourcePath;
import io.purplejs.core.value.ScriptExports;

public abstract class AbstractScriptTest
    implements EngineModule
{
    private Engine engine;

    @Before
    public final void setUp()
    {
        final EngineBuilder builder = EngineBuilder.newBuilder();
        configure( builder );

        this.engine = builder.build();

        RunMode.TEST.set();
    }

    @After
    public final void tearDown()
    {
        this.engine.dispose();
    }

    private void configure( final EngineBuilder builder )
    {
        builder.module( this );

        final ResourceLoaderBuilder resourceLoaderBuilder = ResourceLoaderBuilder.newBuilder();
        configure( resourceLoaderBuilder );

        builder.resourceLoader( resourceLoaderBuilder.build() );
    }

    private void configure( final ResourceLoaderBuilder builder )
    {
        builder.from( getClass().getClassLoader(), "/app" );
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
}
