package io.purplejs.boot.internal.server;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.purplejs.boot.internal.config.ConfigHelper;
import io.purplejs.boot.internal.config.Configurable;
import io.purplejs.core.Engine;
import io.purplejs.core.EngineBuilder;
import io.purplejs.core.RunMode;
import io.purplejs.core.settings.Settings;

public final class EngineConfigurator
    implements Configurable
{
    private final static Logger LOG = LoggerFactory.getLogger( EngineConfigurator.class );

    private Engine engine;

    public Engine getEngine()
    {
        return this.engine;
    }

    @Override
    public void configure( final Settings settings )
    {
        final EngineBuilder builder = EngineBuilder.newBuilder();
        configure( settings, builder );
        this.engine = builder.build();
    }

    private void configure( final Settings settings, final EngineBuilder builder )
    {
        builder.classLoader( getClass().getClassLoader() );
        builder.settings( settings );
        configureDevMode( settings, builder );
    }

    private void configureDevMode( final Settings settings, EngineBuilder builder )
    {
        final RunMode runMode = RunMode.get();
        if ( runMode != RunMode.DEV )
        {
            return;
        }

        final List<File> devSourceDirs = new ConfigHelper( settings ).getDevSourceDirs();
        devSourceDirs.forEach( builder::devSourceDir );

        LOG.info( "Running in DEV mode. Do not use in production!" );
        LOG.debug( "Using devSourceDirs = " + devSourceDirs.toString() );
    }
}
