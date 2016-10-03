package io.purplejs.boot.internal.server;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import io.purplejs.boot.internal.config.Configurable;
import io.purplejs.core.Engine;
import io.purplejs.core.EngineBuilder;
import io.purplejs.core.RunMode;
import io.purplejs.core.settings.Settings;

public final class EngineConfigurator
    implements Configurable
{
    private final static Logger LOG = Logger.getLogger( EngineConfigurator.class.getName() );

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
        builder.settings( settings.getAsSettings( "app" ) );
        configureDevMode( settings, builder );
    }

    private void configureDevMode( final Settings settings, EngineBuilder builder )
    {
        final RunMode runMode = RunMode.get();
        if ( runMode != RunMode.DEV )
        {
            return;
        }

        final List<File> devSourceDirs = settings.getAsArray( File.class, "devSourceDirs", File::new );
        devSourceDirs.forEach( builder::devSourceDir );

        LOG.info( "Running in DEV mode. Do not use in production!" );

        LOG.info( "Monitoring the following directories (devSourceDirs) for changes:" );
        devSourceDirs.forEach( file -> LOG.info( "*#* " + file ) );
    }
}
