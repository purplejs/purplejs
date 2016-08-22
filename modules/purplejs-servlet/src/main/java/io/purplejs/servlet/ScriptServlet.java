package io.purplejs.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.purplejs.core.Engine;
import io.purplejs.core.EngineBinder;
import io.purplejs.core.EngineBuilder;
import io.purplejs.http.Request;
import io.purplejs.http.Response;
import io.purplejs.http.handler.HttpHandler;
import io.purplejs.http.handler.HttpHandlerFactory;
import io.purplejs.servlet.internal.RequestWrapper;
import io.purplejs.servlet.internal.ResponseSerializer;

public class ScriptServlet
    extends HttpServlet
{
    Engine engine;

    HttpHandler handler;

    @Override
    public void init( final ServletConfig config )
        throws ServletException
    {
        super.init( config );
        configure( new ScriptServletConfig( config ) );
    }

    private void configure( final ScriptServletConfig config )
    {
        final EngineBuilder builder = EngineBuilder.newBuilder();
        configure( config, builder );
        this.engine = builder.build();

        final HttpHandlerFactory handlerFactory = this.engine.getInstance( HttpHandlerFactory.class );
        this.handler = handlerFactory.newHandler( config.getResource() );
    }

    private void configure( final ScriptServletConfig config, final EngineBuilder builder )
    {
        builder.classLoader( getClass().getClassLoader() );
        config.getDevSourceDirs().forEach( builder::devSourceDir );
        builder.module( binder -> configure( config, binder ) );
    }

    private void configure( final ScriptServletConfig config, final EngineBinder binder )
    {
        config.getConfig().forEach( binder::config );
    }

    @Override
    protected void service( final HttpServletRequest req, final HttpServletResponse resp )
        throws ServletException, IOException
    {
        final RequestWrapper requestWrapper = new RequestWrapper( req );
        final Response response = serve( requestWrapper );
        new ResponseSerializer( resp ).serialize( response );
    }

    private Response serve( final Request request )
    {
        try
        {
            final Response response = this.handler.serve( request );
            return this.handler.errorIfNeeded( request, response );
        }
        catch ( final Exception e )
        {
            return this.handler.handleException( request, e );
        }
    }

    @Override
    public void destroy()
    {
        this.engine.dispose();
    }
}
