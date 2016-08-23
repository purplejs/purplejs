package io.purplejs.boot.internal.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import io.purplejs.core.settings.Settings;
import io.purplejs.core.settings.SettingsBuilder;

public final class ConfigBuilder
{
    private final SettingsBuilder builder;

    public ConfigBuilder()
    {
        this.builder = SettingsBuilder.newBuilder();
        this.builder.put( System.getProperties() );
        this.builder.put( "env", System.getenv() );

        load( getClass(), "default.properties" );
        load( getClass().getClassLoader(), "config.properties" );
        applyOverrides();
    }

    public Settings build()
    {
        return this.builder.interpolate().build();
    }

    private ConfigBuilder load( final File file )
    {
        if ( !file.isFile() )
        {
            return this;
        }

        try
        {
            doLoad( file.getPath(), new FileInputStream( file ) );
            return this;
        }
        catch ( final IOException e )
        {
            return this;
        }
    }

    private ConfigBuilder load( final ClassLoader loader, final String path )
    {
        final InputStream in = loader.getResourceAsStream( path );
        if ( in == null )
        {
            return this;
        }

        doLoad( "classpath:" + path, in );
        return this;
    }

    private ConfigBuilder load( final Class context, final String path )
    {
        final String fullPath = context.getName().replace( '.', '/' ) + "/" + path;
        return load( context.getClassLoader(), fullPath );
    }

    private void doLoad( final String path, final InputStream in )
    {
        try
        {
            final Properties props = new Properties();
            props.load( in );
            this.builder.put( props );
        }
        catch ( final IOException e )
        {
            throw new RuntimeException( "Error loading [" + path + "]", e );
        }
    }

    private ConfigBuilder applyOverrides()
    {
        this.builder.put( SettingsBuilder.newBuilder().
            put( System.getProperties() ).
            build().
            getAsSettings( "io.purplejs" ) );

        return this;
    }
}
