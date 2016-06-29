package io.purplejs.boot;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import io.purplejs.servlet.ScriptServlet;

public final class Main
{
    public static void main( final String... args )
        throws Exception
    {
        final ServletHandler handler = new ServletHandler();
        final ServletHolder servlet = handler.addServletWithMapping( ScriptServlet.class, "/*" );
        servlet.setInitParameter( "resource", "/app/main.js" );
        servlet.setInitParameter( "devMode", "true" );
        servlet.setInitParameter( "devSourceDirs", "/Users/srs/development/workspace/purplejs/modules/purplejs-boot/src/main/resources" );

        final Server server = new Server( 8080 );
        server.setHandler( handler );
        server.start();
    }
}
