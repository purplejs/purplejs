package io.purplejs.v2.impl;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Injector;

import io.purplejs.resource.ResourceLoader;
import io.purplejs.v2.Environment;
import io.purplejs.v2.RunMode;

final class EnvironmentImpl
    implements Environment
{
    @Inject
    RunMode mode;

    @Inject
    ResourceLoader resourceLoader;

    ClassLoader classLoader;

    ImmutableMap<String, Object> globalVariables;

    ImmutableMap<String, String> config;

    @Inject
    Injector injector;

    @Override
    public RunMode getRunMode()
    {
        return this.mode;
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
    public <T> T resolve( final Class<T> type )
    {
        return this.injector.getInstance( type );
    }

    @Override
    public <T> Provider<T> provider( final Class<T> type )
    {
        return this.injector.getProvider( type );
    }
}
