package io.purplejs.core.internal;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.purplejs.core.Engine;
import io.purplejs.core.EngineBinder;
import io.purplejs.core.EngineBuilder;
import io.purplejs.core.EngineModule;
import io.purplejs.core.RunMode;
import io.purplejs.core.internal.util.RequirementChecker;
import io.purplejs.core.registry.RegistryBuilder;
import io.purplejs.core.resource.ResourceLoader;
import io.purplejs.core.resource.ResourceLoaderBuilder;
import io.purplejs.core.resource.ResourceResolver;
import io.purplejs.core.resource.ResourceResolverBuilder;
import io.purplejs.core.settings.Settings;
import io.purplejs.core.settings.SettingsBuilder;

public final class EngineBuilderImpl
    implements EngineBuilder, EngineBinder
{
    private ClassLoader classLoader;

    private ResourceLoader resourceLoader;

    private ResourceResolver resourceResolver;

    private final List<File> devSourceDirs;

    private final Map<String, Object> globalVariables;

    private final Map<String, String> config;

    private final CompositeModule module;

    private final RegistryBuilder registryBuilder;

    private Settings settings;

    public EngineBuilderImpl()
    {
        RequirementChecker.check();

        this.devSourceDirs = Lists.newArrayList();
        this.globalVariables = Maps.newHashMap();
        this.config = Maps.newHashMap();
        this.registryBuilder = RegistryBuilder.newBuilder();

        this.module = new CompositeModule();
        this.module.autoLoad();
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
    public EngineBuilder resourceResolver( final ResourceResolver resourceResolver )
    {
        this.resourceResolver = resourceResolver;
        return this;
    }

    @Override
    public EngineBuilder module( final EngineModule module )
    {
        this.module.add( module );
        return this;
    }

    @Override
    public EngineBinder globalVariable( final String name, final Object value )
    {
        this.globalVariables.put( name, value );
        return this;
    }

    @Override
    public EngineBuilder settings( final Settings settings )
    {
        this.settings = settings;
        return this;
    }

    @Override
    public <T> EngineBinder instance( final Class<T> type, final T instance )
    {
        this.registryBuilder.instance( type, instance );
        return this;
    }

    @Override
    public <T> EngineBinder provider( final Class<T> type, final Supplier<T> supplier )
    {
        this.registryBuilder.provider( type, supplier );
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
            this.resourceLoader = ResourceLoaderBuilder.newBuilder().from( this.classLoader ).build();
        }

        if ( this.resourceResolver == null )
        {
            this.resourceResolver = ResourceResolverBuilder.newBuilder().build();
        }

        if ( this.settings == null )
        {
            this.settings = SettingsBuilder.newBuilder().build();
        }
    }

    private ResourceLoader createResourceLoader()
    {
        if ( RunMode.get() != RunMode.DEV )
        {
            return this.resourceLoader;
        }

        final ResourceLoaderBuilder builder = ResourceLoaderBuilder.newBuilder();
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
        engine.classLoader = this.classLoader;
        engine.resourceLoader = createResourceLoader();
        engine.resourceResolver = this.resourceResolver;
        engine.config = ImmutableMap.copyOf( this.config );
        engine.globalVariables = ImmutableMap.copyOf( this.globalVariables );
        engine.devSourceDirs = ImmutableList.copyOf( this.devSourceDirs );
        engine.module = this.module;
        engine.settings = this.settings;

        this.registryBuilder.instance( Engine.class, engine );
        this.registryBuilder.instance( ResourceLoader.class, this.resourceLoader );

        engine.registry = this.registryBuilder.build();
        engine.init();

        return engine;
    }
}
