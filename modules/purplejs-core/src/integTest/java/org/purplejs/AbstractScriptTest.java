package org.purplejs;

import org.purplejs.engine.Engine;
import org.purplejs.engine.EngineBuilder;
import org.purplejs.resource.ResourcePath;
import org.purplejs.value.ScriptExports;

public abstract class AbstractScriptTest
{
    private final Engine engine;

    public AbstractScriptTest()
    {
        this.engine = EngineBuilder.create().
            build();
    }

    protected final ScriptExports run( final ResourcePath path )
    {
        return this.engine.execute( path );
    }
}
