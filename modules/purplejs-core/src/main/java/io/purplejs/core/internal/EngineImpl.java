package io.purplejs.core.internal;

import java.io.File;
import java.util.List;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import io.purplejs.core.Engine;
import io.purplejs.core.inject.BeanInjector;
import io.purplejs.core.internal.executor.ScriptExecutorImpl;
import io.purplejs.core.internal.nashorn.NashornRuntime;
import io.purplejs.core.internal.nashorn.NashornRuntimeFactory;
import io.purplejs.core.registry.Registry;
import io.purplejs.core.resource.ResourceLoader;
import io.purplejs.core.resource.ResourcePath;
import io.purplejs.core.resource.ResourceResolver;
import io.purplejs.core.settings.Settings;
import io.purplejs.core.value.ScriptExports;

final class EngineImpl
    implements Engine
{
    ResourceLoader resourceLoader;

    ResourceResolver resourceResolver;

    BeanInjector beanInjector;

    ClassLoader classLoader;

    ImmutableMap<String, String> config;

    ImmutableMap<String, Object> globalVariables;

    Registry registry;

    CompositeModule module;

    Settings settings;

    ImmutableList<File> devSourceDirs;

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
    public ResourceResolver getResourceResolver()
    {
        return this.resourceResolver;
    }

    @Override
    public BeanInjector getBeanInjector()
    {
        return this.beanInjector;
    }

    @Override
    public ClassLoader getClassLoader()
    {
        return this.classLoader;
    }

    @Override
    public Settings getSettings()
    {
        return this.settings;
    }

    @Override
    public <T> T getInstance( final Class<T> type )
    {
        return this.registry.getInstance( type );
    }

    @Override
    public <T> T getInstanceOrNull( final Class<T> type )
    {
        return this.registry.getInstanceOrNull( type );
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
    public List<File> getDevSourceDirs()
    {
        return this.devSourceDirs;
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
