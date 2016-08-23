package io.purplejs.boot;

import io.purplejs.boot.internal.BannerPrinter;
import io.purplejs.boot.settings.SettingsLoader;
import io.purplejs.core.settings.Settings;
import io.purplejs.core.settings.SettingsBuilder;

public final class MainApp
{
    public void start()
        throws Exception
    {
        // System.setProperty( "io.purplejs.devSourceDirs", "a" );

        // Print banner
        new BannerPrinter().printBanner();

        final SettingsBuilder builder = SettingsBuilder.newBuilder();

        final SettingsLoader loader = new SettingsLoader( builder );
        loader.load( getClass(), "default.properties" );
        loader.load( getClass().getClassLoader(), "config.properties" );
        loader.applyOverrides();

        final Settings settings = builder.interpolate().build();

        /*
        final ServerConfigurator serverConfigurator = new ServerConfigurator();
        serverConfigurator.configure( settings );

        serverConfigurator.getServer().start();
        */

        System.out.println( settings.get( "devSourceDirs" ) );

    }

    public static void main( final String... args )
        throws Exception
    {
        new MainApp().start();
    }
}
