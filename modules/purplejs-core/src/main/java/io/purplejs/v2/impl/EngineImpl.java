package io.purplejs.v2.impl;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import io.purplejs.resource.ResourceLoader;
import io.purplejs.resource.ResourcePath;
import io.purplejs.v2.Engine;
import io.purplejs.v2.Environment;
import io.purplejs.v2.RunMode;
import io.purplejs.value.ScriptExports;

final class EngineImpl
    implements Engine
{
    @Inject
    private Environment env;

    @Override
    public RunMode getRunMode()
    {
        return this.env.getRunMode();
    }

    @Override
    public ResourceLoader getResourceLoader()
    {
        return this.env.getResourceLoader();
    }

    @Override
    public ClassLoader getClassLoader()
    {
        return this.env.getClassLoader();
    }

    @Override
    public Map<String, Object> getGlobalVariables()
    {
        return this.env.getGlobalVariables();
    }

    @Override
    public Map<String, String> getConfig()
    {
        return this.env.getConfig();
    }

    @Override
    public <T> T resolve( final Class<T> type )
    {
        return this.env.resolve( type );
    }

    @Override
    public <T> Provider<T> provider( final Class<T> type )
    {
        return this.env.provider( type );
    }

    @Override
    public ScriptExports require( final ResourcePath resource )
    {
        return null;
    }

    @Override
    public void dispose()
    {

    }
}
