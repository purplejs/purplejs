package io.purplejs.impl;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableMap;

import io.purplejs.Engine;
import io.purplejs.impl.executor.ScriptExecutorImpl;
import io.purplejs.impl.nashorn.NashornRuntime;
import io.purplejs.impl.nashorn.NashornRuntimeFactory;
import io.purplejs.registry.Registry;
import io.purplejs.resource.ResourceLoader;
import io.purplejs.resource.ResourcePath;
import io.purplejs.value.ScriptExports;

final class EngineImpl
    implements Engine
{
    ResourceLoader resourceLoader;

    ClassLoader classLoader;

    ImmutableMap<String, String> config;

    ImmutableMap<String, Object> globalVariables;

    Registry registry;

    CompositeModule module;

    private final ScriptExecutorImpl executor;

    EngineImpl()
    {
        this.executor = new ScriptExecutorImpl();
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
    public <T> Optional<T> getOptional( final Class<T> type )
    {
        return this.registry.getOptional( type );
    }

    @Override
    public <T> Supplier<T> getProvider( final Class<T> type )
    {
        return this.registry.getProvider( type );
    }

    @Override
    public ScriptExports require( final ResourcePath path )
    {
        return this.executor.executeMain( path );
    }

    @Override
    public void dispose()
    {
        this.module.dispose( this );
        this.executor.dispose();
    }

    void init()
    {
        final NashornRuntimeFactory nashornRuntimeFactory = new NashornRuntimeFactory();
        final NashornRuntime nashornRuntime = nashornRuntimeFactory.newRuntime( this.classLoader );

        this.executor.setEnvironment( this );
        this.executor.setNashornRuntime( nashornRuntime );
        this.executor.init();
        this.executor.addGlobalVariables( this.globalVariables );

        this.module.init( this );
    }
}
