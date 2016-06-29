package io.purplejs.v2.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.Stage;
import com.google.inject.util.Modules;

import io.purplejs.resource.ResourceLoader;
import io.purplejs.resource.ResourceLoaderBuilder;
import io.purplejs.v2.Engine;
import io.purplejs.v2.EngineBuilder;
import io.purplejs.v2.EngineModule;
import io.purplejs.v2.Environment;
import io.purplejs.v2.RunMode;

public final class EngineBuilderImpl
    extends EngineModule
    implements EngineBuilder
{
    // private final List<EngineModule> modules;
    private RunMode mode;

    private ClassLoader classLoader;

    private ResourceLoader resourceLoader;

    private final List<File> devSourceDirs;

    private final Map<String, Object> globalVariables;

    private final Map<String, String> config;

    private Module module;

    public EngineBuilderImpl()
    {
        this.module = this;
        this.devSourceDirs = Lists.newArrayList();
        this.globalVariables = Maps.newHashMap();
        this.config = Maps.newHashMap();
    }

    @Override
    public EngineBuilder mode( final RunMode mode )
    {
        this.mode = mode;
        return this;
    }

    @Override
    public EngineBuilder module( final EngineModule module )
    {
        this.module = Modules.override( this.module ).with( module );
        return this;
    }

    @Override
    public EngineBuilder devMode( final boolean devMode )
    {
        return mode( RunMode.DEV );
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
    public EngineBuilder globalVariable( final String name, final Object value )
    {
        this.globalVariables.put( name, value );
        return this;
    }

    @Override
    public EngineBuilder config( final String name, final String value )
    {
        this.config.put( name, value );
        return this;
    }

    private void setupDefaults()
    {
        if ( this.mode == null )
        {
            this.mode = RunMode.PROD;
        }

        if ( this.classLoader == null )
        {
            this.classLoader = getClass().getClassLoader();
        }

        if ( this.resourceLoader == null )
        {
            this.resourceLoader = ResourceLoaderBuilder.create().from( this.classLoader ).build();
        }
    }

    @Override
    protected void configure()
    {
        bind( RunMode.class ).toInstance( this.mode );
        bind( Engine.class ).to( EngineImpl.class ).in( Singleton.class );
        bind( ResourceLoader.class ).toInstance( this.resourceLoader );

        final Environment env = createEnvironment();
        requestInjection( env );

        bind( Environment.class ).toInstance( env );
    }

    private Environment createEnvironment()
    {
        final EnvironmentImpl env = new EnvironmentImpl();
        env.classLoader = this.classLoader;
        env.globalVariables = ImmutableMap.copyOf( this.globalVariables );
        env.config = ImmutableMap.copyOf( this.config );
        return env;
    }

    @Override
    public Engine build()
    {
        setupDefaults();

        final Stage stage = this.mode == RunMode.PROD ? Stage.PRODUCTION : Stage.DEVELOPMENT;
        final Injector injector = Guice.createInjector( stage, this.module );
        return injector.getInstance( Engine.class );
    }
}
