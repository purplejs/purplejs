package io.purplejs.testing;

import org.junit.Assert;

import io.purplejs.Engine;
import io.purplejs.EngineBinder;
import io.purplejs.EngineBuilder;
import io.purplejs.EngineModule;
import io.purplejs.resource.ResourcePath;
import io.purplejs.value.ScriptExports;

public abstract class ScriptTestSupport
    implements EngineModule
{
    final Engine engine;

    protected ScriptTestSupport()
    {
        final EngineBuilder builder = EngineBuilder.newBuilder();
        builder.module( this );

        customize( builder );

        this.engine = builder.build();
    }

    protected void customize( final EngineBuilder builder )
    {
        // Override to customize
    }

    @Override
    public void configure( final EngineBinder binder )
    {
        binder.globalVariable( "assert", Assert.class );
        binder.globalVariable( "testInstance", this );
    }

    final ScriptExports run( final ResourcePath path )
    {
        return this.engine.require( path );
    }
}
