package io.purplejs.v3.impl;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.purplejs.resource.ResourceLoader;
import io.purplejs.resource.ResourceLoaderBuilder;
import io.purplejs.v3.Engine;
import io.purplejs.v3.EngineBinder;
import io.purplejs.v3.EngineBuilder;
import io.purplejs.v3.EngineModule;
import io.purplejs.v3.registry.AwareInjector;
import io.purplejs.v3.registry.RegistryBuilder;

public final class EngineBuilderImpl
    implements EngineBuilder, EngineBinder
{
    private final CompositeModule module;

    private boolean devMode;

    private ClassLoader classLoader;

    private ResourceLoader resourceLoader;

    private final List<File> devSourceDirs;

    private final Map<String, Object> globalVariables;

    private final Map<String, String> config;

    private final RegistryBuilder registryBuilder;

    public EngineBuilderImpl()
    {
        this.module = new CompositeModule();
        this.devSourceDirs = Lists.newArrayList();
        this.globalVariables = Maps.newHashMap();
        this.config = Maps.newHashMap();
        this.registryBuilder = new RegistryBuilder();
    }

    @Override
    public EngineBuilder module( final EngineModule module )
    {
        this.module.add( module );
        return this;
    }

    @Override
    public EngineBuilder devMode( final boolean devMode )
    {
        this.devMode = devMode;
        return this;
    }

    @Override
    public EngineBuilder devSourceDir( final File dir )
    {
        this.devSourceDirs.add( dir );
        return this;
    }

    @Override
    public EngineBuilder classLoader( final ClassLoader classLoader )
    {
        this.classLoader = classLoader;
        return this;
    }

    @Override
    public EngineBuilder resourceLoader( final ResourceLoader resourceLoader )
    {
        this.resourceLoader = resourceLoader;
        return this;
    }

    @Override
    public <T> EngineBinder instance( final Class<T> type, final T instance )
    {
        this.registryBuilder.instance( type, instance );
        return this;
    }

    @Override
    public <T> EngineBinder supplier( final Class<T> type, final Supplier<T> supplier )
    {
        this.registryBuilder.supplier( type, supplier );
        return this;
    }

    @Override
    public EngineBinder globalVariable( final String name, final Object value )
    {
        this.globalVariables.put( name, value );
        return this;
    }

    @Override
    public EngineBinder config( final String name, final String value )
    {
        this.config.put( name, value );
        return this;
    }

    @Override
    public EngineBinder initializer( final Consumer<Engine> initializer )
    {
        this.module.addInitializer( initializer );
        return this;
    }

    @Override
    public EngineBinder disposer( final Consumer<Engine> disposer )
    {
        this.module.addDisposer( disposer );
        return this;
    }

    private void setupDefaults()
    {
        if ( this.classLoader == null )
        {
            this.classLoader = getClass().getClassLoader();
        }

        if ( this.resourceLoader == null )
        {
            this.resourceLoader = ResourceLoaderBuilder.create().from( this.classLoader ).build();
        }
    }

    private ResourceLoader createResourceLoader()
    {
        if ( !this.devMode )
        {
            return this.resourceLoader;
        }

        final ResourceLoaderBuilder builder = ResourceLoaderBuilder.create();
        this.devSourceDirs.forEach( builder::from );
        builder.add( this.resourceLoader );
        return builder.build();
    }

    @Override
    public Engine build()
    {
        setupDefaults();

        this.module.configure( this );

        final EngineImpl engine = new EngineImpl();
        engine.devMode = this.devMode;
        engine.classLoader = this.classLoader;
        engine.resourceLoader = createResourceLoader();
        engine.config = ImmutableMap.copyOf( this.config );
        engine.globalVariables = ImmutableMap.copyOf( this.globalVariables );
        engine.module = this.module;

        this.registryBuilder.injector( new AwareInjector( engine ) );
        this.registryBuilder.instance( Engine.class, engine );
        this.registryBuilder.instance( ResourceLoader.class, this.resourceLoader );

        engine.registry = this.registryBuilder.build();
        engine.init();

        return engine;
    }
}
