package io.purplejs.v3.impl;

import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableMap;

import io.purplejs.resource.ResourceLoader;
import io.purplejs.resource.ResourcePath;
import io.purplejs.v3.Engine;
import io.purplejs.v3.registry.Registry;
import io.purplejs.value.ScriptExports;

final class EngineImpl
    implements Engine
{
    CompositeModule module;

    boolean devMode;

    ResourceLoader resourceLoader;

    ClassLoader classLoader;

    ImmutableMap<String, String> config;

    ImmutableMap<String, Object> globalVariables;

    Registry registry;

    @Override
    public ScriptExports require( final ResourcePath resource )
    {
        return null;
    }

    void init()
    {
        this.module.init( this );
    }

    @Override
    public void dispose()
    {
        this.module.dispose( this );
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
    public Map<String, String> getConfig()
    {
        return this.config;
    }

    @Override
    public <T> T getInstance( final Class<T> type )
    {
        return this.registry.getInstance( type );
    }

    @Override
    public <T> Supplier<T> getSupplier( final Class<T> type )
    {
        return this.registry.getSupplier( type );
    }

    /*
    @Override
    public <T> T newInstance( final Class<T> type )
    {
        return this.registry.newInstance( type );
    }
    */
}
