package org.purplejs.impl.engine;

import java.util.Map;

import org.purplejs.engine.Engine;
import org.purplejs.engine.ScriptSettings;
import org.purplejs.impl.executor.ScriptExecutorImpl;
import org.purplejs.impl.util.NashornHelper;
import org.purplejs.impl.value.ScriptExportsImpl;
import org.purplejs.resource.ResourceLoader;
import org.purplejs.resource.ResourcePath;
import org.purplejs.value.ScriptExports;
import org.purplejs.value.ScriptValue;

import com.google.common.collect.ImmutableMap;

final class EngineImpl
    implements Engine, ScriptSettings
{
    private final ScriptExecutorImpl executor;

    private boolean devMode;

    private ClassLoader classLoader;

    private Map<String, Object> globalVariables;

    private Map<String, String> config;

    private ResourceLoader resourceLoader;

    public EngineImpl()
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

    public void setDevMode( final boolean devMode )
    {
        this.devMode = devMode;
    }

    public void setClassLoader( final ClassLoader classLoader )
    {
        this.classLoader = classLoader;
    }

    public void setResourceLoader( final ResourceLoader resourceLoader )
    {
        this.resourceLoader = resourceLoader;
    }

    public void setGlobalVariables( final Map<String, Object> globalVariables )
    {
        this.globalVariables = ImmutableMap.copyOf( globalVariables );
    }

    public void setConfig( final Map<String, String> config )
    {
        this.config = ImmutableMap.copyOf( config );
    }

    protected void init()
    {
        this.executor.setEngine( NashornHelper.getScriptEngine( this.classLoader, "-strict" ) );
        this.executor.setSettings( this );
        this.executor.init();
    }

    @Override
    public ScriptExports execute( final ResourcePath path )
    {
        final Object exports = this.executor.executeRequire( path );
        final ScriptValue value = this.executor.newScriptValue( exports );
        return new ScriptExportsImpl( path, value );
    }

    @Override
    public void dispose()
    {
        this.executor.dispose();
    }
}
