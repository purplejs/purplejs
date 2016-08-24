package io.purplejs.boot;

import org.eclipse.jetty.server.Server;

import io.purplejs.boot.internal.BannerPrinter;
import io.purplejs.boot.internal.config.ConfigBuilder;
import io.purplejs.boot.internal.server.ServerConfigurator;
import io.purplejs.core.settings.Settings;

public final class MainApp
{
    public void start()
        throws Exception
    {
        System.setProperty( "java.net.preferIPv4Stack", "true" );

        // Print banner
        new BannerPrinter().printBanner();

        // Load settings
        final Settings settings = new ConfigBuilder().build();

        // Configure the server
        final ServerConfigurator serverConfigurator = new ServerConfigurator();
        serverConfigurator.configure( settings );

        // Start the server
        final Server server = serverConfigurator.getServer();
        server.start();
    }

    public static void main( final String... args )
        throws Exception
    {
        new MainApp().start();
    }
}
