package io.purplejs.impl;

import java.util.Map;
import java.util.function.Function;

import com.google.common.collect.ImmutableMap;

import io.purplejs.Engine;
import io.purplejs.impl.executor.ScriptExecutorImpl;
import io.purplejs.impl.util.NashornHelper;
import io.purplejs.impl.value.ScriptExportsImpl;
import io.purplejs.resource.ResourceLoader;
import io.purplejs.resource.ResourcePath;
import io.purplejs.value.ScriptExports;
import io.purplejs.value.ScriptValue;

final class EngineImpl
    implements Engine
{
    boolean devMode;

    ResourceLoader resourceLoader;

    ClassLoader classLoader;

    ImmutableMap<String, String> config;

    ImmutableMap<String, Object> globalVariables;

    private final ScriptExecutorImpl executor;

    EngineImpl()
    {
        this.executor = new ScriptExecutorImpl();
    }

    @Override
    public boolean isDevMode()
    {
        return this.devMode;
    }

    @Override
    public ResourceLoader getResourceLoader()
    {
        return this.resourceLoader;
    }

    @Override
    public ClassLoader getClassLoader()
    {
        return this.classLoader;
    }

    @Override
    public Map<String, Object> getGlobalVariables()
    {
        return this.globalVariables;
    }

    @Override
    public Map<String, String> getConfig()
    {
        return this.config;
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
        this.executor.setSettings( this );
        this.executor.setEngine( NashornHelper.getScriptEngine( this.classLoader, "-strict" ) );
        this.executor.init();
    }
}
