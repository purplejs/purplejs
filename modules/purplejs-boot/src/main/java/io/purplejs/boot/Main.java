package io.purplejs.boot;

import javax.servlet.MultipartConfigElement;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.google.common.io.Files;

import io.purplejs.servlet.ScriptServlet;

public final class Main
{
    public static void main( final String... args )
        throws Exception
    {
        final Server server = new Server( 8080 );
        final ServletContextHandler context = new ServletContextHandler( ServletContextHandler.SESSIONS );
        server.setHandler( context );

        final ServletHolder servlet = context.addServlet( ScriptServlet.class, "/*" );
        servlet.setInitParameter( "resource", "/app/main.js" );
        servlet.setInitParameter( "devMode", "true" );
        servlet.setInitParameter( "devSourceDirs", "/Users/srs/development/workspace/purplejs/modules/purplejs-boot/src/main/resources" );

        final String location = Files.createTempDir().getAbsolutePath();
        final MultipartConfigElement multipartConfig = new MultipartConfigElement( location );
        servlet.getRegistration().setMultipartConfig( multipartConfig );

        server.start();
    }
}
