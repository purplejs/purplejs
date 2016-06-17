package io.purplejs.impl;

import java.util.function.Function;

import io.purplejs.Engine;
import io.purplejs.ScriptSettings;
import io.purplejs.impl.executor.ScriptExecutorImpl;
import io.purplejs.impl.util.NashornHelper;
import io.purplejs.impl.value.ScriptExportsImpl;
import io.purplejs.resource.ResourcePath;
import io.purplejs.value.ScriptExports;
import io.purplejs.value.ScriptValue;

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
        return this.executor.executeCommand( exports, command );
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
