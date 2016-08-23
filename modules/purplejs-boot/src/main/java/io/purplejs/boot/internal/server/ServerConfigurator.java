package io.purplejs.boot.internal.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;

import io.purplejs.boot.internal.config.Configurable;
import io.purplejs.core.settings.Settings;

public final class ServerConfigurator
    implements Configurable
{
    private Server server;

    private HandlerCollection handlers;

    public Server getServer()
    {
        return this.server;
    }

    @Override
    public void configure( final Settings settings )
    {
        final EngineConfigurator engineConfigurator = new EngineConfigurator();
        engineConfigurator.configure( settings );

        final ServletConfigurator servletConfigurator = new ServletConfigurator();
        servletConfigurator.setEngine( engineConfigurator.getEngine() );
        servletConfigurator.setDevSourceDirs( engineConfigurator.getEngine().getDevSourceDirs() );
        servletConfigurator.configure( settings );

        this.handlers = new HandlerCollection();
        this.handlers.addHandler( servletConfigurator.getHandler() );

        configureServer( settings.getAsSettings( "server" ) );
    }

    private void configureServer( final Settings settings )
    {
        this.server = new Server( settings.get( Integer.class, "port", 8080 ) );
        this.server.setHandler( this.handlers );
    }
}
