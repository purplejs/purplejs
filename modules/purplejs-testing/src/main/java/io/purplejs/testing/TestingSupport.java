package io.purplejs.testing;

import org.junit.After;
import org.junit.Before;

import io.purplejs.core.Engine;
import io.purplejs.core.EngineBinder;
import io.purplejs.core.EngineBuilder;
import io.purplejs.core.EngineModule;
import io.purplejs.core.resource.ResourceLoaderBuilder;
import io.purplejs.core.resource.ResourcePath;
import io.purplejs.core.value.ScriptExports;
import io.purplejs.core.value.ScriptValue;

// TODO: Move to testing project...
public abstract class TestingSupport
    implements EngineModule
{
    private Engine engine;

    private boolean runDisposer = true;

    private String classLoaderPrefix;

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
        builder.from( getClass().getClassLoader(), this.classLoaderPrefix );
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

    protected final ScriptValue run( final String path, final String func, final Object... args )
    {
        return run( ResourcePath.from( path ), func, args );
    }

    protected final ScriptValue run( final ResourcePath path, final String func, final Object... args )
    {
        final ScriptExports exports = run( path );
        return exports.executeMethod( func, args );
    }

    protected final Engine getEngine()
    {
        return this.engine;
    }

    protected final void setRunDisposer( final boolean flag )
    {
        this.runDisposer = flag;
    }

    protected final void setClassLoaderPrefix( final String prefix )
    {
        this.classLoaderPrefix = prefix;
    }
}
