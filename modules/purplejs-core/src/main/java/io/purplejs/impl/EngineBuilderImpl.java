package io.purplejs.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.purplejs.Engine;
import io.purplejs.EngineBuilder;
import io.purplejs.resource.ResourceLoader;
import io.purplejs.resource.ResourceLoaderBuilder;

public final class EngineBuilderImpl
    implements EngineBuilder
{
    private boolean devMode;

    private ClassLoader classLoader;

    private ResourceLoader resourceLoader;

    private final List<File> devSourceDirs;

    private final Map<String, Object> globalVariables;

    private final Map<String, String> config;

    public EngineBuilderImpl()
    {
        this.devSourceDirs = Lists.newArrayList();
        this.globalVariables = Maps.newHashMap();
        this.config = Maps.newHashMap();
        setupFromEnvironment();
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

    private void setupFromEnvironment()
    {
        setDevModeFromEnv();
        setDevSourceDirsFromEnv();
    }

    private void setDevModeFromEnv()
    {
        final String value = System.getProperty( "purplejs.devMode" );

        try
        {
            this.devMode = Boolean.parseBoolean( value );
        }
        catch ( final Exception e )
        {
            this.devMode = false;
        }
    }

    private void setDevSourceDirsFromEnv()
    {
        final String value = System.getProperty( "purplejs.devSourceDirs" );
        if ( value == null )
        {
            return;
        }

        Splitter.on( ',' ).trimResults().omitEmptyStrings().split( value ).forEach( str -> this.devSourceDirs.add( new File( str ) ) );
    }

    @Override
    public Engine build()
    {
        setupDefaults();

        final ScriptSettingsImpl settings = new ScriptSettingsImpl();
        settings.devMode = this.devMode;
        settings.classLoader = this.classLoader;
        settings.resourceLoader = createResourceLoader();
        settings.config = ImmutableMap.copyOf( this.config );
        settings.globalVariables = ImmutableMap.copyOf( this.globalVariables );

        final EngineImpl engine = new EngineImpl();
        engine.settings = settings;
        engine.init();

        return engine;
    }
}
