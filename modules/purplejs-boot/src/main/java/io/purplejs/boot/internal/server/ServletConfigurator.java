package io.purplejs.boot.internal.server;

import java.io.File;
import java.util.EnumSet;
import java.util.List;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.Servlet;

import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.google.common.io.Files;

import io.purplejs.boot.internal.config.Configurable;
import io.purplejs.boot.internal.servlet.AssetFilter;
import io.purplejs.boot.internal.servlet.ScriptServlet;
import io.purplejs.core.Engine;
import io.purplejs.core.RunMode;
import io.purplejs.core.settings.Settings;

public final class ServletConfigurator
    implements Configurable
{
    private ServletContextHandler handler;

    private Engine engine;

    private List<File> devSourceDirs;

    public ServletContextHandler getHandler()
    {
        return this.handler;
    }

    public void setEngine( final Engine engine )
    {
        this.engine = engine;
    }

    public void setDevSourceDirs( final List<File> devSourceDirs )
    {
        this.devSourceDirs = devSourceDirs;
    }

    @Override
    public void configure( final Settings settings )
    {
        this.handler = new ServletContextHandler( ServletContextHandler.SESSIONS );

        final AssetFilter filter = new AssetFilter();
        filter.setDevMode( RunMode.get() == RunMode.DEV );
        filter.setDevSourceDirs( this.devSourceDirs );
        registerFilter( filter );

        final ScriptServlet servlet = new ScriptServlet();
        servlet.setEngine( this.engine );
        registerServlet( servlet );
    }

    private void registerFilter( final Filter filter )
    {
        final FilterHolder holder = new FilterHolder( filter );
        this.handler.addFilter( holder, "/*", EnumSet.of( DispatcherType.REQUEST ) );
    }

    private void registerServlet( final Servlet servlet )
    {
        final ServletHolder holder = new ServletHolder( servlet );
        this.handler.addServlet( holder, "/*" );

        final String location = Files.createTempDir().getAbsolutePath();
        final MultipartConfigElement multipartConfig = new MultipartConfigElement( location );
        holder.getRegistration().setMultipartConfig( multipartConfig );
    }
}
