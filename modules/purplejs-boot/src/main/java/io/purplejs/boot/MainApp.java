package io.purplejs.boot;

import io.purplejs.boot.internal.BannerPrinter;
import io.purplejs.boot.internal.server.ServerConfigurator;
import io.purplejs.boot.settings.SettingsLoader;
import io.purplejs.core.settings.Settings;
import io.purplejs.core.settings.SettingsBuilder;

public final class MainApp
{
    public void start()
        throws Exception
    {
        // Print banner
        new BannerPrinter().printBanner();

        final SettingsBuilder builder = SettingsBuilder.newBuilder();
        builder.put( System.getProperties() );
        builder.put( "env", System.getenv() );

        final SettingsLoader loader = new SettingsLoader( builder );
        loader.load( getClass(), "default.properties" );
        loader.load( getClass().getClassLoader(), "config.properties" );

        final Settings settings = builder.interpolate().build();

        final ServerConfigurator serverConfigurator = new ServerConfigurator();
        serverConfigurator.configure( settings );

        serverConfigurator.getServer().start();
    }

    public static void main( final String... args )
        throws Exception
    {
        new MainApp().start();
    }
}
