package org.purplejs;

import io.purplejs.resource.ResourcePath;
import io.purplejs.value.ScriptExports;

import io.purplejs.Engine;
import io.purplejs.EngineBuilder;

public abstract class AbstractScriptTest
{
    protected final Engine engine;

    public AbstractScriptTest()
    {
        this.engine = EngineBuilder.newBuilder().
            build();
    }

    protected final ScriptExports run( final ResourcePath path )
    {
        return this.engine.require( path );
    }
}
