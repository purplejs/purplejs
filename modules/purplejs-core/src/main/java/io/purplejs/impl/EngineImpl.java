package io.purplejs.impl;

import java.util.function.Function;

import org.purplejs.engine.ScriptSettings;
import org.purplejs.impl.executor.ScriptExecutorImpl;
import org.purplejs.impl.util.NashornHelper;
import org.purplejs.impl.value.ScriptExportsImpl;
import org.purplejs.resource.ResourcePath;
import org.purplejs.value.ScriptExports;
import org.purplejs.value.ScriptValue;

import io.purplejs.Engine;

final class EngineImpl
    implements Engine
{
    ScriptSettings settings;

    private final ScriptExecutorImpl executor;

    EngineImpl()
    {
        this.executor = new ScriptExecutorImpl();
    }

    @Override
    public ScriptSettings getSettings()
    {
        return this.settings;
    }

    @Override
    public ScriptExports require( final ResourcePath resource )
    {
        final Object exports = this.executor.executeRequire( resource );
        final ScriptValue value = this.executor.newScriptValue( exports );
        return new ScriptExportsImpl( resource, value );
    }

    @Override
    public <R> R execute( final ResourcePath resource, final Function<ScriptExports, R> command )
    {
        final ScriptExports exports = require( resource );

        // TODO: Set the command in context so that it's available as __.command (and clear afterwards)
        return command.apply( exports );
    }

    @Override
    public void dispose()
    {
        this.executor.dispose();
    }

    void init()
    {
        this.executor.setSettings( this.settings );
        this.executor.setEngine( NashornHelper.getScriptEngine( this.settings.getClassLoader(), "-strict" ) );
        this.executor.init();
    }
}
