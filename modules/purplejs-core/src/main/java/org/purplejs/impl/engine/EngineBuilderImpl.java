package org.purplejs.impl.engine;

import java.util.Map;

import org.purplejs.engine.Engine;
import org.purplejs.engine.EngineBuilder;
import org.purplejs.resource.ResourceLoader;
import org.purplejs.resource.ResourceLoaderBuilder;

import com.google.common.collect.Maps;

public final class EngineBuilderImpl
    implements EngineBuilder
{
    private boolean devMode;

    private ClassLoader classLoader;

    private ResourceLoader resourceLoader;

    private final Map<String, Object> globalVariables;

    private final Map<String, String> config;

    public EngineBuilderImpl()
    {
        this.globalVariables = Maps.newHashMap();
        this.config = Maps.newHashMap();
    }

    @Override
    public EngineBuilder devMode( final boolean devMode )
    {
        this.devMode = devMode;
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

    @Override
    public Engine build()
    {
        setupDefaults();

        final EngineImpl engine = new EngineImpl();
        engine.setDevMode( this.devMode );
        engine.setClassLoader( this.classLoader );
        engine.setResourceLoader( this.resourceLoader );
        engine.setGlobalVariables( this.globalVariables );
        engine.setConfig( this.config );
        engine.init();

        return engine;
    }
}
