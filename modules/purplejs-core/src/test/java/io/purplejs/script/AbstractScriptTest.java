package io.purplejs.script;

import io.purplejs.Engine;
import io.purplejs.EngineBinder;
import io.purplejs.EngineBuilder;
import io.purplejs.EngineModule;
import io.purplejs.resource.ResourcePath;
import io.purplejs.value.ScriptExports;

abstract class AbstractScriptTest
    implements EngineModule
{
    final Engine engine;

    AbstractScriptTest()
    {
        final EngineBuilder builder = EngineBuilder.newBuilder();
        builder.module( this );

        customize( builder );

        this.engine = builder.build();
    }

    void customize( final EngineBuilder builder )
    {
        // Override to customize
    }

    @Override
    public void configure( final EngineBinder binder )
    {
        binder.globalVariable( "__test", this );
    }

    final ScriptExports run( final ResourcePath path )
    {
        return this.engine.require( path );
    }
}
