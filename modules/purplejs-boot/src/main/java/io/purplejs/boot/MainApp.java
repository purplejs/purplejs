package io.purplejs.boot;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.slf4j.bridge.SLF4JBridgeHandler;

import io.purplejs.boot.internal.BannerPrinter;
import io.purplejs.boot.internal.config.ConfigBuilder;
import io.purplejs.boot.internal.server.ServerConfigurator;
import io.purplejs.core.settings.Settings;

public final class MainApp
{
    private Server server;

    public void start()
        throws Exception
    {
        // Setup logging bridge
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        // Add shutdown-hook
        Runtime.getRuntime().addShutdownHook( new Thread()
        {
            @Override
            public void run()
            {
                MainApp.this.stop();
            }
        } );

        // Set some system properties
        System.setProperty( "java.net.preferIPv4Stack", "true" );

        // Print banner
        new BannerPrinter().printBanner();

        // Load settings
        final Settings settings = new ConfigBuilder().build();

        // Configure the server
        final ServerConfigurator serverConfigurator = new ServerConfigurator();
        serverConfigurator.configure( settings );

        // Start the server
        this.server = serverConfigurator.getServer();
        this.server.start();
    }

    public int getPort()
    {
        return ( (ServerConnector) server.getConnectors()[0] ).getLocalPort();
    }

    public void stop()
    {
        try
        {
            if ( this.server != null )
            {
                this.server.stop();
            }
        }
        catch ( final Exception e )
        {
            // Ignore
        }
    }

    public static void main( final String... args )
        throws Exception
    {
        new MainApp().start();
    }
}
